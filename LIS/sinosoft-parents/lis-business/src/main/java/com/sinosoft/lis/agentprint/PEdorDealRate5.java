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
public class PEdorDealRate5
{
private static Logger logger = Logger.getLogger(PEdorDealRate5.class);

    public CErrors mErrors = new CErrors();
    private String mManageCom = "";
    private String mStartDate = "";
    private String mEndDate = "";
    private String mSaleChnl = "";  
    private TransferData mTransferData = new TransferData();
    private GlobalInput mGlobalInput = new GlobalInput();
    private VData mResult = new VData();

    public PEdorDealRate5()
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
            
            String sql_02_contno = "";           
            String sql_03_contno = "";            
            String sql_05_contno = "";            
            String sql_07_contno = "";            
            String sql_10_contno = "";
            if(mSaleChnl != null && !mSaleChnl.equals("") && mSaleChnl.equals("02"))//个人营销
            {
            	sql_02_contno = " and exists(select 1 from lccont where contno=a.contno and salechnl='02')";
            }
            if(mSaleChnl != null && !mSaleChnl.equals("") && mSaleChnl.equals("03"))//银行代理
            {
            	sql_03_contno = " and exists(select 1 from lccont where contno=a.contno and salechnl='03')";
            }
            if(mSaleChnl != null && !mSaleChnl.equals("") && mSaleChnl.equals("05"))//中介
            {            	
            	sql_05_contno = " and exists(select 1 from lccont b,lacom c where b.contno=a.contno and b.agentcom=c.agentcom and c.branchtype='7')";
            }
            if(mSaleChnl != null && !mSaleChnl.equals("") && mSaleChnl.equals("07"))//联办代理
            {            	
            	sql_07_contno = " and exists(select 1 from lccont where contno=a.contno and salechnl='07')";
            }
            if(mSaleChnl != null && !mSaleChnl.equals("") && mSaleChnl.equals("10"))//收展业务
            {            	
            	sql_10_contno = " and exists(select 1 from lccont where contno=a.contno and salechnl='10')";
            }
            
            ListTable tListTable = new ListTable();
            tListTable.setName("PEdorDealRate");            
            
