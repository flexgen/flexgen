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
 * Class representing types of map tiles that can be used in maps. Each map tile type contains a
 * two-dimensional array of map units that define the map tile type and an array of map tile edges
 * that define the four edges of the map tile.
 */
public class MapTileType
{
    /**
     * Name of the map tile type.
     */
    private final String name;

    /**
     * The weight value used to determine the probability of selecting this tile type when
     * generating a map.
     */
    private final int weight;

    /**
     * Two-dimensional array of map units that define the map tile type.
     */
    private final MapUnit[][] mapUnits;

    /**
     * Array of map tile edges that define the four edges of this map tile type.
     */
    private final MapTileEdge[] mapTileEdges;

    /**
     * Array of map tile orientations that specify the distinct orientations that are possible for
     * this map tile type.
     */
    private final MapTileOrientation[] distinctMapTileOrientations;

    /**
     * Construct a map tile type.
     *
     * @param name
     *            Name of the map tile type. Cannot be null.
     * @param weight
     *            The weight value used to determine the probability of selecting this tile type
     *            when generating a map. Larger values make this tile type more likely to be
     *            selected. The probability of selecting this tile type is computed by taking the
     *            weight of this tile type and dividing it by the sum of the weight values for all
     *            of the tile types. Cannot be negative.
     * @param mapUnits
     *            Two-dimensional array of map units that define the map tile type. Cannot be null.
     *            Must contain at least one row. Each row must contain the same number of elements.
     *            Must contain the same number of columns as it does rows. No element can be null.
     * @param mapTileEdges
     *            Array of map tile edges that define the four edges of this map tile type. Cannot
     *            be null. Must contain four elements. No element can be null. The first element is
     *            the top edge of the tile. The second element is the right edge of the tile. The
     *            third element is the bottom edge of the tile. The fourth element is the left edge
     *            of the tile.
     * @param distinctMapTileOrientations
     *            Array of map tile orientations that specify the distinct orientations that are
     *            possible for this map tile type. Cannot be null. Must contain at lease one
     *            element. No element can be null. Cannot contain two or more elements that are
     *            identical.
     * @param openMapTileEdgePositions
     *            Array of map tile edge positions that specify the open edges for this map tile
     *            type. Cannot be null. Must contain at lease one element. No element can be null.
     *            Cannot contain two or more elements that are identical.
     */
    public MapTileType( String name, int weight, MapUnit[][] mapUnits, MapTileEdge[] mapTileEdges,
                        MapTileOrientation[] distinctMapTileOrientations,
                        MapTileEdgePosition[] openMapTileEdgePositions )
    {
        if ( name == null )
        {
            throw new IllegalArgumentException( "Parameter 'name' cannot be null." );
        }

        if ( weight < 0 )
        {
            throw new IllegalArgumentException( "Parameter 'weight' cannot be less than 0." );
        }

        if ( mapUnits == null )
        {
            throw new IllegalArgumentException( "Parameter 'mapUnits' cannot be null." );
        }

        if ( mapUnits.length == 0 )
        {
            throw new IllegalArgumentException(
                    "Parameter 'mapUnits' must contain at least one element." );
        }

        int columnCount = mapUnits[ 0 ].length;

        for ( MapUnit[] row : mapUnits )
        {
            if ( row.length != columnCount )
            {
                throw new IllegalArgumentException( "Parameter 'mapUnits' must contain the same " +
                                                    "number of elements in each row." );
            }
        }

        int rowCount = mapUnits.length;

        if ( columnCount != rowCount )
        {
            throw new IllegalArgumentException( "Parameter 'mapUnits' must contain the same " +
                                                "number of columns as it does rows." );
        }

        for ( int i = 0; i < mapUnits.length; i++ )
        {
            for ( int j = 0; j < mapUnits.length; j++ )
            {
                if ( mapUnits[ i ][ j ] == null )
                {
                    throw new IllegalArgumentException(
                            "Parameter 'mapUnits' must not contain any null elements." );
                }
            }
        }

        if ( mapTileEdges == null )
        {
            throw new IllegalArgumentException( "Parameter 'mapTileEdges' cannot be null." );
        }

        if ( mapTileEdges.length != 4 )
        {
            throw new IllegalArgumentException(
                    "Parameter 'mapTileEdges' must contain 4 elements." );
        }

        for ( int i = 0; i < mapTileEdges.length; i++ )
        {
            if ( mapTileEdges[ i ] == null )
            {
                throw new IllegalArgumentException(
                        "Parameter 'mapTileEdges' must not contain any null elements." );
            }
        }

        if ( distinctMapTileOrientations == null )
        {
            throw new IllegalArgumentException(
                    "Parameter 'distinctMapTileOrientations' cannot be null." );
        }

        if ( distinctMapTileOrientations.length == 0 )
        {
            throw new IllegalArgumentException(
                    "Parameter 'distinctMapTileOrientations' must contain at least one element." );
        }

        for ( int i = 0; i < distinctMapTileOrientations.length; i++ )
        {
            // check for a null element
            if ( distinctMapTileOrientations[ i ] == null )
            {
                throw new IllegalArgumentException( "Parameter 'distinctMapTileOrientations' " +
                                                    "must not contain any null elements." );
            }

            // check for a duplicate element
            for ( int j = i + 1; j < distinctMapTileOrientations.length; j++ )
            {
                if ( distinctMapTileOrientations[ i ].equals( distinctMapTileOrientations[ j ] ))
                {
                    throw new IllegalArgumentException(
                            "Parameter 'distinctMapTileOrientations' must not contain any " +
                            "duplicate elements." );
                }
            }
        }

        if ( openMapTileEdgePositions == null )
        {
            throw new IllegalArgumentException(
                    "Parameter 'openMapTileEdgePositions' cannot be null." );
        }

        if ( openMapTileEdgePositions.length == 0 )
        {
            throw new IllegalArgumentException(
                    "Parameter 'openMapTileEdgePositions' must contain at least one element." );
        }

        this.name                        = name;
        this.weight                      = weight;
        this.mapUnits                    = mapUnits;
        this.mapTileEdges                = mapTileEdges;
        this.distinctMapTileOrientations = distinctMapTileOrientations;
    }

