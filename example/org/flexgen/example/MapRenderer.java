/*

FlexGen : Flexible Map Generator Library

Copyright (C) 2009 - Jeffrey J. Weston <jjweston@gmail.com>
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
import java.util.Map;

import javax.imageio.ImageIO;

import org.flexgen.map.MapGenerator;
import org.flexgen.map.MapTile;
import org.flexgen.map.MapTileLocation;
import org.flexgen.map.MapUnit;

/**
 * Class implementing logic for rendering a map to an image.
 */
public class MapRenderer
{
    /**
     * The size (in pixels) of map units in rendered images.
     */
    private final int mapUnitSize;

    /**
     * Map of map units to colors.
     */
    private final Map< MapUnit, Color > colorMap;

    /**
     * Construct a map renderer.
     *
     * @param mapUnitSize
     *            The size (in pixels) of map units in rendered images.
     * @param colorMap
     *            Map of map units to colors.
     */
    public MapRenderer( int mapUnitSize, Map< MapUnit, Color > colorMap )
    {
        this.mapUnitSize = mapUnitSize;
        this.colorMap    = colorMap;
    }

    /**
     * Render a map to a file.
     *
     * @param mapGenerator
     *            Map generator containing the map to render.
     * @param fileName
     *            File name to use.
     */
    public void render( MapGenerator mapGenerator, String fileName )
    {
        // get needed information about the map
        int minX     = mapGenerator.getMinX();
        int minY     = mapGenerator.getMinY();
        int maxX     = mapGenerator.getMaxX();
        int maxY     = mapGenerator.getMaxY();
        int tileSize = mapGenerator.getTileSize();

        // create a buffered image on which to render the map
        BufferedImage image = new BufferedImage(
                (( maxX - minX ) + 1 ) * tileSize * mapUnitSize,
                (( maxY - minY ) + 1 ) * tileSize * mapUnitSize,
                BufferedImage.TYPE_INT_RGB );
        Graphics2D graphics = image.createGraphics();

        // render the map
        for ( int tileY = minY; tileY <= maxY; tileY++ )
        {
            for ( int tileX = minX; tileX <= maxX; tileX++ )
            {
                MapTileLocation mapTileLocation = new MapTileLocation( tileX, tileY );
                MapTile mapTile = mapGenerator.getMapTile( mapTileLocation );

                for ( int unitY = 0; unitY < tileSize; unitY++ )
                {
                    for ( int unitX = 0; unitX < tileSize; unitX++ )
                    {
                        Color color = null;

                        if ( mapTile != null )
                        {
                            MapUnit mapUnit = mapTile.getMapUnit( unitX, unitY );
                            color = colorMap.get( mapUnit );
                        }

                        if ( color == null )
                        {
                            color = new Color( 0, 0, 0 );
                        }

                        graphics.setColor( color );

                        int renderX = ((( tileX - minX ) * tileSize ) + unitX ) * mapUnitSize;
                        int renderY = ((( tileY - minY ) * tileSize ) + unitY ) * mapUnitSize;

                        graphics.fillRect( renderX, renderY, mapUnitSize, mapUnitSize );
                    }
                }
            }
        }

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
