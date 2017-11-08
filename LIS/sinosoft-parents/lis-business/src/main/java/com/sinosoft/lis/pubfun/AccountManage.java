/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.pubfun;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.Date;

import com.sinosoft.lis.bq.BqCalBL;
import com.sinosoft.lis.db.LCInsureAccDB;
import com.sinosoft.lis.db.LCInsureAccTraceDB;
import com.sinosoft.lis.db.LCMedBuyMainDB;
import com.sinosoft.lis.db.LDBankRateDB;
import com.sinosoft.lis.db.LMEdorCalDB;
import com.sinosoft.lis.db.LMRiskInsuAccDB;
import com.sinosoft.lis.schema.LCInsureAccClassSchema;
import com.sinosoft.lis.schema.LCInsureAccSchema;
import com.sinosoft.lis.schema.LCInsureAccTraceSchema;
import com.sinosoft.lis.schema.LDBankRateSchema;
import com.sinosoft.lis.schema.LMRiskInsuAccSchema;
import com.sinosoft.lis.schema.LOBonusPolHisSchema;
import com.sinosoft.lis.schema.LOLoanSchema;
import com.sinosoft.lis.tb.CachedRiskInfo;
import com.sinosoft.lis.vdb.LCInsureAccDBSet;
import com.sinosoft.lis.vdb.LCInsureAccTraceDBSet;
import com.sinosoft.lis.vschema.LCInsureAccSet;
import com.sinosoft.lis.vschema.LCInsureAccTraceSet;
import com.sinosoft.lis.vschema.LCMedBuyMainSet;
import com.sinosoft.lis.vschema.LDBankRateSet;
import com.sinosoft.lis.vschema.LMEdorCalSet;
import com.sinosoft.lis.vschema.LMRiskInsuAccSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.DBConnPool;
import com.sinosoft.utility.DBOper;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Life Information System
 * </p>
 * <p>
 * Description:帐户处理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author unascribed
 * @version 1.0
 */
public class AccountManage {
private static Logger logger = Logger.getLogger(AccountManage.class);

	public CErrors mErrors = new CErrors(); // 错误信息
	LCInsureAccSet mLCInsureAccSet = new LCInsureAccSet();
	LCInsureAccTraceSet mLCInsureAccTraceSet = new LCInsureAccTraceSet();
	private CachedRiskInfo mCRI = CachedRiskInfo.getInstance();

	// 算年利率时有个要素是年度红利，算年度红利时要用到PayEndYear PayIntv
	private String mPayEndYear;
	private String mPayIntv;

	public AccountManage() {
	}

	// 增加构造器。算年利率时有个要素是年度红利，算年度红利时要用到PayEndYear，所以加此构造器
	public AccountManage(String tPayEndYear) {
		this.mPayEndYear = tPayEndYear;
	}

	// 增加接口。算年利率时有个要素是年度红利，算年度红利时要用到PayEndYear
	public void setPayEndYear(String tPayEndYear) {
		this.mPayEndYear = tPayEndYear;
	}

	// 增加接口。算年利率时有个要素是年度红利，算年度红利时要用到PayIntv
	public void setPayIntv(String tPayIntv) {
		this.mPayIntv = tPayIntv;
	}

	/**
	 * 按照保单得到帐户子帐户的余额
	 * 
	 * @param aOtherNo
	 * @param aInsuAccNo
	 * @param aPolNo
	 * @param BalanceDate
	 *            结息日期
	 * @param aRateType
	 *            取得描述帐户利率的时间单位。
	 * @param aIntervalType
	 *            结息的时间单位。
	 * @return
	 */
	public double getAccBalance(String aOtherNo, String aInsuAccNo,
			String aPolNo, String aBalanceDate, String aRateType,
			String aIntervalType) {
		double aAccBalance = 0.0;
		double tAccRate, tCalAccRate;
		int tInterval = 0;
		String tSql = "";
		String iSql = "";
		double tAccInterest = 0.0;

		mLCInsureAccSet.clear();
		mLCInsureAccTraceSet.clear();

		// 得到描述表中帐户利率
		LMRiskInsuAccDB tLMRiskInsuAccDB = new LMRiskInsuAccDB();
		tLMRiskInsuAccDB.setInsuAccNo(aInsuAccNo);
		if (!tLMRiskInsuAccDB.getInfo()) {
			return aAccBalance;
		}

		switch (Integer.parseInt(tLMRiskInsuAccDB.getAccComputeFlag())) {
		// 不计息
		case 0:
			aAccBalance = 0.0;
			break;
		// 按照固定利率单利生息
		case 1:
			tAccRate = tLMRiskInsuAccDB.getAccRate();
			tCalAccRate = TransAccRate(tAccRate, aRateType, "S", aIntervalType);

			// tSql = "select * from lcInsureAcc where PolNo='"+aPolNo+"' and
			// InsuAccNo='"+aInsuAccNo+"'";
			LCInsureAccDB tLCInsureAccDB = new LCInsureAccDB();
			tLCInsureAccDB.setPolNo(aPolNo);
			tLCInsureAccDB.setInsuAccNo(aInsuAccNo);

			// tLCInsureAccDB.setOtherNo(aOtherNo);
			mLCInsureAccSet = tLCInsureAccDB.query();
			for (int i = 1; i <= mLCInsureAccSet.size(); i++) {
				LCInsureAccTraceDB tLCInsureAccTraceDB = new LCInsureAccTraceDB();
				tLCInsureAccTraceDB.setPolNo(aPolNo);
				tLCInsureAccTraceDB.setInsuAccNo(aInsuAccNo);
				// tLCInsureAccTraceDB.setOtherNo(mLCInsureAccSet.get(i).getOtherNo());
				mLCInsureAccTraceSet = tLCInsureAccTraceDB.query();
				for (int j = 1; j <= mLCInsureAccTraceSet.size(); j++) {
					// 得到时间间隔
					tInterval = PubFun.calInterval(mLCInsureAccSet.get(i)
							.getBalaDate(), aBalanceDate, aIntervalType);
					double tInterest = mLCInsureAccTraceSet.get(j).getMoney()
							+ mLCInsureAccTraceSet.get(j).getMoney()
							* getIntvRate(tInterval, tCalAccRate, "S");
					mLCInsureAccTraceSet.get(j).setMoney(tInterest);
					tAccInterest += tInterest;
				}
				mLCInsureAccSet.get(i).setInsuAccBala(tAccInterest);
				mLCInsureAccSet.get(i).setBalaDate(aBalanceDate);
				mLCInsureAccSet.get(i).setBalaTime(PubFun.getCurrentTime());
			}
			break;
		// 按照固定利率复利生息
		case 2:
			tAccRate = tLMRiskInsuAccDB.getAccRate();
			tCalAccRate = TransAccRate(tAccRate, aRateType, "C", aIntervalType);

			// tSql = "select * from lcInsureAcc where PolNo='"+aPolNo+"' and
			// InsuAccNo='"+aInsuAccNo+"'";
			LCInsureAccDB tLCInsureAccDB2 = new LCInsureAccDB();
			tLCInsureAccDB2.setPolNo(aPolNo);
			tLCInsureAccDB2.setInsuAccNo(aInsuAccNo);

			// tLCInsureAccDB2.setOtherNo(aOtherNo);
			mLCInsureAccSet = tLCInsureAccDB2.query();
			for (int i = 1; i <= mLCInsureAccSet.size(); i++) {
				LCInsureAccTraceDB tLCInsureAccTraceDB = new LCInsureAccTraceDB();
				tLCInsureAccTraceDB.setPolNo(aPolNo);
				tLCInsureAccTraceDB.setInsuAccNo(aInsuAccNo);
				// tLCInsureAccTraceDB.setOtherNo(mLCInsureAccSet.get(i).getOtherNo());
				mLCInsureAccTraceSet = tLCInsureAccTraceDB.query();
				for (int j = 1; j <= mLCInsureAccTraceSet.size(); j++) {
					// 得到时间间隔
					tInterval = PubFun.calInterval(mLCInsureAccSet.get(i)
							.getBalaDate(), aBalanceDate, aIntervalType);
					double tInterest = mLCInsureAccTraceSet.get(j).getMoney()
							+ mLCInsureAccTraceSet.get(j).getMoney()
							* getIntvRate(tInterval, tCalAccRate, "C");
					mLCInsureAccTraceSet.get(j).setMoney(tInterest);
					tAccInterest += tInterest;
				}
				mLCInsureAccSet.get(i).setInsuAccBala(tAccInterest);
				mLCInsureAccSet.get(i).setBalaDate(aBalanceDate);
				mLCInsureAccSet.get(i).setBalaTime(PubFun.getCurrentTime());
			}

			break;
		// 按照利率表单利生息
		case 3:

			// tAccRate = this.getAccRate(tLMRiskInsuAccDB.getSchema());
			// tCalAccRate = TransAccRate(tAccRate,aRateType,"S",aIntervalType);
			// tSql = "select * from lcInsureAcc where PolNo='"+aPolNo+"' and
			// InsuAccNo='"+aInsuAccNo+"'";
			LCInsureAccDB tLCInsureAccDB3 = new LCInsureAccDB();
			tLCInsureAccDB3.setPolNo(aPolNo);
			tLCInsureAccDB3.setInsuAccNo(aInsuAccNo);

			// tLCInsureAccDB3.setOtherNo(aOtherNo);
			mLCInsureAccSet = tLCInsureAccDB3.query();
			for (int i = 1; i <= mLCInsureAccSet.size(); i++) {
				LCInsureAccTraceDB tLCInsureAccTraceDB = new LCInsureAccTraceDB();
				tLCInsureAccTraceDB.setPolNo(aPolNo);
				tLCInsureAccTraceDB.setInsuAccNo(aInsuAccNo);
				// tLCInsureAccTraceDB.setOtherNo(mLCInsureAccSet.get(i).getOtherNo());
				mLCInsureAccTraceSet = tLCInsureAccTraceDB.query();
				for (int j = 1; j <= mLCInsureAccTraceSet.size(); j++) {
					double tInterest = 0;
					// 得到分段的单利计算
					String[] ResultRate = getMultiAccRate(aInsuAccNo,
							tLMRiskInsuAccDB.getSchema(), mLCInsureAccSet
									.get(i).getBalaDate(), aBalanceDate,
							aRateType, "S", aIntervalType);
					for (int m = 0; m < ResultRate.length; m++) {

						tCalAccRate = Double.parseDouble(ResultRate[m]);
						double tSubInterest = mLCInsureAccTraceSet.get(j)
								.getMoney()
								* tCalAccRate;
						tInterest += tSubInterest;

					}
					double tBalance = mLCInsureAccTraceSet.get(j).getMoney()
							+ tInterest;
					mLCInsureAccTraceSet.get(j).setMoney(tBalance);
					tAccInterest += tBalance;
				}
				mLCInsureAccSet.get(i).setInsuAccBala(tAccInterest);
				mLCInsureAccSet.get(i).setBalaDate(aBalanceDate);
				mLCInsureAccSet.get(i).setBalaTime(PubFun.getCurrentTime());
			}

			break;
		// 按照利率表复利生息
		case 4:
			tAccRate = AccountManage.getAccRate(tLMRiskInsuAccDB.getSchema());
			tCalAccRate = TransAccRate(tAccRate, aRateType, "C", aIntervalType);

			// tSql = "select * from lcInsureAcc where PolNo='"+aPolNo+"' and
			// InsuAccNo='"+aInsuAccNo+"'";
			LCInsureAccDB tLCInsureAccDB4 = new LCInsureAccDB();
			tLCInsureAccDB4.setPolNo(aPolNo);
			tLCInsureAccDB4.setInsuAccNo(aInsuAccNo);

			// tLCInsureAccDB4.setOtherNo(aOtherNo);
			mLCInsureAccSet = tLCInsureAccDB4.query();
			for (int i = 1; i <= mLCInsureAccSet.size(); i++) {
				LCInsureAccTraceDB tLCInsureAccTraceDB = new LCInsureAccTraceDB();
				tLCInsureAccTraceDB.setPolNo(aPolNo);
				tLCInsureAccTraceDB.setInsuAccNo(aInsuAccNo);
				// tLCInsureAccTraceDB.setOtherNo(mLCInsureAccSet.get(i).getOtherNo());
				mLCInsureAccTraceSet = tLCInsureAccTraceDB.query();
				for (int j = 1; j <= mLCInsureAccTraceSet.size(); j++) {
					double tInterest = 0;
					// 得到分段的单利计算
					String[] ResultRate = getMultiAccRate(aInsuAccNo,
							tLMRiskInsuAccDB.getSchema(), mLCInsureAccSet
									.get(i).getBalaDate(), aBalanceDate,
							aRateType, "C", aIntervalType);
					for (int m = 0; m < ResultRate.length; m++) {
						tCalAccRate = Double.parseDouble(ResultRate[m]);
						double tSubInterest = mLCInsureAccTraceSet.get(j)
								.getMoney()
								* tCalAccRate;
						tInterest += tSubInterest;
					}
					double tBalance = mLCInsureAccTraceSet.get(j).getMoney()
							+ tInterest;
					mLCInsureAccTraceSet.get(j).setMoney(tBalance);
					tAccInterest += tBalance;
				}
				mLCInsureAccSet.get(i).setInsuAccBala(tAccInterest);
				mLCInsureAccSet.get(i).setBalaDate(aBalanceDate);
				mLCInsureAccSet.get(i).setBalaTime(PubFun.getCurrentTime());
			}
			break;
		default:
			aAccBalance = 0.0;
			break;
		}
		aAccBalance = tAccInterest;
		return aAccBalance;
	}
	


	/**
	 * 按照保单得到帐户子帐户的余额
	 * 
	 * @param aOtherNo
	 * @param aInsuAccNo
	 * @param aPolNo
	 * @param BalanceDate
	 *            结息日期
	 * @param aRateType
	 *            取得描述帐户利率的时间单位。
	 * @param aIntervalType
	 *            结息的时间单位。
	 * @return
	 */
	public double getAccBalance(LCInsureAccSchema aLCInsureAccSchema,
			String aBalanceDate, String aRateType, String aIntervalType) {
		double aAccBalance = 0.0;
		double tAccRate, tCalAccRate;
		int tInterval = 0;
		String tSql = "";
		String iSql = "";
		double tAccInterest = 0.0;

		mLCInsureAccSet.clear();
		mLCInsureAccTraceSet.clear();

		// 得到描述表中帐户利率
		LMRiskInsuAccDB tLMRiskInsuAccDB = new LMRiskInsuAccDB();
		tLMRiskInsuAccDB.setInsuAccNo(aLCInsureAccSchema.getInsuAccNo());
		if (!tLMRiskInsuAccDB.getInfo()) {
			return aAccBalance;
		}

		switch (Integer.parseInt(tLMRiskInsuAccDB.getAccComputeFlag())) {
		// 不计息
		case 0:
			aAccBalance = 0.0;
			break;
		// 按照固定利率单利生息
		case 1:
			tAccRate = tLMRiskInsuAccDB.getAccRate();
			tCalAccRate = TransAccRate(tAccRate, aRateType, "S", aIntervalType);

			LCInsureAccDB tLCInsureAccDB = new LCInsureAccDB();
			tLCInsureAccDB.setPolNo(aLCInsureAccSchema.getPolNo());
			tLCInsureAccDB.setInsuAccNo(aLCInsureAccSchema.getInsuAccNo());

			// tLCInsureAccDB.setOtherNo(aLCInsureAccSchema.getOtherNo());
			mLCInsureAccSet = tLCInsureAccDB.query();
			for (int i = 1; i <= mLCInsureAccSet.size(); i++) {
				LCInsureAccTraceDB tLCInsureAccTraceDB = new LCInsureAccTraceDB();
				tLCInsureAccTraceDB.setPolNo(aLCInsureAccSchema.getPolNo());
				tLCInsureAccTraceDB.setInsuAccNo(aLCInsureAccSchema
						.getInsuAccNo());
				// tLCInsureAccTraceDB.setOtherNo(mLCInsureAccSet.get(i).getOtherNo());
				mLCInsureAccTraceSet = tLCInsureAccTraceDB.query();
				for (int j = 1; j <= mLCInsureAccTraceSet.size(); j++) {
					// 得到时间间隔
					tInterval = PubFun.calInterval(mLCInsureAccSet.get(i)
							.getBalaDate(), aLCInsureAccSchema.getBalaDate(),
							aIntervalType);
					double tInterest = mLCInsureAccTraceSet.get(j).getMoney()
							+ mLCInsureAccTraceSet.get(j).getMoney()
							* getIntvRate(tInterval, tCalAccRate, "S");
					mLCInsureAccTraceSet.get(j).setMoney(tInterest);
					tAccInterest += tInterest;
				}
				mLCInsureAccSet.get(i).setInsuAccBala(tAccInterest);
				mLCInsureAccSet.get(i).setBalaDate(aBalanceDate);
				mLCInsureAccSet.get(i).setBalaTime(PubFun.getCurrentTime());
			}
			break;
		// 按照固定利率复利生息
		case 2:
			tAccRate = tLMRiskInsuAccDB.getAccRate();
			tCalAccRate = TransAccRate(tAccRate, aRateType, "C", aIntervalType);

			// tSql = "select * from lcInsureAcc where PolNo='"+aPolNo+"' and
			// InsuAccNo='"+aInsuAccNo+"'";
			LCInsureAccDB tLCInsureAccDB2 = new LCInsureAccDB();
			tLCInsureAccDB2.setPolNo(aLCInsureAccSchema.getPolNo());
			tLCInsureAccDB2.setInsuAccNo(aLCInsureAccSchema.getInsuAccNo());

			// tLCInsureAccDB2.setOtherNo(aLCInsureAccSchema.getOtherNo());
			mLCInsureAccSet = tLCInsureAccDB2.query();
			for (int i = 1; i <= mLCInsureAccSet.size(); i++) {
				LCInsureAccTraceDB tLCInsureAccTraceDB = new LCInsureAccTraceDB();
				tLCInsureAccTraceDB.setPolNo(aLCInsureAccSchema.getPolNo());
				tLCInsureAccTraceDB.setInsuAccNo(aLCInsureAccSchema
						.getInsuAccNo());
				// tLCInsureAccTraceDB.setOtherNo(mLCInsureAccSet.get(i).getOtherNo());
				mLCInsureAccTraceSet = tLCInsureAccTraceDB.query();
				for (int j = 1; j <= mLCInsureAccTraceSet.size(); j++) {
					// 得到时间间隔
					tInterval = PubFun.calInterval(mLCInsureAccSet.get(i)
							.getBalaDate(), aBalanceDate, aIntervalType);
					double tInterest = mLCInsureAccTraceSet.get(j).getMoney()
							+ mLCInsureAccTraceSet.get(j).getMoney()
							* getIntvRate(tInterval, tCalAccRate, "C");
					mLCInsureAccTraceSet.get(j).setMoney(tInterest);
					tAccInterest += tInterest;
				}
				mLCInsureAccSet.get(i).setInsuAccBala(tAccInterest);
				mLCInsureAccSet.get(i).setBalaDate(aBalanceDate);
				mLCInsureAccSet.get(i).setBalaTime(PubFun.getCurrentTime());
			}

			break;
		// 按照利率表单利生息
		case 3:
			tAccRate = AccountManage.getAccRate(tLMRiskInsuAccDB.getSchema());
			tCalAccRate = TransAccRate(tAccRate, aRateType, "S", aIntervalType);

			// tSql = "select * from lcInsureAcc where PolNo='"+aPolNo+"' and
			// InsuAccNo='"+aInsuAccNo+"'";
			LCInsureAccDB tLCInsureAccDB3 = new LCInsureAccDB();
			tLCInsureAccDB3.setPolNo(aLCInsureAccSchema.getPolNo());
			tLCInsureAccDB3.setInsuAccNo(aLCInsureAccSchema.getInsuAccNo());

			// tLCInsureAccDB3.setOtherNo(aLCInsureAccSchema.getOtherNo());
			mLCInsureAccSet = tLCInsureAccDB3.query();
			for (int i = 1; i <= mLCInsureAccSet.size(); i++) {
				LCInsureAccTraceDB tLCInsureAccTraceDB = new LCInsureAccTraceDB();
				tLCInsureAccTraceDB.setPolNo(aLCInsureAccSchema.getPolNo());
				tLCInsureAccTraceDB.setInsuAccNo(aLCInsureAccSchema
						.getInsuAccNo());
				// tLCInsureAccTraceDB.setOtherNo(mLCInsureAccSet.get(i).getOtherNo());
				mLCInsureAccTraceSet = tLCInsureAccTraceDB.query();
				for (int j = 1; j <= mLCInsureAccTraceSet.size(); j++) {
					double tInterest = 0;
					// 得到分段的单利计算
					String[] ResultRate = getMultiAccRate(aLCInsureAccSchema
							.getInsuAccNo(), tLMRiskInsuAccDB.getSchema(),
							mLCInsureAccSet.get(i).getBalaDate(), aBalanceDate,
							aRateType, "S", aIntervalType);
					for (int m = 0; m < ResultRate.length; m++) {
						tCalAccRate = Double.parseDouble(ResultRate[m]);
						double tSubInterest = mLCInsureAccTraceSet.get(j)
								.getMoney()
								* tCalAccRate;
						tInterest += tSubInterest;
					}
					double tBalance = mLCInsureAccTraceSet.get(j).getMoney()
							+ tInterest;
					mLCInsureAccTraceSet.get(j).setMoney(tBalance);
					tAccInterest += tBalance;
				}
				mLCInsureAccSet.get(i).setInsuAccBala(tAccInterest);
				mLCInsureAccSet.get(i).setBalaDate(aBalanceDate);
				mLCInsureAccSet.get(i).setBalaTime(PubFun.getCurrentTime());
			}
			break;
		// 按照利率表复利生息
		case 4:
			tAccRate = AccountManage.getAccRate(tLMRiskInsuAccDB.getSchema());
			tCalAccRate = TransAccRate(tAccRate, aRateType, "C", aIntervalType);

			// tSql = "select * from lcInsureAcc where PolNo='"+aPolNo+"' and
			// InsuAccNo='"+aInsuAccNo+"'";
			LCInsureAccDB tLCInsureAccDB4 = new LCInsureAccDB();
			tLCInsureAccDB4.setPolNo(aLCInsureAccSchema.getPolNo());
			tLCInsureAccDB4.setInsuAccNo(aLCInsureAccSchema.getInsuAccNo());

			// tLCInsureAccDB4.setOtherNo(aLCInsureAccSchema.getOtherNo());
			mLCInsureAccSet = tLCInsureAccDB4.query();
			for (int i = 1; i <= mLCInsureAccSet.size(); i++) {
				LCInsureAccTraceDB tLCInsureAccTraceDB = new LCInsureAccTraceDB();
				tLCInsureAccTraceDB.setPolNo(aLCInsureAccSchema.getPolNo());
				tLCInsureAccTraceDB.setInsuAccNo(aLCInsureAccSchema
						.getInsuAccNo());
				// tLCInsureAccTraceDB.setOtherNo(mLCInsureAccSet.get(i).getOtherNo());
				mLCInsureAccTraceSet = tLCInsureAccTraceDB.query();
				for (int j = 1; j <= mLCInsureAccTraceSet.size(); j++) {
					double tInterest = 0;
					// 得到分段的单利计算
					String[] ResultRate = getMultiAccRate(aLCInsureAccSchema
							.getInsuAccNo(), tLMRiskInsuAccDB.getSchema(),
							mLCInsureAccSet.get(i).getBalaDate(), aBalanceDate,
							aRateType, "C", aIntervalType);
					for (int m = 0; m < ResultRate.length; m++) {
						if (!(ResultRate[m] == null || ResultRate[m].equals(""))) {
							tCalAccRate = Double.parseDouble(ResultRate[m]);
							logger.debug("当m的值是" + m + "时，值是"
									+ tCalAccRate);
							double tSubInterest = mLCInsureAccTraceSet.get(j)
									.getMoney()
									* tCalAccRate;
							logger.debug("当m的值是" + m + "时，利息是"
									+ tSubInterest);
							tInterest += tSubInterest;
						}
					}
					double tBalance = mLCInsureAccTraceSet.get(j).getMoney()
							+ tInterest;
					mLCInsureAccTraceSet.get(j).setMoney(tBalance);
					tAccInterest += tBalance;
				}
				mLCInsureAccSet.get(i).setInsuAccBala(tAccInterest);
				mLCInsureAccSet.get(i).setBalaDate(aBalanceDate);
				mLCInsureAccSet.get(i).setBalaTime(PubFun.getCurrentTime());
			}
			break;
		default:
			aAccBalance = 0.0;
			break;
		}
		aAccBalance = tAccInterest;
		return aAccBalance;
	}

	/**
	 * 按照保单得到某个帐户的余额
	 * 
	 * @param aInsuAccNo
	 * @param aPolNo
	 * @param BalanceDate
	 *            结息日期
	 * @param aRateType
	 *            取得描述帐户利率的时间单位。
	 * @param aIntervalType
	 *            结息的时间单位。
	 * @return
	 */
	public double getAccBalance(String aInsuAccNo, String aPolNo,
			String aBalanceDate, String aRateType, String aIntervalType) {
		double aAccBalance = 0.0;
		double tAccRate, tCalAccRate;
		int tInterval = 0;
		String tSql = "";
		String iSql = "";
		double tAccInterest = 0.0;
		mLCInsureAccSet.clear();
		mLCInsureAccTraceSet.clear();

		// 得到描述表中帐户利率
		LMRiskInsuAccDB tLMRiskInsuAccDB = new LMRiskInsuAccDB();
		tLMRiskInsuAccDB.setInsuAccNo(aInsuAccNo);
		if (!tLMRiskInsuAccDB.getInfo()) {
			return aAccBalance;
		}

		switch (Integer.parseInt(tLMRiskInsuAccDB.getAccComputeFlag())) {
		// 不计息
		case 0:
			aAccBalance = 0.0;
			break;
		// 按照固定利率单利生息
		case 1:
			tAccRate = tLMRiskInsuAccDB.getAccRate();
			tCalAccRate = TransAccRate(tAccRate, aRateType, "S", aIntervalType);

			// tSql = "select * from lcInsureAcc where PolNo='"+aPolNo+"' and
			// InsuAccNo='"+aInsuAccNo+"'";
			LCInsureAccDB tLCInsureAccDB = new LCInsureAccDB();
			tLCInsureAccDB.setPolNo(aPolNo);
			tLCInsureAccDB.setInsuAccNo(aInsuAccNo);
			mLCInsureAccSet = tLCInsureAccDB.query();
			for (int i = 1; i <= mLCInsureAccSet.size(); i++) {
				LCInsureAccTraceDB tLCInsureAccTraceDB = new LCInsureAccTraceDB();
				tLCInsureAccTraceDB.setPolNo(aPolNo);
				tLCInsureAccTraceDB.setInsuAccNo(aInsuAccNo);
				// tLCInsureAccTraceDB.setOtherNo(mLCInsureAccSet.get(i).getOtherNo());
				mLCInsureAccTraceSet = tLCInsureAccTraceDB.query();
				for (int j = 1; j <= mLCInsureAccTraceSet.size(); j++) {
					// 得到时间间隔
					tInterval = PubFun.calInterval(mLCInsureAccSet.get(i)
							.getBalaDate(), aBalanceDate, aIntervalType);
					double tInterest = mLCInsureAccTraceSet.get(j).getMoney()
							+ mLCInsureAccTraceSet.get(j).getMoney()
							* getIntvRate(tInterval, tCalAccRate, "S");
					mLCInsureAccTraceSet.get(j).setMoney(tInterest);
					tAccInterest += tInterest;
				}
				mLCInsureAccSet.get(i).setInsuAccBala(tAccInterest);
				mLCInsureAccSet.get(i).setBalaDate(aBalanceDate);
				mLCInsureAccSet.get(i).setBalaTime(PubFun.getCurrentTime());
			}
			break;
		// 按照固定利率复利生息
		case 2:
			tAccRate = tLMRiskInsuAccDB.getAccRate();
			tCalAccRate = TransAccRate(tAccRate, aRateType, "C", aIntervalType);

			// tSql = "select * from lcInsureAcc where PolNo='"+aPolNo+"' and
			// InsuAccNo='"+aInsuAccNo+"'";
			LCInsureAccDB tLCInsureAccDB2 = new LCInsureAccDB();
			tLCInsureAccDB2.setPolNo(aPolNo);
			tLCInsureAccDB2.setInsuAccNo(aInsuAccNo);
			mLCInsureAccSet = tLCInsureAccDB2.query();
			for (int i = 1; i <= mLCInsureAccSet.size(); i++) {
				LCInsureAccTraceDB tLCInsureAccTraceDB = new LCInsureAccTraceDB();
				tLCInsureAccTraceDB.setPolNo(aPolNo);
				tLCInsureAccTraceDB.setInsuAccNo(aInsuAccNo);
				// tLCInsureAccTraceDB.setOtherNo(mLCInsureAccSet.get(i).getOtherNo());
				mLCInsureAccTraceSet = tLCInsureAccTraceDB.query();
				for (int j = 1; j <= mLCInsureAccTraceSet.size(); j++) {
					// 得到时间间隔
					tInterval = PubFun.calInterval(mLCInsureAccSet.get(i)
							.getBalaDate(), aBalanceDate, aIntervalType);
					double tInterest = mLCInsureAccTraceSet.get(j).getMoney()
							+ mLCInsureAccTraceSet.get(j).getMoney()
							* getIntvRate(tInterval, tCalAccRate, "C");
					mLCInsureAccTraceSet.get(j).setMoney(tInterest);
					tAccInterest += tInterest;
				}
				mLCInsureAccSet.get(i).setInsuAccBala(tAccInterest);
				mLCInsureAccSet.get(i).setBalaDate(aBalanceDate);
				mLCInsureAccSet.get(i).setBalaTime(PubFun.getCurrentTime());
			}

			break;
		// 按照利率表单利生息
		case 3:
			tAccRate = AccountManage.getAccRate(tLMRiskInsuAccDB.getSchema());
			tCalAccRate = TransAccRate(tAccRate, aRateType, "S", aIntervalType);

			// tSql = "select * from lcInsureAcc where PolNo='"+aPolNo+"' and
			// InsuAccNo='"+aInsuAccNo+"'";
			LCInsureAccDB tLCInsureAccDB3 = new LCInsureAccDB();
			tLCInsureAccDB3.setPolNo(aPolNo);
			tLCInsureAccDB3.setInsuAccNo(aInsuAccNo);
			mLCInsureAccSet = tLCInsureAccDB3.query();
			for (int i = 1; i <= mLCInsureAccSet.size(); i++) {
				LCInsureAccTraceDB tLCInsureAccTraceDB = new LCInsureAccTraceDB();
				tLCInsureAccTraceDB.setPolNo(aPolNo);
				tLCInsureAccTraceDB.setInsuAccNo(aInsuAccNo);
				// tLCInsureAccTraceDB.setOtherNo(mLCInsureAccSet.get(i).getOtherNo());
				mLCInsureAccTraceSet = tLCInsureAccTraceDB.query();
				for (int j = 1; j <= mLCInsureAccTraceSet.size(); j++) {
					double tInterest = 0;
					// 得到分段的单利计算
					String[] ResultRate = getMultiAccRate(aInsuAccNo,
							tLMRiskInsuAccDB.getSchema(), mLCInsureAccSet
									.get(i).getBalaDate(), aBalanceDate,
							aRateType, "S", aIntervalType);
					for (int m = 0; m < ResultRate.length; m++) {
						tCalAccRate = Double.parseDouble(ResultRate[m]);
						double tSubInterest = mLCInsureAccTraceSet.get(j)
								.getMoney()
								* tCalAccRate;
						tInterest += tSubInterest;
					}
					double tBalance = mLCInsureAccTraceSet.get(j).getMoney()
							+ tInterest;
					mLCInsureAccTraceSet.get(j).setMoney(tBalance);
					tAccInterest += tBalance;
				}
				mLCInsureAccSet.get(i).setInsuAccBala(tAccInterest);
				mLCInsureAccSet.get(i).setBalaDate(aBalanceDate);
				mLCInsureAccSet.get(i).setBalaTime(PubFun.getCurrentTime());
			}

			break;
		// 按照利率表复利生息
		case 4:
			tAccRate = AccountManage.getAccRate(tLMRiskInsuAccDB.getSchema());
			tCalAccRate = TransAccRate(tAccRate, aRateType, "C", aIntervalType);

			// tSql = "select * from lcInsureAcc where PolNo='"+aPolNo+"' and
			// InsuAccNo='"+aInsuAccNo+"'";
			LCInsureAccDB tLCInsureAccDB4 = new LCInsureAccDB();
			tLCInsureAccDB4.setPolNo(aPolNo);
			tLCInsureAccDB4.setInsuAccNo(aInsuAccNo);
			mLCInsureAccSet = tLCInsureAccDB4.query();
			for (int i = 1; i <= mLCInsureAccSet.size(); i++) {
				LCInsureAccTraceDB tLCInsureAccTraceDB = new LCInsureAccTraceDB();
				tLCInsureAccTraceDB.setPolNo(aPolNo);
				tLCInsureAccTraceDB.setInsuAccNo(aInsuAccNo);
				// tLCInsureAccTraceDB.setOtherNo(mLCInsureAccSet.get(i).getOtherNo());
				mLCInsureAccTraceSet = tLCInsureAccTraceDB.query();
				for (int j = 1; j <= mLCInsureAccTraceSet.size(); j++) {
					double tInterest = 0;
					// 得到分段的单利计算
					String[] ResultRate = getMultiAccRate(aInsuAccNo,
							tLMRiskInsuAccDB.getSchema(), mLCInsureAccSet
									.get(i).getBalaDate(), aBalanceDate,
							aRateType, "C", aIntervalType); // modify by sxy
															// 2004-05-19
					// String[] ResultRate =
					// getMultiAccRate(aInsuAccNo,tLMRiskInsuAccDB.getSchema(),mLCInsureAccTraceSet.get(j).getMakeDate(),aBalanceDate,aRateType,"C",aIntervalType);

					for (int m = 0; m < ResultRate.length; m++) {
						if (ResultRate[m] != null
								&& !ResultRate[m].trim().equals("")) {
							tCalAccRate = Double.parseDouble(ResultRate[m]);
							double tSubInterest = mLCInsureAccTraceSet.get(j)
									.getMoney()
									* tCalAccRate;
							tInterest += tSubInterest;
						}
					}
					double tBalance = mLCInsureAccTraceSet.get(j).getMoney()
							+ tInterest;
					mLCInsureAccTraceSet.get(j).setMoney(tBalance);
					tAccInterest += tBalance;
				}
				mLCInsureAccSet.get(i).setInsuAccBala(tAccInterest);
				mLCInsureAccSet.get(i).setBalaDate(aBalanceDate);
				mLCInsureAccSet.get(i).setBalaTime(PubFun.getCurrentTime());
			}
			break;
		default:
			aAccBalance = 0.0;
			break;
		}
		aAccBalance = tAccInterest;
		return aAccBalance;
	}

	/**
	 * 按照本金从起始时间到结束时间的利息
	 * 
	 * @param aInsuAccNo
	 * @param aBalance
	 * @param aStartDate
	 * @param aBalanceDate
	 *            结息日期
	 * @param aRateType
	 *            取得描述帐户利率的时间单位。
	 * @param aIntervalType
	 *            结息的时间单位。
	 * @return
	 */
	public double getInterestNew(String aInsuAccNo, double aBalance,
			String aStartDate, String aBalanceDate, String aRateType,
			String aIntervalType, int Period, String tType, String Depst) {
		double tAccRate, tCalAccRate;
		int tInterval = 0;

		double tInterest = 0.0;
		// 得到描述表中帐户利率
		LMRiskInsuAccSchema tLMRiskInsuAccSchema = new LMRiskInsuAccSchema();
		tLMRiskInsuAccSchema = mCRI.findRiskInsuAccByInsuAccNo(aInsuAccNo);
		switch (Integer.parseInt(tLMRiskInsuAccSchema.getAccComputeFlag())) {
		// 不计息
		case 0:
			tInterest = 0.0;
			break;
		// 按照固定利率单利生息
		case 1:
			tAccRate = tLMRiskInsuAccSchema.getAccRate();
			tCalAccRate = TransAccRate(tAccRate, aRateType, "S", aIntervalType);

			// // 对于aStartDate>aBalanceDate的情况，tInterval有问题，反之没有问题
			// //得到时间间隔
			// if (aStartDate.compareTo(aBalanceDate)>0)
			// {
			// tInterval = PubFun.calInterval(aBalanceDate, aStartDate,
			// aIntervalType);
			// tInterval=0-tInterval;
			// }
			// else
			// {
			tInterval = PubFun.calInterval(aStartDate, aBalanceDate,
					aIntervalType);

			// }

			tInterest = aBalance * getIntvRate(tInterval, tCalAccRate, "S");

			break;
		// 按照固定利率复利生息
		case 2:
			tAccRate = tLMRiskInsuAccSchema.getAccRate();
			tCalAccRate = TransAccRate(tAccRate, aRateType, "C", aIntervalType);

			// 对于aStartDate>aBalanceDate的情况，tInterval有问题，反之没有问题
			// 得到时间间隔
			// if (aStartDate.compareTo(aBalanceDate)>0)
			// {
			// tInterval = PubFun.calInterval(aBalanceDate, aStartDate,
			// aIntervalType);
			// tInterval=0-tInterval;
			// }
			// else
			// {
			tInterval = PubFun.calInterval(aStartDate, aBalanceDate,
					aIntervalType);

			// }
			tInterest = aBalance * getIntvRate(tInterval, tCalAccRate, "C");

			break;
		// 按照利率表单利生息
		case 3:
			String[] ResultRate1;

			// 得到分段的单利计算
			// 对轨迹表的这笔钱tempMoney作结息
			// 对于aStartDate>aBalanceDate的情况，tInterval有问题，反之没有问题
			// 得到时间间隔
			// if (aStartDate.compareTo(aBalanceDate)>0)
			// {
			// ResultRate1 = getMultiAccRateNew(aInsuAccNo,
			// tLMRiskInsuAccSchema, aBalanceDate, aStartDate,
			// aRateType, "S", aIntervalType, Period, tType, Depst);
			// }
			// else
			// {
			ResultRate1 = getMultiAccRateNew(aInsuAccNo, tLMRiskInsuAccSchema,
					aStartDate, aBalanceDate, aRateType, "S", aIntervalType,
					Period, tType, Depst);

			// }
			// 记录ResultRate1[]的有效数组长度
			int tCount1 = Integer.parseInt(ResultRate1[0]);
			for (int m = 1; m < tCount1 + 1; m++) {
				if (ResultRate1[m] == null) {
					ResultRate1[m] = "0";
				}
				double tCalAccClassRate = Double.parseDouble(ResultRate1[m]);
				double tSubInterest = aBalance * tCalAccClassRate;
				tInterest += tSubInterest;
			}

			break;
		// 按照利率表复利生息
		case 4:

			// 得到分段的单利计算
			// 对轨迹表的这笔钱tempMoney作结息
			// 对于aStartDate>aBalanceDate的情况，tInterval有问题，反之没有问题
			// 得到时间间隔
			String[] ResultRate2;

			// if (aStartDate.compareTo(aBalanceDate)>0)
			// {
			// ResultRate2 = getMultiAccRateNew(aInsuAccNo,
			// tLMRiskInsuAccSchema, aBalanceDate, aStartDate,
			// aRateType, "C", aIntervalType, Period, tType, Depst);
			// }
			// else
			// {
			ResultRate2 = getMultiAccRateNew(aInsuAccNo, tLMRiskInsuAccSchema,
					aStartDate, aBalanceDate, aRateType, "C", aIntervalType,
					Period, tType, Depst);

			// }

			// 记录ResultRate2[]的有效数组长度
			int tCount2 = Integer.parseInt(ResultRate2[0]);
			for (int m = 1; m < tCount2 + 1; m++) {
				if (ResultRate2[m] == null) {
					ResultRate2[m] = "0";
				}
				double tCalAccClassRate = Double.parseDouble(ResultRate2[m]);
				double tSubInterest = aBalance * tCalAccClassRate;
				tInterest += tSubInterest;
			}
			break;
		default:
			tInterest = 0.0;
			break;
		}

		return tInterest;
	}

