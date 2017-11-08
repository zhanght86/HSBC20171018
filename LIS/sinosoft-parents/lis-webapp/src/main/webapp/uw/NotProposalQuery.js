//***********************************************
//程序名称：NotProposalQuery.js
//程序功能：未承保查询
//创建日期：2005-06-01 11:10:36
//创建人  ：HL
//更新记录：  更新人    更新日期     更新原因/内容
//***********************************************

//               该文件中包含客户端需要处理的函数和事件


//全局变量
var showInfo;
var turnPage = new turnPageClass();
var turnPage2 = new turnPageClass();
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes;";

/*********************************************************************
*  投保单复核的提交后的操作,服务器数据返回后执行的操作
*  参数  ：  无
*  返回值：  无
*********************************************************************
*/
function afterSubmit(FlagStr, content)
{
    if (FlagStr == "Fail")
    {
        showInfo.close();
        var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
        //showModalDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=350;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();

    }
    if (FlagStr == "Succ")
    {
        showInfo.close();
        var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
        //showModalDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=350;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();

    }

}


/*********************************************************************
*  返回上一页
*  参数  ：  无
*  返回值：  无
*********************************************************************
*/

function returnParent() {
    top.close();

}


/*********************************************************************
*  查询未承保保单
*  参数  ：  无
*  返回值：  无
*********************************************************************
*/

function queryCont() {
	
	//tongmeng 2007-12-03 modify
	//增加核保日期查询
	
		var sqlid1="NotProposalQuerySql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName("uw.NotProposalQuerySql"); //指定使用的properties文件名
		mySql1.setSqlId(sqlid1);//指定使用的Sql的id
		mySql1.addSubPara(customerNo);//指定传入的参数
	     var aSQL=mySql1.getString();	
	
//    var aSQL = "select a.contno,a.proposalcontno,a.appntno,a.appntname,a.insuredname," 
//            + " (select nvl(to_char(min(makedate),'yyyy-mm-dd'),'未人工核保') from lccuwsub where contno=a.contno "
//            + " ) "
//            + ",a.CValiDate,"
//            + " decode(nvl(substr(trim(a.state), 4, 1), 'z'),"
//            + " 'z',"
//            + " '不逾期',"
//            + " '2',"
//            + " '逾期作废',"
//            + " '逾期'),"
//            + " (case when exists(select 'X' from loprtmanager where otherno=a.contno and code in('00','06','81','82','83','84','85','86','87','88','89','04'))"
//            + " then '有'	else '无'"
//            + " end),"
//            + " (case when exists(select 'X' from LCPENotice where contno=a.contno)"
//            + " then '有' else '无'"
//            + " end),"
//            + " (case"
//            + " when exists (select 'X' from LCRreport where contno = a.contno) then"
//            + " '有'"
//            + " else"
//            + " '无'"
//            + " end),"
//            + " (case when exists(select 'X' from LCCustomerImpart where contno=a.contno) "
//            + " then '有' else '无'"
//            + " end),a.prtno,"
//            + " case a.uwflag when 'a' then '撤保' else '正常' end,"
//            + " case conttype when '1' then '个人投保单' when '2' then '集体投保单' end,conttype,"
//            + " case a.uwflag "
//         		+ "		when 'a' then '撤保' "
//         		+ "		when '2' then '延期' "
//         		+ "		when '4' then '特约承保' "
//         		+ "		when '3' then '加费承保' "
//         		+ "		when 'd' then '限额' "
//         		+ "		when '1' then '拒保' "
//         		+ "		when '9' then '标准承保' "
//         		+ "		when 'z' then '核保订正' "
//         		+ "		when '5' then '未通过自动核保' "
//         		+ "	end "
//            + " from lccont a,lcinsured b where b.insuredno='" + customerNo + "' and a.contno=b.contno and a.appflag in('0','2')";//Modified By QianLy on 2006-12-12 for BUG 7619
    turnPage.queryModal(aSQL, ContGrid);

}

//查询客户信息
function queryPersonInfo()
{
    var aSQL = "select CustomerNo, Name from LDPerson where CustomerNo = '" + customerNo + "'";
    var arrResult = easyExecSql(aSQL);
    if (arrResult != null)
    {
        document.all('CustomerNo').value = arrResult[0][0];
        document.all('CustomerName').value = arrResult[0][1];
    }
}

