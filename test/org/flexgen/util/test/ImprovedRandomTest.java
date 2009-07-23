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
import org.flexgen.util.ImprovedRandom;
import org.flexgen.util.test.support.TestImprovedRandom;
import org.flexgen.util.test.support.TestImprovedRandomTransaction;

/**
 * Test class for the ImprovedRandom class.
 */
public class ImprovedRandomTest
{
    /**
     * Verify that the nextLong() method throws the correct exception when the n parameter is too
     * small.
     */
    @Test
    public void nextLong_tooSmall()
    {
        ImprovedRandom improvedRandom = new ImprovedRandom();

        try
        {
            improvedRandom.nextLong( -1 );
            Assert.fail( "Expected exception." );
        }
        catch ( IllegalArgumentException e )
        {
            Assert.assertEquals( "Unexpected message.", "Parameter 'n' must be greater than 0.",
                                 e.getMessage() );
        }
    }

    /**
     * Verify that the nextInt() method performs correctly.
     */
    @Test
    public void nextInt()
    {
        int value = GeneralHelper.getRandom().nextInt();

        TestImprovedRandom testImprovedRandom = new TestImprovedRandom();
        testImprovedRandom.addTransaction( new TestImprovedRandomTransaction( 32, value ));

        Assert.assertEquals( "Unexpected result for nextInt().", value,
                             testImprovedRandom.nextInt() );
        Assert.assertEquals( "Unexpected result for isEmpty().", true,
                             testImprovedRandom.isEmpty() );
    }

    /**
     * Verify that the nextLong() method performs correctly.
     */
    @Test
    public void nextLong()
    {
        long value = GeneralHelper.getRandom().nextLong();

        TestImprovedRandom testImprovedRandom = new TestImprovedRandom();
        testImprovedRandom.addTransaction( value );

        Assert.assertEquals( "Unexpected result for nextLong().", value,
                             testImprovedRandom.nextLong() );
        Assert.assertEquals( "Unexpected result for isEmpty().", true,
                             testImprovedRandom.isEmpty() );
    }
}
