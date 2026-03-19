package sebastianrichter.auftragsplanner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sebastianrichter.auftragsplanner.model.Kunde;

import java.util.List;

public interface KundeRepository extends JpaRepository<Kunde,Long> {

    List<Kunde> findByFirmennameContainingIgnoreCase(String firmenname);
}
