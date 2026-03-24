package sebastianrichter.auftragsplanner.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sebastianrichter.auftragsplanner.model.Kunde;
import sebastianrichter.auftragsplanner.service.KundeService;

import java.util.List;

@RestController
@RequestMapping("/api/kunden")
public class KundeController {

    private final KundeService kundeService;

    public KundeController(KundeService kundeService) {
        this.kundeService = kundeService;
    }

    @GetMapping()
    public ResponseEntity<List<Kunde>> alleKundenfinden(){
        return ResponseEntity.ok(kundeService.alleKundenFinden());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Kunde> kundeFindenById(@PathVariable Long id){
        return ResponseEntity.ok(kundeService.kundeFindenById(id));
    }

    @GetMapping("/suche")
    public ResponseEntity<List<Kunde>> kundeSuchen(@RequestParam String firmenname){
        return ResponseEntity.ok(kundeService.kundeSuchenNachFirmenname(firmenname));
    }

    @PostMapping
    public ResponseEntity<Kunde> kundeSpeichern(@RequestBody @Valid Kunde kunde){
        return ResponseEntity.ok(kundeService.kundeSpeichern(kunde));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Kunde> kundeAktualisieren(@PathVariable Long id, @RequestBody Kunde kunde){
        return ResponseEntity.ok(kundeService.kundeAktualisieren(kunde));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> kundeLöschen(@PathVariable Long id){
         kundeService.kundeLoeschen(id);
         return ResponseEntity.noContent().build();
    }
}
