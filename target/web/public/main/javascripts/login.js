var username;
var password;
var text = "";

function toLogIn() {
    //event.preventDefault();

    // openDialogLoad();
    //console.log("button works");
    var auth = jsRoutes.controllers.Application.authenticate();

    var un = $("input#username").val();
    var pw = $("input#password").val();
    //console.log(auth);
    //console.log(auth.url);

    var info = {
      username: un,
      password: pw,
    };


    $.ajax({
        type: 'POST',
        url: auth.url,
        data:info,
        success: function() {
          // console.log(info);
        },
    })
    //return false;

}



/*
$(function(){
    $('#the-node').contextMenu({
        selector: 'li',
        callback: function(key, options) {
            var m = "clicked: " + key + " on " + $(this).text();
            window.console && console.log(m) || alert(m);
        },
        items: {
            "edit": {name: "Edit", icon: "edit"},
            "cut": {name: "Cut", icon: "cut"},
            "copy": {name: "Copy", icon: "copy"},
            "paste": {name: "Paste", icon: "paste"},
            "delete": {name: "Delete", icon: "delete"},
            "sep1": "---------",
            "quit": {name: "Quit", icon: function($element, key, item){ return 'context-menu-icon context-menu-icon-quit'; }}
        }
    });
});*/



/*$(function() {
  $("#register").click(function() {
    console.log("reg button works");
  });
});*/

/*$(function() {
                $("form").on("submit", function(e) {
                  var auth = jsRoutes.controllers.Application.authenticate();

                  var un = $("input#username").val();
                  var pw = $("input#password").val();
                  //console.log(auth);
                  //console.log(auth.url);
                  console.log("username is: "+ un);
                  console.log("password is: " + pw);

                    $.ajax({
                        url: auth.url,
                        type: 'POST',
                        data: {username:un, password:pw},
                        beforeSend: function() {
                        },
                        success: function(data) {
                        }
                    });
                });
            });*/


/*$(function() {
    $(".button").click(function() {

      console.log("new function works");

      var name = $("input#username").val();
      var auth = jsRoutes.controllers.Application.authenticate();
      //console.log(auth);
      console.log(auth.url);
      console.log("username is: "+ name);
      // validate and process form here
      var dataString = 'username=' + name;
      $.ajax({
        type: 'POST',
        url: auth.url,
        success: function() {
        }




      })

    });
  });
*/
