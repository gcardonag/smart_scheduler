//Add condition so that clicking text elements do not trigger event as well.
$(document).ready(function(){
	//Ensures that input fields do not trigger sliding.
	$(":input").click(function(e){
		e.stopPropagation();
	});
	
	//Trigger slide up/down for clicking on each event type tab.
	$("[class^='tab-']").click(function() {
		if($(this).children(".sub").is(":hidden")) {
			$(this).children(".sub").slideDown("slow");
		}
		else {
			$(this).children(".sub").slideUp("slow");
		}
	});
	
	$("#eventBox_container").on("click", "[class$='eventBox']", function() {
		if($(this).children(".eventBox_sub").is(":hidden")) {
			$(this).children(".eventBox_sub").slideDown("slow");
		}
		else {
			$(this).children(".eventBox_sub").slideUp("slow");
		}
	});
	
	$("#eventBox_container").on("click", ":input", function(e) {
		e.stopPropagation();
	});
});

function toggleTime(checkbox) {
	
	if(checkbox.checked) {
		document.getElementById('extra_time_container').style.display = "block";
		document.getElementById('extra_time_required_container').style.display = "none";
	}
	else {
		document.getElementById('extra_time_container').style.display = "none";
		document.getElementById('extra_time_required_container').style.display = "block";
	}
}

function togglerecurrence(checkbox, eventType) {
	
	var checkboxes = document.getElementById(eventType + '_recurrence');
	
	if(checkbox.checked)
	{
		checkboxes.style.display = "block";
	}
	else
	{
		checkboxes.style.display = "none";
	}
}

function toggleExtraRecurrence(checkbox) {
	if(checkbox.checked) {
		document.getElementById('extra_recurrence').style.display = "block";
	}
	else {
		document.getElementById('extra_recurrence').style.display = "none";
	}
}

function toggleEventRecurrence(checkbox, eventCounter) {
	if(checkbox.checked) {
		document.getElementById('eventBox_' + eventCounter + "_recurrence").style.display = "block";
	}
	else {
		document.getElementById('eventBox_' + eventCounter + "_recurrence").style.display = "none";
	}
}

function toggleEventTime(checkbox, eventCounter) {
	if(checkbox.checked) {
		document.getElementById('event_' + eventCounter + '_time_container').style.display = "block";
		document.getElementById('event_' + eventCounter + '_time_required_container').style.display = "none";
	}
	else {
		document.getElementById('event_' + eventCounter + '_time_container').style.display = "none";
		document.getElementById('event_' + eventCounter + '_time_required_container').style.display = "block";			
	}
}

