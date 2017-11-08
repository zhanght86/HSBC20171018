<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：UnderwriteCho.jsp
//程序功能：个人核保信息查询
//创建日期：2006-09-20 11:32
//创建人  ：Fuqx
//更新记录：  更新人    更新日期     更新原因/内容
%> 
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.service.*" %>
<%
//1-得到所有纪录，显示纪录值
  int index=0;
  int TempCount=0;
  //输出参数
  CErrors tError = null;
  String FlagStr = "Fail";
  String Content = "";
  
  //得到radio列的数组
  loggerDebug("QueryUnderwriteCho","---4----");  
  
  String tRadio[] = request.getParameterValues("InpPolAddGridSel");  
  String tTempClassNum[] = request.getParameterValues("PolAddGridNo");
  String tPolCode[] = request.getParameterValues("PolAddGrid1");
  String tProposalCode[] = request.getParameterValues("PolAddGrid2");
  
  //得到check列的数组
  //String tChk[] = request.getParameterValues("PolAddGridChk");  
  
  int temp = tRadio.length;
  loggerDebug("QueryUnderwriteCho","radiolength:"+temp);
  
  //保单表 
  LCPolSchema mLCPolSchema = new LCPolSchema();
  LCUWMasterSchema mLCUWMasterSchema = new LCUWMasterSchema();
  if(tTempClassNum!=null) //如果不是空纪录	
  {
  	TempCount = tTempClassNum.length; //记录数      
   	loggerDebug("QueryUnderwriteCho","Start query Count="+TempCount);   
  	while(index<TempCount)
  	{
  		loggerDebug("QueryUnderwriteCho","----3-----");
  		loggerDebug("QueryUnderwriteCho","polcode:"+tPolCode[index]);
  		loggerDebug("QueryUnderwriteCho","radio:"+tRadio[index]);
  		if (!tPolCode[index].equals("")&&tRadio[index].equals("1"))
  		{
  			loggerDebug("QueryUnderwriteCho","GridNO="+tTempClassNum[index]);
  			loggerDebug("QueryUnderwriteCho","Grid 1="+tPolCode[index]);
  			loggerDebug("QueryUnderwriteCho","Grid 2="+tProposalCode[index]);
  			loggerDebug("QueryUnderwriteCho","Radio:"+tRadio[index]);
  			
  			//if(tRadio[index].equals("1"))
    			loggerDebug("QueryUnderwriteCho","this radio is selected");
    			
    			//查询并显示单条信息
    			LCPolSchema tLCPolSchema = new LCPolSchema();
    			
    			tLCPolSchema.setPolNo(tPolCode[index]);
    			
    			// 准备传输数据 VData
			VData tVData = new VData();
			VData tVData2 = new VData();
			tVData.addElement(tLCPolSchema);

			// 数据传输
  			//ProposalQueryUI tProposalQueryUI   = new ProposalQueryUI();
  			//LCUWMasterQueryUI tLCUWMasterQueryUI = new LCUWMasterQueryUI();
  			
  			String busiName="tbProposalQueryUI";
  		    BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
			
  		  String busiName2="cbcheckLCUWMasterQueryUI";
		   BusinessDelegate tBusinessDelegate2=BusinessDelegate.getBusinessDelegate();
  		    
			if (!tBusinessDelegate.submitData(tVData,"QUERY||MAIN||V_LCPol_LBPol",busiName))
			{
      				Content = " 查询失败，原因是: " + tBusinessDelegate.getCErrors().getError(0).errorMessage;
      				FlagStr = "Fail";
			}
			else
			 if (!tBusinessDelegate2.submitData(tVData,"QUERY||MAIN||V_LCPol_LBPol",busiName2))
			 {
      			Content = " 查询失败，原因是: " + tBusinessDelegate2.getCErrors().getError(0).errorMessage;
      			FlagStr = "Fail";
      			loggerDebug("QueryUnderwriteCho","查询失败LCUWMasterQueryUI");
			 }
			else
			{
				tVData.clear();
				tVData = tBusinessDelegate.getResult();
		        tVData2 = tBusinessDelegate2.getResult();
				// 显示
				LCPolSet mLCPolSet = new LCPolSet(); 
				LCUWMasterSet mLCUWMasterSet = new LCUWMasterSet();
				mLCPolSet.set((LCPolSet)tVData.getObjectByObjectName("LCPolSet",0));
				mLCUWMasterSet.set((LCUWMasterSet)tVData2.getObjectByObjectName("LCUWMasterSet",0));
				
				if (mLCPolSet.size() == 1)
				{
					mLCPolSchema = mLCPolSet.get(1);
					loggerDebug("QueryUnderwriteCho","---LCPolSchema ok ---");
				}
				loggerDebug("QueryUnderwriteCho","---LCUWMasterSet.size()---"+mLCUWMasterSet.size());
				if (mLCUWMasterSet.size() == 1)
				{
					mLCUWMasterSchema = mLCUWMasterSet.get(1);
					loggerDebug("QueryUnderwriteCho","---LCUWMasterSchema ok---");
				}
				loggerDebug("QueryUnderwriteCho","proposalno:"+mLCPolSchema.getProposalNo().trim());
				loggerDebug("QueryUnderwriteCho","mainpolno:"+mLCPolSchema.getMainPolNo());
				
				if(mLCPolSchema.getPolNo().equals(mLCPolSchema.getMainPolNo()))
				{
%>
<html>
	<head>
				<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
				<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
				<SCRIPT src="QueryUnderwrite.js"></SCRIPT>
	</head>
	<script language="javascript">
				showAddButton();
	</script>
</html>			
<%
				}
				else
				{
%>
<html>
	<head>
				<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
				<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
				<SCRIPT src="QueryUnderwrite.js"></SCRIPT>
	</head>
	<script language="javascript">
  				hideAddButton();
  	</script>
</html>				
<%
				}									
%>
			<script language="javascript">
				parent.fraInterface.fm.ProposalNo.value="<%=mLCPolSchema.getProposalNo()%>";
				parent.fraInterface.fm.PolNo.value="<%=mLCPolSchema.getPolNo()%>";
          		parent.fraInterface.fm.RiskCode.value="<%=mLCPolSchema.getRiskCode()%>";
         	 	parent.fraInterface.fm.RiskVersion.value="<%=mLCPolSchema.getRiskVersion()%>";
                parent.fraInterface.fm.ManageCom.value="<%=mLCPolSchema.getManageCom()%>";
                parent.fraInterface.fm.AppntNo.value="<%=mLCPolSchema.getAppntNo()%>";
                parent.fraInterface.fm.AppntName.value="<%=mLCPolSchema.getAppntName()%>";
                parent.fraInterface.fm.InsuredNo.value="<%=mLCPolSchema.getInsuredNo()%>";
                parent.fraInterface.fm.InsuredName.value="<%=mLCPolSchema.getInsuredName()%>";
                parent.fraInterface.fm.InsuredSex.value="<%=mLCPolSchema.getInsuredSex()%>";
                parent.fraInterface.fm.Mult.value="<%=mLCPolSchema.getMult()%>";
                parent.fraInterface.fm.Prem.value="<%=mLCPolSchema.getPrem()%>";
                parent.fraInterface.fm.Amnt.value="<%=mLCPolSchema.getAmnt()%>";
                parent.fraInterface.fm.PrtNoHide.value="<%=mLCPolSchema.getPrtNo()%>";
                parent.fraInterface.fm.MainPolNoHide.value="<%=mLCPolSchema.getMainPolNo()%>";
               
            </script>
         
<%
			} // end of if
  			loggerDebug("QueryUnderwriteCho","---2---");
			//如果在Catch中发现异常，则不从错误类中提取错误信息
			if (FlagStr == "Fail")
			{
				tError = tBusinessDelegate.getCErrors();
				if (!tError.needDealError())
				{                          
			    		Content = " 查询成功! ";
			    		FlagStr = "Succ";
				}
			 	else                                                                           
 			 	{
 			   		Content = " 查询失败，原因是:" + tError.getFirstError();
  			  		FlagStr = "Fail";
  			 	}
 			}
    			
    			if(tRadio[index].equals("1"))
      				loggerDebug("QueryUnderwriteCho","the "+index+" line is checked!");
    			else
      				loggerDebug("QueryUnderwriteCho","the "+index+" line is not checked!");
      		}
    		
    		index=index+1;
    		loggerDebug("QueryUnderwriteCho","index:"+index);          
	}
}
   
%>  
