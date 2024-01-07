package guru.springframework.spring6restmvc.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Created by robertZ on 2024-01-07.
 */
@Data
public class Beer {
    private UUID id;
    private Integer version;
    private String beerName;
    private BeerStryle beerStryle;
    private String upc;
    private Integer quantityOnHand;
    private BigDecimal price;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
}
