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
public class OtherRequestResponse {
    private String statusCode;
    private Body body;

    public OtherRequestResponse() {
    }

    @JsonCreator
    public OtherRequestResponse(
            @JsonProperty("status_code") String statusCode, @JsonProperty("body") Body body) {
        this.body = body;
        this.statusCode = statusCode;
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
        private boolean success;
        private String message;
        private Data data;

        public Body() {
        }

        @JsonCreator
        public Body(
                @JsonProperty("success") boolean success,
                @JsonProperty("message") String message,
                @JsonProperty("data") Data data) {
            this.success = success;
            this.message = message;
            this.data = data;
        }

        @Override
        public String toString() {
            return JsonParserUtils.stringify(this);
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Getter
    @Setter
    @Builder
    public static class Data {
        private String id;
        private String orderNumber;
        private String distributorOrder;
        private int status;
        private int amount;
        private Product product;

        public Data() {
        }

        @JsonCreator
        public Data(
                @JsonProperty("id") String id,
                @JsonProperty("order_number") String orderNumber,
                @JsonProperty("distributor_order_number") String distributorOrder,
                @JsonProperty("status") int status,
                @JsonProperty("amount") int amount,
                @JsonProperty("product") Product product) {
            this.id = id;
            this.orderNumber = orderNumber;
            this.distributorOrder = distributorOrder;
            this.status = status;
            this.amount = amount;
            this.product = product;
        }

        @Override
        public String toString() {
            return JsonParserUtils.stringify(this);

        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Getter
    @Setter
    @Builder
    public static class Product {
        private String name;
        private String code;

        public Product() {
        }

        @JsonCreator
        public Product(
                @JsonProperty("name") String name,
                @JsonProperty("code") String code) {
            this.name = name;
            this.code = code;
        }

        @Override
        public String toString() {
            return JsonParserUtils.stringify(this);
        }
    }

}
