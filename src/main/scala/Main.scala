object Main {
  type Subscription = (String, String)
  type Post         = (String, String, String, String, Int, String)

  def main(args: Array[String]): Unit = {
    println(s"Reddit Post Parser\n${"=" * 40}")

    val subscriptions: List[Subscription] = FileIO.readSubscriptions("subscriptions.json").getOrElse(List())
    
    val allPosts: List[String] = subscriptions.map { case (name: String, url: String) =>
      println(s"Fetching posts from: $url")
      
      val maybePosts: Option[List[Post]] = FileIO.downloadFeed(url)
      
      maybePosts match {
        case Some(listaDePosts) => 
          val top5 = listaDePosts.take(5)
          val wordFreq = TextProcessing.wordFrequency(listaDePosts)
          val totalScore = TextProcessing.sumScore(listaDePosts)

          Formatters.formatReport(name, totalScore, top5) 
        case None => 
          s" ERROR: No se pudo descargar '$name' "
      }
    }
    println(allPosts)
  }
}
