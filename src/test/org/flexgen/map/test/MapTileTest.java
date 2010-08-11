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

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;

import org.flexgen.map.MapTile;
import org.flexgen.map.MapTileEdge;
import org.flexgen.map.MapTileEdgePosition;
import org.flexgen.map.MapTileOrientation;
import org.flexgen.map.MapTileType;
import org.flexgen.map.MapUnit;
import org.flexgen.test.helper.GeneralHelper;
import org.flexgen.test.helper.MapTileEdgeHelper;
import org.flexgen.test.helper.MapTileHelper;
import org.flexgen.test.helper.MapTileOrientationHelper;
import org.flexgen.test.helper.MapTileTypeHelper;
import org.flexgen.test.helper.MapUnitHelper;

/**
 * Test class for the MapTile class.
 */
public class MapTileTest
{
    /**
     * Verify that the constructor throws the correct exception when the mapTileType parameter is
     * null.
     */
    @Test
    public void constructor_nullType()
    {
        try
        {
            new MapTile( null, MapTileOrientation.UPRIGHT );
            Assert.fail( "Expected exception." );
        }
        catch ( IllegalArgumentException e )
        {
            Assert.assertEquals( "Unexpected message.", "Parameter 'mapTileType' cannot be null.",
                                 e.getMessage() );
        }
    }

    /**
     * Verify that the constructor throws the correct exception when the mapTileOrientation
     * parameter is null.
     */
    @Test
    public void constructor_nullOrientation()
    {
        try
        {
            new MapTile( MapTileTypeHelper.build(), null );
            Assert.fail( "Expected exception." );
        }
        catch ( IllegalArgumentException e )
        {
            Assert.assertEquals( "Unexpected message.",
                                 "Parameter 'mapTileOrientation' cannot be null.", e.getMessage() );
        }
    }

