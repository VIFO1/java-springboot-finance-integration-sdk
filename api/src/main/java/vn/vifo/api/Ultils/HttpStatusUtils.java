package vn.vifo.api.Ultils;

import org.springframework.http.HttpStatus;

public class HttpStatusUtils {
    public static String getStatusMessage(HttpStatus statusCode) {
        return (statusCode != null) ? statusCode.value() + " " + statusCode.getReasonPhrase() : "Unknown Status";
    }
}
