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

package org.flexgen.map.test;

import org.junit.Test;

import org.flexgen.map.MapGenerator;
import org.flexgen.map.MapTile;
import org.flexgen.map.MapTileLocation;
import org.flexgen.map.MapTileOrientation;
import org.flexgen.map.MapTileType;
import org.flexgen.map.RectangularMapTileLocationFilter;
import org.flexgen.test.helper.MapGeneratorHelper;
import org.flexgen.test.helper.tiles.DungeonTiles;
import org.flexgen.test.helper.tiles.RiverTiles;
import org.flexgen.util.ImprovedRandom;
import org.flexgen.util.test.support.TestImprovedRandom;

/**
 * Test class for the MapGenerator class. Contains tests for the generate() method.
 */
public class MapGeneratorTest_Generate
{
    /**
     * Verify that the generate() method works correctly when there are no open locations.
     */
    @Test
    public void generate_noOpenLocations()
    {
        MapGenerator expectedMapGenerator =
                new MapGenerator( new ImprovedRandom(), RiverTiles.MAP_TILE_TYPES,
                                  new RectangularMapTileLocationFilter( 0, 0, 0, 0 ));
        expectedMapGenerator.addMapTile(
                new MapTileLocation( 0, 0 ),
                new MapTile( RiverTiles.ALL_GRASS, MapTileOrientation.UPRIGHT ));

        MapGenerator actualMapGenerator =
                new MapGenerator( new ImprovedRandom(), RiverTiles.MAP_TILE_TYPES,
                                  new RectangularMapTileLocationFilter( 0, 0, 0, 0 ));
        actualMapGenerator.addMapTile(
                new MapTileLocation( 0, 0 ),
                new MapTile( RiverTiles.ALL_GRASS, MapTileOrientation.UPRIGHT ));

        actualMapGenerator.generate();

        MapGeneratorHelper.assertAreEqual( expectedMapGenerator, actualMapGenerator );
    }

    /**
     * Verify that the generate() method works correctly when generating a map with one open
     * location that is on the top side of an existing tile.
     */
    @Test
    public void generate_topNeighbor()
    {
        MapGenerator expectedMapGenerator =
                new MapGenerator( new ImprovedRandom(), RiverTiles.MAP_TILE_TYPES,
                                  new RectangularMapTileLocationFilter( 0, 0, 0, 1 ));
        expectedMapGenerator.addMapTile(
                new MapTileLocation( 0, 0 ),
                new MapTile( RiverTiles.STRAIGHT_RIVER, MapTileOrientation.UPRIGHT ));
        expectedMapGenerator.addMapTile(
                new MapTileLocation( 0, 1 ),
                new MapTile( RiverTiles.STRAIGHT_RIVER, MapTileOrientation.UPRIGHT ));

        MapGenerator actualMapGenerator =
                new MapGenerator( new ImprovedRandom(), RiverTiles.MAP_TILE_TYPES,
                                  new RectangularMapTileLocationFilter( 0, 0, 0, 1 ));
        actualMapGenerator.addMapTile(
                new MapTileLocation( 0, 1 ),
                new MapTile( RiverTiles.STRAIGHT_RIVER, MapTileOrientation.UPRIGHT ));

        actualMapGenerator.generate();

        MapGeneratorHelper.assertAreEqual( expectedMapGenerator, actualMapGenerator );
    }

    /**
     * Verify that the generate() method works correctly when generating a map with one open
     * location that is on the bottom side of an existing tile.
     */
    @Test
    public void generate_bottomNeighbor()
    {
        MapGenerator expectedMapGenerator =
                new MapGenerator( new ImprovedRandom(), RiverTiles.MAP_TILE_TYPES,
                                  new RectangularMapTileLocationFilter( 0, 0, 0, 1 ));
        expectedMapGenerator.addMapTile(
                new MapTileLocation( 0, 0 ),
                new MapTile( RiverTiles.STRAIGHT_RIVER, MapTileOrientation.UPRIGHT ));
        expectedMapGenerator.addMapTile(
                new MapTileLocation( 0, 1 ),
                new MapTile( RiverTiles.STRAIGHT_RIVER, MapTileOrientation.UPRIGHT ));

        MapGenerator actualMapGenerator =
                new MapGenerator( new ImprovedRandom(), RiverTiles.MAP_TILE_TYPES,
                                  new RectangularMapTileLocationFilter( 0, 0, 0, 1 ));
        actualMapGenerator.addMapTile(
                new MapTileLocation( 0, 0 ),
                new MapTile( RiverTiles.STRAIGHT_RIVER, MapTileOrientation.UPRIGHT ));

        actualMapGenerator.generate();

        MapGeneratorHelper.assertAreEqual( expectedMapGenerator, actualMapGenerator );
    }

    /**
     * Verify that the generate() method works correctly when generating a map with one open
     * location that is on the left side of an existing tile.
     */
    @Test
    public void generate_leftNeighbor()
    {
        MapGenerator expectedMapGenerator =
                new MapGenerator( new ImprovedRandom(), RiverTiles.MAP_TILE_TYPES,
                                  new RectangularMapTileLocationFilter( 0, 0, 1, 0 ));
        expectedMapGenerator.addMapTile(
                new MapTileLocation( 0, 0 ),
                new MapTile( RiverTiles.STRAIGHT_RIVER, MapTileOrientation.CLOCKWISE ));
        expectedMapGenerator.addMapTile(
                new MapTileLocation( 1, 0 ),
                new MapTile( RiverTiles.STRAIGHT_RIVER, MapTileOrientation.CLOCKWISE ));

        MapGenerator actualMapGenerator =
                new MapGenerator( new ImprovedRandom(), RiverTiles.MAP_TILE_TYPES,
                                  new RectangularMapTileLocationFilter( 0, 0, 1, 0 ));
        actualMapGenerator.addMapTile(
                new MapTileLocation( 0, 0 ),
                new MapTile( RiverTiles.STRAIGHT_RIVER, MapTileOrientation.CLOCKWISE ));

        actualMapGenerator.generate();

        MapGeneratorHelper.assertAreEqual( expectedMapGenerator, actualMapGenerator );
    }

