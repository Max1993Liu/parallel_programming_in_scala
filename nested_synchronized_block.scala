class Account(private var amount: Int = 0) {
  def transfer(target: Account, n: Int) = {
    this.synchronized {
      target.synchronized {
	this.amount -= n
	target.amount += n
	}
      }
}

def startThread(a: Account, b: Account, n:Int) = {
  val t = new Thread {
    override def run() {
      for ( i <- 0 until n) {
	a.transfer(b, 1)
	}
      }
    }
  t.start()
  t
}

val a1 = new Account(500)
val b1 = new Account(700)

val t = startThread(a1, a2, 300)
val s = startThread(a2, a1, 200)
t.join()
s.join()

//here deadlock happens: two or more threads compete for resources
//and wait for each to finish without releasing the already acquired resources

//solution 1: assign uids

private uid = 0L

private def getUniqueUid = {
  uid += 1
  uid
}


class Account(private var amount: Int = 0) {
  
  val uid = getUniqueId()

  private def lockAndTransfer(target: Account, n: Int) = 
    this.synchronized {
      target.synchronized{     
 	target.amount += n
        this.amount -= n
        }
      }

  def transfer(target: Account, n: Int) = {
    if (this.uid < target.uid) this.lockAndTransfer(target, n)
    else target.lockAndTransfer(this, -n)
}

