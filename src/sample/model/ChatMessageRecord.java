package sample.model;

import net.jini.core.entry.Entry;

/**
 * Created by francisco on 15/04/15.
 */
public class ChatMessageRecord implements Entry{
    public String userFrom;
    public String userTo;
    public String content;
    public boolean checked;

    public ChatMessageRecord() {
    }

    public ChatMessageRecord(String userFrom, String userTo, String content, boolean checked) {
        this.userFrom = userFrom;
        this.userTo = userTo;
        this.content = content;
        this.checked = checked;
    }
}
