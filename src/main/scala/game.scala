import swing._


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