    /**
     * Verify that the generate() method works correctly when generating a map with one open
     * location that is on the right side of an existing tile.
     */
    @Test
    public void generate_rightNeighbor()
    {
        MapGenerator expectedMapGenerator =
                new MapGenerator( new ImprovedRandom(), RiverTiles.MAP_TILE_TYPES,
                                  new RectangularMapTileLocationFilter( 0, 0, 1, 0 ));
        expectedMapGenerator.addMapTile(
                new MapTileLocation( 0, 0 ),
                new MapTile( RiverTiles.STRAIGHT_RIVER, MapTileOrientation.CLOCKWISE ));
        expectedMapGenerator.addMapTile(
                new MapTileLocation( 1, 0 ),
                new MapTile( RiverTiles.STRAIGHT_RIVER, MapTileOrientation.CLOCKWISE ));

        MapGenerator actualMapGenerator =
                new MapGenerator( new ImprovedRandom(), RiverTiles.MAP_TILE_TYPES,
                                  new RectangularMapTileLocationFilter( 0, 0, 1, 0 ));
        actualMapGenerator.addMapTile(
                new MapTileLocation( 1, 0 ),
                new MapTile( RiverTiles.STRAIGHT_RIVER, MapTileOrientation.CLOCKWISE ));

        actualMapGenerator.generate();

        MapGeneratorHelper.assertAreEqual( expectedMapGenerator, actualMapGenerator );
    }

    /**
     * Verify that the generate() method works correctly when using a tile set with limited open
     * map tile edge positions.
     */
    @Test
    public void generate_limitedOpenMapTileEdgePositions()
    {
        MapTileType[] mapTileTypes = new MapTileType[]
        {
            DungeonTiles.STRAIGHT_HALLWAY_TYPE
        };

        MapGenerator expectedMapGenerator =
                new MapGenerator( new ImprovedRandom(), mapTileTypes,
                                  new RectangularMapTileLocationFilter( -1, -1, 1, 1 ));
        expectedMapGenerator.addMapTile(
                new MapTileLocation( 0, -1 ),
                new MapTile( DungeonTiles.STRAIGHT_HALLWAY_TYPE, MapTileOrientation.UPRIGHT ));
        expectedMapGenerator.addMapTile(
                new MapTileLocation( 0, 0 ),
                new MapTile( DungeonTiles.STRAIGHT_HALLWAY_TYPE, MapTileOrientation.UPRIGHT ));
        expectedMapGenerator.addMapTile(
                new MapTileLocation( 0, 1 ),
                new MapTile( DungeonTiles.STRAIGHT_HALLWAY_TYPE, MapTileOrientation.UPRIGHT ));

        MapGenerator actualMapGenerator =
                new MapGenerator( new ImprovedRandom(), mapTileTypes,
                                  new RectangularMapTileLocationFilter( -1, -1, 1, 1 ));
        actualMapGenerator.addMapTile(
                new MapTileLocation( 0, -1 ),
                new MapTile( DungeonTiles.STRAIGHT_HALLWAY_TYPE, MapTileOrientation.UPRIGHT ));

        actualMapGenerator.generate();

        MapGeneratorHelper.assertAreEqual( expectedMapGenerator, actualMapGenerator );
    }

    /**
     * Verify that the generate() method works correctly when the generation process results in a
     * situation where it is impossible to add a map tile to one or more locations on the map.
     */
    @Test
    public void generate_badLocationCorrection()
    {
        MapTileType[] mapTileTypes = new MapTileType[]
        {
            DungeonTiles.STRAIGHT_HALLWAY_TYPE,
            DungeonTiles.CORNER_HALLWAY_TYPE,
            DungeonTiles.THREE_WAY_HALLWAY_TYPE
        };

        MapGenerator expectedMapGenerator =
                generate_badLocationCorrection_getExpextedMapGenerator( mapTileTypes );

        TestImprovedRandom testImprovedRandom = new TestImprovedRandom();

        // three way hallway, (4,2), clockwise
        testImprovedRandom.addTransaction( 1 );
        testImprovedRandom.addTransaction( 0 );

        // corner hallway, (4,1), flipped
        testImprovedRandom.addTransaction( 0 );
        testImprovedRandom.addTransaction( 1 );

        // corner hallway, (3,1), clockwise
        testImprovedRandom.addTransaction( 0 );
        testImprovedRandom.addTransaction( 0 );

        // removal

        // corner hallway, (4,1), clockwise
        testImprovedRandom.addTransaction( 0 );
        testImprovedRandom.addTransaction( 0 );

        // straight hallway, (5,1), clockwise
        testImprovedRandom.addTransaction( 0 );
        testImprovedRandom.addTransaction( 0 );

        // corner hallway, (6,1), flipped
        testImprovedRandom.addTransaction( 0 );
        testImprovedRandom.addTransaction( 0 );

        // straight hallway, (6,2), upright
        testImprovedRandom.addTransaction( 0 );
        testImprovedRandom.addTransaction( 0 );

        // straight hallway, (6,3), upright
        testImprovedRandom.addTransaction( 0 );
        testImprovedRandom.addTransaction( 0 );

        // corner hallway, (6,4), counter-clockwise
        testImprovedRandom.addTransaction( 0 );
        testImprovedRandom.addTransaction( 0 );

        // straight hallway, (5,4), clockwise
        testImprovedRandom.addTransaction( 0 );
        testImprovedRandom.addTransaction( 0 );

        // straight hallway, (4,4), clockwise
        testImprovedRandom.addTransaction( 0 );
        testImprovedRandom.addTransaction( 0 );

        // straight hallway, (3,4), clockwise
        testImprovedRandom.addTransaction( 0 );
        testImprovedRandom.addTransaction( 0 );

        // straight hallway, (2,4), clockwise
        testImprovedRandom.addTransaction( 0 );
        testImprovedRandom.addTransaction( 0 );

        // corner hallway, (1,4), upright
        testImprovedRandom.addTransaction( 0 );
        testImprovedRandom.addTransaction( 0 );

        // straight hallway, (1,3), upright
        testImprovedRandom.addTransaction( 0 );
        testImprovedRandom.addTransaction( 0 );

        // straight hallway, (1,2), upright
        testImprovedRandom.addTransaction( 0 );
        testImprovedRandom.addTransaction( 0 );

        // removal

        // three way hallway, (4,1), flipped
        testImprovedRandom.addTransaction( 0 );
        testImprovedRandom.addTransaction( 0 );

        // corner hallway, (3,1), clockwise
        testImprovedRandom.addTransaction( 1 );
        testImprovedRandom.addTransaction( 0 );

        // removal

        // corner hallway, (4,2), clockwise
        testImprovedRandom.addTransaction( 0 );
        testImprovedRandom.addTransaction( 0 );

        MapGenerator actualMapGenerator = generate_badLocationCorrection_getActualMapGenerator(
                mapTileTypes, testImprovedRandom );

        actualMapGenerator.generate();

        MapGeneratorHelper.assertAreEqual( expectedMapGenerator, actualMapGenerator );
    }

