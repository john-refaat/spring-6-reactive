package guru.springframework.spring6reactive.services;

import guru.springframework.spring6reactive.model.BeerDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author john
 * @since 14/09/2024
 */
public interface BeerService {
    Mono<BeerDTO> findBeerById(Integer beerId);
    Mono<BeerDTO> createBeer(BeerDTO beerDTO);
    Mono<BeerDTO> updateBeer(Integer beerId, BeerDTO beerDTO);
    Mono<BeerDTO> patchBeer(Integer beerId, BeerDTO beerDTO);
    Mono<Void> deleteBeer(Integer beerId);
    Flux<BeerDTO> listBeers();
}
