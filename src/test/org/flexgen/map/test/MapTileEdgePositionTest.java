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

/**
 * Test class for the MapTileEdgePosition class.
 */
public class MapTileEdgePositionTest
{
    /**
     * Verify that the get() method returns the correct value for all valid index values.
     */
    @Test
    public void get_validIndex()
    {
        Assert.assertEquals( "Unexpected result for index 0.", MapTileEdgePosition.TOP,
                             MapTileEdgePosition.get( 0 ));

        Assert.assertEquals( "Unexpected result for index 1.", MapTileEdgePosition.RIGHT,
                             MapTileEdgePosition.get( 1 ));

        Assert.assertEquals( "Unexpected result for index 2.", MapTileEdgePosition.BOTTOM,
                             MapTileEdgePosition.get( 2 ));

        Assert.assertEquals( "Unexpected result for index 3.", MapTileEdgePosition.LEFT,
                             MapTileEdgePosition.get( 3 ));
    }

    /**
     * Verify that the get() method throws the correct exception when an invalid index is specified.
     */
    @Test
    public void get_invalidIndex()
    {
        try
        {
            MapTileEdgePosition.get( -1 );
            Assert.fail( "Expected exception." );
        }
        catch ( IllegalArgumentException e )
        {
            Assert.assertEquals( "Unexpected message.",
                                 "Parameter 'index' must be between 0 and 3.", e.getMessage() );
        }
    }

    /**
     * Verify that the getIndex() method returns the correct value for all map tile edge positions.
     */
    @Test
    public void getIndex()
    {
        Assert.assertEquals( "Unexpected result for TOP.", 0,
                             MapTileEdgePosition.TOP.getIndex() );
        Assert.assertEquals( "Unexpected result for RIGHT.", 1,
                             MapTileEdgePosition.RIGHT.getIndex() );
        Assert.assertEquals( "Unexpected result for BOTTOM.", 2,
                             MapTileEdgePosition.BOTTOM.getIndex() );
        Assert.assertEquals( "Unexpected result for LEFT.", 3,
                             MapTileEdgePosition.LEFT.getIndex() );
    }

    /**
     * Verify that the toString() method returns the correct value for all map tile edge positions.
     */
    @Test
    public void toStringTest()
    {
        Assert.assertEquals( "Unexpected result for TOP.", "Top",
                             MapTileEdgePosition.TOP.toString() );
        Assert.assertEquals( "Unexpected result for RIGHT.", "Right",
                             MapTileEdgePosition.RIGHT.toString() );
        Assert.assertEquals( "Unexpected result for BOTTOM.", "Bottom",
                             MapTileEdgePosition.BOTTOM.toString() );
        Assert.assertEquals( "Unexpected result for LEFT.", "Left",
                             MapTileEdgePosition.LEFT.toString() );
    }

    /**
     * Verify that the equals() method returns the correct result when called with a null reference.
     */
    @Test
    public void equals_null()
    {
        MapTileEdgePosition mapTileEdgePosition1 = MapTileEdgePosition.LEFT;
        MapTileEdgePosition mapTileEdgePosition2 = null;

        boolean result = mapTileEdgePosition1.equals( mapTileEdgePosition2 );
        Assert.assertEquals( "Unexpected result.", false, result );
    }

    /**
     * Verify that the equals() method returns the correct result when called with the wrong type of
     * object.
     */
    @Test
    public void equals_wrongType()
    {
        MapTileEdgePosition mapTileEdgePosition1 = MapTileEdgePosition.RIGHT;
        Object              mapTileEdgePosition2 = new Object();

        boolean result = mapTileEdgePosition1.equals( mapTileEdgePosition2 );
        Assert.assertEquals( "Unexpected result.", false, result );
    }

    /**
     * Verify that the equals() method returns the correct result when called with a different map
     * tile edge position.
     */
    @Test
    public void equals_differentInstance()
    {
        MapTileEdgePosition mapTileEdgePosition1 = MapTileEdgePosition.TOP;
        MapTileEdgePosition mapTileEdgePosition2 = MapTileEdgePosition.BOTTOM;

        boolean result = mapTileEdgePosition1.equals( mapTileEdgePosition2 );
        Assert.assertEquals( "Unexpected result.", false, result );
    }

    /**
     * Verify that the equals() method returns the correct result when called with an identical map
     * tile edge position.
     */
    @Test
    public void equals_identicalInstance()
    {
        MapTileEdgePosition mapTileEdgePosition1 = MapTileEdgePosition.BOTTOM;
        MapTileEdgePosition mapTileEdgePosition2 = MapTileEdgePosition.BOTTOM;

        boolean result = mapTileEdgePosition1.equals( mapTileEdgePosition2 );
        Assert.assertEquals( "Unexpected result.", true, result );
    }
}
