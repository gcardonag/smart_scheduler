/*
 * ADDING DATE&TIME PICKERS HERE
 * 
 */

$(function() {
	$( "[name*='Date']" ).datepicker({ minDate: -20, maxDate: "+12M" });
	$( "[id*='Time']" ).AnyTime_picker({
		format: "%I:%i %p"
	});
});

//eventCount is used to create a unique id for each event
var eventCount = 0; //need to modify this since the number will never decrease in the current code.
var eventList = new Array();

$(document).ready(function() {
    $('#submitBtn').click(function(){
        var eventName = $("input[name=eName]").val();      
        var startTime = $("input[name=eStartTime]").val();
        var endTime = $("input[name=eEndTime]").val();
        var startDate = $("input[name=eStartDate]").val();
        var endDate = $("input[name=eEndDate]").val();
        //var $recurrent = $('#recurrent').is(':checked');

        eventCount++;
        var eventId = eventCount;
        //This is used to help select the color for the event's accordion heading, based on its event type.
        var eType = $(''); //add propper code later
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
       
        //Add to global aray
        eventList[eventCount-1] = {
            name : eventName,
            sTime : startTime,
            eTime : endTime,
            sTime : startDate,
            eDate : endDate,
            type : eType
        };

       //Add event to list on page
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
    //Delete current event from list and global array
    $('#deleteBtn').live('click', function(){
        $(this).parents().eq(2).remove();
    });

    $('#Exam').hide(); //Initially form wil be hidden.
    $('#ExamButton').click(function() {
        $('#Exam').show();//Form shows on button click  
        $('#Class').hide();
        $('#Project').hide();
        $('#Custom').hide();
    });

    $('#Class').hide(); //Initially form wil be hidden.
    $('#ClassButton').click(function() {
        $('#Class').show();//Form shows on button click 
        $('#Exam').hide();
        $('#Project').hide();
        $('#Custom').hide();
    });

    $('#Project').hide(); //Initially form wil be hidden.
    $('#ProjectButton').click(function() {
        $('#Project').show();//Form shows on button click
        $('#Class').hide();
        $('#Exam').hide();
        $('#Custom').hide();
    });

    $('#Custom').hide(); //Initially form wil be hidden.
    $('#CustomButton').click(function() {
        $('#Custom').show();//Form shows on button click
        $('#Class').hide();
        $('#Project').hide();
        $('#Exam').hide();
    });

    $('.recurringCheckbox').click(function () {
        $(".recurringDiv").toggle(this.checked);
    });
});

