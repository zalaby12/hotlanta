package hotlanta;


import java.util.*;

public class Editor {
    private String name;
    private String email;
    private Date timestamp;

    public Editor(String n, String e, Date t) {
        this.name = n;
        this.email = e;
        this.timestamp = t;
    }

    public String getName() {
        return this.name;
    }

    public String getEmail() {
        return this.email;
    }

    public Date getTimestamp() {
        return this.timestamp;
    }

}
