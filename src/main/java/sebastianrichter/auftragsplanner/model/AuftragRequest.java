package sebastianrichter.auftragsplanner.model;

import java.time.LocalDate;

public class AuftragRequest {
    private Long kundeId;
    private Long produktId;
    private Integer menge;
    private LocalDate gewuenschtesLieferdatum;

    public Long getKundeId() {
        return kundeId;
    }

    public void setKundeId(Long kundeId) {
        this.kundeId = kundeId;
    }

    public Long getProduktId() {
        return produktId;
    }

    public void setProduktId(Long produktId) {
        this.produktId = produktId;
    }

    public Integer getMenge() {
        return menge;
    }

    public void setMenge(Integer menge) {
        this.menge = menge;
    }

    public LocalDate getGewuenschtesLieferdatum() {
        return gewuenschtesLieferdatum;
    }

    public void setGewuenschtesLieferdatum(LocalDate gewuenschtesLieferdatum) {
        this.gewuenschtesLieferdatum = gewuenschtesLieferdatum;
    }
}
