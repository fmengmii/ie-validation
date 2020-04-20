var elementIDMap = {};
var elementHTMLIDMap = {};
var gridData = new Array();
var gridData2 = new Array();
var frameInstanceData;
var highlightStart;
var highlightEnd;
var currFrameInstanceID;
var currRowIndex;
var currTableOffset;
var docNamespace;
var docTable;
var docID;
var docName;
var clickStart;
var clickEnd;
var clickValueElement;
var clickValue;
var valueClickCount1 = 0;
var valueClickCount2 = 0;
var origText = "";
var docHistory = {};
var currSelectedDoc;
var docIndexMap = {};
var frameArray;
var currFrameInstanceIndex;
var prevFrameInstanceIndex = -1;
var docFeatures;
var docFeatureKey;
var docFeatureValue = null;
var docFeatureRange = {start:-1,end:-1};
var docFeatureID = null;
var selectRange = {start:0, end:0};
var selectFlag;
var highlightRangeList = [];
var highlightRanges = [];
var highlightIndexes = [];
var annotList;
var highlightMenuStart;
var loadDialogCount = 0;
var highlightRangeMap = {};
var docSelectIndex = 0;
var tokenSelectFlag = false;
var entityIDStr = null;
var colNames = null;
var colTypes = null;
var colValues = null;


var crfSelectDisabled = false;
var crfSelectDisabled2 = false;

//new vars
//var username;
//var password;
var wordBasedHighlighting = false;
var history = new Object();
var processID = 1;

var userActions = 0;


$(document).ready(function () {
//$(function() {
	//init widgets (dialog window, split pane, etc)
   $('#splitter').jqxSplitter({ width: '100%', height: '100%', panels: [{size: '40%'}, {size: '60%'}]});

   /*
   $('#splitter').on('resize', function (event) {
	   loadFrameInstance(currFrameInstanceID, false);
	   //getDocument('{"docNamespace":"' + docNamespace + '","docTable":"' + docTable + '","docID":' + docID + "}");
   })
   */

   $('#splitter2').jqxSplitter({ width: '100%', height: '100%', panels: [{size: '30%'}, {size: '70%'}]});


   //$('#crfPanel').jqxPanel({ width: '100%', height: '100%', autoUpdate: true, sizeMode: 'fixed'});
   //$('#docPanel').jqxPanel({ width: '90%', height: '90%', autoUpdate: true, sizeMode : 'fixed'});
   
   


   $('#dialogLoadInstance').jqxWindow({
	   width: 200,
	       height: 100,
	       resizable: false,
	       //okButton: $('#okButton'),
	       //cancelButton: $('#cancelButton'),
	       isModal: true,
	       initContent: function() {
	   }
   });

   $('#dialogLoadInstance').jqxWindow('close');

   $('#dialogClearAll').jqxWindow({
	   width: 200,
       height: 100,
       resizable: false,
       okButton: $('#clearAllOKButton'),
       cancelButton: $('#clearAllCancelButton'),
       isModal: true
   });

   $('#dialogClearAll').jqxWindow('close');

   var docListBoxSource = [];
   $("#docListBox").jqxListBox({ selectedIndex: 0, source: docListBoxSource, width: '95%', height: '90%'});
   $('#docListBox').on('select', function(event) {

	   //docIndex = event.args.item.index;
	   //docHistory[event.args.item.value] = true;
	   var item = event.args.item;
	   
	   clog("selected doc: " + item.index + " docSelectIndex: " + docSelectIndex);


	   if (docSelectIndex == item.index)
		   return;

	   docSelectIndex = item.index;

	   getDocument(item.value, item.index, true, null, function () {
		   //clog("HERERERE!");
	 	   getHighlightRanges();
		   highlightText();
	   });
	   //addDocumentHistory(event.args.item.value);
	   //$('#docListBox').val(event.args.item.value);

   });


   $('#docListBox').jqxListBox({renderer: function(index, label, value) {
			if (docHistory[value] == true) {
				return "<font style='background-color: powderblue'>" + label + "</font>";
			}

			return label;
		}
   });


   var data = new Array();
   data[0] = {element:"e1",value:"v1"};
   data[1] = {element:"e2",value:"v2"};

   gridSource =
   {
       localData: data,
       dataType: "array",
       dataFields: [
        {name: 'element', type: 'string'},
        {name: 'value', type: 'string'},
        {name: 'start', type: 'int'},
        {name: 'end', type: 'int'},
        {name: 'section', type: 'string'},
        {name: 'elementID', type: 'string'},
        {name: 'elementType', type: 'string'},
        {name: 'htmlID', type: 'string'},
        {name: 'elementHTMLID', type: 'string'},
        {name: 'elementValue', type: 'string'},
        {name: 'vScrollPos', type: 'int'},
        {name: 'scrollHeight', type: 'int'},
        {name: 'scrollWidth', type: 'int'},
        {name: 'valueHTMLID', type: 'string'},
        {name: 'annotFeatures', type: 'string'}
       ]
   };

   dataAdapter = new $.jqx.dataAdapter(gridSource, {
       loadComplete: function (data) { },
       loadError: function (xhr, status, error) { }
   });

   $('#dataElementTable').jqxDataTable(
   {
	   height: '95%',
	   width: '95%',
	   theme: 'energyblue',
       source: dataAdapter,
       selectionmode: 'singlerow',
       enablehover: true,
       columnsResize: true,
       columns: [
         { text: 'Section', dataField: 'section', hidden: true },
         { text: 'Element', dataField: 'element', width: 400 },
         { text: 'Value', dataField: 'value', width: 400 }
       ],
       groups: ['section'],
       groupsRenderer: function(value, rowData, level)
       {
    	   //clog(value);
    	   var sectionData = JSON.parse(value);
    	   //var sectionData = JSON.parse("{\"sectionID\":1}");
    	   //clog(sectionData);
    	   var sectionName = sectionData["sectionName"];
    	   var sectionDisplayName = sectionData["sectionDisplayName"];
    	   //clog(sectionName);

    	   var index = sectionName.indexOf("|");
    	   var sectionNameShort = sectionName.substring(index+1);

    	   //var sectionHTML = sectionName;
    	   var repeat = sectionData["repeat"];
    	   var repeatIndex = sectionData["repeatIndex"];
    	   
    	   clog("sectionName: " + sectionName + " sectionDisplayName: " + sectionDisplayName + " repeat: " + repeat);
    	   
    	   if (repeat > 0 || repeat == -1) {
    	       repeatIndex++;
	    	   if (repeatIndex == 1) {
	    		   //sectionName = sectionNameShort +  "<br><input type='button' id='" + sectionNameShort + "' value='+' onclick='addSection(this.id)'/>";

	    		   // modify by wyu for repeat number
	    		   sectionName = sectionDisplayName + "_" + repeatIndex
	    		        + "<br><input type='button' id='" + sectionNameShort + "' value='+' onclick='addSection(this.id)'/>";
	    	   }
	    	   else{
	    		   //sectionName = sectionNameShort + "<br><input type='button' id='" + sectionNameShort + "_" + repeatIndex + "' value='-' onclick='removeSection(this.id)'/>";

	    		   // modify by wyu for repeat number
	    		   sectionName = sectionDisplayName  + "_" + repeatIndex
	    		        + "<br><input type='button' id='" + sectionNameShort + "_" + repeatIndex + "' value='-' onclick='removeSection(this.id)'/>";
	    	   }
    	   } else{
    		     //sectionName = sectionNameShort;
    		   sectionName = sectionDisplayName;
           }
           return sectionName;
       },
       rendered: function() {
    	   if (currTableOffset != undefined) {
    		   //clog("table rendered scroll offset: " + currTableOffset.top + ", " + currTableOffset.left);
    		   $("#dataElementTable").jqxDataTable('scrollOffset', currTableOffset.top, currTableOffset.left);
    	   }
    	   else
    		   currTableOffset = $("#dataElementTable").jqxDataTable('scrollOffset');
       }
   });

   $('#dataElementTable').on('rowSelect', function (event) {
	   clog("rowselect event: " + docNamespace + "," + docTable + "," + docID);
	   //clog(event.args.index + " meta: " + event.args.originalEvent + " ctrl: " + event.args.ctrlKey);
	   //rowSelect(event.args.row);
	   //rowSelect(event.args);
   });

   $('#dataElementTable').on('rowClick', function (event) {
	   clog("rowClick: " + event.args.index + " meta: " + event.args.originalEvent.metaKey + " ctrl: " + event.ctrlKey);
	   rowSelect(event.args);
   });

   $('#dataElementTable').on('columnResized', function (event) {
	   loadFrameInstanceNoRT();
   });

   $('#highlightMenu').jqxMenu({ width: '300px', height: '140px', autoOpenPopup: false, mode: 'popup'});

   $('#highlightMenu').on('itemclick', function (event) {
	   // get the clicked LI element.
	   var element = event.args;
	   clog("clicked: " + element.id);
	   if (element.id == 'clear')
		   clearFromMenu();
	   else
		   fillSlot(element.id);
	});


   document.getElementById('projSelect').selectedIndex = 0;

   //jquery-fieldselection
   //$('textarea').keyup(update).mousedown(update).mousemove(update).mouseup(update);
   $('textarea').keyup(update).mousedown(update).mouseup(update);
   
   $('crfPanel').mousedown(function(e) {clog("mousedown: " + e.target.id); selectFlag = false;});
   
   
   $('#docPanel').highlightWithinTextarea(onInput);
   $('#docPanel').click(function(event) {
	   var cursorPosition = $('#docPanel').prop("selectionStart");
	   clog("click: " + cursorPosition);
	   
	   docPanelClick(cursorPosition, false);
	   
	   $("#docFeatures input:radio").attr("checked", false);
		$('[name=docfeature]').each(function() {
			var keyValue = JSON.parse($(this).val());
			$(this).next().html(keyValue["key"] + ": " + keyValue["value"]);
		});
		
		docFeatureValue = null;

   });
   
   $('#docPanel').dblclick(function(event) {
	   var cursorPosition = $('#docPanel').prop("selectionStart");
	   clog("double click: " + cursorPosition);
	   
	   docPanelClick(cursorPosition, true);
	   
	   $("#docFeatures input:radio").attr("checked", false);
		$('[name=docfeature]').each(function() {
			var keyValue = JSON.parse($(this).val());
			$(this).next().html(keyValue["key"] + ": " + keyValue["value"]);
		});
		
		docFeatureValue = null;

   });
   
   $(document).click(function(event) {
	   if (event.target.tagName == "TEXTAREA")
		   return;
	   
	   if (event.target.tagName != "TD" && selectFlag) {
		   selectFlag = false;
	   }
	 });
   

   	//load the entity if there's an entity ID

	colNames = $('#colNames').val();
	colValues = $('#colValues').val();

	if (colNames.length > 0) {

		$('#projSelect option:nth-child(2)').prop('selected', true);

		var projName = $('#projSelect option:nth-child(2)').val();
		loadProject(projName);
	}

    var notificationWidth = 720;
    var notificationHeight = 50;
    //var notificationX = $("#buttonDiv").position().left;
    //var notificationY = $("#buttonDiv").position();
    var notificationX = $("body").width() / 2 - notificationWidth / 2;
    var notificationY = 26;

    $('#successWindow').jqxWindow({
            width: notificationWidth,
            height: notificationHeight,
            resizable: true,
            position: {x:notificationX, y:notificationY},
            //okButton: $('#doneButton'),
            autoOpen: false
    });

    $('#errorWindow').jqxWindow({
            width: notificationWidth,
            height: notificationHeight,
            resizable: true,
            position: {x:notificationX, y:notificationY},
            //okButton: $('#doneButton'),
            autoOpen: false
    });
    $('.alert').css("height", notificationHeight);
    $('.alert').css("width", notificationWidth);
    $('.jqx-window-header').hide();

});

//$( window ).resize(function() {
$(window).on('resize', function(e) {

	  window.resizeEvt;
	  $(window).resize(function() {
		  clearTimeout(window.resizeEvt);
		  window.resizeEvt = setTimeout(function() {
			  if (loadDialogCount == 0) {
				  //code to do after window is resized when the dialog window is not open (the app is not refreshing itself)
				  clog("window resized!");
				  //loadFrameInstance(currFrameInstanceID, false);
				  loadFrameInstanceNoRT();
				  $('#docPanel').width($('#docDiv').width() * .95);
				  $('#docPanel').height($('#docListBox').height() - $('#docFeatures').height() - 20);
			  }
		  }, 250);
	  });
});



