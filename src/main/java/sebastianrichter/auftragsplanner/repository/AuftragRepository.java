package sebastianrichter.auftragsplanner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sebastianrichter.auftragsplanner.model.Auftrag;

public interface AuftragRepository extends JpaRepository<Auftrag,Long> {
}
