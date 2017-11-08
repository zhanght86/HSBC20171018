var showInfo;
 var turnPage = new turnPageClass();
 var turnPage1 = new turnPageClass();
 var mAllType = '2';
//是否为数字
function isdigit(c){
  return(c>='0'&&c<='9');
}

function initEdorType(cObj)
{
	mEdorType = " #1# and length(trim(ComCode))=4 and comcode not like #8699%%# ";
	showCodeList('comcode',[cObj],[0],null,mEdorType,"1",1);
}

function actionKeyUp(cObj)
{	
	mEdorType = " #1# and length(trim(ComCode))=4 and comcode not like #8699%%# ";
	showCodeListKey('comcode',[cObj],[0],null,mEdorType,"1",1);
}

function queryData(ttype)
{
	 initNoBonusRiskGrid();  
         initBonusRiskGrid();
	if(trim(document.all('BonusCalYear').value)=='')
	{
		alert('请输入红利分配会计年度');
		document.all('BonusCalYear').focus();
		return false;
	}
	mAllType = ttype;
	if(ttype=='0')
	{
	       var sqlid827144842="DSHomeContSql827144842";
var mySql827144842=new SqlClass();
mySql827144842.setResourceName("get.BonusRiskPreInputSql");//指定使用的properties文件名
mySql827144842.setSqlId(sqlid827144842);//指定使用的Sql的id
mySql827144842.addSubPara(document.all('BonusCalYear').value);//指定传入的参数
mySql827144842.addSubPara(fm.BonusCalRisk.value);//指定传入的参数
var strSQL=mySql827144842.getString();

	       
//	       var strSQL = " select riskcode,riskname from lmriskapp a where bonusflag = '1' "
//                          + " and not exists (select '1' from LoBonusRiskRem where fiscalyear='"+document.all('BonusCalYear').value+"' "
//                          + " and riskcode=a.riskcode and state='0') "
//                          +getWherePart('a.riskcode','BonusCalRisk')
//                          + " order by riskcode ";
                 
        	//initBonusRiskGrid();
                turnPage1.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
		//alert(strSQL);
		if (!turnPage1.strQueryResult) 
		{
			alert("查询无数据！");
			return false;
		}
		
		//查询成功则拆分字符串，返回二维数组
		turnPage1.arrDataCacheSet = decodeEasyQueryResult(turnPage1.strQueryResult,0,0,turnPage1);
		//设置初始化过的MULTILINE对象，VarGrid为在初始化页中定义的全局变量
		turnPage1.pageDisplayGrid = BonusRiskGrid;
		//保存SQL语句
		turnPage1.strQuerySql = strSQL;
		//设置查询起始位置
		turnPage1.pageIndex = 0;
		//在查询结果数组中取出符合页面显示大小设置的数组
		var tArr = new Array();
                tArr=turnPage1.getData(turnPage1.arrDataCacheSet,turnPage1.pageIndex,MAXSCREENLINES,turnPage1);
		//调用MULTILINE对象显示查询结果
		displayMultiline(tArr, turnPage1.pageDisplayGrid,turnPage1);
 
          }
        else if(ttype=='1')
	{  
  	      var sqlid827145049="DSHomeContSql827145049";
					var mySql827145049=new SqlClass();
					mySql827145049.setResourceName("get.BonusRiskPreInputSql");//指定使用的properties文件名
					mySql827145049.setSqlId(sqlid827145049);//指定使用的Sql的id
					mySql827145049.addSubPara(fm.BonusCalRisk.value);//指定传入的参数
					mySql827145049.addSubPara(fm.BonusCalYear.value);//指定传入的参数
					var strSQL=mySql827145049.getString();
  	       
//  	       var strSQL = " select riskcode,(select riskname from lmriskapp where riskcode=a.riskcode),decode(state,'0','未复核','1','已复核') "
//  	                  + " from LoBonusRiskRem a where state='0'"
//                          +getWherePart('a.riskcode','BonusCalRisk')
//                          +getWherePart('a.fiscalyear','BonusCalYear')
//                          + " order by riskcode ";
                 
        	//initNoBonusRiskGrid();
                turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
		//alert(strSQL);
		if (!turnPage.strQueryResult) 
		{
			alert("查询无数据！");
			return false;
		}
		
		//查询成功则拆分字符串，返回二维数组
		turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult,0,0,turnPage);
		//设置初始化过的MULTILINE对象，VarGrid为在初始化页中定义的全局变量
		turnPage.pageDisplayGrid = NoBonusRiskGrid;
		//保存SQL语句
		turnPage.strQuerySql = strSQL;
		//设置查询起始位置
		turnPage.pageIndex = 0;
		//在查询结果数组中取出符合页面显示大小设置的数组
		var tArr = new Array();
    tArr=turnPage.getData(turnPage.arrDataCacheSet,turnPage.pageIndex,MAXSCREENLINES,turnPage);
		//调用MULTILINE对象显示查询结果
		displayMultiline(tArr, turnPage.pageDisplayGrid,turnPage);
	}
        	
}
function submitForm()
{
	
if(verifyInput()) 
  {	  	
  	var i = 0;
	  var showStr="正在准备打印数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	  var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	  document.getElementById("fm").submit(); //提交
	}
}

