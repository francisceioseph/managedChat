package sample.model;

import net.jini.core.entry.Entry;

/**
 * Created by francisco on 15/04/15.
 */
public class UserInformationTuple implements Entry{
    public String username;
    public String senha;

    public UserInformationTuple() {

    }

    public UserInformationTuple(String senha, String username) {
        this.senha = senha;
        this.username = username;
    }
}
