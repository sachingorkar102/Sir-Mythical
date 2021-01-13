package me.mythical.mmcommand;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.EmbedBuilder;

import java.util.Arrays;
import java.util.List;

public class MMcommand extends Command {

    public MMcommand(){
        this.aliases = new String[]{"mm","MM","mythicmobs"};
        this.name = "MythicMobs";
    }

    @Override
    protected void execute(CommandEvent e) {
        List<String> args = Arrays.asList(e.getArgs().split(" "));
        String moduleWord = null;
        if(args.size() >1){
            moduleWord = args.get(0)+args.get(1);
        }else{
            moduleWord = args.get(0);
        }
        if(moduleWord != null){
            EmbedBuilder eb = new EmbedBuilder();
            if(moduleWord.equalsIgnoreCase("bossbar") || moduleWord.equalsIgnoreCase("bossbars")){
                new BossBarEmbed().bbEmbed(e,eb);
                System.out.println("sending bossbar info");
            }
            else if (moduleWord.equalsIgnoreCase("Equipment")){
                new EquipmentEmbed().equipmentEmbed(e,eb);
                System.out.println("sending bossbar info");
            }
            else if(Arrays.asList("damagemodifier","modifier","dm").contains(moduleWord)){
                new DamageModifierEmbed().dmEmbed(e,eb);
                System.out.println("sending damage modifier info");
            }













        }
    }
}
