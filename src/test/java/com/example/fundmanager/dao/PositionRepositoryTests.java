package com.example.fundmanager.dao;

import com.example.fundmanager.entity.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@ExtendWith(SpringExtension.class)  //use the spring test framework like @MockBean
//Using this annotation will disable full auto-configuration and instead apply only configuration relevant to JPA tests.
@DataJpaTest
//Annotation that can be applied to a test class to configure a test database to use instead of any application defined
// or auto-configured DataSource.
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PositionRepositoryTests {
    @Autowired
    PositionRepository positionRepository;

    //insert data into the table for test
    List<Position> defaultPositions = List.of(
            new Position(1L, 100L, LocalDate.of(2016, 1, 10), 1L),
            new Position(1L, 250L, LocalDate.of(2016, 9, 23), 1L),
            new Position(1L, 200L, LocalDate.of(2016, 8, 14), 2L),
            new Position(1L, 125L, LocalDate.of(2016, 9, 23), 3L),
            new Position(1L, 75L, LocalDate.of(2017, 1, 27), 4L)
    );

    @BeforeEach
    public void bootstrapDatabase(){
        positionRepository.deleteAll();
        positionRepository.saveAll(defaultPositions);
    }

    @Test
    public void testFindPositionsByPositionIdSuccess(){
        Optional<Position> position =  positionRepository.findPositionsByPositionId(1l);
        assertTrue(position.isPresent());
    }

    @Test
    public void testFindPositionsByPositionIdNotFound(){
        Optional<Position> position =  positionRepository.findPositionsByPositionId(12l);
        assertFalse(position.isPresent());
    }

    @Test
    public void testFindPositions(){
        Optional<Position> position =  positionRepository.findPositionsByPositionId(1l);
        assertTrue(position.isPresent());
    }

    @Test
    public void testfindPositionsSuccess(){
        Optional<Position> positions =
                positionRepository.findPositions(1L,1L, LocalDate.of(2016, 1, 10));
        assertTrue(positions.isPresent());
    }

    @Test
    public void testfindPositionsFalse(){
        Optional<Position> positions =
                positionRepository.findPositions(2L,1L, LocalDate.of(2016, 1, 10));
        assertFalse(positions.isPresent());
    }
}

