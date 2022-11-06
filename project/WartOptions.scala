import wartremover.Wart

object WartOptions {

  val default = Seq(
    Wart.Equals,
    Wart.StringPlusAny,
  )

}