function getContDetail() {
    //alert();

}

function getPolInfo() {
    //alert("ContGrid.getRowColData(0,1)=="+ContGrid.getRowColData(ContGrid.getSelNo()-1,1));
    //	var aSQL = "select a.contno,a.polno,a.SignDate,b.riskcode,b.riskname,'',a.amnt,a.mult,c.PassFlag,d.SuppRiskScore "
    //	+"from lcpol a,lmrisk b,LCUWMaster c,lcprem d "
    //	+"where a.contno='"+ContGrid.getRowColData(ContGrid.getSelNo()-1,1)+"' and b.riskcode=a.riskcode and c.polno=a.polno and d.polno=a.polno ";

    //var aSQL = "select distinct a.contno,a.polno,a.SignDate,b.riskcode,b.riskname,'',a.amnt,a.mult,"
    //    +" (select distinct PassFlag from LCUWMaster where polno=a.polno and PassFlag is not null and rownum=1) as newPassFlag,"
    //     +" d.SuppRiskScore "
    //	     +" from lcpol a,lmrisk b,lcprem d "
    //	     +" where a.contno='"+ContGrid.getRowColData(ContGrid.getSelNo()-1,1)+"' and b.riskcode=a.riskcode and d.polno=a.polno ";
    
			var sqlid2="NotProposalQuerySql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName("uw.NotProposalQuerySql"); //指定使用的properties文件名
		mySql2.setSqlId(sqlid2);//指定使用的Sql的id
		mySql2.addSubPara(ContGrid.getRowColData(ContGrid.getSelNo() - 1, 1));//指定传入的参数
	     var aSQL=mySql2.getString();	
	
//	var aSQL = "select distinct a.prtno,a.polno,a.SignDate,b.riskcode,b.riskname,"
//            + " (case when exists(select endDate from LmriskApp where RiskCode=b.riskcode)"
//            + " then '在销' else '停售'"
//            + " end),"
//            + " a.amnt,a.mult,"
//            + " case a.uwflag when '5' then '自核不通过' when 'z' then '核保订正' else (select codename from ldcode where codetype='grpuwstate' and code=a.uwflag)end ,"
//            + " nvl((select sum(d.SuppRiskScore) from lcprem d where d.polno=a.polno and d.payplantype = '01'),0)"
//            + " from lcpol a,lmrisk b "
//            + " where a.contno='" + ContGrid.getRowColData(ContGrid.getSelNo() - 1, 1) + "' and b.riskcode=a.riskcode";
    //  initPolGrid();
    //  alert(aSQL);
    turnPage2.queryModal(aSQL, PolGrid);

}


function contInfoClick() {
    getPolInfo();
    fm.button1.disabled = "";
    //判断是否有财务缴费信息

    //判断是否有核保结论
    //haveUWResult();
    //??????
    ctrlButtonDisabled();

}

//判断是否有财务缴费信息
function haveFeeInfo() {
    return;
}

//判断是否有影像资料
function havePicData() {
    return;
}

//判断是否有核保结论
//function haveUWResult() {
//    var aSQL = "select * from LCCUWMaster where contno='" + ContGrid.getRowColData(ContGrid.getSelNo() - 1, 1) + "'";
//    //  alert(aSQL);
//    var arrResult = easyExecSql(aSQL);
//    if (arrResult != null) {
//        fm.button4.disabled = "";
//        return;
//    }
//    else {
//        fm.button4.disabled = "true";
//        return;
//    }
//    return;
//}

