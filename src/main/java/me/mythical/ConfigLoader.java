package me.mythical;

import org.json.simple.JSONObject;

public class ConfigLoader {
    public void load(JSONObject configobj){
        if(configobj != null) {
            try {
                main_class.BOT_TOKEN = configobj.get("bot_token").toString();
                System.out.println("loaded bot_token from config.json");
            } catch (Exception ex) {
                System.out.println("could not find \"bot_token\" in config.json\nbot will not load");
                main_class.BOT_TOKEN = null;
            }
            try {
                main_class.CLIENT_ID = configobj.get("client_id").toString();
                System.out.println("loaded client_id from config.json");
            } catch (Exception ex) {
                System.out.println("could not find \"client_id\" in config.json");
                main_class.CLIENT_ID = null;
            }
            try{
                main_class.PREFIX = configobj.get("prefix").toString();
                System.out.println("Prefix loaded");
            }catch (Exception ex){
                main_class.PREFIX = "!";
                System.out.println("error loading prefix from config.json, defaulting to \"!\"");
            }
            try{
                main_class.GAME_ACTIVITY = configobj.get("game_activity").toString();
                System.out.println("game activity changed");
            }catch (Exception ex){
                main_class.PREFIX = "Mooshroom Lab";
                System.out.println("error loading game activity from config.json, defaulting to \"Mooshroom Lab\"");
            }
        }
    }
}
