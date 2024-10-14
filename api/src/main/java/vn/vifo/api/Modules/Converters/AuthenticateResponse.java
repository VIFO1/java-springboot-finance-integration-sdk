package vn.vifo.api.Modules.Converters;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@Builder
public class AuthenticateResponse {
    private HttpStatus statusCode;
    private int httpCode;
    private Body body;

    public AuthenticateResponse() {
    }
@JsonCreator
    public AuthenticateResponse(
            @JsonProperty("status_code") HttpStatus statusCode,
            @JsonProperty("http_code") int httpCode,
            @JsonProperty("body") Body body) {
        this.statusCode = statusCode;
        this.httpCode = httpCode;
        this.body = body;
    }

    public String toString() {
        return "{body{" + body + "}" + ",stautus_code=" + statusCode + ",http_code=" + httpCode + "}";
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Getter
    @Setter
    @Builder
    public static class Body {
        private String tokenType;
        private int expiresIn;
        private String accessToken;
        private String refreshToken;
        private String message;
        private String errors;

        public Body() {
        }

        public Body(
                @JsonProperty("token_type") String tokenType,
                @JsonProperty("expire_in") int expiresIn,
                @JsonProperty("access_token") String accessToken,
                @JsonProperty("refresh_token") String refreshToken,
                @JsonProperty("message") String message,
                @JsonProperty("errors") String errors) {
            this.tokenType = tokenType;
            this.expiresIn = expiresIn;
            this.accessToken = accessToken;
            this.refreshToken = refreshToken;
            this.message = message;
            this.errors = errors;
        }

        public String toString() {
            return "token_type=" + tokenType + ",expire_in=" + expiresIn + ",access_token=" + accessToken
                    + ",refresh_token=" + refreshToken + ",message:" + message + ",errors:" + errors;
        }
    }
}
