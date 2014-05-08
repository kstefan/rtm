package cz.karelstefan.rtm.client;

import javax.ws.rs.core.Form;

abstract public class Method {

    public abstract String getName();

    public Form getParams() {
        return new Form();
    }

}
