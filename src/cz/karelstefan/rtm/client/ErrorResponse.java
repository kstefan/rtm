package cz.karelstefan.rtm.client;

import javax.xml.bind.annotation.XmlAttribute;

public class ErrorResponse {

    @XmlAttribute
    private Integer code;

    @XmlAttribute(name="msg")
    private String message;

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
