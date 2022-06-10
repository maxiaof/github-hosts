# GitHub-Hosts

## 一、介绍
通过修改Hosts解决国内Github经常抽风访问不到

---

## 二、使用方法

### 2.1 复制下面的内容
```bash
#Github Hosts Start
#Update Time: 2022-06-10
#Project Address: https://github.com/maxiaof/github-hosts
#Update URL: https://github.com/maxiaof/github-hosts/blob/master/hosts
nullnullnull140.82.112.22 central.github.com
185.199.109.133 desktop.githubusercontent.com
185.199.110.153 assets-cdn.github.com
185.199.109.133 camo.githubusercontent.com
185.199.110.133 github.map.fastly.net
null140.82.121.3 gist.github.com
185.199.110.153 github.io
140.82.121.3 github.com
192.0.66.2 github.blog
140.82.121.5 api.github.com
185.199.110.133 raw.githubusercontent.com
185.199.109.133 user-images.githubusercontent.com
185.199.108.133 favicons.githubusercontent.com
null185.199.111.133 avatars4.githubusercontent.com
nullnullnullnull185.199.109.133 avatars.githubusercontent.com
nullnullnullnullnullnullnullnullnullnullnullnullnullnull#Github Hosts End

```
最后更新时间：`2022-06-10`

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
