/*
 * ADDING DATE&TIME PICKERS HERE
 * 
 */

$(function() {
	$( '[name*=Date]' ).datepicker({ minDate: -20, maxDate: "+12M" });
	$( '[name*=Time]' ).AnyTime_picker({
		format: "%I:%i %p"
	});
});

//eventCount is used to create a unique id for each event
var eventCount = 0;

$(document).ready(function() {
    $('#submitBtn').click(function(){
        var eventName = $("input[name=eName]").val();      
        var startTime = $("input[name=esTime]").val();
        var endTime = $("input[name=eeTime]").val();
        var startDate = $("input[name=esDate]").val();
        var endDate = $("input[name=eeDate]").val();
        //var $recurrent = $('input[name=recurrent]:checked');
        var $recurrent = $('#recurrent').is(':checked');
        
/*        if ()
            recurrent = true;*/


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
        
        //event adding html code       
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

    //Set event recurrance html
/*    $recurrent.after(
        '<label class="checkbox inline">\
            <input type="checkbox" id="sundayCheckbox" value="option1"> Sun\
        </label>\
        <label class="checkbox inline">\
            <input type="checkbox" id="mondayCheckbox" value="option2"> Mon\
        </label>\
        <label class="checkbox inline">\
            <input type="checkbox" id="tuesdayCheckbox" value="option3"> Tue\
        </label>\
        <label class="checkbox inline">\
            <input type="checkbox" id="wednesdayCheckbox" value="option4"> Wed\
        </label>\
        <label class="checkbox inline">\
            <input type="checkbox" id="thrusdayCheckbox" value="option5"> Thu\
        </label>\
        <label class="checkbox inline">\
            <input type="checkbox" id="fridayCheckbox" value="option6"> Fri\
        </label>\
        <label class="checkbox inline">\
            <input type="checkbox" id="saturdayCheckbox" value="option7"> Sat\
        </label>\ '
    );*/

//Show recurrance checkkboxes
$('#recurrentOptions').hide();
$('#isRecurrent').click(function () {
    $("#recurrentOptions").toggle(this.checked);
});
    //Get event reccurance 

    //Event deletion
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
