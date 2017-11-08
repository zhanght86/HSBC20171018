

package com.sinosoft.lis.reinsure;

import java.util.Date;

import com.sinosoft.lis.db.LAAgentDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LREdorMainDB;
import com.sinosoft.lis.db.LRPolDB;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.pubfun.ReportPubFun;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LREdorMainSchema;
import com.sinosoft.lis.schema.LRPolSchema;
import com.sinosoft.lis.vschema.LREdorMainSet;
import com.sinosoft.lis.vschema.LRPolSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class CalFillRetentBL {

	public CErrors mErrors = new CErrors();
	private GlobalInput mGlobalInput = new GlobalInput();
	private String mOperate;
	private VData mResult = new VData();
	private String mStartDate = "";
	private String mEndDate = "";
	private String mToday = "";
	private String mRiskCode = "";
	private String mContNo = ""; // 计算单张保单时使用
	Reflections mReflections = new Reflections();

	public CalFillRetentBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		System.out.println("beging CalFillRetentBL.......");
		this.mOperate = cOperate;
		try {
			if (!mOperate.equals("CalFill")) {
				CError.buildErr(this,  "不支持的操作字符串"+mOperate);
				return false;
			}

			// 得到外部传入的数据，将数据备份到本类中
			if (!getInputData(cInputData)) {
				return false;
			}
			mResult.clear();
			FDate chgdate = new FDate();
			Date dbdate = chgdate.getDate(mStartDate);
			Date dedate = chgdate.getDate(mEndDate);

			while (dbdate.compareTo(dedate) <= 0) {
				mToday = chgdate.getString(dbdate);
				if (!getFillData()) // 处理在该起止时间内的补名单保单，并将结果存入lrpol和lredormain
				{
					return false;
				}

				dbdate = PubFun.calDate(dbdate, 1, "D", null);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			CError.buildErr(this,  "发生异常"+ex.getMessage());
			return false;
		}
		return true;
	}

	private boolean getInputData(VData cInputData) {
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		TransferData mTransferData = (TransferData) cInputData
				.getObjectByObjectName("TransferData", 0);

		if (mTransferData == null) {
			CError.buildErr(this,  "没有得到足够的信息!");
			return false;
		}

		mStartDate = (String) mTransferData.getValueByName("StartDate");
		mEndDate = (String) mTransferData.getValueByName("EndDate");
		// mReinsureCom = (String)mTransferData.getValueByName("ReinsureCom");
		mContNo = (String) mTransferData.getValueByName("ContNo");
		mRiskCode = (String) mTransferData.getValueByName("RiskCode");

		if (mStartDate.equals("")) {
			CError.buildErr(this,  "没有起始日期!");
			return false;
		}

		if (mEndDate.equals("")) {
			CError.buildErr(this,  "没有终止日期!");
			return false;
		}

		if (mGlobalInput == null) {
			CError.buildErr(this,  "没有得到足够的信息!");
			return false;
		}
		return true;
	}

	private boolean getFillData() {
		// 查询当天的补名单
		String tSql = "select polno,grppolno from lcpol a where exists "
				+ " (select 1 from lcpol b where b.grpcontno = a.grpcontno "
				+ "	and poltypeflag = '1' and exists (select 1 from lrpol where polno = b.polno)) "
				+ "	and poltypeflag <> '1' and modifydate = '" + mToday + "'"
				+ " and not exists(select 1 from lrpol where polno=a.polno)";  //已经补过的单子不需再补

		tSql += ReportPubFun.getWherePart("contno", mContNo);
		tSql += ReportPubFun.getWherePart("riskcode", mRiskCode);

		SSRS tSSRS1 = new ExeSQL().execSQL(tSql);
		LRPolSet newLRPolSet=new LRPolSet();
		LREdorMainSet newLREdorMainSet=new LREdorMainSet();
		for (int i = 1; i <= tSSRS1.MaxRow; i++) {
			String polno = tSSRS1.GetText(i, 1);
			LCPolDB tLCPolDB=new LCPolDB();
			tLCPolDB.setPolNo(polno);
			// 查询出补名单
			tLCPolDB.getInfo();
			
			// 查询补名单的无名单
			String orapolno=new ExeSQL().getOneValue("select polno from lcpol where poltypeflag = '1' and grppolno='"+tSSRS1.GetText(i, 2)+"' ");
			LRPolDB tLRPolDB = new LRPolDB();
			// 查询分保记录
			LRPolSet tLRPolSet = tLRPolDB
					.executeQuery("select * from lrpol where polno='" + orapolno+ "'");
			if (tLRPolSet.size() < 1) {
				System.out.println("error lrpol in caledor " + polno);
				continue;
			}
			for (int j = 1; j <= tLRPolSet.size(); j++) {
				LRPolSchema tLRPolSchema = tLRPolSet.get(j);
				
				double premrate=tLCPolDB.getPrem() / tLRPolSchema.getPrem();
				double amntrate=tLCPolDB.getAmnt() / tLRPolSchema.getAmnt();
				
				// 构造新lrpol
				LRPolSchema nLRPolSchema=tLRPolSchema.getSchema();
				mReflections.transFields(nLRPolSchema, tLCPolDB.getSchema());
				nLRPolSchema.setOldPolNo(nLRPolSchema.getPolNo());
				nLRPolSchema.setCessComm(PubFun.round(tLRPolSchema.getCessComm() * premrate,2));
				nLRPolSchema.setCessionAmount(PubFun.round(tLRPolSchema.getCessionAmount() * amntrate,2));
				nLRPolSchema.setCessPrem(PubFun.round(tLRPolSchema.getCessPrem() * premrate,2));
				nLRPolSchema.setNowRiskAmount(PubFun.round(tLRPolSchema.getNowRiskAmount() * amntrate, 2));
				
				nLRPolSchema.setTransSaleChnl(calTransSaleChnl(tLCPolDB));				
				nLRPolSchema.setMakeDate(PubFun.getCurrentDate());
				nLRPolSchema.setMakeTime(PubFun.getCurrentTime());
				nLRPolSchema.setModifyDate(PubFun.getCurrentDate());
				nLRPolSchema.setModifyTime(PubFun.getCurrentTime());
				
				LRPolDB ttLRPolDB = new LRPolDB();
				ttLRPolDB.setPolNo(nLRPolSchema.getPolNo());
				ttLRPolDB.setReinsureCom(nLRPolSchema.getReinsureCom());
				ttLRPolDB.setInsuredYear(nLRPolSchema.getInsuredYear());
				ttLRPolDB.setReinsurItem(nLRPolSchema.getReinsurItem());
				ttLRPolDB.setRiskCalSort(nLRPolSchema.getRiskCalSort());
				if(ttLRPolDB.getCount()>0)
					continue;
				
	
				LREdorMainSchema nLREdorMainSchema=new LREdorMainSchema();
				mReflections.transFields(nLREdorMainSchema, nLRPolSchema);
				nLREdorMainSchema.setEdorType("LB"); // 虚拟'LB'的保全项目，表示补名单
				nLREdorMainSchema.setEdorValiDate(nLRPolSchema.getSignDate());
				nLREdorMainSchema.setConfDate(nLRPolSchema.getSignDate());
				nLREdorMainSchema.setChgCessAmt(-nLRPolSchema.getCessionAmount());
				nLREdorMainSchema.setShRePrem(-nLRPolSchema.getCessPrem());
				nLREdorMainSchema.setShReComm(-nLRPolSchema.getCessComm());
				nLREdorMainSchema.setOperator(this.mGlobalInput.Operator);

				LREdorMainDB ttLREdorMainDB = new LREdorMainDB();
				ttLREdorMainDB.setPolNo(nLRPolSchema.getPolNo());
				ttLREdorMainDB.setEdorType("LB");
				ttLREdorMainDB.setReinsureCom(nLRPolSchema.getReinsureCom());
				ttLREdorMainDB.setReinsurItem(nLRPolSchema.getReinsurItem());
				ttLREdorMainDB.setInsuredYear(nLRPolSchema.getInsuredYear());
				ttLREdorMainDB.setRiskCalSort(nLRPolSchema.getRiskCalSort());
				ttLREdorMainDB.setCessStart(nLRPolSchema.getCessStart());
				if (ttLREdorMainDB.getCount() > 0)
					continue;
				
				newLRPolSet.add(nLRPolSchema);
				nLREdorMainSchema.setEdorNo(PubFun1.CreateMaxNo("SERIALNO",	""));
				nLREdorMainSchema.setEdorAcceptNo(nLREdorMainSchema.getEdorNo());
				newLREdorMainSet.add(nLREdorMainSchema);
				
				if (newLRPolSet.size() > 1000) {
					PubSubmit tPubSubmit=new PubSubmit();
					VData tVData=new VData();
					MMap tMMap=new MMap();
					tMMap.put(newLRPolSet, "INSERT");
					tMMap.put(newLREdorMainSet, "INSERT");
					tVData.add(tVData);
					if(!tPubSubmit.submitData(tVData)){
						System.out.println("CalFillRetentBL PubSubmit error");
						CError.buildErr(this, "CalFillRetentBL PubSubmit error");
						return false;
					}
					newLRPolSet.clear();
					newLREdorMainSet.clear();
				}
			}
		}
		if (newLRPolSet.size() > 0) {
			PubSubmit tPubSubmit=new PubSubmit();
			VData tVData=new VData();
			MMap tMMap=new MMap();
			tMMap.put(newLRPolSet, "INSERT");
			tMMap.put(newLREdorMainSet, "INSERT");
			tVData.add(tMMap);
			if(!tPubSubmit.submitData(tVData)){
				CError.buildErr(this, "CalFillRetentBL PubSubmit error");
				return false;
			}
		}
		return true;
	}
	
	private String calTransSaleChnl(LCPolSchema tLCPolSchema) {
		String TransSaleChnl = tLCPolSchema.getSaleChnl();
		if ("01".equals(TransSaleChnl)) {
			String sql = "select count(*) from laagent where branchtype = '2' and 'LB' = substr(name, 1, 2) and agentcode='"
					+ tLCPolSchema.getAgentCode() + "'";
			LAAgentDB tLAAgentDB = new LAAgentDB();
			tLAAgentDB.setAgentCode(tLCPolSchema.getAgentCode());
			String r = new ExeSQL().getOneValue(sql);
			if (!"0".equals(r)) {
				TransSaleChnl = "07";
			}
		} else if ("00000000000000000000".equals(tLCPolSchema.getGrpPolNo())
				&& "05,06,08,09,".indexOf(tLCPolSchema.getSaleChnl() + ",") >= 0) {
			if (tLCPolSchema.getAgentCom() != null
					&& (tLCPolSchema.getAgentCom().length() == 13 || tLCPolSchema
							.getAgentCom().length() == 16)
					&& tLCPolSchema.getAgentCom().startsWith("86")
					&& tLCPolSchema.getAgentCom().substring(8, 9).equals("8"))
				TransSaleChnl = "07";
		}
		return TransSaleChnl;
	}

	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {
		VData vData = new VData();
		GlobalInput tG = new GlobalInput();

		tG.Operator = "001";
		tG.ManageCom = "86";
		String bdate = "2009-12-2";
		String edate = bdate;
		String contno="130330001123032";
		vData.addElement(tG);
		TransferData tTransferData=new TransferData();
		tTransferData.setNameAndValue("StartDate", bdate);
		tTransferData.setNameAndValue("EndDate", edate);
//		tTransferData.setNameAndValue("ContNo", contno);
		vData.addElement(tTransferData);
		
		CalFillRetentBL tCalFillRetentBL=new CalFillRetentBL();
		tCalFillRetentBL.submitData(vData, "CalFill");
	}
}
