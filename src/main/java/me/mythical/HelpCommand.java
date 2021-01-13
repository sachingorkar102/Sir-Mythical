package me.mythical;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.EmbedBuilder;

public class HelpCommand extends Command {
    public HelpCommand(){
        this.aliases = new String[]{"help","helpme"};
        this.name = "help command";
    }
    @Override
    protected void execute(CommandEvent e) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Help Command")
        .setColor(main_class.helpcolor)
        .setThumbnail("https://emojipedia-us.s3.dualstack.us-west-1.amazonaws.com/thumbs/120/google/274/question-mark_2753.png")
        .addField("Available Commands:","``!help``shows this help output" +
                "\n``!c [condition name]``to get info on specific condition" +
                "\n``!m [mech name]``shows on specific mechanic and effect ``!m help`` for more info" +
                "\n``!type help`` to get info on types present in mythic mobs" +
                "\n``!mm [info name]``to get info on specific topic",false);
        e.reply(eb.build());
    }
}
