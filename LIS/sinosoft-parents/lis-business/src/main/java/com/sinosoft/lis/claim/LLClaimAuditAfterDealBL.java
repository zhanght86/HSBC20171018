/**
 * Copyright (c) 2005 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LLClaimDB;
import com.sinosoft.lis.db.LLClaimUWMainDB;
import com.sinosoft.lis.db.LLRegisterDB;
import com.sinosoft.lis.db.LWMissionDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LBMissionSchema;
import com.sinosoft.lis.schema.LLClaimSchema;
import com.sinosoft.lis.schema.LLClaimUWMainSchema;
import com.sinosoft.lis.schema.LLRegisterSchema;
import com.sinosoft.lis.vschema.LBMissionSet;
import com.sinosoft.lis.vschema.LWMissionSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 审核确认逻辑处理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author: zl
 * @version 1.0
 */
public class LLClaimAuditAfterDealBL {
private static Logger logger = Logger.getLogger(LLClaimAuditAfterDealBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	private MMap map = new MMap();
	private String CurrentDate = PubFun.getCurrentDate();
	private String CurrentTime = PubFun.getCurrentTime();
	private TransferData mTransferData = new TransferData();
	private Reflections mReflections = new Reflections();

	private LLClaimUWMainSchema mLLClaimUWMainSchema = new LLClaimUWMainSchema();
	private LLClaimSchema mLLClaimSchema = new LLClaimSchema();
	private LLRegisterSchema mLLRegisterSchema = new LLRegisterSchema();

	private String mClmNo = "";
	private String mIsRunBL = "";

	private GlobalInput mG = new GlobalInput();

	public LLClaimAuditAfterDealBL() {
	}

	public static void main(String[] args) {
	}

	/**
	 * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
	 * 
	 * @param cInputData
	 *            传入的数据,VData对象
	 * @param cOperate
	 *            数据操作字符串
	 * @return 布尔值（true--提交成功, false--提交失败）
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		logger.debug("----------LLClaimAuditAfterDealBL begin submitData----------");
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData()) {
			return false;
		}
		logger.debug("----------after getInputData----------");

		// 检查数据合法性
		if (!checkInputData()) {
			return false;
		}
		logger.debug("----------after checkInputData----------");
		
		
		// 进行业务处理
		if (!dealData(cOperate)) {
			return false;
		}
		logger.debug("----------after dealData----------");
		
		
		// 准备往后台的数据
		if (!prepareOutputData()) {
			return false;
		}
		logger.debug("----------after prepareOutputData----------");

		
		mInputData = null;
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData() {
		logger.debug("---LLClaimAuditAfterDealBL start getInputData()");
		// 获取页面报案信息
		mG = (GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0); // 按类取值
		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		mClmNo = (String) mTransferData.getValueByName("RptNo");
		mIsRunBL = (String) mTransferData.getValueByName("IsRunBL"); // 是否运行BL

		if (mClmNo.equals("") && mIsRunBL.equals("1")) {
			// @@错误处理
			CError.buildErr(this, "前台传输的赔案号为空!");
			return false;
		}
		return true;
	}

	/**
	 * 校验传入的报案信息是否合法 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkInputData() {
		return true;
	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData(String cOperate) {

		logger.debug("------start dealData-----");
		boolean tReturn = false;

		// 添加纪录
		if (cOperate.equals("INSERT")) {
			// 案件核赔表
			LLClaimUWMainDB tLLClaimUWMainDB = new LLClaimUWMainDB();
			tLLClaimUWMainDB.setClmNo(mClmNo);
			tLLClaimUWMainDB.getInfo();
			mLLClaimUWMainSchema.setSchema(tLLClaimUWMainDB.getSchema());

			// 赔案表
			LLClaimDB tLLClaimDB = new LLClaimDB();
			tLLClaimDB.setClmNo(mClmNo);
			tLLClaimDB.getInfo();
			mLLClaimSchema.setSchema(tLLClaimDB.getSchema());
			mLLClaimSchema.setGiveType(mLLClaimUWMainSchema
					.getAuditConclusion());
			mLLClaimSchema.setGiveTypeDesc(mLLClaimUWMainSchema.getAuditIdea());

			// 更新立案表
			LLRegisterDB tLLRegisterDB = new LLRegisterDB();
			tLLRegisterDB.setRgtNo(mClmNo);
			tLLRegisterDB.getInfo();
			mLLRegisterSchema.setSchema(tLLRegisterDB.getSchema());
			
			String sql1="";

			// 如果为公司撤案和客户撤案则关闭赔案
			if (mLLClaimUWMainSchema.getAuditConclusion().equals("2")
					|| mLLClaimUWMainSchema.getAuditConclusion().equals("3")) {
				mLLClaimSchema.setClmState("70"); // 赔案关闭
				mLLClaimSchema.setEndCaseDate(CurrentDate);

				mLLRegisterSchema.setClmState("70");
				mLLRegisterSchema.setEndCaseDate(CurrentDate);
				mLLRegisterSchema.setEndCaseFlag("1");
				
				//赔案明细表
				sql1= " update llclaimpolicy set ClmState = '70',endcaseDate='"+"?endcaseDate?"+"' where"
					+ " clmno = '" + "?clmno?" + "'";
				
				// 解除保单挂起
				LLLcContReleaseBL tLLLcContReleaseBL = new LLLcContReleaseBL();
				if (!tLLLcContReleaseBL.submitData(mInputData, "")) {
					// @@错误处理
					CError.buildErr(this, "解除保单挂起失败,"+tLLLcContReleaseBL.mErrors.getLastError());
					return false;
				} else {
					VData tempVData = new VData();
					tempVData = tLLLcContReleaseBL.getResult();
					MMap tMap = new MMap();
					tMap = (MMap) tempVData.getObjectByObjectName("MMap", 0);
					map.add(tMap);
				}
			} else {
				mLLClaimSchema.setClmState("40");
				mLLRegisterSchema.setClmState("40");
				
				sql1 = " update llclaimpolicy set ClmState = '40' where"
					 + " clmno = '" + "?clmno?" + "'";
			}
			SQLwithBindVariables sqlbv = new SQLwithBindVariables();
			sqlbv.sql(sql1);
			sqlbv.put("endcaseDate", CurrentDate);
			sqlbv.put("clmno", mLLClaimSchema.getClmNo());
			map.put(mLLClaimSchema, "DELETE&INSERT");
			map.put(mLLRegisterSchema, "DELETE&INSERT");
			map.put(sqlbv, "UPDATE");
			
			
			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0 将发起二核节点和通知书初始化节点信息转出到备份表中
			 * 
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			/**LWMissionDB tLWMissionDB = new LWMissionDB();
			tLWMissionDB.setMissionProp1(mClmNo);
			tLWMissionDB.setActivityID("0000005505");
			LWMissionSet tLWMissionSet = tLWMissionDB.query();
			

			
			if (tLWMissionSet.size()> 0) {
				
				LBMissionSchema tLBMissionSchema = null;
				LBMissionSet tLBMissionSet = new LBMissionSet();
				
				for(int i=1;i<=tLWMissionSet.size();i++){
					
					tLBMissionSchema = new LBMissionSchema();
					this.mReflections.transFields(tLBMissionSchema, tLWMissionSet
							.get(i));	
					tLBMissionSchema.setSerialNo(PubFun1.CreateMaxNo("MissionSerielNo", 10));
					tLBMissionSet.add(tLBMissionSchema);
					tLBMissionSchema=null;
				}
				
				map.put(tLBMissionSet, "DELETE&INSERT");

				tLBMissionSet=null;
			}*/
		
			
			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No7.0 将发起二核节点和通知书初始化节点信息转出到备份表中
			 * 
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			/**tLWMissionDB.setMissionProp1(mClmNo);
			tLWMissionDB.setActivityID("0000005510");
			tLWMissionSet= tLWMissionDB.query();
			
			if (tLWMissionSet.size()> 0) {
				
				LBMissionSchema tLBMissionSchema = null;
				LBMissionSet tLBMissionSet = new LBMissionSet();
				
				for(int i=1;i<=tLWMissionSet.size();i++){
					
					tLBMissionSchema = new LBMissionSchema();
					this.mReflections.transFields(tLBMissionSchema, tLWMissionSet
							.get(i));
					tLBMissionSchema.setSerialNo(PubFun1.CreateMaxNo("MissionSerielNo", 10));
					tLBMissionSet.add(tLBMissionSchema);
					tLBMissionSchema=null;
				}
				
				map.put(tLBMissionSet, "DELETE&INSERT");
				tLBMissionSet=null;
			}
		
			
			map.put("delete from lwmission where missionprop1='"+mClmNo+"' and activityid in('0000005505','0000005510')", "DELETE");
			
			tLWMissionSet=null;
			tLWMissionDB=null;
			*/
			tReturn = true;
		}
		return tReturn;
	}

	/**
	 * 准备需要保存的数据
	 * 
	 * @return boolean
	 */
	private boolean prepareOutputData() {
		try {
			mInputData.clear();
			mInputData.add(map);
			mResult.add(map);
		} catch (Exception ex) {
			// @@错误处理
			CError.buildErr(this, "在准备往后层处理所需要的数据时出错。");
			return false;
		}
		return true;
	}

}
