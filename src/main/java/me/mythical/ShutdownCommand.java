package me.mythical;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

public class ShutdownCommand extends Command {
    public ShutdownCommand() {
        this.name = "ShutDown";
        this.aliases = new String[]{"shutdown"};
    }

    @Override
    protected void execute(CommandEvent commandEvent) {
        String ownerId = commandEvent.getAuthor().getId();
        if(ownerId.equals("687954591844925496")){
            commandEvent.reply("shuting down the bot");
            commandEvent.getJDA().shutdown();
        }
        else {
            commandEvent.reply("permission Denied");
        }
    }


}
