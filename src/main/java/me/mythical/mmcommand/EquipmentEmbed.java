package me.mythical.mmcommand;

import com.jagrosh.jdautilities.command.CommandEvent;
import me.mythical.main_class;
import net.dv8tion.jda.api.EmbedBuilder;

public class EquipmentEmbed {
    public void equipmentEmbed(CommandEvent e, EmbedBuilder eb) {
        eb.setColor(main_class.mmcolor)
                .setTitle("Equipment","https://www.mythicmobs.net/manual/doku.php/databases/misc/equipment")
                .setDescription("The Equipment section in a mob config is a special droptable that defines what kind of equipment the mob will spawn with. The equipment will only be applied to the mob when it spawns or during a reload, and can be changed afterwards by using the Equip mechanic``(use !m equip)``.")
                .addField("Syntax:","```yaml\ninternal_mobname:\n" +
                        "  Type: <mobtype>\n" +
                        "  Equipment:\n" +
                        "  - <item> <slot>\n" +
                        "  - <item> <slot>\n" +
                        "  - ...```\n ``<item>``\n" +
                        "Can be either the name of a MythicMobs item or a vanilla item.\n" +
                        "``<slot>``\n" +
                        "Defines the slot on the mob that item should be carried on.\n",false)
                .addField("Slots:","HEAD:\n> The head slot. Apart from regular helmets can be any blocktype or player head. Most will render accurately.\n"+
                        "CHEST:\n> The chest slot. Will only render chestplates, but will carry any items.\n"+
                        "LEGS:\n> The leg slot. Will only render leggings, but will carry any items.\n"+
                        "FEET:\n> The feet slot. Will only render boots, but will carry any items.\n"+
                        "HAND:\n> The mainhand slot. Will render any type of item.\n"+
                        "OFFHAND:\n> The offhand slot, introduced in MC 1.9. Will render any type of item.",false)
                .addField("Example:","```yaml\nawesome_boss:\n" +
                        "  Type: pig_zombie\n" +
                        "  Equipment:\n" +
                        "  - awesome_boss_helmet:4\n" +
                        "  - diamond_sword:0```",false)
                .addField("Related Tutorial:","[Getting Started - Items & Equipment](https://www.youtube.com/watch?v=LoK_lxG7spg&list=PLj4aPW7DJDGiZv0G-3DYiMA3XuIRTCFaL&index=5)",false);
        e.reply(eb.build());
    }
}
