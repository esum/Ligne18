import swing._

object Ligne18 extends SimpleSwingApplication
{
  def top = new MainFrame
  {
    title = "Ligne 18"
    preferredSize = new Dimension(800, 600)
    contents = new Label("Ceci est la ligne 18... ou pas")
  }
}
