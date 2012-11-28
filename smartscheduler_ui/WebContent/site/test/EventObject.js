eventCounter = 0;
function Event(name) {
	this.name = name;
};

Event.setDates = function(startDate, endDate){
	this.startDate = startDate;
	if(endDate != null)
		this.endDate = endDate;
};

Event.setTimes = function(startTime, endTime){
	this.startTime = startTime;
	this.endTime = endTime;
}