function addExtra() {
	var error = false;
	
	//Generate the innerHTML for the new div based on the event's values.
	var newDivHTML = '<div class="eventBox_main">';
	
	//Get all event variable values
	if(document.getElementById('extra_event_name') != null) { 
		var eventName = document.getElementById('extra_event_name').value;

		if(eventName == "") {
			error = true;
			//TODO: Make important empty fields turn red.
		}
		else {
			newDivHTML = newDivHTML + eventName + '</div>' + '<div class="eventBox_sub"><form name="eventBoxForm_' + eventCounter + '" method="post">' + 
			'<input type="hidden" name="event_type" value="extra" />' +
			'Name: <input readonly="readonly" name="extra_event_name" value= "' + eventName + '" /><br>';
		}
	}
	else {
		alert("Event name field missing!");
		return;
	}
	 
	if(document.getElementById('extra_isScheduled').checked) {
		
		newDivHTML = newDivHTML + 'Scheduled? <input type="checkbox" id="extra_isScheduled" onchange="toggleEventTime(this, ' + eventCounter + ')" value="yes" checked="checked" />' + 
		'<table border="0" id="event_' + eventCounter + '_time_container><tr>';
		
		
		if(document.getElementById('extra_time_start') != null) { 
			var timeStart = document.getElementById('extra_time_start').value;

			if(timeStart == "") {
				error = true;
				//TODO: Make important empty fields turn red.
			}
			else {
				newDivHTML = newDivHTML + '<td>Time Start: <input readonly="readonly" name="event_time_start" value= "' + timeStart + '" /> - </td>';
			}
		}
		else {
			alert("Event Time Start field missing!");
			return;
		}
		
		if(document.getElementById('extra_time_end') != null) { 
			var timeEnd = document.getElementById('extra_time_end').value;

			if(timeEnd == "") {
				error = true;
				//TODO: Make important empty fields turn red.
			}
			else {
				newDivHTML = newDivHTML + '<td>Time End: <input readonly="readonly" name="event_time_end" value= "' + timeEnd + '" /></td>';
			}
		}
		else {
			alert("Event Time End field missing!");
			return;
		}
		
		newDivHTML = newDivHTML + '</tr></table><br>';
		
		if(document.getElementById('extra_time_required') != null) { 
			var timeRequired = document.getElementById('extra_time_required').value;

			newDivHTML = newDivHTML + '<div id="event_' + eventCounter + '_time_required_container" style="display:none">' 
			'Time Required: <input readonly="readonly" name="event_time_required" value= "' + timeRequired + '" /><br></div>';
		}
		else {
			alert("Event Time Required field missing!");
			return;
		}
	}
	else {
		
		newDivHTML = newDivHTML + 'Scheduled? <input type="checkbox" id="extra_isScheduled" onchange="toggleEventTime(this, ' + eventCounter + ')" value="yes" />' + 
		'<table border="0" id="event_time_container style="display:none"><tr>';
		
		
		if(document.getElementById('extra_time_start') != null) { 
			var timeStart = document.getElementById('extra_time_start').value;

			if(timeStart == "") {
				error = true;
				//TODO: Make important empty fields turn red.
			}
			else {
				newDivHTML = newDivHTML + '<td>Time Start: <input readonly="readonly" name="event_time_start" value= "' + timeStart + '" /> - </td>';
			}
		}
		else {	
			alert("Event Time Start field missing!");
			return;
		}
		
		if(document.getElementById('extra_time_end') != null) { 
			var timeEnd = document.getElementById('extra_time_end').value;

			if(timeEnd == "") {
				error = true;
				//TODO: Make important empty fields turn red.
			}
			else {
				newDivHTML = newDivHTML + '<td>Time End: <input readonly="readonly" name="event_time_end" value= "' + timeEnd + '" /></td>';
			}
		}
		else {
			alert("Event Time End field missing!");
			return;
		}
		
		newDivHTML = newDivHTML + '</tr></table><br>';
		
		if(document.getElementById('extra_time_required') != null) { 
			var timeRequired = document.getElementById('extra_time_required').value;

			if(timeRequired == "") {
				error = true;
				//TODO: Make important empty fields turn red.
			}
			else {
				newDivHTML = newDivHTML + '<div id="event_time_required_container">' 
				'Time Required: <input readonly="readonly" name="event_time_required" value= "' + timeRequired + '" /><br></div>';
			}
		}
		else {
			alert("Event Time Required field missing!");
			return;
		}
	}
	
	if(document.getElementById('extra_date_start') != null) { 
		var dateStart = document.getElementById('extra_date_start').value;

		if(dateStart == "") {
			error = true;
			//TODO: Make important empty fields turn red.
		}
		else {
			newDivHTML = newDivHTML + '<input readonly="readonly" name="event_date_start" value= "' + dateStart + '" /><br>';
		}
	}
	else {
		alert("Event Date Start field missing!");
		return;
	}
	
	if(document.getElementById('extra_date_end') != null) { 
		var dateEnd = document.getElementById('extra_date_end').value;

		if(dateEnd == "") {
			error = true;
			//TODO: Make important empty fields turn red.
		}
		else {
			newDivHTML = newDivHTML + '<input readonly="readonly" name="event_date_end" value= "' + dateEnd + '" /><br>';
		}
	}
	else {
		alert("Event Date End field missing!");
		return;
	}
	
	//Get all checkbox values for recurrence
	var repeatList = document.getElementById("extra_recurrence").getElementsByTagName("input");
	
	var recurrence = "";
	
	var count;
	
	
	if(document.getElementById('extra_isRecurring').checked) {
		newDivHTML = newDivHTML + 
		'Is Recurring? <input id="event_' + eventCounter + '_isRecurring" name="event_' + eventCounter + '_isRecurring" type="checkbox" onchange="toggleEventRecurrence(this, ' + eventCounter + ')" value="yes" checked="checked" /><br>' +
		'<div id="eventBox_' + eventCounter + '_recurrence">';
	}
	else {
		newDivHTML = newDivHTML + 
		'Is Recurring? <input id="event_' + eventCounter + '_isRecurring" name="event_' + eventCounter + '_isRecurring" type="checkbox" onchange="toggleEventRecurrence(this, ' + eventCounter + ')" value="yes" /><br>' +
		'<div id="eventBox_' + eventCounter + '_recurrence" style="display:none">';
	}
	
	for (count=0; count < repeatList.length; count++) {
		if(repeatList[count].checked) {
			newDivHTML = newDivHTML + repeatList[count].value + '<input type="checkbox" name="recurrence" value="' + repeatList[count].value + '" checked="checked" />';
		}
		else {
			newDivHTML = newDivHTML + repeatList[count].value + '<input type="checkbox" name="recurrence" value="' + repeatList[count].value + '" />';
		}
	}
	newDivHTML = newDivHTML + '</div>';
	
	if(error) {
		alert("Important fields are blank and have been marked.");
		return;
	}
	
	//Close remaining tags for the new eventBox's HTML.
	newDivHTML = newDivHTML + '</form></div>';
	
	//Create the new eventbox
	var newDiv = document.createElement('div');
	
	var newDivName = 'eventBox_' + eventCounter;
	
	newDiv.setAttribute('id', newDivName);
	
	newDiv.setAttribute('class', "extra_eventBox");
	
	newDiv.innerHTML = newDivHTML;
	
	//clearTabFields(eventType);
	
	document.getElementById('eventBox_container').appendChild(newDiv);
	
	eventCounter = eventCounter + 1;
}

