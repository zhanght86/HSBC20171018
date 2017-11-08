package com.sinosoft.lis.tb;
import org.apache.log4j.Logger;

//import utils.system;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.sinosoft.ibrms.RuleUI;
import com.sinosoft.ibrms.SQLTaskResult;
import com.sinosoft.ibrms.bom.BOMAgent;
import com.sinosoft.ibrms.bom.BOMAppnt;
import com.sinosoft.ibrms.bom.BOMBnf;
import com.sinosoft.ibrms.bom.BOMCalInsured;
import com.sinosoft.ibrms.bom.BOMCont;
import com.sinosoft.ibrms.bom.BOMInsured;
import com.sinosoft.ibrms.bom.BOMMainPol;
import com.sinosoft.ibrms.bom.BOMPol;
import com.sinosoft.ibrms.bom.BOMSubPol;
import com.sinosoft.ibrms.bom.BOMSubPol2;
import com.sinosoft.lis.db.LAAgentDB;
import com.sinosoft.lis.db.LCAppntDB;
import com.sinosoft.lis.db.LCBnfDB;
import com.sinosoft.lis.db.LCCUWErrorDB;
import com.sinosoft.lis.db.LCCUWMasterDB;
import com.sinosoft.lis.db.LCCUWSubDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCIndUWErrorDB;
import com.sinosoft.lis.db.LCIndUWMasterDB;
import com.sinosoft.lis.db.LCIndUWSubDB;
import com.sinosoft.lis.db.LCInsuredDB;
import com.sinosoft.lis.db.LCIssuePolDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LCUWErrorDB;
import com.sinosoft.lis.db.LCUWMasterDB;
import com.sinosoft.lis.db.LCUWSubDB;
import com.sinosoft.lis.db.LDOccupationDB;
import com.sinosoft.lis.db.LDUserDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.LDExch;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LAAgentSchema;
import com.sinosoft.lis.schema.LCAppntSchema;
import com.sinosoft.lis.schema.LCBnfSchema;
import com.sinosoft.lis.schema.LCCUWErrorSchema;
import com.sinosoft.lis.schema.LCCUWMasterSchema;
import com.sinosoft.lis.schema.LCCUWSubSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCIndUWErrorSchema;
import com.sinosoft.lis.schema.LCIndUWMasterSchema;
import com.sinosoft.lis.schema.LCIndUWSubSchema;
import com.sinosoft.lis.schema.LCInsuredSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LCUWErrorSchema;
import com.sinosoft.lis.schema.LCUWMasterSchema;
import com.sinosoft.lis.schema.LCUWSubSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.tb.GlobalCheckSpot;
import com.sinosoft.lis.tb.ProductSaleControlBL;
import com.sinosoft.lis.vschema.LCBnfSet;
import com.sinosoft.lis.vschema.LCCUWErrorSet;
import com.sinosoft.lis.vschema.LCCUWMasterSet;
import com.sinosoft.lis.vschema.LCCUWSubSet;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LCIndUWErrorSet;
import com.sinosoft.lis.vschema.LCIndUWMasterSet;
import com.sinosoft.lis.vschema.LCIndUWSubSet;
import com.sinosoft.lis.vschema.LCInsuredSet;
import com.sinosoft.lis.vschema.LCIssuePolSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCUWErrorSet;
import com.sinosoft.lis.vschema.LCUWMasterSet;
import com.sinosoft.lis.vschema.LCUWSubSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.SysConst;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;


/**
 * <p>
 * Title: 自动核保校验业务类
 * </p>
 * 
 * <p>
 * Description: 自动核保校验业务类
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author HL
 * @version 6.0
 */
