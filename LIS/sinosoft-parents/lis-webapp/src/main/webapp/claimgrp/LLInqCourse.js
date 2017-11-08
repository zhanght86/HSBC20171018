//程序名称：LLInqCourse.js
//程序功能：调查过程
//创建日期：2005-6-22

//更新记录：  更新人:yuejw    更新日期     更新原因/内容

//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="1";
var turnPage = new turnPageClass();
var k = 0;

//提交对应操作
function submitForm()
{
  var i = 0;
  var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
//  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
  var name='提示';   //网页名称，可为空; 
  var iWidth=550;      //弹出窗口的宽度; 
  var iHeight=250;     //弹出窗口的高度; 
  var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
  var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
  showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

  showInfo.focus();

  fm.submit(); //提交
}


//[过程或者费用录入]提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
  showInfo.close();
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
 //  showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
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
//    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
    var name='提示';   //网页名称，可为空; 
    var iWidth=550;      //弹出窗口的宽度; 
    var iHeight=350;     //弹出窗口的高度; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

    showInfo.focus();

    initInqCourseInfo();
    initInqFeeInfo();
    initLLInqCertificateInfo();
  }

}

//[调查确认]提交后操作,服务器数据返回后执行的操作
function afterComfirmSubmit( FlagStr, content )
{
  showInfo.close();
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
//    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
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
//    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
    var name='提示';   //网页名称，可为空; 
    var iWidth=550;      //弹出窗口的宽度; 
    var iHeight=350;     //弹出窗口的高度; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

    showInfo.focus();

    turnback();//返回
  }

}

