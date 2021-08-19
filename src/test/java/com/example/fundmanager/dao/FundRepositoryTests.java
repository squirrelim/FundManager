// package com.example.fundmanager.dao;

// import com.example.fundmanager.entity.Fund;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
// import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
// import org.springframework.test.context.junit.jupiter.SpringExtension;

// import java.util.List;
// import java.util.Optional;

// import static org.junit.jupiter.api.Assertions.*;

// @ExtendWith(SpringExtension.class)
// @DataJpaTest
// @AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
// public class FundRepositoryTests {
//     @Autowired
//     FundRepository fundRepository;

//     List<Fund> defaultFunds = List.of(
//             new Fund("Olympic Memorial Fund", 1L),
//             new Fund("UK Overseas Income Fund", 1L),
//             new Fund("North America Growth", 2L),
//             new Fund("Global Tech Fund", 2L)
//     );

//     @BeforeEach
//     public void bootstrapDatabase(){
//         fundRepository.deleteAll();
//         fundRepository.saveAll(defaultFunds);
//     }

//     @Test
//     public void testSaveReadDeleteAll(){
//         fundRepository.deleteAll();
//         List<Fund> funds = fundRepository.findAll();
//         assertEquals(0, funds.size());

//         fundRepository.saveAll(defaultFunds);
//         funds = fundRepository.findAll();
//         assertEquals(defaultFunds.size(), funds.size());
//     }

//     @Test
//     public void testFindByNameSuccess(){
//         Optional<Fund> fund = fundRepository.findFundByName("Olympic Memorial Fund");
//         assertTrue(fund.isPresent());
//     }

//     @Test
//     public void testFindByNameNotFound(){
//         Optional<Fund> fund = fundRepository.findFundByName("Memorial Fund");
//         assertFalse(fund.isPresent());
//     }
// }
