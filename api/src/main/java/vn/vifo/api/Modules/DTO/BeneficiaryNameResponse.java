package vn.vifo.api.Modules.DTO;



import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.HashMap;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import vn.vifo.api.Ultils.JsonParserUtils;

@Getter
@Setter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class BeneficiaryNameResponse {
    private String statusCode;
    private Body body;
    private int httpCode;

    @JsonCreator
    public BeneficiaryNameResponse(
            @JsonProperty("status_code") String statusCode,
            @JsonProperty("body") Body body,
            @JsonProperty("http_code") int httpCode) {
        this.statusCode = statusCode;
        this.body = body;
        this.httpCode = httpCode;
    }

    @Override
    public String toString() {
        return JsonParserUtils.stringify(this);
    }

    @Getter
    @Setter
    @Builder
    public static class Body {
        private boolean success;
        private int code;
        private String message;
        private HashMap<String, String> data;

        @JsonCreator
        public Body(
                @JsonProperty("success") boolean success,
                @JsonProperty("code") int code,
                @JsonProperty("message") String message,
                @JsonProperty("data") HashMap<String, String> data) {
            this.success = success;
            this.code = code;
            this.message = message;
            this.data = data;
        }

        @Override
        public String toString() {
            return JsonParserUtils.stringify(this);
        }
    }

}
