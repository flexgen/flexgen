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

import org.junit.Assert;
import org.junit.Test;

import org.flexgen.map.MapTileEdge;
import org.flexgen.map.MapTileEdgePosition;
import org.flexgen.map.MapTileOrientation;
import org.flexgen.map.MapTileType;
import org.flexgen.map.MapUnit;
import org.flexgen.test.helper.GeneralHelper;
import org.flexgen.test.helper.MapTileEdgeHelper;
import org.flexgen.test.helper.MapTileOrientationHelper;
import org.flexgen.test.helper.MapTileTypeHelper;
import org.flexgen.test.helper.MapUnitHelper;

/**
 * Test class for the MapTileType class.
 */
public class MapTileTypeTest
{
    /**
     * Verify that the constructor throws the correct exception when the name parameter is null.
     */
    @Test
    public void constructor_name_null()
    {
        try
        {
            new MapTileType( null, 0, MapUnitHelper.buildArray(), MapTileEdgeHelper.buildArray(),
                             MapTileOrientationHelper.ALL_ORIENTATIONS );
            Assert.fail( "Expected exception." );
        }
        catch ( IllegalArgumentException e )
        {
            Assert.assertEquals( "Unexpected message.", "Parameter 'name' cannot be null.",
                                 e.getMessage() );
        }
    }

    /**
     * Verify that the constructor throws the correct exception when the weight parameter is less
     * than zero.
     */
    @Test
    public void constructor_weight_tooSmall()
    {
        try
        {
            new MapTileType( GeneralHelper.getUniqueString(), -1,
                             MapUnitHelper.buildArray(), MapTileEdgeHelper.buildArray(),
                             MapTileOrientationHelper.ALL_ORIENTATIONS );
            Assert.fail( "Expected exception." );
        }
        catch ( IllegalArgumentException e )
        {
            Assert.assertEquals( "Unexpected message.",
                                 "Parameter 'weight' cannot be less than 0.", e.getMessage() );
        }
    }

    /**
     * Verify that the constructor throws the correct exception when the mapUnits parameter is null.
     */
    @Test
    public void constructor_mapUnits_nullArray()
    {
        try
        {
            new MapTileType( GeneralHelper.getUniqueString(), 0,
                             null, MapTileEdgeHelper.buildArray(),
                             MapTileOrientationHelper.ALL_ORIENTATIONS );
            Assert.fail( "Expected exception." );
        }
        catch ( IllegalArgumentException e )
        {
            Assert.assertEquals( "Unexpected message.", "Parameter 'mapUnits' cannot be null.",
                                 e.getMessage() );
        }
    }

    /**
     * Verify that the constructor throws the correct exception when the mapUnits parameter is an
     * empty array.
     */
    @Test
    public void constructor_mapUnits_emptyArray()
    {
        MapUnit[][] mapUnits = new MapUnit[][] {};

        try
        {
            new MapTileType( GeneralHelper.getUniqueString(), 0,
                             mapUnits, MapTileEdgeHelper.buildArray(),
                             MapTileOrientationHelper.ALL_ORIENTATIONS );
            Assert.fail( "Expected exception." );
        }
        catch ( IllegalArgumentException e )
        {
            Assert.assertEquals( "Unexpected message.",
                                 "Parameter 'mapUnits' must contain at least one element.",
                                 e.getMessage() );
        }
    }

    /**
     * Verify that the constructor throws the correct exception when the mapUnits parameter doesn't
     * contain the same number of elements in all of the rows.
     */
    @Test
    public void constructor_mapUnits_inconsistentRowCount()
    {
        MapUnit[][] mapUnits = new MapUnit[][]
        {
            {
                MapUnitHelper.build()
            },
            {
                MapUnitHelper.build(),
                MapUnitHelper.build()
            },
        };

        try
        {
            new MapTileType( GeneralHelper.getUniqueString(), 0,
                             mapUnits, MapTileEdgeHelper.buildArray(),
                             MapTileOrientationHelper.ALL_ORIENTATIONS );
            Assert.fail( "Expected exception." );
        }
        catch ( IllegalArgumentException e )
        {
            Assert.assertEquals(
                "Unexpected message.",
                "Parameter 'mapUnits' must contain the same number of elements in each row.",
                e.getMessage() );
        }
    }

