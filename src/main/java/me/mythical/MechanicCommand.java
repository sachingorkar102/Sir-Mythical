package me.mythical;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.EmbedBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;

//import javax.print.Doc;
import java.awt.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MechanicCommand extends Command {
    public void helpSender(CommandEvent e){
        EmbedBuilder eb = new EmbedBuilder();
        eb.setColor(main_class.helpcolor)
        .setTitle("Mechanic command Usage")
        .setDescription("**Used to get info on various skills and effects**")
        .addField("Aliases:","!m, !M, !mech, !Mech, !mechanic",false)
        .addField("to get info a mechanic use:","``!mechanic [mechanic name]``",false)
        .addField("to get info on effect use","``!mechanic effect [effect name]``",false);
        e.reply(eb.build());
    }
    public void effectManager(String word,CommandEvent e){
        if(word.equals("Explosion")){
            EmbedBuilder eb = new EmbedBuilder()
                    .setColor(main_class.mmcolor)
                    .setTitle("Explosion","https://www.mythicmobs.net/manual/doku.php/skills/effects/explosion")
                    .setDescription("Creates an explosion effect at the specified target. Will play the sound and the particle effects, but won't cause any damage to the target.")
                    .addField("Examples:","```yaml\n- effect:explosion @self ~onDamaged```",false)
                    .addField("Related Tutorial","[Let 'er rip! [MythicMobs Explosion Tutorial]](https://www.youtube.com/watch?v=O-6NrJAWwT0&list=PLj4aPW7DJDGi7d11DUI14tMurI6BvDj6o&index=10)",false);
            e.reply(eb.build());
            return;
        }
        if(word.equalsIgnoreCase("help")){
            helpSender(e);
            return;
        }
        if(word.equals("all")){
            EmbedBuilder eb = new EmbedBuilder()
                    .setTitle("Effects","https://www.mythicmobs.net/manual/doku.php/skills/effects/start")
                    .setDescription("List of all effects:\n```fix\n"+EFFECT_LIST.toString()+"```")
                    .setColor(main_class.mmcolor);
            e.reply(eb.build());
            return;
        }
        Document effectlistpage = null;
        try {
            effectlistpage = Jsoup.connect("https://www.mythicmobs.net/manual/doku.php/skills/effects/start").get();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        if (effectlistpage != null) {
            Elements name = effectlistpage.getElementsByClass("table sectionedit4");
            System.out.println(word);
            String effecttailURL = name.select("tbody").select(".col0").select(":contains("+word+")").first().select("[href]").attr("href");
            String effectURL = "https://www.mythicmobs.net"+effecttailURL;
            Document page = null;
            System.out.println(effectURL);
            try{
                page = Jsoup.connect(effectURL).get();
            }catch (Exception ex){
                System.out.println("failed to connect");
            }
            if(page != null){
                String Desc = "no description";
                try {
                    Desc = page.select("[id*=effect]").first().nextElementSibling().select("p").first().text();
                }catch (Exception ex){
                    System.out.println("failed to load Desc");
                }
//                System.out.println(Desc);
                Elements attri = null;
                try{
                    attri = page.select("[id*=attributes]").first().nextElementSiblings();
                }catch (Exception ex){
                    System.out.println("could not found any attributes");
                }
                EmbedBuilder eb = new EmbedBuilder();
                eb.setTitle(word,effectURL)
                .setColor(main_class.mmcolor)
                .setDescription("**Description:**\n"+Desc+"\n\n"+"**Attributes:**\n");
                if (attri != null) {

                    Elements rows = attri.select("tbody").select("tr");
                    for (Element row:rows) {
//                        System.out.println(row);
                        eb.addField(row.select(".col0").text()+" | "+row.select(".col1").text()
                                ,"default: "+row.select(".col3").text()+"\n"+row.select(".col2").text(),false);
                    }
                }
                String effectExampleDesc = "";
                Elements effectExamplecode = null;
                try { effectExampleDesc = page.select("[id*=examples]").first().nextElementSiblings().select("p").first().text(); }
                catch (Exception ex){ System.out.println("failed to load examples"); }

                try{effectExamplecode = page.select(".code");}
                catch (Exception ex){ System.out.println("could not load code"); }

                if(effectExamplecode != null) {
                    System.out.println(effectExampleDesc + effectExamplecode);
                    switch (effectExamplecode.size()) {
                        case 0:
                            System.out.println("no examples to search");
                            eb.addField("Examples:","No examples found",false);
                            break;
                        case 1:
                            System.out.println("1");
                            eb.addField("\nExamples:\n", effectExampleDesc
                                    + "\n" + "```yaml\n"
                                    + effectExamplecode.eachText().get(0)
                                    + "```", false);
                            break;
                        case 2:
                            System.out.println("2");
                            eb.addField("\nExamples:\n", effectExampleDesc
                                    + "\n" + "```yaml\n"
                                    + effectExamplecode.eachText().get(0)
                                    + "```" + "```yaml\n"
                                    + effectExamplecode.eachText().get(1)
                                    + "```", false);
                            break;
                        case 3:
                            System.out.println("3");
                            eb.addField("\nExamples:\n", effectExampleDesc
                                    + "\n" + "```yaml\n" + effectExamplecode.eachText().get(0)
                                    + "```" + "```yaml\n" + effectExamplecode.eachText().get(1)
                                    + "```" + "```yaml\n" + effectExamplecode.eachText().get(1)
                                    + "```", false);
                            break;
                        default:
                            System.out.println("default");
                            eb.addField("\nExamples:\n", effectExampleDesc
                                    + "\n" + "```yaml\n"
                                    + effectExamplecode.eachText().get(0)
                                    + "```", false);
                            break;
                    }
                }
                else {
                    System.out.println("4");
                    eb.addField("Examples:","error finding examples",false);
                }
                e.reply(eb.build());
            }
        }
    }
    public static final List<String> EFFECT_LIST = Arrays.asList("Black Screen","Block Mask","Block Unmask","Bloody Screen","Ender","Block Wave"
            ,"Ender Beam","Explosion","Firework","Flames","Geyser","Glow","Item Spray","Lightning","Particles","Particle Box"
            ,"Particle Line","Particle Orbital","Particle Ring","Particle Sphere","Particle Tornado","Skybox","Smoke","Smoke Swirl","Sound","Spin","all");
    public static List<String> effectMatchedList;
    public static List<String> effectSemiMatchedList;



    public MechanicCommand() {
        this.name = "Mechanic";
        this.aliases = new String[]{"m", "M", "mech", "Mech", "mechanic"};
    }

    @Override
    protected void execute(CommandEvent commandEvent) {
        String[] argument = commandEvent.getArgs().split(" ");
        String argMechName;
        if(argument.length != 0) {
            if (argument.length > 1) {
                argMechName = argument[0] + argument[1];
            } else {
                argMechName = argument[0];
            }
            if(argMechName.equalsIgnoreCase("") || argMechName.equalsIgnoreCase("help")){
                helpSender(commandEvent);
                return;
            }
            if(argMechName.equalsIgnoreCase("all")){
                EmbedBuilder eb = new EmbedBuilder()
                        .setTitle("All Mechanics")
                        .setDescription("list of all mechanic can be found here\nhttps://www.mythicmobs.net/manual/doku.php/skills/mechanics/start")
                        .setColor(main_class.helpcolor);
                commandEvent.reply(eb.build());
                return;
            }
            if(Arrays.asList("effects", "effect", "e").contains(argument[0])){
                if (argument.length > 2) {
                    argMechName = argument[1] + argument[2];
                }
                else {
                    argMechName = argument[1];
                }
                String finalArgMechName = argMechName;
                if(finalArgMechName.equalsIgnoreCase("") || finalArgMechName.equalsIgnoreCase("help")){
                    helpSender(commandEvent);
                    return;
                }
                effectSemiMatchedList = EFFECT_LIST.stream().filter(s -> s.toLowerCase().replace(" ","").contains(finalArgMechName.toLowerCase())).collect(Collectors.toList());
                effectMatchedList = EFFECT_LIST.stream().filter(s -> s.toLowerCase().replace(" ","").equals(finalArgMechName.toLowerCase())).collect(Collectors.toList());
                if(!effectMatchedList.isEmpty()){
                    effectManager(effectMatchedList.get(0),commandEvent);
                }
                else if(effectSemiMatchedList.size()>1){
                    EmbedBuilder eb = new EmbedBuilder().setTitle("multiple words found")
                            .setDescription("```"+effectSemiMatchedList.toString()+"```");
                    commandEvent.reply(eb.build());
                }
                else if(!effectSemiMatchedList.isEmpty()){
                    effectManager(effectSemiMatchedList.get(0),commandEvent);
                }
                return;
            }
            else {
                String mechanicUrl = "https://www.mythicmobs.net/manual/doku.php/skills/mechanics/start";
                String mechBaseUrl = "https://www.mythicmobs.net";
                Document mechanicPage = null;
                try {
                    mechanicPage = Jsoup.connect(mechanicUrl).get();
                } catch (IOException e) {
                    commandEvent.reply("error parsing url");
                }
                String finalArgMechName1 = argMechName;
                List<String> mechNamesList = mechanicPage.getElementsByTag("td").select(".col0").select("[href]").tagName("href")
                        .eachText().stream().map(String::toLowerCase)
                        .map(s -> s.replaceAll(" ", ""))
                        .collect(Collectors.toList()).stream().filter(s -> s.contains(finalArgMechName1.toLowerCase())).collect(Collectors.toList());
                List<String> equalMechName = mechNamesList.stream().filter(s -> s.equalsIgnoreCase(finalArgMechName1)).collect(Collectors.toList());
                System.out.println(mechNamesList);
                System.out.println(equalMechName);


                if (mechNamesList.isEmpty()) {
                    commandEvent.reply("``" + argMechName + "``" + "could not be found in database");
                } else if (!equalMechName.isEmpty() && equalMechName.get(0).equals(argMechName)) {
//                System.out.println("going in 1");
                    String mainMechLinkSyntax = "[href$=" + "/manual/doku.php/skills/mechanics/" + equalMechName.get(0) + "]";
                    String mainMechLink = mechBaseUrl + mechanicPage.getElementsByTag("td").select(mainMechLinkSyntax).first().attr("href");
                    System.out.println(mainMechLink);
                    System.out.println(equalMechName.get(0));
                    new mechanism_parser(equalMechName.get(0), mainMechLink, commandEvent);
                } else if (mechNamesList.size() == 1) {
//                System.out.println("going in 2");
                    String mainMechLinkSyntax = "[href$=" + mechNamesList.get(0) + "]";
                    String mainMechLink = mechBaseUrl + mechanicPage.getElementsByTag("td").select(mainMechLinkSyntax).first().attr("href");
                    System.out.println(mainMechLink);
                    System.out.println(mechNamesList.get(0));
                    new mechanism_parser(mechNamesList.get(0), mainMechLink, commandEvent);
                } else if (!mechNamesList.isEmpty() && equalMechName.isEmpty()) {
//                System.out.println("going in 3");
                    System.out.println("test2");
                    mechanism_parser.null_mech(commandEvent, mechNamesList);
                }
            }



        }else{
            System.out.println("failed");
        }

    }

}
    /*
        if(argument.length > 1){
            inputName1 = argument[0].toLowerCase() + argument[1].toLowerCase();
        }else{
            inputName1= argument[0].toLowerCase();
        }
        if(argument.length > 0 && !argument[0].equals("")) {
//            commandEvent.reply(argument[0]);
            String mechanicUrl = "https://www.mythicmobs.net/manual/doku.php/skills/mechanics/start";
            String mechMain = "https://www.mythicmobs.net";
            Document mechanicPage = null;
            try {
                mechanicPage = Jsoup.connect(mechanicUrl).get();
            } catch (IOException e) {
                commandEvent.reply("could Not pass url");
            }
            List<String> inputNameList = null;
            if (mechanicPage != null) {
                inputNameList = mechanicPage.getElementsByTag("td").select("[href]").tagName("href").eachText().stream().map(String::toLowerCase).map(s -> s.replaceAll(" ","")).collect(Collectors.toList()).stream().filter(s -> s.contains(inputName1)).collect(Collectors.toList());
            }
            System.out.println(inputNameList);
            assert inputNameList != null;
            if (inputNameList.isEmpty()) {
                commandEvent.reply("``" + inputName1 + "``" + "could not be found in database");
            } else {
                if (inputNameList.size() > 1 && !inputNameList.contains(inputName1)) {
                    EmbedBuilder ebdym = new EmbedBuilder();
                    ebdym.setColor(Color.darkGray);
                    ebdym.setTitle("Multiple Mechanic found\n");
                    ebdym.setDescription("```" + inputNameList + "```");
                    commandEvent.reply(ebdym.build());
                } else {

                    String inputName = inputNameList.get(0).replace(" ", "");
                    String inputName2 = "[href*=" + inputName + "]";
                    String mechLinks = mechanicPage.getElementsByTag("td").select(inputName2).first().attr("href");

                    String mechMainLink = mechMain + mechLinks;
                    Document mechPage = null;
                    try {
                        mechPage = Jsoup.connect(mechMainLink).get();
                    } catch (IOException e) {
                        System.out.println("failed");
                    }
//                    System.out.println(mechPage);
//                    System.out.println(mechMainLink);
                    String mechInfo1 = mechPage.select("[id*=mechanic]").get(0).nextElementSibling().select("p").text();
                    Elements mechTable = mechPage.getElementsByTag("h2").select("[id*=Attribute]").next();
                    Elements mechRow = mechTable.select("table").select("tbody").select("tr");
                    EmbedBuilder eb = new EmbedBuilder();
                    eb.setTitle(inputName, mechMainLink);
                    eb.setDescription("**Mechanic:**\n" + mechInfo1 + "\n" + "\n**Attributes:**\n");
                    int k = 0;
                    for (Element rows : mechRow) {
                        if (k > 10 || k>=rows.select("td").size()) {
                            eb.addField("\n``Too many attributes``", "``click on link for more info``\n", false);
                            break;
                        }
                        k++;
                        eb.addField("```" + rows.select("td").get(0).text() + "/" + rows.select("td").get(1).text() + "```", "> default: " + rows.select("td").get(3).text() + "\n" + "> " + rows.select("td").get(2).text() + "\n", false);
                    }
                    String exampleDesc = null;
                    exampleDesc = mechPage.select("[id^=example]").first().nextElementSiblings().select("p").text();

                    String exampleCode = "```" + mechPage.select("[id^=example]").first().nextElementSiblings().select("pre").text() + "```";
                    eb.addField("Examples:\n", exampleDesc + "\n\n" + exampleCode, false);
                    commandEvent.reply(eb.build());
                }

            }
        }else {
            System.out.println("dint work");
        }


    }


}

     */
