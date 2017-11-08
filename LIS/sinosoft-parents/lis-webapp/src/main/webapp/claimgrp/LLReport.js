var showInfo;
var mDebug="1";
var tSaveFlag = "0";
var turnPage = new turnPageClass();

//开发中
function zhoulei()
{
	alert("开发中!");
}

//返回按钮
function goToBack()
{
    location.href="LLReportMissInput.jsp";
}

//设置界面上的所有输入栏为readonly
function readonlyFormElements()
{
    var elementsNum = 0;//FORM中的元素数
    //遍历FORM中的所有ELEMENT
    for (elementsNum=0; elementsNum<fm.elements.length; elementsNum++)
    {
  	    if (fm.elements[elementsNum].type == "text" || fm.elements[elementsNum].type == "textarea")
  	    {
  	        fm.elements[elementsNum].readonly = true;
  	    }
  	    if (fm.elements[elementsNum].type == "button")
  	    {
  	        fm.elements[elementsNum].disabled = true;
  	    }
  	}
}

//初始化查询
function initQuery()
{
    var rptNo = fm.ClmNo.value;
    if(rptNo == "")
    {
        alert("传入赔案为空！");
        return;
    }
    //检索事件号(一条)
    var strSQL1 = "select LLCaseRela.CaseRelaNo,LLAccident.AccDate from LLCaseRela,LLAccident where LLCaseRela.CaseRelaNo = LLAccident.AccNo and "
                + "LLCaseRela.CaseNo = '"+rptNo+"'";
//    alert("strSQL1= "+strSQL1);
    var AccNo = easyExecSql(strSQL1);
//    alert("AccNo= "+AccNo);
    //检索报案号及其他报案信息(一条)
    var strSQL2 = "select RptNo,RptorName,RptorPhone,RptorAddress,Relation,RptMode,AccidentSite,AccidentReason,RptDate,MngCom,Operator,RgtClass from LLReport where "
                + "RptNo = '"+rptNo+"'";
//    alert("strSQL2= "+strSQL2);
    var RptContent = easyExecSql(strSQL2);
//    alert("RptContent= "+RptContent);
    //更新页面内容
    fm.AccNo.value = AccNo[0][0];
    fm.AccidentDate.value = AccNo[0][1];

    fm.RptNo.value = RptContent[0][0];
    fm.RptorName.value = RptContent[0][1];
    fm.RptorPhone.value = RptContent[0][2];
    fm.RptorAddress.value = RptContent[0][3];
    fm.Relation.value = RptContent[0][4];
    fm.RptMode.value = RptContent[0][5];
    fm.AccidentSite.value = RptContent[0][6];
    fm.occurReason.value = RptContent[0][7];
    fm.RptDate.value = RptContent[0][8];
    fm.MngCom.value = RptContent[0][9];
    fm.Operator.value = RptContent[0][10];
    fm.RgtClass.value = RptContent[0][11];
    showOneCodeName('llrgtclass','RgtClass','RgtClassName'); //团体个体
    showOneCodeName('llrgrelation','Relation','RelationName');//报案人与事故人关系
    showOneCodeName('llrptmode','RptMode','RptModeName');//报案方式
    showOneCodeName('lloccurreason','occurReason','ReasonName');//出险原因
    showOneCodeName('commendhospital','hospital','TreatAreaName');//治疗医院
//    showOneCodeName('lloccurreason','IsDead','IsDeadName');//死亡标志

    //---------------------------------------------------*
    //更新页面权限
    //---------------------------------------------------*
    fm.AccidentDate.readonly = true;

//    fm.QueryPerson.disabled=true;
//    fm.QueryReport.disabled=true;

//    fm.occurReason.disabled=true;
//    fm.accidentDetail.disabled=true;
    fm.claimType.disabled=true;

//    fm.AddReport.disabled=false;
    fm.addbutton.disabled=false;
//    fm.updatebutton.disabled=false;
    fm.DoHangUp.disabled=false;
    fm.CreateNote.disabled=false;
    fm.BeginInq.disabled=false;
    fm.ViewInvest.disabled=false;
   	fm.Condole.disabled=false;
    fm.SubmitReport.disabled=false;
    fm.ViewReport.disabled=false;
    fm.AccidentDesc.disabled=false;
//   	fm.QueryCont1.disabled=false;
    fm.QueryCont2.disabled=false;
    fm.QueryCont3.disabled=false;

    //检索分案关联出险人信息(多条)
    var strSQL3 = "select CustomerNo,Name,Sex,Birthday,VIPValue from LDPerson where "
                + "CustomerNo in("
                + "select CustomerNo from LLSubReport where SubRptNo = '"+ rptNo +"')";
//    alert("strSQL3= "+strSQL3);
    easyQueryVer3Modal(strSQL3, SubReportGrid);
    if (SubReportGrid.getRowColData(0,1) != null && SubReportGrid.getRowColData(0,1) != "")
    {
        SubReportGridClick(0);
    }
}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
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

        initQuery();
        fm.RptConfirm.disabled = false;
    }
    tSaveFlag ="0";
}

