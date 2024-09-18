package guru.springframework.spring6reactive.mapper;

import guru.springframework.spring6reactive.domain.Beer;
import guru.springframework.spring6reactive.model.BeerDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

/**
 * @author john
 * @since 14/09/2024
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BeerMapper {

    Beer beerDTOToBeer(BeerDTO beerDTO);

    BeerDTO beerToBeerDTO(Beer beer);
}
