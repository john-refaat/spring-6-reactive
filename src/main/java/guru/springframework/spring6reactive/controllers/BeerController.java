package guru.springframework.spring6reactive.controllers;

import guru.springframework.spring6reactive.model.BeerDTO;
import guru.springframework.spring6reactive.services.BeerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author john
 * @since 14/09/2024
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(BeerController.BEER_PATH)
public class BeerController {

    public static final String BEER_PATH = "/api/v2/beer";
    public static final String LOCATION = "Location";

    private final BeerService beerService;

    @PostMapping({"", "/"})
    public Mono<ResponseEntity<Void>> createBeer(@Valid @RequestBody BeerDTO beerDTO) {
        log.info("Create New Beer: {}", beerDTO);
        return beerService.createBeer(beerDTO)
                .map(savedBeer -> ResponseEntity.created(
                        UriComponentsBuilder.fromPath(BEER_PATH+"/"+savedBeer.getId())
                                .build().toUri())
                        .build());
    }

    @PutMapping("/{beerId}")
    public Mono<ResponseEntity<Void>> updateBeer(@PathVariable Integer beerId, @Validated @RequestBody BeerDTO beerDTO) {
        log.info("Update Beer: {}, with Id: {}", beerDTO, beerId);
        return beerService.updateBeer(beerId, beerDTO)
               .map(updatedBeer -> ResponseEntity.ok()
                       .header(LOCATION, BEER_PATH+"/"+updatedBeer.getId())
                       .build());
    }

    @GetMapping("/{beerId}")
    public Mono<BeerDTO> findBeerById(@PathVariable Integer beerId) {
        log.info("Find Beer By Id: {}", beerId);
        return beerService.findBeerById(beerId);
    }

    @PatchMapping("/{beerId}")
    public Mono<ResponseEntity<Void>> patchBeerById(@PathVariable Integer beerId, @Validated @RequestBody BeerDTO beer) {
        log.info("Patch: {}, with Id: {}", beer, beerId);
        return beerService.patchBeer(beerId, beer)
                .map(patchedBeer -> ResponseEntity.ok()
                        .header(LOCATION, BEER_PATH+"/"+patchedBeer.getId())
                        .build());
    }

    @DeleteMapping("/{beerId}")
    public Mono<ResponseEntity<Void>> deleteBeer(@PathVariable Integer beerId) {
        log.info("Delete Beer By Id: {}", beerId);
        return beerService.deleteBeer(beerId)
               .map(deletedBeer -> ResponseEntity.noContent().build());
    }

    @GetMapping({"", "/"})
    public Flux<BeerDTO> listBeers() {
        log.info("List All Beers");
        return beerService.listBeers();
    }

}