function loadDocument(docInfoStr)
{
}

function getDocument(docInfoStr, index, clear, options, callback)
{
    // new code
    clearHistory();

	//clog("getDocument: " + docInfoStr);
	docHistory[docInfoStr] = true;
	//var docInfo = JSON.parse(docInfoStr);
	addDocumentHistory(docInfoStr);

	docSelectIndex = index;

	$('#docListBox').jqxListBox('refresh');
	$('#docListBox').jqxListBox('ensureVisible', index);
	$('#docListBox').jqxListBox('selectedIndex', index);

	//clear highlight
	//highlightRange.start = 0;
	//highlightRange.end = -1;
	//highlightRanges = [];

	if (clear) {
		highlightRangeList = undefined;
		//getHighlightRanges();
		//highlightText();
	}

	var docInfo = JSON.parse(docInfoStr);

	docNamespace = docInfo["docNamespace"];
	docTable = docInfo["docTable"];
	//docKey = docInfo["docKey"];
	//docTextColumn = docInfo["docTextColumn"];
	docID = docInfo["docID"];

	clog("get doc: " + docNamespace + ", " + docTable + ", " + docID);

	//text = "";
	openDialogLoad();
	var getDocumentAjax = jsRoutes.controllers.Application.getDocument();
	$.ajax({
		type: "POST",
		url: getDocumentAjax.url,
		data:{docNamespace:docNamespace, docTable:docTable, docID:docID},
		cache: false
	}).done(function(data) {
		//clog(data);
		var docData = JSON.parse(data);
		docName = docData["docName"];
		origText = docData["docText"];
		//clog("text len: " + origText.length);

		origText = origText.replace(/[\r]/g, '');
		//clog("text len: " + origText.length);

		//var text = origText.split("\n").join("&nbsp;<br />");
		var text = origText;

		//$('#docPanel').jqxPanel('clearcontent');
		//$('#docPanel').jqxPanel('append', "<div style='margin:10px;'>" + text + "</div>");
		//$('#docPanel').val("<div style='margin:10px;'>" + text + "</div>");
		$('#docPanel').val(text);

		annotList = docData["annotList"];
		//clog("annotList: " + JSON.stringify(annotList));
		//getHighlightRanges();
		//highlightText();

		$('#docTitleDiv').text(docName);
		$('#docFeatures').html('');

		//set the document features
		var featuresHTML = "";
		docFeatures = JSON.parse(docData["docFeatures"]);
		for (var key in docFeatures) {
			var value = docFeatures[key];
			featuresHTML = featuresHTML.concat("<input id='" + key + "_docfeature' name='docfeature' type='radio' value='{\"key\":\"" + key + "\",\"value\":\"" + value + "\"}' onclick='docFeatureClicked(this.value, this.id)'><label class='unselectable'>" + key + ": " + value + "</label><br>");
		}

		$('#docFeatures').html(featuresHTML);
		$('#docPanel').height($('#docListBox').height() - $('#docFeatures').height());

		//highlightText(rowStart, rowEnd, vScrollPos);

		closeDialogLoad();

		if (callback != null)
			callback(options);

        //var frameInstanceStatus = docData["frameInstanceStatus"];
        //if( frameInstanceStatus == 1 ) {
            //docListBox item background color from powderblue to green  #228B22; or #4CAF50;
            //$('#docListBox font').css("background-color", "#32CD32");
        //}
		//$("#validatedButtonDiv").show();

	}).fail(function() {
	});

}


// new code

function highlightByWord() {
  var buttonText = document.getElementById("highlightButton");
  if(buttonText.textContent == "Word Highlighting: Off") {
    buttonText.textContent = "Word Highlighting: On";
    wordBasedHighlighting = true;
  } else {
    buttonText.textContent = "Word Highlighting: Off";
    wordBasedHighlighting = false;
  }
}

// clears whatever content the user double right-clicked on
function onRightClick(event) {
  // the following three lines will hide the right-click menu
  document.getElementById("docPanel").oncontextmenu = function() {
    return false;
  }
  clog(history);
  if(event.button == 2) {
    // clog("right click worked");
    // text = $("#dataElementTable").jqxDataTable('getSelection');
    // text = window.getSelection().toString();
    var text = "";

    // clog("selectRange: " + JSON.stringify(selectRange));
    if (window.getSelection) {
        text = window.getSelection().toString();
    } else if (document.selection && document.selection.type != "Control") {
        text = document.selection.createRange().text;
    }

    // clog(annotList);
    // var temp = document.getElementsByTagName("html");
    // clog(temp);

    // clog(elementIDMap);
    // clog(elementHTMLIDMap);
    // clog(gridData);
    // clog(gridData2);
    // clog(frameInstanceData);

    // clog(highlightRangeMap);

    if(text) {
      // clog(highlightRanges);
      if(highlightedPartIsAnnot(selectRange.start, selectRange.end)) {
        var htmlID = getAnnotFromHighlight(selectRange.start, selectRange.end);
        // clog("htmlID: " + htmlID);
        htmlID = findAnnotID(htmlID);
        var elementID = findElementID(htmlID);
        var elementType = findElementType(htmlID);
        clog("htmlID: " + htmlID);
        clog("elementID: " + elementID);
        clog("elementType: " + elementType);

        var elementIndex = elementIDMap[elementID];
        gridData2[elementIndex]["start"] = 0;
    		gridData2[elementIndex]["end"] = -1;
    		gridData2[elementIndex]["elementValue"] = "";

        clearElementOnDoubleRightClick(elementID, htmlID, elementType);
        // clog(gridData2);


      }
    }
  }
}

function findAnnotID(str) {
  // the following commented code will not work
  // it turns out that you can't return something from a jquery thingy

  for(var key in highlightRangeMap) {
    clog(key);
    var temp = highlightRangeMap[key][0]["annotID"];
    if(str.localeCompare(temp) == 0) {
      return key;
    }
  }
  return "failed";
}

function findElementID(htmlID) {
  for (var i=0; i<frameInstanceData.length; i++) {
    if(frameInstanceData[i]["elementHTMLID"].localeCompare(htmlID) == 0)
      return frameInstanceData[i]["elementID"];
  }
  return "failed";
}

function findElementType(htmlID) {
  for (var i=0; i<frameInstanceData.length; i++) {
    if(frameInstanceData[i]["elementHTMLID"].localeCompare(htmlID) == 0)
      return frameInstanceData[i]["elementType"];
  }
  return "failed";
}

function clearHistory() {
  history = new Object();
  processID = 1;
}

function undoAction() {
  var mostRecentAction = history[processID - 1];
  if(mostRecentAction == null || mostRecentAction == undefined) {
    return;
  }
  clog(mostRecentAction);
  var tempPID = mostRecentAction["id"];
  var tempAction = mostRecentAction["act"];
  var tempHTMLID = mostRecentAction["htmlID"];
  var tempExtraInfo = mostRecentAction["extraInfo"];

  clog(tempHTMLID);
  var annotID = findAnnotID(tempHTMLID);
  var elementHTMLID = findElementID(annotID);
  var elementType = findElementType(annotID);

  clog(annotID);
  clog(elementType);
  clog(elementHTMLID);


  clog('button works');

  clog(frameInstanceData);
  clog(annotList);
  clog(highlightRangeMap);

  // openDialogLoad();

  var undoAjax = jsRoutes.controllers.Application.getHistory();
  clog(undoAjax);
  $.ajax({
	cache: false,
    type: 'GET',
    url: undoAjax.url,
  }).done(function(data) {




  })


}

function clearElementOnDoubleRightClick(elementID, elementHTMLID, elementType) {
  openDialogLoad();
  // history.push({"id": processID, "act": "clear", "htmlID": elementHTMLID, "extraInfo": "temp"});
  history[processID] = [];
  history[processID]["id"] = processID;
  history[processID]["act"] = "clear";
  history[processID]["htmlID"] = elementHTMLID;
  history[processID]["extraInfo"] = "temp";
  processID++;
  var clearElementAjax = jsRoutes.controllers.Application.clearElement(elementID, elementHTMLID);
  $.ajax({
    type: 'GET',
    url: clearElementAjax.url,
    cache:false
  }).done(function(data) {
    var dataObj = JSON.parse(data);
    frameInstanceData = dataObj[0];
    highlightRangeMap = dataObj[1];

    //reload document annotations
    var getDocumentAnnotationsAjax = jsRoutes.controllers.Application.getDocumentAnnotations();
    $.ajax({
      type: 'POST',
      url: getDocumentAnnotationsAjax.url,
      data: {docNamespace:docNamespace,docTable:docTable,docID:docID},
      cache: false
    }).done(function(data) {
      clog("clear element ranges: " + data);
      annotList = JSON.parse(data);
      getHighlightRanges();

      //clear user-highlighted element
      //highlightRange.start = 0;
      //highlightRange.end = -1;
      //highlightRanges = [];
      highlightRangeList = undefined;
      highlightText();
      selectFlag = false;

      var element = $(jq(elementHTMLID));
      if (elementType == "text" || elementType == 'textarea')
        element.val('');
      else {
        $('input[name="' + elementHTMLID + '"]').prop('checked',false);
      }

      $("#docFeatures input:radio").attr("checked", false);
      $('[name=docfeature]').each(function() {
        var keyValue = JSON.parse($(this).val());
        $(this).next().html(keyValue["key"] + ": " + keyValue["value"]);
      });

      closeDialogLoad();
    })
  })
}


function highlightedPartIsAnnot(start, end) {
  for(var i = 0; i < highlightRanges.length; i++) {
    if(highlightRanges[i][0] <= start && highlightRanges[i][1] >= end) {
      return true;
    }
  }
  return false;
}

function getAnnotFromHighlight(start, end) {
  for(var i = 0; i < annotList.length; i++) {
    if(annotList[i]["start"] <= start && annotList[i]["end"] >= end) {
      return annotList[i]["annotType"];
    }
  }
  return "failed";
}


function logout() {
  //clog("logout");
	prevFrameInstanceIndex = -1;
	
  var log = jsRoutes.controllers.Application.logout();
	$.ajax({
			type: 'GET',
			url: log.url,
			cache: false
		}).done(function(data) {
			docHistory = {};
		//	closeDialogLoad();
		}).fail(function () {
	});
}


/*
function toLogIn() {

    // openDialogLoad();
    clog("Hello");
    var auth = jsRoutes.controllers.Application.authenticate();
    $.ajax({

        type: 'POST',
        url: auth.url,
        data:{username:username, password:password}
    }).done(function(data) {



        // closeDialogLoad();
    })



}*/
// end of new code

