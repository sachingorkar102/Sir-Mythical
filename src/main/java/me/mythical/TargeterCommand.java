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

public class TargeterCommand extends Command {
    public void targeterEmbedBuilder(String word, CommandEvent e, EmbedBuilder eb,Document targetPage){
        Element tr = targetPage.select("tbody").select("tr").select(":contains("+word+")").first();
        String targetName= tr.select(".col0").text();
        String targetShorthand= tr.select(".col1").text();
        String targetDesc= tr.select(".col2").text();
        if(targetShorthand.equals("")){
            targetShorthand = "NONE";
        }
        eb.setColor(main_class.mmcolor)
                .setTitle(targetName,URL)
                .setDescription("Shorthand: "+targetShorthand+"\n"+targetDesc);
        e.reply(eb.build());

    }

    public static final List<String> TARGETER_TYPES = Arrays.asList("Single-Entity","Multi-Entity","ThreatTable","Single-Location","Multi-Location","Special");
    public static List<String> matchedTargeterWord;
    public static List<String> matchedSemiTargeterWord;
    public static List<String> matchedSemiShorthandList;
    public static List<String> matchedShorthandList;
    public static final String URL = "https://www.mythicmobs.net/manual/doku.php/skills/targeters/start";

    public TargeterCommand(){
        this.name= "targeter";
        this.aliases= new String[]{"tar","target","targeter","Target"};
    }



