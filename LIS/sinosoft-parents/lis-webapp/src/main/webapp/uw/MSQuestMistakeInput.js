var showInfo;
var turnPage = new turnPageClass();
//var Str="1 and codealias= #" + fm.ErrManageCom.value + "#";

function initQuery()
{

	var strSql;
	//个单
//		strSql = " SELECT P.ErrManagecom,P.ErrorType, P.ErrContent, P.MakeDate, P.Operator, P.ManageCom, P.Serialno" 
//				 + " FROM LCIssueMistake P " 
//				 + "WHERE p.Proposalcontno = '"+tPrtNo+"' "
//				 + "ORDER BY P.MAKEDATE, P.MAKETIME ";
		
		    var  sqlid1="MSQuestMistakeInputSql0";
			var  mySql1=new SqlClass();
			mySql1.setResourceName("uw.MSQuestMistakeInputSql"); //指定使用的properties文件名
			mySql1.setSqlId(sqlid1);//指定使用的Sql的id
			mySql1.addSubPara(tPrtNo);//指定传入的参数
			strSql=mySql1.getString();
	//alert(111);
	turnPage.queryModal(strSql, QuestGrid); 	
}

function addNote()
{

	if (trim(fm.ErrManageCom.value) == "") 
	{
		alert("必须填写所属岗位！");
		return;
	}
	if (trim(fm.ErrorType.value) == "") 
	{
		alert("必须填写差错类型！");
		return;
	}
	//var tSerialNo=QuestGrid.getRowColData(selno, 7);
	
	var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;    
	//showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    lockScreen('lkscreen');  
	fm.maction.value =  "INSERT";
	
	document.getElementById("fm").submit(); //提交	
	
}

function UpdateMistake(){
	var selno = QuestGrid.getSelNo()-1;
	if (selno <0)
	{
		  alert("请先选择一条记录！");
	      return false;
	}	
	if (trim(fm.ErrManageCom.value) == "") 
	{
		alert("必须填写所属岗位！");
		return;
	}
	if (trim(fm.ErrorType.value) == "") 
	{
		alert("必须填写差错类型！");
		return;
	}
	//var tSerialNo=QuestGrid.getRowColData(selno, 7);
	
	var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;    
	//showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    lockScreen('lkscreen');  
	fm.maction.value =  "UPDATE";
	
	document.getElementById("fm").submit(); //提交	
}

function DeleteMistake(){
	var selno = QuestGrid.getSelNo()-1;
	if (selno <0)
	{
		  alert("请先选择一条记录！");
	      return false;
	}	
	if (trim(fm.ErrManageCom.value) == "") 
	{
		alert("必须填写所属岗位！");
		return;
	}
	if (trim(fm.ErrorType.value) == "") 
	{
		alert("必须填写差错类型！");
		return;
	}
	//var tSerialNo=QuestGrid.getRowColData(selno, 7);
	
	var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;    
	//showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    lockScreen('lkscreen');  
	fm.maction.value =  "DELETE";
	
	document.getElementById("fm").submit(); //提交	
}

function afterSubmit( FlagStr, content )
{
	unlockScreen('lkscreen');  
	//alert(0);
	showInfo.close();
            
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
	//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	if(FlagStr == "Succ")
	{
		initQuery();
		top.opener.checkNotePad(fm.PrtNo.value);
	}
	fm.Content.value="";
}

function showContent()
{
	var selno = QuestGrid.getSelNo()-1;
	if (selno <0)
	{
	      return;
	}	
	var Content = QuestGrid.getRowColData(selno, 3);
	fm.Content.value = Content;
	fm.ErrManageCom.value = QuestGrid.getRowColData(selno, 1);
	fm.ErrorType.value = QuestGrid.getRowColData(selno, 2);
	fm.ErrManageCom.value = QuestGrid.getRowColData(selno, 1);
	fm.SerialNo.value = QuestGrid.getRowColData(selno, 7);
}

function afterCodeSelect( cCodeName, Field ){
	if(cCodeName=="ErrManageCom"){
		ErrManageCom = Field.value;//alert("ErrManageCom=="+ErrManageCom);
	}
}