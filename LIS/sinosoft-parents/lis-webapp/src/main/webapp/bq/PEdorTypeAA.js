//该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="1";
var turnPage = new turnPageClass();          //使用翻页功能，必须建立为全局变量
var selno;
var sqlresourcename = "bq.PEdorTypeAAInputSql";

function reback(){
	  document.all("fmtransact").value = "DELETE";             
	  var showStr="正在计算数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();    
    fm.action= "./PEdorTypeAASubmit.jsp";
    document.getElementById("fm").submit();;    
    
}

function edorTypeAASave()
{

		  selno = PolGrid.getSelNo()-1;    
	  if (selno <0)
	  {
	      alert("请选择要加保的险种！");
	      return;
	  }
	  var acontno = document.all("ContNo").value;
	  var aDate = document.all("EdorValiDate").value;
	  if(document.all("AAMoney").value == null){
		 alert("请填写加保金额！");
		 return;
	}
  var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  document.getElementById("fm").submit();;
}


//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content,Result )
{

	queryBackFee();
  showInfo.close();

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
  	 var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
     //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");    	
	 var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=350;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
     if(fm.fmtransact.value == "DELETE"){
        initForm();
        fm.fmtransact.value='';      
     }
     else{
        updatepol();
     }
  }

}

function updatepol(){
	 var polno2 = PolGrid.getRowColData(selno,10);

	var strSQL = "";
	var sqlid1="PEdorTypeAAInputSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql1.setSqlId(sqlid1);//指定使用的Sql的id
	mySql1.addSubPara(document.all("EdorNo").value);//指定传入的参数
	mySql1.addSubPara(polno2);
	strSQL=mySql1.getString();
	 
	 var arrResult = easyExecSql(strSQL, 1, 0);
//	 var arrResult = easyExecSql("select a.amnt,a.mult,(select sum(prem) from lpprem where polno = a.polno and edorno = a.edorno) from lppol a where 1 =1 and a.edorno ='"+document.all("EdorNo").value+"' and a.edortype = 'AA' and polno = '" + polno2 + "'", 1, 0);
	 if(arrResult == null){
	 	  return false;
	 }	 
	 PolGrid.setRowColData(selno, 6, arrResult[0][0]);
	 PolGrid.setRowColData(selno, 7, arrResult[0][1]);
	 PolGrid.setRowColData(selno, 8, arrResult[0][2]);
}


//提交前的校验、计算  
function beforeSubmit()
{
  //添加操作	
}           
       
//---------------------------
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

