#!/bin/sh
#Enter your bitbucket login credentials as we are trying to execute the bitbucket rest api 
read -p "Enter your bitbucket username:" user
read -s -p  "Enter Bitbucket Password:" pass
# check if jq is installed 
which jq
if [ "$?" != "0" ] 
then
 echo "you should install jq using: brew install jq\n";
 brew install jq
exit 1
 fi
#passing the project key as the first argument 
if [ "$1" != '' ]
then
#Executing the Bitbucket rest api which results the json data including the Project permission details
curl -s -u $user:$pass https://bitbucket.wdc.com/rest/api/1.0/projects/$1/permissions/users?limit=1000 > project.json
#Fetching the user count from the json data
count=($(jq '.size' project.json))
echo "\nPROJECT_ADMINS:\n"
#Iterating through the Json data,validating against the permission = "PROJECT_ADMIN" and retrive the user having PROJECT_ADMIN permission
for ((i = 0; i < $count; i++)); do
    a=($(jq ".values[$i].permission" project.json))
    b='"PROJECT_ADMIN"'
    if [ "$a" == "$b" ]; then
      jq ".values[$i].user.name" project.json
fi
done
else
echo "\nPlease pass the project key"
fi
continue
#passing the repository name as the second argument 
if [ "$2" != '' ]
then 
#Executing the Bitbucket rest api which results the json data including the Repository permission details
curl -s -u $user:$pass https://bitbucket.wdc.com/rest/api/1.0/projects/$1/repos/$2/permissions/users?limit=1000 > repo.json
count=($(jq '.size' repo.json))
echo "\nREPOSITORY_ADMINS:\n"   
#Iterating through the Json data,validating against the permission = "REPO_ADMIN" and retrive the user having REPO_ADMIN permission
for ((i = 0; i < $count; i++)); do
    a=($(jq ".values[$i].permission" repo.json))
    b='"REPO_ADMIN"'
    if [ "$a" == "$b" ]; then
     jq ".values[$i].user.name" repo.json
    fi
done
rm -rf *.json
fi
