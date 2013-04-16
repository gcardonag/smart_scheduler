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
var eventArray = [];
var currentEventType, eventPriority;

$(document).ready(function() {
    $('#addClassBtn, #addDeadlineBtn, #addMeetingBtn, #addFlexibleBtn').click(function(){
        var eventName = $("input[name=" + currentEventType + "Name]").val();      
        var startTime = $("input[name=" + currentEventType + "StartTime]").val();
        var endTime = $("input[name=" + currentEventType + "EndTime]").val();
        var startDate = $("input[name=" + currentEventType + "StartDate]").val();
        var endDate = $("input[name=" + currentEventType + "EndDate]").val();
        var recType = $('#recType').val();
        var recInterval = $('#recInterval').val();

        eventCount++; //so first event is counted as 1 and not 0.
        var eventId = currentEventType + eventCount.toString();
        
        //Selects accordion label color
        var hColor;

        switch(currentEventType) {
          case 'class':
            hColor = "#46a546";
            break;
          case 'deadline':
            hColor = "#ffc40d";
            break;
          case 'meeting':
            hColor = "#9d261d";
            break;
          default:
            hColor = '#049cdb';
        }

        //Recurrance
/*        var recurrent = $(this).$('#recurrent').is(':checked');
        if(recurrent === true)*/

        //Add to global array  NEED TO CHANGE TO LIST
        var newEvent = {
            name : eventName,
            type : currentEventType,
            sDate : startDate,
            eDate : endDate,
            sTime : startTime,
            eTime : endTime,
            recurrence : recType,
            interval : recInterval,
/*          days : recDays,  
            hours : recHours,*/
            priority : eventPriority
        };

        eventArray.push(newEvent);

        //[{name: eventName, ...},{name:eventName2}]
        //alert("The event as an object: " + JSON.stringify(eventArray));

        //Accordion html code to add
        var newEventHtml = "<div class='accordion-group' id='" + eventId + "'>\
                <div class='accordion-heading'>\
                    <a class='accordion-toggle' data-toggle='collapse' data-parent='#accordionEventsList' href='#collapse" + eventId + "' style='background-color:" + hColor + "'>\
                    <strong style='color:white'>" + eventName + "</strong>\
                    <strong class='pull-right' style='color:white'>" + currentEventType + "</strong>\
                    </a>\
                </div>\
                <div id='collapse" + eventId + "' class='accordion-body collapse'>\
                    <div class='accordion-inner'>\
                        <dl class='dl-horizontal'>\
                            <dt>Name:</dt>\
                                <dd>" + eventName + "</dd>\
                            <dt>Type:</dt>\
                                <dd>" + currentEventType + "</dd>"; 
                            //meeting type does not have a start date.
                            if(currentEventType != 'meeting'){
                                newEventHtml += "<dt>Start Date:</dt>\
                                <dd>" + startDate + "</dd>";
                            }
                            //variations on End Date for different event types
                            switch (currentEventType){
                                case 'deadline':
                                  newEventHtml += "<dt>Due Date:</dt>";
                                  break;
                                case 'meeting':
                                  newEventHtml += "<dt>Date:</dt>";
                                  break;
                                default:
                                  newEventHtml += "<dt>End Date:</dt>"; 
                            }

                            newEventHtml += "<dd>" + endDate + "</dd>";

                            switch (currentEventType){
                              case 'deadline':
                                newEventHtml += "<dt>Due by:</dt>\
                                <dd>" + endTime + "</dd>";
                              break;
                              default:
                                newEventHtml += "<dt>Start Time:</dt>\
                                <dd>" + startTime + "</dd>\
                                <dt>End Time:</dt>\
                                <dd>" + endTime + "</dd>";
                            }

                            newEventHtml += "<dt>Priority: </dt>\
                            <dd>" + eventPriority + "</dd>\
                            <dt>Recurrence: </dt>\
                            <dd>" + recType + "</dd>\
                            <dt>Interval: </dt>\
                            <dd>" + recInterval + "</dd>";

                            newEventHtml += "</dl>\
                        <button class='btn btn-inverse' id='editBtn'>Edit</button>\
                        <button class='btn btn-inverse pull-right' id='deleteBtn'>Delete</button>\
                    </div>\
                </div>\
            </div>";
        //Add event to list on page
        $('#rightCol #accordionEventsList').append(newEventHtml);
        clearForm('form');
    });

    //Delete current event from list and global array
    $(document).on('click', '#deleteBtn', function() {
        $(this).parents().eq(2).remove();
    });
    //Clear all forms
    function clearForm(datForm){
        $(datForm).find(':input').each(function(){
            switch(this.type) {
                case 'text':
                case 'select-one':
                    $(this).val('');
                    break;
                case 'checkbox':
                    this.checked = false;
            }
        });
    }
    //Edit current event
/*    $(document).on('click', '#editBtn', function() {
    });*/

    //Delete all events button
/*    $('removeAll').live('click', functon(){
        for (var i = eventCount; i > 0; i--)
            "$('#delete"+ eventType + eventCount +"')"
            //add code to erase events from list object
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

    //Help popover
    $help = $('#helpButton');
    $help.popover();  
    $help.click(function(){
        $help.data('popover').tip().find('.popover-title').empty().append("Help Popover");
        $help.data('popover').tip().find('.popover-content').empty().append("While this popover is active, a brief description of each event type will show after selecting an event type");
    });
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
    
    //Recurrence Help Popover
    $('#recHelpBtn').popover();


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
    var lowPriority = currentEventType + "LowPrio";
    //var medPriority = "#" + currentEventType + "MedPrio";
    var highPriority = "#" + currentEventType + "HiPrio";
    
    //This works.
    $('#classLowPrio').click(function(){
        eventPriority = "low";
        //alert(eventPriority + " priority was selected for this " + currentEventType + " class");
    });

    //But these variations dont.
/*    $('#'+lowPriority).click(function(){
        eventPriority = "low";
        alert(eventPriority + " priority was selected for this " + currentEventType + " class");
    });*/
/*    $("#" + currentEventType + "MedPrio").click(function(){
        eventPriority = "medium";
        alert(eventPriority + " priority was selected for this " + currentEventType + " class");
    });
    $(highPriority).click(function(){
        eventPriority = "high";
        alert(eventPriority + " priority was selected for this " + currentEventType + " class");
    });*/

    //Generate string of schedule events
    $('#generateBtn').click(function(){
        $('#eventArrayList').val(JSON.stringify(eventArray));
        $('generateForm').submit();
    });
    $('#eventArrayParagraph').html(window.location.search);
});