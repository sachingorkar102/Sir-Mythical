package me.mythical.mmcommand;

import com.jagrosh.jdautilities.command.CommandEvent;
import me.mythical.main_class;
import net.dv8tion.jda.api.EmbedBuilder;

public class BossBarEmbed {


    public void bbEmbed(CommandEvent e,EmbedBuilder eb){
        eb.setTitle("BossBars","https://www.mythicmobs.net/manual/doku.php/databases/misc/bossbar")
        .setDescription("Added in version 2.4\n" +
                "Requires Minecraft 1.9 or later\n" +
                "\n" +
                "The BossBar feature is used to give your custom bosses a healthbar in the same style used by the Ender Dragon and Wither plus a bunch of customization options.\n")
        .setColor(main_class.mmcolor)
        .addField("Syntax","```yaml\ninternal_mobname:\n" +
                "Type: <mobtype>\n" +
                "  BossBar:\n" +
                "    Enabled: [true/false]\n" +
                "    Title: '[name]'\n" +
                "    Range: [range]\n" +
                "    Color: [color]\n" +
                "    Style: [style]\n" +
                "    CreateFog: [true/false]\n" +
                "    DarkenSky: [true/false]\n" +
                "    PlayMusic: [true/false]```\n"+
                "\n" +
                "Available colors for Color (case sensitive):\n" +
                "``    PINK, BLUE, RED, GREEN, YELLOW, PURPLE, WHITE``\n" +
                "\n" +
                "Available styles for Style (case sensitive):\n" +
                "``    SOLID, SEGMENTED_6, SEGMENTED_10, SEGMENTED_12, SEGMENTED_20``\n" +
                "\n" +
                "Range is the distance the BossBar will be displayed to players around the mob\n" +
                "\n" +
                "CreateFog, DarkenSky, and PlayMusic are buggy and do not currently work as of 2.5.0\n",false)
        .addField("Example:","```yaml\nMobtype: ZOMBIE_PIGMAN\n" +
                "Health: 20\n" +
                "BossBar:\n" +
                "  Enabled: true\n" +
                "  Title: 'Custom Boss'\n" +
                "  Range: 20\n" +
                "  Color: PURPLE\n" +
                "  Style: SEGMENTED_6```",false)
        .addField("Related Tutorial:","[Getting Started - Boss Bar](https://www.youtube.com/watch?v=uEuXO4sMkIc&list=PLj4aPW7DJDGiZv0G-3DYiMA3XuIRTCFaL&index=2)",false)
        .setImage("https://i.ytimg.com/vi/NCTVRffRurg/maxresdefault.jpg");
        e.reply(eb.build());
    }

}
