 //               该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="0";

//存放添加动作执行的次数
var addAction = 0;
//暂交费总金额
var sumTempFee = 0.0;
//暂交费信息中交费金额累计
var tempFee = 0.0;
//暂交费分类信息中交费金额累计
var tempClassFee = 0.0;
//单击确定后，该变量置为真，单击添加一笔时，检验该值是否为真，为真继续，然后再将该变量置假
var confirmFlag=false;
//
var arrCardRisk;

var n=0;

//提交，保存按钮对应操作
function submitForm()
{
 if(addAction>0)//如果提交数据活动存在
  {
	  var i = 0;
	  var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
      var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();  
      document.getElementById("fmSave").submit();
  }
  else alert("条件不满足");
  document.all('distill').disabled=false;

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
    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=350;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    
  }
  
  clearFormData();
}


function showFinance(cashValue1,chequeValue1)
{
	
	showInfo.close();
	var urlStr="./FinanceInfo.jsp?cashValue="+cashValue1+"&chequeValue="+chequeValue1 ;
	//window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:1000px;dialogHeight:400px");  
	var name='提示';   //网页名称，可为空; 
	var iWidth=1000;      //弹出窗口的宽度; 
	var iHeight=400;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
}

//提交后操作,服务器数据返回后执行的操作
function afterQuery( FlagStr, content )
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
  	alert("在SinCardTempFeeInput.js-->resetForm函数中发生异常:初始化界面错误!");
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
  	    window.open("./TempFeeQuery.html");
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

function queryTempfee()
{
	window.open("../finfee/TempFeeQuery.html");
}




//验证字段的值
function confirm()
{
  //首先检验录入框
//   if (!verifyInput()) return false; 
    

   if((trim(document.all('ManageCom').value)).length!=8) 
   {
	  	alert("管理机构必须四级机构，即8位机构代码！");
	    return false;	
    }
   
   		//因录入单证类型后能自动带出代理人，因此先校验是否录入了单证类型
   		if(document.all('CertifyCode').value=="" || document.all('CertifyCode').value==null)
   		{
   			alert("请输入单证类型！");
   			return ;
   		}
   		
//   		var sql="SELECT agentstate FROM laagent where agentcode='"+trim(document.all('AgentCode').value)+"'";
   		
   		var sqlid1="SinCardTempFeeInputSql1";
   	 	var mySql1=new SqlClass();
   	 	mySql1.setResourceName("selflist.SinCardTempFeeInputSql");
   	 	mySql1.setSqlId(sqlid1); //指定使用SQL的id
   	 	mySql1.addSubPara(trim(document.all('AgentCode').value));//指定传入参数
   	 	var sql = mySql1.getString();
   		
	 	var state = decodeEasyQueryResult(easyQueryVer3(sql));
	 	if(state == null || state == "")
		{
			alert("该代理人不存在或状态未知!");
			return false;
		}
		if(parseInt(state[0][0])>=3)
		{
			alert("该代理人已经离职，不能录入新单暂交费!");
			return false;
		}
	//必须为个险渠道代理人
		//2010-4-30 放开收展渠道
		
//		var sql="SELECT BranchType FROM laagent where agentcode='"+trim(document.all('AgentCode').value)+"'";
		
		var sqlid2="SinCardTempFeeInputSql2";
   	 	var mySql2=new SqlClass();
   	 	mySql2.setResourceName("selflist.SinCardTempFeeInputSql");
   	 	mySql2.setSqlId(sqlid2); //指定使用SQL的id
   	 	mySql2.addSubPara(trim(document.all('AgentCode').value));//指定传入参数
   	 	var sql = mySql2.getString();
		
		var branchtype = decodeEasyQueryResult(easyQueryVer3(sql));
		if(branchtype==null){
			alert("查询代理人"+document.all('AgentCode').value+"信息失败！");
			return false;
		}
		if(branchtype[0][0] != "1" && branchtype[0][0] !="4")
		{
			alert("该代理人不是个险渠道代理人，请核实!");
			return false;
		}
		
    //2010-1-14 10:36 增加助理与专员的校验
   
//		var sql="select agentcode,upagentcode from laxagent where managecom='"+trim(document.all('PolicyCom').value)+"'";
		
		var sqlid3="SinCardTempFeeInputSql3";
   	 	var mySql3=new SqlClass();
   	 	mySql3.setResourceName("selflist.SinCardTempFeeInputSql");
   	 	mySql3.setSqlId(sqlid3); //指定使用SQL的id
   	 	mySql3.addSubPara(trim(document.all('PolicyCom').value));//指定传入参数
   	 	var sql = mySql3.getString();
		
    var aAgent= decodeEasyQueryResult(easyQueryVer3(sql));
    if(aAgent == null || aAgent == "")
		{
			alert("该代理人不存在助理或专员，请核实!");
			return false;
		}
		else
		{
			
//			var sql="select agentcode from laagent where agentcode='"+trim(aAgent[0][1])+"' and agentstate<'03' ";
			
			var sqlid4="SinCardTempFeeInputSql4";
	   	 	var mySql4=new SqlClass();
	   	 	mySql4.setResourceName("selflist.SinCardTempFeeInputSql");
	   	 	mySql4.setSqlId(sqlid4); //指定使用SQL的id
	   	 	mySql4.addSubPara(trim(aAgent[0][1]));//指定传入参数
	   	 	var sql = mySql4.getString();

			var cAgent= decodeEasyQueryResult(easyQueryVer3(sql));
	    if(cAgent == null || cAgent == "")
			{
				alert("该代理人对应的专员"+aAgent[0][1]+"不存在或者已离职，请核实!");
				return false;
			}
	  }
		
    initTempGrid();
    initTempClassGrid();
    TempGrid.clearData("TempGrid");
    TempClassGrid.clearData("TempClassGrid"); 

    //校验录入
    if (verifyElement("卡单起始号码|NOTNULL&len=20", document.all('StartNo').value) != true)  return false;

    if (verifyElement("卡单终止号码|NOTNULL&len=20", document.all('EndNo').value) != true)    return false;

//zy 2010-1-15 15:32 如果不是新单证则不允许进行收费
    if(document.all('StartNo').value.substr(2,1)!="7" || document.all('EndNo').value.substr(2,1)!="7" )
    {
      alert("您输入的单证不符合新单证规则，请核实");
      return false;
    }
	  var cardNumSql = "select '"+document.all('EndNo').value+"'-'"+document.all('StartNo').value+"'+1 from dual";
    var cardNumResult = easyExecSql(cardNumSql);
         //alert(arrResult);
	  if(cardNumResult>5000)
	  {
		    alert("您输入的卡单数量超过5000条，请减少一次卡单收费的数量！");
         return false;
	  }
//    if(document.all('CertifyCode').value=="" || document.all('CertifyCode').value==null)
//    {
//    	alert("请输入单证编码！");
//    	return ;
//    }
    //保存界面输入的卡单起止号
    fmSave.fmStartNo.value=document.all('StartNo').value;
    fmSave.fmEndNo.value=document.all('EndNo').value;

    //校验单证发放，只需校验是否发放到进行交费的四级机构

  // 	var receivecom = decodeEasyQueryResult(easyQueryVer3("SELECT receivecom FROM lzcard where certifycode='"+trim(document.all('CertifyCode').value)+"' and  startno='"+trim(document.all('StartNo').value)+"' and endno='"+trim(document.all('EndNo').value)+"' "));
  // if(receivecom == null || receivecom == "")
  // {
	 //  	alert("您输入的该批卡单在单证系统中没有记录，请核对卡单起止号！");
	//   	return false;
  // 	}

    //2008-08-27 zz 为了自主卡单二期测试代理人佣金，需把单证发到代理人手中，先打开这个校验
   	//if(receivecom[0][0]!=("A"+document.all('ManageCom').value))
   	//{
   		//alert("您所录入的该批卡单没有发送到该管理机构下，请核对！");
   		//return false;
   	//}
   	//升级的单证校验，可以发放到四级机构或者是部门或者是代理人手里
//   	var receivecom = decodeEasyQueryResult(easyQueryVer3("select count(*) from lzcard where certifycode='"+trim(document.all('CertifyCode').value)+"' and  startno>='"+trim(document.all('StartNo').value)+"' and startno<='"+trim(document.all('EndNo').value)+"' and stateflag = '3' and (substr(receivecom, 1, 1)='B' or	substr(receivecom, 1, 1)='D' or (substr(receivecom, 0, 1)='A' and length(trim(receivecom))='9'))"));
//   	var cardcount = parseInt(document.all('EndNo').value.substr(12,19),10)-parseInt(document.all('StartNo').value.substr(12,19),10)+1;   	                                                         
//   	var csql="select count(*) from lzcard where certifycode='"+trim(document.all('CertifyCode').value)+"' and  startno>='"+trim(document.all('StartNo').value)+"' and startno<='"+trim(document.all('EndNo').value)+"' and stateflag = '3' and (substr(receivecom, 1, 1)='B' or	substr(receivecom, 1, 1)='D' or (substr(receivecom, 0, 1)='A' and length(trim(receivecom))='9'))";
//    if(receivecom == null || receivecom == "")
//    {
//	   	alert("您输入的该批卡单在单证系统中没有记录，请核对卡单起止号！");
//	   	return false;
//    }
//    if(receivecom!=cardcount)
//    {
//    	alert("您输入的该批卡单中有某些单证没有按照发放规则进行发放，请核实！");
//    	return  false;
//    }
    
    //判断该卡单是否发给该业务员
    
//    var sql="select count(*) from lzcard where certifycode='"+trim(document.all('CertifyCode').value)+"' and  startno>='"+trim(document.all('StartNo').value)+"' and endno<='"+trim(document.all('EndNo').value)+"' and stateflag = '3' and receivecom='D"+fm.AgentCode.value+"'";
    
        var sqlid5="SinCardTempFeeInputSql5";
	 	var mySql5=new SqlClass();
	 	mySql5.setResourceName("selflist.SinCardTempFeeInputSql");
	 	mySql5.setSqlId(sqlid5); //指定使用SQL的id
	 	mySql5.addSubPara(trim(document.all('CertifyCode').value));//指定传入参数
	 	mySql5.addSubPara(trim(document.all('StartNo').value));
	 	mySql5.addSubPara(trim(document.all('EndNo').value));
	 	mySql5.addSubPara("D"+fm.AgentCode.value);
	 	var sql = mySql5.getString();
    
     var receivecom = decodeEasyQueryResult(easyQueryVer3(sql));
     var dif= decodeEasyQueryResult(easyQueryVer3("select ("+trim(document.all('EndNo').value)+"-"+trim(document.all('StartNo').value)+")+1 from dual"));

     if(receivecom[0][0] < dif[0][0])
     {
    	 alert("该批卡单未发放给该业务员！");
    	 return false;
     }
    
   	if(!(document.all('PayDate').value==null || trim(document.all('PayDate').value)==""))
    {
      if(!isDate(document.all('PayDate').value))
      {
      	return false;
      }
      if(compareDate(document.all('PayDate').value,getCurrentDate())==1)
      {
      	alert("交费日期不能超过今天！");
      	return false;
      }
     }
   	
   
    TempGrid.unLock();   	   	
    TempGrid.addOne("TempGrid");
  //  TempClassGrid.unLock();   	   	
  //  TempClassGrid.addOne("TempClassGrid");
    divTempFeeClassInput.style.display="";
    confirmFlag=true;
    document.all('distill').disabled=true;  //只能点击一次确定，防止多次录入
   
}

  /**
   * 对小数点后第三位四舍五入
   * <p><b>Example: </b><p>
   * <p>mathRound(123.456) returns 123.46<p>
   * @param intValue 整型数值
   * @return 对小数点后第三位四舍五入后的整型数值
   */
