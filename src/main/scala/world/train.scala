import math.{min, floor}


class Train(train_line :TrainLine, train_orientation :Boolean = true)
{
  var line = train_line
  var orientation = train_orientation
  var passengers = 0
  var max_passengers = 50
  var progress = 0.0f
  var speed = 1.0f
  var price = 1.0f

  def tick (money :Mutable[Float]) {
    // Update train positions
    progress += speed

    if (progress >= line.length) {

      progress = 0
      passengers = 0

      if (orientation) {
        passengers = min(max_passengers, floor(line.city2.waiting_passengers).toInt)
        line.city2.waiting_passengers = line.city2.waiting_passengers - passengers.toFloat
      }
      else {
        passengers = min(max_passengers, floor(line.city1.waiting_passengers).toInt)
        line.city1.waiting_passengers = line.city1.waiting_passengers - passengers.toFloat
      }

      money.value = money.value + (passengers.toFloat * price)

      orientation = !orientation
    }
  }
}
