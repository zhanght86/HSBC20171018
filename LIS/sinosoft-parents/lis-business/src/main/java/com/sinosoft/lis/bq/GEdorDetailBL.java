package com.sinosoft.lis.bq;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;


/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 团体保全集体下个人功能类</p>
 */
public class GEdorDetailBL {
private static Logger logger = Logger.getLogger(GEdorDetailBL.class);
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
    public GEdorDetailBL() {
    }

    public boolean submitData(VData cInputData, String cOperate) {
        //将操作数据拷贝到本类中
        mInputData = (VData) cInputData.clone();
        mOperate = cOperate;

        //得到外部传入的数据
        if (!getInputData()) {
            return false;
        }
        logger.debug("---End getInputData---");

        //数据校验操作
        if (!checkData()) {
            return false;
        }
        logger.debug("---End checkData---");

        //数据准备操作
        if (mOperate.equals("INSERT||EDOR")) {
            if (!prepareData()) {
                return false;
            }
            logger.debug("---End prepareData---");
        }
        //数据准备操作
        if (mOperate.equals("DELETE||EDOR")) {
            if (!deleteData()) {
                return false;
            }
            logger.debug("---End prepareData---");
        }

        

        PubSubmit tPubSubmit = new PubSubmit();
        logger.debug("Start tPRnewManualDunBLS Submit...");

        if (!tPubSubmit.submitData(mResult, mOperate)) {
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
    public VData getResult() {
        return mResult;
    }


    /**
     * 将外部传入的数据分解到本类的属性中
     * @param: 无
     * @return: boolean
     */
    private boolean getInputData() {
        try {
            mLPEdorItemSet = (LPEdorItemSet) mInputData.getObjectByObjectName(
                    "LPEdorItemSet", 0);
            mLPGrpEdorItemSchema = (LPGrpEdorItemSchema) mInputData.
                                   getObjectByObjectName("LPGrpEdorItemSchema",
                    0);

            mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
                    "GlobalInput", 0);
        } catch (Exception e) {
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
    private boolean checkData() {
        LPGrpEdorItemDB tLPGrpEdorItemDB = new LPGrpEdorItemDB();
        tLPGrpEdorItemDB.setSchema(mLPGrpEdorItemSchema);
        if (!tLPGrpEdorItemDB.getInfo()) {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "PInsuredBL";
            tError.functionName = "Preparedata";
            tError.errorMessage = "无保全申请数据!";
            logger.debug("------" + tError);
            this.mErrors.addOneError(tError);
            return false;
        }

        //将查询出来的团体保全主表数据保存至模块变量中，省去其它的重复查询
        mLPGrpEdorItemSchema.setSchema(tLPGrpEdorItemDB.getSchema());

        if (tLPGrpEdorItemDB.getEdorState().trim().equals("2")) {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "PInsuredBL";
            tError.functionName = "Preparedata";
            tError.errorMessage = "该保全已经申请确认不能修改!";
            logger.debug("------" + tError);
            this.mErrors.addOneError(tError);
            return false;
        }

        return true;
    }


    /**
     * 准备需要保存的数据
     * @return
     */
    private boolean prepareData() {

        //按个人保全主表进行处理
        for (int i = 1; i <= mLPEdorItemSet.size(); i++) {
            LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
            tLPEdorItemSchema = mLPEdorItemSet.get(i);

            ref.transFields(tLPEdorItemSchema, mLPGrpEdorItemSchema);
            tLPEdorItemSchema.setPolNo("000000");
            tLPEdorItemSchema.setEdorState("3");
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
            tLPEdorMainSet = tLPEdorMainDB.query();
            if (tLPEdorMainDB.mErrors.needDealError()) {
                CError.buildErr(this, "查询个人保全主表失败!");
                return false;
            }
            if (tLPEdorMainSet.size() == 0) {
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
        if (saveLPEdorItemSet.size() > 0) {
        	SQLwithBindVariables sbv=new SQLwithBindVariables();
        	sbv.sql("UPDATE LPGrpEdorItem set EdorState='3' where EdorNo='" +
                    "?EdorNo?" + "' and EdorType='" +
                    "?EdorType?" + "'");
        	sbv.put("EdorNo", saveLPEdorItemSet.get(1).getEdorNo());
        	sbv.put("EdorType", saveLPEdorItemSet.get(1).getEdorType());
            map.put(sbv,"UPDATE");
        }
        mResult.clear();
        mResult.add(map);

        return true;
    }


    /**
     * 准备需要保存的数据
     * @return
     */
    private boolean deleteData() {

        String cotnNoStr = "";
        ArrayList<String> arrStr=new ArrayList<String>();
        
        //按个人保全主表进行处理
        for (int i = 1; i <= mLPEdorItemSet.size(); i++) {
        	arrStr.add(mLPEdorItemSet.get(i).getContNo());
//            if (i != mLPEdorItemSet.size()) {
//                cotnNoStr = cotnNoStr + "'" + mLPEdorItemSet.get(i).getContNo() +
//                            "',";
//            } else {
//                cotnNoStr = cotnNoStr + "'" + mLPEdorItemSet.get(i).getContNo() +
//                            "'";
//        }
        
        //删除个人批改项目
            String deleteSql = "delete from lpedoritem where contno='"
                               + "?contno?"
                               + "' and edorno='" +
                               "?edorno?" + "'"
                               + " and edortype='" +
                               "?edortype?" + "'";
            SQLwithBindVariables sbv1=new SQLwithBindVariables();
            sbv1.sql(deleteSql);
            sbv1.put("contno", mLPEdorItemSet.get(i).getContNo());
            sbv1.put("edorno", mLPEdorItemSet.get(i).getEdorNo());
            sbv1.put("edortype", mLPGrpEdorItemSchema.getEdorType());
            map.put(sbv1, "DELETE");
            //lpcont
            String deleteLPCont = "delete from lpcont where contno='"
                                  + "?contno?"
                                  + "' and edorno='" +
                                  "?edorno?" + "'"
                                  + " and edortype='" +
                                  "?edortype?" + "'";
            SQLwithBindVariables sbv2=new SQLwithBindVariables();
            sbv2.sql(deleteLPCont);
            sbv2.put("contno", mLPEdorItemSet.get(i).getContNo());
            sbv2.put("edorno", mLPEdorItemSet.get(i).getEdorNo());
            sbv2.put("edortype", mLPGrpEdorItemSchema.getEdorType());
            map.put(sbv2, "DELETE");
            //lppol
            String deleteLPPol = "delete from lppol where contno='"
                                 + "?contno?"
                                 + "' and edorno='" +
                                 "?edorno?" + "'"
                                 + " and edortype='" +
                                 "?edortype?" + "'";
            SQLwithBindVariables sbv3=new SQLwithBindVariables();
            sbv3.sql(deleteLPPol);
            sbv3.put("contno", mLPEdorItemSet.get(i).getContNo());
            sbv3.put("edorno", mLPEdorItemSet.get(i).getEdorNo());
            sbv3.put("edortype", mLPGrpEdorItemSchema.getEdorType());
            map.put(sbv3, "DELETE");
            //lpduty
            String deleteLPDuty = "delete from lpduty where contno='"
                                  + "?contno?"
                                  + "' and edorno='" +
                                  "?edorno?" + "'"
                                  + " and edortype='" +
                                  "?edortype?" + "'";
            SQLwithBindVariables sbv4=new SQLwithBindVariables();
            sbv4.sql(deleteLPDuty);
            sbv4.put("contno", mLPEdorItemSet.get(i).getContNo());
            sbv4.put("edorno", mLPEdorItemSet.get(i).getEdorNo());
            sbv4.put("edortype", mLPGrpEdorItemSchema.getEdorType());
            map.put(sbv4, "DELETE");
            //lpprem
            String deleteLPPrem = "delete from LPPrem where contno='"
                                  + "?contno?"
                                  + "' and edorno='" +
                                  "?edorno?" + "'"
                                  + " and edortype='" +
                                  "?edortype?" + "'";
            SQLwithBindVariables sbv5=new SQLwithBindVariables();
            sbv5.sql(deleteLPPrem);
            sbv5.put("contno", mLPEdorItemSet.get(i).getContNo());
            sbv5.put("edorno", mLPEdorItemSet.get(i).getEdorNo());
            sbv5.put("edortype", mLPGrpEdorItemSchema.getEdorType());
            map.put(sbv5, "DELETE");
            //lpget
            String deleteLPGet = "delete from LPGet where contno='"
                                 + "?contno?"
                                 + "' and edorno='" +
                                 "?edorno?" + "'"
                                 + " and edortype='" +
                                 "?edortype?" + "'";
            SQLwithBindVariables sbv6=new SQLwithBindVariables();
            sbv6.sql(deleteLPGet);
            sbv6.put("contno", mLPEdorItemSet.get(i).getContNo());
            sbv6.put("edorno", mLPEdorItemSet.get(i).getEdorNo());
            sbv6.put("edortype", mLPGrpEdorItemSchema.getEdorType());
            map.put(sbv6, "DELETE");
            //LPBnf
            String deleteLPBnf = "delete from LPBnf where contno='"
                                 + "?contno?"
                                 + "' and edorno='" +
                                 "?edorno?" + "'"
                                 + " and edortype='" +
                                 "?edortype?" + "'";
            SQLwithBindVariables sbv7=new SQLwithBindVariables();
            sbv7.sql(deleteLPBnf);
            sbv7.put("contno", mLPEdorItemSet.get(i).getContNo());
            sbv7.put("edorno", mLPEdorItemSet.get(i).getEdorNo());
            sbv7.put("edortype", mLPGrpEdorItemSchema.getEdorType());
            map.put(sbv7, "DELETE");
            //LPInsured
            String deleteLPInsured = "delete from LPInsured where contno='"
                                     + "?contno?"
                                     + "' and edorno='" +
                                     "?edorno?" + "'"
                                     + " and edortype='" +
                                     "?edortype?" + "'";
            SQLwithBindVariables sbv8=new SQLwithBindVariables();
            sbv8.sql(deleteLPInsured);
            sbv8.put("contno", mLPEdorItemSet.get(i).getContNo());
            sbv8.put("edorno", mLPEdorItemSet.get(i).getEdorNo());
            sbv8.put("edortype", mLPGrpEdorItemSchema.getEdorType());
            map.put(sbv8, "DELETE");
            
            LPInsuredDB tLPInsuredDB = new LPInsuredDB();
            tLPInsuredDB.setContNo(mLPEdorItemSet.get(i).getContNo());
            tLPInsuredDB.setEdorNo(mLPEdorItemSet.get(i).getEdorNo());
            tLPInsuredDB.setEdorType(mLPGrpEdorItemSchema.getEdorType());
            LPInsuredSet tLPInsuredSet = tLPInsuredDB.query();
            
            if(tLPInsuredSet.size()>0)
            {   
            	String deleteLPPerson = null;
            	for(int k = 1;k<=tLPInsuredSet.size();k++)
            	{
            	//LPPerson
            		
            		deleteLPPerson = "delete from LPPerson where CustomerNo='"
            		+ "?CustomerNo?"
            		+ "' and edorno='" +
            		"?edorno?" + "'"
            		+ " and edortype='" +
            		"?edortype?" + "'";
            		SQLwithBindVariables sbv=new SQLwithBindVariables();
            		sbv.sql(deleteLPPerson);
            		sbv.put("CustomerNo", tLPInsuredSet.get(k).getInsuredNo());
            		sbv.put("edorno", mLPEdorItemSet.get(i).getEdorNo());
            		sbv.put("edortype", mLPGrpEdorItemSchema.getEdorType());
            		map.put(sbv, "DELETE");
            	}
            }
            //ljsgetendorse
            String deleteLjsgetendorse =
                    "delete from Ljsgetendorse where contno='"
                    + "?contno?"
                    + "' and endorsementno='" +
                    "?edorno?" + "'"
                    + " and feeoperationtype='" +
                    "?edortype?" + "'";
            SQLwithBindVariables sbv9=new SQLwithBindVariables();
            sbv9.sql(deleteLjsgetendorse);
            sbv9.put("contno", mLPEdorItemSet.get(i).getContNo());
            sbv9.put("edorno", mLPEdorItemSet.get(i).getEdorNo());
            sbv9.put("edortype", mLPGrpEdorItemSchema.getEdorType());
            map.put(sbv9, "DELETE");
            //ljsgetdraw
            String deleteljsgetdraw = "delete from ljsgetdraw where contno='"
                                      + "?contno?"
                                      + "' and GETNOTICENO='" +
                                      "?edorno?" + "'";
            SQLwithBindVariables sbv10=new SQLwithBindVariables();
            sbv10.sql(deleteljsgetdraw);
            sbv10.put("contno", mLPEdorItemSet.get(i).getContNo());
            sbv10.put("edorno", mLPEdorItemSet.get(i).getEdorNo());
            map.put(sbv10, "DELETE");
            //lpinsureaccclass
            String deletelpinsureaccclass =
                    "delete from lpinsureaccclass where contno='"
                    + "?contno?"
                    + "' and edorno='" +
                    "?edorno?" + "'"
                    + " and edortype='" +
                    "?edortype?" + "'";
            SQLwithBindVariables sbv11=new SQLwithBindVariables();
            sbv11.sql(deletelpinsureaccclass);
            sbv11.put("contno", mLPEdorItemSet.get(i).getContNo());
            sbv11.put("edorno", mLPEdorItemSet.get(i).getEdorNo());
            sbv11.put("edortype", mLPGrpEdorItemSchema.getEdorType());
            map.put(sbv11, "DELETE");
            //lpinsureacctrace
            String deletelpinsureacctrace =
                    "delete from lpinsureacctrace where contno='"
                    + "?contno?"
                    + "' and edorno='" +
                    "?edorno?" + "'"
                    + " and edortype='" +
                    "?edortype?" + "'";
            SQLwithBindVariables sbv12=new SQLwithBindVariables();
            sbv12.sql(deletelpinsureacctrace);
            sbv12.put("contno", mLPEdorItemSet.get(i).getContNo());
            sbv12.put("edorno", mLPEdorItemSet.get(i).getEdorNo());
            sbv12.put("edortype", mLPGrpEdorItemSchema.getEdorType());
            map.put(sbv12, "DELETE");
            //lpinsureacctrace2
            String deletelpinsureacctrace2 =
                    "delete from lpinsureacctrace where edorno='" +
                    "?edorno?" + "'"
                    + " and edortype='" +
                    "?edortype?" +
                    "' and grpcontno='" + "?grpcontno?" +
                    "' and AccAlterNo in (select polno from lcpol where contno='"
                    + "?contno?" + "')";
            SQLwithBindVariables sbv13=new SQLwithBindVariables();
            sbv13.sql(deletelpinsureacctrace2);
            sbv13.put("contno", mLPEdorItemSet.get(i).getContNo());
            sbv13.put("edorno", mLPEdorItemSet.get(i).getEdorNo());
            sbv13.put("edortype", mLPGrpEdorItemSchema.getEdorType());
            sbv13.put("grpcontno", mLPGrpEdorItemSchema.getGrpContNo());
            map.put(sbv13, "DELETE");

        }

        //当个人批单主表没有批改项目时需要删掉个人批改主表
        String sql1 = "delete from LPEdorMain where LPEdorMain.edorno='"
                      + "?edorno?"
                      + "' and LPEdorMain.contno in (?cotnNoStr?) and 0=(select count(*) from lpedoritem b where b.contno=LPEdorMain.contno "
                      + "and b.edorNo='" + "?edorno?" +
                      "') ";
        SQLwithBindVariables sbv14=new SQLwithBindVariables();
        sbv14.sql(sql1);
        sbv14.put("edorno", mLPGrpEdorItemSchema.getEdorNo());
        sbv14.put("cotnNoStr", arrStr);
        map.put(sbv14, "DELETE");
        sql1 = "delete from LJSPayPerson where LJSPayPerson.edorno='"
               + "?edorno?"
               + "' and LJSPayPerson.contno in (?cotnNoStr?) and 0=(select count(*) from lpedoritem b where b.contno=LJSPayPerson.contno "
               + "and b.edorNo='" + "?edorno?" +
               "') ";
        SQLwithBindVariables sbv15=new SQLwithBindVariables();
        sbv15.sql(sql1);
        sbv15.put("edorno", mLPGrpEdorItemSchema.getEdorNo());
        sbv15.put("cotnNoStr", arrStr);
        map.put(sbv15, "DELETE");
        SQLwithBindVariables sbv16=new SQLwithBindVariables();
        sbv16.sql("update lpgrpedoritem set edorstate='1' where 0=(select count(*) from lpedoritem where edorno='" +
                "?edorno?" + "' and edortype='" +
                "?edortype?" +
                "' and edorstate='3') and edortype='" +
                "?edortype?" + "' and edorno='" +
                "?edorno?" + "'");
        sbv16.put("edorno", mLPGrpEdorItemSchema.getEdorNo());
        sbv16.put("edortype", mLPGrpEdorItemSchema.getEdorType());
        map.put(sbv16, "UPDATE");
        SQLwithBindVariables sbv17=new SQLwithBindVariables();
        sbv17.sql("update lpgrpedoritem set edorstate='3' where 0=(select count(*) from lpedoritem where edorno='" +
        		"?edorno?" + "' and edortype='" +
        		"?edortype?" +
                "') and edortype='" +
                "?edortype?" + "' and edorno='" +
                "?edorno?" + "'");
        sbv17.put("edorno", mLPGrpEdorItemSchema.getEdorNo());
        sbv17.put("edortype", mLPGrpEdorItemSchema.getEdorType());
        map.put(sbv17, "UPDATE");
        
        
        SQLwithBindVariables sbv18=new SQLwithBindVariables();
		if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
			sbv18.sql("delete from ljsgetendorse where 0=(select (case when SUM(getmoney) is null then 0 else SUM(getmoney) end) from ljsgetendorse where endorsementno='?EdorNo?' and feeoperationtype='AA' and feefinatype in ('BF','GL','LX')) and endorsementno='?EdorNo?' and feeoperationtype='AA' and feefinatype='EY'");
			sbv18.put("EdorNo", mLPGrpEdorItemSchema.getEdorNo());
			map.put(sbv18,"DELETE");
		}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
			sbv18.sql("delete from ljsgetendorse where 0=(select a from (select (case when SUM(getmoney) is null then 0 else SUM(getmoney) end) as a from ljsgetendorse where endorsementno='?EdorNo?' and feeoperationtype='AA' and feefinatype in ('BF','GL','LX')) b ) and endorsementno='?EdorNo?' and feeoperationtype='AA' and feefinatype='EY'");
			sbv18.put("EdorNo", mLPGrpEdorItemSchema.getEdorNo());
			map.put(sbv18,"DELETE");
		}

	    SQLwithBindVariables sbv19=new SQLwithBindVariables();
		if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
			sbv19.sql("update ljsgetendorse set getmoney=(select (case when SUM(getmoney) is null then 0 else SUM(getmoney) end) from ljsgetendorse where endorsementno='?EdorNo?' and feeoperationtype='AA' and feefinatype in ('BF','GL','LX')) where (select (case when SUM(getmoney) is null then 0 else SUM(getmoney) end) from ljsgetendorse where endorsementno='?EdorNo?' and feeoperationtype='AA' and feefinatype in ('BF','GL','LX'))>0 and endorsementno='?EdorNo?' and feeoperationtype='AA' and feefinatype='EY'");
			sbv19.put("EdorNo", mLPGrpEdorItemSchema.getEdorNo());
			map.put(sbv19,"UPDATE");
		}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
			sbv19.sql("UPDATE 	ljsgetendorse a,(SELECT  (case when SUM(getmoney) is null then 0 else  SUM(getmoney) end) as sumgetmoney,endorsementno  FROM  ljsgetendorse   WHERE  endorsementno='?EdorNo?' AND  	feeoperationtype='AA' AND  	feefinatype IN ('BF', 'GL', 'LX')) b set a.getmoney=b.sumgetmoney where a.endorsementno=b.endorsementno and a.endorsementno='?EdorNo?' AND 	a.feeoperationtype='AA' AND a.feefinatype='EY'");
			sbv19.put("EdorNo", mLPGrpEdorItemSchema.getEdorNo());
			map.put(sbv19,"UPDATE");
		}
        mResult.clear();
        mResult.add(map);

        return true;
    }

}
