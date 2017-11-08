package com.sinosoft.lis.ebusiness;

import org.apache.log4j.Logger;

import com.sinosoft.lis.bl.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vbl.*;
import com.sinosoft.lis.vdb.*;
import com.sinosoft.lis.vschema.*;

import com.sinosoft.utility.*;

import java.util.*;

/**
 * <p>
 * Title: Web电子商务系统
 * </p>
 * <p>
 * Description: 内网综合查询调用接口
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author JL
 * @version 1.0
 */
public class AllQueryBL {
	private static Logger logger = Logger.getLogger(AllQueryBL.class);

	/** 传入数据的容器 */
	private VData mInputData = new VData();

	/** 传送数据的容器 */
	private VData mOutputData = new VData();

	/** 返回数据的容器 */
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperate;

	/** 错误处理类 */
	public CErrors mErrors = new CErrors();

	public AllQueryBL() {
	}

	public Vector submitData(Vector inParam) {
		try {
			String strAct = (String) inParam.get(0);
			logger.debug("==> Begin to AllQueryBL" + strAct);
			if (strAct.equals("LOGIN||MATCHING")) {
				// 注册时通过用户输入信息，返回客户信息
				if (!execLogin(inParam)) {
					// @@ 错误处理
					mResult.add("个人用户注册失败");
				}

				return mResult;
			} else if (strAct.equals("EXE||SQL")) {
				// 执行SQL语句
				if (!exeSql(inParam)) {
					// @@ 错误处理
					mResult.add("查询：查询失败");
				}

				return mResult;
			} else if (strAct.equals("PQUERY||LCPOL")) {
				// 保单信息查询
				if (!execQueryLcpol(inParam)) {
					// @@ 错误处理
					mResult.add("保单详细信息查询失败");
				}

				return mResult;
			} else if (strAct.equals("QUERY||LJSPAY")) {
				// 续期交费查询
				if (!execQueryLJSPay(inParam)) {
					// @@ 错误处理
					mResult.add("查询：续期交费查询失败");
				}

				return mResult;
			} else if (strAct.equals("PQUERY||CUSTOMER")) {
				// 客户信息查询
				if (!execQueryCustomer(inParam)) {
					// @@ 错误处理
					mResult.add("客户查询失败");
				}

				return mResult;
			}
			// 放在代理人的处理类TBInfoBL进行处理
			// else if (strAct.equals("QUERY||AGENT"))
			// {
			// //服务人员查询
			// if (!execQueryAgent(inParam))
			// {
			// // @@ 错误处理
			// mResult.add("查询：服务人员查询失败");
			// }
			//
			// return mResult;
			// }
			else if (strAct.equals("QUERY||GRPPOL")) {
				// 团单查询
				if (!execQueryGrpPol(inParam)) {
					// @@ 错误处理
					mResult.add("团单查询失败");
				}

				return mResult;
			} else if (strAct.equals("QUERY||GRPLCPOL")) {
				// 团单下个人保单查询
				if (!execQueryGrpLCPol(inParam)) {
					// @@ 错误处理
					mResult.add("团单下个人保单查询失败");
				}

				return mResult;
			} else if (strAct.equals("QUERY||LCINSUREACC")) {
				// 团单下个人帐户信息查询
				if (!execQueryLCInsureAcc(inParam)) {
					// @@ 错误处理
					mResult.add("团单下个人帐户信息查询失败");
				}

				return mResult;
			} else if (strAct.equals("PQUERY||LCCONT")) {
				// 基本信息查询
				if (!execQueryLCContAcc(inParam)) {
					// @@ 错误处理
					mResult.add("保单基本信息查询失败");
				}

				return mResult;
			} else if (strAct.equals("XQUERY||PAY")) {
				// 续期缴费查询
				if (!execQueryPAYAcc(inParam)) {
					// @@ 错误处理
					mResult.add("续期缴费查询失败");
				}

				return mResult;
			} else if (strAct.equals("PQUERY||BONUS")) {
				// 分红信息查询
				if (!execQueryBONUSAcc(inParam)) {
					// @@ 错误处理
					mResult.add("分红信息查询失败");
				}

				return mResult;
			} else if (strAct.equals("PQUERY||WNACCOUNT")) {
				// 万能账户-帐户查询
				if (!execQueryWNACCOUNTAcc(inParam)) {
					// @@ 错误处理
					mResult.add("万能账户-帐户查询失败");
				}

				return mResult;
			} else if (strAct.equals("PQUERY||WNACCOUNTLOG")) {
				// 万能账户-帐户查询
				if (!execQueryWNACCOUNTAccLog(inParam)) {
					// @@ 错误处理
					mResult.add("万能账户-帐户历史查询失败");
				}

				return mResult;
			} else if (strAct.equals("PQUERY||EDOR")) {
				// 保全历史查询
				if (!execQueryEDORAcc(inParam)) {
					// @@ 错误处理
					mResult.add("保全历史查询失败");
				}

				return mResult;
			} else if (strAct.equals("PQUERY||CLAIM")) {
				// 理赔信息查询
				if (!execQueryCLAIMAcc(inParam)) {
					// @@ 错误处理
					mResult.add("理赔信息查询失败");
				}

				return mResult;
			} else if (strAct.equals("PQUERY||GETDRAW")) {
				// 生存领取查询
				if (!execQueryGETDRAWAcc(inParam)) {
					// @@ 错误处理
					mResult.add("生存领取查询失败");
				}

				return mResult;
			} else if (strAct.equals("PQUERY||AGENT")) {
				// 服务人员查询
				if (!execQueryAGENTAcc(inParam)) {
					// @@ 错误处理
					mResult.add("服务人员查询失败");
				}

				return mResult;
			} else if (strAct.equals("AQUERY||PROPOSAL")) {
				// 投保单查询
				if (!execaQueryPROPOSALAcc(inParam)) {
					// @@ 错误处理
					mResult.add("中介投保单查询失败");
				}

				return mResult;
			} else if (strAct.equals("AQUERY||TPROPOSAL")) {
				// 当天投保单查询
				if (!execaQueryTPROPOSAL(inParam)) {
					// @@ 错误处理
					mResult.add("中介投保单查询失败");
				}

				return mResult;
			} else if (strAct.equals("AQUERY||LCPOL")) {
				// 保单查询
				if (!execaQueryLCPOLAcc(inParam)) {
					// @@ 错误处理
					mResult.add("中介保单查询失败");
				}

				return mResult;
			}
			// 理财产品
			else if (strAct.equals("ACCRATE")) {
				// 保单查询
				if (!execACCRATEAcc(inParam)) {
					// @@ 错误处理
					mResult.add("理财产品价格查询失败");
				}

				return mResult;
			} else if (strAct.equals("AQUERY||AIRGRPPOL")) {
				// 保单查询
				if (!execaQueryAIRGRPPOL(inParam)) {
					// @@ 错误处理
					mResult.add("团险短期险保单查询查询失败");
				}

				return mResult;
			} else if (strAct.equals("AQUERY||AIRPOL")) {
				// 保单查询
				if (!execaQueryAIRPOL(inParam)) {
					// @@ 错误处理
					mResult.add("个险短期险保单查询");
				}

				return mResult;
			}
		} catch (Exception ex) {
			mResult.add(ex);
		}
		return mResult;
	}

