package sebastianrichter.auftragsplanner.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name="produkte")
public class Produkt {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    private String bezeichnung;

    @NotBlank
    private String produktTyp;

    @NotNull
    @Min(1)
    private Integer produktionszeitInTagen;

    @NotNull
    @Min(1)
    private Integer maxParalleleAuftraege;

    private String spezifikation;

    protected Produkt() {}

    public Produkt(String bezeichnung, String produktTyp, Integer produktionszeitInTagen, Integer maxParalleleAuftraege) {
        this.bezeichnung = bezeichnung;
        this.produktTyp = produktTyp;
        this.produktionszeitInTagen = produktionszeitInTagen;
        this.maxParalleleAuftraege = maxParalleleAuftraege;
    }

    public long getId() {
        return id;
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

    public String getProduktTyp() {
        return produktTyp;
    }

    public Integer getProduktionszeitInTagen() {
        return produktionszeitInTagen;
    }

    public Integer getMaxParalleleAuftraege() {
        return maxParalleleAuftraege;
    }

    public String getSpezifikation() {
        return spezifikation;
    }

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    public void setProduktTyp(String produktTyp) {
        this.produktTyp = produktTyp;
    }

    public void setProduktionszeitInTagen(Integer produktionszeitInTagen) {
        this.produktionszeitInTagen = produktionszeitInTagen;
    }

    public void setMaxParalleleAuftraege(Integer maxParalleleAuftraege) {
        this.maxParalleleAuftraege = maxParalleleAuftraege;
    }

    public void setSpezifikation(String spezifikation) {
        this.spezifikation = spezifikation;
    }
}
