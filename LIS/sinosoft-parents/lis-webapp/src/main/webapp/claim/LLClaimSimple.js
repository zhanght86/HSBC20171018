var showInfo;
var turnPage = new turnPageClass();
var mySql = new SqlClass();
//发起二核
function showSecondUWInput()
{
	  var strUrl="SecondUWInput.jsp?CaseNo="+fm.RptNo.value+"&InsuredNo="+fm.customerNo.value;
    //window.open(strUrl,"发起二核");
    window.open(strUrl,"","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
}

//录入医疗单证信息
function showLLMedicalFeeInp()
{


    var tClaimNo = fm.RptNo.value;         //赔案号
    var tCaseNo = fm.RptNo.value;          //分案号
    var tCustNo = fm.customerNo.value;          //客户号

    var strUrl="LLMedicalFeeInpMain.jsp?claimNo="+tClaimNo+"&caseNo="+tCaseNo+"&custNo="+tCustNo+"&accDate="+fm.AccidentDate.value+"&medAccDate="+fm.MedicalAccidentDate.value+"&otherAccDate=";
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');

}


//医疗单证调整信息
function showMedicalAdjClick()
{
    var tClaimNo = fm.RptNo.value;         //赔案号
    var tCaseNo = fm.RptNo.value;          //分案号
    var tCustNo = fm.customerNo.value;          //客户号

    //var strUrl="LLColQueryMedicalFeeInpMain.jsp?claimNo="+tClaimNo+"&caseNo="+tCaseNo+"&custNo="+tCustNo+"&accDate="+fm.AccidentDate.value+"&medAccDate="+fm.MedicalAccidentDate.value+"&otherAccDate=";
    var strUrl="LLMedicalFeeInpMain.jsp?claimNo="+tClaimNo+"&caseNo="+tCaseNo+"&custNo="+tCustNo+"&accDate="+fm.AccidentDate.value+"&medAccDate="+fm.MedicalAccidentDate.value+"&otherAccDate="+fm.MedicalAccidentDate.value;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}


//由客户查询（LLLdPersonQuery.js）返回单条记录时调用
function afterQueryLL(arr)
{
    fm.customerNo.value = arr[0];
    fm.customerName.value = arr[1];
    fm.customerSex.value = arr[2];
    fm.customerAge.value = arr[3];
    
    //2008-11-25 去掉VIP标志的显示
//    fm.IsVip.value = arr[4];
    //if(arr[4]==null || arr[4]=="" || arr[4]==0)
    //{
     	//fm.VIPValueName.value ="否";
     	//fm.IsVip.value = "0";
    //}
    //else
    //{
     	//fm.IsVip.value = arr[4];
     	//fm.VIPValueName.value ="是";
    //}
    showOneCodeName('sex','customerSex','SexName');//性别
    //初始化录入域
    fm.hospital.value = "";
    fm.TreatAreaName.value = "";
    fm.MedicalAccidentDate.value = "";
    //fm.OtherAccidentDate.value = "";
    fm.accidentDetail.value = "";
//    fm.IsDead.value = "";
    fm.cureDesc.value = "";
//    fm.Remark.value = "";
    for(var j=0;j<fm.claimType.length;j++)
    {
    	  fm.claimType[j].checked = false;
    }
}

//立案查询
function queryRegister()
{
    var rptNo = fm.ClmNo.value;
    if(rptNo == "")
    {
        alert("传入赔案为空！");
        return;
    }
    //检索事件号、意外事故发生日期(一条)
    /*var strSQL1 = "select LLCaseRela.CaseRelaNo,LLAccident.AccDate from LLCaseRela,LLAccident where LLCaseRela.CaseRelaNo = LLAccident.AccNo and "
                + "LLCaseRela.CaseNo = '"+rptNo+"'";*/
	mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimSimpleInputSql");
	mySql.setSqlId("LLClaimSimpleSql1");
	mySql.addSubPara( rptNo );                
//    alert("strSQL1= "+strSQL1);
    var AccNo = easyExecSql(mySql.getString());
//    alert("AccNo= "+AccNo);
    //检索立案号及其他立案信息(一条)
    //----------------------1------2-----------3-----------4------------5--------6------------7-------8--------9-----------10---------11--------12-----------13--------------14---------15-----------16------------17------------18---------19
    /*var strSQL2 = "select rgtno,rgtantname,rgtantphone,rgtantaddress,relation,accidentsite,rgtdate,(select username from llclaimuser where usercode = llregister.Operator),mngcom,(case assigneetype when '0' then '业务员' when '1' then '客户' end),assigneecode,assigneename,(case assigneesex when '0' then '男' when '1' then '女' when '2' then '未知' end),assigneephone,assigneeaddr,assigneezip,accidentreason,RgtConclusion,RgtState,RgtClass,(case RgtClass when '1' then '个人' when '2' then '团体' when '3' then '家庭' end),accepteddate,ApplyDate,Rgtantmobile,postcode from llregister where "
                + "rgtno = '"+rptNo+"'";*/
	mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimSimpleInputSql");
	mySql.setSqlId("LLClaimSimpleSql2");
	mySql.addSubPara( rptNo );  
	                
//    alert("strSQL2= "+strSQL2);
    var RptContent = easyExecSql(mySql.getString());
//    alert("RptContent= "+RptContent);
    //更新页面内容
    fm.AccNo.value = AccNo[0][0];
    fm.AccidentDate.value = AccNo[0][1];

    fm.RptNo.value = RptContent[0][0];
    fm.RptorName.value = RptContent[0][1];
    fm.RptorPhone.value = RptContent[0][2];
    fm.RptorAddress.value = RptContent[0][3];
    fm.Relation.value = RptContent[0][4];
    fm.AccidentSite.value = RptContent[0][5];
    fm.RptDate.value = RptContent[0][6];
    fm.Operator.value = RptContent[0][7];
    fm.MngCom.value = RptContent[0][8];
    fm.AssigneeType.value = RptContent[0][9];
    fm.AssigneeCode.value = RptContent[0][10];
    fm.AssigneeName.value = RptContent[0][11];
    fm.AssigneeSex.value = RptContent[0][12];
    fm.AssigneePhone.value = RptContent[0][13];
    fm.AssigneeAddr.value = RptContent[0][14];
    fm.AssigneeZip.value = RptContent[0][15];
    fm.occurReason.value = RptContent[0][16];
    fm.RgtConclusion.value = RptContent[0][17];
    fm.rgtType.value = RptContent[0][18];
    fm.RgtClass.value = RptContent[0][19];
    fm.RgtClassName.value = RptContent[0][20];
    fm.AcceptedDate.value = RptContent[0][21]; 
    fm.ApplyDate.value = RptContent[0][22];
    fm.RptorMoPhone.value = RptContent[0][23];
    fm.AppntZipCode.value = RptContent[0][24];
    
    showOneCodeName('llrgttype','rgtType','rgtTypeTypeName');
    showOneCodeName('llrgrelation','Relation','RelationName');//报案人与事故人关系
    showOneCodeName('lloccurreason','occurReason','ReasonName');//出险原因
    showOneCodeName('llrgtconclusion','RgtConclusion','RgtConclusionName');//立案结论

    //****************************************************
    //更新页面权限
    //****************************************************
    fm.AccidentDate.readonly = true;

//    fm.QueryPerson.disabled=true;
//    fm.QueryReport.disabled=true;

//    fm.occurReason.disabled=true;
//    fm.accidentDetail.disabled=true;
    fm.claimType.disabled=true;
    fm.Remark.disabled=true;

    //设置按钮++++++++++++++++++++++++++++++++++++++++待添加

    //检索分案关联出险人信息(多条)
    /*var strSQL3 = " select CustomerNo,Name, "
                         + " Sex,"
                         + " (case trim(Sex) when '0' then '男' when '1' then '女' when '2' then '不详' end) as SexNA,"
                         + " Birthday,"
                         + " nvl(SocialInsuFlag,0) as SocialInsuFlag,"
                         + " (case when trim(nvl(SocialInsuFlag, 0)) = '1' then '有' else '无' end) as 是否有社保标志 "
                         + " from LDPerson where "
                         + " CustomerNo in("
                         + " select Customerno from llcase where caseno = '"+ rptNo +"')";*/
 	mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimSimpleInputSql");
	mySql.setSqlId("LLClaimSimpleSql3");
	mySql.addSubPara( rptNo );  
	                        
//    alert("strSQL3= "+strSQL3);
    easyQueryVer3Modal(mySql.getString(), SubReportGrid);
    if (SubReportGrid.getRowColData(0,1) != null && SubReportGrid.getRowColData(0,1) != "")
    {
        SubReportGridClick(0);
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
	//fm.IsVip.value = "";
	fm.hospital.value = "";
	fm.TreatAreaName.value = "";
	fm.MedicalAccidentDate.value = "";
	//fm.OtherAccidentDate.value = "";
	fm.accidentDetail.value = "";
	fm.accidentDetailName.value = "";
//	fm.IsDead.value = "";
//	fm.IsDeadName.value = "";
	fm.claimType.value = "";
	fm.cureDesc.value = "";
	fm.cureDescName.value = "";
	fm.AccResult1.value = "";
	fm.AccResult1Name.value = "";
	fm.AccResult2.value = "";
	fm.AccResult2Name.value = "";
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
        fm.customerAge.value = calAge(SubReportGrid.getRowColData(i,5));
        //fm.IsVip.value = SubReportGrid.getRowColData(i,6);
        //fm.VIPValueName.value = SubReportGrid.getRowColData(i,7);
        showOneCodeName('sex','customerSex','SexName');//性别
    }

    //查询获得理赔类型
    var tClaimType = new Array;/*
    var strSQL1 = "select ReasonCode from LLAppClaimReason where "
                + " CaseNo = '"+fm.RptNo.value+"'"
                + " and CustomerNo = '"+fm.customerNo.value+"'";
    */            
 	mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimSimpleInputSql");
	mySql.setSqlId("LLClaimSimpleSql4");
	mySql.addSubPara(fm.RptNo.value );  
	mySql.addSubPara( fm.customerNo.value );  
	                
//    alert(tClaimType);
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
//        	  	  alert(tClaim);
        	  	  if(fm.claimType[j].value == tClaim)
        	  	  {
                	  fm.claimType[j].checked = true;
            	  }
            }
        }
    }
    //查询分案表信息
    var tSubReport = new Array;
    //--------------------------1----------2---------3-----------4---------5------6-----7-------------8----------9----------10---------11
    /*var strSQL2 = "select hospitalcode,MedAccDate,AccidentDetail,DieFlag,CureDesc,Remark,AccdentDesc,AccResult1,AccResult2,EditFlag,AffixConclusion,(case EditFlag when '0' then '否' when '1' then '是' end),(case AffixConclusion when '0' then '否' when '1' then '是' end),AccDate from LLCase where 1=1 "
                + getWherePart( 'CaseNo','RptNo' )
                + getWherePart( 'CustomerNo','customerNo' );*/
 	mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimSimpleInputSql");
	mySql.setSqlId("LLClaimSimpleSql5");
	mySql.addSubPara(fm.RptNo.value );  
	mySql.addSubPara( fm.customerNo.value );
	
