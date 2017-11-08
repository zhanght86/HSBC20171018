package com.sinosoft.lis.bq;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.sinosoft.lis.bl.LPAddressBL;
import com.sinosoft.lis.bl.LPAppntBL;
import com.sinosoft.lis.bl.LPContBL;
import com.sinosoft.lis.bl.LPInsuredBL;
import com.sinosoft.lis.bl.LPPolBL;
import com.sinosoft.lis.db.LCAppntDB;
import com.sinosoft.lis.db.LCBnfDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCInsuredDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LDPersonDB;
import com.sinosoft.lis.db.LMRiskAppDB;
import com.sinosoft.lis.db.LMRiskRoleDB;
import com.sinosoft.lis.db.LPAppntDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.db.LPPersonDB;
import com.sinosoft.lis.pubfun.ContHangUpBL;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCAppntSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCInsuredSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LDPersonSchema;
import com.sinosoft.lis.schema.LPAddressSchema;
import com.sinosoft.lis.schema.LPAppntSchema;
import com.sinosoft.lis.schema.LPBnfSchema;
import com.sinosoft.lis.schema.LPContSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPInsuredSchema;
import com.sinosoft.lis.schema.LPPersonSchema;
import com.sinosoft.lis.schema.LPPolSchema;
import com.sinosoft.lis.tb.ContBL;
import com.sinosoft.lis.vdb.LDPersonDBSet;
import com.sinosoft.lis.vdb.LPPersonDBSet;
import com.sinosoft.lis.vschema.LCBnfSet;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LDPersonSet;
import com.sinosoft.lis.vschema.LMRiskRoleSet;
import com.sinosoft.lis.vschema.LPAppntSet;
import com.sinosoft.lis.vschema.LPBnfSet;
import com.sinosoft.lis.vschema.LPContSet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.lis.vschema.LPInsuredSet;
import com.sinosoft.lis.vschema.LPPersonSet;
import com.sinosoft.lis.vschema.LPPolSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 个单/团单下个单客户资料变更项目明细
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author Pengqian
 * @ReWrite ZhangRong
 * @ReWrite pst  on 2008-12-07
 * @version 1.0,2.0
 
 */
public class PEdorCMDetailBL implements EdorDetail {
private static Logger logger = Logger.getLogger(PEdorCMDetailBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	/** 往界面传输数据的容器 */
	private MMap mMap = new MMap();
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperate;
	
	//是否需要合并客户号标记
	private String mMergeFlag = ""; //0-不需要,1-需要
	private String new_CustomerNo = "";//新客户号
	private LDPersonSchema xLDPersonSchema = new LDPersonSchema();  //将要合并的新客户信息
	
	/**投被保人标记 AppntIsInsuredFlag 投保人还是被保人0,投保人，1，被保人，2 两者兼之,但是注意，只针对某一保单*/
	private String tAppntIsInsuredFlag="";

	/**  */
	private LPContSet mLPContSet = new LPContSet();
	private LCContSet mLCContSet = new LCContSet();
	private LPAppntSet mLPAppntSet = new LPAppntSet();
	private LPBnfSet mLPBnfSet = new LPBnfSet();
	private LPInsuredSet mLPInsuredSet = new LPInsuredSet();
	private LPPolSet mLPPolSet = new LPPolSet();
	private LPPersonSchema mLPPersonSchema = new LPPersonSchema();
	//原本的客户信息
	private LDPersonSchema mLDPersonSchema = new LDPersonSchema();
	private LPPersonSchema tLPPersonSchema = new LPPersonSchema();
	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();
		

	/**保全受理号*/
	private String mEdorAcceptNo = "";
	/**客户号*/
	private String mCustomerNo = "";
    
	/**入口保单号*/
	private String mTempContNo = "";
	/**字段映射类*/
	private Reflections tRef = new Reflections();
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private TransferData mTransferData = new TransferData();
	
	// 获得此时的日期和时间
	private String strCurrentDate = PubFun.getCurrentDate();
	private String strCurrentTime = PubFun.getCurrentTime();
	
	private ExeSQL tExeSQL = new ExeSQL();

	public PEdorCMDetailBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public void setOperate(String cOperate) {
		mOperate = cOperate;
	}

	public String getOperate() {
		return mOperate;
	}

	public CErrors getErrors() {
		return mErrors;
	}

	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		this.setOperate(cOperate);

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData()) {
			return false;
		}

		// 数据校验操作（checkdata)
		if (!checkData()) {
			return false;
		}
		
		//由于可能有保单a不需要进行IC操作，其保单信息需要在CM保全确认时修改保单信息，此时如果合并客户号的话
		//可能导致该a保单客户号修改，但是其他需要进行IC操作的保单并没有修改，如果中间再进行一次CM的话，原来的老客户号将
		//不能关联到保单a，未避免此风险，暂时不进行客户号合并操作
		if (!checkMerge()) {
			return false;
		}

