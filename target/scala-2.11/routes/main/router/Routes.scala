
// @GENERATOR:play-routes-compiler
// @SOURCE:/home/wyu/workspace-luna/ie-validation-github-dev-BackUp-022417/conf/routes
// @DATE:Thu Apr 06 15:26:00 PDT 2017

package router

import play.core.routing._
import play.core.routing.HandlerInvokerFactory._
import play.core.j._

import play.api.mvc._

import _root_.controllers.Assets.Asset
import _root_.play.libs.F

class Routes(
  override val errorHandler: play.api.http.HttpErrorHandler, 
  // @LINE:6
  Application_1: controllers.Application,
  // @LINE:45
  Assets_0: controllers.Assets,
  val prefix: String
) extends GeneratedRouter {

   @javax.inject.Inject()
   def this(errorHandler: play.api.http.HttpErrorHandler,
    // @LINE:6
    Application_1: controllers.Application,
    // @LINE:45
    Assets_0: controllers.Assets
  ) = this(errorHandler, Application_1, Assets_0, "/")

  import ReverseRouteContext.empty

  def withPrefix(prefix: String): Routes = {
    router.RoutesPrefix.setPrefix(prefix)
    new Routes(errorHandler, Application_1, Assets_0, prefix)
  }

  private[this] val defaultPrefix: String = {
    if (this.prefix.endsWith("/")) "" else "/"
  }

  def documentation = List(
    ("""GET""", this.prefix, """controllers.Application.login()"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """logout""", """controllers.Application.logout()"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """get/index""", """controllers.Application.index()"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """get/frameinstance/$colNames<[^/]+>/$colValues<[^/]+>""", """controllers.Application.loadApp(colNames:String, colValues:String)"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """get/document""", """controllers.Application.getDocument()"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """get/crf/$crfID<[^/]+>""", """controllers.Application.getCRF(crfID:Int)"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """add/section/$sectionName<[^/]+>""", """controllers.Application.addSection(sectionName:String)"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """remove/section/$sectionName<[^/]+>/$repeatIndex<[^/]+>""", """controllers.Application.removeSection(sectionName:String, repeatIndex:Int)"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """add/element/$htmlID<[^/]+>""", """controllers.Application.addElement(htmlID:String)"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """remove/element/$elementID<[^/]+>/$htmlID<[^/]+>""", """controllers.Application.removeElement(elementID:String, htmlID:String)"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """get/project/$projID<[^/]+>""", """controllers.Application.loadProject(projID:Int)"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """get/frameinstance/$frameInstanceID<[^/]+>""", """controllers.Application.loadFrameInstance(frameInstanceID:Int)"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """add/annotation""", """controllers.Application.addAnnotation()"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """clear/element/$elementID<[^/]+>/$htmlID<[^/]+>""", """controllers.Application.clearElement(elementID:String, htmlID:String)"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """clear""", """controllers.Application.clearAll()"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """clear/value/$htmlID<[^/]+>""", """controllers.Application.clearValue(htmlID:String)"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """add/history""", """controllers.Application.addDocumentHistory()"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """get/history""", """controllers.Application.getDocumentHistory()"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """clear/history""", """controllers.Application.clearDocumentHistory()"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """fillslot""", """controllers.Application.fillSlot()"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """get/annotations""", """controllers.Application.getDocumentAnnotations()"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """get/slotvalues/$annotType<[^/]+>""", """controllers.Application.getSlotValues(annotType:String)"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """get/framedata/$frameInstanceID<[^/]+>""", """controllers.Application.getFrameData(frameInstanceID:Int)"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """get/frameinstance/id/$colNames<[^/]+>/$colValues<[^/]+>""", """controllers.Application.getFrameInstanceID(colNames:String, colValues:String)"""),
    ("""POST""", this.prefix, """controllers.Application.authenticate()"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """undo""", """controllers.Application.getHistory()"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """validated""", """controllers.Application.frameInstanceValidated()"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """annotation""", """controllers.Application.annotIndex()"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """assets/$file<.+>""", """controllers.Assets.at(path:String = "/public", file:String)"""),
    Nil
  ).foldLeft(List.empty[(String,String,String)]) { (s,e) => e.asInstanceOf[Any] match {
    case r @ (_,_,_) => s :+ r.asInstanceOf[(String,String,String)]
    case l => s ++ l.asInstanceOf[List[(String,String,String)]]
  }}


  // @LINE:6
  private[this] lazy val controllers_Application_login0_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix)))
  )
  private[this] lazy val controllers_Application_login0_invoker = createInvoker(
    Application_1.login(),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.Application",
      "login",
      Nil,
      "GET",
      """new code""",
      this.prefix + """"""
    )
  )

  // @LINE:7
  private[this] lazy val controllers_Application_logout1_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("logout")))
  )
  private[this] lazy val controllers_Application_logout1_invoker = createInvoker(
    Application_1.logout(),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.Application",
      "logout",
      Nil,
      "GET",
      """""",
      this.prefix + """logout"""
    )
  )

  // @LINE:12
  private[this] lazy val controllers_Application_index2_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("get/index")))
  )
  private[this] lazy val controllers_Application_index2_invoker = createInvoker(
    Application_1.index(),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.Application",
      "index",
      Nil,
      "GET",
      """ Home page""",
      this.prefix + """get/index"""
    )
  )

  // @LINE:13
  private[this] lazy val controllers_Application_loadApp3_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("get/frameinstance/"), DynamicPart("colNames", """[^/]+""",true), StaticPart("/"), DynamicPart("colValues", """[^/]+""",true)))
  )
  private[this] lazy val controllers_Application_loadApp3_invoker = createInvoker(
    Application_1.loadApp(fakeValue[String], fakeValue[String]),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.Application",
      "loadApp",
      Seq(classOf[String], classOf[String]),
      "GET",
      """""",
      this.prefix + """get/frameinstance/$colNames<[^/]+>/$colValues<[^/]+>"""
    )
  )

  // @LINE:14
  private[this] lazy val controllers_Application_getDocument4_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("get/document")))
  )
  private[this] lazy val controllers_Application_getDocument4_invoker = createInvoker(
    Application_1.getDocument(),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.Application",
      "getDocument",
      Nil,
      "POST",
      """""",
      this.prefix + """get/document"""
    )
  )

  // @LINE:15
  private[this] lazy val controllers_Application_getCRF5_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("get/crf/"), DynamicPart("crfID", """[^/]+""",true)))
  )
  private[this] lazy val controllers_Application_getCRF5_invoker = createInvoker(
    Application_1.getCRF(fakeValue[Int]),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.Application",
      "getCRF",
      Seq(classOf[Int]),
      "GET",
      """""",
      this.prefix + """get/crf/$crfID<[^/]+>"""
    )
  )

  // @LINE:16
  private[this] lazy val controllers_Application_addSection6_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("add/section/"), DynamicPart("sectionName", """[^/]+""",true)))
  )
  private[this] lazy val controllers_Application_addSection6_invoker = createInvoker(
    Application_1.addSection(fakeValue[String]),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.Application",
      "addSection",
      Seq(classOf[String]),
      "GET",
      """""",
      this.prefix + """add/section/$sectionName<[^/]+>"""
    )
  )

  // @LINE:17
  private[this] lazy val controllers_Application_removeSection7_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("remove/section/"), DynamicPart("sectionName", """[^/]+""",true), StaticPart("/"), DynamicPart("repeatIndex", """[^/]+""",true)))
  )
  private[this] lazy val controllers_Application_removeSection7_invoker = createInvoker(
    Application_1.removeSection(fakeValue[String], fakeValue[Int]),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.Application",
      "removeSection",
      Seq(classOf[String], classOf[Int]),
      "GET",
      """""",
      this.prefix + """remove/section/$sectionName<[^/]+>/$repeatIndex<[^/]+>"""
    )
  )

  // @LINE:18
  private[this] lazy val controllers_Application_addElement8_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("add/element/"), DynamicPart("htmlID", """[^/]+""",true)))
  )
  private[this] lazy val controllers_Application_addElement8_invoker = createInvoker(
    Application_1.addElement(fakeValue[String]),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.Application",
      "addElement",
      Seq(classOf[String]),
      "GET",
      """""",
      this.prefix + """add/element/$htmlID<[^/]+>"""
    )
  )

  // @LINE:19
  private[this] lazy val controllers_Application_removeElement9_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("remove/element/"), DynamicPart("elementID", """[^/]+""",true), StaticPart("/"), DynamicPart("htmlID", """[^/]+""",true)))
  )
  private[this] lazy val controllers_Application_removeElement9_invoker = createInvoker(
    Application_1.removeElement(fakeValue[String], fakeValue[String]),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.Application",
      "removeElement",
      Seq(classOf[String], classOf[String]),
      "GET",
      """""",
      this.prefix + """remove/element/$elementID<[^/]+>/$htmlID<[^/]+>"""
    )
  )

  // @LINE:20
  private[this] lazy val controllers_Application_loadProject10_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("get/project/"), DynamicPart("projID", """[^/]+""",true)))
  )
  private[this] lazy val controllers_Application_loadProject10_invoker = createInvoker(
    Application_1.loadProject(fakeValue[Int]),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.Application",
      "loadProject",
      Seq(classOf[Int]),
      "GET",
      """""",
      this.prefix + """get/project/$projID<[^/]+>"""
    )
  )

  // @LINE:21
  private[this] lazy val controllers_Application_loadFrameInstance11_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("get/frameinstance/"), DynamicPart("frameInstanceID", """[^/]+""",true)))
  )
  private[this] lazy val controllers_Application_loadFrameInstance11_invoker = createInvoker(
    Application_1.loadFrameInstance(fakeValue[Int]),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.Application",
      "loadFrameInstance",
      Seq(classOf[Int]),
      "GET",
      """""",
      this.prefix + """get/frameinstance/$frameInstanceID<[^/]+>"""
    )
  )

  // @LINE:22
  private[this] lazy val controllers_Application_addAnnotation12_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("add/annotation")))
  )
  private[this] lazy val controllers_Application_addAnnotation12_invoker = createInvoker(
    Application_1.addAnnotation(),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.Application",
      "addAnnotation",
      Nil,
      "POST",
      """""",
      this.prefix + """add/annotation"""
    )
  )

  // @LINE:23
  private[this] lazy val controllers_Application_clearElement13_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("clear/element/"), DynamicPart("elementID", """[^/]+""",true), StaticPart("/"), DynamicPart("htmlID", """[^/]+""",true)))
  )
  private[this] lazy val controllers_Application_clearElement13_invoker = createInvoker(
    Application_1.clearElement(fakeValue[String], fakeValue[String]),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.Application",
      "clearElement",
      Seq(classOf[String], classOf[String]),
      "GET",
      """""",
      this.prefix + """clear/element/$elementID<[^/]+>/$htmlID<[^/]+>"""
    )
  )

  // @LINE:24
  private[this] lazy val controllers_Application_clearAll14_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("clear")))
  )
  private[this] lazy val controllers_Application_clearAll14_invoker = createInvoker(
    Application_1.clearAll(),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.Application",
      "clearAll",
      Nil,
      "GET",
      """""",
      this.prefix + """clear"""
    )
  )

  // @LINE:25
  private[this] lazy val controllers_Application_clearValue15_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("clear/value/"), DynamicPart("htmlID", """[^/]+""",true)))
  )
  private[this] lazy val controllers_Application_clearValue15_invoker = createInvoker(
    Application_1.clearValue(fakeValue[String]),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.Application",
      "clearValue",
      Seq(classOf[String]),
      "GET",
      """""",
      this.prefix + """clear/value/$htmlID<[^/]+>"""
    )
  )

  // @LINE:26
  private[this] lazy val controllers_Application_addDocumentHistory16_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("add/history")))
  )
  private[this] lazy val controllers_Application_addDocumentHistory16_invoker = createInvoker(
    Application_1.addDocumentHistory(),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.Application",
      "addDocumentHistory",
      Nil,
      "POST",
      """""",
      this.prefix + """add/history"""
    )
  )

  // @LINE:27
  private[this] lazy val controllers_Application_getDocumentHistory17_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("get/history")))
  )
  private[this] lazy val controllers_Application_getDocumentHistory17_invoker = createInvoker(
    Application_1.getDocumentHistory(),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.Application",
      "getDocumentHistory",
      Nil,
      "GET",
      """""",
      this.prefix + """get/history"""
    )
  )

  // @LINE:28
  private[this] lazy val controllers_Application_clearDocumentHistory18_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("clear/history")))
  )
  private[this] lazy val controllers_Application_clearDocumentHistory18_invoker = createInvoker(
    Application_1.clearDocumentHistory(),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.Application",
      "clearDocumentHistory",
      Nil,
      "GET",
      """""",
      this.prefix + """clear/history"""
    )
  )

  // @LINE:29
  private[this] lazy val controllers_Application_fillSlot19_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("fillslot")))
  )
  private[this] lazy val controllers_Application_fillSlot19_invoker = createInvoker(
    Application_1.fillSlot(),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.Application",
      "fillSlot",
      Nil,
      "POST",
      """""",
      this.prefix + """fillslot"""
    )
  )

  // @LINE:30
  private[this] lazy val controllers_Application_getDocumentAnnotations20_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("get/annotations")))
  )
  private[this] lazy val controllers_Application_getDocumentAnnotations20_invoker = createInvoker(
    Application_1.getDocumentAnnotations(),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.Application",
      "getDocumentAnnotations",
      Nil,
      "POST",
      """""",
      this.prefix + """get/annotations"""
    )
  )

  // @LINE:31
  private[this] lazy val controllers_Application_getSlotValues21_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("get/slotvalues/"), DynamicPart("annotType", """[^/]+""",true)))
  )
  private[this] lazy val controllers_Application_getSlotValues21_invoker = createInvoker(
    Application_1.getSlotValues(fakeValue[String]),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.Application",
      "getSlotValues",
      Seq(classOf[String]),
      "GET",
      """""",
      this.prefix + """get/slotvalues/$annotType<[^/]+>"""
    )
  )

  // @LINE:32
  private[this] lazy val controllers_Application_getFrameData22_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("get/framedata/"), DynamicPart("frameInstanceID", """[^/]+""",true)))
  )
  private[this] lazy val controllers_Application_getFrameData22_invoker = createInvoker(
    Application_1.getFrameData(fakeValue[Int]),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.Application",
      "getFrameData",
      Seq(classOf[Int]),
      "GET",
      """""",
      this.prefix + """get/framedata/$frameInstanceID<[^/]+>"""
    )
  )

  // @LINE:33
  private[this] lazy val controllers_Application_getFrameInstanceID23_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("get/frameinstance/id/"), DynamicPart("colNames", """[^/]+""",true), StaticPart("/"), DynamicPart("colValues", """[^/]+""",true)))
  )
  private[this] lazy val controllers_Application_getFrameInstanceID23_invoker = createInvoker(
    Application_1.getFrameInstanceID(fakeValue[String], fakeValue[String]),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.Application",
      "getFrameInstanceID",
      Seq(classOf[String], classOf[String]),
      "GET",
      """""",
      this.prefix + """get/frameinstance/id/$colNames<[^/]+>/$colValues<[^/]+>"""
    )
  )

  // @LINE:36
  private[this] lazy val controllers_Application_authenticate24_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix)))
  )
  private[this] lazy val controllers_Application_authenticate24_invoker = createInvoker(
    Application_1.authenticate(),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.Application",
      "authenticate",
      Nil,
      "POST",
      """new stuff""",
      this.prefix + """"""
    )
  )

  // @LINE:37
  private[this] lazy val controllers_Application_getHistory25_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("undo")))
  )
  private[this] lazy val controllers_Application_getHistory25_invoker = createInvoker(
    Application_1.getHistory(),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.Application",
      "getHistory",
      Nil,
      "GET",
      """""",
      this.prefix + """undo"""
    )
  )

  // @LINE:38
  private[this] lazy val controllers_Application_frameInstanceValidated26_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("validated")))
  )
  private[this] lazy val controllers_Application_frameInstanceValidated26_invoker = createInvoker(
    Application_1.frameInstanceValidated(),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.Application",
      "frameInstanceValidated",
      Nil,
      "GET",
      """""",
      this.prefix + """validated"""
    )
  )

  // @LINE:41
  private[this] lazy val controllers_Application_annotIndex27_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("annotation")))
  )
  private[this] lazy val controllers_Application_annotIndex27_invoker = createInvoker(
    Application_1.annotIndex(),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.Application",
      "annotIndex",
      Nil,
      "GET",
      """ Annotation viewer""",
      this.prefix + """annotation"""
    )
  )

  // @LINE:45
  private[this] lazy val controllers_Assets_at28_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("assets/"), DynamicPart("file", """.+""",false)))
  )
  private[this] lazy val controllers_Assets_at28_invoker = createInvoker(
    Assets_0.at(fakeValue[String], fakeValue[String]),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.Assets",
      "at",
      Seq(classOf[String], classOf[String]),
      "GET",
      """ Map static resources from the /public folder to the /assets URL path""",
      this.prefix + """assets/$file<.+>"""
    )
  )


  def routes: PartialFunction[RequestHeader, Handler] = {
  
    // @LINE:6
    case controllers_Application_login0_route(params) =>
      call { 
        controllers_Application_login0_invoker.call(Application_1.login())
      }
  
    // @LINE:7
    case controllers_Application_logout1_route(params) =>
      call { 
        controllers_Application_logout1_invoker.call(Application_1.logout())
      }
  
    // @LINE:12
    case controllers_Application_index2_route(params) =>
      call { 
        controllers_Application_index2_invoker.call(Application_1.index())
      }
  
    // @LINE:13
    case controllers_Application_loadApp3_route(params) =>
      call(params.fromPath[String]("colNames", None), params.fromPath[String]("colValues", None)) { (colNames, colValues) =>
        controllers_Application_loadApp3_invoker.call(Application_1.loadApp(colNames, colValues))
      }
  
    // @LINE:14
    case controllers_Application_getDocument4_route(params) =>
      call { 
        controllers_Application_getDocument4_invoker.call(Application_1.getDocument())
      }
  
    // @LINE:15
    case controllers_Application_getCRF5_route(params) =>
      call(params.fromPath[Int]("crfID", None)) { (crfID) =>
        controllers_Application_getCRF5_invoker.call(Application_1.getCRF(crfID))
      }
  
    // @LINE:16
    case controllers_Application_addSection6_route(params) =>
      call(params.fromPath[String]("sectionName", None)) { (sectionName) =>
        controllers_Application_addSection6_invoker.call(Application_1.addSection(sectionName))
      }
  
    // @LINE:17
    case controllers_Application_removeSection7_route(params) =>
      call(params.fromPath[String]("sectionName", None), params.fromPath[Int]("repeatIndex", None)) { (sectionName, repeatIndex) =>
        controllers_Application_removeSection7_invoker.call(Application_1.removeSection(sectionName, repeatIndex))
      }
  
    // @LINE:18
    case controllers_Application_addElement8_route(params) =>
      call(params.fromPath[String]("htmlID", None)) { (htmlID) =>
        controllers_Application_addElement8_invoker.call(Application_1.addElement(htmlID))
      }
  
    // @LINE:19
    case controllers_Application_removeElement9_route(params) =>
      call(params.fromPath[String]("elementID", None), params.fromPath[String]("htmlID", None)) { (elementID, htmlID) =>
        controllers_Application_removeElement9_invoker.call(Application_1.removeElement(elementID, htmlID))
      }
  
    // @LINE:20
    case controllers_Application_loadProject10_route(params) =>
      call(params.fromPath[Int]("projID", None)) { (projID) =>
        controllers_Application_loadProject10_invoker.call(Application_1.loadProject(projID))
      }
  
    // @LINE:21
    case controllers_Application_loadFrameInstance11_route(params) =>
      call(params.fromPath[Int]("frameInstanceID", None)) { (frameInstanceID) =>
        controllers_Application_loadFrameInstance11_invoker.call(Application_1.loadFrameInstance(frameInstanceID))
      }
  
    // @LINE:22
    case controllers_Application_addAnnotation12_route(params) =>
      call { 
        controllers_Application_addAnnotation12_invoker.call(Application_1.addAnnotation())
      }
  
    // @LINE:23
    case controllers_Application_clearElement13_route(params) =>
      call(params.fromPath[String]("elementID", None), params.fromPath[String]("htmlID", None)) { (elementID, htmlID) =>
        controllers_Application_clearElement13_invoker.call(Application_1.clearElement(elementID, htmlID))
      }
  
    // @LINE:24
    case controllers_Application_clearAll14_route(params) =>
      call { 
        controllers_Application_clearAll14_invoker.call(Application_1.clearAll())
      }
  
    // @LINE:25
    case controllers_Application_clearValue15_route(params) =>
      call(params.fromPath[String]("htmlID", None)) { (htmlID) =>
        controllers_Application_clearValue15_invoker.call(Application_1.clearValue(htmlID))
      }
  
    // @LINE:26
    case controllers_Application_addDocumentHistory16_route(params) =>
      call { 
        controllers_Application_addDocumentHistory16_invoker.call(Application_1.addDocumentHistory())
      }
  
    // @LINE:27
    case controllers_Application_getDocumentHistory17_route(params) =>
      call { 
        controllers_Application_getDocumentHistory17_invoker.call(Application_1.getDocumentHistory())
      }
  
    // @LINE:28
    case controllers_Application_clearDocumentHistory18_route(params) =>
      call { 
        controllers_Application_clearDocumentHistory18_invoker.call(Application_1.clearDocumentHistory())
      }
  
    // @LINE:29
    case controllers_Application_fillSlot19_route(params) =>
      call { 
        controllers_Application_fillSlot19_invoker.call(Application_1.fillSlot())
      }
  
    // @LINE:30
    case controllers_Application_getDocumentAnnotations20_route(params) =>
      call { 
        controllers_Application_getDocumentAnnotations20_invoker.call(Application_1.getDocumentAnnotations())
      }
  
    // @LINE:31
    case controllers_Application_getSlotValues21_route(params) =>
      call(params.fromPath[String]("annotType", None)) { (annotType) =>
        controllers_Application_getSlotValues21_invoker.call(Application_1.getSlotValues(annotType))
      }
  
    // @LINE:32
    case controllers_Application_getFrameData22_route(params) =>
      call(params.fromPath[Int]("frameInstanceID", None)) { (frameInstanceID) =>
        controllers_Application_getFrameData22_invoker.call(Application_1.getFrameData(frameInstanceID))
      }
  
    // @LINE:33
    case controllers_Application_getFrameInstanceID23_route(params) =>
      call(params.fromPath[String]("colNames", None), params.fromPath[String]("colValues", None)) { (colNames, colValues) =>
        controllers_Application_getFrameInstanceID23_invoker.call(Application_1.getFrameInstanceID(colNames, colValues))
      }
  
    // @LINE:36
    case controllers_Application_authenticate24_route(params) =>
      call { 
        controllers_Application_authenticate24_invoker.call(Application_1.authenticate())
      }
  
    // @LINE:37
    case controllers_Application_getHistory25_route(params) =>
      call { 
        controllers_Application_getHistory25_invoker.call(Application_1.getHistory())
      }
  
    // @LINE:38
    case controllers_Application_frameInstanceValidated26_route(params) =>
      call { 
        controllers_Application_frameInstanceValidated26_invoker.call(Application_1.frameInstanceValidated())
      }
  
    // @LINE:41
    case controllers_Application_annotIndex27_route(params) =>
      call { 
        controllers_Application_annotIndex27_invoker.call(Application_1.annotIndex())
      }
  
    // @LINE:45
    case controllers_Assets_at28_route(params) =>
      call(Param[String]("path", Right("/public")), params.fromPath[String]("file", None)) { (path, file) =>
        controllers_Assets_at28_invoker.call(Assets_0.at(path, file))
      }
  }
}