function addData()
{
	
	if(trim(document.all('BonusCalYear').value)=='')
	{
		alert('请输入红利分配会计年度');
		document.all('BonusCalYear').focus();
		return false;
	}
	document.all('hideaction').value='INSERT';
	if(!submitForm())
	{
		return false;
	}
	document.all('hideaction').value='';
}

function delData()
{
	var tSel = NoBonusRiskGrid.getSelNo();
	//alert('tSel:'+tSel);
	if(tSel==0)
	{
		alert('请选择一行记录再做删除操作');
		return false;
	}
        
        if (!confirm('确定删除操作?'))
	{
		return false;
	}
	
	document.all('hideaction').value='DELETE';
	if(!submitForm())
	{
		return false;
	}
	document.all('hideaction').value='';
}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
	document.all('hideaction').value='';
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
    parent.fraInterface.document.all('compute').disabled = false;
  }
  else
  { 
    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;  
  	parent.fraInterface.document.all('compute').disabled = false;
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=350;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  }
   initNoBonusRiskGrid();  
   initBonusRiskGrid(); 
   //去除选择框
   //关闭扩充算法
   if (FlagStr != "Fail" )
   {
      document.all('checkbox1').checked = false;
      document.all('divOtherGrid').style.display="none";
   }
  queryData(mAllType);
  mAllType = '2';
}




function onReportSelected(parm1,parm2)
{
	document.all('CalManageCom').value=AgentGrid.getRowColData(AgentGrid.getSelNo() - 1, 1);
        document.all('IndexCalNo').value=AgentGrid.getRowColData(AgentGrid.getSelNo() - 1, 3);
	//fm.
	//alert(AgentGrid.getRowColData(AgentGrid.getSelNo() - 1, 6));
	//var tSel = AgentGrid.getSelNo();
	//alert('tSel:'+tSel);
	//if(document.all('IndexCalNo').value=='')
	//{
	//	alert('请先输入计算年月');
	//}
	//else
	//{
	/*
		document.all('HideReportCode').value = AgentGrid.getRowColData(AgentGrid.getSelNo() - 1, 1);
		var strSQL = " select count(*) from LAAgentAutoReportLog "
		           + " where logtype='01' and indexcalno='"+document.all('IndexCalNo').value+"'"
		           + " and reportcode='"+document.all('HideReportCode').value+"'";
		var strQueryResult = easyQueryVer3(strSQL, 1, 1, 1);
		if(strQueryResult)
		{
			var tArr = new Array();
                        tArr = decodeEasyQueryResult(strQueryResult);
                        if(tArr[0][0]<=0)
                        {
                        	AgentGrid.setRowColData(AgentGrid.getSelNo() - 1,3,'未计算');
                        }
                        else
                        {
		
	                   var sql = "select decode(state,'00','处理中','01','已计算','计算出错') from LAAgentAutoReportLog"
	                           + " where logtype='01' and indexcalno='"+document.all('IndexCalNo').value+"'"
	                           + " and reportcode='"+document.all('HideReportCode').value+"'";
	                   strQueryResult = easyQueryVer3(sql, 1, 1, 1);  
	                   var tArr1 = new Array();
                               tArr1 = decodeEasyQueryResult(strQueryResult);
                               AgentGrid.setRowColData(AgentGrid.getSelNo() - 1,3,tArr1[0][0]);
	        
	                }
                }  
        }      
        */
}

