<%
//�������ƣ�PEdorTypePCSubmit.jsp
//�����ܣ�6
//�������ڣ�2005-6-28 10:47����
//������  ��Lizhuo
//���¼�¼��  ������    ��������     ����ԭ��/����
%>

  
<!--�û�У����-->

  <%@page import="java.lang.*"%>
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.vdb.*"%>
  <%@page import="com.sinosoft.lis.bq.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@page contentType="text/html;charset=GBK" %>
  <%@page import="com.sinosoft.service.*" %> 
  
<html>
<script language="javascript">
var tMulArray = new Array();

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
  String[] Result = new String[8];
  
  //ִ�ж�����insert ��Ӽ�¼��update �޸ļ�¼��delete ɾ����¼
  transact = request.getParameter("fmtransact");
  System.out.println("------transact:"+transact);
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput)session.getValue("GI");
  
  //���˱���������Ϣ
  String theCurrentDate = PubFun.getCurrentDate();
  String theCurrentTime = PubFun.getCurrentTime();
  
  System.out.println("+++++++++++++++++++++++");
  tLPEdorItemSchema.setPolNo("000000");  
  tLPEdorItemSchema.setEdorAcceptNo(request.getParameter("EdorAcceptNo"));
	tLPEdorItemSchema.setContNo(request.getParameter("ContNo"));
  tLPEdorItemSchema.setEdorNo(request.getParameter("EdorNo"));
	tLPEdorItemSchema.setEdorType(request.getParameter("EdorType"));		
	tLPEdorItemSchema.setInsuredNo("000000");
	tLPEdorItemSchema.setPolNo("000000");
	System.out.println(tLPEdorItemSchema.getEdorAcceptNo());
	System.out.println("+++++++++++++++++++++++");
	
	String tisCalInterest = request.getParameter("isCalInterest");
	System.out.println("==> CallInterestFlag is " + tisCalInterest);

  String EdorNo = tLPEdorItemSchema.getEdorNo();
  tLPEdorItemSchema.setMakeDate(theCurrentDate);
  tLPEdorItemSchema.setMakeTime(theCurrentTime);
  tLPEdorItemSchema.setModifyDate(theCurrentDate);
  tLPEdorItemSchema.setModifyTime(theCurrentTime);  
  tLPEdorItemSchema.setOperator(tG.Operator);
  
  //����standbyflag3�������Ƿ��Ϣ��Ӧ����Ӧ��δ��������
  tLPEdorItemSchema.setStandbyFlag3(tisCalInterest);
  
  String tImpartNum[] = request.getParameterValues("ImpartGridNo");
	String tImpartVer[] = request.getParameterValues("ImpartGrid1");            //��֪���
	String tImpartCode[] = request.getParameterValues("ImpartGrid2");           //��֪����
	String tImpartContent[] = request.getParameterValues("ImpartGrid3");        //��֪����
	String tImpartParamModle[] = request.getParameterValues("ImpartGrid4");        //��д����
		//String tImpartCustomerNoType[] = request.getParameterValues("ImpartGrid5"); //��֪�ͻ�����
		//String tImpartCustomerNo[] = request.getParameterValues("ImpartGrid6");  
  int ImpartCount = 0;
  
    //add by jiaqiangli 2009-02-18 LCCustomerImpart.ProposalContNo = lccont.prtno ��������Լ�÷�
	LCContDB tLCContDB = new LCContDB();
	tLCContDB.setContNo(request.getParameter("ContNo"));
	tLCContDB.getInfo();
	//add by jiaqiangli 2009-02-18 LCCustomerImpart.ProposalContNo = lccont.prtno ��������Լ�÷�

  LCCustomerImpartSet tLCCustomerImpartSet = new LCCustomerImpartSet();
  LCCustomerImpartSchema tLCCustomerImpartSchema = new LCCustomerImpartSchema();
	if (tImpartNum != null) ImpartCount = tImpartNum.length;
	System.out.println(ImpartCount);
	if(ImpartCount != 0){	  	    
	   for (int i = 0; i < ImpartCount; i++)	{
	      if(tImpartCode[i] == null || tImpartCode[i].equals("") || tImpartContent[i] == null || tImpartContent[i].equals("")
	      || tImpartParamModle[i] == null ||tImpartParamModle[i].equals("")
	      || tImpartVer[i] == null || tImpartVer[i].equals("")){
	      }
	      else{
	         tLCCustomerImpartSchema = new LCCustomerImpartSchema();		
           //tLCCustomerImpartSchema.setProposalContNo(request.getParameter("ContNo"));
           tLCCustomerImpartSchema.setProposalContNo(tLCContDB.getPrtNo());
           tLCCustomerImpartSchema.setContNo(request.getParameter("ContNo"));
				   tLCCustomerImpartSchema.setCustomerNo(request.getParameter("InsuredNo"));
				   tLCCustomerImpartSchema.setCustomerNoType("1");
				   tLCCustomerImpartSchema.setImpartCode(tImpartCode[i]);
				   tLCCustomerImpartSchema.setImpartContent(tImpartContent[i]);
				   tLCCustomerImpartSchema.setImpartParamModle(tImpartParamModle[i]);
				   tLCCustomerImpartSchema.setImpartVer(tImpartVer[i]) ;
				   tLCCustomerImpartSchema.setMakeDate(theCurrentDate);
				   tLCCustomerImpartSchema.setMakeTime(theCurrentTime);
				   tLCCustomerImpartSchema.setModifyDate(theCurrentDate);
				   tLCCustomerImpartSchema.setModifyTime(theCurrentTime);
				   System.out.println("InsuredNo" + request.getParameter("InsuredNo"));
				   tLCCustomerImpartSet.add(tLCCustomerImpartSchema);
				}
			}			
      System.out.println("end set schema ��֪��Ϣ..."+ImpartCount);
  }
  
  String aImpartNum[] = request.getParameterValues("AppntImpartGridNo");
	String aImpartVer[] = request.getParameterValues("AppntImpartGrid1");            //��֪���
	String aImpartCode[] = request.getParameterValues("AppntImpartGrid2");           //��֪����
	String aImpartContent[] = request.getParameterValues("AppntImpartGrid3");        //��֪����
	String aImpartParamModle[] = request.getParameterValues("AppntImpartGrid4");        //��д����
		//String tImpartCustomerNoType[] = request.getParameterValues("AppntImpartGrid5"); //��֪�ͻ�����
		//String tImpartCustomerNo[] = request.getParameterValues("AppntImpartGrid6");  
  int aImpartCount = 0;

	if (aImpartNum != null) aImpartCount = aImpartNum.length;
	System.out.println(aImpartCount);
	String ReFlag = request.getParameter("ReFlag");
	if(aImpartCount != 0){	  	    
	   for (int i = 0; i < aImpartCount; i++)	{
	      if(aImpartCode[i] == null || aImpartCode[i].equals("") || aImpartContent[i] == null || aImpartContent[i].equals("")
	      || aImpartParamModle[i] == null ||aImpartParamModle[i].equals("")
	      || aImpartVer[i] == null || aImpartVer[i].equals("")){
	      }
	      else{
	         tLCCustomerImpartSchema = new LCCustomerImpartSchema();		
           //tLCCustomerImpartSchema.setProposalContNo(request.getParameter("ContNo"));
           tLCCustomerImpartSchema.setProposalContNo(tLCContDB.getPrtNo());
           tLCCustomerImpartSchema.setContNo(request.getParameter("ContNo"));
				   tLCCustomerImpartSchema.setCustomerNo(request.getParameter("CustomerNo"));
				   if(ReFlag.trim().equals("Yes")){
				     tLCCustomerImpartSchema.setCustomerNoType("1");
				   }
				   else{
				     tLCCustomerImpartSchema.setCustomerNoType("0");
				   }
				   tLCCustomerImpartSchema.setImpartCode(aImpartCode[i]);
				   tLCCustomerImpartSchema.setImpartContent(aImpartContent[i]);
				   tLCCustomerImpartSchema.setImpartParamModle(aImpartParamModle[i]);
				   tLCCustomerImpartSchema.setImpartVer(aImpartVer[i]) ;
				   tLCCustomerImpartSchema.setMakeDate(theCurrentDate);
				   tLCCustomerImpartSchema.setMakeTime(theCurrentTime);
				   tLCCustomerImpartSchema.setModifyDate(theCurrentDate);
				   tLCCustomerImpartSchema.setModifyTime(theCurrentTime);
				   System.out.println("CustomerNo ==" + request.getParameter("CustomerNo"));
				   tLCCustomerImpartSet.add(tLCCustomerImpartSchema);
				}
			}			
      System.out.println("end set schema ��֪��Ϣ..."+aImpartCount);
  }
  
  String tRiskNum[] = request.getParameterValues("LCInsuredGridNo");
  String tRiskCode[] = request.getParameterValues("LCInsuredGrid1");
  String tRiskName[] = request.getParameterValues("LCInsuredGrid2");
  
  

  try 
  {
     // ׼���������� VData
  
  	 VData tVData = new VData();  
  	
	 //������˱�����Ϣ(��ȫ)
	    System.out.println("tLPEdorItemSchema  =========>"+tLPEdorItemSchema.encode());
      System.out.println("tLCCustomerImpartSet =============>" + tLCCustomerImpartSet.encode());	
      tVData.addElement(tG);
      tVData.addElement(tLPEdorItemSchema);
      tVData.addElement(tLCCustomerImpartSet);
//      boolean tag = tEdorDetailUI.submitData(tVData,transact);
      boolean tag = tBusinessDelegate.submitData(tVData,transact,busiName);           
  } 
  catch(Exception ex) 
  {
		  Content = "����ʧ�ܣ�ԭ����:" + ex.toString();
    	FlagStr = "Fail";
	}			
  
    //�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
  if (FlagStr=="") {
    	System.out.println("------success");
//    	tError = tEdorDetailUI.mErrors;
    	tError = tBusinessDelegate.getCErrors(); 
    	if (!tError.needDealError()) {                          
         Content = " ����ɹ�";
    		 FlagStr = "Success";  
    		 System.out.println("==========================================");
    		 try
    		 {
    		   System.out.println("<============TransferData====================>");
    		   TransferData tTransferData = new TransferData();  
    		   VData aVData = new VData();
//    		   aVData = tEdorDetailUI.getResult();  
    		   aVData = tBusinessDelegate.getResult();  
    		   tTransferData = (TransferData) aVData.getObjectByObjectName(
                        "TransferData", 0);
           System.out.println("<============TransferData2====================>");
    		   Result[0] = ((Integer) tTransferData.getValueByName("PayTime")).toString(); 
    		   System.out.println("<============TransferData3====================>");		
    		   Result[1] = ((Double) tTransferData.getValueByName("Prem")).
                            toString();
    		   Result[2] = ((Double) tTransferData.getValueByName("Interest")).
                            toString();
    		   Result[3] = ((Double) tTransferData.getValueByName("Total")).
                            toString();
           System.out.println("<============TransferData4====================>");
           Result[4] = ((Double) tTransferData.getValueByName("AddJK")).
                            toString();
           System.out.println("<============TransferData5====================>");
           Result[5] = ((Double) tTransferData.getValueByName("AddZY")).
                            toString();               
           Result[6] = ((Double) tTransferData.getValueByName("AutoPay")).
                            toString();
           System.out.println(Result[0]);
					 System.out.println(Result[1]);
					 System.out.println(Result[2]);
					 System.out.println(Result[3]);
								
           if (Result == null)
           {
              FlagStr = "Fail";
              Content = "���гɹ�����ִ�н��δ����!";
           }
           else
           {   
              String Str2 = "select polno from ljspayperson where paytype = 'RE' and contno = '" + tLPEdorItemSchema.getContNo() + "' group by polno";
              ExeSQL tes = new ExeSQL();
              SSRS tss = new SSRS();
              tss = tes.execSQL(Str2);
              if(tss.getMaxRow() < 2){                  
                  LMRiskDB tLMRiskDB = new LMRiskDB();
                  LMRiskSet tLMRiskSet = new LMRiskSet();
                  tLMRiskDB.setRiskCode(tRiskCode[0]);
                  tLMRiskSet = tLMRiskDB.query();
                  String tName = tLMRiskSet.get(1).getRiskName();
                  %>                	
                    	tMulArray[0]=new Array();
                    	tMulArray[0][0]="<%=tRiskCode[0]%>";
			                tMulArray[0][1]="<%=tName%>";
                    	tMulArray[0][2]="<%=Result[0]%>";
                    	tMulArray[0][3]="<%=Result[1]%>";
                    	tMulArray[0][4]= "<%=Result[4]%>";
			                tMulArray[0][5]= "<%=Result[5]%>";
                    	tMulArray[0][6]="<%=Result[2]%>";                	
                    	tMulArray[0][7]= "<%=Result[6]%>";
			                tMulArray[0][8]= "<%=0%>";
			                tMulArray[0][9]= "<%=0%>";
			                tMulArray[0][10]="<%=Result[3]%>";
                  <%
                  }
              else{
                  ExeSQL es = new ExeSQL();
                  SSRS ss = new SSRS();
                  System.out.println("Count====" + tss.getMaxRow());
                  for(int a = 1; a <= tss.getMaxRow(); a++){                     String PolNo = tss.GetText(a,1);
                     System.out.println("a:"+a);
                     String Str4 = "select a.riskcode,a.riskname from lmrisk a,lcpol b where a.riskcode = b.riskcode and b.polno = '"+ PolNo + "'";
                     ss.Clear();
                     ss = es.execSQL(Str4);
                     %>

                     tMulArray[<%=a-1%>]=new Array();
                     tMulArray[<%=a-1%>][0]="<%=ss.GetText(1,1)%>";
			               tMulArray[<%=a-1%>][1]="<%=ss.GetText(1,2)%>";
			               tMulArray[<%=a-1%>][2]="<%=Result[0]%>";
			               <%
			               String Str3 = "select sum(sumactupaymoney) from ljspayperson where polno = '" + PolNo + "' and substr(payplancode, 0, 6) <> '000000'";
			               ss.Clear();
			               ss = es.execSQL(Str3);
			               %>
                     tMulArray[<%=a-1%>][3] = "<%=ss.GetText(1,1)%>";
                     <%
                     //�����ӷ�
                     String Str5 = "select nvl(sum(getmoney),0) from ljsgetendorse where SubFeeOperationType like '%P004%' and polno = '" + PolNo + "' and endorsementno = '" + EdorNo + "'";
                     ss.Clear();
			               ss = es.execSQL(Str5);
                     %>
                     tMulArray[<%=a-1%>][4] = "<%=ss.GetText(1,1)%>";
                     <%
                     //ְҵ�ӷ�
                     String Str6 = "select nvl(sum(getmoney),0) from ljsgetendorse where SubFeeOperationType like '%P005%' and polno = '" + PolNo + "' and endorsementno = '" + EdorNo + "'";
                     ss.Clear();
			               ss = es.execSQL(Str6);
                     %>
			               tMulArray[<%=a-1%>][5] = "<%=ss.GetText(1,1)%>";
			               <%
			               //��Ϣ
                     String Str7 = "select sum(getmoney) from ljsgetendorse where SubFeeOperationType like '%P007%' and polno = '" + PolNo + "' and endorsementno = '" + EdorNo + "'";
                     ss.Clear();
			               ss = es.execSQL(Str7);
			               %>
                     tMulArray[<%=a-1%>][6] = "<%=ss.GetText(1,1)%>";                	
                     tMulArray[<%=a-1%>][7] = "<%=Result[6]%>";
			               tMulArray[<%=a-1%>][8] = "<%=0%>";
			               tMulArray[<%=a-1%>][9] = "<%=0%>";
			               <%
			               String Str8 = "select sum(getmoney) from ljsgetendorse where polno = '" + PolNo + "' and endorsementno = '" + EdorNo + "'";
                     ss.Clear();
			               ss = es.execSQL(Str8);
			               %>
			               tMulArray[<%=a-1%>][10]= "<%=ss.GetText(1,1)%>";
                     <%            

                  }
              }
           }
			   }
			   catch(Exception e){
			     System.out.println("Error");
			   }
    		
    		
    	 }
    	 else
    	 {
    		 Content = " ����ʧ�ܣ�ԭ����:" + tError.getFirstError();
    		 FlagStr = "Fail";
    	 }
  	}
%>  
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>",tMulArray);
</script>
</html>