		// 数据操作业务处理(新增insertData();修改updateData();删除deletedata())
		if (!dealData()) {
			return false;
		}

		if (!prepareOutputData()) {
			return false;
		}

		PubSubmit tSubmit = new PubSubmit();
		if (!tSubmit.submitData(mResult, "")) {
			// @@错误处理
			this.mErrors.copyAllErrors(tSubmit.mErrors);
			CError tError = new CError();
			tError.moduleName = "PEdorCMDetailBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	public VData getResult() {
		return mResult;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData() {
		 mLPEdorItemSchema = (LPEdorItemSchema) mInputData
				.getObjectByObjectName("LPEdorItemSchema", 0);
		 tLPPersonSchema = (LPPersonSchema) mInputData.getObjectByObjectName(
				"LPPersonSchema", 0);
		mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0);
		if (tLPPersonSchema == null || mLPEdorItemSchema == null
				|| mGlobalInput == null) {
			mErrors.addOneError(new CError("数据传输不完全！"));
			return false;
		}
		mTempContNo=mLPEdorItemSchema.getContNo();
		// 获得mLPEdorItemSchema的其它信息
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPEdorItemDB.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPEdorItemDB.setContNo(mLPEdorItemSchema.getContNo());
		tLPEdorItemDB.setInsuredNo(mLPEdorItemSchema.getInsuredNo());
		mLPEdorItemSchema.setSchema(tLPEdorItemDB.query().get(1));
		mEdorAcceptNo = mLPEdorItemSchema.getEdorAcceptNo();
		mCustomerNo = tLPPersonSchema.getCustomerNo();
		return true;
	}

	/**
	 * 校验传入的数据的合法性 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkData() {
		LCContDB tLCContDB = new LCContDB();

		String tConSQL="select * from lccont where grpcontno='00000000000000000000' and (InsuredNo='?mCustomerNo?' or appntno='?mCustomerNo?') and appflag='1'";		
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(tConSQL);
		sqlbv.put("mCustomerNo", mCustomerNo);
		mLCContSet = tLCContDB.executeQuery(sqlbv);
		if (mLCContSet == null || mLCContSet.size() <= 0) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorCMDetailBL";
			tError.functionName = "checkData";
			tError.errorMessage = "无本客户相关保单数据!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}
	/**
	 * 校验是否需要合并客户号
	 */
	private boolean checkMerge() 
	{
		//校验是否需要合并客户号
		mMergeFlag="0"; //默认为不需要合并
		//如果传入的新证件类型是身份证，根据传入的新身份证号a得到其另一长度的身份证号b(当前15位，取出其18位号；当前18位，取出其15位号)
		//如果系统中存在已有客户信息，其他条件都一致，此时只要身份证号是a,b中一个，则需要合并客户号
		String otheridno = "";
		if(tLPPersonSchema.getIDType().equals("0"))
		{
			ContBL tContBL = new ContBL();
			if(tLPPersonSchema.getIDNo().length()==15)
			{
				otheridno = tContBL.get18IDNo(tLPPersonSchema.getIDNo(), tLPPersonSchema.getBirthday());
			}
			else
			{
				otheridno = tContBL.get15IDNo(tLPPersonSchema.getIDNo());
			}
		}
		
		String query_sql="select * from ldperson a where a.name='?name?' and a.sex='?sex?'  and a.idtype='?idtype?' and a.idno in ('?arr?') and a.birthday='?birthday?' ";
		logger.debug("query_sql="+query_sql);
		ArrayList<String> strArr=new ArrayList<String>();
		strArr.add(otheridno);
		strArr.add(tLPPersonSchema.getIDNo());
		SQLwithBindVariables sbv=new SQLwithBindVariables();
		sbv.sql(query_sql);
		sbv.put("name", tLPPersonSchema.getName());
		sbv.put("sex", tLPPersonSchema.getSex());
		sbv.put("idtype", tLPPersonSchema.getIDType());
		sbv.put("birthday", tLPPersonSchema.getBirthday());
		sbv.put("arr", strArr);
		LDPersonDB xLDPersonDB = new LDPersonDB();
		LDPersonSet xLDPersonSet = new LDPersonSet();
		xLDPersonSet=xLDPersonDB.executeQuery(sbv);
		if(xLDPersonSet.size()>0)
		{
			mMergeFlag="1";
			xLDPersonSchema =xLDPersonSet.get(1);
			new_CustomerNo = xLDPersonSchema.getCustomerNo();
		}
		else
		{
			new_CustomerNo = this.mCustomerNo;
		}
		return true;
	}

