//               该文件中包含客户端需要处理的函数和事件

var mDebug="0";
var mOperate="";
var showInfo;
var tResourceName="wagecal.LABranchGroupInputSql";
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
  if (!beforeSubmit())
    return false;
  if(mOperate=="INSERT||MAIN")
  {
  	if(!CheckBranchValid())
  		return false;
  }
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

  fm.hideOperate.value=mOperate;
  if (fm.hideOperate.value=="")
  {
    alert("操作控制数据丢失！");
  }
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
	var iHeight=350;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  }
  else
  { 
    //alert(content);
    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;  
  	//parent.fraInterface.initForm();
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=350;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

    showDiv(operateButton,"true"); 
    showDiv(inputButton,"false"); 
    //执行下一步操作
  }
}



//重置按钮对应操作,Form的初始化函数在功能名+Init.jsp文件中实现，函数的名称为initForm()
function resetForm()
{
  try
  {
    //showDiv(operateButton,"true"); 
    //showDiv(inputButton,"false"); 
	  initForm();
  }
  catch(re)
  {
  	alert("在LABranchGroup.js-->resetForm函数中发生异常:初始化界面错误!");
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
 if (!verifyInput()) return false;
  //alert(mOperate);
  if (trim(mOperate) == 'INSERT||MAIN')
  {
    //校验显式展业机构代码  
    var strSQL = "";
    /*strSQL = "select AgentGroup from LABranchGroup where 1=1 and (state<>'1' or state is null)"
	    + getWherePart('BranchAttr')
	    + getWherePart('BranchType')
	    +" Union "
	    +"Select AgentGroup From LABranchGroupB Where 1=1 and (state<>'1' or state is null)"
	    + getWherePart('BranchAttr')
	    + getWherePart('BranchType');*/
    strSQL = wrapSql(tResourceName,"querysqldes1",[document.all('BranchAttr').value,document.all('BranchType').value,document.all('BranchAttr').value,document.all('BranchType').value]);
    var strQueryResult  = easyQueryVer3(strSQL, 1, 1, 1);  
    //alert(strSQL);
    if (strQueryResult) {  	
  	alert('该展业机构代码已存在！');
  	document.all('BranchAttr').value = '';
  	return false;
    }
  }
  //tongmeng 2007-04-28 add
  //增加机构级别的校验
  var tBranchLevel = document.all('BranchLevel').value;
  if(tBranchLevel == '05')
  {
  	alert("机构级别违法!");
  	document.all('BranchLevel').value='';
  	return false;
  }
  return true;
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
  //下面增加相应的代码
  mOperate="INSERT||MAIN";
  showDiv(operateButton,"false"); 
  showDiv(inputButton,"true"); 
  if(document.all('EndFlag').value=='Y')
  {
   initForm();
  }
   document.all('BranchManager').value ='';
  document.all('BranchManagerName').value ='';
   document.all('AgentGroup').disabled = false;
   document.all('Name').disabled = false;                                             
		    document.all('ManageCom').disabled = false;                                              
			document.all('UpBranch').disabled = false;                     
        	document.all('BranchAttr').disabled = false; 
			document.all('BranchType').disabled = false;                                             
			document.all('BranchLevel').disabled = false;                                           
			document.all('BranchManager').disabled = false;                                          
			document.all('BranchAddress').disabled = false;                                              
			document.all('BranchPhone').disabled = false;                                             
			document.all('BranchFax').disabled = false;                                             
			document.all('BranchZipcode').disabled = false;                                            
			document.all('FoundDate').disabled = false;                                             
			document.all('EndDate').disabled = false;                                             
			document.all('EndFlag').disabled = false;                                              
			document.all('FieldFlag').disabled = false;                                             
			document.all('UpBranchAttr').disabled = false;                                               
			document.all('Operator').disabled = false;                                              
			document.all('BranchManagerName').disabled = false;
			
  
}           

//Click事件，当点击“修改”图片时触发该函数
function updateClick()
{
  //下面增加相应的代码
  
  if ((document.all("BranchAttr").value==null)||(trim(document.all("BranchAttr").value)==''))
    alert("请确定要修改的销售单位！");
  else
  { 
    if (!queryBranchCode())
      return false;
    if (confirm("您确实想修改该记录吗?"))
    {
      mOperate="UPDATE||MAIN";
      submitForm();
    }
    else
    {
      mOperate="";
      alert("您取消了修改操作！");
    }
  }
}           

//Click事件，当点击“查询”图片时触发该函数
function queryClick()
{
  //下面增加相应的代码
  mOperate="QUERY||MAIN";
  showInfo=window.open("./LABranchGroupQuery.html","","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
}           

//Click事件，当点击“删除”图片时触发该函数
function deleteClick()
{
  //下面增加相应的删除代码
  if ((document.all("BranchAttr").value==null)||(trim(document.all("BranchAttr").value)==''))
    alert("请确定要删除的销售单位！");
  else
  {
    if (!queryBranchCode())
      return false;
    if (!queryAgent())
      return false;
    if (confirm("您确实想删除该记录吗?"))
    {
      mOperate="DELETE||MAIN";  
      submitForm();
    }
    else
    {
      mOperate="";
      alert("您取消了删除操作！");
    }
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

function afterQuery(arrQueryResult)
{	
	var arrResult = new Array();
	//alert('11');
	if( arrQueryResult != null )
	{
		arrResult = arrQueryResult;

		document.all('AgentGroup').value = arrResult[0][0];  
		document.all('Name').value = arrResult[0][1];                                              
		document.all('ManageCom').value = arrResult[0][2];                                              
		document.all('UpBranch').value = arrResult[0][3];                         
        document.all('BranchAttr').value = arrResult[0][4];   
		document.all('BranchType').value = arrResult[0][5];                                             
		document.all('BranchLevel').value = arrResult[0][6];                                           
		document.all('BranchManager').value = arrResult[0][7];                                           
		document.all('BranchAddress').value = arrResult[0][9];                                               
		document.all('BranchPhone').value = arrResult[0][10];                                               
		document.all('BranchFax').value = arrResult[0][11];                                               
		document.all('BranchZipcode').value = arrResult[0][12];                                               
		document.all('FoundDate').value = arrResult[0][13];                                               
		document.all('EndDate').value = arrResult[0][14];                                               
		document.all('EndFlag').value = arrResult[0][15];                                               
		document.all('FieldFlag').value = arrResult[0][17];                                               
		document.all('UpBranchAttr').value = arrResult[0][25];                                                  
		document.all('Operator').value = arrResult[0][19];                                                 
		document.all('BranchManagerName').value = arrResult[0][24];
		
		//add by jiaqiangli 2007-11-26 
		//重新查询BranchManagerName
		//var tsql="select name from laagent where agentcode = '"+arrResult[0][7]+"' ";
		var tsql = wrapSql(tResourceName,"querysqldes2",[arrResult[0][7]]);
                var tQueryResult = easyQueryVer3(tsql, 1, 1, 1); 
                var tarr;
                if(tQueryResult) {
                	tarr = decodeEasyQueryResult(tQueryResult);
                	document.all('BranchManagerName').value = tarr[0][0];
                }   
		
		if(arrResult[0][15]=='Y')
		{
			document.all('AgentGroup').disabled = true;
		    document.all('Name').disabled = true;                                             
		    document.all('ManageCom').disabled = true;                                              
			document.all('UpBranch').disabled = true;                     
        	document.all('BranchAttr').disabled = true; 
			document.all('BranchType').disabled = true;                                             
			document.all('BranchLevel').disabled = true;                                           
			document.all('BranchManager').disabled = true;                                          
			document.all('BranchAddress').disabled = true;                                              
			document.all('BranchPhone').disabled = true;                                             
			document.all('BranchFax').disabled = true;                                             
			document.all('BranchZipcode').disabled = true;                                            
			document.all('FoundDate').disabled = true;                                             
			document.all('EndDate').disabled = true;                                             
			document.all('EndFlag').disabled = true;                                              
			document.all('FieldFlag').disabled = true;                                             
			document.all('UpBranchAttr').disabled = true;                                               
			document.all('Operator').disabled = true;                                              
			document.all('BranchManagerName').disabled = true;

			}                                                                                                                                                                                                                                              	
 	}   
}

function queryBranchCode()
{  
  // 书写SQL语句
  var strSQL = "";
  /*strSQL = "select AgentGroup,UpBranch from LABranchGroup where 1=1 and (EndFlag is null or EndFlag <> 'Y') and (state<>'1' or state is null)"
	  + getWherePart('BranchAttr')
	  + getWherePart('BranchType');*/
  strSQL = wrapSql(tResourceName,"querysqldes3",[document.all('BranchAttr').value,document.all('BranchType').value]);
	        
  var strQueryResult  = easyQueryVer3(strSQL, 1, 1, 1);  
	
  if (!strQueryResult) {
    alert("不存在所要操作的销售单位！");
    document.all("BranchAttr").value = '';
    document.all('AgentGroup').value = '';
    document.all('UpBranch').value = '';
    return false;
  }
  var arr = decodeEasyQueryResult(strQueryResult);
  document.all('AgentGroup').value = arr[0][0];
  document.all('UpBranch').value = arr[0][1];
  return true;
}

function queryAgent()
{
    var strSQL="";	
    /*strSQL="select AgentCode from LAAgent where 1=1 and (AgentState is null or AgentState < '03') "
            +getWherePart('AgentGroup','AgentGroup');*/
    strSQL = wrapSql(tResourceName,"querysqldes4",[document.all('AgentGroup').value]);
	        
    var strQueryResult  = easyQueryVer3(strSQL, 1, 1, 1);  
	
    if (strQueryResult) {
    	alert('该销售单位下还有业务员！');
    	return false;
    }
    strQueryResult = null;
    /*strSQL = "select AgentGroup from LABranchGroup where 1=1 and (EndFlag is null or EndFlag <> 'Y') and (state<>'1' or state is null)"
	  + getWherePart('UpBranch','AgentGroup')
	  + getWherePart('BranchType');*/
    strSQL = wrapSql(tResourceName,"querysqldes5",[document.all('AgentGroup').value,document.all('BranchType').value]);
	        
    strQueryResult  = easyQueryVer3(strSQL, 1, 1, 1);  
    if (strQueryResult) {
    	alert('该销售单位下还有下属机构！');
    	return false;
    }
    return true;
} 

function CheckBranchValid()
{
	if((document.all('BranchAttr').value==null)||(document.all('BranchAttr').value==""))
	{
		alert("机构代码不能为空！");
		return false;
	}
	/*var sql="select AgentGroup from LABranchGroup where 1 = 1 and (state<>'1' or state is null)" 
	        + getWherePart('BranchAttr','BranchAttr')
	        + getWherePart('BranchType') ;*/
	var sql = wrapSql(tResourceName,"querysqldes6",[document.all('BranchAttr').value,document.all('BranchType').value]);
	strQueryResult  = easyQueryVer3(sql, 1, 0, 1);  
    	if (strQueryResult) 
    	{
    		alert('该机构已经存在，只能更新或请先删除再录入！');
    		return false;
    	}
    return true;
}
/*
function changeManager()
{
    if (getWherePart('AgentCode','BranchManager')=='')
      return false;
      
    var strSQL="";	
    strSQL="select AgentCode from LAAgent where 1=1 and (AgentState is null or AgentState < '03') "
            +getWherePart('AgentCode','BranchManager');	 
    //alert(strSQL);
    var strQueryResult  = easyQueryVer3(strSQL, 1, 1, 1);  
	
    if (!strQueryResult) {
    	alert('所输代码无效！');
    	document.all('BranchManager').value='';
    	return false;
    }
}*/
