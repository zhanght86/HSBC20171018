//该文件中包含客户端需要处理的函数和事件
var showInfo;
var selno;
var turnPage = new turnPageClass();          //使用翻页功能，必须建立为全局变量
var polflag = "0";
var PolNo = "";
var sqlresourcename = "bq.PEdorTypeXSInputSql";

function queryLRInfo()
{
//		var tSQL = "select c.losttimes from lccont c where c.contno ='"+fm.ContNo.value+"'";
		
	var tSQL = "";
	var sqlid1="PEdorTypeXSInputSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql1.setSqlId(sqlid1);//指定使用的Sql的id
	mySql1.addSubPara(fm.ContNo.value);//指定传入的参数
	tSQL=mySql1.getString();
		
		var ret = easyExecSql(tSQL);
		if(ret)
		{
			var tLostTimes = ret[0][0];
			if(tLostTimes > 0)
			{
				fm.TrueLostTimes.value = tLostTimes;
				divLRInfo.style.display="";
			}
			else
			{
				fm.TrueLostTimes.value = 0;
			}
		}
}

function isExemptRisk(tRiskCode) {
	//1为投保人豁免 2为被保人豁免
//	var SQL = "select 1 from lmriskapp where riskcode = '"+tRiskCode+"' and risktype7 in ('1','2')";
	
	var SQL = "";
	var sqlid2="PEdorTypeXSInputSql2";
	var mySql2=new SqlClass();
	mySql2.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql2.setSqlId(sqlid2);//指定使用的Sql的id
	mySql2.addSubPara(tRiskCode);//指定传入的参数
	SQL=mySql2.getString();
	
	var strResult = easyExecSql(SQL);
        if(strResult != null) {
        	return true;
        }
        else {
        	return false;
        }
}

function reduceOneRisk()
{
    polflag = "1";
    selno = PolGrid.getSelNo()-1;
    if (selno <0)
    {
        alert("请选择要减保的险种！");
        return;
    }
    
    if (fm.SurrReasonCode.value == null || fm.SurrReasonCode.value == "")
    {
        alert("请录入退保原因!");
        return;
    }
    else {
    	//add by jiaqiangli 2009-05-23
    	if (fm.SurrReasonCode.value == "05" && (fm.ReasonContent.value == null || fm.ReasonContent.value == "")) {
    		alert("请在备注中录入更加详细的其他退保原因!");
        	return;
    	}
    	//add by jiaqiangli 2009-05-23
    }
    
    //add by jiaqiangli 2008-11-20 豁免险种自动关联
    var sRiskCode = PolGrid.getRowColData(selno, 1);
    if (isExemptRisk(sRiskCode) == true) {
    	alert("豁免险种自动关联！");
    	return;
    }

    var mtempt = PolGrid.getRowColData(selno, 4);
    PolNo = PolGrid.getRowColData(selno, 10);

    var strRN = "select * from ljapayperson where polno = '"+PolNo+"'";
    var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;

    //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

    document.all('fmtransact').value = "INSERT||MAIN";
    fm.action = "PEdorTypeXSSubmit.jsp";
    fm.submit();
}

