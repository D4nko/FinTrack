package pl.engineering.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class AccountNotFoundExeption extends RuntimeException {
    public AccountNotFoundExeption(String string) {

    }
}
