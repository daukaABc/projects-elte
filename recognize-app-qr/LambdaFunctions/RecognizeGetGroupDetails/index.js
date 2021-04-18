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


    /*var requestBody = {};
    try {
        requestBody = JSON.parse(event.body);
    } catch (err) {
        response.error("Request has no body", context.awsRequestId, callback);
    }

    if (!requestBody.groupId) {
        response.error('Group ID is required', context.awsRequestId, callback);
    }
    const groupId = requestBody.groupId;*/
    if (!event.pathParameters.groupId) {
        response.error("Group ID path parameter is required", context.awsRequestId, callback);
    }
    const groupId = event.pathParameters.groupId;

    try {
        var group = await getGroupDetails(groupId, user);
        return response.success(group, context.awsRequestId, callback);
    } catch(err) {
        return response.error(err, context.awsRequestId, callback);
    }

};

async function getGroupDetails(groupId, user) {
    var result;
    try {
        result = await ddb.get({
            TableName : 'RecognizeGroupsProd',
            Key: { id : groupId }
        }).promise();
    } catch (err) {
        throw err;
    }

    if (!result.Item.id || !result.Item.creator || !result.Item.name) {
        throw "There was an issue with the info received from the server";
    }

    if (result.Item.creator === user) {
            return result.Item;
    }

    throw "You don't have permission to get details of this group";
}
