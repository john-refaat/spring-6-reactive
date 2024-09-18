package guru.springframework.spring6reactive.services;

import guru.springframework.spring6reactive.model.CustomerDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author john
 * @since 18/09/2024
 */
public interface CustomerService {
    Mono<CustomerDTO> findCustomerById(Integer id);
    Mono<CustomerDTO> createCustomer(CustomerDTO customerDTO);
    Mono<CustomerDTO> updateCustomer(Integer id, CustomerDTO customerDTO);
    Mono<CustomerDTO> patchCustomer(Integer id, CustomerDTO customerDTO);
    Mono<Void> deleteCustomer(Integer id);
    Flux<CustomerDTO> listCustomers();
}
