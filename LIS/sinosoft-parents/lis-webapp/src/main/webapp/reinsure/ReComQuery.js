//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="0";
var turnPage = new turnPageClass();          //使用翻页功能，必须建立为全局变量

//提交，保存按钮对应操作
function submitForm()
{
/**
  var strSQL = "select ComPanyNo,ComPanyName,GrpAddress,GrpZipCode,Fax from RIComInfo where 1=1 "
  + getWherePart("ComPanyNo","ReComCode")
  + getWherePart("ComPanyName","ReComName","like")
  ;
  strSQL = strSQL +" order by ComPanyNo";
  */
  var mySql100=new SqlClass();
	  mySql100.setResourceName("reinsure.ReComQuerySql"); //指定使用的properties文件名
	  mySql100.setSqlId("ReComQuerySql100");//指定使用的Sql的id
	  /**
	  mySql100.addSubPara(getWherePart("ComPanyNo","ReComCode"));//指定传入的参数
	  mySql100.addSubPara(getWherePart("ComPanyName","ReComName","like"));//指定传入的参数
	  */
	  mySql100.addSubPara(fm.ReComCode.value);//指定传入的参数
	  mySql100.addSubPara(fm.ReComName.value);//指定传入的参数
  var strSQL=mySql100.getString();
  
  var arrResult = new Array();
	//arrResult = easyExecSql(strSQL);
	
	turnPage.queryModal(strSQL, ReComGrid)
  
}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
  if (FlagStr == "Fail" )
  {
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(content));
    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  }
  else
  {
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
  	myAlert(""+"在Proposal.js"+"-->"+"resetForm函数中发生异常:初始化界面错误!"+"");
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
  myAlert("update click");
}

//Click事件，当点击“查询”图片时触发该函数
function queryClick()
{
  //下面增加相应的代码
	myAlert("query click");
	  //查询命令单独弹出一个模态对话框，并提交，和其它命令是不同的
  //因此，表单中的活动名称也可以不用赋值的
}

//Click事件，当点击“删除”图片时触发该函数
function deleteClick()
{
  //下面增加相应的代码
  myAlert("delete click");
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
			parent.fraMain.rows = "0,0,0,0,*";
  }
 	else {
  		parent.fraMain.rows = "0,0,0,82,*";
 	}

	parent.fraMain.rows = "0,0,0,0,*";
}

function ReturnData(){
		var tRow=ReComGrid.getSelNo();
  	if (tRow==0){
   		myAlert(""+"请您先进行选择!"+"");
  		return;
  	}
  	//var strSQL = "select ComPanyNo,ComPanyName,GrpZipCode,GrpAddress,Fax,Remark from RIComInfo  where ComPanyNo='"+ReComGrid.getRowColData(tRow-1,1)+"'";
  	var mySql101=new SqlClass();
	  	mySql101.setResourceName("reinsure.ReComQuerySql"); //指定使用的properties文件名
	  	mySql101.setSqlId("ReComQuerySql101");//指定使用的Sql的id
	  	mySql101.addSubPara(ReComGrid.getRowColData(tRow-1,1));//指定传入的参数
  	var strSQL=mySql101.getString();
  	strArray = easyExecSql(strSQL);
  	
  	if (strArray==null){
  		myAlert(""+"无法返回"+","+"该数据可能刚被删除!"+"");
  		return false;
  	}
    
    //var ComType=strArray[0][2];
    top.opener.fm.all('ReComCode').value 	=strArray[0][0];
    top.opener.fm.all('ReComName').value 	=strArray[0][1];
    top.opener.fm.all('PostalCode').value =strArray[0][2];
    top.opener.fm.all('Address').value 		=strArray[0][3];
    top.opener.fm.all('FaxNo').value			=strArray[0][4];
    top.opener.fm.all('Note').value 			=strArray[0][5];
    /**
    strSQL = "select RelaName,Department,Duty,RelaTel,MobileTel,FaxNo,Email,RelaCode"
    +"  from RIComLinkManInfo"
	  + "  where  RecomCode='"+ReComGrid.getRowColData(tRow-1,1)+"'";
	*/
	var mySql102=new SqlClass();
	  	mySql102.setResourceName("reinsure.ReComQuerySql"); //指定使用的properties文件名
	  	mySql102.setSqlId("ReComQuerySql102");//指定使用的Sql的id
	  	mySql102.addSubPara(ReComGrid.getRowColData(tRow-1,1));//指定传入的参数
  		strSQL=mySql102.getString();	
		strArray = easyExecSql(strSQL);
		top.opener.RelateGrid.clearData();
		
		if (strArray!=null)
		{
			for (var k=0;k<strArray.length;k++)
			{ 
				
				top.opener.RelateGrid.addOne("RelateGrid");
				top.opener.RelateGrid.setRowColData(k,1,strArray[k][0]);
				top.opener.RelateGrid.setRowColData(k,2,strArray[k][1]);
				top.opener.RelateGrid.setRowColData(k,3,strArray[k][2]);
				top.opener.RelateGrid.setRowColData(k,4,strArray[k][3]);
				top.opener.RelateGrid.setRowColData(k,5,strArray[k][4]);
				top.opener.RelateGrid.setRowColData(k,6,strArray[k][5]);
				top.opener.RelateGrid.setRowColData(k,7,strArray[k][6]);
				top.opener.RelateGrid.setRowColData(k,8,strArray[k][7]);
			}
		}
   top.close();
  
}

function ClosePage()
{
	top.close();
}