//    alert(strSQL2);
    tSubReport = easyExecSql(mySql.getString());
//    alert(tSubReport);
    fm.hospital.value = tSubReport[0][0];
    fm.MedicalAccidentDate.value = tSubReport[0][1];
    fm.accidentDetail.value = tSubReport[0][2];
//    fm.IsDead.value = tSubReport[0][3];
    fm.cureDesc.value = tSubReport[0][4];
    fm.Remark.value = tSubReport[0][5];
    fm.AccDesc.value = tSubReport[0][6];
    fm.AccResult1.value = tSubReport[0][7];
    fm.AccResult2.value = tSubReport[0][8];
    fm.IsModify.value = tSubReport[0][9];
    fm.IsAllReady.value = tSubReport[0][10];
    fm.IsModifyName.value = tSubReport[0][11];
    fm.IsAllReadyName.value = tSubReport[0][12];
    //fm.OtherAccidentDate.value = tSubReport[0][13];
    showOneCodeName('commendhospital','hospital','TreatAreaName');//治疗医院
    showOneCodeName('llaccidentdetail','accidentDetail','accidentDetailName');//出险细节
//    showOneCodeName('llDieFlag','IsDead','IsDeadName');//死亡标识
    showOneCodeName('llCureDesc','cureDesc','cureDescName');//治疗情况
    showOneCodeName('lldiseases1','AccResult1','AccResult1Name');//出险结果1
    showOneCodeName('lldiseases2','AccResult2','AccResult2Name');//出险结果2
    queryResult('AccResult1','AccResult1Name');
    queryResult('AccResult2','AccResult2Name');
    queryHospital('hospital','TreatAreaName');
