package com.sinosoft.easyscan5.core.vo;

import java.io.File;

public class UploadImageVo {
	private File[] PageFiles;
	private String[] nFileNames;
	public File[] getPageFiles() {
		return PageFiles;
	}
	public void setPageFiles(File[] pageFiles) {
		PageFiles = pageFiles;
	}
	public String[] getNFileNames() {
		return nFileNames;
	}
	public void setNFileNames(String[] fileNames) {
		nFileNames = fileNames;
	}
}
