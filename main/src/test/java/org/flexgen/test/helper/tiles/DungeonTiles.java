/*

FlexGen : Flexible Map Generator Library

Copyright (C) 2009-2013 Jeffrey J. Weston <jjweston@gmail.com>
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
import org.flexgen.map.MapTileEdgePosition;
import org.flexgen.map.MapTileOrientation;
import org.flexgen.map.MapTileType;
import org.flexgen.map.MapUnit;

/**
 * Test helper class containing a set of dungeon tiles.
 */
public class DungeonTiles
{
    /**
     * Floor map unit.
     */
    private static final MapUnit FLOOR_UNIT = new MapUnit( "Floor" );

    /**
     * Wall map unit.
     */
    private static final MapUnit WALL_UNIT = new MapUnit( "Wall" );

    /**
     * All wall tile edge.
     */
    private static final MapTileEdge WALL_EDGE = new MapTileEdge( "All Wall" );

    /**
     * Hallway tile edge.
     */
    private static final MapTileEdge HALLWAY_EDGE = new MapTileEdge( "Hallway" );

    /**
     * Tile type representing a straight hallway.
     */
    public static final MapTileType STRAIGHT_HALLWAY_TYPE = new MapTileType(
            "Straight Hallway", 1,
            new MapUnit[][]
            {
                { WALL_UNIT, FLOOR_UNIT, WALL_UNIT },
                { WALL_UNIT, FLOOR_UNIT, WALL_UNIT },
                { WALL_UNIT, FLOOR_UNIT, WALL_UNIT }
            },
            new MapTileEdge[]
            {
                HALLWAY_EDGE,
                WALL_EDGE,
                HALLWAY_EDGE,
                WALL_EDGE
            },
            new MapTileOrientation[]
            {
                MapTileOrientation.UPRIGHT,
                MapTileOrientation.CLOCKWISE
            },
            new MapTileEdgePosition[]
            {
                MapTileEdgePosition.TOP,
                MapTileEdgePosition.BOTTOM
            } );

    /**
     * Tile type representing a corner hallway.
     */
    public static final MapTileType CORNER_HALLWAY_TYPE = new MapTileType(
            "Corner Hallway", 1,
            new MapUnit[][]
            {
                { WALL_UNIT, FLOOR_UNIT, WALL_UNIT  },
                { WALL_UNIT, FLOOR_UNIT, FLOOR_UNIT },
                { WALL_UNIT, WALL_UNIT,  WALL_UNIT  }
            },
            new MapTileEdge[]
            {
                HALLWAY_EDGE,
                HALLWAY_EDGE,
                WALL_EDGE,
                WALL_EDGE
            },
            new MapTileOrientation[]
            {
                MapTileOrientation.UPRIGHT,
                MapTileOrientation.CLOCKWISE,
                MapTileOrientation.FLIPPED,
                MapTileOrientation.COUNTER_CLOCKWISE
            },
            new MapTileEdgePosition[]
            {
                MapTileEdgePosition.TOP,
                MapTileEdgePosition.RIGHT
            } );

    /**
     * Tile type representing a hallway with a three-way intersection.
     */
    public static final MapTileType THREE_WAY_HALLWAY_TYPE = new MapTileType(
            "Three-Way Hallway", 1,
            new MapUnit[][]
            {
                { WALL_UNIT,  FLOOR_UNIT, WALL_UNIT  },
                { FLOOR_UNIT, FLOOR_UNIT, FLOOR_UNIT },
                { WALL_UNIT,  WALL_UNIT,  WALL_UNIT  }
            },
            new MapTileEdge[]
            {
                HALLWAY_EDGE,
                HALLWAY_EDGE,
                WALL_EDGE,
                HALLWAY_EDGE
            },
            new MapTileOrientation[]
            {
                MapTileOrientation.UPRIGHT,
                MapTileOrientation.CLOCKWISE,
                MapTileOrientation.FLIPPED,
                MapTileOrientation.COUNTER_CLOCKWISE
            },
            new MapTileEdgePosition[]
            {
                MapTileEdgePosition.TOP,
                MapTileEdgePosition.RIGHT,
                MapTileEdgePosition.LEFT
            } );

    /**
     * Private constructor to keep this class from being instantiated since all methods are static.
     */
    private DungeonTiles()
    {
    }
}
