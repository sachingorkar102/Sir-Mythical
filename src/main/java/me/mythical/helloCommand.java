package me.mythical;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.EmbedBuilder;

public class helloCommand extends Command {

    public helloCommand(){
        this.aliases = new String[]{"hello world","hello","github"};
        this.name = "github link";
    }

    @Override
    protected void execute(CommandEvent event) {
        EmbedBuilder eb = new EmbedBuilder()
                .setTitle("Hello World")
                .setDescription("hello there,i am a bot,you can find my source code on github,i am created and maintained by *sachin#8481*" +
                        "")
                .setColor(main_class.mmcolor);
    }
}
