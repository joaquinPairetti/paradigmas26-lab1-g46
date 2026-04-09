object Main {

  type Subscription = (String, String)
  type Post = (String, String, String, String)

  def main(args: Array[String]): Unit = {
    val header = s"Reddit Post Parser\n${"=" * 40}"

    val subscriptions: List[Subscription] = FileIO.readSubscriptions()

    val allPosts: List[(String, List[Post])] = subscriptions.map { case (name, url) =>
      println(s"Fetching posts from: $url")
      val posts = FileIO.downloadJson(url)
      (name, posts)
    }

    val output = allPosts
      .map { case (name, posts) => Formatters.formatSubscription(name, posts) }
      .mkString("\n")

    println(output)
  }
}
