<%
//�������ƣ�PEdorSave.jsp
//�����ܣ�
//�������ڣ�2002-07-19 16:49:22
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.sys.*"%>
  <%@page import="com.sinosoft.lis.bq.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@page contentType="text/html;charset=GBK" %>

<%
  LPEdorAppSchema tLPEdorAppSchema=new LPEdorAppSchema();
  
//  tLPEdorAppSchema.setEdorAcceptNo(request.getParameter("EdorAcceptNo")); //��ȫ�����
    tLPEdorAppSchema.setOtherNo(request.getParameter("OtherNo")); //�������
    tLPEdorAppSchema.setOtherNoType(request.getParameter("OtherNoType")); //�����������
    tLPEdorAppSchema.setEdorAppName(request.getParameter("EdorAppName")); //����������
    tLPEdorAppSchema.setAppType(request.getParameter("AppType")); //���뷽ʽ
    tLPEdorAppSchema.setManageCom(request.getParameter("ManageCom")); //�������
//    tLPEdorAppSchema.setChgPrem(request.getParameter("ChgPrem")); //�䶯�ı���
//    tLPEdorAppSchema.setChgAmnt(request.getParameter("ChgAmnt")); //�䶯�ı���
//    tLPEdorAppSchema.setChgGetAmnt(request.getParameter("ChgGetAmnt")); //�䶯����ȡ����
//    tLPEdorAppSchema.setGetMoney(request.getParameter("GetMoney")); //��/�˷ѽ��
//    tLPEdorAppSchema.setGetInterest(request.getParameter("GetInterest")); //��/�˷���Ϣ
    tLPEdorAppSchema.setEdorAppDate(request.getParameter("EdorAppDate")); //���Ĺ�����������
//    tLPEdorAppSchema.setEdorState(request.getParameter("EdorState")); //����״̬
//    tLPEdorAppSchema.setBankCode(request.getParameter("BankCode")); //���б���
//    tLPEdorAppSchema.setBankAccNo(request.getParameter("BankAccNo")); //�����ʺ�
//    tLPEdorAppSchema.setAccName(request.getParameter("AccName")); //�����ʻ���


  //����������Ϣ
  LPEdorMainSchema tLPEdorMainSchema = new LPEdorMainSchema();
  LPEdorMainSet tLPEdorMainSet = new LPEdorMainSet();
  
  
 
 //   tLPEdorMainSchema.setEdorNo(request.getParameter("EdorNo")); //������
    tLPEdorMainSchema.setContNo(request.getParameter("ContNo")); //��ͬ����
    tLPEdorMainSchema.setEdorAppNo(request.getParameter("EdorNo")); //���������
    tLPEdorMainSchema.setManageCom(request.getParameter("ManageCom")); //�������
    tLPEdorMainSchema.setEdorAppDate(request.getParameter("EdorAppDate")); //���Ĺ�����������
    tLPEdorMainSchema.setEdorValiDate(request.getParameter("EdorValiDate")); //������Ч����
    
    tLPEdorMainSet.add(tLPEdorMainSchema);
  
  //���˱�����Ϣ
  LCContSchema tLCContSchema = new LCContSchema();
  LCContSet tLCContSet = new LCContSet();

  PEdorMainUI tPEdorMainUI   = new PEdorMainUI();
 	CheckFieldUI tCheckFieldUI = new CheckFieldUI();

  GlobalInput tG = new GlobalInput();
  tG = (GlobalInput)session.getValue("GI");
  
  System.out.println("-----"+tG.Operator);
  System.out.println("-----"+tG.ManageCom);
  
  //�������
  CErrors tError = null;   
  String tRela  = "";                
  String FlagStr = "";
  String Content = "";
  String transact = "";
  String Result = "";
  String strEdorType = "";
  
  transact = request.getParameter("fmtransact");
  System.out.println("------transact:"+transact);
 
  
	
	////��Կ��Լ���Ϊ0 �ı�ȫ��Ŀ�������Ƿ��޸ĵı�ǡ�
	//strEdorType = request.getParameter("EdorType");
	//if(strEdorType.equals("BC") || strEdorType.equals("SC"))
	//{
	//   System.out.println("==> Make Flag of Chenged,Flag=" + strEdorType + "NOC0");   
	//   tLPEdorMainSchema.setCalCode(strEdorType+"NOC0");
	//}
	//
	//tLPEdorMainSet.add(tLPEdorMainSchema);

    try
    {  
  	    VData tVCheckData = new VData();   
        
        tVCheckData.addElement(tG);
  	    tVCheckData.addElement(tLPEdorMainSchema);
  	    tVCheckData.addElement("VERIFY||BEGIN");
  	    tVCheckData.addElement("PEDORINPUT#EDORTYPE");
  	    
   	    //if (tCheckFieldUI.submitData(tVCheckData,"CHECK||FIELD")) 
   	    //{ 	    
   	    //    if (tCheckFieldUI.getResult()!=null&&tCheckFieldUI.getResult().size()>0) 
   	    //    {
       	//	    for (int i=1;i<=tCheckFieldUI.getResult().size();i++) 
       	//	    {
       	//	        Result = Result + (String)tCheckFieldUI.getResult().get(i)+"\n";
       	//	    }
   	    //    }
   						
  	        VData tVData = new VData();  
  	    
  	        //������˱�����Ϣ(��ȫ)
  	        tVData.addElement(tLPEdorAppSchema);			 
  	        tVData.addElement(tLPEdorMainSet);
  	        tVData.addElement(tG);
  	    
  	        if (tPEdorMainUI.submitData(tVData,transact)) 
  	        {		
        	    if (transact.equals("INSERT||EDOR")) 
        	    {        			    	
        	        tVData.clear();
        	        tVData = tPEdorMainUI.getResult();
        	                		    	
        	        LPEdorMainSchema mLPEdorMainSchema = new LPEdorMainSchema(); 
        	        LPEdorMainSet mLPEdorMainSet=new LPEdorMainSet();
        	        mLPEdorMainSet=(LPEdorMainSet)tVData.getObjectByObjectName("LPEdorMainSet",0);
        	        mLPEdorMainSchema=mLPEdorMainSet.get(1);
        	        %>
        	        <SCRIPT language="javascript">
        	    	    parent.fraInterface.fm.EdorNo.value="<%=mLPEdorMainSchema.getEdorNo()%>";
                        parent.fraInterface.fm.EdorAcceptNo.value="<%=mLPEdorMainSchema.getEdorAcceptNo()%>";
        	        </SCRIPT>
        	        <%	
        	    }
            }  
     //   }
    } 
    catch(Exception ex) 
    {
      Content = transact+"ʧ�ܣ�ԭ����:" + ex.toString();
      FlagStr = "Fail";
    }			
  
  //�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
 	tError = tCheckFieldUI.mErrors;
 	
 	if (tError.getErrType().equals(CError.TYPE_NONEERR))
 	{
 		tError = tPEdorMainUI.mErrors;
 	}
    if (tError.getErrType().equals(CError.TYPE_NONEERR))
    {                          
        Content = " ����ɹ�" + ":"+ Result.trim();
        FlagStr = "Success";
    }
  
    if (FlagStr.equals(""))
    {
        String ErrorContent = tError.getErrContent();  
        
        if (tError.getErrType().equals(CError.TYPE_ALLOW)) 
        {
            Content = " ����ɹ������ǣ�" + ErrorContent;
            FlagStr = "Success";
        }
        else 
        {
        	Content = "����ʧ�ܣ�ԭ���ǣ�" + ErrorContent;
        	FlagStr = "Fail";
        }
    
        Content = PubFun.changForHTML(Content);
    }

  //��Ӹ���Ԥ����
  //System.out.println("End here "+tError.getErrContent());
%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>", "<%=Content%>");
</script>
</html>

