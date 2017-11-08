package com.sinosoft.lis.cbcheck;
import org.apache.log4j.Logger;

import java.util.Hashtable;

import com.sinosoft.lis.db.ES_DOC_MAINDB;
import com.sinosoft.lis.db.LACommisionDB;
import com.sinosoft.lis.db.LBPolDB;
import com.sinosoft.lis.db.LBRemarkDB;
import com.sinosoft.lis.db.LCAddPolDB;
import com.sinosoft.lis.db.LCApplyRecallPolDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCEdorReasonDB;
import com.sinosoft.lis.db.LWNotePadDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LPPolDB;
import com.sinosoft.lis.db.LPRemarkDB;
import com.sinosoft.lis.db.LWMissionDB;
import com.sinosoft.lis.db.LockTableDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LBMissionSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCDelTraceSchema;
import com.sinosoft.lis.schema.LWMissionSchema;
import com.sinosoft.lis.vschema.ES_DOC_MAINSet;
import com.sinosoft.lis.vschema.LACommisionSet;
import com.sinosoft.lis.vschema.LBMissionSet;
import com.sinosoft.lis.vschema.LBPolSet;
import com.sinosoft.lis.vschema.LBRemarkSet;
import com.sinosoft.lis.vschema.LCAddPolSet;
import com.sinosoft.lis.vschema.LCApplyRecallPolSet;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LCEdorReasonSet;
import com.sinosoft.lis.vschema.LWNotePadSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LPPolSet;
import com.sinosoft.lis.vschema.LPRemarkSet;
import com.sinosoft.lis.vschema.LWMissionSet;
import com.sinosoft.lis.vschema.LockTableSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.workflow.tb.TbWorkFlowUI;

/**
 * <p>Title: Web业务系统印刷号替换功能部分 </p>
 * <p>Description: 数据库功能类</p>
 * <p>Copyright: Copyright (c) 2006 </p>
 * <p>Company: Sinosoft< /p>
 * @author ck
 * @version 1.0
 */
public class DelPrtBL
{
private static Logger logger = Logger.getLogger(DelPrtBL.class);

    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();

    /** 往后面传输数据的容器 */
    private VData mInputData;

    /** 往界面传输数据的容器 */
    /** 全局数据 */
    private GlobalInput mGlobalInput = new GlobalInput();
    private TransferData mTransferData = new TransferData();
    private String PrtNo = "";
    private String ESFlag = "";
    private String CurrentDate = PubFun.getCurrentDate();
    private String CurrentTime = PubFun.getCurrentTime();
    private VData mResult = new VData();
    private LCPolSet tLCPolSet = new LCPolSet();

    /**需要替换的表*/
    private LCContSchema mLCContSchema = new LCContSchema();
    private LCDelTraceSchema mLCDelTraceSchema = new LCDelTraceSchema();
    private LCPolSet mLCPolSet = new LCPolSet();
    private LBPolSet mLBPolSet = new LBPolSet();
    private LPPolSet mLPPolSet = new LPPolSet();
    private LACommisionSet mLACommisionSet = new LACommisionSet();
    private LCEdorReasonSet mLCEdorReasonSet = new LCEdorReasonSet();
    private LCAddPolSet mLCAddPolSet = new LCAddPolSet();
    private LWNotePadSet mLWNotePadSet = new LWNotePadSet();
    private LWNotePadSet tLWNotePadSet = new LWNotePadSet();
    private LCApplyRecallPolSet mLCApplyRecallPolSet = new LCApplyRecallPolSet();
    private LPRemarkSet mLPRemarkSet = new LPRemarkSet();
    private LBRemarkSet mLBRemarkSet = new LBRemarkSet();    
    private String BPOMissionStateStr = "";
    
    private String LCAppntStr = "";
    private String LCInsuredStr = "";
    private String LCPolOriginalStr = "";
    private String lcuwreportStr = "";
    private String LDContTimeStr = "";
    private String LCCustomerImpartStr = "";

    /**要修改状态的表*/
    private LockTableSet mLockTableSet = new LockTableSet();
    private ES_DOC_MAINSet mES_DOC_MAINSet = new ES_DOC_MAINSet();
    private Hashtable hashParams = new Hashtable();
    private VData tVData = new VData();
    private String CertifyCode = "";
    
	// 存放需要备份的结点
    private LWMissionSet mLWMissionSet_Delete = new LWMissionSet();
	private LBMissionSet mLBMissionSet_Insert = new LBMissionSet();
	