    @Override
    protected void execute(CommandEvent e) {
        List<String> args = Arrays.asList(e.getArgs().split(" "));
        String word;
        if(args.size() > 1){
            word = args.get(0)+args.get(1);
        }
        else{
            word = args.get(0);
        }
        EmbedBuilder eb = new EmbedBuilder();


        if(word.equalsIgnoreCase("help") || word.equalsIgnoreCase("")){
            eb.setTitle("Targeter Command",URL)
            .setColor(main_class.helpcolor)
            .setDescription("**Used to get info on targeters**")
            .addField("aliases: "," !targeter , !tar , !Target , !target ",false)
            .addField("to get info on a single targeter use:","``!targeter [targeter name]``",false)
            .addField("to know more about target filter use:","``!targeter target filter``",false)
            .addField("to know more about target limits use:","``!targeter target limits``",false)
            .addField("to get info on a specific group of targeters use:","``!targeter [targeter group name]``\nvalid targeter group names are:\n```Single-Entity\nMulti-Entity\nThreatTable\nSingle-Location\nMulti-Location\nSpecial```",false);
            e.reply(eb.build());
//            return;
        }
        else if(word.equalsIgnoreCase("targetfilter")){
            eb.setTitle("Target Filters",URL)
            .setDescription("\n" +
                    "Target Filters allow you to filter out certain targets, and make targeters a lot more flexible.\n" +
                    "They are used with two options (available on ANY entity-targeter):\n" +
                    "\n" +
                    "    ignore=X\n" +
                    "    target=X\n" +
                    "\n" +
                    "For example, to make a targeter that will ignore any players or non-hostile mobs, you'd use this:\n" +
                    "``damage{a=20} @EntitiesInRadius{r=10;ignore=players,animals}``\n" +
                    "\n" +
                    "To make a targeter ONLY target players, you'd do something like this:\n" +
                    "``skill{s=ASkill} @EntitiesInRadius{r=5;target=players}\n``" +
                    "\n" +
                    "Possible filters include:\n" +
                    "\n" +
                    "```    animals (non-hostile mobs)\n" +
                    "    creative (ignored by default)\n" +
                    "    creatures (any type of sentient entity)\n" +
                    "    flyingmobs\n" +
                    "    monsters (hostile mobs)\n" +
                    "    NPCs (Citizens NPCs, ignored by default)\n" +
                    "    players\n" +
                    "    samefaction (mobs marked with the same faction type)\n" +
                    "    spectators (ignored by default)\n" +
                    "    vanilla\n" +
                    "    watermobs\n" +
                    "    More coming later…```\n" +
                    "\n" +
                    "You can also turn off specific filters by just adding the option targetXXXXX replacing XXXXX with the filter name, e.g. targetPlayers=false or targetcreative=true\n")
            .setColor(main_class.mmcolor);
            e.reply(eb.build());
        }
        else if(word.toLowerCase().contains("targetlimits")){
            eb.setColor(main_class.mmcolor)
            .setTitle("Target Limits",URL)
            .setDescription("\n" +
                    "All entity targeters also support target limits (as of v4.5). With this you can limit how many things you target, including the order in which they are selected.\n" +
                    "\n" +
                    "This is done with the options:\n" +
                    "``limit=#``\n" +
                    "``sort=X``\n" +
                    "Lets say you want your ability to only target the 2 nearest players within 30 yards. To do this, you'd simply set the limit 2 to and sort by nearest:\n" +
                    "``@PlayersInRadius{r=30;limit=2;sort=NEAREST}\n``" +
                    "\n" +
                    "Currently sort can have the following values:\n" +
                    "```\n" +
                    "NONE (usually sorts by how long the entity has existed)\n" +
                    "RANDOM\n" +
                    "NEAREST\n" +
                    "FURTHEST\n" +
                    "\n```");
            e.reply(eb.build());
        }
        else {
            Document targeterPage = null;
            try {
                targeterPage = Jsoup.connect(URL).get();
            } catch (Exception ex) {
                System.out.println("could not connect to targeter page");
            }
            if (targeterPage != null) {
                List<String> matchedTargeterList = TARGETER_TYPES.stream().filter(s -> s.toLowerCase().contains(word.toLowerCase())).collect(Collectors.toList());
                if (!matchedTargeterList.isEmpty()) {
                    String shorthand;
                    if (matchedTargeterList.get(0).equals("Special")) {
                        System.out.println("going with special targeter list");
                        Elements specialTargeterList = targeterPage.getElementById("entity_targeters")
                                .nextElementSiblings().select("h2").select(".sectionedit14").first().nextElementSibling().select("tbody").select("tr");
                        eb.setColor(main_class.mmcolor)
                                .setTitle("Special Targeter", URL);
                        for (Element row : specialTargeterList) {
                            shorthand = row.select(".col1").text();
                            if (shorthand.equals("")) {
                                shorthand = "NONE";
                            }
                            eb.addField(row.select(".col0").text()
                                    , "Shorthand: " + shorthand + "\n" + row.select(".col2").text()
                                    , false);
                        }
                        e.reply(eb.build());
                        return;
                    }
                    Elements targeterTypesList = targeterPage.getElementById("entity_targeters").nextElementSiblings().select("h3")
                            .select(":contains(" + word + ")").first().nextElementSibling().select("tbody").select("tr");
                    eb.setColor(main_class.mmcolor)
                            .setTitle(matchedTargeterList.get(0), URL);
                    for (Element row : targeterTypesList) {
                        shorthand = row.select(".col1").text();
                        if (shorthand.equals("")) {
                            shorthand = "NONE";
                        }
                        eb.addField(row.select(".col0").text()
                                , "Shorthand: " + shorthand + "\n" + row.select(".col2").text()
                                , false);
                    }
                    e.reply(eb.build());
                    return;
                }
                List<String> targetersList = targeterPage.getElementById("entity_targeters")
                        .nextElementSiblings().select("tbody").select("tr").select(".col0").eachText();
                List<String> shortHandList = targeterPage.getElementById("entity_targeters")
                        .nextElementSiblings().select("tbody").select("tr").select(".col1").eachText();
                matchedTargeterWord = targetersList.stream().filter(s -> s.equalsIgnoreCase(word)).collect(Collectors.toList());
                matchedSemiTargeterWord = targetersList.stream().filter(s -> s.toLowerCase().contains(word.toLowerCase())).collect(Collectors.toList());
                matchedSemiShorthandList = shortHandList.stream().filter(s -> s.toLowerCase().contains(word.toLowerCase())).collect(Collectors.toList());
                matchedShorthandList = shortHandList.stream().filter(s -> s.equalsIgnoreCase(word)).collect(Collectors.toList());
                System.out.println(matchedSemiShorthandList);
                System.out.println(matchedTargeterWord);
                System.out.println(matchedSemiTargeterWord);
                int d;
                String targeterDesc;
                if (!matchedSemiTargeterWord.isEmpty()) {
                    if (!matchedTargeterWord.isEmpty()) {
                        System.out.println("found main match");
                        targeterEmbedBuilder(matchedTargeterWord.get(0), e, eb, targeterPage);
                    } else if (matchedSemiTargeterWord.size() > 1) {
                        System.out.println("found multiple match");
                        targeterDesc = "";
                        for (d = 0; d < matchedSemiTargeterWord.size(); d++) {
                            targeterDesc = targeterDesc + matchedSemiTargeterWord.get(d) + "\n";
                        }
                        eb.setColor(main_class.mmcolor)
                                .setTitle("Multiple Targeters found")
                                .setDescription("```\n" + targeterDesc + "```");
                        e.reply(eb.build());
                    } else if (matchedSemiTargeterWord.size() == 1) {
                        System.out.println("going with semi matched targeter");
                        targeterEmbedBuilder(matchedSemiTargeterWord.get(0), e, eb, targeterPage);
                    }
                } else if (!matchedSemiShorthandList.isEmpty()) {

                    if (!matchedShorthandList.isEmpty()) {
                        System.out.println("found main match for shorthand");
                        targeterEmbedBuilder(matchedShorthandList.get(0), e, eb, targeterPage);
                    } else if (matchedSemiShorthandList.size() > 1) {
                        System.out.println("found multiple match for shorthand");
                        targeterDesc = "";
                        for (d = 0; d < matchedSemiShorthandList.size(); d++) {
                            targeterDesc = targeterDesc + matchedSemiTargeterWord.get(d) + "\n";
                        }
                        eb.setColor(main_class.mmcolor)
                                .setTitle("Multiple Targeters found")
                                .setDescription("```\n" + targeterDesc + "```");
                        e.reply(eb.build());
                    } else if (matchedSemiShorthandList.size() == 1) {
                        System.out.println("going with semi matched targeter for shorthand");
                        targeterEmbedBuilder(matchedSemiShorthandList.get(0), e, eb, targeterPage);
                    }
                } else {
                    System.out.println("failed to find word");
                    eb.setTitle("Could not find targeter")
                    .setColor(Color.red)
                    .setThumbnail("https://raw.githubusercontent.com/twitter/twemoji/master/assets/72x72/26a0.png")
                    .setDescription("Could not find targeter named``"+word+"``\nuse ``!targeter help`` for more info");
                    e.reply(eb.build());
                }


            }
        }
    }
}
