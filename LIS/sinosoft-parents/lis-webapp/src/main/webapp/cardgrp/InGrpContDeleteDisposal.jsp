<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.cbcheckgrp.*"%>
<%@page import="java.io.*"%>
<%@page import="java.util.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.f1printgrp.*"%>
<%@page import="com.sinosoft.lis.cardgrp.*"%>

<%
String FlagStr = "";
String Content = "";


//���mutline�е�������Ϣ
int nIndex = 0;
String tLCContGrids[] = request.getParameterValues("GrpGridNo");
String tLCContNos[] = request.getParameterValues("GrpGrid1");
String tChecks[] = request.getParameterValues("InpGrpGridChk");

//���session�е���ԱϲѶ��
GlobalInput tG = new GlobalInput();
tG=(GlobalInput)session.getValue("GI");

//������������
LCContSet   tLCContSet = new LCContSet();
LCContSchema tLCContSchema = new LCContSchema();
LCContDelPUI tLCContDelPUI = new LCContDelPUI();
VData vData = new VData();
loggerDebug("InGrpContDeleteDisposal",tLCContGrids.length);  //------------------------
//ѭ����ӡѡ�е��ŵ�
for(nIndex = 0; nIndex < tLCContGrids.length; nIndex++ )
{
	//If this line isn't selected, continue�����û��ѡ�е�ǰ�У������
	if( !tChecks[nIndex].equals("1") )
	{
		continue;
	}
	loggerDebug("InGrpContDeleteDisposal",nIndex);    //---------------------
	loggerDebug("InGrpContDeleteDisposal",tLCContNos[nIndex]);  //--------------------
	//�����ݷ����ͬ��������
	tLCContSchema = new LCContSchema();
	tLCContSchema.setContNo(tLCContNos[nIndex]);
	tLCContSet.add(tLCContSchema);
	//�����ݼ��Ϸ��������У�׼�������̨����


}	
 
  int i = tLCContSet.size();   //--------------------------
  loggerDebug("InGrpContDeleteDisposal",i);       //---------------------------
  

  vData.addElement(tLCContSet);
  vData.addElement(tG);
	
	if(!tLCContDelPUI.submitData(vData,"DELETE"))
  {    loggerDebug("InGrpContDeleteDisposal","notok");          //----------------
       FlagStr = "Fail";
       Content=tLCContDelPUI.mErrors.getFirstError().toString();                 
  }
	

//���û��ʧ�ܣ��򷵻�ɾ���ɹ�
if (!FlagStr.equals("Fail"))
{
	Content = "����ɾ���ɹ�! ";
	FlagStr = "Succ";
}

loggerDebug("InGrpContDeleteDisposal","ok");
%>

<html>
<script language="javascript">

parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");

</script>
</html>
