package com.sinosoft.lis.pubfun;
import org.apache.log4j.Logger;

/**
 * <p>Title:销售管理系统</p>
 * <p>Description:销售管理的公共业务处理函数
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author
 * @version 1.0
 */
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.sinosoft.lis.db.LDCodeRelaDB;
import com.sinosoft.lis.vschema.LDCodeRelaSet;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;

public class AgentPubFun {
private static Logger logger = Logger.getLogger(AgentPubFun.class);

	public AgentPubFun() {
	}

	/**
	 * 转换业务系统的销售渠道为销售系统的展业类型的函数 author: liujw
	 * 
	 * @param cSalChnl
	 *            业务系统的销售渠道
	 * @return String 销售系统的展业类型(BranchType)
	 */
	public static String switchSalChnltoBranchType(String cSalChnl) {
		String tBranchType = "";

		cSalChnl = cSalChnl.trim();
		if (cSalChnl.equals("01")) { // 团险
			tBranchType = "2";
		} else if (cSalChnl.equals("02")) { // 个险
			tBranchType = "1";
		} else if (cSalChnl.equals("03") || cSalChnl.equals("04")
				|| cSalChnl.equals("05") || cSalChnl.equals("06"))
		// 银行保险 兼业代理 专业代理 经纪公司
		{
			tBranchType = "3";
		}

		return tBranchType;
	}
	public static String switchSalChnltoBranchType(String cSalChnl,String cAgentCode)
   {
     //tongmeng 2007-07-18 add
     //增加中介、中介续期提数
//     String tSQL_branchtype = "select branchtype from laagent where agentcode='"+cAgentCode+"' ";
//     ExeSQL tExeSQL = new ExeSQL();
//     String tBranchType_temp = "";
//     tBranchType_temp = tExeSQL.getOneValue(tSQL_branchtype);
//     if(tBranchType_temp.equals("7")||tBranchType_temp.equals("9"))
//     {
//       return tBranchType_temp;
//     }
     //tongmeng 2006-12-30 modify
     //增加保单渠道
      logger.debug(" switchSalChnltoBranchType Into Into !");
      String tBranchType="";

      cSalChnl = cSalChnl.trim();
      if (cSalChnl.equals("01")) //团险
       {
        tBranchType = "2";
       }
     //新增综拓渠道.09 变为12
       else if(cSalChnl.equals("12"))
    	  
       {tBranchType = "X";
       }	  
      // modify by jiaqiangli 2007-10-29
      else {
        String tSQL_branchtype = "select branchtype from laagent where agentcode='"+"?agentcode?"+"' ";
        SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
        sqlbv1.sql(tSQL_branchtype);
        sqlbv1.put("agentcode", cAgentCode);
        ExeSQL tExeSQL = new ExeSQL();
        tBranchType = tExeSQL.getOneValue(sqlbv1);
        logger.debug("agentcode["+cAgentCode+"]branchtype["+tBranchType+"]");
      }
//      else if (cSalChnl.equals("02")||cSalChnl.equals("10")||cSalChnl.equals("11")) //个险,收展,其他???11待确认
//      {
//        //tongmeng 2006-06-19 add
//        //增加续期督导提数
//        String qSQL="select branchtype from laagent where agentcode ='"
//                   +cAgentCode+"'";
//        ExeSQL aExeSQL = new ExeSQL();
//        if(aExeSQL.getOneValue(qSQL).equals("4"))
//          tBranchType="4";
//        else if(aExeSQL.getOneValue(qSQL).equals("5"))
//          tBranchType="5";
//        //mahw增加于2007-05-22
//        else if(aExeSQL.getOneValue(qSQL).equals("8"))
//          tBranchType="8";
//        else
//          tBranchType="1";
//      }
//      else if (cSalChnl.equals("03")||cSalChnl.equals("04")
//                ||cSalChnl.equals("05")||cSalChnl.equals("06"))
//        else if (cSalChnl.equals("03")||cSalChnl.equals("06")||cSalChnl.equals("08")
//                 ||cSalChnl.equals("09"))
//        //银行保险 兼业代理 专业代理 经纪公司 职团
//      {
//        tBranchType = "3";
//      }
//      //联办代理
//      else if(cSalChnl.equals("07"))
//      {
//        tBranchType = "6";
//      }
//      //modify by jiaqiangli 2007-04-30
//      else if (cSalChnl.equals("05"))
//      {
//        String qSQL="select branchtype from laagent where agentcode ='"+cAgentCode+"'";
//        ExeSQL aExeSQL = new ExeSQL();
//        if(aExeSQL.getOneValue(qSQL).equals("6"))
//          tBranchType="6";
//        else
//          tBranchType="3";
//      }

      return tBranchType;
   }
  public static String getNAAreaType(String cManageCom)
  {
      //AreaType
      String tSql = "Select trim(code2) From LDCodeRela Where RelaType = 'nacomtoareatype' and trim(code1) = '"+"?code?"+"'";
      SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
      sqlbv2.sql(tSql);
      sqlbv2.put("code", cManageCom);
      ExeSQL tExeSQL = new ExeSQL();
      String tAreaType = tExeSQL.getOneValue(sqlbv2);
      if (tExeSQL.mErrors.needDealError())
          return null;
      if (tAreaType == null || tAreaType.equals(""))
          return null;
      return tAreaType;
   }

	// xjh Add 2005/04/01
	public static String[] switchSalChnl(String cSalChnl) {
		String[] result = new String[2];
		LDCodeRelaSet tLDCodeRelaSet = new LDCodeRelaSet();
		LDCodeRelaDB tLDCodeRelaDB = new LDCodeRelaDB();
		tLDCodeRelaDB.setRelaType("salechnlvsbranchtype");
		tLDCodeRelaDB.setCode3(cSalChnl);
		tLDCodeRelaSet = tLDCodeRelaDB.query();
		if (tLDCodeRelaSet.size() == 0)
			return null;
		else {
			result[0] = tLDCodeRelaSet.get(1).getCode1();
			result[1] = tLDCodeRelaSet.get(1).getCode2();
		}
		return result;
	}

