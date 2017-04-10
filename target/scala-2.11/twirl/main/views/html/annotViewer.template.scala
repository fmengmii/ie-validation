
package views.html

import play.twirl.api._
import play.twirl.api.TemplateMagic._


     object annotViewer_Scope0 {
import models._
import controllers._
import play.api.i18n._
import views.html._
import play.api.templates.PlayMagic._
import java.lang._
import java.util._
import scala.collection.JavaConversions._
import scala.collection.JavaConverters._
import play.core.j.PlayMagicForJava._
import play.mvc._
import play.data._
import play.api.data.Field
import play.mvc.Http.Context.Implicit._

class annotViewer extends BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with play.twirl.api.Template1[Array[Map[String, String]],play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(projList: Array[Map[String, String]]):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*1.40*/("""

"""),format.raw/*3.1*/("""<!DOCTYPE html>
<html>
	<head>
		<link href=""""),_display_(/*6.16*/routes/*6.22*/.Assets.at("stylesheets/main.css")),format.raw/*6.56*/("""" rel="stylesheet" />
		<link href=""""),_display_(/*7.16*/routes/*7.22*/.Assets.at("stylesheets/jqx.base.css")),format.raw/*7.60*/("""" rel="stylesheet" />
		<link href=""""),_display_(/*8.16*/routes/*8.22*/.Assets.at("stylesheets/jqx.energyblue.css")),format.raw/*8.66*/("""" rel="stylesheet" />
		<link href=""""),_display_(/*9.16*/routes/*9.22*/.Assets.at("stylesheets/jquery.highlight-within-textarea.css")),format.raw/*9.84*/("""" rel="stylesheet" />
				
		<meta charset=utf-8 />
		<title>Validation Viewer</title>
		
		<script src=""""),_display_(/*14.17*/routes/*14.23*/.Assets.at("javascripts/jquery-2.1.3.min.js")),format.raw/*14.68*/(""""></script>
		<script src=""""),_display_(/*15.17*/routes/*15.23*/.Assets.at("javascripts/jqxcore.js")),format.raw/*15.59*/(""""></script>
	    <script src=""""),_display_(/*16.20*/routes/*16.26*/.Assets.at("javascripts/jqxsplitter.js")),format.raw/*16.66*/(""""></script>
	    <script src=""""),_display_(/*17.20*/routes/*17.26*/.Assets.at("javascripts/jqxpanel.js")),format.raw/*17.63*/(""""></script>
	    <script src=""""),_display_(/*18.20*/routes/*18.26*/.Assets.at("javascripts/jqxbuttons.js")),format.raw/*18.65*/(""""></script>
	    <script src=""""),_display_(/*19.20*/routes/*19.26*/.Assets.at("javascripts/jqxscrollbar.js")),format.raw/*19.67*/(""""></script>
	    <script src=""""),_display_(/*20.20*/routes/*20.26*/.Assets.at("javascripts/jqxwindow.js")),format.raw/*20.64*/(""""></script>
	   	<script src=""""),_display_(/*21.20*/routes/*21.26*/.Assets.at("javascripts/jqxgrid.js")),format.raw/*21.62*/(""""></script>
	   	<script src=""""),_display_(/*22.20*/routes/*22.26*/.Assets.at("javascripts/jqxgrid.pager.js")),format.raw/*22.68*/(""""></script>
		<script src=""""),_display_(/*23.17*/routes/*23.23*/.Assets.at("javascripts/jqxgrid.selection.js")),format.raw/*23.69*/(""""></script>
		<script src=""""),_display_(/*24.17*/routes/*24.23*/.Assets.at("javascripts/jqxgrid.sort.js")),format.raw/*24.64*/(""""></script>
		<script src=""""),_display_(/*25.17*/routes/*25.23*/.Assets.at("javascripts/jqxgrid.columnsresize.js")),format.raw/*25.73*/(""""></script>
		<script src=""""),_display_(/*26.17*/routes/*26.23*/.Assets.at("javascripts/jqxdata.js")),format.raw/*26.59*/(""""></script>
		<script src=""""),_display_(/*27.17*/routes/*27.23*/.Assets.at("javascripts/jqxdatatable.js")),format.raw/*27.64*/(""""></script>
		<script src=""""),_display_(/*28.17*/routes/*28.23*/.Assets.at("javascripts/jqxeditor.js")),format.raw/*28.61*/(""""></script>
		<script src=""""),_display_(/*29.17*/routes/*29.23*/.Assets.at("javascripts/jqxlistbox.js")),format.raw/*29.62*/(""""></script>
		<script src=""""),_display_(/*30.17*/routes/*30.23*/.Assets.at("javascripts/jqxdropdownlist.js")),format.raw/*30.67*/(""""></script>
		<script src=""""),_display_(/*31.17*/routes/*31.23*/.Assets.at("javascripts/jqxdropdownbutton.js")),format.raw/*31.69*/(""""></script>
		<script src=""""),_display_(/*32.17*/routes/*32.23*/.Assets.at("javascripts/jquery-fieldselection.js")),format.raw/*32.73*/(""""></script>
		<script src=""""),_display_(/*33.17*/routes/*33.23*/.Assets.at("javascripts/jquery.highlight-within-textarea.js")),format.raw/*33.84*/(""""></script>
		<script src=""""),_display_(/*34.17*/routes/*34.23*/.Assets.at("javascripts/jqxmenu.js")),format.raw/*34.59*/(""""></script>
			    
	    <!-- our own scripts -->
	    <script src=""""),_display_(/*37.20*/routes/*37.26*/.Assets.at("javascripts/viewer.js")),format.raw/*37.61*/(""""></script>
	    
	    <!-- Embedded Javascript router -->
        """),_display_(/*40.10*/helper/*40.16*/.javascriptRouter("jsRoutes")/*40.45*/(
              routes.javascript.Application.index,
              routes.javascript.Application.getDocument,
              routes.javascript.Application.getCRF,
              routes.javascript.Application.addSection,
              routes.javascript.Application.removeSection,
              routes.javascript.Application.addElement,
              routes.javascript.Application.removeElement,
              routes.javascript.Application.loadProject,
              routes.javascript.Application.loadFrameInstance,
              routes.javascript.Application.addAnnotation,
              routes.javascript.Application.clearElement,
              routes.javascript.Application.clearAll,
              routes.javascript.Application.addDocumentHistory,
              routes.javascript.Application.getDocumentHistory,
              routes.javascript.Application.clearDocumentHistory,
              routes.javascript.Application.fillSlot,
              routes.javascript.Application.getDocumentAnnotations,
              routes.javascript.Application.getSlotValues
              )),format.raw/*59.16*/("""

		"""),format.raw/*61.3*/("""<style>
	        html, body
	        """),format.raw/*63.10*/("""{"""),format.raw/*63.11*/("""
	            """),format.raw/*64.14*/("""width: 100%;
	            height: 100%;
	            overflow: hidden;
	        """),format.raw/*67.10*/("""}"""),format.raw/*67.11*/("""
	        """),format.raw/*68.10*/("""span.highlight
	        """),format.raw/*69.10*/("""{"""),format.raw/*69.11*/("""
	        	"""),format.raw/*70.11*/("""background-color: lightblue;
	        """),format.raw/*71.10*/("""}"""),format.raw/*71.11*/("""
	        """),format.raw/*72.10*/("""#dataElementTable .jqx-grid-cell-pinned
		    """),format.raw/*73.7*/("""{"""),format.raw/*73.8*/("""
		        """),format.raw/*74.11*/("""background-color: lightyellow;
		        font-weight: bold;
		    """),format.raw/*76.7*/("""}"""),format.raw/*76.8*/("""
		    """),format.raw/*77.7*/("""#dataElementTable .jqx-widget-header
		    """),format.raw/*78.7*/("""{"""),format.raw/*78.8*/("""
			    """),format.raw/*79.8*/("""font-weight: bold;
		    """),format.raw/*80.7*/("""}"""),format.raw/*80.8*/("""
		    """),format.raw/*81.7*/(""".unselectable """),format.raw/*81.21*/("""{"""),format.raw/*81.22*/("""
			    """),format.raw/*82.8*/("""-webkit-touch-callout: none;
			    -webkit-user-select: none;
			    -khtml-user-select: none;
			    -moz-user-select: none;
			    -ms-user-select: none;
			    user-select: none;
			"""),format.raw/*88.4*/("""}"""),format.raw/*88.5*/("""
			
	    """),format.raw/*90.6*/("""</style>
	</head>

	
	<body>
	<div id="buttonsDiv">
		<select id='projSelect' onchange="loadProject(this.value)">
			<option selected disabled value=''>Select Project</option>
		"""),_display_(/*98.4*/for(proj <- projList) yield /*98.25*/ {_display_(Seq[Any](format.raw/*98.27*/("""
			"""),format.raw/*99.4*/("""<option value='"""),_display_(/*99.20*/proj/*99.24*/.get("id")),format.raw/*99.34*/("""'>"""),_display_(/*99.37*/proj/*99.41*/.get("name")),format.raw/*99.53*/("""</option>
		""")))}),format.raw/*100.4*/("""
		"""),format.raw/*101.3*/("""</select>
		<select id='crfSelect' onchange="frameInstanceSelected(this.value, true, this.selectedIndex)">
			<option selected disabled hidden value=''>Select Instance</option>
		</select>
		<button type="button" id="clearButton" onclick="clearElement()">Clear</button>
		<button type="button" id="clearAllButton" onclick="openClearAllDialog()">Clear All</button>
		<button type="button" id="clearDocHistButton" onclick="clearDocumentHistory()">Clear Document History</button>
		<button type="button" id="fillAll" onclick="fillAll()">Fill All</button>
		
<!-- 		<select id='docSelect' onchange="getDocument(this.value)"> -->
<!-- 			<option selected disabled hidden value=''>Select Report</option> -->
<!-- 		</select> -->
		
		
		<button type="button" onclick="prevInstance()">Previous</button>
		<button type="button" onclick="nextInstance()">Next</button>
	</div>
    <div id='splitter'>
        <div id='crfPanel'>
        	Test
        </div>
        <div>
        	<div class='unselectable' id='docTitleDiv' style="float: top;">No Document Selected</div>
        	<div id='splitter2'>
	        	<div class="jqx-hideborder jqx-hidescrollbars">
	        	  <div id='docListBox'></div>
				</div>
	        	<div id="docSplitPane" style="height:100%; width:100%; overflow:auto" >
	        		<div id="docDiv" style="height:100%;width:100%;overflow:hidden">
		        		<div id='docFeatures'></div>
						<textarea id='docPanel' readonly style="height:90%;width:90%"></textarea>
					</div>
	        	</div>
        	</div>
       </div>
    </div>

    <div id='dialogLoadInstance'>
      <div>
      </div>
      <div>
		Loading...
      </div>
    </div>
    
    <div id='dialogClearAll'>
      <div>
      </div>
      <div>
      	<div>
			Clear all elements?
		</div>
		<div>
			<input type="button" id="clearAllOKButton" value="OK" style="margin-right: 10px" onclick="clearAll()" />
			<input type="button" id="clearAllCancelButton" value="Cancel" />
		</div>
      </div>
    </div>
    
    <div id='highlightMenu'>
    	<ul>
    		<li id="clear">Clear</li>
    	</ul>
    </div>
    
    </body>
</html>
"""))
      }
    }
  }

  def render(projList:Array[Map[String, String]]): play.twirl.api.HtmlFormat.Appendable = apply(projList)

  def f:((Array[Map[String, String]]) => play.twirl.api.HtmlFormat.Appendable) = (projList) => apply(projList)

  def ref: this.type = this

}


}

