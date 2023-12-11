package com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.datalayer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CustomerAccountRepositoryTest {

    @Autowired
    private CustomerAccountRepository customerAccountRepository;

    private String savedCustomerId;

    @BeforeEach
    public void setUp() {
        CustomerAccount newAccount = new CustomerAccount();
        CustomerAccountIdentifier identifier = new CustomerAccountIdentifier();
        newAccount.setCustomerAccountIdentifier(identifier);
        CustomerAccount savedAccount = customerAccountRepository.save(newAccount);
        savedCustomerId = savedAccount.getCustomerAccountIdentifier().getCustomerId();
    }

    @AfterEach
    public void tearDown() {
        customerAccountRepository.deleteAll();
    }

    @Test
    public void whenFindByCustomerId_thenReturnCustomerAccount() {
        // Arrange
        assertNotNull(savedCustomerId);

        // Act
        CustomerAccount found = customerAccountRepository.findCustomerAccountByCustomerAccountIdentifier_CustomerId(savedCustomerId);

        // Assert
        assertNotNull(found);
        assertEquals(savedCustomerId, found.getCustomerAccountIdentifier().getCustomerId());
    }

    @Test
    public void whenFindByNonExistentCustomerId_thenReturnNull() {
        // Arrange
        String nonExistentCustomerId = new CustomerAccountIdentifier().getCustomerId();

        // Act
        CustomerAccount found = customerAccountRepository.findCustomerAccountByCustomerAccountIdentifier_CustomerId(nonExistentCustomerId);

        // Assert
        assertNull(found);
    }
}