//    showOneCodeName('IsAllReady','IsModify','IsModifyName');//重要信息修改标志
//    showOneCodeName('IsAllReady','IsAllReady','IsAllReadyName');//单证齐全标志

}


//选中PolDutyCodeGrid响应事件
function PolDutyCodeGridClick()
{
    //清空表单
    fm.GiveType.value = "";//赔付结论
    fm.RealPay.value = "";
    fm.AdjReason.value = "";//调整原因
    fm.AdjReasonName.value = "";//
    fm.AdjRemark.value = "";//调整备注
    fm.GiveTypeDesc.value = "";//拒付原因代码
    fm.GiveReason.value = "";//拒付依据
    fm.SpecialRemark.value = "";//特殊备注
    //设置按钮
    fm.addUpdate.disabled = false;//添加修改
    //得到mulline信息
    var i = PolDutyCodeGrid.getSelNo();
    if (i != '0')
    {
        i = i - 1;
        fm.GiveType.value = PolDutyCodeGrid.getRowColData(i,14);//赔付结论
        fm.RealPay.value = PolDutyCodeGrid.getRowColData(i,22);
        fm.AdjReason.value = PolDutyCodeGrid.getRowColData(i,23);//调整原因
        fm.AdjReasonName.value = PolDutyCodeGrid.getRowColData(i,24);//
        fm.AdjRemark.value = PolDutyCodeGrid.getRowColData(i,25);//调整备注
        fm.GiveTypeDesc.value = PolDutyCodeGrid.getRowColData(i,16);//拒付原因代码
        fm.GiveReason.value = PolDutyCodeGrid.getRowColData(i,18);//拒付依据
        fm.SpecialRemark.value = PolDutyCodeGrid.getRowColData(i,19);//特殊备注
        //tongmeng 2011-02-23 modify
        //处理币种
        fm.Currency.value = PolDutyCodeGrid.getRowColData(i,9);//币种
        
        showOneCodeName('currency','Currency','CurrencyName');
        showOneCodeName('llpayconclusion','GiveType','GiveTypeName');
        showOneCodeName('llprotestreason','GiveTypeDesc','GiveTypeDescName');
    }
    //显示隐藏层
    divBaseUnit5.style.display= "";
    choiseGiveTypeType();
}

