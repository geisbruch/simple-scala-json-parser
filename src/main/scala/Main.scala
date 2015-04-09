import com.simplejsonparser._
import com.simplejsonparser.SimpleJsonParser

object Main {

  def main(args: Array[String]) {
	  
    val m = new SimpleJsonParser()
    			.parseJsonObject("""{"a":123453323,"b":"ds"}""");
	val j = new J(m)
	println(j("application"))
  }
  
  
  
}