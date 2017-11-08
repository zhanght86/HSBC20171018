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
public class PEdorDealRate
{
private static Logger logger = Logger.getLogger(PEdorDealRate.class);

    public CErrors mErrors = new CErrors();
    private String mManageCom = "";
    private String mStartDate = "";
    private String mEndDate = "";
    private String mSaleChnl = "";  
    private TransferData mTransferData = new TransferData();
    private GlobalInput mGlobalInput = new GlobalInput();
    private VData mResult = new VData();

    public PEdorDealRate()
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
            String sql_02_contno = "";
            String sql_03 = "";
            String sql_03_contno = "";
            String sql_05 = "";
            String sql_05_contno = "";
            String sql_07 = "";
            String sql_07_contno = "";
            String sql_10 = "";
            String sql_10_contno = "";
            if(mSaleChnl != null && !mSaleChnl.equals("") && mSaleChnl.equals("02"))//个人营销
            {
            	sql_02 = " and exists(select 1 from lccont where contno=a.otherno and salechnl='02')";
            	sql_02_contno = " and exists(select 1 from lccont where contno=a.contno and salechnl='02')";
            }
            if(mSaleChnl != null && !mSaleChnl.equals("") && mSaleChnl.equals("03"))//银行代理
            {
            	sql_03 = " and exists(select 1 from lccont where contno=a.otherno and salechnl='03')";
            	sql_03_contno = " and exists(select 1 from lccont where contno=a.contno and salechnl='03')";
            }
            if(mSaleChnl != null && !mSaleChnl.equals("") && mSaleChnl.equals("05"))//中介
            {
            	sql_05 = " and exists(select 1 from lccont b,lacom c where b.contno=a.otherno and b.agentcom=c.agentcom and c.branchtype='7')";
            	sql_05_contno = " and exists(select 1 from lccont b,lacom c where b.contno=a.contno and b.agentcom=c.agentcom and c.branchtype='7')";
            }
            if(mSaleChnl != null && !mSaleChnl.equals("") && mSaleChnl.equals("07"))//联办代理
            {
            	sql_07 = " and exists(select 1 from lccont where contno=a.otherno and salechnl='07')";
            	sql_07_contno = " and exists(select 1 from lccont where contno=a.contno and salechnl='07')";
            }
            if(mSaleChnl != null && !mSaleChnl.equals("") && mSaleChnl.equals("10"))//收展业务
            {
            	sql_10 = " and exists(select 1 from lccont where contno=a.otherno and salechnl='10')";
            	sql_10_contno = " and exists(select 1 from lccont where contno=a.contno and salechnl='10')";
            }
            
            ListTable tListTable = new ListTable();
            tListTable.setName("PEdorDealRate");            
            
            String key_sql =" select comcode from ldcom where (length(comcode)='"+"?comcode?"+"' or comcode='"+"?comcode1?"+"') and comcode like '"+"?comcode1?"+"%'"
                           +" and comcode not like '8699%' order by comcode";
                           SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
                           sqlbv1.sql(key_sql);
                           sqlbv1.put("comcode", mComLength);
                           sqlbv1.put("comcode1", mManageCom);
            //保全确认
            String SQL_1 =" select substr(b.comcode,1,"+"?comcode?"+"),count(*) from lpedorapp a,lduser b where a.operator = b.usercode and a.othernotype='3'"
            		     +" and b.comcode like '"+"?comcode2?"+"%' and a.edorstate='0' and a.makedate<='"+"?makedate?"+"' and a.makedate>='"+"?makedate2?"+"'"
            		     +" and not exists(select 1 from lpedoritem where contno=a.otherno and edortype in ('EC','ED'))"
                         + sql_02
                         + sql_03
                         + sql_05
                         + sql_07
                         + sql_10
                         +" group by substr(b.comcode,1,"+"?comcode?"+")";
                         SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
                         sqlbv2.sql(SQL_1);
                         sqlbv2.put("comcode", mComLength);
                         sqlbv2.put("comcode2", mManageCom);
                         sqlbv2.put("makedate", mEndDate);
                         sqlbv2.put("makedate2", mStartDate);
            
