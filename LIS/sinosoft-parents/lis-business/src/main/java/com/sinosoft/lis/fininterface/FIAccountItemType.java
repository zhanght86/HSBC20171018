package com.sinosoft.lis.fininterface;
import java.sql.SQLWarning;

import org.apache.log4j.Logger;




import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.lis.db.FIFinItemDefDB;
import com.sinosoft.lis.schema.FIFinItemDefSchema;
import com.sinosoft.lis.db.FIDetailFinItemDefDB;
import com.sinosoft.lis.schema.FICostTypeDefSchema;
import com.sinosoft.lis.vschema.FIDetailFinItemDefSet;
import com.sinosoft.lis.db.FIDetailFinItemCodeDB;
import com.sinosoft.lis.vschema.FIDetailFinItemCodeSet;
import com.sinosoft.lis.schema.FIAboriginalDataSchema;
import com.sinosoft.lis.schema.FIDetailFinItemDefSchema;
import com.sinosoft.lis.schema.FIDetailFinItemCodeSchema;
import com.sinosoft.lis.schema.FIDataTransResultSchema;
import com.sinosoft.lis.db.FIAssociatedItemDefDB;
import com.sinosoft.lis.schema.FIAssociatedItemDefSchema;
import com.sinosoft.lis.vschema.FIAssociatedDirectItemDefSet;
import com.sinosoft.lis.db.FIAssociatedDirectItemDefDB;
import com.sinosoft.lis.schema.FIAssociatedDirectItemDefSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.lis.vschema.FIAssociatedItemDefSet;


/**
 * <p>Title: AccountItemType</p>
 *
 * <p>Description:财务科目和处理规则定义 </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: sinosoft </p>
 *
 * @author
 * @version 1.0
 */
public class FIAccountItemType {
private static Logger logger = Logger.getLogger(FIAccountItemType.class);

    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();
    private FICostTypeDefSchema mFICostTypeDefSchema;
    /** 科目类型定义 */
    private FIFinItemDefSchema mFIFinItemDefSchema;
    /** 明细科目类型定义 */
    private FIDetailFinItemDefSchema mFIDetailFinItemDefSchema;
    private FIDetailFinItemDefSet mFIDetailFinItemDefSet;
    /** 明细科目类型判断条件 */
    private String ItemCondition[][];
    /** 科目关联核算项*/
    private FIAssociatedItemDefSet mFIAssociatedItemDefSet;


    /** 明细科目类型判断条件数值组合对照表 */
    private FIDetailFinItemCodeSet mFIDetailFinItemCodeSet;
    /** 明细科目类型判断条件数值组合对照表索引*/
    private FIDetailFinItemCodeSet[] mFIDetailFinItemCodeSetUint;

    /** 关联核算项目转换处理类 */
    private ValueTrans mValueTrans[];
    /** 科目固定专项定义表*/
    private FIAssociatedDirectItemDefSet mFIAssociatedDirectItemDefSet;
    /** 科目特殊处理类*/
    FIAccItemType mFIAccItemType;

    String VersionNo = "";
    StringBuffer logString = new StringBuffer();
    String ErrInfo = "";
    String ErrType = "";
    private final String enter = "\r\n"; // 换行符

    public LogInfoDeal tLogInfoDeal = null;

    public FIAccountItemType() {
    }

    public StringBuffer GetLogString() {
        return logString;
    }

    public String GetErrInfo() {
        return ErrInfo;
    }

    public String GetErrType() {
        return ErrType;
    }

