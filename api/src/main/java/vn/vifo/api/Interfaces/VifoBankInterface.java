package vn.vifo.api.Interfaces;

import java.util.HashMap;
import java.util.List;

import vn.vifo.api.Modules.DTO.BankResponse;
import vn.vifo.api.Modules.DTO.BeneficiaryNameResponse;

public interface VifoBankInterface {
    public List<String> validateBody(HashMap<String, String> headers, HashMap<String, Object> body);

    public BankResponse getBank(HashMap<String, String> headers);

    public BeneficiaryNameResponse getBeneficiaryName(HashMap<String, String> headers, HashMap<String, Object> body);

}
