package com.sinosoft.lis.workflowmanage;

import java.io.*;

import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;
public class ServiceFileBL 
{
	public CErrors mErrors = new CErrors();
	private String FilePath="";
    public ServiceFileBL()
    {
    	
    }
    public boolean GetPath()
    {
    	ExeSQL tExeSQL=new ExeSQL();
    	SQLwithBindVariables sqlbv=new SQLwithBindVariables();
    	sqlbv.sql("select sysvarvalue from ldsysvar where sysvar='ServiceFilePath'");
    	FilePath=tExeSQL.getOneValue(sqlbv);
    	if(FilePath==null||FilePath.equals(""))
    	{
			CError tError = new CError();
			tError.moduleName = "ServiceFileBL";
			tError.functionName = "GetPath";
			tError.errorMessage = "查询文件目录失败！";
			this.mErrors.addOneError(tError);   		
    		return false;
    	}
    	File tFile=new File(FilePath);
    	if(!tFile.exists())
    	{
			CError tError = new CError();
			tError.moduleName = "ServiceFileBL";
			tError.functionName = "GetPath";
			tError.errorMessage = "文件目录不存在！";
			this.mErrors.addOneError(tError);       		
    		return false;
    	}
    	return true;
    }
    public boolean CreateFile(String Package,String Name,String Interface)
    {
    	if(FilePath==null||FilePath.equals(""))
    	{
    		if(!GetPath())
    		{
    			return false;
    		}
    	}
    	File tFile=new File(FilePath+Package.replace('.', '\\'));
    	if(!tFile.exists())
    	{
    		if(!tFile.mkdir())
    		{
    			CError tError = new CError();
    			tError.moduleName = "ServiceFileBL";
    			tError.functionName = "CreateFile";
    			tError.errorMessage = "文件目录创建失败！";
    			this.mErrors.addOneError(tError);       		
        		return false;    			
    		}
    	}  
    	try
    	{
        	PrintWriter out = null;
        	out = new PrintWriter(new FileWriter(new File(FilePath+Package.replace('.', '\\')+"\\"+Name+".java")), true);
            // 文件头信息
            out.println("/**");
            out.println(" * Copyright (c) 2002 sinosoft  Co. Ltd.");
            out.println(" * All right reserved.");
            out.println(" */");
            out.println("");
            // @Package
            out.println("package "+Package+";");
            out.println("");
            // @Import
            out.println("import com.sinosoft.workflowengine.*;");
            out.println("import com.sinosoft.utility.*;");
            out.println("");
            // 类信息
            out.println("/*");
            out.println(" * <p>ClassName: " + Name + " </p>");
            out.println(" * <p>Description: 工作流服务类接口文件 </p>");
            out.println(" * <p>Copyright: Copyright (c) 2002</p>");
            out.println(" * <p>Company: sinosoft </p>");
            out.println(" * @CreateDate：" + PubFun.getCurrentDate());
            out.println(" */");
            // @Begin
            out.println("public class " + Name + " implements "+Interface);
            out.println("{");  
            //field
            out.println("   public CErrors mErrors = new CErrors();"); 
            out.println("   private VData mResult;");  
            out.println("   private TransferData mTransferData;");  
            out.println("");
            // gouzaoqi
            out.println("   public "+Name+"()");
            out.println("   {");  
            out.println("   }");  
            
            out.println("   ");  
            out.println("   public boolean submitData(VData cInputData, String cOperate)");
            out.println("   {");  
            out.println("       mTransferData = (TransferData) cInputData.getObjectByObjectName(\"TransferData\", 0);");  
            out.println("       if (mTransferData == null) "); 
            out.println("       {"); 
            out.println("           CError tError = new CError();"); 
            out.println("           tError.moduleName = \""+Name+"\";"); 
            out.println("           tError.functionName = \"CreateFile\";"); 
            out.println("           tError.errorMessage = \"前台传输业务数据失败!\";");  
            out.println("           this.mErrors.addOneError(tError);"); 
            out.println("           return false;");  
            out.println("       }"); 
            out.println("       ");  
            out.println("       //调用BLF 进行逻辑处理,此处有开发人员根据具体情况进行修改");  
            out.println("       //XXXBLF tXXXBLF=new XXXBLF();");  
            out.println("       //if(tXXXBLF.submitData(VData cInputData, String cOperate))"); 
            out.println("       //{");  
            out.println("       //    mResult=tXXXBLF.getResult();");
            out.println("       //}");  
            out.println("       //else");  
            out.println("       //{");  
            out.println("       //    this.mErrors.copyAllErrors(tXXXBLF.mErrors);"); 
            out.println("       //    return false;");
            out.println("       //}");
            out.println("       return true;");  
            out.println("   }"); 
            
            out.println("   ");  
            out.println("   public VData getResult()");
            out.println("   {");
            out.println("       return mResult;");
            out.println("   }");
            
            out.println("   ");  
            out.println("   public TransferData getReturnTransferData()");
            out.println("   {");
            out.println("       return mTransferData;");
            out.println("   }");
            
            out.println("   ");  
            out.println("   public CErrors getErrors()");
            out.println("   {");
            out.println("       return mErrors;");
            out.println("   }");
            
            out.println("}");  
            out.close();
    	}
    	catch(Exception ex)
    	{
			CError tError = new CError();
			tError.moduleName = "ServiceFileBL";
			tError.functionName = "CreateFile";
			tError.errorMessage = ex.toString();
			this.mErrors.addOneError(tError);       		
    		return false;
    	}
    	
        return true; 	
    }
    public boolean DeleteFile(String Package,String Name)
    {
    	if(FilePath==null||FilePath.equals(""))
    	{
    		if(!GetPath())
    		{
    			return false;
    		}
    	}
    	File tFile=new File(FilePath+Package.replace('.', '\\')+"\\"+Name+".java");
    	if(tFile.exists())
    	{
    		if(!tFile.delete())
    		{
    			CError tError = new CError();
    			tError.moduleName = "ServiceFileBL";
    			tError.functionName = "CreateFile";
    			tError.errorMessage = "文件删除失败！";
    			this.mErrors.addOneError(tError);       		
        		return false;    			
    		}
    	}  
    	return true;
    }
}