function rowSelect(row)
{
	openDialogLoad();
	
	
	var index = row.index;
	currRowIndex = row.index;
	var rowData = gridData2[index];
	var rowValue = rowData["elementValue"];
	var elementType = rowData['elementType'];
	var elementHTMLID = rowData["elementHTMLID"];
	var dataField = row.dataField;
	
	/*
	if (dataField == 'value') {
		selectFlag = false;
		
		$("#docFeatures input:radio").attr("checked", false);
		$('[name=docfeature]').each(function() {
			var keyValue = JSON.parse($(this).val());
			$(this).next().html(keyValue["key"] + ": " + keyValue["value"]);
		});

		return;
	}
	*/
	
	currTableOffset = $("#dataElementTable").jqxDataTable('scrollOffset');
	

	var add = row.originalEvent.metaKey;
	if (!add)
		add = row.originalEvent.ctrlKey;

	//check if the user highlighted anything
	var start = 0;
	var end = 0;
	var value;
	var range = null;

	if (selectRange != null && selectFlag) {
		start = selectRange.start;
		end = selectRange.end;
		
		if (start > end) {
			var start2 = start;
			start = end;
			end = start2;
		}

		if (tokenSelectFlag) {
			//use token-based highlighting
			var start2 = origText.substring(0, start).lastIndexOf(" ");
			var end2 = origText.indexOf(" ", end);

			start = start2+1;
			end = end2;
			clog("token-based: " + start + ", " + end);
		}
        while (origText.charAt(start) == ' ')
            start++;
        while (origText.charAt(end-1) == ' ')
            end--;
		value = origText.substring(start, end);
        clog("row select highlighted: " + start + "," + end + ", value: " + value);
	}
	else if (selectFlag == undefined || !selectFlag) {
		clog("selectFlag: " + selectFlag);
		start = 0;
		end = 0;
	}


	//var rowStart = rowData["start"];
	//var rowEnd = rowData["end"];


	//if (start == end && docFeatureValue == null && rowValue != null && rowValue.length > 0) {
	if (start == end && docFeatureValue == null) {

		var rowStart = undefined;
		var rowEnd = undefined;

		highlightRangeList = highlightRangeMap[elementHTMLID];
		var highlightMap;
		if (highlightRangeList != null) {
			rowStart = highlightRangeList[0]["start"];
			rowEnd = highlightRangeList[0]["end"];
			highlightMap = highlightRangeList[0];
		}


		clog("rowStart: " + rowStart);
		clog("rowEnd: " + rowEnd);
		clog("element: " + rowData['element']);
		

		//clear any existing selections highlights for doc metadata
		$('[name=docfeature]').each(function() {
			var keyValue = JSON.parse($(this).val());
			$(this).prop('checked', false);
			$(this).next().html(keyValue["key"] + ": " + keyValue["value"]);
		});

		if (highlightRangeList != undefined) {
		    clog("rowselect: " + docNamespace + "," + docTable + "," + docID);
		    clog("elementhtmlid: " + elementHTMLID + " highlightrangemap: " + JSON.stringify(highlightRangeMap));
		    clog("highlightmap: " + JSON.stringify(highlightMap));
		    
		    userActions++;
			clog("userActions: " + userActions);
		    
		    var docIndex = docIndexMap["{\"docNamespace\":\"" + highlightMap["docNamespace"] + "\",\"docTable\":\"" + highlightMap["docTable"] + "\",\"docID\":" + highlightMap["docID"] + "}"];
		    if (highlightMap["docNamespace"] != docNamespace || highlightMap["docTable"] != docTable || highlightMap["docID"] != docID) {
			    getDocument("{\"docNamespace\":\"" + highlightMap["docNamespace"] + "\",\"docTable\":\"" + highlightMap["docTable"] + "\",\"docID\":" + highlightMap["docID"] + "}",
			    	docIndex, false, [rowStart], function (options) {
			    	clog("highlightRangeList: " + JSON.stringify(highlightRangeList));
			    	var rowStart = options[0];
			    	if (rowStart >= 0) {
				    	//highlightRange.start = rowStart;
				    	//highlightRange.end = rowEnd;
				    	//highlightRanges[0] = [rowStart,rowEnd];
			    		getHighlightRanges();
				    	//highlightText();
				    }
				    else if (rowData["annotFeatures"] != undefined && rowData["annotFeatures"].length > 0) {
				    	//this is document metadata
				    	var annotFeatures = JSON.parse(rowData["annotFeatures"]);
				    	clog(annotFeatures);
				    	var id = annotFeatures["key"] + "_docfeature";
				    	$('#' + id).prop('checked', true);
				    	$('#' + id).next().html("<span class='highlight' style='background-color:yellow'>" + annotFeatures["key"] + ": " + annotFeatures["value"] + "</span>");
						//highlightRange.start = 0;
						//highlightRange.end = -1;
						//highlightRanges = [];
				    	highlightRangeList = undefined;
				    	getHighlightRanges();
				    	//highlightText();
				    }
				    else
				    	highlightRangeList = undefined;

			    	highlightText();
				    $('#docPanel').scrollTop(0);
				    //var lastEnd = highlightRangeList[0]["end"];
					//scrollTextareaToPosition($('#docPanel'), lastEnd);
					//var scrollTop = $('#docPanel').scrollTop();
					//$('#docPanel').scrollTop(scrollTop + ($('#docPanel').innerHeight() / 2));
					
					if (highlightRangeList != undefined) {
					    var lastEnd = highlightRangeList[0]["end"];
						scrollTextareaToPosition($('#docPanel'), lastEnd);
				    }
			    	
			    });
		    }
		    else {
				//$('#docListBox').jqxListBox('refresh');
				//$('#docListBox').jqxListBox('ensureVisible', docIndex);
				//$('#docListBox').jqxListBox('selectedIndex', docIndex);


			    if (rowStart >= 0) {
			    	//highlightRange.start = rowStart;
			    	//highlightRange.end = rowEnd;
			    	//highlightRanges[0] = [rowStart,rowEnd];
			    	getHighlightRanges();
			    	//highlightText();
			    }
			    else if (rowData["annotFeatures"] != undefined && rowData["annotFeatures"].length > 0) {
			    	clog("annotFeatures: " + rowData["annotFeatures"]);
			    	//this is document metadata
			    	var annotFeatures = JSON.parse(rowData["annotFeatures"]);
			    	clog(annotFeatures);
			    	var id = annotFeatures["key"] + "_docfeature";
			    	$('#' + id).prop('checked', true);
			    	$('#' + id).next().html("<span class='highlight' style='background-color:yellow'>" + annotFeatures["key"] + ": " + annotFeatures["value"] + "</span>");
					//highlightRange.start = 0;
					//highlightRange.end = -1;
					//highlightRanges = [];
			    	highlightRangeList = undefined;
			    	//highlightText();
			    }
			    else
			    	highlightRangeList = undefined;

			    highlightText();
			    var height = $('#docPanel').innerHeight();
			    $('#docPanel').scrollTop(0);
			    
			    if (highlightRangeList != undefined) {
				    var lastEnd = highlightRangeList[0]["end"];
					scrollTextareaToPosition($('#docPanel'), lastEnd);
			    }
				
				//var scrollTop = $('#docPanel').scrollTop();
				//clog("scrolltop: " + scrollTop + ", height: " + height);
				//if (scrollTop > (height/2))
				//	$('#docPanel').scrollTop(scrollTop + (height / 2));
		    	
		    }
		    
		    

		    clog("index: " + docIndex);
		}

		else {
			//highlightRange.start = 0;
			//highlightRange.end = -1;
			//highlightRanges = [];
		    highlightText();
		}

	}


	if (elementType != 'text' || elementType != 'textarea') {

		//callback for individual value element (e.g., radio button, checkbox, etc)
		clickStart = start;
		clickEnd = end;
		clickValue = value;
		valueClickCount1++;

		if (valueClickCount1 > 1) {
			valueClickCount1 = 1;
		}

		valueClickCallback(false);
	}

	var annotFeatures = null;
	
	clog("row select 4");
	
	
	
	//something was highlighted
	if (selectFlag && (start != end || docFeatureValue != null)) {
		
		clog("highlight add element: " + elementHTMLID);
		

		if (docFeatureValue != null) {
			start = -1;
			end = -1;
			vScrollPos = 0;
			value = docFeatureValue;
			//$('#docfeature:checked').prop('checked', false);
			annotFeatures = '{"key":"' + docFeatureKey + '","value":"' + value + '"}';
			rowData["annotFeatures"] = annotFeatures;
			//highlightRange.start = 0;
			//highlightRange.end = -1;
			//highlightRanges = [];
			highlightText();
		}
		else {
			$('[name=docfeature]').each(function() {
				var keyValue = JSON.parse($(this).val());
				$(this).prop('checked', false);
				$(this).next().html(keyValue["key"] + ": " + keyValue["value"]);
			});
		}
		
		
		
       if (wordBasedHighlighting) { // new code
           var regex = /^[a-z0-9]+$/i;
           for(var i = start; i >= 0; i--) {
             var temp = origText.substring(i, i+1);
             //the following if statement allows for things like "12:45", "check-in", and "5.21" to be counted as words
             if((temp == "." || temp == ":" || temp == "-") && origText.substring(i-1, i).match(regex)) {
               i--;
               continue;
             }
             if(i == 0) {
               start = i;
               break;
             }
             if(!temp.match(regex)) {
               i++;
               start = i;
               break;
             }
           }
           for(var i = end; i < origText.length; i++) {
             var temp = origText.substring(i, i+1);
             //the following if statement allows for things like "12:45", "check-in", and "5.21" to be counted as words
             if((temp == "." || temp == ":" || temp == "-") && origText.substring(i+1, i+2).match(regex)) {
               i++;
               continue;
             }
             if(!temp.match(regex)) {
               end = i;
               break;
             }
           }

           value = origText.substring(start, end);
         }

		
		
		
		

		if (value != rowData['elementValue']) {
			//var elementType = rowData['elementType'];
			clog("elementType: " + elementType);

			var htmlID = rowData['elementHTMLID'];
			var element = $(jq(htmlID))
			//clog(element.prop('id'));

			if (elementType != undefined && (elementType == 'text') || elementType == 'textarea') {
				element.val(value);

				clog("Add: " + docNamespace + "," + docTable + "," + docID);

				/*
				var newRow = {};
				newRow["htmlID"] = htmlID;
				newRow["value"] = value;
				frameInstanceData.push(newRow);
				*/
				


				//openDialogLoad();
		
		        var addAnnotationAjax = jsRoutes.controllers.Application.addAnnotation();
		
						$.ajax({
								type: 'POST',
								url: addAnnotationAjax.url,
							    data:{htmlID:htmlID, value:value,start:start,end:end,docNamespace:docNamespace,docTable:docTable,docID:docID,features:annotFeatures,add:add},
							    cache: false
							}).done(function(data) {
								clog("highlight: start=" + start + " end=" + end + " docFeatureValue=" + docFeatureValue);
								clog("add annot data: " + data);
		
								var dataObj = JSON.parse(data);
								frameInstanceData = dataObj[0];
								highlightRangeMap = dataObj[1];
								
								//add row if repeatable
								clog("highlight add element: " + elementHTMLID);
								if (document.getElementById(elementHTMLID + '_add') != null || document.getElementById(elementHTMLID + '_remove') != null) {
									addElement(elementHTMLID + '_add');
								}
		
								//add to annotList
		
								var getDocumentAnnotationsAjax = jsRoutes.controllers.Application.getDocumentAnnotations();
								$.ajax({
									type: 'POST',
									url: getDocumentAnnotationsAjax.url,
									data: {docNamespace:docNamespace,docTable:docTable,docID:docID},
									cache: false
								}).done(function(data) {
									clog("clear element ranges: " + data);
									annotList = JSON.parse(data);
		
									//annotList.push({start:start,end:end,annotType:""});
									//getHighlightRanges();
									if (start >= 0) {
										//highlightRange.start = start;
										//highlightRange.end = end;
								    	//highlightRanges = [[start,end]];
										highlightRangeList = highlightRangeMap[elementHTMLID];
										getHighlightRanges();
										highlightText();
									}
		
		
									else if (docFeatureValue != null) {
										$('#docfeature:checked').each(function() {
											$(this).next().html("<span class='highlight' style='background-color:lightgray'>" + annotFeatures["key"] + ": " + annotFeatures["value"] + "</span>");
										});
		
										var id = annotFeatures["key"] + "_docfeature";
								    	$('#' + id).prop('checked', true);
								    	$('#' + id).next().html("<span class='highlight' style='background-color:yellow'>" + annotFeatures["key"] + ": " + annotFeatures["value"] + "</span>");
		
										docFeatureValue = null;
									}
									
									selectFlag = false;
									clog("row select 2");
									closeDialogLoad();
								});
		
		
							}).fail(function () {
						});

						if (docFeatureValue == null) {
							//highlightRange.start = start;
							//highlightRange.end = end;
					    	//highlightRanges = [[start,end]];
							highlightText();
							clog("row select 3");
						}

				//selectFlag = false;
					}
			else
				closeDialogLoad();
			}
			else
				closeDialogLoad();

		}
		else {
			clog("row select 1");
			closeDialogLoad();
		}

	//closeDialogLoad();
}

function highlightText()
{
	if (origText == null || origText.length == 0)
		return;
	
	var scrollTop = $('#docPanel').scrollTop();


	if (highlightRangeList == undefined) {
		//$('#docPanel').data('hwt').destroy();
		$('#docPanel').highlightWithinTextarea(onInput);
		
		$('.hwt-content mark').each(function (index) {
			//clog("setting " + index + " to " + highlightRanges[index][2]);
			//$(this).css("background-color","lightgray");
			$(this).css("background-color", highlightRanges[index][2]);
		});
		//$('.hwt-content mark').css("background-color","lightgray");
	}

	else {
		var highlightIndex = 0;
		var lastIndex = -1;
		var i=0;
		var lastEnd = highlightRangeList[0]["end"];

		clog("highlightrangelist: " + JSON.stringify(highlightRangeList));
		clog("highlightRanges: " + JSON.stringify(highlightRanges));
		clog("highlightIndexes: " + JSON.stringify(highlightIndexes));
		clog("hwt contents: " + $('#docPanel .hwt-content').length);
		

		//$('#docPanel').data('hwt').destroy();
		$('#docPanel').highlightWithinTextarea(onInput);
		//$('.hwt-content mark').css("background-color","yellow");
		
		if (highlightIndexes.length == 0) {
			$('.hwt-content mark').css("background-color","lightgray");	
		}
		
		$('.hwt-content mark').each(function (index) {
			if (index != highlightIndexes[highlightIndex]) {
				//clog("setting " + index + " to " + highlightRanges[index][2]);
				//$(this).css("background-color","lightgray");
				$(this).css("background-color", highlightRanges[index][2]);
			}
		});
		

		


		//$(window).resize();
		//$('#docPanel').resize();
		$('#docPanel').width($('#docDiv').width() * .9);
		$('#docPanel').width($('#docDiv').width() * .95);
		//scrollTextareaToPosition($('#docPanel'), lastEnd+1000);
		//scrollTextareaToPosition($('#docPanel'), lastEnd + $('#docPanel').height() / 2);
		

	}
	
	$('#docPanel').scrollTop(scrollTop);

}

