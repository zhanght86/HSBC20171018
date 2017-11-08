var showInfo;
var turnPage = new turnPageClass();
var turnPage2 = new turnPageClass();
var turnPage3 = new turnPageClass();
var mySql = new SqlClass();
//由客户查询（LLLdPersonQuery.js）返回单条记录时调用
function afterQueryLL(arr)
{
  
    fm.customerNo.value   = arr[0];//投保人客户号
    fm.customerName.value = arr[1];//投保人姓名
    fm.customerSex.value  = arr[2];//性别
    fm.customerAge.value  = arr[3];//年纪
    fm.IDNo.value         = arr[6];//ID号
    fm.IDTypeName.value   = arr[5];//证件类型名称
    fm.IDType.value       = arr[7];//证件类型
    showOneCodeName('sex','customerSex','SexName');//性别
    fm.QueryCont2.disabled = false; //返回数据后，将QueryCont2设置为可用
    fm.QueryCont3.disabled = false; //返回数据后，将QueryCont3设置为可用
    //初始化录入域
    fm.AccidentDate2.value = "";
    for(var j=0;j<fm.claimType.length;j++)
    {
        fm.claimType[j].checked = false;
    }

}

function afterQuery(mRgtNo){
if(mRgtNo!=''){
document.all('RptNo').value = mRgtNo;
queryRegister();
}
}
//立案查询
function queryRegister()
{
  
    var rptNo = document.all('RptNo').value;
    var tFlag = fm.Flag.value;
    if(rptNo == "")
    {
      
     fm.updatebutton.disabled = true;
     fm.QueryCont2.disabled = true;
     fm.QueryCont3.disabled = true;
     fm.QueryReport.disabled = true;
     
     fm.dutySet.disabled = true;
     fm.QuerydutySet.disabled = true;
     fm.addUpdate.disabled = true;
     fm.simpleClaim.disabled = true;
     divSimpleClaim2.style.display= "";
     divSimpleClaim3.style.display= "none";
    }else{
      
     fm.updatebutton.disabled = false;
     fm.QueryCont2.disabled = false;
     fm.QueryCont3.disabled = false;
     fm.QueryReport.disabled = false;
     if(tFlag =="FROMSIMALL")
        fm.dutySet.disabled = true;
     else
         fm.dutySet.disabled = false;
     fm.QuerydutySet.disabled = false;
     fm.addUpdate.disabled = false;
     fm.simpleClaim.disabled = false;
    }
      
    //检索事件号、意外事故发生日期(一条)
   /* var strSQL1 = "select LLCaseRela.CaseRelaNo,LLAccident.AccDate from LLCaseRela,LLAccident where LLCaseRela.CaseRelaNo = LLAccident.AccNo and "
                + "LLCaseRela.CaseNo = '"+rptNo+"'";*/
    mySql = new SqlClass();
mySql.setResourceName("claimgrp.LLGrpSimpleAllInputSql");
mySql.setSqlId("LLGrpSimpleAllSql1");
mySql.addSubPara(rptNo); 
    var AccNo = easyExecSql(mySql.getString());
    //检索立案号及其他立案信息(一条)
   /* var strSQL2 = "select AppntNo,GrpName,GrpContNo,RgtNo,Peoples2,AppPeoples,RgtantName,AccidentReason,RgtConclusion,RgtClass,clmState,RiskCode,Operator from llregister where "
                + "rgtno = '"+rptNo+"'";*/
    mySql = new SqlClass();
mySql.setResourceName("claimgrp.LLGrpSimpleAllInputSql");
mySql.setSqlId("LLGrpSimpleAllSql2");
mySql.addSubPara(rptNo); 
    var RptContent = easyExecSql(mySql.getString());
    //更新页面内容
    if(AccNo!=null)
    {
        fm.AccNo.value = AccNo[0][0];
        //fm.AccidentDate.value = AccNo[0][1];
    }

    if(RptContent!=null)
    {
        fm.GrpCustomerNo.value = RptContent[0][0];
        fm.GrpName.value = RptContent[0][1];
        fm.GrpContNo.value = RptContent[0][2];
        fm.RptNo.value = RptContent[0][3];
        fm.Peoples.value = RptContent[0][4];
        fm.customerName.value = RptContent[0][6];
        fm.occurReason.value = RptContent[0][7];
        fm.clmState.value = RptContent[0][8];
        fm.RgtClass.value = RptContent[0][9];
        fm.clmState.value = RptContent[0][10];
        fm.Polno.value = RptContent[0][11];
        fm.tOperator.value = RptContent[0][12];

        showOneCodeName('lloccurreason','occurReason','ReasonName');//出险原因
    
        fm.clmState2.value = RptContent[0][10]
        //结案按键控制
        if( fm.clmState2.value == '60' )
        {
             fm.QueryPerson.disabled = true;
             fm.addbutton.disabled = true;
             fm.Inputbutton.disabled = true;
             fm.addbutton.disabled = true;
             fm.addbutton2.disabled = true;
             fm.updatebutton.disabled = true;
             fm.deletebutton.disabled = true;
             fm.QueryCont2.disabled = true;
             fm.QueryCont3.disabled = true;
             fm.QueryReport.disabled = true;
             fm.dutySet.disabled = true;
             fm.addUpdate.disabled = true;
             fm.simpleClaim.disabled = true;
             fm.SimpleConclusion.value = '0';
             showOneCodeName('llexamconclusion','SimpleConclusion','SimpleConclusionName');//出险原因
             divSimpleClaim2.style.display= "none";
             divSimpleClaim3.style.display= "";
         }else if( fm.clmState2.value == '40')//复核按键控制
         {
             fm.QueryPerson.disabled = true;
             fm.addbutton.disabled = true;
             fm.addbutton.disabled = true;
             fm.addbutton2.disabled = true;
             fm.updatebutton.disabled = true;
             fm.deletebutton.disabled = true;
             fm.QueryCont2.disabled = true;
             fm.QueryCont3.disabled = true;
             fm.QueryReport.disabled = true;
             fm.dutySet.disabled = true;
             fm.addUpdate.disabled = true;
            if(fm.Flag.value == '2')
            {
                 divSimpleClaim2.style.display= "none";
                 divSimpleClaim3.style.display= "";
            }
            else
            {
                 divSimpleClaim2.style.display= "";
                 divSimpleClaim3.style.display= "none";
            }
        }
        else
        {
             divSimpleClaim2.style.display= "";
             divSimpleClaim3.style.display= "none";
        }

    }
  
   // var strSQL4 = "select count(*) CustomerNo from llcase where caseno = '"+rptNo+"'";
     mySql = new SqlClass();
mySql.setResourceName("claimgrp.LLGrpSimpleAllInputSql");
mySql.setSqlId("LLGrpSimpleAllSql3");
mySql.addSubPara(rptNo); 
    var CustomerNoCount = easyExecSql(mySql.getString());
    if(CustomerNoCount!=null)
    {
        fm.PeopleNo.value = CustomerNoCount[0][0];
    }
    
    //****************************************************
    //更新页面权限
    //****************************************************
    fm.AccidentDate.readonly = true;
    fm.claimType.disabled=true;
    
    //检索分案关联出险人信息(多条)
   /* var strSQL1 = "select count(*) from LDPerson where "
                + "CustomerNo in("
                + "select CustomerNo from LLSubReport where SubRptNo = '"+ rptNo +"')";*/
 //     alert("rptNo"+rptNo);          
    mySql = new SqlClass();
mySql.setResourceName("claimgrp.LLGrpSimpleAllInputSql");
mySql.setSqlId("LLGrpSimpleAllSql4");
mySql.addSubPara(rptNo); 
    var count = easyExecSql(mySql.getString());
   
    if(count > 0)
    {
      
      /* var strSQL3 = "select a.CustomerNo,a.Name,a.Sex,a.Birthday,"
                    + " a.VIPValue,(select codename from ldcode where "
                    + " codetype = 'idtype' and code = a.IDType),a.IDNo,"
                    + " a.IDType, b.accdate from LDPerson a,LLCase b where "
                    + " a.CustomerNo=b.CustomerNo "                 
                    + " and b.CaseNo = '"+ rptNo +"' order by lpad(b.seconduwflag,4,'0')";*/
       mySql = new SqlClass();
mySql.setResourceName("claimgrp.LLGrpSimpleAllInputSql");
mySql.setSqlId("LLGrpSimpleAllSql5");
mySql.addSubPara(rptNo);               
       //  var strSQL3 = "select a.CustomerNo,a.Name,a.Sex,a.Birthday,"
       //             + " a.VIPValue from LDPerson a,LLCase b where "
        //            + " a.CustomerNo=b.CustomerNo "                 
         //           + " and b.CaseNo = '"+ rptNo +"' order by lpad(b.seconduwflag,4,'0')";            
                    
        turnPage3.queryModal(mySql.getString(),SubReportGrid);
        if (SubReportGrid.getRowColData(0,1) != null && SubReportGrid.getRowColData(0,1) != "")
        {
            SubReportGridClick(0);
        }
    }
   

}

