
package views.html

import play.twirl.api._
import play.twirl.api.TemplateMagic._


     object viewer_Scope0 {
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

class viewer extends BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with play.twirl.api.Template3[Array[Map[String, String]],String,String,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(projList: Array[Map[String, String]], colNames:String, colValues:String):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*1.75*/("""

"""),format.raw/*3.1*/("""<!DOCTYPE html>
<html>
	<head>
		<link href=""""),_display_(/*6.16*/routes/*6.22*/.Assets.at("stylesheets/main.css")),format.raw/*6.56*/("""" rel="stylesheet" />
		<link href=""""),_display_(/*7.16*/routes/*7.22*/.Assets.at("stylesheets/jqx.base.css")),format.raw/*7.60*/("""" rel="stylesheet" />
		<link href=""""),_display_(/*8.16*/routes/*8.22*/.Assets.at("stylesheets/jqx.energyblue.css")),format.raw/*8.66*/("""" rel="stylesheet" />
		<link href=""""),_display_(/*9.16*/routes/*9.22*/.Assets.at("stylesheets/jquery.highlight-within-textarea.css")),format.raw/*9.84*/("""" rel="stylesheet" />
		<link href=""""),_display_(/*10.16*/routes/*10.22*/.Assets.at("stylesheets/jquery-ui.css")),format.raw/*10.61*/("""" rel="stylesheet" />
		<link href=""""),_display_(/*11.16*/routes/*11.22*/.Assets.at("stylesheets/select2.min.css")),format.raw/*11.63*/("""" rel="stylesheet" />
				
		<meta charset=utf-8 />
		<title>Validation Viewer</title>
		
		<script src=""""),_display_(/*16.17*/routes/*16.23*/.Assets.at("javascripts/jquery-2.1.3.min.js")),format.raw/*16.68*/(""""></script>
		<script src=""""),_display_(/*17.17*/routes/*17.23*/.Assets.at("javascripts/jquery-ui.js")),format.raw/*17.61*/(""""></script>
		<script src=""""),_display_(/*18.17*/routes/*18.23*/.Assets.at("javascripts/jqxcore.js")),format.raw/*18.59*/(""""></script>
	    <script src=""""),_display_(/*19.20*/routes/*19.26*/.Assets.at("javascripts/jqxsplitter.js")),format.raw/*19.66*/(""""></script>
	    <script src=""""),_display_(/*20.20*/routes/*20.26*/.Assets.at("javascripts/jqxpanel.js")),format.raw/*20.63*/(""""></script>
	    <script src=""""),_display_(/*21.20*/routes/*21.26*/.Assets.at("javascripts/jqxbuttons.js")),format.raw/*21.65*/(""""></script>
	    <script src=""""),_display_(/*22.20*/routes/*22.26*/.Assets.at("javascripts/jqxscrollbar.js")),format.raw/*22.67*/(""""></script>
	    <script src=""""),_display_(/*23.20*/routes/*23.26*/.Assets.at("javascripts/jqxwindow.js")),format.raw/*23.64*/(""""></script>
	   	<script src=""""),_display_(/*24.20*/routes/*24.26*/.Assets.at("javascripts/jqxgrid.js")),format.raw/*24.62*/(""""></script>
	   	<script src=""""),_display_(/*25.20*/routes/*25.26*/.Assets.at("javascripts/jqxgrid.pager.js")),format.raw/*25.68*/(""""></script>
		<script src=""""),_display_(/*26.17*/routes/*26.23*/.Assets.at("javascripts/jqxgrid.selection.js")),format.raw/*26.69*/(""""></script>
		<script src=""""),_display_(/*27.17*/routes/*27.23*/.Assets.at("javascripts/jqxgrid.sort.js")),format.raw/*27.64*/(""""></script>
		<script src=""""),_display_(/*28.17*/routes/*28.23*/.Assets.at("javascripts/jqxgrid.columnsresize.js")),format.raw/*28.73*/(""""></script>
		<script src=""""),_display_(/*29.17*/routes/*29.23*/.Assets.at("javascripts/jqxdata.js")),format.raw/*29.59*/(""""></script>
		<script src=""""),_display_(/*30.17*/routes/*30.23*/.Assets.at("javascripts/jqxdatatable.js")),format.raw/*30.64*/(""""></script>
		<script src=""""),_display_(/*31.17*/routes/*31.23*/.Assets.at("javascripts/jqxeditor.js")),format.raw/*31.61*/(""""></script>
		<script src=""""),_display_(/*32.17*/routes/*32.23*/.Assets.at("javascripts/jqxlistbox.js")),format.raw/*32.62*/(""""></script>
		<script src=""""),_display_(/*33.17*/routes/*33.23*/.Assets.at("javascripts/jqxdropdownlist.js")),format.raw/*33.67*/(""""></script>
		<script src=""""),_display_(/*34.17*/routes/*34.23*/.Assets.at("javascripts/jqxdropdownbutton.js")),format.raw/*34.69*/(""""></script>
		<script src=""""),_display_(/*35.17*/routes/*35.23*/.Assets.at("javascripts/jquery-fieldselection.js")),format.raw/*35.73*/(""""></script>
		<script src=""""),_display_(/*36.17*/routes/*36.23*/.Assets.at("javascripts/jquery.highlight-within-textarea.js")),format.raw/*36.84*/(""""></script>
		<script src=""""),_display_(/*37.17*/routes/*37.23*/.Assets.at("javascripts/jqxmenu.js")),format.raw/*37.59*/(""""></script>
		<script src=""""),_display_(/*38.17*/routes/*38.23*/.Assets.at("javascripts/select2.min.js")),format.raw/*38.63*/(""""></script>
			    
	    <!-- our own scripts -->
	    <script src=""""),_display_(/*41.20*/routes/*41.26*/.Assets.at("javascripts/viewer.js")),format.raw/*41.61*/(""""></script>
	    
	    <!-- Embedded Javascript router -->
        """),_display_(/*44.10*/helper/*44.16*/.javascriptRouter("jsRoutes")/*44.45*/(
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
              routes.javascript.Application.getSlotValues,
              routes.javascript.Application.getFrameData,
              routes.javascript.Application.clearValue,
              routes.javascript.Application.getFrameInstanceID,
              routes.javascript.Application.authenticate,
              routes.javascript.Application.logout,
              routes.javascript.Application.getHistory,
			  routes.javascript.Application.docValidated
              
              )),format.raw/*71.16*/("""

		"""),format.raw/*73.3*/("""<style>
	        html, body
	        """),format.raw/*75.10*/("""{"""),format.raw/*75.11*/("""
	            """),format.raw/*76.14*/("""width: 100%;
	            height: 100%;
	            overflow: hidden;
	        """),format.raw/*79.10*/("""}"""),format.raw/*79.11*/("""
	        """),format.raw/*80.10*/("""span.highlight
	        """),format.raw/*81.10*/("""{"""),format.raw/*81.11*/("""
	        	"""),format.raw/*82.11*/("""background-color: lightblue;
	        """),format.raw/*83.10*/("""}"""),format.raw/*83.11*/("""
	        """),format.raw/*84.10*/("""#dataElementTable .jqx-grid-cell-pinned
		    """),format.raw/*85.7*/("""{"""),format.raw/*85.8*/("""
		        """),format.raw/*86.11*/("""background-color: lightyellow;
		        font-weight: bold;
		    """),format.raw/*88.7*/("""}"""),format.raw/*88.8*/("""
		    """),format.raw/*89.7*/("""#dataElementTable .jqx-widget-header
		    """),format.raw/*90.7*/("""{"""),format.raw/*90.8*/("""
			    """),format.raw/*91.8*/("""font-weight: bold;
		    """),format.raw/*92.7*/("""}"""),format.raw/*92.8*/("""
		    """),format.raw/*93.7*/(""".unselectable """),format.raw/*93.21*/("""{"""),format.raw/*93.22*/("""
			    """),format.raw/*94.8*/("""-webkit-touch-callout: none;
			    -webkit-user-select: none;
			    -khtml-user-select: none;
			    -moz-user-select: none;
			    -ms-user-select: none;
			    user-select: none;
			"""),format.raw/*100.4*/("""}"""),format.raw/*100.5*/("""
			"""),format.raw/*101.4*/("""#dataElementTable textarea """),format.raw/*101.31*/("""{"""),format.raw/*101.32*/("""
			    """),format.raw/*102.8*/("""border: 0 none white;
			    overflow-y: scroll;
			    padding: 0;
			    outline: none;
			    background-color: #D0D0D0;
			    resize: none;
			    height: 300px;
			    width: 90%;
			"""),format.raw/*110.4*/("""}"""),format.raw/*110.5*/("""
			
"""),format.raw/*112.1*/("""/* 			.select2-container """),format.raw/*112.26*/("""{"""),format.raw/*112.27*/(""" """),format.raw/*112.28*/("""*/
/* 				font-size: 12px; */
/* 				width: 50px; */
/* 				margin: 0px; */
/* 				padding: 0px; */
/* 				box-sizing: content-box; */
/* 				vertical-align: middle; */
/* 			"""),format.raw/*119.7*/("""}"""),format.raw/*119.8*/(""" """),format.raw/*119.9*/("""*/
/* 			.select2-container .select2-selection--single """),format.raw/*120.53*/("""{"""),format.raw/*120.54*/(""" """),format.raw/*120.55*/("""*/
/* 				height: 15px; */
/* 				padding: 0px; */
/* 				margin: 0px; */
/* 				vertical-align: top; */
/* 				box-sizing: content-box; */
/* 				text-align: center; */
/* 			"""),format.raw/*127.7*/("""}"""),format.raw/*127.8*/(""" """),format.raw/*127.9*/("""*/
			
/* 			.select2-container .select2-selection--single .select2-selection__rendered """),format.raw/*129.82*/("""{"""),format.raw/*129.83*/(""" """),format.raw/*129.84*/("""*/
/* 		      padding: 0px; */
/* 		      margin: 0px; */
/* 		      text-align: center; */
/* 		      vertical-align: top; */
/* 		      box-sizing: content-box; */
/* 		     """),format.raw/*135.11*/("""}"""),format.raw/*135.12*/(""" """),format.raw/*135.13*/("""*/
/* 		     .select2-dropdown """),format.raw/*136.29*/("""{"""),format.raw/*136.30*/(""" """),format.raw/*136.31*/("""*/
/* 				text-align: center; */
/* 				border-radius: 0px; */
/* 				box-sizing: content-box; */
/* 		     """),format.raw/*140.11*/("""}"""),format.raw/*140.12*/(""" """),format.raw/*140.13*/("""*/
		     
/* 		     .select2-container--default .select2-selection--single """),format.raw/*142.66*/("""{"""),format.raw/*142.67*/(""" """),format.raw/*142.68*/("""*/
/* 				  background-color: #fff; */
/* 				  border: 1px solid #aaa; */
/* 				  border-radius: 0px; */
/* 			"""),format.raw/*146.7*/("""}"""),format.raw/*146.8*/(""" """),format.raw/*146.9*/("""*/
			
/* 			 .select2-container--default .select2-selection--single .select2-selection__rendered """),format.raw/*148.92*/("""{"""),format.raw/*148.93*/(""" """),format.raw/*148.94*/("""*/
/* 			    color: #444; */
/* 			    line-height: 20px; """),format.raw/*150.30*/("""}"""),format.raw/*150.31*/(""" """),format.raw/*150.32*/("""*/
			    
/* 			    .select2-results__option """),format.raw/*152.36*/("""{"""),format.raw/*152.37*/(""" """),format.raw/*152.38*/("""*/
/* 				  padding: 0px; */
/* 				 """),format.raw/*154.9*/("""}"""),format.raw/*154.10*/(""" """),format.raw/*154.11*/("""*/
			
/* 			  .select2-container--default .select2-selection--single .select2-selection__arrow """),format.raw/*156.90*/("""{"""),format.raw/*156.91*/(""" """),format.raw/*156.92*/("""*/
/* 				    height: 30px; */
/* 				    position: absolute; */
/* 				    top: 1px; */
/* 				    right: 1px; */
/* 				    width: 20px; """),format.raw/*161.25*/("""}"""),format.raw/*161.26*/(""" """),format.raw/*161.27*/("""*/


					.select2-container """),format.raw/*164.25*/("""{"""),format.raw/*164.26*/(""" 
					 				"""),format.raw/*165.11*/("""font-size: 12px; 
					 				width: 100px;
					 				padding: 0px;
					 """),format.raw/*168.7*/("""}"""),format.raw/*168.8*/("""


				"""),format.raw/*171.5*/(""".select2-container .select2-selection--single """),format.raw/*171.51*/("""{"""),format.raw/*171.52*/(""" 
				 				"""),format.raw/*172.10*/("""padding: 0px; 
				 				margin: 0px; 
				 				vertical-align: top; 
				 				box-sizing: content-box; 
				 				text-align: center;
								height: 15px;					 				
				 				
				 			"""),format.raw/*179.9*/("""}"""),format.raw/*179.10*/(""" 

			"""),format.raw/*181.4*/(""".select2-container .select2-selection--single .select2-selection__rendered """),format.raw/*181.79*/("""{"""),format.raw/*181.80*/(""" 
			 	      """),format.raw/*182.12*/("""padding: 0px; 
			 		      margin: 0px; 
			 		      text-align: center; 
			 		      vertical-align: top; 
					      box-sizing: content-box;
					      
 		     """),format.raw/*188.9*/("""}"""),format.raw/*188.10*/("""

		"""),format.raw/*190.3*/(""".select2-dropdown """),format.raw/*190.21*/("""{"""),format.raw/*190.22*/(""" 
 				"""),format.raw/*191.6*/("""text-align: center; 
 				border-radius: 0px; 
 				box-sizing: content-box; 
		     """),format.raw/*194.8*/("""}"""),format.raw/*194.9*/("""
		     
		     
		     """),format.raw/*197.8*/(""".select2-container--default .select2-selection--single """),format.raw/*197.63*/("""{"""),format.raw/*197.64*/(""" 
 				  """),format.raw/*198.8*/("""background-color: #fff; 
 				  border: 1px solid #aaa; 
 				  border-radius: 0px; 
 			"""),format.raw/*201.5*/("""}"""),format.raw/*201.6*/("""
 			

			    
 			    """),format.raw/*205.9*/(""".select2-results__option """),format.raw/*205.34*/("""{"""),format.raw/*205.35*/(""" 
 				  """),format.raw/*206.8*/("""padding: 0px;
 				  margin: 0px;
			      /*color: green;*/
 				 """),format.raw/*209.7*/("""}"""),format.raw/*209.8*/(""" 

  """),format.raw/*211.3*/(""".select2-container--default .select2-selection--single .select2-selection__arrow """),format.raw/*211.84*/("""{"""),format.raw/*211.85*/("""
    """),format.raw/*212.5*/("""height: 15px;
    position: absolute;
    top: 1px;
    right: 1px;
    width: 20px; """),format.raw/*216.18*/("""}"""),format.raw/*216.19*/("""
    
    
      """),format.raw/*219.7*/(""".select2-container--default .select2-selection--single .select2-selection__rendered """),format.raw/*219.91*/("""{"""),format.raw/*219.92*/("""
    """),format.raw/*220.5*/("""color: #444;
    line-height: 15px; """),format.raw/*221.24*/("""}"""),format.raw/*221.25*/("""

			"""),format.raw/*223.4*/("""#select2-crfSelect-result-tyh6-101"""),format.raw/*223.38*/("""{"""),format.raw/*223.39*/("""
				"""),format.raw/*224.5*/("""color: red;
			"""),format.raw/*225.4*/("""}"""),format.raw/*225.5*/("""
			
	    """),format.raw/*227.6*/("""</style>
	</head>

	
	<body>
	<div id="buttonsDiv">
		<select id='projSelect' onchange="loadProject(this.value)">
			<option selected disabled value=''>Select Project</option>
		"""),_display_(/*235.4*/for(proj <- projList) yield /*235.25*/ {_display_(Seq[Any](format.raw/*235.27*/("""
			"""),format.raw/*236.4*/("""<option value='"""),_display_(/*236.20*/proj/*236.24*/.get("id")),format.raw/*236.34*/("""'>"""),_display_(/*236.37*/proj/*236.41*/.get("name")),format.raw/*236.53*/("""</option>
		""")))}),format.raw/*237.4*/("""
		"""),format.raw/*238.3*/("""</select>
		<select id='crfSelect' onchange="frameInstanceSelected(this.value, true, this.selectedIndex)">
			<option selected disabled hidden value=''>Select Instance</option>
		</select>
		
		
<!-- 		<input type="text" id="instanceText"/> -->
<!-- 		<button type="button" id="instanceButton" disabled onclick="frameInstanceSelectedText($('#instanceText').val())">Get Instance</button> -->
		
		
		
		<!--new code-->
		<!--<button type="button" id="undoButton" onclick="undoAction()">Undo</button>-->
		<!--end of new code-->
		
		<button type="button" id="clearButton" onclick="clearElement()">Clear</button>
		<button type="button" id="clearAllButton" onclick="openClearAllDialog()">Clear All</button>
		<button type="button" id="clearDocHistButton" onclick="clearDocumentHistory()">Clear Document History</button>
<!-- 		<button type="button" id="fillAll" onclick="fillAll()">Fill All</button> -->
<!-- 		<label for="tokenSelectButton"><font size="2">Token Select</font></label> -->
<!-- 		<input type="checkbox" id="tokenSelectButton" onclick="toggleTokenSelect()"/> -->
		
<!-- 		<select id='docSelect' onchange="getDocument(this.value)"> -->
<!-- 			<option selected disabled hidden value=''>Select Report</option> -->
<!-- 		</select> -->
		
		
		<button type="button" onclick="prevInstance()">Previous</button>
		<button type="button" onclick="nextInstance()">Next</button>
		
		<!--new code-->
		<button type="button" onclick="highlightByWord()" id="highlightButton" value="Word Highlighting: Off">Word Highlighting: Off</button>
        <a href=""""),_display_(/*270.19*/routes/*270.25*/.Application.logout()),format.raw/*270.46*/(""""><button type="button" onclick="logout()">Log Out</button></a>
        <!--end of new code-->
	</div>
	<div id="errorWindow">
		<div>title</div>
		<div id="alert-box" class="alert alert-danger" >
			<p class="close" data-dismiss="alert" aria-label="close"
			   onclick="hideAlertBox()">&times;</p>
			<strong>Alert:</strong>&nbsp;<span class="alertBoxMessage"></span>
		</div>
	</div>

	<div id="successWindow">
		<div>title</div>
		<div id="success-box" class="alert alert-success" >
			<p class="close" data-dismiss="alert" aria-label="close" onclick="hideSuccessBox()">&times;</p>
			<strong>Success!</strong>&nbsp;<span class="successBoxMessage"></span>
		</div>
	</div>
    <div id='splitter'>
        <div id='crfPanel'>
        	<div id='dataElementTable'></div>
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
						<textarea id='docPanel' readonly style="height:90%;width:90%" onclick=docPanelSelect(event) onmouseup="onRightClick(event)"></textarea>
						<!--<textarea id='docPanel' readonly style="height:90%;width:90%" onclick=docPanelSelect(event);></textarea>--> 
						<div id="validatedButtonDiv">
							<button type="button" onclick="docValidated()" id="validatedButton" value="validated">Validated</button>
						</div>
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
    

	<input type='hidden' id='colNames' value='"""),_display_(/*342.45*/colNames),format.raw/*342.53*/("""'>
	<input type='hidden' id='colValues' value='"""),_display_(/*343.46*/colValues),format.raw/*343.55*/("""'>

    
    </body>
</html>"""))
      }
    }
  }

  def render(projList:Array[Map[String, String]],colNames:String,colValues:String): play.twirl.api.HtmlFormat.Appendable = apply(projList,colNames,colValues)

  def f:((Array[Map[String, String]],String,String) => play.twirl.api.HtmlFormat.Appendable) = (projList,colNames,colValues) => apply(projList,colNames,colValues)

  def ref: this.type = this

}


}

