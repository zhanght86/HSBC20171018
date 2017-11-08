//该文件中包含客户端需要处理的函数和事件
var showInfo;
var GEdorFlag = true;
var selGridFlag = 0;            //标识当前选中记录的GRID
var sqlresourcename = "bq.PEdorTypeARDetailInputSql";

//该文件中包含客户端需要处理的函数和事件
var turnPage1 = new turnPageClass();
var turnPage = new turnPageClass();           //使用翻页功能，必须建立为全局变量
var turnPage3 = new turnPageClass();          //使用翻页功能，必须建立为全局变量
//重定义获取焦点处理事件
//window.onfocus = initFocus;

//查询按钮
function queryClick()
{
	var tPolNo=document.all('PolNo').value;
	var strSql="";
	var tStartDate="";
////	strSql="select min(startdate) from loaccunitprice where riskcode in(select riskcode from lcpol where polno='"+tPolNo+"') and startdate>='"+fm.EdorItemAppDate.value+"'";
///////////////////////add by luzhe on 2007-09-17///////////////////////
    /*      var mySql=new SqlClass();
              mySql.setJspName("../../bq/PEdorTypeARDetailInputSql.jsp");
              mySql.setSqlId("PEdorTypeARDetailInputSql_0");
              mySql.addPara('tPolNo',tPolNo);
              mySql.addPara('EdorItemAppDate',fm.EdorItemAppDate.value); */
//              mySql.addPara('"'";',"'";);
//              turnPage.queryModal(mySql.getString(), XXXXGrid);
///////////////////////end                 add///////////////////////
  var sqlid1="PEdorTypeARDetailInputSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql1.setSqlId(sqlid1);//指定使用的Sql的id
	mySql1.addSubPara(document.all('PolNo').value);//指定传入的参数
	mySql1.addSubPara(document.all('EdorItemAppDate').value);//指定传入的参数
	strSql=mySql1.getString();
	var arrResult=easyExecSql(strSql);
	if(arrResult==null||arrResult=="")
	{
////		strSql="select max(startdate) from loaccunitprice where riskcode in(select riskcode from lcpol where polno='"+tPolNo+"') and startdate<'"+fm.EdorItemAppDate.value+"'";
///////////////////////add by luzhe on 2007-09-17///////////////////////
      /*    var mySql=new SqlClass();
              mySql.setJspName("../../bq/PEdorTypeARDetailInputSql.jsp");
              mySql.setSqlId("PEdorTypeARDetailInputSql_1");
              mySql.addPara('tPolNo',tPolNo);
              mySql.addPara('EdorItemAppDate',fm.EdorItemAppDate.value); */
//              mySql.addPara('"'";',"'";);
//              turnPage.queryModal(mySql.getString(), XXXXGrid);
///////////////////////end                 add///////////////////////
          var tSQL = "";
          var sqlid2="PEdorTypeARDetailInputSql2";
					var mySql2=new SqlClass();
					mySql2.setResourceName(sqlresourcename); //指定使用的properties文件名
					mySql2.setSqlId(sqlid2);//指定使用的Sql的id
					mySql2.addSubPara(document.all('PolNo').value);//指定传入的参数
					mySql2.addSubPara(document.all('EdorItemAppDate').value);//指定传入的参数
					tSQL=mySql2.getString();
					
		arrResult=easyExecSql(tSQL);
	}
	tStartDate=arrResult[0][0];
	
	//var strSql="select a.InsuredNo,a.PolNo,a.InsuAccNo,b.InsuAccName,a.PayPlanCode,(select payplanname from lmriskaccpay where riskcode=a.riskcode and insuaccno=a.insuaccno and payplancode=a.PayPlanCode),a.UnitCount,a.UnitCount-(select nvl(sum(accoutunit),0) from lpinsuaccout where trim(outpolno)=trim(a.polno) and trim(outinsuaccno)=trim(a.insuaccno) and trim(outpayplancode)=trim(a.payplancode) and state!='0' and edorno in (select edorno from lpedoritem where contno = a.contno and edorstate in ('0','1','2','3'))),(select nvl(sum(accoutunit),0) from lpinsuaccout where trim(outpolno)=trim(a.polno) and trim(outinsuaccno)=trim(a.insuaccno) and trim(outpayplancode)=trim(a.payplancode) and state!='0' and edorno in (select edorno from lpedoritem where contno = a.contno and edorstate in ('0','1','2','3'))),nvl((select UnitPriceSell from loaccunitprice where riskcode=a.riskcode and insuaccno=a.insuaccno and startdate='"+tStartDate+"'),0) from lcinsureaccclass a,lmriskinsuacc b where a.insuaccno=b.insuaccno and PolNo='"+fm.PolNo.value+"' and b.acctype='002' order by a.appntno,a.polno,a.insuaccno,a.payplancode";
////	  var strSql="select a.InsuredNo,a.PolNo,a.InsuAccNo,b.InsuAccName,a.UnitCount,a.UnitCount-(select nvl(sum(accoutunit),0) from lpinsuaccout where trim(outpolno)=trim(a.polno) and trim(outinsuaccno)=trim(a.insuaccno) and state!='0' and edorno in (select edorno from lpedoritem where contno = a.contno and edorstate in ('0','1','2','3'))),(select nvl(sum(accoutunit),0) from lpinsuaccout where trim(outpolno)=trim(a.polno) and trim(outinsuaccno)=trim(a.insuaccno) and state!='0' and edorno in (select edorno from lpedoritem where contno = a.contno and edorstate in ('0','1','2','3'))),nvl((select UnitPriceSell from loaccunitprice where riskcode=a.riskcode and insuaccno=a.insuaccno and startdate='"+tStartDate+"'),0) from lcinsureacc a,lmriskinsuacc b where a.insuaccno=b.insuaccno and PolNo='"+fm.PolNo.value+"' and b.acctype='002' order by a.appntno,a.polno,a.insuaccno";
///////////////////////add by luzhe on 2007-09-17///////////////////////
      /*    var mySql2=new SqlClass();
              mySql2.setJspName("../../bq/PEdorTypeARDetailInputSql.jsp");
              mySql2.setSqlId("PEdorTypeARDetailInputSql_2");
              mySql2.addPara('tStartDate',tStartDate);
              mySql2.addPara('PolNo',fm.PolNo.value); */
//              turnPage.queryModal(mySql.getString(), XXXXGrid);
///////////////////////end                 add///////////////////////

          var sqlid3="PEdorTypeARDetailInputSql3";
					var mySql3=new SqlClass();
					mySql3.setResourceName(sqlresourcename); //指定使用的properties文件名
					mySql3.setSqlId(sqlid3);//指定使用的Sql的id
					mySql3.addSubPara(tStartDate);//指定传入的参数
					mySql3.addSubPara(document.all('PolNo').value);//指定传入的参数
					

	turnPage1.pageDivName = "divPage1";
	turnPage1.queryModal(mySql3.getString(), LCGrpInsuAccGrid);
	
	
	//判断犹豫期
//	arrResult = easyExecSql(strSql);
//	var strpolno = arrResult[0][1];
//	
//	
//	var SQL = "select a.hesitateend,c.CustomGetPolDate from lmedorwt a,lcpol b,lccont c where b.polno = '"+strpolno+"' and c.contno = b.contno and a.riskcode = b.riskcode ";
//	var result = easyExecSql(SQL);
//	var datecount = result[0][0];
//	var CustomGetPolDate = result[0][1];
//	
//	var EdorAcceptNo = document.all('EdorAcceptNo').value;
//	var SQL2 = "select EdorValiDate from lpedoritem where EdorAcceptNo = '"+EdorAcceptNo+"'";
//	var result = easyExecSql(SQL2);
//	var EdorValiDate = result[0][0];
//	//alert(CustomGetPolDate);
//	
//	if(dateDiff(CustomGetPolDate,EdorValiDate,'D')-10>=0)
//				{
//					
//					}
//				else
//					{				
//				document.all("userinputbutton").style.display = "none";
//				alert("犹豫期内不能赎回");
//			}
//	
	//strSql="select a.outinsuredno,a.outpolno,a.outinsuaccno,a.outpayplancode,a.accoutunit,a.accoutbala,a.modifydate,a.modifytime,a.serialno from lpinsuaccout a where a.edorno='"+fm.EdorNo.value+"' and a.contno='"+fm.ContNo.value+"' and a.edortype='"+fm.EdorType.value+"' order by a.serialno ";
////	  strSql=" select outinsuredno,outpolno,outinsuaccno,'',sum(accoutunit),'',max(modifydate),max(modifytime),'' from lpinsuaccout a where edorno='"+fm.EdorNo.value+"' group by outinsuredno,outpolno,outinsuaccno";
///////////////////////add by luzhe on 2007-09-17///////////////////////
    /*      var mySql3=new SqlClass();
              mySql3.setJspName("../../bq/PEdorTypeARDetailInputSql.jsp");
              mySql3.setSqlId("PEdorTypeARDetailInputSql_3");  
              mySql3.addPara('EdorNo',fm.EdorNo.value);  */
//              mySql.addPara('"' group by outinsuredno,outpolno,outinsuaccno";',"' group by outinsuredno,outpolno,outinsuaccno";);
//              turnPage.queryModal(mySql.getString(), XXXXGrid);
///////////////////////end                 add///////////////////////
          var sqlid4="PEdorTypeARDetailInputSql4";
					var mySql4=new SqlClass();
					mySql4.setResourceName(sqlresourcename); //指定使用的properties文件名
					mySql4.setSqlId(sqlid4);//指定使用的Sql的id
					mySql4.addSubPara(document.all('EdorNo').value);//指定传入的参数
					
	turnPage3.pageDivName = "divPage3";
	turnPage3.queryModal(mySql4.getString(), LPInsuAccGrid); 
}

