import swing._
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO


class UI extends MainFrame
{
  title = "Ligne 18"
  preferredSize = new Dimension(800, 600)

  iconImage = ImageIO.read(new File("src/main/resources/icon.png"))

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