//报案确认返回后执行的操作
function afterSubmit2( FlagStr, content )
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

        goToBack();
    }
    tSaveFlag ="0";
}

//提起慰问返回后执行的操作
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
    tSaveFlag ="0";
}

//重置按钮对应操作,Form的初始化函数在功能名+Init.jsp文件中实现，函数的名称为initForm()
function resetForm()
{
    try
    {
        initForm();

  	    fm.AccNo.value = "";
 	    fm.RptNo.value = "";
	    fm.RptorName.value = "";
	    fm.RptorPhone.value = "";
	    fm.RptorAddress.value = "";
	    fm.Relation.value = "";
	    fm.RelationName.value = "";
	    fm.RptMode.value = "";
	    fm.RptModeName.value = "";
	    fm.AccidentSite.value = "";
	    fm.occurReason.value = "";
	    fm.RptDate.value = "";
 	    fm.MngCom.value = "";
 	    fm.Operator.value = "";
	    fm.CaseState.value = "";

	    fm.customerNo.value = "";
	    fm.customerAge.value = "";
 	    fm.customerSex.value = "";
 	    fm.SexName.value = "";
	    fm.IsVip.value = "";
	    fm.AccidentDate.value = "";
 	    fm.occurReason.value = "";
 	    fm.ReasonName.value = "";
	    fm.hospital.value = "";
	    fm.TreatAreaName.value = "";
	    fm.AccidentDate2.value = "";
 	    fm.accidentDetail.value = "";
 	    fm.accidentDetailName.value = "";
//	 	    fm.IsDead.value = "";
//	 	    fm.IsDeadName.value = "";
 	    fm.claimType.value = "";
 	    fm.cureDesc.value = "";
 	    fm.cureDescName.value = "";
 	    fm.Remark.value = "";
 	    fm.AccDesc.value = "";//事故描述
 	    fm.AccResult1.value = "";
 	    fm.AccResult1Name.value = "";
 	    fm.AccResult2.value = "";
 	    fm.AccResult2Name.value = "";

        //理赔类型置空
        for (var j = 0;j < fm.claimType.length; j++)
        {
        	  fm.claimType[j].checked = false;
        }
    }
    catch(re)
    {
        alert("在LLReport.js-->resetForm函数中发生异常:初始化界面错误!");
    }
}

//取消按钮对应操作
function cancelForm()
{
}

//提交前的校验、计算
function beforeSubmit()
{
	//获取表单域信息
    var RptNo = fm.RptNo.value;//赔案号
    var CustomerNo = fm.customerNo.value;//出险人编号
    var AccDate = fm.AccidentDate.value;//事故日期
    var AccReason = fm.occurReason.value;//出险原因
    var AccDate2 = fm.AccidentDate2.value;//出险日期
    var AccDesc = fm.accidentDetail.value;//出险细节
    var ClaimType = new Array;
    //理赔类型
    for(var j=0;j<fm.claimType.length;j++)
    {
    	  if(fm.claimType[j].checked == true)
    	  {
            ClaimType[j] = fm.claimType[j].value;
          }
    }
//    alert("ClaimType = "+ClaimType);
    //取得年月日信息
    var AccYear = AccDate.substring(0,4);
    var AccMonth = AccDate.substring(5,7);
    var AccDay = AccDate.substring(8,10);
    var AccYear2 = AccDate2.substring(0,4);
    var AccMonth2 = AccDate2.substring(5,7);
    var AccDay2 = AccDate2.substring(8,10);
    //----------------------------------------------------------BEG
    //增加报案人姓名和关系的非空校验
    //----------------------------------------------------------
    if (fm.RptorName.value == "" || fm.RptorName.value == null)
    {
        alert("请输入报案人姓名！");
        return false;
    }
    if (fm.Relation.value == "" || fm.Relation.value == null)
    {
        alert("请输入报案人与事故人关系！");
        return false;
    }
    //----------------------------------------------------------end
    //1 检查出险人、信息
    if (checkInput1() == false)
    {
    	  return false;
    }
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
                alert("出险日期不能晚于当前日期");
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
    //---------------------------------------------------------------------------------**Beg*2005-7-12 16:27
    //添加出险原因为疾病时，事故日期等于出险日期
    //---------------------------------------------------------------------------------**
    if(fm.occurReason.value == "2" && (fm.AccidentDate.value != fm.AccidentDate2.value))
    {
        alert("出险原因为疾病时，事故日期必须等于出险日期！");
        return false;
    }
    //---------------------------------------------------------------------------------**End
    //4 检查出险细节
    if ((AccDesc == null || AccDesc == '') && fm.occurReason.value == '1')
    {
        alert("出险原因为意外,出险细节不能为空！");
        return false;
    }
    //5 检查理赔类型
    if (ClaimType == null || ClaimType == '')
    {
        alert("理赔类型不能为空！");
        return false;
    }
    //---------------------------------------------*Beg*2005-6-13 20:26
    //添加理赔类型中选中"医疗"必须选择医院的判断
    //---------------------------------------------*
    /*
    if(fm.claimType[5].checked == true && (fm.hospital.value == "" || fm.hospital.value == null))
    {
        alert("请选择医院！");
        return false;
    }
    */
    //---------------------------------------------*End
    //---------------------------------------------------------Beg*2005-6-27 11:55
    //添加理赔类型中选中豁免,必须选中死亡或高残之一的判断
    //---------------------------------------------------------
    if (fm.claimType[4].checked == true && fm.claimType[1].checked == false && fm.claimType[0].checked == false)
    {
        alert("选中豁免,必须选中死亡或高残之一!");
        return false;
    }
    //---------------------------------------------------------End
    //---------------------------------------------------------Beg*2005-6-27 13:59
    //添加未在出险后十日内报案的判断
    //---------------------------------------------------------
    if (dateDiff(fm.AccidentDate.value,mCurrentDate,'D') > 10)
    {
        alert("未在出险后十日内报案!");
    }
    //---------------------------------------------------------End
    return true;
}
//显示frmSubmit框架，用来调试
function showSubmitFrame(cDebug)
{
    if(cDebug=="1")
    {
        parent.fraMain.rows = "0,0,0,0,*";
    }
     else
     {
        parent.fraMain.rows = "0,0,0,0,*";
     }
}

