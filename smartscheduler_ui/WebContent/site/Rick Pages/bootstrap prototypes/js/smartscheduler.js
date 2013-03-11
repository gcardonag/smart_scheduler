$(document).ready(function () {
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
    })
}

/*var signIn = function(){
    alert("Test alert; please ignore");
}*/