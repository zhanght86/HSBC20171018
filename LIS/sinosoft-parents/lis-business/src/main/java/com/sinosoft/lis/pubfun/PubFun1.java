/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.pubfun;
import java.math.BigInteger;

import org.apache.log4j.Logger;


import com.sinosoft.lis.db.LMCalModeDB;
import com.sinosoft.lis.maxnomanage.CreateMaxNo;
import com.sinosoft.lis.maxnomanage.CreateMaxNoImp;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author YT
 * @version 1.0
 */
public class PubFun1 implements BusinessService {
private static Logger logger = Logger.getLogger(PubFun1.class);
	public PubFun1() {
	}
	private VData rResult = new VData(); 
	/**
	 * <p>
	 * 生成流水号的函数
	 * <p>
	 * <p>
	 * 号码规则：机构编码 日期年 校验位 类型 流水号
	 * <p>
	 * <p>
	 * 1-6 7-10 11 12-13 14-20
	 * <p>
	 * 
	 * @param cNoType
	 *            为需要生成号码的类型
	 * @param cNoLimit
	 *            为需要生成号码的限制条件
	 * @return 生成的符合条件的流水号，如果生成失败，返回空字符串""
	 */
	public static String CreateMaxNo(String cNoType, String cNoLimit) {
		try {
			//根据是否可以从最大号配置功能中获取到数据,判断是否采用新的调用方式
			CachedLDMaxNo mCached = CachedLDMaxNo.getInstance();
			VData tMaxNoVData = mCached.findLDMaxNoRuleConfig(cNoType);
			if(tMaxNoVData==null)
			{
				//没有查到结果,使用原有方式生成
				// 动态载入类
				logger.debug("sysmaxnotype:"
						+ com.sinosoft.utility.SysConst.MAXNOTYPE);
				String className = "com.sinosoft.lis.pubfun.SysMaxNo"
						+ com.sinosoft.utility.SysConst.MAXNOTYPE;
				Class cc = Class.forName(className);
				com.sinosoft.lis.pubfun.SysMaxNo tSysMaxNo = (com.sinosoft.lis.pubfun.SysMaxNo) (cc
						.newInstance());
				return tSysMaxNo.CreateMaxNo(cNoType, cNoLimit);
			}
			else
			{
				CreateMaxNo tC = new CreateMaxNoImp();
				//假设NoLimit是管理机构
				tC.setManageCom(cNoLimit);
				return tC.getMaxNo(cNoType);
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	/**
	 * <p>
	 * MOD-10保单号校验规则的函数
	 * <p>
	 * <p>
	 * 校验规则：Number: 0 0 0 0 0 0 1 2 3 4 7
	 * <p>
	 * <p>
	 * (x2 alternate digits): 0 0 0 2 6 14
	 * <p>
	 * <p>
	 * (Add when double digits) 0 0 2 6 5
	 * <p>
	 * <p>
	 * Add all digits 0 0 0 0 0 0 2 2 6 4 5
	 * <p>
	 * <p>
	 * Sum of all digits = 2+2+6+4+5 = 19
	 * <p>
	 * <p>
	 * Last digit = 9
	 * <p>
	 * <p>
	 * Check digit = 9-9 = 0
	 * <p>
	 * <p>
	 * Number is 000000123470
	 * <p>
	 * 
	 * @author quyang 2005.06.29
	 * @version 1.0
	 * @param cNoType
	 *            为需要生成号码的类型
	 * @param cNoLimit
	 *            为需要生成号码的限制条件
	 * @return 生成的符合条件的流水号，如果生成失败，返回空字符串""
	 */
	public static String creatVerifyDigit(String originalDigit) {
		try {
			String finalDigit = null;
			String[] alterDigOdd = null;
			String[] alterDigEven = null;
			logger.debug("originalDigit" + originalDigit
					+ "originalDigit.length()" + originalDigit.length());
			if (!"".equalsIgnoreCase(originalDigit)) {
				alterDigOdd = new String[originalDigit.length()];
				alterDigEven = new String[originalDigit.length()];
				for (int i = 0; i < originalDigit.length(); i++) {
					if (i == 0) {
						alterDigOdd[i] = originalDigit.substring(i, i + 1);
						logger.debug("alterDigOdd[" + i + "]"
								+ alterDigOdd[i].toString());
					}
					if (i == 1) {
						alterDigEven[i] = originalDigit.substring(i, i + 1);
						logger.debug("alterDigEven[" + i + "]"
								+ alterDigEven[i].toString());
					}
					if (i > 1 && i % 2 == 1) {
						alterDigEven[i] = originalDigit.substring(i, i + 1);
						logger.debug("alterDigEven[" + i + "]"
								+ alterDigEven[i].toString());
					}
					if (i > 1 && i % 2 == 0) {
						alterDigOdd[i] = originalDigit.substring(i, i + 1);
						logger.debug("alterDigOdd[" + i + "]"
								+ alterDigOdd[i].toString());
					}
				}
			}
			for (int i = alterDigOdd.length - 1; i >= 0; i--) {
				logger.debug("alterDigOdd数组的元素[" + i + "]"
						+ alterDigOdd[i]);
			}
			for (int i = alterDigEven.length - 1; i >= 0; i--) {
				logger.debug("alterDigEven数组的元素[" + i + "]"
						+ alterDigEven[i]);
			}
			logger.debug("alterDigOdd" + alterDigOdd.toString()
					+ "alterDigOdd.length()" + alterDigOdd.length);
			logger.debug("alterDigEven" + alterDigEven.toString()
					+ "alterDigEven.length()" + alterDigEven.length);
			// x2 alternate digits
			if (!"".equals(alterDigOdd)) {

				for (int i = 0; i < alterDigOdd.length; i++) {
					if ((String) alterDigOdd[i] != null
							&& (String) alterDigOdd[i] != "null"
							&& (String) alterDigOdd[i] != "0") {
						logger.debug("(String)alterDigOdd[i]"
								+ (String) alterDigOdd[i]);
						String tempString = null;
						logger.debug("alterDigOdd before double[" + i
								+ "]:" + alterDigOdd[i]);
						tempString = String.valueOf(2 * Integer
								.parseInt(alterDigOdd[i].toString()));
						logger.debug("alterDigOdd after double[" + i
								+ "]:" + alterDigOdd[i]);
						alterDigOdd[i] = "";
						alterDigOdd[i] = tempString;
						// Add when double digits
						if ((!"".equals(alterDigOdd[i]))
								&& alterDigOdd[i].toString().length() > 1) {
							int tempInt = 0;
							for (int j = 0; j < alterDigOdd[i].length(); j++) {
								String temp = (String) alterDigOdd[i];
								tempInt += Integer.parseInt(String.valueOf(temp
										.charAt(j)));
							}
							alterDigOdd[i] = "";
							alterDigOdd[i] = String.valueOf(tempInt);
						}
						logger.debug("alterDigOdd after Add double"
								+ alterDigOdd[i].toString());
					}
				}
				for (int i = alterDigOdd.length - 1; i >= 0; i--) {
					logger.debug("alterDigOdd数组的元素 after Add double ["
							+ i + "]" + alterDigOdd[i]);
				}
				// Add all digits
				// Sum of all digits
				int allDig = 0;
				for (int i = 0; i < alterDigOdd.length; i++) {
					if (i == 0) {
						allDig += Integer.parseInt(alterDigOdd[i]);
						logger.debug("alterDigOdd[" + i + "]"
								+ alterDigOdd[i].toString());
					}
					if (i == 1) {
						allDig += Integer.parseInt(alterDigEven[i]);
						logger.debug("alterDigEven[" + i + "]"
								+ alterDigEven[i].toString());
					}
					if (i > 1 && i % 2 == 1) {
						allDig += Integer.parseInt(alterDigEven[i]);
						logger.debug("alterDigEven[" + i + "]"
								+ alterDigEven[i].toString());
					}
					if (i > 1 && i % 2 == 0) {
						allDig += Integer.parseInt(alterDigOdd[i]);
						logger.debug("alterDigOdd[" + i + "]"
								+ alterDigOdd[i].toString());
					}

				}
				logger.debug("allDig" + String.valueOf(allDig));
				// Last digit
				int lastDig = 0;
				String allDigString = String.valueOf(allDig);
				if (allDig != 0) {
					lastDig = Integer.parseInt(allDigString.substring(
							allDigString.length() - 1, allDigString.length()));
				}
				logger.debug("lastDig" + String.valueOf(lastDig));
				// Check digit
				int checkDig = 0;
				checkDig = 9 - lastDig;
				/** @todo 校验位如果为4则转换为8 */
				if (checkDig == 4) {
					checkDig = 8;
				}
				finalDigit = originalDigit + String.valueOf(checkDig);
				logger.debug("finalDigit" + finalDigit);
			}
			return finalDigit;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	/**
	 * 生成流水号的函数 号码规则：机构编码 日期年 校验位 类型 流水号 1-6 7-10 11 12-13 14-20
	 * 
	 * @param cNoType
	 *            String 为需要生成号码的类型
	 * @param cNoLimit
	 *            String 为需要生成号码的限制条件
	 * @param tVData
	 *            VData
	 * @return String 生成的符合条件的流水号，如果生成失败，返回空字符串""
	 */
	public static String CreateMaxNo(String cNoType, String cNoLimit,
			VData tVData) {
		try {
			// 动态载入类
			//根据是否可以从最大号配置功能中获取到数据,判断是否采用新的调用方式
			CachedLDMaxNo mCached = CachedLDMaxNo.getInstance();
			VData tMaxNoVData = mCached.findLDMaxNoRuleConfig(cNoType);
			if(tMaxNoVData==null)
			{
				// 没有查到结果,使用原有方式生成
				logger.debug("sysmaxnotype:"
						+ com.sinosoft.utility.SysConst.MAXNOTYPE);
				String className = "com.sinosoft.lis.pubfun.SysMaxNo"
						+ com.sinosoft.utility.SysConst.MAXNOTYPE;
				Class cc = Class.forName(className);
				com.sinosoft.lis.pubfun.SysMaxNo tSysMaxNo = (com.sinosoft.lis.pubfun.SysMaxNo) (cc
						.newInstance());
				return tSysMaxNo.CreateMaxNo(cNoType, cNoLimit, tVData);
			}
			else
			{
				 CreateMaxNo tC = new CreateMaxNoImp();
				 tC.setManageCom(cNoLimit);
				   
				 return tC.getMaxNo(cNoType);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	/**
	 * 功能：产生指定长度的流水号，一个号码类型一个流水
	 * 
	 * @param cNoType
	 *            String 流水号的类型
	 * @param cNoLength
	 *            int 流水号的长度
	 * @return String 返回产生的流水号码
	 */
	public static String CreateMaxNo(String cNoType, int cNoLength) {
		try {
			// 动态载入类
			//根据是否可以从最大号配置功能中获取到数据,判断是否采用新的调用方式
			CachedLDMaxNo mCached = CachedLDMaxNo.getInstance();
			VData tMaxNoVData = mCached.findLDMaxNoRuleConfig(cNoType);
			if(tMaxNoVData==null)
			{
				String className = "com.sinosoft.lis.pubfun.SysMaxNo"
						+ com.sinosoft.utility.SysConst.MAXNOTYPE;
				Class cc = Class.forName(className);
				com.sinosoft.lis.pubfun.SysMaxNo tSysMaxNo = (com.sinosoft.lis.pubfun.SysMaxNo) (cc
						.newInstance());
				return tSysMaxNo.CreateMaxNo(cNoType, cNoLength);
			}
			else
			{
				CreateMaxNo tC = new CreateMaxNoImp(); 
				return tC.getMaxNo(cNoType);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	/**
	 * tongmeng 2011-05-17 add
	 * 规则引擎生成编码的程序
	 * 前三位为分类码,用于区分规则的所属业务功能.
		1,2位为算法识别位,统一由RU开头.
		第3,4位为功能编码

	 * @param tModulCode
	 * @return
	 */
	public static String CreateRuleCalCode(String tModulCode)
	{
		String tNo = "";
		tNo = tNo + "RU" + tModulCode.toUpperCase();
		String tID = CreateMaxNo(tNo,6);
		tNo = tNo + tID;
		return tNo;
	}
	
	/**
	 * tongmeng 2011-07-13 add
	 * 算法引擎生成编码的程序
	 * 前三位为分类码,用于区分规则的所属业务功能.
		1,2位为算法识别位,统一由CL开头.
		第3,4位为功能编码

	 * @param tModulCode
	 * @return
	 */
	public static String CreateRuleCalCode(String tModulCode,String tFlag)
	{
		String tNo = "";
		if(tFlag.equals("Y"))
		{
			tNo = tNo + "RU" + tModulCode.toUpperCase();
		}
		else
		{
			tNo = tNo + "CL" + tModulCode.toUpperCase();
		}
		String tID = CreateMaxNo(tNo,6);
		tNo = tNo + tID;
		return tNo;
	}
	

	/**
	 * 得到calculator解析过的SQL语句。原来的calculator，只能返回aCalCode所代表的SQL语句
	 * 的一个执行结果。这个方法得到的是aCalCode所代表的实际的SQL语句。
	 * 
	 * @param strSql
	 *            String 需要解析的SQL语句
	 * @param calculator
	 *            Calculator 已经设置了基本计算元素的calculator对象
	 * @return String 解析后的SQL语句。如果发生任何错误，将会抛出异常
	 * @throws Exception
	 */
	public static String getSQL(String strSql, Calculator calculator)
			throws Exception {
		LMCalModeDB tLMCalModeDB = new LMCalModeDB();

		// 对引号进行特殊的处理
		String strSQL = strSql.replace('\'', '@');
		// 查询临时表，不是很好的选择
		strSQL = "select ''" + strSQL + "'' from dual";
		tLMCalModeDB.setCalCode("XXX");
		tLMCalModeDB.setRiskCode("0");
		tLMCalModeDB.setType("F");
		tLMCalModeDB.setCalSQL(strSQL);

		if (!tLMCalModeDB.update()) {
			throw new Exception("设置计算公式时出错");
		}

		calculator.setCalCode("XXX");
		strSQL = calculator.calculate();

		if (strSQL.equals("")) {
			throw new Exception("解析SQL语句时出错");
		}

		return strSQL.replace('@', '\'');
	}

	/**
	 * 通过传入的日期可以得到所在月的计算第一天和计算最后一天的日期 author: LH
	 * <p>
	 * <b>Example: </b>
	 * <p>
	 * <p>
	 * calFLDate("2003-5-8") returns 2003-4-26 2003-5-25
	 * <p>
	 * 
	 * @param tDate
	 *            String 起始日期，(String,格式："YYYY-MM-DD")
	 * @return String[] 本月开始和结束日期，返回String[2]
	 */
	public static String[] calFLDate(String tDate) {
		String[] MonDate = new String[2];
		String asql = "select startdate,enddate from LAStatSegment where stattype='5' and startdate<='"
				+ "?tDate?" + "' and enddate>='" + "?tDate?" + "'";
		SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
        sqlbv2.sql(asql);
        sqlbv2.put("tDate", tDate);
		ExeSQL aExeSQL = new ExeSQL();
		SSRS aSSRS = new SSRS();
		aSSRS = aExeSQL.execSQL(sqlbv2);
		MonDate[0] = aSSRS.GetText(1, 1);
		MonDate[1] = aSSRS.GetText(1, 2);

		return MonDate;
	}

	/**
	 * 生成本机构下新成立的机构编码：BranchAttr
	 * 
	 * @param tUpAttr
	 *            String 新建机构的上级机构的BranchAttr编码
	 * @param tLength
	 *            int 编码长度(10位-区域督导部、12－督导部、15－营销服务部、18－营业组）
	 * @return String 新建机构的编码BranchAttr
	 */
	public static String CreateBranchAttr(String tUpAttr, int tLength) {
		// LABranchGroupDB tDB = new LABranchGroupDB();
		// LABranchGroupSchema tSch = new LABranchGroupSchema();
		ExeSQL tExeSql = new ExeSQL();

		String aNewAttr = "";

		String tSQL = "select max(branchattr) from labranchgroup where trim(branchattr) like concat('"
				+ "?tUpAttr?"
				+ "','%') and (trim(state) <> '1' or state is null)"
				+ " and branchtype = '1' and char_length(trim(branchattr)) = "
				+ tLength;
		SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
        sqlbv3.sql(tSQL);
        sqlbv3.put("tUpAttr", tUpAttr.trim());
		aNewAttr = tExeSql.getOneValue(sqlbv3);
		logger.debug("---maximum = " + aNewAttr);

		BigInteger tInt = null;
		BigInteger tAdd = null;

		try {
			tInt = new BigInteger(aNewAttr.trim());
			tAdd = new BigInteger("1");
		} catch (Exception ex) {
			ex.printStackTrace();

			return "";
		}

		tInt = tInt.add(tAdd);

		aNewAttr = tInt.toString();
		logger.debug("---aNewAttr = " + aNewAttr);

		return aNewAttr;
	}

	/**
	 * 取扫描时间(无扫描件返回 NOSCAN)
	 * 
	 * @param tPrtNo
	 * @return
	 */
	/*"Oracle：select nvl(sum(prem),0) from ljtempfee_lmriskapp
	 改造为：select (case when sum(prem) is not null then sum(prem)  else 0 end)  from ljtempfee_lmriskapp;"
    "concat只使用两个参数，改造样例如下：
     Oracle：select 'a'||'b'||'c'||'d' from dual
            改造为：select concat(concat(concat('a','b'),'c'),'d') from dual"
    */
	
	public static String getScanDate(String tPrtNo) {
		/*String tSQL = "select nvl(min(to_char concat(concat(makedate,'yyyy-mm-dd'),' '),maketime),'NOSCAN') from es_doc_main where doccode='"
				+ "?tPrtNo?" + "' ";*/
		String tSQL = "select (case when min(concat(concat(to_char(makedate,'yyyy-mm-dd'),' '),maketime)) is not null then min(concat(concat(to_char(makedate,'yyyy-mm-dd'),' '),maketime)) else 'NOSCAN' end)" +
				" from es_doc_main where doccode='"
				+ "?tPrtNo?" + "' ";
		SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
        sqlbv4.sql(tSQL);
        sqlbv4.put("tPrtNo", tPrtNo);
		ExeSQL tExeSQL = new ExeSQL();
		return tExeSQL.getOneValue(sqlbv4);
	}

	/**
	 * 取核保状态 1 - 未人工核保 2 - 问题件已回复 (录入\复核) 3 - 其他核保已回复 4 - 核保未回复
	 * 
	 * @param tContNo
	 * @return
	 */
	public static String getUwState(String tOpertor, String tContNo,
			String tMissionId, String tSubMissionId) {
		String tResult = "1";
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tState = new SSRS();
		
		//SYY BEGIN 人核状态修改为lccuwmaster的uwstate字段
		if (tOpertor.equals("10010005") || tOpertor.equals("10010041")) {
			String tSQL_a = "select 1,a.uwstate from lccuwmaster a where a.contno = '"+"?tContNo?"+"'";
			SQLwithBindVariables sqlbv5=new SQLwithBindVariables();
	        sqlbv5.sql(tSQL_a);
	        sqlbv5.put("tContNo", tContNo);
		//SYY END
		
		/*
		if (tOpertor.equals("0000001003") || tOpertor.equals("0000001149")) {
			// 自核调用,只需要查询最近一次人工核保的结果即可
			// 判断是否做过人工核保
			String tSQL_a = " select A.x,A.y from ( "
					+ " select '9999999999' x,missionprop18 y from lwmission where  activityid='0000001100' "
					+ " and missionid = '"
					+ tMissionId
					+ "' "
					+ " union "
					+ " select serialno x,missionprop18 y from lbmission where  activityid='0000001100' "
					+ " and missionid = '" + tMissionId + "' "
					+ " ) A order by A.x desc ";
		*/
			tState = tExeSQL.execSQL(sqlbv5);
			if (tState.getMaxRow() <= 0) {
				// 没有查到结果
				tResult = "1";
				return tResult;
			} else {
				// 查到结果 肯定是核保已回复
				if (tOpertor.equals("10010005")) {
					// 查询是否是其他核保已回复状态
									
					String tSQL3 = "select (case when sum(a) is not null then sum(a) else 0 end) from ("
							+ " select 1 a from lcissuepol where contno='"
							+ "?tContNo?"
							+ "' and state is not null "
							// 排除人工核保和问题件修改岗
							//tongmeng 2009-04-29 modify
//							//通过问题件编码判读返回状态
							//+ " and operatepos in ('6','3') "
							+ " and substr(issuetype,2,1)='3' "
							// 是否有加费
							/*+ " union "
							+ " select 1 a from lcprem where payplancode like '000000%' and contno = '"
							+ tContNo
							+ "' "*/
							+ " union "
							+ " select 1 a from LCUWMaster where contno = '"
							+ "?tContNo?" + "' and AddPremFlag is not null and AddPremFlag<>'0' "
							// 是否有承保计划变更
							+ " union "
							+ " select 1 a from LCUWMaster where contno = '"
							+ "?tContNo?" + "' and ChangePolFlag is not null and ChangePolFlag<>'0' "
							// 是否有特约
							+ " union "
							+ " select 1 a from lccspec where contno = '"
							+ "?tContNo?" + "' and prtflag is not null "
							+ " union "
							// 是否发放过体检\生调\
							+ " select 1 a from loprtmanager where otherno='"
							+ "?tContNo?" + "' and othernotype='02' "
							+ " and code in ('03','04') " + " ) ";
					SQLwithBindVariables sqlbv6=new SQLwithBindVariables();
			        sqlbv6.sql(tSQL_a);
			        sqlbv6.put("tContNo", tContNo);
					String tState3 = "";
					tState3 = tExeSQL.getOneValue(sqlbv6);
					if (Integer.parseInt(tState3) <= 0) {
						// 问题件已回复
						return "2";
					} else {
						// 其他核保已回复
						return "3";
					}
				} else {
					// 核保订正 取上一次的状态
					tResult = tState.GetText(1, 2);
					return tResult;
				}
			}
		} else {
			// 查询是否是核保未回复状态
			/*"Oracle：select nvl(sum(prem),0) from ljtempfee_lmriskapp
			 改造为：select (case when sum(prem) is not null then sum(prem)  else 0 end)  from ljtempfee_lmriskapp;"
			 */	
			String tSQl1 = "select (case when sum(a) is not null then sum(a) else 0 end) from ( "
					// 查询是否有未回复的问题件
					+ " select 1 a from lwmission where missionid = '"
					+ "?tMissionId?"
					+ "' and activityid in "
					+ " ( '0000001002','0000001017','0000001019','0000001020','0000001106','0000001107','0000001111', "
					+ " '0000001121','0000001112','0000001108','0000001113','0000001120','0000001130','0000001302','0000001303') "
					+ " and not (activityid = '" + "?tOpertor?"
					+ "' and submissionid = '" + "?tSubMissionId?" + "')" + " ) g";
			// 问题件修改 业务员通知书打印 业务员通知书回收 机构问题件回复 打印体检通知书,打印核保通知书,回收体检通知书
			// 体检结果录入 回收核保通知书 打印生调通知书 回收生调通知书 生调结果录入 生调处理 核保通知书(非打印类)打印,非打印类回收

			// 判断是否是核保未回复
			String tState1 = "";
			SQLwithBindVariables sqlbv7=new SQLwithBindVariables();
	        sqlbv7.sql(tSQl1);
	        sqlbv7.put("tMissionId", tMissionId);
	        sqlbv7.put("tOpertor", tOpertor);
	        sqlbv7.put("tSubMissionId", tSubMissionId);
			tState1 = tExeSQL.getOneValue(sqlbv7);
			if (Integer.parseInt(tState1) > 0) {
				return "4";
			} else {
				// 判断是否是未人工核保
				String tState2 = "";
				String tSQL2 = "select (case when sum(a) is not null then sum(a) else 0 end) from ( "
						// 是否发放过体检\生调\核保通知书(打印类)\核保通知书(非打印类)\业务员通知书
						+ " select 1 a from loprtmanager where otherno='"
						+ "?tContNo?"
						+ "' and othernotype='02' "
						+ " and code in ('TB89','TB90','14','03','04') "
						+ " union "
						// 是否发放过问题件
						+ " select 1 a from lcissuepol where contno='"
						+ "?tContNo?"
						+ "' and state is not null "
						// 是否有加费
						/*+ " union "
						+ " select 1 a from lcprem where payplancode like '000000%' and contno = '"
						+ tContNo
						+ "' "*/
						+ " union "
						+ " select 1 a from LCUWMaster where contno = '"
						+ "?tContNo?" + "' and AddPremFlag is not null and AddPremFlag<>'0' "
						// 是否有承保计划变更
						+ " union "
						+ " select 1 a from LCUWMaster where contno = '"
						+ "?tContNo?" + "' and ChangePolFlag is not null and ChangePolFlag<>'0' "
						// 是否有特约
						+ " union "
						+ " select 1 a from lccspec where contno = '" + "?tContNo?"
						+ "' and prtflag is not null " + " ) g ";
				SQLwithBindVariables sqlbv8=new SQLwithBindVariables();
		        sqlbv8.sql(tSQL2);
		        sqlbv8.put("tContNo", tContNo);
				tState2 = tExeSQL.getOneValue(sqlbv8);
				if (Integer.parseInt(tState2) <= 0) {
					// 未人工核保
					return "1";

				}
				// 查询是否是其他核保已回复状态
				String tSQL3 = "select (case when sum(a) is not null then sum(a) else 0 end) from ("
						+ " select 1 a from lcissuepol where contno='"
						+ "?contno?"
						+ "' and state is not null "
						// 排除人工核保和问题件修改岗
						//+ " and operatepos in ('6','3') "
						+ " and substr(issuetype,2,1)='3' "
						// 是否有加费
						/*+ " union "
						+ " select 1 a from lcprem where payplancode like '000000%' and contno = '"
						+ tContNo
						+ "' "*/
						+ " union "
						+ " select 1 a from LCUWMaster where contno = '"
						+ "?contno?" + "' and AddPremFlag is not null and AddPremFlag<>'0' "
						// 是否有承保计划变更
						+ " union "
						+ " select 1 a from LCUWMaster where contno = '"
						+ "?contno?" + "' and  ChangePolFlag is not null and ChangePolFlag<>'0' "
						// 是否有特约
						+ " union "
						+ " select 1 a from lccspec where contno = '" + "?contno?"
						+ "' and prtflag is not null "
						+ " union "
						// 是否发放过体检\生调\
						+ " select 1 a from loprtmanager where otherno='"
						+ "?contno?" + "' and othernotype='02' "
						+ " and code in ('03','04') " + " ) g";
				String tState3 = "";
				SQLwithBindVariables sqlbv9=new SQLwithBindVariables();
		        sqlbv9.sql(tSQL3);
		        sqlbv9.put("contno", tContNo);
				tState3 = tExeSQL.getOneValue(sqlbv9);
				if (Integer.parseInt(tState3) <= 0) {
					// 问题件已回复
					return "2";
				} else {
					// 其他核保已回复
					return "3";
				}
			}

		}
		// return tResult;
	}
	//理赔分红判断
	/*
    public static String[] CheckFeildClaim(TransferData mTransferData) {
        String[] tReturn = new String[3];
        //第一个字段表示校验是否通过 0：通过  1：不通过
        //第二个字段表示校验是否影响程序继续运行 0：不影响； 1:影响
        //第一个字段表示校验结论
        StringBuffer sbSQL = new StringBuffer();

        sbSQL.append( "select distinct calcode, msg, serialno from lmcheckfield ")
                .append(" where fieldname = '")
                .append("LP")
                .append("INSERT")
                .append("' order by length(trim(serialno)), serialno ");
        logger.debug("-----" + sbSQL.toString());


        ExeSQL tExeSQL = new ExeSQL();
        SSRS tSSRS = tExeSQL.execSQL(sbSQL.toString());
        if (tExeSQL.mErrors.needDealError()) {
            tReturn[0] = "1";
            tReturn[1] = "1";
            tReturn[2] = "调用失败";
            return tReturn;
        }

        boolean bResult; //计算结果
        String sCalCode = ""; //计算代码
        String sErroMsg = ""; //规则信息

        BqCalBase mBqCalBase = new BqCalBase();
        mBqCalBase.setGrpContNo((String) mTransferData.getValueByName("GrpContNo"));
        mBqCalBase.setInsuredNo((String) mTransferData.getValueByName("InsuredNo"));
        mBqCalBase.setEdorValiDate((String) mTransferData.getValueByName("AccDate"));

        BqCalBL tBqCalBL;
        if (tSSRS != null && tSSRS.getMaxRow() > 0) {
            for (int j = 1; j <= tSSRS.getMaxRow(); j++) {
                sCalCode = tSSRS.GetText(j, 1);
                sErroMsg = tSSRS.GetText(j, 2);

                tBqCalBL = new BqCalBL(mBqCalBase, sCalCode, "");
                bResult = tBqCalBL.calEdorItemDetailValidate();
                logger.debug("------" + bResult);
                if (tBqCalBL.mErrors.needDealError()) {
                    tReturn[0] = "1";
                    tReturn[1] = "1";
                    tReturn[2] = "调用失败";
                    return tReturn;

                }

                if (bResult) {
                    tReturn[0] = "1";
                    tReturn[1] = "1";
                    tReturn[2] = sErroMsg;
                    return tReturn;

                }
            }
        }

        return tReturn;
    }
*/
	/**
	 * 调试函数
	 * 
	 * @param args
	 *            String[]
	 */
	public static void main(String[] args) {
		 PubFun1 tPF1 = new PubFun1();
		 logger.debug(PubFun1.CreateRuleCalCode("A"));
	}

	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return null;
	}

	public VData getResult() {
		// TODO Auto-generated method stub
		return rResult;
	}

	public boolean submitData(VData cInputData, String cOperate) {
		String TranSN = "";
		boolean tFlag = true ;
		TransferData mTransferData = (TransferData) cInputData.getObjectByObjectName("TransferData", 0);
		//准备传递参数
		String tNoType=(String)mTransferData.getValueByName("NoType");
		int tNoLength=(Integer)mTransferData.getValueByName("NoLength");
		
		//通过cOperate 获取要调用的方法
		if("CreateMaxNo".equalsIgnoreCase(cOperate)){
			if(!"".equals(tNoType) && !"".equals(tNoLength)){
				TranSN = PubFun1.CreateMaxNo(tNoType, tNoLength);
			}
			if(TranSN==null || "".equals(TranSN)){
				tFlag = false;
			}
		} 
		rResult.clear();
		TransferData tTransferData=new TransferData();
        tTransferData.setNameAndValue("SN", TranSN);
//        VData tVData = new VData();
//        tVData.add(tTransferData);
        rResult.add(tTransferData);
        
		return tFlag;
	}
}