	/**
	 * 报户注册
	 * 
	 * @param inParam
	 * @return
	 */
	private boolean execLogin(Vector inParam) {
		mResult.clear();
		mResult.add("SUCCESS");

		// 注册时通过用户输入信息，返回客户信息
		String strName = (String) inParam.get(1);
		String strSec = (String) inParam.get(2);
		String strBirthday = (String) inParam.get(3);
		String strIDType = (String) inParam.get(4);
		String strIDNo = (String) inParam.get(5);
		String strContNo = (String) inParam.get(6);

		// logger.debug("strName:"+strName);
		// logger.debug("strSec:"+strSec);
		// logger.debug("strBirthday:"+strBirthday);
		// logger.debug("strIDType:"+strIDType);
		// logger.debug("strIDNo:"+strIDNo);
		// logger.debug("strContNo:"+strContNo);

		// 查找客户信息
		LCAppntDB tLCAppntDB = new LCAppntDB();
		// tLCAppntDB.setContNo(strContNo);
		// tLCAppntDB.setAppntName(strName);
		// tLCAppntDB.setAppntSex(strSec);
		// tLCAppntDB.setAppntBirthday(strBirthday);
		// tLCAppntDB.setIDType(strIDType);
		// tLCAppntDB.setIDNo(strIDNo);
		// zy 2010-01-26 家庭单的处理
		if (isPortfolio(strContNo)) {
			String cSql = "";
			if (SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)) {
				cSql = "select contno from lccont where familyid='"
						+"?familyid?"+"' and rownum=1 ";
			} else if (SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)) {
				cSql = "select contno from lccont where familyid='"
						+"?familyid?"+"' limit 1 ";
			}
			SQLwithBindVariables sqlvb1 = new SQLwithBindVariables();
			sqlvb1.sql(cSql);
			sqlvb1.put("familyid", strContNo);
			ExeSQL tExeSQL = new ExeSQL();
			String fContno = tExeSQL.getOneValue(sqlvb1);
			if (fContno == null || "".equals(fContno)) {
				mResult.clear();
				mResult.add("FAIL");
				mResult.add("该卡单的保单信息不存在，请核实您输入的信息是否正确"); // 注册：查询客户信息失败
			} else {
				strContNo = fContno;
			}
		}
		// 由于身份证最后一位可能为大写或小写的X，所以需要进行转换，不区分大小写。
		String appSql = "select * from lcappnt where contno='"+"?contno?"
				+ "' and appntname='"+"?appntname?"+"' and appntsex='"
				+"?appntsex?"+"' "+"and appntbirthday='"
				+"?appntbirthday?"+"' and idtype='"+"?idtype?"
				+ "' and lower(idno)='"+"?idno?"+"'";
		SQLwithBindVariables sqlvb2 = new SQLwithBindVariables();
		sqlvb2.sql(appSql);
		sqlvb2.put("contno", strContNo);
		sqlvb2.put("appntname", strName);
		sqlvb2.put("appntsex", strSec);
		sqlvb2.put("appntbirthday", strBirthday);
		sqlvb2.put("idtype", strIDType);
		sqlvb2.put("idno", strIDNo.toLowerCase());
		LCAppntSet tLCAppntSet = new LCAppntSet();
		// tLCAppntSet = tLCAppntDB.query();
		tLCAppntSet = tLCAppntDB.executeQuery(sqlvb2);
		logger.debug("查询客户注册的客户信息-----" + appSql + "tLCAppntSet.size()"
				+ tLCAppntSet.size());

		if (tLCAppntSet.size() < 1) {
			mResult.clear();
			mResult.add("FAIL");
			mResult.add("该保单的客户信息不存在，请核实您输入的信息是否正确"); // 注册：查询客户信息失败

			return false;
		}

		if (tLCAppntSet.size() > 1) {
			mResult.clear();
			mResult.add("FAIL");
			mResult.add("该保单的客户信息重复，请与客服联系进行合并"); // 注册：客户信息重复，请与客服联系进行合并

			return false;
		}

		// logger.debug("strPolNo:"+strContNo);
		// logger.debug("tLCAppntSet.get(1).getCustomerNo():"+tLCAppntSet.get(1).getAppntNo());

		// 检查保单与客户信息是否匹配（投保人）
		// 客户号、通讯地址、邮政编码、电子邮件、手机、单位电话、家庭电话、传真、婚姻状况、职业代码 ---请注意返回的信息不是合同记录 zy
		String cSql = "select a.customerno,a.postaladdress,a.zipcode,a.email,a.mobile,a.companyphone,a.homephone,a.fax,b.marriage,b.occupationcode  "
				+ "from lcaddress a ,lcappnt b where a.customerno=b.appntno and a.addressno=b.addressno  and b.contno='"
				+"?contno?"+"'";

		// logger.debug("查询客户信息"+cSql);
		SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		sqlbv3.sql(cSql);
		sqlbv3.put("contno", strContNo);
		ExeSQL tExeSQL = new ExeSQL();
		String strReturn = tExeSQL.getEncodedResult(sqlbv3, 1);
		String strR = strReturn.substring(0, 3);
		if (strR.equals("100")) {
			mResult.clear();
			mResult.add("FAIL");
		}

		// 返回客户信息记录串
		mResult.add(strReturn);

		return true;
	}

	/**
	 * 执行查询
	 * 
	 * @param inParam
	 * @return
	 */
	private boolean exeSql(Vector inParam) {
		mResult.clear();
		mResult.add(CError.TYPE_NONE);

		String strSQL = null;

		for (int i = 1; i < inParam.size(); i++) {
			strSQL = (String) inParam.get(i);
			// logger.debug("Server ExecSql: " + strSQL);

			ExeSQL tExeSQL = new ExeSQL();
			String strReturn = tExeSQL.getEncodedResult(strSQL, 1);

			mResult.add(strReturn);
		}

		return true;
	}

	/**
	 * 执行保单查询
	 * 
	 * @param inParam
	 * @return
	 */
	private boolean execQueryLcpol(Vector inParam) {
		mResult.clear();
		mResult.add("SUCCESS");

		String strContNo = (String) inParam.get(1);

		if ((strContNo == null) || strContNo.equals("")) {
			mResult.clear();
			mResult.add("FAIL");
			mResult.add("保单号为空，请核实"); // 查询：客户号错误

			return false;
		}

		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(strContNo);

		if (!tLCContDB.getInfo()) {
			mResult.clear();
			mResult.add("FAIL");
			mResult.add("保单不存在，请确认您输入的信息是否正确"); // 查询：客户号信息查询失败

			return false;
		}

		// String strSQL =
		// "select (select riskname from lmriskapp where riskcode = a.riskcode),a.riskcode, "
		// +" decode((select enddate from lmriskapp where riskcode = a.riskcode),'',	'有效','失效'),a.insuredname, "
		// +" a.insuyear||a.insuyearflag, a.cvalidate,a.enddate,a.prem, a.amnt, a.payyears from lcpol a where a.contno='"
		// + strContNo + "' and a.appflag='1' ";
		// zy 2009-07-07 调整险种状态
		// zy 2009-11-04 调整查询被保人逻辑，如果为121301附加豁免保费险种，则被保人显示为该险种的投保人信息
		// String strSQL =
		// "select (select riskname from lmriskapp where riskcode = a.riskcode),a.riskcode, "
		// +" decode((select state from lccontstate where polno = a.polno and statetype = 'Available' and startdate <= sysdate "
		// +" and enddate is null and rownum = 1),'1','失效','有效'),decode(a.riskcode,'121301',a.appntname,a.insuredname), "
		// +" a.insuyear||a.insuyearflag, a.cvalidate,a.enddate,a.prem, a.amnt, a.payyears from lcpol a where a.contno='"
		// + strContNo + "' and a.appflag='1' ";
		String strSQL = "";
		if (SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)) {
			strSQL = "select (select riskname from lmriskapp where riskcode = a.riskcode),a.riskcode, "
					+ "(case (select state from lccontstate where polno = a.polno and statetype = 'Available' and startdate <= now() "
					+ " and enddate is null limit 1) when '1' then '失效' else '有效' end),(case a.riskcode when '121301' then a.appntname else a.insuredname end), "
					+ " concat(a.insuyear,a.insuyearflag), a.cvalidate,a.enddate,a.prem, a.amnt, a.payyears from lcpol a where a.contno='"
					+ strContNo +"' and a.appflag='1' ";
		} else if (SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)) {
			strSQL = "select (select riskname from lmriskapp where riskcode = a.riskcode),a.riskcode, "
					+ "(case (select state from lccontstate where polno = a.polno and statetype = 'Available' and startdate <= now() "
					+ " and enddate is null and rownum = 1) when '1' then '失效' else '有效' end),(case a.riskcode when '121301' then a.appntname else a.insuredname end), "
					+ " concat(a.insuyear,a.insuyearflag), a.cvalidate,a.enddate,a.prem, a.amnt, a.payyears from lcpol a where a.contno='"
					+ strContNo +"' and a.appflag='1' ";
		}
		
		String strReturn = execSql(strSQL);
		String strR = strReturn.substring(0, 3);
		if (strR.equals("100")) {
			mResult.clear();
			mResult.add("FAIL");
		}
		mResult.add(strReturn);

		return true;
	}

	/**
	 * 续期交费查询
	 * 
	 * @param inParam
	 * @return
	 */
	private boolean execQueryLJSPay(Vector inParam) {
		mResult.clear();
		mResult.add("SUCCESS");

		String strAppntNo = (String) inParam.get(1);

		if ((strAppntNo == null) || strAppntNo.equals("")) {
			mResult.clear();
			mResult.add("FAIL");
			mResult.add("查询：客户号错误"); // 查询：客户号错误

			return false;
		}

		LDPersonDB tLDPersonDB = new LDPersonDB();
		tLDPersonDB.setCustomerNo(strAppntNo);

		if (!tLDPersonDB.getInfo()) {
			mResult.clear();
			mResult.add("FAIL");
			mResult.add("查询：客户号信息查询失败"); // 查询：客户号信息查询失败

			return false;
		}

		String strSQL = (String) inParam.get(2);
		String strReturn = execSql(strSQL);
		String strR = strReturn.substring(0, 3);
		if (strR.equals("100")) {
			mResult.clear();
			mResult.add("FAIL");
		}
		mResult.add(strReturn);

		return true;
	}

	/**
	 * 客户查询
	 * 
	 * @param inParam
	 * @return
	 */
	private boolean execQueryCustomer(Vector inParam) {

		String strContNo = (String) inParam.get(1);
		// 校验投保人客户号输入
		if ((strContNo == null) || strContNo.equals("")) {
			mResult.clear();
			mResult.add("FAIL");
			mResult.add("保单号为空，请核实"); // 查询：查询条件有误

			return false;
		}
		// 投保人
		// String strSQL =
		// " select a.appntname,a.appntsex,a.appntbirthday,nvl(s.email,s.email2),a.idtype,a.idno,nvl(s.mobile,s.phone),s.companyphone,s.homephone,s.fax,s.zipcode,s.postaladdress "
		// +
		// " from lcaddress s,lcappnt a where s.customerno=a.appntno and s.addressno=a.addressno  and a.contno='"
		// + strContNo +
		// "' and exists(select 1 from lcpol where contno = a.contno and appntno=a.appntno and appflag='1') ";
		String strSQL = " select a.appntname,a.appntsex,a.appntbirthday,(case when s.email is not null then s.email else s.email2 end),a.idtype,a.idno,(case when s.mobile is  not null then s.mobile else s.phone end),s.companyphone,s.homephone,s.fax,s.zipcode,s.postaladdress "
				+ " from lcaddress s,lcappnt a where s.customerno=a.appntno and s.addressno=a.addressno  and a.contno='"
				+ strContNo +"' and exists(select 1 from lcpol where contno = a.contno and appntno=a.appntno and appflag='1') ";
		
		String strReturn = execSql(strSQL);
		String strR = strReturn.substring(0, 3);
		if (strR.equals("100")) {
			mResult.clear();
			mResult.add("FAIL");
		} else {
			mResult.clear();
			mResult.add("SUCCESS");
		}
		mResult.add(strReturn);
		// 被保人
		strSQL = "select d.name,d.sex,d.birthday,d.relationtoappnt from lcinsured d where d.contno='"
				+ strContNo
				+ "'  and exists(select 1 from lcpol where contno = d.contno and insuredno=d.insuredno and appflag='1') ";
		
		strReturn = execSql(strSQL);
		strR = strReturn.substring(0, 3);
		if (strR.equals("100")) {
			mResult.add("FAIL");
		} else {
			mResult.add("SUCCESS");
		}
		mResult.add(strReturn);
		// 受益人
		strSQL = "select f.name,f.idtype,f.idno,f.relationtoinsured,f.bnfgrade,f.bnflot from lcbnf f where f.contno='"
				+ strContNo
				+ "' and exists(select 1 from lcpol where polno=f.polno and appflag='1') order by f.bnfno";
		
		strReturn = execSql(strSQL);
		strR = strReturn.substring(0, 3);
		if (strR.equals("100")) {
			mResult.add("FAIL");
		} else {
			mResult.add("SUCCESS");
		}
		mResult.add(strReturn);
		return true;
	}

	private boolean execQueryAgent(Vector inParam) {
		mResult.clear();
		mResult.add("SUCCESS");

		String strAgentcode = (String) inParam.get(1);
		String strName = (String) inParam.get(2);
		if ((strAgentcode == null) || (strName == null)) {
			mResult.clear();
			mResult.add("FAIL");
			mResult.add("查询：查询条件有误"); // 查询：查询条件有误

			return false;
		}

		String strSQL = (String) inParam.get(3);
		String strReturn = execSql(strSQL);
		String strR = strReturn.substring(0, 3);
		if (strR.equals("100")) {
			mResult.clear();
			mResult.add("FAIL");
		}
		mResult.add(strReturn);

		return true;
	}

	private boolean execQueryGrpPol(Vector inParam) {
		mResult.clear();
		mResult.add("SUCCESS");

		String strCustomerNo = (String) inParam.get(1);
		if ((strCustomerNo == null)) {
			mResult.clear();
			mResult.add("FAIL");
			mResult.add("客户号为空，请核实"); // 查询：查询条件有误

			return false;
		}

		String strSQL = " select grpcontno,grpname,riskname,subriskflag,peoples2,prem,amnt,sumpay from lcgrppol a ,lmriskapp b where a.riskcode=b.riskcode and customerno='" + strCustomerNo + "' ";
		
		String strReturn = execSql(strSQL);
		String strR = strReturn.substring(0, 3);
		if (strR.equals("100")) {
			mResult.clear();
			mResult.add("FAIL");
		}
		mResult.add(strReturn);
		return true;
	}

	private boolean execQueryGrpLCPol(Vector inParam) {
		mResult.clear();
		mResult.add("SUCCESS");

		String strGrpPolNo = (String) inParam.get(1);
		String strContNo = (String) inParam.get(2);
		String strName = (String) inParam.get(3);
		String strSex = (String) inParam.get(4);
		String strCount = (String) inParam.get(5);
		String strIndex = (String) inParam.get(6);
		boolean rFlag = false;
		if ((strGrpPolNo == null) || strGrpPolNo.equals("")) {
			mResult.clear();
			mResult.add("FAIL");
			mResult.add("团单号为空，请核实");
			return false;
		}

		int nCount = Integer.parseInt(strCount);
		int nIndex = Integer.parseInt(strIndex);
		int nBegin = (nCount * (nIndex - 1)) + 1;
		int nEnd = nCount * nIndex;
		// 如果传入的每页数为0，页数为1，则认为查询所有的数据
		if (nCount == 0 && nIndex == 1)
			rFlag = true;
		else {
			if ((nBegin > nEnd) || (nBegin <= 0)) {
				mResult.clear();
				mResult.add("FAIL");
				mResult.add("页数与每页个数不正确，请核实");
				return false;
			}
		}
        String strSQL = "select count(*) from lcpol where grpcontno='"
                + strGrpPolNo + "'";
            String strSQLHead = "select * ";
            String strSQLBody="";
            if(rFlag)
            {
            	if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
                 strSQLBody =
                    " from (select rownum r,contno,insuredname,insuredsex,prem,sumprem"
                    + " from lcpol where grpcontno='" + strGrpPolNo + "')" ;
            	}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
            		strSQLBody =
            				" from (select (@rownum:=@rownum+1) as r,contno,insuredname,insuredsex,prem,sumprem from (select @rownum:= 0,contno,insuredname,insuredsex,prem,sumprem"
                            + " from lcpol where grpcontno='" + strGrpPolNo + "') t) g" ;	
            	}
            }
            else     
            {
            	if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
            	strSQLBody =
                        " from (select rownum r,contno,insuredname,insuredsex,prem,sumprem"
                        + " from lcpol where grpcontno='" + strGrpPolNo + "') where r>="
                        + nBegin + " and r<=" + nEnd;
                	}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
                		strSQLBody =
                                " from (select (@rownum:=@rownum+1) as r,contno,insuredname,insuredsex,prem,sumprem from (select @rownum:= 0,contno,insuredname,insuredsex,prem,sumprem"
                                + " from lcpol where grpcontno='" + strGrpPolNo + "') t) g where r>="
                                + nBegin + " and r<=" + nEnd;
                	}
             
            }
		 if (!((strContNo == null) || strContNo.equals("")))
	        {
	            strSQLBody = strSQLBody + " and contno='" + strContNo + "'";
	            strSQL =strSQL +" and contno='" + strContNo + "' ";
	        }
	        if (!((strName == null) || strName.equals("")))
	        {
	            strSQLBody = strSQLBody + " and insuredname='" + strName + "'";
	            strSQL =strSQL +"  and insuredname='" + strName + "'";
	        }
	        if (!((strSex == null) || strSex.equals("")))
	        {
	            strSQLBody = strSQLBody + " and insuredsex='" + strSex + "'";
	            strSQL =strSQL +" and insuredsex='" + strSex + "'";
	        }
		
		String strReturn = execSql(strSQL);
		mResult.add(strReturn);

		
		strReturn = execSql(strSQLHead + strSQLBody);
		String strR = strReturn.substring(0, 3);
		if (strR.equals("100")) {
			mResult.clear();
			mResult.add("FAIL");
		}
		mResult.add(strReturn);

		return true;
	}

	private boolean execaQueryPROPOSALAcc(Vector inParam) {
		mResult.clear();
		mResult.add("SUCCESS");
		String strAgentcom = (String) inParam.get(1);
		String strPrtNo = (String) inParam.get(2);
		String strAppnt = (String) inParam.get(3);
		String strAppntID = (String) inParam.get(4);
		String strRiskcode = (String) inParam.get(5);
		String StartDate = (String) inParam.get(6);
		String EndDate = (String) inParam.get(7);
		String strCount = (String) inParam.get(8);
		String strIndex = (String) inParam.get(9);
		// String strPrtNo = (String) inParam.get(1);
		// if ((strPrtNo == null) || strPrtNo.equals(""))
		// {
		// mResult.clear();
		// mResult.add("FAIL");
		// return false;
		// }

		boolean rFlag = false;
		int nCount = Integer.parseInt(strCount);
		int nIndex = Integer.parseInt(strIndex);
		int nBegin = (nCount * (nIndex - 1)) + 1;
		int nEnd = nCount * nIndex;
		// 如果传入的每页数为0，页数为1，则认为查询所有的数据
		if (nCount == 0 && nIndex == 1)
			rFlag = true;
		else {
			if ((nBegin > nEnd) || (nBegin <= 0)) {
				mResult.clear();
				mResult.add("FAIL");
				mResult.add("页数与每页个数不正确，请核实");
				return false;
			}
		}

		String strSQL = "select count(*) "
		       	 + " from lcpol a where appflag='0'  ";
		       	 
		        String strSQLHead = "select * ";

		        String strSQLBody = "";
		        if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
		        	strSQLBody = "from (select rownum r, prtno c1,(select riskname from lmriskapp where riskcode=a.riskcode) c2,a.riskcode c3, "
		   		       	 + " a.appntname c4,a.prem c5,a.makedate c6,(nvl(decode((select b.activityid from lwmission a,lwactivity b where missionprop1 =a.prtno and a.activityid in ('0000001001','0000001003','0000001100','0000001150','0000001403','0000001402','0000001401','0000001089','0000001094','0000001099','0000001098','0000001501') and a.activityid=b.activityid),'0000001001','待复核','0000001003','待自核','0000001100','待人工核保','0000001150','待签单','0000001501','销售限制','待录入'),decode((select codename from lccont x,ldcode y where  x.uwflag=y.code and y.codetype='contuwstate' and x.uwflag in ('2','1','a') and x.prtno=a.prtno ),'1','已拒保','2','已延期','a','已撤单','待签单'))) c7"
		   		       	 + " from lcpol  a where  appflag='0'  ";
		        }else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
		        	strSQLBody = "from (select (@rownum:=@rownum+1) as r, c1, c2, c3, c4, c5, c6, c7"
			   		       	 + " from (select @rownum:=0, prtno c1,(select riskname from lmriskapp where riskcode=a.riskcode) c2,a.riskcode c3, "
			   		       	 + " a.appntname c4,a.prem c5,a.makedate c6,(case when (case (select b.activityid from lwmission a,lwactivity b where missionprop1 =a.prtno and a.activityid in ('0000001001','0000001003','0000001100','0000001150','0000001403','0000001402','0000001401','0000001089','0000001094','0000001099','0000001098','0000001501') and a.activityid=b.activityid) when '0000001001' then '待复核' when '0000001003' then '待自核' when '0000001100' then '待人工核保' when '0000001150' then '待签单' when '0000001501' then '销售限制' else '待录入' end) is not null then (case (select b.activityid from lwmission a,lwactivity b where missionprop1 =a.prtno and a.activityid in ('0000001001','0000001003','0000001100','0000001150','0000001403','0000001402','0000001401','0000001089','0000001094','0000001099','0000001098','0000001501') and a.activityid=b.activityid) when '0000001001' then '待复核' when '0000001003' then '待自核' when '0000001100' then '待人工核保' when '0000001150' then '待签单' when '0000001501' then '销售限制' else '待录入' end) else (case (select codename from lccont x,ldcode y where  x.uwflag=y.code and y.codetype='contuwstate' and x.uwflag in ('2','1','a') and x.prtno=a.prtno ) when '1' then '已拒保' when '2' then '已延期' when 'a' then '已撤单' else '待签单' end) end) c7"
			   		       	 + " from lcpol  a where  appflag='0'  ";
		        }
		        if (!((strAgentcom == null) || strAgentcom.equals("")))
		        {
		            strSQLBody = strSQLBody + "  and agentcom='" + strAgentcom + "' ";
		            strSQL = strSQL + "  and agentcom='" + strAgentcom + "' ";
		        }
		        if (!((strPrtNo == null) || strPrtNo.equals("")))
		        {
		            strSQLBody = strSQLBody + " and prtno='" + strPrtNo + "' ";
		            strSQL = strSQL + "  and prtno='" + strPrtNo + "' ";
		        }
		        if (!((StartDate == null) || StartDate.equals("")))
		        {
		            strSQLBody = strSQLBody + " and makedate>='" + StartDate + "' ";
		            strSQL = strSQL + "  and makedate>='" + StartDate + "' ";
		        }
		        if (!((EndDate == null) || EndDate.equals("")))
		        {
		            strSQLBody = strSQLBody + " and makedate<='" + EndDate + "'";
		            strSQL = strSQL + "  and makedate<='" + EndDate + "'";
		        }
		        if (!((strAppnt == null) || strAppnt.equals("")))
		        {
		            strSQLBody = strSQLBody + " and appntname='" + strAppnt + "'";
		            strSQL = strSQL + "   and appntname='" + strAppnt + "'";
		        }
		        if (!((strAppntID == null) || strAppntID.equals("")))
		        {
		            strSQLBody = strSQLBody + " and exists(select 1 from lcappnt where appntno=a.appntno and idno='" + strAppntID + "')";
		            strSQL = strSQL +" and exists(select 1 from lcappnt where appntno=a.appntno and idno='" + strAppntID + "')";
		        }
		        if (!((strRiskcode == null) || strRiskcode.equals("")))
		        {
		            strSQLBody = strSQLBody + " and riskcode='" + strRiskcode + "'";
		            strSQL = strSQL + "   and riskcode='" + strRiskcode + "'";
		        }
		        if(rFlag)
		        	strSQLBody = strSQLBody+ " order by makedate desc  ) t) g";
		        else
		        	strSQLBody = strSQLBody+ " order by makedate desc  ) t) g where r>=" + nBegin + " and r<=" + nEnd;
		        strSQL = strSQL + " order by c6 desc "; 
		logger.debug("strSQL---" + strSQL);

		String strReturn = execSql(strSQL);
		String strR = strReturn.substring(0, 3);
		if (strR.equals("100")) {
			mResult.clear();
			mResult.add("FAIL");
		}
		mResult.add(strReturn);

		String strReturn1 = execSql(strSQLHead + strSQLBody);
		String strR1 = strReturn1.substring(0, 3);
		if (strR1.equals("100")) {
			mResult.clear();
			mResult.add("FAIL");
		}

		mResult.add(strReturn1);

		return true;
	}

	private boolean execaQueryTPROPOSAL(Vector inParam) {
		mResult.clear();
		mResult.add("SUCCESS");
		// String strSQL = (String) inParam.get(1);
		String strSQL = "";
		if (SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)) {
			strSQL = " select nvl(agentcom,'null') r, prtno c1,(select riskname from lmriskapp where riskcode=a.riskcode) c2,a.riskcode c3, "
					+ " a.appntname c4,a.prem c5,a.makedate c6,(nvl(decode((select b.activityid from lwmission a,lwactivity b where missionprop1 =a.prtno and a.activityid in ('0000001001','0000001003','0000001100','0000001150','0000001403','0000001402','0000001401','0000001089','0000001094','0000001099','0000001098','0000001501') and a.activityid=b.activityid),'0000001001','待复核','0000001003','待自核','0000001100','待人工核保','0000001150','待签单','0000001501','销售限制','待录入'),decode((select codename from lccont x,ldcode y where  x.uwflag=y.code and y.codetype='contuwstate' and x.uwflag in ('2','1','a') and x.prtno=a.prtno ),'1','已拒保','2','已延期','a','已撤单','待签单'))) c7"
					+ " from lcpol  a where  appflag='0'  and makedate='"
					+ PubFun.getCurrentDate()
					+ "'  and salechnl='05' order by makedate desc  ";
		} else if (SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)) {
			String sqlAprt1 = " case when (select b.activityid from lwmission a,lwactivity b where missionprop1 =a.prtno and a.activityid in ('0000001001','0000001003','0000001100','0000001150','0000001403','0000001402','0000001401','0000001089','0000001094','0000001099','0000001098','0000001501') and a.activityid=b.activityid) when '0000001001' then '待复核' when '0000001003' then '待自核' when '0000001100' then '待人工核保' when '0000001150' then '待签单' when '0000001501' then '销售限制' else '待录入' end ";
			String sqlAprt2 = " case when (select codename from lccont x,ldcode y where  x.uwflag=y.code and y.codetype='contuwstate' and x.uwflag in ('2','1','a') and x.prtno=a.prtno ) when '1' then '已拒保' when '2' then '已延期' when 'a' then '已撤单' else '待签单' end ";
			strSQL = " select (case when agentcom is not null agentcom else 'null' end) r, prtno c1,(select riskname from lmriskapp where riskcode=a.riskcode) c2,a.riskcode c3, "
					+ " a.appntname c4,a.prem c5,a.makedate c6,(case when ("
					+ sqlAprt1
					+ ") is not null then ("
					+ sqlAprt1
					+ ") else ("
					+ sqlAprt2
					+ ") end) c7"
					+ " from lcpol  a where  appflag='0'  and makedate='"
					+ PubFun.getCurrentDate()
					+ "'  and salechnl='05' order by makedate desc  ";
		}

		
		String strReturn = execSql(strSQL);
		String strR = strReturn.substring(0, 3);
		if (strR.equals("100")) {
			mResult.clear();
			mResult.add("FAIL");
		}
		mResult.add(strReturn);

		return true;
	}

	private boolean execaQueryLCPOLAcc(Vector inParam) {
		mResult.clear();
		mResult.add("SUCCESS");

		String tAgentCom = (String) inParam.get(1);
		String StartDate = (String) inParam.get(2);
		String EndDate = (String) inParam.get(3);
		String strCount = (String) inParam.get(4);
		String strIndex = (String) inParam.get(5);
		boolean rFlag = false;
		int nCount = Integer.parseInt(strCount);
		int nIndex = Integer.parseInt(strIndex);
		int nBegin = (nCount * (nIndex - 1)) + 1;
		int nEnd = nCount * nIndex;
		// 如果传入的每页数为0，页数为1，则认为查询所有的数据
		if (nCount == 0 && nIndex == 1)
			rFlag = true;
		else {
			if ((nBegin > nEnd) || (nBegin <= 0)) {
				mResult.clear();
				mResult.add("FAIL");
				mResult.add("页数与每页个数不正确，请核实");
				return false;
			}
		}

		String strSQL = "select count(*) "
				+ " from lacommision   a "
				+ " where a.grppolno = '00000000000000000000' and exists (select 1 from laagent where agentcode=a.agentcode and branchtype = '7') ";
		String strSQLHead = "select * ";

		String strSQLBody = "";
		// 判断数据库类型
		if (SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)) {
			strSQLBody = " from (select rownum r, a.contno c1,P11 c2,(select riskname from lmriskapp where riskcode=a.riskcode) c3,a.riskcode c4,a.payyears c5,a.payyear + 1 c6, decode(a.payintv,'-1','趸交','0','趸交','期交') c7,decode(p5, '1', '自动垫交', '正常交费') c8,a.makepoldate c9,a.signdate c10,a.getpoldate c11,a.tmakedate c12,(select name from lacom where agentcom=substr(a.agentcom,1,13)) c13,substr(a.agentcom,1,13) c14,a.insuyear c15, a.transmoney c16,(select name from lacom where agentcom=a.agentcom) c17,a.cvalidate c18, decode(p5, '1', '自动垫交', '正常交费') c19, decode(paycount, '0', '首期', '1', '首期', '续期') c20, P14 c21,a.insuyearflag c22 "
					+ " from lacommision   a "
					+ " where a.grppolno = '00000000000000000000' and exists (select 1 from laagent where agentcode=a.agentcode and branchtype = '7') ";
		} else if (SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)) {
			String strSQLBodyAprt1 = "select (@rownum := @rownum + 1) AS r,c1,c2,c3,c4,c5,c6,c7,c8,c9,c10,c11,c12,c13,c14,c15,c16,c17,c18,c19,c20,c21,c22 ";
			String strSQLBodyAprt2 = " select @rownum:= 0, a.contno c1,P11 c2,(select riskname from lmriskapp where riskcode=a.riskcode) c3,a.riskcode c4,a.payyears c5,a.payyear + 1 c6, (case a.payintv when '-1' then '趸交' when '0' then '趸交' else '期交' end) c7,( case p5 when '1' then '自动垫交' else '正常交费' end) c8,a.makepoldate c9,a.signdate c10,a.getpoldate c11,a.tmakedate c12,(select name from lacom where agentcom=substr(a.agentcom,1,13)) c13,substr(a.agentcom,1,13) c14,a.insuyear c15, a.transmoney c16,(select name from lacom where agentcom=a.agentcom) c17,a.cvalidate c18, ( case p5 when '1' then '自动垫交' else '正常交费' end) c19, ( case paycount when '0' then '首期' when '1' then '首期' else '续期' end) c20, P14 c21,a.insuyearflag c22  ";
			strSQLBody = " from ("
					+ strSQLBodyAprt1
					+ " from ("
					+ strSQLBodyAprt2
					+ " from lacommision a "
					+ " where a.grppolno = '00000000000000000000' and exists (select 1 from laagent where agentcode=a.agentcode and branchtype = '7') ";
		}

		 if (!((tAgentCom == null) || tAgentCom.equals("")))
	       {
	           strSQLBody = strSQLBody + " and a.agentcom like '" + tAgentCom + "%' ";
	           strSQL = strSQL + "  and a.agentcom like '" + tAgentCom + "%' ";
	       }
		if (!((StartDate == null) || StartDate.equals("")))
	       {
	           strSQLBody = strSQLBody + " and a.tmakedate >= '" + StartDate + "' ";
	           strSQL = strSQL + "  and a.tmakedate >= '" + StartDate + "' ";
	       }
	       if (!((EndDate == null) || EndDate.equals("")))
	       {
	           strSQLBody = strSQLBody + " and a.tmakedate <= '" + EndDate + "'";
	           strSQL = strSQL + "  and a.tmakedate <= '" + EndDate + "' ";
	       }
	       if(rFlag)
	    	   strSQLBody = strSQLBody + ") ";
	       else
	    	 strSQLBody = strSQLBody + ") where r>=" + nBegin + " and r<=" + nEnd;

		
		String strReturn = execSql(strSQL);
		mResult.add(strReturn);

		
		strReturn = execSql(strSQLHead + strSQLBody);
		String strR = strReturn.substring(0, 3);
		if (strR.equals("100")) {
			mResult.clear();
			mResult.add("FAIL");
		}
		mResult.add(strReturn);

		return true;
	}

	private boolean execQueryLCInsureAcc(Vector inParam) {
		mResult.clear();
		mResult.add("SUCCESS");

		String strGrpPolNo = (String) inParam.get(1);
		String strContNo = (String) inParam.get(2);
		if ((strGrpPolNo == null) || strGrpPolNo.equals("")) {
			mResult.clear();
			mResult.add("FAIL");
			mResult.add("团单号为空，请核实");

			return false;
		}
		String strGrpNo = (String) inParam.get(2);
		if ((strGrpNo == null) || strGrpNo.equals("")) {
			mResult.clear();
			mResult.add("FAIL");
			mResult.add("保单号为空，请核实");
			return false;
		}

		String strSQL = " select insuaccname,insuaccbala from lcinsureacc,lmriskinsuacc   where lcinsureacc.insuaccno=lmriskinsuacc.insuaccno and grpcontno='"
				+strGrpPolNo+"' and contno='"+strContNo+"' ";

		String strReturn = execSql(strSQL);
		String strR = strReturn.substring(0, 3);
		if (strR.equals("100")) {
			mResult.clear();
			mResult.add("FAIL");
		}
		mResult.add(strReturn);

		return true;
	}

	private boolean execQueryLCContAcc(Vector inParam) {
		mResult.clear();
		mResult.add("SUCCESS");

		String strAppntNo = (String) inParam.get(1);
		if ((strAppntNo == null) || strAppntNo.equals("")) {
			mResult.clear();
			mResult.add("FAIL");
			mResult.add("客户号为空，请核实");
			return false;
		}
		// zy 2009-11-04 调整查询被保人逻辑，如果为121301附加豁免保费险种，则被保人显示为该险种的投保人信息
		// String strSQL = "select a.contno,'有效', "
		// +
		// " a.appntname,decode(nvl((select count(*) from ljspayperson where polno = a.polno), 0),'0','否','是'),(select riskname from lmriskapp where riskcode=a.riskcode), "
		// +
		// " decode(a.riskcode,'121301',a.appntname,a.insuredname),a.cvalidate,(case when exists (select 1 from lccontstate where contno = a.contno and polno = a.polno and statetype = 'Terminate' and state = '1'and startdate <= sysdate and enddate is null) then '终止' when exists (select 1 from lccontstate where contno = a.contno and polno = a.polno and statetype = 'Available'and state = '1' and startdate <= sysdate and enddate is null) then '失效' else '有效' end)  from lcpol a   where  a.appntno = '"
		// + strAppntNo +
		// "'  and a.appflag='1' and grpcontno='00000000000000000000' order by a.contno,a.riskcode ";
		// zy 2010-01-26 加上家庭单的排除
		// String strSQL = "select a.contno,'有效', "
		// +
		// " a.appntname,decode(nvl((select count(*) from ljspayperson where polno = a.polno), 0),'0','否','是'),(select riskname from lmriskapp where riskcode=a.riskcode), "
		// +
		// " decode(a.riskcode,'121301',a.appntname,a.insuredname),a.cvalidate,(case when exists (select 1 from lccontstate where contno = a.contno and polno = a.polno and statetype = 'Terminate' and state = '1'and startdate <= sysdate and enddate is null) then '终止' when exists (select 1 from lccontstate where contno = a.contno and polno = a.polno and statetype = 'Available'and state = '1' and startdate <= sysdate and enddate is null) then '失效' else '有效' end)  from lcpol a   where  a.appntno = '"
		// + strAppntNo +
		// "'  and a.appflag='1' and grpcontno='00000000000000000000' "
		// +
		// "and exists(select 1 from lccont where contno=a.contno and familyid is null ) "
		// +
		// " union all select a.contno,'有效',  a.appntname,decode(nvl((select count(*) from ljspayperson where polno = a.polno), 0),'0','否','是'),(select riskname from lmriskapp where riskcode=a.riskcode),"
		// +
		// " decode(a.riskcode,'121301',a.appntname,a.insuredname),a.cvalidate,"
		// +
		// " (case when exists (select 1 from lccontstate where contno = a.contno and polno = a.polno and statetype = 'Terminate' and state = '1'and startdate <= sysdate and enddate is null) then '终止' when exists (select 1 from lccontstate where contno = a.contno and polno = a.polno and statetype = 'Available'and state = '1' and startdate <= sysdate and enddate is null) then '失效' else '有效' end)"
		// + "  from lcpol a  where  a.insuredno = '" + strAppntNo +
		// "'  and a.appflag='1' and grpcontno<>'00000000000000000000'"
		// +
		// " and exists(select 1 from lccont where contno=a.contno and familyid is null ) ";
		String strSQL = "select a.contno,'有效', "
				+ " a.appntname,( case (case when (select count(*) from ljspayperson where polno = a.polno) then (select count(*) from ljspayperson where polno = a.polno) else 0 end) when '0' then '否' else '是' end),(select riskname from lmriskapp where riskcode=a.riskcode), "
				+ " ( case (a.riskcode) when '121301' then a.appntname else a.insuredname end),a.cvalidate,(case when exists (select 1 from lccontstate where contno = a.contno and polno = a.polno and statetype = 'Terminate' and state = '1'and startdate <= now() and enddate is null) then '终止' when exists (select 1 from lccontstate where contno = a.contno and polno = a.polno and statetype = 'Available'and state = '1' and startdate <= now() and enddate is null) then '失效' else '有效' end)  from lcpol a   where  a.appntno = '"
				+strAppntNo
				+ "'  and a.appflag='1' and grpcontno='00000000000000000000' "
				+ "and exists(select 1 from lccont where contno=a.contno and familyid is null ) "
				+ " union all select a.contno,'有效',  a.appntname,( case (case when (select count(*) from ljspayperson where polno = a.polno) is not null then (select count(*) from ljspayperson where polno = a.polno) else 0 end) when '0' then '否' else '是' end),(select riskname from lmriskapp where riskcode=a.riskcode),"
				+ " ( case a.riskcode when '121301' then a.appntname else a.insuredname end),a.cvalidate,"
				+ " (case when exists (select 1 from lccontstate where contno = a.contno and polno = a.polno and statetype = 'Terminate' and state = '1'and startdate <= now() and enddate is null) then '终止' when exists (select 1 from lccontstate where contno = a.contno and polno = a.polno and statetype = 'Available'and state = '1' and startdate <= now() and enddate is null) then '失效' else '有效' end)"
				+ "  from lcpol a  where  a.insuredno = '"
				+strAppntNo
				+ "'  and a.appflag='1' and grpcontno<>'00000000000000000000'"
				+ " and exists(select 1 from lccont where contno=a.contno and familyid is null ) ";
		String result = "";// 返回结果,是非家庭单与家庭单组合

		String strReturn = execSql(strSQL);
		String strR = strReturn.substring(0, 3);
		if (strR.equals("100")) {
			mResult.clear();
			mResult.add("FAIL");
		}
		mResult.add(strReturn);

		// zy 家庭单的查询
		String jSQL = "";
		if (SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)) {
			jSQL = "select b.familyid,(select contplanname from ldplan where contplancode in (select riskcode from lmcardrisk where certifycode=substr(b.familyid,3,6))), "
					+ "appntname,(select concat(insuyear,insuyearflag) from lcpol where contno=a.contno and rownum=1), cvalidate,b.name,a.contno from lccont a,lcinsured b where  a.appntno = '"
					+strAppntNo
					+ "' "
					+ "and a.contno=b.contno and b.familyid is not null order by b.sequenceno ";
		} else if (SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)) {
			jSQL = "select b.familyid,(select contplanname from ldplan where contplancode in (select riskcode from lmcardrisk where certifycode=substr(b.familyid,3,6))), "
					+ "appntname,(select concat(insuyear,insuyearflag) from lcpol where contno=a.contno limit 1), cvalidate,b.name,a.contno from lccont a,lcinsured b where  a.appntno = '"
					+strAppntNo
					+ "' "
					+ "and a.contno=b.contno and b.familyid is not null order by b.sequenceno ";
		}

		String strReturnj = execSql(jSQL);
		strR = strReturnj.substring(0, 3);
		if (strR.equals("100")) {

			mResult.add("FAIL");
		} else {
			mResult.add("SUCCESS");
		}

		mResult.add(strReturnj);

		return true;
	}

	private boolean execQueryPAYAcc(Vector inParam) {
		mResult.clear();
		mResult.add("SUCCESS");

		String strContNo = (String) inParam.get(1);
		if ((strContNo == null) || strContNo.equals("")) {
			mResult.clear();
			mResult.add("FAIL");
			mResult.add("保单号为空，请核实");
			return false;
		}

		// String strSQL =
		// "select (select riskname from lmriskapp where riskcode=a.riskcode),sumactupaymoney,paycount, "
		// +" (select codename from ldcode d where codetype='paylocation' and exists (select paylocation from lcpol c where  c.polno=a.polno and c.paylocation=d.code)),lastpaytodate,enteraccdate, "
		// +" sumactupaymoney,(select bankaccno from ljapay where payno=a.payno),(select bankcode from ljapay where payno=a.payno),(select accname from ljapay where payno=a.payno) "
		// +" from ljapayperson a where  contno='" + strContNo +
		// "' and paycount>1 and paytype='ZC'  order by a.paycount,a.polno ";
		String strSQL = "select (select riskname from lmriskapp where riskcode=a.riskcode),sumactupaymoney,paycount, "
				+ " (select codename from ldcode d where codetype='paylocation' and exists (select paylocation from lccont c where  c.contno=a.contno and c.paylocation=d.code)),lastpaytodate,enteraccdate, "
				+ " sumactupaymoney,(select bankaccno from ljapay where payno=a.payno),(select bankcode from ljapay where payno=a.payno),(select accname from ljapay where payno=a.payno) "
				+ " from ljapayperson a where  contno='"
				+strContNo
				+ "' and paycount>1 and paytype='ZC'  order by a.paycount,a.polno ";

		String strReturn = execSql(strSQL);
		String strR = strReturn.substring(0, 3);
		if (strR.equals("100")) {
			mResult.clear();
			mResult.add("FAIL");
		}
		mResult.add(strReturn);

		return true;
	}

	private boolean execQueryBONUSAcc(Vector inParam) {
		mResult.clear();
		mResult.add("SUCCESS");

		String strContNo = (String) inParam.get(1);
		if ((strContNo == null) || strContNo.equals("")) {
			mResult.clear();
			mResult.add("FAIL");
			mResult.add("保单号为空，请核实");
			return false;
		}

		// String strSQL =
		// " select a.fiscalyear,a.bonusmoney,a.agetdate from lobonuspol a where a.contno='"
		// + strContNo + "'  order by a.fiscalyear,a.polno ";
		// 调整分红的查询逻辑，查询显示的必须是已分配的红利，及查询账户轨迹表
		String strSQL = " select a.fiscalyear,a.bonusmoney,a.agetdate from lobonuspol a where a.contno='" + strContNo + "' and agetdate is not null  order by a.fiscalyear,a.polno ";

		String strReturn = execSql(strSQL);
		String strR = strReturn.substring(0, 3);
		if (strR.equals("100")) {
			mResult.clear();
			mResult.add("FAIL");
		}
		mResult.add(strReturn);

		return true;
	}

	private boolean execQueryWNACCOUNTAcc(Vector inParam) {
		mResult.clear();
		mResult.add("SUCCESS");

		String strContNo = (String) inParam.get(1);
		if ((strContNo == null) || strContNo.equals("")) {
			mResult.clear();
			mResult.add("FAIL");
			mResult.add("保单号为空，请核实");
			return false;
		}

		String strSQL = "select (select insuaccname from lmriskinsuacc where insuaccno=a.insuaccno),a.insuaccbala,a.baladate from lcinsureacc a where insuaccno='000006' and contno='" + strContNo + "' ";
		
		String strReturn = execSql(strSQL);
		String strR = strReturn.substring(0, 3);
		if (strR.equals("100")) {
			mResult.clear();
			mResult.add("FAIL");
		}
		mResult.add(strReturn);

		return true;
	}

	private boolean execQueryWNACCOUNTAccLog(Vector inParam) {
		mResult.clear();
		mResult.add("SUCCESS");

		String strContNo = (String) inParam.get(1);
		String acctype = (String) inParam.get(2);
		String strStartDate = (String) inParam.get(3);
		String strEndDate = (String) inParam.get(4);
		if ((strContNo == null) || strContNo.equals("")) {
			mResult.clear();
			mResult.add("FAIL");
			mResult.add("保单号为空，请核实");
			return false;
		}
		String strSQL = " select ( case a.moneytype when 'BF' then '初始保费' when 'TBFY' then '退保费用' when'JJ' then '持续奖金' when 'GL' then '管理费' when 'LX' then '利息' when 'TB' then '退保金' else '其他' end),a.money,a.paydate from lcinsureacctrace a where insuaccno='000006' and contno='" + strContNo + "' and paydate>='" + strStartDate + "' and paydate<='" + strEndDate + "' order by paydate desc ";
		
		String strReturn = execSql(strSQL);
		String strR = strReturn.substring(0, 3);
		if (strR.equals("100")) {
			mResult.clear();
			mResult.add("FAIL");
		}
		mResult.add(strReturn);

		return true;
	}

	private boolean execQueryEDORAcc(Vector inParam) {
		mResult.clear();
		mResult.add("SUCCESS");

		String strContNo = (String) inParam.get(1);
		if ((strContNo == null) || strContNo.equals("")) {
			mResult.clear();
			mResult.add("FAIL");
			mResult.add("保单号为空，请核实");
			return false;
		}

		String strSQL = "";
		if (SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)) {
			strSQL = "select a.edorappdate,b.appntname,(select idtype from lcappnt where appntno = b.appntno and contno = b.contno),(select idno	from lcappnt where appntno = b.appntno and contno = b.contno), "
					+ " b.insuredname,(select idtype from lcinsured where contno = b.contno and insuredno = b.insuredno), (select idno	from lcinsured where contno = b.contno and insuredno = b.insuredno), "
					+ " (select edorname from lmedoritem where edorcode=a.edortype and appobj in ('I','E') and rownum=1) "
					+ "	from lpedoritem a, lccont b "
					+ "  where a.contno = '" + strContNo + "' "
					+ "	 and b.contno = a.contno "
					+ "   order by a.edorappdate desc ";
		} else if (SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)) {
			strSQL = "select a.edorappdate,b.appntname,(select idtype from lcappnt where appntno = b.appntno and contno = b.contno),(select idno	from lcappnt where appntno = b.appntno and contno = b.contno), "
					+ " b.insuredname,(select idtype from lcinsured where contno = b.contno and insuredno = b.insuredno), (select idno	from lcinsured where contno = b.contno and insuredno = b.insuredno), "
					+ " (select edorname from lmedoritem where edorcode=a.edortype and appobj in ('I','E') limit 1) "
					+ "	from lpedoritem a, lccont b "
					+ "  where a.contno = '" + strContNo + "' "
					+ "	 and b.contno = a.contno "
					+ "   order by a.edorappdate desc ";
		}
		
		String strReturn = execSql(strSQL);
		String strR = strReturn.substring(0, 3);
		if (strR.equals("100")) {
			mResult.clear();
			mResult.add("FAIL");
		}
		mResult.add(strReturn);

		return true;
	}

	private boolean execQueryCLAIMAcc(Vector inParam) {
		mResult.clear();
		mResult.add("SUCCESS");

		String strContNo = (String) inParam.get(1);
		if ((strContNo == null) || strContNo.equals("")) {
			mResult.clear();
			mResult.add("FAIL");
			mResult.add("保单号为空，请核实");
			return false;
		}

		String strSQL = "select a.caseno, a.customername, a.accdate, "
				+ " (case b.clmstate when '10' then '报案' when '20' then '立案' when '30' then '审核'  when '35' then '预付'  when '40' then '审批'  when '50' then '结案'  when '60' then '关闭'  ELSE '完成'  END ), "
				+ " a.customername, a.idtype, a.idno, (case substr(c.getdutykind,2) when '00' then '医疗' when '01' then '伤残,烧伤,重要器官切除' when '02' then '身故'  when '03' then '高残'  when '04' then '重大疾病'  when '05' then '特种疾病'  when '09' then '豁免'  ELSE '成'  END ), b.makedate "
				+ " from llcase a, llclaimpolicy b,  LLClaimDutyKind c "
				+ " where  a.caseno = b.clmno and b.clmno = c.clmno and b.contno='" + strContNo +"' "+" order by a.caseno ";

		String strReturn = execSql(strSQL);
		String strR = strReturn.substring(0, 3);
		if (strR.equals("100")) {
			mResult.clear();
			mResult.add("FAIL");
		}
		mResult.add(strReturn);

		return true;
	}

	private boolean execQueryGETDRAWAcc(Vector inParam) {
		mResult.clear();
		mResult.add("SUCCESS");

		String strContNo = (String) inParam.get(1);
		if ((strContNo == null) || strContNo.equals("")) {
			mResult.clear();
			mResult.add("FAIL");
			mResult.add("保单号为空，请核实");
			return false;
		}

		String strSQL = " select ( case a.feefinatype when 'YF' then '年金' when 'EF' then '满期金' else '利息' end),( case (select paymode from ljaget where actugetno=a.actugetno) when '1' then '现金' when '4' then '银行转账' else '其他' end),a.getmoney,a.makedate,a.confdate from ljagetdraw a where contno='" + strContNo + "' order by makedate desc  ";
		
		String strReturn = execSql(strSQL);
		String strR = strReturn.substring(0, 3);
		if (strR.equals("100")) {
			mResult.clear();
			mResult.add("FAIL");
		}
		mResult.add(strReturn);

		return true;
	}

	private boolean execQueryAGENTAcc(Vector inParam) {
		mResult.clear();
		mResult.add("SUCCESS");

		String strContNo = (String) inParam.get(1);
		if ((strContNo == null) || strContNo.equals("")) {
			mResult.clear();
			mResult.add("FAIL");
			mResult.add("保单号为空，请核实");
			return false;
		}

		String strSQL = " select name ,sex,phone,mobile,agentcode from laagent a where exists (select 1 from lccont c where contno='" + strContNo + "' and c.agentcode=a.agentcode) ";
		
		String strReturn = execSql(strSQL);
		String strR = strReturn.substring(0, 3);
		if (strR.equals("100")) {
			mResult.clear();
			mResult.add("FAIL");
		}
		mResult.add(strReturn);

		return true;
	}

	private boolean execACCRATEAcc(Vector inParam) {
		mResult.clear();
		mResult.add("SUCCESS");

		String strRiskcode = (String) inParam.get(1);
		String strStartDate = (String) inParam.get(2);
		String strEndDate = (String) inParam.get(3);
		if ((strRiskcode == null) || strRiskcode.equals("")) {
			mResult.clear();
			mResult.add("FAIL");
			mResult.add("产品号码为空，请核实");

			return false;
		}

		String strSQL = " select aratedate,rate,baladate,(select riskname from lmrisk where riskcode=a.riskcode),riskcode from lminsuaccrate a where riskcode='"+strRiskcode+"' and aratedate>='"+strStartDate+"' and aratedate<='"+strEndDate+"' ";
		
		String strReturn = execSql(strSQL);
		String strR = strReturn.substring(0, 3);
		if (strR.equals("100")) {
			mResult.clear();
			mResult.add("FAIL");
		}
		mResult.add(strReturn);
		return true;
	}

	private boolean execaQueryAIRGRPPOL(Vector inParam) {
		mResult.clear();
		mResult.add("SUCCESS");

		String tGrpContNo = (String) inParam.get(1);

		if ((tGrpContNo == null) || tGrpContNo.equals("")) {
			mResult.clear();
			mResult.add("FAIL");
			mResult.add("被保人姓名为空，请核实");

			return false;
		}

		String strSQL = "select grpcontno,(Select riskname from lmrisk Where riskcode=a.riskcode),"
				+ "(concat(InsuYear,(Case InsuYearFlag When 'D' Then '天' Else '单位有误' End ))),"
				+ "(select name from lacom where agentcom =a.agentcom),"
				+ "GrpName from LCGrpAirCont a where a.grpcontno='" + tGrpContNo +"'";

		
		String strReturn = execSql(strSQL);
		String strR = strReturn.substring(0, 3);
		if (strR.equals("100")) {
			mResult.clear();
			mResult.add("FAIL");
		} else {
			mResult.clear();
			mResult.add("SUCCESS");
		}
		mResult.add(strReturn);

		strSQL = "Select Insuredname, Insuredidno, Prem, Amnt,"
				+ "(concat(InsuYear,(Case InsuYearFlag When 'D' Then '天' Else '单位有误' End ))) "
				+ " From lcairpol a  where  standbyflag2='" + tGrpContNo +"'";
		
		strReturn = execSql(strSQL);
		strR = strReturn.substring(0, 3);
		if (strR.equals("100")) {
			mResult.add("FAIL");
		} else {
			mResult.add("SUCCESS");
		}
		mResult.add(strReturn);

		return true;
	}

	private boolean execaQueryAIRPOL(Vector inParam) {
		mResult.clear();
		mResult.add("SUCCESS");

		String tContNo = (String) inParam.get(1);
		String tInsuredName = (String) inParam.get(2);
		String tInsuredIdNo = (String) inParam.get(3);

		if ((tInsuredName == null) || tInsuredName.equals("")) {
			mResult.clear();
			mResult.add("FAIL");
			mResult.add("被保人姓名为空，请核实");

			return false;
		}

		if ((tInsuredIdNo == null) || tInsuredIdNo.equals("")) {
			mResult.clear();
			mResult.add("FAIL");
			mResult.add("被保人证件号为空，请核实");

			return false;
		}

		String strSQL = "Select Insuredname, insuredidno ,(Select Riskname From Lmrisk Where Riskcode = a.Riskcode), "
				+ "polno,(concat(Insuyear , (Case Insuyearflag When 'D' Then '天' when 'M' then '月' when 'Y' then '年' when 'A' then '岁' Else '单位有误' End))), "
				+ "prem,amnt,(select name from ldcom where comcode = substr(a.managecom,1,4)) From lcairpol a where insuredname = '" + tInsuredName + "' and  insuredidno = '" + tInsuredIdNo + "'";

		if (!((tContNo == null) || tContNo.equals(""))) {
        	strSQL =  strSQL + " and polno='" + tContNo +"'";
		}
		
		String strReturn = execSql(strSQL);
		String strR = strReturn.substring(0, 3);
		if (strR.equals("100")) {
			mResult.clear();
			mResult.add("FAIL");
		} else {
			mResult.clear();
			mResult.add("SUCCESS");
		}
		mResult.add(strReturn);

		return true;
	}

	// zy 2010-01-26 校验是否为组合产品
	private boolean isPortfolio(String fContno) {
		boolean PortfolioFlag = false;
		String mCertiCode = fContno.substring(2, 8);
		String pSql = "select portfolioflag from lmcardrisk where certifycode='"+"?mCertiCode?"+"'";
		SQLwithBindVariables sqlbv31 = new SQLwithBindVariables();
		sqlbv31.sql(pSql);
		sqlbv31.put("mCertiCode", mCertiCode);
		ExeSQL mExeSQL = new ExeSQL();
		String mPortfolio = mExeSQL.getOneValue(sqlbv31);
		if (mPortfolio == null || "".equals(mPortfolio))
			PortfolioFlag = false;
		else
			PortfolioFlag = true;
		return PortfolioFlag;
	}

	/**
	 * 执行查询
	 * 
	 * @param inParam
	 * @return
	 */
	public String execSql(String strSQL) {
		logger.debug("Server ExecSql: " + strSQL);

		ExeSQL tExeSQL = new ExeSQL();
		String strReturn = tExeSQL.getEncodedResult(strSQL, 1);

		return strReturn;
	}

	public static void main(String[] args) {
		// WebServiceServer webServiceServer1 = new WebServiceServer();
		VData tVData = new VData();
		// String strAgentcom = "86350100071237";
		// String strPrtNo ="12001302000017";
		// String StartDate ="2008-1-1";
		// String EndDate ="2008-12-1";
		// String strCount ="12";
		// String strIndex = "1";
		// int nCount = Integer.parseInt(strCount);
		// int nIndex = Integer.parseInt(strIndex);
		// int nBegin = (nCount * (nIndex - 1)) + 1;
		// int nEnd = nCount * nIndex;
		//
		//
		// String strSQL = "select count(*) "
		// + " from lcpol where appflag='0'  "
		// ;
		// String strSQLHead = "select * ";
		//
		// String strSQLBody =
		// " from (select prtno c1,(select riskname from lmriskapp where riskcode=a.riskcode) c2,a.riskcode c3, "
		// +
		// " a.appntname c4,a.prem c5,a.makedate c6,(nvl((select b.activityname from lwmission a,lwactivity b where missionprop1 =a.prtno and a.activityid in ('0000001001','0000001003','0000001100','0000001150','0000001403','0000001402','0000001401','0000001089','0000001094','0000001099','0000001098','0000001501') and a.activityid=b.activityid),(select codename from lccont x,ldcode y where  x.uwflag=y.code and y.codetype='contuwstate' and x.uwflag in ('2','1','a') and x.prtno=a.prtno ))) c7"
		// + " from lcpol  a where  appflag='0'  ";
		// if (!((strAgentcom == null) || strAgentcom.equals("")))
		// {
		// strSQLBody = strSQLBody + "  and agentcom='" + strAgentcom + "' ";
		// strSQL = strSQL + "  and agentcom='" + strAgentcom + "' ";
		// }
		// if (!((strPrtNo == null) || strPrtNo.equals("")))
		// {
		// strSQLBody = strSQLBody + " and prtno='" + strPrtNo + "' ";
		// strSQL = strSQL + "  and prtno='" + strPrtNo + "' ";
		// }
		// if (!((StartDate == null) || StartDate.equals("")))
		// {
		// strSQLBody = strSQLBody + " and makedate>='" + StartDate + "' ";
		// strSQL = strSQL + "  and makedate>='" + StartDate + "' ";
		// }
		// if (!((EndDate == null) || EndDate.equals("")))
		// {
		// strSQLBody = strSQLBody + " and makedate<='" + EndDate + "'";
		// strSQL = strSQL + "  and makedate<='" + EndDate + "'";
		// }
		// strSQL = strSQL+ " order by makedate desc ";
		// strSQLBody = strSQLBody + " order by makedate desc "
		// + ") where r>=" + nBegin + " and r<=" + nEnd;
		// VData mOutputData = new VData();
		// mOutputData.add("AQUERY||PROPOSAL");
		// mOutputData.add(strPrtNo);
		// mOutputData.add(strCount);
		// mOutputData.add(strIndex);
		// mOutputData.add(strSQL);
		// mOutputData.add(strSQLHead+strSQLBody);
		// tVData.add("LOGIN||MATCHING");
		// tVData.add("8611000020033001");
		// tVData.add("86210100277424");
		// tVData.add("健十一");
		// tVData.add("1974-08-24");
		// tVData.add("112214");
		// tVData.add("2009-01-01");
		// tVData.add("2009-06-06");
		// tVData.add("1");
		// tVData.add("1");
		// tVData.add("86110020080210025322");
		// tVData.add("2009-03-01");
		// tVData.add("2009-06-06");
		// tVData.add("1");
		// tVData.add("1");姓名，性别，出生日期，证件类型，证件号码，保单号/卡单号
		tVData.add("PQUERY||LCCONT");
		tVData.add("0000017161");
		tVData.add("1");
		tVData.add("1977-5-1");
		tVData.add("1");
		tVData.add("12319770501");
		tVData.add("86728002110200000001");
		// tVData.add("崔照红");
		// tVData.add("G08464686");

		AllQueryBL tAllQueryBL = new AllQueryBL();
		tAllQueryBL.submitData(tVData);
	}
}
