package com.sinosoft.lis.sys;

import java.sql.SQLException;

import com.sinosoft.lis.db.LDSysVarDB;
import com.sinosoft.lis.schema.LDSysVarSchema;
import com.sinosoft.lis.vschema.LDSysVarSet;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>表示 qcloud 腾讯云对象存储的设置的业务逻辑</p>
 * @author Wang Zhang
 *
 */
public class CloudObjectStorageQCloudBL implements BusinessService {
	public CErrors mErrors = new CErrors();
	private static final String APP_ID_SYSVAR = "QCloudAppId";
	private static final String SECRET_ID_SYSVAR = "QCloudSecretId";
	private static final String SECRET_KEY_SYSVAR = "QCloudSecretKey";
	private static final String BUCKET_NAME_SYSVAR = "QCloudBucketName";
	private static final String ACCESS_CHANNEL_SYSVAR = "QCloudAccessChannel";
	private static final String BASE_SELECT_STRING = "SELECT SYSVARVALUE FROM Ldsysvar WHERE SYSVAR = ";
	private TransferData mTransferData = null;
	private String appIdSysVarValue = null;
	private String secretIdSysVarValue = null;
	private String secretKeySysVarValue = null;
	private String bucketNameSysVarValue =  null;
	private String accessChannelSysVarValue =  null;
	private VData mResult = new VData();
	
	/**
	 * 从输入的vData中获取信息
	 * @param vData 表示一个一维向量，当中应当存储有一个TransferData对象
	 * @param operator 表示SELECT或UPDATE操作
	 * @return 成功获取到数据则返回 true, 否则返回 false.
	 */
	private boolean getData(VData vData, String operator){
		if(operator == null || operator.isEmpty() || operator.equalsIgnoreCase("SELECT"))
			return true;
		Object inputData = vData.getObjectByObjectName("TransferData", 0);
		if(inputData == null || !(inputData instanceof TransferData)){
		    CError.buildErr(this, "无法从所传数据中找到类型为TransferData的对象");
		    return false;
		}
		this.mTransferData = (TransferData)inputData;
		Object inputAppIdSysVarValue = this.mTransferData.getValueByName("AppId");
		Object inputSecretIdSysVarValue = this.mTransferData.getValueByName("SecretId");
		Object inputSecretKeySysVarValue = this.mTransferData.getValueByName("SecretKey");
		Object inputBucketNameSysVarValue = this.mTransferData.getValueByName("BucketName");
		Object inputAccessChannelSysVarValue = this.mTransferData.getValueByName("AccessChannel");
        if(inputAppIdSysVarValue != null)
        	this.appIdSysVarValue = inputAppIdSysVarValue.toString();
        if(inputSecretIdSysVarValue != null)
        	this.secretIdSysVarValue = inputSecretIdSysVarValue.toString();
        if(inputSecretKeySysVarValue != null)
        	this.secretKeySysVarValue = inputSecretKeySysVarValue.toString();
        if(inputBucketNameSysVarValue != null)
        	this.bucketNameSysVarValue = inputBucketNameSysVarValue.toString();
        if(inputAccessChannelSysVarValue != null)
        	this.accessChannelSysVarValue = inputAccessChannelSysVarValue.toString();
		return true;
	}

	/**
	 * <p>补完 SQL 的 Select String.</p>
	 * @param sysVar <span>Ldsysvar 表中的 SYSVAR 列的值。可以是静态字段 <i style="color:#0000C0;">APP_ID_SYSVAR</i>, <i style="color:#0000C0;">SECRET_ID_SYSVAR</i>, <i style="color:#0000C0;">SECRET_KEY_SYSVAR</i>, <i style="color:#0000C0;">BUCKET_NAME_SYSVAR</i> 和 <i style="color:#0000C0;">ACCESS_CHANNEL_SYSVAR</i> 中的任意一个的值。</span>
	 * @return 补完的 SQL 语句，例如 <i>SELECT SYSVARVALUE FROM Ldsysvar WHERE SYSVAR = 'QCloudAppId'</i>
	 */
	private String completeSelectString(String sysVar){
		String param = sysVar.trim();
		return BASE_SELECT_STRING + "\'" + param + "\'";
	}
	
