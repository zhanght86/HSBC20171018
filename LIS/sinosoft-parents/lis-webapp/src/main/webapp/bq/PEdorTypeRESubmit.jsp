<%
//程序名称：PEdorTypePCSubmit.jsp
//程序功能：6
//创建日期：2005-6-28 10:47上午
//创建人  ：Lizhuo
//更新记录：  更新人    更新日期     更新原因/内容
%>

  
<!--用户校验类-->

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
 //接收信息，并作校验处理。
  //输入参数
  //个人批改信息
  

  LPEdorItemSchema tLPEdorItemSchema   = new LPEdorItemSchema();
//  EdorDetailUI tEdorDetailUI = new EdorDetailUI();
	 String busiName="EdorDetailUI";
	 BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  
  CErrors tError = null;
  //后面要执行的动作：添加，修改
 
  String tRela  = "";                
  String FlagStr = "";
  String Content = "";
  String transact = "";
  String[] Result = new String[8];
  
  //执行动作：insert 添加纪录，update 修改纪录，delete 删除纪录
  transact = request.getParameter("fmtransact");
  System.out.println("------transact:"+transact);
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput)session.getValue("GI");
  
  //个人保单批改信息
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
  
  //借用standbyflag3来保存是否计息（应用于应交未交，借款，垫款）
  tLPEdorItemSchema.setStandbyFlag3(tisCalInterest);
  
  String tImpartNum[] = request.getParameterValues("ImpartGridNo");
	String tImpartVer[] = request.getParameterValues("ImpartGrid1");            //告知版别
	String tImpartCode[] = request.getParameterValues("ImpartGrid2");           //告知编码
	String tImpartContent[] = request.getParameterValues("ImpartGrid3");        //告知内容
	String tImpartParamModle[] = request.getParameterValues("ImpartGrid4");        //填写内容
		//String tImpartCustomerNoType[] = request.getParameterValues("ImpartGrid5"); //告知客户类型
		//String tImpartCustomerNo[] = request.getParameterValues("ImpartGrid6");  
  int ImpartCount = 0;
  
    //add by jiaqiangli 2009-02-18 LCCustomerImpart.ProposalContNo = lccont.prtno 参照新契约置法
	LCContDB tLCContDB = new LCContDB();
	tLCContDB.setContNo(request.getParameter("ContNo"));
	tLCContDB.getInfo();
	//add by jiaqiangli 2009-02-18 LCCustomerImpart.ProposalContNo = lccont.prtno 参照新契约置法

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
      System.out.println("end set schema 告知信息..."+ImpartCount);
  }
  
  String aImpartNum[] = request.getParameterValues("AppntImpartGridNo");
	String aImpartVer[] = request.getParameterValues("AppntImpartGrid1");            //告知版别
	String aImpartCode[] = request.getParameterValues("AppntImpartGrid2");           //告知编码
	String aImpartContent[] = request.getParameterValues("AppntImpartGrid3");        //告知内容
	String aImpartParamModle[] = request.getParameterValues("AppntImpartGrid4");        //填写内容
		//String tImpartCustomerNoType[] = request.getParameterValues("AppntImpartGrid5"); //告知客户类型
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
      System.out.println("end set schema 告知信息..."+aImpartCount);
  }
  
  String tRiskNum[] = request.getParameterValues("LCInsuredGridNo");
  String tRiskCode[] = request.getParameterValues("LCInsuredGrid1");
  String tRiskName[] = request.getParameterValues("LCInsuredGrid2");
  
  

  try 
  {
     // 准备传输数据 VData
  
  	 VData tVData = new VData();  
  	
	 //保存个人保单信息(保全)
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
		  Content = "保存失败，原因是:" + ex.toString();
    	FlagStr = "Fail";
	}			
  
    //如果在Catch中发现异常，则不从错误类中提取错误信息
  if (FlagStr=="") {
    	System.out.println("------success");
//    	tError = tEdorDetailUI.mErrors;
    	tError = tBusinessDelegate.getCErrors(); 
    	if (!tError.needDealError()) {                          
         Content = " 保存成功";
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
              Content = "运行成功，但执行结果未返回!";
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
                     //健康加费
                     String Str5 = "select nvl(sum(getmoney),0) from ljsgetendorse where SubFeeOperationType like '%P004%' and polno = '" + PolNo + "' and endorsementno = '" + EdorNo + "'";
                     ss.Clear();
			               ss = es.execSQL(Str5);
                     %>
                     tMulArray[<%=a-1%>][4] = "<%=ss.GetText(1,1)%>";
                     <%
                     //职业加费
                     String Str6 = "select nvl(sum(getmoney),0) from ljsgetendorse where SubFeeOperationType like '%P005%' and polno = '" + PolNo + "' and endorsementno = '" + EdorNo + "'";
                     ss.Clear();
			               ss = es.execSQL(Str6);
                     %>
			               tMulArray[<%=a-1%>][5] = "<%=ss.GetText(1,1)%>";
			               <%
			               //利息
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
    		 Content = " 保存失败，原因是:" + tError.getFirstError();
    		 FlagStr = "Fail";
    	 }
  	}
%>  
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>",tMulArray);
</script>
</html>

