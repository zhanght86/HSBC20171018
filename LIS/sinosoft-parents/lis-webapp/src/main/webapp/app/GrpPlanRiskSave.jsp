<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//�������ƣ�GrpPlanRiskSave.jsp
//�����ܣ�
//�������ڣ�2006-11-22 17:39:52
//������  ��Chenrong
//���¼�¼��  ������    ��������     ����ԭ��/����
//      
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.vdb.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.service.*" %>
<%
    //������Ϣ������У�鴦��
    //�������
    
    LCGrpContSchema tLCGrpContSchema   = new LCGrpContSchema();
    LCGrpPolSet mLCGrpPolSet =new LCGrpPolSet();
    
    TransferData tTransferData = new TransferData(); 
    //GrpPlanRiskUI tGrpPlanRiskUI   = new GrpPlanRiskUI();
     String busiName="tbGrpPlanRiskUI";
     BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
    //�������
    String FlagStr = "";
    String Content = ""; 
    
    GlobalInput tGI = new GlobalInput(); //repair:
    tGI=(GlobalInput)session.getValue("GI");  //�μ�loginSubmit.jsp
    loggerDebug("GrpPlanRiskSave","tGI"+tGI);
    if(tGI==null)
    {
        loggerDebug("GrpPlanRiskSave","ҳ��ʧЧ,�����µ�½");   
        FlagStr = "Fail";        
        Content = "ҳ��ʧЧ,�����µ�½";  
    }
    else //ҳ����Ч
    {
        String Operator  = tGI.Operator ;  //�����½����Ա�˺�
        String ManageCom = tGI.ComCode  ; //�����½��վ,ManageCom=�ڴ��е�½��վ����
      
        CErrors tError = null;
        String fmAction=request.getParameter("fmAction");

        tLCGrpContSchema.setGrpContNo(request.getParameter("GrpContNo"));
        tLCGrpContSchema.setPrtNo(request.getParameter("PrtNo"));
        tLCGrpContSchema.setMult(request.getParameter("Mult")); //��LCGrpContSchema�ݴ����
        
        loggerDebug("GrpPlanRiskSave","����¼����Ʒ���¼��->������ʽ��" + fmAction);
        LDPlanSchema tLDPlanSchema = new LDPlanSchema();
        if(fmAction.equals("INSERT||PLANRISK"))
        {
            tLDPlanSchema.setContPlanCode(request.getParameter("PlanRiskCode"));
        }
        
        if(fmAction.equals("DELETE||PLANRISK"))
        {
            String tRiskNum[] = request.getParameterValues("PlanGridNo");
    		String tContPlanCode[] = request.getParameterValues("PlanGrid1");            //���ֱ���
    		String tRadio[] = request.getParameterValues("InpPlanGridSel"); 
    		loggerDebug("GrpPlanRiskSave","tRadio.length"+tRadio.length);
            for(int index=0;index<tRadio.length;index++)
            {
                if(tRadio[index].equals("1"))
                {
                    tLDPlanSchema.setContPlanCode(tContPlanCode[index]);              
                }           
            }                   
        }    
        
        try
        {
            // ׼���������� VData
            VData tVData = new VData();
            
            tVData.add(tLDPlanSchema);
            tVData.add(tLCGrpContSchema);
            tVData.add(tTransferData);
            tVData.add(tGI);            
             
              
             //ִ�ж�����insert ��Ӽ�¼��update �޸ļ�¼��delete ɾ����¼
            if (tBusinessDelegate.submitData(tVData,fmAction,busiName))
            {
    		    if (fmAction.equals("INSERT||PLANRISK"))
		        {
		    	    loggerDebug("GrpPlanRiskSave","�ŵ���Ʒ���¼��");			        	
		    	    tVData.clear();
		    	    tVData = tBusinessDelegate.getResult();		    	    
		    	    LCContPlanSchema tLCContPlanSchema = new LCContPlanSchema();
		    	    tLCContPlanSchema=(LCContPlanSchema)tVData.getObjectByObjectName("LCContPlanSchema",0);
		            %>
    		            <SCRIPT language="javascript">
    		           
    		            	parent.fraInterface.PlanGrid.addOne("PlanGrid");     
                            parent.fraInterface.PlanGrid.setRowColData(parent.fraInterface.PlanGrid.mulLineCount-1,1,"<%=tLCContPlanSchema.getContPlanCode()%>");
                            parent.fraInterface.PlanGrid.setRowColData(parent.fraInterface.PlanGrid.mulLineCount-1,2,"<%=tLCContPlanSchema.getContPlanName()%>");
                            parent.fraInterface.PlanGrid.setRowColData(parent.fraInterface.PlanGrid.mulLineCount-1,3,"0");
                            parent.fraInterface.PlanGrid.setRowColData(parent.fraInterface.PlanGrid.mulLineCount-1,4,"0");
                            parent.fraInterface.PlanGrid.setRowColData(parent.fraInterface.PlanGrid.mulLineCount-1,5,"0");
                            parent.fraInterface.PlanGrid.setRowColData(parent.fraInterface.PlanGrid.mulLineCount-1,6,"0");
                           
    
    		            </SCRIPT>
		            <%
		        }
		        else if (fmAction.equals("DELETE||PLANRISK"))
		        {
		             %>
    		            <SCRIPT language="javascript">
    		                parent.fraInterface.PlanGrid.delRadioTrueLine();    
    		            </SCRIPT>
		            <%
		        }
	        }
        }
    
        catch(Exception ex)
        {
            Content = "����ʧ�ܣ�ԭ����:" + ex.toString();
            loggerDebug("GrpPlanRiskSave","aaaa"+ex.toString());
            FlagStr = "Fail";
        }
  

        //�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
        if (FlagStr=="")
        {
            tError = tBusinessDelegate.getCErrors();
            if (!tError.needDealError())
            {                          
                Content ="����ɹ���";
                FlagStr = "Succ";
            }
            else                                                                           
            {
                Content = "����ʧ�ܣ�ԭ����:" + tError.getFirstError();
                FlagStr = "Fail";
            }
        }
 
    } //ҳ����Ч��
%>                                       
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
	parent.fraInterface.initPlanRiskGrid(); 
	parent.fraInterface.document.all('Mult').value="";	
	parent.fraInterface.document.all('PlanRiskCode').value="";
	parent.fraInterface.document.all('PlanRiskCodeName').value="";
</script>
</html>

