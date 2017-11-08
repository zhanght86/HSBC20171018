package com.sinosoft.lis.claimgrp;
import org.apache.log4j.Logger;

import java.util.*;

import com.sinosoft.lis.bl.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vbl.*;
import com.sinosoft.lis.vdb.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;


/**
 * <p>Title:团单整单删除UI层 </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: sinosoft</p>
 * @author zhangrong
 * @version 1.0
 */
public class ClaimScanDeleteBL {
private static Logger logger = Logger.getLogger(ClaimScanDeleteBL.class);
    /** 传入数据的容器 */
    private VData mInputData = new VData();

    /** 往前面传输数据的容器 */
    private VData mResult = new VData();

    /**传输到后台处理的map*/
    private MMap mMap = new MMap();

    /** 数据操作字符串 */
    private String mOperate;
    private String mOperator;
    private String mManageCom;

    /** 错误处理类 */
    public CErrors mErrors = new CErrors();

    /**业务处理相关变量*/
    private GlobalInput mGlobalInput = new GlobalInput();
    private LLClaimSchema mLLClaimSchema = new LLClaimSchema();
    private LCDelPolLogSchema mLCDelPolLog = new LCDelPolLogSchema();

    //统一更新日期，时间
    private String theCurrentDate = PubFun.getCurrentDate();
    private String theCurrentTime = PubFun.getCurrentTime();

    public ClaimScanDeleteBL() {
    }

    /**
     * 处理实际的业务逻辑。
     * @param cInputData VData   从前台接收的表单数据
     * @param cOperate String    操作字符串
     * @return boolean
     */
    public boolean submitData(VData cInputData, String cOperate) {
        //将数据取到本类变量中
        mInputData = (VData) cInputData.clone();
        this.mOperate = cOperate;

        //将VData数据还原成业务需要的类
        if (this.getInputData() == false) {
            return false;
        }

        if (this.dealData() == false) {
            return false;
        }

        // 装配处理好的数据，准备给后台进行保存
        this.prepareOutputData();

        PubSubmit tPubSubmit = new PubSubmit();

        if (!tPubSubmit.submitData(mResult, cOperate)) {
            // @@错误处理
            this.mErrors.copyAllErrors(tPubSubmit.mErrors);

            return false;
        }

        return true;
    }

    /**
     * 将UI层传输来得数据根据业务还原成具体的类
     * @return boolean
     */
    private boolean getInputData() {
        //全局变量实例
        mGlobalInput.setSchema((GlobalInput) mInputData.getObjectByObjectName(
                "GlobalInput", 0));

        if (mGlobalInput == null) {
            mErrors.addOneError(new CError("没有得到全局量信息"));

            return false;
        }

        mOperator = mGlobalInput.Operator;
        mManageCom = mGlobalInput.ManageCom;

        //团体保单实例
        mLLClaimSchema.setSchema((LLClaimSchema) mInputData.
                                   getObjectByObjectName("LLClaimSchema", 0));

        if (mLLClaimSchema == null) {
            this.mErrors.addOneError(new CError("传入的团单信息为空！"));

            return false;
        }

        return true;
    }

    /**
     * 对业务数据进行加工
     * 对于新增的操作，这里需要有生成新合同号和新客户号的操作。
     * @return boolean
     */
    private boolean dealData() {
        String tClaimNo = mLLClaimSchema.getClmNo();
        String tInsuredNo = mLLClaimSchema.getCaseNo();
        String tScanType = mLLClaimSchema.getGetDutyKind();
        //如果未录入合同信息则直接删除
        String aSQL="";
        String bSQL="";
        String cSQL="";
        aSQL="delete from es_doc_main where doccode='" + tClaimNo + "'";
        bSQL="delete from es_doc_pages where docid in (select docid from es_doc_main where doccode='" + tClaimNo + "')";
        cSQL="delete from es_doc_relation where bussno='" + tClaimNo + "'";

        if ((tInsuredNo!=null&&!tInsuredNo.equals(""))&&(tScanType!=null&&!tScanType.equals("0000")&&!tScanType.equals("")&&tScanType!=""))
        {
            aSQL=aSQL+" and printcode like '"+tInsuredNo+"%' and subtype='"+tScanType+"' ";
            bSQL="delete from es_doc_pages where docid in (select docid from es_doc_main where doccode='" + tClaimNo + "' and printcode like '"+tInsuredNo+"%' and subtype='"+tScanType+"' ) ";
            cSQL="delete from es_doc_relation where docid in (select docid from es_doc_main where doccode='" + tClaimNo + "' and printcode like '"+tInsuredNo+"%' and subtype='"+tScanType+"' ) ";
        }else if ((tInsuredNo!=null&&!tInsuredNo.equals(""))&&(tScanType==null||tScanType.equals("0000")||tScanType.equals("")||tScanType==""))
        {
            aSQL=aSQL+" and printcode like '"+tInsuredNo+"%' ";
            bSQL="delete from es_doc_pages where docid in (select docid from es_doc_main where doccode='" + tClaimNo + "'  and printcode like '"+tInsuredNo+"%' ) ";
            cSQL="delete from es_doc_relation where docid in (select docid from es_doc_main where doccode='" + tClaimNo + "'  and printcode like '"+tInsuredNo+"%' )  ";
        }else if ((tInsuredNo==null||tInsuredNo.equals(""))&&(tScanType!=null&&!tScanType.equals("0000")&&!tScanType.equals("")&&tScanType!=""))
        {
            aSQL=aSQL+" and subtype='"+tScanType+"' ";
            bSQL="delete from es_doc_pages where docid in (select docid from es_doc_main where doccode='" + tClaimNo + "'  and subtype='"+tScanType+"' ) ";
            cSQL="delete from es_doc_relation where docid in (select docid from es_doc_main where doccode='" + tClaimNo + "'  and subtype='"+tScanType+"' )  ";
        }else
        {
           aSQL=aSQL;
           bSQL=bSQL;
           cSQL=cSQL;
        }

        mMap.put(aSQL,"DELETE");
        mMap.put(bSQL, "DELETE");
        mMap.put(cSQL, "DELETE");

        mLCDelPolLog.setIsPolFlag("0");
        mLCDelPolLog.setOtherNo(tClaimNo);
        mLCDelPolLog.setOtherNoType("5");//理赔扫描件删除
        mLCDelPolLog.setPrtNo(tClaimNo);
        mLCDelPolLog.setManageCom(mManageCom);
        mLCDelPolLog.setOperator(mOperator);
        mLCDelPolLog.setMakeDate(theCurrentDate);
        mLCDelPolLog.setModifyDate(theCurrentDate);
        mLCDelPolLog.setMakeTime(theCurrentTime);
        mLCDelPolLog.setModifyTime(theCurrentTime) ;
        mMap.put(mLCDelPolLog, "INSERT");

        return true;
    }

    /**
     * 准备数据，重新填充数据容器中的内容
     */
    private void prepareOutputData() {
        //记录当前操作员
        mResult.clear();
        mResult.add(mMap);
    }

    /**
     * 操作结果
     * @return VData
     */
    public VData getResult() {
        return mResult;
    }
}