function mathRound( x )
{
  var v = Math.round( x * 100 ) ;
  v = v/100;
  return v;
}

function addRecord()
{   
   if(confirmFlag==false){alert("请单击确定");return;}//如果没有单击确定，提示

   if(!checkPubValue())  return;
   if(!checkTempRecord()) return;
   if(!checkTempClassRecord()) return;

  //判断暂交费信息中的交费金额是否和暂交费分类信息中的交费金额相等
   tempClassFee=tempClassFee/10000;
   tempFee=tempFee/10000;

   if(pointTwo(tempClassFee)==pointTwo(tempFee) && tempFee!='')   
   {
      addAction = addAction+1;
      sumTempFee = sumTempFee+tempFee;
      document.all('TempFeeCount').value = addAction;
      document.all('SumTempFee').value = pointTwo(sumTempFee);
      tempClassFee=0.0;
      tempFee=0.0;
   }
   else 
   {  
      tempClassFee=0.0;
      tempFee=0.0;
      alert("金额不等或缺少数据！");		
      return ;
   }

  //	 addAction = addAction+1;
  divTempFeeSave.style.display="";      
  var i = 0;	
  var TempRecordCount = 0;
  var TempClassRecordCount = 0;
  var EnterAccDate=""; //到帐日期
  var EnterAccDateFlag=1; //是否录入到帐日期的标记(如果交费项有多条，并且都填写了到帐日期，那么有效)
  TempRecordCount = TempGrid.mulLineCount; //TempGrid的行数 
  TempClassRecordCount = TempClassGrid.mulLineCount; //页面上纪录的行数 
  //alert("往jiaofei表中插的行数==="+TempRecordCount);


 //暂交费分类信息赋给用于提交数据的Grid 
 for(i=0;i<TempClassRecordCount;i++)
 {
    
    if(TempClassGrid.getRowColData(i,1)!='')  //如果交费方式为空，则跳过
    {
     if(i==0) 
     EnterAccDate=TempClassGrid.getRowColData(i,6);//将到帐日期赋第一行的到帐日期
     
     TempClassToGrid.addOne("TempClassToGrid");
     TempClassToGrid.setRowColData(TempClassToGrid.mulLineCount-1,2,TempClassGrid.getRowColData(i,1));
     TempClassToGrid.setRowColData(TempClassToGrid.mulLineCount-1,3,document.all('PayDate').value.trim());
     TempClassToGrid.setRowColData(TempClassToGrid.mulLineCount-1,4,TempClassGrid.getRowColData(i,3));
     TempClassToGrid.setRowColData(TempClassToGrid.mulLineCount-1,5,TempClassGrid.getRowColData(i,6));
     TempClassToGrid.setRowColData(TempClassToGrid.mulLineCount-1,6,document.all('PolicyCom').value.trim());
     TempClassToGrid.setRowColData(TempClassToGrid.mulLineCount-1,7,TempClassGrid.getRowColData(i,4)); 
     TempClassToGrid.setRowColData(TempClassToGrid.mulLineCount-1,8,TempClassGrid.getRowColData(i,5));     
     TempClassToGrid.setRowColData(TempClassToGrid.mulLineCount-1,9,TempClassGrid.getRowColData(i,7));     
     TempClassToGrid.setRowColData(TempClassToGrid.mulLineCount-1,10,TempClassGrid.getRowColData(i,8));     
     TempClassToGrid.setRowColData(TempClassToGrid.mulLineCount-1,11,TempClassGrid.getRowColData(i,9));     
     TempClassToGrid.setRowColData(TempClassToGrid.mulLineCount-1,12,TempClassGrid.getRowColData(i,10));     
     TempClassToGrid.setRowColData(TempClassToGrid.mulLineCount-1,13,TempClassGrid.getRowColData(i,11));     
     TempClassToGrid.setRowColData(TempClassToGrid.mulLineCount-1,14,TempClassGrid.getRowColData(i,12));
     TempClassToGrid.setRowColData(TempClassToGrid.mulLineCount-1,18,TempClassGrid.getRowColData(i,16));
     TempClassToGrid.setRowColData(TempClassToGrid.mulLineCount-1,19,TempClassGrid.getRowColData(i,17));
              
     if(trim(TempClassGrid.getRowColData(i,6))=="") //如果有交费项的到帐日期为空，那么标志置0
        EnterAccDateFlag=0;
     
     if(compareDate(EnterAccDate,TempClassGrid.getRowColData(i,6))==2)
       EnterAccDate=TempClassGrid.getRowColData(i,6);

    }

  }

   
  if(EnterAccDateFlag==0)
    EnterAccDate="";
   

     
  //暂交费信息赋给用于提交数据的Grid
  for(i=0;i<TempRecordCount;i++)
  {
    if(TempGrid.getRowColData(i,1)!='')  //如果险种编码为空，则跳过
     {
       TempToGrid.addOne("TempToGrid");//当前文件中的对象，不用加前缀
       TempToGrid.setRowColData(TempToGrid.mulLineCount-1,3,document.all('PayDate').value.trim());          
       TempToGrid.setRowColData(TempToGrid.mulLineCount-1,4,TempGrid.getRowColData(i,3));
       TempToGrid.setRowColData(TempToGrid.mulLineCount-1,5,EnterAccDate);          
       TempToGrid.setRowColData(TempToGrid.mulLineCount-1,6,document.all('PolicyCom').value.trim());       
       TempToGrid.setRowColData(TempToGrid.mulLineCount-1,7,TempGrid.getRowColData(i,1));          
       TempToGrid.setRowColData(TempToGrid.mulLineCount-1,8,document.all('AgentGroup').value.trim());
       TempToGrid.setRowColData(TempToGrid.mulLineCount-1,9,document.all('AgentCode').value.trim());               
       TempToGrid.setRowColData(TempToGrid.mulLineCount-1,10,TempGrid.getRowColData(0,4));                  
     	 TempToGrid.setRowColData(TempToGrid.mulLineCount-1,12,TempGrid.getRowColData(i,5));     
     	 TempToGrid.setRowColData(TempToGrid.mulLineCount-1,13,TempGrid.getRowColData(i,6));   
     }
  }
  fm.PayMode.value = '';
  fm.PayModeName.value = '';
  initTempGrid();
  initTempClassGrid();
  divTempFeeClassInput.style.display="none";
  fm.butAdd.disabled =false;
  initDivPayMode();//初始化divPayMode
  confirmFlag=false; //复位，需重新单击确定
}



