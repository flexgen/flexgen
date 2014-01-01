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

package org.flexgen.map.test.support;

import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;

import org.flexgen.map.BeforeMapTileRemovedListener;
import org.flexgen.map.MapGenerator;
import org.flexgen.map.MapTileLocation;

/**
 * Test class implementing the BeforeMapTileRemovedListener interface.
 */
public class TestBeforeMapTileRemovedListener implements BeforeMapTileRemovedListener
{
    /**
     * The list of map tile locations that have been removed.
     */
    private List< MapTileLocation > mapTileLocations;

    /**
     * The list of map generators that have removed map tiles.
     */
    private List< MapGenerator > mapGenerators;

    /**
     * Construct a test "before map tile removed" listener.
     */
    public TestBeforeMapTileRemovedListener()
    {
        mapTileLocations = new LinkedList< MapTileLocation >();
        mapGenerators = new LinkedList< MapGenerator >();
    }

    /**
     * Informs the listener that a map tile will be removed at the specified location.
     *
     * @param mapGenerator
     *            Map generator that will remove the map tile.
     * @param mapTileLocation
     *            Location at which the map tile will be removed.
     */
    public void beforeMapTileRemoved( MapGenerator mapGenerator, MapTileLocation mapTileLocation )
    {
        Assert.assertNotNull( "Expected map tile to not be null.",
                              mapGenerator.getMapTile( mapTileLocation ));
        mapTileLocations.add( mapTileLocation );
        mapGenerators.add( mapGenerator );
    }

    /**
     * Get the list of map tile locations that have been removed.
     *
     * @return The list of map tile locations that have been removed.
     */
    public List< MapTileLocation > getMapTileLocations()
    {
        return mapTileLocations;
    }

    /**
     * Get the list of map generators that have removed map tiles.
     *
     * @return The list of map generators that have removed map tiles.
     */
    public List< MapGenerator > getMapGenerators()
    {
        return mapGenerators;
    }
}
