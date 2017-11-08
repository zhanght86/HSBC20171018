package com.sinosoft.lis.bq;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.sinosoft.lis.bl.LPContBL;
import com.sinosoft.lis.db.LCAppntDB;
import com.sinosoft.lis.db.LCBnfDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCInsuredDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LDPersonDB;
import com.sinosoft.lis.db.LPAppntDB;
import com.sinosoft.lis.db.LPBnfDB;
import com.sinosoft.lis.db.LPContDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.db.LPInsuredDB;
import com.sinosoft.lis.db.LPPersonDB;
import com.sinosoft.lis.db.LPPolDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCAppntSchema;
import com.sinosoft.lis.schema.LCBnfSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCInsuredSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LDPersonSchema;
import com.sinosoft.lis.schema.LPAppntSchema;
import com.sinosoft.lis.schema.LPBnfSchema;
import com.sinosoft.lis.schema.LPContSchema;
import com.sinosoft.lis.schema.LPContTempInfoSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPInsuredSchema;
import com.sinosoft.lis.schema.LPPersonSchema;
import com.sinosoft.lis.schema.LPPolSchema;
import com.sinosoft.lis.vschema.LCAppntSet;
import com.sinosoft.lis.vschema.LCBnfSet;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LCDutySet;
import com.sinosoft.lis.vschema.LCGetSet;
import com.sinosoft.lis.vschema.LCInsuredSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LDPersonSet;
import com.sinosoft.lis.vschema.LOEngBonusPolSet;
import com.sinosoft.lis.vschema.LPAppntSet;
import com.sinosoft.lis.vschema.LPBnfSet;
import com.sinosoft.lis.vschema.LPContSet;
import com.sinosoft.lis.vschema.LPContTempInfoSet;
import com.sinosoft.lis.vschema.LPDutySet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.lis.vschema.LPEngBonusPolSet;
import com.sinosoft.lis.vschema.LPGetSet;
import com.sinosoft.lis.vschema.LPInsuredSet;
import com.sinosoft.lis.vschema.LPPersonSet;
import com.sinosoft.lis.vschema.LPPolSet;
import com.sinosoft.lis.vschema.LPPremSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 被保人重要资料变更保全申请确认
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author Minim
 * @ReWrite ZhangRong pst
 * @version 1.0 2.0
 */
public class PEdorCMConfirmBL implements EdorConfirm {
private static Logger logger = Logger.getLogger(PEdorCMConfirmBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	//public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	/** 往界面传输数据的容器 */
	private MMap mMap = new MMap();

	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperate;

	/** 全局数据 */
	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();

	private GlobalInput mGlobalInput = new GlobalInput();

	private LCContSet aLCContSet = new LCContSet();

	private LCPolSet aLCPolSet = new LCPolSet();

	private LCInsuredSet delete_LCInsuredSet = new LCInsuredSet();
	private LCInsuredSet insert_LCInsuredSet = new LCInsuredSet();
	
	private LCAppntSet delete_LCAppntSet = new LCAppntSet();
	private LCAppntSet insert_LCAppntSet = new LCAppntSet();

	private LCBnfSet aLCBnfSet = new LCBnfSet();

	private LDPersonSet aLDPersonSet = new LDPersonSet();

	private LPContSet aLPContSet = new LPContSet();

	private LPPolSet aLPPolSet = new LPPolSet();

	private LPInsuredSet delete_LPInsuredSet = new LPInsuredSet();
	private LPInsuredSet insert_LPInsuredSet = new LPInsuredSet();
	
	private LPAppntSet delete_LPAppntSet = new LPAppntSet();
	private LPAppntSet insert_LPAppntSet = new LPAppntSet();

	private LPPersonSet aLPPersonSet = new LPPersonSet();

	private LPBnfSet aLPBnfSet = new LPBnfSet();

	/** 处理其他项目正在处理的本客户下的保单信息,进行UPDATE操作 */
	private LPContSet otherLPContSet = new LPContSet();

	private LPPolSet otherLPPolSet = new LPPolSet();

	private LPInsuredSet otherLPInsuredSet = new LPInsuredSet();

	private LPAppntSet otherLPAppntSet = new LPAppntSet();

	private LPPersonSet otherLPPersonSet = new LPPersonSet();

	private LPBnfSet otherLPBnfSet = new LPBnfSet();

	private LPContTempInfoSet tLPContTempInfoSet = new LPContTempInfoSet();

	/** 字段映射类 */
	private Reflections tRef = new Reflections();

	/** 投被保人标记 AppntIsInsuredFlag 投保人还是被保人0,投保人，1，被保人，2 两者兼之 */
	private String tAppntIsInsuredFlag = "";
	
	/**相关连的保单*/
	private String tContNoRela = "";

	// 获得此时的日期和时间
	private String strCurrentDate = PubFun.getCurrentDate();

	private String strCurrentTime = PubFun.getCurrentTime();

	public PEdorCMConfirmBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData()) {
			return false;
		}

		// 数据复杂业务处理(dealData())
		if (!dealData()) {
			return false;
		}

		/** ***************Start********************* */

		if (!DealOtherEdorItem(mLPEdorItemSchema)) {
			CError.buildErr(this, "处理其他保单的保全项目情况失败");
			return false;
		}

		/** ***************End********************** */

		// 准备数据
		this.prepareOutputData();

