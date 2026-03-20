package sebastianrichter.auftragsplanner.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Entity
@Table(name="auftraege")
public class Auftrag {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Min(1)
    private Integer menge;

    @Enumerated(EnumType.STRING)
    private AuftragsStatus status = AuftragsStatus.NEU;


    private LocalDate bestellDatum;

    @NotNull
    @Future
    private LocalDate gewuenschtesLieferdatum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Kunde kunde;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Produkt produkt;

    protected Auftrag(){}

    public Auftrag(Integer menge, LocalDate gewuenschtesLieferdatum, Kunde kunde, Produkt produkt) {
        this.menge = menge;
        this.gewuenschtesLieferdatum = gewuenschtesLieferdatum;
        this.kunde = kunde;
        this.produkt = produkt;
    }

    public Long getId() {
        return id;
    }

    public Integer getMenge() {
        return menge;
    }

    public AuftragsStatus getStatus() {
        return status;
    }

    public LocalDate getBestellDatum() {
        return bestellDatum;
    }

    public LocalDate getGewuenschtesLieferdatum() {
        return gewuenschtesLieferdatum;
    }

    public Kunde getKunde() {
        return kunde;
    }

    public Produkt getProdukt() {
        return produkt;
    }

    public void setMenge(Integer menge) {
        this.menge = menge;
    }

    public void setStatus(AuftragsStatus auftragsstatus) {
        this.status = auftragsstatus;
    }

    public void setGewuenschtesLieferdatum(LocalDate gewuenschtesLieferdatum) {
        this.gewuenschtesLieferdatum = gewuenschtesLieferdatum;
    }

    public void setKunde(Kunde kunde) {
        this.kunde = kunde;
    }

    public void setProdukt(Produkt produkt) {
        this.produkt = produkt;
    }


    @PrePersist
    private void initBestelldatum(){
        this.bestellDatum = LocalDate.now();
    }
}
