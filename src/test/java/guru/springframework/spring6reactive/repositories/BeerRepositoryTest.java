package guru.springframework.spring6reactive.repositories;

import guru.springframework.spring6reactive.config.DatabaseConfig;
import guru.springframework.spring6reactive.domain.Beer;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author john
 * @since 14/09/2024
 */
@Slf4j
@DataR2dbcTest
@Import(DatabaseConfig.class)
class BeerRepositoryTest {

    private static final Beer beer = Beer.builder()
            .beerName("New Beer")
            .beerStyle("New Style")
            .upc("1234567890123")
            .quantityOnHand(100)
            .price(new BigDecimal("9.99"))
            .build();


    @Autowired
    BeerRepository beerRepository;

    @Test
    void saveNewBeer() {
        // Given

        // When
        beerRepository.save(beer).subscribe(savedBeer -> {
            // Then
            log.info("Saved Beer: {}", savedBeer);
            assertNotNull(savedBeer.getId());
            assertNotNull(savedBeer);
            assertNotNull(savedBeer.getCreatedDate());
            assertNotNull(savedBeer.getLastModifiedDate());
            assertEquals("New Beer", savedBeer.getBeerName());
            assertEquals("New Style", savedBeer.getBeerStyle());
        });


    }


}