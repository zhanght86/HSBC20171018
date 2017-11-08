/**
 * Copyright (c) 2005 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 审核结论逻辑处理类</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Sinosoft</p>
 * @author: zl
 * @version 1.0
 */
public class LLClaimScanIssueConclusionBL
{
private static Logger logger = Logger.getLogger(LLClaimScanIssueConclusionBL.class);

    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();
    /** 往后面传输数据的容器 */
    private VData mInputData;
    /** 往界面传输数据的容器 */
    private VData mResult = new VData();
    /** 数据操作字符串 */
    private String mOperate;
    
	private String CurrentDate = PubFun.getCurrentDate();
	private String CurrentTime = PubFun.getCurrentTime();
	
    private MMap map = new MMap();

    private LLRegisterIssueSchema mLLRegisterIssueSchema = new LLRegisterIssueSchema();
	private TransferData mTransferData = new TransferData();

	private String mMissionID;
	private String mSubMissionID;
	private String mActivityID;
    private GlobalInput mG = new GlobalInput();
    String tCustomerNo;
    
    public LLClaimScanIssueConclusionBL()
    {
    }

    public static void main(String[] args)
    {
    }

    /**
     * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
     * @param cInputData 传入的数据,VData对象
     * @param cOperate 数据操作字符串
     * @return 布尔值（true--提交成功, false--提交失败）
     */
    public boolean submitData(VData cInputData, String cOperate)
    {
        logger.debug(
                "----------LLClaimAuditBL begin submitData----------");
        //将操作数据拷贝到本类中
        mInputData = (VData) cInputData.clone();
        this.mOperate = cOperate;

        //得到外部传入的数据,将数据备份到本类中
        if (!getInputData())
        {
            return false;
        }
        logger.debug("----------after getInputData----------");

        //检查数据合法性
        if (!checkInputData())
        {
            return false;
        }
        logger.debug("----------after checkInputData----------");
        //进行业务处理
        if (!dealData(cOperate))
        {
            return false;
        }
        logger.debug("----------after dealData----------");
        //准备往后台的数据
        if (!prepareOutputData())
        {
            return false;
        }
        logger.debug("----------after prepareOutputData----------");

        PubSubmit tPubSubmit = new PubSubmit();
        if (!tPubSubmit.submitData(mInputData, ""))
        {
            // @@错误处理
            CError.buildErr(this, "数据提交失败,原因是"+tPubSubmit.mErrors.getLastError());
            return false;
        }
        mInputData = null;
        return true;
    }

    public VData getResult()
    {
    	mResult.clear();
        return mResult;
    }

    /**
     * 从输入数据中得到所有对象
     * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean getInputData()
    {
        logger.debug("---LLClaimAuditBL start getInputData()");
        //获取页面报案信息
        mG = (GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0); //按类取值
        mLLRegisterIssueSchema = (LLRegisterIssueSchema) mInputData.
                               getObjectByObjectName(
                                       "LLRegisterIssueSchema", 0);
        mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
        if (mLLRegisterIssueSchema == null)
        {
            // @@错误处理
            CError.buildErr(this, "接受的信息全部为空!");
            return false;
        }
		mMissionID = (String) mTransferData.getValueByName("MissionID");
		if (mMissionID == null || mMissionID.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "LLClaimScanIssueConclusionBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输数据MissionID失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		mSubMissionID = (String) mTransferData.getValueByName("SubMissionID");
		if (mSubMissionID == null || mSubMissionID.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "LLClaimScanIssueConclusionBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输数据SubMissionID失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		mActivityID = (String) mTransferData.getValueByName("ActivityID");
		if (mActivityID == null || mActivityID.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "LLClaimScanIssueConclusionBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输数据ActivityID失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

        return true;
    }

    /**
     * 校验传入的报案信息是否合法
     * 输出：如果发生错误则返回false,否则返回true
     */
    private boolean checkInputData()
    {
    	
    	logger.debug("----------begin checkInputData----------");

        return true;
    }

