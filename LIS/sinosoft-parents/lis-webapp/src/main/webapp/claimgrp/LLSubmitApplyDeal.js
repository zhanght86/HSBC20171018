var showInfo;
var mDebug="1";
var tSaveFlag = "0";
var turnPage = new turnPageClass();
var mOperate = "";

function easyQueryClick() 
{  	
    var strSQL = "";
    strSQL = "select ClmNO,SubNO,SubCount,CustomerNo,Customername,VIPFlag,InitPhase,SubType,SubRCode,SubDesc,SubPer,SubDate,SubDept,SubState,DispDept,DispPer,DispIdea from llsubmitapply"// where SubCount=1 and disptype is null";
           +" where 1=1 and ClmNO='"+document.all('ClmNo').value+"'"
           +" and SubNO='"+document.all('SubNO').value+"'"
           +" and SubCount='"+document.all('SubCount').value+"'"; 
    var arr = easyExecSql(strSQL );
    if (arr == null)
    {
	      alert("报案不存在!");
	      return;
    }
    else
    {
    	fm.ClmNo.value=arr[0][0]; //赔案号
        fm.SubNO.value=arr[0][1];  //呈报序号
        fm.SubCount.value=arr[0][2]; //呈报次数
        fm.CustomerNo.value=arr[0][3]; //出险人客户号
        fm.CustomerName.value=arr[0][4];//出险人客户姓名
        fm.VIPFlag.value=arr[0][5];   //VIP客户
        fm.InitPhase.value=arr[0][6]; //提起阶段
        fm.SubType.value=arr[0][7];    //呈报类型
        fm.SubRCode.value=arr[0][8];  //呈报原因
        fm.SubDesc.value=arr[0][9];  //呈报描述
        fm.SubPer.value=arr[0][10];   //呈报人
        fm.SubDate.value=arr[0][11];  //呈报日期
        fm.SubDept.value=arr[0][12];  //呈报机构
        fm.SubState.value=arr[0][13]; //呈报状态
        fm.DispDept.value=arr[0][14]; //承接机构代码
        fm.DispPer.value=arr[0][15];  //承接人员编号    	
        fm.HeadIdea.value=arr[0][16];  //总公司处理意见  -----2005-08-14添加
        showOneCodeName('llinitphase','InitPhase','InitPhaseName');    
        showOneCodeName('llsubtype','SubType','SubTypeName');    
        showOneCodeName('llsubstate','SubState','SubStateName');    
        showOneCodeName('station','SubDept','SubDeptName');    
    }
}     
function afterCodeSelect( cName, Filed)   //CodeSelect处理程序
{
    if(cName=='lldisptype')           //
    {
    	var disType = fm.DispType.value;
        if(disType=="0")       	//提起调查
        {
 	    	fm.fmtransact.value = "UPDATE||Investgate";   //修改记录
      	   document.all('DispIdea').value="";  
      	   DivDispType.style.display="none";  
      	   
      	   document.all('ReportheadSubDesc').value="";  
      	   DivReportheadSubDesc.style.display="none";    
 	    	//弹出发起调查页面
 	    	var strUrl="LLInqApplyMain.jsp?claimNo="+fm.ClmNo.value+"&custNo="+fm.CustomerNo.value+"&custName="+fm.CustomerName.value+"&custVip="+fm.VIPFlag.value+"&initPhase=03";
            window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
        }
       if(disType=="1")  //呈报总公司按钮，添加一条记录（原选中记录的呈报次数加"1"）
       {       		
	       fm.fmtransact.value = "INSERT||Reporthead";   //添加记录   
      	   
      	   document.all('DispIdea').value="";  
      	   DivDispType.style.display="none";  
      	   
      	   document.all('ReportheadSubDesc').value="";  
      	   DivReportheadSubDesc.style.display="";         	
       }
       if(disType=="2")//回复意见
       {    
       	    DivReportheadSubDesc.style.display="none";  
       		document.all('ReportheadSubDesc').value="";  
       	
       		fm.fmtransact.value = "UPDATE||Replyport";   //修改记录
       		document.all('DispIdea').value="";  
       		DivDispType.style.display="";

       }  
    }
}

//"呈报确认"提交按钮
function Replyport()
{
	var disType = fm.DispType.value;
	if(disType=="" || disType==null)
	{
		alert("选择呈处理类型");
		return;
	}

    if(disType=="0")       	//提起调查
    {
    	fm.fmtransact.value = "UPDATE||Investgate";   //修改记录
    }
   if(disType=="1")  //呈报总公司按钮，添加一条记录（原选中记录的呈报次数加"1"）
   {       		
       fm.fmtransact.value = "INSERT||Reporthead";   //添加记录   
   	    if(fm.ReportheadSubDesc.value=="" ||fm.ReportheadSubDesc.value==null)
   	    {
   	    	alert("请填写呈报原因!");
   	    	return;
   	    }       
   	   //fm.SubType.value="1";    //呈报类型（分公司到总公司）
   	     　
   }
   if(disType=="2")//回复意见
   {    
   	    if(fm.DispIdea.value=="" ||fm.DispIdea.value==null)
   	    {
   	    	alert("请填写回复意见!");
   	    	return;
   	    }
   		fm.fmtransact.value = "UPDATE||Replyport";   //修改记录
   }     	 
	submitForm();   //提交页面数据
}
function TurnBack()
{
    var strUrl= "LLSubmitApplyDealMissInput.jsp?";
    location.href=strUrl;
}
  //提交数据
function submitForm()
{
   var i = 0;
   var showStr = "正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
   var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
//   showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
   var name='提示';   //网页名称，可为空; 
   var iWidth=550;      //弹出窗口的宽度; 
   var iHeight=250;     //弹出窗口的高度; 
   var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
   var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
   showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

   showInfo.focus();

   //  showSubmitFrame(mDebug);
    fm.action = './LLSubmitApplyDealSave.jsp';
    fm.submit(); //提交
    tSaveFlag ="0";   
}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
    showInfo.close();
    if (FlagStr == "Fail" )
    {
        var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
//        showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
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
 //       showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
        var name='提示';   //网页名称，可为空; 
        var iWidth=550;      //弹出窗口的宽度; 
        var iHeight=350;     //弹出窗口的高度; 
        var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
        var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
        showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

        showInfo.focus();

        TurnBack();//[成功则返回队列页面]
    }
    tSaveFlag ="0";
}
