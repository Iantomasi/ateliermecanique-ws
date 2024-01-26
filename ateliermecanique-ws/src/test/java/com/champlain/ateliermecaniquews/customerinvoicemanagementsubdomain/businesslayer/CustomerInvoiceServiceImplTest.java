package com.champlain.ateliermecaniquews.customerinvoicemanagementsubdomain.businesslayer;

import com.champlain.ateliermecaniquews.authenticationsubdomain.dataLayer.repositories.UserRepository;
import com.champlain.ateliermecaniquews.customerinvoicemanagementsubdomain.datalayer.CustomerInvoice;
import com.champlain.ateliermecaniquews.customerinvoicemanagementsubdomain.datalayer.CustomerInvoiceRepository;
import com.champlain.ateliermecaniquews.customerinvoicemanagementsubdomain.datamapperlayer.CustomerInvoiceResponseMapper;
import com.champlain.ateliermecaniquews.customerinvoicemanagementsubdomain.presentationlayer.CustomerInvoiceResponseModel;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class CustomerInvoiceServiceImplTest {


    @Mock
    private CustomerInvoiceRepository customerInvoiceRepository;

    @Mock
    private CustomerInvoiceResponseMapper customerInvoiceResponseMapper;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CustomerInvoiceServiceImpl customerInvoiceServiceImpl;

    @Captor
    private ArgumentCaptor<CustomerInvoice> customerInvoiceArgumentCaptor;

    @Test
    void getAllCustomerInvoices_shouldSucceed() {
        // Arrange
        CustomerInvoice invoice = new CustomerInvoice(

                "yzab8cd5-3e6f-8796-2abi-96330c6bw164",
                "1008dc5c-d460-443f-8f37-a174284f868l",
                "2024-02-04 19:00",
                "Muffler was fixed",
                118.95
        );

        List<CustomerInvoice> invoices = Collections.singletonList(invoice);
        when(customerInvoiceRepository.findAll()).thenReturn(invoices);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse("2024-02-04 19:00", formatter);

        CustomerInvoiceResponseModel responseModel = CustomerInvoiceResponseModel.builder()
                .invoiceId(invoice.getCustomerInvoiceIdentifier().getInvoiceId().toString())
                .customerId(invoice.getCustomerId())
                .appointmentId(invoice.getAppointmentId())
                .invoiceDate(dateTime)
                .mechanicNotes(invoice.getMechanicNotes())
                .sumOfServices(invoice.getSumOfServices())
                .build();

        List<CustomerInvoiceResponseModel> responseModels = Collections.singletonList(responseModel);

        when(customerInvoiceResponseMapper.entityToResponseModelList(invoices)).thenReturn(responseModels);

        // Act
        List<CustomerInvoiceResponseModel> result = customerInvoiceServiceImpl.getAllInvoices();

        // Assert
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(responseModels.size(), result.size());
        assertEquals(responseModels.get(0).getInvoiceId(), result.get(0).getInvoiceId());

        verify(customerInvoiceRepository, times(1)).findAll();
        verify(customerInvoiceResponseMapper, times(1)).entityToResponseModelList(invoices);
    }




}