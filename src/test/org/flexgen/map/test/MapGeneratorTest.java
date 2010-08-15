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

package org.flexgen.map.test;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import org.flexgen.map.MapGenerator;
import org.flexgen.map.MapTile;
import org.flexgen.map.MapTileLocation;
import org.flexgen.map.MapTileOrientation;
import org.flexgen.map.MapTileType;
import org.flexgen.map.RectangularMapTileLocationFilter;
import org.flexgen.map.test.support.TestMapTileAddedListener;
import org.flexgen.map.test.support.TestMapTileRemovedListener;
import org.flexgen.test.helper.GeneralHelper;
import org.flexgen.test.helper.MapGeneratorHelper;
import org.flexgen.test.helper.MapTileLocationHelper;
import org.flexgen.test.helper.MapTileOrientationHelper;
import org.flexgen.test.helper.MapTileTypeHelper;
import org.flexgen.test.helper.tiles.DungeonTiles;
import org.flexgen.test.helper.tiles.RiverTiles;
import org.flexgen.util.ImprovedRandom;

/**
 * Test class for the MapGenerator class.
 */
public class MapGeneratorTest
{
    /**
     * Verify that the constructor throws the correct exception when the improvedRandom parameter is
     * null.
     */
    @Test
    public void constructor_improvedRandom_null()
    {
        try
        {
            new MapGenerator( null, MapTileTypeHelper.buildArray(),
                              new RectangularMapTileLocationFilter( 0, 0, 0, 0 ));
            Assert.fail( "Expected exception." );
        }
        catch ( IllegalArgumentException e )
        {
            Assert.assertEquals( "Unexpected message.",
                                 "Parameter 'improvedRandom' cannot be null.", e.getMessage() );
        }
    }

    /**
     * Verify that the constructor throws the correct exception when the mapTileTypes parameter is
     * null.
     */
    @Test
    public void constructor_mapTileTypes_nullArray()
    {
        try
        {
            new MapGenerator( new ImprovedRandom(), null,
                              new RectangularMapTileLocationFilter( 0, 0, 0, 0 ));
            Assert.fail( "Expected exception." );
        }
        catch ( IllegalArgumentException e )
        {
            Assert.assertEquals( "Unexpected message.", "Parameter 'mapTileTypes' cannot be null.",
                                 e.getMessage() );
        }
    }

    /**
     * Verify that the constructor throws the correct exception when the mapTileTypes parameter is
     * an empty array.
     */
    @Test
    public void constructor_mapTileTypes_emptyArray()
    {
        MapTileType[] mapTileTypes = new MapTileType[] {};

        try
        {
            new MapGenerator( new ImprovedRandom(), mapTileTypes,
                              new RectangularMapTileLocationFilter( 0, 0, 0, 0 ));
            Assert.fail( "Expected exception." );
        }
        catch ( IllegalArgumentException e )
        {
            Assert.assertEquals( "Unexpected message.",
                                 "Parameter 'mapTileTypes' must contain at least one element.",
                                 e.getMessage() );
        }
    }

    /**
     * Verify that the constructor throws the correct exception when the mapTileTypes parameter
     * contains a null element.
     */
    @Test
    public void constructor_mapTileTypes_nullElement()
    {
        MapTileType[] mapTileTypes = new MapTileType[]
        {
            null
        };

        try
        {
            new MapGenerator( new ImprovedRandom(), mapTileTypes,
                              new RectangularMapTileLocationFilter( 0, 0, 0, 0 ));
            Assert.fail( "Expected exception." );
        }
        catch ( IllegalArgumentException e )
        {
            Assert.assertEquals( "Unexpected message.",
                                 "Parameter 'mapTileTypes' must not contain any null elements.",
                                 e.getMessage() );
        }
    }

    /**
     * Verify that the constructor throws the correct exception when the mapTileTypes parameter
     * contains a duplicate element.
     */
    @Test
    public void constructor_mapTileTypes_duplicateElement()
    {
        MapTileType mapTileType = MapTileTypeHelper.build();

        MapTileType[] mapTileTypes = new MapTileType[]
        {
            mapTileType,
            mapTileType
        };

        try
        {
            new MapGenerator( new ImprovedRandom(), mapTileTypes,
                              new RectangularMapTileLocationFilter( 0, 0, 0, 0 ));
            Assert.fail( "Expected exception." );
        }
        catch ( IllegalArgumentException e )
        {
            Assert.assertEquals(
                    "Unexpected message.",
                    "Parameter 'mapTileTypes' must not contain any duplicate elements.",
                    e.getMessage() );
        }
    }

