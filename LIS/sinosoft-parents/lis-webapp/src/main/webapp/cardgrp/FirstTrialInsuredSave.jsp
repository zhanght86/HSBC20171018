<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//�������ƣ�LDPersonSave.jsp
//�����ܣ�
//�������ڣ�2002-06-27 08:49:52
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
//      
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.vdb.*"%>
  <%@page import="com.sinosoft.lis.cardgrp.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.service.*" %>
<%
  //������Ϣ������У�鴦��
  //�������
  LCContSchema tLCContSchema = new LCContSchema();
  LDPersonSchema tLDPersonSchema   = new LDPersonSchema();
  LCInsuredDB tOLDLCInsuredDB=new LCInsuredDB();
  LCAddressSchema tLCAddressSchema = new LCAddressSchema();
  LCInsuredSchema tmLCInsuredSchema =new LCInsuredSchema();
  LCCustomerImpartSet tLCCustomerImpartSet = new LCCustomerImpartSet();
  LCCustomerImpartDetailSet tLCCustomerImpartDetailSet = new LCCustomerImpartDetailSet();
  TransferData tTransferData = new TransferData(); 
  String busiName1="tbContInsuredUI";
  BusinessDelegate tBusinessDelegate1=BusinessDelegate.getBusinessDelegate();
  
  //ContInsuredUI tContInsuredUI   = new ContInsuredUI();
  //�������
  String FlagStr = "";
  String Content = "";
   
  GlobalInput tGI = new GlobalInput(); //repair:
  tGI=(GlobalInput)session.getValue("GI");  //�μ�loginSubmit.jsp
  if(tGI==null)
  {
    loggerDebug("FirstTrialInsuredSave","ҳ��ʧЧ,�����µ�½");   
    FlagStr = "Fail";        
    Content = "ҳ��ʧЧ,�����µ�½";  
  }
  else //ҳ����Ч
  {
  CErrors tError = null;
  String tBmCert = "";
  //����Ҫִ�еĶ�������ӣ��޸ģ�ɾ��
  String fmAction=request.getParameter("fmAction");
         
        tLCContSchema.setGrpContNo(request.getParameter("ContNo"));  
        tLCContSchema.setContNo(request.getParameter("ContNo")); 
        tLCContSchema.setPrtNo(request.getParameter("PrtNo"));
        tLCContSchema.setManageCom(request.getParameter("ManageCom"));
        
        tmLCInsuredSchema.setInsuredNo(request.getParameter("InsuredNo"));
        tmLCInsuredSchema.setContNo(request.getParameter("ContNo"));
        tmLCInsuredSchema.setGrpContNo(request.getParameter("ContNo"));
                
        tLDPersonSchema.setCustomerNo(request.getParameter("InsuredNo"));
        tLDPersonSchema.setName(request.getParameter("InsuredName"));
        tLDPersonSchema.setSex(request.getParameter("InsuredSex"));      
        tLDPersonSchema.setBirthday(request.getParameter("InsuredBirthday"));
        tLDPersonSchema.setIDType(request.getParameter("InsuredIDType"));
        tLDPersonSchema.setIDNo(request.getParameter("InsuredIDNo"));
        
        tTransferData.setNameAndValue("SavePolType","0"); //��ȫ�����ǣ�Ĭ��Ϊ0����ʶ�Ǳ�ȫ  
        tTransferData.setNameAndValue("ContNo",request.getParameter("ContNo")); 
        tTransferData.setNameAndValue("FamilyType","0"); //��ͥ�����  //
        tTransferData.setNameAndValue("ContType","1"); //�ŵ������˵����
        tTransferData.setNameAndValue("PolTypeFlag","0"); //���������
        tTransferData.setNameAndValue("InsuredPeoples","0"); //������������
        tTransferData.setNameAndValue("InsuredAppAge","0"); //������������
        tTransferData.setNameAndValue("SequenceNo","0"); //
        
        tOLDLCInsuredDB.setInsuredNo(request.getParameter("InsuredNo"));
                
        try
         {
             VData tVData = new VData();
             tVData.add(tLCContSchema);
             tVData.add(tLDPersonSchema);
             tVData.add(tLCCustomerImpartSet);
             tVData.add(tLCCustomerImpartDetailSet);             
             tVData.add(tmLCInsuredSchema);
             tVData.add(tLCAddressSchema);
             tVData.add(tOLDLCInsuredDB);
             
             tVData.add(tTransferData);
             tVData.add(tGI);
             
            if (tBusinessDelegate1.submitData(tVData,fmAction,busiName1))
            {
    		    if (fmAction.equals("INSERT||CONTINSURED"))
		        {
		    	    tVData.clear();
		    	    tVData = tBusinessDelegate1.getResult();
		    	    LCInsuredSchema mLCInsuredSchema = new LCInsuredSchema(); 
			        mLCInsuredSchema=(LCInsuredSchema)tVData.getObjectByObjectName("LCInsuredSchema",0);
			
			        String strCustomerNo = mLCInsuredSchema.getInsuredNo(); 
			        String strContNo =  mLCInsuredSchema.getContNo();
			        String strAddressNo= mLCInsuredSchema.getAddressNo();
		            %>
		            <SCRIPT language="javascript">
		            	      parent.fraInterface.GrpGrid.addOne("GrpGrid");    	            	 
                        parent.fraInterface.GrpGrid.setRowColData(parent.fraInterface.GrpGrid.mulLineCount-1,1,"<%=mLCInsuredSchema.getInsuredNo()%>");
                        parent.fraInterface.GrpGrid.setRowColData(parent.fraInterface.GrpGrid.mulLineCount-1,2,"<%=mLCInsuredSchema.getName()%>");
                        parent.fraInterface.GrpGrid.setRowColData(parent.fraInterface.GrpGrid.mulLineCount-1,3,"");       

                        
		            </SCRIPT>
		            <%
		        }
		        if (fmAction.equals("UPDATE||CONTINSURED"))
		        {
		    	    loggerDebug("FirstTrialInsuredSave","------return");
			        	
		    	    tVData.clear();
		    	    tVData = tBusinessDelegate1.getResult();
		    	    loggerDebug("FirstTrialInsuredSave","-----size:"+tVData.size());
		    	    
		    	    LCInsuredSchema mLCInsuredSchema = new LCInsuredSchema(); 
			        mLCInsuredSchema=(LCInsuredSchema)tVData.getObjectByObjectName("LCInsuredSchema",0);
			
			        String strCustomerNo = mLCInsuredSchema.getInsuredNo(); 
			        String strContNo =  mLCInsuredSchema.getContNo();
			        String strAddressNo= mLCInsuredSchema.getAddressNo();
			        loggerDebug("FirstTrialInsuredSave","jsp"+strAddressNo);
		            %>
		            <SCRIPT language="javascript">
		                parent.fraInterface.fm.all("InsuredNo").value="<%=strCustomerNo%>";
		                parent.fraInterface.fm.all("AddressNo").value="<%=strAddressNo%>";  
		                if (parent.fraInterface.fm.all("FamilyType").value=="1"){  
                            parent.fraInterface.InsuredGrid.setRowColData(parent.fraInterface.InsuredGrid.getSelNo()-1,1,"<%=mLCInsuredSchema.getInsuredNo()%>");
                            parent.fraInterface.InsuredGrid.setRowColData(parent.fraInterface.InsuredGrid.getSelNo()-1,2,"<%=mLCInsuredSchema.getName()%>");
                            parent.fraInterface.InsuredGrid.setRowColData(parent.fraInterface.InsuredGrid.getSelNo()-1,3,"<%=mLCInsuredSchema.getSex()%>");
                            parent.fraInterface.InsuredGrid.setRowColData(parent.fraInterface.InsuredGrid.getSelNo()-1,4,"<%=mLCInsuredSchema.getBirthday()%>");
                            parent.fraInterface.InsuredGrid.setRowColData(parent.fraInterface.InsuredGrid.getSelNo()-1,5,"<%=mLCInsuredSchema.getRelationToMainInsured()%>");
                        }
		            </SCRIPT>
		            <%
		        }
		        if (fmAction.equals("DELETE||CONTINSURED"))
		        {
		          Content = "ɾ���ɹ�����";
		            %>
		            <SCRIPT language="javascript">
		                parent.fraInterface.fm.all("InsuredNo").value=""; 
		            </SCRIPT>
		            <%
		        }
	        }
    }
    catch(Exception ex)
    {
      Content = "����ʧ�ܣ�ԭ����:" + ex.toString();
      FlagStr = "Fail";
    }
  

  //�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
  if (FlagStr=="")
  {
    tError = tBusinessDelegate1.getCErrors();
    if (!tError.needDealError())
    {                          
      Content ="����ɹ���";
    	FlagStr = "Succ";
    }
    else                                                                           
    {
    	Content = "����ʧ�ܣ�ԭ����:" + tError.getFirstError();
    	FlagStr = "Fail";
    }
  }
  loggerDebug("FirstTrialInsuredSave","FlagStr:"+FlagStr+"Content:"+Content);
  
} //ҳ����Ч��
%>                                       
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

