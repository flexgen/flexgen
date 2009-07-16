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

package org.flexgen.map.test;

import org.junit.Assert;
import org.junit.Test;

import org.flexgen.map.MapGenerator;
import org.flexgen.map.MapTile;
import org.flexgen.map.MapTileLocation;
import org.flexgen.map.MapTileOrientation;
import org.flexgen.map.MapTileType;
import org.flexgen.test.helper.GeneralHelper;
import org.flexgen.test.helper.MapGeneratorHelper;
import org.flexgen.test.helper.MapTileOrientationHelper;
import org.flexgen.test.helper.MapTileTypeHelper;

/**
 * Test class for the MapGenerator class.
 */
public class MapGeneratorTest
{
    /**
     * Verify that the constructor throws the correct exception when the mapTileTypes parameter is
     * null.
     */
    @Test
    public void constructor_mapTileTypes_nullArray()
    {
        try
        {
            new MapGenerator( null, 0, 0, 0, 0 );
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
            new MapGenerator( mapTileTypes, 0, 0, 0, 0 );
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
            new MapGenerator( mapTileTypes, 0, 0, 0, 0 );
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
            new MapGenerator( mapTileTypes, 0, 0, 0, 0 );
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
            new MapGenerator( mapTileTypes, 0, 0, 0, 0 );
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
     * Verify that the constructor throws the correct exception when the maxX parameter is less than
     * the minX parameter.
     */
    @Test
    public void constructor_maxX_tooSmall()
    {
        try
        {
            new MapGenerator( MapTileTypeHelper.buildArray(), 0, 0, -1, 0 );
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
            new MapGenerator( MapTileTypeHelper.buildArray(), 0, 0, 0, -1 );
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
     * Verify that the getTileSize() method returns the correct value.
     */
    @Test
    public void getTileSize()
    {
        int size = GeneralHelper.getRandom().nextInt( 5 ) + 1;
        MapTileType[] mapTileTypes = MapTileTypeHelper.buildArray( size );
        MapGenerator mapGenerator = new MapGenerator( mapTileTypes, 0, 0, 0, 0 );
        Assert.assertEquals( "Unexpected return value.", size, mapGenerator.getTileSize() );
    }

    /**
     * Verify that the getMinX() method returns the correct value.
     */
    @Test
    public void getMinX()
    {
        int value = GeneralHelper.getRandom().nextInt( 1000 );
        MapGenerator mapGenerator =
                new MapGenerator( MapTileTypeHelper.buildArray(), value, 0, 5000, 5000 );
        Assert.assertEquals( "Unexpected return value.", value, mapGenerator.getMinX() );
    }

    /**
     * Verify that the getMinY() method returns the correct value.
     */
    @Test
    public void getMinY()
    {
        int value = GeneralHelper.getRandom().nextInt( 1000 );
        MapGenerator mapGenerator =
                new MapGenerator( MapTileTypeHelper.buildArray(), 0, value, 5000, 5000 );
        Assert.assertEquals( "Unexpected return value.", value, mapGenerator.getMinY() );
    }

    /**
     * Verify that the getMaxX() method returns the correct value.
     */
    @Test
    public void getMaxX()
    {
        int value = GeneralHelper.getRandom().nextInt( 1000 );
        MapGenerator mapGenerator =
                new MapGenerator( MapTileTypeHelper.buildArray(), 0, 0, value, 5000 );
        Assert.assertEquals( "Unexpected return value.", value, mapGenerator.getMaxX() );
    }

    /**
     * Verify that the getMaxY() method returns the correct value.
     */
    @Test
    public void getMaxY()
    {
        int value = GeneralHelper.getRandom().nextInt( 1000 );
        MapGenerator mapGenerator =
                new MapGenerator( MapTileTypeHelper.buildArray(), 0, 0, 5000, value );
        Assert.assertEquals( "Unexpected return value.", value, mapGenerator.getMaxY() );
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
        MapTileLocation mapTileLocation = new MapTileLocation( 0, 0 );
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
        MapTileLocation mapTileLocation = new MapTileLocation( 0, 0 );

        MapTileType[] mapTileTypes = new MapTileType[]
        {
            mapTileType
        };

        MapGenerator mapGenerator = new MapGenerator( mapTileTypes, 0, 0, 0, 0 );
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

        MapGenerator mapGenerator = new MapGenerator( mapTileTypes, 0, 0, 0, 0 );

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
     * Verify that the addMapTile() method works correctly when the mapTile parameter is null.
     */
    @Test
    public void addMapTile_mapTile_null()
    {
        MapTileType mapTileType = MapTileTypeHelper.build();
        MapTileOrientation mapTileOrientation = MapTileOrientationHelper.getRandomOrientation();
        MapTile mapTile = new MapTile( mapTileType, mapTileOrientation );
        MapTileLocation mapTileLocation = new MapTileLocation( 0, 0 );

        MapTileType[] mapTileTypes = new MapTileType[]
        {
            mapTileType
        };

        MapGenerator mapGenerator = new MapGenerator( mapTileTypes, 0, 0, 0, 0 );
        mapGenerator.addMapTile( mapTileLocation, mapTile );
        mapGenerator.addMapTile( mapTileLocation, null );

        Assert.assertEquals( "Unexpected return value.", null,
                             mapGenerator.getMapTile( mapTileLocation ));
    }
}
