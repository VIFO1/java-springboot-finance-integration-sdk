package vn.vifo.api.Interfaces;

import java.util.Map;
import java.util.List;

public interface VifoTransferMoneyInterface {

    public List<String> validateRequestInput(Map<String, String> headers, Map<String, Object> body);

    public Map<String, Object> createTransferMoney(Map<String, String> headers, Map<String, Object> body);
}