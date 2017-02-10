package com.cloudcraftgaming.novagameslib.internal.listener;

import com.cloudcraftgaming.novagameslib.NovaGamesLib;
import com.cloudcraftgaming.novagameslib.api.utils.SignUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

/**
 * Created by Nova Fox on 11/23/2016.
 * Website: www.cloudcraftgaming.com
 * For Project: NovaGamesLib-Bukkit
 */
@SuppressWarnings("unused")
public class SignChangeListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onSignChange(SignChangeEvent event) {
        if (NovaGamesLib.plugin.getConfig().getString("Sign.Manage").equalsIgnoreCase("True")) {
            Player player = event.getPlayer();
            if (SignUtils.isJoinSign(event)) {
                Integer id = SignUtils.getIdFromSign(event);
                SignUtils.setJoinSign(event, id);
            } else if (SignUtils.isQuitSign(event)) {
                SignUtils.setQuitSign(event);
            } else if (SignUtils.isSpectateSign(event)) {
                SignUtils.setSpectateSign(event, SignUtils.getIdFromSign(event));
            }
        }
    }
}