//选中SubReportGrid响应事件,tPhase=0为初始化时调用
function SubReportGridClick(tPhase)
{
  //********************************************Beg
  //置空相关表单
  //********************************************
  fm.customerName.value = "";
  fm.customerAge.value = "";
  fm.customerSex.value = "";
  fm.SexName.value = "";
  fm.AccidentDate2.value = "";
  fm.claimType.value = "";
  fm.IDType.value = "";
  fm.IDTypeName.value = "";
  fm.IDNo.value = "";
  //理赔类型置空
    for (var j = 0;j < fm.claimType.length; j++)
    {
        fm.claimType[j].checked = false;
    }
    //********************************************End

    //取得数据
    var i = "";
    if (tPhase == "0")
    {
        i = 1;
    }
    else
    {
        i = SubReportGrid.getSelNo();
    }
    if (i != '0')
    {
        i = i - 1;
        fm.customerNo.value = SubReportGrid.getRowColData(i,1);
        fm.customerName.value = SubReportGrid.getRowColData(i,2);
        fm.customerSex.value = SubReportGrid.getRowColData(i,3);
        fm.customerAge.value = calAge(SubReportGrid.getRowColData(i,4));
        fm.IDTypeName.value = SubReportGrid.getRowColData(i,6);
        fm.IDNo.value = SubReportGrid.getRowColData(i,7);
        fm.IDType.value = SubReportGrid.getRowColData(i,8);
        showOneCodeName('sex','customerSex','SexName');//性别
        fm.AccidentDate.value = SubReportGrid.getRowColData(i,9);//出险日期
    }

    //查询获得理赔类型
    var tClaimType = new Array;
   /* var strSQL1 = "select ReasonCode from LLAppClaimReason where "
                + " CaseNo = '"+fm.RptNo.value+"'"
                + " and CustomerNo = '"+fm.customerNo.value+"'";*/
    mySql = new SqlClass();
mySql.setResourceName("claimgrp.LLGrpSimpleAllInputSql");
mySql.setSqlId("LLGrpSimpleAllSql6");
mySql.addSubPara(fm.RptNo.value);  
mySql.addSubPara(fm.customerNo.value);  
    tClaimType = easyExecSql(mySql.getString());
    if (tClaimType == null)
    {
        alert("理赔类型为空，请检查此记录的有效性！");
        return;
    }
    else
    {
        for(var j=0;j<fm.claimType.length;j++)
        {
            for (var l=0;l<tClaimType.length;l++)
            {
                var tClaim = tClaimType[l].toString();
                tClaim = tClaim.substring(tClaim.length-2,tClaim.length);//取理赔类型后两位
                if(fm.claimType[j].value == tClaim)
                {
                    fm.claimType[j].checked = true;
                }
            }
        }
    }
//根据选中的出险人显示相关的理算结果
afterMatchDutyPayQuery();
}

//选中PolDutyCodeGrid响应事件
function PolDutyCodeGridClick()
{
    if(document.all('Flag').value != '2'){
    //清空表单
    fm.GiveType.value = "";//赔付结论
    fm.RealPay.value = "";
    fm.AdjReason.value = "";//调整原因
    fm.AdjReasonName.value = "";//
    fm.AdjRemark.value = "";//调整备注
    fm.GiveTypeDesc.value = "";//拒付原因代码
    fm.GiveReason.value = "";//拒付依据
    fm.SpecialRemark.value = "";//特殊备注
    var tRiskCode = '';
    //设置按钮
    if(fm.clmState2.value == '60'){
    fm.addUpdate.disabled = true;//添加修改
    }else{
    fm.addUpdate.disabled = false;//添加修改
    }
    //得到mulline信息
    var i = PolDutyCodeGrid.getSelNo();
    if (i != '0')
    {
        i = i - 1;
        fm.GiveType.value = PolDutyCodeGrid.getRowColData(i,13);//赔付结论
        fm.RealPay.value = PolDutyCodeGrid.getRowColData(i,21);
        fm.AdjReason.value = PolDutyCodeGrid.getRowColData(i,22);//调整原因
        fm.AdjReasonName.value = PolDutyCodeGrid.getRowColData(i,23);//
        fm.AdjRemark.value = PolDutyCodeGrid.getRowColData(i,24);//调整备注
        fm.GiveTypeDesc.value = PolDutyCodeGrid.getRowColData(i,15);//拒付原因代码
        fm.GiveReason.value = PolDutyCodeGrid.getRowColData(i,17);//拒付依据
        fm.SpecialRemark.value = PolDutyCodeGrid.getRowColData(i,18);//特殊备注
        showOneCodeName('llpayconclusion','GiveType','GiveTypeName');
        showOneCodeName('llprotestreason','GiveTypeDesc','GiveTypeDescName');
        tRiskCode = PolDutyCodeGrid.getRowColData(i,3);//险种编码
    }
    //帐户型险种判断，是的不允许修改理算金额
   // var sql1 = " select insuaccflag From lmrisk where riskcode = '"+tRiskCode+"'";
    mySql = new SqlClass();
mySql.setResourceName("claimgrp.LLGrpSimpleAllInputSql");
mySql.setSqlId("LLGrpSimpleAllSql7");
mySql.addSubPara(tRiskCode);  
    var tInsuaccFlag = easyExecSql(mySql.getString());
    //长险险种判断，是的不允许修改理算金额
   // var sql2 = " select riskperiod from lmriskapp where riskcode = '"+tRiskCode+"'";
    mySql = new SqlClass();
mySql.setResourceName("claimgrp.LLGrpSimpleAllInputSql");
mySql.setSqlId("LLGrpSimpleAllSql8"); 
mySql.addSubPara(tRiskCode); 
    var tRiskPeriod = easyExecSql(mySql.getString());
    //显示隐藏层
    if(tInsuaccFlag != 'Y' && tRiskPeriod != 'L'){
    divBaseUnit5.style.display= "";
    choiseGiveTypeType();
    }else{
    divBaseUnit5.style.display= "none";
    }
    }
}

//对保项增加修改
function AddUpdate()
{
    checkAdjMoney();//检查保项金额调整
    var i = PolDutyCodeGrid.getSelNo() - 1;//得到焦点行

    PolDutyCodeGrid.setRowColData(i,13,fm.GiveType.value);//赔付结论
    PolDutyCodeGrid.setRowColData(i,15,fm.GiveTypeDesc.value);//拒付原因代码
    PolDutyCodeGrid.setRowColData(i,17,fm.GiveReason.value);//拒付依据
    PolDutyCodeGrid.setRowColData(i,18,fm.SpecialRemark.value);//特殊备注
    PolDutyCodeGrid.setRowColData(i,21,fm.RealPay.value);
    PolDutyCodeGrid.setRowColData(i,22,fm.AdjReason.value);//调整原因
    PolDutyCodeGrid.setRowColData(i,23,fm.AdjReasonName.value);//
    PolDutyCodeGrid.setRowColData(i,24,fm.AdjRemark.value);//调整备注
    if(fm.GiveType.value == 0){
    PolDutyCodeGrid.setRowColData(i,14,"给付");//赔付结论名称
    }else{
    PolDutyCodeGrid.setRowColData(i,14,"拒付");//赔付结论名称
    }
    fm.saveUpdate.disabled = false;//保存修改
}

//对保项修改保存到后台
function SaveUpdate()
{
    fm.action = './LLClaimAuditGiveTypeSave.jsp';
    fm.fmtransact.value = "UPDATE";
    submitForm();
}

//选择赔付结论
function choiseGiveTypeType()
{
    if (fm.GiveType.value == '0')
  {
        divGiveTypeUnit1.style.display= "";
        divGiveTypeUnit2.style.display= "none";
    }
    else if (fm.GiveType.value == '1')
    {
        divGiveTypeUnit1.style.display= "none";
        divGiveTypeUnit2.style.display= "";
    }
}

//进行保险责任匹配
function showMatchDutyPay()
{
    /*var strSQL = "select count(*) from lcinsureaccclass where accascription = '0'"
                + " and grpcontno = '" + fm.GrpContNo.value+"'";*/
     mySql = new SqlClass();
mySql.setResourceName("claimgrp.LLGrpSimpleAllInputSql");
mySql.setSqlId("LLGrpSimpleAllSql9"); 
mySql.addSubPara(fm.GrpContNo.value); 
    var tCount = easyExecSql(mySql.getString());
    if(tCount > 0){
       if(confirm("您确定已经做了归属?"))
      {
    mOperate="MATCH||INSERT";
    var showStr="正在进行保险责任匹配操作，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
//    showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
    var name='提示';   //网页名称，可为空; 
    var iWidth=550;      //弹出窗口的宽度; 
    var iHeight=250;     //弹出窗口的高度; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

    showInfo.focus();

    fm.hideOperate.value=mOperate;
    fm.action = "./LLClaimRegisterMatchCalSave.jsp";
    fm.submit(); //提交

      }else{
        alert("请到保全做归属!");
        return false;
      }
    }else{

    mOperate="MATCH||INSERT";
    var showStr="正在进行保险责任匹配操作，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
//    showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
    var name='提示';   //网页名称，可为空; 
    var iWidth=550;      //弹出窗口的宽度; 
    var iHeight=250;     //弹出窗口的高度; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

    showInfo.focus();

    fm.hideOperate.value=mOperate;
    fm.action = "./LLClaimRegisterMatchCalSave.jsp";
    fm.submit(); //提交

    }

}

//匹配后的查询

