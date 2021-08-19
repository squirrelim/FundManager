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
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.List;


@ExtendWith(SpringExtension.class)
@WebMvcTest(PositonController.class)
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

    @Test
    public void testAddPosition() throws Exception {
        String json = "{\"positionId\": 8, \"securityId\": 2, \"quantity\": 400, \"datePurchased\": " +
                "\"1970-01-01\", \"fund\": {\"fundId\":1}}";
        RequestBuilder request = MockMvcRequestBuilders
                .post("/api/position")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk());
        verify(positionService).addPosition(any(Position.class));
    }

    @Test
    public void testRemovePositionSuccess() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/position/1")).andExpect(status().isOk());
    }

    @Test
    public void testUpdatePositionSuccess() throws Exception{
        String json = "{\"positionId\": 1, \"securityId\": 1, \"quantity\": 400, \"datePurchased\": " +
                "\"1970-01-01\", \"fundId\":1}";
        RequestBuilder request = MockMvcRequestBuilders
                .put("/api/position/1")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk());
        verify(positionService).updatePosition(any(Long.class),any(Position.class));
    }

}