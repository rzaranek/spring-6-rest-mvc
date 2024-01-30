package guru.springframework.spring6restmvc.controller;

import guru.springframework.spring6restmvc.model.CustomerDTO;
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
public class CustomerController {

    public static final String CUSTOMER_PATH = "/api/v1/customer";
    public static final String CUSTOMER_ID = "customerId";
    public static final String CUSTOMER_PATH_ID = CUSTOMER_PATH + "/{" + CUSTOMER_ID + "}";

    private final CustomerService customerService;

    @GetMapping(CUSTOMER_PATH)
    public List<CustomerDTO> getCustomers() {
        return customerService.listCustomers();
    }

    @GetMapping(CUSTOMER_PATH_ID)
    public CustomerDTO getCustomerById(@PathVariable(CUSTOMER_ID) UUID customerId) {
        return customerService.getCustomerById(customerId).orElseThrow(NotFoundException::new);
    }

    @PostMapping(CUSTOMER_PATH)
    public ResponseEntity createNewCustomer(@RequestBody CustomerDTO customer) {

        CustomerDTO savedCustomer = customerService.savedNewCustomer(customer);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Localization", CUSTOMER_PATH + "/" + savedCustomer.getId());

        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @PutMapping(CUSTOMER_PATH_ID)
    public ResponseEntity updateCustomerById(@PathVariable(CUSTOMER_ID) UUID customerId, @RequestBody CustomerDTO customer) {

        customerService.updateById(customerId, customer);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PatchMapping(CUSTOMER_PATH_ID)
    public ResponseEntity updatePatchById(@PathVariable(CUSTOMER_ID) UUID idCustomer, @RequestBody CustomerDTO customer) {
        customerService.patchCustomerById(idCustomer, customer);

        return new ResponseEntity(HttpStatus.NO_CONTENT);

    }

    @DeleteMapping(CUSTOMER_PATH_ID)
    public ResponseEntity deleteCustomerById(@PathVariable(CUSTOMER_ID) UUID customerId) {
        customerService.deleteById(customerId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
