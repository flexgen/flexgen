/*

FlexGen : Flexible Map Generator Library

Copyright (C) 2009-2013 Jeffrey J. Weston <jjweston@gmail.com>
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
import org.flexgen.util.Chooser;
import org.flexgen.util.ImprovedRandom;
import org.flexgen.util.test.support.BrokenImprovedRandom;
import org.flexgen.util.test.support.TestImprovedRandom;

/**
 * Test class for the Chooser class.
 */
public class ChooserTest
{
    /**
     * Verify that the constructor throws the correct exception when the improvedRandom parameter is
     * null.
     */
    @Test
    public void constructor_improvedRandom_null()
    {
        try
        {
            new Chooser< Object >( null );
            Assert.fail( "Expected exception." );
        }
        catch ( IllegalArgumentException e )
        {
            Assert.assertEquals( "Unexpected message.",
                                 "Parameter 'improvedRandom' cannot be null.", e.getMessage() );
        }
    }

    /**
     * Verify that the addOption() method throws the correct exception when the option parameter is
     * null.
     */
    @Test
    public void addOption_option_null()
    {
        Chooser< Object > chooser = new Chooser< Object >( new ImprovedRandom() );

        try
        {
            chooser.addOption( null, 0 );
            Assert.fail( "Expected exception." );
        }
        catch ( IllegalArgumentException e )
        {
            Assert.assertEquals( "Unexpected message.", "Parameter 'option' cannot be null.",
                                 e.getMessage() );
        }
    }

    /**
     * Verify that the addOption() method throws the correct exception when the weight parameter is
     * less than 0.
     */
    @Test
    public void addOption_weight_tooSmall()
    {
        Chooser< Object > chooser = new Chooser< Object >( new ImprovedRandom() );

        try
        {
            chooser.addOption( new Object(), -1 );
            Assert.fail( "Expected exception." );
        }
        catch ( IllegalArgumentException e )
        {
            Assert.assertEquals( "Unexpected message.",
                                 "Parameter 'weight' must be greater than or equal to 0.",
                                 e.getMessage() );
        }
    }

    /**
     * Verify that the choose() method throws the correct exception when no options are specified.
     */
    @Test
    public void choose_noOptions()
    {
        Chooser< Object > chooser = new Chooser< Object >( new ImprovedRandom() );

        try
        {
            chooser.choose();
            Assert.fail( "Expected exception." );
        }
        catch ( IllegalStateException e )
        {
            Assert.assertEquals( "Unexpected message.",
                                 "No options with any weight were added.",
                                 e.getMessage() );
        }
    }

    /**
     * Verify that the choose() method throws the correct exception when encountering an illegal
     * state.
     */
    @Test
    public void choose_illegalState()
    {
        int weight = GeneralHelper.getRandom().nextInt( 1000 ) + 1;
        String option = GeneralHelper.getUniqueString();

        Chooser< Object > chooser = new Chooser< Object >( new BrokenImprovedRandom() );
        chooser.addOption( option, weight );

        try
        {
            chooser.choose();
            Assert.fail( "Expected exception." );
        }
        catch ( IllegalStateException e )
        {
            Assert.assertEquals( "Unexpected message.", "Illegal state.", e.getMessage() );
        }
    }

    /**
     * Verify that the choose() method returns the correct value when only one option is specified.
     */
    @Test
    public void choose_singleOption()
    {
        int weight = GeneralHelper.getRandom().nextInt( 1000 ) + 1;
        int value = GeneralHelper.getRandom().nextInt( weight );
        String option = GeneralHelper.getUniqueString();

        TestImprovedRandom testImprovedRandom = new TestImprovedRandom();
        testImprovedRandom.addTransaction( value );

        Chooser< String > chooser = new Chooser< String >( testImprovedRandom );
        chooser.addOption( option, weight );

        Assert.assertEquals( "Unexpected result.", option, chooser.choose() );
        Assert.assertEquals( "Unexpected result for isEmpty().", true,
                             testImprovedRandom.isEmpty() );
    }

    /**
     * Verify that the choose() method returns the correct value when multiple options are
     * specified.
     */
    @Test
    public void choose_multipleOptions()
    {
        int optionCount = 100;
        int weight = 10;
        String[] options = new String[ optionCount ];

        for ( int i = 0; i < optionCount; i++ )
        {
            options[ i ] = GeneralHelper.getUniqueString();
        }

        int value = GeneralHelper.getRandom().nextInt( optionCount * weight );
        String chosenOption = options[ value / weight ];

        TestImprovedRandom testImprovedRandom = new TestImprovedRandom();
        testImprovedRandom.addTransaction( value );

        Chooser< String > chooser = new Chooser< String >( testImprovedRandom );

        for ( int i = 0; i < optionCount; i++ )
        {
            chooser.addOption( options[ i ], weight );
        }

        Assert.assertEquals( "Unexpected result.", chosenOption, chooser.choose() );
        Assert.assertEquals( "Unexpected result for isEmpty().", true,
                             testImprovedRandom.isEmpty() );
    }

    /**
     * Verify that the optionsAvailable() returns the correct value when options are available.
     */
    @Test
    public void optionsAvailable_true()
    {
        Chooser< Object > chooser = new Chooser< Object >( new ImprovedRandom() );
        chooser.addOption( new Object(), 1 );
        Assert.assertEquals( "Unexpected result.", true, chooser.optionsAvailable() );
    }

    /**
     * Verify that the optionsAvailable() returns the correct value when no options are available.
     */
    @Test
    public void optionsAvailable_false()
    {
        Chooser< Object > chooser = new Chooser< Object >( new ImprovedRandom() );
        chooser.addOption( new Object(), 0 );
        Assert.assertEquals( "Unexpected result.", false, chooser.optionsAvailable() );
    }
}
