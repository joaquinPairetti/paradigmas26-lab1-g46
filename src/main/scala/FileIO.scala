import scala.io.Source
import org.json4s._
import org.json4s.jackson.JsonMethods._

object FileIO {
  // Definición de un alias para representar una suscripción como un par de Strings (Nombre, URL)
  type Subscription = (String, String)
    // (subreddit, title, selftext,)
  type Post = (String, String, String, String)

  /**
   * Lee el archivo 'subscriptions.json' y lo transforma en una lista de tuplas.
   * @return Una lista de pares (nombre, url).
   */
  def readSubscriptions(path:String): Option[List[Subscription]]= {
    try {
      implicit val formats: DefaultFormats.type = DefaultFormats
      val source = Source.fromFile(path)
      val content = source.mkString
      source.close()
      val json = parse(content)
      val items = json.extract[List[Map[String, String]]]
      val subscriptions = items.map {item => (item("name"), item("url"))}
      Some(subscriptions)
    } catch {
      case e: Exception => None
    }
  }

  def downloadFeed(subredditUrl: String): Option[List[Post]] = {
  // Usamos Try para capturar cualquier error de red o de parseo
  scala.util.Try {
    val source = Source.fromURL(subredditUrl)
    implicit val formats: DefaultFormats.type = DefaultFormats

    try {
      val data = source.mkString
      val jsonData = parse(data) 
      
      val jsonPost = (jsonData \ "data" \ "children").children

      jsonPost.map { post =>
        val data = post \ "data"
        
        val subreddit = (data \ "subreddit").extract[String]
        val title     = (data \ "title").extract[String]
        val selftext  = (data \ "selftext").extract[String]
        
        val createdUtc = (data \ "created_utc").extract[Double].toLong
        val date       = TextProcessing.formatDateFromUTC(createdUtc)

        (subreddit, title, selftext, date)
      }
    } finally {
      source.close()
    }
  }.toOption // Si todo salió bien, devuelve Some(lista). Si hubo error, devuelve None.
  }
}