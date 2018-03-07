import swing._


class Menu(ui :UI)
{
  var mainFrame: Component = new FlowPanel
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
