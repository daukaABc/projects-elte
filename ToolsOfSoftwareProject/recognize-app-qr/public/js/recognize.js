/* global Recognize _config*/

var Recognize = window.Recognize || {};

(function recognizeScopeWrapper($) {
    var authToken;

    Recognize.authToken.then((token) => {
        if (token) {
            authToken = token;
            console.log(`Your auth token is: ${token}`);
        } else {
            window.location.href = 'aws-codestar-eu-central-1-247216321180-recognize-app/public/signin.html';
        }
    }).catch((error) => {
        alert(error);
        window.location.href = 'aws-codestar-eu-central-1-247216321180-recognize-app/public/signin.html';
    });


    // Processes the result json object received from API to entries in Teaching Groups table
    function listUserCreatedGroups(result) {
        //console.log('Response received from API: ', result);
        // The code to put result in the table...
        for (var i = 0; i < result.GroupsCreated.length; i++){
            createdGroups(result.GroupsCreated[i]);
        }
    }

    function createdGroups(rowData){
        var row = $("<tr/>");
        $('#createdTable').append(row);
        row.append($("<td onclick=window.location='group.html?id=" + rowData.id + "'; style='color:red'; >" + rowData.name + "</td>"));
        row.append($("<td>" + rowData.creator + "</td>"));
    }

    function listUserAttendingGroups(result) {
       //console.log('Response received from API: ', result);
        // The code to put result in the table...
        for (var i = 0; i < result.GroupsAttending.length; i++){
            attendingGroups(result.GroupsAttending[i]);
        }
    }

    function attendingGroups(rowData){
        var row = $("<tr/>");
        $('#attendingTable').append(row);
        row.append($("<td>" + rowData.name + "</td>"));
        row.append($("<td>" + rowData.creator + "</td>"));
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
    $(() => {
        // This is for demonstrating how to call the API
        $.ajax({
            method: 'GET',
            url: `${_config.api.invokeUrl}/user/created-groups`,
            headers: {
                Authorization: authToken
            },
            contentType: 'application/json',
            success: listUserCreatedGroups,
            error: function ajaxError(jqXHR, textStatus, errorThrown) {
                console.error('Error requesting attention: ', textStatus, ', Details: ', errorThrown);
                console.error('Response: ', jqXHR.responseText);
                alert(`An error occured when sending your request:\n${jqXHR.responseText}`);
            }
        });
    });

    $(() => {
        $.ajax({
            method: 'GET',
            url: `${_config.api.invokeUrl}/user/attending-groups`,
            headers: {
                Authorization: authToken
            },
            contentType: 'application/json',
            success: listUserAttendingGroups,
            error: function ajaxError(jqXHR, textStatus, errorThrown) {
                console.error('Error requesting attention: ', textStatus, ', Details: ', errorThrown);
                console.error('Response: ', jqXHR.responseText);
                alert(`An error occured when sending your request:\n${jqXHR.responseText}`);
            }
        });
    });

    $(() => {
         $('#newGroupForm').submit(handleNewGroup);
         $('#enrollGroupFrom').submit(handleEnrollGroup);

    });

    function newGroupSuccess(result) {
        location.reload();
    }

    function handleNewGroup(event) {
        var name = $('#newGroupName').val();
        event.preventDefault();
        $.ajax({
            method: 'POST',
            url: `${_config.api.invokeUrl}/group`,
            headers: {
                Authorization: authToken
            },
            data: JSON.stringify({
                name: name
            }),
            contentType: 'application/json',
            success: newGroupSuccess,
            error: function ajaxError(jqXHR, textStatus, errorThrown) {
                console.error('Error requesting attention: ', textStatus, ', Details: ', errorThrown);
                console.error('Response: ', jqXHR.responseText);
                alert(`An error occured when sending your request:\n${jqXHR.responseText}`);
            }
        });
    }

    function enrollGroupSuccess(result) {
        location.reload();
    }

    function handleEnrollGroup(event) {
        var enrollGroupId = $('#enrollGroupId').val();
        event.preventDefault();
        $.ajax({
            method: 'PUT',
            url: `${_config.api.invokeUrl}/group/${enrollGroupId}`,
            headers: {
                Authorization: authToken
            },
            contentType: 'application/json',
            success: newGroupSuccess,
            error: function ajaxError(jqXHR, textStatus, errorThrown) {
                console.error('Error requesting attention: ', textStatus, ', Details: ', errorThrown);
                console.error('Response: ', jqXHR.responseText);
                alert(`An error occured when sending your request:\n${jqXHR.responseText}`);
            }
        });
    }

}(jQuery));
