/*
 * ADDING DATE&TIME PICKERS HERE
 * 
 */

$(function() {
	$( "[id*='Date']" ).datepicker({ minDate: -20, maxDate: "+12M" });
	$( "[id*='Time']" ).AnyTime_picker({
		format: "%I:%i %p"
	});
});

//eventCount is used to create a unique id for each event
var eventCount = 0;

$(document).ready(function() {
    $('#submitBtn').click(function(){
        var eventName = $('#eName').val(); //alternative is .attr('value')        
        var startTime = $('#esTime').val();
        var endTime = $('#eeTime').val();
        var startDate = $('#esDate').val();
        var endDate = $('#eeDate').val();
       
        //Works like this: you add an event and it gets added as an extra collapsable element in the accordion.

        eventCount++;
        var eventId = eventCount;

        //This is used to help select the color for the event's accordion heading, based on its event type.
        var eType = "class"; //add propper code later
        var hColor;

        if (eType === "class")
            hColor = "#46a546";
        else if (eType === "exam")
            hColor = "#ffc40d";
        else if (eType === "project")
            hColor = "#9d261d";
        else if (eType ==="extracurricular")
            hColor = "#049cdb";
        else
            hColor = "gray";
       
        $('#rightCol #accordionEventsList').append(
             "\
                 <div class='accordion-group' id='" + eType + eventId + "'>\
                     <div class='accordion-heading'>\
                         <a class='accordion-toggle' data-toggle='collapse' data-parent='#accordionEventsList' href='#collapse" + eventId + "' style='background-color:" + hColor + "'>\
                         <strong style='color:white'>" + eventName + "</strong>\
                         </a>\
                     </div>\
                     <div id='collapse" + eventId + "' class='accordion-body collapse'>\
                         <div class='accordion-inner'>\
                             <dl class='dl-horizontal'>\
                                 <dt>Name:</dt>\
                                 <dd>" + eventName + "</dd>\
                                 <dt>Start Time:</dt>\
                                 <dd>" + startTime + "</dd>\
                                 <dt>End Time:</dt>\
                                 <dd>" + endTime + "</dd>\
                                 <dt>Start Date:</dt>\
                                 <dd>" + startDate + "</dd>\
                                 <dt>End Date:</dt>\
                                 <dd>" + endDate + "</dd>\
                             </dl>\
                             <button class='btn btn-inverse' id='deleteBtn'>Delete event</button>\
                         </div>\
                     </div>\
                 </div>\
             "
         );
    });


    $(document).on('click', '#deleteBtn', function() {
        $(this).parents().eq(2).remove();
    });
});

/* ALTERNATE DELETE   
    $('#deleteBtn').live('click', function(){
        $(this).parents().eq(2).remove();
    });*/

/*
    //event handler for submit button
    $("#signInBtn").click(function () {
        //collect userName and password entered by users
        var userName = $("#username").val();
        var password = $("#password").val();

        //call the authenticate function
        //authenticate(userName, password);
        alert("OMG " + userName + ", your password is " + password);
    });

    $("a").click(function(event){
        event.preventDefault();
        $(this).hide("slow");
    });


    //authenticate function to make ajax call
    function authenticate(userName, password) {
        $.ajax
        ({
            type: "POST",
            //the url where you want to sent the userName and password to
            //url: "http://your-url.com/secure/authenticate.php",
            dataType: 'json',
            async: false,
            //json object to sent to the authentication url
            data: '{"userName": "' + userName + '", "password" : "' + password + '"}',
            success: function () {
                //do any process for successful authentication here
                alert("Welcome " + userName + "!");
            }
            alert("Server is down :(");
        });
    }
    var signIn = function(){
    alert("Test alert; please ignore");
}
*/