	/**
	 * parseTime
	 * 
	 * @param endTime
	 *            String
	 * @param reportType
	 *            String
	 * @param reportCycle
	 *            String
	 * @return String[]
	 */
	public static String[] parseTime(String endTime, int reportType,
			int reportCycle) {
		// 当前时间形式,yyyy-MM-dd
		// String
		// sourcePattern=getSessionPool().getConfig().getDefaultDateFormat();

		// int reportCycle=1;
		String sourcePattern = "yyyy-MM-dd";
		String targetPattern = "yyyy-MM-dd";

		SimpleDateFormat sdf = new SimpleDateFormat(sourcePattern); // 用户定义形式
		SimpleDateFormat targetDF = new SimpleDateFormat(targetPattern); // 数据库要求形式

		String reportStartTime = endTime;
		String reportEndTime = endTime;

		String settedEnd = endTime;

		Date endDate;
		// Date startDate;
		try {
			endDate = sdf.parse(settedEnd);

			// 报表类型
			int type = reportType;
			// 报表周期
			int cycle = reportCycle;

			Calendar cal = Calendar.getInstance();
			cal.setTime(endDate);
			switch (type) {
			case 1: // 日报
				reportEndTime = targetDF.format(endDate);
				cal.add(Calendar.DATE, (-1) * cycle + 1); // 开始时间=结束时间-周期+1
				reportStartTime = targetDF.format(cal.getTime());
				break;
			case 2: // 月报
				cal.set(Calendar.DAY_OF_MONTH, 1); // 月初
				cal.add(Calendar.MONTH, (-1) * cycle + 1); // 到周期的月初
				reportStartTime = targetDF.format(cal.getTime()); // 开始时间
				cal.add(Calendar.MONTH, cycle); // 周期末的下月初
				cal.add(Calendar.DAY_OF_MONTH, -1); // 周期末
				reportEndTime = targetDF.format(cal.getTime());
				break;
			case 3: // 季报

				// int month=cal.get(Calendar.MONTH)+1; //月度,需要+1
				int month = cal.get(Calendar.MONTH);
				int quarter = month / 3; // 所在季度,0-spring;1-summer
				cal.set(Calendar.MONTH, quarter * 3 + ((-1) * cycle + 1) * 3); // 季初-周期持续时间
				cal.set(Calendar.DATE, 1); // 设到1号
				reportStartTime = targetDF.format(cal.getTime());

				// //////////////////end time
				cal.add(Calendar.MONTH, cycle * 3); // 多了一天
				cal.add(Calendar.DATE, -1);
				reportEndTime = targetDF.format(cal.getTime());
				break;
			case 4: // 年报
				cal.add(Calendar.YEAR, (-1) * cycle + 1);
				cal.set(Calendar.MONTH, Calendar.JANUARY);
				cal.set(Calendar.DATE, 1); // 设到年初
				reportStartTime = targetDF.format(cal.getTime());

				// ///////
				cal.add(Calendar.YEAR, cycle); // 下一年元旦,多了一天
				cal.add(Calendar.DATE, -1); // 年末
				reportEndTime = targetDF.format(cal.getTime());
				break;
			default:
				break;
			}
		} catch (ParseException ex) {

		}

		String[] result = new String[2];
		result[0] = reportStartTime;
		result[1] = reportEndTime;
		return result;
	}

	/**
	 * 将日期格式化成“YYYY年MM月DD天”的格式，输入格式为“YYYY/MM/DD”，“YYYY-MM-DD”
	 */
	public static String formatDatex(String cDate) {
		if (cDate.indexOf("/") != -1) {
			cDate = cDate.substring(0, cDate.indexOf("/"))
					+ "年"
					+ cDate.substring(cDate.indexOf("/") + 1, cDate
							.lastIndexOf("/")) + "月"
					+ cDate.substring(cDate.lastIndexOf("/") + 1) + "日";
		} else if (cDate.indexOf("-") != -1) {
			cDate = cDate.substring(0, cDate.indexOf("-"))
					+ "年"
					+ cDate.substring(cDate.indexOf("-") + 1, cDate
							.lastIndexOf("-")) + "月"
					+ cDate.substring(cDate.lastIndexOf("-") + 1) + "日";
		}
		return cDate;
	}
	 /**
	   * 转换业务系统的销售渠道为销售系统的展业类型的函数 author: liujw
	   * @param cSalChnl 销售系统的展业类型(BranchType)
	   * @return String  业务系统的销售渠道
	   */
	   public static String switchBranchTypetoSalChnl(String cBranchType)
	   {
	      String tSalChnl="";

	      cBranchType = cBranchType.trim();
	      if (cBranchType.equals("2")) //团险
	      {
	        tSalChnl = "01";
	      //tongmeng 2006-06-19 modify
	      //增加续期督导提数
	      //tongmeng 2006-12-30 modify
	      //增加保单渠道
	      //mahw增加于2007-05-22
	      }else if (cBranchType.equals("1")||cBranchType.equals("4")||cBranchType.equals("5")||cBranchType.equals("8")||cBranchType.equals("99")) //个险
	      {
	        tSalChnl = "02,10,11";
	      }else if (cBranchType.equals("3"))
	        //银行保险 兼业代理 专业代理 经纪公司
	      {
	        tSalChnl = "03,05,06,08,09";
	      }
	      //联办代理
	      else if(cBranchType.equals("6"))
	      {
	        tSalChnl = "05,07";
	      }
	      //comment by jiaqiangli 2007-04-30 有交叉的地方

	      return tSalChnl;
	   }



	// xjh Add 2005/04/01
	public static String switchBranchTypetoSalChnl(String cBranchType,
			String cBranchType2) {
		LDCodeRelaSet tLDCodeRelaSet = new LDCodeRelaSet();
		LDCodeRelaDB tLDCodeRelaDB = new LDCodeRelaDB();
		tLDCodeRelaDB.setRelaType("salechnlvsbranchtype");
		tLDCodeRelaDB.setCode1(cBranchType);
		tLDCodeRelaDB.setCode2(cBranchType2);
		tLDCodeRelaSet = tLDCodeRelaDB.query();
		if (tLDCodeRelaSet.size() == 0)
			return null;
		else
			return tLDCodeRelaSet.get(1).getCode3();
	}

	/**
	 * 得到机构2468位的函数 author: zy
	 * 
	 * @param cmanagecom
	 *            销售系统的管理机构
	 * @param mManagecom
	 *            登陆管理机构
	 * @return SSRS 机构数
	 */
	public static SSRS getbranch(String cManagecom, String mManagecom) {
		// GlobalInput mGlobalInput =new GlobalInput() ;
		SSRS tSSRS = new SSRS();
		int n = cManagecom.trim().length();
		logger.debug("录入机构－－－" + cManagecom);
		logger.debug("录入机构长度－－－" + n);
		String MM = "";
		switch (n) {
		case 2: {
			MM = " and char_length(a)=4 ";
			break;
		}
		case 4: {
			MM = " and char_length(a)=6 ";
			break;
		}
		case 6: {
			MM = " and char_length(a)=8 ";
			break;
		}
		case 8: {
			MM = " and char_length(a)=12 ";
			break;
		}
		case 12: {
			MM = " and char_length(a)=15 ";
			break;
		}
		case 15: {
			MM = " and char_length(a)=18 ";
			break;
		}
		}
		String nsql = "select a,b from (select trim(comcode) a,shortname b from ldcom union"
				+ " select trim(branchattr) a,name b From labranchgroup where branchtype='1'"
				+ " and (state<>1 or state is null) and char_length(trim(branchattr))<>8) g"
				+ " where a like concat('"
				+ "?code?"
				+ "','%') and a like concat('"
				+ "?a1?" + "','%')" + MM + "order by a";
		 SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
	      sqlbv3.sql(nsql);
	      sqlbv3.put("code", cManagecom);
	      sqlbv3.put("a1", mManagecom);
		// SSRS ySSRS = new SSRS();
		ExeSQL aExeSQL = new ExeSQL();
		tSSRS = aExeSQL.execSQL(sqlbv3);
		return tSSRS;
	}
	