    /**
     * Verify that the getMapUnit() method throws the correct exception when the x parameter is too
     * small.
     */
    @Test
    public void getMapUnit_x_tooSmall()
    {
        MapTile mapTile = new MapTile( MapTileTypeHelper.build( 1 ),
                                       MapTileOrientation.UPRIGHT );

        try
        {
            mapTile.getMapUnit( -1, 0 );
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
        MapTile mapTile = new MapTile( MapTileTypeHelper.build( mapUnitArraySize ),
                                       MapTileOrientation.UPRIGHT );

        try
        {
            mapTile.getMapUnit( mapUnitArraySize, 0 );
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
        MapTile mapTile = new MapTile( MapTileTypeHelper.build( 1 ),
                                       MapTileOrientation.UPRIGHT );

        try
        {
            mapTile.getMapUnit( 0, -1 );
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
        MapTile mapTile = new MapTile( MapTileTypeHelper.build( mapUnitArraySize ),
                                       MapTileOrientation.UPRIGHT );

        try
        {
            mapTile.getMapUnit( 0, mapUnitArraySize );
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
     * Verify that the getMapUnit() method throws the correct exception when the map tile is
     * constructed with an unsupported map tile orientation.
     *
     * @throws Exception
     *             For any errors that occur.
     */
    @Test
    public void getMapUnit_unsupportedOrientation() throws Exception
    {
        // use reflection to construct an unsupported map tile orientation
        Class< MapTileOrientation > mapTileOrientationClass = MapTileOrientation.class;
        Constructor< MapTileOrientation > mapTileOrientationConstructor =
                mapTileOrientationClass.getDeclaredConstructor( String.class );
        mapTileOrientationConstructor.setAccessible( true );
        MapTileOrientation mapTileOrientation =
                mapTileOrientationConstructor.newInstance( GeneralHelper.getUniqueString() );

        MapTile mapTile = new MapTile( MapTileTypeHelper.build( 1 ), mapTileOrientation );

        try
        {
            mapTile.getMapUnit( 0, 0 );
            Assert.fail( "Expected exception." );
        }
        catch ( IllegalStateException e )
        {
            Assert.assertEquals( "Unexpected message.", "Unsupported orientation.",
                                 e.getMessage() );
        }
    }

    /**
     * Verify that the getMapUnit() method returns the correct values for all coordinates in a small
     * array of map units when the tile is in the upright orientation.
     */
    @Test
    public void getMapUnit_upright()
    {
        int mapUnitArraySize = 4;
        MapUnit[][] mapUnits = MapUnitHelper.buildArray( mapUnitArraySize );
        MapTile mapTile =
                new MapTile( new MapTileType( GeneralHelper.getUniqueString(), 0,
                                              mapUnits, MapTileEdgeHelper.buildArray(),
                                              MapTileOrientationHelper.ALL_ORIENTATIONS ),
                             MapTileOrientation.UPRIGHT );

        Assert.assertEquals( "Unexpected return value for (0, 0).",
                             mapUnits[ 0 ][ 0 ], mapTile.getMapUnit( 0, 0 ));

        Assert.assertEquals( "Unexpected return value for (1, 0).",
                             mapUnits[ 0 ][ 1 ], mapTile.getMapUnit( 1, 0 ));

        Assert.assertEquals( "Unexpected return value for (2, 0).",
                             mapUnits[ 0 ][ 2 ], mapTile.getMapUnit( 2, 0 ));

        Assert.assertEquals( "Unexpected return value for (3, 0).",
                             mapUnits[ 0 ][ 3 ], mapTile.getMapUnit( 3, 0 ));

        Assert.assertEquals( "Unexpected return value for (0, 1).",
                             mapUnits[ 1 ][ 0 ], mapTile.getMapUnit( 0, 1 ));

        Assert.assertEquals( "Unexpected return value for (1, 1).",
                             mapUnits[ 1 ][ 1 ], mapTile.getMapUnit( 1, 1 ));

        Assert.assertEquals( "Unexpected return value for (2, 1).",
                             mapUnits[ 1 ][ 2 ], mapTile.getMapUnit( 2, 1 ));

        Assert.assertEquals( "Unexpected return value for (3, 1).",
                             mapUnits[ 1 ][ 3 ], mapTile.getMapUnit( 3, 1 ));

        Assert.assertEquals( "Unexpected return value for (0, 2).",
                             mapUnits[ 2 ][ 0 ], mapTile.getMapUnit( 0, 2 ));

        Assert.assertEquals( "Unexpected return value for (1, 2).",
                             mapUnits[ 2 ][ 1 ], mapTile.getMapUnit( 1, 2 ));

        Assert.assertEquals( "Unexpected return value for (2, 2).",
                             mapUnits[ 2 ][ 2 ], mapTile.getMapUnit( 2, 2 ));

        Assert.assertEquals( "Unexpected return value for (3, 2).",
                             mapUnits[ 2 ][ 3 ], mapTile.getMapUnit( 3, 2 ));

        Assert.assertEquals( "Unexpected return value for (0, 3).",
                             mapUnits[ 3 ][ 0 ], mapTile.getMapUnit( 0, 3 ));

        Assert.assertEquals( "Unexpected return value for (1, 3).",
                             mapUnits[ 3 ][ 1 ], mapTile.getMapUnit( 1, 3 ));

        Assert.assertEquals( "Unexpected return value for (2, 3).",
                             mapUnits[ 3 ][ 2 ], mapTile.getMapUnit( 2, 3 ));

        Assert.assertEquals( "Unexpected return value for (3, 3).",
                             mapUnits[ 3 ][ 3 ], mapTile.getMapUnit( 3, 3 ));
    }

    /**
     * Verify that the getMapUnit() method returns the correct values for all coordinates in a small
     * array of map units when the tile is rotated 90 degrees clockwise.
     */
    @Test
    public void getMapUnit_clockwise()
    {
        int mapUnitArraySize = 4;
        MapUnit[][] mapUnits = MapUnitHelper.buildArray( mapUnitArraySize );
        MapTile mapTile =
                new MapTile( new MapTileType( GeneralHelper.getUniqueString(), 0,
                                              mapUnits, MapTileEdgeHelper.buildArray(),
                                              MapTileOrientationHelper.ALL_ORIENTATIONS ),
                             MapTileOrientation.CLOCKWISE );

        Assert.assertEquals( "Unexpected return value for (0, 0).",
                             mapUnits[ 3 ][ 0 ], mapTile.getMapUnit( 0, 0 ));

        Assert.assertEquals( "Unexpected return value for (1, 0).",
                             mapUnits[ 2 ][ 0 ], mapTile.getMapUnit( 1, 0 ));

        Assert.assertEquals( "Unexpected return value for (2, 0).",
                             mapUnits[ 1 ][ 0 ], mapTile.getMapUnit( 2, 0 ));

        Assert.assertEquals( "Unexpected return value for (3, 0).",
                             mapUnits[ 0 ][ 0 ], mapTile.getMapUnit( 3, 0 ));

        Assert.assertEquals( "Unexpected return value for (0, 1).",
                             mapUnits[ 3 ][ 1 ], mapTile.getMapUnit( 0, 1 ));

        Assert.assertEquals( "Unexpected return value for (1, 1).",
                             mapUnits[ 2 ][ 1 ], mapTile.getMapUnit( 1, 1 ));

        Assert.assertEquals( "Unexpected return value for (2, 1).",
                             mapUnits[ 1 ][ 1 ], mapTile.getMapUnit( 2, 1 ));

        Assert.assertEquals( "Unexpected return value for (3, 1).",
                             mapUnits[ 0 ][ 1 ], mapTile.getMapUnit( 3, 1 ));

        Assert.assertEquals( "Unexpected return value for (0, 2).",
                             mapUnits[ 3 ][ 2 ], mapTile.getMapUnit( 0, 2 ));

        Assert.assertEquals( "Unexpected return value for (1, 2).",
                             mapUnits[ 2 ][ 2 ], mapTile.getMapUnit( 1, 2 ));

        Assert.assertEquals( "Unexpected return value for (2, 2).",
                             mapUnits[ 1 ][ 2 ], mapTile.getMapUnit( 2, 2 ));

        Assert.assertEquals( "Unexpected return value for (3, 2).",
                             mapUnits[ 0 ][ 2 ], mapTile.getMapUnit( 3, 2 ));

        Assert.assertEquals( "Unexpected return value for (0, 3).",
                             mapUnits[ 3 ][ 3 ], mapTile.getMapUnit( 0, 3 ));

        Assert.assertEquals( "Unexpected return value for (1, 3).",
                             mapUnits[ 2 ][ 3 ], mapTile.getMapUnit( 1, 3 ));

        Assert.assertEquals( "Unexpected return value for (2, 3).",
                             mapUnits[ 1 ][ 3 ], mapTile.getMapUnit( 2, 3 ));

        Assert.assertEquals( "Unexpected return value for (3, 3).",
                             mapUnits[ 0 ][ 3 ], mapTile.getMapUnit( 3, 3 ));
    }

    /**
     * Verify that the getMapUnit() method returns the correct values for all coordinates in a small
     * array of map units when the tile is flipped around 180 degrees.
     */
    @Test
    public void getMapUnit_flipped()
    {
        int mapUnitArraySize = 4;
        MapUnit[][] mapUnits = MapUnitHelper.buildArray( mapUnitArraySize );
        MapTile mapTile =
                new MapTile( new MapTileType( GeneralHelper.getUniqueString(), 0,
                                              mapUnits, MapTileEdgeHelper.buildArray(),
                                              MapTileOrientationHelper.ALL_ORIENTATIONS ),
                             MapTileOrientation.FLIPPED );

        Assert.assertEquals( "Unexpected return value for (0, 0).",
                             mapUnits[ 3 ][ 3 ], mapTile.getMapUnit( 0, 0 ));

        Assert.assertEquals( "Unexpected return value for (1, 0).",
                             mapUnits[ 3 ][ 2 ], mapTile.getMapUnit( 1, 0 ));

        Assert.assertEquals( "Unexpected return value for (2, 0).",
                             mapUnits[ 3 ][ 1 ], mapTile.getMapUnit( 2, 0 ));

        Assert.assertEquals( "Unexpected return value for (3, 0).",
                             mapUnits[ 3 ][ 0 ], mapTile.getMapUnit( 3, 0 ));

        Assert.assertEquals( "Unexpected return value for (0, 1).",
                             mapUnits[ 2 ][ 3 ], mapTile.getMapUnit( 0, 1 ));

        Assert.assertEquals( "Unexpected return value for (1, 1).",
                             mapUnits[ 2 ][ 2 ], mapTile.getMapUnit( 1, 1 ));

        Assert.assertEquals( "Unexpected return value for (2, 1).",
                             mapUnits[ 2 ][ 1 ], mapTile.getMapUnit( 2, 1 ));

        Assert.assertEquals( "Unexpected return value for (3, 1).",
                             mapUnits[ 2 ][ 0 ], mapTile.getMapUnit( 3, 1 ));

        Assert.assertEquals( "Unexpected return value for (0, 2).",
                             mapUnits[ 1 ][ 3 ], mapTile.getMapUnit( 0, 2 ));

        Assert.assertEquals( "Unexpected return value for (1, 2).",
                             mapUnits[ 1 ][ 2 ], mapTile.getMapUnit( 1, 2 ));

        Assert.assertEquals( "Unexpected return value for (2, 2).",
                             mapUnits[ 1 ][ 1 ], mapTile.getMapUnit( 2, 2 ));

        Assert.assertEquals( "Unexpected return value for (3, 2).",
                             mapUnits[ 1 ][ 0 ], mapTile.getMapUnit( 3, 2 ));

        Assert.assertEquals( "Unexpected return value for (0, 3).",
                             mapUnits[ 0 ][ 3 ], mapTile.getMapUnit( 0, 3 ));

        Assert.assertEquals( "Unexpected return value for (1, 3).",
                             mapUnits[ 0 ][ 2 ], mapTile.getMapUnit( 1, 3 ));

        Assert.assertEquals( "Unexpected return value for (2, 3).",
                             mapUnits[ 0 ][ 1 ], mapTile.getMapUnit( 2, 3 ));

        Assert.assertEquals( "Unexpected return value for (3, 3).",
                             mapUnits[ 0 ][ 0 ], mapTile.getMapUnit( 3, 3 ));
    }

    /**
     * Verify that the getMapUnit() method returns the correct values for all coordinates in a small
     * array of map units when the tile is rotated 90 degrees counter-clockwise.
     */
    @Test
    public void getMapUnit_counterClockwise()
    {
        int mapUnitArraySize = 4;
        MapUnit[][] mapUnits = MapUnitHelper.buildArray( mapUnitArraySize );
        MapTile mapTile =
                new MapTile( new MapTileType( GeneralHelper.getUniqueString(), 0,
                                              mapUnits, MapTileEdgeHelper.buildArray(),
                                              MapTileOrientationHelper.ALL_ORIENTATIONS ),
                             MapTileOrientation.COUNTER_CLOCKWISE );

        Assert.assertEquals( "Unexpected return value for (0, 0).",
                             mapUnits[ 0 ][ 3 ], mapTile.getMapUnit( 0, 0 ));

        Assert.assertEquals( "Unexpected return value for (1, 0).",
                             mapUnits[ 1 ][ 3 ], mapTile.getMapUnit( 1, 0 ));

        Assert.assertEquals( "Unexpected return value for (2, 0).",
                             mapUnits[ 2 ][ 3 ], mapTile.getMapUnit( 2, 0 ));

        Assert.assertEquals( "Unexpected return value for (3, 0).",
                             mapUnits[ 3 ][ 3 ], mapTile.getMapUnit( 3, 0 ));

        Assert.assertEquals( "Unexpected return value for (0, 1).",
                             mapUnits[ 0 ][ 2 ], mapTile.getMapUnit( 0, 1 ));

        Assert.assertEquals( "Unexpected return value for (1, 1).",
                             mapUnits[ 1 ][ 2 ], mapTile.getMapUnit( 1, 1 ));

        Assert.assertEquals( "Unexpected return value for (2, 1).",
                             mapUnits[ 2 ][ 2 ], mapTile.getMapUnit( 2, 1 ));

        Assert.assertEquals( "Unexpected return value for (3, 1).",
                             mapUnits[ 3 ][ 2 ], mapTile.getMapUnit( 3, 1 ));

        Assert.assertEquals( "Unexpected return value for (0, 2).",
                             mapUnits[ 0 ][ 1 ], mapTile.getMapUnit( 0, 2 ));

        Assert.assertEquals( "Unexpected return value for (1, 2).",
                             mapUnits[ 1 ][ 1 ], mapTile.getMapUnit( 1, 2 ));

        Assert.assertEquals( "Unexpected return value for (2, 2).",
                             mapUnits[ 2 ][ 1 ], mapTile.getMapUnit( 2, 2 ));

        Assert.assertEquals( "Unexpected return value for (3, 2).",
                             mapUnits[ 3 ][ 1 ], mapTile.getMapUnit( 3, 2 ));

        Assert.assertEquals( "Unexpected return value for (0, 3).",
                             mapUnits[ 0 ][ 0 ], mapTile.getMapUnit( 0, 3 ));

        Assert.assertEquals( "Unexpected return value for (1, 3).",
                             mapUnits[ 1 ][ 0 ], mapTile.getMapUnit( 1, 3 ));

        Assert.assertEquals( "Unexpected return value for (2, 3).",
                             mapUnits[ 2 ][ 0 ], mapTile.getMapUnit( 2, 3 ));

        Assert.assertEquals( "Unexpected return value for (3, 3).",
                             mapUnits[ 3 ][ 0 ], mapTile.getMapUnit( 3, 3 ));
    }

    /**
     * Verify that the getMapTileEdge() method throws the correct exception when the
     * mapTileEdgePosition parameter is null.
     */
    @Test
    public void getMapTileEdge_mapTileEdgePosition_null()
    {
        MapTile mapTile = MapTileHelper.build();

        try
        {
            mapTile.getMapTileEdge( null );
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
     * Verify that the getMapTileEdge() method returns the correct values for all four edges when
     * the tile is in the upright orientation.
     */
    @Test
    public void getMapTileEdge_upright()
    {
        MapTileEdge[] mapTileEdges = MapTileEdgeHelper.buildArray();
        MapTile mapTile =
                new MapTile( new MapTileType( GeneralHelper.getUniqueString(), 0,
                                              MapUnitHelper.buildArray(), mapTileEdges,
                                              MapTileOrientationHelper.ALL_ORIENTATIONS ),
                             MapTileOrientation.UPRIGHT );

        Assert.assertEquals( "Unexpected return value for \"top\".",
                             mapTileEdges[ 0 ],
                             mapTile.getMapTileEdge( MapTileEdgePosition.TOP ));

        Assert.assertEquals( "Unexpected return value for \"right\".",
                             mapTileEdges[ 1 ],
                             mapTile.getMapTileEdge( MapTileEdgePosition.RIGHT ));

        Assert.assertEquals( "Unexpected return value for \"bottom\".",
                             mapTileEdges[ 2 ],
                             mapTile.getMapTileEdge( MapTileEdgePosition.BOTTOM ));

        Assert.assertEquals( "Unexpected return value for \"left\".",
                             mapTileEdges[ 3 ],
                             mapTile.getMapTileEdge( MapTileEdgePosition.LEFT ));
    }

    /**
     * Verify that the getMapTileEdge() method returns the correct values for all four edges when
     * the tile is rotated 90 degrees clockwise.
     */
    @Test
    public void getMapTileEdge_clockwise()
    {
        MapTileEdge[] mapTileEdges = MapTileEdgeHelper.buildArray();
        MapTile mapTile =
                new MapTile( new MapTileType( GeneralHelper.getUniqueString(), 0,
                                              MapUnitHelper.buildArray(), mapTileEdges,
                                              MapTileOrientationHelper.ALL_ORIENTATIONS ),
                             MapTileOrientation.CLOCKWISE );

        Assert.assertEquals( "Unexpected return value for \"top\".",
                             mapTileEdges[ 3 ],
                             mapTile.getMapTileEdge( MapTileEdgePosition.TOP ));

        Assert.assertEquals( "Unexpected return value for \"right\".",
                             mapTileEdges[ 0 ],
                             mapTile.getMapTileEdge( MapTileEdgePosition.RIGHT ));

        Assert.assertEquals( "Unexpected return value for \"bottom\".",
                             mapTileEdges[ 1 ],
                             mapTile.getMapTileEdge( MapTileEdgePosition.BOTTOM ));

        Assert.assertEquals( "Unexpected return value for \"left\".",
                             mapTileEdges[ 2 ],
                             mapTile.getMapTileEdge( MapTileEdgePosition.LEFT ));
    }

    /**
     * Verify that the getMapTileEdge() method returns the correct values for all four edges when
     * the tile is flipped around 180 degrees.
     */
    @Test
    public void getMapTileEdge_flipped()
    {
        MapTileEdge[] mapTileEdges = MapTileEdgeHelper.buildArray();
        MapTile mapTile =
                new MapTile( new MapTileType( GeneralHelper.getUniqueString(), 0,
                                              MapUnitHelper.buildArray(), mapTileEdges,
                                              MapTileOrientationHelper.ALL_ORIENTATIONS ),
                             MapTileOrientation.FLIPPED );

        Assert.assertEquals( "Unexpected return value for \"top\".",
                             mapTileEdges[ 2 ],
                             mapTile.getMapTileEdge( MapTileEdgePosition.TOP ));

        Assert.assertEquals( "Unexpected return value for \"right\".",
                             mapTileEdges[ 3 ],
                             mapTile.getMapTileEdge( MapTileEdgePosition.RIGHT ));

        Assert.assertEquals( "Unexpected return value for \"bottom\".",
                             mapTileEdges[ 0 ],
                             mapTile.getMapTileEdge( MapTileEdgePosition.BOTTOM ));

        Assert.assertEquals( "Unexpected return value for \"left\".",
                             mapTileEdges[ 1 ],
                             mapTile.getMapTileEdge( MapTileEdgePosition.LEFT ));
    }

    /**
     * Verify that the getMapTileEdge() method returns the correct values for all four edges when
     * the tile is rotated 90 degrees counter-clockwise.
     */
    @Test
    public void getMapTileEdge_counterClockwise()
    {
        MapTileEdge[] mapTileEdges = MapTileEdgeHelper.buildArray();
        MapTile mapTile =
                new MapTile( new MapTileType( GeneralHelper.getUniqueString(), 0,
                                              MapUnitHelper.buildArray(), mapTileEdges,
                                              MapTileOrientationHelper.ALL_ORIENTATIONS ),
                             MapTileOrientation.COUNTER_CLOCKWISE );

        Assert.assertEquals( "Unexpected return value for \"top\".",
                             mapTileEdges[ 1 ],
                             mapTile.getMapTileEdge( MapTileEdgePosition.TOP ));

        Assert.assertEquals( "Unexpected return value for \"right\".",
                             mapTileEdges[ 2 ],
                             mapTile.getMapTileEdge( MapTileEdgePosition.RIGHT ));

        Assert.assertEquals( "Unexpected return value for \"bottom\".",
                             mapTileEdges[ 3 ],
                             mapTile.getMapTileEdge( MapTileEdgePosition.BOTTOM ));

        Assert.assertEquals( "Unexpected return value for \"left\".",
                             mapTileEdges[ 0 ],
                             mapTile.getMapTileEdge( MapTileEdgePosition.LEFT ));
    }

    /**
     * Verify that the getOpenMapTileEdgePositions() method returns the correct value when the tile
     * is in the upright orientation.
     */
    @Test
    public void getOpenMapTileEdgePositions_upright()
    {
        MapTileEdgePosition[] openMapTileEdgePositions = new MapTileEdgePosition[]
        {
            MapTileEdgePosition.RIGHT,
            MapTileEdgePosition.BOTTOM
        };

        MapTile mapTile = new MapTile( new MapTileType( GeneralHelper.getUniqueString(), 0,
                                                        MapUnitHelper.buildArray(),
                                                        MapTileEdgeHelper.buildArray(),
                                                        MapTileOrientationHelper.ALL_ORIENTATIONS,
                                                        openMapTileEdgePositions ),
                                       MapTileOrientation.UPRIGHT );

        Collection< MapTileEdgePosition > actualOpenMapTileEdgePositions =
                Arrays.asList( mapTile.getOpenMapTileEdgePositions() );

        Assert.assertEquals( "Unexpected collection size.", 2,
                             actualOpenMapTileEdgePositions.size() );

        Assert.assertTrue( "Collection does not contain \"RIGHT\".",
                           actualOpenMapTileEdgePositions.contains( MapTileEdgePosition.RIGHT ));
        Assert.assertTrue( "Collection does not contain \"BOTTOM\".",
                           actualOpenMapTileEdgePositions.contains( MapTileEdgePosition.BOTTOM ));
    }

    /**
     * Verify that the toString() method returns the correct value.
     */
    @Test
    public void toStringTest()
    {
        MapTileType mapTileType = MapTileTypeHelper.build();
        MapTileOrientation mapTileOrientation = MapTileOrientationHelper.getRandomOrientation();
        MapTile mapTile = new MapTile( mapTileType, mapTileOrientation );

        Assert.assertEquals(
                "Unexpected return value.",
                "{ " + mapTileType.toString() + ", " + mapTileOrientation.toString() + " }",
                mapTile.toString() );
    }

    /**
     * Verify that the equals() method returns the correct result when called with a null reference.
     */
    @Test
    public void equals_null()
    {
        MapTile mapTile1 = MapTileHelper.build();
        MapTile mapTile2 = null;

        boolean result = mapTile1.equals( mapTile2 );
        Assert.assertEquals( "Unexpected result.", false, result );
    }

    /**
     * Verify that the equals() method returns the correct result when called with the wrong type of
     * object.
     */
    @Test
    public void equals_wrongType()
    {
        MapTile mapTile1 = MapTileHelper.build();
        Object  mapTile2 = new Object();

        boolean result = mapTile1.equals( mapTile2 );
        Assert.assertEquals( "Unexpected result.", false, result );
    }

    /**
     * Verify that the equals() method returns the correct result when called with a map tile with a
     * different map tile type.
     */
    @Test
    public void equals_differentMapTileType()
    {
        MapTile mapTile1 = new MapTile( MapTileTypeHelper.build(), MapTileOrientation.UPRIGHT );
        MapTile mapTile2 = new MapTile( MapTileTypeHelper.build(), MapTileOrientation.UPRIGHT );

        boolean result = mapTile1.equals( mapTile2 );
        Assert.assertEquals( "Unexpected result.", false, result );
    }

    /**
     * Verify that the equals() method returns the correct result when called with a map tile with a
     * different map tile orientation.
     */
    @Test
    public void equals_differentMapTileOrientation()
    {
        MapTileType mapTileType = MapTileTypeHelper.build();
        MapTile mapTile1 = new MapTile( mapTileType, MapTileOrientation.UPRIGHT );
        MapTile mapTile2 = new MapTile( mapTileType, MapTileOrientation.FLIPPED );

        boolean result = mapTile1.equals( mapTile2 );
        Assert.assertEquals( "Unexpected result.", false, result );
    }

    /**
     * Verify that the equals() method returns the correct result when called with an identical map
     * tile.
     */
    @Test
    public void equals_identical()
    {
        MapTileType mapTileType = MapTileTypeHelper.build();
        MapTile mapTile1 = new MapTile( mapTileType, MapTileOrientation.UPRIGHT );
        MapTile mapTile2 = new MapTile( mapTileType, MapTileOrientation.UPRIGHT );

        boolean result = mapTile1.equals( mapTile2 );
        Assert.assertEquals( "Unexpected result.", true, result );
    }

    /**
     * Verify that the hashCode() method returns the correct value.
     */
    @Test
    public void hashCodeTest()
    {
        MapTileType mapTileType = MapTileTypeHelper.build();
        MapTileOrientation mapTileOrientation = MapTileOrientationHelper.getRandomOrientation();
        MapTile mapTile = new MapTile( mapTileType, mapTileOrientation );

        Assert.assertEquals( "Unexpected return value.",
                             mapTileType.hashCode() ^ mapTileOrientation.hashCode(),
                             mapTile.hashCode() );
    }
}