function addExam() {
	var error = false;
	
	//Generate the innerHTML for the new div based on the event's values.
	var newDivHTML = '<div class="eventBox_main">';
	
	//Get all event variable values
	if(document.getElementById('exam_event_name') != null) { 
		var eventName = document.getElementById('exam_event_name').value;

		if(eventName == "") {
			error = true;
			//TODO: Make important empty fields turn red.
		}
		else {
			newDivHTML = newDivHTML + eventName + '</div>' + '<div class="eventBox_sub"><form name="eventBoxForm_' + eventCounter + '" method="post">' + 
			'<input type="hidden" name="event_type" value="exam" />' +
			'<input readonly="readonly" name="exam_event_name" value= "' + eventName + '" /><br>';
		}
	}
	else {
		alert("Event name field missing!");
		return;
	}
	
	if(document.getElementById('exam_date') != null) { 
		var eventDate = document.getElementById('exam_date').value;

		if(eventDate == "") {
			error = true;
			//TODO: Make important empty fields turn red.
		}
		else {
			newDivHTML = newDivHTML + '<input readonly="readonly" name="event_date" value= "' + eventDate + '" /><br>';
		}
	}
	else {
		alert("Event Date field missing!");
		return;
	}
	
	if(document.getElementById('exam_time_start') != null) {
		var startTime = document.getElementById('exam_time_start').value;

		if(startTime == "") {
			error = true;
			//TODO: Make important empty fields turn red.
		}
		else {
			newDivHTML = newDivHTML + '<input readonly="readonly" name="event_time_start" value= "' + startTime + '" /> - ';
		}
	}
	else {
		alert("Event Time Start field missing!");
		return;
	}
	
	if(document.getElementById('exam_time_end') != null) {
		var endTime = document.getElementById('exam_time_end').value;

		if(endTime == "") {
			error = true;
			//TODO: Make important empty fields turn red.
		}
		else {
			newDivHTML = newDivHTML + '<input readonly="readonly" name="event_time_end" value= "' + endTime + '" /><br>';
		}
	}
	else {
		alert("Event Time End field missing!");
		return;
	}
	
	if(error) {
		alert("Important fields are blank and have been marked.");
		return;
	}
	
	//Close remaining tags for the new eventBox's HTML.
	newDivHTML = newDivHTML + '</form></div>';
	
	//Create the new eventbox
	var newDiv = document.createElement('div');
	
	var newDivName = 'eventBox_' + eventCounter;
	
	newDiv.setAttribute('id', newDivName);
	
	newDiv.setAttribute('class', "exam_eventBox");
	
	newDiv.innerHTML = newDivHTML;
	
	//clearTabFields(eventType);
	
	document.getElementById('eventBox_container').appendChild(newDiv);
	
	eventCounter = eventCounter + 1;
}

