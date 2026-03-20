package sebastianrichter.auftragsplanner.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sebastianrichter.auftragsplanner.exception.ResourceNotFoundException;
import sebastianrichter.auftragsplanner.model.Kunde;
import sebastianrichter.auftragsplanner.repository.KundeRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class KundeServiceTest {

    @InjectMocks
    private KundeService kundeService;

    @Mock
    private KundeRepository kundeRepository;


    private static Kunde kunde1;

    private static final long GUELTIGE_ID = 1L;

    private static final long UNGUELTIGE_ID = 2L;


    @BeforeAll
    static void setUpKunde(){
        String kunde1_firmenname = "1gmbh";
        String kunde1_ansprechpartner = "ansprechpartner1";
        kunde1 = new Kunde(kunde1_firmenname,kunde1_ansprechpartner);

    }

    @Test
    @DisplayName("Gibt eine Liste aller Kunden zurueck")
    void alleKundenFinden(){
        when(kundeRepository.findAll()).thenReturn(List.of(kunde1));

        List<Kunde> result = kundeService.alleKundenFinden();

        assertEquals(List.of(kunde1),result,"alle kunden sollten wiedergeben werden");

    }

    @Test
    @DisplayName("Gibt eine leere Liste zurueck, wenn es keine Kunden gibt")
    void keineKundenFinden(){
        when(kundeRepository.findAll()).thenReturn(List.of());

        List<Kunde> result = kundeService.alleKundenFinden();

        assertEquals(List.of(),result,"Kundenliste sollte leer sein");

    }

    @Test
    @DisplayName("kundeFinden gueltige ID")
    void kundeFindenGueltigeID(){
        when(kundeRepository.findById(GUELTIGE_ID)).thenReturn(Optional.ofNullable(kunde1));


        Kunde result = kundeService.kundeFindenById(GUELTIGE_ID);

        assertEquals(kunde1,result,"der richtige Kunde wurde nicht gefunden");

    }

    @Test
    @DisplayName("Finden mit ungegueltiger ID schlägt fehl")
    void kundeFindenUngueltigeID(){
        when(kundeRepository.findById(UNGUELTIGE_ID)).thenReturn(Optional.empty());

        Exception result = assertThrows(ResourceNotFoundException.class, () -> kundeService.kundeFindenById(UNGUELTIGE_ID));

        assertEquals("Kunde mit ID "+UNGUELTIGE_ID+" nicht gefunden",result.getMessage(), "es wird nicht die richtige Fehlermeldung ausgegeben");

    }


    @Test
    @DisplayName("KundeSpeichern gibt gespeicherten kunden zurueck(")
    void kundeSpeichern(){
        when(kundeRepository.save(kunde1)).thenReturn(kunde1);

        Kunde result = kundeService.kundeSpeichern(kunde1);

        assertEquals(kunde1,result,"der Kunde  nicht korrekt gespeichert");

    }

    @Test
    @DisplayName("Aktualisieren mit unbekannter ID schlägt fehl")
    void kundeAktualisieren(){
        Kunde neuerKunde = spy(new Kunde("Firma", "Person"));
        doReturn(UNGUELTIGE_ID).when(neuerKunde).getId();

        Exception result = assertThrows(ResourceNotFoundException.class, () -> kundeService.kundeAktualisieren(neuerKunde));

        assertEquals("Kunde mit ID "+UNGUELTIGE_ID+" nicht gefunden",result.getMessage(), "es wird nicht die richtige Fehlermeldung ausgegeben");

    }

    @Test
    @DisplayName("Löschen mit gueltiger ID")
    void kundeLoeschenMitGueltigerID(){
        when(kundeRepository.findById(GUELTIGE_ID)).thenReturn(Optional.ofNullable(kunde1));

        kundeService.kundeLoeschen(GUELTIGE_ID);
        verify(kundeRepository,times(1)).deleteById(GUELTIGE_ID);
    }


    @Test
    @DisplayName("Löschen mit ungegueltiger ID schlaegt fehl")
    void kundeLoeschenMitUngueltigerID(){
        when(kundeRepository.findById(UNGUELTIGE_ID)).thenReturn(Optional.empty());

        Exception result = assertThrows(ResourceNotFoundException.class, () -> kundeService.kundeLoeschen(UNGUELTIGE_ID));

        assertEquals("Kunde mit ID "+UNGUELTIGE_ID+" nicht gefunden",result.getMessage(), "es wird nicht die richtige Fehlermeldung ausgegeben");

    }

    @Test
    @DisplayName("kundenSuchenNachFirmenname gibt alle Kunden mit dem gleichen Firmenname aus")
    void firmennamenSuche(){
        String firmenname3 = "1gmbh";
        String ansprechpartner3 = "ansprechpartner3";
        Kunde kunde3 = new Kunde(firmenname3, ansprechpartner3);

        when(kundeRepository.findByFirmennameContainingIgnoreCase(firmenname3)).thenReturn(List.of(kunde1,kunde3));

        List<Kunde> result = kundeService.kundeSuchenNachFirmenname(firmenname3);

        assertEquals(kundeRepository.findByFirmennameContainingIgnoreCase(firmenname3),result, "Kunde1 und Kunde3 sollten wiedergegeben werden");

    }
}