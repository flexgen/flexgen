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

import java.util.Collection;
import java.util.LinkedList;
import java.util.Random;

import org.junit.Assert;
import org.junit.Test;

import org.flexgen.map.MapTileLocation;
import org.flexgen.map.RectangularMapTileLocationFilter;
import org.flexgen.test.helper.GeneralHelper;

/**
 * Test class for the RectangularMapTileLocationFilter class.
 */
public class RectangularMapTileLocationFilterTest
{
    /**
     * Verify that the constructor throws the correct exception when the maxX parameter is less than
     * the minX parameter.
     */
    @Test
    public void constructor_maxX_tooSmall()
    {
        try
        {
            new RectangularMapTileLocationFilter( 0, 0, -1, 0 );
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
            new RectangularMapTileLocationFilter( 0, 0, 0, -1 );
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
     * Verify that the getMinX() method returns the correct value.
     */
    @Test
    public void getMinX()
    {
        int value = GeneralHelper.getRandom().nextInt( 1000 );
        RectangularMapTileLocationFilter rectangularMapTileLocationFilter =
                new RectangularMapTileLocationFilter( value, 0, 5000, 5000 );
        Assert.assertEquals( "Unexpected return value.", value,
                             rectangularMapTileLocationFilter.getMinX() );
    }

    /**
     * Verify that the getMinY() method returns the correct value.
     */
    @Test
    public void getMinY()
    {
        int value = GeneralHelper.getRandom().nextInt( 1000 );
        RectangularMapTileLocationFilter rectangularMapTileLocationFilter =
                new RectangularMapTileLocationFilter( 0, value, 5000, 5000 );
        Assert.assertEquals( "Unexpected return value.", value,
                             rectangularMapTileLocationFilter.getMinY() );
    }

    /**
     * Verify that the getMaxX() method returns the correct value.
     */
    @Test
    public void getMaxX()
    {
        int value = GeneralHelper.getRandom().nextInt( 1000 );
        RectangularMapTileLocationFilter rectangularMapTileLocationFilter =
                new RectangularMapTileLocationFilter( 0, 0, value, 5000 );
        Assert.assertEquals( "Unexpected return value.", value,
                rectangularMapTileLocationFilter.getMaxX() );
    }

    /**
     * Verify that the getMaxY() method returns the correct value.
     */
    @Test
    public void getMaxY()
    {
        int value = GeneralHelper.getRandom().nextInt( 1000 );
        RectangularMapTileLocationFilter rectangularMapTileLocationFilter =
                new RectangularMapTileLocationFilter( 0, 0, 5000, value );
        Assert.assertEquals( "Unexpected return value.", value,
                             rectangularMapTileLocationFilter.getMaxY() );
    }

    /**
     * Verify that the getFilteredMapTileLocations() method correctly filters a collection of
     * locations.
     */
    @Test
    public void getFilteredMapTileLocations()
    {
        int max = 1000;
        Random random = GeneralHelper.getRandom();

        MapTileLocation smallX   = new MapTileLocation( -5, random.nextInt( max + 1 ));
        MapTileLocation smallY   = new MapTileLocation( random.nextInt( max + 1 ), -5 );
        MapTileLocation bigX     = new MapTileLocation( max + 5, random.nextInt( max + 1 ));
        MapTileLocation bigY     = new MapTileLocation( random.nextInt( max + 1 ), max + 5 );
        MapTileLocation inRange1 = new MapTileLocation( random.nextInt( max + 1 ),
                                                        random.nextInt( max + 1 ) );
        MapTileLocation inRange2 = new MapTileLocation( random.nextInt( max + 1 ),
                                                        random.nextInt( max + 1 ) );

        Collection< MapTileLocation > mapTileLocations = new LinkedList< MapTileLocation >();
        mapTileLocations.add( smallX );
        mapTileLocations.add( smallY );
        mapTileLocations.add( bigX );
        mapTileLocations.add( bigY );
        mapTileLocations.add( inRange1 );
        mapTileLocations.add( inRange2 );

        RectangularMapTileLocationFilter rectangularMapTileLocationFilter =
                new RectangularMapTileLocationFilter( 0, 0, max, max );
        Collection< MapTileLocation > filteredMapTileLocations =
                rectangularMapTileLocationFilter.getFilteredMapTileLocations( mapTileLocations );

        Assert.assertEquals( "Unexpected collection size.", 2, filteredMapTileLocations.size() );
        Assert.assertEquals( "Missing element " + inRange1.toString() + ".", true,
                             filteredMapTileLocations.contains( inRange1 ));
        Assert.assertEquals( "Missing element " + inRange2.toString() + ".", true,
                             filteredMapTileLocations.contains( inRange2 ));
    }
}
