
/**
 * FUNCTION:StandPaySave()
 * DESC :保存__预估金额信息
 */
 var mySql = new SqlClass();
function StandPaySave()
{
  if(fm.CaseNo.value==null || fm.CaseNo.value==""){
    alert("理赔号为空，请先保存出险人信息！");
    return false;
  }
  AffixGrid.delBlankLine();
  var nmulLineCount = AffixGrid.mulLineCount;
  if (nmulLineCount == null || nmulLineCount <= 0)
  {
    alert("请至少输入一个预估金额！");
    return false;
  }
  if(AffixGrid.checkValue()==false){
	return false;
  }
  //校验不同行的险种不能相同，即一个险种只能录入一条记录
  for(var i=1; i<=nmulLineCount; i++){
    var RiskCode = AffixGrid.getRowColData(i-1,1);
    for(var j=i+1; j<=nmulLineCount; j++){
      var RiskCode2 = AffixGrid.getRowColData(j-1,1);
      if(RiskCode2==RiskCode){
        alert("第"+i+"行险种编码和第"+j+"行险种编码相同，一个险种只能录入一条记录，请检查！");
        return false;
      }
    }  
  }
  
    fm.fmtransact.value="INSERT||MAIN";
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

    fm.action ="./LLAffixListSave.jsp";
    fm.submit();
}

function afterSubmit(FlagStr, content)
{
   showInfo.close();
   var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
//   showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
   var name='提示';   //网页名称，可为空; 
   var iWidth=550;      //弹出窗口的宽度; 
   var iHeight=350;     //弹出窗口的高度; 
   var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
   var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
   showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

   showInfo.focus();

   initList();
}

/**
 * FUNCTION:initList()
 * DESC :初始化__预估金额信息列表.
 */
function initList()
{
   try
  {
  //var strSQL1="  select a.riskcode,b.riskname,a.standpay from LLStandPayInfo a,lmrisk b  where trim(a.riskcode) = b.riskcode and a.CaseNo = '"+fm.CaseNo.value+"'";
  mySql = new SqlClass();
	mySql.setResourceName("claimgrp.StandPayInfoListSql");
	mySql.setSqlId("StandPayInfoListSql1");
	mySql.addSubPara(fm.CaseNo.value ); 
  arr1=easyExecSql(mySql.getString());
  if(arr1)
    displayMultiline(arr1,AffixGrid);
  }
  catch(ex)
  {
   alert("CaseAffixList.js-->initList函数中发生异常!"+ ex.message);
  }
}

function returnParent()
{
     top.opener.getGrpstandpay();
     top.close();
}