package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.SQLException;

import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.CMySQLBlob;
import com.sinosoft.utility.COracleBlob;
import com.sinosoft.utility.DBConnPool;
import com.sinosoft.utility.SysConst;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class BqPrintBL {
private static Logger logger = Logger.getLogger(BqPrintBL.class);
	private VData mInputData = new VData();

	public CErrors mErrors = new CErrors();
	
	private TransferData mTransferData;

	private VData mResult = new VData();
	private BqPrintBean bqb = null;
	private String mEdorNo = "";
	private String mTableName = "";
	private String mColName = "";
	public VData getResult() {
		return mResult;
	}
	public CErrors getErrors() {
		return mErrors;
	}
	
	public boolean submitData(VData cInputData, String cOperate) {
		
		mInputData = (VData) cInputData.clone();
		
		if (!getInputData()) {
			return false;
		}
		// 数据准备操作
		if (!dealData()) {
			return false;
		}
		
		return true;
	}
	
	private boolean dealData() {
		Connection conn = DBConnPool.getConnection();
		InputStream ins = null;
		ByteArrayOutputStream bsm = null;
		Blob tBlob = null;  
		byte[] bytes = null;
		try {

			if( conn == null ){ 
				CError.buildErr(this, "数据库创建连接失败！");
				return false;
			}
			else{
				//输出数据文件
				COracleBlob tCOracleBlob = new COracleBlob();
				CMySQLBlob tCMySQLBlob = new CMySQLBlob();
				
				String tSQL = " and EdorNo = '" + mEdorNo + "'";
				if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
					tBlob = tCOracleBlob.SelectBlob(mTableName,mColName,tSQL,conn);
				}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
					tBlob = tCMySQLBlob.SelectBlob(mTableName,mColName,tSQL,conn);		
				}
				ins=tBlob.getBinaryStream();
				bsm = new ByteArrayOutputStream();  
				int read = 0;
				while((read = ins.read())!=-1){
					bsm.write(read);
				}
				bytes = bsm.toByteArray();
				bqb = new BqPrintBean();
				bqb.setBytes(bytes);
				conn.close(); 
				bsm.close();
			}
		}catch(Exception ex){
			try {
				if (conn!=null) {
					conn.close();
				}
				if (ins!=null) {
					ins.close();
				}
				if (bsm!=null) {
					bsm.close();
				}
			} 
			catch (Exception ext){
				ext.printStackTrace();
			}
		}finally{
			try {
				if (conn!=null) {
					conn.close();
				}
				if (ins!=null) {
					ins.close();
				}
				if (bsm!=null) {
					bsm.close();
				}
			} 
			catch (Exception ex){
				ex.printStackTrace();
			}
		}
		if(bqb != null){

			mResult.add(bqb);
		}
			
		return true;
	}
	private boolean getInputData() {

		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		mEdorNo = (String)mTransferData.getValueByName("EdorNo");
		mTableName = (String)mTransferData.getValueByName("TableName");
		mColName = (String)mTransferData.getValueByName("ColName");
		return true;
	}
	
	public static void main(String[] args){
		byte[] bytes = new byte[2];
		bytes[0] = 1;
		bytes[1] = 0;
		BqPrintBean bpb = new BqPrintBean();
		bpb.setBytes(bytes);
		VData data = new VData();
		data.add(bpb);
		BqPrintBean bpb1 = (BqPrintBean)data.getObjectByObjectName("BqPrintBean", 0);
		logger.debug(bpb1.getBytes()[0]+"____"+bpb1.getBytes()[1]);
	}
}
