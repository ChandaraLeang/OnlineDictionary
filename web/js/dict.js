/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

"use strict";

$(document).ready(function () {
    $(function () {
        $('#loading').hide();
        $(document).ajaxStart(function () {
            $('#loading').show();
        }).ajaxStop(function () {
            $('#loading').hide();
        });
    });
    
    //Trigger user press enter key
    $('#term').keypress(function (event) {
        if (event.which == '13') {
            requestData();
        }
    });
    
    //Show result when user click lookup button
    $("#btnLookup").click(requestData);
    
    function requestData() {
        var term = $("#term").val();
        var requestURL = "./DictServlet";
        if (term !== '') {
            $.post(requestURL, {"term": term}).done(getDictionaryData).error(onRequestError);
        }
    }
    
    function getDictionaryData(data) {
        var result = JSON.parse(data);
        result = result.dictionaryData;
        var searchResult = '';
        $.each(result, function (index, value) {
            searchResult += "<dl>";
            searchResult += "<dt class='word'>";
            searchResult += index+1;
            searchResult += " <span class='wordType'>(" + value.wordType + ")</span></dt>";
            searchResult += "<dd class='definition'>" + value.definition + "</dd>";
            searchResult += "</dl>";
        });
        
        if (searchResult !== '') {
            $("#output").html(searchResult);
        } else {
            $("#output").html("<div class='notFound'>Search not found!</div>");
        }

    }

    function onRequestError(xhr, status, exception) {
        console.log(xhr, status, exception);
    }
});
