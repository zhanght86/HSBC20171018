var showInfo;
var turnPage = new turnPageClass(); 


function polquery()
{

    if((fm.ContNo.value==null||fm.ContNo.value=="")&&(fm.PrtNo.value==null||fm.PrtNo.value=="")){
      alert("请录入‘投保单号’或‘印刷号’其中的一项后再进行查询！");
      return false;
    }
	initPolGrid();
//	var sql = "select ContNo,PrtNo,"
//	             + " (select riskcode from lcpol where contno=a.contno and insuredno=a.insuredno and polno=mainpolno and (risksequence='1' or risksequence='-1')),"
//	             + " AppntName,InsuredName, Operator, MakeDate from LCCont a where AppFlag='0' "
//				 + "and GrpContNo='00000000000000000000' "
//				 + getWherePart( 'ContNo' )
//				 + getWherePart( 'ManageCom', 'ManageCom' )//查询条件中的集中权限管理体现
//				 + getWherePart( 'AgentCode' )
//				 + getWherePart( 'RiskCode' )
//				 + getWherePart( 'PrtNo' )
//				 + getWherePart( 'MakeDate' )		 
//				 + " order by MakeDate desc,prtno";
	
  	var  ContNo0 = window.document.getElementsByName(trim("ContNo"))[0].value;
  	var  ManageCom0 = window.document.getElementsByName(trim("ManageCom"))[0].value;
  	var  AgentCode0 = window.document.getElementsByName(trim("AgentCode"))[0].value;
  	var  RiskCode0 = window.document.getElementsByName(trim("RiskCode"))[0].value;
  	var  PrtNo0 = window.document.getElementsByName(trim("PrtNo"))[0].value;
  	var  MakeDate0 = window.document.getElementsByName(trim("MakeDate"))[0].value;
	var sqlid0="delproposalSql0";
	var mySql0=new SqlClass();
	mySql0.setResourceName("app.delproposalSql"); //指定使用的properties文件名
	mySql0.setSqlId(sqlid0);//指定使用的Sql的id
	mySql0.addSubPara(ContNo0);//指定传入的参数
	mySql0.addSubPara(ManageCom0);//指定传入的参数
	mySql0.addSubPara(AgentCode0);//指定传入的参数
	mySql0.addSubPara(RiskCode0);//指定传入的参数
	mySql0.addSubPara(PrtNo0);//指定传入的参数
	mySql0.addSubPara(MakeDate0);//指定传入的参数s
	var sql=mySql0.getString();
	
	turnPage.strQueryResult  = easyQueryVer3(sql, 1, 0, 1);
	if (!turnPage.strQueryResult) 
	{
      alert("没有投保单信息!");
      return "";
    }
    turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
    turnPage.pageDisplayGrid = PolGrid;
    turnPage.strQuerySql     = sql;
    turnPage.pageIndex       = 0;
    var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
    displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
    return true;
}

function delpol()
{
	var tSel = PolGrid.getSelNo();
	if( tSel == 0 || tSel == null )
	{
		alert( "请先选择一条记录" );
		return;
	}	
	var prtNo=PolGrid.getRowColData(tSel - 1,2);
		if (prtNo == "")
		{
			alert("未查询到要被替换的印刷号!");
			return;
		}		
		document.all('PrtNoHide').value = prtNo;
  if (confirm("您确实想删除该印刷号下的所有投保单吗?"))
  {
  var showStr="正在更新数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  var name='提示';   //网页名称，可为空; 
  var iWidth=550;      //弹出窗口的宽度; 
  var iHeight=250;     //弹出窗口的高度; 
  var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
  var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
  showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

  showInfo.focus();
  document.all('fmAction').value = "DELETE||PRT";
  //document.all('PrtNo').value = PolGrid.getRowColData(PolGrid.getSelNo() - 1, 2);
  //document.all('PolNo').value = PolGrid.getRowColData(PolGrid.getSelNo() - 1, 1);
  document.getElementById("fm").submit(); 
  }
}

function afterSubmit( FlagStr, content )
{
  initForm();
  showInfo.close();
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
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
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=350;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  }
}