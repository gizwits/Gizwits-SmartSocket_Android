package com.gizwits.framework.config;

public enum GizwitsErrorMsg {
	E1(9001,"mac already registered!","mac已经注册"),
	E2(9002,"product_key invalid","product key无效"),
	E3(9003,"appid invalid","appid无效"),
	E4(9004,"token invalid","token无效"),
	E5(9005,"user not exist","用户名不存在"),
	E6(9006,"token expired","token已过期"),
	E7(9007,"m2m_id invalid","m2m_id无效"),
	E8(9008,"server error","服务器错误"),
	E9(9009,"code expired","验证码已过期"),
	E10(9010,"code invalid","验证码无效"),
	E11(9011,"sandbox scale quota exhausted!","沙箱环境配额用尽"),
	E12(9012,"production scale quota exhausted!","生产环境配额用尽"),
	E13(9013,"product has no request scale!","产品未设置配额"),
	E14(9014,"device not found!","设备找不到"),
	E15(9015,"form invalid!","表单无效"),
	E16(9016,"did or passcode invalid!","did或者passcode无效"),
	E17(9017,"device not bound!","设备未绑定"),
	E18(9018,"phone unavailable!","手机不可用"),
	E19(9019,"username unavailable!","用户名不可用"),
	E20(9020,"username or password error!","用户名或者密码错误");
	
private String EngDescript,CHNDescript;
private int num;
private GizwitsErrorMsg(int num,String EngDescript,String CHNDescript){
	this.num=num;
	this.EngDescript=EngDescript;
	this.CHNDescript=CHNDescript;
}
public String getEngDescript() {
	return EngDescript;
}
public String getCHNDescript() {
	return CHNDescript;
}
public int getNum() {
	return num;
}

public static GizwitsErrorMsg getEqual(int num){
	for(GizwitsErrorMsg GEM :GizwitsErrorMsg.values()){
		if(GEM.getNum()==num)
			return GEM;
	}
	return E1;
}


}
