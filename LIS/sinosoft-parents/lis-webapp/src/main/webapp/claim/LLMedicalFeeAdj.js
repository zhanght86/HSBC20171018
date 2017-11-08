var showInfo;
var turnPage = new turnPageClass();
var mySql = new SqlClass();
//********************************************Beg
//置空相关表单
//********************************************
function cleanFmElement(){
//显示区

	//********************************************Beg
	//置空相关表单
	//********************************************
	if (fm.OperationType.value == 'A' || fm.OperationType.value == 'B')
	{
        	fm.ClmNo.value = "";
			fm.DutyFeeType.value = "";
			fm.DutyFeeCode.value = "";
			fm.DutyFeeName.value = "";
			fm.DutyFeeStaNo.value = "";
			fm.HosID.value = "";
			fm.HosName.value = "";
			fm.HosGrade.value = "";
			fm.StartDate.value = "";
			fm.EndDate.value = "";
			fm.DayCount.value = "";
			fm.OriSum.value = "";
        	fm.AdjSum.value = "";
			fm.AdjReason.value = "";
//			fm.AdjReasonName.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,1);
			fm.AdjRemark.value = "";

			fm.HosGradeName.value = "";
			fm.AdjReasonName.value = "";
    }
    else if (fm.OperationType.value == 'C')
    {
			fm.ClmNo.value = "";
			fm.DutyFeeType.value = "";
			fm.DutyFeeCode.value = "";
			fm.DutyFeeName.value = "";
			fm.DutyFeeStaNo.value = "";
			fm.DefoType.value = "";
			fm.DefoCode.value = "";
			fm.DefoeName.value = "";
			fm.DefoRate.value = "";
			fm.RealRate.value = "";
			fm.AdjReason1.value = "";
//			fm.AdjReasonName.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,1);
			fm.AdjRemark1.value = "";

			fm.AdjReasonName.value = "";
    }
    else if (fm.OperationType.value == 'F' || fm.OperationType.value == 'D' || fm.OperationType.value == 'E')
    {
        fm.ClmNo.value = "";
		fm.DutyFeeType.value = "";
		fm.DutyFeeCode.value = "";
		fm.DutyFeeName.value = "";
		fm.DutyFeeStaNo.value = "";
        fm.OriSum1.value = "";
		fm.AdjSum1.value = "";
		fm.AdjReason2.value = "";
//		fm.AdjReasonName.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,1);
		fm.AdjRemark2.value = "";

		fm.AdjReasonName.value = "";
    }
}

/*医疗费用调整显示信息*/
function getMedFeeInHosInpGrid()
{
    cleanFmElement();
	
    var rptNo = fm.ClmNo2.value;
    if(rptNo == "")
    {
        alert("传入赔案为空！");
        return;
    }

    //取得数据
    
    //得到MulLine行
	var tNo = MedFeeInHosInpGrid.getSelNo();
    if (fm.OperationType.value == 'A' || fm.OperationType.value == 'B')
	{

		    fm.ClmNo.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,1);
			fm.DutyFeeType.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,2);
			fm.DutyFeeCode.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,3);
			fm.DutyFeeName.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,4);
			fm.DutyFeeStaNo.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,5);
			fm.HosID.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,6);
			fm.HosName.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,7);
			fm.HosGrade.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,22);
			fm.StartDate.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,9);
			fm.EndDate.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,10);
			fm.DayCount.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,11);
			fm.OriSum.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,12);
        	fm.AdjSum.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,13);
			fm.AdjReason.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,21);
//			fm.AdjReasonName.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,1);
			fm.AdjRemark.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,15);
			showOneCodeName('llmedfeeadjreason','AdjReason','AdjReasonName');//保项金额调整原因
			showOneCodeName('llhosgrade','HosGrade','HosGradeName');//医院等级

    }
    else if (fm.OperationType.value == 'C')
    {
		
		    fm.ClmNo.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,1);
			fm.DutyFeeType.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,2);
			fm.DutyFeeCode.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,3);
			fm.DutyFeeName.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,4);
			fm.DutyFeeStaNo.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,5);
			fm.DefoType.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,16);
			fm.DefoCode.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,17);
			fm.DefoeName.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,18);
			fm.DefoRate.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,19);
			fm.RealRate.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,20);
			fm.AdjReason1.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,21);