    /**
     * Verify that the constructor throws the correct exception when the mapTileTypes parameter
     * contains map tile types with different sizes.
     */
    @Test
    public void constructor_mapTileTypes_differentSize()
    {
        MapTileType[] mapTileTypes = new MapTileType[]
        {
            MapTileTypeHelper.build( 1 ),
            MapTileTypeHelper.build( 2 )
        };

        try
        {
            new MapGenerator( new ImprovedRandom(), mapTileTypes,
                              new RectangularMapTileLocationFilter( 0, 0, 0, 0 ));
            Assert.fail( "Expected exception." );
        }
        catch ( IllegalArgumentException e )
        {
            Assert.assertEquals(
                    "Unexpected message.",
                    "All map tile types in parameter 'mapTileTypes' must be the same size.",
                    e.getMessage() );
        }
    }

    /**
     * Verify that the constructor throws the correct exception when the improvedRandom parameter is
     * null.
     */
    @Test
    public void constructor_mapTileLocationFilter_null()
    {
        try
        {
            new MapGenerator( new ImprovedRandom(), MapTileTypeHelper.buildArray(), null );
            Assert.fail( "Expected exception." );
        }
        catch ( IllegalArgumentException e )
        {
            Assert.assertEquals( "Unexpected message.",
                                 "Parameter 'mapTileLocationFilter' cannot be null.",
                                 e.getMessage() );
        }
    }

    /**
     * Verify that the constructor throws the correct exception when the maxX parameter is less than
     * the minX parameter.
     */
    @Test
    public void constructor_maxX_tooSmall()
    {
        try
        {
            new MapGenerator( new ImprovedRandom(), MapTileTypeHelper.buildArray(), 0, 0, -1, 0 );
            Assert.fail( "Expected exception." );
        }
        catch ( IllegalArgumentException e )
        {
            Assert.assertEquals(
                    "Unexpected message.",
                    "Parameter 'maxX' must be greater than or equal to parameter 'minX'.",
                    e.getMessage() );
        }
    }

    /**
     * Verify that the constructor throws the correct exception when the maxY parameter is less than
     * the minY parameter.
     */
    @Test
    public void constructor_maxY_tooSmall()
    {
        try
        {
            new MapGenerator( new ImprovedRandom(), MapTileTypeHelper.buildArray(), 0, 0, 0, -1 );
            Assert.fail( "Expected exception." );
        }
        catch ( IllegalArgumentException e )
        {
            Assert.assertEquals(
                    "Unexpected message.",
                    "Parameter 'maxY' must be greater than or equal to parameter 'minY'.",
                    e.getMessage() );
        }
    }

    /**
     * Verify that the constructor that doesn't take the mapTileLocationFilter parameter works the
     * same as the constructor that does.
     */
    @Test
    public void constructor_withoutMapTileLocationFilter()
    {
        MapTileType[] mapTileTypes = MapTileTypeHelper.buildArray();

        MapGenerator expectedMapGenerator =
                new MapGenerator( new ImprovedRandom(), mapTileTypes,
                                  new RectangularMapTileLocationFilter( 0, 0, 0, 0 ));
        MapGenerator actualMapGenerator =
                new MapGenerator( new ImprovedRandom(), mapTileTypes, 0, 0, 0, 0 );

        MapGeneratorHelper.assertAreEqual( expectedMapGenerator, actualMapGenerator );
    }

    /**
     * Verify that the getTileSize() method returns the correct value.
     */
    @Test
    public void getTileSize()
    {
        int mapUnitArraySize = GeneralHelper.getRandom().nextInt( 5 ) + 1;
        int mapTileArraySize = GeneralHelper.getRandom().nextInt( 5 ) + 2;
        MapTileType[] mapTileTypes =
                MapTileTypeHelper.buildArray( mapUnitArraySize, mapTileArraySize );
        MapGenerator mapGenerator =
                new MapGenerator( new ImprovedRandom(), mapTileTypes,
                                  new RectangularMapTileLocationFilter( 0, 0, 0, 0 ));
        Assert.assertEquals( "Unexpected return value.", mapUnitArraySize,
                             mapGenerator.getTileSize() );
    }

