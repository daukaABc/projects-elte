#!/bin/sh


JS_FILES=$(find ./public ./LambdaFunctions | grep ".*\.js$" | grep -v ".*vendor.*")

for FILE in $JS_FILES
do
  eslint "$FILE"
  if [[ "$?" == 0 ]]; then
    printf "\t\033[32mESLint Passed: $FILE\033[0m \n"
  else
    printf "\t\033[41mESLint Failed: $FILE\033[0m \n"
  fi
done
