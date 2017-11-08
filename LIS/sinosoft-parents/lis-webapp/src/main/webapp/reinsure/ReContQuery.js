//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="0";
var turnPage = new turnPageClass();          //使用翻页功能，必须建立为全局变量

//提交，保存按钮对应操作
function submitForm()
{
	var strSQL = "";
	if(fm.PageFlag.value=="CONT")
	{
	/**
	  strSQL = "select ricontno,conttype,ricontname,"
	  +" risktype,decode(conttype,'01','正常合同','02','临分合同','03','团体临分',''),"
	  +" reinsurancetype,decode(reinsurancetype,'01','比例分保','02','非比例分保',''),cvalidate,enddate, "
	  +" State,decode(State,'0','未生效','1','有效',''),gitype,decode(gitype,'1','月度','3','季度','12','年度') from RIBarGainInfo where 1=1 "
	  + getWherePart("RIContNo","RIContNo")
	  ;
	  strSQL = strSQL +" order by RIContNo";
	  */
	  var mySql100=new SqlClass();
	 	  mySql100.setResourceName("reinsure.ReContQuerySql"); //指定使用的properties文件名
	  	  mySql100.setSqlId("ReContQuerySql100");//指定使用的Sql的id
	 	  //mySql100.addSubPara(getWherePart("RIContNo","RIContNo"));//指定传入的参数
	  	  mySql100.addSubPara(fm.RIContNo.value);//指定传入的参数
	  	  strSQL=mySql100.getString();
	  
	}else
	{
	/**
		strSQL = "select ricontno,conttype,decode(conttype,'1','正常合同','2','临分合同','3','团体临分',''),"
	  +" risktype,decode(conttype,'1','正常合同','2','临分合同','03','团体临分',''),"
	  +" reinsurancetype,decode(reinsurancetype,'01','比例分保','02','非比例分保',''),cvalidate,enddate,"
	  +" State,decode(State,'0','未生效','1','有效',''),gitype,decode(gitype,'1','月度','3','季度','12','年度') from RIBarGainInfo where 1=1 "
	  + getWherePart("RIContNo","RIContNo")
	  ;
	  strSQL = strSQL +" order by RIContNo";
	  */
	  var mySql101=new SqlClass();
	 	  mySql101.setResourceName("reinsure.ReContQuerySql"); //指定使用的properties文件名
	  	  mySql101.setSqlId("ReContQuerySql101");//指定使用的Sql的id
	 	  //mySql101.addSubPara(getWherePart("RIContNo","RIContNo"));//指定传入的参数
	  	  mySql101.addSubPara(fm.RIContNo.value);//指定传入的参数
	  	  strSQL=mySql101.getString();
	}
	
	var arrResult = new Array();
	//arrResult = easyExecSql(strSQL);
		
	turnPage.queryModal(strSQL, ReContGrid);
  
}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{

  //showInfo.close();
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

function ReturnData()
{
	if(fm.PageFlag.value=="CONT")
	{
		//先选择一行
		var tRow=ReContGrid.getSelNo();
  	if (tRow==0)
  	{
  		myAlert(""+"请您先进行选择!"+"");
  		return;
  	}
  	/**
  	var strSQL = "select RIContNo,RIContName,ContType,(case ContType when '01' then '正常合同' when '02' then '临分合同' when '03' then '团体临分' end), "
		+" ReInsuranceType,(case ReInsuranceType when '01' then '成数分保' when '02' then '溢额分保' when '03' then '成数溢额分保' end), "
		+" CValiDate,EndDate,RISignDate,state,decode(State,'0','未生效','1','有效',''),gitype,decode(gitype,'1','月度','3','季度','12','年度') "
		+" from RIBarGainInfo "
  	+ "where 1=1 and RIContNo='"+ReContGrid.getRowColData(tRow-1,1)+"'"
  	;
  	*/
  	var mySql102=new SqlClass();
	 	mySql102.setResourceName("reinsure.ReContQuerySql"); //指定使用的properties文件名
	  	mySql102.setSqlId("ReContQuerySql102");//指定使用的Sql的id
	 	mySql102.addSubPara(ReContGrid.getRowColData(tRow-1,1));//指定传入的参数
	var strSQL=mySql102.getString();
  	//执行SQL查询语句，让strArray 数组 存放查询结果
  	strArray=easyExecSql(strSQL);	
  	
  	
  	//如果查询结果为空，
  	if (strArray==null)
  	{
  		myAlert(""+"无法返回"+","+"该数据可能刚被删除!"+"");
  		return false;
  	}
  	
  	//如果查询结果不为空，则把查询的结果依次自动填充到主页面对应的对象中
  	top.opener.fm.all('RIContNo').value 					=strArray[0][0];
  	top.opener.fm.all('RIContName').value 				=strArray[0][1];
  	top.opener.fm.all('ContType').value 					=strArray[0][2];
  	top.opener.fm.all('ContTypeName').value	 			=strArray[0][3];
  	top.opener.fm.all('ReInsuranceType').value	 	=strArray[0][4];
  	top.opener.fm.all('ReInsuranceTypeName').value=strArray[0][5];
  	top.opener.fm.all('RValidate').value 					=strArray[0][6];
  	top.opener.fm.all('RInvalidate').value 				=strArray[0][7];
  	top.opener.fm.all('RSignDate').value					=strArray[0][8];
  	top.opener.fm.all('ContState').value					=strArray[0][9];    
  	top.opener.fm.all('ContStateName').value			=strArray[0][10];
  	top.opener.fm.all('BillingCycle').value			=strArray[0][11];
  	top.opener.fm.all('BillingCycleName').value			=strArray[0][12];
  	
  	//该语句用来从联系人表RIBarGainSigner中查询相应的记录：
   	//strSQL="select (select ComPanyName from RIComInfo where ComPanyNo=a.recomcode),a.recomcode,a.RelaName,a.Duty,a.RelaTel,a.MobileTel,a.FaxNo,a.Email,a.relacode from RIBarGainSigner a where RIContNo='"+ReContGrid.getRowColData(tRow-1,1)+"'";
   	var mySql103=new SqlClass();
	 	mySql103.setResourceName("reinsure.ReContQuerySql"); //指定使用的properties文件名
	  	mySql103.setSqlId("ReContQuerySql103");//指定使用的Sql的id
	 	mySql103.addSubPara(ReContGrid.getRowColData(tRow-1,1));//指定传入的参数
		strSQL=mySql103.getString();
   	strArray=easyExecSql(strSQL);
   	top.opener.SignerGrid.clearData();
	 	if (strArray!=null)
	 	{
			for (var k=0;k<strArray.length;k++)
			{	  
  			top.opener.SignerGrid.addOne("SignerGrid");
  			
				top.opener.SignerGrid.setRowColData(k,1,strArray[k][0]); 
				top.opener.SignerGrid.setRowColData(k,2,strArray[k][1]); 
				top.opener.SignerGrid.setRowColData(k,3,strArray[k][2]); 
				top.opener.SignerGrid.setRowColData(k,4,strArray[k][3]);
				top.opener.SignerGrid.setRowColData(k,5,strArray[k][4]);
				top.opener.SignerGrid.setRowColData(k,6,strArray[k][5]);
				top.opener.SignerGrid.setRowColData(k,7,strArray[k][6]);
				top.opener.SignerGrid.setRowColData(k,8,strArray[k][7]);
				top.opener.SignerGrid.setRowColData(k,9,strArray[k][8]);
			}
		}
		
		//strSQL="select a.FactorName,a.FactorCode,a.FactorValue from RICalFactorValue a where a.ReContCode='"+ReContGrid.getRowColData(tRow-1,1)+"' and a.FactorClass='01' ";
		var mySql104=new SqlClass();
	 		mySql104.setResourceName("reinsure.ReContQuerySql"); //指定使用的properties文件名
	  		mySql104.setSqlId("ReContQuerySql104");//指定使用的Sql的id
	 		mySql104.addSubPara(ReContGrid.getRowColData(tRow-1,1));//指定传入的参数
			strSQL=mySql104.getString();
		strArray=easyExecSql(strSQL);
		top.opener.FactorGrid.clearData();
		if (strArray!=null)
		{
			for (var k=0;k<strArray.length;k++)
			{	
  			top.opener.FactorGrid.addOne("FactorGrid");
				top.opener.FactorGrid.setRowColData(k,1,strArray[k][0]);
				top.opener.FactorGrid.setRowColData(k,2,strArray[k][1]);
				top.opener.FactorGrid.setRowColData(k,3,strArray[k][2]);
			}
		}
  }
 	top.close(); 
}

function ClosePage()
{
	top.close();
}
