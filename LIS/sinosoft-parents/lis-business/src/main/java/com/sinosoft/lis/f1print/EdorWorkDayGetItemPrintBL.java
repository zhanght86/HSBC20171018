package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.bq.*;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2009
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author pst
 * @version 1.0
 * @CreateDate：2009-04-11 清单名称：保全退保统计表
 */

public class EdorWorkDayGetItemPrintBL implements BqBill {
private static Logger logger = Logger.getLogger(EdorWorkDayGetItemPrintBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	private TransferData mTransferData = new TransferData();

	private VData mInputData = new VData();

	private String mOperate = "";

	private GlobalInput mGlobalInput = new GlobalInput();

	public static final String VTS_NAME = "EdorWorkDayGetItem.vts";

	private TextTag texttag = new TextTag();

	private String mManageCom = "";

	private String mSaleChnl = "";

	private String mStartDate = "";

	private String mEndDate = "";

	private String mUserCode = "";

	private String mEdorType = "";

	private String mSaleChlSQL = "";

	private String mUserCodeSQL = "";

	private String mEdorTypeSQL = "";

	private String mDateSQL = "";

	private String mRePortFlag = "";

	private String mRePortFlagSQL = "";

	private String mRePortFlagSQLMG = "";

	private int tComLength = 0;

	private String mOrderSQL = "order by a.managecom,a.edortype";

	private ListTable tBonusListTable = new ListTable();

	private XmlExport tXmlExport = new XmlExport();

	/** 其他机构的编码，指此次统计指标中为零的机构 */
	private String tOtherManageCom = "";

	/**
	 * 1|退保概况 2|险种退保概况 3|协议退保原因 4|协议退保差值比较 5|协议退保险种差值比较 6|撤件率 7|协议退保差值明细
	 */
	private String tTypeControl = "displayCT";

	public EdorWorkDayGetItemPrintBL() {

	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据
	 * @param: cOperate 数据操作
	 * @return: boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;
		if (!mOperate.equals("PRINT")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "EdorAllCTBillPrintBL";
			tError.functionName = "submitData";
			tError.errorMessage = "不支持的操作字符串！";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}

		// 准备需要打印的数据
		if (!preparePrintData()) {
			return false;
		}

		return true;
	}

	private boolean getInputData(VData cInputData) {
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		if (mGlobalInput == null || mTransferData == null) {
			mErrors.addOneError(new CError("数据传输不完全！"));
			return false;
		}
		mStartDate = (String) mTransferData.getValueByName("StartDate");
		if (mStartDate == null || mStartDate.trim().equals("")) {
			mErrors.addOneError(new CError("没有得到足够的信息:统计起期不能为空！"));
			return false;
		}
		mEndDate = (String) mTransferData.getValueByName("EndDate");
		if (mEndDate == null || mEndDate.trim().equals("")) {
			mErrors.addOneError(new CError("没有得到足够的信息:统计止期不能为空！"));
			return false;
		}
		mManageCom = (String) mTransferData.getValueByName("ManageCom");
		if (mManageCom == null || mManageCom.trim().equals("")) {
			mErrors.addOneError(new CError("没有得到足够的信息:机构不能为空！"));
			return false;
		}
		if (mManageCom.length() == 2) {
			tComLength = 4;
		} else if (mManageCom.length() == 4) {
			tComLength = 6;
		} else {
			tComLength = 8;
		}

		mSaleChnl = (String) mTransferData.getValueByName("SaleChnl");

		// 处理渠道

		mEdorType = (String) mTransferData.getValueByName("EdorType");

		// 1|退保概况 2|险种退保概况 3|协议退保原因 4|协议退保原因 5|协议退保险种差值比较 6|撤件率 7|协议退保差值明细
		mRePortFlag = (String) mTransferData.getValueByName("NewRePortFlag");
		if (mRePortFlag != null && !"".equals(mRePortFlag)) {
			tXmlExport.createDocument(VTS_NAME, "printer"); // 初始化xml文档
			SSRS tssrs = new SSRS();
			ExeSQL texesql = new ExeSQL();
			ListTable tContListTable = new ListTable();
			ListTable tPolListTable = new ListTable();
			String strLine[] = null;
			String sql = "";
			SSRS rssrs = new SSRS();
			String comSql="";
			String headSql="";
			double tSum1 = 0, tSum2 = 0, tSum3 = 0, tSum4 = 0, tSum5 = 0, tSum6 = 0, tSum7 = 0, tSum8 = 0,tSum9 = 0,tSum10 = 0;
			switch (Integer.parseInt(mRePortFlag)) {
			case 1: //
				texttag.add("Title", "个险保全补费件5日处理完成情况统计");
				texttag.add("Makedate",PubFun.getCurrentDate()+" "+PubFun.getCurrentTime() );
				tTypeControl = tTypeControl + mRePortFlag;


				if (mSaleChnl != null && !"".equals(mSaleChnl)) {
					mSaleChlSQL = " and exists (select 'X'" + " from lccont p "
							+ "where p.contno = a.otherno "
							+ " and p.salechnl = '" + "?mSaleChnl?" + "') ";
				}
				else
				{
					mSaleChlSQL = " and exists (select 'X'" + " from lccont p "
					+ "where p.contno = a.otherno "
					+ " and p.salechnl in ( '02','03','05','07','10')) ";
				}
				
				SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
					comSql=") a left join (select comcode from ldcom where (char_length(trim(comcode))="+tComLength+"  or comcode='"+"?mManageCom?"+"') and comcode not like '8699%' and comcode like concat('"+"?mManageCom?"+"','%')) b  on a.comcode=b.comcode order by b.comcode";
					
				headSql="select (select name from ldcom where comcode = b.comcode) com,(case when ca is null then 0 else ca end) ca,(case when cb is null then 0 else cb end) cb ,(case when cc is null then 0 else cc end) c from (";
				// 按机构进行统计
				sql=headSql+"select (select name from ldcom where comcode=b.com) com ,b.com comcode,(case when ca is null then 0 else ca end) ca,cb,round((case when ca is null then 0 else ca end)/cb,4)*100  cc from "
				+" (select substr(managecom,1,"+tComLength+") com,count(*) ca  from (select (select substr(comcode,1,"+tComLength+") from lduser c where a.operator=usercode) managecom,a.edoracceptno From lpedorapp a"
				+" where  a.confdate >= '" + "?mStartDate?" + "'"
				+" and a.confdate <= '" + "?mEndDate?" + "' and  exists (select '1' from lduser c where usercode=a.operator and comcode like concat('"+"?mManageCom?"+"','%'))  and a.edorstate in ('0', 'b')"
				+" and othernotype = '3' and getmoney>0"+mSaleChlSQL
				+" and exists (select 'X' from lpedoritem p"
				+" where p.contno = a.otherno and p.edoracceptno = a.edoracceptno"
				+" and p.edortype in (select code from ldcode where codetype = 'edortype' and othersign = '1'))"
				+" and exists (select '1' from lccont where contno=a.otherno and conttype<>'2') and (exists (select '1' from ljagetendorse b where a.otherno = contno" 
				+" and exists (select count(*) from ldcalendar where commondate >= b.makedate and commondate <= a.confdate and workdateflag = 'Y' having count(*)-1 <= 5)"
				+" ) or exists (select 'X' from ljapay b where incomeno = a.edoracceptno and exists (select count(*) from ldcalendar where commondate >= b.confdate  and commondate <= a.confdate and workdateflag = 'Y' having count(*) - 1 <= 5)))) group by  substr(managecom,1,"+tComLength+")) a left join "
				+" (select substr(managecom,1,"+tComLength+") com,count(*) cb  from (select (select substr(comcode,1,"+tComLength+") from lduser c where a.operator=usercode) managecom,a.edoracceptno From lpedorapp a"
				+" where  a.confdate >= '" + "?mStartDate?" + "'"
				+" and a.confdate <= '" +"?mEndDate?"+ "' and  exists (select '1' from lduser c where usercode=a.operator and comcode like concat('"+"?mManageCom?"+"','%'))  and a.edorstate in ('0', 'b')"
				+" and othernotype = '3' and getmoney>0"+mSaleChlSQL
				+" and exists (select 'X' from lpedoritem p"
				+" where p.contno = a.otherno and p.edoracceptno = a.edoracceptno"
				+" and p.edortype in (select code from ldcode where codetype = 'edortype' and othersign = '1')) " +
						") group by  substr(managecom,1,"+tComLength+") ) b"
				+" on a.com=b.com"+comSql;
				logger.debug("SQL:" + sql);
				sqlbv1.sql(sql);
				sqlbv1.put("mManageCom", mManageCom);
				sqlbv1.put("mStartDate", mStartDate);
				sqlbv1.put("mEndDate", mEndDate);
				sqlbv1.put("mSaleChnl", mSaleChnl);
				rssrs = texesql.execSQL(sqlbv1);
				for (int k = 1; k <= rssrs.getMaxRow(); k++) {
					
					strLine = new String[5];
					strLine[0]=k+"";
		              for(int j=1;j<5;j++)
		      	      {
		            	  strLine[j] = rssrs.GetText(k,j);
		              }
		              strLine[4]=BqNameFun.getFormat(strLine[4])+"%";
		              tSum1+= Double.parseDouble(strLine[2]);
		              tSum2+= Double.parseDouble(strLine[3]);
		              tContListTable.add(strLine);
		              					
				}

				tXmlExport.addDisplayControl(tTypeControl);

				tContListTable.setName("BILL");

				strLine = new String[5];
				strLine[0] = " ";
				strLine[1] = "合   计";
				strLine[2] = String.valueOf((int) tSum1);
				strLine[3] = String.valueOf((int) tSum2);
				if(strLine[3].equals("0"))
					strLine[4]="0.00%";
				else
				strLine[4] = String.valueOf(BqNameFun.getFormat(tSum1/tSum2*100))+"%";
				tContListTable.add(strLine);
				tXmlExport.addListTable(tContListTable, strLine);

				break;
			case 2: //
				texttag.add("Title", "个险保全问题件5日处理情况统计");
				texttag.add("Makedate",PubFun.getCurrentDate()+" "+PubFun.getCurrentTime() );
				tTypeControl = tTypeControl + mRePortFlag;


				if (mSaleChnl != null && !"".equals(mSaleChnl)) {
					mSaleChlSQL = " and exists (select 'X'" + " from lccont p "
							+ "where p.contno = b.contno "
							+ " and p.salechnl = '" + "?mSaleChnl?" + "') ";
				}
				else
				{
					mSaleChlSQL = " and exists (select 'X'" + " from lccont p "
					+ "where p.contno = b.contno "
					+ " and p.salechnl in ( '02','03','05','07','10')) ";
				}
				SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
				    comSql=") a left join (select comcode from ldcom where (char_length(trim(comcode))="+tComLength+"  or comcode='"+"?mManageCom?"+"') and comcode not like '8699%' and comcode like concat('"+"?mManageCom?"+"','%')) b  on a.comcode=b.comcode order by b.comcode";

					headSql="select (select name from ldcom where comcode = b.comcode) com,(case when ca is null then 0 else ca end) ca,(case when cb is null then 0 else cb end) cb ,(case when cc is null then 0 else cc end) c from (";

				// 按机构进行统计
				sql = headSql+" select (select name from ldcom where comcode=b.com) com ,b.com comcode,(case when ca is null then 0 else ca end) ca,cb,round((case when ca is null then 0 else ca end)/cb,4)*100 cc from "
					 +" (select  substr(trim(managecom), 1, "+tComLength+") com, count(*) ca  from (select (select substr(comcode,1,"+tComLength+") from lduser c where d.operator=usercode) managecom,b.edoracceptno"
					 +" from loprtmanager a,lpedoritem b,lpedorapp d"
					 +" where a.code in ('BQ37', 'BQ38', 'BQ33') and a.makedate >= '" + "?mStartDate?" + "' and b.edoracceptno=d.edoracceptno  and a.makedate <= '" + "?mEndDate?" + "' and a.standbyflag1=b.edoracceptno and  exists (select '1' from lduser c where usercode=d.operator and comcode like concat('"+"?mManageCom?"+"','%')) "
					 + mSaleChlSQL
					 +" and exists (select '1'  from ldcalendar  where commondate >= b.edorappdate and commondate <= a.makedate and workdateflag = 'Y' having count(*) - 1 <= 5)"
					 +" ) group by substr(trim(managecom), 1, "+tComLength+") ) a left join "
					 +" (select  substr(trim(managecom), 1, "+tComLength+") com,count(*) cb  from (select (select substr(comcode,1,"+tComLength+") from lduser c where d.operator=usercode) managecom,b.edoracceptno from loprtmanager a ,lpedoritem b,lpedorapp d"
					 +" where a.code in ('BQ37', 'BQ38', 'BQ33') and a.makedate >= '" + "?mStartDate?" + "'  and b.edoracceptno=d.edoracceptno  and a.makedate <= '" + "?mEndDate?" + "' and a.standbyflag1=b.edoracceptno  and  exists (select '1' from lduser c where usercode=d.operator and comcode like concat('"+"?mManageCom?"+"','%')) "
					 + mSaleChlSQL
					 +") group by substr(trim(managecom), 1, "+tComLength+"))b"
					 +" on a.com=b.com"+comSql;
				logger.debug("SQL:" + sql);
				sqlbv2.sql(sql);
				sqlbv2.put("mManageCom", mManageCom);
				sqlbv2.put("mStartDate", mStartDate);
				sqlbv2.put("mSaleChnl", mSaleChnl);
				sqlbv2.put("mEndDate", mEndDate);
				rssrs = texesql.execSQL(sqlbv2);
				for (int k = 1; k <= rssrs.getMaxRow(); k++) {
					
					strLine = new String[5];
					strLine[0]=k+"";
		              for(int j=1;j<5;j++)
		      	      {
		            	  strLine[j] = rssrs.GetText(k,j);
		              }
		              strLine[4]=BqNameFun.getFormat(strLine[4])+"%";
		              tSum1+= Double.parseDouble(strLine[2]);
		              tSum2+= Double.parseDouble(strLine[3]);
		              tContListTable.add(strLine);
		              					
				}

				tXmlExport.addDisplayControl(tTypeControl);

				tContListTable.setName("BILL");

				strLine = new String[5];
				strLine[0] = " ";
				strLine[1] = "合   计";
				strLine[2] = String.valueOf((int) tSum1);
				strLine[3] = String.valueOf((int) tSum2);
				if(strLine[3].equals("0"))
					strLine[4]="0.00%";
				else
				strLine[4] = String.valueOf(BqNameFun.getFormat(tSum1/tSum2*100))+"%";
				tContListTable.add(strLine);
				tXmlExport.addListTable(tContListTable, strLine);
				break;
			case 3: //
				texttag.add("Title", "个险保全5日结案情况统计");
				texttag.add("Makedate",PubFun.getCurrentDate()+" "+PubFun.getCurrentTime() );
				tTypeControl = tTypeControl + mRePortFlag;


				if (mSaleChnl != null && !"".equals(mSaleChnl)) {
					mSaleChlSQL = " and exists (select 'X'" + " from lccont p "
							+ "where p.contno = a.contno "
							+ " and p.salechnl = '" + "?mSaleChnl?" + "') ";
				}
				else
				{
					mSaleChlSQL = " and exists (select 'X'" + " from lccont p "
					+ "where p.contno = a.contno "
					+ " and p.salechnl in ( '02','03','05','07','10')) ";
				}

				SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
				comSql=") a left join (select comcode from ldcom where (char_length(trim(comcode))="+tComLength+"  or comcode='"+"?mManageCom?"+"') and comcode not like '8699%' and comcode like concat('"+"?mManageCom?"+"','%')) b  on a.comcode=b.comcode order by b.comcode";
				
				headSql="select (select name from ldcom where comcode = b.comcode) com,(case when ca is null then 0 else ca end) ca,(case when cb is null then 0 else cb end) cb ,(case when cc is null then 0 else cc end) c from (";

				// 按机构进行统计
				sql = headSql+"select (select name from ldcom where comcode=b.com) com ,b.com comcode,(case when ca is null then 0 else ca end) ca,cb,round((case when ca is null then 0 else ca end)/cb,4)*100 cc from "
					 +" (select substr(managecom,1,"+tComLength+") com,count(*) ca  from (select (select substr(comcode,1,"+tComLength+") from lduser c where d.operator=usercode) managecom,a.edoracceptno from lpedoritem a,lpedorapp d"
					 +" where a.modifydate >= '" + "?mStartDate?" + "' and a.modifydate <= '" + "?mEndDate?" + "' and a.edoracceptno=d.edoracceptno  and  exists (select '1' from lduser c where usercode=d.operator and comcode like concat('"+"?mManageCom?"+"','%'))  and a.edorstate in ('0','9','c','d')  "
					 +mSaleChlSQL
					 +" and exists (select count(*) from ldcalendar where commondate >= a.edorappdate and commondate <= a.modifydate and workdateflag = 'Y' having count(*)-1 <= 5) "
					 +") group by  substr(managecom,1,"+tComLength+")) a left join "
					 +" (select substr(managecom,1,"+tComLength+") com,count(*) cb  from (select (select substr(comcode,1,"+tComLength+") from lduser c where d.operator=usercode) managecom,a.edoracceptno from lpedoritem a,lpedorapp d "
					 +" where a.modifydate >= '" + "?mStartDate?" + "' and a.modifydate <= '" + "?mEndDate?" + "'  and a.edoracceptno=d.edoracceptno and   exists (select '1' from lduser c where usercode=d.operator and comcode like concat('"+"?mManageCom?"+"','%'))  and a.edorstate in ('0','9','c','d')"
					 +mSaleChlSQL
					 +")  group by  substr(managecom,1,"+tComLength+") ) b"
					 +" on a.com=b.com"+comSql;
				logger.debug("SQL:" + sql);
				sqlbv3.sql(sql);
				sqlbv3.put("mManageCom", mManageCom);
				sqlbv3.put("mStartDate", mStartDate);
				sqlbv3.put("mEndDate", mEndDate);
				sqlbv3.put("mSaleChnl", mSaleChnl);
				rssrs = texesql.execSQL(sqlbv3);
				for (int k = 1; k <= rssrs.getMaxRow(); k++) {
					
					strLine = new String[5];
					strLine[0]=k+"";
		              for(int j=1;j<5;j++)
		      	      {
		            	  strLine[j] = rssrs.GetText(k,j);
		              }
		              strLine[4]=BqNameFun.getFormat(strLine[4])+"%";
		              tSum1+= Double.parseDouble(strLine[2]);
		              tSum2+= Double.parseDouble(strLine[3]);
		              tContListTable.add(strLine);
		              					
				}

				tXmlExport.addDisplayControl(tTypeControl);

				tContListTable.setName("BILL");

				strLine = new String[5];
				strLine[0] = " ";
				strLine[1] = "合   计";
				strLine[2] = String.valueOf((int) tSum1);
				strLine[3] = String.valueOf((int) tSum2);
				if(strLine[3].equals("0"))
					strLine[4]="0.00%";
				else
				strLine[4] = String.valueOf(BqNameFun.getFormat(tSum1/tSum2*100))+"%";
				tContListTable.add(strLine);
				tXmlExport.addListTable(tContListTable, strLine);
				break;
			case 4:
				texttag.add("Title", "个险保全5日系统处理完成情况统计");
				texttag.add("Makedate",PubFun.getCurrentDate()+" "+PubFun.getCurrentTime() );
				tTypeControl = tTypeControl + mRePortFlag;


				if (mSaleChnl != null && !"".equals(mSaleChnl)) {
					mSaleChlSQL = " and exists (select 'X'" + " from lccont p "
							+ "where p.contno = a.otherno "
							+ " and p.salechnl = '" + "?mSaleChnl?" + "') ";
				}
				else
				{
					mSaleChlSQL = " and exists (select 'X'" + " from lccont p "
					+ "where p.contno = a.otherno "
					+ " and p.salechnl in ( '02','03','05','07','10')) ";
				}

				SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
				comSql=") a left join (select comcode from ldcom where (char_length(trim(comcode))="+tComLength+"  or comcode='"+"?mManageCom?"+"') and comcode not like '8699%' and comcode like concat('"+"?mManageCom?"+"','%')) b  on a.comcode=b.comcode order by b.comcode";
				
				headSql="select (select name from ldcom where comcode = b.comcode) com,(case when ca is null then 0 else ca end) ca,(case when cb is null then 0 else cb end) cb ,(case when cc is null then 0 else cc end) c from (";

				// 按机构进行统计
				sql = headSql+"select (select name from ldcom where comcode=b.com) com ,b.com comcode,(case when ca is null then 0 else ca end) ca,cb,round((case when ca is null then 0 else ca end)/cb,4)*100 cc from "
					 +" (select substr(managecom,1,"+tComLength+") com,count(*) ca  from (select (select substr(comcode,1,"+tComLength+") from lduser c where a.operator=usercode) managecom,a.edoracceptno from lpedorapp a,lpedormain b"
					 +" where a.modifydate >= '" + "?mStartDate?" + "' and a.modifydate <= '" + "?mEndDate?" + "' and a.edoracceptno=b.edoracceptno and exists (select '1' from lduser c where usercode=a.operator and comcode like concat('"+"?mManageCom?"+"','%'))  and a.edorstate in ('0','9','c','d') and (a.getmoney<=0 or (a.getmoney>0 and a.confdate is  null))"
					 +mSaleChlSQL
					 +" and not exists (select '1' from lpuwmastermain where autouwflag='2' and contno=a.otherno and edorno=b.edorno)"
					 +" and exists (select count(*) from ldcalendar where commondate >= a.makedate and commondate <= a.modifydate and workdateflag = 'Y' having count(*)-1 <= 5) "
					 +") group by  substr(managecom,1,"+tComLength+")) a left join "
					 +" (select substr(managecom,1,"+tComLength+") com,count(*) cb  from (select (select substr(comcode,1,"+tComLength+") from lduser c where a.operator=usercode) managecom,a.edoracceptno from lpedorapp a,lpedormain b"
					 +" where a.modifydate >= '" + "?mStartDate?" + "' and a.modifydate <= '" + "?mEndDate?" + "' and a.edoracceptno=b.edoracceptno  and  exists (select '1' from lduser c where usercode=a.operator and comcode like concat('"+"?mManageCom?"+"','%'))  and a.edorstate in ('0','9','c','d') and (a.getmoney<=0 or (a.getmoney>0 and a.confdate is  null)) "
					 +mSaleChlSQL
					 +" and not exists (select '1' from lpuwmastermain where autouwflag='2' and contno=a.otherno and edorno=b.edorno)"
					 +")  group by  substr(managecom,1,"+tComLength+") ) b"
					 +" on a.com=b.com"+comSql;
				logger.debug("SQL:" + sql);
				sqlbv4.sql(sql);
				sqlbv4.put("mManageCom", mManageCom);
				sqlbv4.put("mStartDate", mStartDate);
				sqlbv4.put("mEndDate", mEndDate);
				sqlbv4.put("mSaleChnl", mSaleChnl);
				rssrs = texesql.execSQL(sqlbv4);
				for (int k = 1; k <= rssrs.getMaxRow(); k++) {
					
					strLine = new String[5];
					strLine[0]=k+"";
		              for(int j=1;j<5;j++)
		      	      {
		            	  strLine[j] = rssrs.GetText(k,j);
		              }
		              strLine[4]=BqNameFun.getFormat(strLine[4])+"%";
		              tSum1+= Double.parseDouble(strLine[2]);
		              tSum2+= Double.parseDouble(strLine[3]);
		              tContListTable.add(strLine);
		              					
				}

				tXmlExport.addDisplayControl(tTypeControl);

				tContListTable.setName("BILL");

				strLine = new String[5];
				strLine[0] = " ";
				strLine[1] = "合   计";
				strLine[2] = String.valueOf((int) tSum1);
				strLine[3] = String.valueOf((int) tSum2);
				if(strLine[3].equals("0"))
					strLine[4]="0.00%";
				else
				strLine[4] = String.valueOf(BqNameFun.getFormat(tSum1/tSum2*100))+"%";
				tContListTable.add(strLine);
				tXmlExport.addListTable(tContListTable, strLine);
				break;
			case 5://

				texttag.add("Title", "个险保全2日受理情况统计");
				texttag.add("Makedate",PubFun.getCurrentDate()+" "+PubFun.getCurrentTime() );
				tTypeControl = tTypeControl + mRePortFlag;

                String mSaleChlSQL2="";
				if (mSaleChnl != null && !"".equals(mSaleChnl)) {
					mSaleChlSQL = " and exists (select 'X'" + " from lccont p "
							+ "where p.contno = d.otherno "
							+ " and p.salechnl = '" + "?mSaleChnl?" + "') ";
					mSaleChlSQL2=" and exists (select '1' from lpedorapp b where a.doccode=b.edoracceptno  and exists (select 'X'" + " from lccont p "
							+ "where p.contno = b.otherno "
							+ " and p.salechnl = '" + "?mSaleChnl?" + "') )";
				}
				else
				{
					mSaleChlSQL = " and exists (select 'X'" + " from lccont p "
					+ "where p.contno = d.otherno "
					+ " and p.salechnl in ( '02','03','05','07','10')) ";
				}

				SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
				comSql=") a left join (select comcode from ldcom where (char_length(trim(comcode))="+tComLength+"  or comcode='"+"?mManageCom?"+"') and comcode not like '8699%' and comcode like concat('"+"?mManageCom?"+"','%')) b  on a.comcode=b.comcode order by b.comcode";
				
				headSql="select (select name from ldcom where comcode = b.comcode) com,(case when ca is null then 0 else ca end) ca,(case when cb is null then 0 else cb end) cb ,(case when cc is null then 0 else cc end) c from (";

				// 按机构进行统计
				sql =headSql+"select (select name from ldcom where comcode=b.com) com ,b.com comcode,(case when ca is null then 0 else ca end) ca,cb,round((case when ca is null then 0 else ca end)/cb,4)*100 cc from "
					+ " (select substr(managecom, 1, "+tComLength+") com, count(*) ca  from (select (select substr(comcode,1,"+tComLength+") from lduser c where d.operator=usercode) managecom,b.edoracceptno from lpedoritem b,lpedorapp d"
					+ " where  d.makedate >= '" + "?mStartDate?" + "' and d.makedate <= '" + "?mEndDate?" + "' and b.edorstate not in('1','3') and b.edoracceptno=d.edoracceptno and  exists (select '1' from lduser c where usercode=d.operator and comcode like concat('"+"?mManageCom?"+"','%'))  "
					+mSaleChlSQL
					+ " and exists (select '1' from ldcalendar where commondate <= d.makedate and commondate >= b.edorappdate and workdateflag = 'Y' having count(*) - 1 <= 2)"
					+ ") group by substr(managecom, 1, "+tComLength+")) a left join "
					+ "	(select substr(managecom, 1, "+tComLength+") com, count(*) cb  from (select (select substr(comcode,1,"+tComLength+") from lduser c where d.operator=usercode) managecom,a.edoracceptno from lpedoritem a,lpedorapp d "
					+ "	where  d.makedate >= '" + "?mStartDate?" + "' and d.makedate <= '" + "?mEndDate?" + "' and a.edoracceptno=d.edoracceptno  and  exists (select '1' from lduser c where usercode=d.operator and comcode like concat('"+"?mManageCom?"+"','%')) "
					+mSaleChlSQL
					+ ") group by substr(managecom, 1, "+tComLength+")) b  "
					+ "	on a.com = b.com "+comSql;
				
				logger.debug("SQL:" + sql);
				sqlbv5.sql(sql);
				sqlbv5.put("mManageCom", mManageCom);
				sqlbv5.put("mStartDate", mStartDate);
				sqlbv5.put("mEndDate", mEndDate);
				sqlbv5.put("mSaleChnl", mSaleChnl);
				rssrs = texesql.execSQL(sqlbv5);
				for (int k = 1; k <= rssrs.getMaxRow(); k++) {
					
					strLine = new String[5];
					strLine[0]=k+"";
		              for(int j=1;j<5;j++)
		      	      {
		            	  strLine[j] = rssrs.GetText(k,j);
		              }
		              strLine[4]=BqNameFun.getFormat(strLine[4])+"%";
		              tSum1+= Double.parseDouble(strLine[2]);
		              tSum2+= Double.parseDouble(strLine[3]);
		              tContListTable.add(strLine);
		              					
				}

				tXmlExport.addDisplayControl(tTypeControl);

				tContListTable.setName("BILL");

				strLine = new String[5];
				strLine[0] = " ";
				strLine[1] = "合   计";
				strLine[2] = String.valueOf((int) tSum1);
				strLine[3] = String.valueOf((int) tSum2);
				if(strLine[3].equals("0"))
					strLine[4]="0.00%";
				else
				strLine[4] = String.valueOf(BqNameFun.getFormat(tSum1/tSum2*100))+"%";
				tContListTable.add(strLine);
				tXmlExport.addListTable(tContListTable, strLine);
				break;
			case 6://
				break;
			case 7://

				break;
			}

		}
		return true;
	}

	private boolean preparePrintData() {

		mManageCom = BqNameFun.getCBranch(mManageCom);
		mStartDate = BqNameFun.getChDate(mStartDate);
		mEndDate = BqNameFun.getChDate(mEndDate);
		// 模版自上而下的元素
		texttag.add("ManageCom", mManageCom);
		texttag.add("StartDate", mStartDate);
		texttag.add("EndDate", mEndDate);

		texttag
				.add("PrintDate", BqNameFun
						.getChDate((PubFun.getCurrentDate())));

		if (texttag.size() > 0) {
			tXmlExport.addTextTag(texttag);
		}

		// tXmlExport.outputDocumentToFile("c:\\XML\\", "EdorAllCTBill.vts");
		mResult.clear();
		mResult.addElement(tXmlExport);

		return true;

	}

	public VData getResult() {
		return mResult;
	}

	public CErrors getErrors() {
		return mErrors;
	}
}

