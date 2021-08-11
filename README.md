# 简介

该项目是学习android项目mvvm框架的编写。
前端时间通过学习别人mvp框架的项目和 mvvm的框架组件（livedata，lifecycle）
然后通过自己学习环信的demo app 将自己学习到知识写一个简单的项目。

  项目尚未完成


### 主要运用的知识

- tomcat服务器简单编写
- BottomNavigationView与fragment的使用
- Base组件抽象类的提取（键盘的隐藏，沉浸式布局，各种网络不同的数据加载等）
- Retrofit与Okhttp的使用
- jetpack控件的使用（toolbar，cardview等）
- viewmodel 与 repository 的加载更多使用
- 环信的聊天接入
- 数据持久化 SharedPreferences使用
- Builder建造者模式学习
- 等等

### 主要功能

登录界面：登录，注册。
主页：首页（商品），交流圈（动态信息），宠物界面（宠物信息），会话界面（聊天系统），我的（自己的动态信息）
目前只简单的做到：
登录界面（没有注册，服务器已经过期了 账号test1或test2 密码 111）之前做登录界面的（服务器没过期的时候不是很懂rest所以服务器做的一知半解）

首页（商品）：用了之前学习别人mvp项目用的api用淘宝做。（电商平台，推自己的商品，领券等一系列接入）

交流圈：类似朋友圈，（服务器过期，图片都不见了，所以打算用别人图片，视频用别人的。）---发动态界面，之前什么都不懂，图片优化，视频压缩优化就往服务器放，总疑惑为什么这么慢

会话界面：只简单的添加会话界面，（通过关注列表来添加， 进入他人的动态界面 进行和他人会话）

我的：自己的动态信息，优惠圈，和设置界面。