    /**
     * Get the expected map generator for the generate_badLocationCorrection() test.
     *
     * @param mapTileTypes
     *            Map tile types for the expected map generator.
     *
     * @return The expected map generator for the generate_badLocationCorrection() test.
     */
    private MapGenerator generate_badLocationCorrection_getExpextedMapGenerator(
            MapTileType[] mapTileTypes )
    {
        MapGenerator expectedMapGenerator =
                new MapGenerator( new ImprovedRandom(), mapTileTypes,
                                  new RectangularMapTileLocationFilter( 0, 0, 7, 5 ));
        expectedMapGenerator.addMapTile(
                new MapTileLocation( 1, 0 ),
                new MapTile( DungeonTiles.STRAIGHT_HALLWAY_TYPE, MapTileOrientation.CLOCKWISE ));
        expectedMapGenerator.addMapTile(
                new MapTileLocation( 0, 0 ),
                new MapTile( DungeonTiles.CORNER_HALLWAY_TYPE, MapTileOrientation.CLOCKWISE ));
        expectedMapGenerator.addMapTile(
                new MapTileLocation( 0, 1 ),
                new MapTile( DungeonTiles.STRAIGHT_HALLWAY_TYPE, MapTileOrientation.UPRIGHT ));
        expectedMapGenerator.addMapTile(
                new MapTileLocation( 0, 2 ),
                new MapTile( DungeonTiles.STRAIGHT_HALLWAY_TYPE, MapTileOrientation.UPRIGHT ));
        expectedMapGenerator.addMapTile(
                new MapTileLocation( 0, 3 ),
                new MapTile( DungeonTiles.STRAIGHT_HALLWAY_TYPE, MapTileOrientation.UPRIGHT ));
        expectedMapGenerator.addMapTile(
                new MapTileLocation( 0, 4 ),
                new MapTile( DungeonTiles.STRAIGHT_HALLWAY_TYPE, MapTileOrientation.UPRIGHT ));
        expectedMapGenerator.addMapTile(
                new MapTileLocation( 0, 5 ),
                new MapTile( DungeonTiles.CORNER_HALLWAY_TYPE, MapTileOrientation.UPRIGHT ));
        expectedMapGenerator.addMapTile(
                new MapTileLocation( 1, 5 ),
                new MapTile( DungeonTiles.STRAIGHT_HALLWAY_TYPE, MapTileOrientation.CLOCKWISE ));
        expectedMapGenerator.addMapTile(
                new MapTileLocation( 2, 5 ),
                new MapTile( DungeonTiles.STRAIGHT_HALLWAY_TYPE, MapTileOrientation.CLOCKWISE ));
        expectedMapGenerator.addMapTile(
                new MapTileLocation( 3, 5 ),
                new MapTile( DungeonTiles.STRAIGHT_HALLWAY_TYPE, MapTileOrientation.CLOCKWISE ));
        expectedMapGenerator.addMapTile(
                new MapTileLocation( 4, 5 ),
                new MapTile( DungeonTiles.STRAIGHT_HALLWAY_TYPE, MapTileOrientation.CLOCKWISE ));
        expectedMapGenerator.addMapTile(
                new MapTileLocation( 5, 5 ),
                new MapTile( DungeonTiles.STRAIGHT_HALLWAY_TYPE, MapTileOrientation.CLOCKWISE ));
        expectedMapGenerator.addMapTile(
                new MapTileLocation( 6, 5 ),
                new MapTile( DungeonTiles.STRAIGHT_HALLWAY_TYPE, MapTileOrientation.CLOCKWISE ));
        expectedMapGenerator.addMapTile(
                new MapTileLocation( 7, 5 ),
                new MapTile( DungeonTiles.CORNER_HALLWAY_TYPE,
                             MapTileOrientation.COUNTER_CLOCKWISE ));
        expectedMapGenerator.addMapTile(
                new MapTileLocation( 7, 4 ),
                new MapTile( DungeonTiles.STRAIGHT_HALLWAY_TYPE, MapTileOrientation.UPRIGHT ));
        expectedMapGenerator.addMapTile(
                new MapTileLocation( 7, 3 ),
                new MapTile( DungeonTiles.STRAIGHT_HALLWAY_TYPE, MapTileOrientation.UPRIGHT ));
        expectedMapGenerator.addMapTile(
                new MapTileLocation( 7, 2 ),
                new MapTile( DungeonTiles.STRAIGHT_HALLWAY_TYPE, MapTileOrientation.UPRIGHT ));
        expectedMapGenerator.addMapTile(
                new MapTileLocation( 7, 1 ),
                new MapTile( DungeonTiles.STRAIGHT_HALLWAY_TYPE, MapTileOrientation.UPRIGHT ));
        expectedMapGenerator.addMapTile(
                new MapTileLocation( 7, 0 ),
                new MapTile( DungeonTiles.CORNER_HALLWAY_TYPE, MapTileOrientation.FLIPPED ));
        expectedMapGenerator.addMapTile(
                new MapTileLocation( 6, 0 ),
                new MapTile( DungeonTiles.STRAIGHT_HALLWAY_TYPE, MapTileOrientation.CLOCKWISE ));
        expectedMapGenerator.addMapTile(
                new MapTileLocation( 5, 0 ),
                new MapTile( DungeonTiles.STRAIGHT_HALLWAY_TYPE, MapTileOrientation.CLOCKWISE ));
        expectedMapGenerator.addMapTile(
                new MapTileLocation( 4, 0 ),
                new MapTile( DungeonTiles.STRAIGHT_HALLWAY_TYPE, MapTileOrientation.CLOCKWISE ));
        expectedMapGenerator.addMapTile(
                new MapTileLocation( 3, 0 ),
                new MapTile( DungeonTiles.STRAIGHT_HALLWAY_TYPE, MapTileOrientation.CLOCKWISE ));
        expectedMapGenerator.addMapTile(
                new MapTileLocation( 2, 0 ),
                new MapTile( DungeonTiles.THREE_WAY_HALLWAY_TYPE, MapTileOrientation.FLIPPED ));
        expectedMapGenerator.addMapTile(
                new MapTileLocation( 2, 1 ),
                new MapTile( DungeonTiles.STRAIGHT_HALLWAY_TYPE, MapTileOrientation.UPRIGHT ));
        expectedMapGenerator.addMapTile(
                new MapTileLocation( 2, 2 ),
                new MapTile( DungeonTiles.STRAIGHT_HALLWAY_TYPE, MapTileOrientation.UPRIGHT ));
        expectedMapGenerator.addMapTile(
                new MapTileLocation( 2, 3 ),
                new MapTile( DungeonTiles.CORNER_HALLWAY_TYPE, MapTileOrientation.UPRIGHT ));
        expectedMapGenerator.addMapTile(
                new MapTileLocation( 3, 3 ),
                new MapTile( DungeonTiles.STRAIGHT_HALLWAY_TYPE, MapTileOrientation.CLOCKWISE ));
        expectedMapGenerator.addMapTile(
                new MapTileLocation( 4, 3 ),
                new MapTile( DungeonTiles.THREE_WAY_HALLWAY_TYPE, MapTileOrientation.UPRIGHT ));
        expectedMapGenerator.addMapTile(
                new MapTileLocation( 5, 3 ),
                new MapTile( DungeonTiles.CORNER_HALLWAY_TYPE,
                             MapTileOrientation.COUNTER_CLOCKWISE ));
        expectedMapGenerator.addMapTile(
                new MapTileLocation( 5, 2 ),
                new MapTile( DungeonTiles.CORNER_HALLWAY_TYPE, MapTileOrientation.FLIPPED ));
        expectedMapGenerator.addMapTile(
                new MapTileLocation( 4, 2 ),
                new MapTile( DungeonTiles.CORNER_HALLWAY_TYPE, MapTileOrientation.CLOCKWISE ));

        return expectedMapGenerator;
    }

