package guru.springframework.spring6reactive.services;

import guru.springframework.spring6reactive.mapper.CustomerMapper;
import guru.springframework.spring6reactive.model.CustomerDTO;
import guru.springframework.spring6reactive.model.CustomerPatchDTO;
import guru.springframework.spring6reactive.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author john
 * @since 18/09/2024
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public Mono<CustomerDTO> findCustomerById(Integer id) {
        return customerRepository.findById(id).map(customerMapper::customerToCustomerDTO);
    }

    @Override
    public Mono<CustomerDTO> createCustomer(CustomerDTO customerDTO) {
        return customerRepository.save(customerMapper.customerDTOToCustomer(customerDTO))
                .map(customerMapper::customerToCustomerDTO);
    }

    @Override
    public Mono<CustomerDTO> updateCustomer(Integer id, CustomerDTO customerDTO) {
        return customerRepository.findById(id).map(foundCustomer -> {
            foundCustomer.setFirstName(customerDTO.getFirstName());
            foundCustomer.setLastName(customerDTO.getLastName());
            foundCustomer.setEmail(customerDTO.getEmail());
            return foundCustomer;
        }).flatMap(customerRepository::save).map(customerMapper::customerToCustomerDTO);
    }

    @Override
    public Mono<CustomerDTO> patchCustomer(Integer id, CustomerPatchDTO customerDTO) {
        return customerRepository.findById(id)
                .map(foundCustomer -> {
            foundCustomer.setFirstName(StringUtils.hasText(customerDTO.getFirstName())? customerDTO.getFirstName() : foundCustomer.getFirstName());
            foundCustomer.setLastName(StringUtils.hasText(customerDTO.getLastName())? customerDTO.getLastName() : foundCustomer.getLastName());
            foundCustomer.setEmail(StringUtils.hasText(customerDTO.getEmail())? customerDTO.getEmail() : foundCustomer.getEmail());
            return foundCustomer;
        }).flatMap(customerRepository::save).map(customerMapper::customerToCustomerDTO);
    }

    @Override
    public Mono<Void> deleteCustomer(Integer id) {
        return customerRepository.deleteById(id);
    }

    @Override
    public Flux<CustomerDTO> listCustomers() {
        return customerRepository.findAll().map(customerDTO -> {
            log.info("customer {}", customerDTO);
            return customerDTO;
        }).map(customerMapper::customerToCustomerDTO).map(customerDTO -> {
            log.info("customerDTO {}", customerDTO);
            return customerDTO;
        });
    }
}
