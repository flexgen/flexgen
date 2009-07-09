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

package org.flexgen.map;

/**
 * Class representing the positions of the four edges on a map tile.
 */
public class MapTileEdgePosition
{
    /**
     * Position of the top edge of the map tile.
     */
    public static final MapTileEdgePosition TOP = new MapTileEdgePosition( "Top", 0 );

    /**
     * Position of the right edge of the map tile.
     */
    public static final MapTileEdgePosition RIGHT = new MapTileEdgePosition( "Right", 1 );

    /**
     * Position of the bottom edge of the map tile.
     */
    public static final MapTileEdgePosition BOTTOM = new MapTileEdgePosition( "Bottom", 2 );

    /**
     * Position of the left edge of the map tile.
     */
    public static final MapTileEdgePosition LEFT = new MapTileEdgePosition( "Left", 3 );

    /**
     * Name of the map tile edge position.
     */
    private final String name;

    /**
     * Index of the map tile edge position.
     */
    private final int index;

    /**
     * Construct a map tile edge position with the specified name and index.
     *
     * @param name
     *            Name of the map tile position.
     * @param index
     *            Index of the map tile edge position.
     */
    private MapTileEdgePosition( String name, int index )
    {
        this.name = name;
        this.index = index;
    }

    /**
     * Get the map tile edge position that corresponds with the specified index.
     *
     * @param index
     *            Index of the map tile edge position to get.
     *
     * @return The map tile edge position that corresponds with the specified index.
     */
    public static MapTileEdgePosition get( int index )
    {
        switch ( index )
        {
            case 0  : return MapTileEdgePosition.TOP;
            case 1  : return MapTileEdgePosition.RIGHT;
            case 2  : return MapTileEdgePosition.BOTTOM;
            case 3  : return MapTileEdgePosition.LEFT;
            default : return null;
        }
    }

    /**
     * Get the index of the map tile edge position. Used when looking up a specific map tile edge in
     * an array containing all of the map tile edges of a map tile.
     *
     * @return The index of the map tile edge position.
     */
    public int getIndex()
    {
        return index;
    }

    /**
     * Get the string representation of this object.
     *
     * @return The string representation of this object.
     */
    public String toString()
    {
        return name;
    }

    /**
     * Determines whether or not this map tile edge position is equal to another map tile edge
     * position.
     *
     * @param obj
     *            The map tile edge position against which to compare this map tile edge position.
     *
     * @return True if the two map tile edge positions are equal, false otherwise.
     */
    public boolean equals( Object obj )
    {
        if ( ! ( obj instanceof MapTileEdgePosition ))
        {
            return false;
        }

        MapTileEdgePosition mapTileEdgePosition = (MapTileEdgePosition) obj;
        return this.name.equals( mapTileEdgePosition.name );
    }

    /**
     * Returns a hash code value for the object.
     *
     * @return A hash code value for this object.
     */
    public int hashCode()
    {
        return name.hashCode();
    }
}