    /**
     * Construct a map tile type.
     *
     * @param name
     *            Name of the map tile type. Cannot be null.
     * @param weight
     *            The weight value used to determine the probability of selecting this tile type
     *            when generating a map. Larger values make this tile type more likely to be
     *            selected. The probability of selecting this tile type is computed by taking the
     *            weight of this tile type and dividing it by the sum of the weight values for all
     *            of the tile types. Cannot be negative.
     * @param mapUnits
     *            Two-dimensional array of map units that define the map tile type. Cannot be null.
     *            Must contain at least one row. Each row must contain the same number of elements.
     *            Must contain the same number of columns as it does rows. No element can be null.
     * @param mapTileEdges
     *            Array of map tile edges that define the four edges of this map tile type. Cannot
     *            be null. Must contain four elements. No element can be null. The first element is
     *            the top edge of the tile. The second element is the right edge of the tile. The
     *            third element is the bottom edge of the tile. The fourth element is the left edge
     *            of the tile.
     * @param distinctMapTileOrientations
     *            Array of map tile orientations that specify the distinct orientations that are
     *            possible for this map tile type. Cannot be null. Must contain at lease one
     *            element. No element can be null. Cannot contain two or more elements that are
     *            identical.
     */
    public MapTileType( String name, int weight, MapUnit[][] mapUnits, MapTileEdge[] mapTileEdges,
                        MapTileOrientation[] distinctMapTileOrientations )
    {
        this( name, weight, mapUnits, mapTileEdges, distinctMapTileOrientations,
              new MapTileEdgePosition[]
              {
                  MapTileEdgePosition.TOP,
                  MapTileEdgePosition.RIGHT,
                  MapTileEdgePosition.BOTTOM,
                  MapTileEdgePosition.LEFT,
              } );
    }

    /**
     * Get the weight value used to determine the probability of selecting this tile type when
     * generating a map.
     *
     * @return The weight value used to determine the probability of selecting this tile type when
     *         generating a map.
     */
    public int getWeight()
    {
        return weight;
    }

    /**
     * Get the size of the map unit array that defines this map tile type.
     *
     * @return The size of the map unit array that defines this map tile type.
     */
    public int getSize()
    {
        return mapUnits.length;
    }

    /**
     * Get the map unit at the specified coordinates in this map tile type.
     *
     * @param x
     *            X coordinate of the map unit to get. Must be greater than or equal to 0. Must be
     *            less than the size of the array of map units defining this map tile type.
     * @param y
     *            Y coordinate of the map unit to get. Must be greater than or equal to 0. Must be
     *            less than the size of the array of map units defining this map tile type.
     *
     * @return The map unit at the specified coordinates in this map tile type.
     */
    public MapUnit getMapUnit( int x, int y )
    {
        if ( x < 0 )
        {
            throw new IllegalArgumentException(
                    "Parameter 'x' must be greater than or equal to 0." );
        }

        if ( x >= mapUnits.length )
        {
            throw new IllegalArgumentException( "Parameter 'x' must be less than " +
                                                mapUnits.length + "." );
        }

        if ( y < 0 )
        {
            throw new IllegalArgumentException(
                    "Parameter 'y' must be greater than or equal to 0." );
        }

        if ( y >= mapUnits.length )
        {
            throw new IllegalArgumentException( "Parameter 'y' must be less than " +
                                                mapUnits.length + "." );
        }

        // NOTE: The X and Y coordinates are reversed when accessing the map units array. This is
        //       done because the obvious approach to initializing a two-dimensional array leads to
        //       an array that indexes rows using the first index and columns using the second
        //       index.
        return mapUnits[ y ][ x ];
    }

    /**
     * Get the map tile edge at the specified position in this map tile type.
     *
     * @param mapTileEdgePosition
     *            Position of the map tile edge to get.
     *
     * @return The map unit at the specified coordinates in this map tile type.
     */
    public MapTileEdge getMapTileEdge( MapTileEdgePosition mapTileEdgePosition )
    {
        if ( mapTileEdgePosition == null )
        {
            throw new IllegalArgumentException( "Parameter 'mapTileEdgePosition' cannot be null." );
        }

        return mapTileEdges[ mapTileEdgePosition.getIndex() ];
    }

    /**
     * Get the array of map tile orientations that specify the distinct orientations that are
     * possible for this map tile type.
     *
     * @return The array of map tile orientations that specify the distinct orientations that are
     *         possible for this map tile type.
     */
    public MapTileOrientation[] getDistinctMapTileOrientations()
    {
        return distinctMapTileOrientations;
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
     * Determines whether or not this map tile type is equal to another map tile type.
     *
     * @param obj
     *            The map tile type against which to compare this map tile type.
     *
     * @return True if the two map tile types are equal, false otherwise.
     */
    public boolean equals( Object obj )
    {
        if ( ! ( obj instanceof MapTileType ))
        {
            return false;
        }

        MapTileType mapTileType = (MapTileType) obj;
        return this.name.equals( mapTileType.name );
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