    /**
     * Verify that the constructor throws the correct exception when the mapUnits parameter doesn't
     * contain the same number of columns as it does rows.
     */
    @Test
    public void constructor_mapUnits_columnCountNotEqualRowCount()
    {
        MapUnit[][] mapUnits = new MapUnit[][]
        {
            {
                MapUnitHelper.build()
            },
            {
                MapUnitHelper.build()
            },
        };

        try
        {
            new MapTileType( GeneralHelper.getUniqueString(), 0,
                             mapUnits, MapTileEdgeHelper.buildArray(),
                             MapTileOrientationHelper.ALL_ORIENTATIONS );
            Assert.fail( "Expected exception." );
        }
        catch ( IllegalArgumentException e )
        {
            Assert.assertEquals(
                "Unexpected message.",
                "Parameter 'mapUnits' must contain the same number of columns as it does rows.",
                e.getMessage() );
        }
    }

    /**
     * Verify that the constructor throws the correct exception when the mapUnits parameter contains
     * a null element.
     */
    @Test
    public void constructor_mapUnits_nullElement()
    {
        MapUnit[][] mapUnits = new MapUnit[][]
        {
            {
                null
            }
        };

        try
        {
            new MapTileType( GeneralHelper.getUniqueString(), 0,
                             mapUnits, MapTileEdgeHelper.buildArray(),
                             MapTileOrientationHelper.ALL_ORIENTATIONS );
            Assert.fail( "Expected exception." );
        }
        catch ( IllegalArgumentException e )
        {
            Assert.assertEquals(
                "Unexpected message.",
                "Parameter 'mapUnits' must not contain any null elements.",
                e.getMessage() );
        }
    }

    /**
     * Verify that the constructor throws the correct exception when the mapTileEdges parameter is
     * null.
     */
    @Test
    public void constructor_mapTileEdges_nullArray()
    {
        try
        {
            new MapTileType( GeneralHelper.getUniqueString(), 0,
                             MapUnitHelper.buildArray(), null,
                             MapTileOrientationHelper.ALL_ORIENTATIONS );
            Assert.fail( "Expected exception." );
        }
        catch ( IllegalArgumentException e )
        {
            Assert.assertEquals( "Unexpected message.", "Parameter 'mapTileEdges' cannot be null.",
                                 e.getMessage() );
        }
    }

    /**
     * Verify that the constructor throws the correct exception when the mapTileEdges parameter
     * doesn't contain enough elements.
     */
    @Test
    public void constructor_mapTileEdges_tooFew()
    {
        MapTileEdge[] mapTileEdges = new MapTileEdge[]
        {
            MapTileEdgeHelper.build(),
            MapTileEdgeHelper.build(),
            MapTileEdgeHelper.build()
        };

        try
        {
            new MapTileType( GeneralHelper.getUniqueString(), 0,
                             MapUnitHelper.buildArray(), mapTileEdges,
                             MapTileOrientationHelper.ALL_ORIENTATIONS );
            Assert.fail( "Expected exception." );
        }
        catch ( IllegalArgumentException e )
        {
            Assert.assertEquals( "Unexpected message.",
                                 "Parameter 'mapTileEdges' must contain 4 elements.",
                                 e.getMessage() );
        }
    }

