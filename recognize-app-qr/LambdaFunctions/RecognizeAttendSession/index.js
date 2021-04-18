const idgenerator = require('./idgenerator');
const response = require('./response');

const AWS = require('aws-sdk');
const ddb = new AWS.DynamoDB.DocumentClient();


exports.handler = async (event, context, callback) => {

    if (!event.requestContext.authorizer) {
      response.error('Authorization not configured', context.awsRequestId, callback);
      return;
    }
    const user = event.requestContext.authorizer.claims['email'];

    /*var requestBody = {};
    try {
        requestBody = JSON.parse(event.body);
    } catch (err) {
        response.error("Request has no body", context.awsRequestId, callback);
    }
    if (!requestBody.sessionId) {
        response.error('Session ID is required', context.awsRequestId, callback);
    }
    const sessionId = requestBody.sessionId;*/

    if (!event.pathParameters.sessionId) {
        response.error("Session ID path parameter is required", context.awsRequestId, callback);
    }
    const sessionId = event.pathParameters.sessionId;

    const id = idgenerator.generate();

    const time = new Date();
    const timeString = time.toUTCString();

    if (await isUserEnrolled(sessionId, user) == true) {
        if (await isSessionOpen(sessionId, time) == true) {
            try {
                const res = await putAttendance(id, sessionId, user, timeString);
                response.success(id, timeString, user, context.awsRequestId, callback);
            } catch (err) {
                response.error(err.message, context.awsRequestId, callback);
            }
        } else {
            response.error("You can only attend a session 20 minutes after it starts.", context.awsRequestId, callback);
        }
    } else {
            response.error("zzYou are not enrolled in this group.", context.awsRequestId, callback);
    }

};

async function putAttendance(id, sessionId, user, time) {
    return ddb.put({
        TableName: 'RecognizeAttendanceProd',
        Item: {
            id: id,
            session: sessionId,
            attender: user,
            time: time
        },
    }).promise();
}

async function isSessionOpen(sessionId, time) {
    const currentTime = time;
    var session;
    try {
        session = await ddb.get({
            TableName : 'RecognizeSessionsProd',
            Key: { id : sessionId }
        }).promise();
    } catch (err) {
        throw err;
    }
    if (!session.Item.starts_at) {
        throw "There is an issue with the this class session. Contact the class moderators.";
    }

    const startTime = new Date(session.Item.starts_at);
    const allowTime = new Date(startTime.getTime() + 20*60000); // Adding 20 minutes to start time

    if (allowTime > currentTime && currentTime > startTime) {
        return true;
    } else {
        return false;
    }
}

async function isUserEnrolled(sessionId, user) {
    var session;
    try {
        session = await ddb.get({
            TableName : 'RecognizeSessionsProd',
            Key: { id : sessionId }
        }).promise();
    } catch (err) {
        throw err;
    }

    if (!session.Item.group) {
        throw "There is an issue with the this class session. Contact the class moderators.";
    }

    const groupId = session.Item.group;

    var group;
    try {
        group = await ddb.get({
            TableName : 'RecognizeGroupsProd',
            Key: { id : groupId }
        }).promise();
    } catch (err) {
        throw err;
    }

    if (!group.Item.attenders) {
        throw "There is an issue with the this class session. Contact the class moderators.";
    }

    const attenders = group.Item.attenders;

    if (attenders.includes(user)) {
        return true;
    } else {
        return false;
    }
}
