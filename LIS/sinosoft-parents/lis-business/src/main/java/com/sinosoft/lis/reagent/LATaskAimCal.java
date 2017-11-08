
package com.sinosoft.lis.reagent;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.*;
import com.sinosoft.lis.pubfun.Calculator;
import com.sinosoft.lis.pubfun.HashReport;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vdb.*;
import com.sinosoft.lis.vschema.*;

import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.Schema;
import com.sinosoft.utility.SchemaSet;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;
import java.lang.ref.*;
import java.lang.reflect.*;
/**
 * <p>Title: </p>
 * <p>Description: 指标计算保存公共类 </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */
public class LATaskAimCal
{
private static Logger logger = Logger.getLogger(LATaskAimCal.class);

    private String mTaskID = "";
    private TransferData mTransferData = new TransferData();
    private TransferData mXHTransferData = new TransferData();
    private SchemaSet mSchemaSet = null; //= new SchemaSet();
    private MMap mMap = new MMap();
    private VData mXHVData = new VData();
    private String mTableName = "";
    private String mCalType = "";

    public LATaskAimCal()
    {
    }

    public static void main(String[] args)
    {
        LATaskAimCal tLATaskAimCal = new LATaskAimCal();
        TransferData tTransferData = new TransferData();
        tTransferData.setNameAndValue("YearMonth", "200612");
        tTransferData.setNameAndValue("ManageCom", "8613");
        VData tVData = new VData();
        tVData.add(tTransferData);
        tVData.add("FinishRate");
        MMap map = new MMap();

        //        TransferData ttTransferData = new TransferData();
        //        ttTransferData.setNameAndValue("AgentCode","");
        //        ttTransferData.setNameAndValue("branchtype","");
        VData ttVData = new VData();
        ttVData.add("AgentCode");
        ttVData.add("branchtype");
        String sql =
            " select agentcode,branchtype from laagent where branchtype='5' and managecom like '8613%' "
            + " and (outworkdate is null or outworkdate >(select enddate from lastatsegment where yearmonth='200612' and stattype='1' ))";
        SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
        sqlbv2.sql(sql);
        map.put(ttVData, sqlbv2);
        tVData.add(map);
        tVData.add("000001");

        //        tLATaskAimCal.submitData(tVData, "test");
    }

    public SchemaSet getResultSchemaSet()
    {
        return this.mSchemaSet;
    }

    public boolean submitData()
    {
        //        if (!getInputData(tInputData))
        //        {
        //            return false;
        //        }
        if(!dealTaskDescribe())
        {
              return false;
        }
        if (!getXHPara())
        {
            return false;
        }
        if (!dealData())
        {
            return false;
        }
        return true;
    }

    /**
     * 获取描述信息：目标表和计算类型
     */
    private boolean dealTaskDescribe()
    {
        LATaskDescribeDB tLATaskDescribeDB = new LATaskDescribeDB();
        tLATaskDescribeDB.setTaskID(this.mTaskID);
        if (!tLATaskDescribeDB.getInfo())
        {
            return false;
        }
        this.mCalType = tLATaskDescribeDB.getCalType();
        this.mTableName = tLATaskDescribeDB.getTableName();
        return true;
    }
    /**
     *获取循环解析参数
     * 循环变量存放在一个MMap变量中
     * for example：
     * String sql =
                " select agentcode,BranchType from laagent where branchtype='4' and managecom like '"
                + managecom + "%' "
                + " and (outworkdate is null or outworkdate >(select enddate from lastatsegment where yearmonth='"
                + yearmonth + "' and stattype='1' )) and employdate <=(select startdate from lastatsegment where yearmonth='"+yearmonth+"' and stattype='1' )  order by managecom";
     * MMap map = new MMap();
     * VData XHVData = new VData();
     * XHVData.add("AgentCode");
     * XHVData.add("BranchType");
     * map.put(XHVData, sql);
     * 函数执行完成后在mXHVData中加入成员每一个TransferData类型
     * 而每一个TransferData中包含两组 键－－值 如：AgentCode－－8611000001和 BranchType－－4
     */
    private boolean getXHPara()
    {
        try
        {
            Class tClass = Class.forName("com.sinosoft.lis.vschema."
                    + this.mTableName + "Set");
            mSchemaSet = (SchemaSet) tClass.newInstance();
            if ((this.mMap != null) && (this.mMap.keySet().size() != 0))
            {
                Set set = this.mMap.keySet();
                for (int i = 0; i < set.size(); i++)
                {
                    VData tVData = (VData) this.mMap.getOrder().get(String
                            .valueOf(i + 1));
                    SQLwithBindVariables sqlbva = new SQLwithBindVariables();
                    if(this.mMap.get(tVData) instanceof SQLwithBindVariables){
                    	sqlbva = (SQLwithBindVariables)this.mMap.get(tVData);
                    }else{
                    	String Sql = (String) this.mMap.get(tVData);
                    	sqlbva.sql(Sql);
                    }
                    
//                    logger.debug("sql:" + Sql);
                    ExeSQL tExe = new ExeSQL();
                    SSRS tSSRS = new SSRS();
                    tSSRS = tExe.execSQL(sqlbva);

                    if (tVData.size() != tSSRS.getMaxCol())
                    {
                        return false;
                    }
                    for (int j = 1; j <= tSSRS.getMaxRow(); j++)
                    {
                        TransferData colTransferData = new TransferData();
                        for (int k = 1; k <= tSSRS.getMaxCol(); k++)
                        {
                            colTransferData.setNameAndValue((String) tVData.get(k
                                    - 1), tSSRS.GetText(j, k));
                        }
                        this.mXHVData.add(colTransferData);
                    }
                }
            }
        }
        catch (Exception e)
        {
            return false;
        }
        return true;
    }

