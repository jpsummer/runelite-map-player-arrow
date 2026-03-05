package com.MapPlayerIndicator;

import java.awt.Color;
import net.runelite.client.config.*;

@ConfigGroup("MapPlayerIndicator")
public interface PlayerIndicatorConfig extends Config
{
	@ConfigItem(
			keyName = "worldMapIconSize",
			name = "World map icon size",
			description = "Rendered size in pixels of the world map arrow."
	)
	default int worldMapIconSize()
	{
		return 42;
	}

	@ConfigItem(
			keyName = "worldMapEnabled",
			name = "Enable world map arrow",
			description = "Show the custom arrow on the world map."
	)
	default boolean worldMapEnabled()
	{
		return true;
	}
}