public class PrepareBOMUWBL {
private static Logger logger = Logger.getLogger(PrepareBOMUWBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private static GlobalCheckSpot mGlobalCheckSpot = GlobalCheckSpot.getInstance();
	/** 往界面传输数据的容器 */
	MMap mMap = new MMap();
	private VData mResult = new VData();

	/** 往工作流引擎中传输数据的容器 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private TransferData mTransferData = new TransferData();
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private ExeSQL mExeSQL = new ExeSQL();
	private String theDate = "";

	private List tPolList = new ArrayList();//存放险种自核不通过信息的list
	private List tContList = new ArrayList();//存放合同自核不通过提示信息的list
	private List tInsList = new ArrayList();//存放被保人自核不通过提示信息的list
	
	
	private List mBomList = new ArrayList();
	
	/** 数据操作字符串 */
	private String mOperator;
	private String mManageCom;
	private String mAllPolPassFlag = "9";
	/** 业务数据操作字符串 */
	// private String mMissionID;
	/** 保单表 */
	private LCContSchema mLCContSchema = new LCContSchema();
	private String mPolPassFlag = "0"; // 险种通过标记 初始为未核保
	private String mContPassFlag = "0"; // 合同通过标记 初始为未核保
	private String mInsPassFlag = "0";
	private String mUWGrade = "";//规则引擎接口返回的核保级别
	private String mContNo = "";
	private String mPContNo = "";
	private String mOldPolNo = "";//获得的保单号码 
	private boolean FirstUW = true;
	private String ProductSaleFlag = "0";//产品上市停售规则标记，如果不符合规则则置为1
	private int LCCUWNO=0;
	private int LCUWNO=0;
	private int LCINDUWNO=0;
	private int LCBatchNo=0;
	private int LCCBatchNo=0;
	private int LCIndBatchNo=0;
	
	private LCContSet mAllLCContSet = new LCContSet();
	private LCPolSet mAllLCPolSet = new LCPolSet();
	private LCInsuredSet mAllInsuredSet = new LCInsuredSet();

	/** 2008-11-20，自动核保改调用保险业务规则管理功能来完成自动核保*/
	private RuleUI mRuleUI = new RuleUI();//
	
	/** 初始化保险业务规则管理所需要的相应的数据*/
	
	/** 合同核保主表 */
	private LCCUWMasterSet mLCCUWMasterSet = new LCCUWMasterSet();
	private LCCUWMasterSet mAllLCCUWMasterSet = new LCCUWMasterSet();

	/** 合同核保子表 */
	private LCCUWSubSet mLCCUWSubSet = new LCCUWSubSet();
	private LCCUWSubSet mAllLCCUWSubSet = new LCCUWSubSet();

	/** 合同核保错误信息表 */
	private LCCUWErrorSet mLCCUWErrorSet = new LCCUWErrorSet();
	private LCCUWErrorSet mAllLCCUWErrorSet = new LCCUWErrorSet();

	/** 各险种核保主表 */
	private LCUWMasterSet mLCUWMasterSet = new LCUWMasterSet();
	private LCUWMasterSet mAllLCUWMasterSet = new LCUWMasterSet();

	/** 各险种核保子表 */
	private LCUWSubSet mLCUWSubSet = new LCUWSubSet();
	private LCUWSubSet mAllLCUWSubSet = new LCUWSubSet();

	/** 被保人核保主表*/
	private LCIndUWMasterSet mLCIndUWMasterSet = new LCIndUWMasterSet();
	
	/** 被保人核保子表*/
	private LCIndUWSubSet mLCIndUWSubSet = new LCIndUWSubSet();
	
	/** 被保人核保错误信息表*/
	private LCIndUWErrorSet mLCIndUWErrorSet = new LCIndUWErrorSet();
	
	/** 核保错误信息表 */
	private LCUWErrorSet mLCUWErrorSet = new LCUWErrorSet();

	
	/** 发送拒保通知书 */
	LOPRTManagerSchema mLOPRTManagerSchema = new LOPRTManagerSchema();
	private String hierarhy = ""; // 核保级别 add by yaory
	private String reDistribute = ""; // 分保标志 add by yaory
	private double LSumDangerAmnt = 0; // 同一被保险人下寿险类的累计危险保额add by yaory
	private double DSumDangerAmnt = 0; // 同一被保险人下重大疾病类的累计危险保额add by yaory
	private double ASumDangerAmnt = 0; // 同一被保险人下人身意外伤害类的累计危险保额add by yaory
	private double MSumDangerAmnt = 0; // 同一被保险人下人身意外医疗类的累计危险保额add by yaory
	private double SSumDangerAmnt = 0; // 同一被保险人下住院医疗类的累计危险保额add by yaory
	private double SSumDieAmnt = 0; // 同一被保险人下累计身故风险保额
	private double AllSumAmnt = 0;
	private String mBankInsu = "0";
	private int insuredCount=0;
	private boolean HasAddFreeFlag = false;
	private String locCurrency = "";//本币信息
	private String theCurrentDate = PubFun.getCurrentDate();
	private String theCurrentTime = PubFun.getCurrentTime();
//	private int theCount = 0;
	public PrepareBOMUWBL() {
	}


	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 * 
	 * @param tLCContSchema
	 *            输入数据
	 * @return boolean
	 */
	public List dealData(LCContSchema tLCContSchema,LCPolSchema tLCPolSchema) {
		/** 将该合同下的所有信息查询出来,并将相应的数据转储到相应的BOM中*/
		try {
			mContNo = tLCContSchema.getContNo(); // 获得保单号
			mPContNo = tLCContSchema.getProposalContNo();
			
			//LCAppntSchema tLCAppntSchema = new LCAppntSchema();
			LCAppntDB tLCAppntDB = new LCAppntDB();
			tLCAppntDB.setContNo(mContNo);
			if(!tLCAppntDB.getInfo()){
				//CError.buildErr(this, "查询投保人信息失败！");
				return this.mBomList;
			}
				
			//LCInsuredSchema tLCInsuredSchema = new LCInsuredSchema();
			LCInsuredDB tLCInsuredDB = new LCInsuredDB();
			tLCInsuredDB.setContNo(mContNo);
			mAllInsuredSet=tLCInsuredDB.query();
			LCInsuredSet tFreeLCInsuredSet = new LCInsuredSet();

			tLCInsuredDB.setInsuredNo(tLCPolSchema.getInsuredNo());
			LCInsuredSchema tLCInsuredSchema = tLCInsuredDB.query().get(1);
						
			LCInsuredDB tFreeLCInsuredDB = new LCInsuredDB();
			insuredCount=mAllInsuredSet.size();
		
			LCBnfSet tLCBnfSet = new LCBnfSet();
			LCBnfDB tLCBnfDB = new LCBnfDB();
			tLCBnfDB.setContNo(mContNo);
			tLCBnfDB.setPolNo(tLCPolSchema.getPolNo());
			tLCBnfDB.setInsuredNo(tLCPolSchema.getInsuredNo());
			tLCBnfSet = tLCBnfDB.query();
			int bnfCount = tLCBnfSet.size();
				
			LAAgentDB tLAAgentDB = new LAAgentDB();
			tLAAgentDB.setAgentCode(tLCContSchema.getAgentCode());
			boolean tAgent = true;
			if(!tLAAgentDB.getInfo()){
				logger.debug("未查询到代理人信息！");
				tAgent = false;
			}
			LCPolDB tLCPolDB=new LCPolDB();
			String tMainPolSql = "select * from lcpol where mainpolno='"+"?mainpolno?" +"' and contno='?mContNo?' and insuredno='"+"?insuredno?"+"'";
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql(tMainPolSql);
			sqlbv.put("mainpolno", tLCPolSchema.getMainPolNo());
			sqlbv.put("mContNo", mContNo);
			sqlbv.put("insuredno", tLCPolSchema.getInsuredNo());
			LCPolSet tmainLCPolSet = new LCPolSet();
			tmainLCPolSet=tLCPolDB.executeQuery(sqlbv);
			int mainCount=tmainLCPolSet.size();
				
			String tSubPolSql = "select * from lcpol where polno='"+"?polno?"+"'  and mainpolno!='"+"?mainpolno?"+"' and contno='?mContNo?' and insuredno='"+"?insuredno?"+"'";
			SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
			sqlbv1.sql(tSubPolSql);
			sqlbv1.put("polno", tLCPolSchema.getPolNo());
			sqlbv1.put("mainpolno", tLCPolSchema.getPolNo());
			sqlbv1.put("mContNo", mContNo);
			sqlbv1.put("insuredno", tLCPolSchema.getInsuredNo());
			LCPolSet tSubLCPolSet = new LCPolSet();
			tSubLCPolSet = tLCPolDB.executeQuery(sqlbv1);
			int subCount=tSubLCPolSet.size();
				
			//准备BOMCont数据
			BOMCont cont = new BOMCont();
			cont = DealBOMCont(tLCContSchema);
			mBomList.add(cont);
			//准备被保人BOMAppnt数据
			BOMAppnt appnt = new BOMAppnt();
				
			//准备投保人
			appnt = DealBOMAppnt(tLCAppntDB.getSchema(),tLCContSchema);
			mBomList.add(appnt);
				
			//准备代理人BOMAgent数据
			BOMAgent agent = new BOMAgent();
			if(tAgent){
				agent = DealBOMAgent(tLAAgentDB.getSchema());
			}
			mBomList.add(agent);
				
			//准备被保人BOMInsured数据
			BOMInsured insured = new  BOMInsured();//为适用多被保人
			insured = DealBOMInsured(tLCInsuredSchema,tLCContSchema);
			mBomList.add(insured);
				
			//准备险种BOMPol数据
			BOMPol pols = new BOMPol();//多个险种
			pols = DealBOMPol(tLCPolSchema,insured);
			mBomList.add(pols);
				
			//准备受益人BOMBnf数据
			BOMBnf bnf = new BOMBnf();//多个受益人
			if(bnfCount > 0) {
				bnf = DealBOMBnf(tLCBnfSet.get(1),insured);
				mBomList.add(bnf);
			}
				
			//准备主险BOMMainPol数据
			BOMMainPol mainpol = new BOMMainPol();//多主险
			mainpol=DealBOMMainPol(tmainLCPolSet.get(1),insured);
			mBomList.add(mainpol);
				
			//处理附加险，规则引擎需要传入两个附加险的BOM，以对应录入磁条的“附加险”、“另一附加险”，而传入的两个附加险BOM只需一样就可以
			BOMSubPol subpol = new BOMSubPol();	//多个附加险
			if(subCount>0)
			{
				
				subpol=DealBOMSubPol(tSubLCPolSet.get(1),mainpol);
				mBomList.add(subpol);
			}
		    BOMCalInsured calInsured=new BOMCalInsured();
		    calInsured = DealCalInsured(tLCInsuredSchema,tLCContSchema,insured);
		    mBomList.add(calInsured);
			
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return this.mBomList;
		}
	
		return this.mBomList;
	}

	private BOMCalInsured DealCalInsured(LCInsuredSchema tLCInsuredSchema,
			LCContSchema tLCContSchema,BOMInsured bomInsured) {
		
		BOMCalInsured bomCalInsured = new BOMCalInsured();
		bomCalInsured.setAppAge(bomInsured.getInsuredAppAge());
		return bomCalInsured;
	}


	private BOMCont DealBOMCont(LCContSchema tLCContSchema){
		BOMCont cont = new BOMCont();
		try{
			//tongmeng 2009-02-19 modify
			//卡单取录入时间
			String tScanSql = "select MakeDate,maketime from ES_DOC_MAIN where doccode='"+"?mContNo?"+"' "
			                + " and busstype='TB' and subtype='UA001' ";
			SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
			sqlbv2.sql(tScanSql);
			sqlbv2.put("mContNo", mContNo);
			ExeSQL tExeSQL = new ExeSQL();
			SSRS tSSRS = tExeSQL.execSQL(sqlbv2);
			String tScanDate = null;
			String tScanTime = null;
			if(tSSRS.getMaxRow()>0)
			{
				tScanDate = tSSRS.GetText(1, 1);//扫描日期
				tScanTime = tSSRS.GetText(1, 2);
			}
			else
			{
				String tNoScanSql = "select makedate,maketime  from  lccont where contno='"+"?mContNo?"+"' ";
				SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
				sqlbv3.sql(tNoScanSql);
				sqlbv3.put("mContNo", mContNo);
				tSSRS = tExeSQL.execSQL(sqlbv3);
				tScanDate = tSSRS.GetText(1, 1);
				tScanTime = tSSRS.GetText(1, 2);
			}
			logger.debug("扫描日期: "+tScanDate+" "+tScanTime);
			LCIssuePolSet tLCIssuePolSet = new LCIssuePolSet();
			LCIssuePolDB tLCIssuePolDB = new LCIssuePolDB();
			//tLCIssuePolDB.setContNo(mContNo);
			//tongmeng 2009-04-27 modify
			//问题件只校验未发放的/
			String tSQL_Issue = "select * from lcissuepol where contno='"+"?contno?"+"'  "
			                  + " and state is null and needprint='Y'";
			SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
			sqlbv4.sql(tSQL_Issue);
			sqlbv4.put("contno", this.mContNo);
			tLCIssuePolSet = tLCIssuePolDB.executeQuery(sqlbv4);
			cont.setSellType(tLCContSchema.getSellType());//出单方式
			//tongmeng 2009-02-19
			//问题件信息先设置默认值
			cont.setComIssueFlag("0");//机构问题件标记
			cont.setCustomerIssueFlag("0");//客户问题件标记
			cont.setAppntIssueFlag("0");//投保人问题件标记
			cont.setAgentIssueFlag("0");//业务员问题件标记
			if(tLCIssuePolSet.size()>0){
				for(int i=1;i<=tLCIssuePolSet.size();i++){
					if("1".equals(tLCIssuePolSet.get(i).getBackObjType())){
						cont.setComIssueFlag("1");
					}
					else if("2".equals(tLCIssuePolSet.get(i).getBackObjType())){
						cont.setCustomerIssueFlag("1");
						if("0".equals(tLCIssuePolSet.get(i).getQuestionObj())){
							cont.setAppntIssueFlag("1");
						}
					}
					else if("3".equals(tLCIssuePolSet.get(i).getBackObjType())){
						cont.setAgentIssueFlag("1");
					}
					else if("4".equals(tLCIssuePolSet.get(i).getBackObjType())){
						cont.setComIssueFlag("1");
					}
				}
			}
			
			//tongmeng 2009-03-12 modify
			//合同级别的保额,保费,份数取pol的汇总值
//			String tSQL = "select nvl(sum(prem),0),nvl(sum(amnt),0),nvl(sum(mult),1) from lcpol where "
//				        + " contno='"+tLCContSchema.getContNo()+"' "
//				        + " and (uwflag is null or uwflag not in ('1','2','a'))";
			//录单时已对保单下多币种进行转换，此处直接取保单表即可
//			String tSQL = "select (case when prem is not null then prem else 0 end),(case when amnt is not null then amnt else 0 end),(case when mult is not null then mult else 1 end) from lccont a where "
//		        + " contno='"+"?contno?"+"' "
//		        + " and exists (select 1 from lcpol where contno=a.contno and (uwflag is null or uwflag not in ('1','2','a')))";
//			SQLwithBindVariables sqlbv5=new SQLwithBindVariables();
//			sqlbv5.sql(tSQL);
//			sqlbv5.put("contno", tLCContSchema.getContNo());
//			ExeSQL tAmntExeSQL = new ExeSQL();
//			SSRS tAmntSSRS = new SSRS();
//			tAmntSSRS = tAmntExeSQL.execSQL(sqlbv5);
			String tSumAmnt = "0";
			String tSumPrem = "0";
			String tSumMult = "0";
//			if(tAmntSSRS.getMaxRow()>0)
//			{
//				tSumPrem = tAmntSSRS.GetText(1,1);
//				tSumAmnt = tAmntSSRS.GetText(1,2);
//				tSumMult = tAmntSSRS.GetText(1,3);
//			}
//			if(!(tLCContSchema.getCValiDate()==null||"".equals(tLCContSchema.getCValiDate()))){
//				theDate=tLCContSchema.getCValiDate()+" 00:00:00";
//				cont.setCvalidate(sdf.parse(theDate));
//			}
			cont.setAmnt(new Double(tSumAmnt));
			cont.setAutoPayFlag(tLCContSchema.getAutoPayFlag());
			cont.setBankAccNo(tLCContSchema.getBankAccNo());
			cont.setBankCode(tLCContSchema.getBankCode());
			cont.setCardFlag(tLCContSchema.getCardFlag());
			cont.setContNo(tLCContSchema.getContNo());
			//获得责任终止日期
//			String tSQL_EndDate = " select concat(cast(to_char(max(enddate), 'yyyy-mm-dd') as char(100)),' 00:00:00'),(case when max(payintv/1) is not null then max(payintv/1) else 0 end) from lcpol a where contno='"+"?mContNo?"+"' " 
//                        	    + " and exists (select '1' from lmriskapp where subriskflag='M' "
//                        	    + " and riskcode=a.riskcode) and mainpolno=polno ";
//			SQLwithBindVariables sqlbv6=new SQLwithBindVariables();
//			sqlbv6.sql(tSQL_EndDate);
//			sqlbv6.put("mContNo", mContNo);
//			ExeSQL tExeSQL_EndDate = new ExeSQL();
//			SSRS tSSRS_EndDate = new SSRS();
//			tSSRS_EndDate = tExeSQL_EndDate.execSQL(sqlbv6);
			String tEndDate = PubFun.getCurrentDate() +" 00:00:00";;
			String tPayIntv = "";
//			if(tSSRS_EndDate.getMaxRow()>0)
//			{
//				tEndDate = tSSRS_EndDate.GetText(1,1);
//				tPayIntv = tSSRS_EndDate.GetText(1,2);
//			}
			//String tEndDate = tExeSQL_EndDate.getOneValue(tSQL_EndDate);
			if(tEndDate!=null&&!tEndDate.equals(""))
			{
				cont.setEndDate(sdf.parse(tEndDate));//责任终止日期
			}
			//强制人工核保标志
			cont.setForceUWFlag(tLCContSchema.getForceUWFlag());
			cont.setInsuredPeoples(Double.valueOf(String.valueOf(insuredCount)));
			if(!((tLCContSchema.getMakeDate()==null)||"".equals(tLCContSchema.getMakeDate()))){
				theDate = tLCContSchema.getMakeDate()+" "+tLCContSchema.getMakeTime();
				cont.setMakeDate(sdf.parse(theDate));
//				logger.debug("日期为："+theDate+"   转换后的："+sdf.parse(theDate));
			}
			cont.setManageCom(tLCContSchema.getManageCom());
			cont.setMult(new Double(tSumMult));
			cont.setOutPayFlag(tLCContSchema.getOutPayFlag());
			//tongmeng 2009-03-25 modify
			//缴费间隔取保单下主险的最大缴费年期.
			//String tSQL_PayIntv = "select nvl(max(payintv/1),0) from lcpol where contno='"+tLCContSchema.getContNo()+"' and mainpolno=polno";
			//ExeSQL tExeSQL_PayIntv = new ExeSQL();
			//String tValue = "";
			//tValue = tExeSQL_PayIntv.getOneValue(tSQL_PayIntv);
			if(tPayIntv==null||tPayIntv.equals(""))
			{
				cont.setPayIntv("0");
			}
			else
			{
				cont.setPayIntv(tPayIntv);
			}
			//tongmeng 2009-04-22 modify
			//首期或者续期如果有一个为4(银行转账),就赋值为银行转账
			String tNewPayMode = tLCContSchema.getNewPayMode();
			String tPayMode = tLCContSchema.getPayLocation();
			String tAccName = "";
			if(tNewPayMode!=null&&tNewPayMode.equals("0")){
				cont.setPayMode("0");
				tAccName = tLCContSchema.getNewAccName();
			}else if(tPayMode!=null&&tPayMode.equals("0")) {
				 cont.setPayMode("0");
				 tAccName = tLCContSchema.getAccName();
			}
			else
			{
				cont.setPayMode(tLCContSchema.getPayMode());
			}
			cont.setAccName(tAccName);
			if(!((tLCContSchema.getPolApplyDate()==null)||"".equals(tLCContSchema.getPolApplyDate()))){
				theDate=tLCContSchema.getPolApplyDate()+" 00:00:00";
				cont.setPolApplyDate(sdf.parse(theDate));
				logger.debug(sdf.parse(theDate));
			}
			cont.setPrem(new Double(tSumPrem));
			if(!(tLCContSchema.getRemark()==null||"".equals(tLCContSchema.getRemark()))){
				cont.setRemarkFlag("1");
			}else{
				cont.setRemarkFlag("0");
			}
			cont.setRnewFlag(String.valueOf(tLCContSchema.getRnewFlag()));
			cont.setSaleChnl(tLCContSchema.getSaleChnl());
			//如果时间为空则默认为00:00:00
			if(!((tScanDate==null)||"".equals(tScanDate))){
				if(!((tScanTime==null)||"".equals(tScanTime))){
					theDate=tScanDate+" "+tScanTime;
				} else {
					theDate=tScanDate+" 00:00:00";
				}
				cont.setScanDate(sdf.parse(theDate));//扫描日期
			}
//			String tOldPayPremSql="select (case when sum(InputPrem) is null then 0 else sum(InputPrem) end)"
//									+"from lcpoloriginal where contno ='"+"?mContNo?"+"'";
//			SQLwithBindVariables sqlbv7=new SQLwithBindVariables();
//			sqlbv7.sql(tOldPayPremSql);
//			sqlbv7.put("mContNo", mContNo);
			//String OldPayPrem = tExeSQL.getOneValue(sqlbv7);
			cont.setContPrem(Double.valueOf("0"));//填单保费
			cont.setRealPrem(new Double(tSumPrem));//应交保费
//			String tSQL_TempFee = "select (case when sum(paymoney) is not null then sum(paymoney) else 0 end) from ljtempfee where tempfeetype='1' "
//				                + " and otherno='"+"?mContNo?"+"'";
//			SQLwithBindVariables sqlbv8=new SQLwithBindVariables();
//			sqlbv8.sql(tSQL_TempFee);
//			sqlbv8.put("mContNo", mContNo);
//			String tTempFee = tExeSQL.getOneValue(sqlbv8);
			cont.setTempfee(new Double("0"));//暂收保费
			cont.setHospitalCodePro("0");
//
//			//2009-2-11 ln add  体检医院名称与系统定义不一致
//			String tSqlNew=" select count(1) from lcpenotice m where 1=1 and contno='"+"?mContNo?"+"'"
//			    //+ " and hospitcode is not null "
//				+ " and not exists (select 1 from ldhospital where hospitcode=m.hospitcode and trim(hospitalname)=m.peaddress "
//				+ " and (hosstate is null or hosstate='0'))"
//				;
//			SQLwithBindVariables sqlbv9=new SQLwithBindVariables();
//			sqlbv9.sql(tSqlNew);
//			sqlbv9.put("mContNo", mContNo);
//			String tValue_Hos = "";
//			tValue_Hos = tExeSQL.getOneValue(sqlbv9);
//			if(tValue_Hos!=null&&!tValue_Hos.equals("")
//					&&Integer.parseInt(tValue_Hos)>0)
//			{
//				cont.setHospitalCodePro("1");//体检医院名称与系统定义不一致
//			}else{
//				cont.setHospitalCodePro("0");
//			}	
			
			//2009-2-11 ln add  个险渠道有同一业务员且同一险种且同一扫描日期的保单关联份数
//			tSqlNew="select (case when max(c) is not null then max(c) else 0 end) from(select count(distinct(contno)) as c"
//				+ " from ("
//				+ " select contno,riskcode from lcpol l where 1=1" 
//				+ " and salechnl='02' "
//				+ " and agentcode = '"+ "?agentcode?" +"' "
//				+ " and exists (select 1 from es_doc_main where doccode=l.prtno "
//				+ " and busstype='TB' and subtype='UA001' " 
//				+ " and makedate=(select min(makedate) from es_doc_main where doccode='"+ "?doccode?" +"' "
//			    + " and busstype='TB' and subtype='UA001')) "
//				+ " and riskcode in (select distinct riskcode from lcpol where contno='"+ "?mContNo?" +"')) g group by riskcode) t"
//				;
////			SQLwithBindVariables sqlbv10=new SQLwithBindVariables();
////			sqlbv10.sql(tSqlNew);
//			sqlbv10.put("agentcode", tLCContSchema.getAgentCode());
//			sqlbv10.put("doccode", tLCContSchema.getPrtNo());	
//			sqlbv10.put("mContNo", mContNo);
//			String tResultNew = tExeSQL.getOneValue(sqlbv10);
			cont.setUnionContCount(Double.valueOf("0"));
			
			//2009-2-11 ln add  核保结论		
			cont.setUWFlag(tLCContSchema.getUWFlag());

			//2009-3-16 duanyh
			//select * from es_doc_def;			
			//新系统规则（根据扫描类代码校验）

			//tongmeng 2009-04-07 modify
//			String tSQL_DocAll = "select (case (case when sum(case when A.x='UR201' then 1 else 0 end) is not null then sum(case when A.x='UR201' then 1 else 0 end) else 0 end) when 0 then 0 else 1 end), "
//				               + " (case (case when sum(case when A.x='UR202' then 1 else 0 end) is not null then sum(case when A.x='UR202' then 1 else 0 end) else 0 end) when 0 then 0 else 1 end), "
//				               + " (case (case when sum(case when A.x='UR203' then 1 else 0 end) is not null then sum(case when A.x='UR203' then 1 else 0 end) else 0 end) when 0 then 0 else 1 end), "
//				               + " (case (case when sum(case when A.x='UR204' then 1 else 0 end) is not null then sum(case when A.x='UR204' then 1 else 0 end) else 0 end) when 0 then 0 else 1 end), "
//				               + " (case (case when sum(case when A.x='UR205' then 1 else 0 end) is not null then sum(case when A.x='UR205' then 1 else 0 end) else 0 end) when 0 then 0 else 1 end), "
//				               + " (case (case when sum(case when A.x='UR206' then 1 else 0 end) is not null then sum(case when A.x='UR206' then 1 else 0 end) else 0 end) when 0 then 0 else 1 end), "
//				               + " (case (case when sum(case when A.x='UR207' then 1 else 0 end) is not null then sum(case when A.x='UR207' then 1 else 0 end) else 0 end) when 0 then 0 else 1 end), "
//				               + " (case (case when sum(case when A.x='UR208' then 1 else 0 end) is not null then sum(case when A.x='UR208' then 1 else 0 end) else 0 end) when 0 then 0 else 1 end), "
//				               + " (case (case when sum(case when A.x='UR209' then 1 else 0 end) is not null then sum(case when A.x='UR209' then 1 else 0 end) else 0 end) when 0 then 0 else 1 end), "
//				               + " (case (case when sum(case when A.x='UR210' then 1 else 0 end) is not null then sum(case when A.x='UR210' then 1 else 0 end) else 0 end) when 0 then 0 else 1 end)"
//				               + " from ( "
//				               + " select  distinct subtype x from es_doc_main where subtype in "
//				               + " ('UR201','UR202','UR203','UR204','UR205','UR206','UR207','UR208','UR209','UR210') "
//				               + " and doccode='"+"?doccode?"+"' "
//				               + " ) A";
//			SQLwithBindVariables sqlbv11=new SQLwithBindVariables();
//			sqlbv11.sql(tSQL_DocAll);
//			sqlbv11.put("doccode", tLCContSchema.getProposalContNo());
//			SSRS tSSRS_DocAll = new SSRS();
//			ExeSQL tExeSQL_DocAll = new ExeSQL();
//			tSSRS_DocAll = tExeSQL_DocAll.execSQL(sqlbv11);
//			//String t
//			if(tSSRS_DocAll.getMaxRow()>0)
//			{
//				cont.setOImportDisFlag(tSSRS_DocAll.GetText(1,1));//  要约撤销标记
//				cont.setImportUpdatePFlag(tSSRS_DocAll.GetText(1,2));//  要约更正标记（打印）
//				cont.setImportUpdateNFlag(tSSRS_DocAll.GetText(1,3));//  要约更正标记（非打印）
//				cont.setPolAppDFlag(tSSRS_DocAll.GetText(1,4));//  生效日回溯标记
//				cont.setHPENotice(tSSRS_DocAll.GetText(1,5));//有体检资料标记???
//				cont.setHSpecFlag(tSSRS_DocAll.GetText(1,6));//有特别约定标记
//				cont.setHAskFlag(tSSRS_DocAll.GetText(1,7));//问卷标记
//				cont.setStatusFlag(tSSRS_DocAll.GetText(1,8));//  身份证明标记
//				cont.setIllFlag(tSSRS_DocAll.GetText(1,9));//  病历资料标记
//				cont.setOtherDataFlag(tSSRS_DocAll.GetText(1,10));//  其他资料标记
//				
//			}
//			else
			{
				cont.setOImportDisFlag("0");//  要约撤销标记
				cont.setImportUpdatePFlag("0");//  要约更正标记（打印）
				cont.setImportUpdateNFlag("0");//  要约更正标记（非打印）
				cont.setPolAppDFlag("0");//  生效日回溯标记
				cont.setHPENotice("0");//有体检资料标记???
				cont.setHSpecFlag("0");//有特别约定标记
				cont.setHAskFlag("0");//问卷标记
				cont.setStatusFlag("0");//  身份证明标记
				cont.setIllFlag("0");//  病历资料标记
				cont.setOtherDataFlag("0");//  其他资料标记
			}
			//
			cont.sethasSeenInsured("0");
//
//			String sqlFind = "select count(*) from lccustomerimpart where contno='"+"?mContNo?"+"' and impartver='103' and impartcode='12'";
//			SQLwithBindVariables sqlbv12=new SQLwithBindVariables();
//			sqlbv12.sql(sqlFind);
//			sqlbv12.put("mContNo", mContNo);
//			String tFindonTent = tExeSQL.getOneValue(sqlbv12);
//			if(tFindonTent!=null&&!tFindonTent.equals("")&&Integer.parseInt(tFindonTent)>0){
//				cont.sethasSeenInsured("1");// 业务员是否亲眼见过被保险人
//			}
//			else
//				cont.sethasSeenInsured("0");
//			
			//tongmeng 2009-04-13 add
			//合同词条增加经过自核次数
//			String tCount_SQL = "select count(*)+1 from lbmission "
//				              + " where processID = '0000000003' and ActivityID = '0000001003' "
//				              + " and missionprop1='"+this.mContNo+"'";
			//用lwactivity.functionid替换activityid，可以去掉processid。2013-04-23 lzf
//			String tCount_SQL = "select count(*)+1 from lbmission "
//		              + " where  ActivityID in (select activityid from lwactivity  where functionid ='10010005') "
//		              + " and missionprop1='"+"?missionprop1?"+"'";
//			SQLwithBindVariables sqlbv13=new SQLwithBindVariables();
//			sqlbv13.sql(tCount_SQL);
//			sqlbv13.put("missionprop1", this.mContNo);
//			ExeSQL tExeSQL_Auto = new ExeSQL();
//			String tValue_Auto = tExeSQL_Auto.getOneValue(sqlbv13);
			cont.setAutoUWTimes(new Double(0));
//
//			if(tValue_Auto==null||tValue_Auto.equals(""))
//			{
//				cont.setAutoUWTimes(new Double(0));
//			}
//			else
//			{
//				cont.setAutoUWTimes(new Double(tValue_Auto));
//			}
			cont.setReinImpart(getReinImpart());//补充告知问卷
			cont.setRemarkCount(getRemarkCount());//备注栏字数
			cont.setAccoBodyCheck(getAccoBodyCheck());//陪检记录
			cont.setIsAppointHos(getIsAppointHos());//是否是定点医院
			cont.setSpotCheckFlag(getSpotCheckFlag());//系统抽检标记
			cont.setMOpeIsNotDefined(getMOpeIsNotDefined());//生调回复人员是否与系统定义不一致
			cont.setSecondaryManagecom(getManagetCom(tLCContSchema.getManageCom(),4));//二级机构
			cont.setThirdStageManagecom(getManagetCom(tLCContSchema.getManageCom(),6));//三级机构
			cont.setFourStageManagecom(getManagetCom(tLCContSchema.getManageCom(),8));//四级机构
			cont.setSamplingFactor(getSamplingFactor());//抽检因子
			cont.setTotalCount(Double.valueOf(String.valueOf(mGlobalCheckSpot.getCurrentCheckNum())));//当前已抽检数
			cont.setUWLevel(getContUWLevel(tLCContSchema.getManageCom()));
			cont.setFirstTrialDate(sdf.parse(tLCContSchema.getFirstTrialDate()+ " "+ "00:00:00"));//合同的【初审日期】
			cont.setUWDate(sdf.parse((getUWDate(tLCContSchema.getPrtNo()))));//进入【自核日期】的时间
			cont.setManaSpecFlag(getManaSpecFlag(tLCContSchema.getManageCom()));//管理机构百团标记
		    
			
			//缴费方式---
			cont.setInitialPayment(tLCContSchema.getNewPayMode());
			//续保交费方式---
			cont.setRenewalPayment(tLCContSchema.getPayMode());
			
		}catch(Exception e){
			CError.buildErr(this, "准备BOMCont时出错！");
			e.printStackTrace();
		}
		return cont;
	}
	
	private BOMAppnt DealBOMAppnt(LCAppntSchema tLCAppntSchema,LCContSchema tLCContSchema){
		BOMAppnt appnt = new BOMAppnt();
		ExeSQL tempExeSQL = new ExeSQL();
		try{
			int tAppAge = PubFun.calInterval2(tLCAppntSchema.getAppntBirthday(), tLCContSchema.getPolApplyDate(), "Y");
			appnt.setAppntAge(Double.valueOf(String.valueOf(tAppAge)));
			if(!((tLCAppntSchema.getAppntBirthday()==null))||"".equals(tLCAppntSchema.getAppntBirthday())){
				theDate=tLCAppntSchema.getAppntBirthday()+" 00:00:00";
				appnt.setAppntBirthday(sdf.parse(theDate));
			}
			appnt.setAppntName(tLCAppntSchema.getAppntName());
			appnt.setAppntNo(tLCAppntSchema.getAppntNo());
			appnt.setAppntSex(tLCAppntSchema.getAppntSex());			
//			String tBlackList="select (case when BlacklistFlag is not null then BlacklistFlag else '0' end) from ldperson where customerno='"+"?customerno?"+"'";
//			SQLwithBindVariables sqlbv14=new SQLwithBindVariables();
//			sqlbv14.sql(tBlackList);
//			sqlbv14.put("customerno", tLCAppntSchema.getAppntNo());
//			String tBlackFlag = tempExeSQL.getOneValue(sqlbv14);
//			
			appnt.setBlackListFlag("0");//黑名单标记
			appnt.setCreditGrade(tLCAppntSchema.getCreditGrade());
			appnt.setDegree(tLCAppntSchema.getDegree());			

			if(!((tLCAppntSchema.getJoinCompanyDate()==null)||"".equals(tLCAppntSchema.getJoinCompanyDate()))){
				theDate=tLCAppntSchema.getJoinCompanyDate()+" 00:00:00";
				appnt.setJoinCompanyDate(sdf.parse(theDate));
			}
			appnt.setMarriage(tLCAppntSchema.getMarriage());
			if(!((tLCAppntSchema.getMarriageDate()==null))||"".equals(tLCAppntSchema.getMarriageDate())){
				theDate=tLCAppntSchema.getMarriageDate()+" 00:00:00";
				appnt.setMarriageDate(sdf.parse(theDate));
			}
			appnt.setNationality(tLCAppntSchema.getNationality());
			appnt.setNativePlace(tLCAppntSchema.getNativePlace());
			appnt.setOccupationCode(tLCAppntSchema.getOccupationCode());
			appnt.setOccupationType(tLCAppntSchema.getOccupationType());
			appnt.setPosition(tLCAppntSchema.getPosition());
			appnt.setRelationToInsured(tLCAppntSchema.getRelationToInsured());
			appnt.setRgtAddress(tLCAppntSchema.getRgtAddress());
			appnt.setSalary(new Double(tLCAppntSchema.getSalary()));

			if(!((tLCAppntSchema.getStartWorkDate()==null))||"".equals(tLCAppntSchema.getStartWorkDate())){
				theDate=tLCAppntSchema.getStartWorkDate()+" 00:00:00";
				appnt.setStartWorkDate(sdf.parse(theDate));
			}			
			//个人单
			appnt.setSmokeFlag("0");//吸烟标记
//
//			String tSmokeFlagSql="select count(*)"
//				 +" from lccustomerimpartparams"
//				 +" where impartcode = 'A0102'"
//				 +" and impartver = 'A01' "
//				 +" and impartparamno in ('3','4')"
//				 +" and contno='"+"?mContNo?"+"'";
//			SQLwithBindVariables sqlbv15=new SQLwithBindVariables();
//			sqlbv15.sql(tSmokeFlagSql);
//			sqlbv15.put("mContNo",mContNo);
//			if(mLCContSchema.getPrtNo()!=null && !mLCContSchema.getPrtNo().equals("") 
//					&& mLCContSchema.getPrtNo().length()==14 && mLCContSchema.getPrtNo().substring(2,4).equals("51"))//家庭单
//			{	//家庭单			
//				tSmokeFlagSql="select count(*)"
//					 +" from lccustomerimpartparams"
//					 +" where impartcode = 'D0102'"
//					 +" and impartver = 'D01' "
//					 //+" and customernotype='1'"//被保人
//					 //+" and customerno='"
//					 //+tLCInsuredSchema.getInsuredNo()
//					 +" and impartparamno in ('1','2')"
//					 +" and contno='"+"?mContNo?"+"'";
//				sqlbv15=new SQLwithBindVariables();
//				sqlbv15.sql(tSmokeFlagSql);
//				sqlbv15.put("mContNo",mContNo);
//			}
//			String tSmokeFlag = tempExeSQL.getOneValue(sqlbv15);//是否有吸烟习惯
//			if(tSmokeFlag!=null&&!tSmokeFlag.equals("")&&Integer.parseInt(tSmokeFlag)>0){
//				appnt.setSmokeFlag("1");
//			}
//			else
//			{
//				appnt.setSmokeFlag("0");//吸烟标记
//			}
//			String tAppHosSql="select (case when blacklistflag is not null then blacklistflag else '0' end),(case when hospitalname is not null then hospitalname else '' end) from ldhospital where hospitcode in " 
//								+"(select hospitcode from lcpenotice where contno='"+"?mContNo?"
//								+"' and customerno='"+"?customerno?"
//								+"'and customertype='A')";
//			SQLwithBindVariables sqlbv16=new SQLwithBindVariables();
//			sqlbv16.sql(tAppHosSql);
//			sqlbv16.put("mContNo", mContNo);
//			sqlbv16.put("customerno", tLCAppntSchema.getAppntNo());
//			SSRS tSSRS_Hospital = new SSRS();
//			tSSRS_Hospital = tempExeSQL.execSQL(sqlbv16);
//			if(tSSRS_Hospital.getMaxRow()>0)
//			{
//				//黑名单标记
//				appnt.setAppHosBlack(tSSRS_Hospital.GetText(1,1));
//				//体检医院名称
//				appnt.setAppHosName(tSSRS_Hospital.GetText(1,2));
//			}
//			
			//健康告知不全为否 HealthTellConTent
		   //tongmeng 2009-04-25 modify
			//按照客户号查询
			appnt.setHealthTellConTent("0");
//
//			String tHealthTellConTent_sql = "select count(*) from lccustomerimpart where impartcode in('A0105','A0110','A0111e','A0111j',"
//				+"'A0106','A0111a','A0111f','A0112','A0107','A0111b','A0111g','A0108','A0111c','A0111h','A0109','A0111d',"
//				+"'A0111i','D0105','D0110a','D0110e','D0110i','D0106','D0110b','D0110f','D0110j','D0107','D0110c','D0110g',"
//				+"'D0111','D0108','D0110d','D0110h','D0122','D0109','C0101','C0102','C0103','C0104','C0105','C0106','B0101',"
//				+"'B0102','B0103','B0104','A0504','A0505','A0506','A0507','A0509','A0510','A0511','A0512',"
//				+"'A0513','A0514','A0515','A0516','A0517','A0518','A0519','A0520')"
//				+" and contno='"+"?mContNo?"+"' and customerno='"+"?customerno?"+"' ";//and CustomerNoType='0'";
//			String tHealthTellConTent = "";
//			SQLwithBindVariables sqlbv17=new SQLwithBindVariables();
//			sqlbv17.sql(tHealthTellConTent_sql);
//			sqlbv17.put("mContNo", mContNo);
//			sqlbv17.put("customerno", tLCAppntSchema.getAppntNo());
//			tHealthTellConTent = tempExeSQL.getOneValue(sqlbv17);
//			if(tHealthTellConTent!=null&&!tHealthTellConTent.equals("")&&Integer.parseInt(tHealthTellConTent)>0){
//				appnt.setHealthTellConTent("1");
//			}else{
//				appnt.setHealthTellConTent("0");
//			}
			appnt.setFemaleConTent("0");
//
//			//妇科告知不全为否 FemaleConTent
//			String tFemaleConTent_sql = "select count(*) from lccustomerimpart where impartcode in('A0113a','A0113b','D0112a','D0112b')"
//				+" and contno='"+"?mContNo?"+"' and customerno='"+"?customerno?"+"' ";//and CustomerNoType='0'";
//			String tFemaleConTent = "";
//			SQLwithBindVariables sqlbv18=new SQLwithBindVariables();
//			sqlbv18.sql(tFemaleConTent_sql);
//			sqlbv18.put("mContNo", mContNo);
//			sqlbv18.put("customerno", tLCAppntSchema.getAppntNo());
//			tFemaleConTent = tempExeSQL.getOneValue(sqlbv18);
//			if(tFemaleConTent!=null&&!tFemaleConTent.equals("")&&Integer.parseInt(tFemaleConTent)>0){
//				appnt.setFemaleConTent("1");
//			}else{
//				appnt.setFemaleConTent("0");
//			}
			//婴儿告知不为否 BabyConTent
			appnt.setBabyConTent("0");
//
//			String tBabyConTent_sql = "select count(*) from lccustomerimpart where impartcode in('A0114b','D0121')"
//				+" and contno='"+"?mContNo?"+"' and customerno='"+"?customerno?"+"' ";//and CustomerNoType='0'";
//			String tBabyConTent = "";
//			SQLwithBindVariables sqlbv19=new SQLwithBindVariables();
//			sqlbv19.sql(tBabyConTent_sql);
//			sqlbv19.put("mContNo", mContNo);
//			sqlbv19.put("customerno", tLCAppntSchema.getAppntNo());
//			
//			tBabyConTent = tempExeSQL.getOneValue(sqlbv19);
//			if(tBabyConTent!=null&&!tBabyConTent.equals("")&&Integer.parseInt(tBabyConTent)>0){
//				appnt.setBabyConTent("1");
//			}else{
//				appnt.setBabyConTent("0");
//			}
			//家族史 FamilyConTent
			appnt.setFamilyConTent("0");
//
//			String tFamilyConTent_sql = "select count(*) from lccustomerimpart where impartcode in('A0115a','A0115b','D0113a','D0113b')"
//				+" and contno='"+"?mContNo?"+"' and customerno='"+"?customerno?"+"' ";//and CustomerNoType='0'";
//			String tFamilyConTent = "";
//			SQLwithBindVariables sqlbv20=new SQLwithBindVariables();
//			sqlbv20.sql(tFamilyConTent_sql);
//			sqlbv20.put("mContNo", mContNo);
//			sqlbv20.put("customerno", tLCAppntSchema.getAppntNo());
//			tFamilyConTent = tempExeSQL.getOneValue(sqlbv20);
//			if(tFamilyConTent!=null&&!tFamilyConTent.equals("")&&Integer.parseInt(tFamilyConTent)>0){
//				appnt.setFamilyConTent("1");
//			}else{
//				appnt.setFamilyConTent("0");
//			}
			//既往异常投保/理赔史 OuncommonConTent
			appnt.setOuncommonConTent("0");
//
//			String tOuncommonConTent_sql = "select count(*) from lccustomerimpart where impartcode in('A0117','A0118','D0115','D0116','D0117','C0108')"
//				+" and contno='"+"?mContNo?"+"' and customerno='"+"?customerno?"+"' ";//and CustomerNoType='0'";
//			String tOuncommonConTent = "";
//			SQLwithBindVariables sqlbv21=new SQLwithBindVariables();
//			sqlbv21.sql(tOuncommonConTent_sql);
//			sqlbv21.put("mContNo", mContNo);
//			sqlbv21.put("customerno", tLCAppntSchema.getAppntNo());
//			tOuncommonConTent = tempExeSQL.getOneValue(sqlbv21);
//			if(tOuncommonConTent!=null&&!tOuncommonConTent.equals("")&&Integer.parseInt(tOuncommonConTent)>0){
//				appnt.setOuncommonConTent("1");
//			}else{
//				appnt.setOuncommonConTent("0");
//			}
			
			//累计年交保费：交费方式不为趸交的不累计,其他换算为年交 半年交费率=年交费率×0.52	季交费率=年交费率×0.27	月交费率=年交费率×0.09
//            String tAddYPrem_sql = "select  nvl(sum(case when payintv = '12' then prem when payintv = '6' then prem / 0.52 when payintv = '3' then prem / 0.27"						
//						+" when payintv = '1' then prem / 0.09 else 0 end),0) from lcpol where appflag not in ('4','9') and appntno='"+tLCAppntSchema.getAppntNo()+"' "
//						+" and uwflag not in ('1','2','a')";
//            String tAddYPrem = "";
//            tAddYPrem = tempExeSQL.getOneValue(tAddYPrem_sql);
//            appnt.setAddYPrem(Double.valueOf(tAddYPrem));//累积年交保费
			//投保人名下有多币种险种的情况
            String currString = "select * from lcpol where appflag not in ('4','9') and appntno='"+"?appntno?"+"' and uwflag not in ('1','2','a')";
            LDExch tLDExch = new LDExch();
            LCPolDB currLCPolDB = new LCPolDB();
            LCPolSet currLCPolSet = new LCPolSet();
            SQLwithBindVariables sqlbv22=new SQLwithBindVariables();
            sqlbv22.sql(currString);
            sqlbv22.put("appntno", tLCAppntSchema.getAppntNo());
            currLCPolSet = currLCPolDB.executeQuery(sqlbv22);
            double totalCurr = 0.00;
            for(int i=1;i<=currLCPolSet.size();i++){
            	int tPayIntv = currLCPolSet.get(i).getPayIntv();
            	if(tPayIntv == 12){
            		totalCurr += tLDExch.toBaseCur(currLCPolSet.get(i).getCurrency(), locCurrency, theCurrentDate, currLCPolSet.get(i).getPrem()); 
            	}
            	else if(tPayIntv == 6){
            		totalCurr += tLDExch.toBaseCur(currLCPolSet.get(i).getCurrency(), locCurrency, theCurrentDate, currLCPolSet.get(i).getPrem()/0.52); 
            	}
            	else if(tPayIntv == 3){
            		totalCurr += tLDExch.toBaseCur(currLCPolSet.get(i).getCurrency(), locCurrency, theCurrentDate, currLCPolSet.get(i).getPrem()/0.27); 
            	}
            	else if(tPayIntv == 1){
            		totalCurr += tLDExch.toBaseCur(currLCPolSet.get(i).getCurrency(), locCurrency, theCurrentDate, currLCPolSet.get(i).getPrem()/0.09); 
            	}
            	else {
            		totalCurr += 0; 
            	}
            }
            appnt.setAddYPrem(totalCurr);//累积年交保费
			
//			String tYearGet_sql = 
////				"select nvl(max(ImpartParam),0) from lccustomerimpartparams where impartcode='A0120' and impartver='A02' and impartparamno='3'"
////				+"and contno='"+mContNo+"' and customerno='"+tLCAppntSchema.getAppntNo()+"' ";//and CustomerNoType='0'";
//						"select (case when max(A.x) is not null then max(A.x) else 0 end) from (select (case when max(ImpartParam*1) is not null then max(ImpartParam*1) else 0 end) x"
//						+ " from lccustomerimpartparams"
//						+ " where impartcode in ('A0120', 'D0119')"
//						+ " and impartver in ('A02', 'D02')"
//						+ " and impartparamno = '3'"
//						+ " and contno = '"+"?mContNo?"+"'"
//						+ " and customerno = '"+"?customerno?"+"'"
//						+ " union"
//						+ " select (case when max(impartparam * 10000) is not null then max(impartparam * 10000) else 0 end) x"
//						+ " from lccustomerimpartparams"
//						+ " where impartcode = 'A0534'"
//						+ " and impartparamno = '3'"
//						+ " and contno = '"+"?mContNo?"+"'"
//						+ " and customerno = '"+"?customerno?"+"') A";
//			String tYearGet = "";
//			SQLwithBindVariables sqlbv23=new SQLwithBindVariables();
//			sqlbv23.sql(tYearGet_sql);
//			sqlbv23.put("mContNo", mContNo);
//			sqlbv23.put("customerno", tLCAppntSchema.getAppntNo());
            String tYearGet="10000";
			//tYearGet = tempExeSQL.getOneValue(sqlbv23);
			if(tYearGet!=null&&!tYearGet.equals(""))
			{
				//60-U-10-201tYearGet 会作为除数,如果为0,设置为1
				//
//				if(tYearGet.equals("0"))
//				{
//					tYearGet_sql = "select (case when max(A.x) is not null then max(A.x) else 0 end) from (select (case when max(ImpartParam*1) is not null then max(ImpartParam*1) else 0 end) x"
//						+ " from lccustomerimpartparams"
//						+ " where impartcode in ('A0120', 'D0119')"
//						+ " and impartver in ('A02', 'D02')"
//						+ " and impartparamno = '3'"
//						+ " and contno = '"+"?mContNo?"+"'"
//						+ " and customerno = '"+"?customerno?"+"'"
//						+ " union"
//						+ " select (case when max(impartparam * 10000) is not null then max(impartparam * 10000) else 0 end) x"
//						+ " from lccustomerimpartparams"
//						+ " where impartcode = 'A0534'"
//						+ " and impartparamno = '1'"
//						+ " and contno = '"+"?mContNo?"+"'"
//						+ " and customerno = '"+"?customerno?"+"') A";
//					sqlbv23=new SQLwithBindVariables();
//					sqlbv23.sql(tYearGet_sql);
//					sqlbv23.put("mContNo", mContNo);
//					sqlbv23.put("customerno", tLCAppntSchema.getAppntNo());
//					tYearGet = tempExeSQL.getOneValue(sqlbv23);
//					if(tYearGet.equals("0"))
//					{
//						tYearGet = "1";
//					}
//				}
				appnt.setYearGet(Double.valueOf(tYearGet));//年收入
			}

//			appnt.setAppntISOperator(AppntISOperator);//投保人职业为MS公司业务员（待确认）
//			appnt.setInnerFlag(InnerFlag);//内部员工标记
			
//			String sqlFind = "";
//			String tFindonTent = "";
//			sqlFind = "select count(*) from lccustomerimpart where contno='"+mContNo+"' and impartver='103' and impartcode='21' and impartcontent like '%同事朋友推荐%'";			
//			tFindonTent = tempExeSQL.getOneValue(sqlFind);
//			if(tFindonTent!=null&&!tFindonTent.equals("")&&Integer.parseInt(tFindonTent)>0){
//				appnt.setisFriendsRecommend("1");//投保人投保经过是否为同事朋友推荐
//			}
//			else
//				appnt.setisFriendsRecommend("0");
//			
//			sqlFind = "select count(*) from lccustomerimpart where contno='"+mContNo+"' and impartver='103' and impartcode='21' and impartcontent like '%投保人自己提出%'";
//			tFindonTent = tempExeSQL.getOneValue(sqlFind);
//			if(tFindonTent!=null&&!tFindonTent.equals("")&&Integer.parseInt(tFindonTent)>0){
//				appnt.setisPresenter("1");//投保人投保经过是否投保人自己提出
//			}
//			else
//				appnt.setisPresenter("0");
//			
//			sqlFind = "select count(*) from lccustomerimpart where contno='"+mContNo+"' and impartver='103' and impartcode='21' and impartcontent like '%业务员推销%'";
//			tFindonTent = tempExeSQL.getOneValue(sqlFind);
//			if(tFindonTent!=null&&!tFindonTent.equals("")&&Integer.parseInt(tFindonTent)>0){
//				appnt.setisSalerPromotion("1");//投保人投保经过是否为业务员推销
//			}
//			else
//				appnt.setisSalerPromotion("0");
//			
//			sqlFind = "select count(*) from lccustomerimpart where contno='"+mContNo+"' and impartver='103' and impartcode='22' and impartcontent like '%家庭经济保障%'";
//			tFindonTent = tempExeSQL.getOneValue(sqlFind);
//			if(tFindonTent!=null&&!tFindonTent.equals("")&&Integer.parseInt(tFindonTent)>0){
//				appnt.setisEnsureIntention("1");//投保人投保目的为家庭经济保障
//			}
//			else
//				appnt.setisEnsureIntention("0");
//			
//			sqlFind = "select count(*) from lccustomerimpart where contno='"+mContNo+"' and impartver='103' and impartcode='22' and impartcontent like '%储蓄/投资%'";
//			tFindonTent = tempExeSQL.getOneValue(sqlFind);
//			if(tFindonTent!=null&&!tFindonTent.equals("")&&Integer.parseInt(tFindonTent)>0){
//				appnt.setisInvestItention("1");//投保人投保目的为贮蓄/投资
//			}
//			else
//				appnt.setisInvestItention("0");
//			
//			sqlFind = "select count(*) from lccustomerimpart where contno='"+mContNo+"' and impartver='103' and impartcode='22' and impartcontent like '%贷款偿还%'";
//			tFindonTent = tempExeSQL.getOneValue(sqlFind);
//			if(tFindonTent!=null&&!tFindonTent.equals("")&&Integer.parseInt(tFindonTent)>0){
//				appnt.setisPayIntention("1");//投保人投保目的为贷款偿还
//			}
//			else
////				appnt.setisPayIntention("0");
//			String sqlFind = "";
//			sqlFind ="select (case when impartparam is not null then impartparam else '0' end)"
//				 +" from lccustomerimpartparams"
//				 +" where impartcode = 'A0101'"
//				 +" and impartver = 'A01' "
//				// +" and impartparamno in('3','4')"
//				 + " and customerno='"+"?customerno?"+"' "
//				 +" and contno='"+"?mContNo?"+"'"
//				 +" order by customernotype,customerno,impartparamno";	
//			SQLwithBindVariables sqlbv24=new SQLwithBindVariables();
//			sqlbv24.sql(sqlFind);
//			sqlbv24.put("customerno", tLCAppntSchema.getAppntNo());
//			sqlbv24.put("mContNo", mContNo);
//			if(mLCContSchema.getPrtNo()!=null && !mLCContSchema.getPrtNo().equals("") 
//					&& mLCContSchema.getPrtNo().length()==14 && mLCContSchema.getPrtNo().substring(2,4).equals("51"))//家庭单
//			{	
//				sqlFind ="select (case when impartparam is not null then impartparam else '0' end)"
//						 +" from lccustomerimpartparams"
//						 +" where impartcode = 'D0101'"
//						 +" and impartver = 'D01' "
//						 +" and impartparamno in('1','2')"						 
//						 +" and contno='"+"?mContNo?"+"'"
//						 +" order by customernotype,customerno,impartparamno";		
//				sqlbv24=new SQLwithBindVariables();
//				sqlbv24.sql(sqlFind);
//				sqlbv24.put("mContNo", mContNo);
//			}
//			SSRS tSSRS = new SSRS();
//			tSSRS=tempExeSQL.execSQL(sqlbv24);
//			try {
//				if(tSSRS.MaxRow >= 4){
//					double tStature=Double.parseDouble(tSSRS.GetText(3, 1));//身高
//					double tAvoirdupois=Double.parseDouble(tSSRS.GetText(4, 1));//体重
//					appnt.setAvoirdupois(new Double(tAvoirdupois));
//					appnt.setStature(new Double(tStature));
//				}
//				else if(tSSRS.MaxRow >= 2){
//					double tStature=Double.parseDouble(tSSRS.GetText(1, 1));//身高
//					double tAvoirdupois=Double.parseDouble(tSSRS.GetText(2, 1));//体重
//					appnt.setAvoirdupois(new Double(tAvoirdupois));
//					appnt.setStature(new Double(tStature));
//				}
//			} catch (Exception e) {
//				CError.buildErr(this, mContNo+"投保人身高体重异常！");
//				e.printStackTrace();
//			}
			appnt.setPregnancyWeeks(getPregnancyWeeks(tLCAppntSchema.getAppntNo()));//怀孕周数
			appnt.setSmokeYears(getSmokeYears(tLCAppntSchema.getAppntNo(),"A",false));//吸烟年数
			appnt.setDrinkType(getDrinkType(tLCAppntSchema.getAppntNo()));//饮酒类型
			appnt.setDrinkYears(getDrinkYears(tLCAppntSchema.getAppntNo()));//饮酒年数
			appnt.setISPregnancy(getISImpart(tLCAppntSchema.getAppntNo(),"01"));//孕妇告知
			appnt.setDanSportInter(getISImpart(tLCAppntSchema.getAppntNo(),"02"));//危险运动爱好
			appnt.setTrafAccImpart(getISImpart(tLCAppntSchema.getAppntNo(),"03"));//交通事故告知
			appnt.setAbroadImpart(getISImpart(tLCAppntSchema.getAppntNo(),"04"));//出国意向告知
			appnt.setDisabilityImpart(getDisabilityImpart(tLCAppntSchema.getAppntNo()));//残疾事项告知
			//appnt.setSamePhone(getSamePhone(tLCAppntSchema.getAppntNo(),tLCAppntSchema.getAddressNo()));//联系电话与业务员的联系电话是否一致
		}catch(Exception e){
			CError.buildErr(this, "准备BOMAppnt时出错");
		}
		return appnt;
	}
	
	private BOMAgent DealBOMAgent(LAAgentSchema tLAAgentSchema){
		BOMAgent agent = new BOMAgent();
		String tBlackList="select blacklisflag from latree where agentcode='"+"?agentcode?"+"'";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(tBlackList);
		sqlbv.put("agentcode", tLAAgentSchema.getAgentCode());
		ExeSQL tempExeSQL = new ExeSQL();
		String tBlackFlag = tempExeSQL.getOneValue(sqlbv);
		agent.setAgentBlankFlag(tBlackFlag);//黑名单标记
		agent.setAgentCode(tLAAgentSchema.getAgentCode());
		agent.setAgentKind(tLAAgentSchema.getAgentKind());
		agent.setAgentState(tLAAgentSchema.getAgentState());
		agent.setBranchType(tLAAgentSchema.getBranchType());
		agent.setInsideFlag(tLAAgentSchema.getInsideFlag());
		agent.setManageCom(tLAAgentSchema.getManageCom());
		agent.setQuafNo(tLAAgentSchema.getQuafNo());
		agent.setSaleQuaf(tLAAgentSchema.getSaleQuaf());
		//tongmeng 2009-04-11 add
		//增加业务员告知异常和投保人投保告知异常词条 
		agent.setSpecAImpart(this.getSpecAImpart(mContNo));
		agent.setProAImpart(this.getProAImpart(mContNo));
		agent.setUWLevel(getAgentUWLevel(tLAAgentSchema.getAgentCode()));
		String tRelToInsured = getRelToInsured();
		agent.setRelToAppnt(tRelToInsured);//与被保人关系
//		agent.setLevelYN(LevelYN);//等级是否为差
		return agent;
	}
	
	private BOMInsured DealBOMInsured(LCInsuredSchema tLCInsuredSchema,LCContSchema tLCContSchema){
		BOMInsured insured = new  BOMInsured();
		try{
					insured.setInsuredNo(tLCInsuredSchema.getInsuredNo());
					/** 是否是投保人 ture-是*/
					boolean FreeFlag = false;
					if(tLCInsuredSchema.getGrpContNo()!=null&&tLCInsuredSchema.getGrpContNo().equals("FREE"))
					{
						FreeFlag = true;
					}
					ExeSQL tExeSQL2 = new ExeSQL();					
					GetAllSumAmnt(tLCInsuredSchema.getInsuredNo());//得到以下各种累计风险保额
					insured.setAccidentSumAmnt(new Double(this.MSumDangerAmnt));//累计意外伤害风险保额
					
					String tApproveSql = " select count(*) from lwmission where activityid in (select activityid from lwactivity  where functionid ='10010003') "
						               + " and missionprop1 in (select contno from lcinsured where insuredno='"+"?insuredno?"+"') ";
					SQLwithBindVariables sqlbv=new SQLwithBindVariables();
					sqlbv.sql(tApproveSql);
					sqlbv.put("insuredno", tLCInsuredSchema.getInsuredNo());
					String tApprovePolCount = tExeSQL2.getOneValue(sqlbv);
					if(!(tApprovePolCount==null||"".equals(tApprovePolCount))){
						insured.setApprovePolCount(Double.valueOf(tApprovePolCount));//待复合投保单数
					}
//					insured.setAvoirdupois(new Double(tLCInsuredSchema.getAvoirdupois()));//体重
					//tongmeng 2009-04-25 modify
					//按照客户号查询
//					String BirthSql1="select (case when max(ImpartParam) is not null then max(ImpartParam) else '0' end) from lccustomerimpartparams where impartver in ('A01','A05')"
//						 +" and impartcode in ('A0114a','A0523') and impartparamno='1'"
//						 //+" and customernotype='1'"//被保人
//						 +" and customerno='"+"?customerno?"+"' and contno='"+"?mContNo?"+"'";
//					SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
//					sqlbv1.sql(BirthSql1);
//					sqlbv1.put("customerno", tLCInsuredSchema.getInsuredNo());
//					sqlbv1.put("mContNo", mContNo);
//					try {
//						String tBirth1 = "";
////					String tempBirth1 = "";
//						tBirth1=tExeSQL2.getOneValue(sqlbv1);
////					if(tempBirth1!=null&&!tempBirth1.equals("")){
////						tBirth1 = tempBirth1.replaceAll("[^0-9]", "");
////					}
//						logger.debug("tBirth1 "+tBirth1);
//						if(!(tBirth1==null||"".equals(tBirth1.trim())))
//						{
//							insured.setBirthStature(Double.valueOf(tBirth1));//出生时身高					
//						}
//					} catch (Exception e1) {
//						CError.buildErr(this, mContNo+"被保人出生时身高数据有误！");
//						e1.printStackTrace();
//					}
//					try {
//						String BirthSql2="select (case when max(ImpartParam) is not null then max(ImpartParam) else '0' end) from lccustomerimpartparams where impartver in ('A01','A05')"
//							 +" and impartcode in ('A0114a','A0523') and impartparamno='2'"
//							 //+" and customernotype='1'"//被保人
//							 +" and customerno='"+"?customerno?"+"' and contno='"+"?mContNo?"+"'";
//						SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
//						sqlbv2.sql(BirthSql2);
//						sqlbv2.put("customerno", tLCInsuredSchema.getInsuredNo());
//						sqlbv2.put("mContNo", mContNo);
//						String tBirth2 = "";
////					String tempBirth2 = "";
//						tBirth2=tExeSQL2.getOneValue(sqlbv2);
////					if(tempBirth2!=null&&!tempBirth2.equals("")){
////						tBirth2 = tempBirth2.replaceAll("[^0-9]", "");
////					}
//						if(!(tBirth2==null||"".equals(tBirth2.trim())))
//						{							
//								insured.setBirthAvoirdupois(Double.valueOf(tBirth2));//出生时体重
//						}
//					} catch (Exception e1) {
//						CError.buildErr(this, mContNo+"被保人出生时体重数据有误！");
//						e1.printStackTrace();
//					}
//					
//					if(!((tLCInsuredSchema.getBirthday()==null))||"".equals(tLCInsuredSchema.getBirthday())){
//						theDate=tLCInsuredSchema.getBirthday()+" 00:00:00";
//						insured.setBirthday(sdf.parse(theDate));
//					}
//					String tBlackList="select (case when BlacklistFlag is not null then BlacklistFlag else '0' end) from ldperson where customerno='"+"?customerno?"+"'";
//					SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
//					sqlbv3.sql(tBlackList);
//					sqlbv3.put("customerno", tLCInsuredSchema.getInsuredNo());
//					ExeSQL tempExeSQL = new ExeSQL();
//					String tBlackFlag = tempExeSQL.getOneValue(sqlbv3);
//					insured.setBlackListFlag(tBlackFlag);//黑名单标记
//					
//					
//					
//					String tBMISql ="select (case when impartparam is not null then impartparam else '0' end)"
//						 +" from lccustomerimpartparams"
//						 +" where impartcode in ('A0101','A0501')"
//						 +" and impartver in ('A01','A05') "
//						 //+" and impartparamno in('1','2')"
//						 //+" and customernotype='1'"//被保人
//						 +" and customerno='"+"?customerno?"+"' "
//						 +" and contno='"+"?mContNo?"+"'"
//						 +" order by customernotype,customerno,impartparamno";	
//					SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
//					sqlbv4.sql(tBMISql);
//					sqlbv4.put("customerno", tLCInsuredSchema.getInsuredNo());
//					sqlbv4.put("mContNo", mContNo);
//					if(mLCContSchema.getPrtNo()!=null && !mLCContSchema.getPrtNo().equals("") 
//							&& mLCContSchema.getPrtNo().length()==14 && mLCContSchema.getPrtNo().substring(2,4).equals("51")
//							&& tLCInsuredSchema.getSequenceNo()!=null && !tLCInsuredSchema.getSequenceNo().equals(""))//家庭单
//					{	
//						//tBMISql = "select 1 from dual";
//						if(tLCInsuredSchema.getSequenceNo().equals("1")
//								|| tLCInsuredSchema.getSequenceNo().equals("-1"))
//						{
//							tBMISql ="select (case when impartparam is not null then impartparam else '0' end)"
//								 +" from lccustomerimpartparams"
//								 +" where impartcode = 'D0101'"
//								 +" and impartver = 'D01' "
//								 +" and impartparamno in('1','2')"
//								 //+" and customernotype='1'"//被保人
//								 //+" and customerno='"+tLCInsuredSchema.getInsuredNo()+"' "
//								 +" and contno='"+"?mContNo?"+"'"
//								 +" order by customernotype,customerno,impartparamno";
//							sqlbv4=new SQLwithBindVariables();
//							sqlbv4.sql(tBMISql);
//							sqlbv4.put("mContNo", mContNo);
//						}
//						else if(tLCInsuredSchema.getSequenceNo().equals("2"))
//						{
//							tBMISql ="select (case when impartparam is not null then impartparam else '0' end)"
//								 +" from lccustomerimpartparams"
//								 +" where impartcode = 'D0101'"
//								 +" and impartver = 'D01' "
//								 +" and impartparamno in('3','4')"
//								 //+" and customernotype='1'"//被保人
//								 //+" and customerno='"+tLCInsuredSchema.getInsuredNo()+"' "
//								 +" and contno='"+"?mContNo?"+"'"
//								 +" order by customernotype,customerno,impartparamno";
//							sqlbv4=new SQLwithBindVariables();
//							sqlbv4.sql(tBMISql);
//							sqlbv4.put("mContNo", mContNo);
//						}
//						else if(tLCInsuredSchema.getSequenceNo().equals("3"))
//						{
//							tBMISql ="select (case when impartparam is not null then impartparam else '0' end)"
//								 +" from lccustomerimpartparams"
//								 +" where impartcode = 'D0101'"
//								 +" and impartver = 'D01' "
//								 +" and impartparamno in('5','6')"
//								 //+" and customernotype='1'"//被保人
//								 //+" and customerno='"+tLCInsuredSchema.getInsuredNo()+"' "
//								 +" and contno='"+"?mContNo?"+"'"
//								 +" order by customernotype,customerno,impartparamno";
//							sqlbv4=new SQLwithBindVariables();
//							sqlbv4.sql(tBMISql);
//							sqlbv4.put("mContNo", mContNo);
//						}
//					}
//					SSRS tSSRS = new SSRS();
//					tSSRS=tempExeSQL.execSQL(sqlbv4);
//					double tBMI=0;
//					try {
//						if(FreeFlag)
//						{
//							//如果是投保人
//							if(tSSRS.MaxRow >= 4){
//								double tStature=Double.parseDouble(tSSRS.GetText(3, 1));//身高
//								double tAvoirdupois=Double.parseDouble(tSSRS.GetText(4, 1));//体重
//								insured.setAvoirdupois(new Double(tAvoirdupois));
//								insured.setStature(new Double(tStature));
//								if(tStature>0){
//									tBMI=tAvoirdupois*10000/(tStature*tStature);
//									 DecimalFormat mDecimalFormat = new DecimalFormat("0");
////									 tBMI = Double.parseDouble(mDecimalFormat.format(tBMI));
//									 tBMI = PubFun.round(tBMI,0);;
//								}
//								insured.setBMI(new Double(tBMI));
//							}
//							else if(tSSRS.MaxRow >= 2){
//								double tStature=Double.parseDouble(tSSRS.GetText(1, 1));//身高
//								double tAvoirdupois=Double.parseDouble(tSSRS.GetText(2, 1));//体重
//								insured.setAvoirdupois(new Double(tAvoirdupois));
//								insured.setStature(new Double(tStature));
//								if(tStature>0){
//									tBMI=tAvoirdupois*10000/(tStature*tStature);
//									 DecimalFormat mDecimalFormat = new DecimalFormat("0");
////									 tBMI = Double.parseDouble(mDecimalFormat.format(tBMI));
//									 tBMI = PubFun.round(tBMI,0);;
//								}
//								insured.setBMI(new Double(tBMI));
//							}
//							else
//							{
//								insured.setAvoirdupois(new Double("0"));
//								insured.setStature(new Double("0"));
//								insured.setBMI(new Double("0"));
//							}
//						}
//						else
//						{
//						if(tSSRS.MaxRow >= 2){
//							double tStature=Double.parseDouble(tSSRS.GetText(1, 1));//身高
//							double tAvoirdupois=Double.parseDouble(tSSRS.GetText(2, 1));//体重
//							insured.setAvoirdupois(new Double(tAvoirdupois));
//							insured.setStature(new Double(tStature));
//							if(tStature>0){
//								tBMI=tAvoirdupois*10000/(tStature*tStature);
//								 DecimalFormat mDecimalFormat = new DecimalFormat("0");
////								 tBMI = Double.parseDouble(mDecimalFormat.format(tBMI));
//								 tBMI = PubFun.round(tBMI,0);
//							}
//							insured.setBMI(new Double(tBMI));
//						}
//						else
//						{
//							insured.setAvoirdupois(new Double("0"));
//							insured.setStature(new Double("0"));
//							insured.setBMI(new Double("0"));
//						}
//						}
//					} catch (Exception e) {
//						CError.buildErr(this, mContNo+"身高体重数据异常!");
//						e.printStackTrace();
//					}
					/*if(FreeFlag){
						insured.setBMI(new Double("0"));
					}*/
					//tongmeng 2009-03-26 add
					//增加身高体重是否在标准范围内
//					insured.setStature(Double.valueOf(String.valueOf(tLCInsuredSchema.getStature())));
					String tBMIFlag = "";
					tBMIFlag = this.getBMIFlag(tLCContSchema.getContNo(),
							tLCInsuredSchema, insured, tLCContSchema,"0");
					insured.setStatureFlag(tBMIFlag);//身高是否符合标准范围
					tBMIFlag = this.getBMIFlag(tLCContSchema.getContNo(),
							tLCInsuredSchema, insured, tLCContSchema,"1");
					insured.setAvoirdupoisFlag(tBMIFlag);//体重是否符合标准范围
					//tongmeng 2009-03-24 add
				    //增加BMI是否在标准范围内
					tBMIFlag = this.getBMIFlag(tLCContSchema.getContNo(),
							tLCInsuredSchema, insured, tLCContSchema,"2");
					insured.setBMIFlag(tBMIFlag);
					insured.setHealthFlag("0");//体检标记

					//tongmeng 2009-02-20 modify
//					//生调不会记录到人,只查合同是否生调即可
//					String LiveSql="select count(*) from lcrreport where contno='"+"?contno?"+"'";
//					SQLwithBindVariables sqlbv5=new SQLwithBindVariables();
//					sqlbv5.sql(LiveSql);
//					sqlbv5.put("contno", tLCInsuredSchema.getContNo());
//					String tLiveValue = "";
//					tLiveValue = tempExeSQL.getOneValue(sqlbv5);
//					if(tLiveValue!=null&&!tLiveValue.equals("")&&Integer.parseInt(tLiveValue)>0){
//						insured.setContenteFlag("1");//生调标记
//					}else{
//						insured.setContenteFlag("0");//生调标记
//					}
//					String HeathSql="select count(*) from LCPENotice where customerno='"+"?customerno?"+"'";
//					SQLwithBindVariables sqlbv6=new SQLwithBindVariables();
//					sqlbv6.sql(HeathSql);
//					sqlbv6.put("customerno", tLCInsuredSchema.getInsuredNo());
//					String tHealthValue = "";
//					tHealthValue = tempExeSQL.getOneValue(sqlbv6);
//					if(tHealthValue!=null&&!tHealthValue.equals("")&&Integer.parseInt(tHealthValue)>0){
//						insured.setHealthFlag("1");//体检标记
//					}else{
//						insured.setHealthFlag("0");//体检标记
//					}
					//tongmeng 2009-04-02 modify
					//是否需要体检
					String tNeedPEN_SQL = "";
					SQLwithBindVariables sqlbv7=new SQLwithBindVariables();
					SQLwithBindVariables sqlbv_mysql_call=new SQLwithBindVariables();
					ExeSQL tExeSQL_NeedPEN = new ExeSQL();
					String	strSQL = "";
					SSRS  result_call_sr = new SSRS(); //mysql查询存储过程准备结果
					String  str_call_result = "";   //mysql存储过程返回结果
					if(!FreeFlag)
					{	
						if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
							tNeedPEN_SQL = " select (case count(*) when 0 then 0 else 1 end) from lcinsured a "
									+ " where insuredno='"+"?insuredno?"+"' "
									+ " and contno='"+"?contno?"+"' "
									+ " and contno= HEALTHYAMNT(a.insuredno,a.contno,'0') ";
							sqlbv7.sql(tNeedPEN_SQL);
							sqlbv7.put("insuredno", tLCInsuredSchema.getInsuredNo());
							sqlbv7.put("contno", tLCInsuredSchema.getContNo());
						}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
							strSQL =  " select a.insuredno,a.contno from lcinsured a "
					                + " where insuredno='"+"?insuredno?"+"' "
					                + " and contno='"+"?contno?"+"' ";
							sqlbv_mysql_call.sql(strSQL);
							sqlbv_mysql_call.put("appntno", tLCInsuredSchema.getInsuredNo());
							sqlbv_mysql_call.put("contno", tLCInsuredSchema.getContNo());
							result_call_sr = tExeSQL_NeedPEN.execSQL(sqlbv_mysql_call);
							if(result_call_sr.getMaxRow()>0)
							{
								String insuredno = result_call_sr.GetText(1, 1);
								String contno = result_call_sr.GetText(1, 2);
								String strCall  = "{ call HEALTHYAMNT('?#@a#?','"+insuredno+"','"+contno+"','0') }";
								tExeSQL_NeedPEN = new ExeSQL();
								str_call_result = tExeSQL_NeedPEN.getOneValue(strCall);
							}
							tNeedPEN_SQL = " select (case count(*) when 0 then 0 else 1 end) from lcinsured a "
									+ " where insuredno='"+"?insuredno?"+"' "
									+ " and contno='"+"?contno?"+"' "
									+ " and contno= '"+0+"'"; //str_call_result
							sqlbv7.sql(tNeedPEN_SQL);
							sqlbv7.put("insuredno", tLCInsuredSchema.getInsuredNo());
							sqlbv7.put("contno", tLCInsuredSchema.getContNo());
						}
					}
					else
					{
						if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
							tNeedPEN_SQL = " select (case count(*) when 0 then 0 else 1 end) from lcappnt a "
					                + " where appntno='"+"?appntno?"+"' "
					                + " and contno='"+"?contno?"+"' "
					                + " and contno= HEALTHYAMNT(a.appntno,a.contno,'1') ";
							sqlbv7.sql(tNeedPEN_SQL);
							sqlbv7.put("appntno", tLCInsuredSchema.getInsuredNo());
							sqlbv7.put("contno", tLCInsuredSchema.getContNo());
						}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
							strSQL =  " select appntno,contno from lcappnt a "
									+ " where appntno='"+"?appntno?"+"' "
					                + " and contno='"+"?contno?"+"' ";
							sqlbv_mysql_call.sql(strSQL);
							sqlbv_mysql_call.put("appntno", tLCInsuredSchema.getInsuredNo());
							sqlbv_mysql_call.put("contno", tLCInsuredSchema.getContNo());
							result_call_sr = tExeSQL_NeedPEN.execSQL(sqlbv_mysql_call);
							if(result_call_sr.getMaxRow()>0)
							{
								String appntno = result_call_sr.GetText(1, 1);
								String contno = result_call_sr.GetText(1, 2);
								String strCall  = "{ call HEALTHYAMNT('?#@a#?','"+appntno+"','"+contno+"','0') }";
								logger.debug("8989");
								tExeSQL_NeedPEN = new ExeSQL();
								str_call_result = tExeSQL_NeedPEN.getOneValue(strCall);
							}
							tNeedPEN_SQL = " select (case count(*) when 0 then 0 else 1 end) from lcinsured a "
									+ " where appntno='"+"?appntno?"+"' "
					                + " and contno='"+"?contno?"+"' "
									+ " and contno= '"+str_call_result+"'";
							sqlbv7.sql(tNeedPEN_SQL);
							sqlbv7.put("insuredno", tLCInsuredSchema.getInsuredNo());
							sqlbv7.put("contno", tLCInsuredSchema.getContNo());
							
						}
						
					} 
					//insured.setNeedPEN(NeedPEN);
					String resoltProcedure = "";
					String tNeedPE = "";
					String tempNeedPE = "";
					tempNeedPE = tExeSQL_NeedPEN.getOneValue(sqlbv7);
					if(tempNeedPE!=null&&!tempNeedPE.equals("")){
						tNeedPE = tempNeedPE.replaceAll("[^0-9]", "");
					}else{
						insured.setNeedPEN("0");
					}
					if(tNeedPE!=null&&!tNeedPE.equals("")&&Integer.parseInt(tNeedPE)>0)
					{
						insured.setNeedPEN("1");
					}
					else
					{
						insured.setNeedPEN("0");
					}
					
					insured.setCreditGrade(tLCInsuredSchema.getCreditGrade());
					insured.setDegree(tLCInsuredSchema.getDegree());
					insured.setDiseaseSumAmnt(new Double(DSumDangerAmnt));//累积重疾风险保额
					String tDrinkFlagSql="select count(*)"
						 +" from lccustomerimpartparams"
						 +" where impartcode in ('A0103','A0503')"
						 +" and impartver in ('A01','A05') "
						 //+" and customernotype='1'"//被保人
						 +" and customerno='"
						 +"?customerno?"
						 +"' and contno='"+"?mContNo?"+"'";
					SQLwithBindVariables sqlbv8=new SQLwithBindVariables();
					sqlbv8.sql(tDrinkFlagSql);
					sqlbv8.put("customerno", tLCInsuredSchema.getInsuredNo());
					sqlbv8.put("mContNo", mContNo);
					if(mLCContSchema.getPrtNo()!=null && !mLCContSchema.getPrtNo().equals("") 
							&& mLCContSchema.getPrtNo().length()==14 && mLCContSchema.getPrtNo().substring(2,4).equals("51")
							&& tLCInsuredSchema.getSequenceNo()!=null && !tLCInsuredSchema.getSequenceNo().equals(""))//家庭单
					{
						tDrinkFlagSql = "select 0 from dual where 1=1 ";
						sqlbv8=new SQLwithBindVariables();
						sqlbv8.sql(tDrinkFlagSql);
						if(tLCInsuredSchema.getSequenceNo().equals("1")
								|| tLCInsuredSchema.getSequenceNo().equals("-1"))
						{
							tDrinkFlagSql="select count(*)"
							 +" from lccustomerimpartparams"
							 +" where impartcode = 'D0103'"
							 +" and impartver = 'D01' "
							 //+" and customernotype='1'"//被保人
							 +" and customerno='"+"?customerno?"
							 +"' and impartparamno in ('1','2','3')"
							 +" and contno='"+"?mContNo?"+"'";	
							sqlbv8=new SQLwithBindVariables();
							sqlbv8.sql(tDrinkFlagSql);
							sqlbv8.put("customerno", tLCInsuredSchema.getInsuredNo());
							sqlbv8.put("mContNo", mContNo);
						}
						else if(tLCInsuredSchema.getSequenceNo().equals("2"))
						{
							tDrinkFlagSql="select count(*)"
							 +" from lccustomerimpartparams"
							 +" where impartcode = 'D0103'"
							 +" and impartver = 'D01' "
							 //+" and customernotype='1'"//被保人
							 +" and customerno='"+"?customerno?"
							 +"' and impartparamno in ('4','5','6')"
							 +" and contno='"+"?mContNo?"+"'";	
							sqlbv8=new SQLwithBindVariables();
							sqlbv8.sql(tDrinkFlagSql);
							sqlbv8.put("customerno", tLCInsuredSchema.getInsuredNo());
							sqlbv8.put("mContNo", mContNo);
						}
						
					}
					String tDrinkFlag = "";
					insured.setDrinkFlag("0");