    /**
     * Verify that the constructor throws the correct exception when the mapTileEdges parameter
     * contains too many elements.
     */
    @Test
    public void constructor_mapTileEdges_tooMany()
    {
        MapTileEdge[] mapTileEdges = new MapTileEdge[]
        {
            MapTileEdgeHelper.build(),
            MapTileEdgeHelper.build(),
            MapTileEdgeHelper.build(),
            MapTileEdgeHelper.build(),
            MapTileEdgeHelper.build()
        };

        try
        {
            new MapTileType( GeneralHelper.getUniqueString(), 0,
                             MapUnitHelper.buildArray(), mapTileEdges,
                             MapTileOrientationHelper.ALL_ORIENTATIONS );
            Assert.fail( "Expected exception." );
        }
        catch ( IllegalArgumentException e )
        {
            Assert.assertEquals( "Unexpected message.",
                                 "Parameter 'mapTileEdges' must contain 4 elements.",
                                 e.getMessage() );
        }
    }

    /**
     * Verify that the constructor throws the correct exception when the mapTileEdges parameter
     * contains a null element.
     */
    @Test
    public void constructor_mapTileEdges_nullElement()
    {
        MapTileEdge[] mapTileEdges = new MapTileEdge[]
        {
            MapTileEdgeHelper.build(),
            null,
            MapTileEdgeHelper.build(),
            MapTileEdgeHelper.build()
        };

        try
        {
            new MapTileType( GeneralHelper.getUniqueString(), 0,
                             MapUnitHelper.buildArray(), mapTileEdges,
                             MapTileOrientationHelper.ALL_ORIENTATIONS );
            Assert.fail( "Expected exception." );
        }
        catch ( IllegalArgumentException e )
        {
            Assert.assertEquals( "Unexpected message.",
                                 "Parameter 'mapTileEdges' must not contain any null elements.",
                                 e.getMessage() );
        }
    }

    /**
     * Verify that the constructor throws the correct exception when the distinctMapTileOrientations
     * parameter is null.
     */
    @Test
    public void constructor_distinctMapTileOrientations_nullArray()
    {
        try
        {
            new MapTileType( GeneralHelper.getUniqueString(), 0,
                             MapUnitHelper.buildArray(),
                             MapTileEdgeHelper.buildArray(), null );
            Assert.fail( "Expected exception." );
        }
        catch ( IllegalArgumentException e )
        {
            Assert.assertEquals( "Unexpected message.",
                                 "Parameter 'distinctMapTileOrientations' cannot be null.",
                                 e.getMessage() );
        }
    }

    /**
     * Verify that the constructor throws the correct exception when the distinctMapTileOrientations
     * parameter is an empty array.
     */
    @Test
    public void constructor_distinctMapTileOrientations_emptyArray()
    {
        MapTileOrientation[] distinctMapTileOrientations = new MapTileOrientation[] {};

        try
        {
            new MapTileType( GeneralHelper.getUniqueString(), 0,
                             MapUnitHelper.buildArray(), MapTileEdgeHelper.buildArray(),
                             distinctMapTileOrientations );
            Assert.fail( "Expected exception." );
        }
        catch ( IllegalArgumentException e )
        {
            Assert.assertEquals(
                    "Unexpected message.",
                    "Parameter 'distinctMapTileOrientations' must contain at least one element.",
                    e.getMessage() );
        }
    }

    /**
     * Verify that the constructor throws the correct exception when the distinctMapTileOrientations
     * parameter contains a null element.
     */
    @Test
    public void constructor_distinctMapTileOrientations_nullElement()
    {
        MapTileOrientation[] distinctMapTileOrientations = new MapTileOrientation[]
        {
            null
        };

        try
        {
            new MapTileType( GeneralHelper.getUniqueString(), 0,
                             MapUnitHelper.buildArray(), MapTileEdgeHelper.buildArray(),
                             distinctMapTileOrientations );
            Assert.fail( "Expected exception." );
        }
        catch ( IllegalArgumentException e )
        {
            Assert.assertEquals(
                    "Unexpected message.",
                    "Parameter 'distinctMapTileOrientations' must not contain any null elements.",
                    e.getMessage() );
        }
    }

