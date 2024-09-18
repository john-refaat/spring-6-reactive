package guru.springframework.spring6reactive.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author john
 * @since 14/09/2024
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BeerDTO {

    private Integer id;

    @Size(min=3, max=255, message = "Name length must be between 3 and 255 characters")
    private String beerName;

    @Size(min=3, max=255)
    private String beerStyle;

    @Size(min=3, max=255)
    private String upc;

    @Min(1)
    @Max(999)
    private Integer quantityOnHand;

    @Positive
    private BigDecimal price;

    private LocalDateTime createdDate;

    private LocalDateTime lastModifiedDate;
}