//
//					tDrinkFlag= tempExeSQL.getOneValue(sqlbv8);//是否有饮酒习惯
//					if(tDrinkFlag!=null&&!tDrinkFlag.equals("")&&Integer.parseInt(tDrinkFlag)>0){
//						insured.setDrinkFlag("1");//饮酒标记
//					}
//					else
//					{
//						insured.setDrinkFlag("0");
//					}
					//tongmeng 2009-03-26 add
					insured.setLiveBnfSelf("0");
//
//					//增加生存受益人是被保人本人,但姓名不一致
//					String tLiveSefSQL = " select (case count(*) when 0 then 0 else 1 end) from lcbnf a "
//						               + " where contno='"+"?contno?"+"' and bnftype='0' "
//						               + " and insuredno='"+"?insuredno?"+"' "
//						               + " and relationtoinsured is not null " 
//						               + " and relationtoinsured ='00' "
//						               + " and name not in " 
//						               + " (select name from lcinsured where contno=a.contno and insuredno=a.insuredno)";
//					String tValue = "";
//					SQLwithBindVariables sqlbv9=new SQLwithBindVariables();
//					sqlbv9.sql(tLiveSefSQL);
//					sqlbv9.put("contno", tLCInsuredSchema.getContNo());
//					sqlbv9.put("insuredno", tLCInsuredSchema.getInsuredNo());
//					String tempValue = "";
//					tempValue = tempExeSQL.getOneValue(sqlbv9);
//					if(tempValue!=null&&!tempValue.equals("")){
//						tValue = tempValue.replaceAll("[^0-9]", "");
//					}else{
//						insured.setLiveBnfSelf("0");
//					}
//					if(tValue!=null&&!tValue.equals("")&&Integer.parseInt(tValue)>0)
//					{
//						insured.setLiveBnfSelf("1");
//					}
//					else
//					{
//						insured.setLiveBnfSelf("0");
//					}
//					
					//SSumDieAmnt
					insured.setSumDieAmnt(new Double(SSumDieAmnt));
					//tongmeng 2009-04-02 add
					//增加身故受益人是被保人本人
					//tongmeng 2009-04-23 modify
					//校验身故受益人和被保人的姓名.
					insured.setDieBnfSelf("0");
//
//					String tDieSefSQL = " select (case count(*) when 0 then 0 else 1 end) from lcbnf a "
//			               + " where contno='"+"?contno?"+"' and bnftype='1' "
//			               + " and insuredno='"+"?insuredno?"+"' "
//			              // + " and relationtoinsured is not null " 
//			               //+ " and relationtoinsured ='00' ";
//			               + " and name=(select name from lcinsured where "
//			               + " contno=a.contno and insuredno=a.insuredno) ";
//					SQLwithBindVariables sqlbv10=new SQLwithBindVariables();
//					sqlbv10.sql(tDieSefSQL);
//					sqlbv10.put("contno", tLCInsuredSchema.getContNo());
//					sqlbv10.put("insuredno", tLCInsuredSchema.getInsuredNo());
//					tValue = "";
//					tempValue = tempExeSQL.getOneValue(sqlbv10);
//					if(tempValue!=null&&!tempValue.equals("")){
//						tValue = tempValue.replaceAll("[^0-9]", "");
//					}else{
//						insured.setDieBnfSelf("0");
//						tValue = "";
//					}
//					if(tValue!=null&&!tValue.equals("")&&Integer.parseInt(tValue)>0)
//					{
//						insured.setDieBnfSelf("1");
//					}
//					else
//					{
//						insured.setDieBnfSelf("0");
//					}
					//
					/** 此处用SQL查询出该合同该被保人下所有生存受益人受益比例之和、身故受益人受益比例之和*/
//					String tLiveBnfSql="select sum(bnflot)"+
//									" from lcbnf where insuredno = '"+"?insuredno?"+"'"+
//									" and bnftype = '0' and contno='"+"?mContNo?"+"'"+
//									" group by bnfgrade"+
//									" order by bnfgrade";//生存受益人
//					String tDeadBnfSql="select sum(bnflot)"+
//									" from lcbnf where insuredno = '"+"?insuredno?"+"'"+
//									" and bnftype = '1' and contno='"+"?mContNo?"+"'"+
//									" group by bnfgrade"+
//									" order by bnfgrade";//身故受益人
//					SQLwithBindVariables sqlbv11=new SQLwithBindVariables();
//					SQLwithBindVariables sqlbv12=new SQLwithBindVariables();
//					sqlbv11.sql(tLiveBnfSql);
//					sqlbv11.put("insuredno", tLCInsuredSchema.getInsuredNo());
//					sqlbv11.put("mContNo", mContNo);
//					sqlbv12.sql(tDeadBnfSql);
//					sqlbv12.put("insuredno", tLCInsuredSchema.getInsuredNo());
//					sqlbv12.put("mContNo", mContNo);
//					SSRS LiveBnfs = new SSRS();//生存受益人
//					SSRS DeadBnfs = new SSRS();//身故受益人
//					LiveBnfs=tempExeSQL.execSQL(sqlbv11);
//					DeadBnfs=tempExeSQL.execSQL(sqlbv12);
//					if(LiveBnfs.MaxRow>0){
//						if(!(LiveBnfs.GetText(1, 1)==null||"".equals(LiveBnfs.GetText(1, 1)))){
//							insured.setFirstSurviveBnfSum(Double.valueOf(LiveBnfs.GetText(1, 1)));//第一顺序生存受益人受益比例之和
//						}
//					}
//					if(LiveBnfs.MaxRow>1){
//						if(!(LiveBnfs.GetText(2, 1)==null||"".equals(LiveBnfs.GetText(2, 1)))){
//							insured.setSecondSurviveBnfSum(Double.valueOf(LiveBnfs.GetText(2, 1)));
//						}
//					}
//					if(LiveBnfs.MaxRow>2){
//						if(!(LiveBnfs.GetText(3, 1)==null||"".equals(LiveBnfs.GetText(3, 1)))){
//							insured.setThirdSurviveBnfSum(Double.valueOf(LiveBnfs.GetText(3, 1)));
//						}
//					}
//					if(LiveBnfs.MaxRow>3){
//						if(!(LiveBnfs.GetText(4, 1)==null||"".equals(LiveBnfs.GetText(4, 1)))){
//							insured.setFourthSurviveBnfSum(Double.valueOf(LiveBnfs.GetText(4, 1)));
//						}
//					}
					//tongmeng 2009-03-26 add
					//增加是否录入身故受益人
					insured.setDieBnfFlag("0");
//
//					if(DeadBnfs.MaxRow>0)
//					{
//						insured.setDieBnfFlag("1");
//					}
//					else
//					{
//						insured.setDieBnfFlag("0");
//					}
//					
					
//					if(DeadBnfs.MaxRow>0){
//						if(!(DeadBnfs.GetText(1, 1)==null||"".equals(DeadBnfs.GetText(1, 1)))){
//							insured.setFirstDieBnfSum(Double.valueOf(DeadBnfs.GetText(1, 1)));
//						}
//					}
//					if(DeadBnfs.MaxRow>1){
//						if(!(DeadBnfs.GetText(2, 1)==null||"".equals(DeadBnfs.GetText(2, 1)))){
//							insured.setSecondDieBnfSum(Double.valueOf(DeadBnfs.GetText(2, 1)));
//						}
//					}
//					if(DeadBnfs.MaxRow>2){
//						if(!(DeadBnfs.GetText(3, 1)==null||"".equals(DeadBnfs.GetText(3, 1)))){
//							insured.setThirdDieBnfSum(Double.valueOf(DeadBnfs.GetText(3, 1)));
//						}
//					}
//					if(DeadBnfs.MaxRow>3){
//						if(!(DeadBnfs.GetText(4, 1)==null||"".equals(DeadBnfs.GetText(4, 1)))){
//							insured.setFourthDieBnfSum(Double.valueOf(DeadBnfs.GetText(4, 1)));
//						}
//					}
					int tInsAge = PubFun.calInterval(tLCInsuredSchema.getBirthday(), tLCContSchema.getPolApplyDate(), "Y");
					//---
					insured.setInsuredAppAge(Double.valueOf(String.valueOf(tInsAge)));//投保年龄
					insured.setInsuredStat(tLCInsuredSchema.getInsuredStat());
					if(!((tLCInsuredSchema.getJoinCompanyDate()==null))||"".equals(tLCInsuredSchema.getJoinCompanyDate())){
						theDate=tLCInsuredSchema.getJoinCompanyDate()+" 00:00:00";
						insured.setJoinCompanyDate(sdf.parse(theDate));
					}
					insured.setLicense(tLCInsuredSchema.getLicense());
					insured.setLicenseType(tLCInsuredSchema.getLicenseType());
					insured.setLifeSumAmnt(Double.valueOf(String.valueOf(LSumDangerAmnt)));//累计寿险风险保额
					insured.setMarriage(tLCInsuredSchema.getMarriage());
					if(!(tLCInsuredSchema.getMarriageDate()==null||"".equals(tLCInsuredSchema.getMarriageDate()))){
						theDate=tLCInsuredSchema.getMarriageDate()+" 00:00:00";
						insured.setMarriageDate(sdf.parse(theDate));
					}
					insured.setMedicalSumAmnt(new Double(SSumDangerAmnt));//累计意外医疗风险保额
					insured.setNationality(tLCInsuredSchema.getNationality());
					insured.setNativePlace(tLCInsuredSchema.getNativePlace());
//					//下面的SQL查询出既往加费次数
//					String tOaddFeeSql="select sum(A.y) from ( "
//                                      + " select contno x,(case count(*) when 0 then 0 else 1 end) y from lcprem "
//                                      + " where polno in (select polno from lcpol where insuredno='"+"?insuredno?"+"' ) "
//                                      + " and payplancode like '000000%%' and contno<>'"+"?contno?"+"' group by contno "
//                                      + " ) A ";
//					SQLwithBindVariables sqlbv13=new SQLwithBindVariables();
//					sqlbv13.sql(tOaddFeeSql);
//					sqlbv13.put("insuredno", tLCInsuredSchema.getInsuredNo());
//					sqlbv13.put("contno", this.mContNo);
					String OAddFeeTimes="0";
					if(!(OAddFeeTimes==null||"".equals(OAddFeeTimes))){
						insured.setOAddFeeTimes(Double.valueOf(OAddFeeTimes));//既往投保加费次数
					}
					insured.setOccupationCode(tLCInsuredSchema.getOccupationCode());
					insured.setOccupationType(tLCInsuredSchema.getOccupationType());
//					String tSQL_ChangePol = "select count(*) from lcuwmaster where insuredno='"+"?insuredno?"+"' "
//						                  + " and changepolflag<>'0' and contno<>'"+"?contno?"+"' ";
					String tChangeValue = "0";
//					SQLwithBindVariables sqlbv14=new SQLwithBindVariables();
//					sqlbv14.sql(tSQL_ChangePol);
//					sqlbv14.put("insuredno", tLCInsuredSchema.getInsuredNo());
//					sqlbv14.put("contno", this.mContNo);
//					tChangeValue = tempExeSQL.getOneValue(sqlbv14);
					if(tChangeValue!=null&&!tChangeValue.equals(""))
					{
						insured.setOChangeFlag(new Double(tChangeValue));//既往承保计划变更次数
					}
					//下面的SQL查询是否有既往理赔记录
					insured.setOClaimFlag("0");//既往理赔记录标记
//
//					String tOClaimSql="select count(*) from llcase where customerno='"+"?customerno?"+"'";
//					String OClaimFlag = "0";
//					SQLwithBindVariables sqlbv15=new SQLwithBindVariables();
//					sqlbv15.sql(tOClaimSql);
//					sqlbv15.put("customerno", tLCInsuredSchema.getInsuredNo());
//					String tempOClaimFlag = "";
//					tempOClaimFlag =tempExeSQL.getOneValue(sqlbv15);
//					if(tempOClaimFlag!=null&&!tempOClaimFlag.equals("")){
//						OClaimFlag = tempOClaimFlag.replaceAll("[^0-9]", "");
//					}else{
//						insured.setOClaimFlag("0");//既往理赔记录标记
//					}
//					if(OClaimFlag!=null&&!OClaimFlag.equals("")&&Integer.parseInt(OClaimFlag)>0){
//						insured.setOClaimFlag("1");//既往理赔记录标记
//					}else{
//						insured.setOClaimFlag("0");//既往理赔记录标记
//					}
					insured.setODeferTimes(Double.valueOf("0"));//既往延期承保次数 
//					String ODeferSql="select count(*) from lccont where uwflag='2' and contno in "
//									+ "(select contno from lcpol where insuredno='"+"?insuredno?"+"' ) "
//									+ " and contno<>'"+"?contno?"+"' ";
//					SQLwithBindVariables sqlbv16=new SQLwithBindVariables();
//					sqlbv16.sql(ODeferSql);
//					sqlbv16.put("insuredno", tLCInsuredSchema.getInsuredNo());
//					sqlbv16.put("contno", this.mContNo);
//					String tODeferTimes=tempExeSQL.getOneValue(sqlbv16);
//					if(!(tODeferTimes==null||"".equals(tODeferTimes))){
//						insured.setODeferTimes(Double.valueOf(tODeferTimes));//既往延期承保次数 
//					}
					insured.setOHealthFlag("0");//既往保单体检标记
					insured.setOHealthTimes(Double.valueOf("0"));
//					String oHealthSql="select count(*) from LCPENotice where contno!='?mContNo?' and customerno='"+"?customerno?"+"'";
//					String oHealthFlag = "";
//					SQLwithBindVariables sqlbv17=new SQLwithBindVariables();
//					sqlbv17.sql(oHealthSql);
//					sqlbv17.put("mContNo", mContNo);
//					sqlbv17.put("customerno", tLCInsuredSchema.getInsuredNo());
//					oHealthFlag=tempExeSQL.getOneValue(sqlbv17);
//					if(oHealthFlag!=null&&!oHealthFlag.equals("")&&Integer.parseInt(oHealthFlag)>0){
//						insured.setOHealthFlag("1");//既往保单体检标记
//						insured.setOHealthTimes(Double.valueOf(oHealthFlag));//既往投保体检次数
//					}else{
//						insured.setOHealthFlag("0");//既往保单体检标记
//						insured.setOHealthTimes(Double.valueOf("0"));
//					}
					insured.setFirstOCPType("0");//既往职业类别为一级
