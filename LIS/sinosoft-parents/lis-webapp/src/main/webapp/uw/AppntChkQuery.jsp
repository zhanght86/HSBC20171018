<%@page contentType="text/html;charset=GBK"%> 
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�AppntChkQuery.jsp
//�����ܣ�Ͷ���˲���
//�������ڣ�2002-09-24 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����
%> 
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
<%
//1-�õ����м�¼����ʾ��¼ֵ
  int index=0;
  int TempCount=0;
  //�������
  CErrors tError = null;
  String FlagStr = "Fail";
  String Content = "";
  LCContSchema tLCContSchema = new LCContSchema();
  LCAppntSet tLCAppntSet = new LCAppntSet();
  LCInsuredSet tLCInsuredSet = new LCInsuredSet();
  LDPersonSet tLDPersonSet = new LDPersonSet();
  
  //�õ�radio�е�����
  loggerDebug("AppntChkQuery","---4----");  
  	GlobalInput tG = new GlobalInput();
  
	tG=(GlobalInput)session.getValue("GI");
  
  	if(tG == null) {
		out.println("session has expired");
		return;
	}
  String tLoadFlag = request.getParameter("LoadFlag");
  String tProposalNo = request.getParameter("ProposalNo");
  String tInsuredNo="";  
  String tFlag = request.getParameter("Flag");
  if(tFlag.equals("I"))  
  {
  tInsuredNo=request.getParameter("InsuredNo");  
  }
  //������ 
