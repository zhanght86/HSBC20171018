
var showInfo;
var mDebug="0";
var FlagDel;//在delete函数中判断删除的是否成功

function afterSubmit( FlagStr, content )
{
  FlagDel = FlagStr;

    showInfo.close();
    if (FlagStr == "Fail" )
    {
      var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
			//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
			var name='提示';   //网页名称，可为空; 
			var iWidth=550;      //弹出窗口的宽度; 
			var iHeight=250;     //弹出窗口的高度; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
			showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
}
    else
    {
    	showDiv(inputButton,"false");
    }
}

function afterCodeSelect( cCodeName, Field )
{
 //alert("*****"+ cCodeName +"  " + Field.value);
 if(cCodeName == "AppReportType")
 { 
    fm.AppManageType.value="";
   /* if( Field.value=="App11" || Field.value=="App12" )
    {
      fm.AppManageType.CodeData ="0|^4|四级机构统计"; 
    }
  else*/
    if(comcode = "86")
    {
     fm.AppManageType.CodeData ="0|^2|二级机构统计^3|三级机构统计^S|南区^N|北区";
    }
    else
    {
     fm.AppManageType.CodeData ="0|^2|二级机构统计^3|三级机构统计";
    }
  // alert(fm.AppManageType.CodeData);
  
    /* if(Field.value=="App09")
     {
     	 alert("目前不支持体检件统计!");
     	 fm.AppReportType.value="";
     }*/
     
     if(Field.value=="App02")
     {
     	 divRisk.style.display="";
     }
     else
     {
     	divRisk.style.display="none";
     }
  }
}

//显示frmSubmit框架，用来调试
function showSubmitFrame(cDebug)
{
    if(cDebug=="1")
    {
			parent.fraMain.rows = "0,0,0,0,*";
    }
    else
    {
  		parent.fraMain.rows = "0,0,0,0,*";
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

function PrintData()
{
  if((fm.StartDate.value=="")||(fm.EndDate.value=="")||(fm.StartDate.value=="null")||(fm.EndDate.value=="null"))
  {
    alert("请输入保单生效起始日期和结束日期");
    return false;
  } 
  
  if( fm.AppReportType.value == "" || fm.AppReportType.value ==null )
  {
  	alert("报表类型不能为空！");
    return false;
  }
  
  if( fm.AppManageType.value == "" || fm.AppManageType.value ==null )
  {
  	alert("机构类型不能为空！");
    return false;
  }
  if( fm.AppSalechnl.value == "" || fm.AppSalechnl.value ==null )
  {
  	alert("销售渠道不能为空！");
    return false;
  }
  
  //保单生效开始日期不能大于结束日期
  if(compareDate(fm.StartDate.value,fm.EndDate.value)==1) 
   {
     alert("开始日期不能大于结束日期！");	
    return false;                       
   }
 // alert("comcode.length :"+comcode.length+"\n fm.ManageType.value :"+ fm.ManageType.value);
  //操作人员在统计二级机构时其登陆机构必须不能超过4位（如86，8611）

   if(comcode.length>2*(fm.AppManageType.value)) 
    {
      alert("您的登陆机构无权打印您选择的报表类型！");
      return false;
    } 
   var tNoticeStr = "开始日期:  "+fm.StartDate.value+" "+fm.StartTime.value+" \n结束日期:  "+fm.EndDate.value+" "+fm.EndTime.value+"\n报表类型:  "+fm.ReportName.value+"\n机构类型:  "+fm.ManageName.value+"\n销售渠道:  "+fm.SalechnlName.value;
   if(divRisk.style.display=="")
   {
   		if(fm.RiskCode.value==null || fm.RiskCode.value=='')
   			tNoticeStr = tNoticeStr +"\n险种编码:  所有";
   		else
   			tNoticeStr = tNoticeStr +"\n险种编码:  "+fm.RiskCode.value;
   }
   
    if(!confirm("您输入的统计条件为：\n\n"+tNoticeStr))
      return false;
    	
    var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    //fm.action = './XQBankSuccPrint.jsp';
    fm.target="f1print";
    showInfo.close();
    document.getElementById("fm").submit();
  
}

