
//Name:LLRptMAffixList.js
//Function����֤���ɳ���
//Date��2005-05-21 17:44:28
//Author ����־��
var mySql = new SqlClass();
function AffixSave()
{
   if(fm.RptNo.value ==''){
    alert("������δ�������ܱ��棡");
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
        alert("û�пɱ�������ݣ�");
        return false;
      }
      fm.Operate.value="Rpt||INSERT";
      submitForm();  
}
//�ύ����
function submitForm()
{
    var i = 0;
    var showStr = "���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
//    showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
    var iWidth=550;      //�������ڵĿ��; 
    var iHeight=250;     //�������ڵĸ߶�; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

    showInfo.focus();

    fm.submit(); //�ύ
    tSaveFlag ="0";
}

function afterSubmit( FlagStr, content )
{
    showInfo.close();
    if (FlagStr == "Fail" )
    {             
        var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
//        showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
        var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
        var iWidth=550;      //�������ڵĿ��; 
        var iHeight=350;     //�������ڵĸ߶�; 
        var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
        var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
        showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

        showInfo.focus();

    }
    else
    { 
        var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;  
//        showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
        var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
        var iWidth=550;      //�������ڵĿ��; 
        var iHeight=350;     //�������ڵĸ߶�; 
        var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
        var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
        showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

        showInfo.focus();

        //initList();
    }
}
//��ʼ�������ѯ
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
      alert("RptAffixList.js-->initList�����з����쳣:���ݲ�ѯ����!");
    }
}

function showMyPage(spanID,flag)
{
  if(!flag)//�ر�
  { 
    spanID.style.display="none";
  }
  else    //��
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
        alert("��ѡ�����赥֤");
        return;
      }
      if (codeCheck(affixCode)){
        alert("�õ�֤�Ѵ��ڣ�������ѡ��");
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
        alert("�����뵥֤����");
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