            //保全拒绝
            String SQL_2 =" select substr(b.comcode,1,"+"?comcode1?"+"),count(*) from lpedorapp a,lduser b where a.operator = b.usercode and a.othernotype='3'"
            		     +" and b.comcode like '"+"?comcode3?"+"%' and  a.edorstate in ('9','c') and a.makedate<='"+"?makedate1?"+"' and a.makedate>='"+"?makedate3?"+"'"
            		     +" and not exists(select 1 from lpedoritem where contno=a.otherno and edortype in ('EC','ED'))"
    			         + sql_02_contno
    			         + sql_03_contno
    			         + sql_05_contno
    			         + sql_07_contno
    			         + sql_10_contno
    			         +" group by substr(b.comcode,1,"+mComLength+")";
                        SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
                        sqlbv3.sql(SQL_2);
                        sqlbv3.put("comcode1", mComLength);
                        sqlbv3.put("comcode3", mManageCom);
                        sqlbv3.put("makedate1", mEndDate);
                        sqlbv3.put("makedate3", mStartDate);
            //保全申请撤销
            String SQL_3 =" select substr(b.comcode,1,"+"?comcode2?"+"),count(*) from lobedoritem a,lduser b,lobedorapp c"
                         +" where a.edoracceptno = c.edoracceptno and c.operator = b.usercode and a.grpcontno='00000000000000000000'"
    			         +" and a.makedate <='"+"?makedate2?"+"' and a.makedate>='"+"?makedate4?"+"' and b.comcode like '"+"?comcode4?"+"%'"
    			         +" and not exists(select 1 from loprtmanager where otherno=a.contno and code='BQ33')"
    			         +" and a.edortype not in ('EC','ED')"
    			         + sql_02_contno
    			         + sql_03_contno
    			         + sql_05_contno
    			         + sql_07_contno
    			         + sql_10_contno
    			         +" group by substr(b.comcode,1,"+"?comcode2?"+")";
                         SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
                         sqlbv4.sql(SQL_3);
                         sqlbv4.put("makedate2", mEndDate);
                         sqlbv4.put("comcode4", mManageCom);
                         sqlbv4.put("makedate4", mStartDate);   
                         sqlbv4.put("comcode2", mComLength);
            //强制终止
            String SQL_4 =" select substr(b.comcode,1,"+"?comcode3?"+"),count(*) from lpedorapp a,lduser b where a.operator = b.usercode and a.othernotype='3'"
    	                 +" and b.comcode like '"+"?comcode5?"+"%' and  a.edorstate='d' and a.makedate<='"+"?makedate3?"+"' and a.makedate>='"+"?makedate5?"+"'"
    	                 +" and not exists(select 1 from lpedoritem where contno=a.otherno and edortype in ('EC','ED'))"
                         +sql_02
                         +sql_03
                         +sql_05
                         +sql_07
                         +sql_10
                         +" group by substr(b.comcode,1,"+"?comcode3?"+")";
                         SQLwithBindVariables sqlbv5=new SQLwithBindVariables();
                         sqlbv5.sql(SQL_4);
                         sqlbv5.put("comcode3", mComLength);
                         sqlbv5.put("comcode5", mManageCom);
                         sqlbv5.put("makedate3", mEndDate);
                         sqlbv5.put("makedate5", mStartDate);   
            //总件数
            String SQL_5 =" select a, sum(b) from" 
                         +" (select substr(b.comcode, 1,"+"?comcode6?"+") a, count(*) b from lpedorapp a,lduser b where a.operator = b.usercode and a.othernotype = '3'"
                         +" and b.comcode like '"+"?comcode6?"+"%' and a.edorstate in ('0','9','c','d') and a.makedate <= '"+"?makedate6?"+"'"
                         +" and a.makedate >= '"+"?makedate6?"+"'"
                         +" and not exists(select 1 from lpedoritem where contno=a.otherno and edortype in ('EC','ED'))"
                         + sql_02 
                         + sql_03 
                         + sql_05 
                         + sql_07 
                         + sql_10
                         +" group by substr(b.comcode, 1,"+"?comcode6?"+")"
                         +" union all"  
                         +" select substr(b.comcode, 1, "+"?comcode6?"+") a, count(*) b from lobedoritem a,lduser b,lobedorapp c"
                         +" where a.edoracceptno = c.edoracceptno and c.operator = b.usercode and a.grpcontno = '00000000000000000000'"
                         +" and a.makedate <= '"+"?makedate6?"+"' and a.makedate >= '"+"?makedate6?"+"' and b.comcode like '"+"?comcode6?"+"%'"
                         +" and not exists(select 1 from loprtmanager where otherno=a.contno and code='BQ33')"
                         +" and a.edortype not in ('EC','ED')"
                         + sql_02_contno 
                         + sql_03_contno 
                         + sql_05_contno 
                         + sql_07_contno 
                         + sql_10_contno
                         +" group by substr(b.comcode, 1,"+mComLength+")) group by a";
                         SQLwithBindVariables sqlbv6=new SQLwithBindVariables();
                         sqlbv6.sql(SQL_5);
                         sqlbv6.put("comcode6", mComLength);
                         sqlbv6.put("comcode6", mManageCom);
                         sqlbv6.put("makedate6", mEndDate);
                         sqlbv6.put("makedate6", mStartDate);   
            logger.debug("key_sql="+key_sql);
            logger.debug("SQL_1="+SQL_1);
            logger.debug("SQL_2="+SQL_2);
            logger.debug("SQL_3="+SQL_3);
            logger.debug("SQL_4="+SQL_4);
            logger.debug("SQL_5="+SQL_5);
            VData tVData = new VData();
            tVData.add(sqlbv2);
            tVData.add(sqlbv3);
            tVData.add(sqlbv4);
            tVData.add(sqlbv5);
            tVData.add(sqlbv6);  
            ZHashReport tZHashReport = new ZHashReport(sqlbv1,tVData);            
            tZHashReport.setDoformat(false);
            String[][] tStringResult = tZHashReport.calItem();
            String strArr[] = null; 
            logger.debug("tStringResult.length="+tStringResult.length);
            double bq_confirm = 0;//保全确认
            double bq_confuse = 0;//保全拒绝
            double bq_cancel = 0;//保全申请撤销
            double bq_stop = 0;//保全强制终止
            double bq_total = 0;//总件数
            for(int i=0;i<tStringResult.length;i++)
            {
            	logger.debug("tStringResult["+i+"][0]="+tStringResult[i][0]);
            	logger.debug("tStringResult["+i+"][1]="+tStringResult[i][1]);
            	logger.debug("tStringResult["+i+"][2]="+tStringResult[i][2]);
            	logger.debug("tStringResult["+i+"][3]="+tStringResult[i][3]);
            	logger.debug("tStringResult["+i+"][4]="+tStringResult[i][4]);
            	logger.debug("tStringResult["+i+"][5]="+tStringResult[i][5]);
            	strArr = new String[11];
            	strArr[0] = String.valueOf(i+1);//序号        	
            	SQLwithBindVariables sqlbva = new SQLwithBindVariables();
            	sqlbva.sql("select name from ldcom where comcode='"+"?comcode?"+"'");
            	sqlbva.put("comcode", tStringResult[i][0]);
            	strArr[1] = tExe.getOneValue(sqlbva);//机构名称
            	strArr[2] = tStringResult[i][1];//保全确认件数
            	if(tStringResult[i][5]==null || tStringResult[i][5].equals("0.0"))
            	{
            		strArr[3] = "0.00%";//保全确认件数占比
            	}
            	else
            	{
            		strArr[3] = new DecimalFormat("0.00%").format(Double.parseDouble(tStringResult[i][1])/Double.parseDouble(tStringResult[i][5]));//保全确认件数占比
            	}
            	strArr[4] = tStringResult[i][2];//保全拒绝件数
            	if(tStringResult[i][5]==null || tStringResult[i][5].equals("0.0"))
            	{
            		strArr[5] = "0.00%";//保全拒绝件数占比
            	}
            	else
            	{
            		strArr[5] = new DecimalFormat("0.00%").format(Double.parseDouble(tStringResult[i][2])/Double.parseDouble(tStringResult[i][5]));//保全拒绝件数占比
            	}
            	strArr[6] = tStringResult[i][3];//保全申请撤销件数
            	if(tStringResult[i][5]==null || tStringResult[i][5].equals("0.0"))
            	{
            		strArr[7] = "0.00%";//保全申请撤销件数占比
            	}
            	else
            	{
            		strArr[7] = new DecimalFormat("0.00%").format(Double.parseDouble(tStringResult[i][3])/Double.parseDouble(tStringResult[i][5]));//保全申请撤销件数占比
            	}
            	strArr[8] = tStringResult[i][4];//强制终止件数
            	if(tStringResult[i][5]==null || tStringResult[i][5].equals("0.0"))
            	{
            		strArr[9] = "0.00%";//强制终止件数占比
            	}
            	else
            	{
            		strArr[9] = new DecimalFormat("0.00%").format(Double.parseDouble(tStringResult[i][4])/Double.parseDouble(tStringResult[i][5]));//强制终止件数占比
            	}
            	strArr[10] = tStringResult[i][5];//总件数
            	
            	bq_confirm = bq_confirm + Double.parseDouble(tStringResult[i][1]);
            	bq_confuse = bq_confuse + Double.parseDouble(tStringResult[i][2]);
            	bq_cancel = bq_cancel + Double.parseDouble(tStringResult[i][3]);
            	bq_stop = bq_stop + Double.parseDouble(tStringResult[i][4]);
            	bq_total = bq_total + Double.parseDouble(tStringResult[i][5]);
            	tListTable.add(strArr);
            }
            strArr = new String[11];
            strArr[0] = "";
            strArr[1] = "合计";
            strArr[2] = String.valueOf(bq_confirm);
            strArr[3] = new DecimalFormat("0.00%").format(bq_confirm/bq_total);
            strArr[4] = String.valueOf(bq_confuse);
            strArr[5] = new DecimalFormat("0.00%").format(bq_confuse/bq_total);
            strArr[6] = String.valueOf(bq_cancel);
            strArr[7] = new DecimalFormat("0.00%").format(bq_cancel/bq_total);
            strArr[8] = String.valueOf(bq_stop);
            strArr[9] = new DecimalFormat("0.00%").format(bq_stop/bq_total);
            strArr[10] = String.valueOf(bq_total);
            if(strArr[10].equals("0.0"))
            {
            	strArr[3] = "0.00%";
            	strArr[5] = "0.00%";
            	strArr[7] = "0.00%";
            	strArr[9] = "0.00%";
            }
            tListTable.add(strArr);

            TextTag tTextTag = new TextTag();
            tTextTag.add("StartDate", this.mStartDate);
            tTextTag.add("EndDate", this.mEndDate);
            tTextTag.add("CurrenDate",PubFun.getCurrentDate()+" "+PubFun.getCurrentTime());
            SQLwithBindVariables sqlbvb = new SQLwithBindVariables();
            sqlbvb.sql("select name from ldcom where comcode='"+"?mManageCom?"+"'");
            sqlbvb.put("mManageCom", mManageCom);
            tTextTag.add("ManageCom", tExe.getOneValue(sqlbvb));
            
            XmlExport xmlexport = new XmlExport();
            xmlexport.createDocument("PEdorDealRate.vts", "printer");
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
