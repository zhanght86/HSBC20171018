<%@include file="/i18n/language.jsp"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.f1print.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="java.util.*"%>
<%@page import="com.sinosoft.service.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="java.io.*"%>
<%@page import="com.f1j.ss.*"%>
<%@page import= "java.text.DecimalFormat"%>
<%
	String tTempleteID = request.getParameter("TempleteID");
	String ReprotType = request.getParameter("ReportType");
	String Startdate = request.getParameter("RValidate");
	String Enddate = request.getParameter("RInvalidate");
	String RIComCode = request.getParameter("RIComCode");
	String RIComName = request.getParameter("RIComName");

	ListTable tlistTable = new ListTable();
	ListTable tlistTableFirst = new ListTable();
	String strArr[] = null;
	String tBusiName = "CreateXMLFile";
	BusinessDelegate tBusinessDelegate = BusinessDelegate
			.getBusinessDelegate();
	TransferData tTransferData = new TransferData();
	XmlExportNew tXmlExportNew = new XmlExportNew();
	ArrayList tArr = new ArrayList();

	String Content = "";
	InputStream tInput;
	TextTag texttag = new TextTag();
	ExeSQL aExeSQL = new ExeSQL();
	SSRS tSSRS = new SSRS();
	SSRS aSSRS = new SSRS();
	SSRS bSSRS = new SSRS();
	PubFun pubFun = new PubFun();
	DecimalFormat df = new DecimalFormat(",###.##");
	
	
	//临分 ----START  
	if (ReprotType.equals("01")) {
		tTempleteID = "003018";
		tlistTable.setName("TEMPROW");//注意这里是循环体表格名称   
		String aasql = "SELECT a.Proposalcontno,  (select x.Nameen from Lcinsured x where x.Contno=a.Contno and x.Insuredno=a.Insuredno) Insuredname , a.Signdate, a.Amnt, decode(a.Currency,'01','CN','13','HK','14','USB') Currency  FROM lccont a, Rigrpstate b where a.Proposalcontno = b.Proposalcontno "
				+ " and b.State = '02'  and a.Signdate Between To_Date('"
				+ Startdate
				+ "' , 'yyyy-mm-dd')    and   To_Date('"
				+ Enddate + "' , 'yyyy-mm-dd')  order by Signdate  ";
		tSSRS = aExeSQL.execSQL(aasql);
		strArr = new String[tSSRS.getMaxCol()];
		for (int a = 1; a <= tSSRS.getMaxRow(); a++) {
			for (int b = 1; b <= tSSRS.getMaxCol(); b++) {
				strArr[b - 1] = tSSRS.GetText(a, b);
				 if(b==4){
						strArr[b - 1]=df.format(Double.parseDouble(tSSRS.GetText(a, b)))   ;	}
			
			}
			tlistTable.add(strArr);
			strArr = new String[tSSRS.getMaxCol()];
		}
	}
	
	//萬能共保(UL coinsurance)----START
	if (ReprotType.equals("02")) {
		tTempleteID = "003130";
		tlistTable.setName("COINROW");
		StringBuffer sql = new StringBuffer();
		sql.append("select b.Contno, b.Idno, b.Insuredname, b.Insuredbirthday,B.InsuredAge, decode(b.InsuredSex, '1', 'F', '0', 'M'),"
				+ " b.Cvalidate, b.Enddate, decode(b.Currency, '13', 'HK', '14', 'US', '01', 'CN'),b.Riskcode, b.InsuredYear,"
				+ " 0 as LastAccBala,"
				+ " 0 as UnitCount,"
				+ " 0 as UnitPrice,"
				+ " 0 as  InsuAccBala,"
				+ "  sum(decode(c.Dutycode, 'U00001', c.Premchang, 0)-decode ( c.Dutycode,'U00001',c.Prepremchang,0)) U00001 ,"
				+ "  sum (decode(c.Dutycode, 'U00002', c.Premchang, 0)-decode ( c.Dutycode,'U00002',c.Prepremchang,0)) U00002 ,"
				+ "  sum(decode(c.Dutycode, 'U00003', c.Premchang, 0)-decode ( c.Dutycode,'U00003',c.Prepremchang,0)) U00003 ,"
				+ " 0,"
				+ " 0,"
				+ " 0,"
				+ " 0,"
				+ " 0,"
				+ " 0,"
				+ " 0,"
				+ " sum(b.Amnt), sum(b.Amnt), '0.95' as CessionRate,"
				+ " nvl(sum(c.Premchang), 0) Premchang,"
				+ " nvl(sum(c.CommChang), 0) CommChang1,"
				+ " nvl(sum(c.Standbydouble2), 0) Chang2,"
				+ " nvl(sum(c.Standbydouble3), 0) Chang3,"
				+ " nvl(sum(c.Standbydouble4), 0) Chang4,"
				+ " nvl(sum(c.Premchang + c.Standbydouble2 + c.Standbydouble3 + c.Standbydouble4), 0) SumCommChang"
				+ " from RIPolRecordBake b, RIRecordTrace c "
				+ " where b.Eventno = c.Eventno"
				+ " and b.Accumulatedefno = 'L01010060010'"
				+ " and b.Riskcode = c.Riskcode"
				+ " and b.Recordtype in ('02','01')"
			    + "  and   b.Getdate  between To_Date('"
				+ Startdate
				+ "' , 'yyyy-mm-dd')    and   To_Date('"
				+ Enddate
				+ "' , 'yyyy-mm-dd')  " + " and b.Riskcode='IBU00'"
				+ " and c.Incomecompanyno='"
				+ RIComCode
				+ "'group by b.Contno,b.Idno, b.Insuredname, b.Insuredbirthday, B.InsuredAge,b.InsuredSex, b.Cvalidate, b.Enddate,b.Currency, b.InsuredYear,b.Riskcode,Polno order by Cvalidate ");
				
		tSSRS = aExeSQL.execSQL(sql.toString());
		strArr = new String[tSSRS.getMaxCol()];
		for (int a = 1; a <= tSSRS.getMaxRow(); a++) {
			for (int b = 1; b <= tSSRS.getMaxCol(); b++) {
				strArr[b - 1] = tSSRS.GetText(a, b);
				  if(b>=12&&tSSRS.GetText(a, b)!=null){
						strArr[b - 1]=df.format(Double.parseDouble(tSSRS.GetText(a, b)))   ;	}
			}
			tlistTable.add(strArr);
			strArr = new String[tSSRS.getMaxCol()];
		}
	}
	//萬能共保(UL coinsurance)----END
	//再保新單續期(inforce)----START
	if (ReprotType.equals("03")) {

		tTempleteID = "003114"; //
		tlistTable.setName("INFORCEROW");
		StringBuffer sql = new StringBuffer();
		sql.append(" select (select companyname from RIComInfo e where e.Companyno=c.Incomecompanyno), decode(b.Currency, '13', 'HK','14', 'US','01','CN'),b.InsuredYear, b.Contno, b.Cvalidate,b.Enddate,b.Currentage, "
				+ "decode(b.InsuredSex, '1', 'F', '0', 'M'), b.Standbystring2, b.HealthTime1, b.HealthTime2,b.OccupationType, b.RiskCode, "
				+ "round(sum( decode(d.Arealevel, '1', decode(d.Possessscale, '0',0, c.CessionAmount/ d.Possessscale))),2) onesum, "
				+ "round(sum(  decode(d.Arealevel, '2', decode(d.Possessscale, '0',0, c.CessionAmount/ d.Possessscale)) ),2) twosum, "
				+ "round(sum( decode(d.Arealevel, '3', decode(d.Possessscale, '0',0, c.CessionAmount/ d.Possessscale))),2) threesum, "
				+ "sum(decode(d.Arealevel, '1', c.CessionAmount)) oneCess,sum(decode(d.Arealevel, '2', c.CessionAmount)) twoCess, sum(decode(d.Arealevel, '3', c.CessionAmount) )threeCess, "
				+ "sum(decode(d.Arealevel, '1', c.PremChang)),sum(decode(d.Arealevel, '2', c.PremChang)), sum(decode(d.Arealevel, '3', c.PremChang)), "
				+ "sum( decode(d.Arealevel, '1', c.CommChang)),sum(decode(d.Arealevel, '2', c.CommChang)),sum( decode(d.Arealevel, '3', c.CommChang)) "
				+ "from  RIPolRecordBake b,RIRecordTrace c, RIRiskDivide d "
				+ "where b.Eventno = c.Eventno  and c.Areaid=d.Areaid and c.Ripreceptno=d.Ripreceptno and c.Incomecompanyno=d.Incomecompanyno "
				+ "and b.Eventtype in ('02', '01')and b.Recordtype='01'   "
				+ " and  b.Getdate  between To_Date('"
				+ Startdate
				+ "' , 'yyyy-mm-dd')    and   To_Date('"
				+ Enddate
				+ "' , 'yyyy-mm-dd')  "
				+ " and c.Incomecompanyno='"
				+ RIComCode
				+ "' group by  b.InsuredYear,b.Contno,b.Cvalidate, b.Enddate,b.Currentage, b.InsuredSex,b.Standbystring2, b.HealthTime1,b.HealthTime2,b.OccupationType,b.RiskCode,c.Incomecompanyno,b.Currency order by b.Currency,Cvalidate  ");

		tSSRS = aExeSQL.execSQL(sql.toString());
		strArr = new String[tSSRS.getMaxCol()];
		for (int a = 1; a <= tSSRS.getMaxRow() ; a++) {
	
				for (int b = 1; b <= tSSRS.getMaxCol(); b++) {
					if (tSSRS.GetText(a, b).equals("0")|| tSSRS.GetText(a, b).equals("null")) {
						tSSRS.SetText(a, b, "0");
					}
					  strArr[b - 1] = tSSRS.GetText(a, b);
					  if(b>=14&&tSSRS.GetText(a, b)!=null){
							strArr[b - 1]=df.format(Double.parseDouble(tSSRS.GetText(a, b)))   ;	}
				
			}
			tlistTable.add(strArr);
			strArr = new String[tSSRS.getMaxCol()];
		}

		tlistTableFirst.setName("Rowinfo");
		StringBuffer sqlt = new StringBuffer();
		sqlt.append(" select  (select companyname from RIComInfo e where e.Companyno=c.Incomecompanyno), decode(b.Currency, '13', 'HK','14', 'US','01','CN'),'','TOTAL','','','','','','','','','',  "
				+ "round(sum( decode(d.Arealevel, '1', decode(d.Possessscale, '0',0, c.CessionAmount/ d.Possessscale))),2) , "
				+ "round(sum(  decode(d.Arealevel, '2', decode(d.Possessscale, '0',0, c.CessionAmount/ d.Possessscale)) ),2), "
				+ "round(sum( decode(d.Arealevel, '3', decode(d.Possessscale, '0',0, c.CessionAmount/ d.Possessscale))),2) , "
				+ "sum(decode(d.Arealevel, '1', c.CessionAmount)) oneCess,sum(decode(d.Arealevel, '2', c.CessionAmount)) twoCess, sum(decode(d.Arealevel, '3', c.CessionAmount) )threeCess, "
				+ "sum(decode(d.Arealevel, '1', c.PremChang)),sum(decode(d.Arealevel, '2', c.PremChang)), sum(decode(d.Arealevel, '3', c.PremChang)), "
				+ "sum( decode(d.Arealevel, '1', c.CommChang)),sum(decode(d.Arealevel, '2', c.CommChang)),sum( decode(d.Arealevel, '3', c.CommChang)) "
				+ "from  RIPolRecordBake b,RIRecordTrace c, RIRiskDivide d "
				+ "where b.Eventno = c.Eventno  and c.Areaid=d.Areaid and c.Ripreceptno=d.Ripreceptno and c.Incomecompanyno=d.Incomecompanyno "
				+ "and b.Eventtype in ('02', '01')and b.Recordtype='01'  "
				+ "  and   b.Getdate  between To_Date('"
				+ Startdate
				+ "' , 'yyyy-mm-dd')    and   To_Date('"
				+ Enddate
				+ "' , 'yyyy-mm-dd')  "
				+ " and c.Incomecompanyno='"
				+ RIComCode
				+ "' group by  b.Currency ,c.Incomecompanyno order by b.Currency  ");

		bSSRS = aExeSQL.execSQL(sqlt.toString());
		strArr = new String[bSSRS.getMaxCol()];
		for (int a = 1; a <= bSSRS.getMaxRow(); a++) {
			for (int b = 1; b <= bSSRS.getMaxCol(); b++) {
				if (bSSRS.GetText(a, b).equals("0")
						|| bSSRS.GetText(a, b).equals("null")) {
					bSSRS.SetText(a, b, "0");
				}
				strArr[b - 1] = bSSRS.GetText(a, b);
				  if(b>=14&&bSSRS.GetText(a, b)!=null){
						strArr[b - 1]=df.format(Double.parseDouble(bSSRS.GetText(a, b)))   ;	}
			}
			tlistTableFirst.add(strArr);
			strArr = new String[bSSRS.getMaxCol()];
		}
	}
	//再保新單續期(inforce)----END
	//再保變更(Movemen) ----END
	if (ReprotType.equals("04")) {

		tTempleteID = "003121";
		tlistTable.setName("MOVEMENROW");//注意这里是循环体表格名称   
		StringBuffer sql = new StringBuffer();
		sql.append(" select (select companyname from RIComInfo e where e.Companyno=c.Incomecompanyno), decode(b.Currency, '13', 'HK','14', 'US','01','CN'), "
				+ " b.Contno, b.Currentage,decode(b.InsuredSex, '1', 'F', '0', 'M'),b.Standbystring2, b.HealthTime1, b.HealthTime2,b.OccupationType,  "
				+ "  b.Cvalidate,b.GetDate,(select edorname from lmedoritem where  edorcode= b.FeeOperationType and  appobj='I'), b.RiskCode, "
				+ " sum( decode(d.Arealevel, '1',  c.CessionAmount+ c.Amountchang)),sum(decode(d.Arealevel, '1', c.CessionAmount)) ,sum(decode(d.Arealevel, '1', c.Amountchang)),sum(decode(d.Arealevel, '1', c.PremChang)),sum( decode(d.Arealevel, '1', c.CommChang)), "
				+ " sum( decode(d.Arealevel, '2',  c.CessionAmount+ c.Amountchang)),sum(decode(d.Arealevel, '2', c.CessionAmount)) ,sum(decode(d.Arealevel, '2', c.Amountchang)),sum(decode(d.Arealevel, '2', c.PremChang)),sum( decode(d.Arealevel, '2', c.CommChang)), "
				+ " sum( decode(d.Arealevel, '3',  c.CessionAmount+ c.Amountchang)),sum(decode(d.Arealevel, '3', c.CessionAmount)) ,sum(decode(d.Arealevel, '3', c.Amountchang)),sum(decode(d.Arealevel, '3', c.PremChang)),sum( decode(d.Arealevel, '3', c.CommChang)), b.InsuredYear "
				+ " from  RIPolRecordBake b,RIRecordTrace c, RIRiskDivide d "
				+ " where b.Eventno = c.Eventno  and c.Areaid=d.Areaid and c.Ripreceptno=d.Ripreceptno and c.Incomecompanyno=d.Incomecompanyno "
				+ " and b.Eventtype in ('03')and b.Recordtype='01'  "
				+ "  and   b.Getdate  between To_Date('"
				+ Startdate
				+ "' , 'yyyy-mm-dd')    and   To_Date('"
				+ Enddate
				+ "' , 'yyyy-mm-dd')  "
				+ " and c.Incomecompanyno='"
				+ RIComCode
				+ "'  group by  b.InsuredYear,b.Contno,b.Cvalidate,b.Currentage, b.Getdate, b.InsuredSex,b.Standbystring2, b.HealthTime1,b.HealthTime2,b.OccupationType,b.RiskCode,c.Incomecompanyno,b.Currency,b.Feeoperationtype order by Cvalidate ");
		tSSRS = aExeSQL.execSQL(sql.toString());
		strArr = new String[tSSRS.getMaxCol()];
		for (int a = 1; a <= tSSRS.getMaxRow() + aSSRS.getMaxRow(); a++) {
		
				for (int b = 1; b <= tSSRS.getMaxCol(); b++) {
					if (tSSRS.GetText(a, b).equals("0")|| tSSRS.GetText(a, b).equals("null")) {
						tSSRS.SetText(a, b, "0");
					}
					strArr[b - 1] = tSSRS.GetText(a, b);
					  if(b>=14&&tSSRS.GetText(a, b)!=null){
							strArr[b - 1]=df.format(Double.parseDouble(tSSRS.GetText(a, b)))   ;	}
			}
			tlistTable.add(strArr);
			strArr = new String[tSSRS.getMaxCol()];
		}

		tlistTableFirst.setName("Rowinfo");
		StringBuffer sqlt = new StringBuffer();
		sqlt.append(" select (select companyname from RIComInfo e where e.Companyno=c.Incomecompanyno), decode(b.Currency, '13', 'HK','14', 'US','01','CN'),'TOTAL','','','','','','','','','','',   "
				+ "sum( decode(d.Arealevel, '1',  c.CessionAmount+ c.Amountchang)),sum(decode(d.Arealevel, '1', c.CessionAmount)) ,sum(decode(d.Arealevel, '1', c.Amountchang)),sum(decode(d.Arealevel, '1', c.PremChang)),sum( decode(d.Arealevel, '1', c.CommChang)),   "
				+ "sum( decode(d.Arealevel, '2',  c.CessionAmount+ c.Amountchang)),sum(decode(d.Arealevel, '2', c.CessionAmount)) ,sum(decode(d.Arealevel, '2', c.Amountchang)),sum(decode(d.Arealevel, '2', c.PremChang)),sum( decode(d.Arealevel, '2', c.CommChang)),   "
				+ "sum( decode(d.Arealevel, '3',  c.CessionAmount+ c.Amountchang)),sum(decode(d.Arealevel, '3', c.CessionAmount)) ,sum(decode(d.Arealevel, '3', c.Amountchang)),sum(decode(d.Arealevel, '3', c.PremChang)),sum( decode(d.Arealevel, '3', c.CommChang))   "
				+ "from  RIPolRecordBake b,RIRecordTrace c, RIRiskDivide d   "
				+ "where b.Eventno = c.Eventno  and c.Areaid=d.Areaid and c.Ripreceptno=d.Ripreceptno and c.Incomecompanyno=d.Incomecompanyno   "
				+ "and b.Eventtype in ('03')and b.Recordtype='01'  "
				+ "  and   b.Getdate  between To_Date('"
				+ Startdate
				+ "' , 'yyyy-mm-dd')    and   To_Date('"
				+ Enddate
				+ "' , 'yyyy-mm-dd')  "
				+ " and c.Incomecompanyno='"
				+ RIComCode
				+ "' group by b.Currency, c.Incomecompanyno order by b.Currency  ");

		bSSRS = aExeSQL.execSQL(sqlt.toString());
		strArr = new String[bSSRS.getMaxCol()];
		for (int a = 1; a <= bSSRS.getMaxRow(); a++) {
			for (int b = 1; b <= bSSRS.getMaxCol(); b++) {
				if (bSSRS.GetText(a, b).equals("0")
						|| bSSRS.GetText(a, b).equals("null")) {
					bSSRS.SetText(a, b, "0");
				}
                    
				strArr[b - 1] = bSSRS.GetText(a, b);
				 if(b>=14&&bSSRS.GetText(a, b)!=null){
						strArr[b - 1]=df.format(Double.parseDouble(bSSRS.GetText(a, b)))   ;	}
				
			}
			tlistTableFirst.add(strArr);
			strArr = new String[bSSRS.getMaxCol()];
		}
	}
	//再保變更(Movemen) ----END
	//再保(cert)----START
	if (ReprotType.equals("05")) {

		tTempleteID = "003122";
		tlistTable.setName("CERTInfo");//注意这里是循环体表格名称   
		String aasql = "  select  (select companyname from RIComInfo e where e.Companyno = c.Incomecompanyno), decode(b.Currency, '13', 'HK','14', 'US','01','CN'), "
				+ "b.Contno, b.Insuredname, b.Idno, b.Insuredbirthday, b.Currentage, decode(b.InsuredSex, '1', 'F', '0', 'M'),  b.Standbystring2, "
				+ "b.HealthTime1, b.HealthTime2, b.Cvalidate,b.OccupationType,b.Enddate, b.RiskCode,b.Amnt, b.Riskamnt,sum( c.Curentamnt-c.Cessionamount ), "
				+ "round(sum( decode(d.Arealevel, '1', decode(d.Possessscale, '0',0, c.CessionAmount/ d.Possessscale))),2) ,  sum(decode(d.Arealevel, '1', c.CessionAmount)),  "   
				+ "round(sum(  decode(d.Arealevel, '2', decode(d.Possessscale, '0',0, c.CessionAmount/ d.Possessscale)) ),2),sum(decode(d.Arealevel, '2', c.CessionAmount)), "
				+ "round(sum( decode(d.Arealevel, '3', decode(d.Possessscale, '0',0, c.CessionAmount/ d.Possessscale))),2) ,sum(decode(d.Arealevel, '3', c.CessionAmount)), "
				+ "b.Riskamnt, c.Cessionamount  "
				+ "from RIPolRecordBake b, RIRecordTrace c, RIRiskDivide d  "
				+ "where b.Eventno = c.Eventno and c.Areaid = d.Areaid and c.Ripreceptno = d.Ripreceptno  "
				+ "and c.Incomecompanyno = d.Incomecompanyno  "
				+ "and b.Reinsreflag='03' and  b.Getdate  between To_Date('"
						+ Startdate
						+ "' , 'yyyy-mm-dd')    and   To_Date('"
						+ Enddate
						+ "' , 'yyyy-mm-dd')  "
						+ " and c.Incomecompanyno='"
						+ RIComCode
				+ "' group by   c.Incomecompanyno, b.Currency,b.Contno, b.Insuredname, b.Idno, b.Insuredbirthday,b.Currentage, b.InsuredSex,   "
				+ "b.Standbystring2, b.HealthTime1, b.HealthTime2, b.Cvalidate, b.OccupationType,b.Enddate,b.RiskCode, b.Amnt,  "
				+ "b.Riskamnt, c.Cessionamount   order by Cvalidate  " ;

		tSSRS = aExeSQL.execSQL(aasql);
		strArr = new String[tSSRS.getMaxCol()];
		for (int a = 1; a <= tSSRS.getMaxRow(); a++) {
			for (int b = 1; b <= tSSRS.getMaxCol(); b++) {
				if (tSSRS.GetText(a, b).equals("0")
						|| tSSRS.GetText(a, b).equals("null")) {
					tSSRS.SetText(a, b, "0");
				}
				strArr[b - 1] = tSSRS.GetText(a, b);
				if(b>=16&&tSSRS.GetText(a, b)!=null){
					strArr[b - 1]=df.format(Double.parseDouble(tSSRS.GetText(a, b)))   ;	}
			}
			tlistTable.add(strArr);
			strArr = new String[tSSRS.getMaxCol()];
			
		}
	}
	//再保(cert)----END
	//共保新單續期(con  inforce)----START
	if (ReprotType.equals("06")) {

		tTempleteID = "003117";
		String aasql = "  SELECT b.Contno, b.IDNo, b.InsuredName, b.InsuredBirthday,b.InsuredAge,decode(b.InsuredSex, '1', 'M', '0', 'F'),b.CValiDate, b.EndDate, decode(b.Currency,'01','CN','CN'), "
				+ " b.RiskCode,b.InsuredYear,sum(b.Amnt), sum(b.Amnt), sum(b.APE), sum(b.APE), b.CSV,   '0.95'  as  Cessionrate,  nvl(sum(c.Premchang), 0) Premchang,"
				+ "  nvl(sum(c.CommChang), 0) CommChang1, "
				+ "  nvl(sum(c.FeeMoney), 0) CommChang2, "
				+ "  nvl(sum(CommChang), 0) + nvl(sum(c.FeeMoney), 0) SUMCommChang "
				+ " FROM Ripolrecordbake b, RIRecordTrace c "
				+ " where b.Eventno = c.Eventno "
				+
				//  "and b.Eventtype = c.Eventtype "+
				// " and b.Contno = c.Contno "+
				//"    and b.Insuredno=c.otherno "+
				"and b.EventType in ('01', '02') "
				+ "and b.RecordType = '02' "
				+ "	and  c.Incomecompanyno='"
				+ RIComCode
				+ "' "
				+ "  and   b.Getdate  between To_Date('"
				+ Startdate
				+ "' , 'yyyy-mm-dd')    and   To_Date('"
				+ Enddate
				+ "' , 'yyyy-mm-dd')  "
				+ "group by b.RiskCode, b.Contno,b.IDNo, b.InsuredName, b.InsuredBirthday, b.InsuredAge, b. InsuredSex, b.CValiDate,b.EndDate, b.Currency, b.InsuredYear,  b.CSV"
				+ " order by CValiDate ";
		tSSRS = aExeSQL.execSQL(aasql);

		if (tSSRS.getMaxRow() != 0) {
			tlistTable.setName("COINInfo");//注意这里是循环体表格名称   
			strArr = new String[tSSRS.getMaxCol()];
			for (int a = 1; a <= tSSRS.getMaxRow(); a++) {
				/**将所得到得符合条件得记录逐条加入到strArr中相应得位置中*/
				for (int b = 1; b <= tSSRS.getMaxCol(); b++) {
					strArr[b - 1] = tSSRS.GetText(a, b);
					if(b>=12&&tSSRS.GetText(a, b)!=null){
						strArr[b - 1]=df.format(Double.parseDouble(tSSRS.GetText(a, b)))   ;	}
				}
				tlistTable.add(strArr);
				strArr = new String[tSSRS.getMaxCol()];
			}
		}
	}
	//REI-共保變更(Coinsurance movement)
	if (ReprotType.equals("07")) {

		tTempleteID = "003119";
		tlistTable.setName("MOVEInfo");//注意这里是循环体表格名称   
		String aasql = " SELECT b.Contno, b.IDNo,  b.InsuredName, b.InsuredBirthday, b.InsuredAge, decode(b.InsuredSex, '1', 'M', '0', 'F'), b.CValiDate, b.EndDate,decode(b.Currency,'01','CN','CN'),  "
				+ "b.RiskCode, b.InsuredYear, sum(b.Amnt), sum(b.Amnt), sum(b.APE),sum(b.APE),b.CSV,nvl(sum(c.FeeMoney), 0) FeeMoney, nvl( sum(c.CurentAmnt - c.CessionAmount),0)  , b.PreRiskAmnt,  "
				+ "b.FeeOperationType,b.Extprem, b.RiskAmnt, nvl(sum(c.Premchang), 0) Premchang, nvl(sum(c.CommChang), 0) CommChang1,nvl(sum(c.FeeMoney), 0) CommChang2,nvl(sum(CommChang), 0) + nvl(sum(c.FeeMoney), 0) SUMCommChang "
				+ " FROM Ripolrecordbake b, RIRecordTrace c  where b.Eventno = c.Eventno    and b.EventType in ('03')   and RecordType = '02' "
				+
				// "  and b.Eventtype = c.Eventtype "+
				// " and b.Contno = c.Contno "+
				"	and  c.Incomecompanyno='"
				+ RIComCode
				+ "' "
				+ "  and   b.Getdate  between To_Date('"
				+ Startdate
				+ "' , 'yyyy-mm-dd')    and   To_Date('"
				+ Enddate
				+ "' , 'yyyy-mm-dd')  "
				+ " group by  b.RiskCode,b.Contno,b.IDNo,b.InsuredName,b.InsuredBirthday,b.InsuredAge,b. InsuredSex,b.CValiDate,b.EndDate,b.Currency,b.InsuredYear,b.PreRiskAmnt,b.FeeOperationType,  "
				+ " b.Extprem,b.RiskAmnt,b.CSV order by CValiDate ";
		tSSRS = aExeSQL.execSQL(aasql);
		strArr = new String[tSSRS.getMaxCol()];
		for (int a = 1; a <= tSSRS.getMaxRow(); a++) {
			/**将所得到得符合条件得记录逐条加入到strArr中相应得位置中*/
			for (int b = 1; b <= tSSRS.getMaxCol(); b++) {
				strArr[b - 1] = tSSRS.GetText(a, b);
				if(b>=12&&b!=20){
					strArr[b - 1]=df.format(Double.parseDouble(tSSRS.GetText(a, b)))   ;	}
			}
			tlistTable.add(strArr);
			strArr = new String[tSSRS.getMaxCol()];
		}
	}
	

	tTransferData.setNameAndValue("ArrayList", tArr);
	tTransferData.setNameAndValue("TempleteID", tTempleteID);
	//tTransferData.setNameAndValue("OutPath", PubFun.getOutPathByPDF()); 

	VData tVData = new VData();
	tVData.addElement(tTransferData);

	if (!tBusinessDelegate.submitData(tVData, "", tBusiName)) {
		Content = ""+"账单不存在"+"";
	} else {
		tXmlExportNew = (XmlExportNew) tBusinessDelegate.getResult()
				.get(0);
		if (texttag.size() != 0) {
			tXmlExportNew.addTextTag(texttag);
		}
		if (tlistTableFirst.getName() != null) {
			tXmlExportNew.addListTable(tlistTableFirst, strArr);
		}

		if (tlistTable.getName() != null) {
			tXmlExportNew.addListTable(tlistTable, strArr);
		}

		tInput = tXmlExportNew.getInputStream();
		request.setAttribute("PrintStream", tInput);
		request.getRequestDispatcher("../print/ShowPrintTest.jsp")
				.forward(request, response);
	}
%>
<html>

</html>
<script type="text/javascript">
     myAlert("<%=Content%>");
	top.close();
</script>