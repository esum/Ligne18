import math.{round, min}
import util.Random


class World
{
  var cities: List[City] = List()
  var lines: List[TrainLine] = List()
  var trains: List[Train] = List()

  var width = 500
  var height = 500
  var city_spacing = (width + height).toFloat * 0.075f
  var max_line_length = (width + height).toFloat * 0.175f
  var max_new_lines = 3

  var money = new Mutable(0.0f)

  var generating = true
  var city_probability = 0.0f
  var city_probability_increase = 0.0002f
  var max_city = 15

  var city_max_population = 5000
  var city_min_population = 300

  var rand = new Random()

  def init() {
    var c1 = new City("Paris")
    c1.coordinates = new Vector(100.0f, 100.0f)
    var c2 = new City("Cachan")
    c2.coordinates = new Vector(200.0f, 300.0f)
    cities = List(c1, c2)
    var l = new TrainLine(c1, c2)
    lines = List(l)
    var t = new Train(l)
    t.progress = 0.0f
    t.speed = 1.0f
    trains = List(t)
  }

  def tick()
  {
    for (train <- trains)
      train.tick(money)

    for (city <- cities)
      city.tick
    
    if (generating) {
      var p = rand.nextFloat

      if (p <= city_probability) {
        // Create a new city
        var new_cities :List[Vector] = List()

        var tries = 0

        while (new_cities.isEmpty && generating) {
          for (i <- 1 to 10) {

            var coordinates = new Vector(rand.nextFloat * width.toFloat, rand.nextFloat * height.toFloat)
            var keep_city = true
            var city_too_far = true
            
            for (city <- cities) {
              if ((city.coordinates - coordinates).length < city_spacing)
                keep_city = false
              if ((city.coordinates - coordinates).length < max_line_length)
                city_too_far = false
            }

            if (keep_city && !city_too_far)
              new_cities = new_cities ++ List(coordinates)
          }

          tries += 1

          if (tries >= 10)
            generating = false
        }
        
        if (generating)
        {
          var new_city = new City("", new_cities(rand.nextInt(new_cities.length)))
          new_city.name = "City #" + new_city.id.toString
          new_city.population = city_min_population + rand.nextInt(city_max_population - city_min_population + 1)

          var new_lines :List[TrainLine] = List()

          for (city <- cities)
            if ((city.coordinates - new_city.coordinates).length <= max_line_length)
              new_lines = new_lines ++ List(new TrainLine(city, new_city))
          
          var new_lines_number = 0

          for (i <- 0 to rand.nextInt(min(max_new_lines, new_lines.length))) {
            var j = rand.nextInt(new_lines.length)
            if (!lines.contains(new_lines(j)))
            lines = lines ++ List(new_lines(j))
          }

          cities = cities ++ List(new_city)

          city_probability = 0.0f
        }
      }
      else
        city_probability += city_probability_increase
    }
  }


}
