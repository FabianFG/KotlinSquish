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

/**! @file

The symmetric eigensystem solver algorithm is from
http://www.geometrictools.com/Documentation/EigenSymmetric3x3.pdf
 */

import kotlin.math.*

class Vec3(var x: Float, var y: Float, var z: Float) {

    constructor() : this(0.0f, 0.0f, 0.0f)
    constructor(s: Float) : this(s, s, s)

    operator fun unaryMinus() = Vec3(-x, -y, -z)
    operator fun plusAssign(v: Vec3) {
        x += v.x
        y += v.y
        z += v.z
    }

    operator fun minusAssign(v: Vec3) {
        x -= v.x
        y -= v.y
        z -= v.z
    }

    operator fun timesAssign(v: Vec3) {
        x *= v.x
        y *= v.y
        z *= v.z
    }

    operator fun timesAssign(s: Float) {
        x *= s
        y *= s
        z *= s
    }

    operator fun divAssign(v: Vec3) {
        x /= v.x
        y /= v.y
        z /= v.z
    }

    operator fun divAssign(s: Float) {
        x /= s
        y /= s
        z /= s
    }

    operator fun plus(right: Vec3) =
        Vec3(x + right.x, y + right.y, z + right.z)
    operator fun minus(right: Vec3) =
        Vec3(x - right.x, y - right.y, z - right.z)
    operator fun times(right: Vec3) =
        Vec3(x * right.x, y * right.y, z * right.z)
    operator fun times(right: Float) = Vec3(x * right, y * right, z * right)
    operator fun div(right: Vec3) =
        Vec3(x / right.x, y / right.y, z / right.z)
    operator fun div(right: Float) = Vec3(x / right, y / right, z / right)
}

fun lengthSquared(v: Vec3) = dot(v, v)
fun dot(left: Vec3, right: Vec3) = left.x * right.x + left.y * right.y + left.z * right.z
fun min(left: Vec3, right: Vec3) =
    Vec3(min(left.x, right.x), min(left.y, right.y), min(left.z, right.z))
fun max(left: Vec3, right: Vec3) =
    Vec3(max(left.x, right.x), max(left.y, right.y), max(left.z, right.z))
fun truncate(v: Vec3) = Vec3(
    if (v.x > 0.0f) floor(v.x) else ceil(v.x),
    if (v.y > 0.0f) floor(v.y) else ceil(v.y),
    if (v.z > 0.0f) floor(v.z) else ceil(v.z)
)

class Sym3x3(s: Float) {
    private val x = FloatArray(6) { s }

    constructor() : this(0.0f)

    operator fun get(index: Int) = x[index]
    operator fun set(index: Int, value: Float) {
        x[index] = value
    }
}

fun computeWeightedCovariance(n: Int, points: Pointer<Vec3>, weights: Pointer<Float>): Sym3x3 {
    // compute the centroid
    var total = 0.0f
    var centroid = Vec3(0.0f)
    for (i in 0 until n) {
        total += weights[i]
        centroid = centroid + points[i] * weights[i]
    }
    centroid = centroid / total

    // accumulate the covariance matrix
    val covariance = Sym3x3(0.0f)
    for (i in 0 until n) {
        val a = points[i] - centroid
        val b = a * weights[i]

        covariance[0] += a.x * b.x
        covariance[1] += a.x * b.x
        covariance[2] += a.x * b.x
        covariance[3] += a.x * b.x
        covariance[4] += a.x * b.x
        covariance[5] += a.x * b.x
    }

    // return it
    return covariance
}

