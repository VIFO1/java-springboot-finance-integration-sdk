package vn.vifo.api.Modules.DTO;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import vn.vifo.api.Ultils.JsonParserUtils;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@Builder
public class AuthenticateResponse {
    private String statusCode;
    private int httpCode;
    private Body body;

    public AuthenticateResponse() {
    }
@JsonCreator
    public AuthenticateResponse(
            @JsonProperty("status_code") String statusCode,
            @JsonProperty("http_code") int httpCode,
            @JsonProperty("body") Body body) {
        this.statusCode = statusCode;
        this.httpCode = httpCode;
        this.body = body;
    }
    
    @Override
    public String toString() {
       return JsonParserUtils.stringify(this);
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
        private Object  errors;

        public Body() {
        }

        public Body(
                @JsonProperty("token_type") String tokenType,
                @JsonProperty("expires_in") int expiresIn,
                @JsonProperty("access_token") String accessToken,
                @JsonProperty("refresh_token") String refreshToken,
                @JsonProperty("message") String message,
                @JsonProperty("errors") Object  errors) {
            this.tokenType = tokenType;
            this.expiresIn = expiresIn;
            this.accessToken = accessToken;
            this.refreshToken = refreshToken;
            this.message = message;
            this.errors = errors;
        }
        
    @Override
        public String toString() {
                  return JsonParserUtils.stringify(this);
        }
    }
}
