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

package org.flexgen.test.helper;

import org.junit.Assert;

import org.flexgen.map.MapGenerator;
import org.flexgen.map.MapTileLocation;
import org.flexgen.map.RectangularMapTileLocationFilter;
import org.flexgen.util.ImprovedRandom;

/**
 * Test helper class for the MapGenerator class.
 */
public class MapGeneratorHelper
{
    /**
     * Build a map generator with default values.
     *
     * @return The generated map generator.
     */
    public static MapGenerator build()
    {
        return new MapGenerator( new ImprovedRandom(), MapTileTypeHelper.buildArray(),
                                 new RectangularMapTileLocationFilter( 0, 0, 0, 0 ));
    }

    /**
     * Verify that two map generators are equal.
     *
     * @param expected
     *            The expected map generator.
     * @param actual
     *            The actual map generator.
     */
    public static void assertAreEqual( MapGenerator expected, MapGenerator actual )
    {
        Assert.assertEquals( "Unexpected value for minX.", expected.getMinX(), actual.getMinX() );
        Assert.assertEquals( "Unexpected value for minY.", expected.getMinY(), actual.getMinY() );
        Assert.assertEquals( "Unexpected value for maxX.", expected.getMaxX(), actual.getMaxX() );
        Assert.assertEquals( "Unexpected value for maxY.", expected.getMaxY(), actual.getMaxY() );

        for ( int y = expected.getMinY(); y <= expected.getMaxY(); y++ )
        {
            for ( int x = expected.getMinX(); x <= expected.getMaxX(); x++ )
            {
                MapTileLocation mapTileLocation = new MapTileLocation( x, y );
                Assert.assertEquals( "Unexpected map tile at " + mapTileLocation.toString() + ".",
                                     expected.getMapTile( mapTileLocation ),
                                     actual.getMapTile( mapTileLocation ));
            }
        }
    }

    /**
     * Private constructor to keep this class from being instantiated since all methods are static.
     */
    private MapGeneratorHelper()
    {
    }
}
