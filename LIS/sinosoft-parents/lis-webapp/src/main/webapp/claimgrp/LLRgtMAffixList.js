//Name:LLRgtMAffixList.js
//Function����֤����js����
//Date��2005-07-1 17:44:28
//Author ��yuejw
var mySql = new SqlClass();
//��ʼ��[�Ѿ�������֤]��ѯ
function initRgtAffixGridQuery()
{
    try
    {
        var Proc=fm.Proc.value;
        strSQL="select affixno,affixcode,affixname,subflag,(case needflag when '0' then '0-��' when '1' then '1-��'  end ),readycount,shortcount,"
               +"(case property when '0' then '0-ԭ��' when '1' then '1-��ӡ��' end ),"
               +"(case returnflag when '0' then '0-��' when '1' then '1-��' end ),"
               +"(case affixconclusion when '0' then '0-��ȫ' when '1' then '1-����ȫ' end ),affixreason from llaffix where 1=1 "
               + " and rgtno='"+fm.ClmNo.value+"' "
               + " and customerno='"+fm.CustomerNo.value+"' "         
             + " and subflag='0'";   
    /* mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLRgtMAffixListInputSql");
	mySql.setSqlId("LLRgtMAffixListSql1");
	mySql.addSubPara(fm.ClmNo.value ); 
	mySql.addSubPara(fm.CustomerNo.value ); */
      arr=easyExecSql(strSQL);
        if(arr!=null)
        {
            displayMultiline(arr,RgtAffixGrid);
        }
        else
        {
            RgtAffixGrid.clearData();
            return;
        }

    }
    catch(ex)
    {
      alert("LLRgtMAffixList.js-->initRgtAffixGridQuery�����з����쳣:���ݲ�ѯ����!");
    }
}

//��ʼ��[��������֤]��ѯ
function initAffixGridQuery()
{
    try
    {
      //returnflag,affixconclusion,affixreason----��ѯ���ɾ�����ֶΣ�2005-08-09�޸ģ�
        var Proc=fm.Proc.value;
      strSQL="select affixno,affixcode,affixname,subflag,(case needflag when '0' then '0-��' when '1' then '1-��'  end ),readycount,shortcount,property from llaffix where 1=1 and"
               + " rgtno='"+fm.ClmNo.value+"' and"
               + " customerno='"+fm.CustomerNo.value+"' and"         
             + " (subflag is null or subflag='1')";   
       /*mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLRgtMAffixListInputSql");
	mySql.setSqlId("LLRgtMAffixListSql2");
	mySql.addSubPara(fm.ClmNo.value ); 
	mySql.addSubPara(fm.CustomerNo.value ); */
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
      alert("LLRgtMAffixList.js-->initAffixGridQueryt�����з����쳣:���ݲ�ѯ����!");
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

//[����]��ť
function returnParent()
{
  top.opener.queryRegister();
  top.close();
}

//[�ж��Ƿ���ѡ�е��м�����ѡ���е�������д�Ƿ�����]-----��AffixSave()����
function CheckBeforeSave()
{
    var row = AffixGrid.mulLineCount;
    var i = 0;
  for(var m=0;m<row;m++ )
  {
    if(AffixGrid.getChkNo(m))
    {
      i=i+1;
      var n=m+1;
        var tAffixConclusion=AffixGrid.getRowColData(m,10);//[��֤����]
        var tAffixReason=AffixGrid.getRowColData(m,11);//[��֤����ȫԭ��]
        if(tAffixConclusion=="" ||tAffixConclusion==null)//[����Ƿ�ѡ���˵�֤������]
      {
        alert("��"+n+"��û��ѡ��֤�����ۣ�");
        return false;
      }
      //[������Ϊ��ȫʱ��������д ����ȫԭ��]
      if(tAffixConclusion=="0")
      {
        AffixGrid.setRowColData(m,11,"")
      }
      //[���Ϊ����ȫʱ���Ƿ���д�˲���ȫԭ��]
      if(tAffixConclusion=="1" && tAffixReason=="")
      {
        alert("��"+n+"��û����д��֤�첻��ȫԭ��");
        return false;
      }  
      AffixGrid.setRowColData(m,4,"0");//�á�0--�ύ��־��
    }
  }
  if(i==0)
  {
    alert("��û��ѡ������κε�֤��");
    return false;
  }
}

//[����]��ť-----����ѡ�еĽ�����Ϣ�����л���
function AffixSave()
{
  if(CheckBeforeSave()==false)//[��������ǰ���м��]
  {
    return;
  }
    fm.Operate.value="Rgt||UPDATE";
    fm.action="LLRgtMAffixListSave.jsp";
    SubmitForm();  
}

function SubmitForm()
{
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

    fm.submit();  
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

        returnParent();    //[����]��ť   
    }
}