/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.pubfun;
import org.apache.log4j.Logger;

// import org.jdom.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Blob;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.sinosoft.lis.db.LBPolDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LDCode1DB;
import com.sinosoft.lis.db.LJAGetEndorseDB;
import com.sinosoft.lis.db.LJTempFeeDB;
import com.sinosoft.lis.db.LYSendToBankDB;
import com.sinosoft.lis.schema.LDCode1Schema;
import com.sinosoft.lis.vschema.LDCode1Set;
import com.sinosoft.lis.vschema.LJAGetEndorseSet;
import com.sinosoft.lis.vschema.LJTempFeeSet;
import com.sinosoft.lis.vschema.LYSendToBankSet;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;

public class XmlFun {
private static Logger logger = Logger.getLogger(XmlFun.class);
	private int starNum = 0;
	private int sendNum = 0;
	private int startNum = 0;
	private int lengthNum = 0;
	private static boolean firstLineFlag = true;

	public XmlFun() {
	}

	/**
	 * 返回年份加月份信息
	 * 
	 * @param value
	 *            String
	 * @return String
	 */
	public static String getYearMonth(String value) {
		String tYearMonth = "";
		tYearMonth = value.substring(2, 6);
		return tYearMonth;
	}

	/**
	 * 判断中文字符的个数，用于银行xml转换
	 * 
	 * @param value
	 *            String
	 * @param len
	 *            String
	 * @return String
	 */
	public static String getChinaLen(String value, String len) {
		int n = Integer.parseInt(len);
		char[] arr = value.toCharArray();
		int result = 0;

		for (int i = 0; i < n; i++) {
			if (arr[i] > 511) {
				result++;
			}
		}

		return result + "";
	}

	/**
	 * 判断中文字符的个数，用于银行xml转换
	 * 
	 * @param value
	 *            String
	 * @return String
	 */
	public static String getChinaLen(String value) {
		char[] arr = value.toCharArray();
		int result = 0;

		for (int i = 0; i < arr.length; i++) {
			if (arr[i] > 511) {
				result++;
			}
		}

		return result + "";
	}

	/**
	 * 判断中文字符的个数，用于银行xml转换
	 * 
	 * @param value
	 *            String
	 * @return int
	 */
	public static int getChinaLength(String value) {
		char[] arr = value.toCharArray();
		int result = 0;

		for (int i = 0; i < arr.length; i++) {
			if (arr[i] > 511) {
				// logger.debug(arr[i]);
				result = result + 2;
			} else {
				// logger.debug(arr[i]);
				result = result + 1;
			}
		}

		return result;
	}

	/**
	 * 得到当前系统日期 author: YT
	 * 
	 * @return 当前日期的格式字符串,日期格式为"yyyy-MM-dd"
	 */
	public static String getCurrentDate() {
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		Date today = new Date();
		return df.format(today);
	}

	/**
	 * 得到当前系统时间 author: YT
	 * 
	 * @return 当前时间的格式字符串，时间格式为"HH:mm:ss"
	 */
	public static String getCurrentTime() {
		String pattern = "HH:mm:ss";
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		Date today = new Date();
		return df.format(today);
	}

	/**
	 * 标志第一行开始
	 */
	public static void setFirstLine() {
		firstLineFlag = true;
	}

	/**
	 * 判断是否是第一行
	 * 
	 * @return boolean
	 */
	public static boolean isFirstLine() {
		if (firstLineFlag) {
			firstLineFlag = false;
			return true;
		}

		return false;
	}

