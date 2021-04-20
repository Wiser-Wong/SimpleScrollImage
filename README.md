# SimpleScrollImage
长图 带滚动的图片控件

## 截图
![images](https://github.com/Wiser-Wong/SimpleScrollImage/blob/master/images/scrollImage1.gif)
![images](https://github.com/Wiser-Wong/SimpleScrollImage/blob/master/images/scrollImage2.gif)

## 操作指南
ScrollImageView:
属性介绍：
* siv_src：图片本地资源Id
* siv_autoPlay：是否开始自动滚动
* siv_duration：滚动时长
* siv_switch_interval_duration：切换图片间隔时长
* siv_circlePlay：是否循环滚动
* siv_delayPlayDuration：初始时延迟滚动间隔
* siv_speed：滚动速度，优先级高于滚动时长
* siv_direction：滚动方向
* siv_switch_anim_in：切换进入动画
* siv_switch_anim_out：切换退出动画

配置方法：
* setResIds     ->设置本地资源集合
* setUrls         ->设置网络图片集合
* setPlaySpeed      ->设置播放速度
* setCirclePlay       ->设置是否循环播放
* startScroll           ->开始滚动
* pauseScroll         ->暂停滚动
* resumeScroll       ->恢复滚动
* stopPlay             ->停止滚动
* resetTranslation    ->恢复图片位移
* setLoadNetImageListener    ->设置加载网络图片监听
