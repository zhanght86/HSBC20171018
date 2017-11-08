//***********************************************
//程序名称：ProposalQuery.js
//程序功能：承保保单查询
//创建日期：2005-06-01 11:10:36
//创建人  ：HL
//更新记录：  更新人    更新日期     更新原因/内容
//***********************************************

//               该文件中包含客户端需要处理的函数和事件


//全局变量
var showInfo;
var turnPage = new turnPageClass();
var turnPage2 = new turnPageClass();
var mSwitch = parent.VD.gVSwitch;
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
		var iHeight=250;     //弹出窗口的高度; 
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
		var iHeight=250;     //弹出窗口的高度; 
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

function returnParent()
{
    top.close();
}


/*********************************************************************
*  查询已承保保单
*  参数  ：  无
*  要在既往承保保单信息查询中增加上已经终止的保单(2005-11-26修改)
*  返回值：  无
*********************************************************************
*/

function queryCont()
{
    //	var aSQL="select a.contno,a.appntno,a.appntname,a.CValiDate,decode(nvl(substr(trim(a.state),4,1),'z'),'z','不逾期','2','逾期作废','逾期'),"
    //		+" (case when exists(select 'X' from loprtmanager where otherno=a.contno and code in('00','06','81','82','83','84','85','86','87','88','89','04'))"
    //		+" then '有'	else '无' end),"
    //		+" (case when exists(select 'X' from LCPENotice where contno=a.contno)"
    //		+" then '有' else '无' end),"
    //		+" (case when exists(select 'X' from LCCustomerImpart where contno=a.contno) "
    //		+" then '有' else '无' end),a.proposalcontno"
    //		+" from lccont a,lcinsured b where 1=1 and a.contno=b.contno and a.appflag in('1','4')"
    //		+" and a.salechnl in('1','3','01','03') and b.insuredno='"+customerNo+"'";
    
	    var aSQL = "";
	    var sqlid1="ProposalQuerySql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName("uw.ProposalQuerySql"); //指定使用的properties文件名
		mySql1.setSqlId(sqlid1);//指定使用的Sql的id
		mySql1.addSubPara(customerNo);//指定传入的参数
		mySql1.addSubPara(customerNo);//指定传入的参数
	    aSQL=mySql1.getString();	
	
	
//       var aSQL = "select a.contno,case conttype when '1' then a.contno when '2' then a.grpcontno end,"
//                + " a.appntno,"
//                + " a.appntname,"
//                + " a.insuredname,"
//                + " a.CValiDate,"
//                + " decode(nvl(substr(trim(a.state), 4, 1), 'z'),"
//                + " 'z',"
//                + " '不逾期',"
//                + " '2',"
//                + " '逾期作废',"
//                + " '逾期'),"
//                + " (case"
//                + " when exists"
//                + " (select 'X'"
//                + " from loprtmanager"
//                + " where otherno = a.contno"
//                + " and code in"
//                + " ('00', '06', '81', '82', '83', '84', '85', '86', '87', '88', '89', '04')) then"
//                + " '有'"
//                + " else"
//                + " '无'"
//                + " end),"
//                + " (case"
//                + " when exists (select 'X' from LCPENotice where contno = a.contno) then"
//                + " '有'"
//                + " else"
//                + " '无'"
//                + " end),"
//                + " (case"
//                + " when exists (select 'X' from LCRreport where contno = a.contno) then"
//                + " '有'"
//                + " else"
//                + " '无'"
//                + " end),"
//                + " (case"
//                + " when exists"
//                + " (select 'X' from LCCustomerImpart where contno = a.contno) then"
//                + " '有'"
//                + " else"
//                + " '无'"
//                + " end),"
//                + " a.prtno,"
//                + " case (select state"
//                + " from "
//                + " (select x.* from lccontstate x,lcpol y where x.polno=y.mainpolno order by x.modifydate desc,x.modifytime desc) "
//                + " where contno = a.contno"
//                + " and statetype = 'Available' and enddate is null and rownum=1)"
//                + " when '0' then"
//                + " '有效'"
//                + " else"
//                + " '失效'"
//                + " end,"
//                + "case conttype when '1' then '个人投保单' when '2' then '集体投保单' end,conttype"
//                + " from lccont a, lcinsured b"
//                + " where 1 = 1"
//                + " and a.contno = b.contno"
//                + " and a.appflag in ('1', '4')"
//                //+ " and a.salechnl in ('1', '3', '01', '03','2')"
//                + " and b.insuredno = '"+customerNo+"'"
//                + " and not exists (select 'x'"
//                + " from lccontstate t,lcpol p "
//                + " where t.polno=p.mainpolno and t.contno = a.contno"
//                + " and statetype = 'Terminate' "
//                + " and state = '1')"
//                
//                + " union"
//                + " select a.contno,case conttype when '1' then a.contno when '2' then a.grpcontno end,"
//                + " a.appntno,"
//                + " a.appntname,"
//                + " a.insuredname,"
//                + " a.CValiDate,"
//                + " decode(nvl(substr(trim(a.state), 4, 1), 'z'),"
//                + " 'z',"
//                + " '不逾期',"
//                + " '2',"
//                + " '逾期作废',"
//                + " '逾期'),"
//                + " (case"
//                + " when exists"
//                + " (select 'X'"
//                + " from loprtmanager"
//                + " where otherno = a.contno"
//                + " and code in"
//                + " ('00', '06', '81', '82', '83', '84', '85', '86', '87', '88', '89', '04')) then"
//                + " '有'"
//                + " else"
//                + " '无'"
//                + " end),"
//                + " (case"
//                + " when exists (select 'X' from LCPENotice where contno = a.contno) then"
//                + " '有'"
//                + " else"
//                + " '无'"
//                + " end),"
//                + " (case"
//                + " when exists (select 'X' from LCRreport where contno = a.contno) then"
//                + " '有'"
//                + " else"
//                + " '无'"
//                + " end),"
//                + " (case"
//                + " when exists"
//                + " (select 'X' from LCCustomerImpart where contno = a.contno) then"
//                + " '有'"
//                + " else"
//                + " '无'"
//                + " end),"
//                + " a.prtno,"
//                + " (case a.appflag when '1' then '有效' when '4' then '终止' end),"
//                + "case conttype when '1' then '个人投保单' when '2' then '集体投保单' end,conttype"
//                + " from lccont a, lcinsured b"
//                + " where 1 = 1"
//                + " and a.contno = b.contno"
//                + " and a.appflag in ('1', '4')"
//                //+ " and a.salechnl in ('1', '3', '01', '03','2')"
//                + " and b.insuredno = '"+customerNo+"'"
//                + " and exists (select 'x'"
//                + " from lccontstate t,lcpol p "
//                + " where t.polno=p.mainpolno and t.contno = a.contno "
//                + " and statetype = 'Terminate'"
//                + " and state = '1')";    
    turnPage.queryModal(aSQL, ContGrid);

}

