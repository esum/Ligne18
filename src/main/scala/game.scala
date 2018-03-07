import swing._


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

  def update()
  {
    world.tick
    world_canvas.repaint
  }

  mainFrame = new FlowPanel {
    contents += new BorderPanel {
      layout(world_canvas) = BorderPanel.Position.Center
      layout(back_to_menu) = BorderPanel.Position.South
    }
  }
}
