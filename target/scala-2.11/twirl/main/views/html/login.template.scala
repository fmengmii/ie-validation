
package views.html

import play.twirl.api._
import play.twirl.api.TemplateMagic._


     object login_Scope0 {
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

class login extends BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with play.twirl.api.Template2[Boolean,Boolean,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(isCorrect: Boolean, needToRegister: Boolean):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*1.47*/("""
"""),format.raw/*2.1*/("""<!DOCTYPE html>
<html>
      <head>
            <link href=""""),_display_(/*5.26*/routes/*5.32*/.Assets.at("stylesheets/main.css")),format.raw/*5.66*/("""" rel="stylesheet" />
		<link href=""""),_display_(/*6.16*/routes/*6.22*/.Assets.at("stylesheets/jqx.base.css")),format.raw/*6.60*/("""" rel="stylesheet" />
		<link href=""""),_display_(/*7.16*/routes/*7.22*/.Assets.at("stylesheets/jqx.energyblue.css")),format.raw/*7.66*/("""" rel="stylesheet" />
		<link href=""""),_display_(/*8.16*/routes/*8.22*/.Assets.at("stylesheets/jquery.highlight-within-textarea.css")),format.raw/*8.84*/("""" rel="stylesheet" />
		<link href=""""),_display_(/*9.16*/routes/*9.22*/.Assets.at("stylesheets/jquery-ui.css")),format.raw/*9.61*/("""" rel="stylesheet" />
		<link href=""""),_display_(/*10.16*/routes/*10.22*/.Assets.at("stylesheets/select2.min.css")),format.raw/*10.63*/("""" rel="stylesheet" />
          <title>Validation Viewer Login</title>
          <meta charset=utf-8 />
          
          
          
            <script src=""""),_display_(/*16.27*/routes/*16.33*/.Assets.at("javascripts/jquery-2.1.3.min.js")),format.raw/*16.78*/(""""></script>
	        <script src=""""),_display_(/*17.24*/routes/*17.30*/.Assets.at("javascripts/jquery-ui.js")),format.raw/*17.68*/(""""></script>
		    <script src=""""),_display_(/*18.21*/routes/*18.27*/.Assets.at("javascripts/jqxcore.js")),format.raw/*18.63*/(""""></script>
	        <script src=""""),_display_(/*19.24*/routes/*19.30*/.Assets.at("javascripts/jqxsplitter.js")),format.raw/*19.70*/(""""></script>
	        <script src=""""),_display_(/*20.24*/routes/*20.30*/.Assets.at("javascripts/jqxpanel.js")),format.raw/*20.67*/(""""></script> 
    	    <script src=""""),_display_(/*21.24*/routes/*21.30*/.Assets.at("javascripts/jqxbuttons.js")),format.raw/*21.69*/(""""></script>
    	    <script src=""""),_display_(/*22.24*/routes/*22.30*/.Assets.at("javascripts/jqxscrollbar.js")),format.raw/*22.71*/(""""></script>
    	    <script src=""""),_display_(/*23.24*/routes/*23.30*/.Assets.at("javascripts/jqxwindow.js")),format.raw/*23.68*/(""""></script>
    	   	<script src=""""),_display_(/*24.24*/routes/*24.30*/.Assets.at("javascripts/jqxgrid.js")),format.raw/*24.66*/(""""></script>
    	   	<script src=""""),_display_(/*25.24*/routes/*25.30*/.Assets.at("javascripts/jqxgrid.pager.js")),format.raw/*25.72*/(""""></script>
    		<script src=""""),_display_(/*26.21*/routes/*26.27*/.Assets.at("javascripts/jqxgrid.selection.js")),format.raw/*26.73*/(""""></script>
    		<script src=""""),_display_(/*27.21*/routes/*27.27*/.Assets.at("javascripts/jqxgrid.sort.js")),format.raw/*27.68*/(""""></script>
    		<script src=""""),_display_(/*28.21*/routes/*28.27*/.Assets.at("javascripts/jqxgrid.columnsresize.js")),format.raw/*28.77*/(""""></script>
    		<script src=""""),_display_(/*29.21*/routes/*29.27*/.Assets.at("javascripts/jqxdata.js")),format.raw/*29.63*/(""""></script>
    		<script src=""""),_display_(/*30.21*/routes/*30.27*/.Assets.at("javascripts/jqxdatatable.js")),format.raw/*30.68*/(""""></script>
    		<script src=""""),_display_(/*31.21*/routes/*31.27*/.Assets.at("javascripts/jqxeditor.js")),format.raw/*31.65*/(""""></script>
    		<script src=""""),_display_(/*32.21*/routes/*32.27*/.Assets.at("javascripts/jqxlistbox.js")),format.raw/*32.66*/(""""></script>
    		<script src=""""),_display_(/*33.21*/routes/*33.27*/.Assets.at("javascripts/jqxdropdownlist.js")),format.raw/*33.71*/(""""></script>
    		<script src=""""),_display_(/*34.21*/routes/*34.27*/.Assets.at("javascripts/jqxdropdownbutton.js")),format.raw/*34.73*/(""""></script>
    		<script src=""""),_display_(/*35.21*/routes/*35.27*/.Assets.at("javascripts/jquery-fieldselection.js")),format.raw/*35.77*/(""""></script>
    		<script src=""""),_display_(/*36.21*/routes/*36.27*/.Assets.at("javascripts/jquery.highlight-within-textarea.js")),format.raw/*36.88*/(""""></script>
    		<script src=""""),_display_(/*37.21*/routes/*37.27*/.Assets.at("javascripts/jqxmenu.js")),format.raw/*37.63*/(""""></script>
    		<script src=""""),_display_(/*38.21*/routes/*38.27*/.Assets.at("javascripts/select2.min.js")),format.raw/*38.67*/(""""></script>
			    
    	    <!-- our own scripts -->
    	    <!--<script src=""""),_display_(/*41.28*/routes/*41.34*/.Assets.at("javascripts/viewer.js")),format.raw/*41.69*/(""""></script>-->
    	    <script src=""""),_display_(/*42.24*/routes/*42.30*/.Assets.at("javascripts/login.js")),format.raw/*42.64*/(""""></script>
    	    
    	    <!--new-->
    	    <!--<script src=""""),_display_(/*45.28*/routes/*45.34*/.Assets.at("javascripts/jquery.contextMenu.js")),format.raw/*45.81*/(""""></script>-->
    	    
    	     """),_display_(/*47.12*/helper/*47.18*/.javascriptRouter("jsRoutes")/*47.47*/(
              routes.javascript.Application.authenticate,
              routes.javascript.Application.login
              //routes.javascript.Application.createUser
              )),format.raw/*51.16*/("""

          """),format.raw/*53.11*/("""<style>
            html, body
    	        """),format.raw/*55.14*/("""{"""),format.raw/*55.15*/("""
    	            """),format.raw/*56.18*/("""width: 100%;
    	            height: 100%;
    	            overflow: hidden;
    	        """),format.raw/*59.14*/("""}"""),format.raw/*59.15*/("""
    	    """),format.raw/*60.10*/("""#title 
    	        """),format.raw/*61.14*/("""{"""),format.raw/*61.15*/("""
                    """),format.raw/*62.21*/("""font-size: 2em;
                """),format.raw/*63.17*/("""}"""),format.raw/*63.18*/("""

            """),format.raw/*65.13*/("""#login 
                """),format.raw/*66.17*/("""{"""),format.raw/*66.18*/("""
                """),format.raw/*67.17*/("""line-height: 1.75em;
                """),format.raw/*68.17*/("""}"""),format.raw/*68.18*/("""

            """),format.raw/*70.13*/(""".input 
                """),format.raw/*71.17*/("""{"""),format.raw/*71.18*/("""
                """),format.raw/*72.17*/("""font-size: 1.5em;

                """),format.raw/*74.17*/("""}"""),format.raw/*74.18*/("""
            """),format.raw/*75.13*/("""p
                """),format.raw/*76.17*/("""{"""),format.raw/*76.18*/("""
                 """),format.raw/*77.18*/("""font-size: 1.2em;
                 color: red;
                """),format.raw/*79.17*/("""}"""),format.raw/*79.18*/("""
            """),format.raw/*80.13*/("""button
                """),format.raw/*81.17*/("""{"""),format.raw/*81.18*/("""
                    """),format.raw/*82.21*/("""font-size: 1.25em;
                    
                """),format.raw/*84.17*/("""}"""),format.raw/*84.18*/("""
            """),format.raw/*85.13*/("""td, p, h3
                """),format.raw/*86.17*/("""{"""),format.raw/*86.18*/("""
                    """),format.raw/*87.21*/("""font-family: 'Karla', sans-serif;
                """),format.raw/*88.17*/("""}"""),format.raw/*88.18*/("""
          """),format.raw/*89.11*/("""</style>

      </head>
    <body>
      <center>
        <div id='title'>
        <h1>
          login
        </h1>
        </div>
        
        <!--test code-->
        <!--<ul id="the-node">-->
        <!--    <li>right click me</li>-->
        <!--</ul>-->
        
       


        """),_display_(/*108.10*/if(!isCorrect)/*108.24*/ {_display_(Seq[Any](format.raw/*108.26*/("""
            """),format.raw/*109.13*/("""<p>incorrect username or password</p>
        """)))}),format.raw/*110.10*/("""
        """),format.raw/*111.9*/("""<div>
            <h2 id='createAccount'>
                
            </h2>
        </div>
        
        
        <div id='login'>
        <!--<form action='auth' method='post' name='loginForm'>-->
        
            
            """),_display_(/*122.14*/if(needToRegister)/*122.32*/ {_display_(Seq[Any](format.raw/*122.34*/("""
                """),format.raw/*123.17*/("""<form method = 'post' name='loginForm' >
                <input type="text" name="username" id="username" placeholder="username" class='input'>
                <br>
                <input type="password" name="password" id="password" placeholder="password" class='input'>
                <br>
                <!--<a href='add/user'><button type="button" class='register' id='register' onclick='add/user'>Register</button></a>-->
                <button type="submit" class='button' id='button' onclick="toLogIn()">Submit</button>
                </form>
            """)))}),format.raw/*131.14*/("""
            """),format.raw/*132.13*/("""<!--<input type="submit" value="submit" id='button' class='input'>-->
            <!--<button type="submit" class='button' id='button'>Submit</button>-->
            <!--<button type="submit" value="submit" id='button' onclick="toLogIn()">Submit</button>-->
            <!--"""),_display_(/*135.18*/if(!needToRegister)/*135.37*/ {_display_(Seq[Any](format.raw/*135.39*/("""-->
            <!--    <h3>create an account</h3>-->
            <!--    <form method = 'post' name='registerForm'>-->
            <!--    <table style='width: 40%'>-->
            <!--        <tr>-->
            <!--            <td>username:</td>-->
            <!--            <td><input type="text" name="username" id="username" class='input' size='32'></td>-->
            <!--        </tr>-->
            <!--        <tr>-->
            <!--            <td>password:</td>-->
            <!--            <td><input type="password" name="password" id="password" class='input' size='32'></td>-->
            <!--        </tr>-->
            <!--        <tr>-->
            <!--            <td>confirm password:</td>-->
            <!--            <td><input type="password" name="confirmPassword" id="confirmPassword" class='input' size='32'></td>-->
            <!--        </tr>-->
            <!--    </table>-->
            <!--    <button type="submit" class='registerButton' id='registerButton'>Create</button>-->
            <!--    </form>-->
            <!--""")))}),format.raw/*154.18*/("""-->
            
                         <!--<input type="submit" value="submit" id='button' class='input' href=""""),_display_(/*156.99*/routes/*156.105*/.Application.index),format.raw/*156.123*/(""""> -->
        
        </div>
        
        </center>  
    </body>








</html>
"""))
      }
    }
  }

  def render(isCorrect:Boolean,needToRegister:Boolean): play.twirl.api.HtmlFormat.Appendable = apply(isCorrect,needToRegister)

  def f:((Boolean,Boolean) => play.twirl.api.HtmlFormat.Appendable) = (isCorrect,needToRegister) => apply(isCorrect,needToRegister)

  def ref: this.type = this

}


}

