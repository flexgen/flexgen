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

package org.flexgen.map;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
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
     * Data structure tracking when each map tile location was added to the map.
     */
    private final Map< MapTileLocation, Integer > mapAge;

    /**
     * Set of open locations on the map.
     */
    private final Set< MapTileLocation > openLocations;

    /**
     * Map tiles that are excluded for various locations in the map.
     */
    private final Map< MapTileLocation, Collection< MapTile >> excludedMapTilesMap;

    /**
     * List of "before map tile added" listeners.
     */
    private final List< BeforeMapTileAddedListener > beforeMapTileAddedListeners;

    /**
     * List of "before map tile removed" listeners.
     */
    private final List< BeforeMapTileRemovedListener > beforeMapTileRemovedListeners;

    /**
     * List of "map tile added" listeners.
     */
    private final List< MapTileAddedListener > mapTileAddedListeners;

    /**
     * List of "map tile removed" listeners.
     */
    private final List< MapTileRemovedListener > mapTileRemovedListeners;

    /**
     * The size of the map unit array that defines the map tile types used by this map generator.
     */
    private final int tileSize;

    /**
     * Counter tracking the age of map tile locations as they are added to the map.
     */
    private int ageCounter;

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

        // TODO: Update openLocations to use HashSet once the tests are no longer sensitive to the
        //       iteration order of elements in the set.

        this.improvedRandom                = improvedRandom;
        this.mapTileTypes                  = mapTileTypes;
        this.mapTileLocationFilter         = mapTileLocationFilter;
        this.map                           = new HashMap< MapTileLocation, MapTile >();
        this.mapAge                        = new HashMap< MapTileLocation, Integer >();
        this.openLocations                 = new LinkedHashSet< MapTileLocation >();
        this.excludedMapTilesMap           = new HashMap< MapTileLocation,
                                                          Collection< MapTile > >();
        this.beforeMapTileAddedListeners   = new LinkedList< BeforeMapTileAddedListener >();
        this.beforeMapTileRemovedListeners = new LinkedList< BeforeMapTileRemovedListener >();
        this.mapTileAddedListeners         = new LinkedList< MapTileAddedListener >();
        this.mapTileRemovedListeners       = new LinkedList< MapTileRemovedListener >();
        this.tileSize                      = tileSize;
        this.ageCounter                    = 0;
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
     * Add a new "before map tile added" listener.
     *
     * @param beforeMapTileAddedListener
     *            The listener to add.
     */
    public void addBeforeMapTileAddedListener(
            BeforeMapTileAddedListener beforeMapTileAddedListener )
    {
        beforeMapTileAddedListeners.add( beforeMapTileAddedListener );
    }

    /**
     * Add a new "before map tile removed" listener.
     *
     * @param beforeMapTileRemovedListener
     *            The listener to add.
     */
    public void addBeforeMapTileRemovedListener(
            BeforeMapTileRemovedListener beforeMapTileRemovedListener )
    {
        beforeMapTileRemovedListeners.add( beforeMapTileRemovedListener );
    }

    /**
     * Add a new "map tile added" listener.
     *
     * @param mapTileAddedListener
     *            The listener to add.
     */
    public void addMapTileAddedListener( MapTileAddedListener mapTileAddedListener )
    {
        mapTileAddedListeners.add( mapTileAddedListener );
    }

    /**
     * Add a new "map tile removed" listener.
     *
     * @param mapTileRemovedListener
     *            The listener to add.
     */
    public void addMapTileRemovedListener( MapTileRemovedListener mapTileRemovedListener )
    {
        mapTileRemovedListeners.add( mapTileRemovedListener );
    }

    /**
     * Get the map tile at a specified location.
     *
     * @param mapTileLocation
     *            Location for which to get the map tile. Cannot be null.
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
     *            Location at which to add the map tile. Cannot be null.
     * @param mapTile
     *            Map tile to add. Cannot be null.
     */
    public void addMapTile( MapTileLocation mapTileLocation, MapTile mapTile )
    {
        if ( mapTileLocation == null )
        {
            throw new IllegalArgumentException( "Parameter 'mapTileLocation' cannot be null." );
        }

        if ( mapTile == null )
        {
            throw new IllegalArgumentException( "Parameter 'mapTile' cannot be null." );
        }

        for ( BeforeMapTileAddedListener beforeMapTileAddedListener : beforeMapTileAddedListeners )
        {
            beforeMapTileAddedListener.beforeMapTileAdded( this, mapTileLocation );
        }

        openLocations.remove( mapTileLocation );
        map.put( mapTileLocation, mapTile );
        mapAge.put( mapTileLocation, ageCounter++ );
        addOpenLocations( mapTileLocation );

        for ( MapTileAddedListener mapTileAddedListener : mapTileAddedListeners )
        {
            mapTileAddedListener.mapTileAdded( this, mapTileLocation );
        }

        boolean done = ! badOpenLocationsExist();
        MapTileLocation localMapTileLocation = mapTileLocation;

        while ( ! done )
        {
            if ( ! excludedMapTilesMap.containsKey( localMapTileLocation ))
            {
                excludedMapTilesMap.put( localMapTileLocation, new LinkedList< MapTile >() );
            }

            excludedMapTilesMap.get( localMapTileLocation ).add( map.get( localMapTileLocation ));

            removeNewerMapTileLocations( localMapTileLocation );

            if ( badOpenLocationsExist() )
            {
                excludedMapTilesMap.remove( localMapTileLocation );

                MapTileLocation mostRecentMapTileLocation = null;
                MapTileLocation tempMapTileLocation;

                tempMapTileLocation = new MapTileLocation(
                        localMapTileLocation.getX(), localMapTileLocation.getY() - 1 );

                if ( compareMapTileLocationAge( tempMapTileLocation, mostRecentMapTileLocation ))
                {
                    mostRecentMapTileLocation = tempMapTileLocation;
                }

                tempMapTileLocation = new MapTileLocation(
                        localMapTileLocation.getX(), localMapTileLocation.getY() + 1 );

                if ( compareMapTileLocationAge( tempMapTileLocation, mostRecentMapTileLocation ))
                {
                    mostRecentMapTileLocation = tempMapTileLocation;
                }

                tempMapTileLocation = new MapTileLocation(
                        localMapTileLocation.getX() - 1, localMapTileLocation.getY() );

                if ( compareMapTileLocationAge( tempMapTileLocation, mostRecentMapTileLocation ))
                {
                    mostRecentMapTileLocation = tempMapTileLocation;
                }

                tempMapTileLocation = new MapTileLocation(
                        localMapTileLocation.getX() + 1, localMapTileLocation.getY() );

                if ( compareMapTileLocationAge( tempMapTileLocation, mostRecentMapTileLocation ))
                {
                    mostRecentMapTileLocation = tempMapTileLocation;
                }

                localMapTileLocation = mostRecentMapTileLocation;
            }
            else
            {
                done = true;
            }
        }
    }

    /**
     * Remove a map tile from the map at the specified location.
     *
     * @param mapTileLocation
     *            Location at which to remove the map tile. Cannot be null. Map tile must be present
     *            at the specified location.
     */
    public void removeMapTile( MapTileLocation mapTileLocation )
    {
        if ( mapTileLocation == null )
        {
            throw new IllegalArgumentException( "Parameter 'mapTileLocation' cannot be null." );
        }

        if ( ! map.containsKey( mapTileLocation ))
        {
            throw new IllegalArgumentException(
                    "Parameter 'mapTileLocation' must locate an existing map tile." );
        }

        for ( BeforeMapTileRemovedListener beforeMapTileRemovedListener :
                beforeMapTileRemovedListeners )
        {
            beforeMapTileRemovedListener.beforeMapTileRemoved( this, mapTileLocation );
        }

        map.remove( mapTileLocation );
        mapAge.remove( mapTileLocation );

        openLocations.remove(
                new MapTileLocation( mapTileLocation.getX(),     mapTileLocation.getY() - 1 ));
        openLocations.remove(
                new MapTileLocation( mapTileLocation.getX(),     mapTileLocation.getY() + 1 ));
        openLocations.remove(
                new MapTileLocation( mapTileLocation.getX() - 1, mapTileLocation.getY()     ));
        openLocations.remove(
                new MapTileLocation( mapTileLocation.getX() + 1, mapTileLocation.getY()     ));

        addOpenLocations(
                new MapTileLocation( mapTileLocation.getX(),     mapTileLocation.getY() - 1 ));
        addOpenLocations(
                new MapTileLocation( mapTileLocation.getX(),     mapTileLocation.getY() + 1 ));
        addOpenLocations(
                new MapTileLocation( mapTileLocation.getX() - 1, mapTileLocation.getY()     ));
        addOpenLocations(
                new MapTileLocation( mapTileLocation.getX() + 1, mapTileLocation.getY()     ));
        addOpenLocations(
                new MapTileLocation( mapTileLocation.getX(),     mapTileLocation.getY() - 2 ));
        addOpenLocations(
                new MapTileLocation( mapTileLocation.getX(),     mapTileLocation.getY() + 2 ));
        addOpenLocations(
                new MapTileLocation( mapTileLocation.getX() - 2, mapTileLocation.getY()     ));
        addOpenLocations(
                new MapTileLocation( mapTileLocation.getX() + 2, mapTileLocation.getY()     ));
        addOpenLocations(
                new MapTileLocation( mapTileLocation.getX() - 1, mapTileLocation.getY() - 1 ));
        addOpenLocations(
                new MapTileLocation( mapTileLocation.getX() + 1, mapTileLocation.getY() - 1 ));
        addOpenLocations(
                new MapTileLocation( mapTileLocation.getX() - 1, mapTileLocation.getY() + 1 ));
        addOpenLocations(
                new MapTileLocation( mapTileLocation.getX() + 1, mapTileLocation.getY() + 1 ));

        for ( MapTileRemovedListener mapTileRemovedListener : mapTileRemovedListeners )
        {
            mapTileRemovedListener.mapTileRemoved( this, mapTileLocation );
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
            ( ! neighbor.getMapTileEdge( MapTileEdgePosition.BOTTOM ).mapTileEdgeMatches(
                        mapTile.getMapTileEdge( MapTileEdgePosition.TOP ))))
        {
            return false;
        }

        neighborLocation =
                new MapTileLocation( mapTileLocation.getX(), mapTileLocation.getY() + 1 );
        neighbor = map.get( neighborLocation );

        if (( neighbor != null ) &&
            ( ! neighbor.getMapTileEdge( MapTileEdgePosition.TOP ).mapTileEdgeMatches(
                        mapTile.getMapTileEdge( MapTileEdgePosition.BOTTOM ))))
        {
            return false;
        }

        neighborLocation =
                new MapTileLocation( mapTileLocation.getX() - 1, mapTileLocation.getY() );
        neighbor = map.get( neighborLocation );

        if (( neighbor != null ) &&
            ( ! neighbor.getMapTileEdge( MapTileEdgePosition.RIGHT ).mapTileEdgeMatches(
                        mapTile.getMapTileEdge( MapTileEdgePosition.LEFT ))))
        {
            return false;
        }

        neighborLocation =
                new MapTileLocation( mapTileLocation.getX() + 1, mapTileLocation.getY() );
        neighbor = map.get( neighborLocation );

        if (( neighbor != null ) &&
            ( ! neighbor.getMapTileEdge( MapTileEdgePosition.LEFT ).mapTileEdgeMatches(
                        mapTile.getMapTileEdge( MapTileEdgePosition.RIGHT ))))
        {
            return false;
        }

        if (( excludedMapTilesMap.containsKey( mapTileLocation )) &&
            ( excludedMapTilesMap.get( mapTileLocation ).contains( mapTile )))
        {
            return false;
        }

        return true;
    }

    /**
     * Add open locations for the map tile at the specified map tile location.
     *
     * @param mapTileLocation
     *            Map tile location specifying the map tile for which to add open locations.
     */
    private void addOpenLocations( MapTileLocation mapTileLocation )
    {
        MapTile mapTile = map.get( mapTileLocation );

        if ( mapTile != null )
        {
            for ( MapTileLocation neighborLocation :
                    mapTileLocation.getNeighborLocations( mapTile.getOpenMapTileEdgePositions() ))
            {
                if ( ! map.containsKey( neighborLocation ))
                {
                    openLocations.add( neighborLocation );
                }
            }
        }
    }

    /**
     * Get a flag indicating whether or not any bad open locations exist. A bad open location is an
     * open location where no legal map tile can be placed there.
     *
     * @return True if one or more bad locations exist, false otherwise.
     */
    private boolean badOpenLocationsExist()
    {
start:  for ( MapTileLocation mapTileLocation : openLocations )
        {
            for ( MapTileType mapTileType : mapTileTypes )
            {
                for ( MapTileOrientation mapTileOrientation :
                        mapTileType.getDistinctMapTileOrientations() )
                {
                    if ( legalMapTilePlacement(
                            mapTileType, new MapTilePosition( mapTileLocation,
                                                              mapTileOrientation )))
                    {
                        continue start;
                    }
                }
            }

            return true;
        }

        return false;
    }

    /**
     * Compares the age of two map tile locations.
     *
     * @param mapTileLocation1
     *            The first map tile location to compare.
     * @param mapTileLocation2
     *            The second map tile location to compare.
     *
     * @return True if the first map location exists and is newer than the second map location.
     */
    private boolean compareMapTileLocationAge( MapTileLocation mapTileLocation1,
                                               MapTileLocation mapTileLocation2 )
    {
        return (( mapAge.containsKey( mapTileLocation1 )) &&
                (( mapTileLocation2 == null ) ||
                 ( mapAge.get( mapTileLocation2 ) < mapAge.get( mapTileLocation1 ))));
    }

    /**
     * Remove the specified map tile location and all connected map tile locations that are newer
     * than the specified map tile location.
     *
     * @param mapTileLocation
     *            Map tile location to remove.
     */
    private void removeNewerMapTileLocations( MapTileLocation mapTileLocation )
    {
        // list of map tile locations to be removed that need to have their adjacent map tile
        // locations checked to see if they also need to be removed
        List< MapTileLocation > openRemovedMapTileLocationList =
                new LinkedList< MapTileLocation >();

        // list of map tile locations to be removed that have already had their adjacent map
        // tile locations checked
        List< MapTileLocation > closedRemovedMapTileLocationList =
                new LinkedList< MapTileLocation >();

        openRemovedMapTileLocationList.add( mapTileLocation );

        while ( ! openRemovedMapTileLocationList.isEmpty() )
        {
            closedRemovedMapTileLocationList.addAll( openRemovedMapTileLocationList );

            List< MapTileLocation > tempRemovedMapTileLocationList =
                    new LinkedList< MapTileLocation >( openRemovedMapTileLocationList );

            openRemovedMapTileLocationList.clear();

            for ( MapTileLocation removedMapTileLocation : tempRemovedMapTileLocationList )
            {
                for ( MapTileEdgePosition mapTileEdgePosition :
                        map.get( removedMapTileLocation ).getOpenMapTileEdgePositions() )
                {
                    MapTileLocation tempMapTileLocation = null;

                    if ( mapTileEdgePosition.equals( MapTileEdgePosition.TOP ))
                    {
                        tempMapTileLocation =
                                new MapTileLocation( removedMapTileLocation.getX(),
                                                     removedMapTileLocation.getY() - 1 );
                    }

                    if ( mapTileEdgePosition.equals( MapTileEdgePosition.RIGHT ))
                    {
                        tempMapTileLocation =
                                new MapTileLocation( removedMapTileLocation.getX() + 1,
                                                     removedMapTileLocation.getY() );
                    }

                    if ( mapTileEdgePosition.equals( MapTileEdgePosition.BOTTOM ))
                    {
                        tempMapTileLocation =
                                new MapTileLocation( removedMapTileLocation.getX(),
                                                     removedMapTileLocation.getY() + 1 );
                    }

                    if ( mapTileEdgePosition.equals( MapTileEdgePosition.LEFT ))
                    {
                        tempMapTileLocation =
                                new MapTileLocation( removedMapTileLocation.getX() - 1,
                                                     removedMapTileLocation.getY() );
                    }

                    if (( mapAge.containsKey( tempMapTileLocation )) &&
                        ( ! openRemovedMapTileLocationList.contains( tempMapTileLocation )) &&
                        ( ! closedRemovedMapTileLocationList.contains( tempMapTileLocation )) &&
                        ( mapAge.get( tempMapTileLocation ) >
                          mapAge.get( removedMapTileLocation )))
                    {
                        openRemovedMapTileLocationList.add( tempMapTileLocation );
                    }
                }
            }
        }

        for ( MapTileLocation removedMapTileLocation : closedRemovedMapTileLocationList )
        {
            removeMapTile( removedMapTileLocation );
        }
    }
}
