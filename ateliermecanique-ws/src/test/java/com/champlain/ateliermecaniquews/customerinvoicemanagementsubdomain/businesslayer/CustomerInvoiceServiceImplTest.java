package com.champlain.ateliermecaniquews.customerinvoicemanagementsubdomain.businesslayer;

import com.champlain.ateliermecaniquews.appointmentmanagementsubdomain.datalayer.Appointment;
import com.champlain.ateliermecaniquews.appointmentmanagementsubdomain.datalayer.AppointmentRepository;
import com.champlain.ateliermecaniquews.appointmentmanagementsubdomain.datalayer.Status;
import com.champlain.ateliermecaniquews.authenticationsubdomain.dataLayer.User;
import com.champlain.ateliermecaniquews.authenticationsubdomain.dataLayer.repositories.UserRepository;
import com.champlain.ateliermecaniquews.customerinvoicemanagementsubdomain.datalayer.CustomerInvoice;
import com.champlain.ateliermecaniquews.customerinvoicemanagementsubdomain.datalayer.CustomerInvoiceRepository;
import com.champlain.ateliermecaniquews.customerinvoicemanagementsubdomain.datamapperlayer.CustomerInvoiceResponseMapper;
import com.champlain.ateliermecaniquews.customerinvoicemanagementsubdomain.presentationlayer.CustomerInvoiceRequestModel;
import com.champlain.ateliermecaniquews.customerinvoicemanagementsubdomain.presentationlayer.CustomerInvoiceResponseModel;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
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

    @Mock
    private AppointmentRepository appointmentRepository;

    @InjectMocks
    private CustomerInvoiceServiceImpl customerInvoiceService;

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
        List<CustomerInvoiceResponseModel> result = customerInvoiceService.getAllInvoices();

        // Assert
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(responseModels.size(), result.size());
        assertEquals(responseModels.get(0).getInvoiceId(), result.get(0).getInvoiceId());

        verify(customerInvoiceRepository, times(1)).findAll();
        verify(customerInvoiceResponseMapper, times(1)).entityToResponseModelList(invoices);
    }


    @Test
    void getAllInvoicesByCustomerId_shouldReturnInvoiceResponseModelList() {
        // Arrange
        String customerId = "testCustomerId";
        CustomerInvoice invoice = new CustomerInvoice();
        List<CustomerInvoice> invoices = Arrays.asList(invoice);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse("2024-03-24 11:00", formatter);


        CustomerInvoiceResponseModel responseModel = CustomerInvoiceResponseModel.builder()
                .invoiceId(invoice.getCustomerInvoiceIdentifier().getInvoiceId().toString())
                .customerId(invoice.getCustomerId())
                .appointmentId(invoice.getAppointmentId())
                .invoiceDate(dateTime)
                .mechanicNotes(invoice.getMechanicNotes())
                .sumOfServices(invoice.getSumOfServices())
                .build();


        when(customerInvoiceRepository.findAllInvoicesByCustomerId(customerId)).thenReturn(invoices);
        when(customerInvoiceResponseMapper.entityToResponseModelList(invoices)).thenReturn(Arrays.asList(responseModel));

        // Act
        List<CustomerInvoiceResponseModel> result = customerInvoiceService.getAllInvoicesByCustomerId(customerId);

        // Assert
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(customerInvoiceRepository).findAllInvoicesByCustomerId(customerId);
        verify(customerInvoiceResponseMapper).entityToResponseModelList(invoices);
    }



    @Test
    void getAllInvoicesByCustomerId_noAppointments_shouldReturnEmptyList() {
        String customerId = "someCustomerId";
        when(customerInvoiceRepository.findAllInvoicesByCustomerId(customerId)).thenReturn(Collections.emptyList());

        List<CustomerInvoiceResponseModel> result = customerInvoiceService.getAllInvoicesByCustomerId(customerId);

        assertTrue(result.isEmpty());
        verify(customerInvoiceRepository).findAllInvoicesByCustomerId(customerId);
        verify(customerInvoiceResponseMapper, never()).entityToResponseModelList(any());
    }


    @Test
    void getAllInvoicesByCustomerId_shouldReturnNull() {
        String customerId = "testCustomerId";
        List<CustomerInvoice> invoices = Arrays.asList(new CustomerInvoice());
        when(customerInvoiceRepository.findAllInvoicesByCustomerId(customerId)).thenReturn(invoices);
        when(customerInvoiceResponseMapper.entityToResponseModelList(invoices)).thenReturn(null);

        List<CustomerInvoiceResponseModel> result = customerInvoiceService.getAllInvoicesByCustomerId(customerId);

        assertNull(result);
        verify(customerInvoiceRepository).findAllInvoicesByCustomerId(customerId);
        verify(customerInvoiceResponseMapper).entityToResponseModelList(invoices);
    }



    @Test
    void addInvoiceToCustomerAccount_shouldSucceed() {
        // Arrange
        String customerId = "testCustomerId";
        String appointmentId = "testAppointmentId";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse("2024-03-24 11:00", formatter);
        CustomerInvoiceRequestModel requestModel = CustomerInvoiceRequestModel.builder()
                .customerId(customerId)
                .appointmentId(appointmentId)
                .invoiceDate(dateTime)
                .mechanicNotes("Test mechanic notes")
                .sumOfServices(100.00)
                .build();

        User customerAccount = new User();
        Appointment testAppointment = new Appointment(); // Mocked appointment
        testAppointment.setStatus(Status.COMPLETED);

        when(userRepository.findUserByUserIdentifier_UserId(customerId))
                .thenReturn(customerAccount);
        when(appointmentRepository.findAppointmentByAppointmentIdentifier_AppointmentId(appointmentId))
                .thenReturn(testAppointment);

        CustomerInvoice savedInvoice = new CustomerInvoice();
        when(customerInvoiceRepository.save(any(CustomerInvoice.class)))
                .thenReturn(savedInvoice);
        when(appointmentRepository.save(any(Appointment.class)))
                .then(AdditionalAnswers.returnsFirstArg()); // Mock the save operation to return the updated appointment

        CustomerInvoiceResponseModel expectedResponse = CustomerInvoiceResponseModel.builder()
                .invoiceId("1")
                .customerId(customerId)
                .appointmentId(appointmentId)
                .invoiceDate(dateTime)
                .mechanicNotes("Test mechanic notes")
                .sumOfServices(100.00)
                .build();

        when(customerInvoiceResponseMapper.entityToResponseModel(savedInvoice))
                .thenReturn(expectedResponse);

        // Act
        CustomerInvoiceResponseModel result = customerInvoiceService.addInvoiceToCustomerAccount(customerId, requestModel);

        // Assert
        assertNotNull(result);
        assertEquals(expectedResponse.getCustomerId(), result.getCustomerId());
        assertEquals(expectedResponse.getSumOfServices(), result.getSumOfServices());
        verify(customerInvoiceRepository, times(1)).save(any(CustomerInvoice.class));
        verify(appointmentRepository, times(1)).save(testAppointment);
        assertEquals(Status.COMPLETED, testAppointment.getStatus()); // Verify that the appointment status has been updated
    }


    @Test
    void addInvoiceToCustomerAccount_CustomerNotFound_shouldReturnNull() {
        // Arrange
        String nonExistentCustomerId = "nonExistentCustomerId";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse("2024-03-24 11:00", formatter);

        CustomerInvoiceRequestModel requestModel = CustomerInvoiceRequestModel.builder()
                .customerId(nonExistentCustomerId)
                .appointmentId("testAppointmentId")
                .invoiceDate(dateTime)
                .mechanicNotes("Test mechanic notes")
                .sumOfServices(100.00)
                .build();

        when(userRepository.findUserByUserIdentifier_UserId(nonExistentCustomerId))
                .thenReturn(null); // Simulate that the user is not found

        // Act
        CustomerInvoiceResponseModel result = customerInvoiceService.addInvoiceToCustomerAccount(nonExistentCustomerId, requestModel);

        // Assert
        assertNull(result, "No customer found with corresponding customerId");
    }


    @Test
    void getInvoiceById_shouldReturnInvoiceDetails() {
        String invoiceDateStr = "2024-02-04 19:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(invoiceDateStr, formatter);

        CustomerInvoice mockInvoice = new CustomerInvoice(
                "test-customer-id",
                "test-appointment-id",
                invoiceDateStr,
                "Muffler was fixed",
                118.95
        );

        CustomerInvoiceResponseModel expectedResponse = CustomerInvoiceResponseModel.builder()
                .invoiceId(mockInvoice.getCustomerInvoiceIdentifier().getInvoiceId().toString())
                .customerId(mockInvoice.getCustomerId())
                .appointmentId(mockInvoice.getAppointmentId())
                .invoiceDate(dateTime)
                .mechanicNotes(mockInvoice.getMechanicNotes())
                .sumOfServices(mockInvoice.getSumOfServices())
                .build();

        when(customerInvoiceRepository.findCustomerInvoiceByCustomerInvoiceIdentifier_InvoiceId(mockInvoice.getCustomerInvoiceIdentifier().getInvoiceId().toString())).thenReturn(mockInvoice);
        when(customerInvoiceResponseMapper.entityToResponseModel(mockInvoice)).thenReturn(expectedResponse);

        // Act
        CustomerInvoiceResponseModel actualResponse = customerInvoiceService.getInvoiceById(mockInvoice.getCustomerInvoiceIdentifier().getInvoiceId().toString());

        // Assert
        assertNotNull(actualResponse);
        assertEquals(expectedResponse.getInvoiceId(), actualResponse.getInvoiceId());
        assertEquals(expectedResponse.getMechanicNotes(), actualResponse.getMechanicNotes());

        verify(customerInvoiceRepository, times(1)).findCustomerInvoiceByCustomerInvoiceIdentifier_InvoiceId(mockInvoice.getCustomerInvoiceIdentifier().getInvoiceId().toString());
        verify(customerInvoiceResponseMapper, times(1)).entityToResponseModel(mockInvoice);
    }

    @Test
    void getInvoiceById_whenInvoiceNotFound_shouldReturnNull() {
        String testInvoiceId = "non-existent-invoice-id";
        when(customerInvoiceRepository.findCustomerInvoiceByCustomerInvoiceIdentifier_InvoiceId(testInvoiceId)).thenReturn(null);
        CustomerInvoiceResponseModel actualResponse = customerInvoiceService.getInvoiceById(testInvoiceId);

        assertNull(actualResponse);
        verify(customerInvoiceRepository, times(1)).findCustomerInvoiceByCustomerInvoiceIdentifier_InvoiceId(testInvoiceId);
        verify(customerInvoiceResponseMapper, never()).entityToResponseModel(any(CustomerInvoice.class));
    }


}