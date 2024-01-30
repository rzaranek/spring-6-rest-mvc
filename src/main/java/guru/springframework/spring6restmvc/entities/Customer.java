package guru.springframework.spring6restmvc.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Version;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Created by robertZ on 2024-01-30.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Customer {
    @Id
    private UUID id;
    @Version
    private Integer version;
    private String name;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
}
