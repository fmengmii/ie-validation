var elementIDMap = {};
var elementHTMLIDMap = {};
var gridData = new Array();
var gridData2 = new Array();
var frameInstanceData;
var highlightStart;
var highlightEnd;
var currFrameInstanceID;
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
var docSelectIndex = -1;
var tokenSelectFlag = false;
var entityIDStr = null;
var colNames = null;
var colTypes = null;
var colValues = null;



//new vars
//var username;
//var password;
var wordBasedHighlighting = false;
var history = new Object();
var processID = 1;

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

	   if (docSelectIndex == item.index)
		   return;

	   docSelectIndex = item.index;

	   console.log("selected doc: " + item.index);
	   getDocument(item.value, item.index, true, null, null);
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
    	   //console.log(value);
    	   var sectionData = JSON.parse(value);
    	   //var sectionData = JSON.parse("{\"sectionID\":1}");
    	   //console.log(sectionData);
    	   var sectionName = sectionData["sectionName"];
    	   //console.log(sectionName);

    	   var index = sectionName.indexOf("|");
    	   var sectionNameShort = sectionName.substring(index+1);

    	   //var sectionHTML = sectionName;
    	   var repeat = sectionData["repeat"];
    	   var repeatIndex = sectionData["repeatIndex"];
    	   if (repeat > 0 || repeat == -1) {
    	       var sectionSlotNum = repeatIndex;
    	       repeatIndex++;
	    	   if (repeatIndex == 1) {
	    		   //sectionName = sectionNameShort +  "<br><input type='button' id='" + sectionNameShort + "' value='+' onclick='addSection(this.id)'/>";

	    		   // modify by wyu for repeat number
	    		   sectionName = sectionNameShort + "_" + repeatIndex +
	    		        "<br><input type='button' id='" + sectionNameShort + "' value='+' onclick='addSection(this.id)'/>";
	    	   }
	    	   else{
	    		   //sectionName = sectionNameShort + "<br><input type='button' id='" + sectionNameShort + "_" + repeatIndex + "' value='-' onclick='removeSection(this.id)'/>";

	    		   // modify by wyu for repeat number
	    		   sectionName = sectionNameShort  + "_" + repeatIndex +
	    		        "<br><input type='button' id='" + sectionNameShort + "_" + sectionSlotNum + "' value='-' onclick='removeSection(this.id)'/>";
	    	   }
    	   } else{
    		     sectionName = sectionNameShort;
           }
           return sectionName;
       }
   });

   $('#dataElementTable').on('rowSelect', function (event) {
	   console.log("rowselect event: " + docNamespace + "," + docTable + "," + docID);
	   //console.log(event.args.index + " meta: " + event.args.originalEvent + " ctrl: " + event.args.ctrlKey);
	   //rowSelect(event.args.row);
	   //rowSelect(event.args);
   });

   $('#dataElementTable').on('rowClick', function (event) {
	   console.log("rowClick: " + event.args.index + " meta: " + event.args.originalEvent.metaKey + " ctrl: " + event.ctrlKey);
     // console.log(event.args);
	   rowSelect(event.args);
   });

   $('#dataElementTable').on('columnResized', function (event) {
	   loadFrameInstanceNoRT();
   });

   $('#highlightMenu').jqxMenu({ width: '300px', height: '140px', autoOpenPopup: false, mode: 'popup'});

   $('#highlightMenu').on('itemclick', function (event) {
	   // get the clicked LI element.
	   var element = event.args;
	   console.log("clicked: " + element.id);
	   if (element.id == 'clear')
		   clearFromMenu();
	   else
		   fillSlot(element.id);
	});


   document.getElementById('projSelect').selectedIndex = 0;

   //jquery-fieldselection
   $('textarea').keyup(update).mousedown(update).mousemove(update).mouseup(update);
   
   
   $('#docPanel').highlightWithinTextarea(onInput);

   //$('#tokenSelectButton').button();

   /*
   $('#docPanel').on("mouseup", function(e) {
		   var pos = $('#docPanel').getCursorPosition();
		   console.log("cursor: " + pos);
		   var scrollTop = $(window).scrollTop();
           var scrollLeft = $(window).scrollLeft();
           if (inHighlight(pos)) {

        	   var annotType = "";
        	   for (var i=0; i<annotList.length; i++) {
        			if (annotList[i]["start"] == highlightMenuStart) {
        				annotType = annotList[i]["annotType"];
        				break;
        			}
        	   }

        	   openDialogLoad();
        	   var getSlotValuesAjax = jsRoutes.controllers.Application.getSlotValues(annotType);
        		$.ajax({
        			type: "GET",
        			url: getSlotValuesAjax.url
        		}).done(function(data) {
        			console.log("slot values: " + data);
        			var slotValues = JSON.parse(data);
        			var source = [];
        			for (var i=0; i<slotValues.length; i++) {
        				source.push({id:slotValues[i]["htmlID"],label:slotValues[i]["name"]});
        			}

        			//$('#highlightMenu').jqxMenu('destroy');
        			$('#highlightMenu').jqxMenu({source:source});
             	   	$('#highlightMenu').jqxMenu('open', parseInt(e.clientX) + 5 + scrollLeft, parseInt(e.clientY) + 5 + scrollTop);

             	   	closeDialogLoad();
        		});
           }
           else
        	   $('#highlightMenu').jqxMenu('close');
   });
   */


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
				  console.log("window resized!");
				  //loadFrameInstance(currFrameInstanceID, false);
				  loadFrameInstanceNoRT();
				  $('#docPanel').width($('#docDiv').width() * .95);
				  $('#docPanel').height($('#docListBox').height() - $('#docFeatures').height());
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

	console.log("getDocument: " + docInfoStr);
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

	console.log("get doc: " + docNamespace + ", " + docTable + ", " + docID);

	//text = "";
	openDialogLoad();
	var getDocumentAjax = jsRoutes.controllers.Application.getDocument();
	$.ajax({
		type: "POST",
		url: getDocumentAjax.url,
		data:{docNamespace:docNamespace, docTable:docTable, docID:docID}
	}).done(function(data) {
		console.log(data);
		var docData = JSON.parse(data);
		docName = docData["docName"];
		origText = docData["docText"];
		console.log("text len: " + origText.length);

		origText = origText.replace(/[\r]/g, '');
		console.log("text len: " + origText.length);

		//var text = origText.split("\n").join("&nbsp;<br />");
		var text = origText;

		//$('#docPanel').jqxPanel('clearcontent');
		//$('#docPanel').jqxPanel('append', "<div style='margin:10px;'>" + text + "</div>");
		//$('#docPanel').val("<div style='margin:10px;'>" + text + "</div>");
		$('#docPanel').val(text);

		annotList = docData["annotList"];
		console.log("annotList: " + JSON.stringify(annotList));
		getHighlightRanges();
		highlightText();

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

        var frameInstanceStatus = docData["frameInstanceStatus"];
        if( frameInstanceStatus == 1 ) {
            //docListBox item background color from powderblue to green  #228B22; or #4CAF50;
            $('#docListBox font').css("background-color", "#32CD32");
        }
		$("#validatedButtonDiv").show();

	}).fail(function() {
	});

	/*
	var getAnnotationsAjax = jsRoutes.controllers.Application.getAnnotations(docID);
	$.ajax({
			type: 'POST',
			url: getAnnotationsAjax.url,
			data:{docID: docID, annotTypeList: "['nodule-size','nodule-location']"}
		}).done(function(data) {
			console.log(data);

			annotList = JSON.parse(data);
			var i;
			var gridData = new Array();
			for (i=0; i<annotList.length; i++) {
				row = {};
				row["element"] = annotList[i]["annotationTypes"][0];
				start = annotList[i]["start"];
				end = annotList[i]["end"];
				features = JSON.parse(annotList[i]["features"]);
				row["value"] = features["Text"];
				row["start"] = start;
				row["end"] = end;
				gridData[i] = row;
			}

			console.log(JSON.stringify(gridData));

			gridSource.localData = gridData;
			dataAdapter = new $.jqx.dataAdapter(gridSource, {
			       loadComplete: function (data) { },
			       loadError: function (xhr, status, error) { }
			   });
            $("#dataElementTable").jqxDataTable({ source: dataAdapter });

		}).fail(function() {
	});
	*/
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
  console.log("onRightClick coming in...");
  // the following three lines will hide the right-click menu
  document.getElementById("docPanel").oncontextmenu = function() {
    return false;
  }
  console.log(history);
  if(event.button == 2) {
    //console.log("right click worked");
    // text = $("#dataElementTable").jqxDataTable('getSelection');
    // text = window.getSelection().toString();
    var text = "";

    // console.log("selectRange: " + JSON.stringify(selectRange));
    if (window.getSelection) {
        text = window.getSelection().toString();
    } else if (document.selection && document.selection.type != "Control") {
        text = document.selection.createRange().text;
    }
    // console.log(annotList);
    // var temp = document.getElementsByTagName("html");
    // console.log(temp);

    // console.log(elementIDMap);
    // console.log(elementHTMLIDMap);
    // console.log(gridData);
    // console.log(gridData2);
    // console.log(frameInstanceData);

    // console.log(highlightRangeMap);

    if(text) {
      // console.log(highlightRanges);
      if(highlightedPartIsAnnot(selectRange.start, selectRange.end)) {
        var htmlID = getAnnotFromHighlight(selectRange.start, selectRange.end);
        // console.log("htmlID: " + htmlID);
        htmlID = findAnnotID(htmlID);
        var elementID = findElementID(htmlID);
        var elementType = findElementType(htmlID);
        console.log("htmlID: " + htmlID);
        console.log("elementID: " + elementID);
        console.log("elementType: " + elementType);

        var elementIndex = elementIDMap[elementID];
        gridData2[elementIndex]["start"] = 0;
    		gridData2[elementIndex]["end"] = -1;
    		gridData2[elementIndex]["elementValue"] = "";

        clearElementOnDoubleRightClick(elementID, htmlID, elementType);
        // console.log(gridData2);


      }
    }
  }
}

