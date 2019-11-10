$( document ).ready(function() { 
    $( "#aText1" ).click(function() { sendAnswer(currentQuestionId, 1);});
    $( "#aText2" ).click(function() { sendAnswer(currentQuestionId, 2);});
    $( "#aText3" ).click(function() { sendAnswer(currentQuestionId, 3);});
    $( "#aText4" ).click(function() { sendAnswer(currentQuestionId, 4);});
          
    loadRandomQuestion();
    
});

var currentPersonalQuestionId = -1;
var currentQuestionId = -1;
var alreadyTipped = false;
//var ip_address = '192.168.178.43:7000'
var ip_address = 'localhost:7000'
var highscore = 0
var multiplier = 1
var correct_streak = 0

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
            personalQuestionLoader();    
        });
        $( "#MadOakPicture" ).attr("id", "EichPicture");
        $( "#HappyOakPicture").attr("id", "EichPicture");
      }
    });
}

function personalQuestionLoader(){
    $.ajax({
      type: 'GET',
      url: "http://"+ ip_address + "/api/p/generate/",
      async: true,
      headers: {"Access-Control-Allow-Origin": "*"},
      dataType: 'json',
      success: function (data) {
        $( "#EichText" ).html("Es wäre nett, wenn du mir kurz eine Frage beantwortest: </br>" + data.question);
        if(data.answer1){
            $( "#eichAnswerContainer" ).html("<div id='p1' class='eichAnswer'><p class='eichAnswerText'>"+data.answer1+"</p></div>");
            $( "#p1" ).click(function() { uploadPersonalData( 1);});
        }
        if(data.answer2){
            $( "#eichAnswerContainer" ).append("<div id='p2' class='eichAnswer'><p class='eichAnswerText'>"+data.answer2+"</p></div>");
            $( "#p2" ).click(function() { uploadPersonalData( 2);});
        }
        if(data.answer3){
            $( "#eichAnswerContainer" ).append("<div id='p3' class='eichAnswer'><p class='eichAnswerText'>"+data.answer3+"</p></div>");
            $( "#p3" ).click(function() { uploadPersonalData( 3);});
        }   
        if(data.answer4){
            $( "#eichAnswerContainer" ).append("<div id='p4' class='eichAnswer'><p class='eichAnswerText'>"+data.answer4+"</p></div>");
            $( "#p4" ).click(function() { uploadPersonalData( 4);});
        }

        currentPersonalQuestionId = data.id
          
      }
    });    
}

function uploadPersonalData( answerChoosen){
    $.ajax({
      type: 'POST',
      url: "http://"+ ip_address + "/api/p/store/",
      data: JSON.stringify({ "questionID":  currentPersonalQuestionId ,"personaSelectionID": answerChoosen }), 
      async: true,
      contentType: "application/json", 
      dataType: 'json',
      success: function (data) {
          eichTippLoader();
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
           highscore = highscore + (100 * multiplier);
           correct_streak++;
           if (correct_streak >= 2){
               multiplier = multiplier + 0.5;
               correct_streak = 0;
           }
        }else{
           $( "#answer"+answerID ).addClass("wrongAnswer");
           $( "#answer"+data.correct ).addClass("rightAnswer");
           $( "#EichText" ).html("Leider Falsch! </br> "+ data.info);
           $( "#EichPicture" ).attr("id", "MadOakPicture");
           multiplier = 1;
           correct_streak = 0;
        }
        updateScore();
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

function updateScore(){
    $( "#multiplier_text").html('<p>Multiplier: '+ multiplier + 'x</p>');
    $( "#highscore_text").html("<p>Highscore: "+ highscore+ '</p>');
}
