# GitHub-Hosts

## 一、介绍
通过修改Hosts解决国内Github经常抽风访问不到

---

## 二、使用方法

### 2.1 复制下面的内容
```bash
#Github Hosts Start
#Update Time: 2025-05-16
#Project Address: https://github.com/maxiaof/github-hosts
#Update URL: https://raw.githubusercontent.com/maxiaof/github-hosts/master/hosts
140.82.114.25 alive.github.com
140.82.112.26 live.github.com
185.199.110.154 github.githubassets.com
140.82.113.22 central.github.com
185.199.110.133 desktop.githubusercontent.com
185.199.111.133 camo.githubusercontent.com
185.199.109.133 github.map.fastly.net
146.75.121.194 github.global.ssl.fastly.net
140.82.121.3 gist.github.com
185.199.111.153 github.io
140.82.121.3 github.com
192.0.66.2 github.blog
140.82.121.6 api.github.com
185.199.108.133 raw.githubusercontent.com
185.199.109.133 user-images.githubusercontent.com
185.199.108.133 favicons.githubusercontent.com
185.199.110.133 avatars5.githubusercontent.com
185.199.109.133 avatars4.githubusercontent.com
185.199.111.133 avatars3.githubusercontent.com
185.199.110.133 avatars2.githubusercontent.com
185.199.108.133 avatars1.githubusercontent.com
185.199.110.133 avatars0.githubusercontent.com
185.199.111.133 avatars.githubusercontent.com
140.82.121.9 codeload.github.com
52.217.174.129 github-cloud.s3.amazonaws.com
54.231.172.73 github-com.s3.amazonaws.com
54.231.163.97 github-production-release-asset-2e65be.s3.amazonaws.com
3.5.9.136 github-production-user-asset-6210df.s3.amazonaws.com
3.5.30.84 github-production-repository-file-5c1aeb.s3.amazonaws.com
185.199.110.153 githubstatus.com
140.82.113.18 github.community
51.137.3.17 github.dev
140.82.114.22 collector.github.com
13.107.42.16 pipelines.actions.githubusercontent.com
185.199.108.133 media.githubusercontent.com
185.199.111.133 cloud.githubusercontent.com
185.199.109.133 objects.githubusercontent.com
#Github Hosts End

```
最后更新时间：`2025-05-16`

## 2.2 修改 hosts 文件
hosts 文件在不同系统位置不一，详情如下：
- Windows 系统：`C:\Windows\System32\drivers\etc\hosts`。
- Mac（苹果电脑）系统：`/etc/hosts`。
- Linux 系统：`/etc/hosts`。

修改方法，把2.1的内容复制到文本末尾：

1. Windows 使用记事本。
2. Linux、Mac 使用 Root 权限：`sudo vi /etc/hosts`。

#### 2.3 激活生效
大部分情况下是直接生效，如未生效可尝试下面的办法，刷新 DNS：

1. Windows：在 CMD 窗口输入：`ipconfig /flushdns`
2. Mac 命令：`sudo killall -HUP mDNSResponder`
3. Linux 命令：`sudo nscd restart`

**Tips：** 如以上刷新不好使，请重启尝试
