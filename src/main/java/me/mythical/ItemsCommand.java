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

public class ItemsCommand extends Command{

    public void attrifinder(String word,CommandEvent e,EmbedBuilder eb){
        Document attributePage = null;
        String URL = "https://www.mythicmobs.net/manual/doku.php/databases/items/attributes";
        try {
            attributePage = Jsoup.connect(URL).get();
        } catch (Exception exception) {
            exception.printStackTrace();
            System.out.println("failed to connect to site");
        }
        if(attributePage!=null){
            System.out.println("found match");
            Elements page = attributePage.select("#attributes").first().nextElementSiblings();
            Elements name = page.select("p").select(":contains("+word+")");
            Element desc = name.next().first();
            eb.setColor(new Color(0x00486f))
                    .setTitle("Item Attributes",URL)
                    .addField(name.first().text(),desc.text(),false);
            e.reply(eb.build());
        }
    }
    public void optionFinder(String word,CommandEvent e,EmbedBuilder eb){
        Document optionPage = null;
        String URL = "https://www.mythicmobs.net/manual/doku.php/databases/items/options";
        try {
            optionPage = Jsoup.connect(URL).get();
        } catch (Exception exception) {
            exception.printStackTrace();
            System.out.println("failed to connect to site");
        }
        if(optionPage!=null){
            System.out.println("found match");
            Elements name = optionPage.select("p").select(":contains("+word+")");
            Element desc = name.next().first();
            eb.setColor(new Color(0x00486f))
                    .setTitle("Item Options",URL)
                    .addField(name.first().text(),desc.text(),false);
            e.reply(eb.build());
        }
    }
    public void embedbuilder(String Desc,String Title,CommandEvent e,EmbedBuilder eb){
        eb.setTitle(Title,"https://www.mythicmobs.net/manual/doku.php/databases/items/overview")
        .setDescription(Desc);
        e.reply(eb.build());
    }
    public void strucFrinder(String word,CommandEvent e,EmbedBuilder eb){
        System.out.println(word);
        String Desc;
        switch(word){
            case "id":
                Desc =  "Defines the type of item.\n" +
                        "Can be either the item id or the bukkit material/item name.\n" +
                        "Often used items are listed here: [Common Item IDs](https://www.mythicmobs.net/manual/doku.php/databases/items/commonitems)\n" +
                        "```yaml\n Examples:\n" +
                        "    Id: 359\n" +
                        "    Id: diamond_sword\n```" +
                        "It is STRONGLY RECOMMENDED to use namespaced item IDs, e.g. diamond_sword, on Minecraft 1.12.2.\n" +
                        "Numbered IDs do not exist in versions 1.13.2 and above, and you will save yourself a lot of time if you don't rely on the old numbered ID system.\n";
                eb.setColor(new Color(0x00486f));
                embedbuilder(Desc,word,e,eb);
                break;
            case "data":
                Desc =  "Sets the data value of the item created.\n" +
                        "Used to specify the used up durability points on items, weapons or armor or to specify the sub-type of a block.\n";
                eb.setColor(new Color(0x00486f));
                embedbuilder(Desc,word,e,eb);
                break;
            case "display":
                Desc=   "Sets the display name of the item.\n" +
                        "Supports color codes and string variables: Variables\n" +
                        "Must be encased by single quotes.\n" +
                        "For using single quotes inside of the name, you can use the <&sq> [variable](https://www.mythicmobs.net/manual/doku.php/skills/stringvariables).\n" +
                        "```yaml\n Examples:\n" +
                        "    Display: 'Very Strong Sword'\n" +
                        "    Display: '&eVery Strong Sword'```\n";
                eb.setColor(new Color(0x00486f));
                embedbuilder(Desc,word,e,eb);
                break;
            case "model":
                Desc= "\n" +
                        "Sets the CustomModelData tag on the item.\n" +
                        "Only usable in 1.14+\n";
                eb.setColor(new Color(0x00486f));
                embedbuilder(Desc,word,e,eb);
                break;
            case "attributes":
                Desc= "Special field that allows the addition of item attributes to certain entity slots\n"+
                "use ``!item <attribute name>`` for more info\n"+
                "to view all attributes use ``!item attribute all";
                eb.setColor(new Color(0x00486f));
                embedbuilder(Desc,word,e,eb);
                break;
            case "amount":
                Desc = "\n" +
                        "Defines the default amount of items to give when this item is being called by the plugin.\n" +
                        "```yaml\n Examples:\n" +
                        "Amount: 8```\n";
                eb.setColor(new Color(0x00486f));
                embedbuilder(Desc,word,e,eb);
                break;
            case "options":
                Desc = "\n" +
                        "This is a special field which comes with numerous sub-options, determining lots of extra attributes for the item.\n" +
                        "A complete list of all available options: [Item Options](https://www.mythicmobs.net/manual/doku.php/databases/items/options)\n" +
                        "use ``!item <option name>`` for more info or\n"+
                        "use ``!item options all`` to view all options";
                eb.setColor(new Color(0x00486f));
                embedbuilder(Desc,word,e,eb);
                break;
            case "durability":
                Desc = "Defines the starting durability of the item(should be a number).";
                embedbuilder(Desc,word,e,eb);
                break;
            case "hide":
                Desc =  "Special field that allows to hide specific things from the item tooltip.\n" +
                        "Possible are “ATTRIBUTES”, “ENCHANTS”, “DESTROYS”, “PLACED_ON”, “POTION_EFFECTS” and “UNBREAKABLE”.\n" +
                        "Examples:\n" +
                        "```yaml\nHide:\n" +
                        "  - ATTRIBUTES\n" +
                        "  - UNBREAKABLE```\n";
                eb.setColor(new Color(0x00486f));
                embedbuilder(Desc,word,e,eb);
                break;
            case "enchantments":
                Desc =  "This field allows to add enchantments to items.\n" +
                        "Any type of of item can have any enchanment(s).\n" +
                        "A complete list of all available enchantments: [Enchantments](https://www.mythicmobs.net/manual/doku.php/databases/items/enchantments)\n" +
                        "use ``!types enchantment <enchantment name>`` for more info";
                eb.setColor(new Color(0x00486f));
                embedbuilder(Desc,word,e,eb);
                break;
            case "lore":
                Desc =  "Allows you to add custom lore to your items.\n" +
                        "Supports color codes and string [Variables](https://www.mythicmobs.net/manual/doku.php/skills/stringvariables).\n" +
                        "Must be encased by single quotes.\n" +
                        "For using single quotes inside of the name, you can use the <&sq> variable.\n" +
                        "Putting number ranges surrounded by curly braces will generate a random number in that range when the item is created (i.e. Health: +{100-200} would become something like Health: +152). Works with ItemLoreStats.\n"+
                        "Examples:\n"+
                        "```yaml\nLore:\n" +
                        "- '&rThe weapon of a true warrior'\n" +
                        "- ''\n" +
                        "- '&cIncreases ones greed'```";
                eb.setColor(new Color(0x00486f));
                embedbuilder(Desc,word,e,eb);
                break;
            case "potioneffects":
                Desc=   "This allows you to add potion effects to your items.\n" +
                        "These effects won't do anything, except for showing up in the item tooltip, if the specified item isn't a potion.\n" +
                        "See [Potions](https://www.mythicmobs.net/manual/doku.php/databases/items/potions).\n";
                eb.setColor(new Color(0x00486f));
                embedbuilder(Desc,word,e,eb);
                break;
            case "bannerlayers":
                Desc=   "This option allows you to edit the layers of a banner.\n" +
                        "Won't do anything if the selected item isn't a banner.\n" +
                        "This option is capeable of passing minecraft's 6 layer limit. However adding excessive amounts of layers may cause weird behaviour and will not be supported.\n" +
                        "See [Banner Layers](https://www.mythicmobs.net/manual/doku.php/databases/items/bannerlayers)\n"+
                        "use ``!type banner layer`` for more info";
                eb.setColor(new Color(0x00486f));
                embedbuilder(Desc,word,e,eb);
                break;
            case "nbt":
                Desc= "You can now specify NBT tags on items in the format:\n" +
                      "```yaml\nItem:\n" +
                      "  Id: DIAMOND_SWORD\n" +
                      "  NBT:\n" +
                      "    Base:\n" +
                      "      ATag: 20\n" +
                      "      SomeOtherTag: something\n" +
                      "    GemSlots:\n" +
                      "      RedGem: 0\n" +
                      "    'Denizen NBT':\n" +
                      "      somedenizentag: a_string```"+
                      " This allows cross-over with a lot of other plugins, or just for storing some custom information.\n" +
                      "\n" +
                      "For the more technically-inclined, anything under 'Base' will go under the item's base compound tag, and anything else will go under the corresponding key (or if no sub-items are defined, everything will go under the base tag).\n" +
                      "\n" +
                      "If using with Denizen, all tags you want to use in Denizen must go under 'Denizen NBT' and must be lower-case to work in your Denizen scripts. ";
                eb.setColor(new Color(0x00486f));
                embedbuilder(Desc,word,e,eb);
                break;
            case "examples":
                Desc= " More items can be found in the [Examples](https://www.mythicmobs.net/manual/doku.php/examples) section.\n"+
                "```yaml\nClothSlippers:\n" +
                        "  Id: 301\n" +
                        "  Data: 0\n" +
                        "  Display: '&fCloth Slippers'\n" +
                        "  Lore:\n" +
                        "  - ''\n" +
                        "  - 'So Soft!'\n" +
                        "  - ''\n" +
                        "  Enchantments:\n" +
                        "  - DURABILITY:1\n" +
                        "  Options:\n" +
                        "    Color: 200,200,200```";
                eb.setColor(new Color(0x00486f));
                embedbuilder(Desc,word,e,eb);
                break;
            case "attributesall":
                String list = Arrays.asList("``AttackSpeed``","``Armor``","``ArmorToughness``","``Damage``"
                        ,"``FollowRange``","``Health``","``Luck``","``KnockbackResistance``","``MovementSpeed``").toString();
                Desc= list+"\nfor more info use ``!item <attribute name>``";
                eb.setColor(new Color(0x00486f));
                embedbuilder(Desc,word,e,eb);
                break;
            case "optionsall":
                String list1 = Arrays.asList("``Repairable``","``RepairCost``","``Player``","``SkinTexture``","``Color``").toString();
                Desc = list1+"\nfor more info use ``!item <option name>";
                eb.setColor(new Color(0x00486f));
                embedbuilder(Desc,word,e,eb);
                break;
            default:
                Desc="Could not found ``"+word+"`` in database";
                eb.setColor(Color.red);
                embedbuilder(Desc,"Word not found",e,eb);

        }
    }
    public static final List<String> ATTRIBUTES_LIST = Arrays.asList("AttackSpeed","Armor","ArmorToughness","Damage"
            ,"FollowRange","Health","Luck","KnockbackResistance","MovementSpeed");
    public static final List<String> OPTION_LIST = Arrays.asList("Repairable","RepairCost","Player","SkinTexture","Color");
    public static final List<String> STRUCTURE_LIST = Arrays.asList("Id","Data","Display","Model"
            ,"Attributes","Amount","Options","Durability","Enchantments","Lore","PotionEffects","BannerLayers","Hide","Examples","Nbt","Attributesall","Optionsall");
    public static List<String> attriSemiMatchedWords;
    public static List<String> attriMatchedWords;
    public static List<String> strucSemiMatchedWords;
    public static List<String> strucMatchedWords;
    public static List<String> optionMatchedWords;
    public static List<String> optionSemiMatchedWords;

