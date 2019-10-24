package me.fungames.kotlinSquish

@ExperimentalUnsignedTypes
class UBytePointer (val data : UByteArray) {

    var pos = 0
        private set
    val size : Int = data.size

    operator fun plusAssign(i : Int) {
        rangeCheck(pos + i)
        pos += i
    }

    operator fun minusAssign(i : Int) {
        rangeCheck(pos - i)
        pos -= i
    }

    operator fun inc() : UBytePointer {
        plusAssign(1)
        return this
    }

    operator fun dec() : UBytePointer {
        minusAssign(1)
        return this
    }

    operator fun get(i : Int) : UByte {
        rangeCheck(pos + i)
        return data[pos + i] ?: throw IllegalStateException("Value at ${pos + 1} is not initialized yet")
    }

    operator fun set(i : Int, b : UByte) {
        rangeCheck(pos + i)
        data[pos + i] = b
    }

    operator fun minus(decrement : Int) : UBytePointer {
        val p = UBytePointer(this.data)
        p.pos = pos - decrement
        return p
    }

    operator fun plus(increment : Int) : UBytePointer {
        val p = UBytePointer(this.data)
        p.pos = pos + increment
        return p
    }

    private fun rangeCheck(newValue : Int) {
        //if(newValue !in data.indices) throw ArrayIndexOutOfBoundsException("$newValue is out of range, array size ${data.size}")
    }

}