
// @GENERATOR:play-routes-compiler
// @SOURCE:/home/wyu/workspace-luna/ie-validation-github-dev-BackUp-022417/conf/routes
// @DATE:Wed Mar 01 09:29:13 PST 2017


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
