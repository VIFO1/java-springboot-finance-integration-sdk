# finance-integration-sdk
Java SDK finance of VIFO
# Java File Usage Guide
## Purpose

This Java file uses services from `VifoServiceFactory` to perform banking, money transfer and other requests. The following guide provides detailed information on how to use and understand the functions of the code.

## Requirements
- **Java**: Version 18 or higher.
- **Maven**: Version 3.9.9

## Code Structure
```java
1.Active Profile Configuration
System.setProperty("spring.profiles.active", "stg");

2.Application Context Initialization
ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

3.VifoServiceFactory Bean Retrieval
VifoServiceFactory vifoServiceFactory = context.getBean(VifoServiceFactory.class);

4.Login
AuthenticateResponse authenticateUser = vifoServiceFactory.performUserAuthentication(String username, String password);

5.Methods for Token Setup
vifoServiceFactory.setUserToken("your_user_token_here");
-This method is used to set the authentication token for user-based requests. 

vifoServiceFactory.setAminToken("your_admin_token_here");
-This method is used to set the authentication token for admin-based requests.

Using Tokens in Requests
Once the tokens are set using the above methods, they will be automatically included in the headers for their respective requests.

6.Get List of available Banks:
BankResponse bank = vifoServiceFactory.fetchBankInformation();

7.Get NAPAS Beneficiary Name:
BeneficiaryNameResponse bank = vifoServiceFactory.fetchBeneficiaryName(HashMap<String,Object> body);

8.Create Transfer Money API:
TransferMoneyResponse transferMoney = vifoServiceFactory.executeMoneyTransfer(HashMap<String,Object> body);

9.Bulk Approve Transfer Money API
ApproveTransferMoneyResponse approveMoneyTransfer = vifoServiceFactory.approveMoneyTransfer(String secretKey, String timestamp, HashMap<String,Object> body );

10.Others request:
OtherRequestResponse otherRequest = vifoServiceFactory.processOtherRequest(String key);

11.Webhook Response Conversion
WebhookResponse bank = vifoServiceFactory.convertObjectToWebhookResponse(jsonObject);

12.Create Reva Order:
CreateRevaOrderResponse createRevaOrder = vifoServiceFactory.createRevaOrder(
                String fullname,
                String benefiaryAccountName,
                String productCode,
                String distributorOrderNumber,
                String phone,
                String email,
                String address,
                int finalAmount,
                String comment,
                boolean bankDetail,
                QRTypeOrder qrType,
                String endDate
)

13.Create Seva Order:
CreateSevaOrderResponse createSevaOrder = vifoServiceFactory.createSevaOrder(
                String productCode,
                String phone,
                String fullname,
                int finalAmount,
                String distributorOrderNumber,
                String beneficiaryBankCode,
                String beneficiaryAccountNo,
                String comment,
                String sourceAccountNo
)

