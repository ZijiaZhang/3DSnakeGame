package Exceptions;

import java.security.MessageDigest;

public class AlreadyDestroyedException extends Exception {
    private String message;
    public AlreadyDestroyedException(){
    }
    public AlreadyDestroyedException(String m){
        this.message = m;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
