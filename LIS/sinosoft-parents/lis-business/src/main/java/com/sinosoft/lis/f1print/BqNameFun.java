package com.sinosoft.lis.f1print;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Hashtable;

import org.apache.log4j.Logger;

import com.sinosoft.lis.bq.BqCalBL;
import com.sinosoft.lis.bq.CodeNameQuery;
import com.sinosoft.lis.db.LAAgentDB;
import com.sinosoft.lis.db.LABranchGroupDB;
import com.sinosoft.lis.db.LAComDB;
import com.sinosoft.lis.db.LCCSpecDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCInsureAccDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LCPremDB;
import com.sinosoft.lis.db.LCSpecDB;
import com.sinosoft.lis.db.LDCodeDB;
import com.sinosoft.lis.db.LDComDB;
import com.sinosoft.lis.db.LDUserDB;
import com.sinosoft.lis.db.LMEdorCalDB;
import com.sinosoft.lis.db.LOLoanDB;
import com.sinosoft.lis.db.LPBnfDB;
import com.sinosoft.lis.db.LPCSpecDB;
import com.sinosoft.lis.db.LPPremDB;
import com.sinosoft.lis.db.LPSpecDB;
import com.sinosoft.lis.pubfun.AccountManage;
import com.sinosoft.lis.pubfun.Calculator;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.InsuAccBala;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LAAgentSchema;
import com.sinosoft.lis.schema.LABranchGroupSchema;
import com.sinosoft.lis.schema.LAComSchema;
import com.sinosoft.lis.schema.LCCSpecSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCInsureAccSchema;
import com.sinosoft.lis.schema.LCPremSchema;
import com.sinosoft.lis.schema.LCSpecSchema;
import com.sinosoft.lis.schema.LDComSchema;
import com.sinosoft.lis.schema.LOLoanSchema;
import com.sinosoft.lis.schema.LPCSpecSchema;
import com.sinosoft.lis.schema.LPEdorAppSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPPremSchema;
import com.sinosoft.lis.schema.LPSpecSchema;
import com.sinosoft.lis.vschema.LCCSpecSet;
import com.sinosoft.lis.vschema.LCInsureAccSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LCSpecSet;
import com.sinosoft.lis.vschema.LDUserSet;
import com.sinosoft.lis.vschema.LJAGetSet;
import com.sinosoft.lis.vschema.LMEdorCalSet;
import com.sinosoft.lis.vschema.LOLoanSet;
import com.sinosoft.lis.vschema.LPBnfSet;
import com.sinosoft.lis.vschema.LPCSpecSet;
import com.sinosoft.lis.vschema.LPPremSet;
import com.sinosoft.lis.vschema.LPSpecSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;
import com.sinosoft.utility.XmlExportNew;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description:本类封装了打印取数、格式转换、日期操作、数组操作、常用统计等公用函数。所有的函数都采用Static的类型
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author liurx
 * @version 1.0
 */
public class BqNameFun {
private static Logger logger = Logger.getLogger(BqNameFun.class);
	public static final int FILIAL_LEN = 4; // 分公司代码长度
	public static final int CENTER_BRANCH_LEN = 6; // 中心支公司代码长度

	public static final int MAXCOL = 15; // getAllNames()返回的数组长度
	public static final int DISTRICT = 0; // 区下标
	public static final int DEPART = 1; // 部下标
	public static final int TEAM = 2; // 组下标
	public static final int SALE_SERVICE = 3; // 营销服务部下标
	public static final int COM = 4; // 分公司中心支公司下标
	public static final int ADDRESS = 5; // 地址下标
	public static final int ZIPCODE = 6; // 邮编下标
	public static final int PHONE = 7; // 电话下标
	public static final int AGENT_NAME = 8; // 代理人姓名下标
	public static final int FILIAL = 9; // 分公司下标
	public static final int CENTER_BRANCH = 10; // 中支下标
	public static final int SALE_SERVICE_CODE = 11; // 营销服务部代码下标
	public static final int DISTRICT_CODE = 12; // 区代码下标
	public static final int DEPART_CODE = 13; // 部代码下标
	public static final int TEAM_CODE = 14; // 组代码下标

	public static final int ADDRESSINFO = 3; // 地址信息数组长度
	public static final int ADDRESS_AT = 0; // 地址信息数组中，公司地址下标
	public static final int ZIPCODE_AT = 1; // 地址信息数组中，公司邮编下标
	public static final int PHONE_AT = 2; // 地址信息数组中，公司电话下标

	public static final int BANKINFO = 4; // 银代信息数组长度
	public static final int BANKCODE = 0; // 银行代码
	public static final int BANKPOINT = 1; // 银行网点
	public static final int BANKAGENT = 2; // 专管员代码
	public static final int BANKAGENTNAME = 3; // 专管员姓名

	private static final int MONTH_LENGTH[] = { 31, 28, 31, 30, 31, 30, 31, 31,
			30, 31, 30, 31 }; // 非闰年各月份长度
	private static final int LEAP_MONTH_LENGTH[] = { 31, 29, 31, 30, 31, 30,
			31, 31, 30, 31, 30, 31 }; // 闰年各月份长度

	public BqNameFun() {
	}

	/**
	 * 通过AgentCode得到其AgentGroup的级别
	 * 
	 * @param s
	 *            String
	 * @return int 1:组; 2:部; 3:区 ; 0:无法判定
	 */
	public static int getAgentClass(String tAgentCode) {
		// tongmeng 2007-09-13 modify
		// lis6.0使用5.3的销售系统表结构
		int tAgentClass = 0;
		if (tAgentCode == null || tAgentCode.trim().equals("")) {
			return 0;
		}
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		String strsql = "select branchlevel,'' "
				+ " from labranchgroup where agentgroup = "
				+ " (select agentgroup  from laagent " + " where agentcode = '"
				+ "?tAgentCode?" + "')";
		sqlbv1.sql(strsql);
		sqlbv1.put("tAgentCode", tAgentCode);
		ExeSQL q_exesql = new ExeSQL();
		SSRS tssrs = new SSRS();
		tssrs = q_exesql.execSQL(sqlbv1);
		if (tssrs == null || tssrs.getMaxRow() < 1) {
			return 0;
		}
		String tLevel = tssrs.GetText(1, 1);
		// 先用branchlevel判断
		if (tLevel != null && !tLevel.equals("")) {
			int len = tLevel.length();
			if (len == 2) {
				tLevel = tLevel.substring(1, 2);
				if (tLevel.equals("1")) {
					tAgentClass = 1;
				} else if (tLevel.equals("2")) {
					tAgentClass = 2;
				} else if (tLevel.equals("3")) {
					tAgentClass = 3;
				}
			}

		}
		// 如果判断不出来，就用branchseries判断
		// tongmeng 2007-09-13modify
		// 判断不出来就返回组
		if (tAgentClass == 0) {
			/*
			 * String tSeries = tssrs.GetText(1,2); if(tSeries!=null &&
			 * !tSeries.equals("")) { int maxClass = 3; for(int i = 0;i<=tSeries.length()-1;i++) {
			 * if(tSeries.substring(i,1).equals(":")) { maxClass--; } }
			 * if(maxClass<0) { maxClass = 0; } tAgentClass = maxClass; }
			 */
			tAgentClass = 1;

		}
		return tAgentClass;
	}

	/**
	 * 通过AgentCode得到其所属的组的名称，如果该agent属于部、或区一级则返回""
	 * 
	 * @param s
	 *            String
	 * @return String
	 */
	public static String getTeam(String tAgentCode) {
		String teamName = "";
		if (tAgentCode == null || tAgentCode.trim().equals("")) {
			return "";
		}
		int tClass = BqNameFun.getAgentClass(tAgentCode);
		if (tClass == 1) {
			String agentGroup = getAgentGroup(tAgentCode);
			teamName = getAgentGroupName(agentGroup);
		}
		return teamName;
	}

	// 重载楼上的函数（已知级别,agentgroup）
	// 主要是为了getAllNames()函数调用，提高效率
	public static String getTeam(String tAgentCode, int tClass,
			String agentGroup) {
		String teamName = "";
		if (tAgentCode == null || tAgentCode.trim().equals("")) {
			return "";
		}
		if (tClass == 1) {
			teamName = getAgentGroupName(agentGroup);
		}
		return teamName;
	}

	public static String getTeamCode(String tAgentCode, int tClass,
			String agentGroup) {
		String teamCode = "";
		if (tAgentCode == null || tAgentCode.trim().equals("")) {
			return "";
		}
		if (tClass == 1) {
			teamCode = agentGroup;
		}
		return teamCode;
	}

	/**
	 * 通过AgentCode得到其所属的部的名称，如果该agent属于区一级则返回""
	 * 
	 * @param s
	 *            String
	 * @return String
	 */
	public static String getDepart(String tAgentCode) {
		String depName = "";
		if (tAgentCode == null || tAgentCode.trim().equals("")) {
			return "";
		}
		int tClass = BqNameFun.getAgentClass(tAgentCode);
		String agentGroup = "";
		if (tClass == 1) {
			agentGroup = getAgentGroup(tAgentCode);
			String upBranch = getUpbranch(agentGroup);
			depName = getAgentGroupName(upBranch);
		} else if (tClass == 2) {
			agentGroup = getAgentGroup(tAgentCode);
			depName = getAgentGroupName(agentGroup);
		}
		return depName;
	}

	// 重载楼上的函数（已知级别,agentgroup）
	// 主要是为了getAllNames()函数调用，提高效率
	public static String getDepart(String tAgentCode, int tClass,
			String agentGroup) {
		String depName = "";
		if (tAgentCode == null || tAgentCode.trim().equals("")) {
			return "";
		}
		if (tClass == 1) {
			String upBranch = getUpbranch(agentGroup);
			depName = getAgentGroupName(upBranch);
		} else if (tClass == 2) {
			depName = getAgentGroupName(agentGroup);
		}
		return depName;
	}

	public static String getDepartCode(String tAgentCode, int tClass,
			String agentGroup) {
		String depCode = "";
		if (tAgentCode == null || tAgentCode.trim().equals("")) {
			return "";
		}
		if (tClass == 1) {
			depCode = getUpbranch(agentGroup);
		} else if (tClass == 2) {
			depCode = agentGroup;
		}
		return depCode;
	}
	/**
     * ING规定退费精确到分，补费精确到角
     */
    public static double getBJMoney(double aMoney){
        double BJMoney = 0.0;
        if(aMoney < 0){
            BJMoney = Double.parseDouble(getRound(aMoney));
        }else{
            BJMoney = getDropFen(aMoney);
        }
        return BJMoney;
    }
    //小数点后只保留1位，后面的无条件舍弃
    public static double getDropFen(double x){
        return Math.floor(x*10)/10;
    }
    /**
     * 判断保单或险种是否含有投连或万能险
     * 投连返回 1, 万能返回 2
     * XinYQ added on 2008-01-14
     */
    public static int isInsureAccRisk(String sContNo, String sPolNo)
    {
        sContNo = (sContNo != null) ? sContNo.trim() : "";
        sPolNo = (sPolNo != null) ? sPolNo.trim() : "";

        if ((sContNo != null && !sContNo.equals("")) || (sPolNo != null && !sPolNo.equals("")))
        {
        	SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
            String QuerySQL = new String("");
            QuerySQL = "select RiskCode "
                     +   "from LCPol "
                     +  "where 1 = 1 and appflag = '1' ";
            if (sContNo != null && !sContNo.equals(""))
            {
                QuerySQL += "and ContNo = '" + "?sContNo?" + "' ";
            }
            if (sPolNo != null && !sPolNo.equals(""))
            {
                QuerySQL += "and PolNo = '" + "?sPolNo?" + "' ";
            }
            QuerySQL +=   "order by PolNo asc";
            //System.out.println(QuerySQL);
            sqlbv2.sql(QuerySQL);
            sqlbv2.put("sContNo", sContNo);
            sqlbv2.put("sPolNo", sPolNo);

            ExeSQL tExeSQL = new ExeSQL();
            SSRS tSSRS = new SSRS();
            try
            {
                tSSRS = tExeSQL.execSQL(sqlbv2);
            }
            catch (Exception ex)
            {
                return -1;
            }
            tExeSQL = null;

            if (tSSRS != null && tSSRS.getMaxRow() > 0)
            {
                int nIsInsureAccRisk = -1;
                for (int i = 1; i <= tSSRS.getMaxRow(); i++)
                {
                    String sRiskCode = tSSRS.GetText(i, 1);
                    nIsInsureAccRisk = isInsureAccRisk(sRiskCode);
                    if (nIsInsureAccRisk == -1)
                    {
                        return -1;
                    }
                    else if (nIsInsureAccRisk == 1)
                    {
                        return 1;
                    }
                    else if (nIsInsureAccRisk == 2)
                    {
                        return 2;
                    }
                    else
                    {
                        continue;
                    }
                }
                return nIsInsureAccRisk;
            }
            else
            {
                return 0;
            }
        }

        return -1;
    }
    /**
     * 判断险种是否是投连或万能险
     * 投连返回 1, 万能返回 2
     * XinYQ added on 2008-01-14
     */
    public static int isInsureAccRisk(String sRiskCode)
    {
        sRiskCode = (sRiskCode != null) ? sRiskCode.trim() : "";

        if (sRiskCode != null && !sRiskCode.equals(""))
        {
        	SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
            String QuerySQL = new String("");
            QuerySQL = "select RiskType3 "
                     +   "from LMRiskApp "
                     +  "where 1 = 1 "
                     +    "and RiskCode = '" + "?sRiskCode?" + "' "
                     +    "and RiskType3 in ('3', '4')";
            //System.out.println(QuerySQL);
            sqlbv3.sql(QuerySQL);
            sqlbv3.put("sRiskCode", sRiskCode);

            ExeSQL tExeSQL = new ExeSQL();
            String sTempResult = new String("");
            try
            {
                sTempResult = tExeSQL.getOneValue(sqlbv3);
            }
            catch (Exception ex)
            {
                return -1;
            }
            tExeSQL = null;

            if (sTempResult != null && !sTempResult.trim().equals(""))
            {
                if (sTempResult.equals("3"))
                {
                    return 1;
                }
                else if (sTempResult.equals("4"))
                {
                    return 2;
                }
            }
            else
            {
                return 0;
            }
        }

        return -1;
    }
	/**
     * LH added on 2008-01-12
     *  计算保单的生效对应日
     *  @return String
     */
    public static String getCValiMatchDate(String sCvaliDate, String sEdorValiDate)
    {
        int nYear = PubFun.calInterval2(sCvaliDate, sEdorValiDate, "Y");
        SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
        String QuerySQL = "select add_months('"+ "?sCvaliDate?" +"', '" + "?nYear?" + "' * 12) from dual ";
        sqlbv4.sql(QuerySQL);
        sqlbv4.put("sCvaliDate", sCvaliDate);
        sqlbv4.put("nYear", nYear);
        ExeSQL tExeSQL = new ExeSQL();
        String sCValiToDate = new String("");
        try
        {
            sCValiToDate = tExeSQL.getOneValue(sqlbv4);
        }catch (Exception ex)
        {
            return "";
        }
        return sCValiToDate;
    }
	/**
	 * 通过AgentCode得到其所属的区的名称
	 * 
	 * @param s
	 *            String
	 * @return String
	 */
	public static String getDistrict(String tAgentCode) {
		String districtName = "";
		if (tAgentCode == null || tAgentCode.trim().equals("")) {
			return "";
		}
		int tClass = BqNameFun.getAgentClass(tAgentCode);
		String agentGroup = "";
		String upBranch = "";
		if (tClass == 1) {
			agentGroup = getAgentGroup(tAgentCode);
			upBranch = getUpbranch(agentGroup);
			upBranch = getUpbranch(upBranch);
			districtName = getAgentGroupName(upBranch);
		} else if (tClass == 2) {
			agentGroup = getAgentGroup(tAgentCode);
			upBranch = getUpbranch(agentGroup);
			districtName = getAgentGroupName(upBranch);
		} else if (tClass == 3) {
			agentGroup = getAgentGroup(tAgentCode);
			districtName = getAgentGroupName(agentGroup);
		}
		return districtName;
	}

	// 重载楼上的函数（已知级别,agentgroup）
	// 主要是为了getAllNames()函数调用，提高效率
	public static String getDistrict(String tAgentCode, int tClass,
			String agentGroup) {
		String districtName = "";
		if (tAgentCode == null || tAgentCode.trim().equals("")) {
			return "";
		}
		String upBranch = "";
		if (tClass == 1) {
			upBranch = getUpbranch(agentGroup);
			upBranch = getUpbranch(upBranch);
			districtName = getAgentGroupName(upBranch);
		} else if (tClass == 2) {
			upBranch = getUpbranch(agentGroup);
			districtName = getAgentGroupName(upBranch);
		} else if (tClass == 3) {
			districtName = getAgentGroupName(agentGroup);
		}
		return districtName;
	}

	public static String getDistrictCode(String tAgentCode, int tClass,
			String agentGroup) {
		String districtCode = "";
		if (tAgentCode == null || tAgentCode.trim().equals("")) {
			return "";
		}
		String upBranch = "";
		if (tClass == 1) {
			upBranch = getUpbranch(agentGroup);
			districtCode = getUpbranch(upBranch);
		} else if (tClass == 2) {
			districtCode = getUpbranch(agentGroup);
		} else if (tClass == 3) {
			districtCode = agentGroup;
		}
		return districtCode;
	}

	/**
	 * 通过AgentCode得到其agentgroup
	 * 
	 * @param s
	 *            String
	 * @return String
	 */
	public static String getAgentGroup(String tAgentCode) {
		LAAgentDB tLAAgentDB = new LAAgentDB();
		LAAgentSchema tLAAgentSchema = new LAAgentSchema();
		tLAAgentDB.setAgentCode(tAgentCode);
		if (!tLAAgentDB.getInfo()) {
			return "";
		}
		tLAAgentSchema = tLAAgentDB.getSchema();
		// XinYQ modified on 2006-08-01 : 解决组经理对应“部”甚至“区的问题 : OLD : String
		// tAgentGroup = tLAAgentSchema.getAgentGroup();
		String tAgentGroup = tLAAgentSchema.getBranchCode();
		return tAgentGroup;
	}

