package com.sinosoft.lis.agentprint;
import org.apache.log4j.Logger;

import java.text.DecimalFormat;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */
public class PEdorDealRate8
{
private static Logger logger = Logger.getLogger(PEdorDealRate8.class);

    public CErrors mErrors = new CErrors();
    private String mManageCom = "";
    private String mStartDate = "";
    private String mEndDate = "";
    private String mSaleChnl = "";  
    private TransferData mTransferData = new TransferData();
    private GlobalInput mGlobalInput = new GlobalInput();
    private VData mResult = new VData();

    public PEdorDealRate8()
    {
    }

    public static void main(String[] args)
    {
        
    }

    public boolean submitData(VData tInputData, String tOperator)
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

    public VData getResult()
    {
        return this.mResult;
    }

    private boolean dealData()
    {
    	try
    	{
    		ExeSQL tExe = new ExeSQL();
            int mComLength = mManageCom.length()+2;
            if(mComLength > 8)
            {
            	mComLength = 8;
            }
            String sql_02 = "";
            //String sql_02_contno = "";
            String sql_03 = "";
            //String sql_03_contno = "";
            String sql_05 = "";
            //String sql_05_contno = "";
            String sql_07 = "";
            //String sql_07_contno = "";
            String sql_10 = "";
            //String sql_10_contno = "";
            if(mSaleChnl != null && !mSaleChnl.equals("") && mSaleChnl.equals("02"))//个人营销
            {
            	sql_02 = " and exists(select 1 from lccont where contno=a.otherno and salechnl='02')";
            	//sql_02_contno = " and exists(select 1 from lccont where contno=a.contno and salechnl='02')";
            }
            if(mSaleChnl != null && !mSaleChnl.equals("") && mSaleChnl.equals("03"))//银行代理
            {
            	sql_03 = " and exists(select 1 from lccont where contno=a.otherno and salechnl='03')";
            	//sql_03_contno = " and exists(select 1 from lccont where contno=a.contno and salechnl='03')";
            }
            if(mSaleChnl != null && !mSaleChnl.equals("") && mSaleChnl.equals("05"))//中介
            {
            	sql_05 = " and exists(select 1 from lccont b,lacom c where b.contno=a.otherno and b.agentcom=c.agentcom and c.branchtype='7')";
            	//sql_05_contno = " and exists(select 1 from lccont b,lacom c where b.contno=a.contno and b.agentcom=c.agentcom and c.branchtype='7')";
            }
            if(mSaleChnl != null && !mSaleChnl.equals("") && mSaleChnl.equals("07"))//联办代理
            {
            	sql_07 = " and exists(select 1 from lccont where contno=a.otherno and salechnl='07')";
            	//sql_07_contno = " and exists(select 1 from lccont where contno=a.contno and salechnl='07')";
            }
            if(mSaleChnl != null && !mSaleChnl.equals("") && mSaleChnl.equals("10"))//收展业务
            {
            	sql_10 = " and exists(select 1 from lccont where contno=a.otherno and salechnl='10')";
            	//sql_10_contno = " and exists(select 1 from lccont where contno=a.contno and salechnl='10')";
            }
            
            ListTable tListTable = new ListTable();
            tListTable.setName("PEdorDealRate");            
            
            String key_sql =" select comcode from ldcom where (char_length(comcode)='"+"?key_sql?"+"' or comcode='"+"?comcode1?"+"') and comcode like concat('"+"?comcode1?"+"','%')"
                           +" and comcode not like '8699%' order by comcode";
            SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
            sqlbv1.sql(key_sql);
            sqlbv1.put("comcode", mComLength);
            sqlbv1.put("comcode1", mManageCom);
//          机构名称
            String SQL_1 =" select comcode,name from ldcom where (char_length(comcode)='"+"?comcode1?"+"' or comcode='"+"?comcode2?"+"') and comcode like concat('"+"?comcode2?"+"','%')"
                         +" and comcode not like '8699%' order by comcode";
            SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
            sqlbv2.sql(SQL_1);
            sqlbv2.put("comcode1", mComLength);
            sqlbv2.put("comcode2", mManageCom);
            //问题件件数
            String SQL_2 =" select substr(b.comcode, 1,"+"?comcode2?"+") a, count(*) b from lpedorapp a,lduser b where a.operator = b.usercode and a.othernotype = '3'"
			             +" and b.comcode like concat('"+"?comcode3?"+"','%') and a.makedate <= '"+"?a?"+"' and a.makedate >= '"+"?a1?"+"'"
			             +" and exists(select 1 from loprtmanager where code='BQ37' and standbyflag1 = a.edoracceptno)" 
			             +" and not exists(select 1 from lpedoritem where contno=a.otherno and edortype in ('EC','ED'))"
			             + sql_02 
			             + sql_03 
			             + sql_05 
			             + sql_07 
			             + sql_10
			             +" group by substr(b.comcode, 1,"+"?comcode2?"+")";         
            SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
            sqlbv3.sql(SQL_2);
            sqlbv3.put("comcode2", mComLength);
            sqlbv3.put("comcode3", mManageCom);
            sqlbv3.put("a", mEndDate);
            sqlbv3.put("a1", mStartDate);
            //总件数
            String SQL_3 =" select substr(b.comcode, 1,"+"?comcode3?"+") a, count(*) b from lpedorapp a,lduser b where a.operator = b.usercode and a.othernotype = '3'"
                         +" and b.comcode like concat('"+"?comcode4?"+"','%') and a.makedate <= '"+"?a1?"+"' and a.makedate >= '"+"?a2?"+"'"
                         +" and not exists(select 1 from lpedoritem where contno=a.otherno and edortype in ('EC','ED'))"
                         + sql_02 
                         + sql_03 
                         + sql_05 
                         + sql_07 
                         + sql_10
                         +" group by substr(b.comcode, 1,"+"?comcode3?"+")";
            SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
            sqlbv4.sql(SQL_2);
            sqlbv4.put("comcode3", mComLength);
            sqlbv4.put("comcode4", mManageCom);
            sqlbv4.put("a1", mEndDate);
            sqlbv4.put("a2", mStartDate);           
            
            logger.debug("key_sql="+key_sql);
            logger.debug("SQL_1="+SQL_1);
            logger.debug("SQL_2="+SQL_2);
            logger.debug("SQL_3="+SQL_3);
            VData tVData = new VData();
            tVData.add(sqlbv2);
            tVData.add(sqlbv3);
            tVData.add(sqlbv4);
            ZHashReport tZHashReport = new ZHashReport(sqlbv1,tVData);            
            tZHashReport.setDoformat(false);
            tZHashReport.setColumnType(1, tZHashReport.StringType);
            String[][] tStringResult = tZHashReport.calItem();
            String strArr[] = null; 
            logger.debug("tStringResult.length="+tStringResult.length);
            double bq_1 = 0;//问题件
            double bq_total = 0;//总件数
            for(int i=0;i<tStringResult.length;i++)
            {
            	logger.debug("tStringResult["+i+"][0]="+tStringResult[i][0]);
            	logger.debug("tStringResult["+i+"][1]="+tStringResult[i][1]);
            	logger.debug("tStringResult["+i+"][2]="+tStringResult[i][2]);
            	strArr = new String[5];
            	strArr[0] = String.valueOf(i+1);//序号        	
            	strArr[1] = tStringResult[i][1];//机构名称
            	strArr[2] = tStringResult[i][2];//问题件件数
            	strArr[3] = tStringResult[i][3];//总件数
            	if(tStringResult[i][3]==null || tStringResult[i][3].equals("0.0"))
            	{
            		strArr[4] = "0.00%";//问题件数占比
            	}
            	else
            	{
            		strArr[4] = new DecimalFormat("0.00%").format(Double.parseDouble(tStringResult[i][2])/Double.parseDouble(tStringResult[i][3]));//问题件数占比
            	}            	
            	
            	bq_1 = bq_1 + Double.parseDouble(tStringResult[i][2]);            	
            	bq_total = bq_total + Double.parseDouble(tStringResult[i][3]);
            	tListTable.add(strArr);
            }
            strArr = new String[5];
            strArr[0] = "";
            strArr[1] = "合计";
            strArr[2] = String.valueOf(bq_1);
            strArr[3] = String.valueOf(bq_total);
            strArr[4] = new DecimalFormat("0.00%").format(bq_1/bq_total);        
            if(strArr[3].equals("0.0"))
            {
            	strArr[4] = "0.00%";
            }
            tListTable.add(strArr);

            TextTag tTextTag = new TextTag();
            tTextTag.add("StartDate", this.mStartDate);
            tTextTag.add("EndDate", this.mEndDate);
            tTextTag.add("CurrenDate",PubFun.getCurrentDate()+" "+PubFun.getCurrentTime());
            SQLwithBindVariables sqlbv=new SQLwithBindVariables();
            sqlbv.sql("select name from ldcom where comcode='"+"?mManageCom?"+"'");
            sqlbv.put("mManageCom", mManageCom);
            tTextTag.add("ManageCom", tExe.getOneValue(sqlbv));
            
            XmlExport xmlexport = new XmlExport();
            xmlexport.createDocument("PEdorDealRate8.vts", "printer");
            xmlexport.addTextTag(tTextTag);
            strArr = new String[3];
            xmlexport.addListTable(tListTable, strArr);
            mResult.addElement(xmlexport);    		
    	}catch (Exception ex)
    	{
    		ex.printStackTrace();
    		return false;
    	}
    	
        return true;
    }

    private boolean getInputData(VData tInputData)
    {
        try
        {
        	mGlobalInput.setSchema((GlobalInput) tInputData.getObjectByObjectName(
    				"GlobalInput", 0));
    		mTransferData = (TransferData) tInputData.getObjectByObjectName(
    				"TransferData", 0);
    		
            this.mManageCom = (String) mTransferData.getValueByName("ManageCom");
            this.mStartDate = (String) mTransferData.getValueByName("StartDate");
            this.mEndDate = (String) mTransferData.getValueByName("EndDate");
            this.mSaleChnl = (String) mTransferData.getValueByName("SaleChnl");            
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
