const idgenerator = require('./idgenerator');
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


    var requestBody = {};
    try {
        requestBody = JSON.parse(event.body);
    } catch (err) {
        response.error("Request has no body", context.awsRequestId, callback);
    }

    if (!requestBody.starts_at || !requestBody.ends_at) {
        response.error('Start and End time and date are required', context.awsRequestId, callback);
    }
    const starts_at = requestBody.starts_at;
    const ends_at = requestBody.ends_at;

    if (!event.pathParameters.groupId) {
        response.error("Group ID path parameter is required", context.awsRequestId, callback);
    }
    const groupId = event.pathParameters.groupId;

    const id = idgenerator.generate();

    try {
        if (await userHasPermission(user, groupId)) {
            try {
                const res = await putSession(id, groupId, starts_at, ends_at);
                response.success(id, groupId, starts_at, ends_at, context.awsRequestId, callback);
            } catch (err) {
                response.error(err.message, context.awsRequestId, callback);
            }
        }
    } catch(err) {
        return response.error(err, context.awsRequestId, callback);
    }

};

async function userHasPermission(user, groupId) {
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
        return true;
    } else {
        return false;
    }
}

function putSession(id, groupId, starts_at, ends_at) {
    return ddb.put({
        TableName: 'RecognizeSessionsProd',
        Item: {
            id: id,
            group: groupId,
            starts_at: starts_at,
            ends_at: ends_at
        },
    }).promise();
}
