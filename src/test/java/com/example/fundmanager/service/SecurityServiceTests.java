package com.example.fundmanager.service;

import  com.example.fundmanager.dao.SecurityRepository;
import  com.example.fundmanager.entity.Security;
import com.example.fundmanager.exception.SecurityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

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
    public void testAddNewSecurity(){
        Security newSecurity = new Security(3L,"Facebook");

        securityService.addSecurity(newSecurity);
        verify(securityRepository).save(newSecurity);
    }
}