//对保项增加修改
function AddUpdate()
{
    if(PolDutyCodeGrid.getSelNo() <= 0)
    {
        alert("请先选择要处理的保项信息,再执行此操作！");
        return;
    }
    var i = PolDutyCodeGrid.getSelNo() - 1;//得到焦点行
    
    //拒付时,要填写拒付原因
    if (fm.GiveType.value == "1" && fm.GiveTypeDesc.value == "")//拒付
    {
        alert("请填写拒付原因！");
        return;
    }
    
    //调整金额时，调整原因不能为空 
    if(fm.GiveType.value == "1" && fm.GiveReason.value == "")
    {
    	alert("请填写拒付依据！");
    	return;
    }
    
    
    if(checkAdjMoney()==false)
    {
    	return false;
    }
    
    PolDutyCodeGrid.setRowColData(i,14,fm.GiveType.value);//赔付结论
    PolDutyCodeGrid.setRowColData(i,16,fm.GiveTypeDesc.value);//拒付原因代码
    PolDutyCodeGrid.setRowColData(i,18,fm.GiveReason.value);//拒付依据
    PolDutyCodeGrid.setRowColData(i,19,fm.SpecialRemark.value);//特殊备注
    PolDutyCodeGrid.setRowColData(i,22,fm.RealPay.value);
    PolDutyCodeGrid.setRowColData(i,23,fm.AdjReason.value);//调整原因
    PolDutyCodeGrid.setRowColData(i,24,fm.AdjReasonName.value);//
    PolDutyCodeGrid.setRowColData(i,25,fm.AdjRemark.value);//调整备注

    
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
	  if(!KillTwoWindows(fm.RptNo.value,'30'))
	  {
	  	  return false;
	  }	
    mOperate="MATCH||INSERT";
    var showStr="正在进行保险责任匹配操作，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    fm.hideOperate.value=mOperate;
    fm.action = "./LLClaimRegisterMatchCalSave.jsp";
    document.getElementById("fm").submit(); //提交
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
    /*tSql = " select a.StandPay ,a.BeforePay,a.BalancePay,a.RealPay,a.DeclinePay,"
       +" '','','' "
       +" from LLClaim a  where 1=1 "
       +" and a.ClmNo = '"+tClaimNo+"'"
       ;*/
    	mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimSimpleInputSql");
	mySql.setSqlId("LLClaimSimpleSql6");
	mySql.addSubPara(tClaimNo );  

	
    arr = easyExecSql( mySql.getString() );
    if (arr)
    {
        displayMultiline(arr,ClaimGrid);
    }
    else
    {
        initClaimGrid();
    }
    
    //查询LLClaimDutyKind，按照赔案理赔类型进行查找
   /* tSql = " select a.GetDutyKind ,"
       +" (select c.codename from ldcode c where c.codetype = 'llclaimtype' and trim(c.code)=trim(a.GetDutyKind)),"
       +" a.TabFeeMoney,a.SelfAmnt,a.ClaimMoney,a.StandPay,a.SocPay,a.OthPay,a.RealPay "
       +" from LLClaimDutyKind a  where 1=1 "
       +" and a.ClmNo = '"+tClaimNo+"'"
*/
	    	mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimSimpleInputSql");
	mySql.setSqlId("LLClaimSimpleSql7");
	mySql.addSubPara(tClaimNo ); 
	
    arr = easyExecSql( mySql.getString() );
    if (arr)
    {
        displayMultiline(arr,DutyKindGrid);
    }


    //查询LLClaimPolicy,查询保单理赔类型层面的信息
    /*tSql = " select a.ContNo,a.PolNo,a.GetDutyKind,"
       +" a.CValiDate,b.PaytoDate,"
       +" a.RiskCode,(select RiskName from LMRisk d where d.RiskCode = a.RiskCode), "
       +" a.RealPay "
       +" from LLClaimPolicy a ,LCPol b where 1=1 "
       +" and a.PolNo = b.PolNo"       
       +" and a.ClmNo = '"+tClaimNo+"'"*/
	mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimSimpleInputSql");
	mySql.setSqlId("LLClaimSimpleSql8");
	mySql.addSubPara(tClaimNo ); 
	
    arr = easyExecSql( mySql.getString() );
    if (arr)
    {
        displayMultiline(arr,PolDutyKindGrid);
    }


    //查询LLClaimDetail,查询保单理赔类型保项层面的信息
   /* tSql = " select a.PolNo,a.GetDutyKind,a.RiskCode,a.GetDutyCode, "
       +" (select c.GetDutyName from LMDutyGetRela c where trim(c.GetDutyCode) = trim(a.GetDutyCode)),"
       +" b.GetStartDate,b.GetEndDate,"
       +" nvl(a.GracePeriod,0)," //交至日期 + 宽限期--+ a.GracePeriod
       +" a.Amnt,a.YearBonus,a.EndBonus,"
       +" a.StandPay,a.GiveType, "
       +" (select e.codename from ldcode e where e.codetype = 'llpayconclusion' and trim(e.code)=trim(a.GiveType)),"
       +" a.GiveReason,"
       +" (select f.codename from ldcode f where f.codetype = 'llprotestreason' and trim(f.code)=trim(a.GiveReason)),"
       +" a.GiveReasonDesc,a.SpecialRemark,"
       +" a.PrepaySum ,"//预付金额
       +" '',"
       +" a.RealPay,a.AdjReason,"
       +" (select g.codename from ldcode g where g.codetype = 'lldutyadjreason' and trim(g.code)=trim(a.AdjReason)),"
       +" a.AdjRemark, "
       +" a.PrepayFlag,case a.PrepayFlag when '0' then '无预付' when '1' then '有预付' end,"
       +" case a.PolSort when 'A' then '承保前' when 'B' then '过期' when 'C' then '当期' end ,"
       +" a.DutyCode ,a.customerno"
       +" from LLClaimDetail a,LCGet b  where 1=1 "
       +" and a.PolNo = b.PolNo"
       +" and a.DutyCode = b.DutyCode"       
       +" and a.GetDutyCode = b.GetDutyCode"        
       +" and a.ClmNo = '"+tClaimNo+"'"
       +" and a.GiveType not in ('2')"*/
	mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimSimpleInputSql");
	mySql.setSqlId("LLClaimSimpleSql9");
	mySql.addSubPara(tClaimNo ); 
	
    //prompt("查询LLClaimDetail,查询保单理赔类型保项层面的信息",tSql);
    arr = easyExecSql( mySql.getString() );
    if (arr)
    {
        displayMultiline(arr,PolDutyCodeGrid);
    }
}

