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
}