//
//					String OInvaliSql="select count(*) from lccontstate where statetype = 'Available'"+
//							   	" and state = '1' and enddate <> null"+
//							    " and contno in (select contno from lcinsured where insuredno = '"+
//							    "?insuredno?"+"')";
//					SQLwithBindVariables sqlbv18=new SQLwithBindVariables();
//					sqlbv18.sql(OInvaliSql);
//					sqlbv18.put("insuredno", tLCInsuredSchema.getInsuredNo());
//					String tOInvaliTimes=tempExeSQL.getOneValue(sqlbv18);
//					if(!(tOInvaliTimes==null||"".equals(tOInvaliTimes))){
//						insured.setOInvaliTimes(Double.valueOf(tOInvaliTimes));//既往保单失效次数 
//					}
//					String FirstOCPSql="select count(*) from lcinsured where OccupationType='1' and insuredno='" +
//								"?insuredno?"+"' and contno<>'"+"?contno?"+"' ";
//					String tOOccupationType = "";
//					SQLwithBindVariables sqlbv19=new SQLwithBindVariables();
//					sqlbv19.sql(FirstOCPSql);
//					sqlbv19.put("insuredno", insured.getInsuredNo());
//					sqlbv19.put("contno", this.mContNo);
//					tOOccupationType=tempExeSQL.getOneValue(sqlbv19);
//					if(tOOccupationType!=null&&!tOOccupationType.equals("")&&Integer.parseInt(tOOccupationType)>0){
//						insured.setFirstOCPType("1");//既往职业类别为一级
//					}else{
//						insured.setFirstOCPType("0");//既往职业类别为一级
//					}
					insured.setSecondOCPType("0");//既往职业类别为二级
//
//					String SecondOCPSql="select count(*) from lcinsured where OccupationType='2' and insuredno='" +
//								"?insuredno?"+"' and contno<>'"+"?contno?"+"' ";
//					String tSecondOCPType = "";
//					SQLwithBindVariables sqlbv20=new SQLwithBindVariables();
//					sqlbv20.sql(SecondOCPSql);
//					sqlbv20.put("insuredno", insured.getInsuredNo());
//					sqlbv20.put("contno", this.mContNo);
//					tSecondOCPType=tempExeSQL.getOneValue(sqlbv20);
//					if(tSecondOCPType!=null&&!tSecondOCPType.equals("")&&Integer.parseInt(tSecondOCPType)>0){
//						insured.setSecondOCPType("1");//既往职业类别为二级
//					}else{
//						insured.setSecondOCPType("0");//既往职业类别为二级
//					}
					insured.setThirdOCPType("0");//既往职业类别为三级
//
//					String ThirdOCPSql="select count(*) from lcinsured where OccupationType='3' and insuredno='" +
//								"?insuredno?"+"' and contno<>'"+"?contno?"+"' ";
//					String tThirdOCPType = "";
//					SQLwithBindVariables sqlbv21=new SQLwithBindVariables();
//					sqlbv21.sql(ThirdOCPSql);
//					sqlbv21.put("insuredno", insured.getInsuredNo());
//					sqlbv21.put("contno", this.mContNo);
//					tThirdOCPType=tempExeSQL.getOneValue(sqlbv21);
//					if(tThirdOCPType!=null&&!tThirdOCPType.equals("")&&Integer.parseInt(tThirdOCPType)>0){
//						insured.setThirdOCPType("1");//既往职业类别为三级
//					}else{
//						insured.setThirdOCPType("0");//既往职业类别为三级
//					}
					insured.setFourthOCPType("0");//既往职业类别为四级
//
//					String FourthOCPSql="select count(*) from lcinsured where OccupationType='4' and insuredno='" +
//								"?insuredno?"+"' and contno<>'"+"?contno?"+"' ";
//					String tFourthOCPType = "";
//					SQLwithBindVariables sqlbv22=new SQLwithBindVariables();
//					sqlbv22.sql(FourthOCPSql);
//					sqlbv22.put("insuredno", insured.getInsuredNo());
//					sqlbv22.put("contno", this.mContNo);
//					tFourthOCPType=tempExeSQL.getOneValue(sqlbv22);
//					if(tFourthOCPType!=null&&!tFourthOCPType.equals("")&&Integer.parseInt(tFourthOCPType)>0){
//						insured.setFourthOCPType("1");//既往职业类别为四级
//					}else{
//						insured.setFourthOCPType("0");//既往职业类别为四级
//					}
					insured.setFifthOCPType("0");//既往职业类别为五级
//
//					String FifthOCPSql="select  count(*) from lcinsured where OccupationType='5' and insuredno='" +
//								"?insuredno?"+"' and contno<>'"+"?contno?"+"' ";
//					String tFifthOCPType = "";
//					SQLwithBindVariables sqlbv23=new SQLwithBindVariables();
//					sqlbv23.sql(FifthOCPSql);
//					sqlbv23.put("insuredno", insured.getInsuredNo());
//					sqlbv23.put("contno", this.mContNo);
//					tFifthOCPType=tempExeSQL.getOneValue(sqlbv23);
//					if(tFifthOCPType!=null&&!tFifthOCPType.equals("")&&Integer.parseInt(tFifthOCPType)>0){
//						insured.setFifthOCPType("1");//既往职业类别为五级
//					}else{
//						insured.setFifthOCPType("0");//既往职业类别为五级
//					}
					insured.setSixthOCPType("0");//既往职业类别为六级
//
//					String SixthOCPSql="select count(*) from lcinsured where OccupationType='6' and insuredno='" +
//								"?insuredno?"+"' and contno<>'"+"?contno?"+"' ";
//					String tSixthOCPType = "";
//					SQLwithBindVariables sqlbv24=new SQLwithBindVariables();
//					sqlbv24.sql(SixthOCPSql);
//					sqlbv24.put("insuredno", insured.getInsuredNo());
//					sqlbv24.put("contno", this.mContNo);
//					tSixthOCPType=tempExeSQL.getOneValue(sqlbv24);
//					if(tSixthOCPType!=null&&!tSixthOCPType.equals("")&&Integer.parseInt(tSixthOCPType)>0){
//						insured.setSixthOCPType("1");//既往职业类别为六级
//					}else{
//						insured.setSixthOCPType("0");//既往职业类别为六级
//					}
					insured.setNOOCPType("0");//既往职业类别为拒保类职业
//
//					String NOOCPSql="select count(*) from lcinsured where OccupationType='z' and insuredno='" +
//								"?insuredno?"+"' and contno<>'"+"?contno?"+"' ";
//					String tNOOCPType = "";
//					SQLwithBindVariables sqlbv25=new SQLwithBindVariables();
//					sqlbv25.sql(NOOCPSql);
//					sqlbv25.put("insuredno", insured.getInsuredNo());
//					sqlbv25.put("contno", this.mContNo);
//					tNOOCPType=tempExeSQL.getOneValue(sqlbv25);
//					if(tNOOCPType!=null&&!tNOOCPType.equals("")&&Integer.parseInt(tNOOCPType)>0){
//						insured.setNOOCPType("1");//既往职业类别为拒保类职业
//					}else{
//						insured.setNOOCPType("0");//既往职业类别为拒保类职业
//					}
					insured.setNegativeOCPType("0");//既往职业类别为-1级
//
//					String NegativeOCPSql="select count(*) from lcinsured where OccupationType='-1' and insuredno='" +
//								"?insuredno?"+"' and contno<>'"+"?contno?"+"' ";
//					String tNegativeOCPType = "";
//					SQLwithBindVariables sqlbv26=new SQLwithBindVariables();
//					sqlbv26.sql(NegativeOCPSql);
//					sqlbv26.put("insuredno", insured.getInsuredNo());
//					sqlbv26.put("contno", this.mContNo);
//					tNegativeOCPType=tempExeSQL.getOneValue(sqlbv26);
//					if(tNegativeOCPType!=null&&!tNegativeOCPType.equals("")&&Integer.parseInt(tNegativeOCPType)>0){
//						insured.setNegativeOCPType("1");//既往职业类别为-1级
//					}else{
//						insured.setNegativeOCPType("0");//既往职业类别为-1级
//					}
					//tongmeng 2009-03-25 add
					//既往职业类别高于本次
					insured.setOOMaxType("0");//既往职业类别高于本次
//
//					String OTypeCPSql = "select count(*) from lcinsured where "
//									 + " ((OccupationType='-1' and '"+"?OccupationType?"+"'<>'-1' )"
//									 + " or (OccupationType<>'-1' and  OccupationType>"+"?OccupationType?"+" ) )"
//									 + " and insuredno='" +"?insuredno?"+"' and contno<>'"+"?contno?"+"' ";
//					String tOOType = "";
//					SQLwithBindVariables sqlbv27=new SQLwithBindVariables();
//					sqlbv27.sql(OTypeCPSql);
//					sqlbv27.put("OccupationType", tLCInsuredSchema.getOccupationType());
//					sqlbv27.put("insuredno", insured.getInsuredNo());
//					sqlbv27.put("contno", this.mContNo);
//					tOOType=tempExeSQL.getOneValue(sqlbv27);
//					if(tOOType!=null&&!tOOType.equals("")&&Integer.parseInt(tOOType)>0){
//						insured.setOOMaxType("1");//既往职业类别高于本次
//					}else{
//						insured.setOOMaxType("0");//既往职业类别高于本次
//					}
//					
					
					insured.setLateOUWFlag("0");//既往核保结论为延期
//
//					String LateOUWSql="select count(*) from lccont where uwflag='2' "
//						             + " and contno in (select contno from lcpol where insuredno='"+"?insuredno?"+"')"
//						             + " and contno!='"+"?mContNo?"+"'";
//					String tLateOUWFlag = "";
//					SQLwithBindVariables sqlbv28=new SQLwithBindVariables();
//					sqlbv28.sql(LateOUWSql);
//					sqlbv28.put("insuredno", insured.getInsuredNo());
//					sqlbv28.put("mContNo", mContNo);
//					tLateOUWFlag=tempExeSQL.getOneValue(sqlbv28);
//					if(tLateOUWFlag!=null&&!tLateOUWFlag.equals("")&&Integer.parseInt(tLateOUWFlag)>0){
//						insured.setLateOUWFlag("1");//既往核保结论为延期
//					}else{
//						insured.setLateOUWFlag("0");//既往核保结论为延期
//					}
					insured.setStandarOUWFlag("0");//既往核保结论为标准承保
//
//					String StandarOUWSql= " select count(*) from lccont where uwflag='9' "
//										+ " and contno in (select contno from lcpol where insuredno='"+"?insuredno?"+"')"
//										+ " and contno!='"+"?mContNo?"+"'";
//					String tStandarOUWFlag = "";
//					SQLwithBindVariables sqlbv29=new SQLwithBindVariables();
//					sqlbv29.sql(StandarOUWSql);
//					sqlbv29.put("insuredno", insured.getInsuredNo());
//					sqlbv29.put("mContNo", mContNo);
//					tStandarOUWFlag=tempExeSQL.getOneValue(sqlbv29);
//					if(tStandarOUWFlag!=null&&!tStandarOUWFlag.equals("")&&Integer.parseInt(tStandarOUWFlag)>0){
//						insured.setStandarOUWFlag("1");//既往核保结论为标准承保
//					}else{
//						insured.setStandarOUWFlag("0");//既往核保结论为标准承保
//					}
					insured.setRejectOUWFlag("0");//既往核保结论为拒保
					insured.setORejeceTimes(Double.valueOf("0"));//既往拒保次数 
//					
//					String SStandarOUWSql= " select count(*) from lccont where uwflag='4' "
//						+ " and contno in (select contno from lcpol where insuredno='"+"?insuredno?"+"')"
//						+ " and contno!='"+"?mContNo?"+"'";
//					String tSStandarOUWFlag = "";
//					SQLwithBindVariables sqlbv30=new SQLwithBindVariables();
//					sqlbv30.sql(SStandarOUWSql);
//					sqlbv30.put("insuredno", insured.getInsuredNo());
//					sqlbv30.put("mContNo", mContNo);
////					tSStandarOUWFlag=tempExeSQL.getOneValue(SStandarOUWSql);
////					if(tSStandarOUWFlag!=null&&!tSStandarOUWFlag.equals("")&&Integer.parseInt(tSStandarOUWFlag)>0){
////						insured.setSecStandardUW("1");//既往核保结论为次标准承保
////					}else{
////						insured.setSecStandardUW("0");
////					}
//					String RejectOUWSql= "select count(*) from lccont where uwflag='1'" 
//									   + " and contno in (select contno from lcpol where insuredno='"+"?insuredno?"+"')"
//									   + " and contno!='"+"?mContNo?"+"'";
//					String tRejectOUWFlag = "";
//					SQLwithBindVariables sqlbv31=new SQLwithBindVariables();
//					sqlbv31.sql(RejectOUWSql);
//					sqlbv31.put("insuredno", insured.getInsuredNo());
//					sqlbv31.put("mContNo", mContNo);
//					tRejectOUWFlag=tempExeSQL.getOneValue(sqlbv31);
//					if(tRejectOUWFlag!=null&&!tRejectOUWFlag.equals("")&&Integer.parseInt(tRejectOUWFlag)>0){
//						insured.setRejectOUWFlag("1");//既往核保结论为拒保
//						insured.setORejeceTimes(Double.valueOf(tRejectOUWFlag));//既往拒保次数 
//					}else{
//						insured.setRejectOUWFlag("0");//既往核保结论为拒保
//						insured.setORejeceTimes(Double.valueOf("0"));//既往拒保次数 
//					}
					insured.setBackOUWFlag("0");//既往核保结论为撤单
//
//					String BackOUWSql = " select count(*) from lccont where uwflag='a' "
//									  + " and contno in (select contno from lcpol where insuredno='"+"?insuredno?"+"')"
//									  + " and contno!='"+"?mContNo?"+"'";
//					String tBackOUWFlag = "";
//					SQLwithBindVariables sqlbv32=new SQLwithBindVariables();
//					sqlbv32.sql(BackOUWSql);
//					sqlbv32.put("insuredno", insured.getInsuredNo());
//					sqlbv32.put("mContNo", mContNo);
//					tBackOUWFlag=tempExeSQL.getOneValue(sqlbv32);
//					if(tBackOUWFlag!=null&&!tBackOUWFlag.equals("")&&Integer.parseInt(tBackOUWFlag)>0){
//						insured.setBackOUWFlag("1");//既往核保结论为撤单
//					}else{
//						insured.setBackOUWFlag("0");//既往核保结论为撤单
//					}
					insured.setAddOUWFlag("0");//既往核保结论为加费承保
//
//					String AddOUWSql= "select count(*) from lccont where uwflag='3' " 
//						 		    + " and contno in (select contno from lcpol where insuredno='"+"?insuredno?"+"')"
//						 		    + " and contno!='"+"?mContNo?"+"'";
//					String tAddOUWFlag = "";
//					SQLwithBindVariables sqlbv33=new SQLwithBindVariables();
//					sqlbv33.sql(AddOUWSql);
//					sqlbv33.put("insuredno", insured.getInsuredNo());
//					sqlbv33.put("mContNo", mContNo);
//					tAddOUWFlag=tempExeSQL.getOneValue(sqlbv33);
//					if(tAddOUWFlag!=null&&!tAddOUWFlag.equals("")&&Integer.parseInt(tAddOUWFlag)>0){
//						insured.setAddOUWFlag("1");//既往核保结论为加费承保
//					}else{
//						insured.setAddOUWFlag("0");//既往核保结论为加费承保
//					}
					insured.setSpecialOUWFlag("0");//既往核保结论为特约承保
//
//					String SpecialOUWSql = "select count(*) from lccont where uwflag='4' "
//										 + " and contno in (select contno from lcpol where insuredno='"+"?insuredno?"+"')"
//										 + " and contno!='"+"?mContNo?"+"'";
//
//					String tSpecialOUWFlag = "";
//					SQLwithBindVariables sqlbv34=new SQLwithBindVariables();
//					sqlbv34.sql(SpecialOUWSql);
//					sqlbv34.put("insuredno", insured.getInsuredNo());
//					sqlbv34.put("mContNo", mContNo);
//					tSpecialOUWFlag=tempExeSQL.getOneValue(sqlbv34);
//					if(tSpecialOUWFlag!=null&&!tSpecialOUWFlag.endsWith("")&&Integer.parseInt(tSpecialOUWFlag)>0){
//						insured.setSpecialOUWFlag("1");//既往核保结论为特约承保
//					}else{
//						insured.setSpecialOUWFlag("0");//既往核保结论为特约承保
//					}
					insured.setOReTimes(Double.valueOf("0"));//既往保单复效次数 
//
//					String OOReSql= " select count(*) from lpedoritem where edortype='RE' "
//								  + " and contno in (select contno from lcinsured where insuredno = '"+
//							    "?insuredno?"+"') and contno<>'"+"?contno?"+"' ";
//					SQLwithBindVariables sqlbv35=new SQLwithBindVariables();
//					sqlbv35.sql(OOReSql);
//					sqlbv35.put("insuredno", tLCInsuredSchema.getInsuredNo());
//					sqlbv35.put("contno", this.mContNo);
//					String tOReTimes=tempExeSQL.getOneValue(sqlbv35);
//					if(!(tOReTimes==null||"".equals(tOReTimes))){
//						insured.setOReTimes(Double.valueOf(tOReTimes));//既往保单复效次数 
//					}
					insured.setOReleveTimes(Double.valueOf("0"));//既往保单合同解除次数
					insured.setOWithdrawTimes(Double.valueOf("0"));
//					
//					String OReleveSql="select count(*),1 from lpedoritem where edortype in ('CT','WT','XT') " +
//									 "AND EDORSTATE='0' and contno in (select contno from lcinsured where " +
//									 " insuredno='"+"?insuredno?"+"')";
//					SQLwithBindVariables sqlbv36=new SQLwithBindVariables();
//					sqlbv36.sql(OReleveSql);
//					sqlbv36.put("insuredno", tLCInsuredSchema.getInsuredNo());
//					String tOReleveTimes=tempExeSQL.getOneValue(sqlbv36);
//					if(!(tOReleveTimes==null||"".equals(tOReleveTimes))){
//						insured.setOReleveTimes(Double.valueOf(tOReleveTimes));//既往保单合同解除次数
//						insured.setOWithdrawTimes(Double.valueOf(tOReleveTimes));//既往撤件次数????
//					}
					
					String tOSurrenderTimes="0";
					insured.setOSurrenderTimes(Double.valueOf(tOSurrenderTimes));//既往退保次数 
					insured.setPosition(tLCInsuredSchema.getPosition());
					insured.setRelationToAppnt(tLCInsuredSchema.getRelationToAppnt());
					insured.setRgtAddress(tLCInsuredSchema.getRgtAddress());
					insured.setSalary(Double.valueOf(String.valueOf(tLCInsuredSchema.getSalary())));
					insured.setSex(tLCInsuredSchema.getSex());
//					//计算每天吸烟量
//					String tSmokeSql ="select (case when max(impartparam) is not null then max(impartparam) else '0' end)"
//						 +" from lccustomerimpartparams"
//						 +" where impartcode in ('A0102','A0502')"
//						 +" and impartver in ('A01','A05') "
//						 +" and impartparamno in ('1','3')"
//						 //+" and customernotype='1'"//被保人
//						 +" and customerno='"
//						 +"?customerno?"
//						 +"' and contno='"+"?mContNo?"+"'";	
//					SQLwithBindVariables sqlbv38=new SQLwithBindVariables();
//					sqlbv38.sql(tSmokeSql);
//					sqlbv38.put("customerno", tLCInsuredSchema.getInsuredNo());
//					sqlbv38.put("mContNo", mContNo);
//					if(mLCContSchema.getPrtNo()!=null && !mLCContSchema.getPrtNo().equals("") 
//							&& mLCContSchema.getPrtNo().length()==14 && mLCContSchema.getPrtNo().substring(2,4).equals("51")
//							&& tLCInsuredSchema.getSequenceNo()!=null && !tLCInsuredSchema.getSequenceNo().equals(""))//家庭单
//					{
//						tSmokeSql = "select 0 from dual where 1=1 ";
//						sqlbv38=new SQLwithBindVariables();
//						sqlbv38.sql(tSmokeSql);
//						if(tLCInsuredSchema.getSequenceNo().equals("1")
//								|| tLCInsuredSchema.getSequenceNo().equals("-1"))
//						{
//							tSmokeSql ="select (case when max(impartparam) is not null then max(impartparam) else '0' end)"
//								 +" from lccustomerimpartparams"
//								 +" where impartcode = 'D0102'"
//								 +" and impartver = 'D01' "
//								 +" and impartparamno ='1'"
//								 //+" and customernotype='1'"//被保人
//								 +" and customerno='"
//								 +"?customerno?"
//								 +"' and contno='"+"?mContNo?"+"'";		
//							sqlbv38=new SQLwithBindVariables();
//							sqlbv38.sql(tSmokeSql);
//							sqlbv38.put("customerno", tLCInsuredSchema.getInsuredNo());
//							sqlbv38.put("mContNo", mContNo);
//						}
//						else if(tLCInsuredSchema.getSequenceNo().equals("2"))
//						{
//							tSmokeSql ="select (case when max(impartparam) is not null then max(impartparam) else '0' end)"
//								 +" from lccustomerimpartparams"
//								 +" where impartcode = 'D0102'"
//								 +" and impartver = 'D01' "
//								 +" and impartparamno ='3'"
//								 //+" and customernotype='1'"//被保人
//								 +" and customerno='"
//								 +"?customerno?"
//								 +"' and contno='"+"?mContNo?"+"'";		
//							sqlbv38=new SQLwithBindVariables();
//							sqlbv38.sql(tSmokeSql);
//							sqlbv38.put("customerno", tLCInsuredSchema.getInsuredNo());
//							sqlbv38.put("mContNo", mContNo);
//						}
//						
//					}
//					try {
//						String tSmoke = "";
////						String tempSmoke = 
//						tSmoke = tempExeSQL.getOneValue(sqlbv38);//吸烟量
//						logger.debug("tSmoke = "+tSmoke);
////						if(tempSmoke!=null&&!tempSmoke.equals("")){
////							tSmoke = tempSmoke.replaceAll("[^0-9]", "");
////						}else{
////							insured.setSmokeAmount(new Double("0"));//每天吸烟量
////						}
//						if(tSmoke==null||tSmoke.equals(""))
//						{
//							insured.setSmokeAmount(new Double("0"));//每天吸烟量
//						}
//						else
//						{
//							insured.setSmokeAmount(new Double(tSmoke));//每天吸烟量
//						}
//					} catch (Exception e) {
//						CError.buildErr(this, mContNo+"吸烟量数据异常！");
//						e.printStackTrace();
//					}
//					String tSmokeFlagSql="select count(*)"
//						 +" from lccustomerimpartparams"
//						 +" where impartcode in ('A0102','A0502')"
//						 +" and impartver in ('A01','A05') "
//						 //+" and customernotype='1'"//被保人
//						 +" and impartparamno in ('1','2')"
//						 +" and customerno='"
//						 +"?customerno?"
//						 +"' and contno='"+"?mContNo?"+"'";
//					SQLwithBindVariables sqlbv39=new SQLwithBindVariables();
//					sqlbv39.sql(tSmokeFlagSql);
//					sqlbv39.put("customerno", tLCInsuredSchema.getInsuredNo());
//					sqlbv39.put("mContNo", mContNo);
//					if(mLCContSchema.getPrtNo()!=null && !mLCContSchema.getPrtNo().equals("") 
//							&& mLCContSchema.getPrtNo().length()==14 && mLCContSchema.getPrtNo().substring(2,4).equals("51")
//							&& tLCInsuredSchema.getSequenceNo()!=null && !tLCInsuredSchema.getSequenceNo().equals(""))//家庭单
//					{
//						tSmokeFlagSql = "select 0 from dual where 1=1 ";
//						sqlbv39=new SQLwithBindVariables();
//						sqlbv39.sql(tSmokeFlagSql);
//						if(tLCInsuredSchema.getSequenceNo().equals("1")
//								|| tLCInsuredSchema.getSequenceNo().equals("-1"))
//						{
//							tSmokeFlagSql="select count(*)"
//							 +" from lccustomerimpartparams"
//							 +" where impartcode = 'D0102'"
//							 +" and impartver = 'D01' "
//							 //+" and customernotype='1'"//被保人
//							 +" and customerno='"
//							 +"?customerno?"
//							 +"' and impartparamno in ('1','2')"
//							 +" and contno='"+"?mContNo?"+"'";	
//							sqlbv39=new SQLwithBindVariables();
//							sqlbv39.sql(tSmokeFlagSql);
//							sqlbv39.put("customerno", tLCInsuredSchema.getInsuredNo());
//							sqlbv39.put("mContNo", mContNo);
//						}
//						else if(tLCInsuredSchema.getSequenceNo().equals("2"))
//						{
//							tSmokeFlagSql="select count(*)"
//							 +" from lccustomerimpartparams"
//							 +" where impartcode = 'D0103'"
//							 +" and impartver = 'D01' "
//							 //+" and customernotype='1'"//被保人
//							 +" and customerno='"
//							 +"?customerno?"
//							 +"' and impartparamno in ('3','4')"
//							 +" and contno='"+"?mContNo?"+"'";	
//							sqlbv39=new SQLwithBindVariables();
//							sqlbv39.sql(tSmokeFlagSql);
//							sqlbv39.put("customerno", tLCInsuredSchema.getInsuredNo());
//							sqlbv39.put("mContNo", mContNo);
//						}
//						
//					}
//					String tSmokeFlag = tempExeSQL.getOneValue(sqlbv39);//是否有吸烟习惯
//					if(tSmokeFlag!=null&&!tSmokeFlag.equals("")&&Integer.parseInt(tSmokeFlag)>0){
//						insured.setSmokeFlag("1");
//					}
//					else
//					{
//						insured.setSmokeFlag("0");
//					}
					insured.setSocialInsuNo(tLCInsuredSchema.getSocialInsuFlag());
					if(!(tLCInsuredSchema.getStartWorkDate()==null||"".equals(tLCInsuredSchema.getStartWorkDate()))){
						theDate=tLCInsuredSchema.getStartWorkDate()+" 00:00:00";
						insured.setStartWorkDate(sdf.parse(theDate));
					}
//					String sumMultSql = "select (case when sum( case when mult=0 then 1 else mult end) is not null then sum( case when mult=0 then 1 else mult end) else 0 end) from lcpol where insuredno='"+"?insuredno?"+"'";
//					SQLwithBindVariables sqlbv40=new SQLwithBindVariables();
//					sqlbv40.sql(sumMultSql);
//					sqlbv40.put("insuredno", tLCInsuredSchema.getInsuredNo());
					String tsumMult = "0";
					insured.setSumMult(tsumMult);//累计投保份数
//					String UWPolCountSql=" select count(*) from lwmission where activityid='0000001100' "
//					String UWPolCountSql=" select count(*) from lwmission where activityid in (select activityid from lwactivity  where functionid ='10010028') "
//										+ " and missionprop1 in (select prtno from lcpol where insuredno='"+"?insuredno?"+"') ";
//                    SQLwithBindVariables sqlbv41=new SQLwithBindVariables();
//                    sqlbv41.sql(UWPolCountSql);
//                    sqlbv41.put("insuredno", tLCInsuredSchema.getInsuredNo());
					String tUWPolCount = "0";
					insured.setUWPolCount(Double.valueOf(tUWPolCount));//待核保投保单数
					String tOccupationCode=tLCInsuredSchema.getOccupationCode();//被保人职业代码
					LDOccupationDB tLDOccupationDB = new LDOccupationDB();
					tLDOccupationDB.setOccupationCode(tOccupationCode);
					if(!tLDOccupationDB.getInfo()){
						CError.buildErr(this, "查询LDOccupation表失败！");
					}
					double tMEDRate = tLDOccupationDB.getMedRate();//职业代码与医疗险费率比例
					insured.setOMRate(Double.valueOf(String.valueOf(tMEDRate)));
//					String ThirdImpartSql="select * from lccustomerimpart where"
//											+" impartver='101' and impartcode "
//											+"in('020','030','040','050','060','080','090') "
//											+"and impartparammodle <>'否' and contno='"
//											+mContNo+"' and customerno='"+tLCInsuredSchema.getInsuredNo()+"'";
//					SSRS tThirdImpartSSRS = new SSRS();
//					tThirdImpartSSRS=tempExeSQL.execSQL(ThirdImpartSql);
//					if(tThirdImpartSSRS.MaxRow>0){
//						insured.setThirdImpartOption("1");
//					}else{
//						insured.setThirdImpartOption("0");
//					}
//					String OUWFlagSql="select count(*) from lccont where insuredno='"
//										+"?insuredno?"+"' and uwflag='4'";
//	                 SQLwithBindVariables sqlbv42=new SQLwithBindVariables();
//	                 sqlbv42.sql(OUWFlagSql);
//	                 sqlbv42.put("insuredno", tLCInsuredSchema.getInsuredNo());
					String tOUWSSRS = "0";
//					tOUWSSRS =tempExeSQL.getOneValue(sqlbv42);
					if(tOUWSSRS!=null&&!tOUWSSRS.equals("")&&Integer.parseInt(tOUWSSRS)>0){
						insured.setOUWFlag4("1");//既往承保记录有次标准体
					}else{
						insured.setOUWFlag4("0");//既往承保记录有次标准体
					}
//					String tEdorUWFlagSql="select count(*) from lwmission where missionprop2 in (select contno from lcinsured where insuredno='"
//											+"?insuredno?"+"') and activityid='0000000005'";
//	                 SQLwithBindVariables sqlbv43=new SQLwithBindVariables();
//	                 sqlbv43.sql(tEdorUWFlagSql);
//	                 sqlbv43.put("insuredno", tLCInsuredSchema.getInsuredNo());
					String tEdorUWSSRS = "0";
					if(tEdorUWSSRS!=null&&!tEdorUWSSRS.equals("")&&Integer.parseInt(tEdorUWSSRS)>0){
						insured.setEdorUWFlag("1");//正在申请保全核保的记录
					}else{
						insured.setEdorUWFlag("0");//正在申请保全核保的记录
					}
//					String tRNewUWFlagSql="select count(*) from lcpol where appflag='9' and uwflag='5' and insuredno='"
//											+"?insuredno?"+"' ";
//	                 SQLwithBindVariables sqlbv44=new SQLwithBindVariables();
//	                 sqlbv44.sql(tRNewUWFlagSql);
//	                 sqlbv44.put("insuredno", tLCInsuredSchema.getInsuredNo());
					String tRNewUWSSRS = "0";
					if(tRNewUWSSRS!=null&&!tRNewUWSSRS.equals("")&&Integer.parseInt(tRNewUWSSRS)>0){
						insured.setReNewUWFlag("1");//正在申请续期核保记录
					}else{
						insured.setReNewUWFlag("0");//正在申请续期核保记录
					}
//					String tRegisterSql="select count(*) from LLCase where customerno='"
//										+"?insuredno?"+"'";
//	                 SQLwithBindVariables sqlbv45=new SQLwithBindVariables();
//	                 sqlbv45.sql(tRegisterSql);
//	                 sqlbv45.put("insuredno", tLCInsuredSchema.getInsuredNo());
					String tRegisterSSRS = "0";
//					tRegisterSSRS = tempExeSQL.getOneValue(sqlbv45);
					if(tRegisterSSRS!=null&&!tRegisterSSRS.equals("")&&Integer.parseInt(tRegisterSSRS)>0){
						insured.setORegister("1");//既往立案标记
					}else{
						insured.setORegister("0");
					}
					insured.settORPTnoRGT("0");
//
//					String ttORPTnoRGTSql = "select count(*) from llsubreport where "
//											+"not exists(select 1 from llregister where rgtno=subrptno)"
//											+" and customerno='"+"?customerno?"+"'"
//											+"and llsubreport.makedate <= substr(now(),1) "
//											+"and llsubreport.makedate>=substr(add_months(now(),-24),1)";
//	                 SQLwithBindVariables sqlbv46=new SQLwithBindVariables();
//	                 sqlbv46.sql(ttORPTnoRGTSql);
//	                 sqlbv46.put("customerno", tLCInsuredSchema.getInsuredNo());
//					String tORPTnoSSRS = "";
//					tORPTnoSSRS = tempExeSQL.getOneValue(sqlbv46);
//					if(tORPTnoSSRS!=null&&!tORPTnoSSRS.equals("")&&Integer.parseInt(tORPTnoSSRS)>0){
//						insured.settORPTnoRGT("1");//既往两年内发生报案但未立案标记
//					}else{
//						insured.settORPTnoRGT("0");
//					}
					insured.setOEdorUWFlag("0");
