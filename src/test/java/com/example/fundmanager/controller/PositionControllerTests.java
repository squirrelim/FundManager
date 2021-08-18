package com.example.fundmanager.controller;

import com.example.fundmanager.dao.FundManagerRepository;
import com.example.fundmanager.dao.FundRepository;
import com.example.fundmanager.dao.PositionRepository;
import com.example.fundmanager.dao.SecurityRepository;
import com.example.fundmanager.entity.Fund;
import com.example.fundmanager.entity.Position;
import com.example.fundmanager.exception.FundNotFoundException;
import com.example.fundmanager.exception.PositionNotFoundException;
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
public class PositionControllerTests {
    @MockBean
    FundManagerController fundManagerController;

    @MockBean
    FundManagerService fundManagerService;

    @MockBean
    FundManagerRepository fundManagerRepository;

//    @MockBean
//    PositonController positonController;

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

    List<Position> defaultPositions = List.of(
            new Position(1L, 100L, LocalDate.of(2016, 1, 10), 1L),
            new Position(1L, 250L, LocalDate.of(2016, 9, 23), 1L),
            new Position(1L, 200L, LocalDate.of(2016, 8, 14), 2L),
            new Position(1L, 125L, LocalDate.of(2016, 9, 23), 3L),
            new Position(1L, 75L, LocalDate.of(2017, 1, 27), 4L)
    );

    @Test
    public void testGetAllPositionsSuccess() throws Exception{
        when(positionService.getPositions()).thenReturn(defaultPositions);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/position")).andExpect(status().isOk());
    }

    @Test
    public void testGetPositionSuccess() throws Exception{
        when(positionService.getPosition(1L)).thenReturn(defaultPositions.get(0));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/position/1")).andExpect(status().isOk());
    }

    @Test
    public void testGetPositionNotFound() throws Exception{
        when(positionService.getPosition(1L)).thenThrow(PositionNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/position/1")).andExpect(status().isNotFound());
    }


}
