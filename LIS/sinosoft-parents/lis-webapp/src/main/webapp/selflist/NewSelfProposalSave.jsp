<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//程序名称：NewSelfProposalInputSave.jsp
//程序功能：新自助卡单-客户信息录入
//创建日期：2010-01-25 
//创建人  ：yanglh
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.tb.*"%>
<%@page import="com.sinosoft.lis.ebusiness.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.cbcheck.*"%>
<%@page import="java.util.*"%>

<%

	    loggerDebug("NewSelfProposalSave","开始执行SelfProposalInputSave.jsp");
	
		//输出参数
		CErrors tError = null;
		String FlagStr = "";
		String Content = "";
		String tAction = "";
		String tOperate = "";

	    
		Vector tVector=new Vector();	
		VData tResult=new VData();

 
	 	//GlobalInput tG = new GlobalInput();	
	 	//tG = ( GlobalInput )session.getValue( "GI" );
	 	//loggerDebug("NewSelfProposalSave","tG: "+tG);

	 	tAction = request.getParameter("fmAction");
	 	loggerDebug("NewSelfProposalSave","用户选择的操作为tAction1:"+tAction);
	 	
	 	//保单印刷号,对于自助卡单就是保单号
	 	String tPrtNo=request.getParameter("CertifyNo");
	 	loggerDebug("NewSelfProposalSave","用户选择的操作的卡号:"+tPrtNo);

	
	   //激活确认     
       if (tAction.equals("Confirm")) 
       {
			//保单信息部分
			loggerDebug("NewSelfProposalSave","开始设置保单基本信息...");
    		LCPolSchema tLCPolSchema = new LCPolSchema();
    		 
    		tLCPolSchema.setContNo(tPrtNo);
    		tLCPolSchema.setPrtNo(tPrtNo);
          tLCPolSchema.setInsuredPeoples("1"); //被保人人数
          tLCPolSchema.setPolTypeFlag("0"); //保单类型标记 0 --个人单,1 --无名单,2 --（团单）公共帐户  
    		tLCPolSchema.setCValiDate(request.getParameter("CValiDate"));
    		tLCPolSchema.setStandbyFlag1("电话"); //卡单激活类型
    		loggerDebug("NewSelfProposalSave","Save:CValidate:"+tLCPolSchema.getCValiDate());

         	loggerDebug("NewSelfProposalSave","设置保单基本信息结束...");       
          		
    		// 投保人信息部分
    		loggerDebug("NewSelfProposalSave","开始设置投保人基本信息...");
    		LCAppntSchema tLCAppntSchema = new LCAppntSchema();
    			
    		tLCAppntSchema.setContNo(tPrtNo); //保单号
    		tLCAppntSchema.setPrtNo(tPrtNo);
    		tLCAppntSchema.setGrpContNo("00000000000000000000");
    		tLCAppntSchema.setAppntGrade("M"); //投保人级别:M ---主投保人,S ---从头保人
    		tLCAppntSchema.setAppntName(request.getParameter("AppntName"));              //姓名 
    		tLCAppntSchema.setAppntSex(request.getParameter("AppntSex"));                //性别
    		tLCAppntSchema.setAppntBirthday(request.getParameter("AppntBirthday"));      //出生日期 
    		tLCAppntSchema.setIDType(request.getParameter("AppntIDType"));          //证件类型
    		tLCAppntSchema.setIDNo(request.getParameter("AppntIDNo"));              //证件号码     		
    		tLCAppntSchema.setOccupationType(request.getParameter("AppntOccupationType"));      //职业类别
    		tLCAppntSchema.setOccupationCode(request.getParameter("AppntOccupationCode"));      //职业代码
//    		tLCAppntSchema.setRelationToInsured(request.getParameter("RelationToLCInsured")); //与被保险人关系
//    		loggerDebug("NewSelfProposalSave","投保人与被保人关系:"+tLCAppntSchema.getRelationToInsured());
    		
    		LCAddressSchema tLCAddressSchema = new LCAddressSchema(); 
    		tLCAddressSchema.setPostalAddress(request.getParameter("AppntPostalAddress")); //联系地址
    		tLCAddressSchema.setZipCode(request.getParameter("AppntZipCode"));        //邮政编码
    		tLCAddressSchema.setPhone(request.getParameter("AppntPhone"));            //联系电话 
    		tLCAddressSchema.setEMail(request.getParameter("AppntEMail"));            //电子邮箱
    			
    		loggerDebug("NewSelfProposalSave","设置投保人信息结束...");    			
    			
    		loggerDebug("NewSelfProposalSave","开始设置被保人基本信息...");
		String tGridNo[] = request.getParameterValues("LCInsuredGridNo");  //序号
    String tGrid1 [] = request.getParameterValues("LCInsuredGrid1"); //姓名
    String tGrid2 [] = request.getParameterValues("LCInsuredGrid2"); //性别
    String tGrid3 [] = request.getParameterValues("LCInsuredGrid3"); //出生日期
    String tGrid4 [] = request.getParameterValues("LCInsuredGrid4"); //证件类型
    String tGrid5 [] = request.getParameterValues("LCInsuredGrid5"); //证件号码
    String tGrid6 [] = request.getParameterValues("LCInsuredGrid6"); //职业类别
    String tGrid7 [] = request.getParameterValues("LCInsuredGrid7"); //职业代码
    String tGrid8 [] = request.getParameterValues("LCInsuredGrid8"); //联系地址
    String tGrid9 [] = request.getParameterValues("LCInsuredGrid9"); //邮政编码
    String tGrid10 [] = request.getParameterValues("LCInsuredGrid10"); //联系电话
    String tGrid11 [] = request.getParameterValues("LCInsuredGrid11"); //电子邮箱
    String tGrid12 [] = request.getParameterValues("LCInsuredGrid12"); //与投保人关系

		LCInsuredSet tLCInsuredSet = new LCInsuredSet();    
		LCAddressSet tLCAddressSet2 = new LCAddressSet(); 		
		for(int pi=0;pi< tGridNo.length;pi++){			
			//被保人信息
    	LCInsuredSchema tLCInsuredSchema = new LCInsuredSchema();
			LCAddressSchema tLCAddressSchema2 = new LCAddressSchema(); 		
			if("00".equals(tGrid12[pi]))
			{
				//被保人是投保人本人
				tLCInsuredSchema.setContNo(tPrtNo); //保单号
				tLCInsuredSchema.setPrtNo(tPrtNo); //保单号
				tLCInsuredSchema.setGrpContNo("00000000000000000000");
				tLCInsuredSchema.setRelationToMainInsured("00");
				tLCInsuredSchema.setSequenceNo(tGridNo[pi]);
	    	tLCInsuredSchema.setName(tLCAppntSchema.getAppntName());              //姓名 
	    	tLCInsuredSchema.setSex(tLCAppntSchema.getAppntSex());                //性别
	    	tLCInsuredSchema.setBirthday(tLCAppntSchema.getAppntBirthday());      //出生日期 
	    	tLCInsuredSchema.setIDType(tLCAppntSchema.getIDType());          //证件类型
	    	tLCInsuredSchema.setIDNo(tLCAppntSchema.getIDNo());              //证件号码 	    		
	    	tLCInsuredSchema.setOccupationType(tLCAppntSchema.getOccupationType());       //职业类别
	    	tLCInsuredSchema.setOccupationCode(tLCAppntSchema.getOccupationCode());       //职业代码
	    	tLCInsuredSchema.setRelationToAppnt("00");
	    	tLCAddressSchema2.setPostalAddress(request.getParameter("AppntPostalAddress")); //联系地址
    		tLCAddressSchema2.setZipCode(request.getParameter("AppntZipCode"));        //邮政编码
    		tLCAddressSchema2.setPhone(request.getParameter("AppntPhone"));            //联系电话 
    		tLCAddressSchema2.setEMail(request.getParameter("AppntEMail"));            //电子邮箱
			}
			else
			{
				//投保人是被保人的父亲或母亲
				tLCInsuredSchema.setContNo(tPrtNo); //保单号
				tLCInsuredSchema.setPrtNo(tPrtNo); //保单号
				tLCInsuredSchema.setGrpContNo("00000000000000000000");
				tLCInsuredSchema.setRelationToMainInsured("00");
				tLCInsuredSchema.setSequenceNo(tGridNo[pi]);
	    	tLCInsuredSchema.setName(tGrid1[pi]);              //姓名 
	    	tLCInsuredSchema.setSex(tGrid2[pi]);                //性别
	    	tLCInsuredSchema.setBirthday(tGrid3[pi]);      //出生日期 
	    	tLCInsuredSchema.setIDType(tGrid4[pi]);          //证件类型
	    	tLCInsuredSchema.setIDNo(tGrid5[pi]);              //证件号码 
	    	tLCInsuredSchema.setOccupationType(tGrid6[pi]);       //职业类别
	    	tLCInsuredSchema.setOccupationCode(tGrid7[pi]);       //职业代码
	    	tLCInsuredSchema.setRelationToAppnt(tGrid12[pi]);
	    	tLCAddressSchema2.setPostalAddress(tGrid8[pi]); //联系地址
    		tLCAddressSchema2.setZipCode(tGrid9[pi]);        //邮政编码
    		tLCAddressSchema2.setPhone(tGrid10[pi]);            //联系电话 
    		tLCAddressSchema2.setEMail(tGrid11[pi]);            //电子邮箱
			}
			tLCInsuredSet.add(tLCInsuredSchema);
			tLCAddressSet2.add(tLCAddressSchema2);
		}
				
			loggerDebug("NewSelfProposalSave","被置投保人信息结束...");		
				
			TransferData tTransferData = new TransferData();
			tTransferData.setNameAndValue("SelfFlag", "new");				
			tVector.addElement(tAction);
			tVector.add("HX"); //表示数据是从核心业务系统传到后台
			tVector.addElement(tLCPolSchema);
			tVector.addElement(tLCAppntSchema);
			tVector.addElement(tLCInsuredSet);
			tVector.addElement(tLCAddressSchema);
			tVector.addElement(tLCAddressSet2);
			tVector.add(tTransferData);	 
				 
			//激活处理类
			try
			{
			  	//将操作类型，管理机构，操作员添加到容器中
			    //激活处理类
			    ActivateBL tActivateBL=new ActivateBL();
			    tResult=(VData)tActivateBL.submitData(tVector);
			      	
			    loggerDebug("NewSelfProposalSave","返回的标识位:"+tResult.get(0));
			    loggerDebug("NewSelfProposalSave","返回的提示信息:"+tResult.get(1));
			}
			catch(Exception ex)
			{
			    Content = "激活失败，原因是:" + ex.toString();
			    FlagStr = "Fail";
			}


			if (FlagStr=="")
			{
			  if (tResult.get(0).equals("fail"))
			  {
			      Content = (String)tResult.get(1);
			      FlagStr = "Fail";
			  }
			  else
			  {
				  String tempContent=(String)tResult.get(1);
				  String enddate = tempContent.substring(tempContent.lastIndexOf("||") + 2,tempContent.length());
			      loggerDebug("NewSelfProposalSave","SelfProposalSave-enddate:"+enddate);
			      String tt=tempContent.substring(0,tempContent.lastIndexOf("||"));
			      String cvalidate = tt.substring(tt.lastIndexOf("||") + 2,tt.length());
			      loggerDebug("NewSelfProposalSave","SelfProposalSave-cvalidate:"+cvalidate);
			      String RealContent=tempContent.substring(0,tempContent.indexOf("||")-1);
			      Content = RealContent+",有效日期为"+cvalidate+"日0时至"+enddate+"日0时!";
			      FlagStr = "Success";
			  }
			}
		    loggerDebug("NewSelfProposalSave","Flag : " + FlagStr + " -- Content : " + Content);
    }
%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

