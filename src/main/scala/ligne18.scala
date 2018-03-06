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

  val update_timer = new Timer(1000, { update }, true)
  update_timer.start

  def update()
  {
    println("Update")
  }

  mainFrame = new FlowPanel {
    contents += new BoxPanel(Orientation.Vertical) {
      contents += new WorldCanvas(world)
      contents += Swing.VStrut(50)
      contents += Button("Retour au menu") {
        update_timer.stop
        ui.change_menu(new MainMenu(ui))
      }
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
