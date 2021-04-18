const AWS = require('aws-sdk');
const ddb = new AWS.DynamoDB.DocumentClient();


exports.handler = async (event, context, callback) => {
    console.log(event);


    if (event.request.userAttributes.email && event.request.userAttributes.given_name && event.request.userAttributes.family_name) {
        const email = event.request.userAttributes.email;
        const fname = event.request.userAttributes.given_name;
        const lname = event.request.userAttributes.family_name;

        try {
            const res = await putUser(email, fname, lname);
            context.done(null, event);
        } catch (err) {
            context.done(err);
        }

    } else {
        // Nothing to do, the user's email ID is unknown
            context.done(null, event);
    }
};

function putUser(email, fname, lname) {
    return ddb.put({
        TableName: 'RecognizeUsersProd',
        Item: {
            email,
            fname,
            lname,
            groups_attending: [],
            groups_moderating: [],
            groups_created: []
        },
    }).promise();
}
