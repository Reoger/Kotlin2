# 用一个demo来认识kotlin

kotlin其实已经出道很久了，但是直到上次google的IO大会才慢慢奠定了其位置
关于kotlin的学习资料现在网上也是一抓一大把，这里就不做过多的介绍了。这里记录下几个学习的资源网站
* [kotlin官方中文翻译文档网址：](https://www.kotlincn.net/docs/reference/)
* [中文官网](https://www.kotlincn.net/)

本次主要记录使用kotlin 写的一个小demo

本次利用干货集中营的api接口，我们将其美女图片用recyclerView显示出来。很简单，但是也比较有针对性。
能很好的了解kotlin的语法和其基本的使用。正所谓，在实践中学。

我们首先还是看一下本次demo的效果吧

可以看到，我们demo写的还是比较简单的。但是已经我们来领略kotlin的魅力了。
下面我们正式来实现本demo的效果。

首先，在 我们先构建起recyclerView的使用环境，导入相应的包：
``` implementation 'com.android.support:recyclerview-v7:26.0.0-beta2'```
然后在主界面的xml中使用：
```<?xml version="1.0" encoding="utf-8"?>
   <LinearLayout
       xmlns:android="http://schemas.android.com/apk/res/android"
       xmlns:app="http://schemas.android.com/apk/res-auto"
       xmlns:tools="http://schemas.android.com/tools"
       android:layout_width="match_parent"
       android:layout_height="match_parent"
       android:orientation="vertical"
       tools:context="com.create.kotlin.MainActivity">
   
   <android.support.v7.widget.RecyclerView
       android:background="#333"
       android:id="@+id/recyclerView"
       android:layout_width="match_parent"
       android:layout_height="match_parent"/>
   </LinearLayout>
```
接下来，我们就来编写适配器MyAdapter.代码如下；
```class MyAdapter() : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
   
       private var context:Context?=null
       private var list:ArrayList<Img> ?= null
   
       constructor(context: Context,list: ArrayList<Img>) : this() {
           this.context = context
           this.list = list
           notifyDataSetChanged()
       }
   
       override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MyViewHolder {
           return MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item,parent,false))
       }
   
       override fun onBindViewHolder(holder: MyViewHolder?, position: Int) {
           holder?.text!!.text = list?.get(position)?.who
           Glide.with(context).load(list?.get(position)?.url).error(R.drawable.abc_ab_share_pack_mtrl_alpha).into(holder.image)
           holder.image.setOnClickListener {
               var io:String = list?.get(position)?.url.toString()
               context?.startActivity<NextActivity>((Pair("param",io)))//启动另一个activity并传递数据io
           }
   
       }
   
       override fun getItemCount(): Int {
           return list?.size as Int
       }
   
   
       class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
           var image:ImageView
           var text :TextView
   
           init {
               image = view.findViewById(R.id.image)
               text  = view.findViewById(R.id.text)
           }
       }
   }```
 代码很简洁对吧，哈哈，这就是kotiln的妙处。其实这里我还是遵守了java的写法，我们完全可以将
 构造方法不单独成一个方法，那样会更加简洁。
 
 接下来，看看我们的Img实体类，用来存储要显示的实现。

```class Img {
       var url:String?=null
       var who:String?=null
   
       constructor(text: String,image: String){
           this.url = image
           this.who = text
       }
   }
```
接下来就是我们的MainActivity代码的编写：
```class MainActivity : AppCompatActivity() {
   
       var adapter:MyAdapter?=null
       override fun onCreate(savedInstanceState: Bundle?) {
           super.onCreate(savedInstanceState)
           setContentView(R.layout.activity_main)
   
           var forecastList :RecyclerView = findViewById(R.id.recyclerView)
           forecastList.layoutManager= LinearLayoutManager(this)

            adapter = MyAdapter(this,initData()!!)
           forecastList.adapter = adapter
   
       }
   
        fun  initData(): ArrayList<Img>?{
   
            var list = ArrayList<Img>()
            async() {
                val url = "http://gank.io/api/data/%E7%A6%8F%E5%88%A9/10/1"
                val forecastJsonStr = URL(url).readText()
   
                try {
                    var a = Gson().fromJson(forecastJsonStr, GankGril::class.java)
                    var j=0
                       for(item in a.results){
                           var i = Img(item.who,item.url)
                           list.add(j,i)
                           j++
                       }
   
                } catch(e: Exception) {
                    Log.d("ATg","这里出现错误了"+e)
                }
   
                uiThread {
                    toast("数据加载完成")
                    adapter!!.notifyDataSetChanged()
                }
            }
            return list
        }
   
       fun Activity.toast(message:CharSequence, duration:Int=
       Toast.LENGTH_SHORT){
           Toast.makeText(this,message,duration).show()
       }
   
       override fun onDestroy() {
           super.onDestroy()
       }
   }
```

什么？GankGril对象不知道是什么？对哦，这个是我们用来解析返回的json数据的一个实体类，很简单，代码如下：
```public class GankGril {

       private boolean error;
       private List<ResultsBean> results;
   
       public boolean isError() {
           return error;
       }
   
       public void setError(boolean error) {
           this.error = error;
       }
   
       public List<ResultsBean> getResults() {
           return results;
       }
   
       public void setResults(List<ResultsBean> results) {
           this.results = results;
       }
   
       public static class ResultsBean {
     
           private String _id;
           private String createdAt;
           private String desc;
           private String publishedAt;
           private String source;
           private String type;
           private String url;
           private boolean used;
           private String who;
   
           public String get_id() {
               return _id;
           }
   
           public void set_id(String _id) {
               this._id = _id;
           }
   
           public String getCreatedAt() {
               return createdAt;
           }
   
           public void setCreatedAt(String createdAt) {
               this.createdAt = createdAt;
           }
   
           public String getDesc() {
               return desc;
           }
   
           public void setDesc(String desc) {
               this.desc = desc;
           }
   
           public String getPublishedAt() {
               return publishedAt;
           }
   
           public void setPublishedAt(String publishedAt) {
               this.publishedAt = publishedAt;
           }
   
           public String getSource() {
               return source;
           }
   
           public void setSource(String source) {
               this.source = source;
           }
   
           public String getType() {
               return type;
           }
   
           public void setType(String type) {
               this.type = type;
           }
   
           public String getUrl() {
               return url;
           }
   
           public void setUrl(String url) {
               this.url = url;
           }
   
           public boolean isUsed() {
               return used;
           }
   
           public void setUsed(boolean used) {
               this.used = used;
           }
   
           public String getWho() {
               return who;
           }
   
           public void setWho(String who) {
               this.who = who;
           }
       }
   }```
   
   要实现点击图片方法图片的效果，最简单的方法就是在新的activity中进行展示，所以NextActivity的代码如下：
```java
class NextActivity : AppCompatActivity() {
   
       override fun onCreate(savedInstanceState: Bundle?) {
           super.onCreate(savedInstanceState)
           setContentView(R.layout.activity_next)
           var data = intent.getStringExtra("param")
   
           Glide.with(this).load(data).into(imageView)
       }
   
       override fun onTouchEvent(event: MotionEvent?): Boolean {
           when(event?.action){
                MotionEvent.ACTION_UP->{
                    finish()
                    return false
                }
   
           }
           return super.onTouchEvent(event)
       }
   }
```
对哦，这里还忘记了item的布局文件：
```xml
<android.support.v7.widget.CardView xmlns:android="http://schemas.android.com/apk/res/android"
xmlns:app="http://schemas.android.com/apk/res-auto"
android:layout_height="96dp"
android:layout_width="match_parent"
android:focusable="true"
android:clickable="true"
android:foreground="?android:attr/selectableItemBackground"
app:cardCornerRadius="4dp"
app:cardElevation="1dp"
app:cardPreventCornerOverlap="true"
android:layout_marginTop="8dp"
android:layout_marginLeft="8dp"
android:layout_marginRight="8dp">

<LinearLayout
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="horizontal"
    android:paddingLeft="8dp"
    android:paddingRight="8dp" >

    <TextView
        android:layout_width="0dp"
        android:layout_height="match_parent"
        android:layout_weight="1"
        android:id="@+id/text"
        android:paddingTop="8dp"
        android:paddingBottom="8dp"
        android:layout_marginRight="8dp"
        android:layout_marginEnd="8dp"
        android:gravity="center_vertical"
        android:maxLines="3"
        android:ellipsize="end"
        android:textSize="18sp" />

    <ImageView
        android:layout_width="80dp"
        android:layout_height="80dp"
        android:id="@+id/image"
        android:layout_gravity="center_vertical" />

</LinearLayout>

</android.support.v7.widget.CardView>
```
当然，要实现上面的效果，必须添加如下的依赖：
```gdb
  implementation 'com.android.support:cardview-v7:26.0.0-beta2'
  // 卡片式布局
    compile 'org.jetbrains.anko:anko-sdk15:0.8.2'
    // sdk19, sdk21, sdk23 are also available
    compile 'org.jetbrains.anko:anko-support-v4:0.8.2'
    // In case you need support-v4 bindings
    compile 'org.jetbrains.anko:anko-appcompat-v7:0.8.2'
    // For appcompat-v7 bindings
    //这三个是添加anko的依赖，本demo中主要用于异步获取资源
    
    implementation 'com.google.code.gson:gson:2.8.1'
    //gson依赖
```

因为涉及到网络操作，必要网络权限：
```xml
<uses-permission android:name="android.permission.INTERNET" />
```