	/**
	 * 通过AgentCode得到其managecom
	 * 
	 * @param s
	 *            String
	 * @return String
	 */
	public static String getManageCom(String tAgentCode) {
		LAAgentDB tLAAgentDB = new LAAgentDB();
		LAAgentSchema tLAAgentSchema = new LAAgentSchema();
		tLAAgentDB.setAgentCode(tAgentCode);
		if (!tLAAgentDB.getInfo()) {
			return "";
		}
		tLAAgentSchema = tLAAgentDB.getSchema();
		String tManageCom = tLAAgentSchema.getManageCom();
		return tManageCom;
	}

	/**
	 * 通过AgentCode得到其营销服务部
	 * 
	 * @param s
	 *            String
	 * @return String
	 */
	public static String getSaleService(String tAgentCode) {
		String tManageCom = getManageCom(tAgentCode);
		String tSaleService = getSaleServiceM(tManageCom);
		return tSaleService;
	}

	/**
	 * 通过ManageCom得到其营销服务部
	 * 
	 * @param s
	 *            String
	 * @return String
	 */
	public static String getSaleServiceM(String tManageCom) {
		SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
		String strsql = "select name from ldcom  " + " where comcode = '"
				+ "?tManageCom?" + "'";
		sqlbv5.sql(strsql);
		sqlbv5.put("tManageCom", tManageCom);
		ExeSQL q_exesql = new ExeSQL();
		SSRS tssrs = new SSRS();
		tssrs = q_exesql.execSQL(sqlbv5);
		if (tssrs == null || tssrs.getMaxRow() < 1) {
			return "";
		}
		String tSaleService = tssrs.GetText(1, 1);
		return tSaleService;
	}

	/**
	 * 通过ManageCom只得到其分公司,如果是总公司则返回空
	 * 
	 * @param s
	 *            String
	 * @return String
	 */
	public static String getFilialM(String tManageCom) {
		String tFilial = "";
		if (tManageCom != null && !tManageCom.trim().equals("")) {
			tManageCom = tManageCom.trim();
			if (tManageCom.length() >= FILIAL_LEN) {
				tFilial = getComName(tManageCom.substring(0, FILIAL_LEN));
			}
		}
		return tFilial;
	}

	/**
	 * 通过AgentCode只得到其分公司,如果是总公司则返回空
	 * 
	 * @param s
	 *            String
	 * @return String
	 */
	public static String getFilial(String tAgentCode) {
		String tManageCom = getManageCom(tAgentCode);
		String tFilial = getFilialM(tManageCom);
		return tFilial;
	}

	/**
	 * 通过AgentCode得到其分公司代码,如果是总公司则返回总公司代码
	 * 
	 * @param s
	 *            String
	 * @return String
	 */
	public static String getFilialCode(String tAgentCode) {
		String tManageCom = getManageCom(tAgentCode);
		String tFilial = "";
		if (tManageCom != null && !tManageCom.trim().equals("")) {
			tManageCom = tManageCom.trim();
			if (tManageCom.length() >= FILIAL_LEN) {
				tFilial = tManageCom.substring(0, FILIAL_LEN);
			} else {
				tFilial = tManageCom;
			}
		}
		return tFilial;
	}

	/**
	 * 通过AgentCode只得到其中心支公司,如果是总公司或分公司则返回空
	 * 
	 * @param s
	 *            String
	 * @return String
	 */
	public static String getCenterBranch(String tAgentCode) {
		String tManageCom = getManageCom(tAgentCode);
		String tCenterBranch = getCenterBranchM(tManageCom);
		return tCenterBranch;
	}

	/**
	 * 通过ManageCom只得到其中心支公司,如果是总公司或分公司则返回空
	 * 
	 * @param s
	 *            String
	 * @return String
	 */
	public static String getCenterBranchM(String tManageCom) {
		String tCenterBranch = "";
		if (tManageCom != null && !tManageCom.trim().equals("")) {
			tManageCom = tManageCom.trim();
			if (tManageCom.length() >= CENTER_BRANCH_LEN) {
				tCenterBranch = getComName(tManageCom.substring(0,
						CENTER_BRANCH_LEN));
			}
		}
		return tCenterBranch;
	}

	/**
	 * 通过ManageCom得到其中心支公司,如果是总公司或分公司则返回总公司或分公司
	 * 
	 * @param s
	 *            String
	 * @return String
	 */
	public static String getCBranch(String tManageCom) {
		String tCom = "";
		if (tManageCom != null && !tManageCom.trim().equals("")) {
			tManageCom = tManageCom.trim();
			if (tManageCom.length() >= CENTER_BRANCH_LEN) {
				tCom = getComName(tManageCom.substring(0, CENTER_BRANCH_LEN));
			} else if (tManageCom.length() >= FILIAL_LEN) {
				tCom = getComName(tManageCom.substring(0, FILIAL_LEN));
			} else {
				tCom = getComName(tManageCom);
			}
		}
		return tCom;
	}

	/**
	 * 通过AgentCode得到其分公司中心支公司,如果是总公司则返回总公司
	 * 
	 * @param s
	 *            String
	 * @return String
	 */
	public static String getCom(String tAgentCode) {
		String tManageCom = getManageCom(tAgentCode);
		String tCom = getComM(tManageCom);
		return tCom;
	}

	/**
	 * 通过ManageCom得到其分公司中心支公司,如果是总公司则返回总公司
	 * 
	 * @param s
	 *            String
	 * @return String
	 */
	public static String getComM(String tManageCom) {
		String tCom = "";
		if (tManageCom != null && !tManageCom.trim().equals("")) {
			tManageCom = tManageCom.trim();
			if (tManageCom.length() >= CENTER_BRANCH_LEN) {
				tCom = getComName(tManageCom.substring(0, FILIAL_LEN))
						+ getComName(tManageCom.substring(0, CENTER_BRANCH_LEN));
			} else if (tManageCom.length() >= FILIAL_LEN) {
				tCom = getComName(tManageCom.substring(0, FILIAL_LEN));
			} else {
				tCom = getComName(tManageCom);
			}
		}
		return tCom;
	}

	/**
	 * 通过AgentCode得到其公司地址信息，最小到中支
	 * 
	 * @param s
	 *            String
	 * @return String [3]; [0]:公司地址 [1]:公司邮编 [2]:公司电话
	 */
	public static String[] getAddressInfo(String tAgentCode) {
		String tManageCom = getManageCom(tAgentCode);
		String[] addressArr = getAddressInfoM(tManageCom);
		return addressArr;
	}

	/**
	 * 通过ManageCom得到其公司地址信息
	 * 
	 * @param String
	 * @return String [3]; [0]:公司地址 [1]:公司邮编 [2]:公司电话
	 */
	public static String[] getAddressInfoM(String tManageCom) {
		String[] addressArr = new String[ADDRESSINFO];
		if (tManageCom != null && !tManageCom.trim().equals("")) {
			tManageCom = tManageCom.trim();
			if (tManageCom.length() >= CENTER_BRANCH_LEN) {
				addressArr = getAddressArr(tManageCom.substring(0,
						CENTER_BRANCH_LEN));
			} else if (tManageCom.length() >= FILIAL_LEN) {
				addressArr = getAddressArr(tManageCom.substring(0, FILIAL_LEN));
			} else {
				addressArr = getAddressArr(tManageCom);
			}
		}
		addressArr = nullToEmpty(addressArr);
		return addressArr;
	}

	public static String[] getAddressArr(String tCom) {
		String[] addressArr = new String[ADDRESSINFO];
		if (tCom != null && !tCom.trim().equals("")) {
			SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
			String strsql = "select address,zipcode,phone "
					+ " from ldcom where comcode = '" + "?tCom?" + "'";
			sqlbv6.sql(strsql);
			sqlbv6.put("tCom", tCom);
			ExeSQL q_exesql = new ExeSQL();
			SSRS tssrs = new SSRS();
			tssrs = q_exesql.execSQL(sqlbv6);
			if (tssrs != null && tssrs.getMaxRow() >= 1) {
				addressArr[ADDRESS_AT] = tssrs.GetText(1, 1);
				addressArr[ZIPCODE_AT] = tssrs.GetText(1, 2);
				addressArr[PHONE_AT] = tssrs.GetText(1, 3);
			}
		}
		addressArr = nullToEmpty(addressArr);
		return addressArr;
	}

	/**
	 * 通过agentgroup得到其名称
	 * 
	 * @param s
	 *            String
	 * @return String
	 */
	public static String getAgentGroupName(String tAgentGroup) {
		LABranchGroupDB tLABranchGroupDB = new LABranchGroupDB();
		LABranchGroupSchema tLABranchGroupSchema = new LABranchGroupSchema();
		tLABranchGroupDB.setAgentGroup(tAgentGroup);
		if (!tLABranchGroupDB.getInfo()) {
			return "";
		}
		tLABranchGroupSchema = tLABranchGroupDB.getSchema();
		String tAgentGroupName = tLABranchGroupSchema.getName();
		return tAgentGroupName;
	}

	/**
	 * 通过agentgroup得到其上一级agentgroup
	 * 
	 * @param s
	 *            String
	 * @return String
	 */
	public static String getUpbranch(String tAgentGroup) {
		LABranchGroupDB tLABranchGroupDB = new LABranchGroupDB();
		LABranchGroupSchema tLABranchGroupSchema = new LABranchGroupSchema();
		tLABranchGroupDB.setAgentGroup(tAgentGroup);
		if (!tLABranchGroupDB.getInfo()) {
			return "";
		}
		tLABranchGroupSchema = tLABranchGroupDB.getSchema();
		String tUpbranch = tLABranchGroupSchema.getUpBranch();
		return tUpbranch;
	}

	public static String getAgentName(String tAgentCode) {
		LAAgentDB tLAAgentDB = new LAAgentDB();
		LAAgentSchema tLAAgentSchema = new LAAgentSchema();
		tLAAgentDB.setAgentCode(tAgentCode);
		if (!tLAAgentDB.getInfo()) {
			return "";
		}
		tLAAgentSchema = tLAAgentDB.getSchema();
		String tAgentName = tLAAgentSchema.getName();
		return tAgentName;
	}

	public static LAComSchema getAgentComName(String tAgentCom) {
		LAComDB LAComDB = new LAComDB();
		LAComSchema tLAComSchema = new LAComSchema();
		LAComDB.setAgentCom(tAgentCom);
		if (!LAComDB.getInfo()) {
			return null;
		}
		tLAComSchema = LAComDB.getSchema();
		return tLAComSchema;
	}
	
	public static LDComSchema getBranchComInfo(String tComCode) {
		LDComDB LDComDB = new LDComDB();
		LDComSchema tLDComSchema = new LDComSchema();
		LDComDB.setComCode(tComCode);
		if (!LDComDB.getInfo()) {
			return null;
		}
		tLDComSchema = LDComDB.getSchema();
		return tLDComSchema;
	}
	/**
	 * 得到带有年、月、日字样的日期格式
	 * 
	 * @param s
	 *            String
	 * @return String
	 */
	public static String getFDate(String mDate) {
		String mFDate = "";
		if (mDate != null && !mDate.trim().equals("")) {
			FDate fDate = new FDate();
			GregorianCalendar tCalendar = new GregorianCalendar();
			tCalendar.setTime(fDate.getDate(mDate.trim()));
			int tYear = tCalendar.get(Calendar.YEAR);
			int tMonth = tCalendar.get(Calendar.MONTH) + 1;
			int tDay = tCalendar.get(Calendar.DAY_OF_MONTH);
			mFDate = String.valueOf(tYear) + "年" + String.valueOf(tMonth) + "月"
					+ String.valueOf(tDay) + "日";
		}
		return mFDate;
	}

	/**
	 * 通过代码、代码类型到ldcode表中得到其名称
	 * 
	 * @param tCode,tCodeType
	 * @return String
	 */
	public static String getCodeName(String tCode, String tCodeType) {
		if (tCodeType == null || tCodeType.trim().equals("")) {
			return "";
		}
		LDCodeDB tLDCodeDB = new LDCodeDB();

		tLDCodeDB.setCode(tCode);
		tLDCodeDB.setCodeType(tCodeType.trim().toLowerCase());

		if (!tLDCodeDB.getInfo()) {
			return "";
		}
		return tLDCodeDB.getCodeName();
	}

	/**
	 * 得到通过机构代码去ldcom中得到机构名称
	 * 
	 * @param strComCode
	 * @return String
	 */
	public static String getComName(String strComCode) {
		LDComDB tLDComDB = new LDComDB();
		tLDComDB.setComCode(strComCode);
		if (!tLDComDB.getInfo()) {
			return "";
		}
		return tLDComDB.getName();
	}

	/**
	 * 得到通过机构代码去ldcom中得到机构名称
	 * 
	 * @param strComCode
	 * @return String
	 */
	public static String getComAddRess(String strComCode) {
		LDComDB tLDComDB = new LDComDB();
		if(strComCode.length()>=6) //取三级机构地址
		{
			strComCode=strComCode.substring(0, 6);
		}
		tLDComDB.setComCode(strComCode);
		if (!tLDComDB.getInfo()) {
			return "";
		}
		return tLDComDB.getAddress();
	}
	/**
	 * 将null转换为""
	 * 
	 * @param String
	 * @return String
	 */
	public static String nullToEmpty(String tStr) {
		if (tStr == null || tStr == "null") {
			tStr = "";
		}
		return tStr;
	}

	/**
	 * 将字符数组中的null字符转换为""
	 * 
	 * @param String[]
	 * @return String[]
	 */
	public static String[] nullToEmpty(String[] strArr) {
		if (strArr != null) {
			for (int i = 0; i < strArr.length; i++) {
				strArr[i] = nullToEmpty(strArr[i]);
			}
		}
		return strArr;
	}

	/**
	 * 一维String数组tArr和二维double数组tMoneyArr有着相同的长度，
	 * 事实上tMoneyArr[i][]中保存的是tArr[i]所对应的数据； 现按tArr分组统计tMoneyArr，返回分组后的数组；
	 * 本函数主要是为保全清单中按险种统计金额和件数所用，相当于sql中group by RiskCode的功能；
	 * 在已经查出参与分组的数据的情况下，不必再写group by 的sql再查一次数据库，可以直接调用本函数。
	 * 05-12-12扩充：当tMoneyArr为空时，只统计件数
	 * 
	 * @param String[]
	 *            double[][]
	 * @return String[][]
	 */
	public static String[][] getGroupBy(String[] tArr, double[][] tMoneyArr) {
		String[][] rtnArr = null;
		if (tArr == null) {
			return null;
		}
		if (tMoneyArr != null) {
			// 如果tMoneyArr不为空，则必须有跟tArr一样的长度；为空则只统计件数
			if (tArr.length != tMoneyArr.length) {
				return null;
			}
		}
		String[] distinctArr = getDistinctArr(tArr);
		int len = distinctArr.length;
		int wid = 0;
		if (tMoneyArr != null) {
			wid = tMoneyArr[0].length;
		}
		rtnArr = new String[len][wid + 2];// 加2是除了tMoneyArr的列外，还要统计tArr的值和件数
		double[][] valueArr = null;
		if (tMoneyArr != null) {
			valueArr = new double[len][wid]; // 保存tMoneyArr中分组累加的值
		}
		int[] numArr = new int[len];// 保存每组的件数
		// 初始化数组
		for (int k = 0; k < len; k++) {
			numArr[k] = 0;
			// tMoneyArr为空时，wid=0，故此处不用加if(tMoneyArr != null)的条件
			for (int n = 0; n < wid; n++) {
				valueArr[k][n] = 0;
			}
		}
		for (int i = 0; i < len; i++) {
			rtnArr[i][0] = distinctArr[i];// 据以分组的值，如险种代码
			for (int j = 0; j < tArr.length; j++) {
				if (distinctArr[i].equals(tArr[j])) {
					for (int m = 0; m < wid; m++) {
						valueArr[i][m] += tMoneyArr[j][m];
					}
					numArr[i]++;
				}
			}
			for (int h = 0; h < wid; h++) {
				rtnArr[i][h + 1] = BqNameFun.getRound(valueArr[i][h]);// 保留两位小数
			}
			rtnArr[i][wid + 1] = String.valueOf(numArr[i]);
		}
		return rtnArr;
	}

