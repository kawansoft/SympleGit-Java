package com.symplegit.api.exception;

/**
 * UncheckedTimeoutException is a runtime exception that represents a timeout condition.
 * This exception is typically used to wrap a checked {@link java.util.concurrent.TimeoutException}
 * in scenarios where explicit catching or throwing of checked exceptions is not desirable.
 * 
 * <p>This exception is useful in contexts where operations are subject to a time constraint,
 * such as external process execution or network communication. It provides a way to indicate
 * a timeout failure without the overhead of checked exceptions.</p>
 *
 * <p>Usage of this exception should be consistent with the general expectations of runtime exceptions,
 * meaning it's unchecked and should be used for exceptional conditions that are not part of a normal
 * operation flow. It's intended to signal a significant problem that a reasonable application
 * might want to catch, but it's not compelled to do so.</p>
 */
public class UncheckedTimeoutException extends RuntimeException {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new runtime exception with the specified detail message.
     *
     * @param message the detail message. The detail message is saved for 
     *                later retrieval by the {@link Throwable#getMessage()} method.
     */
    public UncheckedTimeoutException(String message) {
        super(message);
    }

    /**
     * Constructs a new runtime exception with the specified detail message and cause.
     * 
     * <p>Note that the detail message associated with {@code cause} is not automatically
     * incorporated in this exception's detail message.</p>
     *
     * @param message the detail message (which is saved for later retrieval 
     *                by the {@link Throwable#getMessage()} method).
     * @param cause   the cause (which is saved for later retrieval by the
     *                {@link Throwable#getCause()} method). (A {@code null} value is
     *                permitted, and indicates that the cause is nonexistent or unknown.)
     */
    public UncheckedTimeoutException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new runtime exception with the specified cause and a detail
     * message of {@code (cause==null ? null : cause.toString())} (which typically
     * contains the class and detail message of {@code cause}). This constructor is
     * useful for runtime exceptions that are little more than wrappers for other throwables.
     *
     * @param cause the cause (which is saved for later retrieval by the
     *              {@link Throwable#getCause()} method). (A {@code null} value is
     *              permitted, and indicates that the cause is nonexistent or unknown.)
     */
    public UncheckedTimeoutException(Throwable cause) {
        super(cause);
    }
}
