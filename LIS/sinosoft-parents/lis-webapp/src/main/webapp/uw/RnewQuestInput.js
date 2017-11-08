//程序名称：QuestInput.js
//程序功能：问题件录入
//创建日期：2002-06-19 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容

//该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="0";
var flag;
var k = 0;
var turnPage = new turnPageClass();
var QuestionOperator = "";

//提交，保存按钮对应操作
function submitForm(tOperator)
{
   if(getAllChecked()==false)
       return false;
   // return false;
    if(verifyInput()==false)
     return false;
	if(tOperator=='0')
	{
		//增加
		QuestionOperator = "INSERT";
	}
	else if(tOperator=='1')
	{
		QuestionOperator = "DELETE";
		var tRowNum = QuestGrid.getSelNo();
		if(tRowNum<=0)
		{
			alert("请选择需要删除的行!");
			return false;
		}
		if(document.all("hiddenQuestionSeq").value=='')
		{
			alert("没有需要删除的问题件!");
			return false;
		}
		if (!confirm('确认删除?'))
		{
		return false;
		}
		//alert(tRowNum);
		//return false;
	}
	else if(tOperator=='2')
	{
		QuestionOperator = "UPDATE";
		var tRowNum = QuestGrid.getSelNo();
		if(tRowNum<=0)
		{
			alert("请选择需要修改的行!");
			return false;
		}
		if(document.all("hiddenQuestionSeq").value=='')
		{
			alert("没有需要修改的问题件!");
			return false;
		}
		//tongmeng 2007-12-12 modify
		//对于返回给机构和问题件修改岗的不允许修改
		var tBackObjType = QuestGrid.getRowColData(QuestGrid.getSelNo() - 1, 8);
		//alert(tBackObjType);
		if(tBackObjType=='1'||tBackObjType=='5')
		{
			alert("发送给问题件修改岗和机构的不允许修改下发标记!");
			return false;
			}
		if (!confirm('确认修改?'))
		{
		return false;
		}
	}
	if(LoadFlag=="1"&&document.all('BackObj').value=="5"){
	  alert("在新契约录单时不允许选择“发送对象”为“问题件修改岗”，请重新选择“发送对象”！");
	  return;
	}
  var i = 0;
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
	document.all('hiddenQuestionOperator').value=QuestionOperator;
  fm.submit(); //提交
}


//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
   // showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
   var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    
    showInfo.close();
   
    //parent.close();
  }
  else
  { 
	var content="操作成功！";
  	var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo1 = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo1.focus();
  	showInfo.close();
  	initInpBox();
  	
  	//parent.close();
  	
    //执行下一步操作
  }
  questAllNeedQuestion(tContNo,tFlag)
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


function manuchkspecmain()
{
	fm.submit();
}