	/**
	 * 查询 LdSysVar 表，并获取到 <i>AppId</i>, <i>SecretId</i>, <i>SecretKey</i>, <i>BucketName</i> 和 <i>AccessChannel</i> 这五个值。
	 * @return 一个集合，存有 <i>AppId</i>, <i>SecretId</i>, <i>SecretKey</i>, <i>BucketName</i> 和 <i>AccessChannel</i> 这五个键以及对应的数值。
	 */
	private java.util.Map<String, String> queryLdSysVar(){
		java.util.HashMap<String, String> result = new java.util.HashMap<String, String>();
		com.sinosoft.utility.ExeSQL esql = new com.sinosoft.utility.ExeSQL();
	    String appId = null;
	    String secretId = null;
	    String secretKey = null;
	    String bucketName = null;
	    String accessChannel = null;
	    try{
	    	appId = esql.getOneValue(completeSelectString(APP_ID_SYSVAR));
	    	secretId = esql.getOneValue(completeSelectString(SECRET_ID_SYSVAR));
	    	secretKey = esql.getOneValue(completeSelectString(SECRET_KEY_SYSVAR));
	    	bucketName = esql.getOneValue(completeSelectString(BUCKET_NAME_SYSVAR));
	    	accessChannel = esql.getOneValue(completeSelectString(ACCESS_CHANNEL_SYSVAR));
	    	result.put("AppId", appId);
	    	result.put("SecretId", secretId);
	    	result.put("SecretKey", secretKey);
	    	result.put("BucketName", bucketName);
	    	result.put("AccessChannel", accessChannel);
	    	return result;
	    }catch(Exception e){
	    	CError.buildErr(this, e.getMessage());
	    	return null;
	    }
	}
	
	/**
	 * <p>根据所给出的 Update 语句和参数执行 UPDATE 操作</p>
	 * @param updateStatement &nbsp;一个 <a href="eclipse-javadoc:%E2%98%82=lis65_SourceCodeNew/java%3Ccom.sinosoft.lis.sys%7BCloudObjectStorageQCloudBL.java%E2%98%83CloudObjectStorageQCloudBL~execUpdate~Qjava.sql.PreparedStatement;~QString;~QString;%E2%98%82java.sql.PreparedStatement">PreparedStatement</a>
	 * 对象。创建自SQL语句: &nbsp;&nbsp;&nbsp;&nbsp;<i style="white-space:nowrap">"UPDATE Ldsysvar SET SysVarValue = ? WHERE SysVar = ?"</i>
	 * @param value1 <span>Update 语句的第一个参数。</span>
	 * @param value2 <span>Update 语句的第二个参数。</span>
	 * @throws SQLException
	 */
	private void execUpdate(java.sql.PreparedStatement updateStatement, String value1, String value2) throws SQLException{
		if(value1 == null){
			updateStatement.setNull(1, java.sql.Types.VARCHAR);
		}else{
			updateStatement.setString(1, value1);
		}
		updateStatement.setString(2, value2);
		updateStatement.executeUpdate();
	}
	
	/**
	 * <p>根据所给出的 Insert 语句和参数执行 INSERT 操作</p>
	 * @param insertStatement &nbsp;一个 <a href="eclipse-javadoc:%E2%98%82=lis65_SourceCodeNew/java%3Ccom.sinosoft.lis.sys%7BCloudObjectStorageQCloudBL.java%E2%98%83CloudObjectStorageQCloudBL~execUpdate~Qjava.sql.PreparedStatement;~QString;~QString;%E2%98%82java.sql.PreparedStatement">PreparedStatement</a>
	 * 对象。创建自SQL语句: &nbsp;&nbsp;&nbsp;&nbsp;<i style="white-space:nowrap">"INSERT INTO Ldsysvar (SysVar, SysVarValue) VALUES (?, ?)"</i>
	 * @param value1 <span>Update 语句的第一个参数。</span>
	 * @param value2 <span>Update 语句的第二个参数。</span>
	 * @throws SQLException
	 */
	private void execInsert(java.sql.PreparedStatement insertStatement, String value1, String value2) throws SQLException{
		insertStatement.setString(1, value1);
		if(value2 == null){
			insertStatement.setNull(2, java.sql.Types.VARCHAR);
		}else{
			insertStatement.setString(2, value2);
		}
		insertStatement.executeUpdate();
	}
	
