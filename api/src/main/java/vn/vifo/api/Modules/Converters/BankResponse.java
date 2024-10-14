package vn.vifo.api.Modules.Converters;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

import org.springframework.http.HttpStatus;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@Builder
public class BankResponse {
    private Body body;
    private HttpStatus statusCode;
    private int httpCode;
    @JsonCreator
    public BankResponse(@JsonProperty("body") Body body,
                        @JsonProperty("status_code") HttpStatus statusCode,
                        @JsonProperty("http_code") int httpCode ) {
        this.body = body;
        this.statusCode = statusCode;
        this.httpCode = httpCode;
    }

    @Override
    public String toString() {
        return "{" +
                "status_code:'" + statusCode  +"," 
                + body + ",http_code:" + httpCode +
                '}';
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Getter
    @Setter
    @Builder
    public static class Body {
        private Boolean success;
        private String message;
        private String errorCode;
        private List<Data> data; 
 

        @JsonCreator
        public Body(@JsonProperty("success") Boolean success,
                    @JsonProperty("message") String message,
                    @JsonProperty("error_code") String errorCode,
                    @JsonProperty("data") List<Data> data
                    ) {
            this.success = success;
            this.message = message;
            this.errorCode = errorCode;
            this.data = data;
 
        }

        @Override
        public String toString() {
            return "body{" +
                    "success=" + success +
                    ", message='" + message  +
                    ", error_code='" + errorCode  +
                    ", data=" + data +
           
                    '}';
        }
    }

    @Getter
    @Setter
    @Builder
    public static class Data {
        private String code;
        private String name;
        private String shortName;

        @JsonCreator
        public Data(@JsonProperty("code") String code,
                    @JsonProperty("name") String name,
                    @JsonProperty("short_name") String shortName) {
            this.code = code;
            this.name = name;
            this.shortName = shortName;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "code='" + code  +
                    ", name='" + name  +
                    ", shortName='" + shortName  +
                    '}';
        }
    }
}
