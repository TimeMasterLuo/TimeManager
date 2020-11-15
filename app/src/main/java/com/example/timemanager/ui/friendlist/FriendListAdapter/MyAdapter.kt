package com.example.timemanager.ui.friendlist.FriendListAdapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.timemanager.R

class MyAdapter(activity: Activity, val resourceId:Int, data:List<ListItem>) : ArrayAdapter<ListItem>(activity,resourceId,data){
    inner class ViewHolder(val fruitImage:ImageView,val fruitName:TextView)
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view:View
        val viewHolder:ViewHolder
        if (convertView==null){
            view=LayoutInflater.from(context).inflate(resourceId,parent,false)
            val itemImage: ImageView =view.findViewById(R.id.itemImage)//绑定布局得图片
            val itemName: TextView =view.findViewById(R.id.itemName)//绑定布局中得名字
            viewHolder=ViewHolder(itemImage,itemName)
            view.tag=viewHolder
        }else{
            view=convertView
            viewHolder=view.tag as ViewHolder
        }
        //val view = LayoutInflater.from(context).inflate(resourceId,parent,false)
//        val itemImage: ImageView =view.findViewById(R.id.itemImage)//绑定布局得图片
//        val itemName: TextView =view.findViewById(R.id.itemName)//绑定布局中得名字
        val item=getItem(position)//获取当前项得Fruit实例
        if (item!=null){
            viewHolder.fruitImage.setImageResource(item.imageId)
            viewHolder.fruitName.text=item.name
        }
        return  view
    }
}