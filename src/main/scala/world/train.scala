import math.{min, floor}


class Train(train_line: TrainLine, train_orientation: Boolean = true)
{
  val id = Train.next_id
  var line = train_line
  var orientation = train_orientation
  var passengers = 0
  var max_passengers = 50
  var progress = 0.0f
  var speed = 1.0f
  var price = 1.0f

  override def toString: String = "Train #" + id.toString

  def tick(money: Mutable[Float]) {
    // Update train positions
    progress += speed

    if (progress >= line.length) {

      progress = 0
      passengers = 0
      orientation = !orientation

      fill(money)
    }
  }

  def fill(money: Mutable[Float])
  {
    if (orientation) {
      passengers = min(max_passengers, floor(line.city1.waiting_passengers).toInt)
      line.city1.waiting_passengers = line.city1.waiting_passengers - passengers.toFloat
    }
    else {
      passengers = min(max_passengers, floor(line.city2.waiting_passengers).toInt)
      line.city2.waiting_passengers = line.city2.waiting_passengers - passengers.toFloat
    }
    money.value = money.value + (passengers.toFloat * price * line.length / 100.0f)
  }
}

object Train
{
  var id = 0

  def next_id(): Int = { id += 1; id }
}