//扩充算法数据保存
function saveOtherData()
{
	if(trim(document.all('BonusCalYear').value)=='')
	{
		alert('请输入红利分配会计年度');
		document.all('BonusCalYear').focus();
		return false;
	}
	
	document.all('hideaction').value='OTHERSAVE';
	if(!submitForm())
	{
		return false;
	}
	document.all('hideaction').value='';
}


function setDefaultClass()
{
	if(trim(document.all('BonusCalYear').value)=='')
	{
		alert('请输入红利分配会计年度');
		document.all('checkbox1').checked = false;
		document.all('BonusCalYear').focus();
		return false;
	}
	mAllType = '2';
	if(document.all('checkbox1').checked == true)
	{
	  document.all('divOtherGrid').style.display="";
	  
	  var sqlid830154605="DSHomeContSql830154605";
var mySql830154605=new SqlClass();
mySql830154605.setResourceName("get.BonusRiskPreInputSql");//指定使用的properties文件名
mySql830154605.setSqlId(sqlid830154605);//指定使用的Sql的id
mySql830154605.addSubPara(document.all('BonusCalYear').value);//指定传入的参数
mySql830154605.addSubPara(document.all('BonusCalYear').value);//指定传入的参数
mySql830154605.addSubPara(document.all('BonusCalYear').value);//指定传入的参数
document.all('WorkDetail').value=mySql830154605.getString();

	  
	  
//	  document.all('WorkDetail').value = "select * from lcpol a where grppolno = \'00000000000000000000\' "
//	                             + " and appflag='1' and cvalidate <= '"+document.all('BonusCalYear').value+ "'||'-12-31' "
//	                             + " and exists (select riskcode from lmriskapp where bonusflag = '1' and riskcode=a.riskcode) "
//	                             + " and not exists (select '1' from LoBonusRiskRem where FiscalYear='"+document.all('BonusCalYear').value+"'"
//	                             + " and state='00' and riskcode = a.riskcode) "
//                                     + " and not exists (select polno from LOBonusPol "
//                                     + " where FiscalYear="+document.all('BonusCalYear').value+" and polno = a.polno and GroupID='1')";

          var sqlid830154901="DSHomeContSql830154901";
var mySql830154901=new SqlClass();
mySql830154901.setResourceName("get.BonusRiskPreInputSql");//指定使用的properties文件名
mySql830154901.setSqlId(sqlid830154901);//指定使用的Sql的id
mySql830154901.addSubPara(document.all('BonusCalYear').value);//指定传入的参数
var tSQL=mySql830154901.getString();
          
//          var tSQL = " select trim(BonusCondition) from lobonusmain where FiscalYear="+document.all('BonusCalYear').value+" and GroupID='1'";
          //prompt('1',tSQL);
          var strQueryResult1 = easyQueryVer3(tSQL,1,1,1);
          if(!strQueryResult1)
          {
          	document.all('OtherCondition').value='';
          }
          else 
     	  {
     	    var arr = decodeEasyQueryResult(strQueryResult1);
     	    document.all('OtherCondition').value=arr[0][0];
     	  }
        }
        else
	{
	   document.all('divOtherGrid').style.display="none";
	}	
}


function clearAllShow()
{  
   initNoBonusRiskGrid();  
   initBonusRiskGrid();
   document.all('checkbox1').checked = false;
   document.all('divOtherGrid').style.display="none";
}