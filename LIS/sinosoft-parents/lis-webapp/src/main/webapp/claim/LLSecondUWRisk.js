//程序名称：LLSecondUWRisk.js
//程序功能：险种核保信息界面-------理赔部分
//创建日期：2005-01-06 11:10:36
//创建人  ：HYQ
//更新记录：  更新人 yuejw   更新日期     更新原因/内容

var showInfo;
var turnPage    = new turnPageClass();
var turnsubPage = new turnPageClass();
var turnPage1   = new turnPageClass();
var temp        = new Array();
var str_sql = "",sql_id = "", my_sql = "";   //绑定变量访问数据库
///***************查询核保被保人险种信息列表*********************/
function queryLLRiskGridInfo()
{
	var tContNo = fm.ContNo.value;//Modify by zhaorx 2006-09-23
//   	var tSql = " select t.polno,t.contno,t.riskcode,a.riskname,t.prem,t.amnt,t.cvalidate,t.enddate,t.payintv,t.payyears ,t.proposalno,a.subriskflag "
//   			+ " from lcpol t,lmriskapp a where 1=1 and t.riskcode = a.riskcode and t.appflag='1'"
//			+ " and t.contno='"+tContNo+"' and t.polno = t.mainpolno union "
//			+ " select t.polno,t.contno,t.riskcode,a.riskname,t.prem,t.amnt,t.cvalidate,t.enddate,t.payintv,t.payyears ,t.proposalno,a.subriskflag "
//   			+ " from lcpol t,lmriskapp a where 1=1 and t.riskcode = a.riskcode and t.appflag='1'"
//			+ " and t.contno='"+tContNo+"' and t.polno <> t.mainpolno order by 12";
   	sql_id = "LLSecondUWRiskSql0";
	my_sql = new SqlClass();
	my_sql.setResourceName("claim.LLSecondUWRiskSql"); //指定使用的properties文件名
	my_sql.setSqlId(sql_id);//指定使用的Sql的id
	my_sql.addSubPara(tContNo);//指定传入的参数
	my_sql.addSubPara(tContNo);//指定传入的参数
	str_sql = my_sql.getString();
	turnPage.queryModal(str_sql,LLRiskGrid)
	return true;  					
}

//险种信息列表 单击响应
function LLRiskGridClick()
{
	cancelClick();
	var tSelNo =LLRiskGrid.getSelNo()-1;
	var tPolNo=LLRiskGrid.getRowColData(tSelNo,1);
	fm.RiskCode.value = LLRiskGrid.getRowColData(tSelNo,3);
	if(tSelNo != 0)
	{
	      fm.addbutton.disabled  = true;
	      fm.specbutton.disabled = true;
    }
    else
	{
		  fm.addbutton.disabled  = false;
	      fm.specbutton.disabled = false;
	}
//	var strSQL=" select tt.caseno,tt.batno,tt.contno,tt.polno,tt.uwno"
//		+" ,tt.passflag,(select codename from ldcode jj where jj.codetype='uwstate' and code=tt.passflag)"
//		+" ,tt.uwidea,tt.makedate,tt.maketime "
//		+" from lluwsub tt where 1=1 and tt.caseno='"+fm.CaseNo.value+"'"
//		+" and tt.polno='"+tPolNo+"' order by tt.batno desc ,tt.uwno desc ";
	sql_id = "LLSecondUWRiskSql1";
	my_sql = new SqlClass();
	my_sql.setResourceName("claim.LLSecondUWRiskSql"); //指定使用的properties文件名
	my_sql.setSqlId(sql_id);//指定使用的Sql的id
	my_sql.addSubPara(fm.CaseNo.value);//指定传入的参数
	my_sql.addSubPara(tPolNo);//指定传入的参数
	str_sql = my_sql.getString();
	turnsubPage.queryModal(str_sql,LLUWSubGrid)
	
	return true;  
}


/********************  加费承保***********************/ 