    /**
     * Get the actual map generator for the generate_badLocationCorrection() test.
     *
     * @param mapTileTypes
     *            Map tile types for the actual map generator.
     * @param testImprovedRandom
     *            Random number generator for the actual map generator.
     *
     * @return The actual map generator for the generate_badLocationCorrection() test.
     */
    private MapGenerator generate_badLocationCorrection_getActualMapGenerator(
            MapTileType[] mapTileTypes, TestImprovedRandom testImprovedRandom )
    {
        MapGenerator actualMapGenerator =
                new MapGenerator( testImprovedRandom, mapTileTypes,
                                  new RectangularMapTileLocationFilter( 0, 0, 7, 5 ));
        actualMapGenerator.addMapTile(
                new MapTileLocation( 1, 0 ),
                new MapTile( DungeonTiles.STRAIGHT_HALLWAY_TYPE, MapTileOrientation.CLOCKWISE ));
        actualMapGenerator.addMapTile(
                new MapTileLocation( 0, 0 ),
                new MapTile( DungeonTiles.CORNER_HALLWAY_TYPE, MapTileOrientation.CLOCKWISE ));
        actualMapGenerator.addMapTile(
                new MapTileLocation( 0, 1 ),
                new MapTile( DungeonTiles.STRAIGHT_HALLWAY_TYPE, MapTileOrientation.UPRIGHT ));
        actualMapGenerator.addMapTile(
                new MapTileLocation( 0, 2 ),
                new MapTile( DungeonTiles.STRAIGHT_HALLWAY_TYPE, MapTileOrientation.UPRIGHT ));
        actualMapGenerator.addMapTile(
                new MapTileLocation( 0, 3 ),
                new MapTile( DungeonTiles.STRAIGHT_HALLWAY_TYPE, MapTileOrientation.UPRIGHT ));
        actualMapGenerator.addMapTile(
                new MapTileLocation( 0, 4 ),
                new MapTile( DungeonTiles.STRAIGHT_HALLWAY_TYPE, MapTileOrientation.UPRIGHT ));
        actualMapGenerator.addMapTile(
                new MapTileLocation( 0, 5 ),
                new MapTile( DungeonTiles.CORNER_HALLWAY_TYPE, MapTileOrientation.UPRIGHT ));
        actualMapGenerator.addMapTile(
                new MapTileLocation( 1, 5 ),
                new MapTile( DungeonTiles.STRAIGHT_HALLWAY_TYPE, MapTileOrientation.CLOCKWISE ));
        actualMapGenerator.addMapTile(
                new MapTileLocation( 2, 5 ),
                new MapTile( DungeonTiles.STRAIGHT_HALLWAY_TYPE, MapTileOrientation.CLOCKWISE ));
        actualMapGenerator.addMapTile(
                new MapTileLocation( 3, 5 ),
                new MapTile( DungeonTiles.STRAIGHT_HALLWAY_TYPE, MapTileOrientation.CLOCKWISE ));
        actualMapGenerator.addMapTile(
                new MapTileLocation( 4, 5 ),
                new MapTile( DungeonTiles.STRAIGHT_HALLWAY_TYPE, MapTileOrientation.CLOCKWISE ));
        actualMapGenerator.addMapTile(
                new MapTileLocation( 5, 5 ),
                new MapTile( DungeonTiles.STRAIGHT_HALLWAY_TYPE, MapTileOrientation.CLOCKWISE ));
        actualMapGenerator.addMapTile(
                new MapTileLocation( 6, 5 ),
                new MapTile( DungeonTiles.STRAIGHT_HALLWAY_TYPE, MapTileOrientation.CLOCKWISE ));
        actualMapGenerator.addMapTile(
                new MapTileLocation( 7, 5 ),
                new MapTile( DungeonTiles.CORNER_HALLWAY_TYPE,
                             MapTileOrientation.COUNTER_CLOCKWISE ));
        actualMapGenerator.addMapTile(
                new MapTileLocation( 7, 4 ),
                new MapTile( DungeonTiles.STRAIGHT_HALLWAY_TYPE, MapTileOrientation.UPRIGHT ));
        actualMapGenerator.addMapTile(
                new MapTileLocation( 7, 3 ),
                new MapTile( DungeonTiles.STRAIGHT_HALLWAY_TYPE, MapTileOrientation.UPRIGHT ));
        actualMapGenerator.addMapTile(
                new MapTileLocation( 7, 2 ),
                new MapTile( DungeonTiles.STRAIGHT_HALLWAY_TYPE, MapTileOrientation.UPRIGHT ));
        actualMapGenerator.addMapTile(
                new MapTileLocation( 7, 1 ),
                new MapTile( DungeonTiles.STRAIGHT_HALLWAY_TYPE, MapTileOrientation.UPRIGHT ));
        actualMapGenerator.addMapTile(
                new MapTileLocation( 7, 0 ),
                new MapTile( DungeonTiles.CORNER_HALLWAY_TYPE, MapTileOrientation.FLIPPED ));
        actualMapGenerator.addMapTile(
                new MapTileLocation( 6, 0 ),
                new MapTile( DungeonTiles.STRAIGHT_HALLWAY_TYPE, MapTileOrientation.CLOCKWISE ));
        actualMapGenerator.addMapTile(
                new MapTileLocation( 5, 0 ),
                new MapTile( DungeonTiles.STRAIGHT_HALLWAY_TYPE, MapTileOrientation.CLOCKWISE ));
        actualMapGenerator.addMapTile(
                new MapTileLocation( 4, 0 ),
                new MapTile( DungeonTiles.STRAIGHT_HALLWAY_TYPE, MapTileOrientation.CLOCKWISE ));
        actualMapGenerator.addMapTile(
                new MapTileLocation( 3, 0 ),
                new MapTile( DungeonTiles.STRAIGHT_HALLWAY_TYPE, MapTileOrientation.CLOCKWISE ));
        actualMapGenerator.addMapTile(
                new MapTileLocation( 2, 0 ),
                new MapTile( DungeonTiles.THREE_WAY_HALLWAY_TYPE, MapTileOrientation.FLIPPED ));
        actualMapGenerator.addMapTile(
                new MapTileLocation( 2, 1 ),
                new MapTile( DungeonTiles.STRAIGHT_HALLWAY_TYPE, MapTileOrientation.UPRIGHT ));
        actualMapGenerator.addMapTile(
                new MapTileLocation( 2, 2 ),
                new MapTile( DungeonTiles.STRAIGHT_HALLWAY_TYPE, MapTileOrientation.UPRIGHT ));
        actualMapGenerator.addMapTile(
                new MapTileLocation( 2, 3 ),
                new MapTile( DungeonTiles.CORNER_HALLWAY_TYPE, MapTileOrientation.UPRIGHT ));
        actualMapGenerator.addMapTile(
                new MapTileLocation( 3, 3 ),
                new MapTile( DungeonTiles.STRAIGHT_HALLWAY_TYPE, MapTileOrientation.CLOCKWISE ));
        actualMapGenerator.addMapTile(
                new MapTileLocation( 4, 3 ),
                new MapTile( DungeonTiles.THREE_WAY_HALLWAY_TYPE, MapTileOrientation.UPRIGHT ));
        actualMapGenerator.addMapTile(
                new MapTileLocation( 5, 3 ),
                new MapTile( DungeonTiles.CORNER_HALLWAY_TYPE,
                             MapTileOrientation.COUNTER_CLOCKWISE ));
        actualMapGenerator.addMapTile(
                new MapTileLocation( 5, 2 ),
                new MapTile( DungeonTiles.CORNER_HALLWAY_TYPE, MapTileOrientation.FLIPPED ));

        return actualMapGenerator;
    }

