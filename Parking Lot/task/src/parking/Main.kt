package parking

import parkingSlotPOJO.ParkingSlot
import java.util.NoSuchElementException

fun main() {

    var isParkingSlotsCreated = false
    var parkingSlots: Array<String> = Array(0) { "" }

    while (true) {
        try {
            val commandParameters = readLine()!!.split(" ")
            when (commandParameters[0]) {
                "create" -> {
                    isParkingSlotsCreated = true
                    parkingSlots = createParkingSlots(size = commandParameters[1].toInt())
                }

                "park" -> parkVehicle(isParkingSlotsCreated, parkingSlots,
                    vehicleNumber = commandParameters[1], vehicleColor = commandParameters[2])

                "leave" -> removeVehicle(isParkingSlotsCreated, parkingSlots, parkingSlotNumber = commandParameters[1].toInt())

                "status" -> parkingStatus(isParkingSlotsCreated, parkingSlots)

                "reg_by_color" -> searchForVehicle(isParkingSlotsCreated, parkingSlots, searchOption = 1, searchParameter = commandParameters[1])

                "spot_by_color" -> searchForVehicle(isParkingSlotsCreated, parkingSlots, searchOption = 2, searchParameter = commandParameters[1])

                "spot_by_reg" -> searchForVehicle(isParkingSlotsCreated, parkingSlots, searchOption = 3, searchParameter = commandParameters[1])

                    "exit" -> break

                else -> throw NoSuchElementException("Invalid Option")
            }
        } catch (ISE : java.lang.IllegalStateException) {
            println(ISE.message)
        } catch (NSF: NoSuchFieldException) {
            println(NSF.message)
        } catch (NSE : NoSuchElementException) {
            println(NSE.message)
        }
    }

}

fun createParkingSlots(size: Int) = ParkingSlot(size).parkingSlots

fun parkVehicle(isParkingSlotsCreated: Boolean, parkingSlots: Array<String>,
                vehicleNumber: String, vehicleColor: String) {
    if (isParkingSlotsCreated) {
        var freeParkingSlot = 0

        // Find the lowest free parking slot
        while (freeParkingSlot <= parkingSlots.lastIndex &&
            parkingSlots[freeParkingSlot].isNotEmpty()) {
            freeParkingSlot++
        }

        if (freeParkingSlot == parkingSlots.size) { // No parking slots available
            throw IllegalStateException("Sorry, the parking lot is full.")
        } else {
            parkingSlots[freeParkingSlot] = "$vehicleNumber $vehicleColor"
            println("$vehicleColor car parked in spot ${freeParkingSlot + 1}.")
        }
    } else {
        throw NoSuchFieldException("Sorry, a parking lot has not been created.")
    }
}

fun removeVehicle(isParkingSlotsCreated: Boolean, parkingSlots: Array<String>, parkingSlotNumber: Int) {
    if (isParkingSlotsCreated) {
        if (parkingSlots[parkingSlotNumber - 1].contentEquals("")) {
            println("There is no car in spot $parkingSlotNumber.")
        } else {
            println("Spot $parkingSlotNumber is free.")
            parkingSlots[parkingSlotNumber - 1] = ""
        }
    } else {
        throw NoSuchFieldException("Sorry, a parking lot has not been created.")
    }
}

fun parkingStatus(isParkingSlotsCreated: Boolean, parkingSlots: Array<String>) {
    if (isParkingSlotsCreated) {
        var countOfOccupiedSlots = 0
        for(parkingSlotIndex in 0..parkingSlots.lastIndex) {
            val status = parkingSlots[parkingSlotIndex]
            if (status.isNotEmpty()) {
                ++countOfOccupiedSlots
                println("${parkingSlotIndex + 1} $status")
            }
        }

        if (countOfOccupiedSlots == 0) {
            println("Parking lot is empty.")
        }
    } else {
        throw NoSuchFieldException("Sorry, a parking lot has not been created.")
    }
}

fun searchForVehicle(isParkingSlotsCreated: Boolean, parkingSlots: Array<String>, searchOption: Int, searchParameter: String) {
    if (isParkingSlotsCreated) {
        when (searchOption) {
            1 -> searchForVehicleByColor(parkingSlots, _option = 1, _color = searchParameter)
            2 ->  searchForVehicleByColor(parkingSlots, _option = 2, _color = searchParameter)
            3 -> searchForVehicleByRegistration(parkingSlots, _option = 3, _registration = searchParameter)
            else -> throw NoSuchElementException("Invalid Option")
        }
    } else {
        throw NoSuchFieldException("Sorry, a parking lot has not been created.")
    }
}

fun searchForVehicleByColor(parkingSlots: Array<String>, _option: Int, _color: String) {
    val color = _color.toLowerCase()
    var vehicleRegistrationNumbers = emptyArray<String>()
    var vehicleParkingSpots = emptyArray<Int>()

    for (parkingSlotIndex in 0..parkingSlots.lastIndex) {
        if (parkingSlots[parkingSlotIndex].isNotEmpty()) {
            val vehicleDetails: List<String> = parkingSlots[parkingSlotIndex].split(" ")
            val tempColor = vehicleDetails[1].toLowerCase()
            if (tempColor == color) {
                vehicleRegistrationNumbers += vehicleDetails[0]
                vehicleParkingSpots += (parkingSlotIndex + 1)
            }
        }
    }

    when {

        vehicleRegistrationNumbers.isEmpty() -> {
            throw NoSuchElementException("No cars with color $_color were found.")
        }

        _option == 1 -> {
            println(vehicleRegistrationNumbers.joinToString())
        }

        else -> {
            println(vehicleParkingSpots.joinToString())
        }
    }
}

fun searchForVehicleByRegistration(parkingSlots: Array<String>, _option: Int, _registration: String) {
    val registration = _registration.toLowerCase()
    var vehicleParkingSpot = 0

    for (parkingSlotIndex in 0..parkingSlots.lastIndex) {
        if (parkingSlots[parkingSlotIndex].isNotEmpty()) {
            val vehicleDetails: List<String> = parkingSlots[parkingSlotIndex].split(" ")
            val tempRegistration = vehicleDetails[0].toLowerCase()
            if (tempRegistration == registration) {
                vehicleParkingSpot = parkingSlotIndex + 1
                break
            }
        }
    }

    when (vehicleParkingSpot) {
        0 -> {
            throw NoSuchElementException("No cars with registration number $_registration were found.")
        }
        else -> {
            println(vehicleParkingSpot)
        }
    }
}
