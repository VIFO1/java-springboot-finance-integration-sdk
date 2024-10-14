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
public class CreateSevaOrderResponse {
    private HttpStatus statusCode;
    private Body body;
    private String errors;
    public CreateSevaOrderResponse() {
    }

    @JsonCreator
    public CreateSevaOrderResponse(
            @JsonProperty("status_code") HttpStatus statusCode,
            @JsonProperty("body") Body body,
            @JsonProperty("errors") String errors
            ) {
        this.statusCode = statusCode;
        this.body = body;
        this.errors = errors;
    }

    @Override
    public String toString() {
        return "status_code=" + statusCode + ",body={" + body + "}" + ",errors:" + errors;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Getter
    @Setter
    @Builder
    public static class Body {
        private String status;
        private int code;
        private Data data;
        public Body() {
        }

        @JsonCreator
        public Body(
                @JsonProperty("status") String status,
                @JsonProperty("code") int code,
                @JsonProperty("data") Data data
             ) {
            this.status = status;
            this.code = code;
            this.data = data;
   
        }

        @Override
        public String toString() {
            return "status=" + status + ",code=" + code + ",data={" + data + "}";
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Getter
    @Setter
    @Builder
    public static class Data {
        private String id;
        private String orderNumber;
        private int amount;
        private String beneficiaryAccountName;
        private CreatedAt createdAt;

        public Data() {
        }

        @JsonCreator
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

        @Override
        public String toString() {
            return "id=" + id + ",order_number=" + orderNumber + ",amount=" + amount +
                    ",beneficiary_account_name=" + beneficiaryAccountName + ",created_at={" + createdAt + "}";
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Getter
    @Setter
    @Builder
    public static class CreatedAt {
        private String date;
        private int timeZoneType;
        private String timeZone;

        public CreatedAt() {
        }

        @JsonCreator
        public CreatedAt(
                @JsonProperty("date") String date,
                @JsonProperty("timezone_type") int timeZoneType,
                @JsonProperty("timezone") String timeZone) {
            this.date = date;
            this.timeZoneType = timeZoneType;
            this.timeZone = timeZone;
        }

        @Override
        public String toString() {
            return "date=" + date + ",timezone_type=" + timeZoneType + ",timezone=" + timeZone;
        }

    }

}