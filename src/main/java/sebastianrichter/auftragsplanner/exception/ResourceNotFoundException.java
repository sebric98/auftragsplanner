package sebastianrichter.auftragsplanner.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String ressource, Long id) {
        super(ressource + " mit ID " + id + " nicht gefunden");
    }
}
