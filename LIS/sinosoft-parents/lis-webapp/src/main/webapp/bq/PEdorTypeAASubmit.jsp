<%
/*******************************************************************************
 * <p>Title: Lis 6.5</p>
 * <p>Description: �п������ٱ��պ���ҵ�����ϵͳ</p>
 * <p>Copyright: Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
 * <p>Company: �п���Ƽ��ɷ����޹�˾</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : pst <pengst@sinosoft.com.cn>
 * @version  : 1.00, 
 * @date     : 2008-11-26
 * @direction: �����ռӱ�
 ******************************************************************************/
%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<!--�û�У����-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.bq.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.service.*" %>
<%
    //������Ϣ������У�鴦��
    System.out.println("-----Detail submit---");                 
    String FlagStr = "";
    String Content = "";
    String transact = "";
    String Result="";  
    GlobalInput tG = new GlobalInput();
    tG = (GlobalInput)session.getValue("GI");
    transact=(String)request.getParameter("fmtransact");
    

             
    LPEdorItemSchema tLPEdorItemSchema   = new LPEdorItemSchema(); 
    LPDutySchema tLPDutySchema = new LPDutySchema();
    LCCustomerImpartSet tLCCustomerImpartSet = new LCCustomerImpartSet(); 
    tLPEdorItemSchema.setPolNo("000000");
	tLPEdorItemSchema.setContNo(request.getParameter("ContNo"));
	tLPEdorItemSchema.setInsuredNo("000000");
	tLPEdorItemSchema.setEdorAcceptNo(request.getParameter("EdorAcceptNo"));
    tLPEdorItemSchema.setEdorNo(request.getParameter("EdorNo"));
	tLPEdorItemSchema.setEdorType(request.getParameter("EdorType")); 
	  
	  if(transact.trim().equals("DELETE")){
//	      PEdorAADetailUI tPEdorAADetailUI = new PEdorAADetailUI();
	 String busiName="tPEdorAADetailUI";
	 BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	      try{
	          
	        VData tVData = new VData();              
            tVData.addElement(tG);	 	                
            tVData.addElement(tLPEdorItemSchema);    
            tVData.addElement(tLPDutySchema);	      
//            tPEdorAADetailUI.submitData(tVData, transact);
            tBusinessDelegate.submitData(tVData, transact,busiName);
	      }   
	      catch(Exception ex) {
            Content = "��ԭʧ�ܣ�ԭ����:" + ex.toString();
            FlagStr = "Fail";
	      }	
	      //�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
        if (FlagStr=="") 
        {
            CErrors tError = new CErrors(); 
//            tError = tPEdorAADetailUI.mErrors;
            tError = tBusinessDelegate.getCErrors();    
            if (!tError.needDealError()) 
            {                          
                Content = " ��ԭ�ɹ�";
        	      FlagStr = "Success";
        	}
            else
            {
                FlagStr = "Fail";
                Content = " ��ԭʧ�ܣ�ԭ����:" + tError.getFirstError();
            }
        }      
	  }
	  else
	  {   
//         EdorDetailUI tEdorDetailUI = new EdorDetailUI();
         String busiName="EdorDetailUI";
   		 BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
         
         String tAAMoney = request.getParameter("AAMoney");
         
         String theCurrentDate = PubFun.getCurrentDate();
         String theCurrentTime = PubFun.getCurrentTime();
         
         String tChk[] = request.getParameterValues("InpPolGridSel"); 
         String tRiskCode[] = request.getParameterValues("PolGrid1");   
         String tPolNo[] = request.getParameterValues("PolGrid8");
         String tAmntFlag[] = request.getParameterValues("PolGrid10");;  
            
         System.out.println("tChk.length =========>"+tChk.length);
         System.out.println("tChk.length =========>"+tChk[0]);
         for(int index=0;index<tChk.length;index++)
         {
            System.out.println(tChk[index]);
            if(tChk[index].equals("1"))
            {       
                System.out.println(index);
                System.out.println("PolNo ========>" + tPolNo[index]);
                System.out.println("AmntFlag ========>" + tAmntFlag[index]);
                System.out.println(tAAMoney);
                if(tAAMoney.substring(tAAMoney.length()-1,tAAMoney.length()).equals("f")||
                   tAAMoney.substring(tAAMoney.length()-1,tAAMoney.length()).equals("F")||
                   tAAMoney.substring(tAAMoney.length()-1,tAAMoney.length()).equals("d")||
                   tAAMoney.substring(tAAMoney.length()-1,tAAMoney.length()).equals("D")){
                   Content = "�������ݲ����Ϲ淶!";
                   FlagStr = "Fail";
                }
                tLPDutySchema.setPolNo(tPolNo[index]);
                if(tAmntFlag[index].equals("1")){
                   tLPDutySchema.setAmnt(tAAMoney);
                }
                else{	  
                   tLPDutySchema.setMult(tAAMoney);
                }   
                break;
            }           
         }
          
         if(FlagStr=="")
         {           	           
	           System.out.println("tLPEdorItemSchema ==============>"+tLPEdorItemSchema.encode());
	           System.out.println("tLPDutySchema ================>"+tLPDutySchema.encode());
	           System.out.println("tLCCustomerImpartSet ===============>"+tLCCustomerImpartSet.encode());       
             
             try 
             {
                 // ׼���������� VData
                VData tVData = new VData();  
                tVData.addElement(tG);	 	
                tVData.addElement(tLPEdorItemSchema);
                tVData.addElement(tLPDutySchema);	
                //������˱�����Ϣ(��ȫ)	
//                tEdorDetailUI.submitData(tVData, transact);
                tBusinessDelegate.submitData(tVData, transact,busiName);
             }
             catch(Exception ex) 
             {
                Content = "����ʧ�ܣ�ԭ����:" + ex.toString();
                FlagStr = "Fail";
             } 
         }
         //�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
         if (FlagStr=="") 
         {
             CErrors tError = new CErrors(); 
//             tError = tEdorDetailUI.mErrors; 
             tError = tBusinessDelegate.getCErrors();   
             if (!tError.needDealError()) 
             {                          
                 Content = " ����ɹ�";
           	     FlagStr = "Success";
           	 }
             else
             {
                 FlagStr = "Fail";
                 Content = " ����ʧ�ܣ�ԭ����:" + tError.getFirstError();
             }
         }
    }
%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>", "<%=Content%>", "<%=Result%>");
</script>
</html>

