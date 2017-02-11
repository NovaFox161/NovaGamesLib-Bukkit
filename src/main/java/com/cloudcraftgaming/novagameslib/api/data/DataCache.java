package com.cloudcraftgaming.novagameslib.api.data;

import com.cloudcraftgaming.novagameslib.api.utils.FileManager;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.ArrayList;
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

	public static ArrayList<Integer> getAllUsedIDs() {
		ArrayList<Integer> usedIds = new ArrayList<>();
		YamlConfiguration pluginCache = FileManager.getPluginCacheYml();
		List<String> usedIdsString = pluginCache.getStringList("UsedIds");
		for (String s : usedIdsString) {
			if (!usedIds.contains(Integer.valueOf(s))) {
				usedIds.add(Integer.valueOf(s));
			}
		}
		return usedIds;
	}
}