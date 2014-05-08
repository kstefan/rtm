package cz.karelstefan.rtm.service;

import cz.karelstefan.rtm.client.Client;
import cz.karelstefan.rtm.client.methods.auth.GetFrobMethod;
import cz.karelstefan.rtm.client.methods.auth.GetFrobResponse;

public class AuthService {

    private Client client;

    public AuthService(Client client) {
        this.client = client;
    }

    private String getFrob() {
        GetFrobMethod method = new GetFrobMethod();
        GetFrobResponse response = client.call(method, GetFrobResponse.class);
        return response.getFrob();
    }

    public String getAuthorizationUrl() {
        String frob = getFrob();
        return client.createAuthUrl(frob);
    }
}
