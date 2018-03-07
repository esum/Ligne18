import swing._


class GameScreen(ui :UI) extends Menu(ui :UI)
{
  var world = new World
  world.init

  val update_timer = new Timer(100, { update }, true)
  update_timer.start

  val world_canvas = new WorldCanvas(world)

  def update()
  {
    world.tick
    world_canvas.repaint
  }

  mainFrame = new FlowPanel {
    contents += new BoxPanel(Orientation.Vertical) {
      contents += world_canvas
      contents += Swing.VStrut(50)
      contents += Button("Retour au menu") {
        update_timer.stop
        ui.change_menu(new MainMenu(ui))
      }
    }
  }
}
