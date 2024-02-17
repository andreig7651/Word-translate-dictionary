import java.util.ArrayList;

public class Word {
    String word;
    String word_en;
    String type;
    ArrayList<String> singular;
    ArrayList<String> plural;
    public Word() {
        this.singular = new ArrayList<String>();
        this.plural = new ArrayList<String>();
    }
}
