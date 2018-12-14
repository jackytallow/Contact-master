package net.blogjava.mobile.db;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
//数据库服务
public class DBService extends SQLiteOpenHelper
{
	//创建数据库版本
	private final static int DATABASE_VERSION = 1;
	//创建数据库名称
	private final static String DATABASE_NAME = "contact.db";

	@Override
	public void onCreate(SQLiteDatabase db)
	{


		String sql = "CREATE TABLE [t_contacts] ("
				+ "[id] AUTOINC,"
				+ "[name] VARCHAR(20) NOT NULL ON CONFLICT FAIL,"
				+ "[message] VARCHAR(20) NOT NULL ON CONFLICT FAIL,"
				+ "[date] VARCHAR(20),"
				+ "[photo] BINARY, "
				+ "CONSTRAINT [sqlite_autoindex_t_contacts_1] PRIMARY KEY ([id]))";

		db.execSQL(sql);
	}

	public DBService(Context context)
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		String sql = "drop table if exists [t_contacts]";
		db.execSQL(sql);
		// ´Ë´¦Ó¦¸ÃÊÇÐÂµÄSQLÓï¾ä
		sql = "CREATE TABLE [t_contacts] ("
				+ "[id] AUTOINC,"
				+ "[name] VARCHAR(20) NOT NULL ON CONFLICT FAIL,"
				+ "[message] VARCHAR(20) NOT NULL ON CONFLICT FAIL,"
				+ "[date] VARCHAR(20),"
				+ "[photo] BINARY, "
				+ "CONSTRAINT [sqlite_autoindex_t_contacts_1] PRIMARY KEY ([id]))";
		db.execSQL(sql);

	}
    //  Ö´ÐÐinsert¡¢update¡¢deleteµÈSQLÓï¾ä
	public void execSQL(String sql, Object[] args)
	{
		SQLiteDatabase db = this.getWritableDatabase();				
		db.execSQL(sql, args);	
	}
    //  Ö´ÐÐselectÓï¾ä
	public Cursor query(String sql, String[] args)
	{
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(sql, args);		
		return cursor;
	}
}