	/**
	 * 按SSRS的comp一列分组，返回每一组在vablue列上值最大的记录
	 * 
	 * @param SSRS,int,int
	 * @return String[][]
	 */
	public static String[][] getRowsMax(SSRS tssrs, int comp, int value) {
		String[][] rtnArr = null;
		if (tssrs == null)
			return null;
		int ROW = tssrs.getMaxRow();
		int COL = tssrs.getMaxCol();
		if (ROW < 1 || comp < 1 || comp > COL || value < 1 || value > COL)
			return null;
		String[][] sameArr = new String[ROW][3];// 第一列：据以分组的列；第二列：比较的值；第三列：峰值的行标
		int rtnRow = 0;
		try {
			for (int i = 1; i <= ROW; i++) {
				int j = rtnRow - 1;
				for (; j >= 0; j--) {
					if ((sameArr[j][0] == null && tssrs.GetText(i, comp) == null)
							|| (sameArr[j][0] != null
									&& tssrs.GetText(i, comp) != null && sameArr[j][0]
									.trim().equals(
											tssrs.GetText(i, comp).trim()))) {
						if (Double.parseDouble(tssrs.GetText(i, value)) > Double
								.parseDouble(sameArr[j][1])) {
							sameArr[j][1] = tssrs.GetText(i, value);
							sameArr[j][2] = String.valueOf(i);
						}
						break;
					}
				}
				if (j < 0) {
					sameArr[rtnRow][0] = tssrs.GetText(i, comp);
					sameArr[rtnRow][1] = tssrs.GetText(i, value);
					sameArr[rtnRow][2] = String.valueOf(i);
					rtnRow++;
				}
			}
			rtnArr = new String[rtnRow][COL];
			for (int k = 0; k < rtnRow; k++) {
				for (int t = 0; t < COL; t++) {
					rtnArr[k][t] = tssrs.GetText(Integer
							.parseInt(sameArr[k][2]), t + 1);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
		return rtnArr;
	}

	/**
	 * 将数组strArr转换为不含重复值的数组 (第一次写此函数时所用的方法改变了strArr里的值，应避免此失误)
	 * 
	 * @param String[]
	 * @return String[]
	 */
	public static String[] getDistinctArr(String[] strArr) {

		if (strArr == null) {
			return null;
		}
		String[] tmpArr = new String[strArr.length];
		int len = 0;
		tmpArr[0] = strArr[0];
		len++;
		for (int i = 1; i < strArr.length; i++) {
			int j = 0;
			for (j = 0; j < len; j++) {
				if (strArr[i] == null || tmpArr[j] == null
						|| strArr[i].trim().equals(tmpArr[j].trim())) {
					break;
				}
			}
			if (j == len) {
				tmpArr[len] = strArr[i];
				len++;
			}
		}
		String[] rtnArr = new String[len];
		for (int k = 0; k < len; k++) {
			rtnArr[k] = tmpArr[k];
		}
		return rtnArr;
	}

	/**
	 * 重载楼上函数,尚未测试
	 * 
	 * @param String[],String[]
	 * @return String[][]
	 */
	public static String[][] getDistinctArr(String[][] strArr) {
		if (strArr == null) {
			return null;
		}
		String[][] tmpArr = new String[strArr.length][strArr[0].length];
		int len = 0;
		for (int p = 0; p < strArr.length; p++) {
			tmpArr[p][0] = strArr[p][0];
		}
		len++;
		for (int i = 1; i < strArr[0].length; i++) {
			for (int j = 0; j < len; j++) {
				for (int t = 0; t < strArr.length; t++) {
					if (strArr[t][i] != null && tmpArr[t][j] != null
							&& !strArr[t][i].trim().equals(tmpArr[t][j].trim())) {
						tmpArr[t][len] = strArr[t][i];
						len++;
					}
				}
			}
		}
		String[][] rtnArr = new String[strArr.length][len];
		for (int k = 0; k < len; k++) {
			for (int h = 0; h < strArr.length; h++) {
				rtnArr[h][k] = tmpArr[h][k];
			}
		}
		return rtnArr;
	}

	/**
	 * 将字符数组中的非空字符取出,组成一个新的数组返回
	 * 
	 * @param String[]
	 * @return String[]
	 */
	public static String[] getNotNullArr(String[] strArr) {
		String[] returnArr = null;
		if (strArr != null) {
			int len = 0;
			for (int i = 0; i < strArr.length; i++) {
				if (strArr[i] != null && !strArr[i].trim().equals("")) {
					len++;
				}
			}
			if (len > 0) {
				returnArr = new String[len];
				int t = 0;
				for (int j = 0; j < strArr.length; j++) {
					if (strArr[j] != null && !strArr[j].trim().equals("")) {
						returnArr[t] = strArr[j];
						t++;
					}
				}
			}
		}
		return returnArr;
	}

	/**
	 * 得到某年某月的月份天数
	 * 
	 * @param int，int
	 * @return int
	 */
	public static int monthLength(int year, int month) {
		if (month > 12 || month < 1)
			return 0;
		return isLeapYear(year) ? LEAP_MONTH_LENGTH[month - 1]
				: MONTH_LENGTH[month - 1];
	}

	/**
	 * 判断是否闰年
	 * 
	 * @param String
	 * @return boolean
	 */
	public static boolean isLeapYear(String mYear) {
		int tYear = Integer.parseInt(mYear);
		boolean returnFlag = (tYear % 4) == 0 ? ((tYear % 100) == 0 ? ((tYear % 400) == 0 ? true
				: false)
				: true)
				: false;

		return returnFlag;
	}

	/**
	 * 判断是否闰年
	 * 
	 * @param int
	 * @return boolean
	 */
	public static boolean isLeapYear(int tYear) {
		boolean returnFlag = (tYear % 4) == 0 ? ((tYear % 100) == 0 ? ((tYear % 400) == 0 ? true
				: false)
				: true)
				: false;

		return returnFlag;
	}

	/**
	 * 返回某一日期的自然月份起止期 主要是清单、报表的统计起止期取默认值时用。现在已不采用以前的财务月份，而是自然月份。2006-05-08
	 * 
	 * @param String
	 * @return String[]
	 */
	public static String[] getNomalMonth(String tDate) {
		String[] dateArr = new String[2];
		FDate fDate = new FDate();
		GregorianCalendar tCalendar = new GregorianCalendar();
		tCalendar.setTime(fDate.getDate(tDate));
		int tYear = tCalendar.get(Calendar.YEAR);
		int tMonth = tCalendar.get(Calendar.MONTH) + 1;
		int tDay = monthLength(tYear, tMonth);
		dateArr[0] = String.valueOf(tYear) + "-" + String.valueOf(tMonth) + "-"
				+ "01";
		dateArr[1] = String.valueOf(tYear) + "-" + String.valueOf(tMonth) + "-"
				+ String.valueOf(tDay);
		dateArr[0] = delTime(dateArr[0]);
		dateArr[1] = delTime(dateArr[1]);
		return dateArr;
	}

	/**
	 * 返回某一天的上一自然月份起止期
	 * 
	 * @param String
	 * @return String[]
	 */
	public static String[] getPreNomalMonth(String tDate) {
		String[] preFinaArr = new String[2];
		String preDate = PubFun.calDate(tDate, -1, "M", null);
		preFinaArr = getNomalMonth(preDate);
		return preFinaArr;
	}

	/**
	 * 返回某一天的上一财务月份 主要是清单、报表的统计起止期取默认值时用
	 * 业务结帐日和财务结帐日的规定：每年1月,1－25日；2－11月，上月26日至本月25日；12月，上月26日至本月31日。
	 * 
	 * @param String
	 * @return String[]
	 */
	public static String[] getPreFinaMonth(String tDate) {
		String[] preFinaArr = new String[2];
		String[] nowArr = getFinaMonth(tDate, tDate);
		if (nowArr[0] != null && !nowArr[0].trim().equals("")) {
			String preDate = PubFun.calDate(nowArr[0], -1, "D", null);
			preFinaArr = getFinaMonth(preDate, preDate);
		}
		return preFinaArr;
	}

	/**
	 * 返回tStartDate与tEndDate这一段时期所处的财务月份,如果要判断某一天，则tStartDate与tEndDate取同一值
	 * 如果tStartDate与tEndDate跨越多个财务月份，则返回空值
	 * 业务结帐日和财务结帐日的规定：每年1月,1－25日；2－11月，上月26日至本月25日；12月，上月26日至本月31日。
	 * 
	 * @param String，String
	 * @return String[]
	 */
	public static String[] getFinaMonth(String tStartDate, String tEndDate) {
		String[] finaArr = new String[2];
		String[][] FINAMONTH = new String[12][2];
		for (int i = 0; i < 12; i++) {
			switch (i) {
			case 0:
				FINAMONTH[0][0] = "01-01";
				FINAMONTH[0][1] = "01-25";
				break;
			case 11:
				FINAMONTH[11][0] = "11-26";
				FINAMONTH[11][1] = "12-31";
				break;
			default:
				FINAMONTH[i][0] = String.valueOf(i) + "-26";
				FINAMONTH[i][1] = String.valueOf(i + 1) + "-25";
				break;
			}
		}
		if (tStartDate != null && !tStartDate.trim().equals("")
				&& tEndDate != null && !tStartDate.trim().equals("")) {
			try {
				FDate fDate = new FDate();
				if (!fDate.getDate(tStartDate).after(fDate.getDate(tEndDate))) {
					GregorianCalendar tCalendar = new GregorianCalendar();
					tCalendar.setTime(fDate.getDate(tStartDate));
					int sYear = tCalendar.get(Calendar.YEAR);
					tCalendar.setTime(fDate.getDate(tEndDate));
					int eYear = tCalendar.get(Calendar.YEAR);
					if (sYear == eYear) {
						String tyear = String.valueOf(sYear);
						for (int j = 0; j < 12; j++) {
							if (!fDate.getDate(tStartDate).before(
									fDate
											.getDate(tyear + "-"
													+ FINAMONTH[j][0]))
									&& !fDate.getDate(tEndDate).after(
											fDate.getDate(tyear + "-"
													+ FINAMONTH[j][1]))) {
								finaArr[0] = tyear + "-" + FINAMONTH[j][0];
								finaArr[1] = tyear + "-" + FINAMONTH[j][1];
								return finaArr;
							}
						}
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				return finaArr;
			}
		}
		return finaArr;
	}

	/**
	 * 将日期时间字符串转换为仅带有日期的字符串 如'2005-08-22 12:12:10'的字符串将被转换为'2005-08-22'
	 * 
	 * @param String
	 * @return String
	 */
	public static String delTime(String tStr) {
		if (tStr != null && !tStr.trim().equals("")) {
			FDate fdate = new FDate();
			tStr = fdate.getString(fdate.getDate(tStr));
		}
		return tStr;
	}

	public static int getYear(String tDate) {
		int tYear = 0;
		if (tDate != null && !tDate.trim().equals("")) {
			FDate fDate = new FDate();
			GregorianCalendar tCalendar = new GregorianCalendar();
			tCalendar.setTime(fDate.getDate(tDate));
			tYear = tCalendar.get(Calendar.YEAR);
		}
		return tYear;
	}

	public static int getMonth(String tDate) {
		int tMonth = 0;
		if (tDate != null && !tDate.trim().equals("")) {
			FDate fDate = new FDate();
			GregorianCalendar tCalendar = new GregorianCalendar();
			tCalendar.setTime(fDate.getDate(tDate));
			tMonth = tCalendar.get(Calendar.MONTH) + 1;
		}
		return tMonth;
	}

	/**
	 * 通过保单号去得到其代理信息
	 * 
	 * @param strComCode
	 * @return String
	 */
	public static String[] getBankInfo(String tContNo) {
		String[] bankArr = new String[BANKINFO];
		if (tContNo != null && !tContNo.trim().equals("")) {
			SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
			String strsql = "select substr(a.agentcom,1,6),(select name from lacom where (agentcom) = (a.agentcom)), "
					+ " agentcode,(select name from laagent where (agentcode) = trim(a.agentcode))  "
					+ " from lccont a where contno = '" + "?tContNo?" + "'";
			sqlbv7.sql(strsql);
			sqlbv7.put("tContNo", tContNo);
			ExeSQL q_exesql = new ExeSQL();
			SSRS tssrs = new SSRS();
			tssrs = q_exesql.execSQL(sqlbv7);
			if (tssrs != null && tssrs.getMaxRow() >= 1) {
				bankArr[BANKCODE] = tssrs.GetText(1, 1);
				bankArr[BANKPOINT] = tssrs.GetText(1, 2);
				bankArr[BANKAGENT] = tssrs.GetText(1, 3);
				bankArr[BANKAGENTNAME] = tssrs.GetText(1, 4);
			}
		}
		bankArr = nullToEmpty(bankArr);
		return bankArr;
	}

	public static int getDay(String tDate) {
		int tDay = 0;
		if (tDate != null && !tDate.trim().equals("")) {
			FDate fDate = new FDate();
			GregorianCalendar tCalendar = new GregorianCalendar();
			tCalendar.setTime(fDate.getDate(tDate));
			tDay = tCalendar.get(Calendar.DAY_OF_MONTH);
		}
		return tDay;
	}

	/**
	 * 通过业务员代码得到其名字、所属部门、公司地址等所有信息
	 * 
	 * @param String
	 * @return String[]
	 */
	public static String[] getAllNames(String tAgentCode) {
		String[] allArr = new String[MAXCOL];
		LAAgentDB tLAAgentDB = new LAAgentDB();
		LAAgentSchema tLAAgentSchema = new LAAgentSchema();
		tLAAgentDB.setAgentCode(tAgentCode);
		if (tLAAgentDB.getInfo()) {
			tLAAgentSchema = tLAAgentDB.getSchema();
			String tAgentGroup = tLAAgentSchema.getAgentGroup();
			String tManageCom = tLAAgentSchema.getManageCom();
			int tClass = BqNameFun.getAgentClass(tAgentCode);

			allArr[DISTRICT] = getDistrict(tAgentCode, tClass, tAgentGroup);
			allArr[DEPART] = getDepart(tAgentCode, tClass, tAgentGroup);
			allArr[TEAM] = getTeam(tAgentCode, tClass, tAgentGroup);
			allArr[SALE_SERVICE] = getSaleServiceM(tManageCom);
			allArr[COM] = getComM(tManageCom);
			String[] addressArr = new String[ADDRESSINFO];
			addressArr = getAddressInfoM(tManageCom);
			allArr[ADDRESS] = addressArr[ADDRESS_AT];
			allArr[ZIPCODE] = addressArr[ZIPCODE_AT];
			allArr[PHONE] = addressArr[PHONE_AT];
			allArr[AGENT_NAME] = tLAAgentSchema.getName();
			allArr[FILIAL] = getFilialM(tManageCom);
			allArr[CENTER_BRANCH] = getCenterBranchM(tManageCom);
			allArr[SALE_SERVICE_CODE] = tManageCom;
			allArr[DISTRICT_CODE] = getDistrictCode(tAgentCode, tClass,
					tAgentGroup);
			allArr[DEPART_CODE] = getDepartCode(tAgentCode, tClass, tAgentGroup);
			allArr[TEAM_CODE] = getTeamCode(tAgentCode, tClass, tAgentGroup);

		}
		allArr = nullToEmpty(allArr);
		return allArr;
	}

	/**
	 * 得到小数点后两位,之所以不返回double类型，是考虑到如果传入的参数是科学计数法的double
	 * 类型，那么如果返回值是double类型，就会还是科学计数法的了，打印页面一般不会显示科学计算法数据
	 * 
	 * @param double
	 * @return String
	 */
	public static String getRound(double tValue) {
//		String t = "0.00";
//		DecimalFormat tDF = new DecimalFormat(t);
//		return tDF.format(tValue);
		//modify by jiaqiangli 2008-10-30 lis65程序四舍五入调用方法
		//修改子程序接口
		return String.valueOf(PubFun.round(tValue,2));
	}

	public static String getRound(String tValue) {
//		String t = "0.00";
//		DecimalFormat tDF = new DecimalFormat(t);
//		String str = "";
//		if (tValue != null && !tValue.trim().equals("")) {
//			try {
//				double value = Double.parseDouble(tValue);
//				str = tDF.format(value);
//			} catch (Exception ex) {
//				ex.printStackTrace();
//				return tValue;
//			}
//		}
//		return str;
		//modify by jiaqiangli 2008-10-30 lis65程序四舍五入调用方法
		//修改子程序接口
		return String.valueOf(PubFun.round(Double.parseDouble(tValue),2));
	}

	/**
	 * 将tStr取小数点后两位，并每隔3位用逗号隔开
	 * 
	 * @param String
	 * @return String
	 */
	public static String getPartRound(String tValue) {
		String str = BqNameFun.getRound(tValue);
		if (str != null && !str.trim().equals("")) {
			str = str.trim();
			int dotIndex = str.indexOf(".");
			if (dotIndex != -1 && dotIndex != 0) {
				String preStr = str.substring(0, dotIndex);
				String afterStr = str.substring(dotIndex);
				double preNum = Double.parseDouble(preStr);
				if (preNum >= 0) {
					str = BqNameFun.getSplit(preStr, ",", 3) + afterStr;
				} else {
					str = "-"
							+ BqNameFun.getSplit(delPreSuffix(preStr, "-"),
									",", 3) + afterStr;
				}
			}
		}
		return str;
	}

	public static String getPartRound(double tValue) {
		String str = BqNameFun.getRound(tValue);
		if (str != null && !str.trim().equals("")) {
			str = str.trim();
			int dotIndex = str.indexOf(".");
			if (dotIndex != -1 && dotIndex != 0) {
				String preStr = str.substring(0, dotIndex);
				String afterStr = str.substring(dotIndex);
				double preNum = Double.parseDouble(preStr);
				if (preNum >= 0) {
					str = BqNameFun.getSplit(preStr, ",", 3) + afterStr;
				} else {
					str = "-"
							+ BqNameFun.getSplit(delPreSuffix(preStr, "-"),
									",", 3) + afterStr;
				}
			}
		}
		return str;
	}

	// pubfun里的getChnMoney有缺陷，暂在此弥补
	public static String getCMoney(double tValue) {
		String str = PubFun.getChnMoney(tValue);
		if (tValue < 1 && tValue > 0) {
			str = "零" + str;
		} else if (tValue < 0 && tValue > -1) {
			str = "负零" + PubFun.getChnMoney(-tValue);
		}
		return str;
	}

	public static String getCMoney(String tStr) {
		try {
			double tValue = Double.parseDouble(tStr);
			return BqNameFun.getCMoney(tValue);
		} catch (Exception ex) {
			ex.printStackTrace();
			return tStr;
		}
	}

	/**
	 * 将tStr从后开始每隔n位用t分隔后返回
	 * 
	 * @param String,String
	 * @return String
	 */
	public static String getSplit(String tStr, String t, int n) {
		if (tStr != null && !tStr.trim().equals("") && t != null
				&& !t.trim().equals("") && n > 0) {
			String reslt = "";
			tStr = tStr.trim();
			t = t.trim();
			int tLen = tStr.length();
			if (tLen > n) {
				int tNum = tLen / n;
				int tRemainder = tLen % n;
				int startIndex = 0;
				int endIndex = 0;
				for (int i = 0; i <= tNum; i++) {
					endIndex = (i == 0) ? tRemainder : n;
					reslt += tStr.substring(startIndex, startIndex + endIndex)
							+ t;
					startIndex += endIndex;
				}
				reslt = BqNameFun.delPreSuffix(reslt, t);
				return reslt;
			}
		}
		return tStr;
	}

	/**
	 * 2003-04-28 Kevin 格式化浮点型数据
	 * 
	 * @param dValue
	 * @return
	 */
	public static String getFormat(double dValue) {
		return new DecimalFormat("0.00").format(dValue);
	}

	public static String getFormat(String dValue) {
		return new DecimalFormat("0.00").format(Double.parseDouble(dValue));
	}
	public static String getRound(double tValue, String t) {
		if (t == null || t.trim().equals("")) {
			t = "0.00";
		}
//		DecimalFormat tDF = new DecimalFormat(t);
//		return tDF.format(tValue);
		//modify by jiaqiangli 2008-10-30 lis65程序四舍五入调用方法
		//修改子程序接口
		int scale = 2;
		if (t.equals("0"))
			scale = 0;
		else 
			scale = t.length() - 2;
		return String.valueOf(PubFun.round(tValue,scale));
	}

	// 去掉字符串tStr头尾的tLast字符
	public static String delPreSuffix(String tStr, String tLast) {
		if (tStr != null && !tStr.trim().equals("") && tLast != null) {
			tStr = tStr.trim();
			tLast = tLast.trim();
			int tLength = tStr.length();
			if (tStr.endsWith(tLast)) {
				tStr = tStr.substring(0, tLength - 1);
				tLength = tStr.length();
			}
			if (tStr.startsWith(tLast)) {
				tStr = tStr.substring(1, tLength);
			}
		}
		return tStr;
	}

	public static String getRound(String tValue, String t) {
		if (t == null || t.trim().equals("")) {
			t = "0.00";
		}
//		DecimalFormat tDF = new DecimalFormat(t);
//		String str = "";
//		if (tValue != null && !tValue.trim().equals("")) {
//			try {
//				double value = Double.parseDouble(tValue);
//				str = tDF.format(value);
//			} catch (Exception ex) {
//				ex.printStackTrace();
//				return tValue;
//			}
//		}
//		return str;
		//modify by jiaqiangli 2008-10-30 lis65程序四舍五入调用方法
		//修改子程序接口
		int scale = 2;
		if (t.equals("0"))
			scale = 0;
		else 
			scale = t.length() - 2;
		return String.valueOf(PubFun.round(Double.parseDouble(tValue),scale));
	}

	public static String getHonor(String tSex) {
		String str = "女士/先生";
		if (tSex != null && !tSex.trim().equals("")) {
			tSex = tSex.trim();
			if (tSex.equals("0")) {
				str = "先生";
			} else if (tSex.equals("1")) {
				str = "女士";
			}
		}
		return str;
	}

	/**
	 * Add By QianLy on 2006-10-17
	 * 
	 * @todo 根据源表名、源字段名和目标表名查到目标字段名
	 * @param String
	 *            sourceTableName : 源表名
	 * @param String
	 *            sourceFieldName : 源字段名
	 * @param String
	 *            destTableName : 目标表名
	 * @return String 目标字段名
	 */
	public static String mapped(String sourceTableName, String sourceFieldName,
			String destTableName) {
		String destFieldName = "";
		SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
		String sql = "select DestFieldName" + "  from LCPolOtherFieldDesc"
				+ " where SourFieldName = '" + "?sourceFieldName?" + "'"
				+ "   and SourTableName = '" + "?sourceTableName?" + "'"
				+ "   and DESTTABLENAME = '" + "?destTableName?" + "'" + "";
		sqlbv8.sql(sql);
		sqlbv8.put("sourceFieldName", sourceFieldName);
		sqlbv8.put("sourceTableName", sourceTableName);
		sqlbv8.put("destTableName", destTableName);
		ExeSQL es = new ExeSQL();
		destFieldName = es.getOneValue(sqlbv8);
		if (destFieldName == null || destFieldName.equals("")) {
			destFieldName = "";
		}
		return destFieldName;
	}

	/**
	 * Add By QianLy on 2006-10-20
	 * 
	 * @todo 为了减少繁琐的转换类型，将类似于 -15232.0 的String类型的金额转换成 15,232.00的打印类型
	 * @param String
	 *            money : 金额
	 * @return String
	 */
	public static String chgMoney(String money) {
		if (money == null || money == "") {
			return "0.00";
		}
		return getPartRound(Math.abs(Double.parseDouble(money)));
	}

	/**
	 * Add By QianLy on 2006-10-20
	 * 
	 * @todo 为了减少繁琐的转换类型，将类似于 -15232.0 的double类型的金额转换成 15,232.00的打印类型
	 *       增加这个方法是为了跟上面处理String类型的达成一致
	 * @param String
	 *            money : 金额
	 * @return String
	 */
	public static String chgMoney(double money) {
		return getPartRound(Math.abs(money));
	}

	/**
	 * Add By QianLy on 2006-11-13
	 * 
	 * @todo 为了减少繁琐的转换类型，将类似于 -15232.0 的String类型的金额转换成 15232.00的打印类型
	 * @param String
	 *            money : 金额
	 * @return String
	 */
	public static String getRoundMoney(String money) {
		if (money == null || money == "") {
			return "0.00";
		}
		return getRound(Math.abs(Double.parseDouble(money)));
	}

	/**
	 * Add By QianLy on 2006-11-13
	 * 
	 * @todo 为了减少繁琐的转换类型，将类似于 -15232.0 的double类型的金额转换成 15232.00的打印类型
	 *       增加这个方法是为了跟上面处理String类型的达成一致
	 * @param String
	 *            money : 金额
	 * @return String
	 */
	public static String getRoundMoney(double money) {
		return getRound(Math.abs(money));
	}

	/**
	 * 判断险种是否是分红险
	 * 
	 * @param aRiskCode
	 * @param aPayToDate
	 * @return 之所以不采用返回布尔值是因为如果查询SQL失败也会返回False，区分不出是查询失败还是不分红
	 */
	public static String getBonusFlag(String sRiskcode) {
		SQLwithBindVariables sqlbv9 = new SQLwithBindVariables();
		// 判断险种是否是分红险
		String sql = " select BonusFlag from lmriskapp "
				+ " where riskcode = '" + "?sRiskcode?" + "'";
		sqlbv9.sql(sql);
		sqlbv9.put("sRiskcode", sRiskcode);
		ExeSQL tExeSQL = new ExeSQL();
		String sBonusFlag = tExeSQL.getOneValue(sqlbv9);
		if (tExeSQL.mErrors.needDealError()) {
			return "Failed";
		}
		if (sBonusFlag == null || sBonusFlag.equals("")
				|| sBonusFlag.equals("0")) {
			// 不是分红险
			sBonusFlag = "N";
		} else {
			sBonusFlag = "Y";
		}
		return sBonusFlag;
	}

	/**
	 * 用于统计小计信息，只要提供一个hashtable，将统计的条目和值传入即可
	 * 
	 * @param hashtable
	 * @param Key
	 * @param Value
	 * @return boolean
	 */
	public static boolean dealHashTable(Hashtable hashtable, String Key,
			String Value) {
		try {
			String oValue = (String) hashtable.get(Key);
			if (oValue == null) {
				hashtable.put(Key, String.valueOf(Value));
			} else {
				hashtable.put(Key, String.valueOf(Double.parseDouble(oValue)
						+ Double.parseDouble(Value)));
			}
		} catch (NumberFormatException e) {
			CError.buildErr("dealHashTable", "数据类型转换失败!");
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			CError.buildErr("dealHashTable", "处理小计信息失败!");
			return false;
		}
		return true;
	}

	// 由“3.00”的字符串得到“3”的字符串
	// 临时解决方法 : (
	public static String formatDoubleToInt(String num) {
		String s = BqNameFun.getRound(num);
		return s.substring(0, s.length() - 3);
	}

	/**
	 * 判断一个团单是否是产品组合 FAILED：产品组合校验失败；TRUE：产品组合；FALSE：非产品组合；
	 * 
	 * @param grpcontno
	 * @return
	 */
	public static String isMulProd(String grpcontno) {
		String srtn = "FAILED";
		SQLwithBindVariables sqlbv10 = new SQLwithBindVariables();
		String sql = "select plantype from lccontplan where grpcontno ='"
				+ "?grpcontno?" + "'";
		sqlbv10.sql(sql);
		sqlbv10.put("grpcontno", grpcontno);
		ExeSQL tExeSQL = new ExeSQL();
		String plantype = tExeSQL.getOneValue(sqlbv10);
		if (tExeSQL.mErrors.needDealError()) {
			srtn = "FAILED";
		} else {
			if (plantype.equals("6")) {
				srtn = "TRUE";
			} else {
				srtn = "FALSE";
			}
		}
		return srtn;
	}

	// 由一个表的一个字段的值来获得另外一个字段的值
	// 注意该方法要求两个字段能唯一对应
	// 参数 sTable 是表名
	// 参数 sField 是源字段名
	// 参数 sValue 是源字段值
	// 参数 dField 是要求的目标字段名
	public static String getAnother(SQLwithBindVariables sqlbv,String sTable, String sField,
			String sValue, String dField) {
		String srtn = "";
		SQLwithBindVariables sqlbv11 = new SQLwithBindVariables();
		String sql = "select " + dField + " from " + sTable + " where "
				+ sField + " ='?sValue?'";
		if(sqlbv!=null){
			sqlbv11.put(sqlbv);
		}
		sqlbv11.sql(sql);
		sqlbv11.put("sValue", sValue);
		ExeSQL tExeSQL = new ExeSQL();
		String another = tExeSQL.getOneValue(sqlbv11);
		if (tExeSQL.mErrors.needDealError()) {
			srtn = "FAILED";
		} else {
			srtn = another;
		}
		return srtn;
	}
	//如果where 含有 rownum 加 limit  --zhangyingfeng  2016-04-06
	public static String getAnother_row(SQLwithBindVariables sqlbv,String sTable, String sField,
			String sValue, String dField , String limit) {
		String srtn = "";
		SQLwithBindVariables sqlbv11 = new SQLwithBindVariables();
		String sql = "select " + dField + " from " + sTable + " where "
				+ sField + " ='?sValue?' " + limit;
		if(sqlbv!=null){
			sqlbv11.put(sqlbv);
		}
		sqlbv11.sql(sql);
		sqlbv11.put("sValue", sValue);
		ExeSQL tExeSQL = new ExeSQL();
		String another = tExeSQL.getOneValue(sqlbv11);
		if (tExeSQL.mErrors.needDealError()) {
			srtn = "FAILED";
		} else {
			srtn = another;
		}
		return srtn;
	}
	
	
    //与上面getAnother方法差不多，返回一个SSRS集合
    //比上面参数多的一个tail用来传递"Group By"、"Order By"子句
    public static SSRS getSome(String sTable,String sField,String sValue,String dField,String tail){
    	SQLwithBindVariables sqlbv12 = new SQLwithBindVariables();
       String sql = "select " + dField + " from " + sTable + " where " + sField + " = '" + sValue + "' " + " tail ";
       sqlbv12.sql(sql);
       //logger.debug("Your SQL: "+sql);
       ExeSQL tExeSQL = new ExeSQL();
       SSRS tss = new SSRS();
       tss = tExeSQL.execSQL(sqlbv12);

       return tss;
    }

	public static boolean fileFilterByName(String[] fileNameArr, String filename) {
		for (int i = 0; i < fileNameArr.length; i++) {
			if (filename.substring(filename.lastIndexOf(".") + 1,
					filename.length()).toUpperCase().equals(
					fileNameArr[i].toUpperCase())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断保全是否变更了新契约的特约内容 XinYQ added on 2004-05-14
	 */
	public static boolean isSpecChanged(String sEdorNo) {
		boolean isChangedResult = false;
		if (sEdorNo == null || sEdorNo.trim().equals("")) {
			logger.debug("\t@> BqNameFun.isEdorSpecChanged() : 无法获取 EdorNo 数据！");
			return false;
		}

		String sContNo = new String("");
		String QuerySQL = new String("");
		SQLwithBindVariables sqlbv13 = new SQLwithBindVariables();
		QuerySQL = "select distinct ContNo from LPEdorItem where EdorNo = '"
				+ "?sEdorNo?" + "'";
		sqlbv13.sql(QuerySQL);
		sqlbv13.put("sEdorNo", sEdorNo);
		ExeSQL tExeSQL = new ExeSQL();
		try {
			sContNo = tExeSQL.getOneValue(sqlbv13);
		} catch (Exception ex) {
			logger.debug("\t@> BqNameFun.isEdorSpecChanged() : 根据 EdorNo 获取 ContNo 异常！");
			return false;
		}
		if (sContNo == null || sContNo.trim().equals("")) {
			logger.debug("\t@> BqNameFun.isEdorSpecChanged() : 根据 EdorNo 获取 ContNo 失败！");
			return false;
		}

		// 查询 LCSpec
		LCSpecDB tLCSpecDB = new LCSpecDB();
		tLCSpecDB.setContNo(sContNo);
		LCSpecSet tLCSpecSet = new LCSpecSet();
		try {
			tLCSpecSet = tLCSpecDB.query();
		} catch (Exception ex) {
			logger.debug("\t@> BqNameFun.isEdorSpecChanged() : 根据 ContNo 获取 LCSpec 异常！");
			return false;
		}
		tLCSpecDB = null;

		// 查询 LPSpec
		LPSpecDB tLPSpecDB = new LPSpecDB();
		tLPSpecDB.setEdorNo(sEdorNo);
		tLPSpecDB.setContNo(sContNo);
		LPSpecSet tLPSpecSet = new LPSpecSet();
		try {
			tLPSpecSet = tLPSpecDB.query();
		} catch (Exception ex) {
			logger.debug("\t@> BqNameFun.isEdorSpecChanged() : 根据 EdorNo 获取 LPSpec 异常！");
			return false;
		}
		tLCSpecDB = null;

		// 进行比较判断
		if ((tLCSpecSet == null || tLCSpecSet.size() <= 0)
				&& (tLPSpecSet == null || tLPSpecSet.size() <= 0)) {
			return false;
		} else if (tLCSpecSet.size() != tLPSpecSet.size()) {
			return true;
		} else if (tLCSpecSet.size() == tLPSpecSet.size()) {
			for (int i = 1; i <= tLCSpecSet.size(); i++) {
				LCSpecSchema tLCSpecSchema = new LCSpecSchema();
				LPSpecSchema tLPSpecSchema = new LPSpecSchema();
				tLCSpecSchema = tLCSpecSet.get(i);
				tLPSpecSchema = tLPSpecSet.get(i);
				if (!tLCSpecSchema.getSpecContent().equals(
						tLPSpecSchema.getSpecContent())) {
					isChangedResult = true;
					break;
				}
				tLCSpecSchema = null;
				tLPSpecSchema = null;
			}
		} else {
			logger.debug("\t@> BqNameFun.isEdorSpecChanged() : LCSpec 与 LPSpec 匹配异常！");
			return false;
		}
		tLCSpecSet = null;
		tLPSpecSet = null;

		return isChangedResult;
	} // function isSpecChanged end

	/**
	 * 获取指定用户的明文密码 XinYQ added on 2004-05-14
	 */
	public static String getUserPassword(String sUserCode) {
		String sResultPassword = null;
		if (sUserCode != null && !sUserCode.trim().equals("")) {
			LDUserDB tLDUserDB = new LDUserDB();
			tLDUserDB.setUserCode(sUserCode);
			LDUserSet tLDUserSet = new LDUserSet();
			try {
				tLDUserSet = tLDUserDB.query();
			} catch (Exception ex) {
				logger.debug("\t@> BqNameFun.getUserPassword() :  查询 LDUser 获取用户信息失败！");
				return null;
			}
//			if (tLDUserSet != null && tLDUserSet.size() > 0) {
//				LDUserSchema tLDUserSchema = new LDUserSchema();
//				tLDUserSchema = tLDUserSet.get(1);
//				sResultPassword = tLDUserSchema.getPassword();
//				if (sResultPassword != null) {
//					LisIDEA tLisIdea = new LisIDEA();
//					sResultPassword = SZAgentUI.decryptString(sResultPassword);
//					tLisIdea = null;
//				}
//				tLDUserSchema = null;
//			}
			tLDUserDB = null;
			tLDUserSet = null;
		}
		return sResultPassword;
	}

	/**
	 * 得到tDate在tYear这一年的对应日
	 * 
	 * @param tYear
	 *            所在年度
	 * @param tDate
	 *            日期
	 * @return String : tDate在tYear这一年的对应日
	 */
	public static String calDate(int tYear, String tDate) {
		String coDate = "";
		if (tDate != null && !tDate.trim().equals("")) {
			FDate fDate = new FDate();
			GregorianCalendar tCalendar = new GregorianCalendar();
			tCalendar.setTime(fDate.getDate(tDate));
			int tMonth = tCalendar.get(Calendar.MONTH) + 1;
			int tDay = tCalendar.get(Calendar.DAY_OF_MONTH);
			// 如果是2月29日
			if (tMonth == 2 && tDay == 29 && !isLeapYear(tYear)) {
				tMonth = 2;
				tDay = 28;
			}
			coDate = String.valueOf(tYear) + "-" + String.valueOf(tMonth) + "-"
					+ String.valueOf(tDay);
		}

		return coDate;
	}

	// 判断一个变量是否是允许值
	// 参数 fileNameArr是一个允许的值的字符串，例如"xp/dC/fk/al"
	// filename是要检测的变量名, 如"dc"
	// seprt 是分隔符 caseFlag 表示是否区分大小写 caseFlag = true 表示区分
	public static boolean isIn(String filename, String fileNameArr,
			String seprt, boolean caseFlag) {
		if (!caseFlag) {
			filename = filename.toLowerCase();
			fileNameArr = fileNameArr.toLowerCase();
			seprt = seprt.toLowerCase();
		}
		String[] x = fileNameArr.trim().split(seprt);
		for (int i = 0; i < x.length; i++) {
			if (filename.equals(x[i])) {
				return true;
			}
		}
		return false;
	}

	/** 获取上次借款的本次和* 
	 *  @param tLoanDate 借款日期
	 *  @param tLoanType 借款类型 "0" --借款 "1"--自垫
	 *  @return double double[0] --借款本金 ，double[1]--借款利息
	 * */

	public static double[] getLastSumLoanMoney(String tContNo,String tLoanDate,String tLoanType) {
		double tSumAccMoney = 0, tIntrest = 0;
        double tSumLastLoan[]= new double[2];
		LOLoanSet tLOLoanSet = new LOLoanSet();
		LOLoanDB tLOLoanDB = new LOLoanDB();
		tLOLoanDB.setContNo(tContNo);
		tLOLoanDB.setPayOffFlag("0");
		tLOLoanDB.setLoanType(tLoanType); // 说明是借款，与自垫区分开来
		tLOLoanSet = tLOLoanDB.query();
		if (tLOLoanSet.size() < 1) // 说明以前没有借款记录，此是第一次借款
		{
//			return null;
		} else {
			for (int i = 1; i <= tLOLoanSet.size(); i++) {
				LOLoanSchema tLOLoanSchema = new LOLoanSchema();
				tLOLoanSchema = tLOLoanSet.get(i);
				tSumAccMoney += tLOLoanSchema.getLeaveMoney();
				// 分段计息,系统级别计息函数
				double tRate = AccountManage.calMultiRateMS(tLOLoanSchema.getLoanDate(),  tLoanDate, "000000","00","L","C","Y",tLOLoanSchema.getCurrency());
				if (tRate+1==0) {
					return null;
				}
				//累计计息
				tIntrest+=tLOLoanSchema.getLeaveMoney()*tRate;
			}
			tSumLastLoan[0]=PubFun.round(tSumAccMoney, 2);
			tSumLastLoan[1]=PubFun.round(tIntrest, 2);			
		}
		return tSumLastLoan;
	}
	
	/** 获取上次借款的本次和* 
	 *  @param tLoanDate 借款日期
	 *  @param tLoanType 借款类型 "0" --借款 "1"--自垫
	 *  @return double double[0] --借款本金 ，double[1]--借款利息
	 * */

	public static double[] getPolLastSumLoanMoney(String tPolNo,String tLoanDate,String tLoanType) {
		double tSumAccMoney = 0, tIntrest = 0;
        double tSumLastLoan[]= new double[2];
		LOLoanSet tLOLoanSet = new LOLoanSet();
		LOLoanDB tLOLoanDB = new LOLoanDB();
		tLOLoanDB.setPolNo(tPolNo);
		tLOLoanDB.setPayOffFlag("0");
		tLOLoanDB.setLoanType(tLoanType); // 说明是借款，与自垫区分开来
		tLOLoanSet = tLOLoanDB.query();
		if (tLOLoanSet.size() < 1) // 说明以前没有借款记录，此是第一次借款
		{
//			return null;
		} else {
			for (int i = 1; i <= tLOLoanSet.size(); i++) {
				LOLoanSchema tLOLoanSchema = new LOLoanSchema();
				tLOLoanSchema = tLOLoanSet.get(i);
				tSumAccMoney += tLOLoanSchema.getLeaveMoney();
				// 分段计息,系统级别计息函数
				double tRate = AccountManage.calMultiRateMS(tLOLoanSchema.getLoanDate(),  tLoanDate, "000000","00","L","C","Y",tLOLoanSchema.getCurrency());
				if (tRate+1==0) {
					return null;
				}
				//累计计息
				tIntrest+=tLOLoanSchema.getLeaveMoney()*tRate;
			}
			tSumLastLoan[0]=PubFun.round(tSumAccMoney, 2);
			tSumLastLoan[1]=PubFun.round(tIntrest, 2);			
		}
		return tSumLastLoan;
	}
	
	
	
	/**
	 * 判断险种是否万能险
	 * pst
	 */

	public static String isAccRisk(String  tRiskCode) {

		ExeSQL tExeSQL=new ExeSQL();
		SQLwithBindVariables sqlbv14 = new SQLwithBindVariables();
		String tRiskFLag="select RiskType3 from lmriskapp where riskcode='"+"?tRiskCode?"+"'";
		sqlbv14.sql(tRiskFLag);
		sqlbv14.put("tRiskCode", tRiskCode);
		tRiskFLag=tExeSQL.getOneValue(sqlbv14);
		if ("4".equals(tRiskFLag)) // 1-帐户计算现金价值
		{
			return "Y";
		} else {
			return "N";
		}
	}
	/**
    根据批单和申请批单显示不同的头
	 */

	public static void AddEdorHead(
			LPEdorItemSchema  mLPEdorItemSchema,LPEdorAppSchema  mLPEdorAppSchema,XmlExport xmlexport) {
		ExeSQL tExeSQL=new ExeSQL();
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(mLPEdorItemSchema.getContNo());
		if (!tLCContDB.getInfo()) {
		}
		LCContSchema tLCContSchema = tLCContDB.getSchema();
		SQLwithBindVariables sqlbv15 = new SQLwithBindVariables();
		String tSQL="select 1 from lwmission where activityid='0000000009'  and missionprop1='"+"?missionprop1?"+"' and  missionprop2='"+"?missionprop2?"+"'";
		sqlbv15.sql(tSQL);
		sqlbv15.put("missionprop1", mLPEdorItemSchema.getEdorAcceptNo());
		sqlbv15.put("missionprop2", mLPEdorAppSchema.getOtherNo());
		String tRiskFLag=tExeSQL.getOneValue(sqlbv15);
		if ("1".equals(tRiskFLag)) // 项目已经进入保全确认节点,显示批单的头
		{
			xmlexport.addDisplayControl("displayHead1");
			TextTag mTextTag = new TextTag();
			mTextTag.add("ApplyName", mLPEdorAppSchema.getEdorAppName());// 申请资格人
			mTextTag.add("AppntName", tLCContSchema.getAppntName());// 投保人姓名
			mTextTag.add("InsuredName", tLCContSchema.getInsuredName());// 被保人姓名
			mTextTag.add("ContNo", tLCContSchema.getContNo()); //
			mTextTag.add("EdorConfNO", mLPEdorAppSchema.getEdorConfNo()); //批单号
			mTextTag.add("EdorAcceptNo", mLPEdorAppSchema.getEdorAcceptNo()); // 保全受理号
			mTextTag.add("EdorAppDate", mLPEdorAppSchema.getEdorAppDate()); // 保全受理日期
			mTextTag.add("EdorConfirmDate", PubFun.getCurrentDate()); // 保全受理确认日期
			mTextTag.add("PrintDate", PubFun.getCurrentDate()); // 打印日期
			xmlexport.addTextTag(mTextTag);			
		}else  //显示申请批单的头
		{
			xmlexport.addDisplayControl("displayHead1");
			TextTag mTextTag = new TextTag();
			mTextTag.add("AppntName", tLCContSchema.getAppntName());// 投保人姓名
			mTextTag.add("InsuredName", tLCContSchema.getInsuredName());// 被保人姓名
			mTextTag.add("ContNo", tLCContSchema.getContNo()); //
			SQLwithBindVariables sqlbv16 = new SQLwithBindVariables();
			sqlbv16.sql("Select edorname from lmedoritem where edorcode = '"
					+ "?edorcode?" + "' and appobj in ('I','B')");
			sqlbv16.put("edorcode", mLPEdorItemSchema.getEdorType());
			String mChgItem = tExeSQL.getOneValue(sqlbv16); // 变更项目
			mTextTag.add("EdorName", mChgItem); // 变更项目
			mTextTag.add("EdorAcceptNo", mLPEdorAppSchema.getEdorAcceptNo()); // 保全受理号
			String strCodeParam = "BarHeight=20&BarRation=2&BarWidth=1&BgColor=FFFFFF&ForeColor=000000&XMargin=10&YMargin=10";
			mTextTag.add("BarCode1", mLPEdorAppSchema.getEdorAcceptNo());
			mTextTag.add("BarCodeParam1", strCodeParam);
			xmlexport.addTextTag(mTextTag);	
		}
	}

	/**根据批单和申请批单显示不同的头
	 * @param mLPEdorItemSchema
	 * @param mLPEdorAppSchema
	 * @param xmlexport xmlexportnew类
	 */
	public static void AddEdorHead(
			LPEdorItemSchema  mLPEdorItemSchema,LPEdorAppSchema  mLPEdorAppSchema,XmlExportNew xmlexport) {
		ExeSQL tExeSQL=new ExeSQL();
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(mLPEdorItemSchema.getContNo());
		if (!tLCContDB.getInfo()) {
		}
		LCContSchema tLCContSchema = tLCContDB.getSchema();
		SQLwithBindVariables sqlbv17 = new SQLwithBindVariables();
		String tSQL="select 1 from lwmission where activityid='0000000009'  and missionprop1='"+"?missionprop1?"+"' and  missionprop2='"+"?missionprop2?"+"'";
		sqlbv17.sql(tSQL);
		sqlbv17.put("missionprop1", mLPEdorItemSchema.getEdorAcceptNo());
		sqlbv17.put("missionprop2", mLPEdorAppSchema.getOtherNo());
		String tRiskFLag=tExeSQL.getOneValue(sqlbv17);
		if ("1".equals(tRiskFLag)) // 项目已经进入保全确认节点,显示批单的头
		{
			xmlexport.addDisplayControl("displayHead1");
			TextTag mTextTag = new TextTag();
			mTextTag.add("ApplyName", mLPEdorAppSchema.getEdorAppName());// 申请资格人
			mTextTag.add("AppntName", tLCContSchema.getAppntName());// 投保人姓名
			mTextTag.add("InsuredName", tLCContSchema.getInsuredName());// 被保人姓名
			mTextTag.add("ContNo", tLCContSchema.getContNo()); //
			mTextTag.add("EdorConfNO", mLPEdorAppSchema.getEdorConfNo()); //批单号
			mTextTag.add("EdorAcceptNo", mLPEdorAppSchema.getEdorAcceptNo()); // 保全受理号
			mTextTag.add("EdorAppDate", mLPEdorAppSchema.getEdorAppDate()); // 保全受理日期
			mTextTag.add("EdorConfirmDate", PubFun.getCurrentDate()); // 保全受理确认日期
			mTextTag.add("PrintDate", PubFun.getCurrentDate()); // 打印日期
			xmlexport.addTextTag(mTextTag);			
		}else  //显示申请批单的头
		{
			xmlexport.addDisplayControl("displayHead1");
			TextTag mTextTag = new TextTag();
			mTextTag.add("AppntName", tLCContSchema.getAppntName());// 投保人姓名
			mTextTag.add("InsuredName", tLCContSchema.getInsuredName());// 被保人姓名
			mTextTag.add("ContNo", tLCContSchema.getContNo()); //
			SQLwithBindVariables sqlbv18 = new SQLwithBindVariables();
			sqlbv18.sql("Select edorname from lmedoritem where edorcode = '"
					+ "?edorcode?" + "' and appobj in ('I','B')");
			sqlbv18.put("edorcode", mLPEdorItemSchema.getEdorType());
			String mChgItem = tExeSQL.getOneValue(sqlbv18); // 变更项目
			mTextTag.add("EdorName", mChgItem); // 变更项目
			mTextTag.add("EdorAcceptNo", mLPEdorAppSchema.getEdorAcceptNo()); // 保全受理号
			String strCodeParam = "BarHeight=20&BarRation=2&BarWidth=1&BgColor=FFFFFF&ForeColor=000000&XMargin=10&YMargin=10";
			mTextTag.add("BarCode1", mLPEdorAppSchema.getEdorAcceptNo());
			mTextTag.add("BarCodeParam1", strCodeParam);
			xmlexport.addTextTag(mTextTag);	
		}
	}
	/**
    根据批单和申请批单显示不同的尾
	 */

	public static void AddEdorValiDatePart(LPEdorItemSchema  mLPEdorItemSchema,LPEdorAppSchema  mLPEdorAppSchema,XmlExport xmlexport) {

		ExeSQL tExeSQL=new ExeSQL();
		String  mOperator = mLPEdorAppSchema.getOperator(); // 经办人
		String mApproveOperator=mLPEdorAppSchema.getApproveOperator();
		SQLwithBindVariables sqlbv19 = new SQLwithBindVariables();
		String tSQL="select 1 from lwmission where activityid='0000000009'  and missionprop1='"+"?missionprop1?"+"' and  missionprop2='"+"?missionprop2?"+"'";
		sqlbv19.sql(tSQL);
		sqlbv19.put("missionprop1", mLPEdorItemSchema.getEdorAcceptNo());
		sqlbv19.put("missionprop2", mLPEdorItemSchema.getContNo());
		String tRiskFLag=tExeSQL.getOneValue(sqlbv19);
		if ("1".equals(tRiskFLag)) // 项目已经进入保全确认节点,显示批单的尾
		{
			xmlexport.addDisplayControl("displayTail1");
			TextTag mTextTag = new TextTag();
			mTextTag.add("EdorValiDate",getEdorValiDate(mLPEdorItemSchema));
			mTextTag.add("Operator", CodeNameQuery.getOperator(mOperator)); // 经办人
			mTextTag.add("ApproveOperator", CodeNameQuery.getOperator(mApproveOperator)); // 审核人
			xmlexport.addTextTag(mTextTag);			
		}else //显示申请批单的尾巴
		{
			xmlexport.addDisplayControl("displayTail1");
			TextTag mTextTag = new TextTag();
			mTextTag.add("Operator", CodeNameQuery.getOperator(mOperator)); // 经办人
			mTextTag.add("PrintDate", PubFun.getCurrentDate()); // 打印日期
			xmlexport.addTextTag(mTextTag);	
		}
	}

	/** 根据批单和申请批单显示不同的尾
	 * @param mLPEdorItemSchema
	 * @param mLPEdorAppSchema
	 * @param xmlexport xmlexportnew类
	 */
	public static void AddEdorValiDatePart(LPEdorItemSchema  mLPEdorItemSchema,LPEdorAppSchema  mLPEdorAppSchema,XmlExportNew xmlexport) {

		ExeSQL tExeSQL=new ExeSQL();
		String  mOperator = mLPEdorAppSchema.getOperator(); // 经办人
		String mApproveOperator=mLPEdorAppSchema.getApproveOperator();
		SQLwithBindVariables sqlbv20 = new SQLwithBindVariables();
		String tSQL="select 1 from lwmission where activityid='0000000009'  and missionprop1='"+"?missionprop1?"+"' and  missionprop2='"+"?missionprop2?"+"'";
		sqlbv20.sql(tSQL);
		sqlbv20.put("missionprop1", mLPEdorItemSchema.getEdorAcceptNo());
		sqlbv20.put("missionprop2", mLPEdorItemSchema.getContNo());
		String tRiskFLag=tExeSQL.getOneValue(sqlbv20);
		if ("1".equals(tRiskFLag)) // 项目已经进入保全确认节点,显示批单的尾
		{
			xmlexport.addDisplayControl("displayTail1");
			TextTag mTextTag = new TextTag();
			mTextTag.add("EdorValiDate",getEdorValiDate(mLPEdorItemSchema));
			mTextTag.add("Operator", CodeNameQuery.getOperator(mOperator)); // 经办人
			mTextTag.add("ApproveOperator", CodeNameQuery.getOperator(mApproveOperator)); // 审核人
			xmlexport.addTextTag(mTextTag);			
		}else //显示申请批单的尾巴
		{
			xmlexport.addDisplayControl("displayTail1");
			TextTag mTextTag = new TextTag();
			mTextTag.add("Operator", CodeNameQuery.getOperator(mOperator)); // 经办人
			mTextTag.add("PrintDate", PubFun.getCurrentDate()); // 打印日期
			xmlexport.addTextTag(mTextTag);	
		}
	}
	/**
     根据申请方式类型选择相应的限制
	 * pst
	 */

	public static void AddEdorHeadInfo(
			LPEdorAppSchema mLPEdorAppSchema,
			TextTag mTextTag)
	{
		ExeSQL tExeSQL=new ExeSQL();
		SQLwithBindVariables sqlbv21 = new SQLwithBindVariables();
		String tEdorSQL="select edortype from lpedoritem where EdorAcceptNo='"+"?EdorAcceptNo?"+"'";
		sqlbv21.sql(tEdorSQL);
		sqlbv21.put("EdorAcceptNo", mLPEdorAppSchema.getEdorAcceptNo());
		String tEdorName=tExeSQL.getOneValue(sqlbv21);

		   //内部申请
           if("6".equals(mLPEdorAppSchema.getAppType()))
           {
        	   mTextTag.add("AppName", "    经本公司审核后,同意将"+mLPEdorAppSchema.getOtherNo());//
           }else if("XT".equals(tEdorName) || "XS".equals(tEdorName) )
           {
      	       mTextTag.add("AppName", "    经与"+mLPEdorAppSchema.getEdorAppName()+"协商,本公司审核同意将"+mLPEdorAppSchema.getOtherNo());//   
           }else
           {
        	   mTextTag.add("AppName", "    兹经"+mLPEdorAppSchema.getEdorAppName()+"申请,并经本公司审核同意将"+mLPEdorAppSchema.getOtherNo());//
           }
           
        

	}
	
	
	/**
    添加通知书的联系联系方式
	 * pst
	 */

	public static void AddBqNoticeAgentInfo(
			LCContSchema mLCContSchema,
			TextTag texttag)
	{
		LAAgentDB tLAAgentDB = new LAAgentDB();
		LAAgentSchema tLAAgentSchema = new LAAgentSchema();
		if(!"".equals(mLCContSchema.getAgentCode())& mLCContSchema.getAgentCode()!=null)
		{
			tLAAgentDB.setAgentCode(mLCContSchema.getAgentCode());
			if (!tLAAgentDB.getInfo()) {
				texttag.add("Operator", ""); // 经办人
				texttag.add("TelNo", ""); // 
				texttag.add("UserCode", ""); // 变更项目
			}else
			{
				tLAAgentSchema = tLAAgentDB.getSchema();	
				texttag.add("Operator",tLAAgentSchema.getName()); // 经办人
				texttag.add("TelNo", NVL(tLAAgentSchema.getMobile(),"")+" "+NVL(tLAAgentSchema.getPhone(),"")); // 
				texttag.add("UserCode", tLAAgentSchema.getAgentCode()); // 变更项目
			}

		}else
		{
			texttag.add("Operator", ""); // 经办人
			texttag.add("TelNo", ""); // 
			texttag.add("UserCode", ""); // 变更项目
		}      
	}
	/**
      根据获取保全项目名称
	 * pst
	 */

	public static String  getEdorName(LPEdorItemSchema mLPEdorItemSchema) {
		ExeSQL tExeSQL=new ExeSQL();
		SQLwithBindVariables sqlbv22 = new SQLwithBindVariables();
		String tEdorSQL="select edorname from lmedoritem where edorcode='"+"?edorcode?"+"' and appobj='I'";
		sqlbv22.sql(tEdorSQL);
		sqlbv22.put("edorcode", mLPEdorItemSchema.getEdorType());
		String tEdorName=tExeSQL.getOneValue(sqlbv22);
		if (!"".equals(tEdorName)) // 1-帐户计算现金价值
		{
			return tEdorName;
		} else {
			return "";
		}
	}
	
	/**
	 * 批单打印添加付费信息
	 * vts 中的标签块描叙约定为
	 * 交付方式 displayXXSub ,XX 为项目
	 * 银行信息 displayXXBank
	 * pst
	 */

//	public static void AddEdorPayGetInfo(LPEdorItemSchema mLPEdorItemSchema,
//			LPEdorAppSchema mLPEdorAppSchema, XmlExport xmlexport,
//			TextTag mTextTag) {
//
//		ExeSQL tExeSQL = new ExeSQL();
//		String tSumGetMoney=BqNameFun.getSumEdorContMoney(mLPEdorItemSchema,mLPEdorItemSchema.getContNo());
//		if ((!"".equals(tSumGetMoney))) {
//			xmlexport.addDisplayControl("display"+mLPEdorItemSchema.getEdorType()+"Sub");
//			mTextTag.add("SumGetMoney", PubFun.getChnMoney(Double
//					.parseDouble(tSumGetMoney))
//					+ "(￥" + tSumGetMoney + ")");//金额
//
//			String strSql = "SELECT distinct getnoticeno"
//					+ " FROM LJSGetEndorse" + " WHERE EndorsementNo='"
//					+ mLPEdorItemSchema.getEdorNo() + "'"
//					+ " and FeeOperationType='"
//					+ mLPEdorItemSchema.getEdorType() + "'";
//			String tGetnoticeNo = tExeSQL.getOneValue(strSql);
//			mTextTag.add("GetNoticeNo", tGetnoticeNo);// 
//			mTextTag.add("GetPayForm", BqNameFun.getCodeName(mLPEdorAppSchema
//					.getPayForm(), "edorgetpayform"));// 
//			// 走的银行转账
//			if ("4".equals(mLPEdorAppSchema.getPayForm())
//					|| "6".equals(mLPEdorAppSchema.getPayForm())) {
//				xmlexport.addDisplayControl("display"+mLPEdorItemSchema.getEdorType()+"Bank");
//				mTextTag
//						.add(
//								"BankName",
//								tExeSQL
//										.getOneValue("select bankname from ldbank where bankcode = '"
//												+ mLPEdorAppSchema.getBankCode()
//												+ "'"));// 银行名称
//				mTextTag.add("BankAccNo", mLPEdorAppSchema.getBankAccNo());// 银行帐号
//				mTextTag.add("BankAccName", mLPEdorAppSchema.getAccName());// 帐户名
//			}
//		}
//
//
//	}
	
	public static void AddEdorGetInfo(LJAGetSet tLJAGetSet ,LPEdorAppSchema tLPEdorAppSchema
			,XmlExport xmlexport,TextTag mTextTag)
	{
		ExeSQL tExeSQL = new ExeSQL();
		if(tLJAGetSet!=null&&tLJAGetSet.size()>0)
		{
			for(int i=1;i<=tLJAGetSet.size();i++)
			{
				xmlexport.addDisplayControl("displayPubSub"+i);
				mTextTag.add("GetNoticeNo"+i, tLJAGetSet.get(i).getActuGetNo());
				mTextTag.add("GetPayForm"+i, BqNameFun.getCodeName(tLJAGetSet.get(i).getPayMode(),"edorgetpayform"));
				mTextTag.add("SumGetMoney"+i, PubFun.round(tLJAGetSet.get(i).getSumGetMoney(),2)+"(大写："+PubFun.getChnMoney(PubFun.round(tLJAGetSet.get(i).getSumGetMoney(),2))+")");
				//9 网银代付 add by jiaqiangli 2009-05-21
				if("4".equals(tLJAGetSet.get(i).getPayMode()) || "9".equals(tLJAGetSet.get(i).getPayMode()))
				{
					xmlexport.addDisplayControl("displayPubBank"+i);
					SQLwithBindVariables sqlbv23 = new SQLwithBindVariables();
					sqlbv23.sql("select bankname from ldbank where bankcode = '"
							+ "?bankcode?"
							+ "'");
					sqlbv23.put("bankcode", tLJAGetSet.get(i).getBankCode());
					mTextTag.add("BankName"+i, tExeSQL.getOneValue(sqlbv23));
					mTextTag.add("BankAccName"+i,  tLJAGetSet.get(i).getAccName());
					mTextTag.add("BankAccNo"+i, tLJAGetSet.get(i).getBankAccNo());
							
				}
				xmlexport.addDisplayControl("displayPubGet"+i);
				mTextTag.add("GetPerson"+i, tLJAGetSet.get(i).getDrawer());
				
				LPBnfDB tLPBnfDB = new LPBnfDB();
				SQLwithBindVariables sqlbv24 = new SQLwithBindVariables();
				String sql = "select * from LPBnf where edorno in ( select edorno from lpedoritem where edoracceptno='"+"?edoracceptno?"+"')"
				+" and edortype in ('CT','LG','XT') "//涉及多收益人分配的保全项目只有生存领取（LG）退保（CT）协议退保（XT）
				+" and name='"+"?name?"+"' and idno='"+"?idno?"+"'"
				;
				sqlbv24.sql(sql);
				sqlbv24.put("edoracceptno", tLPEdorAppSchema.getEdorAcceptNo());
				sqlbv24.put("name", tLJAGetSet.get(i).getDrawer());
				sqlbv24.put("idno", tLJAGetSet.get(i).getDrawerID());
				LPBnfSet tLPBnfSet = tLPBnfDB.executeQuery(sqlbv24);
				if(tLPBnfSet.size()>0)
				{
					mTextTag.add("IdnoType"+i, BqNameFun.getCodeName(tLPBnfSet.get(1).getIDType(),"idtype"));
				}
				else
				{
					mTextTag.add("IdnoType"+i,"身份证");
				}
				mTextTag.add("Idno"+i, tLJAGetSet.get(i).getDrawerID());
			}
		}
	}
	/**
	 * @param tLJAGetSet
	 * @param tLPEdorAppSchema
	 * @param xmlexport xmlexportNew类
	 * @param mTextTag
	 */
	public static void AddEdorGetInfo(LJAGetSet tLJAGetSet ,LPEdorAppSchema tLPEdorAppSchema
			,XmlExportNew xmlexport,TextTag mTextTag)
	{
		ExeSQL tExeSQL = new ExeSQL();
		if(tLJAGetSet!=null&&tLJAGetSet.size()>0)
		{
			for(int i=1;i<=tLJAGetSet.size();i++)
			{
				xmlexport.addDisplayControl("displayPubSub"+i);
				mTextTag.add("GetNoticeNo"+i, tLJAGetSet.get(i).getActuGetNo());
				mTextTag.add("GetPayForm"+i, BqNameFun.getCodeName(tLJAGetSet.get(i).getPayMode(),"edorgetpayform"));
				mTextTag.add("SumGetMoney"+i, PubFun.round(tLJAGetSet.get(i).getSumGetMoney(),2)+"(大写："+PubFun.getChnMoney(PubFun.round(tLJAGetSet.get(i).getSumGetMoney(),2))+")");
				//9 网银代付 add by jiaqiangli 2009-05-21
				if("4".equals(tLJAGetSet.get(i).getPayMode()) || "9".equals(tLJAGetSet.get(i).getPayMode()))
				{
					xmlexport.addDisplayControl("displayPubBank"+i);
					SQLwithBindVariables sqlbv25 = new SQLwithBindVariables();
					sqlbv25.sql("select bankname from ldbank where bankcode = '"
							+ "?bankcode?"
							+ "'");
					sqlbv25.put("bankcode", tLJAGetSet.get(i).getBankCode());
					mTextTag.add("BankName"+i, tExeSQL.getOneValue(sqlbv25));
					mTextTag.add("BankAccName"+i,  tLJAGetSet.get(i).getAccName());
					mTextTag.add("BankAccNo"+i, tLJAGetSet.get(i).getBankAccNo());
							
				}
				xmlexport.addDisplayControl("displayPubGet"+i);
				mTextTag.add("GetPerson"+i, tLJAGetSet.get(i).getDrawer());
				
				LPBnfDB tLPBnfDB = new LPBnfDB();
				SQLwithBindVariables sqlbv26 = new SQLwithBindVariables();
				String sql = "select * from LPBnf where edorno in ( select edorno from lpedoritem where edoracceptno='"+"?edoracceptno?"+"')"
				+" and edortype in ('CT','LG','XT') "//涉及多收益人分配的保全项目只有生存领取（LG）退保（CT）协议退保（XT）
				+" and name='"+"?name?"+"' and idno='"+"?idno?"+"'"
				;
				sqlbv26.sql(sql);
				sqlbv26.put("edoracceptno", tLPEdorAppSchema.getEdorAcceptNo());
				sqlbv26.put("name", tLJAGetSet.get(i).getDrawer());
				sqlbv26.put("idno", tLJAGetSet.get(i).getDrawerID());
				LPBnfSet tLPBnfSet = tLPBnfDB.executeQuery(sqlbv26);
				if(tLPBnfSet.size()>0)
				{
					mTextTag.add("IdnoType"+i, BqNameFun.getCodeName(tLPBnfSet.get(1).getIDType(),"idtype"));
				}
				else
				{
					mTextTag.add("IdnoType"+i,"身份证");
				}
				mTextTag.add("Idno"+i, tLJAGetSet.get(i).getDrawerID());
			}
		}
	}
	/**
	 * 保全生效日规则  无补退费 当天生效  退费 当天生效  收费 次日生效
	 * @param: 无
	 * @return: void
	 */
	public static String getEdorValiDate(LPEdorItemSchema    tLPEdorItemSchema) {

		String sEdorValiDate = ""; // 生效日期
		String sCalCode = ""; // 计算代码
		String sRiskCode = "000000";
		String mVAILIDATE_CALTYPE="ValiDate";
		String sPolNo = tLPEdorItemSchema.getPolNo();
		if (sPolNo.equals("000000")) {
			sRiskCode = "000000";
		} else {
			LCPolDB tLCPolDB = new LCPolDB();
			tLCPolDB.setPolNo(tLPEdorItemSchema.getPolNo());
			if (!tLCPolDB.getInfo()) {
				return "";
			}
			sRiskCode = tLCPolDB.getRiskCode();
		}
		LMEdorCalSet tLMEdorCalSet;
		LMEdorCalDB tLMEdorCalDB = new LMEdorCalDB();
		tLMEdorCalDB.setRiskCode("000000");
		tLMEdorCalDB.setDutyCode("000000");
		tLMEdorCalDB.setEdorType(tLPEdorItemSchema.getEdorType());
		tLMEdorCalDB.setCalType(mVAILIDATE_CALTYPE);
		tLMEdorCalSet = tLMEdorCalDB.query();
		if (tLMEdorCalDB.mErrors.needDealError()) {
			return "";
		}
		if (tLMEdorCalSet != null && tLMEdorCalSet.size() == 1) {
			sCalCode = tLMEdorCalSet.get(1).getCalCode();
		}
		logger.debug("===sCalCode1===" + sCalCode);
		if (sCalCode == null || sCalCode.trim().equals("")) {
			// 如果该险种没有特殊描述保全生效日期计算代码，则取通用计算代码
			tLMEdorCalDB = new LMEdorCalDB();
			tLMEdorCalDB.setRiskCode("000000");
			tLMEdorCalDB.setDutyCode("000000");
			tLMEdorCalDB.setEdorType(tLPEdorItemSchema.getEdorType());
			tLMEdorCalDB.setCalType(mVAILIDATE_CALTYPE);
			tLMEdorCalSet = tLMEdorCalDB.query();
			if (tLMEdorCalDB.mErrors.needDealError()) {
				return "";
			}
			if (tLMEdorCalSet != null && tLMEdorCalSet.size() == 1) {
				sCalCode = tLMEdorCalSet.get(1).getCalCode();
			}
			logger.debug("===sCalCode2===" + sCalCode);
			if (sCalCode == null || sCalCode.trim().equals("")) {
				// 如果该保全项目没有特殊描述保全生效日期计算代码，则取通用计算代码
				tLMEdorCalDB = new LMEdorCalDB();
				tLMEdorCalDB.setRiskCode("000000");
				tLMEdorCalDB.setDutyCode("000000");
				tLMEdorCalDB.setEdorType("00");
				tLMEdorCalDB.setCalType(mVAILIDATE_CALTYPE);
				if (!tLMEdorCalDB.getInfo()) {
					return "";
				}
				sCalCode = tLMEdorCalDB.getCalCode();
				logger.debug("===sCalCode3===" + sCalCode);
			}
		}
		  
		Calculator tCalculator = new Calculator();
		tCalculator.setCalCode(sCalCode);
		// 增加基本要素
		tCalculator.addBasicFactor("EdorAppDate", tLPEdorItemSchema
				.getEdorAppDate());// 申请日期
		tCalculator.addBasicFactor("PolNo", tLPEdorItemSchema.getPolNo()); // 险种号码
		tCalculator.addBasicFactor("ContNo", tLPEdorItemSchema.getContNo()); // 保单号码 
		tCalculator.addBasicFactor("EdorAcceptNo", tLPEdorItemSchema.getEdorAcceptNo());//保全受理号
		// 进行计算
		sEdorValiDate = tCalculator.calculate();
		if (tCalculator.mErrors.needDealError()) {
			return "";
		}
		if (sEdorValiDate == null || sEdorValiDate.equals("")) {
			return "";
		}
		if (sEdorValiDate.length() > 10) {
			sEdorValiDate = sEdorValiDate.substring(0, 10);
		}
		return sEdorValiDate;
	}
	/**获取险种的名称*/
	public  static String getRiskShortName(String tRiskCode)
	{
		String tRiskName="";
		ExeSQL tExeSQL = new ExeSQL();
		SQLwithBindVariables sqlbv27 = new SQLwithBindVariables();
		String tSQL="select riskname from lmriskapp where riskcode='"+"?tRiskCode?"+"'";
		sqlbv27.sql(tSQL);
		sqlbv27.put("tRiskCode", tRiskCode);
		tRiskName=tExeSQL.getOneValue(sqlbv27);
		return tRiskName;
	}
	
	/**获取险种的名称*/
	public  static String getRiskNameByPolNo(String tPolNo)
	{
		String tRiskName="";
		ExeSQL tExeSQL = new ExeSQL();
		SQLwithBindVariables sqlbv28 = new SQLwithBindVariables();
		String tSQL="select (select riskname from lmriskapp where riskcode=a.riskcode) from lcpol a where a.polno='"+"?tPolNo?"+"'";
		sqlbv28.sql(tSQL);
		sqlbv28.put("tPolNo", tPolNo);
		tRiskName=tExeSQL.getOneValue(sqlbv28);
		return tRiskName;
	}
	
	
	/**获取主险险种的名称*/
	public  static String getRiskNameByContNo(String tContNo)
	{
		String tRiskName="";
		ExeSQL tExeSQL = new ExeSQL();
		SQLwithBindVariables sqlbv29 = new SQLwithBindVariables();
		String tSQL="select (select riskname from lmriskapp where riskcode=a.riskcode) from lcpol a where a.polno=a.mainpolno and a.contno='"+"?tContNo?"+"'";
		sqlbv29.sql(tSQL);
		sqlbv29.put("tContNo", tContNo);
		tRiskName=tExeSQL.getOneValue(sqlbv29);
		return tRiskName;
	}
	/**获取主险险种的编码*/
	public  static String getMainRiskCodeByContNo(String tContNo)
	{
		String tRiskCode="";
		ExeSQL tExeSQL = new ExeSQL();
		SQLwithBindVariables sqlbv30 = new SQLwithBindVariables();
		String tSQL="select riskcode from lcpol a where a.polno=a.mainpolno and a.contno='"+"?tContNo?"+"'";
		sqlbv30.sql(tSQL);
		sqlbv30.put("tContNo", tContNo);
		tRiskCode=tExeSQL.getOneValue(sqlbv30);
		return tRiskCode;
	}
	/**获取本次批改的累计补退费，险种层面*/
	public  static String getSumEdorPolMoney(LPEdorItemSchema mLPEdorItemSchema,String tPolNo)
	{
		SQLwithBindVariables sqlbv31 = new SQLwithBindVariables();
	    String strSQL = "SELECT abs(GetMoney),getflag"
	        + " FROM LJSGetEndorse"
	        + " WHERE PolNo='" + "?tPolNo?" + "'"
	        + " and EndorsementNo='" + "?EndorsementNo?" + "'"
	        + " and FeeOperationType='" + "?FeeOperationType?" + "'" ;
	    sqlbv31.sql(strSQL);
	    sqlbv31.put("tPolNo", tPolNo);
	    sqlbv31.put("EndorsementNo", mLPEdorItemSchema.getEdorNo ());
	    sqlbv31.put("FeeOperationType", mLPEdorItemSchema.getEdorType ());
		SSRS tSSRS = new SSRS();
		ExeSQL tExeSQL =new ExeSQL();
		tSSRS = tExeSQL.execSQL(sqlbv31);
		double tSumPayMoney=0,tSumGetMoney=0;
		if (tSSRS != null && tSSRS.MaxRow > 0) {
			for(int k=1;k<=tSSRS.MaxRow ;k++)
			{
				String tFlag=tSSRS.GetText(k, 2);
				String tGetMoney=tSSRS.GetText(k, 1);
				if("1".equals(tFlag))
				{
					tSumGetMoney+=Double.parseDouble(tGetMoney);
				}else
				{
					tSumPayMoney-=Double.parseDouble(tGetMoney);
				}					
			}
			return String.valueOf(Math.abs(PubFun.round(tSumPayMoney+tSumGetMoney, 2)));
			
		}else
		{
			return "";
		}

		
	}
	
	/**获取本次批改的累计补退费，合同层面*/
	public  static String getSumEdorContMoney(LPEdorItemSchema mLPEdorItemSchema,String tContNo)
	{
		SQLwithBindVariables sqlbv32 = new SQLwithBindVariables();
	    String strSQL = "SELECT abs(GetMoney),getflag"
	        + " FROM LJSGetEndorse"
	        + " WHERE Contno='" + "?tContNo?" + "'"
	        + " and EndorsementNo='" + "?EndorsementNo?" + "'"
	        + " and FeeOperationType='" + "?FeeOperationType?" + "'" ;
	    sqlbv32.sql(strSQL);
	    sqlbv32.put("tContNo", tContNo);
	    sqlbv32.put("EndorsementNo", mLPEdorItemSchema.getEdorNo ());
	    sqlbv32.put("FeeOperationType", mLPEdorItemSchema.getEdorType ());
		SSRS tSSRS = new SSRS();
		ExeSQL tExeSQL =new ExeSQL();
		tSSRS = tExeSQL.execSQL(sqlbv32);
		double tSumPayMoney=0,tSumGetMoney=0;
		if (tSSRS != null && tSSRS.MaxRow > 0) {
			for(int k=1;k<=tSSRS.MaxRow ;k++)
			{
				String tFlag=tSSRS.GetText(k, 2);
				String tGetMoney=tSSRS.GetText(k, 1);
				if("1".equals(tFlag))
				{
					tSumGetMoney+=Double.parseDouble(tGetMoney);
				}else
				{
					tSumPayMoney-=Double.parseDouble(tGetMoney);
				}					
			}
			return String.valueOf(Math.abs(PubFun.round(tSumPayMoney+tSumGetMoney, 2)));
		}else
		{
			return "";
		}

		
	}
	
	/**获取本次批改的累计退费，合同层面，只有退费*/
	public  static String getSumGetMoney(LPEdorItemSchema mLPEdorItemSchema,String tContNo)
	{
		SQLwithBindVariables sqlbv33 = new SQLwithBindVariables();
	    String strSQL = "SELECT abs(GetMoney)"
	        + " FROM LJSGetEndorse"
	        + " WHERE Contno='" + "?tContNo?" + "'"
	        + " and EndorsementNo='" + "?EndorsementNo?" + "'"
	        + " and FeeOperationType='" + "?FeeOperationType?" + "' and getflag='1'" ;
	    sqlbv33.sql(strSQL);
	    sqlbv33.put("tContNo", tContNo);
	    sqlbv33.put("EndorsementNo", mLPEdorItemSchema.getEdorNo ());
	    sqlbv33.put("FeeOperationType", mLPEdorItemSchema.getEdorType ());
		SSRS tSSRS = new SSRS();
		ExeSQL tExeSQL =new ExeSQL();
		tSSRS = tExeSQL.execSQL(sqlbv33);
		double tSumGetMoney=0;
		if (tSSRS != null && tSSRS.MaxRow > 0) {
			for(int k=1;k<=tSSRS.MaxRow ;k++)
			{
				String tGetMoney=tSSRS.GetText(k, 1);
			    tSumGetMoney+=Double.parseDouble(tGetMoney);			
			}
			return String.valueOf(Math.abs(PubFun.round(tSumGetMoney, 2)));
			
		}else
		{
			return "";
		}				
	}
	
	
	/**获取本次批改的累计收费，合同层面，只有收费*/
	public  static String getSumPayMoney(LPEdorItemSchema mLPEdorItemSchema,String tContNo)
	{
		SQLwithBindVariables sqlbv34 = new SQLwithBindVariables();
	    String strSQL = "SELECT abs(GetMoney)"
	        + " FROM LJSGetEndorse"
	        + " WHERE Contno='" + "?tContNo?" + "'"
	        + " and EndorsementNo='" + "?EndorsementNo?" + "'"
	        + " and FeeOperationType='" + "?FeeOperationType?" + "' and getflag='0' " ;
	    sqlbv34.sql(strSQL);
	    sqlbv34.put("tContNo", tContNo);
	    sqlbv34.put("EndorsementNo", mLPEdorItemSchema.getEdorNo ());
	    sqlbv34.put("FeeOperationType", mLPEdorItemSchema.getEdorType ());
		SSRS tSSRS = new SSRS();
		ExeSQL tExeSQL =new ExeSQL();
		tSSRS = tExeSQL.execSQL(sqlbv34);
		double tSumPayMoney=0;
		if (tSSRS != null && tSSRS.MaxRow > 0) {
			for(int k=1;k<=tSSRS.MaxRow ;k++)
			{
				String tGetMoney=tSSRS.GetText(k, 1);
				tSumPayMoney-=Double.parseDouble(tGetMoney);			
			}			
			return String.valueOf(Math.abs(PubFun.round(tSumPayMoney, 2)));
		}else
		{
			return "";
		}	
		
	}
	
	
   /**获取保单下期保费
    * 返回数据，元素一为主险交至日期，元素二为下期保费
    * */
   public static String [] getContNextPrem(LPEdorItemSchema mLPEdorItemSchema,String tEdorAppDate)
   {
		double RNewPrem = 0;
		ExeSQL tExeSQL = new ExeSQL();
		String tData[]= new String[2];
		SQLwithBindVariables sqlbv35 = new SQLwithBindVariables();
		String tContSQL = "select polno,paytodate from lcpol where contno='"
				+ "?contno?" + "' and appflag in ('1','2')";
		sqlbv35.sql(tContSQL);
		sqlbv35.put("contno", mLPEdorItemSchema.getContNo());
		SSRS tSSRS = tExeSQL.execSQL(sqlbv35);
		if (tSSRS != null && tSSRS.MaxRow > 0) {
			for (int k = 1; k <= tSSRS.MaxRow; k++) {
				String tPolNo = tSSRS.GetText(k, 1);
				SQLwithBindVariables sqlbv36 = new SQLwithBindVariables();
				String tPSQL = "select prem from lppol a where a.polno='"
						+ "?tPolNo?"
						+ "' and a.edorno='"
						+ "?edorno?"
						+ "' and a.edortype='"
						+ "?edortype?"
						+ "' and  a.appflag in('1','2')" 
//						+ " and ((rnewflag = -1 and payintv = 0) or (rnewflag <> -1 and payintv <> 0) or (rnewflag = -1 and payintv <> 0))"
						+ " and not exists (select 'X'" + "  from lcprem "
						+ " where polno = a.polno "
						+ " and freeflag = 1)" ;
				sqlbv36.sql(tPSQL);
				sqlbv36.put("tPolNo", tPolNo);
				sqlbv36.put("edorno", mLPEdorItemSchema.getEdorNo());
				sqlbv36.put("edortype", mLPEdorItemSchema.getEdorType());
				String tPolPrem=tExeSQL.getOneValue(sqlbv36);
				// 此项目存在P表数据，则从P表中查询,否则查询C表
				if (!"".equals(tPolPrem)) {
					RNewPrem += Double.parseDouble(tPolPrem);
				} else {	
					SQLwithBindVariables sqlbv37 = new SQLwithBindVariables();
					String SSQL = "select prem"
							+ " from lcpol a"
							+ " where "
							+ " a.appflag in ( '1','2')"
//							+ " and ((rnewflag = -1 and payintv = 0) or (rnewflag <> -1 and payintv <> 0) or (rnewflag = -1 and payintv <> 0) )"
							+ " and not exists (select 'X'"
							+ "  from lcprem "
							+ "  where polno = a.polno "
							+ "   and freeflag = 1)"
							+ " and a.polno = '"
							+ "?tPolNo?"
							+ "' "
							+ " and not exists (select 'X'" + "  from lppol "
							+ " where polno = a.polno "
							+ " and   appflag = 4)" ;
					sqlbv37.sql(SSQL);
					sqlbv37.put("tPolNo", tPolNo);
				  	String tCPolPrem=tExeSQL.getOneValue(sqlbv37);
				  	if(!"".equals(tCPolPrem))
				  	{
						RNewPrem += Double.parseDouble(tCPolPrem);				  		
				  	}
					}

		   }
		  tData[0]="2008-08-08";//保单交至日期，但批单已经不需要，为了容错，暂时写死
		  tData[1]=String.valueOf(PubFun.round(RNewPrem, 2));
		}
		return tData;
	}
	
	/** 类似数据库中的选择函数 */
	public  static String NVL(String tInData,String tOutData)
	{
		String tTemp=tOutData;
		if("".equals(tInData)||tInData==null)
		{
			return  tTemp;
		}else
		{
			return  tInData;	
		}
	}
	
	/** 针对特约变更的特殊处理，因为在同一个特约记录集的p表数据中，对与每条记录可能存在增删改操作
	 *  不能简单做CP互换，采用如下逻辑，现在Ｐ表就是未来的C表(类似剪切动作)，现在的C表就是未来的P表，而不是简单一一互换
	 *  */
	public  static MMap DealSpecData(LPEdorItemSchema mLPEdorItemSchema)
	{
		MMap tMMap= new MMap();
		Reflections tRef = new Reflections();
		SQLwithBindVariables sqlbv38 = new SQLwithBindVariables();
		String delLJSSql = " delete from LPCSpec where EdorNo = '"
			+ "?EdorNo?" + "' and edorType = '"
			+ "?edorType?" + "' and contno = '"
			+ "?contno?" + "' ";
		sqlbv38.sql(delLJSSql);
		sqlbv38.put("EdorNo", mLPEdorItemSchema.getEdorNo());
		sqlbv38.put("edorType", mLPEdorItemSchema.getEdorType());
		sqlbv38.put("contno", mLPEdorItemSchema.getContNo());
		tMMap.put(sqlbv38, "DELETE");
		SQLwithBindVariables sqlbv39 = new SQLwithBindVariables();
		String delLJCSql = " delete from LCCSpec where  contno = '"
			+ "?contno?" + "' ";
		sqlbv39.sql(delLJCSql);
		sqlbv39.put("contno", mLPEdorItemSchema.getContNo());
		tMMap.put(sqlbv39, "DELETE");
		
		LPCSpecDB tLPCSpecDB = new LPCSpecDB();
		LPCSpecSet tLPCSpecSet = new LPCSpecSet();
		LCCSpecSet tLCCSpecSet = new LCCSpecSet();
		LPCSpecSet aLPCSpecSet = new LPCSpecSet();
		LCCSpecSet aLCCSpecSet = new LCCSpecSet();
		tLPCSpecDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPCSpecDB.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPCSpecDB.setContNo(mLPEdorItemSchema.getContNo());
		tLPCSpecSet = tLPCSpecDB.query();
		for (int j = 1; j <= tLPCSpecSet.size(); j++) {
			LCCSpecSchema tLCCSpecSchema = new LCCSpecSchema();
			tRef.transFields(tLCCSpecSchema, tLPCSpecSet.get(j).getSchema());
			aLCCSpecSet.add(tLCCSpecSchema);
		}
		tMMap.put(aLCCSpecSet, "DELETE&INSERT");
		LCCSpecDB tLCCSpecDB = new LCCSpecDB();
		tLCCSpecDB.setContNo(mLPEdorItemSchema.getContNo());
		tLCCSpecSet = tLCCSpecDB.query();
		for (int j = 1; j <= tLCCSpecSet.size(); j++) {
			LPCSpecSchema tLPCSpecSchema = new LPCSpecSchema();
			tRef.transFields(tLPCSpecSchema, tLCCSpecSet.get(j).getSchema());
			tLPCSpecSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPCSpecSchema.setEdorType(mLPEdorItemSchema.getEdorType());
			aLPCSpecSet.add(tLPCSpecSchema);
		}
		tMMap.put(aLPCSpecSet, "DELETE&INSERT");
		

		return tMMap;
	}
	
	/**
	 * add by jiaqiangli 2009-05-16
	 * 当然如果新增附加险需要回退的话也需要特殊处理
	 * 核保加费后的prem表的特殊处理方式(从lpprem表查询lcprem时根据polno而不根据主键) 
	 * confirm && backconfirm
	 * @param tLPEdorItemSchema
	 * @return MMap
	 */
	public static MMap DealPrem4BackConfirm(LPEdorItemSchema tLPEdorItemSchema) {
		MMap tMMap = new MMap();
		Reflections tRef = new Reflections();
		
		LPPremDB tLPPremDB = new LPPremDB();
		LPPremSet tLPPremSet = new LPPremSet();
		
		//安全机制 防止全表查询
		if (tLPEdorItemSchema == null) {
			CError.buildErr(tLPEdorItemSchema, "传入保全数据为空!");
			return null;
		}
		if (tLPEdorItemSchema.getEdorNo() == null || "".equals(tLPEdorItemSchema.getEdorNo())
			|| tLPEdorItemSchema.getContNo() == null || "".equals(tLPEdorItemSchema.getContNo())
			|| tLPEdorItemSchema.getEdorType() == null || "".equals(tLPEdorItemSchema.getEdorType())
			) {
			CError.buildErr(tLPEdorItemSchema, "传入保全数据为空!");
			return null;
		}
		tLPPremDB.setEdorNo(tLPEdorItemSchema.getEdorNo());
		tLPPremDB.setContNo(tLPEdorItemSchema.getContNo());
		tLPPremDB.setEdorType(tLPEdorItemSchema.getEdorType());
		
		tLPPremSet = tLPPremDB.query();
		if (tLPPremSet.size() < 1) {
			CError.buildErr(tLPEdorItemSchema, "查询保费项目表失败!");
			return null;
		}
		
		LCPremSet aLCPremSet = new LCPremSet();
		LPPremSet aLPPremSet = new LPPremSet();

		for (int i = 1; i <= tLPPremSet.size(); i++) {
			LCPremSchema aLCPremSchema = new LCPremSchema();
			LPPremSchema aLPPremSchema = new LPPremSchema();
			aLPPremSchema = tLPPremSet.get(i);
			tRef.transFields(aLCPremSchema, aLPPremSchema);
			// 修改时间戳 crm项目可以需要遍历这两个字段
			aLCPremSchema.setModifyDate(PubFun.getCurrentDate());
			aLCPremSchema.setModifyTime(PubFun.getCurrentTime());
			aLCPremSet.add(aLCPremSchema);// 只往C表中插数据
		}
		
		ExeSQL tExeSQL = new ExeSQL();
		SQLwithBindVariables sqlbv40 = new SQLwithBindVariables();
		sqlbv40.sql("select distinct polno from lpprem where edorno='"
				+ "?edorno?" + "' and edortype='" + "?edortype?"
				+ "' and contno='" + "?contno?" + "'");
		sqlbv40.put("edorno", tLPEdorItemSchema.getEdorNo());
		sqlbv40.put("edortype", tLPEdorItemSchema.getEdorType());
		sqlbv40.put("contno", tLPEdorItemSchema.getContNo());
		SSRS tSSRSPolno = tExeSQL.execSQL(sqlbv40);
		LPPremSchema tLPPremSchema = new LPPremSchema();
		
		for (int j = 1; j <= tSSRSPolno.MaxRow; j++) {
			LCPremDB tLCPremDB = new LCPremDB();
			LPPremSet tmpLPPremSet = new LPPremSet();
			tmpLPPremSet.add(tLPPremSchema);
			SQLwithBindVariables sqlbv41 = new SQLwithBindVariables();
			sqlbv41.sql("select * from lcprem where polno='"
					+ "?polno?" + "' and char_length(dutycode)=6 ");
			sqlbv41.put("polno", tSSRSPolno.GetText(j, 1));
			//排除增额缴清的
			LCPremSet tLCPremSet = tLCPremDB.executeQuery(sqlbv41);
			tRef.transFields(tmpLPPremSet, tLCPremSet);
			aLPPremSet.add(tmpLPPremSet);
			SQLwithBindVariables sqlbv42 = new SQLwithBindVariables();
			sqlbv42.sql("delete from lcprem where polno='"+"?polno?"+ "'and char_length(dutycode)=6 ");
			sqlbv42.put("polno", tSSRSPolno.GetText(j, 1));
			//记得删除lcprem的那张核保加费新增的记录 这里统一的先删一次 下面的delete是按主键生成delete语句的
			tMMap.put(sqlbv42, "DELETE");
		}
		
		for (int k=1;k<=aLPPremSet.size();k++){
			aLPPremSet.get(k).setEdorNo(tLPEdorItemSchema.getEdorNo());
			aLPPremSet.get(k).setEdorType(tLPEdorItemSchema.getEdorType());
			aLPPremSet.get(k).setModifyDate(PubFun.getCurrentDate());
			aLPPremSet.get(k).setModifyTime(PubFun.getCurrentTime());
		}
		
		tMMap.put(aLPPremSet,"DELETE&INSERT");
		tMMap.put(aLCPremSet,"DELETE&INSERT");
		return tMMap;
	}
	
	/**获取帐户价值，红利帐户和万能帐户通用,MS的保单不会出现分红和万能在一张合同下*/
	public  static String[][] getInsuaccContMoney(LPEdorItemSchema mLPEdorItemSchema,GlobalInput mGlobalInput)
	{
		String tSumInsuracc="0",tFLag=""; //tFLag=1,万能险帐户；tFLag=2,分红险帐户，
		
	    LCInsureAccDB tLCInsureAccDB = new LCInsureAccDB () ;
	    LCInsureAccSet tLCInsureAccSet = new LCInsureAccSet () ;
	    tLCInsureAccDB.setContNo (mLPEdorItemSchema.getContNo ());
	    tLCInsureAccSet = tLCInsureAccDB.query () ;
	    if (tLCInsureAccSet == null || tLCInsureAccSet.size () == 0)
	    {
	      return null ;
	    }
	    double tSumPayMoney=0;
	    String tData[][] = new String[tLCInsureAccSet.size()][2];
	    for (int i = 1 ; i <= tLCInsureAccSet.size () ; i++)
	    {
	    	LCInsureAccSchema tLCInsureAccSchema=tLCInsureAccSet.get (i) ;
		    InsuAccBala tInsuAccBala = new InsuAccBala();
		    TransferData tTransferData = new TransferData();
		    
		    String tOperate="NonUniversal";
		    //如果是万能险帐户
		    if("000006".equals(tLCInsureAccSchema.getInsuAccNo()))
		    {
		    	tFLag="1";
		    	
		    	tOperate="CashValue";
		    }else
		    {
		    	tFLag="2";
		    	tOperate="NonUniversal";
		    }
			tTransferData.setNameAndValue("InsuAccNo", tLCInsureAccSchema.getInsuAccNo());
			tTransferData.setNameAndValue("PolNo",tLCInsureAccSchema.getPolNo());
			tTransferData.setNameAndValue("BalaDate", mLPEdorItemSchema.getEdorValiDate());
			VData tVData = new VData();
			tVData.add(mGlobalInput);
			tVData.add(tTransferData);
			//非万能险的账户型结算
			if (!tInsuAccBala.submitData(tVData, tOperate)) {
				return null;
			}
			VData tResult = new VData();
			tResult = tInsuAccBala.getResult();
			if("1".equals(tFLag))
			{
				LCInsureAccSchema rLCInsureAccSchema = (LCInsureAccSchema) tResult.getObjectByObjectName("LCInsureAccSchema", 0);
				tSumPayMoney=rLCInsureAccSchema.getInsuAccBala();
				tSumPayMoney=PubFun.round(tSumPayMoney, 2);
				tData[i-1][0]=rLCInsureAccSchema.getInsuAccNo();
			    tData[i-1][1]=String.valueOf(tSumPayMoney);				
			}else
			{
			LCInsureAccSet tempLCInsureAccSet = (LCInsureAccSet) tResult.getObjectByObjectName("LCInsureAccSet", 0);
			tSumPayMoney=tempLCInsureAccSet.get(1).getInsuAccBala();
			tSumPayMoney=PubFun.round(tSumPayMoney, 2);
			tData[i-1][0]=tempLCInsureAccSet.get(1).getInsuAccNo();
		    tData[i-1][1]=String.valueOf(tSumPayMoney);
			}

	    }
	    
       
		return tData;
		
	}
	
	/**获取分红险帐户,生存金的年金累计帐户保全批改补退费金额*/
	public  static String[][] getInsuaccContMoney(LPEdorItemSchema mLPEdorItemSchema)
	{
		
		
	    LCInsureAccDB tLCInsureAccDB = new LCInsureAccDB () ;
	    SQLwithBindVariables sqlbv43 = new SQLwithBindVariables();
    	String strSql = "select * from LCInsureAcc where contno ='" + "?contno?" + "' and insuaccno in  ('000005','000001','000008','000007')";
    	sqlbv43.sql(strSql);
    	sqlbv43.put("contno", mLPEdorItemSchema.getContNo());
    	LCInsureAccSet tLCInsureAccSet = tLCInsureAccDB.executeQuery(sqlbv43);
	    if (tLCInsureAccSet == null || tLCInsureAccSet.size () == 0)
	    {
	      return null ;
	    }
	    double tSumPayMoney=0;
	    String tData[][] = new String[tLCInsureAccSet.size()][2];
	    ExeSQL exeSQL = new ExeSQL () ;
	    int k=0;
	    for (int i = 1 ; i <= tLCInsureAccSet.size () ; i++)
	    {
	    	LCInsureAccSchema tLCInsureAccSchema=tLCInsureAccSet.get (i) ;
	    	
	    	String trace_finType="";
	    	//生存金的年金
	    	if("000005".equals(tLCInsureAccSchema.getInsuAccNo()))
	    	{
		    	trace_finType = BqCalBL.getFinType_HL_SC("SC", tLCInsureAccSchema.getInsuAccNo(), "LQ");
	    	}else  //红利帐户
	    	{
	    		trace_finType= BqCalBL.getFinType_HL_SC("HL", tLCInsureAccSchema.getInsuAccNo(), "LQ");
	    	}
	    	SQLwithBindVariables sqlbv44 = new SQLwithBindVariables();
	        String strSQL = "SELECT abs(Sum(GetMoney))"
	            + " FROM LJSGetEndorse"
	            + " where EndorsementNo='" + "?EndorsementNo?" + "'"
	            + " and  FeeOperationType='" + "?FeeOperationType?" + "'"
	            + " and  FeefinaType like concat(concat('%','" +"?trace_finType?"+"'),'%') " ;
	        sqlbv44.sql(strSQL);
	        sqlbv44.put("EndorsementNo", mLPEdorItemSchema.getEdorNo ());
	        sqlbv44.put("FeeOperationType", mLPEdorItemSchema.getEdorType ());
	        sqlbv44.put("trace_finType", trace_finType);
	        String sumYE = exeSQL.getOneValue (sqlbv44) ;
	        if(!"".equals(sumYE) && !"0".equals(sumYE))
	        {
				tData[k][0]=tLCInsureAccSchema.getInsuAccNo();
			    tData[k][1]=String.valueOf(sumYE);
			    k++;
	        }

	    }
		return tData;
	}
	/*获取中文日期，只支持简单 YYYY-MM-DD格式*/
	public static String getChDate(String tDate)
	{
		FDate tFDate = new FDate();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy" + "年" + "MM" + "月"
				+ "dd" + "日");

		return sdf1.format(tFDate.getDate(tDate));
	}
	
	/**
	 * 如果参数parameter值为空直接返回null反则返回变量字符串paraStr
	 * @param parameter 参数值
	 * @param paraStr 变量字符串
	 * @return
	 */
	public static String getParameterStr(String parameter,String paraStr){
		if ((parameter == null) || parameter.equals("")) {
			return null;
		}else{
			return paraStr;
		}
	}
	
	/**类似页面js中的getWHerePart
	 * 支持传入值与表字段值的多种操作符匹配,如果Where中无其他条件需要添加1=1的条件
	 * @param TableCol1 表字段值
	 * @param Value 要比较的值
	 * @param tOperation 比较符
	 * @author pst
	 * @return String
	 * */
	public static String getWherePart(String TableCol1, String Value,
			String tOperation) {
		String str = "";
		if (!"".equals(tOperation) && tOperation != null) {
			if ("like".equals(tOperation.toLowerCase())) {
				if (!"".equals(Value) && Value != null && TableCol1 != null
						&& !"".equals(TableCol1)) {
					str += (" AND " + TableCol1  + " "+ tOperation + "'" + Value + "%'");
				}

			}else if (!"".equals(Value) && Value != null && TableCol1 != null
					&& !"".equals(TableCol1)) {
				str += (" AND " + TableCol1 + " "+ tOperation + "'" + Value + "'");
			}
		} 
		return str;

	}
	/**传入值与字段值相等
	 * 如果Where中无其他条件需要添加1=1的条件
	 * @param TableCol1 表字段值
	 * @param Value 要比较的值
	 * 类似页面js中的getWHerePart 
	 * pst 
	 */
	public static String getWherePart(String TableCol1, String Value
			) {
		String str = "";
		if (!"".equals(Value) && Value != null && TableCol1 != null
				&& !"".equals(TableCol1)) {
			str += (" AND " + TableCol1 +" ='" + Value + "'");
		}       	  
		  return str;
	}
	/**
	 * 主函数，测试用。对本类每一个函数均用例测试。
	 * 
	 * @param args
	 *            String[]
	 */
	public static void main(String[] args) {
		logger.debug("test start:");
		// String [] agentArr =
		// {"00000011","02273501","02501001","02270039","00000010"};
		// String tAgentCode = "";
		// String tAgentName = "";
		// int tClass = 0;
		// String team = "";
		// String dep = "";
		// String district = "";
		// for(int i = 0;i < agentArr.length;i++)
		// {
		// tAgentCode = agentArr[i];
		// tClass = BqNameFun.getAgentClass(tAgentCode);
		// tAgentName = BqNameFun.getAgentName(tAgentCode);
		// team = BqNameFun.getTeam(tAgentCode);
		// dep = BqNameFun.getDepart(tAgentCode);
		// district = BqNameFun.getDistrict(tAgentCode);
		// logger.debug(tAgentCode+","+tAgentName+"-级别："+tClass+"
		// 区："+district+" 部："+dep+" 组："+team);
		// }
		// String tCB;
		// String filial;
		// String com;
		// String service;
		// String [] address ;
		// for(int h= 0;h< agentArr.length;h++)
		// {
		// tAgentCode = agentArr[h];
		// tCB = BqNameFun.getCenterBranch(tAgentCode);
		// filial = BqNameFun.getFilial(tAgentCode);
		// com = BqNameFun.getCom(tAgentCode);
		// service = BqNameFun.getSaleService(tAgentCode);
		// address = BqNameFun.getAddressInfo(tAgentCode);
		// logger.debug(tAgentCode+"-分公司："+filial+" 中支："+tCB+"
		// 分公司中支："+com+" 营销服务部："+service );
		// logger.debug("address:"+address[0]+" zipcode:"+address[1]+"
		// phone:"+address[2]);
		// }
		// String[] arr;
		// for(int j = 0;j < agentArr.length;j++)
		// {
		// tAgentCode = agentArr[j];
		// logger.debug("information of "+tAgentCode+":");
		// arr = BqNameFun.getAllNames(tAgentCode);
		// for(int t =0;t<arr.length;t++)
		// {
		// logger.debug("row of "+t+" is "+arr[t]);
		// }
		//
		// }
		// String zhongzhi = getCBranch("86");
		// logger.debug("86 is :"+zhongzhi);
		// zhongzhi = getCBranch("8655");
		// logger.debug("8655 is :"+zhongzhi);
		// zhongzhi = getCBranch("86550100");
		// logger.debug("86550100 is :"+zhongzhi);
		// String[] addr;
		// addr = getAddressInfo("022DC027");
		// logger.debug("address:"+addr[0]+" post:"+addr[1]+"
		// phone:"+addr[2]);

		/** ********************对于保单层面，用agentcode得到相关信息****************************** */
		// String tAgentCode = "02200036";
		//
		// String[] allArr = BqNameFun.getAllNames(tAgentCode);
		// String tDistrict = allArr[BqNameFun.DISTRICT]; //区
		// String tDepart = allArr[BqNameFun.DEPART]; //部
		// String tTeam = allArr[BqNameFun.TEAM]; //组
		// String tSaleService = allArr[BqNameFun.SALE_SERVICE]; //营销服务部
		// String tCom = allArr[BqNameFun.COM]; //*分公司*中心支公司
		// String tAdress = allArr[BqNameFun.ADDRESS]; //公司地址
		// String tZipCode = allArr[BqNameFun.ZIPCODE]; //公司邮编
		// String tPhone = allArr[BqNameFun.PHONE]; //公司电话
		// String tAgentName = allArr[BqNameFun.AGENT_NAME]; //代理人姓名
		// String tFilial = allArr[BqNameFun.FILIAL]; //*分公司
		// String tBranch = allArr[BqNameFun.CENTER_BRANCH]; //*中心支公司
		// String tDistrictCode = allArr[BqNameFun.DISTRICT_CODE]; //区
		// String tDepartCode = allArr[BqNameFun.DEPART_CODE]; //部
		// String tTeamCode = allArr[BqNameFun.TEAM_CODE]; //组
		// String tSaleServiceCode = allArr[BqNameFun.SALE_SERVICE_CODE];
		// //营销服务部
		// logger.debug("tDistrict:"+tDistrict);
		// logger.debug("tDepart:"+tDepart);
		// logger.debug("tTeam:"+tTeam);
		// logger.debug("tSaleService:"+tSaleService);
		// logger.debug("tDistrict:"+tDistrictCode);
		// logger.debug("tDepart:"+tDepartCode);
		// logger.debug("tTeam:"+tTeamCode);
		// logger.debug("tSaleService:"+tSaleServiceCode);
		// logger.debug("tCom:"+tCom);
		// logger.debug("tAdress:"+tAdress);
		// logger.debug("tZipCode:"+tZipCode);
		// logger.debug("tPhone:"+tPhone);
		// logger.debug("tAgentName:"+tAgentName);
		// logger.debug("tFilial:"+tFilial);
		// logger.debug("tBranch:"+tBranch);
		/** **********************涉及多张保单时，则用ManageCom********************************************** */
		// String tManageCom = "86320100";
		// String tFilialBranch = BqNameFun.getComM(tManageCom);
		// String[] addressArr = BqNameFun.getAddressInfoM(tManageCom);
		// tAdress = addressArr[BqNameFun.ADDRESS_AT];
		// tZipCode = addressArr[BqNameFun.ZIPCODE_AT];
		// tPhone = addressArr[BqNameFun.PHONE_AT];
		// logger.debug("tFilialBranch:"+tFilialBranch);
		// logger.debug("tAdress:"+tAdress);
		// logger.debug("tZipCode:"+tZipCode);
		// logger.debug("tPhone:"+tPhone);
		/** *********************清单打印中显示中心支公司*********************************************** */
		// String tCenterBranch = BqNameFun.getCBranch(tManageCom);
		// logger.debug("tCenterBranch:"+tCenterBranch);
		/** ***************************************************************************** */
		// String tt = "2005-01-02 12:52:30";
		// logger.debug("tt:"+tt);
		// tt = BqNameFun.delTime(tt);
		// logger.debug("tt:"+tt);
		/** **************************************************************** */
		// double t = 595859.958574435676754567;
		// logger.debug("t:"+t);
		// String ht = BqNameFun.getRound(t);
		// logger.debug("ht:"+ht);
		// t = 9.95857443567E8;
		// logger.debug("t:"+t);
		// ht = BqNameFun.getRound(null);
		// logger.debug("ht:"+ht);
		/** **************************************************************** */
		// String tContNo = "HB050536091003583";
		// String[] bankArr = BqNameFun.getBankInfo(tContNo);
		// logger.debug("银行代码:"+bankArr[BqNameFun.BANKCODE]);
		// logger.debug("银行网点:"+bankArr[BqNameFun.BANKPOINT]);
		// logger.debug("专管员代码:"+bankArr[BqNameFun.BANKAGENT]);
		// logger.debug("专管员姓名:"+bankArr[BqNameFun.BANKAGENTNAME]);
		/** **************************************************************** */
		// String ttt = "0";
		// double hh = 89.876;
		// logger.debug("it is :"+BqNameFun.getRound(hh,ttt));
		// String ui = "5678679.8978";
		// double qq = 8789859.9847;
		// String rlt = BqNameFun.getPartRound(qq);
		// String mCMoney = BqNameFun.getCMoney(-0.984)+"
		// "+BqNameFun.getCMoney("0.984");
		// logger.debug("mCMoney:"+mCMoney);
		/** **************************************************************** */
		// String[] finadate = BqNameFun.getFinaMonth("2005-1-20","2005-1-25");
		// if(finadate[0]==null)
		// {
		// logger.debug("it is null");
		// }else if(finadate[0].trim().equals(""))
		// {
		// logger.debug("empty");
		// }else
		// {
		// logger.debug("start:"+finadate[0]+",end:"+finadate[1]);
		// }
		/** **************************************************************** */
		// String[] arr = new String[15];
		// for(int i = 0;i<8;i++)
		// {
		// arr[i] = "t";
		// }
		// logger.debug("arr:"+arr.length);
		// arr = BqNameFun.getNotNullArr(arr);
		// logger.debug("arr:"+arr.length);
		/** **************************************************************** */
		// String a1 = "876746373683.03938";
		// logger.debug("a1:"+BqNameFun.getPartRound(a1));
		// a1 = "-876746373683.03938";
		// logger.debug("a1:"+BqNameFun.getPartRound(a1));
		// String[] a = {"00601000","00602000","00601000",""};
		// a = getDistinctArr(a);
		// if(a!=null)
		// {
		// for (int i = 0; i < a.length; i++)
		// {
		// logger.debug("a" + i + ":" + a[i]);
		// }
		// }
		// String[] riskArr =
		// {"00601000","00602000","00601000","00601000","00603000"};
		// double[][] moneyArr = {{10,10},{10,10},{0,10},{10,10},{10,10}};
		// String[][] rtnArr = BqNameFun.getGroupBy(riskArr,null);
		// if(rtnArr!=null)
		// {
		// for(int i = 0;i< rtnArr.length;i++)
		// {
		// logger.debug(rtnArr[i][0]+":");
		// for(int j =1;j<rtnArr[0].length;j++)
		// {
		// logger.debug(rtnArr[i][j]+" ");
		// }
		// 
		// }
		// }
		// String[] dateArr =
		// BqNameFun.getPreFinaMonth(PubFun.getCurrentDate());
		// logger.debug("dateArr[0]:"+dateArr[0]);
		// logger.debug("dateArr[1]:"+dateArr[1]);
		// String[] nomaldate = BqNameFun.getNomalMonth("2006-2-20");
		// logger.debug("start:"+nomaldate[0]+",end:"+nomaldate[1]);
		// String[] prenomaldate = BqNameFun.getNomalMonth("2006-2-20");
		// logger.debug("start:"+prenomaldate[0]+",end:"+prenomaldate[1]);
		// String[] nomaldate1 = BqNameFun.getPreNomalMonth("2000-2-20");
		// logger.debug("start:"+nomaldate1[0]+",end:"+nomaldate1[1]);
		/** **************************峰值统计********************************** */
		// SSRS tssrs = new SSRS();
		// ExeSQL texesql = new ExeSQL();
		// String strsql = "select edortype, makedate, count(*) from lpedoritem
		// where makedate between '2006-04-01' and add_months('2006-04-01',1)-1
		// group by edortype, makedate order by edortype";
		// tssrs = texesql.execSQL(strsql);
		// String[][] maxArr = BqNameFun.getRowsMax(tssrs,1,2);
		// for(int i = 0; i < maxArr.length; i++)
		// {
		// for(int j = 0; j < maxArr[i].length; j++)
		// {
		// logger.debug(maxArr[i][j]+" ");
		//
		// }
		// logger.debug("");
		// }
		/** *********************************************************************** */

		// String[] fileFilter = {"GIF","TIF","MD5"};
		// String filename = "a.gif";
		// if(fileFilterByName(fileFilter,filename)){
		// logger.debug("允许的格式");
		// }else{
		// logger.debug("不允许的格式");
		// }
//		String s = "0~Success~迁移验证成功，启动影像迁移线程！";
//		String[] sarr = s.trim().split("~");
//		for (int i = 0; i < sarr.length; i++) {
//			logger.debug(sarr[i]);
//		}
//       
//		String tQ=BqNameFun.calDate(2008, "2008-02-29");
//		
//		logger.debug("test end."+tQ);
		LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
		tLPEdorItemSchema.setEdorNo("86000020090460000175");
		tLPEdorItemSchema.setEdorType("PM");
		tLPEdorItemSchema.setContNo("86110020070210102704");
		BqNameFun.DealPrem4BackConfirm(tLPEdorItemSchema);
	}
}
