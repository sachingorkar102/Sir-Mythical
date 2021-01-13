package me.mythical;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.EmbedBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.awt.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;



public class TypesCommand extends Command {
    private void allDamagedModifier(Document dmPage, EmbedBuilder eb, CommandEvent typeEvent,String URL,List<String> list) {
        eb.setTitle("All Damage Modifier",URL)
        .setColor(main_class.mmcolor);
        String Desc;
        int c;
        for (c=0;c<list.size();c++){
            Desc = dmPage.select("#options").first().nextElementSibling().select("tbody")
                    .select(".col0").select(":contains("+list.get(c)+")").first().nextElementSiblings().first().text();
            eb.addField(list.get(c),Desc,false);
        }
        typeEvent.reply(eb.build());
    }
    public void dmEmbedBuilder(String word,Document page,CommandEvent e,EmbedBuilder eb,String URL){
        String Desc = page.select("#options").first().nextElementSibling().select("tbody")
                .select(".col0").select(":contains("+word+")").first().nextElementSiblings().first().text();
        eb.setTitle(word,URL)
        .setColor(main_class.mmcolor)
        .setDescription(Desc);
        e.reply(eb.build());
    }
    public void typeEmbedBuilder(String titleName,String titleUrl,String Description,CommandEvent typeEvent){
        EmbedBuilder typeBuilder = new EmbedBuilder();
        typeBuilder.setColor(main_class.mmcolor);
        typeBuilder.setTitle(titleName,titleUrl);
        typeBuilder.setDescription(Description);
        typeEvent.reply(typeBuilder.build());
    }
    public void typeEmbedBuilder(Document doc, CommandEvent typeEvent, String word){
        Element title = doc.getElementById(word).nextElementSibling();
        Elements table1 = title.selectFirst("thead").select("th");
//        Element table2 = title.selectFirst("tbody").select("td").get(1);
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
        String embedImage = doc.getElementById(word).nextElementSibling().selectFirst("a").selectFirst("img[src~=(?i)\\.(png|jpe?g|gif)]").attr("src");
        eb.setImage("https://www.mythicmobs.net" + embedImage);
        eb.setColor(main_class.mmcolor);
        eb.setTitle(word.replace("_"," "),"https://www.mythicmobs.net/manual/doku.php/skills/effects/particles/types" + "#" + word);
        typeEvent.reply(eb.build());

        System.out.println("done");
    }
    public void typeEmbedBuilder(CommandEvent typeEvent, String a, Document doc) {
        System.out.println(a);
        List<String> enchSet = doc.select("#available_enchantments").first().nextElementSiblings()
                .select("p").select(":contains("+a+")").first().nextElementSiblings().first().select("div").eachText();
        EmbedBuilder enchEmbed = new EmbedBuilder();
        enchEmbed.setColor(main_class.mmcolor);
        enchEmbed.setTitle(a,"https://www.mythicmobs.net/manual/doku.php/databases/items/enchantments");
        enchEmbed.setThumbnail("https://static.wikia.nocookie.net/minecraft_gamepedia/images/5/55/Enchanted_Book.gif/revision/latest?cb=20200428014446");
        enchEmbed.setDescription(enchSet.toString().replace("]","").replace("[","").replace(", ","\n"));
        typeEvent.reply(enchEmbed.build());
    }
    public void typeEmbedBuilder(List<String> a,CommandEvent typeEvent){
        EmbedBuilder nullBuilder = new EmbedBuilder();
        nullBuilder.setColor(main_class.mmcolor);
        nullBuilder.setTitle("Multiple enchantments found", "https://www.mythicmobs.net/manual/doku.php/databases/items/enchantments");
        nullBuilder.setDescription("``"+ a.toString() +"``");
        nullBuilder.setThumbnail("https://static.wikia.nocookie.net/minecraft_gamepedia/images/5/55/Enchanted_Book.gif/revision/latest?cb=20200428014446");
        typeEvent.reply(nullBuilder.build());
    }
    public void helpSender(CommandEvent e){
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Types Command")
                .setColor(main_class.helpcolor)
                .setThumbnail("https://emojipedia-us.s3.dualstack.us-west-1.amazonaws.com/thumbs/120/google/274/question-mark_2753.png")
                .addField("Aliases:","``!types , !t , !T , !Types , !Type , !type , !TYPE``",false)
                .addField("Usage:","``!types [type] [name/all]``" +
                        "\n**[type]**\n" +
                        "valid groups/types in mythic are:" +
                        "\n```particles" +
                        "sound\n" +
                        "enchantment\n" +
                        "damage modifier or dm\n" +
                        "banner layer\n" +
                        "more coming soon\n```" +
                        "\n**[name/all]**\n" +
                        "Option for that specific type\nfor example\n" +
                        "``!type enchantment unbreaking``\n" +
                        "``!type particles lava``\n",false);
        e.reply(eb.build());
    }

