<%
//程序名称：PEdorTypePCSubmit.jsp
//程序功能：
//创建日期：2005-5-12 09:49上午
//创建人  ：Lihs
//更新记录：  更新人    更新日期     更新原因/内容
%>

  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.bq.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@include file="../common/jsp/UsrCheck.jsp"%>
 <%@page contentType="text/html;charset=GBK" %>
 <%@page import="com.sinosoft.service.*" %>

<%
  //个人批改信息

    LPContSchema tLPContSchema   = new LPContSchema();
    LPPolSchema tLPPolSchema = new LPPolSchema();
    LPEdorItemSchema tLPEdorItemSchema   = new LPEdorItemSchema();

    LPAccountSchema tLPAccountSchema = new LPAccountSchema();
//    EdorDetailUI tEdorDetailUI = new EdorDetailUI();
//    GEdorDetailUI tGEdorDetailUI   = new GEdorDetailUI();    //XinYQ added on 2006-11-20 : 加入团险支持
     String busiName="EdorDetailUI";
	 BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	 String GbusiName="GEdorDetailUI";
	 BusinessDelegate GBusinessDelegate=BusinessDelegate.getBusinessDelegate();

    CErrors tError = null;
    //后面要执行的动作：添加，修改

    String tRela  = "";
    String FlagStr = "";
    String Content = "";
    String transact = "";
    String Result="";
    String sAppObj = request.getParameter("AppObj");    //XinYQ added on 2006-11-20 : 加入团险支持

    //执行动作：insert 添加纪录，update 修改纪录，delete 删除纪录
    transact = request.getParameter("fmtransact");
    System.out.println("------transact:"+transact);
      GlobalInput tG = new GlobalInput();
      tG = (GlobalInput)session.getValue("GI");
      
    //LPBnfSet
      LPBnfSet tLPBnfSet = new LPBnfSet();

    //个人保单批改信息

  if (transact.equals("UPDATE||MAIN")) {

    tLPEdorItemSchema.setPolNo(request.getParameter("PolNo"));
    tLPEdorItemSchema.setEdorAcceptNo(request.getParameter("EdorAcceptNo"));
      tLPEdorItemSchema.setContNo(request.getParameter("ContNo"));
    tLPEdorItemSchema.setEdorNo(request.getParameter("EdorNo"));
      tLPEdorItemSchema.setEdorType(request.getParameter("EdorType"));
      tLPEdorItemSchema.setInsuredNo(request.getParameter("InsuredNo"));

      String theCurrentDate = PubFun.getCurrentDate();
    String theCurrentTime = PubFun.getCurrentTime();
    tLPEdorItemSchema.setMakeDate(theCurrentDate);
    tLPEdorItemSchema.setMakeTime(theCurrentTime);
    tLPEdorItemSchema.setModifyDate(theCurrentDate);
    tLPEdorItemSchema.setModifyTime(theCurrentTime);
    tLPEdorItemSchema.setOperator(tG.Operator);

    //tLPContSchema.setEdorNo(request.getParameter("EdorNo"));
    //tLPContSchema.setEdorType(request.getParameter("EdorType"));
    //tLPContSchema.setContNo(request.getParameter("ContNo"));


      tLPPolSchema.setEdorNo(request.getParameter("EdorNo"));
    tLPPolSchema.setEdorType(request.getParameter("EdorType"));
    tLPPolSchema.setPolNo(request.getParameter("PolNo"));

    tLPPolSchema.setGetForm(request.getParameter("GetForm"));
    tLPPolSchema.setGetBankCode(request.getParameter("GetBankCode"));
    tLPPolSchema.setGetBankAccNo(request.getParameter("GetBankAccNo"));
    tLPPolSchema.setGetAccName(request.getParameter("GetAccName"));

      //tLPAccountSchema.setEdorNo(request.getParameter("EdorNo"));
      //tLPAccountSchema.setEdorType(request.getParameter("EdorType"));
      //tLPAccountSchema.setCustomerNo(request.getParameter("InsuredNo"));
      //tLPAccountSchema.setAccKind("Y");
      //tLPAccountSchema.setBankCode(request.getParameter("GetBankCode"));
      //tLPAccountSchema.setBankAccNo(request.getParameter("GetBankAccNo"));
      //tLPAccountSchema.setAccName(request.getParameter("GetAccName"));
      //tLPAccountSchema.setOperator(tG.Operator);
      //tLPAccountSchema.setMakeDate(theCurrentDate);
    //tLPAccountSchema.setMakeTime(theCurrentTime);
    //tLPAccountSchema.setModifyDate(theCurrentDate);
    //tLPAccountSchema.setModifyTime(theCurrentTime);
    System.out.println("InsuredNo:" + request.getParameter("InsuredNo"));
    System.out.println("Bankcode:" + request.getParameter("GetBankCode"));
    System.out.println("BankAccNo:" + request.getParameter("GetBankAccNo"));
    System.out.println("BankAccName:" + request.getParameter("GetAccName"));
    
    //add by jiaqiangli 2008-08-25
  //受益人分配信息
    String arrGridNo[] = request.getParameterValues("NewBnfGridNo");
    String arrInsuredNo[] = request.getParameterValues("NewBnfGrid1");
    //String arrInsuredName[] = request.getParameterValues("NewBnfGrid2");
    //String arrBnfType[] = request.getParameterValues("NewBnfGrid3");
    String arrName[] = request.getParameterValues("NewBnfGrid4");
    String arrBirthday[] = request.getParameterValues("NewBnfGrid13");
    String arrSex[] = request.getParameterValues("NewBnfGrid12");
    String arrIDType[] = request.getParameterValues("NewBnfGrid6");
    String arrIDNo[] = request.getParameterValues("NewBnfGrid7");
    String arrRelationToInsured[] = request.getParameterValues("NewBnfGrid8");
    String arrBnfGrade[] = request.getParameterValues("NewBnfGrid9");
    String arrBnfLot[] = request.getParameterValues("NewBnfGrid10");
    String arrPostalAddress[] = request.getParameterValues("NewBnfGrid14");
    String arrZipCode[] = request.getParameterValues("NewBnfGrid15");
    String arrRemark[] = request.getParameterValues("NewBnfGrid16");
    //add by jiaqiangli 2008-08-25新增银行信息
    String bankcode[] = request.getParameterValues("NewBnfGrid17");
    String bankaccno[] = request.getParameterValues("NewBnfGrid18");
    String accname[] = request.getParameterValues("NewBnfGrid19");
    
  
    if (arrGridNo != null)
    {
        for (int i = 0; i < arrGridNo.length; i++)
        {
            LPBnfSchema tLPBnfSchema = new LPBnfSchema();
            tLPBnfSchema.setEdorNo(request.getParameter("EdorNo"));
            tLPBnfSchema.setEdorType(request.getParameter("EdorType"));
            tLPBnfSchema.setContNo(request.getParameter("ContNo"));
            tLPBnfSchema.setPolNo(request.getParameter("PolNo"));
            tLPBnfSchema.setInsuredNo(arrInsuredNo[i]);
            //生存受益人
            tLPBnfSchema.setBnfType("0");
            tLPBnfSchema.setName(arrName[i]);
            tLPBnfSchema.setSex(arrSex[i]);
            tLPBnfSchema.setBirthday(arrBirthday[i]);
            tLPBnfSchema.setIDType(arrIDType[i]);
            tLPBnfSchema.setIDNo(arrIDNo[i]);
            tLPBnfSchema.setRelationToInsured(arrRelationToInsured[i]);
            tLPBnfSchema.setBnfGrade(arrBnfGrade[i]);           
            tLPBnfSchema.setPostalAddress(arrPostalAddress[i]);
            tLPBnfSchema.setZipCode(arrZipCode[i]);
            tLPBnfSchema.setRemark(arrRemark[i]);
            
            //增加三列信息
            tLPBnfSchema.setBankCode(bankcode[i]);
            tLPBnfSchema.setBankAccNo(bankaccno[i]);
            tLPBnfSchema.setAccName(accname[i]);
            
            //受益份额
            double dBnfLot = 0.00;
            try
            {
                dBnfLot = Double.parseDouble(arrBnfLot[i]);
                tLPBnfSchema.setBnfLot(dBnfLot);
            }
            catch (Exception ex)
            {
            	ex.printStackTrace();
            	tError.addOneError("第 " + (i + 1) + " 行受益份额数据类型转换异常！");
                break;
            }
            //受益人序号, 按 PolNo 分组, 不再按 InsuredNo 细分
            tLPBnfSchema.setBnfNo(i + 1);
            tLPBnfSet.add(tLPBnfSchema);
            tLPBnfSchema = null;
        }
    }

  }

  try
  {
      // 准备传输数据 VData
      VData tVData = new VData();
            //保存个人保单信息(保全)
      tVData.addElement(tG);
      tVData.addElement(tLPEdorItemSchema);
      //tVData.addElement(tLPContSchema);
      tVData.addElement(tLPPolSchema);
      tVData.addElement(tLPAccountSchema);
      tVData.addElement(tLPBnfSet);

      if (sAppObj != null && sAppObj.trim().equalsIgnoreCase("G"))    //XinYQ added on 2006-11-20 : 加入团险支持
      {
          LPGrpEdorItemSchema tLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
          tLPGrpEdorItemSchema.setEdorAcceptNo(request.getParameter("EdorAcceptNo"));
          tLPGrpEdorItemSchema.setEdorNo(request.getParameter("EdorNo"));
          tLPGrpEdorItemSchema.setEdorType(request.getParameter("EdorType"));
          tVData.addElement(tLPGrpEdorItemSchema);
          tLPGrpEdorItemSchema = null;
//          tGEdorDetailUI.submitData(tVData, transact);
          GBusinessDelegate.submitData(tVData, transact ,GbusiName);
      }
      else
      {
//          tEdorDetailUI.submitData(tVData,transact);
          tBusinessDelegate.submitData(tVData, transact ,busiName);
      }
   }
   catch(Exception ex)
   {
        Content = "保存失败，原因是:" + ex.toString();
        FlagStr = "Fail";
   }

   //如果在Catch中发现异常，则不从错误类中提取错误信息
   if (FlagStr=="")
   {
        if (sAppObj != null && sAppObj.trim().equalsIgnoreCase("G"))
        {
//            tError = tGEdorDetailUI.getErrors();
            tError = GBusinessDelegate.getCErrors(); 
        }
        else
        {
//            tError = tEdorDetailUI.getErrors();
            tError = tBusinessDelegate.getCErrors(); 
        }
        if (!tError.needDealError())
        {
            Content = " 保存成功";
            FlagStr = "Success";
        }
        else
        {
            Content = " 保存失败，原因是:" + tError.getFirstError();
            FlagStr = "Fail";
        }
    }
%>

<html>
    <script language="JavaScript">
        try
        {
            parent.fraInterface.afterSubmit('<%=FlagStr%>', '<%=Content%>');
        }
        catch (ex)
        {
            alert('<%=Content%>');
        }
    </script>
</html>

