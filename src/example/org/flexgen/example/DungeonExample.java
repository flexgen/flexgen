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

package org.flexgen.example;

import java.awt.Color;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.flexgen.map.MapGenerator;
import org.flexgen.map.MapTile;
import org.flexgen.map.MapTileEdge;
import org.flexgen.map.MapTileEdgePosition;
import org.flexgen.map.MapTileLocation;
import org.flexgen.map.MapTileOrientation;
import org.flexgen.map.MapTileType;
import org.flexgen.map.MapUnit;
import org.flexgen.util.ImprovedRandom;

/**
 * Class implementing an example application for the FlexGen library that generates a map for a
 * dungeon.
 */
public class DungeonExample
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
     * Door map unit.
     */
    private static final MapUnit DOOR_UNIT = new MapUnit( "Door" );

    /**
     * Open tile edge.
     */
    private static final MapTileEdge OPEN_EDGE = new MapTileEdge( "Open" );

    /**
     * Left wall tile edge.
     */
    private static final MapTileEdge LEFT_WALL_EDGE = new MapTileEdge( "Left Wall" );

    /**
     * Right wall tile edge.
     */
    private static final MapTileEdge RIGHT_WALL_EDGE = new MapTileEdge( "Right Wall" );

    /**
     * All wall tile edge.
     */
    private static final MapTileEdge WALL_EDGE = new MapTileEdge( "All Wall" );

    /**
     * Hallway tile edge.
     */
    private static final MapTileEdge HALLWAY_EDGE = new MapTileEdge( "Hallway" );

    /**
     * Doorway tile edge.
     */
    private static final MapTileEdge DOORWAY_EDGE = new MapTileEdge( "Doorway" );

    static
    {
        LEFT_WALL_EDGE.addMatchingMapTileEdge( RIGHT_WALL_EDGE );
        RIGHT_WALL_EDGE.addMatchingMapTileEdge( LEFT_WALL_EDGE );
        DOORWAY_EDGE.addMatchingMapTileEdge( HALLWAY_EDGE );
        HALLWAY_EDGE.addMatchingMapTileEdge( HALLWAY_EDGE );
        HALLWAY_EDGE.addMatchingMapTileEdge( DOORWAY_EDGE );
    }

    /**
     * Tile type representing open floor.
     */
    private static final MapTileType OPEN_FLOOR_TYPE = new MapTileType(
            "Open Floor", 1000,
            new MapUnit[][]
            {
                { FLOOR_UNIT, FLOOR_UNIT, FLOOR_UNIT },
                { FLOOR_UNIT, FLOOR_UNIT, FLOOR_UNIT },
                { FLOOR_UNIT, FLOOR_UNIT, FLOOR_UNIT }
            },
            new MapTileEdge[]
            {
                OPEN_EDGE,
                OPEN_EDGE,
                OPEN_EDGE,
                OPEN_EDGE
            },
            new MapTileOrientation[]
            {
                MapTileOrientation.UPRIGHT
            },
            new MapTileEdgePosition[]
            {
                MapTileEdgePosition.TOP,
                MapTileEdgePosition.RIGHT,
                MapTileEdgePosition.BOTTOM,
                MapTileEdgePosition.LEFT
            } );

    /**
     * Tile type representing open floor with one corner as a wall.
     */
    private static final MapTileType ONE_CORNER_WALL_TYPE = new MapTileType(
            "One Corner Wall", 100,
            new MapUnit[][]
            {
                { WALL_UNIT,  FLOOR_UNIT, FLOOR_UNIT },
                { FLOOR_UNIT, FLOOR_UNIT, FLOOR_UNIT },
                { FLOOR_UNIT, FLOOR_UNIT, FLOOR_UNIT }
            },
            new MapTileEdge[]
            {
                LEFT_WALL_EDGE,
                OPEN_EDGE,
                OPEN_EDGE,
                RIGHT_WALL_EDGE
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
                MapTileEdgePosition.BOTTOM,
                MapTileEdgePosition.LEFT
            } );

    /**
     * Tile type representing open floor with two corners as a wall.
     */
    private static final MapTileType TWO_CORNER_WALL_TYPE = new MapTileType(
            "Two Corner Wall", 50,
            new MapUnit[][]
            {
                { WALL_UNIT,  FLOOR_UNIT, FLOOR_UNIT },
                { FLOOR_UNIT, FLOOR_UNIT, FLOOR_UNIT },
                { FLOOR_UNIT, FLOOR_UNIT, WALL_UNIT  }
            },
            new MapTileEdge[]
            {
                LEFT_WALL_EDGE,
                RIGHT_WALL_EDGE,
                LEFT_WALL_EDGE,
                RIGHT_WALL_EDGE
            },
            new MapTileOrientation[]
            {
                MapTileOrientation.UPRIGHT,
                MapTileOrientation.CLOCKWISE
            },
            new MapTileEdgePosition[]
            {
                MapTileEdgePosition.TOP,
                MapTileEdgePosition.RIGHT,
                MapTileEdgePosition.BOTTOM,
                MapTileEdgePosition.LEFT
            } );

    /**
     * Tile type representing open floor with one edge as a wall.
     */
    private static final MapTileType WALL_TYPE = new MapTileType(
            "Wall", 1000,
            new MapUnit[][]
            {
                { WALL_UNIT,  WALL_UNIT,  WALL_UNIT  },
                { FLOOR_UNIT, FLOOR_UNIT, FLOOR_UNIT },
                { FLOOR_UNIT, FLOOR_UNIT, FLOOR_UNIT }
            },
            new MapTileEdge[]
            {
                WALL_EDGE,
                LEFT_WALL_EDGE,
                OPEN_EDGE,
                RIGHT_WALL_EDGE
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
                MapTileEdgePosition.RIGHT,
                MapTileEdgePosition.BOTTOM,
                MapTileEdgePosition.LEFT
            } );

    /**
     * Tile type representing open floor with one edge as a door.
     */
    private static final MapTileType DOORWAY_TYPE = new MapTileType(
            "Doorway", 100,
            new MapUnit[][]
            {
                { WALL_UNIT,  DOOR_UNIT,  WALL_UNIT  },
                { FLOOR_UNIT, FLOOR_UNIT, FLOOR_UNIT },
                { FLOOR_UNIT, FLOOR_UNIT, FLOOR_UNIT }
            },
            new MapTileEdge[]
            {
                DOORWAY_EDGE,
                LEFT_WALL_EDGE,
                OPEN_EDGE,
                RIGHT_WALL_EDGE
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
                MapTileEdgePosition.BOTTOM,
                MapTileEdgePosition.LEFT
            } );

    /**
     * Tile type representing a corner wall.
     */
    private static final MapTileType CORNER_TYPE = new MapTileType(
            "Corner", 500,
            new MapUnit[][]
            {
                { WALL_UNIT, WALL_UNIT,  WALL_UNIT  },
                { WALL_UNIT, FLOOR_UNIT, FLOOR_UNIT },
                { WALL_UNIT, FLOOR_UNIT, FLOOR_UNIT }
            },
            new MapTileEdge[]
            {
                WALL_EDGE,
                LEFT_WALL_EDGE,
                RIGHT_WALL_EDGE,
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
                MapTileEdgePosition.RIGHT,
                MapTileEdgePosition.BOTTOM
            } );

    /**
     * Tile type representing a corner wall with a door on the right side.
     */
    private static final MapTileType RIGHT_DOOR_CORNER_TYPE = new MapTileType(
            "Right Door Corner", 50,
            new MapUnit[][]
            {
                { WALL_UNIT, DOOR_UNIT,  WALL_UNIT  },
                { WALL_UNIT, FLOOR_UNIT, FLOOR_UNIT },
                { WALL_UNIT, FLOOR_UNIT, FLOOR_UNIT }
            },
            new MapTileEdge[]
            {
                DOORWAY_EDGE,
                LEFT_WALL_EDGE,
                RIGHT_WALL_EDGE,
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
                MapTileEdgePosition.RIGHT,
                MapTileEdgePosition.BOTTOM
            } );

    /**
     * Tile type representing a corner wall with a door on the left side.
     */
    private static final MapTileType LEFT_DOOR_CORNER_TYPE = new MapTileType(
            "Left Door Corner", 50,
            new MapUnit[][]
            {
                { WALL_UNIT, WALL_UNIT,  WALL_UNIT  },
                { DOOR_UNIT, FLOOR_UNIT, FLOOR_UNIT },
                { WALL_UNIT, FLOOR_UNIT, FLOOR_UNIT }
            },
            new MapTileEdge[]
            {
                WALL_EDGE,
                LEFT_WALL_EDGE,
                RIGHT_WALL_EDGE,
                DOORWAY_EDGE
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
                MapTileEdgePosition.RIGHT,
                MapTileEdgePosition.BOTTOM,
                MapTileEdgePosition.LEFT
            } );

    /**
     * Tile type representing a corner wall with a door on both sides.
     */
    private static final MapTileType TWO_DOOR_CORNER_TYPE = new MapTileType(
            "Two Door Corner", 25,
            new MapUnit[][]
            {
                { WALL_UNIT, DOOR_UNIT,  WALL_UNIT  },
                { DOOR_UNIT, FLOOR_UNIT, FLOOR_UNIT },
                { WALL_UNIT, FLOOR_UNIT, FLOOR_UNIT }
            },
            new MapTileEdge[]
            {
                DOORWAY_EDGE,
                LEFT_WALL_EDGE,
                RIGHT_WALL_EDGE,
                DOORWAY_EDGE
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
                MapTileEdgePosition.BOTTOM,
                MapTileEdgePosition.LEFT
            } );

    /**
     * Tile type representing a straight hallway.
     */
    private static final MapTileType STRAIGHT_HALLWAY_TYPE = new MapTileType(
            "Straight Hallway", 1500,
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
    private static final MapTileType CORNER_HALLWAY_TYPE = new MapTileType(
            "Corner Hallway", 300,
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
    private static final MapTileType THREE_WAY_HALLWAY_TYPE = new MapTileType(
            "Three-Way Hallway", 50,
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
     * Tile type representing a hallway with a four-way intersection.
     */
    private static final MapTileType FOUR_WAY_HALLWAY_TYPE = new MapTileType(
            "Four-Way Hallway", 25,
            new MapUnit[][]
            {
                { WALL_UNIT,  FLOOR_UNIT, WALL_UNIT  },
                { FLOOR_UNIT, FLOOR_UNIT, FLOOR_UNIT },
                { WALL_UNIT,  FLOOR_UNIT, WALL_UNIT  }
            },
            new MapTileEdge[]
            {
                HALLWAY_EDGE,
                HALLWAY_EDGE,
                HALLWAY_EDGE,
                HALLWAY_EDGE
            },
            new MapTileOrientation[]
            {
                MapTileOrientation.UPRIGHT
            },
            new MapTileEdgePosition[]
            {
                MapTileEdgePosition.TOP,
                MapTileEdgePosition.RIGHT,
                MapTileEdgePosition.BOTTOM,
                MapTileEdgePosition.LEFT
            } );

    /**
     * Array of all map tile types for this example.
     */
    private static final MapTileType[] MAP_TILE_TYPES = new MapTileType[]
    {
        OPEN_FLOOR_TYPE,
        ONE_CORNER_WALL_TYPE,
        TWO_CORNER_WALL_TYPE,
        WALL_TYPE,
        DOORWAY_TYPE,
        CORNER_TYPE,
        RIGHT_DOOR_CORNER_TYPE,
        LEFT_DOOR_CORNER_TYPE,
        TWO_DOOR_CORNER_TYPE,
        STRAIGHT_HALLWAY_TYPE,
        CORNER_HALLWAY_TYPE,
        THREE_WAY_HALLWAY_TYPE,
        FOUR_WAY_HALLWAY_TYPE
    };

    /**
     * Map of map units to colors for this example.
     */
    private static final Map< MapUnit, Color > COLOR_MAP;

    static
    {
        COLOR_MAP = new HashMap< MapUnit, Color >();

        COLOR_MAP.put( FLOOR_UNIT, new Color( 170, 170, 170 ));
        COLOR_MAP.put( WALL_UNIT,  new Color(  85,  85,  85 ));
        COLOR_MAP.put( DOOR_UNIT,  new Color( 255,   0, 255 ));
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
        System.out.println( "Example Application - Dungeon" );

        String dirName = "work/worlds/Dungeon";
        File dir = new File( dirName );
        dir.mkdirs();

        MapGenerator mapGenerator =
                new MapGenerator( new ImprovedRandom(), MAP_TILE_TYPES, -10, -10, 10, 10 );

        MapRenderer mapRenderer = new MapRenderer( dirName + "/", 5, COLOR_MAP );
        mapGenerator.addMapTileAddedListener( mapRenderer );
        mapGenerator.addMapTileRemovedListener( mapRenderer );

        mapGenerator.addMapTile( new MapTileLocation( 0, 0 ),
                                 new MapTile( OPEN_FLOOR_TYPE, MapTileOrientation.UPRIGHT ));

        mapGenerator.generate();
    }

    /**
     * Private constructor to keep this class from being instantiated since all methods are static.
     */
    private DungeonExample()
    {
    }
}
