##  RecyclerView的LayoutManager，轻松实现各种炫酷、特殊效果，再也不怕产品经理为难！
### 博客详情： https://blog.csdn.net/u011387817/article/details/81875021

### 使用方式:
#### 添加依赖：
```
implementation 'com.wuyr:pathlayoutmanager:1.0.3'
```

### APIs:
|Method|Description|
|------|-----------|
|updatePath(Path path)|更新Path|
|setItemOffset(int itemOffset)|设置Item间距 (单位: px)|
|setOrientation(int orientation)|设置滑动方向:<br>**RecyclerView.HORIZONTAL** (水平滑动)<br>**RecyclerView.VERTICAL** (垂直滑动)|
|setScrollMode(int mode)|设置滚动模式:<br>**SCROLL_MODE_NORMAL** (普通模式)<br>**SCROLL_MODE_OVERFLOW** (允许溢出)<br>**SCROLL_MODE_LOOP** (无限循环)<br>|
|setItemDirectionFixed(boolean isFixed)|设置Item是否保持垂直|
|setAutoSelect(boolean isAutoSelect)|设置是否开启自动选中效果|
|setAutoSelectFraction(float position)|设置自动选中的目标落点 (0~1)|
|setFlingEnable(boolean enable)|设置惯性滚动是否开启|
|setCacheCount(int count)|设置Item缓存个数|
|setItemScaleRatio(float... ratios)|设置平滑缩放比例<br>**ratios**: 缩放比例， 数组长度必须是双数，<br>**偶数索引**表示要**缩放的比例**<br>**奇数索引**表示在**路径上的位置** (0~1)<br>奇数索引必须要递增，即越往后的数值应越大<br>例如：<br> **setItemScaleRatio(0.8, 0.5)** <br>表示在路径的50%处把Item缩放到原来的80%<br>**setItemScaleRatio(0, 0, 1, 0.5, 0, 1)** <br>表示在起点处的Item比例是原来的0%，在路径的50%处会恢复原样<br>到路径终点处会缩小到0%|
|scrollToPosition(int position)|将目标Item滚动到自动选中的落点(setAutoSelectFraction)<br>例如 setAutoSelectFraction(0) 则滚动到Path的起点处<br>若为1，则滚动到路径终点处，0.6则路径的60%处 (默认: 0.5)|
|smoothScrollToPosition(int position)|同上，此方法为平滑滚动，即选中时会播放动画 <br>动画时长通过 setFixingAnimationDuration 方法来设置|
|setFixingAnimationDuration(long duration)|设置自动选中后的选中动画时长|
|setOnItemSelectedListener(Listener listener)|设置Item被选中后的监听器 (需开启自动选中才生效)|


### 使用示例：
```java
    mPathLayoutManager = new PathLayoutManager(path, itemOffset);
    mRecyclerView.setLayoutManager(mPathLayoutManager);
```

### Demo下载: [app-debug.apk](https://github.com/wuyr/PathLayoutManager/raw/master/app-debug.apk)
### 库源码地址： https://github.com/Ifxcyr/PathLayoutManager

### 效果：
![preview](https://github.com/wuyr/PathLayoutManager/raw/master/previews/preview.gif) ![preview](https://github.com/wuyr/PathLayoutManager/raw/master/previews/preview2.gif)
![preview](https://github.com/wuyr/PathLayoutManager/raw/master/previews/preview3.gif) ![preview](https://github.com/wuyr/PathLayoutManager/raw/master/previews/preview4.gif)
![preview](https://github.com/wuyr/PathLayoutManager/raw/master/previews/preview7.gif) ![preview](https://github.com/wuyr/PathLayoutManager/raw/master/previews/preview8.gif)
![preview](https://github.com/wuyr/PathLayoutManager/raw/master/previews/preview5.gif) ![preview](https://github.com/wuyr/PathLayoutManager/raw/master/previews/preview6.gif)
