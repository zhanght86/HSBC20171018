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
<%@page import="com.sinosoft.lis.db.*"%>
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
  System.out.println("-----PGsubmit---");
  CErrors tError = null;
  //����Ҫִ�еĶ�������ӣ��޸�      
  String FlagStr = "";
  String Content = "";
  String fmAction = request.getParameter("Transact");
  
  LPEdorItemSchema tLPEdorItemSchema=new LPEdorItemSchema();
  //ִ�ж�����insert ��Ӽ�¼��update �޸ļ�¼��delete ɾ����¼
  GlobalInput tG = new GlobalInput();
  tG = (GlobalInput)session.getValue("GI");
  tLPEdorItemSchema.setEdorAcceptNo(request.getParameter("EdorAcceptNo"));
  tLPEdorItemSchema.setEdorNo(request.getParameter("EdorNo"));
  tLPEdorItemSchema.setEdorAppNo(request.getParameter("EdorAppNo"));
  tLPEdorItemSchema.setEdorType(request.getParameter("EdorType"));
  tLPEdorItemSchema.setContNo(request.getParameter("ContNo"));
  tLPEdorItemSchema.setPolNo(request.getParameter("PolNo"));
  System.out.println("encode:"+tLPEdorItemSchema.encode());
  
  LPInsuAccOutSchema tLPInsuAccOutSchema=new LPInsuAccOutSchema();
  LPInsuAccInSet tLPInsuAccInSet=new LPInsuAccInSet();

  String EdorNo=request.getParameter("EdorNo");
  String EdorType=request.getParameter("EdorType");
  String ContNo=request.getParameter("ContNo");
  String OutPolNo="";
  String OutInsuAccNo="";
  //String OutPayPlanCode="";
  String AccOutType="1";
  String AccOutUnit=request.getParameter("OutUnit");
  String AccOutBala="";

  if(fmAction.equals("INSERT||DEALOUT"))
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
	  //String[] tOutPayPlanCode=request.getParameterValues("LCGrpInsuAccGrid5");//ת�����˻�
	  String[] tCanMoveUnit=request.getParameterValues("LCGrpInsuAccGrid6");//��ת����λ
	  //String[] tAccOutUnit=request.getParameterValues("LCGrpInsuAccGrid8");//�䶯��λ
	  String[] tAccOutBala=request.getParameterValues("LCGrpInsuAccGrid9");//�䶯���
	  
	  OutPolNo=tOutPolNo[tSelNo];
	  OutInsuAccNo=tOutInsuAccNo[tSelNo];
	  //OutPayPlanCode=tOutPayPlanCode[tSelNo];
  
  
	  /*�����˻�ת����Ϣ*/
	  tLPInsuAccOutSchema.setEdorNo(EdorNo);
  	tLPInsuAccOutSchema.setEdorType(EdorType);
  	tLPInsuAccOutSchema.setContNo(ContNo);
  	tLPInsuAccOutSchema.setOutPolNo(OutPolNo);
  	tLPInsuAccOutSchema.setOutInsuAccNo(OutInsuAccNo);
  	//tLPInsuAccOutSchema.setOutPayPlanCode(OutPayPlanCode);
  	tLPInsuAccOutSchema.setAccOutType(AccOutType);
  	tLPInsuAccOutSchema.setAccOutUnit(AccOutUnit);
  	//tLPInsuAccOutSchema.setAccOutBala(AccOutBala);
  	  
	  //System.out.println("AccOutType:"+AccOutType);
	  System.out.println("AccOutUnit:"+AccOutUnit);
	  //System.out.println("AccOutBala:"+AccOutBala);

		
  }
  else if(fmAction.equals("DELETE||CANCELOUT"))
  {
  	  /*�����˻�ת����Ϣ*/
	  tLPInsuAccOutSchema.setEdorNo(EdorNo);
  	tLPInsuAccOutSchema.setEdorType(EdorType);
  	tLPInsuAccOutSchema.setContNo(ContNo);
  	tLPInsuAccOutSchema.setOutPolNo(request.getParameter("PolNoDO"));
  	tLPInsuAccOutSchema.setOutInsuAccNo(request.getParameter("InsuAccNo"));
  	//tLPInsuAccOutSchema.setOutPayPlanCode(request.getParameter("PayPlanCode"));
  	//tLPInsuAccOutSchema.setAccOutType(AccOutType);
  	//tLPInsuAccOutSchema.setAccOutUnit(AccOutUnit);
  	//tLPInsuAccOutSchema.setAccOutBala(AccOutBala);
  	  
	  //System.out.println("AccOutType:"+AccOutType);
	  System.out.println("AccOutUnit:"+AccOutUnit);
	  //System.out.println("AccOutBala:"+AccOutBala);
  }
  else if(fmAction.equals("INSERT||DEALIN"))
  {
  
  
  String tChk[] = request.getParameterValues("InpLCInsuAccGridChk"); 
		  
		  String[] tInPolNo=request.getParameterValues("LCInsuAccGrid2");//ת�뱣����
		  String[] tInInsuAccNo=request.getParameterValues("LCInsuAccGrid3");//ת���˻�
		  //String[] tInPayPlanCode=request.getParameterValues("LCInsuAccGrid5");//ת�����˻�
		  String[] tAccInRate=request.getParameterValues("LCInsuAccGrid7");//ת�����
		  //String[] tAccInBala=request.getParameterValues("LCInsuAccGrid10");//ת����
		  
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
			           	tLPInsuAccInSchema.setContNo(ContNo);
			           	tLPInsuAccInSchema.setInPolNo(tInPolNo[index]);
			           	tLPInsuAccInSchema.setInInsuAccNo(tInInsuAccNo[index]);
			           	//tLPInsuAccInSchema.setInPayPlanCode(tInPayPlanCode[index]);
			           	
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
			           				//if(tAccInBala[index]!=null||!tAccInBala[index].equals(""))
			           				//{
			           				//	AccInType="2";
			           				//}
			           			}
			           		}
			           		System.out.println("AccInType:"+AccInType);   
			           	}
			           		if(AccInType.equals("3"))
			           		{
			          System.out.println("tAccInRate[index]:"+tAccInRate[index]);
			          
			           			if(tAccInRate[index]==null||tAccInRate[index].equals(""))
			           			{
			           				FlagStr="Fail";
			           				Content = "������ת�����!";
			           				break;
			           			}else if(Double.parseDouble(tAccInRate[index])*1000%50!=0)
			           			{
			           				FlagStr="Fail";
			           				Content = "ת���������Ϊ�ٷ�֮���������!";
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
									
					          //if(AccInType.equals("2"))
					          //{
					          //	if(tAccInBala[index]==null||tAccInBala[index].equals(""))
					          //	{
					          //		FlagStr="Fail";
					          //		Content = "������ת�����!";
					          //		break;
					          //	}else{
					          //		if(!PubFun.isNumeric(tAccInBala[index]))
					          //		{
					          //			FlagStr="Fail";
					          //			Content = "��������ȷ��ת�����!";
					          //			break;
					          //		}
					          //	}
					          //}
				    
				tLPInsuAccInSchema.setAccInType(AccInType);
				tLPInsuAccInSchema.setAccInRate(tAccInRate[index]);
				//tLPInsuAccInSchema.setAccInBala(tAccInBala[index]);
				tLPInsuAccInSet.add(tLPInsuAccInSchema);
			  }
  
  }
  
  }
  else if(fmAction.equals("DELETE||CANCELIN"))//ת�볷��
  {

  }    

  if(FlagStr.equals("Fail"))
  {
  	//������
  }else
  {
	  try
	  {
	  	 //EdorDetailUI tEdorDetailUI   = new EdorDetailUI();
	  	String busiName="EdorDetailUI";
	    BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	  	 //׼���������� VData
	  	 VData tVData = new VData();  
		 //������˱�����Ϣ(��ȫ)
		 tVData.addElement(tG);	 	
		 tVData.addElement(tLPEdorItemSchema);
		 tVData.addElement(tLPInsuAccOutSchema);
		 tVData.addElement(tLPInsuAccInSet);
		 System.out.println("tEdorDetailUI.submitData:");
		 tBusinessDelegate.submitData(tVData,fmAction,busiName);
			
	  	//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
	 	if (FlagStr=="")
	  	{
	    		tError = tBusinessDelegate.getCErrors();
	    		if (!tError.needDealError())
	    		{
	      			Content = " �����ɹ�";
	    			FlagStr = "Succ";
	    			//�ж�ת���ת���Ƿ��м�¼
	    			String strSQL1 = "select count(*) from LPInsuAccOut where edorno = '"+tLPEdorItemSchema.getEdorNo()+"'";
            		String strSQL2 = "select count(*) from LPInsuAccIn where edorno = '"+tLPEdorItemSchema.getEdorNo()+"'";
            
            		ExeSQL ex = new ExeSQL();
            
            		String count1 = ex.getOneValue(strSQL1);
            		String count2 = ex.getOneValue(strSQL2);
            		System.out.println("count1::"+count1+";;count2::"+count2);
            		if(Integer.parseInt(count1)*Integer.parseInt(count2)==0)
            		{    
            			LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
        				tLPEdorItemDB.setEdorNo(tLPEdorItemSchema.getEdorNo());
        				tLPEdorItemSchema.setSchema(tLPEdorItemDB.query().get(1));
        				tLPEdorItemSchema.setEdorState("3");
        				MMap mmap = new MMap();
        				VData lzVData = new VData();
        				mmap.put(tLPEdorItemSchema, "UPDATE");
        				lzVData.add(mmap);
        				PubSubmit tPubSubmit = new PubSubmit();
        				if(!tPubSubmit.submitData(lzVData, ""))
        				{
        				Content = " ��ȫ״̬�޸�ʧ�ܣ�";
	    				FlagStr = "Fail";
        				}
            		}
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