    private boolean dealData()
    {
        try
        {
            if (this.mCalType.equals("A"))
            {
                Iterator tIterator = this.mXHVData.iterator();
                //循环遍历循环变量集
                while (tIterator.hasNext())
                {
                    //将固定解析变量和循环解析变量和成为本次计算的解析变量集
                    VData tVData = new VData();
                    TransferData tTransferData = new TransferData();
                    tTransferData = (TransferData) tIterator.next();
                    Vector tVector = this.mTransferData.getValueNames();
                    for (int i = 0; i < tVector.size(); i++)
                    {
                        tTransferData.setNameAndValue((String) tVector.get(i),
                            this.mTransferData.getValueByName(
                                (String) tVector.get(i)));
                    }
                    tVData.add(tTransferData);

                    Class tClass = Class.forName("com.sinosoft.lis.schema."
                            + this.mTableName + "Schema");
                    Schema tSchema = (Schema) tClass.newInstance();
                    LATaskAimSchemaCal tLATaskAimSchemaCal = new LATaskAimSchemaCal(tSchema);
                    if (!tLATaskAimSchemaCal.submitData(tVData, ""))
                    {
                        return false;
                    }
                    this.mSchemaSet.add(tLATaskAimSchemaCal.getResultSchema());
                }
            }
            else if (this.mCalType.equals("B"))
            {
                Class tClass = Class.forName("com.sinosoft.lis.vschema."
                        + this.mTableName + "Set");
                VData tVData = new VData();
                tVData.add(this.mTransferData);
                SchemaSet tSchemaSet = (SchemaSet) tClass.newInstance();
                LATaskAimSchemaCal tLATaskAimSchemaCal = new LATaskAimSchemaCal(tSchemaSet);
                if (!tLATaskAimSchemaCal.submitData(tVData, ""))
                {
                    return false;
                }
                this.mSchemaSet.add(tLATaskAimSchemaCal.getResultSchemaSet());
            }
            else if (this.mCalType.equals("C"))
            {
                  Iterator tIterator = this.mXHVData.iterator();
                  while (tIterator.hasNext())
                  {
//                        Class tClass = Class.forName("com.sinosoft.lis.schema."
//                            + this.mTableName + "DB");
                        VData tVData = new VData();
                        TransferData tPTransferData = new TransferData();//主键
                        //tPTransferData = (TransferData) tIterator.next();
                        TransferData tTransferData = new TransferData();
                        tTransferData = (TransferData) tIterator.next();
                        Vector ttVector = tTransferData.getValueNames();
                        for(int k = 0;k<ttVector.size();k++)
                        {
                              tPTransferData.setNameAndValue((String) ttVector.get(k),(String)tTransferData.getValueByName((String) ttVector.get(k)));
                        }

                        Vector tVector = this.mTransferData.getValueNames();
                        for (int i = 0; i < tVector.size(); i++)
                        {
                              tTransferData.setNameAndValue((String) tVector.get(i),
                              this.mTransferData.getValueByName(
                              (String) tVector.get(i)));
                        }
                        tVData.add(tTransferData);
                        Class tClass = Class.forName("com.sinosoft.lis.db."
                            + this.mTableName + "DB");
                        Schema tSchema = (Schema)tClass.newInstance();

                        Vector tPVector = tPTransferData.getValueNames();
                        for(int j = 0 ; j <tPVector.size();j++)
                        {
                              tSchema.setV((String)tPVector.get(j),(String)tPTransferData.getValueByName((String)tPVector.get(j)));
                        }
//                        Class[] paramentType = new Class[1];
//                        Object[] paraments = new Object[1];
                        Constructor tConstructor = tClass.getConstructor(null);
                        Object newObject = tConstructor.newInstance(null);
                        Method addMethod = newObject.getClass().getMethod("getInfo",
                                    null);
//                        paraments[0] = tSchema;
                        Object resultObject = addMethod.invoke((Object)tSchema, null);
                        logger.debug("over!!");

//                        Class tClass = Class.forName("com.sinosoft.lis.schema."
//                                    + this.mTableName + "Schema");
//                        Schema tSchemaSet = (SchemaSet) tClass.newInstance();
                        LATaskAimSchemaCal tLATaskAimSchemaCal = new LATaskAimSchemaCal(tSchema);
                        if (!tLATaskAimSchemaCal.submitData(tVData, ""))
                        {
                              return false;
                        }
                        this.mSchemaSet.add(tLATaskAimSchemaCal.getResultSchema());
                  }
            }
	    else if (this.mCalType.equals("D"))
	    {
		    VData tVData = new VData();
                    tVData.add(this.mTransferData);
		    tVData.add(this.mTaskID);
		    Class tClass = Class.forName("com.sinosoft.lis.schema."
			    + this.mTableName + "Schema");
		    Schema tSchema = (Schema) tClass.newInstance();
		    LATaskAimSchemaCal tLATaskAimSchemaCal = new LATaskAimSchemaCal(tSchema);
		    if (!tLATaskAimSchemaCal.submitData(tVData, ""))
		    {
			return false;
		    }
		    this.mSchemaSet.add(tLATaskAimSchemaCal.getResultSchema());
		}
            else
            {
                  throw new Exception("没有这种计算类型");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    //通用计算指标
    public void setCommomPara(TransferData tTransferData)
    {
        this.mTransferData = tTransferData;
    }

    //循环计算指标
    public void setMap(MMap tMMap)
    {
        this.mMap = tMMap;
    }

    //任务号
    public void setTaskID(String tTaskID)
    {
        this.mTaskID = tTaskID;
    }

    //表名
    public void setTableName(String tTableName)
    {
        this.mTableName = tTableName;
    }

    //计算类型
    public void setCalType(String tCalType)
    {
        this.mCalType = tCalType;
    }

    private boolean getInputData(VData tInputData)
    {
        try
        {
            this.mTransferData = (TransferData) tInputData.get(0); //通用计算指标
            this.mTableName = (String) tInputData.get(1);
            this.mMap = (MMap) tInputData.get(2); //循环计算指标
            this.mTaskID = (String) tInputData.get(3);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * <p>Title: 计算指标，返回schema,或者返回set</p>
     * <p>Description: </p>
     * <p>Copyright: Copyright (c) 2003</p>
     * <p>Company: </p>
     * @author unascribed
     * @version 1.0
     */
    class LATaskAimSchemaCal //implements LATaskAimCalSetParaInterface
    {
    	//private static Logger logger = Logger.getLogger(LATaskAimCal.class);

        private String miTaskID = "";
        private TransferData miTransferData = new TransferData();

        //    private LATaskAimCalSetParaInterface mLATaskAimCalSetParaInterface = null;
        private Calculator mCalculator = null;
        private Schema mSchema = null;
        private SchemaSet mSchemaSet = null;

        //        public LATaskAimSchemaCal()
        //        {
        //        }
        public LATaskAimSchemaCal(Schema tSchema)
        {
            this.mSchema = tSchema;
        }

        public LATaskAimSchemaCal(SchemaSet tSchemaSet)
        {
            this.mSchemaSet = tSchemaSet;
        }

        //    public static void main(String[] args)
        //    {
        //        LATaskAimCal LATaskAimCal1 = new LATaskAimCal();
        //    }
        public Schema getResultSchema()
        {
            return this.mSchema;
        }

        public SchemaSet getResultSchemaSet()
        {
            return this.mSchemaSet;
        }

        public boolean submitData(VData tInputData, String tOperate)
        {
            if (!getInputData(tInputData))
            {
                return false;
            }
            if (!dealData())
            {
                return false;
            }
            return true;
        }

        private boolean dealData()
        {
            this.mCalculator = buildParaInterface();
            if (this.mCalculator == null)
            {
                return false;
            }

            LATaskAimDB tLATaskAimDB = new LATaskAimDB();

            //            tLATaskAimDB.setTaskID(mTaskID);
            LATaskAimSet tLATaskAimSet = new LATaskAimSet();
            SQLwithBindVariables sqlbv=new SQLwithBindVariables();
            sqlbv.sql("select * from lataskaim where taskid = '" + "?mTaskID?"
                    + "' order by CalOrder");
            sqlbv.put("mTaskID", mTaskID);
            tLATaskAimSet = tLATaskAimDB.executeQuery(sqlbv);
            try
            {
                //            Class tSchemaClass = Class.forName("com.sinosoft.lis.schema."
                //                    + tLATaskAimSet.get(1).getAimTable() + "Schema");
                //            Schema tSchema = (Schema) tSchemaClass.newInstance();
                if (mCalType.equals("A")||mCalType.equals("C")||mCalType.equals("D"))
                {
                    for (int i = 1; i <= tLATaskAimSet.size(); i++)
                    {
                        //设置计算编码
                        this.mCalculator.setCalCode(tLATaskAimSet.get(i)
                                                                 .getCalCode());
                        ExeSQL tExeSQL = new ExeSQL();
                        //执行解析后的sql语句，得到计算结果
                        String tReturnValue = tExeSQL.getOneValue(this.mCalculator
                                .getCalSQL());
                        if ((tReturnValue == null)
                                || tReturnValue.trim().equals(""))
                        {
                            tReturnValue = "0";
                        }
                        mSchema.setV(tLATaskAimSet.get(i).getAimCol(),
                            tReturnValue);
                    }
                }
                if (mCalType.equals("B"))
                {
                    //首先设置hashreport中用到的主键sql
                    VData tVData = new VData();
                    String Premary_key_sql = "";
                    String[] mColNames = new String[tLATaskAimSet.size()];
                    for (int i = 1; i <= tLATaskAimSet.size(); i++)
                    {
                        this.mCalculator.setCalCode(tLATaskAimSet.get(i)
                                                                 .getCalCode());
                        if (i == 1)
                        {
                            Premary_key_sql = this.mCalculator.getCalSQL();
                        }
                        else
                        {
                            tVData.add(this.mCalculator.getCalSQL());
                        }
                        mColNames[i - 1] = tLATaskAimSet.get(i).getAimCol();
                    }
                    //使用hashreport将结果组成数组
                    HashReport tHashReport = new HashReport(Premary_key_sql,
                            tVData, mColNames.length);
                    String[][] arr = tHashReport.calReportItemForString();
                    //数组每一行对应一个schema
                    for (int k = 1; k <= arr.length; k++)
                    {
                        Class tSchemaClass = Class.forName(
                                "com.sinosoft.lis.schema."
                                + tLATaskAimSet.get(1).getAimTable() + "Schema");
                        Schema tSchema = (Schema) tSchemaClass.newInstance();
                        for (int j = 1; j <= mColNames.length; j++)
                        {
                            tSchema.setV(mColNames[j - 1], arr[k-1][j - 1]);
                        }
                        this.mSchemaSet.add(tSchema);
                    }
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
                return false;
            }
            return true;
        }

        /**
         * 计算类中添加计算元素
         * @return
         */
        private Calculator buildParaInterface()
        {
            //        LATaskDescribeDB tLATaskDescribeDB = new LATaskDescribeDB();
            //        tLATaskDescribeDB.setTaskID(this.mTaskID);
            //        if (!tLATaskDescribeDB.getInfo())
            //        {
            //            return null;
            //        }
            Calculator tCalculator = new Calculator();
            try
            {
                //            Class tClass = Class.forName("com.sinosoft.lis.reagent."
                //                    + tLATaskDescribeDB.getClassName());
                //            mLATaskAimCalSetParaInterface = (LATaskAimCalSetParaInterface) tClass
                //                .newInstance();
                //            mLATaskAimCalSetParaInterface.setLATaskAimCalSetParaInterface(tCalculator,this.mTransferData);
                Vector tValueNames = this.miTransferData.getValueNames();
                for (Iterator tIterator = tValueNames.iterator();
                        tIterator.hasNext();)
                {
                    String tPara = (String) tIterator.next();
                    tCalculator.addBasicFactor(tPara,
                        (String) this.miTransferData.getValueByName(tPara));
                }
            }
            catch (Exception e)
            {
                return null;
            }
            return tCalculator;
        }

        private boolean getInputData(VData tInputData)
        {
            try
            {
                //            this.miTaskID = (String) tInputData.get(0);
                this.miTransferData = (TransferData) tInputData.get(0);
            }
            catch (Exception e)
            {
                e.printStackTrace();
                return false;
            }
            return true;
        }
    }
}
