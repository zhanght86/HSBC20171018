//               该文件中包含客户端需要处理的函数和事件
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();

var tResourceName="get.LFGetNoticeSql";
var tResourceSQL1="LFGetNoticeSql1";
var tResourceSQL2="LFGetNoticeSql2";

//提交，保存按钮对应操作
function printPol()
{
  var showStr = "正在提交数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  
  var tSel = PolGrid.getSelNo();

  if( tSel == 0 || tSel == null )
	{
		alert( "请先选择一条记录，再点击打印按钮。" );
		return false;
	}
	else
	{
		fm.PrtSeq.value = PolGrid.getRowColData(PolGrid.getSelNo()-1, 1);
   var tResult = fm.PrtSeq.value;
   //alert(fm.PrtSeq.value);
   if(tResult == null || tResult == ""){
       alert("查询领取清单信息失败！");
       return;
   }
   fm.action = "../bq/EdorNoticePrintSave.jsp";
   fm.target = "f1print";
   document.submit();
	}
}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
  //showInfo.close();
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
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

    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=350;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

    //showDiv(operateButton,"true"); 
    //showDiv(inputButton,"false"); 
    //执行下一步操作
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
			parent.fraMain.rows = "0,0,50,82,*";
  }
 	else
 	{
  		parent.fraMain.rows = "0,0,0,82,*";
 	}
}

// 查询按钮
function easyQueryClick()
{
	initPolGrid();

  //首先检验录入框
 if(trim(fm.GrpContNo.value)==""){
 		alert("请录入团单号");
 		return;
 }
	
	// 书写SQL语句
	//var strSQL = "select a.prtseq,a.otherno,b.grpname,a.standbyflag1,a.standbyflag2,a.ReqOperator " 
	//					 + "from loprtmanager a,lcgrpcont b " 
	//					 + "where a.othernotype = '01' and code = 'BQ54' and a.otherno = b.GrpContNo " 
	//					 + getWherePart('a.otherno','GrpContNo');
	var strSQL = wrapSql(tResourceName,tResourceSQL1,[fm.GrpContNo.value]); 
	
//	alert(strSQL);
	turnPage.queryModal(strSQL, PolGrid);   
}

function reportDetailClick()
{
	initLFGetGrid();
	var tSel = PolGrid.getSelNo();
	var tGrpContNo = PolGrid.getRowColData(PolGrid.getSelNo()-1, 2);
	var tSerialNo = PolGrid.getRowColData(PolGrid.getSelNo()-1, 4);
	//var tSql = " select a.contno,b.name,decode(a.getdutykind, '1','趸领','2','年领','3','月领','4','年领','5','月领','6','年领','7','月领','8','年领','9','月领','其它'),"
	//				 + " '第' || (select count(distinct GetNoticeNo)+1 from ljagetdraw where getdate < a.getdate and contno = a.contno and insuredno = a.insuredno) || '期',a.getdate,"
	//				 + " sum(a.getmoney)||'元',decode(c.paymode, '1','现金','4','银行转账','其它'),"
	//				 + " nvl((select bankname from ldbank where bankcode = c.bankcode),''),nvl(c.accname,''),nvl(c.bankaccno,'') "
	//				 + " from ljagetdraw a, lcinsured b, ljaget c"
	//				 + " where a.grpcontno = b.grpcontno "
	//				 + " and a.contno = b.contno "
	//				 + " and a.insuredno = b.insuredno "
	//				 + " and a.serialno = c.serialno"
	//				 + " and a.actugetno = c.actugetno"
	//				 + " and a.grpcontno = '"+tGrpContNo+"'"
	//				 + " and a.serialno = '"+tSerialNo+"' "
	//				 + " group by a.contno,b.name,a.getdutykind,a.insuredno,a.getdate,c.paymode,c.bankcode,c.bankaccno,c.accname order by a.ContNo";
  var tSql = wrapSql(tResourceName,tResourceSQL2,[tGrpContNo,tSerialNo]); 
	turnPage1.queryModal(tSql, LFGetGrid);
	var rowNum=LFGetGrid.mulLineCount;
	//alert(rowNum);
	if(rowNum<1)
	{
		divGetDrawInfo.style.display = 'none';
	}else {
		divGetDrawInfo.style.display = '';	
	}
}