    /**
     * Verify that the getMinX() method returns the correct value when the map contains a tile
     * within the boundaries of the map tile location filter.
     */
    @Test
    public void getMinX_tileWithinBoundary()
    {
        int minX  = GeneralHelper.getRandom().nextInt( 1000 );
        int tileX = GeneralHelper.getRandom().nextInt( 1000 ) + minX  + 1;
        int maxX  = GeneralHelper.getRandom().nextInt( 1000 ) + tileX + 1;

        MapGenerator mapGenerator = new MapGenerator(
                new ImprovedRandom(), RiverTiles.MAP_TILE_TYPES,
                new RectangularMapTileLocationFilter( minX, 0, maxX, 0 ));

        mapGenerator.addMapTile(
                new MapTileLocation( tileX, 0 ),
                new MapTile( RiverTiles.ALL_GRASS, MapTileOrientation.UPRIGHT ));

        Assert.assertEquals( "Unexpected return value.", minX, mapGenerator.getMinX() );
    }

    /**
     * Verify that the getMinX() method returns the correct value when the map contains a tile
     * outside the boundaries of the map tile location filter.
     */
    @Test
    public void getMinX_tileOutsideBoundary()
    {
        int tileX = GeneralHelper.getRandom().nextInt( 1000 );
        int minX  = GeneralHelper.getRandom().nextInt( 1000 ) + tileX + 1;
        int maxX  = GeneralHelper.getRandom().nextInt( 1000 ) + minX  + 1;

        MapGenerator mapGenerator = new MapGenerator(
                new ImprovedRandom(), RiverTiles.MAP_TILE_TYPES,
                new RectangularMapTileLocationFilter( minX, 0, maxX, 0 ));

        mapGenerator.addMapTile(
                new MapTileLocation( tileX, 0 ),
                new MapTile( RiverTiles.ALL_GRASS, MapTileOrientation.UPRIGHT ));

        Assert.assertEquals( "Unexpected return value.", tileX, mapGenerator.getMinX() );
    }

    /**
     * Verify that the getMinY() method returns the correct value when the map contains a tile
     * within the boundaries of the map tile location filter.
     */
    @Test
    public void getMinY_tileWithinBoundary()
    {
        int minY  = GeneralHelper.getRandom().nextInt( 1000 );
        int tileY = GeneralHelper.getRandom().nextInt( 1000 ) + minY  + 1;
        int maxY  = GeneralHelper.getRandom().nextInt( 1000 ) + tileY + 1;

        MapGenerator mapGenerator = new MapGenerator(
                new ImprovedRandom(), RiverTiles.MAP_TILE_TYPES,
                new RectangularMapTileLocationFilter( 0, minY, 0, maxY ));

        mapGenerator.addMapTile(
                new MapTileLocation( 0, tileY ),
                new MapTile( RiverTiles.ALL_GRASS, MapTileOrientation.UPRIGHT ));

        Assert.assertEquals( "Unexpected return value.", minY, mapGenerator.getMinY() );
    }

    /**
     * Verify that the getMinY() method returns the correct value when the map contains a tile
     * outside the boundaries of the map tile location filter.
     */
    @Test
    public void getMinY_tileOutsideBoundary()
    {
        int tileY = GeneralHelper.getRandom().nextInt( 1000 );
        int minY  = GeneralHelper.getRandom().nextInt( 1000 ) + tileY + 1;
        int maxY  = GeneralHelper.getRandom().nextInt( 1000 ) + minY  + 1;

        MapGenerator mapGenerator = new MapGenerator(
                new ImprovedRandom(), RiverTiles.MAP_TILE_TYPES,
                new RectangularMapTileLocationFilter( 0, minY, 0, maxY ));

        mapGenerator.addMapTile(
                new MapTileLocation( 0, tileY ),
                new MapTile( RiverTiles.ALL_GRASS, MapTileOrientation.UPRIGHT ));

        Assert.assertEquals( "Unexpected return value.", tileY, mapGenerator.getMinY() );
    }

    /**
     * Verify that the getMaxX() method returns the correct value when the map contains a tile
     * within the boundaries of the map tile location filter.
     */
    @Test
    public void getMaxX_tileWithinBoundary()
    {
        int minX  = GeneralHelper.getRandom().nextInt( 1000 );
        int tileX = GeneralHelper.getRandom().nextInt( 1000 ) + minX  + 1;
        int maxX  = GeneralHelper.getRandom().nextInt( 1000 ) + tileX + 1;

        MapGenerator mapGenerator = new MapGenerator(
                new ImprovedRandom(), RiverTiles.MAP_TILE_TYPES,
                new RectangularMapTileLocationFilter( minX, 0, maxX, 0 ));

        mapGenerator.addMapTile(
                new MapTileLocation( tileX, 0 ),
                new MapTile( RiverTiles.ALL_GRASS, MapTileOrientation.UPRIGHT ));

        Assert.assertEquals( "Unexpected return value.", maxX, mapGenerator.getMaxX() );
    }