function QuestQuery(tContNo, tFlag)
{
	// 初始化表格
	var i,j,m,n;
	//initQuestGrid();
	
	
	// 书写SQL语句
	k++;
	var strSQL = "";
	//tongmeng 2007-09-12 modify
	//问题件使用5.3的描述
	//if (tFlag == "1")
	//{
	//	strSQL = "select code,cont from ldcodemod where "+k+"="+k				 	
	//			 + " and codetype = 'Question'"
	//			 + " and code <> '99'";
				 
//		strSQL = "select code,cont from ldcodemod where "+k+"="+k				 	
//				 + " and codetype = 'Question'";
		
		    var  sqlid1="RnewQuestInputSql0";
	 	 	var  mySql1=new SqlClass();
	 	 	mySql1.setResourceName("uw.RnewQuestInputSql"); //指定使用的properties文件名
	 	 	mySql1.setSqlId(sqlid1);//指定使用的Sql的id
	 	 	mySql1.addSubPara(k);//指定传入的参数
	 	 	mySql1.addSubPara(k);//指定传入的参数
	 	    var strSQL=mySql1.getString();
	//}
	
	//alert(strSQL);
	
  //查询SQL，返回结果字符串
  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
  
  //判断是否查询成功
  if (!turnPage.strQueryResult) {
    alert("没有问题件描述");
    return "";
  }
  
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //设置初始化过的MULTILINE对象，HealthGrid为在初始化页中定义的全局变量
  //turnPage.pageDisplayGrid = QuestGrid;    
          
  //保存SQL语句
  //turnPage.strQuerySql     = strSQL; 
  
  //设置查询起始位置
  //turnPage.pageIndex       = 0;  
  
  //在查询结果数组中取出符合页面显示大小设置的数组
  //var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  
  //调用MULTILINE对象显示查询结果
  //displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  var returnstr = "";
  var n = turnPage.arrDataCacheSet.length;  
    if (n > 0)
  {
  	for( i = 0;i < n; i++)
  	{
  		m = turnPage.arrDataCacheSet[i].length;
  		//alert("M:"+m);
  		if (m > 0)
  		{
  			for( j = 0; j< m; j++)
  			{
  				if (i == 0 && j == 0)
  				{
  					returnstr = "0|^"+turnPage.arrDataCacheSet[i][j];
  				}
  				if (i == 0 && j > 0)
  				{
  					returnstr = returnstr + "|" + turnPage.arrDataCacheSet[i][j];
  				}
  				if (i > 0 && j == 0)
  				{
  					returnstr = returnstr+"^"+turnPage.arrDataCacheSet[i][j];
  				}
  				if (i > 0 && j > 0)
  				{
  					returnstr = returnstr+"|"+turnPage.arrDataCacheSet[i][j];
  				}
  				
  			}
  		}
  		else
  		{
  			alert("查询失败!!");
  			return "";
  		}
  	}
}
else
{
	alert("查询失败!");
	return "";
}
  //alert("returnstr:"+returnstr);		
  fm.Quest.CodeData = returnstr;
  return "";	
}


function QueryCont(tContNo, tFlag)
{	
	
	// 书写SQL语句

	k++;

	var strSQL = "";

	//if (tFlag == "1")
	//{
//		strSQL = "select issuecont from RnewIssuePol where "+k+"="+k				 	
//				 + " and ContNo = '"+tContNo+"'";
		var  sqlid2="RnewQuestInputSql1";
 	 	var  mySql2=new SqlClass();
 	 	mySql2.setResourceName("uw.RnewQuestInputSql"); //指定使用的properties文件名
 	 	mySql2.setSqlId(sqlid2);//指定使用的Sql的id
 	 	mySql2.addSubPara(k);//指定传入的参数
 	 	mySql2.addSubPara(k);//指定传入的参数
 	 	mySql2.addSubPara(tContNo);//指定传入的参数
 	    var strSQL=mySql2.getString();
	//}
	
	//alert(strSQL);
	
  //查询SQL，返回结果字符串
  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
  
  //判断是否查询成功
  if (!turnPage.strQueryResult) {
    alert("没有录入过问题键！");
    return "";
  }
  
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  var returnstr = "";
  var n = turnPage.arrDataCacheSet.length;
  //alert("N:"+n);
  if (n > 0)
  {
  	m = turnPage.arrDataCacheSet[0].length;
  		//alert("M:"+m);
  		if (m > 0)
  		{
  			//alert("turnPage:"+turnPage.arrDataCacheSet[0][0]);
			returnstr = turnPage.arrDataCacheSet[0][0];
  		}
  		else
  		{
  			alert("没有录入过问题件！");
  			return "";
  		}
  	
  }
  else
  {
  	alert("没有录入过问题件！");
	return "";
  }

  if (returnstr == "")
  {
  	alert("没有录入过问题件！");
  }
  
  //alert("returnstr:"+returnstr);		
  document.all('Content').value = returnstr;
  //alert("已经录入过问题键，请考虑清楚再重新录入！");
  return returnstr;
}


