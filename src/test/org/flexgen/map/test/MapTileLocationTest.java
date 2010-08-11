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

import org.flexgen.map.MapTileEdgePosition;
import org.flexgen.map.MapTileLocation;
import org.flexgen.test.helper.GeneralHelper;

/**
 * Test class for the MapTileLocation class.
 */
public class MapTileLocationTest
{
    /**
     * Verify that the getX() method returns the correct value.
     */
    @Test
    public void getX()
    {
        int value = GeneralHelper.getRandom().nextInt( 1000 );
        MapTileLocation mapTileLocation = new MapTileLocation( value, 0 );
        Assert.assertEquals( "Unexpected return value.", value, mapTileLocation.getX() );
    }

    /**
     * Verify that the getY() method returns the correct value.
     */
    @Test
    public void getY()
    {
        int value = GeneralHelper.getRandom().nextInt( 1000 );
        MapTileLocation mapTileLocation = new MapTileLocation( 0, value );
        Assert.assertEquals( "Unexpected return value.", value, mapTileLocation.getY() );
    }

    /**
     * Verify that the getNeighborLocations() method returns the correct value when called without
     * specifying the neighboring map tile edge positions.
     */
    @Test
    public void getNeighborLocations_defaultNeighbors()
    {
        int x = GeneralHelper.getRandom().nextInt( 1000 );
        int y = GeneralHelper.getRandom().nextInt( 1000 );
        MapTileLocation mapTileLocation = new MapTileLocation( x, y );

        MapTileLocation[] neighborLocations = new MapTileLocation[]
        {
            new MapTileLocation( x + 1, y ),
            new MapTileLocation( x - 1, y ),
            new MapTileLocation( x, y + 1 ),
            new MapTileLocation( x, y - 1 )
        };

        Assert.assertArrayEquals( "Unexpected return value.", neighborLocations,
                                  mapTileLocation.getNeighborLocations() );
    }

    /**
     * Verify that the getNeighborLocations() method returns the correct value when called with
     * specifying the top neighboring map tile edge position.
     */
    @Test
    public void getNeighborLocations_topNeighbor()
    {
        int x = GeneralHelper.getRandom().nextInt( 1000 );
        int y = GeneralHelper.getRandom().nextInt( 1000 );
        MapTileLocation mapTileLocation = new MapTileLocation( x, y );

        MapTileLocation[] neighborLocations = new MapTileLocation[]
        {
            new MapTileLocation( x, y - 1 )
        };

        MapTileEdgePosition[] neighborMapTileEdgePositions = new MapTileEdgePosition[]
        {
            MapTileEdgePosition.TOP
        };

        Assert.assertArrayEquals(
                "Unexpected return value.", neighborLocations,
                mapTileLocation.getNeighborLocations( neighborMapTileEdgePositions ));
    }

    /**
     * Verify that the getNeighborLocations() method returns the correct value when called with
     * specifying the right neighboring map tile edge position.
     */
    @Test
    public void getNeighborLocations_rightNeighbor()
    {
        int x = GeneralHelper.getRandom().nextInt( 1000 );
        int y = GeneralHelper.getRandom().nextInt( 1000 );
        MapTileLocation mapTileLocation = new MapTileLocation( x, y );

        MapTileLocation[] neighborLocations = new MapTileLocation[]
        {
            new MapTileLocation( x + 1, y )
        };

        MapTileEdgePosition[] neighborMapTileEdgePositions = new MapTileEdgePosition[]
        {
            MapTileEdgePosition.RIGHT
        };

        Assert.assertArrayEquals(
                "Unexpected return value.", neighborLocations,
                mapTileLocation.getNeighborLocations( neighborMapTileEdgePositions ));
    }

    /**
     * Verify that the toString() method returns the correct value.
     */
    @Test
    public void toStringTest()
    {
        int x = GeneralHelper.getRandom().nextInt( 1000 );
        int y = GeneralHelper.getRandom().nextInt( 1000 );
        MapTileLocation mapTileLocation = new MapTileLocation( x, y );

        Assert.assertEquals( "Unexpected return value.", "{ " + x + ", " + y + " }",
                             mapTileLocation.toString() );
    }

    /**
     * Verify that the equals() method returns the correct result when called with a null reference.
     */
    @Test
    public void equals_null()
    {
        MapTileLocation mapTileLocation1 = new MapTileLocation( 0, 0 );
        MapTileLocation mapTileLocation2 = null;

        boolean result = mapTileLocation1.equals( mapTileLocation2 );
        Assert.assertEquals( "Unexpected result.", false, result );
    }

    /**
     * Verify that the equals() method returns the correct result when called with the wrong type of
     * object.
     */
    @Test
    public void equals_wrongType()
    {
        MapTileLocation mapTileLocation1 = new MapTileLocation( 0, 0 );
        Object          mapTileLocation2 = new Object();

        boolean result = mapTileLocation1.equals( mapTileLocation2 );
        Assert.assertEquals( "Unexpected result.", false, result );
    }

    /**
     * Verify that the equals() method returns the correct result when called with a map tile
     * location with a different X coordinate.
     */
    @Test
    public void equals_differentX()
    {
        MapTileLocation mapTileLocation1 = new MapTileLocation( 0, 0 );
        MapTileLocation mapTileLocation2 = new MapTileLocation( 1, 0 );

        boolean result = mapTileLocation1.equals( mapTileLocation2 );
        Assert.assertEquals( "Unexpected result.", false, result );
    }

    /**
     * Verify that the equals() method returns the correct result when called with a map tile
     * location with a different Y coordinate.
     */
    @Test
    public void equals_differentY()
    {
        MapTileLocation mapTileLocation1 = new MapTileLocation( 0, 0 );
        MapTileLocation mapTileLocation2 = new MapTileLocation( 0, 1 );

        boolean result = mapTileLocation1.equals( mapTileLocation2 );
        Assert.assertEquals( "Unexpected result.", false, result );
    }

    /**
     * Verify that the equals() method returns the correct result when called with an identical map
     * tile location.
     */
    @Test
    public void equals_identical()
    {
        MapTileLocation mapTileLocation1 = new MapTileLocation( 0, 0 );
        MapTileLocation mapTileLocation2 = new MapTileLocation( 0, 0 );

        boolean result = mapTileLocation1.equals( mapTileLocation2 );
        Assert.assertEquals( "Unexpected result.", true, result );
    }

    /**
     * Verify that the hashCode() method returns the correct value.
     */
    @Test
    public void hashCodeTest()
    {
        int x = GeneralHelper.getRandom().nextInt( 1000 );
        int y = GeneralHelper.getRandom().nextInt( 1000 );
        MapTileLocation mapTileLocation = new MapTileLocation( x, y );

        Assert.assertEquals( "Unexpected return value.", (( x & 65535 ) << 16 ) | ( y & 65535 ),
                             mapTileLocation.hashCode() );
    }
}