	/**
	 * 准备需要保存的数据
	 */
	private boolean dealData() {
		

		   // 准备个人保单（保全）的信息

			LDPersonDB tLDPersonDB = new LDPersonDB();
			LDPersonSet tLDPersonSet = new LDPersonDBSet();
			tLDPersonDB.setCustomerNo(mCustomerNo);
			tLDPersonSet = tLDPersonDB.query();
			if (tLDPersonSet.size() < 1) {
				// 产生新的客户号
				CError.buildErr(this, "查询客户号为" + mCustomerNo
						+ "的保单信息时失败!");
				return false;
			} else {
				mLDPersonSchema=tLDPersonSet.get(1);
				tRef.transFields(mLPPersonSchema, tLDPersonSet.get(1));
				mLPPersonSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
				mLPPersonSchema.setEdorType("CM");
				mLPPersonSchema.setSex(tLPPersonSchema.getSex());
				mLPPersonSchema.setBirthday(tLPPersonSchema.getBirthday());
				mLPPersonSchema.setName(tLPPersonSchema.getName());
				mLPPersonSchema.setLastName(tLPPersonSchema.getLastName());
				mLPPersonSchema.setFirstName(tLPPersonSchema.getFirstName());
				mLPPersonSchema.setIDType(tLPPersonSchema.getIDType());
				mLPPersonSchema.setIDNo(tLPPersonSchema.getIDNo());
				/*由于可能有保单a不需要进行IC操作，其保单信息需要在CM保全确认时修改保单信息，此时如果合并客户号的话
				//可能导致该a保单客户号修改，但是其他需要进行IC操作的保单并没有修改，如果中间再进行一次CM的话，原来的老客户号将
				//不能关联到保单a，未避免此风险，暂时不进行客户号合并操作*/
				//add by xiongzh 2010-4-20 经讨论确定CM做完之后必须做完IC之后才能重新发起CM，因此上述问题不复存在，加上客户号合并处理
				//lpperson.customerno不能修改，否则生成打印批单时无法查询变更后客户信息
			   // mLPPersonSchema.setCustomerNo(new_CustomerNo);
				
				mLPPersonSchema.setOperator(this.mGlobalInput.Operator);
				mLPPersonSchema.setModifyDate(strCurrentDate);
				mLPPersonSchema.setModifyTime(strCurrentTime);				
			}


		for (int i = 1; i <= mLCContSet.size(); i++) {
			LCContSchema tLCContSchema = mLCContSet.get(i);
			LCContDB tLCContDB = new LCContDB();
			tLCContDB.setContNo(tLCContSchema.getContNo());
			if (!tLCContDB.getInfo()) {
				CError.buildErr(this, "查询保单号为" + tLCContDB.getContNo()
						+ "的保单信息时失败!");
				return false;
			}
			tLCContSchema = tLCContDB.getSchema();
			//全局变量的局限性，必须将其每次都初始化
			tAppntIsInsuredFlag="";
		
			mLPEdorItemSchema.setContNo(tLCContSchema.getContNo());
			
			String sql = " select 1 from lcAppnt where contno = '?contno?' and AppntNo ='?mCustomerNo?'";
			SQLwithBindVariables sbv=new SQLwithBindVariables();
			sbv.sql(sql);
			sbv.put("contno", tLCContSchema.getContNo());
			sbv.put("mCustomerNo", mCustomerNo);
			String tFlag=tExeSQL.getOneValue(sbv);
            if("1".equals(tFlag))
			{
				tAppntIsInsuredFlag="0";
			}
			       sql=" select 1 from lcinsured where contno = '?contno?' and insuredno ='?mCustomerNo?'";
			       sbv=new SQLwithBindVariables();
			       sbv.sql(sql);
			       sbv.put("contno", tLCContSchema.getContNo());
			       sbv.put("mCustomerNo", mCustomerNo);
			       tFlag=tExeSQL.getOneValue(sbv);
            if("1".equals(tFlag))
			{
				tAppntIsInsuredFlag="1";
			}
			     
			 sql=" select 1 from lcinsured where contno = '?contno?' and insuredno = (select appntno from lccont where contno = '?contno?')";			
			 sbv=new SQLwithBindVariables();
			 sbv.sql(sql);
		     sbv.put("contno", tLCContSchema.getContNo());
			 tFlag=tExeSQL.getOneValue(sbv);
            if("1".equals(tFlag))
			{
				tAppntIsInsuredFlag="2";
			}

			//说明此客户在此保单中只是投保人，无可能无需进行重算，除非此保单下有投保人豁免险 
			if("0".equals(tAppntIsInsuredFlag))
			{

				LPAppntSchema tLPAppntSchema = new LPAppntSchema();
				LCAppntDB tLCAppntDB = new LCAppntDB();
				tLCAppntDB.setContNo(mLPEdorItemSchema.getContNo());
				tLCAppntDB.setAppntNo(mLPPersonSchema.getCustomerNo());
				
				LCAppntSchema tLCAppntSchema = tLCAppntDB.query().get(1);

				tRef.transFields(tLPAppntSchema, tLCAppntSchema);

				tLPAppntSchema.setContNo(tLCContSchema.getContNo());
				tLPAppntSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
				tLPAppntSchema.setEdorType("CM");
				tLPAppntSchema.setAppntNo(new_CustomerNo);
				tLPAppntSchema.setAppntName(mLPPersonSchema.getName());
				tLPAppntSchema.setLastName(mLPPersonSchema.getLastName());
				tLPAppntSchema.setFirstName(mLPPersonSchema.getFirstName());
				tLPAppntSchema.setAppntSex(mLPPersonSchema.getSex());
				tLPAppntSchema.setAppntBirthday(mLPPersonSchema.getBirthday());
				tLPAppntSchema.setIDType(mLPPersonSchema.getIDType());
				tLPAppntSchema.setIDNo(mLPPersonSchema.getIDNo());
				tLPAppntSchema.setOperator(this.mGlobalInput.Operator);
				tLPAppntSchema.setModifyDate(strCurrentDate);
				tLPAppntSchema.setModifyTime(strCurrentTime);
				mLPAppntSet.add(tLPAppntSchema);

				
				LPContSchema tLPContSchema = new LPContSchema();
				LCContDB aLCContDB = new LCContDB();
				aLCContDB.setContNo(mLPEdorItemSchema.getContNo());
				if (!aLCContDB.getInfo()) {
					mErrors.copyAllErrors(aLCContDB.mErrors);
					mErrors.addOneError(new CError("查询被保人信息失败！"));
					return false;
				}
				tRef.transFields(tLPContSchema, aLCContDB.getSchema());
				tLPContSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
				tLPContSchema.setEdorType(mLPEdorItemSchema.getEdorType());
				
				tLPContSchema.setAppntNo(new_CustomerNo);
				tLPContSchema.setAppntName(mLPPersonSchema.getName());
				tLPContSchema.setAppntSex(mLPPersonSchema.getSex());
				tLPContSchema.setAppntBirthday(mLPPersonSchema.getBirthday());
				tLPContSchema.setAppntIDType(mLPPersonSchema.getIDType());
				tLPContSchema.setAppntIDNo(mLPPersonSchema.getIDNo());
				//字段借用，存放本次变更是否需要重新计算，以提高效率
				

				//有无豁免险标记
				//查询本客户是否在系统中作为投保人，而且投的险种是附加豁免投保人，则需要进行下一步的重算，否则不要需要进行重算
                boolean tFLag=false;		
				LCPolSet tLCPolSet = new LCPolSet();
				LCPolDB tLCPolDB = new LCPolDB();
				tLCPolDB.setContNo(tLCContSchema.getContNo());
				tLCPolSet=tLCPolDB.query();
				for(int k=1;k<=tLCPolSet.size();k++)
				{
					LCPolSchema gLCPolSchema = new LCPolSchema();
					LPPolSchema gLPPolSchema = new LPPolSchema();
					gLCPolSchema=tLCPolSet.get(k);	
					tRef.transFields(gLPPolSchema, gLCPolSchema);
	                String tSQL="select riskcode from lmriskapp where "
                    + " risktype7 in ('1') and riskcode='?riskcode?'";
	                SQLwithBindVariables sqlbv=new SQLwithBindVariables();
	                sqlbv.sql(tSQL);
	                sqlbv.put("riskcode", gLPPolSchema.getRiskCode());
	   			     String tHMFlag=tExeSQL.getOneValue(sqlbv);
	   			     //投保人下有投保人豁免险
	   			     if(!"".equals(tHMFlag))
	   			     {
	   	              	tFLag=true;         	              	
	   			     }
					gLPPolSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
					gLPPolSchema.setEdorType(mLPEdorItemSchema.getEdorType());
					//由于客户号可能为合并后的新号，此处原本是需要修改的，但是为保证IC中保费重算，此处暂时不修改，待IC重算后会统一处理
					//gLPPolSchema.setAppntNo(mLPPersonSchema.getCustomerNo());
					gLPPolSchema.setAppntName(mLPPersonSchema.getName());
					gLPPolSchema.setOperator(this.mGlobalInput.Operator);
					gLPPolSchema.setModifyDate(strCurrentDate);
					gLPPolSchema.setModifyTime(strCurrentTime);	
				
					mLPPolSet.add(gLPPolSchema);
				}
                // 如果性别和年龄改变则重算保额保费
				if ((!StrTool.compareString(tLPAppntSchema.getAppntSex(), tLCAppntSchema
						.getAppntSex())
						|| !StrTool.compareString(tLPAppntSchema.getAppntBirthday(),
								tLCAppntSchema.getAppntBirthday()))&& tFLag) { 
					//字段借用，存放本次变更是否需要重新计算，以提高效率
					tLPContSchema.setLang("1");
				}else
				{
					tLPContSchema.setLang("0");		
                    //如果不需要重算，则在此修改lppol中的客户号
					for (int p=1;p<=mLPPolSet.size();p++)
					{
						mLPPolSet.get(p).setAppntNo(new_CustomerNo);
					}
				}
				
				// 已进入养老金领取期状态的保单，进行重要资料错误变更时，只更正错误信息，不再进行补退费计算；
				String strSQL = "select distinct getstartdate from lcget  a where getdutycode in "
						+ "(select getdutycode from lmdutyget where gettype1='1') "
						+ " and exists (select 'X' "
						+ " from lcpol b  where contno = '?contno?' and a.contno=b.contno "
						+ " and  b.polno=b.mainpolno and  riskcode in (select riskcode from lmriskapp where RiskType4 = '4'))";
				SQLwithBindVariables sqlbv=new SQLwithBindVariables();
				sqlbv.sql(strSQL);
				sqlbv.put("contno", tLPContSchema.getContNo());
				ExeSQL rExeSQL = new ExeSQL();
				SSRS tSSRS = new SSRS();
				tSSRS = rExeSQL.execSQL(sqlbv);
				if (tSSRS.getMaxRow() > 0) {
					FDate tD = new FDate();
					if ((!"".equals(tSSRS.GetText(1, 1))
							&& tD.getDate(tSSRS.GetText(1, 1)) != null && tD
							.getDate(mLPEdorItemSchema.getEdorAppDate())
							.after(tD.getDate(tSSRS.GetText(1, 1))))) {
						tLPContSchema.setLang("0");
					}
				}	
				// tLPContSchema.setOperator(this.mGlobalInput.Operator);
				tLPContSchema.setModifyDate(strCurrentDate);
				tLPContSchema.setModifyTime(strCurrentTime);
				mLPContSet.add(tLPContSchema);

			}

			//说明此客户在此保单中只是被保人  
			if("1".equals(tAppntIsInsuredFlag))
			{

				LPInsuredSchema tLPInsuredSchema = new LPInsuredSchema();
				LCInsuredDB tLCInsuredDB = new LCInsuredDB();
				tLCInsuredDB.setContNo(mLPEdorItemSchema.getContNo());
				tLCInsuredDB.setInsuredNo(mLPPersonSchema.getCustomerNo());
				LCInsuredSchema tLCInsuredSchema =  tLCInsuredDB.query().get(1);
				tRef.transFields(tLPInsuredSchema,tLCInsuredSchema);
				
				tLPInsuredSchema.setContNo(tLCContSchema.getContNo());
				tLPInsuredSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
				tLPInsuredSchema.setEdorType("CM");
				tLPInsuredSchema.setInsuredNo(new_CustomerNo);
				tLPInsuredSchema.setSex(mLPPersonSchema.getSex());
				tLPInsuredSchema.setBirthday(mLPPersonSchema.getBirthday());
				tLPInsuredSchema.setName(mLPPersonSchema.getName());
				tLPInsuredSchema.setLastName(mLPPersonSchema.getLastName());
				tLPInsuredSchema.setFirstName(mLPPersonSchema.getFirstName());
				tLPInsuredSchema.setIDType(mLPPersonSchema.getIDType());
				tLPInsuredSchema.setIDNo(mLPPersonSchema.getIDNo());
				tLPInsuredSchema.setOperator(this.mGlobalInput.Operator);
				tLPInsuredSchema.setModifyDate(strCurrentDate);
				tLPInsuredSchema.setModifyTime(strCurrentTime);
				mLPInsuredSet.add(tLPInsuredSchema);
				

				
				LPContSchema tLPContSchema = new LPContSchema();
				LCContDB aLCContDB = new LCContDB();
				aLCContDB.setContNo(mLPEdorItemSchema.getContNo());
				if (!aLCContDB.getInfo()) {
					mErrors.copyAllErrors(aLCContDB.mErrors);
					mErrors.addOneError(new CError("查询被保人信息失败！"));
					return false;
				}
				tRef.transFields(tLPContSchema, aLCContDB.getSchema());
				
				tLPContSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
				tLPContSchema.setEdorType(mLPEdorItemSchema.getEdorType());
                // 如果性别和年龄改变则重算保额保费
				if (!StrTool.compareString(tLPInsuredSchema.getSex(), tLCInsuredSchema
						.getSex())
						|| !StrTool.compareString(tLPInsuredSchema.getBirthday(),
								tLCInsuredSchema.getBirthday())) { 
					//字段借用，存放本次变更是否需要重新计算，以提高效率
					tLPContSchema.setLang("1");
				}else
				{
					tLPContSchema.setLang("0");					
				}
				
				tLPContSchema.setInsuredNo(new_CustomerNo);
				tLPContSchema.setInsuredSex(mLPPersonSchema.getSex());
				tLPContSchema.setInsuredBirthday(mLPPersonSchema.getBirthday());
				tLPContSchema.setInsuredName(mLPPersonSchema.getName());
				tLPContSchema.setInsuredIDType(mLPPersonSchema.getIDType());
				tLPContSchema.setInsuredIDNo(mLPPersonSchema.getIDNo());

				
				

				// tLPContSchema.setOperator(this.mGlobalInput.Operator);
				tLPContSchema.setModifyDate(strCurrentDate);
				tLPContSchema.setModifyTime(strCurrentTime);
				mLPContSet.add(tLPContSchema);				
				
				LCPolSet tLCPolSet = new LCPolSet();
				LCPolDB tLCPolDB = new LCPolDB();
				tLCPolDB.setContNo(tLCContSchema.getContNo());
				tLCPolSet=tLCPolDB.query();
				for(int k=1;k<=tLCPolSet.size();k++)
				{
					LCPolSchema gLCPolSchema = new LCPolSchema();
					LPPolSchema gLPPolSchema = new LPPolSchema();
					gLCPolSchema=tLCPolSet.get(k);
					tRef.transFields(gLPPolSchema, gLCPolSchema);
					gLPPolSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
					gLPPolSchema.setEdorType(mLPEdorItemSchema.getEdorType());
					gLPPolSchema.setInsuredSex(mLPPersonSchema.getSex());
					gLPPolSchema.setInsuredBirthday(mLPPersonSchema.getBirthday());
					gLPPolSchema.setInsuredName(mLPPersonSchema.getName());
					//由于客户号可能为合并后的新号，此处原本是需要修改的，但是为保证IC中保费重算，此处暂时不修改，待IC重算后会统一处理
					//如果不需要重算，则修改
					if("0".equals(tLPContSchema.getLang()))
					{
						gLPPolSchema.setInsuredNo(new_CustomerNo);
					}
					gLPPolSchema.setOperator(this.mGlobalInput.Operator);
					//维护投保年龄
					gLPPolSchema.setInsuredAppAge(PubFun.calInterval(mLPPersonSchema.getBirthday(),gLCPolSchema.getCValiDate(), "Y"));
					gLPPolSchema.setModifyDate(strCurrentDate);
					gLPPolSchema.setModifyTime(strCurrentTime);	
					

					// 已进入养老金领取期状态的保单，进行重要资料错误变更时，只更正错误信息，不再进行补退费计算；
					String strSQL = "select distinct getstartdate from lcget  a where getdutycode in "
							+ "(select getdutycode from lmdutyget where gettype1='1') "
							+ " and exists (select 'X' "
							+ " from lcpol b  where polno = '?polno?' and a.contno=b.contno "
							+ " and  b.polno=b.mainpolno and  riskcode in (select riskcode from lmriskapp where RiskType4 = '4'))";
					SQLwithBindVariables sqlbv=new SQLwithBindVariables();
					sqlbv.sql(strSQL);
					sqlbv.put("polno", gLPPolSchema.getPolNo());
					ExeSQL tExeSQL = new ExeSQL();
					SSRS tSSRS = new SSRS();
					tSSRS = tExeSQL.execSQL(sqlbv);
					if (tSSRS.getMaxRow() > 0) {
						FDate tD = new FDate();
						if ((!"".equals(tSSRS.GetText(1, 1))
								&& tD.getDate(tSSRS.GetText(1, 1)) != null && tD
								.getDate(mLPEdorItemSchema.getEdorAppDate())
								.after(tD.getDate(tSSRS.GetText(1, 1))))) {
							tLPContSchema.setLang("0");
						}
					}					
					mLPPolSet.add(gLPPolSchema);
				}	
			}

			//2 两者兼之,但是注意，只针对某一保单  
			if("2".equals(tAppntIsInsuredFlag))
			{

				LPInsuredSchema tLPInsuredSchema = new LPInsuredSchema();
				LCInsuredDB tLCInsuredDB = new LCInsuredDB();
				tLCInsuredDB.setContNo(mLPEdorItemSchema.getContNo());
				tLCInsuredDB.setInsuredNo(mLPPersonSchema.getCustomerNo());
				
				LCInsuredSchema tLCInsuredSchema = tLCInsuredDB.query().get(1);

				tRef.transFields(tLPInsuredSchema, tLCInsuredSchema);
				tLPInsuredSchema.setContNo(tLCContSchema.getContNo());
				tLPInsuredSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
				tLPInsuredSchema.setEdorType("CM");
				tLPInsuredSchema.setInsuredNo(new_CustomerNo);
				tLPInsuredSchema.setSex(mLPPersonSchema.getSex());
				tLPInsuredSchema.setBirthday(mLPPersonSchema.getBirthday());
				tLPInsuredSchema.setName(mLPPersonSchema.getName());
				tLPInsuredSchema.setLastName(mLPPersonSchema.getLastName());
				tLPInsuredSchema.setFirstName(mLPPersonSchema.getFirstName());
				tLPInsuredSchema.setIDType(mLPPersonSchema.getIDType());
				tLPInsuredSchema.setIDNo(mLPPersonSchema.getIDNo());
				tLPInsuredSchema.setOperator(this.mGlobalInput.Operator);
				tLPInsuredSchema.setModifyDate(strCurrentDate);
				tLPInsuredSchema.setModifyTime(strCurrentTime);
				mLPInsuredSet.add(tLPInsuredSchema);			
				
				
				LPAppntSchema tLPAppntSchema = new LPAppntSchema();
				LCAppntDB tLCAppntDB = new LCAppntDB();
				tLCAppntDB.setContNo(mLPEdorItemSchema.getContNo());
				tLCAppntDB.setAppntNo(mLPPersonSchema.getCustomerNo());

				tRef.transFields(tLPAppntSchema, tLCAppntDB.query().get(1));
				
				tLPAppntSchema.setContNo(tLCContSchema.getContNo());
				tLPAppntSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
				tLPAppntSchema.setEdorType("CM");
				tLPAppntSchema.setAppntNo(new_CustomerNo);
				tLPAppntSchema.setAppntName(mLPPersonSchema.getName());
				tLPAppntSchema.setLastName(mLPPersonSchema.getLastName());
				tLPAppntSchema.setFirstName(mLPPersonSchema.getFirstName());
				tLPAppntSchema.setAppntSex(mLPPersonSchema.getSex());
				tLPAppntSchema.setAppntBirthday(mLPPersonSchema.getBirthday());
				tLPAppntSchema.setIDType(mLPPersonSchema.getIDType());
				tLPAppntSchema.setIDNo(mLPPersonSchema.getIDNo());
				tLPAppntSchema.setOperator(this.mGlobalInput.Operator);
				tLPAppntSchema.setModifyDate(strCurrentDate);
				tLPAppntSchema.setModifyTime(strCurrentTime);
				mLPAppntSet.add(tLPAppntSchema);
				
				LPContSchema tLPContSchema = new LPContSchema();
				LCContDB aLCContDB = new LCContDB();
				aLCContDB.setContNo(mLPEdorItemSchema.getContNo());
				if (!aLCContDB.getInfo()) {
					mErrors.copyAllErrors(aLCContDB.mErrors);
					mErrors.addOneError(new CError("查询被保人信息失败！"));
					return false;
				}
				tRef.transFields(tLPContSchema, aLCContDB.getSchema());
	
				tLPContSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
				tLPContSchema.setEdorType(mLPEdorItemSchema.getEdorType());
				
				tLPContSchema.setAppntNo(new_CustomerNo);
				tLPContSchema.setAppntName(mLPPersonSchema.getName());
				tLPContSchema.setAppntSex(mLPPersonSchema.getSex());
				tLPContSchema.setAppntBirthday(mLPPersonSchema.getBirthday());
				tLPContSchema.setAppntIDType(mLPPersonSchema.getIDType());
				tLPContSchema.setAppntIDNo(mLPPersonSchema.getIDNo());
				
				tLPContSchema.setInsuredNo(new_CustomerNo);
				tLPContSchema.setInsuredSex(mLPPersonSchema.getSex());
				tLPContSchema.setInsuredBirthday(mLPPersonSchema.getBirthday());
				tLPContSchema.setInsuredName(mLPPersonSchema.getName());
				tLPContSchema.setInsuredIDType(mLPPersonSchema.getIDType());
				tLPContSchema.setInsuredIDNo(mLPPersonSchema.getIDNo());
				

                // 如果性别和年龄改变则重算保额保费
				if (!StrTool.compareString(tLPInsuredSchema.getSex(), tLCInsuredSchema
						.getSex())
						|| !StrTool.compareString(tLPInsuredSchema.getBirthday(),
								tLCInsuredSchema.getBirthday())) { 
					//字段借用，存放本次变更是否需要重新计算，以提高效率
					tLPContSchema.setLang("1");
				}else
				{
					tLPContSchema.setLang("0");					
				}
				// tLPContSchema.setOperator(this.mGlobalInput.Operator);
				tLPContSchema.setModifyDate(strCurrentDate);
				tLPContSchema.setModifyTime(strCurrentTime);
				mLPContSet.add(tLPContSchema);
				
				
				LCPolSet tLCPolSet = new LCPolSet();
				LCPolDB tLCPolDB = new LCPolDB();
				tLCPolDB.setContNo(tLCContSchema.getContNo());
				tLCPolSet=tLCPolDB.query();
				for(int k=1;k<=tLCPolSet.size();k++)
				{
					LCPolSchema gLCPolSchema = new LCPolSchema();
					LPPolSchema gLPPolSchema = new LPPolSchema();
					gLCPolSchema=tLCPolSet.get(k);
					tRef.transFields(gLPPolSchema, gLCPolSchema);
					gLPPolSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
					gLPPolSchema.setEdorType(mLPEdorItemSchema.getEdorType());
					gLPPolSchema.setInsuredSex(mLPPersonSchema.getSex());
					gLPPolSchema.setInsuredBirthday(mLPPersonSchema.getBirthday());
					gLPPolSchema.setInsuredName(mLPPersonSchema.getName());
					gLPPolSchema.setAppntName(mLPPersonSchema.getName());
					//由于客户号可能为合并后的新号，为保证保费重算，此处暂时不修改
					//如果不需要重算，则修改
					if("0".equals(tLPContSchema.getLang()))
					{
						gLPPolSchema.setAppntNo(new_CustomerNo);
						gLPPolSchema.setInsuredNo(new_CustomerNo);
					}
					
					gLPPolSchema.setOperator(this.mGlobalInput.Operator);
					//维护投保年龄
					gLPPolSchema.setInsuredAppAge(PubFun.calInterval(mLPPersonSchema.getBirthday(),gLCPolSchema.getCValiDate(), "Y"));
					
					gLPPolSchema.setModifyDate(strCurrentDate);
					gLPPolSchema.setModifyTime(strCurrentTime);	

					// 已进入养老金领取期状态的保单，进行重要资料错误变更时，只更正错误信息，不再进行补退费计算；
					String strSQL = "select distinct getstartdate from lcget  a where getdutycode in "
							+ "(select getdutycode from lmdutyget where gettype1='1') "
							+ " and exists (select 'X' "
							+ " from lcpol b  where polno = '?polno?' and a.contno=b.contno "
							+ " and  b.polno=b.mainpolno and  riskcode in (select riskcode from lmriskapp where RiskType4 = '4'))";
					SQLwithBindVariables sqlbv=new SQLwithBindVariables();
					sqlbv.sql(strSQL);
					sqlbv.put("polno", gLPPolSchema.getPolNo());
					ExeSQL tExeSQL = new ExeSQL();
					SSRS tSSRS = new SSRS();
					tSSRS = tExeSQL.execSQL(sqlbv);
					if (tSSRS.getMaxRow() > 0) {
						FDate tD = new FDate();
						if ((!"".equals(tSSRS.GetText(1, 1))
								&& tD.getDate(tSSRS.GetText(1, 1)) != null && tD
								.getDate(mLPEdorItemSchema.getEdorAppDate())
								.after(tD.getDate(tSSRS.GetText(1, 1))))) {
							tLPContSchema.setLang("0");
						}
					}										
					mLPPolSet.add(gLPPolSchema);
				}	
			}
		}
		
		mLPEdorItemSchema.setOperator(this.mGlobalInput.Operator);
		//mLPEdorItemSchema.setStandbyFlag1(tAppntIsInsuredFlag);
		//存放老客户号
		mLPEdorItemSchema.setStandbyFlag2(mCustomerNo);
		mLPEdorItemSchema.setModifyDate(strCurrentDate);
		mLPEdorItemSchema.setModifyTime(strCurrentTime);
		mMap.put(mLPEdorItemSchema, "UPDATE");
		return true;
	}

