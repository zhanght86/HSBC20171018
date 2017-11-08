<%
/*******************************************************************************
 * <p>Title: Lis 6.5</p>
 * <p>Description: 中科软人寿保险核心业务管理系统</p>
 * <p>Copyright: Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
 * <p>Company: 中科软科技股份有限公司</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : pst <pengst@sinosoft.com.cn>
 * @version  : 1.00, 
 * @date     : 2008-11-26
 * @direction: 附加险加保
 ******************************************************************************/
%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<!--用户校验类-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.bq.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.service.*" %>
<%
    //接收信息，并作校验处理
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
            Content = "还原失败，原因是:" + ex.toString();
            FlagStr = "Fail";
	      }	
	      //如果在Catch中发现异常，则不从错误类中提取错误信息
        if (FlagStr=="") 
        {
            CErrors tError = new CErrors(); 
//            tError = tPEdorAADetailUI.mErrors;
            tError = tBusinessDelegate.getCErrors();    
            if (!tError.needDealError()) 
            {                          
                Content = " 还原成功";
        	      FlagStr = "Success";
        	}
            else
            {
                FlagStr = "Fail";
                Content = " 还原失败，原因是:" + tError.getFirstError();
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
                   Content = "传入数据不符合规范!";
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
                 // 准备传输数据 VData
                VData tVData = new VData();  
                tVData.addElement(tG);	 	
                tVData.addElement(tLPEdorItemSchema);
                tVData.addElement(tLPDutySchema);	
                //保存个人保单信息(保全)	
//                tEdorDetailUI.submitData(tVData, transact);
                tBusinessDelegate.submitData(tVData, transact,busiName);
             }
             catch(Exception ex) 
             {
                Content = "保存失败，原因是:" + ex.toString();
                FlagStr = "Fail";
             } 
         }
         //如果在Catch中发现异常，则不从错误类中提取错误信息
         if (FlagStr=="") 
         {
             CErrors tError = new CErrors(); 
//             tError = tEdorDetailUI.mErrors; 
             tError = tBusinessDelegate.getCErrors();   
             if (!tError.needDealError()) 
             {                          
                 Content = " 保存成功";
           	     FlagStr = "Success";
           	 }
             else
             {
                 FlagStr = "Fail";
                 Content = " 保存失败，原因是:" + tError.getFirstError();
             }
         }
    }
%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>", "<%=Content%>", "<%=Result%>");
</script>
</html>

