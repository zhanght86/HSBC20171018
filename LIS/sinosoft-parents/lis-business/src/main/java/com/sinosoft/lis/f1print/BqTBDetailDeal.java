package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.ReportPubFun;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.SysConst;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;

public class BqTBDetailDeal {
private static Logger logger = Logger.getLogger(BqTBDetailDeal.class);
	   public CErrors mErrors = new CErrors();
	   private VData mResult = new VData();
	   private TransferData tTransferData = new TransferData();
	   private String mTBBeginDate;//退保初始日期
	   private String mTBEndDate;//退保终止日期
	   private String mCBBeginDate;//承保初始日期
	   private String mCBEndDate;//承保终止日期
	   private String mEdorType;//保全类型
	   private String mEdorTypeName;//保全类型
	   private String mManageCom;//管理机构
	   private String mStatType;//统计粒度
	   private String mSaleChnl = "";//销售渠道
	

	    public BqTBDetailDeal(){}

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


	    private boolean getInputData(VData cInputData)
	    {
	        tTransferData = (TransferData) cInputData.getObjectByObjectName("TransferData",0);

					if (tTransferData == null)
					{
							CError.buildErr(this, "缺少传入后台的参数！");
							return false;
	        }

	        //从容器中获得页面传入的数据
	        mCBBeginDate = (String) tTransferData.getValueByName("CBBdate");
	        mCBEndDate = (String) tTransferData.getValueByName("CBEdate");
	        mTBBeginDate = (String) tTransferData.getValueByName("TBBdate");
	        mTBEndDate = (String) tTransferData.getValueByName("TBEdate");
	        mEdorType = (String) tTransferData.getValueByName("EdorType");
	        mManageCom =  (String) tTransferData.getValueByName("ManageCom");
			mStatType = (String)tTransferData.getValueByName("StatType");
			mSaleChnl = (String)tTransferData.getValueByName("SaleChnl");
	      

	        if(mCBBeginDate.equals("") || mCBEndDate.equals("")||mTBBeginDate.equals("") || mTBEndDate.equals("") || mEdorType.equals("")
						   || mManageCom.equals("")|| mStatType.equals(""))
	        {
	        	logger.debug("没有得到足够的查询信息");
	        	CError.buildErr(this,"没有得到足够的查询信息！");
							return false;
	        }

				
	        //获取保全类型对应名称
					ExeSQL tExeSQL = new ExeSQL();
					SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
					String tsql = "";
					if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
						tsql ="select edorname From lmedoritem where edorcode='"
								 + "?mEdorType?" + "' and appobj='I'and rownum=1";
					}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
						tsql ="select edorname From lmedoritem where edorcode='"
								 + "?mEdorType?" + "' and appobj='I'  limit 0,1";
					}
					sqlbv1.sql(tsql);
					sqlbv1.put("mEdorType", mEdorType);
					mEdorTypeName = tExeSQL.getOneValue(sqlbv1);

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

	    	
						  XmlExport xmlexport = new XmlExport(); //新建一个XmlExport的实例
						  xmlexport.createDocument("BqTBDetail.vts", "");

						  ListTable tlistTable = new ListTable();
	                      tlistTable.setName("BQList");
	                      SQLwithBindVariables sqlbv = new SQLwithBindVariables();
					    //指标项：机构代码
						  tsql = "select comcode,name from ldcom where comcode like concat('" + "?mManageCom?" + "','%') and comcode not in('8699') and char_length(trim(comcode))="
							        + "?mStatType?" + "order by comcode";
						  sqlbv.sql(tsql);
						  sqlbv.put("mManageCom", mManageCom);
						  sqlbv.put("mStatType", mStatType);
							SSRS mssrs = new ExeSQL().execSQL(sqlbv);
					    if (mssrs == null || mssrs.getMaxRow() <= 0)
					    {
							    CError.buildErr(this, "查询到的险种数据为空！");
							    return false;
	            }
					    SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
					    String sql = "select contno,edorno, (select appntname from lccont where contno = a.contno),"
			                   + "(select trim(riskname) from lmriskapp where riskcode in (select  distinct riskcode from lcpol where  contno = a.contno and polno=mainpolno)),"
			                   + "(select min(cvalidate) from lcpol where contno = a.contno and polno=mainpolno),"
			                   + "abs(getmoney),abs(getmoney)-(case when StandbyFlag3 is not null then StandbyFlag3  else 0 end),(case edortype when 'XT' then (case when StandbyFlag3 is not null then StandbyFlag3  else 0 end) else 0 end),(select max(agentcode) from lcpol where  contno = a.contno and polno=mainpolno),"
			                   + "substr(managecom,1,4),managecom,makedate "
			                   + "from lpedoritem a "
			                   + "where edortype = '"+"?mEdorType?"+"' and edorstate = '0' and grpcontno = '00000000000000000000' "
			                   + "and exists(select 1 from lcpol where contno = a.contno "
			                   + ReportPubFun.getWherePart("salechnl",ReportPubFun.getParameterStr(mSaleChnl, "?mSaleChnl?")) +") "
			                   + ReportPubFun.getWherePartLike("managecom",ReportPubFun.getParameterStr(mManageCom, "?mManageCom?"))
			                   + " and exists(select '1' from lpedormain where a.edorno=edorno and a.contno=contno  "
							   + ReportPubFun.getWherePart("confdate", ReportPubFun.getParameterStr(mTBBeginDate, "?mTBBeginDate?"),ReportPubFun.getParameterStr(mTBEndDate, "?mTBEndDate?"),1)
							   + ")"
							   + " and exists(select '1'from lccont where contno = a.contno and signdate between '"
							   + "?mCBBeginDate?" + "' and " + " '" + "?mCBEndDate?" + "')"
			                   + " order by managecom,edorno,contno ";
			        logger.debug(sql);
			        sqlbv2.sql(sql);
			        sqlbv2.put("mEdorType", mEdorType);
			        sqlbv2.put("mSaleChnl", mSaleChnl);
			        sqlbv2.put("mManageCom", mManageCom);
			        sqlbv2.put("mTBBeginDate", mTBBeginDate);
			        sqlbv2.put("mTBEndDate", mTBEndDate);
			        sqlbv2.put("mCBBeginDate", mCBBeginDate);
			        sqlbv2.put("mCBEndDate", mCBEndDate);
			        SSRS tssrs = new ExeSQL().execSQL(sqlbv2);
			        if(tssrs == null || tssrs.getMaxRow() <= 0)
			        {  logger.debug("统计数据为空");
			        	CError.buildErr(this ,"统计数据为空" );
			            return false;
			        }

			        String[] strArr = new String[13];
			        for (int i = 1 ; i <= tssrs.getMaxRow(); i++)
			        {
			            

			            strArr = new String[13];
			            strArr[0] = tssrs.GetText(i,1);                                     //保单号
			            strArr[1] = tssrs.GetText(i,2);                                     //批单号
			            strArr[2] = tssrs.GetText(i,3);                                     //投保人
			            strArr[3] = tssrs.GetText(i,4);                                     //险种名称
			            strArr[4] = tssrs.GetText(i,5);                                     //保单生效日期
			            strArr[5] = tssrs.GetText(i,6);                                     //退费金额
			            strArr[6] = ReportPubFun.functionJD(Double.parseDouble(tssrs.GetText(i,7)),"0.00");              //实际现价
			            strArr[7] = ReportPubFun.functionJD(Double.parseDouble(tssrs.GetText(i,8)),"0.00"); //差值
			            strArr[8] = ReportPubFun.getAgentName(tssrs.GetText(i,9));          //代理人
			            strArr[9] = tssrs.GetText(i,9);                                     //代理人编码
			            strArr[10] = tssrs.GetText(i,10);                                    //分公司
			            strArr[11] = tssrs.GetText(i,11);                                    //管理机构
			                                        //操作员代码
			            strArr[12] = tssrs.GetText(i,12);                                   //操作日期
			            tlistTable.add(strArr);
			        }

			        xmlexport.addListTable(tlistTable, strArr);

							TextTag texttag = new TextTag(); //新建一个TextTag的实例
							texttag.add("ManageCom", ReportPubFun.getMngName(mManageCom));
							texttag.add("CBBdate", mCBBeginDate);
							texttag.add("CBEdate", mCBEndDate);
							texttag.add("TBBdate", mTBBeginDate);
							texttag.add("TBEdate", mTBEndDate);
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
	       

	      

	        return true;
	    }

	    /*
	    * @function :in order to debug
	    */
	    public static void main(String[] args)
	    {
	    }
	}

