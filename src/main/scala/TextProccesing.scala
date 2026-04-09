object TextProcessing {
  type Post = (String, String, String, String)

  /*ejercicio 2
  * Recibe un numero en formato UTC y devuelve una fecha formateada como "dd-MM-yyyy HH:mm"
  */
  def formatDateFromUTC(utc: Long): String = {
    // Convierte el numero UTC a una fecha legible
    val fecha = java.time.Instant.ofEpochSecond(utc)

    // Convierte la fecha a la zona horaria local
    val fechaLocal = java.time.LocalDateTime.ofInstant(fecha, java.time.ZoneId.systemDefault())

    // Formatea la fecha en el formato "dd-MM-yyyy HH:mm"
    fechaLocal.format(java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"))
  }

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

  /* Ejercicio 5:
  * Recibe una lista de posts y calcula la frecuencia 
  * de palabras que empiezan con mayuscula y que no sean stopwords
  */
  def wordFrequency(posts: List[Post]): Map[String, Int] = {
    val stopwords = Set("the", "about", "above", "after", "again", "against", "all", "am", "an",
                        "and", "any", "are", "aren't", "as", "at", "be", "because", "been",
                        "before", "being", "below", "between", "both", "but", "by", "can't",
                        "cannot", "could", "couldn't", "did", "didn't", "do", "does", "doesn't",
                        "doing", "don't", "down", "during", "each", "few", "for", "from", "further",
                        "had", "hadn't", "has", "hasn't", "have", "haven't", "having", "he", "he'd",
                        "he'll", "he's", "her", "here", "here's", "hers", "herself", "him",
                        "himself", "his", "how", "how's", "i", "i'd", "i'll", "i'm", "i've", "if",
                        "in", "into", "is", "isn't", "it", "it's", "its", "itself", "let's", "me",
                        "more", "most", "mustn't", "my", "myself", "no", "nor", "not", "of", "off",
                        "on", "once", "only", "or", "other", "ought", "our", "ours", "ourselves",
                        "out", "over", "own", "same", "shan't", "she", "she'd", "she'll", "she's",
                        "should", "shouldn't", "so", "some", "such", "than", "that", "that's",
                        "the", "their", "theirs", "them", "themselves", "then", "there", "there's",
                        "these", "they", "they'd", "they'll", "re", "they've", "this", "those",
                        "through", "to", "too", "under", "until", "up", "very", "was", "wasn't",
                        "we", "we'd", "we'll", "we're", "we've", "were", "weren't", "what",
                        "what's", "when", "when's", "where", "where's", "which", "while", "who",
                        "who's", "whom", "why", "why's", "with", "won't", "would",
                        "wouldn't", "you", "you'd", "you'll", "you're", "you've", "your", "yours",
                        "yourself", "yourselves")
    
    // Extraigo todas las palabras de los posts.
    val words = posts.flatMap { case (_, title, selftext, _) => 
      (title + " " + selftext).split("\\W+").toList
    }

    // Filtro las palabras que empiezan con mayuscula y no son stopwords, luego cuento la frecuencia 
    words.filter { word => word.nonEmpty && word(0).isUpper && !stopwords.contains(word.toLowerCase) }
         .groupBy(word => word)
         .map{ case (word, list) => (word, list.length) }
  }
}