//
//					String tOEdorUWSql="select count(*) from LPUWMasterMain where State='4' and "
//										+" insuredno='"
//										+ "?insuredno?"+"'";
//	                 SQLwithBindVariables sqlbv47=new SQLwithBindVariables();
//	                 sqlbv47.sql(tOEdorUWSql);
//	                 sqlbv47.put("insuredno", tLCInsuredSchema.getInsuredNo());
//					String tOEdorUWSSRS = "";
//					tOEdorUWSSRS = tempExeSQL.getOneValue(sqlbv47);
//					if(tOEdorUWSSRS!=null&&!tOEdorUWSSRS.equals("")&&Integer.parseInt(tOEdorUWSSRS)>0){
//						insured.setOEdorUWFlag("1");//既往保全核保结论
//					}else{
//						insured.setOEdorUWFlag("0");
//					}
//					
					String tAppHosSql = "";
					SQLwithBindVariables sqlbvs=new SQLwithBindVariables();
					if(!FreeFlag)
					{
						tAppHosSql="select blacklistflag,hospitalname from ldhospital where hospitcode in " 
							+"(select hospitcode from lcpenotice where contno='?mContNo?' and customerno='"+"?customerno?"
							+"'and customertype='I')";
						sqlbvs.sql(tAppHosSql);
						sqlbvs.put("mContNo", mContNo);
						sqlbvs.put("customerno", tLCInsuredSchema.getInsuredNo());
					}
					else
					{
					   tAppHosSql="select blacklistflag,hospitalname from ldhospital where hospitcode in " 
										+"(select hospitcode from lcpenotice where contno='?mContNo?' and customerno='"+"?customerno?"
										+"'and customertype='A')";
					   sqlbvs.sql(tAppHosSql);
						sqlbvs.put("mContNo", mContNo);
					   sqlbvs.put("customerno", tLCInsuredSchema.getInsuredNo());
					}
//					SSRS tInsHosBlack = tempExeSQL.execSQL(sqlbvs);
//					if(tInsHosBlack.getMaxRow()>0)
//					{
//						insured.setInsHosBlack(tInsHosBlack.GetText(1,1));
//						insured.setInsHopName(tInsHosBlack.GetText(1,2));
//					}
					
//					//计算饮酒量
//					String tDrinkSql="select max(A.x) from ("
//						+ " select (case when max(impartparam) is not null then max(impartparam) else '0' end) x"
//						+ " from lccustomerimpartparams" 
//						+ " where impartcode = 'A0103'" 
//						+ " and impartver = 'A01'"  
//						+ " and impartparamno='2'" 
//						+ " and customerno='"+"?customerno?"+"' "
//						+ " and contno='"+"?mContNo?"+"'" 
//						+ " union"  
//						+ " select (case when max(impartparam) is not null then max(impartparam) else '0' end) x"
//						+ " from lccustomerimpartparams"
//						+ " where impartcode ='A0503'"
//						+ " and impartver = 'A05'"
////						+ " and impartparamno in ('2','3','4')"
//						+ " and impartparamno in ('4')"  //09-09-27目前只取白酒
//						+ " and customerno='"+"?customerno?"+"'"
//						+ " and contno='"+"?mContNo?"+"' )A";
//					SQLwithBindVariables sqlbv48=new SQLwithBindVariables();
//					sqlbv48.sql(tDrinkSql);
//					sqlbv48.put("customerno", tLCInsuredSchema.getInsuredNo());
//					sqlbv48.put("mContNo", mContNo);
//		            if(mLCContSchema.getPrtNo()!=null && !mLCContSchema.getPrtNo().equals("")
//		            		&& mLCContSchema.getPrtNo().length()==14 && mLCContSchema.getPrtNo().substring(2,4).equals("51")
//				            && tLCInsuredSchema.getSequenceNo()!=null && !tLCInsuredSchema.getSequenceNo().equals(""))//家庭单
//		            {
//
//		    			tDrinkSql = "select 0 from dual where 1=1 ";
//		    			sqlbv48=new SQLwithBindVariables();
//		    			sqlbv48.sql(tDrinkSql);
//		    			if(tLCInsuredSchema.getSequenceNo().equals("1")
//		    					|| tLCInsuredSchema.getSequenceNo().equals("-1"))
//		    			{
//		    				tDrinkSql="select (case when max(impartparam) is not null then max(impartparam) else '0' end)"
//		    				 +" from lccustomerimpartparams"
//		    				 +" where impartcode = 'D0103'"
//		    				 +" and impartver = 'D01' "
//		    				 +" and impartparamno ='2'"
//		    				 //+" and customernotype='1'"//被保人
//		    				 +" and customerno='"
//		    				 +"?customerno?"
//		    				 +"' and contno='"+"?mContNo?"+"'";
//		    				sqlbv48=new SQLwithBindVariables();
//							sqlbv48.sql(tDrinkSql);
//							sqlbv48.put("customerno", tLCInsuredSchema.getInsuredNo());
//							sqlbv48.put("mContNo", mContNo);
//		    			}
//		    			else if(tLCInsuredSchema.getSequenceNo().equals("2"))
//		    			{
//		    				tDrinkSql="select (case when max(impartparam) is not null then max(impartparam) else '0' end)"
//		    				 +" from lccustomerimpartparams"
//		    				 +" where impartcode = 'D0103'"
//		    				 +" and impartver = 'D01' "
//		    				 +" and impartparamno ='5'"
//		    				 //+" and customernotype='1'"//被保人
//		    				 +" and customerno='"
//		    				 +"?customerno?"
//		    				 +"' and contno='"+"?mContNo?"+"'";
//		    				sqlbv48=new SQLwithBindVariables();
//							sqlbv48.sql(tDrinkSql);
//							sqlbv48.put("customerno", tLCInsuredSchema.getInsuredNo());
//							sqlbv48.put("mContNo", mContNo);
//		    			}		    		
//		            }
//		            try {
//						String tDrink ="";
////						String tempDrink 
//						tDrink= tempExeSQL.getOneValue(sqlbv48);//饮酒量
//						logger.debug("tDrink = "+tDrink);
////						if(tempDrink!=null&&!tempDrink.equals("")){
////							tDrink = tempDrink.replaceAll("[^0-9]", "");
////						}else{
////							insured.setDrink(new Double("0"));//饮酒量
////						}
//						if(tDrink==null||tDrink.equals(""))
//						{
//							 insured.setDrink(new Double("0"));//饮酒量
//						}
//						else
//						{
//							insured.setDrink(new Double(tDrink));//饮酒量
//						}
//					} catch (Exception e) {
//						CError.buildErr(this, mContNo+"饮酒量数据有误！");
//						e.printStackTrace();
//					}
//		            
//					insured.setAddRiskPrem(Double.valueOf(String.valueOf(AllSumAmnt)));//累计风险保额
//					String tOMedRateSql="select (case when max(medrate) is not null then max(medrate) else 0 end) from ldoccupation where occupationcode"
//										+" in(select occupationcode from lcinsured where insuredno='"+
//										"?insuredno?"+"' and contno<>'"+"?contno?"+"') ";
//					SQLwithBindVariables sqlbv49=new SQLwithBindVariables();
//					sqlbv49.sql(tOMedRateSql);
//					sqlbv49.put("insuredno", tLCInsuredSchema.getInsuredNo());
//					sqlbv49.put("contno", tLCInsuredSchema.getContNo());
//					String tOMedRate = tempExeSQL.getOneValue(sqlbv49);
//					if(tOMedRate==null||tOMedRate.equals(""))
//					{
//						insured.setOMedRate(new Double("0"));
//					}
//					else
//					{
//						insured.setOMedRate(new Double(tOMedRate));//既往投保的职业医疗险费率
//					}
					insured.setComrAppFlag("1");
//
//					String tBussFlagSql="select count(*) from lccont where bussflag='Y' and insuredno='"
//										+"?insuredno?"+"'";
//					SQLwithBindVariables sqlbv50=new SQLwithBindVariables();
//					sqlbv50.sql(tBussFlagSql);
//					sqlbv50.put("insuredno", tLCInsuredSchema.getInsuredNo());
//					String tBussFlag=tempExeSQL.getOneValue(sqlbv50);
//					if("0".equals(tBussFlag)){
//						insured.setComrAppFlag("0");//是否有商业因素标准体承保记录
//					}else{
//						insured.setComrAppFlag("1");
//					}
					
					//2009-2-11 ln add  既往有逾期未回复调取医疗资料撤单记录
					insured.setWithDHispital("0");
//					String tSqlNew=" select 1 from lccont l where  contno<>'"+"?mContNo?"+"' "
//						+ " and exists (select 1 from lccuwmaster where contno=l.contno and commonreasoncode='12' and passflag='a')"
//						+" and exists (select 1 from lcinsured where contno=l.contno and insuredno='" +"?insuredno?" +"' )";
//					SQLwithBindVariables sqlbv51=new SQLwithBindVariables();
//					sqlbv51.sql(tSqlNew);
//					sqlbv51.put("mContNo", mContNo);
//					sqlbv51.put("insuredno", tLCInsuredSchema.getInsuredNo());
//					String tSSRSNew = tempExeSQL.getOneValue(sqlbv51);
//					if(tSSRSNew!=null&&tSSRSNew.equals("1")){
//						insured.setWithDHispital("1");
//					}else{
//						insured.setWithDHispital("0");
//					}
					
					//2009-2-11 ln add  有愈期未交费撤单记录，且该记录中有次标准体承保的轨迹
					insured.setHaveNoFeeWithD("0");
//					tSqlNew=" select 1 from lccont l where  1=1 "
//						+ " and exists (select 1 from lccuwmaster where contno=l.contno and commonreasoncode='99' and passflag='a')"
//						+ " and exists (select 1 from lccuwsub where contno=l.contno and passflag='4')"
//						+" and exists (select 1 from lcinsured where contno=l.contno and insuredno='" +"?insuredno?" +"' )";
//					sqlbv51=new SQLwithBindVariables();
//					sqlbv51.sql(tSqlNew);
//					sqlbv51.put("insuredno", tLCInsuredSchema.getInsuredNo());
//					tSSRSNew = tempExeSQL.getOneValue(sqlbv51);
//					if(tSSRSNew!=null&&tSSRSNew.equals("1")){
//						insured.setHaveNoFeeWithD("1");
//					}else{
//						insured.setHaveNoFeeWithD("0");
//					}
					
					//2009-2-11 ln add  本次或既往体检录入的体检结果不为正常
					//tongmeng 2009-08-05 modify
					//兼容老系统体检数据
					//tongmeng 2009-08-10 modify
					//修改老系统校验逻辑.
					insured.setHealthPro("0");
					insured.setPENoticeError("0");
//					tSqlNew=" select distinct 1 from lccont l where 1=1 "
//						+ " and exists (select 1 from lcpenotice where contno=l.contno " 
//						+ " and (peresult is null or peresult='' or peresult like '异常%') "
//						+ " and customerno='" +"?insuredno?" +"' and makedate>'2009-06-13' "
//						+ " union "
//						+ " select 1 from lcpenotice where contno=l.contno "
//						//+ " and peresult is not null "
//						+ " and customerno='" +"?insuredno?" +"' "
//						+ " and makedate<='2009-06-13' "
//						+ ") and insuredno='"+"?insuredno?"+"' ";
//						;
//					sqlbv51=new SQLwithBindVariables();
//					sqlbv51.sql(tSqlNew);
//					sqlbv51.put("insuredno", tLCInsuredSchema.getInsuredNo());	
//					tSSRSNew = tempExeSQL.getOneValue(sqlbv51);
//					if(tSSRSNew!=null&&tSSRSNew.equals("1")){
//						insured.setHealthPro("1");
//						insured.setPENoticeError("1");//本次或既往体检结果异常记录(本次或既往体检录入的体检结果不为正常	没回复算空白(也是不正常))
//					}else{
//						insured.setHealthPro("0");
//						insured.setPENoticeError("0");
//					}	
					
					//2009-2-11 ln add  额外死亡率--加费评点数
//					tSqlNew = "select (case when max(suppriskscore) is not null then max(suppriskscore) else 0 end) from lcprem "
//						 +" where contno in ( select contno from lcinsured where 1=1 and insuredno='"
//						 +"?insuredno?"
//						 +"' and contno='"+"?mContNo?"+"') ";
//					sqlbv51=new SQLwithBindVariables();
//					sqlbv51.sql(tSqlNew);
//					sqlbv51.put("insuredno", tLCInsuredSchema.getInsuredNo());	
//					sqlbv51.put("mContNo", mContNo);
//					String tResultNew = tempExeSQL.getOneValue(sqlbv51);
					insured.setEM(Double.valueOf("0"));
					
					String tZBFlag = "";
					
					tZBFlag = this.getZBFlag(this.LSumDangerAmnt, DSumDangerAmnt, MSumDangerAmnt,"0");
					insured.setZBFlag(tZBFlag);
					insured.setHealthTellConTent("0");
					//2009-3-16 add by duanyh
					//健康告知不全为否 HealthTellConTent
//					String tHealthTellConTent_sql = "select count(*) from lccustomerimpart where impartcode in('A0105','A0110','A0111e','A0111j',"
//						+"'A0106','A0111a','A0111f','A0112','A0107','A0111b','A0111g','A0108','A0111c','A0111h','A0109','A0111d',"
//						+"'A0111i','D0105','D0110a','D0110e','D0110i','D0106','D0110b','D0110f','D0110j','D0107','D0110c','D0110g',"
//						+"'D0111','D0108','D0110d','D0110h','D0122','D0109','C0101','C0102','C0103','C0104','C0105','C0106','B0101',"
//						+"'B0102','B0103','B0104','A0504','A0505','A0506','A0507','A0509','A0510','A0511','A0512',"
//						+"'A0513','A0514','A0515','A0516','A0517','A0518','A0519','A0520')"
//						+" and contno='"+"?mContNo?"+"' and customerno='"+"?customerno?"+"' ";//and CustomerNoType='1'";
//					String tHealthTellConTent = "";
//					SQLwithBindVariables sqlbv52=new SQLwithBindVariables();
//					sqlbv52.sql(tHealthTellConTent_sql);
//					sqlbv52.put("mContNo", mContNo);
//					sqlbv52.put("customerno", tLCInsuredSchema.getInsuredNo());
//					tHealthTellConTent = tempExeSQL.getOneValue(sqlbv52);
//					if(tHealthTellConTent!=null&&!tHealthTellConTent.equals("")&&Integer.parseInt(tHealthTellConTent)>0){
//						insured.setHealthTellConTent("1");
//					}else{
//						insured.setHealthTellConTent("0");
//					}
					insured.setFemaleConTent("0");
					//妇科告知不全为否 FemaleConTent
//					String tFemaleConTent_sql = "select count(*) from lccustomerimpart where impartcode in('A0113a','A0113b','D0112a','D0112b','A0522')"
//						+" and contno='"+"?mContNo?"+"' and customerno='"+"?customerno?"+"' ";//and CustomerNoType='1'";
//					String tFemaleConTent = "";
//					SQLwithBindVariables sqlbv53=new SQLwithBindVariables();
//					sqlbv53.sql(tFemaleConTent_sql);
//					sqlbv53.put("mContNo", mContNo);
//					sqlbv53.put("customerno", tLCInsuredSchema.getInsuredNo());
//					tFemaleConTent = tempExeSQL.getOneValue(sqlbv53);
//					if(tFemaleConTent!=null&&!tFemaleConTent.equals("")&&Integer.parseInt(tFemaleConTent)>0){
//						insured.setFemaleConTent("1");
//					}else{
//						insured.setFemaleConTent("0");
//					}
					//婴儿告知不为否 BabyConTent
					insured.setBabyConTent("0");
//					String tBabyConTent_sql = "select count(*) from lccustomerimpart where impartcode in('A0114b','D0121','A0524')"
//						+" and contno='"+"?mContNo?"+"' and customerno='"+"?customerno?"+"' ";//and CustomerNoType='1'";
//					String tBabyConTent = "";
//					SQLwithBindVariables sqlbv54=new SQLwithBindVariables();
//					sqlbv54.sql(tBabyConTent_sql);
//					sqlbv54.put("mContNo", mContNo);
//					sqlbv54.put("customerno", tLCInsuredSchema.getInsuredNo());
//					tBabyConTent = tempExeSQL.getOneValue(sqlbv54);
//					if(tBabyConTent!=null&&!tBabyConTent.equals("")&&Integer.parseInt(tBabyConTent)>0){
//						insured.setBabyConTent("1");
//					}else{
//						insured.setBabyConTent("0");
//					}
					//家族史 FamilyConTent
					insured.setFamilyConTent("0");
//
//					String tFamilyConTent_sql = "select count(*) from lccustomerimpart where impartcode in('A0115a','A0115b','D0113a','D0113b','A0525','A0526')"
//						+" and contno='"+"?mContNo?"+"' and customerno='"+"?customerno?"+"' ";//and CustomerNoType='1'";
//					String tFamilyConTent = "";
//					SQLwithBindVariables sqlbv55=new SQLwithBindVariables();
//					sqlbv55.sql(tFamilyConTent_sql);
//					sqlbv55.put("mContNo", mContNo);
//					sqlbv55.put("customerno", tLCInsuredSchema.getInsuredNo());
//					tFamilyConTent = tempExeSQL.getOneValue(sqlbv55);
//					if(tFamilyConTent!=null&&!tFamilyConTent.equals("")&&Integer.parseInt(tFamilyConTent)>0){
//						insured.setFamilyConTent("1");
//					}else{
//						insured.setFamilyConTent("0");
//					}
					//既往异常投保/理赔史 OuncommonConTent
					insured.setOuncommonConTent("0");
//
//					String tOuncommonConTent_sql = "select count(*) from lccustomerimpart where impartcode in('A0117','A0118','D0115','D0116','D0117','C0108','A0528','A0529')"
//						+" and contno='"+"?mContNo?"+"' and customerno='"+"?customerno?"+"' ";//and CustomerNoType='1'";
//					String tOuncommonConTent = "";
//					SQLwithBindVariables sqlbv56=new SQLwithBindVariables();
//					sqlbv56.sql(tOuncommonConTent_sql);
//					sqlbv56.put("mContNo", mContNo);
//					sqlbv56.put("customerno", tLCInsuredSchema.getInsuredNo());
//					tOuncommonConTent = tempExeSQL.getOneValue(sqlbv56);
//					if(tOuncommonConTent!=null&&!tOuncommonConTent.equals("")&&Integer.parseInt(tOuncommonConTent)>0){
//						insured.setOuncommonConTent("1");
//					}else{
//						insured.setOuncommonConTent("0");
//					}
					insured.setClaimUWFlag("0");

					//add by duanyh 2009-3-18					
//                    String tClaimUWFlag_sql = "select count(*) from lwmission where activityid='0000005505' and missionprop3='"+"?missionprop3?"+"'";
//                    SQLwithBindVariables sqlbv57=new SQLwithBindVariables();
//                    sqlbv57.sql(tClaimUWFlag_sql);
//                    sqlbv57.put("missionprop3", tLCInsuredSchema.getInsuredNo());
//                    String tClaimUWFlag = "";
//                    tClaimUWFlag = tempExeSQL.getOneValue(sqlbv57);
//					if(tClaimUWFlag!=null&&!tClaimUWFlag.equals("")&&Integer.parseInt(tClaimUWFlag)>0){
//						insured.setClaimUWFlag("1");//是否有正在申请的理赔核保的保单
//					}else{
//						insured.setClaimUWFlag("0");
//					}
					insured.setORNewUWFlag("0");
//
//					String tORNewUWFlag_sql = "select count(*) from lcuwmaster where passflag in ('4','1') and insuredno='"+"?insuredno?"+"' and uwtype='1'";
//                    SQLwithBindVariables sqlbv58=new SQLwithBindVariables();
//                    sqlbv58.sql(tORNewUWFlag_sql);
//                    sqlbv58.put("insuredno", tLCInsuredSchema.getInsuredNo());
//					String tORNewUWFlag = "";
//                    tORNewUWFlag = tempExeSQL.getOneValue(sqlbv58);
//					if(tORNewUWFlag!=null&&!tORNewUWFlag.equals("")&&Integer.parseInt(tORNewUWFlag)>0){
//						insured.setORNewUWFlag("1");//既往续期核保结论为“次标准体"或"拒保" ？？待确认
//					}else{
//						insured.setORNewUWFlag("0");
//					}
					insured.setOClaimUWFlag("0");
//
//					String tOClaimUWFlag_sql = "select count(*) from LLCUWMaster where passflag in ('1','4') and insuredno='"+"?insuredno?"+"'";
//                    SQLwithBindVariables sqlbv59=new SQLwithBindVariables();
//                    sqlbv59.sql(tOClaimUWFlag_sql);
//                    sqlbv59.put("insuredno", tLCInsuredSchema.getInsuredNo());
//					String tOClaimUWFlag = "";
//					tOClaimUWFlag = tempExeSQL.getOneValue(sqlbv59);
//					if(tOClaimUWFlag!=null&&!tOClaimUWFlag.equals("")&&Integer.parseInt(tOClaimUWFlag)>0){
//						insured.setOClaimUWFlag("1");//既往理赔核保结论 为“次标准体"或"拒保"
//					}else{
//						insured.setOClaimUWFlag("0");
//					}
					insured.setOReportFlag("0");
//
//					String tOReportFlag_sql =" select count(*) from llreport ,llsubreport where rptno=subrptno and not exists(select 1 from llregister where rgtno=rptno)"
//                                            +" and RptDate>=add_months(now(),-24) and customerno='"+"?customerno?"+"'";
//                    SQLwithBindVariables sqlbv60=new SQLwithBindVariables();
//                    sqlbv60.sql(tOReportFlag_sql);
//                    sqlbv60.put("customerno", tLCInsuredSchema.getInsuredNo());
//					String tOReportFlag = "";
//					tOReportFlag = tempExeSQL.getOneValue(sqlbv60);
//					if(tOReportFlag!=null&&!tOReportFlag.equals("")&&Integer.parseInt(tOReportFlag)>0){
//						insured.setOReportFlag("1");//二年内既往报案记录(被保人险既往2年内曾经在我公司发生过报案记录,但是未立案；)
//					}else{
//						insured.setOReportFlag("0");
//					}
//			
					insured.setOAddFeeYN("0");
//
//					tSqlNew=" select 1 from lccont l where  contno<>'"+"?mContNo?"+"' "
//						+ " and exists (select 1 from lccuwmaster where contno=l.contno and commonreasoncode='03' and passflag='a')"
//						+" and exists (select 1 from lcinsured where contno=l.contno and insuredno='" +"?insuredno?" +"' )";
//					sqlbv51=new SQLwithBindVariables();
//					sqlbv51.sql(tSqlNew);
//					sqlbv51.put("insuredno", tLCInsuredSchema.getInsuredNo());	
//					sqlbv51.put("mContNo", mContNo);
//					tSSRSNew = tempExeSQL.getOneValue(sqlbv51);
//					if(tSSRSNew!=null&&tSSRSNew.equals("1")){
//						insured.setOAddFeeYN("1");//既往未同意加费记录
//					}else{
//						insured.setOAddFeeYN("0");
//					}
//					
					insured.setOChangePlanYN("0");
//
//					tSqlNew=" select 1 from lccont l where  contno<>'"+"?mContNo?"+"' "
//					+ " and exists (select 1 from lccuwmaster where contno=l.contno and commonreasoncode='05' and passflag='a')"
//					+" and exists (select 1 from lcinsured where contno=l.contno and insuredno='" +"?insuredno?" +"' )";
//					sqlbv51=new SQLwithBindVariables();
//					sqlbv51.sql(tSqlNew);
//					sqlbv51.put("insuredno", tLCInsuredSchema.getInsuredNo());	
//					sqlbv51.put("mContNo", mContNo);
//					tSSRSNew = tempExeSQL.getOneValue(sqlbv51);
//					if(tSSRSNew!=null&&tSSRSNew.equals("1")){
//						insured.setOChangePlanYN("1");//既往未同意保险计划变更记录(被保险人既往有未同意保险计划变更客户申请撤单撤单记录)
//					}else{
//						insured.setOChangePlanYN("0");
//					}
					insured.setOLateAddFeeYN("0");
//
//					tSqlNew=" select 1 from lccont l where  contno<>'"+"?mContNo?"+"' "
//					+ " and exists (select 1 from lccuwmaster where contno=l.contno and commonreasoncode='09' and passflag='a')"
//					+" and exists (select 1 from lcinsured where contno=l.contno and insuredno='" +"?insuredno?" +"' )";
//					sqlbv51=new SQLwithBindVariables();
//					sqlbv51.sql(tSqlNew);
//					sqlbv51.put("insuredno", tLCInsuredSchema.getInsuredNo());	
//					sqlbv51.put("mContNo", mContNo);
//					tSSRSNew = tempExeSQL.getOneValue(sqlbv51);
//					if(tSSRSNew!=null&&tSSRSNew.equals("1")){
//						insured.setOLateAddFeeYN("1");//既往逾期未加费记录
//					}else{
//						insured.setOLateAddFeeYN("0");
//					}
					insured.setOLateChangePlanYN("0");
//
//					tSqlNew=" select 1 from lccont l where  contno<>'"+"?mContNo?"+"' "
//					+ " and exists (select 1 from lccuwmaster where contno=l.contno and commonreasoncode='11' and passflag='a')"
//					+" and exists (select 1 from lcinsured where contno=l.contno and insuredno='" +"?insuredno?" +"' )";
//					sqlbv51=new SQLwithBindVariables();
//					sqlbv51.sql(tSqlNew);
//					sqlbv51.put("insuredno", tLCInsuredSchema.getInsuredNo());	
//					sqlbv51.put("mContNo", mContNo);
//					tSSRSNew = tempExeSQL.getOneValue(sqlbv51);
//					if(tSSRSNew!=null&&tSSRSNew.equals("1")){
//						insured.setOLateChangePlanYN("1");//逾期未回复保险计划变更记录
//					}else{
//						insured.setOLateChangePlanYN("0");
//					}
					insured.setOLateMeetYN("0");
//
//					tSqlNew=" select 1 from lccont l where  contno<>'"+"?mContNo?"+"' "
//					+ " and exists (select 1 from lccuwmaster where contno=l.contno and commonreasoncode='08' and passflag='a')"
//					+" and exists (select 1 from lcinsured where contno=l.contno and insuredno='" +"?insuredno?" +"' )";
//					sqlbv51=new SQLwithBindVariables();
//					sqlbv51.sql(tSqlNew);
//					sqlbv51.put("insuredno", tLCInsuredSchema.getInsuredNo());	
//					sqlbv51.put("mContNo", mContNo);
//					tSSRSNew = tempExeSQL.getOneValue(sqlbv51);
//					if(tSSRSNew!=null&&tSSRSNew.equals("1")){
//						insured.setOLateMeetYN("1");//既往逾期未生调
//					}else{
//						insured.setOLateMeetYN("0");
//					}
					insured.setOLatePENoticeYN("0");
//
//					tSqlNew=" select 1 from lccont l where  contno<>'"+"?mContNo?"+"' "
//					+ " and exists (select 1 from lccuwmaster where contno=l.contno and commonreasoncode='07' and passflag='a')"
//					+" and exists (select 1 from lcinsured where contno=l.contno and insuredno='" +"?insuredno?" +"' )";
//					sqlbv51=new SQLwithBindVariables();
//					sqlbv51.sql(tSqlNew);
//					sqlbv51.put("insuredno", tLCInsuredSchema.getInsuredNo());	
//					sqlbv51.put("mContNo", mContNo);
//					tSSRSNew = tempExeSQL.getOneValue(sqlbv51);
//					if(tSSRSNew!=null&&tSSRSNew.equals("1")){
//						insured.setOLatePENoticeYN("1");//既往逾期未体检记录
//					}else{
//						insured.setOLatePENoticeYN("0");
//					}
//					
					insured.setOLateSpecYN("0");
//
//					tSqlNew=" select 1 from lccont l where  contno<>'"+"?mContNo?"+"' "
//					+ " and exists (select 1 from lccuwmaster where contno=l.contno and commonreasoncode='10' and passflag='a')"
//					+" and exists (select 1 from lcinsured where contno=l.contno and insuredno='" +"?insuredno?" +"' )";
//					sqlbv51=new SQLwithBindVariables();
//					sqlbv51.sql(tSqlNew);
//					sqlbv51.put("insuredno", tLCInsuredSchema.getInsuredNo());	
//					sqlbv51.put("mContNo", mContNo);
//					tSSRSNew = tempExeSQL.getOneValue(sqlbv51);
//					if(tSSRSNew!=null&&tSSRSNew.equals("1")){
//						insured.setOLateSpecYN("1");//既往逾期未回复特约记录
//					}else{
//						insured.setOLateSpecYN("0");
//					}
//					
					insured.setOMeetYN("0");
//
//					tSqlNew=" select 1 from lccont l where  contno<>'"+"?mContNo?"+"' "
//					+ " and exists (select 1 from lccuwmaster where contno=l.contno and commonreasoncode='02' and passflag='a')"
//					+" and exists (select 1 from lcinsured where contno=l.contno and insuredno='" +"?insuredno?" +"' )";
//					sqlbv51=new SQLwithBindVariables();
//					sqlbv51.sql(tSqlNew);
//					sqlbv51.put("insuredno", tLCInsuredSchema.getInsuredNo());	
//					sqlbv51.put("mContNo", mContNo);
//					tSSRSNew = tempExeSQL.getOneValue(sqlbv51);
//					if(tSSRSNew!=null&&tSSRSNew.equals("1")){
//						insured.setOMeetYN("1");//既往未同意生调记录(被保险人既往有未同意生调客户申请撤单撤单记录)
//					}else{
//						insured.setOMeetYN("0");
//					}
					insured.setOOtherOver("0");
//
//					tSqlNew=" select 1 from lccont l where  contno<>'"+"?mContNo?"+"' "
//					+ " and exists (select 1 from lccuwmaster where contno=l.contno and commonreasoncode='14' and passflag='a')"
//					+" and exists (select 1 from lcinsured where contno=l.contno and insuredno='" +"?insuredno?" +"' )";
//					sqlbv51=new SQLwithBindVariables();
//					sqlbv51.sql(tSqlNew);
//					sqlbv51.put("insuredno", tLCInsuredSchema.getInsuredNo());	
//					sqlbv51.put("mContNo", mContNo);
//					tSSRSNew = tempExeSQL.getOneValue(sqlbv51);
//					if(tSSRSNew!=null&&tSSRSNew.equals("1")){
//						insured.setOOtherOver("1");//既往有其他撤单记录
//					}else{
//						insured.setOOtherOver("0");
//					}
					insured.setOPENotice("0");
//
//					tSqlNew=" select 1 from lccont l where  contno<>'"+"?mContNo?"+"' "
//					+ " and exists (select 1 from lccuwmaster where contno=l.contno and commonreasoncode='01' and passflag='a')"
//					+" and exists (select 1 from lcinsured where contno=l.contno and insuredno='" +"?insuredno?" +"' )";
//					sqlbv51=new SQLwithBindVariables();
//					sqlbv51.sql(tSqlNew);
//					sqlbv51.put("insuredno", tLCInsuredSchema.getInsuredNo());	
//					sqlbv51.put("mContNo", mContNo);
//					tSSRSNew = tempExeSQL.getOneValue(sqlbv51);
//					if(tSSRSNew!=null&&tSSRSNew.equals("1")){
//						insured.setOPENotice("1");//既往未同意体检记录(被保险人既往有未同意体检客户申请撤单撤单记录)
//					}else{
//						insured.setOPENotice("0");
//					}
					
					
					insured.setOSpecYN("0");
//
//					tSqlNew=" select 1 from lccont l where  contno<>'"+"?mContNo?"+"' "
//					+ " and exists (select 1 from lccuwmaster where contno=l.contno and commonreasoncode='04' and passflag='a')"
//					+" and exists (select 1 from lcinsured where contno=l.contno and insuredno='" +"?insuredno?" +"' )";
//					sqlbv51=new SQLwithBindVariables();
//					sqlbv51.sql(tSqlNew);
//					sqlbv51.put("insuredno", tLCInsuredSchema.getInsuredNo());	
//					sqlbv51.put("mContNo", mContNo);
//					tSSRSNew = tempExeSQL.getOneValue(sqlbv51);
//					if(tSSRSNew!=null&&tSSRSNew.equals("1")){
//						insured.setOSpecYN("1");//既往未同意特约记录(被保险人既往有未同意特约客户申请撤单撤单记录)
//					}else{
//						insured.setOSpecYN("0");
//					}
					
//					insured.setOMainPol();//既往主险退保次数
//					insured.setOOccupationType();//既往职业类别
//					insured.setInsuredISOperator(); //被保险人职业为MS公司业务员（待确认）

					//tongmeng 2009-05-04 add
					//增加住院费用补偿险保额,康顺意外保额,康顺老年份数
					ExeSQL tExeSQL_SumAmnt = new ExeSQL();
