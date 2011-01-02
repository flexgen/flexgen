/*

FlexGen : Flexible Map Generator Library

Copyright (C) 2009-2011 Jeffrey J. Weston <jjweston@gmail.com>
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

* Redistributions of source code must retain the above copyright notice, this
  list of conditions and the following disclaimer.
* Redistributions in binary form must reproduce the above copyright notice,
  this list of conditions and the following disclaimer in the documentation
  and/or other materials provided with the distribution.
* Neither the name of the FlexGen project nor the names of its contributors
  may be used to endorse or promote products derived from this software
  without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

*/

package org.flexgen.example;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Map;

import javax.imageio.ImageIO;

import org.flexgen.map.MapGenerator;
import org.flexgen.map.MapTileAddedListener;
import org.flexgen.map.MapTileLocation;
import org.flexgen.map.MapTileRemovedListener;
import org.flexgen.map.MapUnit;

/**
 * Class implementing logic for rendering a portion of a map to an image.
 */
public class PartialMapRenderer implements MapTileAddedListener, MapTileRemovedListener
{
    /**
     * Prefix to use for file names.
     */
    private final String fileNamePrefix;

    /**
     * The size (in pixels) of map units in rendered images.
     */
    private final int mapUnitSize;

    /**
     * Map of map units to colors.
     */
    private final Map< MapUnit, Color > colorMap;

    /**
     * Width (in map tiles) of the rendered image.
     */
    private final int width;

    /**
     * Height (in map tiles) of the rendered image.
     */
    private final int height;

    /**
     * Construct a partial map renderer.
     *
     * @param fileNamePrefix
     *            Prefix to use for file names.
     * @param mapUnitSize
     *            The size (in pixels) of map units in rendered images.
     * @param colorMap
     *            Map of map units to colors.
     * @param width
     *            Width (in map tiles) of the rendered image.
     * @param height
     *            Height (in map tiles) of the rendered image.
     */
    public PartialMapRenderer( String fileNamePrefix, int mapUnitSize,
                               Map< MapUnit, Color > colorMap, int width, int height )
    {
        this.fileNamePrefix = fileNamePrefix;
        this.mapUnitSize    = mapUnitSize;
        this.colorMap       = colorMap;
        this.width          = width;
        this.height         = height;
    }

    /**
     * Informs the listener that a map tile has been added at the specified location.
     *
     * @param mapGenerator
     *            Map generator that added the map tile.
     * @param mapTileLocation
     *            Location at which the map tile was added.
     */
    public void mapTileAdded( MapGenerator mapGenerator, MapTileLocation mapTileLocation )
    {
        render( mapGenerator, mapTileLocation );
    }

    /**
     * Informs the listener that a map tile has been removed at the specified location.
     *
     * @param mapGenerator
     *            Map generator that removed the map tile.
     * @param mapTileLocation
     *            Location at which the map tile was removed.
     */
    public void mapTileRemoved( MapGenerator mapGenerator, MapTileLocation mapTileLocation )
    {
        render( mapGenerator, mapTileLocation );
    }

    /**
     * Render a map to a file.
     *
     * @param mapGenerator
     *            Map generator containing the map to render.
     * @param centerMapTileLocation
     *            Map tile location indicating the center of the image.
     */
    private void render( MapGenerator mapGenerator, MapTileLocation centerMapTileLocation )
    {
        // determine the file name for the map
        SimpleDateFormat dateFormat = new SimpleDateFormat( "yyyyMMddHHmmssSSS" );
        GregorianCalendar now = new GregorianCalendar();
        String fileName = fileNamePrefix + dateFormat.format( now.getTime() ) + ".png";

        // determine the minimum and maximum coordinates for image
        int minX = centerMapTileLocation.getX() - ( width  / 2 );
        int minY = centerMapTileLocation.getY() - ( height / 2 );
        int maxX = centerMapTileLocation.getX() + ( width  / 2 );
        int maxY = centerMapTileLocation.getY() + ( height / 2 );
        int pixelCount = mapUnitSize * mapGenerator.getTileSize();

        // render the map
        BufferedImage image = MapRendererHelper.getMapAsImage(
                mapGenerator, minX, minY, maxX, maxY, mapUnitSize, colorMap );

        Graphics2D graphics = image.createGraphics();
        graphics.setColor( new Color( 255, 255, 255 ));
        graphics.drawRect( (( width / 2 ) * pixelCount ) - 1, (( height / 2 ) * pixelCount ) - 1,
                           pixelCount + 1, pixelCount + 1 );

        try
        {
            // save the map as a PNG
            File file = new File( fileName );
            ImageIO.write( image, "png", file );
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }
    }
}
