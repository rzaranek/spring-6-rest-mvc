package guru.springframework.spring6restmvc.controller;

import guru.springframework.spring6restmvc.model.Customer;
import guru.springframework.spring6restmvc.services.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Created by robertZ on 2024-01-09.
 */
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/customer")
public class CustomerController {

    private final CustomerService customerService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Customer> getCustomers() {
        return customerService.listCustomers();
    }

    @RequestMapping(value = "{customerId}", method = RequestMethod.GET)
    public Customer getCustomerById(@PathVariable("customerId") UUID customerId) {
        return customerService.getCustomerById(customerId);
    }

    @PostMapping
    public ResponseEntity createNewCustomer(@RequestBody Customer customer) {

        Customer savedCustomer = customerService.savedNewCustomer(customer);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Localization", "/api/v1/customer/" + savedCustomer.getId());

        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @PutMapping("{customerId}")
    public ResponseEntity updateCustomerById(@PathVariable("customerId") UUID customerId, @RequestBody Customer customer) {

        customerService.updateById(customerId, customer);

        return new ResponseEntity(HttpStatus.NO_CONTENT);

    }

    @DeleteMapping("{customerId}")
    public ResponseEntity deleteCustomerById(@PathVariable("customerId") UUID customerId) {
        customerService.deleteById(customerId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
