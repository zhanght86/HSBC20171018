package com.sinosoft.lis.bqgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.bl.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;


/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 个单被保人基本信息变更</p>
 */
public class PEdorBBDetailBL {
private static Logger logger = Logger.getLogger(PEdorBBDetailBL.class);
    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();


    /** 往后面传输数据的容器 */
    private VData mInputData;
    private MMap mMap = new MMap();


    /** 往界面传输数据的容器 */
    private VData mResult = new VData();


    /** 数据操作字符串 */
    private String mOperate;


    /** 计算要素 */
    private BqCalBase mBqCalBase = new BqCalBase();


    /** 全局数据 */
    private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();
    private LPEdorItemSet mLPEdorItemSet = new LPEdorItemSet();
    private LPGrpEdorItemSchema mLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
    private LPInsuredSchema mLPInsuredSchema = new LPInsuredSchema();
    private LPInsuredSet mLPInsuredSet = new LPInsuredSet();
    private LPAddressSchema mLPAddressSchema = new LPAddressSchema();
    private LPAddressSet mLPAddressSet = new LPAddressSet();
    private LPPersonSchema mLPPersonSchema = new LPPersonSchema();
    private LPContSchema mLPContSchema = new LPContSchema();
    private LPPolSet mLPPolSet = new LPPolSet();
    private GlobalInput mGlobalInput = new GlobalInput();
    private Reflections tRef = new Reflections();
    public PEdorBBDetailBL() {}


    /**
     * 传输数据的公共方法
     * @param: cInputData 输入的数据
     * @param: cOperate 数据操作
     * @return:
     */

    public boolean submitData(VData cInputData, String cOperate) {
        //将操作数据拷贝到本类中
        mInputData = (VData) cInputData.clone();
        mOperate = cOperate;

        //得到外部传入的数据
        if (!getInputData()) {
            return false;
        }

        logger.debug("after getInputData...");

        //数据校验
        if (!checkData()) {
            return false;
        }

        logger.debug("after checkData...");

        //数据操作业务处理
        if (!dealData()) {
            return false;
        }

        logger.debug("after dealData...");

        return true;
    }


    public VData getResult() {
        return mResult;
    }




    /**
     * 从输入数据中得到所有对象
     * @return
     */
    private boolean getInputData() {
        try {
            mLPInsuredSchema = (LPInsuredSchema) mInputData.
                               getObjectByObjectName(
                                       "LPInsuredSchema", 0);
            mLPAddressSchema = (LPAddressSchema) mInputData.
                               getObjectByObjectName(
                                       "LPAddressSchema", 0);
            mLPEdorItemSchema = (LPEdorItemSchema) mInputData.
                                getObjectByObjectName(
                                        "LPEdorItemSchema", 0);
            mLPContSchema = (LPContSchema) mInputData.getObjectByObjectName(
                    "LPContSchema", 0);
//      mLPPersonSchema = (LPPersonSchema) mInputData.getObjectByObjectName(
//          "LPPersonSchema", 0);
            logger.debug(mLPEdorItemSchema.getContNo() + "&&&&&&&&&&=" +
                               mLPEdorItemSchema.getInsuredNo());
            //mLPGrpEdorItemSchema = (LPGrpEdorItemSchema) mInputData.getObjectByObjectName("LPGrpEdorItemSchema", 0);
            mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
                    "GlobalInput", 0);

            if (mLPInsuredSchema == null || mLPAddressSchema == null ||
                mLPEdorItemSchema == null || mLPContSchema == null) {
                return false;
            }

            //获得mLPEdorItemSchema的其它信息
            if (mOperate != null && !mOperate.equals("")
                && mOperate.equals("LOAD")) {
                logger.debug("导入－－－－－－－－");
                LCContDB tLCContDB = new LCContDB();
                tLCContDB.setContNo(mLPEdorItemSchema.getContNo());
                tLCContDB.setInsuredNo(mLPEdorItemSchema.getInsuredNo());
                LCContSet tLCContSet = tLCContDB.query();
                if (tLCContSet.size() < 1) {
                    CError.buildErr(this,
                                    "未查询到保单号为" + mLPEdorItemSchema.getContNo() +
                                    "，客户号为" + mLPEdorItemSchema.getInsuredNo() +
                                    "的记录！");
                    return false;
                }
                LPGrpEdorItemDB tLPGrpEdorItemDB = new LPGrpEdorItemDB();
                tLPGrpEdorItemDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
                tLPGrpEdorItemDB.setEdorType("BB");
                LPGrpEdorItemSet tLPGrpEdorItemSet = tLPGrpEdorItemDB.query();
                if (tLPGrpEdorItemSet.size() < 1) {
                    CError.buildErr(this, "LPGrpEdorItem表中无匹配记录！");
                    return false;
                }
                LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
                tRef.transFields(tLPEdorItemSchema, tLPGrpEdorItemSet.get(1));
                tRef.transFields(tLPEdorItemSchema, tLCContSet.get(1));
                tRef.transFields(mLPEdorItemSchema, tLPEdorItemSchema);
                mLPEdorItemSchema.setEdorState("1");
                mLPEdorItemSchema.setOperator(mGlobalInput.Operator);
                mLPEdorItemSchema.setMakeDate(PubFun.getCurrentDate());
                mLPEdorItemSchema.setMakeTime(PubFun.getCurrentTime());
                mLPEdorItemSchema.setModifyDate(PubFun.getCurrentDate());
                mLPEdorItemSchema.setModifyTime(PubFun.getCurrentTime());
                LPEdorMainSchema tLPEdorMainSchema = new LPEdorMainSchema();
                tRef.transFields(tLPEdorMainSchema, mLPEdorItemSchema);
                mMap.put(tLPEdorMainSchema, "DELETE&INSERT");
            } else {
                LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
                tLPEdorItemDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
                tLPEdorItemDB.setEdorType(mLPEdorItemSchema.getEdorType());
                tLPEdorItemDB.setContNo(mLPEdorItemSchema.getContNo());
                if (tLPEdorItemDB.query().size() < 1) {
                    CError.buildErr(this, "LPEdorItem表中无匹配记录！");
                    return false;
                }
                mLPEdorItemSchema.setSchema(tLPEdorItemDB.query().get(1));
            }
        } catch (Exception e) {
            CError.buildErr(this, "接收数据失败！");
            return false;
        }

