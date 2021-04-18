Recognize
==================================================

This is the new repository for Recognize.
Previous repo [can be found here](https://github.com/BaiLu233/Tools-of-software-projects)

|Author|Email|
|---|---|
|Andor Luo|luoandor@gmail.com|
|Ziyan Wang|wangziyan1998@gmail.com|
|Basir Doost|dm.basir@gmail.com|
|Yerdaulet Taskyn|ytaskyn@gmail.com|

Website
-----------
Link can be found [here](https://s3.eu-central-1.amazonaws.com/aws-codestar-eu-central-1-247216321180-recognize-app/public/index.html).

To access the QR function
- 1. Navigate to My Services
- 2. Click on Host

Running the project
-----------
Local deployment of APIs requires Docker and is not stable at the moment.

To deploy the static website locally, run:
```
sam local start-api
```
Then the website will be accessible on http://127.0.0.1:3000/website.


**(Deployed application can be found under "Application endpoints" in AWS CodeStar console)**



What's Here
-----------

This repo includes:

* README.md - this file
* buildspec.yml - this file is used by AWS CodeBuild to package the
  application for deployment to AWS Lambda
* template.yml - this file contains the AWS Serverless Application Model (AWS SAM) used
  by AWS CloudFormation to deploy the application to AWS Lambda and Amazon API
  Gateway.
* tests/ - this directory contains unit tests for Node components
* public/ - this directory contains the static website design to be deployed on an AWS S3 instance
* template-configuration.json - this file contains the project ARN with placeholders used for tagging resources with the project ID

Some Tips
------------------

You can access the CI/CD flow from the AWS Console -> CodeStar, using the AWS credentials you have for the project.

Master branch is reserved for working code.
For development, create a branch from master, make your changes, and then open a pull request to master. Since pull requests need to be approved by at least 1 person before being able to merge, make sure to add Reviewers, from the right side page of the PR.

You can use `./lint_files.sh` to run ESLint on all files.

RECOMMENDED: Use `./add_hook.sh` to add a Git pre-commit hook that ensures quality for API code.
