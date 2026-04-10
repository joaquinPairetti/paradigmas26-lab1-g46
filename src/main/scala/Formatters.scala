
object Formatters {

  type Post = (String, String, String, String, Int, String)


  // Pure function to format posts from a subscription
  def formatSubscription(url: String, posts: List[Post]): String = {
    val header = s"\n${"=" * 80}\nPosts from: $url \n${"=" * 80}"

    val formattedPosts = posts.map {case (subreddit, title, selftext, date, score, url) =>
    s"\n${"-" * 160} \nr/$subreddit - $date \n   $title  \n${"-" * 160} \n \n $selftext"
    }.mkString

    header + formattedPosts

  }

  def formatReport(name: String, totalScore: Int, top5: List[Post], wordFreq: List[(String, Int)]): String = {
  val postsText = top5.map { case (_, title, _, date, _, url) => s"   * [$date] $title\n $url" }.mkString("\n")

  val wordsText = wordFreq
      .map { case (word, count) => s"  - $word: $count" }
      .mkString("\n")
  
  s"""
     |Subreddit: $name
     |Score Total: $totalScore
     |
     |Top 5 Posts:
     |$postsText
     |
     |Top 5 words:
     |$wordsText
  """.stripMargin
}
}