	/**
	 * <p>自动根据所给出的 exist 参数进行判断并做出 UPDATE 或 INSERT 操作</p>
	 * @param exist <span>表示所要操作的行在数据库中是否已存在。若为 true 则会执行 UPDATE 操作，反之则会执行 INSERT 操作。</span>
	 * @param updateStatement <br/><span> &nbsp;一个 <a href="eclipse-javadoc:%E2%98%82=lis65_SourceCodeNew/java%3Ccom.sinosoft.lis.sys%7BCloudObjectStorageQCloudBL.java%E2%98%83CloudObjectStorageQCloudBL~execUpdate~Qjava.sql.PreparedStatement;~QString;~QString;%E2%98%82java.sql.PreparedStatement">PreparedStatement</a>
	 * 对象。创建自SQL语句: &nbsp;&nbsp;&nbsp;&nbsp;<i style="white-space:nowrap">"UPDATE Ldsysvar SET SysVarValue = ? WHERE SysVar = ?"</i></span>
	 * @param insertStatement <br/><span> &nbsp;一个 <a href="eclipse-javadoc:%E2%98%82=lis65_SourceCodeNew/java%3Ccom.sinosoft.lis.sys%7BCloudObjectStorageQCloudBL.java%E2%98%83CloudObjectStorageQCloudBL~execUpdate~Qjava.sql.PreparedStatement;~QString;~QString;%E2%98%82java.sql.PreparedStatement">PreparedStatement</a>
	 * 对象。创建自SQL语句: &nbsp;&nbsp;&nbsp;&nbsp;<i style="white-space:nowrap">"INSERT INTO Ldsysvar (SysVar, SysVarValue) VALUES (?, ?)"</i></span>
	 * @param key <br/><span>Ldsysvar 表中的 SYSVAR 列的值。可以是静态字段 <i style="color:#0000C0;">APP_ID_SYSVAR</i>, <i style="color:#0000C0;">SECRET_ID_SYSVAR</i>, <i style="color:#0000C0;">SECRET_KEY_SYSVAR</i>, <i style="color:#0000C0;">BUCKET_NAME_SYSVAR</i> 和 <i style="color:#0000C0;">ACCESS_CHANNEL_SYSVAR</i> 中的任意一个的值。</span>
	 * @param value <br/><span>表中SysVarValue所要存的值。</span>
	 * @throws SQLException
	 */
	private void autoUpdateOrInsert(boolean exist, java.sql.PreparedStatement updateStatement,java.sql.PreparedStatement insertStatement, String key, String value) throws SQLException{
		if(exist){
			this.execUpdate(updateStatement, value, key);
		}else{
			this.execInsert(insertStatement, key, value);
		}
	}
	
	/**
	 * <p>查询 LdSysVar 表, 判断所给的SysVar的值的行是否存在。</p>
	 * @param sysVar <span>Ldsysvar 表中的 SYSVAR 列的值。可以是静态字段 <i style="color:#0000C0;">APP_ID_SYSVAR</i>, <i style="color:#0000C0;">SECRET_ID_SYSVAR</i>, <i style="color:#0000C0;">SECRET_KEY_SYSVAR</i>, <i style="color:#0000C0;">BUCKET_NAME_SYSVAR</i> 和 <i style="color:#0000C0;">ACCESS_CHANNEL_SYSVAR</i> 中的任意一个的值。</span>
	 * @param con 一个 <a href="eclipse-javadoc:%E2%98%82=lis65_SourceCodeNew/java%3Ccom.sinosoft.lis.sys%7BCloudObjectStorageQCloudBL.java%E2%98%83CloudObjectStorageQCloudBL~execUpdate~Qjava.sql.PreparedStatement;~QString;~QString;%E2%98%82java.sql.Connection">Connection</a>
	 * 对象。用于链接到数据库。
	 * @return 所给的SysVar的值的行是否存在。存在则返回true, 否则返回 false.
	 */
	private boolean existRow(String sysVar, java.sql.Connection con){
		LDSysVarSchema lDSysVarSchema = null;
		LDSysVarDB lDSysVarDB = null;
		if(con == null)    
			lDSysVarDB = new LDSysVarDB();
		else
			lDSysVarDB = new LDSysVarDB(con);
		lDSysVarDB.setSysVar(sysVar);
		LDSysVarSet lDSysVarSet = lDSysVarDB.query();
		if(lDSysVarSet == null || lDSysVarSet.size() == 0){
			return false;
		}
		for(int i = 1; i <= lDSysVarSet.size(); i++){
			lDSysVarSchema = lDSysVarSet.get(i);
			if(lDSysVarSchema != null){
				break;
			}
		}
		return (lDSysVarSchema != null);
	}
	