//受益人分配
function showBnf()
{
	  if(!KillTwoWindows(fm.RptNo.value,'30'))
	  {
	  	  return false;
	  }	
    /**=========================================================================BEG
        修改状态：
        修改原因：在受益人分配前进行医疗类理赔金额和保项层面汇总金额的校验
        修 改 人：续涛
        修改日期：2005.08.11
       =========================================================================**/
    var tSql ;
    var tClaimNo = fm.RptNo.value;
    
    //查询医疗类理赔类型的金额
    /*tSql = " select nvl(sum(a.RealPay),0) from LLClaimDutyKind a  where 1=1 "
       +" and a.ClmNo = '"+tClaimNo+"'"
       +" and a.GetDutyKind in ('100','200') "
       ;*/
    mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimSimpleInputSql");
	mySql.setSqlId("LLClaimSimpleSql10");
	mySql.addSubPara(tClaimNo ); 
	
    var arr = easyExecSql( mySql.getString() );    
    var tSumDutyKind = arr[0][0];


    //查询保项层面医疗类理赔类型的金额
    /*tSql = " select nvl(sum(a.RealPay),0) from LLClaimDetail a  where 1=1 "
       +" and a.ClmNo = '"+tClaimNo+"'"
       +" and a.GetDutyKind in ('100','200') "
       +" and a.GiveType in ('0') "       
       ;*/
    mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimSimpleInputSql");
	mySql.setSqlId("LLClaimSimpleSql11");
	mySql.addSubPara(tClaimNo ); 
    var brr = easyExecSql( mySql.getString() );    
    
    var tSumDutyCode = brr[0][0];
            
    if (tSumDutyKind != tSumDutyCode)
    {
        alert("提示:医疗类赔付总金额为:["+tSumDutyKind+"],而保项赔付总金额为:["+tSumDutyCode+"],请先进行保项层面金额调整,以便进行保额冲减!");
        return;
    }
    /**=========================================================================END**/
    
    //2009-04-30 zhangzheng 增加对发起调查和校验的判断
  //调查
    /*var strSql1 = "select FiniFlag from LLInqConclusion where "
               + "clmno = '" + tClaimNo + "'";*/
    mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimSimpleInputSql");
	mySql.setSqlId("LLClaimSimpleSql12");
	mySql.addSubPara(tClaimNo ); 
	
    var tFiniFlag = easyExecSql(mySql.getString());
    
    if (tFiniFlag)
    {
        for (var i = 0; i < tFiniFlag.length; i++)
        {
            if (tFiniFlag[i] != '1')
            {
                alert("发起的调查没有完成!");
                return false;
            }
        }
    }
    //呈报
    /*var strSql2 = "select SubState from LLSubmitApply where "
               + "clmno = '" + tClaimNo + "'";*/
               
    mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimSimpleInputSql");
	mySql.setSqlId("LLClaimSimpleSql13");
	mySql.addSubPara(tClaimNo ); 
	
    var tSubState = easyExecSql(mySql.getString());
    
    if (tSubState)
    {
        for (var i = 0; i < tSubState.length; i++)
        {
            if (tSubState[i] != '1')
            {
                alert("发起的呈报没有完成!");
                return false;
            }
        }
    }
    
	var rptNo = fm.RptNo.value;//赔案号
    var strUrl="LLBnfMain.jsp?RptNo="+rptNo+"&BnfKind=A&InsuredNo="+document.all('customerNo').value;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
//    window.open(strUrl,"出险人分配");
}

