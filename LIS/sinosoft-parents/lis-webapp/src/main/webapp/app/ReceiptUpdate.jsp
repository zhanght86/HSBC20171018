<%@page import="java.text.ParseException"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>
<%
//�������ƣ�ReceiptUpdate.jsp
//�����ܣ����»���ʱ��
//�������ڣ�2016-05-05 17:41
//������  ��lzj
%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.sql.SQLException"%>
<%@page import="com.sinosoft.utility.VData"%>
<%@page import="com.sinosoft.utility.TransferData"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%
String FlagStr = "Fail";
String Content = "";
String sDate="";//�ַ�����ʽ��ǩ������
//ѡ�е�����
int selectSum=0;
String tPolGrids[] = request.getParameterValues("PolGridNo");
String tPrtNos[] = request.getParameterValues("PolGrid1");//�õ���һ�е�ֵ
String tSingDates[] = request.getParameterValues("PolGrid5"); //�õ������е�ֵ��Ҳ����ǩ������
String tChecks[] = request.getParameterValues("InpPolGridChk");
sDate=request.getParameter("ValidStartDate1");
StringBuffer tUpSql = new StringBuffer("update lccont l set l.GetPolDate=to_date('");
tUpSql.append(sDate);
tUpSql.append("','yyyy-mm-dd'),l.GetPolTime=to_char(now(),'hh24:mi:ss'),l.CustomGetPolDate=to_date('");
tUpSql.append(sDate);
tUpSql.append("','yyyy-mm-dd'),l.ModifyDate=to_date(to_char(now(),'yyyy-MM-dd'),'yyyy-MM-dd'),ModifyTime=to_char(now(),'hh24:mi:ss') where l.prtno in(");
for (int nIndex = 0; nIndex < tChecks.length; nIndex++) {
	if (tChecks[nIndex].equals("1")) { //ѡ�е�ʱ��
		++selectSum; //[���ѡ�н��ñ���+1]���ڼ����ܹ�ѡ�еļ�¼��
		tUpSql.append("'" + tPrtNos[nIndex] + "',");
	}
}
tUpSql.replace(tUpSql.length()-1, tUpSql.length(),")"); //������һ������,�滻Ϊ")"
boolean flag=false;
BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
TransferData tTransferData=new TransferData();
tTransferData.setNameAndValue("SQL", tUpSql.toString());
VData tVData = new VData();
tVData.add(tTransferData);
try{
flag=tBusinessDelegate.submitData(tVData, "execUpdateSQL", "ExeSQLUI");
}catch(Exception e){
/* 	System.out.println("aeon:"+e.toString()); */
	loggerDebug("ReceiptUpdate.jsp","aeon:"+e.toString());
	flag=false;
	selectSum=0;
	Content = "����ʧ��!";
}
if(flag){
	Content="<strong>"+selectSum+"</strong>"+"�����ݸ��³ɹ�!";
	FlagStr = "Succ";
}
%>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>