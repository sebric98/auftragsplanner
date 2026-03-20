package sebastianrichter.auftragsplanner.service;

import org.springframework.stereotype.Service;
import sebastianrichter.auftragsplanner.repository.AuftragRepository;
import sebastianrichter.auftragsplanner.repository.KundeRepository;
import sebastianrichter.auftragsplanner.repository.ProduktRepository;

@Service
public class AuftragService {

    private final AuftragRepository auftragRepository;

    private final KundeRepository kundeRepository;

    private final ProduktRepository produktRepository;

    public AuftragService(AuftragRepository auftragRepository, KundeRepository kundeRepository, ProduktRepository produktRepository) {
        this.auftragRepository = auftragRepository;
        this.kundeRepository = kundeRepository;
        this.produktRepository = produktRepository;
    }
}
