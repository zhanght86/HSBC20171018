//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="0";
var arrDataSet;
var turnPage = new turnPageClass();

//将机构名称去空格
function trimname(){
  fm.AgentGroup.value = trim(document.all('AgentGroup').value);
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

  //showSubmitFrame(mDebug);
  fm.submit(); //提交
}


//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
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
    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

    //执行下一步操作
  }
}



//重置按钮对应操作,Form的初始化函数在功能名+Init.jsp文件中实现，函数的名称为initForm()
function resetForm()
{
  try
  {
	  initForm();
  }
  catch(re)
  {
  	alert("在AgentQuery.js-->resetForm函数中发生异常:初始化界面错误!");
  }
} 

//取消按钮对应操作
function cancelForm()
{
    showDiv(operateButton,"true"); 
    showDiv(inputButton,"false"); 
}
 
//提交前的校验、计算  
function beforeSubmit()
{
  //添加操作	
}           

//Click事件，当点击增加图片时触发该函数
function addClick()
{
  //下面增加相应的代码
  showDiv(operateButton,"false"); 
  showDiv(inputButton,"true"); 
}           

//Click事件，当点击“修改”图片时触发该函数
function updateClick()
{
  //下面增加相应的代码
  alert("update click");
}           

//Click事件，当点击“查询”图片时触发该函数
function queryClick()
{
  //下面增加相应的代码
  //window.showModalDialog("./ProposalQuery.jsp",window,"status:0;help:0;edge:sunken;dialogHide:0;dialogWidth=15cm;dialogHeight=12cm");
  //查询命令单独弹出一个模态对话框，并提交，和其它命令是不同的
  //因此，表单中的活动名称也可以不用赋值的
}           

//Click事件，当点击“删除”图片时触发该函数
function deleteClick()
{
  //下面增加相应的代码
  alert("delete click");
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
 	else {
  		parent.fraMain.rows = "0,0,0,82,*";
 	}
}

/*function returnParent()
{
  var tRow=AgentGrid.getSelNo();
   if(tRow>0)
   {
     //得到被选中的记录字符串
     tRow--;
     //alert(tRow);
     var str=document.all("AgentInformation"+tRow).value;
     var arrRecord = str.split("|");  //拆分字符串，形成返回的数组
     for(var i=0; i<63;i++)
     {
       if(arrRecord[i]=='null')
       {
         arrRecord[i]='';
       }
     }
     top.opener.document.all('AgentCode').value = arrRecord[0];
     top.opener.document.all('AgentGroup').value = arrRecord[1];
     top.opener.document.all('ManageCom').value = arrRecord[2];
     top.opener.document.all('Password').value = arrRecord[3];
     top.opener.document.all('EntryNo').value = arrRecord[4];
     top.opener.document.all('Name').value = arrRecord[5];
     top.opener.document.all('Sex').value = arrRecord[6];
     top.opener.document.all('Birthday').value = arrRecord[7];
     top.opener.document.all('NativePlace').value = arrRecord[8];
     top.opener.document.all('Nationality').value = arrRecord[9];
     top.opener.document.all('Marriage').value = arrRecord[10];
     top.opener.document.all('CreditGrade').value = arrRecord[11];
     top.opener.document.all('HomeAddressCode').value = arrRecord[12];
     top.opener.document.all('HomeAddress').value = arrRecord[13];
     top.opener.document.all('PostalAddress').value = arrRecord[14];
     top.opener.document.all('ZipCode').value = arrRecord[15];
     top.opener.document.all('Phone').value = arrRecord[16];
     top.opener.document.all('BP').value = arrRecord[17];
     top.opener.document.all('Mobile').value = arrRecord[18];
     top.opener.document.all('EMail').value = arrRecord[19];
     top.opener.document.all('MarriageDate').value = arrRecord[20];
     top.opener.document.all('IDNo').value = arrRecord[21];
     top.opener.document.all('Source').value = arrRecord[22];
     top.opener.document.all('BloodType').value = arrRecord[23];
     top.opener.document.all('PolityVisage').value = arrRecord[24];
     top.opener.document.all('Degree').value = arrRecord[25];
     top.opener.document.all('GraduateSchool').value = arrRecord[26];
     top.opener.document.all('Speciality').value = arrRecord[27];
     top.opener.document.all('PostTitle').value = arrRecord[28];
     top.opener.document.all('ForeignLevel').value = arrRecord[29];
     top.opener.document.all('WorkAge').value = arrRecord[30];
     top.opener.document.all('OldCom').value = arrRecord[31];
     top.opener.document.all('OldOccupation').value = arrRecord[32];
     top.opener.document.all('HeadShip').value = arrRecord[33];
     top.opener.document.all('RecommendAgent').value = arrRecord[34];
     top.opener.document.all('Business').value = arrRecord[35];
     top.opener.document.all('SaleQuaf').value = arrRecord[36];
     top.opener.document.all('QuafNo').value = arrRecord[37];
     top.opener.document.all('QuafStartDate').value = arrRecord[38];
     top.opener.document.all('QuafEndDate').value = arrRecord[39];
     top.opener.document.all('DevNo1').value = arrRecord[40];
     top.opener.document.all('DevNo2').value = arrRecord[41];
     top.opener.document.all('RetainContNo').value = arrRecord[42];
     top.opener.document.all('AgentKind').value = arrRecord[43];
     top.opener.document.all('DevGrade').value = arrRecord[44];
     top.opener.document.all('InsideFlag').value = arrRecord[45];
     top.opener.document.all('FullTimeFlag').value = arrRecord[46];
     top.opener.document.all('NoWorkFlag').value = arrRecord[47];
     top.opener.document.all('TrainDate').value = arrRecord[48];
     top.opener.document.all('EmployDate').value = arrRecord[49];
     top.opener.document.all('InDueFormDate').value = arrRecord[50];
     top.opener.document.all('OutWorkDate').value = arrRecord[51];
     top.opener.document.all('Approver').value = arrRecord[52];
     top.opener.document.all('ApproveDate').value = arrRecord[53];
     top.opener.document.all('AssuMoney').value = arrRecord[54];
     top.opener.document.all('AgentState').value = arrRecord[55];
     top.opener.document.all('QualiPassFlag').value = arrRecord[56];
     top.opener.document.all('SmokeFlag').value = arrRecord[57];
     top.opener.document.all('RgtAddress').value = arrRecord[58];
     top.opener.document.all('BankCode').value = arrRecord[59];
     top.opener.document.all('BankAccNo').value = arrRecord[60];
     top.opener.document.all('Remark').value = arrRecord[61];
     top.opener.document.all('Operator').value = arrRecord[62];
     //alert("Operateor:"+arrRecord[62]);
     
     top.close();
     top.opener.afterQuery();
     //top.location.href="./LAWarrantorQuery.jsp?AgentCode="+tAgentCode;
    }
   else
    alert("请选择记录!");
}*/

