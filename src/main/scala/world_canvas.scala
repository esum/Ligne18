import swing._
import swing.event._
import java.awt.{Color, Graphics2D, BasicStroke, Font}
import java.awt.geom._


class WorldCanvas(var world: World) extends Component
{
  preferredSize = new Dimension(500, 500)

  var city_info_id = 0

  listenTo(mouse.clicks)
  reactions += {
    case MouseClicked(_, p, _, _, _) => mouseClicked(p.x, p.y)
  }

  private def mouseClicked(x: Int, y: Int)
  {


    repaint
  }

  override def paintComponent(g: Graphics2D)
  {
    val d = size
    g.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON)
    g.setColor(Color.WHITE)
    g.fillRect(0, 0, d.width, d.height)

    for (line <- world.lines)
    {
      g.setColor(Color.BLACK)
      g.setStroke(new BasicStroke(2.0f))
      g.draw(new Line2D.Float(line.city1.coordinates.x, line.city1.coordinates.y,
        line.city2.coordinates.x, line.city2.coordinates.y))
      g.setStroke(new BasicStroke(1.0f))
    }

    for (city <- world.cities)
    {
      g.setColor(Color.BLUE)
      g.fill(new Ellipse2D.Float(city.coordinates.x - 5.0f, city.coordinates.y - 5.0f, 10.0f, 10.0f))
      g.setColor(Color.BLACK)
      g.setFont(new Font("Monospaced", Font.BOLD, 15))
      g.drawString(city.name, city.coordinates.x + 10.0f, city.coordinates.y - 10.0f)
    }

    for (train <- world.trains)
    {
      var position = new Vector()
      if (train.orientation) {
        position = (train.line.city2.coordinates - train.line.city1.coordinates) * (train.progress / train.line.length) + train.line.city1.coordinates
      }
      else
      {
        position = (train.line.city1.coordinates - train.line.city2.coordinates) * (train.progress / train.line.length) + train.line.city2.coordinates
      }

      g.setColor(Color.BLUE)
      g.fill(new Ellipse2D.Float(position.x - 1.0f, position.y - 1.0f, 2.0f, 2.0f))
    }
  }
}