function findAnnotID(str) {
  // the following commented code will not work
  // it turns out that you can't return something from a jquery thingy
  /*
  $.each(highlightRangeMap, function(key, value){
    console.log("key: " + key);
    var temp = highlightRangeMap[key][0]["annotID"];
    console.log("annotID: " + temp);
    console.log(str.localeCompare(temp));
    console.log(str.localeCompare(temp) == 0);
    if(str.localeCompare(temp) == 0) {
      return key;
    }
  });*/

  for(var key in highlightRangeMap) {
    console.log(key);
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
  console.log(mostRecentAction);
  var tempPID = mostRecentAction["id"];
  var tempAction = mostRecentAction["act"];
  var tempHTMLID = mostRecentAction["htmlID"];
  var tempExtraInfo = mostRecentAction["extraInfo"];

  console.log(tempHTMLID);
  var annotID = findAnnotID(tempHTMLID);
  var elementHTMLID = findElementID(annotID);
  var elementType = findElementType(annotID);

  console.log(annotID);
  console.log(elementType);
  console.log(elementHTMLID);


  console.log('button works');

  console.log(frameInstanceData);
  console.log(annotList);
  console.log(highlightRangeMap);

  // openDialogLoad();

  var undoAjax = jsRoutes.controllers.Application.getHistory();
  console.log(undoAjax);
  $.ajax({
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
  }).done(function(data) {
    var dataObj = JSON.parse(data);
    frameInstanceData = dataObj[0];
    highlightRangeMap = dataObj[1];

    //reload document annotations
    var getDocumentAnnotationsAjax = jsRoutes.controllers.Application.getDocumentAnnotations();
    $.ajax({
      type: 'POST',
      url: getDocumentAnnotationsAjax.url,
      data: {docNamespace:docNamespace,docTable:docTable,docID:docID}
    }).done(function(data) {
      console.log("clear element ranges: " + data);
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
  //console.log("logout");
  var log = jsRoutes.controllers.Application.logout();
	$.ajax({
			type: 'GET',
			url: log.url
		}).done(function(data) {
			docHistory = {};
		//	closeDialogLoad();
		}).fail(function () {
	});
}


/*
function toLogIn() {

    // openDialogLoad();
    console.log("Hello");
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
	var index = row.index;
	var rowData = gridData2[index];
	var rowValue = rowData["elementValue"];
	var elementType = rowData['elementType'];
	var elementHTMLID = rowData["elementHTMLID"];

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

		if (tokenSelectFlag) {
			//use token-based highlighting
			var start2 = origText.substring(0, start).lastIndexOf(" ");
			var end2 = origText.indexOf(" ", end);

			start = start2+1;
			end = end2;
			console.log("token-based: " + start + ", " + end);
		}

		value = origText.substring(start, end);
        console.log("row select highlighted: " + start + "," + end + ", value: " + value);
	}
	else if (selectFlag == undefined || !selectFlag) {
		console.log("selectFlag: " + selectFlag);
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


		console.log("rowStart: " + rowStart);
		console.log("rowEnd: " + rowEnd);
		console.log("element: " + rowData['element']);
		

		//clear any existing selections highlights for doc metadata
		$('[name=docfeature]').each(function() {
			var keyValue = JSON.parse($(this).val());
			$(this).prop('checked', false);
			$(this).next().html(keyValue["key"] + ": " + keyValue["value"]);
		});

		if (highlightRangeList != undefined) {
		    console.log("rowselect: " + docNamespace + "," + docTable + "," + docID);
		    var docIndex = docIndexMap["{\"docNamespace\":\"" + highlightMap["docNamespace"] + "\",\"docTable\":\"" + highlightMap["docTable"] + "\",\"docID\":" + highlightMap["docID"] + "}"];
		    if (highlightMap["docNamespace"] != docNamespace || highlightMap["docTable"] != docTable || highlightMap["docID"] != docID) {
			    getDocument("{\"docNamespace\":\"" + highlightMap["docNamespace"] + "\",\"docTable\":\"" + highlightMap["docTable"] + "\",\"docID\":" + highlightMap["docID"] + "}",
			    	docIndex, false, [rowStart], function (options) {
			    	console.log("highlightRangeList: " + JSON.stringify(highlightRangeList));
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
				    	console.log(annotFeatures);
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
			    });
		    }
		    else {
				$('#docListBox').jqxListBox('refresh');
				$('#docListBox').jqxListBox('ensureVisible', docIndex);
				$('#docListBox').jqxListBox('selectedIndex', docIndex);


			    if (rowStart >= 0) {
			    	//highlightRange.start = rowStart;
			    	//highlightRange.end = rowEnd;
			    	//highlightRanges[0] = [rowStart,rowEnd];
			    	getHighlightRanges();
			    	//highlightText();
			    }
			    else if (rowData["annotFeatures"] != undefined && rowData["annotFeatures"].length > 0) {
			    	console.log("annotFeatures: " + rowData["annotFeatures"]);
			    	//this is document metadata
			    	var annotFeatures = JSON.parse(rowData["annotFeatures"]);
			    	console.log(annotFeatures);
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
		    }




		    console.log("index: " + docIndex);
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
	//something was highlighted
	if (start != end || docFeatureValue != null) {

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
			console.log("elementType: " + elementType);

			var htmlID = rowData['elementHTMLID'];
			var element = $(jq(htmlID))
			console.log(element.prop('id'));

			if (elementType != undefined && (elementType == 'text') || elementType == 'textarea') {
				element.val(value);

				console.log("Add: " + docNamespace + "," + docTable + "," + docID);

				/*
				var newRow = {};
				newRow["htmlID"] = htmlID;
				newRow["value"] = value;
				frameInstanceData.push(newRow);
				*/
				


		openDialogLoad();

        var addAnnotationAjax = jsRoutes.controllers.Application.addAnnotation();

				$.ajax({
						type: 'POST',
						url: addAnnotationAjax.url,
					    data:{htmlID:htmlID, value:value,start:start,end:end,docNamespace:docNamespace,docTable:docTable,docID:docID,features:annotFeatures,add:add}
					}).done(function(data) {
						console.log("highlight: start=" + start + " end=" + end + " docFeatureValue=" + docFeatureValue);
						console.log("add annot data: " + data);

						var dataObj = JSON.parse(data);
						frameInstanceData = dataObj[0];
						highlightRangeMap = dataObj[1];

						//add to annotList

						var getDocumentAnnotationsAjax = jsRoutes.controllers.Application.getDocumentAnnotations();
						$.ajax({
							type: 'POST',
							url: getDocumentAnnotationsAjax.url,
							data: {docNamespace:docNamespace,docTable:docTable,docID:docID}
						}).done(function(data) {
							console.log("clear element ranges: " + data);
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
						});

						closeDialogLoad();

					}).fail(function () {
				});

				if (docFeatureValue == null) {
					//highlightRange.start = start;
					//highlightRange.end = end;
			    	//highlightRanges = [[start,end]];
					highlightText();
				}

				selectFlag = false;
			}
		}

	}

}

function highlightText()
{
	if (origText == null || origText.length == 0)
		return;

	/*
	highlightStart = start;
	highlightEnd = end;
	var text = origText;
	var origVPos = $("#docPanel").jqxPanel('getVScrollPosition');
	var flag = false;
	if (start < end) {
		text = origText.substring(0, start) + "<span class='highlight' style='background-color:lightgray'>" + origText.substring(start, end) + "</span>" + origText.substring(end);
		flag = true;
	}
	text = text.split("\n").join("&nbsp;<br/>");
	//console.log(text);
	$("#docPanel").jqxPanel('clearcontent');
	$("#docPanel").jqxPanel('append', '<div style="margin: 50px">' + text + '</div>');
	//$("#docPanel").jqxPanel('append', text);
	//$("#docPanel").jqxPanel('append', "</div>");

	var caretPos = origVPos;
	if (flag) {
	    console.log("vscrollpos = " + vScrollPos + " scrollHeight = " + scrollHeight);
	    //caretPos = vScrollPos;
	    var currScrollWidth = Math.round($("#docPanel").jqxPanel('getScrollWidth'));
	    var currScrollHeight = Math.round($("#docPanel").jqxPanel('getScrollHeight'));
		var factor = vScrollPos / scrollHeight;
		console.log("currScrollHeight = " + currScrollHeight + " factor = " + factor);

		caretPos = Math.round(currScrollHeight * factor);
	}
	*/


	//$('#docPanel').jqxPanel('scrollTo', 0, caretPos);


	//activeHighlightRanges = [];
	//activeHighlightRanges.push([highlightRange.start, highlightRange.end]);





	/*
	if (highlightRange.end < highlightRange.start) {
		$('#docPanel').highlightWithinTextarea(onInput);
		$('.hwt-content mark').css("background-color","lightgray");
	}

	else {
		var highlightIndex = -1;
		for (var i=0; i<highlightRanges.length; i++) {
			if (highlightRanges[i][0] == highlightRange.start && highlightRanges[i][1] == highlightRange.end)
				highlightIndex = i+1
		}

		console.log("highlightRanges: " + JSON.stringify(highlightRanges));

		//$('.hwt-content mark:nth-of-type(' + highlightIndex + ')').css("background-color","red");
		//$('.hwt-content mark').css("background-color","red");
		//$('.hwt-content mark:nth-of-type(' + highlightIndex + ')').html('test');
		$('#docPanel').highlightWithinTextarea(onInput);

		$('.hwt-content mark:nth-of-type(' + highlightIndex + ')').css("background-color","yellow");
		if (highlightIndex > 1)
			$('.hwt-content mark:nth-of-type(-n+' + (highlightIndex-1) + ')').css("background-color","lightgray");
		$('.hwt-content mark:nth-of-type(n+' + (highlightIndex+1) + ')').css("background-color","lightgray");

		$('#docPanel').width($('#docDiv').width() * .95);
		scrollTextareaToPosition($('#docPanel'), highlightRange.end+10);
	}
	*/



	if (highlightRangeList == undefined) {
		//$('#docPanel').data('hwt').destroy();
		$('#docPanel').highlightWithinTextarea(onInput);
		$('.hwt-content mark').css("background-color","lightgray");
	}

	else {
		var highlightIndex = 0;
		var lastIndex = -1;
		var i=0;
		var lastEnd = highlightRangeList[0]["end"];

		console.log("highlightrangelist: " + JSON.stringify(highlightRangeList));
		console.log("highlightRanges: " + JSON.stringify(highlightRanges));
		console.log("highlightIndexes: " + JSON.stringify(highlightIndexes));
		console.log("hwt contents: " + $('#docPanel .hwt-content').length);
		

		//$('#docPanel').data('hwt').destroy();
		$('#docPanel').highlightWithinTextarea(onInput);
		//$('.hwt-content mark').css("background-color","yellow");
		
		if (highlightIndexes.length == 0) {
			$('.hwt-content mark').css("background-color","lightgray");	
		}
		
		$('.hwt-content mark').each(function (index) {
			if (index != highlightIndexes[highlightIndex]) {
				console.log("setting " + index + " to lightgray");
				$(this).css("background-color","lightgray");
			}
		});
		
		/*
		for (i=0; i<highlightRanges.length; i++) {
			var end = highlightRangeList[highlightIndex]["end"];
			
			if (i != highlightIndexes[highlightIndex]) {
				console.log('.hwt-content mark:nth-of-type(' + (i+1) + ') lightgray');
				$('.hwt-content mark:nth-of-type(' + (i+1) + ')').css("background-color","lightgray");
			}

	
			
			if (i == highlightIndexes[highlightIndex]) {

				//$('#docPanel').highlightWithinTextarea(onInput);
				//$('.hwt-content mark:nth-of-type(' + (i+1) + ')').css("background-color","yellow");
				//$('.hwt-content mark').css("background-color","yellow");
				//console.log('.hwt-content mark:nth-of-type(' + (i+1) + ') yellow');
				
				if (i > 0) {
					if (i-lastIndex == 2) {
						console.log('== 2: .hwt-content mark:nth-of-type(' + i + ') lightgray');
						$('#docPanel .hwt-content mark:nth-of-type(' + i + ')').css("background-color","lightgray");
					}
					else if (i-lastIndex > 2) {
						console.log('>2: .hwt-content mark:nth-of-type(n+' + (lastIndex+2) + '):nth-of-type(-n+' + i + ') lightgray');
						$('#docPanel .hwt-content mark:nth-of-type(n+' + (lastIndex+2) + '):nth-of-type(-n+' + i + ')').css("background-color","lightgray");
					}
				}
				

				lastIndex = i;
				highlightIndex++;

				if (end > lastEnd)
					lastEnd = end;

				if (highlightIndex == highlightRangeList.length)
					break;
			}
			
		}
		*/
		
		
		/*
		if (i < highlightRanges.length-1) {
			$('.hwt-content mark:nth-of-type(n+' + (i+2) + ')').css("background-color","lightgray");
			console.log('end: .hwt-content mark:nth-of-type(n+' + (i+2) + ') lightgray');
		}
		*/


		//$(window).resize();
		//$('#docPanel').resize();
		$('#docPanel').width($('#docDiv').width() * .9);
		$('#docPanel').width($('#docDiv').width() * .95);
		scrollTextareaToPosition($('#docPanel'), lastEnd+10);
		

	}

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
	}).done(function(data) {
		//console.log(data);

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
	}).done(function(data) {
		console.log(data);

		var result = JSON.parse(data);
		frameArray = result[0];
		var lastFrameAccessed = result[1];
		var crfData = result[2];
		var optionsStr = "<option selected disabled value=''>Select Instance</option>";
		for(var i = 0; i < frameArray.length; i++) {
		    if( frameArray[i]["validatedByUserName"] != "" ) {
		        optionsStr += "<option value='" + frameArray[i]["frameInstanceID"]
		            + "'>" + frameArray[i]["name"] + " Validated By "
		            + frameArray[i]["validatedByUserName"] + "</option>";
		    } else {
		        optionsStr += "<option value='" + frameArray[i]["frameInstanceID"] + "'>" + frameArray[i]["name"] + "</option>";
		    }
		}

		$("#crfSelect").find('option').remove().end().append($(optionsStr));
		document.getElementById('crfSelect').selectedIndex = 0;
		$('#crfSelect').select2();
		loadCRFData(crfData);

		if (colNames.length > 0) {
			loadEntity(colNames, colValues);
		}
		else {
			currFrameInstanceIndex = lastFrameAccessed["lastFrameInstanceIndex"];
			var frameInstanceID = lastFrameAccessed["lastFrameInstanceID"];

			console.log("frameInstanceID: " + frameInstanceID);

			//loadFrameInstance(frameInstanceID, true);
			//$('#crfSelect').prop('selectedIndex', currFrameInstanceIndex);
			//$('#crfSelect').prop('selectedIndex', currFrameInstanceIndex);
			$('#crfSelect').val(frameInstanceID).trigger("change");

		}


		$('#instanceButton').prop('disabled', false);
		closeDialogLoad();
	})
}

