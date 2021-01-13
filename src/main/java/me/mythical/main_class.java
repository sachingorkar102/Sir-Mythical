package me.mythical;

import com.jagrosh.jdautilities.command.CommandClient;
import com.jagrosh.jdautilities.command.CommandClientBuilder;
import me.mythical.mmcommand.MMcommand;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.security.auth.login.LoginException;
import java.awt.*;
import java.io.FileReader;
import java.io.IOException;
//import javax.xml.bind.Marshaller;
//import java.io.IOException;q

public class main_class extends ListenerAdapter {

    public static final Logger LOGGER = LoggerFactory.getLogger(main_class.class);
    public static final Color mmcolor = new Color(0x00486F);
    public static final Color helpcolor = new Color(0, 72, 111, 171);
    public static String BOT_TOKEN = null;
    public static String CLIENT_ID = null;
    public static String PREFIX = null;
    public static String GAME_ACTIVITY = null;
    public static void main(String[] args) throws LoginException, IOException, ParseException {
        JSONObject configobj= null;
        try {
            JSONParser jsonParser = new JSONParser();
            FileReader reader = new FileReader("config.json");
            Object obj = jsonParser.parse(reader);
            configobj = (JSONObject) obj;
            new ConfigLoader().load(configobj);
            System.out.println("config.json loaded successfully");
        }
        catch (Exception ex){
            System.out.println("could not find \"config.json\"");
            return;
        }

        if(BOT_TOKEN != null && CLIENT_ID != null) {
            LOGGER.info("hello world");
            JDA jda = JDABuilder.createDefault(BOT_TOKEN).build();
            System.out.println("-=====================Bot Is Ready=======================-");
            CommandClientBuilder builder = new CommandClientBuilder();
            builder.setPrefix(PREFIX);
            builder.setActivity(Activity.watching(GAME_ACTIVITY));
            builder.setOwnerId(CLIENT_ID);
            builder.addCommand(new MechanicCommand());
            builder.addCommand(new ShutdownCommand());
            builder.addCommand(new TypesCommand());
            builder.addCommand(new ConditionCommand());
            builder.addCommand(new ItemsCommand());
            builder.addCommand(new TargeterCommand());
            builder.addCommand(new OptionsCommand());
            builder.addCommand(new MMcommand());
            builder.addCommand(new HelpCommand());
            builder.addCommand(new pastbincommand());
            builder.addCommand(new helloCommand());
            CommandClient client = builder.build();
            jda.addEventListener(client);

        }
        else{
            System.out.println("error found while loading bot \nplease check your \"config.json\" is configured correctly");
        }
    }

}
