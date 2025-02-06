package newbie.place_review.api;

import lombok.Getter;
import lombok.NonNull;
import org.springframework.http.HttpStatus;

@Getter
public class ApiResponse<T> {

    private final HttpStatus httpStatus;

    private final String message;

    private final T data;

    public ApiResponse(String message, HttpStatus httpStatus, @NonNull T data) {
        this.httpStatus = httpStatus;
        this.message = message;
        this.data = data;
    }

    public ApiResponse(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
        this.data = null;
    }

    public static <U> ApiResponse<U> of(String message, HttpStatus httpStatus, U data) {
        return new ApiResponse<>(message, httpStatus, data);
    }

    public static ApiResponse<Void> of(String message, HttpStatus httpStatus) {
        return new ApiResponse<>(message, httpStatus);
    }
}