	/**
	 * <p>更新 LdSysVar 表</p>
	 * @return <span>如果成功则返回 true, 否则返回 false.</span>
	 */
	private boolean updateLdSysVar() {
		boolean existAppId = false;
		boolean existSecretId = false;
		boolean existSecretKey = false;
		boolean existBucketName = false;
		boolean existAccessChannel = false;
		java.sql.Connection con = null;
		String updateString = "UPDATE Ldsysvar SET SysVarValue = ? WHERE SysVar = ?";
		String insertString = "INSERT INTO Ldsysvar (SysVar, SysVarValue) VALUES (?, ?)";
		java.sql.PreparedStatement updateStatement = null;
		java.sql.PreparedStatement insertStatement = null;
		try{
			con = com.sinosoft.utility.DBConnPool.getConnection();
			con.setAutoCommit(false);
			existAppId = existRow(APP_ID_SYSVAR, con);
			existSecretId = existRow(SECRET_ID_SYSVAR, con);
			existSecretKey = existRow(SECRET_KEY_SYSVAR, con);
			existBucketName = existRow(BUCKET_NAME_SYSVAR, con);
			existAccessChannel = existRow(ACCESS_CHANNEL_SYSVAR, con);
			updateStatement = con.prepareStatement(updateString);
			insertStatement = con.prepareStatement(insertString);

			this.autoUpdateOrInsert(existAppId, updateStatement, insertStatement, APP_ID_SYSVAR, this.appIdSysVarValue);
			this.autoUpdateOrInsert(existSecretId, updateStatement, insertStatement, SECRET_ID_SYSVAR, this.secretIdSysVarValue);
			this.autoUpdateOrInsert(existSecretKey, updateStatement, insertStatement, SECRET_KEY_SYSVAR, this.secretKeySysVarValue);
			this.autoUpdateOrInsert(existBucketName, updateStatement, insertStatement, BUCKET_NAME_SYSVAR, this.bucketNameSysVarValue);
			this.autoUpdateOrInsert(existAccessChannel, updateStatement, insertStatement, ACCESS_CHANNEL_SYSVAR, this.accessChannelSysVarValue);
			con.commit();
			return true;
   		}catch(Exception e){
			CError.buildErr(this, e.getMessage());
	    	return false;
		} finally{
			try{
				if(updateStatement != null){
					updateStatement.close();
				}
			}catch(Exception ee){}
			try{
				if(insertStatement != null){
					insertStatement.close();
				}
			}catch(Exception ee){}
			try{
				if(con != null){
					con.setAutoCommit(true);
				}
			}catch(Exception ee){}
			try{
				if(con != null){
					con.close();
				}
			}catch(Exception ee){}
		}
	}
	
	/**
	 * 根据操作符进行查询或更新操作 (在尝试更新时若数据不存在则尝试插入新数据).
	 * @param operator <span>表示所要进行的操作 SELECT 或 UPDATE</span>
	 * @return 操作成功则返回true, 否则返回false
	 */
	@SuppressWarnings("unchecked")	
	private boolean dealData(String operator){
		try{
		    if(operator == null || operator.isEmpty() || operator.equalsIgnoreCase("SELECT")){
			    java.util.Map<String, String> queryResult = this.queryLdSysVar();
			    if(queryResult == null || queryResult.isEmpty()) {
			    	throw new Exception("没有查询到所请求的数据。");
			    } else {
			    	this.mResult.add(queryResult);
			    	return true;
			    }
		    } else if (operator.equalsIgnoreCase("UPDATE")) {
		    	if(!this.updateLdSysVar()) {
		    		throw new Exception("更新数据库时失败。");
		    	} else {
		    		return true;
		    	}
		    } else {
		    	throw new Exception ("出现未知操作符 " + operator + "");
		    }
		}catch(Exception e){
			CError.buildErr(this, e.getMessage());
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean submitData(VData vData, String Operater) {
		// TODO Auto-generated method stub
		if(this.getData(vData, Operater) && this.dealData(Operater)){
			return true;
		} else {
			this.mResult.add("Fail");
			return false;
		}
	}

	@Override
	public VData getResult() {
		// TODO Auto-generated method stub
		return this.mResult;
	}

	@Override
	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return this.mErrors;
	}
}
