package guru.springframework.spring6reactive.bootstrap;

import guru.springframework.spring6reactive.domain.Beer;
import guru.springframework.spring6reactive.repositories.BeerRepository;
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
}
