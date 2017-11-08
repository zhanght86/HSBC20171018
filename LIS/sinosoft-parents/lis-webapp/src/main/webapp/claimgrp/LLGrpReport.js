var showInfo;
var mDebug="1";
var tSaveFlag = "0";
var turnPage = new turnPageClass();
var turnPage2 = new turnPageClass();
var mySql = new SqlClass();
//开发中
function zhoulei()
{
  alert("开发中!");
//Q:who's "zhoulei"?? do u know
//A:
}

//返回按钮
function goToBack()
{
    location.href="LLGrpReportMissInput.jsp";
    if(fm.isClaimState.value == '1')
    {
     location.href="LLStateQueryInput.jsp";	
    }
}

//理赔责任控制信息
function ctrlclaimduty()
{
    var strUrl="../appgrp/CtrlClaimDuty.jsp?ProposalGrpContNo="+fm.GrpContNo.value+"&GrpContNo="+fm.GrpContNo.value+"&LoadFlag=2&LPFlag=1";
    //  window.open(strUrl,"案件核赔履历查询");
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');

    //showInfo = window.open();
    //parent.fraInterface.window.location = "../appgrp/CtrlClaimDuty.jsp?scantype="+scantype+"&MissionID="+MissionID+"&ManageCom="+ManageCom+"&SubMissionID="+SubMissionID+"&ActivityID="+ActivityID+"&NoType="+NoType+"&GrpContNo="+fm.GrpContNo.value+"&ProposalGrpContNo="+tGrpContNo+"&LoadFlag="+LoadFlag;
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
	
	
    //查询批量导入的错误信息

    //easyQueryClick();
//alert(49);
	
    var rptNo = fm.ClmNo.value;
    if(rptNo == "")
    {
        alert("传入赔案为空！");
        return;
    }

    //检索事件号(一条)
    var strSQL1 = "select LLCaseRela.CaseRelaNo,LLAccident.AccDate from LLCaseRela,LLAccident where LLCaseRela.CaseRelaNo = LLAccident.AccNo and "
                + "LLCaseRela.CaseNo = '"+rptNo+"'";
    /*mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpReportInputSql");
	mySql.setSqlId("LLGrpReportSql1");
	mySql.addSubPara(rptNo); */
//    prompt("检索事件号sql:",strSQL1);
    
    var AccNo = easyExecSql(strSQL1);
    
    //检索报案号及其他报案信息(一条)
    var strSQL2 = "select RptNo,RptorName,RptorPhone,RptorAddress,Relation,RptMode,AccidentSite,AccidentReason,RptDate,MngCom,Operator,RgtClass,GRPCONTNO,APPNTNO,PEOPLES2,GRPNAME,RiskCode,RgtObj from LLReport where "
                + "RptNo = '"+rptNo+"'";
     /*mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpReportInputSql");
	mySql.setSqlId("LLGrpReportSql2");
	mySql.addSubPara(rptNo); */
//    prompt("检索报案号及其他报案信息(一条)sql:",strSQL2);
    
    var RptContent = easyExecSql(strSQL2);
    //2007-07-03 jinsh----------------//
    //alert(74)
    var strcondoleflag="select distinct condoleflag from llsubreport where subrptno='"+rptNo+"'";
    /*mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpReportInputSql");
	mySql.setSqlId("LLGrpReportSql3");
	mySql.addSubPara(rptNo); */
    resultstrcondoleflag=easyExecSql(strcondoleflag);
    if(resultstrcondoleflag!=null)
    {
    	fm.strcondoleflag.value=resultstrcondoleflag;
    	//20070704alert("strcondoleflag"+resultstrcondoleflag);
    }
    
	//alert(83);
    //更新页面内容
    if(AccNo!=null){
    	fm.AccNo.value = AccNo[0][0];
    	fm.AccidentDate.value = AccNo[0][1];
    }

    //alert(90);alert("RptContent   "+RptContent);
    if(RptContent!=null){
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
	
	    fm.GrpContNo.value = RptContent[0][12];
	    fm.GrpCustomerNo.value = RptContent[0][13];
	    fm.Peoples.value = RptContent[0][14];
	    fm.GrpName.value = RptContent[0][15];
	    fm.RiskCode.value = RptContent[0][16];
	    fm.AccFlag.value = RptContent[0][17];
	    showOneCodeName('llrgrelation','Relation','RelationName');//报案人与事故人关系
	    showOneCodeName('llrptmode','RptMode','RptModeName');//报案方式
	    showOneCodeName('lloccurreason','occurReason','ReasonName');//出险原因
	    showOneCodeName('commendhospital','hospital','TreatAreaName');//治疗医院
    }
  


    //---------------------------------------------------*
    //更新页面权限
    //---------------------------------------------------*
    fm.AccidentDate.readonly = true;
    fm.claimType.disabled=true;
    fm.addbutton.disabled=false; 
    fm.deletebutton.disabled=false;
    //fm.QueryPerson.disabled=false;
    fm.DoHangUp.disabled=false;
    fm.CreateNote.disabled=false;
    fm.BeginInq.disabled=false;
    fm.ViewInvest.disabled=false;
     fm.Condole.disabled=false;
    fm.SubmitReport.disabled=false;
    fm.ViewReport.disabled=false;
    fm.AccidentDesc.disabled=false;
//    fm.QueryCont2.disabled=false;
//    fm.QueryCont3.disabled=false;

    //检索分案关联出险人信息(多条)
    var strSQL4 = "select count(*) from ldperson where "
                + "CustomerNo in("
                + "select CustomerNo from LLSubReport where SubRptNo = '"+ rptNo +"')";
   /* mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpReportInputSql");
	mySql.setSqlId("LLGrpReportSql4");
	mySql.addSubPara(rptNo); */
//    prompt("检索分案关联出险人信息(多条)sql:",strSQL4);
    
    var tCount = easyExecSql(strSQL4);
    
    if( tCount > 0 )
    {//alert(147);
    	var strSQL3 = "select CustomerNo,Name,"
    			+"Sex,"
    			+ " (case trim(Sex) when '0' then '男' when '1' then '女' when '2' then '不详' end) as SexNA,"
    			+ "Birthday,"
    			+ " nvl(SocialInsuFlag,0) as SocialInsuFlag,"
                + " (case when trim(nvl(SocialInsuFlag, 0)) = '1' then '有' else '无' end) as 是否有社保标志, "
                + " idtype,idno"
    			+ " from ldperson where "
                + "CustomerNo in("
                + "select CustomerNo from LLSubReport where SubRptNo = '"+ rptNo +"')";
    	/*mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLGrpReportInputSql");
		mySql.setSqlId("LLGrpReportSql5");
		mySql.addSubPara(rptNo); */
//    	prompt("从报案分案信息中查询出险人信息",strSQL3);
    	
    	easyQueryVer3Modal(strSQL3, SubReportGrid);
    	if (SubReportGrid.getRowColData(0,1) != null && SubReportGrid.getRowColData(0,1) != "")
    	{
    		SubReportGridClick(0,rptNo);
    	}//alert(164);
    }
    else
    {
    	//2008-11-01 zhangzheng 当删除掉最后一个出险人时，初始化界面出险人信息为空
    	initSubReportGrid();
    	fm.customerNo.value = "";
    	fm.customerName.value = "";
        fm.customerSex.value = "";
    	fm.SexName.value = "";
    	fm.customerAge.value = "";
    	fm.hospital.value = ""; 
        fm.TreatAreaName.value = "";
        fm.accidentDetail.value = "";
        fm.accidentDetailName.value = "";
        fm.AccResult2.value = "";
        fm.AccResult2Name.value = "";    
        fm.cureDesc.value = "";
        fm.cureDescName.value = "";
        fm.AccidentDate2.value = "";

        
        for(var j=0;j<fm.claimType.length;j++)
        {
            fm.claimType[j].checked = false;
        }

       
    }
	//alert(193);
    var strSQL5 = "select reasoncode from llreportreason where "
                + "RpNo = '"+rptNo+"'";
    /*mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLGrpReportInputSql");
		mySql.setSqlId("LLGrpReportSql6");
		mySql.addSubPara(rptNo); */
//    prompt("查询赔案理赔类型",strSQL5);
    var ReasonCode = easyExecSql(strSQL5);
    if (ReasonCode!=null&&ReasonCode!="")
    {
      fm.occurReason.value=	ReasonCode[0][0].substring(0,1);
      showCodeName('occurReason','occurReason','ReasonName');
    }


    
}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr,content,Result )
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
          
        if (Result==null||Result==""||Result==undefined)
        {
        	document.all('RptNo').value= fm.ClmNo.value;
        }
        else
        {
        		document.all('RptNo').value="";
        } 
        
        
        fm.RptConfirm.disabled = false;
        fm.addbutton.disabled = true;

        //2007-06-14saveClick();
    }
    tSaveFlag ="0";
    spanText7.disabled = true;     
    fm.addbutton.disabled = false;   
}