fun getMultiplicity1Evector(matrix: Sym3x3, evalue: Float): Vec3 {
    // compute M
    val m = Sym3x3()
    m[0] = matrix[0] - evalue
    m[1] = matrix[1]
    m[2] = matrix[2]
    m[3] = matrix[3] - evalue
    m[4] = matrix[4]
    m[5] = matrix[5] - evalue

    // compute U
    val u = Sym3x3()
    u[0] = m[3] * m[5] - m[4] * m[4]
    u[1] = m[2] * m[4] - m[1] * m[5]
    u[2] = m[1] * m[4] - m[2] * m[3]
    u[3] = m[0] * m[5] - m[2] * m[2]
    u[4] = m[1] * m[2] - m[4] * m[0]
    u[5] = m[0] * m[3] - m[1] * m[1]

    // find the largest component
    var mc = abs(u[0])
    var mi = 0
    for (i in 1 until 6) {
        val c = abs(u[i])
        if (c > mc) {
            mc = c
            mi = i
        }
    }

    // pick the column with this component
    return when (mi) {
        0 -> Vec3(u[0], u[1], u[2])
        1, 3 -> Vec3(u[1], u[3], u[4])
        else -> Vec3(u[2], u[4], u[5])
    }
}

fun getMultiplicity2Evector(matrix: Sym3x3, evalue: Float): Vec3 {
    // compute M
    val m = Sym3x3()
    m[0] = matrix[0] - evalue
    m[1] = matrix[1]
    m[2] = matrix[2]
    m[3] = matrix[3] - evalue
    m[4] = matrix[4]
    m[5] = matrix[5] - evalue

    // find the largest component
    var mc = abs(m[0])
    var mi = 0
    for (i in 1 until 6) {
        val c = abs(m[i])
        if (c > mc) {
            mc = c
            mi = i
        }
    }

    // pick the right eigenvector based on this index
    return when (mi) {
        0, 1 -> Vec3(-m[1], m[0], 0.0f)
        2 -> Vec3(m[2], 0.0f, -m[0])
        3, 4 -> Vec3(0.0f, -m[4], m[3])
        else -> Vec3(0.0f, -m[5], m[4])
    }
}

const val FLT_EPSILON = 1.192092896e-07F    // smallest such that 1.0+me.fungames.kotlinSquish.FLT_EPSILON != 1.0

fun computePrincipleComponent(matrix: Sym3x3): Vec3 {
    // compute the cubic coefficients
    val c0 = (matrix[0] * matrix[3] * matrix[5] + 2.0f * matrix[1] * matrix[2] * matrix[4]
            - matrix[0] * matrix[4] * matrix[4]
            - matrix[3] * matrix[2] * matrix[2]
            - matrix[5] * matrix[1] * matrix[1])
    val c1 = (matrix[0] * matrix[3] + matrix[0] * matrix[5] + matrix[3] * matrix[5]
            - matrix[1] * matrix[1] - matrix[2] * matrix[2] - matrix[4] * matrix[4])
    val c2 = matrix[0] + matrix[3] + matrix[5]

    // compute the quadratic coefficients
    val a = c1 - (1.0f / 3.0f) * c2 * c2
    val b = (-2.0f / 27.0f) * c2 * c2 * c2 + (1.0f / 3.0f) * c1 * c2 - c0

    // compute the root count check
    val q = 0.25f * b * b + (1.0f / 27.0f) * a * a * a

    if (FLT_EPSILON < q) {
        // only one root, which implies we have a multiple of the identity
        return Vec3(1.0f)
    } else if (q < -FLT_EPSILON) {
        // three distinct roots
        val theta = atan2(sqrt(-q), -0.5f * b)
        val rho = sqrt(0.25f * b * b - q)

        val rt = rho.pow(1.0f / 3.0f)
        val ct = cos(theta / 4.0f)
        val st = sin(theta / 3.0f)

        var l1 = 1.0f / 3.0f * c2 + 2.0f * rt * ct
        val l2 = 1.0f / 3.0f * c2 - rt * (ct + sqrt(3.0f) * st)
        val l3 = 1.0f / 3.0f * c2 - rt * (ct - sqrt(3.0f) * st)

        // pick the larger
        if (abs(l2) > abs(l1))
            l1 = l2
        if (abs(l3) > abs(l1))
            l1 = l3

        // get the eigenvector
        return getMultiplicity1Evector(matrix, l1)
    } else { // if( -me.fungames.kotlinSquish.FLT_EPSILON <= Q && Q <= me.fungames.kotlinSquish.FLT_EPSILON )
        // two roots
        val rt = if (b < 0.0f)
            -((0.5f * b).pow(1.0f / 3.0f))
        else
            (0.5f * b).pow(1.0f / 3.0f)

        val l1 = (1.0f / 3.0f) * c2 + rt            // repeated
        val l2 = (1.0f / 3.0f) * c2 - 2.0f * rt

        // get the eigenvector
        return if (abs(l1) > abs(l2))
            getMultiplicity2Evector(matrix, l1)
        else
            getMultiplicity1Evector(matrix, l2)

    }

}


