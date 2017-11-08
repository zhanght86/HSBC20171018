

package com.sinosoft.lis.reinsure;

import com.sinosoft.utility.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.lis.pubfun.*;

import java.util.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.bl.*;
import com.sinosoft.lis.bq.*;

public class CalEndorRetentBL {

	public CErrors mErrors = new CErrors();
	private GlobalInput mGlobalInput = new GlobalInput();
	private VData mInputData;
	private String mOperate;
	private LREdorMainSet mLREdorMainSet = new LREdorMainSet();
	private VData mResult = new VData();
	private String mStartDate = "";
	private String mEndDate = "";
	private String mToday = "";
	private String mRiskCode = "";
    private String mContNo = "";                            //计算单张保单时使用

	// private String mReinsureCom="";

	public CalEndorRetentBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		System.out.println("beging bl.......");
		// LREdorMainDB tLREdorMainDB = new LREdorMainDB();
		// tLREdorMainDB.deleteSQL();
		mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;
		try {
			if (!mOperate.equals("CalEdor")) {
				buildError("submitData", "不支持的操作字符串");
				return false;
			}

			// 得到外部传入的数据，将数据备份到本类中
			if (!getInputData(cInputData)) {
				return false;
			}
			this.mLREdorMainSet.clear();
			mResult.clear();
			FDate chgdate = new FDate();
			Date dbdate = chgdate.getDate(mStartDate);
			Date dedate = chgdate.getDate(mEndDate);
//			if (this.mOperate.equals("CalEdor")) {
//				if (!getLapseData()) // 处理在该起止时间内的失效保单，并将结果存入this.mLREdorMainSet
//				{
//					buildError("submitData", "错误");
//					return false;
//				}
//				// if(!getEndorseDataKN()){
//				// buildError("submitData", "错误");
//				// return false;
//				// }
//			}

			while (dbdate.compareTo(dedate) <= 0) {
				mToday = chgdate.getString(dbdate);
				if (this.mOperate.equals("CalEdor")) {
					if (!getLapseData()) // 处理在该起止时间内的失效保单，并将结果存入this.mLREdorMainSet
					{
						buildError("submitData", "错误");
						return false;
					}
					if (!getEndorseData()) {
						buildError("submitData", "错误");
						return false;
					}
				}

				dbdate = PubFun.calDate(dbdate, 1, "D", null);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			buildError("submit", "发生异常");
			return false;
		}
		return true;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "CalEdorRetentBL";

		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	private boolean getInputData(VData cInputData) {
		mGlobalInput.setSchema((GlobalInput)cInputData.getObjectByObjectName("GlobalInput",0));
        TransferData mTransferData = (TransferData)cInputData.getObjectByObjectName("TransferData",0);

        if( mTransferData == null )
        {
            buildError("getInputData", "没有得到足够的信息！");
            return false;
        }

        mStartDate = (String)mTransferData.getValueByName("StartDate");
        mEndDate = (String)mTransferData.getValueByName("EndDate");
//        mReinsureCom = (String)mTransferData.getValueByName("ReinsureCom");
        mContNo = (String)mTransferData.getValueByName("ContNo");
		mRiskCode = (String) mTransferData.getValueByName("RiskCode");

		if (mStartDate.equals("")) {
			buildError("getInputData", "没有起始日期!");
			return false;
		}

		if (mEndDate.equals("")) {
			buildError("getInputData", "没有终止日期!");
			return false;
		}

		if (mGlobalInput == null) {
			buildError("getInputData", "没有得到足够的信息！");
			return false;
		}
		return true;
	}

	private boolean getLapseData() {
		this.mLREdorMainSet.clear();
		// 本保单年度失效保单处理，按照失效时期到分保终止日期的的跨度计算应该退回的分保费，并存入LREDORMAINset中
		// 思路与保额变动的情况类似（第一个判断），只是由于保全中无此保全项目，所以程序判断失效；
		String tSql="select polno from lccontstate s where statetype = 'Available' and state = '1' and modifydate = '"+this.mToday+"' " +
			" and exists (select 1 from lrpol where polno = s.polno and CessStart <= '"+this.mToday+"' and CessEnd >= '"+this.mToday+"' "
			+ ReportPubFun.getWherePart("riskcode", mRiskCode)
			+ " and ((grppolno='00000000000000000000' and add_months(cvalidate,12)<enddate) or(grppolno<>'00000000000000000000' and riskcode='211801'and payintv >0)))";
		tSql += ReportPubFun.getWherePart("contno", mContNo);
		
		SSRS tSSRS1=new ExeSQL().execSQL(tSql);
		for (int i = 1; i <= tSSRS1.MaxRow; i++) {
			String polno=tSSRS1.GetText(i, 1);
			LRPolDB tLRPolDB=new LRPolDB();
			LRPolSet tLRPolSet = tLRPolDB.executeQuery("select * from lrpol where polno='" + polno
					+ "' and CessStart <= '"+this.mEndDate+"' and CessEnd >= '"+this.mEndDate+"'");
			if(tLRPolSet.size()<1){
				System.out.println("error lrpol in caledor " + polno);
				continue;
			}
			for(int j=1;j<=tLRPolSet.size();j++){
			LRPolSchema tLRPolSchema = tLRPolSet.get(j);
			LCPolDB tLCPolDB = new LCPolDB();
			tLCPolDB.setPolNo(tLRPolSchema.getPolNo());
			System.out.println("LCPolBL.getinfo begin");
			if (!tLCPolDB.getInfo())
				continue;
			
			// 查询保单当前交至日期是否小于提数止期，若是，则是失效保单
			String tLapsDate;
			if(!tLCPolDB.getGrpPolNo().equals("00000000000000000000")){
					
				if (!EdorCalZT.JudgeLapse(tLCPolDB.getRiskCode(), tLCPolDB
						.getPaytoDate(), this.mEndDate, String.valueOf(tLCPolDB
						.getPayIntv()), tLCPolDB.getEndDate())) {
					continue;
				}
				// 处理失效保单
				// 失效时间
				tLapsDate = EdorCalZT.CalLapseDate(tLCPolDB.getRiskCode(),
						tLCPolDB.getPaytoDate());
			}else{
				String s = "select max(startdate) from lccontstate where polno='"
						+ tLCPolDB.getPolNo()
						+ "' and statetype='Available' and state='1' and startdate>='"
						+ tLRPolSchema.getCessStart()
						+ "' and startdate<='"
						+ tLRPolSchema.getCessEnd() + "'";
				SSRS tSSRS=new ExeSQL().execSQL(s);
				if(tSSRS.MaxRow==0){
					continue;
				}
				tLapsDate=tSSRS.GetText(1, 1);
			}
			
			// 取得所有本保单年度内的分保记录
			// int
			// tPolPremYear=PubFun.calInterval(tLCPolBL.getCValiDate(),tLRPolSchema.getCessStart(),"Y");
			// //当前的保单年度
			// String
			// tNextPerDay=PubFun.calDate(tLCPolBL.getCValiDate(),tPolPremYear+1,"Y","");
			// //当前保单年度对应的分保止期
			// System.out.println("tLapsDate="+tLapsDate);
			// System.out.println("tNextPerDay="+tNextPerDay);
			double tIntvl = PubFun.calInterval(tLapsDate, tLRPolSchema
					.getCessEnd(), "D"); // 失效日到分保止期之间的天数
			double tCessPeriod = PubFun
					.calInterval(tLRPolSchema.getCessStart(), tLRPolSchema
							.getCessEnd(), "D");
			double tReturnRate = tIntvl / tCessPeriod; // 时间比例 365天-old
			if (tReturnRate > 1)
				tReturnRate = 1;

			double tRRP = -tReturnRate * tLRPolSchema.getCessPrem(); // 应退回的分保费
			double tRRC = tRRP * tLRPolSchema.getCessCommRate();
			double tRRA = -tLRPolSchema.getCessionAmount();
			LREdorMainSchema tLREdorMainSchema = new LREdorMainSchema();
			Reflections tReflections = new Reflections();
			tReflections.transFields(tLREdorMainSchema, tLRPolSchema);
			// 准备LREDORMAIN的数据
			tLREdorMainSchema.setPolNo(tLRPolSchema.getPolNo());
			tLREdorMainSchema.setEdorType("LA"); // 虚拟'LA'的保全项目，表示失效
			tLREdorMainSchema.setReinsureCom(tLRPolSchema.getReinsureCom());
			tLREdorMainSchema.setReinsurItem(tLRPolSchema.getReinsurItem());
			tLREdorMainSchema.setRiskCalSort(tLRPolSchema.getRiskCalSort());
			tLREdorMainSchema.setInsuredYear(tLRPolSchema.getInsuredYear());
			tLREdorMainSchema.setChgCessAmt(tRRA);
			tLREdorMainSchema.setShRePrem(tRRP);
			tLREdorMainSchema.setShReComm(tRRC);
			tLREdorMainSchema.setCessStart(tLapsDate);
			tLREdorMainSchema.setCessEnd(tLRPolSchema.getCessEnd());
			tLREdorMainSchema.setOperator(this.mGlobalInput.Operator);
			tLREdorMainSchema.setMakeDate(PubFun.getCurrentDate());
			tLREdorMainSchema.setMakeTime(PubFun.getCurrentTime());
			tLREdorMainSchema.setModifyDate(PubFun.getCurrentDate());
			tLREdorMainSchema.setModifyTime(PubFun.getCurrentTime());
			LREdorMainDB tLREdorMainDB = new LREdorMainDB();
			tLREdorMainDB.setPolNo(tLRPolSchema.getPolNo());
			tLREdorMainDB.setEdorType("LA");
			tLREdorMainDB.setReinsureCom(tLRPolSchema.getReinsureCom());
			tLREdorMainDB.setReinsurItem(tLRPolSchema.getReinsurItem());
			tLREdorMainDB.setInsuredYear(tLRPolSchema.getInsuredYear());
			tLREdorMainDB.setRiskCalSort(tLRPolSchema.getRiskCalSort());
			tLREdorMainDB.setCessStart(tLapsDate);
			if (tLREdorMainDB.getCount() == 0) {
				tLREdorMainSchema
						.setEdorNo(PubFun1.CreateMaxNo("SERIALNO", ""));
				this.mLREdorMainSet.add(tLREdorMainSchema);
			}
			if (this.mLREdorMainSet.size() >1000) {
				this.prepareData();
				CalEndorRetentBLS tCalEndorRetentBLS = new CalEndorRetentBLS();
				if (!tCalEndorRetentBLS.submitData(mInputData, this.mOperate)) {
					System.out.println("tCalEndorRetentBLS error out");
					this.mErrors.copyAllErrors(tCalEndorRetentBLS.mErrors);
					CError tError = new CError();
					tError.moduleName = "CalEndorRetentBL";
					tError.functionName = "submitData";
					tError.errorMessage = "数据提交失败!";
					this.mErrors.addOneError(tError);
					return false;
				}
				mLREdorMainSet.clear();
			}
			}
		}
		if (this.mLREdorMainSet.size() > 0) {
			this.prepareData();
			CalEndorRetentBLS tCalEndorRetentBLS = new CalEndorRetentBLS();
			if (!tCalEndorRetentBLS.submitData(mInputData, this.mOperate)) {
				System.out.println("tCalEndorRetentBLS error out");
				this.mErrors.copyAllErrors(tCalEndorRetentBLS.mErrors);
				CError tError = new CError();
				tError.moduleName = "CalEndorRetentBL";
				tError.functionName = "submitData";
				tError.errorMessage = "数据提交失败!";
				this.mErrors.addOneError(tError);
				return false;
			}
		}
		return true;
	}

	// 保单发生保全或者失效的处理
	private boolean getEndorseData() {
		// 保全事件处理
		this.mLREdorMainSet.clear();
		String tSql = "select * from lpedorapp a where confdate='"
				+ this.mToday
				+ "' and EdorState='0' "
				+ " and not exists(select 1 from lredormain where edoracceptno=a.edoracceptno "
				+ " and cessstart>=trunc(DATE '" + mToday + "','YEAR') )";
		if(mContNo!=null && !mContNo.equals(""))
			tSql += " and exists(select 1 from lpedoritem where edoracceptno=a.edoracceptno and contno='"+mContNo+"')" ;

		// tSql=tSql+" and edorno='86330020030420000051'";
		System.out.println(tSql);
		LPEdorAppDB tLPEdorAppDB = new LPEdorAppDB();
		LPEdorAppSet tLPEdorAppSet = tLPEdorAppDB.executeQuery(tSql);

		for (int i = 1; i <= tLPEdorAppSet.size(); i++) {
			LPEdorAppSchema tPEdorAppSchema = tLPEdorAppSet.get(i);
			String sql = "select * from lppol p where exists(select 1 from lpedoritem where edoracceptno='"
				+tPEdorAppSchema.getEdorAcceptNo()+"' and edorno=p.edorno and contno=p.contno and ChgAmnt<>0 and edortype not in ('ZT','WT','GT'))"
				+" and exists(select 1 from lrpol where polno=p.polno) and edortype not in ('ZT','WT','GT')"
				+ ReportPubFun.getWherePart("riskcode", mRiskCode);
			sql += "union all select * from lppol p where exists(select 1 from lpedoritem where edoracceptno='"
				+tPEdorAppSchema.getEdorAcceptNo()+"' and edorno=p.edorno and contno=p.contno and edortype in ('ZT','WT','GT'))"
				+" and exists(select 1 from lrpol where polno=p.polno) and edortype in ('ZT','WT','GT')"
				+ ReportPubFun.getWherePart("riskcode", mRiskCode);
			System.out.println(tSql);			
			LPPolDB tLPPolDB = new LPPolDB();
			LPPolSet tLPPolSet = tLPPolDB.executeQuery(sql);

			for (int k = 1; k <= tLPPolSet.size(); k++) {
				LPPolSchema tLPPolSchema = tLPPolSet.get(k);

				double tChgAmnt = 0;
				double tChgPrem = 0;
				// 由于业务系统对退保保单的变得保额都为0，与分保处理有异，所以按分保处理，将退保的变得保额置为变化额
				if (tLPPolSchema.getEdorType().equals("ZT")
						|| tLPPolSchema.getEdorType().equals("GT")
						|| tLPPolSchema.getEdorType().equals("WT")) {
					tChgAmnt = 0 - tLPPolSchema.getAmnt();
					tChgPrem = 0 - tLPPolSchema.getPrem();
				} else {
					tChgAmnt = tPEdorAppSchema.getChgAmnt();
					tChgPrem = tPEdorAppSchema.getChgPrem();
				}
				LPEdorMainDB tLPEdorMainDB=new LPEdorMainDB();
				tLPEdorMainDB.setEdorAcceptNo(tPEdorAppSchema.getEdorAcceptNo());
				tLPEdorMainDB.setEdorNo(tLPPolSchema.getEdorNo());
				tLPEdorMainDB.setContNo(tLPPolSchema.getContNo());
				if(!tLPEdorMainDB.getInfo()){
					continue;
				}
				// 计算保全确认时的保单年度，即首年为第一保单年度
				int tInsureYear = PubFun.calInterval(tLPPolSchema
						.getCValiDate(), tLPEdorMainDB.getEdorValiDate(),
						"Y") + 1;
				// 查询在保全对应的保单年度的分保记录
				LRPolDB tLRPolDB = new LRPolDB();
				tLRPolDB.setPolNo(tLPPolSchema.getPolNo());
				tLRPolDB.setInsuredYear(tInsureYear);
				// tLRPolDB.setReinsureCom(this.mReinsureCom);
				LRPolSet tLRPolSet = tLRPolDB.query();
				Reflections tReflections = new Reflections();

				// 下面处理已经作过分保，之后保额发生变化的的分保记录
				// 分保结束日，分保的结束为下个保单年度生效对应日
				String tNextPerDay = PubFun.calDate(
						tLPPolSchema.getCValiDate(), tInsureYear, "Y", "");

				if (tChgAmnt != 0) // 本次保全引起保额变化
				{
					double tIntvl = PubFun.calInterval(tLPEdorMainDB
							.getEdorValiDate(), tNextPerDay, "D");
					// 循环在保全确认所在的保单年度的新单分保记录，计算每条分保记录由于本次保全应该变动的分保额和分保费
					// 其中分保费的变化＝新单分出保费×时间比例×保额比例
					for (int j = 1; j <= tLRPolSet.size(); j++) {
						LRPolSchema tLRPolSchema = new LRPolSchema();
						tLRPolSchema = tLRPolSet.get(j);
						LREdorMainSchema tLREdorMainSchema = new LREdorMainSchema();
						tReflections.transFields(tLREdorMainSchema,
								tLPEdorMainDB.getSchema());

						double tCessPeriod = PubFun
								.calInterval(tLRPolSchema.getCessStart(),
										tLRPolSchema.getCessEnd(), "D");
						double tReturnRate = tIntvl / tCessPeriod; // 时间比例
						// 365-old
						if (tReturnRate > 1)
							tReturnRate = 1;

						double tRRP1 = tReturnRate * tLRPolSchema.getCessPrem();
						
						//检验是否有按责任分保
						double[] rs = checkDutyCode(tLRPolSchema, tLPPolSchema.getEdorType(), tChgAmnt, tChgPrem);
						if(rs == null)
							continue;
						tChgAmnt = rs[0];
						tChgPrem = rs[1];
						
						// 计算本次变动保额与总保额的保额比例
						double tAmountRate = tChgAmnt / tLRPolSchema.getAmnt();
						// 计算变动的分保费和佣金
						double tRRP = tRRP1 * tAmountRate;
						double tRRC = tRRP * tLRPolSchema.getCessCommRate();
						// 准备LREDORMAIN数据，并保存
						tLREdorMainSchema.setEdorAcceptNo(tPEdorAppSchema.getEdorAcceptNo());
						tLREdorMainSchema.setPolNo(tLPPolSchema.getPolNo());
						tLREdorMainSchema.setEdorType(tLPPolSchema
								.getEdorType());
						tLREdorMainSchema.setChgPrem(tChgPrem);
						tLREdorMainSchema.setReinsureCom(tLRPolSchema
								.getReinsureCom());
						tLREdorMainSchema.setReinsurItem(tLRPolSchema
								.getReinsurItem());
						tLREdorMainSchema.setRiskCalSort(tLRPolSchema
								.getRiskCalSort());
						tLREdorMainSchema.setInsuredYear(tLRPolSchema
								.getInsuredYear());
						tLREdorMainSchema.setShRePrem(tRRP);
						tLREdorMainSchema.setShReComm(tRRC);
						tLREdorMainSchema.setChgCessAmt(tLRPolSchema
								.getCessionAmount()
								* tAmountRate);
						tLREdorMainSchema.setCessStart(tLREdorMainSchema
								.getEdorValiDate());
						tLREdorMainSchema.setCessEnd(tLRPolSchema.getCessEnd());
						tLREdorMainSchema.setMakeDate(PubFun.getCurrentDate());
						tLREdorMainSchema.setMakeTime(PubFun.getCurrentTime());
						tLREdorMainSchema
								.setModifyDate(PubFun.getCurrentDate());
						tLREdorMainSchema
								.setModifyTime(PubFun.getCurrentTime());
						tLREdorMainSchema.setTransSaleChnl(tLRPolSchema.getTransSaleChnl());
						tLREdorMainSchema.setDutyCode(tLRPolSchema.getDutyCode());
						LREdorMainDB tLREdorMainDB = new LREdorMainDB();
						tLREdorMainDB.setSchema(tLREdorMainSchema);
						if (!tLREdorMainDB.getInfo())
							this.mLREdorMainSet.add(tLREdorMainSchema);
					}
				}

				if (tLPPolSchema.getEdorType().equals("RE")) // 该判断与以上判断无交集
				{
					// 计算复效时对应的保单失效日期
					String tLapsDate = EdorCalZT.CalLapseDate(tLPPolSchema
							.getRiskCode(), tLPPolSchema.getPaytoDate());
					for (int j = 1; j <= tLRPolSet.size(); j++) {
						LRPolSchema tLRPolSchema = new LRPolSchema();
						tLRPolSchema = tLRPolSet.get(j);
						FDate tFDate = new FDate();
						// 只处理分保起始日期在失效日期之前的分保记录，即从时间的先后顺序看：分保开始－失效－复效－分保结束
						if (tFDate.getDate(tLRPolSchema.getCessStart()).before(
								tFDate.getDate(tLapsDate))) {
							LREdorMainSchema tLREdorMainSchema = new LREdorMainSchema();
							tReflections.transFields(tLREdorMainSchema,
									tPEdorAppSchema);
							// 保全确认日期到分保结束日期的天数
							double tIntv = PubFun.calInterval(tLREdorMainSchema
									.getEdorValiDate(), tNextPerDay, "D");
							double tCessPeriod = PubFun.calInterval(
									tLRPolSchema.getCessStart(), tLRPolSchema
											.getCessEnd(), "D");
							double tReturnRate = tIntv / tCessPeriod; // 时间比例
							// 365天-old
							if (tReturnRate > 1)
								tReturnRate = 1;
							// 应该补交分保费＝分保费×时间比例
							double tRRP = tLRPolSchema.getCessPrem()
									* tReturnRate;
							tLREdorMainSchema.setPolNo(tLPPolSchema.getPolNo());
							tLREdorMainSchema.setEdorType(tLPPolSchema
									.getEdorType());
							tLREdorMainSchema.setShRePrem(tRRP);
							double tRRC = tRRP * tLRPolSchema.getCessCommRate();
							tLREdorMainSchema.setShReComm(tRRC);
							tLREdorMainSchema.setReinsureCom(tLRPolSchema
									.getReinsureCom());
							tLREdorMainSchema.setReinsurItem(tLRPolSchema
									.getReinsurItem());
							tLREdorMainSchema.setRiskCalSort(tLRPolSchema
									.getRiskCalSort());
							tLREdorMainSchema.setChgCessAmt(tLRPolSchema
									.getCessionAmount());
							tLREdorMainSchema.setInsuredYear(tLRPolSchema
									.getInsuredYear());
							tLREdorMainSchema.setCessStart(tLREdorMainSchema
									.getEdorValiDate());
							tLREdorMainSchema.setCessEnd(tLRPolSchema
									.getCessEnd());
							tLREdorMainSchema.setMakeDate(PubFun
									.getCurrentDate());
							tLREdorMainSchema.setMakeTime(PubFun
									.getCurrentTime());
							tLREdorMainSchema.setModifyDate(PubFun
									.getCurrentDate());
							tLREdorMainSchema.setModifyTime(PubFun
									.getCurrentTime());
							tLREdorMainSchema.setTransSaleChnl(tLRPolSchema.getTransSaleChnl());
							tLREdorMainSchema.setDutyCode(tLRPolSchema.getDutyCode());
							LREdorMainDB tLREdorMainDB = new LREdorMainDB();
							tLREdorMainDB.setSchema(tLREdorMainSchema);
							if (!tLREdorMainDB.getInfo())
								this.mLREdorMainSet.add(tLREdorMainSchema);
						}
					}
				}

			}
			if (this.mLREdorMainSet.size() > 1000) {
				System.out.println("记录数：" + this.mLREdorMainSet.size());
				this.prepareData();
				CalEndorRetentBLS tCalEndorRetentBLS = new CalEndorRetentBLS();
				if (!tCalEndorRetentBLS.submitData(mInputData, this.mOperate)) {
					CError.buildErr(this, "数据提交失败!");
					return false;
				}
				mLREdorMainSet.clear();
			}

		}

		// 从set中将记录插入LREDORMAIN表中
		if (this.mLREdorMainSet.size() > 0) {
			System.out.println("记录数：" + this.mLREdorMainSet.size());
			this.prepareData();
			CalEndorRetentBLS tCalEndorRetentBLS = new CalEndorRetentBLS();
			if (!tCalEndorRetentBLS.submitData(mInputData, this.mOperate)) {
				CError.buildErr(this, "数据提交失败!");
				return false;
			}
		}

		return true;
	}

	private double[] checkDutyCode(LRPolSchema tLRPolSchema, String edortype,
			double oldChgAmnt, double oldChgPrem) {
		if (tLRPolSchema.getDutyCode().equals("000000"))
			return new double[] { oldChgAmnt, oldChgPrem };
		if (edortype.equals("ZT") || edortype.equals("GT")
				|| edortype.equals("WT")) {
			LCDutyDB tLCDutyDB = new LCDutyDB();
			tLCDutyDB.setPolNo(tLRPolSchema.getPolNo());
			tLCDutyDB.setDutyCode(tLRPolSchema.getDutyCode());
			if (tLCDutyDB.getInfo()) {
				return new double[] { -tLCDutyDB.getAmnt(), -tLCDutyDB.getPrem() };
			}
		}
		// 由于保全不支持到责任级别，所以除退保外的都不支持
		return null;
	}

	private void prepareData() {
		this.mInputData.clear();
		this.mResult.clear();
		this.mInputData.add(this.mLREdorMainSet);
		this.mResult.addElement(this.mLREdorMainSet);
	}

	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {
		CalEndorRetentBL tCalEndorRetentBL = new CalEndorRetentBL();
		VData vData = new VData();
		GlobalInput tG = new GlobalInput();

		tG.Operator = "001";
		tG.ManageCom = "86";
		String bdate = "2004-12-3";
		String edate = "2004-12-3";
		vData.addElement(tG);
		vData.addElement(bdate);
		vData.addElement(edate);
		vData.addElement("1001");
		tCalEndorRetentBL.submitData(vData, "CalEdor");

	}
}
