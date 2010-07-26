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

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.flexgen.util.Chooser;
import org.flexgen.util.ImprovedRandom;

/**
 * Class containing logic for randomly generating a map using a specified set of map tile types.
 */
public class MapGenerator
{
    /**
     * Random number generator to use for generating the map.
     */
    private final ImprovedRandom improvedRandom;

    /**
     * Array of map tile types that define the available map tile types for randomly generating the
     * map.
     */
    private final MapTileType[] mapTileTypes;

    /**
     * Map tile location filter for open locations.
     */
    private final MapTileLocationFilter mapTileLocationFilter;

    /**
     * Data structure containing the map. Maps map tile locations to map tiles.
     */
    private final Map< MapTileLocation, MapTile > map;

    /**
     * Set of open locations on the map.
     */
    private final Set< MapTileLocation > openLocations;

    /**
     * List of listeners for the event of adding a map tile.
     */
    private final List< MapTileAddedListener > mapTileAddedListeners;

    /**
     * The size of the map unit array that defines the map tile types used by this map generator.
     */
    private final int tileSize;

    /**
     * Construct a map generator.
     *
     * @param improvedRandom
     *            Random number generator to use for generating the map. Cannot be null.
     * @param mapTileTypes
     *            Array of map tile types that define the available map tile types for randomly
     *            generating the map. Cannot be null. Must contain at least one element. No element
     *            can be null. Cannot contain two or more elements that are identical. All map tile
     *            types in the array must be the same size.
     * @param mapTileLocationFilter
     *            Map tile location filter for open locations. Cannot be null.
     */
    public MapGenerator( ImprovedRandom improvedRandom, MapTileType[] mapTileTypes,
                         MapTileLocationFilter mapTileLocationFilter )
    {
        if ( improvedRandom == null )
        {
            throw new IllegalArgumentException( "Parameter 'improvedRandom' cannot be null." );
        }

        if ( mapTileTypes == null )
        {
            throw new IllegalArgumentException( "Parameter 'mapTileTypes' cannot be null." );
        }

        if ( mapTileTypes.length == 0 )
        {
            throw new IllegalArgumentException(
                    "Parameter 'mapTileTypes' must contain at least one element." );
        }

        int tileSize = 0;

        for ( int i = 0; i < mapTileTypes.length; i++ )
        {
            // check for a null element
            if ( mapTileTypes[ i ] == null )
            {
                throw new IllegalArgumentException(
                        "Parameter 'mapTileTypes' must not contain any null elements." );
            }

            // check for a duplicate element
            for ( int j = i + 1; j < mapTileTypes.length; j++ )
            {
                if ( mapTileTypes[ i ].equals( mapTileTypes[ j ] ))
                {
                    throw new IllegalArgumentException(
                            "Parameter 'mapTileTypes' must not contain any duplicate elements." );
                }
            }

            // check to see that all elements are the same size
            if ( tileSize == 0 )
            {
                tileSize = mapTileTypes[ i ].getSize();
            }
            else
            {
                if ( tileSize != mapTileTypes[ i ].getSize() )
                {
                    throw new IllegalArgumentException( "All map tile types in parameter " +
                                                        "'mapTileTypes' must be the same size." );
                }
            }
        }

        if ( mapTileLocationFilter == null )
        {
            throw new IllegalArgumentException(
                    "Parameter 'mapTileLocationFilter' cannot be null." );
        }

        this.improvedRandom        = improvedRandom;
        this.mapTileTypes          = mapTileTypes;
        this.mapTileLocationFilter = mapTileLocationFilter;
        this.map                   = new HashMap< MapTileLocation, MapTile >();
        this.openLocations         = new HashSet< MapTileLocation >();
        this.mapTileAddedListeners = new LinkedList< MapTileAddedListener >();
        this.tileSize              = tileSize;
    }

    /**
     * Construct a map generator.
     *
     * @param improvedRandom
     *            Random number generator to use for generating the map. Cannot be null.
     * @param mapTileTypes
     *            Array of map tile types that define the available map tile types for randomly
     *            generating the map. Cannot be null. Must contain at least one element. No element
     *            can be null. Cannot contain two or more elements that are identical. All map tile
     *            types in the array must be the same size.
     * @param minX
     *            Smallest possible X coordinate for map tiles in the map.
     * @param minY
     *            Smallest possible Y coordinate for map tiles in the map.
     * @param maxX
     *            Largest possible X coordinate for map tiles in the map. Must be greater than or
     *            equal to minX.
     * @param maxY
     *            Largest possible Y coordinate for map tiles in the map. Must be greater than or
     *            equal to minY.
     */
    public MapGenerator( ImprovedRandom improvedRandom, MapTileType[] mapTileTypes,
                         int minX, int minY, int maxX, int maxY )
    {
        this( improvedRandom, mapTileTypes,
              new RectangularMapTileLocationFilter( minX, minY, maxX, maxY ));
    }