/**/
object login extends login_Scope0.login
              /*
                  -- GENERATED --
                  DATE: Mon Feb 20 13:46:48 PST 2017
                  SOURCE: /home/wyu/workspace-luna/ie-validation-github/app/views/login.scala.html
                  HASH: 2bb7f43ab184a92cd8cf1413f1157e80197f2eae
                  MATRIX: 754->1|894->46|921->47|1008->108|1022->114|1076->148|1139->185|1153->191|1211->229|1274->266|1288->272|1352->316|1415->353|1429->359|1511->421|1574->458|1588->464|1647->503|1711->540|1726->546|1788->587|1978->750|1993->756|2059->801|2121->836|2136->842|2195->880|2254->912|2269->918|2326->954|2388->989|2403->995|2464->1035|2526->1070|2541->1076|2599->1113|2662->1149|2677->1155|2737->1194|2799->1229|2814->1235|2876->1276|2938->1311|2953->1317|3012->1355|3074->1390|3089->1396|3146->1432|3208->1467|3223->1473|3286->1515|3345->1547|3360->1553|3427->1599|3486->1631|3501->1637|3563->1678|3622->1710|3637->1716|3708->1766|3767->1798|3782->1804|3839->1840|3898->1872|3913->1878|3975->1919|4034->1951|4049->1957|4108->1995|4167->2027|4182->2033|4242->2072|4301->2104|4316->2110|4381->2154|4440->2186|4455->2192|4522->2238|4581->2270|4596->2276|4667->2326|4726->2358|4741->2364|4823->2425|4882->2457|4897->2463|4954->2499|5013->2531|5028->2537|5089->2577|5197->2658|5212->2664|5268->2699|5333->2737|5348->2743|5403->2777|5499->2846|5514->2852|5582->2899|5645->2935|5660->2941|5698->2970|5901->3152|5941->3164|6013->3208|6042->3209|6088->3227|6208->3319|6237->3320|6275->3330|6324->3351|6353->3352|6402->3373|6462->3405|6491->3406|6533->3420|6585->3444|6614->3445|6659->3462|6724->3499|6753->3500|6795->3514|6847->3538|6876->3539|6921->3556|6984->3591|7013->3592|7054->3605|7100->3623|7129->3624|7175->3642|7266->3705|7295->3706|7336->3719|7387->3742|7416->3743|7465->3764|7549->3820|7578->3821|7619->3834|7673->3860|7702->3861|7751->3882|7829->3932|7858->3933|7897->3944|8218->4237|8242->4251|8283->4253|8325->4266|8404->4313|8441->4322|8706->4559|8734->4577|8775->4579|8821->4596|9420->5163|9462->5176|9765->5451|9794->5470|9835->5472|10938->6543|11081->6658|11098->6664|11139->6682
                  LINES: 27->1|32->1|33->2|36->5|36->5|36->5|37->6|37->6|37->6|38->7|38->7|38->7|39->8|39->8|39->8|40->9|40->9|40->9|41->10|41->10|41->10|47->16|47->16|47->16|48->17|48->17|48->17|49->18|49->18|49->18|50->19|50->19|50->19|51->20|51->20|51->20|52->21|52->21|52->21|53->22|53->22|53->22|54->23|54->23|54->23|55->24|55->24|55->24|56->25|56->25|56->25|57->26|57->26|57->26|58->27|58->27|58->27|59->28|59->28|59->28|60->29|60->29|60->29|61->30|61->30|61->30|62->31|62->31|62->31|63->32|63->32|63->32|64->33|64->33|64->33|65->34|65->34|65->34|66->35|66->35|66->35|67->36|67->36|67->36|68->37|68->37|68->37|69->38|69->38|69->38|72->41|72->41|72->41|73->42|73->42|73->42|76->45|76->45|76->45|78->47|78->47|78->47|82->51|84->53|86->55|86->55|87->56|90->59|90->59|91->60|92->61|92->61|93->62|94->63|94->63|96->65|97->66|97->66|98->67|99->68|99->68|101->70|102->71|102->71|103->72|105->74|105->74|106->75|107->76|107->76|108->77|110->79|110->79|111->80|112->81|112->81|113->82|115->84|115->84|116->85|117->86|117->86|118->87|119->88|119->88|120->89|139->108|139->108|139->108|140->109|141->110|142->111|153->122|153->122|153->122|154->123|162->131|163->132|166->135|166->135|166->135|185->154|187->156|187->156|187->156
                  -- GENERATED --
              */
          