try
{  
  if(tProposalNo!=null&&tFlag!=null) //������ǿռ�¼	
  {
  		loggerDebug("AppntChkQuery","----3-----");
		loggerDebug("AppntChkQuery","Contno="+tProposalNo);
		loggerDebug("AppntChkQuery","flag="+tFlag);
  			
		tLCContSchema.setContNo(tProposalNo);
		tLCContSchema.setInsuredNo(tInsuredNo);		
		tLCContSchema.setAppFlag(tFlag);
		tLCContSchema.setRemark(tLoadFlag);
		VData tVData = new VData();
		tVData.add(tLCContSchema);
		tVData.add(tG);
		
		PersonChkUI tPersonChkUI = new PersonChkUI();
		if(tPersonChkUI.submitData(tVData,"") == false)
		{			
			FlagStr = "Fail";
			loggerDebug("AppntChkQuery","FlagSgr:"+FlagStr);
			Content = tPersonChkUI.mErrors.getError(0).errorMessage;
		}
		else
		{
			
					
			VData tVdata = new VData();
	  		tVdata = tPersonChkUI.getResult();
  			tLDPersonSet.set((LDPersonSet)tVdata.getObjectByObjectName("LDPersonSet",0));			
			if(tLDPersonSet.size()>0)
			{					
			
			//��ѯ����ʾ������Ϣ    					
	%>
                                        
					<script language="javascript">
					//        alert(111);
					        parent.fraInterface.PolGrid.clearData("PolGrid");
					</script>
	<%					
					for(int i = 1;i <= tLDPersonSet.size();i++)
					{
						LDPersonSchema tLDPersonSchema = new LDPersonSchema();
						tLDPersonSchema = tLDPersonSet.get(i);
						loggerDebug("AppntChkQuery","--"+i+"--");
						loggerDebug("AppntChkQuery","NO:"+tLDPersonSchema.getCustomerNo());
						
	%>                      	
						<script language="javascript">
							parent.fraInterface.PolGrid.addOne("PolGrid");
			   				parent.fraInterface.PolGrid.setRowColData(<%=i-1%>, 1, "<%=tLDPersonSchema.getCustomerNo()%>");
			   				parent.fraInterface.PolGrid.setRowColData(<%=i-1%>, 2, "<%=tLDPersonSchema.getName()%>");
							<%if(tLDPersonSchema.getIDType()==null||tLDPersonSchema.getIDType().equals("null")){%>			   				
			   				parent.fraInterface.PolGrid.setRowColData(<%=i-1%>, 3, "");
			   				<%} else {%>
			   				parent.fraInterface.PolGrid.setRowColData(<%=i-1%>, 3, "<%=tLDPersonSchema.getIDType()%>");
			   				<%} if(tLDPersonSchema.getIDNo()==null||tLDPersonSchema.getIDNo().equals("null")){%>
			   				parent.fraInterface.PolGrid.setRowColData(<%=i-1%>, 4, "");
			   				<%} else {%>
			   				parent.fraInterface.PolGrid.setRowColData(<%=i-1%>, 4, "<%=tLDPersonSchema.getIDNo()%>");
			   				<%} if(tLDPersonSchema.getSex()==null||tLDPersonSchema.getSex().equals("null")){%>
			   				parent.fraInterface.PolGrid.setRowColData(<%=i-1%>, 5, "");
			   				<%} else{%>
			   				parent.fraInterface.PolGrid.setRowColData(<%=i-1%>, 5, "<%=tLDPersonSchema.getSex()%>");
			   				<%} if(tLDPersonSchema.getBirthday()==null||tLDPersonSchema.getBirthday().equals("null")){%>			   				
			   				parent.fraInterface.PolGrid.setRowColData(<%=i-1%>, 6, "");
			   				<%} else{%>
			   				parent.fraInterface.PolGrid.setRowColData(<%=i-1%>, 6, "<%=tLDPersonSchema.getBirthday()%>");
			   				<%}%>
	                    			</script>         
	<%    				
					}
					FlagStr = "succ";
					
				}
				else
				{
					FlagStr = "Fail";
					Content = "û����ͬ�ͻ�!";
					
				}
			//}
			
	  		if(tFlag.equals("A"))
	  		{
	  			tLCAppntSet.set((LCAppntSet)tVdata.getObjectByObjectName("LCAppntSet",0));	  			
				if(tLCAppntSet.size()>0)
				{					
			
				//��ѯ����ʾ������Ϣ    					
	%>
					<script>parent.fraInterface.OPolGrid.clearData("OPolGrid");</script>
	<%
					for(int i = 1;i <= tLCAppntSet.size();i++)
					{
						LCAppntSchema tLCAppntSchema = new LCAppntSchema();
						LDPersonSchema tLDPersonSchema = new LDPersonSchema();
						LDPersonDB tLDPersonDB = new LDPersonDB();
						tLCAppntSchema = tLCAppntSet.get(i);
						loggerDebug("AppntChkQuery","--"+i+"--");
						loggerDebug("AppntChkQuery","NO:"+tLCAppntSchema.getAppntNo());
						tLDPersonDB.setCustomerNo(tLCAppntSchema.getAppntNo());
						if(tLDPersonDB.getInfo() == false)
						{
						
						}
						else
						{
							tLDPersonSchema = tLDPersonDB.getSchema();
	%>                      	
							<script language="javascript">
								parent.fraInterface.OPolGrid.addOne("OPolGrid");                                                  
                parent.fraInterface.OPolGrid.setRowColData(<%=i-1%>, 1, "<%=tLDPersonSchema.getCustomerNo()%>"); 
                parent.fraInterface.OPolGrid.setRowColData(<%=i-1%>, 2, "<%=tLDPersonSchema.getName()%>");       
                <%if(tLDPersonSchema.getIDType()==null||tLDPersonSchema.getIDType().equals("null")){%>		
                parent.fraInterface.OPolGrid.setRowColData(<%=i-1%>, 3, "");                                     
                <%} else {%>                                                                                    
                parent.fraInterface.OPolGrid.setRowColData(<%=i-1%>, 3, "<%=tLDPersonSchema.getIDType()%>");     
                <%} if(tLDPersonSchema.getIDNo()==null||tLDPersonSchema.getIDNo().equals("null")){%>            
                parent.fraInterface.OPolGrid.setRowColData(<%=i-1%>, 4, "");                                     
                <%} else {%>                                                                                    
                parent.fraInterface.OPolGrid.setRowColData(<%=i-1%>, 4, "<%=tLDPersonSchema.getIDNo()%>");       
                <%} if(tLDPersonSchema.getSex()==null||tLDPersonSchema.getSex().equals("null")){%>  
                parent.fraInterface.OPolGrid.setRowColData(<%=i-1%>, 5, "");                                     
                <%} else{%>                                                                                     
                parent.fraInterface.OPolGrid.setRowColData(<%=i-1%>, 5, "<%=tLDPersonSchema.getSex()%>");  
                <%} if(tLDPersonSchema.getBirthday()==null||tLDPersonSchema.getBirthday().equals("null")){%>	
                parent.fraInterface.OPolGrid.setRowColData(<%=i-1%>, 6, "");                                     
                <%} else{%>                                                                                     
                parent.fraInterface.OPolGrid.setRowColData(<%=i-1%>, 6, "<%=tLDPersonSchema.getBirthday()%>");    
                <%}%>                                                                                           

              </script>                                                                                               
	<%    				                                                                                                                        
						}                                                                                                               
					}                                                                                                                       
					FlagStr = "succ";                                                                                                     
				}                                                                                                                               
				else                                                                                                                            
				{                                                                                                                               
					FlagStr = "Fail";                                                                                                       
					Content = "û��ԭ�ͻ���Ϣ!";                                                                                            
					                                                                                                                        
				}                                                                                                                               
			}                                                                                                                                       
	  		if(tFlag.equals("I"))
	  		{
	  			tLCInsuredSet.set((LCInsuredSet)tVdata.getObjectByObjectName("LCInsuredSet",0));
			
				if(tLCInsuredSet.size()>0)
				{					
			
				//��ѯ����ʾ������Ϣ    					
	%>
					<script>parent.fraInterface.OPolGrid.clearData("OPolGrid");</script>
	<%
					for(int i = 1;i <= tLCInsuredSet.size();i++)
					{
						LCInsuredSchema tLCInsuredSchema = new LCInsuredSchema();
						tLCInsuredSchema = tLCInsuredSet.get(i);
						loggerDebug("AppntChkQuery","--"+i+"--");
						loggerDebug("AppntChkQuery","NO:"+tLCInsuredSchema.getInsuredNo());
						LDPersonSchema tLDPersonSchema = new LDPersonSchema();
						LDPersonDB tLDPersonDB = new LDPersonDB();
						tLDPersonDB.setCustomerNo(tLCInsuredSchema.getInsuredNo());
						if(tLDPersonDB.getInfo() == false)
						{
						
						}
						else
						{
							tLDPersonSchema = tLDPersonDB.getSchema();

	%>                      	
							<script language="javascript">
								parent.fraInterface.OPolGrid.addOne("OPolGrid");                                                  
                parent.fraInterface.OPolGrid.setRowColData(<%=i-1%>, 1, "<%=tLDPersonSchema.getCustomerNo()%>"); 
                parent.fraInterface.OPolGrid.setRowColData(<%=i-1%>, 2, "<%=tLDPersonSchema.getName()%>");       
                <%if(tLDPersonSchema.getIDType()==null||tLDPersonSchema.getIDType().equals("null")){%>		
                parent.fraInterface.OPolGrid.setRowColData(<%=i-1%>, 3, "");                                     
                <%} else {%>                                                                                    
                parent.fraInterface.OPolGrid.setRowColData(<%=i-1%>, 3, "<%=tLDPersonSchema.getIDType()%>");     
                <%} if(tLDPersonSchema.getIDNo()==null||tLDPersonSchema.getIDNo().equals("null")){%>            
                parent.fraInterface.OPolGrid.setRowColData(<%=i-1%>, 4, "");                                     
                <%} else {%>                                                                                    
                parent.fraInterface.OPolGrid.setRowColData(<%=i-1%>, 4, "<%=tLDPersonSchema.getIDNo()%>");       
                <%} if(tLDPersonSchema.getSex()==null||tLDPersonSchema.getSex().equals("null")){%>  
                parent.fraInterface.OPolGrid.setRowColData(<%=i-1%>, 5, "");                                     
                <%} else{%>                                                                                     
                parent.fraInterface.OPolGrid.setRowColData(<%=i-1%>, 5, "<%=tLDPersonSchema.getSex()%>");  
                <%} if(tLDPersonSchema.getBirthday()==null||tLDPersonSchema.getBirthday().equals("null")){%>	
                parent.fraInterface.OPolGrid.setRowColData(<%=i-1%>, 6, "");                                     
                <%} else{%>                                                                                     
                parent.fraInterface.OPolGrid.setRowColData(<%=i-1%>, 6, "<%=tLDPersonSchema.getBirthday()%>");
	              <%}%> 
              </script>         
	<%    				
						}
					}
					FlagStr = "succ";
				}
				else
				{
					FlagStr = "Fail";
					Content = "û��ԭ�ͻ���Ϣ!";
				}
			}
	}


		
  }
}
catch(Exception e)
{
	e.printStackTrace();
	Content = Content.trim() +" ��ʾ:�쳣�˳�.";
}
%>  
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
