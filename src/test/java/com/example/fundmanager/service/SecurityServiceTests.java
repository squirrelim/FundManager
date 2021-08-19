package com.example.fundmanager.service;

import  com.example.fundmanager.dao.SecurityRepository;
import  com.example.fundmanager.entity.Security;
import com.example.fundmanager.entity.Fund;
import com.example.fundmanager.exception.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class SecurityServiceTests {
    @Mock
    private SecurityRepository securityRepository;

    @InjectMocks
    private SecurityService securityService;

    List<Security> defaultSecurities = List.of(
            new Security(1L, "IBM"),
            new Security(2L, "Microsoft")
    );

    @Test
    public void testGetSecurities(){

        when(securityRepository.findAll()).thenReturn(defaultSecurities);

        List<Security> securities = securityService.getSecurities();
        assertNotNull(securities);
        assertTrue(securities.size() > 0);
    }

    @Test
    public void testGetSecuritySuccess(){
        when(securityRepository.findById(1L))
                .thenReturn(Optional.of(defaultSecurities.get(0)));
        Security security = securityService.getSecurity(1L);
        assertNotNull(security);
    }

    @Test
    public void testGetSecurityNotFound(){
        assertThrows(SecurityNotFoundException.class,
                () -> securityService.getSecurity(0L));
    }

    @Test
    public void testAddSecuritySuccess(){
        Security newSecurity = new Security(3L,"Facebook");

        securityService.addSecurity(newSecurity);
        verify(securityRepository).save(newSecurity);
    }

    @Test
    public void testAddSecurityAlreadyInUse(){
        doThrow(SecurityAlreadyInUseException.class).when(securityRepository).save(any(Security.class));
        Security newSecurity = new Security(1L, "IBM");
        assertThrows(SecurityAlreadyInUseException.class,
                () -> securityService.addSecurity(newSecurity));
        verify(securityRepository).save(newSecurity);
    }

    @Test
    public void testUpdateSecuritySuccess(){
        Security newSecurity = new Security(1L,"Oracle1");
        Long id = 1L;


        when(securityRepository.findById(id)).thenReturn(Optional.of(defaultSecurities.get(1)));
        securityService.updateSecurity(id,newSecurity);
        Security security = securityService.getSecurity(1L);
        assertEquals(newSecurity.getSymbol(), security.getSymbol());
    }

    @Test
    public void testUpdateSecurityNotMatching(){
        Security newSecurity= new Security(1L,"Oracle1");
        Long id = 2L;

        when(securityRepository.findById(id)).thenReturn(Optional.of(defaultSecurities.get(1)));
        assertFalse(securityRepository.findSecurityBySymbol(newSecurity.getSymbol()).isPresent());

        assertThrows(SecurityIdNotMatchingException.class,
                () -> securityService.updateSecurity(id, newSecurity));
    }

    @Test
    public void testUpdateSecurityNotFound(){
        Security newSecurity = new Security(4L,"Oracle");
        Long id = 4L;

        assertThrows(SecurityNotFoundException.class,
                () -> securityService.updateSecurity(id, newSecurity));
    }

    @Test
    public void testUpdateSecurityAlreadyInUse(){
        Security newSecurity = new Security(1L,"IBM");
        Long id = 1L;

        when(securityRepository.findById(id)).thenReturn(Optional.of(defaultSecurities.get(1)));
        when(securityRepository.findSecurityBySymbol(newSecurity.getSymbol())).thenReturn(Optional.of(defaultSecurities.get(1)));
        assertThrows(SecurityAlreadyInUseException.class,
                () -> securityService.updateSecurity(id, newSecurity));
    }

    @Test
    public void testUpdateSecurityIllegalUpdate(){
        Security newSecurity = new Security(1L,null);
        Long id = 1L;

        assertThrows(IllegalUpdatedSecurityException.class,
                () -> securityService.updateSecurity(id, newSecurity));
    }

    @Test
    public void testRemoveSecuritySuccess(){
        Long id = 1L;
        when(securityRepository.existsById(id)).thenReturn(true);
        securityService.removeSecurity(id);
        verify(securityRepository).deleteById(anyLong());
    }

    @Test
    public void testRemoveSecurityNotFound(){
        assertThrows(SecurityNotFoundException.class,
                () -> securityService.removeSecurity(5L));
    }
}
