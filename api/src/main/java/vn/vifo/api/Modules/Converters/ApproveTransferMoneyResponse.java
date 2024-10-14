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
public class ApproveTransferMoneyResponse {
    private HttpStatus statusCode;
    private Body body;
    public ApproveTransferMoneyResponse() {
    }
    @JsonCreator
    public ApproveTransferMoneyResponse(
            @JsonProperty("status_code") HttpStatus statusCode,
            @JsonProperty("body") Body body) {
        this.statusCode = statusCode;
        this.body = body;
    }

    public String toString() {
        return "status_code=" + statusCode + ",body={" + body + "}";
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Getter
    @Setter
    @Builder
    public static class Body {
        private boolean success;
        private String message;
        private int code;

        public Body() {
        }
        @JsonCreator
        public Body(
            @JsonProperty("success") boolean success,
            @JsonProperty("message") String message,
            @JsonProperty("code") int code
        )
        {
            this.success= success;
            this.message= message;
            this.code= code;
        }

        public String toString()
        {
            return "success:" + success + ",message:" + message + ",code:" + code;
        }
    }
}
