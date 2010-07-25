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
     * Verify that the allowLocation() method returns the correct value when the specified X
     * coordinate is too small.
     */
    @Test
    public void allowLocation_xTooSmall()
    {
        int value = GeneralHelper.getRandom().nextInt( 1000 );
        RectangularMapTileLocationFilter rectangularMapTileLocationFilter =
                new RectangularMapTileLocationFilter( value, 0, 5000, 0 );
        Assert.assertEquals( "Unexpected return value.", false,
                             rectangularMapTileLocationFilter.allowLocation(
                                     new MapTileLocation( value - 5, 0 )));
    }

    /**
     * Verify that the allowLocation() method returns the correct value when the specified X
     * coordinate is too large.
     */
    @Test
    public void allowLocation_xTooLarge()
    {
        int value = GeneralHelper.getRandom().nextInt( 1000 );
        RectangularMapTileLocationFilter rectangularMapTileLocationFilter =
                new RectangularMapTileLocationFilter( 0, 0, value, 0 );
        Assert.assertEquals( "Unexpected return value.", false,
                             rectangularMapTileLocationFilter.allowLocation(
                                     new MapTileLocation( value + 5, 0 )));
    }

    /**
     * Verify that the allowLocation() method returns the correct value when the specified Y
     * coordinate is too small.
     */
    @Test
    public void allowLocation_yTooSmall()
    {
        int value = GeneralHelper.getRandom().nextInt( 1000 );
        RectangularMapTileLocationFilter rectangularMapTileLocationFilter =
                new RectangularMapTileLocationFilter( 0, value, 0, 5000 );
        Assert.assertEquals( "Unexpected return value.", false,
                             rectangularMapTileLocationFilter.allowLocation(
                                     new MapTileLocation( 0, value - 5 )));
    }

    /**
     * Verify that the allowLocation() method returns the correct value when the specified Y
     * coordinate is too large.
     */
    @Test
    public void allowLocation_yTooLarge()
    {
        int value = GeneralHelper.getRandom().nextInt( 1000 );
        RectangularMapTileLocationFilter rectangularMapTileLocationFilter =
                new RectangularMapTileLocationFilter( 0, 0, 0, value );
        Assert.assertEquals( "Unexpected return value.", false,
                             rectangularMapTileLocationFilter.allowLocation(
                                     new MapTileLocation( 0, value + 5 )));
    }

    /**
     * Verify that the allowLocation() method returns the correct value when the specified
     * coordinates are within range.
     */
    @Test
    public void allowLocation_withinRange()
    {
        int x = GeneralHelper.getRandom().nextInt( 1000 );
        int y = GeneralHelper.getRandom().nextInt( 1000 );
        RectangularMapTileLocationFilter rectangularMapTileLocationFilter =
                new RectangularMapTileLocationFilter( 0, 0, 1000, 1000 );
        Assert.assertEquals( "Unexpected return value.", true,
                             rectangularMapTileLocationFilter.allowLocation(
                                     new MapTileLocation( x, y )));
    }
}
