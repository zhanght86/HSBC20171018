//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="0";
var mOperate="";
var turnPage = new turnPageClass();
window.onfocus=myonfocus;

//使得从该窗口弹出的窗口能够聚焦
function myonfocus()
{
	if(showInfo!=null) //shwoInfo是什么？
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


//进入页面自动查询
function initCostTypeDefQuery() 
{		
  	var strSQL = "";
  	/**
  	strSQL = "select VersionNo,CertificateID,CostID,CostName,FinItemType,decode(FinItemType,'C','贷方','D','借方','其他'),FinItemID,FinItemName,Remark,State from FICostTypeDef where ";
		strSQL = strSQL + " VersionNo ='"+VersionNo+"' and CertificateID ='"+CertificateID+"' ";
  	*/
  	//alert(strSQL);
  	var mySql1=new SqlClass();
		mySql1.setResourceName("fininterface.CostTypeDefInputSql"); //指定使用的properties文件名
		mySql1.setSqlId("CostTypeDefInputSql1");//指定使用的Sql的id
		mySql1.addSubPara(VersionNo);//指定传入的参数
		mySql1.addSubPara(CertificateID);//指定传入的参数
		strSQL= mySql1.getString();
  	
  	turnPage.queryModal(strSQL, CostTypeDefGrid);
} 



function addClick()
{
	if (!beforeSubmit()) //beforeSubmit()函数
  {
  	return false;
  }
  mOperate="INSERT||MAIN";
  submitForm();
}


//Click事件，当点击“删除”图片时触发该函数
function deleteClick()
{
  //提交前的检验
  if (!beforeSubmit2())
  {
  	return false;
  }	
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


function updateClick()
{
  //提交前的检验
  if (!beforeSubmit2())
  {
  	return false;
  }		
	if (!beforeSubmit()) //beforeSubmit()函数
  {
  	return false;
  }
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
  	alert("在CostTypeDefInput.js-->resetForm函数中发生异常:初始化界面错误!");
  }
}


//取消按钮对应操作
function cancelForm()
{
//  window.location="../common/html/Blank.html";
    showDiv(operateButton,"true");
    showDiv(inputButton,"false");
}



function submitForm()
{

  //提交前的检验
  

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

  fm.hideOperate.value=mOperate;
  if (fm.hideOperate.value=="")
  {
    alert("操作控制数据丢失！");
  }
  fm.action="CostTypeDefSave.jsp";
  //lockButton(); 
  document.getElementById("fm").submit(); //提交

}



//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
  showInfo.close();
  //释放“增加”按钮

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
    //resetForm();
		mOperate="";
  }
  else
  {
    //alert(content);
    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
  	//parent.fraInterface.initForm();
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=350;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

//    showDiv(operateButton,"true");
//    showDiv(inputButton,"false");
    //执行下一步操作
    //resetForm();
    if(mOperate=="DELETE||MAIN")
    {
    		document.all('State').value = '02'; 
    		document.all('CostID').value = '';
    		document.all('CostName').value = '';
    		document.all('FinItemType').value = '';
    		document.all('FinItemTypeName').value = '';
    		document.all('FinItemID').value = '';
    		document.all('FinItemName').value = '';
    		document.all('Remark').value = '';
    		
    		document.all('CostID').readOnly = false;	
    }
    mOperate="";
    initCostTypeDefQuery();    
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


//增加、删除、修改提交前的校验、计算
function beforeSubmit()
{			
  if((fm.VersionNo.value=="")||(fm.VersionNo.value==null))
  {
    alert("请您录入版本编号！");
    return ;
  }
  
  if((fm.CertificateID.value=="")||(fm.CertificateID.value==null))
  {
    alert("请您录入凭证类型编号！");
    return ;
  }

  if((fm.CostID.value=="")||(fm.CostID.value==null))
  {
    alert("请您录入费用类型编码！");
    return ;
  }
  
  if((fm.CostName.value=="")||(fm.CostName.value==null))
  {
    alert("请您录入费用类型名称！");
    return ;
  }
  
  if((fm.FinItemType.value=="")||(fm.FinItemType.value==null))
  {
    alert("请您录入借贷标志！");
    return ;
  }
  
  if((fm.FinItemID.value=="")||(fm.FinItemID.value==null))
  {
    alert("请您录入科目类型编码！");
    return ;
  }
  
  if((fm.FinItemName.value=="")||(fm.FinItemName.value==null))
  {
    alert("请您录入科目类型名称！");
    return ;
  }  
  
/*  
  if((fm.State.value=="")||(fm.State.value==null))
  {
    alert("请您录入状态！");
    return ;
  }
*/
  
  if (!verifyInput2()) 
  {
  	return false;
  }
    
    return true;
}


//删除和修改前必须选中一条记录
function beforeSubmit2()
{		
	var tSel = CostTypeDefGrid.getSelNo();	
	var strSQL="";
	if( tSel == 0 || tSel == null )
	{
		alert( "请您先选择一条记录，再进行修改和删除的操作!" );
		return;
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


//选中mulline中一行，自动给输入框赋值
 function ShowCostTypeDef()
{
 	var arrResult = new Array();

	var tSel = CostTypeDefGrid.getSelNo();	
	var strSQL="";
	if( tSel == 0 || tSel == null )
	{
		alert( "请先选择一条记录!" );
		return;
	}			
	else
	{
	//设置需要返回的数组
	// 书写SQL语句		
  	var strSQL = "";
  	/**
		strSQL = "select a.VersionNo,a.CertificateID,a.CostID,a.CostName,a.FinItemType,a.FinItemID,a.FinItemName,a.Remark,a.State from FICostTypeDef a where a.VersionNo='"+
	          CostTypeDefGrid.getRowColData(tSel-1,1)+"' and a.CertificateID='"+
	          CostTypeDefGrid.getRowColData(tSel-1,2)+"' and a.CostID='"+
	          CostTypeDefGrid.getRowColData(tSel-1,3)+"'";
	          */
		//alert(strSQL);
		var mySql2=new SqlClass();
			mySql2.setResourceName("fininterface.CostTypeDefInputSql"); //指定使用的properties文件名
			mySql2.setSqlId("CostTypeDefInputSql2");//指定使用的Sql的id
			mySql2.addSubPara(CostTypeDefGrid.getRowColData(tSel-1,1));//指定传入的参数
			mySql2.addSubPara(CostTypeDefGrid.getRowColData(tSel-1,2));//指定传入的参数
			mySql2.addSubPara(CostTypeDefGrid.getRowColData(tSel-1,3));//指定传入的参数
			strSQL= mySql2.getString();
		turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
  //判断是否查询成功
  if (!turnPage.strQueryResult) 
  {
    alert("查询失败！");
    return false;
  }
	//查询成功则拆分字符串，返回二维数组
  	arrResult = decodeEasyQueryResult(turnPage.strQueryResult); 
  	document.all('VersionNo').value = arrResult[0][0];
  	//alert(VersionNo);  
  	document.all('CertificateID').value = arrResult[0][1];
  	//alert(CertificateID);    	
  	document.all('CostID').value = arrResult[0][2];
  	document.all('CostName').value = arrResult[0][3];
  	document.all('FinItemType').value = arrResult[0][4];  	  	  	  	  	
  	document.all('FinItemID').value = arrResult[0][5];  	  	  	  	
  	document.all('FinItemName').value = arrResult[0][6];  	  	  	  	
  	document.all('Remark').value = arrResult[0][7];  	  	
		document.all('State').value = arrResult[0][8]; 
		showCodeName(); 	  	  	
	}	 
	//选中mulline赋值之后，JudgementNo不能进行修改，只能修改非主键信息
		document.all('CostID').readOnly = true;	
		//document.all('CostID').disabled = true;	
}

function resetAgain()
{
			 	document.all('State').value = '02'; 
    		document.all('CostID').value = '';
    		document.all('CostName').value = '';
    		document.all('FinItemType').value = '';
    		document.all('FinItemTypeName').value = '';
    		document.all('FinItemID').value = '';
    		document.all('FinItemName').value = '';
    		document.all('Remark').value = '';
    	 
				document.all('CostID').readOnly = false;	 
}