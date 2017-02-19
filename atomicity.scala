private var uidCount = 0L

def getUniqueId(): Long = {
  uidCount += 1
  uidCount
}

def startThread() = {
  val t = new Thread {
    override def run() {
	val uids = for ( i <- 0 until 10) yield getUniqueId()
	println(uids)
	}
  }
  t.start()
  t
}

//the output number is not unique right now
startThread(); startThread()

//in order to solve it, we use synchronized blck
//code blck after a synchromized call on an object x is never executed
//by two threads at the same time

//jbm inplements it by creating a monitor on each object
//at any specific time, a monitor can only be used by one thread
//here's how to use it:
//note that the synchronized method must be invoked on an instance of some object

private val x = new AnyRef {}
private val uidCount = 0L
def getUniqueId(): Long = x.synchronized {
  uidCount += 1
  uidCount
}

def startThread() = {
  val t = new Thread {
    override def run() {
        val uids = for ( i <- 0 until 10) yield getUniqueId()
        println(uids)
        }
  }
  t.start()
  t
}

