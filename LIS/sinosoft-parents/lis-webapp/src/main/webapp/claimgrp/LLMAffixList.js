
//Name:LLMAffixList.js
//Function：单证生成及回销js程序
//Date：2005-05-21 17:44:28
//Author ：潘志豪

function AffixSave()
{
    var row = AffixGrid.mulLineCount;
    var i = 0;
    //var arr = new Array();  
    switch(fm.Proc.value){
    case "Rpt":
      for(var m=0;m<row;m++ )
      {
        if(AffixGrid.getChkNo(m))
        {
          i=i+1;
        }
      }
      if(i==0)
      {
        alert("没有可保存的数据！");
        return false;
      }
      fm.Operate.value="Rpt||INSERT";
      break;
    case "RgtAdd":
      fm.Operate.value="RgtAdd||INSERT";
      break;
    case "Rgt":
      fm.Operate.value="Rgt||UPDATE";
    }
    var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
//    showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
    var name='提示';   //网页名称，可为空; 
    var iWidth=550;      //弹出窗口的宽度; 
    var iHeight=250;     //弹出窗口的高度; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

    showInfo.focus();

    //fm.action ="./LLMAffixListSave.jsp?RgtNo="+fm.RgtNo.value+"&CaseNo="+fm.CaseNo.value+"&Reason="+fm.Reason.value;
    fm.submit();  
}

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
//        showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
        var name='提示';   //网页名称，可为空; 
        var iWidth=550;      //弹出窗口的宽度; 
        var iHeight=350;     //弹出窗口的高度; 
        var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
        var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
        showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

        showInfo.focus();

        //initList();
    }
}
//初始化界面查询
function initList()
{
    try
    {
        var Proc=fm.Proc.value;
        var strSQL="";
        var arr1=null;
        switch(Proc){
        case "Rpt":	
          var claimTypes=fm.claimTypes.value.split(",");
          var types="";
          for (var i=0;i<claimTypes.length;i++){
          	if (types=="")
          		types+="'"+claimTypes[i]+"'";
          	else 
          		types+=",'"+claimTypes[i]+"'";
          }
          strSQL="select distinct b.affixcode,b.affixname,1,b.needflag from LLMAppReasonAffix a,llmaffix b where a.reasoncode in ("+types+") and a.affixcode=b.affixcode";
          break;
        case "Rgt":
          strSQL="select affixcode,affixname,subflag,needflag,readycount,shortcount,property,returnflag,affixno from LLAffix where rgtno='"+fm.CaseNo.value+"' and customerno='"+fm.whoNo.value+"'";        	
          break;
        case "RgtAdd":	        
	  strSQL="select affixcode,affixname,subflag,needflag,readycount,shortcount,property,returnflag,affixno from LLAffix where rgtno='"+fm.CaseNo.value+"' and customerno='"+fm.whoNo.value+"'";        	
	  break;
	  default:
	}
	arr1=easyExecSql(strSQL);
        //alert(arr1);
        if(arr1!=null){
            displayMultiline(arr1,AffixGrid);
            var mValue="";
            for (var j=0;j<AffixGrid.mulLineCount;j++){
            	mValue=AffixGrid.getRowColData(j,3);
            	if (mValue=="0"){
            	  AffixGrid.setRowColData(j,3,"是");
            	}
            	else if (mValue=="1"){
            	  AffixGrid.setRowColData(j,3,"否");
            	}
            	
            	mValue=AffixGrid.getRowColData(j,4);
            	if (mValue=="0"){
            	  AffixGrid.setRowColData(j,4,"是");
            	}
            	else if (mValue=="1"){
            	  AffixGrid.setRowColData(j,4,"否");
            	}
            	  
            	mValue=AffixGrid.getRowColData(j,7);
            	if (mValue=="0"){
            	  AffixGrid.setRowColData(j,7,"原件");
            	}
            	else if (mValue=="1"){
            	  AffixGrid.setRowColData(j,7,"复印件");
            	}

            	mValue=AffixGrid.getRowColData(j,8);
            	if (mValue=="0"){
            	  AffixGrid.setRowColData(j,8,"是");
            	}
            	else if (mValue=="1"){
            	  AffixGrid.setRowColData(j,8,"否");
            	}
            }
            //AffixGrid.checkBoxAll();
        }
        else
        {
            AffixGrid.clearData();
            return;
        }
//        var strSQL2="select AffixType,AffixCode,AffixName,SupplyDate,Property,Count,ShortCount,AffixNo,SerialNo from LLAffix";
//        arr2=easyExecSql(strSQL2);
//        var m=0;
//        var n=0;
//        var q=arr2.length;
//        if(arr2!=null)
//        {      
//            for(m=0;m<arr1.length;m++)
//            {        
//                for(n=0;n<q;n++)
//                {
//                    if(AffixGrid.getRowColData(m,10)==arr2[n][0]&&AffixGrid.getRowColData(m,2)==arr2[n][1])
//                    {
//                        AffixGrid.setRowColData(m,4,arr2[n][3]);
//                        AffixGrid.setRowColData(m,6,arr2[n][5]);
//                        AffixGrid.setRowColData(m,7,arr2[n][6]);
//                    }
//                }
//            }        
//        }

    }
    catch(ex)
    {
//      alert("CaseAffixList.js-->initList函数中发生异常:数据查询错误!");
    }
}

