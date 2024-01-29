#!/bin/bash

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

LOCAL_GITHUB_HOSTS_PATH="$DIR/hosts"

if [ ! -f "$LOCAL_GITHUB_HOSTS_PATH" ]; then
    echo "GitHub hosts文件不存在于脚本所在目录"
    exit 1
fi

sudo cp /etc/hosts /etc/hosts.backup

while IFS= read -r line
do
    ip=$(echo $line | awk '{print $1}')
    domain=$(echo $line | awk '{print $2}')

    if [[ -n "$line" && ! $line =~ ^# ]]; then
        if grep -q "$domain" /etc/hosts; then
            sudo sed -i".bak" "/$domain/d" /etc/hosts
        fi
        echo "$line" | sudo tee -a /etc/hosts > /dev/null
    fi
done < "$LOCAL_GITHUB_HOSTS_PATH"

case "$(uname -s)" in
    Darwin)
        echo "刷新Mac DNS缓存..."
        sudo killall -HUP mDNSResponder
        ;;
    Linux)
        echo "刷新Linux DNS缓存..."
        sudo systemctl restart nscd
        ;;
    *)
        echo "未知操作系统，跳过刷新DNS缓存"
        ;;
esac

echo "GitHub Hosts 更新完成!"