// 初始化界面查询
function LLInqApplyQueryClick()
{
	if(document.all('ClmNo').value=="" ||document.all('InqNo').value=="")
	{
		alert("数据传送错误!");
	    return;
	}
	//汉化申请人姓名
	var strSQL = "";
    strSQL = "select clmno,inqno,batno,customerno,customername,vipflag,inqdept,initphase"
    	   +",inqrcode,inqitem,(select username from lduser where usercode = applyper),applydate,initdept, "
    	   +" (case locflag when '0' then '本地' when '1' then '异地' end), "
    	   +" inqdesc "
           +" from llinqapply where 1=1 "
           +" and clmno='"+document.all('ClmNo').value+"' and inqno='"+document.all('InqNo').value+"'"; 
    var arr = easyExecSql(strSQL);
    if (arr == null)
    {
	      alert("调查申请不存在!");
	      return;
    }
    else
    {
    	fm.ClmNo.value=arr[0][0]; //赔案号
        fm.InqNo.value=arr[0][1];  //调查序号
        fm.BatNo.value=arr[0][2]; //批次号
        fm.CustomerNo.value=arr[0][3]; //出险人客户号
        fm.CustomerName.value=arr[0][4];//出险人客户姓名
        fm.VIPFlag.value=arr[0][5];   //VIP客户
        fm.InqDept.value=arr[0][6];   //调查机构
        fm.InitPhase.value=arr[0][7]; //提起阶段
        fm.InqRCode.value=arr[0][8];  //调查原因
        fm.InqItem.value=arr[0][9];  //调查项目
        fm.ApplyPer.value=arr[0][10];  //申请人
        fm.ApplyDate.value=arr[0][11];  //申请日期
        fm.InitDept.value=arr[0][12];  //发起机构
        fm.LocFlag.value=arr[0][13];  //本地标志
        fm.InqDesc.value=arr[0][14];  //调查描述
        showOneCodeName('llinitphase','InitPhase','InitPhaseName');
        showOneCodeName('llinqreason','InqRCode','InqRCodeName');
        showOneCodeName('stati','InqDept','InqDeptName');   
        showOneCodeName('stati','InitDept','InitDeptName'); 
      //showOneCodeNameEx('LocFlag','LocFlag','LocFlagName'); 
    }
}
//添加保存调查过程信息
function AddInqCourseClick()
{
	var tInqMode=fm.InqMode.value;
	var tInqSite=fm.InqSite.value;
	var tInqDate=fm.InqDate.value;
	var tInqPer1=fm.InqPer1.value;
	var tInqByPer=fm.InqByPer.value; 
	var tInqCourse=fm.InqCourse.value; 
	var tInqPer1=fm.InqPer1.value;
	var tInqByPer=fm.InqByPer.value;
	//alert("tInqCourse"+tInqCourse);
    if (tInqMode=="" ||tInqSite == "" || tInqCourse== "")
    {
        alert("调查方式、地点、调查过程填写不完整！");
        return;
    }
//    if (tInqPer1=="" ||tInqByPer == "" || tInqCourse== "")
//    {
//        alert("调查人、被调查人、填写不完整！");
//        return;
//    }
    //得到系统当前时间函数的函数为getCurrentDate()，用于校验调查日期
    if((fm.InqDate.value!="") && (dateDiff(mCurrentDate,fm.InqDate.value,'D') > 0))
    {
    	alert("调查日期不能晚于当前日期！");
    	return;
    }
    
  
    //第1调查人必须录入
    if(tInqPer1==""||tInqPer1==null)
    {
    	alert("必须录入第一调查人！");
    	return;
    }
    
    //被调查人必须录入
    if(tInqByPer==""||tInqByPer==null)
    {
    	alert("必须录入被调查人！");
    	return;
    }
    
    //检查 调查过程单证信息
    var rowNum=LLInqCertificateGrid.mulLineCount ; //行数
    if(rowNum==0)
    {
    	if(confirm("你还没有录入调查过程单证信息？是否需要录入?")==true)
    	{
//    		alert("录入单证");
    		showInqCerficate();
    		return;
    	}
    }
	fm.action="LLInqCourseSave.jsp";
	submitForm();
}
//查询调查过程信息
function InqCourseQueryClick()
{
	 var strSql="select * from llinqcourse where 1=1 "
	 		+" and clmno='"+fm.ClmNo.value+"' "
	 		+" and inqno='"+fm.InqNo.value+"'  "
	 		+" order by clmno" ;
	
	 //prompt("查询调查过程信息",strSql);
	 var arr = easyExecSql(strSql);
	//alert("arr="+arr);
	 if (arr == null)
    {
	      alert("该调查申请还没有任何调查过程信息!");
	      return;
    }
    //弹出查看调查过程信息页面
	var strUrl="LLInqCourseQueryMain.jsp?ClmNo="+fm.ClmNo.value+"&InqNo="+fm.InqNo.value;
    //System.out.println(strUrl);
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}
//添加保存调查费用信息
function AddInqFeeClick()
{
    if (fm.FeeType.value == "" || fm.FeeSum.value == "" ||fm.FeeDate.value=="")
    {
        alert("费用类型、费用金额、发生时间不完整！");
        return;
    }
//    if (fm.Payee.value == "" || fm.PayeeType.value == "" )
//    {
//       alert("领款人、领款方式填写不完整！");
//        return;
//    }
    if (fm.FeeSum.value!="" && !isNumeric(fm.FeeSum.value))
    {
        alert("费用金额填写有误！");
        return;
    }
    //得到系统当前时间函数的函数为getCurrentDate()，用于校验费用发生时间
    if( (fm.FeeDate.value!="")&& (dateDiff(mCurrentDate,fm.FeeDate.value,'D') > 0))
    {
    	alert("费用发生日期不能晚于当前日期！");
    	return;
    }
	fm.fmtransact.value = "INSERT";
	fm.action="LLInqFeeSave.jsp";
	submitForm();

}
//查询调查费用信息
function QueryInqFeeClick()
{
	 var strSql="select * from llinqfee where 1=1 "
 			+" and clmno='"+fm.ClmNo.value+"' "
 			+" and inqno='"+fm.InqNo.value+"'  "
 			+" order by clmno" ;
	 var arr = easyExecSql(strSql);
	 if (arr == null)
    {
	      alert("该调查申请还没有任何费用信息!");
	      return;
    }
    //弹出查看调查过程信息页面
	var strUrl="LLInqFeeQueryMain.jsp?ClmNo="+fm.ClmNo.value+"&InqNo="+fm.InqNo.value;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}
//调查完成标志
function MoreInqClick()
{
	if (fm.MoreInq.checked==true)
    {
    	fm.InqState.value = "1";//调查完成标示
        fm.InqConclusion.disabled = false;
        fm.InqConfirm.disabled = false;        
    }
    else
    {
        fm.InqState.value = "0";//调查未完成标示
        fm.InqConclusion.disabled = true;
        fm.InqConfirm.disabled = true;  
    }	  	  
}
//返回队列页面
function turnback()
{
    var strUrl= "LLInqCourseMissInpute.jsp?";    
    location.href=strUrl;
}

