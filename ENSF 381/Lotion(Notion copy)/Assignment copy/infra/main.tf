terraform {
  required_providers {
    aws = {
      version = ">= 4.0.0"
      source = "hashicorp/aws"
    }
  }
}

# specify the provider region
provider "aws" {
  region = "ca-central-1"
}

# locals block to declare constants
locals {
  function_get_notes = "get-notes-30147405"
  function_save_note = "save-note-30147405"
  function_delete_note = "delete-note-30147405"
  handler_name  = "main.lambda_handler"
  artifact_name = "artifact.zip"
}

# create a role for the Lambda function to assume
# every service on AWS that wants to call other AWS services should first assume a role and
# then any policy attached to the role will give permissions
# to the service so it can interact with other AWS services
# see the docs: https://registry.terraform.io/providers/hashicorp/aws/latest/docs/resources/iam_role
resource "aws_iam_role" "lambda" {
  name               = "iam-for-lambda"
  assume_role_policy = <<EOF
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Action": "sts:AssumeRole",
      "Principal": {
        "Service": "lambda.amazonaws.com"
      },
      "Effect": "Allow",
      "Sid": ""
    }
  ]
}
EOF
}

# create a policy for publishing logs to CloudWatch
# see the docs: https://registry.terraform.io/providers/hashicorp/aws/latest/docs/resources/iam_policy
resource "aws_iam_policy" "logs" {
  name        = "lambda-logging"
  description = "IAM policy for logging from a lambda"

  policy = <<EOF
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Action": [
        "logs:CreateLogGroup",
        "logs:CreateLogStream",
        "logs:PutLogEvents",
        "dynamodb:PutItem",
        "dynamodb:DeleteItem",
        "dynamodb:GetItem",
        "dynamodb:Query"
      ],
      "Resource": ["arn:aws:logs:*:*:*", "${aws_dynamodb_table.lotion-30142179.arn}"],
      "Effect": "Allow"
    }
  ]
}
EOF
}

# attach the above policy to the function role
# see the docs: https://registry.terraform.io/providers/hashicorp/aws/latest/docs/resources/iam_role_policy_attachment
resource "aws_iam_role_policy_attachment" "lambda_logs" {
  role       = aws_iam_role.lambda.name
  policy_arn = aws_iam_policy.logs.arn
}

# dynamodb table
resource "aws_dynamodb_table" "lotion-30142179" {
  name         = "lotion-30142179"
  billing_mode = "PROVISIONED"

  # up to 8KB read per second (eventually consistent)
  read_capacity = 1

  # up to 1KB per second
  write_capacity = 1

  # we only need a student id to find an item in the table; therefore, we 
  # don't need a sort key here
  hash_key = "email"
  range_key = "id"

  # the hash_key data type is string
  attribute {
    name = "email"
    type = "S"
  }

  attribute {
    name = "id"
    type = "S"
  }
}

# creating archive file for get-notes
data "archive_file" "get_notes" {
  type = "zip"
  source_file = "../functions/get-notes/main.py"
  output_path = "../functions/get-notes/artifact.zip"
}

# creating archive file for save-note
data "archive_file" "save_note" {
  type = "zip"
  source_file = "../functions/save-note/main.py"
  output_path = "../functions/save-note/artifact.zip"
}

# create archive file for delete-note
data "archive_file" "delete_note" {
  type = "zip"
  source_file = "../functions/delete-note/main.py"
  output_path = "../functions/delete-note/artifact.zip"
}

# create a Lambda function for get notes file
# see the docs: https://registry.terraform.io/providers/hashicorp/aws/latest/docs/resources/lambda_function
resource "aws_lambda_function" "lambda_get_notes" {
  role             = aws_iam_role.lambda.arn
  function_name    = local.function_get_notes
  handler          = local.handler_name
  filename         = "../functions/get-notes/${local.artifact_name}"
  source_code_hash = data.archive_file.get_notes.output_base64sha256

  # see all available runtimes here: https://docs.aws.amazon.com/lambda/latest/dg/API_CreateFunction.html#SSS-CreateFunction-request-Runtime
  runtime = "python3.9"
}

# create a Lambda function for save note file
# see the docs: https://registry.terraform.io/providers/hashicorp/aws/latest/docs/resources/lambda_function
resource "aws_lambda_function" "lambda_save_note" {
  role             = aws_iam_role.lambda.arn
  function_name    = local.function_save_note
  handler          = local.handler_name
  filename         = "../functions/save-note/${local.artifact_name}"
  source_code_hash = data.archive_file.save_note.output_base64sha256

  # see all available runtimes here: https://docs.aws.amazon.com/lambda/latest/dg/API_CreateFunction.html#SSS-CreateFunction-request-Runtime
  runtime = "python3.9"
}

# create a Lambda function for delete note file
# see the docs: https://registry.terraform.io/providers/hashicorp/aws/latest/docs/resources/lambda_function
resource "aws_lambda_function" "lambda_delete_note" {
  role             = aws_iam_role.lambda.arn
  function_name    = local.function_delete_note
  handler          = local.handler_name
  filename         = "../functions/delete-note/${local.artifact_name}"
  source_code_hash = data.archive_file.delete_note.output_base64sha256

  # see all available runtimes here: https://docs.aws.amazon.com/lambda/latest/dg/API_CreateFunction.html#SSS-CreateFunction-request-Runtime
  runtime = "python3.9"
}

# create a Function URL for Lambda get notes
# see the docs: https://registry.terraform.io/providers/hashicorp/aws/latest/docs/resources/lambda_function_url
resource "aws_lambda_function_url" "url_get_notes" {
  function_name      = aws_lambda_function.lambda_get_notes.function_name
  authorization_type = "NONE"

  cors {
    allow_credentials = true
    allow_origins     = ["*"]
    allow_methods     = ["GET"]
    allow_headers     = ["*"]
    expose_headers    = ["keep-alive", "date"]
  }
}

# create a Function URL for Lambda save note
resource "aws_lambda_function_url" "url_save_note" {
  function_name      = aws_lambda_function.lambda_save_note.function_name
  authorization_type = "NONE"

  cors {
    allow_credentials = true
    allow_origins     = ["*"]
    allow_methods     = ["POST"]
    allow_headers     = ["*"]
    expose_headers    = ["keep-alive", "date"]
  }
}

# create a Function URL for Lambda delete note
resource "aws_lambda_function_url" "url_delete_note" {
  function_name      = aws_lambda_function.lambda_delete_note.function_name
  authorization_type = "NONE"

  cors {
    allow_credentials = true
    allow_origins     = ["*"]
    allow_methods     = ["DELETE"]
    allow_headers     = ["*"]
    expose_headers    = ["keep-alive", "date"]
  }
}

# show the Function URL after creation
output "lambda_url_get_notes" {
  value = aws_lambda_function_url.url_get_notes.function_url
}

output "lambda_url_save_note" {
  value = aws_lambda_function_url.url_save_note.function_url
}

output "lambda_url_delete_note" {
  value = aws_lambda_function_url.url_delete_note.function_url
}