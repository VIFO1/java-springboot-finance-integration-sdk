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
public class TransferMoneyResponse {
    private HttpStatus statusCode;
    private Body body;
    private String errors;
    private int httpCode;

    @JsonCreator
    public TransferMoneyResponse(@JsonProperty("status_code") HttpStatus statusCode, @JsonProperty("body") Body body,
            @JsonProperty("errors") String errors, @JsonProperty("http_code") int httpCode) {
        this.body = body;
        this.statusCode = statusCode;
        this.errors = errors;
        this.httpCode = httpCode;
    }

    public TransferMoneyResponse() {
    }

    @Override
    public String toString() {
        return "{" + body + "," + "status_code=" + statusCode + ",errors:" + errors + ",http_code=" + httpCode + "}";

    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Getter
    @Setter
    @Builder
    public static class Body {
        private String status;
        private int code;
        private String statusCode;
        private Data data;

        @JsonCreator
        public Body(
                @JsonProperty("status") String status,
                @JsonProperty("code") int code,
                @JsonProperty("status_code") String statusCode,
                @JsonProperty("data") Data data) {
            this.status = status;
            this.code = code;
            this.statusCode = statusCode;
            this.data = data;
        }

        public Body() {
        }

        @Override
        public String toString() {
            return "body{" +
                    "status:" + status + "," +
                    "code:" + code + "," +
                    "status_code:" + statusCode + "," +
                    "data" + data
                    + "}";
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)

    public static class Data {
        private String id;
        private String orderNumber;
        private int amount;
        private String beneficiaryAccountName;
        private CreatedAt createdAt;

        public Data(
                @JsonProperty("id") String id,
                @JsonProperty("order_number") String orderNumber,
                @JsonProperty("amount") int amount,
                @JsonProperty("beneficiary_account_name") String beneficiaryAccountName,
                @JsonProperty("created_at") CreatedAt createdAt) {
            this.id = id;
            this.orderNumber = orderNumber;
            this.amount = amount;
            this.beneficiaryAccountName = beneficiaryAccountName;
            this.createdAt = createdAt;
        }

        public Data() {
        }

        public String toString() {
            return "{" +
                    "id=" + id + "," +
                    "order_number=" + orderNumber + "," +
                    "amount=" + amount + "," +
                    "beneficiary_account_name=" + beneficiaryAccountName + "," +
                    "created_at={" + createdAt + "}"
                    + "";
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Getter
    @Setter
    @Builder
    public static class CreatedAt {
        private String date;
        private int timezoneType;
        private String timeZone;
        private int statusCode;

        public CreatedAt(
                @JsonProperty("data") String date,
                @JsonProperty("timezone_type") int timezoneType,
                @JsonProperty("timezone") String timeZone,
                @JsonProperty("status_code") int statusCode) {
            this.date = date;
            this.timezoneType = timezoneType;
            this.timeZone = timeZone;
            this.statusCode = statusCode;
        }

        public CreatedAt() {
        }

        @Override
        public String toString() {
            return "date=" + date + "," +
                    "timezone_type=" + timezoneType + "," +
                    "timezone=" + timeZone + "," +
                    "status_code=" + statusCode;
        }
    }

}
