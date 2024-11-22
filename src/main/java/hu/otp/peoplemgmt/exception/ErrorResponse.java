package hu.otp.peoplemgmt.exception;

import java.time.LocalDateTime;

public class ErrorResponse {

    private LocalDateTime timestamp;

    private String reason;

    private String message;

    public ErrorResponse(LocalDateTime timestamp, String reason, String message) {
        this.timestamp = timestamp;
        this.reason = reason;
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