function InqConfirmClick()
{
	  var strinqcourse="select * from llinqcourse where 1=1"
	             + getWherePart('ClmNo','ClmNo')
                 + getWherePart('InqNo','InqNo')
                 + " order by ClmNo";      
      var arrinqcourse=easyExecSql(strinqcourse);
      if(arrinqcourse==null)
      {
      	alert("没有任何调查过程，调查不可以完成！");	
      	return;
      }            
	  var strSQL = "";//查询结论序号
	  strSQL ="select max(ConNo) from Llinqconclusion where 1=1"
	        +" and clmno='"+document.all('clmno').value+"'"    //赔案号
	        +" and batno='"+document.all('batno').value+"'"    //调查批次
	        +" and inqdept='"+document.all('inqdept').value+"'"; //发起机构
	  var arr=easyExecSql(strSQL);  
      fm.ConNo.value = arr;
	  if(fm.InqConclusion.value =="" || fm.InqConclusion.value ==null)
	  {
		  alert("请填写调查结论!");
	      return;  
	  }
    fm.fmtransact.value = "UPDATE";
	fm.action="LLInqConfirmSave.jsp";
	submitForm();    
}
function AddAffixClick()
{
      var tClmNo=document.all("ClmNo").value;
      var urlStr;
      urlStr="./LLInqCourseAffixFrame.jsp?ClmNo="+tClmNo+""; 
      window.open(urlStr,"",'height=100, width=400, top=0, left=0, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=n o, status=no');   	
}

function LoadAffixClick()
{
      var tClmNo=document.all("ClmNo").value;
      var urlStr;
      urlStr="./LLInqCourseShowAffixFrame.jsp?ClmNo="+tClmNo+""; 
      window.open(urlStr,"",'height=100, width=400, top=0, left=0, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=n o, status=no');   	
}

/*****************************************************************
以下为添加--“调查过程单证信息”部分----2005-08-28添加修改
******************************************************************/
//[确认按钮]--------录入调查过程时向表格中添加 调查过程单证信息
function addInqCertificate()
{
	var affixCode="";   
	var affixName="";
	if(!fm.checkbox.checked)
	{
	    affixCode=fm.AffixCode.value;
	    affixName=fm.AffixName.value;
		if (affixCode=="")
		{
			alert("请选择所需单证");
			return;
		}
		if (codeCheck(affixCode))
		{
	        alert("该单证已存在，请重新选择");
	        return;
	    }
	}
	else
	{
	  affixCode="000000";
      affixName=fm.OtherName.value;
      if (affixName=="")
      {
        alert("请输入单证名称");
        return;
      }
	}

	LLInqCertificateGrid.addOne();
	var rows=LLInqCertificateGrid.mulLineCount;
	LLInqCertificateGrid.setRowColData(rows-1,1,affixCode);
	LLInqCertificateGrid.setRowColData(rows-1,2,affixName);
}
//[校验单证类型]
function codeCheck(mcode)
{
	var rows=LLInqCertificateGrid.mulLineCount;
	var affixCode="";
	for (var i=0;i<rows;i++)
	{
	  	affixCode=LLInqCertificateGrid.getRowColData(i,1);
	      if (affixCode==mcode)
	      {
	    	return true;
	      }
	}
}


function checkboxClick()
{   
	if(fm.checkbox.checked==true)
	{
//		alert("其他单证");
		fm.AffixCode.value="";
		fm.AffixName.value="";
		fm.AffixCode.disabled=true;
		fm.OtherName.value="";
		fm.OtherName.disabled=false;
	}
	else
	{
//		alert("标准单证");
		fm.AffixCode.value="";
		fm.AffixName.value="";
		fm.AffixCode.disabled=false;
		fm.OtherName.value="";
		fm.OtherName.disabled=true;
	}
	
}

function cancelInqCerficate()
{
	DivInqCertificate.style.display="none";
}

function showInqCerficate()
{
	
	fm.AffixCode.value="";
	fm.AffixName.value="";
	fm.checkbox.checked=false;
	fm.OtherName.value="";
	fm.OtherName.disabled=true;
	DivInqCertificate.style.display="";
}

/*****************************************************************
以下为添加--“查询其他调查信息”部分----2005-10-6添加修改
******************************************************************/
//[查询其他调查信息]--------查询其他调查信息
function queryOtherInqClick()
{
	var strUrl="LLInqOtherQueryMain.jsp";
	strUrl=strUrl+"?ClmNo="+fm.ClmNo.value;
	strUrl=strUrl+"&BatNo="+fm.BatNo.value;
	strUrl=strUrl+"&InqNo="+fm.InqNo.value;
	strUrl=strUrl+"&InqDept="+fm.InqDept.value;
	strUrl=strUrl+"&Type=0";
//	window.open(strUrl,"查询其他调查信息");
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}
