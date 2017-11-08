

/**
 * <p>Title: PDLoan</p>
 * <p>Description: 保单贷款描述表</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author
 * @version 1.0
 * @CreateDate：2009-3-16
 */
 
package com.sinosoft.productdef;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

import com.sinosoft.lis.db.*;
import com.sinosoft.lis.vdb.*;
import com.sinosoft.lis.sys.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;


public class RiskDeployScriptDownloadBL  {
 /** 错误处理类，每个需要错误处理的类中都放置该类 */
 public  CErrors mErrors=new CErrors();
 private VData mResult = new VData();
 /** 往后面传输数据的容器 */
 private VData mInputData= new VData();
 private TransferData mTransferData;
 /** 全局数据 */
 private GlobalInput mGlobalInput =new GlobalInput() ;
 /** 数据操作字符串 */
 private String mOperate;
 private String zipName="ProductSetupSQL.zip";
 /** 业务处理相关变量 */
 private String localPath;
 private MMap map=new MMap();
 private List<String> list=new ArrayList<String>();
 private LDDeployScriptInfoSet mLDDeployScriptInfoSet;
 private boolean flag=true;
 public RiskDeployScriptDownloadBL() {
 }

/**
* 传输数据的公共方法
 * @param: cInputData 输入的数据
   *         cOperate 数据操作
* @return:
*/
 public boolean submitData(VData cInputData,String cOperate)
 {
	 getInputData(cInputData,cOperate);
	 
    if(!check())
    	
    	
    {
     return false;
    }
    if(!deal()){
    	return false;
    }


    return flag;
}
 
 private void getInputData(VData cInputData,String cOperate) {
	this.mInputData=cInputData;
	this.mTransferData=(TransferData)mInputData.getObjectByObjectName("TransferData", 0);
	this.mLDDeployScriptInfoSet=(LDDeployScriptInfoSet)mInputData.getObjectByObjectName("LDDeployScriptInfoSet", 0);
	this.mOperate=cOperate;
	localPath=PubFun.getClassPath();
	
	
}

private boolean deal() {
if("del".equals(mOperate)){
		for (int i = 1; i <=mLDDeployScriptInfoSet.size(); i++) {
			LDDeployScriptInfoSchema tLDDeployScriptInfoSchema=mLDDeployScriptInfoSet.get(i);
			LDDeployScriptInfoDB tLDDeployScriptInfoDB=new LDDeployScriptInfoDB();
			tLDDeployScriptInfoDB.setSchema(tLDDeployScriptInfoSchema);
			String path=tLDDeployScriptInfoSchema.getPath();
			String name=tLDDeployScriptInfoSchema.getName();
			String Path=localPath+path+name;
			File f=new File(Path);
			if(f.exists()){			
			if(f.delete()){
				 tLDDeployScriptInfoDB.delete();
			}
			}
		}
	}else{
		for (int i = 1; i <=mLDDeployScriptInfoSet.size(); i++) {
			LDDeployScriptInfoSchema tLDDeployScriptInfoSchema=mLDDeployScriptInfoSet.get(i);
			String path=tLDDeployScriptInfoSchema.getPath();
			String name=tLDDeployScriptInfoSchema.getName();
			String Path=localPath+path+name;
			System.out.println(Path);
			this.list.add(Path);				
		}
}
	
	
	return true;
}
public List<String> getPath(){
	return this.list;
}
private boolean check()
 {
  return true;
 }

public File downLoadFiles(){
	List<File> file=new ArrayList<File>();
	//String filename="";
	for (int i = 0; i <list.size(); i++) {
		String path=list.get(i);
		File f=new File(path);
		if(!f.exists())continue;
		//filename=f.getName();
		file.add(f);		
	}
	if(file.size()<=0){
		this.mErrors.addOneError("您要下载的文件不存在！");
		flag=false;
		return null;
		}
	if(file.size()<=1){
		return file.get(0);
	}

	return downLoadFiles(file);
}
//文件打包下载
public  File downLoadFiles(List<File> files)
        {
    try {
        /**这个集合就是你想要打包的所有文件，
         * 这里假设已经准备好了所要打包的文件*/
        //List<File> files = new ArrayList<File>();
        /**创建一个临时压缩文件，
         * 我们会把文件流全部注入到这个文件中
         * 这里的文件你可以自定义是.rar还是.zip*/
        File file = new File(zipName);
        if (!file.exists()){   
            file.createNewFile();   
        }
       // response.reset();
        //response.getWriter()
        //创建文件输出流
        FileOutputStream fous = new FileOutputStream(file);   
        /**打包的方法我们会用到ZipOutputStream这样一个输出流,
         * 所以这里我们把输出流转换一下*/
//        org.apache.tools.zip.ZipOutputStream zipOut 
//            = new org.apache.tools.zip.ZipOutputStream(fous);
       ZipOutputStream zipOut 
        = new ZipOutputStream(fous);
        /**这个方法接受的就是一个所要打包文件的集合，
         * 还有一个ZipOutputStream*/
       zipFile(files, zipOut);
        zipOut.close();
        fous.close();
        return new File(zipName);
      // return downloadZip(file,response);
    }catch (Exception e) {
            e.printStackTrace();
        }
        /**直到文件的打包已经成功了，
         * 文件的打包过程被我封装在FileUtil.zipFile这个静态方法中，
         * 稍后会呈现出来，接下来的就是往客户端写数据了*/
       // OutputStream out = response.getOutputStream();
       
 return null;
   // return response ;
}

/**
 * 把接受的全部文件打成压缩包 
 * @param List<File>;  
 * @param org.apache.tools.zip.ZipOutputStream  
 */
public  void zipFile(List<File> files,ZipOutputStream outputStream) {
    int size = files.size();
    for(int i = 0; i < size; i++) {
        File file = (File) files.get(i);
        zipFile(file, outputStream);
    }
}

public File getFiles(){
	
	return null;
}
/**  
 * 根据输入的文件与输出流对文件进行打包
 * @param File
 * @param org.apache.tools.zip.ZipOutputStream
 */
public  void zipFile(File inputFile,
        ZipOutputStream ouputStream) {
    try {
        if(inputFile.exists()) {
            /**如果是目录的话这里是不采取操作的，
             * 至于目录的打包正在研究中*/
            if (inputFile.isFile()) {
                FileInputStream IN = new FileInputStream(inputFile);
                BufferedInputStream bins = new BufferedInputStream(IN, 512);
                //org.apache.tools.zip.ZipEntry
                ZipEntry entry = new ZipEntry(inputFile.getName());
                ouputStream.putNextEntry(entry);
                // 向压缩文件中输出数据   
                int nNumber;
                byte[] buffer = new byte[512];
                while ((nNumber = bins.read(buffer)) != -1) {
                    ouputStream.write(buffer, 0, nNumber);
                }
                // 关闭创建的流对象   
                bins.close();
                IN.close();
            } else {
                try {
                    File[] files = inputFile.listFiles();
                    for (int i = 0; i < files.length; i++) {
                        zipFile(files[i], ouputStream);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}

 public static void main(String[] args) {
	 try {
		 File f=new File("Template.sql");
		 f.createNewFile();
		 
		 
		System.out.println(f.getAbsolutePath());
		f.deleteOnExit();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
 }
}