    /**
     * 数据操作类业务处理
     * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean dealData(String cOperate)
    {

        logger.debug("------start dealData-----");
        boolean tReturn = false;
        String IssueConclusion = mLLRegisterIssueSchema.getIssueConclusion();
        
        if (cOperate.equals("Insert"))
        {
        	LLRegisterIssueSchema tLLRegisterIssueSchema =new LLRegisterIssueSchema ();

        	tLLRegisterIssueSchema.setRgtNo(mLLRegisterIssueSchema.getRgtNo());
        	
			String strSQL = "";
			strSQL = " select count(*)+1 from LLRegisterIssue where "
			       + " rgtno='" + "?rgtno?" + "'";
			SQLwithBindVariables sqlbv = new SQLwithBindVariables();
			sqlbv.sql(strSQL);
			sqlbv.put("rgtno", tLLRegisterIssueSchema.getRgtNo());
			ExeSQL exesql = new ExeSQL();
			String tMaxNo = exesql.getOneValue(sqlbv);
			
			tLLRegisterIssueSchema.setAutditNo(tMaxNo);
			tLLRegisterIssueSchema.setIssueConclusion(mLLRegisterIssueSchema.getIssueConclusion());		
			tLLRegisterIssueSchema.setMakeDate(PubFun.getCurrentDate());
			tLLRegisterIssueSchema.setMakeTime(PubFun.getCurrentTime());
			tLLRegisterIssueSchema.setModifyDate(PubFun.getCurrentDate());
			tLLRegisterIssueSchema.setModifyTime(PubFun.getCurrentTime());
			
			if (!IssueConclusion.equals("01"))
			{
				tLLRegisterIssueSchema.setIssueReason(mLLRegisterIssueSchema.getIssueReason());
				tLLRegisterIssueSchema.setIssueStage(mLLRegisterIssueSchema.getIssueStage());
				tLLRegisterIssueSchema.setIssueBackCom(mG.ManageCom);
				tLLRegisterIssueSchema.setIssueBacker(mG.Operator);
				tLLRegisterIssueSchema.setIssueBackDate(PubFun.getCurrentDate());
				tLLRegisterIssueSchema.setIssueBackTime(PubFun.getCurrentTime());
			}
			
            
			// 打包提交数据
			map.put(tLLRegisterIssueSchema, "INSERT");
            tReturn = true;
        }
      
        if (!IssueConclusion.equals("01"))
        {
        	//将DefaultOperator置为空
        	LWMissionSchema tLWMissionSchema = new LWMissionSchema();
    		LWMissionDB tLWMissionDB = new LWMissionDB();
    		tLWMissionDB.setActivityID(mActivityID);
    		tLWMissionDB.setMissionID(mMissionID);
    		tLWMissionDB.setSubMissionID(mSubMissionID);
    		if (!tLWMissionDB.getInfo()) {
    			// @@错误处理
    			CError.buildErr(this, "工作流任务查询失败!");
    			return false;
    		}
    		tLWMissionSchema.setSchema(tLWMissionDB.getSchema());
    		if (tLWMissionSchema.getDefaultOperator() == null
    				|| tLWMissionSchema.getDefaultOperator().equals("")) {
    			// @@错误处理
    			CError.buildErr(this, "此任务未被申请，请重新申请!");
    			return false;
    		}
    		tLWMissionSchema.setDefaultOperator("");
    		tLWMissionSchema.setInDate(PubFun.getCurrentDate());
    		tLWMissionSchema.setInTime(PubFun.getCurrentTime());
    		tLWMissionSchema.setModifyDate(PubFun.getCurrentDate());
    		tLWMissionSchema.setModifyTime(PubFun.getCurrentTime());
    		map.put(tLWMissionSchema, "UPDATE");
    		tReturn = true;
        }
        if (IssueConclusion.equals("01"))
        {
        	//将DefaultOperator置为空
        	LWMissionSchema pLWMissionSchema = new LWMissionSchema();
    		LWMissionDB pLWMissionDB = new LWMissionDB();
    		pLWMissionDB.setActivityID(mActivityID);
    		pLWMissionDB.setMissionID(mMissionID);
    		pLWMissionDB.setSubMissionID(mSubMissionID);
    		if (!pLWMissionDB.getInfo()) {
    			// @@错误处理
    			CError.buildErr(this, "工作流任务查询失败!");
    			return false;
    		}
    		pLWMissionSchema.setSchema(pLWMissionDB.getSchema());
    		if (pLWMissionSchema.getDefaultOperator() == null
    				|| pLWMissionSchema.getDefaultOperator().equals("")) {
    			// @@错误处理
    			CError.buildErr(this, "此任务未被申请，请重新申请!");
    			return false;
    		}
    		pLWMissionSchema.setMissionProp2("23");//21--扫描完成未初审，22--初审中，23--初审完毕未立案完成
    		pLWMissionSchema.setInDate(PubFun.getCurrentDate());
    		pLWMissionSchema.setInTime(PubFun.getCurrentTime());
    		pLWMissionSchema.setModifyDate(PubFun.getCurrentDate());
    		pLWMissionSchema.setModifyTime(PubFun.getCurrentTime());
    		map.put(pLWMissionSchema, "UPDATE");
    		tReturn = true;
        }
        if (IssueConclusion.equals("02"))
        {
        	
			if (!insertLOPRTManager("PCT003", "3")) {
				return false;
			}
			else
			{
				tReturn = true;
			}
        }
        return tReturn;

    }
    
	/**
	 * 添加打印数据 2005-8-16 14:49
	 * 
	 * @return boolean
	 */
	private boolean insertLOPRTManager(String tCode, String tStateFlag) {
		LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();

		// 插入新值,生成印刷流水号
		String strNolimit = PubFun.getNoLimit(mG.ManageCom);
		logger.debug("strlimit=" + strNolimit);
		String tPrtSeq = PubFun1.CreateMaxNo("PRTSEQNO", strNolimit);
		logger.debug("-----生成的LOPRTManager的印刷流水号= " + tPrtSeq);

		tLOPRTManagerSchema.setPrtSeq(tPrtSeq); // 印刷流水号
		tLOPRTManagerSchema.setOtherNo(mLLRegisterIssueSchema.getRgtNo()); // 对应其它号码
		tLOPRTManagerSchema.setOtherNoType("05"); // 其它号码类型--05赔案号
		tLOPRTManagerSchema.setCode(tCode); // 单据编码
		tLOPRTManagerSchema.setManageCom(mG.ManageCom); // 管理机构
		tLOPRTManagerSchema.setReqCom(mG.ComCode); // 发起机构
		tLOPRTManagerSchema.setReqOperator(mG.Operator); // 发起人
		tLOPRTManagerSchema.setPrtType("0"); // 打印方式
		tLOPRTManagerSchema.setStateFlag(tStateFlag); // 打印状态
		tLOPRTManagerSchema.setMakeDate(CurrentDate); // 入机日期
		tLOPRTManagerSchema.setMakeTime(CurrentTime); // 入机时间
		tLOPRTManagerSchema.setPatchFlag("0"); // 补打标志
		tLOPRTManagerSchema.setStandbyFlag1(CurrentDate);
		tLOPRTManagerSchema.setStandbyFlag4(tCustomerNo); // 客户号码
		tLOPRTManagerSchema.setStandbyFlag5(CurrentDate); // 立案日期
		tLOPRTManagerSchema.setStandbyFlag6("20"); // 赔案状态

		map.put(tLOPRTManagerSchema, "INSERT");
		return true;
	}

    /**
     * 准备需要保存的数据
     * @return boolean
     */
    private boolean prepareOutputData()
    {
        try
        {
            mInputData.clear();
            mInputData.add(map);
            mResult.add(map);
        }
        catch (Exception ex)
        {
            // @@错误处理
            CError.buildErr(this, "在准备往后层处理所需要的数据时出错!");
            return false;
        }
        return true;
    }

}