//jinsh------导入的执行完后调这个-------2007-07-02
function afterSubmitDiskInput( FlagStr,content,Result )
{    
	
    showInfo.close();        
   /* if (FlagStr == "Fail" )
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
    { */

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

        document.all('RptNo').value= fm.ClmNo.value;  

        fm.RptConfirm.disabled = false;

        fm.addbutton.disabled = true;    

        //var wsql="select MissionID,SubMissionID from LWMission where activityid = '0000009005' and processid = '0000000009' and missionprop1 = '"+Result+"'";
		 mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLGrpReportInputSql");
		mySql.setSqlId("LLGrpReportSql7");
		mySql.addSubPara(Result); 
//        //prompt("批量报案返回后查询工作流",wsql);
        var wsub = new Array();
        wsub=easyExecSql(mySql.getString());
        if(!(wsub==null||wsub==''))
        {
        	 document.all('MissionID').value= wsub[0][0];
             document.all('SubMissionID').value= wsub[0][1];  
        }

                 
    //}
    tSaveFlag ="0";
    spanText7.disabled = true;    
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
	    //fm.IsVip.value = "";
	    //fm.VIPValueName.value = "";
	    fm.AccidentDate.value = "";
 	    fm.occurReason.value = "";
 	    fm.ReasonName.value = "";
	    fm.hospital.value = "";
	    fm.TreatAreaName.value = "";
	    //fm.OtherAccidentDate.value = "";
	    //fm.MedicalAccidentDate.value = "";
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
	
	
	//alert("开始进行提交前校验!");
	
    //判断团体保单号
    if(fm.GrpContNo.value == ''){
     alert("团体保单号不能为空!");
     return false;
    }  
	
    //判断团体客户号
    if(fm.GrpCustomerNo.value == ''){
     alert("团体客户号不能为空!");
     return false;
    }
    //判断单位名称
    if(fm.GrpName.value == ''){
     alert("单位名称不能为空!");
     return false;
    }
     
    
    if (fm.rgtstate[0].checked == false && fm.rgtstate[1].checked == false && fm.rgtstate[2].checked == false )
    {
       fm.addbutton.disabled = false;
       alert("请选择报案类型！");
       return;	
    } 
    
    

  //获取表单域信息
    var RptNo = fm.RptNo.value;//赔案号
    var CustomerNo = fm.customerNo.value;//出险人编号
    var AccReason = fm.occurReason.value;//出险原因
    var AccDesc = fm.accidentDetail.value;//出险细节
    
    var AccDate = fm.AccidentDate.value;//事故日期

    var AccDate2 = fm.AccidentDate2.value;//出险日期

    
    var AccFlag = fm.AccFlag.value;//是否使用团体帐户金额
    
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
//alert(fm.claimType[5].checked);
//	if(fm.claimType[5].checked == true)
//    {
		//校验出险日期和事故日期
		if (checkDate(fm.AccidentDate.value,fm.AccidentDate2.value,"出险日期") == false)
		{
		     return false;
		}
//    }
    //----------------------------------------------------------BEG
    //增加报案人姓名和关系的非空校验
    //----------------------------------------------------------
   if (fm.rgtstate[0].checked == true)  
   {    
	   if (fm.RptorName.value == "" || fm.RptorName.value == null)
	   {
		   alert("请输入报案人姓名！");
		   return false;
	   }
	   if (fm.Relation.value == "" || fm.Relation.value == null)
	   {
		   alert("请输入报案人与出险人关系！");
		   return false;
	   }

    
    //----------------------------------------------------------end
    //1 检查出险人、信息
    if (checkInput1() == false)
    {
        return false;
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
    if ((fm.accidentDetail.value == null || fm.accidentDetail.value == '') && fm.occurReason.value == '1')
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
    //6 检查医院代码
    if (fm.hospital.value == '' && fm.occurReason.value == '2')
    {
        alert("出险原因为疾病,治疗医院代码不能为空！");
        return false;
    }

    //添加理赔类型中选中豁免,必须选中死亡或高残之一的判断
    //---------------------------------------------------------
    //if (fm.claimType[4].checked == true && fm.claimType[1].checked == false && fm.claimType[0].checked == false)
    //{
        //alert("选中豁免,必须选中死亡或高残之一!");
        //return false;
    //}
    
  } 
  
  else if (fm.rgtstate[1].checked==true )
 {
 		
  if (fm.RptorName.value == "" || fm.RptorName.value == null)
    {
        alert("请输入报案人姓名！");
        return false;
    }

    //1 检查出险人、信息
    if (checkInput1() == false)
    {
        return false;
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

  
    //5 检查理赔类型
    if (ClaimType == null || ClaimType == '')
    {
        alert("理赔类型不能为空！");
        return false;
    }
     
     	
 }  
 else if ( fm.rgtstate[2].checked==true )
 {
 		
    if (fm.RptorName.value == "" || fm.RptorName.value == null)
    {
        alert("请输入报案人姓名！");
        return false;
    }

    //1 检查出险人、信息
    if (checkInput1() == false)
    {
        return false;
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


  
    //5 检查理赔类型
    if (ClaimType == null || ClaimType == '')
    {
        alert("理赔类型不能为空！");
        return false;
    }
    
    //5 检查是否使用团体帐户
    if (AccFlag == null || AccFlag == '')
    {
        alert("是否使用团体帐户金额不能为空！");
        return false;
    }    
    
    //5 检查险种编码
    if (fm.RiskCode.value == null || fm.RiskCode.value == '')
    {
        alert("险种编码不能为空！");
        return false;
    }        
     
     	
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

    //alert("提交前校验结束！!");
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
	var selno = SubReportGrid.getSelNo() - 1;
	if (selno <0)
	{
	      alert("请选择要修改的客户！");
	      return;
	}
	/////////////////////////////////////////////////////////////////////////
	// * 增加出险人的出现原因必须一致的校验  09-04-17                         //
	/////////////////////////////////////////////////////////////////////////
	var tClmNo = fm.RptNo.value;
	var tCustomerNo = fm.customerNo.value;
	//var tReasonSql = "select AccidentType from LLSubReport where subrptno='"+tClmNo+"' and customerno<>'"+tCustomerNo+"'";
	mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLGrpReportInputSql");
		mySql.setSqlId("LLGrpReportSql8");
		mySql.addSubPara(tCustomerNo); 
	var occurReasonResult = easyExecSql(mySql.getString());//prompt("",tReasonSql);
	if(occurReasonResult){
		if(fm.occurReason.value!=occurReasonResult[0][0]){
			alert("所有客户的出险原因必须保持一致！");
			return false;
		}
	}
    if (confirm("您确实想修改该记录吗？"))
    {
        if (beforeSubmit() == true)
        {
              //jixf add 20051213
                /*var strSQL = " select a.polno from LCPol a, LCGrpCont b where  a.CValidate<='"+fm.AccidentDate.value+"' and a.enddate>='"+fm.AccidentDate.value+"'"
                            +" and a.GrpContNo=b.GrpContNo and insuredno='"+fm.customerNo.value+"' and a.appflag in ('1','4')";*/
                mySql = new SqlClass();
				mySql.setResourceName("claimgrp.LLGrpReportInputSql");
				mySql.setSqlId("LLGrpReportSql9");
				mySql.addSubPara(fm.AccidentDate.value); 
				mySql.addSubPara(fm.AccidentDate.value); 
				mySql.addSubPara(fm.customerNo.value); 
                if (fm.GrpCustomerNo.value!=null&&fm.GrpCustomerNo.value!='000000')
                {
                     //strSQL=strSQL+" and b.AppntNo='"+fm.GrpCustomerNo.value+"'";
                     mySql = new SqlClass();
					mySql.setResourceName("claimgrp.LLGrpReportInputSql");
					mySql.setSqlId("LLGrpReportSql10");
					mySql.addSubPara(fm.AccidentDate.value); 
					mySql.addSubPara(fm.AccidentDate.value); 
					mySql.addSubPara(fm.customerNo.value); 
					mySql.addSubPara(fm.GrpCustomerNo.value); 
                }
                
                if (fm.GrpContNo.value!=null)
                {
                   // strSQL=strSQL+" and b.GrpContNo='"+fm.GrpContNo.value+"'";
                    mySql = new SqlClass();
					mySql.setResourceName("claimgrp.LLGrpReportInputSql");
					mySql.setSqlId("LLGrpReportSql11");
					mySql.addSubPara(fm.AccidentDate.value); 
					mySql.addSubPara(fm.AccidentDate.value); 
					mySql.addSubPara(fm.customerNo.value); 
					mySql.addSubPara(fm.GrpCustomerNo.value); 
					mySql.addSubPara(fm.GrpContNo.value); 
                }
                
//                prompt("校验该客户保单未生效或这次出险日期不在该被保险人的保险期间内该客户保单未生效或这次出险日期不在该被保险人的保险期间内sql",strSQL);
                
                var arr= easyExecSql(mySql.getString());
                if ( arr == null )
                {
                     alert("该客户保单未生效或这次出险日期不在该被保险人的保险期间内！");             
                     return false;
                }

               var strSQLBQ = " select a.polno from LPEdorItem a, LCGrpCont b where  a.EdorValidate>='"+fm.AccidentDate.value+"'"
                              +" and a.GrpContNo=b.GrpContNo and a.insuredno='"+fm.customerNo.value+"'"
                              +" and a.EdorType in ('AA','PT','IC','LC')　and EdorState='0'";
                /*mySql = new SqlClass();
					mySql.setResourceName("claimgrp.LLGrpReportInputSql");
					mySql.setSqlId("LLGrpReportSql12");
					mySql.addSubPara(fm.AccidentDate.value);  
					mySql.addSubPara(fm.customerNo.value);  */           
                if (fm.GrpCustomerNo.value!=null)
                {
                     strSQLBQ=strSQLBQ+" and b.AppntNo='"+fm.GrpCustomerNo.value+"'";
                     /*mySql = new SqlClass();
					mySql.setResourceName("claimgrp.LLGrpReportInputSql");
					mySql.setSqlId("LLGrpReportSql13");
					mySql.addSubPara(fm.AccidentDate.value);  
					mySql.addSubPara(fm.customerNo.value);     
					mySql.addSubPara(fm.GrpCustomerNo.value);    */ 
                }
                
                if (fm.GrpContNo.value!=null)
                {
                    strSQLBQ=strSQLBQ+" and b.GrpContNo='"+fm.GrpContNo.value+"'";
                   /*  mySql = new SqlClass();
					mySql.setResourceName("claimgrp.LLGrpReportInputSql");
					mySql.setSqlId("LLGrpReportSql14");
					mySql.addSubPara(fm.AccidentDate.value);  
					mySql.addSubPara(fm.customerNo.value);     
					mySql.addSubPara(fm.GrpCustomerNo.value);   
					mySql.addSubPara(fm.GrpContNo.value);  */
                }
                
//                prompt("严重警告：该被保险人在出险日期之后做过可能更改保额的保全sql",strSQLBQ);
                var arrbq= easyExecSql(strSQLBQ);
                if ( arrbq != null )
                {
                     alert("严重警告：该被保险人在出险日期之后做过可能更改保额的保全！");             
                }
               
                var trgtstate;
                for (var i = 0; i < fm.rgtstate.length; i++)
                {
                	if (fm.rgtstate[i].checked==true)
                	{
                		trgtstate=fm.rgtstate[i].value;
                		//alert("trgtstate:"+trgtstate);
                	} 
                }                               
            
	            fm.fmtransact.value = "update";
	            fm.action = "LLGrpReportSave.jsp?rgtstate="+trgtstate; 
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
    //alert("您无法进行删除操作！");
	var selno = SubReportGrid.getSelNo() - 1;
	if (selno <0)
	{
	      alert("请选择待删除的客户！");
	      return;
	}

	
    if (confirm("您确实想删除该记录吗？"))
    {
        if (beforeSubmit() == true)
        {
              //jixf add 20051213

                /*var strSQLBQ = " select a.polno from LPEdorItem a, LCGrpCont b where  a.EdorValidate>='"+fm.AccidentDate.value+"'"
                             +" and a.GrpContNo=b.GrpContNo and a.insuredno='"+fm.customerNo.value+"'"
                             +" and a.EdorType in ('AA','PT','IC','LC')　and EdorState='0'"*/
               mySql = new SqlClass();
					mySql.setResourceName("claimgrp.LLGrpReportInputSql");
					mySql.setSqlId("LLGrpReportSql15");
					mySql.addSubPara(fm.AccidentDate.value);  
					mySql.addSubPara(fm.customerNo.value);     
					
                if (fm.GrpCustomerNo.value!=null)
                {
                    //strSQLBQ=strSQLBQ+" and b.AppntNo='"+fm.GrpCustomerNo.value+"'"
                    mySql = new SqlClass();
					mySql.setResourceName("claimgrp.LLGrpReportInputSql");
					mySql.setSqlId("LLGrpReportSql16");
					mySql.addSubPara(fm.AccidentDate.value);  
					mySql.addSubPara(fm.customerNo.value);   
					mySql.addSubPara(fm.GrpCustomerNo.value);   
                }
                
                if (fm.GrpContNo.value!=null)
                {
                    //strSQLBQ=strSQLBQ+" and b.GrpContNo='"+fm.GrpContNo.value+"'"
                    mySql = new SqlClass();
					mySql.setResourceName("claimgrp.LLGrpReportInputSql");
					mySql.setSqlId("LLGrpReportSql17");
					mySql.addSubPara(fm.AccidentDate.value);  
					mySql.addSubPara(fm.customerNo.value);   
					mySql.addSubPara(fm.GrpCustomerNo.value);  
					mySql.addSubPara(fm.GrpContNo.value);  
                }
                
                //prompt("判断出险人是否做过保全的校验:",strSQLBQ);

                var arrbq= easyExecSql(mySql.getString());

                if ( arrbq != null )
                {
                    alert("严重警告：该被保险人在出险日期之后做过可能更改保额的保全！");             
                }
             
                fm.fmtransact.value = "delete";
                fm.action = './LLGrpReportSave.jsp';
                submitForm();
        }
    }
    else
    {
        mOperate="";
    }
    
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
      alert("请选择一条客户信息！");
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
  var tClmNo = fm.RptNo.value;
  if (tCustomerNo == "")
  {
      alert("请选择一条客户信息！");
      return;
  }
    var strUrl="LLLClaimQueryMain.jsp?AppntNo="+tCustomerNo+"&ClmNo="+tClmNo;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//卡单信息查询
function showCard(){
    var strUrl="../card/CardContInput.jsp?flag=1";//flag = 1 卡单查询里的Button都不显示
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
    fm.SexName.value = arr[3];
    
    fm.customerAge.value = arr[4];
    
    //alert("fm.customerSex.value: "+fm.customerSex.value)
    //alert("fm.SexName.value: "+fm.SexName.value)

    
    //团体投保人信息
    fm.GrpCustomerNo.value=arr[8];
    fm.GrpName.value=arr[9];
    fm.GrpContNo.value=arr[10];
    
    //fm.IsVip.value = '';
    showOneCodeName('sex','customerSex','SexName');//性别
   //初始化录入域
    fm.hospital.value = ""; 
    fm.TreatAreaName.value = "";
    fm.accidentDetail.value = "";
    fm.accidentDetailName.value = "";
    fm.AccResult2.value = "";
    fm.AccResult2Name.value = "";    
    fm.cureDesc.value = "";
    fm.cureDescName.value = "";
    for(var j=0;j<fm.claimType.length;j++)
    {
        fm.claimType[j].checked = false;
    }
    //设置可操作按钮
    fm.addbutton.disabled = false;
    fm.deletebutton.disabled = false;
    fm.disabled = false;
//    fm.QueryCont3.disabled = false;

}

//出险人查询
function showInsPerQuery()
{
	
      var tGrpContNo  = fm.GrpContNo.value ;

      var tGrpCustomerNo = fm.GrpCustomerNo.value;

      var tGrpName = fm.GrpName.value;
      
      if (fm.rgtstate[0].checked == false && fm.rgtstate[1].checked == false && fm.rgtstate[2].checked == false )
      {
         fm.addbutton.disabled = false;
         alert("请先选择报案类型！");
         return;	
      } 
      
      if(tGrpContNo == "")
      {
    	  	 alert("请先输入团体保单号!");
             return;
      }
      else
      {
            var strUrl = "LLGrpLdPersonQueryMain.jsp?GrpContNo="+tGrpContNo+"&GrpCustomerNo="+tGrpCustomerNo+"&GrpName="+tGrpName+"&ManageCom="+document.allManageCom.value;
            //window.open(strUrl);
            window.open(strUrl,"","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
      }

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
    var row = SubReportGrid.getSelNo();
    if(row < 1)
    {
        alert("请选中一条客户信息！");
        return;
    }
    var tClaimNo = fm.RptNo.value;         //赔案号
    var tCaseNo = fm.RptNo.value;          //分案号
    var tCustNo = fm.customerNo.value;          //客户号
    if (tCustNo == "")
    {
    	alert("请选择客户！");
    	return;
    }
  
    var claimTypes=getClaimTypes();

    var strUrl="LLRptMAffixListMain.jsp?claimTypes="+claimTypes+"&CaseNo="+tCaseNo+"&whoNo="+tCustNo+"&Proc=Rpt";
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
    
   // var JustStateSql="select COUNT(*) from lwmission where activityid in ('0000009125','0000009145','0000009165','0000009175') and missionprop1='"+fm.RptNo.value+"'";
     mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpReportInputSql");
	mySql.setSqlId("LLGrpReportSql18");
	mySql.addSubPara(fm.RptNo.value);  
    var JustStateCount=easyExecSql(mySql.getString());
    if(parseInt(JustStateCount)>0)
    {      				
    		alert("该案件已经发起调查，请不要重复调查!");
    		return;
    }    
    
    var strUrl="../claimgrp/LLInqApplyMain.jsp?claimNo="+fm.RptNo.value+"&custNo="+fm.customerNo.value+"&custName="+fm.customerName.value+/*"&custVip="+fm.IsVip.value+*/"&initPhase=01";
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
  //---------------------------------------
  //判断该赔案是否存在调查
  //---------------------------------------
   /* var strSQL = "select count(1) from LLInqConclusion where "
                + "ClmNo = '" + fm.RptNo.value+"'";*/
    mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpReportInputSql");
	mySql.setSqlId("LLGrpReportSql19");
	mySql.addSubPara(fm.RptNo.value);  
    var tCount = easyExecSql(mySql.getString());
    if (tCount == "0")
    {
        alert("该赔案还没有调查信息！");
        return;
    }
    var strUrl="LLInqQueryMain.jsp?claimNo="+fm.RptNo.value;
      window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
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
    /*mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpReportInputSql");
	mySql.setSqlId("LLGrpReportSql20");
	mySql.addSubPara(fm.RptNo.value); 
	mySql.addSubPara(fm.customerNo.value);   */
    var tCondoleFlag = easyExecSql(strSQL);
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

  var strUrl="LLSubmitApplyMain.jsp?claimNo="+fm.RptNo.value+"&custNo="+fm.customerNo.value+"&custName="+fm.customerName.value//+"&custVip="+fm.IsVip.value;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');

}

//打开呈报查询
function showQuerySubmit()
{
    //---------------------------------------
    //判断该赔案是否存在呈报
    //---------------------------------------
    /*var strSQL = "select count(1) from LLSubmitApply where "
                + "ClmNo = " + fm.RptNo.value;*/
    mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpReportInputSql");
	mySql.setSqlId("LLGrpReportSql21");
	mySql.addSubPara(fm.RptNo.value); 
	 
    var tCount = easyExecSql(mySql.getString());
    //alert(tCount);
    if (tCount == "0")
    {
        alert("该赔案还没有呈报信息！");
        return;
    }
    
    var strUrl="LLSubmitQueryMain.jsp?claimNo="+fm.RptNo.value;
      window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
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
	//alert("1113-checkInput1");
	
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
        alert("事故日期不能为空！");
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

                alert("事故日期不能晚于当前日期");
                return false;
            }
            else if (AccMonth == mNowMonth && AccDay > mNowDay)
            {
                alert("事故日期不能晚于当前日期!");
                return false;
            }
        }
    }
    
    //alert("1113-checkInput1-End!");
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
   /* var strSQL1 = "select AccNo from LLAccident where "
                + "AccDate = to_date('"+fm.AccidentDate.value
                + "','yyyy-mm-dd') "
                + getWherePart( 'AccType','occurReason' )
                + " and AccNo in (select AccNo from LLAccidentSub where 1=1 "
                + getWherePart( 'CustomerNo','customerNo' )
                + ")";*/
	mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpReportInputSql");
	mySql.setSqlId("LLGrpReportSql22");
	mySql.addSubPara(fm.AccidentDate.value); 
	mySql.addSubPara(fm.occurReason.value); 
	mySql.addSubPara(fm.customerNo.value); 
    AccNo = decodeEasyQueryResult(easyQueryVer3(mySql.getString()));
    if (AccNo == null )
    {
        fm.isReportExist.value = "false";
        alert("报案不存在!");
        return
    }
    else
    {
        //检索报案号及其他报案信息(一条)
       /* var strSQL2 = "select RptNo,RptorName,RptorPhone,RptorAddress,Relation,RptMode,AccidentSite,AccidentReason,RptDate,MngCom,Operator,RgtClass from LLReport where "
                    + "RptNo in (select CaseNo from LLCaseRela where "
                    + "CaseRelaNo = '"+ AccNo +"' and SubRptNo in (select SubRptNo from LLSubReport where 1=1 "
                    + getWherePart( 'CustomerNo','customerNo' )
                    + "))";*/
        mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpReportInputSql");
	mySql.setSqlId("LLGrpReportSql23");
	mySql.addSubPara(AccNo); 
	mySql.addSubPara(fm.customerNo.value); 
        RptContent = decodeEasyQueryResult(easyQueryVer3(mySql.getString()));
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
            //更新页面权限
            fm.AccidentDate.readonly = true;
            fm.claimType.disabled=true;

            fm.addbutton.disabled=false;
            fm.deletebutton.disabled=false;
            //fm.QueryPerson.disabled=false;
            fm.DoHangUp.disabled=false;
            fm.CreateNote.disabled=false;
            fm.BeginInq.disabled=false;
            fm.ViewInvest.disabled=false;
            fm.Condole.disabled=false;
            fm.SubmitReport.disabled=false;
            fm.ViewReport.disabled=false;
            fm.AccidentDesc.disabled=false;
//            fm.QueryCont2.disabled=false;
//            fm.QueryCont3.disabled=false;
        //出险原因校验
        checkOccurReason();

            //检索分案关联出险人信息(多条)
           /* var strSQL3 = " select CustomerNo,Name,"
                        + " Sex,"
                        + " (case trim(Sex) when '0' then '男' when '1' then '女' when '2' then '不详' end) as SexNA,"
                        + " Birthday,"
                        + " nvl(SocialInsuFlag,0) as SocialInsuFlag,"
                        + " (case when trim(nvl(SocialInsuFlag, 0)) = '1' then '有' else '无' end) as 是否有社保标志, "
                        + " idtype,idno"
                        + " from LDPerson where "
                        + " CustomerNo in("
                        + " select CustomerNo from LLSubReport where SubRptNo = '"+ RptContent[0][0] +"')";*/
//            personInfo = decodeEasyQueryResult(easyQueryVer3(strSQL3));
	mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpReportInputSql");
	mySql.setSqlId("LLGrpReportSql24");
	mySql.addSubPara(RptContent[0][0]); 
	
            easyQueryVer3Modal(mySql.getString(), SubReportGrid);
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
    /*var strSQL = "select AccNo from LLAccident where "
                + "AccDate = to_date('"+fm.AccidentDate.value
                + "','yyyy-mm-dd') and AccNo in (select AccNo from LLAccidentSub where 1=1 "
                + getWherePart( 'CustomerNo','customerNo' )
//                + getWherePart( 'AccType','occurReason' )
                + ")";*/
  mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpReportInputSql");
	mySql.setSqlId("LLGrpReportSql25");
	mySql.addSubPara(fm.AccidentDate.value); 
	mySql.addSubPara(fm.customerNo.value); 
    var tAccNo = easyExecSql(mySql.getString());
    if (tAccNo == null)
    {
        alert("没有相关事件！");
        return;
    }
    //打开事件查询窗口
//  var strUrl="LLAccidentQueryMain.jsp?AccDate="+fm.AccidentDate.value+"&CustomerNo="+fm.customerNo.value+"&AccType="+fm.occurReason.value;
  var strUrl="LLAccidentQueryMain.jsp?AccDate="+fm.AccidentDate.value+"&CustomerNo="+fm.customerNo.value;
window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
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

	/////////////////////////////////////////////////////////////////////////
	// * 增加出险人的出现原因必须一致的校验  09-04-17                         //
	/////////////////////////////////////////////////////////////////////////
	var tClmNo = fm.RptNo.value;
	var tCustomerNo = fm.customerNo.value;
	var tReasonSql = "select AccidentType from LLSubReport where subrptno='"+tClmNo+"' and customerno<>'"+tCustomerNo+"'";
	/*mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpReportInputSql");
	mySql.setSqlId("LLGrpReportSql26");
	mySql.addSubPara(tClmNo); 
	mySql.addSubPara(tCustomerNo); */
	var occurReasonResult = easyExecSql(tReasonSql);//prompt("",tReasonSql);
	if(occurReasonResult){
		if(fm.occurReason.value!=occurReasonResult[0][0]){
			alert("所有客户的出险原因必须保持一致！");
			return false;
		}
	}
    //首先进行非空字段检验
    if (beforeSubmit() == true)
    {
    	
        if (fm.rgtstate[0].checked == false && fm.rgtstate[1].checked == false && fm.rgtstate[2].checked == false )
        {
           fm.addbutton.disabled = false;
           alert("请选择报案类型！");
           return;	
        } 

      /*================================================================
         * 修改原因：增加对被保险人有已报案数据的赔案判断不允许报案
         * 修 改 人：wood
         * 修改日期：2007-6-15
         *=================================================================
         */
       //----------------jinsh--2007-06-12--避免重复报案-------------------------//
      // var repsql="select a.subrptno from llsubreport a ,llreport b where a.subrptno=b.rptno and b.grpcontno='"+fm.GrpContNo.value+"' and a.customerno='"+fm.customerNo.value+"' and not exists ( select 'X' from llregister where rgtno = a.subrptno )  ";
       mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLGrpReportInputSql");
		mySql.setSqlId("LLGrpReportSql27");
		mySql.addSubPara(fm.GrpContNo.value); 
		mySql.addSubPara(fm.customerNo.value); 
       var arr1= easyExecSql(mySql.getString());
       if(arr1 != null&&arr1!="")
       {  
             fm.addbutton.disabled = false;
             alert("该出险人在 " + arr1 + "赔案下有报案数据，请不要重复报案！");
             return false;
       }
       //----------------jinsh--2007-06-12--避免重复报案-------------------------//         

       /*================================================================
         * 修改原因：增加对被保险人有未结案的赔案判断不允许报案
         * 修 改 人：L.Y
         * 修改日期：2006-8-16
         *=================================================================
         */
     //add by wood 增加b.clmstate<>'70'，主要考虑不予立案后会在llcase表下有数据并且rgtstate='20'
        //var StrSQL = "select a.caseno from llcase a, llregister b where a.rgtno=b.rgtno and b.grpcontno='"+fm.GrpContNo.value+"' and a.customerno = '"+fm.customerNo.value+"' and  b.clmstate not in ('60','80','50','70') and rownum<=1";
       mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLGrpReportInputSql");
		mySql.setSqlId("LLGrpReportSql28");
		mySql.addSubPara(fm.GrpContNo.value); 
		mySql.addSubPara(fm.customerNo.value); 
        var arr= easyExecSql(mySql.getString());
        if(arr != null&&arr!=""){
         //  showInfo.close();
           fm.addbutton.disabled = false;
           alert("该出险人在 " + arr + " 赔案下有未结案件，请结案后再做新增！");
           return false;
        }
        
        /*var MstrSQL = " select a.polno from LCPol a, LCGrpCont b where  a.CValidate<='"+fm.AccidentDate2.value+"' and a.enddate>='"+fm.AccidentDate2.value+"'"
        +" and a.GrpContNo=b.GrpContNo and insuredno='"+fm.customerNo.value+"' and a.appflag in ('1','4')"*/
        //alert(strSQL);
        mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLGrpReportInputSql");
		mySql.setSqlId("LLGrpReportSql29");
		mySql.addSubPara(fm.AccidentDate2.value); 
		mySql.addSubPara(fm.AccidentDate2.value); 
		mySql.addSubPara(fm.customerNo.value); 
        if (fm.GrpCustomerNo.value!=null&&fm.GrpCustomerNo.value!='000000')
        {
        	//MstrSQL=MstrSQL+" and b.AppntNo='"+fm.GrpCustomerNo.value+"'"
        	mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLGrpReportInputSql");
		mySql.setSqlId("LLGrpReportSql30");
		mySql.addSubPara(fm.AccidentDate2.value); 
		mySql.addSubPara(fm.AccidentDate2.value); 
		mySql.addSubPara(fm.customerNo.value); 
		mySql.addSubPara(fm.GrpCustomerNo.value); 
        }
        
        if (fm.GrpContNo.value!=null)
        {
        	//MstrSQL=MstrSQL+" and b.GrpContNo='"+fm.GrpContNo.value+"'"
        	mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLGrpReportInputSql");
		mySql.setSqlId("LLGrpReportSql31");
		mySql.addSubPara(fm.AccidentDate2.value); 
		mySql.addSubPara(fm.AccidentDate2.value); 
		mySql.addSubPara(fm.customerNo.value); 
		mySql.addSubPara(fm.GrpCustomerNo.value); 
		mySql.addSubPara(fm.GrpContNo.value); 
        }

        var arr= easyExecSql(mySql.getString());
		if ( arr == null )
		{
			MstrSQL = " select a.polno from LCPol a, LCGrpCont b where  a.CValidate<='"+fm.AccidentDate2.value+"' and a.enddate>='"+fm.AccidentDate2.value+"'"
	        +" and a.GrpContNo=b.GrpContNo and a.insuredno='"+fm.customerNo.value+"' and a.appflag in ('1','4')"
	        +"union select a.polno from LCPol a, LCGrpCont b, lcinsuredrelated c where  a.CValidate<='"+fm.AccidentDate2.value+"' and a.enddate>='"+fm.AccidentDate2.value+"'"
	        +" and a.GrpContNo=b.GrpContNo  and a.polno=c.polno and c.customerno='"+fm.customerNo.value+"' and a.appflag in ('1','4')";
	        /*mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLGrpReportInputSql");
		mySql.setSqlId("LLGrpReportSql32");
		mySql.addSubPara(fm.AccidentDate2.value); 
		mySql.addSubPara(fm.AccidentDate2.value); 
		mySql.addSubPara(fm.customerNo.value); 
		mySql.addSubPara(fm.AccidentDate2.value); 
		mySql.addSubPara(fm.AccidentDate2.value); 
		mySql.addSubPara(fm.customerNo.value); */
	        //alert(strSQL);
	        if (fm.GrpCustomerNo.value!=null&&fm.GrpCustomerNo.value!='000000')
	        {
	        	MstrSQL=MstrSQL+" and b.AppntNo='"+fm.GrpCustomerNo.value+"'"
	        	/*mySql = new SqlClass();
				mySql.setResourceName("claimgrp.LLGrpReportInputSql");
				mySql.setSqlId("LLGrpReportSql33");
				mySql.addSubPara(fm.AccidentDate2.value); 
				mySql.addSubPara(fm.AccidentDate2.value); 
				mySql.addSubPara(fm.customerNo.value); 
				mySql.addSubPara(fm.AccidentDate2.value); 
				mySql.addSubPara(fm.AccidentDate2.value); 
				mySql.addSubPara(fm.customerNo.value); 
				mySql.addSubPara(fm.GrpCustomerNo.value); */
	        }
	        
	        if (fm.GrpContNo.value!=null)
	        {
	        	MstrSQL=MstrSQL+" and b.GrpContNo='"+fm.GrpContNo.value+"'"
	        	/*mySql = new SqlClass();
				mySql.setResourceName("claimgrp.LLGrpReportInputSql");
				mySql.setSqlId("LLGrpReportSql34");
				mySql.addSubPara(fm.AccidentDate2.value); 
				mySql.addSubPara(fm.AccidentDate2.value); 
				mySql.addSubPara(fm.customerNo.value); 
				mySql.addSubPara(fm.AccidentDate2.value); 
				mySql.addSubPara(fm.AccidentDate2.value); 
				mySql.addSubPara(fm.customerNo.value); 
				mySql.addSubPara(fm.GrpCustomerNo.value); 
				mySql.addSubPara(fm.GrpContNo.value); */
	        }
	        
	        //prompt("",MstrSQL);
	        arr= easyExecSql(MstrSQL);
	        
			if ( arr == null )
			{
				alert("该客户保单未生效或这次出险日期不在该被保险人的保险期间内,不能新增出险人！");             
				return false;
			}
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
				mySql.setResourceName("claimgrp.LLGrpReportInputSql");
				mySql.setSqlId("LLGrpReportSql35");
				mySql.addSubPara(fm.customerNo.value); 
				mySql.addSubPara(fm.GrpContNo.value); 
				
        if(fm.RiskCode.value != ''){
           // SqlPol += " and riskcode = '"+fm.RiskCode.value+"'";
             mySql = new SqlClass();
				mySql.setResourceName("claimgrp.LLGrpReportInputSql");
				mySql.setSqlId("LLGrpReportSql36");
				mySql.addSubPara(fm.customerNo.value); 
				mySql.addSubPara(fm.GrpContNo.value); 
				mySql.addSubPara(fm.RiskCode.value); 
             }
        var tPolState = easyExecSql(mySql.getString());
        if(tPolState > 0){
          // showInfo.close();
           fm.addbutton.disabled = false;
           alert("该出险人已经做了生存给付，不允许做新增！");
           return false;
         }       
         
         /*对保单号和客户号进行校验
         *2007-02-13 L。Y 
         */        
        var StrSQL = "select count(*) from lcgrpcont where appntno='"+fm.GrpCustomerNo.value+"' and grpcontno='"+fm.GrpContNo.value+"' ";
        /* mySql = new SqlClass();
				mySql.setResourceName("claimgrp.LLGrpReportInputSql");
				mySql.setSqlId("LLGrpReportSql37");
				mySql.addSubPara(fm.customerNo.value); 
				mySql.addSubPara(fm.GrpContNo.value); */
			
        var arr= easyExecSql(StrSQL);
        if(arr == 0){
        //   showInfo.close();
           fm.addbutton.disabled = false;
           alert("该团体客户号与团体保单号不匹配，请修改后再操作！");
           return false;
        }
        /*===================================================================
         * 修改原因：增加对出险人是否作过死亡理赔的判断，如作过不允许再作理赔
         * 修 改 人：JINSH
         * 修改日期：2007-05-17
         *===================================================================
         */
        ///////////////////////////////////////////////////////////////////////////////
        //					老系统中会去判断polstate 6.5不用，故注释掉				 //
        //					直接去查ldperson中的死亡日期是否为空						 //
        //        var Polsqlflag = " select count(*) From lcpol where polstate = '7'"//
        //                   + " and  insuredno = '"+fm.customerNo.value+"'"		 //
        //                   + " and  grpcontno = '"+fm.GrpContNo.value+"'";		 //
        //                   //alert(fm.customerNo.value);							 //
        ///////////////////////////////////////////////////////////////////////////////
//        var Polsqlflag = " select count(*) From ldperson where DeathDate is not null"
//        	+ " and  customerno = '"+fm.customerNo.value+"'";
//        	//+ " and  grpcontno = '"+fm.GrpContNo.value+"'";
//        //alert(fm.customerNo.value);
//        var Polflag = easyExecSql(Polsqlflag);
//        if(Polflag > 0)
//        {
//          // showInfo.close();
//           fm.addbutton.disabled = false;
//           alert("该出险人"+fm.customerNo.value+"已经做了死亡理赔，不允许做新增！");
//           return false;
//        }

         /*================================================================
         * 修改原因：由于个险和团险理赔分别进行，所以注释掉上面的校验逻辑使用如下的校验逻辑
         * 修 改 人：WK
         * 修改日期：2010-1-27
         *=================================================================
         */
		//var strSQL = "select deathdate from LDPerson where CustomerNo = '" + fm.customerNo.value + "'";
     mySql = new SqlClass();
				mySql.setResourceName("claimgrp.LLGrpReportInputSql");
				mySql.setSqlId("LLGrpReportSql38");
				mySql.addSubPara(fm.customerNo.value); 
				
    var tDeathDate = easyExecSql(mySql.getString());
    var tAccDate = fm.AccidentDate2.value;
    if (tDeathDate != null && tDeathDate != "")
    {
        if (dateDiff(tAccDate,tDeathDate[0][0],'D') < 0)
        {
            alert("客户"+fm.customerName.value+"已被确认于"+tDeathDate+"身故，此日期以后的赔案不予受理！");
            return;
        }
        else
        {
            if (!confirm("客户"+fm.customerName.value+"已被确认于"+tDeathDate+"身故，是否继续进行？"))
            {
                return;
            }
        }
    }
        
        /*================================================================
         * 修改原因：增加对保全的判断，保全未确认和退保的不允许理赔
         * 修 改 人：P.D
         * 修改日期：2006-8-14
         *=================================================================
         */
        //var SqlBq = "select  count(*) from LPEdorItem where grpcontno = '"+fm.GrpContNo.value+"' and insuredno = '"+fm.customerNo.value+"' and edorstate != '0' and edortype = 'CT'";
        mySql = new SqlClass();
				mySql.setResourceName("claimgrp.LLGrpReportInputSql");
				mySql.setSqlId("LLGrpReportSql39");
				mySql.addSubPara(fm.GrpContNo.value); 
				mySql.addSubPara(fm.customerNo.value); 
        var tEdorState = easyExecSql(mySql.getString());
        var sqlC = "select count(*) from lmriskapp where "
                 + "riskcode in (select riskcode From lcpol where "
                 + "grpcontno = '"+fm.GrpContNo.value+"' and insuredno = '"+fm.customerNo.value+"') "
                 + "and RiskPeriod = 'L'";
         /*mySql = new SqlClass();
				mySql.setResourceName("claimgrp.LLGrpReportInputSql");
				mySql.setSqlId("LLGrpReportSql40");
				mySql.addSubPara(fm.GrpContNo.value); 
				mySql.addSubPara(fm.customerNo.value); */
        var tcount = easyExecSql(sqlC);
        if(tEdorState > 0 && tcount > 0 ){
          // showInfo.close();
           fm.addbutton.disabled = false;
           alert("该出险人有未确认的保全或已经退保，请确认后在做新增！");
           return false;
        }

        //判断区分保存报案还是保存出险人
        
        //jixf add 20051213
       /* var strSQL = " select a.polno from LCPol a, LCGrpCont b where  a.CValidate<='"+fm.AccidentDate.value+"' and a.enddate>='"+fm.AccidentDate.value+"'";
        strSQL=strSQL+" and a.GrpContNo=b.GrpContNo and insuredno='"+fm.customerNo.value+"' and a.appflag in ('1','4')";*/
       	mySql = new SqlClass();
				mySql.setResourceName("claimgrp.LLGrpReportInputSql");
				mySql.setSqlId("LLGrpReportSql41");
				mySql.addSubPara(fm.AccidentDate.value); 
				mySql.addSubPara(fm.AccidentDate.value); 
				mySql.addSubPara(fm.customerNo.value); 
        if (fm.GrpCustomerNo.value!=null&&fm.GrpCustomerNo.value!='000000')
        {
         // strSQL=strSQL+" and b.AppntNo='"+fm.GrpCustomerNo.value+"'";
          mySql = new SqlClass();
				mySql.setResourceName("claimgrp.LLGrpReportInputSql");
				mySql.setSqlId("LLGrpReportSql42");
				mySql.addSubPara(fm.AccidentDate.value); 
				mySql.addSubPara(fm.AccidentDate.value); 
				mySql.addSubPara(fm.customerNo.value); 
				mySql.addSubPara(fm.GrpCustomerNo.value); 
        }
        if (fm.GrpContNo.value!=null)
        {
          //strSQL=strSQL+" and b.GrpContNo='"+fm.GrpContNo.value+"'";
           mySql = new SqlClass();
				mySql.setResourceName("claimgrp.LLGrpReportInputSql");
				mySql.setSqlId("LLGrpReportSql43");
				mySql.addSubPara(fm.AccidentDate.value); 
				mySql.addSubPara(fm.AccidentDate.value); 
				mySql.addSubPara(fm.customerNo.value); 
				mySql.addSubPara(fm.GrpCustomerNo.value); 
				mySql.addSubPara(fm.GrpContNo.value); 
        }        
        var arr= easyExecSql(mySql.getString());
        
    	  //var tsql="select distinct riskcode from lcgrppol where grpcontno='"+fm.GrpContNo.value+"' ";
       	mySql = new SqlClass();
				mySql.setResourceName("claimgrp.LLGrpReportInputSql");
				mySql.setSqlId("LLGrpReportSql44");
				mySql.addSubPara(fm.GrpContNo.value); 
        if(fm.RiskCode.value != ''){
            //tsql += " and riskcode = '"+fm.RiskCode.value+"'";
            mySql = new SqlClass();
				mySql.setResourceName("claimgrp.LLGrpReportInputSql");
				mySql.setSqlId("LLGrpReportSql45");
				mySql.addSubPara(fm.GrpContNo.value); 
				mySql.addSubPara(fm.RiskCode.value); 
             }        
        var tsub=new Array;
        tsub=easyExecSql(mySql.getString());
      
//       if (tsub != null)
//       {
//         for (var i=0; i < tsub.length; i++)
//         {
//          //alert("tsub[i]:"+tsub[i]);   
//          	var accSql="select risktype6 from lmriskapp where riskcode='"+fm.RiskCode.value+"' ";
//          	prompt("accSql:",accSql);
//	          var tRiskType6 = easyExecSql(accSql);	
//	                       
//          if (tRiskType6 != '8') //目前有307的团单下只有307一个险种,risktype6='8'代表307类险种
//          {
//            if(arr == null||arr=="")
//            {
//         //    showInfo.close();
//               fm.addbutton.disabled = false;
//               alert("该保单未生效或这次出险日期不在该被保险人的保险期间内！");         
//               return false;
//            }
//           }
//          }
//        }
        
        var strSQLBQ = " select a.polno from LPEdorItem a, LCGrpCont b where  a.EdorValidate>='"+fm.AccidentDate2.value+"'";
        strSQLBQ=strSQLBQ+" and a.GrpContNo=b.GrpContNo and a.insuredno='"+fm.customerNo.value+"'";
        strSQLBQ=strSQLBQ+" and a.EdorType in ('AA','PT','IC','LC')　and EdorState='0'";
        /*mySql = new SqlClass();
				mySql.setResourceName("claimgrp.LLGrpReportInputSql");
				mySql.setSqlId("LLGrpReportSql46");
				mySql.addSubPara(fm.AccidentDate2.value); 
				mySql.addSubPara(fm.customerNo.value); */
        if (fm.GrpCustomerNo.value!=null)
        {
           strSQLBQ=strSQLBQ+" and b.AppntNo='"+fm.GrpCustomerNo.value+"'";
           /* mySql = new SqlClass();
				mySql.setResourceName("claimgrp.LLGrpReportInputSql");
				mySql.setSqlId("LLGrpReportSql47");
				mySql.addSubPara(fm.AccidentDate2.value); 
				mySql.addSubPara(fm.customerNo.value); 
				mySql.addSubPara(fm.GrpCustomerNo.value); */
        }
        if (fm.GrpContNo.value!=null)
        {
            strSQLBQ=strSQLBQ+" and b.GrpContNo='"+fm.GrpContNo.value+"'";
            /*mySql = new SqlClass();
				mySql.setResourceName("claimgrp.LLGrpReportInputSql");
				mySql.setSqlId("LLGrpReportSql48");
				mySql.addSubPara(fm.AccidentDate2.value); 
				mySql.addSubPara(fm.customerNo.value); 
				mySql.addSubPara(fm.GrpCustomerNo.value); 
				mySql.addSubPara(fm.GrpContNo.value); */
        }
        
       var arrbq= easyExecSql(mySql.getString());
       if ( arrbq != null )
       {
         //  showInfo.close();
           fm.addbutton.disabled = false;
          alert("严重警告：该被保险人在出险日期之后做过可能更改保额的保全！");             
       }
               
       
         //-----------------jinsh--20070517------------------如果是账户--查询lcinsureaccclass表--//
        if (fm.rgtstate[2].checked == true )
        {
        		//alert("OK");
          	//var QueryStrSql="select count(*) from lcinsureaccclass where grpcontno='"+fm.GrpContNo.value+"' and insuredno='"+fm.customerNo.value+"'";
         		 mySql = new SqlClass();
				mySql.setResourceName("claimgrp.LLGrpReportInputSql");
				mySql.setSqlId("LLGrpReportSql49");
				mySql.addSubPara(fm.GrpContNo.value); 
				mySql.addSubPara(fm.customerNo.value); 
         		var QueryNum=easyExecSql(mySql.getString());
         		if(QueryNum=='0'||QueryNum==null)
         		{
           //     showInfo.close();
           			fm.addbutton.disabled = false;
         				alert("该出险人"+fm.customerNo.value+"没有账户信息!不允许做新增！");
         				return false;
         		}
			       //var StringSQL="select * from lcinsureaccclass where grpcontno='"+fm.GrpContNo.value+"' and riskcode='"+fm.RiskCode.value+"'";
			        mySql = new SqlClass();
					mySql.setResourceName("claimgrp.LLGrpReportInputSql");
					mySql.setSqlId("LLGrpReportSql50");
					mySql.addSubPara(fm.GrpContNo.value); 
					mySql.addSubPara(fm.RiskCode.value); 
			       var ForbidResult=easyExecSql(mySql.getString());
			       //alert(ForbidResult);
			       if(ForbidResult==null)
			       {			
            //      showInfo.close();
           			  fm.addbutton.disabled = false;
			       			alert("该险种"+fm.RiskCode.value+"在账户表中不存在，不能做帐户理赔 ！");
			       			return false;
			       }
            // var StrAccSQL = "select rgtno from llregister  where grpcontno = '"+fm.GrpContNo.value+"' and clmstate not in ('60', '80', '50', '70') and rownum<=1 "; //如果加只报案没立案的限制，在添加第二个人时就会添加不了
             mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpReportInputSql");
			mySql.setSqlId("LLGrpReportSql51");
			mySql.addSubPara(fm.GrpContNo.value); 
             var arr2= easyExecSql(mySql.getString());
             if(arr2 > 0)
             {
            //    	showInfo.close();
           				fm.addbutton.disabled = false;
                 alert("该保单有未结案件" + arr2 + "，请结案后再做新增！");
                 return false;
             }
             if (document.all('AccFlag').value=='10')
              {
               // var asql="select polstate from lcpol where grpcontno='"+fm.GrpContNo.value+"' and riskcode='"+fm.RiskCode.value+"' and poltypeflag='2' ";
                mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpReportInputSql");
			mySql.setSqlId("LLGrpReportSql52");
			mySql.addSubPara(fm.GrpContNo.value); 
			mySql.addSubPara(fm.RiskCode.value); 
                var tpolstate=easyExecSql(mySql.getString());
                 if (tpolstate=='4')
                 {                   
            //    	showInfo.close();
           				fm.addbutton.disabled = false;                   
                   alert("该团体账户已退保！请使用个人帐户理赔！");	
                   return false;
                 }
              } //判断公共帐户是否退保。             			       
			                		
        }           
        //-----------------jinsh--20070517------------------如果是账户--查询lcinsureaccclass表--//
         
        if (fm.RptNo.value != "")
        {
          fm.fmtransact.value = "insert||customer";
        }
        else
        {
            fm.fmtransact.value = "insert||first";
        }
        
        for (var i = 0; i < fm.rgtstate.length; i++)
       {
          if (fm.rgtstate[i].checked==true)
          {         	
          	var trgtstate=new Array();
            trgtstate=fm.rgtstate[i].value;
            //alert("trgtstate:"+trgtstate);
          } 
       }

        //提交
        fm.addbutton.disabled = true;
    	fm.action = "LLGrpReportSave.jsp?rgtstate="+trgtstate;
        submitForm();
    }
}

//报案确认
function reportConfirm()
{
    //---------------------------------------------------------------------beg 2005-8-4 16:32
    //增加死亡类案件保单挂起提醒功能
    //---------------------------------------------------------------------
/*
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
//      alert(tYesOrNo);
      if (tYesOrNo > 0)
      {
          if (confirm("理赔类型选择死亡,是否进行保单挂起操作?"))
            {
                return;
            }
        }
    }
*/
    //---------------------------------------------------------------------end
    //检查非空
    
    
    
    if (fm.RptNo.value == "")
    {
         alert("报案号为空！");
         return;
    }
    var sql2 = "select count(1) from LLStandPayInfo where"
                 + " CaseNo = '" + fm.RptNo.value + "'";
    /*mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpReportInputSql");
			mySql.setSqlId("LLGrpReportSql53");
			mySql.addSubPara(fm.RptNo.value);           */
    var tDutyKind = easyExecSql(sql2);
        if (tDutyKind == 0)
        {
            alert("请先进行预估金额录入!");
            return;
        }
    for (var i = 0; i < fm.rgtstate.length; i++)
       {
          if (fm.rgtstate[i].checked==true)
          {
          	var trgtstate=new Array();
            trgtstate=fm.rgtstate[i].value;
            //alert("trgtstate:"+trgtstate);
          } 
       }
        
    fm.action = './LLReportConfirmSave.jsp?rgtstate='+trgtstate;
    submitForm();
}


//导入出险人信息
function getin() {
   	//fm.action = "./GrpCustomerDiskReportSave.jsp?";
   	//document.getElementById("fm").submit();
	//alert(1665);
   	var rgtstate="";
	var i = 0;
	
    getImportPath();
    fmload.all('ImportPath').value = ImportPath;
    
    if (fmload.all('FileName').value==null||fmload.all('FileName').value=="")
    {
       alert ("请输入文件地址!");	
       return false;
    }
    

	if(document.all('RptorName').value==null||document.all('RptorName').value=="")
	{
		alert ("请输入报案人姓名！");	
       	return false;
	}
	
	if(document.all('GrpCustomerNo').value==null||document.all('GrpCustomerNo').value=="")
	{
		alert ("请输入团体客户号！");	
       	return false;
	}
	
	if(document.all('GrpContNo').value==null||document.all('GrpContNo').value=="")
	{
		alert ("请输入团体保单号！");	
       	return false;
	}
	
	if(document.all('GrpName').value==null||document.all('GrpName').value=="")
	{
		alert ("请输入单位名称！");	
       	return false;
	}
	
	if(document.all('occurReason').value==null||document.all('occurReason').value=="")
	{
		alert ("请输入出险原因！");	
       	return false;
	}
	
	
    var AccDate = fm.AccidentDate.value;//事故日期
    //取得年月日信息
    var AccYear = AccDate.substring(0,4);
    var AccMonth = AccDate.substring(5,7);
    var AccDay = AccDate.substring(8,10);

    //检查事故日期
    if (AccDate == null || AccDate == '')
    {
        alert("事故日期不能为空！");
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

                alert("事故日期不能晚于当前日期");
                return false;
            }
            else if (AccMonth == mNowMonth && AccDay > mNowDay)
            {
                alert("事故日期不能晚于当前日期!");
                return false;
            }
        }
    }
		
	
	if (fm.rgtstate[0].checked == false && fm.rgtstate[1].checked == false && fm.rgtstate[2].checked == false )
    {
          alert("请选择报案类型！");
          return false;	
    } 
	
    if(fm.rgtstate[1].checked == true)
    {
    	  rgtstate="03";
    }
    
    if (fm.rgtstate[2].checked == true )
    {
    	  rgtstate="02";
          if (fm.AccFlag.value == null || fm.AccFlag.value == '')
    	  {
        		alert("是否使用团体帐户金额不能为空！");
        		return false;
    	  }
          
          if(document.all('Polno').value=="")
		  {
				alert ("请输入险种编码！");	
       		    return false;
		  }
          
    	//  var StringSQL="select * from lcinsureaccclass where grpcontno='"+fm.GrpContNo.value+"' and riskcode='"+fm.RiskCode.value+"'";
		  mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpReportInputSql");
			mySql.setSqlId("LLGrpReportSql54");
			mySql.addSubPara(fm.GrpContNo.value);  
			mySql.addSubPara(fm.RiskCode.value);  
		  var ForbidResult=easyExecSql(mySql.getString());
			    //alert(ForbidResult);
		  if(ForbidResult==null)
		  {			
			  alert("该险种"+fm.RiskCode.value+"在账户表中不存在，不能做帐户理赔 ！");
	          return false;
		  }	     			    
    }

    
    var StrSQL = "select count(*) from lcgrpcont where appntno='"+fm.GrpCustomerNo.value+"' and grpcontno='"+fm.GrpContNo.value+"' ";
   /* mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpReportInputSql");
			mySql.setSqlId("LLGrpReportSql55");
			mySql.addSubPara(fm.GrpCustomerNo.value);  
			mySql.addSubPara(fm.GrpContNo.value);  */
    //prompt("校验团体客户号和保单号是否匹配sql",StrSQL);
    var arr= easyExecSql(StrSQL);
    if(arr == 0)
    {
          alert("该团体客户号与团体保单号不匹配，请修改后再操作！");
          return false;
    }

    
    tSaveFlag ="0";  
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

    fmload.action = "./GrpCustomerDiskReportSave.jsp?RptorName="+document.all('RptorName').value+"&GrpCustomerNo="+document.all('GrpCustomerNo').value+"&GrpContNo="+document.all('GrpContNo').value+"&GrpName="+document.all('GrpName').value+"&rgtstate="+rgtstate+"&AccFlag="+document.all('AccFlag').value+"&RiskCode="+document.all('RiskCode').value+"&RptorPhone="+document.all('RptorPhone').value+"&RptorAddress="+document.all('RptorAddress').value+"&RptNo="+document.all('RptNo').value+"&ImportPath="+fmload.all('ImportPath').value+"&RptMode="+document.all('RptMode').value+"&AccidentDate="+document.all('AccidentDate').value+"&AccidentReason="+document.all('occurReason').value+"&Relation="+document.all('Relation').value;
    //alert(fmload.action);
    fmload.submit(); //提交
 
   	//fm.action = "./GrpCustomerDiskReportSave.jsp?";
   	//document.getElementById("fm").submit();
}

function getImportPath ()
{
	  //var strSQL = "";
	 // strSQL = "select SysvarValue from ldsysvar where sysvar ='XmlPath'";
	  mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpReportInputSql");
			mySql.setSqlId("LLGrpReportSql56");
			mySql.addSubPara('');
	  turnPage.strQueryResult  = easyQueryVer3(mySql.getString(), 1, 1, 1);
	  //alert(mySql.getString()) modified by lilinsen 2011-10-8 11:44:38
	  if (!turnPage.strQueryResult) 
	  {
		    alert("未找到上传路径");
		    return;
	  }
	  turnPage.arrDataCacheSet = clearArrayElements(turnPage.arrDataCacheSet);
	  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
	  ImportPath = turnPage.arrDataCacheSet[0][0];
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
    //fm.addbutton.disabled = false;
    document.getElementById("fm").submit(); //提交
    tSaveFlag ="0";
}

//选中SubReportGrid响应事件,tPhase=0为初始化时调用
function SubReportGridClick(tPhase,rptNo)
{
  //------------------------------------------**Beg
  //置空相关表单
  //------------------------------------------**
 
  	rptNo=fm.ClmNo.value;
  	fm.customerName.value = "";
	fm.customerAge.value = "";
	fm.customerSex.value = "";
	fm.SexName.value = "";
	//fm.IsVip.value = "";
	//fm.VIPValueName.value = "";
	fm.hospital.value = "";
	fm.TreatAreaName.value = "";
	//fm.OtherAccidentDate.value = "";
	//fm.MedicalAccidentDate.value = "";
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
  spanText1.style.display = "";
  spanText2.style.display = "none";
  spanText3.style.display = "";
  //spanText4.style.display = "";
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
        fm.customerAge.value = calAge(SubReportGrid.getRowColData(i,5));
        fm.IDNo.value = SubReportGrid.getRowColData(i,9);
        fm.IDType.value = SubReportGrid.getRowColData(i,8);
        //fm.IsVip.value = SubReportGrid.getRowColData(i,5);
        showOneCodeName('sex','customerSex','SexName');//性别
//显示团体信息
       if(fm.customerNo.value!=''){
         var strSQL = "";
       }
    }

    //查询获得理赔类型
    var tClaimType = new Array;
    /*var strSQL1 = "select ReasonCode from LLReportReason where "
                + "RpNo = '"+rptNo+"'"
                + " and CustomerNo = '"+fm.customerNo.value+"'";//prompt("",strSQL1);*/
    mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpReportInputSql");
			mySql.setSqlId("LLGrpReportSql57");
			mySql.addSubPara(rptNo);  
			mySql.addSubPara(fm.customerNo.value); 
    tClaimType = easyExecSql(mySql.getString());
    if (tClaimType == null)
    {
        alert("理赔类型为空，请检查此记录的有效性！");
        return;
    }
    else
    {
      fm.occurReason.value=	tClaimType[0][0].substring(0,1);
      showCodeName('occurReason','occurReason','ReasonName');

        for(var j=0;j<fm.claimType.length;j++)
        {
            for (var l=0;l<tClaimType.length;l++)
            {
                var tClaim = tClaimType[l].toString();
                tClaim = tClaim.substring(tClaim.length-2,tClaim.length);//取理赔类型后两位
                
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
   /* var strSQL2 = "select c.HospitalCode,c.AccDate,c.AccidentDetail,c.DieFlag,c.CureDesc,c.Remark,c.AccDesc,c.AccResult1,c.AccResult2,c.HospitalName "
    	         +" ,(select codename from ldcode a where a.codetype='accidentcode' and a.code=c.AccidentDetail),f.AccidentDate"
    	         +" from LLSubReport c,LLReport f where 1=1 "
    	         +" and c.subrptno=f.rptno"
                + getWherePart( 'SubRptNo','RptNo' )
                + getWherePart( 'CustomerNo','customerNo' );*/
     mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpReportInputSql");
			mySql.setSqlId("LLGrpReportSql58");
			mySql.addSubPara(fm.RptNo.value);  
			mySql.addSubPara(fm.customerNo.value); 
//    prompt("查询分案表sql",strSQL2);
    tSubReport = easyExecSql(mySql.getString());
    fm.hospital.value = tSubReport[0][0];
    fm.AccidentDate2.value = tSubReport[0][1];
    fm.AccidentDate.value = tSubReport[0][11];
    fm.accidentDetail.value = tSubReport[0][2];
//    fm.IsDead.value = tSubReport[0][3];
    fm.cureDesc.value = tSubReport[0][4];
    fm.Remark.value = tSubReport[0][5];
    fm.RemarkDisabled.value = tSubReport[0][5];
    fm.AccDesc.value = tSubReport[0][6];
    fm.AccDescDisabled.value = tSubReport[0][6];
    fm.AccResult1.value = tSubReport[0][7];
    fm.AccResult2.value = tSubReport[0][8];
    fm.TreatAreaName.value = tSubReport[0][9];
    fm.accidentDetailName.value = tSubReport[0][10];

    
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

function operStandPayInfo()
{
   var tSel = SubReportGrid.getSelNo();
/*
   if( tSel == 0 || tSel == null ){
        alert( "请在出险人信息中选择一条记录" );
        return false;
     }
else {
     customerNo = SubReportGrid.getRowColData(tSel-1 ,1);
    }
*/
     var varSrc ="";
     var CaseNo = fm.ClmNo.value;//alert(CaseNo);
     var customerName = fm.customerName.value;
     var customerNo=fm.customerNo.value;
     var GrpContNo=fm.GrpContNo.value;
     var RiskCode=fm.RiskCode.value;
     pathStr="./StandPayInfoMain.jsp?CaseNo="+CaseNo+"&customerName="+customerName+"&customerNo="+customerNo
     		+"&GrpContNo="+GrpContNo+"&RiskCode="+RiskCode;
     showInfo = OpenWindowNew(pathStr,"","middle");
}

function callrgtstate(ttype)
{
  	switch (ttype)
    {
       case "03" : 
          //alert("ttype:"+ttype);
          
           	divreport1.style.display="none";  
           	divreport2.style.display="none"; 
           	divreport3.style.display="none";   
           	divreport4.style.display="";
           	divreport5.style.display="none";  
           	initDiskErrQueryGrid();    
           	divDiskErr.style.display="";       
           	operateButton2.style.display="none";
           	/*document.all('Relation').value=" ";
           	document.all('hospital').value=" "; 
           	document.all('TreatAreaName').value=" ";
           	document.all('accidentDetail').value=" ";
           	document.all('accidentDetailName').value=" ";*/
           break;
       case "02" :	
           //alert("ttype:"+ttype);

           	divreport1.style.display="none";
           	divreport2.style.display="none";
           	divreport3.style.display="none";
           	divreport4.style.display="";
           	divreport5.style.display="";
           	/*document.all('Relation').value=" ";
           	document.all('hospital').value=" ";
           	document.all('TreatAreaName').value=" ";
            document.all('accidentDetail').value=" ";
            document.all('accidentDetailName').value=" ";*/
           break;
       case "11":   
           //alert("ttype:"+ttype); 

           	divreport1.style.display="";
           	divreport2.style.display="";
          	divreport3.style.display="";
          	operateButton2.style.display="";
          	divreport4.style.display="none";
          	divreport5.style.display="none";
           break;
       default ://divreport.style.display="";
            return;
    }    

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
                //fm.claimType[4].checked = true;
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
                //fm.claimType[4].checked = true;
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
   /* var strSql = " select ICDName from LDDisease where "
               + " ICDCode = '" + fm.AccResult2.value + "'"
               + " order by ICDCode";*/
   mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpReportInputSql");
			mySql.setSqlId("LLGrpReportSql59");
			mySql.addSubPara(fm.AccResult2.value);  
    var tICDName = easyExecSql(mySql.getString());
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
	var row = SubReportGrid.getSelNo();	
    if(row < 1) {
        alert("请选中一名客户！");
        return;
    }
    var CustomerNo = SubReportGrid.getRowColData(row-1 ,1);
    fm.customerNo.value = CustomerNo;
    fm.action = '../claim/LLPRTCertificatePutOutSave.jsp';  
    var tAffixSql = "select * from LLReportAffix where rptno='"+fm.RptNo.value
    			+"' and customerno='"+CustomerNo+"'";
    var arrAffix = easyExecSql(tAffixSql);
    if(!arrAffix){
    	alert("没有需要打印的索赔资料！");
    	return false;
    }
    
    if(beforePrtCheck(fm.ClmNo.value, CustomerNo, "PCT002")==false)
    {
      return;
    } 
  //  fm.target = "../f1print";
  //  document.getElementById("fm").submit();
}


function showPrtAffixNew()
{   fm.action = './LLPRTCertificatePutOutSaveNew.jsp';   //
    fm.target = "../f1print";
    document.getElementById("fm").submit();
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
    var CustomerNo = SubReportGrid.getRowColData(row-1 ,1);
    fm.customerNo.value = CustomerNo;
    fm.action = './LLPRTAppraisalSave.jsp';   
    if(beforePrtCheck(fm.ClmNo.value, CustomerNo, "PCT001")==false)
    {
      return;
    } 
//    fm.target = "../f1print";
//    document.getElementById("fm").submit();

}

//影像查询---------------2005-08-14添加
function colQueryImage()
{
    //var row = SubReportGrid.getSelNo();
    //if(row < 1)
    //{
        //alert("请选中客户！");
        //return;
    //}
    //var tClaimNo = fm.RptNo.value;         //赔案号  
    //var tCustNo = fm.customerNo.value;     //客户号
    
  //if (tCustNo == "")
  //{
      //alert("请选择客户！");
      //return;
  //}
  
  var strUrl="LLColQueryImageMain.jsp?claimNo="+document.all('ClmNo').value+"&subtype=LP1001";
  window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//打印赔案号条形码
function colBarCodePrint()
{
	if(fm.RptNo.value==""||fm.RptNo.value==null){
		alert("请先保存报案信息！");
		return false;
	}
  fm.action="LLClaimBarCodePrintSave.jsp";
    fm.target = "../f1print";
    document.getElementById("fm").submit();
}



/**********************************
//打印前检验（），需要传入参数（单证号码、赔案号）--------2005-08-22添加
***********************************/
function beforePrtCheck(clmno,CustomerNo,code)
{
  //查询单证流水号，对应其它号码（赔案号）、单据类型、打印方式、打印状态、补打标志
     var tclmno=trim(clmno);
     var tCustomerNo =trim(CustomerNo);
     var tcode =trim(code);
     
     if(tclmno=="" ||tcode=="")
     {
       alert("传入的赔案号或单证类型（号码）为空");
       return false;
     }
    var strSql="select t.prtseq,t.otherno,t.code,t.prttype,t.stateflag,t.patchflag from loprtmanager t where 1=1 "
      +" and t.otherno='" + tclmno + "'"
      +" and trim(t.code)='" + tcode + "'"
      +" and t.standbyflag4='" + tCustomerNo + "'";
  /* mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpReportInputSql");
			mySql.setSqlId("LLGrpReportSql60");
			mySql.addSubPara(tclmno); 
			mySql.addSubPara(tcode); 
			mySql.addSubPara(tCustomerNo); */
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
//      fm.action = './LLPRTCertificatePutOutSave.jsp';   //
    fm.target = "../f1print";
    document.getElementById("fm").submit();
   }
  else
  {
    //存在但已经打印过，tstateflag[打印状态：1 -- 完成、2 -- 打印的单据已经回复、3 -- 表示已经发催办通知书]
    if(confirm("该单证已经打印完成，你确实要补打吗？"))
     {
       //进入补打原因录入页面
       var strUrl="../claim/LLPrtagainMain.jsp?PrtSeq="+fm.PrtSeq.value+"&CustomerNo="+fm.customerNo.value+"&RgtClass=2";
       window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
     }
  }
  
}

function easyQueryClick()
{
	  //alert(2327);
	  
	  //alert("fm.ClmNo.value:"+fm.ClmNo.value);
	  //alert("fmload.tRgtNo.value:"+fmload.tRgtNo.value);
	  
	  if(fm.RptNo.value==null||fm.RptNo.value=="")
	  {
		  fmload.tRgtNo.value="00000000000000000000";
	  }
	  else
	  {
		  fmload.tRgtNo.value=fm.ClmNo.value;
	  }
	  
	  //alert("fmload.tRgtNo.value2:"+fmload.tRgtNo.value);

	  if(fmload.tBatchNo.value==null||fmload.tBatchNo.value=="")
	  {
		  	alert("请录入导入文件名！");
		  	return;
	  }
	  
	  if (fmload.tRgtNo.value==""&&fmload.tCustomerNo.value==""&&fmload.tCustomerName.value==""&&fmload.tBatchNo.value=="")
	  {
	  	alert("请至少输入一个查询条件！");
	  	return;
	  }
	  
	  initDiskErrQueryGrid();
	  // 书写SQL语句
	  //var strSql = "select rgtno,customerno,customername,ErrorInfo,BatchNo,ID,operator,makedate,maketime from lcGrpCustomerImportLog where 1=1 ";
	   // + "and ErrorState='0' "; 暂不加这个条件
	   mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpReportInputSql");
			mySql.setSqlId("LLGrpReportSql61");
	  if(fmload.all('tRgtNo').value!=null&&fmload.all('tRgtNo').value!="")
	  {
	   // strSql=strSql+ "and RgtNo='"+fmload.all('tRgtNo').value+"'";
	     mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpReportInputSql");
			mySql.setSqlId("LLGrpReportSql62");
			mySql.addSubPara(fmload.all('tRgtNo').value); 
			
	  }
	  if(fmload.all('tCustomerNo').value!=null&&fmload.all('tCustomerNo').value!="")
	  {
	    //strSql=strSql+ "and CustomerNo='"+fmload.all('tCustomerNo').value+"'";
	    mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpReportInputSql");
			mySql.setSqlId("LLGrpReportSql63");
			mySql.addSubPara(fmload.all('tRgtNo').value); 
			mySql.addSubPara(fmload.all('tCustomerNo').value); 
	  }
	  if(fmload.all('tCustomerName').value!=null&&fmload.all('tCustomerName').value!="")
	  {
	    //strSql=strSql+ "and customername='"+fmload.all('tCustomerName').value+"'";
	    mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpReportInputSql");
			mySql.setSqlId("LLGrpReportSql64");
			mySql.addSubPara(fmload.all('tRgtNo').value); 
			mySql.addSubPara(fmload.all('tCustomerNo').value); 
			mySql.addSubPara(fmload.all('tCustomerName').value); 
	  }
	  if(fmload.all('tBatchNo').value!=null&&fmload.all('tBatchNo').value!="")
	  {
	    //strSql=strSql+ "and BatchNo like '"+fmload.all('tBatchNo').value+"%%'";
	    mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpReportInputSql");
			mySql.setSqlId("LLGrpReportSql65");
			mySql.addSubPara(fmload.all('tRgtNo').value); 
			mySql.addSubPara(fmload.all('tCustomerNo').value); 
			mySql.addSubPara(fmload.all('tCustomerName').value); 
			mySql.addSubPara(fmload.all('tBatchNo').value); 
	  }  
	    //strSql=strSql+ "and operator='"+document.all('Operator').value+"'";
	 // strSql=strSql+ " Order by makedate desc";	
	  
	//  prompt("查询批量导入错误信息sql",strSql);
	  turnPage2.queryModal(mySql.getString(),DiskErrQueryGrid,1,1);
	  
	  if (!turnPage.strQueryResult) {
	    //alert("未查询到满足条件的数据！");
		fmload.tRgtNo.value="";
	    return false;
	  }  
	  
}



function afterLCGrpQuery(arrReturn)
{
     document.all('GrpCustomerNo').value=arrReturn[0][0];
     document.all('GrpName').value=arrReturn[0][1];
     document.all('GrpContNo').value=arrReturn[0][2];
     document.all('Peoples').value=arrReturn[0][3];
}

//导出该赔案下所有出险人既往赔付信息
function getLastCaseInfo(){
var CaseNo = fm.RptNo.value;
var GrpContNo = fm.GrpContNo.value
if(CaseNo == "" || CaseNo == null){
	alert("请先点保存！");
	return false;
}else{
//alert("bb");
	var row = SubReportGrid.getSelNo() - 1;
	if (row <0)
	{
	      alert("请先选择一条客户信息！");
	      return;
	}
  var tCustomerNo = SubReportGrid.getRowColData(row,1);
 //+" and a.grpcontno = '" + GrpContNo + "'";
  var tSQL = "select a.clmno,a.grpcontno,a.insuredno,a.insuredname,a.getdutykind,"
		 + " b.accdate,b.endcasedate,a.riskcode,a.standpay," 
		 + "a.realpay from llclaimpolicy a,llcase b "
		 +"where  a.clmno=b.caseno and a.insuredno=b.customerno "
		 //+" and b.rgtstate='60'"
		 +" and "
		 +" a.insuredno = '"+ tCustomerNo +"'"
	     +" and a.clmno <>'"+CaseNo+"'";
	mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpReportInputSql");
			mySql.setSqlId("LLGrpReportSql66");
			mySql.addSubPara(tCustomerNo); 
			mySql.addSubPara(CaseNo);       
  var arr = easyExecSql(mySql.getString());
  if(!arr){
	  alert("未查询到既往赔案信息，无法导出");
	  return false;
  }
fm.tSQL.value = tSQL;
//prompt("",tSQL);
  var Title="既往赔案信息查询";
	var SheetName="既往赔案信息查询";       
	var ColName = "赔案号@团体保单号@客户号@客户姓名@理赔类型@出险日期@结案日期@险种代码@应赔付金额@实际赔付金额";
	fm.action = "./PubCreateExcelSave.jsp?tSQl="+tSQL+"&Title=既往赔案信息查询"+"&SheetName="+Title+"&ColName="+ColName;
	fm.target="../claimgrp";		 	
	document.getElementById("fm").submit();
}   
}



//出险细节查询
function showAccDetail(tCode,tName)
{
	var strUrl="../claim/LLColQueryAccDetailInput.jsp?codeno="+tCode+"&codename="+tName;
	window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//医院模糊查询
function showHospital(tCode,tName)
{
	var strUrl="../claim/LLColQueryHospitalInput.jsp?codeno="+tCode+"&codename="+tName;
	window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}



/**=========================================================================
    修改状态：开始
    修改原因：如果出险人和报案人是同一个人的话，那么报案人的信息从出险人的
              信息中得到
    修 改 人：万泽辉
    修改日期：2005.11.15
   =========================================================================
**/
function getRptorInfo()
{
   var tCustomerNo = fm.customerNo.value ;
   var tRelation = fm.Relation.value;

   var tRelationName = fm.RelationName.value;
   if(tRelation == "00"|| tRelationName == "本人")
   {
      if( tCustomerNo != null  && tCustomerNo != "")
      {
          //var strSQL = "select postaladdress,phone from lcaddress where customerno ='"+tCustomerNo+"'";
          mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpReportInputSql");
			mySql.setSqlId("LLGrpReportSql67");
			mySql.addSubPara(tCustomerNo); 
          var strQueryResult = easyExecSql(mySql.getString());
          if(strQueryResult==null || strQueryResult=="")
          {
          	return;
          }
          else
          {
             fm.RptorAddress.value = strQueryResult[0][0];
             fm.RptorPhone.value = strQueryResult[0][1];
             fm.RptorName.value = fm.customerName.value;
          }
      }
      else
      {
          return;
      }
   }

   

   //修改原因：如果“报案人和出险人关系”为“GX02-保单服务人员”时，那么报案人的信息取保单服务人员的信息
   //修 改 人：zhangzheng 
   //修改时间：2008-12-16
   //MS没有保单服务人员,去掉这段逻辑
   
   //else if(tRelation == "GX02" || tRelationName == "保单服务人员")
   //{
   	 //if( tCustomerNo != null  && tCustomerNo != "")
   	 //{
   	 	   //var strSQL =" select b.name, b.homeaddress, b.phone from laagent b " 
           //         +" where 1=1 "
           //         +" and b.agentcode in (select distinct trim(a.agentcode) from lccont a where a.insuredno = '"+tCustomerNo+"') ";
         //var arr = easyExecSql(strSQL);
         // if(arr==null || arr=="")
         // {
         // 	return;
         // }
         // else
         // {
         //    fm.RptorName.value = arr[0][0];
         //    fm.RptorAddress.value = arr[0][1];
         //    fm.RptorPhone.value = arr[0][2];
         // }
         
   	// }
   	// else
   	 //{
   	 	// return;
   	 //}
   //}
   else
   {
       return;
   }
}

/*
 * 日期校验,校验两个日期都不能晚于当前日期，且第2个日期不能早于第1个日期
 * Date1,传入的第一个日期,此处为事故日期
 * Date2 传入的第二个日期,此处根据情况为医疗出险日期或其他出险日期
 * tDateName 传入的日期名称，用于组成弹出的提示语言
 */
function checkDate(tDate1,tDate2,tDateName)
{
    
    var AccDate  =  tDate1;//事故日期
    var AccDate2 =  tDate2;//出险日期
   
    //alert("AccDate:"+AccDate);
//    alert("AccDate2:"+AccDate2);
    //alert("tDateName:"+tDateName);
       
    //检查事故日期
    if (AccDate == null || AccDate == '')
    {
        alert("请输入事故日期！");
        return false;
    }
    else
    {       
      	if (dateDiff(mCurrentDate,AccDate,'D') > 0)
        {
      		alert("事故日期不能晚于当前日期!");
            return false; 
        }
    }
    
    //验证事故下有两个或以上的处理中赔案--------BEG
    if (fm.BaccDate.value == null || fm.BaccDate.value == '')//Modify by zhaorx 2006-07-31
    {
    	fm.BaccDate.value = AccDate; //新增报案时，不校验事故日期
    }    
    
    var OAccDate = fm.BaccDate.value;//原事故日期Modify by zhaorx 2006-07-04
   
    if (OAccDate != AccDate)
    {    
        /*var strSQL = " select count(*) from llreport a left join llregister b on a.rptno = b.rgtno "
                    + " where nvl(clmstate,0) != '70' "
                    + " and rptno in (select caseno from llcaserela where caserelano = '" +	fm.AccNo.value + "')"*/
        //prompt("验证事故下有两个或以上的处理中赔案",strSQL);
         mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpReportInputSql");
			mySql.setSqlId("LLGrpReportSql68");
			mySql.addSubPara(fm.AccNo.value); 
		var tCount = easyExecSql(mySql.getString());
		
        if (tCount != null && tCount != "1" && tCount != "0")
        {
            alert("事故下有两个或以上的处理中赔案，不允许修改事故日期！");
            fm.AccidentDate.value = OAccDate;
            return false;
        }
    }
//    alert(2497);

    //校验出险日期
    if (AccDate2 == null || AccDate2 == '')
    {
        alert("出险日期"+"不能为空！");
        return false;
    }
    else
    {

       	//比较出险日期和当前日期
    	if (dateDiff(mCurrentDate,AccDate2,'D') > 0)
        {
        	alert("出险日期"+"不能晚于当前日期!");
            return false; 
        }

        //比较出险日期和事故日期       
    	if (dateDiff(AccDate2,AccDate,'D') > 0)
        {
        	alert("出险日期"+"不能早于事故日期!");
            return false; 
        }

    }
    
    return true;
}


/*
 * 根据团单号查询投保人客户号和姓名
 */
function queryCustomerIn()
{
	 var strSQL = "  select customerno,grpname from lcgrppol where grpcontno= '"+fm.GrpContNo.value + "'"
     //prompt("根据团体合同号查询投保人客户号和姓名",strSQL);
	/*mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpReportInputSql");
			mySql.setSqlId("LLGrpReportSql69");
			mySql.addSubPara(fm.GrpContNo.value); */
	 var tAppnt = easyExecSql(strSQL);

	 if (tAppnt != null)
	 {
		 fm.GrpCustomerNo.value=tAppnt[0][0];
		 fm.GrpName.value=tAppnt[0][1];
	 }
}

