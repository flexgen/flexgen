/*

FlexGen : Flexible Map Generator Library

Copyright (C) 2009-2010 Jeffrey J. Weston <jjweston@gmail.com>
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

package org.flexgen.test.helper.tiles;

import org.flexgen.map.MapTileEdge;
import org.flexgen.map.MapTileOrientation;
import org.flexgen.map.MapTileType;
import org.flexgen.map.MapUnit;

/**
 * Test helper class containing a set of river tiles.
 */
public class RiverTiles
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
    public static final MapTileType ALL_GRASS = new MapTileType(
            "All Grass", 1,
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
    public static final MapTileType STRAIGHT_RIVER = new MapTileType(
            "Straight River", 1,
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
     * Array of all map tile types for this helper.
     */
    public static final MapTileType[] MAP_TILE_TYPES = new MapTileType[]
    {
        ALL_GRASS,
        STRAIGHT_RIVER
    };

    /**
     * Private constructor to keep this class from being instantiated since all methods are static.
     */
    private RiverTiles()
    {
    }
}