    /**
     * Verify that the getMaxX() method returns the correct value when the map contains a tile
     * outside the boundaries of the map tile location filter.
     */
    @Test
    public void getMaxX_tileOutsideBoundary()
    {
        int minX  = GeneralHelper.getRandom().nextInt( 1000 );
        int maxX  = GeneralHelper.getRandom().nextInt( 1000 ) + minX + 1;
        int tileX = GeneralHelper.getRandom().nextInt( 1000 ) + maxX + 1;

        MapGenerator mapGenerator = new MapGenerator(
                new ImprovedRandom(), RiverTiles.MAP_TILE_TYPES,
                new RectangularMapTileLocationFilter( minX, 0, maxX, 0 ));

        mapGenerator.addMapTile(
                new MapTileLocation( tileX, 0 ),
                new MapTile( RiverTiles.ALL_GRASS, MapTileOrientation.UPRIGHT ));

        Assert.assertEquals( "Unexpected return value.", tileX, mapGenerator.getMaxX() );
    }

    /**
     * Verify that the getMaxY() method returns the correct value when the map contains a tile
     * within the boundaries of the map tile location filter.
     */
    @Test
    public void getMaxY_tileWithinBoundary()
    {
        int minY  = GeneralHelper.getRandom().nextInt( 1000 );
        int tileY = GeneralHelper.getRandom().nextInt( 1000 ) + minY  + 1;
        int maxY  = GeneralHelper.getRandom().nextInt( 1000 ) + tileY + 1;

        MapGenerator mapGenerator = new MapGenerator(
                new ImprovedRandom(), RiverTiles.MAP_TILE_TYPES,
                new RectangularMapTileLocationFilter( 0, minY, 0, maxY ));

        mapGenerator.addMapTile(
                new MapTileLocation( 0, tileY ),
                new MapTile( RiverTiles.ALL_GRASS, MapTileOrientation.UPRIGHT ));

        Assert.assertEquals( "Unexpected return value.", maxY, mapGenerator.getMaxY() );
    }

    /**
     * Verify that the getMaxY() method returns the correct value when the map contains a tile
     * outside the boundaries of the map tile location filter.
     */
    @Test
    public void getMaxY_tileOutsideBoundary()
    {
        int minY  = GeneralHelper.getRandom().nextInt( 1000 );
        int maxY  = GeneralHelper.getRandom().nextInt( 1000 ) + minY + 1;
        int tileY = GeneralHelper.getRandom().nextInt( 1000 ) + maxY + 1;

        MapGenerator mapGenerator = new MapGenerator(
                new ImprovedRandom(), RiverTiles.MAP_TILE_TYPES,
                new RectangularMapTileLocationFilter( 0, minY, 0, maxY ));

        mapGenerator.addMapTile(
                new MapTileLocation( 0, tileY ),
                new MapTile( RiverTiles.ALL_GRASS, MapTileOrientation.UPRIGHT ));

        Assert.assertEquals( "Unexpected return value.", tileY, mapGenerator.getMaxY() );
    }

    /**
     * Verify that the getMapTile() method throws the correct exception when the mapTileLocation
     * parameter is null.
     */
    @Test
    public void getMapTile_mapTileLocation_null()
    {
        MapGenerator mapGenerator = MapGeneratorHelper.build();

        try
        {
            mapGenerator.getMapTile( null );
            Assert.fail( "Expected exception." );
        }
        catch ( IllegalArgumentException e )
        {
            Assert.assertEquals( "Unexpected message.",
                                 "Parameter 'mapTileLocation' cannot be null.", e.getMessage() );
        }
    }

    /**
     * Verify that the getMapTile() method returns null when specifying a location where there is no
     * map tile.
     */
    @Test
    public void getMapTile_noTile()
    {
        MapGenerator mapGenerator = MapGeneratorHelper.build();
        MapTileLocation mapTileLocation = MapTileLocationHelper.build();
        Assert.assertEquals( "Unexpected return value.", null,
                             mapGenerator.getMapTile( mapTileLocation ));
    }

    /**
     * Verify that the getMapTile() method returns the correct value when specifying a location
     * where there is a map tile.
     */
    @Test
    public void getMapTile()
    {
        MapTileType mapTileType = MapTileTypeHelper.build();
        MapTileOrientation mapTileOrientation = MapTileOrientationHelper.getRandomOrientation();
        MapTile mapTile = new MapTile( mapTileType, mapTileOrientation );

        MapTileLocation mapTileLocation = MapTileLocationHelper.build();

        MapTileType[] mapTileTypes = new MapTileType[]
        {
            mapTileType
        };

        MapGenerator mapGenerator = new MapGenerator(
                new ImprovedRandom(), mapTileTypes,
                new RectangularMapTileLocationFilter(
                        mapTileLocation.getX(), mapTileLocation.getY(),
                        mapTileLocation.getX(), mapTileLocation.getY() ));

        mapGenerator.addMapTile( mapTileLocation, mapTile );

        Assert.assertEquals( "Unexpected return value.", mapTile,
                             mapGenerator.getMapTile( mapTileLocation ));
    }