//tongmeng 2007-11-13 modify
//按照MS的逻辑修改
function afterCodeSelect( cCodeName, Field )
{
	var tObjFlag = document.all('BackObj').value;
	var tCustomerNoFlag = document.all('QuestionObj').value;
	
	//alert(cCodeName);
	if(cCodeName=="rnbackobj")
	{
	  divQuestionnaireFlag.style.display = 'none';
	  if(tObjFlag == '1'||tObjFlag == '2')
	  {
		 divCustomerqustion.style.display = '';
		//只有在人工核保才显示问卷 2009-01-12
		 if(tObjFlag == '2'&& tFlag == "6")
		    divQuestionnaireFlag.style.display = '';
			
	  }
	  if(tObjFlag == '5' || tObjFlag == '3'|| tObjFlag == '5')
	  {
		divCustomerqustion.style.display = 'none';
		//divquestobjname.style.display = 'none';
	  //divquestobj.style.display = 'none';
	  }

	  //查询实际的问题件类型
	 //var tSQL = "select comcode,othersign from ldcode where codetype='backobj' and code='"+tObjFlag+"' ";
	    var  sqlid3="RnewQuestInputSql2";
	 	var  mySql3=new SqlClass();
	 	mySql3.setResourceName("uw.RnewQuestInputSql"); //指定使用的properties文件名
	 	mySql3.setSqlId(sqlid3);//指定使用的Sql的id
	 	mySql3.addSubPara(tObjFlag);//指定传入的参数
	    var tSQL=mySql3.getString();
	 var arrResult =  easyExecSql(tSQL);
	 //alert(arrResult[0][0]+"111"+arrResult[0][1]);
     if(arrResult==null)
	  {
	  	document.all('hiddenBackObj').value = " ";
	  	document.all('hiddenQuestionPrint').value =" ";
	  	alert("查询问题件类型出错!");
	  }
	  else
	  {
	 	 	document.all('hiddenBackObj').value = arrResult[0][0];
	 	 	document.all('hiddenQuestionPrint').value = arrResult[0][1];
	  }
   }
	//问卷选择
	var tDivName=document.all('Questionnaire').value;
	divQuestionnaire.style.display='none';
	if(cCodeName == "Questionnaire")
	{
	  if(tDivName=="Y")
	  {
	    divQuestionnaire.style.display='';
	  }
	  else
	  {
	  }
	}
	else
	{
		document.all('Questionnaire').value = 'N';
		document.all('QuestionnaireName').value = '否';
	}

if(cCodeName == "question")
{
	
if(tCustomerNoFlag =='0')
{
	
  initAppnt();
}
if(tCustomerNoFlag =='1')
{
	
	initInsured();
}
if(tCustomerNoFlag == '2'||tCustomerNoFlag == '3')
{
	document.all('CustomerNo').value = "";
	document.all('CustomerName').value = "";
}
	
}
}
function initAppnt()
{
	var tContNo = fm.ContNo.value;
//	strsql = " select appntno,appntname from lccont where  ContNo = '"+tContNo+"' ";
	var  sqlid5="RnewQuestInputSql3";
 	var  mySql5=new SqlClass();
 	mySql5.setResourceName("uw.RnewQuestInputSql"); //指定使用的properties文件名
 	mySql5.setSqlId(sqlid5);//指定使用的Sql的id
 	mySql5.addSubPara(tObjFlag);//指定传入的参数
    var strsql=mySql5.getString();
	
  //查询SQL，返回结果字符串
  turnPage.strQueryResult = easyQueryVer3(strsql, 1, 1, 1);  

  //判断是否查询成功
  if (!turnPage.strQueryResult) {
      
    return "";
  }
  
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  fm.CustomerNo.value = turnPage.arrDataCacheSet[0][0];
 
  fm.CustomerName.value = turnPage.arrDataCacheSet[0][1];
}
function initInsured()
{
	var tContNo = fm.ContNo.value;
//	alert(tContNo+","+document.all('QuestionObj').value);
//	strsql = "select insuredno,Name from lcinsured "
//	       + "where  ContNo = '"+tContNo+"' "
	       //+ "and SequenceNo = '"+document.all('QuestionObj').value+"'"
	       ;
	var  sqlid5="RnewQuestInputSql4";
 	var  mySql5=new SqlClass();
 	mySql5.setResourceName("uw.RnewQuestInputSql"); //指定使用的properties文件名
 	mySql5.setSqlId(sqlid5);//指定使用的Sql的id
 	mySql5.addSubPara(tContNo);//指定传入的参数
    var strsql=mySql5.getString();
  //查询SQL，返回结果字符串
  turnPage.strQueryResult = easyQueryVer3(strsql, 1, 1, 1);  

  //判断是否查询成功
  if (!turnPage.strQueryResult) {
      
    alert("无此客户");
    return ;
  }
  
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  fm.CustomerNo.value = turnPage.arrDataCacheSet[0][0];
 
  fm.CustomerName.value = turnPage.arrDataCacheSet[0][1];
}

