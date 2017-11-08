
<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：LLUWSpecSave.jsp
//程序功能：二核特约承保
//创建日期：2005-11-04 
//创建人  ：万泽辉
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
  <%@page import="com.sinosoft.lis.claimnb.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.workflowengine.*"%>
  <%@page import="com.sinosoft.service.*" %>
<%
  //输出参数
  CErrors tError = null;
  String FlagStr = "Fail";
  String Content = "";
  String tOperate = "";
  String tSerialNo = "";
  boolean flag = true;
  GlobalInput tG = new GlobalInput();  
  tG=(GlobalInput)session.getValue("GI");  
  if(tG == null) {
		out.println("session has expired");
		return;
    } 
   
    //接收信息
  	LCUWMasterSchema tLCUWMasterSchema         = new LCUWMasterSchema();
  	LLUWSpecMasterSchema tLLUWSpecMasterSchema = new LLUWSpecMasterSchema();
  	LLUWSpecSubSchema tLLUWSpecSubSchema       = new LLUWSpecSubSchema();
  	TransferData tTransferData                 = new TransferData();
  
      String tContNo         = request.getParameter("ContNo");
	  String tRemark         = request.getParameter("Remark");
	  String tSpecReason     = request.getParameter("SpecReason");
	  String tPrtNo          = request.getParameter("PrtNo");
	  String tMissionID      = request.getParameter("MissionID");
	  String tSubMissionID   = request.getParameter("SubMissionID");
	  String tBaNo           = request.getParameter("BatNo");
	  String tPolNo          = request.getParameter("PolNo");
	  String tClmNo          = request.getParameter("ClmNo");
	  String tProposalNo     = request.getParameter("ProposalNo");
	  tSerialNo       = request.getParameter("SerialNo");
	  tOperate               = request.getParameter("hideOperate");
	  if(tSerialNo.trim().equals("")||tSerialNo == ""||tSerialNo == null)
	  {
	      tSerialNo = "";
	  }
	  if (tContNo == "" || (tRemark == "" ) )
	  {
		    Content = "请录入续保特别约定信息或续保备注信息!";
		    FlagStr = "Fail";
		    flag = false;
	  }
	  else
	  {     
	      if(tContNo != null && tPrtNo != null && tMissionID != null && tSubMissionID != null)
	      {
		        //准备特约信息
		   	    tLLUWSpecMasterSchema.setContNo(tContNo); 
		        tLLUWSpecMasterSchema.setProposalContNo(tProposalNo); 
                tLLUWSpecMasterSchema.setSpecContent(tRemark);
                tLLUWSpecMasterSchema.setSerialNo(tSerialNo);
                tLLUWSpecMasterSchema.setSpecType("1");
		   	    tLLUWSpecMasterSchema.setSpecCode("1");
		        //准备特约原因
		        tLCUWMasterSchema.setProposalNo(tProposalNo);
		        tLCUWMasterSchema.setSpecReason(tSpecReason);
		        
		        //同时写入理赔核保轨迹信息表
		        tLLUWSpecSubSchema.setContNo(tContNo);
		        tLLUWSpecSubSchema.setProposalContNo(tProposalNo);
		        tLLUWSpecSubSchema.setSpecType("1");
		        tLLUWSpecSubSchema.setSpecCode("1");
		        tLLUWSpecSubSchema.setBatNo(tBaNo);
				   	
                
		   } // End of if
		  else
		  {
			     Content = "传输数据失败!";
			     flag = false;
		  }
	}
try
{
  	if (flag == true)
  	{
		//准备公共传输信息
        tTransferData.setNameAndValue("ClmNo",tClmNo);
        tTransferData.setNameAndValue("ContNo",tContNo);
        tTransferData.setNameAndValue("PolNo",tPolNo);
        tTransferData.setNameAndValue("ProposalNo",tProposalNo);
        tTransferData.setNameAndValue("SpecContent",tRemark);
        tTransferData.setNameAndValue("SerialNo",tSerialNo);
        tTransferData.setNameAndValue("BatNo",tBaNo);
        tTransferData.setNameAndValue("Operatetype",tOperate);
        // 准备传输数据 VData
		VData tVData = new VData();
		tVData.add( tLLUWSpecMasterSchema );
		tVData.add( tLCUWMasterSchema );
		tVData.add( tLLUWSpecSubSchema );
		tVData.add( tTransferData);
		tVData.add( tG );

		// 数据传输
//		LLUWSpecUI tLLUWSpecUI   = new LLUWSpecUI();
//		if (!tLLUWSpecUI.submitData(tVData,tOperate))
//		{     
//		        
//			int n = tLLUWSpecUI.mErrors.getErrorCount();
//			Content = " 核保特约失败，原因是: " + tLLUWSpecUI.mErrors.getError(0).errorMessage;
//			FlagStr = "Fail";
//		}
		String busiName="LLUWSpecUI";
		 BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		   if(!tBusinessDelegate.submitData(tVData,tOperate,busiName))
		   {    
		        if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
		        { 
						   Content = "核保特约失败，原因是:" + tBusinessDelegate.getCErrors().getError(0).errorMessage;
						   FlagStr = "Fail";
				}
				else
				{
						   Content = "核保特约失败";
						   FlagStr = "Fail";				
				}
		   }

		//如果在Catch中发现异常，则不从错误类中提取错误信息
		if (FlagStr == "Fail")
		{
		    //tError = tLLUWSpecUI.mErrors;
		    tError = tBusinessDelegate.getCErrors();
		    if (!tError.needDealError())
		    {                          
		    	Content = " 理赔核保特约成功! ";
		    	FlagStr = "Succ";
		    }
		    else                                                                           
		    {
		    	FlagStr = "Fail";
		    }
		}
	} 
}
catch(Exception e)
{
	e.printStackTrace();
	Content = Content.trim()+"提示：异常终止!";
}
%>                    
                 
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

