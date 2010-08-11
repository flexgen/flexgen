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

package org.flexgen.map;

/**
 * Class representing the location of a map tile in a map.
 */
public class MapTileLocation
{
    /**
     * X coordinate of the map tile location.
     */
    private final int x;

    /**
     * Y coordinate of the map tile location.
     */
    private final int y;

    /**
     * Construct a map tile location.
     *
     * @param x
     *            X coordinate of the map tile location.
     * @param y
     *            Y coordinate of the map tile location.
     */
    public MapTileLocation( int x, int y )
    {
        this.x = x;
        this.y = y;
    }

    /**
     * Get the X coordinate of the map tile location.
     *
     * @return The X coordinate of the map tile location.
     */
    public int getX()
    {
        return x;
    }

    /**
     * Get the Y coordinate of the map tile location.
     *
     * @return The Y coordinate of the map tile location.
     */
    public int getY()
    {
        return y;
    }

    /**
     * Get an array of map tile locations that are neighbors of this map tile location.
     *
     * @param neighborMapTileEdgePositions
     *            Array of map tile edge positions specifying the neighbors of this map tile
     *            location.
     *
     * @return Array of map tile locations that are neighbors of this map tile location.
     */
    public MapTileLocation[] getNeighborLocations(
            MapTileEdgePosition[] neighborMapTileEdgePositions )
    {
        MapTileLocation[] neighborLocations =
                new MapTileLocation[ neighborMapTileEdgePositions.length ];

        for ( int i = 0; i < neighborMapTileEdgePositions.length; i++ )
        {
            MapTileEdgePosition mapTileEdgePosition = neighborMapTileEdgePositions[ i ];

            if ( mapTileEdgePosition.equals( MapTileEdgePosition.TOP ))
            {
                neighborLocations[ i ] = new MapTileLocation( x, y - 1 );
            }
            if ( mapTileEdgePosition.equals( MapTileEdgePosition.RIGHT ))
            {
                neighborLocations[ i ] = new MapTileLocation( x + 1, y );
            }
            if ( mapTileEdgePosition.equals( MapTileEdgePosition.BOTTOM ))
            {
                neighborLocations[ i ] = new MapTileLocation( x, y + 1 );
            }
            if ( mapTileEdgePosition.equals( MapTileEdgePosition.LEFT ))
            {
                neighborLocations[ i ] = new MapTileLocation( x - 1, y );
            }
        }

        return neighborLocations;
    }

    /**
     * Get an array of map tile locations that are neighbors of this map tile location.
     *
     * @return Array of map tile locations that are neighbors of this map tile location.
     */
    public MapTileLocation[] getNeighborLocations()
    {
        return new MapTileLocation[]
        {
            new MapTileLocation( x + 1, y ),
            new MapTileLocation( x - 1, y ),
            new MapTileLocation( x, y + 1 ),
            new MapTileLocation( x, y - 1 )
        };
    }

    /**
     * Get the string representation of this object.
     *
     * @return The string representation of this object.
     */
    public String toString()
    {
        return "{ " + x + ", " + y + " }";
    }

    /**
     * Determines whether or not this map tile location is equal to another map tile location.
     *
     * @param obj
     *            The map tile location against which to compare this map tile location.
     *
     * @return True if the two map tile locations are equal, false otherwise.
     */
    public boolean equals( Object obj )
    {
        if ( ! ( obj instanceof MapTileLocation ))
        {
            return false;
        }

        MapTileLocation mapTileLocation = (MapTileLocation) obj;

        if ( this.x != mapTileLocation.x )
        {
            return false;
        }

        if ( this.y != mapTileLocation.y )
        {
            return false;
        }

        return true;
    }

    /**
     * Returns a hash code value for the object.
     *
     * @return A hash code value for this object.
     */
    public int hashCode()
    {
        return (( x & 65535 ) << 16 ) | ( y & 65535 );
    }
}
