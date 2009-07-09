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

import org.flexgen.map.MapTile;
import org.flexgen.map.MapTileOrientation;
import org.flexgen.map.MapTileType;
import org.flexgen.map.MapUnit;
import org.flexgen.test.helper.GeneralHelper;
import org.flexgen.test.helper.MapTileEdgeHelper;
import org.flexgen.test.helper.MapTileHelper;
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
            new MapTile( MapTileTypeHelper.build( 1 ), null );
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
        int size = GeneralHelper.getRandom().nextInt( 5 ) + 1;
        MapTile mapTile = new MapTile( MapTileTypeHelper.build( size ),
                                       MapTileOrientation.UPRIGHT );

        try
        {
            mapTile.getMapUnit( size, 0 );
            Assert.fail( "Expected exception." );
        }
        catch ( IllegalArgumentException e )
        {
            Assert.assertEquals( "Unexpected message.",
                                 "Parameter 'x' must be less than " + size + ".", e.getMessage() );
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
        int size = GeneralHelper.getRandom().nextInt( 5 ) + 1;
        MapTile mapTile = new MapTile( MapTileTypeHelper.build( size ),
                                       MapTileOrientation.UPRIGHT );

        try
        {
            mapTile.getMapUnit( 0, size );
            Assert.fail( "Expected exception." );
        }
        catch ( IllegalArgumentException e )
        {
            Assert.assertEquals( "Unexpected message.",
                                 "Parameter 'y' must be less than " + size + ".", e.getMessage() );
        }
    }

    /**
     * Verify that the getMapUnit() method returns the correct values for all coordinates in a small
     * array of map units when the tile is in the upright orientation.
     */
    @Test
    public void getMapUnit_upright()
    {
        int size = 4;
        MapUnit[][] mapUnits = MapUnitHelper.buildArray( size );
        MapTile mapTile = new MapTile( new MapTileType( mapUnits, MapTileEdgeHelper.buildArray() ),
                                       MapTileOrientation.UPRIGHT );

        Assert.assertEquals( "Unexpected return value for (0, 0).",
                             mapUnits[ 0 ][ 0 ], mapTile.getMapUnit( 0, 0 ));

        Assert.assertEquals( "Unexpected return value for (1, 0).",
                             mapUnits[ 1 ][ 0 ], mapTile.getMapUnit( 1, 0 ));

        Assert.assertEquals( "Unexpected return value for (2, 0).",
                             mapUnits[ 2 ][ 0 ], mapTile.getMapUnit( 2, 0 ));

        Assert.assertEquals( "Unexpected return value for (3, 0).",
                             mapUnits[ 3 ][ 0 ], mapTile.getMapUnit( 3, 0 ));

        Assert.assertEquals( "Unexpected return value for (0, 1).",
                             mapUnits[ 0 ][ 1 ], mapTile.getMapUnit( 0, 1 ));

        Assert.assertEquals( "Unexpected return value for (1, 1).",
                             mapUnits[ 1 ][ 1 ], mapTile.getMapUnit( 1, 1 ));

        Assert.assertEquals( "Unexpected return value for (2, 1).",
                             mapUnits[ 2 ][ 1 ], mapTile.getMapUnit( 2, 1 ));

        Assert.assertEquals( "Unexpected return value for (3, 1).",
                             mapUnits[ 3 ][ 1 ], mapTile.getMapUnit( 3, 1 ));

        Assert.assertEquals( "Unexpected return value for (0, 2).",
                             mapUnits[ 0 ][ 2 ], mapTile.getMapUnit( 0, 2 ));

        Assert.assertEquals( "Unexpected return value for (1, 2).",
                             mapUnits[ 1 ][ 2 ], mapTile.getMapUnit( 1, 2 ));

        Assert.assertEquals( "Unexpected return value for (2, 2).",
                             mapUnits[ 2 ][ 2 ], mapTile.getMapUnit( 2, 2 ));

        Assert.assertEquals( "Unexpected return value for (3, 2).",
                             mapUnits[ 3 ][ 2 ], mapTile.getMapUnit( 3, 2 ));

        Assert.assertEquals( "Unexpected return value for (0, 3).",
                             mapUnits[ 0 ][ 3 ], mapTile.getMapUnit( 0, 3 ));

        Assert.assertEquals( "Unexpected return value for (1, 3).",
                             mapUnits[ 1 ][ 3 ], mapTile.getMapUnit( 1, 3 ));

        Assert.assertEquals( "Unexpected return value for (2, 3).",
                             mapUnits[ 2 ][ 3 ], mapTile.getMapUnit( 2, 3 ));

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
        int size = 4;
        MapUnit[][] mapUnits = MapUnitHelper.buildArray( size );
        MapTile mapTile = new MapTile( new MapTileType( mapUnits, MapTileEdgeHelper.buildArray() ),
                                       MapTileOrientation.CLOCKWISE );

        Assert.assertEquals( "Unexpected return value for (0, 0).",
                             mapUnits[ 3 ][ 0 ], mapTile.getMapUnit( 0, 0 ));

        Assert.assertEquals( "Unexpected return value for (1, 0).",
                             mapUnits[ 3 ][ 1 ], mapTile.getMapUnit( 1, 0 ));

        Assert.assertEquals( "Unexpected return value for (2, 0).",
                             mapUnits[ 3 ][ 2 ], mapTile.getMapUnit( 2, 0 ));

        Assert.assertEquals( "Unexpected return value for (3, 0).",
                             mapUnits[ 3 ][ 3 ], mapTile.getMapUnit( 3, 0 ));

        Assert.assertEquals( "Unexpected return value for (0, 1).",
                             mapUnits[ 2 ][ 0 ], mapTile.getMapUnit( 0, 1 ));

        Assert.assertEquals( "Unexpected return value for (1, 1).",
                             mapUnits[ 2 ][ 1 ], mapTile.getMapUnit( 1, 1 ));

        Assert.assertEquals( "Unexpected return value for (2, 1).",
                             mapUnits[ 2 ][ 2 ], mapTile.getMapUnit( 2, 1 ));

        Assert.assertEquals( "Unexpected return value for (3, 1).",
                             mapUnits[ 2 ][ 3 ], mapTile.getMapUnit( 3, 1 ));

        Assert.assertEquals( "Unexpected return value for (0, 2).",
                             mapUnits[ 1 ][ 0 ], mapTile.getMapUnit( 0, 2 ));

        Assert.assertEquals( "Unexpected return value for (1, 2).",
                             mapUnits[ 1 ][ 1 ], mapTile.getMapUnit( 1, 2 ));

        Assert.assertEquals( "Unexpected return value for (2, 2).",
                             mapUnits[ 1 ][ 2 ], mapTile.getMapUnit( 2, 2 ));

        Assert.assertEquals( "Unexpected return value for (3, 2).",
                             mapUnits[ 1 ][ 3 ], mapTile.getMapUnit( 3, 2 ));

        Assert.assertEquals( "Unexpected return value for (0, 3).",
                             mapUnits[ 0 ][ 0 ], mapTile.getMapUnit( 0, 3 ));

        Assert.assertEquals( "Unexpected return value for (1, 3).",
                             mapUnits[ 0 ][ 1 ], mapTile.getMapUnit( 1, 3 ));

        Assert.assertEquals( "Unexpected return value for (2, 3).",
                             mapUnits[ 0 ][ 2 ], mapTile.getMapUnit( 2, 3 ));

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
        int size = 4;
        MapUnit[][] mapUnits = MapUnitHelper.buildArray( size );
        MapTile mapTile = new MapTile( new MapTileType( mapUnits, MapTileEdgeHelper.buildArray() ),
                                       MapTileOrientation.FLIPPED );

        Assert.assertEquals( "Unexpected return value for (0, 0).",
                             mapUnits[ 3 ][ 3 ], mapTile.getMapUnit( 0, 0 ));

        Assert.assertEquals( "Unexpected return value for (1, 0).",
                             mapUnits[ 2 ][ 3 ], mapTile.getMapUnit( 1, 0 ));

        Assert.assertEquals( "Unexpected return value for (2, 0).",
                             mapUnits[ 1 ][ 3 ], mapTile.getMapUnit( 2, 0 ));

        Assert.assertEquals( "Unexpected return value for (3, 0).",
                             mapUnits[ 0 ][ 3 ], mapTile.getMapUnit( 3, 0 ));

        Assert.assertEquals( "Unexpected return value for (0, 1).",
                             mapUnits[ 3 ][ 2 ], mapTile.getMapUnit( 0, 1 ));

        Assert.assertEquals( "Unexpected return value for (1, 1).",
                             mapUnits[ 2 ][ 2 ], mapTile.getMapUnit( 1, 1 ));

        Assert.assertEquals( "Unexpected return value for (2, 1).",
                             mapUnits[ 1 ][ 2 ], mapTile.getMapUnit( 2, 1 ));

        Assert.assertEquals( "Unexpected return value for (3, 1).",
                             mapUnits[ 0 ][ 2 ], mapTile.getMapUnit( 3, 1 ));

        Assert.assertEquals( "Unexpected return value for (0, 2).",
                             mapUnits[ 3 ][ 1 ], mapTile.getMapUnit( 0, 2 ));

        Assert.assertEquals( "Unexpected return value for (1, 2).",
                             mapUnits[ 2 ][ 1 ], mapTile.getMapUnit( 1, 2 ));

        Assert.assertEquals( "Unexpected return value for (2, 2).",
                             mapUnits[ 1 ][ 1 ], mapTile.getMapUnit( 2, 2 ));

        Assert.assertEquals( "Unexpected return value for (3, 2).",
                             mapUnits[ 0 ][ 1 ], mapTile.getMapUnit( 3, 2 ));

        Assert.assertEquals( "Unexpected return value for (0, 3).",
                             mapUnits[ 3 ][ 0 ], mapTile.getMapUnit( 0, 3 ));

        Assert.assertEquals( "Unexpected return value for (1, 3).",
                             mapUnits[ 2 ][ 0 ], mapTile.getMapUnit( 1, 3 ));

        Assert.assertEquals( "Unexpected return value for (2, 3).",
                             mapUnits[ 1 ][ 0 ], mapTile.getMapUnit( 2, 3 ));

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
        int size = 4;
        MapUnit[][] mapUnits = MapUnitHelper.buildArray( size );
        MapTile mapTile = new MapTile( new MapTileType( mapUnits, MapTileEdgeHelper.buildArray() ),
                                       MapTileOrientation.COUNTER_CLOCKWISE );

        Assert.assertEquals( "Unexpected return value for (0, 0).",
                             mapUnits[ 0 ][ 3 ], mapTile.getMapUnit( 0, 0 ));

        Assert.assertEquals( "Unexpected return value for (1, 0).",
                             mapUnits[ 0 ][ 2 ], mapTile.getMapUnit( 1, 0 ));

        Assert.assertEquals( "Unexpected return value for (2, 0).",
                             mapUnits[ 0 ][ 1 ], mapTile.getMapUnit( 2, 0 ));

        Assert.assertEquals( "Unexpected return value for (3, 0).",
                             mapUnits[ 0 ][ 0 ], mapTile.getMapUnit( 3, 0 ));

        Assert.assertEquals( "Unexpected return value for (0, 1).",
                             mapUnits[ 1 ][ 3 ], mapTile.getMapUnit( 0, 1 ));

        Assert.assertEquals( "Unexpected return value for (1, 1).",
                             mapUnits[ 1 ][ 2 ], mapTile.getMapUnit( 1, 1 ));

        Assert.assertEquals( "Unexpected return value for (2, 1).",
                             mapUnits[ 1 ][ 1 ], mapTile.getMapUnit( 2, 1 ));

        Assert.assertEquals( "Unexpected return value for (3, 1).",
                             mapUnits[ 1 ][ 0 ], mapTile.getMapUnit( 3, 1 ));

        Assert.assertEquals( "Unexpected return value for (0, 2).",
                             mapUnits[ 2 ][ 3 ], mapTile.getMapUnit( 0, 2 ));

        Assert.assertEquals( "Unexpected return value for (1, 2).",
                             mapUnits[ 2 ][ 2 ], mapTile.getMapUnit( 1, 2 ));

        Assert.assertEquals( "Unexpected return value for (2, 2).",
                             mapUnits[ 2 ][ 1 ], mapTile.getMapUnit( 2, 2 ));

        Assert.assertEquals( "Unexpected return value for (3, 2).",
                             mapUnits[ 2 ][ 0 ], mapTile.getMapUnit( 3, 2 ));

        Assert.assertEquals( "Unexpected return value for (0, 3).",
                             mapUnits[ 3 ][ 3 ], mapTile.getMapUnit( 0, 3 ));

        Assert.assertEquals( "Unexpected return value for (1, 3).",
                             mapUnits[ 3 ][ 2 ], mapTile.getMapUnit( 1, 3 ));

        Assert.assertEquals( "Unexpected return value for (2, 3).",
                             mapUnits[ 3 ][ 1 ], mapTile.getMapUnit( 2, 3 ));

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
}
