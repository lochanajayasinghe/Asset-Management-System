package com.example.AMS.service;

import com.example.AMS.model.Invoice;
import com.example.AMS.repository.M_InvoiceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class M_InvoiceService {
    private final M_InvoiceRepository invoiceRepository;

    public M_InvoiceService(M_InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    @Transactional
    public Invoice saveInvoice(Invoice invoice) {
        return invoiceRepository.save(invoice);
    }

    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }
}