function saveEdorTypePT()
{
    if (!isReducedPol())      return;
    //if (!checkIsReissue())    return;
    
    if(fm.TrueLostTimes.value > 0 )
    	{
    		if(fm.LostTimes.value == null || fm.LostTimes.value == "")
    		{
    			alert("该保单有补发记录，请录入补发记录！");
    			fm.LostTimes.focus();
    			return;
    		}
    		
    		if(fm.LostTimes.value != fm.TrueLostTimes.value)
    		{
    			alert("输入的补发次数不正确请核实！");
    			return;
    		}
    	}
    
    //协议金额的校验 add by jiaqiangli 2008-09-17
    var rowNum=CTFeePolGrid.mulLineCount;
    if(rowNum > 0)
    {
    	var i;
    	for(i = 0 ; i <rowNum ; i++)
    	{
    		var inputNum = CTFeePolGrid.getRowColData(i,5);
    		var sourceNum = CTFeePolGrid.getRowColData(i,4);
    		//alert(inputNum);
    		if(!isNumeric(inputNum))
    		{
    			alert("调整金额请录入合法数字！")
    			return;
    		}
    		//comment by jiaqiangli 2008-09-16 调整金额有可能大于应退金额，做权限控制
//    		if (inputNum > sourceNum) {
//	    		if (!confirm("调整金额大于应退金额，是否继续退保？"))
//	        	{
//	            		return;
//	        	}
//        	}
    	}
    }
    //add by jiaqiangli 2008-09-17
    
    
    //add by jiaqiangli 2009-05-16
    //整单协议减保时增加提示
/*    var tZDFlagSQL = "select case when (select count(1) from lcpol where contno='"+fm.ContNo.value+"' and appflag='1')"
    			   + "=(select count(1) from lppol where contno='"+fm.ContNo.value+"' and edortype='XS' and edorno='"+document.all('EdorNo').value+"') then 0 else 1 end from dual";
*/	
	var tZDFlagSQL = "";
	var sqlid3="PEdorTypeXSInputSql3";
	var mySql3=new SqlClass();
	mySql3.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql3.setSqlId(sqlid3);//指定使用的Sql的id
	mySql3.addSubPara(fm.ContNo.value);//指定传入的参数
	mySql3.addSubPara(fm.ContNo.value);
	mySql3.addSubPara(document.all('EdorNo').value);
	tZDFlagSQL=mySql3.getString();
	
	var tZDFlag = easyExecSql(tZDFlagSQL, 1, 0,1);
	if (tZDFlag != null && tZDFlag != "" && tZDFlag== "0") {
//			var tYJFlagSQL="select case when (select nvl(sum(leavingmoney),0) from lcpol where contno='"+fm.ContNo.value+"' and appflag='1')>0 then 0 else 1 end from dual";
			
	var tYJFlagSQL = "";
	var sqlid4="PEdorTypeXSInputSql4";
	var mySql4=new SqlClass();
	mySql4.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql4.setSqlId(sqlid4);//指定使用的Sql的id
	mySql4.addSubPara(fm.ContNo.value);//指定传入的参数
	tYJFlagSQL=mySql4.getString();
			
			var tYJFlag=easyExecSql(tYJFlagSQL, 1, 0,1);
			if (tYJFlag != null && tYJFlag != "" && tYJFlag== "0") {
				alert("提示：该保单下存在溢交保费！");
			}
//			var tHLFlagSQL="select case when (select nvl(sum(money),0) from lcinsureacctrace where contno='"+fm.ContNo.value+"' and insuaccno in ('000001','000007','000008'))>0 then 0 else 1 end from dual";
			
	var tHLFlagSQL = "";
	var sqlid5="PEdorTypeXSInputSql5";
	var mySql5=new SqlClass();
	mySql5.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql5.setSqlId(sqlid5);//指定使用的Sql的id
	mySql5.addSubPara(fm.ContNo.value);//指定传入的参数
	tHLFlagSQL=mySql5.getString();
			
			var tHLFlag=easyExecSql(tHLFlagSQL, 1, 0,1);
			if (tHLFlag != null && tHLFlag != "" && tHLFlag== "0") {
				alert("提示：该保单下存在应领红利！");
			}
//			var tYFFlagSQL="select case when (select nvl(sum(INSUACCBALA),0) from lcinsureacc where contno='"+fm.ContNo.value+"' and insuaccno in ('000005','000009'))>0 then 0 else 1 end from dual";
			
	var tYFFlagSQL = "";
	var sqlid6="PEdorTypeXSInputSql6";
	var mySql6=new SqlClass();
	mySql6.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql6.setSqlId(sqlid6);//指定使用的Sql的id
	mySql6.addSubPara(fm.ContNo.value);//指定传入的参数
	tYFFlagSQL=mySql6.getString();
			
			var tYFFlag=easyExecSql(tYFFlagSQL, 1, 0,1);
			if (tYFFlag != null && tYFFlag != "" && tYFFlag== "0") {
				alert("提示：该保单下存在应领给付金！");
			}  
//			var tLoanFlagSQL="select case when (select nvl(sum(leavemoney),0) from loloan where contno='"+fm.ContNo.value+"' and loantype='0' and payoffflag='0')>0 then 0 else 1 end from dual";
			
	var tLoanFlagSQL = "";
	var sqlid7="PEdorTypeXSInputSql7";
	var mySql7=new SqlClass();
	mySql7.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql7.setSqlId(sqlid7);//指定使用的Sql的id
	mySql7.addSubPara(fm.ContNo.value);//指定传入的参数
	tLoanFlagSQL=mySql7.getString();
			
			var tLoanFlag=easyExecSql(tLoanFlagSQL, 1, 0,1);
			if (tLoanFlag != null && tLoanFlag != "" && tLoanFlag== "0") {
				alert("提示：该保单下存在保单借款！");
			}
	}
    //add by jiaqiangli 2009-05-16
    
    polflag = "0";
    var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    document.all('fmtransact').value = "EdorSave";
    fm.action = "./PEdorTypeXSSubmit.jsp";
    fm.submit();
}