		return true;
	}

	/**
	 * 数据输出方法，供外界获取数据处理结果
	 * 
	 * @return 包含有数据查询结果字符串的VData对象
	 */
	public VData getResult() {
		return mResult;
	}

	/**
	 * 将外部传入的数据分解到本类的属性中
	 * 
	 * @param: 无
	 * @return: boolean
	 */
	private boolean getInputData() {
		try {
			mLPEdorItemSchema = (LPEdorItemSchema) mInputData
					.getObjectByObjectName("LPEdorItemSchema", 0);
			mGlobalInput.setSchema((GlobalInput) mInputData
					.getObjectByObjectName("GlobalInput", 0));
		} catch (Exception e) {
			CError.buildErr(this, "接收数据失败");
			return false;
		}
		if (mLPEdorItemSchema == null || mGlobalInput == null) {
			CError.buildErr(this, "接受数据不完全！");
			return false;
		}

		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		// tLPEdorItemDB.setSchema(mLPEdorItemSchema);
		tLPEdorItemDB.setEdorAcceptNo(mLPEdorItemSchema.getEdorAcceptNo());
		tLPEdorItemDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPEdorItemDB.setContNo(mLPEdorItemSchema.getContNo());
		tLPEdorItemDB.setInsuredNo(mLPEdorItemSchema.getInsuredNo());
		tLPEdorItemDB.setEdorType(mLPEdorItemSchema.getEdorType());
		LPEdorItemSet tLPEdorItemSet = tLPEdorItemDB.query();
		if (tLPEdorItemSet == null || tLPEdorItemSet.size() <= 0) {
			CError.buildErr(this, "查询批改项目信息失败！");
			return false;
		}
		mLPEdorItemSchema.setSchema(tLPEdorItemSet.get(1));

		return true;
	}

	/**
	 * 准备需要保存的数据
	 */
	private boolean dealData() {
		try {

			LDPersonSchema aLCPersonSchema = new LDPersonSchema();
			LPPersonSchema aLPPersonSchema = new LPPersonSchema();
			LPPersonSet tLPPersonSet = new LPPersonSet();

			LPPersonSchema tLPPersonSchema = new LPPersonSchema();
			tLPPersonSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPPersonSchema.setEdorType(mLPEdorItemSchema.getEdorType());
			//tLPPersonSchema.setCustomerNo(mLPEdorItemSchema.getInsuredNo());
			LPPersonDB tLPPersonDB = new LPPersonDB();
			tLPPersonDB.setSchema(tLPPersonSchema);
			tLPPersonSet = tLPPersonDB.query();
			for (int j = 1; j <= tLPPersonSet.size(); j++) {
				aLPPersonSchema = tLPPersonSet.get(j);
				LDPersonSchema tLDPersonSchema = new LDPersonSchema();
				tLPPersonSchema = new LPPersonSchema();

				LDPersonDB aLDPersonDB = new LDPersonDB();
				aLDPersonDB.setCustomerNo(aLPPersonSchema.getCustomerNo());
				if (!aLDPersonDB.getInfo()) {
					mErrors.copyAllErrors(aLDPersonDB.mErrors);
					mErrors.addOneError(new CError("查询投保人信息失败！"));
					return false;
				}
				// 转换成保单个人信息。
				tRef.transFields(tLDPersonSchema, aLPPersonSchema);
				tLDPersonSchema.setModifyDate(strCurrentDate);
				tLDPersonSchema.setModifyTime(strCurrentTime);

				aLCPersonSchema = aLDPersonDB.getSchema();
				tRef.transFields(tLPPersonSchema, aLCPersonSchema);
				tLPPersonSchema.setEdorNo(aLPPersonSchema.getEdorNo());
				tLPPersonSchema.setEdorType(aLPPersonSchema.getEdorType());

				aLPPersonSet.add(tLPPersonSchema);
				aLDPersonSet.add(tLDPersonSchema);
			}
			LPContDB tLPContDB = new LPContDB();
			LPContSet tLPContSet = new LPContSet();
//			tLPContDB.setInsuredNo(mLPEdorItemSchema.getInsuredNo());
			tLPContDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPContDB.setEdorType("CM");
			tLPContSet = tLPContDB.query();
			if (tLPContSet == null || tLPContSet.size() <= 0) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "PEdorCMDetailBL";
				tError.functionName = "checkData";
				tError.errorMessage = "无本客户相关保单数据!";
				this.mErrors.addOneError(tError);
				return false;
			}

			for (int k = 1; k <= tLPContSet.size(); k++) {
				LPContSchema tLPContSchema = new LPContSchema();
				LCContSchema tLCContSchema = new LCContSchema();
				tLPContSchema = tLPContSet.get(k);
				
				tContNoRela+="'"+tLPContSchema.getContNo()+"',";


				// 是否需要重新计算 '0','不需要','1','需要',请见明细处理
				if ("1".equals(tLPContSchema.getLang())) {
					LPContTempInfoSchema tLPContTempInfoSchema = new LPContTempInfoSchema();
                    //由于lpcont中的appntno和insuredno可能已经是合并后的新号，这将会导致IC层查询原客户信息出错
					//所以修改为从lppol中获取相关客户信息
					tRef.transFields(tLPContTempInfoSchema, tLPContSchema);
					
					LPPolSet xLPPolSet = new LPPolSet();
					LPPolDB xLPPolDB = new LPPolDB();
					xLPPolDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
					xLPPolDB.setEdorType(mLPEdorItemSchema.getEdorType());
					xLPPolDB.setContNo(tLPContSchema.getContNo());
					xLPPolSet = xLPPolDB.query();
					LPPolSchema xLPPolSchema = new LPPolSchema();
					xLPPolSchema = xLPPolSet.get(1);
					tLPContTempInfoSchema.setAppntNo(xLPPolSchema.getAppntNo());
					tLPContTempInfoSchema.setInsuredNo(xLPPolSchema.getInsuredNo());
					
					tLPContTempInfoSchema.setEdorAcceptNo(mLPEdorItemSchema
							.getEdorAcceptNo());
					tLPContTempInfoSchema.setConfDate(mLPEdorItemSchema
							.getEdorValiDate());
					tLPContTempInfoSchema.setState("0");
					tLPContTempInfoSchema.setApplyOperator(mLPEdorItemSchema
							.getOperator());
					tLPContTempInfoSchema.setApplyDate(mLPEdorItemSchema
							.getMakeDate());
					tLPContTempInfoSchema.setIsNeedReCal("1");
					ExeSQL rExeSQL = new ExeSQL();
					String rAppntIsInsuredFlag = "";
					String sql = " select 1 from lcAppnt where contno = '?contno?' and AppntNo ='?AppntNo?'";
					SQLwithBindVariables sbv1=new SQLwithBindVariables();
					sbv1.sql(sql);
					sbv1.put("contno", tLPContSchema.getContNo());
					sbv1.put("AppntNo", mLPEdorItemSchema.getInsuredNo());
					String tFlag = rExeSQL.getOneValue(sbv1);
					if ("1".equals(tFlag)) {
						rAppntIsInsuredFlag = "0";
					}
					sql = " select 1 from lcinsured where contno = '?contno?' and insuredno ='?insuredno?'";
					sbv1=new SQLwithBindVariables();
					sbv1.sql(sql);
					sbv1.put("contno", tLPContSchema.getContNo());
					sbv1.put("insuredno", mLPEdorItemSchema.getInsuredNo());
					tFlag = rExeSQL.getOneValue(sbv1);
					if ("1".equals(tFlag)) {
						rAppntIsInsuredFlag = "1";
					}

					sql = " select 1 from lcinsured where contno = '?contno?' and insuredno = (select appntno from lccont where contno = '?contno?')";
					sbv1=new SQLwithBindVariables();
					sbv1.sql(sql);
					sbv1.put("contno", tLPContSchema.getContNo());
					tFlag = rExeSQL.getOneValue(sbv1);
					if ("1".equals(tFlag)) {
						rAppntIsInsuredFlag = "2";
					}

					tLPContTempInfoSchema.setConfDate(strCurrentDate);
					tLPContTempInfoSchema
							.setConfOperator(mGlobalInput.Operator);
					tLPContTempInfoSchema.setModifyDate(strCurrentDate);
					tLPContTempInfoSchema.setModifyTime(strCurrentTime);
					tLPContTempInfoSchema.setMakeDate(strCurrentDate);
					tLPContTempInfoSchema.setMakeTime(strCurrentTime);
					tLPContTempInfoSchema.setRemark(rAppntIsInsuredFlag);// 用于记录本次变更保单中客户的角色
					tLPContTempInfoSet.add(tLPContTempInfoSchema);
				} 

				// 回置此字段
				//tLPContSchema.setLang(null);
				//由于保全部门要求CM仅仅修改客户信息，保单层信息在IC修改
                //Lang=0的保单由于不需要做IC操作，直接在此将保单信息修改，而Lang=1的保单，其CM中生成的保单层的p表信息将在IC中进行PC互换
				else 
				{
					// 转换成保单个人信息。
					tRef.transFields(tLCContSchema, tLPContSchema);
					tLCContSchema.setModifyDate(strCurrentDate);
					tLCContSchema.setModifyTime(strCurrentTime);
	
					LCContDB aLCContDB = new LCContDB();
					aLCContDB.setContNo(tLPContSchema.getContNo());
					if (!aLCContDB.getInfo()) {
						mErrors.copyAllErrors(aLCContDB.mErrors);
						mErrors.addOneError(new CError("查询被保人信息失败！"));
						return false;
					}
					tRef.transFields(tLPContSchema, aLCContDB.getSchema());
					tLPContSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
					tLPContSchema.setEdorType(mLPEdorItemSchema.getEdorType());
	
					aLPContSet.add(tLPContSchema);
					aLCContSet.add(tLCContSchema);
	
					//由于可能有客户号合并，p表中的客户号可能已经改变，因此此处需要
					LCInsuredSchema aLCInsuredSchema = new LCInsuredSchema();
					LPInsuredSchema aLPInsuredSchema = new LPInsuredSchema();
					LPInsuredSet tLPInsuredSet = new LPInsuredSet();
	
					LPInsuredSchema tLPInsuredSchema = new LPInsuredSchema();
					tLPInsuredSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
					tLPInsuredSchema.setEdorType(mLPEdorItemSchema.getEdorType());
					tLPInsuredSchema.setContNo(tLPContSchema.getContNo());
	
					LPInsuredDB tLPInsuredDB = new LPInsuredDB();
					tLPInsuredDB.setSchema(tLPInsuredSchema);
					tLPInsuredSet = tLPInsuredDB.query();
					for (int j = 1; j <= tLPInsuredSet.size(); j++) {
						aLPInsuredSchema = tLPInsuredSet.get(j);
						delete_LPInsuredSet.add(aLPInsuredSchema);
						
						LCInsuredSchema tLCInsuredSchema = new LCInsuredSchema();
						tLPInsuredSchema = new LPInsuredSchema();
	
						// 转换成保单个人信息。
						tRef.transFields(tLCInsuredSchema, aLPInsuredSchema);
						tLCInsuredSchema.setModifyDate(strCurrentDate);
						tLCInsuredSchema.setModifyTime(strCurrentTime);
						insert_LCInsuredSet.add(tLCInsuredSchema);
						
						LCInsuredDB aLCInsuredDB = new LCInsuredDB();
						aLCInsuredDB.setContNo(aLPInsuredSchema.getContNo());
						if (aLCInsuredDB.query().size()==0) {
							mErrors.copyAllErrors(aLCInsuredDB.mErrors);
							mErrors.addOneError(new CError("查询被保人信息失败！"));
							return false;
						}
						aLCInsuredSchema = aLCInsuredDB.query().get(1);
						delete_LCInsuredSet.add(aLCInsuredSchema);
						
						tRef.transFields(tLPInsuredSchema, aLCInsuredSchema);
						tLPInsuredSchema.setEdorNo(aLPInsuredSchema.getEdorNo());
						tLPInsuredSchema.setEdorType(aLPInsuredSchema.getEdorType());
						insert_LPInsuredSet.add(tLPInsuredSchema);
						
					}
	
					// 得到当前客户资料
					LCAppntSchema aLCAppntSchema = new LCAppntSchema();
					LPAppntSchema aLPAppntSchema = new LPAppntSchema();
					LPAppntSet tLPAppntSet = new LPAppntSet();
	
					LPAppntSchema tLPAppntSchema = new LPAppntSchema();
					tLPAppntSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
					tLPAppntSchema.setEdorType(mLPEdorItemSchema.getEdorType());
					tLPAppntSchema.setContNo(tLPContSchema.getContNo());
	
					LPAppntDB tLPAppntDB = new LPAppntDB();
					tLPAppntDB.setSchema(tLPAppntSchema);
					tLPAppntSet = tLPAppntDB.query();
					for (int j = 1; j <= tLPAppntSet.size(); j++) {
						aLPAppntSchema = tLPAppntSet.get(j);
						delete_LPAppntSet.add(aLPAppntSchema);
						
						LCAppntSchema tLCAppntSchema = new LCAppntSchema();
						tLPAppntSchema = new LPAppntSchema();
	
						// 转换成保单个人信息。
						tRef.transFields(tLCAppntSchema, aLPAppntSchema);
						tLCAppntSchema.setModifyDate(strCurrentDate);
						tLCAppntSchema.setModifyTime(strCurrentTime);
						insert_LCAppntSet.add(tLCAppntSchema);
	
						LCAppntDB aLCAppntDB = new LCAppntDB();
						aLCAppntDB.setContNo(aLPAppntSchema.getContNo());
						if (aLCAppntDB.query().size()==0) 
						{
							mErrors.copyAllErrors(aLCAppntDB.mErrors);
							mErrors.addOneError(new CError("查询投保人信息失败！"));
							return false;
						}
						aLCAppntSchema = aLCAppntDB.query().get(1);
						delete_LCAppntSet.add(aLCAppntSchema);
						
						tRef.transFields(tLPAppntSchema, aLCAppntSchema);
						tLPAppntSchema.setEdorNo(aLPAppntSchema.getEdorNo());
						tLPAppntSchema.setEdorType(aLPAppntSchema.getEdorType());
						insert_LPAppntSet.add(tLPAppntSchema);
					}
	
					LPPolSet tLPPolSet = new LPPolSet();
					LPPolDB tLPPolDB = new LPPolDB();
					tLPPolDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
					tLPPolDB.setEdorType(mLPEdorItemSchema.getEdorType());
					tLPPolDB.setContNo(tLPContSchema.getContNo());
					tLPPolSet = tLPPolDB.query();
	
					for (int i = 1; i <= tLPPolSet.size(); i++) {
						LPPolSchema tLPPolSchema = new LPPolSchema();
						LCPolSchema tLCPolSchema = new LCPolSchema();
						tRef.transFields(tLCPolSchema, tLPPolSet.get(i));
	
						LCPolDB tLCPolDB = new LCPolDB();
						tLCPolDB.setPolNo(tLCPolSchema.getPolNo());
						if (!tLCPolDB.getInfo()) {
							mErrors.copyAllErrors(tLCPolDB.mErrors);
							mErrors.addOneError(new CError("查询险种保单表失败！"));
							return false;
						}
						tRef.transFields(tLPPolSchema, tLCPolDB.getSchema());
						tLPPolSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
						tLPPolSchema.setEdorType(mLPEdorItemSchema.getEdorType());
						aLPPolSet.add(tLPPolSchema);
						aLCPolSet.add(tLCPolSchema);
					}
			    }
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();

			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorCMConfirmBL";
			tError.functionName = "dealData";
			tError.errorMessage = "数据处理失败! " + e.getMessage();
			this.mErrors.addOneError(tError);

			return false;
		}
	}

	/**
	 * 判断并处理此保单是否被其他保全挂起，要处理其保单的Ｐ表信息 不在如下状态下 0 确认生效,4逾期终止,8核保终止,9审批终止,b保全回退
	 * 要将表数据进行修改
	 */
	public boolean DealOtherEdorItem(LPEdorItemSchema tLPEdorItemSchema) {
		String tSQL = "";
		ExeSQL tExeSQL = new ExeSQL();
		// 取得客户信息
		LDPersonSchema mLDPersonSchema = new LDPersonSchema();
		mLDPersonSchema = aLDPersonSet.get(1);

		// 取得其他保单的批改信息
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		LPEdorItemSet tLPEdorItemSet = new LPEdorItemSet();
		tSQL = "select * from LPEdorItem b where b.grpcontno='00000000000000000000' and  b.contno in ('?tContNoRela?') and  b.edorstate not in ('0','4','8','9','b')  and b.edorno !='?edorno?'";
		
		logger.debug(""+tSQL);
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		ArrayList<String> arrStr=new ArrayList<String>();
		String[] str=tContNoRela.split(",");
		for(int i=0;i<str.length;i++){
			arrStr.add(str[i]);
		}
		sqlbv.sql(tSQL);
		sqlbv.put("tContNoRela", arrStr);
		sqlbv.put("edorno", tLPEdorItemSchema.getEdorNo());
		tLPEdorItemSet = tLPEdorItemDB.executeQuery(sqlbv);
		if (tLPEdorItemSet == null || tLPEdorItemSet.size()< 1) {
			// @@错误处理
			return true;
		}

		for (int k = 1; k <= tLPEdorItemSet.size(); k++) {
			LPContSchema tLPContSchema = new LPContSchema();
			//本客户其他保单的批改表，注意和tLPEdorItemSchema的区别
			//tLPEdorItemSchema为本保单的批改表
			LPEdorItemSchema cLPEdorItemSchema = new LPEdorItemSchema();
			cLPEdorItemSchema=tLPEdorItemSet.get(k);
			LPContDB tLPContDB = new LPContDB();
			tLPContDB.setContNo(cLPEdorItemSchema.getContNo());
			tLPContDB.setEdorNo(cLPEdorItemSchema.getEdorNo());
			tLPContDB.setEdorType(cLPEdorItemSchema.getEdorType());
			if (tLPContDB.getInfo()) {
				tLPContSchema = tLPContDB.getSchema();
			}else
			{
				tLPContSchema=null;
			}

			
            //全局变量的局限性，必须将其每次都初始化
			tAppntIsInsuredFlag="";
			LPPersonSet tLPPersonSet = new LPPersonSet();
			LPPersonDB tLPPersonDB = new LPPersonDB();
			tLPPersonDB.setCustomerNo(tLPEdorItemSchema.getInsuredNo());
			tLPPersonDB.setEdorNo(cLPEdorItemSchema.getEdorNo());
			tLPPersonDB.setEdorType(cLPEdorItemSchema.getEdorType());
			tLPPersonSet = tLPPersonDB.query();
			if (tLPPersonDB.mErrors.needDealError()) {
				CError.buildErr(this, "关联客户信息查询失败!");
				return false;
			}
			if (tLPPersonSet != null && tLPPersonSet.size() > 0) {
				for (int i = 1; i <= tLPPersonSet.size(); i++) {
					LPPersonSchema gLPPersonSchema = new LPPersonSchema();
					gLPPersonSchema = tLPPersonSet.get(i);
					gLPPersonSchema.setSex(mLDPersonSchema.getSex());
					gLPPersonSchema.setBirthday(mLDPersonSchema.getBirthday());
					gLPPersonSchema.setName(mLDPersonSchema.getName());
					gLPPersonSchema.setIDNo(mLDPersonSchema.getIDNo());
					gLPPersonSchema.setIDType(mLDPersonSchema.getIDType());
					otherLPPersonSet.add(gLPPersonSchema);
				}
			}

			String sql = " select 1 from lcAppnt where contno = '?contno?' and AppntNo ='?AppntNo?'";
			SQLwithBindVariables sbv=new SQLwithBindVariables();
			sbv.sql(sql);
			sbv.put("contno", cLPEdorItemSchema.getContNo());
			sbv.put("AppntNo", tLPEdorItemSchema.getInsuredNo());
			String tFlag = tExeSQL.getOneValue(sbv);
           if ("1".equals(tFlag)) {
				tAppntIsInsuredFlag = "0";
			}
			sql = " select 1 from lcinsured where contno = '?contno?' and insuredno ='?insuredno?'";
			sbv=new SQLwithBindVariables();
			sbv.sql(sql);
			sbv.put("contno", cLPEdorItemSchema.getContNo());
			sbv.put("insuredno", tLPEdorItemSchema.getInsuredNo());
			tFlag = tExeSQL.getOneValue(sbv);
           if ("1".equals(tFlag)) {
				tAppntIsInsuredFlag = "1";
			}

			sql = " select 1 from lcinsured where contno = '?contno?' and insuredno = (select appntno from lccont where contno = '?contno?')";
			sbv=new SQLwithBindVariables();
			sbv.sql(sql);
			sbv.put("contno", cLPEdorItemSchema.getContNo());
			tFlag = tExeSQL.getOneValue(sbv);
            if ("1".equals(tFlag)) {
				tAppntIsInsuredFlag = "2";
			}

			if ("0".equals(tAppntIsInsuredFlag)) {
				LPAppntDB tLPAppntDB = new LPAppntDB();
				tLPAppntDB.setContNo(cLPEdorItemSchema.getContNo());
				tLPAppntDB.setAppntNo(tLPEdorItemSchema.getInsuredNo());
				tLPAppntDB.setEdorNo(cLPEdorItemSchema.getEdorNo());
				tLPAppntDB.setEdorType(cLPEdorItemSchema.getEdorType());
				LPAppntSet tLPAppntSet = tLPAppntDB.query();
				if (tLPAppntDB.mErrors.needDealError()) {
					CError.buildErr(this, "关联投保人信息查询失败!");
					return false;
				}
				if (tLPAppntSet != null && tLPAppntSet.size() > 0) {
					for (int i = 1; i <= tLPAppntSet.size(); i++) {
						LPAppntSchema tLPAppntSchema = new LPAppntSchema();
						tLPAppntSchema = tLPAppntSet.get(i);
						tLPAppntSchema.setAppntName(mLDPersonSchema.getName());
						tLPAppntSchema.setAppntSex(mLDPersonSchema.getSex());
						tLPAppntSchema.setAppntBirthday(mLDPersonSchema
								.getBirthday());
						tLPAppntSchema.setIDNo(mLDPersonSchema.getIDNo());
						tLPAppntSchema.setIDType(mLDPersonSchema.getIDType());
						otherLPAppntSet.add(tLPAppntSchema);
					}
				}
                if(tLPContSchema!=null)
                {
				tLPContSchema.setAppntName(mLDPersonSchema.getName());
				tLPContSchema.setAppntSex(mLDPersonSchema.getSex());
				tLPContSchema.setAppntBirthday(mLDPersonSchema.getBirthday());
				tLPContSchema.setAppntIDType(mLDPersonSchema.getIDType());
				tLPContSchema.setAppntIDNo(mLDPersonSchema.getIDNo());
				otherLPContSet.add(tLPContSchema);
                }

				LPPolSet tLPPolSet = new LPPolSet();
				LPPolDB tLPPolDB = new LPPolDB();
				tLPPolDB.setContNo(cLPEdorItemSchema.getContNo());
				tLPPolDB.setEdorNo(cLPEdorItemSchema.getEdorNo());
				tLPPolDB.setEdorType(cLPEdorItemSchema.getEdorType());
				tLPPolSet = tLPPolDB.query();
				if (tLPPolDB.mErrors.needDealError()) {
					CError.buildErr(this, "关联保单信息查询失败!");
					return false;
				}
				if (tLPPolSet != null && tLPPolSet.size() > 0) {
					for (int i = 1; i <= tLPPolSet.size(); i++) {
						LPPolSchema gLPPolSchema = new LPPolSchema();
						gLPPolSchema = tLPPolSet.get(i);
						gLPPolSchema.setAppntName(mLDPersonSchema.getName());
						otherLPPolSet.add(gLPPolSchema);
					}
				}

//				LPBnfDB tLPBnfDB = new LPBnfDB();
//				tLPBnfDB.setContNo(cLPEdorItemSchema.getContNo());
//				tLPBnfDB.setCustomerNo(tLPEdorItemSchema.getInsuredNo());
//				tLPBnfDB.setEdorNo(cLPEdorItemSchema.getEdorNo());
//				tLPBnfDB.setEdorType(cLPEdorItemSchema.getEdorType());
//				LPBnfSet tLPBnfSet = tLPBnfDB.query();
//				if (tLPBnfDB.mErrors.needDealError()) {
//					CError.buildErr(this, "关联受益人查询失败!");
//					return false;
//				}
//				if (tLPBnfSet != null && tLPBnfSet.size() > 0) {
//					for (int i = 1; i <= tLPBnfSet.size(); i++) {
//						LPBnfSchema tLPBnfSchema = new LPBnfSchema();
//						tLPBnfSchema = tLPBnfSet.get(i);
//						tLPBnfSchema.setName(mLDPersonSchema.getName());
//						tLPBnfSchema.setSex(mLDPersonSchema.getSex());
//						tLPBnfSchema.setBirthday(mLDPersonSchema.getBirthday());
//						tLPBnfSchema.setIDNo(mLDPersonSchema.getIDNo());
//						tLPBnfSchema.setIDType(mLDPersonSchema.getIDType());
//						otherLPBnfSet.add(tLPBnfSchema);
//					}
//				}

			}

			//说明此客户在此保单中只是被保人  
			if ("1".equals(tAppntIsInsuredFlag)) {
				if(tLPContSchema!=null)
				{
					tLPContSchema.setInsuredSex(mLDPersonSchema.getSex());
					tLPContSchema.setInsuredBirthday(mLDPersonSchema.getBirthday());
					tLPContSchema.setInsuredName(mLDPersonSchema.getName());
					tLPContSchema.setInsuredIDType(mLDPersonSchema.getIDType());
					tLPContSchema.setInsuredIDNo(mLDPersonSchema.getIDNo());
					otherLPContSet.add(tLPContSchema);

				}

				LPInsuredDB tLPInsuredDB = new LPInsuredDB();
				tLPInsuredDB.setContNo(cLPEdorItemSchema.getContNo());
				tLPInsuredDB.setInsuredNo(tLPEdorItemSchema.getInsuredNo());
				tLPInsuredDB.setEdorNo(cLPEdorItemSchema.getEdorNo());
				tLPInsuredDB.setEdorType(cLPEdorItemSchema.getEdorType());
				LPInsuredSet tLPInsuredSet = tLPInsuredDB.query();
				if (tLPInsuredDB.mErrors.needDealError()) {
					CError.buildErr(this, "关联被保人查询失败!");
					return false;
				}
				if (tLPInsuredSet != null && tLPInsuredSet.size() > 0) {
					for (int i = 1; i <= tLPInsuredSet.size(); i++) {
						LPInsuredSchema tLPInsuredSchema = new LPInsuredSchema();
						tLPInsuredSchema = tLPInsuredSet.get(i);
						tLPInsuredSchema.setName(mLDPersonSchema.getName());
						tLPInsuredSchema.setSex(mLDPersonSchema.getSex());
						tLPInsuredSchema.setBirthday(mLDPersonSchema
								.getBirthday());
						tLPInsuredSchema.setIDNo(mLDPersonSchema.getIDNo());
						tLPInsuredSchema.setIDType(mLDPersonSchema.getIDType());
						otherLPInsuredSet.add(tLPInsuredSchema);
					}
				}

				LPPolSet tLPPolSet = new LPPolSet();
				LPPolDB tLPPolDB = new LPPolDB();
				tLPPolDB.setContNo(cLPEdorItemSchema.getContNo());
				tLPPolDB.setEdorNo(cLPEdorItemSchema.getEdorNo());
				tLPPolDB.setEdorType(cLPEdorItemSchema.getEdorType());
				tLPPolSet = tLPPolDB.query();
				if (tLPPolDB.mErrors.needDealError()) {
					CError.buildErr(this, "关联险种信息查询失败!");
					return false;
				}
				if (tLPPolSet != null && tLPPolSet.size() > 0) {
					for (int i = 1; i <= tLPPolSet.size(); i++) {
						LPPolSchema gLPPolSchema = new LPPolSchema();
						gLPPolSchema = tLPPolSet.get(i);
						gLPPolSchema.setInsuredSex(mLDPersonSchema.getSex());
						gLPPolSchema.setInsuredBirthday(mLDPersonSchema
								.getBirthday());
						gLPPolSchema.setInsuredName(mLDPersonSchema.getName());
						otherLPPolSet.add(gLPPolSchema);
					}
				}

//				LPBnfDB tLPBnfDB = new LPBnfDB();
//				tLPBnfDB.setContNo(cLPEdorItemSchema.getContNo());
//				tLPBnfDB.setCustomerNo(tLPEdorItemSchema.getInsuredNo());
//				tLPBnfDB.setEdorNo(cLPEdorItemSchema.getEdorNo());
//				tLPBnfDB.setEdorType(cLPEdorItemSchema.getEdorType());
//				LPBnfSet tLPBnfSet = tLPBnfDB.query();
//				if (tLPBnfDB.mErrors.needDealError()) {
//					CError.buildErr(this, "关联受益人查询失败!");
//					return false;
//				}
//				if (tLPBnfSet != null && tLPBnfSet.size() > 0) {
//					for (int i = 1; i <= tLPBnfSet.size(); i++) {
//						LPBnfSchema tLPBnfSchema = new LPBnfSchema();
//						tLPBnfSchema = tLPBnfSet.get(i);
//						tLPBnfSchema.setName(mLDPersonSchema.getName());
//						tLPBnfSchema.setSex(mLDPersonSchema.getSex());
//						tLPBnfSchema.setBirthday(mLDPersonSchema.getBirthday());
//						tLPBnfSchema.setIDNo(mLDPersonSchema.getIDNo());
//						tLPBnfSchema.setIDType(mLDPersonSchema.getIDType());
//						otherLPBnfSet.add(tLPBnfSchema);
//					}
//				}
			}
			if ("2".equals(tAppntIsInsuredFlag)) {
				
				if(tLPContSchema!=null)
				{
				tLPContSchema.setAppntName(mLDPersonSchema.getName());
				tLPContSchema.setAppntSex(mLDPersonSchema.getSex());
				tLPContSchema.setAppntBirthday(mLDPersonSchema.getBirthday());
				tLPContSchema.setAppntIDType(mLDPersonSchema.getIDType());
				tLPContSchema.setAppntIDNo(mLDPersonSchema.getIDNo());

				tLPContSchema.setInsuredSex(mLDPersonSchema.getSex());
				tLPContSchema.setInsuredBirthday(mLDPersonSchema.getBirthday());
				tLPContSchema.setInsuredName(mLDPersonSchema.getName());
				tLPContSchema.setInsuredIDType(mLDPersonSchema.getIDType());
				tLPContSchema.setInsuredIDNo(mLDPersonSchema.getIDNo());
				otherLPContSet.add(tLPContSchema);
				}

				LPAppntDB tLPAppntDB = new LPAppntDB();
				tLPAppntDB.setContNo(cLPEdorItemSchema.getContNo());
				tLPAppntDB.setAppntNo(tLPEdorItemSchema.getInsuredNo());
				tLPAppntDB.setEdorNo(cLPEdorItemSchema.getEdorNo());
				tLPAppntDB.setEdorType(cLPEdorItemSchema.getEdorType());
				LPAppntSet tLPAppntSet = tLPAppntDB.query();
				if (tLPAppntDB.mErrors.needDealError()) {
					CError.buildErr(this, "关联投保人信息查询失败!");
					return false;
				}
				if (tLPAppntSet != null && tLPAppntSet.size() > 0) {
					for (int i = 1; i <= tLPAppntSet.size(); i++) {
						LPAppntSchema tLPAppntSchema = new LPAppntSchema();
						tLPAppntSchema = tLPAppntSet.get(i);
						tLPAppntSchema.setAppntName(mLDPersonSchema.getName());
						tLPAppntSchema.setAppntSex(mLDPersonSchema.getSex());
						tLPAppntSchema.setAppntBirthday(mLDPersonSchema
								.getBirthday());
						tLPAppntSchema.setIDNo(mLDPersonSchema.getIDNo());
						tLPAppntSchema.setIDType(mLDPersonSchema.getIDType());
						otherLPAppntSet.add(tLPAppntSchema);
					}
				}

				LPInsuredDB tLPInsuredDB = new LPInsuredDB();
				tLPInsuredDB.setContNo(cLPEdorItemSchema.getContNo());
				tLPInsuredDB.setInsuredNo(tLPEdorItemSchema.getInsuredNo());
				tLPInsuredDB.setEdorNo(cLPEdorItemSchema.getEdorNo());
				tLPInsuredDB.setEdorType(cLPEdorItemSchema.getEdorType());
				LPInsuredSet tLPInsuredSet = tLPInsuredDB.query();
				if (tLPInsuredDB.mErrors.needDealError()) {
					CError.buildErr(this, "关联被保人查询失败!");
					return false;
				}
				if (tLPInsuredSet != null && tLPInsuredSet.size() > 0) {
					for (int i = 1; i <= tLPInsuredSet.size(); i++) {
						LPInsuredSchema tLPInsuredSchema = new LPInsuredSchema();
						tLPInsuredSchema = tLPInsuredSet.get(i);
						tLPInsuredSchema.setName(mLDPersonSchema.getName());
						tLPInsuredSchema.setSex(mLDPersonSchema.getSex());
						tLPInsuredSchema.setBirthday(mLDPersonSchema
								.getBirthday());
						tLPInsuredSchema.setIDNo(mLDPersonSchema.getIDNo());
						tLPInsuredSchema.setIDType(mLDPersonSchema.getIDType());
						otherLPInsuredSet.add(tLPInsuredSchema);
					}
				}

				LPPolSet tLPPolSet = new LPPolSet();
				LPPolDB tLPPolDB = new LPPolDB();
				tLPPolDB.setContNo(cLPEdorItemSchema.getContNo());
				tLPPolDB.setEdorNo(cLPEdorItemSchema.getEdorNo());
				tLPPolDB.setEdorType(cLPEdorItemSchema.getEdorType());
				tLPPolSet = tLPPolDB.query();
				if (tLPPolDB.mErrors.needDealError()) {
					CError.buildErr(this, "关联险种信息查询失败!");
					return false;
				}
				if (tLPPolSet != null && tLPPolSet.size() > 0) {
					for (int i = 1; i <= tLPPolSet.size(); i++) {
						LPPolSchema gLPPolSchema = new LPPolSchema();
						gLPPolSchema = tLPPolSet.get(i);
						gLPPolSchema.setAppntName(mLDPersonSchema.getName());
						gLPPolSchema.setInsuredSex(mLDPersonSchema.getSex());
						gLPPolSchema.setInsuredBirthday(mLDPersonSchema
								.getBirthday());
						gLPPolSchema.setInsuredName(mLDPersonSchema.getName());
						otherLPPolSet.add(gLPPolSchema);
					}
				}
//				LPBnfDB tLPBnfDB = new LPBnfDB();
//				tLPBnfDB.setContNo(cLPEdorItemSchema.getContNo());
//				tLPBnfDB.setCustomerNo(tLPEdorItemSchema.getInsuredNo());
//				tLPBnfDB.setEdorNo(cLPEdorItemSchema.getEdorNo());
//				tLPBnfDB.setEdorType(cLPEdorItemSchema.getEdorType());
//				LPBnfSet tLPBnfSet = tLPBnfDB.query();
//				if (tLPBnfDB.mErrors.needDealError()) {
//					CError.buildErr(this, "关联受益人查询失败!");
//					return false;
//				}
//				if (tLPBnfSet != null && tLPBnfSet.size() > 0) {
//					for (int i = 1; i <= tLPBnfSet.size(); i++) {
//						LPBnfSchema tLPBnfSchema = new LPBnfSchema();
//						tLPBnfSchema = tLPBnfSet.get(i);
//						tLPBnfSchema.setName(mLDPersonSchema.getName());
//						tLPBnfSchema.setSex(mLDPersonSchema.getSex());
//						tLPBnfSchema.setBirthday(mLDPersonSchema.getBirthday());
//						tLPBnfSchema.setIDNo(mLDPersonSchema.getIDNo());
//						tLPBnfSchema.setIDType(mLDPersonSchema.getIDType());
//						otherLPBnfSet.add(tLPBnfSchema);
//					}
//				}

			}
		}

		return true;
	}

	/**
	 * 
	 */
	private void prepareOutputData() {
		mMap.put(aLPContSet, "DELETE&INSERT");
		mMap.put(delete_LPAppntSet, "DELETE");
		mMap.put(insert_LPAppntSet, "INSERT");
		mMap.put(delete_LPInsuredSet, "DELETE");
		mMap.put(insert_LPInsuredSet, "INSERT");
		
		
		mMap.put(tLPContTempInfoSet, "DELETE&INSERT");
		mMap.put(aLPPolSet, "DELETE&INSERT");
//		mMap.put(aLPBnfSet, "DELETE&INSERT");
		mMap.put(aLPPersonSet, "DELETE&INSERT");

		mMap.put(otherLPContSet, "UPDATE");
		mMap.put(otherLPAppntSet, "UPDATE");
		mMap.put(otherLPInsuredSet, "UPDATE");
		mMap.put(otherLPPolSet, "UPDATE");
//		mMap.put(otherLPBnfSet, "UPDATE");
		mMap.put(otherLPPersonSet, "UPDATE");

		mMap.put(aLDPersonSet, "DELETE&INSERT");
		mMap.put(aLCContSet, "DELETE&INSERT");
		mMap.put(delete_LCAppntSet, "DELETE");
		mMap.put(insert_LCAppntSet, "INSERT");
		mMap.put(delete_LCInsuredSet, "DELETE");
		mMap.put(insert_LCInsuredSet, "INSERT");
		mMap.put(aLCPolSet, "DELETE&INSERT");
//		mMap.put(aLCBnfSet, "DELETE&INSERT");

		mResult.clear();
		mResult.add(mMap);
	}

}