    /**
     * Verify that the constructor throws the correct exception when the distinctMapTileOrientations
     * parameter contains a duplicate element.
     */
    @Test
    public void constructor_distinctMapTileOrientations_duplicateElement()
    {
        MapTileOrientation[] distinctMapTileOrientations = new MapTileOrientation[]
        {
            MapTileOrientation.UPRIGHT,
            MapTileOrientation.UPRIGHT
        };

        try
        {
            new MapTileType( GeneralHelper.getUniqueString(), 0,
                             MapUnitHelper.buildArray(), MapTileEdgeHelper.buildArray(),
                             distinctMapTileOrientations );
            Assert.fail( "Expected exception." );
        }
        catch ( IllegalArgumentException e )
        {
            Assert.assertEquals( "Unexpected message.",
                                 "Parameter 'distinctMapTileOrientations' must not contain any " +
                                 "duplicate elements.", e.getMessage() );
        }
    }

    /**
     * Verify that the constructor throws the correct exception when the openMapTileEdgePositions
     * parameter is null.
     */
    @Test
    public void constructor_openMapTileEdgePositions_nullArray()
    {
        try
        {
            new MapTileType( GeneralHelper.getUniqueString(), 0,
                             MapUnitHelper.buildArray(),
                             MapTileEdgeHelper.buildArray(),
                             MapTileOrientationHelper.ALL_ORIENTATIONS, null );
            Assert.fail( "Expected exception." );
        }
        catch ( IllegalArgumentException e )
        {
            Assert.assertEquals( "Unexpected message.",
                                 "Parameter 'openMapTileEdgePositions' cannot be null.",
                                 e.getMessage() );
        }
    }

    /**
     * Verify that the constructor throws the correct exception when the openMapTileEdgePositions
     * parameter is an empty array.
     */
    @Test
    public void constructor_openMapTileEdgePositions_emptyArray()
    {
        MapTileEdgePosition[] openMapTileEdgePositions = new MapTileEdgePosition[] {};

        try
        {
            new MapTileType( GeneralHelper.getUniqueString(), 0,
                             MapUnitHelper.buildArray(),
                             MapTileEdgeHelper.buildArray(),
                             MapTileOrientationHelper.ALL_ORIENTATIONS, openMapTileEdgePositions );
            Assert.fail( "Expected exception." );
        }
        catch ( IllegalArgumentException e )
        {
            Assert.assertEquals(
                    "Unexpected message.",
                    "Parameter 'openMapTileEdgePositions' must contain at least one element.",
                    e.getMessage() );
        }
    }

    /**
     * Verify that the constructor throws the correct exception when the openMapTileEdgePositions
     * parameter contains a null element.
     */
    @Test
    public void constructor_openMapTileEdgePositions_nullElement()
    {
        MapTileEdgePosition[] openMapTileEdgePositions = new MapTileEdgePosition[]
        {
            null
        };

        try
        {
            new MapTileType( GeneralHelper.getUniqueString(), 0,
                             MapUnitHelper.buildArray(),
                             MapTileEdgeHelper.buildArray(),
                             MapTileOrientationHelper.ALL_ORIENTATIONS, openMapTileEdgePositions );
            Assert.fail( "Expected exception." );
        }
        catch ( IllegalArgumentException e )
        {
            Assert.assertEquals(
                    "Unexpected message.",
                    "Parameter 'openMapTileEdgePositions' must not contain any null elements.",
                    e.getMessage() );
        }
    }

