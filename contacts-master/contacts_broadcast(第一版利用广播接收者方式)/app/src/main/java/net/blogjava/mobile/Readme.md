### 在编写AddContact需要注意以下三点:
 - 向BINARY类型字段插入值,与向简单类型字段插入值的方法相同
 - BINARY类型字段对应的数据是字节数组（byte[]）,因此，在设置SQL参数的时
    要先将要保存在BINARY类型字段的值转换成字符数组。BINARY类型不仅可以保存图像，还可以保存任何数据类型的数据。
 - 在更新数据表后，与数据表绑定的ListView等组件不会自动刷新，需要使用notifyDataSet-Changed方法来通知Adapter对象数据已经改变，这个时候ListView等组件会重新通过与其绑定的Adapter对象获得数据，并更新列表项。