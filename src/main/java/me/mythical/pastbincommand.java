package me.mythical;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;

public class pastbincommand extends Command {
    public static final String paste = "https://hastebin.com";

    public pastbincommand(){
        this.aliases = new String[]{"paste","haste","pastebin","hastebin"};
        this.name = "pastebin";
    }


    @Override
    protected void execute(CommandEvent event) {
        EmbedBuilder eb = new EmbedBuilder()
                .setTitle("Haste Bin",paste)
                .setThumbnail("https://lystic.dev/img/Hastebin_logo.png")
                .setColor(Color.CYAN)
                .setDescription("provide your *serverlogs/mob codes/skills/scripts* in hastebin rather then pasting it in *discord or in file download*.\n this helps us to help you in a much better and faster way\n"+paste);
        event.reply(eb.build());
    }
}
