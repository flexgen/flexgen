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

package org.flexgen.util.test.support;

import java.util.Deque;
import java.util.LinkedList;

import org.junit.Assert;

import org.flexgen.util.ImprovedRandom;

/**
 * Test class extending the ImprovedRandom class.
 */
public class TestImprovedRandom extends ImprovedRandom
{
    /**
     * Transactions to play back when asked for random numbers.
     */
    private final Deque< TestImprovedRandomTransaction > transactions;

    /**
     * Construct a test improved random number generator.
     */
    public TestImprovedRandom()
    {
        transactions = new LinkedList< TestImprovedRandomTransaction >();
    }

    /**
     * Add a transaction.
     *
     * @param transaction
     *            The transaction to add.
     */
    public void addTransaction( TestImprovedRandomTransaction transaction )
    {
        if ( transaction == null )
        {
            throw new IllegalArgumentException( "Parameter 'transaction' cannot be null." );
        }

        transactions.addLast( transaction );
    }

    /**
     * Add transactions needed for returning a long.
     *
     * @param value
     *            The value for which to generate transactions.
     */
    public void addTransaction( long value )
    {
        int high = (int) ( value >>> 32 );
        int low  = (int) ( value & 4294967295L );

        if ( low < 0 )
        {
            high++;
        }

        addTransaction( new TestImprovedRandomTransaction( 32, high ));
        addTransaction( new TestImprovedRandomTransaction( 32, low  ));
    }

    /**
     * Get flag indicating whether or not the collection of transactions is empty.
     *
     * @return True if the collection of transactions is empty, false otherwise.
     */
    public boolean isEmpty()
    {
        return transactions.isEmpty();
    }

    /**
     * Generates the next random number.
     *
     * @param bits
     *            Number of random bits to generate.
     *
     * @return The next random number.
     */
    protected int next( int bits )
    {
        if ( transactions.isEmpty() )
        {
            throw new IllegalStateException( "No transactions available." );
        }

        TestImprovedRandomTransaction transaction = transactions.removeFirst();
        Assert.assertEquals( "Unexpected bits parameter.", transaction.getBits(), bits );
        return transaction.getResult();
    }
}
