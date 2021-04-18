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


    if (!event.pathParameters.groupId) {
        response.error('Group ID path parameter is required', context.awsRequestId, callback);
    }
    const groupId = event.pathParameters.groupId;

    try {
        var sessions = await getGroupSessions(groupId, user);
        return response.success(sessions, context.awsRequestId, callback);
    } catch(err) {
        return response.error(err, context.awsRequestId, callback);
    }

};

async function hasPermission(groupId, user) {
    var result;
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

async function getGroupSessions(groupId, user) {
    if (!hasPermission(groupId, user)) {
        throw 'You do not have permission to get the list of sessions for this group.';
    }

    var result;
    try {
        result = await ddb.query({
            TableName : 'RecognizeSessionsProd',
            IndexName: 'group-index',
            KeyConditionExpression: '#gp = :gpid',
            ExpressionAttributeValues: {':gpid' : groupId},
            ExpressionAttributeNames: {'#gp' : 'group'}
        }).promise();
    } catch (err) {
        throw err;
    }

    if (!result.Items) {
        throw 'There was an issue with the info received from the server';
    }

    return result;

}
