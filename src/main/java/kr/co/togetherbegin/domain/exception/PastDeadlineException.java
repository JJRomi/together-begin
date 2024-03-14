package kr.co.togetherbegin.domain.exception;

public class PastDeadlineException extends RuntimeException{
    public PastDeadlineException(String message) {
        super(message);
    }
}
