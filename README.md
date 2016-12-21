智能云插座
=============

    此公版开源App已不再维护，机智云推出了物联开源框架，并提供了其他开源案例供参考。机智云公版开源App项目地址：

    机智云物联开源框架Android Studio项目 https://github.com/gizwits/GOpenSource_AppKit_Android_AS
    机智云物联开源框架Eclipse项目 https://github.com/gizwits/GizOpenSource_AppKit_Android
    机智云智能灯2代Eclipse项目 https://github.com/gizwits/Gizwits-SmartBuld_Android
    机智云Gokit的Eclipse项目 https://github.com/gizwits/gokit-android
    机智云Gokit的APICloud项目 https://github.com/gizwits/gokit_demo_in_apicloud

    我们在机智云社区、QQ群提供技术支持，意见反馈渠道。机智云社区网址：http://club.gizwits.com/forum.php

    QQ群：
    G1机智云物联网云服务 104975951
    G2机智云物联网云服务 491509598
    G3机智云物联网云服务 287087942

    意见反馈：http://form.mikecrm.com/s1ZJxj

Gizwits Power Socket Android Demo App

机智云 SDK 版本号

    1.6.1.15123015

使用说明

    使用机智云开源APP之前，需要先在机智云开发平台创建您自己的产品和应用。
    开源App需要使用您申请的AppId、AppSecret以及您自己的产品ProductKey才能正常运行。
    具体申请流程请参见：http://docs.gizwits.com/hc/。
    上述信息申请好之后，在代码中请找到"your_app_id"、"your_app_secret"、"your_product_key"字符串做相应的替换。


功能介绍

    这是一款使用XPGWifiSDK的开源代码示例APP，可以帮助开发者快速入手，使用XPGWifiSDK开发连接机智云的物联APP。该APP针对的是智能家电中的插座类产品。包括了以下几点插座常用功能：

    ▪ 插座电源的开关
    ▪ 插座定时开关
    ▪ 插座倒计时开关
    ▪ 定时周重复

    如果开发者希望开发的设备与以上功能类似，可参考或直接使用该APP进行修改进行快速开发自己的智能家电App。

    以下功能是机智云开源App的几个通用功能，除UI有些许差异外，流程和代码都几乎一致：

    ▪	机智云账户系统的注册、登陆、修改密码、注销等功能
    ▪	机智云设备管理系统的AirLink配置入网、SoftAP配置入网，设备与账号绑定、解绑定，修改设备别名等功能
    ▪	机智云设备的登陆，控制指令发送，状态接收，设备连接断开等功能

    另外，因为该项目并没有相对应的实体硬件设备供开发者使用，因此还提供了扫描虚拟设备功能，通过扫描机智云实验室内相对应的虚拟设备，可进行设备的绑定和控制等功能。同时可免费申请gokit进行设备的配置入网和绑定等流程。


项目依赖和安装

    ▪	XPGWifiSDK的jar包和支持库
    登录机智云官方网站http://gizwits.com的开发者中心，下载并解压最新版本的SDK。
    下载后，将解压后的目录拷贝到复制到 Android 项目 libs 目录即可。

    ▪	Gokit设备
    使用机智云开发的Gokit设备并烧写相对应的产品标识码，可以体验设备配置上线等功能。

    ▪	虚拟设备
    使用机智云实验室的相对应虚拟设备，可以体验设备指令收发，状态的获取等功能。

项目工程结构

    ▪	包结构说明
    com.gizwits.powersocket                              -智能云插座独有代码，包含控制部分和侧边栏部分
    com.gizwits.powersocket.activity.control             -智能云插座控制界面activity
    com.gizwits.powersocket.activity.slipbar             -智能云插座侧边栏activity
    com.gizwits.framework                                -机智云设备开源APP框架,包含除控制界面Activity外的代码，暂时机智云实验室中的其他开源APP所用框架一致
    com.gizwits.framework.activity                       -机智云设备开源APP框架相关activity
    com.gizwits.framework.adapter                        -机智云设备开源APP框架相关数据适配器
    com.gizwits.framework.config                         -机智云设备开源APP框架配置类
    com.gizwits.framework.entity                         -机智云设备开源APP框架实体类
    com.gizwits.framework.sdk                            -机智云设备开源APP框架操作SDK相关类
    com.gizwits.framework.utils                          -机智云设备开源APP框架工具类
    com.gizwits.framework.widget                         -机智云设备开源APP框架自定义控件
    com.gizwits.framework.XpgApplication                 -机智云设备开源APP框架自定义Application
    com.xpg.XXX                                          -机智云通用开发API
    zxing                                                -第三方二维码扫描控件


使用流程

    ▪	虚拟设备＋app使用流程（体验指令发、状态获取等流程）

    1.在机智云官网上注册并登录帐号
    2.使用机智云实验室里面的智能云插座启动虚拟设备
    3.在app上注册并登录帐号
    4.通过扫描网页上的二维码添加虚拟设备
    5.进入控制界面与虚拟设备进行交互

    ▪	gokit＋app使用流程（体验配置、绑定实体设备等流程）

    1.免费申请gokit，http://gizwits.com/zh-cn/gokit
    2.下载gokit开源代码并按说明替换为智能云插座的产品标识码
    3.在app上注册并登录帐号
    4.通过我要配置gokit按钮，使用Airlink或SoftAP模式配置gokit入网
    5.绑定gokit
    6.进入控制界面

问题反馈

    您可以给机智云的技术支持人员发送邮件，反馈您在使用过程中遇到的任何问题。
    邮箱：janel@gizwits.com
