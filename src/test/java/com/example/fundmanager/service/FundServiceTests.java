package com.example.fundmanager.service;

import com.example.fundmanager.dao.FundRepository;
import com.example.fundmanager.entity.Fund;
import com.example.fundmanager.exception.FundAlreadyInUseException;
import com.example.fundmanager.exception.FundIdNotMatchingException;
import com.example.fundmanager.exception.FundNotFoundException;
import com.example.fundmanager.exception.IllegalUpdatedFundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FundServiceTests {
    @Mock
    private FundRepository fundRepository;

    @InjectMocks
    private FundService fundService;

    List<Fund> defaultFunds = List.of(
            new Fund(1L, "Olympic Memorial Fund", 1L),
            new Fund(2L, "UK Overseas Income Fund", 1L),
            new Fund(3L, "North America Growth", 2L),
            new Fund(4L, "Global Tech Fund", 2L)
    );

    @Test
    public void testGetFundsSuccess(){
        when(fundRepository.findAll()).thenReturn(defaultFunds);

        List<Fund> funds = fundService.getFunds();
        assertNotNull(funds);
        assertTrue(funds.size() > 0);
    }

    @Test
    public void testGetFundSuccess(){
        when(fundRepository.findById(1L)).thenReturn(Optional.of(defaultFunds.get(0)));

        Fund fund = fundService.getFund(1L);
        assertNotNull(fund);
    }

    @Test
    public void testGetFundNotFound(){
        assertThrows(FundNotFoundException.class,
                () -> fundService.getFund(0L));
    }

    @Test
    public void testAddFundSuccess(){
        Fund newFund = new Fund("Income Fund", 1L);
        fundService.addFund(newFund);
        verify(fundRepository).save(newFund);
    }

    @Test
    public void testAddFundAlreadyInUse(){
        doThrow(FundAlreadyInUseException.class).when(fundRepository).save(any(Fund.class));
        Fund newFund = new Fund("Olympic Memorial Fund", 1L);
        assertThrows(FundAlreadyInUseException.class,
                () -> fundService.addFund(newFund));
        verify(fundRepository).save(newFund);
    }

    @Test
    public void testRemoveFundSuccess(){
        Long id = 1L;
        when(fundRepository.existsById(id)).thenReturn(true);
        fundService.removeFund(id);
        verify(fundRepository).deleteById(anyLong());
    }

    @Test
    public void testRemoveFundNotFound(){
        assertThrows(FundNotFoundException.class,
                () -> fundService.removeFund(0L));
    }

    @Test
    public void testUpdateFundSuccess(){
        Fund updatedFund = new Fund(1L,"Income Fund", 1L);
        Long id = 1L;

        when(fundRepository.findById(id)).thenReturn(Optional.of(defaultFunds.get(1)));

        fundService.updateFund(id, updatedFund);
        Fund fund = fundService.getFund(1L);
        assertEquals(updatedFund.getName(), fund.getName());
        assertEquals(updatedFund.getEmployeeId(), fund.getEmployeeId());
    }

    @Test
    public void testUpdateFundIdNotMatching(){
        Fund updatedFund = new Fund(1L,"Income Fund", 1L);
        Long id = 2L;

        when(fundRepository.findById(id)).thenReturn(Optional.of(defaultFunds.get(1)));
        assertFalse(fundRepository.findFundByName(updatedFund.getName()).isPresent());

        assertThrows(FundIdNotMatchingException.class,
                () -> fundService.updateFund(id, updatedFund));
    }

    @Test
    public void testUpdateFundNotFound(){
        Fund updatedFund = new Fund(1L,"Income Fund", 1L);
        Long id = 1L;

        assertThrows(FundNotFoundException.class,
                () -> fundService.updateFund(id, updatedFund));
    }


    @Test
    public void testUpdateFundIllegalUpdate(){
        Fund updatedFund = new Fund(1L,null, 1L);
        Long id = 1L;

        assertThrows(IllegalUpdatedFundException.class,
                () -> fundService.updateFund(id, updatedFund));
    }
}