//检查MulLine的信息域的数据格式是否合格
function checkTempRecord()
{
  tempFee=0.0;
  var TempRecordCount = 0;
  TempRecordCount=TempGrid.mulLineCount; //页面上纪录的行数 

  var i=0;
  var j=0;
  //严格限制只能录入一条交费记录
  if(TempRecordCount>1)
  {
  	alert("一批卡单只能录入一条交费信息");
  	return false;
  }
  
  var arrCardMoney = new Array();
  var allMoney = 0;
  var prem = 0;
  
   //parseInt第二个参数指定为10,则按照十进制来返回值,否则:如果第一个参数以 "0x" 开头，parseInt() 会把第一个参数的其余部分解析为十六进制的整数。如果第一个参数以 0 开头，那么 ECMAScript v3 允许 parseInt() 的一个实现把其后的字符解析为八进制或十六进制的数字。如果 第一个参数 以 1 ~ 9 的数字开头，parseInt() 将把它解析为十进制的整数
   var j = parseInt(document.all('EndNo').value.substr(12,19),10)-parseInt(document.all('StartNo').value.substr(12,19),10)+1;
   //alert("document.all('EndNo').value.substr(12,19):"+document.all('EndNo').value.substr(12,19));
   //alert("parseInt(document.all('EndNo').value.substr(12,19)):"+parseInt(document.all('EndNo').value.substr(12,19)));


  //增加录入金额与保费的校验
  if(TempGrid.checkValue("TempGrid"))
  { 
     for(var n=0;n<TempGrid.mulLineCount;n++)
     {
     	   if(TempGrid.getRowColData(n,3)<=0)
    	  {
	    		alert("录入的交费金额应该为大于0的数据，请重新录入");
	    	 	return false;
    	  }           	   
		     allMoney = allMoney+ parseInt(TempGrid.getRowColData(n,3)); 
		     //alert("从页面multiline得到的保费:"+allMoney);
		    //查询该卡单的保费信息
		     
//		     var sql="select prem from LMCardRisk where certifycode ='"+trim(document.all('CertifyCode').value)+"'  and riskcode='"+TempGrid.getRowColData(n,1)+"' and portfolioflag='1'";
		     
		        var sqlid6="SinCardTempFeeInputSql6";
			 	var mySql6=new SqlClass();
			 	mySql6.setResourceName("selflist.SinCardTempFeeInputSql");
			 	mySql6.setSqlId(sqlid6); //指定使用SQL的id
			 	mySql6.addSubPara(trim(document.all('CertifyCode').value));//指定传入参数
			 	mySql6.addSubPara(TempGrid.getRowColData(n,1));
			 	var sql = mySql6.getString();
		     
		     prem=decodeEasyQueryResult(easyQueryVer3(sql));
		     // portfolioflag字段为1时，risocde存放的是产品代码
		     // var sprem = "select prem from LMCardRisk where certifycode ='"+trim(document.all('CertifyCode').value)+"'  and riskcode='"+TempGrid.getRowColData(n,1)+"' ";
		     // prompt("sprem",sprem);
		     if(prem == null || prem == "")
		     {
		     	alert("单证没有对该批卡单的保费进行描述！");
		     	return false;
		     	}     	      
     } //end of for


     //alert("计算该批卡单的总保费"+parseInt(prem[0][0])*j);
     
		 if(parseInt(prem[0][0])*j!=allMoney)
		 {
		    alert("录入的交费金额总额与该批卡单保费不符，请重新录入！");
		   	return false;
		 } 
     
     for(i=0;i<TempRecordCount;i++)  
     {
     	 if(TempGrid.getRowColData(i,1)!='')  //如果产品代码为空，则跳过
       {   	     	      	   	
         tempFee = tempFee+10000*parseFloat(TempGrid.getRowColData(i,3));//交费金额累计 
       }
       if(TempGrid.getRowColData(i,5)== '' ||TempGrid.getRowColData(i,6)== '')
       {
        alert("新单暂交费录入，请录入缴费方式和缴费年期信息！");
      	return false;
       }      
     }//end of for        	
   } //end of if	    
  return true; 
}



