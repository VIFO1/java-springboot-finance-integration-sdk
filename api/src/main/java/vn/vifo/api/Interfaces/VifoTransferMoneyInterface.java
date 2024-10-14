package vn.vifo.api.Interfaces;

import java.util.Map;

import vn.vifo.api.Modules.Converters.TransferMoneyResponse;

import java.util.List;

public interface VifoTransferMoneyInterface {

    public List<String> validateRequestInput(Map<String, String> headers, Map<String, Object> body);

    public TransferMoneyResponse createTransferMoney(Map<String, String> headers, Map<String, Object> body);
}