            String key_sql =" select comcode from ldcom where (char_length(comcode)='"+"?mComLength?"+"' " +
            		"or comcode='"+"?mManageCom?"+"') and comcode like concat('?mManageCom?','%') "
                           +" and comcode not like '8699%' order by comcode";
            SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
            sqlbv1.sql(key_sql);
            sqlbv1.put("mComLength", mComLength);
            sqlbv1.put("mManageCom", mManageCom);
            //机构名称
            String SQL_1 ="select comcode,name from ldcom where (char_length(comcode)='"+"?mComLength?"+"' or comcode='"+"?mManageCom?"+"') and comcode like concat('?mManageCom?','%') "
                           +" and comcode not like '8699%' order by comcode";
            SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
            sqlbv2.sql(SQL_1);
            sqlbv2.put("mComLength", mComLength);
            sqlbv2.put("mManageCom", mManageCom);
            //总件数
            String SQL_2 =" select a, sum(b) from" 
		                 +" (select substr(b.comcode, 1,"+"?mComLength?"+") a, count(*) b from lpedoritem a,lduser b,lpedorapp c "
		                 +" where a.edoracceptno = c.edoracceptno and c.operator = b.usercode and a.grpcontno='00000000000000000000'"
		                 +" and b.comcode like concat('"+"?comcode3?"+"','%') and a.edorstate in ('0','9','c','d') and a.modifydate <= '"+"?modifydate?"+"'"
		                 +" and a.modifydate >= '"+"?modifydate1?"+"' and a.edortype not in ('EC','ED')"		                 
		                 + sql_02_contno 
		                 + sql_03_contno 
		                 + sql_05_contno 
		                 + sql_07_contno 
		                 + sql_10_contno
		                 +" group by substr(b.comcode, 1,"+"?comcode3?"+")"
		                 +" union all"  
		                 +" select substr(b.comcode, 1, "+"?comcode3?"+") a, count(*) b from lobedoritem a,lduser b,lobedorapp c"
		                 +" where a.edoracceptno = c.edoracceptno and c.operator = b.usercode and a.grpcontno = '00000000000000000000'"
		                 +" and a.modifydate <= '"+"?modifydate?"+"' and a.modifydate >= '"+"?modifydate1?"+"' and b.comcode like concat('"+"?comcode3?"+"','%')"
		                 +" and not exists(select 1 from loprtmanager where otherno=a.contno and code='BQ33')"
		                 +" and a.edortype not in ('EC','ED')"
		                 + sql_02_contno 
		                 + sql_03_contno 
		                 + sql_05_contno 
		                 + sql_07_contno 
		                 + sql_10_contno
		                 +" group by substr(b.comcode, 1,"+"?comcode3?"+")) g group by a";
            SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
            sqlbv3.sql(SQL_2);
            sqlbv3.put("mComLength", mComLength);
            sqlbv3.put("comcode3", mManageCom);
            sqlbv3.put("modifydate", mEndDate);
            sqlbv3.put("modifydate1", mStartDate);
            //核保件 uwflag='5' 自核未通过，需要人工核保
            String SQL_3 =" select a, sum(b) from" 
                         +" ( select substr(b.comcode,1,"+"?comcode4?"+") a,count(*) b from lpedoritem a,lduser b,lpedorapp c"
                         +" where a.edoracceptno = c.edoracceptno and c.operator = b.usercode and a.grpcontno='00000000000000000000'"
		                 +" and b.comcode like concat('"+"?comcode4?"+"','%') and a.modifydate<='"+"?modifydate1?"+"' and a.modifydate >='"+"?modifydate2?"+"'"
		                 +" and a.edorstate in ('0','9','c','d') and a.edortype not in ('EC','ED') and a.uwflag='5'"		                 
			             + sql_02_contno
			             + sql_03_contno
			             + sql_05_contno
			             + sql_07_contno
			             + sql_10_contno
			             +" group by substr(b.comcode,1,"+"?comcode4?"+")"
			             +" union all"
			             +" select substr(b.comcode, 1, "+"?comcode4?"+") a, count(*) b from lobedoritem a,lduser b,lobedorapp c"
			             +" where a.edoracceptno = c.edoracceptno and c.operator = b.usercode and a.grpcontno = '00000000000000000000'"
                         +" and a.modifydate <= '"+"?modifydate1?"+"' and a.modifydate >= '"+"?modifydate2?"+"' and b.comcode like concat('"+"?comcode4?"+"','%')"
                         +" and a.edortype not in ('EC','ED') and a.uwflag='5'"                         
                         + sql_02_contno 
                         + sql_03_contno 
                         + sql_05_contno 
                         + sql_07_contno 
                         + sql_10_contno
                         +" group by substr(b.comcode, 1,"+"?comcode4?"+")) g group by a";
            SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
            sqlbv4.sql(SQL_3);
            sqlbv4.put("comcode4", mComLength);
            sqlbv4.put("comcode4", mManageCom);
            sqlbv4.put("modifydate1", mEndDate);
            sqlbv4.put("modifydate2", mStartDate);
            //收费件
            String SQL_4 =" select substr(b.comcode,1,"+"?comcode5?"+") a,count(*) b from lpedoritem a,lduser b,lpedorapp c"
                         +" where a.edoracceptno = c.edoracceptno and c.operator = b.usercode and a.grpcontno='00000000000000000000'"
		                 +" and b.comcode like concat('"+"?comcode5?"+"','%') and a.modifydate<='"+"?modifydate2?"+"' and a.modifydate>='"+"?modifydate3?"+"'"
		                 +" and a.edorstate in ('0','9','c','d') and a.edortype not in ('EC','ED')"
		                 +" and exists (select 1 from ljapay where incometype='10' and incomeno=a.edoracceptno)"
			             + sql_02_contno
			             + sql_03_contno
			             + sql_05_contno
			             + sql_07_contno
			             + sql_10_contno
			             +" group by substr(b.comcode,1,"+"?comcode5?"+")";
            SQLwithBindVariables sqlbv5=new SQLwithBindVariables();
            sqlbv5.sql(SQL_4);
            sqlbv5.put("comcode5", mComLength);
            sqlbv5.put("comcode5", mManageCom);
            sqlbv5.put("modifydate2", mEndDate);
            sqlbv5.put("modifydate3", mStartDate);
            
