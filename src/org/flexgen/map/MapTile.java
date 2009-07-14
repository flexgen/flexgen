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
 * Class representing a map tile. Contains the type and orientation for the map tile.
 */
public class MapTile
{
    /**
     * Type of the map tile.
     */
    private final MapTileType mapTileType;

    /**
     * Orientation of the map tile.
     */
    private final MapTileOrientation mapTileOrientation;

    /**
     * Construct a map tile.
     *
     * @param mapTileType
     *            Type of the map tile. Cannot be null.
     * @param mapTileOrientation
     *            Orientation of the map tile. Cannot be null.
     */
    public MapTile( MapTileType mapTileType, MapTileOrientation mapTileOrientation )
    {
        if ( mapTileType == null )
        {
            throw new IllegalArgumentException( "Parameter 'mapTileType' cannot be null." );
        }

        if ( mapTileOrientation == null )
        {
            throw new IllegalArgumentException( "Parameter 'mapTileOrientation' cannot be null." );
        }

        this.mapTileType = mapTileType;
        this.mapTileOrientation = mapTileOrientation;
    }

    /**
     * Get the map unit at the specified coordinates in this map tile.
     *
     * @param x
     *            X coordinate of the map unit to get.
     * @param y
     *            Y coordinate of the map unit to get.
     *
     * @return The map unit at the specified coordinates in this map tile.
     */
    public MapUnit getMapUnit( int x, int y )
    {
        if ( x < 0 )
        {
            throw new IllegalArgumentException(
                    "Parameter 'x' must be greater than or equal to 0." );
        }

        if ( x >= mapTileType.getSize() )
        {
            throw new IllegalArgumentException( "Parameter 'x' must be less than " +
                                                mapTileType.getSize() + "." );
        }

        if ( y < 0 )
        {
            throw new IllegalArgumentException(
                    "Parameter 'y' must be greater than or equal to 0." );
        }

        if ( y >= mapTileType.getSize() )
        {
            throw new IllegalArgumentException( "Parameter 'y' must be less than " +
                                                mapTileType.getSize() + "." );
        }

        if ( mapTileOrientation.equals( MapTileOrientation.UPRIGHT ))
        {
            return mapTileType.getMapUnit( x, y );
        }
        else if ( mapTileOrientation.equals( MapTileOrientation.CLOCKWISE ))
        {
            return mapTileType.getMapUnit(( mapTileType.getSize() - 1 ) - y, x );
        }
        else if ( mapTileOrientation.equals( MapTileOrientation.FLIPPED ))
        {
            return mapTileType.getMapUnit(( mapTileType.getSize() - 1 ) - x,
                                          ( mapTileType.getSize() - 1 ) - y );
        }
        else if ( mapTileOrientation.equals( MapTileOrientation.COUNTER_CLOCKWISE ))
        {
            return mapTileType.getMapUnit( y, ( mapTileType.getSize() - 1 ) - x );
        }
        else
        {
            throw new IllegalStateException( "Unsupported orientation." );
        }
    }

    /**
     * Get the map tile edge at the specified position in this map tile.
     *
     * @param mapTileEdgePosition
     *            Position of the map tile edge to get.
     *
     * @return The map unit at the specified coordinates in this map tile.
     */
    public MapTileEdge getMapTileEdge( MapTileEdgePosition mapTileEdgePosition )
    {
        if ( mapTileEdgePosition == null )
        {
            throw new IllegalArgumentException( "Parameter 'mapTileEdgePosition' cannot be null." );
        }

        // get the index of the map tile edge position
        int index = mapTileEdgePosition.getIndex();

        // update the index based upon the orientation of the tile
        if ( mapTileOrientation.equals( MapTileOrientation.CLOCKWISE ))
        {
            index -= 1;
        }
        else if ( mapTileOrientation.equals( MapTileOrientation.FLIPPED ))
        {
            index -= 2;
        }
        else if ( mapTileOrientation.equals( MapTileOrientation.COUNTER_CLOCKWISE ))
        {
            index -= 3;
        }

        // take care of index values that have gone out of the valid range
        if ( index < 0 )
        {
            index += 4;
        }

        return mapTileType.getMapTileEdge( MapTileEdgePosition.get( index ));
    }

    /**
     * Get the string representation of this object.
     *
     * @return The string representation of this object.
     */
    public String toString()
    {
        return "{ " + mapTileType.toString() + ", " + mapTileOrientation.toString() + " }";
    }

    /**
     * Determines whether or not this map tile is equal to another map tile.
     *
     * @param obj
     *            The map tile against which to compare this map tile.
     *
     * @return True if the two map tiles are equal, false otherwise.
     */
    public boolean equals( Object obj )
    {
        if ( ! ( obj instanceof MapTile ))
        {
            return false;
        }

        MapTile mapTile = (MapTile) obj;

        if ( ! this.mapTileType.equals( mapTile.mapTileType ))
        {
            return false;
        }

        if ( ! this.mapTileOrientation.equals( mapTile.mapTileOrientation ))
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
        throw new UnsupportedOperationException( "Not written yet." );
    }
}
