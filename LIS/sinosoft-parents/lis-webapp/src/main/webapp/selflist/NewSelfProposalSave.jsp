<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//�������ƣ�NewSelfProposalInputSave.jsp
//�����ܣ�����������-�ͻ���Ϣ¼��
//�������ڣ�2010-01-25 
//������  ��yanglh
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

	    loggerDebug("NewSelfProposalSave","��ʼִ��SelfProposalInputSave.jsp");
	
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
	 	//loggerDebug("NewSelfProposalSave","tG: "+tG);

	 	tAction = request.getParameter("fmAction");
	 	loggerDebug("NewSelfProposalSave","�û�ѡ��Ĳ���ΪtAction1:"+tAction);
	 	
	 	//����ӡˢ��,���������������Ǳ�����
	 	String tPrtNo=request.getParameter("CertifyNo");
	 	loggerDebug("NewSelfProposalSave","�û�ѡ��Ĳ����Ŀ���:"+tPrtNo);

	
	   //����ȷ��     
       if (tAction.equals("Confirm")) 
       {
			//������Ϣ����
			loggerDebug("NewSelfProposalSave","��ʼ���ñ���������Ϣ...");
    		LCPolSchema tLCPolSchema = new LCPolSchema();
    		 
    		tLCPolSchema.setContNo(tPrtNo);
    		tLCPolSchema.setPrtNo(tPrtNo);
          tLCPolSchema.setInsuredPeoples("1"); //����������
          tLCPolSchema.setPolTypeFlag("0"); //�������ͱ�� 0 --���˵�,1 --������,2 --���ŵ��������ʻ�  
    		tLCPolSchema.setCValiDate(request.getParameter("CValiDate"));
    		tLCPolSchema.setStandbyFlag1("�绰"); //������������
    		loggerDebug("NewSelfProposalSave","Save:CValidate:"+tLCPolSchema.getCValiDate());

         	loggerDebug("NewSelfProposalSave","���ñ���������Ϣ����...");       
          		
    		// Ͷ������Ϣ����
    		loggerDebug("NewSelfProposalSave","��ʼ����Ͷ���˻�����Ϣ...");
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
//    		tLCAppntSchema.setRelationToInsured(request.getParameter("RelationToLCInsured")); //�뱻�����˹�ϵ
//    		loggerDebug("NewSelfProposalSave","Ͷ�����뱻���˹�ϵ:"+tLCAppntSchema.getRelationToInsured());
    		
    		LCAddressSchema tLCAddressSchema = new LCAddressSchema(); 
    		tLCAddressSchema.setPostalAddress(request.getParameter("AppntPostalAddress")); //��ϵ��ַ
    		tLCAddressSchema.setZipCode(request.getParameter("AppntZipCode"));        //��������
    		tLCAddressSchema.setPhone(request.getParameter("AppntPhone"));            //��ϵ�绰 
    		tLCAddressSchema.setEMail(request.getParameter("AppntEMail"));            //��������
    			
    		loggerDebug("NewSelfProposalSave","����Ͷ������Ϣ����...");    			
    			
    		loggerDebug("NewSelfProposalSave","��ʼ���ñ����˻�����Ϣ...");
		String tGridNo[] = request.getParameterValues("LCInsuredGridNo");  //���
    String tGrid1 [] = request.getParameterValues("LCInsuredGrid1"); //����
    String tGrid2 [] = request.getParameterValues("LCInsuredGrid2"); //�Ա�
    String tGrid3 [] = request.getParameterValues("LCInsuredGrid3"); //��������
    String tGrid4 [] = request.getParameterValues("LCInsuredGrid4"); //֤������
    String tGrid5 [] = request.getParameterValues("LCInsuredGrid5"); //֤������
    String tGrid6 [] = request.getParameterValues("LCInsuredGrid6"); //ְҵ���
    String tGrid7 [] = request.getParameterValues("LCInsuredGrid7"); //ְҵ����
    String tGrid8 [] = request.getParameterValues("LCInsuredGrid8"); //��ϵ��ַ
    String tGrid9 [] = request.getParameterValues("LCInsuredGrid9"); //��������
    String tGrid10 [] = request.getParameterValues("LCInsuredGrid10"); //��ϵ�绰
    String tGrid11 [] = request.getParameterValues("LCInsuredGrid11"); //��������
    String tGrid12 [] = request.getParameterValues("LCInsuredGrid12"); //��Ͷ���˹�ϵ

		LCInsuredSet tLCInsuredSet = new LCInsuredSet();    
		LCAddressSet tLCAddressSet2 = new LCAddressSet(); 		
		for(int pi=0;pi< tGridNo.length;pi++){			
			//��������Ϣ
    	LCInsuredSchema tLCInsuredSchema = new LCInsuredSchema();
			LCAddressSchema tLCAddressSchema2 = new LCAddressSchema(); 		
			if("00".equals(tGrid12[pi]))
			{
				//��������Ͷ���˱���
				tLCInsuredSchema.setContNo(tPrtNo); //������
				tLCInsuredSchema.setPrtNo(tPrtNo); //������
				tLCInsuredSchema.setGrpContNo("00000000000000000000");
				tLCInsuredSchema.setRelationToMainInsured("00");
				tLCInsuredSchema.setSequenceNo(tGridNo[pi]);
	    	tLCInsuredSchema.setName(tLCAppntSchema.getAppntName());              //���� 
	    	tLCInsuredSchema.setSex(tLCAppntSchema.getAppntSex());                //�Ա�
	    	tLCInsuredSchema.setBirthday(tLCAppntSchema.getAppntBirthday());      //�������� 
	    	tLCInsuredSchema.setIDType(tLCAppntSchema.getIDType());          //֤������
	    	tLCInsuredSchema.setIDNo(tLCAppntSchema.getIDNo());              //֤������ 	    		
	    	tLCInsuredSchema.setOccupationType(tLCAppntSchema.getOccupationType());       //ְҵ���
	    	tLCInsuredSchema.setOccupationCode(tLCAppntSchema.getOccupationCode());       //ְҵ����
	    	tLCInsuredSchema.setRelationToAppnt("00");
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
				tLCInsuredSchema.setSequenceNo(tGridNo[pi]);
	    	tLCInsuredSchema.setName(tGrid1[pi]);              //���� 
	    	tLCInsuredSchema.setSex(tGrid2[pi]);                //�Ա�
	    	tLCInsuredSchema.setBirthday(tGrid3[pi]);      //�������� 
	    	tLCInsuredSchema.setIDType(tGrid4[pi]);          //֤������
	    	tLCInsuredSchema.setIDNo(tGrid5[pi]);              //֤������ 
	    	tLCInsuredSchema.setOccupationType(tGrid6[pi]);       //ְҵ���
	    	tLCInsuredSchema.setOccupationCode(tGrid7[pi]);       //ְҵ����
	    	tLCInsuredSchema.setRelationToAppnt(tGrid12[pi]);
	    	tLCAddressSchema2.setPostalAddress(tGrid8[pi]); //��ϵ��ַ
    		tLCAddressSchema2.setZipCode(tGrid9[pi]);        //��������
    		tLCAddressSchema2.setPhone(tGrid10[pi]);            //��ϵ�绰 
    		tLCAddressSchema2.setEMail(tGrid11[pi]);            //��������
			}
			tLCInsuredSet.add(tLCInsuredSchema);
			tLCAddressSet2.add(tLCAddressSchema2);
		}
				
			loggerDebug("NewSelfProposalSave","����Ͷ������Ϣ����...");		
				
			TransferData tTransferData = new TransferData();
			tTransferData.setNameAndValue("SelfFlag", "new");				
			tVector.addElement(tAction);
			tVector.add("HX"); //��ʾ�����ǴӺ���ҵ��ϵͳ������̨
			tVector.addElement(tLCPolSchema);
			tVector.addElement(tLCAppntSchema);
			tVector.addElement(tLCInsuredSet);
			tVector.addElement(tLCAddressSchema);
			tVector.addElement(tLCAddressSet2);
			tVector.add(tTransferData);	 
				 
			//�������
			try
			{
			  	//���������ͣ��������������Ա��ӵ�������
			    //�������
			    ActivateBL tActivateBL=new ActivateBL();
			    tResult=(VData)tActivateBL.submitData(tVector);
			      	
			    loggerDebug("NewSelfProposalSave","���صı�ʶλ:"+tResult.get(0));
			    loggerDebug("NewSelfProposalSave","���ص���ʾ��Ϣ:"+tResult.get(1));
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
			      loggerDebug("NewSelfProposalSave","SelfProposalSave-enddate:"+enddate);
			      String tt=tempContent.substring(0,tempContent.lastIndexOf("||"));
			      String cvalidate = tt.substring(tt.lastIndexOf("||") + 2,tt.length());
			      loggerDebug("NewSelfProposalSave","SelfProposalSave-cvalidate:"+cvalidate);
			      String RealContent=tempContent.substring(0,tempContent.indexOf("||")-1);
			      Content = RealContent+",��Ч����Ϊ"+cvalidate+"��0ʱ��"+enddate+"��0ʱ!";
			      FlagStr = "Success";
			  }
			}
		    loggerDebug("NewSelfProposalSave","Flag : " + FlagStr + " -- Content : " + Content);
    }
%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

