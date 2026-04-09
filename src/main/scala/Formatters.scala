
object Formatters {

  type Post = (String, String, String, String)


  // Pure function to format posts from a subscription
  def formatSubscription(url: String, posts: List[Post]): String = {
    val header = s"\n${"=" * 80}\nPosts from: $url \n${"=" * 80}"

    val formattedPosts = posts.map {case (subreddit, title, selftext, date) =>{
    s"\n${"-" * 160} \nr/$subreddit - $date \n   $title  \n${"-" * 160} \n \n $selftext"
    }.mkString

    header + formattedPosts

  }
}
}