//个人账户赎回
function GEdorRDDetail()
{
  if (LCGrpInsuAccGrid.getSelNo()==0)
  {
    alert("请选择赎回账户!");
    return;
  }
  //转出单位数
  try {
  	var OutUnit=LCGrpInsuAccGrid.getRowColData(LCGrpInsuAccGrid.getSelNo()-1,9);
  	var OutBala=LCGrpInsuAccGrid.getRowColData(LCGrpInsuAccGrid.getSelNo()-1,10);
  	//alert(OutUnit+"222"+OutBala);
	  if((OutUnit==null||OutUnit=='')&&(OutBala==null||OutBala==''))
	  {
		alert("请输入赎回单位或赎回金额!");
		return;	
	}else if(OutUnit-100<0&&OutUnit!=LCGrpInsuAccGrid.getRowColData(LCGrpInsuAccGrid.getSelNo()-1,6))
		{
			alert("每笔领取的投资单位数不得少于100!");
			//alert(LCGrpInsuAccGrid.getRowColData(LCGrpInsuAccGrid.getSelNo()-1,8));
			return false;
		}else if(OutUnit%100!=0&&OutUnit!=LCGrpInsuAccGrid.getRowColData(LCGrpInsuAccGrid.getSelNo()-1,6))
			{
				alert("每笔领取的投资单位数必须为100的整数倍!");
				return false;
				}
			else if(OutUnit -0<= 0)
				{
					alert("不能领取0个或0个以下单位!");
				return false;
					}
				else if(OutUnit-LCGrpInsuAccGrid.getRowColData(LCGrpInsuAccGrid.getSelNo()-1,6)>0)
					{
						alert("领取单位数不能大于可赎回单位数!");
				    return false;
						}
  } catch(ex) { 
  	alert("请输入正确单位数!");
	return;	
  	}
  //return false;
  var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");     
  var name='提示';   //网页名称，可为空; 
var iWidth=550;      //弹出窗口的宽度; 
var iHeight=250;     //弹出窗口的高度; 
var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

showInfo.focus();
  document.all('Transact').value = "INSERT||PMAIN";
  fm.action = "./PEdorTypeARDetailSubmit.jsp";
  fm.submit();
}

