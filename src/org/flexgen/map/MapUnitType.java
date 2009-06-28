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
 * Class representing a map unit type that can be used in map tiles.
 */
public class MapUnitType
{
    /**
     * Name of the map unit type.
     */
    private final String name;

    /**
     * Construct a map unit type.
     *
     * @param name
     *            Name of the map unit type.
     */
    public MapUnitType( String name )
    {
        this.name = name;
    }

    /**
     * Determines whether or not this map unit type is equal to another map unit type.
     *
     * @param obj
     *            The map unit type against which to compare this map unit type.
     *
     * @return True if the two map unit types are equal, false otherwise.
     */
    public boolean equals( Object obj )
    {
        if ( ! ( obj instanceof MapUnitType ))
        {
            return false;
        }

        MapUnitType mapUnitType = (MapUnitType) obj;
        return this.name.equals( mapUnitType.name );
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