    /**
     * Verify that the addMapTile() method throws the correct exception when the mapTileLocation
     * parameter is null.
     */
    @Test
    public void addMapTile_mapTileLocation_null()
    {
        MapTileType mapTileType = MapTileTypeHelper.build();
        MapTileOrientation mapTileOrientation = MapTileOrientationHelper.getRandomOrientation();
        MapTile mapTile = new MapTile( mapTileType, mapTileOrientation );

        MapTileType[] mapTileTypes = new MapTileType[]
        {
            mapTileType
        };

        MapGenerator mapGenerator =
                new MapGenerator( new ImprovedRandom(), mapTileTypes,
                                  new RectangularMapTileLocationFilter( 0, 0, 0, 0 ));

        try
        {
            mapGenerator.addMapTile( null, mapTile );
            Assert.fail( "Expected exception." );
        }
        catch ( IllegalArgumentException e )
        {
            Assert.assertEquals( "Unexpected message.",
                                 "Parameter 'mapTileLocation' cannot be null.", e.getMessage() );
        }
    }

    /**
     * Verify that the addMapTile() method throws the correct exception when the mapTile parameter
     * is null.
     */
    @Test
    public void addMapTile_mapTile_null()
    {
        MapTileType mapTileType = MapTileTypeHelper.build();

        MapTileType[] mapTileTypes = new MapTileType[]
        {
            mapTileType
        };

        MapTileLocation mapTileLocation = MapTileLocationHelper.build();

        MapGenerator mapGenerator = new MapGenerator(
                new ImprovedRandom(), mapTileTypes,
                new RectangularMapTileLocationFilter(
                        mapTileLocation.getX(), mapTileLocation.getY(),
                        mapTileLocation.getX(), mapTileLocation.getY() ));

        try
        {
            mapGenerator.addMapTile( mapTileLocation, null );
            Assert.fail( "Expected exception." );
        }
        catch ( IllegalArgumentException e )
        {
            Assert.assertEquals( "Unexpected message.",
                                 "Parameter 'mapTile' cannot be null.", e.getMessage() );
        }
    }

    /**
     * Verify that the addMapTile() method adds the specified map tile at the specified map tile
     * location.
     */
    @Test
    public void addMapTile_tileAdded()
    {
        MapTileType mapTileType = MapTileTypeHelper.build();
        MapTileOrientation mapTileOrientation = MapTileOrientationHelper.getRandomOrientation();
        MapTile mapTile = new MapTile( mapTileType, mapTileOrientation );

        MapTileLocation mapTileLocation = MapTileLocationHelper.build();

        MapTileType[] mapTileTypes = new MapTileType[]
        {
            mapTileType
        };

        MapGenerator mapGenerator = new MapGenerator(
                new ImprovedRandom(), mapTileTypes,
                new RectangularMapTileLocationFilter(
                        mapTileLocation.getX(), mapTileLocation.getY(),
                        mapTileLocation.getX(), mapTileLocation.getY() ));

        mapGenerator.addMapTile( mapTileLocation, mapTile );

        Assert.assertEquals( "Unexpected map tile.", mapTile,
                             mapGenerator.getMapTile( mapTileLocation ));
    }

