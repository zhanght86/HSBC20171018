//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="0";
var mOperate="";
var turnPage = new turnPageClass();
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


//Click事件，当点击“查询”图片时触发该函数
//进入版本信息查询页面的查询按钮
function queryClick1()
{
  //下面增加相应的代码
  //mOperate="QUERY||MAIN";  
  showInfo=window.open("./FrameVersionRuleQueryForOther.jsp");
}



//进入凭证类型查询页面的查询按钮
function queryClick2()
{
  //下面增加相应的代码
  //mOperate="QUERY||MAIN";  
  var VersionNo = document.all('VersionNo').value;
  var VersionState = document.all('VersionState').value;
    
  if (VersionNo == null||VersionNo == '')
  {
  	alert("请先进行版本信息查询，然后再进行凭证类型定义");
  	return;
  }    
	showInfo=window.open("./FrameCertificateTypeDefQuery.jsp?VersionNo=" + VersionNo + "&VersionState=" + VersionState );  
}


function addClick()
{
	  if (!beforeSubmit()) //beforeSubmit()函数
  {
  	return false;
  }
	//为了防止双击，点击增加后，屏蔽"增加"按钮
  mOperate="INSERT||MAIN";
  submitForm();
}


//Click事件，当点击“删除”图片时触发该函数
function deleteClick()
{
  //下面增加相应的删除代码
  if((fm.VersionNo.value=="")||(fm.VersionNo.value=="null"))
  {
    alert("请您录入版本编号！");
    return ;
  }
  if ((document.all("CertificateID").value==null)||(trim(document.all("CertificateID").value)==''))
  {
    alert("请确定要删除的凭证类型编号！");
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


//进入凭证费用类型定义页面
function intoCostTypeDef()
{
  //下面增加相应的代码
  //mOperate="QUERY||MAIN";
  var VersionNo = document.all('VersionNo').value;
  var VersionState = document.all('VersionState').value;
  var CertificateID = document.all('CertificateID').value;
    
  if (VersionNo == null||VersionNo == '')
  {
  	alert("请先进行版本信息查询，然后再进行凭证费用类型定义");
  	return;
  }
  
  if (CertificateID == null||CertificateID == '')  
  {
  	alert("请先进行科目信息查询，然后再进行凭证费用类型定义");
  	return;
  }          
	var countjudge = "";
  var strSQL = "";
  /**
  strSQL = "select count(*) from ficertificatetypedef where versionno = '"+document.all('VersionNo').value+"' and CertificateID = '"+document.all('CertificateID').value+"' ";
  */
  var mySql1=new SqlClass();
	  mySql1.setResourceName("fininterface.CertificateTypeDefInputSql"); //指定使用的properties文件名
	  mySql1.setSqlId("CertificateTypeDefInputSql1");//指定使用的Sql的id
	  mySql1.addSubPara(document.all('VersionNo').value);//指定传入的参数
	  mySql1.addSubPara(document.all('CertificateID').value);//指定传入的参数
	  strSQL= mySql1.getString();
  
  //alert(strSQL);
  countjudge = easyQueryVer3(strSQL, 1, 0, 1); 
 	countjudge = trim(countjudge.substr(4));
  //alert(countjudge);
  if(countjudge == "0")
  {
  	alert("该组凭证信息不存在，请确定完成添加该组信息后再进行凭证费用类型定义");
  	return false;
  }
  //alert(VersionNo);
  showInfo=window.open("./FrameCostTypeDefInput.jsp?VersionNo=" + VersionNo + "&VersionState=" + VersionState + "&CertificateID="+CertificateID);
	//parent.fraInterface.window.location = "./ProposalGrpInput.jsp?LoadFlag=" + LoadFlag + "&type=" + type;  
	//showInfo=window.open("./LABranchGroupQuery.jsp?BranchType='"+document.all('BranchType').value + "'");  
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
  	alert("CertificateTypeDefInput.js-->resetForm函数中发生异常:初始化界面错误!");
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
  fm.action="CertificateTypeDefSave.jsp";
  //lockButton(); 
  fm.submit(); //提交

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
    	 document.all('CertificateID').value = '';   	
    	 document.all('CertificateName').value = '';  	 
    	 document.all('DetailIndexID').value = '';
    	 document.all('DetailIndexName').value = '';
    	 document.all('AcquisitionType').value = '';
    	 document.all('AcquisitionTypeName').value = '';                                 
    	 document.all('Remark').value = ''; 
    	 
    	 document.all('CertificateID').readOnly = false;
    	 document.all('updatebutton').disabled = true;		
			 document.all('deletebutton').disabled = true; 
    }
    mOperate="";
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


//提交前的校验、计算
function beforeSubmit()
{
  if((fm.VersionNo.value=="")||(fm.VersionNo.value=="null"))
  {
    alert("请您录入版本编号！");
    return ;
  }

  if((fm.CertificateID.value=="")||(fm.CertificateID.value=="null"))
  {
    alert("请您录入凭证类型编号！");
    return ;
  }
  
  if((fm.DetailIndexID.value=="")||(fm.DetailIndexID.value=="null"))
  {
    alert("请您录入明细索引代码！");
    return ;
  }

  if((fm.AcquisitionType.value=="")||(fm.AcquisitionType.value=="null"))
  {
    alert("请您录入数据来源！");
    return ;
  }
  
  if (!verifyInput2())
  {
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



/*********************************************************************
 *  查询返回明细信息时执行的函数,查询返回一个2维数组，数组下标从[0][0]开始
 *  参数  ：  查询返回的二维数组
 *  返回值：  无
 *********************************************************************
 */
//针对版本信息查询子窗口返回的2维数组
function afterQuery( arrQueryResult )
{
	var arrResult = new Array();
		//alert(arrQueryResult);

	if( arrQueryResult != null )
	{
		arrResult = arrQueryResult;
		//alert(arrResult);
		document.all('VersionNo').value = arrResult[0][0];		
		//VersionState显示为01，02，03；而VersionState2显示为正常、维护、删除
		document.all('VersionState').value = arrResult[0][1];		
		document.all('VersionState2').value = arrResult[0][2];								
		//document.all('VersionNo').readOnly=true;
		//document.all('VersionState').readOnly=true;
			 document.all('CertificateID').value = '';   	
    	 document.all('CertificateName').value = '';  	 
    	 document.all('DetailIndexID').value = '';
    	 document.all('DetailIndexName').value = '';
    	 document.all('AcquisitionType').value = '';
    	 document.all('AcquisitionTypeName').value = '';                                 
    	 document.all('Remark').value = ''; 
		document.all('CertificateID').readOnly = false;
    //当版本状态不为02-维护的时候，增删改按钮为灰色		
		if (arrResult[0][1] == "01"||arrResult[0][1] == "03"||arrResult[0][1] == ""||arrResult[0][1] == null)
		{
			document.all('addbutton').disabled = true;				
			document.all('updatebutton').disabled = true;		
			document.all('deletebutton').disabled = true;	
		}
		if (arrResult[0][1] == "02")
		{
			document.all('addbutton').disabled = false;				
			document.all('updatebutton').disabled = true;		
			document.all('deletebutton').disabled = true;	
		}
		
		//来自于Common\javascript\Common.js，根据代码选择的代码查找并显示名称
		showCodeName(); 
		//a.VersionNo,a.VersionState
	}
}

function test()
{
	document.all('AcquisitionType').value = '01';
	showCodeName(); 
}

//针对凭证类型查询子窗口返回的2维数组
function afterQuery2( arrQueryResult )
{
	var arrResult = new Array();
		//alert(arrQueryResult);

	if( arrQueryResult != null )
	{
		arrResult = arrQueryResult;
		//alert(arrResult);
		//document.all('VersionNo').value = arrResult[0][0];
		document.all('CertificateID').value = arrResult[0][1];
		document.all('CertificateName').value = arrResult[0][2];		
		document.all('DetailIndexID').value = arrResult[0][3];
		document.all('DetailIndexName').value = arrResult[0][4];		
		document.all('AcquisitionType').value = arrResult[0][5];
		document.all('Remark').value = arrResult[0][6];
								
		document.all('CertificateID').readOnly = true;						
		
		//在凭证类型查询之后才能进行修改和删除操作
		document.all('updatebutton').disabled = false;		
		document.all('deletebutton').disabled = false;  	
		
    //当版本状态不为02-维护的时候，增删改按钮为灰色		
		if (document.all('VersionState').value == "01"||document.all('VersionState').value == "03"||document.all('VersionState').value == ""||document.all('VersionState').value == null)
		{
			document.all('addbutton').disabled = true;				
			document.all('updatebutton').disabled = true;		
			document.all('deletebutton').disabled = true;	
		}				

		//来自于Common\javascript\Common.js，根据代码选择的代码查找并显示名称
		showCodeName(); 
		//a.VersionNo,a.CertificateID,a.CertificateName,a.DetailIndexID,a.DetailIndexName,a.AcquisitionType,a.Remark
	}
}

function resetAgain()
{
			 document.all('CertificateID').value = '';   	
    	 document.all('CertificateName').value = '';  	 
    	 document.all('DetailIndexID').value = '';
    	 document.all('DetailIndexName').value = '';
    	 document.all('AcquisitionType').value = '';
    	 document.all('AcquisitionTypeName').value = '';                                 
    	 document.all('Remark').value = ''; 
    	 
    	 document.all('CertificateID').readOnly = false;
    	 document.all('updatebutton').disabled = true;		
			 document.all('deletebutton').disabled = true; 
}