function queryCustomerInfo()
{
    var tContNo=top.opener.document.all('ContNo').value;
    var strSQL="";
    if(tContNo!=null || tContNo !='')
    {
/*        strSQL = " Select a.appntno,'投保人',a.appntname,a.appntsex||'-'||sex.codename,a.appntbirthday,a.idtype||'-'||x.codename,a.idno From lcappnt a "
                                        +" Left Join (Select code,codename From ldcode Where codetype='idtype') x On x.code = a.idtype "
                                        +" Left Join (Select code,codename From ldcode Where codetype='sex') sex On sex.code = a.appntsex  Where contno='"+tContNo+"'"
                                        +" Union"
                                        +" Select i.insuredno,'被保人',i.name,i.Sex||'-'||sex.codename,i.Birthday,i.IDType||'-'||xm.codename,i.IDNo From lcinsured i "
                                        +" Left Join (Select code,codename From ldcode Where codetype='idtype') xm On xm.code = i.idtype "
                                        +" Left Join (Select code,codename From ldcode Where codetype='sex') sex On sex.code = i.sex Where contno='"+tContNo+"'";
*/    
	var sqlid8="PEdorTypeXSInputSql8";
	var mySql8=new SqlClass();
	mySql8.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql8.setSqlId(sqlid8);//指定使用的Sql的id
	mySql8.addSubPara(tContNo);//指定传入的参数
	mySql8.addSubPara(tContNo);
	strSQL=mySql8.getString();
    
    }
    else
    {
        alert('没有相应的投保人或被保人信息！');
    }
    var arrSelected = new Array();
    turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);

    //判断是否查询成功
    if (!turnPage.strQueryResult) {
        //alert("没有相应的投保人或被保人信息！");
        return false;
    }
    //清空数据容器，两个不同查询共用一个turnPage对象时必须使用，最好加上，容错
    turnPage.arrDataCacheSet = clearArrayElements(turnPage.arrDataCacheSet);
    //查询成功则拆分字符串，返回二维数组
    turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
    //设置初始化过的MULTILINE对象，VarGrid为在初始化页中定义的全局变量
    turnPage.pageDisplayGrid = CustomerGrid;
    //保存SQL语句
    turnPage.strQuerySql = strSQL;
    //设置查询起始位置
    turnPage.pageIndex = 0;
    //在查询结果数组中取出符合页面显示大小设置的数组
    arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
    //调用MULTILINE对象显示查询结果
    displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit(FlagStr, content,Result)
{
    showInfo.close();
    //alert(document.all('Flag266').value);
    if(document.all('Flag266').value != "NO")
    {
        show266();
    }
    document.all('Flag266').value = "NO";
    document.all('Flag267').value = "NO";
    var urlStr;
    if (FlagStr == "Success")
    {
        urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content;
        //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=350;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
    }
    else if (FlagStr == "Fail")
    {
        urlStr="../common/jsp/MessagePage.jsp?picture=F&content=" + content;
        //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
    }
    else
    {
        urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content;
        //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:300px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=300;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
    }
    //alert(selno);

        if (FlagStr == "Success")
    {
        var iArray;
        //清空数据容器，两个不同查询共用一个turnPage对象时必须使用，最好加上，容错
        turnPage.arrDataCacheSet = clearArrayElements(turnPage.arrDataCacheSet);
        //保存查询结果字符串
        turnPage.strQueryResult  = Result;

        //查询成功则拆分字符串，返回二维数组
        var tArr   = decodeEasyQueryResult(turnPage.strQueryResult,0);
        //alert(Result);
        //polflag = "1";
        
        getPolInfo();
        queryBackFee();
        top.opener.getEdorItemGrid();
	
	    //add by jiaqiangli 2008-09-16
	    queryShouldMoney();
        queryAdjustMoney();
        getCTFeePolGrid();
        //alert("getCTFeeDetailGrid"+getCTFeeDetailGrid);
        getCTFeeDetailGrid();
    }
}

/**
 * 返回主界面
 */
function returnParent()
{
    try
    {
        top.close();
        top.opener.focus();
    }
    catch (ex) {}
}


function showNotePad()
{
    var MissionID = top.opener.document.all("MissionID").value;
    var SubMissionID = top.opener.document.all("SubMissionID").value;;
    var ActivityID = "0000000003";
    var OtherNo = top.opener.document.all("OtherNo").value;;

    var OtherNoType = "1";
    if(MissionID == null || MissionID == "")
    {
        alert("MissionID为空！");
        return;
    }
    var varSrc= "&MissionID="+ MissionID + "&SubMissionID="+ SubMissionID + "&ActivityID="+ ActivityID + "&PrtNo="+ OtherNo + "&NoType="+ OtherNoType;
    var newWindow = OpenWindowNew("../uw/WorkFlowNotePadFrame.jsp?Interface= ../uw/WorkFlowNotePadInput.jsp" + varSrc,"工作流记事本查看","left");
}

