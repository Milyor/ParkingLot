package parking

fun main() {
    val parking = Parking()
    do{
        val input = readln().split(" ")
        val action = input[0].lowercase()
        when (action) {
            "park" -> parking.park(input[1], input[2])
            "leave" -> parking.leave(input[1].toInt())
            "create" -> parking.createParking(input[1].toInt())
            "status" -> parking.status()
            "reg_by_color" -> parking.regByColor(input[1].uppercase())
            "spot_by_color" -> parking.spotByColor(input[1].uppercase())
            "spot_by_reg" -> parking.spotByReg(input[1])
            "exit" -> {
                return
            }
            else -> println("Wrong input")
        }
    } while (action != "exit")
}

class Parking {
    // Subclass to store the parking space properties
    class SpotsInfo(
        var plate: String,
        var color: String,
        var occupied: Boolean
    )
    private var capacity = 0
    private var spots: MutableList<SpotsInfo>? = null
    private var parkingLotCreated = false

    fun regByColor(color: String){
        val regs = mutableListOf<String>()
        if (spots == null){
            println("Sorry, a parking lot has not been created.")
            return
        }
        for (i in spots!!.indices){
            if (spots!![i].color == color){
                regs.add(spots!![i].plate)
            }
        }
        if (regs.isEmpty()) {
            println("No cars with color ${color.uppercase()} were found.")
        } else
        println(regs.joinToString(", "))
    }
    fun spotByColor(color: String){
        val spot = mutableListOf<Int>()
        if (spots == null){
            println("Sorry, a parking lot has not been created.")
            return
        }
        for (i in spots!!.indices){
            if (spots!![i].color.uppercase() == color.uppercase()){
                spot.add(i + 1)
            }
        }
        if (spot.isEmpty()) {
            println("No cars with color ${color.uppercase()} were found.")
        } else
        println(spot.joinToString(", "))
    }
    fun spotByReg(plate: String){
        val spot = mutableListOf<Int>()
        if (spots == null){
            println("Sorry, a parking lot has not been created.")
            return
        }
        for (i in spots!!.indices){
            if (spots!![i].plate == plate){
                spot.add(i + 1)
            }
        }
        if (spot.isEmpty()){
            println("No cars with registration number $plate were found.")
        } else println(spot.joinToString())
    }

    fun park(plate: String, color: String) {
        val currentSpots = spots
        if (!parkingLotCreated){
             println("Sorry, a parking lot has not been created.")
            return
        }
        if (currentSpots != null){
            for (i in 0 until capacity) {
                if (!currentSpots[i].occupied) {
                        currentSpots[i].occupied = true
                        currentSpots[i].plate = plate
                        currentSpots[i].color = color.uppercase()
                        println("$color car parked in spot ${i + 1}.")
                    return
                    }
                }
            println("Sorry, the parking lot is full.")
        }
    }

    fun leave (spotNumber: Int){
        if (spots != null) {
            spots!![spotNumber - 1].occupied = false
            spots!![spotNumber - 1].color = ""
            spots!![spotNumber - 1].plate = ""
            println("Spot $spotNumber is free.")
        } else if (!parkingLotCreated) println("Sorry, a parking lot has not been created.")

    }
    fun createParking(newCapacity: Int){
        if (newCapacity > 0){
            spots = MutableList(newCapacity) { SpotsInfo("","", false) }
            capacity = newCapacity
            parkingLotCreated = true
            println("Created a parking lot with $capacity spots.")
        } else {
            println("Invalid capacity. Please enter a positive integer.")
        }
    }
    fun status() {
        if (spots != null) {
            if (spots!!.all { !it.occupied }) {
                println("Parking lot is empty.")
            } else {
                spots!!.forEachIndexed { index, spot ->
                    if (spot.occupied) {
                        println("${index + 1} ${spot.plate} ${spot.color}")
                    }
                }

            }
        }else if (!parkingLotCreated) {
            println("Sorry, a parking lot has not been created.")
        }

    }
}

