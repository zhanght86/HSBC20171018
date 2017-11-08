package com.sinosoft.lis.fininterface;
import org.apache.log4j.Logger;

import com.sinosoft.utility.VData;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.CError;
import com.sinosoft.lis.schema.FIInfoFinItemAssociatedSchema;
import com.sinosoft.lis.db.FIAssociatedItemDefDB;
import com.sinosoft.lis.schema.FIAboriginalDataSchema;
import com.sinosoft.lis.pubfun.PubCalculator;
import com.sinosoft.lis.schema.FIAssociatedItemDefSchema;
import com.sinosoft.utility.ExeSQL;

/**
 * <p>Title: ValueTrans</p>
 *
 * <p>Description: 代码转换类 </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: sinosoft</p>
 *
 * @author jw
 * @version 1.0
 */
public class ValueTrans {
private static Logger logger = Logger.getLogger(ValueTrans.class);

    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();
    public FIInfoFinItemAssociatedSchema mFIInfoFinItemAssociatedSchema ;
    public FIAssociatedItemDefSchema mFIAssociatedItemDefSchema ;

    /** 存储转换数据对照表*/
    //private LICodeTransSet mLICodeTransSet;
    /** 转换处理类*/
    private TransType mTransTypeClass;
    /** 转换处理标志*/
    private String mTransMode;
    public String mFoundID;
    public String mTargetID;
    String logString = "";
    String ErrInfo = "";
    String ErrType = "";
    private final String enter = "\r\n"; // 换行符
    public ValueTrans() {
    }

    public String GetLogString() {
            return logString;
    }

    public String GetErrInfo() {
        return ErrInfo;
    }

    public String GetErrType() {
        return ErrType;
    }

    public boolean InitInfo(FIInfoFinItemAssociatedSchema tFIInfoFinItemAssociatedSchema,String FoundID,String TargetID)
    {
       try{
           mFIInfoFinItemAssociatedSchema = tFIInfoFinItemAssociatedSchema;
           mFoundID = FoundID;
           mTargetID = TargetID;
           FIAssociatedItemDefDB tFIAssociatedItemDefDB = new FIAssociatedItemDefDB();
           tFIAssociatedItemDefDB.setVersionNo(tFIInfoFinItemAssociatedSchema.getVersionNo());
           tFIAssociatedItemDefDB.setAssociatedID(tFIInfoFinItemAssociatedSchema.getAssociatedID());
           if(!tFIAssociatedItemDefDB.getInfo()){
               logger.debug("FIAssociatedItemDef科目专项没有找到");
               ErrInfo = "在FIAssociatedItemDef(科目专项定义表)中没有专项号码为"+tFIInfoFinItemAssociatedSchema.getAssociatedID()+"的记录";
               ErrType = "03";
               return false;
           }
           mTransMode = tFIAssociatedItemDefDB.getTransFlag();
           mFIAssociatedItemDefSchema = tFIAssociatedItemDefDB.getSchema();
           if (mTransMode == null || mTransMode.equals("")) {
               ErrInfo = "在FIAssociatedItemDef(科目专项定义表)中专项号码为"+tFIInfoFinItemAssociatedSchema.getAssociatedID()+"的记录没有描述转换方式";
               ErrType = "03";
               return false;
           }

//           if (mTransMode.equals("N")) {
//
//           } else if (mTransMode.equals("S")) {
////               LICodeTransDB tLICodeTransDB = new LICodeTransDB();
////               tLICodeTransDB.setCodeType(tLIInfoFinItemAssociatedSchema.
////                                          getTransCode());
////               mLICodeTransSet = tLICodeTransDB.query();
////               if (tLICodeTransDB.mErrors.needDealError()) {
////                   buildError("InitInfo",
////                              "查询转换编码" +
////                              tLIInfoFinItemAssociatedSchema.getTransCode() +
////                              "出错");
////                   return false;
////               } else {
////                   if (mLICodeTransSet.size() == 0) {
////                       buildError("InitInfo",
////                                  "转换编码" +
////                                  tLIInfoFinItemAssociatedSchema.getTransCode() +
////                                  "未定义");
////                       return false;
////                   }
////               }
//           } else if (mTransMode.equals("C")) {
//               Class tClass = Class.forName(tFIAssociatedItemDefDB.getTransClass());
//               mTransTypeClass = (TransType) tClass.newInstance();
//           } else {
//               mTransMode = "N";
//           }
           return true;
       }
       catch(Exception ex)
       {
           ex.printStackTrace();
           return false;
       }
    }

    public void AddLogString(String lineString) {
                logString = logString + lineString + enter;
    }

    public String TransDeal(String TransValue,String TargetID,FIAboriginalDataSchema tFIAboriginalDataSchema) throws Exception
    {
       String resultValue = "";
        try {
            if (mTransMode.equals("N")) {
                resultValue = TransValue;
            } else if (mTransMode.equals("S")) {
                PubCalculator tPubCalculator = new PubCalculator();
                tPubCalculator.addBasicFactor("StandByString1",
                                              TargetID);
                tPubCalculator.addBasicFactor("StringInfo09", TransValue);

                String sql = mFIAssociatedItemDefSchema.getTransSQL();
                if (sql == null && sql.equals("")) {
                    logger.debug("DistillSQL没有定义");
                    return null;
                }
                tPubCalculator.setCalSql(sql);
                sql = tPubCalculator.calculateEx();
                AddLogString(sql);
                ExeSQL tExeSQL = new ExeSQL();
                resultValue = tExeSQL.getOneValue(sql);
            } else if (mTransMode.equals("C")) {
                resultValue = mTransTypeClass.transInfo(TransValue,
                        tFIAboriginalDataSchema);
            }
        }
        catch(Exception ex)
        {
           throw ex;
        }
        return resultValue;
    }



    public static void main(String[] args) {

//
//        LIInfoFinItemAssociatedSchema  tt = new   LIInfoFinItemAssociatedSchema();
//        LIAboriginalDataSchema tLIAboriginalDataSchema = new  LIAboriginalDataSchema();
//
//        tt.setTransFlag("3");
//        tt.setTransCode("test");
//        tt.setTransClass("com.sinosoft.lis.fininterface.TransItem.testu88");
//
//
//        try {
//
//             ValueTrans tValueTrans = new ValueTrans();
//             if(!tValueTrans.InitInfo(tt))
//             {
//                 logger.debug("111");
//                 logger.debug("222");
//                 logger.debug("333");
//                 logger.debug("444");
//                 logger.debug("555");
//             }
//             String temp = "fff";
//             temp = tValueTrans.TransDeal(temp,tLIAboriginalDataSchema);
//             logger.debug(temp);
//        } catch (Exception ex) {
//            logger.debug(ex.getMessage());
//        }

    }

}
