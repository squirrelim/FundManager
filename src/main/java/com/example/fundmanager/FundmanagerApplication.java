package com.example.fundmanager;

import com.example.fundmanager.dao.FundManagerRepository;
import com.example.fundmanager.dao.FundRepository;
import com.example.fundmanager.dao.PositionRepository;
import com.example.fundmanager.dao.SecurityRepository;
import com.example.fundmanager.entity.Fund;
import com.example.fundmanager.entity.FundManager;
import com.example.fundmanager.entity.Position;
import com.example.fundmanager.entity.Security;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class FundmanagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(FundmanagerApplication.class, args);
    }

    @Bean
    ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    CommandLineRunner commandLineRunner(FundManagerRepository fundManagerRepository,
                                        FundRepository fundRepository,
                                        PositionRepository positionRepository,
                                        SecurityRepository securityRepository) {
      return args -> {
          List<FundManager> managers = List.of(
                  new FundManager(1L, "Terry", "Jone", new ArrayList<>()),
                  new FundManager(2L, "Mike", "Smith", new ArrayList<>())
          );
          fundManagerRepository.saveAll(managers);

          List<Fund> funds = List.of(
                  new Fund(1L, "Olympic Memorial Fund", fundManagerRepository.getById(1L), new ArrayList<>()),
                  new Fund(2L, "UK Overseas Income Fund", fundManagerRepository.getById(1L), new ArrayList<>()),
                  new Fund(3L, "North America Growth", fundManagerRepository.getById(2L), new ArrayList<>()),
                  new Fund(4L, "Global Tech Fund", fundManagerRepository.getById(2L), new ArrayList<>())
          );
          fundRepository.saveAll(funds);

          List<Position> positions = List.of(
                  new Position(1L, 100L, LocalDate.of(2016, 1, 10), fundRepository.getById(1L)),
                  new Position(1L, 250L, LocalDate.of(2016, 9, 23), fundRepository.getById(1L)),
                  new Position(1L, 200L, LocalDate.of(2016, 8, 14), fundRepository.getById(2L)),
                  new Position(1L, 125L, LocalDate.of(2016, 9, 23), fundRepository.getById(3L)),
                  new Position(1L, 75L, LocalDate.of(2017, 1, 27), fundRepository.getById(4L))
          );
          positionRepository.saveAll(positions);

          List<Security> securities = List.of(
                  new Security(1L, "IBM"),
                  new Security(2L, "Microsoft")
          );
          securityRepository.saveAll(securities);

      };


    }
}
