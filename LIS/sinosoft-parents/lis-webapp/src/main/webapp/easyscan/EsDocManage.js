
//程序名称：EsDocManage.js
//程序功能：
//创建日期：2004-06-02
//创建人  ：LiuQiang
//更新记录：  更新人    更新日期     更新原因/内容


//               该文件中包含客户端需要处理的函数和事件

var arrDataSet 
var tArr;
var turnPage = new turnPageClass();
var mySql = new SqlClass();
var mDebug="0";
var mOperate="";
var showInfo;
window.onfocus=myonfocus;
//使得从该窗口弹出的窗口能够聚焦
function myonfocus()
{
	if(showInfo!=null)
	{
	  try
	  {
	    showInfo.focus();  
	  }
	  catch(ex)
	  {
	    showInfo=null;
	  }
	}
}

//提交，保存按钮对应操作
function submitForm()
{
  var i = 0;
  var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
//    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
    var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  //fm.hideOperate.value=mOperate;
  //if (fm.hideOperate.value=="")
  //{
  //  alert("操作控制数据丢失！");
  //}
  //showSubmitFrame(mDebug);
  document.getElementById("fm").submit(); //提交
}


//提交后操作,服务器数据返回后执行的操作
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
  	//parent.fraInterface.initForm();
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

    //showDiv(operateButton,"true"); 
    //showDiv(inputButton,"false"); 
    //执行下一步操作
//    divReason.style.display=""; 
    fm.DelReasonCode.value="";
    fm.DelReason.value="";
    if(fm.DOC_CODE.value == null || fm.DOC_CODE.value == '')
    {}
    else 	
    	easyQueryClick();
  }
  
}



//重置按钮对应操作,Form的初始化函数在功能名+Init.jsp文件中实现，函数的名称为initForm()
function resetForm()
{
  try
  {
//    showDiv(operateButton,"true"); 
//    showDiv(inputButton,"false"); 
	  initForm();
  }
  catch(re)
  {
  	alert("在OLDCode.js-->resetForm函数中发生异常:初始化界面错误!");
  }
} 

//取消按钮对应操作
function cancelForm()
{
//  window.location="../common/html/Blank.html";
    showDiv(operateButton,"true"); 
    showDiv(inputButton,"false"); 
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
  		parent.fraMain.rows = "0,0,0,82,*";
 	}
}


//Click事件，当点击增加图片时触发该函数
function addClick()
{
  alert("增加功能暂时不提供，请使用EasyScan上载单证数据!");
  //下面增加相应的代码
  //mOperate="INSERT||MAIN";
//  showDiv(operateButton,"false"); 
//  showDiv(inputButton,"true"); 
//  fm.fmtransact.value = "INSERT||CODE" ;
}           

//Click事件，当点击“修改”图片时触发该函数
function updateClick()
{
	if(fm.PrtNo.value == null || fm.PrtNo.value == '')
	{
		alert("请先查询！");
		return false;
	}
	if(fm.DOC_CODE.value == null || fm.DOC_CODE.value == '')
	{
		alert("请先录入修改后的印刷号码！");
		return false;
	}
  //下面增加相应的代码
  if (confirm("您确实想修改该印刷号码吗?"))
  {
  	var i = 0;
  	var showStr="正在更新数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
  	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  
  	//showSubmitFrame(mDebug);
  	fm.fmtransact.value = "UPDATE||MAIN";

  	document.getElementById("fm").submit(); //提交
  }
}

function updateClick1()
{
	
	//05-12  增加只能修改同一印刷号长度的校验
//	for(var index=0; index< count; index++)
//	{
//	   if(CodeGrid.getChkNo(index)==true)
//	   {
//	   	flag =flag+1;
//	   	str = CodeGrid.getRowColData(index,12);	
//	   	str2 = CodeGrid.getRowColData(index,2);
//	   	if(str1 != '' && str1 != str2)//校验是否有不一致的单证内部号
//	   		flaga = 1;//有不一致单证内部号
//	   	str1 = CodeGrid.getRowColData(index,2);	   	
//	   }
//	}
	
	if(!checkOnlyOne(2))
		return false;	
  //下面增加相应的代码
  if (confirm("您确实想修改该记录吗?"))
  {
  	var i = 0;
  	var showStr="正在更新数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
  	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  
  	//showSubmitFrame(mDebug);
  	fm.fmtransact.value = "UPDATE||DOCID";

  	document.getElementById("fm").submit(); //提交
  }
}           