	private boolean prepareOutputData() {

		mMap.put(mLPContSet, "DELETE&INSERT");
		mMap.put(mLPAppntSet, "DELETE&INSERT");
		mMap.put(mLPInsuredSet, "DELETE&INSERT");
		mMap.put(mLPPolSet, "DELETE&INSERT");
//		mMap.put(mLPBnfSet, "DELETE&INSERT");
		mMap.put(mLPPersonSchema, "DELETE&INSERT");
		mResult.clear();
		mResult.add(mMap);
		mResult.add(mTransferData);

		return true;
	}
	/***************************************************************************
	 * 角色表LMRiskRole表校验方法
	 */
	private boolean checkLMRiskRole(LPPolSchema aLPPolSchema, String aRiskRole,
			String aDate, String bDate) {
		LMRiskRoleDB aLMRiskRoleDB = new LMRiskRoleDB();
		LMRiskRoleSet aLMRiskRoleSet = new LMRiskRoleSet();
		aLMRiskRoleDB.setRiskCode(aLPPolSchema.getRiskCode());
		aLMRiskRoleDB.setRiskRole(aRiskRole);
		aLMRiskRoleSet = aLMRiskRoleDB.query();
		if (aLMRiskRoleSet.size() > 0) {
			int maxAgeIntv = PubFun.calInterval(aDate, bDate, aLMRiskRoleSet
					.get(1).getMAXAppAgeFlag().trim());
			int minAgeIntv = PubFun.calInterval(aDate, bDate, aLMRiskRoleSet
					.get(1).getMinAppAgeFlag().trim());
			logger.debug("判断年龄000maxAgeIntv: " + maxAgeIntv);
			logger.debug("判断年龄000minAgeIntv: " + minAgeIntv);
			// 判断年龄
			if (minAgeIntv < aLMRiskRoleSet.get(1).getMinAppAge()) {
				logger.debug("判断年龄111minAgeIntv: " + minAgeIntv);
				return false;
			}
			if (maxAgeIntv > aLMRiskRoleSet.get(1).getMAXAppAge()) {
				logger.debug("判断年龄111maxAgeIntv: " + maxAgeIntv);
				return false;
			}
		}
		return true;
	}


}