	/**
	 * 根据代理人职级查询代理人系列（只适用个险）
	 * 
	 * @param cAgentGrade
	 *            个险的代理人职级
	 * @return String 职级对应的代理人系列
	 */
	   public static String getAgentSeries(String cAgentGrade)
	   {
	       if (cAgentGrade == null||cAgentGrade.equals(""))
	           return null;
	       String tAgentSeries = "";
	       String tSQL = "select Trim(code2) from ldcodeRela where relaType = 'gradeserieslevel' "
	                    +"and code1 = '"+"?code1?"+"' ";
	       SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
		      sqlbv4.sql(tSQL);
		      sqlbv4.put("code1", cAgentGrade);
	       ExeSQL tExeSQL = new ExeSQL();
	       tAgentSeries = tExeSQL.getOneValue(sqlbv4);
	       if(tExeSQL.mErrors.needDealError())
	           return null;
	       return tAgentSeries;
	   }

	/**
	 * 根据代理人编码查询代理人入司职级
	 * 
	 * @param cAgentCode
	 *            代理人编码
	 * @return String 代理人入司日期
	 */
	public static String getAgentGrade(String cAgentCode) {
		if (cAgentCode == null || cAgentCode.equals("")) {
			return null;
		}
		String tAgentGrade = "";
		String tSQL = "select agentgrade from latreeb where agentcode='"
				+ "?agentcode?" + "' order by makedate,maketime";
		   SQLwithBindVariables sqlbv5=new SQLwithBindVariables();
		      sqlbv5.sql(tSQL);
		      sqlbv5.put("agentcode", cAgentCode);
		ExeSQL tExeSQL = new ExeSQL();
		tAgentGrade = tExeSQL.getOneValue(sqlbv5);
		if (tAgentGrade == null || tAgentGrade.equals("")) {
			String aSQL = "select agentgrade from latree where agentcode='"
					+ "?cAgentCode?" + "' ";
			  SQLwithBindVariables sqlbv11=new SQLwithBindVariables();
		      sqlbv11.sql(aSQL);
		      sqlbv11.put("cAgentCode", cAgentCode);
			ExeSQL aExeSQL = new ExeSQL();
			tAgentGrade = aExeSQL.getOneValue(sqlbv11);
		}
		return tAgentGrade;
	}
	

	 public static String AdjustCommCheck(String tAgentCode,String tStartDate)
	   {
	     if (!tStartDate.endsWith("01") )//如果开始时间不是从1号开始，则返回错误
	        return "调整日期必须是从一号开始" ;
	      String sql="select max(indexcalno) from lawage where AgentCode='"+"?AgentCode?"+"'";
	      SQLwithBindVariables sqlbv6=new SQLwithBindVariables();
	      sqlbv6.sql(sql);
	      sqlbv6.put("AgentCode", tAgentCode);
	      ExeSQL tExeSQL=new ExeSQL();
	      String maxIndexCalNo=tExeSQL.getOneValue(sqlbv6)  ;
	      if (maxIndexCalNo!=null&&!maxIndexCalNo.equals("") )
	      {
	        String lastDate=PubFun.calDate(tStartDate,-1,"M",null) ;
	        lastDate=AgentPubFun.formatDate(lastDate,"yyyyMM") ;
	        if (maxIndexCalNo.trim() .compareTo(lastDate.trim() )>0 )
	        {
	          return ( "上次发工资是"+maxIndexCalNo.substring(0,4) +"年"+maxIndexCalNo.substring(4).trim()  +"月，因次调整日期必须从这个月的下一个月1号");
	        }
	      }
	      return "00";

	   }	
	
