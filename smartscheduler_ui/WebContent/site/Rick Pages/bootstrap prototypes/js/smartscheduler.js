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

/*          <script language="javascript"> 
            <!-- 
            today = new Date(); 
            document.write("<BR>The time now is: ", today.getHours(),":",today.getMinutes()); 
            document.write("<BR>The date is: ", today.getDate(),"/",today.getMonth()+1,"/",today.getYear());
            //--> 
          </script> */

//eventCount is used to create a unique id for each event
var eventCount = 0; //need to modify this since the number will never decrease in the current code.
var eventArray = [];
var currentEventType, eventPriority;

$(document).ready(function() {
    $('#addClassBtn, #addDeadlineBtn, #addMeetingBtn, #addFlexibleBtn').click(function(){
        eventCount++; //so first event is counted as 1 and not 0.

        var eventName = $("input[name=" + currentEventType + "Name]").val();      
        var startTime = $("input[name=" + currentEventType + "StartTime]").val();
        var endTime = $("input[name=" + currentEventType + "EndTime]").val();
        var startDate = $("input[name=" + currentEventType + "StartDate]").val();
        var endDate = $("input[name=" + currentEventType + "EndDate]").val();
        var recType = $('#recType').val();
        var recInterval = $('#recInterval').val();
        var recHours = $('#estHoursClass').val();
        
        //This is more complicated than it needs to be but someone fix it.
        var recDays="none";
        var daysArray=[0,0,0,0,0,0,0];
        $(':checkbox:checked').each(function(i){
            switch($(this).val()){
                case "Sunday":
                    daysArray[0]=1;
                    recDays="yes";
                    break;
                case "Monday":
                    daysArray[1]=1;
                    recDays="yes";
                    break;
                case "Tuesday":
                    daysArray[2]=1;
                    recDays="yes";
                    break;
                case "Wednesday":
                    daysArray[3]=1;
                    recDays="yes";
                    break;
                case "Thursday":
                    daysArray[4]=1;
                    recDays="yes";
                    break;
                case "Friday":
                    daysArray[5]=1;
                    recDays="yes";
                    break;
                case "Saturday":
                    daysArray[6]=1;
                    recDays="yes";
                    break;
                default:
                    recDays="nayda"; //never happens
            };
        });
        //if there is no days checked keeps value as "none"
        if(recDays != "none")
            recDays = daysArray.toString().replace(/,/g, "");

        var hColor, calendarType;
        var eventId = currentEventType + eventCount.toString();
        
        //Selects accordion label color
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
        };
        //Current Event Object
        var newEvent = {
            id : eventId,
            name : eventName,
            type : currentEventType,
            sDate : startDate,
            eDate : endDate,
            sTime : startTime,
            eTime : endTime,
            recurrence : recType,
            interval : recInterval,
            days : recDays,  
            hours : recHours,
            priority : eventPriority
        };
        //Add to Global Array
        eventArray.push(newEvent);

        //[{name: eventName, ...},{name:eventName2}]
        //alert("The event as an object: " + JSON.stringify(eventArray));

        //Accordion html code to add
        var newEventHtml = "\
            <div class='accordion-group' id='" + eventId + "'>\
                <div class='accordion-heading'>\
                    <a class='accordion-toggle' data-toggle='collapse' data-parent='#accordion"+calendarType+"'List' href='#collapse" + eventId + "' style='background-color:" + hColor + "'>\
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
                            <dd>" + recInterval + "</dd>\
                            <dt>Days: </dt>\
                            <dd>" + recDays + "</dd>";

                            newEventHtml += "</dl>\
                        <button class='btn btn-inverse' id='editBtn'>Edit</button>\
                        <button class='btn btn-inverse pull-right' id='deleteBtn'>Delete</button>\
                    </div>\
                </div>\
            </div>";

        //Add event to events list and calendar tabs
        calendarType = "Events";
        $('#accordionEventsList').append(newEventHtml);
        calendarType = "Daily";
        $('#accordionDailyList').append(newEventHtml);
        calendarType = "Weekly";
        $('#accordionWeeklyList').append(newEventHtml);
        calendarType = "Monthly";
        $('#accordionMonthlyList').append(newEventHtml);
        clearForm('form');
    });

    //Edit current event
/*    $(document).on('click', '#editBtn', function() {
    });*/

    //Delete current event from list and global array
    $(document).on('click', '#deleteBtn', function() {
        var thisId = $(this).parents().eq(2).attr('id'); //get this event id
        $.each(eventArray, function(i){
            if(eventArray[i].id === thisId) eventArray.splice(i,1);
        });
        $(this).parents().eq(2).remove(); //delets from events list
    });

    //Delete all events button -fix why it only 1 per click instead of at same time)
    $(document).on('click', '#btnDeleteAll', function(){
        $.each(eventArray, function(i){
            $('#'+eventArray[i].id).remove();
            eventArray.splice(i,1);
        });
        eventCount = 0;
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

    $('#btnRecurring').click(function () {
        $(".recurringDiv").toggle();
    });

    $('#listViewBtn').click(function(){
        $('#listView').show();
        $('#calendarView').hide();
    });

    $('#calendarViewBtn').click(function(){
        $('#listView').hide();
        $('#calendarView').show();
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
    $('#btnRecHelp').popover();

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
    $('#lowPriority').click(function(){
        eventPriority = "low";
    });
    $('#medPriority').click(function(){
        eventPriority = "medium";
    });
    $('#highPriority').click(function(){
        eventPriority = "high";
    });

    //Generate string of schedule events
    $('#generateBtn').click(function(){
        $('#eventArrayList').val(JSON.stringify(eventArray));
        $('generateForm').submit();
    });
    $('#eventArrayParagraph').html(window.location.search);

    //Test alert to show current events
    $('#btnShowEvents').click(function(){
        var printList = "";
        $.each(eventArray, function(i){
            printList += "{" + eventArray[i].id + ", " + eventArray[i].name + "} ";
        });
        alert("Events in the array by name and id are: " + printList);
    });

});