<%
//�������ƣ�PEdorTypeHISubmit.jsp
//�����ܣ� 
//�������ڣ�2005-07-09
//������  ��Nicholas
//�޸�ʱ�䣺2005-07
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.db.LCContDB"%>
  <%@page import="com.sinosoft.lis.bq.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@page contentType="text/html;charset=GBK" %>
  <%@page import="com.sinosoft.service.*" %> 

<%
  //����������Ϣ
  //TransferData tTransferData = new TransferData();
  LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
//  EdorDetailUI tEdorDetailUI = new EdorDetailUI();
  	 String busiName="EdorDetailUI";
	 BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  LPCustomerImpartSchema tLPCustomerImpartSchema = null;
  LPCustomerImpartSet tLPCustomerImpartSet = new LPCustomerImpartSet();
    
  //����Ҫִ�еĶ�����
  CErrors tError = null;                 
  String FlagStr = "";
  String Content = "";
  String transact = "";
  
  //transact = request.getParameter("fmtransact");
  transact = "INSERT||MAIN";
  //System.out.println("---transact: " + transact);  
  
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput)session.getValue("GI");
	
  //���˱���������Ϣ
  tLPEdorItemSchema.setEdorAcceptNo(request.getParameter("EdorAcceptNo"));
	tLPEdorItemSchema.setContNo(request.getParameter("ContNo"));
  tLPEdorItemSchema.setEdorNo(request.getParameter("EdorNo"));
	tLPEdorItemSchema.setEdorType(request.getParameter("EdorType"));
	tLPEdorItemSchema.setInsuredNo(request.getParameter("CustomerNo"));
  //tLPEdorItemSchema.setPolNo(request.getParameter("PolNo"));
	tLPEdorItemSchema.setEdorAppDate(request.getParameter("EdorAppDate")); 
	tLPEdorItemSchema.setEdorValiDate(request.getParameter("EdorValiDate")); 
	tLPEdorItemSchema.setStandbyFlag1(request.getParameter("CustomerRole")); 
	String tCustomerRole=request.getParameter("CustomerRole");
	
  String tImpartNum[] = request.getParameterValues("NewImpartGridNo");
  String tImpartVer[] = request.getParameterValues("NewImpartGrid1");            //��֪���
  String tImpartCode[] = request.getParameterValues("NewImpartGrid2");           //��֪����
  String tImpartContent[] = request.getParameterValues("NewImpartGrid3");        //��֪����
  String tImpartParamModle[] = request.getParameterValues("NewImpartGrid4");        //��д����
  
//add by jiaqiangli 2009-02-18 LCCustomerImpart.ProposalContNo = lccont.prtno ��������Լ�÷�
	LCContDB tLCContDB = new LCContDB();
	tLCContDB.setContNo(request.getParameter("ContNo"));
	tLCContDB.getInfo();
	//add by jiaqiangli 2009-02-18 LCCustomerImpart.ProposalContNo = lccont.prtno ��������Լ�÷�
		
	int ImpartCount = 0;
	if(tImpartNum != null) ImpartCount = tImpartNum.length;
      
	for(int i = 0; i < ImpartCount; i++)
	{
  
		tLPCustomerImpartSchema = new LPCustomerImpartSchema();
    //tLPCustomerImpartSchema.setProposalContNo(request.getParameter("ContNo"));
    tLPCustomerImpartSchema.setProposalContNo(tLCContDB.getPrtNo());
    tLPCustomerImpartSchema.setContNo(request.getParameter("ContNo"));
		tLPCustomerImpartSchema.setCustomerNo(request.getParameter("CustomerNo"));
		tLPCustomerImpartSchema.setCustomerNoType(tCustomerRole);
		tLPCustomerImpartSchema.setImpartCode(tImpartCode[i]);
		tLPCustomerImpartSchema.setImpartContent(tImpartContent[i]);
		tLPCustomerImpartSchema.setImpartParamModle(tImpartParamModle[i]);
		tLPCustomerImpartSchema.setImpartVer(tImpartVer[i]) ;
		//tLPCustomerImpartSchema.setPatchNo("0");
		tLPCustomerImpartSet.add(tLPCustomerImpartSchema);
		System.out.println("------------------------" + tImpartVer[i]);
		System.out.println("------------------------" + tImpartCode[i]);
		System.out.println("------------------------" + tImpartContent[i]);
		System.out.println("------------------------" + tImpartParamModle[i]);
	}
	
  try 
  {
    // ׼���������� VData
    VData tVData = new VData();  
  	
    //������˱�����Ϣ(��ȫ)	
		 tVData.add(tG);
		 tVData.add(tLPEdorItemSchema);
		 //tVData.add(tTransferData);
		 tVData.add(tLPCustomerImpartSet);
    
//     boolean tag = tEdorDetailUI.submitData(tVData,transact);
     boolean tag = tBusinessDelegate.submitData(tVData,transact,busiName);
  } 
  catch(Exception ex) 
  {
    Content = "����ʧ�ܣ�ԭ����:" + ex.toString();
    FlagStr = "Fail";
	}			
	
  //�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
  if (FlagStr == "") 
  {
//  	tError = tEdorDetailUI.mErrors;
  	tError = tBusinessDelegate.getCErrors(); 
  	if (!tError.needDealError()) 
  	{        
  		FlagStr = "Success";                  
      Content = "����ɹ���";
    } 
    else  
    {
  		Content = " ����ʧ�ܣ�ԭ����:" + tError.getFirstError();
  		FlagStr = "Fail";
  	}
	}
%>   
                   
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>", "<%=Content%>");
</script>
</html>