function getPolInfo()
{
    var tContNo = document.all("ContNo").value;
    var tConfDate = document.all("EdorItemAppDate").value;
    var tEdorNo = document.all("EdorNo").value;//Add By QianLy on 2006-9-23
    if(tContNo!=null&&tContNo!="")
    {
        //var strSQL = "Select distinct m.riskcode,m.riskname,c.INSUREDNAME,case when u.amntflag = 1 then p.amnt else p.mult end, p.prem,'','','',c.Insuredno,p.polno,u.amntflag From lcpol p Left Join lmrisk m on m.riskcode = p.riskcode Left Join LCCONT c On c.contno= p.contno Left Join LCDuty d on d.polno = p.polno Left Join LMDuty u on trim(u.dutycode) = substr(trim(d.dutycode),1,6) where ((p.appflag = '1') or (p.appflag = '9' and p.cvalidate + 60 >= '" + tConfDate + "')) and p.cvalidate <= '" + tConfDate + "' and enddate > '" + tConfDate + "' and c.contno = '"+tContNo+"' and polno not in (select polno from lccontstate where statetype = 'Terminate' and state = '1' and enddate is null and contno = c.contno)";
        //Modified By QianLy on 2006-9-23------------------
/*        var strSQL = "Select distinct m.riskcode,"
                   + " m.riskname,"
                   + " c.INSUREDNAME,"
                   + " case when u.amntflag = 1 then a.amnt else a.mult end,"
                   + " a.prem,"
                   + " case"
                   + "   when u.amntflag = 1 then"
                   + "    nvl((select amnt"
                   + "     from lppol"
                   + "    where 1 = 1"
                   + "      and polno = a.polno"
                   + "      and edorno = '"+tEdorNo+"'),a.amnt)"
                   + "   else"
                   + "      nvl((select mult"
                   + "        from lppol"
                   + "        where 1 = 1"
                   + "         and polno = a.polno"
                   + "         and edorno = '"+tEdorNo+"'),a.mult)"
                   + " end,"
                   + " '',"
                   + " nvl((select prem"
                   + "        from lppol"
                   + "       where polno = a.polno"
                   + "         and edorno ='"+tEdorNo+"'),"
                   + " a.prem),"
                   + " c.Insuredno,"
                   + " a.polno,"
                   + " u.amntflag,"
                   //add by jiaqiangli 2008-11-20 PTflag 减少标志 PTFlag 0减少保费1减少份数2减少保额
                   + " (case when u.calmode='P' then '0' when u.calmode='O' then '1' else '2' end)"
                   + " From lcpol a"
                   + " Left Join lmrisk m on m.riskcode = a.riskcode"
                   + " Left Join LCCONT c On c.contno = a.contno"
                   + " Left Join LCDuty d on d.polno = a.polno"
                   + " Left Join LMDuty u on trim(u.dutycode) = substr(trim(d.dutycode), 1, 6)"
                   + " where 1 = 1"
                   + " and a.appflag = '1' "
                   //+ " and ((a.appflag = '1') or"
                   //+ " (a.appflag = '9' and a.cvalidate + 60 >= '" + tConfDate + "'))"
                   + " and a.cvalidate <= '" + tConfDate + "'"
                   + " and enddate > '" + tConfDate + "'"
                   + " and a.contno = '"+tContNo+"'"
                   + " and d.contno = '"+tContNo+"'"
                   + " and c.contno = '"+tContNo+"'"
                   + " and polno not in (select polno"
                   + "                     from lccontstate"
                   + "                    where statetype = 'Terminate'"
                   + "                      and state = '1'"
                   + "                      and enddate is null"
                   + "                      and contno = c.contno)"
                   + " order by a.PolNo asc";
                   //prompt("strSQL",strSQL);
         //----------------------
*/        
    var strSQL = "";
    var sqlid9="PEdorTypeXSInputSql9";
	var mySql9=new SqlClass();
	mySql9.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql9.setSqlId(sqlid9);//指定使用的Sql的id
	mySql9.addSubPara(tEdorNo);//指定传入的参数
	mySql9.addSubPara(tEdorNo);
	mySql9.addSubPara(tEdorNo);//指定传入的参数
	mySql9.addSubPara(tConfDate);
	mySql9.addSubPara(tConfDate);//指定传入的参数
	mySql9.addSubPara(tContNo);
	mySql9.addSubPara(tContNo);//指定传入的参数
	mySql9.addSubPara(tContNo);
	strSQL=mySql9.getString();
        
        turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
        //判断是否查询成功
        if (!turnPage.strQueryResult)
        {
            alert("没有投保险种信息！");
            return false;
        }
        //查询成功则拆分字符串，返回二维数组
        turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
        //设置初始化过的MULTILINE对象
        turnPage.pageDisplayGrid = PolGrid;
        //保存SQL语句
        turnPage.strQuerySql = strSQL;
        //设置查询起始位置
        turnPage.pageIndex = 0;
        //在查询结果数组中取出符合页面显示大小设置的数组
        arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
        //调用MULTILINE对象显示查询结果
        displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
    }
}

