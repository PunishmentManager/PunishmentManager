package me.superbiebel.punishmentmanager.punishmentcore;

import me.clip.placeholderapi.PlaceholderAPI;
import me.lucko.helper.metadata.Metadata;
import me.superbiebel.punishmentmanager.utils.DataUtility;
import org.bukkit.entity.Player;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class OffenseExecutor {
    private Player executor;
    private Player criminal;
    private int offenseID;
    public OffenseExecutor(Player executor, int offenseID) {
        this.executor = executor;
        this.offenseID = offenseID;
    }
    public boolean execute() {
        this.criminal = Metadata.provideForPlayer(executor).get(DataUtility.CRIMINAL_KEY).get();
        Thread thread = new Thread(()->{String script = "to be filled in";
            PlaceholderAPI.setPlaceholders(executor,script);
            Context cx = Context.enter();
            Scriptable scope = cx.initStandardObjects();
            cx.evaluateString(scope,script,"test",1,null);
            Context.exit();
            ;},"testThread");
        thread.start();
        return false;
    }
}