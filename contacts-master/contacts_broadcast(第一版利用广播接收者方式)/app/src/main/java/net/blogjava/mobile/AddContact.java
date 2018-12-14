package net.blogjava.mobile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.nio.channels.SelectableChannel;

import net.blogjava.mobile.widget.FileBrowser;
import net.blogjava.mobile.widget.OnFileBrowserListener;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.Contacts.Intents.Insert;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;


/**
*AddContact是负责保存带头像的联系人信息 
*/
public class AddContact extends Activity implements OnClickListener,
		OnFileBrowserListener, OnMenuItemClickListener
{

	private FileBrowser fileBrowser;
	private View fileBrowserView;
	private AlertDialog alertDialog;
	private EditText etName;
	private EditText etTelephone;
	private EditText etEmail;
	private ImageView ivPhoto;
	private String photoFilename;
	private final String ACTION_ADD_CONTACT = "net.blogjava.mobile.ADDCONTACT";

	@Override
	public void onDirItemClick(String path)
	{

	}

	@Override
	public void onFileItemClick(String filename)
	{
		if (filename.toLowerCase().endsWith("jpg")
				|| filename.toLowerCase().endsWith("jpeg"))
		{
			try
			{
				FileInputStream fis = new FileInputStream(filename);
				ivPhoto.setImageDrawable(Drawable.createFromStream(fis,
						filename));
				alertDialog.dismiss();
				photoFilename = filename;
			}
			catch (Exception e)
			{
				// TODO: handle exception
			}

		}
	}

	@Override
	public void onClick(View view)
	{
		fileBrowserView = getLayoutInflater().inflate(R.layout.select_photo,
				null);
		fileBrowser = (FileBrowser) fileBrowserView
				.findViewById(R.id.filebrowser);
		fileBrowser.setOnFileBrowserListener(this);
		alertDialog = new AlertDialog.Builder(this).setTitle("Ñ¡ÔñÁªÏµÈËÍ·Ïñ")
				.setIcon(R.drawable.select_photo).setView(fileBrowserView)
				.setPositiveButton("¹Ø±Õ", null).create();
		alertDialog.show();

	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_contact);
		Button btnSelectPhoto = (Button) findViewById(R.id.btnSelectPhoto);
		btnSelectPhoto.setOnClickListener(this);
		etName = (EditText) findViewById(R.id.etName);
		etTelephone = (EditText) findViewById(R.id.etMessage);
		etEmail = (EditText) findViewById(R.id.etDate);
		ivPhoto = (ImageView) findViewById(R.id.ivPhoto);

	}

//将头像转换为字节数组流。ivPhoto是一个Imageview组件，用于显示联系人的头像
	@Override
	public boolean onMenuItemClick(MenuItem item)
	{
		String sql = " insert into t_contacts(name, message, date, photo) values(?,?,?,?)";

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		((BitmapDrawable) ivPhoto.getDrawable()).getBitmap().compress(
				CompressFormat.JPEG, 50, baos);
		Object[] args = new Object[]
		{ etName.getText(), etTelephone.getText(), etEmail.getText(),
				baos.toByteArray() };

		Main.dbService.execSQL(sql, args);
		Main.contactAdapter.getCursor().requery();
		//通知主界面的ListView组件，t_contacts表中的数据已经发生变化，需要更新列表
		Main.contactAdapter.notifyDataSetChanged();
		// ¿ª·¢¹ã²¥Ìí¼ÓÁªÏµÈËµÄÏûÏ¢
		Intent addContactIntent = new Intent(ACTION_ADD_CONTACT);
		addContactIntent.putExtra("name", etName.getText().toString());
		addContactIntent.putExtra("message", etTelephone.getText().toString());
		addContactIntent.putExtra("date", etEmail.getText().toString());
		addContactIntent.putExtra("photoFilename", photoFilename);
		
		sendBroadcast(addContactIntent);
		finish();
		return false;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		menu.add("±£´æ").setOnMenuItemClickListener(this);
		return super.onCreateOptionsMenu(menu);
	}

}