    public boolean InitItemDefInfo(FICostTypeDefSchema tFICostTypeDefSchema,LogInfoDeal ttLogInfoDeal)
    {
        try
        {
            tLogInfoDeal = ttLogInfoDeal;
            mFICostTypeDefSchema = tFICostTypeDefSchema;
            String VersionNo = tFICostTypeDefSchema.getVersionNo();
            String FinItemID = tFICostTypeDefSchema.getFinItemID();
            FIFinItemDefDB tFIFinItemDefDB = new FIFinItemDefDB();
            tFIFinItemDefDB.setVersionNo(VersionNo);
            tFIFinItemDefDB.setFinItemID(tFICostTypeDefSchema.getFinItemID());
            if (!tFIFinItemDefDB.getInfo())
            {
                buildError("AccountItemType","InitItemDefInfo", "不存在编号为" + FinItemID +  "版本编号为" + VersionNo + "的科目定义");
                tLogInfoDeal.WriteLogTxt("不存在编号为" + FinItemID +  "版本编号为" + VersionNo + "的科目定义" + enter);
                return false;
            }
            if (tFIFinItemDefDB.mErrors.needDealError())
            {
                buildError("AccountItemType","InitItemDefInfo", "编号为" + FinItemID +  "版本编号为" + VersionNo + "的科目定义查询出错");
                tLogInfoDeal.WriteLogTxt("编号为" + FinItemID +  "版本编号为" + VersionNo + "的科目定义查询出错" + enter);
                return false;
            }
            mFIFinItemDefSchema = new FIFinItemDefSchema();
            mFIFinItemDefSchema.setSchema(tFIFinItemDefDB.getSchema());

            if(mFIFinItemDefSchema.getDealMode().equals("1"))
            {
                FIDetailFinItemDefDB tFIDetailFinItemDefDB = new FIDetailFinItemDefDB();
                String tSql = "select * from FIDetailFinItemDef where FinItemID ='?FinItemID?' and versionno = '?VersionNo?' order by JudgementNo";
                SQLwithBindVariables sqlbv=new SQLwithBindVariables();
                sqlbv.sql(tSql);
                sqlbv.put("FinItemID", FinItemID);
                sqlbv.put("VersionNo", VersionNo);
                mFIDetailFinItemDefSet = tFIDetailFinItemDefDB.executeQuery(sqlbv);
                if (tFIDetailFinItemDefDB.mErrors.needDealError()) {
                    buildError("AccountItemType", "InitItemDefInfo",
                               "编号为" + FinItemID + "版本编号为" + VersionNo + "的科目明细判断条件定义查询出错");
                    tLogInfoDeal.WriteLogTxt("编号为" + FinItemID + "版本编号为" + VersionNo +
                                             "的科目判断条件定义查询出错" + enter);
                    return false;
                }

                tSql = "select * from FIDetailFinItemCode where FinItemID ='?FinItemID?' and versionno = '?VersionNo?' order by JudgementNo";
                SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
                sqlbv1.sql(tSql);
                sqlbv1.put("FinItemID", FinItemID);
                sqlbv1.put("VersionNo", VersionNo);
                FIDetailFinItemCodeDB tFIDetailFinItemCodeDB = new FIDetailFinItemCodeDB();
                mFIDetailFinItemCodeSet = tFIDetailFinItemCodeDB.executeQuery(sqlbv1);
                if (tFIDetailFinItemCodeDB.mErrors.needDealError()) {
                    buildError("AccountItemType", "InitItemDefInfo",
                               "编号为" + FinItemID + "版本编号为" + VersionNo + "的科目明细映射定义查询出错");
                    tLogInfoDeal.WriteLogTxt("编号为" + FinItemID + "版本编号为" + VersionNo +
                                             "的科目明细映射定义查询出错" + enter);
                    return false;
                }

                tSql = "select * from FIAssociatedItemDef a where a.VersionNo = '?VersionNo?' and a.AssociatedID in (select AssociatedID from FIInfoFinItemAssociated b where b.VersionNo = '?VersionNo?' and b.FinItemID = '?FinItemID?')";
                SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
                sqlbv2.sql(tSql);
                sqlbv2.put("VersionNo", VersionNo);
                sqlbv2.put("FinItemID", FinItemID);
                FIAssociatedItemDefDB tFIAssociatedItemDefDB = new FIAssociatedItemDefDB();
                mFIAssociatedItemDefSet = tFIAssociatedItemDefDB.executeQuery(sqlbv2);
                if (tFIAssociatedItemDefDB.mErrors.needDealError()) {
                    buildError("AccountItemType", "InitItemDefInfo",
                               "编号为" + FinItemID + "版本编号为" + VersionNo + "的科目关联专项定义查询出错");
                    tLogInfoDeal.WriteLogTxt("编号为" + FinItemID + "版本编号为" + VersionNo +
                                             "的科目关联专项定义查询出错" + enter);
                    return false;
                }

                FIAssociatedDirectItemDefDB tFIAssociatedDirectItemDefDB = new
                        FIAssociatedDirectItemDefDB();
                tFIAssociatedDirectItemDefDB.setVersionNo(VersionNo);
                mFIAssociatedDirectItemDefSet = tFIAssociatedDirectItemDefDB.query();
                if (tFIAssociatedDirectItemDefDB.mErrors.needDealError()) {
                    buildError("AccountItemType", "InitItemDefInfo",
                               "编号为" + FinItemID + "版本编号为" + VersionNo + "的科目固定数据项定义查询出错");
                    tLogInfoDeal.WriteLogTxt("编号为" + FinItemID + "版本编号为" + VersionNo +
                                             "的科目固定数据项定义查询出错" + enter);
                    return false;
                }

                //初始化科目条件信息
                if (!InitItemCondition()) {
                    return false;
                }
                //初始化专项关联信息
                if (!InitInfoAssociated()) {
                    return false;
                }
                //初始化固定关联信息
                if (!InitInfoDirect()) {
                    return false;
                }
            }
            else
            {
                Class tClass = Class.forName(mFIFinItemDefSchema.getDealSpecialClass());
                mFIAccItemType = (FIAccItemType) tClass.newInstance();
                if(!mFIAccItemType.InitInfo(tFICostTypeDefSchema,tLogInfoDeal))
                {
                    this.mErrors.copyAllErrors(mFIAccItemType.mErrors);
                    return false;
                }
            }
        }
        catch (Exception ex)
        {
            buildError("AccountItemType","InitItemDefInfo", "编号为" + tFICostTypeDefSchema.getFinItemID() +  "版本编号为" + tFICostTypeDefSchema.getVersionNo() + "的科目处理类初始化异常，信息为：" + ex.getMessage());
            tLogInfoDeal.WriteLogTxt("编号为" + tFICostTypeDefSchema.getFinItemID() +  "版本编号为" + tFICostTypeDefSchema.getVersionNo() + "的科目处理类初始化异常，信息为：" + ex.getMessage() + enter);
            return false;
        }
        return true;
    }