function highlightAnnotations()
{

}

function countLines(text, endIndex)
{
	var count = 0;
	var index = text.indexOf("&nbsp;");
	while (index >= 0 && index < endIndex) {
		count++;
		index = text.indexOf("&nbsp;", index+6);
	}

	return count;
}

function setCaretPosition(elemId, caretPos) {
    var el = document.getElementById(elemId);

    el.value = el.value;
    // ^ this is used to not only get "focus", but
    // to make sure we don't have it everything -selected-
    // (it causes an issue in chrome, and having it doesn't hurt any other browser)


    if (el !== null) {

        if (el.createTextRange) {
            var range = el.createTextRange();
            range.move('character', caretPos);
            range.select();
            return true;
        }

        else {
            // (el.selectionStart === 0 added for Firefox bug)
            if (el.selectionStart || el.selectionStart === 0) {
                el.focus();
                el.setSelectionRange(caretPos, caretPos);
                el.blur();

                return true;
            }

            else  { // fail city, fortunately this never happens (as far as I've tested) :)
                el.focus();
                return false;
            }
        }
    }
}

function loadCRF(crfName)
{
	openDialogLoad();
	var getCRFAjax = jsRoutes.controllers.Application.getCRF(crfName);
	$.ajax({
		type: 'GET',
		url: getCRFAjax.url,
		cache: false
	}).done(function(data) {
		//clog(data);

		loadCRFData(data);
		closeDialogLoad();
	})
}

function loadProject(projName)
{
	$("#validatedButtonDiv").hide();
	openDialogLoad();
	var loadProjectAjax = jsRoutes.controllers.Application.loadProject(projName);
	$.ajax({
		type: 'GET',
		url: loadProjectAjax.url,
		cache: false
	}).done(function(data) {
		//clog(data);

		var result = JSON.parse(data);
		frameArray = result[0];
		var lastFrameAccessed = result[1];
		var crfData = result[2];
		
		updateCRFSelect();

		$('#crfSelect').select2();	



		loadCRFData(crfData);


		if (colNames.length > 0) {
			loadEntity(colNames, colValues);
		}
		else {
			currFrameInstanceIndex = lastFrameAccessed["lastFrameInstanceIndex"];
			var frameInstanceID = lastFrameAccessed["lastFrameInstanceID"];
			
			if (frameInstanceID == -1)
				frameInstanceID = frameArray[0]["frameInstanceID"];

			clog("frameInstanceID: " + frameInstanceID);

			//loadFrameInstance(frameInstanceID, true);
			//$('#crfSelect').prop('selectedIndex', currFrameInstanceIndex);
			//$('#crfSelect').prop('selectedIndex', currFrameInstanceIndex);
			
			if (frameInstanceID != -1)
				//frameInstanceValidated(frameInstanceID);
				loadFrameInstance(frameInstanceID, true);
			//$('#crfSelect').val(frameInstanceID).trigger("change");
		}


		$('#instanceButton').prop('disabled', false);

		closeDialogLoad();
	})
}

function frameInstanceSelected(frameInstanceID, clearDoc, frameInstanceIndex)
{
	clog("frameInstanceSelected: " + frameInstanceID + ", " + clearDoc + ", " + frameInstanceIndex + ", " + crfSelectDisabled);
	if (crfSelectDisabled) {
		crfSelectDisabled = false;
		return;
	}
	
	$('#instanceText').val('');
	currFrameInstanceIndex = frameInstanceIndex;
    
	loadFrameInstance(frameInstanceID, clearDoc);

	
	//frameInstanceValidated(frameInstanceID);
}

function frameInstanceSelectedText(frameInstanceName)
{
	clog("frameInstanceSelected: " + frameInstanceName);

	for (var i=0; i<frameArray.length; i++) {
		//clog(frameArray[i]["name"]);
		if (frameArray[i]["name"] == frameInstanceName) {
			$('#crfSelect option').eq(i+1).prop('selected', true);
			loadFrameInstance(frameArray[i]["frameInstanceID"], true);
			break;
		}
	}
}

function loadFrameInstance(frameInstanceID, clearDoc)
{
	clog(frameInstanceID);
	
	openDialogLoad();
	
	//offset = $("#dataElementTable").jqxDataTable('scrollOffset');
	clog("loadFrameInstance, frameInstanceID: " + frameInstanceID + " clearDoc: " + clearDoc);
	
    $("#validatedButtonDiv").hide();
    
    //validate curr frame instance
    //clog("currFrameInstanceID: " + currFrameInstanceID);
    if (currFrameInstanceID != undefined && currFrameInstanceID != null)
    	frameInstanceValidated(currFrameInstanceID);
    
    //check to see if frame instance is locked by another user
    $.ajax({
        type: "GET",
        url: "frameinstancelock/"+frameInstanceID,
        cache: false
    }).done(function(data) {
        data = JSON.parse(data);
        //clog(data);
        
        if (data.Success == 'true'){
        	clog("frame instance locked!");
            alert("Frame instance locked!");
            //openFrameInstanceLock();
            
            closeDialogLoad();
            
            crfSelectDisabled = true;
            
            //currFrameInstanceIndex = frameInstanceIndex;
            //frameInstanceID = currFrameInstanceID;
            
            currFrameInstanceIndex = prevFrameInstanceIndex;
            //loadFrameInstance(currFrameInstanceID);
            
            if (currFrameInstanceIndex == -1)	 {
            	$('#crfSelect').val('default').trigger("change");
            }
            else
            	$('#crfSelect').val(currFrameInstanceID).trigger("change");
            //return true;
        }
    
        else {
    
			
			currFrameInstanceID = frameInstanceID;
			docSelectIndex = -1;
		
		    var loadFrameInstanceAjax = jsRoutes.controllers.Application.loadFrameInstance(frameInstanceID, userActions);
		    $.ajax({
		        type: 'GET',
		        url: loadFrameInstanceAjax.url,
		        cache: false
		    }).done(function(data) {
		
		    	//clog(data);
		        var dataObj = JSON.parse(data);
		
		        //load highlight range map
		        highlightRangeMap = dataObj[4];
		
		
		        var crfData = dataObj[1];
		        loadCRFData(crfData);
		        
		        
		        //reload crf select elements
		        var userStatus = dataObj[5];
		        if (userStatus.length > 0) {
			        for (var i=0; i<userStatus.length; i++) {
			        	if (userStatus[i] != null)
			        		frameArray[i]["validatedByUserName"] = userStatus[i];
			        }
			        
			        updateCRFSelect();
			        
			        crfSelectDisabled = true;
					$('#crfSelect').val(frameInstanceID).trigger("change");
		        }
		
		
		        frameInstanceData = dataObj[3];
		        for (var i=0; i<frameInstanceData.length; i++) {
		            //var value = frameInstanceData[i]["value"];
		            var elementID = frameInstanceData[i]["elementID"];
		            //var htmlID = frameInstanceData[i]["htmlID"];
		            var start = frameInstanceData[i]["start"];
		            var end = frameInstanceData[i]["end"];
		            var docNamespaceLocal = frameInstanceData[i]["docNamespace"];
		            var docTableLocal = frameInstanceData[i]["docTable"];
		            var docIDLocal = frameInstanceData[i]["docID"];
		            //var vScrollPos = frameInstanceData[i]["vScrollPos"];
		            //var scrollHeight = frameInstanceData[i]["scrollHeight"];
		            //var scrollWidth = frameInstanceData[i]["scrollWidth"];
		            var annotFeatures = frameInstanceData[i]["features"];
		
		            var elementIndex = elementIDMap[elementID];
		            clog("elementID: " + elementID);
		            //gridData[elementIndex]["start"] = start;
		            //gridData[elementIndex]["end"] = end;
		            //gridData[elementIndex]["elementValue"] = value;
		            gridData[elementIndex]["docNamespace"] = docNamespaceLocal;
		            gridData[elementIndex]["docTable"] = docTableLocal;
		            gridData[elementIndex]["docID"] = docIDLocal;
		            //gridData[elementIndex]["vScrollPos"] = vScrollPos;
		            //gridData[elementIndex]["scrollHeight"] = scrollHeight;
		            //gridData[elementIndex]["scrollWidth"] = scrollWidth;
		            gridData[elementIndex]["annotFeatures"] = annotFeatures;
		
		
		            /*
		             var element = $("#" + htmlID);
		             clog(element.prop('tagName') + "," + element.attr('type'));
		             if (element.prop('tagName').toLowerCase() == "input") {
		             var elType = element.attr('type').toLowerCase();
		             if (elType == "text") {
		             element.val(value);
		             }
		             else if (elType == "checkbox" || elType == "radio") {
		             element.prop('selected', true);
		             }
		             }
		             */
		        }
		
		
		        //reload grid data into table
		        gridSource.localData = gridData;
		        dataAdapter = new $.jqx.dataAdapter(gridSource, {
		            loadComplete: function (data) {
		            	$("#dataElementTable").jqxDataTable('refresh');
		            },
		            loadError: function (xhr, status, error) { }
		        });
		        $("#dataElementTable").jqxDataTable({ source: dataAdapter });
		        
		        if (currRowIndex != undefined) {
		        	var rows = $("#dataElementTable").jqxDataTable('getRows');
		        	if (currRowIndex < rows.length) {
			        	$("#dataElementTable").jqxDataTable('selectRow', currRowIndex);
		        	}
		        }
		
		
		        setHTMLElements();
		
		
		
		        if (clearDoc) {
		            //set document panel title
		            $("#docTitleDiv").text("No Document Selected");
		
		            //clear document panel
		            $('#docPanel').val('');
		            //highlightRange.start = 0;
		            //highlightRange.end = -1;
		            //highlightRanges = [];
		            highlightRangeList = undefined;
		            highlightText();
		
		            //remove doc metadata
		            $('#docFeatures').html("");
		
		            docNamespace = "";
		            docTable = "";
		            docID = -1;
		
		            //load document list
		
		            var docList = dataObj[2];
		
		
		
		            //load document list into listbox
		            var docListBoxSource = [];
		            for (var i = 0; i < docList.length; i++) {
		                var oneDoc = {};
		                oneDoc["label"] = docList[i]["docName"];
		                oneDoc["value"] = "{\"docNamespace\":\"" + docList[i]["docNamespace"] + "\",\"docTable\":\"" + docList[i]["docTable"]
		                    + "\",\"docID\":" + docList[i]["docID"] + "}";
		                docIndexMap["{\"docNamespace\":\"" + docList[i]["docNamespace"] + "\",\"docTable\":\"" + docList[i]["docTable"]
		                + "\",\"docID\":" + docList[i]["docID"] + "}"] = i;
		
		                docListBoxSource.push(oneDoc);        
		            }
		            
		            $("#docListBox").jqxListBox({source: docListBoxSource});
		            $('#docListBox').jqxListBox('refresh');
		            
		            if (docList.length == 1) {
		            	$('#docListBox').jqxListBox('selectIndex', 0);
		            }
		            
		            crfSelectDisabled = true;
		            //frameInstanceValidated(frameInstanceID);
			        prevFrameInstanceIndex = currFrameInstanceIndex;

		        }
		
		        getDocumentHistory();
		        
		        $('#crfSelect').val(frameInstanceID).trigger("change");
		        
		        //$("#dataElementTable").jqxDataTable('refresh');
		
		        
		        //highlightText();
		        
		        userActions = 0;
		
		        clog("end of load frame docNamespace: " + docNamespace + ", docTable: " + docTable + ", docID: " + docID);
		
		        closeDialogLoad();
		    }
        )};
    });
	
}

