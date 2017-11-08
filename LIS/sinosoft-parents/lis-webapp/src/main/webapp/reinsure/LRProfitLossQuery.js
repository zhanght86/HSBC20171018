//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="0";
var turnPage = new turnPageClass();          //使用翻页功能，必须建立为全局变量

//提交，保存按钮对应操作
function submitForm()
{
	/**
	var strSQL = "select a.SerialNo,a.RIContNo,(select RIContName from RIBarGainInfo where RIContNo=a.RIContNo),"
		+ " a.ReComCode,(select CompanyName from RIComInfo where companyno=a.Recomcode) x,"
		+ " a.CalYear,a.ProLosAmnt from RIProLossResult a where 1=1 "
		+ getWherePart("a.SerialNo","SerialNo")
		+ getWherePart("a.ReComCode","ReComCode")
		+ getWherePart("a.CalYear","CalYear")
		;
	 strSQL = strSQL +" order by a.SerialNo desc ";
	 */
	 var mySql100=new SqlClass();
		 mySql100.setResourceName("reinsure.LRProfitLossQuerySql"); //指定使用的properties文件名
		 mySql100.setSqlId("LRProfitLossQuerySql100");//指定使用的Sql的id
		/**
		 mySql100.addSubPara(getWherePart("a.SerialNo","SerialNo"));//指定传入的参数
		 mySql100.addSubPara(getWherePart("a.ReComCode","ReComCode"));//指定传入的参数
		 mySql100.addSubPara(getWherePart("a.CalYear","CalYear"));//指定传入的参数
*/

		 mySql100.addSubPara(fm.SerialNo.value);//指定传入的参数
		 mySql100.addSubPara(fm.ReComCode.value);//指定传入的参数
		 mySql100.addSubPara(fm.CalYear.value);//指定传入的参数
		
	 var strSQL=mySql100.getString();
	 
	 var arrResult = new Array();
	//arrResult = easyExecSql(strSQL);
	turnPage.queryModal(strSQL,ProfitLossGrid)
  
}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
  //showInfo.close();
  if (FlagStr == "Fail" ){
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(content));
    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  }
  else{
  }
}

//重置按钮对应操作,Form的初始化函数在功能名+Init.jsp文件中实现，函数的名称为initForm()
function resetForm(){
  try{
	  initForm();
  }
  catch(re){
  	myAlert(""+"在Proposal.js"+"-->"+"resetForm函数中发生异常:初始化界面错误!"+"");
  }
}

//取消按钮对应操作
function cancelForm(){
    showDiv(operateButton,"true");
    showDiv(inputButton,"false");
}

//提交前的校验、计算
function beforeSubmit(){
  //添加操作
}

//Click事件，当点击增加图片时触发该函数
function addClick(){
  //下面增加相应的代码
  showDiv(operateButton,"false");
  showDiv(inputButton,"true");
}

//Click事件，当点击“修改”图片时触发该函数
function updateClick(){
  //下面增加相应的代码
  myAlert("update click");
}

//Click事件，当点击“查询”图片时触发该函数
function queryClick(){
  //下面增加相应的代码
	myAlert("query click");
	  //查询命令单独弹出一个模态对话框，并提交，和其它命令是不同的
  //因此，表单中的活动名称也可以不用赋值的
}

//Click事件，当点击“删除”图片时触发该函数
function deleteClick(){
  //下面增加相应的代码
  myAlert("delete click");
}

//显示div，第一个参数为一个div的引用，第二个参数为是否显示，如果为"true"则显示，否则不显示
function showDiv(cDiv,cShow)
{
  if (cShow=="true"){
    cDiv.style.display="";
  }
  else{
    cDiv.style.display="none";
  }
}

