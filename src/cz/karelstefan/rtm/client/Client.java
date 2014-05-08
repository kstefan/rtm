package cz.karelstefan.rtm.client;

import org.apache.commons.codec.digest.DigestUtils;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class Client {

    private static final String SERVICE_URL = "https://api.rememberthemilk.com/services";
    private static final String REST_URL = SERVICE_URL + "/rest";
    private static final String AUTH_URL = SERVICE_URL + "/auth";
    private static final String PARAM_METHOD = "method";
    private static final String PARAM_API_KEY = "api_key";
    private static final String PARAM_API_SIG = "api_sig";
    private static final String PARAM_PERMS = "perms";
    private static final String PARAM_FROB = "frob";
    private static final String VALUE_PERMS = "delete";



    private String apiKey;
    private String secret;

    public Client(String apiKey, String secret) {
        this.apiKey = apiKey;
        this.secret = secret;
    }

    private String calculateHash(SortedMap<String, String> map) {
        String str = "";
        for (Map.Entry<String, String> entry: map.entrySet()) {
            str += entry.getKey();
            str += entry.getValue();
        }
        return DigestUtils.md5Hex(secret + str);
    }

    private String calculateHash(Form form) {
        TreeMap<String, String> map = new TreeMap<>();
        for(Map.Entry<String, List<String>> entry: form.asMap().entrySet()) {
            map.put(entry.getKey(), String.join("", entry.getValue()));
        }
        return calculateHash(map);
    }

    private String encodeUrlParam(String param) {
        try {
            return URLEncoder.encode(param, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException(e);
        }
    }

    public String createAuthUrl(String frob) {
        TreeMap<String, String> params = new TreeMap<>();
        params.put(PARAM_API_KEY, apiKey);
        params.put(PARAM_PERMS, VALUE_PERMS);
        params.put(PARAM_FROB, frob);
        String hash = calculateHash(params);
        params.put(PARAM_API_SIG, hash);

        StringBuilder str = new StringBuilder();
        for (Map.Entry<String, String> entry: params.entrySet()) {
            if (str.length() == 0) {
                str.append(AUTH_URL);
                str.append("?");
            } else {
                str.append("&");
            }
            str.append(encodeUrlParam(entry.getKey()));
            str.append("=");
            str.append(encodeUrlParam(entry.getValue()));
        }
        return str.toString();
    }


    public <T> T call(Method method, Class<T> responseType) {
        javax.ws.rs.client.Client client = ClientBuilder.newClient();
        Form params = method.getParams();
        params.param(PARAM_METHOD, method.getName());
        params.param(PARAM_API_KEY, apiKey);
        String hash = calculateHash(params);
        params.param(PARAM_API_SIG, hash);
        return client.target(REST_URL)
                .request(MediaType.TEXT_XML)
                .post(Entity.entity(params, MediaType.APPLICATION_FORM_URLENCODED_TYPE), responseType);
    }
}
