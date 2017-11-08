

package com.sinosoft.productdef;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import com.sinosoft.lis.db.LDRiskToRateDB;
import com.sinosoft.lis.db.LDTaskRunLogDB;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LDRiskToRateSchema;
import com.sinosoft.lis.schema.LDTaskRunLogSchema;
import com.sinosoft.lis.vschema.LDRiskToRateSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class LDRiskToRateReleaseBL  {
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	
	/** 往前台传输数据的容器 */
	private VData mResult = new VData();
	private String filePath;
	private String Transact;
	private String fileName;
	private String riskCode;
	private String rateType;
	private String rateTable;
	private String changeReason ;
	private String auditConclusion;
	private String [][] rateResult;
	private String currentDate=PubFun.getCurrentDate();
	private String currentTime=PubFun.getCurrentTime();
	SSRS result=new SSRS();
	SSRS tSSRS=new SSRS();
	LDTaskRunLogSchema mLDTaskTunlogSchema=new  LDTaskRunLogSchema();
	LDTaskRunLogDB mLDTaskRunLogDB=new LDTaskRunLogDB();
	String mCurrentDate = PubFun.getCurrentDate();
	
	public LDRiskToRateReleaseBL ()
	{
	}
	public boolean submitData(VData cInputData, String Transact){
		this.Transact=Transact;
		try {
			if( !getInputData(cInputData) ) 
			{
		        return false;
		    }

			if( !dealData() ) 
			{
		        return false;
		    }
			
	
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		return true;

	}
	
	private boolean getInputData(VData cInputData)
	{	
		TransferData tTransferData = (TransferData) cInputData.getObjectByObjectName("TransferData", 0);
		String parameter = (String) tTransferData.getValueByName("parameter");
		changeReason = (String) tTransferData.getValueByName("changeReason");
		auditConclusion = (String) tTransferData.getValueByName("changeReason");
		String [] prt=new String [3]; 
		prt=parameter.split(",");
		riskCode=prt[0];
		rateType=prt[1];
		rateTable=prt[2];
		filePath="/lis/Interface/Output/BatchLog";
		currentTime=currentTime.replace(":","-");
		ExeSQL tExeSQL = new ExeSQL();		
		String sql1="SELECT column_name FROM user_tab_columns where table_name ='"+rateTable+"'";
		 
		tSSRS=tExeSQL.execSQL(sql1);
		rateResult=tSSRS.getAllData();
		
		String sql="";
			sql="SELECT * FROM "+rateTable;
		result= tExeSQL.execSQL(sql);
//		System.out.println(result.getMaxRow()+"@@@@@@@@@@@");
		System.out.println(result.getMaxRow()+"@@@@@@@@@@@@@@@@@@");
		if(result.getMaxRow()<=0){
			CError tError = new CError();
			tError.moduleName = "LDRiskToRateReleaseBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "没有对应的费率信息！";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}
	
	private boolean dealData(){
		fileName=rateTable+".csv";
		isFileExist(filePath);	
		if("DATAD".equals(this.Transact)){
			try {
				String data="";
				ExeSQL tExeSQL = new ExeSQL();						
				for(int j=0;j<tSSRS.getMaxRow();j++){
					if(""==data){
						data=data+rateResult[j][0];
					}else{
						data=data+","+rateResult[j][0];
					}		
				}
				data=data+"\r";
				
				 FileWriter fw = new FileWriter(filePath+"/"+fileName);
					for(int i=1;i<=result.getMaxRow();i++){
						for(int j=1;j<=result.getMaxCol();j++){
							if(result.GetText(i, j)==null||result.GetText(i, j).trim().length()<=0||result.GetText(i, j)==""){
								continue;
							}else{
								if(""==data||data.endsWith("\r")){
									data=data+result.GetText(i, j);									
								}else{
									data=data+","+result.GetText(i, j);
								}
		
							}
						}
						data=data+"\r";
					}
				 System.out.println(data);
				 fw.write(data);
				 fw.close();
				
				mResult.add(filePath);
				mResult.add(fileName);
				
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
			return true;
		}else if("RATED".equals(this.Transact)){
			fileName=rateTable+".sql";
			String ziduan="";
			for(int j=0;j<tSSRS.getMaxRow();j++){
				if(""==ziduan){
					ziduan=ziduan+rateResult[j][0];
				}else{
					ziduan=ziduan+","+rateResult[j][0];
				}		
			}
			String sql="select * from "+ rateTable;
			ExeSQL tExeSQL = new ExeSQL();
			result= tExeSQL.execSQL(sql);	
			String [][] rs=(String [][])result.getAllData();
			String s="";
			String sql2="";
			for(int j=0;j<result.MaxRow;j++){
				for(int i=0;i<result.MaxCol;i++){
					if(""==s){
						s=s+"date'"+rs[j][i]+"'";
					}else{
						if(0==i||1==i){
							s=s+",date'"+rs[j][i]+"'";
						}else{
							s=s+",'"+rs[j][i]+"'";
						}						
					}
				}
				if(""==sql2){
					sql2=sql2+"insert into "+rateTable+" ( "+ziduan+")values("+s+");";	
					s="";
				}else{
					sql2=sql2+"\r\n"+"insert into "+rateTable+" ( "+ziduan+")values("+s+");";		
					s="";
				}
			}				
				String sql3="delete from "+rateTable+";";
			try {
				StringBuffer sb = new StringBuffer("");
				FileWriter writer = new FileWriter(filePath+"/"+fileName);
				BufferedWriter bw = new BufferedWriter(writer);
				sql3=sql3+"\r\n"+sql2;
				
				sb.append(sql3);
				bw.write(sb.toString());
				bw.newLine();
				bw.flush();
				bw.close();
				writer.close();		
				
				mResult.add(filePath);
				mResult.add(fileName);
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
			return true;
		}else{
			return false;
		}
		
	}
	//	判断文件夹是否存在,如果不存在则创建文件夹
	public static void isFileExist(String path) {
		  File file = new File(path);
		  if (!file.exists()) {
		   file.mkdir();
		  }
		 }

	public boolean logicBL() {
		// TODO Auto-generated method stub
		return false;
	}
	public VData getResult() {
		// TODO Auto-generated method stub
		return this.mResult;
	}
	
	 public static void main(String[] args) {
		 LDRiskToRateReleaseBL ldt=new LDRiskToRateReleaseBL();
		 ldt.isFileExist("/lis/Interface/Output/批处理日志/"+ldt.mCurrentDate);
		 
//		 VData tVData = new VData();
//		 tVData.add(TaskCode);
//		 tVData.add(BussDate);
//		 ldt.submitData(TaskCode, BussDate);
	}

	
}

