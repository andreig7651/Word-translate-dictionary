import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class Tema2
{
    public static void main(String[] args ) {
        Functions myfunc=new Functions();
        Map<String, ArrayList<Dict_entry>> tree_map = myfunc.getTree_map();
        String hash=null;
        File input = new File("D:\\Anul 2\\Programare orientata pe obiecte\\Tema 2");
        //salvam denumirile fisierelor din folderul parinte
        File[] filesList = input.listFiles();
        for (File f : filesList) {
            //cautam si prelucram doar fisierele care sunt json
            if (f.isFile() && f.getName().contains(".json")) {

                //lista corespunzatoare fiecarui hash
                ArrayList<Dict_entry> list=new ArrayList<Dict_entry>();
                try {

                    //extragem hash-ul din numele fisierului
                    for (String val: f.getName().split("_")){
                        hash=val;
                        break;
                    }
                    //citim din fisierul json
                    JsonElement fileElement = JsonParser.parseReader(new FileReader(f));
                    JsonArray wordArray = fileElement.getAsJsonArray();
                    //parcurgem lista de cuvinte din fisierul json
                    for (JsonElement myword : wordArray) {
                        Dict_entry myentry=new Dict_entry();
                        JsonObject wordJsonObject = myword.getAsJsonObject();
                        //citim campurile pentru cuvant
                        String word = wordJsonObject.get("word").getAsString();
                        String word_en = wordJsonObject.get("word_en").getAsString();
                        String type = wordJsonObject.get("type").getAsString();
                        //initializam campurile cuvantului
                        myentry.myword.word=word;
                        myentry.myword.word_en=word_en;
                        myentry.myword.type=type;

                        //citim si initializam lista singular
                        JsonArray singularArray = wordJsonObject.get("singular").getAsJsonArray();
                        for (JsonElement singularword : singularArray) {
                            String singular = singularword.getAsString();
                            myentry.myword.singular.add(singular);
                        }

                        //citim si initializam lista plural
                        JsonArray pluralArray = wordJsonObject.get("plural").getAsJsonArray();
                        for (JsonElement pluralword : pluralArray) {
                            String plural = pluralword.getAsString();
                            myentry.myword.plural.add(plural);
                        }

                        //citim din lista de definitii
                        JsonArray definitionArray = wordJsonObject.get("definitions").getAsJsonArray();
                        for (JsonElement definition : definitionArray) {
                            //citim si initializam fiecare element
                            JsonObject definitionJsonObject = definition.getAsJsonObject();
                            String dictionary = definitionJsonObject.get("dict").getAsString();
                            String dictionary_type = definitionJsonObject.get("dictType").getAsString();
                            String string_year = definitionJsonObject.get("year").getAsString();
                            int year = Integer.parseInt(string_year);
                            Definition def=new Definition();
                                def.dict_name = dictionary;
                                def.dict_type = dictionary_type;
                                def.year = year;
                                JsonArray textArray = definitionJsonObject.get("text").getAsJsonArray();
                                for (JsonElement myphrase : textArray) {
                                    String phrase = myphrase.getAsString();
                                    def.text.add(phrase);
                                }
                            myentry.mydefinition.add(def);

                        }
                        //punem lista actualizata de intrari la hash-ul cirespunzator
                        list.add(myentry);
                        tree_map.put(hash,list);
                        myfunc.setTree_map(tree_map);
                   }
                } catch (FileNotFoundException e) {
                    System.out.println("Can't find this");
                }
            }
    }

    //cateva teste pentru fiecare functie
    Word new_word=new Word();
    new_word.word="om";
    new_word.word_en="man";
    new_word.type="noun";
    new_word.singular.add("om");
    new_word.plural.add("oameni");
    myfunc.addWord(new_word,"ro");

    Word new_word1=new Word();
    new_word1.word="chat";
    new_word1.word_en="cat";
    new_word1.type="noun";
    new_word1.singular.add("chat");
    new_word1.plural.add("chats");
    myfunc.addWord(new_word1,"fr");

    myfunc.setTree_map(tree_map);
    //myfunc.afisare_map();

    myfunc.removeWord("copil","ro");
    myfunc.removeWord("jeu","fr");
    myfunc.setTree_map(tree_map);
    //myfunc.afisare_map();

    Definition new_definition=new Definition();
    new_definition.dict_name="DOOM";
    new_definition.dict_type="synonyms";
    new_definition.year=2022;
    new_definition.text.add("Persoana");
    new_definition.text.add("Individ");
    myfunc.addDefinitionForWord("om","ro",new_definition);
    myfunc.setTree_map(tree_map);
    //myfunc.afisare_map();

    Definition new_definition1=new Definition();
    new_definition1.dict_name="Dictionar explicativ";
    new_definition1.dict_type="synonyms";
    new_definition1.year=2019;
    new_definition1.text.add("Tip");
    new_definition1.text.add("Locuitor al planetei");
    myfunc.addDefinitionForWord("om","ro",new_definition1);
    myfunc.setTree_map(tree_map);
    //myfunc.afisare_map();

    Definition new_definition2=new Definition();
    new_definition2.dict_name="Dictionar explicativ";
    new_definition2.dict_type="synonyms";
    new_definition2.year=2019;
    new_definition2.text.add("Individ");
    new_definition2.text.add("Persoana");
    myfunc.addDefinitionForWord("om","ro",new_definition2);
    myfunc.setTree_map(tree_map);
    //myfunc.afisare_map();

    myfunc.removeDefinition("om","ro","DOOM");
    myfunc.removeDefinition("om","ro","Dictionar");
    myfunc.afisare_map();

    String translate,translate1;
    translate=myfunc.translateWord("mananca","ro","fr");
    translate1=myfunc.translateWord("garcon","fr","ro");
    System.out.println(translate1);
    System.out.println(translate);

    String translated_sentence,translated_sentence1;
    translated_sentence=myfunc.translateSentence("pisică mananca","ro","fr");
    translated_sentence1=myfunc.translateSentence("câine mananca","ro","fr");
    System.out.println(translated_sentence);
    System.out.println(translated_sentence1);

    ArrayList<String> translated_sentences=myfunc.translateSentences("pisică mananca","ro","fr");
    for (int i=0;i<translated_sentences.size();i++)
        System.out.println(translated_sentences.get(i));

    ArrayList<Definition>mydefinitions=myfunc.getDefinitionsForWord("câine","ro");
    for(Definition def:mydefinitions)
        System.out.println(def.year+":"+def.dict_name);

    ArrayList<Definition>mydefinitions1=myfunc.getDefinitionsForWord("chat","fr");
    for(Definition def:mydefinitions1)
        System.out.println(def.year + ":" + def.dict_name);

    myfunc.exportDictionary("fr");
    }
}