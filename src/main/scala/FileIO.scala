import scala.io.Source
import org.json4s._
import org.json4s.jackson.JsonMethods._

object FileIO {
  // Definición de un alias para representar una suscripción como un par de Strings (Nombre, URL)
  type Subscription = (String, String)
    // (subreddit, title, selftext,)
  type Post = (String, String, String, String, Int)

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
        
        val subreddit: String = (data \ "subreddit").extractOpt[String].getOrElse("Unknown Subreddit")
        val title: String     = (data \ "title").extractOpt[String].getOrElse("No Title")
        val selftext: String  = (data \ "selftext").extractOpt[String].getOrElse("")

        val createdUtc: Long = (data \ "created_utc").extractOpt[Double].getOrElse(0.0).toLong
        val date: String     = TextProcessing.formatDateFromUTC(createdUtc)

        val score: Int = (data \ "ups").extractOpt[Int].getOrElse(0)

        (subreddit, title, selftext, date, score)
      }
    } finally {
      source.close()
    }
  }.toOption // Si todo salió bien, devuelve Some(lista). Si hubo error, devuelve None.
  }
}