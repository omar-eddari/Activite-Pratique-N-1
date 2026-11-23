package ma.enset.bankaccountservice.exceptions;

public class ClientNotFoundException extends RuntimeException {
    public ClientNotFoundException(Long id) {
        super(String.format("Client %s introuvable", id));
    }
}
