package vn.vifo.api.Modules.DTO;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import vn.vifo.api.Ultils.JsonParserUtils;

@JsonIgnoreProperties(ignoreUnknown = true)
@Setter
@Getter
@Builder
public class CreateRevaOrderResponse {
    private String statusCode;
    private Body body;
    private String errors;
    public CreateRevaOrderResponse(){}

    @JsonCreator
    public CreateRevaOrderResponse(
            @JsonProperty("status_code") String statusCode,
            @JsonProperty("body") Body body,
            @JsonProperty("erros") String errors) 
    {
        this.statusCode = statusCode;
        this.body = body;
        this.errors = errors;
    }

    @Override
    public String toString() {
        return JsonParserUtils.stringify(this);
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Setter
    @Getter
    @Builder
    public static class Body {
        private String status;
        private int code;
        private Data data;
        private String message;
        public Body() {
        }

        @JsonCreator
        public Body(
                @JsonProperty("status") String status,
                @JsonProperty("code") int code,
                @JsonProperty("data") Data data,
                @JsonProperty("message") String message) {
            this.status = status;
            this.code = code;
            this.data = data;
        }

        @Override
        public String toString() {
            return JsonParserUtils.stringify(this);
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Setter
    @Getter
    @Builder
    public static class Data {
        private String id;
        private String orderNumber;
        private String distributorOrderNumber;
        private String paymentAccount;
        private String paymentBankName;
        private String paymentAccountName;
        private String qrPayment;
        private int amount;
        private String startDate;
        private String endDate;
        private CreatedAt createdAt;

        public Data() {
        }

        @JsonCreator
        public Data(
            @JsonProperty("id") String id,
            @JsonProperty("order_number") String orderNumber,
            @JsonProperty("distributor_order_number") String distributorOrderNumber,
            @JsonProperty("payment_account") String paymentAccount,
            @JsonProperty("payment_bank_name") String paymentBankName,
            @JsonProperty("payment_account_name") String paymentAccountName,
            @JsonProperty("qr_payment") String qrPayment,
            @JsonProperty("amount") int amount,
            @JsonProperty("start_date") String startDate,
            @JsonProperty("end_date") String endDate,
            @JsonProperty("created_at") CreatedAt createdAt
        ){
            this.id = id;
            this.orderNumber = orderNumber;
            this.distributorOrderNumber = distributorOrderNumber;
            this.paymentAccount = paymentAccount;
            this.paymentBankName = paymentBankName;
            this.paymentAccountName = paymentAccountName;
            this.qrPayment = qrPayment;
            this.amount = amount;
            this.startDate = startDate;
            this.endDate = endDate;
            this.createdAt = createdAt;
        }

        @Override
        public String toString() {
            return JsonParserUtils.stringify(this);
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Setter
    @Getter
    @Builder
    public static class CreatedAt {
        private String date;
        private String timezoneType;
        private String timezone;

        public CreatedAt(){}

        @JsonCreator
        public CreatedAt(
            @JsonProperty("date") String date,
            @JsonProperty("timezone_type") String timezoneType,
            @JsonProperty("timezone") String timezone
        ){
            this.date = date;
            this.timezoneType = timezoneType;
            this.timezone = timezone;
        }

        @Override
        public String toString() {
            return JsonParserUtils.stringify(this);
        }
    }
}