    public void AddLogString(String lineString)
    {
        logString.append(lineString + enter);
    }

    public FIDataTransResultSchema DealInfo(FIAboriginalDataSchema tFIAboriginalDataSchema)
    {

        FIDataTransResultSchema tFIDataTransResultSchema = new FIDataTransResultSchema(); ;
        try
        {
            if (mFIFinItemDefSchema.getDealMode().equals("1"))
            {
                //封装科目编码信息
                if (!BuildItemCode(tFIAboriginalDataSchema,tFIDataTransResultSchema))
                {
                    return null;
                }

                //封装一些固定转换信息
                if (!SetDirectInfo(tFIAboriginalDataSchema,tFIDataTransResultSchema))
                {
                    return null;
                }

                //封装一些专项信息
                if (!SetAssociatedInfo(tFIAboriginalDataSchema,tFIDataTransResultSchema))
                {
                    return null;
                }

                //封装一些重要信息
                if (!SetImportanceInfo(tFIAboriginalDataSchema,tFIDataTransResultSchema))
                {
                    return null;
                }

            }
            else if (mFIFinItemDefSchema.getDealMode().equals("2"))
            {
                tFIDataTransResultSchema = mFIAccItemType.DealInfo(tFIAboriginalDataSchema);
            }
        }
        catch (Exception ex)
        {
            return null;

        }
        return tFIDataTransResultSchema;
    }


