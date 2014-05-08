package cz.karelstefan.rtm.client;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;


public abstract class Response {

    @XmlAttribute(name = "stat")
    protected String status;

    @XmlElement(name = "err")
    protected ErrorResponse error;

    public String getStatus() {
        return status;
    }

    public ErrorResponse getError() {
        return error;
    }

}
