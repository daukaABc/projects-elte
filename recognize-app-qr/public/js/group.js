/* global Recognize _config*/

var Recognize = window.Recognize || {};

(function recognizeScopeWrapper($) {
    let authToken;

    const urlParams = new URLSearchParams(window.location.search);
    const groupId = urlParams.get('id');
    if (groupId == null) {
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
    function listGroupSessions(result) {
        //console.log('Response received from API: ', result);
        // The code to put result in the table...
        for (var i = 0; i < result.Response.Items.length; i++){
            groupSessions(result.Response.Items[i]);
        }
    }

    function groupSessions(rowData){
        var row = $("<tr/>");
        $('#sessionsTable').append(row);
        row.append($("<td onclick=window.location='session.html?id=" + rowData.id + "'; style='color:red'; >" + rowData.starts_at + "</td>"));
        row.append($("<td>" + rowData.ends_at + "</td>"));
        row.append($("<td onclick=window.location='qr.html?session=" + rowData.id + "'; style='color:red'; > Get QR </td>"));
    }


    // Register click handler for buttons
    $(() => {
        $('#newSessionForm').submit(handleNewSession);
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
            url: _config.api.invokeUrl + '/group/'+ groupId + '/session',
            headers: {
                Authorization: authToken
            },
            contentType: 'application/json',
            success: listGroupSessions,
            error: function ajaxError(jqXHR, textStatus, errorThrown) {
                console.error('Error requesting attention: ', textStatus, ', Details: ', errorThrown);
                console.error('Response: ', jqXHR.responseText);
                alert(`An error occured when sending your request:\n${jqXHR.responseText}`);
            }
        });
    };


    function newSessionSuccess(result) {
        location.reload();
    }

    function handleNewSession(event) {
        const start = $('#startTime').val();
        const end = $('#endTime').val();
        event.preventDefault();
        console.log("gp:"+groupId);
        $.ajax({
            method: 'POST',
            url: `${_config.api.invokeUrl}/group/${groupId}/session`,
            headers: {
                Authorization: authToken
            },
            data: JSON.stringify({
                starts_at: start,
                ends_at: end
            }),
            contentType: 'application/json',
            success: newSessionSuccess,
            error: function ajaxError(jqXHR, textStatus, errorThrown) {
                console.error('Error requesting attention: ', textStatus, ', Details: ', errorThrown);
                console.error('Response: ', jqXHR.responseText);
                alert(`An error occured when sending your request:\n${jqXHR.responseText}`);
            }
        });
    }

}(jQuery));
