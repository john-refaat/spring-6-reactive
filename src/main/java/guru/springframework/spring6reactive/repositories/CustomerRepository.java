package guru.springframework.spring6reactive.repositories;

import guru.springframework.spring6reactive.domain.Customer;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

/**
 * @author john
 * @since 18/09/2024
 */
public interface CustomerRepository extends ReactiveCrudRepository<Customer, Integer> {

}