	private SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
	private SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
	private SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
	private SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
	
    public DelPrtBL()
    {
    }

    /**
    * 传输数据的公共方法
    * @param: cInputData 输入的数据
    *         cOperate 数据操作
    * @return:
    */
    public boolean submitData(VData cInputData, String cOperate)
    {
        //将操作数据拷贝到本类中
        mInputData = (VData) cInputData.clone();

        //得到外部传入的数据,将数据备份到本类中
        if (!getInputData(cInputData))
        {
            return false;
        }

        //进行业务数据校验
        if (!checkData())
        {
            return false;
        }

        //进行业务处理
        if (!dealData())
        {
            return false;
        }
        
      //进行业务处理
        if (!dealWorkFlow())
        {
            return false;
        }

        logger.debug("before prepareData");

        if (!prepareData())
        {
            return false;
        }

        return true;
    }

    /**
     * 从输入数据中得到所有对象
     *输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean getInputData(VData cInputData)
    {
        //从输入数据中得到所有对象
        //获得全局公共数据
        mTransferData = (TransferData) cInputData.getObjectByObjectName("TransferData",
                                                                        0);
        PrtNo = ((String) mTransferData.getValueByName("PrtNo")).trim();
        ESFlag = ((String) mTransferData.getValueByName("ESFlag"));
        mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName("GlobalInput",
        			0));
        mInputData = cInputData;

        return true;
    }

    /**
      * 校验业务数据
      * @return
      */
    private boolean checkData()
    {
    	if(!"1".equals(ESFlag)){
	        if (mGlobalInput == null)
	        {
	            CError.buildErr(this, "请重新登录!");
	
	            return false;
	        }
	        
	        LCContDB tLCContDB = new LCContDB();
	        LCContSet tLCContSet = new LCContSet();
	        tLCContDB.setPrtNo(PrtNo);
	        tLCContSet = tLCContDB.query();
	        if(tLCContSet == null || tLCContSet.size()<1)
	        {
	        	CError.buildErr(this, "查询合同表失败！");
	        	return false;
	        }
	        mLCContSchema = tLCContSet.get(1).getSchema();
	        
	        if (mLCContSchema.getAppFlag().equals("1"))
	        {
	            CError.buildErr(this, "该投保单已经签单，不能删除!");
	
	            return false;
	        }
    	} else {
    		LCContDB tLCContDB = new LCContDB();
	        LCContSet tLCContSet = new LCContSet();
	        tLCContDB.setPrtNo(PrtNo);
	        tLCContSet = tLCContDB.query();
	        if(tLCContSet == null || tLCContSet.size()<1) {
	        	
	        }else{
	        	mLCContSchema = tLCContSet.get(1).getSchema();
	        }
    	}
        return true;
    }