    private boolean InitItemCondition()
    {
        FIAboriginalDataSchema tLIAboriginalDataSchema = new  FIAboriginalDataSchema();
        if(mFIDetailFinItemDefSet.size()>0)
        {
            ItemCondition = new  String[mFIDetailFinItemDefSet.size()][];
            for (int i = 0; i < mFIDetailFinItemDefSet.size(); i++)
            {
                FIDetailFinItemDefSchema tFIDetailFinItemDefSchema = mFIDetailFinItemDefSet.get(i+1);
                String tLevelCondition[] = tFIDetailFinItemDefSchema.getLevelCondition().trim().split(",");
                ItemCondition[i] = new  String[tLevelCondition.length];
                for (int j = 0; j < tLevelCondition.length; j++)
                {
                    String temp = tLevelCondition[j];
                    if (tLIAboriginalDataSchema.getFieldIndex(temp) < 0)
                    {
                        buildError("AccountItemType","InitItemCondition","科目定义编号为"+ mFIFinItemDefSchema.getFinItemID()+ "的明细科目判断条件定义涉及的数据字段" + temp + "在FIAboriginalData表中不存在");
                        tLogInfoDeal.WriteLogTxt("科目定义编号为"+ mFIFinItemDefSchema.getFinItemID()+ "的明细科目判断条件定义涉及的数据字段" + temp + "在FIAboriginalData表中不存在" + enter);
                        return false;
                    }
                    else
                    {
                       ItemCondition[i][j] = temp;
                    }
                }
            }

            if(mFIDetailFinItemCodeSet.size()==0)
            {
                buildError("AccountItemType","InitItemCondition","科目定义编号为"+ mFIFinItemDefSchema.getFinItemID()+ "的明细科目判断条件定义表存在但明细科目映射数据定义为空");
                tLogInfoDeal.WriteLogTxt("科目定义编号为"+ mFIFinItemDefSchema.getFinItemID()+ "的明细科目判断条件定义表存在但明细科目映射数据定义为空" + enter);
                return false;
            }

            mFIDetailFinItemCodeSetUint = new FIDetailFinItemCodeSet[mFIDetailFinItemDefSet.size()];
            int tempIndex=0;
            String tempJudgementNo = mFIDetailFinItemDefSet.get(1).getJudgementNo();
            if(!tempJudgementNo.equals(mFIDetailFinItemCodeSet.get(1).getJudgementNo()))
            {
                buildError("AccountItemType","InitItemCondition","科目定义编号为"+ mFIFinItemDefSchema.getFinItemID()+ "的明细科目判断条件定义与明细科目映射数据定义不匹配，明细科目映射数据可能缺少或多余定义");
                tLogInfoDeal.WriteLogTxt("科目定义编号为"+ mFIFinItemDefSchema.getFinItemID()+ "的明细科目判断条件定义与明细科目映射数据定义不匹配，明细科目映射数据可能缺少或多余定义" + enter);
                return false;
            }

            for(int i = 1;i<=mFIDetailFinItemCodeSet.size();i++)
            {
                 if(i==1)
                 {
                    mFIDetailFinItemCodeSetUint[tempIndex] = new FIDetailFinItemCodeSet();
                 }

                 if(mFIDetailFinItemCodeSet.get(i).getJudgementNo().equals(tempJudgementNo))
                 {
                     mFIDetailFinItemCodeSetUint[tempIndex].add(mFIDetailFinItemCodeSet.get(i));
                 }
                 else
                 {
                     tempIndex++;
                     if(tempIndex>=mFIDetailFinItemDefSet.size())
                     {
                         buildError("AccountItemType","InitItemCondition","科目定义编号为"+ mFIFinItemDefSchema.getFinItemID()+ "的明细科目判断条件定义与明细科目映射数据定义不匹配，明细科目映射数据可能缺少或多余定义");
                         tLogInfoDeal.WriteLogTxt("科目定义编号为"+ mFIFinItemDefSchema.getFinItemID()+ "的明细科目判断条件定义与明细科目映射数据定义不匹配，明细科目映射数据可能缺少或多余定义" + enter);
                         return false;
                     }
                     tempJudgementNo = mFIDetailFinItemCodeSet.get(i).getJudgementNo();
                     if(!tempJudgementNo.equals(mFIDetailFinItemDefSet.get(tempIndex+1).getJudgementNo()))
                     {
                         buildError("AccountItemType","InitItemCondition","科目定义编号为"+ mFIFinItemDefSchema.getFinItemID()+ "的明细科目判断条件定义与明细科目映射数据定义不匹配，明细科目映射数据可能缺少或多余定义");
                         tLogInfoDeal.WriteLogTxt("科目定义编号为"+ mFIFinItemDefSchema.getFinItemID()+ "的明细科目判断条件定义与明细科目映射数据定义不匹配，明细科目映射数据可能缺少或多余定义" + enter);
                         return false;
                     }
                     mFIDetailFinItemCodeSetUint[tempIndex] = new FIDetailFinItemCodeSet();
                     mFIDetailFinItemCodeSetUint[tempIndex].add(mFIDetailFinItemCodeSet.get(i));
                 }

            }

        }
        return true;
    }

