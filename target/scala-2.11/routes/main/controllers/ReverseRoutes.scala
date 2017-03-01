
// @GENERATOR:play-routes-compiler
// @SOURCE:/home/wyu/workspace-luna/ie-validation-github-dev-BackUp-022417/conf/routes
// @DATE:Tue Feb 28 10:30:01 PST 2017

import play.api.mvc.{ QueryStringBindable, PathBindable, Call, JavascriptLiteral }
import play.core.routing.{ HandlerDef, ReverseRouteContext, queryString, dynamicString }


import _root_.controllers.Assets.Asset
import _root_.play.libs.F

// @LINE:6
package controllers {

  // @LINE:45
  class ReverseAssets(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:45
    def at(file:String): Call = {
      implicit val _rrc = new ReverseRouteContext(Map(("path", "/public")))
      Call("GET", _prefix + { _defaultPrefix } + "assets/" + implicitly[PathBindable[String]].unbind("file", file))
    }
  
  }

  // @LINE:6
  class ReverseApplication(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:33
    def getFrameInstanceID(colNames:String, colValues:String): Call = {
      import ReverseRouteContext.empty
      Call("GET", _prefix + { _defaultPrefix } + "get/frameinstance/id/" + implicitly[PathBindable[String]].unbind("colNames", dynamicString(colNames)) + "/" + implicitly[PathBindable[String]].unbind("colValues", dynamicString(colValues)))
    }
  
    // @LINE:27
    def getDocumentHistory(): Call = {
      import ReverseRouteContext.empty
      Call("GET", _prefix + { _defaultPrefix } + "get/history")
    }
  
    // @LINE:41
    def annotIndex(): Call = {
      import ReverseRouteContext.empty
      Call("GET", _prefix + { _defaultPrefix } + "annotation")
    }
  
    // @LINE:28
    def clearDocumentHistory(): Call = {
      import ReverseRouteContext.empty
      Call("GET", _prefix + { _defaultPrefix } + "clear/history")
    }
  
    // @LINE:20
    def loadProject(projID:Int): Call = {
      import ReverseRouteContext.empty
      Call("GET", _prefix + { _defaultPrefix } + "get/project/" + implicitly[PathBindable[Int]].unbind("projID", projID))
    }
  
    // @LINE:38
    def frameInstanceValidated(): Call = {
      import ReverseRouteContext.empty
      Call("GET", _prefix + { _defaultPrefix } + "validated")
    }
  
    // @LINE:24
    def clearAll(): Call = {
      import ReverseRouteContext.empty
      Call("GET", _prefix + { _defaultPrefix } + "clear")
    }
  
    // @LINE:31
    def getSlotValues(annotType:String): Call = {
      import ReverseRouteContext.empty
      Call("GET", _prefix + { _defaultPrefix } + "get/slotvalues/" + implicitly[PathBindable[String]].unbind("annotType", dynamicString(annotType)))
    }
  
    // @LINE:17
    def removeSection(sectionName:String, repeatIndex:Int): Call = {
      import ReverseRouteContext.empty
      Call("GET", _prefix + { _defaultPrefix } + "remove/section/" + implicitly[PathBindable[String]].unbind("sectionName", dynamicString(sectionName)) + "/" + implicitly[PathBindable[Int]].unbind("repeatIndex", repeatIndex))
    }
  
    // @LINE:21
    def loadFrameInstance(frameInstanceID:Int): Call = {
      import ReverseRouteContext.empty
      Call("GET", _prefix + { _defaultPrefix } + "get/frameinstance/" + implicitly[PathBindable[Int]].unbind("frameInstanceID", frameInstanceID))
    }
  
    // @LINE:15
    def getCRF(crfID:Int): Call = {
      import ReverseRouteContext.empty
      Call("GET", _prefix + { _defaultPrefix } + "get/crf/" + implicitly[PathBindable[Int]].unbind("crfID", crfID))
    }
  
    // @LINE:23
    def clearElement(elementID:String, htmlID:String): Call = {
      import ReverseRouteContext.empty
      Call("GET", _prefix + { _defaultPrefix } + "clear/element/" + implicitly[PathBindable[String]].unbind("elementID", dynamicString(elementID)) + "/" + implicitly[PathBindable[String]].unbind("htmlID", dynamicString(htmlID)))
    }
  
    // @LINE:37
    def getHistory(): Call = {
      import ReverseRouteContext.empty
      Call("GET", _prefix + { _defaultPrefix } + "undo")
    }
  
    // @LINE:16
    def addSection(sectionName:String): Call = {
      import ReverseRouteContext.empty
      Call("GET", _prefix + { _defaultPrefix } + "add/section/" + implicitly[PathBindable[String]].unbind("sectionName", dynamicString(sectionName)))
    }
  
    // @LINE:18
    def addElement(htmlID:String): Call = {
      import ReverseRouteContext.empty
      Call("GET", _prefix + { _defaultPrefix } + "add/element/" + implicitly[PathBindable[String]].unbind("htmlID", dynamicString(htmlID)))
    }
  
    // @LINE:7
    def logout(): Call = {
      import ReverseRouteContext.empty
      Call("GET", _prefix + { _defaultPrefix } + "logout")
    }
  
    // @LINE:26
    def addDocumentHistory(): Call = {
      import ReverseRouteContext.empty
      Call("POST", _prefix + { _defaultPrefix } + "add/history")
    }
  
    // @LINE:30
    def getDocumentAnnotations(): Call = {
      import ReverseRouteContext.empty
      Call("POST", _prefix + { _defaultPrefix } + "get/annotations")
    }
  
    // @LINE:32
    def getFrameData(frameInstanceID:Int): Call = {
      import ReverseRouteContext.empty
      Call("GET", _prefix + { _defaultPrefix } + "get/framedata/" + implicitly[PathBindable[Int]].unbind("frameInstanceID", frameInstanceID))
    }
  
    // @LINE:13
    def loadApp(colNames:String, colValues:String): Call = {
      import ReverseRouteContext.empty
      Call("GET", _prefix + { _defaultPrefix } + "get/frameinstance/" + implicitly[PathBindable[String]].unbind("colNames", dynamicString(colNames)) + "/" + implicitly[PathBindable[String]].unbind("colValues", dynamicString(colValues)))
    }
  
    // @LINE:14
    def getDocument(): Call = {
      import ReverseRouteContext.empty
      Call("POST", _prefix + { _defaultPrefix } + "get/document")
    }
  
    // @LINE:19
    def removeElement(elementID:String, htmlID:String): Call = {
      import ReverseRouteContext.empty
      Call("GET", _prefix + { _defaultPrefix } + "remove/element/" + implicitly[PathBindable[String]].unbind("elementID", dynamicString(elementID)) + "/" + implicitly[PathBindable[String]].unbind("htmlID", dynamicString(htmlID)))
    }
  
    // @LINE:29
    def fillSlot(): Call = {
      import ReverseRouteContext.empty
      Call("POST", _prefix + { _defaultPrefix } + "fillslot")
    }
  
    // @LINE:25
    def clearValue(htmlID:String): Call = {
      import ReverseRouteContext.empty
      Call("GET", _prefix + { _defaultPrefix } + "clear/value/" + implicitly[PathBindable[String]].unbind("htmlID", dynamicString(htmlID)))
    }
  
    // @LINE:36
    def authenticate(): Call = {
      import ReverseRouteContext.empty
      Call("POST", _prefix)
    }
  
    // @LINE:12
    def index(): Call = {
      import ReverseRouteContext.empty
      Call("GET", _prefix + { _defaultPrefix } + "get/index")
    }
  
    // @LINE:22
    def addAnnotation(): Call = {
      import ReverseRouteContext.empty
      Call("POST", _prefix + { _defaultPrefix } + "add/annotation")
    }
  
    // @LINE:6
    def login(): Call = {
      import ReverseRouteContext.empty
      Call("GET", _prefix)
    }
  
  }


}