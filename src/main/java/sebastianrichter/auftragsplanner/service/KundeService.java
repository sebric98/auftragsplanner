package sebastianrichter.auftragsplanner.service;

import org.springframework.stereotype.Service;
import sebastianrichter.auftragsplanner.exception.ResourceNotFoundException;
import sebastianrichter.auftragsplanner.model.Kunde;
import sebastianrichter.auftragsplanner.repository.KundeRepository;

import java.util.List;
import java.util.Optional;

@Service
public class KundeService {
    private final KundeRepository kundeRepository;

    public KundeService(KundeRepository kundeRepository) {
        this.kundeRepository = kundeRepository;
    }

    public List<Kunde> alleKundenFinden(){
        return kundeRepository.findAll();
    }

    public Kunde kundeFindenById(long id){
        return kundeRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("kunde",id));
    }

    public List<Kunde> kundeSuchenNachFirmenname(String firmenname){
        return kundeRepository.findByFirmennameContainingIgnoreCase(firmenname);
    }

    public Kunde kundeSpeichern(Kunde kunde){
        return kundeRepository.save(kunde);
    }

    public Kunde kundeAktualisieren(Kunde kunde){
        this.kundeFindenById(kunde.getId());
        return kundeRepository.save(kunde);
    }


    public void kundeLoeschen(long id){
        this.kundeFindenById(id);
        kundeRepository.deleteById(id);
    }

}
