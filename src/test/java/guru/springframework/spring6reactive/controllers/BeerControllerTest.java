package guru.springframework.spring6reactive.controllers;

import guru.springframework.spring6reactive.model.BeerDTO;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.mockJwt;

/**
 * @author john
 * @since 21/09/2024
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@AutoConfigureWebTestClient
class BeerControllerTest {

    private static final String BASE_PATH = "/api/v2/beer";
    private BeerDTO beer;

    @Autowired
    WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        webTestClient = webTestClient.mutateWith(mockJwt());
        beer = BeerDTO.builder()
                .beerName("New Beer")
                .beerStyle("New Style")
                .upc("1234567890123")
                .quantityOnHand(100)
                .price(new BigDecimal("9.99"))
                .build();
    }

    @Test
    void createBeer() {
        EntityExchangeResult<BeerDTO> result = webTestClient.post()
                .uri(BASE_PATH).body(Mono.just(beer), BeerDTO.class)
                .header("Content-Type", "application/json")
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().exists("Location")
                .expectBody(BeerDTO.class).returnResult();
        System.out.println(result);
    }

    @Test
    void createBeerBlankName() {
        beer.setBeerName("");
        webTestClient.post().uri(BASE_PATH).body(Mono.just(beer), BeerDTO.class)
               .header("Content-Type", "application/json")
               .exchange()
               .expectStatus().isBadRequest();
    }

    @Test
    void createBeerBlankStyle() {
        beer.setBeerStyle("");
        webTestClient.post().uri(BASE_PATH).body(Mono.just(beer), BeerDTO.class)
               .header("Content-Type", "application/json")
               .exchange()
               .expectStatus().isBadRequest();
    }
    @Test
    void createBeerInvalidPrice() {
        beer.setPrice(new BigDecimal("-9.99"));
        webTestClient.post().uri(BASE_PATH).body(Mono.just(beer), BeerDTO.class)
               .header("Content-Type", "application/json")
               .exchange()
               .expectStatus().isBadRequest();
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

    @Test
    void updateBeerNotFound() {
        webTestClient.put().uri(BASE_PATH+"/{beerId}", 1000).body(Mono.just(beer), BeerDTO.class)
               .header("Content-Type", "application/json")
               .exchange()
               .expectStatus().isNotFound();
    }

    @Order(1)
    @Test
    void findBeerById() {
        webTestClient.get().uri(BASE_PATH+"/{beerId}", 1).exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("content-type", "application/json")
                .expectBody(BeerDTO.class);
    }

    @Test
    void findBeerByIdNotFound() {
        webTestClient.get().uri(BASE_PATH+"/{beerId}", 1000).exchange()
               .expectStatus().isNotFound();
    }

    @Order(4)
    @Test
    void patchBeerById() {
        webTestClient.patch().uri(BASE_PATH+"/{beerId}", 1)
                .body(Mono.just(BeerDTO.builder().beerName("Stella").build()), BeerDTO.class)
                .header("Content-Type", "application/json")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().exists("Location")
                .expectBody(BeerDTO.class);
    }

    @Test
    void patchBeerByIdNotFound() {
        webTestClient.patch().uri(BASE_PATH+"/{beerId}", 1000).body(Mono.just(beer), BeerDTO.class)
               .header("Content-Type", "application/json")
               .exchange()
               .expectStatus().isNotFound();
    }

    @Test
    void deleteBeer() {
        webTestClient.delete().uri(BASE_PATH+"/{beerId}", 1).exchange()
               .expectStatus().isNoContent();
    }

    @Test
    void deleteBeerNotFound() {
        webTestClient.delete().uri(BASE_PATH+"/{beerId}", 1000).exchange()
               .expectStatus().isNotFound();
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