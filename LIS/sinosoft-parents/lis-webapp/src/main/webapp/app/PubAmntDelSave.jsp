<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.cbcheck.*"%>
<%@page import="java.io.*"%>
<%@page import="java.util.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.f1print.*"%>
<%@page import="com.sinosoft.lis.tb.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.service.*" %>
<%
//输入参数
LCContSchema tLCContSchema = new LCContSchema();
TransferData tTransferData = new TransferData(); 
//ContDeleteUI tContDeleteUI = new ContDeleteUI();
String busiName="tbContDeleteUI";
BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
//输出参数
String FlagStr = "";
String Content = "";

//获得session中的人员
GlobalInput tG = new GlobalInput();
tG=(GlobalInput)session.getValue("GI");
if(tG==null)
{
    loggerDebug("PubAmntDelSave","页面失效,请重新登陆");   
    FlagStr = "Fail";        
    Content = "页面失效,请重新登陆";  
}
else //页面有效
{
    CErrors tError = null;
    loggerDebug("PubAmntDelSave","Start delete PubAmnt ......");

    //获得mutline中的数据信息
    int nIndex = 0;
    int lineCount = 0;
    String arrCount[] = request.getParameterValues("PubAmntGridNo");
    if (arrCount != null)
    {
        String tRadio[] = request.getParameterValues("InpPubAmntGridSel");  //Radio选项
        String tContNo[] = request.getParameterValues("PubAmntGrid9");      //合同号
        
        lineCount = arrCount.length; //行数

    	//选择单选框被选中的数据提交
    	for(int i=0;i<lineCount;i++)
    	{
    		if(tRadio[i].equals("1"))
    		{
    
    			loggerDebug("PubAmntDelSave","ContNo: "+tContNo[i]);
    			
    			LCContDB tLCContDB = new LCContDB();
    			tLCContDB.setContNo(tContNo[i]);
    			tLCContSchema = tLCContDB.query().get(1);			
    		}
    	}
    }

    try
    {
		//操作对象及容器
		VData vData = new VData();

		vData.addElement(tG);
		vData.addElement(tLCContSchema);
		
		//执行动作
        loggerDebug("PubAmntDelSave","Start to save ContDeleteUI **************");	

   	    if(!tBusinessDelegate.submitData(vData,"DELETE",busiName))
		{       
			loggerDebug("PubAmntDelSave","删除失败！");
			FlagStr = "Fail";
			Content=tBusinessDelegate.getCErrors().getFirstError().toString();                 
		}
		//如果没有失败，则返回删除成功
		if (!FlagStr.equals("Fail"))
		{
			loggerDebug("PubAmntDelSave","删除成功!");
			Content = "个单删除成功! ";
			FlagStr = "Succ";
		}
  	}

    catch(Exception ex)
    {
        Content = "删除失败，原因是:" + ex.toString();
        FlagStr = "Fail";
    }
}
%>                                       

<html>
<script language="javascript">

parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");

</script>
</html>