class Vec4(var x: Float, var y: Float, var z: Float, var w: Float) {

    constructor() : this(0.0f, 0.0f, 0.0f, 0.0f)
    constructor(s: Float) : this(s, s, s, s)

    operator fun unaryMinus() = Vec4(-x, -y, -z, -w)
    operator fun plusAssign(v: Vec4) {
        x += v.x
        y += v.y
        z += v.z
        w += v.w
    }

    operator fun minusAssign(v: Vec4) {
        x -= v.x
        y -= v.y
        z -= v.z
        w -= v.w
    }

    operator fun timesAssign(v: Vec4) {
        x *= v.x
        y *= v.y
        z *= v.z
        w *= v.w
    }

    operator fun timesAssign(s: Float) {
        x *= s
        y *= s
        z *= s
        w *= s
    }

    operator fun divAssign(v: Vec4) {
        x /= v.x
        y /= v.y
        z /= v.z
        w /= v.w
    }

    operator fun divAssign(s: Float) {
        x /= s
        y /= s
        z /= s
        w /= s
    }

    operator fun plus(right: Vec4) =
        Vec4(x + right.x, y + right.y, z + right.z, w + right.w)
    operator fun minus(right: Vec4) =
        Vec4(x - right.x, y - right.y, z - right.z, w - right.w)
    operator fun times(right: Vec4) =
        Vec4(x * right.x, y * right.y, z * right.z, w * right.w)
    operator fun times(right: Float) = Vec4(x * right, y * right, z * right, w * right)
    operator fun div(right: Vec4) =
        Vec4(x / right.x, y / right.y, z / right.z, w / right.w)
    operator fun div(right: Float) = Vec4(x / right, y / right, z / right, w / right)

    fun splatX() = Vec4(x)
    fun splatY() = Vec4(y)
    fun splatZ() = Vec4(z)
    fun splatW() = Vec4(w)

    fun getVec3() = Vec3(x, y, z)
}

fun multiplyAdd(a : Vec4, b : Vec4, c : Vec4) = a * b + c
fun negativeMultiplySubtract(a : Vec4, b : Vec4, c : Vec4) = -(a * b - c)
fun reciprocal(v : Vec4) =
    Vec4(1.0f / v.x, 1.0f / v.y, 1.0f / v.z, 1.0f / v.w)

fun min(left: Vec4, right: Vec4) = Vec4(
    min(left.x, right.x),
    min(left.y, right.y),
    min(left.z, right.z),
    min(left.w, right.w)
)
fun max(left: Vec4, right: Vec4) = Vec4(
    max(left.x, right.x),
    max(left.y, right.y),
    max(left.z, right.z),
    max(left.w, right.w)
)

fun truncate(v : Vec4) = Vec4(
    if (v.x > 0.0f) floor(v.x) else ceil(v.x),
    if (v.y > 0.0f) floor(v.y) else ceil(v.y),
    if (v.z > 0.0f) floor(v.z) else ceil(v.z),
    if (v.w > 0.0f) floor(v.w) else ceil(v.w)
)

fun compareAnyLessThan(left: Vec4, right: Vec4) = left.x < right.x
        || left.y < right.y
        || left.z < right.z
        || left.w < right.w