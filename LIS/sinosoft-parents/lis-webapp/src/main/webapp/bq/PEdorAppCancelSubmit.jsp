<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%
//程序名称：AllPBqQuerySubmit.jsp
//程序功能：保全撤销处理

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
  LPEdorAppSchema tLPEdorAppSchema = new LPEdorAppSchema();
 
  BusinessDelegate tBusinessDelegate;
  ExeSQL tExeSQL = new ExeSQL();
  //输出参数
  String FlagStr = "";
  String Content = "";
  GlobalInput tGI = new GlobalInput();
  tGI=(GlobalInput)session.getValue("GI");
  String Operator  = tGI.Operator ;  //保存登陆管理员账号
  String ManageCom = tGI.ComCode  ; //保存登陆区站,ManageCom内存中登陆区站代码
  CErrors tError = null;
  SSRS BQMissionData = null;
  //后面要执行的动作：添加，修改，删除
  String transact = request.getParameter("Transact");
  String DelFlag =  request.getParameter("DelFlag");
  System.out.println("**********删除的标志是："+DelFlag);
  String tEdorAcceptNo=request.getParameter("EdorAcceptNo");
  //其他原因内容
	String CancelReasonContent = request.getParameter("CancelReasonContent");
	//原因编号
	String 	SCanclReason = request.getParameter("CancelReasonCode");
	TransferData tTransferData = new TransferData();
	VData tVData = new VData();
	tTransferData.setNameAndValue("CancelReasonContent", CancelReasonContent);
	tTransferData.setNameAndValue("SCanclReason", SCanclReason);
	//add by sunsx 团体保全撤销时内部用的撤销原因和代码和个险不一样。
	tTransferData.setNameAndValue("DelReason", CancelReasonContent);
	tTransferData.setNameAndValue("ReasonCode", SCanclReason);
	String xEdorAcceptNo="";
  //LB0001 保全撤销与银行代收的组间并发控制 
	try
	{
	    //add by xiongzh 增加客户层保全撤销处理
	    //判断是否是客户层保全
	    boolean BFControl= true;
	    String mCustomerBQFlag = "0"; //0-非客户层；1-客户层保全
		
		
      String sql1 ="select EdorAcceptNo from lpedorapp where othernotype='1' and EdorAcceptNo ='"+tEdorAcceptNo+"'";
      tTransferData=new TransferData();
      tTransferData.setNameAndValue("SQL", sql1);
      tVData = new VData();
      tVData.add(tTransferData);
      tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
      if(tBusinessDelegate.submitData(tVData, "getOneValue", "ExeSQLUI"))
      {
          xEdorAcceptNo=(String)tBusinessDelegate.getResult().getObjectByObjectName("String", 0);
      }	

		
		if(xEdorAcceptNo!=null&&!xEdorAcceptNo.equals("")&&DelFlag.equals("EdorApp")) //客户层保全,拆分受理号之后需要循环对各个受理号加锁
		{
			mCustomerBQFlag="1";
			String mEdorAcceptNo ="";
			
			String sql2=" select edoracceptno from lpconttempinfo where icedoracceptno='"+xEdorAcceptNo+"'" ;
      tTransferData=new TransferData();
      tTransferData.setNameAndValue("SQL", sql2);
      tVData = new VData();
      tVData.add(tTransferData);
      tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
      if(tBusinessDelegate.submitData(tVData, "getOneValue", "ExeSQLUI"))
      {
          mEdorAcceptNo=(String)tBusinessDelegate.getResult().getObjectByObjectName("String", 0);
      }		
				
			String sql3="select icedoracceptno from lpconttempinfo "+" where edoracceptno='"+mEdorAcceptNo+"'  union select '"+xEdorAcceptNo+"' from dual ";
      tTransferData=new TransferData();
      tTransferData.setNameAndValue("SQL", sql3);
      tVData = new VData();
      tVData.add(tTransferData);
      tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
      if(tBusinessDelegate.submitData(tVData, "execSQL", "ExeSQLUI"))
      {
          BQMissionData=(SSRS)tBusinessDelegate.getResult().getObjectByObjectName("SSRS", 0);
      }	

			if(BQMissionData.MaxRow==0)
			{
				  BFControl= false;
				  Content = "撤销失败，原因是:客户层保全查询所有子任务失败!" ;
			    FlagStr = "Fail";
			}
			for(int i=1;i<=BQMissionData.MaxRow;i++)
			{
        tTransferData=new TransferData();
        tTransferData.setNameAndValue("OperatedNo", BQMissionData.GetText(i,1));
        tTransferData.setNameAndValue("LockModule", "LB0001");
        tTransferData.setNameAndValue("Operator",  tGI.Operator);
        tVData = new VData();
        tVData.add(tTransferData);			
        tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
			  if(!tBusinessDelegate.submitData(tVData, "lock3String", "PubConcurrencyLockUI"))
				{
					  BFControl= false;
					  Content = "撤销失败，原因是:客户层保全下受理号:"+BQMissionData.GetText(i,1)+"并发控制加锁失败!" ;
				    FlagStr = "Fail";
				}
			}
		}
		else //一般撤销
		{
        	tTransferData=new TransferData();
        	tTransferData.setNameAndValue("OperatedNo", tEdorAcceptNo);
        	tTransferData.setNameAndValue("LockModule", "LB0001");
        	tTransferData.setNameAndValue("Operator",  tGI.Operator);
        	tVData = new VData();
        	tVData.add(tTransferData);					
		    tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
			BFControl=tBusinessDelegate.submitData(tVData, "lock3String", "PubConcurrencyLockUI");
		}
		//LB0001 保全撤销与银行代收的组间并发控制 
		if (BFControl == true) 
		{
			   
//			个人保全撤销
			if(DelFlag.equals("EdorItem"))
			{
				  //删除保全项目
				  LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
				  tLPEdorItemSchema.setEdorAcceptNo(request.getParameter("EdorAcceptNo"));
				  tLPEdorItemSchema.setEdorNo(request.getParameter("EdorNo"));
				  tLPEdorItemSchema.setContNo(request.getParameter("ContNo"));
				  tLPEdorItemSchema.setEdorType(request.getParameter("EdorType"));
				  tLPEdorItemSchema.setEdorState(request.getParameter("EdorItemState"));
				  tLPEdorItemSchema.setInsuredNo(request.getParameter("InsuredNo"));
				  tLPEdorItemSchema.setPolNo(request.getParameter("PolNo"));
				  tLPEdorItemSchema.setMakeDate(request.getParameter("MakeDate"));
				  tLPEdorItemSchema.setMakeTime(request.getParameter("MakeTime"));
          
				  tTransferData.setNameAndValue("EdorAcceptNo", request.getParameter("EdorAcceptNo"));

					// 准备传输数据 VData
					tVData.addElement(tGI);
					tVData.addElement(tLPEdorItemSchema);
					tVData.addElement(tTransferData);

          String busiName="PGrpEdorCancelUI";
          tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
          if(!tBusinessDelegate.submitData(tVData,"I&EDORITEM",busiName))
          {    
              if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
              { 
				          Content = "撤销失败，原因是:" + tBusinessDelegate.getCErrors().getFirstError();
				          FlagStr = "Fail";
				      }
				      else
				      {
				          Content = "撤销失败";
				          FlagStr = "Fail";				
				      }		
	 
	       }
	       else
	       {
				    Content ="撤销成功！";
		    	  FlagStr = "Succ";	
	       }	 					
			}

			if (DelFlag.equals("EdorMain"))
			{
				//删除批单
				LPEdorMainSchema tLPEdorMainSchema = new LPEdorMainSchema();
				tLPEdorMainSchema.setEdorAcceptNo(request.getParameter("EdorAcceptNo"));
				tLPEdorMainSchema.setEdorNo(request.getParameter("EdorNo"));
				tLPEdorMainSchema.setEdorState(request.getParameter("EdorMainState"));
				tLPEdorMainSchema.setContNo(request.getParameter("ContNo"));

				tTransferData.setNameAndValue("EdorAcceptNo", request.getParameter("EdorAcceptNo"));

 
				// 准备传输数据 VData
				tVData.addElement(tGI);
				tVData.addElement(tLPEdorMainSchema);
				tVData.addElement(tTransferData);

        String busiName="PGrpEdorCancelUI";
        tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
        if(!tBusinessDelegate.submitData(tVData,"I&EDORMAIN",busiName))
        {    
            if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
            { 
				        Content = "撤销失败，原因是:" + tBusinessDelegate.getCErrors().getFirstError();
				        FlagStr = "Fail";
				    }
				    else
				    {
				        Content = "撤销失败";
				        FlagStr = "Fail";				
				    }		
	 
	       }
	       else
	       {
				    Content ="撤销成功！";
		    	  FlagStr = "Succ";	
	       }	 					
			}

			if (DelFlag.equals("EdorApp") || DelFlag.equals("GEdorApp"))
			{
				//撤销保全申请		
				tLPEdorAppSchema.setEdorAcceptNo(request.getParameter("EdorAcceptNo"));
				tLPEdorAppSchema.setEdorState(request.getParameter("EdorState"));

			//	String Activityid = "0000000008";
			 
 			 	tTransferData.setNameAndValue("ActivityID",request.getParameter("ActivityID"));
 			 	tTransferData.setNameAndValue("BusiType", "1002");
 			 	String xActivityID;
				if(DelFlag.equals("GEdorApp"))
				{
				    xActivityID = "0000008008";
				    tTransferData.setNameAndValue("ActivityID",xActivityID);
				}

				String tMissionID = request.getParameter("MissionID");
				String tSubMissionID = request.getParameter("SubMissionID");

				tTransferData.setNameAndValue("MissionID",tMissionID);
				tTransferData.setNameAndValue("SubMissionID",tSubMissionID);

				tTransferData.setNameAndValue("EdorAcceptNo", request.getParameter("EdorAcceptNo"));
 
			  // 准备传输数据 VData
			  tVData.addElement(tGI);
			  tVData.addElement(tLPEdorAppSchema);
				tVData.addElement(tTransferData);

       // String busiName="tWorkFlowUI";
        String busiName="WorkFlowUI";
        tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
        if(!tBusinessDelegate.submitData(tVData,"create",busiName))
        {    
            if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
            { 
				        Content = "撤销失败，原因是:" + tBusinessDelegate.getCErrors().getFirstError();
				        FlagStr = "Fail";
				    }
				    else
				    {
				        Content = "撤销失败";
				        FlagStr = "Fail";				
				    }		
	 
	       }
	       else
	       {
				    Content ="撤销成功！";
		    	  FlagStr = "Succ";	
	       }	 			
			}

//			团体保全撤销

			if(DelFlag.equals("GrpEdorItem"))
			{
				//删除团体保全项目
				LPGrpEdorItemSchema tLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
				tLPGrpEdorItemSchema.setEdorAcceptNo(request.getParameter("EdorAcceptNo"));
				tLPGrpEdorItemSchema.setEdorNo(request.getParameter("EdorNo"));
				tLPGrpEdorItemSchema.setGrpContNo(request.getParameter("GrpContNo"));
				tLPGrpEdorItemSchema.setEdorType(request.getParameter("EdorType"));
 
					// 准备传输数据 VData
				tVData.addElement(tGI);
				tVData.addElement(tLPGrpEdorItemSchema);
				tVData.addElement(tTransferData);
 
        String busiName="PGrpEdorCancelUI";
        tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
        if(!tBusinessDelegate.submitData(tVData,"G&EDORITEM",busiName))
        {    
            if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
            { 
				        Content = "撤销失败，原因是:" + tBusinessDelegate.getCErrors().getFirstError();
				        FlagStr = "Fail";
				    }
				    else
				    {
				        Content = "撤销失败";
				        FlagStr = "Fail";				
				    }		
	 
	       }
	       else
	       {
				    Content ="撤销成功！";
		    	  FlagStr = "Succ";	
	       }	 					
			}

			if(DelFlag.equals("GEdorItem"))
			{
				//删除团体保全项目下个人
				LPGrpEdorItemSchema tLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
				tLPGrpEdorItemSchema.setEdorAcceptNo(request.getParameter("EdorAcceptNo"));
				tLPGrpEdorItemSchema.setEdorNo(request.getParameter("EdorNo"));
				tLPGrpEdorItemSchema.setGrpContNo(request.getParameter("GrpContNo"));
				tLPGrpEdorItemSchema.setEdorType(request.getParameter("EdorType"));

				LPEdorItemSet mLPEdorItemSet = new LPEdorItemSet();

				LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
				tLPEdorItemSchema.setEdorAcceptNo(request.getParameter("EdorAcceptNo"));
				tLPEdorItemSchema.setEdorNo(request.getParameter("EdorNo"));
				tLPEdorItemSchema.setGrpContNo(request.getParameter("GrpContNo"));
				tLPEdorItemSchema.setContNo(request.getParameter("ContNo"));
				tLPEdorItemSchema.setEdorType(request.getParameter("EdorType"));
				tLPEdorItemSchema.setInsuredNo(request.getParameter("CustomerNo"));
				tLPEdorItemSchema.setPolNo("000000");

				mLPEdorItemSet.add(tLPEdorItemSchema);
 
				// 准备传输数据 VData
				tVData.addElement(tGI);
				tVData.addElement(tLPGrpEdorItemSchema);
				tVData.addElement(mLPEdorItemSet);
				tVData.addElement(tTransferData);
 
        String busiName="PGrpEdorCancelUI";
        tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
        if(!tBusinessDelegate.submitData(tVData,"G&I&EDORITEM",busiName))
        {    
            if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
            { 
				        Content = "撤销失败，原因是:" + tBusinessDelegate.getCErrors().getFirstError();
				        FlagStr = "Fail";
				    }
				    else
				    {
				        Content = "撤销失败";
				        FlagStr = "Fail";				
				    }		
	 
	       }
	       else
	       {
				    Content ="撤销成功！";
		    	  FlagStr = "Succ";	
	       }	 					
			}


//			<!-- XinYQ added on 2006-04-24 : 保全项目公用险种操作 : BGN -->
//			==========================================================================

//			删除团体保全项目下险种
			if (DelFlag.equalsIgnoreCase("GEdorRisk"))
			{
			    //LPGrpEdorItemSchema
				LPGrpEdorItemSchema tLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
				tLPGrpEdorItemSchema.setEdorAcceptNo(request.getParameter("EdorAcceptNo"));
				tLPGrpEdorItemSchema.setEdorNo(request.getParameter("EdorNo"));
				tLPGrpEdorItemSchema.setEdorType(request.getParameter("EdorType"));
				tLPGrpEdorItemSchema.setGrpContNo(request.getParameter("GrpContNo"));
				//TransferData
			    tTransferData.setNameAndValue("EdorAcceptNo", request.getParameter("EdorAcceptNo"));
			    tTransferData.setNameAndValue("EdorNo", request.getParameter("EdorNo"));
			    tTransferData.setNameAndValue("EdorType", request.getParameter("EdorType"));
			    tTransferData.setNameAndValue("GrpContNo", request.getParameter("GrpContNo"));
			    tTransferData.setNameAndValue("GrpPolNo", request.getParameter("GrpPolNo"));

				//VData
				tVData.addElement(tGI);
				tVData.addElement(tLPGrpEdorItemSchema);
				tVData.addElement(tTransferData);
				
        String busiName="PGrpEdorCancelUI";
        tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
        if(!tBusinessDelegate.submitData(tVData,"G&EdorRisk",busiName))
        {    
            if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
            { 
				        Content = "撤销失败，原因是:" + tBusinessDelegate.getCErrors().getFirstError();
				        FlagStr = "Fail";
				    }
				    else
				    {
				        Content = "撤销失败";
				        FlagStr = "Fail";				
				    }		
	      }
	       else
	       {
				    Content ="撤销成功！";
		    	  FlagStr = "Succ";	
		    	  out.println("<script language='JavaScript'>");
			      out.println("    try { parent.fraInterface.queryBothGrpRiskGrid() } catch (ex) {};");
			      out.println("</script>");
	       }	 					 

			}

//			==========================================================================
//			<!-- XinYQ added on 2006-04-24 : 保全项目公用险种操作 : BGN -->


			if(DelFlag.equals("PolEdorItem"))
			{
				//删除团体保全项目下个人
				LPGrpEdorItemSchema tLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
				tLPGrpEdorItemSchema.setEdorAcceptNo(request.getParameter("EdorAcceptNo"));
				tLPGrpEdorItemSchema.setEdorNo(request.getParameter("EdorNo"));
				tLPGrpEdorItemSchema.setGrpContNo(request.getParameter("GrpContNo"));
				tLPGrpEdorItemSchema.setEdorType(request.getParameter("EdorType"));

				LPEdorItemSet mLPEdorItemSet = new LPEdorItemSet();

				LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
				tLPEdorItemSchema.setEdorAcceptNo(request.getParameter("EdorAcceptNo"));
				tLPEdorItemSchema.setEdorNo(request.getParameter("EdorNo"));
				tLPEdorItemSchema.setGrpContNo(request.getParameter("GrpContNo"));
				tLPEdorItemSchema.setContNo(request.getParameter("ContNo"));
				tLPEdorItemSchema.setEdorType(request.getParameter("EdorType"));
				tLPEdorItemSchema.setInsuredNo(request.getParameter("CustomerNo"));
				tLPEdorItemSchema.setPolNo(request.getParameter("PolNo"));

				mLPEdorItemSet.add(tLPEdorItemSchema);

 
				// 准备传输数据 VData
				tVData.addElement(tGI);
				tVData.addElement(tLPGrpEdorItemSchema);
				tVData.addElement(mLPEdorItemSet);
				tVData.addElement(tTransferData);
 
        String busiName="PGrpEdorCancelUI";
        tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
        if(!tBusinessDelegate.submitData(tVData,"G&I&EDORITEM",busiName))
        {    
            if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
            { 
				        Content = "撤销失败，原因是:" + tBusinessDelegate.getCErrors().getFirstError();
				        FlagStr = "Fail";
				    }
				    else
				    {
				        Content = "撤销失败";
				        FlagStr = "Fail";				
				    }		
	 
	       }
	       else
	       {
				    Content ="撤销成功！";
		    	  FlagStr = "Succ";	
	       }	 					
			}			
			
		}
		else
	 {
			if("".equals(Content))
			{
			  Content = "保单正在进行银行代收，请返回";
			}
		    FlagStr = "Fail";
		}

	}
	finally 
	{
		//注意解锁
		if(xEdorAcceptNo!=null&&!xEdorAcceptNo.equals("")&&DelFlag.equals("EdorApp")) //客户层保全,拆分受理号之后需要循环对各个受理号加锁
		{
			if(BQMissionData.MaxRow==0){
				Content = "撤销失败，原因是:客户层保全查询所有子任务失败!" ;
			    FlagStr = "Fail";
			}
			for(int i=1;i<=BQMissionData.MaxRow;i++)
			{
        		tTransferData=new TransferData();
        		tTransferData.setNameAndValue("OperatedNo", BQMissionData.GetText(i,1));
        		tTransferData.setNameAndValue("LockModule", "LB0001");
        		tTransferData.setNameAndValue("Operator",  tGI.Operator);
        		tVData = new VData();
        		tVData.add(tTransferData);			
        		tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
			  	if(!tBusinessDelegate.submitData(tVData, "unLockJSP", "PubConcurrencyLockUI"))
				{
					Content = "撤销失败，原因是:客户层保全下受理号:"+BQMissionData.GetText(i,1)+"并发控制解锁失败!" ;
				    FlagStr = "Fail";
				}
			}
			

			}else{
	        	tTransferData=new TransferData();
	        	tVData = new VData();
        		tTransferData.setNameAndValue("OperatedNo", tEdorAcceptNo);
        		tTransferData.setNameAndValue("LockModule", "LB0001");
        		tTransferData.setNameAndValue("Operator",  tGI.Operator);
	        	tVData.add(tTransferData);
	        	tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
				if(!tBusinessDelegate.submitData(tVData, "unLockJSP", "PubConcurrencyLockUI"))
				{
					Content = "撤销失败，原因是:"+ tBusinessDelegate.getCErrors().getFirstError();
					FlagStr = "Fail";
				}
			}
		}
%>

<html>
<head>
    <script language="JavaScript">
        try
        {
            parent.fraInterface.afterSubmit('<%=FlagStr%>', '<%=Content%>');
        }
        catch (ex)
        {
            alert('<%=Content%>');
        }
    </script>
</html>
