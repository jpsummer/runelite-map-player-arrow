package com.MapPlayerIndicator;

import com.MapPlayerIndicator.overlay.PlayerIndicatorWorldMapOverlay;
import com.google.inject.Provides;
import javax.inject.Inject;
import net.runelite.api.events.GameTick;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

@PluginDescriptor(
		name = "World Map Player Indicator",
		description = "Places an arrow indicator for the characters facing direction on the world map.",
		tags = {"world", "player", "character", "indicator", "map", "direction", "arrow"}
)
public class PlayerIndicatorPlugin extends Plugin
{
	@Inject private PlayerIndicatorWorldMapOverlay worldMapIndicator;

	@Provides
	PlayerIndicatorConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(PlayerIndicatorConfig.class);
	}

	@Override
	protected void startUp()
	{
		worldMapIndicator.startUp();
	}

	@Override
	protected void shutDown()
	{
		worldMapIndicator.shutDown();
	}

	@Subscribe
	public void onGameTick(GameTick tick)
	{
		worldMapIndicator.onGameTick();
	}
}