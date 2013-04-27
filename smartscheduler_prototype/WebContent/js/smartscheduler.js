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

//will need this for generate to send current time
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
var currentEventType, eventPriority = "medium";//the ="" is needed until validation is implemented so you can add an event even without selecting priority

$(document).ready(function() {
    $('#btnAddEvent').click(function(){
        eventCount++; //so first event is counted as 1 and not 0.

        var eventName = $("input[name=" + currentEventType + "Name]").val();      
        var startTime = $("input[name=" + currentEventType + "StartTime]").val();
        var endTime = $("input[name=" + currentEventType + "EndTime]").val();
        var startDate = $("input[name=" + currentEventType + "StartDate]").val();
        var endDate = $("input[name=" + currentEventType + "EndDate]").val();
        var recType = $('.recType').val();
        var recInterval = $('.recInterval').val();

        var estimateType = $('#estimateType').val();
        //if there is no input for estimated hours, it is set to 0 default -anyway to do this by default using html value atribute?-
        if(estimateType === "minutes"){
            recHours = 0;
            recMinutes = $('input[name=estimate]').val();
        }
        if(estimateType === "hours"){
            recHours = $('input[name=estimate]').val();
            recMinutes = 0;
        }

        //This is more complicated than it needs to be but someone fix it.
        var recDays="none";
        var daysArray=[0,0,0,0,0,0,0];
        $(':checkbox:checked').each(function(i){
            switch($(this).val()){
                case "Sunday":
                    daysArray[0]=1;
                    break; 
                case "Monday":
                    daysArray[1]=1;
                    break;
                case "Tuesday":
                    daysArray[2]=1;
                    break;
                case "Wednesday":
                    daysArray[3]=1;
                    break;
                case "Thursday":
                    daysArray[4]=1;
                    break;
                case "Friday":
                    daysArray[5]=1;
                    break;
                case "Saturday":
                    daysArray[6]=1;
                    break;
            };
            recDays = "foobar"; //this does nothing and is never used
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
            minutes : recMinutes,
            priority : eventPriority
        };
        //Add to Global Array
        eventArray.push(newEvent);

        calendarType = "Events";
        //Accordion html code to add -Need see how i can use this as html so i can later repeat the accordion in the calendar view tabs
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
                                <dd>" + capFirst(currentEventType) + "</dd>"; 
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
                            if(currentEventType != "meeting"){
                                newEventHtml += "<dt>Priority: </dt>\
                                <dd>" + capFirst(eventPriority) + "</dd>\
                                <dt>Recurrence: </dt>\
                                <dd>" + capFirst(recType) + ", every " + recInterval + " days</dd>\
                                <dt>Days: </dt>\
                                <dd>" + recDays + "</dd>\
                                <dt>Estimate: </dt>\
                                <dd>" + recHours + " hours and " + recMinutes + "minutes</dd>";
                            }
                            newEventHtml += "</dl>\
                        <button class='btn btn-inverse' id='editBtn'>Edit</button>\
                        <button class='btn btn-inverse pull-right' id='deleteBtn'>Delete</button>\
                    </div>\
                </div>\
            </div>";

        //Add event to events list and calendar tabs -This needs modifications-
        
        $('#accordionEventsList').append(newEventHtml);
/*        calendarType = "Daily";
        $('#accordionDailyList').append(newEventHtml);
        calendarType = "Weekly";
        $('#accordionWeeklyList').append(newEventHtml);
        calendarType = "Monthly";
        $('#accordionMonthlyList').append(newEventHtml);*/
        clearForm('form'); //is this clearing just currentEventType's forms, or all forms?
    });

    //Cancel button
    $('#btnCancel').click(function(){
        clearForm($('#forms' + capFirst(currentEventType))); //is this clearing just currentEventType's forms, or all forms?
    });

    //Edit current event
    // $(document).on('click', '#editBtn', function(){
    //     var thisId = $(this).parents.eq(2).attr('id');
    //     $.each(eventArray, function(i){
    //         if(eventArray[i].id === thisId){
    //             var eventType = firstCap(eventArray[i].type;
    //             $('#btn' + eventType).click();
    //             $('#forms' + eventType).each(function(){
    //                 var $this = $(this);
    //                 switch(this.type)
    //                 {
    //                     case 'select-one':
    //                     case 'select-multiple':
    //                         $this.attr('selected', true);
    //                         break;
    //                     case 'text':
    //                         $this.val(value);
    //                         break;
    //                     case 'checkbox':
    //                         this.checked = true;
    //                         break;
    //                 }
    //             });
    //         }
    //     });
    // });

    //Delete current event from list and global array
    $(document).on('click', '#deleteBtn', function(){
        var thisId = $(this).parents().eq(2).attr('id'); //get this event id
        $.each(eventArray, function(i){
            if(eventArray[i].id === thisId) 
                eventArray.splice(i,1);
        });
        $(this).parents().eq(2).remove(); //delets from events list
    });

    //Delete all events button -fix why it only 1 per click instead of at same time-
    $(document).on('click', '#btnDeleteAll', function(){
        $.each(eventArray, function(i){
            $('#'+eventArray[i].id).remove();
            eventArray.splice(i,1);
        });
        eventCount = 0;
    });

    //Capitalize first letter
    function capFirst(string){
        return string.charAt(0).toUpperCase() + string.slice(1);
    };

// -------------------------CODE THAT IS IN SECTION NEEDS WORK OR NEEDS TO BE FIXED ----------------------------------
    //Clear all forms
    function clearForm(datForm){
        $(datForm).find(':input, :button').each(function(){
            switch(this.type) {
                case 'text':
                case 'select-one':
                    $(this).val('');
                    break;
                case 'checkbox':
                    this.checked = false;
                    break;
/*                case 'button':
                    $(this).removeClass('active'); //.off('click') & .blur() dont work; .toggle() and .click() only partially work
                    break;*/
            };
        });
        //eventPriority = "N/A";
    };

/*    $('#btnRecurrence').click(function(){
        $("#divRecurrence").toggle();
    });*/
    var recDiv = $('#divRecurrence').detach();

    $('.btnRecurrence').click(function(){
        //$(this).parent().siblings().toggle();  
        $(this).closest('.showDivRecurrence').append(recDiv);
  
    });

/*    //Make this code simpler, using toggle perhaps?
    $('.recType').change(function(){
        $('.checkboxesWeekly').show();
        //$('.checkboxes' + capFirst($(this).val())).show();
    });*/

    var priorityDiv = $('#divPriority').detach();

    $('.btnPriority').click(function(){ //Change this maybe to just having the hours input be there, and if there is anything except a empty or a 0 you show priority buttons
        $(this).closest('.showDivPriority').append(priorityDiv);
    });

/*    //Event Priority Button Functions    
    $('#lowPriority').click(function(){
        eventPriority = "low";
    });
    $('#medPriority').click(function(){
        eventPriority = "medium";
    });
    $('#highPriority').click(function(){
        eventPriority = "high";
    });*/

    //View Events Options
    $('#listViewBtn').click(function(){
        $('#listView').show();
        $('#calendarView').hide();
    });

    $('#calendarViewBtn').click(function(){
        $('#listView').hide();
        $('#calendarView').show();
    });

    //Recurrence Help Popover -Not working properly-
    $('#btnRecHelp').popover();

//--------------------- END OF SECTION IN NEED OF WORK --------------------
    //Show event forms and save type
    $('#btnClass, #btnDeadline, #btnMeeting, #btnFlexible').click(function(){
        $('#divClass, #divDeadline, #divMeeting, #divFlexible').hide();
        switch(this.name){
            case "class":
                $('#divClass').show();
                break;
            case "deadline":
                $('#divDeadline').show();
                break;
            case "meeting":
                $('#divMeeting').show();
                break;
            case "flexible":
                $('#divFlexible').show();
                break;
        };
        $('.form-actions').show();
        currentEventType = this.name;
    });

    //Help popover
    $help = $('#helpButton');
    $help.popover();  
    $help.click(function(){
        $help.data('popover').tip().find('.popover-title').empty().append("Help Popover");
        $help.data('popover').tip().find('.popover-content').empty().append("While this popover is active, a brief description of each event type will show after selecting an event type");
    });
    $('#btnClass').click(function(){
        $help.data('popover').tip().find('.popover-title').empty().append("Class type");
        $help.data('popover').tip().find('.popover-content').empty().append("Class events are those that usually repeat multiple times a week, for 1 or more weeks");
    });
    $('#btnDeadline').click(function(){
        $help.data('popover').tip().find('.popover-title').empty().append("Deadline type");
        $help.data('popover').tip().find('.popover-content').empty().append("Deadline events are those that require a set amount of time to complete by a due date");
    });
    $('#btnMeeting').click(function(){
        $help.data('popover').tip().find('.popover-title').empty().append("Meeting type");
        $help.data('popover').tip().find('.popover-content').empty().append("Meeting events are those that occur at a specific time and date, but can be recurrent");
    });
    $('#btnFlexible').click(function(){
        $help.data('popover').tip().find('.popover-title').empty().append("Flexible type");
        $help.data('popover').tip().find('.popover-content').empty().append("Flexible events are those that do not need to happen at a specific time, as long as they happen during that day or week");
    });

    //[{name: eventName, ...},{name:eventName2}]
    //alert("The event as an object: " + JSON.stringify(eventArray));
    //Generate string of schedule events
    $('#generateBtn').click(function(){
        //eventArray.push(eventCount);
        $('#eventArrayList').val(JSON.stringify(eventArray));
        $('generateForm').submit();
        eventCount=0;
    });
    //$('#eventArrayParagraph').html(window.location.search); //what was this for?

    //Test alert to show current events
    $('#btnShowEvents').click(function(){
        var printList = "";
        $.each(eventArray, function(i){
            printList += "{" + eventArray[i].id + ", " + eventArray[i].name + "} ";
        });
        alert("Events in the array by name and id are: " + printList);
    });

});