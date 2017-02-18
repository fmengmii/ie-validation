
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
              routes.javascript.Application.getHistory

              
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
 				 """),format.raw/*208.7*/("""}"""),format.raw/*208.8*/(""" 

  """),format.raw/*210.3*/(""".select2-container--default .select2-selection--single .select2-selection__arrow """),format.raw/*210.84*/("""{"""),format.raw/*210.85*/("""
    """),format.raw/*211.5*/("""height: 15px;
    position: absolute;
    top: 1px;
    right: 1px;
    width: 20px; """),format.raw/*215.18*/("""}"""),format.raw/*215.19*/("""
    
    
      """),format.raw/*218.7*/(""".select2-container--default .select2-selection--single .select2-selection__rendered """),format.raw/*218.91*/("""{"""),format.raw/*218.92*/("""
    """),format.raw/*219.5*/("""color: #444;
    line-height: 15px; """),format.raw/*220.24*/("""}"""),format.raw/*220.25*/("""

			
	    """),format.raw/*223.6*/("""</style>
	</head>

	
	<body>
	<div id="buttonsDiv">
		<select id='projSelect' onchange="loadProject(this.value)">
			<option selected disabled value=''>Select Project</option>
		"""),_display_(/*231.4*/for(proj <- projList) yield /*231.25*/ {_display_(Seq[Any](format.raw/*231.27*/("""
			"""),format.raw/*232.4*/("""<option value='"""),_display_(/*232.20*/proj/*232.24*/.get("id")),format.raw/*232.34*/("""'>"""),_display_(/*232.37*/proj/*232.41*/.get("name")),format.raw/*232.53*/("""</option>
		""")))}),format.raw/*233.4*/("""
		"""),format.raw/*234.3*/("""</select>
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
        <a href=""""),_display_(/*266.19*/routes/*266.25*/.Application.logout()),format.raw/*266.46*/(""""><button type="button" onclick="logout()">Log Out</button></a>
        <!--end of new code-->
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
    

	<input type='hidden' id='colNames' value='"""),_display_(/*322.45*/colNames),format.raw/*322.53*/("""'>
	<input type='hidden' id='colValues' value='"""),_display_(/*323.46*/colValues),format.raw/*323.55*/("""'>

    
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
                  DATE: Fri Feb 17 17:15:22 PST 2017
                  SOURCE: /home/wyu/workspace-luna/ie-validation-github/app/views/viewer.scala.html
                  HASH: f4cff392bcaf719654169628dc1b9916cbe26c96
                  MATRIX: 781->1|949->74|977->76|1049->122|1063->128|1117->162|1180->199|1194->205|1252->243|1315->280|1329->286|1393->330|1456->367|1470->373|1552->435|1616->472|1631->478|1691->517|1755->554|1770->560|1832->601|1965->707|1980->713|2046->758|2101->786|2116->792|2175->830|2230->858|2245->864|2302->900|2360->931|2375->937|2436->977|2494->1008|2509->1014|2567->1051|2625->1082|2640->1088|2700->1127|2758->1158|2773->1164|2835->1205|2893->1236|2908->1242|2967->1280|3025->1311|3040->1317|3097->1353|3155->1384|3170->1390|3233->1432|3288->1460|3303->1466|3370->1512|3425->1540|3440->1546|3502->1587|3557->1615|3572->1621|3643->1671|3698->1699|3713->1705|3770->1741|3825->1769|3840->1775|3902->1816|3957->1844|3972->1850|4031->1888|4086->1916|4101->1922|4161->1961|4216->1989|4231->1995|4296->2039|4351->2067|4366->2073|4433->2119|4488->2147|4503->2153|4574->2203|4629->2231|4644->2237|4726->2298|4781->2326|4796->2332|4853->2368|4908->2396|4923->2402|4984->2442|5080->2511|5095->2517|5151->2552|5246->2620|5261->2626|5299->2655|6752->4087|6783->4091|6848->4128|6877->4129|6919->4143|7027->4223|7056->4224|7094->4234|7146->4258|7175->4259|7214->4270|7280->4308|7309->4309|7347->4319|7420->4365|7448->4366|7487->4377|7580->4443|7608->4444|7642->4451|7712->4494|7740->4495|7775->4503|7827->4528|7855->4529|7889->4536|7931->4550|7960->4551|7995->4559|8209->4745|8238->4746|8270->4750|8326->4777|8356->4778|8392->4786|8609->4975|8638->4976|8671->4981|8725->5006|8755->5007|8785->5008|8988->5183|9017->5184|9046->5185|9130->5240|9160->5241|9190->5242|9394->5418|9423->5419|9452->5420|9569->5508|9599->5509|9629->5510|9834->5686|9864->5687|9894->5688|9954->5719|9984->5720|10014->5721|10151->5829|10181->5830|10211->5831|10316->5907|10346->5908|10376->5909|10517->6022|10546->6023|10575->6024|10702->6122|10732->6123|10762->6124|10849->6182|10879->6183|10909->6184|10984->6230|11014->6231|11044->6232|11109->6269|11139->6270|11169->6271|11294->6367|11324->6368|11354->6369|11522->6508|11552->6509|11582->6510|11640->6539|11670->6540|11711->6552|11811->6624|11840->6625|11875->6632|11950->6678|11980->6679|12020->6690|12231->6873|12261->6874|12295->6880|12399->6955|12429->6956|12471->6969|12663->7133|12693->7134|12725->7138|12772->7156|12802->7157|12837->7164|12950->7249|12979->7250|13031->7274|13115->7329|13145->7330|13182->7339|13299->7428|13328->7429|13379->7452|13433->7477|13463->7478|13500->7487|13568->7527|13597->7528|13630->7533|13740->7614|13770->7615|13803->7620|13917->7705|13947->7706|13992->7723|14105->7807|14135->7808|14168->7813|14233->7849|14263->7850|14302->7861|14508->8040|14546->8061|14587->8063|14619->8067|14663->8083|14677->8087|14709->8097|14740->8100|14754->8104|14788->8116|14832->8129|14863->8132|16449->9690|16465->9696|16508->9717|18283->11464|18313->11472|18389->11520|18420->11529
                  LINES: 27->1|32->1|34->3|37->6|37->6|37->6|38->7|38->7|38->7|39->8|39->8|39->8|40->9|40->9|40->9|41->10|41->10|41->10|42->11|42->11|42->11|47->16|47->16|47->16|48->17|48->17|48->17|49->18|49->18|49->18|50->19|50->19|50->19|51->20|51->20|51->20|52->21|52->21|52->21|53->22|53->22|53->22|54->23|54->23|54->23|55->24|55->24|55->24|56->25|56->25|56->25|57->26|57->26|57->26|58->27|58->27|58->27|59->28|59->28|59->28|60->29|60->29|60->29|61->30|61->30|61->30|62->31|62->31|62->31|63->32|63->32|63->32|64->33|64->33|64->33|65->34|65->34|65->34|66->35|66->35|66->35|67->36|67->36|67->36|68->37|68->37|68->37|69->38|69->38|69->38|72->41|72->41|72->41|75->44|75->44|75->44|102->71|104->73|106->75|106->75|107->76|110->79|110->79|111->80|112->81|112->81|113->82|114->83|114->83|115->84|116->85|116->85|117->86|119->88|119->88|120->89|121->90|121->90|122->91|123->92|123->92|124->93|124->93|124->93|125->94|131->100|131->100|132->101|132->101|132->101|133->102|141->110|141->110|143->112|143->112|143->112|143->112|150->119|150->119|150->119|151->120|151->120|151->120|158->127|158->127|158->127|160->129|160->129|160->129|166->135|166->135|166->135|167->136|167->136|167->136|171->140|171->140|171->140|173->142|173->142|173->142|177->146|177->146|177->146|179->148|179->148|179->148|181->150|181->150|181->150|183->152|183->152|183->152|185->154|185->154|185->154|187->156|187->156|187->156|192->161|192->161|192->161|195->164|195->164|196->165|199->168|199->168|202->171|202->171|202->171|203->172|210->179|210->179|212->181|212->181|212->181|213->182|219->188|219->188|221->190|221->190|221->190|222->191|225->194|225->194|228->197|228->197|228->197|229->198|232->201|232->201|236->205|236->205|236->205|237->206|239->208|239->208|241->210|241->210|241->210|242->211|246->215|246->215|249->218|249->218|249->218|250->219|251->220|251->220|254->223|262->231|262->231|262->231|263->232|263->232|263->232|263->232|263->232|263->232|263->232|264->233|265->234|297->266|297->266|297->266|353->322|353->322|354->323|354->323
                  -- GENERATED --
              */
          