function loadFrameInstanceNoRT()
{
	if (frameInstanceData == null)
		return;

	/*
    //set HTML elements
    for (var i=0; i<frameInstanceData.length; i++) {
    	var elementHTMLID = frameInstanceData[i]["elementHTMLID"];
    	var valueHTMLID = frameInstanceData[i]["valueHTMLID"];
    	var value = frameInstanceData[i]["value"];
    	var elementType = frameInstanceData[i]["elementType"];

    	var htmlID = elementHTMLID;
    	if (elementType != 'text')
    		htmlID = valueHTMLID;

    	clog("htmlID: " + jq(htmlID));
    	var element = $(jq(htmlID));

		clog(element.prop('tagName') + "," + element.attr('type'));
		if (element.prop('tagName').toLowerCase() == "input") {
			var elType = element.attr('type').toLowerCase();
			if (elType == "text") {
				element.val(value);
			}

			else if (elType == "checkbox" || elType == "radio") {
				element.prop('checked', true);
			}

		}
    }
    */

	setHTMLElements();
}

function loadEntity(colNames, colValues)
{
	clog("colNames: " + colNames + ", colValues: " + colValues);

	openDialogLoad();
	var getFrameInstanceIDAjax = jsRoutes.controllers.Application.getFrameInstanceID(colNames, colValues);
	$.ajax({
		type: 'GET',
		url: getFrameInstanceIDAjax.url,
		cache: false
	}).done(function(data) {
		clog("getframeinstanceid: " + data);
		var map = JSON.parse(data);

		loadFrameInstance(map["frameInstanceID"], true);


		currFrameInstanceIndex = $('#crfSelect option').filter(function(index) { return $(this).text() === map["frameInstanceName"]; }).index();
		$('#crfSelect').prop('selectedIndex', currFrameInstanceIndex);

		closeDialogLoad();
	})
}

function addSection(sectionName)
{
	openDialogLoad();
	var addSectionAjax = jsRoutes.controllers.Application.addSection(sectionName);
	$.ajax({
		type: 'GET',
		url: addSectionAjax.url,
		cache: false
	}).done(function(data) {
		clog(data);
		if (data.length > 0) {
			loadCRFData(JSON.parse(data));
			loadFrameInstance(currFrameInstanceID, false);
			//$("#validatedButtonDiv").show();
		}

		closeDialogLoad();
	})
}

function removeSection(sectionName)
{
	var index = sectionName.lastIndexOf("_");
	var repeatIndex = parseInt(sectionName.substring(index+1));
	sectionName = sectionName.substring(0, index);

	openDialogLoad();
	var removeSectionAjax = jsRoutes.controllers.Application.removeSection(sectionName, repeatIndex);
	$.ajax({
		type: 'GET',
		url: removeSectionAjax.url,
		cache: false
	}).done(function(data) {
		clog(data);
		if (data.length > 0) {
			loadCRFData(JSON.parse(data));
			loadFrameInstance(currFrameInstanceID, false);
			//$("#validatedButtonDiv").show();
		}

		//reload document annotations
		var getDocumentAnnotationsAjax = jsRoutes.controllers.Application.getDocumentAnnotations();
		$.ajax({
			type: 'POST',
			url: getDocumentAnnotationsAjax.url,
			data: {docNamespace:docNamespace,docTable:docTable,docID:docID},
			cache: false
		}).done(function(data) {
			clog("clear element ranges: " + data);
			annotList = JSON.parse(data);
			getHighlightRanges();

			//clear user-highlighted element
			//highlightRange.start = 0;
			//highlightRange.end = -1;
			//highlightRanges = [];
			highlightRangeList = undefined;
			highlightText();
			selectFlag = false;

			closeDialogLoad();
		})
	})
}

function addElement(id)
{
	clog("add Element: docFeatureValue:" + docFeatureValue + " select flag: " + selectFlag);
	

	/*
	//if (selectFlag && docFeatureValue == null) {
	if (docFeatureValue == null) {
		index = id.lastIndexOf("_");
		id = id.substring(0, index);

		clog("add element: " + id);
		openDialogLoad();
		var addElementAjax = jsRoutes.controllers.Application.addElement(id);
		$.ajax({
			type: 'GET',
			url: addElementAjax.url,
			cache: false
		}).done(function(data) {
			clog(data);
			if (data.length > 0) {
				//loadCRFData(JSON.parse(data));
				loadFrameInstance(currFrameInstanceID, false);
			}
			
			closeDialogLoad();
		})
	}

	selectFlag = false;
	*/
	
	var index = id.lastIndexOf("_");
	id = id.substring(0, index);
	
	var origID = id;
	
	clog("add element id: " + id);
	
	var gridIndex = elementHTMLIDMap[id];
	clog(JSON.stringify(elementHTMLIDMap));
	
	index = id.lastIndexOf("_");
	id = id.substring(0, index);
	
	clog("add element id: " + id + " index: " + gridIndex);

	
	var i;
	for (i=gridIndex; i<gridData.length; i++) {
		var htmlID = gridData[i]["elementHTMLID"];
		index = htmlID.lastIndexOf("_");

		if (htmlID.substring(0, index) != id)
			break;
		
		var repeatNum = parseInt(htmlID.substring(index+1));
	}
	
	clog("i: " + i + "htmlID: " + htmlID);
	
	id = id + "_" + (repeatNum + 1);
	
	var newRow = {};
	for (var j in gridData[i-1])
	   newRow[j] = gridData[i-1][j];
	
	gridData.splice(i-1, 0, newRow);
	gridData[i]["value"] = "<input type='text' id='" + id + "'  name='"+ id + "' /><input type='button' id='" + id + "_remove' value='-' onclick='removeElement(this.id)'/>"
	gridData[i]["elementHTMLID"] = id;
	
	gridData2.splice(i-1, 0, newRow);
	gridData2[i]["value"] = "<input type='text' id='" + id + "'  name='"+ id + "' /><input type='button' id='" + id + "_remove' value='-' onclick='removeElement(this.id)'/>"
	gridData2[i]["elementHTMLID"] = id;
	
	//elementHTMLIDMap[id] = i;
	refreshElementHTMLIDMap();
	
	clog("value: " + gridData[i]["value"]);

	//gridData[i-1]["value"] = gridData[i-1]["value"].concat("<input type='button' id='" + origID + "_remove' value='-' onclick='removeElement(this.id)'/>");
	//clog("value: " + gridData[i-1]["value"]);

	
	$("#dataElementTable").jqxDataTable('updateBoundData');
	
	
	if (docFeatureValue == null) {
		index = id.lastIndexOf("_");
		id = id.substring(0, index);

		clog("add element: " + id);
		openDialogLoad();
		var addElementAjax = jsRoutes.controllers.Application.addElement(origID);
		$.ajax({
			type: 'GET',
			url: addElementAjax.url,
			cache: false
		}).done(function(data) {
			/*
			clog(data);
			if (data.length > 0) {
				//loadCRFData(JSON.parse(data));
				loadFrameInstance(currFrameInstanceID, false);
			}
			*/
			
			loadFrameInstanceNoRT();
			
			closeDialogLoad();
		})
	}

	selectFlag = false;
}

function removeElement(id)
{
	clog("remove element");
	if (!selectFlag && docFeatureValue == null) {
		var selection = $("#dataElementTable").jqxDataTable('getSelection');
		if (selection != undefined) {
			var elementID = selection[0]["elementID"];
			index = id.lastIndexOf("_");
			id = id.substring(0, index);
			index = id.lastIndexOf("_");
			var repeatNum = parseInt(id.substring(index+1));

			clog("remove element: " + id);
			var gridIndex = elementHTMLIDMap[id];
			
			gridData.splice(gridIndex, 1);
			gridData2.splice(gridIndex, 1);
			
			var index;
			for (index = gridIndex; index<gridData.length; index++) {
				var elementHTMLID = gridData[index]["elementHTMLID"];
				var index2 = elementHTMLID.lastIndexOf("_");
				elementHTMLID = elementHTMLID.substring(0, index2+1) + repeatNum;
				gridData[index]["elementHTMLID"] = elementHTMLID;
				gridData2[index]["elementHTMLID"] = elementHTMLID;
				repeatNum++;
			}
			
			refreshElementHTMLIDMap();
			for (index=0; index<frameInstanceData.length; index++) {
				clog("frameinstancedata[" + index + "]: " + frameInstanceData[index]["elementHTMLID"]);
				
				if (frameInstanceData[index]["elementHTMLID"] == id) {
					frameInstanceData.splice(index, 1);
					break;
				}
			}
			
			$("#dataElementTable").jqxDataTable('updateBoundData');
			
			
			openDialogLoad();
			var removeElementAjax = jsRoutes.controllers.Application.removeElement(elementID, id);
			$.ajax({
				type: 'GET',
				url: removeElementAjax.url,
				cache: false
			}).done(function(data) {
				clog(data);
				if (data.length > 0) {
					var dataObj = JSON.parse(data);
					highlightRangeMap = dataObj[1][1];
					clog("highlightrangemap: " + JSON.stringify(highlightRangeMap));
					
					//loadCRFData(JSON.parse(data));
					//loadFrameInstance(currFrameInstanceID, false);
					
					loadFrameInstanceNoRT();
				}

				//reload document annotations
				var getDocumentAnnotationsAjax = jsRoutes.controllers.Application.getDocumentAnnotations();
				$.ajax({
					type: 'POST',
					url: getDocumentAnnotationsAjax.url,
					data: {docNamespace:docNamespace,docTable:docTable,docID:docID},
					cache: false
				}).done(function(data) {
					clog("clear element ranges: " + data);
					annotList = JSON.parse(data);
					getHighlightRanges();

					//clear user-highlighted element
					//highlightRange.start = 0;
					//highlightRange.end = -1;
					//highlightRanges = [];
					highlightRangeList = undefined;
					highlightText();
					selectFlag = false;
					
					$("#docFeatures input:radio").attr("checked", false);
					$('[name=docfeature]').each(function() {
						var keyValue = JSON.parse($(this).val());
						$(this).next().html(keyValue["key"] + ": " + keyValue["value"]);
					});

					closeDialogLoad();
				})
			})
		}
	}

	selectFlag = false;
}

function refreshElementHTMLIDMap()
{
	elementHTMLIDMap = {};
	elementIDMap = {};
	var i;
	for (i=0; i<gridData.length; i++) {
		var row = gridData[i];
		elementHTMLIDMap[row["elementHTMLID"]] = i;
		elementIDMap[row["elementID"]] = i;
	}
}

function loadCRFData(elementList)
{
	//var elementList = JSON.parse(data);

	gridData = new Array();

	var i;
	for (i=0; i<elementList.length; i++) {
		var row = {};
		element = elementList[i];
		row["section"] = element["section"];
		row["element"] = "<label class='unselectable'><b>" + element["display"] + "</b></label>";
		row["value"] = element["html"];
		row["elementID"] = element["elementID"];
		row["elementHTMLID"] = element["htmlID"];
		row["elementType"] = element["elementType"];

		elementIDMap[row["elementID"]] = i;
		elementHTMLIDMap[row["elementHTMLID"]] = i;

		gridData[i] = row;
		gridData2[i] = row;
		//clog(row["elementID"] + ", " + row["element"] + ", " + row["elementHTMLID"]);
	}

	gridSource.localData = gridData;
	dataAdapter = new $.jqx.dataAdapter(gridSource, {
	       loadComplete: function (data) { },
	       loadError: function (xhr, status, error) { }
	   });
    $("#dataElementTable").jqxDataTable({ source: dataAdapter });
}