	/**
	 * 根据投保单号获取险种、交费年期和交费间隔类型
	 * 
	 * @param polNo
	 *            String
	 * @return String
	 */
	public static String getRiskInfo(String polNo) {
		LCPolDB tLCPolDB = new LCPolDB();
		tLCPolDB.setPolNo(polNo);
		tLCPolDB.setProposalNo(polNo);

		// 如果保单表有数据，一定是扣款，直接根据销售渠道判断银代即可
		if (!tLCPolDB.getInfo()) {
			// 如果保单表无数据，对应代付的情况
			logger.debug("可能是代付数据！");

			// 从暂交费表中查找，对应投保退费
			LJTempFeeDB tLJTempFeeDB = new LJTempFeeDB();
			tLJTempFeeDB.setTempFeeNo(polNo);
			LJTempFeeSet tLJTempFeeSet = tLJTempFeeDB.query();

			if (tLJTempFeeSet.size() > 0) {
				tLCPolDB.setPolNo(tLJTempFeeSet.get(1).getOtherNo());
				if (!tLCPolDB.getInfo()) {
					logger.debug("从保单表中获取险种信息失败！");
					return "0000000";
				}
			}
			// 如果暂交费表中无数据，去批改补退费表(实收/实付)查找，对应犹豫期撤单
			else {
				LJAGetEndorseDB tLJAGetEndorseDB = new LJAGetEndorseDB();
				tLJAGetEndorseDB.setEndorsementNo(polNo);
				// 犹豫期撤单标记
				tLJAGetEndorseDB.setFeeOperationType("WT");
				LJAGetEndorseSet tLJAGetEndorseSet = tLJAGetEndorseDB.query();
				if (tLJAGetEndorseSet.size() == 0)
					return "0000000";

				// 任意取一个保单号
				LBPolDB tLBPolDB = new LBPolDB();
				tLBPolDB.setPolNo(tLJAGetEndorseSet.get(1).getPolNo());
				if (!tLBPolDB.getInfo()) {
					logger.debug("从保单B表中获取险种信息失败！");
					return "0000000";
				}
				// 获取该保单号对应的主险保单号
				tLBPolDB.setPolNo(tLBPolDB.getMainPolNo());
				if (!tLBPolDB.getInfo()) {
					logger.debug("从保单B表中获取险种信息失败！");
					return "0000000";
				} else {
					tLCPolDB.setSaleChnl(tLBPolDB.getSaleChnl());
					tLCPolDB.setRiskCode(tLBPolDB.getRiskCode());
					tLCPolDB.setPayIntv(tLBPolDB.getPayIntv());
					tLCPolDB.setPayYears(tLBPolDB.getPayYears());
				}
			}
		}

		logger.debug("SaleChnl:" + tLCPolDB.getSaleChnl()
				+ " | RiskCode:" + tLCPolDB.getRiskCode() + " | PayIntv:"
				+ tLCPolDB.getPayIntv() + " | PayYears:"
				+ tLCPolDB.getPayYears());

		// 根据销售渠道、交费年期和交费间隔返回类型值
		if (tLCPolDB.getSaleChnl().equals("03") && tLCPolDB.getPayIntv() == 0
				&& tLCPolDB.getPayYears() == 5) {
			return tLCPolDB.getRiskCode() + "0";
		}

		if (tLCPolDB.getSaleChnl().equals("03") && tLCPolDB.getPayIntv() == 0
				&& tLCPolDB.getPayYears() == 8) {
			return tLCPolDB.getRiskCode() + "1";
		}

		if (tLCPolDB.getSaleChnl().equals("03") && tLCPolDB.getPayIntv() == 0
				&& tLCPolDB.getPayYears() == 12) {
			return tLCPolDB.getRiskCode() + "1";
		}

		return "0000000";
	}

	public static int num = 0;

	/**
	 * 显示W3C的DOM模型对象，显示JDOM的函数在BQ目录下的changeXml类中
	 * 
	 * @param d
	 *            Node
	 */
	public static void displayDocument(Node d) {
		num += 2;

		if (d.hasChildNodes()) {
			NodeList nl = d.getChildNodes();

			for (int i = 0; i < nl.getLength(); i++) {
				Node n = nl.item(i);

				for (int j = 0; j < num; j++) {
					logger.debug(" ");
				}
				if (n.getNodeValue() == null) {
					logger.debug("<" + n.getNodeName() + ">");
				} else {
					logger.debug(n.getNodeValue());
				}

				displayDocument(n);

				num -= 2;
				// logger.debug("num:" + num);

				if (n.getNodeValue() == null) {
					for (int j = 0; j < num; j++) {
						logger.debug(" ");
					}
					logger.debug("</" + n.getNodeName() + ">");
				}

			}

		}
	}