            //体检件
            //select * from lppenotice where edoracceptno='86370720100410000998'
            String SQL_5 =" select a, sum(b) from" 
		                 +" (select substr(b.comcode,1,"+"?comcode6?"+") a,count(*) b from lpedoritem a,lduser b,lpedorapp c"
		                 +" where a.edoracceptno = c.edoracceptno and c.operator = b.usercode and a.grpcontno='00000000000000000000'"
		                 +" and b.comcode like concat('"+"?comcode6?"+"','%') and a.modifydate<='"+"?modifydate3?"+"' and a.modifydate>='"+"?modifydate4?"+"'"
		                 +" and a.edorstate in ('0','9','c','d') and a.edortype not in ('EC','ED')"
		                 +" and exists (select 1 from lppenotice where edoracceptno = a.edoracceptno)"
			             + sql_02_contno
			             + sql_03_contno
			             + sql_05_contno
			             + sql_07_contno
			             + sql_10_contno
			             +" group by substr(b.comcode,1,"+"?comcode6?"+")"
			             +" union all"
			             +" select substr(b.comcode, 1, "+"?mManageCom?"+") a, count(*) b from lobedoritem a,lduser b,lobedorapp c"
			             +" where a.edoracceptno = c.edoracceptno and c.operator = b.usercode and a.grpcontno = '00000000000000000000'"
		                 +" and a.modifydate <= '"+"?modifydate3?"+"' and a.modifydate >= '"+"?modifydate4?"+"' and b.comcode like concat('"+"?comcode6?"+"','%')"
		                 +" and a.edortype not in ('EC','ED')"
		                 +" and exists (select 1 from lppenotice where edoracceptno = a.edoracceptno)"
		                 + sql_02_contno 
		                 + sql_03_contno 
		                 + sql_05_contno 
		                 + sql_07_contno 
		                 + sql_10_contno
		                 +" group by substr(b.comcode, 1,"+"?comcode6?"+")) g group by a";             
            SQLwithBindVariables sqlbv6=new SQLwithBindVariables();
            sqlbv6.sql(SQL_5);
            sqlbv6.put("comcode6", mComLength);
            sqlbv6.put("mManageCom", mManageCom);
            sqlbv6.put("modifydate3", mEndDate);
            sqlbv6.put("modifydate4", mStartDate);
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
            tZHashReport.setColumnType(1, tZHashReport.StringType);
            String[][] tStringResult = tZHashReport.calItem();
            String strArr[] = null; 
            logger.debug("tStringResult.length="+tStringResult.length);
            double total = 0;//合计总件数
            double uwnum = 0;//合计核保件
            double feenum = 0;//合计交费件
            double heanum = 0;//合计体检件
            for(int i=0;i<tStringResult.length;i++)
            {
            	logger.debug("tStringResult["+i+"][0]="+tStringResult[i][0]);
            	logger.debug("tStringResult["+i+"][1]="+tStringResult[i][1]);
            	logger.debug("tStringResult["+i+"][2]="+tStringResult[i][2]);
            	logger.debug("tStringResult["+i+"][3]="+tStringResult[i][3]);
            	logger.debug("tStringResult["+i+"][4]="+tStringResult[i][4]);  
            	logger.debug("tStringResult["+i+"][5]="+tStringResult[i][5]);
            	strArr = new String[9];
            	strArr[0] = String.valueOf(i+1);//序号        	
            	strArr[1] = tStringResult[i][1];//机构名称
            	strArr[2] = tStringResult[i][2];//保全总件数
            	strArr[3] = tStringResult[i][3];//核保件数
            	if(tStringResult[i][2]==null || tStringResult[i][2].equals("0.0"))
            	{
            		strArr[4] = "0.00%";//核保件数占比
            	}
            	else
            	{
            		strArr[4] = new DecimalFormat("0.00%").format(Double.parseDouble(tStringResult[i][3])/Double.parseDouble(tStringResult[i][2]));//核保件数占比
            	}
            	strArr[5] = tStringResult[i][4];//收费件数
            	if(tStringResult[i][2]==null || tStringResult[i][2].equals("0.0"))
            	{
            		strArr[6] = "0.00%";//收费件数占比
            	}
            	else
            	{
            		strArr[6] = new DecimalFormat("0.00%").format(Double.parseDouble(tStringResult[i][4])/Double.parseDouble(tStringResult[i][2]));//收费件数占比
            	}
            	strArr[7] = tStringResult[i][5];//体检件数
            	if(tStringResult[i][2]==null || tStringResult[i][2].equals("0.0"))
            	{
            		strArr[8] = "0.00%";//体检件数占比
            	}
            	else
            	{
            		strArr[8] = new DecimalFormat("0.00%").format(Double.parseDouble(tStringResult[i][5])/Double.parseDouble(tStringResult[i][2]));//体检件数占比
            	}
            	total = total + Double.parseDouble(tStringResult[i][2]);
            	uwnum = uwnum + Double.parseDouble(tStringResult[i][3]);
            	feenum = feenum + Double.parseDouble(tStringResult[i][4]);
            	heanum = heanum + Double.parseDouble(tStringResult[i][5]);
            	tListTable.add(strArr);
            } 
            strArr = new String[9];
            strArr[0] = "";
            strArr[1] = "合计";
            strArr[2] = String.valueOf(total);
            strArr[3] = String.valueOf(uwnum);
            logger.debug("合计="+strArr[2]);            
            strArr[4] = new DecimalFormat("0.00%").format(uwnum/total);
            strArr[5] = String.valueOf(feenum);
            strArr[6] = new DecimalFormat("0.00%").format(feenum/total);
            strArr[7] = String.valueOf(heanum);       
            strArr[8] = new DecimalFormat("0.00%").format(heanum/total);
            if(strArr[2].equals("0.0"))
            {
            	strArr[4] = "0.00%";
            	strArr[6] = "0.00%";
            	strArr[8] = "0.00%";            	
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
            xmlexport.createDocument("PEdorDealRate5.vts", "printer");
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
