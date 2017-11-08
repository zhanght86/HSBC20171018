<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<!--用户校验类-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.tb.*"%>
<%@page import="java.lang.reflect.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
 <%@page import="com.sinosoft.service.*" %>
<%
  //输出参数
  CErrors tError = null;
  String FlagStr = "Fail";
  String Content = "";
  String statusFlag = "";
  String customerNo = "";  //记录被保人客户号，用来与投保人客户号进行判断
  loggerDebug("ProposalQueryDetail","\n\n------ProposalQueryDetail start------");
  // 保单信息部分
  LCPolSchema tLCPolSchema   = new LCPolSchema();
  // 准备传输数据 VData
  tLCPolSchema.setPolNo(request.getParameter("PolNo"));
  tLCPolSchema.setContNo(request.getParameter("ContNo"));
  String tLoadFlag=(String)request.getParameter("LoadFlag");
  loggerDebug("ProposalQueryDetail","tLoadFlag"+tLoadFlag);
  VData tVData = new VData();
  tVData.addElement( tLCPolSchema );
  // 数据传输
  
   String busiName="tbProposalQueryUI";
   BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  
 // ProposalQueryUI tProposalQueryUI   = new ProposalQueryUI();
  if (!tBusinessDelegate.submitData( tVData, "QUERY||DETAIL", busiName)) {
      Content = "查询失败，原因是: " + tBusinessDelegate.getCErrors().getError(0).errorMessage;
      FlagStr = "Fail";
  }else {
    tVData.clear();
    tVData = tBusinessDelegate.getResult();
    // 保单信息
    LCPolSchema mLCPolSchema = new LCPolSchema();
    mLCPolSchema.setSchema((LCPolSchema)tVData.getObjectByObjectName("LCPolSchema",0));

//    String tContType = mLCPolSchema.getContType();
//    String tLoadFlag = "3";
//    if( tContType.equals( "2" ))   // 集体下的个人投保单
//       tLoadFlag = "4";
//add by yaory 表lcpol中字段mainpolno
loggerDebug("ProposalQueryDetail","mainpolno======"+mLCPolSchema.getMainPolNo());
 session.putValue("MainRiskPolNo",mLCPolSchema.getMainPolNo());
%>
<html>
<head>
<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="ProposalInput.js"></SCRIPT>
</head>
<body>
<script language="javascript">
try{parent.VD.gVSwitch.addVar("mainRiskPolNo","","<%=mLCPolSchema.getMainPolNo()%>");}catch(ex){};
//add by yaory
//parent.fraInterface.fm.hidepolno.value="<%=mLCPolSchema.getMainPolNo()%>";
//alert(parent.fraInterface.fm.MainPolNo.value);
//alert("mainriskpolno:"+parent.VD.gVSwitch.getVar("mainRiskPolNo"));

    //alert("mainriskpolno:"+parent.VD.gVSwitch.getVar("mainRiskPolNo"));
    try {
    //alert("parent.fraInterface.LoadFlag"+parent.fraInterface.LoadFlag);
      if (parent.fraInterface.LoadFlag == 5||parent.fraInterface.LoadFlag ==25) {
        parent.fraInterface.getRiskInput("<%=mLCPolSchema.getRiskCode()%>", "5",true);
      } else {
    //  alert("mLCPolSchema.getRiskCode()"+"<%=mLCPolSchema.getRiskCode()%>");
        parent.fraInterface.getRiskInput("<%=mLCPolSchema.getRiskCode()%>", parent.fraInterface.LoadFlag,true);

      }
    } catch(ex) {};
    
    function setValue(){
<%
    //集体下的个人投保单
    int elementsNum;
    Class c = mLCPolSchema.getClass();
    Field f[] = c.getDeclaredFields();
//loggerDebug("ProposalQueryDetail","f.length:"+f.length);
    for(elementsNum=0; elementsNum<f.length; elementsNum++) {

      if (!mLCPolSchema.getV(f[elementsNum].getName()).equals("null")) {
loggerDebug("ProposalQueryDetail",elementsNum+":--:"+mLCPolSchema.getV(f[elementsNum].getName()));
        out.println("try{");
        //out.println("parent.fraInterface.$('#"+f[elementsNum].getName()+" input').attr('value') ='"+mLCPolSchema.getV(f[elementsNum].getName())+"';");
        out.println("parent.fraInterface.document.all('" + f[elementsNum].getName() + "').value='" + mLCPolSchema.getV(f[elementsNum].getName()) + "';");
        out.println("} catch(ex) {};");
      }
      if ((elementsNum == 132) && (f[elementsNum].getName()=="Currency")) {
loggerDebug("ProposalQueryDetail",elementsNum+":"+f[elementsNum].getName()+":"+mLCPolSchema.getV(f[elementsNum].getName()));
    	        out.println("try{");
    	        out.println("parent.fraInterface.document.all('CurrencyCode').value='" + mLCPolSchema.getV(f[elementsNum].getName()) + "';");
    	        out.println("} catch(ex) {};");
    	        
    	        String sql3 = " select currname from ldcurrency  where currcode = '" + mLCPolSchema.getV(f[elementsNum].getName()) + "' ";
    	        TransferData sTransferData3=new TransferData();
 		        sTransferData3.setNameAndValue("SQL", sql3);
 		        VData sVData3 = new VData();
 	            sVData3.add(sTransferData3);
 	            BusinessDelegate tBusinessDelegate3=BusinessDelegate.getBusinessDelegate();
 	            String sCurrencyName = "";
 	            if(tBusinessDelegate3.submitData(sVData3, "getOneValue", "ExeSQLUI"))
 	            {
 	            	sCurrencyName = (String)tBusinessDelegate3.getResult().getObjectByObjectName("String", 0);
 	            }
 	           out.println("try{");
   	           out.println("parent.fraInterface.document.all('CurrencyName').value='" + sCurrencyName + "';");
   	           out.println("} catch(ex) {};");
    	      }
      
      if(f[elementsNum].getName().equals("Amnt")){
    	  DecimalFormat df= new DecimalFormat("0.000");//指定转换的格式
    	  String amnt =  mLCPolSchema.getV(f[elementsNum].getName());
    	  
    	  out.println("try{");
	        out.println("parent.fraInterface.document.all('Amnt').value='" +df.format(Double.parseDouble(amnt)) + "';");
	        out.println("} catch(ex) {};");
    	 
      }
    }
    //设置投保单状态标记
    try {
      if (mLCPolSchema.getApproveFlag().equals("0")) statusFlag = "未进行复核";
      else if (mLCPolSchema.getApproveFlag().equals("1")) statusFlag = "复核未通过";
      else if (mLCPolSchema.getApproveFlag().equals("9") && mLCPolSchema.getUWFlag().equals("0")) statusFlag = "复核通过，待核保";
      else if (mLCPolSchema.getApproveFlag().equals("9") && mLCPolSchema.getUWFlag().equals("5")) statusFlag = "复核通过，自动核保未通过，待人工核保";
      else if (mLCPolSchema.getApproveFlag().equals("9") && mLCPolSchema.getUWFlag().equals("9")) statusFlag = "复核通过，核保通过";
    }
    catch(Exception e) {
      loggerDebug("ProposalQueryDetail","statusFlag throw Error");
    }
    loggerDebug("ProposalQueryDetail","statusFlag:" + statusFlag);
    loggerDebug("ProposalQueryDetail","End Display 保单信息...");


   // 责任信息（未整理）
   LCDutySet mLCDutySet = new LCDutySet();
   mLCDutySet.set((LCDutySet)tVData.getObjectByObjectName("LCDutySet",0));
   LMDutySet mLMDutySet = new LMDutySet();
   mLMDutySet.set((LMDutySet)tVData.getObjectByObjectName("LMDutySet",0));
   int dutyCount = mLCDutySet.size();
   loggerDebug("ProposalQueryDetail","dutyCount:"+dutyCount);
   loggerDebug("ProposalQueryDetail","mainPolNo:"+mLCPolSchema.getMainPolNo());
   loggerDebug("ProposalQueryDetail","mLMDutySet:"+mLMDutySet.encode());
   if(dutyCount==1)
    {
         c = mLCDutySet.get(1).getClass();
         f = c.getDeclaredFields();
         for(elementsNum=0; elementsNum<f.length; elementsNum++)
         {

            if (!mLCDutySet.get(1).getV(f[elementsNum].getName()).equals("null"))
            {
              out.println("try{");
              out.println("parent.fraInterface.$('#"+f[elementsNum].getName()+" input').attr('value','"+mLCPolSchema.getV(f[elementsNum].getName())+"');");
              //out.println("parent.fraInterface.document.all('" + f[elementsNum].getName() + "').value='" + mLCDutySet.get(1).getV(f[elementsNum].getName()) + "';");
              out.println("} catch(ex) {};");
            }
         }
    }

%>



//parent.fraInterface.document.all('ProposalNo').value='<%=mLCPolSchema.getProposalNo()%>';
//alert("parent.fraInterface.document.all('ProposalNo')"+parent.fraInterface.document.all('ProposalNo').value);
//alert("ProposalNo"+'<%=mLCPolSchema.getProposalNo()%>');
parent.fraInterface.initDutyGrid();
parent.fraInterface.DutyGrid.clearData();

<%
   for (int i = 1; i <= dutyCount; i++) {
         LCDutySchema mLCDutySchema = mLCDutySet.get(i);
         LMDutySchema mLMDutySchema = mLMDutySet.get(i);
%>

					//如果需要责任

					//alert(parent.fraInterface.document.all('inpNeedDutyGrid').value);
          if (parent.fraInterface.document.all('inpNeedDutyGrid').value==1 )
          {
          	parent.fraInterface.DutyGrid.addOne();
          	parent.fraInterface.DutyGrid.checkBoxSel(<%=i%>);
          	parent.fraInterface.DutyGrid.setRowColData(<%=i-1%>,1,'<%=mLCDutySchema.getDutyCode()%>');
						parent.fraInterface.DutyGrid.setRowColData(<%=i-1%>,2,'<%=mLMDutySchema.getDutyName()%>');
						parent.fraInterface.DutyGrid.setRowColData(<%=i-1%>,3,'<%=mLCDutySchema.getInsuYear()%>');
						parent.fraInterface.DutyGrid.setRowColData(<%=i-1%>,4,'<%=StrTool.cTrim(mLCDutySchema.getInsuYearFlag())%>');
						parent.fraInterface.DutyGrid.setRowColData(<%=i-1%>,5,'<%=mLCDutySchema.getPayEndYear()%>');
						parent.fraInterface.DutyGrid.setRowColData(<%=i-1%>,6,'<%=StrTool.cTrim(mLCDutySchema.getPayEndYearFlag())%>');
						parent.fraInterface.DutyGrid.setRowColData(<%=i-1%>,7,'<%=StrTool.cTrim(mLCDutySchema.getGetYearFlag())%>');
						parent.fraInterface.DutyGrid.setRowColData(<%=i-1%>,8,'<%=mLCDutySchema.getGetYear()%>');
						parent.fraInterface.DutyGrid.setRowColData(<%=i-1%>,9,'<%=StrTool.cTrim(mLCDutySchema.getStandbyFlag1())%>');
						parent.fraInterface.DutyGrid.setRowColData(<%=i-1%>,10,'<%=StrTool.cTrim(mLCDutySchema.getStandbyFlag2())%>');
						parent.fraInterface.DutyGrid.setRowColData(<%=i-1%>,11,'<%=StrTool.cTrim(mLCDutySchema.getStandbyFlag3())%>');
						parent.fraInterface.DutyGrid.setRowColData(<%=i-1%>,12,'<%=StrTool.cTrim(mLCDutySchema.getPremToAmnt())%>');
						parent.fraInterface.DutyGrid.setRowColData(<%=i-1%>,13,'<%=mLCDutySchema.getPrem()%>');
						parent.fraInterface.DutyGrid.setRowColData(<%=i-1%>,14,'<%=mLCDutySchema.getAmnt()%>');
						parent.fraInterface.DutyGrid.setRowColData(<%=i-1%>,15,'<%=mLCDutySchema.getMult()%>');
						parent.fraInterface.DutyGrid.setRowColData(<%=i-1%>,16,'<%=StrTool.cTrim(mLCDutySchema.getCalRule())%>');
						parent.fraInterface.DutyGrid.setRowColData(<%=i-1%>,17,'<%=mLCDutySchema.getFloatRate()%>');
						parent.fraInterface.DutyGrid.setRowColData(<%=i-1%>,18,'<%=mLCDutySchema.getPayIntv()%>');
						parent.fraInterface.DutyGrid.setRowColData(<%=i-1%>,19,'<%=mLCDutySchema.getGetLimit()%>');
						parent.fraInterface.DutyGrid.setRowColData(<%=i-1%>,20,'<%=mLCDutySchema.getGetRate()%>');
						parent.fraInterface.DutyGrid.setRowColData(<%=i-1%>,21,'<%=mLCDutySchema.getSSFlag()%>');
						parent.fraInterface.DutyGrid.setRowColData(<%=i-1%>,22,'<%=mLCDutySchema.getPeakLine()%>');



				  }


<%
   }
   loggerDebug("ProposalQueryDetail","End Display 责任信息...");
%>

<%
      // 保费项信息
      LCPremSet mLCPremSet = new LCPremSet();
      mLCPremSet.set((LCPremSet)tVData.getObjectByObjectName("LCPremSet",0));

      LMDutyPaySet mLMDutyPaySet = new LMDutyPaySet();
      mLMDutyPaySet.set((LMDutyPaySet)tVData.getObjectByObjectName("LMDutyPaySet",0));
      int PayPlanCount = mLCPremSet.size();
      loggerDebug("ProposalQueryDetail","PayPlanCount:"+PayPlanCount);

%>

      try
      {
         if(parent.fraInterface.initPremGrid()==true)
         {
           parent.fraInterface.PremGrid.clearData();
<%
    for (int i = 1; i <=PayPlanCount; i++)
    {
%>
               parent.fraInterface.PremGrid.addOne();
               parent.fraInterface.PremGrid.checkBoxSel(<%=i%>);
               parent.fraInterface.PremGrid.setRowColData(<%=i-1%>, 1, "<%=mLCPremSet.get(i).getDutyCode()%>");
               parent.fraInterface.PremGrid.setRowColData(<%=i-1%>, 2, "<%=mLCPremSet.get(i).getPayPlanCode()%>");
               parent.fraInterface.PremGrid.setRowColData(<%=i-1%>, 3, "<%=mLMDutyPaySet.get(i).getPayPlanName()%>");

               if(<%=mLCPremSet.get(i).getPrem()%>>0)
               {

                 //parent.fraInterface.PremGrid.setRowColData(<%=i-1%>, 4, "<%=mLCPremSet.get(i).getPrem()/mLCPremSet.get(i).getRate()%>");
                 //parent.fraInterface.PremGrid.setRowColData(<%=i-1%>, 5, "<%=mLCPremSet.get(i).getRate()%>");
                 parent.fraInterface.PremGrid.setRowColData(<%=i-1%>, 4, "<%=mLCPremSet.get(i).getPrem()%>");
                 parent.fraInterface.PremGrid.setRowColData(<%=i-1%>, 5, "<%=mLCPremSet.get(i).getRate()%>");
              }
              else
              {
                parent.fraInterface.PremGrid.setRowColData(<%=i-1%>, 4, "0");
                parent.fraInterface.PremGrid.setRowColData(<%=i-1%>, 5, "0");

              }
<%
      }
%>
           }
      }
      catch(ex){}


<%

   loggerDebug("ProposalQueryDetail","End Display 保费项信息...");
%>

<%
      // 个人投保人信息
      LCAppntSchema mLCAppntSchema = new LCAppntSchema();
      mLCAppntSchema.setSchema((LCAppntSchema)tVData.getObjectByObjectName("LCAppntSchema",0));

      // 集体投保人信息
      LCGrpAppntSchema mLCGrpAppntSchema = new LCGrpAppntSchema();
      mLCGrpAppntSchema.setSchema((LCGrpAppntSchema)tVData.getObjectByObjectName("LCGrpAppntSchema",0));

%>


<%
    loggerDebug("ProposalQueryDetail","End Display 集体投保人信息...");
    // 被保人信息
    LCInsuredSet mLCInsuredSet = new LCInsuredSet();
    mLCInsuredSet.set((LCInsuredSet)tVData.getObjectByObjectName("LCInsuredSet",0));
    int insuredCount = mLCInsuredSet.size();

%>

<%
    loggerDebug("ProposalQueryDetail","End Display 被保人信息...");
%>

<%
     // 险种信息的给付方法
      LCGetSet mLCGetSet = new LCGetSet();
      mLCGetSet.set((LCGetSet)tVData.getObjectByObjectName("LCGetSet",0));
      int lcgetCount = mLCGetSet.size();
			loggerDebug("ProposalQueryDetail","lcgetCount===="+lcgetCount);
      for (int i = 1; i <= lcgetCount; i++)
      {
         LCGetSchema mLCGetSchema = mLCGetSet.get(i);
         loggerDebug("ProposalQueryDetail","mLCGetSchema:" + mLCGetSchema.encode() + "\n" + mLCGetSchema.getGetDutyKind());
       
         if (mLCGetSchema.getGetDutyKind()!=null && !mLCGetSchema.getGetDutyKind().equals(""))
         {
           //对于618险种只显示618041生存领取
           loggerDebug("ProposalQueryDetail","mLCGetSchema.getDutyCode()="+mLCGetSchema.getDutyCode());
           loggerDebug("ProposalQueryDetail","mLCGetSchema.getGetDutyCode()="+mLCGetSchema.getGetDutyCode());
           if(mLCGetSchema.getDutyCode().equals("618000")&&!mLCGetSchema.getGetDutyCode().equals("618041")){
             continue;
           }
%>

  try {
  	
  	parent.fraInterface.document.all("GetDutyKind").value="<%=mLCGetSchema.getGetDutyKind()%>";
    
    <!---------------------------------------------------Beg-->
    <!--Edited by chenrong------------------------------------>
    <!--险种的GetDutyKind包含多个值时，拆分GetDutyKind字符串-->
    parent.fraInterface.splitDutyKind();
    <!---------------------------------------------------End-->
  }
  catch(ex) {
  }

<%		 //领取方式的显示---add by haopan -20070419
         String chkSQL="select 1 from LMDutyCtrl where dutycode='"+mLCGetSchema.getDutyCode()+"' and othercode='"+mLCGetSchema.getGetDutyCode()+"' and fieldname='GetDutyKind'";
         loggerDebug("ProposalQueryDetail","领取方式是否取页面录入chkSQL==="+chkSQL);
         ExeSQL exeSql = new ExeSQL();
           SSRS ssrs = new SSRS();
           TransferData sTransferData=new TransferData();
	       sTransferData.setNameAndValue("SQL", chkSQL);
	       VData sVData = new VData();
           sVData.add(sTransferData);
           BusinessDelegate tBusinessDelegate2=BusinessDelegate.getBusinessDelegate();
           if(tBusinessDelegate2.submitData(sVData, "execSQL", "ExeSQLUI"))
           {
        	   ssrs = (SSRS)tBusinessDelegate2.getResult().getObjectByObjectName("SSRS", 0);
           }
//         SSRS ssrs = exeSql.execSQL(chkSQL);
         int i_count = ssrs.getMaxRow();
         if (i_count == 0)
         {
         		continue;
         	}
        else
        	{
     				loggerDebug("ProposalQueryDetail","跳出循环");
        		break;
        		}
         }
      }
      loggerDebug("ProposalQueryDetail","End Display 给付方法...2");
%>

<%

      // 连带被保人信息（未整理）
      LCInsuredSet mLCSubInsuredSet = new LCInsuredSet();
      mLCSubInsuredSet.set((LCInsuredSet)tVData.getObjectByObjectName("LCInsuredSet",0));
      int insuredSubCount = mLCSubInsuredSet.size();
      loggerDebug("ProposalQueryDetail","End Display 给付方法...2" +insuredSubCount);
      int j=0;
      for (int i = 1; i <= insuredSubCount; i++) {
        LCInsuredSchema mLCSubInsuredSchema = mLCInsuredSet.get(i);
        /**这里if条件判断出错，跳过判断  20041124 wzw*/
        //if (mLCSubInsuredSchema.getInsuredGrade()!=null && mLCSubInsuredSchema.getInsuredGrade().equals("S")) {
        if(1==2){
%>

            parent.fraInterface.SubInsuredGrid.addOne("SubInsuredGrid");
            parent.fraInterface.SubInsuredGrid.setRowColData(<%=j%>, 1, "<%=mLCSubInsuredSchema.getInsuredNo()%>");
            parent.fraInterface.SubInsuredGrid.setRowColData(<%=j%>, 2, "<%=mLCSubInsuredSchema.getName()%>");
            parent.fraInterface.SubInsuredGrid.setRowColData(<%=j%>, 3, "<%=mLCSubInsuredSchema.getSex()%>");
            parent.fraInterface.SubInsuredGrid.setRowColData(<%=j%>, 4, "<%=mLCSubInsuredSchema.getBirthday()%>");
            parent.fraInterface.SubInsuredGrid.setRowColData(<%=j%>, 5, "<%=mLCSubInsuredSchema.getRelationToMainInsured()%>");

<%
         j++;
        }
      }
      loggerDebug("ProposalQueryDetail","End Display 连带被保人信息...");
%>

<%
      // 受益人信息
      LCBnfSet mLCBnfSet = new LCBnfSet();
      mLCBnfSet.set((LCBnfSet)tVData.getObjectByObjectName("LCBnfSet",0));
      int bnfCount = mLCBnfSet.size();
%>

      parent.fraInterface.BnfGrid.clearData("BnfGrid");

<%
      for (int i = 1; i <= bnfCount; i++)   {
        LCBnfSchema mLCBnfSchema = mLCBnfSet.get(i);
%>

  parent.fraInterface.BnfGrid.addOne("BnfGrid");
  parent.fraInterface.BnfGrid.setRowColData(<%=i-1%>, 1, "<%=mLCBnfSchema.getBnfType()%>");
  parent.fraInterface.BnfGrid.setRowColData(<%=i-1%>, 2, "<%=mLCBnfSchema.getName()%>");
  parent.fraInterface.BnfGrid.setRowColData(<%=i-1%>, 3, "<%=mLCBnfSchema.getSex()%>");
  parent.fraInterface.BnfGrid.setRowColData(<%=i-1%>, 4, "<%=mLCBnfSchema.getIDType()%>");
  parent.fraInterface.BnfGrid.setRowColData(<%=i-1%>, 5, "<%=mLCBnfSchema.getIDNo()%>");
  parent.fraInterface.BnfGrid.setRowColData(<%=i-1%>, 6, "<%=mLCBnfSchema.getRelationToInsured()%>");
  parent.fraInterface.BnfGrid.setRowColData(<%=i-1%>, 7, "<%=mLCBnfSchema.getBnfGrade()%>");
  parent.fraInterface.BnfGrid.setRowColData(<%=i-1%>, 8, "<%=mLCBnfSchema.getBnfLot()%>");
  parent.fraInterface.BnfGrid.setRowColData(<%=i-1%>, 10, "<%=mLCBnfSchema.getIDExpDate()%>");
  //hl
  parent.fraInterface.BnfGrid.setRowColData(<%=i-1%>, 12, "<%=mLCBnfSchema.getInsuredNo()%>");
  //parent.fraInterface.BnfGrid.setRowColData(<%=i-1%>, 8, "no method wzw");

<%
      }
      loggerDebug("ProposalQueryDetail","End Display 受益人信息...");

      // 特约信息
      LCSpecSet mLCSpecSet = new LCSpecSet();
      mLCSpecSet.set((LCSpecSet)tVData.getObjectByObjectName("LCSpecSet",0));
      int specCount = mLCSpecSet.size();
%>

  parent.fraInterface.SpecGrid.clearData("SpecGrid");

<%
      if (specCount==0) {
%>

  parent.fraInterface.SpecGrid.addOne("SpecGrid");
  parent.fraInterface.SpecGrid.setRowColData(0,1,"1");
  parent.fraInterface.SpecGrid.setRowColData(0,2,"1");

<%
      }
      String strPath = application.getRealPath("config//Conversion.config");

      for (int i = 1; i <= specCount; i++) {
        LCSpecSchema mLCSpecSchema = mLCSpecSet.get(i);
%>

  parent.fraInterface.SpecGrid.addOne("SpecGrid");
  parent.fraInterface.SpecGrid.setRowColData(<%=i-1%>, 1, "<%=mLCSpecSchema.getSpecCode()%>");
  parent.fraInterface.SpecGrid.setRowColData(<%=i-1%>, 2, "<%=mLCSpecSchema.getSpecType()%>");
  tStr = "<%=StrTool.Conversion(mLCSpecSchema.getSpecContent(), strPath)%>";
  tStr = Conversion(tStr);
  parent.fraInterface.SpecGrid.setRowColData(<%=i-1%>, 3, tStr);

<%
      }
      loggerDebug("ProposalQueryDetail","End Display 特约信息...");
    } // end of if

    //如果在Catch中发现异常，则不从错误类中提取错误信息
    if (FlagStr == "Fail") {
      tError = tBusinessDelegate.getCErrors();
      if (!tError.needDealError()) {
         Content = " 查询成功! ";
         FlagStr = "Succ";
      } else {
         Content = " 查询失败2，原因是:" + tError.getFirstError();
         FlagStr = "Fail";
      }
    }


     loggerDebug("ProposalQueryDetail",FlagStr);
     loggerDebug("ProposalQueryDetail",Content);
     loggerDebug("ProposalQueryDetail","------ProposalQueryDetail end------\n\n");

%>




    //得到界面的调用位置,默认为1,表示个人保单直接录入.
    /**********************************************
     *LoadFlag=1 -- 个人投保单直接录入
     *LoadFlag=2 -- 集体下个人投保单录入
     *LoadFlag=3 -- 个人投保单明细查询
     *LoadFlag=4 -- 集体下个人投保单明细查询
     *LoadFlag=5 -- 复核
     *LoadFlag=6 -- 查询
     *LoadFlag=7 -- 保全新保加人
     *LoadFlag=8 -- 保全新增附加险
     *LoadFlag=9 -- 无名单补名单
     *LoadFlag=10-- 浮动费率
     *LoadFlag=13-- 集体下投保单复核修改
     *LoadFlag=16-- 集体下投保单查询
     *LoadFlag=25-- 人工核保修改投保单
     *LoadFlag=99-- 随动定制
     *
     ************************************************/



  parent.fraInterface.emptyUndefined();
  //alert(parent.fraInterface.LoadFlag);

  //个人投保单直接录入
  if (parent.fraInterface.LoadFlag == 1) {
//tongmeng 2010-11-16 modify
     //初始化投连险
     parent.fraInterface.initULRisk();
     //初始化折扣信息
     parent.fraInterface.Discount();
    parent.fraInterface.inputButton.style.display = "none";
    parent.fraInterface.divButton.style.display = "";
    parent.fraInterface.modifyButton.style.display = "";
    parent.fraInterface.spanApprove.innerHTML = innerHTML;
    //////add by yaory for disabled Addappbutton when modify
//    parent.fraInterface.fm.riskbutton31.style.display='';
//    parent.fraInterface.fm.riskbutton32.style.display='';
//    parent.fraInterface.fm.riskbutton31.disabled='';
//    parent.fraInterface.fm.riskbutton32.disabled='';
    //parent.fraInterface.fm.riskbutton31.onclick="addAppRisk()";
    //parent.fraInterface.fm.riskbutton32.onclick="addAppRisk()";
    //////add by yaory for modify appaddrisk
                        //parent.fraInterface.fm.SelPolNo.value=="";
                        //alert(parent.fraInterface.fm.SelPolNo.value);
                        //parent.fraInterface.mSwitch.deleteVar("PolNo")='';
                        /////end add
     
  }

  //集体下个人投保单录入
  if (parent.fraInterface.LoadFlag == 2) {

    parent.fraInterface.inputButton.style.display = "none";
    parent.fraInterface.divButton.style.display = "";
    parent.fraInterface.modifyButton.style.display = "";
    parent.fraInterface.inputQuest.style.display = "none";
    try{
        var tCalRule = parent.fraInterface.document.all("CalRule").value;
	    if (tCalRule == "2")
        {
            parent.fraInterface.fm.FloatRate.disabled = false;
        }
        else
        {
            parent.fraInterface.fm.FloatRate.disabled = true;
        }
	}
	catch(ex){}
    //显示代码选择中文
    parent.fraInterface.showRiskParamCodeName();
  }

  //个人投保单明细查询
  if (parent.fraInterface.LoadFlag == 3) {
  	 //tongmeng 2010-11-16 modify
     //初始化投连险
     parent.fraInterface.initULRisk();
    parent.fraInterface.divButton.style.display = "none";

    //增加随动处理
    try { parent.fraInterface.setFocus(parent.fraInterface.document.all("PrtNo").value); } catch(e) {}

    parent.fraInterface.inputButton.style.display = "none";
    parent.fraInterface.divButton.style.display = "";
    parent.fraInterface.modifyButton.style.display = "";

    if (top.type!=null && top.type=="ChangePlan") {
      innerHTML = "<center> <INPUT class=common name=ChangePlanSub VALUE='修改附加险' TYPE=button onclick='parent.fraInterface.window.location=\"./ChangePlanSub.jsp?prtNo=" + parent.fraInterface.document.all("PrtNo").value + "&type=SubChangePlan\"'>";
    }
    if (top.type!=null && top.type=="SubChangePlan") {
      innerHTML = "<center> <INPUT class=common name=ChangePlanSub VALUE='修改附加险' TYPE=button onclick='parent.fraInterface.window.location=\"./ChangePlanSub.jsp?prtNo=" + parent.fraInterface.document.all("PrtNo").value + "&type=SubChangePlan\"'>";

      parent.fraInterface.deleteButton.style.display = "";
    }
    parent.fraInterface.spanApprove.style.display = "";
    parent.fraInterface.inputQuestButton.style.display = "none";
    parent.fraInterface.inputQuestButton.disabled = true ;
    
   
  }

  //集体下个人投保单明细查询
  if (parent.fraInterface.LoadFlag == 4) {

    parent.fraInterface.inputButton.style.display = "none";
    parent.fraInterface.updateButton.style.display = "none";
    parent.fraInterface.divButton.style.display = "";
    parent.fraInterface.inputQuest.style.display = "NONE";
    parent.fraInterface.divGrpContButton.style.display = "";
    parent.fraInterface.spanApprove.style.display = "";
    //显示代码选择中文
    parent.fraInterface.showCodeName();
  }


  //复核
  if (parent.fraInterface.LoadFlag == 5) {
   // parent.fraInterface.divButton.style.display = "none";
    //tongmeng 2010-11-16 modify
     //初始化投连险
    parent.fraInterface.initULRisk();
   parent.fraInterface.divRiskPlanSave.style.display = "none";
   parent.fraInterface.inputQuest.style.display = "none";

     
	  var strSQL="select count(*) from LWNotePad where otherno='"+parent.fraInterface.document.all("PrtNo").value+"'";
	
	  var arrResult1 = easyExecSql(strSQL);
	
    //增加随动处理
    try { parent.fraInterface.setFocus(parent.fraInterface.document.all("PrtNo").value); } catch(e) {}

    var innerHTML = "<center>"
		//tongmeng 2007-12-27 modify
		//屏蔽按钮
    innerHTML = innerHTML + " <INPUT class=cssButton VALUE='受益人影像定位 'name='gotoBnf' TYPE=hidden onclick='gotoBnf();'>"
    innerHTML = innerHTML + " <INPUT class=cssButton VALUE='问题件录入' TYPE=button onclick='QuestInput();'>"
    innerHTML = innerHTML + " <INPUT class=cssButton VALUE='问题件查询' TYPE=button onclick='QuestQuery();'>"
    innerHTML = innerHTML + " <INPUT class=cssButton VALUE='强制进入人工核保' TYPE=button onclick='forceUW();'>"
    innerHTML = innerHTML + " <INPUT class=cssButton VALUE='记事本查看（共"+arrResult1[0][0]+"条）' TYPE=button name='NotePadButton' onclick='showNotePad();'>"
    innerHTML = innerHTML + " <INPUT class=cssButton VALUE='复核通过 'TYPE=button onclick='inputConfirm(2);'>"
    innerHTML = innerHTML + " <INPUT class=cssButton VALUE='上一步' TYPE=button     onclick='returnparent();'>"
    innerHTML = innerHTML + "</center><br><br>";
    parent.fraInterface.spanApprove.innerHTML = innerHTML;
    parent.fraInterface.inputButton.style.display = "none";
    parent.fraInterface.spanApprove.style.display = "";



    //显示代码选择中文
    parent.fraInterface.showCodeName();
    
   
     
   // alert(2);
    
  }


  //查询
  if (parent.fraInterface.LoadFlag == 6) {
    parent.fraInterface.inputButton.style.display = "none";
    parent.fraInterface.inputQuest.style.display = "none";
    //“上一步”按钮
    parent.fraInterface.divUWContButton.style.display = "";
    innerHTML = parent.fraInterface.DivRiskCode.innerHTML.substring(0, parent.fraInterface.DivRiskCode.innerHTML.indexOf("</TR>"));
//    innerHTML = innerHTML + " <TD  class= title>投保单状态</TD>"
//    innerHTML = innerHTML + " <TD  class= input><Input class=readonly readonly name=statusFlag value='<%=statusFlag%>'></TD></tr></TBODY></table>";
    parent.fraInterface.DivRiskCode.innerHTML = innerHTML;

    //显示年龄
    parent.fraInterface.document.all("AppntBirthday").onmousemove = parent.fraInterface.showAppntAge;
    parent.fraInterface.document.all("Birthday").onmousemove = parent.fraInterface.showAge;

    //显示代码选择中文
    parent.fraInterface.showCodeName();
  }
  
  //新增附加险
  if (parent.fraInterface.LoadFlag == 8) {
     //alert(parent.fraInterface.LoadFlag);
     parent.fraInterface.inputButton.style.display = "none";
     parent.fraInterface.divInputContButtonBQ.style.display = "none";
     parent.fraInterface.divInputContButtonBQBack.style.display = "";
     parent.fraInterface.divButton.style.display = "";
     parent.fraInterface.divBqNSButton.style.display = "none";
     //alert(parent.fraInterface.PolSaveFlag);
     //alert(parent.VD.gVSwitch.getVar( "mainRiskPolNo"));
     //alert(parent.VD.gVSwitch.getVar( "SelPolNo" ));
     if((parent.fraInterface.PolSaveFlag=="1")&&(parent.VD.gVSwitch.getVar( "mainRiskPolNo" )!=parent.VD.gVSwitch.getVar( "SelPolNo" ))){
         //alert(11);
         parent.fraInterface.modifyButton.style.display = "";
     }
     //
  }
  
  //团体增人
  if (parent.fraInterface.LoadFlag == 7) {
     parent.fraInterface.inputButton.style.display = "none";
     parent.fraInterface.modifyButton.style.display = "";
  }

  //浮动费率
  if(parent.fraInterface.LoadFlag==10){

    try{
      parent.fraInterface.inputButton.style.display = "none";
      parent.fraInterface.updateButton.style.display = "";
      parent.fraInterface.divButton.style.display = "";
      parent.fraInterface.document.all("FloatRate").readOnly = false;
      parent.fraInterface.document.all("FloatRate").className = "common";
    }
    catch(ex)
    {

    }

    var innerHTML = "<center><input class=common name=SubModify VALUE='修改附加险' TYPE=button onclick='parent.fraInterface.window.location=\"../app/changeSubFloatRate.jsp?prtNo=" + parent.fraInterface.document.all("PrtNo").value + "\"'>";
    parent.fraInterface.spanApprove.innerHTML = innerHTML;
    parent.fraInterface.spanApprove.style.display = "";
    parent.fraInterface.inputQuestButton.style.display = "none";

  }



  //集体下投保单复核修改
  if (parent.fraInterface.LoadFlag == 13) {
    parent.fraInterface.inputButton.style.display = "none";
    parent.fraInterface.divButton.style.display = "";
    parent.fraInterface.modifyButton.style.display = "";

    if (top.type!=null && top.type=="ChangePlan") {
      innerHTML = "<center> <INPUT class=common name=ChangePlanSub VALUE='修改附加险' TYPE=button onclick='parent.fraInterface.window.location=\"./ChangePlanSub.jsp?prtNo=" + parent.fraInterface.document.all("PrtNo").value + "&type=SubChangePlan\"'>";
    }
    if (top.type!=null && top.type=="SubChangePlan") {
      innerHTML = "<center> <INPUT class=common name=ChangePlanSub VALUE='修改附加险' TYPE=button onclick='parent.fraInterface.window.location=\"./ChangePlanSub.jsp?prtNo=" + parent.fraInterface.document.all("PrtNo").value + "&type=SubChangePlan\"'>";
      parent.fraInterface.deleteButton.style.display = "";
    }
    parent.fraInterface.spanApprove.style.display = "";
    parent.fraInterface.inputQuestButton.style.display = "none";
    parent.fraInterface.inputQuestButton.disabled = true ;
  }



  //Q:??
  if (parent.fraInterface.LoadFlag == 14) {
    parent.fraInterface.inputButton.style.display = "none";
    parent.fraInterface.divButton.style.display = "";

    parent.fraInterface.modifyButton.style.display = "";

    if (top.type!=null && top.type=="ChangePlan") {
      innerHTML = "<center> <INPUT class=common name=ChangePlanSub VALUE='修改附加险' TYPE=button onclick='parent.fraInterface.window.location=\"./ChangePlanSub.jsp?prtNo=" + parent.fraInterface.document.all("PrtNo").value + "&type=SubChangePlan\"'>";
    }
    if (top.type!=null && top.type=="SubChangePlan") {
      innerHTML = "<center> <INPUT class=common name=ChangePlanSub VALUE='修改附加险' TYPE=button onclick='parent.fraInterface.window.location=\"./ChangePlanSub.jsp?prtNo=" + parent.fraInterface.document.all("PrtNo").value + "&type=SubChangePlan\"'>";

      parent.fraInterface.deleteButton.style.display = "";
    }
    parent.fraInterface.spanApprove.style.display = "";
    parent.fraInterface.inputQuestButton.style.display = "none";
    parent.fraInterface.inputQuestButton.disabled = true ;
  }



  //集体下投保单查询
  if (parent.fraInterface.LoadFlag == 16) {

    parent.fraInterface.inputButton.style.display = "none";
    parent.fraInterface.updateButton.style.display = "none";
    parent.fraInterface.divButton.style.display = "";
    //parent.fraInterface.inputQuest.style.display = "";
    parent.fraInterface.inputQuestIn.style.display = "none";
    parent.fraInterface.spanApprove.style.display = "";

    //显示代码选择中文
    parent.fraInterface.showCodeName();
  }




  //复核修改、问题修改
  if (parent.fraInterface.LoadFlag ==23) {
    parent.fraInterface.inputButton.style.display = "none";
    parent.fraInterface.divButton.style.display = "";

    parent.fraInterface.modifyButton.style.display = "";
    if (top.type!=null && top.type=="ChangePlan") {
      innerHTML = "<center> <INPUT class=common name=ChangePlanSub VALUE='修改附加险' TYPE=button onclick='parent.fraInterface.window.location=\"./ChangePlanSub.jsp?prtNo=" + parent.fraInterface.document.all("PrtNo").value + "&type=SubChangePlan\"'>";
    }
    if (top.type!=null && top.type=="SubChangePlan") {
      innerHTML = "<center> <INPUT class=common name=ChangePlanSub VALUE='修改附加险' TYPE=button onclick='parent.fraInterface.window.location=\"./ChangePlanSub.jsp?prtNo=" + parent.fraInterface.document.all("PrtNo").value + "&type=SubChangePlan\"'>";

      parent.fraInterface.deleteButton.style.display = "";
    }
    parent.fraInterface.spanApprove.style.display = "";
    parent.fraInterface.inputQuestButton.style.display = "none";
    parent.fraInterface.inputQuestButton.disabled = true ;
  }





  //人工核保修改投保单
  if (parent.fraInterface.LoadFlag == 25) {
    parent.fraInterface.divButton.style.display = "none";

    //增加随动处理
    try { parent.fraInterface.setFocus(parent.fraInterface.document.all("PrtNo").value); } catch(e) {}

    var innerHTML = "<center>"

   // innerHTML = innerHTML + " <INPUT class=cssButton VALUE=上一步 TYPE=button     onclick='returnparent();'>"
    innerHTML = innerHTML + "</center><br><br>";
    parent.fraInterface.spanApprove.innerHTML = innerHTML;
    parent.fraInterface.divButton.style.display = "";
    //显示“删除”、“修改”按钮
    parent.fraInterface.modifyButton.style.display = "";
    parent.fraInterface.inputButton.style.display = "none";
    parent.fraInterface.divUWContButton.style.display = "none";
    parent.fraInterface.spanApprove.style.display = "";

    //显示代码选择中文
   // parent.fraInterface.showCodeName();
   //tongmeng 增加新承保计划变更的处理
   //parent.fraInterface.getAppntAndInsuredForChangeRiskPlan(<%=request.getParameter("PolNo")%>);
   
   
   
  }
  if (parent.fraInterface.LoadFlag == 99) {
    parent.fraInterface.divButton.style.display = "none";

     //显示“删除”、“修改”按钮
    parent.fraInterface.modifyButton.style.display = "none";
    parent.fraInterface.inputButton.style.display = "none";
    parent.fraInterface.divUWContButton.style.display = "none";
    parent.fraInterface.spanApprove.style.display = "";
    parent.fraInterface.autoMoveButton.style.display = "";

    //显示代码选择中文
   // parent.fraInterface.showCodeName();
  }
    }
    
 // try{setValue();}catch(e){}

</script>
</body>
</html>