function addProject() {
	var error = false;
	
	//Generate the innerHTML for the new div based on the event's values.
	var newDivHTML = '<div class="eventBox_main">';
	
	//Get all event variable values
	if(document.getElementById('project_event_name') != null) { 
		var eventName = document.getElementById('project_event_name').value;

		if(eventName == "") {
			error = true;
			//TODO: Make important empty fields turn red.
		}
		else {
			newDivHTML = newDivHTML + eventName + '</div>' + '<div class="eventBox_sub"><form name="eventBoxForm_' + eventCounter + '" method="post">' + 
			'<input type="hidden" name="event_type" value="project" />' +
			'<input readonly="readonly" name="event_name" value= "' + eventName + '" /><br>';
		}
	}
	else {
		alert("Event name field missing!");
		return;
	}
	
	if(document.getElementById('project_date_start') != null) {
		var startDate = document.getElementById('project_date_start').value;
		
		if(startDate == "") {
			error = true;
			//TODO: Make important empty fields turn red.
		}
		else {
			newDivHTML = newDivHTML + '<input readonly="readonly" name="event_date_start" value="' + startDate + '" /> to ';
		}
	}
	else {
		alert("Date Start field missing!");
		return;
	}
	
	if(document.getElementById('project_date_end') != null) {
		var endDate = document.getElementById('project_date_end').value;
		
		if(endDate == "") {
			error = true;
			//TODO: Make important empty fields turn red.
		}
		else {
			newDivHTML = newDivHTML + '<input readonly="readonly" name="event_date_end" value="' + endDate + '" />';
		}
	}
	else {
		alert("Date End field missing!");
		return;
	}
	
	if(document.getElementById('project_time_required')) {
		var timeRequired = document.getElementById('project_time_required').value;
		if(timeRequired == "") {
			error = true;
			//TODO: Make important empty fields turn red.
		}
		else {
			newDivHTML = newDivHTML + '<br>Time Required: <input readonly="readonly" name="event_date_end" value="' + timeRequired + '" /> Minutes';
		}
	}
	else {
		alert("Time Required field missing!");
		return;
	}
	
	if(error) {
		alert("Important fields are blank and have been marked.");
		return;
	}
	
	//Close remaining tags for the new eventBox's HTML.
	newDivHTML = newDivHTML + '</form></div>';
	
	//Create the new eventbox
	var newDiv = document.createElement('div');
	
	var newDivName = 'eventBox_' + eventCounter;
	
	newDiv.setAttribute('id', newDivName);
	
	newDiv.setAttribute('class', "project_eventBox");
	
	newDiv.innerHTML = newDivHTML;
	
	//clearTabFields(eventType);
	
	document.getElementById('eventBox_container').appendChild(newDiv);
	
	eventCounter = eventCounter + 1;
	
}

function addClass() {
	
}

