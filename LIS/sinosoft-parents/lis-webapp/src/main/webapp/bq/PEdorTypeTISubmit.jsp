<%
//GrPEdorTypeTISubmit.jsp
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
 <%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>

<%
 //������Ϣ������У�鴦��
  //�������
  //����������Ϣ
  System.out.println("-----PGsubmit---");
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
  System.out.println("encode:"+tLPGrpEdorItemSchema.encode());
  
  LPInsuAccOutSchema tLPInsuAccOutSchema=new LPInsuAccOutSchema();
  LPInsuAccInSet tLPInsuAccInSet=new LPInsuAccInSet();
  
  //LPTransformSet tLPTransformSet=new LPTransformSet();

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
	  String[] tOutPolNo=request.getParameterValues("LCGrpInsuAccGrid2");//ת������
	  String[] tOutInsuAccNo=request.getParameterValues("LCGrpInsuAccGrid3");//ת���˻�
	  String[] tOutPayPlanCode=request.getParameterValues("LCGrpInsuAccGrid5");//ת�����˻�
	  String[] tCanMoveUnit=request.getParameterValues("LCGrpInsuAccGrid7");//��ת����λ
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
	  		Content = "��������ȷ��ת����λ��!";
	  	}else
	  	{
		  	if(Double.parseDouble(tAccOutUnit[tSelNo])>Double.parseDouble(tCanMoveUnit[tSelNo]))
	  	  	{
	  	  		FlagStr="Fail";
		  		Content = "����ĵ�λ��ӦС�ڻ���ڿ�ת����λ!";
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
	  		Content = "��������ȷ��ת�������!";
	  	}else
	  	{
			AccOutType="2";
			AccOutBala=tAccOutBala[tSelNo];
	  	}
	  }
	  
	  /*�����˻�ת����Ϣ*/
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
	  
	  if(!FlagStr.equals("Fail"))
  	  {
		  String tChk[] = request.getParameterValues("InpLCInsuAccGridChk"); 
		  
		  String[] tInPolNo=request.getParameterValues("LCInsuAccGrid2");//ת�뱣����
		  String[] tInInsuAccNo=request.getParameterValues("LCInsuAccGrid3");//ת���˻�
		  String[] tInPayPlanCode=request.getParameterValues("LCInsuAccGrid5");//ת�����˻�
		  String[] tAccInRate=request.getParameterValues("LCInsuAccGrid9");//ת�����
		  String[] tAccInBala=request.getParameterValues("LCInsuAccGrid10");//ת����
		  
		  String AccInType="";	  //1-�ݶ�2-���3-����
		  String flag="";
		  		  
		  //������ʽ=" Inp+MulLine������+Chk"  
	          for(int index=0;index<tChk.length;index++)
	          {
			if(tChk[index].equals("1"))
			{
				LPInsuAccInSchema tLPInsuAccInSchema=new LPInsuAccInSchema();
				tLPInsuAccInSchema.setEdorNo(EdorNo);
				tLPInsuAccInSchema.setEdorType(EdorType);
				tLPInsuAccInSchema.setGrpContNo(GrpContNo);
				tLPInsuAccInSchema.setInPolNo(tInPolNo[index]);
				tLPInsuAccInSchema.setInInsuAccNo(tInInsuAccNo[index]);
				tLPInsuAccInSchema.setInPayPlanCode(tInPayPlanCode[index]);
				
				flag="1";
				if(AccOutType.equals("1"))//���յ�λ����
				{				
					System.out.println("tAccInRate[index]:"+tAccInRate[index]);
					AccInType="3";
					if(tAccInRate[index]==null||tAccInRate[index].equals(""))
					{
						FlagStr="Fail";
						Content = "������ת�����!";
						break;
					}else{
						if(!PubFun.isNumeric(tAccInRate[index]))
						{
							FlagStr="Fail";
							Content = "��������ȷ��ת�����!";
							break;
						}
					}
				}else if(AccOutType.equals("2"))
				{					
					if(AccInType.equals(""))
					{
						System.out.println("tAccInRate[index]:"+tAccInRate[index]);   
						if(tAccInRate[index]!=null&&!tAccInRate[index].equals(""))
						{
							AccInType="3";
						}else
						{
							if(tAccInBala[index]!=null||!tAccInBala[index].equals(""))
							{
								AccInType="2";
							}
						}
					}
					System.out.println("AccInType:"+AccInType);   
					
					if(AccInType.equals("3"))
					{
						if(tAccInRate[index]==null||tAccInRate[index].equals(""))
						{
							FlagStr="Fail";
							Content = "������ת�����!";
							break;
						}else{
							if(!PubFun.isNumeric(tAccInRate[index]))
							{
								FlagStr="Fail";
								Content = "��������ȷ��ת�����!";
								break;
							}
						}
					}
									
					if(AccInType.equals("2"))
					{
						if(tAccInBala[index]==null||tAccInBala[index].equals(""))
						{
							FlagStr="Fail";
							Content = "������ת����!";
							break;
						}else{
							if(!PubFun.isNumeric(tAccInBala[index]))
							{
								FlagStr="Fail";
								Content = "��������ȷ��ת����!";
								break;
							}
						}
					}
				}
				tLPInsuAccInSchema.setAccInType(AccInType);
				tLPInsuAccInSchema.setAccInRate(tAccInRate[index]);
				tLPInsuAccInSchema.setAccInBala(tAccInBala[index]);
				tLPInsuAccInSet.add(tLPInsuAccInSchema);
			}
		  }
		  System.out.println("flag:"+flag);     
		  if(flag.equals(""))
		  {
			FlagStr="Fail";
			Content = "��ѡ��ת���˻�!";
		  }	
		  //�ж������Ƿ�����߼�
		  if(!FlagStr.equals("Fail")&&tLPInsuAccInSet.size()>0)
		  {
		  	System.out.println("FlagStr1:"+FlagStr);
 			System.out.println("Content1:"+Content);
 			System.out.println("AccOutType:"+AccOutType);
 			 
		  	double rate=0;
		  	double bala=0;
		  	if(AccOutType.equals("1"))
		  	{
		  		rate=0;
		  		for(int i=1;i<=tLPInsuAccInSet.size();i++)
		  		{
		  			System.out.println("tLPInsuAccInSet.get(i).getAccInRate():"+tLPInsuAccInSet.get(i).getAccInRate());
		  			rate+=tLPInsuAccInSet.get(i).getAccInRate();
		  		}
		  		if(rate!=1)
		  		{
		  			FlagStr="Fail";
					Content = "ת���������=1!";
		  		}
		  	}
		  	if(AccOutType.equals("2"))
		  	{
		  		System.out.println("tLPInsuAccInSet.get(1).getAccInType():"+tLPInsuAccInSet.get(1).getAccInType());
		  		if(tLPInsuAccInSet.get(1).getAccInType().equals("2"))
		  		{
			  		bala=0;
			  		for(int i=1;i<=tLPInsuAccInSet.size();i++)
			  		{
			  			bala+=tLPInsuAccInSet.get(i).getAccInBala();
			  		}
			  		if(bala!=tLPInsuAccOutSchema.getAccOutBala())
			  		{
			  			FlagStr="Fail";
						Content = "ת����������ת�����!";
			  		}
		  		}else if(tLPInsuAccInSet.get(1).getAccInType().equals("3"))
		  		{
		  			rate=0;
			  		for(int i=1;i<=tLPInsuAccInSet.size();i++)
			  		{
			  			rate+=tLPInsuAccInSet.get(i).getAccInRate();
			  		}
			  		System.out.println("rate:"+rate);
			  		if(rate!=1)
			  		{
			  			FlagStr="Fail";
						Content = "ת���������=1!";
			  		}
		  		}
		  	}
		  }
	  }
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
	  String[] tSerialNo=request.getParameterValues("LPInsuAccGrid13");//��ˮ��
	  String[] tOutPolNo=request.getParameterValues("LPInsuAccGrid2");//������
	  String[] tOutInsuAccNo=request.getParameterValues("LPInsuAccGrid3");//ת���˻�
	  String[] tOutPayPlanCode=request.getParameterValues("LPInsuAccGrid4");//ת�����˻�
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
	  	 PEdorPGDetailUI tPEdorPGDetailUI   = new PEdorPGDetailUI();
	  	 //׼���������� VData
	  	 VData tVData = new VData();  
		 //������˱�����Ϣ(��ȫ)
		 tVData.addElement(tG);	 	
		 tVData.addElement(tLPGrpEdorItemSchema);
		 tVData.addElement(tLPInsuAccOutSchema);
		 tVData.addElement(tLPInsuAccInSet);
	         tPEdorPGDetailUI.submitData(tVData,fmAction);
			
	  	//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
	 	if (FlagStr=="")
	  	{
	    		tError = tPEdorPGDetailUI.mErrors;
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

