
//Name:LLRptMAffixList.js
//Function：单证生成程序
//Date：2005-05-21 17:44:28
//Author ：潘志豪
var mySql = new SqlClass();
function AffixSave()
{
   if(fm.RptNo.value ==''){
    alert("出险人未新增不能保存！");
    return false;
   }
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
        alert("没有可保存的数据！");
        return false;
      }
      fm.Operate.value="Rpt||INSERT";
      submitForm();  
}
//提交数据
function submitForm()
{
    var i = 0;
    var showStr = "正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
//    showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
    var name='提示';   //网页名称，可为空; 
    var iWidth=550;      //弹出窗口的宽度; 
    var iHeight=250;     //弹出窗口的高度; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

    showInfo.focus();

    fm.submit(); //提交
    tSaveFlag ="0";
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
//          var strSQL="select distinct b.affixcode,b.affixname,b.needflag from LLMAppReasonAffix a,llmaffix b where trim(a.reasoncode) in (select substr(ReasonCode,2,2) from LLReportReason where "
          /*var strSQL="select distinct b.affixcode,b.affixname,b.needflag from LLMAppReasonAffix a,llmaffix b where a.reasoncode in (select ReasonCode from LLReportReason where "
                  + " RpNo = '"+fm.RptNo.value+"'"
                  + " and CustomerNo = '"+fm.customerNo.value+"')"
                  + "  and a.affixcode=b.affixcode";*/
         mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLRptMAffixListInputSql");
		mySql.setSqlId("LLRptMAffixListSql1");
		mySql.addSubPara(fm.RptNo.value );   
		mySql.addSubPara(fm.customerNo.value );            
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
    catch(ex)
    {
      alert("RptAffixList.js-->initList函数中发生异常:数据查询错误!");
    }
}

function showMyPage(spanID,flag)
{
  if(!flag)//关闭
  { 
    spanID.style.display="none";
  }
  else    //打开
  {
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
     // var strSQL="select affixtypecode,affixtypename,needflag from llmaffix where affixcode='"+affixCode+"'";
      mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLRptMAffixListInputSql");
		mySql.setSqlId("LLRptMAffixListSql2");
		mySql.addSubPara(affixCode);   
		
      var arr=easyExecSql(mySql.getString());
      affixTypeCode=arr[0][0];
      affixTypeName=arr[0][1];  
      affixNeedFlag=arr[0][2];  
    }  
    else{ 
      affixCode="000000";
      affixName=fm.OtherName.value;
      if (affixName==""){
        alert("请输入单证名称");
        return;
      }
      affixNeedFlag="0";
    }
    AffixGrid.addOne();
    var rows=AffixGrid.mulLineCount;
    AffixGrid.setRowColData(rows-1,1,affixCode);
    AffixGrid.setRowColData(rows-1,2,affixName);
    AffixGrid.setRowColData(rows-1,3,affixNeedFlag);
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