//tongmeng 2007-11-08 add
//初始化查询所有需要回复的问题件
function questAllNeedQuestion(tContNo,tFlag)
{
	initQuestGrid();
//	strsql = " select proposalcontno,issuetype,issuecont,replyresult,(select username from lduser where usercode=a.operator),makedate,(select codename from ldcode where codetype='operatepos' and code=a.operatepos), "
//	       + " backobjtype,needprint,serialno,(select codename from ldcode where codetype='rnbackobj' and comcode=a.BackObjType ),(case when state is not null then state else 'x' end),QuestionObjType from RnewIssuePol a where contno='"+tContNo+"' "
//	       + " and state is null "
	       //+ " and prtseq is null "
	       ;
	var  sqlid6="RnewQuestInputSql5";
 	var  mySql6=new SqlClass();
 	mySql6.setResourceName("uw.RnewQuestInputSql"); //指定使用的properties文件名
 	mySql6.setSqlId(sqlid6);//指定使用的Sql的id
 	mySql6.addSubPara(tContNo);//指定传入的参数
    var strsql=mySql6.getString();

	//alert(strsql);
  //查询SQL，返回结果字符串
  turnPage.strQueryResult = easyQueryVer3(strsql, 1, 1, 1);  

  //判断是否查询成功
  if (!turnPage.strQueryResult) {
    return ;
  }
  
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  turnPage.pageDisplayGrid = QuestGrid;    
          
  //保存SQL语句
  turnPage.strQuerySql     = strsql; 
  
  //设置查询起始位置
  turnPage.pageIndex       = 0;  
  
  //在查询结果数组中取出符合页面显示大小设置的数组
  var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  
  //调用MULTILINE对象显示查询结果
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
}

function queryone(parm1,parm2)
{
	//alert(QuestGrid.getRowColData(QuestGrid.getSelNo() - 1, 10));
	document.all("hiddenQuestionSeq").value = QuestGrid.getRowColData(QuestGrid.getSelNo() - 1, 10);
	document.all("hiddenProposalContNo").value = QuestGrid.getRowColData(QuestGrid.getSelNo() - 1, 1);
	//alert(QuestGrid.getRowColData(QuestGrid.getSelNo() - 1, 12));
	document.all("hiddenQuestionState").value = QuestGrid.getRowColData(QuestGrid.getSelNo() - 1, 12);
	document.all("Content").value = QuestGrid.getRowColData(QuestGrid.getSelNo() - 1, 3);
	document.all("NeedPrintFlag").value = QuestGrid.getRowColData(QuestGrid.getSelNo() - 1, 9);
	var tSql ="select code,codename from ldcode where codename='"+QuestGrid.getRowColData(QuestGrid.getSelNo() - 1, 11)+"'";
	var arrResult =  easyExecSql(tSql);
	document.all("BackObj").value = arrResult[0][0];
	document.all("BackObjName").value = arrResult[0][1];
	
	afterCodeSelect( "rnbackobj", "" );

	document.all("QuestionObj").value = QuestGrid.getRowColData(QuestGrid.getSelNo() - 1, 13);
	    
    if(document.all("QuestionObj").value =='0')
	{
	    initAppnt();
	}
	if(document.all("QuestionObj").value =='1')
	{	
		initInsured();
	}
	if(document.all("QuestionObj").value == '2'||document.all("QuestionObj").value == '3')
	{
		document.all('CustomerNo').value = "";
		document.all('CustomerName').value = "";
	}
	var tSql2 ="select codename from ldcode where codetype='question' and code='"+QuestGrid.getRowColData(QuestGrid.getSelNo() - 1, 13)+"'";
	var arrResult2 =  easyExecSql(tSql2);
	document.all("QuestionObjName").value = arrResult2[0][0];
	//初始化问卷信息
	//alert("InitQuestionnaire");
	queryquestionnaire();
}