//					String tSQL_SumZYAmnt = "select nvl(sum(amnt),0) from lcpol where appflag not in ('4','9') "
//						                  + " and uwflag not in ('1','2','a') "
//						                  + " and insuredno='"+tLCInsuredSchema.getInsuredNo()+"' "
//						                  + " and riskcode in ('111801','111802') ";
//					String tSumZYAmnt = tExeSQL_SumAmnt.getOneValue(tSQL_SumZYAmnt);
//					if(tSumZYAmnt==null||tSumZYAmnt.equals(""))
//					{
//						tSumZYAmnt = "0";
//					}
//					insured.setSumZYAmnt(new Double(tSumZYAmnt));
					//被保人名下有多币种险种的情况
		            String currString = "select * from lcpol where appflag not in ('4','9') "
						                  + " and uwflag not in ('1','2','a') "
						                  + " and insuredno='"+"?insuredno?"+"' "
						                  + " and riskcode in ('111801','111802')";
		            SQLwithBindVariables sqlbv61=new SQLwithBindVariables();
		            sqlbv61.sql(currString);
		            sqlbv61.put("insuredno", tLCInsuredSchema.getInsuredNo());
		            LDExch tLDExch = new LDExch();
		            LCPolDB currLCPolDB = new LCPolDB();
		            LCPolSet currLCPolSet = new LCPolSet();
		            currLCPolSet = currLCPolDB.executeQuery(sqlbv61);
		            double totalCurr = 0.00;
		            for(int j=1;j<=currLCPolSet.size();j++){
		            	totalCurr += tLDExch.toBaseCur(currLCPolSet.get(j).getCurrency(), locCurrency, theCurrentDate, currLCPolSet.get(j).getAmnt());
		            }
					insured.setSumZYAmnt(totalCurr);
					
//					String tSQL_SumKSAmnt = "select nvl(sum(amnt),0) from lcpol where appflag not in ('4','9') "
//		                  				  + " and uwflag not in ('1','2','a') "
//		                  				  + " and insuredno='"+tLCInsuredSchema.getInsuredNo()+"' "
//		                  				  + " and riskcode in ('111601','121601') ";
//					String tSumKSAmnt = tExeSQL_SumAmnt.getOneValue(tSQL_SumKSAmnt);
//					
//					if(tSumKSAmnt==null||tSumKSAmnt.equals(""))
//					{
//						tSumKSAmnt = "0";
//					}
//					insured.setSumKSAmnt(new Double(tSumKSAmnt));
					//有多币种险种的情况
		            String currString2 = "select * from lcpol where appflag not in ('4','9') "
						                  + " and uwflag not in ('1','2','a') "
						                  + " and insuredno='"+"?insuredno?"+"' "
						                  + " and riskcode in ('111601','121601')";
		            SQLwithBindVariables sqlbv62=new SQLwithBindVariables();
		            sqlbv62.sql(currString2);
		            sqlbv62.put("insuredno", tLCInsuredSchema.getInsuredNo());
		            LDExch tLDExch2 = new LDExch();
		            LCPolDB currLCPolDB2 = new LCPolDB();
		            LCPolSet currLCPolSet2 = new LCPolSet();
		            currLCPolSet2 = currLCPolDB2.executeQuery(sqlbv62);
		            double totalCurr2 = 0.00;
		            for(int j=1;j<=currLCPolSet2.size();j++){
		            	totalCurr2 += tLDExch2.toBaseCur(currLCPolSet2.get(j).getCurrency(), locCurrency, theCurrentDate, currLCPolSet2.get(j).getAmnt());
		            }
					insured.setSumKSAmnt(totalCurr2);
//					insured.setSumKSMult(new Double("0"));
//					String tSQL_SumKSMult = "select (case when sum( case when mult=0 then 1 else mult end) is not null then sum( case when mult=0 then 1 else mult end) else 0 end) from lcpol where appflag not in ('4','9') "
//							+ " and uwflag not in ('1','2','a') "
//							+ " and insuredno='"
//							+ "?insuredno?"
//							+ "' "
//							+ " and riskcode in ('141803','111602') ";
//		            SQLwithBindVariables sqlbv63=new SQLwithBindVariables();
//		            sqlbv63.sql(tSQL_SumKSMult);
//		            sqlbv63.put("insuredno", tLCInsuredSchema.getInsuredNo());
//					String tSumKSMult = tExeSQL_SumAmnt
//							.getOneValue(sqlbv63);
//					if (tSumKSMult == null || tSumKSMult.equals("")) {
//						tSumKSMult = "0";
//					}
//					insured.setSumKSMult(new Double(tSumKSMult));
//					
//					
					//tongmeng 2009-04-28 add
					//累计年金险保额和累计医疗险保额
//					String tSQL_Ann = "select nvl(sum(amnt),0) from lcpol where appflag not in ('4','9') "
//						            + " and uwflag not in ('1','2','a') "
//						            + " and insuredno='"+tLCInsuredSchema.getInsuredNo()+"' "
//						            + " and riskcode in (select riskcode from lmriskapp where risktype8='05' ) ";
//					
//					String tSumAnnAmnt = tExeSQL_SumAmnt.getOneValue(tSQL_Ann);
//					if(tSumAnnAmnt==null||tSumAnnAmnt.equals(""))
//					{
//						insured.setSumAnnAmnt(new Double("0"));
//					}
//					else
//					{
//						insured.setSumAnnAmnt(new Double(tSumAnnAmnt));
//					}
					//有多币种险种的情况
		            String currString3 = "select * from lcpol where appflag not in ('4','9') "
						                  + " and uwflag not in ('1','2','a') "
						                  + " and insuredno='"+"?insuredno?"+"' "
						                  + " and riskcode in (select riskcode from lmriskapp where risktype8='05' )";
		            SQLwithBindVariables sqlbv64=new SQLwithBindVariables();
		            sqlbv64.sql(currString3);
		            sqlbv64.put("insuredno", tLCInsuredSchema.getInsuredNo());
		            LDExch tLDExch3 = new LDExch();
		            LCPolDB currLCPolDB3 = new LCPolDB();
		            LCPolSet currLCPolSet3 = new LCPolSet();
		            currLCPolSet3 = currLCPolDB3.executeQuery(sqlbv64);
		            double totalCurr3 = 0.00;
		            for(int j=1;j<=currLCPolSet3.size();j++){
		            	totalCurr3 += tLDExch3.toBaseCur(currLCPolSet3.get(j).getCurrency(), locCurrency, theCurrentDate, currLCPolSet3.get(j).getAmnt());
		            }
					insured.setSumAnnAmnt(totalCurr3);
					
					//累计费用型医疗险保额
//					String tSQL_Med = "select nvl(sum(amnt),0) from lcpol where appflag not in ('4','9') "
//						            + " and uwflag not in ('1','2','a') "
//			            + " and insuredno='"+tLCInsuredSchema.getInsuredNo()+"' "
//			            + " and riskcode in (select riskcode from lmriskapp where risktype8='07' ) ";
//					String tSumMedAmnt = tExeSQL_SumAmnt.getOneValue(tSQL_Med);
//						
//					if(tSumMedAmnt==null||tSumMedAmnt.equals(""))
//					{
//						tSumMedAmnt = "0";
//					}
					//有多币种险种的情况
					String currString4 = "select * from lcpol where appflag not in ('4','9') "
							+ " and uwflag not in ('1','2','a') "
							+ " and insuredno='"+ "?insuredno?"+ "' "
							+ " and riskcode in (select riskcode from lmriskapp where risktype8='07' )";
		            SQLwithBindVariables sqlbv65=new SQLwithBindVariables();
		            sqlbv65.sql(currString4);
		            sqlbv65.put("insuredno", tLCInsuredSchema.getInsuredNo());
					LDExch tLDExch4 = new LDExch();
					LCPolDB currLCPolDB4 = new LCPolDB();
					LCPolSet currLCPolSet4 = new LCPolSet();
					currLCPolSet4 = currLCPolDB4.executeQuery(sqlbv65);
					double totalCurr4 = 0.00;
					for (int j = 1; j <= currLCPolSet4.size(); j++) {
						totalCurr4 += tLDExch4.toBaseCur(currLCPolSet4.get(j).getCurrency(), locCurrency, theCurrentDate,currLCPolSet4.get(j).getAmnt());
					}
					insured.setSumMedAmnt(totalCurr4);
	
					String tInsuredNo = tLCInsuredSchema.getInsuredNo();
					insured.setSumJYMTCount(getSumJYMTCount(tInsuredNo));//被保险人的金玉满堂系列产品份数
					insured.setSumFGYMPrem(getSumFGYMPrem(tInsuredNo));//被保险人的富贵盈门系列产品保费
//					insured.setSumMedAmnt(new Double(tSumMedAmnt));
					insured.setSumKSCont(getSumCont(tInsuredNo,"1"));//康顺合同份数
					insured.setSumMRZYCont(getSumCont(tInsuredNo,"2"));//每日住院合同份数
					insured.setOImpart(getOImpart(tInsuredNo));//既往告知事项不全为否
					insured.setYearIncome(getYearIncome(tInsuredNo));//年收入
//					insured.setFinSumAmnt(FinSumAmnt);//本单财务风险保额
					insured.setPregnancyWeeks(getPregnancyWeeks(tInsuredNo));//怀孕周数
					insured.setSmokeYears(getSmokeYears(tInsuredNo,"I",FreeFlag));//吸烟年数
					insured.setDrinkYears(getDrinkYears(tInsuredNo));//饮酒年数
					insured.setDrinkType(getDrinkType(tInsuredNo));//饮酒类型
					insured.setISPregnancy(getISImpart(tInsuredNo,"01"));//孕妇告知
					insured.setDanSportInter(getISImpart(tInsuredNo,"02"));//危险运动爱好
					insured.setTrafAccImpart(getISImpart(tInsuredNo,"03"));//交通事故告知
					insured.setAbroadImpart(getISImpart(tInsuredNo,"04"));//出国意向告知
					insured.setTrafficAccident(getTrafficAccident(tInsuredNo));//交通事故记录
					insured.setDisabilityImpart(getDisabilityImpart(tInsuredNo));//残疾事项告知
					//联系电话与业务员的联系电话是否一致
//					insured.setSamePhone(getSamePhone(tInsuredNo,tLCInsuredSchema.getAddressNo()));
					insured.setSameOccuCode(getSameOccuCode(tInsuredNo));//一年内职业代码是否一致 
		}catch(Exception e){
			CError.buildErr(this, "准备BOMInsured出错！");
			e.printStackTrace();
		}
		return insured;
	}
	
	private BOMPol DealBOMPol(LCPolSchema tLCPolSchema,BOMInsured insured){
		BOMPol pol = new BOMPol();//多个险种
		ExeSQL tempExeSQL = new ExeSQL();
		try{
					boolean FreeFlag = false;
					BOMInsured tCurrentInsured = new BOMInsured();
					if(tLCPolSchema.getRiskCode().equals("121301"))
					{
						if(insured.getInsuredNo().equals(tLCPolSchema.getAppntNo())){
								pol.setFatherBOM(insured);
								tCurrentInsured = insured;
								FreeFlag = true;
						}
					}
					else if(insured.getInsuredNo().equals(tLCPolSchema.getInsuredNo())){
							pol.setFatherBOM(insured);
							tCurrentInsured = insured;
					}

					
					pol.setAmnt(Double.valueOf(String.valueOf(tLCPolSchema.getAmnt())));
					pol.setBonusGetMode(tLCPolSchema.getBonusGetMode());
					pol.setYears(Double.valueOf(tLCPolSchema.getYears()));
					pol.setBonusManType(tLCPolSchema.getBonusMan());
					if(!(tLCPolSchema.getCValiDate()==null||"".equals(tLCPolSchema.getCValiDate()))){
						theDate=tLCPolSchema.getCValiDate()+" 00:00:00";
						pol.setCValiDate(sdf.parse(theDate));
					}
					String DerateOrFreeSql="select count(*)  from LCDuty where polno='" +
								"?polno?"+"' and FreeFlag='1'";
					SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
					sqlbv1.sql(DerateOrFreeSql);
					sqlbv1.put("polno", tLCPolSchema.getPolNo());
					String tDerateOrFree = "";
					tDerateOrFree = tempExeSQL.getOneValue(sqlbv1);
					if(tDerateOrFree!=null&&!tDerateOrFree.equals("")&&Integer.parseInt(tDerateOrFree)>0){
						pol.setDerateOrFreeFlag("1");//保费减费/免费标志
					}else{
						pol.setDerateOrFreeFlag("0");//保费减费/免费标志
					}
					
					pol.setFloatRate(new Double(tLCPolSchema.getFloatRate()));
					String AddFeeSql="select payplantype from lcprem where  payplancode like '000000%%' and polno='" +
							"?polno?"+"'";
					SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
					sqlbv2.sql(AddFeeSql);
					sqlbv2.put("polno", tLCPolSchema.getPolNo());
					SSRS tAddFeeSSRS = new SSRS();
					tAddFeeSSRS = tempExeSQL.execSQL(sqlbv2);
					for(int add=1;add<=tAddFeeSSRS.getMaxRow();add++)
					{
						String AddFeeFlag = tAddFeeSSRS.GetText(add,1);
						if("01".equals(AddFeeFlag)){
							pol.setHAddFeeFlag("1");//健康加费标记
						}else if("02".equals(AddFeeFlag)){
							pol.setOAddFeeFlag(AddFeeFlag);//职业加费标记
						}
					}

					pol.setInsuredNo(tLCPolSchema.getInsuredNo());
					pol.setInsuYear(Double.valueOf(String.valueOf(tLCPolSchema.getInsuYear())));
					pol.setInsuYearFlag(tLCPolSchema.getInsuYearFlag());
					//tongmeng 2009-03-25 modify
					//
					String tSQL = "select risktype8 from lmriskapp where riskcode='?riskcode?'";
					SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
					sqlbv3.sql(tSQL);
					sqlbv3.put("riskcode", tLCPolSchema.getRiskCode());
					ExeSQL tExeSQL = new ExeSQL();
					String tValue = tExeSQL.getOneValue(sqlbv3);
					//121801险种的规则大部分都不符合意外险的规则，所以在规则引擎中把121801排除意外险的行列
//					if("121801".equals(tLCPolSchema.getRiskCode().trim())){
						pol.setKindCode(tValue);//险种类别
//					} else {
//						pol.setKindCode("50");
//					}
					pol.setLiveGetMode(tLCPolSchema.getLiveGetMode());
					pol.setMainPolNo(tLCPolSchema.getMainPolNo());
					pol.setMult(new Double(tLCPolSchema.getMult()));
					
					//终交年龄年期 ---
					pol.setPayYears(new Double(tLCPolSchema.getPayEndYear()));
					pol.setPolNo(tLCPolSchema.getPolNo());
					pol.setPrem(new Double(tLCPolSchema.getPrem()));
					pol.setRiskCode(tLCPolSchema.getRiskCode());//险种编码
					pol.setStopFlag(tLCPolSchema.getStopFlag());
					
					String tTotalAmntSql = "";
					SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
					  LDExch tLDExch = new LDExch();
				      LCPolDB currLCPolDB = new LCPolDB();
				      LCPolSet currLCPolSet = new LCPolSet();
				      double totalCurr = 0.00;
					if(!FreeFlag)
					{
//						tTotalAmntSql="select sum(Amnt) from lcpol where insuredno='"
//									 +tLCPolSchema.getInsuredNo()+"' and riskcode='"+tLCPolSchema.getRiskCode()+"'"
//									 + " and appflag not in ('4','9') and uwflag not in ('1','2','a')";
						//有多币种险种的情况
						tTotalAmntSql = "select * from lcpol where insuredno='"
									 +"?insuredno?"+"' and riskcode='"+"?riskcode?"+"'"
									 + " and appflag not in ('4','9') and uwflag not in ('1','2','a')";
						sqlbv4.sql(tTotalAmntSql);
						sqlbv4.put("insuredno", tLCPolSchema.getInsuredNo());
						sqlbv4.put("riskcode", tLCPolSchema.getRiskCode());
					}
					else
					{
//						tTotalAmntSql="select sum(Amnt) from lcpol where insuredno='"
//									 +tLCPolSchema.getAppntNo()+"' and riskcode='"+tLCPolSchema.getRiskCode()+"'"
//									 + " and appflag not in ('4','9') and uwflag not in ('1','2','a')";
						//有多币种险种的情况
						tTotalAmntSql = "select * from lcpol where insuredno='"
									 +"?insuredno?"+"' and riskcode='"+"?riskcode?"+"'"
									 + " and appflag not in ('4','9') and uwflag not in ('1','2','a')";
						sqlbv4.sql(tTotalAmntSql);
						sqlbv4.put("insuredno", tLCPolSchema.getAppntNo());
						sqlbv4.put("riskcode", tLCPolSchema.getRiskCode());
					}
//					String tTotalAmnt = tempExeSQL.getOneValue(tTotalAmntSql);
//					if(!(tTotalAmnt==null||"".equals(tTotalAmnt))){
//						pol.setTotalAmnt(Double.valueOf(tTotalAmnt));//累计该险种保额
//					}
					currLCPolSet = currLCPolDB.executeQuery(sqlbv4);
					for (int j = 1; j <= currLCPolSet.size(); j++) {
						totalCurr += tLDExch.toBaseCur(currLCPolSet.get(j).getCurrency(), locCurrency,theCurrentDate, currLCPolSet.get(j).getAmnt());
					}
					pol.setTotalAmnt(totalCurr);//累计该险种保额
					
					String tTotalMultSql = "";
					SQLwithBindVariables sqlbv5=new SQLwithBindVariables();
					if(!FreeFlag)
					{
						tTotalMultSql="select sum((case mult when 0 then 1 else mult end)) from lcpol where insuredno='"
									+"?insuredno?"+"' and riskcode='"+"?riskcode?"+"'"
									+ " and appflag not in ('4','9') and uwflag not in ('1','2','a') ";
						sqlbv5.sql(tTotalMultSql);
						sqlbv5.put("insuredno", tLCPolSchema.getInsuredNo());
						sqlbv5.put("riskcode", tLCPolSchema.getRiskCode());
					}
					else
					{
						
						tTotalMultSql="select sum((case mult when 0 then 1 else mult end)) from lcpol where insuredno='"
									+"?insuredno?"+"' and riskcode='"+"?riskcode?"+"'"
									+ " and appflag not in ('4','9') and uwflag not in ('1','2','a') ";
						sqlbv5.sql(tTotalMultSql);
						sqlbv5.put("insuredno", tLCPolSchema.getAppntNo());
						sqlbv5.put("riskcode", tLCPolSchema.getRiskCode());
					}
					 
					String tTotalMult = tempExeSQL.getOneValue(sqlbv5);
					if(!(tTotalMult==null||"".equals(tTotalMult))){
						pol.setTotalMult(Double.valueOf(tTotalMult));//累计该险种份数
					}	
					pol.setUWFlag(tLCPolSchema.getUWFlag());	
					pol.setSumThisPrem(getSumThisPrem(tLCPolSchema.getRiskCode(),tLCPolSchema.getInsuredNo()));//累计该险种保费
					
					pol.setCurrency(tLCPolSchema.getCurrency());
				    
					
					//总标准保费---
					pol.setStandPrem(tLCPolSchema.getStandPrem());
					//终交年龄年期标志 ---
					pol.setCoveragefeeduringtheUnit(tLCPolSchema.getPayEndYearFlag());
//					定期追加保费---
//			        pol.setAddPrem(tLCPolSchema.getAddFee());
			        //目标保费---
			        String tRistType3Sql="select 1 from lmriskapp where risktype3 in('3','4') and riskcode='?riskcode?'";
                    sqlbv5.sql(tRistType3Sql);
                    sqlbv5.put("riskcode", tLCPolSchema.getRiskCode());
                    String tRiskType3= tempExeSQL.getOneValue(sqlbv5);
                    if("1".equals(tRiskType3)){
                    	SQLwithBindVariables sqlbv6=new SQLwithBindVariables();
                    	SSRS allPremSSRS = new SSRS();
                    	String tTargetPremSql =	"select b.prem\n" +
			    			        	"  from lcpol a, lcprem b, lmdutypay c\n" + 
			    			        	" where a.contno = b.contno\n" + 
			    			        	"   and b.payplancode = c.payplancode\n" + 
			    			        	"   and c.investtype in ('1','2')\n" + 
			    			        	"   and a.contno ='"+"?contno?' order by c.investtype ";
    			        sqlbv6.sql(tTargetPremSql);
    					sqlbv6.put("contno", tLCPolSchema.getContNo());
    					allPremSSRS = tempExeSQL.execSQL(sqlbv6);
    					if(allPremSSRS != null && allPremSSRS.getMaxRow()>0){
    						pol.setTargetPrem(Double.valueOf(allPremSSRS.GetText(1, 1)));//目标保费
    						pol.setAddPrem(Double.valueOf(allPremSSRS.GetText(2, 1)));//定期追加保费
    					}
    			        
    			        
    			        
                    }else{
                    	pol.setTargetPrem(tLCPolSchema.getPrem());
                    }
                    
			        
			        
			        //账户价值---
			        pol.setAccountValue(Double.valueOf(0));
			        //生存金的累计周期---
			        pol.setAccumulationPeriod(String.valueOf(tLCPolSchema.getYears()));
			        //首年度保险费的20%---
			        pol.setFirst20PremiumOfAnn(tLCPolSchema.getPrem()*0.2);
			        //已经生效或正在申请的身故保障金金额---
			        pol.setForOrProposalDiedMomey(Double.valueOf(25));
					
					
		}catch(Exception e){
			CError.buildErr(this, "准备BOMPol出错！");
			e.printStackTrace();
		}
		return pol;
	}
	private BOMBnf DealBOMBnf(LCBnfSchema tLCBnfSchema,BOMInsured insured){
		BOMBnf bnf = new BOMBnf();//多个受益人
		try{

				if(!(tLCBnfSchema.getBirthday()==null||"".equals(tLCBnfSchema.getBirthday()))){
					theDate=tLCBnfSchema.getBirthday()+" 00:00:00";
					bnf.setBirthday(sdf.parse(theDate));
				}
				bnf.setBnfGrade(tLCBnfSchema.getBnfGrade());
				bnf.setBnfLot(new Double(tLCBnfSchema.getBnfLot()));
				bnf.setBnfType(tLCBnfSchema.getBnfType());
				bnf.setBnfName(tLCBnfSchema.getName());
				bnf.setCustomerNo(tLCBnfSchema.getCustomerNo());
				bnf.setInsuredNo(tLCBnfSchema.getInsuredNo());

				insured=insured;
				if(insured.getInsuredNo().equals(bnf.getInsuredNo())){
					bnf.setFatherBOM(insured);
				}
				bnf.setRelationToInsured(tLCBnfSchema.getRelationToInsured());
				bnf.setSex(tLCBnfSchema.getSex());

		}catch(Exception e){
			CError.buildErr(this, "准备BOMBnf时出错或无受益人！");
			e.printStackTrace();
		}
		return bnf;
	}
	
	private BOMMainPol DealBOMMainPol(LCPolSchema tmainLCPolSchema,BOMInsured insured){
		BOMMainPol mainpol = new BOMMainPol();//多主险
		try{
					ExeSQL tempExeSQL = new ExeSQL();
					mainpol.setAmnt(Double.valueOf(String.valueOf(tmainLCPolSchema.getAmnt())));
					mainpol.setBonusGetMode(tmainLCPolSchema.getBonusGetMode());
					mainpol.setBonusManType(tmainLCPolSchema.getBonusMan());
					mainpol.setInsuredNo(tmainLCPolSchema.getInsuredNo());
					if(!(tmainLCPolSchema.getCValiDate()==null||"".equals(tmainLCPolSchema.getCValiDate()))){
						theDate=tmainLCPolSchema.getCValiDate()+" 00:00:00";
						mainpol.setCValiDate(sdf.parse(theDate));
					}
					String DerateOrFreeSql="select count(*)  from LCDuty where polno='" +
							"?polno?"+"' and FreeFlag='1'";
					SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
					sqlbv1.sql(DerateOrFreeSql);
					sqlbv1.put("polno", tmainLCPolSchema.getPolNo());
					String tDerateOrFree = "";
					tDerateOrFree = tempExeSQL.getOneValue(sqlbv1);
					if(tDerateOrFree!=null&&!tDerateOrFree.equals("")&&Integer.parseInt(tDerateOrFree)>0){
						mainpol.setDerateOrFreeFlag("1");//保费减费/免费标志
					}else{
						mainpol.setDerateOrFreeFlag("0");
					}
					if(insured.getInsuredNo().equals(mainpol.getInsuredNo())){
						mainpol.setFatherBOM(insured);
					}
					mainpol.setFloatRate(Double.valueOf(String.valueOf(tmainLCPolSchema.getFloatRate())));
					SSRS tAddFeeSSRS = new SSRS();
					String AddFeeSql="select payplantype from lcprem where  payplancode like '000000%%' and polno='" +
					"?polno?"+"'";
					SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
					sqlbv2.sql(AddFeeSql);
					sqlbv2.put("polno", tmainLCPolSchema.getPolNo());
					tAddFeeSSRS = tempExeSQL.execSQL(sqlbv2);
					for(int add=1;add<=tAddFeeSSRS.getMaxRow();add++)
					{
						String AddFeeFlag = tAddFeeSSRS.GetText(add,1);
						if("01".equals(AddFeeFlag)){
							mainpol.setHAddFeeFlag("1");//健康加费标记
						}else if("02".equals(AddFeeFlag)){
							mainpol.setOAddFeeFlag(AddFeeFlag);//职业加费标记
						}
					}
					mainpol.setInsuYear(Double.valueOf(String.valueOf(tmainLCPolSchema.getInsuYear())));
					mainpol.setInsuYearFlag(tmainLCPolSchema.getInsuYearFlag());
					//tongmeng 2009-03-25 modify
					//
					String tSQL = "select risktype8 from lmriskapp where riskcode='"+"?riskcode?"+"'";
					ExeSQL tExeSQL = new ExeSQL();
					SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
					sqlbv3.sql(tSQL);
					sqlbv3.put("riskcode", tmainLCPolSchema.getRiskCode());
					String tValue = tExeSQL.getOneValue(sqlbv3);
					mainpol.setKindCode(tValue);
					//mainpol.setKindCode(tmainLCPolSchema.getKindCode());
					mainpol.setLiveGetMode(tmainLCPolSchema.getLiveGetMode());
					mainpol.setMainPolNo(tmainLCPolSchema.getMainPolNo());
					mainpol.setMult(Double.valueOf(String.valueOf(tmainLCPolSchema.getMult())));
					mainpol.setPayYears(Double.valueOf(tmainLCPolSchema.getPayYears()));
					mainpol.setPolNo(tmainLCPolSchema.getPolNo());
					mainpol.setPrem(Double.valueOf(String.valueOf(tmainLCPolSchema.getPrem())));
					mainpol.setRiskCode(tmainLCPolSchema.getRiskCode());
					String SpecSql="select count(*) from lccspec where contno = '"+"?contno?"+"'";
					String tSpec = "";
					SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
					sqlbv4.sql(SpecSql);
					sqlbv4.put("contno", tmainLCPolSchema.getContNo());
					tSpec=tempExeSQL.getOneValue(sqlbv4);
					if(tSpec!=null&&!tSpec.equals("")&&Integer.parseInt(tSpec)>0){
						mainpol.setSpecFlag("1");//特约标记
					}else{
						mainpol.setSpecFlag("0");//特约标记
					}
					mainpol.setStopFlag(tmainLCPolSchema.getStopFlag());
//					String totalAmntSql="select sum(amnt) from lcpol where riskcode='"
//						+tmainLCPolSchema.getRiskCode()+"' and insuredno='"
//						+tmainLCPolSchema.getInsuredNo()+"' and uwflag not in ('1','2','a') and appflag not in ('4','9')";
//					String tTotalAmnt = tempExeSQL.getOneValue(totalAmntSql);
//					if(!(tTotalAmnt==null||"".equals(tTotalAmnt))){
//						mainpol.setTotalAmnt(Double.valueOf(tTotalAmnt));//累计该险种保额
//					}
					//有多币种险种的情况
					String currString = "select * from lcpol where riskcode='"
						+"?riskcode?"+"' and insuredno='"
						+"?insuredno?"+"' and uwflag not in ('1','2','a') and appflag not in ('4','9')";
					LDExch tLDExch = new LDExch();
					SQLwithBindVariables sqlbv5=new SQLwithBindVariables();
					sqlbv5.sql(currString);
					sqlbv5.put("riskcode", tmainLCPolSchema.getRiskCode());
					sqlbv5.put("insuredno", tmainLCPolSchema.getInsuredNo());
					LCPolDB currLCPolDB = new LCPolDB();
					LCPolSet currLCPolSet = new LCPolSet();
					currLCPolSet = currLCPolDB.executeQuery(sqlbv5);
					double totalCurr = 0.00;
					for (int j = 1; j <= currLCPolSet.size(); j++) {
						totalCurr += tLDExch.toBaseCur(currLCPolSet.get(j).getCurrency(), locCurrency, theCurrentDate,currLCPolSet.get(j).getAmnt());
					}
					mainpol.setTotalAmnt(totalCurr);//累计该险种保额
					mainpol.setUWFlag(tmainLCPolSchema.getUWFlag());
					mainpol.setCurrency(tmainLCPolSchema.getCurrency());
		}catch(Exception e){
			CError.buildErr(this, "准备BOMMainPol时出错！");
			e.printStackTrace();
		}
		
		return mainpol;
	}
	
	private BOMSubPol DealBOMSubPol(LCPolSchema subLCPolSchema,BOMMainPol mainpol){
		BOMSubPol subpol = new BOMSubPol();	//多个附加险
		ExeSQL tempExeSQL = new ExeSQL();
		try {
					subpol.setAmnt(Double.valueOf(String.valueOf(subLCPolSchema.getAmnt())));
					subpol.setBonusGetMode(subLCPolSchema.getBonusGetMode());//ok
					subpol.setBonusManType(subLCPolSchema.getBonusMan());//ok
					if(!(subLCPolSchema.getCValiDate()==null||"".equals(subLCPolSchema.getCValiDate()))){
						theDate=subLCPolSchema.getCValiDate()+" 00:00:00";
						subpol.setCValiDate(sdf.parse(theDate));
					}
					
					
					String DerateOrFreeSql="select count(*)  from LCDuty where polno='" +
							"?polno?"+"' and FreeFlag='1'";
					SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
					sqlbv1.sql(DerateOrFreeSql);
					sqlbv1.put("polno", subLCPolSchema.getPolNo());
					String tDerateOrFree = "";
					tDerateOrFree = tempExeSQL.getOneValue(sqlbv1);
					subpol.setMainPolNo(subLCPolSchema.getMainPolNo());
					if(tDerateOrFree!=null&&!tDerateOrFree.equals("")&&Integer.parseInt(tDerateOrFree)>0){
						subpol.setDerateOrFreeFlag("1");//保费减费/免费标志
					}else{
						subpol.setDerateOrFreeFlag("0");//保费减费/免费标志
					}
					if(mainpol.getMainPolNo().equals(subpol.getMainPolNo())){
						subpol.setFatherBOM(mainpol);
					}
					subpol.setFloatRate(Double.valueOf(String.valueOf(subLCPolSchema.getFloatRate())));
					SSRS tAddFeeSSRS = new SSRS();
					String AddFeeSql="select payplantype from lcprem where  payplancode like '000000%%' and polno='" +
					"?polno?"+"'";
					SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
					sqlbv2.sql(AddFeeSql);
					sqlbv2.put("polno", subLCPolSchema.getPolNo());
					tAddFeeSSRS = tempExeSQL.execSQL(sqlbv2);
					for(int add=1;add<=tAddFeeSSRS.getMaxRow();add++)
					{
						String AddFeeFlag = tAddFeeSSRS.GetText(add,1);
						if("01".equals(AddFeeFlag)){
							subpol.setHAddFeeFlag("1");//健康加费标记
						}else if("02".equals(AddFeeFlag)){
							subpol.setOAddFeeFlag(AddFeeFlag);//职业加费标记
						}
					}
					subpol.setInsuredNo(subLCPolSchema.getInsuredNo());
					subpol.setInsuYear(Double.valueOf(String.valueOf(subLCPolSchema.getInsuYear())));
					subpol.setInsuYearFlag(subLCPolSchema.getInsuYearFlag());
					//tongmeng 2009-03-25 modify
					//
					String tSQL = "select risktype8 from lmriskapp where riskcode='?riskcode?'";
					SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
					sqlbv3.sql(tSQL);
					sqlbv3.put("riskcode", subpol.getRiskCode());
					ExeSQL tExeSQL = new ExeSQL();
					String tValue = tExeSQL.getOneValue(sqlbv3);
					subpol.setKindCode(tValue);
					//				subpol.setKindCode(subLCPolSchema.getKindCode());
					subpol.setLiveGetMode(subLCPolSchema.getLiveGetMode());
					subpol.setMult(Double.valueOf(String.valueOf(subLCPolSchema.getMult())));
					subpol.setPayYears(Double.valueOf(subLCPolSchema.getPayYears()));
					subpol.setPolNo(subLCPolSchema.getPolNo());
					subpol.setPrem(Double.valueOf(String.valueOf(subLCPolSchema.getPrem())));
					subpol.setRiskCode(subLCPolSchema.getRiskCode());
					String SpecSql="select count(*) from lccspec where contno = '"+"?contno?"+"'";
					String tSpec = "";
					SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
					sqlbv4.sql(SpecSql);
					sqlbv4.put("contno", subLCPolSchema.getContNo());
					tSpec=tempExeSQL.getOneValue(sqlbv4);
					if(tSpec!=null&&!tSpec.equals("")&&Integer.parseInt(tSpec)>0){
						subpol.setSpecFlag("1");//特约标记
					}else{
						subpol.setSpecFlag("0");//特约标记
					}
					subpol.setStopFlag(subLCPolSchema.getStopFlag());
//					String totalAmntSql="select sum(amnt) from lcpol where riskcode='"
//						+subLCPolSchema.getRiskCode()+"' and insuredno='"
//						+subLCPolSchema.getInsuredNo()+"' and uwflag not in ('1','2','a') and appflag not in ('4','9')";
					//有多币种险种的情况
					String currString = "select * from lcpol where riskcode='"
						+"?riskcode?"+"' and insuredno='"
						+"?insuredno?"+"' and uwflag not in ('1','2','a') and appflag not in ('4','9')";
					LDExch tLDExch = new LDExch();
					SQLwithBindVariables sqlbv5=new SQLwithBindVariables();
					sqlbv5.sql(currString);
					sqlbv5.put("riskcode", subLCPolSchema.getRiskCode());
					sqlbv5.put("insuredno", subLCPolSchema.getInsuredNo());
					LCPolDB currLCPolDB = new LCPolDB();
					LCPolSet currLCPolSet = new LCPolSet();
					currLCPolSet = currLCPolDB.executeQuery(sqlbv5);
					double totalCurr = 0.00;
					for (int j = 1; j <= currLCPolSet.size(); j++) {
						totalCurr += tLDExch.toBaseCur(currLCPolSet.get(j).getCurrency(), locCurrency, theCurrentDate,currLCPolSet.get(j).getAmnt());
					}
//					String tTotalAmnt = tempExeSQL.getOneValue(totalAmntSql);
//					if(!(tTotalAmnt==null||"".equals(tTotalAmnt))){
//						subpol.setTotalAmnt(Double.valueOf(tTotalAmnt));
//					}
					subpol.setTotalAmnt(totalCurr);
					
					subpol.setUWFlag(subLCPolSchema.getUWFlag());  //ok
					subpol.setCurrency(subLCPolSchema.getCurrency());
					//wanghaichao payintv 2016-11-29
					subpol.setPayIntv(String.valueOf(subLCPolSchema.getPayIntv()));
					//end
		} catch (Exception e) {
			CError.buildErr(this, "准备BOMSubPol时出错！");
			e.printStackTrace();
		}
		return subpol;
	}
	
	private BOMSubPol2[] DealBOMSubPol2(LCPolSet tSubLCPolSet,BOMMainPol[] mainpols){
		int subCount=tSubLCPolSet.size();
		BOMSubPol2[] subpol2s = new BOMSubPol2[subCount];	//多个附加险
		ExeSQL tempExeSQL = new ExeSQL();
		try {
			if(tSubLCPolSet.size()>0){
				for(int i=1;i<=tSubLCPolSet.size();i++){
					BOMSubPol2 subpol = new BOMSubPol2();
					LCPolSchema subLCPolSchema = new LCPolSchema();
					subLCPolSchema=tSubLCPolSet.get(i);
					subpol.setAmnt(Double.valueOf(String.valueOf(subLCPolSchema.getAmnt())));
					subpol.setBonusGetMode(subLCPolSchema.getBonusGetMode());
					subpol.setBonusManType(subLCPolSchema.getBonusMan());
					subpol.setMainPolNo(subLCPolSchema.getMainPolNo());
					if(!(subLCPolSchema.getCValiDate()==null||"".equals(subLCPolSchema.getCValiDate()))){
						theDate=subLCPolSchema.getCValiDate()+" 00:00:00";
						subpol.setCValiDate(sdf.parse(theDate));
					}
					String DerateOrFreeSql="select count(*) from LCDuty where polno='" +
							"?polno?"+"' and FreeFlag='1'";
					SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
					sqlbv1.sql(DerateOrFreeSql);
					sqlbv1.put("polno", subLCPolSchema.getPolNo());
					String tDerateOrFree = "";
					tDerateOrFree = tempExeSQL.getOneValue(sqlbv1);
					if(tDerateOrFree!=null&&!tDerateOrFree.equals("")&&Integer.parseInt(tDerateOrFree)>0){
						subpol.setDerateOrFreeFlag("1");//保费减费/免费标志
					}else{
						subpol.setDerateOrFreeFlag("0");//保费减费/免费标志
					}
					for(int k=0;k<mainpols.length;k++){
						BOMMainPol tBOMMainPol = mainpols[k];
						if(tBOMMainPol.getMainPolNo().equals(subpol.getMainPolNo())){
							subpol.setFatherBOM(tBOMMainPol);
						}
					}
					subpol.setFloatRate(Double.valueOf(String.valueOf(subLCPolSchema.getFloatRate())));
					SSRS tAddFeeSSRS = new SSRS();
					String AddFeeSql="select payplantype from lcprem where  payplancode like '000000%%' and polno='" +
				    "?polno?"+"'";
					SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
					sqlbv2.sql(AddFeeSql);
					sqlbv2.put("polno", subLCPolSchema.getPolNo());
					tAddFeeSSRS = tempExeSQL.execSQL(sqlbv2);
					for(int add=1;add<=tAddFeeSSRS.getMaxRow();add++)
					{
						String AddFeeFlag = tAddFeeSSRS.GetText(add,1);
						if("01".equals(AddFeeFlag)){
							subpol.setHAddFeeFlag("1");//健康加费标记
						}else if("02".equals(AddFeeFlag)){
							subpol.setOAddFeeFlag(AddFeeFlag);//职业加费标记
						}
					}
					subpol.setInsuredNo(subLCPolSchema.getInsuredNo());
					subpol.setInsuYear(Double.valueOf(String.valueOf(subLCPolSchema.getInsuYear())));
					subpol.setInsuYearFlag(subLCPolSchema.getInsuYearFlag());
					//tongmeng 2009-03-25 modify
					//
					String tSQL = "select risktype8 from lmriskapp where riskcode='?riskcode?'";
					SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
					sqlbv3.sql(tSQL);
					sqlbv3.put("riskcode", subpol.getRiskCode());
					ExeSQL tExeSQL = new ExeSQL();
					String tValue = tExeSQL.getOneValue(sqlbv3);
					subpol.setKindCode(tValue);
					//subpol.setKindCode(subLCPolSchema.getKindCode());
					subpol.setLiveGetMode(subLCPolSchema.getLiveGetMode());
					subpol.setMult(Double.valueOf(String.valueOf(subLCPolSchema.getMult())));
					subpol.setPayYears(Double.valueOf(subLCPolSchema.getPayYears()));
					subpol.setPolNo(subLCPolSchema.getPolNo());
					subpol.setPrem(Double.valueOf(String.valueOf(subLCPolSchema.getPrem())));
					subpol.setRiskCode(subLCPolSchema.getRiskCode());
					subpol.setStopFlag(subLCPolSchema.getStopFlag());
//					String totalAmntSql="select sum(amnt) from lcpol where riskcode='"
//						+subLCPolSchema.getRiskCode()+"' and insuredno='"
//						+subLCPolSchema.getInsuredNo()+"' and uwflag not in ('1','2','a') and appflag not in ('4','9')";
//					String tTotalAmnt = tempExeSQL.getOneValue(totalAmntSql);
//					if(!(tTotalAmnt==null||"".equals(tTotalAmnt))){
//						subpol.setTotalAmnt(Double.valueOf(tTotalAmnt));
//					}
			        //有多币种险种的情况
					String currString = "select * from lcpol where riskcode='"
						+"?riskcode?"+"' and insuredno='"
						+"?insuredno?"+"' and uwflag not in ('1','2','a') and appflag not in ('4','9')";
					LDExch tLDExch = new LDExch();
					SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
					sqlbv4.sql(currString);
					sqlbv4.put("riskcode", subLCPolSchema.getRiskCode());
					sqlbv4.put("insuredno", subLCPolSchema.getInsuredNo());
					LCPolDB currLCPolDB = new LCPolDB();
					LCPolSet currLCPolSet = new LCPolSet();
					currLCPolSet = currLCPolDB.executeQuery(sqlbv4);
					double totalCurr = 0.00;
					for (int j = 1; j <= currLCPolSet.size(); j++) {
						totalCurr += tLDExch.toBaseCur(currLCPolSet.get(j).getCurrency(), locCurrency, theCurrentDate,currLCPolSet.get(j).getAmnt());
					}
					subpol.setTotalAmnt(totalCurr);
					
					subpol.setUWFlag(subLCPolSchema.getUWFlag());
					subpol.setCurrency(subLCPolSchema.getCurrency());
					subpol2s[i-1]=subpol;
				}
			}
		} catch (Exception e) {
			CError.buildErr(this, "准备BOMSubPol时出错！");
			e.printStackTrace();
		}
		return subpol2s;
	}
	


	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(String tContNo) {
		

		// 获得当前工作任务的任务ID
		mContNo = tContNo;
		if (mContNo == null) {
			// @@错误处理
			CError.buildErr(this,"前台传输业务数据中mContNo失败!");
			return false;
		}

		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(mContNo);
		if (tLCContDB.getInfo()) { // 验证LCCont表中是否存在该合同项记录
			mLCContSchema.setSchema(tLCContDB.getSchema());
		} else {
			CError.buildErr(this, "合同号为" + mLCContSchema.getContNo() + "未查询到!");
			return false;
		}

		// 判断国家及本币
		String CurrString = "select codename from ldcode1 where codetype = 'currencyprecision' " 
			+" and code1 = (select sysvarvalue from ldsysvar where sysvar='nativeplace')";
		ExeSQL tExeSQL = new ExeSQL();
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(CurrString);
		locCurrency = tExeSQL.getOneValue(sqlbv);
		if(locCurrency==null||"".equals(locCurrency)){
			CError.buildErr(this, "查询本币信息失败！");
			return false;
		}
		
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public TransferData getReturnTransferData() {
		return mTransferData;
	}

	public CErrors getErrors() {
		return mErrors;
	}

	public float parseFloat(String s) {
		if (s.length() < 0 || s.equals("")) {
			return 0;
		}
		float f1 = 0;
		String tmp = "";
		String s1 = "";
		for (int i = 0; i < s.length(); i++) {
			s1 = s.substring(i, i + 1);
			if (s1.equals("0") || s1.equals("1") || s1.equals("2")
					|| s1.equals("3") || s1.equals("4") || s1.equals("5")
					|| s1.equals("6") || s1.equals("7") || s1.equals("8")
					|| s1.equals("9") || s1.equals(".")) {
				tmp = tmp + s1;
			} else if (tmp.length() > 0) {
				break;
			}
		}
		f1 = Float.parseFloat(tmp);
		return f1;
	}

	public String getHierarhy() {
		// String tsql = "select RiskSortValue from lmrisksort where
		// riskcode='"+tLCPolSchema.getRiskCode()+"' and risksorttype='5'";
		// ExeSQL riskSql = new ExeSQL();
		// SSRS sumAmntSSRS = new SSRS();
		// sumAmntSSRS = riskSql.execSQL(tsql);
		// String riskty=sumAmntSSRS.GetText(1,1);
		// tsql = "select uwpopedom from lduwgradeperson where uwtype=1 and
		// maxamnt>'"+riskamnt+"' and uwkind='"+riskty+"' order by maxamnt";
		// sumAmntSSRS = riskSql.execSQL(tsql);
		return hierarhy; // 返回几级核保师
	}

	public String getReDistribute() {
		logger.debug("返回分保标志前====" + reDistribute);
		return reDistribute; // 返回是否需要分保--1:需要0:不需要

	}

	public boolean SendRefuseNotice(String tContNo) {
		String strNoLimit = PubFun.getNoLimit(mGlobalInput.ComCode);
		String mPrtSeq = PubFun1.CreateMaxNo("PRTSEQNO", strNoLimit); // 生成打印流水号
		mLOPRTManagerSchema.setPrtSeq(mPrtSeq);
		mLOPRTManagerSchema.setOtherNo(tContNo);
		mLOPRTManagerSchema.setOtherNoType("02");
		mLOPRTManagerSchema.setCode("JB00");
		mLOPRTManagerSchema.setManageCom(mLCContSchema.getManageCom());
		mLOPRTManagerSchema.setAgentCode(mLCContSchema.getAgentCode());
		mLOPRTManagerSchema.setReqCom(mLCContSchema.getManageCom());
		mLOPRTManagerSchema.setReqOperator(mGlobalInput.Operator);
		mLOPRTManagerSchema.setExeCom(mLCContSchema.getManageCom());
		mLOPRTManagerSchema.setExeOperator(mGlobalInput.Operator);
		mLOPRTManagerSchema.setPrtType("0");
		mLOPRTManagerSchema.setStateFlag("0");
		mLOPRTManagerSchema.setMakeDate(PubFun.getCurrentDate());
		mLOPRTManagerSchema.setMakeTime(PubFun.getCurrentTime());
		mLOPRTManagerSchema.setOldPrtSeq(mPrtSeq);
		mLOPRTManagerSchema.setPatchFlag("0");
		return true;
	}

	private void GetAllSumAmnt(String tInsuredNo) {
		// //为自核服务 write by yaory///////
		String risktype = "";
		try {
			logger.debug(mContNo);
			ExeSQL riskSql = new ExeSQL();
			//tongmeng 2009-03-12 modify
			//使用新的自核风险保额计算规则
			String tsql = "";
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			//寿险风险保额
			/*
			   -- tRiskType = 1 寿险风险保额
			   -- tRiskType = 2 重疾险风险保额
               -- tRiskType = 3 医疗险风险保额
               -- tRiskType = 4 意外险风险保额
               -- tRiskType = 12 身故风险保额
               -- tRiskType = 13 寿险体检额度
               -- tRiskType = 14 重疾体检额度
               -- tRiskType = 15 医疗体检额度

			 */
//			tsql = "select healthyamnt2('" + "?tInsuredNo?"
//			+ "','1','1') from dual";
		 if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
			 tsql = "select healthyamnt2('" + "?tInsuredNo?" + "','1','1') from dual";
		 }else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
			 tsql ="{ call HEALTHYAMNT2(?#@d#?,'" + "?tInsuredNo?" + "','1','1') }";
		 }
			sqlbv.sql(tsql);
			sqlbv.put("tInsuredNo", tInsuredNo);
			String tempAmnt = riskSql.getOneValue(sqlbv);			
			LSumDangerAmnt = parseFloat(tempAmnt);//寿险
			
//			tsql = "select healthyamnt2('" + "?tInsuredNo?"
//			+ "','2','1') from dual";
		 if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
			 tsql = "select healthyamnt2('" + "?tInsuredNo?" + "','2','1') from dual";
		 }else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
			 tsql ="{ call HEALTHYAMNT2(?#@d#?,'" + "?tInsuredNo?" + "','2','1') }";
		 }
			sqlbv=new SQLwithBindVariables();
			sqlbv.sql(tsql);
			sqlbv.put("tInsuredNo", tInsuredNo);
			tempAmnt = riskSql.getOneValue(sqlbv);
			DSumDangerAmnt = parseFloat(tempAmnt); //重疾
			
