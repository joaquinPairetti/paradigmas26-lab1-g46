
object Formatters {

  type Post = (String, String, String, String)


  // Pure function to format posts from a subscription
  def formatSubscription(url: String, posts: List[Post]): String = {
    val header = s"\n${"=" * 80}\nPosts from: $url \n${"=" * 80}"
    val formattedPosts = posts.take(80)
    header + "\n" + formattedPosts
  }
}
