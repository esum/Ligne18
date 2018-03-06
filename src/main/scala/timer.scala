class Timer(interval: Int, op: => Unit, repeats: Boolean = true)
{
  val timeOut = new javax.swing.AbstractAction() {
    def actionPerformed(e : java.awt.event.ActionEvent) = op
  }
  val t = new javax.swing.Timer(interval, timeOut)
  t.setRepeats(repeats)

  def start() {
    t.start
  }

  def stop() {
    t.stop
  }
}