    /**
    * 根据前面的输入数据，进行BL逻辑处理
    * 如果在处理过程中出错，则返回false,否则返回true
    */
    private boolean dealData()
    {  	
    	/**删除核保分析报告表*/
    	lcuwreportStr = 
        	"delete lcuwreport where otherno='"+"?PrtNo?"+"' and othernotype='1'";
    	sqlbv1.sql(lcuwreportStr);
    	sqlbv1.put("PrtNo", PrtNo);
    	
    	/**删除新契约工作时间记录表*/
    	LDContTimeStr = 
        	"delete LDContTime where prtno='"+"?PrtNo?"+"'";
    	sqlbv2.sql(LDContTimeStr);
    	sqlbv2.put("PrtNo", PrtNo);
    	
    	/**删除客户告知表*/
    	LCCustomerImpartStr = 
        	"delete LCCustomerImpart where prtno='"+"?PrtNo?"+"'";
    	sqlbv3.sql(LCCustomerImpartStr);
    	sqlbv3.put("PrtNo", PrtNo);
    	
        /**删除个人险种保单表*/
        LCPolDB tLCPolDB = new LCPolDB();
        tLCPolDB.setPrtNo(PrtNo);
        mLCPolSet = tLCPolDB.query();

        /**删除个人保单备份表*/
        LBPolDB tLBPolDB = new LBPolDB();
        tLBPolDB.setPrtNo(PrtNo);
        mLBPolSet = tLBPolDB.query();

        /**删除个人保全备份表*/
        LPPolDB tLPPolDB = new LPPolDB();
        tLPPolDB.setPrtNo(PrtNo);
        mLPPolSet = tLPPolDB.query();

        /**删除销售扎帐表*/
        LACommisionDB tLACommisionDB = new LACommisionDB();
        tLACommisionDB.setP14(PrtNo);
        mLACommisionSet = tLACommisionDB.query();

        /**删除保全批改原因表*/
        LCEdorReasonDB tLCEdorReasonDB = new LCEdorReasonDB();
        tLCEdorReasonDB.setEdorNo(PrtNo);
        tLCEdorReasonDB.setEdorType("10");
        tLCEdorReasonDB.setType("11");
        mLCEdorReasonSet = tLCEdorReasonDB.query();

        /**删除补充附加险表*/
        LCAddPolDB tLCAddPolDB = new LCAddPolDB();
        tLCAddPolDB.setPrtNo(PrtNo);
        mLCAddPolSet = tLCAddPolDB.query();

        /**删除记事本表*/
        LWNotePadDB tLWNotePadDB = new LWNotePadDB();
        tLWNotePadDB.setOtherNo(PrtNo);
        mLWNotePadSet = tLWNotePadDB.query();

        /**删除保户撤单申请表*/
        LCApplyRecallPolDB tLCApplyRecallPolDB = new LCApplyRecallPolDB();
        tLCApplyRecallPolDB.setPrtNo(PrtNo);
        mLCApplyRecallPolSet = tLCApplyRecallPolDB.query();

        /**删除保全备注表*/
        LPRemarkDB tLPRemarkDB = new LPRemarkDB();
        tLPRemarkDB.setPrtNo(PrtNo);
        mLPRemarkSet = tLPRemarkDB.query();

        /**删除保全备注B表*/
        LBRemarkDB tLBRemarkDB = new LBRemarkDB();
        tLBRemarkDB.setPrtNo(PrtNo);
        mLBRemarkSet = tLBRemarkDB.query();

        /**更新扫描件相关表*/
        ES_DOC_MAINDB tES_DOC_MAINDB = new ES_DOC_MAINDB();
        tES_DOC_MAINDB.setDocCode(PrtNo);
        mES_DOC_MAINSet = tES_DOC_MAINDB.query();
        logger.debug("PrtNo:" + PrtNo);
        logger.debug("mES_DOC_MAINSet.size():" + mES_DOC_MAINSet.size());

        for (int i = 1; i <= mES_DOC_MAINSet.size(); i++)
        {
            mES_DOC_MAINSet.get(i).setInputState("0");
            mES_DOC_MAINSet.get(i).setOperator("");
        }
        
        /**更新任务处理状态主表*/
        BPOMissionStateStr = 
        	"update BPOMissionState set state='2',remark='1' where BussNo='" +
                    "?PrtNo?" + "' and BussNoType in('TB','JM')";
        sqlbv4.sql(BPOMissionStateStr);
    	sqlbv4.put("PrtNo", PrtNo);
        
        /**删除承保锁表*/
        LockTableDB tLockTableDB = new LockTableDB();
        String strsql =
            "select * from LockTable where notype='LD' and nolimit like concat('" +
            "?PrtNo?" + "','%')";
        SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
        sqlbv5.sql(BPOMissionStateStr);
    	sqlbv5.put("PrtNo", PrtNo);
        mLockTableSet = tLockTableDB.executeQuery(sqlbv5);        
        
        mLCDelTraceSchema.setSerialNo(PubFun1.CreateMaxNo("SERIALNO",
                PubFun.getNoLimit(mGlobalInput.ManageCom)));
        mLCDelTraceSchema.setPrtNo(PrtNo);
       //mLCDelTraceSchema.setPolNo(tLCPolSet.get(1).getMainPolNo());
        mLCDelTraceSchema.setOldOperator(mLCContSchema.getOperator());
        mLCDelTraceSchema.setOperator(mGlobalInput.Operator);
        mLCDelTraceSchema.setManageCom(mGlobalInput.ManageCom);
       // tLCDelTraceDB.setRiskCode();
        if(!"1".equals(ESFlag)){
        	mLCDelTraceSchema.setRemark("投保单删除--原因是投保单录入严重错误!");
        } else {
        	mLCDelTraceSchema.setRemark("投保单删除--原因是待异常件处理或待复核抽检影像件删除!");
        }
        mLCDelTraceSchema.setMakeDate(PubFun.getCurrentDate());
        mLCDelTraceSchema.setMakeTime(PubFun.getCurrentTime());      

        return true;
    }
    
