package com.MapPlayerIndicator.overlay;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import javax.imageio.ImageIO;
import javax.inject.Inject;

import com.MapPlayerIndicator.ImageUtil;
import com.MapPlayerIndicator.PlayerIndicatorConfig;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.Perspective;
import net.runelite.api.Player;
import net.runelite.client.ui.overlay.worldmap.WorldMapPoint;
import net.runelite.client.ui.overlay.worldmap.WorldMapPointManager;

@Slf4j
public class PlayerIndicatorWorldMapOverlay
{
    private final Client client;
    private final PlayerIndicatorConfig config;
    private final WorldMapPointManager worldMapPointManager;

    private WorldMapPoint playerPoint;

    private BufferedImage baseArrow;   // original image

    @Inject
    public PlayerIndicatorWorldMapOverlay(
            Client client,
            PlayerIndicatorConfig config,
            WorldMapPointManager worldMapPointManager
    )
    {
        this.client = client;
        this.config = config;
        this.worldMapPointManager = worldMapPointManager;
    }

    public void startUp() {
        try {
            baseArrow = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/arrow.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (config.worldMapEnabled())
        {
            ensurePointExists();
        }
    }

    public void shutDown()
    {
        removePoint();
    }

    public void onGameTick()
    {
        if (!config.worldMapEnabled())
        {
            removePoint();
            return;
        }

        Player p = client.getLocalPlayer();
        if (p == null)
        {
            return;
        }

        ensurePointExists();

        if (playerPoint == null || baseArrow == null)
        {
            return;
        }

        // Update location
        playerPoint.setWorldPoint(p.getWorldLocation());

        // Rotate to true facing
        double theta = p.getCurrentOrientation() * Perspective.UNIT;

        BufferedImage rotatedScaled = ImageUtil.rotateAndScale(
                baseArrow,
                theta,
                config.worldMapIconSize()
        );

        if (rotatedScaled != null)
        {
            playerPoint.setImage(rotatedScaled);
        }
    }

    private void ensurePointExists()
    {
        if (playerPoint != null)
        {
            return;
        }

        Player p = client.getLocalPlayer();
        if (p == null || baseArrow == null)
        {
            return;
        }

        BufferedImage initial = ImageUtil.rotateAndScale(
                baseArrow,
                p.getCurrentOrientation() * Perspective.UNIT,
                config.worldMapIconSize()
        );

        playerPoint = new WorldMapPoint(p.getWorldLocation(), initial);
        playerPoint.setSnapToEdge(false);
        playerPoint.setJumpOnClick(false);

        worldMapPointManager.add(playerPoint);
    }

    private void removePoint()
    {
        if (playerPoint != null)
        {
            worldMapPointManager.remove(playerPoint);
            playerPoint = null;
        }
    }
}