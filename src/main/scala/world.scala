class City(city_name :String)
{
  var name = city_name
  var population = 0
  var waiting_passengers = 0
  var coordinates = (0.0, 0.0)
}

class TrainLine(city_1 :City, city_2 :City)
{
  var city1 = city_1
  var city2 = city_2
}

class World
{
  var Cities :List[City] = List()
  var Lines :List[TrainLine] = List()
}
