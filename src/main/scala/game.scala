import swing._
import java.awt.{Font}

class GameScreen(ui :UI) extends Menu(ui :UI)
{
  var world = new World
  world.init

  val update_timer = new Timer(100, { update }, true)
  update_timer.start

  val world_canvas = new WorldCanvas(world)
  val back_to_menu = Button("Retour au menu") {
    update_timer.stop
    ui.change_menu(new MainMenu(ui))
  }
  val info = new BoxPanel(Orientation.Horizontal)
  val money_label = new Label("Money: " + world.money.value.toString)
  money_label.font = new Font("Monospaced", Font.PLAIN, 16)
  info.contents += money_label

  def update()
  {
    world.tick
    world_canvas.repaint

    money_label.text = "Money: %.2f₿".format(world.money.value)
    info.repaint
  }

  mainFrame = new FlowPanel {
    contents += new BorderPanel {
      layout(info) = BorderPanel.Position.North
      layout(world_canvas) = BorderPanel.Position.Center
      layout(back_to_menu) = BorderPanel.Position.South
    }
  }
}