//			tsql = "select healthyamnt2('" + "?tInsuredNo?"
//			+ "','3','1') from dual";
		 if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
			 tsql = "select healthyamnt2('" + "?tInsuredNo?" + "','3','1') from dual";
		 }else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
			 tsql ="{ call healthyamnt2(?#@d#?,'" + "?tInsuredNo?" + "','3','1') }";
		 }
			sqlbv=new SQLwithBindVariables();
			sqlbv.sql(tsql);
			sqlbv.put("tInsuredNo", tInsuredNo);
			tempAmnt = riskSql.getOneValue(sqlbv);
			SSumDangerAmnt = parseFloat(tempAmnt); //医疗
			
//			tsql = "select healthyamnt2('" + "?tInsuredNo?"
//			+ "','4','1') from dual";
		 if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
			 tsql = "select healthyamnt2('" + "?tInsuredNo?" + "','4','1') from dual";
		 }else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
			 tsql ="{ call healthyamnt2(?#@d#?,'" + "?tInsuredNo?" + "','4','1') }";
		 }
			sqlbv=new SQLwithBindVariables();
			sqlbv.sql(tsql);
			sqlbv.put("tInsuredNo", tInsuredNo);
			tempAmnt = riskSql.getOneValue(sqlbv);
			MSumDangerAmnt = parseFloat(tempAmnt); //意外
//			tsql = "select healthyamnt2('" + "?tInsuredNo?"
//			+ "','12','1') from dual";
		 if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
			 tsql = "select healthyamnt2('" + "?tInsuredNo?" + "','12','1') from dual";
		 }else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
			 tsql ="{ call healthyamnt2(?#@d#?,'" + "?tInsuredNo?" + "','12','1') }";
		 }
			sqlbv=new SQLwithBindVariables();
			sqlbv.sql(tsql);
			sqlbv.put("tInsuredNo", tInsuredNo);
			tempAmnt = riskSql.getOneValue(sqlbv);
			SSumDieAmnt = parseFloat(tempAmnt); //身故
			AllSumAmnt=LSumDangerAmnt+DSumDangerAmnt+MSumDangerAmnt;//2009-3-5 modify累计风险保额=累计寿险风险保额+累计重疾风险保额+累计意外伤害风险保额
			
		} catch (Exception ex) {
		}
	}
	/**
	 * @param 根据数据库中的描述算出核保级别 执行getUWGrade函数
	 * */
	private String getUWGrade(String tInsuredNo){
		String tUWGrade="";
		String tempUWGrade = "";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		try {
			logger.debug(mContNo);
			ExeSQL riskSql = new ExeSQL();
			double RiskAmnt1=0;//累计寿险风险保额
			double RiskAmnt2=0;//累积重疾风险保额
			double RiskAmnt4=0;//累积意外风险保额
			double RiskAmnt6=0;		
			//duanyh 2009-03-14 modify
			//使用新的自核风险保额计算规则
			String tsql = "";
			//寿险风险保额
			/*
			   -- tRiskType = 1 寿险风险保额
			   -- tRiskType = 2 重疾险风险保额
               -- tRiskType = 3 医疗险风险保额
               -- tRiskType = 4 意外险风险保额
               -- tRiskType = 12 身故风险保额
               -- tRiskType = 13 寿险体检额度
               -- tRiskType = 14 重疾体检额度
               -- tRiskType = 15 医疗体检额度

			 */
//			tsql = "select healthyamnt2('" + "?tInsuredNo?"
//			+ "','1','1') from dual";
		 if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
			 tsql = "select healthyamnt2('" + "?tInsuredNo?" + "','1','1') from dual";
		 }else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
			 tsql ="{ call healthyamnt2(?#@d#?,'" + "?tInsuredNo?" + "','1','1') }";
		 }
			sqlbv.sql(tsql);
			sqlbv.put("tInsuredNo", tInsuredNo);
			String tempAmnt = riskSql.getOneValue(sqlbv);			
			RiskAmnt1 = parseFloat(tempAmnt);//寿险
			
//			tsql = "select healthyamnt2('" + "?tInsuredNo?"
//			+ "','2','1') from dual";
		 if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
			tsql = "select healthyamnt2('" + "?tInsuredNo?" + "','2','1') from dual";
		 }else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
			 tsql ="{ call healthyamnt2(?#@d#?,'" + "?tInsuredNo?" + "','2','1') }";
		 }
			sqlbv=new SQLwithBindVariables();
			sqlbv.sql(tsql);
			sqlbv.put("tInsuredNo", tInsuredNo);
			tempAmnt = riskSql.getOneValue(sqlbv);
			RiskAmnt2 = parseFloat(tempAmnt); //重疾
			
