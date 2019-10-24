package me.fungames.kotlinSquish

/** -----------------------------------------------------------------------------
 *	Copyright (c) 2006 Simon Brown                          si@sjbrown.co.uk
 *	Permission is hereby granted, free of charge, to any person obtaining
 *	a copy of this software and associated documentation files (the
 *	"Software"), to	deal in the Software without restriction, including
 *	without limitation the rights to use, copy, modify, merge, publish,
 *	distribute, sublicense, and/or sell copies of the Software, and to
 *	permit persons to whom the Software is furnished to do so, subject to
 *	the following conditions:
 *	The above copyright notice and this permission notice shall be included
 *	in all copies or substantial portions of the Software.
 *	THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
 *	OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 *	MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 *	IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY
 *	CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
 *	TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
 *	SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 *  @author FunGames
 *
 * --------------------------------------------------------------------------
 */

fun floatToInt(a : Float, limit : Int) : Int {
    // use ANSI round-to-zero behaviour to get round-to-nearest
    var i = (a + 0.5f).toInt()

    // clamp to the limit
    if (i < 0)
        i = 0
    else if (i > limit)
        i = limit

    //done
    return i
}

fun floatTo565(colour : Vec3) : Int {
    // get the components in the correct range
    val r = floatToInt(31.0f * colour.x, 31)
    val g = floatToInt(63.0f * colour.y, 63)
    val b = floatToInt(31.0f * colour.z, 31)

    // pack into a single value
    return (r shl 11) or (g shl 5) or b
}