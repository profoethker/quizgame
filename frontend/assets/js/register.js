$( document ).ready(function() { 
    $("#load").hide();
    $("#result-icon").hide();
    
});

const ip_address = '192.168.178.43:7000'

function bar(){
    $("#load").show();
    setTimeout(sendEvent, 3000);
}

function sendEvent(){
    const username =  $( "#username").val();
    const email = $("#email").val();
    const password = $("#pass").val();    
    
        $.ajax({
            url: "http://"+ ip_address + "/api/register/",
            type: 'POST',
            data: JSON.stringify({"username": username, "email": email, "password": password}),
            async: true,
            contentType: "application/json", 
            dataType: 'json',
            success: function () {
                console.log("Success");
            }
        });
        $("#load").hide();
        $("#result-icon").show();
    

}