function getAllChecked()
{
   //alert(fm.Questionnaire.value);
 if(fm.Questionnaire.value =="Y")
 {
   var tHealthItem = "";
   var tOccupationItem = "";
   var tFinanceItem = "";
  // alert(window.document.forms[0].elements.length);
   for (elementsNum=0; elementsNum<window.document.forms[0].elements.length; elementsNum++)
		{
			if (window.document.forms[0].elements[elementsNum].type != null && window.document.forms[0].elements[elementsNum].type != "")
			{
			//if(window.document.forms[0].elements[elementsNum].type=='checkbox')
			//  {alert("Name:"+window.document.forms[0].elements[elementsNum].name); 
			//  alert("ID:"+window.document.forms[0].elements[elementsNum].id);
			// alert("Substring(0,1)"+window.document.forms[0].elements[elementsNum].name.substring(0,1));
			//  alert("Substring(0,2)"+window.document.forms[0].elements[elementsNum].name.substring(0,2));}
				if(window.document.forms[0].elements[elementsNum].type=='checkbox'
				&&window.document.forms[0].elements[elementsNum].name.substring(0,1)=='H'
				)
				{
			//	alert("当前为健康问卷");
				  //健康问卷
				//  alert(window.document.forms[0].elements[elementsNum].value);
				//  alert(window.document.forms[0].elements[elementsNum].id);
				  if(window.document.forms[0].elements[elementsNum].checked)
				  {
				//  alert("选中了"+window.document.forms[0].elements[elementsNum].value);
				    
				     tHealthItem = tHealthItem + "#" + window.document.forms[0].elements[elementsNum].id 
				                   +"-"+window.document.forms[0].elements[elementsNum].value;
				 //    alert(tHealthItem);
				     
				  }
				}else if(window.document.forms[0].elements[elementsNum].type=='checkbox'
				&&window.document.forms[0].elements[elementsNum].name.substring(0,1)=='O'
				){
			//	alert("当前为职业问卷");
				    //职业问卷
				  //  alert(window.document.forms[0].elements[elementsNum].value);
				  //  alert(window.document.forms[0].elements[elementsNum].id);
				  if(window.document.forms[0].elements[elementsNum].checked)
				  {
				  //  alert("选中了"+window.document.forms[0].elements[elementsNum].value);
				     tOccupationItem = tOccupationItem + "#" + window.document.forms[0].elements[elementsNum].id
				                  +"-"+window.document.forms[0].elements[elementsNum].value;
				//     alert(tOccupationItem);
				     
				  }
				}
				else if(window.document.forms[0].elements[elementsNum].type=='checkbox'
				&&window.document.forms[0].elements[elementsNum].name.substring(0,2)=='FO'
				){
			 //   alert("当前为财务和其问卷");
				    //财务和其他问卷
				  //   alert(window.document.forms[0].elements[elementsNum].value);
				  //   alert(window.document.forms[0].elements[elementsNum].id);
				  if(window.document.forms[0].elements[elementsNum].checked)
				  {
				  //   alert("选中！ 其中 id为："+window.document.forms[0].elements[elementsNum].id+"value 为："+window.document.forms[0].elements[elementsNum].value);
				     tFinanceItem = tFinanceItem + "#" + window.document.forms[0].elements[elementsNum].id
				                 +"-"+window.document.forms[0].elements[elementsNum].value;
				  //   alert(tFinanceItem);
				     
				  }
				}
			}
		}
	//	alert(tAllChecked);
		fm.HealthCheck.value = tHealthItem;
		fm.Occupation.value = tOccupationItem;
		fm.FinanceO.value = tFinanceItem;
		//alert("tHealthItem； "+tHealthItem);
		//alert("tOccupationItem： "+tOccupationItem);
	    //alert("tFinanceItem： "+tFinanceItem);
		if((tHealthItem ==""||tHealthItem ==null)&&(tOccupationItem==""||tOccupationItem==null)&&(tFinanceItem==""||tFinanceItem==null))
		{
		  alert("请至少选择一类问题件中的一种！");
		  return false;
		}
  	}
  		return true;
}