function clearElement()
{
	var selection = $("#dataElementTable").jqxDataTable('getSelection');

	if (typeof (selection[0]) != "undefined") {
		var elementID = selection[0]["elementID"];
		var elementHTMLID = selection[0]["elementHTMLID"];
		var elementType = selection[0]["elementType"];
		var start = selection[0]["start"];
		var elementIndex = elementIDMap[elementID];
		gridData2[elementIndex]["start"] = 0;
		gridData2[elementIndex]["end"] = -1;
		gridData2[elementIndex]["elementValue"] = "";

		clog("clear: " +  elementID);
		clog("clear: " +  elementHTMLID);
		clog("clear: " +  elementType);

		openDialogLoad();
		var clearElementAjax = jsRoutes.controllers.Application.clearElement(elementID, elementHTMLID);
		$.ajax({
			type: 'GET',
			url: clearElementAjax.url,
			cache: false
		}).done(function(data) {
			clog(data);
			var dataObj = JSON.parse(data);
			frameInstanceData = dataObj[0];
			highlightRangeMap = dataObj[1];

			//reload document annotations
			var getDocumentAnnotationsAjax = jsRoutes.controllers.Application.getDocumentAnnotations();
			$.ajax({
				type: 'POST',
				url: getDocumentAnnotationsAjax.url,
				data: {docNamespace:docNamespace,docTable:docTable,docID:docID},
				cache: false
			}).done(function(data) {
				clog("clear element ranges: " + data);
				annotList = JSON.parse(data);
				getHighlightRanges();

				//clear user-highlighted element
				//highlightRange.start = 0;
				//highlightRange.end = -1;
				//highlightRanges = [];
				highlightRangeList = undefined;
				highlightText();
				selectFlag = false;

				var element = $(jq(elementHTMLID));
				if (elementType == "text" || elementType == 'textarea')
					element.val('');
				else {
					$('input[name="' + elementHTMLID + '"]').prop('checked',false);
				}

				$("#docFeatures input:radio").attr("checked", false);
				$('[name=docfeature]').each(function() {
					var keyValue = JSON.parse($(this).val());
					$(this).next().html(keyValue["key"] + ": " + keyValue["value"]);
				});

				closeDialogLoad();
			})
		})
	}
}

function clearValue(valueHTMLID)
{
	openDialogLoad();
	var clearElementAjax = jsRoutes.controllers.Application.clearValue(valueHTMLID);
	$.ajax({
		type: 'GET',
		url: clearElementAjax.url,
		cache: false
	}).done(function(data) {
		var dataObj = JSON.parse(data);
		frameInstanceData = dataObj[0];
		highlightRangeMap = dataObj[1];

		//reload document annotations
		var getDocumentAnnotationsAjax = jsRoutes.controllers.Application.getDocumentAnnotations();
		$.ajax({
			type: 'POST',
			url: getDocumentAnnotationsAjax.url,
			data: {docNamespace:docNamespace,docTable:docTable,docID:docID},
			cache: false
		}).done(function(data) {
			clog("clear element ranges: " + data);
			annotList = JSON.parse(data);
			getHighlightRanges();

			//clear user-highlighted element
			//highlightRange.start = 0;
			//highlightRange.end = -1;
			//highlightRanges = [];
			highlightRangeList = undefined;
			highlightText();
			selectFlag = false;

			/*
			var element = $(jq(elementHTMLID));
			if (elementType == "text")
				element.val('');
			else {
				$('input[name="' + elementHTMLID + '"]').prop('checked',false);
			}

			$("#docFeatures input:radio").attr("checked", false);
			$('[name=docfeature]').each(function() {
				var keyValue = JSON.parse($(this).val());
				$(this).next().html(keyValue["key"] + ": " + keyValue["value"]);
			});
			*/

			closeDialogLoad();
		})
	})
}

function clearAll()
{
	openDialogLoad();
	var clearAllAjax = jsRoutes.controllers.Application.clearAll();
	$.ajax({
		type: 'POST',
		url: clearAllAjax.url,
		data: {docNamespace:docNamespace,docTable:docTable,docID:docID},
		cache: false
		//async: false
	}).done(function(data) {
		annotList = JSON.parse(data);
		
		highlightRangeList = undefined;
		highlightRanges = undefined;
		highlightRangeMap = {};
	    highlightText();
	    frameInstanceData = [];
	    setHTMLElements();
	    //loadFrameInstanceNoRT();
	    
	    getHighlightRanges();
	    highlightText();
	    
	    $("#dataElementTable").jqxDataTable('refresh');
	    
		closeDialogLoad();
	})


}

function valueClick(event)
{
	clog("value click: " + event.target.id + " metaKey: " + event.metaKey + " ctrlKey: " + event.ctrlKey);
	clickValueElement = event.target;

	var add = event.metaKey;
	if (!add)
		add = event.ctrlKey;

	valueClickCount2++;
	valueClickCallback(add);
}

function valueClickCallback(add)
{
	clog("value click count = " + valueClickCount1 + ", " + valueClickCount2) ;

	if (valueClickCount1 > 1)
		valueClickCount1 = 0;

	if (valueClickCount1 != 1 || valueClickCount2 != 1)
		return;

	valueClickCount1 = 0;
	valueClickCount2 = 0;
	var start = selectRange.start;
	var end = selectRange.end;

	if (tokenSelectFlag) {
		//use token-based highlighting
		var start2 = origText.substring(0, start).lastIndexOf(" ");
		var end2 = origText.indexOf(" ", end);

		start = start2+1;
		end = end2;
		clog("token-based: " + start + ", " + end);
	}

	var annotFeatures = null;

	if (docFeatureValue != null) {
		start = -1;
		end = -1;
		clickValue = docFeatureValue;
		annotFeatures = '{"key":"' + docFeatureKey + '","value":"' + clickValue + '"}';
	}

	clog("value click highlighted: " + start + "," + end + " selectFlag: " + selectFlag + " docFeatureValue: " + docFeatureValue);
    //clog(precedingRange.toString());

	var htmlID = clickValueElement.id;
	clog("value click: " + htmlID + ", " + clickValueElement.checked);

	if (!clickValueElement.checked && !add) {
		//this element was clicked off (checkbox)
		clearValue(htmlID);
		setHTMLElements();
		return;
	}


	/*
	if ((start >= end || !selectFlag) && docFeatureValue == null) {
		setHTMLElements();
		return;
	}
	*/

	if ((start >= end || !selectFlag) && docFeatureValue == null) {
		if (docNamespace == null ||  docNamespace.length == 0) {
			setHTMLElements();
			return;
		}
		else {
			//allow a form element to be set without a corresponding highlight or doc feature
			start = -1;
			end = -1;
			clickValue = '';
		}
	}

	openDialogLoad();
	var addAnnotationAjax = jsRoutes.controllers.Application.addAnnotation();
	$.ajax({
			type: 'POST',
			url: addAnnotationAjax.url,
		    data:{htmlID:htmlID, value:clickValue,start:start,end:end,docNamespace:docNamespace,docTable:docTable,docID:docID,
		    features:annotFeatures,add:add},
		    cache: false
		}).done(function(data) {

			var dataObj = JSON.parse(data);
			frameInstanceData = dataObj[0];
			highlightRangeMap = dataObj[1];

			var elType = clickValueElement.getAttribute('type').toLowerCase();
			if (elType != 'checkbox')
				htmlID = clickValueElement.parentElement.id;

			if (docFeatureValue == null) {
				//add to annotList
				//annotList.push({start:start,end:end,annotType:""});
				//getHighlightRanges();

				var getDocumentAnnotationsAjax = jsRoutes.controllers.Application.getDocumentAnnotations();
				$.ajax({
					type: 'POST',
					url: getDocumentAnnotationsAjax.url,
					data: {docNamespace:docNamespace,docTable:docTable,docID:docID},
					cache: false
				}).done(function(data) {
					clog("clear element ranges: " + data);
					annotList = JSON.parse(data);

					//highlightRange.start = start;
					//highlightRange.end = end;
			    	//highlightRanges = [[start,end]];

					highlightRangeList = highlightRangeMap[htmlID];
					getHighlightRanges();

					highlightText();
				})
			}

			else {
				$('#docfeature:checked').each(function() {
					$(this).next().html("<span class='highlight' style='background-color:lightgray'>" + annotFeatures["key"] + ": " + annotFeatures["value"] + "</span>");
				});

				docFeatureValue = null;
			}

			var elementIndex = elementHTMLIDMap[htmlID];

			if (elementIndex != undefined) {
				var rowData = gridData2[elementIndex];

				clog("elementIndex: " + elementIndex + " htmlID: " + htmlID);
				clog("rowData: " + start + "," + end);


				//rowData['start'] = start;
				//rowData['end'] = end;
				rowData['elementValue'] = clickValue;
				//rowData['valueHTMLID'] = htmlID;
				rowData["docNamespace"] = docNamespace;
				rowData["docTable"] = docTable;
				rowData["docID"] = docID;
				rowData['annotFeatures'] = annotFeatures;
			}

			closeDialogLoad();
		}).fail(function () {
	});


	selectFlag = false;


	if (window.getSelection) {
	  if (window.getSelection().empty) {  // Chrome
	    window.getSelection().empty();
	  }
	  else if (window.getSelection().removeAllRanges) {  // Firefox
	    window.getSelection().removeAllRanges();
	  }
	}
	else if (document.selection) {  // IE?
		document.selection.empty();
	}
}

function valueMouseover(valueElement)
{
	clog("mouse over! " + valueElement.id + ", " + valueElement.name);

	highlightRangeList = highlightRangeMap[valueElement.id];
	var highlightMap;
	if (highlightRangeList != null) {
		rowStart = highlightRangeList[0]["start"];
		rowEnd = highlightRangeList[0]["end"];
		highlightMap = highlightRangeList[0];
	}
	else
		return;


	clog("rowStart: " + rowStart);
	clog("rowEnd: " + rowEnd);

	var elementIndex = elementHTMLIDMap[valueElement.parentElement.id];

	var selection = $("#dataElementTable").jqxDataTable('getSelection');
	if (selection == undefined || selection[0] == undefined || selection[0]["elementHTMLID"] != valueElement.parentElement.id)
		return;

	var rowData = gridData2[elementIndex];

	var rowStart = highlightMap["start"];
	var rowEnd = highlightMap["end"];

	//clog("rowselect: " + docNamespace + "," + docTable + "," + docID);
    var docIndex = docIndexMap["{\"docNamespace\":\"" + highlightMap["docNamespace"] + "\",\"docTable\":\"" + highlightMap["docTable"] + "\",\"docID\":" + highlightMap["docID"] + "}"];
    if (highlightMap["docNamespace"] != docNamespace || highlightMap["docTable"] != docTable || highlightMap["docID"] != docID) {
	    getDocument("{\"docNamespace\":\"" + highlightMap["docNamespace"] + "\",\"docTable\":\"" + highlightMap["docTable"] + "\",\"docID\":" + highlightMap["docID"] + "}",
	    	docIndex, true, [rowStart], function (options) {
		    	clog("highlightRangeList: " + JSON.stringify(highlightRangeList));
		    	var rowStart = options[0];
		    	/*
		    	if (rowStart >= 0) {
			    	//highlightRange.start = rowStart;
			    	//highlightRange.end = rowEnd;
			    	//highlightRanges[0] = [rowStart,rowEnd];
		    		getHighlightRanges();
			    	highlightText();
			    }
			    */
			    if (rowData["annotFeatures"] != undefined && rowData["annotFeatures"].length > 0) {
			    	//this is document metadata
			    	clog(rowData["annotFeatures"]);
			    	var annotFeatures = JSON.parse(rowData["annotFeatures"]);
			    	clog(annotFeatures);
			    	var id = annotFeatures["key"] + "_docfeature";
			    	$('#' + id).prop('checked', true);
			    	$('#' + id).next().html("<span class='highlight' style='background-color:yellow'>" + annotFeatures["key"] + ": " + annotFeatures["value"] + "</span>");
					//highlightRange.start = 0;
					//highlightRange.end = -1;
					//highlightRanges = [];
			    	highlightRangeList = undefined;
			    	highlightText();
			    }
			    else {
			    	//highlightRange.start = rowStart;
			    	//highlightRange.end = rowEnd;
			    	//highlightRanges[0] = [rowStart,rowEnd];

			    	if (rowStart < 0)
			    		highlightRangeList = undefined;

		    		getHighlightRanges();
			    	highlightText();
			    }
	    });
    }
    else {
		$('#docListBox').jqxListBox('refresh');
		$('#docListBox').jqxListBox('ensureVisible', docIndex);
		$('#docListBox').jqxListBox('selectIndex', docIndex);
    }

    if (rowData["annotFeatures"] != undefined && rowData["annotFeatures"].length > 0) {
    	//this is document metadata
    	clog(rowData["annotFeatures"]);
    	var annotFeatures = JSON.parse(rowData["annotFeatures"]);
    	clog(annotFeatures);
    	var id = annotFeatures["key"] + "_docfeature";
    	$('#' + id).prop('checked', true);
    	$('#' + id).next().html("<span class='highlight' style='background-color:yellow'>" + annotFeatures["key"] + ": " + annotFeatures["value"] + "</span>");
		//highlightRange.start = 0;
		//highlightRange.end = -1;
		//highlightRanges = [];
    	highlightRangeList = undefined;
    	highlightText();
    }
    else {
    	//highlightRange.start = rowStart;
    	//highlightRange.end = rowEnd;
    	//highlightRanges[0] = [rowStart,rowEnd];

    	if (rowStart < 0)
    		highlightRangeList = undefined;

		getHighlightRanges();
    	highlightText();
    	//scrollTextareaToPosition($('#docPanel'), lastEnd+1000);
    	
    	if (highlightRangeList != undefined) {
		    var lastEnd = highlightRangeList[0]["end"];
			scrollTextareaToPosition($('#docPanel'), lastEnd);
	    }
    }

    clog("index: " + docIndex);
}

