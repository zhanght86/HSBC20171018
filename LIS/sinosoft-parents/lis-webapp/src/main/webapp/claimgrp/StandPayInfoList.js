
/**
 * FUNCTION:StandPaySave()
 * DESC :����__Ԥ�������Ϣ
 */
 var mySql = new SqlClass();
function StandPaySave()
{
  if(fm.CaseNo.value==null || fm.CaseNo.value==""){
    alert("�����Ϊ�գ����ȱ����������Ϣ��");
    return false;
  }
  AffixGrid.delBlankLine();
  var nmulLineCount = AffixGrid.mulLineCount;
  if (nmulLineCount == null || nmulLineCount <= 0)
  {
    alert("����������һ��Ԥ����");
    return false;
  }
  if(AffixGrid.checkValue()==false){
	return false;
  }
  //У�鲻ͬ�е����ֲ�����ͬ����һ������ֻ��¼��һ����¼
  for(var i=1; i<=nmulLineCount; i++){
    var RiskCode = AffixGrid.getRowColData(i-1,1);
    for(var j=i+1; j<=nmulLineCount; j++){
      var RiskCode2 = AffixGrid.getRowColData(j-1,1);
      if(RiskCode2==RiskCode){
        alert("��"+i+"�����ֱ���͵�"+j+"�����ֱ�����ͬ��һ������ֻ��¼��һ����¼�����飡");
        return false;
      }
    }  
  }
  
    fm.fmtransact.value="INSERT||MAIN";
    var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
//    showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
    var iWidth=550;      //�������ڵĿ��; 
    var iHeight=250;     //�������ڵĸ߶�; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
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
   var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
   var iWidth=550;      //�������ڵĿ��; 
   var iHeight=350;     //�������ڵĸ߶�; 
   var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
   var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
   showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

   showInfo.focus();

   initList();
}

/**
 * FUNCTION:initList()
 * DESC :��ʼ��__Ԥ�������Ϣ�б�.
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
   alert("CaseAffixList.js-->initList�����з����쳣!"+ ex.message);
  }
}

function returnParent()
{
     top.opener.getGrpstandpay();
     top.close();
}