//查询客户信息
function queryPersonInfo()
{
	
	    var sqlid2="ProposalQuerySql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName("uw.ProposalQuerySql"); //指定使用的properties文件名
		mySql2.setSqlId(sqlid2);//指定使用的Sql的id
		mySql2.addSubPara(customerNo);//指定传入的参数
	     var aSQL =mySql2.getString();	
	
//    var aSQL = "select CustomerNo, Name from LDPerson where CustomerNo = '" + customerNo + "'";
    var arrResult = easyExecSql(aSQL);
    if (arrResult != null)
    {
        document.all('CustomerNo').value = arrResult[0][0];
        document.all('CustomerName').value = arrResult[0][1];
    }
}

function getContDetail()
{
    //alert();

}

function getPolInfo()
{
	
		    var sqlid3="ProposalQuerySql3";
		var mySql3=new SqlClass();
		mySql3.setResourceName("uw.ProposalQuerySql"); //指定使用的properties文件名
		mySql3.setSqlId(sqlid3);//指定使用的Sql的id
		mySql3.addSubPara(ContGrid.getRowColData(ContGrid.getSelNo() - 1, 1) );//指定传入的参数
	     var aSQL =mySql3.getString();	
	
//    var aSQL = "select distinct case conttype when '1' then a.contno when '2' then a.grpcontno end,"
//            + "a.polno,a.SignDate,b.riskcode,b.riskname,"
//            + " (case when exists(select endDate from LmriskApp where RiskCode=b.riskcode)"
//            + " then '在销' else '停售' end),"
//            + " a.amnt,a.mult,"
//            + " case a.uwflag when '5' then '自核不通过' when 'z' then '核保订正' else (select codename from ldcode where codetype='grpuwstate' and code=a.uwflag) end,"
//            + " (select nvl(sum(d.suppriskscore),0)from lcprem d where d.polno = a.polno and d.payplantype = '01')"
//            + " from lcpol a,lmrisk b,lcprem d "
//            + " where a.contno='" + ContGrid.getRowColData(ContGrid.getSelNo() - 1, 1) + "' "
//            + " and b.riskcode=a.riskcode and d.polno=a.polno and a.appflag not in ('9')";
    turnPage2.queryModal(aSQL, PolGrid);
}


