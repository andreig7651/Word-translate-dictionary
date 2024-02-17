import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Functions {

    //declaram colectia,de tip TreeMap,pentru a fi sortate hash-urile
    Map<String, ArrayList<Dict_entry>> tree_map = new TreeMap<String, ArrayList<Dict_entry>>();

    public Map<String, ArrayList<Dict_entry>> getTree_map() {
        return tree_map;
    }

    public void setTree_map(Map<String, ArrayList<Dict_entry>> tree_map) {
        this.tree_map = tree_map;
    }

    //metoda ce adauga un cuvant in dictionar
    boolean addWord(Word word, String language){
        for(Map.Entry<String, ArrayList<Dict_entry>> intrare : tree_map.entrySet())
        {
            if(intrare.getKey().equals(language)){
                for(Dict_entry entry:intrare.getValue())
                    if(entry.myword.equals(word))
                        return false;
                Dict_entry new_entry=new Dict_entry();
                new_entry.myword=word;
                intrare.getValue().add(new_entry);
                return true;
            }

        }
        return false;
    }

    //metoda ce sterge un cuvant din dictionar
    boolean removeWord(String word, String language){
        for(Map.Entry<String, ArrayList<Dict_entry>> intrare : tree_map.entrySet())
        {
            if(intrare.getKey().equals(language)){
                int count=0;
                for(Dict_entry entry:intrare.getValue()) {
                    if (entry.myword.word.equals(word)) {
                        intrare.getValue().remove(count);
                        return true;
                    }
                count++;
                }
            }

        }
        return false;
    }

    //metoda ce adauga o definitie pentru un cuvant
    boolean addDefinitionForWord(String word, String language, Definition definition){
        for(Map.Entry<String, ArrayList<Dict_entry>> intrare : tree_map.entrySet()) {
            if (intrare.getKey().equals(language)) {

                for (Dict_entry entry : intrare.getValue()) {
                    if (entry.myword.word.equals(word)) {
                        for (Definition new_def:entry.mydefinition)
                            if(new_def.dict_name.equals(definition.dict_name)){
                                int found=0;
                                for (String phrase : definition.text){
                                    for(String new_phrase:new_def.text)
                                        if (phrase.equals(new_phrase))
                                            found = 1;
                                    if(found==0)
                                        new_def.text.add(phrase);

                                }

                                return false;
                            }

                        entry.mydefinition.add(definition);
                        return true;
                    }

                }
            }


        }
        return false;
    }

    //metoda ce sterge un cuvant din dictionar
    boolean removeDefinition(String word, String language, String dictionary)
    {
        for(Map.Entry<String, ArrayList<Dict_entry>> intrare : tree_map.entrySet()) {
            if (intrare.getKey().equals(language)) {

                for (Dict_entry entry : intrare.getValue()) {
                    if (entry.myword.word.equals(word)) {
                        int count=0;
                        for (Definition def:entry.mydefinition)
                            if(def.dict_name.equals(dictionary)){
                                entry.mydefinition.remove(count);
                                count++;
                                return true;
                            }
                    }

                }
            }


        }
        return false;
    }

    //metoda ce traduce un cuvant
    String translateWord(String word, String fromLanguage, String toLanguage){
        for(Map.Entry<String, ArrayList<Dict_entry>> intrare : tree_map.entrySet()) {
            if (intrare.getKey().equals(fromLanguage)) {
                for (Dict_entry entry : intrare.getValue()) {
                    int counts=0;int singular=0;int plural=0,countp=0;
                    String en_word=null;
                    for(String form : entry.myword.singular) {
                        if (form.equals(word)) {
                            singular = 1;
                            en_word = entry.myword.word_en;
                            break;
                        } else {
                            counts++;
                        }
                    }

                    for(String form1 : entry.myword.plural) {
                        if (form1.equals(word)) {
                            plural = 1;
                            en_word = entry.myword.word_en;
                            break;
                        } else {
                            countp++;
                        }
                    }

                        for (Map.Entry<String, ArrayList<Dict_entry>> intrare1 : tree_map.entrySet())
                            if (intrare1.getKey().equals(toLanguage)){
                                for (Dict_entry entry1 : intrare1.getValue()) {
                                    if (entry1.myword.word_en.equals(en_word)) {

                                        if (singular == 1)
                                            return entry1.myword.singular.get(counts);

                                        if (plural == 1)
                                            return entry1.myword.plural.get(countp);

                                    }
                                }
                            }



                }
            }


        }
        return word;
    }

    //metoda ce traduce o propozitie
    String translateSentence(String sentence, String fromLanguage, String toLanguage){
        String translated=null;
        for (String word: sentence.split(" ")) {
            String translated_word=translateWord(word, fromLanguage, toLanguage);
            if(translated==null)
                translated=translated_word;
            else
               translated=translated+" "+translated_word;
        }
        return translated;
    }

    //metoda ce furnizeaza 3 variante de traducere pentru o propozitie
    ArrayList<String> translateSentences(String sentence, String fromLanguage, String toLanguage){
        ArrayList<String> sentences=new ArrayList<String>();
        for(int i = 0;i < 3;i++) {
            String translated = null;
            String en_word = null;
            for (String word : sentence.split(" ")) {
                for (Map.Entry<String, ArrayList<Dict_entry>> intrare : tree_map.entrySet()) {
                    if (intrare.getKey().equals(fromLanguage)) {
                        for (Dict_entry entry : intrare.getValue()) {
                            if (entry.myword.word.equals(word)) {
                                en_word = entry.myword.word_en;
                                for (Map.Entry<String, ArrayList<Dict_entry>> intrare1 : tree_map.entrySet()) {
                                    if (intrare1.getKey().equals(toLanguage)) {
                                        for (Dict_entry entry1 : intrare1.getValue()) {
                                            if (entry1.myword.word_en.equals(en_word)) {
                                                if (translated == null) {
                                                    for (Definition def : entry1.mydefinition)
                                                        if (def.dict_type.equals("synonyms"))
                                                            if(i < def.text.size())
                                                                translated = def.text.get(i);
                                                            else
                                                                return sentences;
                                                } else {
                                                    for (Definition def : entry1.mydefinition)
                                                        if (def.dict_type.equals("synonyms"))
                                                            if(i < def.text.size())
                                                                translated = translated + " " + def.text.get(i);
                                                            else
                                                                return sentences;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            sentences.add(translated);
        }

        return sentences;
    }

    //metoda ce intoarce o lista de definitii pentru un cuvant
    ArrayList<Definition> getDefinitionsForWord(String word, String language){
        ArrayList<Definition> definitions=new ArrayList<Definition>();
        ArrayList<Integer> vector=new ArrayList<Integer>();
        for (Map.Entry<String, ArrayList<Dict_entry>> intrare : tree_map.entrySet()) {
            if (intrare.getKey().equals(language)) {
                for (Dict_entry entry : intrare.getValue()) {
                    if (entry.myword.word.equals(word)) {
                        for(Definition def:entry.mydefinition){
                            vector.add(def.year);
                        }
                        Collections.sort(vector);
                        for (int i=0;i < vector.size();i++)
                            for(Definition def:entry.mydefinition){
                                if(vector.get(i)==def.year){
                                    definitions.add(def);
                                    vector.set(i,0);
                                }
                            }

                    }
                }
            }
        }

        return definitions;
    }

    //metoda ce afiseaza un dictionar in format json
    void exportDictionary(String language){
        ArrayList<String>words=new ArrayList<String>();
        JsonArray list=new JsonArray();
        for (Map.Entry<String, ArrayList<Dict_entry>> intrare : tree_map.entrySet()) {
            if (intrare.getKey().equals(language)) {
                for (Dict_entry entry : intrare.getValue()) {
                    words.add(entry.myword.word);
                }
                Collections.sort(words);
                for (String word:words)
                    for (Dict_entry entry : intrare.getValue())
                        if(entry.myword.word.equals(word)) {
                            JsonObject obj = new JsonObject();
                            obj.addProperty("word", entry.myword.word);
                            obj.addProperty("word_en", entry.myword.word_en);
                            obj.addProperty("type", entry.myword.type);
                            JsonArray singular = new JsonArray();

                            for (String word1 : entry.myword.singular)
                                singular.add(word1);
                            obj.addProperty("singular", String.valueOf(singular));

                            JsonArray plural = new JsonArray();
                            for (String word2 : entry.myword.plural)
                                plural.add(word2);
                            obj.addProperty("plural", String.valueOf(plural));

                            JsonArray definitions = new JsonArray();
                            ArrayList<Integer>years=new ArrayList<Integer>();
                            for (Definition def: entry.mydefinition){
                                years.add(def.year);
                            }
                            Collections.sort(years);
                            for(int year:years)
                                for (Definition def: entry.mydefinition)
                                    if(year==def.year)
                                        {
                                            JsonObject obj1=new JsonObject();
                                            obj1.addProperty("dict",def.dict_name);
                                            obj1.addProperty("dictType",def.dict_type);
                                            obj1.addProperty("year",def.year);
                                            JsonArray text=new JsonArray();
                                            for(String phrase:def.text)
                                                text.add(phrase);
                                            obj1.addProperty("text", String.valueOf(text));
                                            definitions.add(obj1);
                                        }
                            obj.addProperty("definitions", String.valueOf(definitions));
                            list.add(obj);

                            String out_file=language+"_dict_out.json";
                            try(FileWriter file=new FileWriter(out_file)) {
                                file.write(list.toString());
                                file.flush();
                            }catch (IOException e){
                                e.printStackTrace();
                            }
                        }


            }
        }
    }

    //metoda creata in plus de mine pentru a afisa colectia
    void afisare_map(){
        for(Map.Entry<String, ArrayList<Dict_entry>> intrare : tree_map.entrySet())
        {   System.out.println(intrare.getKey());
            for(Dict_entry entry:intrare.getValue()) {
                System.out.println("Cuvantul este: "+entry.myword.word);
                System.out.println("In engleza este: " +entry.myword.word_en);
                System.out.println("Tip: "+entry.myword.type);
                System.out.print("Singular: ");
                for (String singular : entry.myword.singular)
                    System.out.print(singular+" ");
                System.out.println();
                System.out.print("Plural: ");
                for (String plural : entry.myword.plural)
                    System.out.print(plural+" ");
                System.out.println();

                for(Definition def:entry.mydefinition) {
                    System.out.println("Nume dictionar: " + def.dict_name);
                    System.out.println("Tipul dictionarului: " + def.dict_type);
                    System.out.println("Anul aparitiei: " + def.year);
                    System.out.println("Deinfitii: ");
                    for (String phrase : def.text)
                        System.out.println(phrase);
                }
                System.out.println();

            }


        }
    }
}
