package web_demo.controller;

public class CustomExceptionController extends RuntimeException {
    public CustomExceptionController (String message){
        super(message);
    }
}