    /**
     * Verify that the addMapTile() method works correctly when a map tile added listener has been
     * added to the map generator.
     */
    @Test
    public void addMapTile_mapTileAddedListener()
    {
        MapTileType mapTileType = MapTileTypeHelper.build();
        MapTileOrientation mapTileOrientation = MapTileOrientationHelper.getRandomOrientation();
        MapTile mapTile = new MapTile( mapTileType, mapTileOrientation );

        MapTileLocation mapTileLocation = MapTileLocationHelper.build();

        MapTileType[] mapTileTypes = new MapTileType[]
        {
            mapTileType
        };

        MapGenerator mapGenerator = new MapGenerator(
                new ImprovedRandom(), mapTileTypes,
                new RectangularMapTileLocationFilter(
                        mapTileLocation.getX(), mapTileLocation.getY(),
                        mapTileLocation.getX(), mapTileLocation.getY() ));

        TestMapTileAddedListener mapTileAddedListener = new TestMapTileAddedListener();
        mapGenerator.addMapTileAddedListener( mapTileAddedListener );
        mapGenerator.addMapTile( mapTileLocation, mapTile );

        List< MapTileLocation > mapTileLocations = mapTileAddedListener.getMapTileLocations();

        Assert.assertEquals( "Unexpected number of map tile locations.", 1,
                             mapTileLocations.size() );

        Assert.assertEquals( "Unexpected map tile location.", mapTileLocation,
                             mapTileLocations.get( 0 ));

        List< MapGenerator > mapGenerators = mapTileAddedListener.getMapGenerators();

        Assert.assertEquals( "Unexpected number of map generators.", 1,
                             mapGenerators.size() );

        Assert.assertEquals( "Unexpected map generator.", mapGenerator,
                             mapGenerators.get( 0 ));
    }

    /**
     * Verify that the removeMapTile() method throws the correct exception when the mapTileLocation
     * parameter is null.
     */
    @Test
    public void removeMapTile_mapTileLocation_null()
    {
        MapTileType mapTileType = MapTileTypeHelper.build();

        MapTileType[] mapTileTypes = new MapTileType[]
        {
            mapTileType
        };

        MapGenerator mapGenerator =
                new MapGenerator( new ImprovedRandom(), mapTileTypes,
                                  new RectangularMapTileLocationFilter( 0, 0, 0, 0 ));

        try
        {
            mapGenerator.removeMapTile( null );
            Assert.fail( "Expected exception." );
        }
        catch ( IllegalArgumentException e )
        {
            Assert.assertEquals( "Unexpected message.",
                                 "Parameter 'mapTileLocation' cannot be null.", e.getMessage() );
        }
    }

    /**
     * Verify that the removeMapTile() method throws the correct exception when there is no map tile
     * at the specified map tile location.
     */
    @Test
    public void removeMapTile_noMapTile()
    {
        MapTileType mapTileType = MapTileTypeHelper.build();

        MapTileType[] mapTileTypes = new MapTileType[]
        {
            mapTileType
        };

        MapGenerator mapGenerator =
                new MapGenerator( new ImprovedRandom(), mapTileTypes,
                                  new RectangularMapTileLocationFilter( 0, 0, 0, 0 ));

        try
        {
            mapGenerator.removeMapTile( new MapTileLocation( 0, 0 ));
            Assert.fail( "Expected exception." );
        }
        catch ( IllegalArgumentException e )
        {
            Assert.assertEquals( "Unexpected message.",
                                 "Parameter 'mapTileLocation' must locate an existing map tile.",
                                 e.getMessage() );
        }
    }

    /**
     * Verify that the removeMapTile() method removes the map tile at the specified map tile
     * location.
     */
    @Test
    public void removeMapTile_tileRemoved()
    {
        MapTileType mapTileType = MapTileTypeHelper.build();
        MapTileOrientation mapTileOrientation = MapTileOrientationHelper.getRandomOrientation();
        MapTile mapTile = new MapTile( mapTileType, mapTileOrientation );

        MapTileLocation mapTileLocation = MapTileLocationHelper.build();

        MapTileType[] mapTileTypes = new MapTileType[]
        {
            mapTileType
        };

        MapGenerator mapGenerator = new MapGenerator(
                new ImprovedRandom(), mapTileTypes,
                new RectangularMapTileLocationFilter(
                        mapTileLocation.getX(), mapTileLocation.getY(),
                        mapTileLocation.getX(), mapTileLocation.getY() ));

        mapGenerator.addMapTile( mapTileLocation, mapTile );
        mapGenerator.removeMapTile( mapTileLocation );

        Assert.assertEquals( "Unexpected map tile.", null,
                             mapGenerator.getMapTile( mapTileLocation ));
    }