    public TypesCommand() {
        this.name = "types";
        this.aliases = new String[]{"types", "t", "T", "Types", "Type", "type", "TYPE"};
    }


    @Override
    protected void execute(CommandEvent typeEvent) {
        List<String> typeArgs = Arrays.asList(typeEvent.getArgs().split(" "));
        String firstTypeArg;
        if(typeArgs.isEmpty() || typeArgs.get(0).equalsIgnoreCase("help") || typeArgs.get(0).equalsIgnoreCase("")){
            helpSender(typeEvent);
            return;
        }
        if (typeArgs.size() > 2) {
            firstTypeArg = typeArgs.get(1) + typeArgs.get(2);
        } else {
            firstTypeArg = typeArgs.get(1);
        }
        String titleName;
        String titleUrl;
        String description;
        if(Arrays.asList("help","","helpme").contains(typeArgs.get(0))){
            helpSender(typeEvent);
        }
        else if (Arrays.asList("sound", "sounds", "s").contains(typeArgs.get(0)) && firstTypeArg != null) {
            Document soundUrl = null;
            try {
                soundUrl = Jsoup.connect("https://www.digminecraft.com/lists/sound_list_pc.php").get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            String soundFilter = firstTypeArg.toLowerCase();

            List<String> soundUrlList = soundUrl.select("td").eachText().stream().map(String::toLowerCase)
                    .filter(s -> s.contains(soundFilter)).collect(Collectors.toList());
            if (soundUrlList.isEmpty()) {
                typeEvent.reply("no sound with name" + firstTypeArg + "found");
                System.out.println("no sound found");
//                return;
            } else if (Arrays.asList("all", "ALL", "All").contains(firstTypeArg)) {
                System.out.println("listing all sounds");
                titleName = "All sounds";
                titleUrl = "";
                description = "https://www.digminecraft.com/lists/sound_list_pc.php";
                typeEmbedBuilder(titleName, titleUrl, description, typeEvent);
            } else if (soundUrlList.size() < 30) {
                System.out.println("listing sounds less then 30");
                titleName = "Sound list";
                titleUrl = "https://www.digminecraft.com/lists/sound_list_pc.php";
                description = "```fix\n" + soundUrlList.toString().replace("[", "")
                        .replace("]", "").replace(",", "\n") + "```";
                typeEmbedBuilder(titleName, titleUrl, description, typeEvent);
            } else if (soundUrlList.size() > 30) {
                System.out.println("listing sounds greater then 30");
                List<String> soundLimit = soundUrlList.stream().limit(10).collect(Collectors.toList());
                titleName = "Sound list";
                titleUrl = "https://www.digminecraft.com/lists/sound_list_pc.php";
                description = "```fix\n" + soundLimit.toString().replace("[", "")
                        .replace("]", "").replace(", ", "\n") + "```" + "\n**List too big click link for more info**";
                typeEmbedBuilder(titleName, titleUrl, description, typeEvent);
//            String soundList = soundLimit.toString().replace("[","").replace("]","").replace(",","\n");
//            System.out.println(soundList);
            } else {
                System.out.println("error finding sounds");
            }
        } else if (Arrays.asList("particle", "particles", "p").contains(typeArgs.get(0)) && firstTypeArg != null) {
//            System.out.println(firstTypeArg);
            String url = "https://www.mythicmobs.net/manual/doku.php/skills/effects/particles/types";
            Document doc = null;
            try {
                doc = Jsoup.connect(url).get();
            } catch (IOException ioException) {
                System.out.println("could not parse particles url");

            }
            List<String> particleListWords = doc.getElementsByTag("h2").first().nextElementSiblings().select("h3").eachText().stream()
                    .filter(s -> s.contains(firstTypeArg))
                    .collect(Collectors.toList());
            List<String> particleEqualWords = particleListWords.stream().filter(s -> s.equals(firstTypeArg)).collect(Collectors.toList());
            System.out.println(particleListWords);
            System.out.println(particleEqualWords);
            if (particleListWords.isEmpty()) {
                typeEvent.reply("no particle with name" + " " + firstTypeArg + " " + "found");
            } else if (particleEqualWords.size() == 1) {
                typeEmbedBuilder(doc, typeEvent, particleEqualWords.get(0));
                System.out.println("parsing equal words");
            } else if (particleEqualWords.isEmpty() && particleListWords.size() > 1) {
                EmbedBuilder eb = new EmbedBuilder();
                eb.setTitle("multiple words found");
                eb.setDescription("```" + particleListWords + "```");
                eb.setColor(new Color(0x00486f));
                typeEvent.reply(eb.build());
                System.out.println("multiple words sent");
            } else if (particleEqualWords.isEmpty() && particleListWords.size() < 2) {
                typeEmbedBuilder(doc, typeEvent, particleListWords.get(0));
                System.out.println("parsing wordlist");
            } else {
                System.out.println("event failed");
            }

        } else if (Arrays.asList("enchantment", "enchant", "e").contains(typeArgs.get(0)) && typeArgs.size() > 1 && firstTypeArg != null) {
//            firstTypeArg = firstTypeArg.toLowerCase();
            String eTypeArg1;
            String eTypeArg2;
            if (typeArgs.size() > 2){
                eTypeArg1 = typeArgs.get(1)+" "+typeArgs.get(2);
                eTypeArg2 = typeArgs.get(1)+"_"+typeArgs.get(2);
            }
            else {
                eTypeArg1 = typeArgs.get(1);
                eTypeArg2 = typeArgs.get(1);
            }
            Document enchPage = null;
            try {
                enchPage = Jsoup.connect("https://www.mythicmobs.net/manual/doku.php/databases/items/enchantments").get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (enchPage == null) {
                System.out.println("could not load document");
                return;
            }
            Elements OGenchantmentName = enchPage.select("#available_enchantments").first().nextElementSiblings().select("em");
            Elements PLenchantmentName = enchPage.select("#available_enchantments").first().nextElementSiblings().select("strong");
            List<String> matchedEnchList1 = OGenchantmentName.eachText().stream().map(String::toLowerCase).filter(s -> s.contains(eTypeArg1.toLowerCase())).collect(Collectors.toList());
            List<String> matchedEnchList2 = PLenchantmentName.eachText().stream().map(String::toUpperCase).filter(s -> s.contains(eTypeArg2.toUpperCase())).collect(Collectors.toList());
            List<String> equalmatchedList1 = OGenchantmentName.eachText().stream().map(String::toLowerCase).filter(s -> s.equals(eTypeArg1.toLowerCase())).collect(Collectors.toList());
            List<String> equalmatchedList2 = OGenchantmentName.eachText().stream().map(String::toUpperCase).filter(s -> s.equals(eTypeArg1.toUpperCase())).collect(Collectors.toList());
            String matchedEnchSyntax;
            System.out.println(matchedEnchList1 + "\n" + matchedEnchList2);
            if(!equalmatchedList1.isEmpty()){
                matchedEnchSyntax = ":contains(" + equalmatchedList1.get(0) + ")";
//                System.out.println(matchedEnchSyntax);
                String OGtoPLName = OGenchantmentName.select(matchedEnchSyntax).parents().first().parents().first().parents().first().previousElementSiblings().first().text();
//                System.out.println(OGtoPLName);
                typeEmbedBuilder(typeEvent, OGtoPLName, enchPage);
            }
            else if(!equalmatchedList2.isEmpty()){
                System.out.println("going in last");
                String PLmain = equalmatchedList2.get(0);
                System.out.println(PLmain);
                typeEmbedBuilder(typeEvent, PLmain, enchPage);
            }
            else if (matchedEnchList1.size() > 1) {
                typeEmbedBuilder(matchedEnchList1,typeEvent);
            }
            else if (!matchedEnchList1.isEmpty()) {
                matchedEnchSyntax = ":contains(" + matchedEnchList1.get(0) + ")";
                System.out.println(matchedEnchSyntax);
                String OGtoPLName = OGenchantmentName.select(matchedEnchSyntax).parents().first().parents().first().parents().first().previousElementSiblings().first().text();
                System.out.println(OGtoPLName);
                typeEmbedBuilder(typeEvent, OGtoPLName, enchPage);
            }
            else if (matchedEnchList2.size() > 1) {
                typeEmbedBuilder(matchedEnchList2,typeEvent);
            }
            else if (!matchedEnchList2.isEmpty()) {
                System.out.println("going in last");
                String PLmain = matchedEnchList2.get(0);
                System.out.println(PLmain);
                typeEmbedBuilder(typeEvent, PLmain, enchPage);
            }
            else{
                typeEvent.reply("couldnt found "+firstTypeArg+" in database");
            }


//            System.out.println("test");
        }
        else if (Arrays.asList("banner", "bannerlayers", "layer").contains(typeArgs.get(0).toLowerCase())){
            String bannerURL = "https://www.mythicmobs.net/manual/doku.php/databases/items/bannerlayers";
            EmbedBuilder bannerBuilder = new EmbedBuilder()
                    .setTitle("Banner Layers",bannerURL)
                    .setColor(new Color(0x00486f))
                    .setDescription("To make complex banner items in MythicMobs, you can use the following syntax. There is no hard limit placed by MythicMobs on the number of banner layers you can use and you can go past the vanilla maximum of 6 layers set by Minecraft using this. However going past 6 layers may cause unusual behaviour and/or lag.")
                    .addField("Syntax:","```yaml\n"+"Banner:\n" +
                            "  Id: <banner/shield>\n" +
                            "  Options:\n" +
                            "    Color: <BASE COLOR>\n" +
                            "  BannerLayers:\n" +
                            "  - <color> <pattern>\n" +
                            "  - <color> <pattern>"+"```",false)
                    .addField("Patterns:","a list of banner pattern types can be found [HERE](https://www.mythicmobs.net/manual/doku.php/databases/items/bannerlayers)\n",false)
                    .addField("Example:","these pattern types can be used as given below"+"```yaml\n"+"guard_shield:\n" +
                            "  Id: banner\n" +
                            "  Options:\n" +
                            "    Color: WHITE\n" +
                            "  BannerLayers:\n" +
                            "  - GREEN RHOMBUS_MIDDLE\n" +
                            "  - BROWN BORDER\n" +
                            "  - BLACK STRIPE_MIDDLE\n" +
                            "  - BROWN SQUARE_BOTTOM_LEFT\n" +
                            "  - BROWN SQUARE_BOTTOM_RIGHT\n" +
                            "  - ORANGE STRIPE_CENTER\n" +
                            "  - BROWN HALF_HORIZONTAL\n"+"```",false)
                    .setImage("https://cdn.discordapp.com/attachments/756500053161672809/795592098728378368/villager_guard23.png");
            typeEvent.reply(bannerBuilder.build());
        }
        else if(Arrays.asList("dm","damagemodifier","modifier").contains(typeArgs.get(0).toLowerCase())){
            String modifierURL = "https://www.mythicmobs.net/manual/doku.php/databases/misc/damagemodifiers";
            Document dmPage = null;
            try{
                dmPage = Jsoup.connect(modifierURL).get();
            }catch (Exception ex){
                ex.printStackTrace();
                System.out.println("could not connect to modifier url");
            }
            if(dmPage != null){
                EmbedBuilder eb = new EmbedBuilder();
                List<String> dmList = dmPage.select("#options").first().nextElementSibling().select("tbody").select(".col0").eachText();
                List<String> semiMatchedDMWord = dmList.stream().filter(s -> s.toLowerCase().contains(typeArgs.get(1))).collect(Collectors.toList());
                List<String> matchedDMWord = dmList.stream().filter(s -> s.equalsIgnoreCase(typeArgs.get(1))).collect(Collectors.toList());
                if(typeArgs.get(1).equalsIgnoreCase("all") || typeArgs.get(1).equalsIgnoreCase("")){
                    System.out.println("sending all modifiers");
                    allDamagedModifier(dmPage,eb,typeEvent,modifierURL,dmList);
                }
                else if(!matchedDMWord.isEmpty()){
                    System.out.println("sending matched dm");
                    dmEmbedBuilder(matchedDMWord.get(0),dmPage,typeEvent,eb,modifierURL);
                }
                else if(semiMatchedDMWord.size() > 1){
                    System.out.println("sending list of dm");
                    eb.setTitle("multiple modifiers found")
                    .setDescription("```"+semiMatchedDMWord.toString()+"```")
                    .setColor(Color.red)
                    .setThumbnail("https://raw.githubusercontent.com/twitter/twemoji/master/assets/72x72/26a0.png");
                    typeEvent.reply(eb.build());

                }else if(semiMatchedDMWord.size() == 1){
                    System.out.println("sending semi dm");
                    dmEmbedBuilder(semiMatchedDMWord.get(0),dmPage,typeEvent,eb,modifierURL);
                }



            }
        }
    }



}
