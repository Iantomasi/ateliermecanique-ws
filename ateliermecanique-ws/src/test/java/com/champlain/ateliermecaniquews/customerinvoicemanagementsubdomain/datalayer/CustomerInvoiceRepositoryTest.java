package com.champlain.ateliermecaniquews.customerinvoicemanagementsubdomain.datalayer;

import com.champlain.ateliermecaniquews.appointmentmanagementsubdomain.datamapperlayer.AppointmentResponseMapper;
import com.champlain.ateliermecaniquews.customerinvoicemanagementsubdomain.datamapperlayer.CustomerInvoiceResponseMapper;
import com.champlain.ateliermecaniquews.customerinvoicemanagementsubdomain.presentationlayer.CustomerInvoiceRequestModel;
import com.champlain.ateliermecaniquews.customerinvoicemanagementsubdomain.presentationlayer.CustomerInvoiceResponseModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class CustomerInvoiceRepositoryTest {

    @Autowired
    private CustomerInvoiceRepository customerInvoiceRepository;

    private CustomerInvoice savedInvoice;
    private String savedInvoiceId;
    private String savedCustomerId;
    private LocalDateTime testInvoiceDate = LocalDateTime.now();

    private CustomerInvoiceResponseMapper mapper;
    @BeforeEach
    public void setUp() {
        CustomerInvoice newInvoice = new CustomerInvoice(
                "testCustomerId",
                "testAppointmentId",
                testInvoiceDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                "Test mechanic notes",
                100.00
        );
        savedInvoice = customerInvoiceRepository.save(newInvoice);
        savedInvoiceId = savedInvoice.getCustomerInvoiceIdentifier().getInvoiceId();
        savedCustomerId = savedInvoice.getCustomerId();
        mapper = Mappers.getMapper(CustomerInvoiceResponseMapper.class);
    }

    @AfterEach
    public void tearDown() {
        customerInvoiceRepository.deleteAll();
    }

    @Test
    public void findCustomerInvoiceByInvoiceId_shouldSucceed() {
        // Arrange
        assertNotNull(savedInvoiceId);

        // Act
        CustomerInvoice foundInvoice = customerInvoiceRepository.findCustomerInvoiceByCustomerInvoiceIdentifier_InvoiceId(savedInvoiceId);

        // Assert
        assertNotNull(foundInvoice);
        assertEquals(savedInvoiceId, foundInvoice.getCustomerInvoiceIdentifier().getInvoiceId());
    }

    @Test
    public void findAllInvoicesByCustomerId_shouldReturnList() {
        // Arrange
        assertNotNull(savedCustomerId);

        // Act
        List<CustomerInvoice> foundInvoices = customerInvoiceRepository.findAllInvoicesByCustomerId(savedCustomerId);

        // Assert
        assertFalse(foundInvoices.isEmpty());
        assertEquals(savedCustomerId, foundInvoices.get(0).getCustomerId());
    }

    @Test
    public void findCustomerInvoiceByInvalidInvoiceId_thenReturnNull() {
        // Arrange
        String nonExistentInvoiceId = "nonExistent";

        // Act
        CustomerInvoice foundInvoice = customerInvoiceRepository.findCustomerInvoiceByCustomerInvoiceIdentifier_InvoiceId(nonExistentInvoiceId);

        // Assert
        assertNull(foundInvoice);
    }

    @Test
    public void findAllInvoicesByNonExistentCustomerId_thenReturnEmptyList() {
        // Arrange
        String nonExistentCustomerId = "nonExistent";

        // Act
        List<CustomerInvoice> foundInvoices = customerInvoiceRepository.findAllInvoicesByCustomerId(nonExistentCustomerId);

        // Assert
        assertTrue(foundInvoices.isEmpty());
    }

    @Test
    void testEntityToResponseModel() {
        // Arrange
        CustomerInvoice invoice = createSampleInvoice();

        // Act
        CustomerInvoiceResponseModel responseModel = mapper.entityToResponseModel(invoice);

        // Assert
        assertNotNull(responseModel);
        assertEquals(invoice.getCustomerInvoiceIdentifier().getInvoiceId(), responseModel.getInvoiceId());
        assertEquals(invoice.getCustomerId(), responseModel.getCustomerId());
        assertEquals(invoice.getAppointmentId(), responseModel.getAppointmentId());
        assertEquals(invoice.getInvoiceDate(), responseModel.getInvoiceDate());
        assertEquals(invoice.getMechanicNotes(), responseModel.getMechanicNotes());
        assertEquals(invoice.getSumOfServices(), responseModel.getSumOfServices());
    }

    @Test
    void testEntityToResponseModelList() {
        // Arrange
        List<CustomerInvoice> invoices = Arrays.asList(createSampleInvoice(), createSampleInvoice());

        // Act
        List<CustomerInvoiceResponseModel> responseModels = mapper.entityToResponseModelList(invoices);

        // Assert
        assertNotNull(responseModels);
        assertEquals(invoices.size(), responseModels.size());
        for (int i = 0; i < invoices.size(); i++) {
            CustomerInvoice invoice = invoices.get(i);
            CustomerInvoiceResponseModel responseModel = responseModels.get(i);
            assertEquals(invoice.getCustomerInvoiceIdentifier().getInvoiceId(), responseModel.getInvoiceId());
            assertEquals(invoice.getCustomerId(), responseModel.getCustomerId());
            assertEquals(invoice.getAppointmentId(), responseModel.getAppointmentId());
            assertEquals(invoice.getInvoiceDate(), responseModel.getInvoiceDate());
            assertEquals(invoice.getMechanicNotes(), responseModel.getMechanicNotes());
            assertEquals(invoice.getSumOfServices(), responseModel.getSumOfServices());
        }
    }

    private CustomerInvoice createSampleInvoice() {
        CustomerInvoice invoice = new CustomerInvoice();
        invoice.setCustomerInvoiceIdentifier(new CustomerInvoiceIdentifier());
        invoice.setCustomerId("cust123");
        invoice.setAppointmentId("app123");
        invoice.setInvoiceDate(LocalDateTime.now());
        invoice.setMechanicNotes("Replaced brake pads");
        invoice.setSumOfServices(199.99);
        return invoice;
    }
}