    /**
     * Verify that the constructor throws the correct exception when the openMapTileEdgePositions
     * parameter contains a duplicate element.
     */
    @Test
    public void constructor_openMapTileEdgePositions_duplicateElement()
    {
        MapTileEdgePosition[] openMapTileEdgePositions = new MapTileEdgePosition[]
        {
            MapTileEdgePosition.BOTTOM,
            MapTileEdgePosition.BOTTOM
        };

        try
        {
            new MapTileType( GeneralHelper.getUniqueString(), 0,
                             MapUnitHelper.buildArray(),
                             MapTileEdgeHelper.buildArray(),
                             MapTileOrientationHelper.ALL_ORIENTATIONS, openMapTileEdgePositions );
            Assert.fail( "Expected exception." );
        }
        catch ( IllegalArgumentException e )
        {
            Assert.assertEquals(
                    "Unexpected message.",
                    "Parameter 'openMapTileEdgePositions' must not contain any duplicate elements.",
                    e.getMessage() );
        }
    }

    /**
     * Verify that the getSize() method returns the correct value for a small array of map units.
     */
    @Test
    public void getSize()
    {
        int mapUnitArraySize = GeneralHelper.getRandom().nextInt( 5 ) + 1;
        MapTileType mapTileType = MapTileTypeHelper.build( mapUnitArraySize );
        Assert.assertEquals( "Unexpected return value.", mapUnitArraySize, mapTileType.getSize() );
    }

    /**
     * Verify that the getMapUnit() method throws the correct exception when the x parameter is too
     * small.
     */
    @Test
    public void getMapUnit_x_tooSmall()
    {
        MapTileType mapTileType = MapTileTypeHelper.build( 1 );

        try
        {
            mapTileType.getMapUnit( -1, 0 );
            Assert.fail( "Expected exception." );
        }
        catch ( IllegalArgumentException e )
        {
            Assert.assertEquals( "Unexpected message.",
                                 "Parameter 'x' must be greater than or equal to 0.",
                                 e.getMessage() );
        }
    }

    /**
     * Verify that the getMapUnit() method throws the correct exception when the x parameter is too
     * large.
     */
    @Test
    public void getMapUnit_x_tooLarge()
    {
        int mapUnitArraySize = GeneralHelper.getRandom().nextInt( 5 ) + 1;
        MapTileType mapTileType = MapTileTypeHelper.build( mapUnitArraySize );

        try
        {
            mapTileType.getMapUnit( mapUnitArraySize, 0 );
            Assert.fail( "Expected exception." );
        }
        catch ( IllegalArgumentException e )
        {
            Assert.assertEquals( "Unexpected message.",
                                 "Parameter 'x' must be less than " + mapUnitArraySize + ".",
                                 e.getMessage() );
        }
    }

    /**
     * Verify that the getMapUnit() method throws the correct exception when the y parameter is too
     * small.
     */
    @Test
    public void getMapUnit_y_tooSmall()
    {
        MapTileType mapTileType = MapTileTypeHelper.build( 1 );

        try
        {
            mapTileType.getMapUnit( 0, -1 );
            Assert.fail( "Expected exception." );
        }
        catch ( IllegalArgumentException e )
        {
            Assert.assertEquals( "Unexpected message.",
                                 "Parameter 'y' must be greater than or equal to 0.",
                                 e.getMessage() );
        }
    }

    /**
     * Verify that the getMapUnit() method throws the correct exception when the y parameter is too
     * large.
     */
    @Test
    public void getMapUnit_y_tooLarge()
    {
        int mapUnitArraySize = GeneralHelper.getRandom().nextInt( 5 ) + 1;
        MapTileType mapTileType = MapTileTypeHelper.build( mapUnitArraySize );

        try
        {
            mapTileType.getMapUnit( 0, mapUnitArraySize );
            Assert.fail( "Expected exception." );
        }
        catch ( IllegalArgumentException e )
        {
            Assert.assertEquals( "Unexpected message.",
                                 "Parameter 'y' must be less than " + mapUnitArraySize + ".",
                                 e.getMessage() );
        }
    }