    private boolean InitInfoAssociated()
    {
        FIAboriginalDataSchema tFIAboriginalDataSchema = new  FIAboriginalDataSchema();
        FIDataTransResultSchema tFIDataTransResultSchema = new  FIDataTransResultSchema();
        if(mFIAssociatedItemDefSet.size()>0)
        {
            for (int i = 0; i < mFIAssociatedItemDefSet.size(); i++)
            {
                FIAssociatedItemDefSchema tFIAssociatedItemDefSchema = mFIAssociatedItemDefSet.get(i+1);
                String tempFountainID = tFIAssociatedItemDefSchema.getSourceColumnID();
                String tempTargetID = tFIAssociatedItemDefSchema.getColumnID();
                if (tFIAboriginalDataSchema.getFieldIndex(tempFountainID) < 0)
                {
                    buildError("AccountItemType","InitInfoAssociated","科目定义编号为"+ mFIFinItemDefSchema.getFinItemID()+ "的科目专项定义涉及的源数据字段" + tempFountainID + "在FIAboriginalData表中不存在");
                    tLogInfoDeal.WriteLogTxt("科目定义编号为"+ mFIFinItemDefSchema.getFinItemID()+ "的科目专项定义涉及的源数据字段" + tempFountainID + "在FIAboriginalData表中不存在" + enter);
                    return false;
                }
                if(tFIDataTransResultSchema.getFieldIndex(tempTargetID)<0)
                {
                    buildError("AccountItemType","InitInfoAssociated","科目定义编号为"+ mFIFinItemDefSchema.getFinItemID()+ "的科目专项定义涉及的目标数据字段" + tempTargetID + "在FIDataTransResult表中不存在");
                    tLogInfoDeal.WriteLogTxt("科目定义编号为"+ mFIFinItemDefSchema.getFinItemID()+ "的科目专项定义涉及的目标数据字段" + tempTargetID + "在FIDataTransResult表中不存在" + enter);
                    return false;
                }
            }
        }
        return true;
    }


    private boolean InitInfoDirect()
    {
        FIAboriginalDataSchema tFIAboriginalDataSchema = new  FIAboriginalDataSchema();
        FIDataTransResultSchema tFIDataTransResultSchema = new  FIDataTransResultSchema();
        if(mFIAssociatedDirectItemDefSet.size()>0)
        {
            for (int i = 0; i < mFIAssociatedDirectItemDefSet.size(); i++)
            {
                FIAssociatedDirectItemDefSchema tFIAssociatedDirectItemDefSchema = mFIAssociatedDirectItemDefSet.get(i+1);
                String tempFountainID = tFIAssociatedDirectItemDefSchema.getSourceColumnID();
                String tempTargetID = tFIAssociatedDirectItemDefSchema.getColumnID();
                if (tFIAboriginalDataSchema.getFieldIndex(tempFountainID) < 0)
                {
                    buildError("AccountItemType","InitInfoAssociated","科目定义编号为"+ mFIFinItemDefSchema.getFinItemID()+ "的科目固定数据定义涉及的源数据字段" + tempFountainID + "在FIAboriginalData表中不存在");
                    tLogInfoDeal.WriteLogTxt("科目定义编号为"+ mFIFinItemDefSchema.getFinItemID()+ "的科目固定数据定义涉及的源数据字段" + tempFountainID + "在FIAboriginalData表中不存在" + enter);
                    return false;
                }
                if(tFIDataTransResultSchema.getFieldIndex(tempTargetID)<0)
                {
                    buildError("AccountItemType","InitInfoAssociated","科目定义编号为"+ mFIFinItemDefSchema.getFinItemID()+ "的科目固定数据定义涉及的目标数据字段" + tempTargetID + "在FIDataTransResult表中不存在");
                    tLogInfoDeal.WriteLogTxt("科目定义编号为"+ mFIFinItemDefSchema.getFinItemID()+ "的科目固定数据定义涉及的目标数据字段" + tempTargetID + "在FIDataTransResult表中不存在" + enter);
                    return false;
                }
            }
        }
        return true;
    }