//			fm.AdjReasonName.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,1);
			fm.AdjRemark1.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,15);
			showOneCodeName('llmedfeeadjreason','AdjReason1','AdjReasonName1');//保项金额调整原因
    }
    else if (fm.OperationType.value == 'F' || fm.OperationType.value == 'D' || fm.OperationType.value == 'E')
    {
		fm.ClmNo.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,1);
		fm.DutyFeeType.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,2);
		fm.DutyFeeCode.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,3);
		fm.DutyFeeName.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,4);
		fm.DutyFeeStaNo.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,5);
        fm.OriSum1.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,12);
		fm.AdjSum1.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,13);
		fm.AdjReason2.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,21);
//		fm.AdjReasonName.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,1);
		fm.AdjRemark2.value = MedFeeInHosInpGrid.getRowColData(tNo - 1,1);
		showOneCodeName('llmedfeeadjreason','AdjReason2','AdjReasonName2');//保项金额调整原因
    }
    
}

//医疗费用调整显示信息
function queryGrid()
{
    initMedFeeInHosInpGrid();
	var tclaimNo = fm.ClmNo2.value;
	var dutyFeeType = null;
	if (fm.OperationType.value == 'A'){
		dutyFeeType = "A";
	}else if(fm.OperationType.value == 'B'){
		dutyFeeType = "B";
	}else if(fm.OperationType.value == 'C'){
		dutyFeeType = "C";
	}else if(fm.OperationType.value == 'F'){
		dutyFeeType = "F";
	}else if(fm.OperationType.value == 'D'){
		dutyFeeType = "D";
	}else if(fm.OperationType.value == 'E'){
		dutyFeeType = "E";
	}
	//alert("dutyFeeType"+dutyFeeType);
    //----------------------------1-----------2-------------3----------4-----------5----------6-----------7----------8-----------9----10---11-------12--------13-------------14--------15
    /*var strSql = " select distinct ClmNo,DutyFeeType,DutyFeeCode,DutyFeeName,DutyFeeStaNo,HosID,HosName,(select c.codename from ldcode c where c.codetype = 'llhosgrade' and trim(c.code)=trim(LLClaimDutyFee.HosGrade)),"
				+"StartDate,EndDate,(case substr(dutyfeecode,1,2) when 'CR' then DayCount else to_number(EndDate-StartDate+1) end),OriSum,AdjSum,(select c.codename from ldcode c where c.codetype = 'llmedfeeadjreason' and trim(c.code)=trim(LLClaimDutyFee.AdjReason)),AdjRemark,DefoType,DefoCode,DefoeName,DefoRate,"
				+"RealRate,AdjReason,HosGrade from LLClaimDutyFee where ClmNo = '" + tclaimNo + "' and DutyFeeType ='"+dutyFeeType+"'";*/
    mySql = new SqlClass();
	mySql.setResourceName("claim.LLMedicalFeeAdjInputSql");
	mySql.setSqlId("LLMedicalFeeAdjSql1");
	mySql.addSubPara(tclaimNo); 
	mySql.addSubPara(dutyFeeType); 
    //显示所有数据
	//alert("strSql"+strSql);
    var arr = easyExecSql(mySql.getString());//alert("arr"+arr);
    if (arr)
    {
        displayMultiline(arr,MedFeeInHosInpGrid);
    }
}

