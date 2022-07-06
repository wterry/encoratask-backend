package com.skytouch.task.commons.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * This entity isn't strictly part of the physical model (that is, it's not mapped to a physical table), but it is used to
 * store responses obtained from the execution of the <code>CalculateRestockPrices</code> stored procedure and as such
 * it still IS an entity in the model, if only a logical one.
 *
 * @author Waldo Terry
 */
@SqlResultSetMapping(
        name = "restockedInvoicesMapping",
        classes = {
            @ConstructorResult(
                    targetClass = RestockInvoice.class,
                    columns = {
                            @ColumnResult(name = "id"),
                            @ColumnResult(name = "sku"),
                            @ColumnResult(name = "description"),
                            @ColumnResult(name = "missingStock"),
                            @ColumnResult(name = "restockPrice")
                    }
            )
        }
)
@NamedNativeQuery(name = "RestockInvoice.generateRestockInvoices", query = "exec CalculateRestockPrices @RestockAmount = :restockAmount", resultSetMapping = "restockedInvoicesMapping")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class RestockInvoice {

    @Id
    private Integer id;
    private String sku;
    private String description;
    private Integer missingStock;
    private BigDecimal restockPrice;
}
