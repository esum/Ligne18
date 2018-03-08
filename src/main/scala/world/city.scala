import math.min
import util.Random


class City(city_name :String, position :Vector = new Vector(0.0f, 0.0f))
{
  val id = City.next_id
  var name = city_name
  var population = 1000
  var waiting_passengers = 0.0f
  var coordinates = position
  var rand = new Random()
  var max_population_spawn = 0.0005f

  def tick () {
    waiting_passengers += min(rand.nextFloat * max_population_spawn * population.toFloat, population.toFloat)
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