function addEvent(eventType, buttonloc) {
	
	var error = false;
	//Generate the innerHTML for the new div based on the event's values.
	var newDivHTML = '<div class="eventBox_main">';
	
	//Get all event variable values
	if(document.getElementById(eventType + '_event_name') != null) {
		var eventName = document.getElementById(eventType + '_event_name').value;

		if(eventName == "") {
			error = true;
			//TODO: Make important empty fields turn red.
		}
		else {
			newDivHTML = newDivHTML + eventName + '</div>' + '<div class="eventBox_sub"><form name="eventBoxForm_' + eventCounter + '" method="post">' + 
			'<input readonly="readonly" name="event_name" value= "' + eventName + '" /><br>';
		}
	}
	else {
		alert("Event name field missing!");
		return;
	}
	
	if(document.getElementById(eventType + '_date_start') != null) {
		var startDate = document.getElementById(eventType + '_date_start').value;
	}
	else {
		var startDate = null;
	}
	
	if(document.getElementById(eventType + '_date_end') != null) {
		var endDate = document.getElementById(eventType + '_date_end').value;
	}
	else {
		var endDate = null;
	}
	
	if(document.getElementById(eventType + '_time_start') != null) {
		var startTime = document.getElementById(eventType + '_time_start').value;
	}
	else {
		var startTime = null;
	}
	
	if(document.getElementById(eventType + '_time_end') != null) {
		var endTime = document.getElementById(eventType + '_time_end').value;
	}
	else {
		var endTime = null;
	}		
	
	if(document.getElementById(eventType + '_recurrence') != null) {
		var isRecurring = document.getElementById(eventType + '_recurrence').checked;
	}
	else {
		var isRecurring = false;
	}
	
	if(isRecurring) {
		//Get all checkbox values for recurrence
		var repeatList = document.getElementById(eventType + "_recurrence").getElementsByTagName("input");
		
		var recurrence = "";
		
		var count;
		
		var first = true;
		
		for (count=0; count < repeatList.length; count++) {
			if(repeatList[count].checked) {
				if(first){
					recurrence = recurrence + repeatList[count].value;
					first = false;
				}
				else {
					recurrence = recurrence + ", " + repeatList[count].value;
				}
			}
		}
	}
	
	if(error) {
		alert("Important fields are blank and have been marked.");
		return;
	}
	
	//Create the new eventbox
	var newDiv = document.createElement('div');
	
	var newDivName = 'eventBox_' + eventCounter;
	
	newDiv.setAttribute('id', newDivName);
	
	newDiv.setAttribute('class', eventType + "_eventBox");
	
	//Close remaining tags for the new EventBox html
	newDivHTML = newDivHTML + '</form></div>';
	
	newDiv.innerHTML = '<div class="eventBox_main">' + eventName + '</div>' + '<div class="eventBox_sub"><form name="eventBoxForm_' + eventCounter + '" method="post">' + 
	'<input readonly="readonly" name="event_name" value= "' + eventName + '" /><br>' +
	'<input readonly="readonly" name="event_time_start" value="' + startTime + '" />-' +
	'<input readonly="readonly" name="event_time_end" value="' + endTime + '" />' +
	'From <input readonly="readonly" name="event_date_start" value="' + startDate +'" /> to ' +
	'<input readonly="readonly" name="event_date_end" value="' + endDate + '" />' +	
	'<br>Recurring on: ' + recurrence +	
	'</form></div>';
	
	//clearTabFields(eventType);
	
	document.getElementById('eventBox_container').appendChild(newDiv);
	
	eventCounter = eventCounter + 1;		
}

function clearTabFields(eventType) {
	
	if(document.getElementById(eventType + '_event_name') != null) {
		document.getElementById(eventType + '_event_name').value = "";
	}
	
	if(document.getElementById(eventType + '_time_start') != null) {
		document.getElementById(eventType + '_time_start').value = "";
	}
	
	if(document.getElementById(eventType + '_time_end') != null) {
		document.getElementById(eventType + '_time_end').value = "";
	}
	
	if(document.getElementById(eventType + '_date_start') != null) {
		document.getElementById(eventType + '_date_start').value = "";
	}
	
	if(document.getElementById(eventType + '_date_end') != null) {
		document.getElementById(eventType + '_date_end').value = "";
	}
	
	if(document.getElementById(eventType + '_isRecurring') != null) {
		document.getElementById(eventType + '_isRecurring').checked = false;
		
		var repeatList = document.getElementById(eventType + "_recurrence").getElementsByTagName("input");
		
		for (var count=0; count < repeatList.length; count++) {
			repeatList[count].checked = false;
		}
	}			
}