   /**
    *根据销售渠道和分公司,得到基本法的版本
    * @param String sBranchType,String sManageCom
    * @return String
    */
   public static String getAgentEdition(String sBranchType,String sManageCom)
   {
     String sResult = "";
     String sSQL = "";
     sSQL = "select trim(code2) from ldcoderela where relatype='agentedition' "
          + " and code3 = '"+"?AgentCode?"+"' and code1='"+"?code1?"+"'";
     SQLwithBindVariables sqlbv7=new SQLwithBindVariables();
     sqlbv7.sql(sSQL);
     sqlbv7.put("AgentCode", sBranchType);
     sqlbv7.put("code1", sManageCom.substring(0,4));
     ExeSQL sExeSQL = new ExeSQL();
     sResult = sExeSQL.getOneValue(sqlbv7);
     return sResult;
   }
   /**
    * 组织归属程序更新业绩时使用
    * @param tBranchCode
    * @param tAgentGroup
    * @param tBranchLevel
    * @return String[]
    */
   public static String[] ascriptGetAllBranch(String tBranchCode,String tAgentGroup,String tBranchLevel)
   {
     String[] tResult = {"","","",""};
     int sLoopLevel = 4;
     ExeSQL tExeSQL = new ExeSQL();
     String tSQL = "select branchattr from labranchgroup where agentgroup='"+"?agentgroup?"+"' "
                 + " and endflag<>'Y' ";
     SQLwithBindVariables sqlbv8=new SQLwithBindVariables();
     sqlbv8.sql(tSQL);
     sqlbv8.put("agentgroup", tBranchCode);
     String tBranchAttr = tExeSQL.getOneValue(sqlbv8);
     if(tBranchAttr ==null||tBranchAttr.equals(""))
     {
       return tResult;
     }
     for(int i=1;i<=sLoopLevel;i++)
     {
       int tSubLength = 0;
       String tempLevel = "0"+String.valueOf(i);
       String tempBranchCode = "";
       String tempSQL = "";
       SSRS tempSSRS = new SSRS();
       if(i==1)
         tSubLength = 18;
       else if(i==2)
         tSubLength = 15;
       else if(i==3)
         tSubLength = 12;
       else if(i==4)
         tSubLength = 10;
       tempSQL = "select agentgroup,branchlevel from labranchgroup where branchattr=substr('"+"?substr?"+"',1,"+"?s1?"+") "
               + " and branchtype='1' and endflag<>'Y' ";
       SQLwithBindVariables sqlbv9=new SQLwithBindVariables();
       sqlbv9.sql(tempSQL);
       sqlbv9.put("substr", tBranchAttr);
       sqlbv9.put("s1", tSubLength);
       tempSSRS = tExeSQL.execSQL(sqlbv9);
       if(tempSSRS.getMaxRow()<=0||tempSSRS.getMaxRow()>1)
       {
         tResult[i-1] = tempBranchCode;//置空
       }
       else
       {
         logger.debug("!@:"+tempSSRS.GetText(1,2));
         logger.debug("2!@:"+tempSSRS.GetText(1,1));
         if(tempSSRS.GetText(1,2).trim().equals(tBranchLevel))
         {
           tResult[i-1] = tAgentGroup;
         }
         else
         {
           tResult[i-1] = tempSSRS.GetText(1,1);
         }
       }
     }
     return tResult;
   }
   public static String[] ascriptGetAllBranch(String tBranchAttr,String tAgentGroup,String tBranchLevel,String tType)
   {
     String[] tResult = {"","","",""};
     int sLoopLevel = 4;
     ExeSQL tExeSQL = new ExeSQL();
     if(tBranchAttr ==null||tBranchAttr.equals(""))
     {
       return tResult;
     }
     for(int i=1;i<=sLoopLevel;i++)
     {
       int tSubLength = 0;
       String tempLevel = "0"+String.valueOf(i);
       String tempBranchCode = "";
       String tempSQL = "";
       SSRS tempSSRS = new SSRS();
       if(i==1)
         tSubLength = 18;
       else if(i==2)
         tSubLength = 15;
       else if(i==3)
         tSubLength = 12;
       else if(i==4)
         tSubLength = 10;
       //tongmeng 2007-05-21 modify
       //去掉endflag<>'Y'
       tempSQL = "select agentgroup,branchlevel from labranchgroup where branchattr=substr('"+"?s2?"+"',1,"+"?s3?"+") "
               + " and branchtype='1' ";
       SQLwithBindVariables sqlbv10=new SQLwithBindVariables();
       sqlbv10.sql(tempSQL);
       sqlbv10.put("s2", tBranchAttr);
       sqlbv10.put("s3", tSubLength);
       tempSSRS = tExeSQL.execSQL(sqlbv10);
       if(tempSSRS.getMaxRow()<=0||tempSSRS.getMaxRow()>1)
       {
         tResult[i-1] = tempBranchCode;//置空
       }
       else
       {
         logger.debug("!@:"+tempSSRS.GetText(1,2));
         logger.debug("2!@:"+tempSSRS.GetText(1,1));
         if(tempSSRS.GetText(1,2).trim().equals(tBranchLevel))
         {
           tResult[i-1] = tAgentGroup;
         }
         else
         {
           tResult[i-1] = tempSSRS.GetText(1,1);
         }
       }
     }
     return tResult;
   }
	/**
	 * 查询代理人所在组的外部编码
	 * 
	 * @param cAgentGrade
	 *            代理人代码
	 * @return String 代理人所在组的外部编码
	 */
	public static String getAgentBranchAttr(String cAgentCode) {
		String tBranchAttr = "";
		if (cAgentCode == null || cAgentCode.equals("")) {
			return null;
		}
		String tSQL = "Select Trim(BranchAttr) From LABranchGroup Where AgentGroup = ("
				+ "Select BranchCode From LAAgent Where AgentCode = '"
				+ "?s4?" + "') ";
		 SQLwithBindVariables sqlbv11=new SQLwithBindVariables();
	       sqlbv11.sql(tSQL);
	       sqlbv11.put("s4", cAgentCode);
		ExeSQL tExeSQL = new ExeSQL();
		tBranchAttr = tExeSQL.getOneValue(sqlbv11);
		if (tExeSQL.mErrors.needDealError()) {
			return null;
		}
		return tBranchAttr;
	}

	/**
	 * 按cFormat的格式 格式化日期类型 年月日的表示字母为 y M d
	 */
	public static String formatDate(String cDate, String cFormat) {
		String FormatDate = "";
		Date tDate;
		SimpleDateFormat sfd = new SimpleDateFormat(cFormat);
		FDate fDate = new FDate();
		tDate = fDate.getDate(cDate.trim());
		FormatDate = sfd.format(tDate);
		// logger.debug("[--formatedate--]:"+FormatDate);
		return FormatDate;
	}

	/**
	 * 从LDCodeRela表中查出ManageCom对应的地区类型
	 */
	public static String getAreaType(String cManageCom) {
		// AreaType
		String tSql = "Select trim(code2) From LDCodeRela Where trim(RelaType) = 'comtoareatype' and trim(code1) = '"
				+ "?s5?" + "'";
		 SQLwithBindVariables sqlbv11=new SQLwithBindVariables();
	       sqlbv11.sql(tSql);
	       sqlbv11.put("s5", cManageCom.substring(0, 4));
		ExeSQL tExeSQL = new ExeSQL();
		String tAreaType = tExeSQL.getOneValue(sqlbv11);
		if (tExeSQL.mErrors.needDealError()) {
			return null;
		}
		if (tAreaType == null || tAreaType.equals("")) {
			return null;
		}
		return tAreaType;
	}

	/**
	 * 大纲版本分渠道类型.2006-05-18 Howie 从LDCodeRela表中查出ManageCom对应的地区类型
	 * 通过管理机构计算地区类型，各渠道现在有'01'(A版),'02'(B版)大纲
	 */
	public static String getAreaType(String cManageCom, String cBranchType) {
		String tSql = "";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		// AreaType
		if (cBranchType.equals("4")) { // 收费员大纲版本定义到中支
			tSql = "Select trim(code2) From LDCodeRela Where trim(RelaType) = 'comtoareatype' and trim(code1) = '"
					+ "?s6?"
					+ "' and trim(code3) = '"
					+ "?s7?" + "'";
			 sqlbv=new SQLwithBindVariables();
			 sqlbv.sql(tSql);
			 sqlbv.put("s6", cManageCom.substring(0, 6));
		     sqlbv.put("s7", cBranchType);
		} else {
			tSql = "Select trim(code2) From LDCodeRela Where trim(RelaType) = 'comtoareatype' and trim(code1) = '"
					+ "?s8?"
					+ "' and trim(code3) = '"
					+ "?s9?" + "'";
			sqlbv=new SQLwithBindVariables();
			sqlbv.sql(tSql);
			sqlbv.put("s8", cManageCom.substring(0, 4));
			sqlbv.put("s9", cBranchType);
		}

		ExeSQL tExeSQL = new ExeSQL();
		String tAreaType = tExeSQL.getOneValue(sqlbv);
		if (tExeSQL.mErrors.needDealError()) {
			return null;
		}
		if (tAreaType == null || tAreaType.equals("")) {
			return null;
		}
		return tAreaType;
	}