    /**
     * Verify that the removeMapTile() method correctly removes the open location on the top side of
     * the removed map tile.
     */
    @Test
    public void removeMapTile_removeOpenLocation_topNeighbor()
    {
        MapTileType[] mapTileTypes = new MapTileType[]
        {
            DungeonTiles.STRAIGHT_HALLWAY_TYPE,
            DungeonTiles.CORNER_HALLWAY_TYPE
        };

        MapGenerator expectedMapGenerator =
                new MapGenerator( new ImprovedRandom(), mapTileTypes,
                                  new RectangularMapTileLocationFilter( 0, 0, 1, 2 ));

        expectedMapGenerator.addMapTile(
                new MapTileLocation( 0, 1 ),
                new MapTile( DungeonTiles.CORNER_HALLWAY_TYPE, MapTileOrientation.CLOCKWISE ));
        expectedMapGenerator.addMapTile(
                new MapTileLocation( 1, 1 ),
                new MapTile( DungeonTiles.CORNER_HALLWAY_TYPE, MapTileOrientation.FLIPPED ));
        expectedMapGenerator.addMapTile(
                new MapTileLocation( 0, 2 ),
                new MapTile( DungeonTiles.STRAIGHT_HALLWAY_TYPE, MapTileOrientation.UPRIGHT ));
        expectedMapGenerator.addMapTile(
                new MapTileLocation( 1, 2 ),
                new MapTile( DungeonTiles.STRAIGHT_HALLWAY_TYPE, MapTileOrientation.UPRIGHT ));

        MapGenerator actualMapGenerator =
                new MapGenerator( new ImprovedRandom(), mapTileTypes,
                                  new RectangularMapTileLocationFilter( 0, 0, 1, 2 ));

        actualMapGenerator.addMapTile(
                new MapTileLocation( 0, 2 ),
                new MapTile( DungeonTiles.STRAIGHT_HALLWAY_TYPE, MapTileOrientation.UPRIGHT ));
        actualMapGenerator.addMapTile(
                new MapTileLocation( 1, 2 ),
                new MapTile( DungeonTiles.STRAIGHT_HALLWAY_TYPE, MapTileOrientation.UPRIGHT ));
        actualMapGenerator.addMapTile(
                new MapTileLocation( 0, 1 ),
                new MapTile( DungeonTiles.STRAIGHT_HALLWAY_TYPE, MapTileOrientation.UPRIGHT ));

        actualMapGenerator.removeMapTile( new MapTileLocation( 0, 1 ));

        actualMapGenerator.addMapTile(
                new MapTileLocation( 0, 1 ),
                new MapTile( DungeonTiles.CORNER_HALLWAY_TYPE, MapTileOrientation.CLOCKWISE ));

        actualMapGenerator.generate();

        MapGeneratorHelper.assertAreEqual( expectedMapGenerator, actualMapGenerator );
    }

    /**
     * Verify that the removeMapTile() method correctly removes the open location on the bottom side
     * of the removed map tile.
     */
    @Test
    public void removeMapTile_removeOpenLocation_bottomNeighbor()
    {
        MapTileType[] mapTileTypes = new MapTileType[]
        {
            DungeonTiles.STRAIGHT_HALLWAY_TYPE,
            DungeonTiles.CORNER_HALLWAY_TYPE
        };

        MapGenerator expectedMapGenerator =
                new MapGenerator( new ImprovedRandom(), mapTileTypes,
                                  new RectangularMapTileLocationFilter( 0, 0, 1, 2 ));

        expectedMapGenerator.addMapTile(
                new MapTileLocation( 0, 0 ),
                new MapTile( DungeonTiles.STRAIGHT_HALLWAY_TYPE, MapTileOrientation.UPRIGHT ));
        expectedMapGenerator.addMapTile(
                new MapTileLocation( 1, 0 ),
                new MapTile( DungeonTiles.STRAIGHT_HALLWAY_TYPE, MapTileOrientation.UPRIGHT ));
        expectedMapGenerator.addMapTile(
                new MapTileLocation( 0, 1 ),
                new MapTile( DungeonTiles.CORNER_HALLWAY_TYPE, MapTileOrientation.UPRIGHT ));
        expectedMapGenerator.addMapTile(
                new MapTileLocation( 1, 1 ),
                new MapTile( DungeonTiles.CORNER_HALLWAY_TYPE,
                             MapTileOrientation.COUNTER_CLOCKWISE ));

        MapGenerator actualMapGenerator =
                new MapGenerator( new ImprovedRandom(), mapTileTypes,
                                  new RectangularMapTileLocationFilter( 0, 0, 1, 2 ));

        actualMapGenerator.addMapTile(
                new MapTileLocation( 0, 0 ),
                new MapTile( DungeonTiles.STRAIGHT_HALLWAY_TYPE, MapTileOrientation.UPRIGHT ));
        actualMapGenerator.addMapTile(
                new MapTileLocation( 1, 0 ),
                new MapTile( DungeonTiles.STRAIGHT_HALLWAY_TYPE, MapTileOrientation.UPRIGHT ));
        actualMapGenerator.addMapTile(
                new MapTileLocation( 0, 1 ),
                new MapTile( DungeonTiles.STRAIGHT_HALLWAY_TYPE, MapTileOrientation.UPRIGHT ));

        actualMapGenerator.removeMapTile( new MapTileLocation( 0, 1 ));

        actualMapGenerator.addMapTile(
                new MapTileLocation( 0, 1 ),
                new MapTile( DungeonTiles.CORNER_HALLWAY_TYPE, MapTileOrientation.UPRIGHT ));

        actualMapGenerator.generate();

        MapGeneratorHelper.assertAreEqual( expectedMapGenerator, actualMapGenerator );
    }

