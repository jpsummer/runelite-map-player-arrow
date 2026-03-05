package com.MapPlayerIndicator;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public final class ImageUtil
{
    private ImageUtil() {}

    public static BufferedImage rotateAndScale(BufferedImage src, double radians, int outSize)
    {
        if (src == null || outSize <= 0)
        {
            return null;
        }

        BufferedImage out = new BufferedImage(outSize, outSize, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = out.createGraphics();
        try
        {
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            double cx = outSize / 2.0;
            double cy = outSize / 2.0;

            AffineTransform at = new AffineTransform();
            at.translate(cx, cy);
            at.rotate(radians);

            double sx = (double) outSize / src.getWidth();
            double sy = (double) outSize / src.getHeight();
            at.scale(sx, sy);

            at.translate(-src.getWidth() / 2.0, -src.getHeight() / 2.0);

            g.drawImage(src, at, null);
        }
        finally
        {
            g.dispose();
        }
        return out;
    }
}
