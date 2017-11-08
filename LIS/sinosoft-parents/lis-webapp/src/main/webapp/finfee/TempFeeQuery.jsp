
<%
	//程序名称：TempFeeQurey.jsp
	//程序功能：
	//创建日期：
	//创建人  ：
	//更新记录：  更新人    更新日期     更新原因/内容
	//
%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<!--用户校验类-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.operfee.*"%>
<%@page import="com.sinosoft.service.*" %>

<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<!--Step 1--查询是否已经存在数据 -->
<%
	//输出参数
	CErrors tError = null;
	String FlagStr = "";
	String Content = "";
	GlobalInput tGI = new GlobalInput(); //repair:
	tGI = (GlobalInput)session.getValue("GI"); //参见loginSubmit.jsp
	String TempFeeType = request.getParameter("TempFeeType");
	String ClaimFeeType=request.getParameter("ClaimFeeType"); //理赔收费类型
	String InputNo = "";
	String TempFeeNo = "";
	String RiskCode = "";
	//String PolNo="";
	String OtherNo = "";
	String OtherNoType = "";
	String InputNo1 = "";
	String Currency = "";
	double SumDuePayMoney = 0;
	SSRS tSSRS = new SSRS();




	//应收总表
	LJSPaySchema tLJSPaySchema = new LJSPaySchema();
	LJSPaySet mLJSPaySet = new LJSPaySet();
	//-------------上面是共有信息---------------------
	//-------------如果暂交费类型是2和4或7------------------------
	if(TempFeeType.equals("2"))
	{
		InputNo = request.getParameter("InputNo4"); //交费通知书号
		InputNo1 = request.getParameter("InputNo3"); //合同号
	}
	if(TempFeeType.equals("4"))
	{
		InputNo1 = request.getParameter("InputNo7"); //批单受理号
		InputNo = request.getParameter("InputNo8"); //交费通知书号
		tLJSPaySchema.setOtherNoType("10");

	}
	if(TempFeeType.equals("6")) {
		if("1".equals(ClaimFeeType))
		{
		  InputNo   = request.getParameter("InputNo11");
		  InputNo1   = request.getParameter("InputNo12");
		  tLJSPaySchema.setOtherNoType("5");        //理赔赔案号
	  }
	  if("2".equals(ClaimFeeType))
	  {
	  		  InputNo   = request.getParameter("InputNoH11");
				  InputNo1   = request.getParameter("InputNoH12");
				  tLJSPaySchema.setOtherNoType("5");        //理赔赔案号
	  }
	}

	if(TempFeeType.equals("2") || TempFeeType.equals("4") ||TempFeeType.equals("6"))
	{
		//---------------------------------------------------
		//判断输入的号码是保单号还是通知书号
		//如果是保单号，最好判断出是个人，集体，合同
		//查询应收总表，查询条件为OtherNo=输入的号码 OtherNoType=保单类型（可以不要）
		//如果是通知书号，直接查询应收总表，查询条件为 GetNoticeNo=输入的号码
		//因此只是查询条件不一样，其他操作相同
		//---------------------------------------------------

		if (InputNo.length()!=0)                          //交费通知书
		tLJSPaySchema.setGetNoticeNo(InputNo);
		if (InputNo1.length()!=0)
		tLJSPaySchema.setOtherNo(InputNo1);
		
		if(FlagStr == "")
		{
			VData tVData = new VData();
			tVData.addElement(tGI);
			tVData.add(tLJSPaySchema);
			//if(TempFeeType.equals("2") || ("6".equals(TempFeeType) && ("1".equals(ClaimFeeType)))) //续期收费和理赔加费按照险种明细查询（查询个人应收明细或集体应收明细）
			//zy 2009-03-20 10:37 续期催收调整规则，应收总表的险种信息和保单信息为主险保单相关信息，催收界面显示出主险相关信息，暂收记录记在主险下
			if ("6".equals(TempFeeType) && ("1".equals(ClaimFeeType)))
			{
				//VerDuePayFeeQueryUI tVerDuePayFeeQueryUI = new VerDuePayFeeQueryUI();
				
				BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
				
				
				if(!tBusinessDelegate.submitData(tVData, "QUERYDETAIL","VerDuePayFeeQueryUI"))
				{
					Content = " 查询失败，原因是: " + tBusinessDelegate.getCErrors().getError(0).errorMessage;
					FlagStr = "Fail";
				}
				else
				{
					tVData.clear();
					mLJSPaySet = new LJSPaySet();
					tVData = tBusinessDelegate.getResult();
					mLJSPaySet.set((LJSPaySet)tVData.getObjectByObjectName("LJSPaySet", 0));
					tLJSPaySchema = (LJSPaySchema)mLJSPaySet.get(1);
					tSSRS = (SSRS)tVData.getObjectByObjectName("SSRS", 0);

					TempFeeNo = tLJSPaySchema.getGetNoticeNo();
					SumDuePayMoney = tLJSPaySchema.getSumDuePayMoney();
					Currency=tLJSPaySchema.getCurrency();
					OtherNo = tLJSPaySchema.getOtherNo(); //得到保单号（集体或个人）
					OtherNoType = tLJSPaySchema.getOtherNoType(); //得到号码类型（集体或个人或合同）
				//	RiskCode = tLJSPaySchema.getRiskCode();
					//银行在途标记VerDuePayFeeQueryUI处理类中已经对银行在途标记进行了判断
//目前MS不需要查询投保人信息和交费期数
				}
			}
			else
			{
				BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
				
				if(!tBusinessDelegate.submitData(tVData, "QUERY","VerDuePayFeeQueryUI"))
				{
					Content = " 查询失败，原因是: " + tBusinessDelegate.getCErrors().getError(0).errorMessage;
					FlagStr = "Fail";
				}
				else
				{
					tVData.clear();
					mLJSPaySet = new LJSPaySet();
					tVData = tBusinessDelegate.getResult();
					mLJSPaySet.set((LJSPaySet)tVData.getObjectByObjectName("LJSPaySet", 0));

					for (int i =1;i<=mLJSPaySet.size();i++)
					{
						tLJSPaySchema = (LJSPaySchema)mLJSPaySet.get(1);
						if(TempFeeType.equals("4"))
						{
						    if(tLJSPaySchema.getBankCode()!=null && !tLJSPaySchema.getBankCode().equals(""))
						    {
									Content = " 该次收费操作的收费方式为银行转账，不能采用收费界面录入方式，请先做收费方式的变更！";
									FlagStr = "Fail";
									break;
						    }
						}
					}
				}
			}
		}
		if(FlagStr == "")
		{
			Content = " 查询成功";
			FlagStr = "Succ";
		}
	}

	loggerDebug("TempFeeQuery","TempFeeType:" + TempFeeType);
