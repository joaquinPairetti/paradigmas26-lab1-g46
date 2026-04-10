object Main {
  type Subscription = (String, String)
  type Post         = (String, String, String, String, Int)

  def main(args: Array[String]): Unit = {
    println(s"Reddit Post Parser\n${"=" * 40}")

    val subscriptions: List[Subscription] = FileIO.readSubscriptions("subscriptions.json").getOrElse(List())
    
    val allPosts: List[String] = subscriptions.map { case (name: String, url: String) =>
      println(s"Fetching posts from: $url")
      
      val maybePosts: Option[List[Post]] = FileIO.downloadFeed(url)
      
      maybePosts match {
        case Some(listaDePosts) => 
          Formatters.formatSubscription(name, listaDePosts)
        
        case None => 
          s" ERROR: No se pudo descargar '$name' "
      }
    }
    println(allPosts)
  }
}
