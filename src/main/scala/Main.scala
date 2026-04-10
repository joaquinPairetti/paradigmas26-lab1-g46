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
          val filteredPosts = TextProcessing.filterValidPosts(listaDePosts)
          val top5 = filteredPosts.take(5)
          val wordFreq = TextProcessing.wordFrequency(filteredPosts).toList.sortBy(-_._2).take(5)
          val totalScore = TextProcessing.sumScore(filteredPosts)

          Formatters.formatReport(name, totalScore, top5, wordFreq) 
        case None => 
          s" ERROR: No se pudo descargar '$name' "
      }
    }
    println(allPosts.mkString("\n\n"))
  }
}
