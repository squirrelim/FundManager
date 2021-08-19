// package com.example.fundmanager.dao;

// import com.example.fundmanager.entity.Security;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
// import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
// import org.springframework.test.context.junit.jupiter.SpringExtension;

// import static org.junit.jupiter.api.Assertions.*;
// import static org.mockito.Mockito.when;
// import static org.mockito.Mockito.verify;

// import java.time.LocalDate;
// import java.util.ArrayList;
// import java.util.List;
// import java.util.Optional;
// import java.util.OptionalInt;

// @ExtendWith(SpringExtension.class)
// @DataJpaTest
// @AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
// public class SecurityRepositoryTests {

//     @Autowired
//     SecurityRepository securityRepository;

//     List<Security> defaultSecurities = List.of(
//             new Security(1L,"IBM"),
//             new Security(2L,"Microsoft")
//     );

//     @BeforeEach
//     public void bootstrapDatabase(){
//         securityRepository.deleteAll();
//         securityRepository.saveAll(defaultSecurities);
//     }

//     @Test
//     public void testSaveReadDeleteAll(){
//         securityRepository.deleteAll();
//         List<Security> securities = securityRepository.findAll();
//         assertEquals(0, securities.size());

//         securityRepository.saveAll(defaultSecurities);
//         securities = securityRepository.findAll();
//         assertEquals(defaultSecurities.size(), securities.size());
//     }

//     @Test
//     public void testFindBySymbolSuccess(){
//         Optional<Security> test1 = securityRepository.findSecurityBySymbol("IBM");
//         assertTrue(test1.isPresent());
//     }

//     @Test
//     public void testFindBySymbolNotFound(){
//         Optional<Security> test2 = securityRepository.findSecurityBySymbol("IBM1");
//         assertFalse(test2.isPresent());
//     }

// }
