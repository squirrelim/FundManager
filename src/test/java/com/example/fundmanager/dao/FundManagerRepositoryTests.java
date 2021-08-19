// package com.example.fundmanager.dao;

// import com.example.fundmanager.dao.FundManagerRepository;
// import com.example.fundmanager.entity.FundManager;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
// import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
// import org.springframework.test.context.junit.jupiter.SpringExtension;
// import static org.junit.jupiter.api.Assertions.*;

// import java.util.ArrayList;
// import java.util.List;
// import java.util.Optional;

// @ExtendWith(SpringExtension.class)
// @DataJpaTest
// @AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
// public class FundManagerRepositoryTests {

//     @Autowired
//     FundManagerRepository fundManagerRepository;

//     List<FundManager> defaultMangers = List.of(
//             new FundManager(1L, "Terry", "Jone", new ArrayList<>()),
//             new FundManager(2L, "Mike", "Smith", new ArrayList<>())
//     );

//     @BeforeEach
//     public void bootstrapDatabase() {
//         fundManagerRepository.deleteAll();
//         fundManagerRepository.saveAll(defaultMangers);
//     }

//     @Test
//     public void testSaveReadDeleteAll() {
//         fundManagerRepository.deleteAll();
//         List<FundManager> managers = fundManagerRepository.findAll();
//         assertEquals(0, managers.size());

//         fundManagerRepository.saveAll(defaultMangers);
//         assertNotEquals(defaultMangers.size(), managers.size());
//     }

//     @Test
//     public void testFindByName() {
//         Optional<FundManager> managerByName = fundManagerRepository.findFundManagerByName("Terry", "Jone");
//         assertTrue(managerByName.isPresent());
//     }

// }
