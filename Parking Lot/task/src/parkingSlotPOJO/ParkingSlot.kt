package parkingSlotPOJO

class ParkingSlot(private val size: Int) {

    val parkingSlots: Array<String> = Array(size) { "" }

    init {
        println("Created a parking lot with $size spots.")
    }

    override fun toString(): String {
        return "Parking slot has a size of $size"
    }

}