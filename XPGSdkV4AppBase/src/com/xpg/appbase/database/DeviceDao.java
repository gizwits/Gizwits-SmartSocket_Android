package com.xpg.appbase.database;

import java.util.ArrayList;
import java.util.List;

import com.xpg.appbase.entity.Device;

public class DeviceDao {
	private final String TAG = "DeviceDao";
	private static DeviceDao deviceDao;
	private String tableName = DBConstants.DEVICE_TABLE;

	public static DeviceDao getInstance() {
		if (deviceDao == null) {
			deviceDao = new DeviceDao();
		}
		return deviceDao;
	}

	// 增加一个记录
	public int add(Device device) {
		long insertId = -1;
		if (device != null) {
			insertId = DaoCenter.getInstance().getDao()
					.insert(tableName, device, "device_id");
			if (insertId != -1) {
				device.setDevice_id((int) insertId);
			}
		}
		return (int) insertId;
	}

	// 获取所有的历史详细记录
	public List<Device> getAllData() {
		List<Device> bs = new ArrayList<Device>();
		List<Object> resultList = DaoCenter.getInstance().getDao()
				.queryAllData(tableName, Device.class);
		if (resultList != null && resultList.size() > 0) {
			for (Object object : resultList) {
				bs.add((Device) object);
			}
		}
		return bs;
	}

	/**
	 * 判断是否已经存在
	 * 
	 * @param mac
	 * @return
	 */
	public Device isEixtDate(String mac) {
		String condition = " mac = \'" + mac + "\'";
		List<Object> resultList = DaoCenter.getInstance().getDao()
				.queryOneData(tableName, Device.class, condition);
		if (resultList != null && resultList.size() > 0) {
			return (Device) resultList.get(0);
		} else
			return null;

	}

	/**
	 * 更新设备信息
	 * 
	 * @param info
	 * @return
	 */
	public boolean update(Device info) {
		if (delete(info) && add(info) != -1) {
			return true;
		} else {
			return false;
		}

	}

	// 删除一个设备记录
	public boolean delete(Device info) {
		boolean result = false;
		if (info != null) {
			long delCode = DaoCenter.getInstance().getDao()
					.deleteOneData(tableName, "device_id", info.getDevice_id());
			if (delCode >= 1) {
				result = true;
			}
		}
		return result;
	}

}