/**=========================================================================
    修改状态：得到保单险种号码和主保单号码来确定是否是主险，如果是主险，
              那么就可以执行加费承保，如果不是主险，那么就不能执行加费承保。
    修改原因：附加险不能进行“加费承保”操作
    修 改 人：万泽辉
    修改日期：2005.11.03
   =========================================================================
**/
function showAdd()
{
    var tSelNo  = LLRiskGrid.getSelNo();
    if(tSelNo < 1)
    {
        alert("请选中一行记录！");
        return 1;
    }
    tSelNo = tSelNo - 1 ;
	var tClmNo=fm.CaseNo.value;
	var tContNo=fm.ContNo.value;
    var tBatNo=fm.BatNo.value;     
	var tInsuredNo=fm.InsuredNo.value; 
	var tQueryFlag=0;//0非查询，1查询 
    var tUrl = "../claimnb/LLUWAddFeeMain.jsp?ClmNo="+tClmNo+"&ContNo="+tContNo+"&BatNo="+tBatNo+"&InsuredNo="+tInsuredNo+"&QueryFlag="+tQueryFlag;
    window.open(tUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
   
}

/***********************  特约承保********************* */ 


/**=========================================================================
    修改状态：得到保单险种号码和主保单号码来确定是否是主险，如果是主险，
              那么就可以执行特约承保，如果不是主险，那么就不能执行特约承保。
    修改原因：附加险不能进行“特约承保”操作
    修 改 人：万泽辉
    修改日期：2005.11.03
   =========================================================================
**/
function showSpec()
{
  	var tSelNo  = LLRiskGrid.getSelNo();
    if(tSelNo < 1)
    {
        alert("请选中一行记录！");
        return 1;
    }
    tSelNo = tSelNo - 1 ;
	var tProposalNo = LLRiskGrid.getRowColData(tSelNo,11);
	var tContNo = fm.ContNo.value;
	var tBatNo  = fm.BatNo.value;
	if (tContNo != "" )
	{ 	
        var tClmNo=fm.CaseNo.value;
	    var tContNo=fm.ContNo.value;
	    var tBatNo=fm.BatNo.value; 
        var tInsuredNo=fm.InsuredNo.value;         
	    var tQueryFlag=0;//0非查询，1查询     
        var tUrl = "../claimnb/LLUWSpecMain.jsp?ClmNo="+tClmNo+"&ContNo="+tContNo+"&BatNo="+tBatNo+"&InsuredNo="+tInsuredNo+"&QueryFlag="+tQueryFlag+"&ProposalNo="+tProposalNo+"&BatNo="+tBatNo;
        window.open(tUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	 }
	 else
	 {
		 alert("没有得到合同号");
	 }
}

/***************[取消按钮]*********************/
function cancelClick()
{
	fm.UWState.value="";
	fm.UWStateName.value="";
	fm.UWIdea.value="";
}

///***************[确定按钮]*********************/
function uwSaveClick()
{
	var tSelNo =LLRiskGrid.getSelNo()-1;
	if(tSelNo < 0)
	{
		alert("请选择保单险种！");
		return;
	}
	if(fm.UWState.value=="")
	{
		alert("请选择险种核保结论!");
		return;
	}
	document.all('PolNo').value = LLRiskGrid.getRowColData(tSelNo,1);	//保单号码
	fm.action = "./LLSecondUWRiskChk.jsp";
 	submitForm(); //提交
}

//提交，保存按钮对应操作
function submitForm()
{
  var i = 0;
  var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
  var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  fm.submit(); //提交
}


/******************提交后操作,服务器数据返回后执行的操作******************************/ 
function afterSubmit( FlagStr, content )
{
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
    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;  
  	//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  }
  LLRiskGridClick();
  cancelClick();
}

/*********************************************************************
 *  修改原因：查询核保被保人信息
 *  修改人  ：万泽辉
 *  修改日期：2005/12/21/15:20
 *********************************************************************
 */
function queryInsuredInfo()
{
	var arrReturn1 = new Array();
	var arrReturn2 = new Array();
	var arrReturn3 = new Array();
	var tContNo    = fm.ContNo.value;
	var tInsuredNo = fm.InsuredNo.value; 
//	var tSql1 = "select insuredno,name,sex,birthday,NativePlace,RelationToMainInsured,RelationToAppnt,OccupationCode,OccupationType from LCInsured t where 1=1"
//							+ " and contno='"+tContNo+"'"
//							+ " and insuredno ='"+tInsuredNo+"'";
	sql_id = "LLSecondUWRiskSql2";
	my_sql = new SqlClass();
	my_sql.setResourceName("claim.LLSecondUWRiskSql"); //指定使用的properties文件名
	my_sql.setSqlId(sql_id);//指定使用的Sql的id
	my_sql.addSubPara(tContNo);//指定传入的参数
	my_sql.addSubPara(tInsuredNo);//指定传入的参数
	str_sql = my_sql.getString();
	turnPage1.strQueryResult  = easyQueryVer3(str_sql, 1, 0, 1);  
  
  //判断是否查询成功
  if (!turnPage1.strQueryResult)
  {
    alert("被保人信息查询失败!");
    return "";
  }
 
  //查询成功则拆分字符串，返回二维数组
  arrResult1 = decodeEasyQueryResult(turnPage1.strQueryResult);	
  
  
  document.all('InsuredNo1').value = arrResult1[0][0];
  document.all('Name').value = arrResult1[0][1];
  
  document.all('Sex').value = arrResult1[0][2];
  showOneCodeName('sex','Sex','SexName');
 
  document.all('InsuredAppAge').value = calAge(arrResult1[0][3]);
  
  document.all('NativePlace').value = arrResult1[0][4];
  showOneCodeName('nativeplace','NativePlace','NativePlaceName');
  
  document.all('RelationToMainInsured').value = arrResult1[0][5];
  showOneCodeName('relation','RelationToMainInsured','RelationToMainInsuredName');
  
  document.all('RelationToAppnt').value = arrResult1[0][6];
  showOneCodeName('relationtoappnt','RelationToAppnt','RelationToAppntName');
  //查询职业名称
  var sql = "select occupationname from ldoccupation where occupationcode = '"+arrResult1[0][7]+"'";
  var result = easyExecSql(sql);
  if(result == "" || result == null)
  {
      document.all('OccupationCode').value = "";
  }
  else
  {
  	  document.all('OccupationCode').value = result[0][0];
  }
  	
  document.all('OccupationType').value = arrResult1[0][8];
  showOneCodeName('occupationtype','OccupationType','OccupationTypeName');
  
  return true;
 
}