//添加一笔纪录前,计算添加的暂交费分类信息数据交费金额累计
function checkTempClassRecord()
{			
  var TempClassRecordCount = 0;
  tempClassFee=0.0;
  var i = 0;
  TempClassRecordCount=TempClassGrid.mulLineCount; //页面上纪录的行数 
  if(TempClassRecordCount>1)
  {
  	alert("一批卡单只能录入一条交费信息");
  	return false;
  }
     
      
  for(i=0;i<TempClassRecordCount;i++)
  {
   if(TempClassGrid.getRowColData(i,1)!='')  //如果交费方式为空，则跳过
    {
    	if(trim(TempClassGrid.getRowColData(i,1))!='1' && trim(TempClassGrid.getRowColData(i,1))!='2' && trim(TempClassGrid.getRowColData(i,1))!='3' && trim(TempClassGrid.getRowColData(i,1))!='4')
    	{
	    	alert("卡单收费目前不支持该种交费方式！");
	    	return false;
      }
    	tempClassFee = tempClassFee+10000*parseFloat(TempClassGrid.getRowColData(i,3));//交费金额累计 	    	
      if(trim(TempClassGrid.getRowColData(i,6))!='')//如果到帐日期不为空，那么判断是否大于等于当前日期
      {
      	if(isDate(trim(TempClassGrid.getRowColData(i,6))))//如果日期格式正确
      	{
      		if(compareDate(trim(TempClassGrid.getRowColData(i,6)),getCurrentDate())==1)//如果大于当前日期
      		{
      		  alert("到帐日期不能超过今天！");     		  
      		  return false;	
      		} 
      	}
      	else
      	{
      	  alert("到帐日期格式不对！");
      	  return false;	
      	}
      }
      
      
      if(TempClassGrid.getRowColData(i,1)=='4')  //如果交费方式为4-银行转账，必须填写 开户银行,银行账号,户名并且到帐日期必须为空
      {
        if(trim(TempClassGrid.getRowColData(i,7))==''||trim(TempClassGrid.getRowColData(i,8))==''||trim(TempClassGrid.getRowColData(i,9))=='')
          {
           alert("银行转账时，必须填写 开户银行,银行账号,户名");
           return false;
          }
        TempClassGrid.setRowColData(i,6,'');//  将到帐日期置空
        
        if (trim(TempClassGrid.getRowColData(i,7))=="0101") {
          if (trim(TempClassGrid.getRowColData(i,8)).length!=19 || !isInteger(trim(TempClassGrid.getRowColData(i,8)))) {
            alert("工商银行的账号必须是19位的数字，最后一个星号（*）不要！");
            return false;
          }
        }
      }
      
      if(TempClassGrid.getRowColData(i,1)=='2'||TempClassGrid.getRowColData(i,1)=='3')  //如果交费方式为2||3-支票类，则票据号码和银行不能为空
      {
        if(trim(TempClassGrid.getRowColData(i,4))==''||trim(TempClassGrid.getRowColData(i,7))=='')
          {
           alert("交费方式为支票时，票据号码和开户银行不能为空");
           return false;
          }
          TempClassGrid.setRowColData(i,6,'');//  将到帐日期置空
      }
      
      
    }	

  }
     return true;
}

//点击全部提交或者全部清除时--清空数据并初始化
function clearFormData()
{  
  initGlobalData();
  initTempToGrid();
  initTempClassToGrid();
  initTempGrid();
  initTempClassGrid();
  document.all('StartNo').value="";
  document.all('EndNo').value="";
  document.all('CertifyCode').value="";
  document.all('ManageCom').value="";
  document.all('AgentCode').value="";
  document.all('TempFeeCount').value=0;
  document.all('SumTempFee').value=0.0 ;
  divTempFeeSave.style.display="none";
  divTempFeeInput.style.display="";  
  document.all('distill').disabled=false;             
  TempGrid.lock();
}


//初始化全局变量
function initGlobalData()
{
//存放添加动作执行的次数
 addAction = 0;
//暂交费总金额
 sumTempFee = 0.0;
//暂交费信息中交费金额累计
 tempFee = 0.0;
//暂交费分类信息中交费金额累计
 tempClassFee = 0.0;
 confirmFlag=false; //复位，需重新单击确定 
}



//校验录入的单证描述表是否描述自动核销的标记，如果需要返回true,否则返回false
function needVerifyCertifyCode(CertifyCode)
{
	try{
//		var tSql = "select JugAgtFlag from lmcertifydes where certifycode = '" + CertifyCode + "'";
		
		    var sqlid7="SinCardTempFeeInputSql7";
		 	var mySql7=new SqlClass();
		 	mySql7.setResourceName("selflist.SinCardTempFeeInputSql");
		 	mySql7.setSqlId(sqlid7); //指定使用SQL的id
		 	mySql7.addSubPara(CertifyCode);//指定传入参数
		 	var tSql = mySql7.getString();
		
		var tResult = easyExecSql(tSql, 1, 1, 1); 
		var JugAgtFlag = tResult[0][0];
		//JugAgtFlag为0或null-发放\回收都做校验，1-发放作校验,回收不作校验，2-发放不作校验,回收作校验，3-发放/回收都不做校验
		if(JugAgtFlag == "2" || JugAgtFlag == "3")
		{
				return false;
		}
		return true;
	}catch(E){}
	return false;
/*
  try 
  {
     var tSql = "select Sysvarvalue from LDSysVar where Sysvar='NotVerifyCertifyCode'";          
     var tResult = easyExecSql(tSql, 1, 1, 1);       
     var strRiskcode = tResult[0][0];
     var strValue=strRiskcode.split("/");
     var i=0;
	   while(i<strValue.length)
	   {
		   	if(CertifyCode==strValue[i])
		   	{
	           return false;
		   	}
		   	i++;
	   }   	   
	 }
	catch(e){}
 	return true; 
 	*/
}





