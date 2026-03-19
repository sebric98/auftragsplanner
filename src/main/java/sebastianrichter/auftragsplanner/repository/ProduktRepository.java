package sebastianrichter.auftragsplanner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sebastianrichter.auftragsplanner.model.Produkt;

import java.util.List;

public interface ProduktRepository extends JpaRepository<Produkt, Long> {
    List<Produkt> findByProduktTypContainingIgnoreCase(String typ);
}
