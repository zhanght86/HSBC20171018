<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//�������ƣ�SelfProposalInputSave.jsp
//�����ܣ���������-�ͻ���Ϣ¼��
//�������ڣ�2008-03-05 
//������  ��zz
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.tb.*"%>
<%@page import="com.sinosoft.lis.ebusiness.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.cbcheck.*"%>
<%@page import="java.util.*"%>

<%

	    loggerDebug("SelfProposalSave","��ʼִ��SelfProposalInputSave.jsp");
	
		//�������
		CErrors tError = null;
		String FlagStr = "";
		String Content = "";
		String tAction = "";
		String tOperate = "";

	    
		Vector tVector=new Vector();	
		VData tResult=new VData();

 
	 	//GlobalInput tG = new GlobalInput();	
	 	//tG = ( GlobalInput )session.getValue( "GI" );
	 	//loggerDebug("SelfProposalSave","tG: "+tG);

	 	tAction = request.getParameter("fmAction");
	 	loggerDebug("SelfProposalSave","�û�ѡ��Ĳ���ΪtAction1:"+tAction);
	 	
	 	//����ӡˢ��,���������������Ǳ�����
	 	String tPrtNo=request.getParameter("CertifyNo");
	 	loggerDebug("SelfProposalSave","�û�ѡ��Ĳ����Ŀ���:"+tPrtNo);

	
	   //����ȷ��     
       if (tAction.equals("Confirm")) 
       {
			//������Ϣ����
			loggerDebug("SelfProposalSave","��ʼ���ñ���������Ϣ...");
    		LCPolSchema tLCPolSchema = new LCPolSchema();
    		 
    		tLCPolSchema.setContNo(tPrtNo);
    		tLCPolSchema.setPrtNo(tPrtNo);
          tLCPolSchema.setInsuredPeoples("1"); //����������
          tLCPolSchema.setPolTypeFlag("0"); //�������ͱ�� 0 --���˵�,1 --������,2 --���ŵ��������ʻ�  
    		tLCPolSchema.setCValiDate(request.getParameter("CValiDate"));
    		tLCPolSchema.setStandbyFlag1("�绰"); //������������
    		loggerDebug("SelfProposalSave","Save:CValidate:"+tLCPolSchema.getCValiDate());

         	loggerDebug("SelfProposalSave","���ñ���������Ϣ����...");       
          		
    		// Ͷ������Ϣ����
    		loggerDebug("SelfProposalSave","��ʼ����Ͷ���˻�����Ϣ...");
    		LCAppntSchema tLCAppntSchema = new LCAppntSchema();
    			
    		tLCAppntSchema.setContNo(tPrtNo); //������
    		tLCAppntSchema.setPrtNo(tPrtNo);
    		tLCAppntSchema.setGrpContNo("00000000000000000000");
    		tLCAppntSchema.setAppntGrade("M"); //Ͷ���˼���:M ---��Ͷ����,S ---��ͷ����
    		tLCAppntSchema.setAppntName(request.getParameter("AppntName"));              //���� 
    		tLCAppntSchema.setAppntSex(request.getParameter("AppntSex"));                //�Ա�
    		tLCAppntSchema.setAppntBirthday(request.getParameter("AppntBirthday"));      //�������� 
    		tLCAppntSchema.setIDType(request.getParameter("AppntIDType"));          //֤������
    		tLCAppntSchema.setIDNo(request.getParameter("AppntIDNo"));              //֤������     		
    		tLCAppntSchema.setOccupationType(request.getParameter("AppntOccupationType"));      //ְҵ���
    		tLCAppntSchema.setOccupationCode(request.getParameter("AppntOccupationCode"));      //ְҵ����
    		tLCAppntSchema.setRelationToInsured(request.getParameter("RelationToLCInsured")); //�뱻�����˹�ϵ
    		loggerDebug("SelfProposalSave","Ͷ�����뱻���˹�ϵ:"+tLCAppntSchema.getRelationToInsured());
    		
    		LCAddressSchema tLCAddressSchema = new LCAddressSchema(); 
    		tLCAddressSchema.setPostalAddress(request.getParameter("AppntPostalAddress")); //��ϵ��ַ
    		tLCAddressSchema.setZipCode(request.getParameter("AppntZipCode"));        //��������
    		tLCAddressSchema.setPhone(request.getParameter("AppntPhone"));            //��ϵ�绰 
    		tLCAddressSchema.setEMail(request.getParameter("AppntEMail"));            //��������
    			
    		loggerDebug("SelfProposalSave","����Ͷ������Ϣ����...");    			
    			
    		loggerDebug("SelfProposalSave","��ʼ���ñ����˻�����Ϣ...");
    			
			//��������Ϣ
    		LCInsuredSchema tLCInsuredSchema = new LCInsuredSchema();
		LCAddressSchema tLCAddressSchema2 = new LCAddressSchema(); 		
		if(tLCAppntSchema.getRelationToInsured().equals("00"))
		{
				//��������Ͷ���˱���
			tLCInsuredSchema.setContNo(tPrtNo); //������
			tLCInsuredSchema.setPrtNo(tPrtNo); //������
			tLCInsuredSchema.setGrpContNo("00000000000000000000");
			tLCInsuredSchema.setRelationToMainInsured("00");
			tLCInsuredSchema.setSequenceNo("1");
	    		tLCInsuredSchema.setName(tLCAppntSchema.getAppntName());              //���� 
	    		tLCInsuredSchema.setSex(tLCAppntSchema.getAppntSex());                //�Ա�
	    		tLCInsuredSchema.setBirthday(tLCAppntSchema.getAppntBirthday());      //�������� 
	    		tLCInsuredSchema.setIDType(tLCAppntSchema.getIDType());          //֤������
	    		tLCInsuredSchema.setIDNo(tLCAppntSchema.getIDNo());              //֤������ 	    		
	    		tLCInsuredSchema.setOccupationType(tLCAppntSchema.getOccupationType());       //ְҵ���
	    		tLCInsuredSchema.setOccupationCode(tLCAppntSchema.getOccupationCode());       //ְҵ����
	    		
	    		tLCAddressSchema2.setPostalAddress(request.getParameter("AppntPostalAddress")); //��ϵ��ַ
    		     tLCAddressSchema2.setZipCode(request.getParameter("AppntZipCode"));        //��������
    		     tLCAddressSchema2.setPhone(request.getParameter("AppntPhone"));            //��ϵ�绰 
    		     tLCAddressSchema2.setEMail(request.getParameter("AppntEMail"));            //��������
		}
		else
		{
				//Ͷ�����Ǳ����˵ĸ��׻�ĸ��
			tLCInsuredSchema.setContNo(tPrtNo); //������
			tLCInsuredSchema.setPrtNo(tPrtNo); //������
			tLCInsuredSchema.setGrpContNo("00000000000000000000");
			tLCInsuredSchema.setRelationToMainInsured("00");
			tLCInsuredSchema.setSequenceNo("1");
	    		tLCInsuredSchema.setName(request.getParameter("LCInsuredName"));              //���� 
	    		tLCInsuredSchema.setSex(request.getParameter("LCInsuredSex"));                //�Ա�
	    		tLCInsuredSchema.setBirthday(request.getParameter("LCInsuredBirthday"));      //�������� 
	    		tLCInsuredSchema.setIDType(request.getParameter("LCInsuredIDType"));          //֤������
	    		tLCInsuredSchema.setIDNo(request.getParameter("LCInsuredIDNo"));              //֤������ 
	    		tLCInsuredSchema.setOccupationType(request.getParameter("LCInsuredOccupationType"));       //ְҵ���
	    		tLCInsuredSchema.setOccupationCode(request.getParameter("LCInsuredOccupationCode"));       //ְҵ����
	    		
	    		tLCAddressSchema2.setPostalAddress(request.getParameter("LCInsuredPostalAddress")); //��ϵ��ַ
    		     tLCAddressSchema2.setZipCode(request.getParameter("LCInsuredZipCode"));        //��������
    		     tLCAddressSchema2.setPhone(request.getParameter("LCInsuredPhone"));            //��ϵ�绰 
    		     tLCAddressSchema2.setEMail(request.getParameter("LCInsuredEMail"));            //��������
		}
				
			loggerDebug("SelfProposalSave","����Ͷ������Ϣ����...");		
				
				
			tVector.addElement(tAction);
			tVector.add("HX"); //��ʾ�����ǴӺ���ҵ��ϵͳ������̨
			tVector.addElement(tLCPolSchema);
			tVector.addElement(tLCAppntSchema);
			tVector.addElement(tLCInsuredSchema);
			tVector.addElement(tLCAddressSchema);
			tVector.addElement(tLCAddressSchema2);
			TransferData tTransferData = new TransferData();
			tTransferData.setNameAndValue("SelfFlag", "old");				
			tVector.addElement(tLCPolSchema);
			//�������
			try
			{
			  	//���������ͣ��������������Ա��ӵ�������
			    //�������
			    ActivateBL tActivateBL=new ActivateBL();
			    tResult=(VData)tActivateBL.submitData(tVector);
			      	
			    loggerDebug("SelfProposalSave","���صı�ʶλ:"+tResult.get(0));
			    loggerDebug("SelfProposalSave","���ص���ʾ��Ϣ:"+tResult.get(1));
			}
			catch(Exception ex)
			{
			    Content = "����ʧ�ܣ�ԭ����:" + ex.toString();
			    FlagStr = "Fail";
			}


			if (FlagStr=="")
			{
			  if (tResult.get(0).equals("fail"))
			  {
			      Content = (String)tResult.get(1);
			      FlagStr = "Fail";
			  }
			  else
			  {
				  String tempContent=(String)tResult.get(1);
				  String enddate = tempContent.substring(tempContent.lastIndexOf("||") + 2,tempContent.length());
			      loggerDebug("SelfProposalSave","SelfProposalSave-enddate:"+enddate);
			      String tt=tempContent.substring(0,tempContent.lastIndexOf("||"));
			      String cvalidate = tt.substring(tt.lastIndexOf("||") + 2,tt.length());
			      loggerDebug("SelfProposalSave","SelfProposalSave-cvalidate:"+cvalidate);
			      String RealContent=tempContent.substring(0,tempContent.indexOf("||")-1);
			      Content = RealContent+",��Ч����Ϊ"+cvalidate+"��0ʱ��"+enddate+"��0ʱ!";
			      FlagStr = "Success";
			  }
			}
		    loggerDebug("SelfProposalSave","Flag : " + FlagStr + " -- Content : " + Content);
    }
%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

