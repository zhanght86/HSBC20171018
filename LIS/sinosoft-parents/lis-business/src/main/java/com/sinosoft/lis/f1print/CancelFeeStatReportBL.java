package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vdb.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.lis.pubfun.*;
import java.text.DecimalFormat;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: </p>
 * @author ck
 * function :ManageStat
 * @version 1.0
 * @date 2006-07-28
 */
import com.sinosoft.utility.*;

import java.io.*;

import java.util.*;

public class CancelFeeStatReportBL
{
private static Logger logger = Logger.getLogger(CancelFeeStatReportBL.class);

    public CErrors mErrors = new CErrors();
    private VData mResult = new VData();

    /*
    * @define globe variable
    */
    private TransferData tTransferData = new TransferData();
    private String mBeginDate;//初始日期
    private String mEndDate;//终止日期
    private String mEdorType;//保全类型
		private String mEdorTypeName;//保全类型
		private String mManageCom;//管理机构
		private String mStatType;//统计粒度
		private String mSaleChnl = "";//销售渠道
		private String mOperateFlag;//操作标识：CancelFeeStat:退费统计,CancelFeeRiskKind:退费险种统计状况

    public CancelFeeStatReportBL(){}

    public boolean submitData(VData cInputData, String cOperate)
    {
        if (!cOperate.equals("STAT"))
        {
            CError.buildErr(this, "不支持的操作字符串!");
            return false;
        }

        if (!getInputData(cInputData))
        {
            return false;
        }

        if (!getPrintData())
        {
            return false;
        }


        return true;
    }


