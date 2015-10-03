# CSDNBlogDownloader
CSDN Blog Downloader CSDN博客下载器
## Version 2.0
![Version2.0](http://img.blog.csdn.net/20151003134648472?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQv/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/Center)
SHA256:c3fe282f2bb546b4daba38e64edab610966c193045ee70dea60420baa0b8a322
## Version 1.0
![Version1.0](http://img.blog.csdn.net/20151001001614155?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQv/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/Center)
SHA256:91043a527f2bb639eb2e9a17b608e54ff98c2f20e0ba926799735f48f2a0b8b6


## 软件使用说明：
### Version 2.0
   * 下载用户：指定用户名和下载地址，下载即可。可导入/保存配置。
   * 下载文章：输入文章链接，指定下载地址，下载即可。可导入/保存文章链接。
   * 下线分类：输入分类链接，指定下载地址，下载即可。可导入/保存分类链接。
### Version 1.0
输入用户名，指定下载到本地的地址，点击下载即可。配置中可以保存当前的用户名和下载地址，保存为config.ini文件。读取用户配置则可以读取config.ini文件，填充用户名和下载地址。

## 目录说明
   * src————源码
   * doc————javadoc文档
   * exe————可程序执行文件
   * release————zip文件，含可执行程序文件
   * jar————jar包
   * test————测试文件
   * .setting————eclipse工程文件
   * .classpath————eclipse工程文件

## 软件架构
### Version 2.0
更新了架构，采用MVC的设计。
   * 模式分为文章模式（UrlModel）、分类模式（CategoryModel）和用户模式（UserModel），分别可以单独下载文章、下载文章分类和下载用户全部文章。
   * 拓展了User类，定义了新的数据类型：Blog、Category和User类。
   * 减少了Parser的使用，更多资源通过jsoup的select功能提取。
   * 更新了GUI设计

### Version 1.0
1. Crawler 爬虫用于爬取网页内容，按工作内容不同分为三种：
   * IndexCrawler 首页爬虫用于爬取用户博客首页内容，包括博客的基本信息（访问、积分、排名、原创、转载、译文和评论）、用户头像和文章分类。
   * CategoryCrawler 分类爬虫用于爬取一个文章分类下的所有文章链接。
   * BlogCrawler 博客爬虫用于爬取一篇文章的内容和图片，并保存到文件。
2. User是用户类，用于控制爬取用户博客的流程。User 继承了SwingWorker类，使得下载过程可以在后台Work Thread运行，而不会导致GUI界面锁住。
3. Parser 用于处理爬取的网页内容，处理文章内容（去除广告等），使用正则表达式获取指定信息（文章分类，文章链接，图片地址等），生成格式化的索引。
4. Util 提供工具API，如图片下载，文件读写，打开文件浏览器，打开网页浏览器。
5. GUI 是程序界面，使用了WindowBuilder。
6. icons 含有程序界面使用的图标图片。
7. jsoup 是用于爬虫建立网络链接的API包。


## 各类详解
### Version 2.0
#### Model类
Model是工作模式类，包含了文章模式（UrlModel）、分类模式（CategoryModel）和用户模式（UserModel）三种模式，针对不同任务使用不同模式。

#### Type类
Type类定义了数据类型：
   * Blog类，存储文章信息
   * Category类，存储分类信息
   * User类，存储用户信息

#### Controller类
Controller是控制器，用于控制View和设置Model。

### Version 1.0
#### Crawler类
Crawler 提供了以下成员：
   * document 用于存储爬取的网页内容。
   * crawl(SimpleEntry<String, String> link)  爬取链接内容方法。link的定义使用了SImpleEntry的键值对，key是link的标题，value是link的url地址。
   * connect(String url) 建立网络链接方法。其中判断了status code（200）和content-type（text/html），最大尝试次数为20次,，尝试间隔为100ms。

IndexCrawlerr 首页爬虫在获得网页内容后，首先使用Parser的bloggerParser方法得到博客的基本信息，然后使用categoryParser方法得到文章分类信息并在本地以分类名建立目录，接着得到用户头像的图片地址并下载。

CategoryCrawler 分类爬虫根据文章分类网页爬取该类下的文章链接，如果网页内容含有关键字”下一页“则自动爬取下一页的内容。文章链接使用键值对存储，key是文章标题，value是文章链接。

BlogCrawler 博客爬虫得到网页内容，调用Parser的fileNameValify方法将文章标题合法化（可以作为文件名），然后使用docParser方法删减掉网页中不需要的内容，接着使用imageParser获取网页中的图片地址，并使用Util中的downloadImage方法将图片下载到本地。然后更新网页中的一些链接，并将图片链接指向本地地址，最后将网页输出为文件。

#### User类
User 提供了以下方法:
   * profile() 使用IndexCrawler爬取用户博客首页，得到博客信息、用户头像和文章分类信息。
   * crawl() 使用crawlCategory() 和crawlBlog() 方法依次爬取所有文章分类下的文章。
   * crawlCategory() 使用CategoryCrawler 爬取分类下的文章链接。
   * crawlBlog() 使用BlogCrawler爬取文章内容。
   * createIndex() 创建索引，包括博客信息，文章分类和文章链接。
 
doInBackground() 复写SwingWorker的方法，定义User的工作流程：
   * profile() 
   * crawl()  
   * createIndex()

#### GUI类
GUI实现了PropertyChangeListener接口，并通过User的addPropertyChangeListener方法注册给User作为Observer。

User通过firePropertyChange来告知GUI其状态的变化，GUI通过propertyChange来获取变化信息，并改变显示。

### 作者
Geurney geurneyma@gmail.com
