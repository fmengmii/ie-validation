
// @GENERATOR:play-routes-compiler
// @SOURCE:/home/wyu/workspace-luna/ie-validation-github/conf/routes
// @DATE:Fri Feb 17 16:44:52 PST 2017

import play.api.routing.JavaScriptReverseRoute
import play.api.mvc.{ QueryStringBindable, PathBindable, Call, JavascriptLiteral }
import play.core.routing.{ HandlerDef, ReverseRouteContext, queryString, dynamicString }


import _root_.controllers.Assets.Asset
import _root_.play.libs.F

// @LINE:6
package controllers.javascript {
  import ReverseRouteContext.empty

  // @LINE:45
  class ReverseAssets(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:45
    def at: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.Assets.at",
      """
        function(file) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "assets/" + (""" + implicitly[PathBindable[String]].javascriptUnbind + """)("file", file)})
        }
      """
    )
  
  }

  // @LINE:6
  class ReverseApplication(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:33
    def getFrameInstanceID: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.Application.getFrameInstanceID",
      """
        function(colNames,colValues) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "get/frameinstance/id/" + (""" + implicitly[PathBindable[String]].javascriptUnbind + """)("colNames", encodeURIComponent(colNames)) + "/" + (""" + implicitly[PathBindable[String]].javascriptUnbind + """)("colValues", encodeURIComponent(colValues))})
        }
      """
    )
  
    // @LINE:27
    def getDocumentHistory: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.Application.getDocumentHistory",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "get/history"})
        }
      """
    )
  
    // @LINE:41
    def annotIndex: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.Application.annotIndex",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "annotation"})
        }
      """
    )
  
    // @LINE:28
    def clearDocumentHistory: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.Application.clearDocumentHistory",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "clear/history"})
        }
      """
    )
  
    // @LINE:20
    def loadProject: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.Application.loadProject",
      """
        function(projID) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "get/project/" + (""" + implicitly[PathBindable[Int]].javascriptUnbind + """)("projID", projID)})
        }
      """
    )
  
    // @LINE:24
    def clearAll: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.Application.clearAll",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "clear"})
        }
      """
    )
  
    // @LINE:31
    def getSlotValues: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.Application.getSlotValues",
      """
        function(annotType) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "get/slotvalues/" + (""" + implicitly[PathBindable[String]].javascriptUnbind + """)("annotType", encodeURIComponent(annotType))})
        }
      """
    )
  
    // @LINE:17
    def removeSection: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.Application.removeSection",
      """
        function(sectionName,repeatIndex) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "remove/section/" + (""" + implicitly[PathBindable[String]].javascriptUnbind + """)("sectionName", encodeURIComponent(sectionName)) + "/" + (""" + implicitly[PathBindable[Int]].javascriptUnbind + """)("repeatIndex", repeatIndex)})
        }
      """
    )
  
    // @LINE:21
    def loadFrameInstance: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.Application.loadFrameInstance",
      """
        function(frameInstanceID) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "get/frameinstance/" + (""" + implicitly[PathBindable[Int]].javascriptUnbind + """)("frameInstanceID", frameInstanceID)})
        }
      """
    )
  
    // @LINE:15
    def getCRF: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.Application.getCRF",
      """
        function(crfID) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "get/crf/" + (""" + implicitly[PathBindable[Int]].javascriptUnbind + """)("crfID", crfID)})
        }
      """
    )
  
    // @LINE:23
    def clearElement: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.Application.clearElement",
      """
        function(elementID,htmlID) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "clear/element/" + (""" + implicitly[PathBindable[String]].javascriptUnbind + """)("elementID", encodeURIComponent(elementID)) + "/" + (""" + implicitly[PathBindable[String]].javascriptUnbind + """)("htmlID", encodeURIComponent(htmlID))})
        }
      """
    )
  
    // @LINE:37
    def getHistory: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.Application.getHistory",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "undo"})
        }
      """
    )
  
    // @LINE:16
    def addSection: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.Application.addSection",
      """
        function(sectionName) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "add/section/" + (""" + implicitly[PathBindable[String]].javascriptUnbind + """)("sectionName", encodeURIComponent(sectionName))})
        }
      """
    )
  
    // @LINE:18
    def addElement: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.Application.addElement",
      """
        function(htmlID) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "add/element/" + (""" + implicitly[PathBindable[String]].javascriptUnbind + """)("htmlID", encodeURIComponent(htmlID))})
        }
      """
    )
  
    // @LINE:7
    def logout: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.Application.logout",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "logout"})
        }
      """
    )
  
    // @LINE:26
    def addDocumentHistory: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.Application.addDocumentHistory",
      """
        function() {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "add/history"})
        }
      """
    )
  
    // @LINE:30
    def getDocumentAnnotations: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.Application.getDocumentAnnotations",
      """
        function() {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "get/annotations"})
        }
      """
    )
  
    // @LINE:32
    def getFrameData: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.Application.getFrameData",
      """
        function(frameInstanceID) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "get/framedata/" + (""" + implicitly[PathBindable[Int]].javascriptUnbind + """)("frameInstanceID", frameInstanceID)})
        }
      """
    )
  
    // @LINE:13
    def loadApp: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.Application.loadApp",
      """
        function(colNames,colValues) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "get/frameinstance/" + (""" + implicitly[PathBindable[String]].javascriptUnbind + """)("colNames", encodeURIComponent(colNames)) + "/" + (""" + implicitly[PathBindable[String]].javascriptUnbind + """)("colValues", encodeURIComponent(colValues))})
        }
      """
    )
  
    // @LINE:14
    def getDocument: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.Application.getDocument",
      """
        function() {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "get/document"})
        }
      """
    )
  
    // @LINE:19
    def removeElement: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.Application.removeElement",
      """
        function(elementID,htmlID) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "remove/element/" + (""" + implicitly[PathBindable[String]].javascriptUnbind + """)("elementID", encodeURIComponent(elementID)) + "/" + (""" + implicitly[PathBindable[String]].javascriptUnbind + """)("htmlID", encodeURIComponent(htmlID))})
        }
      """
    )
  
    // @LINE:29
    def fillSlot: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.Application.fillSlot",
      """
        function() {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "fillslot"})
        }
      """
    )
  
    // @LINE:25
    def clearValue: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.Application.clearValue",
      """
        function(htmlID) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "clear/value/" + (""" + implicitly[PathBindable[String]].javascriptUnbind + """)("htmlID", encodeURIComponent(htmlID))})
        }
      """
    )
  
    // @LINE:36
    def authenticate: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.Application.authenticate",
      """
        function() {
          return _wA({method:"POST", url:"""" + _prefix + """"})
        }
      """
    )
  
    // @LINE:12
    def index: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.Application.index",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "get/index"})
        }
      """
    )
  
    // @LINE:22
    def addAnnotation: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.Application.addAnnotation",
      """
        function() {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "add/annotation"})
        }
      """
    )
  
    // @LINE:6
    def login: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.Application.login",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + """"})
        }
      """
    )
  
  }


}