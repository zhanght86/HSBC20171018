//               该文件中包含客户端需要处理的函数和事件
var arrDataSet
var showInfo;
var mDebug="0";
var turnPage = new turnPageClass();
var PolNo;
var manageCom = '';
var AppntName ='';
var InsuredName='';
var sqlresourcename = "app.ReProposalPrtSql";
//提交，保存按钮对应操作,提交申请
function ApplyRePrint()
{
	var i = 0;
	var flag = 0;
	flag = 0;
	
	for( i = 0; i < PolGrid.mulLineCount; i++ )
	{
		if( PolGrid.getChkNo(i) == true )
		{
			flag = 1;
			break;
		}
	}
	
	if( flag == 0 )
	{
		alert("请先选择一条记录，再进行申请");
		return false;
	}
	if(document.all('NeedPay').value == null||document.all('NeedPay').value =="")
	{
		alert("请录入计费信息!");
		return false;
	}
	if(document.all('Reason').value == null||document.all('Reason').value =="")
	{
		alert("请录入重打原因信息!");
		return false;
	}
	if(document.all('BatchNo').value == null||document.all('BatchNo').value =="")
	{
		alert("请录入申请表编号信息!");
		return false;
	}
	var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	//showSubmitFrame(mDebug);
	if(document.all('NeedPay').value=='0') //若需要进行交费则需要进行重新生成数据
	{
	fmSave.fmtransact.value="CONFIRM";	
	}
	if(document.all('NeedPay').value=='1')
	{
	fmSave.fmtransact.value="PRINT";	
	}
	
	document.getElementById("fmSave").submit();

}

function getQueryResult()
{
	var arrSelected = null;
	tRow = PolGrid.getSelNo();
	if( tRow == 0 || tRow == null || arrDataSet == null )
	return arrSelected;

	arrSelected = new Array();
	//设置需要返回的数组
	arrSelected[0] = arrDataSet[tRow-1];

	return arrSelected;
}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
	showInfo.close();
	if (FlagStr == "Fail" )
	{
		//如果提交失败，显示错误信息
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
		//如果提交成功，则根据已有条件重新查询需要处理的数据
		// 刷新一下输入框
		initForm();
		//easyQueryClick();
	}
}

//重置按钮对应操作,Form的初始化函数在功能名+Init.jsp文件中实现，函数的名称为initForm()
function resetForm()
{
  try
  {
	  initForm();
  }
  catch(re)
  {
  	alert("在Proposal.js-->resetForm函数中发生异常:初始化界面错误!");
  }
}

//提交前的校验、计算
function beforeSubmit()
{
  //添加操作
}

//显示frmSubmit框架，用来调试
function showSubmitFrame(cDebug)
{
  if(cDebug=="1")
  {
			parent.fraMain.rows = "0,0,50,82,*";
  }
 	else {
  		parent.fraMain.rows = "0,0,0,0,*";
 	}
}

function returnParent()
{
    tPolNo = "00000120020110000050";
    top.location.href="./ProposalQueryDetail.jsp?PolNo="+tPolNo;
}