//显示frmSubmit框架，用来调试
function showSubmitFrame(cDebug){
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
		var tRow=ProfitLossGrid.getSelNo();
  	if (tRow==0){
   		myAlert(""+"请您先进行选择!"+"");
  		return;
  	}
  	
  	/**
  	var strSQL = "select a.SerialNo,a.ReComCode,(select CompanyName from RIComInfo where companyno=a.Recomcode),RIContno,(select RIContName from RIBarGainInfo where RIContNo=a.RIContNo),a.CalYear,ProLosAmnt "
			+" from RIProLossResult a where a.ReComCode='"+ProfitLossGrid.getRowColData(tRow-1,4)+"' and a.CalYear='"+ProfitLossGrid.getRowColData(tRow-1,6)+"'"
  		;
  	*/
  	var mySql101=new SqlClass();
		 mySql101.setResourceName("reinsure.LRProfitLossQuerySql"); //指定使用的properties文件名
		 mySql101.setSqlId("LRProfitLossQuerySql101");//指定使用的Sql的id
		 mySql101.addSubPara(ProfitLossGrid.getRowColData(tRow-1,4));//指定传入的参数
		 mySql101.addSubPara(ProfitLossGrid.getRowColData(tRow-1,6));//指定传入的参数
	 var strSQL=mySql101.getString();
  	
  	
  	
  	strArray = easyExecSql(strSQL);
  	if (strArray==null){
  		myAlert(""+"无法返回"+","+"该数据可能刚被删除!"+"");
  		return false;
  	}
  	var SerialNo=strArray[0][0];
    top.opener.fm.all('SerialNo').value 	=strArray[0][0];
    top.opener.fm.all('RIcomCode').value 	=strArray[0][1];
    top.opener.fm.all('RIcomName').value 	=strArray[0][2];
    top.opener.fm.all('ContNo').value 		=strArray[0][3];
    top.opener.fm.all('ContName').value 	=strArray[0][4];
    top.opener.fm.all('CalYear').value 		=strArray[0][5];
    top.opener.fm.all('CalResult').value	=strArray[0][6];
    
    /**
    strSQL = "select a.FactorCode,a.FactorName,(select InputType from RIProLossRela " 
    + " where ReComCode=a.ReComCode and FactorCode=a.FactorCode ), a.FactorValue "
    + " from riprolossvalue a where serialNo='"+SerialNo+"'"; 
		*/
	var  mySql102=new SqlClass();
		 mySql102.setResourceName("reinsure.LRProfitLossQuerySql"); //指定使用的properties文件名
		 mySql102.setSqlId("LRProfitLossQuerySql102");//指定使用的Sql的id
		 mySql102.addSubPara(SerialNo);//指定传入的参数
	     strSQL=mySql102.getString();
	 	
		strArray = easyExecSql(strSQL);
		top.opener.IncomeGrid.clearData();
		top.opener.PayoutGrid.clearData();
		for (var k=0;k<strArray.length;k++){
			top.opener.IncomeGrid.addOne("IncomeGrid");
			top.opener.IncomeGrid.setRowColData(k,1,strArray[k][0]);
			top.opener.IncomeGrid.setRowColData(k,2,strArray[k][1]);
			top.opener.IncomeGrid.setRowColData(k,3,strArray[k][2]);
			top.opener.IncomeGrid.setRowColData(k,4,strArray[k][3]);
			top.opener.IncomeGrid.setRowColData(k,5,strArray[k][3]);
		}
		
		//strSQL = "select a.FactorCode,a.FactorName,(select InputType from RIProLossRela where ReComCode=a.ReComCode and FactorCode=a.FactorCode ), "
		//	+" a.FactorValue from riprolossvalue a where inouttype='02' and serialNo='"+SerialNo+"'";
		//strArray = easyExecSql(strSQL);
		//for (var k=0;k<strArray.length;k++){
		//	top.opener.PayoutGrid.addOne("PayoutGrid");
		//	top.opener.PayoutGrid.setRowColData(k,1,strArray[k][0]);
		//	top.opener.PayoutGrid.setRowColData(k,2,strArray[k][1]);
		//	top.opener.PayoutGrid.setRowColData(k,3,strArray[k][2]);
		//	top.opener.PayoutGrid.setRowColData(k,4,strArray[k][3]);
		//	if(strArray[k][2]=="01"){ //系统计算的值
		//		top.opener.PayoutGrid.setRowColData(k,5,strArray[k][3]);
		//	}
		//}
    top.close();
}

function ClosePage()
{
	top.close();
}