	/**
	 * 按照保单得到账户型险种某个帐户到结息时点的余额
	 * 
	 * @param aInsuAccNo
	 * @param aPolNo
	 * @param aPayPlanCode
	 * @param aBalanceDate
	 *            结息日期
	 * @param aRateType
	 *            取得描述帐户利率的时间单位。
	 * @param aIntervalType
	 *            结息的时间单位。
	 * @return
	 */

	public double getAccBalanceNew(String aInsuAccNo, String aPolNo,
			String aPayPlanCode, String aBalanceDate, String aRateType,
			String aIntervalType, int Period, String tType, String Depst)

	{
		double aAccBalance = 0.0;
		double tAccRate, tCalAccRate;
		int tInterval = 0;
		String tSql = "";
		String iSql = "";
		double tAccInterest = 0.0;

		double tInterest = 0.0;

		LCInsureAccTraceDB tLCInsureAccTraceDB = new LCInsureAccTraceDB();
		LCInsureAccTraceSet tLCInsureAccTraceSet = new LCInsureAccTraceSet();
		LCInsureAccTraceSchema tLCInsureAccTraceSchema = new LCInsureAccTraceSchema();

		// 得到描述表中帐户利率
		LMRiskInsuAccSchema tLMRiskInsuAccSchema = new LMRiskInsuAccSchema();
		tLMRiskInsuAccSchema = mCRI.findRiskInsuAccByInsuAccNo(aInsuAccNo);

		switch (Integer.parseInt(tLMRiskInsuAccSchema.getAccComputeFlag())) {
		// 不计息
		case 0:
			aAccBalance = 0.0;
			break;
		// 按照固定利率单利生息
		case 1:
			tAccRate = tLMRiskInsuAccSchema.getAccRate();
			tCalAccRate = TransAccRate(tAccRate, aRateType, "S", aIntervalType);

			tSql = "select * from lcinsureacctrace where polno='" + "?d1?"
					+ "' and insuaccno='" + "?d2?" + "' and payplancode='"
					+ "?d3?" + "' and paydate<='" + "?d4?" + "'";
			  SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
			    sqlbv1.sql(tSql);
			    sqlbv1.put("d1",aPolNo);
			    sqlbv1.put("d2",aInsuAccNo);
			    sqlbv1.put("d3",aPayPlanCode);
			    sqlbv1.put("d4",aBalanceDate);
			tLCInsureAccTraceSet = tLCInsureAccTraceDB.executeQuery(sqlbv1);
			for (int n = 1; n <= tLCInsureAccTraceSet.size(); n++) {
				tLCInsureAccTraceSchema = tLCInsureAccTraceSet.get(n);
				// 得到时间间隔
				tInterval = PubFun.calInterval(tLCInsureAccTraceSchema
						.getPayDate(), aBalanceDate, aIntervalType);
				tInterest = tLCInsureAccTraceSchema.getMoney()
						+ tLCInsureAccTraceSchema.getMoney()
						* getIntvRate(tInterval, tCalAccRate, "S");

				aAccBalance += tInterest;
			}
			break;
		// 按照固定利率复利生息
		case 2:
			tAccRate = tLMRiskInsuAccSchema.getAccRate();
			tCalAccRate = TransAccRate(tAccRate, aRateType, "C", aIntervalType);

			tSql = "select * from lcinsureacctrace where polno='" + "?w1?"
					+ "' and insuaccno='" + "?w2?" + "' and payplancode='"
					+ "?w3?" + "' and paydate<='" + "?w4?" + "'";
			 SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
			    sqlbv2.sql(tSql);
			    sqlbv2.put("w1",aPolNo);
			    sqlbv2.put("w2",aInsuAccNo);
			    sqlbv2.put("w3",aPayPlanCode);
			    sqlbv2.put("w4",aBalanceDate);
			tLCInsureAccTraceSet = tLCInsureAccTraceDB.executeQuery(sqlbv2);
			for (int n = 1; n <= tLCInsureAccTraceSet.size(); n++) {
				tLCInsureAccTraceSchema = tLCInsureAccTraceSet.get(n);
				// 得到时间间隔
				tInterval = PubFun.calInterval(tLCInsureAccTraceSchema
						.getPayDate(), aBalanceDate, aIntervalType);
				tInterest = tLCInsureAccTraceSchema.getMoney()
						+ tLCInsureAccTraceSchema.getMoney()
						* getIntvRate(tInterval, tCalAccRate, "C");

				aAccBalance += tInterest;
			}
			break;
		// 按照利率表单利生息
		case 3:
			tSql = "select * from lcinsureacctrace where polno='" + "?e1?"
					+ "' and insuaccno='" + "?e2?" + "' and payplancode='"
					+ "?e3?" + "' and paydate<='" + "?e4?" + "'";
			 SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
			    sqlbv3.sql(tSql);
			    sqlbv3.put("e1",aPolNo);
			    sqlbv3.put("e2",aInsuAccNo);
			    sqlbv3.put("e3",aPayPlanCode);
			    sqlbv3.put("e4",aBalanceDate);
			tLCInsureAccTraceSet = tLCInsureAccTraceDB.executeQuery(sqlbv3);
			for (int n = 1; n <= tLCInsureAccTraceSet.size(); n++) {
				tLCInsureAccTraceSchema = tLCInsureAccTraceSet.get(n);
				tInterest = 0;
				// 得到分段的单利计算
				// 对轨迹表的这笔钱tempMoney作结息
				String[] ResultRate2 = getMultiAccRateNew(aInsuAccNo,
						tLMRiskInsuAccSchema, tLCInsureAccTraceSchema
								.getPayDate(), aBalanceDate, aRateType, "S",
						aIntervalType, Period, tType, Depst);

				// 记录ResultRate2[]的有效数组长度
				int tCount = Integer.parseInt(ResultRate2[0]);
				for (int m = 1; m < tCount + 1; m++) {
					if (ResultRate2[m] == null) {
						ResultRate2[m] = "0";
					}
					double tCalAccClassRate = Double
							.parseDouble(ResultRate2[m]);
					double tSubInterest = tLCInsureAccTraceSchema.getMoney()
							* tCalAccClassRate;
					tInterest += tSubInterest;
				}
				double tBalance = tLCInsureAccTraceSchema.getMoney()
						+ tInterest;
				aAccBalance += tBalance;
			}
			break;
		// 按照利率表复利生息
		case 4:

			tSql = "select * from lcinsureacctrace where polno='" + "?a1?"
					+ "' and insuaccno='" + "?a2?" + "' and payplancode='"
					+ "?a3?" + "' and paydate<='" + "?a4?" + "'";
			 SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
			    sqlbv4.sql(tSql);
			    sqlbv4.put("a1",aPolNo);
			    sqlbv4.put("a2",aInsuAccNo);
			    sqlbv4.put("a3",aPayPlanCode);
			    sqlbv4.put("a4",aBalanceDate);
			tLCInsureAccTraceSet = tLCInsureAccTraceDB.executeQuery(sqlbv4);
			for (int n = 1; n <= tLCInsureAccTraceSet.size(); n++) {
				tLCInsureAccTraceSchema = tLCInsureAccTraceSet.get(n);
				tInterest = 0;
				// 得到分段的单利计算
				// 对轨迹表的这笔钱tempMoney作结息
				String[] ResultRate2 = getMultiAccRateNew(aInsuAccNo,
						tLMRiskInsuAccSchema, tLCInsureAccTraceSchema
								.getPayDate(), aBalanceDate, aRateType, "C",
						aIntervalType, Period, tType, Depst);

				// 记录ResultRate2[]的有效数组长度
				int tCount = Integer.parseInt(ResultRate2[0]);
				for (int m = 1; m < tCount + 1; m++) {
					if (ResultRate2[m] == null) {
						ResultRate2[m] = "0";
					}
					double tCalAccClassRate = Double
							.parseDouble(ResultRate2[m]);
					double tSubInterest = tLCInsureAccTraceSchema.getMoney()
							* tCalAccClassRate;
					tInterest += tSubInterest;
				}
				double tBalance = tLCInsureAccTraceSchema.getMoney()
						+ tInterest;
				aAccBalance += tBalance;
			}
			break;
		default:
			aAccBalance = 0.0;
			break;
		}

		return aAccBalance;
	}

	/**
	 * 按照保单得到所有帐户的余额
	 * 
	 * @param aInsuAccNo
	 * @param aPolNo
	 * @param BalanceDate
	 *            结息日期
	 * @param aRateType
	 *            取得描述帐户利率的时间单位。
	 * @param aIntervalType
	 *            结息的时间单位。
	 * @return
	 */
	public double getAccBalance(String aPolNo, String aBalanceDate,
			String aRateType, String aIntervalType) {
		double aAccBalance = 0.0;
		double tAccRate, tCalAccRate;
		String aInsuAccNo = "";
		int tInterval = 0;
		String tSql = "";
		String iSql = "";
		double tAccInterest = 0.0;
		mLCInsureAccSet.clear();
		mLCInsureAccTraceSet.clear();

		tSql = "select * from LCInsureAcc where PolNo='" + "?d1?"
				+ "' order by InsuAccNo";
		 SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
		    sqlbv4.sql(tSql);
		    sqlbv4.put("d1",aPolNo);
		LCInsureAccDB tLCInsureAccDB = new LCInsureAccDB();
		mLCInsureAccSet = tLCInsureAccDB.executeQuery(sqlbv4);

		for (int i = 1; i <= mLCInsureAccSet.size(); i++) {
			// 得到描述表中帐户利率
			LMRiskInsuAccSchema tLMRiskInsuAccSchema = new LMRiskInsuAccSchema();
			if (i == 1
					|| !mLCInsureAccSet.get(i).getInsuAccNo()
							.equals(aInsuAccNo)) {
				LMRiskInsuAccDB tLMRiskInsuAccDB = new LMRiskInsuAccDB();
				tLMRiskInsuAccDB.setInsuAccNo(mLCInsureAccSet.get(i)
						.getInsuAccNo());
				if (!tLMRiskInsuAccDB.getInfo()) {
					return aAccBalance;
				}
				tLMRiskInsuAccSchema = tLMRiskInsuAccDB.getSchema();
				aInsuAccNo = mLCInsureAccSet.get(i).getInsuAccNo();
			}

			switch (Integer.parseInt(tLMRiskInsuAccSchema.getAccComputeFlag())) {
			// 不计息
			case 0:
				aAccBalance = 0.0;
				break;
			// 按照固定利率单利生息
			case 1:
				tAccRate = tLMRiskInsuAccSchema.getAccRate();
				tCalAccRate = TransAccRate(tAccRate, aRateType, "S",
						aIntervalType);

				LCInsureAccTraceDB tLCInsureAccTraceDB = new LCInsureAccTraceDB();
				LCInsureAccTraceSet tLCInsureAccTraceSet = new LCInsureAccTraceSet();
				tLCInsureAccTraceDB.setPolNo(aPolNo);
				tLCInsureAccTraceDB.setInsuAccNo(aInsuAccNo);

				// tLCInsureAccTraceDB.setOtherNo(mLCInsureAccSet.get(i).getOtherNo());
				tLCInsureAccTraceSet = tLCInsureAccTraceDB.query();
				for (int j = 1; j <= tLCInsureAccTraceSet.size(); j++) {
					// 得到时间间隔
					tInterval = PubFun.calInterval(mLCInsureAccSet.get(i)
							.getBalaDate(), aBalanceDate, aIntervalType);
					double tInterest = tLCInsureAccTraceSet.get(j).getMoney()
							+ tLCInsureAccTraceSet.get(j).getMoney()
							* getIntvRate(tInterval, tCalAccRate, "S");
					tLCInsureAccTraceSet.get(j).setMoney(tInterest);
					mLCInsureAccTraceSet.add(tLCInsureAccTraceSet.get(j));
					tAccInterest += tInterest;
				}
				mLCInsureAccSet.get(i).setInsuAccBala(tAccInterest);
				mLCInsureAccSet.get(i).setBalaDate(aBalanceDate);
				mLCInsureAccSet.get(i).setBalaTime(PubFun.getCurrentTime());
				break;
			// 按照固定利率复利生息
			case 2:
				tAccRate = tLMRiskInsuAccSchema.getAccRate();
				tCalAccRate = TransAccRate(tAccRate, aRateType, "C",
						aIntervalType);

				LCInsureAccTraceDB tLCInsureAccTraceDB2 = new LCInsureAccTraceDB();
				LCInsureAccTraceSet tLCInsureAccTraceSet2 = new LCInsureAccTraceSet();
				tLCInsureAccTraceDB2.setPolNo(aPolNo);
				tLCInsureAccTraceDB2.setInsuAccNo(aInsuAccNo);

				// tLCInsureAccTraceDB2.setOtherNo(mLCInsureAccSet.get(i).getOtherNo());
				tLCInsureAccTraceSet2 = tLCInsureAccTraceDB2.query();
				for (int j = 1; j <= tLCInsureAccTraceSet2.size(); j++) {
					// 得到时间间隔
					tInterval = PubFun.calInterval(mLCInsureAccSet.get(i)
							.getBalaDate(), aBalanceDate, aIntervalType);
					double tInterest = tLCInsureAccTraceSet2.get(j).getMoney()
							+ tLCInsureAccTraceSet2.get(j).getMoney()
							* getIntvRate(tInterval, tCalAccRate, "C");
					tLCInsureAccTraceSet2.get(j).setMoney(tInterest);
					mLCInsureAccTraceSet.add(tLCInsureAccTraceSet2.get(j));
					tAccInterest += tInterest;
				}
				mLCInsureAccSet.get(i).setInsuAccBala(tAccInterest);
				mLCInsureAccSet.get(i).setBalaDate(aBalanceDate);
				mLCInsureAccSet.get(i).setBalaTime(PubFun.getCurrentTime());

				break;
			// 按照利率表单利生息
			case 3:
				tAccRate = AccountManage.getAccRate(tLMRiskInsuAccSchema
						.getSchema());
				tCalAccRate = TransAccRate(tAccRate, aRateType, "S",
						aIntervalType);

				LCInsureAccTraceDB tLCInsureAccTraceDB3 = new LCInsureAccTraceDB();
				LCInsureAccTraceSet tLCInsureAccTraceSet3 = new LCInsureAccTraceSet();
				tLCInsureAccTraceDB3.setPolNo(aPolNo);
				tLCInsureAccTraceDB3.setInsuAccNo(aInsuAccNo);

				// tLCInsureAccTraceDB3.setOtherNo(mLCInsureAccSet.get(i).getOtherNo());
				tLCInsureAccTraceSet3 = tLCInsureAccTraceDB3.query();
				for (int j = 1; j <= tLCInsureAccTraceSet3.size(); j++) {
					double tInterest = 0;
					// 得到分段的单利计算
					String[] ResultRate = getMultiAccRate(aInsuAccNo,
							tLMRiskInsuAccSchema.getSchema(), mLCInsureAccSet
									.get(i).getBalaDate(), aBalanceDate,
							aRateType, "S", aIntervalType);
					for (int m = 0; m < ResultRate.length; m++) {
						tCalAccRate = Double.parseDouble(ResultRate[m]);
						double tSubInterest = tLCInsureAccTraceSet3.get(j)
								.getMoney()
								* tCalAccRate;
						tInterest += tSubInterest;
					}
					double tBalance = tLCInsureAccTraceSet3.get(j).getMoney()
							+ tInterest;
					mLCInsureAccTraceSet.get(j).setMoney(tBalance);
					tAccInterest += tBalance;
				}
				mLCInsureAccSet.get(i).setInsuAccBala(tAccInterest);
				mLCInsureAccSet.get(i).setBalaDate(aBalanceDate);
				mLCInsureAccSet.get(i).setBalaTime(PubFun.getCurrentTime());

				break;
			// 按照利率表复利生息
			case 4:
				tAccRate = AccountManage.getAccRate(tLMRiskInsuAccSchema
						.getSchema());
				tCalAccRate = TransAccRate(tAccRate, aRateType, "C",
						aIntervalType);

				LCInsureAccTraceDB tLCInsureAccTraceDB4 = new LCInsureAccTraceDB();
				LCInsureAccTraceSet tLCInsureAccTraceSet4 = new LCInsureAccTraceSet();

				tLCInsureAccTraceDB4.setPolNo(aPolNo);
				tLCInsureAccTraceDB4.setInsuAccNo(aInsuAccNo);

				// tLCInsureAccTraceDB4.setOtherNo(mLCInsureAccSet.get(i).getOtherNo());
				tLCInsureAccTraceSet4 = tLCInsureAccTraceDB4.query();
				for (int j = 1; j <= tLCInsureAccTraceSet4.size(); j++) {
					double tInterest = 0;
					// 得到分段的复利计算
					String[] ResultRate = getMultiAccRate(aInsuAccNo,
							tLMRiskInsuAccSchema.getSchema(), mLCInsureAccSet
									.get(i).getBalaDate(), aBalanceDate,
							aRateType, "C", aIntervalType);
					for (int m = 0; m < ResultRate.length; m++) {
						tCalAccRate = Double.parseDouble(ResultRate[m]);
						double tSubInterest = tLCInsureAccTraceSet4.get(j)
								.getMoney()
								* tCalAccRate;
						tInterest += tSubInterest;
					}
					double tBalance = tLCInsureAccTraceSet4.get(j).getMoney()
							+ tInterest;
					mLCInsureAccTraceSet.get(j).setMoney(tBalance);
					tAccInterest += tBalance;
				}
				mLCInsureAccSet.get(i).setInsuAccBala(tAccInterest);
				mLCInsureAccSet.get(i).setBalaDate(aBalanceDate);
				mLCInsureAccSet.get(i).setBalaTime(PubFun.getCurrentTime());
				break;
			default:
				aAccBalance = 0.0;
				break;
			}
			aAccBalance += tAccInterest;
		}

		return aAccBalance;
	}

	/**
	 * 已知帐户余额得到帐户的利息（原描述帐户利率默认为年）
	 * 
	 * @param aInsuAccNo
	 * @param aOriginAccBalance
	 * @param aStartDate
	 * @param aEndDate
	 * @param aIntervalType
	 * @return
	 */
	public double getAccInterest(String aInsuAccNo, double aOriginAccBalance,
			String aStartDate, String aEndDate, String aIntervalType) {
		double aAccInterest = 0.0;
		double tAccRate, tCalAccRate;
		int tInterval = 0;
		String aRateType = "Y";
		// 得到时间间隔
		tInterval = PubFun.calInterval(aStartDate, aEndDate, aIntervalType);
		// 得到描述表中帐户利率
		LMRiskInsuAccDB tLMRiskInsuAccDB = new LMRiskInsuAccDB();
		tLMRiskInsuAccDB.setInsuAccNo(aInsuAccNo);
		if (!tLMRiskInsuAccDB.getInfo()) {
			return aAccInterest;
		}

		switch (Integer.parseInt(tLMRiskInsuAccDB.getAccComputeFlag())) {
		// 不计息
		case 0:
			aAccInterest = 0.0;
			break;
		// 按照固定利率单利生息
		case 1:
			tAccRate = tLMRiskInsuAccDB.getAccRate();
			tCalAccRate = TransAccRate(tAccRate, aRateType, "S", aIntervalType);
			aAccInterest = aOriginAccBalance * tCalAccRate * tInterval;
			break;
		// 按照固定利率复利生息
		case 2:
			tAccRate = tLMRiskInsuAccDB.getAccRate();
			tCalAccRate = TransAccRate(tAccRate, aRateType, "C", aIntervalType);
			aAccInterest = aOriginAccBalance * tCalAccRate * tInterval;
			break;
		// 按照利率表单利生息
		case 3:
			tAccRate = AccountManage.getAccRate(tLMRiskInsuAccDB.getSchema());
			tCalAccRate = TransAccRate(tAccRate, aRateType, "S", aIntervalType);
			aAccInterest = aOriginAccBalance * tCalAccRate * tInterval;
			break;
		// 按照利率表复利生息
		case 4:
			tAccRate = AccountManage.getAccRate(tLMRiskInsuAccDB.getSchema());
			tCalAccRate = TransAccRate(tAccRate, aRateType, "C", aIntervalType);
			aAccInterest = aOriginAccBalance * tCalAccRate * tInterval;
			break;
		default:
			aAccInterest = 0.0;
			break;
		}
		return aAccInterest;
	}

	/**
	 * 已知帐户余额得到帐户的利息（按照描述帐户利率）
	 * 
	 * @param aInsuAccNo
	 * @param aOriginAccBalance
	 * @param aStartDate
	 * @param aEndDate
	 * @param aIntervalType
	 * @return
	 */
	public double getAccInterest(String aInsuAccNo, String aRateType,
			double aOriginAccBalance, String aStartDate, String aEndDate,
			String aIntervalType) {
		double aAccInterest = 0.0;
		double tAccRate, tCalAccRate;
		int tInterval = 0;

		// 得到时间间隔
		tInterval = PubFun.calInterval(aStartDate, aEndDate, aIntervalType);
		// 得到描述表中帐户利率
		LMRiskInsuAccDB tLMRiskInsuAccDB = new LMRiskInsuAccDB();
		tLMRiskInsuAccDB.setInsuAccNo(aInsuAccNo);
		if (!tLMRiskInsuAccDB.getInfo()) {
			return aAccInterest;
		}

		switch (Integer.parseInt(tLMRiskInsuAccDB.getAccComputeFlag())) {
		// 不计息
		case 0:
			aAccInterest = 0.0;
			break;
		// 按照固定利率单利生息
		case 1:
			tAccRate = tLMRiskInsuAccDB.getAccRate();
			tCalAccRate = TransAccRate(tAccRate, aRateType, "S", aIntervalType);
			aAccInterest = aOriginAccBalance * tCalAccRate * tInterval;
			break;
		// 按照固定利率复利生息
		case 2:
			tAccRate = tLMRiskInsuAccDB.getAccRate();
			tCalAccRate = TransAccRate(tAccRate, aRateType, "C", aIntervalType);
			aAccInterest = aOriginAccBalance * tCalAccRate * tInterval;
			break;
		// 按照利率表单利生息
		case 3:
			tAccRate = AccountManage.getAccRate(tLMRiskInsuAccDB.getSchema(),
					aRateType);
			tCalAccRate = TransAccRate(tAccRate, aRateType, "S", aIntervalType);
			aAccInterest = aOriginAccBalance * tCalAccRate * tInterval;
			break;
		// 按照利率表复利生息
		case 4:
			tAccRate = AccountManage.getAccRate(tLMRiskInsuAccDB.getSchema(),
					aRateType);
			tCalAccRate = TransAccRate(tAccRate, aRateType, "C", aIntervalType);
			aAccInterest = aOriginAccBalance * tCalAccRate * tInterval;
			break;
		default:
			aAccInterest = 0.0;
			break;
		}
		return aAccInterest;
	}

	/**
	 * 已知帐户余额得到帐户的利息（按照描述帐户利率）902 万能险计算利息
	 * 
	 * @param aInsuAccNo
	 * @param aOriginAccBalance
	 * @param aStartDate
	 * @param aEndDate
	 * @param aIntervalType
	 * @return
	 */
	public double getAccInterest(String aInsuAccNo, String aRateType,
			double tAccRate, double aOriginAccBalance, String aStartDate,
			String aEndDate, String aIntervalType) {
		double aAccInterest = 0.0;
		double tCalAccRate;
		int tInterval = 0;

		// 得到时间间隔
		tInterval = PubFun.calInterval(aStartDate, aEndDate, aIntervalType);
		// 得到描述表中帐户利率
		LMRiskInsuAccDB tLMRiskInsuAccDB = new LMRiskInsuAccDB();
		tLMRiskInsuAccDB.setInsuAccNo(aInsuAccNo);
		if (!tLMRiskInsuAccDB.getInfo()) {
			return aAccInterest;
		}

		switch (Integer.parseInt(tLMRiskInsuAccDB.getAccComputeFlag())) {
		// 不计息
		case 0:
			aAccInterest = 0.0;
			break;
		// 按照固定利率单利生息
		case 1:
			tCalAccRate = TransAccRate(tAccRate, aRateType, "S", aIntervalType);
			aAccInterest = aOriginAccBalance * tCalAccRate * tInterval;
			break;
		// 按照固定利率复利生息
		case 2:
			tCalAccRate = TransAccRate(tAccRate, aRateType, "C", aIntervalType);
			aAccInterest = aOriginAccBalance * tCalAccRate * tInterval;
			break;
		// 按照利率表单利生息
		case 3:
			tCalAccRate = TransAccRate(tAccRate, aRateType, "S", aIntervalType);
			aAccInterest = aOriginAccBalance * tCalAccRate * tInterval;
			break;
		// 按照利率表复利生息
		case 4:
			tCalAccRate = TransAccRate(tAccRate, aRateType, "C", aIntervalType);
			aAccInterest = aOriginAccBalance * tCalAccRate * tInterval;
			break;
		default:
			aAccInterest = 0.0;
			break;
		}
		return aAccInterest;
	}

	/**
	 * 得到描述表中的利率
	 * 
	 * @param InsuAccNo
	 * @return
	 */
	private static double getAccRate(LMRiskInsuAccSchema aLMRiskInsuAccSchema) {
		double aAccRate = 0.0;
		LMRiskInsuAccSchema tLMRiskInsuAccSchema = new LMRiskInsuAccSchema();
		Calculator tCalculator = new Calculator();
		tCalculator.addBasicFactor("InsuAccNo", tLMRiskInsuAccSchema
				.getInsuAccNo());
		// 默认为年利率
		tCalculator.addBasicFactor("RateType", "Y");
		// 待填加其它条件
		// tCalculator.addBasicFactor("","");
		tCalculator.setCalCode(tLMRiskInsuAccSchema.getAccCancelCode());
		String tStr = "";
		tStr = tCalculator.calculate();
		logger.debug("---str" + tStr);
		if (tStr != null && !tStr.trim().equals("")) {
			aAccRate = Double.parseDouble(tStr);
		}

		return aAccRate;
	}

	/**
	 * 按传入类型得到描述表中的利率
	 * 
	 * @param InsuAccNo
	 * @return
	 */
	private static double getAccRate(LMRiskInsuAccSchema aLMRiskInsuAccSchema,
			String aRateType) {
		double aAccRate = 0.0;
		LMRiskInsuAccSchema tLMRiskInsuAccSchema = new LMRiskInsuAccSchema();
		Calculator tCalculator = new Calculator();
		tCalculator.addBasicFactor("InsuAccNo", tLMRiskInsuAccSchema
				.getInsuAccNo());
		tCalculator.addBasicFactor("RateType", aRateType);
		// 待填加其它条件
		// tCalculator.addBasicFactor("","");
		tCalculator.setCalCode(tLMRiskInsuAccSchema.getAccCancelCode());
		String tStr = "";
		tStr = tCalculator.calculate();
		logger.debug("---str" + tStr);
		if (tStr != null && !tStr.trim().equals("")) {
			aAccRate = Double.parseDouble(tStr);
		}

		return aAccRate;
	}

	/**
	 * 利率转换函数
	 * 
	 * @param OriginRate(原始利率）
	 * @param OriginRateType（原始利率类型：年利率（"Y")，月利率("M")，日利率("D")）
	 * @param TransType（复利转换("C")compound，单利转换("S")simple）
	 * @param DestRateType（年利率，月利率,日利率）
	 * @return
	 */
	public static double TransAccRate(double OriginRate, String OriginRateType,
			String TransType, String DestRateType) {
		double DestRate = 0;
		double aPower;

		// Add by Minim for RF of BQ
		if (TransType.equals("1")) {
			TransType = "S";
		}
		if (TransType.equals("2")) {
			TransType = "C";
		}

		if (OriginRateType.equals("1")) {
			OriginRateType = "Y";
		}
		if (OriginRateType.equals("2")) {
			OriginRateType = "M";
		}
		if (OriginRateType.equals("3")) {
			OriginRateType = "D";
		}
		// End add by Minim

		// 复利处理
		if (TransType.equals("C")) {
			// 年复利转换
			if (OriginRateType.equals("Y")) {
				// translate to year
				if (DestRateType.equals("Y")) {
					DestRate = OriginRate;
				}
				// translate to month
				else if (DestRateType.equals("M")) {
					aPower = 1.0 / 12.0;
					DestRate = java.lang.Math.pow(1 + OriginRate, aPower) - 1;
				}
				// translate to day
				else if (DestRateType.equals("D")) {
					aPower = 1.0 / 365.0;// for NCL Modified by lizhuo
					// aPower = 1.0 / Double.parseDouble(SysConst.DAYSOFYEAR);
					DestRate = java.lang.Math.pow(1 + OriginRate, aPower) - 1;
				} else {
					logger.debug("-----CY no this DestRateType----");
				}
			}
			// 月复利转换
			else if (OriginRateType.equals("M")) {
				// translate to month
				if (DestRateType.equals("M")) {
					DestRate = OriginRate;
				}
				// translate to year
				else if (DestRateType.equals("Y")) {
					aPower = 12;
					DestRate = java.lang.Math.pow(1 + OriginRate, aPower) - 1;
				}
				// translate to day
				else if (DestRateType.equals("D")) {
					aPower = 1.0 / 30.0;
					DestRate = java.lang.Math.pow(1 + OriginRate, aPower) - 1;
				} else {
					logger.debug("-----CM no this DestRateType----");
				}
			}
			// 日复利转换
			else if (OriginRateType.equals("D")) {
				// translate to day
				if (DestRateType.equals("D")) {
					DestRate = OriginRate;
				}
				// translate to month
				else if (DestRateType.equals("M")) {
					aPower = 30;
					DestRate = java.lang.Math.pow(1 + OriginRate, aPower) - 1;
				}
				// translate to year
				else if (DestRateType.equals("Y")) {
					aPower = 365;
					DestRate = java.lang.Math.pow(1 + OriginRate, aPower) - 1;
				} else {
					logger.debug("-----CD no this DestRateType----");
				}
			} else {
				logger.debug("------C no this OriginRateType------");
			}
		}
		// 单利处理
		else if (TransType.equals("S")) {
			// 年单利转换
			if (OriginRateType.equals("Y")) {
				// translate to year
				if (DestRateType.equals("Y")) {
					DestRate = OriginRate;
				}
				// translate to month
				else if (DestRateType.equals("M")) {
					DestRate = OriginRate / 12;
				}
				// translate to day
				else if (DestRateType.equals("D")) {
					DestRate = OriginRate / 365;
				} else {
					logger.debug("-----SY no this DestRateType----");
				}
			}
			// 月单利转换
			else if (OriginRateType.equals("M")) {
				// translate to month
				if (DestRateType.equals("M")) {
					DestRate = OriginRate;
				}
				// translate to year
				else if (DestRateType.equals("Y")) {
					DestRate = OriginRate * 12;
				}
				// translate to day
				else if (DestRateType.equals("D")) {
					DestRate = OriginRate / 30;
				} else {
					logger.debug("-----SM no this DestRateType----");
				}
			}
			// 日单利转换
			else if (OriginRateType.equals("D")) {
				// translate to day
				if (DestRateType.equals("D")) {
					DestRate = OriginRate;
				}
				// translate to month
				else if (DestRateType.equals("M")) {
					DestRate = OriginRate * 30;
				}
				// translate to year
				else if (DestRateType.equals("Y")) {
					DestRate = OriginRate * 365;
				} else {
					logger.debug("-----SD no this DestRateType----");
				}
			} else {
				logger.debug("------S no this OriginRateType------");
			}
		} else {
			logger.debug("-------have not this TransType------");
		}
		return DestRate;
	}

	/**
	 * 得到结息后的帐户记录
	 * 
	 * @return
	 */
	public LCInsureAccSet getInsureAcc() {
		return mLCInsureAccSet;
	}

	/**
	 * 得到分段结息计算参数。(未测试)
	 * 
	 * @param aLCInsureAccSchema
	 * @param aLMRiskInsuAccSchema
	 * @param aBalanceDate
	 * @param aRateType
	 * @param aIntervalType
	 * @return
	 */

