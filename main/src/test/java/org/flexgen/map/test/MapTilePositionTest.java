/*

FlexGen : Flexible Map Generator Library

Copyright (C) 2009-2014 Jeffrey J. Weston <jjweston@gmail.com>
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

import org.flexgen.map.MapTileLocation;
import org.flexgen.map.MapTileOrientation;
import org.flexgen.map.MapTilePosition;
import org.flexgen.test.helper.MapTileLocationHelper;
import org.flexgen.test.helper.MapTileOrientationHelper;

/**
 * Test class for the MapTilePosition class.
 */
public class MapTilePositionTest
{
    /**
     * Verify that the constructor throws the correct exception when the mapTileLocation parameter
     * is null.
     */
    @Test
    public void constructor_mapTileLocation_null()
    {
        try
        {
            new MapTilePosition( null, MapTileOrientationHelper.getRandomOrientation() );
            Assert.fail( "Expected exception." );
        }
        catch ( IllegalArgumentException e )
        {
            Assert.assertEquals( "Unexpected message.",
                                 "Parameter 'mapTileLocation' cannot be null.", e.getMessage() );
        }
    }

    /**
     * Verify that the constructor throws the correct exception when the mapTileOrientation
     * parameter is null.
     */
    @Test
    public void constructor_mapTileOrientation_null()
    {
        try
        {
            new MapTilePosition( MapTileLocationHelper.build(), null );
            Assert.fail( "Expected exception." );
        }
        catch ( IllegalArgumentException e )
        {
            Assert.assertEquals( "Unexpected message.",
                                 "Parameter 'mapTileOrientation' cannot be null.", e.getMessage() );
        }
    }

    /**
     * Verify that the getMapTileLocation() method returns the correct value.
     */
    @Test
    public void getMapTileLocation()
    {
        MapTileLocation mapTileLocation = MapTileLocationHelper.build();
        MapTilePosition mapTilePosition = new MapTilePosition(
                mapTileLocation, MapTileOrientationHelper.getRandomOrientation() );
        Assert.assertEquals( "Unexpected return value.", mapTileLocation,
                             mapTilePosition.getMapTileLocation() );
    }

    /**
     * Verify that the getMapTileOrientation() method returns the correct value.
     */
    @Test
    public void getMapTileOrientation()
    {
        MapTileOrientation mapTileOrientation = MapTileOrientationHelper.getRandomOrientation();
        MapTilePosition mapTilePosition = new MapTilePosition( MapTileLocationHelper.build(),
                                                               mapTileOrientation );
        Assert.assertEquals( "Unexpected return value.", mapTileOrientation,
                             mapTilePosition.getMapTileOrientation() );
    }
}
