
var showInfo;
var mDebug="0";
var flag;
var k = 0;
var turnPage = new turnPageClass();

//提交，保存按钮对应操作
function submitForm()
{
	
  if(fm.Content.value=="")
   {
  	alert("请输入特约信息！");
  	return;
   }
  //var i = 0;
  //var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  //var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
  fm.Action.value ="INSERT";
  fm.submit(); //提交
}
//删除某些特约
function deleteInfo()
{
 var tSel = SpecInfoGrid.getSelNo()-1;
  if(tSel<0)
   {
  	alert("请选择一条特约进行删除！");
  	return;
   }
  //var i = 0;
  //var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  //var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
  fm.Action.value ="DELETE";
  fm.submit(); //提交
}


//提交后操作,服务器数据返回后执行的操作
function afterSubmit(tFlag,tContent)
{
    alert(tFlag+tContent);
    querySpec();
}
function getSpecContent(){
   var tSel = SpecInfoGrid.getSelNo()-1;
   fm.Content.value = SpecInfoGrid.getRowColData(tSel,2);
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
         
function querySpec(){
   var tSQL ="select grpcontno,speccontent,operator,makedate,serialno,proposalgrpcontno from lccgrpspec where grpcontno ='"+GrpContno+"'";
     turnPage.queryModal(tSQL,SpecInfoGrid);
     fm.Content.value = "";
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


function afterSubmitB( FlagStr, content )
{
	if(FlagStr=="Fail")
	{
		fm.all("info").value="保存失败！"
	}else{
	fm.all("info").value="保存成功！"
}
	
}