	private String[] getMultiAccRate1(LCInsureAccSchema aLCInsureAccSchema,
			LMRiskInsuAccSchema aLMRiskInsuAccSchema, String aBalanceDate,
			String aTransType, String aIntervalType) {
		String tSql = "";
		String ResultArray[] = new String[100];
		Calculator tCalculator = new Calculator();
		String TableName = aLMRiskInsuAccSchema.getAccRateTable();
		if (aLMRiskInsuAccSchema.getAccCancelCode() != null
				&& !aLMRiskInsuAccSchema.getAccCancelCode().trim().equals("")) {
			try {
				tSql = PubFun1.getSQL(aLMRiskInsuAccSchema.getAccCancelCode(),
						tCalculator);
			} catch (Exception e) {
				e.printStackTrace();
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "AccountManage";
				tError.functionName = "getMultiAccInterest";
				tError.errorMessage = e.toString();
				this.mErrors.addOneError(tError);
			}
		} else {
			TableName = aLMRiskInsuAccSchema.getAccRateTable();
			tSql = "select * from '" + TableName + "' where InsuAccNo='"
					+ aLCInsureAccSchema.getInsuAccNo() + "' and EndDate>='"
					+ aLCInsureAccSchema.getBalaDate() + "' and EndDate<='"
					+ aBalanceDate + "' order by EndDate";
			
		}

		Statement stmt = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		double aValue = 0.0;
		double aResult = 0.0;
		String aDate = "";
		String tStartDate = "";

		DBOper db = new DBOper("");
		Connection con = db.getConnection();

		logger.debug(tSql.trim());
		try {
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			// 得到其中所有的时间段信息
			rs = stmt.executeQuery(StrTool.GBKToUnicode(tSql));
			int i = 0;
			while (rs.next()) {
				i++;
				if (i == 1) {
					tStartDate = aLCInsureAccSchema.getBalaDate();
				}
				aDate = rs.getString("EndDate");
				aValue = rs.getDouble("Rate");
				String aRateIntv = rs.getString("RateIntv");
				double tCalAccRate = TransAccRate(aValue, aRateIntv,
						aTransType, aIntervalType);
				int tInterval = PubFun.calInterval(tStartDate, aDate,
						aIntervalType);
				if (tInterval > 0) {
					aResult = getIntvRate(tInterval, tCalAccRate, aTransType);
					ResultArray[i - 1] = String.valueOf(aResult);
				}
				tStartDate = aDate;
			}
			rs.close();
			
			// 得到结息日在利率临界点后
			if (ResultArray.length > 0) {
				int tLength = ResultArray.length - 1;
				tSql = "select * from '" + TableName + "' where InsuAccNo='"
						+ aLCInsureAccSchema.getInsuAccNo()
						+ "' and StartDate<'" + aBalanceDate
						+ "' and EndDate>'" + aBalanceDate
						+ "' order by EndDate";
				rs1 = stmt.executeQuery(StrTool.GBKToUnicode(tSql));
				i = 0;
				while (rs1.next()) {
					i++;
					aDate = rs1.getString("StartDate");
					aValue = rs1.getDouble("Rate");
					String aRateIntv = rs.getString("RateIntv");
					double tCalAccRate = TransAccRate(aValue, aRateIntv,
							aTransType, aIntervalType);
					int tInterval = PubFun.calInterval(aDate, aBalanceDate,
							aIntervalType);
					aResult = getIntvRate(tInterval, tCalAccRate, aTransType);
					ResultArray[tLength++] = String.valueOf(aResult);
				}
				rs1.close();
			} else {
				tSql = "select * from '" + TableName + "' where InsuAccNo='"
						+ aLCInsureAccSchema.getInsuAccNo()
						+ "' and StartDate<'"
						+ aLCInsureAccSchema.getBalaDate() + "' and EndDate>'"
						+ aBalanceDate + "' order by EndDate";
				rs2 = stmt.executeQuery(StrTool.GBKToUnicode(tSql));
				i = 0;
				while (rs2.next()) {
					i++;
					aDate = rs2.getString("StartDate");
					aValue = rs2.getDouble("Rate");
					String aRateIntv = rs.getString("RateIntv");
					double tCalAccRate = TransAccRate(aValue, aRateIntv,
							aTransType, aIntervalType);
					int tInterval = PubFun.calInterval(aDate, rs2
							.getString("EndDate"), aIntervalType);
					aResult = getIntvRate(tInterval, tCalAccRate, aTransType);
					ResultArray[i - 1] = String.valueOf(aResult);
				}
				rs1.close();
			}
		} catch (Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "AccountManage";
			tError.functionName = "getMultiAccInterest";
			tError.errorMessage = e.toString();
			this.mErrors.addOneError(tError);

		}
		finally
		{
			try {
				if(rs!=null)
				{
					rs.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				if(rs1!=null)
				{
					rs1.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				if(rs2!=null)
				{
					rs2.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			try {
				if(stmt!=null)
				{
					stmt.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return ResultArray;
	}

	/**
	 * 得到分段结息的参数（推荐）
	 * 
	 * @param aLCInsureAccSchema
	 * @param aLMRiskInsuAccSchema
	 * @param aBalanceDate
	 * @param aRateType
	 * @param aTransType
	 * @param aIntervalType
	 * @return
	 */
	private String[] getMultiAccRate(LCInsureAccSchema aLCInsureAccSchema,
			LMRiskInsuAccSchema aLMRiskInsuAccSchema, String aBalanceDate,
			String aRateType, String aTransType, String aIntervalType) {
		String tSql = "";
		String[] ResultArray = new String[100];
		Calculator tCalculator = new Calculator();
		String TableName = aLMRiskInsuAccSchema.getAccRateTable();
		if (aLMRiskInsuAccSchema.getAccCancelCode() != null
				&& !aLMRiskInsuAccSchema.getAccCancelCode().trim().equals("")) {
			try {
				tSql = PubFun1.getSQL(aLMRiskInsuAccSchema.getAccCancelCode(),
						tCalculator);
			} catch (Exception e) {
				e.printStackTrace();
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "AccountManage";
				tError.functionName = "getMultiAccInterest";
				tError.errorMessage = e.toString();
				this.mErrors.addOneError(tError);
			}
		} else {
			TableName = aLMRiskInsuAccSchema.getAccRateTable();
			tSql = "select * from '" + TableName + "' where InsuAccNo='"
					+ aLCInsureAccSchema.getInsuAccNo() + "' and StartDate<='"
					+ aBalanceDate + "' and EndDate>='"
					+ aLCInsureAccSchema.getBalaDate() + "' order by EndDate";
		}

		Statement stmt = null;
		ResultSet rs = null;
		double aValue = 0.0;
		double aResult = 0.0;
		String aDate = "";
		String tStartDate = "";

		DBOper db = new DBOper("");
		Connection con = db.getConnection();

		logger.debug(tSql.trim());
		try {
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			rs = stmt.executeQuery(StrTool.GBKToUnicode(tSql));
			int i = 0;
			while (rs.next()) {
				i++;
				aDate = rs.getString("EndDate");
				aValue = rs.getDouble("Rate");
				String aRateIntv = rs.getString("RateIntv");
				double tCalAccRate = TransAccRate(aValue, aRateIntv,
						aTransType, aIntervalType);
				if (i == 1) {
					tStartDate = aLCInsureAccSchema.getBalaDate();
				}
				// 结息点超过某个利率临界点
				if (PubFun.calInterval(aDate, aBalanceDate, aIntervalType) > 0) {
					int tInterval = PubFun.calInterval(tStartDate, aDate,
							aIntervalType);
					if (tInterval > 0) {
						aResult = getIntvRate(tInterval, tCalAccRate,
								aTransType);
						ResultArray[i - 1] = String.valueOf(aResult);
					}
					tStartDate = aDate;
				} else {
					int tInterval = PubFun.calInterval(tStartDate,
							aBalanceDate, aIntervalType);
					if (tInterval > 0) {
						aResult = getIntvRate(tInterval, tCalAccRate,
								aTransType);
						ResultArray[i - 1] = String.valueOf(aResult);
					}
					break;
				}
			}
			rs.close();
		} catch (Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "AccountManage";
			tError.functionName = "getMultiAccInterest";
			tError.errorMessage = e.toString();
			this.mErrors.addOneError(tError);

		}
		finally
		{
			try {
				if(rs!=null)
				{
					rs.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				if(stmt!=null)
				{
					stmt.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return ResultArray;
	}

	/**
	 * * 得到分段结息的参数（推荐）
	 * 
	 * @param aInsuAccNo
	 * @param aLMRiskInsuAccSchema
	 * @param aOriginBalaDate
	 * @param aBalanceDate
	 * @param aRateType
	 *            原描述利率的类型
	 * @param aTransType
	 *            单利复利
	 * @param aIntervalType
	 *            转换成利率类型
	 * @return
	 */
	private String[] getMultiAccRate(String aInsuAccNo,
			LMRiskInsuAccSchema aLMRiskInsuAccSchema, String aOriginBalaDate,
			String aBalanceDate, String aRateType, String aTransType,
			String aIntervalType) {
		String tSql = "";
		String[] ResultArray = new String[100];
		Calculator tCalculator = new Calculator();
		String TableName = aLMRiskInsuAccSchema.getAccRateTable();
		if (aLMRiskInsuAccSchema.getAccCancelCode() != null
				&& !aLMRiskInsuAccSchema.getAccCancelCode().trim().equals("")) {
			try {
				tSql = PubFun1.getSQL(aLMRiskInsuAccSchema.getAccCancelCode(),
						tCalculator);
			} catch (Exception e) {
				e.printStackTrace();
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "AccountManage";
				tError.functionName = "getMultiAccInterest";
				tError.errorMessage = e.toString();
				this.mErrors.addOneError(tError);
			}
		} else {
			TableName = aLMRiskInsuAccSchema.getAccRateTable();
			tSql = "select * from " + TableName + " where InsuAccNo='"
					+ aInsuAccNo + "' and StartDate<='" + aBalanceDate
					+ "' and EndDate>='" + aOriginBalaDate
					+ "' order by EndDate";
		}
		Statement stmt = null;
		ResultSet rs = null;
		double aValue = 0.0;
		double aResult = 0.0;
		String aDate = "";
		String tStartDate = "";

		DBOper db = new DBOper("");
		Connection con = db.getConnection();

		logger.debug(tSql.trim());
		try {
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			rs = stmt.executeQuery(StrTool.GBKToUnicode(tSql));
			int i = 0;
			while (rs.next()) {
				i++;
				aDate = rs.getString("EndDate");
				aValue = rs.getDouble("Rate");
				String aRateIntv = rs.getString("RateIntv");
				double tCalAccRate = TransAccRate(aValue, aRateType,
						aTransType, aIntervalType);
				if (i == 1) {
					tStartDate = aOriginBalaDate;
				}
				// 结息点超过某个利率临界点
				if (PubFun.calInterval(aDate, aBalanceDate, aIntervalType) > 0) {
					int tInterval = PubFun.calInterval(tStartDate, aDate,
							aIntervalType);
					if (tInterval > 0) {
						aResult = getIntvRate(tInterval, tCalAccRate,
								aTransType);
						ResultArray[i - 1] = String.valueOf(aResult);
					}
					tStartDate = aDate;
				} else {
					int tInterval = PubFun.calInterval(tStartDate,
							aBalanceDate, aIntervalType);
					if (tInterval > 0) {
						aResult = getIntvRate(tInterval, tCalAccRate,
								aTransType);
						ResultArray[i - 1] = String.valueOf(aResult);
					}
					break;
				}
			}
			rs.close();
		} catch (Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "AccountManage";
			tError.functionName = "getMultiAccInterest";
			tError.errorMessage = e.toString();
			this.mErrors.addOneError(tError);
		}
		try {
			try {
				if(rs!=null)
				{
					rs.close();
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				if(stmt!=null)
				{
					stmt.close();
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if (!con.isClosed()) {
				con.close();
			}
		} catch (Exception e) {
			CError tError = new CError();
			tError.moduleName = "AccountManage";
			tError.functionName = "getMultiAccInterest";
			tError.errorMessage = e.toString();
			this.mErrors.addOneError(tError);
		}

		return ResultArray;
	}

	/**
	 * * 得到帐户描述分段结息的参数（推荐）
	 * 
	 * @param aInsuAccNo
	 * @param aLMRiskInsuAccSchema
	 * @param aOriginBalaDate
	 * @param aBalanceDate
	 * @param aRateType
	 *            原描述利率的类型
	 * @param aTransType
	 *            单利复利
	 * @param aIntervalType
	 *            转换成利率类型
	 * @return
	 */
	public double getMultiAccInterest(String aInsuAccNo,
			LMRiskInsuAccSchema aLMRiskInsuAccSchema, double aBalance,
			String aOriginBalaDate, String aBalanceDate, String aTransType,
			String aIntervalType) {
		String tSql = "";
		double tInterest = 0.0;
		String[] ResultArray = new String[100];
		Calculator tCalculator = new Calculator();
		String TableName = aLMRiskInsuAccSchema.getAccRateTable();
		if (aLMRiskInsuAccSchema.getAccCancelCode() != null
				&& !aLMRiskInsuAccSchema.getAccCancelCode().trim().equals("")) {
			try {
				tSql = PubFun1.getSQL(aLMRiskInsuAccSchema.getAccCancelCode(),
						tCalculator);
			} catch (Exception e) {
				e.printStackTrace();
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "AccountManage";
				tError.functionName = "getMultiAccInterest";
				tError.errorMessage = e.toString();
				this.mErrors.addOneError(tError);
			}
			SQLwithBindVariables sqlbva = new SQLwithBindVariables();
			sqlbva.sql(tSql);
			ExeSQL tExeSQL = new ExeSQL();
			String tStr = tExeSQL.getOneValue(sqlbva);
			if (tStr == null || tStr.trim().equals("")) {
				tStr = "0";
			}
			tInterest = Double.parseDouble(tStr);
		} else {
			TableName = aLMRiskInsuAccSchema.getAccRateTable();
			tSql = "select * from " + TableName + " where InsuAccNo='"
					+ aInsuAccNo + "' and StartDate<='" + aBalanceDate
					+ "' and EndDate>='" + aOriginBalaDate
					+ "' order by EndDate";
			
			Statement stmt = null;
			ResultSet rs = null;
			double aValue = 0.0;
			double aResult = 0.0;
			String aDate = "";
			String tStartDate = "";

			DBOper db = new DBOper("");
			Connection con = db.getConnection();

			logger.debug(tSql.trim());
			try {
				stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				rs = stmt.executeQuery(StrTool.GBKToUnicode(tSql));
				int i = 0;
				while (rs.next()) {
					i++;
					aDate = rs.getString("EndDate");
					aValue = rs.getDouble("Rate");
					String aRateIntv = rs.getString("RateIntv");
					double tCalAccRate = TransAccRate(aValue, aRateIntv,
							aTransType, aIntervalType);

					if (i == 1) {
						tStartDate = aOriginBalaDate;
					}
					// 结息点超过某个利率临界点
					if (PubFun.calInterval(aDate, aBalanceDate, aIntervalType) > 0) {
						int tInterval = PubFun.calInterval(tStartDate, aDate,
								aIntervalType);
						if (tInterval > 0) {
							aResult = getIntvRate(tInterval, tCalAccRate,
									aTransType);
							tInterest += aBalance * aResult;
						}
						tStartDate = aDate;
					} else {
						int tInterval = PubFun.calInterval(tStartDate,
								aBalanceDate, aIntervalType);
						if (tInterval > 0) {
							aResult = getIntvRate(tInterval, tCalAccRate,
									aTransType);
							tInterest += aBalance * aResult;
						}
						break;
					}
				}
				rs.close();
				rs = null;
				stmt.close();
				stmt = null;
				con.close();
			} catch (Exception e) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "AccountManage";
				tError.functionName = "getMultiAccInterest";
				tError.errorMessage = e.toString();
				this.mErrors.addOneError(tError);
				
				if(rs!=null)
				{
					try {
						rs.close();
						rs = null;
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				if(stmt!=null)
				{
					try {
						stmt.close();
						stmt = null;
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}				
				try {
					con.close();
				} catch (Exception ex) {
				}
			}
		}
		return tInterest;
	}

	/**
	 * * 得到借款描述分段结息的参数（推荐）
	 * 
	 * @param aInsuAccNo
	 * @param aLMRiskInsuAccSchema
	 * @param aOriginBalaDate
	 * @param aBalanceDate
	 * @param aRateType
	 *            原描述利率的类型
	 * @param aTransType
	 *            单利复利
	 * @param aIntervalType
	 *            转换成利率类型
	 * @return
	 */
	private double getMultiAccInterest(LOLoanSchema aLOLoanSchema,
			double aBalance, String aOriginBalaDate, String aBalanceDate,
			String aTransType, String aIntervalType) {
		String tSql = "";
		double tInterest = 0.0;
		String[] ResultArray = new String[100];
		Calculator tCalculator = new Calculator();
		if (aLOLoanSchema.getRateCalType() != null
				&& aLOLoanSchema.getRateCalType().trim().equals("2")) {
			try {
				tSql = PubFun1.getSQL(aLOLoanSchema.getRateCalCode(),
						tCalculator);
			} catch (Exception e) {
				e.printStackTrace();
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "AccountManage";
				tError.functionName = "getMultiAccInterest";
				tError.errorMessage = e.toString();
				this.mErrors.addOneError(tError);
			}
			SQLwithBindVariables sqlbvb =new SQLwithBindVariables();
			sqlbvb.sql(tSql);
			ExeSQL tExeSQL = new ExeSQL();
			String tStr = tExeSQL.getOneValue(sqlbvb);
			if (tStr == null || tStr.trim().equals("")) {
				tStr = "0";
			}
			tInterest = Double.parseDouble(tStr);
		} else {
			String TableName = aLOLoanSchema.getRateCalCode();
			tSql = "select * from '" + TableName + "' where  and StartDate<='"
					+ aBalanceDate + "' and EndDate>='" + aOriginBalaDate
					+ "' order by EndDate";
			Statement stmt = null;
			ResultSet rs = null;
			double aValue = 0.0;
			double aResult = 0.0;
			String aDate = "";
			String tStartDate = "";

			DBOper db = new DBOper("");
			Connection con = db.getConnection();

			logger.debug(tSql.trim());
			try {
				stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				rs = stmt.executeQuery(StrTool.GBKToUnicode(tSql));
				int i = 0;
				while (rs.next()) {
					i++;
					aDate = rs.getString("EndDate");
					aValue = rs.getDouble("Rate");
					String aRateIntv = rs.getString("RateIntv");
					double tCalAccRate = TransAccRate(aValue, aRateIntv,
							aTransType, aIntervalType);

					if (i == 1) {
						tStartDate = aOriginBalaDate;
					}
					// 结息点超过某个利率临界点
					if (PubFun.calInterval(aDate, aBalanceDate, aIntervalType) > 0) {
						int tInterval = PubFun.calInterval(tStartDate, aDate,
								aIntervalType);
						if (tInterval > 0) {
							aResult = getIntvRate(tInterval, tCalAccRate,
									aTransType);
							tInterest += aBalance * aResult;
						}
						tStartDate = aDate;
					} else {
						int tInterval = PubFun.calInterval(tStartDate,
								aBalanceDate, aIntervalType);
						if (tInterval > 0) {
							aResult = getIntvRate(tInterval, tCalAccRate,
									aTransType);
							tInterest += aBalance * aResult;
						}
						break;
					}
				}
				rs.close();
				stmt.close();
				con.close();
			} catch (Exception e) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "AccountManage";
				tError.functionName = "getMultiAccInterest";
				tError.errorMessage = e.toString();
				this.mErrors.addOneError(tError);
				try {
					rs.close();
					stmt.close();
					con.close();
				} catch (Exception ex) {
				}
			}
		}
		return tInterest;
	}

	/**
	 * 得到按照银行利率结息分段结息的参数（推荐）
	 * 
	 * @param aRateKind
	 *            活期,一年定期,二年定期
	 * @param aBalance
	 *            原余额
	 * @param aOriginBalaDate
	 *            利息起期
	 * @param aBalanceDate
	 *            利息止期
	 * @param aTransType
	 *            单利复利
	 * @param aIntervalType
	 *            转换成利率类型
	 * @return
	 */
	public static double getMultiAccInterest(String aRateType, double aBalance,
			String aOriginBalaDate, String aBalanceDate, String aTransType,
			String aIntervalType) {
		String tSql = "";
		FDate tFDate = new FDate();
		String[] ResultArray = new String[100];
		// tSql = "select to_char(StartDate,'"+"yyyy-mm-dd"+"') as
		// StartDate,to_char(EndDate,'"+"yyyy-mm-dd"+"') as
		// EndDate,RateType,RateIntv,Rate from LDBankRate where
		// RateType='"+aRateType+"' and StartDate<='"+aBalanceDate+"' and
		// EndDate>='"+aOriginBalaDate+"' order by EndDate";
		tSql = "select * from LDBankRate where RateType='" + aRateType
				+ "' and StartDate<='" + aBalanceDate + "' and EndDate>='"
				+ aOriginBalaDate + "' order by EndDate";
		Statement stmt = null;
		ResultSet rs = null;
		double aValue = 0.0;
		double aResult = 0.0;
		String aDate = "";
		String tStartDate = "";

		double tInterest = 0.0;

		DBOper db = new DBOper("");

		logger.debug(tSql.trim());
		Connection con = null;
		try {
			con = db.getConnection();

			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			rs = stmt.executeQuery(StrTool.GBKToUnicode(tSql));
			int i = 0;
			while (rs.next()) {
				i++;
				aDate = tFDate.getString(rs.getDate("EndDate"));
				aValue = rs.getDouble("Rate");
				String aRateIntv = rs.getString("RateIntv");
				double tCalAccRate = TransAccRate(aValue, aRateIntv,
						aTransType, aIntervalType);
				if (i == 1) {
					tStartDate = aOriginBalaDate;
				}
				// 结息点超过某个利率临界点
				if (PubFun.calInterval(aDate, aBalanceDate, aIntervalType) > 0) {
					int tInterval = PubFun.calInterval(tStartDate, aDate,
							aIntervalType);
					if (tInterval > 0) {
						aResult = getIntvRate(tInterval, tCalAccRate,
								aTransType);
						tInterest += aBalance * aResult;
					}
					tStartDate = aDate;
				} else {
					int tInterval = PubFun.calInterval(tStartDate,
							aBalanceDate, aIntervalType);
					if (tInterval > 0) {
						aResult = getIntvRate(tInterval, tCalAccRate,
								aTransType);
						tInterest += aBalance * aResult;
					}
					break;
				}
			}
			rs.close();
			rs = null;
			stmt.close();
			stmt = null;
			con.close();
		} catch (Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "AccountManage";
			tError.functionName = "getMultiAccInterest";
			tError.errorMessage = e.toString();
//			try {
//				rs.close();
//				stmt.close();
//				con.close();
//			} catch (Exception ex) {
//			}
		}
		finally{
			try {
				if(rs!=null){
					rs.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			if(stmt!=null){
				try {
					stmt.close();
					stmt = null;
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return tInterest;
	}

	/**
	 * 按照利率,分段结息
	 * 
	 * @param tRate
	 *            double 银行利率
	 * @param aBalance
	 *            double 余额
	 * @param aOriginBalaDate
	 *            String 起始日期
	 * @param aBalanceDate
	 *            String 截止日期
	 * @param aTransType
	 *            String 单利复利
	 * @param aIntervalType
	 *            String 转换成利率类型
	 * @return double
	 */
	public static double getMultiAccInterest(double tRate, double aBalance,
			String aOriginBalaDate, String aBalanceDate, String aTransType,
			String aIntervalType) {
		double tInterest = 0;
		double aResult = 0;
		int tInterval = 1;
		try {
			// ===add===zhangtao===2006-06-17===按月计息改造支持零头天按日计息而不是按正月计息=======BGN=======
			if (aIntervalType.equals("M")) // 按月计息
			{
				String mMonthInterestCalType = getMonthInterestCalType();
				if (aIntervalType.equals("M")
						&& mMonthInterestCalType.equals("D")) // 按月计息计算方式为
																// D-零头天按日计息
				{
					double tCalAccRate_M = TransAccRate(tRate, "Y", aTransType,
							"M"); // 月利率
					double tCalAccRate_D = TransAccRate(tRate, "Y", aTransType,
							"D"); // 日利率

					int tInterval_M = PubFun.calInterval(aOriginBalaDate,
							aBalanceDate, "M");
					if (tInterval_M > 0) {
						aResult = getIntvRate(tInterval_M, tCalAccRate_M,
								aTransType);
						tInterest += aBalance * aResult;
					}

					String sStartDate = PubFun.calDate(aOriginBalaDate,
							tInterval_M, "M", null);

					int tInterval_D = 1 + PubFun.calInterval(sStartDate,
							aBalanceDate, "D");
					if (tInterval_D > 0) {
						aResult = getIntvRate(tInterval_D, tCalAccRate_D,
								aTransType);
						tInterest += (aBalance + tInterest) * aResult;
					}

					return tInterest;
				}
			}
			// ===add===zhangtao===2006-06-17===按月计息改造支持零头天按日计息而不是按正月计息=======END=======
			tInterval += PubFun.calInterval(aOriginBalaDate, aBalanceDate,
					aIntervalType);
			double tCalAccRate = TransAccRate(tRate, "Y", aTransType,
					aIntervalType);
			if (tInterval > 0) {
				aResult = getIntvRate(tInterval, tCalAccRate, aTransType);
				tInterest += aBalance * aResult;
			}
		} catch (Exception e) {
			CError tError = new CError();
			tError.moduleName = "AccountManage";
			tError.functionName = "getMultiAccInterest";
			tError.errorMessage = e.toString();
		}
		return tInterest;
	}

	/**
	 * 
	 * @return
	 */
	public boolean updAccBalance() {

		Connection conn = null;
		conn = DBConnPool.getConnection();

		if (conn == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "AccountManage";
			tError.functionName = "saveData";
			tError.errorMessage = "数据库连接失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		try {
			conn.setAutoCommit(false);
			if (mLCInsureAccTraceSet.size() > 0) {
				LCInsureAccTraceDBSet tLCInsureAccTraceDBSet = new LCInsureAccTraceDBSet(
						conn);
				tLCInsureAccTraceDBSet.set(mLCInsureAccTraceSet);
				if (!tLCInsureAccTraceDBSet.update()) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "AccountManage";
					tError.functionName = "saveData";
					tError.errorMessage = "子帐户更新失败!";
					this.mErrors.addOneError(tError);
					conn.rollback();
					conn.close();
					return false;
				}
			}

			if (mLCInsureAccSet.size() > 0) {
				LCInsureAccDBSet tLCInsureAccDBSet = new LCInsureAccDBSet(conn);
				tLCInsureAccDBSet.set(mLCInsureAccSet);
				if (!tLCInsureAccDBSet.update()) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "AccountManage";
					tError.functionName = "saveData";
					tError.errorMessage = "帐户余额更新失败!";
					this.mErrors.addOneError(tError);
					conn.rollback();
					conn.close();
					return false;
				}
			}
			conn.commit();
			conn.close();
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "AccountManage";
			tError.functionName = "updAccBalance";
			tError.errorMessage = ex.toString();
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

	/**
	 * 得到结息后的帐户轨迹
	 * 
	 * @return
	 */
	public LCInsureAccTraceSet getInsureAccTrace() {
		return mLCInsureAccTraceSet;
	}

	/**
	 * 得到固定利率利息
	 * 
	 * @param OriginBalance
	 *            原始金额
	 * @param aStartDate
	 *            起始日期
	 * @param aEndDate
	 *            终止日期
	 * @param OriginRate(原始利率）
	 * @param OriginRateType（原始利率类型：年利率（"Y")，月利率("M")，日利率("D")）
	 * @param TransType（复利转换("C")compound，单利转换("S")simple）
	 * @param DestRateType（年利率，月利率,日利率）
	 * @return
	 */
	public double getInterest(double OriginBalance, String aStartDate,
			String aEndDate, double OriginRate, String OriginRateType,
			String TransType, String DestRateType) {
		double aInterest = 0.0;
		double tCalRate = 0.0;
		tCalRate = TransAccRate(OriginRate, OriginRateType, TransType,
				DestRateType);
		int tInterval = PubFun.calInterval(aStartDate, aEndDate, DestRateType);
		aInterest = OriginBalance * getIntvRate(tInterval, tCalRate, TransType);
		return aInterest;
	}

	/**
	 * 取得利息
	 * 
	 * @param aCalType
	 * @param aBalance
	 * @param aStartDate
	 * @param aEndDate
	 * @return
	 */
	public double getMultiInterest(String aCalType, VData aVData,
			double aBalance, String aStartDate, String aEndDate) {
		double aInterest = 0.0;
		if (aCalType.equals("0")) {

		} else if (aCalType.equals("1")) {
			LOLoanSchema tLOLoanSchema = new LOLoanSchema();
			tLOLoanSchema = (LOLoanSchema) aVData.getObjectByObjectName(
					"LOLoanSchema", 0);
			if (tLOLoanSchema.getSpecifyRate().equals("1")) {
				aInterest = getInterest(aBalance, aStartDate, aEndDate,
						tLOLoanSchema.getInterestRate(), tLOLoanSchema
								.getInterestMode(), tLOLoanSchema
								.getInterestType(), "D");
			} else if (tLOLoanSchema.getSpecifyRate().equals("2")) {
				aInterest = getMultiAccInterest(tLOLoanSchema, aBalance,
						aStartDate, aEndDate, tLOLoanSchema.getInterestType(),
						"D");
			} else {
				logger.debug("-----no this type----");
			}
		} else {

		}
		return aInterest;
	}

	/**
	 * 得到计算时间间隔的比率
	 * 
	 * @param aInterval
	 * @param aRate
	 * @param aTransType
	 * @return
	 */
	private static double getIntvRate(int aInterval, double aRate,
			String aTransType) {
		double aIntvRate = 0.0;

		// Add by Minim for RF of BQ
		if (aTransType.equals("1")) {
			aTransType = "S";
		}
		if (aTransType.equals("2")) {
			aTransType = "C";
		}
		// End add by Minim;

		if (aTransType.equals("S")) {
			aIntvRate = aRate * aInterval;
		} else if (aTransType.equals("C")) {
			aIntvRate = java.lang.Math.pow(1 + aRate, aInterval) - 1;
		} else {

		}
		return aIntvRate;
	}

	public static void main(String args[]) {
		// AccountManage aAccountManage = new AccountManage();
		// LCInsureAccSchema aLCInsureAccSchema = new LCInsureAccSchema();
		// aLCInsureAccSchema.setPolNo("86110020040210000722");
		// aLCInsureAccSchema.setInsuAccNo("000006");
		// aLCInsureAccSchema.setRiskCode("211701");
		// aLCInsureAccSchema.setAccType("003");
		// aLCInsureAccSchema.setOtherNo("86110020040210000722");
		// aLCInsureAccSchema.setOtherType("1");
		// aLCInsureAccSchema.setContNo("00000000000000000000");
		// aLCInsureAccSchema.setGrpPolNo("86110020040220000042");
		// aLCInsureAccSchema.setInsuredNo("0000001881");
		// aLCInsureAccSchema.setAppntName("北京捷通投资咨询管理有限公司");
		// aLCInsureAccSchema.setSumPay("93000");
		// aLCInsureAccSchema.setInsuAccBala("111800");
		// aLCInsureAccSchema.setUnitCount("0");
		// aLCInsureAccSchema.setInsuAccGetMoney("0");
		// aLCInsureAccSchema.setSumPaym("0");
		// aLCInsureAccSchema.setFrozenMoney("0");
		// aLCInsureAccSchema.setBalaDate("2004-9-1");
		// aLCInsureAccSchema.setAccComputeFlag("4");
		// aLCInsureAccSchema.setManageCom("86110000");
		// aLCInsureAccSchema.setOperator("001");
		// aLCInsureAccSchema.setMakeDate("2004-10-10");
		// aLCInsureAccSchema.setModifyTime("2004-10-10");
		// double a = aAccountManage.getAccBalance(aLCInsureAccSchema,
		// "2004-10-11", "Y", "D");
		// (String aInsuAccNo,double aOriginAccBalance,String aStartDate,String
		// aEndDate,String aIntervalType)

		// double interest =
		// aAccountManage.getInterest("000006",118000,"2004-9-1","2004-10-11","1");
		// double a = 0.72;
		// double b = 0;
		// double d ;
		// double c ;
		// b = 1.0/365.0;
		// d = 1/365;
		// LMLoanSchema tLMLoanSchema = new LMLoanSchema();
		// LMLoanDB tLMLoanDB = new LMLoanDB();
		// tLMLoanDB.setRiskCode("111301");
		// tLMLoanDB.getInfo();
		// tLMLoanSchema.setSchema(tLMLoanDB.getSchema());
		// LOLoanSchema tLOLoanSchema = new LOLoanSchema();
		// LOLoanDB tLOLoanDB = new LOLoanDB();
		// tLOLoanDB.setEdorNo("86110020030420000338");
		// tLOLoanDB.setPolNo("86110020030210000009");
		// tLOLoanDB.getInfo();
		// tLOLoanSchema.setSchema(tLOLoanDB.getSchema());
		// VData tv = new VData();
		// tv.add(tLOLoanSchema);
		//
		//
		// AccountManage tAccountManage = new AccountManage();
		// double
		// tt=tAccountManage.getMultiInterest("1",tv,1000,"2000-01-01","2003-09-01");
		// // b = tAccountManage.TransAccRate(a,"Y","C","D");
		// // d =
		// tAccountManage.getAccBalance("86110020020210000032","2003-05-01","Y","D");
		// d =
		// tAccountManage.getInterest(1000.0,"2002-10-10","2003-01-01",0.0072,"Y","S","D");
		// LOLoanSchema tL = new LOLoanSchema();
		// tL.set
		// logger.debug("---d:"+d);
	}

	// /*********利率及利息计算函数相关*****基于变动年利率计算*****Add by Nicholas*************\
	// 存储计算的结果，如果产生错误则为-1
	private double mCountResult = 0.0;

	// 得到计算结果
	public double getCalResult() {
		return mCountResult;
	}

	// 得到四舍六入五靠偶数后的计算结果
	public double getCalResultRound() {
		return this.getRound(mCountResult);
	}

	/**
	 * 获得某一天所在利率期的“年利率”，如果产生错误则为-1
	 * 说明：对于自垫的情况，目前客户允许自垫的险种都不会带长期附加险，所以这里按单一险（主险）处理
	 * 如果没有找到相应险种编码对应的算法，则查找公共算法，即RiskCode为"000000"
	 * 
	 * @param String
	 *            tEdorType 批改类型
	 * @param String
	 *            tFindDate 要查询的日期
	 * @param String
	 *            tRiskCode 险种编码，传这个参数是为了（将来）把主险和长期附加险分开算的时候扩充用的
	 * @return boolean true-成功，false-失败。
	 */
	public boolean calYearRateByDate(String tEdorType, String tFindDate,
			String tRiskCode) {
		// 查询算法编码
		LMEdorCalDB tLMEdorCalDB = new LMEdorCalDB();
		tLMEdorCalDB.setRiskCode(tRiskCode);
		tLMEdorCalDB.setEdorType(tEdorType);
		tLMEdorCalDB.setCalType("Rate");
		LMEdorCalSet tLMEdorCalSet = tLMEdorCalDB.query(); // 查找保费累计变动金额计算信息
		if (tLMEdorCalSet == null || tLMEdorCalSet.size() <= 0) {
			// 查询公共的
			tLMEdorCalDB.setRiskCode("000000");
			tLMEdorCalDB.setEdorType(tEdorType);
			tLMEdorCalDB.setCalType("Rate");
			tLMEdorCalSet = tLMEdorCalDB.query();

			if (tLMEdorCalSet == null || tLMEdorCalSet.size() <= 0) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "AccountManage";
				tError.functionName = "calYearRateByDate";
				tError.errorMessage = "获得年利率查询算法时产生错误！" + "保全项目：" + tEdorType
						+ " 险种编码：" + tRiskCode;
				this.mErrors.addOneError(tError);
				mCountResult = -1.0;
				return false;
			}
		}
		if (tLMEdorCalDB.mErrors.needDealError()) {
			// 查询公共的
			tLMEdorCalDB.setRiskCode("000000");
			tLMEdorCalDB.setEdorType(tEdorType);
			tLMEdorCalDB.setCalType("Rate");
			tLMEdorCalSet = tLMEdorCalDB.query();

			if (tLMEdorCalSet == null || tLMEdorCalSet.size() <= 0) {
				mErrors.copyAllErrors(tLMEdorCalDB.mErrors);
				mCountResult = -1.0;
				return false;
			}
		}
		// 组织要素
		BqCalBase tBqCalBase = new BqCalBase();
		tBqCalBase.setStartDate(tFindDate);
		tBqCalBase.setBonusRate(this.getBonusRate(tFindDate, tRiskCode));
		// 开始计算
		BqCalBL tBqCalBL = new BqCalBL();
		mCountResult = tBqCalBL.calChgMoney(tLMEdorCalSet.get(1).getCalCode(),
				tBqCalBase);
		if (tBqCalBL.mErrors.needDealError()) {
			mErrors.copyAllErrors(tBqCalBL.mErrors);
			mCountResult = -1.0;
			return false;
		}
		return true;
	}

	/**
	 * 计算某个时间点的利息增长比率（例如用本次自垫的钱乘此比率就是本次本息和），以结束日期做基准 如果产生错误则返回-1
	 * 
	 * @param String
	 *            tEdorType 批改类型
	 * @param String
	 *            tCorpusDate 本金产生日期 YYYY-MM-DD
	 * @param String
	 *            tCountDate 计算日期 YYYY-MM-DD
	 * @param String
	 *            tRiskCode 传这个参数是为了（将来）把主险和长期附加险分开算的时候扩充用的
	 * @param String
	 *            tIntvFlag 缴费间隔标志 "M" or "Y"
	 * @return boolean true-成功，false-失败。
	 */
	public boolean calCorpusDateGainInRateWithCountDate(String tEdorType,
			String tCorpusDate, String tCountDate, String tRiskCode,
			String tIntvFlag) {
		try {
			// ===add===zhangtao===2006-06-30===无论年交或是月交，统一按月计息=======BGN=======
			if (tIntvFlag.equals("M") || tIntvFlag.equals("m")) {
				tIntvFlag = "Y";
			}
			// ===add===zhangtao===2006-06-30===无论年交或是月交，统一按月计息=======END=======
			// 计算用变量
			double tRdouble = 1.0;
			if (tIntvFlag.equals("Y") || tIntvFlag.equals("y")) {
				// ===add===zhangtao===2006-06-17===按月计息改造支持零头天按日计息而不是按正月计息=======BGN=======
				String mMonthInterestCalType = getMonthInterestCalType();
				if (mMonthInterestCalType.equals("D")) // 按月计息计算方式为 D-零头天按日计息
				{
					int tInterval_M = PubFun.calInterval(tCorpusDate,
							tCountDate, "M");

					String sStartDate = PubFun.calDate(tCorpusDate,
							tInterval_M, "M", null);
					int tInterval_D = 1 + PubFun.calInterval(sStartDate,
							tCountDate, "D");

					String sRateDate = ""; // 利率时点
					// 计算整月份利息
					for (int i = 0; i < tInterval_M; i++) {
						sRateDate = PubFun.calDate(tCorpusDate, i, "M", null);
						if (!calYearRateByDate(tEdorType, sRateDate, tRiskCode)) {
							mCountResult = -1.0;
							return false;
						}
						// 连乘
						tRdouble = tRdouble
								* (double) Math.pow(
										(double) ((double) 1.0 + mCountResult),
										(double) (1.0 / 12.0));
					}
					// 计算零头天利息
					for (int i = 0; i < tInterval_D; i++) {
						sRateDate = PubFun.calDate(sStartDate, i, "D", null);
						if (!calYearRateByDate(tEdorType, sRateDate, tRiskCode)) {
							mCountResult = -1.0;
							return false;
						}
						// 连乘
						tRdouble = tRdouble
								* (double) Math.pow(
										(double) ((double) 1.0 + mCountResult),
										(double) (1.0 / 365.0));
					}
					mCountResult = tRdouble;
					return true;
				}
				// ===add===zhangtao===2006-06-17===按月计息改造支持零头天按日计息而不是按正月计息=======END=======
				// ----------年缴---------->
				// 计算从本金产生日期到计算日期共有几个整月，不足一个月按一个月处理
				int tWholeMonthNum = calDaysBetween(tCorpusDate, tCountDate,
						tIntvFlag);
				String tEveryMonthPayDate = "";
				// 开始计算
				for (int i = 0; i < tWholeMonthNum; i++) {
					// 获得月对应日，此日只做计算用，没有实际意义
					tEveryMonthPayDate = PubFun.calDate(tCorpusDate, i, "M",
							null);
					// 获得月对应日对应年利率
					if (!calYearRateByDate(tEdorType, tEveryMonthPayDate,
							tRiskCode)) {
						mCountResult = -1.0;
						return false;
					}
					// 连乘
					tRdouble = tRdouble
							* (double) Math.pow(
									(double) ((double) 1.0 + mCountResult),
									(double) (1.0 / 12.0));
				}
				mCountResult = tRdouble;
				return true;
			} else if (tIntvFlag.equals("M") || tIntvFlag.equals("m")) {
				// ----------月缴---------->
				// 计算从本金产生日期到计算日期共有几天，不足一天按一天处理
				int tWholeDaysNum = calDaysBetween(tCorpusDate, tCountDate,
						tIntvFlag);
				String tEveryDate = "";
				// 开始计算
				for (int i = 0; i < tWholeDaysNum; i++) {
					// 获得每一天，此日只做计算用，没有实际意义
					tEveryDate = PubFun.calDate(tCorpusDate, i, "D", null);
					// 获得每一天对应年利率
					if (!calYearRateByDate(tEdorType, tEveryDate, tRiskCode)) {
						mCountResult = -1.0;
						return false;
					}
					// 连乘
					tRdouble = tRdouble
							* (double) Math.pow(
									(double) ((double) 1.0 + mCountResult),
									(double) (1.0 / 365.0));
				}
				mCountResult = tRdouble;
				return true;
			}
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "AccountManage";
			tError.functionName = "calCorpusDateGainInRateWithCountDate";
			tError.errorMessage = "输入的缴费间隔标志错误！";
			this.mErrors.addOneError(tError);
			mCountResult = -1.0;
			return false;
		} catch (Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "AccountManage";
			tError.functionName = "calCorpusDateGainInRateWithCountDate";
			tError.errorMessage = e.toString();
			this.mErrors.addOneError(tError);
			mCountResult = -1.0;
			return false;
		}
	}

	/**
	 * 计算某个时间点的本息和，以结束日期做基准 如果产生错误则返回-1
	 * 
	 * @param String
	 *            tEdorType 批改类型
	 * @param double
	 *            tCorpus 本金
	 * @param String
	 *            tCorpusDate 本金产生日期 YYYY-MM-DD
	 * @param String
	 *            tCountDate 计算日期 YYYY-MM-DD
	 * @param String
	 *            tRiskCode 传这个参数是为了（将来）把主险和长期附加险分开算的时候扩充用的
	 * @param String
	 *            tIntvFlag 缴费间隔标志 "M" or "Y"
	 * @return boolean true-成功，false-失败。
	 */
	public boolean calCorpusAddInterest(String tEdorType, double tCorpus,
			String tCorpusDate, String tCountDate, String tRiskCode,
			String tIntvFlag) {
		try {
			if (!calCorpusDateGainInRateWithCountDate(tEdorType, tCorpusDate,
					tCountDate, tRiskCode, tIntvFlag)) {
				return false;
			}
			mCountResult = tCorpus * mCountResult;
			return true;
		} catch (Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "AccountManage";
			tError.functionName = "calCorpusAddInterest";
			tError.errorMessage = e.toString();
			this.mErrors.addOneError(tError);
			mCountResult = -1.0;
			return false;
		}
	}

	/**
	 * 计算某个时间点的本息和，以结束日期做基准 如果产生错误则返回-1
	 * 
	 * @param String
	 *            tEdorType 批改类型
	 * @param String
	 *            tCorpus 本金
	 * @param String
	 *            tCorpusDate 本金产生日期 YYYY-MM-DD
	 * @param String
	 *            tCountDate 计算日期 YYYY-MM-DD
	 * @param String
	 *            tRiskCode 传这个参数是为了（将来）把主险和长期附加险分开算的时候扩充用的
	 * @param String
	 *            tIntvFlag 缴费间隔标志 "M" or "Y"
	 * @return boolean true-成功，false-失败。
	 */
	public boolean calCorpusAddInterest(String tEdorType, String tCorpus,
			String tCorpusDate, String tCountDate, String tRiskCode,
			String tIntvFlag) {
		try {
			double tV = Double.parseDouble(tCorpus);
			return calCorpusAddInterest(tEdorType, tV, tCorpusDate, tCountDate,
					tRiskCode, tIntvFlag);
		} catch (Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "AccountManage";
			tError.functionName = "calCorpusAddInterest";
			tError.errorMessage = e.toString();
			this.mErrors.addOneError(tError);
			mCountResult = -1.0;
			return false;
		}
	}

	/**
	 * 计算某个时间点的利息，以结束日期做基准 如果产生错误则返回-1
	 * 
	 * @param String
	 *            tEdorType 批改类型
	 * @param double
	 *            tCorpus 本金
	 * @param String
	 *            tCorpusDate 本金产生日期 YYYY-MM-DD
	 * @param String
	 *            tCountDate 计算日期 YYYY-MM-DD
	 * @param String
	 *            tRiskCode 传这个参数是为了（将来）把主险和长期附加险分开算的时候扩充用的
	 * @param String
	 *            tIntvFlag 缴费间隔标志 "M" or "Y"
	 * @return boolean true-成功，false-失败。
	 */
	public boolean calInterest(String tEdorType, double tCorpus,
			String tCorpusDate, String tCountDate, String tRiskCode,
			String tIntvFlag) {
		try {
			if (!calCorpusDateGainInRateWithCountDate(tEdorType, tCorpusDate,
					tCountDate, tRiskCode, tIntvFlag)) {
				return false;
			}
			mCountResult = tCorpus * (mCountResult - 1.0);
			return true;
		} catch (Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "AccountManage";
			tError.functionName = "calInterest";
			tError.errorMessage = e.toString();
			this.mErrors.addOneError(tError);
			mCountResult = -1.0;
			return false;
		}
	}

	/**
	 * 计算某个时间点的利息，以结束日期做基准 如果产生错误则返回-1
	 * 
	 * @param String
	 *            tEdorType 批改类型
	 * @param String
	 *            tCorpus 本金
	 * @param String
	 *            tCorpusDate 本金产生日期 YYYY-MM-DD
	 * @param String
	 *            tCountDate 计算日期 YYYY-MM-DD
	 * @param String
	 *            tRiskCode 传这个参数是为了（将来）把主险和长期附加险分开算的时候扩充用的
	 * @param String
	 *            tIntvFlag 缴费间隔标志 "M" or "Y"
	 * @return boolean true-成功，false-失败。
	 */
	public boolean calInterest(String tEdorType, String tCorpus,
			String tCorpusDate, String tCountDate, String tRiskCode,
			String tIntvFlag) {
		try {
			double tV = Double.parseDouble(tCorpus);
			return calInterest(tEdorType, tV, tCorpusDate, tCountDate,
					tRiskCode, tIntvFlag);
		} catch (Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "AccountManage";
			tError.functionName = "calInterest";
			tError.errorMessage = e.toString();
			this.mErrors.addOneError(tError);
			mCountResult = -1.0;
			return false;
		}
	}

	// 根据一个日期算此日期到结束日期有多少个月，不足一个月按一个月算，注意：如果计算日是结束日期则也算一个月！
	private static int calDaysBetween(String tStartDate, String tEndDate,
			String tIntvFlag) {
		int tWholeMonthNum = -1;
		if (tIntvFlag.equals("Y") || tIntvFlag.equals("y")) {
			tWholeMonthNum = PubFun.calInterval(tStartDate, tEndDate, "M") + 1;
			// 下面这段注释掉是因为：如果业务操作动作（如自垫）产生当天客户来清偿，则当日生成的钱也要记一个月的利息（对于年缴），所以在上面直接加1
			// String tStrDate =
			// PubFun.calDate(tStartDate,tWholeMonthNum,"M",null);
			// //最后一个参数写null逻辑与上面算有几个月一致
			// FDate tFDateA = new FDate();
			// FDate tFDateB = new FDate();
			// if (tFDateA.getDate(tStrDate).before(tFDateB.getDate(tEndDate)))
			// {
			// tWholeMonthNum++;
			// }
		} else {
			tWholeMonthNum = PubFun.calInterval(tStartDate, tEndDate, "D") + 1;
		}
		return tWholeMonthNum;
	}

	/**
	 * 获得年度红利率，作为获得年利率的一个要素 注意：这里现在是按险种处理的，今后产品定义可能按责任处理，到时可能还要修改
	 * 目前用此函数的类是自垫清偿，贷款，贷款清偿相关，要件变更
	 * 
	 * @param tFindDate
	 * @param tRiskCode
	 * @return
	 */
	public double getBonusRate(String tFindDate, String tRiskCode) {
		// 先判断是否是分红险，不是就直接通过了
		String tSql = "SELECT" + " case"
				+ " when exists(select 'X' from LMRiskApp where RiskCode='"
				+ "?bb1?" + "' and BonusFlag in ('1','2')) then '1'"
				+ " else '0'" + " end" + " FROM dual";
		SQLwithBindVariables sqlbv13=new SQLwithBindVariables();
	    sqlbv13.sql(tSql);
	    sqlbv13.put("bb1",tRiskCode);
		SSRS tSSRS = new SSRS();
		ExeSQL tExeSQL = new ExeSQL();
		tSSRS = tExeSQL.execSQL(sqlbv13);
		if (tSSRS == null || tSSRS.MaxRow <= 0
				|| tSSRS.GetText(1, 1).equals("0")) {
			// 不是分红险
			return 0.0;
		}

		// 是分红险
		double tBonusRate = 0.0;
		// 查询算法编码
		LMEdorCalDB tLMEdorCalDB = new LMEdorCalDB();
		tLMEdorCalDB.setRiskCode(tRiskCode);
		tLMEdorCalDB.setCalType("BonusRate");
		LMEdorCalSet tLMEdorCalSet = tLMEdorCalDB.query(); // 查找保费累计变动金额计算信息
		if (tLMEdorCalSet == null || tLMEdorCalSet.size() <= 0) {
			return 0.0;
		}
		if (tLMEdorCalDB.mErrors.needDealError()) {
			return 0.0;
		}
		// 组织要素
		Calculator tCalculator = new Calculator();
		tCalculator.setCalCode(tLMEdorCalSet.get(1).getCalCode());
		tCalculator.addBasicFactor("EdorValiDate", tFindDate);
		tCalculator.addBasicFactor("PayEndYear", this.mPayEndYear);
		tCalculator.addBasicFactor("PayIntv", this.mPayIntv);
		tCalculator.addBasicFactor("ForceDVFlag", "1"); // 是否强制分红标志，0：正常分红；1：强制分红
		// 开始计算6
		String tBR = tCalculator.calculate();
		if (tCalculator.mErrors.needDealError()) {
			mErrors.copyAllErrors(tCalculator.mErrors);
			return 0.0;
		}

		try {
			tBonusRate = Double.parseDouble(tBR);
		} catch (Exception e) {
			// CError.buildErr(this, "未获取年度红利利率！");
			logger.debug("未获取年度红利利率！");
			return 0.0;
		}
		return tBonusRate;
	}

	// 四舍六入五靠偶数，保留两位
	private double getRound(double tValue) {
//		String t = "0.00";
//		DecimalFormat tDF = new DecimalFormat(t);
//		return Double.parseDouble(tDF.format(tValue));
		//MS采用通常意义上的四舍五入规则而不是上述的四舍六入五靠偶数
		//modify by jiaqiangli 2008-10-30 lis65程序四舍五入调用方法
		//修改子程序接口
		return PubFun.round(tValue,2);
	}

	// ===add===zhangtao===2006-06-17===按月计息改造支持零头天按日计息而不是按正月计息=======BGN=======
	/**
	 * 获取系统按月计息计算方式 add by zhangtao 2006-06-17
	 * 
	 * @return String M-不满一个月按一个月月计算 D-零头天按日计息
	 */
	public static String getMonthInterestCalType() {
		String sInterestCalType = "M";
		try {
			String sql = " select trim(sysvarvalue) from ldsysvar where sysvar = 'MonthInterestCalType' ";
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql(sql);
			ExeSQL tExeSQL = new ExeSQL();
			sInterestCalType = tExeSQL.getOneValue(sqlbv);
			if (tExeSQL.mErrors.needDealError()) {
				return "M"; // 默认值（不满一个月按一个月月计算）
			}
			if (sInterestCalType == null || sInterestCalType.equals("")) {
				sInterestCalType = "M"; // 默认值（不满一个月按一个月月计算）
			}
		} catch (Exception e) {
		} finally {
			return sInterestCalType;
		}
	}

	// ===add===zhangtao===2006-06-17===按月计息改造支持零头天按日计息而不是按正月计息=======END=======

	// ===add===zhangtao===2006-08-02===账户价值计算=========================BGN=======
	/**
	 * 分账户明细（子账户）计算帐户余额
	 * 
	 * @param pLCInsureAccClassSchema
	 * @param sCurBalaDate
	 *            本次计算日期
	 * @return TransferData 返回账户余额及本次结算利息
	 */
	public TransferData getAccClassBalance(
			LCInsureAccClassSchema pLCInsureAccClassSchema, String sCurBalaDate) {
		// 本次结算利息
		double dAccInterest = 0.0;
		// 本次结算后账户余额（本息和）
		double dAccBala = 0.0;

		// 最近一次结算日期
		String sLastBalaDate = pLCInsureAccClassSchema.getBalaDate();
		// 最近一次结算时间
		String sLastBalaTime = pLCInsureAccClassSchema.getBalaTime();
		// 最近一次结算时点帐户余额
		double dLastInsuAccBala = pLCInsureAccClassSchema.getInsuAccBala();

		// 查询该账户固定利率
		LMRiskInsuAccDB tLMRiskInsuAccDB = new LMRiskInsuAccDB();
		tLMRiskInsuAccDB.setInsuAccNo(pLCInsureAccClassSchema.getInsuAccNo());
		LMRiskInsuAccSet tLMRiskInsuAccSet = tLMRiskInsuAccDB.query();
		if (tLMRiskInsuAccDB.mErrors.needDealError()
				|| tLMRiskInsuAccSet == null || tLMRiskInsuAccSet.size() < 1) {
			CError.buildErr(this, "账户利率查询失败!");
			return null;
		}
		// 账户结算方式
		String sAccComputeFlag = tLMRiskInsuAccSet.get(1).getAccComputeFlag();
		// 0 －－ 不计利息
		// 1 －－ 按固定利率生息（单利）
		// 2 －－ 按固定利率生息（复利）
		// 3 －－ 按利率表生息（单利）
		// 4 －－ 按利率表生息（复利）

		String sTransType = "C"; // 单利复利标志
		if (sAccComputeFlag.equals("1") || sAccComputeFlag.equals("3")) {
			sTransType = "S"; // 单利
		} else if (sAccComputeFlag.equals("2") || sAccComputeFlag.equals("4")) {
			sTransType = "C"; // 复利
		}

		double dAccRate = tLMRiskInsuAccSet.get(1).getAccRate();

		// 对帐户余额进行结息（计算起期：上次结算时点 计算止期：本次计算时点）
		if (sAccComputeFlag.equals("1") || sAccComputeFlag.equals("2")) // 固定利率计息
		{
			dAccInterest += AccountManage.getMultiAccInterest(dAccRate, // 利率
					dLastInsuAccBala, // 本金
					sLastBalaDate, // 计息起期
					sCurBalaDate, // 计息止期
					sTransType, // 单利复利标志
					"D"); // 按日计息
		} else if (sAccComputeFlag.equals("3") || sAccComputeFlag.equals("4")) // 按利率表分段计息
		{
			String[] sRates = getMultiAccRateNew(pLCInsureAccClassSchema
					.getInsuAccNo(), tLMRiskInsuAccSet.get(1), sLastBalaDate,
					sCurBalaDate, "Y", sTransType, "D", 0, "F", "D");
			int iCount = Integer.parseInt(sRates[0]);
			double dSubRate = 0.0;
			for (int m = 1; m < iCount + 1; m++) {
				if (sRates[m] == null) {
					sRates[m] = "0";
				}
				dSubRate = Double.parseDouble(sRates[m]);
				dAccInterest += dLastInsuAccBala * dSubRate;
			}
		}

		// 对上次结算之后账户轨迹表中的新的帐务数据进行结息
		String sql = "  select * from lcinsureacctrace " + "  where polno = '"
				+ "?bb2?" + "' and insuaccno = '"
				+ "?bb3?"
				+ "' and payplancode = '"
				+ "?bb4?"
				+ "' and otherno = '" + "?bb5?"// del
																				// by
																				// zhangtao
																				// 2006-08-14
																				// 个人未归属部分转入公共帐户后otherno
																				// 保存原PolNo
				// + "' and accascription = '" +
				// pLCInsureAccClassSchema.getAccAscription() //del by zhangtao
				// 2006-08-14 是否归属从class层面判断即可，trace无需再做判断
				+ "' order by paydate, makedate, maketime ";
		SQLwithBindVariables sqlbv14=new SQLwithBindVariables();
	    sqlbv14.sql(sql);
	    sqlbv14.put("bb2",pLCInsureAccClassSchema.getPolNo());
	    sqlbv14.put("bb3",pLCInsureAccClassSchema.getInsuAccNo());
	    sqlbv14.put("bb4",pLCInsureAccClassSchema.getPayPlanCode());
	    sqlbv14.put("bb5",pLCInsureAccClassSchema.getOtherNo());
		logger.debug(sql);

		LCInsureAccTraceDB tLCInsureAccTraceDB = new LCInsureAccTraceDB();
		LCInsureAccTraceSet tLCInsureAccTraceSet = tLCInsureAccTraceDB
				.executeQuery(sqlbv14);
		if (tLCInsureAccTraceDB.mErrors.needDealError()) {

			CError.buildErr(this, "账户轨迹查询失败!"
					+ tLCInsureAccTraceDB.mErrors.getErrContent());
			return null;
		}
		if (tLCInsureAccTraceSet != null && tLCInsureAccTraceSet.size() > 0) {
			double dMoney = 0.0;
			String sMoneyType = "";
			String sPaydate = "";
			String sMakeTime = "";
			for (int i = 1; i <= tLCInsureAccTraceSet.size(); i++) {
				sMoneyType = tLCInsureAccTraceSet.get(i).getMoneyType();
				if (sMoneyType.equals("")) {
					continue; // 某些特殊的帐务记录不需要结息，暂时未定 2006-08-02
				}

				dMoney = tLCInsureAccTraceSet.get(i).getMoney();
				sMoneyType = tLCInsureAccTraceSet.get(i).getMoneyType();
				sPaydate = tLCInsureAccTraceSet.get(i).getPayDate();
				sMakeTime = tLCInsureAccTraceSet.get(i).getMakeTime();

				// 在上次结算时点之前的帐务记录不用再做结息
				int tIntv = PubFun.calInterval(sLastBalaDate, sPaydate, "D");
				if (tIntv < 0) {
					continue;
				}
				if (tIntv == 0 && sLastBalaTime.compareTo(sMakeTime) >= 0) {
					continue;
				}

				dAccBala += dMoney;

				// 对每笔帐务进行结息（计算起期：进、出账时点 计算止期：本次计算时点）
				if (sAccComputeFlag.equals("1") || sAccComputeFlag.equals("2")) // 固定利率计息
				{
					dAccInterest += AccountManage.getMultiAccInterest(dAccRate, // 利率
							dMoney, // 本金
							sPaydate, // 计息起期
							sCurBalaDate, // 计息止期
							sTransType, // 单利复利标志
							"D"); // 按日计息
				} else if (sAccComputeFlag.equals("3")
						|| sAccComputeFlag.equals("4")) // 按利率表分段计息
				{
					String[] sRates = getMultiAccRateNew(
							pLCInsureAccClassSchema.getInsuAccNo(),
							tLMRiskInsuAccSet.get(1), sPaydate, sCurBalaDate,
							"Y", sTransType, "D", 0, "F", "D");
					int iCount = Integer.parseInt(sRates[0]);
					double dSubRate = 0.0;
					for (int m = 1; m < iCount + 1; m++) {
						if (sRates[m] == null) {
							sRates[m] = "0";
						}
						dSubRate = Double.parseDouble(sRates[m]);
						dAccInterest += dMoney * dSubRate;
					}
				}
			}
		}

		dAccBala += dLastInsuAccBala + dAccInterest;

		// 将计算结果打包返回
		TransferData rTransferData = new TransferData();
		rTransferData.setNameAndValue("AccInterest", dAccInterest);
		rTransferData.setNameAndValue("AccBala", dAccBala);
		return rTransferData;
	}

	/**
	 * * 根据分段利率获取相应的计息乘积系数（2006-08-03 从泰康拿过来）
	 * 
	 * @return
	 */
	private String[] getMultiAccRateNew(String aInsuAccNo,
			LMRiskInsuAccSchema aLMRiskInsuAccSchema, String aOriginBalaDate,
			String aBalanceDate, String aRateType, String aTransType,
			String aIntervalType, int aPeriod, String aType, String aDepst) {
		String tSql = "";
		String[] ResultArray = new String[100];
		Calculator tCalculator = new Calculator();
		String TableName = aLMRiskInsuAccSchema.getAccRateTable();
		if (aLMRiskInsuAccSchema.getAccCancelCode() != null
				&& !aLMRiskInsuAccSchema.getAccCancelCode().equals("")) {
			try {
				tSql = PubFun1.getSQL(aLMRiskInsuAccSchema.getAccCancelCode(),
						tCalculator);
			} catch (Exception e) {
			}
		} else {
			TableName = aLMRiskInsuAccSchema.getAccRateTable();
			if (TableName == null || TableName == "null"
					|| TableName.toUpperCase().equals("LDBANKRATE")) {
				tSql = "select * from LDBankRate where "
						+ " DeclareDate <= to_date('" + "?aBalanceDate?"
						+ "','yyyy-mm-dd') and EndDate >= to_date('"
						+ "?aOriginBalaDate?" + "','yyyy-mm-dd') and Period = "
						+ "?aPeriod?" + "";
				if (aRateType != null) {
					tSql = tSql + " and PeriodFlag = '" + "?aRateType?" + "'";
				}
				if (aType != null) {
					tSql = tSql + " and Type = '" + "?aType?" + "'";
				}
				if (aDepst != null) {
					tSql = tSql + " and Depst_Loan = '" + "?aDepst?" + "'";
				}
				tSql = tSql + " order by EndDate";
                SQLwithBindVariables sqlbva = new SQLwithBindVariables();
                sqlbva.sql(tSql);
                sqlbva.put("aBalanceDate", aBalanceDate);
                sqlbva.put("aOriginBalaDate", aOriginBalaDate);
                sqlbva.put("aPeriod", aPeriod);
                sqlbva.put("aRateType", aRateType);
                sqlbva.put("aType", aType);
                sqlbva.put("aDepst", aDepst);
				// 取银行获取利率
				ResultArray = getBankRate(sqlbva, aRateType, aTransType,
						aIntervalType, aOriginBalaDate, aBalanceDate);
			} else {
				tSql = "select Rate, EndDate from " + TableName
						+ " where InsuAccNo = '" + "?aInsuAccNo?"
						+ "' and StartDate <= to_date('" + "?aBalanceDate?"
						+ "','yyyy-mm-dd') and EndDate >= to_date('"
						+ "?aOriginBalaDate?" + "','yyyy-mm-dd') order by EndDate";
				    SQLwithBindVariables sqlbva = new SQLwithBindVariables();
	                sqlbva.sql(tSql);
	                sqlbva.put("aInsuAccNo", aInsuAccNo);
	                sqlbva.put("aBalanceDate", aBalanceDate);
	                sqlbva.put("aOriginBalaDate", aOriginBalaDate);
				// 取定期利率
				ResultArray = getTablRate(sqlbva, aRateType, TableName,
						aTransType, aIntervalType, aOriginBalaDate,
						aBalanceDate);
			}
		}

		return ResultArray;
	}

	/**
	 * * 从银行利率表中根据分段利率获取相应的计息乘积系数（2006-08-03 从泰康拿过来）
	 * 
	 * @return
	 */
	private String[] getBankRate(SQLwithBindVariables tSql, String aRateType,
			String aTransType, String aIntervalType, String aOriginBalaDate,
			String aBalanceDate) {

		String aDate = "";
		double aValue = 0.0;
		double aResult = 0.0;
		String tStartDate = "";
		String[] ResultArray = new String[100];

		LDBankRateDB tLDBankRateDB = new LDBankRateDB();
		LDBankRateSet tLDBankRateSet = new LDBankRateSet();
		tLDBankRateSet = tLDBankRateDB.executeQuery(tSql);
		for (int i = 1; i <= tLDBankRateSet.size(); i++) {
			LDBankRateSchema tLDBankRateSchema = new LDBankRateSchema();
			tLDBankRateSchema = tLDBankRateSet.get(i);
			aDate = tLDBankRateSchema.getEndDate();
			aValue = tLDBankRateSchema.getRate();
			// 原始利率转换为目标利率
			double tCalAccRate = TransAccRate(aValue, aRateType, aTransType,
					aIntervalType);
			if (i == 1) {
				tStartDate = aOriginBalaDate;
			}

			// 结息点超过某个利率临界点
			if (PubFun.calInterval(aDate, aBalanceDate, aIntervalType) > 0) {
				int tInterval = PubFun.calInterval(tStartDate, aDate,
						aIntervalType);
				if (tInterval > 0) {
					// 计算一定时间间隔下的利率
					aResult = getIntvRate(tInterval, tCalAccRate, aTransType);
					ResultArray[i] = String.valueOf(aResult);
				}
				tStartDate = aDate;
			} else {
				int tInterval = PubFun.calInterval(tStartDate, aBalanceDate,
						aIntervalType);
				if (tInterval > 0) {
					// 计算一定时间间隔下的利率
					aResult = getIntvRate(tInterval, tCalAccRate, aTransType);
					ResultArray[i] = String.valueOf(aResult);
				}
				break;
			}
		}
		ResultArray[0] = String.valueOf(tLDBankRateSet.size());

		return ResultArray;
	}
	
	/**
	 * * 从账户利率表中根据分段利率获取相应的计息乘积系数（2006-08-03 从泰康拿过来）
	 * 
	 * @return
	 */
	private String[] getTablRate(SQLwithBindVariables tSql, String aRateType,
			String tTabName, String aTransType, String aIntervalType,
			String aOriginBalaDate, String aBalanceDate) {
		String aDate = "";
		double aValue = 0.0;
		double aResult = 0.0;
		String tStartDate = "";
		SSRS tSSRS = new SSRS();
		String[] ResultArray = new String[100];
		ExeSQL tExeSQL = new ExeSQL();
		tSSRS = tExeSQL.execSQL(tSql);
		if (tSSRS == null || tSSRS.MaxRow <= 0) {
			logger.debug(tTabName + "表查询利率为空！");
			return null;
		}
		int MaxRow = tSSRS.getMaxRow();
		for (int i = 1; i <= MaxRow; i++) {

			aValue = Double.parseDouble(tSSRS.GetText(i, 1));
			aDate = String.valueOf(tSSRS.GetText(i, 2));
			double tCalAccRate = TransAccRate(aValue, aRateType, aTransType,
					aIntervalType);
			if (i == 1) {
				tStartDate = aOriginBalaDate;
			}

			// 结息点超过某个利率临界点
			if (PubFun.calInterval(aDate, aBalanceDate, aIntervalType) > 0) {
				int tInterval = PubFun.calInterval(tStartDate, aDate,
						aIntervalType);
				if (tInterval > 0) {
					aResult = getIntvRate(tInterval, tCalAccRate, aTransType);
					ResultArray[i] = String.valueOf(aResult);
				}
				tStartDate = aDate;
			} else {
				int tInterval = PubFun.calInterval(tStartDate, aBalanceDate,
						aIntervalType);
				if (tInterval > 0) {
					aResult = getIntvRate(tInterval, tCalAccRate, aTransType);
					ResultArray[i] = String.valueOf(aResult);
				}
				break;
			}
		}
		ResultArray[0] = String.valueOf(MaxRow);

		return ResultArray;
	}

	public TransferData getAccClassInterestForCSrv(
			LCInsureAccClassSchema aLCInsureAccClassSchema, String aBalaDate,
			String aRateType, String aIntvType, int Period, String tType,
			String Depst) {

		logger.debug("=====This is getAccClassInterestNew!=====\n");

		// 记录帐户分类表的利息值
		double aAccClassInterest = 0.0;

		// 记录帐户分类表的本息和
		double aAccClassSumPay = 0.0;

		// 记录返回值利息和本息
		TransferData aAccClassRet = new TransferData();

		// 检验数据有效性
		if (!verifyNotNull("当前结息日期", aBalaDate)) {
			returnNull(aAccClassRet);
			return aAccClassRet;
		}
		if (!verifyNotNull("原始利率类型", aRateType)) {
			returnNull(aAccClassRet);
			return aAccClassRet;
		}
		if (!verifyNotNull("目标利率类型", aIntvType)) {
			returnNull(aAccClassRet);
			return aAccClassRet;
		}

		// 记录结息间隔
		int tInterval = 0;

		// 记录查询LCInsureAccClassTrace表返回有效记录的个数
		int tCount = 0;

		// 记录原始利率值
		double tAccClassRate;

		// 记录目标利率值
		double tCalAccClassRate;

		// 记录保险帐户现金余额
		double tInsuAccClassBala = aLCInsureAccClassSchema.getInsuAccBala(); // 记录保险帐户现金余额

		// 得到险种保险帐户描述表(lmRiskInsuAcc)中帐户利率
		// LMRiskInsuAccDB tLMRiskInsuAccDB = new LMRiskInsuAccDB();

		// 记录保险帐户号码
		String aInsuAccNo = aLCInsureAccClassSchema.getInsuAccNo();

		// 记录帐户分类表上次结息日期
		String tBalaDate = aLCInsureAccClassSchema.getBalaDate();

		// 记录帐户分类表上次结息时间
		String tBalaTime = aLCInsureAccClassSchema.getBalaTime();

		// 上次结息日期为空或者不存在则取入机日期
		if (tBalaDate == null || tBalaDate.equals("")) {
			tBalaDate = aLCInsureAccClassSchema.getMakeDate();
		}

		// 上次结息时间如果为空或者不存在则取入机时间
		if (tBalaTime == null || tBalaTime.equals("")) {
			tBalaTime = aLCInsureAccClassSchema.getMakeTime();
		}
		// tLMRiskInsuAccDB.setInsuAccNo(aInsuAccNo);
		LCInsureAccTraceDB tLCInsureAccTraceDB = new LCInsureAccTraceDB();
		LCInsureAccTraceSet tLCInsureAccTraceSet = new LCInsureAccTraceSet();

		LMRiskInsuAccSchema tLMRiskInsuAccSchema = new LMRiskInsuAccSchema();
		tLMRiskInsuAccSchema = mCRI.findRiskInsuAccByInsuAccNo(aInsuAccNo);
		double tInsureAccTraceMoneySum = 0.0;
		String[] ResultRate = null;
		if (tLMRiskInsuAccSchema.getInsuAccNo() == null
				|| tLMRiskInsuAccSchema.getInsuAccNo().equals("")) {
			aAccClassRet
					.setNameAndValue("tAccClassInterest", aAccClassInterest);
			aAccClassRet.setNameAndValue("tAccClassSumPay", aAccClassSumPay);

			return aAccClassRet;
		}

		// //记录保险帐户自上次结息以后的缴费总和
		// double tInsureAccTraceMoneySum = 0.0;
		// String[] ResultRate = null;
		// if (!tLMRiskInsuAccDB.getInfo()) {
		// aAccClassRet.setNameAndValue("tAccClassInterest", aAccClassInterest);
		// aAccClassRet.setNameAndValue("tAccClassSumPay", aAccClassSumPay);
		//
		// return aAccClassRet;
		// }
		ExeSQL tExeSql = new ExeSQL();
		SSRS tSSRS = new SSRS();

		// -----testing start----20060216-----------------------
		logger.debug("************开始结息****************");
		logger.debug("保险帐户现金余额: " + tInsuAccClassBala);
		logger.debug("本次结息日期: " + aBalaDate);
		logger.debug("上次结息日期: " + tBalaDate);
		logger.debug("上次结息时间: " + tBalaTime);
		// ----------testing end--------------------------------

		// 获取固定利率
		tAccClassRate = tLMRiskInsuAccSchema.getAccRate();

		// 转换后的利率值
		tCalAccClassRate = TransAccRate(tAccClassRate, aRateType, "C",
				aIntvType);

		// 筛选出paydate > 帐户分类表的结息日期BalaDate
		// 帐户型险种，对于分红险，trace表的payno字段放置class表的otherno字段的值；
		// String tSql = "select BonusFlag from LMRiskApp a where exists " +
		// "(select * from LCPol where PolNo = '" +
		// aLCInsureAccClassSchema.getPolNo() +
		// "' and RiskCode = a.RiskCode)";
		// try{
		// tSSRS = tExeSql.execSQL(tSql);
		// } catch (Exception ex){
		// CError.buildErr(this, ex.toString());
		// returnNull(aAccClassRet);
		// return aAccClassRet;
		// }
		// String tBonusFlag = tSSRS.GetText(1, 1);
		//
		// ExeSQL ttExeSql = new ExeSQL();
		// SSRS ttSSRS = new SSRS();
		String tBonusFlag = tLMRiskInsuAccSchema.getAccKind();
		// 分红险特殊处理
		String tOtherNo = aLCInsureAccClassSchema.getOtherNo();
		if (tBonusFlag.equals("1")) {
			if (tOtherNo.equals("")) {
				CError.buildErr(this, "保险帐户分类表数据不完整！");
				returnNull(aAccClassRet);
				return aAccClassRet;
			}
			tLCInsureAccTraceDB.setPayNo(tOtherNo);
		}
		FDate tD = new FDate();

		Date aNewBalaDate = PubFun.calDate(tD.getDate(aBalaDate), 1, "Y", null);
		String aNewEndDate = tD.getString(aNewBalaDate);

		String tSql = "select * from LCInsureAccTrace where polno='"
				+ "?v1?" + "' and InsuAccNo='"
				+ "?v2?" + "' and PayPlanCode='"
				+ "?v3?" + "' and PayNo='"
				+ "?v4?" + "' and (PayDate>'" + "?v5?" + "' or (PayDate='"
				+ "?v6?" + "' and MakeTime>'" + "?v7?"
				+ "')) and PayDate<='" + "?v8?" + "'";
		SQLwithBindVariables sqlbv20=new SQLwithBindVariables();
	    sqlbv20.sql(tSql);
	    sqlbv20.put("v1",aLCInsureAccClassSchema.getPolNo());
	    sqlbv20.put("v2", aInsuAccNo);
	    sqlbv20.put("v3", aLCInsureAccClassSchema.getPayPlanCode());
	    sqlbv20.put("v4", tOtherNo);
	    sqlbv20.put("v5", tBalaDate);
	    sqlbv20.put("v6", tBalaDate);
	    sqlbv20.put("v7", tBalaTime);
	    sqlbv20.put("v8", aNewEndDate);
		logger.debug("------" + tSql);
		/*
		 * tLCInsureAccTraceDB.setPolNo(aLCInsureAccClassSchema.getPolNo());
		 * tLCInsureAccTraceDB.setInsuAccNo(aInsuAccNo);
		 * tLCInsureAccTraceDB.setPayPlanCode(aLCInsureAccClassSchema.
		 * getPayPlanCode());
		 * //tLCInsureAccTraceDB.setOtherNo(aLCInsureAccClassSchema.getOtherNo());
		 * tLCInsureAccTraceDB.setAccAscription(aLCInsureAccClassSchema.
		 * getAccAscription()); tLCInsureAccTraceSet =
		 * tLCInsureAccTraceDB.executeQuery(tSql);
		 */

		tLCInsureAccTraceSet = tLCInsureAccTraceDB.executeQuery(sqlbv20);

		for (int i = 1; i <= tLCInsureAccTraceSet.size(); i++) {
			LCInsureAccTraceDB tmpLCInsuAccTraceDB = new LCInsureAccTraceDB();
			tmpLCInsuAccTraceDB.setSchema(tLCInsureAccTraceSet.get(i));

			// 如果LCInsureAccTrace表中的moneyType字段的值为PX或者SX,则不作结息;
			String tMoneyType = tmpLCInsuAccTraceDB.getMoneyType();
			if (tMoneyType.equals("PX") || tMoneyType.equals("SX")) {
				continue;
			}

			// 交费日期
			String payDate = tmpLCInsuAccTraceDB.getPayDate();

			// 交费生效时间
			String tMakeTime = tmpLCInsuAccTraceDB.getMakeTime();

			double tempMoney = tmpLCInsuAccTraceDB.getMoney();
			tInsureAccTraceMoneySum += tempMoney;

			// 然后对轨迹表的这笔钱tempMoney作结息
			// jixf add 20050802 对于PayDate>aBalaDate的情况，tInterval有问题，反之没有问题
			logger.debug("------" + payDate);
			logger.debug("------" + aBalaDate);
			logger.debug("-%%%%%%%%%--===="
					+ payDate.compareTo(aBalaDate));
			if (payDate.compareTo(aBalaDate) > 0) {
				logger.debug("33333");
				tInterval = PubFun.calInterval(aBalaDate, payDate, aIntvType);
				tInterval = 0 - tInterval;
			} else {

				tInterval = PubFun.calInterval(payDate, aBalaDate, aIntvType);
			}
			// jixf chg 20060722不区分时间间隔，可以往回反结息
			aAccClassInterest += tempMoney
					* getIntvRate(tInterval, tCalAccClassRate, "C");
			aAccClassInterest = Arith.round(aAccClassInterest, 2);
			// }
		}

		// 将帐户分类表的另一部分余额结息
		tInterval = PubFun.calInterval(tBalaDate, aBalaDate, aIntvType);
		if (tInterval > 0) {
			aAccClassInterest += tInsuAccClassBala
					* getIntvRate(tInterval, tCalAccClassRate, "C");
		}

		aAccClassInterest = Arith.round(aAccClassInterest, 2);

		aAccClassSumPay = tInsuAccClassBala + tInsureAccTraceMoneySum
				+ aAccClassInterest;
		aAccClassSumPay = Arith.round(aAccClassSumPay, 2);
		// 准备返回的数据包
		aAccClassRet.setNameAndValue("aAccClassInterest", aAccClassInterest);
		aAccClassRet.setNameAndValue("aAccClassSumPay", aAccClassSumPay);

		return aAccClassRet;
	}

//	/**
//	 * 按照新的帐户结息的逻辑进行处理 计算帐户分类表的利息
//	 * 
//	 * @param aBalaDate
//	 *            本次结息日期
//	 * @param aRateType
//	 *            原始利率类型，比如年利率，月利率等
//	 * @param aIntvType
//	 *            目标利率类型，比如年利率，月利率等
//	 * @param Period
//	 *            利率期间
//	 * @param tType
//	 *            利率类型（C:活期 F:定期）
//	 * @param Depst
//	 *            贷存款标志 (D: 存款 L: 贷款) 创建人: 创建日期：2006-02-16
//	 * @return double 返回的是帐户分类表和相应的记价履历表的利息和以及总余额 修改人： 修改日期：2006-02-24
//	 *         修改内容：传输参数修改
//	 */
//	public TransferData getAccClassInterestNew(
//			LCInsureAccClassSchema aLCInsureAccClassSchema, String aBalaDate,
//			String aRateType, String aIntvType, int Period, String tType,
//			String Depst) {
//
//		logger.debug("=====This is getAccClassInterestNew!=====\n");
//
//		// 记录帐户分类表的利息值
//		double aAccClassInterest = 0.0;
//
//		// 记录帐户分类表的本息和
//		double aAccClassSumPay = 0.0;
//
//		ExeSQL tExeSql = new ExeSQL();
//		SSRS tSSRS = new SSRS();
//
//		String tsql = "select poltypeflag from lcpol where polno ='"
//				+ aLCInsureAccClassSchema.getPolNo() + "' ";
//		ExeSQL tExeSQL = new ExeSQL();
//		String tpoltypeflag = tExeSQL.getOneValue(tsql);
//
//		if (tpoltypeflag == "2" || tpoltypeflag.equals("2")) {
//			logger.debug("=====poltype=" + tpoltypeflag
//					+ "=====对公共账户重新结息！");
//			aLCInsureAccClassSchema.setBalaDate("1900-1-1");
//			aLCInsureAccClassSchema.setBalaTime("00:00:00");
//			aLCInsureAccClassSchema.setInsuAccBala("0");
//		}
//
//		String accSql = "select count(*) from lmriskapp where riskcode ='"
//				+ aLCInsureAccClassSchema.getRiskCode()
//				+ "' and riskflagacc ='D' "; // riskflagacc ='D'
//												// 代表139，151，121，122，108险种，D类险
//		String accCount = tExeSql.getOneValue(accSql);
//
//		if (!accCount.equals("0")) {
//
//			logger.debug("=====对" + aLCInsureAccClassSchema.getRiskCode()
//					+ "账户重新结息！=====");
//			aLCInsureAccClassSchema.setBalaDate("1900-1-1");
//			aLCInsureAccClassSchema.setBalaTime("00:00:00");
//			aLCInsureAccClassSchema.setInsuAccBala("0");
//		}
//		// 记录返回值利息和本息
//		TransferData aAccClassRet = new TransferData();
//
//		// 检验数据有效性
//		if (!verifyNotNull("当前结息日期", aBalaDate)) {
//			returnNull(aAccClassRet);
//			return aAccClassRet;
//		}
//		if (!verifyNotNull("原始利率类型", aRateType)) {
//			returnNull(aAccClassRet);
//			return aAccClassRet;
//		}
//		if (!verifyNotNull("目标利率类型", aIntvType)) {
//			returnNull(aAccClassRet);
//			return aAccClassRet;
//		}
//
//		// 记录结息间隔
//		int tInterval = 0;
//
//		// 记录查询LCInsureAccClassTrace表返回有效记录的个数
//		int tCount = 0;
//
//		// 记录原始利率值
//		double tAccClassRate = 0.00;
//
//		// 记录目标利率值
//		double tCalAccClassRate = 0.00;
//
//		// 记录保险帐户现金余额
//		double tInsuAccClassBala = aLCInsureAccClassSchema.getInsuAccBala();
//
//		// 得到险种保险帐户描述表(lmRiskInsuAcc)中帐户利率
//		// LMRiskInsuAccDB tLMRiskInsuAccDB = new LMRiskInsuAccDB();
//
//		// 记录保险帐户号码
//		String aInsuAccNo = aLCInsureAccClassSchema.getInsuAccNo();
//		if (!verifyNotNull("保险帐户号码", aInsuAccNo)) {
//			returnNull(aAccClassRet);
//			return aAccClassRet;
//		}
//
//		// 记录帐户分类表上次结息日期
//		String tBalaDate = aLCInsureAccClassSchema.getBalaDate();
//
//		// 记录帐户分类表上次结息时间
//		String tBalaTime = aLCInsureAccClassSchema.getBalaTime();
//
//		// 上次结息日期为空或者不存在则取入机日期
//		if (tBalaDate == null || tBalaDate.equals("")) {
//			tBalaDate = aLCInsureAccClassSchema.getMakeDate();
//		}
//
//		// 上次结息时间如果为空或者不存在则取入机时间
//		if (tBalaTime == null || tBalaTime.equals("")) {
//			tBalaTime = aLCInsureAccClassSchema.getMakeTime();
//		}
//		LCInsureAccTraceDB tLCInsureAccTraceDB = new LCInsureAccTraceDB();
//		LCInsureAccTraceSet tLCInsureAccTraceSet = new LCInsureAccTraceSet();
//
//		// 得到帐户相关信息
//		LMRiskInsuAccSchema tLMRiskInsuAccSchema = new LMRiskInsuAccSchema();
//		tLMRiskInsuAccSchema = mCRI.findRiskInsuAccByInsuAccNo(aInsuAccNo);
//		if (tLMRiskInsuAccSchema == null) {
//			CError.buildErr(this, "查询险种保险帐户信息失败！");
//			returnNull(aAccClassRet);
//			return aAccClassRet;
//		}
//
//		// 该缴费帐户的缴退费总和
//		double tInsureAccTraceMoneySum = 0.0;
//
//		// 查询利率信息
//		String[] ResultRate = null;
//		if (tLMRiskInsuAccSchema.getInsuAccNo() == null
//				|| tLMRiskInsuAccSchema.getInsuAccNo().equals("")) {
//
//			CError.buildErr(this, "查询险种保险帐户信息失败！");
//			aAccClassRet
//					.setNameAndValue("tAccClassInterest", aAccClassInterest);
//			aAccClassRet.setNameAndValue("tAccClassSumPay", aAccClassSumPay);
//			return aAccClassRet;
//		}
//
//		String tSql;
//		LCInsureAccTraceDB tmpLCInsuAccTraceDB = null;
//		String tMoneyType = "";
//		String payDate = "";
//		String tMakeTime = "";
//		double tempMoney = 0.00;
//
//		// -----testing start----20060216-----------------------
//		logger.debug("************开始结息****************");
//		logger.debug("保险帐户现金余额: " + tInsuAccClassBala);
//		logger.debug("本次结息日期: " + aBalaDate);
//		logger.debug("上次结息日期: " + tBalaDate);
//		logger.debug("上次结息时间: " + tBalaTime);
//		// ----------testing end--------------------------------
//
//		switch (Integer.parseInt(tLMRiskInsuAccSchema.getAccComputeFlag())) {
//
//		// 不计息
//		case 0:
//
//			tSql = "select * from LCInsureAccTrace where PolNo = '"
//					+ aLCInsureAccClassSchema.getPolNo()
//					+ "' and InsuAccNo = '" + aInsuAccNo
//					+ "' and PayPlanCode = '"
//					+ aLCInsureAccClassSchema.getPayPlanCode()
//					+ "' and AccAscription = '"
//					+ aLCInsureAccClassSchema.getAccAscription()
//					+ "' and PayDate <= '" + aBalaDate + "'";
//
//			logger.debug("tSql: " + tSql);
//			try {
//				tLCInsureAccTraceSet = tLCInsureAccTraceDB.executeQuery(tSql);
//			} catch (Exception ex) {
//				CError.buildErr(this, ex.toString());
//				returnNull(aAccClassRet);
//				return aAccClassRet;
//			}
//			for (int i = 1; i <= tLCInsureAccTraceSet.size(); i++) {
//
//				tmpLCInsuAccTraceDB = new LCInsureAccTraceDB();
//				tmpLCInsuAccTraceDB.setSchema(tLCInsureAccTraceSet.get(i));
//
//				// 如果LCInsureAccTrace表中的moneyType字段的值为PX或者SX,则不作结息;
//				tMoneyType = tmpLCInsuAccTraceDB.getMoneyType();
//				if (tMoneyType.equals("PX") || tMoneyType.equals("SX")) {
//					continue;
//				}
//				payDate = tmpLCInsuAccTraceDB.getPayDate();
//				if (payDate == null || payDate.equals("")) {
//					payDate = tmpLCInsuAccTraceDB.getMakeDate();
//					if (payDate == null || payDate.equals("")) {
//						CError.buildErr(this, "缴费记录的缴费日期为空！");
//						returnNull(aAccClassRet);
//						return aAccClassRet;
//					}
//				}
//
//				// 交费生效时间
//				tMakeTime = tmpLCInsuAccTraceDB.getMakeTime();
//
//				// ==20060619===testing start===============
//				logger.debug("第" + i + "条记录！");
//				logger.debug("缴费日期：" + payDate);
//				logger.debug("缴费时间：" + tMakeTime);
//				logger.debug("缴费金额：" + tmpLCInsuAccTraceDB.getMoney());
//				// ==20060619==testind end===================
//
//				// 临时存放帐户一笔缴费金额
//				tempMoney = tmpLCInsuAccTraceDB.getMoney();
//				tInsureAccTraceMoneySum += tempMoney;
//
//			}
//
//			break;
//		// 定期利率单利算法
//		case 1:
//
//			// 获取帐户固定利率AccRate
//			tAccClassRate = tLMRiskInsuAccSchema.getAccRate();
//
//			// 将原始固定利率转换为目标固定利率
//			tCalAccClassRate = TransAccRate(tAccClassRate, aRateType, "S",
//					aIntvType);
//
//			// 筛选出paydate > 帐户分类表的结息日期BalaDate
//			// 帐户型险种，对于分红险除公共帐户之外，trace表的payno字段放置class表
//			// 的otherno字段的值, 每笔缴费在class表中都有一条记录，此称为先进先出型
//			// 险种，用LMRiskInsuAcc表中的AccKind来区分；
//			String tAccKind = tLMRiskInsuAccSchema.getAccKind();
//
//			// 先进先出险种特殊处理
//			String tOtherNo = aLCInsureAccClassSchema.getOtherNo();
//			if (!tAccKind.equals("") && tAccKind.equals("3")) {
//				if (tOtherNo.equals("")) {
//					CError.buildErr(this, "保险帐户分类表数据不完整！");
//					returnNull(aAccClassRet);
//					return aAccClassRet;
//				}
//				tLCInsureAccTraceDB.setPayNo(tOtherNo);
//			}
//			tLCInsureAccTraceDB.setPolNo(aLCInsureAccClassSchema.getPolNo());
//			tLCInsureAccTraceDB.setInsuAccNo(aInsuAccNo);
//			tLCInsureAccTraceDB.setPayPlanCode(aLCInsureAccClassSchema
//					.getPayPlanCode());
//			tLCInsureAccTraceDB.setAccAscription(aLCInsureAccClassSchema
//					.getAccAscription());
//			try {
//				tLCInsureAccTraceSet = tLCInsureAccTraceDB.query();
//			} catch (Exception ex) {
//				CError.buildErr(this, ex.toString());
//				returnNull(aAccClassRet);
//				return aAccClassRet;
//			}
//
//			for (int i = 1; i <= tLCInsureAccTraceSet.size(); i++) {
//				tmpLCInsuAccTraceDB = new LCInsureAccTraceDB();
//				tmpLCInsuAccTraceDB = (LCInsureAccTraceDB) tLCInsureAccTraceSet
//						.get(i);
//
//				// 如果LCInsureAccTrace表中的moneyType字段的值为PX或者SX,则不作结息;
//				tMoneyType = tmpLCInsuAccTraceDB.getMoneyType();
//				if (tMoneyType.equals("PX") || tMoneyType.equals("SX")) {
//					continue;
//				}
//
//				// 记录缴费日期
//				payDate = tmpLCInsuAccTraceDB.getPayDate();
//
//				// 交费生效时间
//				tMakeTime = tmpLCInsuAccTraceDB.getMakeTime();
//
//				// 找出上次结息后的缴退费金额
//				int tIntv = PubFun.calInterval(tBalaDate, payDate, aIntvType);
//				if (tIntv >= 0) {
//
//					// 日期相同则比较时间
//					if (tIntv == 0 && tBalaTime.compareTo(tMakeTime) >= 0) {
//						continue;
//					}
//
//					// 暂存一笔缴费金额
//					tempMoney = tmpLCInsuAccTraceDB.getMoney();
//					tInsureAccTraceMoneySum += tempMoney;
//
//					// 然后对轨迹表的这笔钱tempMoney作结息
//					if (payDate.compareTo(aBalaDate) > 0) {
//						tInterval = PubFun.calInterval(aBalaDate, payDate,
//								aIntvType);
//						tInterval = 0 - tInterval;
//					} else {
//						tInterval = PubFun.calInterval(payDate, aBalaDate,
//								aIntvType);
//					}
//					/*
//					 * if (tInterval > 0) { aAccClassInterest += tempMoney *
//					 * getIntvRate(tInterval, tCalAccClassRate, "S"); } else {
//					 * tInterval = 0; aAccClassInterest += tempMoney *
//					 * getIntvRate(tInterval, tCalAccClassRate, "S"); }
//					 */
//					// jixf chg 20060722不区分时间间隔，可以往回反结息
//					aAccClassInterest += tempMoney
//							* getIntvRate(tInterval, tCalAccClassRate, "S");
//					aAccClassInterest = Arith.round(aAccClassInterest, 2);
//				}
//			}
//
//			// 将帐户分类表的另一部分余额结息
//			tInterval = PubFun.calInterval(tBalaDate, aBalaDate, aIntvType);
//			if (tInterval > 0) {
//				aAccClassInterest += tInsuAccClassBala
//						* getIntvRate(tInterval, tCalAccClassRate, "S");
//			}
//			aAccClassInterest = Arith.round(aAccClassInterest, 2);
//			break;
//
//		// 定期利率复利算法
//		case 2:
//
//			// 获取固定利率
//			tAccClassRate = tLMRiskInsuAccSchema.getAccRate();
//
//			// 转换后的利率值
//			tCalAccClassRate = TransAccRate(tAccClassRate, aRateType, "C",
//					aIntvType);
//
//			// 这个单子特殊处理，并且不分红，其他项目组可以不用
//			// jixf add 20061016
//			if (aLCInsureAccClassSchema.getGrpContNo().equals("230501012000")) {
//				tAccClassRate = 0.03;
//				tCalAccClassRate = TransAccRate(tAccClassRate, aRateType, "S",
//						aIntvType);
//			}
//
//			// 筛选出paydate > 帐户分类表的结息日期BalaDate
//			// 帐户型险种，对于分红险，trace表的payno字段放置class表的otherno字段的值；
//			// tSql = "select BonusFlag from LMRiskApp a where exists " +
//			// "(select * from LCPol where PolNo = '" +
//			// aLCInsureAccClassSchema.getPolNo() +
//			// "' and RiskCode = a.RiskCode)";
//			// tSql = "select AccKind from LMRiskInsuAcc where " +
//			// "InsuAccNo = '" + aInsuAccNo + "'";
//			// try{
//			// tSSRS = tExeSql.execSQL(tSql);
//			// } catch (Exception ex){
//			// CError.buildErr(this, ex.toString());
//			// returnNull(aAccClassRet);
//			// return aAccClassRet;
//			// }
//			// tAccKind = tSSRS.GetText(1, 1);
//			// ExeSQL ttExeSql = new ExeSQL();
//			// SSRS ttSSRS = new SSRS();
//			tAccKind = tLMRiskInsuAccSchema.getAccKind();
//
//			// 分红险特殊处理
//			tOtherNo = aLCInsureAccClassSchema.getOtherNo();
//			if (!tAccKind.equals("") && tAccKind.equals("3")) {
//				if (tOtherNo.equals("")) {
//					CError.buildErr(this, "保险帐户分类表数据不完整！");
//					returnNull(aAccClassRet);
//					return aAccClassRet;
//				}
//				// tLCInsureAccTraceDB.setPayNo(tOtherNo);
//			}
//			tSql = "select * from LCInsureAccTrace where polno='"
//					+ aLCInsureAccClassSchema.getPolNo() + "' and InsuAccNo='"
//					+ aInsuAccNo + "' and PayPlanCode='"
//					+ aLCInsureAccClassSchema.getPayPlanCode()
//					+ "' and PayNo='" + tOtherNo + "' and (PayDate>'"
//					+ tBalaDate + "' or (PayDate='" + tBalaDate
//					+ "' and MakeTime>'" + tBalaTime + "')) and PayDate<='"
//					+ aBalaDate + "'";
//			logger.debug("------" + tSql);
//
//			/*
//			 * tLCInsureAccTraceDB.setPolNo(aLCInsureAccClassSchema.getPolNo());
//			 * tLCInsureAccTraceDB.setInsuAccNo(aInsuAccNo);
//			 * tLCInsureAccTraceDB.setPayPlanCode(aLCInsureAccClassSchema.
//			 * getPayPlanCode());
//			 * //tLCInsureAccTraceDB.setOtherNo(aLCInsureAccClassSchema.getOtherNo());
//			 * tLCInsureAccTraceDB.setAccAscription(aLCInsureAccClassSchema.
//			 * getAccAscription()); tLCInsureAccTraceSet =
//			 * tLCInsureAccTraceDB.executeQuery(tSql);
//			 */
//
//			tLCInsureAccTraceSet = tLCInsureAccTraceDB.executeQuery(tSql);
//
//			for (int i = 1; i <= tLCInsureAccTraceSet.size(); i++) {
//
//				tmpLCInsuAccTraceDB = new LCInsureAccTraceDB();
//				tmpLCInsuAccTraceDB.setSchema(tLCInsureAccTraceSet.get(i));
//
//				// 如果LCInsureAccTrace表中的moneyType字段的值为PX或者SX,则不作结息;
//				tMoneyType = tmpLCInsuAccTraceDB.getMoneyType();
//				if (tMoneyType.equals("PX") || tMoneyType.equals("SX")) {
//					continue;
//				}
//
//				// 交费日期
//				payDate = tmpLCInsuAccTraceDB.getPayDate();
//
//				// 交费生效时间
//				tMakeTime = tmpLCInsuAccTraceDB.getMakeTime();
//
//				// 现在比较lcinsureaccclass表的baladate和lcinsuretrace表的paydate的值的大小
//				/*
//				 * //这个限制在前面的查询里面一致限定了 int tIntv = PubFun.calInterval(tBalaDate,
//				 * payDate, aIntvType); if (tIntv >= 0) {
//				 * 
//				 * //处理轨迹表中paydate>=帐户分类表的结息日期BalaDate if (tIntv == 0 &&
//				 * tBalaTime.compareTo(tMakeTime) >= 0) { continue; }
//				 */
//				tempMoney = tmpLCInsuAccTraceDB.getMoney();
//				tInsureAccTraceMoneySum += tempMoney;
//
//				// 然后对轨迹表的这笔钱tempMoney作结息
//				// jixf add 20050802 对于PayDate>aBalaDate的情况，tInterval有问题，反之没有问题
//				logger.debug("------" + payDate);
//				logger.debug("------" + aBalaDate);
//				logger.debug("-%%%%%%%%%--===="
//						+ payDate.compareTo(aBalaDate));
//				if (payDate.compareTo(aBalaDate) > 0) {
//					logger.debug("33333");
//					tInterval = PubFun.calInterval(aBalaDate, payDate,
//							aIntvType);
//					tInterval = 0 - tInterval;
//				} else {
//					tInterval = PubFun.calInterval(payDate, aBalaDate,
//							aIntvType);
//				}
//				/*
//				 * if (tInterval > 0) { aAccClassInterest += tempMoney *
//				 * getIntvRate(tInterval, tCalAccClassRate, "C"); } else {
//				 * 
//				 * //tInterval = 0; //可以往回计算 aAccClassInterest += tempMoney *
//				 * getIntvRate(tInterval, tCalAccClassRate, "C"); }
//				 */
//				// jixf chg 20060722不区分时间间隔，可以往回反结息
//				aAccClassInterest += tempMoney
//						* getIntvRate(tInterval, tCalAccClassRate, "C");
//				logger.debug("=======" + tInterval);
//				logger.debug("=======" + tInsuAccClassBala);
//				logger.debug("======="
//						+ getIntvRate(tInterval, tCalAccClassRate, "C"));
//				aAccClassInterest = Arith.round(aAccClassInterest, 2);
//
//				// }
//			}
//
//			// 将帐户分类表的另一部分余额结息
//			tInterval = PubFun.calInterval(tBalaDate, aBalaDate, aIntvType);
//			if (tInterval > 0) {
//				aAccClassInterest += tInsuAccClassBala
//						* getIntvRate(tInterval, tCalAccClassRate, "C");
//			}
//			logger.debug("=======" + tInterval);
//			logger.debug("=======" + tInsuAccClassBala);
//			logger.debug("======="
//					+ getIntvRate(tInterval, tCalAccClassRate, "C"));
//			aAccClassInterest = Arith.round(aAccClassInterest, 2);
//			break;
//
//		// 活期利率单利算法
//		case 3:
//
//			// 筛选出paydate>帐户分类表的结息日期BalaDate
//			// 帐户型险种，对于分红险，trace表的payno字段放置class表的otherno字段的值；
//			// tSql = "select AccKind from LMRiskInsuAcc where " +
//			// "InsuAccNo = '" + aInsuAccNo + "'";
//			// try{
//			// tSSRS = tExeSql.execSQL(tSql);
//			// } catch (Exception ex){
//			// CError.buildErr(this, ex.toString());
//			// returnNull(aAccClassRet);
//			// return aAccClassRet;
//			// }
//			// tAccKind = tSSRS.GetText(1, 1);
//
//			tAccKind = tLMRiskInsuAccSchema.getAccKind();
//
//			// 分红险特殊处理
//			tOtherNo = aLCInsureAccClassSchema.getOtherNo();
//			if (!tAccKind.equals("") && tAccKind.equals("3")) {
//				if (tOtherNo.equals("")) {
//					CError.buildErr(this, "保险帐户分类表数据不完整！");
//					returnNull(aAccClassRet);
//					return aAccClassRet;
//				}
//				// tLCInsureAccTraceDB.setPayNo(tOtherNo);
//			}
//			tSql = "select * from LCInsureAccTrace where polno='"
//					+ aLCInsureAccClassSchema.getPolNo() + "' and InsuAccNo='"
//					+ aInsuAccNo + "' and PayPlanCode='"
//					+ aLCInsureAccClassSchema.getPayPlanCode()
//					+ "' and AccAscription='"
//					+ aLCInsureAccClassSchema.getAccAscription()
//					+ "' and (PayDate>'" + tBalaDate + "' or (PayDate='"
//					+ tBalaDate + "' and MakeTime>'" + tBalaTime
//					+ "')) and PayDate<='" + aBalaDate + "'";
//			logger.debug("------" + tSql);
//
//			tLCInsureAccTraceSet = tLCInsureAccTraceDB.executeQuery(tSql);
//
//			// tLCInsureAccTraceDB.setPolNo(aLCInsureAccClassSchema.getPolNo());
//			// tLCInsureAccTraceDB.setInsuAccNo(aInsuAccNo);
//			// tLCInsureAccTraceDB.setPayPlanCode(aLCInsureAccClassSchema.
//			// getPayPlanCode());
//			// tLCInsureAccTraceDB.setAccAscription(aLCInsureAccClassSchema.
//			// getAccAscription());
//			// tLCInsureAccTraceSet = tLCInsureAccTraceDB.query();
//			for (int i = 1; i <= tLCInsureAccTraceSet.size(); i++) {
//				tmpLCInsuAccTraceDB = new LCInsureAccTraceDB();
//				tmpLCInsuAccTraceDB.setSchema(tLCInsureAccTraceSet.get(i));
//
//				// 如果LCInsureAccTrace表中的moneyType字段的值为PX或者SX,则不作结息;
//				tMoneyType = tmpLCInsuAccTraceDB.getMoneyType();
//				if (tMoneyType.equals("PX") || tMoneyType.equals("SX")) {
//					continue;
//				}
//
//				// 记录交费日期
//				payDate = tmpLCInsuAccTraceDB.getPayDate();
//
//				// 记录交费生效时间
//				tMakeTime = tmpLCInsuAccTraceDB.getMakeTime();
//
//				// 现在比较lcinsureaccclass表的baladate和lcinsuretrace表的paydate的值的大小
//				int tIntv = PubFun.calInterval(tBalaDate, payDate, aIntvType);
//				if (tIntv >= 0) {
//
//					// 处理轨迹表中paydate>=帐户分类表的结息日期BalaDate
//					if (tIntv == 0 && tBalaTime.compareTo(tMakeTime) >= 0) {
//						continue;
//					}
//
//					// 临时存放LCInsuAccTraceDB表中的帐户缴费
//					tempMoney = tmpLCInsuAccTraceDB.getMoney();
//					tInsureAccTraceMoneySum += tempMoney;
//
//					// 对轨迹表的这笔钱tempMoney作结息
////					String[] ResultRate2 = getMultiAccRateNew(aInsuAccNo,
////							tLMRiskInsuAccSchema, payDate, aBalaDate,
////							aRateType, "S", aIntvType, Period, tType, Depst);
////
////					// 记录ResultRate2[]的有效数组长度
////					tCount = Integer.parseInt(ResultRate2[0]);
////					for (int m = 1; m < tCount + 1; m++) {
////						if (ResultRate2[m] == null) {
////							ResultRate2[m] = "0";
////						}
////						tCalAccClassRate = Double.parseDouble(ResultRate2[m]);
////						double tSubInterest = tempMoney * tCalAccClassRate;
////						tSubInterest = Arith.round(tSubInterest, 2);
////						aAccClassInterest += tSubInterest;
////					}
//					//modify by jiaqiangli 2008-11-11 修改成调用MS算法 单利计息
//					//贷存款标志 (D: 存款 L: 贷款)
//					if (Depst != null && Depst.equals("L")) {
//						tCalAccClassRate = calMultiRateMS(payDate, aBalaDate,"000000","00","L","S","Y");
//					}
//					else if (Depst != null && Depst.equals("D")) {
//						tCalAccClassRate = calMultiRateMS(payDate, aBalaDate,"000000","00","R","S","Y");
//					}
//					//注意清0处理
//					else {
//						tCalAccClassRate = 0.00;
//					}
//					double tSubInterest = PubFun.round(tempMoney * tCalAccClassRate,2);
//					aAccClassInterest += tSubInterest;
//					//modify by jiaqiangli 2008-11-11 修改成调用MS算法 单利计息
//				}
//			}
//
//			// 将帐户分类表的另一部分余额结息
////			ResultRate = getMultiAccRateNew(aInsuAccNo, tLMRiskInsuAccSchema,
////					tBalaDate, aBalaDate, aRateType, "S", aIntvType, Period,
////					tType, Depst);
////			tCount = Integer.parseInt(ResultRate[0]);
////			for (int m = 1; m < tCount + 1; m++) {
////				if (ResultRate[m] == null) {
////					ResultRate[m] = "0";
////				}
////				tCalAccClassRate = Double.parseDouble(ResultRate[m]);
////				aAccClassInterest += tInsuAccClassBala * tCalAccClassRate;
////				aAccClassInterest = Arith.round(aAccClassInterest, 2);
////			}
//			//modify by jiaqiangli 2008-11-11 修改成调用MS算法 单利计息
//			//贷存款标志 (D: 存款 L: 贷款)
//			if (Depst != null && Depst.equals("L")) {
//				tCalAccClassRate = calMultiRateMS(tBalaDate, aBalaDate,"000000","00","L","S","Y");
//			}
//			else if (Depst != null && Depst.equals("D")) {
//				tCalAccClassRate = calMultiRateMS(tBalaDate, aBalaDate,"000000","00","R","S","Y");
//			}
//			//注意清0处理
//			else {
//				tCalAccClassRate = 0.00;
//			}
//			aAccClassInterest += tInsuAccClassBala * tCalAccClassRate;
//			aAccClassInterest = PubFun.round(aAccClassInterest, 2);
//			//modify by jiaqiangli 2008-11-11 修改成调用MS算法 单利计息
//			break;
//
//		// 活期利率复利算法
//		case 4:
//			logger.debug("=====复利结息开始=====\n");
//			tSql = "select * from LCInsureAccTrace where PolNo = '"
//					+ aLCInsureAccClassSchema.getPolNo()
//					+ "' and InsuAccNo = '" + aInsuAccNo
//					+ "' and PayPlanCode = '"
//					+ aLCInsureAccClassSchema.getPayPlanCode()
//					+ "' and AccAscription = '"
//					+ aLCInsureAccClassSchema.getAccAscription()
//					+ "' and PayDate <= '" + aBalaDate + "'";
//
//			// 帐户型险种，对于分红险，trace表的payno字段放置class表的otherno字段的值；
//			// tSql = "select BonusFlag from LMRiskApp a where exists " +
//			// "(select * from LCPol where PolNo = '" +
//			// aLCInsureAccClassSchema.getPolNo() +
//			// "' and RiskCode = a.RiskCode)";
//			// tSql = "select AccKind from LMRiskInsuAcc where " +
//			// "InsuAccNo = '" + aInsuAccNo + "'";
//			// try{
//			// tSSRS = tExeSql.execSQL(tSql);
//			// } catch (Exception ex){
//			// CError.buildErr(this, ex.toString());
//			// returnNull(aAccClassRet);
//			// return aAccClassRet;
//			// }
//			// tAccKind = tSSRS.GetText(1, 1);
//			tAccKind = tLMRiskInsuAccSchema.getAccKind();
//
//			// 分红险特殊处理
//			tOtherNo = aLCInsureAccClassSchema.getOtherNo();
//			if (!tAccKind.equals("") && tAccKind.equals("3")) {
//				if (tOtherNo.equals("")) {
//					CError.buildErr(this, "保险帐户分类表数据不完整！");
//					returnNull(aAccClassRet);
//					return aAccClassRet;
//				}
//				tSql = "select * from LCInsureAccTrace where PolNo = '"
//						+ aLCInsureAccClassSchema.getPolNo()
//						+ "' and InsuAccNo = '" + aInsuAccNo
//						+ "' and PayPlanCode = '"
//						+ aLCInsureAccClassSchema.getPayPlanCode()
//						+ "' and PayNo = '"
//						+ aLCInsureAccClassSchema.getOtherNo()
//						+ "' and AccAscription = '"
//						+ aLCInsureAccClassSchema.getAccAscription()
//						+ "' and PayDate <= '" + aBalaDate + "'";
//			}
//			logger.debug("tSql: " + tSql);
//			try {
//				tLCInsureAccTraceSet = tLCInsureAccTraceDB.executeQuery(tSql);
//			} catch (Exception ex) {
//				CError.buildErr(this, ex.toString());
//				returnNull(aAccClassRet);
//				return aAccClassRet;
//			}
//			for (int i = 1; i <= tLCInsureAccTraceSet.size(); i++) {
//
//				tmpLCInsuAccTraceDB = new LCInsureAccTraceDB();
//				tmpLCInsuAccTraceDB.setSchema(tLCInsureAccTraceSet.get(i));
//
//				// 如果LCInsureAccTrace表中的moneyType字段的值为PX或者SX,则不作结息;
//				tMoneyType = tmpLCInsuAccTraceDB.getMoneyType();
//				if (tMoneyType.equals("PX") || tMoneyType.equals("SX")) {
//					continue;
//				}
//				payDate = tmpLCInsuAccTraceDB.getPayDate();
//				if (payDate == null || payDate.equals("")) {
//					payDate = tmpLCInsuAccTraceDB.getMakeDate();
//					if (payDate == null || payDate.equals("")) {
//						CError.buildErr(this, "缴费记录的缴费日期为空！");
//						returnNull(aAccClassRet);
//						return aAccClassRet;
//					}
//				}
//
//				// 交费生效时间
//				tMakeTime = tmpLCInsuAccTraceDB.getMakeTime();
//
//				// ==20060619===testing start===============
//				logger.debug("第" + i + "条记录！");
//				logger.debug("缴费日期：" + payDate);
//				logger.debug("缴费时间：" + tMakeTime);
//				logger.debug("缴费金额：" + tmpLCInsuAccTraceDB.getMoney());
//				// ==20060619==testind end===================
//
//				// 现在比较lcinsureaccclass表的baladate和lcinsuretrace表的paydate的值的大小
//				int tIntv = PubFun.calInterval(tBalaDate, payDate, aIntvType);
//				if (tIntv >= 0) {
//
//					// 处理轨迹表中paydate>=帐户分类表的结息日期BalaDate
//					if (tIntv == 0 && tBalaTime.compareTo(tMakeTime) >= 0) {
//						continue;
//					}
//
//					// 临时存放帐户一笔缴费金额
//					tempMoney = tmpLCInsuAccTraceDB.getMoney();
//					tInsureAccTraceMoneySum += tempMoney;
//
//					// 然后对轨迹表的这笔钱tempMoney作结息
////					String[] ResultRate2 = getMultiAccRateNew(aInsuAccNo,
////							tLMRiskInsuAccSchema, payDate, aBalaDate,
////							aRateType, "C", aIntvType, Period, tType, Depst);
////					tCount = Integer.parseInt(ResultRate2[0]);
////
////					// ====20060619==testing start=============
////					logger.debug("查到" + tCount + "条利率值");
////					// ===20060619==testing end================
////
////					for (int m = 1; m < tCount + 1; m++) {
////						if (ResultRate2[m] == null) {
////							ResultRate2[m] = "0";
////						}
////						tCalAccClassRate = Double.parseDouble(ResultRate2[m]);
////
////						// ===20060619==testing start===============
////						logger.debug("Rate = " + tCalAccClassRate);
////						// ===2060619===testing end=================
////
////						double tSubInterest = tempMoney * tCalAccClassRate;
////						tSubInterest = Arith.round(tSubInterest, 2);
////
////						// ===20061018===testing======================
////						logger.debug("利息： " + tSubInterest);
////						// ===20061018===testing end==================
////
////						aAccClassInterest += tSubInterest;
////					}
//					//modify by jiaqiangli 2008-11-11 修改成调用MS算法 复利计息
//					//贷存款标志 (D: 存款 L: 贷款)
//					if (Depst != null && Depst.equals("L")) {
//						tCalAccClassRate = calMultiRateMS(payDate, aBalaDate,"000000","00","L","C","Y");
//					}
//					else if (Depst != null && Depst.equals("D")) {
//						tCalAccClassRate = calMultiRateMS(payDate, aBalaDate,"000000","00","R","C","Y");
//					}
//					//注意清0处理
//					else {
//						tCalAccClassRate = 0.00;
//					}
//					double tSubInterest = PubFun.round(tempMoney * tCalAccClassRate,2);
//					aAccClassInterest += tSubInterest;
//					//modify by jiaqiangli 2008-11-11 修改成调用MS算法 复利计息
//				}
//			}
//
//			// 将帐户分类表的另一部分余额结息
//			logger.debug("=====将帐户分类表的另一部分余额结息=====\n");
////			ResultRate = getMultiAccRateNew(aInsuAccNo, tLMRiskInsuAccSchema,
////					tBalaDate, aBalaDate, aRateType, "C", aIntvType, Period,
////					tType, Depst);
////			tCount = Integer.parseInt(ResultRate[0]);
////			for (int m = 1; m < tCount + 1; m++) {
////				if (ResultRate[m] == null) {
////					ResultRate[m] = "0";
////				}
////				tCalAccClassRate = Double.parseDouble(ResultRate[m]);
////				aAccClassInterest += tInsuAccClassBala * tCalAccClassRate;
////				aAccClassInterest = Arith.round(aAccClassInterest, 2);
////				// ===20060703===testing start========================
////				logger.debug("计算后的利率：" + tCalAccClassRate);
////				// ===20060703==testing end===========================
////
////			}
//			//modify by jiaqiangli 2008-11-11 修改成调用MS算法 复利计息
//			//贷存款标志 (D: 存款 L: 贷款)
//			if (Depst != null && Depst.equals("L")) {
//				tCalAccClassRate = calMultiRateMS(tBalaDate, aBalaDate,"000000","00","L","C","Y");
//			}
//			else if (Depst != null && Depst.equals("D")) {
//				tCalAccClassRate = calMultiRateMS(tBalaDate, aBalaDate,"000000","00","R","C","Y");
//			}
//			//注意清0处理
//			else {
//				tCalAccClassRate = 0.00;
//			}
//			aAccClassInterest += tInsuAccClassBala * tCalAccClassRate;
//			aAccClassInterest = PubFun.round(aAccClassInterest, 2);
//			//modify by jiaqiangli 2008-11-11 修改成调用MS算法 复利计息
//			break;
//		}
//		if (Integer.parseInt(tLMRiskInsuAccSchema.getAccComputeFlag()) != 0) {
//			aAccClassSumPay = tInsuAccClassBala + tInsureAccTraceMoneySum
//					+ aAccClassInterest;
//		} else {
//			aAccClassSumPay = tInsureAccTraceMoneySum;
//		}
//		// ===20061018===testing start==================
//		logger.debug("帐户金额： " + tInsuAccClassBala);
//		logger.debug("Trace表的缴费和： " + tInsureAccTraceMoneySum);
//		logger.debug("Trace表的缴费利息和： " + aAccClassInterest);
//		logger.debug("帐户实际余额： " + aAccClassSumPay);
//		// ===20061018===testing end====================
//
//		// 准备返回的数据包
//		aAccClassRet.setNameAndValue("aAccClassInterest", aAccClassInterest);
//		aAccClassRet.setNameAndValue("aAccClassSumPay", aAccClassSumPay);
//
//		return aAccClassRet;
//	}

	/**
	 * 结息不成功，返回零 被getAccClassInterestNew在传入参数无效时调用
	 */
	private void returnNull(TransferData aAccClassRet) {
		aAccClassRet.setNameAndValue("tAccClassInterest", 0.0);
		aAccClassRet.setNameAndValue("tAccClassSumPay", 0.0);
	}

	/**
	 * 不能为空校验
	 */
	private boolean verifyNotNull(String tVName, String tStrValue) {
		if (tStrValue == null || tStrValue.equals("")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "AccountManage";
			tError.functionName = "getAccClassInterestNew";
			tError.errorMessage = "进行结息计算时，" + tVName + "没有传入！";
			this.mErrors.addOneError(tError);
		}

		return true;
	}

	/**
	 * 按照新的帐户结息的逻辑进行处理 计算帐户分类表的利息
	 * 
	 * @param aBalaDate
	 *            本次结息日期
	 * @param aRateType
	 *            原始利率类型，比如年利率，月利率等
	 * @param aIntvType
	 *            目标利率类型，比如年利率，月利率等
	 * @param Period
	 *            利率期间
	 * @param tType
	 *            利率类型（C:活期 F:定期）
	 * @param Depst
	 *            贷存款标志 (D: 存款 L: 贷款) 创建人: 创建日期：2006-02-16
	 * @return double 返回的是帐户分类表和相应的记价履历表的利息和以及总余额 修改人： 修改日期：2006-02-24
	 *         修改内容：传输参数修改
	 */
	public TransferData getAccMedBuyBala(
			LCInsureAccClassSchema aLCInsureAccClassSchema, String aBalaDate,
			String aRateType, String aIntvType, int Period, String tType,
			String Depst) {

		logger.debug("=====This is getAccClassInterestNew!=====\n");

		// 记录帐户分类表的利息值
		double aAccClassInterest = 0.0;
		// 记录帐户分类表的交费的利息值(money>0)
		double aAccInterest = 0.0;

		// 记录帐户分类表的本息和
		double aAccClassSumPay = 0.0;

		ExeSQL tExeSql = new ExeSQL();
		SSRS tSSRS = new SSRS();

		String accSql = "select count(*) from lmriskapp where riskcode ='"
				+ "?m1?"
				+ "'"; // riskflagacc ='D'
												// 代表139，151，121，122，108险种，D类险
		SQLwithBindVariables sqlbv21=new SQLwithBindVariables();
	    sqlbv21.sql(accSql);
	    sqlbv21.put("m1",aLCInsureAccClassSchema.getRiskCode());
		String accCount = tExeSql.getOneValue(sqlbv21);

		if (!accCount.equals("0")) {

			logger.debug("=====对" + aLCInsureAccClassSchema.getRiskCode()
					+ "账户重新结息！=====");
			aLCInsureAccClassSchema.setBalaDate("1900-1-1");
			aLCInsureAccClassSchema.setBalaTime("00:00:00");
			aLCInsureAccClassSchema.setInsuAccBala("0");
		}

		// 记录返回值利息和本息
		TransferData aAccClassRet = new TransferData();

		// 检验数据有效性
		if (!verifyNotNull("当前结息日期", aBalaDate)) {
			returnNull(aAccClassRet);
			return aAccClassRet;
		}
		if (!verifyNotNull("原始利率类型", aRateType)) {
			returnNull(aAccClassRet);
			return aAccClassRet;
		}
		if (!verifyNotNull("目标利率类型", aIntvType)) {
			returnNull(aAccClassRet);
			return aAccClassRet;
		}

		// 记录结息间隔
		int tInterval = 0;

		// 记录查询LCInsureAccClassTrace表返回有效记录的个数
		int tCount = 0;

		// 记录原始利率值
		double tAccClassRate = 0.00;

		// 记录目标利率值
		double tCalAccClassRate = 0.00;

		// 记录保险帐户现金余额
		double tInsuAccClassBala = aLCInsureAccClassSchema.getInsuAccBala();

		// 得到险种保险帐户描述表(lmRiskInsuAcc)中帐户利率
		// LMRiskInsuAccDB tLMRiskInsuAccDB = new LMRiskInsuAccDB();

		// 记录保险帐户号码
		String aInsuAccNo = aLCInsureAccClassSchema.getInsuAccNo();
		if (!verifyNotNull("保险帐户号码", aInsuAccNo)) {
			returnNull(aAccClassRet);
			return aAccClassRet;
		}

		// 记录帐户分类表上次结息日期
		String tBalaDate = aLCInsureAccClassSchema.getBalaDate();

		// 记录帐户分类表上次结息时间
		String tBalaTime = aLCInsureAccClassSchema.getBalaTime();

		// 上次结息日期为空或者不存在则取入机日期
		if (tBalaDate == null || tBalaDate.equals("")) {
			tBalaDate = aLCInsureAccClassSchema.getMakeDate();
		}

		// 上次结息时间如果为空或者不存在则取入机时间
		if (tBalaTime == null || tBalaTime.equals("")) {
			tBalaTime = aLCInsureAccClassSchema.getMakeTime();
		}
		LCInsureAccTraceDB tLCInsureAccTraceDB = new LCInsureAccTraceDB();
		LCInsureAccTraceSet tLCInsureAccTraceSet = new LCInsureAccTraceSet();
		LCMedBuyMainDB tLCMedBuyMainDB = new LCMedBuyMainDB();
		LCMedBuyMainSet tLCMedBuyMainSet = new LCMedBuyMainSet();

		// 得到帐户相关信息
		LMRiskInsuAccSchema tLMRiskInsuAccSchema = new LMRiskInsuAccSchema();
		tLMRiskInsuAccSchema = mCRI.findRiskInsuAccByInsuAccNo(aInsuAccNo);
		if (tLMRiskInsuAccSchema == null) {
			CError.buildErr(this, "查询险种保险帐户信息失败！");
			returnNull(aAccClassRet);
			return aAccClassRet;
		}

		// 该缴费帐户的缴退费总和
		double tInsureAccTraceMoneySum = 0.0;

		// 查询利率信息
		String[] ResultRate = null;
		if (tLMRiskInsuAccSchema.getInsuAccNo() == null
				|| tLMRiskInsuAccSchema.getInsuAccNo().equals("")) {

			CError.buildErr(this, "查询险种保险帐户信息失败！");
			aAccClassRet
					.setNameAndValue("tAccClassInterest", aAccClassInterest);
			aAccClassRet.setNameAndValue("tAccClassSumPay", aAccClassSumPay);
			return aAccClassRet;
		}

		String tSql = null;
		String tSql2 = null;
		LCInsureAccTraceDB tmpLCInsuAccTraceDB = null;
		LCMedBuyMainDB tmpLCMedBuyMainDB = null;
		String tMoneyType = "";
		String payDate = "";
		String tMakeTime = "";
		double tempMoney = 0.00;

		// -----testing start----20060216-----------------------
		logger.debug("************开始结息****************");
		logger.debug("保险帐户现金余额: " + tInsuAccClassBala);
		logger.debug("本次结息日期: " + aBalaDate);
		logger.debug("上次结息日期: " + tBalaDate);
		logger.debug("上次结息时间: " + tBalaTime);
		// ----------testing end--------------------------------

		tSql = "select * from lcmedbuymain where MedCardNo in (select MedCardNo from lcmedcarduserinfo where ContNo = '"
				+ "?m2?"
				+ "' ) and clmflag='0' and MakeDate <= '" + "?m3?" + "'";
		SQLwithBindVariables sqlbv22=new SQLwithBindVariables();
	    sqlbv22.sql(tSql);
	    sqlbv22.put("m2",aLCInsureAccClassSchema.getContNo());
	    sqlbv22.put("m3",aBalaDate);
		logger.debug("tSql: " + tSql);

		tSql2 = "select * from lcinsureacctrace where polno='"
				+ "?hh?" + "' ";
		SQLwithBindVariables sqlbv38=new SQLwithBindVariables();
	    sqlbv38.sql(tSql2);
	    sqlbv38.put("hh",aLCInsureAccClassSchema.getPolNo());
		logger.debug("tSql2: " + tSql2);

		switch (Integer.parseInt(tLMRiskInsuAccSchema.getAccComputeFlag())) {

		// 不计息,只求出trace和即可
		case 0:

			try {
				tLCMedBuyMainSet = tLCMedBuyMainDB.executeQuery(sqlbv22);
			} catch (Exception ex) {
				CError.buildErr(this, ex.toString());
				returnNull(aAccClassRet);
				return aAccClassRet;
			}
			for (int i = 1; i <= tLCMedBuyMainSet.size(); i++) {

				// tmpLCInsuAccTraceDB = new LCInsureAccTraceDB();
				// tmpLCInsuAccTraceDB.setSchema(tLCInsureAccTraceSet.get(i));
				tmpLCMedBuyMainDB = new LCMedBuyMainDB();
				tmpLCMedBuyMainDB.setSchema(tLCMedBuyMainSet.get(i));

				payDate = tmpLCMedBuyMainDB.getMakeDate();
				if (payDate == null || payDate.equals("")) {
					payDate = tmpLCMedBuyMainDB.getMakeDate();
					if (payDate == null || payDate.equals("")) {
						CError.buildErr(this, "缴费记录的缴费日期为空！");
						returnNull(aAccClassRet);
						return aAccClassRet;
					}
				}

				// 交费生效时间
				tMakeTime = tmpLCMedBuyMainDB.getMakeTime();

				// ==20060619===testing start===============
				logger.debug("第" + i + "条记录！");
				logger.debug("缴费日期：" + payDate);
				logger.debug("缴费时间：" + tMakeTime);
				logger.debug("缴费金额：" + tmpLCMedBuyMainDB.getSumClmPrice());
				// ==20060619==testind end===================

				// 临时存放帐户一笔缴费金额
				tempMoney = tmpLCMedBuyMainDB.getSumClmPrice();
				tInsureAccTraceMoneySum += tempMoney;

			}

			break;
		// 定期利率单利算法
		case 1:

			// 获取帐户固定利率AccRate
			tAccClassRate = tLMRiskInsuAccSchema.getAccRate();

			// 将原始固定利率转换为目标固定利率
			tCalAccClassRate = TransAccRate(tAccClassRate, aRateType, "S",
					aIntvType);

			// 筛选出paydate > 帐户分类表的结息日期BalaDate
			// 帐户型险种，对于分红险除公共帐户之外，trace表的payno字段放置class表
			// 的otherno字段的值, 每笔缴费在class表中都有一条记录，此称为先进先出型
			// 险种，用LMRiskInsuAcc表中的AccKind来区分；
			String tAccKind = tLMRiskInsuAccSchema.getAccKind();

			// 先进先出险种特殊处理
			String tOtherNo = aLCInsureAccClassSchema.getOtherNo();
			if (!tAccKind.equals("") && tAccKind.equals("3")) {
				if (tOtherNo.equals("")) {
					CError.buildErr(this, "保险帐户分类表数据不完整！");
					returnNull(aAccClassRet);
					return aAccClassRet;
				}
			}

			try {
				tLCMedBuyMainSet = tLCMedBuyMainDB.executeQuery(sqlbv22);
			} catch (Exception ex) {
				CError.buildErr(this, ex.toString());
				returnNull(aAccClassRet);
				return aAccClassRet;
			}

			for (int i = 1; i <= tLCMedBuyMainSet.size(); i++) {
				// tmpLCInsuAccTraceDB = new LCInsureAccTraceDB();
				// tmpLCInsuAccTraceDB =
				// (LCInsureAccTraceDB)tLCInsureAccTraceSet.get(i);
				tmpLCMedBuyMainDB = new LCMedBuyMainDB();
				tmpLCMedBuyMainDB.setSchema(tLCMedBuyMainSet.get(i));

				// 记录缴费日期
				payDate = tmpLCMedBuyMainDB.getMakeDate();

				// 交费生效时间
				tMakeTime = tmpLCMedBuyMainDB.getMakeTime();

				// 找出上次结息后的缴退费金额
				int tIntv = PubFun.calInterval(tBalaDate, payDate, aIntvType);
				if (tIntv >= 0) {

					// 日期相同则比较时间
					if (tIntv == 0 && tBalaTime.compareTo(tMakeTime) >= 0) {
						continue;
					}

					// 暂存一笔缴费金额
					tempMoney = tmpLCMedBuyMainDB.getSumClmPrice();
					tInsureAccTraceMoneySum += tempMoney;

					// 然后对轨迹表的这笔钱tempMoney作结息
					if (payDate.compareTo(aBalaDate) > 0) {
						tInterval = PubFun.calInterval(aBalaDate, payDate,
								aIntvType);
						tInterval = 0 - tInterval;
					} else {
						tInterval = PubFun.calInterval(payDate, aBalaDate,
								aIntvType);
					}

					aAccClassInterest += tempMoney
							* getIntvRate(tInterval, tCalAccClassRate, "S");
					aAccClassInterest = Arith.round(aAccClassInterest, 2);
				}
			}

			// 将帐户分类表的另一部分余额结息
			tInterval = PubFun.calInterval(tBalaDate, aBalaDate, aIntvType);
			if (tInterval > 0) {
				aAccClassInterest += tInsuAccClassBala
						* getIntvRate(tInterval, tCalAccClassRate, "S");
			}
			aAccClassInterest = Arith.round(aAccClassInterest, 2);
			break;

		// 定期利率复利算法
		case 2:

			// 获取固定利率
			tAccClassRate = tLMRiskInsuAccSchema.getAccRate();

			// 转换后的利率值
			tCalAccClassRate = TransAccRate(tAccClassRate, aRateType, "C",
					aIntvType);

			// 这个单子特殊处理，并且不分红，其他项目组可以不用
			// jixf add 20061016
			if (aLCInsureAccClassSchema.getGrpContNo().equals("230501012000")) {
				tAccClassRate = 0.03;
				tCalAccClassRate = TransAccRate(tAccClassRate, aRateType, "S",
						aIntvType);
			}

			tAccKind = tLMRiskInsuAccSchema.getAccKind();

			// 分红险特殊处理
			tOtherNo = aLCInsureAccClassSchema.getOtherNo();
			if (!tAccKind.equals("") && tAccKind.equals("3")) {
				if (tOtherNo.equals("")) {
					CError.buildErr(this, "保险帐户分类表数据不完整！");
					returnNull(aAccClassRet);
					return aAccClassRet;
				}
			}

			tLCMedBuyMainSet = tLCMedBuyMainDB.executeQuery(sqlbv22);

			for (int i = 1; i <= tLCMedBuyMainSet.size(); i++) {

				tmpLCMedBuyMainDB = new LCMedBuyMainDB();
				tmpLCMedBuyMainDB.setSchema(tLCMedBuyMainSet.get(i));

				// 交费日期
				payDate = tmpLCMedBuyMainDB.getMakeDate();

				// 交费生效时间
				tMakeTime = tmpLCMedBuyMainDB.getMakeTime();

				tempMoney = tmpLCMedBuyMainDB.getSumClmPrice();
				tInsureAccTraceMoneySum += tempMoney;

				// 然后对轨迹表的这笔钱tempMoney作结息
				// jixf add 20050802 对于PayDate>aBalaDate的情况，tInterval有问题，反之没有问题
				logger.debug("------" + payDate);
				logger.debug("------" + aBalaDate);
				logger.debug("-%%%%%%%%%--===="
						+ payDate.compareTo(aBalaDate));
				if (payDate.compareTo(aBalaDate) > 0) {
					logger.debug("33333");
					tInterval = PubFun.calInterval(aBalaDate, payDate,
							aIntvType);
					tInterval = 0 - tInterval;
				} else {
					tInterval = PubFun.calInterval(payDate, aBalaDate,
							aIntvType);
				}

				aAccClassInterest += tempMoney
						* getIntvRate(tInterval, tCalAccClassRate, "C");
				logger.debug("=======" + tInterval);
				logger.debug("=======" + tInsuAccClassBala);
				logger.debug("======="
						+ getIntvRate(tInterval, tCalAccClassRate, "C"));
				aAccClassInterest = Arith.round(aAccClassInterest, 2);

				// }
			}

			// 将帐户分类表的另一部分余额结息
			tInterval = PubFun.calInterval(tBalaDate, aBalaDate, aIntvType);
			if (tInterval > 0) {
				aAccClassInterest += tInsuAccClassBala
						* getIntvRate(tInterval, tCalAccClassRate, "C");
			}
			logger.debug("=======" + tInterval);
			logger.debug("=======" + tInsuAccClassBala);
			logger.debug("======="
					+ getIntvRate(tInterval, tCalAccClassRate, "C"));
			aAccClassInterest = Arith.round(aAccClassInterest, 2);
			break;

		// 活期利率单利算法
		case 3:

			tAccKind = tLMRiskInsuAccSchema.getAccKind();

			// 分红险特殊处理
			tOtherNo = aLCInsureAccClassSchema.getOtherNo();
			if (!tAccKind.equals("") && tAccKind.equals("3")) {
				if (tOtherNo.equals("")) {
					CError.buildErr(this, "保险帐户分类表数据不完整！");
					returnNull(aAccClassRet);
					return aAccClassRet;
				}
			}

			tLCMedBuyMainSet = tLCMedBuyMainDB.executeQuery(sqlbv22);

			for (int i = 1; i <= tLCMedBuyMainSet.size(); i++) {
				tmpLCMedBuyMainDB = new LCMedBuyMainDB();
				tmpLCMedBuyMainDB.setSchema(tLCMedBuyMainSet.get(i));

				// 记录交费日期
				payDate = tmpLCMedBuyMainDB.getMakeDate();

				// 记录交费生效时间
				tMakeTime = tmpLCMedBuyMainDB.getMakeTime();

				// 现在比较lcinsureaccclass表的baladate和lcinsuretrace表的paydate的值的大小
				int tIntv = PubFun.calInterval(tBalaDate, payDate, aIntvType);
				if (tIntv >= 0) {

					// 处理轨迹表中paydate>=帐户分类表的结息日期BalaDate
					if (tIntv == 0 && tBalaTime.compareTo(tMakeTime) >= 0) {
						continue;
					}

					// 临时存放LCInsuAccTraceDB表中的帐户缴费
					tempMoney = tmpLCMedBuyMainDB.getSumClmPrice();
					tInsureAccTraceMoneySum += tempMoney;

					// 对轨迹表的这笔钱tempMoney作结息
					String[] ResultRate2 = getMultiAccRateNew(aInsuAccNo,
							tLMRiskInsuAccSchema, payDate, aBalaDate,
							aRateType, "S", aIntvType, Period, tType, Depst);

					// 记录ResultRate2[]的有效数组长度
					tCount = Integer.parseInt(ResultRate2[0]);
					for (int m = 1; m < tCount + 1; m++) {
						if (ResultRate2[m] == null) {
							ResultRate2[m] = "0";
						}
						tCalAccClassRate = Double.parseDouble(ResultRate2[m]);
						double tSubInterest = tempMoney * tCalAccClassRate;
						tSubInterest = Arith.round(tSubInterest, 2);
						aAccClassInterest += tSubInterest;
					}
				}
			}

			// 将帐户分类表的另一部分余额结息
			ResultRate = getMultiAccRateNew(aInsuAccNo, tLMRiskInsuAccSchema,
					tBalaDate, aBalaDate, aRateType, "S", aIntvType, Period,
					tType, Depst);
			tCount = Integer.parseInt(ResultRate[0]);
			for (int m = 1; m < tCount + 1; m++) {
				if (ResultRate[m] == null) {
					ResultRate[m] = "0";
				}
				tCalAccClassRate = Double.parseDouble(ResultRate[m]);
				aAccClassInterest += tInsuAccClassBala * tCalAccClassRate;
				aAccClassInterest = Arith.round(aAccClassInterest, 2);
			}
			break;

		// 活期利率复利算法
		case 4:
			logger.debug("=====复利结息开始=====\n");

			tAccKind = tLMRiskInsuAccSchema.getAccKind();

			// 分红险特殊处理
			tOtherNo = aLCInsureAccClassSchema.getOtherNo();
			if (!tAccKind.equals("") && tAccKind.equals("3")) {
				if (tOtherNo.equals("")) {
					CError.buildErr(this, "保险帐户分类表数据不完整！");
					returnNull(aAccClassRet);
					return aAccClassRet;
				}
			}

			try {
				tLCMedBuyMainSet = tLCMedBuyMainDB.executeQuery(sqlbv22);
			} catch (Exception ex) {
				CError.buildErr(this, ex.toString());
				returnNull(aAccClassRet);
				return aAccClassRet;
			}
			for (int i = 1; i <= tLCMedBuyMainSet.size(); i++) {

				tmpLCMedBuyMainDB = new LCMedBuyMainDB();
				tmpLCMedBuyMainDB.setSchema(tLCMedBuyMainSet.get(i));

				payDate = tmpLCMedBuyMainDB.getMakeDate();
				if (payDate == null || payDate.equals("")) {
					payDate = tmpLCMedBuyMainDB.getMakeDate();
					if (payDate == null || payDate.equals("")) {
						CError.buildErr(this, "缴费记录的缴费日期为空！");
						returnNull(aAccClassRet);
						return aAccClassRet;
					}
				}

				// 交费生效时间
				tMakeTime = tmpLCMedBuyMainDB.getMakeTime();

				// ==20060619===testing start===============
				logger.debug("第" + i + "条记录！");
				logger.debug("缴费日期：" + payDate);
				logger.debug("缴费时间：" + tMakeTime);
				logger.debug("缴费金额：" + tmpLCMedBuyMainDB.getSumClmPrice());
				// ==20060619==testind end===================

				// 现在比较lcinsureaccclass表的baladate和lcinsuretrace表的paydate的值的大小
				int tIntv = PubFun.calInterval(tBalaDate, payDate, aIntvType);
				if (tIntv >= 0) {

					// 处理轨迹表中paydate>=帐户分类表的结息日期BalaDate
					if (tIntv == 0 && tBalaTime.compareTo(tMakeTime) >= 0) {
						continue;
					}

					// 临时存放帐户一笔缴费金额
					tempMoney = tmpLCMedBuyMainDB.getSumClmPrice();
					tInsureAccTraceMoneySum += tempMoney;

					// 然后对轨迹表的这笔钱tempMoney作结息
					String[] ResultRate2 = getMultiAccRateNew(aInsuAccNo,
							tLMRiskInsuAccSchema, payDate, aBalaDate,
							aRateType, "C", aIntvType, Period, tType, Depst);
					tCount = Integer.parseInt(ResultRate2[0]);

					// ====20060619==testing start=============
					logger.debug("查到" + tCount + "条利率值");
					// ===20060619==testing end================

					for (int m = 1; m < tCount + 1; m++) {
						if (ResultRate2[m] == null) {
							ResultRate2[m] = "0";
						}
						tCalAccClassRate = Double.parseDouble(ResultRate2[m]);

						// ===20060619==testing start===============
						logger.debug("Rate = " + tCalAccClassRate);
						// ===2060619===testing end=================

						double tSubInterest = tempMoney * tCalAccClassRate;
						tSubInterest = Arith.round(tSubInterest, 2);

						// ===20061018===testing======================
						logger.debug("利息： " + tSubInterest);
						// ===20061018===testing end==================

						aAccClassInterest += tSubInterest;
					}
				}
			}

			try {
				tLCInsureAccTraceSet = tLCInsureAccTraceDB.executeQuery(sqlbv38);
			} catch (Exception ex) {
				CError.buildErr(this, ex.toString());
				returnNull(aAccClassRet);
				return aAccClassRet;
			}
			for (int i = 1; i <= tLCInsureAccTraceSet.size(); i++) {

				tmpLCInsuAccTraceDB = new LCInsureAccTraceDB();
				tmpLCInsuAccTraceDB.setSchema(tLCInsureAccTraceSet.get(i));

				payDate = tmpLCInsuAccTraceDB.getPayDate();
				if (payDate == null || payDate.equals("")) {
					payDate = tmpLCInsuAccTraceDB.getMakeDate();
					if (payDate == null || payDate.equals("")) {
						CError.buildErr(this, "缴费记录的缴费日期为空！");
						returnNull(aAccClassRet);
						return aAccClassRet;
					}
				}

				// 交费生效时间
				tMakeTime = tmpLCInsuAccTraceDB.getMakeTime();

				// ==20060619===testing start===============
				logger.debug("第" + i + "条记录！");
				logger.debug("缴费日期：" + payDate);
				logger.debug("缴费时间：" + tMakeTime);
				logger.debug("缴费金额：" + tmpLCInsuAccTraceDB.getMoney());
				// ==20060619==testind end===================

				// 现在比较lcinsureaccclass表的baladate和lcinsuretrace表的paydate的值的大小
				int tIntv = PubFun.calInterval(tBalaDate, payDate, aIntvType);
				if (tIntv >= 0) {

					// 处理轨迹表中paydate>=帐户分类表的结息日期BalaDate,不判断是否为同一时刻的交费，所有交费都进行结息
					// if (tIntv == 0 && tBalaTime.compareTo(tMakeTime) >= 0) {
					// continue;
					// }

					// 临时存放帐户一笔缴费金额
					tempMoney = tmpLCInsuAccTraceDB.getMoney();
					tInsuAccClassBala += tempMoney; // 累加帐户交费金额

					// 然后对轨迹表的这笔钱tempMoney作结息
					String[] ResultRate2 = getMultiAccRateNew(aInsuAccNo,
							tLMRiskInsuAccSchema, payDate, aBalaDate,
							aRateType, "C", aIntvType, Period, tType, Depst);
					tCount = Integer.parseInt(ResultRate2[0]);

					// ====20060619==testing start=============
					logger.debug("查到" + tCount + "条利率值");
					// ===20060619==testing end================

					for (int m = 1; m < tCount + 1; m++) {
						if (ResultRate2[m] == null) {
							ResultRate2[m] = "0";
						}
						tCalAccClassRate = Double.parseDouble(ResultRate2[m]);

						// ===20060619==testing start===============
						logger.debug("Rate = " + tCalAccClassRate);
						// ===2060619===testing end=================

						double tSubInterest = tempMoney * tCalAccClassRate;
						tSubInterest = Arith.round(tSubInterest, 2);

						// ===20061018===testing======================
						logger.debug("利息： " + tSubInterest);
						// ===20061018===testing end==================

						aAccInterest += tSubInterest;
					}
				}
			}

			// 将帐户分类表的另一部分余额结息,注掉因为，消费不存trace，帐户余额不是准确的值，需要单独对帐户余额重新结息
			// logger.debug("=====将帐户分类表的另一部分余额结息=====\n");
			// ResultRate = getMultiAccRateNew(aInsuAccNo,
			// tLMRiskInsuAccSchema,
			// tBalaDate, aBalaDate,
			// aRateType, "C", aIntvType, Period,
			// tType, Depst);
			// tCount = Integer.parseInt(ResultRate[0]);
			// for (int m = 1; m < tCount + 1; m++) {
			// if (ResultRate[m] == null) {
			// ResultRate[m] = "0";
			// }
			// tCalAccClassRate = Double.parseDouble(ResultRate[m]);
			// aAccClassInterest += tInsuAccClassBala * tCalAccClassRate;
			// aAccClassInterest = Arith.round(aAccClassInterest, 2);
			// //===20060703===testing start========================
			// logger.debug("计算后的利率：" + tCalAccClassRate);
			// //===20060703==testing end===========================
			//
			// }
			break;
		}
		if (Integer.parseInt(tLMRiskInsuAccSchema.getAccComputeFlag()) != 0) {
			aAccClassSumPay = tInsuAccClassBala + aAccInterest
					- tInsureAccTraceMoneySum - aAccClassInterest; // 帐户余额 +
																	// 帐户余额结息后的利息
																	// -
																	// 医疗总表中的区间和
																	// -
																	// 医疗总表结息后的利息
		} else {
			aAccClassSumPay = tInsureAccTraceMoneySum;
		}
		// ===20061018===testing start==================
		logger.debug("Trace表缴费和： " + tInsuAccClassBala);
		logger.debug("Trace表缴费利息和： " + aAccInterest);
		logger.debug("医疗卡消费和： " + tInsureAccTraceMoneySum);
		logger.debug("医疗卡消费利息和： " + aAccClassInterest);

		logger.debug("帐户实际余额： " + aAccClassSumPay);
		// ===20061018===testing end====================

		// 准备返回的数据包
		aAccClassRet.setNameAndValue("aAccClassInterest", aAccClassInterest);
		aAccClassRet.setNameAndValue("aAccClassSumPay", aAccClassSumPay);

		return aAccClassRet;
	}
	
    /**
     * 已知帐户的利，计算红利的利息
     * @param tInsuAccNo 帐户号码
     * @param tGetBonusDate 红利领取日期
     * @param tRateType 原始利率类型
     * @param tIntvType 目标利率类型
     * @param tPeriod 期间
     * @param tFlag 标志（F:多条快速结息；S:单条结息）
     **/
    public double getInsuAccBonus(LOBonusPolHisSchema
                                  tLOBonusPolHisSchema,
                                  String tGetBonusDate,
                                  String tPeriod,
                                  String tRateType,
                                  String tIntvType,
                                  String tFlag) {
        logger.debug(
                "=====This is AccoutManage->getInsuAccClassBonus=====\n");
        String tInsuAccNo = tLOBonusPolHisSchema.getInsuAccNo();
        String tPolNo = tLOBonusPolHisSchema.getPolNo();
        if (tPolNo == null || tPolNo.equals("")) {
            logger.debug("个人险种保单号为空！");
            CError.buildErr(this, "接收数据失败！");
            return -1.0;
        }
        if (tInsuAccNo == null || tInsuAccNo.equals("")) {
            CError.buildErr(this, "帐户号为空！");
            CError.buildErr(this, "接收数据失败！");
            return -1.0;
        }
        if (tGetBonusDate == null || tGetBonusDate.equals("")) {
            logger.debug("红利领取日期为空！");
            CError.buildErr(this, "接收数据失败！");
            return -1.0;
        }
        //红利以及利息和
        double tBonusAndInterestSum = 0.00;

        //单条结息(Slow)
        if (tFlag.equals("S")) {

            //红利和
            double tSumBonusTotal = 0.0;

            //红利利息和
            double tSumInterest = 0.0;

            //分红日期
            String tBonusDate = "";
            tBonusDate = tLOBonusPolHisSchema.getBonusDate();
            if (tBonusDate == null || tBonusDate.equals("")) {
                tBonusDate = tLOBonusPolHisSchema.getMakeDate();
                if (tBonusDate.equals("")) {
                    logger.debug("帐户分红日期为空！");
                    return -1.0;
                }
            }

            //活期利率单利结息算法
            int tIntv = PubFun.calInterval(tBonusDate, tGetBonusDate, "D");

            //红利领取日期 > 分红日期
            if (tIntv > 0) {

                //临时存放帐户一笔红利金额

                double tSumBonus = tLOBonusPolHisSchema.getSumBonus();
                tSumBonusTotal += tSumBonus;

                //然后对分红明显表本期红利作结息
                String tSql = "";
                String tSql1 = "";
                String tSql2 = "";
                tSql = "select * from LDBankRate where " +
                        " DeclareDate <= to_date('" + "?tGetBonusDate?" +
                        "', 'yyyy-mm-dd') and EndDate >= to_date('" +
                        "?tBonusDate?" + "', 'yyyy-mm-dd') and Period = " +
                        "?tPeriod?" + "";
                 if (tRateType != null) {
                     tSql1 = tSql + " and PeriodFlag = '" + "?tRateType?" + "'";
                 }
                 tSql1 = tSql1 + " order by EndDate";
                 SQLwithBindVariables sqlbva = new SQLwithBindVariables();
                 sqlbva.sql(tSql1);
                 sqlbva.put("tGetBonusDate", tGetBonusDate);
                 sqlbva.put("tBonusDate", tBonusDate);
                 sqlbva.put("tPeriod", tPeriod);
                 sqlbva.put("tRateType", tRateType);
                 String[] ResultRate = getBankRate(sqlbva, tRateType, "S",
                                                   tIntvType, tBonusDate,
                                                   tGetBonusDate);
                 int tCount = Integer.parseInt(ResultRate[0]);

                 //异常处理
                 if (tCount <= 0) {
                     tRateType = "M";
                     if (tRateType != null) {
                         tSql2 = tSql + " and PeriodFlag = '" + "?tRateType?" + "'";
                     }
                     tSql2 = tSql2 + " order by EndDate";
                     SQLwithBindVariables sqlbvb = new SQLwithBindVariables();
                     sqlbvb.sql(tSql2);
                     sqlbvb.put("tGetBonusDate", tGetBonusDate);
                     sqlbvb.put("tBonusDate", tBonusDate);
                     sqlbvb.put("tPeriod", tPeriod);
                     sqlbvb.put("tRateType", tRateType);
                     ResultRate = getBankRate(sqlbvb, tRateType, "S",
                                              tIntvType, tBonusDate,
                                              tGetBonusDate);
                     tCount = Integer.parseInt(ResultRate[0]);
                     if (tCount <= 0) {
                         CError.buildErr(this, "查询利率表失败！");
                         return -1.0;
                     }
                 }
                 double tCalAccClassRate = 0.0;
                 for (int m = 1; m < tCount + 1; m++) {
                     if (ResultRate[m] == null) {
                         ResultRate[m] = "0";
                     }
                     tCalAccClassRate = Double.parseDouble(ResultRate[m]);
                     double tSubInterest = tSumBonus * tCalAccClassRate;

                     //===20060804===testing start================
                     logger.debug("本金： " + tSumBonus);
                     logger.debug("利率： " + tCalAccClassRate);
                     logger.debug("利息： " + tSubInterest);
                     //===20060804===testing end==================


                     tSumInterest += tSubInterest;
                 }
             }
             tBonusAndInterestSum = tSumInterest + tSumBonusTotal;
         }

         //快速结息(因为很多时候，分红日期即BonusDate是同一天，这就给结息制造了方便)
         if (tFlag.equals("F")) {
             String tSql = "";
             tSql = "select sum(SumBonus) from LOBonusPolHis a where " +
                    "GrpPolNo = '" + "?GrpPolNo?" +
                    "' and BonusGetMode = '2' and BonusDate = '" +
                    "?BonusDate?" +
                    "' and not exists (select 1 from LMRiskInsuAcc where " +
                    "InsuAccNo = a.InsuAccNo and Owner = '2') ";
             logger.debug("tSql: " + tSql);
             SQLwithBindVariables sqlbvt = new SQLwithBindVariables();
             sqlbvt.sql(tSql);
             sqlbvt.put("GrpPolNo", tLOBonusPolHisSchema.getGrpPolNo());
             sqlbvt.put("BonusDate", tLOBonusPolHisSchema.getBonusDate());
             
             SSRS tSSRS = new SSRS();
             ExeSQL tExeSql = new ExeSQL();
             try {
                 tSSRS = tExeSql.execSQL(sqlbvt);
             } catch (Exception ex) {
                 CError.buildErr(this, ex.toString());
                 ex.printStackTrace();
                 return -1.0;
             }
             if (tSSRS == null || tSSRS.MaxRow <= 0) {
                 CError.buildErr(this, "查询失败！");
                 return -1.0;
             }
             double tBonusTotal = 0.00;
             String sBonusTotal = tSSRS.GetText(1, 1);
             if (sBonusTotal == null || sBonusTotal.equals("null")) {
                 CError.buildErr(this, "传入红利金额为空！");
                 return -1.0;
             }
             tBonusTotal = Double.parseDouble(sBonusTotal);
             tLOBonusPolHisSchema.setSumBonus(tBonusTotal);
             tBonusAndInterestSum = this.getInsuAccBonus(
                     tLOBonusPolHisSchema, tGetBonusDate,
                     tPeriod, tRateType, tIntvType, "S");
         }

        return tBonusAndInterestSum;
    }
    
    
    
    
    
    
    //-----------------------------MS分段结息 begin----------------------------------
    /**
	 * 分段计息函数
	 * @param tCodeType 项目
	 * @param tRLType   存借类型
	 * @param SCType    单利/复利
	 * @param YMDinterest 年利/月利/日利
	 * @param tStartDate  开始时间 
	 * @param tBalaDate    结算时间
	 * @return   利息乘积
	 */
	private static double getMultiResultRateMS(Calculator tCalculator ,String tSCType,
			String tYMDinterest,String tStartDate,String tBalaDate)
	{
		double ResultRate = 0.0;
	
//		String tsql= " select rate,enddate from "+tTableName+" where codetype='"+tCodeType+"' "
//		+" and RLType = '"+tRLType+"' and SCType='"+tSCType+"' "+" and YMDinterest='"+tYMDinterest+"' "
//		+" and StartDate<=to_date('"+tBalaDate+"','yyyy-mm-dd') "
//		+" and EndDate>=to_date('"+tStartDate+"','yyyy-mm-dd') order by EndDate ";
		
		String[] ResultArray;
		String tsql= tCalculator.getCalSQL();
		TransferData tParams = new TransferData();
		tParams = tCalculator.getCalSQLParams();
		ResultArray = getTablRateMS(tsql,tParams, tYMDinterest, tSCType, "D", tStartDate,
				tBalaDate);
		
		if(tSCType.equals("S"))
		{
			int count = Integer.parseInt(ResultArray[0]);
			for (int i=1;i<=count;i++)
			{
				double tRate = Double.parseDouble(ResultArray[i]);
				ResultRate +=tRate;   //单利相加
			}
		}
		else if(tSCType.equals("C"))
		{
			ResultRate = 1; 
			int count = Integer.parseInt(ResultArray[0]);
			for (int i=1;i<=count;i++)
			{
				if(ResultArray[i]==null||ResultArray[i].equals(""))
				{
					return -1;
				}
				double tRate = Double.parseDouble(ResultArray[i]);
				if(tRate==0)
				{
					return -1;
				}
				ResultRate=ResultRate*tRate;   //复利连乘 
			}
			ResultRate = ResultRate - 1; 
		}
		return ResultRate;
	}
	
	private static String[] getTablRateMS(SQLwithBindVariables tSql, String aRateType,
			 String aTransType, String aIntervalType,
			String aOriginBalaDate, String aBalanceDate) {
		String aDate = "";
		double aValue = 0.0;
		double aResult = 0.0;
		String tStartDate = "";
		SSRS tSSRS = new SSRS();
//		String[] ResultArray = new String[100];
		ExeSQL tExeSQL = new ExeSQL();
		tSSRS = tExeSQL.execSQL(tSql);
		if (tSSRS == null || tSSRS.MaxRow <= 0) {
			logger.debug(tSql + "查询利率为空！");
			return null;
		}
		int MaxRow = tSSRS.getMaxRow();
		String[] ResultArray = new String[MaxRow+1];
		for (int i = 1; i <= MaxRow; i++) {
			
			aValue = Double.parseDouble(tSSRS.GetText(i, 1));
			aDate = String.valueOf(tSSRS.GetText(i, 2));
			double tCalAccRate = TransAccRate(aValue, aRateType, aTransType,
					aIntervalType);
			if (i == 1) {
				tStartDate = aOriginBalaDate;
			}

			// 结息点超过某个利率临界点
			if (PubFun.calInterval(aDate, aBalanceDate, aIntervalType) > 0) {
				int tInterval = PubFun.calInterval(tStartDate, aDate,
						aIntervalType);
				if (tInterval >= 0) {
					aResult = getIntvRateMS(tInterval, tCalAccRate, aTransType);
					ResultArray[i] = String.valueOf(aResult);
				}
				tStartDate = aDate;
			} else {
				int tInterval = PubFun.calInterval(tStartDate, aBalanceDate,
						aIntervalType);
				if (tInterval >= 0) {
					aResult = getIntvRateMS(tInterval, tCalAccRate, aTransType);
					ResultArray[i] = String.valueOf(aResult);
				}
				break;
			}
		}
		ResultArray[0] = String.valueOf(MaxRow);

		return ResultArray;
	}
	
	private static String[] getTablRateMS(String tSql,TransferData tParams, String aRateType,
			 String aTransType, String aIntervalType,
			String aOriginBalaDate, String aBalanceDate) {
		String aDate = "";
		double aValue = 0.0;
		double aResult = 0.0;
		String tStartDate = "";
		SSRS tSSRS = new SSRS();
//		String[] ResultArray = new String[100];
		VData tVData = new VData();
		tVData.add(0,tSql);
		tVData.add(1,tParams);
		ExeSQL tExeSQL = new ExeSQL();
		tSSRS = tExeSQL.execSQL(tVData);
		if (tSSRS == null || tSSRS.MaxRow <= 0) {
			logger.debug(tSql + "查询利率为空！");
			return null;
		}
		int MaxRow = tSSRS.getMaxRow();
		String[] ResultArray = new String[MaxRow+1];
		for (int i = 1; i <= MaxRow; i++) {
			
			aValue = Double.parseDouble(tSSRS.GetText(i, 1));
			aDate = String.valueOf(tSSRS.GetText(i, 2));
			double tCalAccRate = TransAccRate(aValue, aRateType, aTransType,
					aIntervalType);
			if (i == 1) {
				tStartDate = aOriginBalaDate;
			}

			// 结息点超过某个利率临界点
			if (PubFun.calInterval(aDate, aBalanceDate, aIntervalType) > 0) {
				int tInterval = PubFun.calInterval(tStartDate, aDate,
						aIntervalType);
				if (tInterval >= 0) {
					aResult = getIntvRateMS(tInterval, tCalAccRate, aTransType);
					ResultArray[i] = String.valueOf(aResult);
				}
				tStartDate = aDate;
			} else {
				int tInterval = PubFun.calInterval(tStartDate, aBalanceDate,
						aIntervalType);
				if (tInterval >= 0) {
					aResult = getIntvRateMS(tInterval, tCalAccRate, aTransType);
					ResultArray[i] = String.valueOf(aResult);
				}
				break;
			}
		}
		ResultArray[0] = String.valueOf(MaxRow);

		return ResultArray;
	}
	
	/**
	 * 得到计算时间间隔的比率
	 * 
	 * @param aInterval
	 * @param aRate
	 * @param aTransType
	 * @return
	 */
	private static double getIntvRateMS(int aInterval, double aRate,
			String aTransType) {
		double aIntvRate = 0.0;

		// Add by Minim for RF of BQ
		if (aTransType.equals("1")) {
			aTransType = "S";
		}
		if (aTransType.equals("2")) {
			aTransType = "C";
		}
		// End add by Minim;

		if (aTransType.equals("S")) {
			aIntvRate = aRate * aInterval;
//			aIntvRate = (1+aRate) * aInterval;
		} else if (aTransType.equals("C")) {
//			aIntvRate = java.lang.Math.pow(1 + aRate, aInterval) - 1;
			aIntvRate = java.lang.Math.pow(1 + aRate, aInterval);
		} else {

		}
		return aIntvRate;
	}
	
	
	//---------------------------add by wk----------------------
	/**
	 * 分段计息函数 支持单利和复利两种方式
	 * @param tStartDate 计息开始时间
	 * @param tBalaDate  结算时间
	 * @param tRiskCode 险种编码  默认为000000
	 * @param tEdorType 业务类型    默认为00 
	 * @param tRLType   存借类型 针对客户 R-存款 L-贷款
	 * @param SCType    单利/复利 S-单利 C-复利
	 * @param YMDinterest 年利/月利/日利  Y-年利 M-月 D-日
	 * @param Currency 币种
	 * @return   利率
	 */
	public static double calMultiRateMS(String tStartDate,String tBalaDate, String tRiskCode,
			String tEdorType,String tRLType,String tSCType,
			String tYMDinterest,String tCurrency) {
		// 查询算法编码
		LMEdorCalDB tLMEdorCalDB = new LMEdorCalDB();
		tLMEdorCalDB.setRiskCode(tRiskCode);
		tLMEdorCalDB.setEdorType(tEdorType);
		tLMEdorCalDB.setCalType("Rate");
		LMEdorCalSet tLMEdorCalSet = tLMEdorCalDB.query(); // 查找保费累计变动金额计算信息
		if (tLMEdorCalSet == null || tLMEdorCalSet.size() <= 0) {
			// 查询公共的
			tLMEdorCalDB.setRiskCode("000000");
			tLMEdorCalDB.setEdorType("00");
			tLMEdorCalDB.setCalType("Rate");
			tLMEdorCalSet = tLMEdorCalDB.query();

			if (tLMEdorCalSet == null || tLMEdorCalSet.size() <= 0) {
				// @@错误处理
				logger.debug("未找到通用计算法！");
				 
				return -1;
			}
		}
		if (tLMEdorCalDB.mErrors.needDealError()) {
			// 查询公共的
			tLMEdorCalDB.setRiskCode("000000");
			tLMEdorCalDB.setEdorType("00");
			tLMEdorCalDB.setCalType("Rate");
			tLMEdorCalSet = tLMEdorCalDB.query();

			if (tLMEdorCalSet == null || tLMEdorCalSet.size() <= 0) {
				return -1;
			}
		}
 
		
		Calculator tCalculator = new Calculator();
		tCalculator.setCalCode(tLMEdorCalSet.get(1).getCalCode());
		tCalculator.addBasicFactor("CalType", tLMEdorCalDB.getEdorType()); 
		tCalculator.addBasicFactor("RiskCode", tLMEdorCalDB.getRiskCode());
		tCalculator.addBasicFactor("RLType", tRLType);
		tCalculator.addBasicFactor("SCType", tSCType);
		tCalculator.addBasicFactor("YMDinterest", tYMDinterest);
		tCalculator.addBasicFactor("BalaDate", tBalaDate);
		tCalculator.addBasicFactor("StartDate", tStartDate); 
		tCalculator.addBasicFactor("Currency", tCurrency);
//		tCalculator.

//		mRate = tBqCalBL.calChgMoney(tLMEdorCalSet.get(1).getCalCode(),
//				tBqCalBase);
		double tRate = getMultiResultRateMS(tCalculator, tSCType, 
				tYMDinterest, tStartDate, tBalaDate);
 
		return tRate;
	}
	//---------------------------end wk ------------------------
	
	//---------------------------add by wk----------------------
	/**
	 */
	/**
	 * @param tLMRiskInsuAccSchema  帐户信息
	 * @param tStartDate 结算开始时间
	 * @param tBalaDate  结算终止时间
	 * @param tCurrency  币种
	 * @return
	 */
	public static double calMultiRateForAccMS(LMRiskInsuAccSchema tLMRiskInsuAccSchema,
			String tStartDate,String tBalaDate,String tCurrency)
	{
		// 查询算法编码
		double tRate = 0.0;
		if(tLMRiskInsuAccSchema.getAccComputeFlag().equals("3")
				||tLMRiskInsuAccSchema.getAccComputeFlag().equals("4"))
		{
			String tYMDinterest = "Y";
			String tRLType = "R";
			String tSCType = tLMRiskInsuAccSchema.getAccComputeFlag().equals("3")?"S":"C";
		
			Calculator tCalculator = new Calculator();
			tCalculator.setCalCode(tLMRiskInsuAccSchema.getAccCancelCode());
			tCalculator.addBasicFactor("CalType", tLMRiskInsuAccSchema.getInsuAccNo()); 
			tCalculator.addBasicFactor("RiskCode", "000000");
			tCalculator.addBasicFactor("RLType", tRLType);
			tCalculator.addBasicFactor("SCType", tSCType);
			tCalculator.addBasicFactor("YMDinterest", tYMDinterest);
			tCalculator.addBasicFactor("BalaDate", tBalaDate);
			tCalculator.addBasicFactor("StartDate", tStartDate); 
			tCalculator.addBasicFactor("Currency", tCurrency); 
 
			tRate = getMultiResultRateMS(tCalculator, tSCType, 
					tYMDinterest, tStartDate, tBalaDate);
		}
		else
		{
			//modify by jiaqiangli 2009-06-05 不计息置0不置出错标记(-1)
			tRate = 0.00;
			//tRate = -1;
		}
		logger.debug("Rate:"+tRate);
		return tRate;
	}
	
	
	
	
	/**
	 * @param aLCInsureAccClassSchema 
	 * @param aBalaDate 结算时间
	 * @param aRateType 原始利率类型 
	 * @param aIntvType 目标利率类型
	 * 一般情况下原始利率类型、目标利率类型都为年利率
	 * @return
	 */
	public TransferData getAccClassInterestNewMS(
			LCInsureAccClassSchema aLCInsureAccClassSchema, String aBalaDate,
			String aRateType, String aIntvType) {

		logger.debug("=====This is getAccClassInterestNew!=====\n");

		// 记录帐户分类表的利息值
		double aAccClassInterest = 0.0;

		// 记录帐户分类表的本息和
		double aAccClassSumPay = 0.0;

		ExeSQL tExeSql = new ExeSQL();
		SSRS tSSRS = new SSRS();

		String tsql = "select poltypeflag from lcpol where polno ='"
				+ "?m6?" + "' ";
		SQLwithBindVariables sqlbv26=new SQLwithBindVariables();
	    sqlbv26.sql(tsql);
	    sqlbv26.put("m6",aLCInsureAccClassSchema.getPolNo());
	    
		ExeSQL tExeSQL = new ExeSQL();
		String tpoltypeflag = tExeSQL.getOneValue(sqlbv26);
		//modify by sunsx 公共账户与正常账户一样,不重新结算
//		if (tpoltypeflag == "2" || tpoltypeflag.equals("2")) {
//			logger.debug("=====poltype=" + tpoltypeflag
//					+ "=====对公共账户重新结息！");
//			aLCInsureAccClassSchema.setBalaDate("1900-1-1");
//			aLCInsureAccClassSchema.setBalaTime("00:00:00");
//			aLCInsureAccClassSchema.setInsuAccBala("0");
//		}

		//MS无此字段 被屏蔽  modify by wk 2008-11-13
//		String accSql = "select count(*) from lmriskapp where riskcode ='"
//				+ aLCInsureAccClassSchema.getRiskCode()
//				+ "' and riskflagacc ='D' "; // riskflagacc ='D'
//												// 代表139，151，121，122，108险种，D类险
//		String accCount = tExeSql.getOneValue(accSql);
//
//		if (!accCount.equals("0")) {
//
//			logger.debug("=====对" + aLCInsureAccClassSchema.getRiskCode()
//					+ "账户重新结息！=====");
//			aLCInsureAccClassSchema.setBalaDate("1900-1-1");
//			aLCInsureAccClassSchema.setBalaTime("00:00:00");
//			aLCInsureAccClassSchema.setInsuAccBala("0");
//		}
		// 记录返回值利息和本息
		TransferData aAccClassRet = new TransferData();

		// 检验数据有效性
		if (!verifyNotNull("当前结息日期", aBalaDate)) {
			returnNull(aAccClassRet);
			return aAccClassRet;
		}
		if (!verifyNotNull("原始利率类型", aRateType)) {
			returnNull(aAccClassRet);
			return aAccClassRet;
		}
		if (!verifyNotNull("目标利率类型", aIntvType)) {
			returnNull(aAccClassRet);
			return aAccClassRet;
		}

		// 记录结息间隔
		int tInterval = 0;

		// 记录查询LCInsureAccClassTrace表返回有效记录的个数
		int tCount = 0;

		// 记录原始利率值
		double tAccClassRate = 0.00;

		// 记录目标利率值
		double tCalAccClassRate = 0.00;

		// 记录保险帐户现金余额
		double tInsuAccClassBala = aLCInsureAccClassSchema.getInsuAccBala();

		// 得到险种保险帐户描述表(lmRiskInsuAcc)中帐户利率
		// LMRiskInsuAccDB tLMRiskInsuAccDB = new LMRiskInsuAccDB();

		// 记录保险帐户号码
		String aInsuAccNo = aLCInsureAccClassSchema.getInsuAccNo();
		if (!verifyNotNull("保险帐户号码", aInsuAccNo)) {
			returnNull(aAccClassRet);
			return aAccClassRet;
		}

		// 记录帐户分类表上次结息日期
		String tBalaDate = aLCInsureAccClassSchema.getBalaDate();

		// 记录帐户分类表上次结息时间
		String tBalaTime = aLCInsureAccClassSchema.getBalaTime();

		// 上次结息日期为空或者不存在则取入机日期
		if (tBalaDate == null || tBalaDate.equals("")) {
			tBalaDate = aLCInsureAccClassSchema.getMakeDate();
		}

		// 上次结息时间如果为空或者不存在则取入机时间
		if (tBalaTime == null || tBalaTime.equals("")) {
			tBalaTime = aLCInsureAccClassSchema.getMakeTime();
		}
		LCInsureAccTraceDB tLCInsureAccTraceDB = new LCInsureAccTraceDB();
		LCInsureAccTraceSet tLCInsureAccTraceSet = new LCInsureAccTraceSet();

		// 得到帐户相关信息
		LMRiskInsuAccSchema tLMRiskInsuAccSchema = new LMRiskInsuAccSchema();
		tLMRiskInsuAccSchema = mCRI.findRiskInsuAccByInsuAccNo(aInsuAccNo);
		if (tLMRiskInsuAccSchema == null) {
			CError.buildErr(this, "查询险种保险帐户信息失败！");
			returnNull(aAccClassRet);
			return aAccClassRet;
		}

		// 该缴费帐户的缴退费总和
		double tInsureAccTraceMoneySum = 0.0;

		// 查询利率信息
		String[] ResultRate = null;
		if (tLMRiskInsuAccSchema.getInsuAccNo() == null
				|| tLMRiskInsuAccSchema.getInsuAccNo().equals("")) {

			CError.buildErr(this, "查询险种保险帐户信息失败！");
			aAccClassRet
					.setNameAndValue("tAccClassInterest", aAccClassInterest);
			aAccClassRet.setNameAndValue("tAccClassSumPay", aAccClassSumPay);
			return aAccClassRet;
		}

		String tSql;
		LCInsureAccTraceDB tmpLCInsuAccTraceDB = null;
		String tMoneyType = "";
		String payDate = "";
		String tMakeTime = "";
		double tempMoney = 0.00;

		// -----testing start----20060216-----------------------
		logger.debug("************开始结息****************");
		logger.debug("保险帐户现金余额: " + tInsuAccClassBala);
		logger.debug("本次结息日期: " + aBalaDate);
		logger.debug("上次结息日期: " + tBalaDate);
		logger.debug("上次结息时间: " + tBalaTime);
		// ----------testing end--------------------------------

		switch (Integer.parseInt(tLMRiskInsuAccSchema.getAccComputeFlag())) {

		// 不计息
		case 0:

			tSql = "select * from LCInsureAccTrace where PolNo = '"
					+ "?m7?"
					+ "' and InsuAccNo = '" + "?m8?"
					+ "' and PayPlanCode = '"
					+ "?m9?"
					+ "' and AccAscription = '"
					+ "?m10?"
					+ "' and PayDate <= '" + aBalaDate + "'";
			SQLwithBindVariables sqlbv27=new SQLwithBindVariables();
		    sqlbv27.sql(tSql);
		    sqlbv27.put("m7",aLCInsureAccClassSchema.getPolNo());
		    sqlbv27.put("m8",aInsuAccNo);
		    sqlbv27.put("m9",aLCInsureAccClassSchema.getPayPlanCode());
		    sqlbv27.put("m10",aLCInsureAccClassSchema.getAccAscription());

			logger.debug("tSql: " + tSql);
			try {
				tLCInsureAccTraceSet = tLCInsureAccTraceDB.executeQuery(sqlbv27);
			} catch (Exception ex) {
				CError.buildErr(this, ex.toString());
				returnNull(aAccClassRet);
				return aAccClassRet;
			}
			for (int i = 1; i <= tLCInsureAccTraceSet.size(); i++) {

				tmpLCInsuAccTraceDB = new LCInsureAccTraceDB();
				tmpLCInsuAccTraceDB.setSchema(tLCInsureAccTraceSet.get(i));

				// 如果LCInsureAccTrace表中的moneyType字段的值为PX或者SX,则不作结息;
				tMoneyType = tmpLCInsuAccTraceDB.getMoneyType();
				if (tMoneyType.equals("PX") || tMoneyType.equals("SX")) {
					continue;
				}
				payDate = tmpLCInsuAccTraceDB.getPayDate();
				if (payDate == null || payDate.equals("")) {
					payDate = tmpLCInsuAccTraceDB.getMakeDate();
					if (payDate == null || payDate.equals("")) {
						CError.buildErr(this, "缴费记录的缴费日期为空！");
						returnNull(aAccClassRet);
						return aAccClassRet;
					}
				}

				// 交费生效时间
				tMakeTime = tmpLCInsuAccTraceDB.getMakeTime();

				// ==20060619===testing start===============
				logger.debug("第" + i + "条记录！");
				logger.debug("缴费日期：" + payDate);
				logger.debug("缴费时间：" + tMakeTime);
				logger.debug("缴费金额：" + tmpLCInsuAccTraceDB.getMoney());
				// ==20060619==testind end===================

				// 临时存放帐户一笔缴费金额
				tempMoney = tmpLCInsuAccTraceDB.getMoney();
				tInsureAccTraceMoneySum += tempMoney;

			}

			break;
		// 定期利率单利算法
		case 1:

			// 获取帐户固定利率AccRate
			tAccClassRate = tLMRiskInsuAccSchema.getAccRate();

			// 将原始固定利率转换为目标固定利率
			tCalAccClassRate = TransAccRate(tAccClassRate, aRateType, "S",
					aIntvType);

			// 筛选出paydate > 帐户分类表的结息日期BalaDate
			// 帐户型险种，对于分红险除公共帐户之外，trace表的payno字段放置class表
			// 的otherno字段的值, 每笔缴费在class表中都有一条记录，此称为先进先出型
			// 险种，用LMRiskInsuAcc表中的AccKind来区分；
			String tAccKind = tLMRiskInsuAccSchema.getAccKind();

			// 先进先出险种特殊处理
			String tOtherNo = aLCInsureAccClassSchema.getOtherNo();
			if (!tAccKind.equals("") && tAccKind.equals("3")) {
				if (tOtherNo.equals("")) {
					CError.buildErr(this, "保险帐户分类表数据不完整！");
					returnNull(aAccClassRet);
					return aAccClassRet;
				}
				tLCInsureAccTraceDB.setPayNo(tOtherNo);
			}
			tLCInsureAccTraceDB.setPolNo(aLCInsureAccClassSchema.getPolNo());
			tLCInsureAccTraceDB.setInsuAccNo(aInsuAccNo);
			tLCInsureAccTraceDB.setPayPlanCode(aLCInsureAccClassSchema
					.getPayPlanCode());
			tLCInsureAccTraceDB.setAccAscription(aLCInsureAccClassSchema
					.getAccAscription());
			try {
				tLCInsureAccTraceSet = tLCInsureAccTraceDB.query();
			} catch (Exception ex) {
				CError.buildErr(this, ex.toString());
				returnNull(aAccClassRet);
				return aAccClassRet;
			}

			for (int i = 1; i <= tLCInsureAccTraceSet.size(); i++) {
				tmpLCInsuAccTraceDB = new LCInsureAccTraceDB();
				tmpLCInsuAccTraceDB = (LCInsureAccTraceDB) tLCInsureAccTraceSet
						.get(i);

				// 如果LCInsureAccTrace表中的moneyType字段的值为PX或者SX,则不作结息;
				tMoneyType = tmpLCInsuAccTraceDB.getMoneyType();
				if (tMoneyType.equals("PX") || tMoneyType.equals("SX")) {
					continue;
				}

				// 记录缴费日期
				payDate = tmpLCInsuAccTraceDB.getPayDate();

				// 交费生效时间
				tMakeTime = tmpLCInsuAccTraceDB.getMakeTime();

				// 找出上次结息后的缴退费金额
				int tIntv = PubFun.calInterval(tBalaDate, payDate, aIntvType);
				if (tIntv >= 0) {

					// 日期相同则比较时间
					if (tIntv == 0 && tBalaTime.compareTo(tMakeTime) >= 0) {
						continue;
					}

					// 暂存一笔缴费金额
					tempMoney = tmpLCInsuAccTraceDB.getMoney();
					tInsureAccTraceMoneySum += tempMoney;

					// 然后对轨迹表的这笔钱tempMoney作结息
					if (payDate.compareTo(aBalaDate) > 0) {
						tInterval = PubFun.calInterval(aBalaDate, payDate,
								aIntvType);
						tInterval = 0 - tInterval;
					} else {
						tInterval = PubFun.calInterval(payDate, aBalaDate,
								aIntvType);
					}
					/*
					 * if (tInterval > 0) { aAccClassInterest += tempMoney *
					 * getIntvRate(tInterval, tCalAccClassRate, "S"); } else {
					 * tInterval = 0; aAccClassInterest += tempMoney *
					 * getIntvRate(tInterval, tCalAccClassRate, "S"); }
					 */
					// jixf chg 20060722不区分时间间隔，可以往回反结息
					aAccClassInterest += tempMoney
							* getIntvRate(tInterval, tCalAccClassRate, "S");
					aAccClassInterest = Arith.round(aAccClassInterest, 2);
				}
			}

			// 将帐户分类表的另一部分余额结息
			tInterval = PubFun.calInterval(tBalaDate, aBalaDate, aIntvType);
			if (tInterval > 0) {
				aAccClassInterest += tInsuAccClassBala
						* getIntvRate(tInterval, tCalAccClassRate, "S");
			}
			aAccClassInterest = Arith.round(aAccClassInterest, 2);
			break;

		// 定期利率复利算法
		case 2:

			// 获取固定利率
			tAccClassRate = tLMRiskInsuAccSchema.getAccRate();

			// 转换后的利率值
			tCalAccClassRate = TransAccRate(tAccClassRate, aRateType, "C",
					aIntvType);

			// 这个单子特殊处理，并且不分红，其他项目组可以不用
			// jixf add 20061016
			if (aLCInsureAccClassSchema.getGrpContNo().equals("230501012000")) {
				tAccClassRate = 0.03;
				tCalAccClassRate = TransAccRate(tAccClassRate, aRateType, "S",
						aIntvType);
			}

			// 筛选出paydate > 帐户分类表的结息日期BalaDate
			// 帐户型险种，对于分红险，trace表的payno字段放置class表的otherno字段的值；
			// tSql = "select BonusFlag from LMRiskApp a where exists " +
			// "(select * from LCPol where PolNo = '" +
			// aLCInsureAccClassSchema.getPolNo() +
			// "' and RiskCode = a.RiskCode)";
			// tSql = "select AccKind from LMRiskInsuAcc where " +
			// "InsuAccNo = '" + aInsuAccNo + "'";
			// try{
			// tSSRS = tExeSql.execSQL(tSql);
			// } catch (Exception ex){
			// CError.buildErr(this, ex.toString());
			// returnNull(aAccClassRet);
			// return aAccClassRet;
			// }
			// tAccKind = tSSRS.GetText(1, 1);
			// ExeSQL ttExeSql = new ExeSQL();
			// SSRS ttSSRS = new SSRS();
			tAccKind = tLMRiskInsuAccSchema.getAccKind();

			// 分红险特殊处理
			tOtherNo = aLCInsureAccClassSchema.getOtherNo();
			if (!tAccKind.equals("") && tAccKind.equals("3")) {
				if (tOtherNo.equals("")) {
					CError.buildErr(this, "保险帐户分类表数据不完整！");
					returnNull(aAccClassRet);
					return aAccClassRet;
				}
				// tLCInsureAccTraceDB.setPayNo(tOtherNo);
			}
			tSql = "select * from LCInsureAccTrace where polno='"
					+ "?m7?" + "' and InsuAccNo='"
					+ "?m8?" + "' and PayPlanCode='"
					+ "?m9?"
					+ "' and PayNo='" + "?m10?" + "' and (PayDate>'"
					+ "?m11?" + "' or (PayDate='" + "?m12?"
					+ "' and MakeTime>'" + "?m14?" + "')) and PayDate<='"
					+ "?m15?" + "'";
			SQLwithBindVariables sqlbv28=new SQLwithBindVariables();
		    sqlbv28.sql(tSql);
		    sqlbv28.put("m7",aLCInsureAccClassSchema.getPolNo());
		    sqlbv28.put("m9",aInsuAccNo);
		    sqlbv28.put("m10",aLCInsureAccClassSchema.getPayPlanCode());
		    sqlbv28.put("m11",tOtherNo);
		    sqlbv28.put("m12",tBalaDate);
		    sqlbv28.put("m13",tBalaDate);
		    sqlbv28.put("m14",tBalaTime);
		    sqlbv28.put("m15", aBalaDate);
			logger.debug("------" + tSql);

			/*
			 * tLCInsureAccTraceDB.setPolNo(aLCInsureAccClassSchema.getPolNo());
			 * tLCInsureAccTraceDB.setInsuAccNo(aInsuAccNo);
			 * tLCInsureAccTraceDB.setPayPlanCode(aLCInsureAccClassSchema.
			 * getPayPlanCode());
			 * //tLCInsureAccTraceDB.setOtherNo(aLCInsureAccClassSchema.getOtherNo());
			 * tLCInsureAccTraceDB.setAccAscription(aLCInsureAccClassSchema.
			 * getAccAscription()); tLCInsureAccTraceSet =
			 * tLCInsureAccTraceDB.executeQuery(tSql);
			 */

			tLCInsureAccTraceSet = tLCInsureAccTraceDB.executeQuery(sqlbv28);

			for (int i = 1; i <= tLCInsureAccTraceSet.size(); i++) {

				tmpLCInsuAccTraceDB = new LCInsureAccTraceDB();
				tmpLCInsuAccTraceDB.setSchema(tLCInsureAccTraceSet.get(i));

				// 如果LCInsureAccTrace表中的moneyType字段的值为PX或者SX,则不作结息;
				tMoneyType = tmpLCInsuAccTraceDB.getMoneyType();
				if (tMoneyType.equals("PX") || tMoneyType.equals("SX")) {
					continue;
				}

				// 交费日期
				payDate = tmpLCInsuAccTraceDB.getPayDate();

				// 交费生效时间
				tMakeTime = tmpLCInsuAccTraceDB.getMakeTime();

				// 现在比较lcinsureaccclass表的baladate和lcinsuretrace表的paydate的值的大小
				/*
				 * //这个限制在前面的查询里面一致限定了 int tIntv = PubFun.calInterval(tBalaDate,
				 * payDate, aIntvType); if (tIntv >= 0) {
				 * 
				 * //处理轨迹表中paydate>=帐户分类表的结息日期BalaDate if (tIntv == 0 &&
				 * tBalaTime.compareTo(tMakeTime) >= 0) { continue; }
				 */
				tempMoney = tmpLCInsuAccTraceDB.getMoney();
				tInsureAccTraceMoneySum += tempMoney;

				// 然后对轨迹表的这笔钱tempMoney作结息
				// jixf add 20050802 对于PayDate>aBalaDate的情况，tInterval有问题，反之没有问题
				logger.debug("------" + payDate);
				logger.debug("------" + aBalaDate);
				logger.debug("-%%%%%%%%%--===="
						+ payDate.compareTo(aBalaDate));
				if (payDate.compareTo(aBalaDate) > 0) {
					logger.debug("33333");
					tInterval = PubFun.calInterval(aBalaDate, payDate,
							aIntvType);
					tInterval = 0 - tInterval;
				} else {
					tInterval = PubFun.calInterval(payDate, aBalaDate,
							aIntvType);
				}
				/*
				 * if (tInterval > 0) { aAccClassInterest += tempMoney *
				 * getIntvRate(tInterval, tCalAccClassRate, "C"); } else {
				 * 
				 * //tInterval = 0; //可以往回计算 aAccClassInterest += tempMoney *
				 * getIntvRate(tInterval, tCalAccClassRate, "C"); }
				 */
				// jixf chg 20060722不区分时间间隔，可以往回反结息
				aAccClassInterest += tempMoney
						* getIntvRate(tInterval, tCalAccClassRate, "C");
				logger.debug("=======" + tInterval);
				logger.debug("=======" + tInsuAccClassBala);
				logger.debug("======="
						+ getIntvRate(tInterval, tCalAccClassRate, "C"));
				aAccClassInterest = Arith.round(aAccClassInterest, 2);

				// }
			}

			// 将帐户分类表的另一部分余额结息
			tInterval = PubFun.calInterval(tBalaDate, aBalaDate, aIntvType);
			if (tInterval > 0) {
				aAccClassInterest += tInsuAccClassBala
						* getIntvRate(tInterval, tCalAccClassRate, "C");
			}
			logger.debug("=======" + tInterval);
			logger.debug("=======" + tInsuAccClassBala);
			logger.debug("======="
					+ getIntvRate(tInterval, tCalAccClassRate, "C"));
			aAccClassInterest = Arith.round(aAccClassInterest, 2);
			break;

		// 活期利率单利算法
		case 3:

			// 筛选出paydate>帐户分类表的结息日期BalaDate
			// 帐户型险种，对于分红险，trace表的payno字段放置class表的otherno字段的值；
			// tSql = "select AccKind from LMRiskInsuAcc where " +
			// "InsuAccNo = '" + aInsuAccNo + "'";
			// try{
			// tSSRS = tExeSql.execSQL(tSql);
			// } catch (Exception ex){
			// CError.buildErr(this, ex.toString());
			// returnNull(aAccClassRet);
			// return aAccClassRet;
			// }
			// tAccKind = tSSRS.GetText(1, 1);

			tAccKind = tLMRiskInsuAccSchema.getAccKind();

			// 分红险特殊处理
			tOtherNo = aLCInsureAccClassSchema.getOtherNo();
			if (!tAccKind.equals("") && tAccKind.equals("3")) {
				if (tOtherNo.equals("")) {
					CError.buildErr(this, "保险帐户分类表数据不完整！");
					returnNull(aAccClassRet);
					return aAccClassRet;
				}
				// tLCInsureAccTraceDB.setPayNo(tOtherNo);
			}
			tSql = "select * from LCInsureAccTrace where polno='"
					+ "?mm1?" + "' and InsuAccNo='"
					+ "?mm2?" + "' and PayPlanCode='"
					+ "?mm3?"
					+ "' and AccAscription='"
					+ "?mm4?"
					+ "' and (PayDate>'" + "?mm5?" + "' or (PayDate='"
					+ "?mm6?" + "' and MakeTime>'" + "?mm7?"
					+ "')) and PayDate<='" + "?mm8?" + "'";
			SQLwithBindVariables sqlbv29=new SQLwithBindVariables();
		    sqlbv29.sql(tSql);
		    sqlbv29.put("mm1",aLCInsureAccClassSchema.getPolNo());
		    sqlbv29.put("mm2",aInsuAccNo);
		    sqlbv29.put("mm3",aLCInsureAccClassSchema.getPayPlanCode());
		    sqlbv29.put("mm4",aLCInsureAccClassSchema.getAccAscription());
		    sqlbv29.put("mm5",tBalaDate);
		    sqlbv29.put("mm6",tBalaDate);
		    sqlbv29.put("mm7",tBalaTime);
		    sqlbv29.put("mm8", aBalaDate);
			logger.debug("------" + tSql);

			tLCInsureAccTraceSet = tLCInsureAccTraceDB.executeQuery(sqlbv29);

			// tLCInsureAccTraceDB.setPolNo(aLCInsureAccClassSchema.getPolNo());
			// tLCInsureAccTraceDB.setInsuAccNo(aInsuAccNo);
			// tLCInsureAccTraceDB.setPayPlanCode(aLCInsureAccClassSchema.
			// getPayPlanCode());
			// tLCInsureAccTraceDB.setAccAscription(aLCInsureAccClassSchema.
			// getAccAscription());
			// tLCInsureAccTraceSet = tLCInsureAccTraceDB.query();
			for (int i = 1; i <= tLCInsureAccTraceSet.size(); i++) {
				tmpLCInsuAccTraceDB = new LCInsureAccTraceDB();
				tmpLCInsuAccTraceDB.setSchema(tLCInsureAccTraceSet.get(i));

				// 如果LCInsureAccTrace表中的moneyType字段的值为PX或者SX,则不作结息;
				tMoneyType = tmpLCInsuAccTraceDB.getMoneyType();
				if (tMoneyType.equals("PX") || tMoneyType.equals("SX")) {
					continue;
				}

				// 记录交费日期
				payDate = tmpLCInsuAccTraceDB.getPayDate();

				// 记录交费生效时间
				tMakeTime = tmpLCInsuAccTraceDB.getMakeTime();

				// 现在比较lcinsureaccclass表的baladate和lcinsuretrace表的paydate的值的大小
				int tIntv = PubFun.calInterval(tBalaDate, payDate, aIntvType);
				if (tIntv >= 0) {

					// 处理轨迹表中paydate>=帐户分类表的结息日期BalaDate
					if (tIntv == 0 && tBalaTime.compareTo(tMakeTime) >= 0) {
						continue;
					}

					// 临时存放LCInsuAccTraceDB表中的帐户缴费
					tempMoney = tmpLCInsuAccTraceDB.getMoney();
					tInsureAccTraceMoneySum += tempMoney;

					// 对轨迹表的这笔钱tempMoney作结息
//					String[] ResultRate2 = getMultiAccRateNew(aInsuAccNo,
//							tLMRiskInsuAccSchema, payDate, aBalaDate,
//							aRateType, "S", aIntvType, Period, tType, Depst);
//
//					// 记录ResultRate2[]的有效数组长度
//					tCount = Integer.parseInt(ResultRate2[0]);
//					for (int m = 1; m < tCount + 1; m++) {
//						if (ResultRate2[m] == null) {
//							ResultRate2[m] = "0";
//						}
//						tCalAccClassRate = Double.parseDouble(ResultRate2[m]);
//						double tSubInterest = tempMoney * tCalAccClassRate;
//						tSubInterest = Arith.round(tSubInterest, 2);
//						aAccClassInterest += tSubInterest;
//					}
					//modify by jiaqiangli 2008-11-11 修改成调用MS算法 单利计息
					//贷存款标志 (D: 存款 L: 贷款)
//					if (Depst != null && Depst.equals("L")) {
//						tCalAccClassRate = calMultiRateMS(payDate, aBalaDate,"000000","00","L","S","Y");
//					}
//					else if (Depst != null && Depst.equals("D")) {
//						tCalAccClassRate = calMultiRateMS(payDate, aBalaDate,"000000","00","R","S","Y");
//					}
//					//注意清0处理
//					else {
//						tCalAccClassRate = 0.00;
//					}
					tCalAccClassRate = calMultiRateForAccMS(tLMRiskInsuAccSchema,payDate,aBalaDate,aLCInsureAccClassSchema.getCurrency());
					double tSubInterest = PubFun.round(tempMoney * tCalAccClassRate,2);
					aAccClassInterest += tSubInterest;
					//modify by jiaqiangli 2008-11-11 修改成调用MS算法 单利计息
				}
			}

			// 将帐户分类表的另一部分余额结息
//			ResultRate = getMultiAccRateNew(aInsuAccNo, tLMRiskInsuAccSchema,
//					tBalaDate, aBalaDate, aRateType, "S", aIntvType, Period,
//					tType, Depst);
//			tCount = Integer.parseInt(ResultRate[0]);
//			for (int m = 1; m < tCount + 1; m++) {
//				if (ResultRate[m] == null) {
//					ResultRate[m] = "0";
//				}
//				tCalAccClassRate = Double.parseDouble(ResultRate[m]);
//				aAccClassInterest += tInsuAccClassBala * tCalAccClassRate;
//				aAccClassInterest = Arith.round(aAccClassInterest, 2);
//			}
			//modify by jiaqiangli 2008-11-11 修改成调用MS算法 单利计息
			//贷存款标志 (D: 存款 L: 贷款)
//			if (Depst != null && Depst.equals("L")) {
//				tCalAccClassRate = calMultiRateMS(tBalaDate, aBalaDate,"000000","00","L","S","Y");
//			}
//			else if (Depst != null && Depst.equals("D")) {
//				tCalAccClassRate = calMultiRateMS(tBalaDate, aBalaDate,"000000","00","R","S","Y");
//			}
//			//注意清0处理
//			else {
//				tCalAccClassRate = 0.00;
//			}
			tCalAccClassRate = calMultiRateForAccMS(tLMRiskInsuAccSchema,tBalaDate,aBalaDate,aLCInsureAccClassSchema.getCurrency());
			aAccClassInterest += tInsuAccClassBala * tCalAccClassRate;
			aAccClassInterest = PubFun.round(aAccClassInterest, 2);
			//modify by jiaqiangli 2008-11-11 修改成调用MS算法 单利计息
			break;

		// 活期利率复利算法
		case 4:
			logger.debug("=====复利结息开始=====\n");
			tSql = "select * from LCInsureAccTrace where PolNo = '"
					+ "?g1?"
					+ "' and InsuAccNo = '" + "?g2?"
					+ "' and PayPlanCode = '"
					+ "?g3?"
					+ "' and AccAscription = '"
					+ "?g4?"
					+ "' and PayDate <= '" + "?g5?" + "'";
			SQLwithBindVariables sqlbv30=new SQLwithBindVariables();
		    sqlbv30.sql(tSql);
		    sqlbv30.put("g1",aLCInsureAccClassSchema.getPolNo());
		    sqlbv30.put("g2",aInsuAccNo);
		    sqlbv30.put("g3",aLCInsureAccClassSchema.getPayPlanCode());
		    sqlbv30.put("g4",aLCInsureAccClassSchema.getAccAscription());
		    sqlbv30.put("g5", aBalaDate);
			// 帐户型险种，对于分红险，trace表的payno字段放置class表的otherno字段的值；
			// tSql = "select BonusFlag from LMRiskApp a where exists " +
			// "(select * from LCPol where PolNo = '" +
			// aLCInsureAccClassSchema.getPolNo() +
			// "' and RiskCode = a.RiskCode)";
			// tSql = "select AccKind from LMRiskInsuAcc where " +
			// "InsuAccNo = '" + aInsuAccNo + "'";
			// try{
			// tSSRS = tExeSql.execSQL(tSql);
			// } catch (Exception ex){
			// CError.buildErr(this, ex.toString());
			// returnNull(aAccClassRet);
			// return aAccClassRet;
			// }
			// tAccKind = tSSRS.GetText(1, 1);
			tAccKind = tLMRiskInsuAccSchema.getAccKind();

			// 分红险特殊处理
			tOtherNo = aLCInsureAccClassSchema.getOtherNo();
			if (!tAccKind.equals("") && tAccKind.equals("3")) {
				if (tOtherNo.equals("")) {
					CError.buildErr(this, "保险帐户分类表数据不完整！");
					returnNull(aAccClassRet);
					return aAccClassRet;
				}
				tSql = "select * from LCInsureAccTrace where PolNo = '?PolNo?' and InsuAccNo = '?aInsuAccNo?' and PayPlanCode = '?PayPlanCode?' and PayNo = '?PayNo?' and AccAscription = '?AccAscription?' and PayDate <= '?aBalaDate?'";
				sqlbv30=new SQLwithBindVariables();
				sqlbv30.sql(tSql);
				sqlbv30.put("PolNo", aLCInsureAccClassSchema.getPolNo());
				sqlbv30.put("PayPlanCode", aLCInsureAccClassSchema.getPayPlanCode());
				sqlbv30.put("aInsuAccNo", aInsuAccNo);
				sqlbv30.put("PayNo", aLCInsureAccClassSchema.getOtherNo());
				sqlbv30.put("AccAscription", aLCInsureAccClassSchema.getAccAscription());
				sqlbv30.put("aBalaDate", aBalaDate);
			}
			logger.debug("tSql: " + tSql);
			try {
				tLCInsureAccTraceSet = tLCInsureAccTraceDB.executeQuery(sqlbv30);
			} catch (Exception ex) {
				CError.buildErr(this, ex.toString());
				returnNull(aAccClassRet);
				return aAccClassRet;
			}
			for (int i = 1; i <= tLCInsureAccTraceSet.size(); i++) {

				tmpLCInsuAccTraceDB = new LCInsureAccTraceDB();
				tmpLCInsuAccTraceDB.setSchema(tLCInsureAccTraceSet.get(i));

				// 如果LCInsureAccTrace表中的moneyType字段的值为PX或者SX,则不作结息;
				tMoneyType = tmpLCInsuAccTraceDB.getMoneyType();
				if (tMoneyType.equals("PX") || tMoneyType.equals("SX")) {
					continue;
				}
				payDate = tmpLCInsuAccTraceDB.getPayDate();
				if (payDate == null || payDate.equals("")) {
					payDate = tmpLCInsuAccTraceDB.getMakeDate();
					if (payDate == null || payDate.equals("")) {
						CError.buildErr(this, "缴费记录的缴费日期为空！");
						returnNull(aAccClassRet);
						return aAccClassRet;
					}
				}

				// 交费生效时间
				tMakeTime = tmpLCInsuAccTraceDB.getMakeTime();

				// ==20060619===testing start===============
				logger.debug("第" + i + "条记录！");
				logger.debug("缴费日期：" + payDate);
				logger.debug("缴费时间：" + tMakeTime);
				logger.debug("缴费金额：" + tmpLCInsuAccTraceDB.getMoney());
				// ==20060619==testind end===================

				// 现在比较lcinsureaccclass表的baladate和lcinsuretrace表的paydate的值的大小
				int tIntv = PubFun.calInterval(tBalaDate, payDate, aIntvType);
				if (tIntv >= 0) {

					// 处理轨迹表中paydate>=帐户分类表的结息日期BalaDate
					if (tIntv == 0 && tBalaTime.compareTo(tMakeTime) >= 0) {
						continue;
					}

					// 临时存放帐户一笔缴费金额
					tempMoney = tmpLCInsuAccTraceDB.getMoney();
					tInsureAccTraceMoneySum += tempMoney;

					// 然后对轨迹表的这笔钱tempMoney作结息
//					String[] ResultRate2 = getMultiAccRateNew(aInsuAccNo,
//							tLMRiskInsuAccSchema, payDate, aBalaDate,
//							aRateType, "C", aIntvType, Period, tType, Depst);
//					tCount = Integer.parseInt(ResultRate2[0]);
//
//					// ====20060619==testing start=============
//					logger.debug("查到" + tCount + "条利率值");
//					// ===20060619==testing end================
//
//					for (int m = 1; m < tCount + 1; m++) {
//						if (ResultRate2[m] == null) {
//							ResultRate2[m] = "0";
//						}
//						tCalAccClassRate = Double.parseDouble(ResultRate2[m]);
//
//						// ===20060619==testing start===============
//						logger.debug("Rate = " + tCalAccClassRate);
//						// ===2060619===testing end=================
//
//						double tSubInterest = tempMoney * tCalAccClassRate;
//						tSubInterest = Arith.round(tSubInterest, 2);
//
//						// ===20061018===testing======================
//						logger.debug("利息： " + tSubInterest);
//						// ===20061018===testing end==================
//
//						aAccClassInterest += tSubInterest;
//					}
					//modify by jiaqiangli 2008-11-11 修改成调用MS算法 复利计息
					//贷存款标志 (D: 存款 L: 贷款)
//					if (Depst != null && Depst.equals("L")) {
//						tCalAccClassRate = calMultiRateMS(payDate, aBalaDate,"000000","00","L","C","Y");
//					}
//					else if (Depst != null && Depst.equals("D")) {
//						tCalAccClassRate = calMultiRateMS(payDate, aBalaDate,"000000","00","R","C","Y");
//					}
//					//注意清0处理
//					else {
//						tCalAccClassRate = 0.00;
//					}
					tCalAccClassRate = calMultiRateForAccMS(tLMRiskInsuAccSchema,payDate,aBalaDate,aLCInsureAccClassSchema.getCurrency());
					double tSubInterest = PubFun.round(tempMoney * tCalAccClassRate,2);
					aAccClassInterest += tSubInterest;
					//modify by jiaqiangli 2008-11-11 修改成调用MS算法 复利计息
				}
			}

			// 将帐户分类表的另一部分余额结息
			logger.debug("=====将帐户分类表的另一部分余额结息=====\n");
//			ResultRate = getMultiAccRateNew(aInsuAccNo, tLMRiskInsuAccSchema,
//					tBalaDate, aBalaDate, aRateType, "C", aIntvType, Period,
//					tType, Depst);
//			tCount = Integer.parseInt(ResultRate[0]);
//			for (int m = 1; m < tCount + 1; m++) {
//				if (ResultRate[m] == null) {
//					ResultRate[m] = "0";
//				}
//				tCalAccClassRate = Double.parseDouble(ResultRate[m]);
//				aAccClassInterest += tInsuAccClassBala * tCalAccClassRate;
//				aAccClassInterest = Arith.round(aAccClassInterest, 2);
//				// ===20060703===testing start========================
//				logger.debug("计算后的利率：" + tCalAccClassRate);
//				// ===20060703==testing end===========================
//
//			}
			//modify by jiaqiangli 2008-11-11 修改成调用MS算法 复利计息
			//贷存款标志 (D: 存款 L: 贷款)
//			if (Depst != null && Depst.equals("L")) {
//				tCalAccClassRate = calMultiRateMS(tBalaDate, aBalaDate,"000000","00","L","C","Y");
//			}
//			else if (Depst != null && Depst.equals("D")) {
//				tCalAccClassRate = calMultiRateMS(tBalaDate, aBalaDate,"000000","00","R","C","Y");
//			}
//			//注意清0处理
//			else {
//				tCalAccClassRate = 0.00;
//			}
			tCalAccClassRate = calMultiRateForAccMS(tLMRiskInsuAccSchema,tBalaDate,aBalaDate,aLCInsureAccClassSchema.getCurrency());
			if (tCalAccClassRate == -1) {
				tCalAccClassRate = 0.00;
			}
			aAccClassInterest += tInsuAccClassBala * tCalAccClassRate;
			aAccClassInterest = PubFun.round(aAccClassInterest, 2);
			//modify by jiaqiangli 2008-11-11 修改成调用MS算法 复利计息
			break;
		}
		if (Integer.parseInt(tLMRiskInsuAccSchema.getAccComputeFlag()) != 0) {
			aAccClassSumPay = tInsuAccClassBala + tInsureAccTraceMoneySum
					+ aAccClassInterest;
		} else {
			aAccClassSumPay = tInsureAccTraceMoneySum;
		}
		// ===20061018===testing start==================
		logger.debug("帐户金额： " + tInsuAccClassBala);
		logger.debug("Trace表的缴费和： " + tInsureAccTraceMoneySum);
		logger.debug("Trace表的缴费利息和： " + aAccClassInterest);
		logger.debug("帐户实际余额： " + aAccClassSumPay);
		// ===20061018===testing end====================

		// 准备返回的数据包
		aAccClassRet.setNameAndValue("aAccClassInterest", aAccClassInterest);
		aAccClassRet.setNameAndValue("aAccClassSumPay", aAccClassSumPay);

		return aAccClassRet;
	}
	
	
    
    //-----------------------------MS分段结息 end----------------------------------
    
    
//tongmeng 2009-01-04 add
//EBA系统平移

    /**
     * 按照新的帐户结息的逻辑进行处理
     * 计算帐户分类表的利息
     * @param aBalaDate 本次结息日期
     * @param aRateType 原始利率类型，比如年利率，月利率等
     * @param aIntvType 目标利率类型，比如年利率，月利率等
     * @param Period 利率期间
     * @param tType 利率类型（C:活期 F:定期）
     * @param Depst 贷存款标志 (D: 存款 L: 贷款)
     * 创建人:
     * 创建日期：2006-02-16
     * @return double　返回的是帐户分类表和相应的记价履历表的利息和以及总余额
     * 修改人：
     * 修改日期：2006-02-24
     * 修改内容：传输参数修改
     * */
    public TransferData getAccClassInterestNew(LCInsureAccClassSchema
                                               aLCInsureAccClassSchema,
                                               String aBalaDate,
                                               String aRateType,
                                               String aIntvType,
                                               int Period,
                                               String tType,
                                               String Depst) {

        logger.debug("=====This is getAccClassInterestNew!=====\n");

        //记录帐户分类表的利息值
        double aAccClassInterest = 0.0;

        //记录帐户分类表的本息和
        double aAccClassSumPay = 0.0;

        ExeSQL tExeSql = new ExeSQL();
        SSRS tSSRS = new SSRS();

        String tsql = "select poltypeflag from lcpol where polno ='" +
                      "?g11?" + "' ";
        SQLwithBindVariables sqlbv31=new SQLwithBindVariables();
	    sqlbv31.sql(tsql);
	    sqlbv31.put("g11",aLCInsureAccClassSchema.getPolNo());
        ExeSQL tExeSQL = new ExeSQL();
        String tpoltypeflag = tExeSQL.getOneValue(sqlbv31);

        if (tpoltypeflag == "2" || tpoltypeflag.equals("2")) {
            logger.debug("=====poltype=" + tpoltypeflag +
                               "=====对公共账户重新结息！");
            aLCInsureAccClassSchema.setBalaDate("1900-1-1");
            aLCInsureAccClassSchema.setBalaTime("00:00:00");
            aLCInsureAccClassSchema.setInsuAccBala("0");
        }

        String accSql = "select count(*) from lmriskapp where riskcode ='" +
                        "?g12?" +
                        "'"; //riskflagacc ='D' 代表139，151，121，122，108险种，D类险
        SQLwithBindVariables sqlbv32=new SQLwithBindVariables();
	    sqlbv32.sql(accSql);
	    sqlbv32.put("g12",aLCInsureAccClassSchema.getRiskCode());
        String accCount = tExeSql.getOneValue(sqlbv32);

        if (!accCount.equals("0")) {

            logger.debug("=====对" + aLCInsureAccClassSchema.getRiskCode() + "账户重新结息！=====");
            aLCInsureAccClassSchema.setBalaDate("1900-1-1");
            aLCInsureAccClassSchema.setBalaTime("00:00:00");
            aLCInsureAccClassSchema.setInsuAccBala("0");
        }
        //记录返回值利息和本息
        TransferData aAccClassRet = new TransferData();

        //检验数据有效性
        if (!verifyNotNull("当前结息日期", aBalaDate)) {
            returnNull(aAccClassRet);
            return aAccClassRet;
        }
        if (!verifyNotNull("原始利率类型", aRateType)) {
            returnNull(aAccClassRet);
            return aAccClassRet;
        }
        if (!verifyNotNull("目标利率类型", aIntvType)) {
            returnNull(aAccClassRet);
            return aAccClassRet;
        }

        //记录结息间隔
        int tInterval = 0;

        //记录查询LCInsureAccClassTrace表返回有效记录的个数
        int tCount = 0;

        //记录原始利率值
        double tAccClassRate = 0.00;

        //记录目标利率值
        double tCalAccClassRate = 0.00;

        //记录保险帐户现金余额
        double tInsuAccClassBala = aLCInsureAccClassSchema.getInsuAccBala();

        //得到险种保险帐户描述表(lmRiskInsuAcc)中帐户利率
        //       LMRiskInsuAccDB tLMRiskInsuAccDB = new LMRiskInsuAccDB();

        //记录保险帐户号码
        String aInsuAccNo = aLCInsureAccClassSchema.getInsuAccNo();
        if (!verifyNotNull("保险帐户号码", aInsuAccNo)) {
            returnNull(aAccClassRet);
            return aAccClassRet;
        }

        //记录帐户分类表上次结息日期
        String tBalaDate = aLCInsureAccClassSchema.getBalaDate();

        //记录帐户分类表上次结息时间
        String tBalaTime = aLCInsureAccClassSchema.getBalaTime();

        //上次结息日期为空或者不存在则取入机日期
        if (tBalaDate == null || tBalaDate.equals("")) {
            tBalaDate = aLCInsureAccClassSchema.getMakeDate();
        }

        //上次结息时间如果为空或者不存在则取入机时间
        if (tBalaTime == null || tBalaTime.equals("")) {
            tBalaTime = aLCInsureAccClassSchema.getMakeTime();
        }
        LCInsureAccTraceDB tLCInsureAccTraceDB = new LCInsureAccTraceDB();
        LCInsureAccTraceSet tLCInsureAccTraceSet = new LCInsureAccTraceSet();

        //得到帐户相关信息
        LMRiskInsuAccSchema tLMRiskInsuAccSchema = new LMRiskInsuAccSchema();
        tLMRiskInsuAccSchema = mCRI.findRiskInsuAccByInsuAccNo(aInsuAccNo);
        if (tLMRiskInsuAccSchema == null) {
            CError.buildErr(this, "查询险种保险帐户信息失败！");
            returnNull(aAccClassRet);
            return aAccClassRet;
        }

        //该缴费帐户的缴退费总和
        double tInsureAccTraceMoneySum = 0.0;

        //查询利率信息
        String[] ResultRate = null;
        if (tLMRiskInsuAccSchema.getInsuAccNo() == null ||
            tLMRiskInsuAccSchema.getInsuAccNo().equals("")) {

            CError.buildErr(this, "查询险种保险帐户信息失败！");
            aAccClassRet.setNameAndValue("tAccClassInterest", aAccClassInterest);
            aAccClassRet.setNameAndValue("tAccClassSumPay", aAccClassSumPay);
            return aAccClassRet;
        }

        String tSql;
        LCInsureAccTraceDB tmpLCInsuAccTraceDB = null;
        String tMoneyType = "";
        String payDate = "";
        String tMakeTime = "";
        double tempMoney = 0.00;

        //-----testing start----20060216-----------------------
        logger.debug("************开始结息****************");
        logger.debug("保险帐户现金余额: " + tInsuAccClassBala);
        logger.debug("本次结息日期: " + aBalaDate);
        logger.debug("上次结息日期: " + tBalaDate);
        logger.debug("上次结息时间: " + tBalaTime);
        //----------testing end--------------------------------

        switch (Integer.parseInt(tLMRiskInsuAccSchema.getAccComputeFlag())) {

        //不计息
        case 0:

            tSql = "select * from LCInsureAccTrace where PolNo = '" +
                  "?g13?" +
                   "' and InsuAccNo = '" +
                   "?g14?" +
                   "' and PayPlanCode = '" +
                   "?g15?" +
                   "' and AccAscription = '" +
                   "?g16?" +
                   "' and PayDate <= '" + "?g17?" + "'";
            SQLwithBindVariables sqlbv33=new SQLwithBindVariables();
    	    sqlbv33.sql(tSql);
    	    sqlbv33.put("g13",aLCInsureAccClassSchema.getPolNo());
    	    sqlbv33.put("g14",aInsuAccNo);
    	    sqlbv33.put("g15",aLCInsureAccClassSchema.getPayPlanCode());
    	    sqlbv33.put("g16",aLCInsureAccClassSchema.getAccAscription());
    	    sqlbv33.put("g17",aBalaDate);

            logger.debug("tSql: " + tSql);
            try {
                tLCInsureAccTraceSet = tLCInsureAccTraceDB.executeQuery(sqlbv33);
            } catch (Exception ex) {
                CError.buildErr(this, ex.toString());
                returnNull(aAccClassRet);
                return aAccClassRet;
            }
            for (int i = 1; i <= tLCInsureAccTraceSet.size(); i++) {

                tmpLCInsuAccTraceDB = new LCInsureAccTraceDB();
                tmpLCInsuAccTraceDB.setSchema(tLCInsureAccTraceSet.get(i));

                //如果LCInsureAccTrace表中的moneyType字段的值为PX或者SX,则不作结息;
                tMoneyType = tmpLCInsuAccTraceDB.getMoneyType();
                if (tMoneyType.equals("PX") || tMoneyType.equals("SX")) {
                    continue;
                }
                payDate = tmpLCInsuAccTraceDB.getPayDate();
                if (payDate == null || payDate.equals("")) {
                    payDate = tmpLCInsuAccTraceDB.getMakeDate();
                    if (payDate == null || payDate.equals("")) {
                        CError.buildErr(this, "缴费记录的缴费日期为空！");
                        returnNull(aAccClassRet);
                        return aAccClassRet;
                    }
                }

                //交费生效时间
                tMakeTime = tmpLCInsuAccTraceDB.getMakeTime();

                //==20060619===testing start===============
                logger.debug("第" + i + "条记录！");
                logger.debug("缴费日期：" + payDate);
                logger.debug("缴费时间：" + tMakeTime);
                logger.debug("缴费金额：" + tmpLCInsuAccTraceDB.getMoney());
                //==20060619==testind end===================

                //临时存放帐户一笔缴费金额
                tempMoney = tmpLCInsuAccTraceDB.getMoney();
                tInsureAccTraceMoneySum += tempMoney;

            }

            break;
            //定期利率单利算法
        case 1:

            //获取帐户固定利率AccRate
            tAccClassRate = tLMRiskInsuAccSchema.getAccRate();

            //将原始固定利率转换为目标固定利率
            tCalAccClassRate = TransAccRate(tAccClassRate, aRateType, "S",
                                            aIntvType);

            //筛选出paydate > 帐户分类表的结息日期BalaDate
            //帐户型险种，对于分红险除公共帐户之外，trace表的payno字段放置class表
            //的otherno字段的值, 每笔缴费在class表中都有一条记录，此称为先进先出型
            //险种，用LMRiskInsuAcc表中的AccKind来区分；
            String tAccKind = tLMRiskInsuAccSchema.getAccKind();

            //先进先出险种特殊处理
            String tOtherNo = aLCInsureAccClassSchema.getOtherNo();
            if (!tAccKind.equals("") && tAccKind.equals("3")) {
                if (tOtherNo.equals("")) {
                    CError.buildErr(this, "保险帐户分类表数据不完整！");
                    returnNull(aAccClassRet);
                    return aAccClassRet;
                }
                tLCInsureAccTraceDB.setPayNo(tOtherNo);
            }
            tLCInsureAccTraceDB.setPolNo(aLCInsureAccClassSchema.getPolNo());
            tLCInsureAccTraceDB.setInsuAccNo(aInsuAccNo);
            tLCInsureAccTraceDB.setPayPlanCode(
                    aLCInsureAccClassSchema.getPayPlanCode());
            tLCInsureAccTraceDB.setAccAscription(
                    aLCInsureAccClassSchema.getAccAscription());
            try {
                tLCInsureAccTraceSet = tLCInsureAccTraceDB.query();
            } catch (Exception ex) {
                CError.buildErr(this, ex.toString());
                returnNull(aAccClassRet);
                return aAccClassRet;
            }

            for (int i = 1; i <= tLCInsureAccTraceSet.size(); i++) {
                tmpLCInsuAccTraceDB = new LCInsureAccTraceDB();
                tmpLCInsuAccTraceDB = (LCInsureAccTraceDB)
                                      tLCInsureAccTraceSet.get(i);

                //如果LCInsureAccTrace表中的moneyType字段的值为PX或者SX,则不作结息;
                tMoneyType = tmpLCInsuAccTraceDB.getMoneyType();
                if (tMoneyType.equals("PX") || tMoneyType.equals("SX")) {
                    continue;
                }

                //记录缴费日期
                payDate = tmpLCInsuAccTraceDB.getPayDate();

                //交费生效时间
                tMakeTime = tmpLCInsuAccTraceDB.getMakeTime();

                //找出上次结息后的缴退费金额
                int tIntv = PubFun.calInterval(tBalaDate, payDate, aIntvType);
                if (tIntv >= 0) {

                    //日期相同则比较时间
                    if (tIntv == 0 && tBalaTime.compareTo(tMakeTime) >= 0) {
                        continue;
                    }

                    //暂存一笔缴费金额
                    tempMoney = tmpLCInsuAccTraceDB.getMoney();
                    tInsureAccTraceMoneySum += tempMoney;

                    //然后对轨迹表的这笔钱tempMoney作结息
                    if (payDate.compareTo(aBalaDate) > 0) {
                        tInterval = PubFun.calInterval(aBalaDate, payDate,
                                aIntvType);
                        tInterval = 0 - tInterval;
                    } else {
                        tInterval = PubFun.calInterval(payDate, aBalaDate,
                                aIntvType);
                    }
                    /*
                                         if (tInterval > 0) {
                        aAccClassInterest += tempMoney *
                                getIntvRate(tInterval, tCalAccClassRate, "S");
                                         } else {
                        tInterval = 0;
                        aAccClassInterest += tempMoney *
                                getIntvRate(tInterval, tCalAccClassRate, "S");
                                         }
                     */
                    //jixf chg 20060722不区分时间间隔，可以往回反结息
                    aAccClassInterest += tempMoney *
                            getIntvRate(tInterval, tCalAccClassRate, "S");
                    aAccClassInterest = Arith.round(aAccClassInterest, 2);
                }
            }

            //将帐户分类表的另一部分余额结息
            tInterval = PubFun.calInterval(tBalaDate, aBalaDate, aIntvType);
            if (tInterval > 0) {
                aAccClassInterest += tInsuAccClassBala *
                        getIntvRate(tInterval, tCalAccClassRate, "S");
            }
            aAccClassInterest = Arith.round(aAccClassInterest, 2);
            break;

            //定期利率复利算法
        case 2:

            //获取固定利率
            tAccClassRate = tLMRiskInsuAccSchema.getAccRate();

            //转换后的利率值
            tCalAccClassRate = TransAccRate(tAccClassRate, aRateType, "C",
                                            aIntvType);

            //这个单子特殊处理，并且不分红，其他项目组可以不用
            //jixf add 20061016
            if (aLCInsureAccClassSchema.getGrpContNo().equals("230501012000")) {
                tAccClassRate = 0.03;
                tCalAccClassRate = TransAccRate(tAccClassRate, aRateType, "S",
                                                aIntvType);
            }

            //筛选出paydate > 帐户分类表的结息日期BalaDate
            //帐户型险种，对于分红险，trace表的payno字段放置class表的otherno字段的值；
//            tSql = "select BonusFlag from LMRiskApp a where exists " +
//                   "(select * from LCPol where PolNo = '" +
//                   aLCInsureAccClassSchema.getPolNo() +
//                   "' and RiskCode = a.RiskCode)";
//            tSql = "select AccKind from LMRiskInsuAcc where " +
//                   "InsuAccNo = '" + aInsuAccNo + "'";
//            try{
//                tSSRS = tExeSql.execSQL(tSql);
//            } catch (Exception ex){
//                CError.buildErr(this, ex.toString());
//                returnNull(aAccClassRet);
//                return aAccClassRet;
//            }
//            tAccKind = tSSRS.GetText(1, 1);
//            ExeSQL ttExeSql = new ExeSQL();
//            SSRS ttSSRS = new SSRS();
            tAccKind = tLMRiskInsuAccSchema.getAccKind();

            //分红险特殊处理
            tOtherNo = aLCInsureAccClassSchema.getOtherNo();
            if (!tAccKind.equals("") && tAccKind.equals("3")) {
                if (tOtherNo.equals("")) {
                    CError.buildErr(this, "保险帐户分类表数据不完整！");
                    returnNull(aAccClassRet);
                    return aAccClassRet;
                }
                //tLCInsureAccTraceDB.setPayNo(tOtherNo);
            }
            tSql = "select * from LCInsureAccTrace where polno='" +
                  "?k1?" +
                   "' and InsuAccNo='" + "?k2?" +
                   "' and PayPlanCode='" +
                   "?k3?" +
                   "' and PayNo='" + "?k4?" +
                   "' and (PayDate>'" + "?k5?" + "' or (PayDate='" +
                   "?k6?" +
                   "' and MakeTime>'" + "?k7?" +
                   "')) and PayDate<='" + "?k8?" + "'";
            SQLwithBindVariables sqlbv34=new SQLwithBindVariables();
    	    sqlbv34.sql(tSql);
    	    sqlbv34.put("k1",aLCInsureAccClassSchema.getPolNo());
    	    sqlbv34.put("k2",aInsuAccNo);
    	    sqlbv34.put("k3",aLCInsureAccClassSchema.getPayPlanCode());
    	    sqlbv34.put("k4",tOtherNo);
    	    sqlbv34.put("k5",tBalaDate);
    	    sqlbv34.put("k6",tBalaDate);
    	    sqlbv34.put("k7",tBalaTime);
    	    sqlbv34.put("k8",aBalaDate);
            logger.debug("------" + tSql);

            /*
             tLCInsureAccTraceDB.setPolNo(aLCInsureAccClassSchema.getPolNo());
                         tLCInsureAccTraceDB.setInsuAccNo(aInsuAccNo);
             tLCInsureAccTraceDB.setPayPlanCode(aLCInsureAccClassSchema.
                                               getPayPlanCode());
                         //tLCInsureAccTraceDB.setOtherNo(aLCInsureAccClassSchema.getOtherNo());
             tLCInsureAccTraceDB.setAccAscription(aLCInsureAccClassSchema.
                                                 getAccAscription());
             tLCInsureAccTraceSet = tLCInsureAccTraceDB.executeQuery(tSql);
             */

            tLCInsureAccTraceSet = tLCInsureAccTraceDB.executeQuery(sqlbv34);

            for (int i = 1; i <= tLCInsureAccTraceSet.size(); i++) {

                tmpLCInsuAccTraceDB = new LCInsureAccTraceDB();
                tmpLCInsuAccTraceDB.setSchema(tLCInsureAccTraceSet.get(i));

                //如果LCInsureAccTrace表中的moneyType字段的值为PX或者SX,则不作结息;
                tMoneyType = tmpLCInsuAccTraceDB.getMoneyType();
                if (tMoneyType.equals("PX") || tMoneyType.equals("SX")) {
                    continue;
                }

                //交费日期
                payDate = tmpLCInsuAccTraceDB.getPayDate();

                //交费生效时间
                tMakeTime = tmpLCInsuAccTraceDB.getMakeTime();

                // 现在比较lcinsureaccclass表的baladate和lcinsuretrace表的paydate的值的大小
                /*
                                 //这个限制在前面的查询里面一致限定了
                 int tIntv = PubFun.calInterval(tBalaDate, payDate, aIntvType);
                                 if (tIntv >= 0) {

                    //处理轨迹表中paydate>=帐户分类表的结息日期BalaDate
                    if (tIntv == 0 && tBalaTime.compareTo(tMakeTime) >= 0) {
                        continue;
                    }
                 */
                tempMoney = tmpLCInsuAccTraceDB.getMoney();
                tInsureAccTraceMoneySum += tempMoney;

                //然后对轨迹表的这笔钱tempMoney作结息
                //jixf add 20050802 对于PayDate>aBalaDate的情况，tInterval有问题，反之没有问题
                logger.debug("------" + payDate);
                logger.debug("------" + aBalaDate);
                logger.debug("-%%%%%%%%%--====" +
                                   payDate.compareTo(aBalaDate));
                if (payDate.compareTo(aBalaDate) > 0) {
                    logger.debug("33333");
                    tInterval = PubFun.calInterval(aBalaDate, payDate,
                            aIntvType);
                    tInterval = 0 - tInterval;
                } else {
                    tInterval = PubFun.calInterval(payDate, aBalaDate,
                            aIntvType);
                }
                /*
                                     if (tInterval > 0) {
                    aAccClassInterest += tempMoney *
                            getIntvRate(tInterval, tCalAccClassRate, "C");
                                     } else {

                    //tInterval = 0;
                    //可以往回计算
                    aAccClassInterest += tempMoney *
                            getIntvRate(tInterval, tCalAccClassRate, "C");
                                     }
                 */
                //jixf chg 20060722不区分时间间隔，可以往回反结息
                aAccClassInterest += tempMoney *
                        getIntvRate(tInterval, tCalAccClassRate, "C");
                logger.debug("=======" + tInterval);
                logger.debug("=======" + tInsuAccClassBala);
                logger.debug("=======" +
                                   getIntvRate(tInterval, tCalAccClassRate, "C"));
                aAccClassInterest = Arith.round(aAccClassInterest, 2);

                //}
            }

            //将帐户分类表的另一部分余额结息
            tInterval = PubFun.calInterval(tBalaDate, aBalaDate, aIntvType);
            if (tInterval > 0) {
                aAccClassInterest += tInsuAccClassBala *
                        getIntvRate(tInterval, tCalAccClassRate, "C");
            }
            logger.debug("=======" + tInterval);
            logger.debug("=======" + tInsuAccClassBala);
            logger.debug("=======" +
                               getIntvRate(tInterval, tCalAccClassRate, "C"));
            aAccClassInterest = Arith.round(aAccClassInterest, 2);
            break;

            //活期利率单利算法
        case 3:

            //筛选出paydate>帐户分类表的结息日期BalaDate
            //帐户型险种，对于分红险，trace表的payno字段放置class表的otherno字段的值；
//            tSql = "select AccKind from LMRiskInsuAcc where " +
//                   "InsuAccNo = '" + aInsuAccNo + "'";
//            try{
//                tSSRS = tExeSql.execSQL(tSql);
//            } catch (Exception ex){
//                CError.buildErr(this, ex.toString());
//                returnNull(aAccClassRet);
//                return aAccClassRet;
//            }
//            tAccKind = tSSRS.GetText(1, 1);

            tAccKind = tLMRiskInsuAccSchema.getAccKind();

            //分红险特殊处理
            tOtherNo = aLCInsureAccClassSchema.getOtherNo();
            if (!tAccKind.equals("") && tAccKind.equals("3")) {
                if (tOtherNo.equals("")) {
                    CError.buildErr(this, "保险帐户分类表数据不完整！");
                    returnNull(aAccClassRet);
                    return aAccClassRet;
                }
                //  tLCInsureAccTraceDB.setPayNo(tOtherNo);
            }
            tSql = "select * from LCInsureAccTrace where polno='" +
                   "?k11?" +
                   "' and InsuAccNo='" + "?k22?" +
                   "' and PayPlanCode='" +
                   "?k33?" +
                   "' and AccAscription='" +
                   "?k44?" +
                   "' and (PayDate>'" + "?k55?" + "' or (PayDate='" +
                   "?k66?" +
                   "' and MakeTime>'" + "?k77?" +
                   "')) and PayDate<='" + "?k88?" + "'";
            SQLwithBindVariables sqlbv35=new SQLwithBindVariables();
    	    sqlbv35.sql(tSql);
    	    sqlbv35.put("k11",aLCInsureAccClassSchema.getPolNo());
    	    sqlbv35.put("k22",aInsuAccNo);
    	    sqlbv35.put("k33",aLCInsureAccClassSchema.getPayPlanCode());
    	    sqlbv35.put("k44",aLCInsureAccClassSchema.getAccAscription());
    	    sqlbv35.put("k55",tBalaDate);
    	    sqlbv35.put("k66",tBalaDate);
    	    sqlbv35.put("k77",tBalaTime);
    	    sqlbv35.put("k88",aBalaDate);
            logger.debug("------" + tSql);

            tLCInsureAccTraceSet = tLCInsureAccTraceDB.executeQuery(sqlbv35);

//            tLCInsureAccTraceDB.setPolNo(aLCInsureAccClassSchema.getPolNo());
//            tLCInsureAccTraceDB.setInsuAccNo(aInsuAccNo);
//            tLCInsureAccTraceDB.setPayPlanCode(aLCInsureAccClassSchema.
//                                               getPayPlanCode());
//            tLCInsureAccTraceDB.setAccAscription(aLCInsureAccClassSchema.
//                                                 getAccAscription());
//            tLCInsureAccTraceSet = tLCInsureAccTraceDB.query();
            for (int i = 1; i <= tLCInsureAccTraceSet.size(); i++) {
                tmpLCInsuAccTraceDB = new LCInsureAccTraceDB();
                tmpLCInsuAccTraceDB.setSchema(tLCInsureAccTraceSet.get(i));

                //如果LCInsureAccTrace表中的moneyType字段的值为PX或者SX,则不作结息;
                tMoneyType = tmpLCInsuAccTraceDB.getMoneyType();
                if (tMoneyType.equals("PX") || tMoneyType.equals("SX")) {
                    continue;
                }

                //记录交费日期
                payDate = tmpLCInsuAccTraceDB.getPayDate();

                //记录交费生效时间
                tMakeTime = tmpLCInsuAccTraceDB.getMakeTime();

                // 现在比较lcinsureaccclass表的baladate和lcinsuretrace表的paydate的值的大小
                int tIntv = PubFun.calInterval(tBalaDate, payDate, aIntvType);
                if (tIntv >= 0) {

                    //处理轨迹表中paydate>=帐户分类表的结息日期BalaDate
                    if (tIntv == 0 && tBalaTime.compareTo(tMakeTime) >= 0) {
                        continue;
                    }

                    //临时存放LCInsuAccTraceDB表中的帐户缴费
                    tempMoney = tmpLCInsuAccTraceDB.getMoney();
                    tInsureAccTraceMoneySum += tempMoney;

                    //对轨迹表的这笔钱tempMoney作结息
                    String[] ResultRate2 = getMultiAccRateNew(aInsuAccNo,
                            tLMRiskInsuAccSchema, payDate, aBalaDate,
                            aRateType, "S", aIntvType, Period, tType, Depst);

                    //记录ResultRate2[]的有效数组长度
                    tCount = Integer.parseInt(ResultRate2[0]);
                    for (int m = 1; m < tCount + 1; m++) {
                        if (ResultRate2[m] == null) {
                            ResultRate2[m] = "0";
                        }
                        tCalAccClassRate = Double.parseDouble(ResultRate2[m]);
                        double tSubInterest = tempMoney * tCalAccClassRate;
                        tSubInterest = Arith.round(tSubInterest, 2);
                        aAccClassInterest += tSubInterest;
                    }
                }
            }

            //将帐户分类表的另一部分余额结息
            ResultRate = getMultiAccRateNew(aInsuAccNo, tLMRiskInsuAccSchema,
                                            tBalaDate, aBalaDate, aRateType,
                                            "S",
                                            aIntvType, Period, tType, Depst);
            tCount = Integer.parseInt(ResultRate[0]);
            for (int m = 1; m < tCount + 1; m++) {
                if (ResultRate[m] == null) {
                    ResultRate[m] = "0";
                }
                tCalAccClassRate = Double.parseDouble(ResultRate[m]);
                aAccClassInterest += tInsuAccClassBala * tCalAccClassRate;
                aAccClassInterest = Arith.round(aAccClassInterest, 2);
            }
            break;

            //活期利率复利算法
        case 4:
            logger.debug("=====复利结息开始=====\n");
            tSql = "select * from LCInsureAccTrace where PolNo = '" +
                   "?l1?" +
                   "' and InsuAccNo = '" +
                   "?l2?" +
                   "' and PayPlanCode = '" +
                  "?l3?" +
                   "' and AccAscription = '" +
                  "?l4?" +
                   "' and PayDate <= '" + "?l5?" + "'";
            SQLwithBindVariables sqlbv36=new SQLwithBindVariables();
    	    sqlbv36.sql(tSql);
    	    sqlbv36.put("l1",aLCInsureAccClassSchema.getPolNo());
    	    sqlbv36.put("l2",aInsuAccNo);
    	    sqlbv36.put("l3",aLCInsureAccClassSchema.getPayPlanCode());
    	    sqlbv36.put("l4",aLCInsureAccClassSchema.getAccAscription());
    	    sqlbv36.put("l5",aBalaDate);
            //帐户型险种，对于分红险，trace表的payno字段放置class表的otherno字段的值；
//            tSql = "select BonusFlag from LMRiskApp a where exists " +
//                   "(select * from LCPol where PolNo = '" +
//                   aLCInsureAccClassSchema.getPolNo() +
//                   "' and RiskCode = a.RiskCode)";
//            tSql = "select AccKind from LMRiskInsuAcc where " +
//                   "InsuAccNo = '" + aInsuAccNo + "'";
//            try{
//                tSSRS = tExeSql.execSQL(tSql);
//            } catch (Exception ex){
//                CError.buildErr(this, ex.toString());
//                returnNull(aAccClassRet);
//                return aAccClassRet;
//            }
//            tAccKind = tSSRS.GetText(1, 1);
            tAccKind = tLMRiskInsuAccSchema.getAccKind();

            //分红险特殊处理
            tOtherNo = aLCInsureAccClassSchema.getOtherNo();
            if (!tAccKind.equals("") && tAccKind.equals("3")) {
                if (tOtherNo.equals("")) {
                    CError.buildErr(this, "保险帐户分类表数据不完整！");
                    returnNull(aAccClassRet);
                    return aAccClassRet;
                }
                tSql = "select * from LCInsureAccTrace where PolNo = '" +
                       "?w1?" +
                       "' and InsuAccNo = '" +
                       "?w2?" +
                       "' and PayPlanCode = '" +
                       "?w3?" +
                       "' and PayNo = '" +
                       "?w4?" +
                       "' and AccAscription = '" +
                       "?w5?" +
                       "' and PayDate <= '" + "?w6?" + "'";
                sqlbv36=new SQLwithBindVariables();
        	    sqlbv36.sql(tSql);
        	    sqlbv36.put("w1",aLCInsureAccClassSchema.getPolNo());
        	    sqlbv36.put("w2",aInsuAccNo);
        	    sqlbv36.put("w3",aLCInsureAccClassSchema.getPayPlanCode());
        	    sqlbv36.put("w4",aLCInsureAccClassSchema.getAccAscription());
        	    sqlbv36.put("w5",aLCInsureAccClassSchema.getOtherNo());
        	    sqlbv36.put("w6",aBalaDate);
            }
            logger.debug("tSql: " + tSql);
            try {
                tLCInsureAccTraceSet = tLCInsureAccTraceDB.executeQuery(sqlbv36);
            } catch (Exception ex) {
                CError.buildErr(this, ex.toString());
                returnNull(aAccClassRet);
                return aAccClassRet;
            }
            for (int i = 1; i <= tLCInsureAccTraceSet.size(); i++) {

                tmpLCInsuAccTraceDB = new LCInsureAccTraceDB();
                tmpLCInsuAccTraceDB.setSchema(tLCInsureAccTraceSet.get(i));

                //如果LCInsureAccTrace表中的moneyType字段的值为PX或者SX,则不作结息;
                tMoneyType = tmpLCInsuAccTraceDB.getMoneyType();
                if (tMoneyType.equals("PX") || tMoneyType.equals("SX")) {
                    continue;
                }
                payDate = tmpLCInsuAccTraceDB.getPayDate();
                if (payDate == null || payDate.equals("")) {
                    payDate = tmpLCInsuAccTraceDB.getMakeDate();
                    if (payDate == null || payDate.equals("")) {
                        CError.buildErr(this, "缴费记录的缴费日期为空！");
                        returnNull(aAccClassRet);
                        return aAccClassRet;
                    }
                }

                //交费生效时间
                tMakeTime = tmpLCInsuAccTraceDB.getMakeTime();

                //==20060619===testing start===============
                logger.debug("第" + i + "条记录！");
                logger.debug("缴费日期：" + payDate);
                logger.debug("缴费时间：" + tMakeTime);
                logger.debug("缴费金额：" + tmpLCInsuAccTraceDB.getMoney());
                //==20060619==testind end===================

                // 现在比较lcinsureaccclass表的baladate和lcinsuretrace表的paydate的值的大小
                int tIntv = PubFun.calInterval(tBalaDate, payDate, aIntvType);
                if (tIntv >= 0) {

                    //处理轨迹表中paydate>=帐户分类表的结息日期BalaDate
                    if (tIntv == 0 && tBalaTime.compareTo(tMakeTime) >= 0) {
                        continue;
                    }

                    //临时存放帐户一笔缴费金额
                    tempMoney = tmpLCInsuAccTraceDB.getMoney();
                    tInsureAccTraceMoneySum += tempMoney;

                    //然后对轨迹表的这笔钱tempMoney作结息
                    String[] ResultRate2 = getMultiAccRateNew(aInsuAccNo,
                            tLMRiskInsuAccSchema, payDate, aBalaDate,
                            aRateType, "C", aIntvType, Period, tType, Depst);
                    tCount = Integer.parseInt(ResultRate2[0]);

                    //====20060619==testing start=============
                    logger.debug("查到" + tCount + "条利率值");
                    //===20060619==testing end================

                    for (int m = 1; m < tCount + 1; m++) {
                        if (ResultRate2[m] == null) {
                            ResultRate2[m] = "0";
                        }
                        tCalAccClassRate = Double.parseDouble(ResultRate2[m]);

                        //===20060619==testing start===============
                        logger.debug("Rate = " + tCalAccClassRate);
                        //===2060619===testing end=================

                        double tSubInterest = tempMoney * tCalAccClassRate;
                        tSubInterest = Arith.round(tSubInterest, 2);

                        //===20061018===testing======================
                        logger.debug("利息： " + tSubInterest);
                        //===20061018===testing end==================

                        aAccClassInterest += tSubInterest;
                    }
                }
            }

            //将帐户分类表的另一部分余额结息
            logger.debug("=====将帐户分类表的另一部分余额结息=====\n");
            ResultRate = getMultiAccRateNew(aInsuAccNo,
                                            tLMRiskInsuAccSchema,
                                            tBalaDate, aBalaDate,
                                            aRateType, "C", aIntvType, Period,
                                            tType, Depst);
            tCount = Integer.parseInt(ResultRate[0]);
            for (int m = 1; m < tCount + 1; m++) {
                if (ResultRate[m] == null) {
                    ResultRate[m] = "0";
                }
                tCalAccClassRate = Double.parseDouble(ResultRate[m]);
                aAccClassInterest += tInsuAccClassBala * tCalAccClassRate;
                aAccClassInterest = Arith.round(aAccClassInterest, 2);
                //===20060703===testing start========================
                logger.debug("计算后的利率：" + tCalAccClassRate);
                //===20060703==testing end===========================

            }
            break;
        }
        if (Integer.parseInt(tLMRiskInsuAccSchema.getAccComputeFlag()) != 0) {
            aAccClassSumPay = tInsuAccClassBala + tInsureAccTraceMoneySum +
                              aAccClassInterest;
        } else {
            aAccClassSumPay = tInsureAccTraceMoneySum;
        }
        //===20061018===testing start==================
        logger.debug("帐户金额： " + tInsuAccClassBala);
        logger.debug("Trace表的缴费和： " + tInsureAccTraceMoneySum);
        logger.debug("Trace表的缴费利息和： " + aAccClassInterest);
        logger.debug("帐户实际余额： " + aAccClassSumPay);
        //===20061018===testing end====================

        //准备返回的数据包
        aAccClassRet.setNameAndValue("aAccClassInterest", aAccClassInterest);
        aAccClassRet.setNameAndValue("aAccClassSumPay", aAccClassSumPay);

        return aAccClassRet;
    }


    

}
