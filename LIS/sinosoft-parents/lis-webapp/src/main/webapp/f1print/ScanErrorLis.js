//程序名称：ScanErrorLis.js
//程序功能：综合打印—承保报表清单--扫描差错率统计报表
//创建日期：2010-06-02
//创建人  ：hanabin
//更新记录：  更新人    更新日期     更新原因/内容

var arrDataSet 
var showInfo;
var mDebug="0";
var FlagDel;
var turnPage = new turnPageClass();


function afterSubmit( FlagStr, content )
{
  FlagDel = FlagStr;

    showInfo.close();
    if (FlagStr == "Fail" )
    {
      var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
			//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
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
    	showDiv(inputButton,"false");
    }
}


//根据起始日期进行查询出要该日期范围内的批次号码
function easyPrint()
{
	var ManageCom = document.all('ManageCom').value;
	
	if (ManageCom == "")
	{
		alert("请选择管理机构！");
		return;
	}
	
	
	if(fm.StartDate.value==null||fm.StartDate.value==""||fm.EndDate.value==null||fm.EndDate.value=="")
	{
	   alert("请录入【开始/结束日期】！");
	   return false;
	}
    
    	//var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    	//var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    	//showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
    	fm.action = './ScanErrorLisSave.jsp';
    	fm.target="f1print";
    	document.getElementById("fm").submit(); //提交
}