/**/
object annotViewer extends annotViewer_Scope0.annotViewer
              /*
                  -- GENERATED --
                  DATE: Thu Apr 06 15:26:01 PDT 2017
                  SOURCE: /home/wyu/workspace-luna/ie-validation-github-dev-BackUp-022417/app/views/annotViewer.scala.html
                  HASH: 370485d544e7d8428514c5b933d962a117fc0d28
                  MATRIX: 777->1|910->39|938->41|1010->87|1024->93|1078->127|1141->164|1155->170|1213->208|1276->245|1290->251|1354->295|1417->332|1431->338|1513->400|1646->506|1661->512|1727->557|1782->585|1797->591|1854->627|1912->658|1927->664|1988->704|2046->735|2061->741|2119->778|2177->809|2192->815|2252->854|2310->885|2325->891|2387->932|2445->963|2460->969|2519->1007|2577->1038|2592->1044|2649->1080|2707->1111|2722->1117|2785->1159|2840->1187|2855->1193|2922->1239|2977->1267|2992->1273|3054->1314|3109->1342|3124->1348|3195->1398|3250->1426|3265->1432|3322->1468|3377->1496|3392->1502|3454->1543|3509->1571|3524->1577|3583->1615|3638->1643|3653->1649|3713->1688|3768->1716|3783->1722|3848->1766|3903->1794|3918->1800|3985->1846|4040->1874|4055->1880|4126->1930|4181->1958|4196->1964|4278->2025|4333->2053|4348->2059|4405->2095|4501->2164|4516->2170|4572->2205|4667->2273|4682->2279|4720->2308|5813->3380|5844->3384|5909->3421|5938->3422|5980->3436|6088->3516|6117->3517|6155->3527|6207->3551|6236->3552|6275->3563|6341->3601|6370->3602|6408->3612|6481->3658|6509->3659|6548->3670|6641->3736|6669->3737|6703->3744|6773->3787|6801->3788|6836->3796|6888->3821|6916->3822|6950->3829|6992->3843|7021->3844|7056->3852|7269->4038|7297->4039|7334->4049|7539->4228|7576->4249|7616->4251|7647->4255|7690->4271|7703->4275|7734->4285|7764->4288|7777->4292|7810->4304|7854->4317|7885->4320
                  LINES: 27->1|32->1|34->3|37->6|37->6|37->6|38->7|38->7|38->7|39->8|39->8|39->8|40->9|40->9|40->9|45->14|45->14|45->14|46->15|46->15|46->15|47->16|47->16|47->16|48->17|48->17|48->17|49->18|49->18|49->18|50->19|50->19|50->19|51->20|51->20|51->20|52->21|52->21|52->21|53->22|53->22|53->22|54->23|54->23|54->23|55->24|55->24|55->24|56->25|56->25|56->25|57->26|57->26|57->26|58->27|58->27|58->27|59->28|59->28|59->28|60->29|60->29|60->29|61->30|61->30|61->30|62->31|62->31|62->31|63->32|63->32|63->32|64->33|64->33|64->33|65->34|65->34|65->34|68->37|68->37|68->37|71->40|71->40|71->40|90->59|92->61|94->63|94->63|95->64|98->67|98->67|99->68|100->69|100->69|101->70|102->71|102->71|103->72|104->73|104->73|105->74|107->76|107->76|108->77|109->78|109->78|110->79|111->80|111->80|112->81|112->81|112->81|113->82|119->88|119->88|121->90|129->98|129->98|129->98|130->99|130->99|130->99|130->99|130->99|130->99|130->99|131->100|132->101
                  -- GENERATED --
              */
          