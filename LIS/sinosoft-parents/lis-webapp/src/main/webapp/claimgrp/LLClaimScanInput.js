//               该文件中包含客户端需要处理的函数和事件

var mDebug="0";
var mOperate="";
var showInfo;

//<addcode>############################################################//
var old_AgentGroup="";
var new_AgentGroup="";
//</addcode>############################################################//

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
	if (mOperate=="")
	{
		addClick();
	}
  if (!beforeSubmit())
   return false;
  var i = 0;
  var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
//  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
  var name='提示';   //网页名称，可为空; 
  var iWidth=550;      //弹出窗口的宽度; 
  var iHeight=250;     //弹出窗口的高度; 
  var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
  var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
  showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

  showInfo.focus();
	
  // fm.hideOperate.value=mOperate;
  //if (fm.hideOperate.value=="")
 // {
  //  alert("操作控制数据丢失！");
 // }
  fm.submit(); //提交
}


//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
  mOperate=""
  showInfo.close();

  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
//    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
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
    //parent.fraInterface.initForm();
//    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
    var name='提示';   //网页名称，可为空; 
    var iWidth=550;      //弹出窗口的宽度; 
    var iHeight=350;     //弹出窗口的高度; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

    showInfo.focus();

    initForm();
    //showDiv(operateButton,"true"); 
    //showDiv(inputButton,"false"); 
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
    alert("在LLClaimScanInput.js-->resetForm函数中发生异常:初始化界面错误!");
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
  if(document.all('BussNo').value==null || trim(document.all('BussNo').value)=="")
  {
    alert("请输入赔案号码！");
    return false;
  }

	if(document.all('SubType').value==null || trim(document.all('SubType').value)=="")
  {
    alert("请输入单证具体类型！");
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
  //showDiv(operateButton,"false"); 
  //showDiv(inputButton,"true"); 
}           

//Click事件，当点击“修改”图片时触发该函数
function updateClick()
{
  //下面增加相应的代码
  if ((document.all("AgentCode").value==null)||(trim(document.all("AgentCode").value)==''))
    alert('请先查询出要修改的纪录！');
  else if ((document.all("Idx").value==null)||(trim(document.all("Idx").value)==''))
    alert('请先查询出要修改的纪录！');
  else
  {
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
  //mOperate="QUERY||MAIN";
  showInfo=window.open("./LATrainQuery.html");
}           

//Click事件，当点击“删除”图片时触发该函数
function deleteClick()
{
  //下面增加相应的删除代码
  if ((document.all("AgentCode").value==null)||(document.all("AgentCode").value==''))
    alert('请先查询出要删除的纪录！');
  else if ((document.all("Idx").value==null)||(document.all("Idx").value==''))
    alert('请先查询出要删除的纪录！');
  else
  {
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


//验证业务员编码的合理性
function checkValid()
{ 

  var strSQL = "";
  var tBranchType = document.all('BranchType').value;
  if (getWherePart('AgentCode')!='')
  {
     strSQL = "select * from LAAgent where 1='1' "
	     + getWherePart('AgentCode')
	     +" and ((AgentState in ('01','02')) or (AgentState is null))"
	     +" and branchtype='"+tBranchType+"'";
     var strQueryResult = easyQueryVer3(strSQL, 1, 1, 1);
  }
  else
  {
    document.all('AgentCode').value = '';
    document.all('AgentGroup').value = "";
    document.all('ManageCom').value  = "";
    document.all('Name').value  = "";
    return false;
  }
  //判断是否查询成功
  if (!strQueryResult) {
    alert("无此业务员！");
    document.all('AgentCode').value  = "";
    document.all('AgentGroup').value = "";
    document.all('ManageCom').value  = "";
    document.all('Name').value  = "";
    return false;
  }
  //查询成功则拆分字符串，返回二维数组
  //var arrDataSet = decodeEasyQueryResult(strQueryResult);
  var tArr = new Array();
  tArr = decodeEasyQueryResult(strQueryResult);
  
  //<rem>######//
  //document.all('AgentGroup').value = tArr[0][1];
  //</rem>######//
  document.all('Name').value = tArr[0][5];
  document.all('ManageCom').value = tArr[0][2];
  //存储原AgentGroup值，以备保存时使用  
  //<addcode>############################################################//
  old_AgentGroup=tArr[0][74];
  
  document.all('HiddenAgentGroup').value=tArr[0][1];
  
  strSQL_AgentGroup = "select trim(BranchAttr) from labranchgroup where 1=1 "
                      +"and AgentGroup='"+old_AgentGroup+"'"
     var strQueryResult_AgentGroup = easyQueryVer3(strSQL_AgentGroup, 1, 1, 1);
  //var arrDataSet_AgentGroup = decodeEasyQueryResult(strQueryResult_AgentGroup);
  var tArr_AgentGroup = new Array();
  tArr_AgentGroup = decodeEasyQueryResult(strQueryResult_AgentGroup);
  //以备显示时使用
  document.all('AgentGroup').value = tArr_AgentGroup[0][0];
  new_AgentGroup=tArr_AgentGroup[0][0];
    //alert(new_AgentGroup);
  //</addcode>############################################################//
}


//用来显示返回的选项
function afterQuery(arrQueryResult)
{	
  var arrResult = new Array();
  if( arrQueryResult != null )
  {
    arrResult = arrQueryResult;
    document.all('AgentCode').value = arrResult[0][0];
    document.all('Name').value = arrResult[0][27];
    document.all('AgentGroup').value = arrResult[0][22]
    document.all('ManageCom').value = arrResult[0][2];
    document.all('Idx').value = arrResult[0][3];
    document.all('AClass').value = arrResult[0][4];
    document.all('TrainUnit').value = arrResult[0][5];
    document.all('TrainName').value = arrResult[0][6];
    document.all('Charger').value = arrResult[0][7];
    document.all('ResultLevel').value = arrResult[0][8];
    document.all('Result').value = arrResult[0][9];
    document.all('TrainPassFlag').value = arrResult[0][10];
    document.all('TrainStart').value = arrResult[0][11];
    document.all('TrainEnd').value = arrResult[0][12];
    document.all('Operator').value = arrResult[0][13];
    document.all('DoneDate').value = arrResult[0][14];
    document.all('DoneFlag').value = arrResult[0][15];
    document.all('Noti').value = arrResult[0][16];
    
    //<addcode>############################################################//
    document.all('HiddenAgentGroup').value=arrResult[0][1];
    //</addcode>############################################################//
    
    showOneCodeName('trainaclass','AClass','AClassName');
    showOneCodeName('ResultLevel','ResultLevel','ResultLevelName');
    showOneCodeName('yesno','TrainPassFlag','TrainPassFlagName');
                                                                                                                                                                                                                                      	
  }
    
}


