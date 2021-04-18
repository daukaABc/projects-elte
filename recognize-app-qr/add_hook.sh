#!/bin/sh


mkdir .git/hooks
cp ./hooks/pre-commit .git/hooks

echo "Successfully added pre-commit hook for Lambdas."
