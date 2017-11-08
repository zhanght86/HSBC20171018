<%
//程序名称：PEdorTypePTSubmit.jsp
//程序功能：
//创建日期：2005-07-3 16:49:22
//创建人  ：lizhuo
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
 <%@page import="com.sinosoft.utility.*"%>
 <%@page import="com.sinosoft.lis.schema.*"%>
 <%@page import="com.sinosoft.lis.db.*"%>
 <%@page import="com.sinosoft.lis.vschema.*"%>
 <%@page import="com.sinosoft.lis.bq.*"%>
 <%@page import="com.sinosoft.lis.pubfun.*"%>
 <%@include file="../common/jsp/UsrCheck.jsp"%>
 <%@page contentType="text/html;charset=GBK" %>
 <%@page import="com.sinosoft.service.*" %>

<%
    //接收信息，并作校验处理。
    //输入参数
    //个人批改信息
    System.out.println("-----PT submit---");
    GlobalInput tG = new GlobalInput();
    LPPolSchema tLPPolSchema   = new LPPolSchema();
    LPEdorItemSchema tLPEdorItemSchema   = new LPEdorItemSchema();

    CErrors tErrors = new CErrors();
    //后面要执行的动作：添加，修改

    String tRela  = "";
    String FlagStr = "";
    String Content = "";
    String transact = "";
    String[] Result = new String[14];


    //执行动作：insert 添加纪录，update 修改纪录，delete 删除纪录
    transact = request.getParameter("fmtransact");
    String Flag266 = request.getParameter("Flag266");
    String Flag267 = request.getParameter("Flag267");
    String ContNo = request.getParameter("ContNo");
    String EdorNo = request.getParameter("EdorNo");
    String SubScale = request.getParameter("SubScale");
    System.out.println("SubScale"+SubScale);
    String sAppObj = request.getParameter("AppObj");
    System.out.println("sAppObj"+sAppObj);
    
    String tPTFlagIn = null;

    if(!SubScale.trim().equals("NO"))
    {
        tLPPolSchema.setStandbyFlag1(SubScale);
    }

    tG = (GlobalInput)session.getValue("GI");
    String tMinusNumber = request.getParameter("MinusNumber");

    System.out.println("Flag266"+Flag266);
    System.out.println("Flag267"+Flag267);
    //此处代码lizhuo兄应该是误写了 comment by jiaqiangli 2008-09-04
    if(Flag266.trim().equals("00266000") || Flag267.trim().equals("00267000"))
    {
        System.out.println("Flag266=======" + Flag266.substring(2,5));
        String Str1 = "select * from lcpol where mainpolno = polno and contno = '" + ContNo + "'";
        System.out.println("Str1:===" + Str1);
        LCPolDB tLCPolDB = new LCPolDB();
        LCPolSchema tLCPolSchema = new LCPolSchema();
        LCPolSet tLCPolSet = new LCPolSet();
        tLCPolSet = tLCPolDB.executeQuery(Str1);
        tLCPolSchema = tLCPolSet.get(1);
        String Str2 = "select * from lppol where mainpolno = polno and contno = '" + ContNo + "' and edorno = '" + EdorNo + "'";
        System.out.println("Str2:===" + Str2);
        LPPolDB aLPPolDB = new LPPolDB();
        LPPolSchema aLPPolSchema = new LPPolSchema();
        LPPolSet aLPPolSet = new LPPolSet();
        aLPPolSet = aLPPolDB.executeQuery(Str2);
        aLPPolSchema = aLPPolSet.get(1);
        String Str3 = "select * from lcpol where contno = '" + ContNo + "' and appflag = '1' and substr(trim(riskcode),3,3) = '" + Flag266.trim().substring(2,5) + "'";
        System.out.println("Str3:===" + Str3);
        tLCPolSet.clear();
        tLCPolSet = tLCPolDB.executeQuery(Str3);
        System.out.println("Amnt");
        System.out.println("LCPol Amnt" +tLCPolSchema.getAmnt());
        System.out.println("LPPol Amnt" +aLPPolSchema.getAmnt());
        tLPPolSchema.setAmnt(tLCPolSchema.getAmnt() - aLPPolSchema.getAmnt());
        tLPPolSchema.setPolNo(tLCPolSet.get(1).getPolNo());
    }
    else
    {
        String theCurrentDate = PubFun.getCurrentDate();
        String theCurrentTime = PubFun.getCurrentTime();

        String tChk[] = request.getParameterValues("InpPolGridSel");
        String tRiskCode[] = request.getParameterValues("PolGrid1");
        String tPolNo[] = request.getParameterValues("PolGrid10");
        String tAmntFlag[] = request.getParameterValues("PolGrid11");
        
      //add by jiaqiangli 2008-11-20 PTFlag PTFlag 0减少保费1减少份数2减少保额
        String tPTFlag[] = request.getParameterValues("PolGrid12");
      
        //System.out.println("tChk.length =========>"+tChk.length);
        //System.out.println("tChk[0] =========>"+tChk[0]);
        for(int index=0;index<tChk.length;index++)
        {
           if(tChk[index].equals("1"))
           {
               tLPPolSchema.setPolNo(tPolNo[index]);
               try{
                  System.out.println(tMinusNumber);
                  if (tPTFlag[index].equals("0")) {
                 	 tMinusNumber = request.getParameter("MinusPTPrem");
                      tLPPolSchema.setStandPrem(tMinusNumber);
                   }
                   else if(tPTFlag[index].equals("1")) {
                 	 tMinusNumber = request.getParameter("MinusPTMut");
                      tLPPolSchema.setMult(tMinusNumber);
                      System.out.println("tLPPolSchema" + tLPPolSchema.getAmnt());
                   }
                   else {
                 	 tMinusNumber = request.getParameter("MinusPTAmnt");
                      tLPPolSchema.setAmnt(tMinusNumber);
                   }
                   tPTFlagIn = tPTFlag[index];
               }
               catch(Exception e){
                  System.out.println("传入数据不符合规范!");
                  Content = "传入数据不符合规范!";
                  FlagStr = "Fail";
               }
               break;
           }
        }
    }

    if (!FlagStr.equals("Fail") && transact.equals("INSERT||MAIN"))
    {
        //个人批改信息
        tLPEdorItemSchema.setEdorAcceptNo(request.getParameter("EdorAcceptNo"));
        tLPEdorItemSchema.setEdorNo(request.getParameter("EdorNo"));
        tLPEdorItemSchema.setEdorType(request.getParameter("EdorType"));
        tLPEdorItemSchema.setContNo(request.getParameter("ContNo"));
        tLPEdorItemSchema.setPolNo("000000");
        tLPEdorItemSchema.setInsuredNo("000000");
        tLPEdorItemSchema.setEdorReason(request.getParameter("ReasonContent"));
        tLPEdorItemSchema.setEdorReasonCode(request.getParameter("SurrReasonCode"));
        tLPEdorItemSchema.setStandbyFlag2(request.getParameter("RelationToAppnt"));

        VData tVData = new VData();
        tVData.addElement(tG);
        tVData.addElement(tLPEdorItemSchema);
        tVData.addElement(tLPPolSchema);
        tVData.addElement(tPTFlagIn);
        System.out.println(" tLPPolSchema ========>"+tLPPolSchema.encode());

        //区分个单还是团单复用
        if (sAppObj != null && sAppObj.trim().equalsIgnoreCase("G"))
        {
            //LPGrpEdorItemSchema
            LPGrpEdorItemSchema tLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
            tLPGrpEdorItemSchema.setEdorAcceptNo(request.getParameter("EdorAcceptNo"));
            tLPGrpEdorItemSchema.setEdorNo(request.getParameter("EdorNo"));
            tLPGrpEdorItemSchema.setEdorType(request.getParameter("EdorType"));
            tVData.add(tLPGrpEdorItemSchema);
            tLPGrpEdorItemSchema = null;
            //GrpEdorPTDetailBLF
            GrpEdorPTDetailBLF tGrpEdorPTDetailBLF = new GrpEdorPTDetailBLF();
            if (!tGrpEdorPTDetailBLF.submitData(tVData, "OPERATION"))
            {
                tErrors.copyAllErrors(tGrpEdorPTDetailBLF.mErrors);
            }
            tGrpEdorPTDetailBLF = null;
        }
        else
        {
            //PEdorXSDetailUI
//            PEdorXSDetailUI tPEdorXSDetailUI = new PEdorXSDetailUI();
            String busiNameXS="tPEdorXSDetailUI";
	 		BusinessDelegate tBusinessDelegateXS=BusinessDelegate.getBusinessDelegate();
//            if (!tPEdorXSDetailUI.submitData(tVData, "OPERATION"))
            if (!tBusinessDelegateXS.submitData(tVData, "OPERATION", busiNameXS))
            {
//                tErrors.copyAllErrors(tPEdorXSDetailUI.mErrors);
                tErrors.copyAllErrors(tBusinessDelegateXS.getCErrors());
            }
//            tPEdorXSDetailUI = null;
            tBusinessDelegateXS = null;
        }
        tVData = null;
    }
    //调整金额的保存
    if (transact.equals("EdorSave")) {
        //传递调整后的金额
        String mPolNo[]= request.getParameterValues("CTFeeDetailGrid1");
        String mNewGetMoney[]= request.getParameterValues("CTFeeDetailGrid6");
        String mFeeFinaType[]= request.getParameterValues("CTFeeDetailGrid8");
        LJSGetEndorseSet mLJSGetEndorseSet = new LJSGetEndorseSet();
        LJSGetEndorseSchema tLJSGetEndorseSchema;
        for (int i = 0; i< mPolNo.length; i++)
        {
            tLJSGetEndorseSchema = new LJSGetEndorseSchema();
            tLJSGetEndorseSchema.setPolNo(mPolNo[i]);
            tLJSGetEndorseSchema.setGetMoney(Double.parseDouble(mNewGetMoney[i])); //调整后的金额
            tLJSGetEndorseSchema.setFeeFinaType(mFeeFinaType[i]);
            mLJSGetEndorseSet.add(tLJSGetEndorseSchema);
        }
        
        //个人批改信息
        tLPEdorItemSchema.setEdorAcceptNo(request.getParameter("EdorAcceptNo"));
        tLPEdorItemSchema.setEdorNo(request.getParameter("EdorNo"));
        tLPEdorItemSchema.setEdorType(request.getParameter("EdorType"));
        tLPEdorItemSchema.setContNo(request.getParameter("ContNo"));
        tLPEdorItemSchema.setPolNo("000000");
        tLPEdorItemSchema.setInsuredNo("000000");
        tLPEdorItemSchema.setEdorReason(request.getParameter("ReasonContent"));
        tLPEdorItemSchema.setEdorReasonCode(request.getParameter("SurrReasonCode"));
        tLPEdorItemSchema.setStandbyFlag2(request.getParameter("RelationToAppnt"));
        
        // 准备传输数据 VData
        VData tVData = new VData();
//        EdorDetailUI tEdorDetailUI   = new EdorDetailUI();
        String busiName="EdorDetailUI";
	 	BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
        try
        {
            tVData.add(tG);
            tVData.add(tLPEdorItemSchema);
            tVData.add(mLJSGetEndorseSet);
            tVData.addElement(tLPPolSchema);
//            tEdorDetailUI.submitData(tVData, transact);
            tBusinessDelegate.submitData(tVData, transact ,busiName);
        }
        catch(Exception ex)
        {
              Content = "保存失败，原因是:" + ex.toString();
              FlagStr = "Fail";
        }
        if ("".equals(FlagStr))
        {
//                tErrors = tEdorDetailUI.mErrors;
                tErrors = tBusinessDelegate.getCErrors(); 
                if (!tErrors.needDealError())
                {
                    Content ="保存成功！";
                    FlagStr = "Succ";
                }
                else
                {
                    Content = "保存失败，原因是:" + tErrors.getFirstError();
                    FlagStr = "Fail";
                }
        }
    }

    //页面反馈信息
    if (!tErrors.needDealError())
    {
        FlagStr = "Success";
        Content = "操作成功！";
    }
    else
    {
        FlagStr = "Fail";
        Content = "操作失败，原因是：" + tErrors.getFirstError();
    }
    tErrors = null;
%>


<html>
<head>
    <script language="JavaScript">
        try
        {
            parent.fraInterface.afterSubmit('<%=FlagStr%>', '<%=Content%>');
        }
        catch (ex)
        {
            alert("ex"+ex);
            alert('<%=Content%>');
        }
    </script>
</html>
