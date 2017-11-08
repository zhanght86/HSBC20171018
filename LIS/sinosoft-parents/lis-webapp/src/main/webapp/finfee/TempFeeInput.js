 //               该文件中包含客户端需要处理的函数和事件
var turnPage = new turnPageClass();   
var showInfo;
var mDebug="0";
var tResourceName="finfee.TempFeeInputSql";

//存放添加动作执行的次数
var addAction = 0;
//暂交费总金额
//var sumTempFee = 0.0;
//暂交费信息中交费金额累计
var tempFee = "";
//暂交费分类信息中交费金额累计
var tempClassFee = "";
//单击确定后，该变量置为真，单击添加一笔时，检验该值是否为真，为真继续，然后再将该变量置假
var confirmFlag=false;
// 
var arrCardRisk;

var todayDate = new Date();
var sdate = todayDate.getDate();
var smonth= todayDate.getMonth() +1;
var syear= todayDate.getYear();
//var sToDate = syear + "-" +smonth+"-"+sdate

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
	  document.getElementById("fmSave").submit(); //提交
	  initForm();
	  initInput();
 }
  else alert("条件不满足");

}

//打印票据
function printInvoice()
{
  window.open("./TempFeeInputPrintMain.jsp","","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
}


//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
  showInfo.close();
	TempClassToGrid.clearData(); 
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
//屏蔽掉
/*    if (fm.TempFeeType.value==5)
    {
      var tSql = "select tempfeeno from ljtempfee a where a.otherno='"+TempToGrid.getRowColData(0,10)
                 +"' and a.tempfeetype='5' and managecom='"+TempToGrid.getRowColData(0,6)
                 +"' and a.makedate=to_char(sysdate, 'YYYY-MM-DD')"
                 +"  and a.maketime = (select max(maketime) from ljtempfee b where b.otherno='"+TempToGrid.getRowColData(0,10)
                 +"' and makedate=to_char(sysdate, 'YYYY-MM-DD'))";	

      var arrResult = easyExecSql(tSql);
      tSql = "select enteraccdate from ljtempfee where tempfeeno='"+arrResult+"'";   
      var TarrResult = easyExecSql(tSql);
      //alert("1======================="+TarrResult);
      if (TarrResult != "")
      {
      
      tSql = "select max(actugetno) from ljagettempfee where tempfeeno='"+arrResult+"'";	
      arrResult = easyExecSql(tSql);
      
      alert("该预存实付号码："+"“"+arrResult+"”");
      }
      else
      	{
      alert("该预存未到账，到账时生成预存号");	
      }
    }*/
   // PrintData();
  }  
  clearFormData();
  initForm();
}

