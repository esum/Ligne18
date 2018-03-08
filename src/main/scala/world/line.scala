class TrainLine(city_1 :City, city_2 :City)
{
  var city1 = city_1
  var city2 = city_2
  var length = (city1.coordinates - city2.coordinates).length
  
  def name () {
    city1.name + "--" + city2.name
  }
}