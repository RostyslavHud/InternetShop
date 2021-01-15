package internetshop.exception;

import internetshop.enums.Errors;
import lombok.Getter;

@Getter
public class ServiceException extends Exception {

    private Errors errors;
    public ServiceException(Errors errors) {
        super(errors.getMessage());
        this.errors = errors;
    }
}