function showMyPage(spanID,flag)
{
  if(!flag)
  {
    //关闭
    spanID.style.display="none";
  }
  else
  {
    //打开
    spanID.style.display="";
  }
}

function addAffix(){
  try{
    var affixCode="";
    var affixName="";
    var affixTypeCode="";
    var affixTypeName="";
    
    if(!fm.OtherAffix.checked){
      affixCode=fm.AffixCode.value;   
      affixName=fm.AffixName.value;
      if (affixCode==""){
        alert("请选择所需单证");
        return;
      }
      if (codeCheck(affixCode)){
        alert("该单证已存在，请重新选择");
        return;
      }
      var strSQL="select affixtypecode,affixtypename,needflag from llmaffix where affixcode='"+affixCode+"'";
      //alert(strSQL);
      var arr=easyExecSql(strSQL);
      //alert(arr);
      affixTypeCode=arr[0][0];
      affixTypeName=arr[0][1];  
      affixNeedFlag=arr[0][2];  
      if(affixNeedFlag=="0")
      	affixNeedFlag="是";
      else if(affixNeedFlag=="1")
      	affixNeedFlag="否";	
    }  
    else{ 
      affixCode="99";
      affixName=fm.OtherName.value;
      if (affixName==""){
        alert("请输入单证名称");
        return;
      }
      affixNeedFlag="否";
    }
    AffixGrid.addOne();
    var rows=AffixGrid.mulLineCount;
    AffixGrid.setRowColData(rows-1,1,affixCode);
    AffixGrid.setRowColData(rows-1,2,affixName);
    AffixGrid.setRowColData(rows-1,4,affixNeedFlag);
    clearData();
    showMyPage(AppAffix,false); 
  }
  catch(ex){
  }  
}

function clearData()
{
  fm.AffixCode.value="";
  fm.AffixName.value=""; 
  fm.AffixCode.disabled=false;	
  fm.AffixName.disabled=false;	
  fm.OtherAffix.checked=false;
  fm.OtherName.value="";
  fm.OtherName.disabled=true;
}

function codeCheck(mcode)
{
  try
  {
	    var rows=AffixGrid.mulLineCount;
	    var affixCode="";
	    for (var i=0;i<rows;i++)
	    {
	      	affixCode=AffixGrid.getRowColData(i,1);
		      if (affixCode==mcode)
		      {
		    	return true;
		      }
    }
  }
  catch(ex)
  {
  }
}