//Click事件，当点击“查询”图片时触发该函数
function queryClick()
{
  //下面增加相应的代码
  //mOperate="QUERY||MAIN";
  if(fm.DOC_ID.value=="" && fm.DOC_CODE.value=="")
  {
      alert("请输入印刷号或单证内部号！");
      return;
  }
  
  easyQueryClick();
  
  //showInfo=window.open("./EsDocMainQuery.html");
}

// 查询按钮
function easyQueryClick()
{						 
	//alert(strSQL);
	/*var strSQL = " select docid,(select bussno from es_doc_relation where docid=a.docid and bussnotype='11'),'',(to_char(makedate,'yyyy-mm-dd')||' '||maketime),'',scanoperator,"
					+ "managecom,inputstate,operator,inputstartdate,inputstarttime "
					+ ",docflag,docremark,'',inputenddate,inputendtime,busstype,scanno,subtype " 
					+ " from ES_DOC_MAIN a where 1=1 " 
					+ " "+ getWherePart( 'DOCID','DOC_ID' );*/
	
	mySql = new SqlClass();
	mySql.setResourceName("easyscan.EsDocManageSql");
	mySql.setSqlId("EsDocManageSql1");
	mySql.addSubPara(fm.DOC_ID.value ); 	
				
					if(fm.DOC_CODE.value!=null && fm.DOC_CODE.value!='')
					{
					//	strSQL = strSQL + " and docid in (select docid from es_doc_relation where bussno='"+fm.DOC_CODE.value+"' and bussnotype='11') ";
					mySql = new SqlClass();
					mySql.setResourceName("easyscan.EsDocManageSql");
					mySql.setSqlId("EsDocManageSql2");
					mySql.addSubPara(fm.DOC_ID.value ); 
					mySql.addSubPara(fm.DOC_CODE.value ); 
					}
					//+ " "+ getWherePart( 'DOCCODE','DOC_CODE' )
				//strSQL = strSQL + " order by makedate,maketime";		 
	
	turnPage.strQueryResult  = easyQueryVer3(mySql.getString(), 1, 1, 1);  
  
	//判断是否查询成功
	if (!turnPage.strQueryResult) {
		//alert("查询失败！");
		//return false;
    }
	//查询成功则拆分字符串，返回二维数组
	arrResult = decodeEasyQueryResult(turnPage.strQueryResult);
	
	if( arrResult != null )
	{
		//alert(1);
		document.all( 'DOC_ID' ).value = 			arrResult[0][0];
		document.all( 'PrtNo' ).value = 			arrResult[0][1];
		document.all( 'DOC_CODE' ).value = 		arrResult[0][1];
		document.all('NUM_PAGES').value = 		arrResult[0][2];
		document.all('INPUT_DATE').value = 		arrResult[0][3];
		//document.all('Input_Time').value = 		arrResult[0][4];
		document.all('ScanOperator').value = 		arrResult[0][5];  
		document.all('ManageCom').value = 		arrResult[0][6];
		document.all('InputState').value = 		arrResult[0][7];
		document.all('Operator').value = 			arrResult[0][8];
		document.all('InputStartDate').value = 	arrResult[0][9];
		document.all('InputStartTime').value = 	arrResult[0][10];
		document.all('DOC_FLAGE').value = 		arrResult[0][11];
		document.all('DOC_REMARK').value = 		arrResult[0][12];
		//document.all('Doc_Ex_Flag').value = 		arrResult[0][13];
		document.all('InputEndDate').value = 		arrResult[0][14];
		document.all('InputEndTime').value = 		arrResult[0][15];
		document.all('BussType').value = 		arrResult[0][16];
		document.all('ScanNo').value = 		arrResult[0][17];
		/*document.all('SubType').value = 		arrResult[0][18];
		if(fm.SubType.value!="")
			showOneCodeName('imagetype','SubType','SubTypeName');
		else
			fm.SubTypeName.value="";*/
		//alert(1);
		
	}
	else
	{
		//alert(2);
		initInpBox1();
	}
		
  	queryPages(fm.DOC_CODE.value);
}           

