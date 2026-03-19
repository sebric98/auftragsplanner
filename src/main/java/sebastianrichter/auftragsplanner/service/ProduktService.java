package sebastianrichter.auftragsplanner.service;

import org.springframework.stereotype.Service;
import sebastianrichter.auftragsplanner.exception.ResourceNotFoundException;
import sebastianrichter.auftragsplanner.model.Produkt;
import sebastianrichter.auftragsplanner.repository.ProduktRepository;

import java.util.List;

@Service
public class ProduktService {
    private final ProduktRepository produktRepository;

    public ProduktService(ProduktRepository produktRepository) {
        this.produktRepository = produktRepository;
    }

    public List<Produkt> alleProdukteFinden(){
        return produktRepository.findAll();
    }

    public Produkt produktFindenById(long id){
        return produktRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Produkt",id));
    }

    public List<Produkt> produkteSuchenNachTyp(String typ){
        return produktRepository.findByProduktTypContainingIgnoreCase(typ);
    }


    public Produkt produktSpeichern(Produkt produkt){
        return produktRepository.save(produkt);
    }

    public Produkt produktAktualisieren(Produkt produkt){
        this.produktFindenById(produkt.getId());
        return produktRepository.save(produkt);
    }


    public void produktLoeschen(long id){
        this.produktFindenById(id);
        produktRepository.deleteById(id);
    }
}
