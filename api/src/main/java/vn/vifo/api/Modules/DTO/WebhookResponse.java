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
public class WebhookResponse {
    private String actionName;
    private String message;
    private Data data;
    private String errors;
    public WebhookResponse() {
    }

    @JsonCreator
    public WebhookResponse(
            @JsonProperty("action_name") String actionName,
            @JsonProperty("message") String message,
            @JsonProperty("data") Data data,@JsonProperty("errors") String errors) {
        this.actionName = actionName;
        this.message = message;
        this.data = data;
        this.errors = errors;
    }

    @Override
    public String toString() {
         return JsonParserUtils.stringify(this);
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Getter
    @Setter
    @Builder
    public static class Data {
        private String id;
        private String orderNumber;
        private String distributorOrderNumber;
        private String amount;
        private String transactionId;

        public Data(){}

        @JsonCreator
        public Data(
            @JsonProperty("id") String id,
            @JsonProperty("order_number") String orderNumber,
            @JsonProperty("distributor_order_number") String distributorOrderNumber,
            @JsonProperty("amount") String amount,
            @JsonProperty("transaction_id") String transactionId
        )
        {
            this.id = id;
            this.orderNumber = orderNumber;
            this.distributorOrderNumber = distributorOrderNumber;
            this.amount = amount;
            this.transactionId = transactionId;
        }

        @Override
        public String toString()
        {
            return JsonParserUtils.stringify(this);
        }
    }
}
