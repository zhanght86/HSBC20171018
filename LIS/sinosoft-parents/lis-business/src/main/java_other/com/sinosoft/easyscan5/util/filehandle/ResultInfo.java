package com.sinosoft.easyscan5.util.filehandle;

import java.io.FileInputStream;
import java.io.InputStream;

public class ResultInfo {
	// 操作成功状态
	private boolean succee;
	// 操作信息
	private String msg;
	// FileNet或CM中的itemtID
	private String itemID;
	// 文件存储路径（相对路径）
	private String savePath;
	// 文件查询时返回的文件流
	private InputStream InputStream;

	public boolean isSuccee() {
		return succee;
	}

	public void setSuccee(boolean succee) {
		this.succee = succee;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getItemID() {
		return itemID;
	}

	public void setItemID(String itemID) {
		this.itemID = itemID;
	}

	public String getSavePath() {
		return savePath;
	}

	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}

	public InputStream getInputStream() {
		return InputStream;
	}

	public void setInputStream(InputStream InputStream) {
		this.InputStream = InputStream;
	}

}