/*********************************************************************
 *  显示frmSubmit框架，用来调试
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function showSubmitFrame(cDebug)
{
	if( cDebug == "1" )
		parent.fraMain.rows = "0,0,50,82,*";
	else 
		parent.fraMain.rows = "0,0,0,72,*";
}



function returnParent()
{
	top.opener.initEdorItemGrid();
  top.opener.getEdorItemGrid();
  top.close();
}




function showNotePad()
{
	var MissionID = top.opener.document.all("MissionID").value;
	var SubMissionID = top.opener.document.all("SubMissionID").value;
	var ActivityID = '0000000003';
	var OtherNo = top.opener.document.all("OtherNo").value;

	var OtherNoType = "1";
	if(MissionID == null || MissionID == "")
	{
		alert("MissionID为空！");
		return;
	}		
	var varSrc= "&MissionID="+ MissionID + "&SubMissionID="+ SubMissionID + "&ActivityID="+ ActivityID + "&PrtNo="+ OtherNo + "&NoType="+ OtherNoType;
	var newWindow = OpenWindowNew("../uw/WorkFlowNotePadFrame.jsp?Interface= ../uw/WorkFlowNotePadInput.jsp" + varSrc,"工作流记事本查看","left");	
}

function Edorquery()
{
	var ButtonFlag = top.opener.document.all('ButtonFlag').value;
	if(ButtonFlag!=null && ButtonFlag=="1")
    {
       divEdorquery.style.display = "none";
    }
    else
 	{
 		divEdorquery.style.display = "";
 	}
}
function getMainPolInfo()
{
    
    //alert(tContNo);
    var tContNo= document.all('ContNo').value;		  
    // 书写SQL语句
//    var strSQL ="select InsuredNo,InsuredName,RiskCode,(select RiskName from LMRisk where LMRisk.RiskCode = LCPol.RiskCode),Prem,Amnt,CValiDate,contno,insuyear,payyears,paytodate from LCPol where polno = mainpolno and ContNo='"+tContNo+"'";
    
    var strSQL = "";
	var sqlid2="PEdorTypeAAInputSql2";
	var mySql2=new SqlClass();
	mySql2.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql2.setSqlId(sqlid2);//指定使用的Sql的id
	mySql2.addSubPara(tContNo);//指定传入的参数
	strSQL=mySql2.getString();
    
    turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
    //判断是否查询成功
    if (!turnPage.strQueryResult) 
    {
        return false;
    }
    
    //查询成功则拆分字符串，返回二维数组
    
    turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
    //设置初始化过的MULTILINE对象

    turnPage.pageDisplayGrid = MainPolGrid;    
    //保存SQL语句
    turnPage.strQuerySql = strSQL; 
    //设置查询起始位置
    turnPage.pageIndex = 0;  
    //在查询结果数组中取出符合页面显示大小设置的数组
    arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
    //调用MULTILINE对象显示查询结果
    displayMultiline(arrDataSet, turnPage.pageDisplayGrid);	
    
    
}



function showchange(){
	 var no3 = PolGrid.mulLineCount;	
	 //alert(no3); 
	 var tEdorNo = document.all('EdorNo').value;
	 for(var i = 0; i < no3; i++){
	 	  var tPolNo = PolGrid.getRowColData(i,10);
	 	  //alert(tPolNo);
//	 	  var sql3 = "select a.amnt,a.mult,b.sumactupaymoney from lppol a,ljspayperson b where a.edorno = '" + tEdorNo + "' and a.polno = b.polno and a.polno = '" + tPolNo + "'";
	 	  
	var sql3 = "";
	var sqlid3="PEdorTypeAAInputSql3";
	var mySql3=new SqlClass();
	mySql3.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql3.setSqlId(sqlid3);//指定使用的Sql的id
	mySql3.addSubPara(tEdorNo);//指定传入的参数
	mySql3.addSubPara(tPolNo);//指定传入的参数
	sql3=mySql3.getString();
	 	  
	 	  //alert(sql3);
	 	  var bResult = easyExecSql(sql3);
	 	  //alert(bResult);
	 	  if(bResult == null){
	 	  	  continue;
	 	  }
	 	  PolGrid.setRowColData(i, 6, bResult[0][0]);
	    PolGrid.setRowColData(i, 7, bResult[0][1]);
	    PolGrid.setRowColData(i, 8, bResult[0][2]);

	    
	 }	 
}

/*********************************************************************
 *  查询保全项目，写入MulLine
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function getPolInfo()
{
    showchange();
    var tContNo=document.all("ContNo").value;


/*        var strSQL =" select (select riskcode from lcpol where polno = a.polno), "
					+ "	(select riskname "
					+ "		 from lmriskapp "
					+ "		where riskcode = "
					+ "					(select riskcode from lcpol where polno = a.polno)), "
					+ "	(case "
					+ "		when (select amntflag from lmduty where dutycode = a.dutycode) = '1' then "
					+ "		 a.amnt "
					+ "		else   "
					+ "		 a.mult "
					+ "	end),    "
					+ "	prem,    "
					+ "	'',      "
					+ "	'',      "
					+ "	'',      "
					+ "	a.polno, "
					+ "	(case when (select amntflag from lmduty where dutycode = a.dutycode)='1' then '按保额' else '按份数' end)		" 
			+ " from lcduty a  "
      + " where contno='"+tContNo+"' "
      + " and not exists (select 'Y' from LCPol where PolNo=a.PolNo and mainpolno!=polno and InsuYearFlag='Y' and InsuYear>1)  "
			+ " and not exists (select 'M' from LCPol where PolNo=a.PolNo and mainpolno!=polno and InsuYearFlag='M' and InsuYear>12) "
			+ " and not exists (select 'D' from LCPol where PolNo=a.PolNo and mainpolno!=polno and InsuYearFlag='D' and InsuYear>365)";
*/         
    var strSQL = "";
	var sqlid4="PEdorTypeAAInputSql4";
	var mySql4=new SqlClass();
	mySql4.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql4.setSqlId(sqlid4);//指定使用的Sql的id
	mySql4.addSubPara(tContNo);//指定传入的参数
	strSQL=mySql4.getString();           

        turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
        //判断是否查询成功
        if (!turnPage.strQueryResult)
        {
            alert("没有投保附件险或附加险已中止，不能进行附加险加保!");
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
function getCustomerInfo()
{
    var ContNo=document.all("ContNo").value;
    if(ContNo!=null&&ContNo!="")
    {
//        var strSQL ="select AppntName,AppntIDType,AppntIDNo,InsuredName,InsuredIDType,InsuredIDNo from LCCont where ContNo='"+ContNo+"'";
        
    var strSQL = "";
	var sqlid5="PEdorTypeAAInputSql5";
	var mySql5=new SqlClass();
	mySql5.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql5.setSqlId(sqlid5);//指定使用的Sql的id
	mySql5.addSubPara(ContNo);//指定传入的参数
	strSQL=mySql5.getString();           
        
        turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
        //判断是否查询成功
        if (!turnPage.strQueryResult)
        {
            return false;
        }
        //查询成功则拆分字符串，返回二维数组
        turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
        //设置初始化过的MULTILINE对象
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

}

function queryCustomerInfo()
{
  var tContNo=top.opener.document.all('ContNo').value;
    var strSQL="";
    //alert("------"+tContNo+"---------");
    if(tContNo!=null || tContNo !='')
      {
/*      strSQL = " Select a.appntno,'投保人',a.appntname,a.appntsex||'-'||sex.codename,a.appntbirthday,a.idtype||'-'||x.codename,a.idno From lcappnt a "
                                        +" Left Join (Select code,codename From ldcode Where codetype='idtype') x On x.code = a.idtype "
                                        +" Left Join (Select code,codename From ldcode Where codetype='sex') sex On sex.code = a.appntsex  Where contno='"+tContNo+"'"
                                        +" Union"
                                        +" Select i.insuredno,'被保人',i.name,i.Sex||'-'||sex.codename,i.Birthday,i.IDType||'-'||xm.codename,i.IDNo From lcinsured i "
                                        +" Left Join (Select code,codename From ldcode Where codetype='idtype') xm On xm.code = i.idtype "
                                        +" Left Join (Select code,codename From ldcode Where codetype='sex') sex On sex.code = i.sex Where contno='"+tContNo+"'";
*/    //alert("-----------"+strSQL+"------------");
	var sqlid6="PEdorTypeAAInputSql6";
	var mySql6=new SqlClass();
	mySql6.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql6.setSqlId(sqlid6);//指定使用的Sql的id
	mySql6.addSubPara(tContNo);//指定传入的参数
	mySql6.addSubPara(tContNo);//指定传入的参数
	strSQL=mySql6.getString();      
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
                 
                 
                 
                 
                 
                 
                 
                 
                 
                 
                 
                 
                 
                 
                 
                 
                 
                 
                 
                 