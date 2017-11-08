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
    LCGrpContSchema tLCGrpContSchema = new LCGrpContSchema();
    LCInsuredSchema tmLCInsuredSchema =new LCInsuredSchema();
    LCPolSchema tLCPolSchema = new LCPolSchema();
    LCPolDB tLCPolDB = new LCPolDB();
    LCDutySet tLCDutySet = new LCDutySet();
    LCInsuredSet tLCInsuredSet = new LCInsuredSet();
    LCBnfSet tLCBnfSet = new LCBnfSet();
    LCInsuredRelatedSet tLCInsuredRelatedSet=new LCInsuredRelatedSet();
    TransferData tTransferData = new TransferData(); 
    //ParseGuideIn tParseGuideIn   = new ParseGuideIn();
    String tAction = request.getParameter("mOperate");
    //ProposalUI tProposalUI = new ProposalUI();
    BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
    LCDutySchema tLCDutySchema = new LCDutySchema();
    LCDutyDB tLCDutyDB = new LCDutyDB();
    LCBnfSchema tLCBnfSchema = new LCBnfSchema();
    LCGrpAppntSchema tLCGrpAppntSchema = new LCGrpAppntSchema();
    LCContSchema tLCContSchema = new LCContSchema();  
    LCAppntSchema tLCAppntSchema = new LCAppntSchema();    
    
    //输出参数
    String FlagStr = "";
    String Content = "";
    
    GlobalInput tGI = new GlobalInput(); //repair:
    tGI=(GlobalInput)session.getValue("GI");  //参见loginSubmit.jsp
    loggerDebug("PubAccSave","tGI"+tGI);
    if(tGI==null)
    {
        loggerDebug("PubAccSave","页面失效,请重新登陆");   
        FlagStr = "Fail";        
        Content = "页面失效,请重新登陆";  
    }
    else //页面有效
    {
        CErrors tError = null;
        String tBmCert = "";
        loggerDebug("PubAccSave","开始保存");
    
        /*        
          String tLimit="";
          String CustomerNo="";
        */ 
        int lineCount = 0;
        String arrCount[] = request.getParameterValues("PubAccGridNo");
        if (arrCount != null)
        {
    	    String tRadio[] = request.getParameterValues("InpPubAccGridSel");	//Radio选项          
            String tRiskCode[] = request.getParameterValues("PubAccGrid5");  //险种编码   
            String tPubAccName[] = request.getParameterValues("PubAccGrid3"); //公共帐户名称
            String tDutyCode[] = request.getParameterValues("PubAccGrid6"); //责任编码
            String tPrem[] = request.getParameterValues("PubAccGrid4");  //保费
            String tAmnt[] = request.getParameterValues("PubAccGrid11");  //保额
            String tPubAccFlag[] = request.getParameterValues("PubAccGrid7"); //自动应用公共帐户标记
            String tPolNo[] = request.getParameterValues("PubAccGrid12"); 
            String tInsuredNo[] = request.getParameterValues("PubAccGrid8");
            String tContNo[] = request.getParameterValues("PubAccGrid9");
            
            lineCount = arrCount.length; //行数
        
                //选择单选框被选中的数据提交
        	for(int i=0;i<lineCount;i++)
        	{
        		if(tRadio[i].equals("1"))
        		{
        		
                    loggerDebug("PubAccSave","险种编码 "+tRiskCode[i]);
                    loggerDebug("PubAccSave","公共帐户名称 "+tPubAccName[i]);
                    loggerDebug("PubAccSave","责任编码 "+tDutyCode[i]);
                    loggerDebug("PubAccSave","保费 "+tPrem[i]);
                    loggerDebug("PubAccSave","保额 "+tAmnt[i]);
                    loggerDebug("PubAccSave","自动应用公共帐户标记 "+tPubAccFlag[i]);                    
                    
                    if (tAction.equals("INSERT||PUBACC"))
                    {
                    
                        tLCGrpContSchema.setGrpContNo(request.getParameter("GrpContNo"));
                        tLCGrpContSchema.setProposalGrpContNo(request.getParameter("GrpContNo"));
                        loggerDebug("PubAccSave","集体投保单合同号 " +tLCGrpContSchema.getProposalGrpContNo());
                        tmLCInsuredSchema.setInsuredNo("");
                        tmLCInsuredSchema.setContNo("1");
                        tmLCInsuredSchema.setPrtNo("1");
                    }
                    else if (tAction.equals("UPDATE||PUBACC"))
                    {                        
                        tLCContSchema.setContNo(tContNo[i]);
                        tLCGrpAppntSchema.setGrpContNo(request.getParameter("GrpContNo"));
                        tLCGrpAppntSchema.setCustomerNo(request.getParameter("AppntNo"));
                        tLCAppntSchema.setContNo(tContNo[i]);
                        //tLCAppntSchema.setAppntNo(request.getParameter("AppntCustomerNo"));  //客户号
                        //tLCAppntSchema.setAppntName(request.getParameter("AppntName"));              //姓名
                        //tLCAppntSchema.setAppntSex(request.getParameter("AppntSex"));                //性别
                        //tLCAppntSchema.setAppntBirthday(request.getParameter("AppntBirthday"));      //出生日期
                        tmLCInsuredSchema.setInsuredNo(tInsuredNo[i]);
                        tmLCInsuredSchema.setContNo(tContNo[i]);
                        tmLCInsuredSchema.setPrtNo(tContNo[i]);
                    }
                    tmLCInsuredSchema.setName(tPubAccName[i]); //公共帐户名称
                    tmLCInsuredSchema.setRelationToMainInsured("00");
                    
                    tmLCInsuredSchema.setGrpContNo(request.getParameter("GrpContNo"));
                    //tmLCInsuredSchema.setContPlanCode(request.getParameter("ContPlanCode"));
                    //tmLCInsuredSchema.setExecuteCom(request.getParameter("ExecuteCom"));
                    tmLCInsuredSchema.setInsuredPeoples(0);
                    tmLCInsuredSchema.setCreditGrade("2"); //借用 被保人信用等级 字段保存 保单类型标记
                    tmLCInsuredSchema.setSex("0");
                    tmLCInsuredSchema.setBirthday("1979-01-01");
                    
                    tLCInsuredSet.add(tmLCInsuredSchema);
                    
                    if (tAction.equals("INSERT||PUBACC"))
                    {
                        tLCPolSchema.setInsuredNo("1");
                        tLCPolSchema.setMainPolNo(tRiskCode[i]);
                        tLCPolSchema.setContNo("1");                    
                        tLCPolSchema.setGrpContNo(request.getParameter("GrpContNo"));
                        tLCPolSchema.setManageCom(request.getParameter("ManageCom"));
                        tLCPolSchema.setInsuredPeoples(0); //被保人人数
                        tLCPolSchema.setPolTypeFlag("2"); //保单类型标记                        
                        //tLCPolSchema.setPayRuleCode("0");
                        tLCPolSchema.setRiskCode(tRiskCode[i]); 
                    }
                    if (tAction.equals("UPDATE||PUBACC"))
                    {                        
                        tLCPolDB.setPolNo(tPolNo[i]);
                        if (tLCPolDB.getInfo())
                        {
                            tLCPolSchema = tLCPolDB.getSchema();
                        }
                    } 
                    
                    tLCPolSchema.setAutoPubAccFlag(tPubAccFlag[i]); //AutoPubAccFlag
                    tLCPolSchema.setMult(1);                       //份数
                    tLCPolSchema.setPrem(tPrem[i]);                 //保费
                    tLCPolSchema.setAmnt(0);                    //保额
         	      
        	 	    
                    tLCBnfSchema.setRelationToInsured("00");
                    tLCBnfSet.add(tLCBnfSchema);
                    
                    
                    if (tAction.equals("UPDATE||PUBACC"))
                    {
                        tLCDutySchema.setPolNo(tPolNo[i]);
                    }
                    tLCDutySchema.setDutyCode(tDutyCode[i]);
                    tLCDutySet.add(tLCDutySchema);
        
                }
            }
        }        
        
        
        tTransferData.setNameAndValue("SavePolType","0"); //保全保存标记，默认为0，标识非保全  
        //tTransferData.setNameAndValue("FamilyType","0"); //家庭单标记 
        tTransferData.setNameAndValue("ContType","2"); //团单，个人单标记
        tTransferData.setNameAndValue("PolTypeFlag","2"); //无名单标记
        tTransferData.setNameAndValue("InsuredPeoples",0); //被保险人人数
        tTransferData.setNameAndValue("PubAmntFlag","N"); //公共保额标记
        loggerDebug("PubAccSave","------PubAmntFlag-----"+tTransferData.getValueByName("PubAmntFlag"));

        try
         {
            // 准备传输数据 VData
            VData tVData = new VData();
            tVData.add(tTransferData);
            tVData.add(tGI);                       
            tVData.add(tLCPolSchema);            
            if (tAction.equals("INSERT||PUBACC"))
            {
                tVData.add(tLCGrpContSchema); 
                tVData.add(tLCBnfSet);
                tVData.add(tLCDutySet);
                tVData.add(tLCInsuredSet);
            }
            else if (tAction.equals("UPDATE||PUBACC"))
            {
                tVData.add(tLCContSchema);
                tVData.add(tLCBnfSchema);
                tVData.add(tLCDutySchema);
                tVData.add(tmLCInsuredSchema);
                tVData.add(tLCGrpAppntSchema);
                tVData.add(tLCAppntSchema);
            }
            tVData.addElement(tLCInsuredRelatedSet);
            
            loggerDebug("PubAccSave","tLCPolSchema=" + tLCPolSchema.encode());
            loggerDebug("PubAccSave","tLCGrpContSchema=" + tLCGrpContSchema.encode());
            loggerDebug("PubAccSave","tLCBnfSchema=" + tLCBnfSchema.encode());
            loggerDebug("PubAccSave","tLCDutySchema=" + tLCDutySchema.encode());
            loggerDebug("PubAccSave","tLCGrpAppntSchema=" + tLCGrpAppntSchema.encode());
            loggerDebug("PubAccSave","tmLCInsuredSchema=" + tmLCInsuredSchema.encode());
            loggerDebug("PubAccSave","Start to save PubAcc **************");  
             //执行动作：insert 添加纪录，update 修改纪录，delete 删除纪录
            if (tAction.equals("INSERT||PUBACC"))
            {
            	String busiName="tbParseGuideIn";
                //if (tParseGuideIn.submitData2(tVData,""))
                if (tBusinessDelegate.submitData(tVData,"",busiName))
                {
        		    tVData.clear();
        		    tVData = tBusinessDelegate.getResult();
        		    //loggerDebug("PubAccSave","tVData-----size:"+tVData.size());
    	        }
    	    }
    	    else    	    {
    	        
            	String busiName="tbProposalUI";
            	
    	        if (tBusinessDelegate.submitData(tVData,"UPDATE||PROPOSAL",busiName))
                {
        		    tVData.clear();
        		    tVData = tBusinessDelegate.getResult();
        		    //loggerDebug("PubAccSave","tVData-----size:"+tVData.size());
    	        }
    	    }
	    }
		            
        catch(Exception ex)
        {
            Content = "保存失败，原因是:" + ex.toString();
            FlagStr = "Fail";
        }
  

        //如果在Catch中发现异常，则不从错误类中提取错误信息
        if (FlagStr=="")
        {
            if (tAction.equals("INSERT||PUBACC"))
            {
                tError = tBusinessDelegate.getCErrors();
            }
            else
            {
                tError = tBusinessDelegate.getCErrors();
            }
            if (!tError.needDealError())
            {                          
                Content ="保存成功！";
            	FlagStr = "Succ";
            }
            else                                                                           
            {
            	Content = "保存失败，原因是:" + tError.getFirstError();
            	FlagStr = "Fail";
            }
        }
        loggerDebug("PubAccSave","FlagStr:"+FlagStr+"Content:"+Content);
      
    } //页面有效区
%>                                       
<html>
<script language="javascript">
	parent.fraInterface.afterAccSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

