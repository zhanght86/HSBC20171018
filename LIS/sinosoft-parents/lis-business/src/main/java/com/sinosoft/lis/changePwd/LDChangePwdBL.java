package com.sinosoft.lis.changePwd;
import org.apache.log4j.Logger;

import java.sql.Connection;

import com.sinosoft.lis.db.LDPwdBDB;
import com.sinosoft.lis.db.LDUserDB;
import com.sinosoft.lis.encrypt.LisEncrypt;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LDPwdBSchema;
import com.sinosoft.lis.schema.LDUserSchema;
import com.sinosoft.lis.vdb.LDPwdBDBSet;
import com.sinosoft.lis.vschema.LDPwdBSet;
import com.sinosoft.lis.vschema.LDUserSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.DBConnPool;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SysConst;
import com.sinosoft.utility.VData;

import java.util.Date;
import java.util.regex.*;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description:
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author DingZhong
 * @version 1.0
 */
public class LDChangePwdBL {
private static Logger logger = Logger.getLogger(LDChangePwdBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mResult = new VData();
	// private VData mInputData = new VData();
	/** 数据操作字符串 */
	// private String mOperate;
	/** 业务处理相关变量 */
	/** 用户的相关信息 */
	LDUserSchema mLDOldUserSchema = new LDUserSchema();
	LDUserSchema mLDNewUserSchema = new LDUserSchema();

	String mResultStr = "";
	int mResultNum = 0;
	
	//tongmeng 2011-02-23 modify
	private int mMaxCheckTime = 3;
	public LDChangePwdBL() {
		// just for debug
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param cInputData
	 *            VData
	 * @param cOperate
	 *            String
	 * @return boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 判断操作是不是查询
		if (cOperate.compareTo("changePwd") != 0) {
			return false;
		}

		logger.debug("start BL submit...");

		// 将操作数据拷贝到本类中
		// this.mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}
//		if(!checkPwdComplex(mLDOldUserSchema.getUserCode(), mLDNewUserSchema.getPassword())){
//			this.mErrors.addOneError("新密码复杂度不符合要求");
//			return false;
//		}
	//	mLDOldUserSchema.setPassword(LisEncrypt.encrypt(mLDOldUserSchema.getPassword()));
	//	mLDNewUserSchema.setPassword(LisEncrypt.encrypt(mLDNewUserSchema.getPassword()));
		
		logger.debug("start dealData");
		// 进行业务处理
		if (!dealData()) {
			return false;
		}

		logger.debug("After dealData！");

		return true;
	}

	private boolean dealData() {

		String usercode = mLDOldUserSchema.getUserCode();
		logger.debug("oldPwd plain is :" + mLDOldUserSchema.getPassword());
		// LisIDEA tLisIdea = new LisIDEA();

		String oldpwd = mLDOldUserSchema.getPassword();
		// oldpwd = tLisIdea.encryptString(oldpwd);

		// logger.debug("usercode:" + usercode);
		// logger.debug("oldpwd:" + oldpwd);
		// logger.debug(mLDNewUserSchema.getPassword());

		// String sqlStr = "select * from lduser where usercode = '" + usercode
		// +
		// "' and password = '" + oldpwd + "'";
//		StringBuffer tSBql = new StringBuffer();
//		tSBql.append("select * from lduser where usercode =  '");
//		tSBql.append(usercode);
//		tSBql.append("' and password = '");
//		tSBql.append(oldpwd);
//		tSBql.append("'");

		// logger.debug(sqlStr);
		LDUserDB tLDUserDB1 = new LDUserDB();
		tLDUserDB1.setUserCode(usercode);
		if(!tLDUserDB1.getInfo()){
			this.mErrors.addOneError("用户编码不存在");
			return false;
		}
		LDUserSchema tLDUserSchema = tLDUserDB1.getSchema();
		if(!tLDUserSchema.getPassword().equals(oldpwd)){
			this.mErrors.addOneError("原密码输入错误");
			return false;
		}
		
		// logger.debug("old password is right");

		String newpwd = mLDNewUserSchema.getPassword();
		// logger.debug("newpwd :" + newpwd);
		if (newpwd.equals("")) {
			this.mErrors.addOneError("新密码为空");
			return false;
		}

		tLDUserSchema.setPassword(newpwd);
		tLDUserSchema.setModifyDate(PubFun.getCurrentDate());
		tLDUserSchema.setModifyTime(PubFun.getCurrentTime());
		
		
		//tongmeng 2011-02-23 modify
	       //先查询看看有多少条记录
        //做密码修改的历史备份数据
        LDPwdBSchema tLDPwdBSchema = new LDPwdBSchema();
        String tSQL = "select * from LDPwdB where usercode = '"+"?usercode?"+"' order by pwdtimes/1 desc";
        SQLwithBindVariables sqlbv = new SQLwithBindVariables();
        sqlbv.sql(tSQL);
        sqlbv.put("usercode", usercode);
        logger.debug("查询有没有历史记录-----------------"+tSQL);
        LDPwdBDB tLDPwdBDB = new LDPwdBDB();
        LDPwdBSet tLDPwdBSet = new LDPwdBSet();
        LDPwdBSet tPutLDPwdBSet = new LDPwdBSet();
        
  //      LDPwdBSet tPutLDPwdBSet_insert = new LDPwdBSet();
        
        tLDPwdBSet = tLDPwdBDB.executeQuery(sqlbv);
        boolean singleSchema = false;
        if(tLDPwdBSet != null && tLDPwdBSet.size() > 0)
        {
            for(int i = 0 ; i < tLDPwdBSet.size() ; i++)
            {
            	if(i+1>this.mMaxCheckTime)
            	{
            		break;
            	}
            	
                if(tLDPwdBSet.get(i+1).getPassword().equals(newpwd))
                {
                    CError tError = new CError();
                    tError.moduleName = "LDChangePwdBL";
                    tError.functionName = "dealData";
                    tError.errorMessage = "确认现密码与最近"+this.mMaxCheckTime+"次密码中的一次相同";
                    this.mErrors.addOneError(tError);
                    return false;
                }
            }
        }
        else
        {
            tLDPwdBSchema.setUserCode(tLDUserSchema.getUserCode());
            tLDPwdBSchema.setComCode(tLDUserSchema.getComCode());
            tLDPwdBSchema.setPassword(oldpwd);
            tLDPwdBSchema.setPwdTimes("1");
            tLDPwdBSchema.setMakeDate(PubFun.getCurrentDate());
            tLDPwdBSchema.setMakeTime(PubFun.getCurrentTime());
            tLDPwdBSchema.setOperator(tLDUserSchema.getUserCode());
            tPutLDPwdBSet.add(tLDPwdBSchema);
            singleSchema = true;
            
        }
        if(!singleSchema)
        {
        	
        	//第一条记录就是最近一次的修改
        	
        	String ChangeTimes =  tLDPwdBSet.get(1).getPwdTimes();
            int ChangeTime = Integer.parseInt(ChangeTimes)+1;
        	     
            tLDPwdBSchema.setUserCode(tLDUserSchema.getUserCode());
			tLDPwdBSchema.setComCode(tLDUserSchema.getComCode());
			tLDPwdBSchema.setPassword(oldpwd);
			tLDPwdBSchema.setPwdTimes(String.valueOf(ChangeTime));
			tLDPwdBSchema.setMakeDate(PubFun.getCurrentDate());
			tLDPwdBSchema.setMakeTime(PubFun.getCurrentTime());
			tLDPwdBSchema.setOperator(tLDUserSchema.getUserCode());
			
            tPutLDPwdBSet.add(tLDPwdBSchema);
        	for(int j = 1 ; j < this.mMaxCheckTime; j++)
            {
        		tPutLDPwdBSet.add(tLDPwdBSet.get(j));
            }
        }
        
		// 开始更新用户密码
		Connection conn = DBConnPool.getConnection();
		if (conn == null) {
			logger.debug("更新密码连接数据库失败！");
			return false;

		}
		try {
			conn.setAutoCommit(false);
			// logger.debug("Start 更新用户密码...");
			LDUserDB tLDUserDB = new LDUserDB(conn);
			tLDUserDB.setSchema(tLDUserSchema);

			// 更新菜单组表
			if (!tLDUserDB.update()) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LDChangePwdBL";
				tError.functionName = "dealData";
				tError.errorMessage = "用户密码更新失败!";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();
				return false;
			}
			
			if(!"ORACLE".equalsIgnoreCase(SysConst.DBTYPE)){
				ExeSQL tExeSQL = new ExeSQL(conn);
    			SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
    			String tSql2 = "insert into lduser_trace (operatetype, operatetime, usercode, ip, id) values ('?operateType?','?opertateTime?','?userCode?','?ip?', '?id?') ";
    			sqlbv2.sql(tSql2);
    			sqlbv2.put("operateType", "UPDATE");
    			sqlbv2.put("opertateTime", PubFun.getCurrentDate()+" "+PubFun.getCurrentTime());
    			sqlbv2.put("userCode", tLDUserSchema.getUserCode());
    			String ipAndId = tExeSQL.getOneValue("select USER() from dual");
    			String[] ipId = ipAndId.split("@");
    			sqlbv2.put("ip", ipId[1]);
    			sqlbv2.put("id", ipId[0]);
    			tExeSQL.execUpdateSQL(sqlbv2);
    		}
			
			//删除多余的
			String tDeleteSQL = "delete from LDPwdB where usercode = '"+"?usercode?"+"' "; 
			SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
			sqlbv1.sql(tDeleteSQL);
			sqlbv1.put("usercode", usercode);
			ExeSQL tExeSQL = new ExeSQL(conn);
			if(!tExeSQL.execUpdateSQL(sqlbv1))
			{
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LDChangePwdBL";
				tError.functionName = "dealData";
				tError.errorMessage = "用户轨迹删除失败!";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();
				return false;
				
			}
			 LDPwdBDBSet t1LDPwdBDBSet = new LDPwdBDBSet(conn);
	         t1LDPwdBDBSet.set(tPutLDPwdBSet);
	         //t1LDPwdBDBSet.delete();
	            if (!t1LDPwdBDBSet.insert())
	            {
	                // @@错误处理
	                CError tError = new CError();
	                tError.moduleName = "LDChangePwdBL";
	                tError.functionName = "dealData";
	                tError.errorMessage = "用户表备份表密码更新失败!";
	                this.mErrors.addOneError(tError);
	                conn.rollback();
	                conn.close();
	                return false;
	            }
	            
			conn.commit();
			conn.close();
			logger.debug("commit end");
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			logger.debug(ex.getMessage());
			tError.errorMessage = ex.getMessage();
			this.mErrors.addOneError(tError);
			try {
				conn.rollback();
				conn.close();
			} catch (Exception e) {
			}
			return false;
		}
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public int getResultNum() {
		return mResultNum;
	}

	public String getResultStr() {
		String resultStr = "";
		for (int i = 1; i <= mResultNum; i++) {

		}
		return resultStr;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 * 
	 * @param cInputData
	 *            VData
	 * @return boolean
	 */
	private boolean getInputData(VData cInputData) {
		// 检验查询条件
		mLDOldUserSchema = (LDUserSchema) cInputData.getObjectByObjectName(
				"LDUserSchema", 0);
		mLDNewUserSchema = (LDUserSchema) cInputData.getObjectByObjectName(
				"LDUserSchema", 1);
		if (mLDOldUserSchema == null || mLDNewUserSchema == null) {
			logger.debug("cant get password");
			return false;
		}
		logger.debug("completed get input data");
		return true;
	}

    /**
     * checkPwdComplex
     * @param usercode String
     * @param Pwd_new String
     * @return boolean
     */
    public boolean checkPwdComplex(String usercode, String Pwd_new)
    {   
    	int Lmin = 8;
    	int Lmax = 20;
        // 密码长度效验
        if (Pwd_new.length() < Lmin || Pwd_new.length() > Lmax)
        {   
            return false;
        }
        
        Pattern pattern = Pattern.compile("(?=.*[a-zA-Z])(?=.*?\\d)(?=.*?[#@*&$%^!()~`]).*$");
        Matcher matcher = pattern.matcher(Pwd_new);
        if(!matcher.find()){
        	return false;
        }
        
        if(Pwd_new.indexOf(usercode) != -1){
        	return false;
        }                
        return true;
    }
	/**
	 * 测试函数
	 * 
	 * @param args
	 *            String[]
	 */
	public static void main(String[] args) {
//		 LDChangePwdBL tLDChangePwdBL = new LDChangePwdBL();
//		 logger.debug(tLDChangePwdBL.checkPwdComplex("001", "123456a!"));
		// LDUserSchema tOldSchema = new LDUserSchema();
		// LDUserSchema tNewSchema = new LDUserSchema();
		// String oldPwd = "222";
		// String newPwd = "333";
		// for (int i = 0; i < 110; i++)
		// {
		// oldPwd = "333";
		// newPwd = "333";
		// tOldSchema.setUserCode("002");
		// tOldSchema.setPassword(oldPwd);
		// tNewSchema.setUserCode("002");
		// tNewSchema.setPassword(newPwd);
		// VData tVData = new VData();
		// tVData.add(tOldSchema);
		// tVData.add(tNewSchema);
		// boolean suc = tLDChangePwdBL.submitData(tVData, "changePwd");
		// if (suc)
		// {
		// logger.debug("change successful");
		// }
		// else
		// {
		// logger.debug("change fail");
		// }
		// }
	}
}