	/**
	 * 03修订版本和07版大纲的衔接，取地区类型 added by hust==>e-mail:hushuntao@sinosoft.com.cn
	 * cManageCom==管理机构；cBranchType==渠道；cVersion==版本（03或07）
	 * 返回参数：03版返回01，02，03；07版返回04，05，06
	 */
	public static String getAreaType(String cManageCom, String cBranchType,
			String cVersion) {
		String sql = "";
		SQLwithBindVariables sqlbv14=new SQLwithBindVariables();
		if (cVersion.equals("03")) {
			sql = "Select trim(code2) From LDCodeRela Where trim(RelaType) = 'comtoareatype' and trim(code1) = '"
					+ "?s11?"
					+ "' and trim(code3) = '"
					+ "?s12?" + "'";
			sqlbv14=new SQLwithBindVariables();
		       sqlbv14.sql(sql);
		       sqlbv14.put("s11", cManageCom.substring(0, 4));
		       sqlbv14.put("s12", cBranchType);
		} else if (cVersion.equals("07")) {
			sql = "Select trim(othersign) From LDCodeRela Where trim(RelaType) = 'comtoareatype' and trim(code1) = '"
					+ "?s13?"
					+ "' and trim(code3) = '"
					+ "?s14?" + "'";
			sqlbv14=new SQLwithBindVariables();
			sqlbv14.sql(sql);
			sqlbv14.put("s13", cManageCom.substring(0, 4));
			sqlbv14.put("s14", cBranchType);
		}
		ExeSQL tExeSQL = new ExeSQL();
		String tAreaType = tExeSQL.getOneValue(sqlbv14);
		if (tExeSQL.mErrors.needDealError()) {
			return null;
		}
		if (tAreaType == null || tAreaType.equals("")) {
			return null;
		}
		return tAreaType;
	}

	public static String CalWageNo(String cWageNo, int interval) {
		String tYMD = cWageNo + "01";
		String AYMD = PubFun.calDate(tYMD, interval - 1, "M", "");
		String AWageNo = formatDate(AYMD, "yyyyMM");
		return AWageNo;
	}

	public static int calWageNoIntv(String cBeginWageNo, String cEndWageNo) {
		String cBYMD = cBeginWageNo + "01";
		String cEYMD = cEndWageNo + "01";
		int intv = PubFun.calInterval(cBYMD, cEYMD, "M");
		return intv;
	}

	/**
	 * 转换规则：上月26号---本月25号算作本月
	 * 
	 * @Return :格式为YYYYMM
	 */
	public static String ConverttoYM(String cDate) {
		String sYearMonth = "";
		ExeSQL tExeSQL = new ExeSQL();
		String tSql = "Select YearMonth From LAStatSegment Where StatType = '5' "
				+ "And StartDate <= '"
				+ "?s15?"
				+ "' And EndDate >= '"
				+ "?s16?"
				+ "'";
		SQLwithBindVariables sqlbv16=new SQLwithBindVariables();
	       sqlbv16.sql(tSql);
	       sqlbv16.put("s15", cDate);
	       sqlbv16.put("s16", cDate);
		sYearMonth = tExeSQL.getOneValue(sqlbv16);
		if (tExeSQL.mErrors.needDealError()) {
			return null;
		}
		if (sYearMonth == null || sYearMonth.equals("")) {
			return null;
		}
		return sYearMonth;
	}

	/**
	 * getManagecom 从LABranchGroup 表中由AgentGroup取得其管理机构
	 * 
	 * @param AgentGroup
	 *            String
	 * @return String
	 */
	public static String getManagecom(String cAgentGroup) {
		if (cAgentGroup == null || cAgentGroup.equals("")) {
			return null;
		}
		String tSQL = "Select Trim(Managecom) From LABranchGroup Where AgentGroup = '"
				+ "?AgentGroup?" + "'";
		SQLwithBindVariables sqlbv17=new SQLwithBindVariables();
	       sqlbv17.sql(tSQL);
	       sqlbv17.put("AgentGroup", cAgentGroup);
		ExeSQL tExeSQL = new ExeSQL();
		String tManagecom = tExeSQL.getOneValue(sqlbv17);
		if (tExeSQL.mErrors.needDealError()) {
			return null;
		}
		return tManagecom;
	}

	/**
	 * 转换规则：上月26号---本月25号算作本月
	 * 
	 * @Return :格式为YYYYMMDD
	 */
	public static String ConverttoYMD(String cDate) {
		String sYearMonth = "";
		sYearMonth = ConverttoYM(cDate);
		if (sYearMonth != null && !sYearMonth.equals("")) {
			sYearMonth = sYearMonth.substring(0, 4) + "-"
					+ sYearMonth.substring(4) + "-01";
		}
		return sYearMonth;
	}

	/**
	 * 查询机构系列号
	 * 
	 * @param cAgentGroup
	 *            机构内部编码
	 * @return String 机构系列号
	 */
	/*
	 * public static String getBranchSeries(String cAgentGroup) { String
	 * tBranchSeries = cAgentGroup; if (cAgentGroup == null ||
	 * cAgentGroup.equals("")) { return ""; } String tSQL = "Select
	 * Trim(UpBranch) From LABranchGroup Where AgentGroup = '" + cAgentGroup +
	 * "'"; ExeSQL tExeSQL = new ExeSQL(); String tUpBranch =
	 * tExeSQL.getOneValue(tSQL); if (tExeSQL.mErrors.needDealError()) { return
	 * ""; } if (tUpBranch == null || tUpBranch.compareTo("") == 0) {
	 * logger.debug("到达出口"); } else { //如果发现该机构存在上级机构，则查询上级机构信息 //xjh
	 * Modify ， 2005/02/17，机构间使用“：”分隔符 // tBranchSeries =
	 * getBranchSeries(tUpBranch) + tBranchSeries; tBranchSeries =
	 * getBranchSeries(tUpBranch) + ":" + tBranchSeries;
	 *  // logger.debug("机构序列" + tBranchSeries); } return tBranchSeries; }
	 */
	public static String getBranchSeries(String cAgentGroup) {
		String tBranchSeries = "";
		if (cAgentGroup == null || cAgentGroup.equals("")) {
			return "";
		}
		String tSQL = "Select branchseries From LABranchGroup Where AgentGroup = '"
				+ "?AgentGroup1?" + "'";
		SQLwithBindVariables sqlbv18=new SQLwithBindVariables();
	       sqlbv18.sql(tSQL);
	       sqlbv18.put("AgentGroup1", cAgentGroup);
		ExeSQL tExeSQL = new ExeSQL();
		tBranchSeries = tExeSQL.getOneValue(sqlbv18);
		if (tExeSQL.mErrors.needDealError()) {
			return "";
		}
		return tBranchSeries;
	}

