
//Name:LLRgtAddMAffixList.js
//Function����֤����js����
//Date��2005-07-1 17:44:28
//Author ��yuejw
var mySql = new SqlClass();
//��ʼ�������ѯ-----��ѯδ�����ĵ�֤
function initQueryAffixGrid()
{
    try
    {
        var Proc=fm.Proc.value;
	    /*strSQL="select affixcode,affixname,subflag,needflag,readycount,property,shortcount,returnflag,affixno from LLAffix where "
	         + "rgtno='"+fm.ClmNo.value+"' and "
//	         + "customerno='"+fm.CustomerNo.value+"' and " 
	         + "(subflag is null or subflag='1')";   */
	    mySql = new SqlClass();
		mySql.setResourceName("claim.LLRgtAddMAffixListClmScanInputSql");
		mySql.setSqlId("LLRgtAddMAffixListClmScanSql1");
		mySql.addSubPara(fm.ClmNo.value );   
//	    alert(strSQL);         
	    arr=easyExecSql(mySql.getString());
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
      alert("LLRgtAddMAffixList.js-->initList�����з����쳣:���ݲ�ѯ����!");
    }
}

function showMyPage(spanID,flag)
{
  if(!flag)
  {
    //�ر�
    spanID.style.display="none";
  }
  else
  {
    //��
    spanID.style.display="";
  }
}



//[���ɵ�֤��ť]-----���ⰸ�ڱ���ʱû�����ɵ�֤����ֱ�ӽ�������ʱ���������ɵ�֤��Ϣ�������������ͣ�
function AffixBuild()
{
	//var strSQL="select distinct b.affixcode,b.affixname,1,b.needflag,1,0 from LLMAppReasonAffix a,llmaffix b where a.affixcode=b.affixcode";            
		mySql = new SqlClass();
		mySql.setResourceName("claim.LLRgtAddMAffixListClmScanInputSql");
		mySql.setSqlId("LLRgtAddMAffixListClmScanSql2");
		
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

//[���ӵ�֤��ť]-----�������ӵ�֤��Ϣ
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
        alert("��ѡ�����赥֤");
        return;
      }
      if (codeCheck(affixCode))
      {
        alert("�õ�֤�Ѵ��ڣ�������ѡ��");
        return;
      }
      //var strSQL="select affixtypecode,affixtypename,needflag from llmaffix where affixcode='"+affixCode+"'";
      	    mySql = new SqlClass();
		mySql.setResourceName("claim.LLRgtAddMAffixListClmScanInputSql");
		mySql.setSqlId("LLRgtAddMAffixListClmScanSql3");
		mySql.addSubPara(affixCode );  
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
        alert("�����뵥֤����");
        return;
      }
      affixNeedFlag="0";
    }
    AffixGrid.addOne();
    var rows=AffixGrid.mulLineCount;
    AffixGrid.setRowColData(rows-1,1,affixCode); //��֤����
    AffixGrid.setRowColData(rows-1,2,affixName); //��֤����
    AffixGrid.setRowColData(rows-1,4,"0");//��֤�Ƿ����
    AffixGrid.setRowColData(rows-1,5,"1");    //��֤�������
    AffixGrid.setRowColData(rows-1,6,"0");    //�ύ��ʽ
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

//[����]��ť
function returnParent()
{
//	top.opener.queryRegister();
	top.close();
}

//���水ť
function AffixSave()
{
	//var strSQL="select 1 from LOPRTManager where code='PCT003' and Otherno='"+fm.all('ClmNo').value+"'";
        mySql = new SqlClass();
		mySql.setResourceName("claim.LLRgtAddMAffixListClmScanInputSql");
		mySql.setSqlId("LLRgtAddMAffixListClmScanSql4");
		mySql.addSubPara(fm.all('ClmNo').value ); 
  var arr=easyExecSql(mySql.getString());

    if((arr!=null)&&(arr!=""))
    {
    	alert("�ѷ����������ϣ������ظ�����");
    	return;
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
        alert("û�пɱ���ĵ�֤���ݣ�");
        return false;
      }
    fm.Operate.value="RgtAdd||Insert";
    fm.action="LLRgtAddMAffixListClmScanSave.jsp";
    SubmitForm();  
}
function SubmitForm()
{
    var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
    //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
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
        //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
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
        //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
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