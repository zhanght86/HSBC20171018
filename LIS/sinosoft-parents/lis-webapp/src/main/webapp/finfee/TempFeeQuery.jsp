
<%
	//�������ƣ�TempFeeQurey.jsp
	//�����ܣ�
	//�������ڣ�
	//������  ��
	//���¼�¼��  ������    ��������     ����ԭ��/����
	//
%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<!--�û�У����-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.operfee.*"%>
<%@page import="com.sinosoft.service.*" %>

<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<!--Step 1--��ѯ�Ƿ��Ѿ��������� -->
<%
	//�������
	CErrors tError = null;
	String FlagStr = "";
	String Content = "";
	GlobalInput tGI = new GlobalInput(); //repair:
	tGI = (GlobalInput)session.getValue("GI"); //�μ�loginSubmit.jsp
	String TempFeeType = request.getParameter("TempFeeType");
	String ClaimFeeType=request.getParameter("ClaimFeeType"); //�����շ�����
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




	//Ӧ���ܱ�
	LJSPaySchema tLJSPaySchema = new LJSPaySchema();
	LJSPaySet mLJSPaySet = new LJSPaySet();
	//-------------�����ǹ�����Ϣ---------------------
	//-------------����ݽ���������2��4��7------------------------
	if(TempFeeType.equals("2"))
	{
		InputNo = request.getParameter("InputNo4"); //����֪ͨ���
		InputNo1 = request.getParameter("InputNo3"); //��ͬ��
	}
	if(TempFeeType.equals("4"))
	{
		InputNo1 = request.getParameter("InputNo7"); //���������
		InputNo = request.getParameter("InputNo8"); //����֪ͨ���
		tLJSPaySchema.setOtherNoType("10");

	}
	if(TempFeeType.equals("6")) {
		if("1".equals(ClaimFeeType))
		{
		  InputNo   = request.getParameter("InputNo11");
		  InputNo1   = request.getParameter("InputNo12");
		  tLJSPaySchema.setOtherNoType("5");        //�����ⰸ��
	  }
	  if("2".equals(ClaimFeeType))
	  {
	  		  InputNo   = request.getParameter("InputNoH11");
				  InputNo1   = request.getParameter("InputNoH12");
				  tLJSPaySchema.setOtherNoType("5");        //�����ⰸ��
	  }
	}

	if(TempFeeType.equals("2") || TempFeeType.equals("4") ||TempFeeType.equals("6"))
	{
		//---------------------------------------------------
		//�ж�����ĺ����Ǳ����Ż���֪ͨ���
		//����Ǳ����ţ�����жϳ��Ǹ��ˣ����壬��ͬ
		//��ѯӦ���ܱ���ѯ����ΪOtherNo=����ĺ��� OtherNoType=�������ͣ����Բ�Ҫ��
		//�����֪ͨ��ţ�ֱ�Ӳ�ѯӦ���ܱ���ѯ����Ϊ GetNoticeNo=����ĺ���
		//���ֻ�ǲ�ѯ������һ��������������ͬ
		//---------------------------------------------------

		if (InputNo.length()!=0)                          //����֪ͨ��
		tLJSPaySchema.setGetNoticeNo(InputNo);
		if (InputNo1.length()!=0)
		tLJSPaySchema.setOtherNo(InputNo1);
		
		if(FlagStr == "")
		{
			VData tVData = new VData();
			tVData.addElement(tGI);
			tVData.add(tLJSPaySchema);
			//if(TempFeeType.equals("2") || ("6".equals(TempFeeType) && ("1".equals(ClaimFeeType)))) //�����շѺ�����ӷѰ���������ϸ��ѯ����ѯ����Ӧ����ϸ����Ӧ����ϸ��
			//zy 2009-03-20 10:37 ���ڴ��յ�������Ӧ���ܱ��������Ϣ�ͱ�����ϢΪ���ձ��������Ϣ�����ս�����ʾ�����������Ϣ�����ռ�¼����������
			if ("6".equals(TempFeeType) && ("1".equals(ClaimFeeType)))
			{
				//VerDuePayFeeQueryUI tVerDuePayFeeQueryUI = new VerDuePayFeeQueryUI();
				
				BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
				
				
				if(!tBusinessDelegate.submitData(tVData, "QUERYDETAIL","VerDuePayFeeQueryUI"))
				{
					Content = " ��ѯʧ�ܣ�ԭ����: " + tBusinessDelegate.getCErrors().getError(0).errorMessage;
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
					OtherNo = tLJSPaySchema.getOtherNo(); //�õ������ţ��������ˣ�
					OtherNoType = tLJSPaySchema.getOtherNoType(); //�õ��������ͣ��������˻��ͬ��
				//	RiskCode = tLJSPaySchema.getRiskCode();
					//������;���VerDuePayFeeQueryUI���������Ѿ���������;��ǽ������ж�
//ĿǰMS����Ҫ��ѯͶ������Ϣ�ͽ�������
				}
			}
			else
			{
				BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
				
				if(!tBusinessDelegate.submitData(tVData, "QUERY","VerDuePayFeeQueryUI"))
				{
					Content = " ��ѯʧ�ܣ�ԭ����: " + tBusinessDelegate.getCErrors().getError(0).errorMessage;
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
									Content = " �ô��շѲ������շѷ�ʽΪ����ת�ˣ����ܲ����շѽ���¼�뷽ʽ���������շѷ�ʽ�ı����";
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
			Content = " ��ѯ�ɹ�";
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
			OtherNoType = tLJSPaySchema.getOtherNoType(); //�õ���������
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
		          parent.fraInterface.initTempGridReadOnlyCont();   //��ͬ����ʱ����ʾ������Ϣ
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
		          parent.fraInterface.initTempGridReadOnlyCont();   //��ͬ����ʱ����ʾ������Ϣ
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
		            alert("���ֱ������û�в�ѯ�����ֱ����Ӧ���������ƣ�");
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
		        parent.fraInterface.alert("¼��Ľ����վݺ����뱣ȫ����Ų�ƥ�䣡");
		        parent.fraInterface.TempGrid.clearData();
		      }
		      else if (parent.fraInterface.document.all('TempFeeType').value=="4" && parent.fraInterface.document.all('InputNo7').value!="<%=OtherNo%>") {
		       parent.fraInterface.alert("¼��ı�ȫ������뽻���վݺ��벻ƥ�䣡");
		        parent.fraInterface.TempGrid.clearData();
		     }    
     }
   }
   else if("<%=TempFeeType%>"=="6" && "<%=ClaimFeeType%>"=="1")
   {
     parent.fraInterface.initTempGridReadOnlyCont();   //��ͬ����ʱ����ʾ������Ϣ
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
		            alert("���ֱ������û�в�ѯ�����ֱ����Ӧ���������ƣ�");
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
				    parent.fraInterface.alert("¼��ı��������뽻��֪ͨ����벻ƥ�䣡");
				    parent.fraInterface.TempGrid.clearData();
				}
    }
   }
</script>
</body>
</html>

