
<!--用户校验类-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.fininterface.FiDistillForCertMain"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  


<% 
  String Content = "";
  String FlagStr = "";
  VData tVData  = new VData();
 
 
  String BatchNo = "";

  GlobalInput tG = new GlobalInput();
  tG=(GlobalInput)session.getValue("GI");
  loggerDebug("FIDistillCertificateSave","开始执行Save页面");
  FiDistillForCertMain tFiDistillForCertMain = new FiDistillForCertMain(); 	
  int nIndex = 0;

  String tResultGrids[] = request.getParameterValues("InpResultGridSel");
  String tBatchNo[] = request.getParameterValues("ResultGrid1");
  for(nIndex=0; nIndex< tResultGrids.length;nIndex++)
  {
     if(tResultGrids[nIndex].equals("1"))
     {
         BatchNo = tBatchNo[nIndex];
     }
  }
  
  //兼容中科软财务系统临时代码，其他项目实施可以去掉
  String tSql = "update FIAboriginalData  set TypeFlag06 = (select b.codealias from Ficodetrans b where b.codetype = 'RiskTrans' and b.code =  StringInfo05 ) where StringInfo05 is not null and BatchNo = '" + BatchNo + "'";
  ExeSQL tExeSQL = new ExeSQL();
  tExeSQL.execUpdateSQL(tSql); 
  //中科软财务系统临时代码，其他项目实施可以去掉

  
  tVData.add(tG);
  tVData.add(BatchNo);
  try
  {
     if(!tFiDistillForCertMain.dealProcess(tVData))
     {
        Content = "失败，原因是:" +tFiDistillForCertMain.mErrors.getFirstError();
        FlagStr = "Fail";     
     }    
  }
  catch(Exception ex)
  {
    Content = "执行异常，原因是:" + ex.toString();
    FlagStr = "Fail";
  }

  if (FlagStr=="")
  {
    Content = "凭证数据转换成功";
    FlagStr = "Succ";
 
  }
%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
