package guru.springframework.spring6reactive.controllers;

import guru.springframework.spring6reactive.model.CustomerDTO;
import guru.springframework.spring6reactive.services.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author john
 * @since 18/09/2024
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(CustomerController.PATH)
public class CustomerController {
    public static final String PATH = "/api/v2/customer";
    public static final String LOCATION = "Location";
    public static final String CUSTOMER_ID = "/{customerId}";

    private final CustomerService customerService;

    @GetMapping({"", "/"})
    public Flux<CustomerDTO> getAllCustomers() {
        log.info("Returning all customers");
        return customerService.listCustomers();
    }

    @GetMapping(CUSTOMER_ID)
    public Mono<CustomerDTO> getCustomerById(@PathVariable Integer customerId) {
        log.info("Returning customer with Id: {}", customerId);
        return customerService.findCustomerById(customerId);
    }

    @PostMapping({"", "/"})
    public Mono<CustomerDTO> createCustomer(@Validated @RequestBody CustomerDTO customerDTO) {
        log.info("Creating customer: {}", customerDTO);
        return customerService.createCustomer(customerDTO);
    }

    @PutMapping(CUSTOMER_ID)
    public Mono<ResponseEntity<Object>> updateCustomer(@PathVariable Integer customerId, @Validated @RequestBody CustomerDTO customerDTO) {
        log.info("Updating customer: {}, with Id: {}", customerDTO, customerId);
        return customerService.updateCustomer(customerId, customerDTO)
                .map(updatedCustomer -> ResponseEntity.ok()
                        .header(LOCATION, PATH+"/"+customerId)
                        .build()).defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PatchMapping(CUSTOMER_ID)
    public Mono<ResponseEntity<Void>> patchCustomer(@PathVariable Integer customerId, @Validated @RequestBody CustomerDTO customerDTO) {
        log.info("Patching customer: {}, with Id: {}", customerDTO, customerId);
        return customerService.patchCustomer(customerId, customerDTO)
                .map(patchedCustomer -> ResponseEntity.ok()
                .header(LOCATION, PATH+"/"+customerId).build());
    }

    @DeleteMapping(CUSTOMER_ID)
    public Mono<Void> deleteCustomer(@PathVariable Integer customerId) {
        log.info("Deleting customer with Id: {}", customerId);
        return customerService.deleteCustomer(customerId);
    }


}