    /**
     * Verify that the generate() method works correctly when the generation process results in a
     * situation where it is impossible to add a map tile to one or more locations on the map and
     * one or more map tiles that have been added adjacent to the problematic map tile need to be
     * removed.
     */
    @Test
    public void generate_advancedBadLocationCorrection()
    {
        MapTileType[] mapTileTypes = new MapTileType[]
        {
            DungeonTiles.STRAIGHT_HALLWAY_TYPE,
            DungeonTiles.CORNER_HALLWAY_TYPE,
            DungeonTiles.THREE_WAY_HALLWAY_TYPE
        };

        MapGenerator expectedMapGenerator =
                new MapGenerator( new ImprovedRandom(), mapTileTypes,
                                  new RectangularMapTileLocationFilter( 0, 0, 8, 5 ));

        generate_advancedBadLocationCorrection_updateExpextedMapGenerator( expectedMapGenerator );

        TestImprovedRandom testImprovedRandom = new TestImprovedRandom();

        // three way hallway, (5,2), clockwise
        testImprovedRandom.addTransaction( 1 );
        testImprovedRandom.addTransaction( 0 );

        // three way hallway, (5,1), flipped
        testImprovedRandom.addTransaction( 1 );
        testImprovedRandom.addTransaction( 0 );

        // straight hallway, (6,1), clockwise
        testImprovedRandom.addTransaction( 0 );
        testImprovedRandom.addTransaction( 0 );

        // corner hallway, (7,1), flipped
        testImprovedRandom.addTransaction( 0 );
        testImprovedRandom.addTransaction( 1 );

        // straight hallway, (7,2), upright
        testImprovedRandom.addTransaction( 0 );
        testImprovedRandom.addTransaction( 0 );

        // straight hallway, (7,3), upright
        testImprovedRandom.addTransaction( 0 );
        testImprovedRandom.addTransaction( 0 );

        // corner hallway, (7,4), counter-clockwise
        testImprovedRandom.addTransaction( 0 );
        testImprovedRandom.addTransaction( 0 );

        // straight hallway, (6,4), clockwise
        testImprovedRandom.addTransaction( 0 );
        testImprovedRandom.addTransaction( 0 );

        // straight hallway, (5,4), clockwise
        testImprovedRandom.addTransaction( 0 );
        testImprovedRandom.addTransaction( 0 );

        // straight hallway, (4,4), clockwise
        testImprovedRandom.addTransaction( 0 );
        testImprovedRandom.addTransaction( 0 );

        // straight hallway, (3,4), clockwise
        testImprovedRandom.addTransaction( 0 );
        testImprovedRandom.addTransaction( 0 );

        // three way hallway, (2,4), upright
        testImprovedRandom.addTransaction( 2 );
        testImprovedRandom.addTransaction( 0 );

        // corner hallway, (1,4), upright
        testImprovedRandom.addTransaction( 1 );
        testImprovedRandom.addTransaction( 1 );

        // corner hallway, (2,3), flipped
        testImprovedRandom.addTransaction( 1 );
        testImprovedRandom.addTransaction( 0 );

        // corner hallway, (1,3), clockwise
        testImprovedRandom.addTransaction( 0 );
        testImprovedRandom.addTransaction( 0 );

        // corner hallway, (4,1), clockwise
        testImprovedRandom.addTransaction( 0 );
        testImprovedRandom.addTransaction( 0 );

        // removal

        // corner hallway, (5,1), clockwise
        testImprovedRandom.addTransaction( 0 );
        testImprovedRandom.addTransaction( 0 );

        // straight hallway, (6,1), clockwise
        testImprovedRandom.addTransaction( 0 );
        testImprovedRandom.addTransaction( 0 );

        // corner hallway, (7,1), flipped
        testImprovedRandom.addTransaction( 0 );
        testImprovedRandom.addTransaction( 0 );

        // straight hallway, (7,2), upright
        testImprovedRandom.addTransaction( 0 );
        testImprovedRandom.addTransaction( 0 );

        // straight hallway, (7,3), upright
        testImprovedRandom.addTransaction( 0 );
        testImprovedRandom.addTransaction( 0 );

        // corner hallway, (7,4), counter-clockwise
        testImprovedRandom.addTransaction( 0 );
        testImprovedRandom.addTransaction( 0 );

        // straight hallway, (6,4), clockwise
        testImprovedRandom.addTransaction( 0 );
        testImprovedRandom.addTransaction( 0 );

        // straight hallway, (5,4), clockwise
        testImprovedRandom.addTransaction( 0 );
        testImprovedRandom.addTransaction( 0 );

        // straight hallway, (4,4), clockwise
        testImprovedRandom.addTransaction( 0 );
        testImprovedRandom.addTransaction( 0 );

        // straight hallway, (3,4), clockwise
        testImprovedRandom.addTransaction( 0 );
        testImprovedRandom.addTransaction( 0 );

        // three way hallway, (2,4), upright
        testImprovedRandom.addTransaction( 2 );
        testImprovedRandom.addTransaction( 0 );

        // corner hallway, (1,4), upright
        testImprovedRandom.addTransaction( 1 );
        testImprovedRandom.addTransaction( 1 );

        // corner hallway, (1,3), clockwise
        testImprovedRandom.addTransaction( 1 );
        testImprovedRandom.addTransaction( 1 );

        // corner hallway, (2,3), flipped
        testImprovedRandom.addTransaction( 0 );
        testImprovedRandom.addTransaction( 0 );

        MapGenerator actualMapGenerator =
                generate_advancedBadLocationCorrection_getActualMapGenerator( mapTileTypes,
                                                                              testImprovedRandom );

        actualMapGenerator.generate();

        MapGeneratorHelper.assertAreEqual( expectedMapGenerator, actualMapGenerator );
    }