function frameInstanceSelected(frameInstanceID, clearDoc, frameInstanceIndex)
{
	$('#instanceText').val('');
	currFrameInstanceIndex = frameInstanceIndex;
	loadFrameInstance(frameInstanceID, clearDoc);
}

function frameInstanceSelectedText(frameInstanceName)
{
	console.log("frameInstanceSelected: " + frameInstanceName);

	for (var i=0; i<frameArray.length; i++) {
		//console.log(frameArray[i]["name"]);
		if (frameArray[i]["name"] == frameInstanceName) {
			$('#crfSelect option').eq(i+1).prop('selected', true);
			loadFrameInstance(frameArray[i]["frameInstanceID"], true);
			break;
		}
	}
}

function loadFrameInstance(frameInstanceID, clearDoc)
{
	console.log("loadFrameInstance: frameInstanceID=" + frameInstanceID);
    $("#validatedButtonDiv").hide();
	currFrameInstanceID = frameInstanceID;
	docSelectIndex = -1;

	openDialogLoad();
	var loadFrameInstanceAjax = jsRoutes.controllers.Application.loadFrameInstance(frameInstanceID);
	$.ajax({
		type: 'GET',
		url: loadFrameInstanceAjax.url,
	}).done(function(data) {
		console.log("loadFrameInstance return data:");
		console.log(data);

		var dataObj = JSON.parse(data);
        console.log("loadFrameInstance return data length=" + dataObj.length);
		//load highlight range map
		highlightRangeMap = dataObj[4];


		var crfData = dataObj[1];
		loadCRFData(crfData);


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
			//if( elementID=="92_1_0") {
			    console.log("viewer.js loadFrameInstance: for element");
			    console.log("elementIndex=" + elementIndex);
			    console.log("elementID: " + elementID);
			    console.log("docTableLocal=" + docTableLocal);
			    console.log("docNamespaceLocal=" + docNamespaceLocal);
			//}
			//console.log("elementID: " + elementID);
			//gridData[elementIndex]["start"] = start;
			//gridData[elementIndex]["end"] = end;
			//gridData[elementIndex]["elementValue"] = value;
			gridData[elementIndex]["docNamespace"] = docNamespaceLocal;
			//console.log("docNamespaceLocal=" + docNamespaceLocal);
			gridData[elementIndex]["docTable"] = docTableLocal;
			//console.log("docTableLocal=" + docTableLocal);
			gridData[elementIndex]["docID"] = docIDLocal;
			//gridData[elementIndex]["vScrollPos"] = vScrollPos;
			//gridData[elementIndex]["scrollHeight"] = scrollHeight;
			//gridData[elementIndex]["scrollWidth"] = scrollWidth;
			//console.log("annotFeatures=" + annotFeatures);
			gridData[elementIndex]["annotFeatures"] = annotFeatures;


			/*
			var element = $("#" + htmlID);
			console.log(element.prop('tagName') + "," + element.attr('type'));
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

        console.log("before dataAdapter.");
		//reload grid data into table
		gridSource.localData = gridData;
		dataAdapter = new $.jqx.dataAdapter(gridSource, {
		       loadComplete: function (data) { },
		       loadError: function (xhr, status, error) { }
		   });
	    $("#dataElementTable").jqxDataTable({ source: dataAdapter });

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

	    	console.log("htmlID: " + jq(htmlID));
	    	var element = $(jq(htmlID));

			console.log(element.prop('tagName') + "," + element.attr('type'));
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


	    //load doc text
	    /*
		origText = dataObj[1];
		var text = origText.split("\n").join("&nbsp;<br />");
		//var text = origText;
		//quill.setText(text);

		$('#docPanel').jqxPanel('clearcontent');
		$('#docPanel').jqxPanel('append', text);
		*/


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
		}

		//load document list

		var docList = dataObj[2];

		/*
		optionsStr = "<option selected disabled value=''>Select Report</option>";
		for (var i = 0; i < docList.length; i++) {
		    optionsStr += "<option value='{\"docNamespace\":\"" + docList[i]["docNamespace"] + "\",\"docTable\":\"" + docList[i]["docTable"]
		    + "\",\"docID\":" + docList[i]["docID"] + "}'>" + docList[i]["docName"] + "</option>";
		}

		$("#docSelect").find('option').remove().end().append($(optionsStr));
		document.getElementById('docSelect').selectedIndex = 0;
		*/


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

		getDocumentHistory();

		$("#docListBox").jqxListBox({source: docListBoxSource});
		$('#docListBox').jqxListBox('refresh');

		console.log("end of load frame docNamespace: " + docNamespace + ", docTable: " + docTable + ", docID: " + docID);

		closeDialogLoad();
	})

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

    	console.log("htmlID: " + jq(htmlID));
    	var element = $(jq(htmlID));

		console.log(element.prop('tagName') + "," + element.attr('type'));
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
	console.log("colNames: " + colNames + ", colValues: " + colValues);

	openDialogLoad();
	var getFrameInstanceIDAjax = jsRoutes.controllers.Application.getFrameInstanceID(colNames, colValues);
	$.ajax({
		type: 'GET',
		url: getFrameInstanceIDAjax.url,
	}).done(function(data) {
		console.log("getframeinstanceid: " + data);
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
	}).done(function(data) {
		console.log(data);
		if (data.length > 0) {
			loadCRFData(JSON.parse(data));
			loadFrameInstance(currFrameInstanceID, false);
			$("#validatedButtonDiv").show();
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
	}).done(function(data) {
		console.log(data);
		if (data.length > 0) {
			loadCRFData(JSON.parse(data));
			loadFrameInstance(currFrameInstanceID, false);
			$("#validatedButtonDiv").show();
		}

		//reload document annotations
		var getDocumentAnnotationsAjax = jsRoutes.controllers.Application.getDocumentAnnotations();
		$.ajax({
			type: 'POST',
			url: getDocumentAnnotationsAjax.url,
			data: {docNamespace:docNamespace,docTable:docTable,docID:docID}
		}).done(function(data) {
			console.log("clear element ranges: " + data);
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
	console.log("docFeatureValue:" + docFeatureValue + " select flag: " + selectFlag);

	if (!selectFlag && docFeatureValue == null) {
		index = id.lastIndexOf("_");
		id = id.substring(0, index);

		console.log("add element: " + id);
		openDialogLoad();
		var addElementAjax = jsRoutes.controllers.Application.addElement(id);
		$.ajax({
			type: 'GET',
			url: addElementAjax.url,
		}).done(function(data) {
			console.log(data);
			if (data.length > 0) {
				//loadCRFData(JSON.parse(data));
				loadFrameInstance(currFrameInstanceID, false);
			}

			closeDialogLoad();
		})
	}

	selectFlag = false;
}

function removeElement(id)
{
	if (!selectFlag && docFeatureValue == null) {
		var selection = $("#dataElementTable").jqxDataTable('getSelection');
		if (selection != undefined) {
			var elementID = selection[0]["elementID"];
			index = id.lastIndexOf("_");
			id = id.substring(0, index);

			console.log("remove element: " + id);
			openDialogLoad();
			var removeElementAjax = jsRoutes.controllers.Application.removeElement(elementID, id);
			$.ajax({
				type: 'GET',
				url: removeElementAjax.url,
			}).done(function(data) {
				console.log(data);
				if (data.length > 0) {
					//loadCRFData(JSON.parse(data));
					loadFrameInstance(currFrameInstanceID, false);
				}

				//reload document annotations
				var getDocumentAnnotationsAjax = jsRoutes.controllers.Application.getDocumentAnnotations();
				$.ajax({
					type: 'POST',
					url: getDocumentAnnotationsAjax.url,
					data: {docNamespace:docNamespace,docTable:docTable,docID:docID}
				}).done(function(data) {
					console.log("clear element ranges: " + data);
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
	}

	selectFlag = false;
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
		console.log(row["elementID"] + ", " + row["element"] + ", " + row["elementHTMLID"]);
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
	if (selection != undefined) {
		var elementID = selection[0]["elementID"];
		var elementHTMLID = selection[0]["elementHTMLID"];
		var elementType = selection[0]["elementType"];
		var start = selection[0]["start"];
		var elementIndex = elementIDMap[elementID];
		gridData2[elementIndex]["start"] = 0;
		gridData2[elementIndex]["end"] = -1;
		gridData2[elementIndex]["elementValue"] = "";

		console.log("clear: " +  elementID);
		console.log("clear: " +  elementHTMLID);
		console.log("clear: " +  elementType);

		openDialogLoad();
		var clearElementAjax = jsRoutes.controllers.Application.clearElement(elementID, elementHTMLID);
		$.ajax({
			type: 'GET',
			url: clearElementAjax.url,
		}).done(function(data) {
			var dataObj = JSON.parse(data);
			frameInstanceData = dataObj[0];
			highlightRangeMap = dataObj[1];

			//reload document annotations
			var getDocumentAnnotationsAjax = jsRoutes.controllers.Application.getDocumentAnnotations();
			$.ajax({
				type: 'POST',
				url: getDocumentAnnotationsAjax.url,
				data: {docNamespace:docNamespace,docTable:docTable,docID:docID}
			}).done(function(data) {
				console.log("clear element ranges: " + data);
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
	}).done(function(data) {
		var dataObj = JSON.parse(data);
		frameInstanceData = dataObj[0];
		highlightRangeMap = dataObj[1];

		//reload document annotations
		var getDocumentAnnotationsAjax = jsRoutes.controllers.Application.getDocumentAnnotations();
		$.ajax({
			type: 'POST',
			url: getDocumentAnnotationsAjax.url,
			data: {docNamespace:docNamespace,docTable:docTable,docID:docID}
		}).done(function(data) {
			console.log("clear element ranges: " + data);
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
		type: 'GET',
		url: clearAllAjax.url,
		//async: false
	}).done(function(data) {
		highlightRangeList = undefined;
		highlightRanges = undefined;
		highlightRangeMap = {};
	    highlightText();
	    frameInstanceData = [];
	    setHTMLElements();
	    //loadFrameInstanceNoRT();
		closeDialogLoad();
	})

	//highlightRange.start = 0;
	//highlightRange.end = -1;
	//highlightRanges = [];
	//highlightRangeList = undefined;
    //highlightText();
	//loadFrameInstance(currFrameInstanceID, true);
    //loadFrameInstanceNoRT();
}

function valueClick(event)
{
	console.log("value click: " + event.target.id + " metaKey: " + event.metaKey + " ctrlKey: " + event.ctrlKey);
	clickValueElement = event.target;

	var add = event.metaKey;
	if (!add)
		add = event.ctrlKey;

	valueClickCount2++;
	valueClickCallback(add);
}

function valueClickCallback(add)
{
	console.log("value click count = " + valueClickCount1 + ", " + valueClickCount2) ;

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
		console.log("token-based: " + start + ", " + end);
	}

	var annotFeatures = null;

	if (docFeatureValue != null) {
		start = -1;
		end = -1;
		clickValue = docFeatureValue;
		annotFeatures = '{"key":"' + docFeatureKey + '","value":"' + clickValue + '"}';
	}

	console.log("value click highlighted: " + start + "," + end + " selectFlag: " + selectFlag + " docFeatureValue: " + docFeatureValue);
    //console.log(precedingRange.toString());

	var htmlID = clickValueElement.id;
	console.log("value click: " + htmlID + ", " + clickValueElement.checked);

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
		    	features:annotFeatures,add:add}
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
					data: {docNamespace:docNamespace,docTable:docTable,docID:docID}
				}).done(function(data) {
					console.log("clear element ranges: " + data);
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

				console.log("elementIndex: " + elementIndex + " htmlID: " + htmlID);
				console.log("rowData: " + start + "," + end);


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
	console.log("mouse over! " + valueElement.id);

	highlightRangeList = highlightRangeMap[valueElement.id];
	var highlightMap;
	if (highlightRangeList != null) {
		rowStart = highlightRangeList[0]["start"];
		rowEnd = highlightRangeList[0]["end"];
		highlightMap = highlightRangeList[0];
	}
	else
		return;


	console.log("rowStart: " + rowStart);
	console.log("rowEnd: " + rowEnd);

	var elementIndex = elementHTMLIDMap[valueElement.parentElement.id];

	var selection = $("#dataElementTable").jqxDataTable('getSelection');
	if (selection == undefined || selection[0] == undefined || selection[0]["elementHTMLID"] != valueElement.parentElement.id)
		return;

	var rowData = gridData2[elementIndex];

	var rowStart = highlightMap["start"];
	var rowEnd = highlightMap["end"];

	//console.log("rowselect: " + docNamespace + "," + docTable + "," + docID);
    var docIndex = docIndexMap["{\"docNamespace\":\"" + highlightMap["docNamespace"] + "\",\"docTable\":\"" + highlightMap["docTable"] + "\",\"docID\":" + highlightMap["docID"] + "}"];
    if (highlightMap["docNamespace"] != docNamespace || highlightMap["docTable"] != docTable || highlightMap["docID"] != docID) {
	    getDocument("{\"docNamespace\":\"" + highlightMap["docNamespace"] + "\",\"docTable\":\"" + highlightMap["docTable"] + "\",\"docID\":" + highlightMap["docID"] + "}",
	    	docIndex, true, [rowStart], function (options) {
		    	console.log("highlightRangeList: " + JSON.stringify(highlightRangeList));
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
			    	console.log(rowData["annotFeatures"]);
			    	var annotFeatures = JSON.parse(rowData["annotFeatures"]);
			    	console.log(annotFeatures);
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
    	console.log(rowData["annotFeatures"]);
    	var annotFeatures = JSON.parse(rowData["annotFeatures"]);
    	console.log(annotFeatures);
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

    console.log("index: " + docIndex);
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
				docTable:docInfo["docTable"],docID:docInfo["docID"]}
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
			url: getDocHistAjax.url
		}).done(function(data) {
			console.log("doc hist: " + data);
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
			url: clearDocHistAjax.url
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
		$('#crfSelect').val(frameInstanceID).trigger("change");
		//loadFrameInstance(frameInstanceID, true)
	}
}

function nextInstance()
{
	if (currFrameInstanceIndex < frameArray.length) {
		currFrameInstanceIndex++;
		var frameInstanceID = frameArray[currFrameInstanceIndex-1]["frameInstanceID"];

		console.log("next currFrameInstanceIndex: " + currFrameInstanceIndex);
		console.log("next frameInstanceID: " + frameInstanceID);

		//$('#crfSelect').prop('selectedIndex', currFrameInstanceIndex);
		$('#crfSelect').val(frameInstanceID).trigger("change");
		//loadFrameInstance(frameInstanceID, true)
	}
}

function docFeatureClicked(value, id)
{
	console.log("feature clicked! " + value + " id: " + id);
	console.log($('#' + id).next().text());

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
}

function scrollTextareaToPosition($textarea, position) {
	var text = origText;
	var textBeforePosition = text.substr(0, position);
	var textAfterPosition = text.substr(position);
	$textarea.val(textBeforePosition);
    $textarea.scrollTop($textarea.prop("scrollHeight"));
	$textarea.val(text);

    /*
	var text = $textarea.val();
	var textBeforePosition = text.substr(0, position);
	$textarea.blur();
	$textarea.val(textBeforePosition);
	$textarea.focus();
	$textarea.val(text);
	//$textarea.setSelection(position, position); // assumes that you use jquery-fieldselection.js
	*/
}

function update(e) {
	if (e.metaKey || e.ctrlKey) {
		$(this).getSelection.removeRanges();
		return;
	}

	// here we fetch our text range object
	selectRange = $(this).getSelection();
	//console.log("selectRange: " + JSON.stringify(selectRange));

	//console.log("select range start: " + selectRange.start + " end: " + selectRange.end + " docFeatureRange start: " + docFeatureRange.start + " end: " + docFeatureRange.end + " selectFlag: " + selectFlag);

	//clear doc features if selection made
	if ((selectRange.start != docFeatureRange.start || selectRange.end != docFeatureRange.end) && selectRange.start < selectRange.end) {
		$("#docFeatures input:radio").attr("checked", false);
		$('[name=docfeature]').each(function() {
			var keyValue = JSON.parse($(this).val());
			$(this).next().html(keyValue["key"] + ": " + keyValue["value"]);
		});

		docFeatureValue = null;
		selectFlag = true;

		//docFeatureRange.start = selectRange.start;
		//docFeatureRange.end = selectRange.end;

		docFeatureRange.start = -1;
		docFeatureRange.end = -1;
	}
	else if (selectRange.start == selectRange.end)
		selectFlag = false;

	// just dump the values
	/*
	$('#output').html(
	  "hexdump:\n" + hexdump(this.value, range.start, (range.end != range.start) ? range.end - 1 : range.end) + "\n\n" +
		"id: " + this.id + "\n" +
		"start: " + range.start + "\n" +
		"length: " + range.length + "\n" +
		"end: " + range.end + "\n" +
		((typeof range['row'] != 'undefined') ? "caret row: " + range.row + "\n" : '') +
		((typeof range['col'] != 'undefined') ? "caret col: " + range.col + "\n" : '') +
		"selected text:\n<span class=\"txt\">" + (($('#ws').get(0).checked) ? range.text.whitespace() : range.text) + "</span>\n\n"
		);
*/
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

		highlightRanges.push(range);

		/*
		var inserted = false;
		for (var j=0; j<highlightRanges.length; j++) {
			var range2 = highlightRanges[j];
			if (range[0] < range2[0]) {
				highlightRanges.splice(j, 0, range);
				inserted = true;
				break;
			}
		}

		if (!inserted)
			highlightRanges.push(range);
			*/
	}
}

function getHighlightRanges()
{
	console.log("gethighlightranges: " + JSON.stringify(highlightRangeList));
	console.log("annot list: " + JSON.stringify(annotList));

	highlightRanges = [];
	highlightIndexes = [];

	if (highlightRangeList == undefined)
		return getHighlightRangesNoOverlap();


	var highlightIndex = 0;
	var highlightMap = highlightRangeList[0];
	var lastIndex = -1;
	var lastEnd = -1;
	var annotList2 = [];

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
					if (highlightRanges.length > 0)
						highlightRanges[highlightRanges.length-1][1] = annot["start"];
				}
				else
					range.push(lastEnd);
			}
			else
				range.push(annot["start"]);

			range.push(annot["end"]);
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
					highlightRanges[highlightRanges.length-1][1] = annot["start"];
					range.push(annot["start"]);
				}
				else
					range.push(lastEnd);
			}
			else
				range.push(annot["start"]);


			//range.push(annot["start"]);
			range.push(annot["end"]);

			lastHighlight = false;
		}

		highlightRanges.push(range);

		lastEnd = annot["end"];
	}

	console.log("annot list: " + JSON.stringify(annotList));
	console.log("annot list2: " + JSON.stringify(annotList2));
	console.log("highlightranges: " + JSON.stringify(highlightRanges));
	console.log("highlightindexes: " + JSON.stringify(highlightIndexes));
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
					data: {slotName:htmlID,start:start,end:end,docNamespace:docNamespace,docTable:docTable,docID:docID}
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
					console.log("elementID: " + gridData[i]["elementID"] + ", " + elementID);
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

				/*
				rowData['start'] = start;
				rowData['end'] = end;
				rowData['elementValue'] = value;
				rowData['valueHTMLID'] = htmlID;
				rowData['vScrollPos'] = Math.round($("#docPanel").jqxPanel('getVScrollPosition'));
				rowData['scrollHeight'] = Math.round($("#docPanel").jqxPanel('getScrollHeight'));
				rowData['scrollWidth'] = Math.round($("#docPanel").jqxPanel('getScrollWidth'));
				rowData['docNamespace'] = docNamespace;
				rowData['docTable'] = docTable;
				rowData['docID'] = docID;
				rowData['annotFeatures'] = annotFeatures;
				*/

				/*
				//set HTML elements
			    for (var i=0; i<frameInstanceData.length; i++) {
			    	var htmlID = frameInstanceData[i]["htmlID"];
			    	var value = frameInstanceData[i]["value"];

			    	console.log("htmlID: " + jq(htmlID));
			    	var element = $(jq(htmlID));

					console.log(element.prop('tagName') + "," + element.attr('type'));
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

			//console.log("frameInstanceData: " + JSON.stringify(frameInstanceData) + ", " + JSON.stringify(gridData));

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
			url: clearDocHistAjax.url
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

    	console.log("htmlID: " + jq(htmlID));
    	var element = $(jq(htmlID));

		console.log(element + "," + element.prop('tagName') + "," + element.attr('type'));
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
  console.log("docpanel select " + event + ", " + event.metaKey);
	if (event.metaKey || event.ctrlKey) {
		if (window.getSelection) {
		  if (window.getSelection().empty) {  // Chrome
		    window.getSelection().empty();
		  } else if (window.getSelection().removeAllRanges) {
			  console.log("here2: " + window.getSelection()); // Firefox
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

function frameInstanceValidated() {
    var frameInstanceValidatedAjax = jsRoutes.controllers.Application.frameInstanceValidated();
	$.ajax({
		type: 'GET',
		url: frameInstanceValidatedAjax.url
	}).done(function(data) {


		if( data.startsWith("Error:") ) {
            var message = data.replace("Error:", "");
            alertBoxShow(message);
        } else {
            var message = "This document has been validated successfully";
            $('#docListBox font').css("background-color", "#32CD32");
            successBoxShow(message);

            var result = JSON.parse(data);
		    frameArray = result[0];
		    var lastFrameAccessed = result[1];
		    var optionsStr = "<option selected disabled value=''>Select Instance</option>";
		    for(var i = 0; i < frameArray.length; i++) {
		        if( frameArray[i]["validatedByUserName"] != "" ) {
		            optionsStr += "<option value='" + frameArray[i]["frameInstanceID"]
		                + "'>" + frameArray[i]["name"] + " Validated By "
		                + frameArray[i]["validatedByUserName"] + "</option>";
		        } else {
		            optionsStr += "<option class='redColor' value='" + frameArray[i]["frameInstanceID"]
		            + "'>" + frameArray[i]["name"] + "</option>";
		        }
		    }
            if (colNames.length > 0) {
			    loadEntity(colNames, colValues);
		    }
		    else {
		        $("#crfSelect").find('option').remove().end().append($(optionsStr));
		        document.getElementById('crfSelect').selectedIndex = 0;
		        $('#crfSelect').select2();
		        currFrameInstanceIndex = lastFrameAccessed["lastFrameInstanceIndex"];
			    var frameInstanceID = lastFrameAccessed["lastFrameInstanceID"];
			    $('#crfSelect').val(frameInstanceID).trigger("change");
            }
        }
    }).fail(function(){
    });
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
