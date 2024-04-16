package com.saastutorial.customer;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    private final RestTemplate restTemplate;

    public void registerCustomer(CustomerRegistrationRequest request) {
        Customer customer = Customer.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .build();

        // TODO: check if email is valid
        // TODO: check if email not taken
        customerRepository.saveAndFlush(customer);
        // TODO: check if fraudster
        FraudCheckResponse fraudCheckResponse =
                restTemplate.getForObject("http://FRAUD/api/v1/fraud-check/{customerID}",
                        FraudCheckResponse.class,
                        customer.getId()
                );

        if (fraudCheckResponse.isFraudster()) {
            throw new IllegalStateException("fraudster");
        }

        // TODO: send notification
    }
}
