class Train(train_line :TrainLine, train_orientation :Boolean = true)
{
    var line = train_line
    var orientation = train_orientation
    var passengers = 0
    var max_passengers = 1
    var progress = 0.0f
    var speed = 1.0f
}