    public ItemsCommand(){
        this.name = "Item";
        this.aliases = new String[]{"i","I","items","item"};
    }

    @Override
    protected void execute(CommandEvent e) {
        List<String> args = Arrays.asList(e.getArgs().split(" "));
        if(args.get(0).equals("")){
            e.reply("usage");
            return;
        }
        String word;
        if(args.size()>1){
            word = args.get(0)+args.get(1);
        }
        else{
            word = args.get(0);
        }
        strucMatchedWords = STRUCTURE_LIST.stream().map(String::toLowerCase).filter(s -> s.equals(word.toLowerCase())).collect(Collectors.toList());
        strucSemiMatchedWords = STRUCTURE_LIST.stream().map(String::toLowerCase).filter(s -> s.contains(word.toLowerCase())).collect(Collectors.toList());
        attriMatchedWords = ATTRIBUTES_LIST.stream().map(String::toLowerCase).filter(s -> s.equals(word.toLowerCase())).collect(Collectors.toList());
        attriSemiMatchedWords = ATTRIBUTES_LIST.stream().map(String::toLowerCase).filter(s -> s.contains(word.toLowerCase())).collect(Collectors.toList());
        optionMatchedWords = OPTION_LIST.stream().map(String::toLowerCase).filter(s -> s.equals(word.toLowerCase())).collect(Collectors.toList());
        optionSemiMatchedWords = OPTION_LIST.stream().map(String::toLowerCase).filter(s -> s.contains(word.toLowerCase())).collect(Collectors.toList());
        EmbedBuilder eb = new EmbedBuilder();


        if(!attriSemiMatchedWords.isEmpty()) {

            if (!attriMatchedWords.isEmpty()) {
                System.out.println("going 1");
                attrifinder(attriMatchedWords.get(0), e, eb);
            } else if (1 < attriSemiMatchedWords.size()) {
                System.out.println("sending multiple words");
                eb.setTitle("Multiple words found")
                        .setDescription(attriSemiMatchedWords.toString())
                        .setColor(Color.red);
                e.reply(eb.build());
            } else if (attriSemiMatchedWords.size() == 1) {
                System.out.println("going 2");
                attrifinder(attriSemiMatchedWords.get(0), e, eb);
            }
        }
        else if (!optionSemiMatchedWords.isEmpty()){


            if (!optionMatchedWords.isEmpty()) {
                System.out.println("going 1");
                optionFinder(optionMatchedWords.get(0), e, eb);
            } else if (1 < optionSemiMatchedWords.size()) {
                System.out.println("sending multiple words");
                eb.setTitle("Multiple words found")
                        .setDescription("```css\n"+optionSemiMatchedWords.toString()+"```")
                        .setColor(Color.red);
                e.reply(eb.build());
            } else if (optionSemiMatchedWords.size() == 1) {
                System.out.println("going 2");
                optionFinder(optionSemiMatchedWords.get(0), e, eb);
            }
        }
        else if(!strucSemiMatchedWords.isEmpty()){

            if(!strucMatchedWords.isEmpty()){
                strucFrinder(strucMatchedWords.get(0),e,eb);
            }
            else if (strucSemiMatchedWords.size() > 1){
                //TODO
            }
            else if(strucSemiMatchedWords.size() == 1){
                strucFrinder(strucSemiMatchedWords.get(0),e,eb);
            }
            else {
                strucFrinder(word,e,eb);
            }
        }
        else{
            eb.setColor(Color.red)
            .setDescription("could not find ``"+word+"`` in database")
            .setTitle("word not found");
            e.reply(eb.build());
        }

    }
}
