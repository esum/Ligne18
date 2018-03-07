import math.min


class City(city_name :String)
{
  val id = City.next_id
  var name = city_name
  var population = 0
  var waiting_passengers = 0
  var coordinates = new Vector(0.0f, 0.0f)
}

object City
{
  var current_id = 0
  def next_id(): Int =
  {
    current_id += 1
    current_id
  }
}

class TrainLine(city_1 :City, city_2 :City)
{
  var city1 = city_1
  var city2 = city_2
  var length = (city1.coordinates - city2.coordinates).length
}

class World
{
  var cities :List[City] = List()
  var lines :List[TrainLine] = List()
  var trains :List[Train] = List()

  def init() {
    var c1 = new City("Paris")
    c1.coordinates = new Vector(100.0f, 100.0f)
    var c2 = new City("Cachan")
    c2.coordinates = new Vector(200.0f, 300.0f)
    cities = List(c1, c2)
    var l = new TrainLine(c1, c2)
    lines = List(l)
    var t = new Train(l)
    t.progress = 0.25f * l.length
    t.speed = 10.0f
    trains = List(t)
  }

  def tick() {
    for (train <- trains) {

      // Update train positions
      train.progress += train.speed

      if (train.progress >= train.line.length) {

        train.progress = train.line.length
        train.passengers = 0

        if (train.orientation) {
          train.passengers = min(train.max_passengers, train.line.city2.waiting_passengers)
          train.line.city2.waiting_passengers = train.line.city2.waiting_passengers - train.max_passengers
        }
        else {
          train.passengers = min(train.max_passengers, train.line.city1.waiting_passengers)
          train.line.city1.waiting_passengers = train.line.city1.waiting_passengers - train.max_passengers
        }

        train.orientation = !train.orientation
      }
    }
  }
}