function QueryRiskDetail()
{
    var tContNo=top.opener.document.all('ContNo').value;
    var strSQL=""
    if(tContNo!=null || tContNo !='')
    {
/*        strSQL = "select distinct riskcode, (select riskname from lmrisk where lmrisk.riskcode=lcpol.riskcode),paytodate,mult,Amnt,sumprem,paymode from lcpol WHERE 1=1 AND "
                            +"CONTNO='"+tContNo+"'";
*/    
    var sqlid10="PEdorTypeXSInputSql10";
	var mySql10=new SqlClass();
	mySql10.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql10.setSqlId(sqlid10);//指定使用的Sql的id
	mySql10.addSubPara(tContNo);//指定传入的参数
	strSQL=mySql10.getString();
    
    }
    else
    {
        alert('没有客户信息！');
    }
    var arrSelected = new Array();
    turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
    //arrResult = easyExecSql("select * from LDGrp where GrpNo = '" + arrQueryResult[0][0] + "'", 1, 0);
    if(!turnPage.strQueryResult){
        alert("查询失败");
    }
    else
    {
        arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
        try {document.all('riskcode').value = arrSelected[0][0];} catch(ex) { }; //投保人姓名
        try {document.all('riskname').value = arrSelected[0][1];} catch(ex) { }; //投保人证件类型
        try {document.all('paytodate').value = arrSelected[0][2];} catch(ex) { }; //投保人证件号码
        try {document.all('mult').value = arrSelected[0][3];} catch(ex) { }; //被保人名称
        try {document.all('Amnt').value = arrSelected[0][4];} catch(ex) { }; //被保人证件类型
        try {document.all('sumprem').value = arrSelected[0][5];} catch(ex) { }; //被保人证件号码
        try {document.all('paymode').value = arrSelected[0][6];} catch(ex) {};

        //showOneCodeName('idtype','AppntIDType','AppntIDTypeName');
        //showOneCodeName('idtype','InsuredIDType','InsuredIDTypeName');
    }
}

function Edorquery()
{
    try
    {
        var ButtonFlag = top.opener.document.all('ButtonFlag').value;

        if(ButtonFlag!=null && ButtonFlag=="1")
        {
           divEdorquery.style.display = "none";
           divPTbuttion.style.display = "none";
        }
        else
        {
           divPTReturn.style.display = "none";
           divEdorquery.style.display = "";
        }
    }
    catch(e){}
}

//<!-- XinYQ added on 2006-07-24 : BGN -->
/*============================================================================*/

/**
 * 保全复核时显示减保原因和减保保额
 */
function queryEdorInfo()
{
    try
    {
        queryEdorReasonAndAppnt();
        calcMinusNumber();
    }
    catch (ex) {}
}

function queryEdorReasonAndAppnt(){
    //减保原因
    var QuerySQL, arrResult;
/*    QuerySQL = "select a.EdorReasonCode, "
             +        "(select CodeName "
             +           "from LDCode "
             +          "where 1 = 1 "
             +            "and CodeType = 'xsurrordereason' "
             +            "and Code = a.EdorReasonCode) "
             + ",a.standbyflag2,(select codename from ldcode where codetype='relationtoappnt' and code=a.standbyflag2),EdorReason "
             +   "from LPEdorItem a "
             +  "where 1 = 1 "
             + getWherePart("a.EdorAcceptNo", "EdorAcceptNo")
             + getWherePart("a.EdorNo", "EdorNo")
             + getWherePart("a.EdorType", "EdorType");
*/             
    var sqlid11="PEdorTypeXSInputSql11";
	var mySql11=new SqlClass();
	mySql11.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql11.setSqlId(sqlid11);//指定使用的Sql的id
	mySql11.addSubPara(fm.EdorAcceptNo.value);//指定传入的参数
	mySql11.addSubPara(fm.EdorNo.value);
	mySql11.addSubPara(fm.EdorType.value);
	QuerySQL=mySql11.getString();
             
    try{
        arrResult = easyExecSql(QuerySQL, 1, 0);
    }catch (ex){
        alert("警告：查询保全减保原因出现异常！ ");
        return;
    }
    if (arrResult != null){
        try{
            document.getElementsByName("SurrReasonCode")[0].value = arrResult[0][0];
            document.getElementsByName("SurrReasonName")[0].value = arrResult[0][1];
            
            document.getElementsByName("RelationToAppnt")[0].value = arrResult[0][2];
            document.getElementsByName("RelationToAppntName")[0].value = arrResult[0][3];
            
            document.getElementsByName("ReasonContent")[0].value = arrResult[0][4];
        }catch (ex) {}
    }
}

