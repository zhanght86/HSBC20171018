<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//程序名称：ProposalSave.jsp
//程序功能：
//创建日期：2002-06-19 11:10:36
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.tb.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.cbcheckgrp.*"%>
<%@page import="com.sinosoft.lis.vbl.*"%>
 <%@page import="com.sinosoft.service.*" %>

<%


  //输出参数
  CErrors tError = null;
  String FlagStr = "Fail";
  String Content = "";
  String tAction = "";
  String tOldAction = "";
  String tOperate = "";
  boolean ChangePlanFlag = false;
  String approveFlag = "";
  String UWFlag = "";
  String approveCode = "";
  String approveDate = "";
  String tLoadFlag = "";
  String mLoadFlag = "";
  String remark="";
  tAction = request.getParameter( "fmAction" );
  if( tAction.equals( "INSERTPERSON" )) tOperate = "INSERT||PROPOSAL";
  if( tAction.equals( "INSERTGROUP" )) tOperate = "INSERT||PROPOSAL";
  if( tAction.equals( "UPDATEPERSON" )) tOperate = "UPDATE||PROPOSAL";
  if( tAction.equals( "UPDATEGROUP" )) tOperate = "UPDATE||PROPOSAL";
  if( tAction.equals( "DELETE" )) tOperate = "DELETE||PROPOSAL";
  //loggerDebug("ProposalSave","asdasdsd");
  //增加判断，如果没有录入主险不能录入附加险 write by yaory
    String triskcode=request.getParameter("RiskCode");
    
   String tt=(String)session.getValue("MainRiskNo");


    if(!remark.equals("1")){
      //end 判断
      loggerDebug("ProposalSave","save....111111");
      LCPolSchema mLCPolSchema = new LCPolSchema();
      LCDutyBLSet mLCDutyBLSet=new LCDutyBLSet();
      try  {
    	  String busiName="tbProposalUI";
    	   BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
        //ProposalUI tProposalUI  = new ProposalUI();
        GlobalInput tG = new GlobalInput();
        tG = ( GlobalInput )session.getValue( "GI" );

        VData tVData = new VData();

        tOldAction = tAction;
        loggerDebug("ProposalSave","用户选择的操作为tAction:"+tAction);

        if( tAction.equals( "DELETE" ))  //选择删除保单操作的处理
        {
          LCPolSchema tLCPolSchema  = new LCPolSchema();
          tLCPolSchema.setProposalNo( request.getParameter( "ProposalNo" ));
          // 准备传输数据 VData
          tVData.addElement( tLCPolSchema );
          tVData.addElement( tG );
        }
        else
        {
          if (tAction.indexOf("ChangePlan") != -1) //在承保计划变更处保存保单时，获取投保单状态
          {
            ChangePlanFlag = true;
            loggerDebug("ProposalSave","111 tAction:"+tAction);
            loggerDebug("ProposalSave","111 tAction:这里可能出错，截取错误");
            if(tOldAction.indexOf("Modify") != 1)
            {
              if(tAction.equals("ChangePlanUPDATEGROUP")) //--添加判断
              {
                tAction = tAction.substring(10);
              }
              else
              { tAction = tAction.substring(16);}
            }
            else
            tAction = tAction.substring(10);
            loggerDebug("ProposalSave","222 tAction:"+tAction);

            LCPolSchema tLCPolSchema = new LCPolSchema();
            tLCPolSchema.setPolNo(request.getParameter("ProposalNo"));
            VData VData2 = new VData();
            VData2.add(tLCPolSchema);
            String busiName1="cbcheckgrpChangePlanUI";
            BusinessDelegate tBusinessDelegate1=BusinessDelegate.getBusinessDelegate();
           // ChangePlanUI tChangePlanUI = new ChangePlanUI();
            //向后台传输数据
            if (!tBusinessDelegate1.submitData(VData2, "QUERY||CHANGEPLAN",busiName1))
            {  //操作失败时
            Content = "操作失败，原因是: " + tBusinessDelegate1.getCErrors().getError(0).errorMessage;
            FlagStr = "Fail";
            throw new Exception();
          }
          else
          { //操作成功时
          VData vResult = tBusinessDelegate1.getResult();
          approveFlag = (String)vResult.get(0);
          UWFlag = (String)vResult.get(1);
          approveCode = (String)vResult.get(2);
          approveDate = (String)vResult.get(3);

          loggerDebug("ProposalSave","approveFlag:" + approveFlag + " UWFlag:" + UWFlag);
        }
      }

      //锁印刷号
      loggerDebug("ProposalSave","tOldAction.indexOf_Modify:"+tOldAction.indexOf("Modify"));
      loggerDebug("ProposalSave","GrpContNo:"+request.getParameter("GrpContNo"));
      if (request.getParameter("GrpContNo").equals("00000000000000000000") && tOldAction.indexOf("Modify") == -1)
      {
        LDSysTraceSchema tLDSysTraceSchema = new LDSysTraceSchema();
        tLDSysTraceSchema.setPolNo(request.getParameter("PrtNo"));
        tLDSysTraceSchema.setCreatePos("承保录单");
        tLDSysTraceSchema.setPolState("1002");
        LDSysTraceSet inLDSysTraceSet = new LDSysTraceSet();
        inLDSysTraceSet.add(tLDSysTraceSchema);
        VData VData3 = new VData();
        VData3.add(tG);
        VData3.add(inLDSysTraceSet);
        String busiName1="pubfunLockTableUI";
        BusinessDelegate tBusinessDelegate1=BusinessDelegate.getBusinessDelegate();
        //LockTableUI LockTableUI1 = new LockTableUI();
        if (!tBusinessDelegate1.submitData(VData3, "INSERT",busiName1))
        {
          VData rVData = tBusinessDelegate1.getResult();
          loggerDebug("ProposalSave","LockTable Failed! " + (String)rVData.get(0));
        }
        else
        {
          loggerDebug("ProposalSave","LockTable Succed!");
        }
      }

      if (tAction.indexOf("Modify") != -1)
      tAction = tAction.substring(6);
      loggerDebug("ProposalSave","tAction" + tAction);
      LCContSchema tLCContSchema = new LCContSchema();
      tLCContSchema.setContNo(request.getParameter("ContNo"));
      loggerDebug("ProposalSave","ContNo"+request.getParameter("ContNo"));
      // 保单信息部分
      LCPolSchema tLCPolSchema = new LCPolSchema();
      LCDutySchema tLCDutySchema = new LCDutySchema();

      tLCPolSchema.setProposalNo(request.getParameter("ProposalNo"));
      tLCPolSchema.setPrtNo(request.getParameter("PrtNo"));
      tLCPolSchema.setManageCom(request.getParameter("ManageCom"));
      //tLCPolSchema.setManageCom(tG.ManageCom);
      //tLCPolSchema.setSaleChnl(request.getParameter("SaleChnl"));
      //查询如果银代险种则渠道为3,如果个险则为1 团险为2
      LMRiskAppDB aLMRiskAppDB = new LMRiskAppDB();
      aLMRiskAppDB.setRiskCode(triskcode);
      aLMRiskAppDB.getInfo();
      tLCPolSchema.setSaleChnl("1");
      if(aLMRiskAppDB.getRiskProp()!=null&&aLMRiskAppDB.getRiskProp().equals("I")){
        tLCPolSchema.setSaleChnl("1");
      
      }
      else if(aLMRiskAppDB.getRiskProp()!=null&&aLMRiskAppDB.getRiskProp().equals("Y")){
        tLCPolSchema.setSaleChnl("3");
      
      }
      else if(aLMRiskAppDB.getRiskProp()!=null&&aLMRiskAppDB.getRiskProp().equals("G")){
        tLCPolSchema.setSaleChnl("2");
      
      }
      
      tLCPolSchema.setAgentCom(request.getParameter("AgentCom"));
      tLCPolSchema.setAgentType(request.getParameter("AgentType"));
      tLCPolSchema.setAgentCode(request.getParameter("AgentCode"));
      tLCPolSchema.setAgentGroup(request.getParameter("AgentGroup"));
      tLCPolSchema.setHandler(request.getParameter("Handler"));
      tLCPolSchema.setAgentCode1(request.getParameter("AgentCode1"));
      tLCPolSchema.setInsuredAppAge(request.getParameter("InsuredAppAge")); //被保人投保年龄
      //loggerDebug("ProposalSave",">>>>>>>>>>>>"+request.getParameter("InsuredAppAge"));
      tLCPolSchema.setInsuredPeoples(request.getParameter("InsuredPeoples")); //被保人人数
      loggerDebug("ProposalSave","****人数**"+request.getParameter("InsuredPeoples"));
      tLCPolSchema.setPolTypeFlag(request.getParameter("PolTypeFlag")); //保单类型标记
      //loggerDebug("ProposalSave",">>>>>>>>>>>>"+request.getParameter("PolTypeFlag"));
      tLCPolSchema.setContNo(request.getParameter("ContNo"));
      //tLCPolSchema.setGrpPolNo(request.getParameter("GrpPolNo"));
      tLCPolSchema.setGrpContNo(request.getParameter("GrpContNo"));
      //loggerDebug("ProposalSave","000000000000000="+request.getParameter("GrpContNo")); //yaory
      //借tLCPolSchema把判断的标志传到后台-在添加附加险之前是否添加了主险
      //tLCPolSchema.setKeepValueOpt(remark); //保全不用这个界面
      //tLCPolSchema.setMainPolNo(request.getParameter("MainPolNo"));
      
     
      
        tLCPolSchema.setMainPolNo(request.getParameter("MainPolNo"));
      
      
      tLCPolSchema.setFirstPayDate(request.getParameter("FirstPayDate"));
      //tLCPolSchema.setLang(request.getParameter("Lang"));
      //tLCPolSchema.setCurrency(request.getParameter("Currency"));
      //tLCPolSchema.setDisputedFlag(request.getParameter("DisputedFlag"));
      tLCPolSchema.setAgentPayFlag(request.getParameter("AgentPayFlag"));
      tLCPolSchema.setAgentGetFlag(request.getParameter("AgentGetFlag"));
      tLCPolSchema.setRemark(request.getParameter("Remark"));
      


        // add for GrpAddName
        tLCPolSchema.setMasterPolNo(request.getParameter("MasterPolNo"));
        loggerDebug("ProposalSave","MasterPolNo:" + request.getParameter("MasterPolNo"));

        loggerDebug("ProposalSave","设置保单基本信息...");

        // 险种信息部分

        if(request.getParameter("PolApplyDate")==null||request.getParameter("PolApplyDate").equals(""))
        {
          tLCPolSchema.setPolApplyDate(request.getParameter("CValiDate"));
        }
        else
        {
          tLCPolSchema.setPolApplyDate(request.getParameter("PolApplyDate"));
        }
        tLCPolSchema.setRiskCode(request.getParameter("RiskCode"));
        tLCPolSchema.setRiskVersion(request.getParameter("RiskVersion"));
        loggerDebug("ProposalSave","CValidate=="+request.getParameter("CValiDate"));
       String tCValiDate = request.getParameter("CValiDate");
        tLCPolSchema.setCValiDate(tCValiDate);             //保单生效日期
        if(tCValiDate!=null&&!tCValiDate.equals("")&&!tCValiDate.equals("null"))
        {
        	tLCPolSchema.setSpecifyValiDate("Y");
        }
        //tLCPolSchema.setPolApplyDate(request.getParameter("CValiDate"));          //投保单申请日期
        tLCPolSchema.setHealthCheckFlag(request.getParameter("HealthCheckFlag")); //是否体检件
        //tLCPolSchema.setOutPayFlag(request.getParameter("OutPayFlag"));           //溢交处理方式
        tLCPolSchema.setPayLocation(request.getParameter("PayLocation"));         //收费方式
        //tLCPolSchema.setBankCode(request.getParameter("BankCode"));               //开户行
        //tLCPolSchema.setBankAccNo(request.getParameter("BankAccNo"));             //银行帐号
        //tLCPolSchema.setAccName(request.getParameter("AccName"));                 //户名
        tLCPolSchema.setLiveGetMode(request.getParameter("LiveGetMode"));         //生存保险金领取方式
        
        loggerDebug("ProposalSave","init lcol1 ") ;                                                                         //领取期限，通过TRANSFERDATA传递
        //领取方式，通过TRANSFERDATA传递

        //如果计算方向为份数算保额，则保存计算方向，用于团单打印时打印份数
        //String mPremToAmnt=request.getParameter("PremToAmnt");
        //if(mPremToAmnt!=null&&mPremToAmnt.equals("O")){
          //  tLCPolSchema.setPremToAmnt(mPremToAmnt);
          //}
          tLCPolSchema.setMult(request.getParameter("Mult"));                       //份数
          tLCPolSchema.setPrem(request.getParameter("Prem"));                       //保费
          tLCPolSchema.setAmnt(request.getParameter("Amnt"));                       //保额

          if(request.getParameter("RnewFlag")==null||request.getParameter("RnewFlag").equals(""))
          {
            //如果界面没有续保选项，则默认是自动续保-后台判断：如果该险种可以续保，则不变；如不能续保，则改为-2不续保
            //因为该字段类型为int，所以不传，后台默认是0-人工续保，有冲突，故作修改
            tLCPolSchema.setRnewFlag("-1");

          }
          else
          {
            tLCPolSchema.setRnewFlag(request.getParameter("RnewFlag"));               //
          }
          
          //tLCPolSchema.setSpecifyValiDate(request.getParameter("SpecifyValiDate")); //是否指定生效日期
         // tLCPolSchema.setSpecifyValiDate("N");
          tLCPolSchema.setPayIntv(request.getParameter("PayIntv"));
           loggerDebug("ProposalSave","标志======");
          tLCPolSchema.setFloatRate(request.getParameter("FloatRate"));             //
          
          tLCPolSchema.setInsuYearFlag(request.getParameter("InsuYearFlag"));
          tLCPolSchema.setInsuYear(request.getParameter("InsuYear")); 
          //20050926 yaory endinit
          tLCPolSchema.setBonusMan(request.getParameter("BonusMan"));               //红利领取人
          tLCPolSchema.setBonusGetMode(request.getParameter("BonusGetMode"));       //红利领取方式
          tLCPolSchema.setStandbyFlag1(request.getParameter("StandbyFlag1"));       //内部分类
          loggerDebug("ProposalSave","标志======"+request.getParameter("StandbyFlag1"));
          tLCPolSchema.setStandbyFlag2(request.getParameter("StandbyFlag2"));
          tLCPolSchema.setStandbyFlag3(request.getParameter("StandbyFlag3"));       //打印标志printflag
          tLCPolSchema.setGetYear(request.getParameter("GetYear"));                //年金开始领取年龄
          tLCPolSchema.setGetYearFlag(request.getParameter("GetYearFlag"));
          //tLCPolSchema.setStandbyFlag4(request.getParameter("StandbyFlag4"));       //银行保单号foreighpol


          // 基业 的管理费比例存储
          tLCPolSchema.setManageFeeRate(request.getParameter("ManageFeeRate"));       //管理费比例
          //add end

      
          tLCPolSchema.setKeepValueOpt(request.getParameter("KeepValueOpt"));
          
          //MS乘客综合意外伤害保险
          tLCPolSchema.setTakeDate(request.getParameter("TakeDate"));
          tLCPolSchema.setTakeTime(request.getParameter("TakeTime"));
          tLCPolSchema.setAirNo(request.getParameter("AirNo"));
          tLCPolSchema.setTicketNo(request.getParameter("TicketNo"));
          tLCPolSchema.setSeatNo(request.getParameter("SeatNo"));



          loggerDebug("ProposalSave","init LCDuty ");

          //以下页面信息未整理
          tLCDutySchema.setPayIntv(request.getParameter("PayIntv"));                //交费方式
          loggerDebug("ProposalSave","payIntv:"+request.getParameter("PayIntv"));
        
          tLCDutySchema.setInsuYear(request.getParameter("InsuYear"));              //保险期间
        
          tLCDutySchema.setInsuYearFlag(request.getParameter("InsuYearFlag"));
          loggerDebug("ProposalSave",request.getParameter("InsuYearFlag"));
          
          tLCDutySchema.setPayEndYear(request.getParameter("PayEndYear"));          //交费年期
          
          tLCDutySchema.setPayEndYearFlag(request.getParameter("PayEndYearFlag"));
       
          tLCDutySchema.setGetYear(request.getParameter("GetYear"));                //年金开始领取年龄
          loggerDebug("ProposalSave","6"+request.getParameter("GetYear"));
          tLCDutySchema.setGetYearFlag(request.getParameter("GetYearFlag"));
          tLCDutySchema.setGetIntv(request.getParameter("GetIntv"));  //计算使用的生存领取方式
          loggerDebug("ProposalSave","getyear:"+request.getParameter("GetYear")+"GetYearFlag:"+request.getParameter("GetYearFlag")+"GetIntv"+request.getParameter("GetIntv"));
          tLCDutySchema.setGetStartType(request.getParameter("GetStartType"));
          tLCDutySchema.setCalRule(request.getParameter("CalRule"));
           loggerDebug("ProposalSave","前台CalRule:"+request.getParameter("CalRule"));
          tLCDutySchema.setFloatRate(request.getParameter("FloatRate"));
          loggerDebug("ProposalSave","前台FloatRate:"+request.getParameter("FloatRate"));
          tLCDutySchema.setPremToAmnt(request.getParameter("PremToAmnt"));
          tLCDutySchema.setStandbyFlag1(request.getParameter("StandbyFlag1"));
          tLCDutySchema.setStandbyFlag2(request.getParameter("StandbyFlag2"));
          tLCDutySchema.setStandbyFlag3(request.getParameter("StandbyFlag3"));
          tLCDutySchema.setPrem(request.getParameter("Prem"));
          tLCDutySchema.setAmnt(request.getParameter("Amnt"));
          tLCDutySchema.setMult(request.getParameter("Mult"));
          tLCDutySchema.setGetLimit(request.getParameter("GetLimit"));
          loggerDebug("ProposalSave","GetLimit:"+request.getParameter("GetLimit"));
          tLCDutySchema.setGetRate(request.getParameter("GetRate"));
          tLCDutySchema.setSSFlag(request.getParameter("SSFlag"));
          tLCDutySchema.setPeakLine(request.getParameter("PeakLine"));

          loggerDebug("ProposalSave","设置保单中险种信息...");
          loggerDebug("ProposalSave","init LCAppntInd ");

          // 投保人信息部分
          // 个人投保人
          LCAppntSchema tLCAppntSchema = new LCAppntSchema();
          tLCAppntSchema.setContNo(tLCContSchema.getContNo());
          tLCAppntSchema.setAppntNo(request.getParameter("AppntCustomerNo"));  //客户号
          tLCAppntSchema.setAppntName(request.getParameter("AppntName"));              //姓名
          tLCAppntSchema.setAppntSex(request.getParameter("AppntSex"));                //性别
          tLCAppntSchema.setAppntBirthday(request.getParameter("AppntBirthday"));      //出生日期
          //年龄，不提交
          //      tLCAppntSchema.setAppntIDType(request.getParameter("AppntIDType"));          //证件类型
          //      tLCAppntSchema.setAppntIDNo(request.getParameter("AppntIDNo"));              //证件号码
          //      tLCAppntSchema.setNativePlace(request.getParameter("AppntNativePlace")); //国籍
          //      tLCAppntSchema.setRgtAddress(request.getParameter("AppntRgtAddress"));  //户口所在地
          //      tLCAppntSchema.setMarriage(request.getParameter("AppntMarriage"));      //婚姻状况
          //      tLCAppntSchema.setNationality(request.getParameter("AppntNationality")); //民族
          //      tLCAppntSchema.setDegree(request.getParameter("AppntDegree"));          //学历
          //      tLCAppntSchema.setRelationToInsured(request.getParameter("AppntRelationToInsured")); //与被保险人关系
          //      tLCAppntSchema.setPostalAddress(request.getParameter("AppntPostalAddress"));         //联系地址
          //      tLCAppntSchema.setZipCode(request.getParameter("AppntZipCode"));        //邮政编码
          //      tLCAppntSchema.setHomeAddress(request.getParameter("AppntHomeAddress")); //住址
          //      tLCAppntSchema.setHomeZipCode(request.getParameter("AppntHomeZipCode")); //住址邮政编码
          //      tLCAppntSchema.setPhone(request.getParameter("AppntPhone"));            //联系电话（1）
          //      tLCAppntSchema.setPhone2(request.getParameter("AppntPhone2"));          //联系电话（2）
          //      tLCAppntSchema.setMobile(request.getParameter("AppntMobile"));          //移动电话
          //      tLCAppntSchema.setEMail(request.getParameter("AppntEMail"));            //电子邮箱
          //      tLCAppntSchema.setGrpName(request.getParameter("AppntGrpName"));        //工作单位
          //      tLCAppntSchema.setGrpPhone(request.getParameter("AppntGrpPhone"));      //单位电话
          //      tLCAppntSchema.setGrpAddress(request.getParameter("AppntGrpAddress"));  //单位地址
          //      tLCAppntSchema.setGrpZipCode(request.getParameter("AppntGrpZipCode"));  //单位邮政编码
          //      tLCAppntSchema.setWorkType(request.getParameter("AppntWorkType"));      //职业（工种）
          //      tLCAppntSchema.setPluralityType(request.getParameter("AppntPluralityType"));        //兼职（工种）
          //      tLCAppntSchema.setOccupationType(request.getParameter("AppntOccupationType"));      //职业类别
          //      tLCAppntSchema.setOccupationCode(request.getParameter("AppntOccupationCode"));      //职业代码
          //      tLCAppntSchema.setSmokeFlag(request.getParameter("AppntSmokeFlag"));    //是否吸烟

          // 集体投保人
          LCGrpAppntSchema tLCGrpAppntSchema = new LCGrpAppntSchema();

          tLCGrpAppntSchema.setCustomerNo(request.getParameter("AppGrpNo"));
          tLCGrpAppntSchema.setGrpContNo(request.getParameter("GrpContNo"));
          /*
          tLCGrpAppntSchema.setGrpAddress(request.getParameter("AppGrpAddress"));
          tLCGrpAppntSchema.setGrpZipCode(request.getParameter("AppGrpZipCode"));
          tLCGrpAppntSchema.setGrpNature(request.getParameter("GrpNature"));
          tLCGrpAppntSchema.setBusinessType(request.getParameter("BusinessType"));
          tLCGrpAppntSchema.setPeoples(request.getParameter("Peoples"));
          tLCGrpAppntSchema.setRgtMoney(request.getParameter("RgtMoney"));
          tLCGrpAppntSchema.setAsset(request.getParameter("Asset"));
          tLCGrpAppntSchema.setNetProfitRate(request.getParameter("NetProfitRate"));
          tLCGrpAppntSchema.setMainBussiness(request.getParameter("MainBussiness"));
          tLCGrpAppntSchema.setCorporation(request.getParameter("Corporation"));
          tLCGrpAppntSchema.setComAera(request.getParameter("ComAera"));

          //保险联系人一
          tLCGrpAppntSchema.setLinkMan1(request.getParameter("LinkMan1"));
          tLCGrpAppntSchema.setDepartment1(request.getParameter("Department1"));
          tLCGrpAppntSchema.setHeadShip1(request.getParameter("HeadShip1"));
          tLCGrpAppntSchema.setPhone1(request.getParameter("Phone1"));
          tLCGrpAppntSchema.setE_Mail1(request.getParameter("E_Mail1"));
          tLCGrpAppntSchema.setFax1(request.getParameter("Fax1"));

          //保险联系人二
          tLCGrpAppntSchema.setLinkMan2(request.getParameter("LinkMan2"));
          tLCGrpAppntSchema.setDepartment2(request.getParameter("Department2"));
          tLCGrpAppntSchema.setHeadShip2(request.getParameter("HeadShip2"));
          //tLCGrpAppntSchema.setPhone2(request.getParameter("Phone2"));
          tLCGrpAppntSchema.setE_Mail2(request.getParameter("E_Mail2"));
          tLCGrpAppntSchema.setFax2(request.getParameter("Fax2"));
          tLCGrpAppntSchema.setGetFlag(request.getParameter("GetFlag"));
          tLCGrpAppntSchema.setBankCode(request.getParameter("GrpBankCode"));
          tLCGrpAppntSchema.setBankAccNo(request.getParameter("GrpBankAccNo"));

          loggerDebug("ProposalSave","设置投保人信息...");
          */
          // 被保人信息
          //LCInsuredSchema tLCInsuredSchema = new LCInsuredSchema();

          // 主被保人
          LCInsuredSchema tLCInsuredSchema = new LCInsuredSchema();
          //tLCInsuredSchema.setInsuredGrade("M");
          tLCInsuredSchema.setContNo(tLCContSchema.getContNo());
          tLCInsuredSchema.setInsuredNo(request.getParameter("CustomerNo"));  //客户号
          
          //      tLCInsuredSchema.setName(request.getParameter("Name"));              //姓名
          //      tLCInsuredSchema.setSex(request.getParameter("Sex"));                //性别
          //      tLCInsuredSchema.setBirthday(request.getParameter("Birthday"));      //出生日期
          //年龄，不提交
          //      tLCInsuredSchema.setIDType(request.getParameter("IDType"));          //证件类型
          //      tLCInsuredSchema.setIDNo(request.getParameter("IDNo"));              //证件号码
          //      tLCInsuredSchema.setNativePlace(request.getParameter("NativePlace")); //国籍
          //      tLCInsuredSchema.setRgtAddress(request.getParameter("RgtAddress"));  //户口所在地
          //      tLCInsuredSchema.setMarriage(request.getParameter("Marriage"));      //婚姻状况
          //      tLCInsuredSchema.setNationality(request.getParameter("Nationality")); //民族
          //      tLCInsuredSchema.setDegree(request.getParameter("Degree"));          //学历
          //      tLCInsuredSchema.setSmokeFlag(request.getParameter("SmokeFlag"));    //是否吸烟
          //      tLCInsuredSchema.setPostalAddress(request.getParameter("PostalAddress"));         //联系地址
          //      tLCInsuredSchema.setZipCode(request.getParameter("ZipCode"));        //邮政编码
          //      tLCInsuredSchema.setHomeAddress(request.getParameter("HomeAddress")); //住址
          //      tLCInsuredSchema.setHomeZipCode(request.getParameter("HomeZipCode")); //住址邮政编码
          //      tLCInsuredSchema.setPhone(request.getParameter("Phone"));            //联系电话（1）
          //      tLCInsuredSchema.setPhone2(request.getParameter("Phone2"));          //联系电话（2）
          //      tLCInsuredSchema.setMobile(request.getParameter("Mobile"));          //移动电话
          //      tLCInsuredSchema.setEMail(request.getParameter("EMail"));            //电子邮箱
          //      tLCInsuredSchema.setGrpName(request.getParameter("GrpName"));        //工作单位
          //      tLCInsuredSchema.setGrpPhone(request.getParameter("GrpPhone"));      //单位电话
          //      tLCInsuredSchema.setGrpAddress(request.getParameter("GrpAddress"));  //单位地址
          //      tLCInsuredSchema.setGrpZipCode(request.getParameter("GrpZipCode"));  //单位邮政编码
          //      tLCInsuredSchema.setWorkType(request.getParameter("WorkType"));      //职业（工种）
          //      tLCInsuredSchema.setPluralityType(request.getParameter("PluralityType"));         //兼职（工种）
          //      tLCInsuredSchema.setOccupationType(request.getParameter("OccupationType"));       //职业类别
          //      tLCInsuredSchema.setOccupationCode(request.getParameter("OccupationCode"));       //职业代码

          //以下页面信息未整理
          //      tLCInsuredSchema.setHealth(request.getParameter("Health"));
          //      tLCInsuredSet.add(tLCInsuredSchema);

          loggerDebug("ProposalSave","end set schema 被保人信息...");
          /*
          // 连带被保险人(未整理)
          String tInsuredNum[] = request.getParameterValues("SubInsuredGridNo");
          String tInsuredCustomerNo[] = request.getParameterValues("SubInsuredGrid1");
          String tInsuredName[] = request.getParameterValues("SubInsuredGrid2");
          String tInsuredSex[] = request.getParameterValues("SubInsuredGrid3");
          String tInsuredBirthday[] = request.getParameterValues("SubInsuredGrid4");
          String tRelationToInsured[] = request.getParameterValues("SubInsuredGrid5");

          int InsuredCount = 0;
          if (tInsuredNum != null) InsuredCount = tInsuredNum.length;

          for (int i = 0; i < InsuredCount; i++)
          {
            if(tInsuredCustomerNo[i]==null || tInsuredCustomerNo[i].equals("")) break;

            tLCInsuredSchema = new LCInsuredSchema();
            tLCInsuredSchema.setInsuredGrade("S");
            tLCInsuredSchema.setCustomerNo(tInsuredCustomerNo[i]);
            tLCInsuredSchema.setName(tInsuredName[i]);
            tLCInsuredSchema.setSex(tInsuredSex[i]);
            tLCInsuredSchema.setBirthday(tInsuredBirthday[i]);
            tLCInsuredSchema.setRelationToInsured(tRelationToInsured[i]);

            tLCInsuredSet.add(tLCInsuredSchema);
          }
          */
          // 受益人信息
          LCBnfSet tLCBnfSet = new LCBnfSet();

          String tBnfNum[]   = request.getParameterValues("BnfGridNo");
          String tBnfType[]  = request.getParameterValues("BnfGrid1");
          String tName[]     = request.getParameterValues("BnfGrid2");
          String tIDType[]   = request.getParameterValues("BnfGrid3");
          String tIDNo[]     = request.getParameterValues("BnfGrid4");
          String tBnfRelationToInsured[] = request.getParameterValues("BnfGrid5");

          String tBnfGrade[] = request.getParameterValues("BnfGrid6");
          String tBnfLot[]   = request.getParameterValues("BnfGrid7");
          String tAddress[]  = request.getParameterValues("BnfGrid8");
          String tInsurno[]  = request.getParameterValues("BnfGrid10"); //add by yaory
          int BnfCount = 0;
          if (tBnfNum != null) BnfCount = tBnfNum.length;
          for (int i = 0; i < BnfCount; i++)
          {
            if( tName[i] == null || tName[i].equals( "" )) break;

            LCBnfSchema tLCBnfSchema = new LCBnfSchema();
          
               
            //tLCBnfSchema.setBnfType("1");
            tLCBnfSchema.setBnfType(tBnfType[i]);
            tLCBnfSchema.setName(tName[i]);
            //tLCBnfSchema.setSex(tSex[i]);
            tLCBnfSchema.setIDType(tIDType[i]);
            tLCBnfSchema.setIDNo(tIDNo[i]);
            //tLCBnfSchema.setBirthday(tBirthday[i]);
            tLCBnfSchema.setRelationToInsured(tBnfRelationToInsured[i]);
            tLCBnfSchema.setBnfLot(tBnfLot[i]);
            tLCBnfSchema.setBnfGrade(tBnfGrade[i]);
            tLCBnfSchema.setInsuredNo(tInsurno[i]); //add by yaory
            
                       
            //tLCBnfSchema.setAddress(tAddress[i]);
            //tLCBnfSchema.setZipCode(tZipCode[i]);
            //tLCBnfSchema.setPhone(tPhone[i]);
            tLCBnfSet.add(tLCBnfSchema);
            
          }

          loggerDebug("ProposalSave","end set schema 受益人信息...");

          // 告知信息
          LCCustomerImpartSet tLCCustomerImpartSet = new LCCustomerImpartSet();

          String tImpartNum[] = request.getParameterValues("ImpartGridNo");
          String tImpartVer[] = request.getParameterValues("ImpartGrid1");            //告知版别
          String tImpartCode[] = request.getParameterValues("ImpartGrid2");           //告知编码
          String tImpartContent[] = request.getParameterValues("ImpartGrid4");        //填写内容
          String tImpartCustomerNoType[] = request.getParameterValues("ImpartGrid5"); //告知客户类型

          int ImpartCount = 0;
          if (tImpartNum != null) ImpartCount = tImpartNum.length;

          for (int i = 0; i < ImpartCount; i++)  {
            if( !(tImpartCustomerNoType[i].trim().equals( "A" ) || tImpartCustomerNoType[i].trim().equals( "I" )))
            break;

            LCCustomerImpartSchema tLCCustomerImpartSchema = new LCCustomerImpartSchema();

            if( tImpartCustomerNoType[i].trim().equals( "A" ))
            tLCCustomerImpartSchema.setCustomerNo( request.getParameter( "AppntCustomerNo" ));
            if( tImpartCustomerNoType[i].trim().equals( "I" ))
            tLCCustomerImpartSchema.setCustomerNo( request.getParameter( "CustomerNo" ));

            tLCCustomerImpartSchema.setCustomerNoType( tImpartCustomerNoType[i] );
            tLCCustomerImpartSchema.setImpartCode(tImpartCode[i]);
            tLCCustomerImpartSchema.setImpartContent(tImpartContent[i]);
            tLCCustomerImpartSchema.setImpartVer(tImpartVer[i]) ;
            tLCCustomerImpartSet.add(tLCCustomerImpartSchema);
          }

          loggerDebug("ProposalSave","end set schema 告知信息...");

          // 特别约定
          LCSpecSet tLCSpecSet = new LCSpecSet();

          String tSpecNum[] = request.getParameterValues("SpecGridNo");
          String tSpecType[] = request.getParameterValues("SpecGrid1");
          String tSpecCode[] = request.getParameterValues("SpecGrid2");
          String tSpecContent[] = request.getParameterValues("SpecGrid3");

          int SpecCount = 0;
          if (tSpecNum != null) SpecCount = tSpecNum.length;
          for (int i = 0; i < SpecCount; i++)  {
            if(tSpecCode[i]==null || tSpecCode[i].trim().equals("")) break;
            if(tSpecContent[i]==null || tSpecContent[i].trim().equals("")) break;

            LCSpecSchema tLCSpecSchema = new LCSpecSchema();

            tLCSpecSchema.setSpecCode(tSpecCode[i]);
            tLCSpecSchema.setSpecType(tSpecType[i]);
            tLCSpecSchema.setSpecContent(tSpecContent[i]);

            tLCSpecSet.add(tLCSpecSchema);
          }

          loggerDebug("ProposalSave","end set schema 特约信息...");

          //是否有责任项
          String tNeedDutyGrid=request.getParameter("inpNeedDutyGrid");
          LCDutySet tLCDutySet=new LCDutySet();
          LCDutySchema tLCDutySchema1=new LCDutySchema();

          if (tNeedDutyGrid.equals("1")) {      //有责任项
          tLCDutySchema1 = new LCDutySchema();
          tLCDutySet=new LCDutySet();
          String tDutyChk[] = request.getParameterValues("InpDutyGridChk");

          String tDutyCode[] =request.getParameterValues("DutyGrid1");
          String tInsuYear[] =request.getParameterValues("DutyGrid3");
          String tInsuYearFlag[] =request.getParameterValues("DutyGrid4");
          String tPayEndYear[] =request.getParameterValues("DutyGrid5");
          String tPayEndYearFlag[] =request.getParameterValues("DutyGrid6");
          String tGetYearFlag[] =request.getParameterValues("DutyGrid7");
          String tGetYear[] =request.getParameterValues("DutyGrid8");
          String tStandbyFlag1[] =request.getParameterValues("DutyGrid9");
          String tStandbyFlag2[] =request.getParameterValues("DutyGrid10");
          String tStandbyFlag3[] =request.getParameterValues("DutyGrid11");
          String tPremToAmnt[] =request.getParameterValues("DutyGrid12");
          String tPrem[] =request.getParameterValues("DutyGrid13");
          String tAmnt[] =request.getParameterValues("DutyGrid14");
          String tMult[] =request.getParameterValues("DutyGrid15");
          String tCalRule[] =request.getParameterValues("DutyGrid16");
          String tFloatRate[] =request.getParameterValues("DutyGrid17");
          String tPayIntv[] =request.getParameterValues("DutyGrid18");
          String tGetLimit[] =request.getParameterValues("DutyGrid19");
          String tGetRate[] =request.getParameterValues("DutyGrid20");
          String tSSFlag[] =request.getParameterValues("DutyGrid21");
          String tPeakLine[] =request.getParameterValues("DutyGrid22");
          String tGetIntv[]=request.getParameterValues("DutyGrid26");
          String tAscription[]=request.getParameterValues("DutyGrid27"); //归属规则
          String tPayrule[]=request.getParameterValues("DutyGrid28"); //缴费规则
          String tBonusGetMode[]=request.getParameterValues("DutyGrid29"); //红利领取方式
          String tGetStartType[]=request.getParameterValues("DutyGrid31"); //起领日期计算方式
          //        String tDutyCode[] =request.getParameterValues("DutyGrid1");
          //        String tDutyPrem1[] =request.getParameterValues("DutyGrid3");
          //        String tDutyGet1[] =request.getParameterValues("DutyGrid4");
          //        String tDutyPayEndYear[] =request.getParameterValues("DutyGrid5");
          //        String tDutyPayEndYearFlag[] =request.getParameterValues("DutyGrid6");
          //        String tDutyGetYear[] =request.getParameterValues("DutyGrid7");
          //        String tDutyGetYearFlag[] =request.getParameterValues("DutyGrid8");
          //        String tDutyInsuYear[] =request.getParameterValues("DutyGrid9");
          //        String tDutyInsuYearFlag[] =request.getParameterValues("DutyGrid10");
          //        String tDutyPayIntv[] =request.getParameterValues("DutyGrid11");
          //

          int DutyCount = 0;
          if (tDutyCode != null) DutyCount = tDutyCode.length;
          for (int i = 0; i < DutyCount; i++)  {
            if(tDutyCode[i]==null || tDutyCode[i].equals("")) break;

            tLCDutySchema1= new LCDutySchema();

            if(!tDutyCode[i].equals("") && tDutyChk[i].equals("1"))  {
              tLCDutySchema1.setDutyCode(tDutyCode[i]);
              tLCDutySchema1.setInsuYear(tInsuYear[i]);
              loggerDebug("ProposalSave","<<<<<<<<<<<<<<>>>>>>>>>>"+tInsuYear[i]);
              tLCDutySchema1.setInsuYearFlag(tInsuYearFlag[i]);
              tLCDutySchema1.setPayEndYear(tPayEndYear[i]);
              tLCDutySchema1.setPayEndYearFlag(tPayEndYearFlag[i]);
              tLCDutySchema1.setGetYearFlag(tGetYearFlag[i]);
              tLCDutySchema1.setGetYear(tGetYear[i]);
              tLCDutySchema1.setStandbyFlag1(tStandbyFlag1[i]);
              tLCDutySchema1.setStandbyFlag2(tStandbyFlag2[i]);
              tLCDutySchema1.setStandbyFlag3(tStandbyFlag3[i]);
              tLCDutySchema1.setPremToAmnt(tPremToAmnt[i]);
              tLCDutySchema1.setPrem(tPrem[i]);
              tLCDutySchema1.setGetIntv(tGetIntv[i]); //增加领取方式
              tLCDutySchema1.setAscriptionRuleCode(tAscription[i]); //增加归属规则 
              tLCDutySchema1.setGetStartType(tGetStartType[i]);
              if(tDutyCode[i].equals("188001"))
              {
                if(tLCDutySchema1.getAscriptionRuleCode()!=null && !tLCDutySchema1.getAscriptionRuleCode().equals(""))
                {
                  %>
			        <script language="javascript">
			        
			        parent.fraInterface.afterSubmit('Fail','个人交费不用选择缴费规则！');
			      
			        </script>
			     <%   
			     FlagStr = "Fail";
	        		
	        		
	        		
	        		return;
                }
              }
              tLCDutySchema1.setPayRuleCode(tPayrule[i]); //增加缴费规则
              if(tBonusGetMode[i]==null || tBonusGetMode[i].equals(""))
              {
              tLCDutySchema1.setBonusGetMode("1"); //红利领取方式
              }else{
              tLCDutySchema1.setBonusGetMode(tBonusGetMode[i]); //红利领取方式
              }
              tLCPolSchema.setBonusGetMode(tLCDutySchema1.getBonusGetMode());
              tLCDutySchema1.setAmnt(tAmnt[i]);
              tLCDutySchema1.setMult(tMult[i]);
                            
              tLCDutySchema1.setFloatRate(tFloatRate[i]);
              if(tFloatRate[i]!=null && tFloatRate[i]!="0" && tCalRule[i]=="0")
              {
                tLCDutySchema1.setCalRule("2");
              }else{
              tLCDutySchema1.setCalRule(tCalRule[i]);
              loggerDebug("ProposalSave","***"+tCalRule[i]);
              }
              loggerDebug("ProposalSave","浮动费率 IN PROPOSALSAVE3＝"+tFloatRate[i]);
              tLCDutySchema1.setSSFlag(tSSFlag[i]);
              tLCDutySchema1.setGetRate(tGetRate[i]);
              tLCDutySchema1.setPayIntv(tPayIntv[i]);
              tLCDutySchema1.setPeakLine(tPeakLine[i]); //majj
              	loggerDebug("ProposalSave","^^^^^^^^^^^^^^^^^^");
              	loggerDebug("ProposalSave","^^^^^^^^^^^^^^^^^^");
              	loggerDebug("ProposalSave","^^^^^^^^^^^^^^^^^^");
              	loggerDebug("ProposalSave","^^^^^^^^^^^^^^^^^^");
              	loggerDebug("ProposalSave","tPayIntv[i]=="+tPayIntv[i]);
              if(request.getParameter("RiskCode").equals("00607000"))
              	{
              	loggerDebug("ProposalSave","tPayIntv[i]=="+tPayIntv[i]);
	              if(tPayIntv[i]!=null&&!tPayIntv[i].equals(""))
		          {
		          	tLCDutySchema1.setPayIntv(tPayIntv[i]);
		          }
	              else
		          {
		             %>
			        <script language="javascript">
			        //alert("您未填入交费方式！");
			        parent.fraInterface.afterSubmit('Fail','您未填入交费方式！');
			      
			        </script>
			     <%   
			        
		          	
	        		FlagStr = "Fail";
	        		loggerDebug("ProposalSave","FlagStr------------------------"+FlagStr);
	        		
	        		
	        		return;
		          }
		 }
		else
		{
			tLCDutySchema1.setPayIntv(tPayIntv[i]);
		}
	         
              //tLCDutySchema1.setPayIntv(tPayIntv[i]);
              tLCDutySchema1.setGetLimit(tGetLimit[i]);



              //            tLCDutySchema1.setDutyCode(tDutyCode[i]);
              //            tLCDutySchema1.setPrem(tDutyPrem1[i]);
              //            tLCDutySchema1.setAmnt(tDutyGet1[i]);
              //            tLCDutySchema1.setPayEndYear(tDutyPayEndYear[i]);
              //            tLCDutySchema1.setPayEndYearFlag(tDutyPayEndYearFlag[i]);
              //            tLCDutySchema1.setGetYear(tDutyGetYear[i]);
              //            tLCDutySchema1.setGetYearFlag(tDutyGetYearFlag[i]);
              //            tLCDutySchema1.setInsuYear(tDutyInsuYear[i]);
              //            tLCDutySchema1.setInsuYearFlag(tDutyInsuYearFlag[i]);
              //            tLCDutySchema1.setPayIntv(tDutyPayIntv[i]);
              //                loggerDebug("ProposalSave","payintv:"+tDutyPayIntv[i]);
              //                loggerDebug("ProposalSave","prem:"+tDutyPrem1[i]);
              tLCDutySet.add(tLCDutySchema1);

            } // end of if
          } // end of for
        } // end of if



        loggerDebug("ProposalSave","end set schema 责任信息..."+tNeedDutyGrid);


        //是否有多个保费项
        String tNeedPremGrid="0";
        LCPremSet tLCPremSet=new LCPremSet();
        LCPremSchema tLCPremSchema1=new LCPremSchema();
        LCPremToAccSet tLCPremToAccSet = new LCPremToAccSet();
        LCPremToAccSchema tLCPremToAccSchema = new LCPremToAccSchema();

        try
        { //捕获保费项错误
        tNeedPremGrid=request.getParameter("inpNeedPremGrid");

        loggerDebug("ProposalSave"," tNeedPremGrid:"+tNeedPremGrid);
        if (tNeedPremGrid.equals("1")) {      //有保费项
        tLCPremSchema1 = new LCPremSchema();
        tLCPremSet=new LCPremSet();
        //责任编码
        String tPremDutyCode[] =request.getParameterValues("PremGrid1");
        //缴费项编码
        String tPremPayCode[] =request.getParameterValues("PremGrid2");
        //交费项名称
        String tPremPayCodeName[] =request.getParameterValues("PremGrid3");
        //保费
        String tPremStandPrem[] =request.getParameterValues("PremGrid4");
        //分配比例
        String tPremRate[] =request.getParameterValues("PremGrid5");
        //关联帐户号
        String tPremAccNo[] =request.getParameterValues("PremGrid6");
        //帐户分配比率
        String tPremAccRate[] =request.getParameterValues("PremGrid7");

        //管理费比例 add by guoxiang 2004-9-7 10:34 暂时没用
        //String tManageFeeRate[]=request.getParameterValues("PremGrid8");
        String tPremChk[] = request.getParameterValues("InpPremGridChk");

        //交费占员工工资比例
        String tSalaryRate[] = request.getParameterValues("PremGrid9");

        double actuPrem=0;
        int PremCount = 0;
        if (tPremDutyCode != null) PremCount = tPremDutyCode.length;
        loggerDebug("ProposalSave","&&&&&&&&&&&&&&&&"+PremCount);
        for (int i = 0; i < PremCount; i++)
        {
          if(tPremDutyCode[i]==null || tPremDutyCode[i].equals("")) break;

          tLCPremSchema1= new LCPremSchema();
          tLCPremToAccSchema = new LCPremToAccSchema();

          if(!tPremDutyCode[i].equals("")) //&& tPremChk[i].equals("1")
          {
            loggerDebug("ProposalSave"," tPremDutyCode:"+tPremDutyCode[i]);
            tLCPremSchema1.setDutyCode(tPremDutyCode[i]);
            tLCPremSchema1.setPayPlanCode(tPremPayCode[i]);

            //actuPrem=Double.parseDouble(tPremStandPrem[i])*Double.parseDouble(tPremRate[i]);
            //loggerDebug("ProposalSave"," tPremStandPrem:"+Double.parseDouble(tPremStandPrem[i]));
            //loggerDebug("ProposalSave"," tPremRate:"+Double.parseDouble(tPremRate[i]));
            //loggerDebug("ProposalSave"," actuPrem:"+actuPrem);



            tLCPremSchema1.setStandPrem(tPremStandPrem[i]);   //每个保费项上的保费在js页里算出
            tLCPremSchema1.setPrem(tPremStandPrem[i]);
            loggerDebug("ProposalSave","--------------prem"+i+":"+tPremStandPrem[i]);
            //tLCPremSchema1.setStandPrem(actuPrem);
            //tLCPremSchema1.setPrem(actuPrem);
            tLCPremSchema1.setRate(tPremRate[i]);

            //add by guoxiang 2004-9-7 10:33 for 管理费比例 暂时没用
            //tLCPremSchema1.setManageFeeRate(tManageFeeRate[i]);

            tLCPremToAccSchema.setDutyCode( tPremDutyCode[i] );
            tLCPremToAccSchema.setPayPlanCode( tPremPayCode[i] );
            tLCPremToAccSchema.setInsuAccNo( tPremAccNo[i] );
            tLCPremToAccSchema.setRate( tPremAccRate[i] );

            tLCPremSet.add(tLCPremSchema1);
            tLCPremToAccSet.add(tLCPremToAccSchema);

          } // end of if
        } // end of for
      } // end of if

    } catch(Exception ex){loggerDebug("ProposalSave","set schema 保费项信息...error !!!");}

    loggerDebug("ProposalSave","end set schema 保费项信息...");

    //传递非SCHEMA信息
    TransferData tTransferData = new TransferData();

    tTransferData.setNameAndValue("GetIntv", request.getParameter("GetIntv"));                //领取间隔（方式）
    tTransferData.setNameAndValue("GetDutyKind", request.getParameter("GetDutyKind"));        //年金开始领取年龄


    if (request.getParameter("SamePersonFlag") == null)
    tTransferData.setNameAndValue("samePersonFlag", "0"); //投保人同被保人标志
    else
    tTransferData.setNameAndValue("samePersonFlag", "1"); //投保人同被保人标志

    if (!ChangePlanFlag)
    {
      tTransferData.setNameAndValue("deleteAccNo", "1");
      tTransferData.setNameAndValue("ChangePlanFlag", "1");
    }
    else
    {
      tTransferData.setNameAndValue("deleteAccNo", "0");
      tTransferData.setNameAndValue("ChangePlanFlag", "0");
    }


    tTransferData.setNameAndValue("SavePolType", request.getParameter("BQFlag")); //保全保存标记
    tTransferData.setNameAndValue("EdorType", request.getParameter("EdorType"));
    tTransferData.setNameAndValue("EdorTypeCal", request.getParameter("EdorTypeCal"));
    loggerDebug("ProposalSave","^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^"+request.getParameter("EdorTypeCal"));
    loggerDebug("ProposalSave","SavePolType : " + request.getParameter("BQFlag"));


    // 准备传输数据 VData
    //tVData.addElement(tLCContSchema);
    //tVData.addElement(tLCPolSchema);
    //tVData.addElement(tLCAppntSchema);
    //tVData.addElement(tLCGrpAppntSchema);
    //tVData.addElement(tLCInsuredSchema);
    LCInsuredRelatedSet mLCInsuredRelatedSet=new LCInsuredRelatedSet();
    ////add by yaory write on 2005-7-6-17:43
//    try{
//      String tRadio[] = request.getParameterValues("InpInsuredGridSel");
//
//      //loggerDebug("ProposalSave","宝宝呵呵行数===="+tRadio.length);
//      ////////取出所有MULTINE数值///
//      String tGrid1 [] = request.getParameterValues("InsuredGrid1"); //得到第1列的所有值
//      String tGrid2 [] = request.getParameterValues("InsuredGrid2");
//      String tGrid3 [] = request.getParameterValues("InsuredGrid3");
//      String tGrid4 [] = request.getParameterValues("InsuredGrid4");
//      String tGrid5 [] = request.getParameterValues("InsuredGrid5");
//      LCInsuredRelatedSchema tLCInsuredRelatedSchema=new LCInsuredRelatedSchema();
//      loggerDebug("ProposalSave","总共的行数===="+tRadio.length);
//      for(int i=0; i<tRadio.length;i++)
//      {
//        if(tRadio[i].equals("1"))
//        {
//          loggerDebug("ProposalSave","进入操作");
//          /////说明-主被保险人客户号未做处理
//          //loggerDebug("ProposalSave","该行被选中=="+ tGrid1 [i]);
//          tLCInsuredRelatedSchema.setCustomerNo(tGrid1 [i]);
//          //loggerDebug("ProposalSave","该行被选中=="+ tGrid2 [i]);
//          tLCInsuredRelatedSchema.setName(tGrid2 [i]);
//          //loggerDebug("ProposalSave","该行被选中=="+ tGrid3 [i]);
//          tLCInsuredRelatedSchema.setSex(tGrid3 [i]);
//          //loggerDebug("ProposalSave","该行被选中=="+ tGrid4 [i]);
//          tLCInsuredRelatedSchema.setBirthday(tGrid4 [i]);
//          loggerDebug("ProposalSave","该行被选中=="+ tGrid5 [i]);
//          tLCInsuredRelatedSchema.setRelationToInsured(tGrid5 [i]);
//
//          loggerDebug("ProposalSave","########################");
//          loggerDebug("ProposalSave","########################");
//          loggerDebug("ProposalSave","aaaa="+tGrid1 [i]);
//          loggerDebug("ProposalSave","########################");
//          loggerDebug("ProposalSave","########################");
//          tLCPolSchema.setStandbyFlag1(tGrid1 [i]);
//          tLCDutySchema.setStandbyFlag1(tGrid1 [i]);
//          tLCInsuredRelatedSchema.setOperator(request.getParameter("AgentCode"));
//          tLCInsuredRelatedSchema.setMakeDate(request.getParameter("AgentCode"));
//          tLCInsuredRelatedSchema.setMakeTime(request.getParameter("AgentCode"));
//          tLCInsuredRelatedSchema.setSequenceNo(request.getParameter("SequenceNo"));
//          //tLCInsuredRelatedSchema.setSequenceNo("01");
//          if(tGrid5 [i].equals("00"))
//          {
//            loggerDebug("ProposalSave","要的就是你"); //主被保人客户号
//            tLCInsuredRelatedSchema.setMainCustomerNo(tGrid5 [i]);
//          }
//          tLCInsuredRelatedSchema.setPolNo(request.getParameter("ProposalNo"));
//        }
//        if(tRadio[i].equals("0"))
//        {
//          //loggerDebug("ProposalSave","该行未被选中");
//          if(tGrid5 [i].equals("00"))
//          {
//            //loggerDebug("ProposalSave","要的就是你"); //主被保人客户号
//            tLCInsuredRelatedSchema.setMainCustomerNo(tGrid5 [i]);
//          }
//        }
//      }
//      for(int i=0; i<tRadio.length;i++)
//      {
//        if(tRadio[i].equals("1"))
//        {
//          mLCInsuredRelatedSet.add(tLCInsuredRelatedSchema);
//        }
//      }
//    }catch(Exception ex){}
    ///end add by yaory 2005-7-6
      
    tVData.addElement(tLCContSchema);
    tVData.addElement(tLCPolSchema);
    tVData.addElement(tLCAppntSchema);
    tVData.addElement(tLCGrpAppntSchema);
    tVData.addElement(tLCInsuredSchema);
    tVData.addElement(mLCInsuredRelatedSet);
    tVData.addElement(tLCBnfSet);
    tVData.addElement(tLCCustomerImpartSet);
    tVData.addElement(tLCSpecSet);
    tVData.addElement(tG);
    tVData.addElement(tTransferData);
    
    loggerDebug("ProposalSave","tLCPolSchema:"+tLCPolSchema.encode());
    
    loggerDebug("ProposalSave","tLCDutySchema:"+tLCDutySchema.encode());
    
    loggerDebug("ProposalSave","tLCDutySet:"+tLCDutySet.encode());

    //如果是可选责任项
    if (("1").equals(tNeedDutyGrid))
    tVData.addElement(tLCDutySet);
    else
    tVData.addElement(tLCDutySchema);

    //如果是可选保费项
    if (tNeedPremGrid!=null&&tNeedPremGrid.equals("1"))
    {
      tVData.addElement(tLCPremSet);
      tVData.addElement(tLCPremToAccSet);
    }


  } // end of if




  // 数据传输

  if( !tBusinessDelegate.submitData( tVData, tOperate, busiName) ) {
    Content = " 保存失败，原因是: " + tBusinessDelegate.getCErrors().getError(0).errorMessage;
    FlagStr = "Fail";
  }
  else {
    Content = "*************Proposal 保存成功!*************";
    FlagStr = "Succ";

    tVData.clear();
    tVData = tBusinessDelegate.getResult();

    // 显示
    // 保单信息
    mLCPolSchema.setSchema(( LCPolSchema )tVData.getObjectByObjectName( "LCPolSchema", 0 ));
    mLCDutyBLSet.set((LCDutyBLSet)tVData.getObjectByObjectName( "LCDutyBLSet", 0 ));
    
  }
  //无名单补名单，直接签单
  try  {
    if (FlagStr.equals("Succ") && request.getParameter("BQFlag")!=null && request.getParameter("BQFlag").equals("4")) {
      loggerDebug("ProposalSave","\nNewProposalNo:" + mLCPolSchema.getProposalNo());

      GrpAddNameSignBL tGrpAddNameSignBL = new GrpAddNameSignBL();
      if (!tGrpAddNameSignBL.dealSign(mLCPolSchema, tG)) {
        Content = "无名单补名单签单失败，原因是: " + tGrpAddNameSignBL.mErrors.getError(0).errorMessage;
        FlagStr = "Fail";
      }
      else {
        Content = "无名单补名单保存、核保、签单成功！";
      }
    } // end of if
  }
  catch ( Exception e2 ) {
    Content = "无名单补名单签单失败，原因是: " + e2.toString();
    FlagStr = "Fail";
  }

  //承保计划变更，恢复投保单状态
  if (ChangePlanFlag) {
    LCPolSchema tLCPolSchema = new LCPolSchema();
    tLCPolSchema.setPolNo(request.getParameter("ProposalNo"));
    tLCPolSchema.setApproveFlag(approveFlag);
    tLCPolSchema.setUWFlag(UWFlag);
    tLCPolSchema.setApproveCode(approveCode);
    tLCPolSchema.setApproveDate(approveDate);

    VData VData3 = new VData();
    VData3.add(tLCPolSchema);
    String busiName1="cbcheckgrppubfunLockTableUI";
    BusinessDelegate tBusinessDelegate1=BusinessDelegate.getBusinessDelegate();
   // ChangePlanUI tChangePlanUI = new ChangePlanUI();
    if (!tBusinessDelegate1.submitData(VData3, "INSERT||CHANGEPLAN",busiName1)) {
      Content = "操作失败，原因是: " + tBusinessDelegate1.getCErrors().getError(0).errorMessage;
      FlagStr = "Fail";
      throw new Exception();
    }

  }

} // end of try
catch( Exception e1 )  {
  Content = " 保存失败，原因是: " + e1.toString().trim();
  FlagStr = "Fail";
}

if(FlagStr.equals("Succ")&&!tAction.equals( "DELETE" ))  {
  %>
  <script language="javascript">
    var dutyArray = new Array();
    </script>
    <%
    loggerDebug("ProposalSave",mLCDutyBLSet.encode());
    for (int j=1;j<=mLCDutyBLSet.size();j++)
    {
      %>
      <script language="javascript">
        dutyArray[<%=j-1%>]=new Array();
        dutyArray[<%=j-1%>][0]='<%=mLCDutyBLSet.get(j).getDutyCode()%>';
        dutyArray[<%=j-1%>][1]='<%=mLCDutyBLSet.get(j).getPrem()%>';
        dutyArray[<%=j-1%>][2]='<%=mLCDutyBLSet.get(j).getAmnt()%>';
        </script>
        <%
        }
        %>
        <script language="javascript">
          try
          {

            if (parent.fraInterface.fm.all('inpNeedDutyGrid').value==1)
            {
              var mulLineCount=parent.fraInterface.DutyGrid.mulLineCount;
              for (i=0;i<mulLineCount;i++)
              {
                var dutycode=parent.fraInterface.DutyGrid.getRowColData(i,1);
                for (j=0;j<dutyArray.length;j++)
                {
                  if (dutyArray[j][0]==dutycode)
                  {
                    parent.fraInterface.DutyGrid.setRowColData(i,13,dutyArray[j][1]);
                    parent.fraInterface.DutyGrid.setRowColData(i,14,dutyArray[j][2]);
                  }
                }

              }
            }
            else
            {

              parent.fraInterface.fm.all("Prem").value = "<%=PubFun.round(mLCPolSchema.getPrem(),2)%>";

              parent.fraInterface.fm.all("Amnt").value = "<%=PubFun.round(mLCPolSchema.getAmnt(),2)%>";

            }
            parent.fraInterface.fm.all("ContNo").value = "<%=mLCPolSchema.getContNo()%>";
            //parent.fraInterface.fm.all("GrpPolNo").value = "<%=mLCPolSchema.getGrpPolNo()%>";
            parent.fraInterface.fm.all("ProposalNo").value = "<%=mLCPolSchema.getProposalNo()%>";


            parent.fraInterface.fm.all("ContType").value = "<%=mLCPolSchema.getContType()%>";

            if(parent.VD.gVSwitch.addVar("mainRiskPolNo","","<%=mLCPolSchema.getMainPolNo()%>")==false){
              parent.VD.gVSwitch.updateVar("mainRiskPolNo","","<%=mLCPolSchema.getMainPolNo()%>");
            }

            //录入修改
            if (parent.fraInterface.LoadFlag == 2) {

              parent.fraInterface.inputButton.style.display = "none";
              parent.fraInterface.divButton.style.display = "";
              parent.fraInterface.modifyButton.style.display = "";
              //parent.fraInterface.riskbutton31.disabled='';
              //parent.fraInterface.riskbutton32.disabled='';


            }
          } catch(ex) {
            alert("after Save but happen err:" + ex);
          }
          </script>

          <%
          } // end of if

          loggerDebug("ProposalSave",Content);

          //替换掉错误信息的特殊字符
          Content = Content.replace('\n', ' ');

          while (Content.indexOf("\"") != -1) {
            Content = Content.replace('\"', ' ');
            //loggerDebug("ProposalSave",Content);
          }


          loggerDebug("ProposalSave",Content);
          loggerDebug("ProposalSave",FlagStr);
          
         
          
          %>

          <html>
            <script language="javascript">
              try
              {
                parent.fraInterface.afterSubmita('<%=FlagStr%>','<%=Content%>');
              }  catch(ex) {
                //alert("after Save but happen err:" + ex);
              }
              </script>
              <%
              if(( tAction.equals( "INSERTPERSON" ) || tAction.equals( "UPDATEPERSON" )) && FlagStr.equals( "Succ" ))
              {
                %>
                <script language="javascript">
                  try {
                    var prtNo = "<%=mLCPolSchema.getPrtNo()%>";
                    if (prtNo.substring(2, 4) == "11") {
                      parent.fraPic.goToPic(3); top.fraPic.scrollTo(0, 0);
                    }
                    else if (prtNo.substring(2, 4) == "15") {
                      parent.fraPic.goToPic(0); top.fraPic.scrollTo(300, 2000);
                    }
                    //parent.fraInterface.showFee();
                  }
                  catch(ex) {}
                  </script>
                  <%
                  } // end of if
                }
%>
</html>