//清除所有check,设置为不选择
function clearAllCheck()
{
  for (elementsNum=0; elementsNum<window.document.forms[0].elements.length; elementsNum++)
		{
			if (window.document.forms[0].elements[elementsNum].type != null && window.document.forms[0].elements[elementsNum].type != "")
			{
				if(window.document.forms[0].elements[elementsNum].type=='checkbox'
				//||window.document.forms[0].elements[elementsNum].type=='radio'
				)
				{
				  //alert(window.document.forms[0].elements[elementsNum].id);
				  window.document.forms[0].elements[elementsNum].checked= false;
				}
			}
		}
}

//按照体检组合自动带出体检项目
function showBodyCheck(ID)
{
	clearAllCheck();
	//alert(ID);
	var strValue;
     strValue=ID.split("#");
    for(n=0;n<strValue.length;n++)
    {
     // alert(strValue[n]);
      if(strValue[n]!=null&&strValue[n]!='')
      {
      	   eval("document.fm."+strValue[n]+".checked = true");
      	  // window.document.forms[0].elements[elementsNum].checked
      }
    }
}

function queryquestionnaire()
{
    //查询体检子项目
//    var tSQL_Sub = " select asktype,asktypename,askcontentno,askcontentname from Rnewquestionnaire  "
//                 + " where proposalcontno = '"+fm.hiddenProposalContNo.value+"' ";
    var  sqlid7="RnewQuestInputSql6";
 	var  mySql7=new SqlClass();
 	mySql7.setResourceName("uw.RnewQuestInputSql"); //指定使用的properties文件名
 	mySql7.setSqlId(sqlid7);//指定使用的Sql的id
 	mySql7.addSubPara(fm.hiddenProposalContNo.value);//指定传入的参数
    var tSQL_Sub=mySql7.getString();
    
    arrResult=easyExecSql(tSQL_Sub);
   // alert(arrResult.length);
    var tHealthCheck = "";
    var tOccupation ="";
    var tFinanceO ="";
    if(arrResult!=null)       
    {
         fm.Questionnaire.value = 'Y';
         if(fm.BackObj.value=="2")
            divQuestionnaire.style.display='';
    	 for(i=0;i<arrResult.length;i++)
    	 {
    	 	 if(arrResult[i][0]=='H')
    	 	 {
    	 	    //健康问卷
    	 	    //类型-问卷编码 H-001
    	 	 	 tHealthCheck = tHealthCheck + '#'+arrResult[i][0]+arrResult[i][2];
    	 	 }
    	 	  else if(arrResult[i][0]=='O')
    	 	 {
    	 	    //职业问卷
    	 	 	  tOccupation = tOccupation + '#'+arrResult[i][0]+arrResult[i][2];
    	 	 }
              else if(arrResult[i][0]=='FO')
             {
                //财务及其他问卷
                tFinanceO = tFinanceO + '#'+arrResult[i][0]+arrResult[i][2];
             }
    	 }
    }else{
       fm.Questionnaire.value = 'N';
       divQuestionnaire.style.display='none';
    }  
   // alert(tAllCheckedItem); 
   // alert(tItemOther);  
        var tAllChecked = tHealthCheck+'#'+tOccupation+'#'+tFinanceO;     
      // alert(tAllChecked);
      showBodyCheck(tAllChecked);
	//	fm.proposalContNo.value = UWSpecGrid.getRowColData(tSelNo,5);
}


//tongmeng 2009-02-11 add
//问题件选择和问题件定义功能关联
function getClickIssueCode(Quest,Content)
{
	var tSendObj = document.all('BackObj').value;
	//alert(tManageCom.length);
	//if(length(tManageCom))
	if(tSendObj==null||tSendObj=='')
	{
		alert("请先录入发送对象!");
		return false;
	}
	var tSendSQL = " 1 and sendobj=#"+tSendObj+"# ";

  showCodeList('Quest',[Quest,Content],[0,1],null, tSendSQL, "1",'1');
}

function getKeyUpIssueCode(Quest,Content)
{
	var tSendObj = document.all('BackObj').value;
	var tSendSQL = " 1 and sendobj=#"+tSendObj+"# ";
    getBankCodeSQL = getBankCodeSQL + " and AgentCom like #"+tAgentBankCode+"%#" ;
    showCodeListKey('Quest',[Quest,Content],[0,1],null, tSendSQL, "1",'1');
}