//2 - paslėpimas, parodymas prie validateForm
$(document).ready(function(){
    $("#popup-error-name").hide();
    $("#popup-error-surname").hide();
    $("#popup-error-age").hide();
    $("#popup-error-date").hide();
})


//1.1 - validacija
function validateForm() 
{
    if(!checkName())
    {
        document.getElementById("popup-error-name").setAttribute("style", "display: visible !important; color:red;");
    }
    if(!checkSurname())
    {
        document.getElementById("popup-error-surname").setAttribute("style", "display: visible !important; color:red;");
    }
    if(!checkNumber())
    {
        document.getElementById("popup-error-age").setAttribute("style", "display: visible !important; color:red;");
    }
    if(!checkDate())
    {
        document.getElementById("popup-error-date").setAttribute("style", "display: visible !important; color:red;");
    }
}

function checkName()
{
    var name = document.getElementById("name").value;
    
    if(name == "")
        return false;

    $("#popup-error-name").hide();   
    return true;
}

function checkSurname()
{
    var surname = document.getElementById("surname").value;

    if(surname == "")
        return false;

    $("#popup-error-surname").hide();
    return true;    
}

function checkNumber()
{
    var age = document.getElementById("age").value;

    if(!(parseInt(age) == age && age > 0) || age == "")
        return false;
    
    $("#popup-error-age").hide();    
    return true;
}

function checkDate()
{
    var yearStr = document.getElementById("year").value;
    var monthStr = document.getElementById("month").value;
    var dayStr = document.getElementById("day").value;
    
    if(yearStr == "" || monthStr == "" || dayStr == "")
       return false;
    
    year = parseInt(yearStr);
    month = parseInt(monthStr) - 1;
    day = parseInt(dayStr);
    
    var date = new Date();

    if(month < 0 || month > 11)
        return false;
    
    if(day < 1 || day > 31 || (month == 1 && day > 29)) 
        return false;
    
    if(year < 1920 || year > 2022) 
        return false;    
    
    if(date.getDate() != day)    
        return false;
    
    if(date.getMonth() != month)     
        return false;
    
    if(date.getFullYear() != year)
        return false;
    
    $("#popup-error-date").hide();    
    return true;    
}

//3- dinaminis turinio modifikavimas
$(document).ready(function()
{
    $("#changeText").click(function()
    {
        $(".textChange").text("Yra papildomi pageidavimai");
    });
});

$(document).ready(function()
{
    $("#changeColor").click(function()
    {
        $(".textChange").css({"color": "red"});
    });
});

$(document).ready(function()
{
    $("#deleteParagraph").click(function()
    {
        var remove = document.getElementById("selection").value;
        $("." + remove).remove();
    });
});

$(document).ready(function()
{
    $("#addParagraph").click(function()
    {
        $("div.extraParagraph").append(
            '<p>' + $("#newText").val() + '</p>')
    });
});

// 4 - asinchroninis komunikavimas su serveriu
var response = "";

$("#submit").click(function()
{
    var json = {
        "name": $("#name").val(),
        "surname": $("#surname").val(),
        "age": $("#age").val(),
        "email": $("#email").val(),
        "agreed": "Taip"
    }

    $.ajax({
        type: "POST",
        url: "https://jsonblob.com/api/jsonBlob/",
        data: JSON.stringify(json),
        contentType: 'application/json',
        success: function(res){
            $(".table-info").append(
                '<tr>' + '<td>' + res.name + " " + res.surname + '</td>'
                + '<td>' + res.age + '</td>'
                + '<td>' + res.email + '</td>'
                + '<td>' + res.agreed + '</td>' + '</tr>'
            )
            console.log(res);
            console.log("Added");
        }.bind(this),
        error: function(xhr, status, err)
        {
            console.error(xhr, status, err.toString());
        }.bind(this)
    });
});