package com.example.fundmanager.controller;

import com.example.fundmanager.dao.FundManagerRepository;
import com.example.fundmanager.dao.FundRepository;
import com.example.fundmanager.dao.PositionRepository;
import com.example.fundmanager.dao.SecurityRepository;
import com.example.fundmanager.entity.Fund;
import com.example.fundmanager.entity.Security;
import com.example.fundmanager.exception.FundAlreadyInUseException;
import com.example.fundmanager.exception.FundIdNotMatchingException;
import com.example.fundmanager.exception.FundNotFoundException;
import com.example.fundmanager.exception.IllegalUpdatedFundException;
import com.example.fundmanager.service.FundManagerService;
import com.example.fundmanager.service.FundService;
import com.example.fundmanager.service.PositionService;
import com.example.fundmanager.service.SecurityService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest
public class FundControllerTests {
    @MockBean
    FundManagerController fundManagerController;

    @MockBean
    FundManagerService fundManagerService;

    @MockBean
    FundManagerRepository fundManagerRepository;

    @MockBean
    PositonController positonController;

    @MockBean
    PositionService positionService;

    @MockBean
    PositionRepository positionRepository;

    @MockBean
    SecurityController securityController;

    @MockBean
    SecurityService securityService;

    @MockBean
    SecurityRepository securityRepository;

//    @MockBean
//    FundController fundController;

    @MockBean
    FundService fundService;

    @MockBean
    FundRepository fundRepository;

    @Autowired
    MockMvc mockMvc;

    List<Fund> defaultFunds = List.of(
            new Fund("Olympic Memorial Fund", 1L),
            new Fund("UK Overseas Income Fund", 1L),
            new Fund("North America Growth", 2L),
            new Fund("Global Tech Fund", 2L)
    );

    @Test
    public void testGetFundsSuccess() throws Exception{
        when(fundService.getFunds()).thenReturn(defaultFunds);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/fund")).andExpect(status().isOk());
    }

    @Test
    public void testGetFundSuccess() throws Exception{
        when(fundService.getFund(1L)).thenReturn(defaultFunds.get(0));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/fund/1")).andExpect(status().isOk());
    }

    @Test
    public void testGetFundNotFound() throws Exception{
        when(fundService.getFund(1L)).thenThrow(FundNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/fund/1")).andExpect(status().isNotFound());
    }

    @Test
    public void testAddFundSuccess() throws Exception{
        String json = "{\n" +
                "    \"name\": \"Income Fund\",\n" +
                "    \"employeeId\":1,\n" +
                "    \"positions\": []\n" +
                "}";

        RequestBuilder request = MockMvcRequestBuilders
                .post("/api/fund")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isOk());

        verify(fundService).addFund(any(Fund.class));
    }

    @Test
    public void testAddFundAlreadyInUse() throws Exception{
        doThrow(FundAlreadyInUseException.class).when(fundService).addFund(any(Fund.class));
        String json = "{\n" +
                "    \"name\": \"Olympic Memorial Fund\",\n" +
                "    \"employeeId\": 1,\n" +
                "    \"positions\": []\n" +
                "}";

        RequestBuilder request = MockMvcRequestBuilders
                .post("/api/fund")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isBadRequest());

        verify(fundService).addFund(any(Fund.class));
    }

    @Test
    public void testRemoveFundSuccess() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/fund/1")).andExpect(status().isOk());
        verify(fundService).removeFund(anyLong());
    }

    @Test
    public void testRemoveFundNotFound() throws Exception{
        doThrow(FundNotFoundException.class).when(fundService).removeFund(anyLong());
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/fund/10")).andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateFundSuccess() throws Exception{
        String json = "{\n" +
                "    \"fundId\": 1,\n" +
                "    \"name\": \"UK Fund1\"\n" +
                "}";
        RequestBuilder request = MockMvcRequestBuilders
                .put("/api/fund/1")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isOk());
        verify(fundService).updateFund(anyLong(), any(Fund.class));
    }

    @Test
    public void testUpdateFundNotFound() throws Exception{
        doThrow(FundNotFoundException.class).when(fundService).updateFund(anyLong(), any(Fund.class));

        String json = "{\n" +
                "    \"fundId\": 1,\n" +
                "    \"name\": \"UK Fund1\"\n" +
                "}";
        RequestBuilder request = MockMvcRequestBuilders
                .put("/api/fund/1")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isNotFound());
        verify(fundService).updateFund(anyLong(), any(Fund.class));
    }

    @Test
    public void testUpdateAlreadyInUse() throws Exception{
        doThrow(FundAlreadyInUseException.class).when(fundService).updateFund(anyLong(), any(Fund.class));

        String json = "{\n" +
                "    \"fundId\": 1,\n" +
                "    \"name\": \"UK Fund1\"\n" +
                "}";
        RequestBuilder request = MockMvcRequestBuilders
                .put("/api/fund/1")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isBadRequest());
        verify(fundService).updateFund(anyLong(), any(Fund.class));
    }

    @Test
    public void testUpdateIdNotMatching() throws Exception{
        doThrow(FundIdNotMatchingException.class).when(fundService).updateFund(anyLong(), any(Fund.class));

        String json = "{\n" +
                "    \"fundId\": 1,\n" +
                "    \"name\": \"UK Fund1\"\n" +
                "}";
        RequestBuilder request = MockMvcRequestBuilders
                .put("/api/fund/1")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isBadRequest());
        verify(fundService).updateFund(anyLong(), any(Fund.class));
    }

    @Test
    public void testUpdateIllegalUpdatedFund() throws Exception{
        doThrow(IllegalUpdatedFundException.class).when(fundService).updateFund(anyLong(), any(Fund.class));

        String json = "{\n" +
                "    \"fundId\": 1,\n" +
                "    \"name\": \"UK Fund1\"\n" +
                "}";
        RequestBuilder request = MockMvcRequestBuilders
                .put("/api/fund/1")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isBadRequest());
        verify(fundService).updateFund(anyLong(), any(Fund.class));
    }

}
