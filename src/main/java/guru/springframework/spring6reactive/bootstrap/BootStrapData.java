package guru.springframework.spring6reactive.bootstrap;

import guru.springframework.spring6reactive.domain.Beer;
import guru.springframework.spring6reactive.domain.Customer;
import guru.springframework.spring6reactive.repositories.BeerRepository;
import guru.springframework.spring6reactive.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * @author john
 * @since 14/09/2024
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class BootStrapData implements CommandLineRunner {

    private final BeerRepository beerRepository;
    private final CustomerRepository customerRepository;

    @Override
    public void run(String... args) throws Exception {
        beerRepository.count().subscribe(count -> {
            if (count == 0) {
                loadBeers();
            } else {
               log.info("Beers already loaded");
            }
        });
        beerRepository.count().subscribe(count -> log.info("Loaded Beers: {}", count));

        customerRepository.count().subscribe(count -> {
            if (count == 0) {
                loadCustomers();
            } else {
                log.info("Customers already loaded");
            }
        });
        customerRepository.count().subscribe(count -> log.info("Loaded Customers: {}", count));
    }

    private void loadBeers(){
        Beer beer1 = Beer.builder()
                .beerName("Galaxy Cat")
                .beerStyle("PALE_ALE")
                .upc("12356")
                .price(BigDecimal.valueOf(12.5))
                .quantityOnHand(122)
                .build();

        Beer beer2 = Beer.builder()
                .beerName("Crank")
                .beerStyle("Wheat")
                .upc("12356222")
                .price(BigDecimal.valueOf(11.80))
                .quantityOnHand(392)
                .build();
        Beer beer3 = Beer.builder()
                .beerName("Sunshine City")
                .beerStyle("IPA")
                .upc("12356")
                .price(BigDecimal.valueOf(8.5))
                .quantityOnHand(144)
                .build();

        beerRepository.save(beer1).subscribe();
        beerRepository.save(beer2).subscribe();
        beerRepository.save(beer3).subscribe();
    }

    private void loadCustomers() {
        // Add Customer Data here

        Customer customer1 = Customer.builder()
               .firstName("John")
               .lastName("Doe")
               .email("john@example.com")
               .build();

        Customer customer2 = Customer.builder()
               .firstName("Jane")
               .lastName("Smith")
               .email("jane@example.com")
               .build();

        Customer customer3 = Customer.builder()
               .firstName("Bob")
               .lastName("Johnson")
               .email("bob@example.com")
               .build();

        // Save Customers to the database using the customerRepository's save() method.
        // For example: customerRepository.save(customer1).subscribe();
        //... and so on for the other customers as well.

        customerRepository.save(customer1).subscribe();
        customerRepository.save(customer2).subscribe();
        customerRepository.save(customer3).subscribe();
    }

}
