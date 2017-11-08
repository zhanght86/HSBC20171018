package com.sinosoft.lis.fininterface;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileWriter;

public class GenTxtFile {	
private static Logger logger = Logger.getLogger(GenTxtFile.class);
	
	private String txtFilePath;
	private String txtContent;
	public boolean flag = false;
		
	public GenTxtFile() {
		super();
	}

	public GenTxtFile(String txtFilePath, String txtContent) 
	{
		this.txtFilePath = txtFilePath;
		this.txtContent = txtContent;
	}
		
	public void writeTxt() 
	{	
		try {
			File txtFile;
			txtFile = new File(txtFilePath);
			if (txtFile.exists() == false)    // 文件不存在时创建
			{
				txtFile.createNewFile();
				txtFile = new File(txtFilePath);
			}
			FileWriter fwriter = new FileWriter(txtFile,true);
			fwriter.write(txtContent);
			fwriter.flush();
			flag = true;
		} catch (Exception d) {
			logger.debug(d.getMessage());
			flag = false;	
		}
	}
	
	public void setTxtFileName(String txtFilePath){
		this.txtFilePath = txtFilePath;
	}
	
	public void setTxtContent(String txtContent) {
		this.txtContent = txtContent;
	}

}
