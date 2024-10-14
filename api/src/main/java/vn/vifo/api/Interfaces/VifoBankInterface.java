package vn.vifo.api.Interfaces;

import java.util.List;
import java.util.Map;

import vn.vifo.api.Modules.Converters.BankResponse;
import vn.vifo.api.Modules.Converters.BeneficiaryNameResponse;

public interface VifoBankInterface {
    public List<String> validateBody(Map<String, String> headers, Map<String, Object> body);

    public BankResponse getBank(Map<String, String> headers);

    public BeneficiaryNameResponse getBeneficiaryName(Map<String, String> headers, Map<String, Object> body);

}
