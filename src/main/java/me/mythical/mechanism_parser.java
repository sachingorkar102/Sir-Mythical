package me.mythical;

import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.EmbedBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.awt.*;
import java.io.IOException;
import java.util.List;


public class mechanism_parser {
    public void getVideo(String name, EmbedBuilder eb) {
        String title;
        String link;
        switch (name){
            case "projectile":
                eb.addField("Related tutorial",
                        "[Fantastic Projectiles Pt. 3 [Mythic Mobs Projectiles Tutorial]](https://www.youtube.com/watch?v=Bf8-WbDGIaU)\n"+
                        "[Fantastic Projectiles Pt. 1 [MythicMobs Projectile Tutorial]](https://www.youtube.com/watch?v=qvZnHFLtAZY&list=PLj4aPW7DJDGi7d11DUI14tMurI6BvDj6o&index=7)\n"+
                        "[Fantastic Projectiles Pt. 2 [MythicMobs Projectile Tutorial]](https://www.youtube.com/watch?v=hmYfOmxl2yk&list=PLj4aPW7DJDGi7d11DUI14tMurI6BvDj6o&index=6)\n",false);
                break;
            case "sudoskill":
                eb.addField("Related Tutorial","[It Wasn't Me [Mythic Mobs Sudoskill Tutorial]](https://www.youtube.com/watch?v=S1L05rq26n0)",false);
                break;
            case "command":
                eb.addField("Related Tutorial","[At Your Command! [MythicMobs Command Tutorial]](https://www.youtube.com/watch?v=1CgI4ToOBe4&list=PLj4aPW7DJDGi7d11DUI14tMurI6BvDj6o&index=14)",false);
                break;
            case "consume":
                eb.addField("Related Tutorial","[Om Nom Cookie!! [MythicMobs Consume Tutorial]](https://www.youtube.com/watch?v=1cViiLx8mc4&list=PLj4aPW7DJDGi7d11DUI14tMurI6BvDj6o&index=13&t=455s)",false);
            default:
                System.out.println("no tutorial found");


        }
    }
    public mechanism_parser(String a, String b, CommandEvent c){
        System.out.println(a);
        Document mechPage = null;
        try {
            mechPage = Jsoup.connect(b).get();
        } catch (IOException e) {
            System.out.println("could not connect to main mech link");
        }
        String mechInfo1 = mechPage.select("[id*=mechanic]").get(0).nextElementSibling().select("p").text();
        Elements mechTable = mechPage.getElementsByTag("h2").select("[id*=Attribute]").next();
        Elements mechRow = null;
        try{mechRow = mechTable.select("table").select("tbody").select("tr");}
        catch (Exception ex){
            ex.printStackTrace();
            System.out.println("could not find attributes");
        }
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle(a, b);
        eb.setDescription("**Mechanic:**\n" + mechInfo1 + "\n" + "\n**Attributes:**\n");
        if(mechRow != null){
            int k = 0;
            for (Element rows : mechRow) {
                if (k > 10 || k >= rows.select("td").size()) {
                    //                System.out.println("passed");
                    eb.addField("\n``Too many attributes``", "``click on link for more info``\n", false);
                    break;
                }
                k++;
                eb.addField("```" + rows.select("td").get(0).text() + "/" + rows.select("td").get(1).text() + "```", "> default: " + rows.select("td").get(3).text() + "\n" + "> " + rows.select("td").get(2).text() + "\n", false);
           }
        }
        else{
            eb.addField("No attributes found","",false);
        }
        String exampleDesc = null;
        try{
            exampleDesc = mechPage.select("[id^=example]").first().nextElementSiblings().select("p").text();
        }catch (Exception ex){
            ex.printStackTrace();
            System.out.println("could not find example");
        }
        if(exampleDesc != null) {
            Elements exampleCode = mechPage.select(".code");
            switch (exampleCode.size()) {
                case 0:
                    System.out.println("no examples to search");
                    eb.addField("Examples:","No examples found",false);
                    break;
                case 1:
                    System.out.println("1");
                    eb.addField("Examples:\n", exampleDesc
                            + "\n\n" + "```yaml\n"
                            + exampleCode.eachText().get(0)
                            + "```", false);
                    break;
                case 2:
                    System.out.println("2");
                    eb.addField("Examples:\n", exampleDesc
                            + "\n\n" + "```yaml\n"
                            + exampleCode.eachText().get(0)
                            + "```" + "```yaml\n"
                            + exampleCode.eachText().get(1)
                            + "```", false);
                    break;
                case 3:
                    System.out.println("3");
                    eb.addField("Examples:\n", exampleDesc
                            + "\n\n" + "```yaml\n" + exampleCode.eachText().get(0)
                            + "```" + "```yaml\n" + exampleCode.eachText().get(1)
                            + "```" + "```yaml\n" + exampleCode.eachText().get(1)
                            + "```", false);
                    break;
                default:
                    System.out.println("default");
                    eb.addField("Examples:\n", exampleDesc
                            + "\n\n" + "```yaml\n"
                            + exampleCode.eachText().get(0)
                            + "```", false);
                    break;
            }

        }else {
            eb.addField("Examples:","No examples found",false);
        }
        getVideo(a,eb);
        c.reply(eb.setColor(main_class.mmcolor).build());
//        System.out.println("embed sent");
    }
    public static void null_mech( CommandEvent d, List<String> e){
        EmbedBuilder ebdym = new EmbedBuilder();
        ebdym.setColor(Color.red);
        ebdym.setTitle("Multiple Mechanic found\n");
        ebdym.setDescription("```" + e + "```");
        d.reply(ebdym.build());
    }
}
