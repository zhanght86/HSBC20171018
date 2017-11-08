package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/**
 * <p>Title:保全报表</p>
 * <p>Description: 1张报表</p>
 * <p>bq1：保全人员工作量统计</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: sinosoft</p>
 * @version 1.0
 */

public class BQWorkStatOperatorBL {
private static Logger logger = Logger.getLogger(BQWorkStatOperatorBL.class);
	  /** 错误处理类，每个需要错误处理的类中都放置该类 */
		public CErrors mErrors = new CErrors();
		private VData mResult = new VData();

		/** 全局数据 */
		private GlobalInput mGlobalInput = new GlobalInput();
		private String mStartDate = ""; // 统计开始时间
		private String mEndDate = "";   //统计结束日期
		private String mManageCom = ""; //统计机构

		public BQWorkStatOperatorBL() {
		}

		/**传输数据的公共方法*/
		public boolean submitData(VData cInputData, String cOperate)
		{
				if (!cOperate.equals("PRINT"))
				{
						CError.buildErr(this, "不支持的操作字符串");
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

		/**
		 * 从输入数据中得到所有对象
		 * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
		 */
		private boolean getInputData(VData cInputData)
		{
				mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName("GlobalInput",0));
				TransferData tTransferData = (TransferData)cInputData.getObjectByObjectName("TransferData",0);
				if (tTransferData == null || mGlobalInput == null)
				{
						CError.buildErr(this, "缺少传入后台的参数！");
						return false;
				}

				mStartDate = (String)tTransferData.getValueByName("StartDate");
				mEndDate = (String)tTransferData.getValueByName("EndDate");
				mManageCom = (String)tTransferData.getValueByName("ManageCom");
				if(mStartDate.equals("") || mEndDate.equals("") || mManageCom.equals(""))
				{
						CError.buildErr(this, "没有得到足够的查询信息！");
						return false;
				}

				return true;
		}

		/**
		 * 获取打印数据
		 */
		private boolean getPrintData()
		{
				XmlExport xmlexport = new XmlExport(); //新建一个XmlExport的实例
				xmlexport.createDocument("BQWorkStatOperator.vts", "");

				ListTable tlistTable = new ListTable();
				tlistTable.setName("WorkStat");
				SQLwithBindVariables sqlbv = new SQLwithBindVariables();
				String sql = "select (select name from ldcom where trim(comcode)=substr(com,1,4)),"
							  + "opt,(select username from lduser where usercode=opt),sum(t1),sum(t2),sum(t3),sum(rate), "
								+ "(select name from ldcom where trim(comcode)=com) from (select com,opt,etype,"
								+ "(case type when '1' then 1 else 0 end) t1,(case type when '2' then 1 else 0 end) t2,(case type when '3' then 1 else 0 end) t3,rate from "
								+ "(select substr(min(managecom),1,6) com,"
								+ "min(operator) opt,edorno,edortype etype,"
								+ "(select convertrate from lmedorstatrate where edortype=a.edortype) rate,"
								+ "(select stattype from lmedorstatrate where edortype=a.edortype) type "
								+ "From lpedoritem a  where edorstate='0' and grpcontno='00000000000000000000' "
								+" and exists(select '1' from lpedormain where a.edorno=edorno and a.contno=contno  "
								+ ReportPubFun.getWherePart("confdate", ReportPubFun.getParameterStr(mStartDate, "?mStartDate?"),ReportPubFun.getParameterStr(mEndDate, "?mEndDate?"),1)
								+")"
								+ " and managecom like concat('"+ "?mManageCom?" + "','%')  group by edorno,edortype"
							
								+ " ))  where exists (select 1 from lduser where usercode=opt) "
								+ "group by com,opt order by com,opt"
								;
				sqlbv.sql(sql);
				sqlbv.put("mStartDate", mStartDate);
				sqlbv.put("mEndDate", mEndDate);
				sqlbv.put("mManageCom", mManageCom);
				SSRS tssrs = new ExeSQL().execSQL(sqlbv);
				if(tssrs == null || tssrs.getMaxRow() <= 0)
				{
						CError.buildErr(this, "查询到的数据为空！");
						return false;
        }

				String[] strArr;
				for (int i = 1 ; i <= tssrs.getMaxRow(); i++)
				{
						strArr = new String[9];
						strArr[0] = tssrs.GetText(i,1); //二级机构
						strArr[1] = tssrs.GetText(i,2); //操作员编码
						strArr[2] = tssrs.GetText(i,3); //操作员姓名
						strArr[3] = tssrs.GetText(i,4); //变更类项目操作件数
						strArr[4] = tssrs.GetText(i,5); //给付类项目操作件数
						strArr[5] = tssrs.GetText(i,6); //其他类项目操作件数
						strArr[6] = ReportPubFun.functionJD(Double.valueOf(tssrs.GetText(i,4)).intValue()+Double.valueOf(tssrs.GetText(i,5)).intValue()+Double.valueOf(tssrs.GetText(i,6)).intValue(),"0");//合计
						strArr[7] = tssrs.GetText(i,7); //标准工作量
						strArr[8] = tssrs.GetText(i,8); //三级机构
						tlistTable.add(strArr);
				}

				String[] tArr = new String[9];
				tArr[0] = "";     //"二级机构";
				tArr[1] = "";     //"操作员编码";
				tArr[2] = "";     //"操作员姓名";
				tArr[3] = "";     //"变更类件数";
				tArr[4] = "";     //"给付类件数";
				tArr[5] = "";     //"其他类件数";
				tArr[6] = "";     //"合计件数";
				tArr[7] = "";     //"标准工作量";
				tArr[8] = "";     //"三级机构";
				xmlexport.addListTable(tlistTable, tArr);

				TextTag texttag = new TextTag(); //新建一个TextTag的实例
				texttag.add("ManageCom", ReportPubFun.getMngName(mManageCom));
				texttag.add("StartDate", mStartDate);
				texttag.add("EndDate", mEndDate);
				texttag.add("date", PubFun.getCurrentDate());
				logger.debug("大小" + texttag.size());
				if (texttag.size() > 0)
				{
						xmlexport.addTextTag(texttag);
				}

				//xmlexport.outputDocumentToFile("e:\\","BQWorkStat.xml");//输出xml文档到文件
				mResult.clear();
				mResult.addElement(xmlexport);

        return true;
		}

		public VData getResult()
		{
				return this.mResult;
		}

		/* 测试 */
		public static void main (String []args)
		{
				GlobalInput tGlobalInput = new GlobalInput();
				tGlobalInput.ComCode = "86";
				tGlobalInput.ManageCom = "8613";
				tGlobalInput.Operator = "DEBUG";

				TransferData tTransferData = new TransferData();
				tTransferData.setNameAndValue("StartDate","2006-01-01");
				tTransferData.setNameAndValue("EndDate","2006-05-31");
				tTransferData.setNameAndValue("ManageCom","8613");
				tTransferData.setNameAndValue("StatType","6");

				VData tVData = new VData();
				tVData.add(tGlobalInput);
				tVData.add(tTransferData);

				BQWorkStatOperatorBL tBQWorkStatOperatorBL = new BQWorkStatOperatorBL();
				tBQWorkStatOperatorBL.submitData(tVData,"PRINT");
    }
}
