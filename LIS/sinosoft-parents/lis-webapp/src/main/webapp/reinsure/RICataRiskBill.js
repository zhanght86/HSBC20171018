//该文件中包含客户端需要处理的函数和事件

//程序名称：RICataRisk.js
//程序功能：巨灾报表
//创建日期：2011-6-29
//创建人  ：
//更新记录：  更新人    更新日期     更新原因/内容

var showInfo;
window.onfocus=myonfocus;
var turnPage = new turnPageClass();
var reg=/^[0-9]*\.?[0-9]*$/;
var sqlresourcename = "reinsure.RICataRiskInputSql";


//保  存按钮
function button134( )
{
	fm.OperateType.value = "INSERT";
	try {
		if(!reg.test(fm.RateFee.value)){
			 myAlert(""+"巨灾费率必须为数字类型！"+"");
			 return;
			}
			if(fm.MakeDate.value==""||fm.MakeDate.value==null){
			   myAlert(""+"维护日期不能为空！"+"");
			   return 
			}
			var showStr = ""+"正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面"+"";
var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(showStr));
			showInfo = window
					.showModelessDialog(urlStr, window,
							"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
			fm.action="./RICataRiskSave.jsp";
			fm.submit(); // 提交
	}catch (ex) {
		showInfo.close();
		myAlert(ex);
	}
}

//查  询按钮
function button135( )
{
	if(fm.MakeDateB.value!=""){
		
		
		var mySql = new SqlClass();
	mySql.setResourceName(sqlresourcename);
	mySql.setSqlId("RICataRiskInputSql1");//指定使用的Sql的id
	mySql.addSubPara(fm.MakeDateB.value);// 指定传入的参数，多个参数顺序添加
	var sql = mySql.getString();
		turnPage.queryModal(sql,Mul13Grid);
	}
	else{
		var mySql1 = new SqlClass();
	mySql1.setResourceName(sqlresourcename);
	mySql1.setSqlId("RICataRiskInputSql2");//指定使用的Sql的id
	mySql1.addSubPara("");
	var sql = mySql1.getString();
		
		turnPage.queryModal(sql,Mul13Grid);	
	}
}

//统计按钮
function button136( )
{
	try 
	{
		if (verifyInput() == true) {
			if (verifyInput2() == true) {
	     fm.target = "importCessData"; 
	     fm.action = "LPrtPrintCataRiskBillSave.jsp";
	     fm.submit();
			}
		}

  } 
  catch(ex) 
  {

  	myAlert(ex);
  }
}


//提交前的校验、计算  
function beforeSubmit()
{
  //添加操作 
}

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

//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
  showInfo.close();
  if (FlagStr == "Fail" )
  {             
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(content));
    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
  }
  else
  { 
var urlStr="../common/jsp/MessagePage.jsp?picture=S&content="+encodeURI(encodeURI(content));
    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
    //showDiv(operateButton,"true"); 
    //showDiv(inputButton,"false"); 
    //执行下一步操作
	var  sql="select r.RateFee,r.MakeDate,r.SerialNo from RIBIGRATEFEE r order by r.MakeDate DESC";
	turnPage.queryModal(sql,Mul13Grid);	
  }
}

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
function showCateRate(){
	var tSel = Mul13Grid.getSelNo();	
	fm.RateFee.value = Mul13Grid.getRowColData(tSel-1,1);
	fm.MakeDate.value = Mul13Grid.getRowColData(tSel-1,2);
	fm.SerialNo.value= Mul13Grid.getRowColData(tSel-1,3);
}
function deleteMess(){
	fm.OperateType.value = "DELETE";
	try {
			if(fm.MakeDate.value==""||fm.MakeDate.value==null){
			   myAlert(""+"维护日期不能为空！"+"");
			   return 
			}
			var showStr = ""+"正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面"+"";
var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(showStr));
			showInfo = window
					.showModelessDialog(urlStr, window,
							"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
			fm.action="./RICataRiskSave.jsp";
			fm.submit(); // 提交
	}catch (ex) {
		showInfo.close();
		myAlert(ex);
	}	
}
