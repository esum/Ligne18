class Vector(x_v :Float = 0, y_v :Float = 0)
{
    var x : Float = x_v
    var y : Float = y_v

    def +(b :Vector) :Vector =
    {
        return new Vector(x + b.x, y + b.y)
    }

    def -(b :Vector) :Vector =
    {
        return new Vector(x - b.x, y - b.y)
    }

    def length() :Float =
    {
        return Math.sqrt((x*x + y*y).toDouble).toFloat
    }
}