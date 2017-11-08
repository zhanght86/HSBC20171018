<%@ page contentType="text/html; charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@ page import = "java.util.*"%>
<%@ page import = "com.sinosoft.utility.*"%>
<%@ page import = "com.sinosoft.lis.vschema.*"%>
<%@ page import = "com.sinosoft.lis.schema.*"%>
<%@ page import = "com.sinosoft.lis.pubfun.*"%>
<%@ page import = "com.sinosoft.lis.db.*"%>
<%@ page import = "com.sinosoft.lis.tbgrp.*"%>

<%
String tGrpContno=request.getParameter("GrpContNo");
LCGrpContDB tLCGrpContDB=new LCGrpContDB();
LCGrpContSet tLCGrpContSet=new LCGrpContSet();
tLCGrpContDB.setGrpContNo(tGrpContno);
tLCGrpContSet=tLCGrpContDB.query();
LCGrpContSchema tLCGrpContSchema=tLCGrpContSet.get(1);
BasicFeeCalBL tBasicFeeCalBL=new BasicFeeCalBL();
double outfeepay=0;
double infeepay=0;
outfeepay=tBasicFeeCalBL.VerifyOutPayMoney(tLCGrpContSchema);
loggerDebug("RealFeeInp","outfeepay:"+outfeepay);
infeepay=tBasicFeeCalBL.VerifyInerPayMoney(tLCGrpContSchema);
loggerDebug("RealFeeInp","infeepay:"+infeepay);
//************************************************************
String outpayflag="0";
String FeeCalMode="";
String FeeName="";
double tempValue=0;
String tsql;
String tRiskCode="";
//*******************
loggerDebug("RealFeeInp",tGrpContno);
//**********************************
LCGrpFeeDB tLCGrpFeeDB = new LCGrpFeeDB();
LCGrpFeeSet tLCGrpFeeSet = new LCGrpFeeSet();
//��������д����139����151 ������ͬһ���󱣵��£��мǡ�����Ժ��������������ǵ�����ط�Ҫ�޸�--����
tsql ="select * From lcgrpfee where feecode in (select substr(riskcode,2,3)||'0002' from lmriskapp where risktype6='1' ) and grpcontno='" +tGrpContno+ "'";
loggerDebug("RealFeeInp",tsql);
tLCGrpFeeSet = tLCGrpFeeDB.executeQuery(tsql);
if (tLCGrpFeeSet != null && tLCGrpFeeSet.size() > 0) 
{
FeeCalMode = tLCGrpFeeSet.get(1).getFeeCalMode();
tempValue=tLCGrpFeeSet.get(1).getFeeValue(); //����ڿ۵Ĵ���
loggerDebug("RealFeeInp",FeeCalMode);
if (FeeCalMode.equals("03") || FeeCalMode.equals("04") || FeeCalMode.equals("07"))
    {
      outpayflag = "1"; //��ɱ��
      tRiskCode=tLCGrpFeeSet.get(1).getRiskCode();
    }
}else{
   FeeCalMode="07";
   outpayflag = "1";
   tRiskCode="000";
}
//**********************************
if(FeeCalMode.equals("01"))
{
 FeeName="�̶�ֵ���ڿۣ�";
}else if(FeeCalMode.equals("02")){
 FeeName="�̶����� ���ڿۣ�";
}else if(FeeCalMode.equals("03")){
 FeeName="�̶�ֵ����ɣ�";
}else if(FeeCalMode.equals("04")){
 FeeName="�̶����� ����ɣ�";
}else if(FeeCalMode.equals("05")){
 FeeName="Min���̶�ֵ�������ϣ�";
}else if(FeeCalMode.equals("06")){
 FeeName="Max���̶�ֵ�ͱ�����ϣ�";
}else if(FeeCalMode.equals("07")){
 FeeName="�ֵ�����";
}
//**********************************
double sumPrem=0;
if(outpayflag.equals("1"))
{
ManageFeeCalBL tManageFeeCalBL = new ManageFeeCalBL();
if (!tManageFeeCalBL.submitData(tGrpContno, tRiskCode, FeeCalMode, "1", "000000",tRiskCode.substring(1, 3)+"0002"))
 {
    loggerDebug("RealFeeInp","����ʧ�ܣ�");                      
 }else{
 sumPrem=tManageFeeCalBL.managefee;
 }
}else{
 if(FeeCalMode.equals("01"))
 {
 sumPrem=tempValue;
 }else if(FeeCalMode.equals("02"))
 {
   ExeSQL tExeSQL=new ExeSQL();
   String msql="select sum(prem) from lcpol where riskcode in (select riskcode from lmriskapp where risktype6='1') and  grpcontno='"+tGrpContno+"' group by grpcontno";
   SSRS tSSRS=new SSRS();
   tSSRS=tExeSQL.execSQL(msql);
   if(tSSRS.MaxRow>0)
   {
     sumPrem=Float.parseFloat(tSSRS.GetText(1,1))*tempValue;
   }
 }
}
//*****************************
if(outpayflag.equals("1"))
{
outfeepay=outfeepay+sumPrem;
}else{
infeepay=infeepay+sumPrem;
}
%>
<HTML>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
</head>
<body leftmargin="0" topmargin="0">

<form id="frmLIST2" action="" target="load" method="post">
<table width="100%" cellpadding="0" cellspacing="0" border="1">

<tr>
	<td class="title" nowrap><font size=2><b>��ɹ���� &nbsp</b></font></td>
	<td class="common" nowrap><%=outfeepay%></td>

</tr>
<tr>
	<td class="title" nowrap><font size=2><b>�ڿ۹���� &nbsp</b></font></td>
	<td class="common" nowrap><%=infeepay%></td>

</tr>
<table>
</form>
</BODY>
</HTML>