    /**
     * Update the expected map generator for the generate_advancedBadLocationCorrection() test by
     * adding the expected map tiles.
     *
     * @param expectedMapGenerator
     *            Expected map generator to update.
     */
    private void generate_advancedBadLocationCorrection_updateExpextedMapGenerator(
            MapGenerator expectedMapGenerator )
    {
        expectedMapGenerator.addMapTile(
                new MapTileLocation( 2, 0 ),
                new MapTile( DungeonTiles.STRAIGHT_HALLWAY_TYPE, MapTileOrientation.CLOCKWISE ));
        expectedMapGenerator.addMapTile(
                new MapTileLocation( 1, 0 ),
                new MapTile( DungeonTiles.STRAIGHT_HALLWAY_TYPE, MapTileOrientation.CLOCKWISE ));
        expectedMapGenerator.addMapTile(
                new MapTileLocation( 0, 0 ),
                new MapTile( DungeonTiles.CORNER_HALLWAY_TYPE, MapTileOrientation.CLOCKWISE ));
        expectedMapGenerator.addMapTile(
                new MapTileLocation( 0, 1 ),
                new MapTile( DungeonTiles.STRAIGHT_HALLWAY_TYPE, MapTileOrientation.UPRIGHT ));
        expectedMapGenerator.addMapTile(
                new MapTileLocation( 0, 2 ),
                new MapTile( DungeonTiles.STRAIGHT_HALLWAY_TYPE, MapTileOrientation.UPRIGHT ));
        expectedMapGenerator.addMapTile(
                new MapTileLocation( 0, 3 ),
                new MapTile( DungeonTiles.STRAIGHT_HALLWAY_TYPE, MapTileOrientation.UPRIGHT ));
        expectedMapGenerator.addMapTile(
                new MapTileLocation( 0, 4 ),
                new MapTile( DungeonTiles.STRAIGHT_HALLWAY_TYPE, MapTileOrientation.UPRIGHT ));
        expectedMapGenerator.addMapTile(
                new MapTileLocation( 0, 5 ),
                new MapTile( DungeonTiles.CORNER_HALLWAY_TYPE, MapTileOrientation.UPRIGHT ));
        expectedMapGenerator.addMapTile(
                new MapTileLocation( 1, 5 ),
                new MapTile( DungeonTiles.STRAIGHT_HALLWAY_TYPE, MapTileOrientation.CLOCKWISE ));
        expectedMapGenerator.addMapTile(
                new MapTileLocation( 2, 5 ),
                new MapTile( DungeonTiles.STRAIGHT_HALLWAY_TYPE, MapTileOrientation.CLOCKWISE ));
        expectedMapGenerator.addMapTile(
                new MapTileLocation( 3, 5 ),
                new MapTile( DungeonTiles.STRAIGHT_HALLWAY_TYPE, MapTileOrientation.CLOCKWISE ));
        expectedMapGenerator.addMapTile(
                new MapTileLocation( 4, 5 ),
                new MapTile( DungeonTiles.STRAIGHT_HALLWAY_TYPE, MapTileOrientation.CLOCKWISE ));
        expectedMapGenerator.addMapTile(
                new MapTileLocation( 5, 5 ),
                new MapTile( DungeonTiles.STRAIGHT_HALLWAY_TYPE, MapTileOrientation.CLOCKWISE ));
        expectedMapGenerator.addMapTile(
                new MapTileLocation( 6, 5 ),
                new MapTile( DungeonTiles.STRAIGHT_HALLWAY_TYPE, MapTileOrientation.CLOCKWISE ));
        expectedMapGenerator.addMapTile(
                new MapTileLocation( 7, 5 ),
                new MapTile( DungeonTiles.STRAIGHT_HALLWAY_TYPE, MapTileOrientation.CLOCKWISE ));
        expectedMapGenerator.addMapTile(
                new MapTileLocation( 8, 5 ),
                new MapTile( DungeonTiles.CORNER_HALLWAY_TYPE,
                             MapTileOrientation.COUNTER_CLOCKWISE ));
        expectedMapGenerator.addMapTile(
                new MapTileLocation( 8, 4 ),
                new MapTile( DungeonTiles.STRAIGHT_HALLWAY_TYPE, MapTileOrientation.UPRIGHT ));
        expectedMapGenerator.addMapTile(
                new MapTileLocation( 8, 3 ),
                new MapTile( DungeonTiles.STRAIGHT_HALLWAY_TYPE, MapTileOrientation.UPRIGHT ));
        expectedMapGenerator.addMapTile(
                new MapTileLocation( 8, 2 ),
                new MapTile( DungeonTiles.STRAIGHT_HALLWAY_TYPE, MapTileOrientation.UPRIGHT ));
        expectedMapGenerator.addMapTile(
                new MapTileLocation( 8, 1 ),
                new MapTile( DungeonTiles.STRAIGHT_HALLWAY_TYPE, MapTileOrientation.UPRIGHT ));
        expectedMapGenerator.addMapTile(
                new MapTileLocation( 8, 0 ),
                new MapTile( DungeonTiles.CORNER_HALLWAY_TYPE, MapTileOrientation.FLIPPED ));
        expectedMapGenerator.addMapTile(
                new MapTileLocation( 7, 0 ),
                new MapTile( DungeonTiles.STRAIGHT_HALLWAY_TYPE, MapTileOrientation.CLOCKWISE ));
        expectedMapGenerator.addMapTile(
                new MapTileLocation( 6, 0 ),
                new MapTile( DungeonTiles.STRAIGHT_HALLWAY_TYPE, MapTileOrientation.CLOCKWISE ));
        expectedMapGenerator.addMapTile(
                new MapTileLocation( 5, 0 ),
                new MapTile( DungeonTiles.STRAIGHT_HALLWAY_TYPE, MapTileOrientation.CLOCKWISE ));
        expectedMapGenerator.addMapTile(
                new MapTileLocation( 4, 0 ),
                new MapTile( DungeonTiles.STRAIGHT_HALLWAY_TYPE, MapTileOrientation.CLOCKWISE ));
        expectedMapGenerator.addMapTile(
                new MapTileLocation( 3, 0 ),
                new MapTile( DungeonTiles.THREE_WAY_HALLWAY_TYPE, MapTileOrientation.FLIPPED ));
        expectedMapGenerator.addMapTile(
                new MapTileLocation( 3, 1 ),
                new MapTile( DungeonTiles.STRAIGHT_HALLWAY_TYPE, MapTileOrientation.UPRIGHT ));
        expectedMapGenerator.addMapTile(
                new MapTileLocation( 3, 2 ),
                new MapTile( DungeonTiles.STRAIGHT_HALLWAY_TYPE, MapTileOrientation.UPRIGHT ));
        expectedMapGenerator.addMapTile(
                new MapTileLocation( 3, 3 ),
                new MapTile( DungeonTiles.CORNER_HALLWAY_TYPE, MapTileOrientation.UPRIGHT ));
        expectedMapGenerator.addMapTile(
                new MapTileLocation( 4, 3 ),
                new MapTile( DungeonTiles.STRAIGHT_HALLWAY_TYPE, MapTileOrientation.CLOCKWISE ));
        expectedMapGenerator.addMapTile(
                new MapTileLocation( 5, 3 ),
                new MapTile( DungeonTiles.THREE_WAY_HALLWAY_TYPE, MapTileOrientation.UPRIGHT ));
        expectedMapGenerator.addMapTile(
                new MapTileLocation( 6, 3 ),
                new MapTile( DungeonTiles.CORNER_HALLWAY_TYPE,
                             MapTileOrientation.COUNTER_CLOCKWISE ));
        expectedMapGenerator.addMapTile(
                new MapTileLocation( 6, 2 ),
                new MapTile( DungeonTiles.CORNER_HALLWAY_TYPE, MapTileOrientation.FLIPPED ));
        expectedMapGenerator.addMapTile(
                new MapTileLocation( 5, 2 ),
                new MapTile( DungeonTiles.THREE_WAY_HALLWAY_TYPE, MapTileOrientation.CLOCKWISE ));
        expectedMapGenerator.addMapTile(
                new MapTileLocation( 5, 1 ),
                new MapTile( DungeonTiles.CORNER_HALLWAY_TYPE, MapTileOrientation.CLOCKWISE ));
        expectedMapGenerator.addMapTile(
                new MapTileLocation( 6, 1 ),
                new MapTile( DungeonTiles.STRAIGHT_HALLWAY_TYPE, MapTileOrientation.CLOCKWISE ));
        expectedMapGenerator.addMapTile(
                new MapTileLocation( 7, 1 ),
                new MapTile( DungeonTiles.CORNER_HALLWAY_TYPE, MapTileOrientation.FLIPPED ));
        expectedMapGenerator.addMapTile(
                new MapTileLocation( 7, 2 ),
                new MapTile( DungeonTiles.STRAIGHT_HALLWAY_TYPE, MapTileOrientation.UPRIGHT ));
        expectedMapGenerator.addMapTile(
                new MapTileLocation( 7, 3 ),
                new MapTile( DungeonTiles.STRAIGHT_HALLWAY_TYPE, MapTileOrientation.UPRIGHT ));
        expectedMapGenerator.addMapTile(
                new MapTileLocation( 7, 4 ),
                new MapTile( DungeonTiles.CORNER_HALLWAY_TYPE,
                             MapTileOrientation.COUNTER_CLOCKWISE ));
        expectedMapGenerator.addMapTile(
                new MapTileLocation( 6, 4 ),
                new MapTile( DungeonTiles.STRAIGHT_HALLWAY_TYPE, MapTileOrientation.CLOCKWISE ));
        expectedMapGenerator.addMapTile(
                new MapTileLocation( 5, 4 ),
                new MapTile( DungeonTiles.STRAIGHT_HALLWAY_TYPE, MapTileOrientation.CLOCKWISE ));
        expectedMapGenerator.addMapTile(
                new MapTileLocation( 4, 4 ),
                new MapTile( DungeonTiles.STRAIGHT_HALLWAY_TYPE, MapTileOrientation.CLOCKWISE ));
        expectedMapGenerator.addMapTile(
                new MapTileLocation( 3, 4 ),
                new MapTile( DungeonTiles.STRAIGHT_HALLWAY_TYPE, MapTileOrientation.CLOCKWISE ));
        expectedMapGenerator.addMapTile(
                new MapTileLocation( 2, 4 ),
                new MapTile( DungeonTiles.THREE_WAY_HALLWAY_TYPE, MapTileOrientation.UPRIGHT ));
        expectedMapGenerator.addMapTile(
                new MapTileLocation( 1, 4 ),
                new MapTile( DungeonTiles.CORNER_HALLWAY_TYPE, MapTileOrientation.UPRIGHT ));
        expectedMapGenerator.addMapTile(
                new MapTileLocation( 1, 3 ),
                new MapTile( DungeonTiles.CORNER_HALLWAY_TYPE, MapTileOrientation.CLOCKWISE ));
        expectedMapGenerator.addMapTile(
                new MapTileLocation( 2, 3 ),
                new MapTile( DungeonTiles.CORNER_HALLWAY_TYPE, MapTileOrientation.FLIPPED ));
    }