function queryAgent()
{
	if(document.all('ManageCom').value==""){
		 alert("请先录入管理机构信息！"); 
		 return;
	}
    if(document.all('AgentCode').value == "")	{  
	  var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+document.all('ManageCom').value,"AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');	  
	  }
	if(document.all('AgentCode').value != "")	 {
	var cAgentCode = fm.AgentCode.value;  //保单号码	
//	var strSql = "select AgentCode,Name from LAAgent where AgentCode='" + cAgentCode +"'";
	
	var sqlid8="SinCardTempFeeInputSql8";
 	var mySql8=new SqlClass();
 	mySql8.setResourceName("selflist.SinCardTempFeeInputSql");
 	mySql8.setSqlId(sqlid8); //指定使用SQL的id
 	mySql8.addSubPara(cAgentCode);//指定传入参数
 	var strSql = mySql8.getString();
	
    var arrResult = easyExecSql(strSql);
    if (arrResult != null) {
      alert("查询结果:  代理人编码:["+arrResult[0][0]+"] 代理人名称为:["+arrResult[0][1]+"]");
      } 
	}
	
}

//查询返回时执行的函数,查询返回一个2维数组，数组下标从[0][0]开始
function afterQuery2(arrResult)
{  
  if(arrResult!=null)
  {
  	fm.AgentCode.value = arrResult[0][0];
  	fm.PolicyCom.value = arrResult[0][2];
  }
//  var aSql = "select agentgroup from LAAgent where agentcode='"+fm.AgentCode.value+"'";  
  
    var sqlid9="SinCardTempFeeInputSql9";
	var mySql9=new SqlClass();
	mySql9.setResourceName("selflist.SinCardTempFeeInputSql");
	mySql9.setSqlId(sqlid9); //指定使用SQL的id
	mySql9.addSubPara(fm.AgentCode.value);//指定传入参数
	var aSql = mySql9.getString();
  
  arrResult = easyExecSql(aSql);
   if(arrResult==null||arrResult=="NULL"||arrResult=="")
   {
     alert("业务员编号错误，无组别信息");	
     fm.AgentCode.value="";
     return false;
   }
  else
  	{
     fm.AgentGroup.value=arrResult;
    } 
}

function GetManageCom()
{
  if(fm.AgentCode.value!=null&&fm.AgentCode.value!="")
  {
   //var strSql = "select a.managecom from LAAgent a,LATree b,LABranchGroup c where 1=1 "
	 //        + "and a.AgentCode = b.AgentCode and a.AgentGroup = c.AgentGroup and (a.AgentState is null or a.AgentState < '03') "
	 //        + "and a.agentcode ='"+fm.AgentCode.value+"'";
//	 var strSql = "select managecom from LAAgent where  agentcode='"+trim(fm.AgentCode.value)+"' ";
	 
	    var sqlid10="SinCardTempFeeInputSql10";
		var mySql10=new SqlClass();
		mySql10.setResourceName("selflist.SinCardTempFeeInputSql");
		mySql10.setSqlId(sqlid10); //指定使用SQL的id
		mySql10.addSubPara(trim(fm.AgentCode.value));//指定传入参数
		var strSql = mySql10.getString();
	 
   var arrResult = easyExecSql(strSql);
   if(arrResult==null||arrResult=="NULL"||arrResult=="")
   {
     alert("业务员编号错误，无交费机构信息");	
     fm.AgentCode.value="";
     return false;
   }
  else
  	{
     fm.PolicyCom.value=arrResult;	  	  
    }
//  var aSql = "select agentgroup from LAAgent where agentcode='"+trim(fm.AgentCode.value)+"'";  
  
    var sqlid11="SinCardTempFeeInputSql11";
	var mySql11=new SqlClass();
	mySql11.setResourceName("selflist.SinCardTempFeeInputSql");
	mySql11.setSqlId(sqlid11); //指定使用SQL的id
	mySql11.addSubPara(trim(fm.AgentCode.value));//指定传入参数
	var aSql = mySql11.getString();
  
  arrResult = easyExecSql(aSql);
   if(arrResult==null||arrResult=="NULL"||arrResult=="")
   {
     alert("业务员编号错误，无组别信息");	
     fm.AgentCode.value="";
     return false;
   }
  else
  	{
       fm.AgentGroup.value=arrResult;	
 //      var nSql = "select name from LAAgent where agentcode='"+trim(fm.AgentCode.value)+"'";
       
       var sqlid12="SinCardTempFeeInputSql12";
   	   var mySql12=new SqlClass();
   	   mySql12.setResourceName("selflist.SinCardTempFeeInputSql");
   	   mySql12.setSqlId(sqlid12); //指定使用SQL的id
   	   mySql12.addSubPara(trim(fm.AgentCode.value));//指定传入参数
   	   var nSql = mySql12.getString();
       
       arrResult = easyExecSql(nSql);
       fm.AgentName.value=arrResult;
    }  
  }	
	
}

///获取业务员信息
function GetAgentInfo()
{
	var receivecom="";
	if(_DBT==_DBO){
		
//		var sql="select substr(receivecom, 2, 10) from lzcard where certifycode='"+trim(document.all('CertifyCode').value)+"' and startno='"+trim(document.all('StartNo').value)+"'and stateflag = '3' And substr(receivecom, 1, 1)='D' and rownum='1'";
		
		   var sqlid13="SinCardTempFeeInputSql13";
	   	   var mySql13=new SqlClass();
	   	   mySql13.setResourceName("selflist.SinCardTempFeeInputSql");
	   	   mySql13.setSqlId(sqlid13); //指定使用SQL的id
	   	   mySql13.addSubPara(trim(document.all('CertifyCode').value));//指定传入参数
	   	   mySql13.addSubPara(trim(document.all('StartNo').value));
	   	   var sql = mySql13.getString();
		
		receivecom = decodeEasyQueryResult(easyQueryVer3(sql));
	}else if(_DBT==_DBM){
		
//		   var sql="select substr(receivecom, 2, 10) from lzcard where certifycode='"+trim(document.all('CertifyCode').value)+"' and startno='"+trim(document.all('StartNo').value)+"'and stateflag = '3' And substr(receivecom, 1, 1)='D' limit 1";
		
		   var sqlid14="SinCardTempFeeInputSql14";
	   	   var mySql14=new SqlClass();
	   	   mySql14.setResourceName("selflist.SinCardTempFeeInputSql");
	   	   mySql14.setSqlId(sqlid14); //指定使用SQL的id
	   	   mySql14.addSubPara(trim(document.all('CertifyCode').value));//指定传入参数
	   	   mySql14.addSubPara(trim(document.all('StartNo').value));
	   	   var sql = mySql14.getString();
		
		receivecom = decodeEasyQueryResult(easyQueryVer3(sql));
	}
    if(receivecom == null || receivecom == "")
    {
	   	alert("通过卡单起始号码未查询到业务员！");
	   	return false;
    }
    else
    {
    	fm.AgentCode.value=receivecom[0][0];
    	GetManageCom();
    }
}