function getContDetailInfo() {

    var tsel = ContGrid.getSelNo() - 1;
    if (tsel < 0) {
        alert("请选择保单!");
        return;
    }

    //   var tSql = "select salechnl from lccont where contno='"+ContGrid.getRowColData(ContGrid.getSelNo()-1,1)+"'";
	
				var sqlid3="NotProposalQuerySql3";
		var mySql3=new SqlClass();
		mySql3.setResourceName("uw.NotProposalQuerySql"); //指定使用的properties文件名
		mySql3.setSqlId(sqlid3);//指定使用的Sql的id
		mySql3.addSubPara(ContGrid.getRowColData(ContGrid.getSelNo() - 1, 1));//指定传入的参数
	     var tSql=mySql3.getString();	
	
//    var tSql = "select case lmriskapp.riskprop"
//            + " when 'I' then"
//            + " '1'"
//            + " when 'G' then"
//            + " '2'"
//            + " when 'Y' then"
//            + " '3'"
//            + " end"
//            + " from lmriskapp"
//            + " where riskcode in (select riskcode"
//            + " from lcpol"
//            + " where polno = mainpolno"
//            + " and contno = '" + ContGrid.getRowColData(ContGrid.getSelNo() - 1, 1) + "')"
    var BankFlag = ""
    var brrResult = easyExecSql(tSql);
    if (brrResult != null)
    {
        BankFlag = brrResult[0][0];
    }
    //   	alert(BankFlag);

    window.open("../app/ProposalEasyScan.jsp?LoadFlag=6&prtNo=" + ContGrid.getRowColData(ContGrid.getSelNo() - 1, 13) + "&ContNo=" + ContGrid.getRowColData(ContGrid.getSelNo() - 1, 1) + "&BankFlag=" + BankFlag, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1;"+sFeatures);
}


function showTempFee()
{
    var tsel = ContGrid.getSelNo() - 1;
    if (tsel < 0) {
        alert("请选择保单!");
        return;
    }
    var tAppntNo = ContGrid.getRowColData(tsel, 3);
    var tAppntName = ContGrid.getRowColData(tsel, 4);
    var tContType = ContGrid.getRowColData(tsel, 16);
    window.open("./UWTempFeeQryMain.jsp?PrtNo=" + ContGrid.getRowColData(ContGrid.getSelNo() - 1, 13) + "&AppntNo=" + tAppntNo + "&AppntName=" + tAppntName + "&ContType=" + tContType, "window11",sFeatures);
}


//影像资料查询
function showImage() {

    var tsel = ContGrid.getSelNo() - 1;

    //alert(ContNo);
    if (tsel < 0) {
        alert("请选择保单!");
        return;
    }
    var ContNo = ContGrid.getRowColData(tsel, 1);
    window.open("./ImageQueryMain.jsp?ContNo=" + ContNo, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1;"+sFeatures);

}

//核保查询
function UWQuery() {
    var tsel = ContGrid.getSelNo() - 1;
    if (tsel < 0)
    {
        alert("请选择保单!");
        return;
    }
    var proposalcontno;
    var ContType = ContGrid.getRowColData(tsel, 16);
    
    if (ContType == "2")
    {
       proposalcontno = ContGrid.getRowColData(tsel, 1);   
    }
    else
    {
       proposalcontno = ContGrid.getRowColData(tsel, 2);
    }
            
//    var strSQL = "select prtno from LCCont where contno='" + ContNo + "'";
//    var arrResult = easyExecSql(strSQL);
//    var proposalcontno = arrResult[0][0];
    window.open("./UWQueryMain1.jsp?ProposalContNo=" + proposalcontno + "&ContType=" + ContType, "window13",sFeatures);

}

//点击PolGrid触发的函数

function clickPolGrid() {
    //控制按钮的明暗
    ctrlButtonDisabled();
}


//控制按钮的明暗	
function ctrlButtonDisabled() {
    var tContNo = ContGrid.getRowColData(ContGrid.getSelNo() - 1, 11);
    var ContType = ContGrid.getRowColData(ContGrid.getSelNo() - 1, 14);
    if (ContType == "2")
    {
       var proposalcontno = ContGrid.getRowColData(ContGrid.getSelNo() - 1, 1);   
    }
    else
    {
       var proposalcontno = ContGrid.getRowColData(ContGrid.getSelNo() - 1, 2);
    }
    
    var tSQL = "";
    var arrResult;
    var arrButtonAndSQL = new Array;

    arrButtonAndSQL[0] = new Array;
    arrButtonAndSQL[0][0] = "Button1";
    arrButtonAndSQL[0][1] = "保单明细查询";
    arrButtonAndSQL[0][2] = "";

    arrButtonAndSQL[1] = new Array;
    arrButtonAndSQL[1][0] = "Button2";
    arrButtonAndSQL[1][1] = "影像查询";
    if(_DBT==_DBO){
    	arrButtonAndSQL[1][2] = "select * from es_doc_relation where bussno='" + tContNo + "' and bussnotype='11' and rownum=1";
    }else if(_DBT==_DBM){
    	arrButtonAndSQL[1][2] = "select * from es_doc_relation where bussno='" + tContNo + "' and bussnotype='11' limit 1";
    }

    arrButtonAndSQL[2] = new Array;
    arrButtonAndSQL[2][0] = "Button3";
    arrButtonAndSQL[2][1] = "交费查询";
    if(_DBT==_DBO){
    	arrButtonAndSQL[2][2] = "select * from ljtempfee where otherno='" + tContNo + "' and rownum=1";
    }else if(_DBT==_DBM){
    	arrButtonAndSQL[2][2] = "select * from ljtempfee where otherno='" + tContNo + "' limit 1";
    }

//    arrButtonAndSQL[3] = new Array;
//    arrButtonAndSQL[3][0] = "Button4";
//    arrButtonAndSQL[3][1] = "核保结论查询";
//    arrButtonAndSQL[3][2] = "select * from lccuwsub where contno='" + proposalcontno + "' and rownum=1";

    arrButtonAndSQL[3] = new Array;
    arrButtonAndSQL[3][0] = "Button6";
    arrButtonAndSQL[3][1] = "特约查询";
    if(_DBT==_DBO){
    	arrButtonAndSQL[3][2] = "select 1 from lcspec where contno='" + tContNo + "' and rownum=1 union select 1 from lccspec where contno='" + tContNo + "' and rownum=1";
    }else if(_DBT==_DBM){
    	arrButtonAndSQL[3][2] = "select 1 from lcspec where contno='" + tContNo + "' limit 1 union select 1 from lccspec where contno='" + tContNo + "' limit 1";
    }

    /**
    //影像资料查询
    tSQL="select * from lcissuepol where contno='"+tContNo+"' and rownum=1";
    arrResult = easyExecSql(tSQL);
    if(arrResult!=null){
      document.all("uwButton4").disabled="";
    }
    else{
      document.all("uwButton4").disabled="true";
    }

    //问题件信息查询
    tSQL="select * from lcissuepol where contno='"+tContNo+"' and rownum=1";
    arrResult = easyExecSql(tSQL);
    if(arrResult!=null){
      document.all("uwButton4").disabled="";
    }
    else{
      document.all("uwButton4").disabled="true";
    }
    **/

    for (var i = 0; i < arrButtonAndSQL.length; i++) {
        if (arrButtonAndSQL[i][2] != null && arrButtonAndSQL[i][2] != "") {
            //alert(arrButtonAndSQL[i][1]+":"+arrButtonAndSQL[i][2]);
            arrResult = easyExecSql(arrButtonAndSQL[i][2]);
            if (arrResult != null) {
                eval("document.all('" + arrButtonAndSQL[i][0] + "').disabled=''");
            }
            else {
                eval("document.all('" + arrButtonAndSQL[i][0] + "').disabled='true'");
            }
        }
        else {
            continue;
        }
    }


}

//
function RecordQuery() {
    var tsel = ContGrid.getSelNo() - 1;
    if (tsel < 0) {
        alert("请选择保单!");
        return;
    }

    var cContNo = ContGrid.getRowColData(tsel, 1);
    var cAppntNo = ContGrid.getRowColData(tsel, 3);
    var cContType = ContGrid.getRowColData(tsel, 16);

    window.open("../sys/RecordQueryMain.jsp?ContNo=" + cContNo + "&PrtNo=" + ContGrid.getRowColData(tsel, 13)+ "&ContType=" + cContType,"",sFeatures);


}


function SpecQuery() {
    var arrReturn = new Array();
    var tSel = ContGrid.getSelNo();

    if (tSel == 0 || tSel == null)
        alert("请先选择一条记录。");
    else {
        var ContNo = ContGrid.getRowColData(tSel - 1, 1);
        var PrtNo = ContGrid.getRowColData(tSel - 1, 13);

        if (ContNo == "") return;

        window.open("../uw/UWManuSpecMain.jsp?ContNo=" + ContNo + "&PrtNo=" + PrtNo + "&QueryFlag=1","",sFeatures);
    }


}
