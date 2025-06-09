# GitHub-Hosts

## 一、介绍
通过修改Hosts解决国内Github经常抽风访问不到

---

## 二、使用方法

### 2.1 复制下面的内容
```bash
{[hosts]}
```
最后更新时间：`{[update_time]}`

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
[![Powered by DartNode](https://dartnode.com/branding/DN-Open-Source-sm.png)](https://dartnode.com "Powered by DartNode - Free VPS for Open Source")
