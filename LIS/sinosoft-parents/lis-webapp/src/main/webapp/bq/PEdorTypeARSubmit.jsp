<%
//GrPEdorTypeARSubmit.jsp
//�����ܣ�
//�������ڣ�2007-05-24 16:49:22
//������  ��ck
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.bq.*"%>
 <%@page import="com.sinosoft.lis.pubfun.*"%>
 <%@page import="java.lang.*"%>
 <%@page import="com.sinosoft.service.*" %> 
 <%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>

<%
 //������Ϣ������У�鴦��
  //�������
  //����������Ϣ
  System.out.println("-----RDsubmit---");
  CErrors tError = null;
  //����Ҫִ�еĶ�������ӣ��޸�      
  String FlagStr = "";
  String Content = "";
  String fmAction = request.getParameter("Transact");
  
  LPGrpEdorItemSchema tLPGrpEdorItemSchema=new LPGrpEdorItemSchema();
  //ִ�ж�����insert ��Ӽ�¼��update �޸ļ�¼��delete ɾ����¼
  GlobalInput tG = new GlobalInput();
  tG = (GlobalInput)session.getValue("GI");
  tLPGrpEdorItemSchema.setEdorAcceptNo(request.getParameter("EdorAcceptNo"));
  tLPGrpEdorItemSchema.setEdorNo(request.getParameter("EdorNo"));
  tLPGrpEdorItemSchema.setEdorAppNo(request.getParameter("EdorAppNo"));
  tLPGrpEdorItemSchema.setEdorType(request.getParameter("EdorType"));
  tLPGrpEdorItemSchema.setGrpContNo(request.getParameter("GrpContNo"));
  
  LPInsuAccOutSchema tLPInsuAccOutSchema=new LPInsuAccOutSchema();

  String EdorNo=request.getParameter("EdorNo");
  String EdorType=request.getParameter("EdorType");
  String GrpContNo=request.getParameter("GrpContNo");
  String OutPolNo="";
  String OutInsuAccNo="";
  String OutPayPlanCode="";
  String AccOutType="";
  String AccOutUnit="";
  String AccOutBala="";

  if(fmAction.equals("INSERT||PMAIN"))
  {
	  String tLCGrpInsuAccNo[] = request.getParameterValues("LCGrpInsuAccGridNo"); //����ֶ�
	  String tRadio[] = request.getParameterValues("InpLCGrpInsuAccGridSel");
	  //ע�⣺radio�ֶε����ֽṹΪInp+GridName+Sel������GridName=LCGrpInsuAccGrid
	  int tCount = tLCGrpInsuAccNo.length;   //��¼��������
	  int tSelNo =0 ;
	  for (int i = 0; i < tCount; i++){
		if (tRadio [i].equals("1")) {             //������б�ѡ��
			   tSelNo = i;
		 }
	  }
	  String[] tOutPolNo=request.getParameterValues("LCGrpInsuAccGrid2");//��ر���
	  String[] tOutInsuAccNo=request.getParameterValues("LCGrpInsuAccGrid3");//����˻�
	  String[] tOutPayPlanCode=request.getParameterValues("LCGrpInsuAccGrid5");//������˻�
	  String[] tCanMoveUnit=request.getParameterValues("LCGrpInsuAccGrid7");//����ص�λ
	  String[] tAccOutUnit=request.getParameterValues("LCGrpInsuAccGrid9");//�䶯��λ
	  String[] tAccOutBala=request.getParameterValues("LCGrpInsuAccGrid10");//�䶯���
	  
	  OutPolNo=tOutPolNo[tSelNo];
	  OutInsuAccNo=tOutInsuAccNo[tSelNo];
	  OutPayPlanCode=tOutPayPlanCode[tSelNo];

	  if(tAccOutUnit[tSelNo]!=null&&!tAccOutUnit[tSelNo].equals(""))
	  {
	  	if(!PubFun.isNumeric(tAccOutUnit[tSelNo]))
	  	{
	  		FlagStr="Fail";
	  		Content = "��������ȷ����ص�λ��!";
	  	}else
	  	{
		  	if(Double.parseDouble(tAccOutUnit[tSelNo])>Double.parseDouble(tCanMoveUnit[tSelNo]))
	  	  	{
	  	  		FlagStr="Fail";
		  		Content = "����ĵ�λ��ӦС�ڻ���ڿ���ص�λ!";
	  	  	}else
	  		{
				AccOutType="1";
				AccOutUnit=tAccOutUnit[tSelNo];
	  		}
	  	}
	  }else if(tAccOutBala[tSelNo]!=null&&!tAccOutBala[tSelNo].equals(""))
	  {
	  	if(!PubFun.isNumeric(tAccOutBala[tSelNo]))
	  	{
	  		FlagStr="Fail";
	  		Content = "��������ȷ����ؽ����!";
	  	}else
	  	{
			AccOutType="2";
			AccOutBala=tAccOutBala[tSelNo];
	  	}
	  }
	  
	  /*�����˻������Ϣ*/
	  tLPInsuAccOutSchema.setEdorNo(EdorNo);
  	  tLPInsuAccOutSchema.setEdorType(EdorType);
  	  tLPInsuAccOutSchema.setGrpContNo(GrpContNo);
  	  tLPInsuAccOutSchema.setOutPolNo(OutPolNo);
  	  tLPInsuAccOutSchema.setOutInsuAccNo(OutInsuAccNo);
  	  tLPInsuAccOutSchema.setOutPayPlanCode(OutPayPlanCode);
  	  tLPInsuAccOutSchema.setAccOutType(AccOutType);
  	  tLPInsuAccOutSchema.setAccOutUnit(AccOutUnit);
  	  tLPInsuAccOutSchema.setAccOutBala(AccOutBala);

	  System.out.println("AccOutType:"+AccOutType);
	  System.out.println("AccOutUnit:"+AccOutUnit);
	  System.out.println("AccOutBala:"+AccOutBala);
  }
  else if(fmAction.equals("DELETE||PMAIN"))//���׳���
  {
  	  String tLPInsuAccNo[] = request.getParameterValues("LPInsuAccGridNo"); //����ֶ�
	  String aRadio[] = request.getParameterValues("InpLPInsuAccGridSel");
	  //ע�⣺radio�ֶε����ֽṹΪInp+GridName+Sel������GridName=LPInsuAccGrid
	  int aCount = tLPInsuAccNo.length;   //��¼��������
	  int aSelNo =0 ;
	  for (int i = 0; i < aCount; i++){
		if (aRadio [i].equals("1")) {             //������б�ѡ��
			   aSelNo = i;
		 }
	  }
	  String[] tSerialNo=request.getParameterValues("LPInsuAccGrid9");//��ˮ��
	  String[] tOutPolNo=request.getParameterValues("LPInsuAccGrid2");//������
	  String[] tOutInsuAccNo=request.getParameterValues("LPInsuAccGrid3");//����˻�
	  String[] tOutPayPlanCode=request.getParameterValues("LPInsuAccGrid4");//������˻�
  	  tLPInsuAccOutSchema.setSerialNo(tSerialNo[aSelNo]);
  	  tLPInsuAccOutSchema.setOutPolNo(tOutPolNo[aSelNo]);
  	  tLPInsuAccOutSchema.setOutInsuAccNo(tOutInsuAccNo[aSelNo]);
  	  tLPInsuAccOutSchema.setOutPayPlanCode(tOutPayPlanCode[aSelNo]);
  }    

  if(FlagStr.equals("Fail"))
  {
  	//������
  }else
  {
	  try
	  {
	  	 //PEdorRDDetailUI tPEdorRDDetailUI   = new PEdorRDDetailUI();
	  	 String busiName="PEdorARDetailUI";
		 BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		
	  	 //׼���������� VData
	  	 VData tVData = new VData();  
		 //������˱�����Ϣ(��ȫ)
		 tVData.addElement(tG);	 	
		 tVData.addElement(tLPGrpEdorItemSchema);
		 tVData.addElement(tLPInsuAccOutSchema);
	     //tPEdorRDDetailUI.submitData(tVData,fmAction);
	     tBusinessDelegate.submitData(tVData, fmAction,busiName);
			
	  	//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
	 	if (FlagStr=="")
	  	{
	    		tError = tBusinessDelegate.getCErrors();
	    		if (!tError.needDealError())
	    		{
	      			Content = " �����ɹ�";
	    			FlagStr = "Succ";
	    		}
	    		else
	    		{
	    			Content = " ����ʧ�ܣ�ԭ����:" + tError.getFirstError();
	    			FlagStr = "Fail";
	    		}
	  	}
	  }
	  catch(Exception ex)
	  {
		Content = fmAction+"ʧ�ܣ�ԭ����:" + ex.toString();
		FlagStr = "Fail";
	  }
  }
%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

