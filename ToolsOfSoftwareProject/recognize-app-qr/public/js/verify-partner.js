/*global Recognize _config*/

var Recognize = window.Recognize || {};

(function recognizeScopeWrapper($) {
    var authToken;
    
    var poolData = {
        UserPoolId: _config.cognito.userPoolId,
        ClientId: _config.cognito.userPoolClientId
    };

    var userPool;

    if (!(_config.cognito.userPoolId &&
          _config.cognito.userPoolClientId &&
          _config.cognito.region)) {
        $('#noCognitoMessage').show();
        return;
    }

    userPool = new AmazonCognitoIdentity.CognitoUserPool(poolData);
    
    Recognize.authToken = new Promise(function fetchCurrentAuthToken(resolve, reject) {
        var cognitoUser = userPool.getCurrentUser();

        if (cognitoUser) {
            cognitoUser.getSession(function sessionCallback(err, session) {
                if (err) {
                    reject(err);
                } else if (!session.isValid()) {
                    resolve(null);
                } else {
                    resolve(session.getIdToken().getJwtToken());
                }
            });
        } else {
            resolve(null);
        }
    });

    Recognize.authToken.then(function setAuthToken(token) {
        if (token) {
            authToken = token;
        } else {
            window.location.href = 'aws-codestar-eu-central-1-247216321180-recognize-app/public/signin.html';
        }
    }).catch(function handleTokenError(error) {
        alert(error);
        window.location.href = 'aws-codestar-eu-central-1-247216321180-recognize-app/public/signin.html';
    });
    function sendVerification(email, completeRequest) {
        console.log('SENDING THE POST REQUEST');
        $.ajax({
            method: 'POST',
            url: _config.api.invokeUrl + '/verify',
            headers: {
                Authorization: authToken
            },
            data: JSON.stringify({
                to: email
            }),
            contentType: 'application/json',
            success: completeRequest,
            error: function ajaxError(jqXHR, textStatus, errorThrown) {
                console.error('Error sending partner request: ', textStatus, ', Details: ', errorThrown);
                console.error('Response: ', jqXHR.responseText);
                alert('An error occured when sending your request:\n' + jqXHR.responseText);
            }
        });
    }

    function completeRequest(result) {
        var from;
        var to;
        var time;
        console.log('Response received from API: ', result);
        from = result.From;
        //pronoun = unicorn.Gender === 'Male' ? 'his' : 'her';
        to = result.To;
        displayUpdate('A partner request was sent to ' + to + '.');
    }

    // Register click handler for #request button
    $(function onDocReady() {
        $('#verifyPartnerForm').submit(handleVerify);
        console.log('authenticating');
        Recognize.authToken.then(function updateAuthMessage(token) {
            if (token) {
                console.log('You are authenticated');
            }
        });

        if (!_config.api.invokeUrl) {
            $('#noApiMessage').show();
        }
    });

    function handleVerify(event) {
        var email = $('#emailPartnerVerification').val();
        event.preventDefault();
        sendVerification(email,
            function signinSuccess() {
                console.log('Successfully Sent');
                window.location.href = 'recognize.html';
            }
        );
    }

}(jQuery));
