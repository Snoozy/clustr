#!/bin/bash

echo 'Generating package...'

pack_path=$(activator dist | tee /dev/tty | awk '/package is ready/ {print $7;}')

package_path=${pack_path%????}

fullfile=$(basename ${package_path})

filename=${fullfile%????}

echo "Package is located at ${package_path}"

server_ips="52.88.179.215"

echo 'Starting deployment...'

if [ -e "$package_path" ]; then
    for server in "$server_ips"
    do
        echo "Deploying to ${server}."
        scp "$package_path" ubuntu@${server}:/home/ubuntu/clustr.zip
        ssh ubuntu@${server} bash -c "'
            unzip -o clustr.zip
            sudo rm -rf clustr-backup/
            mv clustr/ clustr-backup/
            mv ${filename}/ clustr/
            chmod 755 ./clustr/bin/clustr
            rm clustr.zip
            sudo kill \$(head -n 1 /home/ubuntu/clustr-backup/RUNNING_PID)
            sudo ./clustr/bin/clustr -J-Xms128M -J-Xmx750M -J-server -Dconfig.file=/home/ubuntu/prod.conf -Dhttp.port=80 &
            disown
        '"
    done
else
    echo 'Deployment error.'
fi