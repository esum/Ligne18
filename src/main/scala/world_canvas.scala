import math.{min, max}
import swing._
import swing.event._
import java.awt.{Color, Graphics2D, BasicStroke, Font, Image}
import java.awt.geom._
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

class WorldCanvas(var world: World) extends Component
{
  preferredSize = new Dimension(500, 500)

  val train_img = ImageIO.read(new File("src/main/resources/train.png"))

  var city_info_id = 0

  listenTo(mouse.clicks)
  reactions += {
    case MouseClicked(_, p, _, _, _) => mouseClicked(new Vector(p.x.toFloat, p.y.toFloat))
  }

  private def mouseClicked(mouse_pos: Vector)
  {
    city_info_id = 0
    for (c <- world.cities)
    {
      if ((c.coordinates - mouse_pos).length < 10.0f)
        city_info_id = c.id
    }

    repaint
  }

  override def paintComponent(g: Graphics2D)
  {
    val d = size
    g.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON)
    g.setColor(Color.WHITE)
    g.fill(new Rectangle(0, 0, d.width, d.height))

    paintLines(g)
    paintCities(g)
    paintTrains(g)
    paintTooltip(g)
  }

  def paintLines(g: Graphics2D) {
    for (line <- world.lines)
    {
      g.setColor(new Color(100, 100, 100))
      g.setStroke(new BasicStroke(2.0f))
      g.draw(new Line2D.Float(line.city1.coordinates.x, line.city1.coordinates.y,
        line.city2.coordinates.x, line.city2.coordinates.y))
      g.setStroke(new BasicStroke(1.0f))
    }
  }

  def paintTrains(g: Graphics2D) {
    for (train <- world.trains)
    {
      var position = new Vector()
      if (train.orientation) {
        position = (train.line.city2.coordinates - train.line.city1.coordinates) *
          (train.progress / train.line.length) + train.line.city1.coordinates
      }
      else
      {
        position = (train.line.city1.coordinates - train.line.city2.coordinates) *
          (train.progress / train.line.length) + train.line.city2.coordinates
      }

      g.setColor(Color.GREEN)
      g.fill(new Ellipse2D.Float(position.x - 3.0f, position.y - 3.0f, 6.0f, 6.0f))
      g.setColor(Color.BLACK)
      g.draw(new Ellipse2D.Float(position.x - 3.0f, position.y - 3.0f, 6.0f, 6.0f))

      g.drawImage(train_img.getScaledInstance(27, 15, Image.SCALE_SMOOTH),
        position.x.toInt - 13, position.y.toInt - 25, null)
    }
  }

  def paintCities(g: Graphics2D) {
    g.setFont(new Font("Monospaced", Font.BOLD, 15))
    for (city <- world.cities)
    {
      if (city.id == city_info_id)
        g.setColor(Color.RED)
      else
        g.setColor(Color.BLUE)
      g.fill(new Ellipse2D.Float(city.coordinates.x - 5.0f, city.coordinates.y - 5.0f, 10.0f, 10.0f))

      if (city.id != city_info_id)
      {
        g.setColor(Color.BLACK)
        g.drawString(city.name, city.coordinates.x + 10.0f, city.coordinates.y - 10.0f)
      }
    }
  }

  def paintTooltip(g: Graphics2D) {
    val city_opt =  world.cities.find((city: City) => city.id == city_info_id)
    city_opt match
    {
      case None =>
      case Some(city) => {
        g.setFont(new Font("Monospaced", Font.PLAIN, 12))
        val name_text = "Name: " + city.name
        val pop_text = "Population: " + city.population.toString
        val width = max(g.getFontMetrics.stringWidth(name_text), g.getFontMetrics.stringWidth(pop_text))
        val height = g.getFontMetrics.getHeight
        var bounds = new Rectangle(
          max(city.coordinates.x.toInt + 10, 0),
          max(city.coordinates.y.toInt - 2 * height - 10, 0),
          width + 10, 2 * height + 10)

        g.setColor(Color.WHITE)
        g.fill(bounds)
        g.setColor(Color.BLACK)
        g.draw(bounds)

        g.drawString(name_text, city.coordinates.x + 15.0f, city.coordinates.y - height - 10.0f)
        g.drawString(pop_text, city.coordinates.x + 15.0f, city.coordinates.y - 5.0f)
      }
    }
  }
}