    /**
     * 替换成功后需要删除并备份当前工作流，并新增特殊投保单录入工作流
     *输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean dealWorkFlow()
    {
    	if(!"1".equals(ESFlag)){
	    	if(mLCContSchema.getCardFlag().equals("4"))
	    	{
	    		logger.debug("****************卡单不用处理工作流*******************");
	    		return true;
	    	}
    	}
    	logger.debug("****************^^^^^^^开始处理工作流^^^^^^^*******************");
    	/**删除并备份当前工作流*/
        LWMissionDB tLWMissionDB = new LWMissionDB();
        tLWMissionDB.setMissionProp1(PrtNo);
        LWMissionSet tLWMissionSet = tLWMissionDB.query();

        if (tLWMissionSet == null || tLWMissionSet.size() < 1)
        {
            logger.debug("工作流数据查询失败！");
            CError.buildErr(this, "工作流数据查询失败！");
            return false;
        }
        Reflections mReflections = new Reflections();
        String ManageCom ="86";
        
        for(int i=1; i<=tLWMissionSet.size(); i++)
        {
        	logger.debug("tLWMissionSet.size():"+tLWMissionSet.size());
        	LWMissionSchema tLWMissionSchema = new LWMissionSchema();
			LBMissionSchema tLBMissionSchema = new LBMissionSchema();
        	tLWMissionSchema = tLWMissionSet.get(i);       
        	
        	String tSerielNo = PubFun1.CreateMaxNo("MissionSerielNo", 10);
			mReflections.transFields(tLBMissionSchema,
					tLWMissionSchema);
			tLBMissionSchema.setSerialNo(tSerielNo);
			tLBMissionSchema.setActivityStatus("3"); // 节点任务执行完毕
			tLBMissionSchema.setLastOperator(mGlobalInput.Operator);
			tLBMissionSchema.setMakeDate(CurrentDate);
			tLBMissionSchema.setMakeTime(CurrentTime);
			this.mLWMissionSet_Delete.add(tLWMissionSchema);
			this.mLBMissionSet_Insert.add(tLBMissionSchema);
        	
        }
        
        String tOperator = mGlobalInput.Operator;
        if(!"1".equals(ESFlag)){
	        if((tOperator!=null && !tOperator.equals("") && tOperator.equals("AUTO")))
	        {  
	        	logger.debug("****************自动删除，不用生成特殊投保单录入工作流*******************");
	        }
	        else
	        {
	        	//新增特殊投保单录入工作流
	            TransferData tTransferData = new TransferData();
	            tTransferData.setNameAndValue("PrtNo", PrtNo);
	            tTransferData.setNameAndValue("ContNo", PrtNo);
	            tTransferData.setNameAndValue("ManageCom", ManageCom);
	            tTransferData.setNameAndValue("Operator", mGlobalInput.Operator);
	            tTransferData.setNameAndValue("BussNoType", "");
	            tTransferData.setNameAndValue("DealType", "");
	            tTransferData.setNameAndValue("State", "");
	            tTransferData.setNameAndValue("BPOID", "");
	            tTransferData.setNameAndValue("AppntNo", "");
	            tTransferData.setNameAndValue("AppntName", "");
	            tTransferData.setNameAndValue("InsuredNo", "");
	            tTransferData.setNameAndValue("InsuredName", "");
	            tTransferData.setNameAndValue("AgentCode", "");
	            tTransferData.setNameAndValue("RiskCode", "");
	            tTransferData.setNameAndValue("DefaultOperator", "");
	            tTransferData.setNameAndValue("ScanMakeDate", "");
	            tTransferData.setNameAndValue("ScanMakeTime", "");
	    		tTransferData.setNameAndValue("SubType", "TB1001");
	            VData tVData = new VData();
	            tVData.add(mGlobalInput);
	            tVData.add(tTransferData);
	            TbWorkFlowUI tTbWorkFlowUI = new TbWorkFlowUI();
	            if(!tTbWorkFlowUI.submitData(tVData, "7199999910"))
	            {
	            	CError.buildErr(this, "新增特殊投保单录入工作流失败！");
	            	return false;
	            }
	        }
        }        
        
        logger.debug("****************^^^^^^^工作流处理完毕^^^^^^^*******************");