function easyQuery()
{
	var ManageCom = document.all('ManageCom').value;
	
	if (ManageCom == "")
	{
		alert("请选择管理机构！");
		return;
	}
	
	
	if(fm.StartDate.value==null||fm.StartDate.value==""||fm.EndDate.value==null||fm.EndDate.value=="")
	{
	   alert("请录入【开始/结束日期】！");
	   return false;
	}
	
    var gradeSql =" X.p ";
    var tComGrade = fm.ComGrade.value  ;
    if(tComGrade != "" && tComGrade.length > 0)
    {
    	if(tComGrade < ManageCom.length)
    	{
    		fm.ComGrade.value = "";
    		fm.ComGradeName.value = "";
    		alert("请选择"+ Mcom.length+ "位及以上的统计粒度！");
    		return;
    	}else{
    		gradeSql = " substr(X.p,1,"+tComGrade+") " ;
    	}
    }
	
    
    var tScanType = document.all('ScanType').value;
    var tScanType_add = " and 1 = 1 ";
    var tScanType_add1 = " and 1 = 1 ";
    
    if(tScanType =="个险"){
    	tScanType_add = tScanType_add+" and substr(a.doccode,1,4) = '8611'  ";
    	tScanType_add1 = tScanType_add1+" and substr(Y.PrtNo,1,4) = '8611' ";
    }else if(tScanType =="中介"){
    	tScanType_add = tScanType_add+" and substr(a.doccode,1,4) = '8621' ";
    	tScanType_add1 = tScanType_add1+" and substr(Y.PrtNo,1,4) = '8621' ";
    }else if(tScanType =="简易"){
    	tScanType_add = tScanType_add+" and substr(a.doccode,1,4) = '8616' ";
    	tScanType_add1 = tScanType_add1+" and substr(a.PrtNo,1,4) = '8616'  ";
    }else if(tScanType =="银代"){
    	tScanType_add = tScanType_add+" and substr(a.doccode,1,4) in ('8615','8625','8635') ";
    	tScanType_add1 = tScanType_add1+" and substr(Y.PrtNo,1,4) in ('8615','8625','8635') ";
    }else {
      
    }
	initCodeGrid();
	// 书写SQL语句
//	var strSQL = " select a.managecom,a.prtno,a.makedate,a.maketime,a.oldoperator,a.remark from lcdeltrace a where 1=1"
//             + getWherePart('a.Makedate','StartDate','>=')
//             + getWherePart('a.Makedate','EndDate','<=')
//             + getWherePart('a.PrtNo','PrtNo')
//             + " and a.ManageCom like '" + document.all("ManageCom").value + "%%'"
//             + " and a.ManageCom like '" + comCode + "%%' "
//             + " order by a.MakeDate "
	
//	var strSQL = "select "+gradeSql+",sum(X.q), sum(X.m), sum(X.n), sum(X.z),(case when  to_char(sum(X.n)) = '0' then concat(sum(X.q) , '/0') when  to_char(sum(X.q)) = '0' then '0%' when to_char(sum(X.q)) =  to_char(sum(X.n)) then  '100%' else to_char(round(sum(X.q) / sum(X.n), 4) * 100, concat('fm990.999') , '%') end) "
//			+ " from (select max(a.managecom) p, "
//			+ " doccode t, "
//			+ " count(1) m, "
//			+ " (case when (select max(1) from es_doc_main where doccode = a.doccode and subtype = 'UA001') is not null then (select max(1) from es_doc_main where doccode = a.doccode and subtype = 'UA001') else  0 end) n, "
//			+ " (count(1) - (case when (select max(1) from es_doc_main where doccode = a.doccode and subtype = 'UA001') is not null then (select max(1) from es_doc_main where doccode = a.doccode and subtype = 'UA001') else 0 end)) z , "
//			+ " 0 q "
//			+ " from es_doc_main a "
//			+ " where 1=1 "
//			+ getWherePart('a.Makedate','StartDate','>=')
//            + getWherePart('a.Makedate','EndDate','<=')
//            + tScanType_add
//            + " and a.ManageCom like '" + document.all("ManageCom").value + "%%'"
//			+ " and a.subtype in ('UA001', 'UN100', 'UN101', 'UN102', 'UN103', 'UN104', 'UN105', 'UR200', 'UR201', 'UR202', 'UR203', 'UR204', 'UR205', 'UR206', 'UR207', 'UR208', 'UR209', 'UR210', 'UR211', 'UR212', 'UR301') "
//			+ " group by doccode "
//			+ " union select max((select comcode from lduser where usercode = Y.Oldoperator)) p, prtno t, 0 m, 0 n, 0 z, 1 q "
//			+ " from lcdeltrace Y where 1=1 "
//			+ getWherePart('Y.Makedate','StartDate','>=')
//            + getWherePart('Y.Makedate','EndDate','<=')
//            + tScanType_add1
//            + " and (select comcode from lduser where usercode = Y.Oldoperator) like '" + document.all("ManageCom").value + "%%'"
//			+ " group by prtno) X "
//			+ " where 1 = 1 "
//			+ " group by "+gradeSql+" "
//			+ " order by "+gradeSql+"";
	
	
  	var  StartDate0 = window.document.getElementsByName(trim("StartDate"))[0].value;
  	var  EndDate0 = window.document.getElementsByName(trim("EndDate"))[0].value;
	var sqlid0="ScanErrorLisSql0";
	var mySql0=new SqlClass();
	mySql0.setResourceName("f1print.ScanErrorLisSql"); //指定使用的properties文件名
	mySql0.setSqlId(sqlid0);//指定使用的Sql的id
	mySql0.addSubPara(gradeSql);//指定传入的参数
	mySql0.addSubPara(StartDate0);//指定传入的参数
	mySql0.addSubPara(EndDate0);//指定传入的参数
	mySql0.addSubPara(tScanType_add);//指定传入的参数
	mySql0.addSubPara(document.all("ManageCom").value);//指定传入的参数
	mySql0.addSubPara(StartDate0);//指定传入的参数
	mySql0.addSubPara(EndDate0);//指定传入的参数
	mySql0.addSubPara(tScanType_add1);//指定传入的参数
	mySql0.addSubPara(document.all("ManageCom").value);//指定传入的参数
	mySql0.addSubPara(gradeSql);//指定传入的参数
	mySql0.addSubPara(gradeSql);//指定传入的参数
	var strSQL=mySql0.getString();
             
	// prompt("abc",strSQL);
	turnPage.queryModal(strSQL, CodeGrid);    
	
}


function afterQuery(arrResult)
{
  
  if(arrResult!=null)
  {
  	fm.BranchGroup.value = arrResult[0][3];
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
function queryCom()
{
	showInfo = window.open("../certify/AgentTrussQuery.html");
	}
function afterQuery(arrResult)
{
  if(arrResult!=null)
  { 	
	    fm.AgentGroup.value = arrResult[0][3];
	    
	}
}


