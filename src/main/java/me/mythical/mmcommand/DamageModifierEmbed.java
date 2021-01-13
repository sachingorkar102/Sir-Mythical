package me.mythical.mmcommand;

import com.jagrosh.jdautilities.command.CommandEvent;
import me.mythical.main_class;
import net.dv8tion.jda.api.EmbedBuilder;

public class DamageModifierEmbed {


    public void dmEmbed(CommandEvent e, EmbedBuilder eb) {
        eb.setTitle("Damage Modifier","https://www.mythicmobs.net/manual/doku.php/databases/misc/damagemodifiers")
        .setColor(main_class.mmcolor)
        .setDescription(" Damage Modifiers are an attribute you can add to your MythicMobs to increase or decrease the damage they receive from various sources.")
        .addField("Syntax:","```yaml\nArmoredZombie:\n" +
                "  Mobtype: zombie\n" +
                "  Display: '&aArmored Zombie'\n" +
                "  Health: 40\n" +
                "  Damage: 6\n" +
                "  DamageModifiers:\n" +
                "  - <MODIFIER> <number>\n```" +
                "\n**<MODIFIER>**" +
                "\n a list of modifier can be found [here](https://www.mythicmobs.net/manual/doku.php/databases/misc/damagemodifiers) or use" +
                "\n``!types modifier [modifier name]``or" +
                "\n``!types modifier all`` to get list of all modifier" +
                "\n**<number>**" +
                "\nA modifier of 1 will cause the mob to take normal damage from that source." +
                "\nA number higher than 1 will increase the damage it takes by that amount." +
                "\nA number lower than 1 will reduce the damage it takes by that amount." +
                "\n0 will make the mob immune to that damage source." +
                "\nA negative value (below 0) will cause the mob to heal from that type of damage. Note that this doesn't work if the mob is naturally immune to that damage.",false);
        e.reply(eb.build());

    }
}
