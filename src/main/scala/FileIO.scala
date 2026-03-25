import scala.io.Source
import org.json4s._
import org.json4s.jackson.JsonMethods._

object FileIO {
  type Subscription = (String, String)

  def readSubscriptions(): List[Subscription] = {
    
    implicit val formats: DefaultFormats.type = DefaultFormats
    
    val source = Source.fromFile("subscriptions.json")
    try {
      val jsonTexto = source.mkString
      
      val jsonJValue = parse(jsonTexto)
      
      val listaMapas = jsonJValue.extract[List[Map[String, String]]]
      
      listaMapas.map(m => (m("name"), m("url")))

    } finally {
      source.close()
    }
  }
}