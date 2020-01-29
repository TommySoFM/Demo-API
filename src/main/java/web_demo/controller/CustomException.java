package web_demo.controller;

public class CustomException extends RuntimeException {
    public CustomException(String message){
        super(message);
    }
}