function PrintData()
{
   //tSql = "select enteraccdate,tempfeetype,otherno from ljtempfee where tempfeeno='"+document.all('TempFeeNo').value+"'";   
   //var mysql=new SqlClass();
	 //mysql.setResourceName("finfee.TempFeeInputSql");
	 //mysql.setSqlId("LJTempFee1");
	 //mysql.addSubPara(document.all('TempFeeNo').value);
   
   
   
   var TarrResult = easyQueryVer3(wrapSql(tResourceName,'LJTempFee1',[document.all('TempFeeNo').value]), 1, 1, 1);;
   
   var ArrData = decodeEasyQueryResult(TarrResult);

   if (TarrResult!=null&&TarrResult!="")
   {
      if (ArrData[0][0]!= ""&&(ArrData[0][1]=="2"||ArrData[0][1]=="4"||ArrData[0][1]=="6"))
   				{
	    				if(confirm("确认要打印发票吗？"))
	      						{
     	     								if (ArrData[0][1]=="2")
     	         									window.open("../operfee/ExtendInvoice.jsp?TempFeeNo=" + document.all('TempFeeNo').value,"","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
           								else if (ArrData[0][1]=="4")
               									window.open("../bq/BqCheckPrintInput.jsp?OtherNo=" + ArrData[0][2],"","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes"); 
               					  else if (ArrData[0][1]=="6")
               					  	     window.open("../claim/ClaimCheckPrintSave.jsp?ClmNo=" + ArrData[0][2],"","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes"); 
        						}
      				else
        						{
  	        							return;
        						}
   				}
  }
      	
}

function afterSubmit2( FlagStr, content )
{
  showInfo.close();
	TempClassToGrid.clearData(); 
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
  
  //clearFormData();
  //initForm();
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
  else
  { 
    //var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
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
  	alert("在LLReport.js-->resetForm函数中发生异常:初始化界面错误!");
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
	window.open("./TempFeeQueryMain.jsp","","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
}

//验证字段的值
function confirm1()
{
	//2008-01-03 zy 去掉界面的空格
  //首先检验录入框
  if((document.all('ManageCom').value).length<8||(document.all('PolicyCom').value).length<8) 
  {
  	alert("收费机构和管理机构代码必须是8位");
    return false;	
  }

  var rowNum=TempToGrid.mulLineCount;
  if(rowNum>=1)
  {
   alert("请先提交已有数据");
   return false;
  }
  
  
  if (!verifyInput()) return false; 
  if(!trimNo()) return false;

  
  var typeFlag=false;	
  
  if (document.all('TempFeeType').value=="1" || document.all('TempFeeType').value=="2"||document.all('TempFeeType').value=="7")
  {
    if (document.all('AgentCode').value=='')	
    {
    	alert("代理人不能为空!");
    	return false;
    }
  }
     
  //新单交费，录入暂交费号 
  if (document.all('TempFeeType').value=="1") 
  {
  	
  	var mysql=new SqlClass();
  	
		mysql.setResourceName("finfee.TempFeeInputSql");
		mysql.setSqlId("LAAgent1");
		mysql.addSubPara(trim(document.all('AgentCode').value));
  	
	  //增加离职的代理人不能录入新单
	 	//var state = decodeEasyQueryResult(easyQueryVer3("SELECT agentstate FROM laagent where agentcode='"+trim(document.all('AgentCode').value)+"'"));
	 	
	 	var state = decodeEasyQueryResult(easyQueryVer3(mysql.getString()));
	 	
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
		/*if(document.all('CertifyFlag').value == "3")
		{
			initTempGridReadOnly6();
		}
		else
		{
      initTempGrid();
    }*/
    initTempGrid();
    initTempClassGrid();
    TempGrid.clearData("TempGrid");
    TempClassGrid.clearData("TempClassGrid"); 
    if(document.all('CertifyFlag').value == "1")
    {
    	    //校验录入
		   if (!verifyElement("投保单印刷号码|NOTNULL&len=14", document.all('InputNo1').value)) {
		      return false;
		   } 
		   if(verifyAgentCode(document.all('InputNo1').value)==false)
		   {
		    	return false;
		   }
      if (verifyElement("暂收费收据号|NOTNULL&len=14", document.all('InputNo2').value) != true) {
       return false;
       } 
           //校验单证发放
           //2008-12-23 17:50 暂时屏蔽掉单证的校验
    //  if (!verifyTempfeeNo(document.all('InputNo2').value)) 
    //   return false;  
    //2009-04-28 18:27 进行单证校验 2009-04-29 17:44 暂时屏蔽单证校验
    //if(!(verifyTempfeeNoNew("3201",document.all('InputNo2').value,document.all('AgentCode').value)))
    //	return false; 
      //add ln 2009
      //if (!verifyTempNo(document.all('InputNo2').value)) return false; 
       
      document.all('TempFeeNo').value = document.all('InputNo2').value;     
    }
    else if(document.all('CertifyFlag').value == "2")
   	{
   		 if (verifyElement("投保单印刷号码|NOTNULL&len=14", document.all('InputNoB1').value) != true) {
		      return false;
		   } 
		   if(verifyAgentCode(document.all('InputNoB1').value)==false)
		   {
		    	return false;
		   }
      if (verifyElement("银行划款协议书号|NOTNULL", document.all('InputNoB').value) != true) {
       return false;
       }
       if(document.all('InputNoB').value.trim()== document.all('InputNoB1').value.trim()) 
       {
       	 alert("银行划款协议书号与投保单印刷号一致，请重新录入银行划款协议书号！");
       	 return false;
       	}       
       //add ln 2009
      if (!verifyTempNo(document.all('InputNoB').value)) return false; 
             
       document.all('TempFeeNo').value = document.all('InputNoB').value; 
         
   	}
    else if(document.all('CertifyFlag').value == "3")
   	{
   		var Cardno=document.all('InputNoC').value.trim();
		  var CardCertifyCode=document.all('CardCode').value.trim();
		  
       if (verifyElement("单证编码|NOTNULL&code:CardCode", CardCertifyCode) != true) {
       return false;
       }    
       if (verifyElement("单证号码|NOTNULL", Cardno) != true) {
       return false;
       } 
       document.all('TempFeeNo').value = Cardno;   
       //2008-12-23 17:51 暂时屏蔽掉
       //2009-04-18 14:27 放开屏蔽的校验
    /*   if(needVerifyCertifyCode(CardCertifyCode)==true)
       {   	
		    //校验单证发放
		    //去单证状态表里查询该号码是否有效,暂交费收据号
		    var strSql = "select CertifyCode from LZCardTrack where Startno='"+Cardno+"' and Endno='"+Cardno+"'  and StateFlag='0' and CertifyCode='" + CardCertifyCode + "' "    
		               + " and length(trim(Startno))=length('"+Cardno+"') and length(trim(Endno))=length('"+Cardno+"')";
		    
		    var strResult=easyQueryVer3(strSql, 1, 1, 1);
		    
		    if(!strResult)
		    {
		      alert("该单证（单证编码为："+Cardno+" ）没有发放给该代理人（"+document.all('AgentCode').value+"）!");
		      return false;
		    }
       }*/
       //2009-04-28 18:47 进行单证校验 暂时屏蔽单证校验
      if(!(verifyTempfeeNoNew(CardCertifyCode,Cardno,document.all('AgentCode').value)))
    	return false; 
    
    //2009-04-29 15:13 按要求取消对定额单的查询显示，由操作人员自己录入
	    //显示该定额单的险种信息
	   /* var strSql = "select CertifyClass from LMCertifyDes where CertifyCode = '" + CardCertifyCode + "'";
	    var strResult = easyExecSql(strSql);
	    
	    if (strResult==null || strResult!="D") {
	      alert("该单证编码不是定额单");
	      return false;
	    }
	    
	    strSql = "select a.RiskCode, b.RiskName, a.Prem, a.PremProp, a.Premlot from LMCardRisk a, LMRisk b where a.riskcode=b.riskcode and CertifyCode = '" + CardCertifyCode + "'";
	    arrCardRisk = easyExecSql(strSql);
	
	    document.all('TempFeeNo').value = Cardno;           	
	    typeFlag = true;
	    
	    if (arrCardRisk==null) 
	    {
	    	alert("没有相关定额单描述信息！");
	    	return;
	    }
	    
	    if (arrCardRisk[0][3] == "1") {
	      initTempGridReadOnly6();
	    }
	    else {
	      initTempGrid();
	    }
	
	    for (k=0; k<arrCardRisk.length; k++) {
	      TempGrid.addOne("TempGrid");	      
	      TempGrid.setRowColData(k,1,arrCardRisk[k][0]);
	      TempGrid.setRowColData(k,2,arrCardRisk[k][1]);
	      TempGrid.setRowColData(k,3,arrCardRisk[k][2]);
	      TempGrid.setRowColData(k,4,CardCertifyCode);
	    } */	
   	}                
   else
   	{
   		alert("请选择正确单证类型");
   		return false;
   	}
    //repair:类型1：新单交费输入的是印刷号，不验证格式
    //验证格式处理，需要按照中英模式修改
    //校验单证发放
   // if (!verifyTempfeeNo(document.all('InputNo2').value)) return false;只针对暂收收据的新单
  //  if (!verifyPrtNo(document.all('InputNo1').value)) return false;保持与5.3一致的校验规则
        	
    typeFlag=true;
    fm.butConfirm.disabled = false;

    //TempGrid.setRowColData(0,7,fm.InputNob.value);
    if(document.all('CertifyFlag').value == "1")
    {
    	TempGrid.unLock();
      TempGrid.addOne("TempGrid");
      TempGrid.setRowColData(0,5,fm.InputNo1.value);
    }
    else if(document.all('CertifyFlag').value == "2")
    {
      TempGrid.unLock();
      TempGrid.addOne("TempGrid");
      TempGrid.setRowColData(0,5,fm.InputNoB1.value);
    }
    else if(document.all('CertifyFlag').value == "3")
    {
      TempGrid.unLock();
      TempGrid.addOne("TempGrid");
      TempGrid.setRowColData(0,5,document.all('CardCode').value);
    }
    else
    {
    }
    TempClassGrid.unLock();
    //如果为银行划款协议书则只显示银行类型的交费方式
    if(document.all('CertifyFlag').value == "2")
    {  
    	divTempFeeClassInput.style.display="";
    	document.all('PayMode').value='4';
    	divPayMode4.style.display="";
    }
    else
    {   	   
    	divTempFeeClassInput.style.display="";
  	}
  }
   
  //续期催收交费，录入暂交费号 
  if(document.all('TempFeeType').value=="2")
  {    	   	               
    //校验录入
    
    if (verifyElement("保单合同号码|NOTNULL", document.all('InputNo3').value) != true) {
      return false;
    } 
    if (verifyElement("交费通知书号码|NOTNULL", document.all('InputNo4').value) != true) {
      return false;
    } 
    //校验录入代理人是否和保单代理人一致
    if(verifyAgent(document.all('InputNo3').value)==false)
    {
    	return false;
    }
    //校验录入合同号和收费机构是否一致
    if(verifyContNo(document.all('InputNo3').value)==false)
    {
    	return false;
    } 
    //校验合同的状态
    if(verifyContState(document.all('InputNo3').value)==false)
    {
    	return false;
    }     
    

    
    //如果录入了3209续期暂收费收据号，则校验单证是否已经发放到代理人手中，如果还没有发放到代理人手中，不准许保存财务信息；
//2008-12-23 17:52暂时屏蔽  
 /*   if(!((document.all('XQCardNo').value==null)||(document.all('XQCardNo').value=="")))
    {

    	 //校验单证发放

	    var strSql = "select CertifyCode from LZCardTrack where Startno<='"+document.all('XQCardNo').value+"' and Endno>='"+document.all('XQCardNo').value+"' and Receivecom like 'D%%' and StateFlag='0' and CertifyCode='3209'";        
      var strResult=easyQueryVer3(strSql, 1, 1, 1);
      if(!strResult) 
      {
         alert("该单证（单证编码为："+document.all('XQCardNo').value+" ）还没有发放给该代理人!");
        	return false;
      }
    }*/
    // 2009-04-28 18:47 进行单证校验 暂时屏蔽单证校验
 
   //续期暂收费收据录入调整校验，支持新单证类型编码531001的录入
    if(!((document.all('XQCardNo').value==null)||(document.all('XQCardNo').value=="")))
    {
    	if(!(verifyTempfeeNoNew("531001",document.all('XQCardNo').value,document.all('AgentCode').value)))
    	{
	     if(!(verifyTempfeeNoNew("3209",document.all('XQCardNo').value,document.all('AgentCode').value)))
	    	{
	    		return false; 
	    	}
	        fmSave.all('CERTIFY_XQTempFee').value ="3209";
	    }
    	
    	else 
    	{
    			fmSave.all('CERTIFY_XQTempFee').value ="531001";
    	}
    }

    var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
    var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	initTempGridReadOnly();
    initTempClassGrid();       
    document.getElementById("fm").submit();//后台可以根据暂交费类型判断
    typeFlag=true;         	
    divTempFeeClassInput.style.display="";       	
  }
   
  //续期非催收交费，生成暂交费号 
  if(document.all('TempFeeType').value=="8")
  {	     
    //校验录入
    //if (verifyElement("保单号码|NOTNULL&len=15", document.all('InputNo5').value) != true) {
    if (verifyElement("保单合同号码|NOTNULL", document.all('InputNo5').value) != true) { 
      return false;
    } 
    initTempGrid();
    
      var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
      var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
      var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	fmTypeQuery.all('QueryNo').value = document.all('InputNo5').value;
      //var strSql = "select * from lcgrpcont where grpcontno = '" + document.all('InputNo5').value + "'";
      var strSql=wrapSql(tResourceName,'LCGrpCont1',[document.all('InputNo5').value]);
      var arrResult = easyExecSql(strSql);
      if(arrResult != null)                //集体合同号
      {
          fmTypeQuery.all('QueryType').value = "1";
          fmSave.all('NewQueryType').value = "1";
      }
      else
      {
    	  fmTypeQuery.all('QueryType').value = "2";
    	  fmSave.all('NewQueryType').value = "2";
      }
      document.getElementById("fmTypeQuery").submit();//去后台查询该号码是否合格       	
        typeFlag=true;         	
      divTempFeeClassInput.style.display="";    
   
  } 
   
  //保全交费，录入交费收据号
  if(document.all('TempFeeType').value=="4")
  {  	
    //initTempGrid();
    //initTempClassGrid();
    //initTempGridReadOnlyDo();
    initTempClassGrid();
    //校验录入
    if (verifyElement("保全受理号|NOTNULL", document.all('InputNo7').value) != true) {
      return false;
    } 
    if (verifyElement("交费收据号码|NOTNULL", document.all('InputNo8').value) != true) {
      return false;
    }

    //类型4：保全交费，录入交费收据号（31）验证格式   
    //验证格式：提交查询时
    var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
    var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	//initTempGridReadOnly();
    //保全交费不显示险种编码及险种名称
    initTempGridReadOnlyCont();
    //initTempClassGrid();          
    document.getElementById("fm").submit();//后台可以根据暂交费类型判断 
    divTempFeeClassInput.style.display="";
    typeFlag=true;   		
  }
   
   //预存保费 
   if(document.all('TempFeeType').value=="5")
   {
   	document.all('PolicyCom').value=document.all('ManageCom').value;        	        
    initTempGridReadOnlyDo();
    initTempClassGrid(); 
    //校验录入
    if (verifyElement("客户号码|NOTNULL", document.all('InputNo9').value) != true) {
      return false;
    } 
		//var strSql = "select GrpName from LDGrp where CustomerNo='"+fm.InputNo9.value+"'";
	  var strSql=wrapSql(tResourceName,'LDGrp1',[fm.InputNo9.value]);
	  var arrResult = easyExecSql(strSql);
	  if (arrResult==null)
		{
			alert("无此单位客户号码");
			return false;
		}
  else
  	{
    TempGrid.setRowColData(0,1,"000000");
    TempGrid.setRowColData(0,2,"0000000000");
    TempGrid.setRowColData(0,3,"");
    TempGrid.setRowColData(0,4,"");
    TempGrid.setRowColData(0,5,document.all('InputNo9').value);
    TempGrid.setRowColData(0,6,arrResult[0][0]);
    divTempFeeClassInput.style.display="";
    typeFlag=true;         	
    }
   }
  if(document.all('TempFeeType').value=="6")
  {  	
    //校验录入
    if(document.all('ClaimFeeType').value=="1")
    {
    	if (verifyElement("赔案号|NOTNULL", document.all('InputNo12').value) != true) {
	      return false;
	    } 
	    if (verifyElement("交费收据号码|NOTNULL", document.all('InputNo11').value) != true) {
	      return false;
	    }
  	}
    //校验录入
    if(document.all('ClaimFeeType').value=="2")
    {
    	if (verifyElement("赔案号|NOTNULL", document.all('InputNoH12').value) != true) {
	      return false;
	    } 
	    if (verifyElement("交费收据号码|NOTNULL", document.all('InputNoH11').value) != true) {
	      return false;
	    }
  	}

    //类型4：保全交费，录入交费收据号（31）验证格式   
    //验证格式：提交查询时
    var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
    var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	if(document.all('ClaimFeeType').value=="2")
    {
    	initTempGridReadOnlyCont();
  	}
    if(document.all('ClaimFeeType').value=="1")
    {
    	initTempGridReadOnly();
  	}
    //initTempClassGrid();          
    document.getElementById("fm").submit();//后台可以根据暂交费类型判断 
    divTempFeeClassInput.style.display="";
    typeFlag=true;
  }   
   //卡单结算
//   if(document.all('TempFeeType').value=="9")
//   {     	        
//    //校验录入
//    if (verifyElement("结算号|NOTNULL", document.all('InputNo99').value) != true) 
//    {
//      return false;
//    }
//    
//    var calsql="select * from ljtempfee where otherno='"+document.all('InputNo99').value+"' and tempfeetype='9'";
//    var casResult=easyExecSql(calsql);
//    if(casResult!=null && casResult.length>0)
//		{
//			alert("已经缴费不能再次缴纳");
//			return ;
//		} 
//     
//   	var strSql = "select SumPrem,managecom from LXbalance where BalanceNo='"+document.all('InputNo99').value+"'"; 	
//   	var TarrResult = easyQueryVer3(strSql, 1, 1, 1);
//   	if(TarrResult == null ||TarrResult == ""||TarrResult=='null')
//   	{
//    		alert("此结算号码不存在");
//    		return false;	
//   	}	
//    else
//    {
//    	var ArrData = decodeEasyQueryResult(TarrResult);
//	  	if(ArrData[0][0]=="")
//	  	{
//	  	  alert("未查到结算金额信息");	
//	  	}
//	  	if(ArrData[0][1]=="")
//	  	{
//	  	  alert("未查到保单管理机构");	
//	  	}	 	
//    }	
//   	var M = arrResult;
//    initTempClassGrid();
//    initBalanceGrid(); 
//		TempGrid.addOne("TempGrid");    
//    
//    TempGrid.setRowColData(0,1,"000000");           
//    TempGrid.setRowColData(0,2,"请选择险种");
//    TempGrid.setRowColData(0,3,pointTwo(ArrData[0][0]));
//    TempGrid.setRowColData(0,4,document.all('InputNo99').value);
//		fm.PolicyCom.value = ArrData[0][1];
//		fm.TempFeeNo.value = document.all('InputNo19').value;
//    divTempFeeClassInput.style.display="";
//    typeFlag=true;         	
//
//   }
   
  if (document.all('TempFeeType').value=="3")
  {
    if (document.all('InputNo13').value=='')	
    {
    	alert("保单合同号不能为空!");
    	return false;
    }
    if (document.all('InputNo14').value=='')	
    {
    	alert("预收收据号不能为空!");
    	return false;
    }
    //var state=decodeEasyQueryResult(easyQueryVer3("SELECT count(*) FROM lcpol where contno='"+trim(document.all('InputNo13').value)+"' and mainpolno=polno and grppolno='00000000000000000000' and payintv>0 and exists (select riskcode from lmriskapp where riskcode=lcpol.riskcode and RiskPeriod='L')"));
		var state=decodeEasyQueryResult(easyQueryVer3(wrapSql(tResourceName,'LCPol1',[document.all('InputNo13').value])));
		


	  if(parseInt(state[0][0])==0)
	  {
	  	//var gstate=decodeEasyQueryResult(easyQueryVer3("SELECT count(*) FROM lcgrppol where grpcontno='"+trim(document.all('InputNo13').value)+"' and payintv>0 and exists (select riskcode from lmriskapp where riskcode=lcgrppol.riskcode and RiskPeriod='L' and subriskflag='M')"));
	  	var gstate=decodeEasyQueryResult(easyQueryVer3(wrapSql(tResourceName,'LCGrpPol1',[document.all('InputNo13').value])));
	  	
	  	
	  	if(parseInt(gstate[0][0])==0)
	  	{
				alert("只有期交长险才能做预收操作!");
				return false;
		  }
	  }
	
	  //var strSql="select RiskCode,(select riskname from lmrisk where riskcode=lcpol.riskcode),'0.0',contno,GrpPolNo from lcpol where grppolno='00000000000000000000' and appflag='1' and mainpolno=polno and contno='"+document.all('InputNo13').value+"' and managecom='"+document.all('ManageCom').value+"' and agentcode='"+document.all('AgentCode').value+"'";
		var strSql=wrapSql(tResourceName,'LMRisk1',[document.all('InputNo13').value,document.all('ManageCom').value,document.all('AgentCode').value])
   	var strResult = easyExecSql(strSql);
   	if (strResult==null)
   	{
   		//var pstrSql="select RiskCode,(select riskname from lmrisk where riskcode=lcgrppol.riskcode),'0.0',GrpContNo,'' from lcgrppol where  appflag='1'  and GrpContNo='"+document.all('InputNo13').value+"' and managecom='"+document.all('ManageCom').value+"' and agentcode='"+document.all('AgentCode').value+"'";
   		strResult = easyExecSql(wrapSql(tResourceName,'LCGrpPol2',[document.all('InputNo13').value,document.all('ManageCom').value,document.all('AgentCode').value]));
   		if(strResult==null)
   		{
	   		alert("未查询到该保单!");
	   		return;
   	  }
   	}
   	
	     //续期暂收费收据录入调整校验，支持新单证类型编码531001的录入
    if(!((document.all('InputNo14').value==null)||(document.all('InputNo14').value=="")))
    {
    	if(!(verifyTempfeeNoNew("531001",document.all('InputNo14').value,document.all('AgentCode').value)))
    	{
	     if(!(verifyTempfeeNoNew("3209",document.all('InputNo14').value,document.all('AgentCode').value)))
	    	{
	    		return false; 
	    	}
	        fmSave.all('CERTIFY_XQTempFee').value ="3209";
	    }
    	
    	else 
    	{
    			fmSave.all('CERTIFY_XQTempFee').value ="531001";
    	}
    }
	  
   	initTempGridYS();
    for (k=0; k<strResult.length; k++) 
    {
	      TempGrid.addOne("TempGrid");	      
	      TempGrid.setRowColData(k,1,strResult[k][0]);
	      TempGrid.setRowColData(k,2,strResult[k][1]);
	      TempGrid.setRowColData(k,4,strResult[k][2]);
	      TempGrid.setRowColData(k,5,strResult[k][3]);
    	}
	  document.all('TempFeeNo').value=document.all('InputNo14').value;
    typeFlag=true;
    divTempFeeClassInput.style.display="";
 }
     
  
   if(typeFlag==true) 
     confirmFlag=true;
   else
     confirmFlag=false;
}


//添加一笔纪录

function addRecord()
{
	//alert(TempClassGrid.getRowColData(i,6));
   if(document.all('TempFeeType').value=="1" && (TempGrid.getRowColData(0,1)=="141814" || TempGrid.getRowColData(0,1)=="141815")){
  	alert("该险种为卡单业务险种，请在卡单财务收费中录入");
 		return false;
   }

   if(confirmFlag==false){alert("请单击确定");return;}//如果没有单击确定，提示

   if(!checkPubValue())  return;

   if(!checkGridValue()) return;

   if(!checkTempRecord()) return;

   if(!checkTempClassRecord()) return;
   

  //判断暂交费信息中的交费金额是否和暂交费分类信息中的交费金额相等
 //  tempClassFee=tempClassFee/1000000000;
 //  tempFee=tempFee/1000000000;   
  var sqlClasscheck = wrapSql(tResourceName,"querysqldes1",[tempClassFee]);  
  var arrResultClass = easyExecSql(sqlClasscheck);  
  
  var sqlcheck = wrapSql(tResourceName,"querysqldes1",[tempFee]);
  var arrResult = easyExecSql(sqlcheck);
  
  var returnFlag=true;
  //alert(arrResult.length);
  if(arrResultClass!=null && arrResult!=null && arrResultClass.length==arrResult.length)
  {
  	for(i=0;i<arrResult.length;i++)
  	{
  		if(arrResult[i][0]==arrResultClass[i][0] && pointTwo(arrResult[i][1])==pointTwo(arrResultClass[i][1]) && arrResult[i][1]!='')
  		{
  			if(arrResult[i][0]!='0')
  			{
  			addAction = addAction+1;
		      //sumTempFee = sumTempFee+arrResult[i][1];
		      document.all('TempFeeCount').value = addAction;
		      //document.all('SumTempFee').value = pointTwo(sumTempFee);
		      SumTempGrid.addOne("SumTempGrid");
     		  SumTempGrid.setRowColData(SumTempGrid.mulLineCount-1,1,arrResult[i][0]);
     		  SumTempGrid.setRowColData(SumTempGrid.mulLineCount-1,2,arrResult[i][1]);
		      tempClassFee="";
		      tempFee="";
		      
		      returnFlag=true;
		    }
  		}
  		else
  		{
  			returnFlag=false;
  			break;
  		}
  	}  	
  }
  else
  	returnFlag=false;
  	
  if(returnFlag==false)
  {
  	tempClassFee="";
	tempFee="";
	SumTempGrid.clearData();
	 alert("金额不等或缺少数据！");		
	 return ;
  }
  
   /*
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
   }*/
  
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
     TempClassToGrid.setRowColData(TempClassToGrid.mulLineCount-1,1,document.all('TempFeeNo').value.trim());
     TempClassToGrid.setRowColData(TempClassToGrid.mulLineCount-1,2,TempClassGrid.getRowColData(i,1));
     TempClassToGrid.setRowColData(TempClassToGrid.mulLineCount-1,3,document.all('PayDate').value.trim());
     TempClassToGrid.setRowColData(TempClassToGrid.mulLineCount-1,4,TempClassGrid.getRowColData(i,3));
     TempClassToGrid.setRowColData(TempClassToGrid.mulLineCount-1,5,TempClassGrid.getRowColData(i,4));
     TempClassToGrid.setRowColData(TempClassToGrid.mulLineCount-1,6,TempClassGrid.getRowColData(i,7));
     TempClassToGrid.setRowColData(TempClassToGrid.mulLineCount-1,7,document.all('PolicyCom').value.trim());
     TempClassToGrid.setRowColData(TempClassToGrid.mulLineCount-1,8,TempClassGrid.getRowColData(i,5)); 
     TempClassToGrid.setRowColData(TempClassToGrid.mulLineCount-1,9,TempClassGrid.getRowColData(i,6));     
     TempClassToGrid.setRowColData(TempClassToGrid.mulLineCount-1,10,TempClassGrid.getRowColData(i,8));     
     TempClassToGrid.setRowColData(TempClassToGrid.mulLineCount-1,11,TempClassGrid.getRowColData(i,9));     
     TempClassToGrid.setRowColData(TempClassToGrid.mulLineCount-1,12,TempClassGrid.getRowColData(i,10));     
     TempClassToGrid.setRowColData(TempClassToGrid.mulLineCount-1,13,TempClassGrid.getRowColData(i,11));     
     TempClassToGrid.setRowColData(TempClassToGrid.mulLineCount-1,14,TempClassGrid.getRowColData(i,12));     
     TempClassToGrid.setRowColData(TempClassToGrid.mulLineCount-1,15,TempClassGrid.getRowColData(i,13));
     TempClassToGrid.setRowColData(TempClassToGrid.mulLineCount-1,19,TempClassGrid.getRowColData(i,17));
     TempClassToGrid.setRowColData(TempClassToGrid.mulLineCount-1,20,TempClassGrid.getRowColData(i,18));
     TempClassToGrid.setRowColData(TempClassToGrid.mulLineCount-1,21,TempClassGrid.getRowColData(i,19));
              
     if(trim(TempClassGrid.getRowColData(i,7))=="") //如果有交费项的到帐日期为空，那么标志置0
        EnterAccDateFlag=0;
     
     if(compareDate(EnterAccDate,TempClassGrid.getRowColData(i,7))==2)
       EnterAccDate=TempClassGrid.getRowColData(i,7);

    }

  }
   fmSave.all('CertifyFlagHidden').value=document.all('CertifyFlag').value;
   fmSave.all('ClaimFeeTypeHidden').value=document.all('ClaimFeeType').value;
   
   
  if(EnterAccDateFlag==0)
    EnterAccDate="";
   

     
  //暂交费信息赋给用于提交数据的Grid
  for(i=0;i<TempRecordCount;i++)
  {
    if(TempGrid.getRowColData(i,1)!='')  //如果险种编码为空，则跳过
     {
       TempToGrid.addOne("TempToGrid");//当前文件中的对象，不用加前缀
       TempToGrid.setRowColData(TempToGrid.mulLineCount-1,1,document.all('TempFeeNo').value.trim());
       TempToGrid.setRowColData(TempToGrid.mulLineCount-1,2,document.all('TempFeeType').value.trim());
       TempToGrid.setRowColData(TempToGrid.mulLineCount-1,3,document.all('PayDate').value.trim());          
       TempToGrid.setRowColData(TempToGrid.mulLineCount-1,4,TempGrid.getRowColData(i,3));
       TempToGrid.setRowColData(TempToGrid.mulLineCount-1,5,TempGrid.getRowColData(i,4));
       TempToGrid.setRowColData(TempToGrid.mulLineCount-1,6,EnterAccDate);          
       TempToGrid.setRowColData(TempToGrid.mulLineCount-1,7,document.all('PolicyCom').value.trim());       
       TempToGrid.setRowColData(TempToGrid.mulLineCount-1,8,TempGrid.getRowColData(i,1));          
       TempToGrid.setRowColData(TempToGrid.mulLineCount-1,9,document.all('AgentGroup').value.trim());
       TempToGrid.setRowColData(TempToGrid.mulLineCount-1,10,document.all('AgentCode').value.trim());               
       if (document.all('TempFeeType').value=="1") {
       	 TempToGrid.setRowColData(TempToGrid.mulLineCount-1,11,TempGrid.getRowColData(0,5));                  
     		 TempToGrid.setRowColData(TempToGrid.mulLineCount-1,13,TempGrid.getRowColData(i,6));     
     		 TempToGrid.setRowColData(TempToGrid.mulLineCount-1,14,TempGrid.getRowColData(i,7));   
       	 //产品组合的校验对MS不适用
  	     	        	 
       }
       else if (document.all('TempFeeType').value=="2"){
         TempToGrid.setRowColData(TempToGrid.mulLineCount-1,11,TempGrid.getRowColData(i,5)); 
         var ReturnNoType=getCodeType(TempGrid.getRowColData(i,5));
         //OtherNoType的处理放在后台处理
         	TempToGrid.setRowColData(TempToGrid.mulLineCount-1,15, document.all('XQCardNo').value.trim());	//续期催收交费录入暂收费收据号码
         } 
         //预收保费
        else if (document.all('TempFeeType').value=="3")
       	{
       		TempToGrid.setRowColData(TempToGrid.mulLineCount-1,12,"4");     
       		TempToGrid.setRowColData(TempToGrid.mulLineCount-1,11,document.all('InputNo13').value.trim()); 
       	}     
       else if (document.all('TempFeeType').value=="5")
       	{ 
       	 	 TempToGrid.setRowColData(TempToGrid.mulLineCount-1,11,TempGrid.getRowColData(i,5));
       	 	 TempToGrid.setRowColData(TempToGrid.mulLineCount-1,16,TempGrid.getRowColData(i,7));
       	 	 TempToGrid.setRowColData(TempToGrid.mulLineCount-1,17,TempGrid.getRowColData(i,8));
       	 	 TempToGrid.setRowColData(TempToGrid.mulLineCount-1,12,"5");
       	}
       else if (document.all('TempFeeType').value=="6")
       	{
       		TempToGrid.setRowColData(TempToGrid.mulLineCount-1,12,"5");
       		TempToGrid.setRowColData(TempToGrid.mulLineCount-1,11,TempGrid.getRowColData(i,5));
       	}
    //   else if (document.all('TempFeeType').value=="9")
    //   	{
    //   		TempToGrid.setRowColData(TempToGrid.mulLineCount-1,11,"04");     
    //   		TempToGrid.setRowColData(TempToGrid.mulLineCount-1,10,TempGrid.getRowColData(i,4));                          		      	               
    //   	}
       else{
       	 TempToGrid.setRowColData(TempToGrid.mulLineCount-1,11,TempGrid.getRowColData(i,5));
       }
     }
  }
  fm.PayMode.value = '';
  fm.PayModeName.value = '';
  initTempGrid();
  initTempClassGrid();
  divTempFeeClassInput.style.display="none";
  fm.butAdd.disabled =false;
  initDivPayMode();//初始化divPayMode
  initInput();  //提交后清空之前录入的内容
  confirmFlag=false; //复位，需重新单击确定
}

function initInput(){
   try{
   		fm.Currency1.value ='';
       fm.PayFee1.value ='';
       fm.Currency2.value ='';
       fm.PayFee2.value ='';
       fm.ChequeNo2.value ='';
       fm.ChequeDate2.value='';
       fm.BankCode2.value ='';
       fm.BankCode2Name.value ='';
       fm.BankAccNo2.value ='';
       fm.AccName2.value ='';
       fm.Currency3.value ='';
       fm.PayFee3.value ='';
       fm.ChequeNo3.value ='';
       fm.ChequeDate3.value ='';
       fm.BankCode3.value ='';
       fm.BankCode3Name.value ='';
       fm.BankAccNo3.value ='';
       fm.AccName3.value ='';
       fm.Currency4.value ='';
       fm.PayFee4.value ='';
       fm.BankCode4.value ='';
       fm.BankCode4Name.value ='';
       fm.BankAccNo4.value ='';
       fm.AccName4.value ='';
       fm.IDType.value ='';
       fm.IDNo.value ='';
       fm.ChequeNo5.value ='';
       fm.OtherNo5.value ='';
       fm.Currency5.value ='';
       fm.PayFee5.value ='';
       fm.Drawer5.value ='';
       fm.Currency6.value ='';
       fm.PayFee6.value ='';
       fm.BankCode6.value ='';
       fm.BankCode6Name.value ='';
       fm.BankAccNo6.value ='';
       fm.AccName6.value ='';
       fm.IDTypeName.value ='';
       fm.CashType.value='';
       fm.CashTypeName.value='';
     }catch(ex){}
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
//检查MulLine的信息域的数据格式是否合格
function checkGridValue()
{

  var arrCardMoney = new Array();
  var allMoney = 0;
  if(TempGrid.checkValue("TempGrid")&&TempClassGrid.checkValue("TempClassGrid"))
  { 
    for(var n=0;n<TempGrid.mulLineCount;n++)
     {//MS支持多主险，所以收费支持多主险收费     
       for(var m=n+1;m<TempGrid.mulLineCount;m++)
       {
         if(TempGrid.getRowColData(n,1)==TempGrid.getRowColData(m,1) && TempGrid.getRowColData(n,3)==TempGrid.getRowColData(m,3))
          {
             alert("不能录入重复的险种编码");	
             return false;
          }
       }
       //定额单的校验 2009-04-29 15:57 取消对定额单的校验
     /*  if (document.all('TempFeeType').value=="1" && document.all('CertifyFlag').value=="3") {  
         //校验险种
         var isCardRisk = false;
         for (var k=0; k<arrCardRisk.length; k++) {
          if (TempGrid.getRowColData(n,1) == arrCardRisk[k][0]) {
            isCardRisk = true;
          }
         }        
         if (!isCardRisk) {
          alert("该险种" + TempGrid.getRowColData(n,1) + "不是该类单证包含的险种！");
          return false;
         }
         
         //保存险种和金额
         arrCardMoney[n] = new Array(TempGrid.getRowColData(n,1), TempGrid.getRowColData(n,3));
         allMoney = allMoney + parseInt(TempGrid.getRowColData(n,3));     
       }*/   
     }  
     
   /*  if (document.all('TempFeeType').value=="1"&& document.all('CertifyFlag').value=="3") {     
       //校验金额比例
       if (arrCardRisk[0][3] != "1") {
         for (var i=0; i<arrCardRisk.length; i++) {

           for (var j=0; j<arrCardMoney.length; j++) {
             //找到相同险种
             if (arrCardRisk[i][0] == arrCardMoney[j][0]) {
               //校验倍数
               if (arrCardRisk[0][3]=="2" && (arrCardMoney[j][1]%arrCardRisk[i][2] != 0)) {
                 alert("该险种（" + arrCardMoney[j][0] + "）的交费金额必须是设定金额（" + arrCardRisk[i][2] + "）的倍数！");
                 return false;
               }
               if (arrCardRisk[0][3]=="3" && (arrCardMoney[j][1]/allMoney != arrCardRisk[i][4])) {
                 alert("该险种（" + arrCardMoney[j][0] + "）的交费金额比例（" + arrCardRisk[i][4] + "）错误！");
                 return false;
               }
             }
           }
         }
       } 
     } */    
     return true;      	
   }
  return false; 
}

//添加一笔纪录前，检查添加的暂交费信息数据是否已经存在于保存数据的Grid中，并计算交费金额累计
function checkTempRecord()
{
  //alert("check3");
  var TempRecordCount = 0;
  var TempClassGridCount = 0;
  tempFee="";
  var i = 0;
  TempRecordCount=TempGrid.mulLineCount; //TempGrid的行数 
  TempToGridCount=TempToGrid.mulLineCount;//TempToGrid的行数
    
	for(i=0;i<TempRecordCount;i++)
	{
		  if(TempGrid.getRowColData(i,4)<=0)
    	{
    		alert("录入的交费金额应该为大于0的数据，请重新录入");
    		return false;
    	} 
	    if (document.all('TempFeeType').value=="1")
	    {
		    if(TempGrid.getRowColData(i,6)==''||TempGrid.getRowColData(i,6)==null)
		     {
	          alert("请录入交费间隔");
	          return false;	
		     }
		    	
		    if((TempGrid.getRowColData(i,7)==''||TempGrid.getRowColData(i,7)==null))
		     {
		        alert("请录入交费期间");
		        return false;	
		     }
		     	    //zy 2009-04-17 16:43 增加对录入交费期间和交费间隔校验
			  if(TempGrid.getRowColData(i,6)!='' && (!isInteger(TempGrid.getRowColData(i,6))))
				{
				        alert("交费间隔一栏，请录入数字！");
				        return false;	
				}
			  if(TempGrid.getRowColData(i,7)!='' && (!isInteger(TempGrid.getRowColData(i,7))))
				{
				        alert("交费期间一栏，请录入数字！");
				        return false;	
				}	    
	    }

	}

  for(i=0;i<TempRecordCount;i++)
  {
    if(TempGrid.getRowColData(i,1)!='')  //如果险种编码为空，则跳过
     {
     	 for(var n=0;n<TempToGridCount;n++)
       {         //检查是否有相同的暂交费号。0表示后台自动产生
	        if(TempToGrid.getRowColData(n,1)==document.all('TempFeeNo').value)
	        {
	          alert("发现重复数据:暂交费号相同");
	          return false;
	        }      
       }   	     	      
       //tempFee = tempFee+1000000000*parseFloat(TempGrid.getRowColData(i,3));//交费金额累计   
       //tempFee = Arithmetic(tempFee,'+',TempGrid.getRowColData(i,4),2);
       tempFee = tempFee + "select '"+TempGrid.getRowColData(i,3)+"' code,'"+TempGrid.getRowColData(i,4)+"' money from dual union all ";
     }   	
  }
     return true;  	
}

//添加一笔纪录前,计算添加的暂交费分类信息数据交费金额累计
function checkTempClassRecord()
{

  var TempClassRecordCount = 0;
  tempClassFee="";
  var i = 0;
  TempClassRecordCount=TempClassGrid.mulLineCount; //页面上纪录的行数 
  var paramTemp=''; 
  for(i=0;i<TempClassRecordCount;i++)
  {
    if(TempClassGrid.getRowColData(i,1)!='')  //如果交费方式为空，则跳过
    {
    	if(TempClassGrid.getRowColData(i,4)<=0)
    	{
    		alert("录入的交费金额应该为大于0的数据，请重新录入");
    		return false;
    	}
      //tempClassFee = tempClassFee+1000000000*parseFloat(TempClassGrid.getRowColData(i,3));//交费金额累计
      //tempClassFee = Arithmetic(tempClassFee,'+',TempClassGrid.getRowColData(i,4),2);
      tempClassFee = tempClassFee + "select '"+TempClassGrid.getRowColData(i,3)+"' code,'"+TempClassGrid.getRowColData(i,4)+"' money from dual union all ";
      if(trim(TempClassGrid.getRowColData(i,7))!='')//如果到帐日期不为空，那么判断是否大于等于当前日期
      {
	      	if(isDate(trim(TempClassGrid.getRowColData(i,7))))//如果日期格式正确
	      	{
	      		if(compareDate(trim(TempClassGrid.getRowColData(i,7)),getCurrentDate())==1)//如果大于当前日期
	      		{
	      		  alert("到账日期不能超过今天！");
	      		  return false;
	      		}
	      	}
	      	else
	      	{
	      	  alert("到账日期格式不对！");
	      	  return false;
	      	}
	     }
	
	      if(TempClassGrid.getRowColData(i,1)=='5')  //如果交费方式为5-内部转账，则票据号码不能为空
	      {
	        if(trim(TempClassGrid.getRowColData(i,5))==''||trim(TempClassGrid.getRowColData(i,7))=='')
	          {
		           alert("内部转账时，票据号码和到账日期不能为空");
		           return false;
	          }
	      }
	
	      if(TempClassGrid.getRowColData(i,1)=='4')  //如果交费方式为4-银行转账，必须填写 开户银行,银行账号,户名并且到帐日期必须为空
	      {
	        if(trim(TempClassGrid.getRowColData(i,8))==''||trim(TempClassGrid.getRowColData(i,9))==''||trim(TempClassGrid.getRowColData(i,10))=='')
	          {
		           alert("银行转账时，必须填写 开户银行,银行账号,户名");
		           return false;
	          }
	          
	        if (trim(TempClassGrid.getRowColData(i,8))=="0101") {
	          if (trim(TempClassGrid.getRowColData(i,9)).length!=19 || !isInteger(trim(TempClassGrid.getRowColData(i,9)))) {
	            alert("工商银行的账号必须是19位的数字，最后一个星号（*）不要！");
	            return false;
	            }
           }
	        TempClassGrid.setRowColData(i,7,'');//  将到帐日期置空
	        
	        if (document.all('TempFeeType').value=="1" && document.all('CertifyFlag').value=="1") 
	        {
              alert("该种暂交费类型（" + document.all('TempFeeTypeName').value + "）不允许使用银行转账的交费方式，请选择银行划款协议书的单证类型！");
             return false;
	        }
	      }
	//如果为现金支票则不加控制
	      if(TempClassGrid.getRowColData(i,1)=='3')  //如果交费方式为2||3-支票类，则票据号码和银行不能为空
	      {
	        if(trim(TempClassGrid.getRowColData(i,5))==''||trim(TempClassGrid.getRowColData(i,8))=='')
	          {
	           alert("交费方式为支票时，票据号码和银行不能为空");
	           return false;
	          }
	      }
	      
	      if(TempClassGrid.getRowColData(i,1)=='6')
	      {
	      		if(trim(TempClassGrid.getRowColData(i,8))=='')
	          {
	           alert("交费方式为银行托收时，银行编码不能为空");
	           return false;
	          }
	       }
	      
	       
	     if (document.all('TempFeeType').value=="3"&&TempClassGrid.getRowColData(i,1)!='1') 
	     {   
     		  alert("暂交费类型为预收交费时,交费方式只能为现金!");  
     			return false;   
     	 } 
     	
     	if (document.all('TempFeeType').value=="2" || document.all('TempFeeType').value=="4") 
	    {
	     	 if(TempClassGrid.getRowColData(i,1)=='4')
	     	 {   
	     			alert("该种暂交费类型不允许使用银行转账的交费方式,请核实！");  
	     			return false;   
     		 }
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
  initSumTempGrid();
  initFee();
  initDivPayMode();
  document.all('TempFeeNo').valur="";  
  document.all('TempFeeCount').value=0;
  //document.all('SumTempFee').value=0.0 ;
  divTempFeeSave.style.display="none";
  divTempFeeInput.style.display="";
  TempGrid.lock();
}


//初始化全局变量
function initGlobalData()
{
//存放添加动作执行的次数
 addAction = 0;
//暂交费总金额
 //sumTempFee = 0.0;
//暂交费信息中交费金额累计
 tempFee = "";
//暂交费分类信息中交费金额累计
 tempClassFee = "";
 confirmFlag=false; //复位，需重新单击确定 
}


/*********************************************************************
 *  选择暂交费类型后的动作
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function afterCodeSelect(cCodeName, Field) 
{
	if(cCodeName == "FINAbank" ){
		if(divPayMode2.style.display == ""){
				checkBank(document.all('BankCode2'),document.all('BankAccNo2'));
		}
		if(divPayMode3.style.display == ""){
				checkBank(document.all('BankCode3'),document.all('BankAccNo3'));
		}
		if(divPayMode4.style.display == ""){
				checkBank(document.all('BankCode4'),document.all('BankAccNo4'));
		}
		if(divPayMode6.style.display == ""){
				checkBank(document.all('BankCode6'),document.all('BankAccNo6'));
		}
  }
	if(cCodeName == "TempFeeType") 
	{  
	  showTempFeeTypeInput(Field.value);
	}
	if(cCodeName == "chargepaymode")
	{
		showTempClassInput(Field.value);
		PayModePrem();
	}

	if(cCodeName=="certifyflag"){
  	afterSelectCode(Field.value);
  	}

	if(cCodeName=="ClaimFeeType"){
  	afterSelectCode(Field.value);
  	}
  	
  if(fm.CertifyFlag9.value=="2")
  {
  	 fm.InputNo19.value = fm.InputNo99.value;
  	 fm.InputNo19.disabled = true;
  }
	else
  {
  	 fm.InputNo19.value = "";
  	 fm.InputNo19.disabled = false;
  }
  if(cCodeName =="chargepaymode"){
    //初始化
    initInput();
  }
  //设置币种金额的显示格式
    if(cCodeName=="currency"){
    	for(i=0;i<=6;i++)
    	{
    		var scurrency=document.all('Currency'+i).value;
    		if(scurrency!=null && scurrency!="")
    		{
    			document.all('PayFee'+i).moneytype=scurrency;
    		}
    	}    	
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
	for(i=0;i<=6;i++)
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


/*********************************************************************
 *  根据选择不同的暂交费类型  ，初始化页面
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function showTempFeeTypeInput(type) 
{         
    document.all('divCertifyFlag1').style.display='none';
    document.all('divCertifyFlag2').style.display='none';
    document.all('divCertifyFlag3').style.display='none';
    initFee();
    for (i=0; i<9; i++) {
    if ((i+1) == type) {
      document.all("TempFeeType" + (i+1)).style.display = '';
    }
    else {
      document.all("TempFeeType" + (i+1)).style.display = 'none';
    }
  }
}


function queryLJSPay()
{
   if(document.all('InputNo4').value!="")
   {
    	window.open("./FinFeeGetQueryLJSPayMain.jsp?Getnoticeno="+document.all('InputNo4').value,"","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
   }
   else
   {
      window.open("./FinFeeGetQueryLJSPayMain.jsp","","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
   }	
}

function queryLJSPayEdor()
{
   if(document.all('InputNo7').value="")
   {
   alert("保全受理号不能为空");
   return false;
   }
   else
   {
    window.open("./FinFeeGetQueryLJSPayEdorMain.jsp?PayDate=" + document.all('PayDate').value,"","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");			
   }	
}
function queryLJSPayClaim ()
{
   if(document.all('InputNo11').value!="")
   {
    	  //var strSql = "select otherno from ljspay where getnoticeno='"+fm.InputNo11.value+"'";
        var strSql =wrapSql(tResourceName,'LJSPay1',[fm.InputNo11.value,'']);
        var arrResult = easyExecSql(strSql);     	
        fm.InputNo12.value=arrResult;
   }
   else if (document.all('InputNo12').value!="")
   {
	     //var strSql = "select getnoticeno from ljspay where otherno='"+fm.InputNo12.value+"'";
	     var strSql =wrapSql(tResourceName,'LJSPay1',['',fm.InputNo12.value]);
       var arrResult = easyExecSql(strSql);     	
       fm.InputNo11.value=arrResult;   	
   }
   else
   {
    window.open("./FinFeeGetQueryLJSPayClaim.jsp","","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");			
   }	
}



//校验录入的单证是否不需要校验是否发放给业务员，如果需要返回true,否则返回false
function needVerifyCertifyCode(CertifyCode)
{
  try {
       //var tSql = "select Sysvarvalue from LDSysVar where Sysvar='NotVerifyCertifyCode'";          
       var tSql=wrapSql(tResourceName,'LDSysVar',[""]);
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
  	catch(e)
  	 {}
  	return true;
}

function queryAgent()
{
	if(document.all('ManageCom').value==""){
		 alert("请先录入管理机构信息！"); 
		 return;
	}
    if(document.all('AgentCode').value == "")	{  
	  //var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+document.all('ManageCom').value+",AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	  var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+document.all('ManageCom').value,"AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	  }
	if(document.all('AgentCode').value != "")	 {
	//var cAgentCode = fm.AgentCode.value;  //保单号码	
	//var strSql = "select AgentCode,Name from LAAgent where AgentCode='" + trim(fm.AgentCode.value) +"'";
    var strSql = wrapSql(tResourceName,'LAAgent2',[fm.AgentCode.value]);
    var arrResult = easyExecSql(strSql);
       //alert(arrResult);
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
  	fm.AgentName.value = arrResult[0][3];
  }
  //var aSql = "select agentgroup from LAAgent where agentcode='"+fm.AgentCode.value+"'";  
  var aSql=wrapSql(tResourceName,'LAAgent3',[fm.AgentCode.value]);
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

//校验录入代理人和保单代理人是否一致
function verifyAgent(PolNo)
{
	var strSql="";

	   //strSql = "select agentcode from lccont where contno='" + PolNo +"'";
		 strSql=wrapSql(tResourceName,'LCCont1',[PolNo]);	
			
    var arrResult = easyExecSql(strSql);
    if (arrResult != null) 
    {
      //alert("查询结果:  代理人编码:["+arrResult[0][0]+"]");
      if(arrResult[0][0]!=trim(document.all('AgentCode').value))
      {
      	alert("录入代理人和保单代理人不一致!");
      	return false;
      }      
    }
    else
    {
    	//strSql = "select agentcode from LCGrpCont where GrpContNo='" + PolNo +"'";
    	strSql=wrapSql(tResourceName,'LCGrpCont2',[PolNo]);
    	arrResult = easyExecSql(strSql);
      if (arrResult != null) 
       {
      //alert("查询结果:  代理人编码:["+arrResult[0][0]+"]");
         if(arrResult[0][0]!=trim(document.all('AgentCode').value))
           {
      	      alert("录入代理人和保单代理人不一致!");
      	      return false;
           }      
       }
      else
       {
          alert("未查到该保单信息！");
          return false;	
       }
    }  
	
	return true;
}

function verifyAgentCode(PrtNo)//新单录入业务员是否一致
{
	//var strSql="select agentcode from lccont where prtno='"+trim(PrtNo)+"'";
  var strSql=wrapSql(tResourceName,'LCCont2',[trim(PrtNo)]);
  var arrResult = easyExecSql(strSql);
  if (arrResult != null) 
   {
      if(arrResult[0][0] != document.all('AgentCode').value)
      {
      	alert("录入业务员和保单代理人不一致!");
      	return false;
      }      
   }	
	return true;
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
	var Currency =  document.all('Currency' + PayMode).value;
	var payFlag = "";
	var curFlag = "";

 // if(PayMode!='3')
 // {
	 for(i=0;i<mulLineCount;i++)
	  {//校验改为同种交费方式下可以存在多个币种
	  	payFlag = TempClassGrid.getRowColData(i,1);
	  	curFlag = TempClassGrid.getRowColData(i,3);	  	
	   	if(payFlag==PayMode && curFlag==Currency)
		   {
			   if(payFlag!='5')
			   {
			    alert("已存在该种交费方式,请进行删除或修改！");
			    fm.PayMode.focus();
			    return false;
	 	     }
	 	     else
	 	     {
	 	       if (TempClassGrid.getRowColData(i,5)==document.all('ChequeNo5').value)
	 	       {
			         alert("此记录已经添加");
			         return false;
	 	       }
	 	     } 
	 	   }
	}
  //}
//  if(PayMode=='5')
 // {
	//   if(document.all('ManageCom').value.substring(0, 4)!=document.all('PolicyCom').value.substring(0, 4))
//	     {
 //       alert("新契约不能异地缴费");
//        return false;
//	      }
//  }
  
 // if (document.all('TempFeeType').value=="1")
 // {
  	if(!CheckPayMode())
  	{
  	  return false;	
  	}	
//  }
	if(document.all('Currency'+PayMode).value=="")
	{
		alert("请录入币种!");
		return;
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
		TempClassGrid.setRowColData(mulLineCount,3,fm.Currency1.value);
		TempClassGrid.setRowColData(mulLineCount,4,fm.PayFee1.value);
		var CashType ;
		if(fm.CashType.value=='2'){
			CashType = "P12";
		}else{
			CashType = "P11";
		}
		TempClassGrid.setRowColData(mulLineCount,19,CashType);
		TempClassGrid.setRowColData(mulLineCount,7,document.all('PayDate').value);
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
    	TempClassGrid.setRowColData(mulLineCount,3,fm.Currency2.value);	
		TempClassGrid.setRowColData(mulLineCount,4,fm.PayFee2.value);
		TempClassGrid.setRowColData(mulLineCount,5,fm.ChequeNo2.value);
		TempClassGrid.setRowColData(mulLineCount,6,fm.ChequeDate2.value);
		TempClassGrid.setRowColData(mulLineCount,8,fm.BankCode2.value);
		TempClassGrid.setRowColData(mulLineCount,9,fm.BankAccNo2.value);
		TempClassGrid.setRowColData(mulLineCount,10,fm.AccName2.value);
		TempClassGrid.setRowColData(mulLineCount,7,document.all('PayDate').value);
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
  		TempClassGrid.setRowColData(mulLineCount,3,fm.Currency3.value);
		TempClassGrid.setRowColData(mulLineCount,4,fm.PayFee3.value);
		TempClassGrid.setRowColData(mulLineCount,5,fm.ChequeNo3.value);
		TempClassGrid.setRowColData(mulLineCount,6,fm.ChequeDate3.value);
		TempClassGrid.setRowColData(mulLineCount,7,'');
		TempClassGrid.setRowColData(mulLineCount,8,fm.BankCode3.value);
		TempClassGrid.setRowColData(mulLineCount,9,fm.BankAccNo3.value);
		TempClassGrid.setRowColData(mulLineCount,10,fm.AccName3.value);
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
		TempClassGrid.setRowColData(mulLineCount,3,fm.Currency5.value);
		TempClassGrid.setRowColData(mulLineCount,4,fm.PayFee5.value);
		TempClassGrid.setRowColData(mulLineCount,5,fm.ChequeNo5.value);
		TempClassGrid.setRowColData(mulLineCount,7,document.all('PayDate').value);
		//TempClassGrid.setRowColData(mulLineCount,5,fm.ChequeDate5.value);
		//TempClassGrid.setRowColData(mulLineCount,7,fm.BankCode5.value);
		//TempClassGrid.setRowColData(mulLineCount,8,fm.BankAccNo5.value);
		//TempClassGrid.setRowColData(mulLineCount,9,fm.AccName5.value);
		//TempClassGrid.setRowColData(mulLineCount,10,fm.InBankCode5.value);
		//TempClassGrid.setRowColData(mulLineCount,11,fm.InBankAccNo5.value);
		//TempClassGrid.setRowColData(mulLineCount,12,fm.InAccName5.value);		
	}	
/*	if(PayMode==6)
	{
		if (verifyElement("收费银行|NOTNULL", document.all('InBankCode6').value) != true) {
      return false;
    }    
		if (verifyElement("收费银行账号|NOTNULL", document.all('InBankAccNo6').value) != true) {
      return false;
    }	
		
		TempClassGrid.setRowColData(mulLineCount,3,fm.PayFee6.value);
		//TempClassGrid.setRowColData(mulLineCount,4,fm.ChequeNo6.value);
		//TempClassGrid.setRowColData(mulLineCount,5,fm.ChequeDate6.value);
		//TempClassGrid.setRowColData(mulLineCount,7,fm.BankCode6.value);
		//TempClassGrid.setRowColData(mulLineCount,8,fm.BankAccNo6.value);
		//TempClassGrid.setRowColData(mulLineCount,9,fm.AccName6.value);
    TempClassGrid.setRowColData(mulLineCount,6,document.all('PayDate').value);
		TempClassGrid.setRowColData(mulLineCount,10,fm.InBankCode6.value);
		TempClassGrid.setRowColData(mulLineCount,11,fm.InBankAccNo6.value);
		//TempClassGrid.setRowColData(mulLineCount,12,fm.InAccName6.value);		
	}	*/
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

		TempClassGrid.setRowColData(mulLineCount,3,fm.Currency4.value);
		TempClassGrid.setRowColData(mulLineCount,4,fm.PayFee4.value);
		TempClassGrid.setRowColData(mulLineCount,7,'');
		TempClassGrid.setRowColData(mulLineCount,8,fm.BankCode4.value);
		TempClassGrid.setRowColData(mulLineCount,9,fm.BankAccNo4.value);
		TempClassGrid.setRowColData(mulLineCount,10,fm.AccName4.value);
		TempClassGrid.setRowColData(mulLineCount,17,fm.IDType.value);
		TempClassGrid.setRowColData(mulLineCount,18,fm.IDNo.value);
		//TempClassGrid.setRowColData(mulLineCount,12,fm.InAccName7.value);		
	}	
	if(PayMode==8)
	{
		TempClassGrid.setRowColData(mulLineCount,3,fm.Currency8.value);
		TempClassGrid.setRowColData(mulLineCount,4,fm.PayFee8.value);
		TempClassGrid.setRowColData(mulLineCount,7,document.all('PayDate').value);
	}	
  if(PayMode==6)
  { //alert("BBBBBBBBBBBBBBBB");
   if (verifyElement("开户银行|NOTNULL", document.all('BankCode6').value) != true) {
      return false;
    }
    
    TempClassGrid.setRowColData(mulLineCount,3,fm.Currency6.value);
    TempClassGrid.setRowColData(mulLineCount,4,fm.PayFee6.value);
		TempClassGrid.setRowColData(mulLineCount,7,'');
		//TempClassGrid.setRowColData(mulLineCount,6,'');
		TempClassGrid.setRowColData(mulLineCount,8,fm.BankCode6.value);
		TempClassGrid.setRowColData(mulLineCount,9,fm.BankAccNo6.value);
		TempClassGrid.setRowColData(mulLineCount,10,fm.AccName6.value);
		//TempClassGrid.setRowColData(mulLineCount,10,fm.InBankCode9.value);
		//TempClassGrid.setRowColData(mulLineCount,11,fm.InBankAccNo9.value);
		//TempClassGrid.setRowColData(mulLineCount,12,fm.InAccName9.value);
  }
	if(PayMode=='0')
	{
		TempClassGrid.setRowColData(mulLineCount,3,fm.Currency0.value);
		TempClassGrid.setRowColData(mulLineCount,4,fm.PayFee0.value);
		TempClassGrid.setRowColData(mulLineCount,7,'');
	}  
//  document.all('spanTempClassGrid'+mulLineCount).all('TempClassGridSel').checked=true;
  if(PayMode=='5')
  {
  	document.all('ChequeNo5').value="";
  	document.all('OtherNo5').value="";
  	document.all('Currency5').value="";
  	document.all('PayFee5').value="";
  	document.all('Drawer5').value="";
  }
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
	var rowNum=TempClassGrid.getSelNo();
	//alert(rowNum);
	if (rowNum==0)
	{
	alert("未发现要修改的数据，请点击“增加”！");
	return false;	
	}
	var mulLineCount = TempClassGrid.getSelNo()-1;
	//var mulLineCount = TempClassGrid.getSelNo();
	if(mulLineCount>=0)
	{
		
		TempClassGrid.setRowColData(mulLineCount,1,fm.PayMode.value);
		TempClassGrid.setRowColData(mulLineCount,2,fm.PayModeName.value);
		var PayMode = TempClassGrid.getRowColData(mulLineCount,1);
		if(PayMode==1)
		{
			TempClassGrid.setRowColData(mulLineCount,3,fm.Currency1.value);
			TempClassGrid.setRowColData(mulLineCount,4,fm.PayFee1.value);
			TempClassGrid.setRowColData(mulLineCount,7,document.all('PayDate').value);
			TempClassGrid.setRowColData(mulLineCount,5,'');
			TempClassGrid.setRowColData(mulLineCount,6,'');
			TempClassGrid.setRowColData(mulLineCount,9,'');
			TempClassGrid.setRowColData(mulLineCount,10,'');
			TempClassGrid.setRowColData(mulLineCount,8,'');
			TempClassGrid.setRowColData(mulLineCount,17,'');
			TempClassGrid.setRowColData(mulLineCount,18,'');
			var CashType ;
			if(fm.CashType.value=='2'){
				CashType = "P12";
			}else{
				CashType = "P11";
			}
			TempClassGrid.setRowColData(mulLineCount,19,CashType);
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
	    	TempClassGrid.setRowColData(mulLineCount,3,fm.Currency2.value);
			TempClassGrid.setRowColData(mulLineCount,4,fm.PayFee2.value);
			TempClassGrid.setRowColData(mulLineCount,5,fm.ChequeNo2.value);
			TempClassGrid.setRowColData(mulLineCount,6,fm.ChequeDate2.value);
			TempClassGrid.setRowColData(mulLineCount,8,fm.BankCode2.value);
			TempClassGrid.setRowColData(mulLineCount,9,fm.BankAccNo2.value);
			TempClassGrid.setRowColData(mulLineCount,10,fm.AccName2.value);
			TempClassGrid.setRowColData(mulLineCount,7,document.all('PayDate').value);
			TempClassGrid.setRowColData(mulLineCount,17,'');
			TempClassGrid.setRowColData(mulLineCount,18,'');
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
	  
	  		TempClassGrid.setRowColData(mulLineCount,3,fm.Currency3.value);
			TempClassGrid.setRowColData(mulLineCount,4,fm.PayFee3.value);
			TempClassGrid.setRowColData(mulLineCount,5,fm.ChequeNo3.value);
			TempClassGrid.setRowColData(mulLineCount,6,fm.ChequeDate3.value);
			TempClassGrid.setRowColData(mulLineCount,7,'');
			TempClassGrid.setRowColData(mulLineCount,8,fm.BankCode3.value);
			TempClassGrid.setRowColData(mulLineCount,9,fm.BankAccNo3.value);
			TempClassGrid.setRowColData(mulLineCount,10,fm.AccName3.value);
			TempClassGrid.setRowColData(mulLineCount,17,'');
			TempClassGrid.setRowColData(mulLineCount,18,'');
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
			TempClassGrid.setRowColData(mulLineCount,3,fm.Currency5.value);
			TempClassGrid.setRowColData(mulLineCount,4,fm.PayFee5.value);
			TempClassGrid.setRowColData(mulLineCount,5,fm.ChequeNo5.value);
			TempClassGrid.setRowColData(mulLineCount,7,document.all('PayDate').value);
			TempClassGrid.setRowColData(mulLineCount,6,'');
			TempClassGrid.setRowColData(mulLineCount,8,'');
			TempClassGrid.setRowColData(mulLineCount,10,'');
			TempClassGrid.setRowColData(mulLineCount,8,'');
			TempClassGrid.setRowColData(mulLineCount,17,'');
			TempClassGrid.setRowColData(mulLineCount,18,'');
			//TempClassGrid.setRowColData(mulLineCount,5,fm.ChequeDate5.value);
			//TempClassGrid.setRowColData(mulLineCount,7,fm.BankCode5.value);
			//TempClassGrid.setRowColData(mulLineCount,8,fm.BankAccNo5.value);
			//TempClassGrid.setRowColData(mulLineCount,9,fm.AccName5.value);
			//TempClassGrid.setRowColData(mulLineCount,10,fm.InBankCode5.value);
			//TempClassGrid.setRowColData(mulLineCount,11,fm.InBankAccNo5.value);
			//TempClassGrid.setRowColData(mulLineCount,12,fm.InAccName5.value);		
		}	
	/*	if(PayMode==6)
		{
			if (verifyElement("收费银行|NOTNULL", document.all('InBankCode6').value) != true) {
	      return false;
	    }    
			if (verifyElement("收费银行账号|NOTNULL", document.all('InBankAccNo6').value) != true) {
	      return false;
	    }	
			
			TempClassGrid.setRowColData(mulLineCount,3,fm.PayFee6.value);
			//TempClassGrid.setRowColData(mulLineCount,4,fm.ChequeNo6.value);
			//TempClassGrid.setRowColData(mulLineCount,5,fm.ChequeDate6.value);
			//TempClassGrid.setRowColData(mulLineCount,7,fm.BankCode6.value);
			//TempClassGrid.setRowColData(mulLineCount,8,fm.BankAccNo6.value);
			//TempClassGrid.setRowColData(mulLineCount,9,fm.AccName6.value);
	    TempClassGrid.setRowColData(mulLineCount,6,document.all('PayDate').value);
			TempClassGrid.setRowColData(mulLineCount,10,fm.InBankCode6.value);
			TempClassGrid.setRowColData(mulLineCount,11,fm.InBankAccNo6.value);
			//TempClassGrid.setRowColData(mulLineCount,12,fm.InAccName6.value);		
		}	*/
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
	
			TempClassGrid.setRowColData(mulLineCount,3,fm.Currency4.value);
			TempClassGrid.setRowColData(mulLineCount,4,fm.PayFee4.value);
			TempClassGrid.setRowColData(mulLineCount,7,'');
			TempClassGrid.setRowColData(mulLineCount,8,fm.BankCode4.value);
			TempClassGrid.setRowColData(mulLineCount,9,fm.BankAccNo4.value);
			TempClassGrid.setRowColData(mulLineCount,10,fm.AccName4.value);
			TempClassGrid.setRowColData(mulLineCount,17,fm.IDType.value);
			TempClassGrid.setRowColData(mulLineCount,18,fm.IDNo.value);
			TempClassGrid.setRowColData(mulLineCount,5,'');
			//TempClassGrid.setRowColData(mulLineCount,12,fm.InAccName7.value);		
		}	
		//if(PayMode==8)
		//{
		//	TempClassGrid.setRowColData(mulLineCount,3,fm.PayFee8.value);
		//	TempClassGrid.setRowColData(mulLineCount,6,document.all('PayDate').value);
		//}	
	  if(PayMode==6)
	  { //alert("BBBBBBBBBBBBBBBB");
	   if (verifyElement("开户银行|NOTNULL", document.all('BankCode6').value) != true) {
	      return false;
	    }
	    
	    TempClassGrid.setRowColData(mulLineCount,3,fm.Currency6.value);
	    TempClassGrid.setRowColData(mulLineCount,4,fm.PayFee6.value);
			TempClassGrid.setRowColData(mulLineCount,7,'');
			//TempClassGrid.setRowColData(mulLineCount,6,'');
			TempClassGrid.setRowColData(mulLineCount,8,fm.BankCode6.value);
			TempClassGrid.setRowColData(mulLineCount,9,fm.BankAccNo6.value);
			TempClassGrid.setRowColData(mulLineCount,10,fm.AccName6.value);
			//TempClassGrid.setRowColData(mulLineCount,10,fm.InBankCode9.value);
			//TempClassGrid.setRowColData(mulLineCount,11,fm.InBankAccNo9.value);
			//TempClassGrid.setRowColData(mulLineCount,12,fm.InAccName9.value);
			TempClassGrid.setRowColData(mulLineCount,5,'');
			TempClassGrid.setRowColData(mulLineCount,6,'');
			TempClassGrid.setRowColData(mulLineCount,17,'');
			TempClassGrid.setRowColData(mulLineCount,18,'');
	  }
		if(PayMode=='0')
		{
			TempClassGrid.setRowColData(mulLineCount,3,fm.Currency0.value);
			TempClassGrid.setRowColData(mulLineCount,4,fm.PayFee0.value);
			TempClassGrid.setRowColData(mulLineCount,7,'');
		}  
	 // document.all('spanTempClassGrid'+mulLineCount).all('TempClassGridSel').checked=true;
 }

}

/*********************************************************************
 *  将Mul选中值，赋给fm的录入框
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function showMul()
{

		var tSelNo = TempClassGrid.getSelNo()-1;
		if(tSelNo>=0)
		{
			var PayMode = TempClassGrid.getRowColData(tSelNo,1);
			fm.PayMode.value=TempClassGrid.getRowColData(tSelNo,1);
			fm.PayModeName.value=TempClassGrid.getRowColData(tSelNo,2);
			showTempClassInput(PayMode);
			if(PayMode==1)
			{
					fm.Currency1.value=TempClassGrid.getRowColData(tSelNo,3);
					fm.PayFee1.value=TempClassGrid.getRowColData(tSelNo,4);
			}
			if(PayMode==2)
			{
					fm.Currency2.value=TempClassGrid.getRowColData(tSelNo,3);
					fm.PayFee2.value = TempClassGrid.getRowColData(tSelNo,4);
					fm.ChequeNo2.value = TempClassGrid.getRowColData(tSelNo,5);
					fm.ChequeDate2.value = TempClassGrid.getRowColData(tSelNo,6);
					fm.BankCode2.value = TempClassGrid.getRowColData(tSelNo,8);
					fm.BankAccNo2.value = TempClassGrid.getRowColData(tSelNo,9);
					fm.AccName2.value = TempClassGrid.getRowColData(tSelNo,10);
			}
			if(PayMode==3)
			{
					fm.Currency3.value=TempClassGrid.getRowColData(tSelNo,3);
					fm.PayFee3.value = TempClassGrid.getRowColData(tSelNo,4);
					fm.ChequeNo3.value = TempClassGrid.getRowColData(tSelNo,5);
					fm.ChequeDate3.value = TempClassGrid.getRowColData(tSelNo,6);
					fm.BankCode3.value = TempClassGrid.getRowColData(tSelNo,8);
					fm.BankAccNo3.value = TempClassGrid.getRowColData(tSelNo,9);
					fm.AccName3.value = TempClassGrid.getRowColData(tSelNo,10);
			}
			if(PayMode==4)
			{
					fm.Currency4.value=TempClassGrid.getRowColData(tSelNo,3);
					fm.PayFee4.value = TempClassGrid.getRowColData(tSelNo,4);
					fm.BankCode4.value = TempClassGrid.getRowColData(tSelNo,8);
					fm.BankAccNo4.value = TempClassGrid.getRowColData(tSelNo,9);
					fm.AccName4.value = TempClassGrid.getRowColData(tSelNo,10);
					fm.IDType.value = TempClassGrid.getRowColData(tSelNo,17);
					fm.IDNo.value = TempClassGrid.getRowColData(tSelNo,18);						 			
			}		
			if(PayMode==5)
			{
					//fm.ChequeDate5.value = TempClassGrid.getRowColData(tSelNo,5);       
					//fm.BankCode5.value = TempClassGrid.getRowColData(tSelNo,7);         
					//fm.BankAccNo5.value = TempClassGrid.getRowColData(tSelNo,8);        
					//fm.AccName5.value = TempClassGrid.getRowColData(tSelNo,9);          
					//fm.InBankCode5.value = TempClassGrid.getRowColData(tSelNo,10);      
					//fm.InBankAccNo5.value = TempClassGrid.getRowColData(tSelNo,11);     
					//fm.InAccName5.value = TempClassGrid.getRowColData(tSelNo,12);		 			
			}		
			if(PayMode==6)
			{
					fm.Currency6.value=TempClassGrid.getRowColData(tSelNo,3);
					fm.PayFee6.value = TempClassGrid.getRowColData(tSelNo,4);            
					//fm.ChequeNo6.value = TempClassGrid.getRowColData(tSelNo,4);      
					fm.BankCode6.value = TempClassGrid.getRowColData(tSelNo,8);         
					fm.BankAccNo6.value = TempClassGrid.getRowColData(tSelNo,9);        
					fm.AccName6.value = TempClassGrid.getRowColData(tSelNo,10);          			
			}
	}
}

function getAgentCode()
{
	if((document.all('InputNo3').value!=null||document.all('InputNo3').value!="")&&(document.all('AgentCode').value==""||document.all('AgentCode').value==null))
	{
		//var strSql = "select AgentCode,managecom,agentgroup from ljspay where otherno='"+fm.InputNo3.value+"'";
	  var strSql=wrapSql(tResourceName,'LJSPay2',[fm.InputNo3.value]);
	  var TarrResult = easyQueryVer3(strSql, 1, 1, 1);
	  //var TarrResult = easyExecSql(strSql);
	  //alert("0000000000000000000");
	  if(TarrResult!=null)
	  {
	  	var ArrData = decodeEasyQueryResult(TarrResult);
	  	//alert("1111111111111111");
	  	if(ArrData!=null)
	  	{
		  	if(ArrData[0][0]==""||ArrData[0][2]=="")
		  	{
		  	  alert("未查到代理人信息");	
		  	}
		  	//alert("2222222222222");
		  	if(ArrData[0][1]=="")
		  	{
		  	  alert("未查到保单管理机构");	
		  	}	  	
		    fm.AgentCode.value=ArrData[0][0];  
		    fm.PolicyCom.value=ArrData[0][1];
		    fm.AgentGroup.value=ArrData[0][2];	
	    }
	  }
	  
	  else
	  {
	     alert("无催收交费数据，请进行续期催缴抽档");	 
	     return;
	  }
	  
  }
//  if(TarrResult==null||TarrResult=="")
//  //if((document.all('PolicyCom').value==null||document.all('PolicyCom').value=="")&&(document.all('AgentCode').value==""||document.all('AgentCode').value==null)&&(document.all('AgentGroup').value==""||document.all('AgentGroup').value==null))
//	  {
//	     alert("此保单还没有催收交费，请继续抽档!"); 
//	  }
//  else
//  {
//     	
//  }
}
 
function getEdorCode()
{

  if(document.all('PayDate').value == null && document.all('PayDate').value == "")
  {
  	alert("请录入交费日期");
  	return false;
  	}
  if((document.all('InputNo7').value!=null&&document.all('InputNo7').value!="")&&(document.all('InputNo8').value==null||document.all('InputNo8').value==""))
  {

   //var strSql = "select GetNoticeNo from LJSPay where othernotype='10' "
   //           + getWherePart('OtherNo','InputNo7')
   //           + getWherePart('PayDate','PayDate','>=');
              //+ getWherePart('PayDate','PayDate','<=');  
   
    var strSql=wrapSql(tResourceName,'LJSPay3',[document.all('InputNo7').value,document.all('PayDate').value]);            	
   var arrResult = easyExecSql(strSql);
   if(arrResult == null ||arrResult == ""||arrResult=='null')
   {
    alert("逾期不能收费!!!");
    return false;	
   }
  else
  	{
    fm.InputNo8.value=arrResult;	
    }
  }	
  if((document.all('InputNo8').value!=null&&document.all('InputNo8').value!="")&&(document.all('InputNo7').value==null||document.all('InputNo7').value==""))
  {
   //var strSql = "select otherno from LJSPay where GetNoticeNo='"+fm.InputNo8.value+"' and othernotype='10' and PayDate>='"+document.all('PayDate').value+"'"; 	
   var strSql=wrapSql(tResourceName,'LJSPay4',[fm.InputNo8.value,document.all('PayDate').value]);
   var arrResult = easyExecSql(strSql);
   if(arrResult==null||arrResult=="")
   {
    alert("该保全申请号不存在，或已经逾期");	
   }
  else
  	{
     fm.InputNo7.value=arrResult;	
    }
  }  

   if (document.all('InputNo7').value!=null&&document.all('InputNo7').value!="")
   {
     //var strSql = "select managecom from LJSPay where GetNoticeNo='"+fm.InputNo8.value+"' and othernotype='10' and PayDate >='" +document.all('PayDate').value+ "' "; 	
			 var strSql=wrapSql(tResourceName,'LJSPay5',[fm.InputNo8.value,document.all('PayDate').value]);
     var arrResult = easyExecSql(strSql);
     if(arrResult == null ||arrResult == ""||arrResult=='null')
      {
        alert("逾期不能收费!!!");
        return false;	
      }
     else
  	  {
        fm.PolicyCom.value=arrResult;	
       }         
   }

}

function GetManageCom()
{
  if(fm.AgentCode.value!=null&&fm.AgentCode.value!="")
  {
   //var strSql = "select a.managecom from LAAgent a,LATree b,LABranchGroup c where 1=1 "
	 //        + "and a.AgentCode = b.AgentCode and a.AgentGroup = c.AgentGroup and (a.AgentState is null or a.AgentState < '03') "
	 //        + "and a.agentcode ='"+fm.AgentCode.value+"'";
	 //var strSql = "select managecom from LAAgent where  agentcode='"+trim(fm.AgentCode.value)+"' ";
   var strSql=wrapSql(tResourceName,'LAAgent4',[trim(fm.AgentCode.value)]);
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
  //var aSql = "select agentgroup from LAAgent where agentcode='"+trim(fm.AgentCode.value)+"'";  
  var aSql=wrapSql(tResourceName,'LAAgent5',[trim(fm.AgentCode.value)]);
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
       //var nSql = "select name from LAAgent where agentcode='"+trim(fm.AgentCode.value)+"'";
       var nSql=wrapSql(tResourceName,'LAAgent6',[trim(fm.AgentCode.value)]);
       arrResult = easyExecSql(nSql);
       fm.AgentName.value=arrResult;
    }  
  }	
	
}
/*********************************************************************
 *  收费方式选择内部转帐时，查询实付数据
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function queryLJAGet()
{
   if(document.all('ChequeNo5').value!=""||document.all('OtherNo5').value!="")
   {
      EasyQueryPay();
   }
   else
   {
    alert("实付号码和其他号码不能同时为空"); 
    return false;	
   }	
}

function getLJSPayPolicyCom()
{
  if (fm.InputNo11.value!=null&&fm.InputNo11.value!="")	
  {
	  //var strSql = "select distinct managecom from ljspay where getnoticeno='"+fm.InputNo11.value+"'";
    var strSql=wrapSql(tResourceName,'LJSPay6',[fm.InputNo11.value]);
    
    var arrResult = easyExecSql(strSql);    	
    fm.PolicyCom.value=arrResult;
  }
  else if (fm.InputNo12.value!=null&&fm.InputNo12.value!="")
  {
	  //var strSql = "select managecom from ljspay where otherno='"+fm.InputNo12.value+"'";
    var strSql = wrapSql(tResourceName,'LJSPay7',fm.InputNo12.value);
    var arrResult = easyExecSql(strSql);     	
    fm.PolicyCom.value=arrResult;
  } 
}

/*********************************************************************
 *  异地代收——预交续期保费查询保单管理机构
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function getPolicyCom()
{
	if (fm.InputNo5.value!=null&&fm.InputNo5.value!="")
	{
	  var flag = "";
	  //var strSql2 = "select * from lcgrpcont where grpcontno = '" + fm.InputNo5.value +"'";
	  var strSql2 =wrapSql(tResourceName,'LCGrpCont1',[fm.InputNo5.value]);
	  var arrResult2 = easyExecSql(strSql2);
	  if(arrResult2 == null)
	  {
	     //var strSql = "select managecom from LCCont where contno='"+fm.InputNo5.value+"'";
       	 var strSql = wrapSql(tResourceName,'LCCont3',[fm.InputNo5.value]);
         var arrResult = easyExecSql(strSql);
         if(arrResult==null||arrResult=="NULL"||arrResult=="")
         {
           alert("无此保单信息，管理机构查询失败");	
           fm.PolicyCom.value="";
           return false;
         }
         else
  	     {
           fm.PolicyCom.value=arrResult;	  	  
           //var ASql = "select agentcode from LCCont where contno='"+fm.InputNo5.value+"'";
           var ASql=wrapSql(tResourceName,'LCCont1',[fm.InputNo5.value]);
           var brrResult = easyExecSql(ASql);
         if(brrResult==null||brrResult=="NULL"||brrResult=="")
         {
           alert("无此保单信息，代理人查询失败");	
           fm.AgentCode.value="";
           return false;
         }
         else
       	 {
       		fm.AgentCode.value=brrResult;
       	 }
       }	
     }   
     else
    {
      //var strSql = "select managecom from LCGrpCont where grpcontno='"+fm.InputNo5.value+"'";
      var strSql = wrapSql(tResourceName,'LCGrpCont3',[fm.InputNo5.value]);
      var arrResult = easyExecSql(strSql);
      if(arrResult==null||arrResult=="NULL"||arrResult=="")
      {
        alert("无此保单信息，管理机构查询失败");	
        fm.PolicyCom.value="";
        return false;
      }
      else
  	  {
        fm.PolicyCom.value=arrResult;	  	  
        //var ASql = "select agentcode from LCGrpCont where grpcontno='"+fm.InputNo5.value+"'";
        var ASql=wrapSql(tResourceName,'LCGrpCont2',[fm.InputNo5.value]);
        var brrResult = easyExecSql(ASql);
        if(brrResult==null||brrResult=="NULL"||brrResult=="")
        {
          alert("无此保单信息，代理人查询失败");	
          fm.AgentCode.value="";
          return false;
        }
        else
       	{
       		fm.AgentCode.value=brrResult;
       	}
      } 
    }
	}
}




function afterSelectCode()
{
	if(document.all('TempFeeType').value=='1' )
	{
		if(document.all('CertifyFlag').value=='1')
		{
			 divCertifyFlag1.style.display="";	
       divCertifyFlag2.style.display="none";
       divCertifyFlag3.style.display="none";
		}
		else if(document.all('CertifyFlag').value=='2')
		{
			 divCertifyFlag2.style.display="";	
       divCertifyFlag3.style.display="none";
       divCertifyFlag1.style.display="none";
		}
		else if (document.all('CertifyFlag').value=='3')
		{
			 divCertifyFlag3.style.display="";	
       divCertifyFlag2.style.display="none";
       divCertifyFlag1.style.display="none";
       document.all.InputNo1.display = "none";
		}
		else
			{
			}
	}
	if(document.all('TempFeeType').value=='6' )
	{
		if(document.all('ClaimFeeType').value=='1')
		{
			 divClaimFeeType1.style.display="";	
       divClaimFeeType2.style.display="none";
		}
		else if(document.all('ClaimFeeType').value=='2')
		{
			 divClaimFeeType2.style.display="";	
       divClaimFeeType1.style.display="none";
       divCertifyFlag1.style.display="none";
		}
		else
			{
			}
	}
}

function PayModePrem()
{
  var SumPrem =0.0;
	var TempCount = TempGrid.mulLineCount;
  for(i=0;i<TempCount;i++)
  {
  		var tempfeegridfee =TempGrid.getRowColData(i,4);
	    if(TempGrid.getRowColData(i,4)=="")
	    tempfeegridfee=0.00;
  //	SumPrem = SumPrem+parseFloat(tempfeegridfee);//交费金额累计 
  SumPrem=Arithmetic(SumPrem,'+',tempfeegridfee,2);  
  }
 // SumPrem = pointTwo(SumPrem);
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
 // if (document.all("PayMode").value == '7')
 // {
 // 	document.all("PayFee7").value = SumPrem;
//  }
 // if (document.all("PayMode").value == '8')
//  {
//  	document.all("PayFee8").value = SumPrem;
//  }
//  if (document.all("PayMode").value == '9')
//  {
//  	document.all("PayFee9").value = SumPrem;
//  }
  }
  
function EasyQueryPay ()
{
  // 拼SQL语句，从页面采集信息
  //var strSql = "select ActuGetNo, OtherNo,SumGetMoney,Drawer,DrawerID from LJAGet where 1=1 and ConfDate is null and EnterAccDate is null and (bankonthewayflag='0' or bankonthewayflag is null) and paymode<>'4' ";
  
  
  //if (document.all('ChequeNo5').value!="")
  //{
  //  	strSql = strSql + " and actugetno='"+document.all('ChequeNo5').value+"'";
  //}
  //if(document.all('OtherNo5').value!="")
	//{
	//    strSql = strSql + " and otherno='"+document.all('OtherNo5').value+"'";	
	//}
  
  var strSql = wrapSql(tResourceName,'LJAGet1',[document.all('ChequeNo5').value,document.all('OtherNo5').value]);
  //查询付费机构sql,返回结果
                   	                
	//查询SQL，返回结果字符串
  turnPage.strQueryResult  = easyQueryVer3(strSql, 1, 1, 1);  
                  
  //判断是否查询成功
  if (!turnPage.strQueryResult) {  
    //清空MULTILINE，使用方法见MULTILINE使用说明 
    QueryLJAGetGrid.clearData('QueryLJAGetGrid');  
    alert("没有查询到数据！");
    return false;
  }
  turnPage.pageLineNum = 5;
  //清空数据容器，两个不同查询共用一个turnPage对象时必须使用，最好加上，容错
  turnPage.arrDataCacheSet = clearArrayElements(turnPage.arrDataCacheSet);
  
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //设置初始化过的MULTILINE对象，VarGrid为在初始化页中定义的全局变量
  turnPage.pageDisplayGrid = QueryLJAGetGrid;    
          
  //保存SQL语句
  turnPage.strQuerySql     = strSql; 
  
  //设置查询起始位置
  turnPage.pageIndex       = 0;  
  
  //在查询结果数组中取出符合页面显示大小设置的数组
  var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, turnPage.pageLineNum);
  
  //调用MULTILINE对象显示查询结果
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  
  //控制是否显示翻页按钮
  if (turnPage.queryAllRecordCount > turnPage.pageLineNum) {
    try { window.divPage.style.display = ""; } catch(ex) { }
  } else {
    try { window.divPage.style.display = "none"; } catch(ex) { }
  }	
} 
  
 
function GetRecord()
{
  var arrReturn = new Array();
	var tSel = QueryLJAGetGrid.getSelNo();	
	if( tSel == 0 || tSel == null )
	{
		alert( "请先选择一条记录，再点击返回按钮。" );
	}
	else 
	{
    try
		{
     document.all('ChequeNo5').value=QueryLJAGetGrid.getRowColData(tSel-1,1);           
     document.all('OtherNo5').value=QueryLJAGetGrid.getRowColData(tSel-1,2);
     document.all('Currency5').value=QueryLJAGetGrid.getRowColData(tSel-1,3);              
     document.all('PayFee5').value=QueryLJAGetGrid.getRowColData(tSel-1,4);         
     document.all('Drawer5').value=QueryLJAGetGrid.getRowColData(tSel-1,5);            
	  }
	  	catch(ex)
		{
				alert(ex);
		}
	} 	
}

function CheckPayMode()
{
	if(document.all('CertifyFlag').value=="2"&&document.all('PayMode').value!="4")
	{
	    alert("银行划款协议书(个险)只能选择交费方式：银行转账")	;
	    return false;
	}
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
/* add ln 2008-07-14*/
// 查询按钮
var queryBug = 1;
function verifyTempNo(tempfeeno) 
{
	var tQueryPrtNo =tempfeeno;		                                           
	
  	  //判断如果这个单子已经成为了正式单，或外包方将需要的数据导入到核心业务系统中来，则提示不能录入
  	  //strSql= "select 1 from LJTempFee where TempFeeNo='"+tQueryPrtNo+"'"
      //            +" union "
      //            +"select 1 from LJTempFeeClass where TempFeeNo='"+tQueryPrtNo+"'"
      //            +" union "
  	  //            + "select 1 from BPOMissionState where BussNo='"+tQueryPrtNo+"' and  BussNoType='OF' and State <>'2'";   //不考虑已删除的异常件
  
  		strSql=wrapSql(tResourceName,'LJTempFee2',[tQueryPrtNo,tQueryPrtNo,tQueryPrtNo]);
  
      var arrResult = easyExecSql(strSql);
	   if (arrResult == null) 
	   {  
          return true;
       }
      else
      {
      		alert("此暂收费收据号/银行划款协议书号代表的财务收费单证数据已经被外包方导入或已经导入到核心业务系统中，不需要重新录入！");
      		return false;
      } 
}

//校验录入的单证描述表是否描述自动核销的标记，如果需要返回true,否则返回false
function needVerifyCertifyCode(CertifyCode)
{
	try{
			//var tSql = "select JugAgtFlag from lmcertifydes where certifycode = '" + CertifyCode + "'";
			var tSql=wrapSql(tResourceName,'LMCertifyDes1',[CertifyCode]);
			var tResult = easyExecSql(tSql, 1, 1, 1); 
			var JugAgtFlag = tResult[0][0];
			//JugAgtFlag为0或null-发放\回收都做校验，1-发放作校验,回收不作校验，2-发放不作校验,回收作校验，3-发放/回收都不做校验
			if(JugAgtFlag == "2" || JugAgtFlag == "3")
			{
					return false;
			}
		return true;
	}catch(ex){}
	return false;
}


//校验录入保单号和管理机构是否一致
function verifyContNo(ContNo)
{
		var strSql="";
		
		//strSql = "select managecom from ljspay where othernotype in ('1','2') and otherno = '" + ContNo +"'";
		strSql = wrapSql(tResourceName,'LJSPay8',[ContNo]);
		var arrResult = easyExecSql(strSql)        ;
		if (arrResult != null) 
		{
				if(arrResult[0][0] != trim(document.all('ManageCom').value))
				{
						alert("登陆的收费机构和保单的应收费管理机构不一致，请核实!");
						return false;
				}      
		}
		else
		{
				alert("未查到该保单信息！");
				return false;	
		}  
		
		return true;
}




//校验保单的状态：如果续期交费保单的状态为失效，不允许交费
function verifyContState(ContNo)
{
  var strSql="";
  var CurrentDate=getCurrentDate();
  //strSql = "select 1 from lccontstate where contno='"+ContNo+"' and statetype='Available' " 
  //       + "and state='1' and startdate<='"+CurrentDate+"' and (enddate >='"+CurrentDate+"' or enddate is null) ";

  strSql=wrapSql(tResourceName,'LCContState1',[ContNo,'Available',CurrentDate,CurrentDate]);

  if(easyExecSql(strSql)!=null)
  {
  	alert("该保单状态为：失效状态，不能续期交费!\n请先做保全复效！");
  	return false;
  }
  
  //var strSql1 = "select 1 from lccontstate where contno='"+ContNo+"' and statetype='PayPrem' " 
  //            + "and state='1' and startdate<='"+CurrentDate+"' and (enddate >='"+CurrentDate+"' or enddate is null) ";

   strSql1=wrapSql(tResourceName,'LCContState1',[ContNo,'PayPrem',CurrentDate,CurrentDate]);
              
  if(easyExecSql(strSql1)!=null)
  {
  	alert("该保单状态为：垫交状态，不能续期交费!\n请先做保全还垫！");
  	return false;
  }            	
 return true;
}


//去掉所有的控件中的空字符
function trimNo()
{
    document.all('InputNo1').value = document.all('InputNo1').value.trim()    ;
    document.all('InputNo2').value = document.all('InputNo2').value.trim()    ;
    document.all('InputNoB').value = document.all('InputNoB').value.trim()    ;
    document.all('InputNoB1').value= document.all('InputNoB1').value.trim()   ;
    document.all('InputNoC').value = document.all('InputNoC').value.trim()    ;
    document.all('InputNo3').value = document.all('InputNo3').value.trim()    ;
    document.all('XQCardNo').value = document.all('XQCardNo').value.trim()    ;
    document.all('InputNo4').value = document.all('InputNo4').value.trim()    ;
    document.all('InputNo5').value = document.all('InputNo5').value.trim()    ;
    document.all('InputNo7').value = document.all('InputNo7').value.trim()    ;
    document.all('InputNo8').value = document.all('InputNo8').value.trim()    ;
    document.all('InputNo11').value= document.all('InputNo11').value.trim()   ;
    document.all('InputNo12').value= document.all('InputNo12').value.trim()   ;
    document.all('InputNo13').value= document.all('InputNo13').value.trim()   ;
    document.all('InputNo14').value= document.all('InputNo14').value.trim()   ;
    document.all('InputNo99').value= document.all('InputNo99').value.trim()   ;
    document.all('InputNo19').value= document.all('InputNo19').value.trim()   ;          	
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
