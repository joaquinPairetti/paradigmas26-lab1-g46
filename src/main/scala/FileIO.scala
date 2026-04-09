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
  def downloadJson(subredditUrl: String): List[Post] = {
      //stream
      val source = Source.fromURL(subredditUrl)
      
      try {
        // stream -> string
        val data = source.mkString
        // string->json
        val jsonData = parse(data) 
        /**
        * En base a una un objeto que contiene todos los post (children):
        * obtengo una lista nativa de scala de Jvalues accesibles a map
        * JArray (un [] en JSON) -> List[JValue]
        **/
        val jsonPost = (jsonData \ "data" \ "children").children

        jsonPost.map { post =>
        // Entro al post
        val data = post \ "data"
        
        // Extraccion de campos 
        val subreddit = (data \ "subreddit").extract[String]
        val title     = (data \ "title").extract[String]
        val selftext  = (data \ "selftext").extract[String]
        
        // Transformación de la fecha (Canonicalización)
        val createdUtc = (data \ "created_utc").extract[Double].toLong
        val date       = TextProcessing.formatDateFromUTC(createdUtc)

        (subreddit, title, selftext, date)
        }
      } finally {
        source.close()
      }
      
    }

  }