package hu.otp.peoplemgmt.exception;

import java.time.LocalDateTime;

/**
 * Represents an error response that contains details about an exception or error event.
 * @author Andras Nyilas
 */
public class ErrorResponse {

    /**
     * The timestamp when the error occurred.
     */
    private LocalDateTime timestamp;

    /**
     * The reason or cause of the error.
     */
    private String reason;

    /**
     * The message describing the error in detail.
     */
    private String message;

    /**
     * Constructs a new {@code ErrorResponse} with the specified details.
     * @param timestamp the timestamp when the error occurred.
     * @param reason    the reason or cause of the error.
     * @param message   the detailed error message.
     */
    public ErrorResponse(LocalDateTime timestamp, String reason, String message) {
        this.timestamp = timestamp;
        this.reason = reason;
        this.message = message;
    }

    /**
     * Gets the timestamp when the error occurred.
     * @return the error timestamp
     */
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    /**
     * Sets the timestamp when the error occurred.
     * @param timestamp the new error timestamp
     */
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Gets the reason or cause of the error.
     * @return the error reason
     */
    public String getReason() {
        return reason;
    }

    /**
     * Sets the reason or cause of the error.
     * @param reason the new error reason
     */
    public void setReason(String reason) {
        this.reason = reason;
    }

    /**
     * Gets the detailed message describing the error.
     * @return the error message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the detailed message describing the error.
     * @param message the new error message
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