/**/
object viewer extends viewer_Scope0.viewer
              /*
                  -- GENERATED --
                  DATE: Fri Feb 24 11:31:46 PST 2017
                  SOURCE: /home/wyu/workspace-luna/ie-validation-github-dev/app/views/viewer.scala.html
                  HASH: 154cd5bc0d4c51cb19cb91fa6ac645d03ed3e9e7
                  MATRIX: 781->1|949->74|977->76|1049->122|1063->128|1117->162|1180->199|1194->205|1252->243|1315->280|1329->286|1393->330|1456->367|1470->373|1552->435|1616->472|1631->478|1691->517|1755->554|1770->560|1832->601|1965->707|1980->713|2046->758|2101->786|2116->792|2175->830|2230->858|2245->864|2302->900|2360->931|2375->937|2436->977|2494->1008|2509->1014|2567->1051|2625->1082|2640->1088|2700->1127|2758->1158|2773->1164|2835->1205|2893->1236|2908->1242|2967->1280|3025->1311|3040->1317|3097->1353|3155->1384|3170->1390|3233->1432|3288->1460|3303->1466|3370->1512|3425->1540|3440->1546|3502->1587|3557->1615|3572->1621|3643->1671|3698->1699|3713->1705|3770->1741|3825->1769|3840->1775|3902->1816|3957->1844|3972->1850|4031->1888|4086->1916|4101->1922|4161->1961|4216->1989|4231->1995|4296->2039|4351->2067|4366->2073|4433->2119|4488->2147|4503->2153|4574->2203|4629->2231|4644->2237|4726->2298|4781->2326|4796->2332|4853->2368|4908->2396|4923->2402|4984->2442|5080->2511|5095->2517|5151->2552|5246->2620|5261->2626|5299->2655|6800->4135|6831->4139|6896->4176|6925->4177|6967->4191|7075->4271|7104->4272|7142->4282|7194->4306|7223->4307|7262->4318|7328->4356|7357->4357|7395->4367|7468->4413|7496->4414|7535->4425|7628->4491|7656->4492|7690->4499|7760->4542|7788->4543|7823->4551|7875->4576|7903->4577|7937->4584|7979->4598|8008->4599|8043->4607|8257->4793|8286->4794|8318->4798|8374->4825|8404->4826|8440->4834|8657->5023|8686->5024|8719->5029|8773->5054|8803->5055|8833->5056|9036->5231|9065->5232|9094->5233|9178->5288|9208->5289|9238->5290|9442->5466|9471->5467|9500->5468|9617->5556|9647->5557|9677->5558|9882->5734|9912->5735|9942->5736|10002->5767|10032->5768|10062->5769|10199->5877|10229->5878|10259->5879|10364->5955|10394->5956|10424->5957|10565->6070|10594->6071|10623->6072|10750->6170|10780->6171|10810->6172|10897->6230|10927->6231|10957->6232|11032->6278|11062->6279|11092->6280|11157->6317|11187->6318|11217->6319|11342->6415|11372->6416|11402->6417|11570->6556|11600->6557|11630->6558|11688->6587|11718->6588|11759->6600|11859->6672|11888->6673|11923->6680|11998->6726|12028->6727|12068->6738|12279->6921|12309->6922|12343->6928|12447->7003|12477->7004|12519->7017|12711->7181|12741->7182|12773->7186|12820->7204|12850->7205|12885->7212|12998->7297|13027->7298|13079->7322|13163->7377|13193->7378|13230->7387|13347->7476|13376->7477|13427->7500|13481->7525|13511->7526|13548->7535|13643->7602|13672->7603|13705->7608|13815->7689|13845->7690|13878->7695|13992->7780|14022->7781|14067->7798|14180->7882|14210->7883|14243->7888|14308->7924|14338->7925|14371->7930|14434->7964|14464->7965|14497->7970|14540->7985|14569->7986|14607->7996|14813->8175|14851->8196|14892->8198|14924->8202|14968->8218|14982->8222|15014->8232|15045->8235|15059->8239|15093->8251|15137->8264|15168->8267|16754->9825|16770->9831|16813->9852|19162->12173|19192->12181|19268->12229|19299->12238
                  LINES: 27->1|32->1|34->3|37->6|37->6|37->6|38->7|38->7|38->7|39->8|39->8|39->8|40->9|40->9|40->9|41->10|41->10|41->10|42->11|42->11|42->11|47->16|47->16|47->16|48->17|48->17|48->17|49->18|49->18|49->18|50->19|50->19|50->19|51->20|51->20|51->20|52->21|52->21|52->21|53->22|53->22|53->22|54->23|54->23|54->23|55->24|55->24|55->24|56->25|56->25|56->25|57->26|57->26|57->26|58->27|58->27|58->27|59->28|59->28|59->28|60->29|60->29|60->29|61->30|61->30|61->30|62->31|62->31|62->31|63->32|63->32|63->32|64->33|64->33|64->33|65->34|65->34|65->34|66->35|66->35|66->35|67->36|67->36|67->36|68->37|68->37|68->37|69->38|69->38|69->38|72->41|72->41|72->41|75->44|75->44|75->44|102->71|104->73|106->75|106->75|107->76|110->79|110->79|111->80|112->81|112->81|113->82|114->83|114->83|115->84|116->85|116->85|117->86|119->88|119->88|120->89|121->90|121->90|122->91|123->92|123->92|124->93|124->93|124->93|125->94|131->100|131->100|132->101|132->101|132->101|133->102|141->110|141->110|143->112|143->112|143->112|143->112|150->119|150->119|150->119|151->120|151->120|151->120|158->127|158->127|158->127|160->129|160->129|160->129|166->135|166->135|166->135|167->136|167->136|167->136|171->140|171->140|171->140|173->142|173->142|173->142|177->146|177->146|177->146|179->148|179->148|179->148|181->150|181->150|181->150|183->152|183->152|183->152|185->154|185->154|185->154|187->156|187->156|187->156|192->161|192->161|192->161|195->164|195->164|196->165|199->168|199->168|202->171|202->171|202->171|203->172|210->179|210->179|212->181|212->181|212->181|213->182|219->188|219->188|221->190|221->190|221->190|222->191|225->194|225->194|228->197|228->197|228->197|229->198|232->201|232->201|236->205|236->205|236->205|237->206|240->209|240->209|242->211|242->211|242->211|243->212|247->216|247->216|250->219|250->219|250->219|251->220|252->221|252->221|254->223|254->223|254->223|255->224|256->225|256->225|258->227|266->235|266->235|266->235|267->236|267->236|267->236|267->236|267->236|267->236|267->236|268->237|269->238|301->270|301->270|301->270|373->342|373->342|374->343|374->343
                  -- GENERATED --
              */
          