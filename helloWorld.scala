class HelloThread extends Thread {
  override def run() {
    println("hello")
    println("world")
  }
}

def main() {
  val t = new HelloThread()
  val s = new HelloThread()
  t.start()
  s.start()
  t.join()
  s.join()
}

main()

