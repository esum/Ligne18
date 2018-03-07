import math.round
import util.Random


class City(city_name :String)
{
  val id = City.next_id
  var name = city_name
  var population = 0
  var waiting_passengers = 0
  var coordinates = new Vector(0.0f, 0.0f)
  var rand = new Random()
  var max_population_spawn = 1.0f

  def tick () {
    waiting_passengers += round(rand.nextFloat * max_population_spawn)
  }
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
  var money = new Mutable(0.0f)

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
      train.tick(money)
    }

    for (city <- cities) {
      city.tick
    }
  }
}