// 数据返回父窗口
function returnParent()
{
	var arrReturn = new Array();
	var tSel = AgentGrid.getSelNo();
	//alert(tSel);
	if( tSel == 0 || tSel == null )
		alert( "请先选择一条记录，再点击返回按钮。" );
	else
	{
		try
		{
			var tt=AgentGrid.getRowColData(tSel-1,1);
			//alert(tt);
			//return;
			
			//alert("queryFlag=="+queryFlag);
      
			  top.opener.fm.AgentCom.value=tt;
     
			top.close();
		}
		catch(ex)
		{
			alert( "要返回的页面函数出错！");
		}
		
	}
}

function getQueryResult()
{
	var arrSelected = null;
	tRow = AgentGrid.getSelNo();
	//alert("111" + tRow);
	if( tRow == 0 || tRow == null || arrDataSet == null )
	  return arrSelected;
	
	arrSelected = new Array();
	
	var strSQL = "";
	strSQL = strSQL + "select a.AgentCode,a.AgentGroup,a.ManageCom,a.Name,c.BranchManager,b.IntroAgency,b.AgentSeries,b.AgentGrade,c.BranchAttr,b.AscriptSeries,c.name from LAAgent a,LATree b,LABranchGroup c where 1=1 "
	         + "and a.AgentCode = b.AgentCode and a.AgentGroup = c.AgentGroup and a.AgentCode='"+AgentGrid.getRowColData(tRow-1,1)+"'"; 
//   	var strSQL = "select a.AgentCode,a.AgentGroup,a.AgentManageCom,a.AgentName,c.BranchManager,b.IntroAgency,b.AgentSeries,b.AgentGrade,c.BranchAttr,b.AscriptSeries,c.name from LAAgent a,LATree b,LABranchGroup c where 1=1 "
//	         + "and a.AgentCode = b.AgentCode and a.AgentGroup = c.AgentGroup and a.AgentCode='"+AgentGrid.getRowColData(tRow-1,1)+"'";
	//alert(strSQL); 
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
  //判断是否查询成功
  if (!turnPage.strQueryResult) {
    alert("查询失败！");
    return false;
    }
//查询成功则拆分字符串，返回二维数组
  arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
	
	return arrSelected;
}