    private boolean BuildItemCode(FIAboriginalDataSchema tFIAboriginalDataSchema,FIDataTransResultSchema tFIDataTransResultSchema)
    {
        ErrType = "N-01";
        try
        {
            String maincode = mFIFinItemDefSchema.getItemMainCode();
            int ii = 0;
            if (mFIDetailFinItemDefSet.size() > 0)
            {
                String nextJudgementNo = mFIDetailFinItemDefSet.get(1).getJudgementNo();
                while(!nextJudgementNo.equals("end"))
                {
                    ii ++;
                    if(ii>20)
                    {
                        Exception ex = new Exception("明细科目生成定义可能存在死锁");
                        throw ex;
                    }
                    int index = this.getDetailFinItemIndex(nextJudgementNo)-1;
                    String SegCode = "";
                    for (int j = 0; j < ItemCondition[index].length; j++) {
                        String temp = tFIAboriginalDataSchema.getV(ItemCondition[index][j]).trim();
                        if (temp.equals("null"))
                        {
                            ErrInfo = "类型为" + tFIAboriginalDataSchema.getCostID() + "的业务数据数据在生成科目代码为" + maincode + "的财务数据信息时出错，因为必要信息字段" + ItemCondition[index][j].trim() + "值为空" + ",业务索引号码为：" + tFIAboriginalDataSchema.getBusinessNo();
                            buildError("FIAccountItemType","BuildItemCode",ErrInfo);
                            tLogInfoDeal.WriteLogTxt(ErrInfo + enter);
                            return false;
                        }
                        if (j != ItemCondition[index].length - 1) {
                            temp += ",";
                        }
                        SegCode += temp;
                    }
                    FIDetailFinItemCodeSchema tFIDetailFinItemCodeSchema = TRansItemCode(mFIFinItemDefSchema.getFinItemID(),nextJudgementNo,SegCode);
                    String partcode = tFIDetailFinItemCodeSchema.getLevelCode();
                    if ((partcode == null || partcode.equals("") || partcode.equals("null"))&&(tFIDetailFinItemCodeSchema.getJudgementNo()==null||tFIDetailFinItemCodeSchema.getJudgementNo().equals("") || tFIDetailFinItemCodeSchema.getJudgementNo().equals("null")))
                    {
                        ErrInfo = "类型为" + tFIAboriginalDataSchema.getCostID() +
                                   "的业务数据数据在生成科目代码为" + maincode +
                                   "的财务数据信息时出错，" +
                                   "的明细科目判断编号为" + nextJudgementNo + "的信息" + SegCode +
                                   "无对应转换值"+ ",业务索引号码为：" + tFIAboriginalDataSchema.getBusinessNo();
                        buildError("FIAccountItemType","BuildItemCode",ErrInfo);
                        tLogInfoDeal.WriteLogTxt(ErrInfo + enter);
                        return false;
                    }
                    else
                    {
                        if(!(partcode == null || partcode.equals("") || partcode.equals("null")))
                        {
                            maincode += partcode;
                        }
                    }
                    if(tFIDetailFinItemCodeSchema.getNextJudgementNo()==null||tFIDetailFinItemCodeSchema.getNextJudgementNo().equals("") || tFIDetailFinItemCodeSchema.getNextJudgementNo().equals("null") || tFIDetailFinItemCodeSchema.getNextJudgementNo().equals("END"))
                    {
                        nextJudgementNo="end";
                    }
                    else
                    {
                        nextJudgementNo=tFIDetailFinItemCodeSchema.getNextJudgementNo();
                    }
                }
            }
            tFIDataTransResultSchema.setAccountCode(maincode);
            return true;
        }
        catch (Exception ex)
        {
            ErrInfo = "类型为" + tFIAboriginalDataSchema.getCostID() +
                       "的业务数据数据在生成科目代码为" + mFIFinItemDefSchema.getItemMainCode() + "的财务数据明细科目代码信息时出错，原因是：" + ex.getMessage()+ ",业务索引号码为：" + tFIAboriginalDataSchema.getBusinessNo();
            buildError("FIAccountItemType","BuildItemCode",ErrInfo);
            tLogInfoDeal.WriteLogTxt(ErrInfo + enter);
            return false;
        }

    }

