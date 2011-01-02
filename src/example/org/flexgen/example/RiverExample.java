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
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.flexgen.map.MapGenerator;
import org.flexgen.map.MapTile;
import org.flexgen.map.MapTileEdge;
import org.flexgen.map.MapTileLocation;
import org.flexgen.map.MapTileOrientation;
import org.flexgen.map.MapTileType;
import org.flexgen.map.MapUnit;
import org.flexgen.map.RectangularMapTileLocationFilter;
import org.flexgen.util.ImprovedRandom;

/**
 * Class implementing an example application for the FlexGen library that generates a map with grass
 * and rivers.
 */
public class RiverExample
{
    /**
     * Grass map unit.
     */
    private static final MapUnit GRASS = new MapUnit( "Grass" );

    /**
     * River map unit.
     */
    private static final MapUnit RIVER = new MapUnit( "River" );

    /**
     * Grass tile edge.
     */
    private static final MapTileEdge GRASS_EDGE = new MapTileEdge( "Grass Edge" );

    /**
     * River tile edge.
     */
    private static final MapTileEdge RIVER_EDGE = new MapTileEdge( "River Edge" );

    /**
     * Tile type representing grass.
     */
    private static final MapTileType ALL_GRASS = new MapTileType(
            "All Grass", 5000,
            new MapUnit[][]
            {
                { GRASS, GRASS, GRASS },
                { GRASS, GRASS, GRASS },
                { GRASS, GRASS, GRASS }
            },
            new MapTileEdge[]
            {
                GRASS_EDGE,
                GRASS_EDGE,
                GRASS_EDGE,
                GRASS_EDGE
            },
            new MapTileOrientation[]
            {
                MapTileOrientation.UPRIGHT
            } );

    /**
     * Tile type representing a straight river.
     */
    private static final MapTileType STRAIGHT_RIVER = new MapTileType(
            "Straight River", 1000,
            new MapUnit[][]
            {
                { GRASS, RIVER, GRASS },
                { GRASS, RIVER, GRASS },
                { GRASS, RIVER, GRASS }
            },
            new MapTileEdge[]
            {
                RIVER_EDGE,
                GRASS_EDGE,
                RIVER_EDGE,
                GRASS_EDGE
            },
            new MapTileOrientation[]
            {
                MapTileOrientation.UPRIGHT,
                MapTileOrientation.CLOCKWISE
            } );

    /**
     * Tile type representing a river corner.
     */
    private static final MapTileType CORNER_RIVER = new MapTileType(
            "Corner River", 500,
            new MapUnit[][]
            {
                { GRASS, RIVER, GRASS },
                { GRASS, RIVER, RIVER },
                { GRASS, GRASS, GRASS }
            },
            new MapTileEdge[]
            {
                RIVER_EDGE,
                RIVER_EDGE,
                GRASS_EDGE,
                GRASS_EDGE
            },
            new MapTileOrientation[]
            {
                MapTileOrientation.UPRIGHT,
                MapTileOrientation.CLOCKWISE,
                MapTileOrientation.FLIPPED,
                MapTileOrientation.COUNTER_CLOCKWISE
            } );

    /**
     * Tile type representing a river split three ways.
     */
    private static final MapTileType THREE_WAY_RIVER = new MapTileType(
            "Three Way River", 250,
            new MapUnit[][]
            {
                { GRASS, RIVER, GRASS },
                { RIVER, RIVER, RIVER },
                { GRASS, GRASS, GRASS }
            },
            new MapTileEdge[]
            {
                RIVER_EDGE,
                RIVER_EDGE,
                GRASS_EDGE,
                RIVER_EDGE
            },
            new MapTileOrientation[]
            {
                MapTileOrientation.UPRIGHT,
                MapTileOrientation.CLOCKWISE,
                MapTileOrientation.FLIPPED,
                MapTileOrientation.COUNTER_CLOCKWISE
            } );

    /**
     * Tile type representing a river split four ways.
     */
    private static final MapTileType FOUR_WAY_RIVER = new MapTileType(
            "Four Way River", 100,
            new MapUnit[][]
            {
                { GRASS, RIVER, GRASS },
                { RIVER, RIVER, RIVER },
                { GRASS, RIVER, GRASS }
            },
            new MapTileEdge[]
            {
                RIVER_EDGE,
                RIVER_EDGE,
                RIVER_EDGE,
                RIVER_EDGE
            },
            new MapTileOrientation[]
            {
                MapTileOrientation.UPRIGHT
            } );

    /**
     * Tile type representing a dead-end for a river.
     */
    private static final MapTileType DEAD_END_RIVER = new MapTileType(
            "Dead End River", 1,
            new MapUnit[][]
            {
                { GRASS, RIVER, GRASS },
                { GRASS, GRASS, GRASS },
                { GRASS, GRASS, GRASS }
            },
            new MapTileEdge[]
            {
                RIVER_EDGE,
                GRASS_EDGE,
                GRASS_EDGE,
                GRASS_EDGE
            },
            new MapTileOrientation[]
            {
                MapTileOrientation.UPRIGHT,
                MapTileOrientation.CLOCKWISE,
                MapTileOrientation.FLIPPED,
                MapTileOrientation.COUNTER_CLOCKWISE
            } );

    /**
     * Array of all map tile types for this example.
     */
    private static final MapTileType[] MAP_TILE_TYPES = new MapTileType[]
    {
        ALL_GRASS,
        STRAIGHT_RIVER,
        CORNER_RIVER,
        THREE_WAY_RIVER,
        FOUR_WAY_RIVER,
        DEAD_END_RIVER
    };

    /**
     * Map of map units to colors for this example.
     */
    private static final Map< MapUnit, Color > COLOR_MAP;

    static
    {
        COLOR_MAP = new HashMap< MapUnit, Color >();

        COLOR_MAP.put( GRASS, new Color(   0, 255,   0 ));
        COLOR_MAP.put( RIVER, new Color(   0,   0, 255 ));
    }

    /**
     * Main entry point for the application.
     *
     * @param args
     *            Command line arguments.
     */
    public static void main( String[] args )
    {
        System.out.println( "FlexGen : Flexible Map Generator Library" );
        System.out.println();
        System.out.println( "Example Application - Grass and Rivers" );

        String dirName = "work/worlds/River";
        File dir = new File( dirName );
        dir.mkdirs();

        GrowingMapTileLocationFilter growingMapTileLocationFilter =
                new GrowingMapTileLocationFilter(
                        new RectangularMapTileLocationFilter( -10, -10, 10, 10 ));

        MapGenerator mapGenerator = new MapGenerator(
                new ImprovedRandom(), MAP_TILE_TYPES, growingMapTileLocationFilter );

        FullMapRenderer fullMapRenderer = new FullMapRenderer( dirName + "/", 5, COLOR_MAP );
        mapGenerator.addMapTileAddedListener( fullMapRenderer );
        mapGenerator.addMapTileRemovedListener( fullMapRenderer );

        mapGenerator.addMapTile( new MapTileLocation( 0, 0 ),
                                 new MapTile( ALL_GRASS, MapTileOrientation.UPRIGHT ));

        mapGenerator.generate();
    }

    /**
     * Private constructor to keep this class from being instantiated since all methods are static.
     */
    private RiverExample()
    {
    }
}
