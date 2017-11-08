<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%
//程序名称：LDPersonSave.jsp
//程序功能：
//创建日期：2002-06-27 08:49:52
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
//    		zhangtao	2005-04-29  
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.vdb.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.service.*" %>
<%
  //接收信息，并作校验处理。
  //输入参数
  
  LPEdorItemSet mLPEdorItemSet =new LPEdorItemSet();
     
  TransferData tTransferData = new TransferData(); 
  String mCurrentDate = PubFun.getCurrentDate();
  //输出参数
  String FlagStr = "";
  CErrors tError = null;
  String Content = "";
  boolean fieldChecked=true;
 
  GlobalInput tGI = new GlobalInput(); //repair:
  tGI=(GlobalInput)session.getValue("GI");  //参见loginSubmit.jsp

    //后面要执行的动作：添加，修改，删除
    String fmAction    = request.getParameter("fmtransact");
    String displayType = request.getParameter("DisplayType");
    String MissionID = request.getParameter("MissionID");
    String SubMissionID     = request.getParameter("SubMissionID");
    String LoadFlag = request.getParameter("LoadFlag");
    
    tTransferData.setNameAndValue("DisplayType", displayType);
    tTransferData.setNameAndValue("MissionID", MissionID);
    tTransferData.setNameAndValue("SubMissionID", SubMissionID);
    tTransferData.setNameAndValue("LoadFlag", LoadFlag);

    if(fmAction.equals("INSERT||EDORITEM"))
    {
		if (displayType.equals("1"))
        {
        	//保单级
			LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
			tLPEdorItemSchema.setEdorAcceptNo(request.getParameter("EdorAcceptNo"));
			tLPEdorItemSchema.setDisplayType(displayType); 
			tLPEdorItemSchema.setEdorType(request.getParameter("EdorType"));
			tLPEdorItemSchema.setEdorAppDate(request.getParameter("EdorItemAppDate"));
			//tLPEdorItemSchema.setEdorValiDate(request.getParameter("EdorValiDate"));
    		tLPEdorItemSchema.setAppReason(request.getParameter("AppReason"));
    		tLPEdorItemSchema.setMakeDate(mCurrentDate);
    		
			tLPEdorItemSchema.setInsuredNo("000000");
			tLPEdorItemSchema.setPolNo("000000");
    		
			mLPEdorItemSet.add(tLPEdorItemSchema); 
		}           
		else if (displayType.equals("2"))
		{
			//客户级
			LPEdorItemSchema tLPEdorItemSchema;
            String tInsuredNo[] = request.getParameterValues("InsuredGrid2");	//被保险人号码 
            String tContNo[] = request.getParameterValues("InsuredGrid8");		//保单号码                                                                                       
            String tGrpContNo[] = request.getParameterValues("InsuredGrid9");	//集体保单号码
            
            String tChk[] = request.getParameterValues("InpInsuredGridSel"); 
            
            for(int index = 0; index < tChk.length; index++)
            {
                if(tChk[index].equals("1"))
                {
                    tLPEdorItemSchema = new LPEdorItemSchema();
                    tLPEdorItemSchema.setEdorAcceptNo(request.getParameter("EdorAcceptNo"));
                    tLPEdorItemSchema.setDisplayType(displayType); 
                    tLPEdorItemSchema.setEdorType(request.getParameter("EdorType")); 
                    tLPEdorItemSchema.setEdorAppDate(request.getParameter("EdorItemAppDate"));
                    //tLPEdorItemSchema.setEdorValiDate(request.getParameter("EdorValiDate"));
                    tLPEdorItemSchema.setAppReason(request.getParameter("AppReason"));
            		tLPEdorItemSchema.setMakeDate(mCurrentDate);
                    tLPEdorItemSchema.setContNo(tContNo[index]); 
                    tLPEdorItemSchema.setGrpContNo(tGrpContNo[index]); 
                    tLPEdorItemSchema.setInsuredNo(tInsuredNo[index]);
                    tLPEdorItemSchema.setPolNo("000000");
                    
                    mLPEdorItemSet.add(tLPEdorItemSchema);
                }           
            }            
            
         }  
         else if (displayType.equals("3"))
         {
			//险种级
			LPEdorItemSchema tLPEdorItemSchema;
            String tPolNo[] = request.getParameterValues("PolGrid2");		//险种保单号            
            String tInsuredNo[] = request.getParameterValues("PolGrid3");	//被保人号码 
            String tContNo[] = request.getParameterValues("PolGrid9");		//保单号码
            String tGrpContNo[] = request.getParameterValues("PolGrid10"); //集体保单号码
                        
			String tChk[] = request.getParameterValues("InpPolGridChk"); 
            for(int index = 0; index < tChk.length; index++)
            {
                if(tChk[index].equals("1"))
                {
                    tLPEdorItemSchema = new LPEdorItemSchema();
                    tLPEdorItemSchema.setEdorAcceptNo(request.getParameter("EdorAcceptNo"));
                    tLPEdorItemSchema.setDisplayType(displayType);  
                    tLPEdorItemSchema.setEdorType(request.getParameter("EdorType"));
                    tLPEdorItemSchema.setEdorAppDate(request.getParameter("EdorItemAppDate"));
                    //tLPEdorItemSchema.setEdorValiDate(request.getParameter("EdorValiDate"));
                    tLPEdorItemSchema.setAppReason(request.getParameter("AppReason"));
            		tLPEdorItemSchema.setMakeDate(mCurrentDate); 
                    tLPEdorItemSchema.setContNo(tContNo[index]); 
                    tLPEdorItemSchema.setGrpContNo(tGrpContNo[index]);
                    tLPEdorItemSchema.setInsuredNo(tInsuredNo[index]);
                    tLPEdorItemSchema.setPolNo(tPolNo[index]);
                    
                    mLPEdorItemSet.add(tLPEdorItemSchema);
                }           
            }              
         }            
    }       
   
	// 准备传输数据 VData
	  VData tVData = new VData();
 
	  tVData.add(mLPEdorItemSet);
    tVData.add(tTransferData);
    tVData.add(tGI);
    String busiName="PEdorAppItemUI";
    BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
    if(!tBusinessDelegate.submitData(tVData,fmAction,busiName))
    {    
        if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
        {  
				   Content = "添加失败，原因是:" + tBusinessDelegate.getCErrors().getFirstError();
				   FlagStr = "Fail";
				}
				else
				{
				   Content = "添加失败";
				   FlagStr = "Fail";				
				}		
	 
	 }
	 else
	 {
				  Content ="添加成功！";
		    	FlagStr = "Succ";	
	 }	 	


//页面有效区
%>                                       
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>", "<%=Content%>");
</script>
</html>

