<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%
//�������ƣ�ProposalQueryDetail.jsp
//�����ܣ�
//�������ڣ�2002-06-19 11:10:36
//������  ��HST
//���¼�¼��  ������ ln   �������� 2008-12-17    ����ԭ��/���� ֧�ֶ����ն౻����
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="java.lang.reflect.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%> 
  <%@page import="java.util.*"%>
<%
  //�������
  CErrors tError = null;
  String FlagStr = "Fail";
  String Content = "";
  String statusFlag = "";
  
  String customerNo = "";  //��¼�����˿ͻ��ţ�������Ͷ���˿ͻ��Ž����ж�

  loggerDebug("WbProposalQueryDetail","\n\n------WBProposalQueryDetail start------");
  // ������Ϣ����

 TBXMLTransfer TBXMLTransfer1 = new TBXMLTransfer();
 JMTBXMLTransfer JMTBXMLTransfer1 = new JMTBXMLTransfer();
    VData tVData = new VData();
    String tBussNoType = request.getParameter("BussNoType");
    loggerDebug("WbProposalQueryDetail","BussNoType: "+ tBussNoType);
    if(tBussNoType==null || tBussNoType.equals(""))
    	loggerDebug("WbProposalQueryDetail","BussNoType���ݴ������");
    else
    {
    	if(tBussNoType.equals("TB"))
    		tVData=TBXMLTransfer1.getOnePolData(request.getParameter("PrtNo")); 
    	else if(tBussNoType.equals("JM"))
			tVData=JMTBXMLTransfer1.getOnePolData(request.getParameter("PrtNo")); 
    }       
    loggerDebug("WbProposalQueryDetail","tVData.size: "+tVData.size());

 // ���ݴ���
 if (tVData != null && tVData.size()>0) 
 { 
    //��vdata�в�ֳ���Ҫ��ʼ���õ�Ͷ����������
    TransferData tTransferData = (TransferData) tVData.getObjectByObjectName("TransferData", 0); //��ͬ������Ϣ��ҪУ�����Ϣ
    LCPolSchema tLCPolSchema = (LCPolSchema) tVData.getObjectByObjectName("LCPolSchema", 0); //��ͬ������Ϣ
    LCAppntIndSchema tLCAppntIndSchema= (LCAppntIndSchema) tVData.getObjectByObjectName("LCAppntIndSchema", 0); //Ͷ������Ϣ
    LCCustomerImpartSet tLCCustomerImpartSet= (LCCustomerImpartSet) tVData.getObjectByObjectName("LCCustomerImpartSet", 0); //�ͻ���֪��Ϣ
    VData tInsuredResults = (VData) tVData.getObjectByObjectName("VData", 0); //��������أ������ˡ����֡������ˣ���Ϣ
   
		// ��ʾ
		// ������Ϣ
		LCPolSchema mLCPolSchema = new LCPolSchema(); 
		mLCPolSchema.setSchema(tLCPolSchema); 
 %>
<html>		
<head>
	<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
</head>
<body>
    <script language="javascript"> 
    <%
    //--------------------------------------------------------------------------------------
	// -------------------------------------��ͬ��Ϣ--------------------------------------------
    	loggerDebug("WbProposalQueryDetail","Start Display ��ͬ��Ϣ...");
	    //�����µĸ���Ͷ����
	    int elementsNum;
	    Class c = mLCPolSchema.getClass();
	    Field f[] = c.getDeclaredFields();
	
	    for(elementsNum=0; elementsNum<f.length; elementsNum++) {
	    	      
	      if (!mLCPolSchema.getV(f[elementsNum].getName()).equals("null")) {
	    	//������ѷ�ʽΪ99,��Ϊ�գ����ʼ������Ϊ��
		  		if(f[elementsNum].getName().equals("PayIntv")&& mLCPolSchema.getV(f[elementsNum].getName()).equals("99"))
		  		{
		  			out.println("try{");
			        out.println("parent.fraInterface.fm.all('" +f[elementsNum].getName() + "').value='';");
			        loggerDebug("WbProposalQueryDetail","parent.fraInterface.fm.all('" + f[elementsNum].getName() + "').value='';");
			        out.println("} catch(ex) {};");
		  			continue;
		  		}
		  		if(f[elementsNum].getName().equals("RnewFlag")&& mLCPolSchema.getV(f[elementsNum].getName()).equals("0"))
		  		{
		  			continue;
		  		}
	        out.println("try{");
	        out.println("parent.fraInterface.fm.all('" + f[elementsNum].getName() + "').value='" + mLCPolSchema.getV(f[elementsNum].getName()) + "';");
	        loggerDebug("WbProposalQueryDetail","parent.fraInterface.fm.all('" + f[elementsNum].getName() + "').value='" + mLCPolSchema.getV(f[elementsNum].getName()) + "';");
	        out.println("} catch(ex) {};");
	      }
	    }

	  	loggerDebug("WbProposalQueryDetail","End Display ��ͬ��Ϣ...");
	  	
	  	if(tTransferData!=null)
	  	{
	  	  Vector nameVData = tTransferData.getValueNames();
	  	  for(int i = 0;i<nameVData.size();i++)
	  	  {
	  	    String tName = (String)nameVData.get(i);	  	
	  	    out.println("try{");
	        out.println("parent.fraInterface.fm.all('" +tName + "').value='" + StrTool.cTrim((String)tTransferData.getValueByName(tName)) + "';");
	        loggerDebug("WbProposalQueryDetail","parent.fraInterface.fm.all('" + tName + "').value='" + StrTool.cTrim((String)tTransferData.getValueByName(tName)) + "';");
	        out.println("} catch(ex) {};");  
	  	  }	  	   
	  	   
		  	//�жϣ�����������˺��Ƿ�һ��
	      	// ������ͬͶ���˱�־********************************************
    	    String tSameAccontFlag = (String)tTransferData.getValueByName("sameAccontFlag");
    	    if("1".equals(tSameAccontFlag))
    	    {
    	    %>
    	      parent.fraInterface.fm.all("theSameAccount").checked = true;
    	    <%
    	    }
    	    //**********************************************************
    	    
    	    //��ʾ���ڽ�����ʾ�� ���� hanbin 2010-05-05
    	    String tXQremindFlag = (String)tTransferData.getValueByName("XQremindflag");
    	    if("1".equals(tXQremindFlag))
    	    {
    	    %>
    	    try	{ parent.fraInterface.fm.all("XQremindFlagName").value = "��"; }catch(ex){}
    	    <%
    	    }else if ("0".equals(tXQremindFlag)){
        	    %>
        	    try	{parent.fraInterface.fm.all("XQremindFlagName").value = "��";}catch(ex){}
        	    <%
        	    }
    	    else{
    	    	 %>
    	    	 try{parent.fraInterface.fm.all("XQremindFlagName").value = "";}catch(ex){}
       	   		 <%
    	    }
    	    
	  	}
	  	loggerDebug("WbProposalQueryDetail","End Display TransferData...");
    %>
    </script> 

    <% 
    //--------------------------------------------------------------------------------------
	// -------------------------------------Ͷ���˼��ɷ���Ϣ--------------------------------------------
        loggerDebug("WbProposalQueryDetail","Start Display ����Ͷ������Ϣ..."); 
	    if(tLCAppntIndSchema!=null)
	  	{
		// ����Ͷ������Ϣ
	%>
    	<script language="javascript">
    	<%
	    	  // ����Ͷ������Ϣ
	    	  
	    	  c = tLCAppntIndSchema.getClass();
	    	  f = c.getDeclaredFields();
	    	  
	    	  for (elementsNum=0; elementsNum<f.length; elementsNum++) 
	    	  {  
		    	    if (!tLCAppntIndSchema.getV(f[elementsNum].getName()).equals("null")) 
		    	    {
		      	    out.println("try {");
		      	    out.println("parent.fraInterface.fm.all('Appnt" + f[elementsNum].getName() +"').value = '" + tLCAppntIndSchema.getV(f[elementsNum].getName()) + "';");
		      	    loggerDebug("WbProposalQueryDetail","parent.fraInterface.fm.all('Appnt" + f[elementsNum].getName() +"').value = '" + tLCAppntIndSchema.getV(f[elementsNum].getName()) + "';");
		      	    out.println("} catch(ex) { };");
		      	  }
	    	  }   
    	%>
	  //try { parent.fraInterface.fm.all("AppntAge").value="<%=PubFun.calInterval3(tLCAppntIndSchema.getBirthday(), PubFun.getCurrentDate(), "Y")%>"; } catch(ex) { }
	  //prompt("bbb",parent.fraInterface.fm.all("AutoPayFlag").value);
    	</script>

  	<script language="javascript">
	<%
	  	}
		loggerDebug("WbProposalQueryDetail","End Display ����Ͷ������Ϣ..."); 
	%>  	
	//prompt("ccc",parent.fraInterface.fm.all("AutoPayFlag").value);
 	 
	</script>

    <%
    //--------------------------------------------------------------------------------------
	// -------------------------------------�����������Ϣ--------------------------------------------
		// ��������Ϣ
		loggerDebug("WbProposalQueryDetail","Start Display �����������Ϣ...");
        //�ӱ����������Ϣ�в�ֳ������˻�����Ϣ��������Ϣ����������Ϣ
        if(tInsuredResults!=null )
        {
            loggerDebug("WbProposalQueryDetail","��������Ŀ��" + tInsuredResults.size());           
          //��ʼ��������¼����ʾ
    		%>	
        	<script language="javascript">          	           	    	                                   	
	    		try { parent.fraInterface.initInsuredInput(<%=tInsuredResults.size()%>);	} catch(ex) {}
	    		
	    	    </script> 
	    	    <%
        	for(int i=0 ; i<tInsuredResults.size(); i++)
            { 
        		loggerDebug("WbProposalQueryDetail","***************������****************"
						+ (i + 1));  
        		 if(i + 1>3)
                 {%>
                 	<script language="javascript">
                 		alert("��Ͷ�����ı�������������3�����������ϵı�������Ϣ��������ʾ��");                  	 
                 	</script>
                 <%
                 	break;
                 }
            	VData tInsuredRelaSet = (VData) tInsuredResults.getObjectByObjectName("VData", i);
            	
            	VData tTransferDataSet = (VData) tInsuredRelaSet.getObjectByObjectName("VData", 0); //��������Ϣ����ҪУ�����Ϣ ��������������
            	VData tTransferDataSet1 = (VData) tInsuredRelaSet.getObjectByObjectName("VData", 1); //���汣�ս���𡢺�������ʽ��Ϣ--���ڶ�����
            	LCInsuredSet tLCInsuredSet = (LCInsuredSet) tInsuredRelaSet.getObjectByObjectName("LCInsuredSet", 2); //�����˻�����Ϣ �������������ˣ������ڲ���Ҫ��������
            	LCBnfSet tLCBnfSet = (LCBnfSet) tInsuredRelaSet.getObjectByObjectName("LCBnfSet", 0); //��������Ϣ--���ڶ�����
            	LCPolSet tLCPolSet1 = (LCPolSet) tInsuredRelaSet.getObjectByObjectName("LCPolSet", 0); //���汣�ս���𡢺�������ʽ��Ϣ--���ڶ�����
            	VData tRiskBasicInfoMainSet = (VData) tInsuredRelaSet.getObjectByObjectName("VData", 3); //������Ϣ--���ڶ�����
            	
            	// -------------------------------------�����˻�����Ϣ--------------------------------------------
                loggerDebug("WbProposalQueryDetail","Start Display �����˻�����Ϣ...");
                if (tLCInsuredSet != null ) 
                {
                	for(int j=0 ; j<tLCInsuredSet.size(); j++)	
            		{
            			LCInsuredSchema tLCInsuredSchema = tLCInsuredSet.get(j+1);
            			TransferData itTransferData = (TransferData)tTransferDataSet.getObjectByObjectName("TransferData", j);
            			loggerDebug("WbProposalQueryDetail","tLCInsuredSchema:" + tLCInsuredSchema.encode());            			
            	
            		    	// ��������Ϣ 
            		    	int k = i+j+1;
                	  		c = tLCInsuredSchema.getClass();
                	  		f = c.getDeclaredFields();
                	  
                	  		for (elementsNum=0; elementsNum<f.length; elementsNum++) 
                	  		{  
            	    	    		if (!tLCInsuredSchema.getV(f[elementsNum].getName()).equals("null")) 
            	    	    		{
            	    	    			%>	
            	    	            	<script language="javascript">          	           	    	                        
//            	    	            	
            		      	    		try { parent.fraInterface.fm.all("<%=f[elementsNum].getName()+k%>").value = "<%=tLCInsuredSchema.getV(f[elementsNum].getName())%>";	} catch(ex) {}
            		      	    		
            		      	    	    </script> 
            		      	    	    <%
            		      	    	  loggerDebug("WbProposalQueryDetail","parent.fraInterface.fm.all('" + f[elementsNum].getName()+k +"').value = '" + tLCInsuredSchema.getV(f[elementsNum].getName()) + "';");
            	             		}
                	  		}
                	  
                	  %>
                	  <script language="javascript">
            		    	//try { parent.fraInterface.fm.all("Age<%=k%>").value="<%=PubFun.calInterval3(tLCInsuredSchema.getBirthday(), StrTool.cTrim((String)tTransferData.getValueByName("PolApplyDate")), "Y")%>"; } catch(ex) {}
            		    	try { parent.fraInterface.fm.all("IDEndDate<%=k%>").value="<%=StrTool.cTrim(tLCInsuredSchema.getIDExpDate())%>"; } catch(ex) {}            		    	
            		    	try { parent.fraInterface.fm.all("HomeAddress<%=k%>").value="<%=StrTool.cTrim((String)itTransferData.getValueByName("HomeAddress"))%>"; } catch(ex) {}
            		    	try { parent.fraInterface.fm.all("HomeZipCode<%=k%>").value="<%=StrTool.cTrim((String)itTransferData.getValueByName("HomeZipCode"))%>"; } catch(ex) {}
            		    	try { parent.fraInterface.fm.all("Phone<%=k%>").value="<%=StrTool.cTrim((String)itTransferData.getValueByName("Phone"))%>"; } catch(ex) {}
            		    	try { parent.fraInterface.fm.all("Phone2<%=k%>").value="<%=StrTool.cTrim((String)itTransferData.getValueByName("Phone2"))%>"; } catch(ex) {}
            		    	try { parent.fraInterface.fm.all("EMail<%=k%>").value="<%=StrTool.cTrim((String)itTransferData.getValueByName("EMail"))%>"; } catch(ex) {}
            		    	try { parent.fraInterface.fm.all("GrpName<%=k%>").value="<%=StrTool.cTrim((String)itTransferData.getValueByName("GrpName"))%>"; } catch(ex) {}
             		//prompt("ccc2",parent.fraInterface.fm.all("AutoPayFlag").value);
            	     </script>
            	     <%    	
  		    				//�жϣ����Ͷ���˵��ڱ�����
			            	// ������ͬͶ���˱�־********************************************
			          	    String tSamePersonFlag = (String)itTransferData.getValueByName("samePersonFlag");
			          	    if("1".equals(tSamePersonFlag))
			          	    {
			          	    %>
			          	    <script language="javascript"> 	
			          	      parent.fraInterface.isSamePersonQuery(<%=i+1%>);
			          	    </script>
			          	    <%
			          	    }
			          	    //**********************************************************
			          	    
            	    		break;
            	    		//}
            	  }
                }
                loggerDebug("WbProposalQueryDetail","End Display �����˻�����Ϣ...");
            	
            	// -------------------------------------������Ϣ--------------------------------------------
                loggerDebug("WbProposalQueryDetail","Start Display ������������Ϣ...");                
                if (tRiskBasicInfoMainSet != null ) 
                {
                	loggerDebug("WbProposalQueryDetail","������Ŀ��" + tRiskBasicInfoMainSet.size());
                	loggerDebug("WbProposalQueryDetail","i+1��" + (i+1));
                	//��ʼ��������¼����ʾ
            		%>	
                	<script language="javascript">        	           	    	                                   	
        	    		try { parent.fraInterface.initMainRiskInput(<%=i+1%>,<%=tRiskBasicInfoMainSet.size()%>);	} catch(ex) {}
        	    		
        	    	    </script> 
        	    	<%
                	for(int j=0 ; j<tRiskBasicInfoMainSet.size(); j++)
                	{
                		loggerDebug("WbProposalQueryDetail","-------------һ������--------------"
								+ (j + 1));
                		if(j + 1>3)
                        {%>
                        	<script language="javascript">
                        		alert("��<%=i+1%>�����˵�������������3�����������ϵ�������Ϣ��������ʾ��");                         	 
                        	</script>
                        <%
                        	break;
                        }
                		VData tRiskBasicInfoSet = (VData)tRiskBasicInfoMainSet.getObjectByObjectName("VData",j); //һ��������Ϣ
                  	    %>
                  	    <script>
                  	    parent.fraInterface.Risk<%=i+1%><%=j+1%>Grid.clearData("Risk<%=i+1%><%=j+1%>Grid");
                  	    </script>
                  	    <%
                  	    for (int k = 0; k < tRiskBasicInfoSet.size(); k++) 
                  	    {  
                  	        RiskBasicInfo tRiskBasicInfo = (RiskBasicInfo)tRiskBasicInfoSet.getObjectByObjectName("RiskBasicInfo" , k); //һ��������Ϣ
                  	        %>     
                  	         <script language="javascript">  
                  	             	          
                  		  		parent.fraInterface.Risk<%=i+1%><%=j+1%>Grid.addOne("Risk<%=i+1%><%=j+1%>Grid");
                  		  	  parent.fraInterface.Risk<%=i+1%><%=j+1%>Grid.setRowColData(<%=k%>,1,"<%=i%>"); //��Ӧ�ı�����
                  		  	  parent.fraInterface.Risk<%=i+1%><%=j+1%>Grid.setRowColData(<%=k%>,2,"<%=StrTool.cTrim(tRiskBasicInfo.getRiskNo())%>"); //�������
                  		  	  parent.fraInterface.Risk<%=i+1%><%=j+1%>Grid.setRowColData(<%=k%>,3,"<%=StrTool.cTrim(tRiskBasicInfo.getRiskCode())%>");
                  		  	  parent.fraInterface.Risk<%=i+1%><%=j+1%>Grid.setRowColData(<%=k%>,4,"<%=StrTool.cTrim(tRiskBasicInfo.getRiskName())%>");
                  	          parent.fraInterface.Risk<%=i+1%><%=j+1%>Grid.setRowColData(<%=k%>,5,"<%=tRiskBasicInfo.getAmnt()%>");
                  	          parent.fraInterface.Risk<%=i+1%><%=j+1%>Grid.setRowColData(<%=k%>,6,"<%=tRiskBasicInfo.getMult()%>");
                  	          parent.fraInterface.Risk<%=i+1%><%=j+1%>Grid.setRowColData(<%=k%>,7,"<%=tRiskBasicInfo.getInsuYear()%>");
                  	          parent.fraInterface.Risk<%=i+1%><%=j+1%>Grid.setRowColData(<%=k%>,8,"<%=StrTool.cTrim(tRiskBasicInfo.getInsuYearFlag())%>");
                  	          parent.fraInterface.Risk<%=i+1%><%=j+1%>Grid.setRowColData(<%=k%>,9,"<%=tRiskBasicInfo.getPayEndYear()%>");
                  	          parent.fraInterface.Risk<%=i+1%><%=j+1%>Grid.setRowColData(<%=k%>,10,"<%=StrTool.cTrim(tRiskBasicInfo.getPayEndYearFlag())%>");
                  	          parent.fraInterface.Risk<%=i+1%><%=j+1%>Grid.setRowColData(<%=k%>,11,"<%=tRiskBasicInfo.getPrem()%>");
                  	          parent.fraInterface.Risk<%=i+1%><%=j+1%>Grid.setRowColData(<%=k%>,12,"<%=tRiskBasicInfo.getAddPrem()%>");
                  	          parent.fraInterface.Risk<%=i+1%><%=j+1%>Grid.setRowColData(<%=k%>,13,"<%=tRiskBasicInfo.getInputPrem()%>");                  	          	          
                  	          </script>
                  	          <%
                  	    }                 	    	                  	                    	
                  	
                	}
                }         	            	
         	   loggerDebug("WbProposalQueryDetail","End Display ������������Ϣ...");
         	   
         	// -------------------------------------��������Ϣ--------------------------------------------
	         	loggerDebug("WbProposalQueryDetail","Start Display ��������������Ϣ...");	         	
	          	  if (tLCBnfSet != null	) {
	          			loggerDebug("WbProposalQueryDetail","��������Ŀ��" + tLCBnfSet.size());
	          		// ��������Ϣ
						%>
						<script>parent.fraInterface.Bnf<%=i+1%>Grid.clearData("Bnf<%=i+1%>Grid");</script>
						<%
						for (int k = 0; k < tLCBnfSet.size(); k++) { // һ����������Ϣ
							LCBnfSchema tLCBnfSchema = (LCBnfSchema) tLCBnfSet.get(k + 1);
							%>	
						    	<script language="javascript">
						    		parent.fraInterface.Bnf<%=i+1%>Grid.addOne("Bnf<%=i+1%>Grid");
						    	parent.fraInterface.Bnf<%=i+1%>Grid.setRowColData(<%=k%>, 1, "<%=i+1%>"); //�����˶�Ӧ�ı�����
						    	parent.fraInterface.Bnf<%=i+1%>Grid.setRowColData(<%=k%>, 2, "<%=tLCBnfSchema.getBnfNo()%>"); //�����˶�Ӧ������
							   	parent.fraInterface.Bnf<%=i+1%>Grid.setRowColData(<%=k%>, 3, "<%=StrTool.cTrim(tLCBnfSchema.getBnfType())%>");
							   	parent.fraInterface.Bnf<%=i+1%>Grid.setRowColData(<%=k%>, 4, "<%=StrTool.cTrim(tLCBnfSchema.getName())%>");
							   	parent.fraInterface.Bnf<%=i+1%>Grid.setRowColData(<%=k%>, 5, "<%=StrTool.cTrim(tLCBnfSchema.getIDType())%>");
							   	parent.fraInterface.Bnf<%=i+1%>Grid.setRowColData(<%=k%>, 6, "<%=StrTool.cTrim(tLCBnfSchema.getIDNo())%>");
							   	parent.fraInterface.Bnf<%=i+1%>Grid.setRowColData(<%=k%>, 7, "<%=StrTool.cTrim(tLCBnfSchema.getRelationToInsured())%>");
							   	parent.fraInterface.Bnf<%=i+1%>Grid.setRowColData(<%=k%>, 8, "<%=tLCBnfSchema.getBnfLot()%>");
							   	parent.fraInterface.Bnf<%=i+1%>Grid.setRowColData(<%=k%>, 9, "<%=StrTool.cTrim(tLCBnfSchema.getBnfGrade())%>");
							   	parent.fraInterface.Bnf<%=i+1%>Grid.setRowColData(<%=k%>, 10, "<%=StrTool.cTrim(tLCBnfSchema.getPostalAddress())%>");
							   	parent.fraInterface.Bnf<%=i+1%>Grid.setRowColData(<%=k%>, 11, "<%=StrTool.cTrim(tLCBnfSchema.getIDExpDate())%>");
						    	</script>
						  <%
						}
						// end bnf
				}
	          	loggerDebug("WbProposalQueryDetail","End Display ��������������Ϣ...");
	          	
	         // -------------------------------------���汣�ս���𡢺�������ʽ��Ϣ--------------------------------------------
                loggerDebug("WbProposalQueryDetail","Start Display ���汣�ս���𡢺�������ʽ��Ϣ...");                
                if (tTransferDataSet1 != null ) {
                		loggerDebug("WbProposalQueryDetail","���汣�ս���𡢺�������ʽ��Ŀ��" + tTransferDataSet1.size());
						%>
						<script>parent.fraInterface.DealType<%=i+1%>Grid.clearData("DealType<%=i+1%>Grid");</script>
						<%
						for (int k = 0; k < tTransferDataSet1.size(); k++) { // һ����Ϣ
							TransferData tTransferData1 = (TransferData) tTransferDataSet1.get(k);
							LCPolSchema tLCPolSchema1 = tLCPolSet1.get(k + 1);
							String getYearS ="";
							int getYear = tLCPolSchema1.getGetYear();
							if(getYear>0)
								getYearS = String.valueOf(getYear);
							%>	
						    	<script language="javascript">
						    		parent.fraInterface.DealType<%=i+1%>Grid.addOne("DealType<%=i+1%>Grid");
						    	parent.fraInterface.DealType<%=i+1%>Grid.setRowColData(<%=k%>, 1, "<%=i+1%>"); //��Ӧ�ı�����
							   	parent.fraInterface.DealType<%=i+1%>Grid.setRowColData(<%=k%>, 2, "<%=tLCPolSchema1.getInsuredNo()%>"); //��Ӧ������
							   	parent.fraInterface.DealType<%=i+1%>Grid.setRowColData(<%=k%>, 3, "<%=getYearS%>");
							   	parent.fraInterface.DealType<%=i+1%>Grid.setRowColData(<%=k%>, 4, "<%=StrTool.cTrim(tLCPolSchema1.getGetYearFlag())%>");
							   	parent.fraInterface.DealType<%=i+1%>Grid.setRowColData(<%=k%>, 5, "<%=StrTool.cTrim((String)tTransferData1.getValueByName("GetYears"))%>");
							   	parent.fraInterface.DealType<%=i+1%>Grid.setRowColData(<%=k%>, 6, "<%=StrTool.cTrim((String)tTransferData1.getValueByName("GetDutyKind"))%>");
							   	parent.fraInterface.DealType<%=i+1%>Grid.setRowColData(<%=k%>, 7, "<%=StrTool.cTrim(tLCPolSchema1.getLiveGetMode())%>");
							   	parent.fraInterface.DealType<%=i+1%>Grid.setRowColData(<%=k%>, 8, "<%=StrTool.cTrim(tLCPolSchema1.getBonusGetMode())%>");
						    	</script>
						  <%
						}					
						
						// end bnf
				}      	            	
         	   loggerDebug("WbProposalQueryDetail","End Display ���汣�ս���𡢺�������ʽ��Ϣ...");
            	
            }  
        }         
        loggerDebug("WbProposalQueryDetail","End Display �����������Ϣ...");   
        
      //--------------------------------------------------------------------------------------
    	// -------------------------------------��֪��Ϣ--------------------------------------------
      loggerDebug("WbProposalQueryDetail","Start Display ��֪��Ϣ...");      
		LCCustomerImpartSet mLCCustomerImpartSet = new LCCustomerImpartSet(); 
		mLCCustomerImpartSet.set(tLCCustomerImpartSet);
		if(mLCCustomerImpartSet!=null)
		{			
			int impartCount = mLCCustomerImpartSet.size();
			loggerDebug("WbProposalQueryDetail","��֪��Ŀ��" + impartCount);
			%>
			<script>parent.fraInterface.ImpartGrid.clearData("ImpartGrid");</script>
			<%
			for (int i = 1; i <= impartCount; i++) 
			{
				LCCustomerImpartSchema mLCCustomerImpartSchema = mLCCustomerImpartSet.get(i);
				loggerDebug("WbProposalQueryDetail","mLCCustomerImpartSchema:" + mLCCustomerImpartSchema.encode());
			%>	
		    	<script language="javascript">
		    		parent.fraInterface.ImpartGrid.addOne("ImpartGrid");
			   	parent.fraInterface.ImpartGrid.setRowColData(<%=i-1%>, 1, "<%=StrTool.cTrim(mLCCustomerImpartSchema.getImpartVer())%>");
			   	parent.fraInterface.ImpartGrid.setRowColData(<%=i-1%>, 2, "<%=StrTool.cTrim(mLCCustomerImpartSchema.getImpartCode())%>");
			   	parent.fraInterface.ImpartGrid.setRowColData(<%=i-1%>, 3, "<%=StrTool.cTrim(mLCCustomerImpartSchema.getImpartContent())%>");
			   	parent.fraInterface.ImpartGrid.setRowColData(<%=i-1%>, 4, "<%=StrTool.cTrim(mLCCustomerImpartSchema.getImpartParamModle())%>");
			   	parent.fraInterface.ImpartGrid.setRowColData(<%=i-1%>, 5, "<%=StrTool.cTrim(mLCCustomerImpartSchema.getCustomerNoType())%>");
			   	parent.fraInterface.ImpartGrid.setRowColData(<%=i-1%>, 6, "<%=StrTool.cTrim(mLCCustomerImpartSchema.getCustomerNo())%>");
			   	parent.fraInterface.ImpartGrid.setRowColData(<%=i-1%>, 7, "1"); //��֪�Ƿ���Ч --��Ч
		    	</script>
		  <%
		  	}
		}		
      loggerDebug("WbProposalQueryDetail","End Display ��֪��Ϣ...");
			     
	} // end of if
  
  //�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
  if (FlagStr == "Fail") {
	  if(tBussNoType.equals("TB"))
		  tError = TBXMLTransfer1.mErrors; 
  	  else if(tBussNoType.equals("JM"))
  		  tError = JMTBXMLTransfer1.mErrors;
    
    if (!tError.needDealError()) {                          
    	Content = " ��ѯ�ɹ�! ";
    	FlagStr = "Succ";
    } else {
    	Content = " ��ѯʧ��2��ԭ����:" + tError.getFirstError();
    	FlagStr = "Fail";
    }
  }
  