//撤销个人保全
function cancelGEdor()
{
 if (LPInsuAccGrid.getSelNo()==0)
  {
    alert("请选择需要撤销的关联交易!");
    return;
  }

  var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");     
  var name='提示';   //网页名称，可为空; 
var iWidth=550;      //弹出窗口的宽度; 
var iHeight=250;     //弹出窗口的高度; 
var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

showInfo.focus();
  document.all('Transact').value = "DELETE||PMAIN";
  fm.action = "./PEdorTypeARDetailSubmit.jsp";
  fm.submit();
}


//提交后操作,服务器数据返回后执行的操作
function afterSubmit(FlagStr, content, Result)
{
	showInfo.close();
	if (FlagStr == "Fail")
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
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
		initLCGrpInsuAccGrid();
    		initLPInsuAccGrid();
		queryClick();
	}
}

/*********************************************************************
 *  显示frmSubmit框架，用来调试
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function showSubmitFrame(cDebug) {
	if( cDebug == "1" )
		parent.fraMain.rows = "0,0,50,82,*";
	else 
		parent.fraMain.rows = "0,0,0,72,*";
}

function returnParent() {
	top.close();
	top.opener.initLPInsuAccGrid();
	top.opener.QueryBussiness();
}
function QueryEdorInfo()
{
	var tEdortype=document.all('EdorType').value
	var strSQL ="";
	if(tEdortype!=null || tEdortype !='')
	{
	  /*  strSQL="select distinct edorcode, edorname from lmedoritem where edorcode = '" + tEdortype + "'";
///////////////////////add by luzhe on 2007-09-17///////////////////////
          var mySql=new SqlClass();
              mySql.setJspName("../../bq/PEdorTypeARDetailInputSql.jsp");
              mySql.setSqlId("PEdorTypeARDetailInputSql_4");
              mySql.addPara('tEdortype',tEdortype); */
//              mySql.addPara('"'";',"'";);
//              turnPage.queryModal(mySql.getString(), XXXXGrid);
///////////////////////end                 add///////////////////////
          var sqlid5="PEdorTypeARDetailInputSql5";
					var mySql5=new SqlClass();
					mySql5.setResourceName(sqlresourcename); //指定使用的properties文件名
					mySql5.setSqlId(sqlid5);//指定使用的Sql的id
					mySql5.addSubPara(tEdortype);//指定传入的参数

					strSQL = mySql5.getString();
    }
    else
	{
		alert('未查询到保全批改项目信息！');
	}
	
	var arrSelected = new Array();	
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
	arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
    //try {document.all('EdorType').value = arrSelected[0][0];} catch(ex) { }; 
    try {document.all('EdorTypeName').value = arrSelected[0][1];} catch(ex) { }; 
}
