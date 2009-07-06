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

        return null;
    }
}