/**
 * 保全复核时显示减保保额
 */
function calcMinusNumber()
{
    var nSelNo;
    try
    {
        nSelNo = PolGrid.getSelNo() - 1;
    }
    catch (ex) {}
    if (nSelNo == null || nSelNo < 0)
    {
        return;
    }
    else
    {
        var sOldMinusNumber, sNewMinusNumber;
        
        var sPTFlag = PolGrid.getRowColData(nSelNo, 12);
        
        try
        {
            sOldMinusNumber = PolGrid.getRowColData(nSelNo, 4);
            sNewMinusNumber = PolGrid.getRowColData(nSelNo, 6);
            
            //减保费 保费相减
            if (sPTFlag != null && sPTFlag == '0') {
            	sOldMinusNumber = PolGrid.getRowColData(nSelNo, 5);
                sNewMinusNumber = PolGrid.getRowColData(nSelNo, 8);
            }
        }
        catch (ex) {}
        if (sOldMinusNumber == null || trim(sOldMinusNumber) == "" || sNewMinusNumber == null || trim(sNewMinusNumber) == "")
        {
            return;
        }
        else
        {
            var fOldMinusNumber, fNewMinusNumber, fFinalMinusNumber;
            try
            {
                fOldMinusNumber = parseFloat(sOldMinusNumber);
                fNewMinusNumber = parseFloat(sNewMinusNumber);
                fFinalMinusNumber = eval((fOldMinusNumber - fNewMinusNumber).toFixed(2));
                
                //区分分支情况 PTFlag 0减少保费1减少份数2减少保额
                if (sPTFlag != null && sPTFlag == '0') {
                	document.getElementsByName("MinusPTPrem")[0].value = fFinalMinusNumber;
                	PTPremDiv.style.display = "";
                	PTAmntDiv.style.display = "none";
                	PTMutDiv.style.display = "none";
                }
                else if (sPTFlag != null && sPTFlag == '1') {
                	document.getElementsByName("MinusPTMut")[0].value = fFinalMinusNumber;
                	PTPremDiv.style.display = "none";
                	PTAmntDiv.style.display = "none";
                	PTMutDiv.style.display = "";
                }
                else if (sPTFlag != null && sPTFlag == '2') {
                	document.getElementsByName("MinusPTAmnt")[0].value = fFinalMinusNumber;
                	PTPremDiv.style.display = "none";
                	PTAmntDiv.style.display = "";
                	PTMutDiv.style.display = "none";
                }
            }
            catch (ex) {}
        }
    } //nSelNo != null
}

/**
 * 点"减保"之后才允许"保存"
 */
function isReducedPol()
{
    var QuerySQL, arrResult;
/*    QuerySQL = "select 'X' "
             +   "from LPPol "
             +  "where 1 = 1 "
             +     getWherePart("EdorNo", "EdorNo")
             +     getWherePart("EdorType", "EdorType")
             +     getWherePart("ContNo", "ContNo");
    //alert(QuerySQL);
*/    
    var sqlid12="PEdorTypeXSInputSql12";
	var mySql12=new SqlClass();
	mySql12.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql12.setSqlId(sqlid12);//指定使用的Sql的id
	mySql12.addSubPara(fm.EdorNo.value);//指定传入的参数
	mySql12.addSubPara(fm.EdorType.value);
	mySql12.addSubPara(fm.ContNo.value);
	QuerySQL=mySql12.getString();
    
    try
    {
        arrResult = easyExecSql(QuerySQL, 1, 0);
    }
    catch (ex)
    {
        alert("警告：检查保单是否已经减保出现异常！ ");
        return;
    }
    if (arrResult == null)
    {
        alert("必须先对某险种减保才允许保存变更结果！ ")
        return false;
    }
    return true;
}

/**
 * 检查是否是补发打印保单
 */
