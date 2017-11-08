/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.menumang;
import java.util.Date;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LDCodeDB;
import com.sinosoft.lis.db.LDUserDB;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LDUserSchema;
import com.sinosoft.lis.vschema.LDCodeSet;
import com.sinosoft.lis.vschema.LDUserSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SysConst;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 用户业务逻辑处理类
 * </p>
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
public class LDUserBL {
private static Logger logger = Logger.getLogger(LDUserBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mResult = new VData();

	/** 数据操作字符串 */
	// private String mOperate;
	/** 业务处理相关变量 */
	/** 菜单组、菜单组到菜单的相关信息 */
	LDUserSchema mLDUserSchema = new LDUserSchema();

	String mResultStr = "";
	int mResultNum = 1;

	public LDUserBL() {
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
		if (!cOperate.equals("query")) {
			return false;
		}

		// 将操作数据拷贝到本类中
		// this.mOperate = cOperate;
		// 得到外部传入的数据,将数据备份到本类中
		// logger.debug("start get inputdata...");
		if (!getInputData(cInputData)) {
			return false;
		}
		// logger.debug("---getInputData---");

		// 进行业务处理
		if (!queryUser()) {
			return false;
		}
		// logger.debug("---queryUser---");
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public int getResultNum() {
		return mResultNum;
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
		mLDUserSchema = (LDUserSchema) cInputData.getObjectByObjectName(
				"LDUserSchema", 0);

		if (mLDUserSchema == null) {
			return false;
		}

		return true;
	}

	/**
	 * 查询符合条件的信息 输出：如果准备数据时发生错误则返回false,否则返回true
	 * 
	 * @return boolean
	 */
	private boolean queryUser() {
		String encryptPwd = mLDUserSchema.getPassword();
		String tUserCode = mLDUserSchema.getUserCode();
		LDUserDB tLDUserDB = new LDUserDB();
		tLDUserDB.setUserCode(tUserCode);
		if(!tLDUserDB.getInfo()){
			 CError.buildErr(this, "用户"+mLDUserSchema.getUserCode()+"不存在！");
			 return false;
		}

		// 判断用户的有效时间，包括开始时间和结束时间
		LDUserSchema tLDUserSchema =tLDUserDB.getSchema();

		String strCurrentDate = PubFun.getCurrentDate();
		String strValidStartDate = tLDUserSchema.getValidStartDate();
		String strValidEndDate = tLDUserSchema.getValidEndDate();
        String userstate= tLDUserSchema.getUserState();
        String tPassWord = tLDUserSchema.getPassword();
        if( "1".equals(userstate)) {
           CError.buildErr(this, "用户"+mLDUserSchema.getUserCode()+"已经失效，登录失败！");
           return false;            
        }
        if( "4".equals(userstate)) {
            CError.buildErr(this, "用户"+mLDUserSchema.getUserCode()+"账号已经锁定，请联系管理员！");
            return false;            
        }
        if(!encryptPwd.equals(tPassWord)){
        	String tSql = "Select sysvarvalue from ldsysvar where sysvar = 'PwdCheck'";
        	SQLwithBindVariables sqlbv=new SQLwithBindVariables();
        	sqlbv.sql(tSql);
        	ExeSQL tExeSQL = new ExeSQL();
        	String tRet = tExeSQL.getOneValue(sqlbv);
        	if("1".equals(tRet)){
        		int eTimes = 0;
        		String tPart = "";
        		String tErrInfo = "";
        		String tErrTimes = tLDUserSchema.getEdorValiFlag();
        		SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
        		if(tErrTimes == null ||tErrTimes.equals("")){
        			eTimes = 1;
        		}else{
        			eTimes = Integer.parseInt(tErrTimes)+1;
        		}
        		
        		if(eTimes>=3){
        			tPart = " EdorValiFlag = '3',UserState = '4'";
        			tErrInfo = "密码连续录入错误达3次，账号已经锁定，请联系系统管理员！";
        		}else{
        			tPart = " EdorValiFlag = '?eTimes?'";
        			sqlbv1.put("eTimes", eTimes);
        			tErrInfo = "密码录入错误，还有"+(3-eTimes)+"次机会！";
        		}
        		tSql = "Update LDUser Set "+tPart+" where UserCode = '?UserCode?' ";
        	
        		sqlbv1.sql(tSql);
        		sqlbv1.put("UserCode", mLDUserSchema.getUserCode());
        		tExeSQL.execUpdateSQL(sqlbv1);
        		
        		if(!"ORACLE".equalsIgnoreCase(SysConst.DBTYPE)){
        			SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
        			String tSql2 = "insert into lduser_trace (operatetype, operatetime, usercode, ip, id) values ('?operateType?','?opertateTime?','?userCode?','?ip?', '?id?') ";
        			sqlbv2.sql(tSql2);
        			sqlbv2.put("operateType", "UPDATE");
        			sqlbv2.put("opertateTime", PubFun.getCurrentDate()+" "+PubFun.getCurrentTime());
        			sqlbv2.put("userCode", mLDUserSchema.getUserCode());
        			String ipAndId = tExeSQL.getOneValue("select USER() from dual");
        			String[] ipId = ipAndId.split("@");
        			sqlbv2.put("ip", ipId[1]);
        			sqlbv2.put("id", ipId[0]);
        			tExeSQL.execUpdateSQL(sqlbv2);
        		}
        		
        		CError.buildErr(this, tErrInfo);
        		return false;  
        		
        	}else{
        		CError.buildErr(this, "密码录入错误！");
        		return false;  
        	}
        }
        
		// 如果生效开始时间为空，则不做校验
        if (strValidStartDate != null && !strValidStartDate.equals("")) {
        	if (strValidStartDate.compareTo(strCurrentDate) > 0) {
        		CError.buildErr(this, "当前帐户还没有生效（系统时间小于生效时间）");
        		return false;
        	}
        }
		// 如果生效结束时间为空，则不做校验
        if (strValidEndDate != null && !strValidEndDate.equals("")) {
        	if (strValidEndDate.compareTo(strCurrentDate) < 0) {
        		CError.buildErr(this, "当前帐户已经失效（系统时间大于失效时间）");
        		return false;
        	}
        }

		String ComCode = mLDUserSchema.getComCode();
		StringBuffer tSBql = new StringBuffer(256);
		tSBql.append("select * from ldcode where codetype = 'station' and code = '");
		tSBql.append("?ComCode?");
		tSBql.append("'");
		SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
		sqlbv2.sql(tSBql.toString());
		sqlbv2.put("ComCode", ComCode);
		LDCodeSet tCodeSet = new LDCodeSet();
		LDCodeDB tCodeDB = new LDCodeDB();
		tCodeSet = tCodeDB.executeQuery(sqlbv2);
		if (tCodeSet == null || tCodeSet.size() == 0) {
    		CError.buildErr(this, "登陆机构:"+ComCode+"不存在！");
			return false;
		}

		int matchCount = 0;
		String getComCode = tLDUserSchema.getComCode();
		if (getComCode.length() <= ComCode.length()) {
			int j = 0;
			for (; j < getComCode.length(); j++) {
				if (ComCode.charAt(j) != getComCode.charAt(j)) {
					break;
				}
			}
			if (j == getComCode.length()) {
				matchCount++;
			}
		}

		if (matchCount == 0) {
    		CError.buildErr(this, "对不起！您没有权限登陆该机构!");
			return false;
		}
		return true;
	}
}
