import swing._

class UI extends MainFrame
{
  title = "Ligne 18"
  preferredSize = new Dimension(800, 600)
  contents = new Label("Ceci est la ligne 18... ou pas")
}

object Ligne18
{
  def main(args : Array[String])
  {
    val ui = new UI
    ui.visible = true
  }
}