        return true;
    }

    /**
     * 打印信息表
     * @return
     */
    private boolean prepareData()
    {
    	logger.debug("****************^^^^^^^prepareData start^^^^^^^*******************");
        MMap tMMap = new MMap();
        String ContNo = mLCContSchema.getContNo();
        //删除外包导入相关表 ln 2009-2-16 add
        SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
        sqlbv6.sql("delete from bpopoldata where bussno='" + "?bussno?" + "'");
        sqlbv6.put("bussno", PrtNo);
        tMMap.put(sqlbv6,
				"DELETE");
        SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
        sqlbv7.sql("delete from bpomissiondetailstate where bussno='" + "?bussno?" + "'");
        sqlbv7.put("bussno", PrtNo);
        tMMap.put(sqlbv7,
				"DELETE");
        SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
        sqlbv8.sql("delete from bpomissiondetailerror where bussno='" + "?bussno?" + "'");
        sqlbv8.put("bussno", PrtNo);
        tMMap.put(sqlbv8,
				"DELETE");
        SQLwithBindVariables sqlbv9 = new SQLwithBindVariables();
        sqlbv9.sql("delete from bpomissionstate where bussno='" + "?bussno?" + "'");
        sqlbv9.put("bussno", PrtNo);
        tMMap.put(sqlbv9,
				"DELETE");
        SQLwithBindVariables sqlbv10 = new SQLwithBindVariables();
        sqlbv10.sql("delete from BPOErrLog where bussno='" + "?bussno?" + "'");
        sqlbv10.put("bussno", PrtNo);
        tMMap.put(sqlbv10,
				"DELETE");
        
      //删除双岗导入相关表 ln 2009-3-2 add
        SQLwithBindVariables sqlbv11 = new SQLwithBindVariables();
        sqlbv11.sql("delete from lbpocont where ContNo='" + "?ContNo?" + "'");
        sqlbv11.put("ContNo", ContNo);
        tMMap.put(sqlbv11,
				"DELETE");
        SQLwithBindVariables sqlbv12 = new SQLwithBindVariables();
        sqlbv12.sql("delete from lbpoappnt where ContNo='" + "?ContNo?" + "'");
        sqlbv12.put("ContNo", ContNo);
        tMMap.put(sqlbv12,
				"DELETE");
        SQLwithBindVariables sqlbv13 = new SQLwithBindVariables();
        sqlbv13.sql("delete from lbpoinsured where ContNo='" + "?ContNo?" + "'");
        sqlbv13.put("ContNo", ContNo);
        tMMap.put(sqlbv13,
				"DELETE");
        SQLwithBindVariables sqlbv14 = new SQLwithBindVariables();
        sqlbv14.sql("delete from lbpobnf where ContNo='" + "?ContNo?" + "'");
        sqlbv14.put("ContNo", ContNo);
        tMMap.put(sqlbv14,
				"DELETE");
        SQLwithBindVariables sqlbv15 = new SQLwithBindVariables();
        sqlbv15.sql("delete from lbpopol where ContNo='" + "?ContNo?" + "'");
        sqlbv15.put("ContNo", ContNo);
        tMMap.put(sqlbv15,
				"DELETE");
        SQLwithBindVariables sqlbv16 = new SQLwithBindVariables();
        sqlbv16.sql("delete from lbpocustomerimpart where ContNo='" + "?ContNo?" + "'");
        sqlbv16.put("ContNo", ContNo);
        tMMap.put(sqlbv16,
		"DELETE");
        SQLwithBindVariables sqlbv17 = new SQLwithBindVariables();
        sqlbv17.sql("delete from lbpocustomerimpartdetail where ContNo='" + "?ContNo?" + "'");
        sqlbv17.put("ContNo", ContNo);
		tMMap.put(sqlbv17,
				"DELETE");
		SQLwithBindVariables sqlbv18 = new SQLwithBindVariables();
        sqlbv18.sql("delete from lbpoperinvestplan where ContNo='" + "?ContNo?" + "'");
        sqlbv18.put("ContNo", ContNo);
		tMMap.put(sqlbv18,
				"DELETE");
		SQLwithBindVariables sqlbv19 = new SQLwithBindVariables();
        sqlbv19.sql("delete from lbpoinsuredrelated where PolNo in (select polno from lbpopol where ContNo='" + "?ContNo?" + "')");
        sqlbv19.put("ContNo", ContNo);
		tMMap.put(sqlbv19,
				"DELETE");
        
        //删除合同表
		SQLwithBindVariables sqlbv20 = new SQLwithBindVariables();
        sqlbv20.sql("delete from LCCont where ContNo='" + "?ContNo?" + "'");
        sqlbv20.put("ContNo", ContNo);
        tMMap.put(sqlbv20,
				"DELETE");
        //投保人表
        SQLwithBindVariables sqlbv21 = new SQLwithBindVariables();
        sqlbv21.sql("delete from LCAppnt where ContNo='" + "?ContNo?" + "'");
        sqlbv21.put("ContNo", ContNo);
		tMMap.put(sqlbv21,
				"DELETE");
		//被保人表
		SQLwithBindVariables sqlbv22 = new SQLwithBindVariables();
	    sqlbv22.sql("delete from LCInsured where ContNo='" + "?ContNo?" + "'");
	    sqlbv22.put("ContNo", ContNo);
		tMMap.put(sqlbv22,
				"DELETE");
		SQLwithBindVariables sqlbv23 = new SQLwithBindVariables();
	    sqlbv23.sql("delete from LCInsureAcc where ContNo='" + "?ContNo?" + "'");
	    sqlbv23.put("ContNo", ContNo);
		tMMap.put(sqlbv23,
				"DELETE");
		SQLwithBindVariables sqlbv24 = new SQLwithBindVariables();
	    sqlbv24.sql("delete from LCInsureAccClass where ContNo='" + "?ContNo?" + "'");
	    sqlbv24.put("ContNo", ContNo);
		tMMap.put(sqlbv24, "DELETE");
		SQLwithBindVariables sqlbv25 = new SQLwithBindVariables();
	    sqlbv25.sql("delete from LCInsureAccTrace where ContNo='" + "?ContNo?" + "'");
	    sqlbv25.put("ContNo", ContNo);
		tMMap.put(sqlbv25, "DELETE");
		SQLwithBindVariables sqlbv26 = new SQLwithBindVariables();
	    sqlbv26.sql("delete from LCInsureAccFee where ContNo='" + "?ContNo?" + "'");
	    sqlbv26.put("ContNo", ContNo);
		tMMap.put(sqlbv26, "DELETE");
		SQLwithBindVariables sqlbv27 = new SQLwithBindVariables();
	    sqlbv27.sql("delete from LCInsureAccClassFee where ContNo='" + "?ContNo?" + "'");
	    sqlbv27.put("ContNo", ContNo);
		tMMap.put(sqlbv27, "DELETE");
		SQLwithBindVariables sqlbv28 = new SQLwithBindVariables();
	    sqlbv28.sql("delete from LCPol where ContNo='" + "?ContNo?" + "'");
	    sqlbv28.put("ContNo", ContNo);
		tMMap.put(sqlbv28,
				"DELETE");
		//删除投保单原始数据
		SQLwithBindVariables sqlbv29 = new SQLwithBindVariables();
	    sqlbv29.sql("delete from LCPolOriginal where ContNo='" + "?ContNo?" + "'");
	    sqlbv29.put("ContNo", ContNo);
		tMMap.put(sqlbv29,
		"DELETE");
		SQLwithBindVariables sqlbv30 = new SQLwithBindVariables();
	    sqlbv30.sql("delete from LCDuty where ContNo='" + "?ContNo?" + "'");
	    sqlbv30.put("ContNo", ContNo);
		tMMap.put(sqlbv30,
				"DELETE");
		SQLwithBindVariables sqlbv31 = new SQLwithBindVariables();
	    sqlbv31.sql("delete from LCPrem where ContNo='" + "?ContNo?" + "'");
	    sqlbv31.put("ContNo", ContNo);
		tMMap.put(sqlbv31,
				"DELETE");
		SQLwithBindVariables sqlbv32 = new SQLwithBindVariables();
	    sqlbv32.sql("delete from LCGet where ContNo='" + "?ContNo?" + "'");
	    sqlbv32.put("ContNo", ContNo);
		tMMap.put(sqlbv32,
				"DELETE");
		SQLwithBindVariables sqlbv33 = new SQLwithBindVariables();
	    sqlbv33.sql("delete from LCBnf where ContNo='" + "?ContNo?" + "'");
	    sqlbv33.put("ContNo", ContNo);
		tMMap.put(sqlbv33,
				"DELETE");
		SQLwithBindVariables sqlbv34 = new SQLwithBindVariables();
	    sqlbv34.sql("delete from LCCustomerImpart where ContNo='" + "?ContNo?" + "'");
	    sqlbv34.put("ContNo", ContNo);
		tMMap.put(sqlbv34, "DELETE");
		SQLwithBindVariables sqlbv35 = new SQLwithBindVariables();
	    sqlbv35.sql("delete from LCCustomerImpartParams where ContNo='" + "?ContNo?" + "'");
	    sqlbv35.put("ContNo", ContNo);
		tMMap.put(sqlbv35, "DELETE");
		SQLwithBindVariables sqlbv36 = new SQLwithBindVariables();
	    sqlbv36.sql("delete from LCCustomerImpartDetail where ContNo='" + "?ContNo?" + "'");
	    sqlbv36.put("ContNo", ContNo);
		tMMap.put(sqlbv36, "DELETE");
		SQLwithBindVariables sqlbv37 = new SQLwithBindVariables();
	    sqlbv37.sql("delete from LACommisionDetail where GrpContNo='"
				+ "?PrtNo?" + "' and agentcode='"+"?agentcode?"+"'");
	    sqlbv37.put("agentcode", mLCContSchema.getAgentCode());
	    sqlbv37.put("PrtNo", PrtNo);
		tMMap.put(sqlbv37, "DELETE");		
		SQLwithBindVariables sqlbv38 = new SQLwithBindVariables();
	    sqlbv38.sql("delete from LCCSpec where ContNo='" + "?ContNo?" + "'");
	    sqlbv38.put("ContNo", ContNo);
		tMMap.put(sqlbv38, "DELETE");
		//删除核保相关表
		tMMap.put("delete from LCCUWMaster where ContNo='"
				+ "?ContNo?" + "'", "DELETE");
		SQLwithBindVariables sqlbv39 = new SQLwithBindVariables();
	    sqlbv39.sql("delete from LCCUWSub where ContNo='" + "?ContNo?" + "'");
	    sqlbv39.put("ContNo", ContNo);
		tMMap.put(sqlbv39, "DELETE");
		SQLwithBindVariables sqlbv40 = new SQLwithBindVariables();
	    sqlbv40.sql("delete from LCCUWError where ContNo='" + "?ContNo?" + "'");
	    sqlbv40.put("ContNo", ContNo);
		tMMap.put(sqlbv40, "DELETE");
		SQLwithBindVariables sqlbv41 = new SQLwithBindVariables();
	    sqlbv41.sql("delete from LCUWMaster where ContNo='" + "?ContNo?" + "'");
	    sqlbv41.put("ContNo", ContNo);
		tMMap.put(sqlbv41, "DELETE");
		SQLwithBindVariables sqlbv42 = new SQLwithBindVariables();
	    sqlbv42.sql("delete from LCUWSub where ContNo='" + "?ContNo?" + "'");
	    sqlbv42.put("ContNo", ContNo);
		tMMap.put(sqlbv42, "DELETE");
		SQLwithBindVariables sqlbv43 = new SQLwithBindVariables();
	    sqlbv43.sql("delete from LCUWError where ContNo='" + "?ContNo?" + "'");
	    sqlbv43.put("ContNo", ContNo);
		tMMap.put(sqlbv43, "DELETE");
		SQLwithBindVariables sqlbv44 = new SQLwithBindVariables();
	    sqlbv44.sql("delete from LCIndUWMaster where ContNo='" + "?ContNo?" + "'");
	    sqlbv44.put("ContNo", ContNo);
		tMMap.put(sqlbv44, "DELETE");
		SQLwithBindVariables sqlbv45 = new SQLwithBindVariables();
	    sqlbv45.sql("delete from LCIndUWSub where ContNo='" + "?ContNo?" + "'");
	    sqlbv45.put("ContNo", ContNo);
		tMMap.put(sqlbv45, "DELETE");
		SQLwithBindVariables sqlbv46 = new SQLwithBindVariables();
	    sqlbv46.sql("delete from LCIndUWError where ContNo='" + "?ContNo?" + "'");
	    sqlbv46.put("ContNo", ContNo);
		tMMap.put(sqlbv46, "DELETE");
		
		//2009-07-24 ln add --删除辅助核保相关表
		SQLwithBindVariables sqlbv47 = new SQLwithBindVariables();
	    sqlbv47.sql("delete from lcpenotice where ContNo='" + "?ContNo?" + "'");
	    sqlbv47.put("ContNo", ContNo);
		tMMap.put(sqlbv47, "DELETE");
		SQLwithBindVariables sqlbv48 = new SQLwithBindVariables();
	    sqlbv48.sql("delete from lcpenoticeitem where ContNo='" + "?ContNo?" + "'");
	    sqlbv48.put("ContNo", ContNo);
		tMMap.put(sqlbv48, "DELETE");
		SQLwithBindVariables sqlbv49 = new SQLwithBindVariables();
	    sqlbv49.sql("delete from lcpenoticereply where ContNo='" + "?ContNo?" + "'");
	    sqlbv49.put("ContNo", ContNo);
		tMMap.put(sqlbv49, "DELETE");
		SQLwithBindVariables sqlbv50 = new SQLwithBindVariables();
	    sqlbv50.sql("delete from lcpenoticeresult where ContNo='" + "?ContNo?" + "'");
	    sqlbv50.put("ContNo", ContNo);
		tMMap.put(sqlbv50, "DELETE");
		SQLwithBindVariables sqlbv51 = new SQLwithBindVariables();
	    sqlbv51.sql("delete from lcrreport where ContNo='" + "?ContNo?" + "'");
	    sqlbv51.put("ContNo", ContNo);
		tMMap.put(sqlbv51, "DELETE");
		SQLwithBindVariables sqlbv52 = new SQLwithBindVariables();
	    sqlbv52.sql("delete from lcissuepol where ContNo='" + "?ContNo?" + "'");
	    sqlbv52.put("ContNo", ContNo);
		tMMap.put(sqlbv52, "DELETE");
		
        logger.debug("mLBPolSet:" + mLBPolSet.size());
        tMMap.put(mLBPolSet, "DELETE");
        logger.debug("mLPPolSet:" + mLPPolSet.size());
        tMMap.put(mLPPolSet, "DELETE");
        logger.debug("mLACommisionSet:" + mLACommisionSet.size());
        tMMap.put(mLACommisionSet, "DELETE");
        logger.debug("mLCEdorReasonSet:" + mLCEdorReasonSet.size());
        //tMMap.put("delete from LCEdorReason where edorno='" + PrtNo +
        //          "' and edortype='10' and type='11'", "DELETE");
        tMMap.put(mLCEdorReasonSet, "UPDATE");
        //tMMap.put(mLCEdorReasonSet, "INSERT");
        logger.debug("mLCAddPolSet:" + mLCAddPolSet.size());
        tMMap.put(mLCAddPolSet, "DELETE");
        logger.debug("mLWNotePadSet:" + mLWNotePadSet.size());
        tMMap.put(mLWNotePadSet, "DELETE");
       // tMMap.put(tLWNotePadSet, "INSERT");
        logger.debug("mLCApplyRecallPolSet:" +
                           mLCApplyRecallPolSet.size());
        tMMap.put(mLCApplyRecallPolSet, "DELETE");
        logger.debug("mLPRemarkSet:" + mLPRemarkSet.size());
        tMMap.put(mLPRemarkSet, "DELETE");
        logger.debug("mLBRemarkSet:" + mLBRemarkSet.size());
        tMMap.put(mLBRemarkSet, "DELETE");
        logger.debug("mES_DOC_MAINSet:" + mES_DOC_MAINSet.size());
        tMMap.put(mES_DOC_MAINSet, "UPDATE");
        logger.debug("BPOMissionState DELETE sql:" + BPOMissionStateStr);
        tMMap.put(sqlbv4,"UPDATE");
        tMMap.put(sqlbv1,"DELETE");
        tMMap.put(sqlbv2,"DELETE");        
        
        
        //删除工作流数据
        if(mLWMissionSet_Delete!=null)
        {
        	logger.debug("mLWMissionSet_Delete:" + mLWMissionSet_Delete.size());
        	tMMap.put(mLWMissionSet_Delete,"DELETE");
        }
        if(mLBMissionSet_Insert!=null)
        {
        	logger.debug("mLBMissionSet_Insert:" + mLBMissionSet_Insert.size());
        	tMMap.put(mLBMissionSet_Insert,"INSERT");
        }        	      

        if (mLockTableSet.size() > 0)
        {
            tMMap.put(mLockTableSet, "DELETE");
        }
        
        tMMap.put(mLCDelTraceSchema,"INSERT");

        mInputData.add(tMMap);

        PubSubmit tPubSubmit = new PubSubmit();
        if(!"1".equals(ESFlag)){
	        if (!tPubSubmit.submitData(mInputData, "DelPrt"))
	        {
	            logger.debug("tPubSubmit.submitData错误 ");
	            this.mErrors.copyAllErrors(tPubSubmit.mErrors);
	            this.mErrors.addOneError("操作数据库出现错误!");
	
	            return false;
	        } 
        }
        logger.debug("****************^^^^^^^prepareData end^^^^^^^*******************");

        return true;
    }

    public VData getResult()
    {
    	mResult.clear();
    	mResult = (VData) mInputData.clone();
        return mResult;
    }
}
