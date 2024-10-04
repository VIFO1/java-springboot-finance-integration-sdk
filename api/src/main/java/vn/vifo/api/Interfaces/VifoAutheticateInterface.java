package vn.vifo.api.Interfaces;
import java.util.List;
import java.util.Map;

public interface VifoAutheticateInterface {
    public List<String> validateLoginInput(Map<String, String> headers, String username, String password);
    public Map<String, Object> buildLoginBody(String username, String password);
    public Map<String, Object> authenticateUser(Map<String, String> headers, String username, String password);
}
