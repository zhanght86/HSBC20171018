package com.sinosoft.lis.bqgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.vdb.*;
import com.sinosoft.lis.bl.*;
import com.sinosoft.lis.vbl.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;
import java.util.*;

/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 团体险种保全明细功能类</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author zhangjt
 * @version 1.0
 */
public class GEdorRiskDetailBL
{
private static Logger logger = Logger.getLogger(GEdorRiskDetailBL.class);

    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();

    /** 往后面传输数据的容器 */
    private VData mInputData;

    /** 往界面传输数据的容器 */
    private VData mResult = new VData();

    /** 数据操作字符串 */
    private String mOperate;
    private LPEdorMainSet mLPEdorMainSet = new LPEdorMainSet();
    private LPEdorItemSet mLPEdorItemSet = new LPEdorItemSet();
    private LPEdorItemSet saveLPEdorItemSet = new LPEdorItemSet();
    private LPEdorMainSet saveLPEdorMainSet = new LPEdorMainSet();
    private LPGrpEdorItemSchema mLPGrpEdorItemSchema = new LPGrpEdorItemSchema();

    /** 全局数据 */
    private GlobalInput mGlobalInput = new GlobalInput();
    private Reflections ref = new Reflections();
    private String currDate = PubFun.getCurrentDate();
    private String currTime = PubFun.getCurrentTime();
    private MMap map = new MMap();

    /**
     * Constructor
     **/
    public GEdorRiskDetailBL()
    {}

    /**
     *
     **/
    public boolean submitData(VData cInputData, String cOperate)
    {
        logger.debug(
            "=====This is GEdorRiskDetailBL->submitData=====\n");

        //将操作数据拷贝到本类中
        mInputData = (VData) cInputData.clone();
        mOperate = cOperate;

        //得到外部传入的数据
        if (!getInputData())
            return false;

        //数据校验操作
        if (!checkData())
            return false;

        //数据准备操作
        if (mOperate.equals("INSERT||EDOR"))
        {
            if (!prepareData())
                return false;
        }
        //数据准备操作
        if (mOperate.equals("DELETE||EDOR"))
        {
            if (!deleteData())
                return false;
        }
        PubSubmit tPubSubmit = new PubSubmit();
        if (!tPubSubmit.submitData(mResult, mOperate))
        {
            // @@错误处理
            this.mErrors.copyAllErrors(tPubSubmit.mErrors);
            CError tError = new CError();
            tError.moduleName = "ContBL";
            tError.functionName = "submitData";
            tError.errorMessage = "数据提交失败!";
            this.mErrors.addOneError(tError);

            return false;
        }

        return true;
    }

    /**
     * 数据输出方法，供外界获取数据处理结果
     * @return 包含有数据查询结果字符串的VData对象
     */
    public VData getResult()
    {
        return mResult;
    }

    /**
     * 将外部传入的数据分解到本类的属性中
     * @param: 无
     * @return: boolean
     */
    private boolean getInputData()
    {
        logger.debug("=====This is GEdorDetailBL->getInputData=====\n");
        try
        {
            mLPEdorItemSet = (LPEdorItemSet)
               mInputData.getObjectByObjectName("LPEdorItemSet", 0);
            mLPGrpEdorItemSchema = (LPGrpEdorItemSchema)
               mInputData.getObjectByObjectName("LPGrpEdorItemSchema", 0);
            mGlobalInput = (GlobalInput)
               mInputData.getObjectByObjectName("GlobalInput", 0);
        }
        catch (Exception e)
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "GEdorDetailBL";
            tError.functionName = "getInputData";
            tError.errorMessage = "接收数据失败!!";
            this.mErrors.addOneError(tError);

            return false;
        }