function afterMatchDutyPayQuery()
{
    var tSql;
    var arr;

    var tClaimNo = fm.RptNo.value;         //赔案号
    var tCaseNo = fm.RptNo.value;          //分案号
    var tCustNo = fm.customerNo.value;          //客户号

    //查询整个赔案的金额
   /* tSql = " select a.StandPay ,a.BeforePay,a.BalancePay,a.RealPay,a.DeclinePay,"
       +" '','','' "
       +" from LLClaim a  where 1=1 "
       +" and a.caseno = '"+tClaimNo+"'"
       ;*/
    mySql = new SqlClass();
mySql.setResourceName("claimgrp.LLGrpSimpleAllInputSql");
mySql.setSqlId("LLGrpSimpleAllSql10"); 
mySql.addSubPara(tClaimNo);    
    arr = easyExecSql( mySql.getString() );
    if (arr)
    {
        displayMultiline(arr,ClaimGrid);
    }
    else
    {
        initClaimGrid();
    }

    initPolDutyCodeGrid();
    //查询LLClaimDetail,查询保单理赔类型保项层面的信息
   /* tSql = " select a.PolNo,a.GetDutyKind,a.RiskCode,a.GetDutyCode, "
       +" (select c.GetDutyName from LMDutyGetRela c where c.GetDutyCode = a.GetDutyCode),"
       +" b.GetStartDate,b.GetEndDate,"
       +" nvl(a.GracePeriod,0)," //交至日期 + 宽限期--+ a.GracePeriod
       +" a.Amnt,a.YearBonus,a.EndBonus,"
       +" a.StandPay,a.GiveType, "
       +" (select e.codename from ldcode e where e.codetype = 'llpayconclusion' and e.code=a.GiveType),"
       +" a.GiveReason,"
       +" (select f.codename from ldcode f where f.codetype = 'llprotestreason' and f.code=a.GiveReason),"
       +" a.GiveReasonDesc,a.SpecialRemark,"
       +" a.PrepaySum ,"//预付金额
       +" '',"
       +" a.RealPay,a.AdjReason,"
       +" (select g.codename from ldcode g where g.codetype = 'lldutyadjreason' and g.code=a.AdjReason),"
       +" a.AdjRemark, "
       +" a.PrepayFlag,case a.PrepayFlag when '0' then '无预付' when '1' then '有预付' end,"
       +" case a.PolSort when 'A' then '承保前' when 'B' then '过期' when 'C' then '当期' end ,"
       +" a.DutyCode,a.CustomerNo,a.GrpContNo,a.ContNo,"
       +" (select name from ldperson where customerno=a.CustomerNo) "
       +" from LLClaimDetail a,LCGet b  where 1=1 "
       +" and a.PolNo = b.PolNo"
       +" and a.DutyCode = b.DutyCode"
       +" and a.GetDutyCode = b.GetDutyCode"
       +" and a.ClmNo = '"+tClaimNo+"'"
       +" and a.GiveType not in ('2')"
       +" and a.CustomerNo = '"+tCustNo+"'"*/
     mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpSimpleAllInputSql");
	mySql.setSqlId("LLGrpSimpleAllSql11"); 
	mySql.addSubPara(tClaimNo);    
	mySql.addSubPara(tCustNo);    
    arr = easyExecSql( mySql.getString() );
    if (arr)
    {
        displayMultiline(arr,PolDutyCodeGrid);
    }

    //查询LLFeeMain和LLCaseReceipt，显示门诊/住院信息
    
     /* tSql = "select a.customerno, a.FeeType,(select hospitalname from LLCommendHospital where hospitalcode=a.hospitalcode), a.HospitalCode, "
         +" a.HospitalGrade,a.MainFeeNo, (select icdname from lddisease where icdcode=b.diseasecode),b.diseasecode,"
         +" b.startdate,b.EndDate,decode(a.FeeType,'A','',b.DayCount),(select codename from ldcode where codetype = 'llfeeitemtype' and Code = b.FeeItemCode),b.FeeItemCode,b.Fee,"
         +" b.RefuseAmnt,(select codename from ldcode where codetype = 'deductreason' and Code = b.AdjReason),b.AdjReason,b.AdjSum,b.SecurityAmnt,b.HospLineAmnt,b.AdjRemark,b.FeeDetailNo "
         +" from llfeemain a, LLCaseReceipt b,LLCase c "
         +" where a.clmno = '"+tClaimNo+"' and a.clmno = b.clmno and a.clmno=c.caseno and b.customerno=c.customerno"
         +" and a.customerno = b.customerno and a.mainfeeno = b.mainfeeno order by lpad(c.seconduwflag,4,'0')";*/
         //+" and a.hospitalcode = c.hospitalcode  and trim(b.diseasecode) = trim(d.icdcode) ";   
    mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpSimpleAllInputSql");
	mySql.setSqlId("LLGrpSimpleAllSql12"); 
	mySql.addSubPara(tClaimNo);        
    turnPage2.queryModal(mySql.getString(),MedFeeInHosInpGrid);

}