function addDocumentHistory(docInfoStr)
{
	var docInfo = JSON.parse(docInfoStr);

	openDialogLoad();
	var addDocHistAjax = jsRoutes.controllers.Application.addDocumentHistory();
	$.ajax({
			type: 'POST',
			url: addDocHistAjax.url,
			data:{docNamespace:docInfo["docNamespace"],
			docTable:docInfo["docTable"],docID:docInfo["docID"]},
			cache: false
		}).done(function(data) {
			closeDialogLoad();
		}).fail(function () {
	});
}

function getDocumentHistory()
{
	openDialogLoad();
	var getDocHistAjax = jsRoutes.controllers.Application.getDocumentHistory();
	$.ajax({
			type: 'GET',
			url: getDocHistAjax.url,
			cache: false
		}).done(function(data) {
			clog("doc hist: " + data);
			docHistory = JSON.parse(data);
			for (var key in docHistory) {
			    var keyStr = JSON.stringify(key);
			    key["index"] = docIndexMap[keyStr];
			}
			$('#docListBox').jqxListBox('refresh');
			closeDialogLoad();

		}).fail(function () {
	});
}

function clearDocumentHistory()
{
	openDialogLoad();
	var clearDocHistAjax = jsRoutes.controllers.Application.clearDocumentHistory();
	$.ajax({
			type: 'GET',
			url: clearDocHistAjax.url,
			cache: false
		}).done(function(data) {
			docHistory = {};
			$('#docListBox').jqxListBox('clearSelection');
			$('#docListBox').jqxListBox('refresh');
			closeDialogLoad();
		}).fail(function () {
	});
}

