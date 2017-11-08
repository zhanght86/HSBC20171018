package com.sinosoft.lis.tb;
import java.lang.reflect.Method;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.BPORiskPlanDB;
import com.sinosoft.lis.schema.BPORiskPlanSchema;
import com.sinosoft.lis.vschema.BPORiskPlanSet;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.SysConst;

/**
 * <p>
 * Title: RiskBasicInfo
 * </p>
 * <p>
 * Description: 险种基本信息
 * </p>
 * <p>
 * Copyright: Copyright (c) 2007
 * </p>
 * <p>
 * Company: SinoSoft
 * </p>
 * 
 * @author Fuqx
 * @version 1.0
 */

public class RiskBasicInfo {
private static Logger logger = Logger.getLogger(RiskBasicInfo.class);
	/** 主险序号 */
	private String mRiskNo;
	/** 险种代码 */
	private String mRiskCode;
	/** 险种名称 */
	private String mRiskName;
	/** 保险费 */
	private double mPrem;
	/** 职业加费 */
	private double mAddPrem;
	/** 投保份数 */
	private int mMult;
	/** 保额 */
	private double mAmnt;
	/** 缴费年期年龄 */
	private int mPayEndYear;
	/** 缴费年期年龄标志 */
	private String mPayEndYearFlag;
	/** 保险年期年龄 */
	private int mInsuYear;
	/** 保险年期年龄标志 */
	private String mInsuYearFlag;
	/** 档次 */
	private String mLevel;
	/** 备注 */
	private String mRemark;
	/** 保险年期年龄(字符型) */
	private String mStrInsuYear;
	/** 缴费年期年龄(字符型) */
	private String mStrPayEndYear;
	/** 领取开始年龄 */
	private int mGetYear;
	/** 投保份数(字符型) */
	private String mStrMult;
	private double mInputPrem;
	public RiskBasicInfo() {
	}

	public void setRiskNo(String tRiskNo) {
		mRiskNo = tRiskNo;
	}

	public String getRiskNo() {
		return mRiskNo;
	}

	public void setRiskCode(String tRiskCode) {
		mRiskCode = tRiskCode;
	}

	public String getRiskCode() {
		return mRiskCode;
	}

	public void setRiskName(String tRiskName) {
		mRiskName = tRiskName;
	}

	public String getRiskName() {
		return mRiskName;
	}

	public void setPrem(double tPrem) {
		mPrem = tPrem;
	}	
	public void setInputPrem(double tInputPrem) {
		mInputPrem = tInputPrem;
	}
	
