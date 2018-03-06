import swing._
import scala.swing.event._
import java.awt.{Color,Graphics2D,BasicStroke}
import java.awt.geom._


class Menu(ui :UI)
{
  var mainFrame = new FlowPanel
}

class MainMenu(ui :UI) extends Menu(ui :UI)
{
  mainFrame = new FlowPanel {
    contents += new BoxPanel(Orientation.Vertical)
    {
      contents += new Label("Ligne 18")
      contents += Swing.VStrut(50)
      contents += Button("Play") { ui.change_menu(new GameScreen(ui)) }
      contents += Swing.VStrut(20)
      contents += Button("Quit") { sys.exit(0) }
    }
  }
}

class GameScreen(ui :UI) extends Menu(ui :UI)
{
  var world = new World
  world.init

  mainFrame = new FlowPanel {
    contents += new BoxPanel(Orientation.Vertical) {
      contents += new Canvas(world)
      contents += Swing.VStrut(50)
      contents += Button("Retour au menu") { ui.change_menu(new MainMenu(ui)) }
    }
  }
}

class Canvas(val world :World) extends Component
{
  preferredSize = new Dimension(500, 500)

  override def paintComponent(g: Graphics2D)
  {
    val d = size
    g.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON)
    g.setColor(Color.WHITE)
    g.fillRect(0,0, d.width, d.height)

    for (line <- world.lines)
    {
      g.setColor(Color.RED)
      g.draw(new Line2D.Double(line.city1.coordinates._1, line.city1.coordinates._2,
        line.city2.coordinates._1, line.city2.coordinates._2))
    }
  }
}

class UI extends MainFrame
{
  title = "Ligne 18"
  preferredSize = new Dimension(800, 600)

  var menu = new Menu(this)

  def change_menu(menu_ :Menu)
  {
    menu = menu_
    contents = menu.mainFrame
  }
}

object Ligne18
{
  def main(args : Array[String])
  {
    val ui = new UI
    ui.change_menu(new MainMenu(ui))

    ui.visible = true
  }
}