//			tsql = "select healthyamnt2('" + "?tInsuredNo?"
//			+ "','4','1') from dual";
		 if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
				tsql = "select healthyamnt2('" + "?tInsuredNo?" + "','4','1') from dual";
		 }else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
			 tsql ="{ call healthyamnt2(?#@d#?,'" + "?tInsuredNo?" + "','4','1') }";
		 }
			sqlbv=new SQLwithBindVariables();
			sqlbv.sql(tsql);
			sqlbv.put("tInsuredNo", tInsuredNo);
			tempAmnt = riskSql.getOneValue(sqlbv);
			RiskAmnt4 = parseFloat(tempAmnt); //意外			

			RiskAmnt6 = RiskAmnt1+RiskAmnt2+RiskAmnt4;
			logger.debug("RiskAmnt1:"+RiskAmnt1+"  RiskAmnt2:"+RiskAmnt2+" " +
					" RiskAmnt4:"+RiskAmnt4+"  RiskAmnt6:"+RiskAmnt6);
			//执行getUWGrade函数，返回核保级别
			String UWGradeSql = "select trim(case when getUWGrade('1','?RiskAmnt1?','2','?RiskAmnt2?','4','?RiskAmnt4?','6','?RiskAmnt6?') is null then '1' else getUWGrade('1','?RiskAmnt1?','2','?RiskAmnt2?','4','?RiskAmnt4?','6','?RiskAmnt6?') end) from dual";
			SQLwithBindVariables sbv=new SQLwithBindVariables();
			sbv.sql(UWGradeSql);
			sbv.put("RiskAmnt1", RiskAmnt1);
			sbv.put("RiskAmnt2", RiskAmnt2);
			sbv.put("RiskAmnt4", RiskAmnt4);
			sbv.put("RiskAmnt6", RiskAmnt6);
			tempUWGrade = riskSql.getOneValue(sbv);
			if(tUWGrade.compareTo(tempUWGrade) < 0){
				tUWGrade=tempUWGrade;
			}
		} catch (Exception ex) {
		}
		return tUWGrade;
	}
	
	/**
	 * 判断被保人的BMI是否在标准范围内
	 * @param tCountNo
	 * @param insured
	 * @param tLCContSchema
	 * @param tFlag 0-身高标准 1 体重标准 2 BMI标准
	 * @return
	 */
	private String getBMIFlag(String tCountNo,LCInsuredSchema tLCInsuredSchema,
			BOMInsured insured,
			LCContSchema tLCContSchema,String tFlag 
			)
	{
		
		/*
		 select decode(count(*),0,0,1) from BMIStandard where 1=1
and Sex = ''
and StartAge>='' and StartAgeFlag='' 
and EndAge<'' and EndAgeFlag=''
and StartStature>=''
and EndStature<=''
and StartAvoirdupois>=''
and EndAvoirdupois<=''
and StartBMI>=''
and EndBMI<=''
		 */
		try {
			String tRes = "";
			String tAgeFlag = "";
			int tAge = 0;
			String tSex = tLCInsuredSchema.getSex();
			double tStature = (insured.getStature()).doubleValue();
			double tAvoirdupois = (insured.getAvoirdupois()).doubleValue();
			double tBMI = (insured.getBMI()).doubleValue();
			String tBirthDay = tLCInsuredSchema.getBirthday();
			String tPolApplyDate = tLCContSchema.getPolApplyDate();
			tAge = PubFun.calInterval(tBirthDay,tPolApplyDate, "Y");
			if(tAge==0)
			{
				//如果为0的话,计算几个月
				tAge = PubFun.calInterval(tBirthDay,tPolApplyDate, "M");
				tAgeFlag = "M";
				//如果0个月则需判断多少天
				if(tAge==0){
					tAge = PubFun.calInterval(tBirthDay,tPolApplyDate, "D");
					//如果年龄小于28天则按0个月计算 
					if(tAge<28){
						tAge = 0;
						tAgeFlag = "M";
					} else {
						tAgeFlag = "D";
					}
				}
			}
			else
			{
				tAgeFlag = "A";
			}
			String tSQL = "select (case count(*) when 0 then 0 else 1 end) from BMIStandard where 1=1 "
						+ " and Sex = '"+"?tSex?"+"' "
						+ " and StartAge<='"+"?tAge?"+"' and StartAgeFlag='"+"?tAgeFlag?"+"' " 
						+ " and EndAge>'"+"?tAge?"+"' and EndAgeFlag='"+"?tAgeFlag?"+"' ";
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql(tSQL);

						if(tFlag.equals("0"))
						{
							tSQL = tSQL + " and StartStature<='"+"?tStature?"+"' "
										+ " and EndStature>='"+"?tStature?"+"' ";
							sqlbv=new SQLwithBindVariables();
							sqlbv.sql(tSQL);
							sqlbv.put("tStature", tStature);
						}
						else if(tFlag.equals("1"))
						{
							tSQL = tSQL + " and StartAvoirdupois<='"+"?tAvoirdupois?"+"' "
										+ " and EndAvoirdupois>='"+"?tAvoirdupois?"+"' ";
							sqlbv=new SQLwithBindVariables();
							sqlbv.sql(tSQL);
							sqlbv.put("tAvoirdupois", tAvoirdupois);
						}
						else if(tFlag.equals("2"))
						{
							tSQL = tSQL + " and StartBMI<='"+"?tBMI?"+"' "
										+ " and EndBMI>='"+"?tBMI?"+"' ";
							sqlbv=new SQLwithBindVariables();
							sqlbv.sql(tSQL);
							sqlbv.put("tBMI", tBMI);
						}
			logger.debug("BMISQL:"+tSQL);
			sqlbv.put("tSex", tSex);
			sqlbv.put("tAge", tAge);
			sqlbv.put("tAgeFlag", tAgeFlag);
			ExeSQL tExeSQL = new ExeSQL();
			String tValue = tExeSQL.getOneValue(sqlbv);
			if(tValue!=null&&!tValue.equals("")&&Integer.parseInt(tValue)>0)
			{
				tRes = "1";
			}
			else
			{
				tRes = "0";
			}
			
			return tRes;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "1";
		}
	}
	
	private String getZBFlag(double SX,double ZJ,double YW,String EM)
	{
		/*
		累计寿险风险保额大于230万的投保单
	 	累计重疾风险保额大于120万的投保单
		累计意外险风险保额大于250万的投保单
		累计寿险风险保额大于30万元且EM评点大于150点的投保单
		累计重疾风险保额大于20万元且EM评点大于125点的投保单
		 */
		String tRes = "0";
		try {
			String tSQL = "select (case (case when sum(A.x) is not null then sum(A.x) else 0 end) when 0 then 0 else 1 end) from  "
				        + "( select 1 x from dual where "
				        + "?SX?" + " >2300000 or "
				        + "?ZJ?" + " >1200000 or "
				        + "?YW?" + " >2500000 or "
				        + "("
				        + "?SX?" + " >300000  and "+"?EM?"+" >150) "
				        + " or "
				        + "("
				        + "?ZJ?" + " >200000  and "+"?EM?"+" >125 "
				        + ")"
				        + ") A";
			ExeSQL tExeSQL = new ExeSQL();
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql(tSQL);
			sqlbv.put("SX", SX);
			sqlbv.put("ZJ", ZJ);
			sqlbv.put("YW", YW);
			sqlbv.put("EM", EM);
			tRes = tExeSQL.getOneValue(sqlbv);
			logger.debug("ZB tSQL:"+tSQL + ":Value:"+tRes);
			if(tRes==null||tRes.equals(""))
			{
				tRes = "0";
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			return "1";
		}
		return tRes;
	}
	
	/**
	 * 业务员告知异常
	 * @param tContNo
	 * @return
	 */
	private String getSpecAImpart(String tContNo)
	{
		String tRes = "0";
		return "0";
//
//		try {
//			String tSQL = "select (case when sum(A.x) is not null then sum(A.x) else 0 end) from ( "
//				        + " select (case when count(*)>0 then 0 else 1 end ) x "
//				        + " from lccustomerimpart where customernotype='2' "
//				        + " and contno='"+"?tContNo?"+"' "
//				        + " and impartcode in ('A0152','D0153') "
//				        + " union "
//				        + " select ( case when count(*)>0 then 1 else 0 end ) x "
//				        + " from lccustomerimpart where customernotype='2' "
//				        + " and contno='"+"?tContNo?"+"' "
//				        + " and impartcode in ('A0153','A0154','D0154','D0155') "
//				       /* + " union "
//				        + " select ( case when count(*)>0 then 0 else 1 end ) x "
//				        + " from lccustomerimpart where customernotype='2' "
//				        + " and contno='"+tContNo+"' "
//				        + " and impartcode in ('A0156','A0157','D0151','D0157','D0158') "
//				        + " and regexp_replace(impartparammodle,'/','') is not null "
//				        */
//				        /*
//				        + " union "
//				        + " select (case when count(*)>0 then 0 else 1 end ) x "
//				        + " from lccustomerimpart where customernotype='2' "
//				        + " and contno='"+tContNo+"' "
//				        + " and impartcode in ('A0155','D0156') and "
//				        + " (regexp_replace(impartparammodle,'/','')='业务员推销' "
//				        + " or regexp_replace(impartparammodle,'/','')='同事朋友推荐' ) "
//				        */
//				        + ") A ";
//			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
//			sqlbv.sql(tSQL);
//			sqlbv.put("tContNo", tContNo);
//			ExeSQL tExeSQL = new ExeSQL();
//			tRes = tExeSQL.getOneValue(sqlbv);
//			logger.debug(":tSQL:"+tSQL);
//			if(tRes==null||tRes.equals("")||Integer.parseInt(tRes)==0)
//			{
//				return "0";
//			}
//			else
//			{
//				return "1";
//			}
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return "1";
//		}
		
	}
	/**
	 * 代理人的_告知投保人投保经过异常
	 * @param tContNo
	 * @return
	 */
	private String getProAImpart(String tContNo)
	{
		String tRes = "0";
//		try {
//			String tSQL = "select (case when sum(A.x) is not null then sum(A.x) else 0 end) from ( "
//				        + " select (case when count(*)>0 then 0 else 1 end ) x "
//				        + " from lccustomerimpart where customernotype='2' "
//				        + " and contno='"+"?tContNo?"+"' "
//				        + " and impartcode in ('A0155','D0156') and "
//				        + " (replace(impartparammodle,'/','')='业务员推销' "
//				        + " or replace(impartparammodle,'/','')='同事朋友推荐' )) A ";
//			ExeSQL tExeSQL = new ExeSQL();
//			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
//			sqlbv.sql(tSQL);
//			sqlbv.put("tContNo", tContNo);
//			tRes = tExeSQL.getOneValue(sqlbv);
//			logger.debug(":tSQL1:"+tSQL);
//			if(tRes==null||tRes.equals("")||Integer.parseInt(tRes)==0)
//			{
//				return "0";
//			}
//			else
//			{
//				return "1";
//			}
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return "1";
//		}
		return "0";
	}
	
	/**
	 * 怀孕周数
	 * 默认返回0
	 * 
	 * @param  customerno
	 * @return Double
	 * */
	private Double getPregnancyWeeks(String tCustomerno){
		Double tPregnancyWeeks = null;
		try {
			String ttPregnancyWeeksSql = "select impartparammodle from lccustomerimpart where 1=1"
						+ " and customerno='"+"?tCustomerno?"+"' and prtno='"+"?mContNo?"+"'"
						+ " and impartver in('A05','A01','D01')"
						+ " and impartcode in('A0521','A0113a','D0112a')";
			ExeSQL mExeSQl = new ExeSQL();
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql(ttPregnancyWeeksSql);
			sqlbv.put("tCustomerno", tCustomerno);
			sqlbv.put("mContNo", mContNo);
			String tPreWeeks = mExeSQl.getOneValue(sqlbv);
			if (!(tPreWeeks == null || "".equals(tPreWeeks.trim()))) {
				tPregnancyWeeks = Double.valueOf(tPreWeeks);
			}else{
				tPregnancyWeeks = Double.valueOf("0");
			}
		} catch (Exception e) {
			CError.buildErr(this, "印刷号："+mContNo+"客户："+tCustomerno+"怀孕周数数据有误！");
			e.printStackTrace();
			tPregnancyWeeks = Double.valueOf("0");
		}
		return tPregnancyWeeks;
	}
	
	/**
	 * 吸烟年数 
	 * @param tCustomerType  A-投保人  I-被保人  FreeFlag 豁免标记
	 * @return 默认返回0
	 * */
	private Double getSmokeYears(String tCustomerno,String tCustomerType,boolean FreeFlag){
		Double tSmokeYears = null;
		String impartparamno = "";
		if(tCustomerType.equals("A")||FreeFlag == true){
			impartparamno = "4";
		}else{
			impartparamno = "2";
		}
		try {
			String tSmokeYearsSql = "select impartparam from lccustomerimpartparams where"
						+ " impartver in('A05','A01','D01') and impartcode in('A0502','A0102','D0102')"
						+ " and impartparamno='"+"?impartparamno?"+"'"
						+ "and prtno='"+"?mContNo?"+"' and customerno='"+"?tCustomerno?"+"'";
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql(tSmokeYearsSql);
			sqlbv.put("impartparamno", impartparamno);
			sqlbv.put("mContNo", mContNo);
			sqlbv.put("tCustomerno", tCustomerno);
			ExeSQL mExeSQl = new ExeSQL();
			String tSmoYears = mExeSQl.getOneValue(sqlbv);
			if (!(tSmoYears == null || "".equals(tSmoYears.trim()))) {
				tSmokeYears = Double.valueOf(tSmoYears);
			}else{
				tSmokeYears = Double.valueOf("0");
			}
		} catch (NumberFormatException e) {
			CError.buildErr(this, "印刷号："+mContNo+"客户："+tCustomerno+"吸烟年数数据有误！");
			e.printStackTrace();
			tSmokeYears  = Double.valueOf("0");
		}
		return tSmokeYears;
	}
	
	/**
	 * 饮酒类型 1-啤酒  2-红酒  3-白酒  4-其他   
	 *  默认返回0
	 *  
	 *  09-09-27  目前规则引擎只支持一种饮酒类型
	 *  故注掉原逻辑 与业管陈军朝沟通后确认目前只取白酒
	 * */
	private String getDrinkType(String tCustomerNo){
		String tDrinkType = "";
//		String tDrinkTypeSql = "select case  when impartparamno='2' then 1 when impartparamno='3' then 2"
//						+ " when impartparamno='4' then 3 when impartparamno='5' then 4 else 0 end"
//						+ " from lccustomerimpartparams where impartver = 'A05' and impartcode='A0503'"
//						+ " and impartparam !='0'"
//						+ " and impartparamno in ('2','3','4','5')"
//						+ " and customerno='"+tCustomerNo+"' and prtno='"+mContNo+"' ";
//		tDrinkType = mExeSQL.getOneValue(tDrinkTypeSql);  09-09-27  目前规则引擎只支持一种饮酒类型
		//故注掉原逻辑 与业管陈军朝沟通后确认目前只取白酒
		String tDrinkTypeSql = "select count(1) from lccustomerimpartparams where impartparamno='4' and impartcode='A0503'"
						+ " and impartparam !='0' and customerno='"+"?tCustomerNo?"+"'"
						+ " and prtno='"+"?mContNo?"+"'";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(tDrinkTypeSql);
		sqlbv.put("tCustomerNo", tCustomerNo);
		sqlbv.put("mContNo", mContNo);
		tDrinkType = mExeSQL.getOneValue(sqlbv);
		if(tDrinkType==null||tDrinkType.equals("")||Integer.parseInt(tDrinkType)==0){
			tDrinkType = "0";
		}
		else {
			tDrinkType = "3";
		}
		return tDrinkType;
	}
	
	/**
	 * 计算饮酒年数
	 * 默认返回0
	 * */
	private Double getDrinkYears(String tCustomerNo){
		Double tDrinkYears = null;
		tDrinkYears = Double.valueOf("0");
//
//		try {
//			String tDrinkYearsSql = "select impartparam from lccustomerimpartparams where impartver='A05'"
//						+ " and impartcode='A0503' and impartparamno='1'"
//						+ " and customerno='"+"?tCustomerNo?"+"' and prtno='"+"?mContNo?"+"'"
//						+ " union"
//						+ " select impartparam from lccustomerimpartparams where impartver in ('A01','D01')"
//						+ " and impartcode in ('A0103','D0103') and impartparamno='3'"
//						+ " and customerno='"+"?tCustomerNo?"+"' and prtno='"+"?mContNo?"+"'";
//			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
//			sqlbv.sql(tDrinkYearsSql);
//			sqlbv.put("tCustomerNo", tCustomerNo);
//			sqlbv.put("mContNo", mContNo);
//			String tDriYears = mExeSQL.getOneValue(sqlbv);
//			tDriYears = mExeSQL.getOneValue(sqlbv);
//			if (!(tDriYears == null || "".equals(tDriYears.trim()))) {
//				tDrinkYears = Double.valueOf(tDriYears);
//			}else{
//				tDrinkYears = Double.valueOf("0");
//			}
//		} catch (NumberFormatException e) {
//			CError.buildErr(this, "印刷号："+mContNo+"客户："+tCustomerNo+"饮酒年数数据有误！");
//			e.printStackTrace();
//			tDrinkYears = Double.valueOf("0");
//		}
		return tDrinkYears;
	}
	
	/**
	 * 获得各种告知 
	 * @param  impartType  01-孕妇告知  02-危险运动爱好  03-交通事故告知
	 * 					   04-出国意向告知
	 * 
	 * @return 0-否  1-是
	 * */
	private String getISImpart(String tCustomerNo,String impartType){
		String tReturn = "0";
//		StringBuffer tReturnSql = new StringBuffer();
//		tReturnSql.append("select (case count(1) when 0 then 0 else 1 end) from lccustomerimpart where");
//		if(impartType.equals("01")){
//			//孕妇告知
//			tReturnSql.append(" impartver in ('A01','A05','D01') and impartcode in ('A0521','A0113a','D0112a')");
//		} else if(impartType.equals("02")){
//			//危险运动爱好
//			tReturnSql.append(" impartver in ('A01','A06','D01','C01') and impartcode in ('A0105','A0530','D0105','C0105')");
//		} else if(impartType.equals("03")){
//			//交通事故告知
//			tReturnSql.append(" impartver in ('A06') and impartcode in ('A0532')");
//		} else if(impartType.equals("04")){
//			//出国意向告知
//			tReturnSql.append(" impartver in ('A06','D02') and impartcode in ('A0533','D0117')");
//		}
//		tReturnSql.append("and contno='"+"?mContNo?"+"' and customerno='"+"?tCustomerNo?"+"'");
//		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
//		sqlbv.sql(tReturnSql.toString());
//		sqlbv.put("mContNo", mContNo);
//		sqlbv.put("tCustomerNo", tCustomerNo);
//		tReturn = mExeSQL.getOneValue(sqlbv);
		return tReturn;
	}
	
	/**
	 * 康顺、每日给付住院合同份数
	 * @param tRiskType: 1-康顺   2-每日住院
	 * */
	private Double getSumCont(String tCustomerNo,String tCalType){
		Double SumKSCont = null;
		SumKSCont = Double.valueOf("0");
//
//		String tRiskType = "";
//		if("1".equals(tCalType)){
//			tRiskType = "'141803','111602'";
//		} else {
//			tRiskType = "'121701','121704'";
//		}
//		try {
//			String tSumKSContSql = "select count(prtno) from lccont a where exists ("
//							+ " select 1 from lcpol b where b.appflag not in ('4','9')" 
//							+ " and b.uwflag not in ('1','2','a')" 
//							+ " and b.insuredno='"+"?tCustomerNo?"+"'" 
//							+ " and b.riskcode in ("+"?tRiskType?"+")" 
//							+ " and a.contno=b.contno)";
//			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
//			sqlbv.sql(tSumKSContSql);
//			sqlbv.put("tCustomerNo", tCustomerNo);
//			sqlbv.put("tRiskType", tRiskType);
//			String tSumKSCont = mExeSQL.getOneValue(sqlbv);
//			if (!(tSumKSCont == null || "".equals(tSumKSCont.trim()))) {
//				SumKSCont = Double.valueOf(tSumKSCont);
//			}else{
//				SumKSCont = Double.valueOf("0");
//			}
//		} catch (NumberFormatException e) {
//			e.printStackTrace();
//			SumKSCont = Double.valueOf("0");
//		}
		return SumKSCont;
	}
	
	/**
	 * 被保险人的金玉满堂系列产品份数
	 * @param tCustomerNo: 被保人客户号
	 * */
	private Double getSumJYMTCount(String tCustomerNo){
		Double SumJYMTCount = null;
		try {
			String tSumJYMTCountSql = "select sum(mult) from lcpol a where appflag not in ('4','9') "
						            + " and uwflag not in ('1','2','a') "
						            + " and riskcode in ('312201','312202','312203','312204','312206') "
						            + " and insuredno='"+"?tCustomerNo?"+"' " ;
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql(tSumJYMTCountSql);
			sqlbv.put("tCustomerNo", tCustomerNo);
			String tSumJYMTCount = mExeSQL.getOneValue(sqlbv);
			if (!(tSumJYMTCount == null || "".equals(tSumJYMTCount.trim()))) {
				SumJYMTCount = Double.valueOf(tSumJYMTCount);
			}else{
				SumJYMTCount = Double.valueOf("0");
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			SumJYMTCount = Double.valueOf("0");
		}
		return SumJYMTCount;
	}
	
	/**
	 * 被保险人的富贵盈门系列产品保费
	 * @param tCustomerNo: 被保人客户号
	 * */
	private Double getSumFGYMPrem(String tCustomerNo){
		Double SumFGYMPrem = null;
		try {
//			String tSumFGYMPremSql = "select sum(Prem) from lcpol a where  appflag not in ('4','9') "
//						            + " and uwflag not in ('1','2','a') "
//						            + " and riskcode in ('314301','314302') "
//						            + " and insuredno='"+tCustomerNo+"' " ;
//			String tSumFGYMPrem = mExeSQL.getOneValue(tSumFGYMPremSql);
//			if (!(tSumFGYMPrem == null || "".equals(tSumFGYMPrem.trim()))) {
//				SumFGYMPrem = Double.valueOf(tSumFGYMPrem);
//			}else{
//				SumFGYMPrem = Double.valueOf("0");
//			}
			//有多币种险种的情况
			String currString = "select * from lcpol where appflag not in ('4','9') "
						            + " and uwflag not in ('1','2','a') "
						            + " and riskcode in ('314301','314302') "
						            + " and insuredno='"+"?tCustomerNo?"+"' ";
			LDExch tLDExch = new LDExch();
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql(currString);
			sqlbv.put("tCustomerNo", tCustomerNo);
			LCPolDB currLCPolDB = new LCPolDB();
			LCPolSet currLCPolSet = new LCPolSet();
			currLCPolSet = currLCPolDB.executeQuery(sqlbv);
			double totalCurr = 0.00;
			for (int j = 1; j <= currLCPolSet.size(); j++) {
				totalCurr += tLDExch.toBaseCur(currLCPolSet.get(j).getCurrency(), locCurrency, theCurrentDate,currLCPolSet.get(j).getAmnt());
			}
			SumFGYMPrem = totalCurr;
			
		} catch (NumberFormatException e) {
			e.printStackTrace();
			SumFGYMPrem = Double.valueOf("0");
		}
		return SumFGYMPrem;
	}
	
	/**
	 * 既往告知事项不全为否
	 * */
	private String getOImpart(String tCustomerNo){
		String OImpart = "";
		String tOImpartSql = "select count(*) from lccustomerimpart where impartcode in"
						+ "('A0105','A0110','A0111e','A0111j','A0106','A0111a','A0111f',"
						+ "'A0112','A0107','A0111b','A0111g','A0108','A0111c','A0111h','A0109','A0111d',"
						+ "'A0111i','D0105','D0110a','D0110e','D0110i','D0106','D0110b','D0110f','D0110j',"
						+ "'D0107','D0110c','D0110g','D0111','D0108','D0110d','D0110h','D0122','D0109',"
						+ "'C0101','C0102','C0103','C0104','C0105','C0106','B0101','B0102','B0103','B0104',"
						+ "'A0113a','A0113b','D0112a','D0112b','A0114b','D0121','A0115a','A0115b','D0113a',"
						+ "'D0113b','A0117','A0118','D0115','D0116','D0117','C0108','A0512','A0513','A0510',"
						+"'A0511','A0516','A0517','A0514','A0515','A0507','A0506','A0509','A0508','A0505',"
						+"'A0504','A0524','A0526','A0525','A0522','A0519','A0518','A0520','A0532','A0529',"
						+"'A0530','A0528','B0504','B0505','B0503','B0501','B0502','B0506','B0508','B0507') "
						+"and customerno='"+"?tCustomerNo?"+"' and contno <> '"+"?mContNo?"+"'";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(tOImpartSql);
		sqlbv.put("tCustomerNo", tCustomerNo);
		sqlbv.put("mContNo", mContNo);
		String tOImpart = mExeSQL.getOneValue(sqlbv);
		if(tOImpart!=null&&!tOImpart.equals("")&&Integer.parseInt(tOImpart)>0){
			OImpart = "1";
		}else{
			OImpart = "0";
		}
		return OImpart;
	}
	
	/**
	 * 查询被保人年收入
	 * */
	private Double getYearIncome(String tCustomerNo){
		Double YearIncome = null;
		YearIncome = Double.valueOf("0");
//
//		try {
//			//09-10-10 与陈军朝确定后被保人年收入逻辑改为如果投被关系为本人则取年收入数值最大的
//			//同时为了对豁免险的支持，查询的sql改为查0534的impartparamno in ('1','3')并且取最大值
//			String tYearIncomeSql = "select (case when max(A.x) is not null then max(A.x) else 0 end) from (select (case when max(ImpartParam*1) is not null then max(ImpartParam*1) else 0 end) x"
//						+ " from lccustomerimpartparams"
//						+ " where impartcode in ('A0120', 'D0119')"
//						+ " and impartver in ('A02', 'D02')"
//						+ " and impartparamno in ('1','3')"
//						+ " and contno = '"+"?mContNo?"+"'"
//						+ " and customerno = '"+"?tCustomerNo?"+"'"
//						+ " union"
//						+ " select (case when max(impartparam * 10000) is not null then max(impartparam * 10000) else 0 end) x"
//						+ " from lccustomerimpartparams"
//						+ " where impartcode = 'A0534'"
//						+ " and impartparamno in ('1','3')"
//						+ " and contno = '"+"?mContNo?"+"'"
//						+ " and customerno = '"+"?tCustomerNo?"+"') A";
//			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
//			sqlbv.sql(tYearIncomeSql);
//			sqlbv.put("mContNo", mContNo);
//			sqlbv.put("tCustomerNo", tCustomerNo);
//			String tYearIncome = mExeSQL.getOneValue(sqlbv);
//			if (!(tYearIncome == null || "".equals(tYearIncome.trim()))) {
//				YearIncome = Double.valueOf(tYearIncome);
//			}else{
//				YearIncome = Double.valueOf("0");
//			}
//		} catch (NumberFormatException e) {
//			CError.buildErr(this, "印刷号："+mContNo+"客户："+tCustomerNo+"年收入数据有误！");
//			e.printStackTrace();
//			YearIncome = Double.valueOf("0");
//		}
		return YearIncome;
	}
	
	/**
	 * 交通事故记录
	 * */
	private String getTrafficAccident(String tCustomerNo){
		String TrafficAccident = "";
		String tTrafficAccidentSql = "select (case count(1) when 0 then 0 else 1 end) from lccustomerimpart"
						+ " where impartcode in ('A0104','D0104','A0532')"
						+ " and contno='"+"?mContNo?"+"' and customerno='"+"?tCustomerNo?"+"'";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(tTrafficAccidentSql);
		sqlbv.put("mContNo", mContNo);
		sqlbv.put("tCustomerNo", tCustomerNo);
		TrafficAccident = mExeSQL.getOneValue(sqlbv);
//		if(TrafficAccident==null||"".equals(TrafficAccident)){
//			TrafficAccident = "0";
//		}
		return TrafficAccident;
	}
	
//	/**
//	 * 本单财务风险保额
//	 * */
//	private Double getFinSumAmnt(tCustomerNo){
//		
//	}
	
	
	
	/**
	 * 补充告知问卷标记
	 * */
	private String getReinImpart(){
		String ReinImpart = "0";
//		String tReinImpartSql = 
//			//09-09-27 与陈军朝确认后此处不取核保问卷   只取扫描件
////			"select decode(count(1),0,0,1) from LCQuestionnaire"
////						+ " where AskContentNo='026' and proposalcontno =("
////						+ " select proposalcontno from lccont where contno='"+mContNo+"'"
////						+ " )";
//						"select (case count(1) when 0 then 0 else 1 end) from es_doc_main"
//						+ " where subtype='UR212' and doccode ='"+"?mContNo?"+"' ";
//		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
//		sqlbv.sql(tReinImpartSql);
//		sqlbv.put("mContNo", mContNo);
//		ReinImpart = mExeSQL.getOneValue(sqlbv);
		return ReinImpart;
	}
	
	/**
	 * 备注栏的字数
	 * */
	private Double getRemarkCount(){

		Double RemarkCount = null;
		RemarkCount = Double.valueOf("0");
//
//		try {
//			String tRemarkCountSql = "select (case when char_length(remark) is not null then char_length(remark) else 0 end) from lccont"
//							+" where contno='"+"?mContNo?"+"'";
//			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
//			sqlbv.sql(tRemarkCountSql);
//			sqlbv.put("mContNo", mContNo);
//			String tRemarkCount = mExeSQL.getOneValue(sqlbv);
//			if (!(tRemarkCount == null || "".equals(tRemarkCount.trim()))) {
//				RemarkCount = Double.valueOf(tRemarkCount);
//			}else{
//				RemarkCount = Double.valueOf("0");
//			}
//		} catch (NumberFormatException e) {
//			CError.buildErr(this, "印刷号："+mContNo+"备注栏字数取数异常！");
//			e.printStackTrace();
//			RemarkCount = Double.valueOf("0");
//		}
		return RemarkCount;
	}
	
	/**
	 * 是否有陪检记录
	 * */
	private String getAccoBodyCheck(){
		String AccoBodyCheck = "0";
//		String tAccoBodyCheckSql = "";
//		if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
//		tAccoBodyCheckSql = "select (case count(1) when 0 then 0 else 1 end) from lcpenotice"
//						+" where agentname is not null and  rownum=1 and contno='"+"?mContNo?"+"'"
//						+" order by makedate";
//		}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
//			tAccoBodyCheckSql = "select (case count(1) when 0 then 0 else 1 end) from lcpenotice"
//					+" where agentname is not null and contno='"+"?mContNo?"+"'"
//					+" order by makedate limit 0,1";	
//		}
//		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
//		sqlbv.sql(tAccoBodyCheckSql);
//		sqlbv.put("mContNo", mContNo);
//		AccoBodyCheck = mExeSQL.getOneValue(sqlbv);
		return AccoBodyCheck;
	}
	
	/**
	 * 体检医院是否是定点医院
	 * */
	private String getIsAppointHos(){
		String IsAppointHos = "0";
		String tIsAppointHosSql = "";
//		if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
//		tIsAppointHosSql = "select (case count(1) when 0 then 0 else 1 end) from ldhospital where hosstate='0'"
//						+ " and hospitcode = ( select A.x from" 
//						+ " ("
//						+ " select hospitcode x from lcpenotice a where a.contno='"+"?mContNo?"+"'"
//						+ " order by a.makedate desc"
//						+ " )A where rownum=1"
//						+ " )";
//		}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
//			tIsAppointHosSql = "select (case count(1) when 0 then 0 else 1 end) from ldhospital where hosstate='0'"
//					+ " and hospitcode = ( select A.x from" 
//					+ " ("
//					+ " select hospitcode x from lcpenotice a where a.contno='"+"?mContNo?"+"'"
//					+ " order by a.makedate desc"
//					+ " )A limit 0,1"
//					+ " )";	
//		}
//		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
//		sqlbv.sql(tIsAppointHosSql);
//		sqlbv.put("mContNo", mContNo);
//		IsAppointHos = mExeSQL.getOneValue(sqlbv);
		return IsAppointHos;
	}
	
	/**
	 * 系统抽检标记
	 * */
	private String getSpotCheckFlag(){
		String SpotCheckFlag = "0";
//		String tSpotCheckFlagSql = "select (case count(1) when 0 then 0 else 1 end) from bpomissionstate"
//						+" where bussno='"+"?mContNo?"+"' and dealtype='01'";
//		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
//		sqlbv.sql(tSpotCheckFlagSql);
//		sqlbv.put("mContNo", mContNo);
//		SpotCheckFlag = mExeSQL.getOneValue(sqlbv);
		return SpotCheckFlag;
	}
	
	/**
	 * 生调回复人员是否与系统定义不一致
	 * */
	private String getMOpeIsNotDefined(){
		String MOpeIsNotDefined = "0";
//		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
//		if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
//			String tMOpeIsNotDefinedSql = " select 1 from dual where"
//				+ " nvl((select username from lduser where usercode =("
//				+ " select A.x from ("
//				+ " select replyoperator x from lcrreport where contno='"+"?mContNo?"+"'"
//				+ "  order by makedate desc) A"
//				+ " where rownum=1"
//				+ " )),'xx')=nvl(("
//				+ " select A.x from ("
//				+ " select remark x from lcrreport where contno='"+"?mContNo?"+"'"
//				+ " order by makedate desc) A where rownum=1),'xx')";
//			sqlbv.sql(tMOpeIsNotDefinedSql);
//			sqlbv.put("mContNo", mContNo);
//		}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
//			String tMOpeIsNotDefinedSql = "select 1 "
//					+"  from dual "
//					+" where (case "
//					+"         when (select username "
//					+"                 from lduser "
//					+"                where usercode = "
//					+"                      (select A.x "
//					+"                         from (select replyoperator x "
//					+"                                 from lcrreport "
//					+"                                where contno = '?mContNo?' "
//					+"                                order by makedate desc) A limit 1)) is null then "
//					+"          'xx' "
//					+"         else "
//					+"          (select username "
//					+"             from lduser "
//					+"            where usercode = "
//					+"                  (select A.x "
//					+"                     from (select replyoperator x "
//					+"                             from lcrreport "
//					+"                            where contno = '?mContNo?' "
//					+"                            order by makedate desc) A limit 1)) "
//					+"       end) = (case "
//					+"         when (select A.x "
//					+"                 from (select remark x "
//					+"                         from lcrreport "
//					+"                        where contno = '?mContNo?' "
//					+"                        order by makedate desc) A limit 1) is null then "
//					+"          'xx' "
//					+"         else "
//					+"          (select A.x "
//					+"             from (select remark x "
//					+"                     from lcrreport "
//					+"                    where contno = '?mContNo?' "
//					+"                    order by makedate desc) A limit 1) "
//					+"       end) ";
//				sqlbv.sql(tMOpeIsNotDefinedSql);
//				sqlbv.put("mContNo", mContNo);
//		}
//		String tMOpeIsNotDefined = mExeSQL.getOneValue(sqlbv);
//		if(!"1".equals(tMOpeIsNotDefined)){
//			MOpeIsNotDefined = "1";
//		} else {
//			MOpeIsNotDefined = "0";
//		}
		return MOpeIsNotDefined;
	}
	
	/**
	 * 累计该险种保费
	 * @param   riskcode   insurdno
	 * */
	private Double getSumThisPrem(String tRiskCode,String tInsuredNo){
		Double SumThisPrem = null;
		try {
//			String tSumThisPremSql = "select sum(prem) from lcpol where"
//							+ " uwflag not in ('1','2','a') and appflag not in ('4','9')"
//							+ " and insuredno='"+tInsuredNo+"'"
//							+ " and riskcode='"+tRiskCode+"'";
//			String tSumThisPrem = mExeSQL.getOneValue(tSumThisPremSql);
//			if (!(tSumThisPrem == null || "".equals(tSumThisPrem.trim()))) {
//				SumThisPrem = Double.valueOf(tSumThisPrem);
//			}else{
//				SumThisPrem = Double.valueOf("0");
//			}
			//有多币种险种的情况
			String currString = "select * from lcpol where"
							+ " uwflag not in ('1','2','a') and appflag not in ('4','9')"
							+ " and insuredno='"+"?tInsuredNo?"+"'"
							+ " and riskcode='"+"?tRiskCode?"+"'";
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql(currString);
			sqlbv.put("tInsuredNo", tInsuredNo);
			sqlbv.put("tRiskCode", tRiskCode);
			LDExch tLDExch = new LDExch();
			LCPolDB currLCPolDB = new LCPolDB();
			LCPolSet currLCPolSet = new LCPolSet();
			currLCPolSet = currLCPolDB.executeQuery(sqlbv);
			double totalCurr = 0.00;
			for (int j = 1; j <= currLCPolSet.size(); j++) {
				totalCurr += tLDExch.toBaseCur(currLCPolSet.get(j).getCurrency(), locCurrency, theCurrentDate,currLCPolSet.get(j).getPrem());
			}
			SumThisPrem = totalCurr ; 
			
		} catch (NumberFormatException e) {
			CError.buildErr(this, "印刷号："+mContNo+"客户："+tInsuredNo+"累计该险种保费数据有误！");
			e.printStackTrace();
			SumThisPrem = Double.valueOf("0");
		}
		return SumThisPrem;
	}
	
	/**
	 * 残疾事项告知
	 * */
	private String getDisabilityImpart(String tCustomerNo){
		String DisabilityImpart = "0";
//		String tDisabilityImpartSql = "select (case count(1) when 0 then 0 else 1 end) from lccustomerimpart"
//						+ " where impartcode='A0508' and contno='"+"?mContNo?"+"' and customerno='"+"?tCustomerNo?"+"'";
//		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
//		sqlbv.sql(tDisabilityImpartSql);
//		sqlbv.put("mContNo", mContNo);
//		sqlbv.put("tCustomerNo", tCustomerNo);
//		DisabilityImpart = mExeSQL.getOneValue(sqlbv);
		return DisabilityImpart;
	}
	
	/**
	 * 取保单管理机构的前*位
	 * 
	 * 
	 * 如果个人保单的管理机构为6位时默认在后面补“4”(反正系统里面不会有以两个4结尾的机构，
	 * 所以就用4补齐了-_-||)
	 * 
	 * */
	private String getManagetCom(String tManageCom,int tLength){
		int strLen = tManageCom.length();

		StringBuffer strReturn = new StringBuffer();
		if (strLen > tLength) {
			strReturn.append(tManageCom.substring(0, tLength));
		} else {
			if (strLen == 0) {
				strReturn.append("");
			} else {
				strReturn.append(tManageCom);
			}

			for (int i = strLen; i < tLength; i++) {
				strReturn.append("4");
			}
		}
		return strReturn.toString();
	}
	
	/**
	 * 获得一个0~100的随机数
	 * */
	private Double getSamplingFactor(){
		Double SamplingFactor;
		Random rand = new Random();
		int tSelect = rand.nextInt(100)+1;//修改抽检因子为1至100的数字
		SamplingFactor = Double.valueOf(String.valueOf(tSelect));
		return SamplingFactor;
	}
	
	/**
	 * 保单的机构差异化等级
	 * */
	private String getContUWLevel(String tComCode){
		String tUWLevel = "1";
//		String getSql = "select case othersign when 'A' then 1 when 'B' then 2 when 'C' then 3 when 'D' then 4 end"
//						+" from ldcode where codetype='station' and code='"+"?tComCode?"+"'";
//		ExeSQL tExeSQL = new ExeSQL();
//		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
//		sqlbv.sql(getSql);
//		sqlbv.put("tComCode", tComCode);
//		tUWLevel = tExeSQL.getOneValue(sqlbv);
		return tUWLevel;
	}
	/**
	 * 获得个单进入自核的日期
	 */
	private String getUWDate(String tPrtNo){
		String tUWDate = "";
//		String getSql = "select makedate||' '||maketime  from lwmission where processid = '0000000003' and activityid = '0000001003' and missionprop1 = '"+tPrtNo+"'";
		String getSql = "select concat(concat(makedate,' '),maketime) from lwmission where activityid in (select activityid from lwactivity  where functionid ='10010005') and missionprop1 = '"+"?tPrtNo?"+"'";
        SQLwithBindVariables sqlbv=new SQLwithBindVariables();
        sqlbv.sql(getSql);
        sqlbv.put("tPrtNo", tPrtNo);
		ExeSQL tExeSQL = new ExeSQL();
		tUWDate = tExeSQL.getOneValue(sqlbv);
		return tUWDate;
	}
	/**
	 * 业务员差异化等级
	 * */
	private String getAgentUWLevel(String tAgentCode){
		String tUWLevel = "";
		String getSql = "select uwlevel from latree where agentcode='"+"?tAgentCode?"+"'";
		ExeSQL tExeSQL = new ExeSQL();
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(getSql);
		sqlbv.put("tAgentCode", tAgentCode);
		tUWLevel = tExeSQL.getOneValue(sqlbv);
		return tUWLevel;
	}
	
	/**
	 * 代理人与被保人关系
	 * */
	private String getRelToInsured(){
		String tRelToInsured = "6";
//		String tRelToInsuredSql = "select (case (select code from ldcode where codetype = 'relagenttoins' and codename = impartparammodle) when"
//						+ " '' then '5'else (select code from ldcode where codetype = 'relagenttoins' and codename = impartparammodle) end)" 
//						+ " from lccustomerimpart where impartver = 'A03' and impartcode='A0151'"
//						+ " and prtno='"+"?mContNo?"+"'";
//		ExeSQL tExeSQL = new ExeSQL();
//		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
//		sqlbv.sql(tRelToInsuredSql);
//		sqlbv.put("mContNo", mContNo);
//		tRelToInsured = tExeSQL.getOneValue(sqlbv);
//		if("".equals(StrTool.cTrim(tRelToInsured))){
//			//如果查询结果为空则认为是 6-否
//			tRelToInsured = "6";
//		}
		return tRelToInsured;
	}
	
	/**
	 * 投(被)保人联系电话与业务员联系电话是否一致
	 * 2010-3-26与陈军朝电话沟通 业务员电话取值为最新增加的告知项“业务员电话”
	 * @return 0-否  1-是
	 * */
	private String getSamePhone(String tCustomerno,String tAddressno){
		String tSamePhone = "0";
		String tPhone = "";
		String tMobile = "";
		String tAgentPhoneSql = "select impartparammodle from lccustomerimpart"
						+ " where impartver='A03' and impartcode='A0158' "
						+ " and prtno='"+"?mContNo?"+"'";
		ExeSQL tExeSQL = new ExeSQL();
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(tAgentPhoneSql);
		sqlbv.put("mContNo", mContNo);
		tPhone = tExeSQL.getOneValue(sqlbv);
		String tSamePhoneSql = "";
		SQLwithBindVariables sbv=new SQLwithBindVariables();
		if(tPhone!=null&&!"".equals(tPhone)){
			if(!"".equals(tPhone.replaceAll("[^0-9]", ""))){
				tSamePhoneSql = " select 1 from lcaddress a where customerno='"
							+ "?tCustomerno?"+ "'"
							+ " and addressno='"+"?tAddressno?"+"' and ("
							+ " a.phone='"+"?phone?"+"' or a.mobile='"+"?phone?"+"')";
				sbv.sql(tSamePhoneSql);
				sbv.put("tCustomerno", tCustomerno);
				sbv.put("tAddressno", tAddressno);
				sbv.put("phone", tPhone.replaceAll("[^0-9]", ""));
				tSamePhone = tExeSQL.getOneValue(sbv);
				if("1".equals(tSamePhone)){
					return "1";
				}
			}
		} else {
			tSamePhone = "0";
		}
		return tSamePhone;
	}
	
	/**
	 * 一年内职业代码是否一致
	 * @return 0-否  1-是
	 * */
	private String getSameOccuCode(String tInsuredNo){
		String tSameOccuCode = "0";
		ExeSQL tExeSQL = new ExeSQL();
		String tCurrentDate = PubFun.getCurrentDate();
		String tOneYearBefore = PubFun.calDate(tCurrentDate, -1, "Y", "");
		String tSql = "select distinct occupationcode from lcinsured where"
						+ " insuredno='"+"?tInsuredNo?"+"' "
						+ " and occupationcode is not null"
						+ " and (makedate>='"+"?tOneYearBefore?"+"' "
						+ " and makedate<='"+"?tCurrentDate?"+"')";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(tSql);
		sqlbv.put("tInsuredNo", tInsuredNo);
		sqlbv.put("tOneYearBefore", tOneYearBefore);
		sqlbv.put("tCurrentDate", tCurrentDate);
		SSRS tSSRS = new SSRS();
		tSSRS = tExeSQL.execSQL(sqlbv);
		if(tSSRS.getMaxRow()>1){
			tSameOccuCode = "0";
		} else {
			tSameOccuCode = "1";
		}
		return tSameOccuCode;
	}
	
	/**
	 * 管理机构百团标记 
	 * 
	 * @return 0-否 1-是
	 * */
	private String getManaSpecFlag(String tManageCom){
		return "0";
//		String tSql = "select (case when comareatype1 is not null then comareatype1 else '0' end) from ldcom where "
//						+ " comcode = '"+"?tManageCom?"+"'";
//		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
//		sqlbv.sql(tSql);
//		sqlbv.put("tManageCom", tManageCom);
//		ExeSQL tExeSQL = new ExeSQL();
//		return tExeSQL.getOneValue(sqlbv);
	}
	

	public static void main(String[] agrs) {String tNeedPEN_SQL = "";
//		ExeSQL tExeSQL_NeedPEN = new ExeSQL();
//		String	strSQL =  " select a.insuredno,a.contno from lcinsured a "
//	                + " where insuredno='"+"0000015724"+"' "
//	                + " and contno='"+"20160509000004"+"' ";
//			
//			SSRS rs = tExeSQL_NeedPEN.execSQL(strSQL);
//			if(rs.getMaxRow()>0)
//			{
//				String insuredno = rs.GetText(1, 1);
//				String contno = rs.GetText(1, 2);
//				String exeProcedure  = "{ CALL HEALTHYAMNT('?#@jkl#?' , '"+insuredno+"' , '"+contno+"' , '0') }";
//				ExeSQL tExeSQL_NeedPEN1 = new ExeSQL();
//				tExeSQL_NeedPEN1.getOneValue(exeProcedure);
//				
//			}
		
		
		
	
	
	}
	
	

}
