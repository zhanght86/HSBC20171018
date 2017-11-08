package com.sinosoft.easyscan5.core.vo;

import java.net.URLDecoder;

import org.apache.log4j.Logger;

public class ExpandPropertyVo {
	Logger log = Logger.getLogger(ExpandPropertyVo.class);

	/** 扩展属性类型（输入框、下拉、日期，这个字段存储对应的代码） */
	private String proType;
	/** 字段名 */
	private String proName;
	/** 字段中文名 */
	private String proChName;
	/** 字段值 */
	private String proVaule;
	/** 使用的字典名 */
	private String proDictType;
	/** 版本号 */
	private String proVersioin;

	public String getProType() {
		return proType;
	}

	public void setProType(String proType) {
		try {
			if(proType!=null){
				this.proType = URLDecoder.decode(proType.trim(), "UTF-8");
			}
		} catch (Exception e) {
			log.error("proType解码失败", e);
			e.printStackTrace();
		}
	}

	public String getProName() {
		return proName;
	}

	public void setProName(String proName) {
		try {
			if(proName!=null){
				this.proName = URLDecoder.decode(proName.trim(), "UTF-8");
			}
		} catch (Exception e) {
			log.error("proName解码失败", e);
			e.printStackTrace();
		}
	}

	public String getProChName() {
		return proChName;
	}

	public void setProChName(String proChName) {
		try {
			if(proChName!=null){
				this.proChName = URLDecoder.decode(proChName.trim(), "UTF-8");
			}
		} catch (Exception e) {
			log.error("proChName解码失败", e);
			e.printStackTrace();
		}
	}

	public String getProVaule() {
		return proVaule;
	}

	public void setProVaule(String proVaule) {
		try {
			if(proVaule!=null){
				this.proVaule = URLDecoder.decode(proVaule.trim(), "UTF-8");
			}
		} catch (Exception e) {
			log.error("proVaule解码失败", e);
			e.printStackTrace();
		}
	}

	public String getProDictType() {
		return proDictType;
	}

	public void setProDictType(String proDictType) {
		try {
			if(proDictType!=null){
				this.proDictType = URLDecoder.decode(proDictType.trim(), "UTF-8");
			}
		} catch (Exception e) {
			log.error("proDictType解码失败", e);
			e.printStackTrace();
		}
	}

	public String getProVersioin() {
		return proVersioin;
	}

	public void setProVersioin(String proVersioin) {
		try {
			if(proVersioin!=null){
				this.proVersioin = URLDecoder.decode(proVersioin.trim(), "UTF-8");
			}
		} catch (Exception e) {
			log.error("proVersioin解码失败", e);
			e.printStackTrace();
		}
	}
}