//Click事件，当点击“删除”图片时触发该函数
function deleteClick()
{
	//提示需要录入删除原因
	//var strSQL = " select nvl(count(*),0) from ES_DOC_MAIN where doccode='"+fm.PrtNo.value+"'"; 	
	mySql = new SqlClass();
	mySql.setResourceName("easyscan.EsDocManageSql");
	mySql.setSqlId("EsDocManageSql3");
	mySql.addSubPara(fm.PrtNo.value );  
	var brr = easyExecSql(mySql.getString());
    if ( brr )  
    {		//银邮保通可以进行删除
    		/*strSQL = " select 1 from lccont where prtno='"+fm.PrtNo.value+"' and appflag='1' and (salechnl!='03'"
    					+ " and selltype!='08')"; */
			mySql = new SqlClass();
			mySql.setResourceName("easyscan.EsDocManageSql");
			mySql.setSqlId("EsDocManageSql4");
			mySql.addSubPara(fm.PrtNo.value );  
			var brr1 = easyExecSql(mySql.getString());
		    if ( brr1 )  
		    {
		    	alert("该扫描件所属投保单已经签单，不能删除所有扫描件！");
				return false;
		    }
    	//alert(brr[0][0]);
    	if( brr[0][0]> 0 )
    	{
    		if(fm.DelReasonCode.value == null || fm.DelReasonCode.value == '')
			{
				alert("请先选择删除原因类型！");
				divReason.style.display="";
				return false;
			}
			if(fm.DelReason.value == null || fm.DelReason.value == '')
			{
				alert("请先录入删除原因！");
				divReason.style.display="";
				return false;
			}
    	}    		
    }
    else
    {
    	alert("该印刷号下没有扫描件可以删除！");
    	return false;
    }
    
	//下面增加相应的删除代码
  if (confirm("您确实想删除该印刷号下所有单证吗?"))
  {	 
	var i = 0;
  	var showStr="正在删除数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
  	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  	
  	//showSubmitFrame(mDebug);
  	fm.fmtransact.value = "DELETE||MAIN";

  	document.getElementById("fm").submit(); //提交
  	initForm();
  }
  else
  {
    //mOperate="";
    alert("您取消了删除操作！");
  }
}   

