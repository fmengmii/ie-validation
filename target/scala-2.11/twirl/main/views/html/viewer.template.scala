
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
			  routes.javascript.Application.frameInstanceValidated
              )),format.raw/*70.16*/("""

		"""),format.raw/*72.3*/("""<style>
	        html, body
	        """),format.raw/*74.10*/("""{"""),format.raw/*74.11*/("""
	            """),format.raw/*75.14*/("""width: 100%;
	            height: 100%;
	            overflow: hidden;
	        """),format.raw/*78.10*/("""}"""),format.raw/*78.11*/("""
	        """),format.raw/*79.10*/("""span.highlight
	        """),format.raw/*80.10*/("""{"""),format.raw/*80.11*/("""
	        	"""),format.raw/*81.11*/("""background-color: lightblue;
	        """),format.raw/*82.10*/("""}"""),format.raw/*82.11*/("""
	        """),format.raw/*83.10*/("""#dataElementTable .jqx-grid-cell-pinned
		    """),format.raw/*84.7*/("""{"""),format.raw/*84.8*/("""
		        """),format.raw/*85.11*/("""background-color: lightyellow;
		        font-weight: bold;
		    """),format.raw/*87.7*/("""}"""),format.raw/*87.8*/("""
		    """),format.raw/*88.7*/("""#dataElementTable .jqx-widget-header
		    """),format.raw/*89.7*/("""{"""),format.raw/*89.8*/("""
			    """),format.raw/*90.8*/("""font-weight: bold;
		    """),format.raw/*91.7*/("""}"""),format.raw/*91.8*/("""
		    """),format.raw/*92.7*/(""".unselectable """),format.raw/*92.21*/("""{"""),format.raw/*92.22*/("""
			    """),format.raw/*93.8*/("""-webkit-touch-callout: none;
			    -webkit-user-select: none;
			    -khtml-user-select: none;
			    -moz-user-select: none;
			    -ms-user-select: none;
			    user-select: none;
			"""),format.raw/*99.4*/("""}"""),format.raw/*99.5*/("""
			"""),format.raw/*100.4*/("""#dataElementTable textarea """),format.raw/*100.31*/("""{"""),format.raw/*100.32*/("""
			    """),format.raw/*101.8*/("""border: 0 none white;
			    overflow-y: scroll;
			    padding: 0;
			    outline: none;
			    background-color: #D0D0D0;
			    resize: none;
			    height: 300px;
			    width: 90%;
			"""),format.raw/*109.4*/("""}"""),format.raw/*109.5*/("""
			
"""),format.raw/*111.1*/("""/* 			.select2-container """),format.raw/*111.26*/("""{"""),format.raw/*111.27*/(""" """),format.raw/*111.28*/("""*/
/* 				font-size: 12px; */
/* 				width: 50px; */
/* 				margin: 0px; */
/* 				padding: 0px; */
/* 				box-sizing: content-box; */
/* 				vertical-align: middle; */
/* 			"""),format.raw/*118.7*/("""}"""),format.raw/*118.8*/(""" """),format.raw/*118.9*/("""*/
/* 			.select2-container .select2-selection--single """),format.raw/*119.53*/("""{"""),format.raw/*119.54*/(""" """),format.raw/*119.55*/("""*/
/* 				height: 15px; */
/* 				padding: 0px; */
/* 				margin: 0px; */
/* 				vertical-align: top; */
/* 				box-sizing: content-box; */
/* 				text-align: center; */
/* 			"""),format.raw/*126.7*/("""}"""),format.raw/*126.8*/(""" """),format.raw/*126.9*/("""*/
			
/* 			.select2-container .select2-selection--single .select2-selection__rendered """),format.raw/*128.82*/("""{"""),format.raw/*128.83*/(""" """),format.raw/*128.84*/("""*/
/* 		      padding: 0px; */
/* 		      margin: 0px; */
/* 		      text-align: center; */
/* 		      vertical-align: top; */
/* 		      box-sizing: content-box; */
/* 		     """),format.raw/*134.11*/("""}"""),format.raw/*134.12*/(""" """),format.raw/*134.13*/("""*/
/* 		     .select2-dropdown """),format.raw/*135.29*/("""{"""),format.raw/*135.30*/(""" """),format.raw/*135.31*/("""*/
/* 				text-align: center; */
/* 				border-radius: 0px; */
/* 				box-sizing: content-box; */
/* 		     """),format.raw/*139.11*/("""}"""),format.raw/*139.12*/(""" """),format.raw/*139.13*/("""*/
		     
/* 		     .select2-container--default .select2-selection--single """),format.raw/*141.66*/("""{"""),format.raw/*141.67*/(""" """),format.raw/*141.68*/("""*/
/* 				  background-color: #fff; */
/* 				  border: 1px solid #aaa; */
/* 				  border-radius: 0px; */
/* 			"""),format.raw/*145.7*/("""}"""),format.raw/*145.8*/(""" """),format.raw/*145.9*/("""*/
			
/* 			 .select2-container--default .select2-selection--single .select2-selection__rendered """),format.raw/*147.92*/("""{"""),format.raw/*147.93*/(""" """),format.raw/*147.94*/("""*/
/* 			    color: #444; */
/* 			    line-height: 20px; """),format.raw/*149.30*/("""}"""),format.raw/*149.31*/(""" """),format.raw/*149.32*/("""*/
			    
/* 			    .select2-results__option """),format.raw/*151.36*/("""{"""),format.raw/*151.37*/(""" """),format.raw/*151.38*/("""*/
/* 				  padding: 0px; */
/* 				 """),format.raw/*153.9*/("""}"""),format.raw/*153.10*/(""" """),format.raw/*153.11*/("""*/
			
/* 			  .select2-container--default .select2-selection--single .select2-selection__arrow """),format.raw/*155.90*/("""{"""),format.raw/*155.91*/(""" """),format.raw/*155.92*/("""*/
/* 				    height: 30px; */
/* 				    position: absolute; */
/* 				    top: 1px; */
/* 				    right: 1px; */
/* 				    width: 20px; """),format.raw/*160.25*/("""}"""),format.raw/*160.26*/(""" """),format.raw/*160.27*/("""*/


					.select2-container """),format.raw/*163.25*/("""{"""),format.raw/*163.26*/(""" 
					 				"""),format.raw/*164.11*/("""font-size: 12px; 
					 				width: 100px;
					 				padding: 0px;
					 """),format.raw/*167.7*/("""}"""),format.raw/*167.8*/("""


				"""),format.raw/*170.5*/(""".select2-container .select2-selection--single """),format.raw/*170.51*/("""{"""),format.raw/*170.52*/(""" 
				 				"""),format.raw/*171.10*/("""padding: 0px; 
				 				margin: 0px; 
				 				vertical-align: top; 
				 				box-sizing: content-box; 
				 				text-align: center;
								height: 15px;					 				
				 				
				 			"""),format.raw/*178.9*/("""}"""),format.raw/*178.10*/(""" 

			"""),format.raw/*180.4*/(""".select2-container .select2-selection--single .select2-selection__rendered """),format.raw/*180.79*/("""{"""),format.raw/*180.80*/(""" 
			 	      """),format.raw/*181.12*/("""padding: 0px; 
			 		      margin: 0px; 
			 		      text-align: center; 
			 		      vertical-align: top; 
					      box-sizing: content-box;
					      
 		     """),format.raw/*187.9*/("""}"""),format.raw/*187.10*/("""

		"""),format.raw/*189.3*/(""".select2-dropdown """),format.raw/*189.21*/("""{"""),format.raw/*189.22*/(""" 
 				"""),format.raw/*190.6*/("""text-align: center; 
 				border-radius: 0px; 
 				box-sizing: content-box; 
		     """),format.raw/*193.8*/("""}"""),format.raw/*193.9*/("""
		     
		     
		     """),format.raw/*196.8*/(""".select2-container--default .select2-selection--single """),format.raw/*196.63*/("""{"""),format.raw/*196.64*/(""" 
 				  """),format.raw/*197.8*/("""background-color: #fff; 
 				  border: 1px solid #aaa; 
 				  border-radius: 0px; 
 			"""),format.raw/*200.5*/("""}"""),format.raw/*200.6*/("""
 			

			    
 			    """),format.raw/*204.9*/(""".select2-results__option """),format.raw/*204.34*/("""{"""),format.raw/*204.35*/(""" 
 				  """),format.raw/*205.8*/("""padding: 0px;
 				  margin: 0px;
 				 """),format.raw/*207.7*/("""}"""),format.raw/*207.8*/(""" 

  """),format.raw/*209.3*/(""".select2-container--default .select2-selection--single .select2-selection__arrow """),format.raw/*209.84*/("""{"""),format.raw/*209.85*/("""
    """),format.raw/*210.5*/("""height: 15px;
    position: absolute;
    top: 1px;
    right: 1px;
    width: 20px; """),format.raw/*214.18*/("""}"""),format.raw/*214.19*/("""
    
    
      """),format.raw/*217.7*/(""".select2-container--default .select2-selection--single .select2-selection__rendered """),format.raw/*217.91*/("""{"""),format.raw/*217.92*/("""
    """),format.raw/*218.5*/("""color: #444;
    line-height: 15px; """),format.raw/*219.24*/("""}"""),format.raw/*219.25*/("""

			
	    """),format.raw/*222.6*/("""</style>
	</head>

	
	<body>
	<div id="buttonsDiv">
		<select id='projSelect' onchange="loadProject(this.value)">
			<option selected disabled value=''>Select Project</option>
		"""),_display_(/*230.4*/for(proj <- projList) yield /*230.25*/ {_display_(Seq[Any](format.raw/*230.27*/("""
			"""),format.raw/*231.4*/("""<option value='"""),_display_(/*231.20*/proj/*231.24*/.get("id")),format.raw/*231.34*/("""'>"""),_display_(/*231.37*/proj/*231.41*/.get("name")),format.raw/*231.53*/("""</option>
		""")))}),format.raw/*232.4*/("""
		"""),format.raw/*233.3*/("""</select>
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
        <a href=""""),_display_(/*265.19*/routes/*265.25*/.Application.logout()),format.raw/*265.46*/(""""><button type="button" onclick="logout()">Log Out</button></a>
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
							<!--<button type="button" onclick="location.href='/validated'" id="validatedButton" value="validated">Validated</button>-->
							<button type="button" onclick="frameInstanceValidated()" id="validatedButton" value="validated">Validated</button>
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
    

	<input type='hidden' id='colNames' value='"""),_display_(/*338.45*/colNames),format.raw/*338.53*/("""'>
	<input type='hidden' id='colValues' value='"""),_display_(/*339.46*/colValues),format.raw/*339.55*/("""'>

    
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
                  DATE: Wed Mar 01 09:29:13 PST 2017
                  SOURCE: /home/wyu/workspace-luna/ie-validation-github-dev-BackUp-022417/app/views/viewer.scala.html
                  HASH: cdf483f35f8d2c7e9a3d2b43a76fa71001c15418
                  MATRIX: 781->1|949->74|977->76|1049->122|1063->128|1117->162|1180->199|1194->205|1252->243|1315->280|1329->286|1393->330|1456->367|1470->373|1552->435|1616->472|1631->478|1691->517|1755->554|1770->560|1832->601|1961->703|1976->709|2042->754|2097->782|2112->788|2171->826|2226->854|2241->860|2298->896|2356->927|2371->933|2432->973|2490->1004|2505->1010|2563->1047|2621->1078|2636->1084|2696->1123|2754->1154|2769->1160|2831->1201|2889->1232|2904->1238|2963->1276|3021->1307|3036->1313|3093->1349|3151->1380|3166->1386|3229->1428|3284->1456|3299->1462|3366->1508|3421->1536|3436->1542|3498->1583|3553->1611|3568->1617|3639->1667|3694->1695|3709->1701|3766->1737|3821->1765|3836->1771|3898->1812|3953->1840|3968->1846|4027->1884|4082->1912|4097->1918|4157->1957|4212->1985|4227->1991|4292->2035|4347->2063|4362->2069|4429->2115|4484->2143|4499->2149|4570->2199|4625->2227|4640->2233|4722->2294|4777->2322|4792->2328|4849->2364|4904->2392|4919->2398|4980->2438|5076->2507|5091->2513|5147->2548|5242->2616|5257->2622|5295->2651|6791->4126|6822->4130|6887->4167|6916->4168|6958->4182|7066->4262|7095->4263|7133->4273|7185->4297|7214->4298|7253->4309|7319->4347|7348->4348|7386->4358|7459->4404|7487->4405|7526->4416|7619->4482|7647->4483|7681->4490|7751->4533|7779->4534|7814->4542|7866->4567|7894->4568|7928->4575|7970->4589|7999->4590|8034->4598|8247->4784|8275->4785|8307->4789|8363->4816|8393->4817|8429->4825|8646->5014|8675->5015|8708->5020|8762->5045|8792->5046|8822->5047|9025->5222|9054->5223|9083->5224|9167->5279|9197->5280|9227->5281|9431->5457|9460->5458|9489->5459|9606->5547|9636->5548|9666->5549|9871->5725|9901->5726|9931->5727|9991->5758|10021->5759|10051->5760|10188->5868|10218->5869|10248->5870|10353->5946|10383->5947|10413->5948|10554->6061|10583->6062|10612->6063|10739->6161|10769->6162|10799->6163|10886->6221|10916->6222|10946->6223|11021->6269|11051->6270|11081->6271|11146->6308|11176->6309|11206->6310|11331->6406|11361->6407|11391->6408|11559->6547|11589->6548|11619->6549|11677->6578|11707->6579|11748->6591|11848->6663|11877->6664|11912->6671|11987->6717|12017->6718|12057->6729|12268->6912|12298->6913|12332->6919|12436->6994|12466->6995|12508->7008|12700->7172|12730->7173|12762->7177|12809->7195|12839->7196|12874->7203|12987->7288|13016->7289|13068->7313|13152->7368|13182->7369|13219->7378|13336->7467|13365->7468|13416->7491|13470->7516|13500->7517|13537->7526|13605->7566|13634->7567|13667->7572|13777->7653|13807->7654|13840->7659|13954->7744|13984->7745|14029->7762|14142->7846|14172->7847|14205->7852|14270->7888|14300->7889|14339->7900|14545->8079|14583->8100|14624->8102|14656->8106|14700->8122|14714->8126|14746->8136|14777->8139|14791->8143|14825->8155|14869->8168|14900->8171|16486->9729|16502->9735|16545->9756|19034->12217|19064->12225|19140->12273|19171->12282
                  LINES: 27->1|32->1|34->3|37->6|37->6|37->6|38->7|38->7|38->7|39->8|39->8|39->8|40->9|40->9|40->9|41->10|41->10|41->10|42->11|42->11|42->11|47->16|47->16|47->16|48->17|48->17|48->17|49->18|49->18|49->18|50->19|50->19|50->19|51->20|51->20|51->20|52->21|52->21|52->21|53->22|53->22|53->22|54->23|54->23|54->23|55->24|55->24|55->24|56->25|56->25|56->25|57->26|57->26|57->26|58->27|58->27|58->27|59->28|59->28|59->28|60->29|60->29|60->29|61->30|61->30|61->30|62->31|62->31|62->31|63->32|63->32|63->32|64->33|64->33|64->33|65->34|65->34|65->34|66->35|66->35|66->35|67->36|67->36|67->36|68->37|68->37|68->37|69->38|69->38|69->38|72->41|72->41|72->41|75->44|75->44|75->44|101->70|103->72|105->74|105->74|106->75|109->78|109->78|110->79|111->80|111->80|112->81|113->82|113->82|114->83|115->84|115->84|116->85|118->87|118->87|119->88|120->89|120->89|121->90|122->91|122->91|123->92|123->92|123->92|124->93|130->99|130->99|131->100|131->100|131->100|132->101|140->109|140->109|142->111|142->111|142->111|142->111|149->118|149->118|149->118|150->119|150->119|150->119|157->126|157->126|157->126|159->128|159->128|159->128|165->134|165->134|165->134|166->135|166->135|166->135|170->139|170->139|170->139|172->141|172->141|172->141|176->145|176->145|176->145|178->147|178->147|178->147|180->149|180->149|180->149|182->151|182->151|182->151|184->153|184->153|184->153|186->155|186->155|186->155|191->160|191->160|191->160|194->163|194->163|195->164|198->167|198->167|201->170|201->170|201->170|202->171|209->178|209->178|211->180|211->180|211->180|212->181|218->187|218->187|220->189|220->189|220->189|221->190|224->193|224->193|227->196|227->196|227->196|228->197|231->200|231->200|235->204|235->204|235->204|236->205|238->207|238->207|240->209|240->209|240->209|241->210|245->214|245->214|248->217|248->217|248->217|249->218|250->219|250->219|253->222|261->230|261->230|261->230|262->231|262->231|262->231|262->231|262->231|262->231|262->231|263->232|264->233|296->265|296->265|296->265|369->338|369->338|370->339|370->339
                  -- GENERATED --
              */
          