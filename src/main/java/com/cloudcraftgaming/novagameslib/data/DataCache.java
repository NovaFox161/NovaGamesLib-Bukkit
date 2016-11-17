package com.cloudcraftgaming.novagameslib.data;

import com.cloudcraftgaming.novagameslib.utils.FileManager;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.List;

/**
 * Created by Nova Fox on 11/17/16.
 * Website: www.cloudcraftgaming.com
 * For Project: NovaGamesLib
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class DataCache {

	public static Integer getNextId() {
		return FileManager.getPluginCacheYml().getInt("NextId");
	}

	public static Integer useNextId() {
		YamlConfiguration pluginCache = FileManager.getPluginCacheYml();
		Integer nextId = pluginCache.getInt("NextId");
		pluginCache.set("NextId", nextId + 1);
		pluginCache.set("ArenaAmount", pluginCache.getInt("ArenaAmount" + 1));

		List<String> usedIds = pluginCache.getStringList("UsedIds");
		if (!usedIds.contains(nextId.toString())) {
			usedIds.add(nextId.toString());
			pluginCache.set("UsedIds", nextId);
		}
		FileManager.savePluginCache(pluginCache);

		return nextId;
	}
}