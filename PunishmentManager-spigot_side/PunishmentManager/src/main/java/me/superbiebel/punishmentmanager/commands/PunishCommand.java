package me.superbiebel.punishmentmanager.commands;

import me.superbiebel.punishmentmanager.menusystem.menu.ActionsListGUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PunishCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            if(args.length < 1) {
                sender.sendMessage("No player provided!");
            }else{
                Player p = (Player) sender;
               // new ActionsListMenu(PunishmentManager.getPlayerMenuUtility(p)).open();
                new ActionsListGUI(p,1,"test").open();

            }
        }



        return true;
    }
}
