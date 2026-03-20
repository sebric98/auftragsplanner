package sebastianrichter.auftragsplanner.service;

import org.springframework.stereotype.Service;
import sebastianrichter.auftragsplanner.exception.KapazitaetskonfliktException;
import sebastianrichter.auftragsplanner.exception.ResourceNotFoundException;
import sebastianrichter.auftragsplanner.model.*;
import sebastianrichter.auftragsplanner.repository.AuftragRepository;
import sebastianrichter.auftragsplanner.repository.KundeRepository;
import sebastianrichter.auftragsplanner.repository.ProduktRepository;

import java.util.List;

@Service
public class AuftragService {

    private final AuftragRepository auftragRepository;

    private final KundeService kundeService;

    private final ProduktService produktService;

    public AuftragService(AuftragRepository auftragRepository, KundeService kundeService, ProduktService produktService) {
        this.auftragRepository = auftragRepository;
        this.kundeService = kundeService;
        this.produktService = produktService;
    }

    public List<Auftrag> alleAuftraegeFinden(){
        return auftragRepository.findAll();
    }

    public Auftrag auftragFindenById(Long id){
        return auftragRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Auftrag",id));
    }

    public void statusAendern(Long id, AuftragsStatus neuerStatus){
        Auftrag auftrag = auftragFindenById(id);
        auftrag.setStatus(neuerStatus);
        auftragRepository.save(auftrag);
    }

    public void auftragLoeschen(long id){
        auftragFindenById(id);
        auftragRepository.deleteById(id);
    }

    public Auftrag auftragErstellen(AuftragRequest auftragRequest){
        Kunde kunde = kundeService.kundeFindenById(auftragRequest.getKundeId());
        Produkt produkt = produktService.produktFindenById(auftragRequest.getProduktId());
        List<Auftrag> aktiveAuftraege = auftragRepository.findByProduktIdAndStatusIn(produkt.getId(),List.of(AuftragsStatus.NEU,AuftragsStatus.IN_PRODUKTION));


        if(aktiveAuftraege.size() >= produkt.getMaxParalleleAuftraege()){
            throw new KapazitaetskonfliktException("Nicht genug Kapazitaeten für den Auftrag");
        }

        Auftrag neuerAuftrag = new Auftrag(auftragRequest.getMenge(),auftragRequest.getGewuenschtesLieferdatum(),kunde, produkt);
        return auftragRepository.save(neuerAuftrag);
    }

}
