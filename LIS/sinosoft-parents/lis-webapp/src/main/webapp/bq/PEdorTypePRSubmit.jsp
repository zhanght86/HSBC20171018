<%
//�������ƣ�GEdorTypePRSubmit.jsp
//�����ܣ��ύ����Ǩ�Ƹ�������
//�������ڣ�2005-4-6 16:55 
//������  ��lanjun
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.vdb.*"%>
  <%@page import="com.sinosoft.lis.sys.*"%>
 	<%@page import="com.sinosoft.lis.bq.*"%>
 	<%@page import="com.sinosoft.lis.pubfun.*"%>
 	<%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@page contentType="text/html;charset=GBK" %>
  <%@page import="com.sinosoft.service.*" %> 

<%
  LPEdorItemSchema tLPEdorItemSchema   = new LPEdorItemSchema();  
  LPAppntSchema tLPAppntSchema = new LPAppntSchema();
  LPAddressSchema tLPAddressSchema = new LPAddressSchema();
  
//  EdorDetailUI  tEdorDetailUI = new EdorDetailUI();
  	 String busiName="EdorDetailUI";
	 BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  
  GlobalInput tG = new GlobalInput();  
  tG = (GlobalInput)session.getValue("GI"); 
  
  CErrors tError = null;
  
  //����Ҫִ�еĶ�������ӣ��޸� 
  String tRela  = "";
  String FlagStr = "";
  String Content = "";
  String transact = "";
  String manageCom = "";
  transact = request.getParameter("fmtransact");
  
  //���˱�ȫ��Ŀ����Ϣ
  tLPEdorItemSchema.setEdorAcceptNo(request.getParameter("EdorAcceptNo"));
  tLPEdorItemSchema.setEdorNo(request.getParameter("EdorNo"));
  tLPEdorItemSchema.setEdorType(request.getParameter("EdorType"));
  tLPEdorItemSchema.setGrpContNo(request.getParameter("GrpContNo"));
  tLPEdorItemSchema.setContNo(request.getParameter("ContNo"));
  tLPEdorItemSchema.setInsuredNo(request.getParameter("CustomerNo"));
  /*
  String theCurrentDate = PubFun.getCurrentDate();
  String theCurrentTime = PubFun.getCurrentTime();
  
  tLPEdorItemSchema.setMakeDate(theCurrentDate);
  tLPEdorItemSchema.setMakeTime(theCurrentTime);
  tLPEdorItemSchema.setModifyDate(theCurrentDate);
  tLPEdorItemSchema.setModifyTime(theCurrentTime);  
  tLPEdorItemSchema.setOperator(tG.Operator); */

  //Ͷ���˱�
  tLPAppntSchema.setContNo(request.getParameter("ContNo")); 
  tLPAppntSchema.setEdorNo(request.getParameter("EdorNo"));
	tLPAppntSchema.setEdorType(request.getParameter("EdorType"));
	//tLPAppntSchema.setAddressNo(request.getParameter("AddressNo")); 
	
  //��ַ��Ϣ��
	//tLPAddressSchema.setEdorNo(request.getParameter("EdorNo"));
	//tLPAddressSchema.setEdorType(request.getParameter("EdorType"));
  //tLPAddressSchema.setPostalAddress(request.getParameter("PostalAddress"));
  //tLPAddressSchema.setZipCode(request.getParameter("ZipCode"));        
  //tLPAddressSchema.setPhone(request.getParameter("Phone"));            
  //tLPAddressSchema.setFax(request.getParameter("Fax"));                
 // tLPAddressSchema.setMobile(request.getParameter("Mobile"));
  //tLPAddressSchema.setAddressNo(request.getParameter("AddressNo")); 
  
  
  manageCom = request.getParameter("ManageCom");
  
  //add by jiaqiangli 2009-02-11 Ǩ������lpmove���Է�����Χ�ӿ�ȡ��
  LPMoveSchema tLPMoveSchema = new LPMoveSchema();
  tLPMoveSchema.setEdorNo(request.getParameter("EdorNo"));
  tLPMoveSchema.setEdorType(request.getParameter("EdorType"));
  tLPMoveSchema.setContNoOld(request.getParameter("ContNo"));
  tLPMoveSchema.setManageComOld(request.getParameter("OldManageCom"));
  tLPMoveSchema.setManageComNew(request.getParameter("ManageCom"));
  tLPMoveSchema.setOldCoName(request.getParameter("OldManageComName"));
  tLPMoveSchema.setNewCoName(request.getParameter("ManageComName"));
  //add by jiaqiangli 2009-02-11 Ǩ������lpmove���Է�����Χ�ӿ�ȡ��
  		  
  try
  { 
  	VData tVData = new VData();  
  	tVData.addElement(tLPEdorItemSchema);
    //add by jiaqiangli 2009-02-11 Ǩ������lpmove���Է�����Χ�ӿ�ȡ��
  	tVData.addElement(tLPMoveSchema);
    //add by jiaqiangli 2009-02-11 Ǩ������lpmove���Է�����Χ�ӿ�ȡ��
  	//tVData.addElement(tLPAppntSchema);
	  //tVData.addElement(tLPAddressSchema);
  	tVData.addElement(tG);
  	tVData.addElement(manageCom);
  	System.out.println("LPEdorItemSchema ======================>"+ tLPEdorItemSchema.encode());
//    tEdorDetailUI.submitData(tVData,transact);
    tBusinessDelegate.submitData(tVData, transact ,busiName);		
	}
	catch(Exception ex)
	{
		Content = "����ʧ�ܣ�ԭ����:" + ex.toString();
    FlagStr = "Fail";
	}			
  //�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
  if (FlagStr=="")
  {
//    tError = tEdorDetailUI.mErrors;
    tError = tBusinessDelegate.getCErrors(); 
    if (!tError.needDealError())
    {                          
      Content = " ����ɹ�,�������ڽ�����Ϣ����ϵ��ʽ��Ϣ!";
    	FlagStr = "Success";
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
   //alert("<%=Content%>");
   //top.close();
	 parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

