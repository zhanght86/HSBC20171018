<%
//�������ƣ�PEdorTypeTRCount.jsp
//�����ܣ�
//�������ڣ�2005-07-04
//������  ��Nicholas
//˵��    ����ҳ�������ǳ�ʼ��MulLine��������Ϣ����Ӻ�̨���
//���¼�¼��  ������    ��������     ����ԭ��/����
%>


  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.bq.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page contentType="text/html;charset=GBK" %>

<html>
<script language="javascript">
  //����jsp�Ķ�ά����
  var tMulArray = new Array();

<%
  //����Ҫִ�еĶ�������ѯ
  CErrors tError = null;                 
  String FlagStr = "";
  String Content = "";
  Double tWholeMoney = new Double(0.0);
  Double tWholeMoneyIntrest = new Double(0.0);
  Double tSumWholeMoney = new Double(0.0);
  //Integer tArrayRows = new Integer(0);
  //VData tMulLineCon = new VData();
  String tMulLineArray[][];


	
  //���˱���������Ϣ
  LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
  tLPEdorItemSchema.setEdorAcceptNo(request.getParameter("EdorAcceptNo"));
  tLPEdorItemSchema.setEdorNo(request.getParameter("EdorNo"));
	tLPEdorItemSchema.setEdorType(request.getParameter("EdorType"));
	tLPEdorItemSchema.setContNo(request.getParameter("ContNo"));
	tLPEdorItemSchema.setInsuredNo(request.getParameter("InsuredNo"));
	tLPEdorItemSchema.setEdorAppDate(request.getParameter("EdorAppDate")); 
	tLPEdorItemSchema.setEdorValiDate(request.getParameter("EdorValiDate")); 
	
	String tisCalInterest = request.getParameter("isCalInterest");
	System.out.println("==> CallInterestFlag is " + tisCalInterest);
	
	PEdorTRDetailBLF tPEdorTRDetailBLF = new PEdorTRDetailBLF();
  try 
  {
    // ׼���������� VData
    VData tVData = new VData();  
    tVData.add(tLPEdorItemSchema);
    tVData.add(tisCalInterest);
    tPEdorTRDetailBLF.submitData(tVData,"QUERY||MAIN");

  } 
  catch(Exception ex) 
  {
    Content = "��ѯ�Ե���Ϣ��������:" + ex.toString();
    FlagStr = "Fail";
	}			
	
  //�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
  if (FlagStr.equals("")) 
  {
  	tError = tPEdorTRDetailBLF.mErrors;
  	if (!tError.needDealError()) 
  	{
  		FlagStr = "Success";

  		if (tPEdorTRDetailBLF.getResult()!=null && tPEdorTRDetailBLF.getResult().size()>0)
  	  {
  	  	VData tResult = new VData();
  	  	tResult = tPEdorTRDetailBLF.getResult();
  		  if (tResult==null) 
  		  {
  				FlagStr = "Fail"; 
  				Content = "��ȡ�Ե���Ϣʧ�ܣ�";
  		  }
  	  	tMulLineArray = (String[][])tResult.get(0);

			  for (int i=0;i<tMulLineArray.length;i++)
			  {
			  	%>
					tMulArray[<%=i%>]=new Array();
			    tMulArray[<%=i%>][0]="<%=tMulLineArray[i][0]%>";
			    tMulArray[<%=i%>][1]="<%=tMulLineArray[i][1]%>";
			    tMulArray[<%=i%>][2]="<%=tMulLineArray[i][2]%>";
			    tMulArray[<%=i%>][3]="<%=tMulLineArray[i][3]%>";
			    tMulArray[<%=i%>][4]="<%=tMulLineArray[i][4]%>";
			    tMulArray[<%=i%>][5]="<%=tMulLineArray[i][5]%>";
			    tMulArray[<%=i%>][6]="<%=tMulLineArray[i][6]%>";
			    tMulArray[<%=i%>][7]="<%=tMulLineArray[i][7]%>";
			  	<%
			  }
  	  }
    } 
    else  
    {
  		Content = tError.getFirstError();
  		FlagStr = "Fail";
  	}
	}
%>
	parent.fraInterface.afterCount("<%=FlagStr%>", tMulArray, "<%=Content%>", "<%=String.valueOf(tWholeMoney.doubleValue())%>","<%=String.valueOf(tWholeMoneyIntrest.doubleValue())%>","<%=String.valueOf(tSumWholeMoney.doubleValue())%>");
</script>
</html>

