package mk.gov.moepp.emi.invertoryinfo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.function.Supplier;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFound extends RuntimeException{

    public ResourceNotFound(String message) {
        super(message);
    }
}
