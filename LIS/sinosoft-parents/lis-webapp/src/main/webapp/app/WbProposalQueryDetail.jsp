<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%
//程序名称：ProposalQueryDetail.jsp
//程序功能：
//创建日期：2002-06-19 11:10:36
//创建人  ：HST
//更新记录：  更新人 ln   更新日期 2008-12-17    更新原因/内容 支持多主险多被保人
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="java.lang.reflect.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%> 
  <%@page import="java.util.*"%>
<%
  //输出参数
  CErrors tError = null;
  String FlagStr = "Fail";
  String Content = "";
  String statusFlag = "";
  
  String customerNo = "";  //记录被保人客户号，用来与投保人客户号进行判断

  loggerDebug("WbProposalQueryDetail","\n\n------WBProposalQueryDetail start------");
  // 保单信息部分

 TBXMLTransfer TBXMLTransfer1 = new TBXMLTransfer();
 JMTBXMLTransfer JMTBXMLTransfer1 = new JMTBXMLTransfer();
    VData tVData = new VData();
    String tBussNoType = request.getParameter("BussNoType");
    loggerDebug("WbProposalQueryDetail","BussNoType: "+ tBussNoType);
    if(tBussNoType==null || tBussNoType.equals(""))
    	loggerDebug("WbProposalQueryDetail","BussNoType数据传输错误！");
    else
    {
    	if(tBussNoType.equals("TB"))
    		tVData=TBXMLTransfer1.getOnePolData(request.getParameter("PrtNo")); 
    	else if(tBussNoType.equals("JM"))
			tVData=JMTBXMLTransfer1.getOnePolData(request.getParameter("PrtNo")); 
    }       
    loggerDebug("WbProposalQueryDetail","tVData.size: "+tVData.size());

 // 数据传输
 if (tVData != null && tVData.size()>0) 
 { 
    //从vdata中拆分出需要初始化用的投保单的数据
    TransferData tTransferData = (TransferData) tVData.getObjectByObjectName("TransferData", 0); //合同基本信息需要校验的信息
    LCPolSchema tLCPolSchema = (LCPolSchema) tVData.getObjectByObjectName("LCPolSchema", 0); //合同基本信息
    LCAppntIndSchema tLCAppntIndSchema= (LCAppntIndSchema) tVData.getObjectByObjectName("LCAppntIndSchema", 0); //投保人信息
    LCCustomerImpartSet tLCCustomerImpartSet= (LCCustomerImpartSet) tVData.getObjectByObjectName("LCCustomerImpartSet", 0); //客户告知信息
    VData tInsuredResults = (VData) tVData.getObjectByObjectName("VData", 0); //被保人相关（被保人、险种、受益人）信息
   
		// 显示
		// 保单信息
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
	// -------------------------------------合同信息--------------------------------------------
    	loggerDebug("WbProposalQueryDetail","Start Display 合同信息...");
	    //集体下的个人投保单
	    int elementsNum;
	    Class c = mLCPolSchema.getClass();
	    Field f[] = c.getDeclaredFields();
	
	    for(elementsNum=0; elementsNum<f.length; elementsNum++) {
	    	      
	      if (!mLCPolSchema.getV(f[elementsNum].getName()).equals("null")) {
	    	//如果交费方式为99,即为空，则初始化界面为空
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

	  	loggerDebug("WbProposalQueryDetail","End Display 合同信息...");
	  	
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
	  	   
		  	//判断，如果首续期账号是否一致
	      	// 被保人同投保人标志********************************************
    	    String tSameAccontFlag = (String)tTransferData.getValueByName("sameAccontFlag");
    	    if("1".equals(tSameAccontFlag))
    	    {
    	    %>
    	      parent.fraInterface.fm.all("theSameAccount").checked = true;
    	    <%
    	    }
    	    //**********************************************************
    	    
    	    //显示续期交费提示的 含义 hanbin 2010-05-05
    	    String tXQremindFlag = (String)tTransferData.getValueByName("XQremindflag");
    	    if("1".equals(tXQremindFlag))
    	    {
    	    %>
    	    try	{ parent.fraInterface.fm.all("XQremindFlagName").value = "是"; }catch(ex){}
    	    <%
    	    }else if ("0".equals(tXQremindFlag)){
        	    %>
        	    try	{parent.fraInterface.fm.all("XQremindFlagName").value = "否";}catch(ex){}
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
	// -------------------------------------投保人及缴费信息--------------------------------------------
        loggerDebug("WbProposalQueryDetail","Start Display 个人投保人信息..."); 
	    if(tLCAppntIndSchema!=null)
	  	{
		// 个人投保人信息
	%>
    	<script language="javascript">
    	<%
	    	  // 个人投保人信息
	    	  
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
		loggerDebug("WbProposalQueryDetail","End Display 个人投保人信息..."); 
	%>  	
	//prompt("ccc",parent.fraInterface.fm.all("AutoPayFlag").value);
 	 
	</script>

    <%
    //--------------------------------------------------------------------------------------
	// -------------------------------------被保人相关信息--------------------------------------------
		// 被保人信息
		loggerDebug("WbProposalQueryDetail","Start Display 被保人相关信息...");
        //从被保人相关信息中拆分出被保人基本信息、险种信息、受益人信息
        if(tInsuredResults!=null )
        {
            loggerDebug("WbProposalQueryDetail","被保人数目：" + tInsuredResults.size());           
          //初始化被保人录入显示
    		%>	
        	<script language="javascript">          	           	    	                                   	
	    		try { parent.fraInterface.initInsuredInput(<%=tInsuredResults.size()%>);	} catch(ex) {}
	    		
	    	    </script> 
	    	    <%
        	for(int i=0 ; i<tInsuredResults.size(); i++)
            { 
        		loggerDebug("WbProposalQueryDetail","***************被保人****************"
						+ (i + 1));  
        		 if(i + 1>3)
                 {%>
                 	<script language="javascript">
                 		alert("该投保单的被保人数超过了3个，三个以上的被保人信息将不会显示！");                  	 
                 	</script>
                 <%
                 	break;
                 }
            	VData tInsuredRelaSet = (VData) tInsuredResults.getObjectByObjectName("VData", i);
            	
            	VData tTransferDataSet = (VData) tInsuredRelaSet.getObjectByObjectName("VData", 0); //被保人信息中需要校验的信息 包含连带被保人
            	VData tTransferDataSet1 = (VData) tInsuredRelaSet.getObjectByObjectName("VData", 1); //生存保险金、年金、红利处理方式信息--对于多主险
            	LCInsuredSet tLCInsuredSet = (LCInsuredSet) tInsuredRelaSet.getObjectByObjectName("LCInsuredSet", 2); //被保人基本信息 包含连带被保人，但现在不需要处理连带
            	LCBnfSet tLCBnfSet = (LCBnfSet) tInsuredRelaSet.getObjectByObjectName("LCBnfSet", 0); //受益人信息--对于多主险
            	LCPolSet tLCPolSet1 = (LCPolSet) tInsuredRelaSet.getObjectByObjectName("LCPolSet", 0); //生存保险金、年金、红利处理方式信息--对于多主险
            	VData tRiskBasicInfoMainSet = (VData) tInsuredRelaSet.getObjectByObjectName("VData", 3); //险种信息--对于多主险
            	
            	// -------------------------------------被保人基本信息--------------------------------------------
                loggerDebug("WbProposalQueryDetail","Start Display 被保人基本信息...");
                if (tLCInsuredSet != null ) 
                {
                	for(int j=0 ; j<tLCInsuredSet.size(); j++)	
            		{
            			LCInsuredSchema tLCInsuredSchema = tLCInsuredSet.get(j+1);
            			TransferData itTransferData = (TransferData)tTransferDataSet.getObjectByObjectName("TransferData", j);
            			loggerDebug("WbProposalQueryDetail","tLCInsuredSchema:" + tLCInsuredSchema.encode());            			
            	
            		    	// 被保人信息 
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
  		    				//判断，如果投保人等于被保人
			            	// 被保人同投保人标志********************************************
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
                loggerDebug("WbProposalQueryDetail","End Display 被保人基本信息...");
            	
            	// -------------------------------------主险信息--------------------------------------------
                loggerDebug("WbProposalQueryDetail","Start Display 被保人主险信息...");                
                if (tRiskBasicInfoMainSet != null ) 
                {
                	loggerDebug("WbProposalQueryDetail","主险数目：" + tRiskBasicInfoMainSet.size());
                	loggerDebug("WbProposalQueryDetail","i+1：" + (i+1));
                	//初始化被保人录入显示
            		%>	
                	<script language="javascript">        	           	    	                                   	
        	    		try { parent.fraInterface.initMainRiskInput(<%=i+1%>,<%=tRiskBasicInfoMainSet.size()%>);	} catch(ex) {}
        	    		
        	    	    </script> 
        	    	<%
                	for(int j=0 ; j<tRiskBasicInfoMainSet.size(); j++)
                	{
                		loggerDebug("WbProposalQueryDetail","-------------一个主险--------------"
								+ (j + 1));
                		if(j + 1>3)
                        {%>
                        	<script language="javascript">
                        		alert("第<%=i+1%>被保人的主险数超过了3个，三个以上的主险信息将不会显示！");                         	 
                        	</script>
                        <%
                        	break;
                        }
                		VData tRiskBasicInfoSet = (VData)tRiskBasicInfoMainSet.getObjectByObjectName("VData",j); //一个主险信息
                  	    %>
                  	    <script>
                  	    parent.fraInterface.Risk<%=i+1%><%=j+1%>Grid.clearData("Risk<%=i+1%><%=j+1%>Grid");
                  	    </script>
                  	    <%
                  	    for (int k = 0; k < tRiskBasicInfoSet.size(); k++) 
                  	    {  
                  	        RiskBasicInfo tRiskBasicInfo = (RiskBasicInfo)tRiskBasicInfoSet.getObjectByObjectName("RiskBasicInfo" , k); //一个险种信息
                  	        %>     
                  	         <script language="javascript">  
                  	             	          
                  		  		parent.fraInterface.Risk<%=i+1%><%=j+1%>Grid.addOne("Risk<%=i+1%><%=j+1%>Grid");
                  		  	  parent.fraInterface.Risk<%=i+1%><%=j+1%>Grid.setRowColData(<%=k%>,1,"<%=i%>"); //对应的被保人
                  		  	  parent.fraInterface.Risk<%=i+1%><%=j+1%>Grid.setRowColData(<%=k%>,2,"<%=StrTool.cTrim(tRiskBasicInfo.getRiskNo())%>"); //主险序号
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
         	   loggerDebug("WbProposalQueryDetail","End Display 被保人主险信息...");
         	   
         	// -------------------------------------受益人信息--------------------------------------------
	         	loggerDebug("WbProposalQueryDetail","Start Display 被保人受益人信息...");	         	
	          	  if (tLCBnfSet != null	) {
	          			loggerDebug("WbProposalQueryDetail","受益人数目：" + tLCBnfSet.size());
	          		// 受益人信息
						%>
						<script>parent.fraInterface.Bnf<%=i+1%>Grid.clearData("Bnf<%=i+1%>Grid");</script>
						<%
						for (int k = 0; k < tLCBnfSet.size(); k++) { // 一个受益人信息
							LCBnfSchema tLCBnfSchema = (LCBnfSchema) tLCBnfSet.get(k + 1);
							%>	
						    	<script language="javascript">
						    		parent.fraInterface.Bnf<%=i+1%>Grid.addOne("Bnf<%=i+1%>Grid");
						    	parent.fraInterface.Bnf<%=i+1%>Grid.setRowColData(<%=k%>, 1, "<%=i+1%>"); //受益人对应的被保人
						    	parent.fraInterface.Bnf<%=i+1%>Grid.setRowColData(<%=k%>, 2, "<%=tLCBnfSchema.getBnfNo()%>"); //受益人对应的主险
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
	          	loggerDebug("WbProposalQueryDetail","End Display 被保人受益人信息...");
	          	
	         // -------------------------------------生存保险金、年金、红利处理方式信息--------------------------------------------
                loggerDebug("WbProposalQueryDetail","Start Display 生存保险金、年金、红利处理方式信息...");                
                if (tTransferDataSet1 != null ) {
                		loggerDebug("WbProposalQueryDetail","生存保险金、年金、红利处理方式数目：" + tTransferDataSet1.size());
						%>
						<script>parent.fraInterface.DealType<%=i+1%>Grid.clearData("DealType<%=i+1%>Grid");</script>
						<%
						for (int k = 0; k < tTransferDataSet1.size(); k++) { // 一个信息
							TransferData tTransferData1 = (TransferData) tTransferDataSet1.get(k);
							LCPolSchema tLCPolSchema1 = tLCPolSet1.get(k + 1);
							String getYearS ="";
							int getYear = tLCPolSchema1.getGetYear();
							if(getYear>0)
								getYearS = String.valueOf(getYear);
							%>	
						    	<script language="javascript">
						    		parent.fraInterface.DealType<%=i+1%>Grid.addOne("DealType<%=i+1%>Grid");
						    	parent.fraInterface.DealType<%=i+1%>Grid.setRowColData(<%=k%>, 1, "<%=i+1%>"); //对应的被保人
							   	parent.fraInterface.DealType<%=i+1%>Grid.setRowColData(<%=k%>, 2, "<%=tLCPolSchema1.getInsuredNo()%>"); //对应的主险
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
         	   loggerDebug("WbProposalQueryDetail","End Display 生存保险金、年金、红利处理方式信息...");
            	
            }  
        }         
        loggerDebug("WbProposalQueryDetail","End Display 被保人相关信息...");   
        
      //--------------------------------------------------------------------------------------
    	// -------------------------------------告知信息--------------------------------------------
      loggerDebug("WbProposalQueryDetail","Start Display 告知信息...");      
		LCCustomerImpartSet mLCCustomerImpartSet = new LCCustomerImpartSet(); 
		mLCCustomerImpartSet.set(tLCCustomerImpartSet);
		if(mLCCustomerImpartSet!=null)
		{			
			int impartCount = mLCCustomerImpartSet.size();
			loggerDebug("WbProposalQueryDetail","告知数目：" + impartCount);
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
			   	parent.fraInterface.ImpartGrid.setRowColData(<%=i-1%>, 7, "1"); //告知是否有效 --有效
		    	</script>
		  <%
		  	}
		}		
      loggerDebug("WbProposalQueryDetail","End Display 告知信息...");
			     
	} // end of if
  
  //如果在Catch中发现异常，则不从错误类中提取错误信息
  if (FlagStr == "Fail") {
	  if(tBussNoType.equals("TB"))
		  tError = TBXMLTransfer1.mErrors; 
  	  else if(tBussNoType.equals("JM"))
  		  tError = JMTBXMLTransfer1.mErrors;
    
    if (!tError.needDealError()) {                          
    	Content = " 查询成功! ";
    	FlagStr = "Succ";
    } else {
    	Content = " 查询失败2，原因是:" + tError.getFirstError();
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
    //增加随动处理
    try { parent.fraInterface.setFocus(); } catch(e) {}
  }
  
  //复核修改、问题修改
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
  
  //复核抽查
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
