
// @GENERATOR:play-routes-compiler
// @SOURCE:/home/wyu/workspace-luna/ie-validation-github-dev/conf/routes
// @DATE:Fri Feb 24 11:31:46 PST 2017


package router {
  object RoutesPrefix {
    private var _prefix: String = "/"
    def setPrefix(p: String): Unit = {
      _prefix = p
    }
    def prefix: String = _prefix
    val byNamePrefix: Function0[String] = { () => prefix }
  }
}