function contInfoClick()
{
    getPolInfo();
    fm.button1.disabled = "";
    //判断是否有财务缴费信息

    //判断是否有核保结论
    //haveUWResult();
    //控制按钮的明暗
    ctrlButtonDisabled();

}

//判断是否有财务缴费信息
function haveFeeInfo()
{
    return;
}

//判断是否有影像资料
function havePicData()
{
    return;
}

//判断是否有核保结论
//function haveUWResult()
//{
//    var ContNo = ContGrid.getRowColData(ContGrid.getSelNo() - 1, 1);
//
//    var strSQL = "select prtno from LCCont where contno='" + ContNo + "'";
//    var arrResult = easyExecSql(strSQL);
//    var proposalcontno = arrResult[0][0];
//    var aSQL = "select * from LCCUWMaster where proposalcontno='" + proposalcontno + "'";
//    var arrResult = easyExecSql(aSQL);
//    if (arrResult != null)
//    {
//        fm.button4.disabled = "";
//        return;
//    }
//    else
//    {
//        fm.button4.disabled = "true";
//        return;
//    }
//}

function getContDetailInfo()
{
    var tsel = ContGrid.getSelNo() - 1;
    var tContType = ContGrid.getRowColData(tsel, 15);
    if (tsel < 0)
    {
        alert("请选择保单！");
        return;
    }

    if (ContGrid.getRowColData(ContGrid.getSelNo() - 1, 1) == '')
    {
        alert("没有已承保保单，不能进行查询！");
        return;
    }
    
    if (tContType == "1")
    {
        //var tSql = "select salechnl from lccont where contno='"+ContGrid.getRowColData(ContGrid.getSelNo()-1,1)+"'";
		
		var sqlid4="ProposalQuerySql4";
		var mySql4=new SqlClass();
		mySql4.setResourceName("uw.ProposalQuerySql"); //指定使用的properties文件名
		mySql4.setSqlId(sqlid4);//指定使用的Sql的id
		mySql4.addSubPara(ContGrid.getRowColData(ContGrid.getSelNo() - 1, 1));//指定传入的参数
	     var tSql =mySql4.getString();	
		
//        var tSql = "select case lmriskapp.riskprop"
//                + " when 'I' then"
//                + " '1'"
//                + " when 'G' then"
//                + " '2'"
//                + " when 'Y' then"
//                + " '3'"
//                + " end"
//                + " from lmriskapp"
//                + " where riskcode in (select riskcode"
//                + " from lcpol"
//                + " where polno = mainpolno"
//                + " and contno = '" + ContGrid.getRowColData(ContGrid.getSelNo() - 1, 1) + "')"
    
        var BankFlag = ""
        var brrResult = easyExecSql(tSql);
        if (brrResult != null)
        {
            BankFlag = brrResult[0][0];
        }
        window.open("../app/ProposalEasyScan.jsp?LoadFlag=6&prtNo=" + ContGrid.getRowColData(ContGrid.getSelNo() - 1, 12) + "&ContNo=" + ContGrid.getRowColData(ContGrid.getSelNo() - 1, 1) + "&BankFlag=" + BankFlag, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1;"+sFeatures);
    }
    
    if (tContType == "2")
    {
        cContNo=ContGrid.getRowColData(tsel,1);
        cPrtNo=ContGrid.getRowColData(tsel,10);;
        cGrpContNo=ContGrid.getRowColData(tsel,2);
        mSwitch.deleteVar( "ContNo" );
      	mSwitch.addVar( "ContNo", "", cContNo );
      	mSwitch.updateVar("ContNo", "", cContNo);
      	mSwitch.deleteVar( "ProposalContNo" );
      	mSwitch.addVar( "ProposalContNo", "", cContNo );
      	mSwitch.updateVar("ProposalContNo", "", cContNo);
      	mSwitch.deleteVar( "PrtNo" );
      	mSwitch.addVar( "PrtNo", "", cPrtNo );
      	mSwitch.updateVar("PrtNo", "", cPrtNo);
      	mSwitch.deleteVar( "GrpContNo" );
      	mSwitch.addVar( "GrpContNo", "", cGrpContNo );
      	mSwitch.updateVar("GrpContNo", "", cGrpContNo);
  	    //window.open("../sys/AllProQueryGMain.jsp?LoadFlag=16&Auditing=1","window1");
  		window.open("../sys/AllProQueryGMain.jsp?LoadFlag=16&checktype=2&ContType=2&Auditing=1&ProposalGrpContNo="+cGrpContNo+"&ContNo="+cContNo+"&NameFlag=0","window1",sFeatures);
    }
}

function showTempFee()
{
    var tsel = ContGrid.getSelNo() - 1;
    if (tsel < 0)
    {
        alert("请选择保单!");
        retutn;
    }
    var tAppntNo = ContGrid.getRowColData(tsel, 3);
    var tAppntName = ContGrid.getRowColData(tsel, 4);
    var tContType = ContGrid.getRowColData(tsel, 13);
    var tContNo = ContGrid.getRowColData(tsel,2);
    window.open("./UWTempFeeQryMain.jsp?PrtNo=" + ContGrid.getRowColData(ContGrid.getSelNo() - 1, 12) + "&AppntNo=" + tAppntNo + "&AppntName=" + tAppntName + "&ContType=" + tContType + "&ContNo=" + tContNo, "window11",sFeatures);
}

//影像资料查询
function showImage()
{

    var tsel = ContGrid.getSelNo() - 1;
    if (tsel < 0)
    {
        alert("请选择保单!");
        retutn;
    }
    var ContNo = ContGrid.getRowColData(tsel, 12);
    window.open("./ImageQueryMain.jsp?ContNo=" + ContNo, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1;"+sFeatures);

}


//核保查询
function UWQuery()
{
    var tsel = ContGrid.getSelNo() - 1;
    if (tsel < 0)
    {
        alert("请选择保单!");
        return;
    }
    var ContNo;
    var ContType = ContGrid.getRowColData(tsel, 15);
    
    if (ContType == "2")
    {
       ContNo = ContGrid.getRowColData(tsel, 1);   
    }
    else
    {
        ContNo = ContGrid.getRowColData(tsel, 12);   
    }
            
//    var strSQL = "select prtno from LCCont where contno='" + ContNo + "'";
//    var arrResult = easyExecSql(strSQL);
//    var proposalcontno = arrResult[0][0];
    window.open("./UWQueryMain1.jsp?ProposalContNo=" + ContNo + "&ContType=" + ContType, "window13",sFeatures);
}

//控制按钮的明暗	
function ctrlButtonDisabled() {
    var tContNo = ContGrid.getRowColData(ContGrid.getSelNo() - 1, 2);  //个单则是个单保单号，集体单则是集体单保单号
    var ttContNo = ContGrid.getRowColData(ContGrid.getSelNo() - 1, 1); //个人保单号
    var tPrtNo = ContGrid.getRowColData(ContGrid.getSelNo() - 1, 12);
//    var strSQL = "select prtno from LCCont where contno='" + tContNo + "'";
//    var arrResult = easyExecSql(strSQL);
//    var proposalcontno = arrResult[0][0];
    
    var tSQL = "";
    var arrResult;
    var arrButtonAndSQL = new Array;

    arrButtonAndSQL[0] = new Array;
    arrButtonAndSQL[0][0] = "Button1";
    arrButtonAndSQL[0][1] = "保单详细信息查询";
    arrButtonAndSQL[0][2] = "";

    arrButtonAndSQL[1] = new Array;
    arrButtonAndSQL[1][0] = "Button2";
    arrButtonAndSQL[1][1] = "影像资料查询";
    if(_DBT==_DBO){
    	arrButtonAndSQL[1][2] = "select * from es_doc_relation where bussno='" + tPrtNo + "' and bussnotype='11' and rownum=1";
    }else if(_DBT==_DBM){
    	arrButtonAndSQL[1][2] = "select * from es_doc_relation where bussno='" + tPrtNo + "' and bussnotype='11' limit 1";
    }

    arrButtonAndSQL[2] = new Array;
    arrButtonAndSQL[2][0] = "Button3";
    arrButtonAndSQL[2][1] = "保单交费查询";  //对于团单要查询团单交费情况
    if(_DBT==_DBO){
    	arrButtonAndSQL[2][2] = "select * from ljtempfee where otherno='" + tContNo + "' and rownum=1";
    }else if(_DBT==_DBM){
    	arrButtonAndSQL[2][2] = "select * from ljtempfee where otherno='" + tContNo + "' limit 1";
    }
   
    arrButtonAndSQL[3] = new Array;
    arrButtonAndSQL[3][0] = "Button6";
    arrButtonAndSQL[3][1] = "特约查询";
    if(_DBT==_DBO){
    	arrButtonAndSQL[3][2] = "select 1 from lcspec where contno in ('" + tPrtNo + "','" + ttContNo + "') and rownum=1 union select 1 from lccspec where contno in ('" + tPrtNo + "','" + ttContNo + "') and rownum=1";
    }else if(_DBT==_DBM){
    	arrButtonAndSQL[3][2] = "select 1 from lcspec where contno in ('" + tPrtNo + "','" + ttContNo + "') limit 1 union select 1 from lccspec where contno in ('" + tPrtNo + "','" + ttContNo + "') limit 1";
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
    var cPrtNo = ContGrid.getRowColData(tsel, 12);
    var cContType = ContGrid.getRowColData(tsel, 15);


    window.open("../sys/RecordQueryMain.jsp?ContNo=" + cContNo + "&PrtNo=" + cPrtNo+ "&ContType=" + cContType,"",sFeatures);


}


function SpecQuery() {
    //alert(1);
    var arrReturn = new Array();
    var tSel = ContGrid.getSelNo();

    if (tSel == 0 || tSel == null)
        alert("请先选择一条记录。");
    else {
        //alert(tSel);
        var ContNo = ContGrid.getRowColData(tSel - 1, 1);
        var PrtNo = ContGrid.getRowColData(tSel - 1, 10);

        if (PrtNo == "" || ContNo == "") return;

        window.open("../uw/UWManuSpecMain.jsp?ContNo=" + ContNo + "&InsuredNo="+customerNo+"&PrtNo=" + PrtNo + "&QueryFlag=1","",sFeatures);
    }


}