//帐户处理(由yuejw于2005年7月11日添加)
function showLCInsureAccont()
{
	var rptNo = fm.RptNo.value;//赔案号
    var strUrl="LCInsureAccMain.jsp?RptNo="+rptNo+"";
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
//    window.open(strUrl,"帐户处理");
}

//保单查询
//按照”客户号“在LCpol中进行查询，显示该客户的保单明细
function showInsuredLCPol()
{
//    var row = SubReportGrid.getSelNo();
//    if(row < 1)
//    {
//        alert("请选中一行记录！");
//        return;
//    }
//	var tCustomerNo = SubReportGrid.getRowColData(row-1,1);
//Modifyed by niuzj,2005-12-24
  var tCustomerNo = fm.customerNo.value;
  if (tCustomerNo == "")
	{
	    alert("请选择出险人！");
	    return;
	}
    var strUrl="LLLcContQueryMain.jsp?AppntNo="+tCustomerNo;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//既往赔案查询
function showOldInsuredCase()
{
//    var row = SubReportGrid.getSelNo();
//    if(row < 1)
//    {
//        alert("请选中一行记录！");
//        return;
//    }
//	var tCustomerNo = SubReportGrid.getRowColData(row-1,1);
//Modifyed by niuzj,2005-12-24
  var tCustomerNo = fm.customerNo.value;
  if (tCustomerNo == "")
	{
	    alert("请选择出险人！");
	    return;
	}
    var strUrl="LLLClaimQueryMain.jsp?AppntNo="+tCustomerNo+"&ClmNo="+fm.RptNo.value;
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

//单证费用调整
function showLLMedicalFeeAdj()
{
	  if(!KillTwoWindows(fm.RptNo.value,'30'))
	  {
	  	  return false;
	  }	
    var tClaimNo = fm.RptNo.value;         //赔案号
    var tCaseNo = fm.RptNo.value;          //分案号
    var tCustNo = fm.customerNo.value;          //客户号

    //alert(tClaimNo);
    var strUrl="LLMedicalFeeAdjMain.jsp?RptNo="+tClaimNo+"&caseNo="+tCaseNo+"&custNo="+tCustNo;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//查看调查
function showQueryInq()
{
    if(fm.RptNo.value == "" || fm.RptNo.value == null)
    {
        alert("先选择赔案！");
        return;
    }
	  //***************************************
	  //判断该赔案是否存在调查
	  //***************************************
    /*var strSQL = "select count(1) from LLInqConclusion where "
                + "ClmNo = '" + fm.RptNo.value + "'";*/
        mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimSimpleInputSql");
	mySql.setSqlId("LLClaimSimpleSql4");
	mySql.addSubPara(fm.RptNo.value ); 
	
    var tCount = easyExecSql(mySql.getString());
//    alert(tCount);
    if (tCount == "0")
    {
    	  alert("该赔案还没有调查信息！");
    	  return;
    }
	  var strUrl="LLInqQueryMain.jsp?claimNo="+fm.RptNo.value;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
//    window.open(strUrl,"查看调查");
}

//打开事故描述信息
function showClaimDesc()
{
    if(fm.RptNo.value == "" || fm.RptNo.value == null)
    {
        alert("先选择赔案！");
        return;
    }
    var strUrl="LLClaimDescMain.jsp?claimNo="+fm.RptNo.value+"&custNo="+fm.customerNo.value+"&startPhase=03";
   window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
//    window.open(strUrl,"");
}

//打开呈报查询
function showQuerySubmit()
{
    if(fm.RptNo.value == "" || fm.RptNo.value == null)
    {
        alert("先选择赔案！");
        return;
    }
	//***************************************
	//判断该赔案是否存在呈报
	//***************************************
    /*var strSQL = "select count(1) from LLSubmitApply where "
                + "ClmNo = '" + fm.RptNo.value+"'";
     */           
    mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimSimpleInputSql");
	mySql.setSqlId("LLClaimSimpleSql5");
	mySql.addSubPara(fm.RptNo.value ); 
	
    var tCount = easyExecSql(mySql.getString());
//    alert(tCount);
    if (tCount == "0")
    {
    	  alert("该赔案还没有呈报信息！");
    	  return;
    }
	  var strUrl="LLSubmitQueryMain.jsp?claimNo="+fm.RptNo.value;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
//    window.open(strUrl,"呈报查询");
}

//数据提交
function submitForm()
{
    //提交数据
    var i = 0;
    var showStr = "正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    //showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    document.getElementById("fm").submit(); //提交
    tSaveFlag ="0";
}

//匹配后的动作
function afterMatchDutyPay(FlagStr, content)
{
    showInfo.close();
    if (FlagStr == "FAIL" )
    {
        var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
        //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
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
        //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
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
    location.href="LLClaimSimpleMissInput.jsp";
}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
    showInfo.close();
    if (FlagStr == "Fail")
    {
        var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
        //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
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
        //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=350;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
        goToBack();
    }
    tSaveFlag ="0";
}

//提交后操作,不返回
function afterSubmit2( FlagStr, content )
{
    showInfo.close();
    if (FlagStr == "Fail" )
    {
        var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
        //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
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
        //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=350;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
        afterMatchDutyPayQuery();
    }
    tSaveFlag ="0";
}

//出险人查询
function ClientQuery()
{
//    window.open("LLLdPersonQueryInput.jsp","出险人查询",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
    window.open("LLLdPersonQueryInput.jsp");

}

//得到0级icd10码
function saveIcdValue()
{
    fm.ICDCode.value = fm.AccResult1.value;
}

//问题件回销
function showRgtMAffixList()
{
    if(SubReportGrid.getSelNo() <= 0)
    {
        alert("先选择出险人！");
        return;
    }
    var tClmNo=fm.ClmNo.value;
    var tcustomerNo=fm.customerNo.value;
    var strUrl="LLRgtMAffixListMain.jsp?ClmNo="+tClmNo+"&CustomerNo="+tcustomerNo+"&Proc=Rgt";

    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//发出问题件
function showRgtAddMAffixList()
{
    if(SubReportGrid.getSelNo() <= 0)
    {
        alert("先选择出险人！");
        return;
    }
    var tClmNo=fm.ClmNo.value;
    var tcustomerNo=fm.customerNo.value;
    var strUrl="LLRgtAddMAffixListMain.jsp?ClmNo="+tClmNo+"&CustomerNo="+tcustomerNo+"&Proc=RgtAdd";

    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//简易案件
function confirmClick()
{
	  if(!KillTwoWindows(fm.RptNo.value,'30'))
	  {
	  	  return false;
	  }	
	  
	//检查非空
	if (fm.SimpleConclusion.value == "" || fm.SimpleConclusion.value == null)
	{
		  alert("请填写简易案件结论！");
		  return;
	}
	
    //如果下给付结论,校验受益人分配
    if (fm.SimpleConclusion.value == "0")
    {
    	
    	//查询是否进行过匹配计算
       /* var sql2 = "select count(1) from LLClaimDetail where"
                 + " ClmNo = '" + fm.RptNo.value + "'";*/
    mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimSimpleInputSql");
	mySql.setSqlId("LLClaimSimpleSql6");
	mySql.addSubPara(fm.RptNo.value );  
	
        var tDutyKind = easyExecSql(mySql.getString());
        if (tDutyKind == 0)
        {
            alert("请先进行匹配并理算!");
            return;
        }
        
        /*var strSql4 = "select (case (select count(1) from llbnf b where b.ClmNo=a.ClmNo and b.polno=a.polno and b.bnfkind = 'A') when 0 then 0 else 1 end) from LLBalance a where 1=1 "
                    + getWherePart( 'ClmNo','ClmNo' );*/
            mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimSimpleInputSql");
	mySql.setSqlId("LLClaimSimpleSql7");
	mySql.addSubPara(fm.ClmNo.value ); 
	
        var tBNF = easyExecSql(mySql.getString());
        
        if (tBNF)
        {
            for (var i = 0; i < tBNF.length; i++)
            {
                if (tBNF[i] == '0')
                {
                    alert("受益人分配没有完成!");
                    return;
                }
            }
        }
 
    }
	
	fm.action="./LLClaimSimpleSave.jsp"
	fm.fmtransact.value = "";
	submitForm();
}

//查询出险结果
function queryResult(tCode,tName)
{
   /* var strSql = " select ICDName from LDDisease where "
               + " trim(ICDCode) = '" + document.all(tCode).value + "'";*/
    mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimSimpleInputSql");
	mySql.setSqlId("LLClaimSimpleSql8");
	mySql.addSubPara(document.all(tCode).value ); 
    var tICDName = easyExecSql(mySql.getString());
    if (tICDName)
    {
        document.all(tName).value = tICDName;
    }
}

//检查保项金额调整,只能调小
function checkAdjMoney()
{
    var i = PolDutyCodeGrid.getSelNo();
    if (i != '0')
    {
        i = i - 1;
        var tRealM = parseFloat(PolDutyCodeGrid.getRowColData(i,10));
        var tRiskCode = parseFloat(PolDutyCodeGrid.getRowColData(i,3));//险种编码
        var tGetDutyCode = parseFloat(PolDutyCodeGrid.getRowColData(i,4));//责任编码
        var tAdjM = parseFloat(fm.RealPay.value);
       // var strSQL = "select 1 from lmrisksort where riskcode='"+tRiskCode+"' and risksorttype='26' and risksortvalue='"+tGetDutyCode+"'";
    mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimSimpleInputSql");
	mySql.setSqlId("LLClaimSimpleSql19");
	mySql.addSubPara(tRiskCode ); 
	mySql.addSubPara(tGetDutyCode ); 
        //prompt("校验调整保项是否是津贴型:",strSQL);
        var arr= easyExecSql(mySql.getString());
        
        var tPolNo   = PolDutyCodeGrid.getRowColData(i,1);//险种保单号
      //  var strPSQL = "select riskamnt from lcpol where polno='"+tPolNo+"'";
    mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimSimpleInputSql");
	mySql.setSqlId("LLClaimSimpleSql20");
	mySql.addSubPara(tPolNo ); 
	        
        var arrp= easyExecSql(mySql.getString());
        
        if(arrp[0][0]== null){
        	alert("风险保额不存在");
        }
        

        //放开对津贴型险种的限制
        if(arr == null){
        	
            if (arrp[0][0] < tAdjM)
            {
                alert("调整金额不能大于风险保额!");
                fm.RealPay.value = PolDutyCodeGrid.getRowColData(i,22);
                return false;
            }
        }
    }
    
    return true;
}

//影像查询---------------2005-08-14添加
function colQueryImage()
{
    var strUrl="LLColQueryImageMain.jsp?claimNo="+document.all('ClmNo').value+"&subtype=LP1001";
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//打印赔案号条形码
function colBarCodePrint()
{
    fm.action="LLClaimBarCodePrintSave.jsp";
    fm.target = "../f1print";
    document.getElementById("fm").submit();
}

//查询治疗医院，Added by niuzj,2005-11-26
function queryHospital(tCode,tName)
{
	  /*var strSql =" select hospitalname from llcommendhospital  "
	             +" where trim(hospitalcode)='"+document.all(tCode).value+"' ";*/
	mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimSimpleInputSql");
	mySql.setSqlId("LLClaimSimpleSq21");
	mySql.addSubPara(document.all(tCode).value ); 
	
	  var tICDName = easyExecSql(mySql.getString());
    if (tICDName)
    {
        document.all(tName).value = tICDName;
    }
}





