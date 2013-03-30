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
var eventList = {};
var currentEventType, eventPriority = "";

$(document).ready(function() {
    $('#addClassBtn, #addDeadlineBtn, #addMeetingBtn, #addFlexibleBtn').click(function(){
        var eventName = $("input[name=" + currentEventType + "Name]").val();      
        var startTime = $("input[name=" + currentEventType + "StartTime]").val();
        var endTime = $("input[name=" + currentEventType + "EndTime]").val();
        var startDate = $("input[name=" + currentEventType + "StartDate]").val();
        var endDate = $("input[name=" + currentEventType + "EndDate]").val();
        
        eventCount++;
        var eventId = eventCount;
        
        //Selects accordion label color
        var hColor = "gray";
        if (currentEventType === "class")
            hColor = "#46a546";
        else if (currentEventType === "deadline")
            hColor = "#ffc40d";
        else if (currentEventType=== "meeting")
            hColor = "#9d261d";
        else if (currentEventType === "flexible")
            hColor = "#049cdb";
        else
            hColor = "gray";

        //Recurrance
        var recurrent = $(this).$('#recurrent').is(':checked');

        //Add to global aray
        eventList[eventCount-1] = {
            name : eventName,
            type : currentEventType,
            sDate : startDate,
            eDate : endDate,
            sTime : startTime,
            eTime : endTime,
/*            recurrence : recType,
            interval : recInterval,
            days : recDays,  
            hours : recHours,*/
            priority : eventPriority
        };

        //Accordion html code to add
        var newEventHtml = "<div class='accordion-group' id='" + currentEventType + eventId + "'>\
                <div class='accordion-heading'>\
                    <a class='accordion-toggle' data-toggle='collapse' data-parent='#accordionEventsList' href='#collapse" + eventId + "' style='background-color:" + hColor + "'>\
                    <strong style='color:white'>" + eventName + "</strong>\
                    </a>\
                </div>\
                <div id='collapse" + eventId + "' class='accordion-body collapse'>\
                    <div class='accordion-inner'>\
                        <dl class='dl-horizontal'>\
                            <dt>Name:</dt>\
                                <dd>" + eventName + "</dd>";
                            //meeting type does not have a start date.
                            if(currentEventType != "meeting"){
                                newEventHtml += "<dt>Start Date:</dt>\
                                <dd>" + startDate + "</dd>";
                            }
                            //variations on End Date for different event types
                            if (currentEventType === "class" || "flexible"){
                                newEventHtml += "<dt>End Date:</dt>"; 
                            }
                            if (currentEventType === "deadline"){
                                newEventHtml += "<dt>Due Date:</dt>";
                            }
                            if (currentEventType === "meeting"){
                                newEventHtml += "<dt>Date:</dt>";
                            }
                            newEventHtml += "<dd>" + endDate + "</dd>";

                            if(currentEventType === "deadline"){
                                newEventHtml += "<dt>Due by:</dt>\
                                <dd>" + endTime + "</dd>";
                            }
                            else{
                                newEventHtml += "<dt>Start Time:</dt>\
                                <dd>" + startTime + "</dd>\
                                <dt>End Time:</dt>\
                                <dd>" + endTime + "</dd>";
                            }
/*                            newEventHtml += "<dt>Priority</dt>\
                            <dd>" + eventPriority + "</dd>";*/
                            newEventHtml += "</dl>\
                        <button class='btn btn-inverse' id='deleteBtn'>Delete event</button>\
                    </div>\
                </div>\
            </div>";
        //Add event to list on page
        $('#rightCol #accordionEventsList').append(newEventHtml);
    });
    //Delete current event from list and global array
    $(document).on('click', '#deleteBtn', function() {
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
    
    //Event Type Button Functions
    $('#classButton').click(function(){
    	currentEventType = "class";
    });
    $('#deadlineButton').click(function(){
    	currentEventType = "deadline";
    });
    $('#meetingButton').click(function(){
    	currentEventType = "meeting";
    });
    $('#flexibleButton').click(function(){
    	currentEventType = "flexible";
    });

    //Event Priority Button Functions
    $('#deadlineButton').click(function(){
        eventPriority = "low";
    });
    $('#meetingButton').click(function(){
        eventPriority = "medium";
    });
    $('#flexibleButton').click(function(){
        eventPriority = "high";
    });
});

