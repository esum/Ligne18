class City(city_name :String)
{
  var name = city_name
  var population = 0
  var waiting_passengers = 0
  var coordinates = (0.0f, 0.0f)
}

class TrainLine(city_1 :City, city_2 :City)
{
  var city1 = city_1
  var city2 = city_2
}

class World
{
  var cities :List[City] = List()
  var lines :List[TrainLine] = List()

  def init() = {
    var c1 = new City("Paris")
    c1.coordinates = (200.0f, 100.0f)
    var c2 = new City("Cachan")
    c2.coordinates = (400.0f, 300.0f)
    cities = List(c1, c2)
    lines = List(new TrainLine(c1, c2))
  }
}
