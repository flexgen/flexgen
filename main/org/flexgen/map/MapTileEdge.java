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
 * Class representing an edge for a map tile.
 */
public class MapTileEdge
{
    /**
     * Name of the map tile edge.
     */
    private final String name;

    /**
     * Construct a map tile edge.
     *
     * @param name
     *            Name of the map tile edge. Cannot be null.
     */
    public MapTileEdge( String name )
    {
        if ( name == null )
        {
            throw new IllegalArgumentException( "Parameter 'name' cannot be null." );
        }

        this.name = name;
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
     * Determines whether or not this map tile edge is equal to another map tile edge.
     *
     * @param obj
     *            The map tile edge against which to compare this map tile edge.
     *
     * @return True if the two map tile edges are equal, false otherwise.
     */
    public boolean equals( Object obj )
    {
        if ( ! ( obj instanceof MapTileEdge ))
        {
            return false;
        }

        MapTileEdge mapTileEdge = (MapTileEdge) obj;
        return this.name.equals( mapTileEdge.name );
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
