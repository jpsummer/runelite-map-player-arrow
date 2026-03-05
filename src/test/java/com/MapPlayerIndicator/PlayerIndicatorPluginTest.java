package com.MapPlayerIndicator;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class PlayerIndicatorPluginTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(PlayerIndicatorPlugin.class);
		RuneLite.main(args);
	}
}