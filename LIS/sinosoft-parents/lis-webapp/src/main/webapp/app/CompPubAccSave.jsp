<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//程序名称：PubAccSave.jsp
//程序功能：
//创建日期：2005-10-10 20:05:52
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
//      
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.vdb.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.service.*" %>
<%
  //接收信息，并作校验处理。
  //输入参数
  LCPolSchema tLCPolSchema = new LCPolSchema();
  TransferData tTransferData = new TransferData(); 
  //PreComputAccBala tPreComputAccBala = new PreComputAccBala();
  String busiName="tbPreComputAccBala";
  BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  //输出参数
  String FlagStr = "";
  String Content = "";
  String SumAccBala = "";
   
  GlobalInput tGI = new GlobalInput(); //repair:
  tGI=(GlobalInput)session.getValue("GI");  //参见loginSubmit.jsp
  loggerDebug("CompPubAccSave","tGI"+tGI);
  if(tGI==null)
  {
    loggerDebug("CompPubAccSave","页面失效,请重新登陆");   
    FlagStr = "Fail";        
    Content = "页面失效,请重新登陆";  
  }
  else //页面有效
  {
  CErrors tError = null;
  String tBmCert = "";
  loggerDebug("CompPubAccSave","开始保存");

  
int lineCount = 0;
String arrCount[] = request.getParameterValues("PubAccGridNo");
if (arrCount != null){
	String tRadio[] = request.getParameterValues("InpPubAccGridSel");	//Radio选项       
        String tPolNo[] = request.getParameterValues("PubAccGrid12");           //个人保单号  
                
        lineCount = arrCount.length; //行数

	//选择单选框被选中的数据提交
	for(int i=0;i<lineCount;i++)
	{
		if(tRadio[i].equals("1"))
		{
		    loggerDebug("CompPubAccSave","个人投保单号 "+tPolNo[i]);

                    LCPolDB tLCPolDB = new LCPolDB();
                    tLCPolDB.setPolNo(tPolNo[i]);
                    tLCPolSchema = tLCPolDB.query().get(1);
                    //loggerDebug("CompPubAccSave","个人保单信息："+tLCPolSchema.encode());

                }
         }
}


        try
         {
            // 准备传输数据 VData
            VData tVData = new VData();
            tVData.add(tGI);
            tVData.add(tLCPolSchema);

             //执行动作
            loggerDebug("CompPubAccSave","Start to save PreComputAccBala **************");
            if ( tBusinessDelegate.submitData(tVData,"",busiName))
            {
        		/*    	
        		tVData.clear();
        		tVData = tPreComputAccBala.getResult();
        		loggerDebug("CompPubAccSave","tVData-----size:"+tVData.size());
        		*/
        	    //SumAccBala = tBusinessDelegate.getResult();
        		VData ttVData = tBusinessDelegate.getResult();
        		if(null!=ttVData&&ttVData.size()>0)
        		{
        			SumAccBala=(String)ttVData.getObject(0);
        		}
        		loggerDebug("CompPubAccSave","SumAccBala:" +SumAccBala);	    		            
    	   }
	 }
		            
    catch(Exception ex)
    {
      Content = "计算失败，原因是:" + ex.toString();
      FlagStr = "Fail";
    }
  

  //如果在Catch中发现异常，则不从错误类中提取错误信息
  if (FlagStr=="")
  {
    tError = tBusinessDelegate.getCErrors();
    if (!tError.needDealError())
    {                          
      Content ="计算成功！";
    	FlagStr = "Succ";
    }
    else                                                                           
    {
    	Content = "计算失败，原因是:" + tError.getFirstError();
    	FlagStr = "Fail";
    }
  }
  loggerDebug("CompPubAccSave","FlagStr:"+FlagStr+"Content:"+Content);
  
} //页面有效区
%>                                       
<html>
<script language="javascript">
	parent.fraInterface.afterCompAcc("<%=FlagStr%>","<%=Content%>","<%=SumAccBala%>");
</script>
</html>