    private boolean SetAssociatedInfo(FIAboriginalDataSchema tFIAboriginalDataSchema,FIDataTransResultSchema tFIDataTransResultSchema)
    {
        ErrType = "N-03";
        try
        {
            for(int i=0;i<mFIAssociatedItemDefSet.size();i++)
            {
                FIAssociatedItemDefSchema tFIAssociatedItemDefSchema = mFIAssociatedItemDefSet.get(i+1);
                String FoundID = tFIAssociatedItemDefSchema.getSourceColumnID();
                String TargetID = tFIAssociatedItemDefSchema.getColumnID();
                String info = tFIAboriginalDataSchema.getV(FoundID);
                if(info == null || info.equals("") || info.equals("null"))
                {
                    ErrInfo = "类型为" + tFIAboriginalDataSchema.getCostID() +
                               "的业务数据数据在生成科目代码为" + mFIFinItemDefSchema.getItemMainCode() + "的财务数据专项数据信息时出错，原因是专项：" + tFIAssociatedItemDefSchema.getAssociatedID() + "设置信息为空,业务索引号码为：" + tFIAboriginalDataSchema.getBusinessNo();
                    buildError("FIAccountItemType","SetDirectInfo",ErrInfo);
                    tLogInfoDeal.WriteLogTxt(ErrInfo + enter);
                    return false;
                }
                tFIDataTransResultSchema.setV(TargetID,tFIAboriginalDataSchema.getV(FoundID));
            }
        }
        catch (Exception ex)
        {
            ErrInfo = "类型为" + tFIAboriginalDataSchema.getCostID() +
                       "的业务数据数据在生成科目代码为" + mFIFinItemDefSchema.getItemMainCode() + "的财务数据专项数据信息时出错，原因是：" + ex.getMessage()+ ",业务索引号码为：" + tFIAboriginalDataSchema.getBusinessNo();
            buildError("FIAccountItemType","SetDirectInfo",ErrInfo);
            tLogInfoDeal.WriteLogTxt(ErrInfo + enter);
            return false;
        }
        return true;
    }



    private boolean SetDirectInfo(FIAboriginalDataSchema tFIAboriginalDataSchema, FIDataTransResultSchema tFIDataTransResultSchema)
    {
        ErrType = "N-02";
        try
        {
            for(int i=0;i<mFIAssociatedDirectItemDefSet.size();i++)
            {
                FIAssociatedDirectItemDefSchema tFIAssociatedDirectItemDefSchema = mFIAssociatedDirectItemDefSet.get(i+1);
                String FoundID = tFIAssociatedDirectItemDefSchema.getSourceColumnID();
                String TargetID = tFIAssociatedDirectItemDefSchema.getColumnID();
                String info = tFIAboriginalDataSchema.getV(FoundID);
                if(info == null || info.equals("") || info.equals("null"))
                {
                    ErrInfo = "类型为" + tFIAboriginalDataSchema.getCostID() +
                               "的业务数据数据在生成科目代码为" + mFIFinItemDefSchema.getItemMainCode() + "的财务数据固定数据信息时出错，原因是会计分录固定数据：" + tFIAssociatedDirectItemDefSchema.getColumnID()+ "设置信息为空,业务索引号码为：" + tFIAboriginalDataSchema.getBusinessNo();
                    buildError("FIAccountItemType","SetDirectInfo",ErrInfo);
                    tLogInfoDeal.WriteLogTxt(ErrInfo + enter);
                    return false;
                }
                tFIDataTransResultSchema.setV(TargetID,tFIAboriginalDataSchema.getV(FoundID));
            }
        }
        catch (Exception ex)
        {
            ErrInfo = "类型为" + tFIAboriginalDataSchema.getCostID() +
                       "的业务数据数据在生成科目代码为" + mFIFinItemDefSchema.getItemMainCode() + "的财务数据固定数据信息时出错，原因是：" + ex.getMessage()+ ",业务索引号码为：" + tFIAboriginalDataSchema.getBusinessNo();
            buildError("FIAccountItemType","SetDirectInfo",ErrInfo);
            tLogInfoDeal.WriteLogTxt(ErrInfo + enter);
            return false;
        }
        return true;
    }

