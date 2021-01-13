package me.mythical;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.EmbedBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ConditionCommand extends Command {
    public ConditionCommand() {
        this.name = "condition";
        this.aliases = new String[]{"conditions","c","con","condition"};
    }
    @Override
    protected void execute(CommandEvent event) {
        List<String> args = Arrays.asList(event.getArgs().split(" "));
        String searchWord;
        EmbedBuilder eb = new EmbedBuilder();
        if(args.isEmpty() || args.get(0).equalsIgnoreCase("") || args.get(0).equalsIgnoreCase("help")){
                    eb.setColor(main_class.helpcolor)
                    .setTitle("Condition Command")
                    .setDescription("**Used to get info on specific condition**")
                    .addField("Aliases:","``conditions , c , con , condition``",false)
                    .addField("Usage:","``!condition [condition name]``to get info on specific condition\nor\n``!condition all``to get link for condtion page",false);

            event.reply(eb.build());
            return;
        }
        if(args.size()>1){
            searchWord = args.get(0)+args.get(1);
        }
        else if(args.size()>2){
            searchWord = args.get(0)+args.get(1)+args.get(2);
        }
        else{
            searchWord = args.get(0);
        }
        if(searchWord.equalsIgnoreCase("all")){
            eb.setColor(main_class.mmcolor)
            .setDescription("here is the list of all conditions\nhttps://www.mythicmobs.net/manual/doku.php/conditions/start")
            .addField("Related Tutorial:","[On One Condition [Mythic Mobs Conditions Tutorial]](https://www.youtube.com/watch?v=XF7kvkn5w9Y)",false);
            event.reply(eb.build());
            return;
        }
        Document test = null;
        try{
            String Url = "https://www.mythicmobs.net/manual/doku.php/conditions/start";
            test = Jsoup.connect(Url).get();
        }catch (Exception e){
            System.out.println("failed");
        }
        if(test != null) {
            Elements conditionList = test.select("h2").select("#conditions").first().nextElementSibling().select(".col0");
            List<String> semiMatchedList = conditionList.eachText().stream().map(String::toLowerCase)
                    .filter(s -> s.contains(searchWord.toLowerCase())).collect(Collectors.toList());
            List<String> matchedList = conditionList.eachText().stream().map(String::toLowerCase)
                    .filter(s -> s.equals(searchWord.toLowerCase())).collect(Collectors.toList());
            String conditionEndURL;
            String conditionMainURL;
            String condtionStartURL = "https://www.mythicmobs.net";




            if(!matchedList.isEmpty()){
                System.out.println("going in matched list");
                conditionEndURL = conditionList.select(":contains(" + matchedList.get(0) + ")").first().select("[href]").attr("href");
                conditionMainURL = condtionStartURL+conditionEndURL;
                conEmbedBuilder(matchedList.get(0),event,conditionMainURL);
            }
            else if(semiMatchedList.size() > 1){
                System.out.println("going with multiple words");
                EmbedBuilder embedBuilder = new EmbedBuilder();
                embedBuilder.setTitle("Multiple matching words found")
                .setColor(main_class.mmcolor);
                embedBuilder.setDescription("```css\n"+semiMatchedList+"```");
                event.reply(embedBuilder.build());
            }
            else if(semiMatchedList.size() == 1){
                System.out.println("going with semi list");
                conditionEndURL = conditionList.select(":contains(" + semiMatchedList.get(0) + ")").first().select("[href]").attr("href");
                conditionMainURL = condtionStartURL+conditionEndURL;
                conEmbedBuilder(semiMatchedList.get(0),event,conditionMainURL);
            }
            else if(matchedList.isEmpty() && semiMatchedList.isEmpty()){
                System.out.println("going with could not found");
                event.reply("Could not find "+"``"+searchWord+"``"+" in condition list\nPlease refer https://www.mythicmobs.net/manual/doku.php/conditions/start");
            }
            else {
                System.out.println("failed to execute");
            }


        }

    }
    public void conEmbedBuilder(String word,CommandEvent event,String wordUrl){
        EmbedBuilder conBuilder = new EmbedBuilder();
        Document conditionSearchedPage = null;
        try {
            conditionSearchedPage = Jsoup.connect(wordUrl).get();
        } catch (Exception e) {
            System.out.println("failed");
        }
        if(word.equalsIgnoreCase("variableisset")){
           conBuilder.setColor(main_class.mmcolor)
           .setDescription("**Condition:**\n Checks if the given variable is set.")
           .setTitle("variableisset",wordUrl)
           .addField("Examples","```yaml\n"+"Conditions:\n" +
                   "- variableisset target.dazed true"+"```",false);
           event.reply(conBuilder.build());
           return;
        }
//        System.out.println(conditionSearchedPage);
        if (conditionSearchedPage != null) {
            String conDesc = conditionSearchedPage.select("h1").select(".sectionedit8").first().nextElementSibling().text();
//            System.out.println(conDesc);
            Elements conTable = conditionSearchedPage.select("#attributes").next().select("table");
//            System.out.println(conTable);
            Elements conTableRows = conTable.first().select("tr");
//            System.out.println(conTableRows);
            conBuilder.setTitle(word, wordUrl);
            conBuilder.setDescription("**Condition:**\n"+conDesc+"\n\n**Attributes:**\n");
            for (Element row : conTableRows) {
                if (!row.attr("class").contains("row0") && !row.text().equals("")) {
                    String conTableColumn0 = row.select(".col0").text();
                    String conTableColumn1 = row.select(".col1").text();
                    String conTableColumn2 = row.select(".col2").text();
                    conBuilder.addField(conTableColumn0 + " / " + "``"+conTableColumn1+"``", conTableColumn2, false);
                }

            }
            String examples = conditionSearchedPage.select("#examples").first().nextElementSiblings().text();
            conBuilder.addField("Examples:","```yaml\n"+examples+"```",false).setColor(main_class.mmcolor);
            System.out.println(examples);
            event.reply(conBuilder.build());
            System.out.println("success");
        }
    }
}
