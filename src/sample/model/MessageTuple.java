package sample.model;

import net.jini.core.entry.Entry;

/**
 * Created by francisco on 15/04/15.
 */
public class MessageTuple implements Entry{
    public String userFrom;
    public String userTo;
    public String content;
    public Boolean checked;

    public MessageTuple() {
    }

    public MessageTuple(String userFrom, String userTo, String content, boolean checked) {
        this.userFrom = userFrom;
        this.userTo = userTo;
        this.content = content;
        this.checked = checked;
    }

    @Override
    public String toString() {
        return String.format("{userFrom: %s\n userTo: %s\n content:%s\n checked: %b }", userFrom, userTo, content, checked);
    }
}