    private boolean SetImportanceInfo(FIAboriginalDataSchema tFIAboriginalDataSchema,FIDataTransResultSchema tFIDataTransResultSchema)
    {
        ErrType = "N-04";
        tFIDataTransResultSchema.setFSerialNo(tFIAboriginalDataSchema.getASerialNo());
        tFIDataTransResultSchema.setASerialNo(tFIAboriginalDataSchema.getASerialNo());
        tFIDataTransResultSchema.setBatchNo(tFIAboriginalDataSchema.getBatchNo());
        tFIDataTransResultSchema.setCertificateID(tFIAboriginalDataSchema.getCertificateID());
        tFIDataTransResultSchema.setCostID(tFIAboriginalDataSchema.getCostID());
        tFIDataTransResultSchema.setFinItemType(mFICostTypeDefSchema.getFinItemType());
        tFIDataTransResultSchema.setSumMoney(tFIAboriginalDataSchema.getSumActuMoney());
        tFIDataTransResultSchema.setAccountDate(tFIAboriginalDataSchema.getAccountDate());

        return true;
    }


    private FIDetailFinItemCodeSchema TRansItemCode(String FinItemID, String JudgementNo, String LevelCondition)
    {

        FIDetailFinItemCodeSchema tFIDetailFinItemCodeSchema = new FIDetailFinItemCodeSchema();
        int index = 0;
        for (int i = 1; i <= mFIDetailFinItemDefSet.size(); i++)
        {
            if (mFIDetailFinItemDefSet.get(i).getJudgementNo().equals(JudgementNo))
            {
                index = i - 1;
                break;
            }
            if (i == mFIDetailFinItemDefSet.size())
            {
                return tFIDetailFinItemCodeSchema;
            }
        }

        //兼容科目由顺序段值拼接模式
        if(mFIDetailFinItemCodeSetUint[index].size()==1 && mFIDetailFinItemCodeSetUint[index].get(1).getLevelConditionValue().equals("original"))
        {
            tFIDetailFinItemCodeSchema.setSchema(mFIDetailFinItemCodeSetUint[index].get(1));
            tFIDetailFinItemCodeSchema.setLevelCode(LevelCondition + tFIDetailFinItemCodeSchema.getLevelCode());
            return tFIDetailFinItemCodeSchema;
        }

        for (int i = 1; i <= mFIDetailFinItemCodeSetUint[index].size(); i++)
        {
            FIDetailFinItemCodeSchema tt = mFIDetailFinItemCodeSetUint[index].get(i);
            if (tt.getFinItemID().trim().equals(FinItemID.trim()) &&
                tt.getJudgementNo().trim().equals(JudgementNo) &&
                tt.getLevelConditionValue().trim().equals
                (LevelCondition.trim()))
            {
                tFIDetailFinItemCodeSchema.setSchema(tt);
                break;
            }
        }
        return tFIDetailFinItemCodeSchema;
    }



    private int getDetailFinItemIndex(String JudgementNo) throws Exception
    {
        for(int i=1;i<=mFIDetailFinItemDefSet.size();i++)
        {
            if(mFIDetailFinItemDefSet.get(i).getJudgementNo().equals(JudgementNo))
            {
                return i;
            }
        }
        Exception e = new Exception("科目定义编号为" + this.mFIFinItemDefSchema.getFinItemID()+ "处理实例中不存在判断编号为"+ JudgementNo + "数据准备信息" );
        throw e;
    }

    private void buildError(String szModuleName, String szFunc, String szErrMsg)
    {
        CError cError = new CError();
        cError.moduleName = szModuleName;
        cError.functionName = szFunc;
        cError.errorMessage = szErrMsg;
        this.mErrors.addOneError(cError);
        logger.debug(szErrMsg);
    }

    public static void main(String[] args) {

    }
}