	/**
	 * 显示InputStream流对象
	 * 
	 * @param in
	 *            InputStream
	 */
	public static void displayStream(InputStream in) {
		try {
			// DataInputStream din = new DataInputStream(in);
			BufferedReader brin = new BufferedReader(new InputStreamReader(in));
			String s = "";

			while ((s = brin.readLine()) != null) {
				logger.debug(s);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 显示Blob的内容
	 * 
	 * @param blob
	 *            Blob
	 */
	public static void displayBlob(Blob blob) {
		try {
			InputStream in = blob.getBinaryStream();

			displayStream(in);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 根据不同的银行账号，返回不同的账户类型
	 * 
	 * @param String
	 *            AccNo
	 * @param String
	 *            BankCode
	 */
	public static String getCardType(String BankCode, String AccNo) {
		String CardType = "";
		LDCode1DB tLDCode1DB = new LDCode1DB();
		LDCode1Set tLDCode1Set = new LDCode1Set();
		LDCode1Schema tLDCode1Schema = new LDCode1Schema();
		String AccNoHead = "";
		int CTSP = 0;
		int CTSL = 0;
		BankCode=BankCode.trim();
		AccNo=AccNo.trim();
		String Privilege = getMaxCodeAlias(BankCode);
		if (Privilege.equals("0") || Privilege.equals("")) {
			CardType = "111";
		} else {
			int t = 0;
			t = Integer.parseInt(Privilege);
			for (int i = 0; i <= t; i++) {
				String SQL = " select * from LDCode1 "
						+ "  where CodeType = 'cardtype' " + "    and Code = '"
						+ "?BankCode?" + "'" + "    and CodeAlias = '" + i + "'";
				SQLwithBindVariables sqlbv=new SQLwithBindVariables();
				sqlbv.sql(SQL);
				sqlbv.put("BankCode", BankCode);
				sqlbv.put("i", i);
				tLDCode1Set = tLDCode1DB.executeQuery(sqlbv);
				logger.debug("SQL===============" + SQL);
				if (tLDCode1Set.size() <= 0) {
					logger.debug("数据库中无相关银行数据。");
					CardType = "111";
				} else {
					for (int j = 1; j <= tLDCode1Set.size(); j++) {
						tLDCode1Schema = tLDCode1Set.get(j);
						String length = tLDCode1Schema.getOtherSign();

						XmlFun tXmlFun = new XmlFun();
						tXmlFun.StrSplit(length);

						CTSP = tXmlFun.starNum();
						logger.debug("起始位置CTSP：" + CTSP);
						CTSL = tXmlFun.sendNum();
						logger.debug("截取长度CTSL：" + CTSL);
						AccNoHead = AccNo.substring(CTSP, CTSL);
						logger.debug("账户头标志AccNoHead============"
								+ AccNoHead);
						if (tLDCode1Schema.getCode1().trim().equals(AccNoHead)) {
							CardType = tLDCode1Schema.getCodeName();
							return CardType;
						}
					}
				}
			}
		}
		logger.debug("CardType=================" + CardType);
		if (CardType.equals("") || CardType.equals(null)
				|| CardType.equals("null")) {
			logger.debug("该账号" + AccNo + "不符合规则，请重新核实。");
		}
		return CardType;
	}

	private static String getMaxCodeAlias(String BankCode) {
		String MaxCodeAlias = "";
		ExeSQL tExeSQL = new ExeSQL();
		String Sql = " select max(CodeAlias) from LDCode1 "
				+ "  where CodeType = 'cardtype' " + "    and Code = '"
				+ "?BankCode?" + "'" + "  order by CodeAlias ";
		SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
		sqlbv1.sql(Sql);
		sqlbv1.put("BankCode", BankCode.trim());
		SSRS tSSRS = tExeSQL.execSQL(sqlbv1);
		MaxCodeAlias = tSSRS.GetText(1, 1);

		return MaxCodeAlias;
	}

	/**
	 * 为getCardType解析进行服务
	 * 
	 * @param args
	 *            str1
	 */
	private void StrSplit(String str1) {
		String temp = str1;
		String[] str = temp.split(",");
		_subSplit1(str[0]);
		_subSplit2(str[1]);
		_subSplit3(str[2]);
		_subSplit4(str[3]);
		logger.debug(str[0]);
		logger.debug(str[1]);
		logger.debug(str[2]);
		logger.debug(str[3]);
	}

	public int starNum() {
		return starNum;
	}

	public int sendNum() {
		return sendNum;
	}

	public int startNum() {
		return startNum;
	}

	public int lengthNum() {
		return lengthNum;
	}

	private void _subSplit1(String str) {
		int temp = 0;
		try {
			temp = Integer.parseInt(str);
			starNum = temp;
		} catch (Exception ex) {
			logger.debug("获得起始位置时出错");
		}
	}

	private void _subSplit2(String str) {
		int temp = 0;
		try {
			temp = Integer.parseInt(str);
			sendNum = temp;
		} catch (Exception ex) {
			logger.debug("获得截取长度时出错");
		}
	}

	private void _subSplit3(String str) {
		int temp = 0;
		try {
			temp = Integer.parseInt(str);
			startNum = temp;
		} catch (Exception ex) {
			logger.debug("获得起始位置时出错");
		}
	}

	private void _subSplit4(String str) {
		int temp = 0;
		try {
			temp = Integer.parseInt(str);
			lengthNum = temp;
		} catch (Exception ex) {
			logger.debug("获得截取长度时出错");
		}
	}

	public static String getLocationCode(String BankCode, String AccNo) {
		String LocationCode = "";
		LDCode1DB sLDCode1DB = new LDCode1DB();
		LDCode1Set sLDCode1Set = new LDCode1Set();
		LDCode1Schema sLDCode1Schema = new LDCode1Schema();
		BankCode=BankCode.trim();
		AccNo=AccNo.trim();
		String AccNoHead = "";
		int CTSP = 0;
		int CTSL = 0;
		int LCSP = 0;
		int LCSL = 0;
		String Privilege = getMaxCodeAlias(BankCode);
		if (Privilege.equals("0") || Privilege.equals("")) {
			LocationCode = "0000";
		} else {
			int t = 0;
			t = Integer.parseInt(Privilege);
			for (int i = 0; i <= t; i++) {
				String SQL = " select * from LDCode1 "
						+ "  where CodeType = 'cardtype' " + "    and Code = '"
						+ "?code?" + "'" + "    and CodeAlias = '" + "?CodeAlias?" + "'";
				SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
				sqlbv2.sql(SQL);
				sqlbv2.put("code", BankCode);
				sqlbv2.put("CodeAlias", i);
				sLDCode1Set = sLDCode1DB.executeQuery(sqlbv2);
				logger.debug("SQL===============" + SQL);
				if (sLDCode1Set.size() <= 0) {
					logger.debug("数据库中无相关银行数据。");
					LocationCode = "0000";
				} else {
					for (int j = 1; j <= sLDCode1Set.size(); j++) {
						sLDCode1Schema = sLDCode1Set.get(j);
						String length = sLDCode1Schema.getOtherSign();

						XmlFun tXmlFun = new XmlFun();
						tXmlFun.StrSplit(length);

						CTSP = tXmlFun.starNum();
						logger.debug("起始位置CTSP：" + CTSP);
						CTSL = tXmlFun.sendNum();
						logger.debug("截取长度CTSL：" + CTSL);
						AccNoHead = AccNo.substring(CTSP, CTSL);
						logger.debug("账户头标志AccNoHead============"
								+ AccNoHead);
						if (sLDCode1Schema.getCode1().trim().equals(AccNoHead)) {
							LCSP = tXmlFun.startNum();
							logger.debug("地区代码起始位置LCSP：" + LCSP);
							LCSL = tXmlFun.lengthNum();
							logger.debug("地区代码截取长度LCSL：" + LCSL);
							LocationCode = AccNo.substring(LCSP, LCSL);
							return LocationCode;
						}
					}
				}
			}
		}
		logger.debug("LocationCode=================" + LocationCode);
		if (LocationCode.equals("") || LocationCode.equals(null)
				|| LocationCode.equals("null")) {
			logger.debug("该账号" + AccNo + "不符合规则，请重新核实。");
		}
		return LocationCode;
	}

    /**
     * 根据输入标记为间隔符号，拆分字符串
     * @param strMain
     * @param strDelimiters
     * @param strIndex
     * @return index对应顺序的字符子串
     */
    public static String split(String strMain, String strDelimiters,String strIndex)
    {
        int intIndex = 0;                    //记录分隔符位置，以取出子串
        Vector vResult = new Vector();       //存储子串的数组
        String strSub = "";                  //存放子串的中间变量

        strMain = strMain.trim();

        //若主字符串比分隔符串还要短的话,则返回空字符串
        if (strMain.length() <= strDelimiters.length())
        {
            logger.debug("分隔符串长度大于等于主字符串长度，不能进行拆分！");
            return null;
        }

        //取出第一个分隔符在主串中的位置
        intIndex = strMain.indexOf(strDelimiters);

        //在主串中找不到分隔符
        if (intIndex == -1)
        {
            return "";
        }

        //分割主串到数组中
        while (intIndex != -1)
        {
            strSub = strMain.substring(0, intIndex);
            if (intIndex != 0)
                vResult.add(strSub);
            else
                //break;
                vResult.add("");

            strMain = strMain.substring(intIndex + strDelimiters.length()).trim();
            intIndex = strMain.indexOf(strDelimiters);
        }

        //如果最末不是分隔符，取最后的字符串
        if (!strMain.equals("") && strMain != null)
            vResult.add(strMain);

        int ind = Integer.parseInt(strIndex);
        if (ind < vResult.size())
            return (String)vResult.get(ind);

        return "";
    }
    
    /**
     * 根据传入缴费通知书号码和保单号获取当前发盘的日期
     * @param cPayCode
     * @param cPolNo
     * @return index 对应记录的当前发盘日期
     */
    public static String getSendDate(String cPayCode,String cPayMoney)
    {
        //不允许两个号码都为空
        if(cPayCode.equals("") || cPayMoney.equals(""))
        {
            logger.debug("传入缴费通知书号码或金额为空！");
            return "";
        }

        String sql = "select * from lysendtobank "
                   + "where 1 = 1 "
                   + "?paycode?"
                   + ReportPubFun.getWherePart("paymoney",cPayMoney.trim());
        SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
		sqlbv3.sql(sql);
		sqlbv3.put("paycode", ReportPubFun.getWherePart("paycode",cPayCode.trim()));
        logger.debug(sql);
        LYSendToBankDB tLYSendToBankDB = new LYSendToBankDB();
        LYSendToBankSet tLYSendToBankSet = (LYSendToBankSet) tLYSendToBankDB.executeQuery(sqlbv3);
        if(tLYSendToBankSet.size() > 0)
        {
            logger.debug("senddate : " + tLYSendToBankSet.get(1).getSendDate());
            return tLYSendToBankSet.get(1).getSendDate();
        }
        else
        {
            logger.debug("无法获取当前数据的发盘日期！" );
            return "";
        }
    }
    
	public static void main(String[] args) {
		logger.debug(getLocationCode("2100011", "0200020301020598177"));
		// String temp = "0,6,7,4";
		// XmlFun t = new XmlFun();
		// t.StrSplit(temp);
		// logger.debug(getRiskInfo("86110020030420001150"));
		// 86110020030310003825
	}
}
