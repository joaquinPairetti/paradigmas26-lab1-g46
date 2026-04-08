object TextProcessing {
  val Post = (String, String, String, String) 
  /*ejercicio 2*/
  def formatDateFromUTC(utc: Long): String = {/*implementar*/}

  /**
   * EJERCICIO 3: Filtrado de posts
   * Recibe la lista sucia de FileIO.scala y devuelve la lista de post sin :
   *  - Post que no tengan texto ("selftext": "").
   *  - Tengan solo espacios ("selftext": "   ").
   *  - No tengan título ("title": "").
   *  - (String, String, String, String) -> (subreddit, title, selftext, date)
   */
  def filterValidPosts(listaPosts: List[Post]): List[Post] = {
    listaPosts.filter { post => post._2.nonEmpty && post._3.trim.nonEmpty }
  }
}