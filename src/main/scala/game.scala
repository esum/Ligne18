import swing._
import java.awt.{Color, Font}


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
  back_to_menu.preferredSize = new Dimension(800, 30)
  val infos_up = new BoxPanel(Orientation.Horizontal)
  val money_label = new Label("")
  money_label.font = new Font("Monospaced", Font.PLAIN, 18)
  val time_label = new Label("")
  time_label.font = new Font("Monospaced", Font.PLAIN, 18)
  infos_up.contents += money_label
  infos_up.contents += Swing.HStrut(10)
  infos_up.contents += time_label

  val info_left = new BoxPanel(Orientation.Vertical)
  info_left.preferredSize = new Dimension(300, 600)
  val train_list = new BoxPanel(Orientation.Vertical) {
    var list = new ComboBox(List[Train]())
  }

  val train_info = new BoxPanel(Orientation.Vertical) {
    var line = new Label("")
    var passengers = new Label("")
    var progress = new Label("")
    var speed = new Label("")
    val affect_line = Button("Change line") {}
    border = Swing.TitledBorder(Swing.LineBorder(Color.BLACK, 2), "")
    contents += new GridPanel(5, 1) {
      contents += line
      contents += passengers
      contents += progress
      contents += speed
      //contents += affect_line
    }
  }

  val city_info = new BoxPanel(Orientation.Vertical) {
    var city_name = new Label("")
    var population = new Label("")
    var waiting_passengers = new Label("")
    contents += new GridPanel(3, 1) {
      contents += city_name
      contents += population
      contents += waiting_passengers
    }
  }

  val new_train = new GridPanel(1, 2) {
    contents += Button("Buy new train")
    {
      val new_train_dialog = new Dialog(ui)
      new_train_dialog.minimumSize = new Dimension(400, 200)
      new_train_dialog.maximumSize = new Dimension(400, 200)
      new_train_dialog.preferredSize = new Dimension(400, 200)
      new_train_dialog.visible = true
      new_train_dialog.open
      new_train_dialog.contents = new GridPanel(5, 2)
      {
        val lines = new ComboBox(world.lines)
        val error = new Label("")
        contents += new Label("Train engine")
        contents += new ComboBox(List("Engine #1"))
        contents += new Label("Passenger carriage")
        contents += new ComboBox(List("Passenger carriage #1"))
        contents += new Label("Line")
        contents += lines
        contents += new Label("Price: 500₿")
        contents += Button("Buy train")
        {
          val line_opt = world.lines.find((line: TrainLine) => line.id == lines.selection.item.id)
          line_opt match {
            case None => new_train_dialog.close()
            case Some(l) => {
              if (world.money.value >= 500.0f)
              {
                val t = new Train(l)
                t.progress = 0.0f
                t.speed = 1.0f
                t.fill(world.money)
                world.trains = t :: world.trains
                world.money.value -= 500
                display_train_list
                new_train_dialog.close()
              }
              else
              {
                error.text = "Not enough money!"
              }
            }
          }
        }
        contents += error
        contents += Button("Quit") { new_train_dialog.close() }
      }
    }
  }

  info_left.contents += train_list
  info_left.contents += train_info
  info_left.contents += city_info
  info_left.contents += Swing.Glue
  info_left.contents += new_train

  display_train_list
  display_train
  display_city

  var time_elapsed = 0

  def display_train_list()
  {
    train_list.list = new ComboBox(world.trains)
    train_list.list.maximumSize = new Dimension(300, 30)
    train_list.list.minimumSize = new Dimension(300, 30)
    train_list.list.preferredSize = new Dimension(300, 30)

    train_list.contents.clear
    train_list.contents += train_list.list
    train_list.revalidate
    train_list.repaint
  }

  def display_train()
  {
    val train_opt = world.trains.find((train: Train) => train.id == train_list.list.selection.item.id)
    train_opt match {
      case None => {
        train_info.line.text = " "
        train_info.passengers.text = " "
        train_info.progress.text = " "
        train_info.speed.text = " "
        train_info.border = Swing.TitledBorder(Swing.LineBorder(Color.BLACK, 2), "No train selected")
      }
      case Some(train) => {
        if (train.orientation)
          train_info.line.text = train.line.city1.name + " → " + train.line.city2.name
        else
          train_info.line.text = train.line.city2.name + " → " + train.line.city1.name
        train_info.passengers.text = "%d/%d passengers".format(train.passengers, train.max_passengers)
        train_info.progress.text = "%.2fkm/%.2fkm".format(train.progress, train.line.length)
        train_info.speed.text = (train.speed * 300).toString + "km/h"
        train_info.border = Swing.TitledBorder(Swing.LineBorder(Color.BLACK, 2), "Train #" + train.id)
      }
    }
  }

  def display_city()
  {
    val city_opt =  world.cities.find((city: City) => city.id == world_canvas.city_info_id)
    city_opt match {
      case None => {
        city_info.city_name.text = " "
        city_info.population.text = " "
        city_info.waiting_passengers.text = " "
        city_info.border = Swing.TitledBorder(Swing.LineBorder(Color.BLUE, 2), "No city selected")
      }
      case Some(city) => {
        city_info.city_name.text = city.name
        city_info.population.text = "Population: " + city.population.toString
        city_info.waiting_passengers.text = "Waiting passengers: %.0f".format(city.waiting_passengers)
        city_info.border = Swing.TitledBorder(Swing.LineBorder(Color.BLUE, 2), "City: " + city.name)
      }
    }
  }

  def update()
  {
    time_elapsed += 1

    world.tick

    money_label.text = "Money: %.2f₿".format(world.money.value)
    val days = ((time_elapsed / 300) / 24).toFloat
    val hours = ((time_elapsed / 300) % 24).toFloat
    val minutes = ((time_elapsed / 5) % 60).toFloat
    time_label.text = "Time: %.0f days %2.0f hours %2.0f minutes".format(days, hours, minutes)

    display_train
    display_city

    infos_up.revalidate
    infos_up.repaint
    world_canvas.repaint
  }

  mainFrame = new BorderPanel {
      layout(infos_up) = BorderPanel.Position.North
      layout(world_canvas) = BorderPanel.Position.Center
      layout(info_left) = BorderPanel.Position.West
      layout(back_to_menu) = BorderPanel.Position.South
  }
}