    /**
     * Get the size of the map unit array that defines the map tile types used by this map
     * generator.
     *
     * @return The size of the map unit array that defines the map tile types used by this map
     *         generator.
     */
    public int getTileSize()
    {
        return tileSize;
    }

    /**
     * Get the smallest possible X coordinate for map tiles in the map.
     *
     * @return The smallest possible X coordinate for map tiles in the map.
     */
    public int getMinX()
    {
        int minX = mapTileLocationFilter.getMinX();

        for ( MapTileLocation mapTileLocation : map.keySet() )
        {
            if ( mapTileLocation.getX() < minX )
            {
                minX = mapTileLocation.getX();
            }
        }

        return minX;
    }

    /**
     * Get the smallest possible Y coordinate for map tiles in the map.
     *
     * @return The smallest possible Y coordinate for map tiles in the map.
     */
    public int getMinY()
    {
        int minY = mapTileLocationFilter.getMinY();

        for ( MapTileLocation mapTileLocation : map.keySet() )
        {
            if ( mapTileLocation.getY() < minY )
            {
                minY = mapTileLocation.getY();
            }
        }

        return minY;
    }

    /**
     * Get the largest possible X coordinate for map tiles in the map.
     *
     * @return The largest possible X coordinate for map tiles in the map.
     */
    public int getMaxX()
    {
        int maxX = mapTileLocationFilter.getMaxX();

        for ( MapTileLocation mapTileLocation : map.keySet() )
        {
            if ( mapTileLocation.getX() > maxX )
            {
                maxX = mapTileLocation.getX();
            }
        }

        return maxX;
    }

    /**
     * Get the largest possible Y coordinate for map tiles in the map.
     *
     * @return The largest possible Y coordinate for map tiles in the map.
     */
    public int getMaxY()
    {
        int maxY = mapTileLocationFilter.getMaxY();

        for ( MapTileLocation mapTileLocation : map.keySet() )
        {
            if ( mapTileLocation.getY() > maxY )
            {
                maxY = mapTileLocation.getY();
            }
        }

        return maxY;
    }

    /**
     * Add a new listener for the event of adding a map tile to the map.
     *
     * @param mapTileAddedListener
     *            The listener to add.
     */
    public void addMapTileAddedListener( MapTileAddedListener mapTileAddedListener )
    {
        mapTileAddedListeners.add( mapTileAddedListener );
    }

    /**
     * Get the map tile at a specified location.
     *
     * @param mapTileLocation
     *            Location for which to get the map tile.
     *
     * @return The map tile at a specified location.
     */
    public MapTile getMapTile( MapTileLocation mapTileLocation )
    {
        if ( mapTileLocation == null )
        {
            throw new IllegalArgumentException( "Parameter 'mapTileLocation' cannot be null." );
        }

        return map.get( mapTileLocation );
    }

    /**
     * Add a map tile to the map at the specified location.
     *
     * @param mapTileLocation
     *            Location at which to add the map tile.
     * @param mapTile
     *            Map tile to add.
     */
    public void addMapTile( MapTileLocation mapTileLocation, MapTile mapTile )
    {
        if ( mapTileLocation == null )
        {
            throw new IllegalArgumentException( "Parameter 'mapTileLocation' cannot be null." );
        }

        openLocations.remove( mapTileLocation );
        map.put( mapTileLocation, mapTile );

        for ( MapTileLocation neighborLocation : mapTileLocation.getNeighborLocations() )
        {
            if ( ! map.containsKey( neighborLocation ))
            {
                openLocations.add( neighborLocation );
            }
        }

        for ( MapTileAddedListener mapTileAddedListener : mapTileAddedListeners )
        {
            mapTileAddedListener.mapTileAdded( this, mapTileLocation );
        }
    }

