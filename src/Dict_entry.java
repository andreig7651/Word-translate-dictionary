import java.util.ArrayList;

public class Dict_entry {
    Word myword;
    ArrayList<Definition> mydefinition;

    public dict_entry() {
        this.myword = new Word();
        this.mydefinition=new ArrayList<Definition>();
    }

}