        return true;
    }

    /**
     * 校验传入的数据的合法性
     * @return
     */
    private boolean checkData()
    {
        logger.debug("=====This is GEdorRiskDetailBL->checkData=====\n");
        LPGrpEdorItemDB tLPGrpEdorItemDB = new LPGrpEdorItemDB();
        tLPGrpEdorItemDB.setSchema(mLPGrpEdorItemSchema);
        if (!tLPGrpEdorItemDB.getInfo())
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "PInsuredBL";
            tError.functionName = "Preparedata";
            tError.errorMessage = "无保全申请数据!";
            this.mErrors.addOneError(tError);

            return false;
        }

        //将查询出来的团体保全主表数据保存至模块变量中，省去其它的重复查询
        mLPGrpEdorItemSchema.setSchema(tLPGrpEdorItemDB.getSchema());
        if (tLPGrpEdorItemDB.getEdorState().equals("2"))
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "PInsuredBL";
            tError.functionName = "Preparedata";
            tError.errorMessage = "该保全已经申请确认不能修改!";
            this.mErrors.addOneError(tError);

            return false;
        }

        return true;
    }

    /**
     * 准备需要保存的数据
     * @return
     */
    private boolean prepareData()
    {
        logger.debug("=====This is GEdorRiskDetailBL->prepareData=====\n");

        //按个人保全主表进行处理
        for (int i = 1; i <= mLPEdorItemSet.size(); i++)
        {
            LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
            LPPolSchema tLPPolSchema = new LPPolSchema();
            tLPEdorItemSchema.setSchema(mLPEdorItemSet.get(i).getSchema());
            ref.transFields(tLPEdorItemSchema, mLPGrpEdorItemSchema);
            if (tLPEdorItemSchema.getPolNo() != null)
            {
              tLPEdorItemSchema.setPolNo(tLPEdorItemSchema.getPolNo());
              LCPolDB tLCPolDB = new LCPolDB();
              tLCPolDB.setPolNo(tLPEdorItemSchema.getPolNo());
              if(tLCPolDB.getInfo()){
                  ref.transFields(tLPPolSchema,tLCPolDB.getSchema());
                  tLPPolSchema.setEdorNo(tLPEdorItemSchema.getEdorNo());
                  tLPPolSchema.setEdorType(tLPEdorItemSchema.getEdorType());
                  map.put(tLPPolSchema,"DELETE&INSERT");
              }
            }else{
                tLPEdorItemSchema.setPolNo("000000");
            }
            tLPEdorItemSchema.setManageCom(mLPGrpEdorItemSchema.getManageCom());
            tLPEdorItemSchema.setOperator(mGlobalInput.Operator);
            tLPEdorItemSchema.setUWFlag("0");
            tLPEdorItemSchema.setMakeDate(currDate);
            tLPEdorItemSchema.setMakeTime(currTime);
            tLPEdorItemSchema.setModifyDate(currDate);
            tLPEdorItemSchema.setModifyTime(currTime);
            saveLPEdorItemSet.add(tLPEdorItemSchema);
            LPEdorMainDB tLPEdorMainDB = new LPEdorMainDB();
            tLPEdorMainDB.setEdorNo(tLPEdorItemSchema.getEdorNo());
            tLPEdorMainDB.setContNo(tLPEdorItemSchema.getContNo());
            LPEdorMainSet tLPEdorMainSet = new LPEdorMainSet();
            try{
                tLPEdorMainSet = tLPEdorMainDB.query();
            } catch (Exception ex){
                CError.buildErr(this, ex.toString());
                ex.printStackTrace();
                return false;
            }
            if (tLPEdorMainDB.mErrors.needDealError())
            {
                CError.buildErr(this, "查询个人保全主表失败!");
                return false;
            }
            if (tLPEdorMainSet.size() == 0)
            {
                LPEdorMainSchema tLPEdorMainSchema = new LPEdorMainSchema();
                ref.transFields(tLPEdorMainSchema, tLPEdorItemSchema);
                tLPEdorMainSchema.setManageCom(mLPGrpEdorItemSchema.getManageCom());
                tLPEdorMainSchema.setOperator(mGlobalInput.Operator);
                tLPEdorMainSchema.setUWState("0");
                tLPEdorMainSchema.setMakeDate(currDate);
                tLPEdorMainSchema.setMakeTime(currTime);
                tLPEdorMainSchema.setModifyDate(currDate);
                tLPEdorMainSchema.setModifyTime(currTime);
                saveLPEdorMainSet.add(tLPEdorMainSchema);
            }
        }
        map.put(saveLPEdorItemSet, "DELETE&INSERT");
        map.put(saveLPEdorMainSet, "DELETE&INSERT");
        mResult.clear();
        mResult.add(map);

        return true;
    }

    /**
     * 准备需要保存的数据
     * @return
     */
    private boolean deleteData()
    {
        logger.debug("=====This is GEdorRiskDetailBL->deleteData=====\n");
        String tContNoStr = "";

        //按个人保全主表进行处理
        for (int i = 1; i <= mLPEdorItemSet.size(); i++)
        {
            if (i != mLPEdorItemSet.size())
            {
                tContNoStr = tContNoStr + "'" +
                             mLPEdorItemSet.get(i).getContNo() + "',";
            } else {
                tContNoStr = tContNoStr + "'" +
                             mLPEdorItemSet.get(i).getContNo() + "'";
            }
            String tDelSql = "delete from LPEdorItem where ContNo = '" +
                             mLPEdorItemSet.get(i).getContNo() +
                             "' and EdorNo = '" +
                             mLPEdorItemSet.get(i).getEdorNo() +
                             "' and EdorType = '" +
                             mLPEdorItemSet.get(i).getEdorType() + "'";
            if (mLPEdorItemSet.get(i).getInsuredNo() != null &&
                !mLPEdorItemSet.get(i).getInsuredNo().equals("") &&
                !mLPEdorItemSet.get(i).getInsuredNo().equals("000000"))
            {
                tContNoStr = tContNoStr + "' and InsuredNo = '" +
                            mLPEdorItemSet.get(i).getContNo() + "'";
                if (mLPEdorItemSet.get(i).getPolNo() != null &&
                    !mLPEdorItemSet.get(i).getPolNo().equals("") &&
                    !mLPEdorItemSet.get(i).getPolNo().equals("000000"))
                {
                    tContNoStr = tContNoStr + "' and PolNo = '" +
                                mLPEdorItemSet.get(i).getPolNo() + "'";
                }
            }

            //删除个人批改项目
            map.put(tDelSql, "DELETE");
        }
//         //删除个人批改项目
//         String sql="delete from LPEdorItem where edorno='"
//                    + mLPGrpEdorItemSchema.getEdorNo()
//                    +"' and edortype='"+mLPGrpEdorItemSchema.getEdorType()
//                    + "' and ContNo in ("
//                    + cotnNoStr + ")";
//         map.put(sql,"UPDATE");
        //当个人批单主表没有批改项目时需要删掉个人批改主表
        String sql1 = "delete from LPEdorMain a where a.EdorNo = '" +
                      mLPGrpEdorItemSchema.getEdorNo() +
                      "' and a.ContNo in (" + tContNoStr +
                      ") and 0 = (select count(1) from LPEdorItem b where " +
                      "b.ContNo = a.ContNo and b.EdorNo = '" +
                      mLPGrpEdorItemSchema.getEdorNo() + "') ";
        map.put(sql1, "UPDATE");
        mResult.clear();
        mResult.add(map);

        return true;
    }
}