// 查询按钮
function easyQueryClick()
{
	if(comcode!='86')
	{
		alert("只有总公司才有重打权利，请返回");
		return false;
		}
	if((document.all('ContNo').value == null || document.all('ContNo').value == "") 
    && (document.all('PrtNo').value == null || document.all('PrtNo').value == ""))
   {
   	
		alert("请输入保单号或者印刷号");
		return false;
   }
	//初始化表格
	initPolGrid();
	//alert(1);
	var strManageComWhere = " ";
	var strContNoSQL="";
  var strRiskCodeWhere = " and EXISTS (select 1 from lcpol where contno = A.contno and polno =mainpolno  and riskcode in (SELECT riskcode FROM LMRiskApp where NotPrintPol in ('0','1')) )"; //支持对卡单的打印
	if((document.all('ContNo').value == null || document.all('ContNo').value == "") 
    && (document.all('PrtNo').value == null || document.all('PrtNo').value == ""))
   {
   	
   	   strContNoSQL=" and a.signdate<=now() and a.signdate>=(subdate(now(),10))";
   	}
//用一个SQL的临时变量进行查询
	var strSQL="";
	var tTempSQL="";
	if(_DBT==_DBO){
		  tTempSQL=" with familycont as ( select prtno,(select contno from lccont "
            +" where prtno = familyprt.prtno and rownum <= 1) contno from "   
+  " (SELECT PrtNo FROM LCCont a where grpcontno = '00000000000000000000'"
		+ " AND AppFlag = '1'   "
		+ " AND ( PrintCount in ('1') )"
		+ getWherePart('a.contno', 'ContNo' )
	  + getWherePart( 'PrtNo' )
		+ getWherePart( 'SaleChnl' )
		+ strContNoSQL
		+ getWherePart('ManageCom', 'ManageCom', 'like')
		+ " and ManageCom like '" + comcode + "%%' "
		+" union  "
+  " (SELECT PrtNo FROM LCCont A where grpcontno = '00000000000000000000'"
		+ " AND AppFlag = '1' and FamilyType in ('1','2')   "
		+ " AND ( PrintCount in ('1'))"
		+ getWherePart('a.familycontno', 'ContNo' )
		+ getWherePart( 'PrtNo' )
		+ getWherePart( 'SaleChnl' )
		+ strContNoSQL
		+ getWherePart('ManageCom', 'ManageCom', 'like')
		+ " and ManageCom like '" + comcode + "%%')) familyprt )"	;	
		  
		  var sqlid6="ReProposalPrtSql6";
		  var mySql6=new SqlClass();
		  mySql6.setResourceName(sqlresourcename);
		  mySql6.setSqlId(sqlid6);
		  mySql6.addSubPara(tTempSQL);
		  mySql6.addSubPara(strRiskCodeWhere);
		  strSQL = mySql6.getString() ;
		   
	}else if(_DBT==_DBM){
		  tTempSQL=" ( select prtno,(select contno from lccont "
            +" where prtno = familyprt.prtno limit 1) contno from "   
+  " (SELECT PrtNo FROM LCCont a where grpcontno = '00000000000000000000'"
		+ " AND AppFlag = '1'   "
		+ " AND ( PrintCount in ('1') )"
		+ getWherePart('a.contno', 'ContNo' )
	  + getWherePart( 'PrtNo' )
		+ getWherePart( 'SaleChnl' )
		+ strContNoSQL
		+ getWherePart('ManageCom', 'ManageCom', 'like')
		+ " and ManageCom like '" + comcode + "%%' "
		+" union  "
+  " (SELECT PrtNo FROM LCCont A where grpcontno = '00000000000000000000'"
		+ " AND AppFlag = '1' and FamilyType in ('1','2')   "
		+ " AND ( PrintCount in ('1'))"
		+ getWherePart('a.familycontno', 'ContNo' )
		+ getWherePart( 'PrtNo' )
		+ getWherePart( 'SaleChnl' )
		+ strContNoSQL
		+ getWherePart('ManageCom', 'ManageCom', 'like')
		+ " and ManageCom like '" + comcode + "%%')) familyprt ) b"	;	
		 
		  var sqlid7="ReProposalPrtSql7";
		  var mySql7=new SqlClass();
		  mySql7.setResourceName(sqlresourcename);
		  mySql7.setSqlId(sqlid7);
		  mySql7.addSubPara(tTempSQL);
		  mySql7.addSubPara(strRiskCodeWhere);
		  strSQL = mySql7.getString() ;
	}
  		

	// 书写SQL语句
/*	var strSQL = "";
	strSQL =tTempSQL+ "select a.ContNo, a.Proposalcontno, a.prtno, a.AppntName, a.signdate,a.ManageCom,a.PrintCount,a.familytype,a.familycontno "
	       +"  from LCCont a , familycont b where a.contno=b.contno and a.PrtNo=b.PrtNo and  a.AppFlag = '1' AND a.PrintCount in ('1' ,'10')"
	       +strRiskCodeWhere
	       + " order by a.ContNo asc,a.signdate asc "; 
*/
//var sqlid6="ReProposalPrtSql6";
//var mySql6=new SqlClass();
//mySql6.setResourceName(sqlresourcename);
//mySql6.setSqlId(sqlid6);
//mySql6.addSubPara(tTempSQL);
//mySql6.addSubPara(strRiskCodeWhere);
//var strSQL = mySql6.getString() ; 

	turnPage.strQueryResult  = easyQueryVer3(strSQL);

	//判断是否查询成功
	if (!turnPage.strQueryResult) {
		alert("没有查询到要处理的业务数据！");
		return false;
	}
	
	//清空数据容器，两个不同查询共用一个turnPage对象时必须使用，最好加上，容错
	turnPage.arrDataCacheSet = clearArrayElements(turnPage.arrDataCacheSet);
	//查询成功则拆分字符串，返回二维数组
	turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
	//设置初始化过的MULTILINE对象，VarGrid为在初始化页中定义的全局变量
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

//判断是否生成新数据
function dataConfirm(rr)
{
	if(rr.checked == true)
	{
		if( confirm("是否生成新数据？生成新数据点击“确定”，否则点“取消”。"))
		{
			rr.checked = true;
			fmSave.fmtransact.value = "CONFIRM";
		}
		else
		{
			rr.checked = false;
			fmSave.fmtransact.value = "PRINT";
		}
	}
	else
	{
		rr.checked = false;
		fmSave.fmtransact.value = "PRINT";
	}
}