/*********************************************************************
 *  增加一行mul
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function addMul()
{
  
	var mulLineCount = TempClassGrid.mulLineCount;
	var PayMode = fm.PayMode.value;
	var payFlag = "";
  if(PayMode=="")
  {
  	alert("请先录入交费方式的相应信息！");
  	return false;
  }
	 for(i=0;i<mulLineCount;i++)
	  {
	  	payFlag = TempClassGrid.getRowColData(i,1);
	   	if(payFlag==PayMode)
		   {
	 	       if (TempClassGrid.getRowColData(i,4)==document.all('ChequeNo5').value)
	 	       {
			         alert("此记录已经添加");
			         return false;
	 	       }
	 	     
	 	   }
	}

  	if(!CheckPayMode())
  	{
  	  return false;	
  	}	
		if(document.all('PayFee'+PayMode).value=="")
		{
			alert("请录入交费金额!");
			return;
		}
  
	TempClassGrid.addOne("TempClassGrid");

	TempClassGrid.setRowColData(mulLineCount, 1, fm.PayMode.value);

	TempClassGrid.setRowColData(mulLineCount,2,fm.PayModeName.value);
	if(PayMode==1)
	{
		TempClassGrid.setRowColData(mulLineCount,3,fm.PayFee1.value);
		TempClassGrid.setRowColData(mulLineCount,6,document.all('PayDate').value);
	}
	if(PayMode==2)
	{
	//	alert("到帐日期======");
		if (verifyElement("开户银行|NOTNULL", document.all('BankCode2').value) != true) {
      return false;
    }
		if (verifyElement("票据号码|NOTNULL", document.all('ChequeNo2').value) != true) {
      return false;
    }		
		TempClassGrid.setRowColData(mulLineCount,3,fm.PayFee2.value);
		TempClassGrid.setRowColData(mulLineCount,4,fm.ChequeNo2.value);
		TempClassGrid.setRowColData(mulLineCount,5,fm.ChequeDate2.value);
		TempClassGrid.setRowColData(mulLineCount,7,fm.BankCode2.value);
		TempClassGrid.setRowColData(mulLineCount,8,fm.BankAccNo2.value);
		TempClassGrid.setRowColData(mulLineCount,9,fm.AccName2.value);
		TempClassGrid.setRowColData(mulLineCount,6,document.all('PayDate').value);
		//TempClassGrid.setRowColData(mulLineCount,10,fm.InBankCode2.value);
		//TempClassGrid.setRowColData(mulLineCount,11,fm.InBankAccNo2.value);
		//TempClassGrid.setRowColData(mulLineCount,12,fm.InAccName2.value);		
	}	
	if(PayMode==3)
	{
		if (verifyElement("票据号|NOTNULL", document.all('ChequeNo3').value) != true) {
      return false;
    }	
	//	if (verifyElement("支票日期|NOTNULL", document.all('ChequeDate3').value) != true) {
   //   return false;
   // }
		if (verifyElement("开户银行|NOTNULL", document.all('BankCode3').value) != true) {
      return false;
    }
	//	if (verifyElement("开户银行户名|NOTNULL", document.all('AccName3').value) != true) {
   //   return false;
   // } 
	//	if (verifyElement("收费银行|NOTNULL", document.all('InBankCode3').value) != true) {
   //   return false;
  //  }
	//	if (verifyElement("收费银行账号|NOTNULL", document.all('InBankAccNo3').value) != true) {
  //    return false;
    //}
  
		TempClassGrid.setRowColData(mulLineCount,3,fm.PayFee3.value);
		TempClassGrid.setRowColData(mulLineCount,4,fm.ChequeNo3.value);
		TempClassGrid.setRowColData(mulLineCount,5,fm.ChequeDate3.value);
		TempClassGrid.setRowColData(mulLineCount,6,'');
		TempClassGrid.setRowColData(mulLineCount,7,fm.BankCode3.value);
		TempClassGrid.setRowColData(mulLineCount,8,fm.BankAccNo3.value);
		TempClassGrid.setRowColData(mulLineCount,9,fm.AccName3.value);
	//	TempClassGrid.setRowColData(mulLineCount,10,fm.InBankCode3.value);
	//	TempClassGrid.setRowColData(mulLineCount,11,fm.InBankAccNo3.value);
		//TempClassGrid.setRowColData(mulLineCount,12,fm.InAccName3.value);		
	}	
	/*if(PayMode==4)
	{
		
		if (verifyElement("开户银行|NOTNULL", document.all('BankCode4').value) != true) {
      return false;
    }
		if (verifyElement("开户银行账号|NOTNULL", document.all('BankAccNo4').value) != true) {
      return false;
    }
		if (verifyElement("开户银行户名|NOTNULL", document.all('AccName4').value) != true) {
      return false;
    } 
		if (verifyElement("收费银行|NOTNULL", document.all('InBankCode4').value) != true) {
      return false;
    }    
		if (verifyElement("收费银行账号|NOTNULL", document.all('InBankAccNo4').value) != true) {
      return false;
    }		
		
		
		TempClassGrid.setRowColData(mulLineCount,3,fm.PayFee4.value);
		//TempClassGrid.setRowColData(mulLineCount,4,fm.ChequeNo4.value);
		//TempClassGrid.setRowColData(mulLineCount,5,fm.ChequeDate4.value);
		TempClassGrid.setRowColData(mulLineCount,6,'');
		TempClassGrid.setRowColData(mulLineCount,7,fm.BankCode4.value);
		TempClassGrid.setRowColData(mulLineCount,8,fm.BankAccNo4.value);
		TempClassGrid.setRowColData(mulLineCount,9,fm.AccName4.value);
		TempClassGrid.setRowColData(mulLineCount,10,fm.InBankCode4.value);
		TempClassGrid.setRowColData(mulLineCount,11,fm.InBankAccNo4.value);
		//TempClassGrid.setRowColData(mulLineCount,12,fm.InAccName4.value);		
	}	*/
	if(PayMode==5)
	{
		 alert("不允许采用内部转账方式进行收费");
		 return false;
	}	

	if(PayMode==4)
	{

		if (verifyElement("开户银行|NOTNULL", document.all('BankCode4').value) != true) {
      return false;
    }
		if (verifyElement("银行账号|NOTNULL", document.all('BankAccNo4').value) != true) {
      return false;
    }
		if (verifyElement("银行户名|NOTNULL", document.all('AccName4').value) != true) {
      return false;
    }

		TempClassGrid.setRowColData(mulLineCount,3,fm.PayFee4.value);
		TempClassGrid.setRowColData(mulLineCount,6,'');
		TempClassGrid.setRowColData(mulLineCount,7,fm.BankCode4.value);
		TempClassGrid.setRowColData(mulLineCount,8,fm.BankAccNo4.value);
		TempClassGrid.setRowColData(mulLineCount,9,fm.AccName4.value);
		TempClassGrid.setRowColData(mulLineCount,16,fm.IDType.value);
		TempClassGrid.setRowColData(mulLineCount,17,fm.IDNo.value);
		//TempClassGrid.setRowColData(mulLineCount,12,fm.InAccName7.value);		
	}	
	if(PayMode==8)
	{
		TempClassGrid.setRowColData(mulLineCount,3,fm.PayFee8.value);
		TempClassGrid.setRowColData(mulLineCount,6,document.all('PayDate').value);
	}	
  if(PayMode==6)
  { //alert("BBBBBBBBBBBBBBBB");
   if (verifyElement("开户银行|NOTNULL", document.all('BankCode6').value) != true) {
      return false;
    }
    
    
    TempClassGrid.setRowColData(mulLineCount,3,fm.PayFee6.value);
		TempClassGrid.setRowColData(mulLineCount,6,'');
		//TempClassGrid.setRowColData(mulLineCount,6,'');
		TempClassGrid.setRowColData(mulLineCount,7,fm.BankCode6.value);
		TempClassGrid.setRowColData(mulLineCount,8,fm.BankAccNo6.value);
		TempClassGrid.setRowColData(mulLineCount,9,fm.AccName6.value);
		//TempClassGrid.setRowColData(mulLineCount,10,fm.InBankCode9.value);
		//TempClassGrid.setRowColData(mulLineCount,11,fm.InBankAccNo9.value);
		//TempClassGrid.setRowColData(mulLineCount,12,fm.InAccName9.value);
  }
	if(PayMode=='0')
	{
		TempClassGrid.setRowColData(mulLineCount,3,fm.PayFee0.value);
		TempClassGrid.setRowColData(mulLineCount,6,'');
	}  
  document.all('spanTempClassGrid'+mulLineCount).all('TempClassGridSel').checked=true;

}

