$( document ).ready(function() { 
    $( "#aText1" ).click(function() { sendAnswer(currentQuestionId, 1);});
    $( "#aText2" ).click(function() { sendAnswer(currentQuestionId, 2);});
    $( "#aText3" ).click(function() { sendAnswer(currentQuestionId, 3);});
    $( "#aText4" ).click(function() { sendAnswer(currentQuestionId, 4);});

    loadRandomQuestion();
    console.log("asdad");
});

var currentQuestionId = -1;

function loadRandomQuestion(){
    $.ajax({
      type: 'GET',
      url: "http://192.168.178.43:7000/api/randomQuestion/",
      async: false,
      headers: {"Access-Control-Allow-Origin": "*"},
      dataType: 'json',
      success: function (data) {
        $( "#fragep" ).html( data.question );
        $( "#aText1" ).html( data.answer1 );
        $( "#aText2" ).html( data.answer2 );
        $( "#aText3" ).html( data.answer3 );
        $( "#aText4" ).html( data.answer4 );
        currentQuestionId = data.id
      }
    });
}

function sendAnswer(questionID, answerID){
    if(currentQuestionId == -1) return;
    
    $.ajax({
      type: 'POST',
      url: "http://192.168.178.43:7000/api/sendAnswer/",
      data: {questionID:" + questionID +",answerID:"+ answerID + "},
      async: true,
      contentType: "application/json", 
      dataType: 'json',
      success: function (data) {
        currentQuestionId = -1
          
        if(data == answerID){
           $( "#answer"+answerID ).addClass("rightAnswer");
        }else{
           $( "#answer"+answerID ).addClass("wrongAnswer");
           $( "#answer"+data ).addClass("rightAnswer");
        }
      }
    });
}

function removeWrongRightIndicator(){
    for( i = 1 ; i < 5 ; i ++){
    $( "#answer"+i ).removeClass("rightAnswer");
    $( "#answer"+i ).removeClass("wrongAnswer");
    }
}
