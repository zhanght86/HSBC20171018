<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%
//�������ƣ�LDPersonSave.jsp
//�����ܣ�
//�������ڣ�2002-06-27 08:49:52
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
//    		zhangtao	2005-04-29  
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.vdb.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.service.*" %>
<%
  //������Ϣ������У�鴦��
  //�������
  
  LPEdorItemSet mLPEdorItemSet =new LPEdorItemSet();
     
  TransferData tTransferData = new TransferData(); 
  String mCurrentDate = PubFun.getCurrentDate();
  //�������
  String FlagStr = "";
  CErrors tError = null;
  String Content = "";
  boolean fieldChecked=true;
 
  GlobalInput tGI = new GlobalInput(); //repair:
  tGI=(GlobalInput)session.getValue("GI");  //�μ�loginSubmit.jsp

    //����Ҫִ�еĶ�������ӣ��޸ģ�ɾ��
    String fmAction    = request.getParameter("fmtransact");
    String displayType = request.getParameter("DisplayType");
    String MissionID = request.getParameter("MissionID");
    String SubMissionID     = request.getParameter("SubMissionID");
    String LoadFlag = request.getParameter("LoadFlag");
    
    tTransferData.setNameAndValue("DisplayType", displayType);
    tTransferData.setNameAndValue("MissionID", MissionID);
    tTransferData.setNameAndValue("SubMissionID", SubMissionID);
    tTransferData.setNameAndValue("LoadFlag", LoadFlag);

    if(fmAction.equals("INSERT||EDORITEM"))
    {
		if (displayType.equals("1"))
        {
        	//������
			LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
			tLPEdorItemSchema.setEdorAcceptNo(request.getParameter("EdorAcceptNo"));
			tLPEdorItemSchema.setDisplayType(displayType); 
			tLPEdorItemSchema.setEdorType(request.getParameter("EdorType"));
			tLPEdorItemSchema.setEdorAppDate(request.getParameter("EdorItemAppDate"));
			//tLPEdorItemSchema.setEdorValiDate(request.getParameter("EdorValiDate"));
    		tLPEdorItemSchema.setAppReason(request.getParameter("AppReason"));
    		tLPEdorItemSchema.setMakeDate(mCurrentDate);
    		
			tLPEdorItemSchema.setInsuredNo("000000");
			tLPEdorItemSchema.setPolNo("000000");
    		
			mLPEdorItemSet.add(tLPEdorItemSchema); 
		}           
		else if (displayType.equals("2"))
		{
			//�ͻ���
			LPEdorItemSchema tLPEdorItemSchema;
            String tInsuredNo[] = request.getParameterValues("InsuredGrid2");	//�������˺��� 
            String tContNo[] = request.getParameterValues("InsuredGrid8");		//��������                                                                                       
            String tGrpContNo[] = request.getParameterValues("InsuredGrid9");	//���屣������
            
            String tChk[] = request.getParameterValues("InpInsuredGridSel"); 
            
            for(int index = 0; index < tChk.length; index++)
            {
                if(tChk[index].equals("1"))
                {
                    tLPEdorItemSchema = new LPEdorItemSchema();
                    tLPEdorItemSchema.setEdorAcceptNo(request.getParameter("EdorAcceptNo"));
                    tLPEdorItemSchema.setDisplayType(displayType); 
                    tLPEdorItemSchema.setEdorType(request.getParameter("EdorType")); 
                    tLPEdorItemSchema.setEdorAppDate(request.getParameter("EdorItemAppDate"));
                    //tLPEdorItemSchema.setEdorValiDate(request.getParameter("EdorValiDate"));
                    tLPEdorItemSchema.setAppReason(request.getParameter("AppReason"));
            		tLPEdorItemSchema.setMakeDate(mCurrentDate);
                    tLPEdorItemSchema.setContNo(tContNo[index]); 
                    tLPEdorItemSchema.setGrpContNo(tGrpContNo[index]); 
                    tLPEdorItemSchema.setInsuredNo(tInsuredNo[index]);
                    tLPEdorItemSchema.setPolNo("000000");
                    
                    mLPEdorItemSet.add(tLPEdorItemSchema);
                }           
            }            
            
         }  
         else if (displayType.equals("3"))
         {
			//���ּ�
			LPEdorItemSchema tLPEdorItemSchema;
            String tPolNo[] = request.getParameterValues("PolGrid2");		//���ֱ�����            
            String tInsuredNo[] = request.getParameterValues("PolGrid3");	//�����˺��� 
            String tContNo[] = request.getParameterValues("PolGrid9");		//��������
            String tGrpContNo[] = request.getParameterValues("PolGrid10"); //���屣������
                        
			String tChk[] = request.getParameterValues("InpPolGridChk"); 
            for(int index = 0; index < tChk.length; index++)
            {
                if(tChk[index].equals("1"))
                {
                    tLPEdorItemSchema = new LPEdorItemSchema();
                    tLPEdorItemSchema.setEdorAcceptNo(request.getParameter("EdorAcceptNo"));
                    tLPEdorItemSchema.setDisplayType(displayType);  
                    tLPEdorItemSchema.setEdorType(request.getParameter("EdorType"));
                    tLPEdorItemSchema.setEdorAppDate(request.getParameter("EdorItemAppDate"));
                    //tLPEdorItemSchema.setEdorValiDate(request.getParameter("EdorValiDate"));
                    tLPEdorItemSchema.setAppReason(request.getParameter("AppReason"));
            		tLPEdorItemSchema.setMakeDate(mCurrentDate); 
                    tLPEdorItemSchema.setContNo(tContNo[index]); 
                    tLPEdorItemSchema.setGrpContNo(tGrpContNo[index]);
                    tLPEdorItemSchema.setInsuredNo(tInsuredNo[index]);
                    tLPEdorItemSchema.setPolNo(tPolNo[index]);
                    
                    mLPEdorItemSet.add(tLPEdorItemSchema);
                }           
            }              
         }            
    }       
   
	// ׼���������� VData
	  VData tVData = new VData();
 
	  tVData.add(mLPEdorItemSet);
    tVData.add(tTransferData);
    tVData.add(tGI);
    String busiName="PEdorAppItemUI";
    BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
    if(!tBusinessDelegate.submitData(tVData,fmAction,busiName))
    {    
        if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
        {  
				   Content = "���ʧ�ܣ�ԭ����:" + tBusinessDelegate.getCErrors().getFirstError();
				   FlagStr = "Fail";
				}
				else
				{
				   Content = "���ʧ��";
				   FlagStr = "Fail";				
				}		
	 
	 }
	 else
	 {
				  Content ="��ӳɹ���";
		    	FlagStr = "Succ";	
	 }	 	


//ҳ����Ч��
%>                                       
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>", "<%=Content%>");
</script>
</html>

