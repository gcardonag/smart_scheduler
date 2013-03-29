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
    $('#addClassBtn, #addDeadlineBtn, #addMeetingBtn, #addFlexibleBtn').click(function(){
        var eventName = $("input[name=eName]").val();      
        var startTime = $("input[name=eStartTime]").val();
        var endTime = $("input[name=eEndTime]").val();
        var startDate = $("input[name=eStartDate]").val();
        var endDate = $("input[name=eEndDate]").val();
        //var $recurrent = $('#recurrent').is(':checked');

        var hColor;
        eventCount++;
        var eventId = eventCount;
        var eType = $(this).attr("value");
        

        //Selects accordion label color
        if (eType === "classEvent")
            hColor = "#46a546";
        else if (eType === "deadlineEvent")
            hColor = "#ffc40d";
        else if (eType === "meetingEvent")
            hColor = "#9d261d";
        else if (eType ==="flexibleEvent")
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

    //Delete all events 
/*    $('removeAll').live('click', functon(){
        for (var i = eventCount; i > 0; i--)
            "$('#delete"+ eventType + eventCount +"')"
    });*/

    $('#classButton').click(function() {
        $('#classForms').show();//Form shows on button click 
        $('#deadlineForms').hide();
        $('#meetingForms').hide();
        $('#flexibleForms').hide();
    });

    $('#deadlineButton').click(function() {
        $('#deadlineForms').show();//Form shows on button click  
        $('#classForms').hide();
        $('#meetingForms').hide();
        $('#flexibleForms').hide();
    });

    $('#meetingButton').click(function() {
        $('#meetingForms').show();//Form shows on button click
        $('#classForms').hide();
        $('#deadlineForms').hide();
        $('#flexibleForms').hide();
    });

    $('#flexibleButton').click(function() {
        $('#flexibleForms').show();//Form shows on button click
        $('#classForms').hide();
        $('#meetingForms').hide();
        $('#deadlineForms').hide();
    });

    $('.recurringCheckbox').click(function () {
        $(".recurringDiv").toggle(this.checked);
    });

    //Help popovers
    $help = $('#helpButton');
    $help.popover();  
/*    $help.click(function(){
        $help.data('popover').tip().find('.popover-title').empty().append("Help information");
        $help.data('popover').tip().find('.popover-content').empty().append("A brief description of each event type will show here while the help button is active");
    });*/
    $('#classButton').click(function(){
        $help.data('popover').tip().find('.popover-title').empty().append("Class type");
        $help.data('popover').tip().find('.popover-content').empty().append("Class events are those that usually repeat multiple times a week, for 1 or more weeks");
    });
    $('#deadlineButton').click(function(){
        $help.data('popover').tip().find('.popover-title').empty().append("Deadline type");
        $help.data('popover').tip().find('.popover-content').empty().append("Deadline events are those that require a set amount of time to complete by a due date");
    });
    $('#meetingButton').click(function(){
        $help.data('popover').tip().find('.popover-title').empty().append("Meeting type");
        $help.data('popover').tip().find('.popover-content').empty().append("Meeting events are those that occur at a specific time and date, but can be recurrent");
    });
    $('#flexibleButton').click(function(){
        $help.data('popover').tip().find('.popover-title').empty().append("Flexible type");
        $help.data('popover').tip().find('.popover-content').empty().append("Flexible events are those that do not need to happen at a specific time, as long as they happen during that day or week");
    });

});

