package sebastianrichter.auftragsplanner.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name="kunden")
public class Kunde {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    private String firmenname;

    @NotBlank
    private String ansprechpartner;

    private String email;

    private String telefon;

    private String branche;

    protected Kunde() {}

    public Kunde(String firmenname, String ansprechpartner) {
        this.firmenname = firmenname;
        this.ansprechpartner = ansprechpartner;
    }

    public Long getId() {
        return id;
    }

    public String getFirmenname() {
        return firmenname;
    }

    public String getAnsprechpartner() {
        return ansprechpartner;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefon() {
        return telefon;
    }

    public String getBranche() {
        return branche;
    }

    public void setFirmenname(String firmenname) {
        this.firmenname = firmenname;
    }

    public void setAnsprechpartner(String ansprechpartner) {
        this.ansprechpartner = ansprechpartner;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public void setBranche(String branche) {
        this.branche = branche;
    }
}