    /**
     * Verify that the getMapUnit() method returns the correct values for all coordinates in a small
     * array of map units.
     */
    @Test
    public void getMapUnit()
    {
        int mapUnitArraySize = 2;
        MapUnit[][] mapUnits = MapUnitHelper.buildArray( mapUnitArraySize );
        MapTileType mapTileType = new MapTileType( GeneralHelper.getUniqueString(), 0,
                                                   mapUnits, MapTileEdgeHelper.buildArray(),
                                                   MapTileOrientationHelper.ALL_ORIENTATIONS );

        for ( int i = 0; i < mapUnitArraySize; i++ )
        {
            for ( int j = 0; j < mapUnitArraySize; j++ )
            {
                Assert.assertEquals( "Unexpected return value for (" + i + ", " + j + ").",
                                     mapUnits[ j ][ i ], mapTileType.getMapUnit( i, j ));
            }
        }
    }

    /**
     * Verify that the getMapTileEdge() method throws the correct exception when the
     * mapTileEdgePosition parameter is null.
     */
    @Test
    public void getMapTileEdge_mapTileEdgePosition_null()
    {
        MapTileType mapTileType = MapTileTypeHelper.build();

        try
        {
            mapTileType.getMapTileEdge( null );
            Assert.fail( "Expected exception." );
        }
        catch ( IllegalArgumentException e )
        {
            Assert.assertEquals( "Unexpected message.",
                                 "Parameter 'mapTileEdgePosition' cannot be null.",
                                 e.getMessage() );
        }
    }

    /**
     * Verify that the getMapTileEdge() method returns the correct values for all four edges.
     */
    @Test
    public void getMapTileEdge()
    {
        MapTileEdge[] mapTileEdges = MapTileEdgeHelper.buildArray();
        MapTileType mapTileType = new MapTileType( GeneralHelper.getUniqueString(), 0,
                                                   MapUnitHelper.buildArray(), mapTileEdges,
                                                   MapTileOrientationHelper.ALL_ORIENTATIONS );

        Assert.assertEquals( "Unexpected return value for \"top\".",
                             mapTileEdges[ 0 ],
                             mapTileType.getMapTileEdge( MapTileEdgePosition.TOP ));

        Assert.assertEquals( "Unexpected return value for \"right\".",
                             mapTileEdges[ 1 ],
                             mapTileType.getMapTileEdge( MapTileEdgePosition.RIGHT ));

        Assert.assertEquals( "Unexpected return value for \"bottom\".",
                             mapTileEdges[ 2 ],
                             mapTileType.getMapTileEdge( MapTileEdgePosition.BOTTOM ));

        Assert.assertEquals( "Unexpected return value for \"left\".",
                             mapTileEdges[ 3 ],
                             mapTileType.getMapTileEdge( MapTileEdgePosition.LEFT ));
    }

    /**
     * Verify that the getDistinctMapTileOrientations() method returns the correct value.
     */
    @Test
    public void getDistinctMapTileOrientations()
    {
        MapTileOrientation[] distinctMapTileOrientations = new MapTileOrientation[]
        {
            MapTileOrientation.UPRIGHT,
            MapTileOrientation.FLIPPED
        };

        MapTileType mapTileType = new MapTileType( GeneralHelper.getUniqueString(), 0,
                                                   MapUnitHelper.buildArray(),
                                                   MapTileEdgeHelper.buildArray(),
                                                   distinctMapTileOrientations );

        Assert.assertArrayEquals( "Unexpected result.", distinctMapTileOrientations,
                                  mapTileType.getDistinctMapTileOrientations() );
    }

    /**
     * Verify that the getOpenMapTileEdgePositions() method returns the correct value when the open
     * map tile edge positions have been specified.
     */
    @Test
    public void getOpenMapTileEdgePositions_specified()
    {
        MapTileEdgePosition[] openMapTileEdgePositions = new MapTileEdgePosition[]
        {
            MapTileEdgePosition.RIGHT,
            MapTileEdgePosition.BOTTOM
        };

        MapTileType mapTileType = new MapTileType( GeneralHelper.getUniqueString(), 0,
                                                   MapUnitHelper.buildArray(),
                                                   MapTileEdgeHelper.buildArray(),
                                                   MapTileOrientationHelper.ALL_ORIENTATIONS,
                                                   openMapTileEdgePositions );

        Assert.assertArrayEquals( "Unexpected result.", openMapTileEdgePositions,
                                  mapTileType.getOpenMapTileEdgePositions() );
    }

