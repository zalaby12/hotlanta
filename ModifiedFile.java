import java.io.File;
import java.util.*;

public class ModifiedFile extends File {
    private LinkedList<Editor> edits;

    //constructor
    public ModifiedFile(String pathname) {
        super(pathname);
        edits = new LinkedList<Editor>();
    }

    public LinkedList<Editor> getEdits() {
       return edits;
    }

    public void updateEdits(String name, String email, Date time ) {
        Editor e = new Editor(name, email, time);
        this.edits.addFirst(e);
    }

    public Editor getMostRecentEditor(){
        return edits.getFirst();
    }
    public Date lastTimeModified(){
        Editor e = getMostRecentEditor();
        return e.getTimestamp();

    }

    public String lastEditorName() {
        Editor e = getMostRecentEditor();
        return e.getName();
    }

    public String lastEditorEmail() {
        Editor e = getMostRecentEditor();
        return e.getEmail();
    }

}