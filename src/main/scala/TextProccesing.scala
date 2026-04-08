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
}