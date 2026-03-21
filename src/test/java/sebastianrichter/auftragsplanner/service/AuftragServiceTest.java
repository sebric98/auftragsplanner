package sebastianrichter.auftragsplanner.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sebastianrichter.auftragsplanner.exception.KapazitaetskonfliktException;
import sebastianrichter.auftragsplanner.exception.ResourceNotFoundException;
import sebastianrichter.auftragsplanner.model.Auftrag;
import sebastianrichter.auftragsplanner.model.AuftragRequest;
import sebastianrichter.auftragsplanner.model.Kunde;
import sebastianrichter.auftragsplanner.model.Produkt;
import sebastianrichter.auftragsplanner.repository.AuftragRepository;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuftragServiceTest {

    @InjectMocks
    private AuftragService auftragService;

    @Mock
    private AuftragRepository auftragRepository;

    @Mock
    private KundeService kundeService;

    @Mock
    private ProduktService produktService;

    private static final Long UNGUEGLTIGE_PRODUKT_ID = 99L;
    private static final Long GUEGLTIGE_PRODUKT_ID = 1L;

    private static final Long UNGUEGLTIGE_KUNDE_ID = 98L;
    private static final Long GUEGLTIGE_KUNDE_ID = 2L;

    @Test
    @DisplayName("Auftrag bei freier Kapazitaet")
    void auftragWirdErstellt(){
        Kunde kunde = testKunde();
        Produkt produkt = testProdukt();
        AuftragRequest request = request(GUEGLTIGE_PRODUKT_ID,GUEGLTIGE_KUNDE_ID);


        when(kundeService.kundeFindenById(GUEGLTIGE_KUNDE_ID)).thenReturn(kunde);
        when(produktService.produktFindenById(GUEGLTIGE_PRODUKT_ID)).thenReturn(produkt);

        when(auftragRepository.findByProduktIdAndStatusIn(any(),any())).thenReturn(List.of());

        when(auftragRepository.save(any())).then(i -> i.getArgument(0));

        Auftrag result = auftragService.auftragErstellen(request);

        assertNotNull(result);
        verify(auftragRepository, times(1)).save(any());


    }

    @Test
    @DisplayName("Auftrag bei voller Kapazitaet")
    void auftragWirdNichtErstellt(){
        Kunde kunde = testKunde();
        Produkt produkt = testProdukt();
        when(kundeService.kundeFindenById(GUEGLTIGE_KUNDE_ID)).thenReturn(kunde);
        when(produktService.produktFindenById(GUEGLTIGE_PRODUKT_ID)).thenReturn(produkt);


        AuftragRequest request = request(GUEGLTIGE_PRODUKT_ID,GUEGLTIGE_KUNDE_ID);
        Auftrag auftrag1 = new Auftrag(2,LocalDate.now(),kunde,produkt);
        Auftrag auftrag2 = new Auftrag(2,LocalDate.now(),kunde,produkt);
        Auftrag auftrag3 = new Auftrag(2,LocalDate.now(),kunde,produkt);

        when(auftragRepository.findByProduktIdAndStatusIn(any(),any())).thenReturn(List.of(auftrag1,auftrag2,auftrag3));


        assertThrows(KapazitaetskonfliktException.class, () ->  auftragService.auftragErstellen(request));


    }

    @Test
    @DisplayName("KundeId ist nicht vorhanden")
    void ungueltigeKundenId(){
        when(kundeService.kundeFindenById(UNGUEGLTIGE_KUNDE_ID)).thenThrow(new ResourceNotFoundException("Kunde",UNGUEGLTIGE_KUNDE_ID));

        assertThrows(ResourceNotFoundException.class, () -> auftragService.auftragErstellen(request(UNGUEGLTIGE_PRODUKT_ID,UNGUEGLTIGE_KUNDE_ID)));

    }

    @Test
    @DisplayName("ProduktId ist nicht vorhanden")
    void ungeultigeProduktId(){
        when(kundeService.kundeFindenById(GUEGLTIGE_KUNDE_ID)).thenReturn(testKunde());
        when(produktService.produktFindenById(UNGUEGLTIGE_PRODUKT_ID)).thenThrow(new ResourceNotFoundException("Produkt",UNGUEGLTIGE_PRODUKT_ID));

        assertThrows(ResourceNotFoundException.class, () -> auftragService.auftragErstellen(request(UNGUEGLTIGE_PRODUKT_ID,UNGUEGLTIGE_KUNDE_ID)));

    }

    private AuftragRequest request(Long produkt_id, Long kunde_id){
        AuftragRequest request = new AuftragRequest();
        request.setKundeId(kunde_id);
        request.setProduktId(produkt_id);
        request.setMenge(42);
        request.setGewuenschtesLieferdatum(LocalDate.now().plusDays(30));
        return request;
    }

    private Produkt testProdukt() {
        Produkt produkt = spy(new Produkt("LWL-48", "Glasfaserkabel", 14, 3));
        doReturn(GUEGLTIGE_PRODUKT_ID).when(produkt).getId();
        return produkt;
    }

    private Kunde testKunde() {
        Kunde kunde = new Kunde("Stadtwerk Köln", "Max Müller");
        return kunde;
    }



}