function checkIsReissue()
{
    var QuerySQL, arrResult;
    //查询是否做过保单补发
/*    QuerySQL = "select 'X' "
             +   "from LPEdorItem "
             +  "where 1 = 1 "
             +  getWherePart("ContNo", "ContNo")
             +    "and EdorType = 'LR' "
             +    "and EdorState = '0'";
    //alert(QuerySQL);
*/    
    var sqlid13="PEdorTypeXSInputSql13";
	var mySql13=new SqlClass();
	mySql13.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql13.setSqlId(sqlid13);//指定使用的Sql的id
	mySql13.addSubPara(fm.ContNo.value);//指定传入的参数
	QuerySQL=mySql13.getString();
    
    try
    {
        arrResult = easyExecSql(QuerySQL, 1, 0);
    }
    catch (ex)
    {
        alert("警告：查询是否做过保单补发出现异常！ ");
        return true;
    }
    if (arrResult != null)
    {
        var sConfirmMsg = "该保单做过补发，";
        //查询补发保单打印日期
/*        QuerySQL = "select distinct max(MakeDate) "
                 +   "from LDContInvoiceMap "
                 +  "where 1 = 1 "
                 +  getWherePart("ContNo", "ContNo")
                 +    "and OperType = '4'";
        //alert(QuerySQL);
*/        
    var sqlid14="PEdorTypeXSInputSql14";
	var mySql14=new SqlClass();
	mySql14.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql14.setSqlId(sqlid14);//指定使用的Sql的id
	mySql14.addSubPara(fm.ContNo.value);//指定传入的参数
	QuerySQL=mySql14.getString();
        
        try
        {
            arrResult = easyExecSql(QuerySQL, 1, 0);
        }
        catch (ex)
        {
            alert("警告：查询补发或重新出单的打印日期出现异常！ ");
            return true;
        }
        if (arrResult != null && trim(arrResult[0][0]) != "")
        {
            sConfirmMsg += "最近一次打印日期为 " + trim(arrResult[0][0]) + "，";
        }
        sConfirmMsg += "是否继续减保？ ";
        //确认提示减保
        if (!confirm(sConfirmMsg))
        {
            return false;
        }
    }
    return true;
}

function getCTFeePolGrid()
{
	    var tEdorAcceptNo=fm.EdorAcceptNo.value;
/*      var QuerySQL = "  select a.polno , "
                 +                "(select riskcode from lcpol where polno = a.polno) , "
                 +                "(select RiskName "
                 +                   "from LMRisk "
                 +                  "where RiskCode = "
                 +                        "(select riskcode from lcpol where polno = a.polno)), "
                 //ljsgetendorse.getmoney为调整的实退金额
                 +                "a.GetMoney,"
                 //lpedoritem.getmoney为调整的实退金额
                 +                "a.GetMoney "
                 //+                "(select nvl(GetMoney,0) from LPEdorItem where EdorAcceptNo='"+tEdorAcceptNo+"' and EdorType='XS')"
                 +           " from ljsgetendorse a  "
                 +          "  where 1 = 1  and subfeeoperationtype in ('G006','G001') "
                 +             getWherePart("a.EndorsementNo", "EdorNo")
                 +  getWherePart("a.ContNo", "ContNo")
                 //add by jiaqiangli 增加排序类型
                 +           "order by a.polno, a.feefinatype, a.subfeeoperationtype ";
*/     
    var QuerySQL = "";
    var sqlid15="PEdorTypeXSInputSql15";
	var mySql15=new SqlClass();
	mySql15.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql15.setSqlId(sqlid15);//指定使用的Sql的id
	mySql15.addSubPara(fm.EdorNo.value);//指定传入的参数
	mySql15.addSubPara(fm.ContNo.value);
	QuerySQL=mySql15.getString();
     
     var arrResult1 = easyExecSql(QuerySQL,1,0,1);
     if (arrResult1)
    {
        displayMultiline(arrResult1, CTFeePolGrid);
        var rowNum=CTFeePolGrid.mulLineCount; 
        if(rowNum > 0)
        {
        	//fm.HasCal.value = 'Y';
        	divCTFeePolDetailButton.style.display="";
        	divCTFeePolDetail.style.display="";
        }
    }
}

/*============================================================================*/

/**
 * XinYQ rewrote on 2007-06-05
 */