    /***
      * Name : getInputData()
      * function :receive data from jsp and check date
      * check :judge whether startdate begin enddate
      * return :true or false
      */
    private boolean getInputData(VData cInputData)
    {
        tTransferData = (TransferData) cInputData.getObjectByObjectName("TransferData",0);

				if (tTransferData == null)
				{
						CError.buildErr(this, "缺少传入后台的参数！");
						return false;
        }

        //从容器中获得页面传入的数据
        mBeginDate = (String) tTransferData.getValueByName("Bdate");
        mEndDate = (String) tTransferData.getValueByName("Edate");
        mEdorType = (String) tTransferData.getValueByName("EdorType");
        mManageCom =  (String) tTransferData.getValueByName("ManageCom");
				mStatType = (String)tTransferData.getValueByName("StatType");
				mSaleChnl = (String)tTransferData.getValueByName("SaleChnl");
        mOperateFlag= (String) tTransferData.getValueByName("strOperateFlag");

        if(mBeginDate.equals("") || mEndDate.equals("") || mEdorType.equals("")
					   || mManageCom.equals("")|| mStatType.equals("")|| mOperateFlag.equals(""))
        {
        	  CError.buildErr(this,"没有得到足够的查询信息！");
						return false;
        }

			
        //获取保全类型对应名称
				ExeSQL tExeSQL = new ExeSQL();
				SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
				String tsql = "";
				if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
					tsql="select edorname From lmedoritem where edorcode='"
							 + "?mEdorType?" + "' and appobj='I'and rownum=1";
				}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
					tsql="select edorname From lmedoritem where edorcode='"
							 + "?mEdorType?" + "' and appobj='I' limit 0,1";
				}
				sqlbv5.sql(tsql);
				sqlbv5.put("mEdorType", mEdorType);
				mEdorTypeName = tExeSQL.getOneValue(sqlbv5);

				if(mEdorTypeName == null || mEdorTypeName.equals(""))
				{
					  CError.buildErr(this,"获取退保类型名称失败！");
						return false;
				}

        return true;
    }

    public VData getResult()
    {
        return this.mResult;
    }

    private boolean getPrintData()
    {
    	  String tsql = "";
    	  String tsqla="";
    	  String tsqlb="";
				String strSaleChnl = "";
				if (mSaleChnl != null && !mSaleChnl.equals(""))
				{
						strSaleChnl = " and (exists (select 1 from lcpol where salechnl='" + "?mSaleChnl?"
						              + "' and polno=ljagetendorse.polno))";
				}

    	  //退保概况报表
        if (mOperateFlag.equals("CancelFeeStat"))
        {  logger.debug("mOperateFlag"+mOperateFlag);
					  XmlExport xmlexport = new XmlExport(); //新建一个XmlExport的实例
					  xmlexport.createDocument("CancelFeeStat.vts", "");

					  ListTable tlistTable = new ListTable();
                      tlistTable.setName("DATA");
                      SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
				    //指标项：机构代码
					  tsql = "select comcode,name from ldcom where comcode like concat('" + "?mManageCom?" + "','%') and comcode not in('8699') and char_length(trim(comcode))="
						        + "?mStatType?" + "order by comcode";
					  sqlbv1.sql(tsql);
					  sqlbv1.put("mManageCom", mManageCom);
					  sqlbv1.put("mStatType", mStatType);
						SSRS mssrs = new ExeSQL().execSQL(sqlbv1);
				    if (mssrs == null || mssrs.getMaxRow() <= 0)
				    {
						    CError.buildErr(this, "查询到的险种数据为空！");
						    return false;
            }
				    SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
						//指标项为退费金额和退保件数
						tsqla = "select com,count(edorno),sum(money) from (select substr(managecom,1,"
						        + "?mStatType?" + ") com,endorsementno edorno,sum(getmoney) money "
						        + "from ljagetendorse where managecom like concat('" + "?mManageCom?" + "','%') and feeoperationtype='" + "?mEdorType?" + "' and feefinatype in('TB','TF') and makedate>='"
						        + "?mBeginDate?" + "' and makedate<='" + "?mEndDate?" + "'" + strSaleChnl
						        + " and grppolno='00000000000000000000' group by substr(managecom,1,"
						        + "?mStatType?" + "),endorsementno ) g group by com order by com";
						sqlbv2.sql(tsqla);
						sqlbv2.put("mSaleChnl", mSaleChnl);
						sqlbv2.put("mManageCom", mManageCom);
						sqlbv2.put("mEdorType", mEdorType);
						sqlbv2.put("mBeginDate", mBeginDate);
						sqlbv2.put("mEndDate", mEndDate);
						sqlbv2.put("mStatType", mStatType);
						SSRS assrs =  new ExeSQL().execSQL(sqlbv2);
						if (assrs == null || assrs.getMaxRow() <= 0)
						{
								CError.buildErr(this, "无法查询得到退保金额！");
								return false;
            }

						int sumCount = 0;
						double sumMoney = 0;
						String[] strArr;
						String tComCode = "";
						for (int i = 1, j = 1; i <= mssrs.getMaxRow() ; i ++)
						{
								strArr = new String[3];
								tComCode = mssrs.GetText(i,1);  //机构代码
								strArr[0] = mssrs.GetText(i,2);  //机构名称
								if (j<=assrs.getMaxRow() && tComCode.equals(assrs.GetText(j,1)))
								{
										strArr[1] = assrs.GetText(j,2);
										strArr[2] = assrs.GetText(j,3);
										sumCount += Integer.parseInt(strArr[1]);
										sumMoney += Double.parseDouble(strArr[2]);
										j++;
								}
								else
								{
									  strArr[1] = "0";
								    strArr[2] = "0.00";
								}
								tlistTable.add(strArr);
						}

						String[] tArr = new String[3];
						tArr[0] = "合计";     //"机构名称";
						tArr[1] = Integer.toString(sumCount);     //"退保件数";
						tArr[2] = Double.toString(sumMoney);     //"退保金额";
						tlistTable.add(tArr);

						tArr = new String[3];
						tArr[0] = "";     //"机构名称";
						tArr[1] = "";     //"退保件数";
						tArr[2] = "";     //"退保金额";

						xmlexport.addListTable(tlistTable, tArr);

						TextTag texttag = new TextTag(); //新建一个TextTag的实例
						texttag.add("ManageCom", ReportPubFun.getMngName(mManageCom));
						texttag.add("Bdate", mBeginDate);
						texttag.add("Edate", mEndDate);
						texttag.add("Date", PubFun.getCurrentDate());
						texttag.add("EdorTypeName", mEdorTypeName);
						logger.debug("大小" + texttag.size());
						if (texttag.size() > 0)
						{
							xmlexport.addTextTag(texttag);
						}

						//xmlexport.outputDocumentToFile("e:\\","data.xml");//输出xml文档到文件
						mResult.clear();
    		    mResult.addElement(xmlexport);
        }

        //险种退保概况报表
        else if (mOperateFlag.equals("CancelInsureRisk"))
        {
					  XmlExport xmlexport = new XmlExport(); //新建一个XmlExport的实例
            xmlexport.createDocument("CancelInsuranceStat.vts", "");

						ListTable tlistTable = new ListTable();
            tlistTable.setName("DATA");

            //累积退保金额
            SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
            sqlbv3.sql("select sum(getmoney) from ljagetendorse "
			         + "where managecom like concat('" + "?mManageCom?" + "','%') and feeoperationtype='"
			         + "?mEdorType?" + "' and feefinatype in('TB','TF') and makedate>='"	+ "?mBeginDate?" + "' and makedate<='"
				       + "?mEndDate?" + "' and grppolno='00000000000000000000'" + strSaleChnl);
            sqlbv3.put("mSaleChnl", mSaleChnl);
            sqlbv3.put("mManageCom", mManageCom);
            sqlbv3.put("mEdorType", mEdorType);
            sqlbv3.put("mBeginDate", mBeginDate);
            sqlbv3.put("mEndDate", mEndDate);
						String sumPay = new ExeSQL().getOneValue(sqlbv3);

					  //指标项：机构代码
//					  tsql = "select distinct riskcode,(select riskname from lmrisk where "
//						       + "riskcode = a.riskcode) From lmriskedoritem a where "
//					         + "appobj='I' order by riskcode";
//						SSRS mssrs = new ExeSQL().execSQL(tsql);
//				    if (mssrs == null || mssrs.getMaxRow() <= 0)
//				    {
//						    CError.buildErr(this, "查询到的险种数据为空！");
//						    return false;
//            }
						SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
						tsqla = "select (select riskname from lmrisk where riskcode=ljagetendorse.riskcode),"
						        +" sum(getmoney),(sum(getmoney)/" +"?sumPay?"
						        + ")*100 from ljagetendorse where managecom like concat('" + "?mManageCom?"+ "','%') and feeoperationtype='" + "?mEdorType?"
						        + "' and feefinatype in('TB','TF') and makedate>='"
						        + "?mBeginDate?" + "' and makedate<='" + "?mEndDate?"
										+ "' and grppolno='00000000000000000000'" + strSaleChnl
						        + " group by riskcode order by sum(getmoney) desc";
						sqlbv4.sql(tsqla);
						sqlbv4.put("sumPay", sumPay);
						sqlbv4.put("mManageCom", mManageCom);
						sqlbv4.put("mEdorType", mEdorType);
						sqlbv4.put("mBeginDate", mBeginDate);
						sqlbv4.put("mEndDate", mEndDate);
						SSRS assrs =  new ExeSQL().execSQL(sqlbv4);
				    if (assrs == null || assrs.getMaxRow() <= 0)
				    {
				    		CError.buildErr(this, "无法查询得到退保金额！");
				    		return false;
            }

            String[] strArr;
						for(int j = 1; j <= assrs.getMaxRow() ; j ++)
            {
							  strArr = new String[3];
                strArr[0] = assrs.GetText(j,1);  //险种名称
								strArr[1] = ReportPubFun.functionJD(Double.parseDouble(assrs.GetText(j,2)),"0.00");
								strArr[2] = ReportPubFun.functionJD(Double.parseDouble(assrs.GetText(j,3)),"0.00000")+"%";

								tlistTable.add(strArr);
						}

						String[] tArr = new String[3];
				    tArr[0] = "";     //"险种名称";
				    tArr[1] = "";     //"退保金额";
				    tArr[2] = "";     //"退保金额之比";

						xmlexport.addListTable(tlistTable, tArr);

						TextTag texttag = new TextTag(); //新建一个TextTag的实例
						texttag.add("ManageCom", ReportPubFun.getMngName(mManageCom));
						texttag.add("Bdate", mBeginDate);
						texttag.add("Edate", mEndDate);
						texttag.add("Date", PubFun.getCurrentDate());
						texttag.add("EdorTypeName", mEdorTypeName);
						texttag.add("sumPay", sumPay);
						logger.debug("大小" + texttag.size());
						if (texttag.size() > 0)
						{
								xmlexport.addTextTag(texttag);
						}

						//xmlexport.outputDocumentToFile("e:\\","data.xml");//输出xml文档到文件
						mResult.clear();
    		    mResult.addElement(xmlexport);
        }

        return true;
    }

    /*
    * @function :in order to debug
    */
    public static void main(String[] args)
    {
    }
}

