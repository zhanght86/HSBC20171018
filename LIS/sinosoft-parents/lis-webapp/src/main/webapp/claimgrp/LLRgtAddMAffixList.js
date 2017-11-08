
//Name:LLRgtAddMAffixList.js
//Function：单证补充js程序
//Date：2005-07-1 17:44:28
//Author ：yuejw
var mySql = new SqlClass();
//初始化界面查询-----查询未回销的单证
function initQueryAffixGrid()
{
    try
    {
        var Proc=fm.Proc.value;
      strSQL="select affixcode,affixname,subflag,needflag,readycount,property,shortcount,returnflag,affixno from LLAffix where "
           + "rgtno='"+fm.ClmNo.value+"' and "
           + "customerno='"+fm.CustomerNo.value+"' and " 
           + "(subflag is null or subflag='1')";    
          /* mySql = new SqlClass();
mySql.setResourceName("claimgrp.LLRgtAddMAffixListInputSql");
mySql.setSqlId("LLRgtAddMAffixListSql1");
mySql.addSubPara(fm.ClmNo.value );  
mySql.addSubPara(fm.CustomerNo.value );    */
//      alert(strSQL);         
      arr=easyExecSql(strSQL);
        if(arr!=null)
        {
            displayMultiline(arr,AffixGrid);
        }
        else
        {
            AffixGrid.clearData();
            return;
        }

    }
    catch(ex)
    {
      alert("LLRgtAddMAffixList.js-->initList函数中发生异常:数据查询错误!");
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



//[生成单证按钮]-----当赔案在报案时没有生成单证，或直接进入立案时，用于生成单证信息（根据理赔类型）
function AffixBuild()
{
  /*var strSQL="select distinct b.affixcode,b.affixname,1,b.needflag,1,0 from LLMAppReasonAffix a,llmaffix b where a.reasoncode in (select ReasonCode from LLReportReason where "
                  + " RpNo = '"+fm.ClmNo.value+"'"
                  + " and CustomerNo = '"+fm.CustomerNo.value+"')"
                  + " and a.affixcode=b.affixcode";   */
   mySql = new SqlClass();
mySql.setResourceName("claimgrp.LLRgtAddMAffixListInputSql");
mySql.setSqlId("LLRgtAddMAffixListSql2");
mySql.addSubPara(fm.ClmNo.value );  
mySql.addSubPara(fm.CustomerNo.value );           
   var arr=easyExecSql(mySql.getString());
   if(arr!=null)
  {
      displayMultiline(arr,AffixGrid);
  }
  else
  {
      AffixGrid.clearData();
      return;
  }
}

//[增加单证按钮]-----用于增加单证信息
function addAffix()
{
  try
  {
    var affixCode="";
    var affixName="";
    var affixTypeCode="";
    var affixTypeName="";
    
    if(!fm.OtherAffix.checked){
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
      //var strSQL="select affixtypecode,affixtypename,needflag from llmaffix where affixcode='"+affixCode+"'";
      mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLRgtAddMAffixListInputSql");
		mySql.setSqlId("LLRgtAddMAffixListSql3");
		mySql.addSubPara(affixCode);  
      var arr=easyExecSql(mySql.getString());
      affixTypeCode=arr[0][1];
      affixTypeName=arr[0][2];  
      affixNeedFlag=arr[0][3];  
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
      affixNeedFlag="0";
    }
    AffixGrid.addOne();
    var rows=AffixGrid.mulLineCount;
    AffixGrid.setRowColData(rows-1,1,affixCode); //单证代码
    AffixGrid.setRowColData(rows-1,2,affixName); //单证名称
    AffixGrid.setRowColData(rows-1,4,"0");//单证是否必需
    AffixGrid.setRowColData(rows-1,5,"1");    //单证所需件数
    AffixGrid.setRowColData(rows-1,6,"0");    //提交形式
    clearData();
    showMyPage(AppAffix,false); 
  }
  catch(ex)
  {
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

//[返回]按钮
function returnParent()
{
//  top.opener.queryRegister();
  top.close();
}

//保存按钮
function AffixSave()
{
    var row = AffixGrid.mulLineCount;
    var i = 0;
      for(var m=0;m<row;m++ )
      {
        if(AffixGrid.getChkNo(m))
        {
          i=i+1;
        }
      }
      if(i==0)
      {
        alert("没有可保存的单证数据！");
        return false;
      }
    fm.Operate.value="RgtAdd||Insert";
    fm.action="LLRgtAddMAffixListSave.jsp";
    SubmitForm();  
}
function SubmitForm()
{
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
    returnParent();    //[返回]按钮   
    }

}