/*********************************************************************
 *  修改选中的mul
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function ModMul()
{
	//var MulData=TempClassGrid.getRowColData(0,3);
	var rowNum=TempClassGrid.mulLineCount;
	if (rowNum<=0)
	{
	alert("未发现要修改的数据，请点击“增加”！");
	return false;	
	}
	var mulLineCount = TempClassGrid.getSelNo()-1;
	//var mulLineCount = TempClassGrid.getSelNo();
	var PayMode = TempClassGrid.getRowColData(mulLineCount,1);
	TempClassGrid.setRowColData(mulLineCount,1,fm.PayMode.value);
	TempClassGrid.setRowColData(mulLineCount,2,fm.PayModeName.value);
	if(PayMode==1)
	{
		TempClassGrid.setRowColData(mulLineCount,3,fm.PayFee1.value);
		TempClassGrid.setRowColData(mulLineCount,6,document.all('PayDate').value);
	}
	if(PayMode==2)
	{
	//	alert("到帐日期======");
		if (verifyElement("开户银行|NOTNULL", document.all('BankCode2').value) != true) {
      return false;
    }
		if (verifyElement("票据号码|NOTNULL", document.all('ChequeNo2').value) != true) {
      return false;
    }		
		TempClassGrid.setRowColData(mulLineCount,3,fm.PayFee2.value);
		TempClassGrid.setRowColData(mulLineCount,4,fm.ChequeNo2.value);
		TempClassGrid.setRowColData(mulLineCount,5,fm.ChequeDate2.value);
		TempClassGrid.setRowColData(mulLineCount,7,fm.BankCode2.value);
		TempClassGrid.setRowColData(mulLineCount,8,fm.BankAccNo2.value);
		TempClassGrid.setRowColData(mulLineCount,9,fm.AccName2.value);
		TempClassGrid.setRowColData(mulLineCount,6,document.all('PayDate').value);
		//TempClassGrid.setRowColData(mulLineCount,10,fm.InBankCode2.value);
		//TempClassGrid.setRowColData(mulLineCount,11,fm.InBankAccNo2.value);
		//TempClassGrid.setRowColData(mulLineCount,12,fm.InAccName2.value);		
	}	
	if(PayMode==3)
	{
		if (verifyElement("票据号|NOTNULL", document.all('ChequeNo3').value) != true) {
      return false;
    }	
	//	if (verifyElement("支票日期|NOTNULL", document.all('ChequeDate3').value) != true) {
   //   return false;
   // }
		if (verifyElement("开户银行|NOTNULL", document.all('BankCode3').value) != true) {
      return false;
    }
	//	if (verifyElement("开户银行户名|NOTNULL", document.all('AccName3').value) != true) {
   //   return false;
   // } 
	//	if (verifyElement("收费银行|NOTNULL", document.all('InBankCode3').value) != true) {
   //   return false;
  //  }
	//	if (verifyElement("收费银行账号|NOTNULL", document.all('InBankAccNo3').value) != true) {
  //    return false;
    //}
  
		TempClassGrid.setRowColData(mulLineCount,3,fm.PayFee3.value);
		TempClassGrid.setRowColData(mulLineCount,4,fm.ChequeNo3.value);
		TempClassGrid.setRowColData(mulLineCount,5,fm.ChequeDate3.value);
		TempClassGrid.setRowColData(mulLineCount,6,'');
		TempClassGrid.setRowColData(mulLineCount,7,fm.BankCode3.value);
		TempClassGrid.setRowColData(mulLineCount,8,fm.BankAccNo3.value);
		TempClassGrid.setRowColData(mulLineCount,9,fm.AccName3.value);
	//	TempClassGrid.setRowColData(mulLineCount,10,fm.InBankCode3.value);
	//	TempClassGrid.setRowColData(mulLineCount,11,fm.InBankAccNo3.value);
		//TempClassGrid.setRowColData(mulLineCount,12,fm.InAccName3.value);		
	}	
	/*if(PayMode==4)
	{
		
		if (verifyElement("开户银行|NOTNULL", document.all('BankCode4').value) != true) {
      return false;
    }
		if (verifyElement("开户银行账号|NOTNULL", document.all('BankAccNo4').value) != true) {
      return false;
    }
		if (verifyElement("开户银行户名|NOTNULL", document.all('AccName4').value) != true) {
      return false;
    } 
		if (verifyElement("收费银行|NOTNULL", document.all('InBankCode4').value) != true) {
      return false;
    }    
		if (verifyElement("收费银行账号|NOTNULL", document.all('InBankAccNo4').value) != true) {
      return false;
    }		
		
		
		TempClassGrid.setRowColData(mulLineCount,3,fm.PayFee4.value);
		//TempClassGrid.setRowColData(mulLineCount,4,fm.ChequeNo4.value);
		//TempClassGrid.setRowColData(mulLineCount,5,fm.ChequeDate4.value);
		TempClassGrid.setRowColData(mulLineCount,6,'');
		TempClassGrid.setRowColData(mulLineCount,7,fm.BankCode4.value);
		TempClassGrid.setRowColData(mulLineCount,8,fm.BankAccNo4.value);
		TempClassGrid.setRowColData(mulLineCount,9,fm.AccName4.value);
		TempClassGrid.setRowColData(mulLineCount,10,fm.InBankCode4.value);
		TempClassGrid.setRowColData(mulLineCount,11,fm.InBankAccNo4.value);
		//TempClassGrid.setRowColData(mulLineCount,12,fm.InAccName4.value);		
	}	*/
	if(PayMode==5)
	{
		 alert("不允许采用内部转账方式进行收费");
		 return false;
	}	

	if(PayMode==4)
	{

		if (verifyElement("开户银行|NOTNULL", document.all('BankCode4').value) != true) {
      return false;
    }
		if (verifyElement("银行账号|NOTNULL", document.all('BankAccNo4').value) != true) {
      return false;
    }
		if (verifyElement("银行户名|NOTNULL", document.all('AccName4').value) != true) {
      return false;
    }

		TempClassGrid.setRowColData(mulLineCount,3,fm.PayFee4.value);
		TempClassGrid.setRowColData(mulLineCount,6,'');
		TempClassGrid.setRowColData(mulLineCount,7,fm.BankCode4.value);
		TempClassGrid.setRowColData(mulLineCount,8,fm.BankAccNo4.value);
		TempClassGrid.setRowColData(mulLineCount,9,fm.AccName4.value);
		TempClassGrid.setRowColData(mulLineCount,16,fm.IDType.value);
		TempClassGrid.setRowColData(mulLineCount,17,fm.IDNo.value);
		//TempClassGrid.setRowColData(mulLineCount,12,fm.InAccName7.value);		
	}	
  document.all('spanTempClassGrid'+mulLineCount).all('TempClassGridSel').checked=true;

}