// 查询按钮
function easyQueryClick()
{
	// 初始化表格
	//alert(branchtype);
	initAgentGrid();
	
	// 书写SQL语句
	var strSQL = "";
	var strOperate = "like";
	var strManagerCom = document.all('ManageCom').value;
	strSQL = "select agentcom,managecom,name from lacom  a where 1=1  "
	         //+ " and a.ManageCom like '" +ManageCom+"'"	                  
	         + getWherePart('agentcom','AgentCode',strOperate)
	        // + getWherePart('name','AgentGroup',strOperate)
	         + getWherePart('ManageCom','ManageCom',strOperate)
	   
	   
	//alert(fm.AgentGroup.value);
	//return;
	if(fm.AgentGroup.value!=null && fm.AgentGroup.value!="")
	{
		strSQL=strSQL+"and  a.name like '%%"+fm.AgentGroup.value+"%%'";
	}
	        
	if(branchtype!=null&&branchtype!="null")
	{
	  if(branchtype=="04"){
	    strSQL=strSQL+"   and  a.branchtype='04'";
	  }else if(branchtype=="06")
	  	{
	  		strSQL=strSQL+"  and  a.branchtype='06'";
	  	}
	  else if(branchtype=="07")
	  	{
	  		strSQL=strSQL+"  and  a.branchtype='07'";
	  	}else{
            strSQL = strSQL+"  and a.branchtype ='2'"	  	
	  	}
	  

	}	
     	
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 1, 1);  
  
  //判断是否查询成功
  if (!turnPage.strQueryResult) {
    alert("未查询到满足条件的数据！");
    return false;
    }
//查询成功则拆分字符串，返回二维数组
  arrDataSet = decodeEasyQueryResult(turnPage.strQueryResult);
  //tArr = decodeEasyQueryResult(turnPage.strQueryResult);
  turnPage.arrDataCacheSet = arrDataSet;
  //设置初始化过的MULTILINE对象，VarGrid为在初始化页中定义的全局变量
  turnPage.pageDisplayGrid = AgentGrid;    
          
  //保存SQL语句
  turnPage.strQuerySql     = strSQL; 
  
  //设置查询起始位置
  turnPage.pageIndex       = 0;  
  
  //在查询结果数组中取出符合页面显示大小设置的数组
  //arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  var tArr = new Array();
  tArr = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  //调用MULTILINE对象显示查询结果
  
  //displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  displayMultiline(tArr, turnPage.pageDisplayGrid);
}


// 投保单信息查询
function ProposalClick()
{
	var arrReturn = new Array();
	var tSel = AgentGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		alert( "请先选择一条记录，再点击返回按钮。" );
	else
	{
	    var cCustomerNo = AgentGrid.getRowColData(tSel - 1,1);				
		
		if (cCustomerNo == "")
		    return;		    
		  //alert(cCustomerNo);
		    var cName = AgentGrid.getRowColData(tSel - 1,4);
		    //alert(cName);
				window.open("../sys/ProposalQuery.jsp?CustomerNo=" + cCustomerNo + "&Name=" + cName + "&Flag=Agent");	
	}
}


// 保单信息查询
function PolClick()
{
	var arrReturn = new Array();
	var tSel = AgentGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		alert( "请先选择一条记录，再点击返回按钮。" );
	else
	{
	    var cCustomerNo = AgentGrid.getRowColData(tSel - 1,1);				
		
		if (cCustomerNo == "")
		    return;		    
		  //alert(cCustomerNo);
		    var cName = AgentGrid.getRowColData(tSel - 1,4);
		    //alert(cName);
		    window.open("../sys/PolQueryMain.jsp?CustomerNo=" + cCustomerNo + "&Name=" + cName + "&Flag=Agent");	
				//window.open("../sys/PolQuery.jsp?CustomerNo=" + cCustomerNo + "&Name=" + cName);	
	}
}


//销户保单信息查询
function DesPolClick()
{
	var arrReturn = new Array();
	var tSel = AgentGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		alert( "请先选择一条记录，再点击返回按钮。" );
	else
	{
	    var cCustomerNo = AgentGrid.getRowColData(tSel - 1,1);				
		
		if (cCustomerNo == "")
		    return;		    
		  //alert(cCustomerNo);
		    var cName = AgentGrid.getRowColData(tSel - 1,4);
		    //alert(cName);
		    window.open("../sys/DesPolQueryMain.jsp?CustomerNo=" + cCustomerNo + "&Name=" + cName + "&Flag=Agent");	
				//window.open("../sys/DesPolQuery.jsp?CustomerNo=" + cCustomerNo + "&Name=" + cName);	
	}
}
