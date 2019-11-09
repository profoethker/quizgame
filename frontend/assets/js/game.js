$( document ).ready(function() { 
    $( "#aText1" ).click(function() { sendAnswer(currentQuestionId, 1);});
    $( "#aText2" ).click(function() { sendAnswer(currentQuestionId, 2);});
    $( "#aText3" ).click(function() { sendAnswer(currentQuestionId, 3);});
    $( "#aText4" ).click(function() { sendAnswer(currentQuestionId, 4);});
          
    loadRandomQuestion();
    
});

var currentQuestionId = -1;
var alreadyTipped = false;
var ip_address = 'localhost:7000'

function loadRandomQuestion(){
    $.ajax({
      type: 'GET',
      url: "http://"+ ip_address + "/api/randomQuestion/",
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
        alreadyTipped = false;
          
        $( "#EichText" ).html("Wenn die Frage zu schwer ist, kannst du mich um einen Tipp bitten.");
        $( "#eichAnswerContainer" ).html("<div id='tipp' class='eichAnswer'><p class='eichAnswerText'>Ich brauche einen Tipp</p></div>");
          
        $( "#tipp" ).click(function() { 
            eichTippLoader();    
        });
        $( "#MadOakPicture" ).attr("id", "EichPicture");
        $( "#HappyOakPicture").attr("id", "EichPicture");
      }
    });
}

function eichTippLoader(){
    $.ajax({
      type: 'POST',
      url: "http://"+ ip_address + "/api/tip/",
      data: JSON.stringify({currentQuestionId}),
      async: true,
      contentType: "application/json", 
      dataType: 'json',
      success: function (data) {
          $( "#eichAnswerContainer" ).html(" ");
          if(alreadyTipped) return;
          alreadyTipped = true;
          if(data.type == "tip"){
              if(data.tip.length == 0) $( "#EichText" ).html("Die Aufgabe ist so einfach, hier bekommst du noch keinen Tip.");
              else $( "#EichText" ).html(data.tip);
          }if(data.type == "50/50"){
             $( "#EichText" ).html("Ich habe dir die Anzahl der möglich Antworten halbiert. Ich hoffe es hilft dir.");
             $( "#aText" + data.wrong1 ).html( "" );
             $( "#aText" + data.wrong2 ).html( "" );
          }if(data.type == "skip"){
             $( "#EichText" ).html("Ok, dann will ich mal nicht so sein. Ich gebe dir eine neue Frage.");
              
             currentQuestionId = -1;
             
             $( "#answer"+data.correct ).addClass("rightAnswer");

             $( "#eichAnswerContainer" ).html("<div id='nextQuestion' class='eichAnswer'><p class='eichAnswerText'>Zur nächsten Frage</p></div>");
              
             $( "#nextQuestion" ).click(function() { 
                removeWrongRightIndicator();
                loadRandomQuestion();    
             });  
          }
          
      }
    });
}

function sendAnswer(questionID, answerID){
    if(currentQuestionId == -1) return;
    
    $.ajax({
      type: 'POST',
      url: "http://" + ip_address + "/api/sendAnswer/",
      data: JSON.stringify({ "questionID":  questionID ,"answerID": answerID }),
      async: true,
      contentType: "application/json", 
      dataType: 'json',
      success: function (data) {
        currentQuestionId = -1
          
        if(data.correct == answerID){
           $( "#answer"+answerID ).addClass("rightAnswer");
           $( "#EichText" ).html("Richtig! </br> "+ data.info);
           $( "#EichPicture").attr("id", "HappyOakPicture");
        }else{
           $( "#answer"+answerID ).addClass("wrongAnswer");
           $( "#answer"+data.correct ).addClass("rightAnswer");
           $( "#EichText" ).html("Leider Falsch! </br> "+ data.info);
           $( "#EichPicture" ).attr("id", "MadOakPicture");
        }
        
        //Sprechblase mit erklärung anzeigen
        $( "#eichAnswerContainer" ).html("<div id='nextQuestion' class='eichAnswer'><p class='eichAnswerText'>OK</p></div>");
          
        $( "#nextQuestion" ).click(function() { 
            removeWrongRightIndicator();
            loadRandomQuestion();
        });
      }
    });
}

function removeWrongRightIndicator(){
    for( i = 1 ; i < 5 ; i ++){
        $( "#answer"+i ).removeClass("rightAnswer");
        $( "#answer"+i ).removeClass("wrongAnswer");
    }
}