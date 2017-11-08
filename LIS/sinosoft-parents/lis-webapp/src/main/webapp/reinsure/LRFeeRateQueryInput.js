//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="0";
var turnPage = new turnPageClass();          //使用翻页功能，必须建立为全局变量

//提交，保存按钮对应操作
function submitForm(){
 var mySql100=new SqlClass();
	 mySql100.setResourceName("reinsure.LRFeeRateQueryInputSql"); //指定使用的properties文件名
	 mySql100.setSqlId("LRFeeRateQueryInputSql100");//指定使用的Sql的id
	 /**
	 mySql100.addSubPara(getWherePart("x.X1","FeeTableNo"));//指定传入的参数
	 mySql100.addSubPara(getWherePart("x.X2","FeeTableName","like"));//指定传入的参数
	 */
	 mySql100.addSubPara(fm.FeeTableNo.value);//指定传入的参数
	 mySql100.addSubPara(fm.FeeTableName.value);//指定传入的参数
var strSQL=mySql100.getString();
 
 /**
  var strSQL = "select x.X1,x.X2,x.X3,x.X4,decode(x.X5,'1','有未导入的费率表','已全部导入费率表'),decode(x.X6,'01','有效','02','未生效') from (select a.FeeTableNo X1,a.FeeTableName X2,a.FeeTableType X3,(case a.FeeTableType when '01' then '一般' when '02' then '特殊' end) X4 ,(select distinct 1 from RIFeeRateTableTrace c where not exists (select * from RIFeeRateTable b where c.feetableno=b.feetableno and c.batchno=b.batchno) and c.feetableno=a.feetableno) X5,a.state X6 from RIFeeRateTableDef a ) x where 1=1"
  + getWherePart("x.X1","FeeTableNo")
  + getWherePart("x.X2","FeeTableName","like")
  ;
  strSQL = strSQL +" order by x.X1";
*/
	turnPage.queryModal(strSQL, FeeRateGrid);
  
}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content ){
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
		var tRow=FeeRateGrid.getSelNo();
  	if (tRow==0){
   		myAlert(""+"请您先进行选择!"+"");
  		return;
  	}
  	var mySql101=new SqlClass();
		mySql101.setResourceName("reinsure.LRFeeRateQueryInputSql"); //指定使用的properties文件名
	 	mySql101.setSqlId("LRFeeRateQueryInputSql101");//指定使用的Sql的id
	 	mySql101.addSubPara(FeeRateGrid.getRowColData(tRow-1,1));//指定传入的参数
	var strSQL=mySql101.getString();
  /**	
  	var strSQL="select a.FeeTableNo,"
  	          +" a.FeeTableName,"
  	          +" a.FeeTableType,"
  	          +"(case a.FeeTableType when '01' then '一般' when '02' then '特殊' end),"
  	          +" a.Remark,"
  	          +" a.state,"
  	          +"(case a.state when '01' then '有效' when '02' then '未生效' end) "
  	          +" from RIFeeRateTableDef a where "
  	+ " a.FeeTableNo='"+FeeRateGrid.getRowColData(tRow-1,1)+"' "
  	;
  	*/
  	strArray = easyExecSql(strSQL);
  	if (strArray==null){
  		myAlert(""+"无法返回"+","+"该数据可能刚被删除!"+"");
  		return false;
  	}
  	var deTailFlag=strArray[0][2];
    top.opener.fm.all('FeeTableNo').value 			=strArray[0][0];
    top.opener.fm.all('FeeTableName').value 		=strArray[0][1];
    top.opener.fm.all('FeeTableType').value 		=strArray[0][2];
    top.opener.fm.all('FeeTableTypeName').value =strArray[0][3];
    top.opener.fm.all('ReMark').value 					=strArray[0][4];
    top.opener.fm.all('State').value 					  =strArray[0][5];
    top.opener.fm.all('stateName').value 			  =strArray[0][6];
		
	var mySql102=new SqlClass();
		mySql102.setResourceName("reinsure.LRFeeRateQueryInputSql"); //指定使用的properties文件名
	 	mySql102.setSqlId("LRFeeRateQueryInputSql102");//指定使用的Sql的id
	 	mySql102.addSubPara(FeeRateGrid.getRowColData(tRow-1,1));//指定传入的参数
	    strSQL=mySql102.getString();
	/**	
		strSQL = " select a.FeeClumnName,a.FeeClumnDataCode,(case a.FeeClumnType when 'A01' then '字符型固定值' when 'A02' then '字符型区间值' when 'N01' then '数值型固定值' when 'N02' then '数值型区间值' end),a.FeeClumnType,a.TagetClumn,a.TagetDLClumn,a.TagetULClumn "
		+ " from RIFeeRateTableClumnDef a where a.FeeTableNo='"+FeeRateGrid.getRowColData(tRow-1,1)+"' order by a.feeclumnno ";
		*/
		strArray = easyExecSql(strSQL);
		top.opener.TableClumnDefGrid.clearData();
		if (strArray!=null){
			for (var k=0;k<strArray.length;k++){
				top.opener.TableClumnDefGrid.addOne("TableClumnDefGrid");
				top.opener.TableClumnDefGrid.setRowColData(k,1,strArray[k][0]);
				top.opener.TableClumnDefGrid.setRowColData(k,2,strArray[k][1]);
				top.opener.TableClumnDefGrid.setRowColData(k,3,strArray[k][2]);
				top.opener.TableClumnDefGrid.setRowColData(k,4,strArray[k][3]);
				top.opener.TableClumnDefGrid.setRowColData(k,5,strArray[k][4]);
				top.opener.TableClumnDefGrid.setRowColData(k,7,strArray[k][5]);
				top.opener.TableClumnDefGrid.setRowColData(k,9,strArray[k][6]);
			}
		}
    top.close();
}

function ClosePage()
{
	top.close();
}
