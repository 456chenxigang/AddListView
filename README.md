### AddListView

#### 一个动态添加，删除item的自定义控件 
#### *Min SDK* 14

<a href="./screenshots/AddListView.gif"> <img src="./screenshots/AddListView.gif" width = 60% /> </a>

#### 使用
- 在project 的build.gradle文件下添加：
```
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```
- 然后添加依赖
```
dependencies {
     compile 'com.github.456chenxigang:AddListView:1.0.0'

 }
```
- 在xml中添加
```
<com.example.library.AddListView
        android:id="@+id/commentListView"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="vertical"
        app:item_color="@color/praise_item">
```
- 监听操作
```
addListView = (AddListView) findViewById(R.id.AddListView);
        addListDatas = new ArrayList<>();

        //添加item
        addListDatas.add("item0");
        addListView.setDatas(addListDatas);

        //删除按钮的监听
        addListView.setOnItemDelBtnClickListener(new AddListView.OnItemDelBtnClickListener() {
            @Override
            public void onItemDelClick(int position) {
                if (!addListDatas.isEmpty()){
                    if (addListDatas.size() > position){
                        addListDatas.remove(position);
                        addListView.setDatas(addListDatas);
                    }
                }
            }
        });

        //添加按钮的监听
        addListView.setOnItemAddBtnClickListener(new AddListView.OnItemAddBtnClickListener() {
            @Override
            public void onItemAddClick() {
                addListDatas.add("item"+ (++ni));
                addListView.setDatas(addListDatas);
            }
        });

        //点击item监听
        addListView.setOnItemClickListener(new AddListView.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(getApplicationContext(),position+"",Toast.LENGTH_SHORT).show();
            }
        });
```