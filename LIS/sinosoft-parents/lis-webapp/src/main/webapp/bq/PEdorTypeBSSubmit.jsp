<%
//�������ƣ�PEdorTypeACSubmit.jsp
//�����ܣ�
//�������ڣ�2007-06-19 16:49:22
//������  ��Pst���򴴽�

%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.bq.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@page contentType="text/html;charset=GBK" %>
  <%@page import="com.sinosoft.service.*" %> 

<%
 //������Ϣ������У�鴦��
  //�������
  //����������Ϣ
  
  LPEdorItemSchema tLPEdorItemSchema   = new LPEdorItemSchema();
//  EdorDetailUI tEdorDetailUI = new EdorDetailUI();
	 String busiName="EdorDetailUI";
	 BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  
  CErrors tError = null;
  //����Ҫִ�еĶ�������ӣ��޸�
 
  String tRela  = "";                
  String FlagStr = "";
  String Content = "";
  String transact = "";
  String Result="";
  String tConFirmName="";
  
  
  //ִ�ж�����insert ��Ӽ�¼��update �޸ļ�¼��delete ɾ����¼
  transact = request.getParameter("fmtransact");
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput)session.getValue("GI");
  
  //���˱���������Ϣ
  
  tLPEdorItemSchema.setEdorAcceptNo(request.getParameter("EdorAcceptNo"));
	tLPEdorItemSchema.setContNo(request.getParameter("ContNo"));
  tLPEdorItemSchema.setEdorNo(request.getParameter("EdorNo"));
	tLPEdorItemSchema.setEdorType(request.getParameter("EdorType"));
	tLPEdorItemSchema.setInsuredNo("000000");
  tLPEdorItemSchema.setPolNo("000000");
	tLPEdorItemSchema.setEdorAppDate(request.getParameter("EdorAppDate")); 
	tLPEdorItemSchema.setEdorValiDate(request.getParameter("EdorValiDate")); 
  tLPEdorItemSchema.setStandbyFlag1(request.getParameter("AppReason"));
  tLPEdorItemSchema.setStandbyFlag2(request.getParameter("UnValiReason")); 
  String  tCheckStr="";
  for(int t=1;t<=8;t++)
  {
    tCheckStr+="S"+t+"-"+request.getParameter("chkS"+t)+"\\";
  }
  tCheckStr=tCheckStr.substring(0,tCheckStr.lastIndexOf("\\"));
  tCheckStr+="$"; //�������
    for(int t=1;t<=3;t++)
  {
    tCheckStr+="O"+t+"-"+request.getParameter("chkO"+t)+"\\";
  }
  tCheckStr=tCheckStr.substring(0,tCheckStr.lastIndexOf("\\"));
  tCheckStr+="$"; //�������
  tLPEdorItemSchema.setStandbyFlag3(tCheckStr+request.getParameter("Remark"));

try
  {
  
     //׼���������� VData
  	 VData tVData = new VData();  
  	
		 //������˱�����Ϣ(��ȫ)	
		 tVData.add(tG);
		 tVData.add(tLPEdorItemSchema);
//	   boolean tag = tEdorDetailUI.submitData(tVData,"");
	   boolean tag = tBusinessDelegate.submitData(tVData,"",busiName);
	   if (tag)
     {
          System.out.println("Successful");
     }
     else
     {
          System.out.println("Fail");
     }
	//System.out.println("6$$$$$$$$$$$$$\r\n$$$$$$$$$$$$$$\r\n$$$$$$$$$$$$$$\r\n$$$$$$$$$$$$$$\r\n$$$$$$$$$$$$$$");
	}
catch(Exception ex)
{
	Content = "����ʧ�ܣ�ԭ����:" + ex.toString();
  FlagStr = "Fail";
}			
  //�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
  if (FlagStr.equals(""))
  {
//    tError = tEdorDetailUI.mErrors;
    tError = tBusinessDelegate.getCErrors();
    if (!tError.needDealError())
    {                          
      Content = " ����ɹ���";
    	FlagStr = "Success";
    }
    else                                                                           
    {
    	Content = " ����ʧ�ܣ�ԭ����:" + tError.getFirstError();
    	FlagStr = "Fail";
    }
  }
  //��Ӹ���Ԥ����

%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