	/**
	 * 自动生成销售机构外部号BranchAttr
	 * 
	 * @param cUpBranchAttr
	 *            上级机构的外部编码 cBranchLevel 被生成机构的级别 cBranchType 渠道 cBranchType2
	 *            渠道类型2
	 * @return String 机构系列号
	 */
	// public static String CreateBranchAttr(String cUpBranchAttr,
	// String cBranchLevel,
	// String cBranchType,
	// String cBranchType2)
	// {
	// logger.debug("CreateBranchAttr...");
	// LABranchLevelDB tLABranchLevelDB = new LABranchLevelDB();
	// tLABranchLevelDB.setBranchLevelCode(cBranchLevel);
	// tLABranchLevelDB.setBranchType(cBranchType);
	// tLABranchLevelDB.setBranchType2(cBranchType2);
	// if (!tLABranchLevelDB.getInfo())
	// {
	// return null;
	// }
	// LABranchLevelSchema tLABranchLevelSchema = new LABranchLevelSchema();
	// tLABranchLevelSchema = tLABranchLevelDB.getSchema();
	//
	// String tmaxBranchAttr = "";
	// ExeSQL tExeSQL = new ExeSQL();
	// String tmaxBranchAttr1 = "";
	// String tmaxBranchAttr2 = "";
	// String sql = "";
	// //新建的展业机构层级为营业区时，要限制系统内外部编码启用'9000'以上的编码
	// if (cBranchLevel.equals("03"))
	// {
	// sql = "select max(branchattr) from labranchgroup where
	// BranchType='"+cBranchType+"' and BranchLevel = '"+cBranchLevel +"' and
	// branchattr < '9000' "
	// +(cUpBranchAttr.equals("")?"":" and branchattr like '" +cUpBranchAttr +
	// "%' ");
	// }
	// else
	// {
	// sql = "select max(branchattr) from labranchgroup where
	// BranchType='"+cBranchType+"' and BranchLevel = '"+cBranchLevel +"'"
	// +(cUpBranchAttr.equals("")?"":" and branchattr like '" +cUpBranchAttr +
	// "%' ");
	// }
	//
	// tmaxBranchAttr1 = tExeSQL.getOneValue(sql);
	// if (tmaxBranchAttr1 == null || tmaxBranchAttr1.equals(""))
	// {
	// tmaxBranchAttr1 = cUpBranchAttr +
	// "000000000000000".substring(0,
	// Integer.parseInt(tLABranchLevelSchema.
	// getBranchLevelProperty2()) -
	// cUpBranchAttr.length());
	// }
	// BigInteger tBigInteger = new BigInteger("1" + tmaxBranchAttr1);
	// logger.debug("tmaxBranchAttr1: "+tmaxBranchAttr1 +" "+
	// tBigInteger);
	// if (cBranchLevel.equals("03"))
	// {
	// sql = "select max(branchattr) from labranchgroupb where
	// BranchType='"+cBranchType+"' and BranchLevel = '"+cBranchLevel +"' and
	// branchattr < '9000' "
	// +(cUpBranchAttr.equals("")?"":" and branchattr like '" +cUpBranchAttr +
	// "%' ");
	// }
	// else
	// {
	// sql = "select max(branchattr) from labranchgroupb where
	// BranchType='"+cBranchType+"' and BranchLevel = '"+cBranchLevel +"'"
	// +(cUpBranchAttr.equals("")?"":" and branchattr like '" +cUpBranchAttr +
	// "%' ");
	// }
	// tmaxBranchAttr2 = tExeSQL.getOneValue(sql);
	// if (tmaxBranchAttr2 == null || tmaxBranchAttr2.equals(""))
	// {
	// tmaxBranchAttr2 = cUpBranchAttr +
	// "000000000000000".substring(0,
	// Integer.parseInt(tLABranchLevelSchema.
	// getBranchLevelProperty2()) -
	// cUpBranchAttr.length());
	// }
	//
	// BigInteger tBigInteger1 = new BigInteger("1" + tmaxBranchAttr2);
	// logger.debug("tmaxBranchAttr2: "+tmaxBranchAttr2 +" "+
	// tBigInteger1);
	// if (tBigInteger1.compareTo(tBigInteger) >= 0)
	// {
	// BigInteger tAdd = new BigInteger("1");
	// tBigInteger1 = tBigInteger1.add(tAdd);
	// tmaxBranchAttr = tBigInteger1.toString().substring(1);
	// }
	// else
	// {
	// BigInteger tAdd = new BigInteger("1");
	// tBigInteger = tBigInteger.add(tAdd);
	// tmaxBranchAttr = tBigInteger.toString().substring(1);
	// }
	//
	// return tmaxBranchAttr;
	// }
	//
	/**
	 * 自动生成销售机构外部号BranchAttr
	 * 
	 * @param cUpBranchAttr
	 *            上级机构的外部编码 cBranchLevel 被生成机构的级别 cBranchType 渠道 cBranchType2
	 *            渠道类型2
	 * @return String 机构系列号
	 */
	public static String getMaxBranchAttr(String cUpBranchAttr,
			String cMaxBranchAttr) {
		logger.debug("CreateBranchAttr...");

		int iLength = cUpBranchAttr.length();
		String tMaxBranchAttr = cMaxBranchAttr.substring(iLength);

		BigInteger tBigInteger = new BigInteger("1" + tMaxBranchAttr);

		BigInteger tAdd = new BigInteger("1");
		tBigInteger = tBigInteger.add(tAdd);
		tMaxBranchAttr = cUpBranchAttr + tBigInteger.toString().substring(1);

		return tMaxBranchAttr;
	}

	/*
	 * 自动校验人员在指定考核年月，是否已经处在考核归属完成的阶段 cAgentCode——人员编码 cBranchType——校验渠道
	 * cIndexCalNo——考核年月 return: 0——本月参加考核，归属完成，条件成立 1——本月参加考核，归属未完成，条件不成立
	 * 2——本月未参加考核，条件成立 3——本月人员所在机构考核工作未开始，条件不成立 4——收费员暂时不处理，条件成立
	 * 5——人员渠道不匹配，条件不成立 6——其它，条件成立 7——其它，条件不成立
	 */
	public static int CheckAgentAssessState(String cAgentCode,
			String cBranchType, String cIndexCalNo) {
		return 0;
	}

