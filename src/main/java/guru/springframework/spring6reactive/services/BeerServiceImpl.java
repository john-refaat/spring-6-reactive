package guru.springframework.spring6reactive.services;

import guru.springframework.spring6reactive.mapper.BeerMapper;
import guru.springframework.spring6reactive.model.BeerDTO;
import guru.springframework.spring6reactive.repositories.BeerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author john
 * @since 14/09/2024
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class BeerServiceImpl implements BeerService {

    private final BeerMapper beerMapper;
    private final BeerRepository beerRepository;

    @Override
    public Mono<BeerDTO> findBeerById(Integer beerId) {
        return beerRepository.findById(beerId).map(beerMapper::beerToBeerDTO);
    }

    @Override
    public Mono<BeerDTO> createBeer(BeerDTO beerDTO) {
        return beerRepository.save(beerMapper.beerDTOToBeer(beerDTO)).map(beerMapper::beerToBeerDTO);
    }

    @Override
    public Mono<BeerDTO> updateBeer(Integer beerId, BeerDTO beerDTO) {
        return beerRepository.findById(beerId).map(foundBeer -> {
            foundBeer.setBeerName(beerDTO.getBeerName());
            foundBeer.setBeerStyle(beerDTO.getBeerStyle());
            foundBeer.setUpc(beerDTO.getUpc());
            foundBeer.setQuantityOnHand(beerDTO.getQuantityOnHand());
            foundBeer.setPrice(beerDTO.getPrice());
            return foundBeer;
        }).flatMap(beerRepository::save).map(beerMapper::beerToBeerDTO);
    }

    @Override
    public Mono<BeerDTO> patchBeer(Integer beerId, BeerDTO beerDTO) {
        return beerRepository.findById(beerId).map(foundBeer -> {
            foundBeer.setBeerName(beerDTO.getBeerName() == null? foundBeer.getBeerName() : beerDTO.getBeerName());
            foundBeer.setBeerStyle(beerDTO.getBeerStyle() == null? foundBeer.getBeerStyle() : beerDTO.getBeerStyle());
            foundBeer.setUpc(beerDTO.getUpc() == null? foundBeer.getUpc() : beerDTO.getUpc());
            foundBeer.setQuantityOnHand(beerDTO.getQuantityOnHand() == null? foundBeer.getQuantityOnHand() : beerDTO.getQuantityOnHand());
            foundBeer.setPrice(beerDTO.getPrice() == null? foundBeer.getPrice() : beerDTO.getPrice());
            return foundBeer;
        }).flatMap(beerRepository::save).map(beerMapper::beerToBeerDTO);
    }

    @Override
    public Mono<Void> deleteBeer(Integer beerId) {
        return beerRepository.deleteById(beerId);
    }

    @Override
    public Flux<BeerDTO> listBeers() {
        return beerRepository.findAll().map(beerMapper::beerToBeerDTO);
    }
}