function jq( myid ) {
    return "#" + myid.replace(/(\(|\)|\/|:|\.|\[|\]|,|\<|\>|\=|\%|\*|\&|\!|\@|\#|\$|\^|\+|\:|\;|\~)/g, "\\$1");
}

function prevInstance()
{
	if (currFrameInstanceIndex > 1) {
		currFrameInstanceIndex--;
		var frameInstanceID = frameArray[currFrameInstanceIndex-1]["frameInstanceID"];
		//$('#crfSelect').prop('selectedIndex', currFrameInstanceIndex);
		//$('#crfSelect').val(frameInstanceID).trigger("change");
		
		currTableOffset.top = 0;
		currTableOffset.left = 0;
		loadFrameInstance(frameInstanceID, true);
		//frameInstanceValidated(frameInstanceID);
	}
}

function nextInstance()
{
	if (currFrameInstanceIndex < frameArray.length) {
		currFrameInstanceIndex++;
		var frameInstanceID = frameArray[currFrameInstanceIndex-1]["frameInstanceID"];

		clog("next currFrameInstanceIndex: " + currFrameInstanceIndex);
		clog("next frameInstanceID: " + frameInstanceID);

		//$('#crfSelect').prop('selectedIndex', currFrameInstanceIndex);
		
		//frameInstanceValidated(frameInstanceID);
		//$('#crfSelect').val(frameInstanceID).trigger("change");
		currTableOffset.top = 0;
		currTableOffset.left = 0;
		loadFrameInstance(frameInstanceID, true)
	}
}

function docFeatureClicked(value, id)
{
	clog("feature clicked! " + value + " id: " + id);
	clog($('#' + id).next().text());

	$('[name=docfeature]').each(function() {
		var keyValue = JSON.parse($(this).val());
		$(this).next().html(keyValue["key"] + ": " + keyValue["value"]);
	});

	var keyValue = JSON.parse(value);
	$('#' + id).next().html("<span class='highlight' style='background-color:lightblue'>" + keyValue["key"] + ": " + keyValue["value"] + "</span>");
	docFeatureKey = keyValue["key"];
	docFeatureValue = keyValue["value"];
	docFeatureID = id;
	docFeatureRange.start = selectRange.start;
	docFeatureRange.end = selectRange.end;
	selectFlag = true;
}

function scrollTextareaToPosition($textarea, position) {
	clog("scrolltextarea: " + position);
	var text = origText;
	var textBeforePosition = text.substr(0, position);
	var textAfterPosition = text.substr(position);
	$textarea.val(textBeforePosition);
	var scrollHeight = $textarea.prop("scrollHeight");
    //$textarea.scrollTop(scrollHeight);
	$textarea.val(text);
	
	var innerHeight = $textarea.innerHeight();
	
	clog("scrollheight: " + scrollHeight + ", innerHeight: " + innerHeight);
	if (scrollHeight > innerHeight)
		$textarea.scrollTop(scrollHeight - (innerHeight / 2));
	else {
		$textarea.scrollTop(0);
	}
	
}

function update(e) {
	if (e.metaKey || e.ctrlKey) {
		$(this).getSelection.removeRanges();
		return;
	}

	// here we fetch our text range object
	selectRange = $(this).getSelection();
	//clog("selectRange: " + JSON.stringify(selectRange));

	//clog("select range start: " + selectRange.start + " end: " + selectRange.end + " docFeatureRange start: " + docFeatureRange.start + " end: " + docFeatureRange.end + " selectFlag: " + selectFlag);

	//clear doc features if selection made
	if ((selectRange.start != docFeatureRange.start || selectRange.end != docFeatureRange.end) && selectRange.start < selectRange.end) {
		$("#docFeatures input:radio").attr("checked", false);
		$('[name=docfeature]').each(function() {
			var keyValue = JSON.parse($(this).val());
			$(this).next().html(keyValue["key"] + ": " + keyValue["value"]);
		});

		docFeatureValue = null;
		clog("!!!! select flag true");
		selectFlag = true;

		//docFeatureRange.start = selectRange.start;
		//docFeatureRange.end = selectRange.end;

		docFeatureRange.start = -1;
		docFeatureRange.end = -1;
	}
	else if (selectRange.start == selectRange.end)
		selectFlag = false;


}

function onInput(input) {
	//if (highlightRange.start > highlightRange.end)
	//	return null;

    //return [[highlightRange.start, highlightRange.end]];
	return highlightRanges;
}



function openClearAllDialog()
{
	$('#dialogClearAll').jqxWindow('open');
}

function inHighlight(pos)
{
	for (var i=0; i<highlightRanges.length; i++) {
		var range = highlightRanges[i];
		if (pos >= range[0] && pos <= range[1]) {
			highlightMenuStart = range[0];
			return true;
		}
	}

	return false;
}

function getHighlightRangesNoOverlap()
{
	highlightRanges = [];
//	highlightRanges.push([-1,-1]);
	for (var i=0; i<annotList.length; i++) {
		var annot = annotList[i];

		if (annot["start"] < 0)
			continue;

		var range = [];
		range.push(annot["start"]);
		range.push(annot["end"]);
		range.push(annot["color"]);

		if (range[0] < range[1])
			highlightRanges.push(range);


	}

	//clog("highlightranges: " + JSON.stringify(highlightRanges));

}

function getHighlightRanges()
{
	//clog("gethighlightranges: " + JSON.stringify(highlightRangeList));
	//clog("annot list: " + JSON.stringify(annotList));

	highlightRanges = [];
	highlightIndexes = [];

	//if (highlightRangeList == undefined)
	//	return getHighlightRangesNoOverlap();


	var highlightIndex = 0;
	var highlightMap = undefined;
	if (highlightRangeList != undefined)		
		highlightMap = highlightRangeList[0];
	
	var lastIndex = -1;
	var lastEnd = -1;
	var annotList2 = [];
	var lastColor = '';

	for (var i=0; i<annotList.length; i++) {
		if (annotList[i]["start"] < 0)
			continue;

		annotList2.push(annotList[i]);
	}

	var lastHighlight = false;
	for (i=0; i<annotList2.length; i++) {
		

		var annot = annotList2[i];

		/*
		if (annot["start"] < 0)
			continue;
			*/

		var range = [];


		//this range is totally overlapped by a previous highlight range
		if (lastIndex >= 0 && annot["end"] <= highlightRanges[lastIndex][1])
			continue;

		if (highlightMap != undefined && annot["start"] == highlightMap["start"] && annot["end"] == highlightMap["end"]) {


			if (annot["start"] < lastEnd) {
				if (lastIndex < i-1) {
					range.push(annot["start"]);
					if (highlightRanges.length > 0) {
						
						var range2 = highlightRanges[highlightRanges.length-1];
						range2[1] = annot["start"];
						
						if (range2[0] == range2[1])
							highlightRanges.splice(highlightRanges.length-1, 1);
						
						//highlightRanges[highlightRanges.length-1][1] = annot["start"];
					}
				}
				else
					range.push(lastEnd);
			}
			else
				range.push(annot["start"]);

			range.push(annot["end"]);
			range.push(annot["color"]);
			highlightIndexes.push(highlightRanges.length);
			lastHighlight = true;

			if (annot["end"] < lastEnd) {
				/*
				var range2 = [];
				range2.push(annot["end"]);
				range2.push(lastEnd);
				highlightRanges.push(range2);
				 */

				var annot2 = {};
				annot2["start"] = annot["end"];
				annot2["end"] = lastEnd;
				annot2["color"] = lastColor;

				var inserted = false;
				for (var j=0; j<annotList2.length; j++) {
					if (annotList2[j]["start"] > annot2["start"]) {
						annotList2.splice(j, 0, annot2);
						inserted = true;
						break;
					}
				}

				if (!inserted)
					annotList2.push(annot2);
			}


			lastIndex = highlightRanges.length;
			highlightIndex++;

			highlightMap = highlightRangeList[highlightIndex];
		}
		else {
			if (annot["end"] <= lastEnd)
				continue;

			if (annot["start"] < lastEnd) {
				//if (lastIndex < i-1) {
				if (!lastHighlight) {
					//set previous range's end to current start
					var range2 = highlightRanges[highlightRanges.length-1];
					range2[1] = annot["start"];
					
					if (range2[0] == range2[1])
						highlightRanges.splice(highlightRanges.length-1, 1);
					
					
					range.push(annot["start"]);
				}
				else
					range.push(lastEnd);
			}
			else
				range.push(annot["start"]);


			//range.push(annot["start"]);
			range.push(annot["end"]);
			range.push(annot["color"]);

			lastHighlight = false;
		}

		if (range[0] < range[1]) {
			highlightRanges.push(range);

			lastEnd = annot["end"];
			lastColor = annot["color"];
		}
	}

	clog("annot list: " + JSON.stringify(annotList));
	//clog("annot list2: " + JSON.stringify(annotList2));
	//clog("highlightranges: " + JSON.stringify(highlightRanges));
	//clog("highlightindexes: " + JSON.stringify(highlightIndexes));
}

function fillSlot(htmlID)
{
	for (var i=0; i<annotList.length; i++) {
		if (annotList[i]["start"] == highlightMenuStart) {
			var annotType = annotList[i]["annotType"];
			var start = annotList[i]["start"];
			var end = annotList[i]["end"];

			openDialogLoad();
			var fillSlotAjax = jsRoutes.controllers.Application.fillSlot();
			$.ajax({
					type: 'POST',
					url: fillSlotAjax.url,
					data: {slotName:htmlID,start:start,end:end,docNamespace:docNamespace,docTable:docTable,docID:docID},
					cache: false
				}).done(function(data) {
				var valueMap = JSON.parse(data);
				var newRow = {};
				newRow["htmlID"] = valueMap["htmlID"];
				newRow["value"] = valueMap["value"];
				var elementID = valueMap["elementID"];
				var sectionSlot = valueMap["sectionSlot"];
				var elementSlot = valueMap["elementSlot"];
				elementID = elementID + "_" + sectionSlot + "_" + elementSlot;
				frameInstanceData.push(newRow);


				//set the row in the data table
				for (var i=0; i<gridData.length; i++) {
					//clog("elementID: " + gridData[i]["elementID"] + ", " + elementID);
					if (gridData[i]["elementID"] == elementID) {
						$('#dataElementTable').jqxDataTable('selectRow', i);
						gridData[i]["start"] = start;
						gridData[i]["end"] = end;
						gridData[i]["docNamespace"] = docNamespace;
						gridData[i]["docTable"] = docTable;
						gridData[i]["docID"] = docID;
						break;
					}
				}

				setHTMLElements();

			    closeDialogLoad();
			});

			break;
		}
	}
}

function clearFromMenu()
{
	for (var i=0; i<gridData.length; i++) {
		if (gridData[i]["start"] == highlightMenuStart) {
			$('#dataElementTable').jqxDataTable('selectRow', i);
			clearElement();

			//clog("frameInstanceData: " + JSON.stringify(frameInstanceData) + ", " + JSON.stringify(gridData));

			//remove from frameInstanceData
			for (var j=0; j<frameInstanceData.length; j++) {
				if (frameInstanceData[j]["htmlID"] == gridData[i]["valueHTMLID"]) {
					frameInstanceData.splice(j, 1);
					break;
				}
			}

			loadFrameInstanceNoRT();
			selectFlag = false;
			break;
		}
	}
}

function fillAll()
{
	openDialogLoad();
	var fillAllAjax = jsRoutes.controllers.Application.fillAll();
	$.ajax({
			type: 'GET',
			url: clearDocHistAjax.url,
			cache: false
		}).done(function(data) {
			docHistory = {};
			$('#docListBox').jqxListBox('clearSelection');
			$('#docListBox').jqxListBox('refresh');

			closeDialogLoad();
		}).fail(function () {
	});
}

function openDialogLoad()
{
	if (loadDialogCount == 0) {
		$('#dialogLoadInstance').jqxWindow('open');
	}

	loadDialogCount++;
}

function closeDialogLoad()
{
	if (loadDialogCount == 1) {
		$('#dialogLoadInstance').jqxWindow('close');
	}

	loadDialogCount--;
}

function openFrameInstanceLock()
{
        $('#frameInstanceLock').show();
}

function closeFrameInstanceLock()
{
    $('#frameInstanceLock').hide();
}

function setHTMLElements()
{
	//clear checkboxes and radios and textboxes and text areas
	$('#dataElementTable input:checkbox').prop('checked', false);
	$('#dataElementTable input:radio').prop('checked', false);
	$('#dataElementTable input:text').val('');
	$('#dataElementTable textarea').val('');

	//set HTML elements
    for (var i=0; i<frameInstanceData.length; i++) {
    	var elementHTMLID = frameInstanceData[i]["elementHTMLID"];
    	var valueHTMLID = frameInstanceData[i]["valueHTMLID"];
    	var value = frameInstanceData[i]["value"];
    	var elementType = frameInstanceData[i]["elementType"];

    	var htmlID = elementHTMLID;
    	if (elementType != 'text' && elementType != 'textarea')
    		htmlID = valueHTMLID;

    	//clog("htmlID: " + jq(htmlID));
    	var element = $(jq(htmlID));

		clog(element + "," + element.prop('tagName') + "," + element.attr('type'));
		var tagName = element.prop('tagName').toLowerCase();
		if (tagName == "input") {
			var elType = element.attr('type').toLowerCase();
			if (elType == "text" || elType == 'textarea') {
				element.val(value);
			}

			else if (elType == "checkbox" || elType == "radio") {
				element.prop('checked', true);
			}

		}
		else if (tagName == 'textarea') {
			element.val(value);
		}
    }
}

function docPanelSelect(event)
{
  clog("docpanel select " + event + ", " + event.metaKey);
	if (event.metaKey || event.ctrlKey) {
		if (window.getSelection) {
		  if (window.getSelection().empty) {  // Chrome
		    window.getSelection().empty();
		  } else if (window.getSelection().removeAllRanges) {
			  clog("here2: " + window.getSelection()); // Firefox
		    window.getSelection().removeAllRanges();
		  }
		} else if (document.selection) {  // IE?
		  document.selection.empty();
		}
	}
}

function toggleTokenSelect()
{
	tokenSelectFlag = $('#tokenSelectButton').prop('checked');
}

(function($) {
    $.fn.getCursorPosition = function() {
        var input = this.get(0);
        if (!input) return; // No (input) element found
        if ('selectionStart' in input) {
            // Standard-compliant browsers
            return input.selectionStart;
        } else if (document.selection) {
            // IE
            input.focus();
            var sel = document.selection.createRange();
            var selLen = document.selection.createRange().text.length;
            sel.moveStart('character', -input.value.length);
            return sel.text.length - selLen;
        }
    }
})(jQuery);

function updateCRFSelect()
{
	var optionsStr = "<option selected disabled value='default'>Select Instance</option>";
	for(var i = 0; i < frameArray.length; i++) {
	    if( frameArray[i]["validatedByUserName"] != "" ) {
	        optionsStr += "<option value='" + frameArray[i]["frameInstanceID"]
	            + "'>" + frameArray[i]["name"] + " : "
	            + frameArray[i]["validatedByUserName"] + "</option>";
	    } else {
	        optionsStr += "<option value='" + frameArray[i]["frameInstanceID"] + "'>" + frameArray[i]["name"] + "</option>";
	    }
	}
	

	$("#crfSelect").find('option').remove().end().append($(optionsStr));
}

function frameInstanceValidated(frameInstanceID)
{
	//openDialogLoad();
	

	/*
    var frameInstanceValidatedAjax = jsRoutes.controllers.Application.frameInstanceValidated();
	$.ajax({
		type: 'GET',
		url: frameInstanceValidatedAjax.url,
		cache: false
	}).done(function(data) {
	*/
		
	var crfSelect = document.getElementById('crfSelect');
	var index = -1;	
	for (var i = 0; i < frameArray.length; i++) {
		//clog("frameArray: " + frameArray[i]["frameInstanceID"] + ", " + frameArray[i]["validatedByUserName"]);

	    if (frameArray[i]["frameInstanceID"] == frameInstanceID) {
	        index = i;
	        break;
	    }
	}
	
	if (index >= 0 && frameArray[index]["validatedByUserName"] != username) {
		frameArray[index]["validatedByUserName"] = username;
		clog(frameArray[index]["frameInstanceID"] + ", " + frameArray[index]["validatedByUserName"]);
		updateCRFSelect();
	}
	
	crfSelectDisabled = true;
}



function docPanelClick(cursorPosition, dblClick)
{
	var highlightElementID;
	var valueHTMLID;
	var annotClickedList = [];

	for (var elementID in highlightRangeMap) {
		//clog("elementID: " + elementID);
		var annotListLocal = highlightRangeMap[elementID];
		//clog(JSON.stringify(annotList));

		annotListLocal.forEach(function (annot) {
			//clog("start: " + annot['start'] + " end: " + annot['end']);
			if (annot['start'] <= cursorPosition && annot['end'] >= cursorPosition) {
				highlightElementID = elementID;
				//annotClickedList.push(annot);
				//elementStart = annot['start'];
				//elementEnd = annot['end'];
				//annotDisplay = annot['annotDisplay'];
			}
		});
		
		annotList.forEach(function (annot) {
			//clog("start: " + annot['start'] + " end: " + annot['end']);
			if (annot['start'] <= cursorPosition && annot['end'] >= cursorPosition) {
				//highlightElementID = elementID;
				annotClickedList.push(annot);
				//elementStart = annot['start'];
				//elementEnd = annot['end'];
				//annotDisplay = annot['annotDisplay'];
			}
		});
	}
	
	/*
	for (var i=0; i<frameInstanceData.length; i++) {
		var element = frameInstanceData[i];
		//clog("frameInstanceData: " + JSON.stringify(element) + ", " + element["elementHTMLID"] + ", " + element["valueHTMLID"]);
		if (element["elementHTMLID"] == highlightElementID)
			valueHTMLID = element["valueHTMLID"];
	}
	*/
	
	//var name = $("#" + highlightElementID).attr('id');
	if (highlightElementID != null) {
		var el = document.getElementById(highlightElementID);
		
		var name = el.name;
		
		clog('highlightelementid: ' + highlightElementID + " name: " + name + " valuehtmlid: " + valueHTMLID);
		
		
		for (index=0; index<gridData2.length; index++) {
			var rowData = gridData2[index];
			//clog(rowData['elementHTMLID']);
			if (rowData['elementHTMLID'] == name || rowData['elementHTMLID'] == highlightElementID) {
				$('#dataElementTable').jqxDataTable('selectRow', index);
				$("#dataElementTable").jqxDataTable('ensureRowVisible', index);
				break;
			}
		}
		
		if (dblClick) {
			selectRange.start = annotClickedList[0]['start'];
			selectRange.end = annotClickedList[0]['end'];
			selectFlag = true;
			
			annotClickedList.forEach(function (annot) {
				annot["color"] = "lightsalmon";
			}
			);
			
			getHighlightRanges();
			highlightText();
			
			clog("annot list docpanelclick: " + JSON.stringify(annotList));
		}
		
		loadFrameInstanceNoRT();
	}
}

function undo()
{
	clog("undo!");
	openDialogLoad();
	var undoAjax = jsRoutes.controllers.Application.undo();
	$.ajax({
		type: 'GET',
		url: undoAjax.url,
		cache: false
	}).done(function(data) {
		var dataObj = JSON.parse(data);
		
		if (dataObj.length > 0) {
			frameInstanceData = dataObj[0];
			highlightRangeMap = dataObj[1];
			
			setHTMLElements();
			
			
			//reload document annotations
			var getDocumentAnnotationsAjax = jsRoutes.controllers.Application.getDocumentAnnotations();
			$.ajax({
				type: 'POST',
				url: getDocumentAnnotationsAjax.url,
				data: {docNamespace:docNamespace,docTable:docTable,docID:docID},
				cache: false
			}).done(function(data) {
				clog("clear element ranges: " + data);
				annotList = JSON.parse(data);
				getHighlightRanges();
	
				//clear user-highlighted element
				//highlightRange.start = 0;
				//highlightRange.end = -1;
				//highlightRanges = [];
				highlightRangeList = undefined;
				highlightText();
				selectFlag = false;
				
				$("#docFeatures input:radio").attr("checked", false);
				$('[name=docfeature]').each(function() {
					var keyValue = JSON.parse($(this).val());
					$(this).next().html(keyValue["key"] + ": " + keyValue["value"]);
				});
				
				closeDialogLoad();
			})
		}
		
		else
			closeDialogLoad();

	});
}


function redo()
{
	clog("redo!");
	openDialogLoad();
	var redoAjax = jsRoutes.controllers.Application.redo();
	$.ajax({
		type: 'GET',
		url: redoAjax.url,
		cache: false
	}).done(function(data) {
		var dataObj = JSON.parse(data);
		if (dataObj.length > 0)	{
			frameInstanceData = dataObj[0];
			highlightRangeMap = dataObj[1];
			
			setHTMLElements();
			
			
			//reload document annotations
			var getDocumentAnnotationsAjax = jsRoutes.controllers.Application.getDocumentAnnotations();
			$.ajax({
				type: 'POST',
				url: getDocumentAnnotationsAjax.url,
				data: {docNamespace:docNamespace,docTable:docTable,docID:docID},
				cache: false
			}).done(function(data) {
				clog("clear element ranges: " + data);
				annotList = JSON.parse(data);
				getHighlightRanges();
	
				//clear user-highlighted element
				//highlightRange.start = 0;
				//highlightRange.end = -1;
				//highlightRanges = [];
				highlightRangeList = undefined;
				highlightText();
				selectFlag = false;
				
				$("#docFeatures input:radio").attr("checked", false);
				$('[name=docfeature]').each(function() {
					var keyValue = JSON.parse($(this).val());
					$(this).next().html(keyValue["key"] + ": " + keyValue["value"]);
				});
	
				closeDialogLoad();
			})
		}
		else
			closeDialogLoad();

	});
}

function clog(message) {
    if (window.console) {
        console.log(message);
    } 
}


function alertBoxShow(message) {
    hideSuccessBox();
    $('.jqx-window-header').hide();
    $("#errorWindow").show();
    $("#alert-box").show();
    $('.alertBoxMessage').html(message);
}

function successBoxShow(message) {
    hideAlertBox();
    $("#successWindow").show();
    $("#success-box").show();
    $('.successBoxMessage').html(message);
    setTimeout(hideSuccessBox, 1300);
}

function hideSuccessBox() {
    $("#successWindow").hide();
    $("#success-box").hide();
}

function hideAlertBox() {
    $("#errorWindow").hide();
    $("#alert-box").hide();
}

function stopEvent(event) {
	clog(event);
	event.stopPropagation();
}
