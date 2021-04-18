/* global Recognize _config*/

var Recognize = window.Recognize || {};

(function recognizeScopeWrapper($) {
    let authToken;

    const urlParams = new URLSearchParams(window.location.search);
    const sessionId = urlParams.get('id');
    if (sessionId == null) {
        // TODO: Print it to html, then redirect user. Remove these 3 lines.
        console.log('No session provided in URL.');
        alert('No session provided in URL.');
        window.location.href = '/index.html';
    }


    Recognize.authToken.then(function setAuthToken(token) {
        if (token) {
            authToken = token;
            console.log(authToken);
            getSessions();
        } else {
            window.location.href = '/signin.html';
        }
    }).catch(function handleTokenError(error) {
        alert(error);
        window.location.href = '/signin.html';
    });

    // Processes the result json object received from API to entries in Teaching Groups table
    function listAttenders(result) {
        //console.log('Response received from API: ', result);
        // The code to put result in the table...
        for (var i = 0; i < result.Response.Items.length; i++){
            sessionAttenders(result.Response.Items[i]);
        }
    }

    function sessionAttenders(rowData){
        var row = $("<tr/>");
        $('#attendersTable').append(row);
        row.append($("<td>" + rowData.attender + "</td>"));
        row.append($("<td>" + rowData.time + "</td>"));
    }


    // Register click handler for buttons
    $(() => {
        $('#signOut').click(() => {
            Recognize.signOut();
            alert("You have been signed out.");
            window.location = "index.html";
        });
    });


    // Register click handler for #request button
    function getSessions() {
        // This is for demonstrating how to call the API
        $.ajax({
            method: 'GET',
            url: _config.api.invokeUrl + '/session/'+ sessionId + '/attendance',
            headers: {
                Authorization: authToken
            },
            contentType: 'application/json',
            success: listAttenders,
            error: function ajaxError(jqXHR, textStatus, errorThrown) {
                console.error('Error requesting attention: ', textStatus, ', Details: ', errorThrown);
                console.error('Response: ', jqXHR.responseText);
                alert(`An error occured when sending your request:\n${jqXHR.responseText}`);
            }
        });
    };

}(jQuery));
