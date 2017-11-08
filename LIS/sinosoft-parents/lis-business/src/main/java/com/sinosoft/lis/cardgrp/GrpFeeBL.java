/**
 * Copyright ? 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.cardgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCGrpPolDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.vschema.LCGrpFeeParamSet;
import com.sinosoft.lis.vschema.LCGrpFeeSet;
import com.sinosoft.lis.vschema.LCGrpFeeTraceSet;
import com.sinosoft.lis.vschema.LCGrpFeeParamTraceSet;
import com.sinosoft.lis.vschema.LCGrpFeeParamSet;
import com.sinosoft.lis.schema.LCGrpFeeSchema;
import com.sinosoft.lis.schema.LCGrpFeeParamTraceSchema;
import com.sinosoft.lis.schema.LCGrpFeeTraceSchema;
import com.sinosoft.lis.schema.LCGrpFeeParamSchema;
import com.sinosoft.lis.db.LCGrpFeeDB;
import com.sinosoft.lis.db.LCGrpFeeParamDB;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.Reflections;
import com.sinosoft.lis.pubfun.PubFun1;


/**
 * 管理费数据校验、准备类
 * <p>Title: </p>
 * <p>Description: 通过前台传入的数据信息，做一定的校验和处理</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class GrpFeeBL {
private static Logger logger = Logger.getLogger(GrpFeeBL.class);
    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();
    private VData mResult = new VData();


    /** 往后面传输数据的容器 */
    private VData mInputData;


    /** 全局数据 */
    private GlobalInput mGlobalInput = new GlobalInput();
    private Reflections tReflections = new Reflections();

    /** 数据操作字符串 */
    private String mOperate;


    /** 业务处理相关变量 */
    private LCGrpFeeSet mLCGrpFeeSet = new LCGrpFeeSet();
    private LCGrpFeeParamSet mLCGrpFeeParamSet = new LCGrpFeeParamSet();
    private LCGrpFeeTraceSet mLCGrpFeeTraceSet = new LCGrpFeeTraceSet();
    private LCGrpFeeParamTraceSet mLCGrpFeeParamTraceSet = new
            LCGrpFeeParamTraceSet();
    private LCGrpFeeTraceSchema mLCGrpFeeTraceSchema = new LCGrpFeeTraceSchema();

    /** 时间信息*/
    String mCurrentDate = PubFun.getCurrentDate(); //当前值
    String mCurrentTime = PubFun.getCurrentTime();
    String tSerialNo =null;
    public GrpFeeBL() {
    }

    /**
     * 传输数据的公共方法
     * @param cInputData VData
     * @param cOperate String
     * @return boolean
     */
    public boolean submitData(VData cInputData, String cOperate) {
        //将操作数据拷贝到本类中
        this.mOperate = cOperate;
        //得到外部传入的数据,将数据备份到本类中
        if (!getInputData(cInputData))
            return false;
        //进行业务处理
        if (!dealData()) {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "GrpFeeBL";
            tError.functionName = "submitData";
            tError.errorMessage = "数据处理失败GrpFeeBL-->dealData!";
            this.mErrors.addOneError(tError);
            return false;
        }
        //准备往后台的数据
        if (!prepareOutputData())
            return false;
        if (this.mOperate.equals("QUERY||MAIN")) {
            this.submitquery();
        } else {
            logger.debug("Start GrpFeeBL Submit...");
            GrpFeeBLS tGrpFeeBLS = new GrpFeeBLS();
            tGrpFeeBLS.submitData(mInputData, cOperate);
            logger.debug("End GrpFeeBL Submit...");
            //如果有需要处理的错误，则返回
            if (tGrpFeeBLS.mErrors.needDealError()) {
                // @@错误处理
                this.mErrors.copyAllErrors(tGrpFeeBLS.mErrors);
                CError tError = new CError();
                tError.moduleName = "GrpFeeBL";
                tError.functionName = "submitDat";
                tError.errorMessage = "数据提交失败!";
                this.mErrors.addOneError(tError);
                return false;
            }
        }
        mInputData = null;
        return true;
    }

    /**
     * 根据传入操作类型，进行数据准备
     * @return boolean
     * 如果在处理过程中出错，则返回false,否则返回true
     */
    private boolean dealData() {
        boolean tReturn = true;
        if (this.mOperate.compareTo("INSERT||MAIN") == 0) 
        {
            //备份fee
	        	for (int j=1;j<=mLCGrpFeeSet.size();j++)
	         {

	            //查询合同单号，险种代码
	            LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
	            tLCGrpPolDB.setGrpPolNo(this.mLCGrpFeeSet.get(1).getGrpPolNo());
	            tLCGrpPolDB.getInfo();
	
	            //放入管理费主表中，由于一次实际提交的管理费主表记录为1，所以可以如此处理
	            this.mLCGrpFeeSet.get(j).setGrpContNo(tLCGrpPolDB.getGrpContNo());
	            this.mLCGrpFeeSet.get(j).setRiskCode(tLCGrpPolDB.getRiskCode());
	            this.mLCGrpFeeSet.get(j).setOperator(this.mGlobalInput.Operator); //operator
	            this.mLCGrpFeeSet.get(j).setMakeDate(mCurrentDate); //makedate
	            this.mLCGrpFeeSet.get(j).setMakeTime(mCurrentTime); //maketime
	            this.mLCGrpFeeSet.get(j).setModifyDate(mCurrentDate); //modifydate
	            this.mLCGrpFeeSet.get(j).setModifyTime(mCurrentTime); //modifytime
		           // LCGrpFeeSet tLCGrpFeeSet = new LCGrpFeeSet();
	           // LCGrpFeeSchema tLCGrpFeeSchema = new LCGrpFeeSchema();
	            //LCGrpFeeDB tLCGrpFeeDB = new LCGrpFeeDB();
	           // tLCGrpFeeDB.setGrpPolNo(mLCGrpFeeSet.get(j).getGrpPolNo());
	           // tLCGrpFeeDB.setPayPlanCode(mLCGrpFeeSet.get(j).getPayPlanCode());
	            //tLCGrpFeeDB.setInsuAccNo(mLCGrpFeeSet.get(j).getInsuAccNo());
	            //tLCGrpFeeDB.setFeeCode(mLCGrpFeeSet.get(j).getFeeCode());
	            //tLCGrpFeeSet.set(tLCGrpFeeDB.query());
	            //if(tLCGrpFeeSet.size()>0)
		       // {
		            //tLCGrpFeeSchema = mLCGrpFeeSet.get(j);
	                mLCGrpFeeTraceSchema=new LCGrpFeeTraceSchema();
		            tReflections.transFields(mLCGrpFeeTraceSchema, mLCGrpFeeSet.get(j));
		            tSerialNo=PubFun1.CreateMaxNo("GrpFeeBackNo",20);
		            mLCGrpFeeTraceSchema.setSerialNo(tSerialNo);
		            mLCGrpFeeTraceSchema.setOperator(this.mGlobalInput.Operator);
		            mLCGrpFeeTraceSchema.setMakeDate(mCurrentDate);
		            mLCGrpFeeTraceSchema.setMakeTime(mCurrentTime);
		            mLCGrpFeeTraceSchema.setModifyDate(mCurrentDate);
		            mLCGrpFeeTraceSchema.setModifyTime(mCurrentTime);
		            
		            mLCGrpFeeTraceSet.add(mLCGrpFeeTraceSchema);
		       // }
	            //查看管理费子表中，是否有数据信息
	            for (int i = 1; i <= this.mLCGrpFeeParamSet.size(); i++) {
	                logger.debug("come in ...");
	                //备份feeparam
	                    LCGrpFeeParamSet tLCGrpFeeParamSet = new LCGrpFeeParamSet();
	                    LCGrpFeeParamDB tLCGrpFeeParamDB = new LCGrpFeeParamDB();
	                    LCGrpFeeParamTraceSchema tLCGrpFeeParamTraceSchema = new LCGrpFeeParamTraceSchema();
	                    LCGrpFeeParamSchema tLCGrpFeeParamSchema = new LCGrpFeeParamSchema();
	                    tLCGrpFeeParamDB.setGrpPolNo(mLCGrpFeeParamSet.get(i).
	                                                 getGrpPolNo());
	                    tLCGrpFeeParamDB.setPayPlanCode(mLCGrpFeeSet.get(1).
	                            getPayPlanCode());
	                    tLCGrpFeeParamDB.setInsuAccNo(mLCGrpFeeSet.get(1).
	                                                  getInsuAccNo());
	                    tLCGrpFeeParamDB.setFeeCode(mLCGrpFeeSet.get(1).
	                                                getFeeCode());
	                    tLCGrpFeeParamDB.setFeeID(mLCGrpFeeParamSet.get(i).getFeeID());
	                    tLCGrpFeeParamSet.set(tLCGrpFeeParamDB.query());
	                    if(tLCGrpFeeParamSet!=null  && tLCGrpFeeParamSet.size()>0)
	                    {
	                        tLCGrpFeeParamSchema = tLCGrpFeeParamSet.get(1);
	                        tReflections.transFields(tLCGrpFeeParamTraceSchema,
	                                                 tLCGrpFeeParamSchema);
	                        tLCGrpFeeParamTraceSchema.setSerialNo(tSerialNo);
	                        tLCGrpFeeParamTraceSchema.setFeeID(mLCGrpFeeParamSet.
	                                get(i).getFeeID());
	                        tLCGrpFeeParamTraceSchema.setOperator(this.mGlobalInput.
	                                Operator);
	                        tLCGrpFeeParamTraceSchema.setMakeDate(mCurrentDate);
	                        tLCGrpFeeParamTraceSchema.setMakeTime(mCurrentTime);
	                        tLCGrpFeeParamTraceSchema.setModifyDate(mCurrentDate);
	                        tLCGrpFeeParamTraceSchema.setModifyTime(mCurrentTime);
	                        mLCGrpFeeParamTraceSet.add(tLCGrpFeeParamTraceSchema);
	                    }
	                this.mLCGrpFeeParamSet.get(i).setGrpContNo(tLCGrpPolDB.
	                        getGrpContNo());
	                this.mLCGrpFeeParamSet.get(i).setRiskCode(tLCGrpPolDB.
	                        getRiskCode());
	                this.mLCGrpFeeParamSet.get(i).setFeeCode(this.mLCGrpFeeSet.get(
	                        1).getFeeCode());
	                this.mLCGrpFeeParamSet.get(i).setInsuAccNo(this.mLCGrpFeeSet.
	                        get(1).getInsuAccNo());
	                this.mLCGrpFeeParamSet.get(i).setPayPlanCode(this.mLCGrpFeeSet.
	                        get(1).getPayPlanCode());
	
	                this.mLCGrpFeeParamSet.get(i).setOperator(this.mGlobalInput.
	                        Operator); //operator
	                this.mLCGrpFeeParamSet.get(i).setMakeDate(mCurrentDate); //makedate
	                this.mLCGrpFeeParamSet.get(i).setMakeTime(mCurrentTime); //maketime
	                this.mLCGrpFeeParamSet.get(i).setModifyDate(mCurrentDate); //modifydate
	                this.mLCGrpFeeParamSet.get(i).setModifyTime(mCurrentTime); //modifytime
	            }
	        }
        }
        tReturn = true;
        return tReturn;
    }

    /**
     * 从输入数据中得到所有对象
     * @param cInputData VData
     * @return boolean
     * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean getInputData(VData cInputData) {
        this.mGlobalInput.setSchema((GlobalInput) cInputData.
                                    getObjectByObjectName(
                                            "GlobalInput", 0));
        this.mLCGrpFeeSet.set((LCGrpFeeSet) cInputData.
                              getObjectByObjectName("LCGrpFeeSet", 0));
        this.mLCGrpFeeParamSet.set((LCGrpFeeParamSet) cInputData.
                                   getObjectByObjectName(
                                           "LCGrpFeeParamSet", 0));
        logger.debug("mLCGrpFeeParamSet size is " +
                           mLCGrpFeeParamSet.size());
        return true;
    }

    /**
     * 查询操作
     * @return boolean
     */
    private boolean submitquery() {
        mInputData = null;
        return true;
    }

    /**
     * 放后台准备数据
     * @return boolean
     * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean prepareOutputData() {
        try {
            this.mInputData = new VData();
            this.mInputData.add(this.mGlobalInput);
            this.mInputData.add(this.mLCGrpFeeSet);
            this.mInputData.add(this.mLCGrpFeeParamSet);

            this.mInputData.add(this.mLCGrpFeeTraceSet);//备份数据
            this.mInputData.add(this.mLCGrpFeeParamTraceSet);//备份数据
        } catch (Exception ex) {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "GrpFeeBL";
            tError.functionName = "prepareData";
            tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
            this.mErrors.addOneError(tError);
            return false;
        }
        return true;
    }

    public VData getResult() {
        return this.mResult;
    }
}
