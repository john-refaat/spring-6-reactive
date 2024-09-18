package guru.springframework.spring6reactive.repositories;

import guru.springframework.spring6reactive.domain.Beer;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

/**
 * @author john
 * @since 14/09/2024
 */
public interface BeerRepository extends ReactiveCrudRepository<Beer, Integer> {

}
