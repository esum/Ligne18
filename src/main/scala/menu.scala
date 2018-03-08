import swing._
import java.awt.Font


class Menu(ui :UI)
{
  var mainFrame: Component = new FlowPanel
}

class MainMenu(ui :UI) extends Menu(ui :UI)
{
  val title = new Label("Ligne 18")
  title.font = new Font("Monospaced", Font.PLAIN, 40)

  val play = Button("Play") { ui.change_menu(new GameScreen(ui)) }
  play.preferredSize = new Dimension(150, 50)

  val quit = Button("Quit") { sys.exit(0) }
  quit.preferredSize = new Dimension(150, 50)

  mainFrame = new GridPanel(5, 1) {
    contents += Swing.Glue
    contents += new FlowPanel { contents += title }
    contents += new FlowPanel { contents += play }
    contents += new FlowPanel { contents += quit }
  }
}