//选择账单类型
function choiseType()
{
    if (fm.OperationType.value == 'A' || fm.OperationType.value == 'B')
	{   
		initMedFeeInHosInpGrid();
		queryGrid();
        divLLSubReport0.style.display="";
        divLLSubReport1.style.display="";
    //    divLLSubReport1.disabled="false";
		divLLSubReport2.style.display="none";
    //   divLLSubReport2.disabled="ture";
		divLLSubReport3.style.display="none";
    //    divLLSubReport3.disabled="ture";
		cleanFmElement();
    }
    else if (fm.OperationType.value == 'C')
    {   
		initMedFeeInHosInpGrid();
		queryGrid();
        divLLSubReport0.style.display="";
        divLLSubReport2.style.display="";
   //     divLLSubReport2.disabled="false";
		divLLSubReport1.style.display="none";
   //     divLLSubReport1.disabled="ture";
		divLLSubReport3.style.display="none";
   //     divLLSubReport3.disabled="ture";
   		cleanFmElement();
    }
    else if (fm.OperationType.value == 'F' || fm.OperationType.value == 'D' || fm.OperationType.value == 'E')
    {   
		initMedFeeInHosInpGrid();
		queryGrid();
        divLLSubReport0.style.display="";
        divLLSubReport3.style.display="";
   //     divLLSubReport3.disabled="false";
		divLLSubReport1.style.display="none";
   //     divLLSubReport1.disabled="ture";
		divLLSubReport2.style.display="none";
   //     divLLSubReport2.disabled="ture";
   		cleanFmElement();
    }
}

//输入的检验
function inputCheck()
{

	if(fm.ClmNo.value == ""){
		alert("请选择一条记录！");
		cleanFmElement();
	}
}

//校验调整金额 2005-10-21 18:02 周磊
function CheckBig()
{
    if (fm.AdjSum.value == "")
    {
        fm.AdjSum.value = fm.OriSum.value;
    }
    var tOriSum = parseFloat(fm.OriSum.value);
	var tAdjSum = parseFloat(fm.AdjSum.value);
	if (tOriSum < tAdjSum)
	{
	    alert("调整金额不能大于原金额!");
	    fm.AdjSum.value = fm.OriSum.value;
	    return;
	}
}

//初始化数据
function initQuery()
{

	return;
}

//公共提交类
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
    fm.submit(); //提交
    tSaveFlag ="0";    
}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
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
        //重新查询
        queryGrid();
    }
    tSaveFlag ="0";
}

//返回按钮
function goToBack()
{
    top.close();
}


//返回按钮
function goToBack2()
{
    location.href="LLClaimAuditInput.jsp";
}

//修改操作
function saveClick()
{
    //首先进行非空字段检验
    if (beforeSubmit())
    {
        fm.action = './LLMedicalFeeAdjSave.jsp';
        submitForm();
    }
}

//提交前的校验、计算
function beforeSubmit()
{
	if(fm.ClmNo.value != ""){
		if(fm.OperationType.value == 'A' || fm.OperationType.value == 'B'){
			if(fm.AdjSum.value==""){
				alert("调整金额不能为空！");
				return;
			}
			if(fm.AdjReason.value==""){
				alert("调整原因不能为空！");
				return;
			}
			if(fm.AdjRemark.value==""){
				alert("调整备注不能为空！");
				return;
			}
		}else if(fm.OperationType.value == 'C'){
			if(fm.RealRate.value==""){
				alert("实际给付比例不能为空！");
				return;
			}
			if(fm.AdjReason1.value==""){
				alert("调整原因不能为空！");
				return;
			}
			if(fm.AdjRemark1.value==""){
				alert("调整备注不能为空！");
				return;
			}
		}else if(fm.OperationType.value == 'F' || fm.OperationType.value == 'D' || fm.OperationType.value == 'E'){
			if(fm.AdjSum1.value==""){
				alert("调整金额不能为空！");
				return;
			}
			if(fm.AdjReason2.value==""){
				alert("调整原因不能为空！");
				return;
			}
			if(fm.AdjRemark2.value==""){
				alert("调整备注不能为空！");
				return;
			}
		}
	}else if(fm.ClmNo.value == ""){
		alert("请选择一条记录！");
		return;
	}
    return true;
}