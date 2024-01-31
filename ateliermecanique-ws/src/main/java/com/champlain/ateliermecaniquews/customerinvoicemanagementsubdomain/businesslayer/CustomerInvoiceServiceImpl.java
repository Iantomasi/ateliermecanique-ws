package com.champlain.ateliermecaniquews.customerinvoicemanagementsubdomain.businesslayer;


import com.champlain.ateliermecaniquews.appointmentmanagementsubdomain.datalayer.Appointment;
import com.champlain.ateliermecaniquews.appointmentmanagementsubdomain.datalayer.AppointmentRepository;
import com.champlain.ateliermecaniquews.appointmentmanagementsubdomain.datalayer.Status;
import com.champlain.ateliermecaniquews.authenticationsubdomain.dataLayer.User;
import com.champlain.ateliermecaniquews.authenticationsubdomain.dataLayer.repositories.UserRepository;
import com.champlain.ateliermecaniquews.customerinvoicemanagementsubdomain.datalayer.CustomerInvoice;
import com.champlain.ateliermecaniquews.customerinvoicemanagementsubdomain.datalayer.CustomerInvoiceIdentifier;
import com.champlain.ateliermecaniquews.customerinvoicemanagementsubdomain.datalayer.CustomerInvoiceRepository;
import com.champlain.ateliermecaniquews.customerinvoicemanagementsubdomain.datamapperlayer.CustomerInvoiceRequestMapper;
import com.champlain.ateliermecaniquews.customerinvoicemanagementsubdomain.datamapperlayer.CustomerInvoiceResponseMapper;
import com.champlain.ateliermecaniquews.customerinvoicemanagementsubdomain.presentationlayer.CustomerInvoiceRequestModel;
import com.champlain.ateliermecaniquews.customerinvoicemanagementsubdomain.presentationlayer.CustomerInvoiceResponseModel;
import com.champlain.ateliermecaniquews.vehiclemanagementsubdomain.datalayer.VehicleRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class CustomerInvoiceServiceImpl implements CustomerInvoiceService{

    private CustomerInvoiceRepository customerInvoiceRepository;
    private UserRepository userRepository;
    private VehicleRepository vehicleRepository;
    private CustomerInvoiceResponseMapper customerInvoiceResponseMapper;
    private AppointmentRepository appointmentRepository;

    @Override
    public List<CustomerInvoiceResponseModel> getAllInvoices() {
        return customerInvoiceResponseMapper.entityToResponseModelList(customerInvoiceRepository.findAll());

    }

    @Override
    public List<CustomerInvoiceResponseModel> getAllInvoicesByCustomerId(String customerId) {
        List<CustomerInvoice> invoices = customerInvoiceRepository.findAllInvoicesByCustomerId(customerId);
        log.info("Fetching invoices for customer ID: {}", customerId);

        // Check if the appointments list is empty or null
        if (invoices.isEmpty()) {
            log.warn("No invoices found for customer ID: {} (invoices list is empty)", customerId);
            return Collections.emptyList();
        } else {
            log.info("Number of invoices found for customer ID {}: {}", customerId, invoices.size());
        }
        List<CustomerInvoiceResponseModel> invoiceResponseModels = customerInvoiceResponseMapper.entityToResponseModelList(invoices);
        if (invoiceResponseModels == null) {
            log.warn("InvoiceResponseModels is null after mapping");
        } else {
            log.info("Mapping successful. Number of InvoiceResponseModels: {}", invoiceResponseModels.size());
        }
        return invoiceResponseModels;
    }

    @Override
    @Transactional
    public CustomerInvoiceResponseModel addInvoiceToCustomerAccount(String customerId, CustomerInvoiceRequestModel customerInvoiceRequestModel) {
        User customerAccount = userRepository.findUserByUserIdentifier_UserId(customerId);

        if(customerAccount == null) {
            log.warn("Customer account not found for customer ID: {}", customerId);
            return null;
        }

        CustomerInvoice invoice = new CustomerInvoice();
        invoice.setCustomerInvoiceIdentifier(new CustomerInvoiceIdentifier());
        invoice.setCustomerId(customerInvoiceRequestModel.getCustomerId());
        invoice.setAppointmentId(customerInvoiceRequestModel.getAppointmentId());
        invoice.setInvoiceDate(customerInvoiceRequestModel.getInvoiceDate());
        invoice.setMechanicNotes(customerInvoiceRequestModel.getMechanicNotes());
        invoice.setSumOfServices(customerInvoiceRequestModel.getSumOfServices());

        CustomerInvoice  savedInvoice = customerInvoiceRepository.save(invoice);

        String appointmentId = customerInvoiceRequestModel.getAppointmentId();
        Appointment appointment = appointmentRepository.findAppointmentByAppointmentIdentifier_AppointmentId(appointmentId);
        if (appointment != null) {
            appointment.setStatus(Status.COMPLETED);
            appointmentRepository.save(appointment);
        } else {
            log.warn("Appointment not found for ID: {}", appointmentId);
        }
        return customerInvoiceResponseMapper.entityToResponseModel(savedInvoice);
    }

    @Override
    public CustomerInvoiceResponseModel getInvoiceById(String invoiceId) {

        CustomerInvoice invoice = customerInvoiceRepository.findCustomerInvoiceByCustomerInvoiceIdentifier_InvoiceId(invoiceId);

        if(invoice == null) {
            log.warn("No invoice found for invoice ID: {}", invoiceId);
            return null;
        }
        return customerInvoiceResponseMapper.entityToResponseModel(invoice);
    }

    @Override
    public CustomerInvoiceResponseModel updateCustomerInvoice(String invoiceId, CustomerInvoiceRequestModel customerInvoiceRequestModel) {

        CustomerInvoice invoice = customerInvoiceRepository.findCustomerInvoiceByCustomerInvoiceIdentifier_InvoiceId(invoiceId);

        if(invoice == null) {
            log.warn("Invoice not found for invoice ID: {}", invoiceId);
            return null;
        }
        invoice.setCustomerId(customerInvoiceRequestModel.getCustomerId());
        invoice.setAppointmentId(customerInvoiceRequestModel.getAppointmentId());
        invoice.setInvoiceDate(customerInvoiceRequestModel.getInvoiceDate());
        invoice.setMechanicNotes(customerInvoiceRequestModel.getMechanicNotes());
        invoice.setSumOfServices(customerInvoiceRequestModel.getSumOfServices());

        return customerInvoiceResponseMapper.entityToResponseModel(customerInvoiceRepository.save(invoice));
    }
}
