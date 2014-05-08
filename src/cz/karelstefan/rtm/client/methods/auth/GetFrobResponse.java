package cz.karelstefan.rtm.client.methods.auth;

import cz.karelstefan.rtm.client.ErrorResponse;
import cz.karelstefan.rtm.client.Response;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name="rsp")
public class GetFrobResponse extends Response {

    @XmlElement
    private String frob;

    public String getStatus() {
        return status;
    }

    public ErrorResponse getError() {
        return error;
    }

    public String getFrob() {
        return frob;
    }
}
