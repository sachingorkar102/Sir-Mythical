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

public class OptionsCommand extends Command {
    public void optionEmbedBuilder(String word, CommandEvent e, EmbedBuilder eb){
        Document opPage = null;
        try{
            opPage = Jsoup.connect(optionURL).get();
        }
        catch (Exception ex){
            System.out.println("could not load Options site");
            ex.printStackTrace();
        }
        if (opPage != null){
            if(word.equalsIgnoreCase("color")){
                eb.setColor(main_class.mmcolor)
                .setTitle("Color: [number]",optionURL)
                .setDescription("Number between 0 and 15\n" +
                        "Sets the wool color of sheep or the collar color of wolves\n" +
                        "NOTE: in MythicMobs 2.0.0 this has changed to a string value.\n" +
                        "The string is the name of the color you want. Ex: Color: RED\n");
                e.reply(eb.build());
                return;
            }
            Elements name = opPage.getElementById("universal_options").nextElementSiblings().select("strong");
            String opList = name.eachText().stream().filter(s -> s.toLowerCase().contains(word.toLowerCase())).collect(Collectors.toList()).get(0);
            Element opName = name.select(":contains("+opList+")").first();
            List<String> DescList = opName.parent().nextElementSibling().select(".li").eachText();
            System.out.println(name);
            StringBuilder Desc = new StringBuilder();
            int a;
            for (a=0;a<DescList.size();a++){
                Desc.append(DescList.get(a)).append("\n");
            }
            eb.setColor(main_class.mmcolor)
            .setTitle(opName.text(),optionURL)
            .setDescription(Desc);
            e.reply(eb.build());
            System.out.println(Desc);
        }
    }
    public static List<String> optionSemiMatchedList;
    public static List<String> optionMatchedList;
    public static final String optionURL = "https://www.mythicmobs.net/manual/doku.php/databases/mobs/options";
    public static final List<String> OPTION_LIST = Arrays.asList("AlwaysShowName", "AttackSpeed", "ApplyInvisibility", "Collidable"
            , "Despawn", "FollowRange", "Glowing", "Invincible", "Interactable", "LockPitch", "KnockbackResistance"
            , "MaxCombatDistance", "MovementSpeed", "NoAI", "NoDamageTicks", "NoGravity", "Persistent", "PreventItemPickup"
            , "PreventLeashing", "PreventMobKillDrops", "PreventOtherDrops", "PreventRandomEquipment", "PreventRenaming"
            , "PreventSunburn", "RepeatAllSkills", "ShowHealth", "Silent", "CanMove", "CanTick", "HasArms", "HasBasePlate"
            , "HasGravity", "Invisible", "ItemBody", "ItemFeet", "ItemHand", "ItemHead", "ItemLegs", "Marker", "Small", "Pose", "Head"
            , "Body", "LeftArm", "RightArm", "LeftLeg", "RightLeg", "Anger", "HasNectar", "HasStung", "Jockey", "ExplosionRadius", "FuseTicks"
            , "SuperCharged", "PreventSuicide", "PreventTeleport", "Block", "BlockData", "DropsItem", "HurtsEntities", "Type", "HorseArmor"
            , "CarryingChest", "HorseColor", "Saddled", "HorseStyle", "Tamed", "HorseType", "PlayerCreated", "Ocelot", "MainGene", "HiddenGene", "Saddled"
            , "RabbitType", "Baby", "Sheared", "PreventBlockInfection", "Derp", "PreventSnowFormation", "FuseTicks", "ExplosionYield", "Incendiary"
            , "Pattern", "BodyColor", "PatternColor", "HasTrades", "VillagerType", "PreventJockeyMounts", "PreventTransformation", "ReinforcementsChance"
            , "Profession", "Age", "AgeLock", "Baby", "Color", "Angry", "PreventSlimeSplit", "Size", "Tameable");

    public OptionsCommand(){
        this.name = "options";
        this.aliases = new String[]{"Options","O","o","option","Option","op"};
    }


    @Override
    protected void execute(CommandEvent e) {
        List<String> args = Arrays.asList(e.getArgs().split(" "));
        String word;
        if(args.size()>1) word = args.get(0)+args.get(1);
        else word = args.get(0);
        EmbedBuilder eb = new EmbedBuilder();
        if(word.equalsIgnoreCase("help") || word.equalsIgnoreCase("")){
            eb.setTitle("Option Command",optionURL)
            .setColor(main_class.helpcolor)
            .setDescription("**used to get info on mob options**\n")
            .addField("Aliases:","!Options ,!O , !o , !option , !Option , !op ",false)
            .addField("Usage:","!option [option name]",false);
            e.reply(eb.build());
            return;
        }
        optionSemiMatchedList = OPTION_LIST.stream().filter(s -> s.toLowerCase()
                .contains(word.toLowerCase())).collect(Collectors.toList());
        optionMatchedList = OPTION_LIST.stream().filter(s -> s.equalsIgnoreCase(word)).collect(Collectors.toList());
//        System.out.println(OPTION_LIST);
        System.out.println(optionMatchedList);
        System.out.println(optionSemiMatchedList);
        if(!optionSemiMatchedList.isEmpty()){
            if(!optionMatchedList.isEmpty()){
                System.out.println("going with matched options");
                optionEmbedBuilder(optionMatchedList.get(0),e,eb);
            }
            else if(optionSemiMatchedList.size()>1){
                System.out.println("found multiple option words");
                eb.setColor(main_class.mmcolor)
                .setTitle("found multiple Options")
                .setDescription("```fix\n"+optionSemiMatchedList.toString()+"```");
                e.reply(eb.build());
            }
            else {
                optionEmbedBuilder(optionSemiMatchedList.get(0),e,eb);
            }
        }else{
            System.out.println("failed to find word");
            eb.setTitle("Could not find Option")
                    .setColor(Color.red)
                    .setThumbnail("https://raw.githubusercontent.com/twitter/twemoji/master/assets/72x72/26a0.png")
                    .setDescription("Could not find option named``"+word+"``\nuse ``!option help`` for more info");
            e.reply(eb.build());
        }


    }
}
