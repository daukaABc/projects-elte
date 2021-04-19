const response = require('./response');

const AWS = require('aws-sdk');
const ddb = new AWS.DynamoDB.DocumentClient();


exports.handler = async (event, context, callback) => {
    console.log(event);

    if (!event.requestContext.authorizer) {
      response.error('Authorization not configured', context.awsRequestId, callback);
      return;
    }
    const user = event.requestContext.authorizer.claims['email'];


    if (!event.pathParameters.sessionId) {
        response.error('Group ID path parameter is required', context.awsRequestId, callback);
    }
    const sessionId = event.pathParameters.sessionId;

    try {
        var result = await getSessionAttendances(sessionId, user);
        return response.success(result, context.awsRequestId, callback);
    } catch(err) {
        return response.error(err, context.awsRequestId, callback);
    }

};

async function hasPermission(sessionId, user) {
    let result;
    let session;
    let groupId;

    try {
        session = await ddb.get({
            TableName : 'RecognizeSessionsProd',
            Key: { id : sessionId }
        }).promise();
    } catch (err) {
        throw err;
    }

    if (!result.Item.group) {
        throw 'There was an issue with the info received from the server';
    }

    groupId = result.Item.group;

    try {
        result = await ddb.get({
            TableName : 'RecognizeGroupsProd',
            Key: { id : groupId }
        }).promise();
    } catch (err) {
        throw err;
    }

    if (!result.Item.creator) {
        throw 'There was an issue with the info received from the server';
    }

    if (result.Item.creator === user) {
            return true;
    }

    return false;
}

async function getSessionAttendances(sessionId, user) {
    if (!hasPermission(sessionId, user)) {
        throw 'You do not have permission to get the list of sessions for this group.';
    }

    var result;
    try {
        result = await ddb.query({
            TableName : 'RecognizeAttendanceProd',
            IndexName: 'seshindex',
            KeyConditionExpression: '#s = :gpid',
            ExpressionAttributeValues: {':gpid' : sessionId},
            ExpressionAttributeNames: {'#s' : 'session'}
        }).promise();
    } catch (err) {
        throw err;
    }

    if (!result.Items) {
        throw 'There was an issue with the info received from the server';
    }

    return result;

}
