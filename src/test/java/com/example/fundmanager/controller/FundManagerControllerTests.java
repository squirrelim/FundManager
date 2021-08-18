package com.example.fundmanager.controller;

import com.example.fundmanager.dao.FundManagerRepository;
import com.example.fundmanager.dao.FundRepository;
import com.example.fundmanager.dao.PositionRepository;
import com.example.fundmanager.dao.SecurityRepository;
import com.example.fundmanager.entity.FundManager;
import com.example.fundmanager.exception.ManagerNotFoundException;
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

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(FundManagerController.class)
public class FundManagerControllerTests {
//    @MockBean
//    FundManagerController fundManagerController;

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

    @MockBean
    FundController fundController;

    @MockBean
    FundService fundService;

    @MockBean
    FundRepository fundRepository;

    @Autowired
    MockMvc mockMvc;

    List<FundManager> defaultManagers = List.of(
            new FundManager(1L, "Terry", "Jone", new ArrayList<>()),
            new FundManager(2L, "Mike", "Smith", new ArrayList<>())
    );

    @Test
    public void testGetManagersSuccess() throws Exception{
        when(fundManagerService.getManagers()).thenReturn(defaultManagers);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/manager")).andExpect(status().isOk());
    }

    @Test
    public void testGetManagerSuccess() throws Exception{
        when(fundManagerService.getManager(1L)).thenReturn(defaultManagers.get(0));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/manager/1")).andExpect(status().isOk());
    }

    @Test
    public void testGetManagerNotFound() throws Exception{
        when(fundManagerService.getManager(1L)).thenThrow(ManagerNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/manager/1")).andExpect(status().isNotFound());
    }
}
