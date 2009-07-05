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
import org.flexgen.map.MapUnit;
import org.flexgen.test.helper.GeneralHelper;
import org.flexgen.test.helper.MapUnitHelper;

/**
 * Test class for the MapTile class.
 */
public class MapTileTest
{
    /**
     * Verify that the constructor throws the correct exception when the map units parameter is
     * null.
     */
    @Test
    public void constructor_null()
    {
        try
        {
            new MapTile( null );
            Assert.fail( "Expected exception." );
        }
        catch ( IllegalArgumentException e )
        {
            Assert.assertEquals( "Unexpected message.", "Parameter 'mapUnits' cannot be null.",
                                 e.getMessage() );
        }
    }

    /**
     * Verify that the constructor throws the correct exception when the map units parameter is an
     * empty array.
     */
    @Test
    public void constructor_emptyArray()
    {
        MapUnit[][] mapUnits = new MapUnit[][] {};

        try
        {
            new MapTile( mapUnits );
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
     * Verify that the constructor throws the correct exception when the map units parameter doesn't
     * contain the same number of elements in all of the rows.
     */
    @Test
    public void constructor_inconsistentRowCount()
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
            new MapTile( mapUnits );
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
     * Verify that the constructor throws the correct exception when the map units parameter doesn't
     * contain the same number of columns as it does rows.
     */
    @Test
    public void constructor_columnCountNotEqualRowCount()
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
            new MapTile( mapUnits );
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
     * Verify that the getMapUnit() method throws the correct exception when the x paramater is too
     * small.
     */
    @Test
    public void getMapUnit_xTooSmall()
    {
        MapTile mapTile = new MapTile( MapUnitHelper.buildArray( 1 ));

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
     * Verify that the getMapUnit() method throws the correct exception when the x paramater is too
     * large.
     */
    @Test
    public void getMapUnit_xTooLarge()
    {
        int size = GeneralHelper.getRandom().nextInt( 5 ) + 1;
        MapTile mapTile = new MapTile( MapUnitHelper.buildArray( size ));

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
     * Verify that the getMapUnit() method throws the correct exception when the y paramater is too
     * small.
     */
    @Test
    public void getMapUnit_yTooSmall()
    {
        MapTile mapTile = new MapTile( MapUnitHelper.buildArray( 1 ));

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
     * Verify that the getMapUnit() method throws the correct exception when the y paramater is too
     * large.
     */
    @Test
    public void getMapUnit_yTooLarge()
    {
        int size = GeneralHelper.getRandom().nextInt( 5 ) + 1;
        MapTile mapTile = new MapTile( MapUnitHelper.buildArray( size ));

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
}