	/*
	 * 根据个险信息找出有效团险交叉销售的专管员,查找顺序按输入对象低至高,遵循低有效原则
	 * cAgentcode——个险业务员编码,cBranchCode——个险营销组的内部编码,cManageCom——个险营销服务部；
	 * 返回：团险交叉销售专管员的数组，按makedate,modifydate,StandbyFlag1(比率)排序；查询不到返回null
	 * 注意事项：当cBranchCode条件查询失败时，方法延溯其上级机构
	 */

	public static String[] getExchangeAgentCode(String cAgentCode,
			String cBranchCode, String cManageCom) {
		String[] GAgentCode = null;
		String AgentGroup = "";
		String District = "";
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		String SQLstr = "";
		// 1.AgentCode条件
		SQLstr = "select agentcode from laagenttobranch where code1='"
				+ "?code1?"
				+ "' and state='1' and type='1' order by makedate,modifydate,StandbyFlag1";
		SQLwithBindVariables sqlbv19=new SQLwithBindVariables();
	       sqlbv19.sql(SQLstr);
	       sqlbv19.put("code1", cAgentCode);
		tSSRS = tExeSQL.execSQL(sqlbv19);
		if (tSSRS.MaxRow <= 0) {// 说明没有具体到人的记录
			SQLstr = "select agentcode from laagenttobranch where code1='"
					+ "?code2?"
					+ "' and state='1' and type='2' order by makedate,modifydate,StandbyFlag1";
			SQLwithBindVariables sqlbv20=new SQLwithBindVariables();
		       sqlbv20.sql(SQLstr);
		       sqlbv20.put("code2", cBranchCode);
			tSSRS = tExeSQL.execSQL(sqlbv20);
			if (tSSRS.MaxRow <= 0) {// 说明没有具体到组的记录
				SQLstr = "select upbranch from labranchgroup where agentgroup='"
						+ "?code3?" + "'";
				SQLwithBindVariables sqlbv21=new SQLwithBindVariables();
			       sqlbv21.sql(SQLstr);
			       sqlbv21.put("code3", cBranchCode);
				AgentGroup = tExeSQL.getOneValue(sqlbv21);
				SQLstr = "select agentcode from laagenttobranch where code1='"
						+ "?code4?"
						+ "' and state='1' and type='2' order by makedate,modifydate,StandbyFlag1";
				SQLwithBindVariables sqlbv22=new SQLwithBindVariables();
			       sqlbv22.sql(SQLstr);
			       sqlbv22.put("code4", AgentGroup);
				tSSRS = tExeSQL.execSQL(sqlbv22);
				if (tSSRS.MaxRow <= 0) {// 说明没有具体到部的记录
					SQLstr = "select upbranch from labranchgroup where agentgroup='"
							+ "?code5?" + "'";
					SQLwithBindVariables sqlbv23=new SQLwithBindVariables();
				       sqlbv23.sql(SQLstr);
				       sqlbv23.put("code5", AgentGroup);
					District = tExeSQL.getOneValue(sqlbv23);
					SQLstr = "select agentcode from laagenttobranch where code1='"
							+ "?code6?"
							+ "' and state='1' and type='2' order by makedate,modifydate,StandbyFlag1";
					SQLwithBindVariables sqlbv24=new SQLwithBindVariables();
				       sqlbv24.sql(SQLstr);
				       sqlbv24.put("code6", District);
					tSSRS = tExeSQL.execSQL(sqlbv24);
					if (tSSRS.MaxRow <= 0) {// 说明没有具体到区的记录
						SQLstr = "select agentcode from laagenttobranch where code1='"
								+ "?code7?"
								+ "' and state='1' and type='3' order by makedate,modifydate,StandbyFlag1";
						SQLwithBindVariables sqlbv25=new SQLwithBindVariables();
					       sqlbv25.sql(SQLstr);
					       sqlbv25.put("code7", cManageCom);
						tSSRS = tExeSQL.execSQL(sqlbv25);
						if (tSSRS.MaxRow <= 0) {// 说明没有具体到营销服务部的记录
							return null;
						}
					}
				}
			}
		}
		try {
			GAgentCode = new String[tSSRS.MaxRow];
			for (int i = 1; i <= tSSRS.MaxRow; i++) {
				GAgentCode[i - 1] = tSSRS.GetText(i, 1);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
		return GAgentCode;
	}

	/*
	 * 功能：根据个险信息找出有效团险交叉销售的专管员,查找顺序为人、组、部、区、营销服务部,遵循低有效原则
	 * 参数：cAgentcode——个险业务员编码；
	 * 返回：团险交叉销售专管员的数组，按makedate,modifydate,StandbyFlag1(比率)排序；查询不到返回null
	 */
	public static String[][] getExchangeAgentCodeAndRate(String cAgentCode) {
		String[][] GAgentCode = null;
		String BranchCode = "";
		String AgentGroup = "";
		String District = "";
		String ManageCom = "";
		String BranchSeries = "";
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		String SQLstr = "";
		SQLstr = "select branchseries,managecom from labranchgroup where agentgroup=(select branchcode from latree where agentcode='"
				+ "?c1?" + "' ) ";
		SQLwithBindVariables sqlbv26=new SQLwithBindVariables();
	       sqlbv26.sql(SQLstr);
	       sqlbv26.put("c1", cAgentCode);
		tSSRS = tExeSQL.execSQL(sqlbv26);
		if (tSSRS.MaxRow <= 0) {
			return null;
		}
		ManageCom = tSSRS.GetText(1, 2);
		BranchSeries = tSSRS.GetText(1, 1);
		// 1.AgentCode条件
		SQLstr = "select agentcode,standbyflag1 from laagenttobranch where code1='"
				+ "?c2?"
				+ "' and state='1' and type='1' order by makedate,modifydate,StandbyFlag1";
		SQLwithBindVariables sqlbv27=new SQLwithBindVariables();
	       sqlbv27.sql(SQLstr);
	       sqlbv27.put("c2", cAgentCode);
		tSSRS = tExeSQL.execSQL(sqlbv27);
		if (tSSRS.MaxRow <= 0) {// 说明没有具体到人的记录
			BranchCode = BranchSeries.substring(BranchSeries.length() - 12);
			BranchSeries = BranchSeries.substring(0, BranchSeries
					.lastIndexOf(":"));
			SQLstr = "select agentcode,standbyflag1 from laagenttobranch where code1='"
					+ "?c3?"
					+ "' and state='1' and type='2' order by makedate,modifydate,StandbyFlag1";
			SQLwithBindVariables sqlbv28=new SQLwithBindVariables();
		       sqlbv28.sql(SQLstr);
		       sqlbv28.put("c3", BranchCode);
			tSSRS = tExeSQL.execSQL(sqlbv28);
			if (tSSRS.MaxRow <= 0) {// 说明没有具体到组的记录
				AgentGroup = BranchSeries.substring(BranchSeries.length() - 12);
				BranchSeries = BranchSeries.substring(0, BranchSeries
						.lastIndexOf(":"));
				SQLstr = "select agentcode,standbyflag1 from laagenttobranch where code1='"
						+ "?c4?"
						+ "' and state='1' and type='2' order by makedate,modifydate,StandbyFlag1";
				SQLwithBindVariables sqlbv29=new SQLwithBindVariables();
			       sqlbv29.sql(SQLstr);
			       sqlbv29.put("c4", AgentGroup);
				tSSRS = tExeSQL.execSQL(sqlbv29);
				if (tSSRS.MaxRow <= 0) {// 说明没有具体到部的记录
					District = BranchSeries;
					SQLstr = "select agentcode,standbyflag1 from laagenttobranch where code1='"
							+ "?c5?"
							+ "' and state='1' and type='2' order by makedate,modifydate,StandbyFlag1";
					SQLwithBindVariables sqlbv30=new SQLwithBindVariables();
				       sqlbv30.sql(SQLstr);
				       sqlbv30.put("c5", District);
					tSSRS = tExeSQL.execSQL(sqlbv30);
					if (tSSRS.MaxRow <= 0) {// 说明没有具体到区的记录
						SQLstr = "select agentcode,standbyflag1 from laagenttobranch where code1='"
								+ "?c6?"
								+ "' and state='1' and type='3' order by makedate,modifydate,StandbyFlag1";
						SQLwithBindVariables sqlbv31=new SQLwithBindVariables();
					       sqlbv31.sql(SQLstr);
					       sqlbv31.put("c6", ManageCom);
						tSSRS = tExeSQL.execSQL(sqlbv31);
						
						if (tSSRS.MaxRow <= 0) {// 说明没有具体到营销服务部的记录
							return null;
						}
					}
				}
			}
		}
		try {
			GAgentCode = tSSRS.getAllData();
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
		return GAgentCode;
	}

	/*
	 * 功能：根据保单号和销售渠道，查询该保单对应的业务员编码、姓名、分摊比例 参数：cContNo-保单号，必填项
	 * cBranchType-渠道类型（个人代理销售团险、团险业务员），必填项 cBranchType2-团险子渠道，可以为空
	 * 返回：业务员的数组；查询不到返回null by zhanggl 2006-12-8
	 */
	public static String[][] getContToAgentAndRate(String cContNo,
			String cBranchType, String cBranchType2) {
		String[][] GAgentCode = null;
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		String SQLstr = "select a.agentcode,"
				+ "(select name from laagent where agentcode=a.agentcode),"
				+ "BusiRate,(select (select codename from ldcode where codetype='branchtype2' and trim(code)=b.branchtype2) from laagent b where agentcode=a.agentcode) from lacommisiondetail a"
				+ " where grpcontno='"
				+ "?c6?"
				+ "' and exists (select 'X' from laagent where agentcode=a.agentcode and branchtype='"
				+ "?c7?" + "'";
		SQLwithBindVariables sqlbv32=new SQLwithBindVariables();
	       
	       sqlbv32.put("c6", cContNo);
	       sqlbv32.put("c7", cBranchType);
		if (!cBranchType2.equals("") && cBranchType2 != null) {
			SQLstr += " and branchtype2='" + "?cBranchType2?" + "')";
		     sqlbv32.put("cBranchType2", cBranchType2);
		} else {
			SQLstr += ")";
		}
		sqlbv32.sql(SQLstr);
		tSSRS = tExeSQL.execSQL(sqlbv32);
		if (tSSRS.MaxRow <= 0) {
			return null;
		}

		try {
			GAgentCode = tSSRS.getAllData();
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
		return GAgentCode;
	}

	/*
	 * 功能：根据中介机构编码，查询该中介机构对应的中介专管员编码、姓名、分摊比例、团险子渠道编码、团险子渠道类型
	 * 参数：cAgentComCode-中介机构编码，必填项 返回：业务员的数组；查询不到返回null by zhanggl 2006-12-8
	 */
	public static String[][] getComToAgentAndRate(String cAgentComCode) {
		String[][] GAgentCode = null;
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		String SQLstr = "select AgentCode,(select name from laagent where agentcode=a.agentcode),Rate,"
				+ "(select branchtype2 from laagent where agentcode=a.agentcode),"
				+ "(select codename from ldcode,laagent b where codetype='branchtype2' and trim(code)=b.branchtype2 and b.agentcode=a.agentcode)"
				+ " from lacomtoagent a where agentcom='" + "?c8?" + "'";
		SQLwithBindVariables sqlbv33=new SQLwithBindVariables();
	       sqlbv33.sql(SQLstr);
	       sqlbv33.put("c8", cAgentComCode);
		tSSRS = tExeSQL.execSQL(sqlbv33);
		if (tSSRS.MaxRow <= 0) {
			return null;
		}

		try {
			GAgentCode = tSSRS.getAllData();
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
		return GAgentCode;
	}
	/**
	 * 个险程序新版基本法使用,由老职级查询新职级
	 * @param tOldAgentGrade
	 * @return String
	 */
	public static String queryNewAgentGrade(String tOldAgentGrade)
	{
	  String tResult = "";
	  String tSQL = "select gradecode1 from laagentgrade where "
	              + " gradecode like 'A%' and branchtype='1' "
	              + " and gradecode ='"+"?c11?"+"'";
	  SQLwithBindVariables sqlbv34=new SQLwithBindVariables();
      sqlbv34.sql(tSQL);
      sqlbv34.put("c11", tOldAgentGrade);
	  logger.debug("m由老职级查询新职级SQL:"+tSQL);
	  ExeSQL tExeSQL = new ExeSQL();
	  tResult = tExeSQL.getOneValue(sqlbv34);
	  return tResult;
	}

	public static void main(String args[]) {
		// String attr = AgentPubFun.CreateBranchAttr("0001","02","1","01");
		// String attr = AgentPubFun.getMaxBranchAttr("0001","0001007");
		// logger.debug(attr);
		// String Agentcode = "000000000473";
		// logger.debug(getBranchSeries(Agentcode));
		logger.debug(calWageNoIntv("200507", "200506"));
	}
}