//保单查询
//按照”客户号“在LCpol中进行查询，显示该客户的保单明细
function showInsuredLCPol()
{
    var row = SubReportGrid.getSelNo();
    if(row < 1)
    {
        alert("请选中一行记录！");
        return;
    }
  var tCustomerNo = SubReportGrid.getRowColData(row-1,1);
    var strUrl="LLLcContQueryMain.jsp?AppntNo="+tCustomerNo;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//既往赔案查询
function showOldInsuredCase()
{
    var row = SubReportGrid.getSelNo();
    if(row < 1)
    {
        alert("请选中一行记录！");
        return;
    }
  var tCustomerNo = SubReportGrid.getRowColData(row-1,1);
    var strUrl="LLLClaimQueryMain.jsp?AppntNo="+tCustomerNo;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//计算医疗单证信息
function showLLMedicalFeeCal()
{
    var tClaimNo = fm.RptNo.value;         //赔案号
    var tCaseNo = fm.RptNo.value;          //分案号
    var tCustNo = fm.customerNo.value;     //客户号

    var strUrl="LLClaimRegMedFeeCalMain.jsp?claimNo="+tClaimNo+"&caseNo="+tCaseNo+"&custNo="+tCustNo;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}


//数据提交
function submitForm()
{
    //提交数据
    var i = 0;
    var showStr = "正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
//    showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
    var name='提示';   //网页名称，可为空; 
    var iWidth=550;      //弹出窗口的宽度; 
    var iHeight=250;     //弹出窗口的高度; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

    showInfo.focus();

    fm.submit(); //提交
    tSaveFlag ="0";
}

//匹配后的动作
function afterMatchDutyPay(FlagStr, content)
{
    showInfo.close();
    if (FlagStr == "FAIL" )
    {
        var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
//        showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
        var name='提示';   //网页名称，可为空; 
        var iWidth=550;      //弹出窗口的宽度; 
        var iHeight=350;     //弹出窗口的高度; 
        var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
        var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
        showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

        showInfo.focus();

        mOperate = '';
    }
    else
    {
        var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
//        showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
        var name='提示';   //网页名称，可为空; 
        var iWidth=550;      //弹出窗口的宽度; 
        var iHeight=350;     //弹出窗口的高度; 
        var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
        var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
        showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

        showInfo.focus();

    }
    afterMatchDutyPayQuery();

}

//返回按钮
function goToBack()
{
    var tFlag = fm.Flag.value;
    if(tFlag == "FROMSIMALL")
    {
       location.href="LLGrpClaimSimpleAllInput.jsp";
    }
    else if(tFlag == '2')
    {
        location.href="LLGrpClaimConfirmAllInput.jsp";
    }
    else
    {
        location.href="LLGrpClaimSimpleInput.jsp";
    }
}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
    showInfo.close();
    if (FlagStr == "Fail")
    {
        var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
//        showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
        var name='提示';   //网页名称，可为空; 
        var iWidth=550;      //弹出窗口的宽度; 
        var iHeight=350;     //弹出窗口的高度; 
        var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
        var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
        showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

        showInfo.focus();

    }
    else
    {
        var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
//        showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
        var name='提示';   //网页名称，可为空; 
        var iWidth=550;      //弹出窗口的宽度; 
        var iHeight=350;     //弹出窗口的高度; 
        var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
        var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
        showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

        showInfo.focus();

        goToBack();
    }
    if(fm.SimpleConclusion2.value != '1')
    {
      goToBack();
    }    
    queryRegister();
    tSaveFlag ="0";
}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit2( FlagStr, content )
{
    showInfo.close();
    if (FlagStr == "Fail")
    {
        var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
//        showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
        var name='提示';   //网页名称，可为空; 
        var iWidth=550;      //弹出窗口的宽度; 
        var iHeight=350;     //弹出窗口的高度; 
        var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
        var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
        showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

        showInfo.focus();

    }
    else
    {
        var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
//        showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
        var name='提示';   //网页名称，可为空; 
        var iWidth=550;      //弹出窗口的宽度; 
        var iHeight=350;     //弹出窗口的高度; 
        var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
        var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
        showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

        showInfo.focus();

        goToBack();
  //      queryRegister();
        alert("2");
    }
    tSaveFlag ="0";
}

//提交后操作,不返回
function afterSubmit3( FlagStr, content )
{
    showInfo.close();
    if (FlagStr == "Fail" )
    {
        var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
//        showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
        var name='提示';   //网页名称，可为空; 
        var iWidth=550;      //弹出窗口的宽度; 
        var iHeight=350;     //弹出窗口的高度; 
        var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
        var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
        showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

        showInfo.focus();

    }
    else
    {
        var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
//        showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
        var name='提示';   //网页名称，可为空; 
        var iWidth=550;      //弹出窗口的宽度; 
        var iHeight=350;     //弹出窗口的高度; 
        var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
        var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
        showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

        showInfo.focus();

    }
      queryRegister();
      afterMatchDutyPayQuery();
      tSaveFlag ="0";
}


//出险人查询
function ClientQuery()
{
    window.open("LLLdPersonQueryInput.jsp","",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
//    window.open("LLLdPersonQueryInput.jsp");

}

//得到0级icd10码
function saveIcdValue()
{
    fm.ICDCode.value = fm.AccResult1.value;
}


//团体简易案件
function confirmClick()
{
  //检查非空
  if (fm.Flag.value != '2' && (fm.SimpleConclusion.value == "" || fm.SimpleConclusion.value == null))
  {
      alert("请填写简易案件结论！");
      return;
  }
  //检查非空
  if (fm.Flag.value == '2'&&(fm.SimpleConclusion2.value == "" || fm.SimpleConclusion2.value == null))
  {
      alert("请填写简易案件复核结论！");
      return;
  }
  //匹配并理算判断
  var i = PolDutyCodeGrid.mulLineCount;
  if(i == '0')
  {
      alert("请先匹配并理算！");
      return;
  }

   //理赔人员权限判断
    if (fm.Flag.value == '2')
    {
    	//var tclmSql="select clmstate from llclaim where clmno='"+fm.RptNo.value+"' ";
    	mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpSimpleAllInputSql");
	mySql.setSqlId("LLGrpSimpleAllSql13"); 
	mySql.addSubPara(fm.RptNo.value); 
    	var ttclmstate=easyExecSql(mySql.getString());
    	if (ttclmstate=="60"||ttclmstate=="70"||ttclmstate=="80")
    	{
    		alert("该赔案已经结案！");    	  
    	  return;    	  
    	}
    	
    fm.simpleClaim2.disabled=true;
    var showStr = "正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
//    showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");    
    var name='提示';   //网页名称，可为空; 
    var iWidth=550;      //弹出窗口的宽度; 
    var iHeight=250;     //弹出窗口的高度; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

    showInfo.focus();

    	
    if(fm.SimpleConclusion2.value == '0'){
    var tClaimType1 ;
    var tClaimType2 ;
    var tRealpay ;
//01.查询该赔案的最大理赔值

     //var csql="select customerno from llcase where caseno='"+fm.RptNo.value+"' ";
     mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpSimpleAllInputSql");
	mySql.setSqlId("LLGrpSimpleAllSql14"); 
	mySql.addSubPara(fm.RptNo.value); 
     var tcustomerno=new Array();
     tcustomerno=easyExecSql(mySql.getString());
     for (var i=0;i<tcustomerno.length;i++)
     {
          /*var strSql01 = " select realpay, insuredno from llclaimpolicy "
                       + " where clmno = '"+fm.RptNo.value+"' and realpay = (select max(realpay) from llclaimpolicy "
                       + " where clmno = '"+fm.RptNo.value+"' )"   */
        /* var strSql00 = " select sum(realpay) from llclaimpolicy "
                       + " where clmno = '"+fm.RptNo.value+"' and substr(getdutykind,2,3) in ('01','02','04') and insuredno='"+tcustomerno[i]+"'";                  
       */  mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpSimpleAllInputSql");
	mySql.setSqlId("LLGrpSimpleAllSql15"); 
	mySql.addSubPara(fm.RptNo.value); 
	mySql.addSubPara(tcustomerno[i]  );            
         var tSubReport = new Array;
         tSubReport = easyExecSql(mySql.getString());
         var  tRealpay1 = tSubReport[0][0];
         //alert ("寿险赔付:"+tRealpay1);
        // var tInsuredno = tSubReport[0][1];
        
             if(tRealpay1 == '' || tRealpay1 == null)
             {
               //  alert("未查询到该赔案的赔付金额！");
               //  return;
                 tRealpay1=0;  
             }
           else
           	{
           	  tClaimType1='10';	
           	}
           	
        /* var strSql01 = " select sum(realpay) from llclaimpolicy "
                       + " where clmno = '"+fm.RptNo.value+"' and substr(getdutykind,2,3) not in ('01','02','04') and insuredno='"+tcustomerno[i]+"'"; */                 
         mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpSimpleAllInputSql");
	mySql.setSqlId("LLGrpSimpleAllSql16"); 
	mySql.addSubPara(fm.RptNo.value); 
	mySql.addSubPara(tcustomerno[i]  );  
         var tSubReport1 = new Array;
         tSubReport1 = easyExecSql(mySql.getString());
         var   tRealpay2 = tSubReport1[0][0];
        //alert ("健康险赔付:"+tRealpay2);
        // var tInsuredno2 = tSubReport[0][1];
           if(tRealpay2==null || tRealpay2 == "")
             {
        //         alert("未查询到该赔案的赔付金额！");
        //         return;
                tRealpay2=0;           
             }
           else
           	{
           	   	tClaimType2='30';
           	}      	
           	
        /*//02.查询该最大理赔值的客户的出险类型
             var strSql02 = "select reasoncode from LLAppClaimReason where caseno = '"+fm.RptNo.value+"' and customerno = '"+tInsuredno+"'";
             var tSubReport2 = new Array;
             tSubReport2 = easyExecSql(strSql02);
               if(tSubReport2 == null ){
                    alert("未查询到该赔案的出险类型！");
                    return;
                   }
             for (var i= 0;i < tSubReport2.length ; i++ )
             {
                 var tReasonCode = tSubReport2[i][0].substring(1,3);
                 if(tReasonCode == 01 || tReasonCode == 02 || tReasonCode == 04){
                      tClaimType = 10;//寿险
                      break;
                     }else{
                      tClaimType = 30;//健康险
                     }
             }*/
             
        //03.1查询理赔人员的相关类型的理赔权限
        //0301.1查询复核人员的理赔权限
            //   var strSql0301 = "select b.basemax from llclaimuser a,LLClaimPopedom b where a.checklevel = b.popedomlevel and UserCode = '"+ document.all('tOperator').value +"' and b.claimtype = '"+tClaimType1+"' and a.StateFlag = '1'"
                mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpSimpleAllInputSql");
	mySql.setSqlId("LLGrpSimpleAllSql17"); 
	mySql.addSubPara(document.all('tOperator').valuee); 
	mySql.addSubPara(tClaimType1);
                var tBasemax0301 = easyExecSql(mySql.getString());
                if (tBasemax0301 == null || tBasemax0301 == "")
                {
        //            alert("未查询到您的理赔权限！");
        //            return;
                      tBasemax0301 = 0; //为查询到的默认为0
                }
        //0302.1查询立案人员的理赔权限
              //  var strSql0302 = "select b.basemax from llclaimuser a,LLClaimPopedom b where a.checklevel = b.popedomlevel and UserCode = '"+ document.all('Operator').value +"' and b.claimtype = '"+tClaimType1+"' and a.StateFlag = '1'"
                mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpSimpleAllInputSql");
	mySql.setSqlId("LLGrpSimpleAllSql18"); 
	mySql.addSubPara(document.all('tOperator').valuee); 
	mySql.addSubPara(tClaimType1);
                var tBasemax0302 = easyExecSql(mySql.getString());
                if (tBasemax0302 == null || tBasemax0302 == "")
                {
        //            alert("未查询到您的理赔权限！");
        //            return;
                      tBasemax0302 = 0; //为查询到的默认为0
                }
        //0303.1立案人员和审核人员权限判断
                tBasemax0301 = tBasemax0301*1;
                tBasemax0302 = tBasemax0302*1;
                if(tBasemax0301 > tBasemax0302){
                  tBasemax1 = tBasemax0301;
                }else{
                  tBasemax1 = tBasemax0302;
                }
        
        //03.2查询理赔人员的相关类型的理赔权限 
        //0301.2查询复核人员的理赔权限        
                //var strSql03011 = "select b.basemax from llclaimuser a,LLClaimPopedom b where a.checklevel = b.popedomlevel and UserCode = '"+ document.all('tOperator').value +"' and b.claimtype = '"+tClaimType2+"' and a.StateFlag = '1'"
               mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpSimpleAllInputSql");
	mySql.setSqlId("LLGrpSimpleAllSql19"); 
	mySql.addSubPara(document.all('tOperator').valuee); 
	mySql.addSubPara(tClaimType2);
                var tBasemax03011 = easyExecSql(mySql.getString());
                if (tBasemax03011 == null || tBasemax03011 == "")
                {
        //            alert("未查询到您的理赔权限！");
        //            return;
                      tBasemax03011 = 0; //为查询到的默认为0
                }
        //0302.2查询立案人员的理赔权限
             //   var strSql03022 = "select b.basemax from llclaimuser a,LLClaimPopedom b where a.checklevel = b.popedomlevel and UserCode = '"+ document.all('Operator').value +"' and b.claimtype = '"+tClaimType2+"' and a.StateFlag = '1'"
               mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpSimpleAllInputSql");
	mySql.setSqlId("LLGrpSimpleAllSql20"); 
	mySql.addSubPara(document.all('tOperator').valuee); 
	mySql.addSubPara(tClaimType2);
                var tBasemax03022 = easyExecSql(mySql.getString());
                if (tBasemax03022== null || tBasemax03022 == "")
                {
        //            alert("未查询到您的理赔权限！");
        //            return;
                      tBasemax03022 = 0; //为查询到的默认为0
                }
        //0303.2立案人员和审核人员权限判断
                tBasemax03011 = tBasemax03011*1;
                tBasemax03022 = tBasemax03022*1;
                if(tBasemax03011 > tBasemax03022){
                  tBasemax2 = tBasemax03011;
                }else{
                  tBasemax2 = tBasemax03022;
                }
        
        //04.权限判断
                tBasemax1 = tBasemax1*1;
                tBasemax2 = tBasemax2*1;
        
              		tRealpay1 = tRealpay1*1;
              		tRealpay2 = tRealpay2*1;
              		
              	//alert ("寿险最高赔付:"+tBasemax1);
                          
                //alert ("健康险最高赔付:"+tBasemax2);
              		
                if(tRealpay1 > tBasemax1 ||tRealpay2 > tBasemax2)
                {
                    showInfo.close();
                    fm.simpleClaim2.disabled=false;                    
                    alert("您的权限不足！请上报总公司处理！");
                    return;
                }     	
             }
             

   }if(fm.SimpleConclusion2.value == '1')
      {
    	  var mngcom = new Array;
    	 // var tsql="select mngcom from llregister where rgtno='"+fm.RptNo.value+"' ";    	  
    	   mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpSimpleAllInputSql");
			mySql.setSqlId("LLGrpSimpleAllSql21"); 
			mySql.addSubPara(fm.RptNo.value); 
    	  mngcom = easyExecSql(mySql.getString());   	      	  
    	      	  
    	  if (fm.AllManageCom.value!=mngcom)
    	  {
           showInfo.close();
           fm.simpleClaim2.disabled=false;    	  	
    	     alert ("请登录立案机构 "+mngcom+" 进行回退操作！");	
    	     return;
    	  }         
      }
      	
    }

  fm.action="./LLGrpClaimSimpleSave.jsp"
  fm.fmtransact.value = "";
 if (fm.Flag.value == '2')
  {    
    showInfo.close();
  }
  
  submitForm();
}

//检查保项金额调整,只能调小
function checkAdjMoney()
{
    var i = PolDutyCodeGrid.getSelNo();
    if (i != '0')
    {
        i = i - 1;
        var tContNo   = parseFloat(PolDutyCodeGrid.getRowColData(i,31));//个单合同号
        var tRiskCode = parseFloat(PolDutyCodeGrid.getRowColData(i,3));//险种编码
        var tDutyCode = parseFloat(PolDutyCodeGrid.getRowColData(i,28));//责任编码
        var tRealM    = parseFloat(PolDutyCodeGrid.getRowColData(i,21));//调整金额
        var tAmnt     = parseFloat(PolDutyCodeGrid.getRowColData(i,9));//保额
        var tGrpContNo= parseFloat(PolDutyCodeGrid.getRowColData(i,30));//团体合同号
        var tGetDutyKind = parseFloat(PolDutyCodeGrid.getRowColData(i,2));
        var tGetDutyCode = parseFloat(PolDutyCodeGrid.getRowColData(i,4));
        var tAdjM     = parseFloat(fm.RealPay.value);
        var tContPlanCode = "0";
        var tDeadTopvalFlag = "";

       //判断津贴型不做限制
      /* var strSQL4 = " select deadtopvalueflag from lmdutygetclm where "
                   + " getdutycode='"+tGetDutyCode+"' and getdutykind='"+tGetDutyKind+"'"*/
       mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpSimpleAllInputSql");
			mySql.setSqlId("LLGrpSimpleAllSql22"); 
			mySql.addSubPara(tGetDutyCode); 
			mySql.addSubPara(tGetDutyKind); 
       var arr4= easyExecSql(mySql.getString());
          if(arr4 != null){
          tDeadTopvalFlag = arr4[0][0];
             }

        if(tRiskCode == '140' || tDeadTopvalFlag == 'N')
        {
          /*
         var strSQL3 = " select contplancode from lcinsured  where contno = '"+tContNo+"'"
         var arr3= easyExecSql(strSQL3);
            if(arr3 != null){
            tContPlanCode = arr3[0][0];
               }
         var strSQL = "select a.TotalLimit from lldutyctrl a"
                    + " where trim(a.ContType) = '2' and trim(a.ContNo) = '"+tGrpContNo+"'"
                    + " and trim(a.DutyCode) = '"+tDutyCode+"' and trim(a.RiskCode) = '"+tRiskCode+"'"
                    + " and trim(a.ContPlanCode) = '"+tContPlanCode+"'";
         var arr= easyExecSql(strSQL);
            if(arr != null){
            tAmnt = arr[0][0];
               }else{
         var strSQL2 = "select a.TotalLimit from lldutyctrl a"
                    + " where trim(a.ContType) = '1' and trim(a.ContNo) = '"+tContNo+"'"
                    + " and trim(a.DutyCode) = '"+tDutyCode+"' and trim(a.RiskCode) = '"+tRiskCode+"'"
                    + " and trim(a.ContPlanCode) = '"+tContPlanCode+"'";
         var arr2= easyExecSql(strSQL2);
             if(arr2 != null)
             {
                tAmnt = arr2[0][0];
             }
             else{
               alert("未查询到140险种的赔付限额！");
               fm.RealPay.value = 0;
               return;
                  }
               }
               */
               tAmnt=1000000;
            }
        if (tAmnt < tAdjM)
        {
            alert("调整金额不能大于保额!");
            fm.RealPay.value = PolDutyCodeGrid.getRowColData(i,12);
            return;
        } else if(tAdjM < 0){
            alert("调整金额不能小于零!");
            fm.RealPay.value = PolDutyCodeGrid.getRowColData(i,12);
            return;
        }
    }
}


//出险人查询
function showInsPerQuery()
{
    var strUrl = "LLGrpLdPersonQueryMain.jsp?GrpContNo="+fm.GrpContNo.value+"&GrpCustomerNo="+fm.GrpCustomerNo.value+"&GrpName="+fm.GrpName.value+"&ManageCom="+fm.AllManageCom.value;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//[保存]按钮对应操作
function saveClick()
{
    //首先进行非空字段检验
    if (beforeSubmit())
    {
        if (fm.RgtClass.value == '' || fm.RgtClass.value == null)
        {
            fm.RgtClass.value = "2";//团险
        }
        
        /*增加对出险人的立案情况进行判断，如果有未结案的案件存在，不允许新增
         *2006-03-06 P。D
         */
      //  var StrSQL = "select count(*) from llcase a, llregister b where a.caseno = b.rgtno and a.customerno = '"+fm.customerNo.value+"' and  b.clmstate not in ('60','80','50','70')";
        mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpSimpleAllInputSql");
			mySql.setSqlId("LLGrpSimpleAllSql23"); 
			mySql.addSubPara(fm.customerNo.value); 
        var arr= easyExecSql(mySql.getString());
        if(arr > 0){
           alert("该出险人有未结案件，请结案后在做新增！");
           return false;
        }
        /*================================================================
         * 修改原因：增加对做完生存给付（趸领）的被保险人的判断不允许理赔
         * 修 改 人：P.D
         * 修改日期：2006-8-16
         *=================================================================
         */
       /* var SqlPol = " select count(*) From lcpol where polstate = '6'"
                   + " and  insuredno = '"+fm.customerNo.value+"'"
                   + " and  grpcontno = '"+fm.GrpContNo.value+"'";*/
         mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpSimpleAllInputSql");
			mySql.setSqlId("LLGrpSimpleAllSql24"); 
			mySql.addSubPara(fm.customerNo.value);   
			mySql.addSubPara(fm.GrpContNo.value);           
        if(fm.Polno.value != ''){
           // SqlPol += " and riskcode = '"+fm.Polno.value+"'";
             mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpSimpleAllInputSql");
			mySql.setSqlId("LLGrpSimpleAllSql25"); 
			mySql.addSubPara(fm.customerNo.value);   
			mySql.addSubPara(fm.GrpContNo.value);
			mySql.addSubPara(fm.Polno.value);    
             }
        var tPolState = easyExecSql(mySql.getString());
        if(tPolState > 0){
           alert("该出险人已经做了生存给付，不允许做新增！");
           return false;
         }
         
        /*===================================================================
         * 修改原因：增加对出险人是否作过死亡理赔的判断，如作过不允许再作理赔
         * 修 改 人：JINSH
         * 修改日期：2007-05-17
         *===================================================================
         */
       /* var Polsqlflag = " select count(*) From lcpol where polstate = '7'"
                   + " and  insuredno = '"+fm.customerNo.value+"'"
                   + " and  grpcontno = '"+fm.GrpContNo.value+"'";*/
                   //alert(fm.customerNo.value);
         mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpSimpleAllInputSql");
			mySql.setSqlId("LLGrpSimpleAllSql26"); 
			mySql.addSubPara(fm.customerNo.value);   
			mySql.addSubPara(fm.GrpContNo.value);            
        var Polflag = easyExecSql(mySql.getString());
        if(Polflag > 0)
        {
           alert("该出险人"+fm.customerNo.value+"已经做了死亡理赔，不允许做新增！");
           return false;
        }
         
         
        /*================================================================
         * 修改原因：增加对长险保全的判断，保全未确认和退保的不允许理赔
         * 修 改 人：P.D
         * 修改日期：2006-8-14
         =================================================================
         */
       // var SqlBq = "select  count(*) from LPEdorItem where insuredno = '"+fm.customerNo.value+"' and edorstate != '0' and edortype = 'CT'";
         mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpSimpleAllInputSql");
			mySql.setSqlId("LLGrpSimpleAllSql27"); 
			mySql.addSubPara(fm.customerNo.value);   
			mySql.addSubPara(fm.GrpContNo.value);   
        var tEdorState = easyExecSql(mySql.getString());
       /* var sqlC = "select count(*) from lmriskapp where "
                 + "riskcode in (select riskcode From lcpol where "
                 + "grpcontno = '"+fm.GrpContNo.value+"' and insuredno = '"+fm.customerNo.value+"') "
                 + "and RiskPeriod = 'L'";*/
        mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpSimpleAllInputSql");
			mySql.setSqlId("LLGrpSimpleAllSql28"); 
			mySql.addSubPara(fm.GrpContNo.value);   
			mySql.addSubPara(fm.customerNo.value);   
			
        var tcount = easyExecSql(mySql.getString());
        if(tEdorState > 0 && tcount > 0 ){
           alert("该出险人有未确认的保全或已经退保，请确认后在做新增！");
           return false;
        }

/*
        //jixf add 20051213
        var strSQL = " select a.polno from LCPol a, LCGrpCont b where  a.CValidate<='"+fm.AccidentDate.value+"' and a.enddate>='"+fm.AccidentDate.value+"'";
        strSQL=strSQL+" and a.GrpContNo=b.GrpContNo and insuredno='"+fm.customerNo.value+"' and a.appflag in ('1','4')";
         if (fm.GrpCustomerNo.value!=null&&fm.GrpCustomerNo.value!='000000')
         {
         strSQL=strSQL+" and b.AppntNo='"+fm.GrpCustomerNo.value+"'";
         }
        if (fm.GrpContNo.value!=null)
        {
        strSQL=strSQL+" and b.GrpContNo='"+fm.GrpContNo.value+"'";
        }
        
         var arr= easyExecSql(strSQL);
         if ( arr == null )
         {
         alert("该保单未生效或这次出险日期不在该被保险人的保险期间内！");         
         return false;
         }
*/
         if(TempCustomer() == false && showDate() == false){
         alert("该保单未生效或这次出险日期不在该被保险人的保险期间内！");         
         return false;
          }
         
       /*  var strSQLBQ = " select a.polno from LPEdorItem a, LCGrpCont b where  a.EdorValidate>='"+fm.AccidentDate.value+"'";
            strSQLBQ=strSQLBQ+" and a.GrpContNo=b.GrpContNo and a.insuredno='"+fm.customerNo.value+"'";
            strSQLBQ=strSQLBQ+" and a.EdorType in ('AA','PT','IC','LC')　and EdorState='0'";*/
            mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpSimpleAllInputSql");
			mySql.setSqlId("LLGrpSimpleAllSql29"); 
			mySql.addSubPara(fm.AccidentDate.value);   
			mySql.addSubPara(fm.customerNo.value);   
             if (fm.GrpCustomerNo.value!=null)
             {
               //strSQLBQ=strSQLBQ+" and b.AppntNo='"+fm.GrpCustomerNo.value+"'";
               mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpSimpleAllInputSql");
			mySql.setSqlId("LLGrpSimpleAllSql30"); 
			mySql.addSubPara(fm.AccidentDate.value);   
			mySql.addSubPara(fm.customerNo.value);  
			mySql.addSubPara(fm.GrpCustomerNo.value);  
             }
            if (fm.GrpContNo.value!=null)
            {
              //strSQLBQ=strSQLBQ+" and b.GrpContNo='"+fm.GrpContNo.value+"'";
               mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpSimpleAllInputSql");
			mySql.setSqlId("LLGrpSimpleAllSql31"); 
			mySql.addSubPara(fm.AccidentDate.value);   
			mySql.addSubPara(fm.customerNo.value);  
			mySql.addSubPara(fm.GrpCustomerNo.value);  
			mySql.addSubPara(fm.GrpContNo.value); 
            }
            
             var arrbq= easyExecSql(mySql.getString());
             if ( arrbq != null )
             {
               alert("严重警告：该被保险人在出险日期之后做过可能更改保额的保全！");                      
             }


        //判断区分新增、保存立案、还是保存出险人
        if(fm.RptNo.value == ''){
         fm.isReportExist.value = false;
        }
        if (fm.isReportExist.value == "false")
        {
            fm.fmtransact.value = "insert||first";
        }
        else
        {
            fm.fmtransact.value = "insert||customer";
        }
        fm.action = './LLGrpRegisterSave.jsp';
        submitForm();
        
  
    
    }
}

//出险人信息修改
function updateClick()
{
    if (confirm("您确实想修改该记录吗？"))
    {
        if (beforeSubmit())
        {
            alert("修改信息后请重新匹配理算！");
            //jixf add 20051213
          /*  var strSQL = " select a.polno from LCPol a, LCGrpCont b where  a.CValidate<='"+fm.AccidentDate.value+"' and a.enddate>='"+fm.AccidentDate.value+"'";
            strSQL=strSQL+" and a.GrpContNo=b.GrpContNo and insuredno='"+fm.customerNo.value+"' and a.appflag in ('1','4')";*/
             mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpSimpleAllInputSql");
			mySql.setSqlId("LLGrpSimpleAllSql32"); 
			mySql.addSubPara(fm.AccidentDate.value);   
			mySql.addSubPara(fm.AccidentDate.value);   
			mySql.addSubPara(fm.customerNo.value);  
             if (fm.GrpCustomerNo.value!=null&&fm.GrpCustomerNo.value!='000000')
             {
             //strSQL=strSQL+" and b.AppntNo='"+fm.GrpCustomerNo.value+"'";
             mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpSimpleAllInputSql");
			mySql.setSqlId("LLGrpSimpleAllSql33"); 
			mySql.addSubPara(fm.AccidentDate.value);   
			mySql.addSubPara(fm.AccidentDate.value);   
			mySql.addSubPara(fm.customerNo.value);  
			mySql.addSubPara(fm.GrpCustomerNo.value);  
             }
            if (fm.GrpContNo.value!=null)
            {
            //strSQL=strSQL+" and b.GrpContNo='"+fm.GrpContNo.value+"'";
             mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpSimpleAllInputSql");
			mySql.setSqlId("LLGrpSimpleAllSql34"); 
			mySql.addSubPara(fm.AccidentDate.value);   
			mySql.addSubPara(fm.AccidentDate.value);   
			mySql.addSubPara(fm.customerNo.value);  
			mySql.addSubPara(fm.GrpCustomerNo.value);  
			mySql.addSubPara(fm.GrpContNo.value);  
            }
            
             var arr= easyExecSql(mySql.getString());
             if ( arr == null )
             {
             alert("该保单未生效或这次出险日期不在该被保险人的保险期间内！");         
             return false;
             }
             
            /* var strSQLBQ = " select a.polno from LPEdorItem a, LCGrpCont b where  a.EdorValidate>='"+fm.AccidentDate.value+"'";
            strSQLBQ=strSQLBQ+" and a.GrpContNo=b.GrpContNo and a.insuredno='"+fm.customerNo.value+"'";
            strSQLBQ=strSQLBQ+" and a.EdorType in ('AA','PT','IC','LC')　and EdorState='0'";*/
            mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpSimpleAllInputSql");
			mySql.setSqlId("LLGrpSimpleAllSql35"); 
			mySql.addSubPara(fm.AccidentDate.value);   
			mySql.addSubPara(fm.customerNo.value);  
			
             if (fm.GrpCustomerNo.value!=null)
             {
              // strSQLBQ=strSQLBQ+" and b.AppntNo='"+fm.GrpCustomerNo.value+"'";
               mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpSimpleAllInputSql");
			mySql.setSqlId("LLGrpSimpleAllSql36"); 
			mySql.addSubPara(fm.AccidentDate.value);   
			mySql.addSubPara(fm.customerNo.value); 
				mySql.addSubPara(fm.GrpCustomerNo.value); 
             }
            if (fm.GrpContNo.value!=null)
            {
             // strSQLBQ=strSQLBQ+" and b.GrpContNo='"+fm.GrpContNo.value+"'";
              mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpSimpleAllInputSql");
			mySql.setSqlId("LLGrpSimpleAllSql37"); 
			mySql.addSubPara(fm.AccidentDate.value);   
			mySql.addSubPara(fm.customerNo.value); 
				mySql.addSubPara(fm.GrpCustomerNo.value); 
				mySql.addSubPara(fm.GrpContNo.value); 
            }
            
             var arrbq= easyExecSql(mySql.getString());
             if ( arrbq != null )
             {
               alert("严重警告：该被保险人在出险日期之后做过可能更改保额的保全！");                      
             }
                       
            fm.fmtransact.value = "update";
            fm.action = './LLGrpRegisterSave.jsp';
            submitForm();
        }
    }
}

//团体信息查询
function ClientQuery2()
{
   var keycode = event.keyCode;
   if(keycode=="13" || keycode=="9")
   {
   if(document.all('GrpCustomerNo').value!=null && document.all('GrpCustomerNo').value!='' ||
      (document.all('GrpContNo').value!=null && document.all('GrpContNo').value!='') ||
      (document.all('GrpName').value!=null && document.all('GrpName').value!='') )
   {
      var arrResult = new Array();
     /* var strSQL="select  a.customerno, a.name, g.grpcontno,g.Peoples2, g.bankcode, g.bankaccno, accname, a.AddressNo  " +
      " from lcgrpcont g, LCGrpAppnt a " +
      " where a.grpcontno = g.grpcontno and g.appflag in ('1','4') "
      + getWherePart("g.AppntNo", "GrpCustomerNo" )
      + getWherePart( "g.GrpContNo","GrpContNo" );*/
       mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpSimpleAllInputSql");
			mySql.setSqlId("LLGrpSimpleAllSql38"); 
			mySql.addSubPara(fm.GrpCustomerNo.value);   
			mySql.addSubPara(fm.GrpContNo.value); 
      if(document.all('GrpName').value!=''){
        // strSQL += " and a.Name like '%%"+document.all('GrpName').value+"%%'";
          mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpSimpleAllInputSql");
			mySql.setSqlId("LLGrpSimpleAllSql39"); 
			mySql.addSubPara(fm.GrpCustomerNo.value);   
			mySql.addSubPara(fm.GrpContNo.value); 
			mySql.addSubPara(document.all('GrpName').value);   
			
      }
      //增加对机构的判断 登陆机构只能处理本机构的单子 总机构可以处理分机构的单子 2006-02-18 P.D
     // strSQL += " and g.managecom like '"+document.all('AllManageCom').value+"%%'";
       mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpSimpleAllInputSql");
			mySql.setSqlId("LLGrpSimpleAllSql40"); 
			mySql.addSubPara(fm.GrpCustomerNo.value);   
			mySql.addSubPara(fm.GrpContNo.value); 
			mySql.addSubPara(document.all('GrpName').value);   
			mySql.addSubPara(document.all('AllManageCom').value);   
      arrResult=easyExecSql(mySql.getString());
       if(arrResult != null)
       {
          if(arrResult.length==1)
          {
             try{document.all('GrpCustomerNo').value=arrResult[0][0]}catch(ex){alert(ex.message+"GrpCustomerNo")}
             try{document.all('GrpName').value=arrResult[0][1]}catch(ex){alert(ex.message+"GrpName")}
             try{document.all('GrpContNo').value=arrResult[0][2]}catch(ex){alert(ex.message+"GrpContNo")}
             try{document.all('Peoples').value=arrResult[0][3]}catch(ex){alert(ex.message+"Peoples")}
             if(document.all('Peoples').value == null ||
                document.all('Peoples').value == "0")
             {
                alert("投保人数为空！");
                return;
             }
          }
      else
      {
            var varSrc = "&CustomerNo=" + fm.GrpCustomerNo.value;
               varSrc += "&GrpContNo=" + fm.GrpContNo.value;
               varSrc += "&GrpName=" + fm.GrpName.value;
               showInfo = window.open("./FrameMainLCGrpQuery.jsp?Interface= LCGrpQuery.jsp"+varSrc,"",'width=800,height=550,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
      }
       }else{
      //取到处理机构代码 2006-02-18 P.D
     // var SQLEx = "select ExecuteCom From lcgeneral where grpcontno='"+document.all('GrpContNo').value+"'";
       mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpSimpleAllInputSql");
			mySql.setSqlId("LLGrpSimpleAllSql41"); 
			mySql.addSubPara(document.all('GrpContNo').value);   
      var tExecuteCom = new Array();
      tExecuteCom = easyExecSql(mySql.getString());
      for(var i = 0;i<tExecuteCom.length;i++){
      //判断登陆机构是否是保单指定机构
      if(tExecuteCom[i][0] == document.all('AllManageCom').value){
      var arrResult = new Array();
     /* var strSQL="select  a.customerno, a.name, g.grpcontno,g.Peoples2, g.bankcode, g.bankaccno, accname, a.AddressNo  " +
      " from lcgrpcont g, LCGrpAppnt a ,lcgeneral b " +
      " where a.grpcontno = g.grpcontno and g.grpcontno = b.grpcontno and g.appflag = '1' "
      + getWherePart("g.AppntNo", "GrpCustomerNo" )
      + getWherePart( "g.GrpContNo","GrpContNo" );*/
       mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpSimpleAllInputSql");
			mySql.setSqlId("LLGrpSimpleAllSql42"); 
			mySql.addSubPara(fm.GrpCustomerNo.value);  
			mySql.addSubPara(fm.GrpContNo.value);
			mySql.addSubPara(fm.tExecuteCom[i][0].value);   
      if(document.all('GrpName').value!=''){
        // strSQL += " and a.Name like '%%"+document.all('GrpName').value+"%%'";
         mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpSimpleAllInputSql");
			mySql.setSqlId("LLGrpSimpleAllSql43"); 
			mySql.addSubPara(fm.GrpCustomerNo.value);  
			mySql.addSubPara(fm.GrpContNo.value); 
			mySql.addSubPara(document.all('GrpName').value); 
			mySql.addSubPara(fm.tExecuteCom[i][0].value); 
      }
         //strSQL += " and b.ExecuteCom = '"+tExecuteCom[i][0]+"'";
      arrResult=easyExecSql(mySql.getString());

   if(arrResult != null){
          if(arrResult.length==1)
          {
             try{document.all('GrpCustomerNo').value=arrResult[0][0]}catch(ex){alert(ex.message+"GrpCustomerNo")}
             try{document.all('GrpName').value=arrResult[0][1]}catch(ex){alert(ex.message+"GrpName")}
             try{document.all('GrpContNo').value=arrResult[0][2]}catch(ex){alert(ex.message+"GrpContNo")}
             try{document.all('Peoples').value=arrResult[0][3]}catch(ex){alert(ex.message+"Peoples")}
             if(document.all('Peoples').value == null ||
                document.all('Peoples').value == "0")
             {
                alert("投保人数为空！");
                return;
             }
          }
      else
      {
            var varSrc = "&CustomerNo=" + fm.GrpCustomerNo.value;
               varSrc += "&GrpContNo=" + fm.GrpContNo.value;
               varSrc += "&GrpName=" + fm.GrpName.value;
               showInfo = window.open("./FrameMainLCGrpQuery.jsp?Interface= LCGrpQuery.jsp"+varSrc,"",'width=800,height=550,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');

      }
       } else {
         alert("没有符合条件的数据！");
         document.all('GrpCustomerNo').value = "";
         document.all('GrpName').value = "";
         document.all('GrpContNo').value = "";
         document.all('Peoples').value = "";
         return;
       }
         }
      }
     }
   }
   else
   {
      alert("请输入查询条件");
   }
   }
}
function afterLCGrpQuery(arrReturn)
{
     document.all('GrpCustomerNo').value=arrReturn[0][0];
     document.all('GrpName').value=arrReturn[0][1];
//     document.all('GrpContNo').value=arrReturn[0][2];
     document.all('Peoples').value=arrReturn[0][3];
}

//参数为出生年月,如1980-5-9
function getAge(birth)
{
    var oneYear = birth.substring(0,4);
    var age = mNowYear - oneYear;
    if (age <= 0)
    {
        age = 0
    }
    return age;
}


//提交前的校验、计算
function beforeSubmit()
{
    //获取表单域信息
    var RptNo = fm.RptNo.value;//赔案号
    var CustomerNo = fm.customerNo.value;//出险人编号
    var AccDate = fm.AccidentDate.value;//事故日期
    var AccReason = fm.occurReason.value;//出险原因
    var AccDate2 = fm.AccidentDate.value;//出险日期
//    var AccDesc = fm.accidentDetail.value;//出险细节
    var ClaimType = new Array;
    //理赔类型
    for(var j=0;j<fm.claimType.length;j++)
    {
        if(fm.claimType[j].checked == true)
        {
            ClaimType[j] = fm.claimType[j].value;
        }
    }
    //取得年月日信息
    var AccYear = AccDate.substring(0,4);
    var AccMonth = AccDate.substring(5,7);
    var AccDay = AccDate.substring(8,10);
    var AccYear2 = AccDate2.substring(0,4);
    var AccMonth2 = AccDate2.substring(5,7);
    var AccDay2 = AccDate2.substring(8,10);
    //----------------------------------------------------------BEG2005-7-12 16:45
    //增加申请人姓名和关系的非空校验
    //----------------------------------------------------------
    if (fm.GrpCustomerNo.value == "" || fm.GrpCustomerNo.value == null)
    {
        alert("请输入团体客户号！");
        return false;
    }
    if (fm.GrpName.value == "" || fm.GrpName.value == null)
    {
        alert("请输入单位名称！");
        return false;
    }
    if (fm.GrpContNo.value == "" || fm.GrpContNo.value == null)
    {
        alert("请输入团体保单号！");
        return false;
    }
/*
    var tAssigneeZip = fm.AssigneeZip.value;
    if (tAssigneeZip.length > 6)
    {
        alert("邮编错误！");
        return false;
    }
*/

    //2-1 检查出险日期
    if (AccDate2 == null || AccDate2 == '')
    {
        alert("出险日期不能为空！");
        return false;
    }
    else
    {
        if (AccYear2 > mNowYear)
        {
            alert("出险日期不能晚于当前日期");
            return false;
        }
        else if (AccYear2 == mNowYear)
        {
            if (AccMonth2 > mNowMonth)
            {

                alert("事故日期不能晚于当前日期");
                return false;
            }
            else if (AccMonth2 == mNowMonth && AccDay2 > mNowDay)
            {
                alert("出险日期不能晚于当前日期");
                return false;
            }
        }
    }
    //2-2 检查出险日期
    if (AccYear > AccYear2 )
    {
        alert("事故日期不能晚于出险日期");
        return false;
    }
    else if (AccYear == AccYear2)
    {
        if (AccMonth > AccMonth2)
        {

            alert("事故日期不能晚于出险日期");
            return false;
        }
        else if (AccMonth == AccMonth2 && AccDay > AccDay2)
        {
            alert("事故日期不能晚于出险日期");
            return false;
        }
    }
    //3 检查出险原因
    if (AccReason == null || AccReason == '')
    {
        alert("出险原因不能为空！");
        return false;
    }

    //5 检查理赔类型
    if (ClaimType == null || ClaimType == '')
    {
        alert("出险类型不能为空！");
        return false;
    }

    return true;
}

//预估金额信息录入
function operStandPayInfo()
{
     var varSrc ="";
     var CaseNo = fm.RptNo.value;
     pathStr="./StandPayInfoMain.jsp?CaseNo="+CaseNo;
     showInfo = OpenWindowNew(pathStr,"","middle");
}

//理算结果统计查询
function QuerydutySetInfo()
{
     var varSrc ="";
     var CaseNo = fm.RptNo.value;
     pathStr="./QuerydutySetInfoMain.jsp?CaseNo="+CaseNo;
     showInfo = OpenWindowNew(pathStr,"","middle");
}

//帐单信息录入
function StandPaySave()
{
    var row = MedFeeInHosInpGrid.mulLineCount-1;
    var i = 0;
    var arr = new Array();
    var tCustomerNo = '';
    var tClinicStartDate = '';
    var tClinicEndDate = '';
    var tClinicDayCount1 = '';
    var tFeeType = '';
    var numFlag = false;
  if(row < 0){
    alert("请您输入数据再保存");
    return false;
    }
  for(var m=0;m<=row;m++ )
  {
   var i = MedFeeInHosInpGrid.getChkNo(m);//得到焦点行
   if(i==true)
   {
    numFlag = true;
   fm.hideOperate.value = "INSERT";
   tCustomerNo = MedFeeInHosInpGrid.getRowColData(m ,1);//出险人号码
   tClinicStartDate = MedFeeInHosInpGrid.getRowColData(m ,9);//帐单开始日期
   tClinicEndDate = MedFeeInHosInpGrid.getRowColData(m ,10);//帐单结束日期
   tClinicDayCount1 = MedFeeInHosInpGrid.getRowColData(m ,11);//天数
   tFeeType = MedFeeInHosInpGrid.getRowColData(m ,2);//帐单种类
   tMainFeeNo = MedFeeInHosInpGrid.getRowColData(m ,6);//帐单号码
   tClinicMedFeeTypeName = MedFeeInHosInpGrid.getRowColData(m ,12);//费用名称
   tClinicMedFeeSum = MedFeeInHosInpGrid.getRowColData(m ,14);//原始费用

//出险人号码检验
  if(tCustomerNo=='')
  {
    alert("请您输入出险人号码");
    return false;
  }
//帐单种类
  if(tFeeType=='')
  {
    alert("请您输入帐单种类");
    return false;
  }
//帐单号码检验
  if(tMainFeeNo=='')
  {
    alert("请您输入帐单号码");
    return false;
  }
//帐单开始日期检验
  if(tClinicStartDate=='')
  {
    alert("请您输入开始日期");
    return false;
  }

if(tClinicStartDate != ''){
 if(!isDate(trim(tClinicStartDate)))
  {
    alert('第'+m+'行的帐单开始日期格式应为XXXX-XX-XX');
    return false;
  }
}

if(tClinicEndDate != ''){
 if(!isDate(trim(tClinicEndDate)))
  {
    alert('第'+m+'行的帐单结束日期格式应为XXXX-XX-XX');
    return false;
  }
}
//费用类型检验
  if(tFeeType == 'A'&& tClinicEndDate != '' )
  {
    alert("门诊类型不应有结束日期");
    return false;
  }
  if(tFeeType == 'B'&& tClinicEndDate == '' )
  {
    alert("住院类型结束日期不能为空");
    return false;
  }
   //日期检验
    if (dateDiff(tClinicStartDate,mCurrentDate,'D') < 0 || dateDiff(tClinicEndDate,mCurrentDate,'D') < 0)
    {
        alert("日期错误");
        return;
    }
    /*var date4 = dateDiff(fm.AccidentDate.value,tClinicStartDate,'D');
    if(date4 < 0)
    {
       alert("单证开始日期早于出险日期");
       return false;
    }*/
//费用名称检验
  if(tClinicMedFeeTypeName=='')
  {
    alert("请您输入费用名称");
    return false;
  }
//原始费用检验
  if(tClinicMedFeeSum=='')
  {
    alert("请您输入原始费用");
    return false;
  }
 }else
   {
      alert ("没有选择要保存的数据!")
      return false;  
   }
   
   
  }

    fm.fmtransact.value="INSERT||MAIN";
    var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
//    showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
    var name='提示';   //网页名称，可为空; 
    var iWidth=550;      //弹出窗口的宽度; 
    var iHeight=250;     //弹出窗口的高度; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

    showInfo.focus();

    fm.action ="./LLGrpMedicalFeeInpSave.jsp";
    fm.submit();
}
//删除帐单
function deleteFeeClick()
{
    var numFlag=false;
    var row = MedFeeInHosInpGrid.mulLineCount-1;
    for(var m=0;m<=row;m++)
    {
      var i = MedFeeInHosInpGrid.getChkNo(m);//得到焦点行
      if(i==true)
      {
         numFlag = true;
         var feeNo = MedFeeInHosInpGrid.getRowColData(m,20);
         if(feeNo=="")
          {
             alert("该明细未保存，可以直接删除！");
          }
      }
    }
     if(numFlag==false)
    {
        alert("请选择要删除的帐单");
        return false;
    }
    else
    {
          fm.hideOperate.value = "DELETE";
          fm.action ='./LLGrpMedicalFeeInpSave.jsp';
          submitForm();  
    }     
    
}
function getMedFeeInHosInpGrid()
{
    var i = MedFeeInHosInpGrid.getSelNo() - 1;//得到焦点行

    MedFeeInHosInpGrid.setRowColData(i,1,fm.customerNo.value);//出险人编码

}
//预估金额返回方法
function getGrpstandpay(){
}

function getin()
{

   var strUrl = "../claim/GrpCustomerDiskInput.jsp?grpcontno="+ fm.RptNo.value
   //showInfo=window.open(strUrl,"被保人清单导入","");
   showInfo=window.open(strUrl,"","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
}

/*－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
  * 修改原因：添加出险人信息删除操作
  * 修 改 人：万泽辉
  * 修改日期：2006-02-17
  －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
  */
function deleteClick()
{
    if (confirm("您确实想删除该记录吗？"))
    {
        alert("删除信息后请重新匹配理算！");
        fm.fmtransact.value = "DELETE";
        fm.action = './LLGrpRegisterSave.jsp';
        submitForm();
    }
}
/*－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
  * 修改原因：添加出险人信息删除后的返回提交
  * 修 改 人：万泽辉
  * 修改日期：2006-02-17
  －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
  */
function afterSubmit4( FlagStr, content )
{
    showInfo.close();
    if (FlagStr == "Fail")
    {
        var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
//        showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
        var name='提示';   //网页名称，可为空; 
        var iWidth=550;      //弹出窗口的宽度; 
        var iHeight=350;     //弹出窗口的高度; 
        var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
        var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
        showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

        showInfo.focus();

    }
    else
    {
        var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
//      showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
        var name='提示';   //网页名称，可为空; 
        var iWidth=550;      //弹出窗口的宽度; 
        var iHeight=350;     //弹出窗口的高度; 
        var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
        var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
        showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

        showInfo.focus();

        initForm();
    }
    queryRegister();
    tSaveFlag ="0";
}

//校验日期格式
function checkapplydate(){
  if(fm.AccidentDate.value.length==8){
  if(fm.AccidentDate.value.indexOf('-')==-1){ 
    var Year =     fm.AccidentDate.value.substring(0,4);
    var Month =    fm.AccidentDate.value.substring(4,6);
    var Day =      fm.AccidentDate.value.substring(6,8);
    fm.AccidentDate.value = Year+"-"+Month+"-"+Day;
    if(Year=="0000"||Month=="00"||Day=="00"){
        alert("您输入的日期有误!");
        fm.AccidentDate.value = ""; 
        return;
      }
  }
} else {var Year =     fm.AccidentDate.value.substring(0,4);
      var Month =    fm.AccidentDate.value.substring(5,7);
      var Day =      fm.AccidentDate.value.substring(8,10);
      if(Year=="0000"||Month=="00"||Day=="00"){
        alert("您输入的日期有误!");
        fm.AccidentDate.value = "";
        return;
         }
  }
}
//无名转有名保险期间判断
function TempCustomer(){
       // var StrSQL = "select grpcontno From lctempcustomer where startdate <= '"+fm.AccidentDate.value+"' and paytodate >= '"+fm.AccidentDate.value+"' and grpcontno = '"+fm.GrpContNo.value+"'";
         mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpSimpleAllInputSql");
			mySql.setSqlId("LLGrpSimpleAllSql44"); 
			mySql.addSubPara(fm.AccidentDate.value);  
			mySql.addSubPara(fm.AccidentDate.value);  
			mySql.addSubPara(fm.GrpContNo.value); 
        var arr= easyExecSql(mySql.getString());
        if(arr != null ){
        return true;
        }else{
        return false;
        }
}
//判断保险期间
function showDate(){
        //jixf add 20051213
      /*  var strSQL = " select a.polno from LCPol a, LCGrpCont b where  a.CValidate<='"+fm.AccidentDate.value+"' and a.enddate>='"+fm.AccidentDate.value+"'";
        strSQL=strSQL+" and a.GrpContNo=b.GrpContNo and insuredno='"+fm.customerNo.value+"' and a.appflag in ('1','4')";*/
        mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpSimpleAllInputSql");
			mySql.setSqlId("LLGrpSimpleAllSql45"); 
			mySql.addSubPara(fm.AccidentDate.value);  
			mySql.addSubPara(fm.AccidentDate.value);  
			mySql.addSubPara(fm.customerNo.value); 
         if (fm.GrpCustomerNo.value!=null&&fm.GrpCustomerNo.value!='000000')
         {
         //strSQL=strSQL+" and b.AppntNo='"+fm.GrpCustomerNo.value+"'";
          mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpSimpleAllInputSql");
			mySql.setSqlId("LLGrpSimpleAllSql46"); 
			mySql.addSubPara(fm.AccidentDate.value);  
			mySql.addSubPara(fm.AccidentDate.value);  
			mySql.addSubPara(fm.customerNo.value); 
			mySql.addSubPara(fm.GrpCustomerNo.value); 
         }
        if (fm.GrpContNo.value!=null)
        {
       // strSQL=strSQL+" and b.GrpContNo='"+fm.GrpContNo.value+"'";
         mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpSimpleAllInputSql");
			mySql.setSqlId("LLGrpSimpleAllSql47"); 
			mySql.addSubPara(fm.AccidentDate.value);  
			mySql.addSubPara(fm.AccidentDate.value);  
			mySql.addSubPara(fm.customerNo.value); 
			mySql.addSubPara(fm.GrpCustomerNo.value); 
				mySql.addSubPara(fm.GrpContNo.value); 
        }
        
         var arr= easyExecSql(mySql.getString());
        if(arr != null ){
        return true;
        }else{
        return false;
        }
}
