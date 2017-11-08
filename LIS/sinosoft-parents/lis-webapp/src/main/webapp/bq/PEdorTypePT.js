//该文件中包含客户端需要处理的函数和事件
var showInfo;
var selno;
var turnPage = new turnPageClass();          //使用翻页功能，必须建立为全局变量
var polflag = "0";
var PolNo = "";
var sqlresourcename = "bq.PEdorTypePTInputSql";

function isExemptRisk(tRiskCode) {
	//1为投保人豁免 2为被保人豁免
//	var SQL = "select 1 from lmriskapp where riskcode = '"+tRiskCode+"' and risktype7 in ('1','2')";
	
	var SQL = "";
	var sqlid1="PEdorTypePTInputSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql1.setSqlId(sqlid1);//指定使用的Sql的id
	mySql1.addSubPara(tRiskCode);//指定传入的参数
	SQL=mySql1.getString();
	
	var strResult = easyExecSql(SQL);
        if(strResult != null) {
        	return true;
        }
        else {
        	return false;
        }
}

function queryLRInfo()
{
//		var tSQL = "select c.losttimes from lccont c where c.contno ='"+fm.ContNo.value+"'";
		
	var tSQL = "";
	var sqlid2="PEdorTypePTInputSql2";
	var mySql2=new SqlClass();
	mySql2.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql2.setSqlId(sqlid2);//指定使用的Sql的id
	mySql2.addSubPara(fm.ContNo.value);//指定传入的参数
	tSQL=mySql2.getString();
		
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

function reduceOneRisk()
{
	//lpedoritem.getmoney的汇总已经放在保存按钮里
    //if (isSavePol() == true)      return;
    
    polflag = "1";
    selno = PolGrid.getSelNo()-1;
    if (selno <0)
    {
        alert("请选择要减保的险种！");
        return;
    }

    var mtempt = PolGrid.getRowColData(selno, 4);
    PolNo = PolGrid.getRowColData(selno, 10);

    //add by jiaqiangli 2008-11-20 豁免险种自动关联
    var sRiskCode = PolGrid.getRowColData(selno, 1);
    if (isExemptRisk(sRiskCode) == true) {
    	alert("豁免险种自动关联！");
    	return;
    }

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
    fm.action = "PEdorTypePTSubmit.jsp";
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
    document.all('fmtransact').value = "OnlyCheck";
    fm.action = "./PEdorTypePTSubmit1.jsp";
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
	var sqlid3="PEdorTypePTInputSql3";
	var mySql3=new SqlClass();
	mySql3.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql3.setSqlId(sqlid3);//指定使用的Sql的id
	mySql3.addSubPara(tContNo);//指定传入的参数
	mySql3.addSubPara(tContNo);
	strSQL=mySql3.getString();
    
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
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
    }

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
        queryBackFee();
        getPolInfo();
        top.opener.getEdorItemGrid();
        //divPol5.style.display = "";
        //divPol2.style.display = "";
        //divPol3.style.display = "";
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
    //modify by jiaqiangli 2008-12-08 取柜面受理日期而非生效日期
    var tConfDate = document.all("EdorItemAppDate").value;
    var tEdorNo = document.all("EdorNo").value;//Add By QianLy on 2006-9-23
    if(tContNo!=null&&tContNo!="")
    {
        //var strSQL = "Select distinct m.riskcode,m.riskname,c.INSUREDNAME,case when u.amntflag = 1 then p.amnt else p.mult end, p.prem,'','','',c.Insuredno,p.polno,u.amntflag From lcpol p Left Join lmrisk m on m.riskcode = p.riskcode Left Join LCCONT c On c.contno= p.contno Left Join LCDuty d on d.polno = p.polno Left Join LMDuty u on trim(u.dutycode) = substr(trim(d.dutycode),1,6) where ((p.appflag = '1') or (p.appflag = '9' and p.cvalidate + 60 >= '" + tConfDate + "')) and p.cvalidate <= '" + tConfDate + "' and enddate > '" + tConfDate + "' and c.contno = '"+tContNo+"' and polno not in (select polno from lccontstate where statetype = 'Terminate' and state = '1' and enddate is null and contno = c.contno)";
        //Modified By QianLy on 2006-9-23------------------
/*        var strSQL = "Select distinct m.riskcode,"
                   + " m.riskname,"
                   + " c.INSUREDNAME,"
                   + " case when u.calmode='O' then a.mult else a.amnt end,"
                   + " a.prem,"
                   + " case"
                   + "   when u.calmode='O' then"
                   + "    nvl((select mult"
                   + "     from lppol"
                   + "    where 1 = 1"
                   + "      and polno = a.polno"
                   + "      and edorno = '"+tEdorNo+"'),a.mult)"
                   + "   else"
                   + "      nvl((select amnt"
                   + "        from lppol"
                   + "        where 1 = 1"
                   + "         and polno = a.polno"
                   + "         and edorno = '"+tEdorNo+"'),a.amnt)"
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
                   //modify by jiaqiangli 2008-12-08 appflag='9'的催收记录会删除后做保全
                   + " and a.appflag = '1' "
                   //+ " and ((a.appflag = '1') or"
                   //+ " (a.appflag = '9' and a.cvalidate + 60 >= '" + tConfDate + "'))"
                   + " and a.cvalidate <= '" + tConfDate + "'"
                   //comment by jiaqiangli 2009-07-06 附加险处于续保的宽限期内目前查询不出来
                   //+ " and enddate > '" + tConfDate + "'"
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
                   
                   //prompt("",strSQL);
         //----------------------
*/        
    var strSQL = "";
    var sqlid4="PEdorTypePTInputSql4";
	var mySql4=new SqlClass();
	mySql4.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql4.setSqlId(sqlid4);//指定使用的Sql的id
	mySql4.addSubPara(tEdorNo);//指定传入的参数
	mySql4.addSubPara(tEdorNo);
	mySql4.addSubPara(tEdorNo);
	mySql4.addSubPara(tConfDate);
	mySql4.addSubPara(tContNo);
	mySql4.addSubPara(tContNo);
	mySql4.addSubPara(tContNo);
	strSQL=mySql4.getString();
        
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
/*        strSQL = "select distinct riskcode, (select riskname from lmrisk where lmrisk.riskcode=lcpol.riskcode),paytodate,mult,Amnt,sumprem,paymode from lcpol WHERE 1=1 AND appflag='1' and "
                            +"CONTNO='"+tContNo+"'";
*/    
    var sqlid5="PEdorTypePTInputSql5";
	var mySql5=new SqlClass();
	mySql5.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql5.setSqlId(sqlid5);//指定使用的Sql的id
	mySql5.addSubPara(tContNo);//指定传入的参数
	strSQL=mySql5.getString();
    
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

/**
 * 保全复核时显示减保原因和减保保额
 */
function queryEdorInfo()
{
    try
    {
        calcMinusNumber();
    }
    catch (ex) {}
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
                //modify by jiaqiangli 2008-12-30 javascript parseFloat精度问题修复用toFixed方法
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
    var sqlid6="PEdorTypePTInputSql6";
	var mySql6=new SqlClass();
	mySql6.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql6.setSqlId(sqlid6);//指定使用的Sql的id
	mySql6.addSubPara(fm.EdorNo.value);//指定传入的参数
	mySql6.addSubPara(fm.EdorType.value);
	mySql6.addSubPara(fm.ContNo.value);
	QuerySQL=mySql6.getString();
    
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
 * 点"保存"之后就不允许"减保"
 */
function isSavePol()
{
    var QuerySQL, arrResult;
/*    QuerySQL = "select 'X' "
             +   "from lpedoritem "
             +  "where 1 = 1 and edorstate = '1'"
             +     getWherePart("EdorNo", "EdorNo")
             +     getWherePart("EdorType", "EdorType")
             +     getWherePart("ContNo", "ContNo");
*/    
    var sqlid7="PEdorTypePTInputSql7";
	var mySql7=new SqlClass();
	mySql7.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql7.setSqlId(sqlid7);//指定使用的Sql的id
	mySql7.addSubPara(fm.EdorNo.value);//指定传入的参数
	mySql7.addSubPara(fm.EdorType.value);
	mySql7.addSubPara(fm.ContNo.value);
	QuerySQL=mySql7.getString();
    
    try
    {
        arrResult = easyExecSql(QuerySQL, 1, 0);
    }
    catch (ex)
    {
        alert("警告：检查保单是否已经减保保存出现异常！ ");
        return;
    }
    if (arrResult != null)
    {
        alert("保存之后不允许再点减保按钮！ ")
        return true;
    }
    return false;
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
    var sqlid8="PEdorTypePTInputSql8";
	var mySql8=new SqlClass();
	mySql8.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql8.setSqlId(sqlid8);//指定使用的Sql的id
	mySql8.addSubPara(fm.ContNo.value);//指定传入的参数
	QuerySQL=mySql8.getString();
    
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
    var sqlid9="PEdorTypePTInputSql9";
	var mySql9=new SqlClass();
	mySql9.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql9.setSqlId(sqlid9);//指定使用的Sql的id
	mySql9.addSubPara(fm.ContNo.value);//指定传入的参数
	QuerySQL=mySql9.getString();
        
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