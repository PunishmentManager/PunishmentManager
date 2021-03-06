package me.superbiebel.punishmentmanager.menu;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import me.lucko.helper.metadata.Metadata;
import me.superbiebel.punishmentmanager.data.DATAKEYS;
import me.superbiebel.punishmentmanager.menu.abstraction.AbstractChestGui;
import me.superbiebel.punishmentmanager.utils.ColorUtils;
import me.superbiebel.punishmentmanager.utils.Log;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class ActionListGui extends AbstractChestGui {
    
    private StaticPane staticPane;
    
    private ItemStack offenseItemStack;
    private GuiItem offenseItem;
    private ItemMeta offenseItemMeta;
    
    private ItemStack historyItemStack;
    private GuiItem historyItem;
    private ItemMeta historyItemMeta;
    
    private ItemStack altCheckItemStack;
    private GuiItem altCheckItem;
    private SkullMeta altCheckItemMeta;

    private Player criminal;

    
    @Override
    public void construct(boolean force, boolean allowlazy){
        Log.debug("constructing gui...");
        super.gui = new ChestGui(3, "Choose action");
        staticPane = new StaticPane(0,0,9,3);
        
        offenseItemStack = new ItemStack(Material.IRON_AXE);
        offenseItemMeta = offenseItemStack.getItemMeta();
        offenseItemMeta.setDisplayName(ColorUtils.colorize("&4&lNew Offense"));
        offenseItemStack.setItemMeta(offenseItemMeta);
        offenseItem = new GuiItem(offenseItemStack);
        
        historyItemStack = new ItemStack(Material.BOOK);
        historyItemMeta = historyItemStack.getItemMeta();
        historyItemMeta.setDisplayName(ColorUtils.colorize("&4&lHistory"));
        historyItemStack.setItemMeta(historyItemMeta);
        historyItem = new GuiItem(historyItemStack);
        
        altCheckItemStack = new ItemStack(Material.PLAYER_HEAD);
        altCheckItemMeta = (SkullMeta) altCheckItemStack.getItemMeta();
        
        
        staticPane.addItem(offenseItem,2,1);
        staticPane.addItem(historyItem,4,1);
        super.hasBeenConstructed = true;
    }
    
    @Override
    public void construct(boolean force, boolean allowlazy, Player player) {
        construct(force, allowlazy);
        cachedPlayer = player;
        setPersonalisedStuff();
        super.gui.addPane(staticPane);
    }
    
    @Override
    public void open(final Player p){
        cachedPlayer = p;
        setPersonalisedStuff();
        super.gui.addPane(staticPane);
        super.gui.show(p);
    }
    
    @Override
    public void open() {
        super.gui.show(cachedPlayer);
    }
    
    public void setPersonalisedStuff() {
        if (!super.hasBeenConstructed) {
            throw new IllegalStateException("Cannot set personalised stuff when the gui hasn't been constructed yet");
        }
        Log.debug("setting personalised stuff for " + cachedPlayer.getName());
        criminal = Metadata.provideForPlayer(cachedPlayer).get(DATAKEYS.CRIMINAL_KEY).get();
        altCheckItemMeta.setOwningPlayer(criminal);
        altCheckItemMeta.setDisplayName(ColorUtils.colorize("&cAlts of " + criminal.getName()));
        altCheckItemStack.setItemMeta(altCheckItemMeta);
        altCheckItem = new GuiItem(altCheckItemStack);
        staticPane.addItem(altCheckItem,6,1);
        
        offenseItem.setAction(e -> {
            e.setCancelled(true);
            AbstractChestGui gui = new OffenseListGui();
            gui.construct(false,true);
            gui.open(cachedPlayer);
        });
        historyItem.setAction((e->{
            e.setCancelled(true);
            AbstractChestGui historyGui = new HistoryGui();
            historyGui.construct(false,true, cachedPlayer);
            historyGui.open(cachedPlayer);
        }));
        
        altCheckItem.setAction(e->{
            e.setCancelled(true);
            AbstractChestGui altcheckGui = new AltCheckGui();
            altcheckGui.construct(false,true,cachedPlayer);
            altcheckGui.open(cachedPlayer);
        });
    }
    
    @Override
    public void setCachedPlayer(Player p) {
        cachedPlayer = p;
        setPersonalisedStuff();
    }
}