//新增报案
function addClick()
{
    resetForm();
}

//出险人信息修改
function updateClick()
{
    if (confirm("您确实想修改该记录吗？"))
    {
        if (beforeSubmit() == true)
        {
            fm.fmtransact.value = "update";
            fm.action = './LLReportSave.jsp';
            submitForm();
        }
    }
    else
    {
        mOperate="";
    }
}

//出险人删除，需要判断，报案不允许删除
function deleteClick()
{
    alert("您无法进行删除操作！");
}

//显示div，第一个参数为一个div的引用，第二个参数为是否显示，如果为"true"则显示，否则不显示
function showDiv(cDiv,cShow)
{
    if (cShow=="true")
    {
        cDiv.style.display="";
    }
    else
    {
        cDiv.style.display="none";
    }
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
	var tCustomerNo = fm.customerNo.value;
	if (tCustomerNo == "")
	{
	    alert("请选择出险人！");
	    return;
	}
    var strUrl="LLLClaimQueryMain.jsp?AppntNo="+tCustomerNo;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//由事件查询返回
function afterQueryLL2(tAccNo,tOccurReason,tAccDesc,tAccidentSite)
{
	//得到返回值
    fm.AccNo.value = tAccNo;
    fm.occurReason.value = tOccurReason;
    fm.AccDesc.value = tAccDesc;
    fm.AccDescDisabled.value = tAccDesc;
    fm.AccidentSite.value = tAccidentSite;
    showOneCodeName('lloccurreason','occurReason','ReasonName');//出险原因
}

//由客户查询（LLLdPersonQuery.js）返回单条记录时调用
function afterQueryLL(arr)
{
	  //得到返回值
    fm.customerNo.value = arr[0];
    fm.customerName.value = arr[1];
    fm.customerSex.value = arr[2];
    fm.customerAge.value = arr[3];
    fm.IsVip.value = arr[4];
    showOneCodeName('sex','customerSex','SexName');//性别
//    fm.customerSex.disabled = true;
    //初始化录入域
    fm.hospital.value = "";
    fm.TreatAreaName.value = "";
    fm.AccidentDate2.value = "";
    fm.accidentDetail.value = "";
    fm.accidentDetailName.value = "";
//    fm.IsDead.value = "";
//    fm.IsDeadName.value = "";
    fm.cureDesc.value = "";
    fm.cureDescName.value = "";
//    fm.Remark.value = "";
    for(var j=0;j<fm.claimType.length;j++)
    {
    	  fm.claimType[j].checked = false;
    }
    //设置可操作按钮
    fm.addbutton.disabled = false;
    fm.QueryCont2.disabled = false;
    fm.QueryCont3.disabled = false;
}

//出险人查询
function showInsPerQuery()
{
    window.open("LLLdPersonQueryMain.jsp","",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
//    window.open("LLLdPersonQueryMain.jsp");
}

//保单挂起
function showLcContSuspend()
{
    var row = SubReportGrid.getSelNo();
    if(row < 1)
    {
        alert("请选中一行记录！");
        return;
    }
    var tCustomerNo = SubReportGrid.getRowColData(row-1,1);
    var strUrl="LLLcContSuspendMain.jsp?InsuredNo="+tCustomerNo+"&ClmNo="+fm.ClmNo.value;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//打开生成单证通知书
function showAffix()
{
    var claimTypes=getClaimTypes();
    var CaseNo=fm.ClmNo.value;
    var whoNo=fm.customerNo.value;
    var strUrl="LLRptMAffixListMain.jsp?claimTypes="+claimTypes+"&CaseNo="+CaseNo+"&whoNo="+whoNo+"&Proc=Rpt";
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//打开发起调查
function showBeginInq()
{
    if(fm.RptNo.value == "" || fm.RptNo.value == null)
    {
        alert("先选择赔案！");
        return;
    }
    var strUrl="LLInqApplyMain.jsp?claimNo="+fm.RptNo.value+"&custNo="+fm.customerNo.value+"&custName="+fm.customerName.value+"&custVip="+fm.IsVip.value+"&initPhase=01";
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
//    window.open(strUrl,"发起调查");
}

//查看调查
function showQueryInq()
{
    if(fm.RptNo.value == "" || fm.RptNo.value == null)
    {
        alert("先选择赔案！");
        return;
    }
	//---------------------------------------
	//判断该赔案是否存在调查
	//---------------------------------------
    var strSQL = "select count(1) from LLInqConclusion where "
                + "ClmNo = '" + fm.RptNo.value+"'";
    var tCount = easyExecSql(strSQL);
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

//提起慰问
function showCondole()
{
    if(SubReportGrid.getSelNo() <= 0)
    {
        alert("先选择出险人！");
        return;
    }
	//---------------------------------------
	//判断该分案是否已提起慰问
	//---------------------------------------
    var strSQL = "select CondoleFlag from LLSubReport where "
                + " SubRptNo = '" + fm.RptNo.value + "'";
                + " and CustomerNo = '" + fm.customerNo.value + "'";
    var tCondoleFlag = easyExecSql(strSQL);
//    alert(tCount);
    if (tCondoleFlag == "1")
    {
    	  alert("已提起慰问！");
    	  return;
    }
    fm.action = './LLReportCondoleSave.jsp';
    submitForm();
}
//打开发起呈报
function showBeginSubmit()
{

	var strUrl="LLSubmitApplyMain.jsp?claimNo="+fm.RptNo.value+"&custNo="+fm.customerNo.value+"&custName="+fm.customerName.value+"&custVip="+fm.IsVip.value;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
//  window.open(strUrl,"发起呈报");

}

//打开呈报查询
function showQuerySubmit()
{
	  //---------------------------------------
	  //判断该赔案是否存在呈报
	  //---------------------------------------
//    var strSQL = "select count(1) from LLSubmitApply where "
//                + "ClmNo = " + fm.RptNo.value;
//    var tCount = easyExecSql(strSQL);
////    alert(tCount);
//    if (tCount == "0")
//    {
//    	  alert("该赔案还没有呈报信息！");
//    	  return;
//    }
	  var strUrl="LLSubmitQueryMain.jsp?claimNo="+fm.RptNo.value;
      window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
//    window.open(strUrl,"呈报查询");
}

//打开事故描述信息
function showClaimDesc()
{
    if(fm.RptNo.value == "" || fm.RptNo.value == null)
    {
        alert("先选择赔案！");
        return;
    }
    var strUrl="LLClaimDescMain.jsp?claimNo="+fm.RptNo.value+"&custNo="+fm.customerNo.value+"&startPhase=01";
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
//    window.open(strUrl,"事故描述信息");
}

////客户详细信息查询
//function showCustomerInfo()
//{
//  window.open("../sys/FrameCQPersonQuery.jsp");
//}

//报案查询
function IsReportExist()
{
	  //检查出险人、信息
	  if (checkInput1() == false)
    {
    	  return;
    }
    queryReport();

}

//检查出险人、信息
function checkInput1()
{
	  //获取表单域信息
    var CustomerNo = fm.customerNo.value;//出险人编号
    var AccDate = fm.AccidentDate.value;//事故日期
    //取得年月日信息
    var AccYear = AccDate.substring(0,4);
    var AccMonth = AccDate.substring(5,7);
    var AccDay = AccDate.substring(8,10);
    //检查出险人信息
    if (CustomerNo == null || CustomerNo == '')
    {
        alert("请先选择出险人！");
        return false;
    }
    //检查事故日期
    if (AccDate == null || AccDate == '')
    {
        alert("请输入事故日期！");
        return false;
    }
    else
    {
        if (AccYear > mNowYear)
        {
            alert("事故日期不能晚于当前日期");
            return false;
        }
        else if (AccYear == mNowYear)
        {
            if (AccMonth > mNowMonth)
            {

                alert("事故日期不能晚于当前日期AccMonth > mNowMonth");
                return false;
            }
            else if (AccMonth == mNowMonth && AccDay > mNowDay)
            {
                alert("事故日期不能晚于当前日期!");
                return false;
            }
        }
    }
    return true;
}

//出险原因判断
function checkOccurReason()
{
    if (fm.occurReason.value == '1')
    {
        fm.accidentDetail.disabled = false;
    }
    else if (fm.occurReason.value == '2')
    {
        fm.accidentDetail.disabled = true;
    }
}

//报案查询
function queryReport()
{
	var AccNo;
	var RptContent = new Array();
	var queryResult = new Array();
    //检索事件号(一条)
    var strSQL1 = "select AccNo from LLAccident where "
                + "AccDate = to_date('"+fm.AccidentDate.value
                + "','yyyy-mm-dd') "
                + getWherePart( 'AccType','occurReason' )
                + " and AccNo in (select AccNo from LLAccidentSub where 1=1 "
                + getWherePart( 'CustomerNo','customerNo' )
                + ")";

//    alert("strSQL1= "+strSQL1);
    AccNo = decodeEasyQueryResult(easyQueryVer3(strSQL1));
//    alert("AccNo= "+AccNo);
    if (AccNo == null )
    {
    	  fm.isReportExist.value = "false";
    	  alert("报案不存在!");
    	  return
    }
    else
    {
        //检索报案号及其他报案信息(一条)
        var strSQL2 = "select RptNo,RptorName,RptorPhone,RptorAddress,Relation,RptMode,AccidentSite,AccidentReason,RptDate,MngCom,Operator,RgtClass from LLReport where "
                    + "RptNo in (select CaseNo from LLCaseRela where "
                    + "CaseRelaNo = '"+ AccNo +"' and SubRptNo in (select SubRptNo from LLSubReport where 1=1 "
                    + getWherePart( 'CustomerNo','customerNo' )
                    + "))";
//        alert("strSQL2= "+strSQL2);
        RptContent = decodeEasyQueryResult(easyQueryVer3(strSQL2));
//        alert("RptContent= "+RptContent);
        if (RptContent == null)
        {
        	  fm.isReportExist.value = "false";
    	      alert("报案不存在!");
    	      return;
        }
        else
      	{
      		  //更新页面内容
      		  fm.AccNo.value = AccNo;
      		  fm.RptNo.value = RptContent[0][0];
      		  fm.RptorName.value = RptContent[0][1];
      		  fm.RptorPhone.value = RptContent[0][2];
      		  fm.RptorAddress.value = RptContent[0][3];
      		  fm.Relation.value = RptContent[0][4];
      		  fm.RptMode.value = RptContent[0][5];
      		  fm.AccidentSite.value = RptContent[0][6];
      		  fm.occurReason.value = RptContent[0][7];
      		  fm.RptDate.value = RptContent[0][8];
      		  fm.MngCom.value = RptContent[0][9];
      		  fm.Operator.value = RptContent[0][10];
              fm.RgtClass.value = RptContent[0][11];
              showOneCodeName('llrgtclass','RgtClass','RgtClassName'); //团体个体
      		  showOneCodeName('llrgrelation','Relation','RelationName');//报案人与事故人关系
      		  showOneCodeName('RptMode','RptMode','RptModeName');//报案方式
      		  showOneCodeName('lloccurreason','occurReason','ReasonName');//出险原因
      		  showOneCodeName('commendhospital','hospital','TreatAreaName');//治疗医院
//      		  showOneCodeName('lloccurreason','IsDead','IsDeadName');//死亡标志
      		  //更新页面权限
      		  fm.AccidentDate.readonly = true;
//      		  fm.occurReason.disabled=true;
//      		  fm.accidentDetail.disabled=true;
      		  fm.claimType.disabled=true;

//      		  fm.AddReport.disabled=false;
      	 	  fm.addbutton.disabled=false;
//     		    fm.updatebutton.disabled=false;
    		    fm.DoHangUp.disabled=false;
    		    fm.CreateNote.disabled=false;
    		    fm.BeginInq.disabled=false;
    		    fm.ViewInvest.disabled=false;
   		      fm.Condole.disabled=false;
    		    fm.SubmitReport.disabled=false;
    		    fm.ViewReport.disabled=false;
    		    fm.AccidentDesc.disabled=false;
//   		      fm.QueryCont1.disabled=false;
    		    fm.QueryCont2.disabled=false;
    		    fm.QueryCont3.disabled=false;
    		//出险原因校验
    		checkOccurReason();

            //检索分案关联出险人信息(多条)
            var strSQL3 = "select CustomerNo,Name,Sex,Birthday,VIPValue from LDPerson where "
                        + "CustomerNo in("
                        + "select CustomerNo from LLSubReport where SubRptNo = '"+ RptContent[0][0] +"')";
//            alert("strSQL3= "+strSQL3);
//            personInfo = decodeEasyQueryResult(easyQueryVer3(strSQL3));
//            alert("personInfo= "+personInfo);
            easyQueryVer3Modal(strSQL3, SubReportGrid);
        }
    }
}

//事件查询
//2005-8-1 16:08 修改:去掉出险原因的查询条件来定位事件
function queryLLAccident()
{
	//非空检查
	if (checkInput1() == false)
    {
    	  return;
    }
//    if (fm.occurReason.value == "")
//    {
//        alert("出险原因为空！");
//        return;
//    }
    //查找事件号
    var strSQL = "select AccNo from LLAccident where "
                + "AccDate = to_date('"+fm.AccidentDate.value
                + "','yyyy-mm-dd') and AccNo in (select AccNo from LLAccidentSub where 1=1 "
                + getWherePart( 'CustomerNo','customerNo' )
//                + getWherePart( 'AccType','occurReason' )
                + ")";
    var tAccNo = easyExecSql(strSQL);
//    alert(tAccNo);
    if (tAccNo == null)
    {
        alert("没有相关事件！");
        return;
    }
    //打开事件查询窗口
//	var strUrl="LLAccidentQueryMain.jsp?AccDate="+fm.AccidentDate.value+"&CustomerNo="+fm.customerNo.value+"&AccType="+fm.occurReason.value;
	var strUrl="LLAccidentQueryMain.jsp?AccDate="+fm.AccidentDate.value+"&CustomerNo="+fm.customerNo.value;
//	alert(strUrl);
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
//    window.open(strUrl,"相关事件");
}

//[保存]按钮对应操作
function toSaveClick()
{
    //判断是否新增还是修改
    if(fm.isNew.value == '1')
    {
    	updateClick();
    }
    else
    {
        saveClick();
    }
}

//保存操作
function saveClick()
{
    //首先进行非空字段检验
    if (beforeSubmit() == true)
    {
//        //死亡检验
//        if (fm.claimType[0].checked == true)
//        {
//            if (confirm("您选择了死亡理赔类型,需要进行保单挂起吗？"))
//            {
//
//            }
//            else
//        }
        //判断区分保存报案还是保存出险人
        if (fm.RptNo.value != "")
        {
        	fm.fmtransact.value = "insert||customer";
        }
        else
        {
            fm.fmtransact.value = "insert||first";
        }
        fm.action = './LLReportSave.jsp';
        submitForm();
    }
}

//报案确认
function reportConfirm()
{
    //---------------------------------------------------------------------beg 2005-8-4 16:32
    //增加死亡类案件保单挂起提醒功能
    //---------------------------------------------------------------------
    var tReasonCode = new Array;
    var strSQL = "select substr(ReasonCode,2,2) from LLReportReason where "
                + "RpNo = '"+fm.RptNo.value+"'";
    tReasonCode = easyExecSql(strSQL);
    var tYesOrNo = 0;
    for (var i = 0;i < tReasonCode.length; i++)
    {
    	if (tReasonCode[i] == '02')
    	{
            tYesOrNo = 1;
    	}
    }
    if (tYesOrNo == 1)
    {
        tYesOrNo = 0;

        //检查是否已经手工挂起
        var strSQL = "";
        var arrResult = new Array;
    	strSQL = "select nvl((select b.PosFlag from LCContHangUpState b where b.ContNo = a.ContNo),0),nvl((select b.RNFlag from LCContHangUpState b where b.ContNo = a.ContNo),0)"
    		   + " from LcCont a where 1=1 "
    		   + " and a.insuredno in (select c.customerno from llsubreport c where c.subrptno = '"+ fm.RptNo.value +"')"
    		   + " or a.AppntNo in (select c.customerno from llsubreport c where c.subrptno = '"+ fm.RptNo.value +"')";  //投保人
	    arrResult = easyExecSql(strSQL);

	    for (var j=0; j<arrResult.length; j++)
	    {
    	    for (var k=0; k<arrResult[j].length; k++)
    	    {
    	        if (arrResult[j][k] == "0")
    	        {
    	            tYesOrNo++;
    	        }
    	    }
	    }
//	    alert(tYesOrNo);
	    if (tYesOrNo > 0)
	    {
        	if (confirm("理赔类型选择死亡,是否进行保单挂起操作?"))
            {
                return;
            }
        }
    }
    //---------------------------------------------------------------------end
    //检查非空
    if (fm.RptNo.value == "")
    {
   	    alert("报案号为空！");
   	    return;
    }
    fm.action = './LLReportConfirmSave.jsp';
    submitForm();
}

//提交数据
function submitForm()
{
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

    //    showSubmitFrame(mDebug);
    fm.target="fraSubmit";
    fm.submit(); //提交
    tSaveFlag ="0";
}

//选中SubReportGrid响应事件,tPhase=0为初始化时调用
function SubReportGridClick(tPhase)
{
	//------------------------------------------**Beg
	//置空相关表单
	//------------------------------------------**
	fm.customerName.value = "";
	fm.customerAge.value = "";
	fm.customerSex.value = "";
	fm.SexName.value = "";
	fm.IsVip.value = "";
	fm.hospital.value = "";
	fm.TreatAreaName.value = "";
	fm.AccidentDate2.value = "";
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
    //------------------------------------------**End

	//--------------------------------------------Beg
	//显示隐藏区域
	//--------------------------------------------
	spanText1.style.display = "none";
	spanText2.style.display = "";
    spanText3.style.display = "none";
	spanText4.style.display = "";
	//--------------------------------------------End

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
        fm.IsVip.value = SubReportGrid.getRowColData(i,5);
        showOneCodeName('sex','customerSex','SexName');//性别
    }

    //查询获得理赔类型
    var tClaimType = new Array;
    var strSQL1 = "select ReasonCode from LLReportReason where "
                + "RpNo = '"+fm.RptNo.value+"'"
                + " and CustomerNo = '"+fm.customerNo.value+"'";
//    alert(tClaimType);
    tClaimType = easyExecSql(strSQL1);
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

                	  //如果为伤残，显示伤残鉴定通知2005-8-13 11:04
                	  if (tClaim == '01')
                	  {
                	      fm.MedCertForPrt.disabled = false;
                	  }
            	  }
            }
        }
    }
    //查询分案表信息
    var tSubReport = new Array;
    var strSQL2 = "select HospitalCode,AccDate,AccidentDetail,DieFlag,CureDesc,Remark,AccDesc,AccResult1,AccResult2 from LLSubReport where 1=1 "
                + getWherePart( 'SubRptNo','RptNo' )
                + getWherePart( 'CustomerNo','customerNo' );
//    alert(strSQL2);
    tSubReport = easyExecSql(strSQL2);
//    alert(tSubReport);
    fm.hospital.value = tSubReport[0][0];
    fm.AccidentDate2.value = tSubReport[0][1];
    fm.accidentDetail.value = tSubReport[0][2];
//    fm.IsDead.value = tSubReport[0][3];
    fm.cureDesc.value = tSubReport[0][4];
    fm.Remark.value = tSubReport[0][5];
    fm.RemarkDisabled.value = tSubReport[0][5];
    fm.AccDesc.value = tSubReport[0][6];
    fm.AccDescDisabled.value = tSubReport[0][6];
    fm.AccResult1.value = tSubReport[0][7];
    fm.AccResult2.value = tSubReport[0][8];
    showOneCodeName('commendhospital','hospital','TreatAreaName');//治疗医院
    showOneCodeName('llaccidentdetail','accidentDetail','accidentDetailName');//出险细节
//    showOneCodeName('llDieFlag','IsDead','IsDeadName');//死亡标识
    showOneCodeName('llCureDesc','cureDesc','cureDescName');//治疗情况
    showOneCodeName('lldiseases1','AccResult1','AccResult1Name');//出险结果1
    showOneCodeName('lldiseases2','AccResult2','AccResult2Name');//出险结果2
    queryResult2();
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

//---------------------------------------------------**
//功能：理赔类型逻辑判断
//处理：1 死亡、高残、医疗三者只可选一
//      2 选择死亡或高残时，默认选中豁免
//      3 选中豁免,必须选中死亡或高残之一(放在保存时判断)
//---------------------------------------------------**
function callClaimType(ttype)
{
    switch (ttype)
    {
        case "02" : //死亡
            if (fm.claimType[0].checked == true)
            {
                fm.claimType[4].checked = true;
            }
//            if ((fm.claimType[1].checked == true || fm.claimType[5].checked == true) && fm.claimType[0].checked == true)
//            {
//                alert("死亡、高残、医疗三者只可选一!");
//                fm.claimType[0].checked = false;
//                return;
//            }
//            else
//            {
//                if (fm.claimType[0].checked == true)
//                {
//                    fm.claimType[4].checked = true;
//                }
//            }
        case "03" :
            if (fm.claimType[1].checked == true)
            {
                fm.claimType[4].checked = true;
            }
//            if ((fm.claimType[0].checked == true || fm.claimType[5].checked == true)&& fm.claimType[1].checked == true)
//            {
//                alert("死亡、高残、医疗三者只可选一!");
//                fm.claimType[1].checked = false;
//                return;
//            }
//            fm.claimType[4].checked = true;
        case "09" :
//            if ((fm.claimType[0].checked == true || fm.claimType[1].checked == true) && fm.claimType[4].checked == false)
//            {
//                alert("选取死亡、高残必须选择豁免!");
//                fm.claimType[4].checked = true;
//                return;
//            }
        case "00" :
//            if ((fm.claimType[0].checked == true || fm.claimType[1].checked == true) && fm.claimType[5].checked == true)
//            {
//                alert("死亡、高残、医疗三者只可选一!");
//                fm.claimType[5].checked = false;
//                return;
//            }
        default :
            return;
    }
}

/**=========================================================================
    修改状态：开始
    修改原因：以下为取理赔类型函数功能区
    修 改 人：潘志豪
    修改日期：2005.05.24
   =========================================================================
**/

function getClaimTypes(){
    var f=fm.elements;
    var types="";
    for (var i=0;i<f.length;i++){
    	if ((f[i].type=="checkbox")&&(f[i].checked)){
    	    if (types=="")
    	    	types=types+f[i].value;
    	    else
    	    	types=types+","+f[i].value;
        }
    }
    return types;
}

//得到0级icd10码
function saveIcdValue()
{
    fm.ICDCode.value = fm.AccResult1.value;
}

//查询出险结果2
function queryResult2()
{
    var strSql = " select ICDName from LDDisease where "
               + " ICDCode = '" + fm.AccResult2.value + "'"
               + " order by ICDCode";
    var tICDName = easyExecSql(strSql);
    if (tICDName)
    {
        fm.AccResult2Name.value = tICDName;
    }
}

/**=========================================================================
    修改状态：开始
    修改原因：打印单证
    修 改 人：续涛
    修改日期：2005.07.28
    修改：2005.08.22，打印单证时在前台判断《是否存在或者需要补打》，查询流水号，传入后台，
   =========================================================================
**/
function showPrtAffix()
{
	fm.action = './LLPRTCertificatePutOutSave.jsp';
	if(beforePrtCheck(fm.ClmNo.value,"PCT002")==false)
    {
    	return;
    }
//	fm.target = "../f1print";
//	fm.submit();


}

//打印伤残鉴定通知书
function PrintMedCert()
{
    var row = SubReportGrid.getSelNo();
    if(row < 1)
    {
        alert("请选中一行记录！");
        return;
    }
    fm.action = './LLPRTAppraisalSave.jsp';
    if(beforePrtCheck(fm.ClmNo.value,"PCT001")==false)
    {
    	return;
    }
//    fm.target = "../f1print";
//    fm.submit();

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
    fm.submit();
}



/**********************************
//打印前检验（），需要传入参数（单证号码、赔案号）--------2005-08-22添加
***********************************/
function beforePrtCheck(clmno,code)
{
	//查询单证流水号，对应其它号码（赔案号）、单据类型、打印方式、打印状态、补打标志
   	var tclmno=trim(clmno);
   	var tcode =trim(code);
   	if(tclmno=="" ||tcode=="")
   	{
   		alert("传入的赔案号或单证类型（号码）为空");
   		return false;
   	}
    var strSql="select t.prtseq,t.otherno,t.code,t.prttype,t.stateflag,t.patchflag from loprtmanager t where 1=1 "
			+" and t.otherno='"+tclmno+"' "
			+" and trim(t.code)='"+tcode+"' ";
	var arrLoprt = easyExecSql(strSql);
	if(arrLoprt==null)
	{
		alert("没有找到该单证的流水号");
		return false;
	}

	var tprtseq=arrLoprt[0][0];//单证流水号
	var totherno=arrLoprt[0][1];//对应其它号码（赔案号）
	var tcode=arrLoprt[0][2];//单据类型
	var tprttype=arrLoprt[0][3];//打印方式
	var tstateflag=arrLoprt[0][4];//打印状态
	var tpatchflag=arrLoprt[0][5];//补打标志
	fm.PrtSeq.value=arrLoprt[0][0];
	//存在但未打印过，直接进入打印页面
 	if(tstateflag=="0")
 	{
//			fm.action = './LLPRTCertificatePutOutSave.jsp';   //
		fm.target = "../f1print";
		fm.submit();
 	}
	else
	{
		//存在但已经打印过，tstateflag[打印状态：1 -- 完成、2 -- 打印的单据已经回复、3 -- 表示已经发催办通知书]
		if(confirm("该单证已经打印完成，你确实要补打吗？"))
 		{
 			//进入补打原因录入页面
 			var strUrl="LLPrtagainMain.jsp?PrtSeq="+fm.PrtSeq.value;
	 		window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
 		}
	}

}
