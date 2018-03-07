import swing._


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