    /**
     * Verify that the removeMapTile() method works correctly when a map tile removed listener has
     * been added to the map generator.
     */
    @Test
    public void removeMapTile_mapTileRemovedListener()
    {
        MapTileType mapTileType = MapTileTypeHelper.build();
        MapTileOrientation mapTileOrientation = MapTileOrientationHelper.getRandomOrientation();
        MapTile mapTile = new MapTile( mapTileType, mapTileOrientation );

        MapTileLocation mapTileLocation = MapTileLocationHelper.build();

        MapTileType[] mapTileTypes = new MapTileType[]
        {
            mapTileType
        };

        MapGenerator mapGenerator = new MapGenerator(
                new ImprovedRandom(), mapTileTypes,
                new RectangularMapTileLocationFilter(
                        mapTileLocation.getX(), mapTileLocation.getY(),
                        mapTileLocation.getX(), mapTileLocation.getY() ));

        TestMapTileRemovedListener mapTileRemovedListener = new TestMapTileRemovedListener();
        mapGenerator.addMapTileRemovedListener( mapTileRemovedListener );

        mapGenerator.addMapTile( mapTileLocation, mapTile );
        mapGenerator.removeMapTile( mapTileLocation );

        List< MapTileLocation > mapTileLocations = mapTileRemovedListener.getMapTileLocations();

        Assert.assertEquals( "Unexpected number of map tile locations.", 1,
                             mapTileLocations.size() );

        Assert.assertEquals( "Unexpected map tile location.", mapTileLocation,
                             mapTileLocations.get( 0 ));

        List< MapGenerator > mapGenerators = mapTileRemovedListener.getMapGenerators();

        Assert.assertEquals( "Unexpected number of map generators.", 1,
                             mapGenerators.size() );

        Assert.assertEquals( "Unexpected map generator.", mapGenerator,
                             mapGenerators.get( 0 ));
    }

    /**
     * Verify that the generate() method throws the correct exception when there are no legal map
     * tile types that can be placed on the map.
     */
    @Test
    public void generate_noLegalMapTileTypes()
    {
        MapGenerator mapGenerator =
                new MapGenerator( new ImprovedRandom(), RiverTiles.MAP_TILE_TYPES,
                                  new RectangularMapTileLocationFilter( -1, -1, 1, 1 ));

        mapGenerator.addMapTile(
                new MapTileLocation( -1, -1 ),
                new MapTile( RiverTiles.ALL_GRASS, MapTileOrientation.UPRIGHT ));
        mapGenerator.addMapTile(
                new MapTileLocation( 0, -1 ),
                new MapTile( RiverTiles.STRAIGHT_RIVER, MapTileOrientation.UPRIGHT ));
        mapGenerator.addMapTile(
                new MapTileLocation( 1, -1 ),
                new MapTile( RiverTiles.ALL_GRASS, MapTileOrientation.UPRIGHT ));
        mapGenerator.addMapTile(
                new MapTileLocation( -1, 0 ),
                new MapTile( RiverTiles.ALL_GRASS, MapTileOrientation.UPRIGHT ));
        mapGenerator.addMapTile(
                new MapTileLocation( 1, 0 ),
                new MapTile( RiverTiles.ALL_GRASS, MapTileOrientation.UPRIGHT ));
        mapGenerator.addMapTile(
                new MapTileLocation( -1, 1 ),
                new MapTile( RiverTiles.ALL_GRASS, MapTileOrientation.UPRIGHT ));
        mapGenerator.addMapTile(
                new MapTileLocation( 0, 1 ),
                new MapTile( RiverTiles.ALL_GRASS, MapTileOrientation.UPRIGHT ));
        mapGenerator.addMapTile(
                new MapTileLocation( 1, 1 ),
                new MapTile( RiverTiles.ALL_GRASS, MapTileOrientation.UPRIGHT ));

        try
        {
            mapGenerator.generate();
            Assert.fail( "Expected exception." );
        }
        catch ( IllegalStateException e )
        {
            Assert.assertEquals( "Unexpected message.",
                                 "No map tile types can be placed on the map.", e.getMessage() );
        }
    }

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
}