    /**
     * Verify that the getWeight() method returns the correct value.
     */
    @Test
    public void getWeight()
    {
        int weight = GeneralHelper.getRandom().nextInt( 1000 );

        MapTileType mapTileType = new MapTileType(
                GeneralHelper.getUniqueString(), weight,
                MapUnitHelper.buildArray(), MapTileEdgeHelper.buildArray(),
                MapTileOrientationHelper.ALL_ORIENTATIONS );

        Assert.assertEquals( "Unexpected result.", weight, mapTileType.getWeight() );
    }

    /**
     * Verify that the toString() method returns the correct value.
     */
    @Test
    public void toStringTest()
    {
        String name = GeneralHelper.getUniqueString();
        MapTileType mapTileType = new MapTileType(
                name, 0, MapUnitHelper.buildArray(), MapTileEdgeHelper.buildArray(),
                MapTileOrientationHelper.ALL_ORIENTATIONS );
        Assert.assertEquals( "Unexpected return value.", name, mapTileType.toString() );
    }

    /**
     * Verify that the equals() method returns the correct result when called with a null reference.
     */
    @Test
    public void equals_null()
    {
        MapTileType mapTileType1 = MapTileTypeHelper.build();
        MapTileType mapTileType2 = null;

        boolean result = mapTileType1.equals( mapTileType2 );
        Assert.assertEquals( "Unexpected result.", false, result );
    }

    /**
     * Verify that the equals() method returns the correct result when called with the wrong type of
     * object.
     */
    @Test
    public void equals_wrongType()
    {
        MapTileType mapTileType1 = MapTileTypeHelper.build();
        Object      mapTileType2 = new Object();

        boolean result = mapTileType1.equals( mapTileType2 );
        Assert.assertEquals( "Unexpected result.", false, result );
    }

    /**
     * Verify that the equals() method returns the correct result when called with a map tile type
     * with a different name.
     */
    @Test
    public void equals_differentName()
    {
        MapTileType mapTileType1 = MapTileTypeHelper.build();
        MapTileType mapTileType2 = MapTileTypeHelper.build();

        boolean result = mapTileType1.equals( mapTileType2 );
        Assert.assertEquals( "Unexpected result.", false, result );
    }

    /**
     * Verify that the equals() method returns the correct result when called with a map tile type
     * with an identical name.
     */
    @Test
    public void equals_identicalName()
    {
        String name = GeneralHelper.getUniqueString();

        MapTileType mapTileType1 = new MapTileType( name, 0, MapUnitHelper.buildArray(),
                                                    MapTileEdgeHelper.buildArray(),
                                                    MapTileOrientationHelper.ALL_ORIENTATIONS );

        MapTileType mapTileType2 = new MapTileType( name, 0, MapUnitHelper.buildArray(),
                                                    MapTileEdgeHelper.buildArray(),
                                                    MapTileOrientationHelper.ALL_ORIENTATIONS );

        boolean result = mapTileType1.equals( mapTileType2 );
        Assert.assertEquals( "Unexpected result.", true, result );
    }

    /**
     * Verify that the hashCode() method returns the correct value.
     */
    @Test
    public void hashCodeTest()
    {
        String name = GeneralHelper.getUniqueString();

        MapTileType mapTileType = new MapTileType( name, 0, MapUnitHelper.buildArray(),
                                                   MapTileEdgeHelper.buildArray(),
                                                   MapTileOrientationHelper.ALL_ORIENTATIONS );

        Assert.assertEquals( "Unexpected return value.", name.hashCode(), mapTileType.hashCode() );
    }
}
