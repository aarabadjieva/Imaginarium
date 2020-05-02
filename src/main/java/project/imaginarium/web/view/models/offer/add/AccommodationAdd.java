package project.imaginarium.web.view.models.offer.add;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;
import project.imaginarium.data.models.Planet;
import project.imaginarium.data.models.Sector;
import project.imaginarium.data.models.offers.Tag;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class AccommodationAdd {

    @NotEmpty(message = "Name cannot be empty")
    private String name;

    @Length(min = 3, message = "Description cannot be less than 3 symbols")
    private String description;

    @Min(value = 1, message = "Days cannot be less than 1")
    private int days;

    private Sector sector;

    @NotNull(message = "Please choose a Planet")
    private Planet planet;

    private MultipartFile picture;

    private List<Tag> tags;

    @NotNull( message = "Price is supposed to be positive number")
    @Min(value = 0, message = "Price is supposed to be positive number")
    private BigDecimal pricePerAdult;

    @NotNull( message = "Price is supposed to be positive number")
    @Min(value = 0, message = "Price is supposed to be positive number")
    private BigDecimal pricePerChildren;
}
