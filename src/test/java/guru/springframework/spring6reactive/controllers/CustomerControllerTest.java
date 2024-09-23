package guru.springframework.spring6reactive.controllers;

import guru.springframework.spring6reactive.domain.Customer;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * @author john
 * @since 23/09/2024
 */
@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@AutoConfigureWebTestClient
class CustomerControllerTest {


    private static final String BASE_PATH = "/api/v2/customer";
    private static final Customer customer = Customer.builder()
            .firstName("John").lastName("Stefanos").build();

    @Autowired
    CustomerController customerController;

    @Autowired
    WebTestClient webTestClient;


    @Order(1)
    @Test
    void getAllCustomers() {
        webTestClient.get().uri(BASE_PATH).header("Accept", "application/json")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Customer.class).hasSize(3);
    }

    @Order(2)
    @Test
    void getCustomerById() {
        webTestClient.get().uri(BASE_PATH + "/1").header("Accept", "application/json")
                .exchange()
                .expectStatus().isOk()
                .expectBody().jsonPath("$.id").isEqualTo(1)
                .jsonPath("$.firstName").isEqualTo("John")
                .jsonPath("$.lastName").isEqualTo("Doe");
    }

    @Order(3)
    @Test
    void createCustomer() {
        webTestClient.post().uri(BASE_PATH).header("Content-type", "application/json")
               .bodyValue(customer)
               .exchange()
               .expectStatus().isCreated()
               .expectBody().jsonPath("$.id").exists()
               .jsonPath("$.firstName").isEqualTo("John")
               .jsonPath("$.lastName").isEqualTo("Stefanos")
                .consumeWith(result -> log.info("Response: {}", result));
    }

    @Order(4)
    @Test
    void updateCustomer() {
        webTestClient.put().uri(BASE_PATH + "/1").header("Content-type", "application/json")
               .bodyValue(Customer.builder().id(1).firstName("Bob").lastName("Doe").build())
               .exchange()
               .expectStatus().isOk()
                .expectHeader().exists("Location")
                .expectBody().consumeWith(result -> log.info("Response {}", result));
    }

    @Order(5)
    @Test
    void updateCustomerNotFound() {
        webTestClient.put().uri(BASE_PATH + "/100").header("Content-type", "application/json")
               .bodyValue(Customer.builder().id(100).firstName("Bob").lastName("Doe").build())
               .exchange()
               .expectStatus().isNotFound();
    }

    @Order(6)
    @Test
    void patchCustomer() {
        webTestClient.patch().uri(BASE_PATH + "/1").header("Content-type", "application/json-patch+json")
               .bodyValue("{\"firstName\": \"Alice\"}")
               .exchange()
               .expectStatus().isOk()
               .expectBody().consumeWith(result -> log.info("Response: {}", result));
    }

    @Test
    void deleteCustomer() {
        webTestClient.delete().uri(BASE_PATH + "/3").exchange()
               .expectStatus().isNoContent().expectBody().consumeWith(result ->log.info("Result: {}", result));
    }

    @Test
    void deleteCustomerNotFound() {
        webTestClient.delete().uri(BASE_PATH + "/100").exchange()
               .expectStatus().isNotFound().expectBody().consumeWith(result ->log.info("Result {}", result));
    }
}