<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.ArrayList" %>

<!doctype html>
<html lang="en">
	<head>
    <meta charset="utf-8">
		<title>Test Page</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
		<link rel='stylesheet' href='css/bootstrap.css'>
    <link rel='stylesheet' href='css/bootstrap-responsive.css'> 
		<script src='js/jquery-1.8.2.js'></script>
    <script src='js/bootstrap.js'></script>
    <!-- ADDING DATE&TIME PICKERS HERE -->
    <link rel="stylesheet" href="jquery-ui-1.8.18.custom/development-bundle/themes/base/jquery.ui.all.css">
  	<script src="jquery-ui-1.8.18.custom/development-bundle/ui/jquery.ui.core.js"></script>
  	<script src="jquery-ui-1.8.18.custom/development-bundle/ui/jquery.ui.widget.js"></script>
  	<script src="jquery-ui-1.8.18.custom/development-bundle/ui/jquery.ui.datepicker.js"></script>
  	<script type="text/javascript">
		var eventsList = <% 
			String s = (String) request.getAttribute("processedEvents");
			if(s != null){
				out.println(s) ;
			}
			else{
				out.println("[]");
			}
		%>;
		
		
	</script>
  	<link rel="stylesheet" type="text/css" href="anytime/anytime_css.css" />
  	<script src="anytime/anytime.js"></script>
	<!-- END DATE&TIME PICKER ADDITION -->
    <script src='js/smartscheduler.js'></script>
    <script>
    
    </script>
    <style type="text/css">
      body {
        padding-top: 60px; /* 60px to make the container go all the way to the bottom of the topbar */
        padding-bottom: 40px;
        background-color: white;
      }
    </style>

	</head>
	
	<body>
		<!-- ------------------------------------------------------ -->
				<button id="alertbutton">Alert Event List</button>
				<script type="text/javascript">
					$('#alertbutton').click(function(){
						alert(eventsList);
					});
				</script>
		<!-- ------------------------------------------------------ -->
		<div class="container">
      <div class="page-header">
        <h1><i class="icon-user"></i> Test User <small>event scheduler</small></h1>

      </div>

      <div class="row-fluid">
        <div class="span1">
          <button type="button" class="btn btn-mini btn-success" id="btnShowEvents">Show current events in array</button>
          <button type="button" class="btn btn-mini" id="btnAddFromArray">Add events from array</button>
        </div>
        <div class="span5">
          <h3>Select the event type you wish to schedule</h3>
                       
          <div class="btn-group" data-toggle="buttons-radio">
              <button type="button" class="btn" id="btnClass" name="class">Class</button>
              <button type="button" class="btn" id="btnDeadline" name="deadline">Deadline</button>
              <button type="button" class="btn" id="btnMeeting" name="meeting">Meeting</button>
              <button type="button" class="btn" id="btnFlexible" name="flexible">Flexible</button>
              <button type="button" class="btn disabled" id="CustomButton" name="custom">Custom</button>
          </div>

          <button type="button" id="helpButton" class="btn btn-inverse" rel="popover" title="Help Popover" data-trigger="click" data-placement="right" data-content=""><i class="icon-info-sign icon-white"></i> Help</button>

          <div id="divClass" style="display: none">
            <br />
            <form class="well form-horizontal" id="formsClass">
              <div class="control-group">
                <label class="control-label">Name:</label>
                <div class="controls">
                  <input type="text" name="className" placeholder="Name">
                </div>
              </div>
              <div class="control-group">
                <label class="control-label">Start Date:</label>
                <div class="controls">
                  <input type="text" name="classStartDate" placeholder="mm/dd/yy">
                </div>
              </div>
              <div class="control-group">
                <label class="control-label">End Date:</label>
                <div class="controls">
                  <input type="text" name="classEndDate" placeholder="mm/dd/yy">
                </div>
              </div>
              <div class="control-group">
                <label class="control-label">From:</label>
                <div class="controls">
                  <input type="text" id="classStartTime" name="classStartTime" placeholder="hh:mm AM/PM">
                </div>
              </div>
              <div class="control-group">
                <label class="control-label">To:</label>
                <div class="controls">
                  <input type="text" id="classEndTime" name="classEndTime" placeholder="hh:mm AM/PM">
                </div>
              </div>
              <br />
              <div class="showDivRecurrence">
                <div class="btn-group">
                  <button type="button" class="btn btnRecurrence" data-toggle="button">Recurrence</button>
                  <button type="button" id="btnRecHelp" class="btn btn-inverse" title="Recurrence Help Popover" data-trigger="click" data-placement="right" data-content="Example: An event recurrs every 10 Days, every 2 Weeks, etc"><i class="icon-info-sign icon-white"></i></button>
                </div>
              </div>
              <br />
              <div class="showDivPriority">
                <button type="button" class="btn btnPriority" data-toggle="button">Priority</button>
              </div>
            </form>
          </div> <!-- End of Class div -->

          <div id="divDeadline" style="display: none">
            <br />
            <form class="well form-horizontal" id="formsDeadline">
              <div class="control-group">
                <label class="control-label">Name:</label>
                <div class="controls">
                  <input type="text" name="deadlineName" placeholder="Name"></input>
                </div>
              </div>
              <div class="control-group">
                <label class="control-label">Start Date:</label>
                <div class="controls">
                  <input type="text" name="deadlineStartDate" placeholder="mm/dd/yy"></input>
                </div>
              </div>
              <div class="control-group">
                <label class="control-label">Due Date:</label>
                <div class="controls">
                  <input type="text" name="deadlineEndDate" placeholder="mm/dd/yy"></input>
                </div>
              </div>
              <div class="control-group">
                <label class="control-label">Due by:</label>
                <div class="controls">
                  <input type="text" id="deadlineEndTime" name="deadlineEndTime" placeholder="hh:mm AM/PM"></input>
                </div>
              </div>
              <br />
              <div class="showDivRecurrence">
                <div class="btn-group">
                  <button type="button" class="btn btnRecurrence" data-toggle="button">Recurrence</button>
                  <button type="button" id="btnRecHelp" class="btn btn-inverse" title="Recurrence Help Popover" data-trigger="click" data-placement="right" data-content="Example: An event recurrs every 10 Days, every 2 Weeks, etc"><i class="icon-info-sign icon-white"></i></button>
                </div>
              </div>
              <div class="showDivPriority">
                <button type="button" class="btn btnPriority" data-toggle="button">Priority</button>
              </div>
            </form>
          </div><!-- End of Deadline div -->

          <div id="divMeeting" style="display: none">
            <br />
            <form class="well form-horizontal" id="formsMeeting">
              <div class="control-group">
                <label class="control-label">Name:</label>
                <div class="controls">
                  <input type="text" name="meetingName" placeholder="Name"></input>
                </div>
              </div>
              <div class="control-group">
                <label class="control-label">Date:</label>
                <div class="controls">
                  <input type="text" name="meetingEndDate" placeholder="mm/dd/yy"></input>
                </div>
              </div>
              <div class="control-group">
                <label class="control-label">From:</label>
                <div class="controls">
                  <input type="text" id="meetingStartTime" name="meetingStartTime" placeholder="hh:mm AM/PM"></input>
                </div>
              </div>
              <div class="control-group">
                <label class="control-label">To:</label>
                <div class="controls">
                  <input type="text" id="meetingEndTime" name="meetingEndTime" placeholder="hh:mm AM/PM"></input>
                </div>
              </div>
            </form>
          </div><!-- End of Meeting div -->

          <div id="divFlexible" style="display: none">
            <br />
            <form class="well form-horizontal" id="formsFlexible">
              <div class="control-group">
                <label class="control-label">Name:</label>
                <div class="controls">
                  <input type="text" name="flexibleName" placeholder="Name">
                </div>
              </div>
              <div class="control-group">
                <label class="control-label">Start Date:</label>
                <div class="controls">
                  <input type="text" name="flexibleStartDate" placeholder="mm/dd/yy">
                </div>
              </div>
              <div class="control-group">
                <label class="control-label">End Date:</label>
                <div class="controls">
                  <input type="text" name="flexibleEndDate" placeholder="mm/dd/yy">
                </div>
              </div>
              <div class="control-group">
                <label class="control-label">From:</label>
                <div class="controls">
                  <input type="text" id="flexibleStartTime" name="flexibleStartTime" placeholder="hh:mm AM/PM">
                </div>
              </div>
              <div class="control-group">
                <label class="control-label">To:</label>
                <div class="controls">
                  <input type="text" id="flexibleEndTime" name="flexibleEndTime" placeholder="hh:mm AM/PM">
                </div>
              </div>
              <br />
              <div class="showDivRecurrence">
                <div class="btn-group">
                  <button type="button" class="btn btnRecurrence" data-toggle="button">Recurrence</button>
                  <button type="button" id="btnRecHelp" class="btn btn-inverse" title="Recurrence Help Popover" data-trigger="click" data-placement="right" data-content="Example: An event recurrs every 10 Days, every 2 Weeks, etc"><i class="icon-info-sign icon-white"></i></button>
                </div>
              </div>
              <br />
              <div class="showDivPriority">
                <button type="button" class="btn btnPriority" data-toggle="button">Priority</button>
              </div>
            </form>
          </div><!--End of Flexible div -->

          <div class="form-actions" style="display:none">
            <button type="button" class="btn btn-block btn-success" id="btnAddEvent">Add event</button>
            <button type="button" class="btn btn-block" id="btnCancel">Cancel</button>
          </div>

          <div id="divRecurrence">
            <label>Repeats every:</label>
            <select class="span2 recInterval">
              <option value="none">N/A</option>
              <option value="1">1</option>
              <option value="2">2</option>
              <option value="3">3</option>
              <option value="4">4</option>
              <option value="5">5</option>
              <option value="6">6</option>
              <option value="7">7</option>
              <option value="8">8</option>
              <option value="9">9</option>
              <option value="10">10</option>
              <option value="11">11</option>
              <option value="12">12</option>
              <option value="13">13</option>
              <option value="14">14</option>
              <option value="15">15</option>
              <option value="16">16</option>
              <option value="17">17</option>
              <option value="18">18</option>
              <option value="19">19</option>
              <option value="20">20</option>
              <option value="21">21</option>
              <option value="22">22</option>
              <option value="23">23</option>
              <option value="24">24</option>
              <option value="25">25</option>
              <option value="26">26</option>
              <option value="27">27</option>
              <option value="28">28</option>
              <option value="29">29</option>
              <option value="30">30</option>
              <option value="31">31</option>
            </select>

            <select class="span3 recType">
              <option value="none">N/A</option>
              <option value="daily">Days</option>
              <option value="weekly">Weeks</option>
              <option value="monthly">Months</option>
            </select>

            <div class="checkboxesWeekly">
              <label>Days of the week:</label>
              <label class="checkbox inline">
                <input type="checkbox" value="Sunday"> Sun
              </label>
              <label class="checkbox inline">
                <input type="checkbox" value="Monday"> Mon
              </label>
              <label class="checkbox inline">
                <input type="checkbox" value="Tuesday"> Tue
              </label>
              <label class="checkbox inline">
                <input type="checkbox" value="Wednesday"> Wed
              </label>
              <label class="checkbox inline">
                <input type="checkbox" value="Thursday"> Thu
              </label>
              <label class="checkbox inline">
                <input type="checkbox" value="Friday"> Fri
              </label>
              <label class="checkbox inline">
                <input type="checkbox" value="Saturday"> Sat
              </label>
            </div>
          </div><!-- End of recurrence div -->

          <div id="divPriority">
            <label>How much weekly time do you estimate you will need to dedicate to this task?</label>
              <input class="input-mini" type="text" name="estimate"></input>
              <select class="input-small" id="estimateType">
                <option value="minutes">Minutes</option>
                <option value="hours">Hours</option>
              </select>
            <label>Priority:</label>
            <div class="btn-group" data-toggle="buttons-radio" id="classPriority">
                <button type="button" class="btn" id="lowPriority">Low</button>
                <button type="button" class="btn" id="medPriority">Medium</button>
                <button type="button" class="btn" id="highPriority">High</button>
            </div>
          </div><!-- End of priority div -->
        </div><!-- End of collumn -->
       
        <div class="span5" id="rightCol">
          <div id="listView">
            <h3>Event List</h3>
            <p class="muted">When you are finished, click the button on the right to submit your events and generate your schedule!</p>
          	<div class='accordion' id='accordionEventsList'>

          	</div><!--End of accodion -->
          </div><!--End of list view -->

          <div id="calendarView" style="display:none">
            <h3>Calendar View</h3>
              <ul class="nav nav-tabs">
                <li class="active"><a href="#tabDaily" data-toggle="tab">Daily</a></li>
                <li><a href="#tabWeekly" data-toggle="tab">Weekly</a></li>
                <li><a href="#tabMonthly" data-toggle="tab">Monthly</a></li>
              </ul>
              <div class="tab-content">
                <div class="tab-pane active" id="tabDaily">
                  <div class='accordion' id='accordionDailyList'>
                  
                  </div>
                </div>
                <div class="tab-pane" id="tabWeekly">
                  <div class='accordion' id='accordionWeeklyList'>
                  
                  </div>
                </div>
                <div class="tab-pane" id="tabMonthly">
                  <div class='accordion' id='accordionMonthlyList'>
                  
                  </div>
                </div>
              </div>
          </div> <!-- End of calendar view -->
          <!--<p id="eventArrayParagraph"></p>-->
        </div><!-- End of collumn -->
        <div class="span1">
          <form action="IOGenerateServlet" method="post" id="generateForm">
            <p>
            <button class="btn btn-block btn-danger" id="generateBtn"><i class="icon-calendar icon-white"></i>Generate Schedule</button>
            <input type="hidden" id="eventArrayList" name="eventArrayList"></input>
            </p>
          </form>
          <div class="btn-group btn-block btn-group-vertical">
            <p>
            <button type="button" class="btn btn-block btn-primary" id="listViewBtn">Event List</button>
            <button type="button" class="btn btn-block btn-warning" id="calendarViewBtn">Calendar</button>
            <button type="button" class="btn btn-block btn-inverse">Alt Calendar</button>
            </p>
          </div>
          <p>
          <button class="btn btn-block btn-info" type="button" id='btnDeleteAll'><i class="icon-trash icon-white"></i>Delete events</button>
          </p>
        </div>
      </div><!-- End of row -->

    </div> <!-- /container -->
	</body>
</html>