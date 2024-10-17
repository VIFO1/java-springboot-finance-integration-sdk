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
public class ApproveTransferMoneyResponse {
    private String statusCode;
    private Body body;

    public ApproveTransferMoneyResponse() {
    }

    @JsonCreator
    public ApproveTransferMoneyResponse(
            @JsonProperty("status_code") String statusCode,
            @JsonProperty("body") Body body) {
        this.statusCode = statusCode;
        this.body = body;
    }

    public String toString() {
        return JsonParserUtils.stringify(this);
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
                @JsonProperty("code") int code) {
            this.success = success;
            this.message = message;
            this.code = code;
        }

        public String toString() {
            return JsonParserUtils.stringify(this);
        }
    }
}