loggerDebug("WbProposalQueryDetail",FlagStr);
loggerDebug("WbProposalQueryDetail",Content);
loggerDebug("WbProposalQueryDetail","------ProposalQueryDetail end------\n\n");

//  out.println("<script language=javascript>");
//  out.println("top.opener.fraInterface.showInfo.close();");
//  out.println("</script>");
%> 


	  
<script>
  //parent.fraInterface.emptyUndefined();
 
  if (parent.fraInterface.loadFlag == 3 || parent.fraInterface.loadFlag == 4) {
    parent.fraInterface.divButton.style.display = "none";
    //�����涯����
    try { parent.fraInterface.setFocus(); } catch(e) {}
  }
  
  //�����޸ġ������޸�
  if (parent.fraInterface.loadFlag == 3) {
    parent.fraInterface.inputButton.style.display = ""; 
    parent.fraInterface.inputQuest.style.display = "";
    parent.fraInterface.divButton.style.display = "";    
    parent.fraInterface.deleteButton.style.display="";
    parent.fraInterface.quaryagentgroup();
    parent.fraInterface.quaryOccupationType();
    parent.fraInterface.initScan();
    parent.fraInterface.showCodeName();
    parent.fraInterface.initialSave();
    parent.fraInterface.initialAge();
    
  }
  
  //���˳��
  if (parent.fraInterface.loadFlag == 4) {
    parent.fraInterface.inputButton.style.display = ""; 
    parent.fraInterface.inputQuest.style.display = "";
    parent.fraInterface.divButton.style.display = "";    
    parent.fraInterface.deleteButton.style.display="none";
    parent.fraInterface.quaryagentgroup();
    parent.fraInterface.quaryOccupationType();
    parent.fraInterface.initScan();
    parent.fraInterface.showCodeName();
    parent.fraInterface.initialSave();
    parent.fraInterface.initialAge();
    
  }  
</script>
</body>
</html>
