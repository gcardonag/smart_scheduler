$(document).ready(function() {
    $('#submitBtn').click(function(){
        var eventName = $('#eName').attr('value');
        var startTime = $('#esTime').val();
        var endTime = $('#eeTime').val();
        var startDate = $('#esDate').val();
        var endDate = $('#eeDate').val();
       

        $('#rightCol #accordionEventsList').html(
            "
                <div class='accordion-group'>
                    <div class='accordion-heading'>
                        <a class='accordion-toggle' data-toggle='collapse' data-parent='#accordionEventsList' href='#collapse" +eventName+ "' style='background-color: #46a546'>
                        <strong style='color:white'>" + eventName + "</strong>
                        </a>
                    </div>
                    <div id='collapse"+eventName+ "' class='accordion-body collapse'>
                        <div class='accordion-inner'>
                            <dl class='dl-horizontal'>
                                <dt>Name:</dt>
                                <dd>" + eventName + "</dd>
                                <dt>Start Time:</dt>
                                <dd>" + startTime + "</dd>
                                <dt>End Time:</dt>
                                <dd>" + endTime + "</dd>
                                <dt>Start Date:</dt>
                                <dd>" + startDate + "</dd>
                                <dt>End Date:</dt>
                                <dd>" + endDate + "</dd>
                            </dl>
                            <button class='btn btn-inverse' id='deleteBtn'>Delete event</button>
                        </div>
                    </div>
                </div>
            "
        );
    });

    $('#deleteBtn').click(function(){

    });
});

/*
    //event handler for submit button
    $("#signInBtn").click(function () {
        //collect userName and password entered by users
        var userName = $("#username").val();
        var password = $("#password").val();

        //call the authenticate function
        //authenticate(userName, password);
        alert("OMG " + userName + ", your password is " + password);
    });

    $("a").click(function(event){
        event.preventDefault();
        $(this).hide("slow");
    });


    //authenticate function to make ajax call
    function authenticate(userName, password) {
        $.ajax
        ({
            type: "POST",
            //the url where you want to sent the userName and password to
            //url: "http://your-url.com/secure/authenticate.php",
            dataType: 'json',
            async: false,
            //json object to sent to the authentication url
            data: '{"userName": "' + userName + '", "password" : "' + password + '"}',
            success: function () {
                //do any process for successful authentication here
                alert("Welcome " + userName + "!");
            }
            alert("Server is down :(");
        });
    }
    var signIn = function(){
    alert("Test alert; please ignore");
}
*/
