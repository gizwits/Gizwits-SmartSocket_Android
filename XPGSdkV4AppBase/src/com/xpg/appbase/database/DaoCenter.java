package com.xpg.appbase.database;

import android.content.Context;

/**
 * 
 */

public class DaoCenter {
	
	private static DaoCenter daoCenter ;
	private BaseDao dao;
	
	public static DaoCenter getInstance(){
		if(daoCenter == null ){
			daoCenter = new DaoCenter();
			
		}
		return daoCenter ;
	}

	public void initDaoCenter(Context context){
		dao = new BaseDao(context);
	}

	public void open(){
		dao.open(DBConstants.DB_NAME, DBConstants.getCreateStr() ,DBConstants.DB_NAME);
	}
	
	public void close(){
		dao.close();
	}

	public BaseDao getDao() {
		return dao;
	}

	public void setDao(BaseDao dao) {
		this.dao = dao;
	}
	
}
