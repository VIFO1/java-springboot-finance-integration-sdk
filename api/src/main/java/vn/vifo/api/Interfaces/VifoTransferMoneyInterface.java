package vn.vifo.api.Interfaces;

import vn.vifo.api.Modules.DTO.TransferMoneyResponse;

import java.util.HashMap;
import java.util.List;

public interface VifoTransferMoneyInterface {

    public List<String> validateRequestInput(HashMap<String, String> headers, HashMap<String, Object> body);

    public TransferMoneyResponse createTransferMoney(HashMap<String, String> headers, HashMap<String, Object> body);
}