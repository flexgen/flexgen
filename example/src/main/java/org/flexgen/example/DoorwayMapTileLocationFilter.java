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

package org.flexgen.example;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import org.flexgen.map.MapGenerator;
import org.flexgen.map.MapTile;
import org.flexgen.map.MapTileAddedListener;
import org.flexgen.map.MapTileEdge;
import org.flexgen.map.MapTileEdgePosition;
import org.flexgen.map.MapTileLocation;
import org.flexgen.map.MapTileLocationFilter;
import org.flexgen.map.MapTileRemovedListener;

/**
 * Class implementing logic for returning a collection of map tile locations that prevents the map
 * from growing past doorways built in newly created rooms.
 */
public class DoorwayMapTileLocationFilter
        implements MapTileLocationFilter, MapTileAddedListener, MapTileRemovedListener
{
    /**
     * Set of map tile locations excluded by this filter.
     */
    private Set< MapTileLocation > excludedMapTileLocations = new HashSet< MapTileLocation >();

    /**
     * Get the smallest possible X coordinate for allowed map tile locations.
     *
     * @return The smallest possible X coordinate for allowed map tile locations.
     */
    public int getMinX()
    {
        return 0;
    }

    /**
     * Get the smallest possible Y coordinate for allowed map tile locations.
     *
     * @return The smallest possible Y coordinate for allowed map tile locations.
     */
    public int getMinY()
    {
        return 0;
    }

    /**
     * Get the largest possible X coordinate for allowed map tile locations.
     *
     * @return The largest possible X coordinate for allowed map tile locations.
     */
    public int getMaxX()
    {
        return 0;
    }

    /**
     * Get the largest possible Y coordinate for allowed map tile locations.
     *
     * @return The largest possible Y coordinate for allowed map tile locations.
     */
    public int getMaxY()
    {
        return 0;
    }

    /**
     * Get a filtered collection of map tile locations.
     *
     * @param mapTileLocations
     *            Collection of map tile locations to filter.
     *
     * @return A filtered collection of map tile locations.
     */
    public Collection< MapTileLocation > getFilteredMapTileLocations(
            Collection< MapTileLocation > mapTileLocations )
    {
        Collection< MapTileLocation > localMapTileLocations = new LinkedList< MapTileLocation >();

        for ( MapTileLocation mapTileLocation : mapTileLocations )
        {
            if ( ! excludedMapTileLocations.contains( mapTileLocation ))
            {
                localMapTileLocations.add( mapTileLocation );
            }
        }

        return localMapTileLocations;
    }

    /**
     * Informs the listener that a map tile has been added at the specified location.
     *
     * @param mapGenerator
     *            Map generator that added the map tile.
     * @param mapTileLocation
     *            Location at which the map tile was added.
     */
    public void mapTileAdded( MapGenerator mapGenerator, MapTileLocation mapTileLocation )
    {
        for ( MapTileLocation neighborMapTileLocation : mapTileLocation.getNeighborLocations() )
        {
            checkMapTileLocation( mapGenerator, neighborMapTileLocation );
        }
    }

    /**
     * Informs the listener that a map tile has been removed at the specified location.
     *
     * @param mapGenerator
     *            Map generator that removed the map tile.
     * @param mapTileLocation
     *            Location at which the map tile was removed.
     */
    public void mapTileRemoved( MapGenerator mapGenerator, MapTileLocation mapTileLocation )
    {
        for ( MapTileLocation neighborMapTileLocation : mapTileLocation.getNeighborLocations() )
        {
            checkMapTileLocation( mapGenerator, neighborMapTileLocation );
        }
    }

    /**
     * Remove all map tile locations from the excluded map tile locations set.
     */
    public void clear()
    {
        excludedMapTileLocations.clear();
    }

    /**
     * Check a map tile location to see if it should be an excluded map tile location. Only excludes
     * a map tile location if it meets the following criteria: a map tile does not exist at that map
     * tile location, the map tile location has at least one neighboring map tile edge that is a
     * doorway, and the map tile location has no other neighboring map tile edges except for
     * doorways and walls.
     *
     * @param mapGenerator
     *            Map generator.
     * @param mapTileLocation
     *            Map tile location to check.
     */
    private void checkMapTileLocation( MapGenerator mapGenerator, MapTileLocation mapTileLocation )
    {
        // remove the map tile location from the excluded map tile locations, adding it back only if
        // all of the exclusion criteria are met
        excludedMapTileLocations.remove( mapTileLocation );

        if ( mapGenerator.getMapTile( mapTileLocation ) != null )
        {
            return;
        }

        MapTileLocation tempMapTileLocation;
        MapTile tempMapTile;

        MapTileEdge topMapTileEdge    = null;
        MapTileEdge rightMapTileEdge  = null;
        MapTileEdge bottomMapTileEdge = null;
        MapTileEdge leftMapTileEdge   = null;

        // get the top map tile edge
        tempMapTileLocation = new MapTileLocation( mapTileLocation.getX(),
                                                   mapTileLocation.getY() - 1 );
        tempMapTile = mapGenerator.getMapTile( tempMapTileLocation );

        if ( tempMapTile != null )
        {
            topMapTileEdge = tempMapTile.getMapTileEdge( MapTileEdgePosition.BOTTOM );
        }

        // get the right map tile edge
        tempMapTileLocation = new MapTileLocation( mapTileLocation.getX() + 1,
                                                   mapTileLocation.getY() );
        tempMapTile = mapGenerator.getMapTile( tempMapTileLocation );

        if ( tempMapTile != null )
        {
            rightMapTileEdge = tempMapTile.getMapTileEdge( MapTileEdgePosition.LEFT );
        }

        // get the bottom map tile edge
        tempMapTileLocation = new MapTileLocation( mapTileLocation.getX(),
                                                   mapTileLocation.getY() + 1 );
        tempMapTile = mapGenerator.getMapTile( tempMapTileLocation );

        if ( tempMapTile != null )
        {
            bottomMapTileEdge = tempMapTile.getMapTileEdge( MapTileEdgePosition.TOP );
        }

        // get the left map tile edge
        tempMapTileLocation = new MapTileLocation( mapTileLocation.getX() - 1,
                                                   mapTileLocation.getY() );
        tempMapTile = mapGenerator.getMapTile( tempMapTileLocation );

        if ( tempMapTile != null )
        {
            leftMapTileEdge = tempMapTile.getMapTileEdge( MapTileEdgePosition.RIGHT );
        }

        // check that at least one map tile edge is a doorway
        if ( ! ((( topMapTileEdge != null ) &&
                 ( topMapTileEdge.equals( DungeonExample.DOORWAY_EDGE ))) ||
                (( rightMapTileEdge != null ) &&
                 ( rightMapTileEdge.equals( DungeonExample.DOORWAY_EDGE ))) ||
                (( bottomMapTileEdge != null ) &&
                 ( bottomMapTileEdge.equals( DungeonExample.DOORWAY_EDGE ))) ||
                (( leftMapTileEdge != null ) &&
                 ( leftMapTileEdge.equals( DungeonExample.DOORWAY_EDGE )))))
        {
            return;
        }

        // check that the top map tile edge is either null, a wall, or a doorway
        if ( ! (( topMapTileEdge == null ) ||
                ( topMapTileEdge.equals( DungeonExample.WALL_EDGE )) ||
                ( topMapTileEdge.equals( DungeonExample.DOORWAY_EDGE ))))
        {
            return;
        }

        // check that the right map tile edge is either null, a wall, or a doorway
        if ( ! (( rightMapTileEdge == null ) ||
                ( rightMapTileEdge.equals( DungeonExample.WALL_EDGE )) ||
                ( rightMapTileEdge.equals( DungeonExample.DOORWAY_EDGE ))))
        {
            return;
        }

        // check that the bottom map tile edge is either null, a wall, or a doorway
        if ( ! (( bottomMapTileEdge == null ) ||
                ( bottomMapTileEdge.equals( DungeonExample.WALL_EDGE )) ||
                ( bottomMapTileEdge.equals( DungeonExample.DOORWAY_EDGE ))))
        {
            return;
        }

        // check that the left map tile edge is either null, a wall, or a doorway
        if ( ! (( leftMapTileEdge == null ) ||
                ( leftMapTileEdge.equals( DungeonExample.WALL_EDGE )) ||
                ( leftMapTileEdge.equals( DungeonExample.DOORWAY_EDGE ))))
        {
            return;
        }

        excludedMapTileLocations.add( mapTileLocation );
    }
}
