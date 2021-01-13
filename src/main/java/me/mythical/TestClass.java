package me.mythical;


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;


public class TestClass extends main_class{

    public static void main(String[] args) throws IOException {
        String modifierURL = "https://www.mythicmobs.net/manual/doku.php/databases/misc/damagemodifiers";
        Document dmPage = null;
        try{
            dmPage = Jsoup.connect(modifierURL).get();
        }catch (Exception ex){
            ex.printStackTrace();
            System.out.println("could not connect to modifier url");
        }
        if(dmPage != null){
            List<String> dmList = dmPage.select("#options").first().nextElementSibling().select("tbody").select(".col0").eachText();
            System.out.println(dmList);


        }
    }



}
