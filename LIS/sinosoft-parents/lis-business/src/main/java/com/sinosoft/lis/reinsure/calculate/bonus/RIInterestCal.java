

package com.sinosoft.lis.reinsure.calculate.bonus;

import java.util.Date;

import com.sinosoft.lis.db.RIBonusAccDB;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.reinsure.tools.RIPubFun;
import com.sinosoft.lis.schema.RIBonusAccSchema;
import com.sinosoft.lis.schema.RIBonusInterestSchema;
import com.sinosoft.lis.schema.RIBonusInterestTraceSchema;
import com.sinosoft.lis.vschema.RIBonusAccSet;
import com.sinosoft.lis.vschema.RIInterestSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class RIInterestCal {
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private String mStartDate = "";
	private String mEndDate = "";
	private String mRiskCode = "";
	private String mCalMonth = "";
	//private  String  sInterestRate = "";
	private TransferData mTransferData;

	private RIInterestSet mRIInterestSet = new RIInterestSet();
	private RIBonusAccSet mRIBonusAccSet;
	private double mInterestRate = 0; // 利率

	/** 往后面传输数据的容器 */
	private VData mVData = new VData();
	private MMap mMap = new MMap();

	private PubSubmit mPubSubmit = new PubSubmit();
	private RIPubFun mRIPubFun = new RIPubFun();
	
	ExeSQL tExeSQL = new ExeSQL();
	SSRS tSSRS = new SSRS();
	

	public RIInterestCal() {

	}

	/**
	 * 得到业务数据
	 * 
	 * @param cInputData
	 *            VData
	 * @param cOperate
	 *            String
	 * @return boolean
	 */
	private boolean getInputData(VData cInputData, String cOperate) {
		this.mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		return true;
	}

	/**
	 * 数据处理
	 * 
	 * @return boolean
	 */
	private boolean dealData() {
		mStartDate = (String) mTransferData.getValueByName("StartDate");
		mEndDate = (String) mTransferData.getValueByName("EndDate");
		mRiskCode = (String) mTransferData.getValueByName("RiskCode");
		//获取当前险种的利率
		String sInterestRate = (String) mTransferData
				.getValueByName("InterestRate");
		mInterestRate = Double.parseDouble(sInterestRate);
		//获取当前月份
		getCalMonth();
		//获取累计红利
		if (!getAccBonus()) {
			buildError("InitInfo", "获取累计红利时出错！" + mErrors.getFirstError());
			return false;
		}

		return true;
	}
	// 获取累计红利或Coupon
	private boolean getAccBonus() {
		StringBuffer strBuffer;
		RIBonusAccDB tRIBonusAccDB = new RIBonusAccDB();
		if (!getCalMonth()) {
			return false;
		}
		while (1 == 1) {
			strBuffer = new StringBuffer();
			strBuffer
					.append(" select * from RIBonusAcc a where a.State = '01' and a.Riskcode='");
			strBuffer.append(mRiskCode);
			strBuffer.append("' and a.Startdate<=to_date('");
			strBuffer.append(mEndDate);
			strBuffer
					.append("','yyyy-Mm-dd') and not exists (select * from RIBonusInterestTrace b where a.Polno=b.Polno and a.Insuaccno = b.Insuaccno and b.Calmonth = '");
			strBuffer.append(mCalMonth);
			strBuffer.append("') and rownum<=100 ");

			String strSQL = strBuffer.toString();
			strSQL = "select count(*) from (" + strSQL + ")";
			tSSRS = tExeSQL.execSQL(strSQL);
			if (tExeSQL.mErrors.needDealError() || tSSRS == null) {
				buildError("InitInfo", "计算利息获取累计红利或Coupon时出错！");
				return false;
			}
			if (tSSRS.GetText(1, 1).equals("0")) {
				break;
			} else {
				mRIBonusAccSet = tRIBonusAccDB.executeQuery(strBuffer
						.toString());
					try {
						if (!calInterest()) {
							buildError("InitInfo", "计算红利利息出错！");
							return false;
						}
					} catch (Exception e) {
						buildError("InitInfo", "计算红利利息出错！" + e.getMessage());
						return false;
					}
					break;
//				}
			}
		}
		return true;
	}
	/**
	 * 获取当前月份
	 * 
	 * @return
	 */
	private boolean getCalMonth() {
		// ......
//		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
//		String tdate = sf.format(mStartDate);
		String[] arrdate = mStartDate.split("-");
		int year = Integer.parseInt(arrdate[0]);
		int month = Integer.parseInt(arrdate[1]);
		int day = Integer.parseInt(arrdate[2]);
		mCalMonth = String.valueOf(year)+String.valueOf(month);

		return true;
	}

	/**
	 * 计算红利利息
	 * 
	 * @return
	 */

	private boolean calInterest() throws Exception {

		RIBonusAccSchema tRIBonusAccSchema;
		FDate fDate = new FDate();
		double interest = 0;
		for (int i = 1; i <= mRIBonusAccSet.size(); i++) {
			tRIBonusAccSchema = mRIBonusAccSet.get(i);
			Date lastPayDate = fDate
					.getDate(tRIBonusAccSchema.getLastPayDate());
			Date startDate = fDate.getDate(mStartDate);
			Date endDate = fDate.getDate(mEndDate);

			if (lastPayDate.compareTo(startDate) > 0
					&& lastPayDate.compareTo(endDate) < 0) {
				// 如果最后一次派发红利在当月之内
				String sql = "select sum(sumBonus),max(a.LastPayDate) from RIBonusAccBake a " +
						" where a.polno = '"+mRIBonusAccSet.get(i).getPolNo()+"' " +
						" and  a.insuAccNo = '"+mRIBonusAccSet.get(i).getInsuAccNo()+"' " +
						" and a.riskcode = '"+mRIBonusAccSet.get(i).getRiskCode()+"' group by a.riskcode,a.polno";
				tSSRS = tExeSQL.execSQL(sql);
				String sumBonus;
				String LastPayDate;
				Double sumBonusLast;
				if(tSSRS.getMaxRow()!=0){
					//备份表的红利和派发日期
					 sumBonus = tSSRS.GetText(1, 1);
					 LastPayDate = tSSRS.GetText(1, 2);
					//最后一次增加的红利
					 sumBonusLast = mRIBonusAccSet.get(i).getSumBonus() - Double.parseDouble(sumBonus);
				}
				else
				{
					 sumBonus = "0";
					 LastPayDate = mRIBonusAccSet.get(i).getLastPayDate();
					 sumBonusLast = mRIBonusAccSet.get(i).getSumBonus();
				}
				interest = calInterestAcc(Double.parseDouble(sumBonus),sumBonusLast,LastPayDate);
			} else {
				// 如果最后一次派发红利不在当月之内
				interest = monthInterest(mInterestRate,mRIBonusAccSet.get(i).getSumBonus());
			}
			RIBonusInterestTraceSchema tRIBonusInterestTraceSchema = new RIBonusInterestTraceSchema();
			// 保存共保红利利息轨迹表
			if (!recordRIBonusInterestTrace(tRIBonusAccSchema,tRIBonusInterestTraceSchema,interest) ){
				return false;
			}
			// 更新共保红利利息总表
			if (!updateRIBonusInterest(tRIBonusInterestTraceSchema,tRIBonusAccSchema)) {
				return false;
			}
		}

		return true;
	}

	/**
	 * 保存共保红利利息轨迹表
	 */
	private boolean recordRIBonusInterestTrace(RIBonusAccSchema tRIBonusAccSchema,RIBonusInterestTraceSchema tRIBonusInterestTraceSchema,double interest) {
		
		tRIBonusInterestTraceSchema.setSerialNo(PubFun1.CreateMaxNo("Interest", 10));
		tRIBonusInterestTraceSchema.setPolNo(tRIBonusAccSchema.getPolNo());
		tRIBonusInterestTraceSchema.setRiskCode(tRIBonusAccSchema.getRiskCode());
		tRIBonusInterestTraceSchema.setInsuAccNo(tRIBonusAccSchema.getInsuAccNo());
		tRIBonusInterestTraceSchema.setInterestRate(mInterestRate);
		tRIBonusInterestTraceSchema.setMoney(interest);
		tRIBonusInterestTraceSchema.setOperator("001");
		tRIBonusInterestTraceSchema.setMakeDate(PubFun.getCurrentDate());
		tRIBonusInterestTraceSchema.setMakeTime(PubFun.getCurrentTime());
		tRIBonusInterestTraceSchema.setModifyDate(PubFun.getCurrentDate());
		tRIBonusInterestTraceSchema.setModifyTime(PubFun.getCurrentTime());
		tRIBonusInterestTraceSchema.setCalMonth(mCalMonth);
		mMap.put(tRIBonusInterestTraceSchema, "INSERT");

		return true;
	}

	/**
	 * 更新共保红利利息总表
	 */
	private boolean updateRIBonusInterest(RIBonusInterestTraceSchema tRIBonusInterestTraceSchema,RIBonusAccSchema tRIBonusAccSchema) {
		
		RIBonusInterestSchema tRIBonusInterestSchema = new RIBonusInterestSchema();	
		tSSRS = new SSRS();
		String sql = "select siMoney from RIBonusInterest where polno='"+tRIBonusInterestTraceSchema.getPolNo()+"' and InsuAccNo='"+tRIBonusInterestTraceSchema.getInsuAccNo()+"'";
		tSSRS = tExeSQL.execSQL(sql);
		Double siMoney = Double.parseDouble(tSSRS.GetText(1, 1))+tRIBonusInterestTraceSchema.getMoney();
		String sqlInterest = "update RIBonusInterest set siMoney='"+siMoney+"' where polno='"+tRIBonusInterestTraceSchema.getPolNo()+"' and InsuAccNo='"+tRIBonusInterestTraceSchema.getInsuAccNo()+"'";
		mMap.put(sqlInterest, "UPDATE");
		// ....

		return true;
	}

	/**
	 * 计算利息
	 * 
	 * @param cInputData
	 *            VData
	 * @param cOperate
	 *            String
	 * @return boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		if (!getInputData(cInputData, cOperate)) {
			return false;
		}
		if (!dealData()) {
			return false;
		}
		if (!saveData()) {
			return false;
		}
		return true;
	}

	/**
	 * 从累计红利表计算利息
	 */
	private double calInterestAcc(double sumBonus,double sumBonusLast,String LastPayDate) {
		double interest = 0;
		interest = mInterestRate * sumBonus + endMonthInterest(LastPayDate,mInterestRate,sumBonusLast);
		return interest;
	}

	/**
	 * 从累计红利备份表计算利息
	 */
	private double calInterestAccBake() {
		double interest = 0;
		// ......
		return interest;
	}

	/**
	 * 月度下计息（首月计息）
	 */
	private double endMonthInterest(String startDate, double rate, double money) {
		// 计算当月首天和末天
		String[] sfMonthDate = mRIPubFun.calFLDate(startDate);
		String startMonthDate = sfMonthDate[0];
		String endMonthDate = sfMonthDate[1];
		// 计算当月天数
		int monthDayNum = mRIPubFun.calInterval(startMonthDate, endMonthDate,"D")+1;
		// 计算红利派发日期到月末天数
		int interestDayNum = mRIPubFun
				.calInterval(startDate, endMonthDate, "D")+1;
		double interest = money * rate * interestDayNum / monthDayNum;
		return interest;
	}

	/**
	 * 月度上计息（末月计息）
	 */
	private double firstMonthInterest(String endDate, double rate, double money) {
		// 计算当月首天和末天
		String[] sfMonthDate = mRIPubFun.calFLDate(endDate);
		String startMonthDate = sfMonthDate[0];
		String endMonthDate = sfMonthDate[1];
		// 计算当月天数
		int monthDayNum = mRIPubFun.calInterval(startMonthDate, endMonthDate,"D")+1;
		// 计算月首到结束日天数
		int interestDayNum = mRIPubFun.calInterval(startMonthDate, endDate, "D")+1;
		double interest = money * rate * interestDayNum / monthDayNum;
		return interest;
	}

	/**
	 * 月利息
	 */
	private double monthInterest(double rate, double money) {
		double interest = money * rate;
		return interest;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "RIRiskCal";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
		System.out.print(szErrMsg);
	}
	/**
	 * 保存结果
	 * 
	 * @return boolean
	 */
	private boolean saveData() {
		try {
			mVData.clear();
			mVData.add(mMap);
			if (!mPubSubmit.submitData(mVData, "")) {
				return false;
			}
			mMap =  new MMap();
		} catch (Exception ex) {
			CError tError = new CError();
			tError.moduleName = "RICalItemProc";
			tError.functionName = "saveResult";
			tError.errorMessage = "风险计算，保存数据时出错：" + ex.getMessage();
			System.out.println(" ex.getMessage() " + ex.getMessage());
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}
	public static void main(String[] args) {
		RIInterestCal tRIInterestCal = new RIInterestCal();

	}
}