function deleteClick1()
{
	if(!checkOnlyOne(1))
		return false;
	//校验如果该单证为印刷号下最后一张单证则提示需要录入删除原因
	//var strSQL = " select nvl(count(*),0) from ES_DOC_MAIN where doccode='"+fm.PrtNo.value+"'"; 	
	mySql = new SqlClass();
			mySql.setResourceName("easyscan.EsDocManageSql");
			mySql.setSqlId("EsDocManageSql5");
			mySql.addSubPara(fm.PrtNo.value );  
	var brr = easyExecSql(mySql.getString());
    if ( brr )  
    {
    	//alert(brr[0][0]);
    	//2010-04-09 ln add 删除任何一种单证都需要录入删除原因
    	//if( brr[0][0]== 1 )
    	if( brr[0][0]>0 )
    	{
    		//银邮保通可以进行补扫
    		/*strSQL = " select 1 from lccont where prtno='"+fm.PrtNo.value+"' and appflag='1' and (salechnl!='03'"
    					+ " and selltype!='08')"; 	*/
			mySql = new SqlClass();
			mySql.setResourceName("easyscan.EsDocManageSql");
			mySql.setSqlId("EsDocManageSql6");
			mySql.addSubPara(fm.PrtNo.value );  
			var brr1 = easyExecSql(mySql.getString());
		    if ( brr1 )  
		    {
		    	alert("该扫描件所属投保单已经签单，不能删除所有扫描件！");
				return false;
		    }
		    if(fm.DelReasonCode.value == null || fm.DelReasonCode.value == '')
			{
				alert("请先选择单证删除原因类型！");
				divReason.style.display="";
				return false;
			}
    		if(fm.DelReason.value == null || fm.DelReason.value == '')
			{
				alert("请先录入单证删除原因！");
				divReason.style.display="";
				return false;
			}
		}
    }
    else
    {
    	alert("该印刷号下没有扫描件可以删除！");
    	return false;
    } 
	
  //下面增加相应的删除代码
  if (confirm("您确实想删除该记录吗?"))
  {
	var i = 0;
  	var showStr="正在删除数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
  	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  	
  	//showSubmitFrame(mDebug);
  	fm.fmtransact.value = "DELETE||DOCID";

  	document.getElementById("fm").submit(); //提交
  	//initForm();
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




/*********************************************************************
 *  查询返回明细信息时执行的函数,查询返回一个2维数组，数组下标从[0][0]开始
 *  参数  ：  查询返回的二维数组
 *  返回值：  无
 *********************************************************************
 */

function afterQuery( arrQueryResult )
{
	var arrResult = new Array();

	//alert("afterQuery");
	// 书写SQL语句
	/*var strSQL = " select docid,doccode,numpages,makedate,maketime,scanoperator,"
					+ "managecom,inputstate,operator,inputstartdate,inputstarttime "
					+ ",docflag,docremark,'',inputenddate,inputendtime,busstype,scanno,subtype " 
					+ " from ES_DOC_MAIN where 1=1 " 
					+ " "+ " and DOCID = " + arrQueryResult[0][0] + " ";	*/		 
	mySql = new SqlClass();
	mySql.setResourceName("easyscan.EsDocManageSql");
	mySql.setSqlId("EsDocManageSql7");
	mySql.addSubPara(arrQueryResult[0][0]);  
	turnPage.strQueryResult  = easyQueryVer3(mySql.getString(), 1, 1, 1);  
  
	//判断是否查询成功
	if (!turnPage.strQueryResult) {
		alert("查询失败！");
		return false;
    }
	//查询成功则拆分字符串，返回二维数组
	arrResult = decodeEasyQueryResult(turnPage.strQueryResult);
	
	if( arrResult != null )
	{
		document.all( 'DOC_ID' ).value = 			arrResult[0][0];
		document.all( 'DOC_CODE' ).value = 		arrResult[0][1];
		document.all('NUM_PAGES').value = 		arrResult[0][2];
		document.all('INPUT_DATE').value = 		arrResult[0][3];
		//document.all('Input_Time').value = 		arrResult[0][4];
		document.all('ScanOperator').value = 		arrResult[0][5];  
		document.all('ManageCom').value = 		arrResult[0][6];
		document.all('InputState').value = 		arrResult[0][7];
		document.all('Operator').value = 			arrResult[0][8];
		document.all('InputStartDate').value = 	arrResult[0][9];
		document.all('InputStartTime').value = 	arrResult[0][10];
		document.all('DOC_FLAGE').value = 		arrResult[0][11];
		document.all('DOC_REMARK').value = 		arrResult[0][12];
		//document.all('Doc_Ex_Flag').value = 		arrResult[0][13];
		document.all('InputEndDate').value = 		arrResult[0][14];
		document.all('InputEndTime').value = 		arrResult[0][15];
		document.all('BussType').value = 		arrResult[0][16];
		document.all('ScanNo').value = 		arrResult[0][17];
		document.all('SubType').value = 		arrResult[0][18];
		if(fm.SubType.value!="")
			showOneCodeName('imagetype','SubType','SubTypeName');
		else
			fm.SubTypeName.value="";
		
		queryPages(arrResult[0][0]);
	}       
	else
		alert("arrQueryResult is null!");
}               
        
        
// 查询按钮
function queryPages(strDoc_Code)
{
	// 书写SQL语句
	/*var strSQL = "select pageid,docid,pagecode,hostname,pagename,pageflag,"
					+ "picpathftp,picpath,operator,makedate,maketime "
					+ ",(select subtype from es_doc_main where docid=ES_DOC_PAGES.docid),scanno"
					+ " from ES_DOC_PAGES where 1=1 " 
					+ " "+ " and DOCID in ( select docid from es_doc_relation where bussno='" + strDoc_Code + "' and bussnotype='11') "
					+ " order by docid,pageid";		*/	 
	//prompt('',strSQL);
	mySql = new SqlClass();
	mySql.setResourceName("easyscan.EsDocManageSql");
	mySql.setSqlId("EsDocManageSql8");
	mySql.addSubPara(strDoc_Code); 
	turnPage.queryModal(mySql.getString(), CodeGrid,0,0,100);    
	
	/*strSQL = "select count(*) "
					+ " from ES_DOC_PAGES where 1=1 " 
					+ " "+ " and DOCID in ( select docid from es_doc_relation where bussno='" + strDoc_Code + "' and bussnotype='11') "
					; 	*/
	mySql = new SqlClass();
	mySql.setResourceName("easyscan.EsDocManageSql");
	mySql.setSqlId("EsDocManageSql9");
	mySql.addSubPara(strDoc_Code); 				
	var brr = easyExecSql(mySql.getString());
	//prompt('',strSQL);
	if ( brr )  
	{
		document.all('NUM_PAGES').value = brr[0][0];
	}  		
}



//Click事件 点击'保存修改'按钮时触发，实现保存MultiLine当前修改的数据 非通用
function saveUpdate()
{
	if(!checkOnlyOne(0))
		return false;
  if (confirm("您确实想保存修改吗?"))
  {
	var i = 0;
  	var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  	
  	fm.fmtransact.value = "UPDATE||PAGES";
  	
  	//选中所有行
  	//CodeGrid.checkBoxAll();

  	document.getElementById("fm").submit(); //提交
  	//initForm();
  }
}        
//Click事件 点击'删除选中'按钮时触发，实现删除选中的MultiLine行数据 非通用
function deleteChecked()
{
	if(!checkOnlyOne(3))
		return false;
  if (confirm("您确实想删除选中的单证页数据吗?"))
  {
	var i = 0;
  	var showStr="正在删除数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  	
  	fm.fmtransact.value = "DELETE||PAGES";

  	document.getElementById("fm").submit(); //提交
  	//重新查询
  	//queryPages(document.all( 'DOC_ID' ).value);
  	//initForm();
  }
}

function checkOnlyOne(flag1)
{
	//提示没有选择记录
	//部分提示只能选择一条记录
	var count = CodeGrid.mulLineCount;
	var flag = 0;
	var str = '';
	var flaga = 0;
	var str1 = '';
	var str2 = '';
	for(var index=0; index< count; index++)
	{
	   if(CodeGrid.getChkNo(index)==true)
	   {
	   	flag =flag+1;
	   	str = CodeGrid.getRowColData(index,12);	
	   	str2 = CodeGrid.getRowColData(index,2);
	   	if(str1 != '' && str1 != str2)//校验是否有不一致的单证内部号
	   		flaga = 1;//有不一致单证内部号
	   	str1 = CodeGrid.getRowColData(index,2);	   	
	   }
	}
	//alert(flag);
  	if(flag ==0)
  	{
  		alert("请至少选择一条记录进行该操作！");
  		return false;
  	}
  	if((flag1 ==1||flag1 ==2) && flag >1)
  	{
  		alert("只能选择一条记录进行该操作！");
  		return false;
  	}
  	if(flag1 ==2 && str =='UA001')
  	{
  		//校验如果该印刷号有投保单类，则不能把别的修改为投保单类型
		//var strSQL = " select 1 from ES_DOC_MAIN where subtype='UA001' and doccode='"+fm.PrtNo.value+"'"; 	
		mySql = new SqlClass();
	mySql.setResourceName("easyscan.EsDocManageSql");
	mySql.setSqlId("EsDocManageSql10");
	mySql.addSubPara(fm.PrtNo.value); 
		var brr = easyExecSql(mySql.getString());
		//prompt('',strSQL);
	    if ( brr )  
	    {
	    	alert("该印刷号下已有投保单类扫描件！");
	    	return false;
	    }
  	}
  	/*if(flag1 ==3 && flaga ==1)
  	{
  		alert("只能选择一个单证（相同的单证内部号）进行操作！");
	    return false;
  	}*/
  	if(flag1 ==3)
  	{
  		//校验如果该印刷号有投保单类，则不能把别的修改为投保单类型
		//var strSQL = " select nvl(count(*),0) from ES_DOC_PAGES where docid in (select docid from ES_DOC_MAIN where doccode='"+fm.PrtNo.value+"')"; 	
		mySql = new SqlClass();
	mySql.setResourceName("easyscan.EsDocManageSql");
	mySql.setSqlId("EsDocManageSql11");
	mySql.addSubPara(fm.PrtNo.value); 
		var brr = easyExecSql(mySql.getString());
		//prompt('',strSQL);
	    if ( brr && (flag - brr[0][0] == 0))  
	    {	    	
	    	alert("如需删除该印刷号下所有单证页信息，请使用“删 除”按钮！");
	    	return false;
	    }
  	}
  	
  	return true;
}
        