    /**
     * Generate the map.
     */
    public void generate()
    {
        Collection< MapTileLocation > filteredOpenLocations =
                mapTileLocationFilter.getFilteredMapTileLocations( openLocations );

        while ( ! filteredOpenLocations.isEmpty() )
        {
            Chooser< MapTileType > mapTileTypeChooser =
                    new Chooser< MapTileType >( improvedRandom );

            // determine what map tile types can be added to the map
            for ( MapTileType mapTileType : mapTileTypes )
            {
                if ( legalMapTileType( mapTileType, filteredOpenLocations ))
                {
                    mapTileTypeChooser.addOption( mapTileType, mapTileType.getWeight() );
                }
            }

            // ensure that at least one map tile type can be added to the map
            if ( ! mapTileTypeChooser.optionsAvailable() )
            {
                throw new IllegalStateException( "No map tile types can be placed on the map." );
            }

            // randomly pick a map tile type
            MapTileType mapTileType = mapTileTypeChooser.choose();

            Chooser< MapTilePosition > mapTilePositionChooser =
                    new Chooser< MapTilePosition >( improvedRandom );

            // determine at what map tile positions the selected map tile type can be added
            for ( MapTileLocation mapTileLocation : filteredOpenLocations )
            {
                for ( MapTileOrientation mapTileOrientation :
                        mapTileType.getDistinctMapTileOrientations() )
                {
                    MapTilePosition mapTilePosition =
                            new MapTilePosition( mapTileLocation, mapTileOrientation );

                    if ( legalMapTilePlacement( mapTileType, mapTilePosition ))
                    {
                        mapTilePositionChooser.addOption( mapTilePosition, 1 );
                    }
                }
            }

            // randomly pick a map tile position
            MapTilePosition mapTilePosition = mapTilePositionChooser.choose();

            // add the selected map tile type at the selected map tile position
            addMapTile( mapTilePosition.getMapTileLocation(),
                        new MapTile( mapTileType, mapTilePosition.getMapTileOrientation() ));

            filteredOpenLocations =
                    mapTileLocationFilter.getFilteredMapTileLocations( openLocations );
        }
    }

    /**
     * Determine whether or not a specified map tile tile can be legally added to the map.
     *
     * @param mapTileType
     *            Map tile type to check.
     * @param filteredOpenLocations
     *            Set of open locations filtered by the map tile location filter.
     *
     * @return True if the map tile type can be legally added to the map, false otherwise.
     */
    private boolean legalMapTileType( MapTileType mapTileType,
                                      Collection< MapTileLocation > filteredOpenLocations )
    {
        for ( MapTileLocation mapTileLocation : filteredOpenLocations )
        {
            for ( MapTileOrientation mapTileOrientation :
                    mapTileType.getDistinctMapTileOrientations() )
            {
                MapTilePosition mapTilePosition =
                        new MapTilePosition( mapTileLocation, mapTileOrientation );

                if ( legalMapTilePlacement( mapTileType, mapTilePosition ))
                {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Determine whether or not a specified map tile tile can be legally added to the map at the
     * specified position.
     *
     * @param mapTileType
     *            Map tile type to check.
     * @param mapTilePosition
     *            Map tile position to check.
     *
     * @return True if the map tile type can be legally added to the map at the specified position,
     *         false otherwise.
     */
    private boolean legalMapTilePlacement( MapTileType mapTileType,
                                           MapTilePosition mapTilePosition )
    {
        MapTile mapTile = new MapTile( mapTileType, mapTilePosition.getMapTileOrientation() );
        MapTileLocation mapTileLocation = mapTilePosition.getMapTileLocation();
        MapTileLocation neighborLocation;
        MapTile neighbor;

        neighborLocation =
                new MapTileLocation( mapTileLocation.getX(), mapTileLocation.getY() - 1 );
        neighbor = map.get( neighborLocation );

        if (( neighbor != null ) &&
            ( ! neighbor.getMapTileEdge( MapTileEdgePosition.BOTTOM ).equals(
                        mapTile.getMapTileEdge( MapTileEdgePosition.TOP ))))
        {
            return false;
        }

        neighborLocation =
                new MapTileLocation( mapTileLocation.getX(), mapTileLocation.getY() + 1 );
        neighbor = map.get( neighborLocation );

        if (( neighbor != null ) &&
            ( ! neighbor.getMapTileEdge( MapTileEdgePosition.TOP ).equals(
                        mapTile.getMapTileEdge( MapTileEdgePosition.BOTTOM ))))
        {
            return false;
        }

        neighborLocation =
                new MapTileLocation( mapTileLocation.getX() - 1, mapTileLocation.getY() );
        neighbor = map.get( neighborLocation );

        if (( neighbor != null ) &&
            ( ! neighbor.getMapTileEdge( MapTileEdgePosition.RIGHT ).equals(
                        mapTile.getMapTileEdge( MapTileEdgePosition.LEFT ))))
        {
            return false;
        }

        neighborLocation =
                new MapTileLocation( mapTileLocation.getX() + 1, mapTileLocation.getY() );
        neighbor = map.get( neighborLocation );

        if (( neighbor != null ) &&
            ( ! neighbor.getMapTileEdge( MapTileEdgePosition.LEFT ).equals(
                        mapTile.getMapTileEdge( MapTileEdgePosition.RIGHT ))))
        {
            return false;
        }

        return true;
    }
}
