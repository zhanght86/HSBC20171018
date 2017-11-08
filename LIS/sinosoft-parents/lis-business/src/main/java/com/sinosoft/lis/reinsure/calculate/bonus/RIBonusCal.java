

package com.sinosoft.lis.reinsure.calculate.bonus;

import com.sinosoft.lis.db.RIBonusAccDB;
import com.sinosoft.lis.db.RIBonusInterestDB;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.RIBonusAccBakeSchema;
import com.sinosoft.lis.schema.RIBonusAccSchema;
import com.sinosoft.lis.schema.RIBonusInterestSchema;
import com.sinosoft.lis.schema.RIBonusTraceSchema;
import com.sinosoft.lis.vschema.RIBonusTraceSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class RIBonusCal {
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private String mStartDate = "";
	private String mEndDate = "";
	private String mRiskCode = "";
	private TransferData mTransferData;
	/** 往后面传输数据的容器 */
	private VData mVData = new VData();
	private MMap mMap = new MMap();

	private PubSubmit mPubSubmit = new PubSubmit();

	private RIBonusTraceSet mRIBonusTraceSet = new RIBonusTraceSet();

	public RIBonusCal() {
	}

	/**
	 * 获取红利数据，计算累计红利
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
		return true;
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
		mMap = new MMap();
		// if(mRiskCode.equals("111303") ){

		if (!getYearBonus()) {// 获取周年红利
			return false;
		}
		if (!getExpBonus()) {// 获取满期红利
			return false;
		}
		if (!getSpeBonus()) {// 获取特别红利
			return false;
		}
		// 对状态为满期，退保，失效的保单停止生息
		if (!setBonusState()) {
			return false;
		}
		if (!saveData()) {
			buildError("calAccAmnt", "计算累计风险，更新接口表数据时出错。");
			return false;
		}
		// }
		// return false;
		return true;
	}

	// 对状态为满期，退保，失效的保单停止生息
	private boolean setBonusState() {
		String sql = "select a.Polno,c.InsuAccNo,b.Statetype from lcpol a, lccontstate b,LCInsureAcc c"
				+ " where a.polno = b.polno "
				+ " and b.Statetype in ('Available', 'Terminate') "
				+ " and b.state ='1' "
				+ " and c.PolNo=a.PolNo "
				+ " and Exists(select  'X' from RIBonusAcc d where d.Polno=a.Polno and c.InsuAccNo = d.InsuAccNo and d.state='01')";
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = tExeSQL.execSQL(sql);
		if (tExeSQL.mErrors.needDealError() || tSSRS == null) {
			buildError("InitInfo", "对红利停止生息出错！");
			return false;
		}
		if (tSSRS.GetText(1, 1).equals("0")) {
			return true;
		} else {
			for (int i = 1; i <= tSSRS.getMaxRow(); i++) {
				RIBonusAccDB tRIBonusAccDB = new RIBonusAccDB();
				tRIBonusAccDB.setPolNo(tSSRS.GetText(i, 1));
				tRIBonusAccDB.setInsuAccNo(tSSRS.GetText(i, 2));
				if (tRIBonusAccDB.getInfo()) {
					RIBonusAccSchema tRIBonusAccSchema = tRIBonusAccDB
							.getSchema();
					tRIBonusAccSchema.setEndDate(PubFun.getCurrentDate());
					if (tSSRS.GetText(i, 3).equals("Available")) {
						tRIBonusAccSchema.setState("03");
					} else {
						tRIBonusAccSchema.setState("02");
					}
					mRIBonusTraceSet.add(tRIBonusAccSchema);
				}
			}
			mMap.put(mRIBonusTraceSet, "UPADTE");
			return true;
		}
	}

	/**
	 * 获取周年红利
	 * 
	 * @return boolean
	 */
	private boolean getYearBonus() {
		ExeSQL tExeSQL = new ExeSQL();
		StringBuffer strBuffer = new StringBuffer();
		SSRS tSSRS;
		// lcget表:getMode: 0,1-领取,4-增额, 2-累计生息；lmdutyget表：周年红利：GetType3='1'
		// Lmriskaccget险种账户关联表
		// Lcinsureacctrace保险帐户表记价履历表
		while (1 == 1) {
			strBuffer = new StringBuffer();
			strBuffer
					.append(" select b.PolNo,b.InsuAccNo,b.Contno,d.Prtno,b.Riskcode,d.Insuredno,d.Appntno,nvl(sum(b.Money),0),b.Currency,b.Managecom,b.Paydate from Lmriskaccget a, Lcinsureacctrace b, (select u.Cvalidate Cvalidate, u.Contno Contno, u.Polno Polno, u.Riskcode Riskcode, v.Dutycode Dutycode, w.Getdutycode Getdutycode, u.Appntno Appntno, u.Insuredno Insuredno, u.Prtno Prtno  from lcpol u, lcget v, Lmdutyget w where u.Polno = v.Polno and u.Riskcode = '");
			strBuffer.append(mRiskCode);
			strBuffer
					.append("' and v.Getdutycode = w.Getdutycode and w.GetType3 = '1' and v.Getmode='4') d  ");
			strBuffer
					.append(" where a.Insuaccno = b.Insuaccno and a.Getdutycode = d.Getdutycode and b.Contno = d.Contno and b.Polno = d.Polno  and b.moneytype = 'DVD' ");
			strBuffer
					.append(" and not exists (select * from RIBonusTrace x where x.Polno=d.Polno and x.Insuaccno=b.Insuaccno and x.Paydate=b.Paydate)");
			strBuffer.append(" and b.money!=0 ");
			strBuffer.append(" and b.Makedate between '");
			strBuffer.append(mStartDate);
			strBuffer.append("' and '");
			strBuffer.append(mEndDate);
			strBuffer.append("' and rownum <=100 ");
			strBuffer
					.append(" group by b.InsuAccNo,b.Contno,b.PolNo,b.Riskcode,b.Money,d.Prtno,d.Insuredno,d.Appntno,b.Currency,b.Managecom,b.Paydate ");
			System.out.print(strBuffer.toString());
			String strSQL = strBuffer.toString();
			strSQL = "select count(*) from (" + strSQL + ")";
			tSSRS = tExeSQL.execSQL(strSQL);
			if (tExeSQL.mErrors.needDealError() || tSSRS == null) {
				buildError("InitInfo", "获取周年红利出错！");
				return false;
			}
			if (tSSRS.GetText(1, 1).equals("0")) {
				break;
			} else {
				tSSRS = tExeSQL.execSQL(strBuffer.toString());
				if (tExeSQL.mErrors.needDealError() || tSSRS.getMaxRow() == 0) {
					buildError("InitInfo", "获取周年红利出错！");
					return false;
				}
				mRIBonusTraceSet = new RIBonusTraceSet();
				// 记录红利轨迹表
				if (!recordBonusTrace(tSSRS, "1")) {
					buildError("InitInfo", "记录红利轨迹表出错！");
					return false;
				}
				// 计算累积红利
				if (!calAccBonus()) {
					buildError("InitInfo", "计算累积红利出错！");
					return false;
				}
				tSSRS = null;
			}
			break;
		}

		return true;
	}

	/**
	 * 获取满期红利
	 * 
	 * @return boolean
	 */
	private boolean getExpBonus() {
		ExeSQL tExeSQL = new ExeSQL();
		StringBuffer strBuffer = new StringBuffer();
		SSRS tSSRS;
		// lcget表:getMode: 0,1-领取,4-增额, 2-累计生息；lmdutyget表：周年红利：GetType3='1'
		while (1 == 1) {
			strBuffer = new StringBuffer();
			strBuffer
					.append(" select b.PolNo,b.InsuAccNo,b.Contno,d.Prtno,b.Riskcode,d.Insuredno,d.Appntno,nvl(sum(b.Money),0),b.Currency,b.Managecom ,b.Paydate from Lmriskaccget a, Lcinsureacctrace b, (select u.Cvalidate Cvalidate, u.Contno Contno, u.Polno Polno, u.Riskcode Riskcode, v.Dutycode Dutycode, w.Getdutycode Getdutycode, u.Appntno Appntno, u.Insuredno Insuredno, u.Prtno Prtno  from lcpol u, lcget v, Lmdutyget w where u.Polno = v.Polno and u.Riskcode = '");
			strBuffer.append(mRiskCode);
			strBuffer
					.append("' and v.Getdutycode = w.Getdutycode and w.GetType3 = '2' and v.Getmode='4') d  ");
			strBuffer
					.append(" where a.Insuaccno = b.Insuaccno and a.Getdutycode = d.Getdutycode and b.Contno = d.Contno and b.Polno = d.Polno and b.Dutycode = d.Dutycode and b.moneytype = 'SI' ");
			strBuffer
					.append(" and not exists (select * from RIBonusTrace x where x.Polno=d.Polno and x.Insuaccno=b.Insuaccno and x.Paydate=b.Paydate)");
			strBuffer.append(" and b.money!=0 ");
			strBuffer.append(" and b.Makedate between '");
			strBuffer.append(mStartDate);
			strBuffer.append("' and '");
			strBuffer.append(mEndDate);
			strBuffer.append("' and rownum <=100 ");
			strBuffer
					.append(" group by b.InsuAccNo,b.Contno,b.PolNo,b.Riskcode,b.Money,d.Prtno,d.Insuredno,d.Appntno,b.Currency,b.Managecom ,b.Paydate");

			String strSQL = strBuffer.toString();
			strSQL = "select count(*) from (" + strSQL + ")";
			tSSRS = tExeSQL.execSQL(strSQL);
			if (tExeSQL.mErrors.needDealError() || tSSRS == null) {
				buildError("InitInfo", "获取满期红利出错！");
				return false;
			}
			if (tSSRS.GetText(1, 1).equals("0")) {
				break;
			} else {
				tSSRS = tExeSQL.execSQL(strBuffer.toString());
				if (tExeSQL.mErrors.needDealError() || tSSRS.getMaxRow() == 0) {
					buildError("InitInfo", "获取满期红利出错！");
					return false;
				}
				mRIBonusTraceSet = new RIBonusTraceSet();
				// 记录红利轨迹表
				if (!recordBonusTrace(tSSRS, "2")) {
					buildError("InitInfo", "记录红利轨迹表出错！");
					return false;
				}
				// 计算累积红利
				if (!calAccBonus()) {
					buildError("InitInfo", "计算累积红利出错！");
					return false;
				}
				tSSRS = null;
			}
		}

		return true;
	}

	/**
	 * 获取特别红利
	 * 
	 * @return boolean
	 */
	private boolean getSpeBonus() {

		ExeSQL tExeSQL = new ExeSQL();
		StringBuffer strBuffer = new StringBuffer();
		SSRS tSSRS;
		// lcget表:getMode: 0,1-领取,4-增额, 2-累计生息；lmdutyget表：周年红利：GetType3='1'
		while (1 == 1) {
			strBuffer = new StringBuffer();
			strBuffer
					.append(" select b.PolNo,b.InsuAccNo,b.Contno,d.Prtno,b.Riskcode,d.Insuredno,d.Appntno,nvl(sum(b.Money),0),b.Currency,b.Managecom ,b.Paydate from Lmriskaccget a, Lcinsureacctrace b, (select u.Cvalidate Cvalidate, u.Contno Contno, u.Polno Polno, u.Riskcode Riskcode, v.Dutycode Dutycode, w.Getdutycode Getdutycode, u.Appntno Appntno, u.Insuredno Insuredno, u.Prtno Prtno  from lcpol u, lcget v, Lmdutyget w where u.Polno = v.Polno and u.Riskcode = '");
			strBuffer.append(mRiskCode);
			strBuffer
					.append("' and v.Getdutycode = w.Getdutycode and w.GetType3 = '3' and v.Getmode='4') d  ");
			strBuffer
					.append(" where a.Insuaccno = b.Insuaccno and a.Getdutycode = d.Getdutycode and b.Contno = d.Contno and b.Polno = d.Polno and b.Dutycode = d.Dutycode and b.moneytype = 'SI' ");
			strBuffer
					.append(" and not exists (select * from RIBonusTrace x where x.Polno=d.Polno and x.Insuaccno=b.Insuaccno  and x.Paydate=b.Paydate)");
			strBuffer.append(" and b.Money!=0 ");
			strBuffer.append(" and b.Makedate between '");
			strBuffer.append(mStartDate);
			strBuffer.append("' and '");
			strBuffer.append(mEndDate);
			strBuffer.append("' and rownum <=100 ");
			strBuffer
					.append(" group by b.InsuAccNo,b.Contno,b.PolNo,b.Riskcode,b.Money,d.Prtno,d.Insuredno,d.Appntno,b.Currency,b.Managecom,b.Paydate ");

			String strSQL = strBuffer.toString();
			strSQL = "select count(*) from (" + strSQL + ")";
			tSSRS = tExeSQL.execSQL(strSQL);
			if (tExeSQL.mErrors.needDealError() || tSSRS == null) {
				buildError("InitInfo", "获取特别红利出错！");
				return false;
			}
			if (tSSRS.GetText(1, 1).equals("0")) {
				break;
			} else {
				tSSRS = tExeSQL.execSQL(strBuffer.toString());
				if (tExeSQL.mErrors.needDealError() || tSSRS.getMaxRow() == 0) {
					buildError("InitInfo", "获取特别红利出错！");
					return false;
				}
				mRIBonusTraceSet = new RIBonusTraceSet();
				// 记录红利轨迹表
				if (!recordBonusTrace(tSSRS, "3")) {
					buildError("InitInfo", "记录红利轨迹表出错！");
					return false;
				}
				// 计算累积红利
				if (!calAccBonus()) {
					buildError("InitInfo", "计算累积红利出错！");
					return false;
				}
				tSSRS = null;
			}
		}

		return true;
	}

	/**
	 * 记录红利轨迹表
	 * 
	 * @return boolean
	 */
	private boolean recordBonusTrace(SSRS tSSRS, String bonusType) {
		RIBonusTraceSchema tRIBonusTraceSchema;
		for (int i = 1; i <= tSSRS.getMaxRow(); i++) {
			tRIBonusTraceSchema = new RIBonusTraceSchema();

			tRIBonusTraceSchema.setSerialNo(PubFun1.CreateMaxNo("RIBONUS", 6));
			tRIBonusTraceSchema.setPolNo(tSSRS.GetText(i, 1));
			tRIBonusTraceSchema.setInsuAccNo(tSSRS.GetText(i, 2));
			tRIBonusTraceSchema.setContNo(tSSRS.GetText(i, 3));
			tRIBonusTraceSchema.setGrpContNo("");
			tRIBonusTraceSchema.setGrpPolNo("");
			tRIBonusTraceSchema.setPrtNo(tSSRS.GetText(i, 4));
			tRIBonusTraceSchema.setRiskCode(tSSRS.GetText(i, 5));
			tRIBonusTraceSchema.setAccType("004");
			tRIBonusTraceSchema.setBonusType(bonusType);
			tRIBonusTraceSchema.setInsuredNo(tSSRS.GetText(i, 6));
			tRIBonusTraceSchema.setAppntNo(tSSRS.GetText(i, 7));
			tRIBonusTraceSchema.setBalaDate(PubFun.getCurrentDate());
			tRIBonusTraceSchema.setBonus(tSSRS.GetText(i, 8));
			tRIBonusTraceSchema.setManageCom(tSSRS.GetText(i, 10));
			tRIBonusTraceSchema.setOperator((String) mTransferData
					.getValueByName("Operator"));
			tRIBonusTraceSchema.setMakeDate(PubFun.getCurrentDate());
			tRIBonusTraceSchema.setMakeTime(PubFun.getCurrentTime());
			tRIBonusTraceSchema.setModifyDate(PubFun.getCurrentDate());
			tRIBonusTraceSchema.setModifyTime(PubFun.getCurrentTime());
			tRIBonusTraceSchema.setCurrency(tSSRS.GetText(i, 9));
			tRIBonusTraceSchema.setPayDate(tSSRS.GetText(i, 11)); // paydate

			mRIBonusTraceSet.add(tRIBonusTraceSchema);
		}
		mMap.put(mRIBonusTraceSet, "INSERT");
		return true;
	}

	/**
	 * 计算累计红利
	 * 
	 * @return boolean
	 */
	private boolean calAccBonus() {
		RIBonusAccSchema tRIBonusAccSchema = new RIBonusAccSchema();
		RIBonusAccDB tRIBonusAccDB = new RIBonusAccDB();

		RIBonusInterestSchema tRIBonusInterestSchema;
		RIBonusInterestDB tRIBonusInterestDB = new RIBonusInterestDB();

		for (int i = 1; i <= mRIBonusTraceSet.size(); i++) {
			double siMoney = 0; // 利息
			double sumBonus = 0;// 红利
			// 累计利息
			tRIBonusInterestDB = new RIBonusInterestDB();
			tRIBonusInterestDB.setPolNo(mRIBonusTraceSet.get(i).getPolNo());
			tRIBonusInterestDB.setInsuAccNo(mRIBonusTraceSet.get(i)
					.getInsuAccNo());
			if (!tRIBonusInterestDB.getInfo()) {
				// buildError("InitInfo", "获取周年红利利息出错！");
				// return false;
				tRIBonusInterestSchema = new RIBonusInterestSchema();
				tRIBonusInterestSchema.setPolNo(mRIBonusTraceSet.get(i)
						.getPolNo());
				tRIBonusInterestSchema.setInsuAccNo(mRIBonusTraceSet.get(i)
						.getInsuAccNo());
				tRIBonusInterestSchema.setContNo(mRIBonusTraceSet.get(i)
						.getContNo());
				tRIBonusInterestSchema.setCurrency(mRIBonusTraceSet.get(i)
						.getCurrency());
				tRIBonusInterestSchema.setInsuredNo(mRIBonusTraceSet.get(i)
						.getInsuredNo());
				tRIBonusInterestSchema.setBonusType(mRIBonusTraceSet.get(i)
						.getBonusType());
				tRIBonusInterestSchema.setRiskCode(mRIBonusTraceSet.get(i)
						.getRiskCode());
				tRIBonusInterestSchema.setPrtNo(mRIBonusTraceSet.get(i)
						.getPrtNo());
				tRIBonusInterestSchema.setSIMoney(0);
				tRIBonusInterestSchema.setOperator((String) mTransferData
						.getValueByName("Operator"));
				tRIBonusInterestSchema.setMakeDate(PubFun.getCurrentDate());
				tRIBonusInterestSchema.setMakeTime(PubFun.getCurrentTime());
				tRIBonusInterestSchema.setModifyDate(PubFun.getCurrentDate());
				tRIBonusInterestSchema.setModifyTime(PubFun.getCurrentTime());
				mMap.put(tRIBonusInterestSchema, "INSERT");

			} else {
				tRIBonusInterestSchema = tRIBonusInterestDB.getSchema();
				siMoney = tRIBonusInterestSchema.getSIMoney();
				// 清零利息汇总表
				tRIBonusInterestSchema.setSIMoney(0);
				tRIBonusInterestSchema.setModifyDate(PubFun.getCurrentDate());
				tRIBonusInterestSchema.setModifyTime(PubFun.getCurrentTime());

				mMap.put(tRIBonusInterestSchema, "UPDATE");
			}
			// 累计红利
			tRIBonusAccDB = new RIBonusAccDB();
			tRIBonusAccDB.setPolNo(mRIBonusTraceSet.get(i).getPolNo());
			tRIBonusAccDB.setInsuAccNo(mRIBonusTraceSet.get(i).getInsuAccNo());
			if (tRIBonusAccDB.getInfo()) {
				tRIBonusAccSchema = tRIBonusAccDB.getSchema();
				// 更新共保红利记录
				sumBonus = tRIBonusAccSchema.getSumBonus();
				// 计算共保红利备份表
				if (!saveRIBonusAccBake(tRIBonusAccSchema)) {
					return false;
				}
				tRIBonusAccSchema.setSumBonus(sumBonus
						+ mRIBonusTraceSet.get(i).getBonus() + siMoney);
				// 记录派发红利时间
				tRIBonusAccSchema.setLastPayDate(mRIBonusTraceSet.get(i)
						.getPayDate());
				tRIBonusAccSchema.setModifyDate(PubFun.getCurrentDate());
				tRIBonusAccSchema.setMakeTime(PubFun.getCurrentTime());
				if (!mRIBonusTraceSet.get(i).getBonusType().equals("1")) {
					tRIBonusAccSchema.setEndDate(PubFun.getCurrentDate());
				}
				mMap.put(tRIBonusAccSchema, "UPDATE");

			} else {
				// 保存共保红利记录
				tRIBonusAccSchema = new RIBonusAccSchema();
				tRIBonusAccSchema.setSumBonus(mRIBonusTraceSet.get(i)
						.getBonus());
				tRIBonusAccSchema.setPolNo(mRIBonusTraceSet.get(i).getPolNo());
				tRIBonusAccSchema
						.setContNo(mRIBonusTraceSet.get(i).getContNo());
				tRIBonusAccSchema.setPrtNo(mRIBonusTraceSet.get(i).getPrtNo());
				tRIBonusAccSchema.setRiskCode(mRIBonusTraceSet.get(i)
						.getRiskCode());
				tRIBonusAccSchema.setInsuAccNo(mRIBonusTraceSet.get(i)
						.getInsuAccNo());
				tRIBonusAccSchema.setInsuredNo(mRIBonusTraceSet.get(i)
						.getInsuredNo());
				tRIBonusAccSchema.setBonusType(mRIBonusTraceSet.get(i)
						.getBonusType());
				if (mRIBonusTraceSet.get(i).getBonusType().equals("2")) {
					// 满期红利
					tRIBonusAccSchema.setState("04");
				} else if (mRIBonusTraceSet.get(i).getBonusType().equals("3")) {
					// 特别红利
					tRIBonusAccSchema.setState("02");
				} else {
					// 周年红利
					tRIBonusAccSchema.setState("01");
				}
				tRIBonusAccSchema.setStartDate(PubFun.getCurrentDate());
				tRIBonusAccSchema.setOperator((String) mTransferData
						.getValueByName("Operator"));
				// 记录派发红利时间
				tRIBonusAccSchema.setLastPayDate(mRIBonusTraceSet.get(i)
						.getPayDate());
				tRIBonusAccSchema.setMakeDate(mRIBonusTraceSet.get(i)
						.getMakeDate());
				tRIBonusAccSchema.setMakeTime(mRIBonusTraceSet.get(i)
						.getMakeTime());
				tRIBonusAccSchema.setModifyDate(PubFun.getCurrentDate());
				tRIBonusAccSchema.setModifyTime(PubFun.getCurrentTime());
				mMap.put(tRIBonusAccSchema, "INSERT");
			}
		}

		return true;
	}

	/**
	 * 共保红利总备份表
	 */
	private boolean saveRIBonusAccBake(RIBonusAccSchema tRIBonusAccSchema) {
		RIBonusAccBakeSchema tRIBonusAccBakeSchema = new RIBonusAccBakeSchema();
		Reflections tReflections = new Reflections();
		tReflections.transFields(tRIBonusAccBakeSchema, tRIBonusAccSchema);
		tRIBonusAccBakeSchema
				.setSerialNo(PubFun1.CreateMaxNo("RIBONUSBAKE", 6));
		mMap.put(tRIBonusAccBakeSchema, "INSERT");
		return true;
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

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "RIRiskCal";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
		System.out.print(szErrMsg);
	}
}

