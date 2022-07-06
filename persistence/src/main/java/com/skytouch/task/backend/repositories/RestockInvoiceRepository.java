package com.skytouch.task.backend.repositories;

import com.skytouch.task.commons.model.RestockInvoice;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestockInvoiceRepository extends CrudRepository<RestockInvoice, Integer> {

    @Query(nativeQuery = true)
    List<RestockInvoice> generateRestockInvoices(@Param("restockAmount") Integer restockAmount);
}
