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

import org.flexgen.map.MapUnit;
import org.flexgen.test.helper.GeneralHelper;
import org.flexgen.test.helper.MapUnitHelper;

/**
 * Test class for the MapUnit class.
 */
public class MapUnitTest
{
    /**
     * Verify that the constructor throws the correct exception when the name parameter is null.
     */
    @Test
    public void constructor_null()
    {
        try
        {
            new MapUnit( null );
            Assert.fail( "Expected exception." );
        }
        catch ( IllegalArgumentException e )
        {
            Assert.assertEquals( "Unexpected message.", "Parameter 'name' cannot be null.",
                                 e.getMessage() );
        }
    }

    /**
     * Verify that the toString() method returns the correct value.
     */
    @Test
    public void toStringTest()
    {
        String name = GeneralHelper.getUniqueString();
        MapUnit mapUnit = new MapUnit( name );
        Assert.assertEquals( "Unexpected return value.", name, mapUnit.toString() );
    }

    /**
     * Verify that the equals() method returns the correct result when called with a null reference.
     */
    @Test
    public void equals_null()
    {
        MapUnit mapUnit1 = MapUnitHelper.build();
        MapUnit mapUnit2 = null;

        boolean result = mapUnit1.equals( mapUnit2 );
        Assert.assertEquals( "Unexpected result.", false, result );
    }

    /**
     * Verify that the equals() method returns the correct result when called with the wrong type of
     * object.
     */
    @Test
    public void equals_wrongType()
    {
        MapUnit mapUnit1 = MapUnitHelper.build();
        Object  mapUnit2 = new Object();

        boolean result = mapUnit1.equals( mapUnit2 );
        Assert.assertEquals( "Unexpected result.", false, result );
    }

    /**
     * Verify that the equals() method returns the correct result when called with a map unit with a
     * different name.
     */
    @Test
    public void equals_differentName()
    {
        MapUnit mapUnit1 = MapUnitHelper.build();
        MapUnit mapUnit2 = MapUnitHelper.build();

        boolean result = mapUnit1.equals( mapUnit2 );
        Assert.assertEquals( "Unexpected result.", false, result );
    }

    /**
     * Verify that the equals() method returns the correct result when called with a map unit with
     * an identical name.
     */
    @Test
    public void equals_identicalName()
    {
        String mapUnitName = GeneralHelper.getUniqueString();

        MapUnit mapUnit1 = new MapUnit( mapUnitName );
        MapUnit mapUnit2 = new MapUnit( mapUnitName );

        boolean result = mapUnit1.equals( mapUnit2 );
        Assert.assertEquals( "Unexpected result.", true, result );
    }

    /**
     * Verify that the hashCode() method returns the correct value.
     */
    @Test
    public void hashCodeTest()
    {
        String name = GeneralHelper.getUniqueString();
        MapUnit mapUnit = new MapUnit( name );
        Assert.assertEquals( "Unexpected return value.", name.hashCode(), mapUnit.hashCode() );
    }
}
