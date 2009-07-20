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

package org.flexgen.util.test;

import org.junit.Assert;
import org.junit.Test;

import org.flexgen.test.helper.GeneralHelper;
import org.flexgen.util.Option;

/**
 * Test class for the Option class.
 */
public class OptionTest
{
    /**
     * Verify that the constructor throws the correct exception when the option parameter is null.
     */
    @Test
    public void constructor_option_null()
    {
        try
        {
            new Option< Object >( null, 0, 0 );
            Assert.fail( "Expected exception." );
        }
        catch ( IllegalArgumentException e )
        {
            Assert.assertEquals( "Unexpected message.", "Parameter 'option' cannot be null.",
                                 e.getMessage() );
        }
    }

    /**
     * Verify that the constructor throws the correct exception when the startRange parameter is
     * less than 0.
     */
    @Test
    public void constructor_startRange_tooSmall()
    {
        try
        {
            new Option< Object >( new Object(), -1, 0 );
            Assert.fail( "Expected exception." );
        }
        catch ( IllegalArgumentException e )
        {
            Assert.assertEquals( "Unexpected message.",
                                 "Parameter 'startRange' must be greater than or equal to 0.",
                                 e.getMessage() );
        }
    }

    /**
     * Verify that the constructor throws the correct exception when the endRange parameter is less
     * than the startRange parameter.
     */
    @Test
    public void constructor_endRange_tooSmall()
    {
        int value = GeneralHelper.getRandom().nextInt( 1000 );

        try
        {
            new Option< Object >( new Object(), value, value - 1 );
            Assert.fail( "Expected exception." );
        }
        catch ( IllegalArgumentException e )
        {
            Assert.assertEquals(
                    "Unexpected message.",
                    "Parameter 'endRange' must be greater than or equal to startRange.",
                    e.getMessage() );
        }
    }

    /**
     * Verify that the getOption() method returns the correct value.
     */
    @Test
    public void getOption()
    {
        String value = GeneralHelper.getUniqueString();
        Option< String > option = new Option< String >( value, 0, 0 );
        Assert.assertEquals( "Unexpected result.", value, option.getOption() );
    }
}
