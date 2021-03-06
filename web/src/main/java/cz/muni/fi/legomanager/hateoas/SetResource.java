package cz.muni.fi.legomanager.hateoas;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import cz.fi.muni.legomanager.dto.KitDTO;
import cz.fi.muni.legomanager.dto.SetOfKitsDTO;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import java.math.BigDecimal;
import java.util.List;

/**
 * Category rendered to JSON. The @Relation annotation specifies its name in HAL rendering of collections.
 *
 * @author Michal Peška, partly
 */
@Relation(value = "set", collectionRelation = "sets")
@JsonPropertyOrder({"id", "name"})
public class SetResource extends ResourceSupport {

    @JsonProperty("id") //ResourceSupport already has getId() method
    private long dtoId;
    private BigDecimal price;
    private String description;
    private List<KitDTO> kits;


    public SetResource(SetOfKitsDTO dto) {
        this.dtoId = dto.getId();
        this.price = dto.getPrice();
        this.description = dto.getDescription();
        this.kits = dto.getKits();
    }

    public long getDtoId() {
        return dtoId;
    }

    public void setDtoId(long dtoId) {
        this.dtoId = dtoId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) { this.price = price; }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<KitDTO> getKits() {
        return kits;
    }

    public void setKits(List<KitDTO> kits) {
        this.kits = kits;
    }
}