function CheckPayMode()
{

 //如果交费方式为2||3-支票类，则票据号码和银行不能为空
   if(document.all('PayMode').value=="2")
  {
  		if (verifyElement("开户银行|NOTNULL", document.all('BankCode2').value) != true) {
      return false;
      }
		  if (verifyElement("票据号码|NOTNULL", document.all('ChequeNo2').value) !=true)  {
			return false;
  		}
  }
  
  if(document.all('PayMode').value=="3")
  {
  	  if (verifyElement("开户银行|NOTNULL", document.all('BankCode3').value) != true) {
      return false;
      }
		  if (verifyElement("票据号码|NOTNULL", document.all('ChequeNo3').value) !=true)  {
			return false;
  		}
  	}
  
  if(document.all('PayMode').value=="4" )
  {
  			if (verifyElement("开户银行|NOTNULL", document.all('BankCode4').value) != true) {
      return false;
    }
		if (verifyElement("银行账号|NOTNULL", document.all('BankAccNo4').value) != true) {
      return false;
    }
		if (verifyElement("银行户名|NOTNULL", document.all('AccName4').value) != true) {
      return false;
    }
  	
  	if(document.all('BankCode4').value=="0101")
  	{
  	if(document.all('BankAccNo4').value.length!=19 || !isInteger(trim(document.all('BankAccNo4').value)))
  	{
  		alert("工商银行的账号必须是19位的数字，最后一个星号（*）不要！");
  		return false;
  		}
  	}
  	
  	}
  	
   if(document.all('PayMode').value=="6")
  {
  		if (verifyElement("开户银行|NOTNULL", document.all('BankCode6').value) != true) {
      return false;
      }
  }
   
  
      return true;	
}

function afterCodeSelect(cCodeName, Field) 
{
		if(cCodeName == "FINAbank" ){
			if(divPayMode2.style.display == ""){
				checkBank(document.all('BankCode2'),document.all('BankAccNo2'));
			}
			if(divPayMode3.style.display == ""){
				checkBank(document.all('BankCode3'),document.all('BankAccNo3'));
			}
			if(divPayMode6.style.display == ""){
				checkBank(document.all('BankCode6'),document.all('BankAccNo6'));
			}
  	}
  	if(cCodeName == "bank" ){
			if(divPayMode4.style.display == ""){
				checkBank(document.all('BankCode4'),document.all('BankAccNo4'));
			}
		}
	if(cCodeName == "chargepaymode")
	{
		showTempClassInput(Field.value);
		PayModePrem();
	}
	
	//选择单证类型后自动带出业务员信息
	if(cCodeName == "CardCode")
	{
		GetAgentInfo();
	}
}

/*********************************************************************
 *  根据选择不同的交费方式，初始化页面 gaoht
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function showTempClassInput(type)
{
	for(i=0;i<=9;i++)
	{
		
		if(i==type)
		{
			document.all("divPayMode"+i).style.display='';
		}
		else
		{
		  document.all("divPayMode"+i).style.display='none';
		}
	}
}

function PayModePrem()
{
  var SumPrem =0.0;
	var TempCount = TempGrid.mulLineCount;
  for(i=0;i<TempCount;i++)
  {
  	SumPrem = SumPrem+parseFloat(TempGrid.getRowColData(i,3));//交费金额累计   
  }
  SumPrem = pointTwo(SumPrem);
  if (document.all("PayMode").value == '0')
  {
  	document.all("PayFee0").value = SumPrem;
  	}
  if (document.all("PayMode").value == '1')
  {
  	document.all("PayFee1").value = SumPrem;
  	}
  if (document.all("PayMode").value == '2')
  {
  	document.all("PayFee2").value = SumPrem;
    }
  if (document.all("PayMode").value == '3')
  {
  	document.all("PayFee3").value = SumPrem;
    }
  if (document.all("PayMode").value == '4')
  {
  	document.all("PayFee4").value = SumPrem;
  }
  //if (document.all("PayMode").value == '5')
  //{
  //	document.all("PayFee5").value = SumPrem;
  //}
  if (document.all("PayMode").value == '6')
  {
  	document.all("PayFee6").value = SumPrem;
  }
  if (document.all("PayMode").value == '7')
  {
  	document.all("PayFee7").value = SumPrem;
  }
  if (document.all("PayMode").value == '8')
  {
  	document.all("PayFee8").value = SumPrem;
  }
  if (document.all("PayMode").value == '9')
  {
  	document.all("PayFee9").value = SumPrem;
  }
}

function checkPubValue()
{

   //将对交费日期的控制扩充到整个收费类型中
   if(document.all('PayDate').value==null || trim(document.all('PayDate').value)=="")
    {
    	alert("交费日期不能为空!");
    	return false;
    }

    if((isDate(document.all('PayDate').value)==false) ||document.all('PayDate').value.length!=10)
    {
    	alert("交费日期格式不正确，请采用'YYYY-MM-DD'格式!");
    	return false;
    }
    return true;
}
  
function checkBank(tBankCode,tBankAccNo){
	if(tBankCode.value.length>0 && tBankAccNo.value.length>0){
		if(!checkBankAccNo(tBankCode.value,tBankAccNo.value)){
			tBankAccNo.value = "";
			return false;
		}
	}
}