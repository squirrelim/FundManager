package com.example.fundmanager.controller;

import com.example.fundmanager.dao.FundManagerRepository;
import com.example.fundmanager.dao.FundRepository;
import com.example.fundmanager.dao.PositionRepository;
import com.example.fundmanager.dao.SecurityRepository;
import com.example.fundmanager.entity.Position;
import com.example.fundmanager.entity.Security;
import com.example.fundmanager.exception.PositionNotFoundException;
import com.example.fundmanager.exception.SecurityNotFoundException;
import com.example.fundmanager.service.FundManagerService;
import com.example.fundmanager.service.FundService;
import com.example.fundmanager.service.PositionService;
import com.example.fundmanager.service.SecurityService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest
public class SecurityControllerTests {
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

//    @MockBean
//    SecurityController securityController;

    @MockBean
    SecurityService securityService;

    @MockBean
    SecurityRepository securityRepository;

    @MockBean
    FundController fundController;

    @MockBean
    FundService fundService;

    @MockBean
    FundRepository fundRepository;

    @Autowired
    MockMvc mockMvc;

    List<Security> defaultSecurities = List.of(
            new Security(1L, "IBM"),
            new Security(2L, "Microsoft")
    );

    @Test
    public void testGetAllSecuritiesSuccess() throws Exception{
        when(securityService.getSecurities()).thenReturn(defaultSecurities);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/security")).andExpect(status().isOk());
    }

    @Test
    public void testGetSecuritySuccess() throws Exception{
        when(securityService.getSecurity(1L)).thenReturn(defaultSecurities.get(0));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/security/1")).andExpect(status().isOk());
    }

    @Test
    public void testGetSecurityNotFound() throws Exception{
        when(securityService.getSecurity(1L)).thenThrow(SecurityNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/security/1")).andExpect(status().isNotFound());
    }


}
