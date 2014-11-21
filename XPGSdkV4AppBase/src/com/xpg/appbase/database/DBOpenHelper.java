package com.xpg.appbase.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class DBOpenHelper extends SQLiteOpenHelper  {
		  private String[] DB_CREATE ;
		  private String DB_TABLE ;
		  private static int version  = 1 ;
		  private static String name = "iDMM.db";
		  
		  public DBOpenHelper(Context context) {
			    super(context, name, null, version);
			  }

		  public DBOpenHelper(Context context,String dbName ,String[] createString, String tableName) {
			  super(context, dbName, null, getVersion());
			  this.DB_CREATE = createString ;
			  this.DB_TABLE = tableName;
		  }
		  
		  public DBOpenHelper(Context context, String name, CursorFactory factory, int version) {
		    super(context, name, factory, version);
		  }

		  @Override
		  public void onCreate(SQLiteDatabase _db) {
			  for (String createSql : DB_CREATE) {
				  _db.execSQL(createSql);
			  }
		  }

		  @Override
		  public void onUpgrade(SQLiteDatabase _db, int _oldVersion, int _newVersion) {		    
		    _db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);
		    onCreate(_db);
		  }

		public static int getVersion() {
//			version++;
			return version;
		}

		public static void setVersion(int version) {
			DBOpenHelper.version = version;
		}
}
