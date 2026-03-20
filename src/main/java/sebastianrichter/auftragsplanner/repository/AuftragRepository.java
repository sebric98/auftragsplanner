package sebastianrichter.auftragsplanner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sebastianrichter.auftragsplanner.model.Auftrag;
import sebastianrichter.auftragsplanner.model.AuftragsStatus;
import sebastianrichter.auftragsplanner.model.Kunde;

import java.util.List;

public interface AuftragRepository extends JpaRepository<Auftrag,Long> {

    List<Auftrag> findByKundeId(Long Id);

    List<Auftrag> findByProduktId(Long Id);

    List<Auftrag> findByProduktIdAndStatusIn(Long id, List<AuftragsStatus> statusList);

    List<Auftrag> findByStatus(AuftragsStatus status);

}
