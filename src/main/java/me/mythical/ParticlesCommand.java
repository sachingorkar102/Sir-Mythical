package me.mythical;

import net.dv8tion.jda.api.EmbedBuilder;
//import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
//import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import sun.plugin2.message.Message;

import java.awt.*;
import java.io.IOException;
//import java.lang.reflect.Field;
//import java.util.List;
//import java.util.Optional;
import java.util.stream.Collectors;


public class ParticlesCommand extends ListenerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(ParticlesCommand.class);

    public static void main(String[] args) {

/*
        String searchWord = "crit";
        String url = "https://www.mythicmobs.net/manual/doku.php/skills/effects/particles/types";
        Document doc = Jsoup.connect(url).get();
        String arg2 = doc.getElementsByTag("h3").eachText().stream()
                .filter(s -> s.contains(searchWord))
                .collect(Collectors.toList()).get(0);
        Element title = doc.getElementById(arg2).nextElementSibling();
        Elements table1=title.selectFirst("thead").select("th");
        Element table2=title.selectFirst("tbody").select("td").get(1);
        int i = 0;
        for (Element test2 : table1) {
            System.out.println("this is test:" + test2.text() +  "   "  + "   " + title.selectFirst("tbody").select("td").get(i).text());
            i++;
            if(i == table1.stream().count()){
                break;
            }
        }

        /*
        if (!title2.isEmpty()) {


            try {
                String title3 = title2.get(0);
                Element title = doc.getElementById(title3).nextElementSibling();
                List<String> table1 = title.selectFirst("table").selectFirst("thead").select("th").eachText();
                List<String> table2 = title.selectFirst("table").selectFirst("tbody").select("td").eachText();
                System.out.println(table1);
                System.out.println(table2);
                System.out.println(title2);
                System.out.println(title3);
                int test = 0;
                while (test <= table1.size()) {
                    System.out.println(table1.get(test) + "  ==>  " + table2.get(test));
                    test++;
                    if (test == table1.size()) {
                        break;
                    }
                }
            }
            catch (Exception IndexOutOfBoundsException) {
                System.out.println(false);
            }


        }

         */
    }
    public void onMessageReceived(MessageReceivedEvent e){
        String[] args = e.getMessage().getContentRaw().split(" ");
        if(!e.getAuthor().isBot() && args[0].equals("!p") && args.length >= 1) {
//            e.getChannel().sendMessage(args[0]).queue();
//            String searchWord = "crit";
            String url = "https://www.mythicmobs.net/manual/doku.php/skills/effects/particles/types";
            Document doc = null;
            try {
                doc = Jsoup.connect(url).get();
            } catch (IOException ioException) {
                System.out.println("false");

            }
            String arg2 = doc.getElementsByTag("h3").eachText().stream()
                    .filter(s -> s.contains(args[1]))
                    .collect(Collectors.toList()).get(0);
            Element title = doc.getElementById(arg2).nextElementSibling();
            Elements table1 = title.selectFirst("thead").select("th");
//            Element table2 = title.selectFirst("tbody").select("td").get(1);
            EmbedBuilder eb = new EmbedBuilder();
            eb.setColor(Color.red);
            int i = 0;
            for (Element test2 : table1) {
                eb.addField(test2.text(), title.selectFirst("tbody").select("td").get(i).text(), true);
//                eb.addBlankField(true);
                i++;
                if (i == (long) table1.size()) {
                    break;
                }

            }
            String embedImage = doc.getElementById(arg2).nextElementSibling().selectFirst("a").selectFirst("img[src~=(?i)\\.(png|jpe?g|gif)]").attr("src");
            eb.setImage("https://www.mythicmobs.net" + embedImage);
            eb.setTitle(arg2.replace("_"," "),url + "#" + arg2);
            e.getChannel().sendMessage(eb.build()).queue();
            System.out.println("done");
        }

    }
}