        return true;
    }



    /**
     * 校验传入的数据的合法性
     * 输出：如果发生错误则返回false,否则返回true
     */
    private boolean checkData() {
        LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
        tLPEdorItemDB.setSchema(mLPEdorItemSchema);


        if (tLPEdorItemDB.getEdorState() != null
            && !tLPEdorItemDB.getEdorState().equals("")
            && tLPEdorItemDB.getEdorState().equals("2")) {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "PEdorBBDetailBL";
            tError.functionName = "checkData";
            tError.errorMessage = "该保全已经申请确认不能修改!";
            logger.debug("------" + tError);
            this.mErrors.addOneError(tError);
            return false;
        }
        return true;
    }


    /**
     * 准备需要保存的数据
     */
    private boolean dealData() {
        //获得此时的日期和时间
        String strCurrentDate = PubFun.getCurrentDate();
        String strCurrentTime = PubFun.getCurrentTime();
        String tSql = "";
        SSRS tSSRS = null;
        ExeSQL tExeSQL = null;
        String strNewAddressNo = "";

        //准备要更新的LPAddress表的最新信息，这组信息要先准备，因为地址号有可能新增加。
        //如果新增了，要把新的地址号更新到相应的表中。
        LPAddressBL tLPAddressBL = new LPAddressBL();
        //先获得当前可用的地址号
        String newAddressNo = tLPAddressBL.getNewAddressNo(mLPAddressSchema);
        //获得当前对应地址信息
//        if (!tLPAddressBL.queryLPAddress(this.mLPAddressSchema)) {
//            return false;
//        }
        LPAddressSchema tLPAddressSchema = new LPAddressSchema();
//        tLPAddressSchema = tLPAddressBL.getSchema();
        //更新保全相关信息
	tLPAddressSchema.setSchema(mLPAddressSchema);
        tLPAddressSchema.setAddressNo(newAddressNo); //最新可用的地址号
        tLPAddressSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
        tLPAddressSchema.setEdorType(mLPEdorItemSchema.getEdorType());
        tLPAddressSchema.setProvince(mLPAddressSchema.getProvince());
        tLPAddressSchema.setCity(mLPAddressSchema.getCity());
        tLPAddressSchema.setCounty(mLPAddressSchema.getCounty());
        tLPAddressSchema.setPostalAddress(mLPAddressSchema.getPostalAddress());
        tLPAddressSchema.setZipCode(mLPAddressSchema.getZipCode());
        tLPAddressSchema.setMobile(mLPAddressSchema.getMobile());
        tLPAddressSchema.setCompanyPhone(mLPAddressSchema.getCompanyPhone());
        tLPAddressSchema.setFax(mLPAddressSchema.getFax());
        tLPAddressSchema.setHomePhone(mLPAddressSchema.getHomePhone());
        tLPAddressSchema.setEMail(mLPAddressSchema.getEMail());
        tLPAddressSchema.setGrpName(mLPAddressSchema.getGrpName());
        tLPAddressSchema.setOperator(mLPEdorItemSchema.getOperator());
        tLPAddressSchema.setMakeDate(strCurrentDate);
        tLPAddressSchema.setMakeTime(strCurrentTime);
        tLPAddressSchema.setModifyDate(strCurrentDate);
        tLPAddressSchema.setModifyTime(strCurrentTime);

        mMap.put("delete from LPAddress where edorno='" +
                 mLPEdorItemSchema.getEdorNo() + "' and edortype='" +
                 mLPEdorItemSchema.getEdorType() + "' and CustomerNo='" +
                 mLPEdorItemSchema.getInsuredNo() + "'", "DELETE");
        mMap.put(tLPAddressSchema, "DELETE&INSERT");

        //准备要更新的LPInsured表的最新信息
        LCInsuredDB aLCInsuredDB = new LCInsuredDB();
        aLCInsuredDB.setInsuredNo(mLPEdorItemSchema.getInsuredNo());
        aLCInsuredDB.setContNo(mLPEdorItemSchema.getContNo());
        LCInsuredSchema tLCInsuredSchema = aLCInsuredDB.query().get(1);
        LPInsuredSchema tLPInsuredSchema = new LPInsuredSchema();
        tRef.transFields(tLPInsuredSchema, tLCInsuredSchema);
        //更新保全相关信息
        tLPInsuredSchema.setAddressNo(newAddressNo);
        tLPInsuredSchema.setName(mLPInsuredSchema.getName());
        tLPInsuredSchema.setJoinCompanyDate(mLPInsuredSchema.getJoinCompanyDate());
        tLPInsuredSchema.setSalary(mLPInsuredSchema.getSalary());
    	tLPInsuredSchema.setWorkNo(mLPInsuredSchema.getWorkNo());
    	tLPInsuredSchema.setBankCode(mLPInsuredSchema.getBankCode());
    	tLPInsuredSchema.setBankAccNo(mLPInsuredSchema.getBankAccNo());
        tLPInsuredSchema.setModifyDate(strCurrentDate);
        tLPInsuredSchema.setModifyTime(strCurrentTime);
        tLPInsuredSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
        tLPInsuredSchema.setEdorType(mLPEdorItemSchema.getEdorType());
        mMap.put(tLPInsuredSchema, "DELETE&INSERT");

        //准备要更新的LPPerson表的最新信息
        LDPersonDB aLDPersonDB = new LDPersonDB();
        LDPersonSchema tLDPersonSchema = new LDPersonSchema();
        LPPersonSchema tLPPersonSchema = new LPPersonSchema();
        aLDPersonDB.setCustomerNo(this.mLPInsuredSchema.getInsuredNo());
        tLDPersonSchema = aLDPersonDB.query().get(1);
        tRef.transFields(tLPPersonSchema, tLDPersonSchema);
        //更新保全相关信息
        tLPPersonSchema.setGrpName(mLPAddressSchema.getGrpName());
        tLPPersonSchema.setName(mLPInsuredSchema.getName());
        tLPPersonSchema.setJoinCompanyDate(mLPInsuredSchema.getJoinCompanyDate());
        tLPPersonSchema.setSalary(mLPInsuredSchema.getSalary());

//        tLPPersonSchema.setOperator(this.mGlobalInput.Operator);
        tLPPersonSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
        tLPPersonSchema.setEdorType(mLPEdorItemSchema.getEdorType());
        tLPPersonSchema.setModifyDate(strCurrentDate);
        tLPPersonSchema.setModifyTime(strCurrentTime);
        mMap.put(tLPPersonSchema, "DELETE&INSERT");

        //准备要更新的LPCont表的最新信息
        LCContDB aLCContDB = new LCContDB();
        aLCContDB.setContNo(mLPEdorItemSchema.getContNo());
        //aLCContDB.setInsuredNo(mLPEdorItemSchema.getInsuredNo());
        LCContSchema tLCContSchema = aLCContDB.query().get(1);
        LPContSchema tLPContSchema = new LPContSchema();
        tRef.transFields(tLPContSchema, tLCContSchema);
        //更新保全相关信息	
        tLPContSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
        tLPContSchema.setEdorType(mLPEdorItemSchema.getEdorType());
        if(tLCContSchema.getContNo().equals(tLCContSchema.getGrpContNo()))
        {
         tLPContSchema.setAppntName(mLPContSchema.getInsuredName());
        }
        if(tLPContSchema.getInsuredNo().equals(tLPInsuredSchema.getInsuredNo())){
        	//连带被保人不更改合同数据
        	tLPContSchema.setInsuredName(mLPContSchema.getInsuredName());
//        tLPContSchema.setOperator(this.mGlobalInput.Operator);
        	tLPContSchema.setModifyDate(strCurrentDate);
        	tLPContSchema.setModifyTime(strCurrentTime);
        	mMap.put(tLPContSchema, "DELETE&INSERT");
        }

        LCPolDB aLCPolDB = new LCPolDB();
        aLCPolDB.setContNo(mLPEdorItemSchema.getContNo());
        aLCPolDB.setInsuredNo(mLPEdorItemSchema.getInsuredNo());
        LCPolSet tLCPolSet = aLCPolDB.query();
        LPPolSchema tLPPolSchema = new LPPolSchema();
        LCPolSchema tLCPolSchema = new LCPolSchema();
        for (int i = 1; i <= tLCPolSet.size(); i++) {
            tLPPolSchema = new LPPolSchema();
            tLCPolSchema = tLCPolSet.get(i);
            tRef.transFields(tLPPolSchema, tLCPolSchema);
            tLPPolSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
            tLPPolSchema.setEdorType(mLPEdorItemSchema.getEdorType());
            if(tLCPolSchema.getContNo().equals(tLCPolSchema.getGrpContNo()))
            {
             tLPPolSchema.setAppntName(mLPContSchema.getInsuredName());
            }
            tLPPolSchema.setInsuredName(mLPContSchema.getInsuredName());
//            tLPPolSchema.setOperator(this.mGlobalInput.Operator);
            tLPPolSchema.setModifyDate(strCurrentDate);
            tLPPolSchema.setModifyTime(strCurrentTime);
            mLPPolSet.add(tLPPolSchema);

            mLPEdorItemSchema.setEdorState("1");
//            mLPEdorItemSchema.setOperator(this.mGlobalInput.Operator);
            mLPEdorItemSchema.setModifyDate(strCurrentDate);
            mLPEdorItemSchema.setModifyTime(strCurrentTime);
            mLPEdorItemSchema.setPolNo(tLCPolSchema.getPolNo());
            LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
            tRef.transFields(tLPEdorItemSchema, mLPEdorItemSchema);
            mLPEdorItemSet.add(tLPEdorItemSchema);
        }
        mMap.put("DELETE FROM LPEDORITEM WHERE EDORNO='" +
        		mLPEdorItemSchema.getEdorNo() +
                 "' AND EDORTYPE='BB' AND POLNO='000000' AND CONTNO='" +
                 mLPEdorItemSchema.getContNo() + "'", "DELETE");
        //add by sunsx 连带被保人可能没有POL数据
        if(mLPEdorItemSet.size()<1)
        {
           mLPEdorItemSchema.setEdorState("1");
           mLPEdorItemSchema.setModifyDate(strCurrentDate);
           mLPEdorItemSchema.setModifyTime(strCurrentTime);
           LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
           tRef.transFields(tLPEdorItemSchema, mLPEdorItemSchema);
           mLPEdorItemSet.add(tLPEdorItemSchema);
        }
        mMap.put(mLPEdorItemSet, "DELETE&INSERT");
        mMap.put(mLPPolSet, "DELETE&INSERT");


        String bSql = "update LPGrpEdorItem set EdorState='1' where EdorNo='" +
                      mLPEdorItemSchema.getEdorNo()
                      + "' and EdorType='" + mLPEdorItemSchema.getEdorType()
                      +
                      "' and 0=(select count(*) from lpedoritem where edorno='" +
                      mLPEdorItemSchema.getEdorNo() +
                      "' and edortype='BB' and edorstate='3')";
        mMap.put(bSql, "UPDATE");

        mResult.clear();
        mResult.add(mMap);


        return true;
    }





}