function getCTFeeDetailGrid()
{
/*    var QuerySQL = "              select polno,  "
                 +                "(select riskcode from lcpol where polno = a.polno) , "
                 +                "(select RiskName "
                 +                   "from LMRisk "
                 +                  "where RiskCode = "
                 +                        "(select riskcode from lcpol where polno = a.polno)) , "
                 +                "(select codename "
                 +                   "from ldcode "
                 +                  "where 1 = 1 "
                 +                    "and codetype = 'finfeetype' "
                 +                    "and code = a.feefinatype) , "
                 +                "(select codename "
                 +                   "from ldcode "
                 +                  "where 1 = 1 "
                 +                    "and codetype = 'BQSubFeeType' "
                 +                    "and code = a.subfeeoperationtype) , "
                 //ljsgetendorse.getmoney为调整的实退金额
                 +                "a.GetMoney , "                
                 +                "a.feefinatype , "
                 +                "a.subfeeoperationtype "
                 +           " from ljsgetendorse a "
                 +          " where 1 = 1 "
                 +             getWherePart("a.EndorsementNo", "EdorNo")
                 +  getWherePart("a.ContNo", "ContNo")
                 +          " order by a.polno, a.feefinatype, a.subfeeoperationtype ";
*/    
    var QuerySQL = "";
    var sqlid16="PEdorTypeXSInputSql16";
	var mySql16=new SqlClass();
	mySql16.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql16.setSqlId(sqlid16);//指定使用的Sql的id
	mySql16.addSubPara(fm.EdorNo.value);//指定传入的参数
	mySql16.addSubPara(fm.ContNo.value);
	QuerySQL=mySql16.getString();
    
    var arrResult = easyExecSql(QuerySQL);
    if (arrResult)
    {
        displayMultiline(arrResult, CTFeeDetailGrid);
        var rowNum=CTFeeDetailGrid.mulLineCount;
        if(rowNum > 0)
        {
        	//fm.HasCal.value = 'Y';
        	divCTFeeDetailButton.style.display="";
        	divCTFeeDetail.style.display="";
        }
    }
}

/**
 * 查询已经计算过的应退金额
 */
function queryShouldMoney()
{
//	alert("come to queryShouldMoney");
//	      //应退金额
//	      var tFLagSQL = "  select a.getFlag,a.GetMoney "
//                 +           " from ljsgetendorse a  "
//                 +          "  where 1 = 1 and OtherNo ='000000'"
//                 +             getWherePart("a.EndorsementNo", "EdorNo");                 
//        var arrResult = easyExecSql(tFLagSQL,1,0,1);
//        var tPreMoney=0; 
//        prompt("tFLagSQL",tFLagSQL);      
//        if(arrResult)
//        {
//        for(var i=0;i<arrResult.length;i++)
//        {
//        	    var tTempMoney=0;
//        	    if(arrResult[i][0]=='0'){  	 //Pay 补费         
//        	     tTempMoney=-parseFloat(arrResult[i][1]);
//        	    }
//        	    if(arrResult[i][0]=='1')
//        	    {
//        	    	tTempMoney=parseFloat(arrResult[i][1]);
//        	    }
//            tPreMoney+=tTempMoney;
//            alert("tPreMoney"+tPreMoney);
//        }
//        fm.GetMoney.value=mathRound(tPreMoney);  
//        }else
        	{
      var tEdorAcceptNo=fm.EdorAcceptNo.value; 
      //comment by jiaqiangli 2008-09-16 保存差值=实退-应退 所以 应退 = 实退 - 差值
//      var tSQL="select nvl(StandbyFlag3,0) from LPEdorItem where EdorAcceptNo='"+tEdorAcceptNo+"' and EdorType='XS'";
      
    var tSQL = "";
    var sqlid17="PEdorTypeXSInputSql17";
	var mySql17=new SqlClass();
	mySql17.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql17.setSqlId(sqlid17);//指定使用的Sql的id
	mySql17.addSubPara(tEdorAcceptNo);//指定传入的参数
	tSQL=mySql17.getString();
      
      var arrResult3 = easyExecSql(tSQL,1,0,1);
      fm.GetMoney.value=mathRound(arrResult3);      
      }
}

/*============================================================================*/

/**
 * 查询已经保存过的退费金额
 */
function queryAdjustMoney()
{
	  var tEdorAcceptNo=fm.EdorAcceptNo.value;
	  //实退合计值 
 //     var tSql="select nvl(GetMoney,0) from LPEdorItem where EdorAcceptNo='"+tEdorAcceptNo+"' and EdorType='XS'";
      
    var tSql = "";
    var sqlid18="PEdorTypeXSInputSql18";
	var mySql18=new SqlClass();
	mySql18.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql18.setSqlId(sqlid18);//指定使用的Sql的id
	mySql18.addSubPara(tEdorAcceptNo);//指定传入的参数
	tSql=mySql18.getString();
      
      var arrResult4 = easyExecSql(tSql,1,0,1);
      if(arrResult4)
      {
      fm.AdjustMoney.value=arrResult4; 
    }
}