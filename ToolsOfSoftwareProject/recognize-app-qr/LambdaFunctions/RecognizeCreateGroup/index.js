const idgenerator = require('./idgenerator');
const response = require('./response');

const AWS = require('aws-sdk');
const ddb = new AWS.DynamoDB.DocumentClient();


exports.handler = async (event, context, callback) => {
    console.log(event);
    var requestBody = {};
    try {
        requestBody = JSON.parse(event.body);
    } catch (err) {
        response.error("Request has no body", context.awsRequestId, callback);
    }

    if (!event.requestContext.authorizer) {
        response.error('Authorization not configured', context.awsRequestId, callback);
        return;
    }

    const id = idgenerator.generate();
    console.log('Received event (', id, '): ', event);

    const user = event.requestContext.authorizer.claims['email'];
    if (!requestBody.name) {
        response.error('Group name is required', context.awsRequestId, callback);
    }
    const name = requestBody.name;
    try {
        await putGroup(id, user, name);
        await updateUser(user, id);
        response.success(id, name, user, context.awsRequestId, callback);
    } catch (err) {
        response.error(err.message, context.awsRequestId, callback);
    }

};

function putGroup(id, user, name) {
    return ddb.put({
        TableName: 'RecognizeGroupsProd',
        Item: {
            id: id,
            name: name,
            creator: user,
            attenders: [],
            moderators: []
        },
    }).promise();
}

function updateUser(user, id) {
    return ddb.update({
        TableName: 'RecognizeUsersProd',
        Key: { email : user },
        UpdateExpression: 'set groups_created = list_append(groups_created, :vals)',
        ExpressionAttributeValues: {
            ':vals' : [id]
        }
    }).promise();
}
