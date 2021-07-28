#!/bin/bash

# Setting commands and variables

outfile={{ report_output_directory }}/bitbucket_user_lastlogin.csv
export PGPASSWORD={{ bitbucket_db_pass }}
psql='psql -A -q -t -d {{ bitbucket_db_name }} -h {{ bitbucket_db_host_slave }} -U {{ bitbucket_db_user }}'
# The instance url
bitbucket_instance_url={{ bitbucket_vhost }}

bitbucket_active_users=`$psql -c "SELECT distinct cu.id FROM cwd_user As cu LEFT JOIN cwd_membership AS cm  ON cu.directory_id=cm.directory_id AND cu.lower_user_name=cm.lower_child_name AND cm.membership_type='GROUP_USER' LEFT JOIN cwd_user_attribute As cua ON cu.ID = cua.user_id WHERE is_active = 'T' AND cm.lower_parent_name='stash-users';"`

# Clear the CSV and print the headers
rm -f {{ report_output_directory }}/bitbucket_user_lastlogin.csv
printf "bitbucket_instance_url,bitbucket_userid,username,email_address,full_name,is_active,seen_first,last_login\n" > $outfile

# Loop over all active users to get the required information

for active_user in $bitbucket_active_users
  do
    username=`$psql -c "select lower_user_name from cwd_user where id = '$active_user'"`
    email=`$psql -c "select lower_email_address from cwd_user where id = '$active_user'"`
    full_name=`$psql -c "select lower_display_name from cwd_user where id = '$active_user'"`
    is_active=`$psql -c "select is_active from cwd_user where id = '$active_user'"`
    seen_first=`$psql -c "select created_date from cwd_user where id = '$active_user'"`
    last_login=`$psql -c "SELECT distinct to_timestamp(CAST(attribute_value AS BIGINT)/1000) FROM cwd_user As cu LEFT JOIN cwd_user_attribute As cua ON cu.ID = cua.user_id where cu.id = '$active_user' AND cua.attribute_name = 'lastAuthenticationTimestamp'"`

    printf "$bitbucket_instance_url,$active_user,$username,$email,$full_name,$is_active,$seen_first,$last_login\n" >> $outfile
done