	public void setPrem(String tPrem) {
		try {
			if (tPrem != null && !"".equals(tPrem)) {
				mPrem = Double.parseDouble(tPrem);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	public void setInputPrem(String tInputPrem) {
		try {
			if (tInputPrem != null && !"".equals(tInputPrem)) {
				mInputPrem = Double.parseDouble(tInputPrem);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	
	public double getPrem() {
		return mPrem;
	}
	public double getInputPrem() {
		return mInputPrem;
	}
	
	public void setAddPrem(double tAddPrem) {
		mAddPrem = tAddPrem;
	}
	
	public void setAddPrem(String tAddPrem) {
		try {
			if (tAddPrem != null && !"".equals(tAddPrem)) {
				mAddPrem = Double.parseDouble(tAddPrem);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public double getAddPrem() {
		return mAddPrem;
	}

	public void setMult(int tMult) {
		mMult = tMult;
	}

	public void setMult(String tMult) {
		if (tMult != null && !tMult.equals("")) {
			Integer tInteger = new Integer(tMult);
			int i = tInteger.intValue();
			mMult = i;
		}
	}

	public int getMult() {
		return mMult;
	}

	public void setAmnt(double tAmnt) {
		mAmnt = tAmnt;
	}

	public void setAmnt(String tAmnt) {
		try {
			if (tAmnt != null && !"".equals(tAmnt)) {
				mAmnt = Double.parseDouble(tAmnt);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public double getAmnt() {
		return mAmnt;
	}

	public void setPayEndYear(int tPayEndYear) {
		mPayEndYear = tPayEndYear;
	}

	public void setPayEndYear(String tPayEndYear) {
		if (tPayEndYear != null && !tPayEndYear.equals("")) {
			Integer tInteger = new Integer(tPayEndYear);
			int i = tInteger.intValue();
			mPayEndYear = i;
		}
	}

	public int getPayEndYear() {
		return mPayEndYear;
	}

	public void setInsuYear(int tInsuYear) {
		mInsuYear = tInsuYear;
	}

	public void setInsuYear(String tInsuYear) {
		if (tInsuYear != null && !tInsuYear.equals("")) {
			Integer tInteger = new Integer(tInsuYear);
			int i = tInteger.intValue();
			mInsuYear = i;
		}
	}

	public int getInsuYear() {
		return mInsuYear;
	}

	public void setPayEndYearFlag(String tPayEndYearFlag) {
		mPayEndYearFlag = tPayEndYearFlag;
	}

	public String getPayEndYearFlag() {
		return mPayEndYearFlag;
	}

	public void setInsuYearFlag(String tInsuYearFlag) {
		mInsuYearFlag = tInsuYearFlag;
	}

	public String getInsuYearFlag() {
		return mInsuYearFlag;
	}

	public void setLevel(String tLevel) {
		mLevel = tLevel;
	}

	public String getLevel() {
		return mLevel;
	}

	public void setRemark(String tRemark) {
		mRemark = tRemark;
	}

	public String getRemark() {
		return mRemark;
	}

	public void setStrInsuYear(String tStrInsuYear) {
		mStrInsuYear = tStrInsuYear;
	}

	public String getStrInsuYear() {
		return mStrInsuYear;
	}

	public void setStrPayEndYear(String tStrPayEndYear) {
		mStrPayEndYear = tStrPayEndYear;
	}

	public String getStrPayEndYear() {
		return mStrPayEndYear;
	}

	public void setGetYear(int tGetYear) {
		mGetYear = tGetYear;
	}

	public void setGetYear(String tGetYear) {
		if (tGetYear != null && !tGetYear.equals("")) {
			Integer tInteger = new Integer(tGetYear);
			int i = tInteger.intValue();
			mGetYear = i;
		}
	}

	public int getGetYear() {
		return mGetYear;
	}

	public void setStrMult(String tStrMult) {
		mStrMult = tStrMult;
	}

	public String getStrMult() {
		return mStrMult;
	}

	/**
	 * 自动转换成份数
	 * 
	 * @return 如果发生异常返回false,否则，返回true;
	 */
	private boolean convertMult() {
		try {
			logger.debug("***RiskCode: " + mRiskCode + "  mStrMult: "
					+ mStrMult);
			if (mStrMult != null && !"".equals(StrTool.cTrim(mStrMult))) {
				int index = mStrMult.indexOf("份");
				if (index != -1) {
					mStrMult = mStrMult.substring(0, index);
				}

				try {
					mMult = Integer.parseInt(mStrMult);
				} catch (Exception ex1) {
				} // 防止转换因格式不对出现异常
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 自动将档次转换成不同的保险要素
	 * 
	 * @return 如果发生异常返回false,否则，返回true;
	 */
	private boolean convertLevel() {
		try {
			logger.debug("***RiskCode: " + mRiskCode + "  mLevel: "
					+ mLevel);
			if (mLevel != null && !"".equals(StrTool.cTrim(mLevel))
					&& !"".equals(StrTool.cTrim(mRiskCode))) {
				Method m;
				Class[] parameterC = new Class[1]; // 调用方法的参数数组
				Object[] parameterO = new Object[1]; // 调用方法的对象数组
				BPORiskPlanSchema tBPORiskPlanSchema = new BPORiskPlanSchema();
				BPORiskPlanSet tBPORiskPlanSet = new BPORiskPlanSet();
				BPORiskPlanDB tBPORiskPlanDB = new BPORiskPlanDB();
				tBPORiskPlanDB.setRiskCode(mRiskCode);
				tBPORiskPlanDB.setPlanName(StrTool.cTrim(mLevel));

				tBPORiskPlanSet = tBPORiskPlanDB.query();
				if (tBPORiskPlanSet != null && tBPORiskPlanSet.size() > 0) {
					for (int i = 1; i <= tBPORiskPlanSet.size(); i++) {
						tBPORiskPlanSchema = tBPORiskPlanSet.get(i);
						if (tBPORiskPlanSchema.getPlanFactor() != null
								&& !"".equals(StrTool.cTrim(tBPORiskPlanSchema
										.getPlanFactor()))) {
							if ("0".equals(tBPORiskPlanSchema.getCalType())) // 如果不需要计算
							{
								parameterC[0] = Class
										.forName("java.lang.String");
								m = this
										.getClass()
										.getMethod(
												"set"
														+ StrTool
																.cTrim(tBPORiskPlanSchema
																		.getPlanFactor()),
												parameterC);
								parameterO[0] = tBPORiskPlanSchema
										.getDefaultValue();
								m.invoke(this, parameterO);
							} else // 需要计算，根据计算编码，去LMCalMode中取算法并计算结果
							{

							}
						}
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 转换保险期间的值，如保险期间初始值为“至60岁”，分解为InsuYear = 60 ,InsuYearFlag=A
	 * 
	 * @return 转换出现异常返回false,否则，返回true;
	 */
	private boolean convertYearValue() {
		try {
			logger.debug("***RiskCode: " + mRiskCode + "  mStrInsuYear: "
					+ mStrInsuYear + "  mStrPayEndYear: " + mStrPayEndYear);
			// 转换保险期间
			if (mStrInsuYear != null && !"".equals(mStrInsuYear)) {
				int index = mStrInsuYear.indexOf("终生");
				int index1 = mStrInsuYear.indexOf("终身");
				if (index != -1 || index1 != -1) // 终生
				{
					mInsuYearFlag = "A";
					mInsuYear = 1000;
				} else {
					index = mStrInsuYear.indexOf("岁");
					if (index != -1) // 如果包含“岁”
					{
						mInsuYearFlag = "A";
						mStrInsuYear = mStrInsuYear.substring(0, index);
						index1 = mStrInsuYear.indexOf("周");
						if (index1 != -1) {
							mStrInsuYear = mStrInsuYear.substring(0, index1);
						}
						index = mStrInsuYear.indexOf("至");
						if (index != -1) // 如果包含“至”，则保险期间取至后边的值,如果不包含，则直接转换
						{
							try {
								mInsuYear = Integer.parseInt(mStrInsuYear
										.substring(index + 1));
							} catch (Exception ex1) {
							} // 防止转换因格式不对出现异常
						} else {
							try {
								mInsuYear = Integer.parseInt(mStrInsuYear);
							} catch (Exception ex1) {
							} // 防止转换因格式不对出现异常
						}
					} else // 如果不包含岁
					{
						index1 = mStrInsuYear.indexOf("保");
						if (index1 != -1) {
							mStrInsuYear = mStrInsuYear.substring(index1 + 1);
						}

						index = mStrInsuYear.indexOf("年");
						if (index != -1) {
							mInsuYearFlag = "Y";
							try {
								mInsuYear = Integer.parseInt(mStrInsuYear
										.substring(0, index));
							} catch (Exception ex1) {
							} // 防止转换因格式不对出现异常
						}
					}
				}
			} // end 转换保险期间

			// 转换交费期间
			if (mStrPayEndYear != null && !"".equals(mStrPayEndYear)) {
				int index = mStrPayEndYear.indexOf("一次交清");
				int index1 = mStrPayEndYear.indexOf("趸");
				if (index != -1 || index1 != -1) // 一次交清 或者 趸交
				{
					mPayEndYearFlag = "A";
					mPayEndYear = 1000;
				} else {
					index = mStrPayEndYear.indexOf("岁");
					if (index != -1) // 如果包含“岁”
					{
						mPayEndYearFlag = "A";
						mStrPayEndYear = mStrPayEndYear.substring(0, index);
						index1 = mStrPayEndYear.indexOf("周");
						if (index1 != -1) {
							mStrPayEndYear = mStrPayEndYear
									.substring(0, index1);
						}
						index = mStrPayEndYear.indexOf("至");
						if (index != -1) // 如果包含“至”，则保险期间取至后边的值,如果不包含，则直接转换
						{
							try {
								mPayEndYear = Integer.parseInt(mStrPayEndYear
										.substring(index + 1));
							} catch (Exception ex1) {
							} // 防止转换因格式不对出现异常
						} else {
							try {
								mPayEndYear = Integer.parseInt(mStrPayEndYear);
							} catch (Exception ex1) {
							} // 防止转换因格式不对出现异常
						}
					} else // 如果不包含岁
					{
						index = mStrPayEndYear.indexOf("年");
						if (index != -1) {
							mPayEndYearFlag = "Y";
							mStrPayEndYear = mStrPayEndYear.substring(0, index);
							index = mStrPayEndYear.indexOf("交");
							index1 = mStrPayEndYear.indexOf("缴");
							if (index != -1) {
								try {
									mPayEndYear = Integer
											.parseInt(mStrPayEndYear
													.substring(index + 1));
								} catch (Exception ex1) {
								} // 防止转换因格式不对出现异常
							} else if (index1 != -1) {
								try {
									mPayEndYear = Integer
											.parseInt(mStrPayEndYear
													.substring(index1 + 1));
								} catch (Exception ex1) {
								} // 防止转换因格式不对出现异常
							} else {
								try {
									mPayEndYear = Integer
											.parseInt(mStrPayEndYear);
								} catch (Exception ex1) {
								} // 防止转换因格式不对出现异常
							}
						}
					}
				}
			} // end 转换交费期间
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 转换传入险种信息的档次，以及保险期间、交费期间等信息
	 * 
	 * @return 转换出现异常返回false,否则，返回true;
	 */
	public boolean convertRiskinfo() {
		try {
			// 转换保险期间等值
			if (!convertYearValue()) {
				return false;
			}

			// 转换档次
			if (!convertLevel()) {
				return false;
			}
			// 转换份数
			if (!convertMult()) {
				return false;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
		return true;
	}

	public String encode() {
		String strReturn = "";
		strReturn = StrTool.cTrim(StrTool.unicodeToGBK(mRiskNo))
				+ SysConst.PACKAGESPILTER
				+ StrTool.cTrim(StrTool.unicodeToGBK(mRiskCode))
				+ SysConst.PACKAGESPILTER
				+ StrTool.cTrim(StrTool.unicodeToGBK(mRiskName))
				+ SysConst.PACKAGESPILTER + mPrem + SysConst.PACKAGESPILTER
				+ mAddPrem + SysConst.PACKAGESPILTER
				+ mMult + SysConst.PACKAGESPILTER + mAmnt
				+ SysConst.PACKAGESPILTER + mPayEndYear
				+ SysConst.PACKAGESPILTER + StrTool.cTrim(mPayEndYearFlag)
				+ SysConst.PACKAGESPILTER + mInsuYear + SysConst.PACKAGESPILTER
				+ StrTool.cTrim(mInsuYearFlag) + SysConst.PACKAGESPILTER;
		return strReturn;
	}

	public boolean isMainRisk() {
		ExeSQL tExeSQL = new ExeSQL();
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		String tSQL = "select trim(SubRiskFlag) from LMRiskApp where RiskCode = '"
				+ "?mRiskCode?" + "'";
		sqlbv.sql(tSQL);
		sqlbv.put("mRiskCode", mRiskCode);
		try {
			if (!"M".equals(tExeSQL.getOneValue(sqlbv)))
				return false;

		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
		return true;
	}

	public static void main(String[] args) {
		RiskBasicInfo riskBasicInfo1 = new RiskBasicInfo();
		// riskBasicInfo1.setRiskCode("111602");
		// riskBasicInfo1.setLevel("计划3");
		// riskBasicInfo1.convertRiskinfo();
		riskBasicInfo1.setStrInsuYear("保88年");
		riskBasicInfo1.setStrPayEndYear("交60年");
		riskBasicInfo1.convertYearValue();
		logger.debug("riskBasicInfo1.encode(): "
				+ riskBasicInfo1.encode());
		// String t = "至88岁";
		// for(int i=0;i<t.length();i++)
		// {
		// logger.debug(t.charAt(i));
		// }

	}
}