    /**
     * Get the actual map generator for the generate_advancedBadLocationCorrection() test.
     *
     * @param mapTileTypes
     *            Map tile types for the actual map generator.
     * @param testImprovedRandom
     *            Random number generator for the actual map generator.
     *
     * @return The actual map generator for the generate_badLocationCorrection() test.
     */
    private MapGenerator generate_advancedBadLocationCorrection_getActualMapGenerator(
            MapTileType[] mapTileTypes, TestImprovedRandom testImprovedRandom )
    {
        MapGenerator actualMapGenerator =
                new MapGenerator( testImprovedRandom, mapTileTypes,
                                  new RectangularMapTileLocationFilter( 0, 0, 8, 5 ));
        actualMapGenerator.addMapTile(
                new MapTileLocation( 2, 0 ),
                new MapTile( DungeonTiles.STRAIGHT_HALLWAY_TYPE, MapTileOrientation.CLOCKWISE ));
        actualMapGenerator.addMapTile(
                new MapTileLocation( 1, 0 ),
                new MapTile( DungeonTiles.STRAIGHT_HALLWAY_TYPE, MapTileOrientation.CLOCKWISE ));
        actualMapGenerator.addMapTile(
                new MapTileLocation( 0, 0 ),
                new MapTile( DungeonTiles.CORNER_HALLWAY_TYPE, MapTileOrientation.CLOCKWISE ));
        actualMapGenerator.addMapTile(
                new MapTileLocation( 0, 1 ),
                new MapTile( DungeonTiles.STRAIGHT_HALLWAY_TYPE, MapTileOrientation.UPRIGHT ));
        actualMapGenerator.addMapTile(
                new MapTileLocation( 0, 2 ),
                new MapTile( DungeonTiles.STRAIGHT_HALLWAY_TYPE, MapTileOrientation.UPRIGHT ));
        actualMapGenerator.addMapTile(
                new MapTileLocation( 0, 3 ),
                new MapTile( DungeonTiles.STRAIGHT_HALLWAY_TYPE, MapTileOrientation.UPRIGHT ));
        actualMapGenerator.addMapTile(
                new MapTileLocation( 0, 4 ),
                new MapTile( DungeonTiles.STRAIGHT_HALLWAY_TYPE, MapTileOrientation.UPRIGHT ));
        actualMapGenerator.addMapTile(
                new MapTileLocation( 0, 5 ),
                new MapTile( DungeonTiles.CORNER_HALLWAY_TYPE, MapTileOrientation.UPRIGHT ));
        actualMapGenerator.addMapTile(
                new MapTileLocation( 1, 5 ),
                new MapTile( DungeonTiles.STRAIGHT_HALLWAY_TYPE, MapTileOrientation.CLOCKWISE ));
        actualMapGenerator.addMapTile(
                new MapTileLocation( 2, 5 ),
                new MapTile( DungeonTiles.STRAIGHT_HALLWAY_TYPE, MapTileOrientation.CLOCKWISE ));
        actualMapGenerator.addMapTile(
                new MapTileLocation( 3, 5 ),
                new MapTile( DungeonTiles.STRAIGHT_HALLWAY_TYPE, MapTileOrientation.CLOCKWISE ));
        actualMapGenerator.addMapTile(
                new MapTileLocation( 4, 5 ),
                new MapTile( DungeonTiles.STRAIGHT_HALLWAY_TYPE, MapTileOrientation.CLOCKWISE ));
        actualMapGenerator.addMapTile(
                new MapTileLocation( 5, 5 ),
                new MapTile( DungeonTiles.STRAIGHT_HALLWAY_TYPE, MapTileOrientation.CLOCKWISE ));
        actualMapGenerator.addMapTile(
                new MapTileLocation( 6, 5 ),
                new MapTile( DungeonTiles.STRAIGHT_HALLWAY_TYPE, MapTileOrientation.CLOCKWISE ));
        actualMapGenerator.addMapTile(
                new MapTileLocation( 7, 5 ),
                new MapTile( DungeonTiles.STRAIGHT_HALLWAY_TYPE, MapTileOrientation.CLOCKWISE ));
        actualMapGenerator.addMapTile(
                new MapTileLocation( 8, 5 ),
                new MapTile( DungeonTiles.CORNER_HALLWAY_TYPE,
                             MapTileOrientation.COUNTER_CLOCKWISE ));
        actualMapGenerator.addMapTile(
                new MapTileLocation( 8, 4 ),
                new MapTile( DungeonTiles.STRAIGHT_HALLWAY_TYPE, MapTileOrientation.UPRIGHT ));
        actualMapGenerator.addMapTile(
                new MapTileLocation( 8, 3 ),
                new MapTile( DungeonTiles.STRAIGHT_HALLWAY_TYPE, MapTileOrientation.UPRIGHT ));
        actualMapGenerator.addMapTile(
                new MapTileLocation( 8, 2 ),
                new MapTile( DungeonTiles.STRAIGHT_HALLWAY_TYPE, MapTileOrientation.UPRIGHT ));
        actualMapGenerator.addMapTile(
                new MapTileLocation( 8, 1 ),
                new MapTile( DungeonTiles.STRAIGHT_HALLWAY_TYPE, MapTileOrientation.UPRIGHT ));
        actualMapGenerator.addMapTile(
                new MapTileLocation( 8, 0 ),
                new MapTile( DungeonTiles.CORNER_HALLWAY_TYPE, MapTileOrientation.FLIPPED ));
        actualMapGenerator.addMapTile(
                new MapTileLocation( 7, 0 ),
                new MapTile( DungeonTiles.STRAIGHT_HALLWAY_TYPE, MapTileOrientation.CLOCKWISE ));
        actualMapGenerator.addMapTile(
                new MapTileLocation( 6, 0 ),
                new MapTile( DungeonTiles.STRAIGHT_HALLWAY_TYPE, MapTileOrientation.CLOCKWISE ));
        actualMapGenerator.addMapTile(
                new MapTileLocation( 5, 0 ),
                new MapTile( DungeonTiles.STRAIGHT_HALLWAY_TYPE, MapTileOrientation.CLOCKWISE ));
        actualMapGenerator.addMapTile(
                new MapTileLocation( 4, 0 ),
                new MapTile( DungeonTiles.STRAIGHT_HALLWAY_TYPE, MapTileOrientation.CLOCKWISE ));
        actualMapGenerator.addMapTile(
                new MapTileLocation( 3, 0 ),
                new MapTile( DungeonTiles.THREE_WAY_HALLWAY_TYPE, MapTileOrientation.FLIPPED ));
        actualMapGenerator.addMapTile(
                new MapTileLocation( 3, 1 ),
                new MapTile( DungeonTiles.STRAIGHT_HALLWAY_TYPE, MapTileOrientation.UPRIGHT ));
        actualMapGenerator.addMapTile(
                new MapTileLocation( 3, 2 ),
                new MapTile( DungeonTiles.STRAIGHT_HALLWAY_TYPE, MapTileOrientation.UPRIGHT ));
        actualMapGenerator.addMapTile(
                new MapTileLocation( 3, 3 ),
                new MapTile( DungeonTiles.CORNER_HALLWAY_TYPE, MapTileOrientation.UPRIGHT ));
        actualMapGenerator.addMapTile(
                new MapTileLocation( 4, 3 ),
                new MapTile( DungeonTiles.STRAIGHT_HALLWAY_TYPE, MapTileOrientation.CLOCKWISE ));
        actualMapGenerator.addMapTile(
                new MapTileLocation( 5, 3 ),
                new MapTile( DungeonTiles.THREE_WAY_HALLWAY_TYPE, MapTileOrientation.UPRIGHT ));
        actualMapGenerator.addMapTile(
                new MapTileLocation( 6, 3 ),
                new MapTile( DungeonTiles.CORNER_HALLWAY_TYPE,
                             MapTileOrientation.COUNTER_CLOCKWISE ));
        actualMapGenerator.addMapTile(
                new MapTileLocation( 6, 2 ),
                new MapTile( DungeonTiles.CORNER_HALLWAY_TYPE, MapTileOrientation.FLIPPED ));

        return actualMapGenerator;
    }
}
