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


//获得mutline中的数据信息
int nIndex = 0;
String tLCContGrids[] = request.getParameterValues("GrpGridNo");
String tLCContNos[] = request.getParameterValues("GrpGrid1");
String tChecks[] = request.getParameterValues("InpGrpGridChk");

//获得session中的人员喜讯你
GlobalInput tG = new GlobalInput();
tG=(GlobalInput)session.getValue("GI");

//操作对象及容器
LCContSet   tLCContSet = new LCContSet();
LCContSchema tLCContSchema = new LCContSchema();
LCContDelPUI tLCContDelPUI = new LCContDelPUI();
VData vData = new VData();
loggerDebug("InGrpContDeleteDisposal",tLCContGrids.length);  //------------------------
//循环打印选中的团单
for(nIndex = 0; nIndex < tLCContGrids.length; nIndex++ )
{
	//If this line isn't selected, continue，如果没有选中当前行，则继续
	if( !tChecks[nIndex].equals("1") )
	{
		continue;
	}
	loggerDebug("InGrpContDeleteDisposal",nIndex);    //---------------------
	loggerDebug("InGrpContDeleteDisposal",tLCContNos[nIndex]);  //--------------------
	//将数据放入合同保单集合
	tLCContSchema = new LCContSchema();
	tLCContSchema.setContNo(tLCContNos[nIndex]);
	tLCContSet.add(tLCContSchema);
	//将数据集合放入容器中，准备传入后台处理


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
	

//如果没有失败，则返回删除成功
if (!FlagStr.equals("Fail"))
{
	Content = "个单删除成功! ";
	FlagStr = "Succ";
}

loggerDebug("InGrpContDeleteDisposal","ok");
%>

<html>
<script language="javascript">

parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");

</script>
</html>