%>
<html>
<script language="javascript">
	parent.fraInterface.afterQuery("<%=FlagStr%>","<%=Content%>");
</script>
<body>
<script language="javascript">
   if("<%=TempFeeType%>"=="4"||("<%=TempFeeType%>"=="6" && "<%=ClaimFeeType%>"=="2") || "<%=TempFeeType%>"=="2")
   {
     if("<%=FlagStr%>"=="Succ")
     {     	
	     <%   
     	for (int i =0;i<mLJSPaySet.size();i++)
		{			
			tLJSPaySchema = (LJSPaySchema)mLJSPaySet.get(i+1);

			TempFeeNo = tLJSPaySchema.getGetNoticeNo();
			SumDuePayMoney = tLJSPaySchema.getSumDuePayMoney();
			Currency=tLJSPaySchema.getCurrency();
			OtherNo = tLJSPaySchema.getOtherNo(); 
			OtherNoType = tLJSPaySchema.getOtherNoType(); //得到号码类型
			RiskCode = tLJSPaySchema.getRiskCode();			
			%>
			 var RiskCode="";
		      var RiskName = new Array();
		      if("<%=RiskCode%>"==null||"<%=RiskCode%>"=="null")
		      {
		        RiskCode = "000000";
		        RiskName[0]= new Array();
		        RiskName[0][0]= "";
		        <%if(i==0){%>
		          parent.fraInterface.initTempGridReadOnlyCont();   //合同交费时不显示险种信息
		          <%}%>
		          parent.fraInterface.TempGrid.addOne("TempGrid");
		      }
		      else
		      {
		        RiskCode="<%=RiskCode%>";
		        if(RiskCode=="000000")
		        {
		          RiskName[0]= new Array();
		          RiskName[0][0]=""; 
		          <%if(i==0){%>
		          parent.fraInterface.initTempGridReadOnlyCont();   //合同交费时不显示险种信息
		          <%}%>
		          parent.fraInterface.TempGrid.addOne("TempGrid");		          
		        }
		        else
		        {
		        	<%if(i==0){%>
		        	var returnRow = "<%=mLJSPaySet.size()%>";
			     	var row= parent.fraInterface.TempGrid.mulLineCount;
				      for(;returnRow>row;row++)
				      {
				        parent.fraInterface.TempGrid.addOne("TempGrid");
				      }
				     <%}%>
		          //var strSql = "select RiskName from LMRisk where RiskCode='"+"<%=RiskCode%>"+"'";
		          var tResourceName="finfee.TempFeeInputSql";
				  var strSql = wrapSql(tResourceName,"querysqldes2",["<%=RiskCode%>"]);
		          var strResult=easyQueryVer3(strSql, 1, 1, 1);
		          
		          if(!strResult)
		          {
		            alert("险种编码表中没有查询到险种编码对应的险种名称！");
		            RiskName[0]= new Array();
		            RiskName[0][0]= "";
		          }
		          else
		          {
		            RiskName=decodeEasyQueryResult(strResult);
		          }
		         }
		      }
		      parent.fraInterface.TempGrid.setRowColData("<%=i%>",1,RiskCode);
		      parent.fraInterface.TempGrid.setRowColData("<%=i%>",2,RiskName[0][0]);
		      parent.fraInterface.TempGrid.setRowColData("<%=i%>",3,"<%=Currency%>");
		      parent.fraInterface.TempGrid.setRowColData("<%=i%>",4,"<%=SumDuePayMoney%>");
		      parent.fraInterface.TempGrid.setRowColData("<%=i%>",5,"<%=OtherNo%>");
		      <%
		}%>	 
			parent.fraInterface.document.all('TempFeeNo').value = "<%=TempFeeNo%>";
		
		      if (parent.fraInterface.document.all('TempFeeType').value=="4" && parent.fraInterface.document.all('InputNo8').value!="<%=TempFeeNo%>") {
		        parent.fraInterface.alert("录入的交费收据号码与保全受理号不匹配！");
		        parent.fraInterface.TempGrid.clearData();
		      }
		      else if (parent.fraInterface.document.all('TempFeeType').value=="4" && parent.fraInterface.document.all('InputNo7').value!="<%=OtherNo%>") {
		       parent.fraInterface.alert("录入的保全受理号与交费收据号码不匹配！");
		        parent.fraInterface.TempGrid.clearData();
		     }    
     }
   }
   else if("<%=TempFeeType%>"=="6" && "<%=ClaimFeeType%>"=="1")
   {
     parent.fraInterface.initTempGridReadOnlyCont();   //合同交费时不显示险种信息
      if("<%=FlagStr%>"=="Succ")
      {
		<%
				for (int i =1;i<=tSSRS.MaxRow;i++)
				{
					SumDuePayMoney=Double.valueOf(tSSRS.GetText(i,2)).doubleValue();
					RiskCode=tSSRS.GetText(i,1);
					//PolNo=tSSRS.GetText(i,3);
					//loggerDebug("TempFeeQuery","PolNo"+PolNo);
					loggerDebug("TempFeeQuery","RiskCode"+RiskCode);
		 %>
		      var row= parent.fraInterface.TempGrid.mulLineCount;
		      parent.fraInterface.TempGrid.addOne("TempGrid");		    
		      if("<%=RiskCode%>"==null||"<%=RiskCode%>"==""||"<%=RiskCode%>"=="null")
		      {     
		        RiskCode= "000000";
		        RiskName[0]= new Array();
		        RiskName[0][0]= "000000";
		      }
		      else
		      {
		        //var strSql = "select RiskName from LMRisk where RiskCode='"+"<%=RiskCode%>"+"'";
		        var tResourceName="finfee.TempFeeInputSql";
			  	var strSql = wrapSql(tResourceName,"querysqldes2",["<%=RiskCode%>"]);
		        RiskCode="<%=RiskCode%>";
		        var strResult=easyQueryVer3(strSql, 1, 1, 1);
		        if(!strResult)
		         {
		            alert("险种编码表中没有查询到险种编码对应的险种名称！");
		            RiskName[0]= new Array();
		            RiskName[0][0]= "";
		         }
		         else
		         {
		            RiskName=decodeEasyQueryResult(strResult);
		         }
		      }
		      parent.fraInterface.TempGrid.setRowColData(row,1,RiskCode);
		      parent.fraInterface.TempGrid.setRowColData(row,2,RiskName[0][0]);
		      parent.fraInterface.TempGrid.setRowColData(row,3,"<%=Currency%>");
		      parent.fraInterface.TempGrid.setRowColData(row,4,"<%=SumDuePayMoney%>");
		      parent.fraInterface.TempGrid.setRowColData(row,5,"<%=OtherNo%>");
		      parent.fraInterface.document.all('TempFeeNo').value = "<%=TempFeeNo%>";
			    row++;
		<%
				}
		%>		
				if (parent.fraInterface.document.all('TempFeeType').value=="2" && parent.fraInterface.document.all('InputNo3').value!="<%=OtherNo%>") 
				{
				    parent.fraInterface.alert("录入的保单号码与交费通知书号码不匹配！");
				    parent.fraInterface.TempGrid.clearData();
				}
    }
   }
</script>
</body>
</html>

