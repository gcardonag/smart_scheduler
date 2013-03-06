eventCounter = 0;
function Event(name) {
	this.name = name;
	this.recurrance = false;
};

Event.setDates = function(startDate, endDate){
	this.startDate = startDate;
	if(endDate != null)
		this.endDate = endDate;
};

Event.setTimes = function(startTime, endTime){
	this.startTime = startTime;
	this.endTime = endTime;
};

Event.toggleRecurrance = function(){
	if(recurrance_flag = true)
		recurrance_flag = false;
	else
		recurrance_flag = true;
};