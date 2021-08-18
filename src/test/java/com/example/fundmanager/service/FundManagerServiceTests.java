package com.example.fundmanager.service;

import com.example.fundmanager.dao.FundManagerRepository;
import com.example.fundmanager.entity.FundManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FundManagerServiceTests {

    @Mock
    private FundManagerRepository fundManagerRepository;

    @InjectMocks
    private FundManagerService fundManagerService;

    List<FundManager> defaultMangers = List.of(
            new FundManager(1L, "Terry", "Jone", new ArrayList<>()),
            new FundManager(2L, "Mike", "Smith", new ArrayList<>())
    );


    @Test
    public void testManagers(){

        when(fundManagerRepository.findAll()).thenReturn(defaultMangers);

        List<FundManager> managers = fundManagerService.getManagers();
        assertNotNull(managers);
        assertTrue(managers.size() > 0);
    }

    @Test
    public void testGetFundManagerSuccess(){
        when(fundManagerRepository.findById(1L))
                .thenReturn(Optional.of(defaultMangers.get(0)));
        FundManager manager = fundManagerService.getManager(1L);
        assertNotNull(manager);
    }

    @Test
    public void testAddFundManager(){
        FundManager newManager = new FundManager(3L, "John", "Smith", new ArrayList<>());

        fundManagerService.addManager(newManager);

        verify(fundManagerRepository).save(newManager);
    }
}
