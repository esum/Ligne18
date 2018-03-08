class TrainLine(city_1 :City, city_2 :City)
{
  val id = TrainLine.next_id
  var city1 = city_1
  var city2 = city_2
  var length = (city1.coordinates - city2.coordinates).length

  def name () {
    city1.name + "--" + city2.name
  }

  override def toString: String = city1.name + " ↔ " + city2.name
}

object TrainLine
{
  var id = 0
  def next_id: Int = { id += 1; id }
}
