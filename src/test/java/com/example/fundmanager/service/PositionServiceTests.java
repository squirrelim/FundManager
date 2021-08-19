package com.example.fundmanager.service;

import com.example.fundmanager.dao.PositionRepository;
import com.example.fundmanager.entity.Fund;
import com.example.fundmanager.entity.Position;
import com.example.fundmanager.exception.PositionAlreadyInUseException;
import com.example.fundmanager.exception.PositionIdNotMatchingException;
import com.example.fundmanager.exception.PositionNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PositionServiceTests {
    @Mock
    private PositionRepository positionRepository;

    @InjectMocks
    private PositionService positionService;

    List<Position> defaultPositions = List.of(
            new Position(1L, 100L, LocalDate.of(2016, 1, 10), 1L),
            new Position(1L, 250L, LocalDate.of(2016, 9, 23), 1L),
            new Position(1L, 200L, LocalDate.of(2016, 8, 14), 2L),
            new Position(1L, 125L, LocalDate.of(2016, 9, 23), 3L),
            new Position(1L, 75L, LocalDate.of(2017, 1, 27), 4L)
    );

    @Test
    public void testGetPostions(){
        when(positionRepository.findAll()).thenReturn(defaultPositions);

        List<Position> positions = positionService.getPositions();
        assertNotNull(positions);
        assertTrue(positions.size() > 0);
    }

    @Test
    public void testGetPositionSuccess(){
        when(positionRepository.findById(1L))
                .thenReturn(Optional.of(defaultPositions.get(0)));

        Position position = positionService.getPosition(1l);
        assertNotNull(position);
    }

    @Test
    public void testGetPositionNotFound(){
        assertThrows(PositionNotFoundException.class,
                () -> positionService.getPosition(0L));
    }

    @Test
    public void testAddNewUser(){
        Position position =
                new Position(2L, 400L, LocalDate.of(2016, 3, 10), 2L);
        positionService.addPosition(position);
        verify(positionRepository).save(position);
    }

    @Test
    public void testUpdatePositionSuccess(){
        when(positionRepository.findById(1L))
                .thenReturn(Optional.of(defaultPositions.get(0)));
        Position updateposition =
                new Position(1L,1L, 400L, LocalDate.of(2016, 1, 10), 2L);

        positionService.updatePosition(1L,updateposition);

        Position position = positionService.getPosition(1L);

        assertEquals(updateposition.getQuantity(), position.getQuantity());
        assertEquals(updateposition.getDatePurchased(), position.getDatePurchased());
    }

    @Test
    public void testUpdatePositionNotFound(){
        Position updateposition =
                new Position(0l,2L, 400L, LocalDate.of(2016, 3, 10), 1L);
        assertThrows(PositionNotFoundException.class,
                () -> positionService.updatePosition(0l,updateposition));
    }

    @Test
    public void testUpdateUserPositionIdNotMatching(){
        Position updateposition =
                new Position(2l,2L, 400L, LocalDate.of(2016, 3, 10), 2L);

        when(positionRepository.findById(1L))
                .thenReturn(Optional.of(defaultPositions.get(0)));
        assertThrows(PositionIdNotMatchingException.class,
                () -> positionService.updatePosition(1l,updateposition));
    }

    @Test
    public void testUpdateUserPositionAlreadyInUse(){
        Position updatedPosition =
                new Position(1L,1L, 100L, LocalDate.of(2016, 1, 10), 1L);

        when(positionRepository.findById(1L))
                .thenReturn(Optional.of(defaultPositions.get(0)));
        when(positionRepository.findPositions(
                updatedPosition.getSecurityId(),
                updatedPosition.getQuantity(),
                updatedPosition.getDatePurchased()))
                .thenReturn(Optional.of(defaultPositions.get(0)));
        assertThrows(PositionAlreadyInUseException.class,
                () -> positionService.updatePosition(1l,updatedPosition));
    }
}

