package net.blogjava.mobile.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class AddContactRceivcer extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        
        //判断系统收到的是否为添加的Broadcast Action
        if ("net.blogjava.mobile.ADDCONTACT".equals(intent.getAction()))
        {
            String message = "";
            Bundle bundle = intent.getExtras();
            if (bundle != null)
            {
                //获得广播中的联系人信息
                message = "姓名：" + bundle.getString("name") + "\n";
                message += "信息:"+bundle.getString("telephone") + "\n";
                message += "时间"+ bundle.getString("email") +"\n";
                message += "头像文件路径"+ bundle.getString("photpFilename") + "\n";
                //使用Toast信息提示框显示广播中的联系人信息
               // Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
            }
        }
    }
}
