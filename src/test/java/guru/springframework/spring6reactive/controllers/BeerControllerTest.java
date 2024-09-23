package guru.springframework.spring6reactive.controllers;

import guru.springframework.spring6reactive.model.BeerDTO;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

/**
 * @author john
 * @since 21/09/2024
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@AutoConfigureWebTestClient
class BeerControllerTest {

    private static final String BASE_PATH = "/api/v2/beer";
    private static final BeerDTO beer = BeerDTO.builder()
            .beerName("New Beer")
            .beerStyle("New Style")
            .upc("1234567890123")
            .quantityOnHand(100)
            .price(new BigDecimal("9.99"))
            .build();

    @Autowired
    WebTestClient webTestClient;

    @Test
    void createBeer() {
        EntityExchangeResult<BeerDTO> result = webTestClient.post().uri(BASE_PATH).body(Mono.just(beer), BeerDTO.class)
                .header("Content-Type", "application/json")
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().exists("Location")
                .expectBody(BeerDTO.class).returnResult();
        System.out.println(result);
    }

    @Order(3)
    @Test
    void updateBeer() {
        webTestClient.put().uri(BASE_PATH+"/{beerId}", 1).body(Mono.just(beer), BeerDTO.class)
                .header("Content-Type", "application/json")
                .exchange()
                .expectStatus().isOk()
                .expectBody(BeerDTO.class);
    }

    @Order(1)
    @Test
    void findBeerById() {
        webTestClient.get().uri(BASE_PATH+"/{beerId}", 1).exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("content-type", "application/json")
                .expectBody(BeerDTO.class);
    }

    @Order(4)
    @Test
    void patchBeerById() {
        webTestClient.patch().uri(BASE_PATH+"/{beerId}", 1).body(Mono.just(beer), BeerDTO.class)
                .header("Content-Type", "application/json")
                .exchange()
                .expectHeader().exists("Location")
                .expectStatus().isOk()
                .expectBody(BeerDTO.class);
    }

    @Test
    void deleteBeer() {
        webTestClient.delete().uri(BASE_PATH+"/{beerId}", 1).exchange()
               .expectStatus().isNoContent();
    }

    @Order(2)
    @Test
    void listBeers() {
        webTestClient.get().uri(BASE_PATH).exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("content-type", "application/json")
                .expectBody().jsonPath("$.size()").isEqualTo(3);
    }
}