/*global Recognize _config*/

var Recognize = window.Recognize || {};

(function recognizeScopeWrapper($) {
    let authToken;

    const urlParams = new URLSearchParams(window.location.search);
    const session = urlParams.get('session');
    if (session == null) {
        // TODO: Print it to html, then redirect user. Remove these 3 lines.
        console.log('No session provided in URL.');
        alert('No session provided in URL.');
        window.location.href = '/index.html';
    }
    

    Recognize.authToken.then(function setAuthToken(token) {
        if (token) {
            authToken = token;
            console.log(authToken);
            sendAttendance(session, completeRequest)
        } else {
            window.location.href = '/signin.html';
        }
    }).catch(function handleTokenError(error) {
        alert(error);
        window.location.href = '/signin.html';
    });



    function completeRequest(result) {
        const time = result.Time;
        // TODO: Show the result to user. Remove these 2 lines.
        console.log('Submitted attendance at ' + time + '.');
        alert('Submitted attendance at ' + time + '.');
    }

    function sendAttendance(session, completeRequest) {
        console.log('SENDING THE POST REQUEST');
        $.ajax({
            method: 'POST',
            url: _config.api.invokeUrl + '/session/'+ session +'/attendance',
            headers: {
                Authorization: authToken
            },
            contentType: 'application/json',
            success: completeRequest,
            error: function ajaxError(jqXHR, textStatus, errorThrown) {
                console.error('Error submitting attendance: ', textStatus, ', Details: ', errorThrown);
                console.error('Response: ', jqXHR.responseText);
                alert('An error occured when sending your request:\n' + jqXHR.responseText);
            }
        });
    }

}(jQuery));
