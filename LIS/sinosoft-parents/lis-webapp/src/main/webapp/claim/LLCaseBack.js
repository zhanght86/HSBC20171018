var showInfo;
var turnPage = new turnPageClass();
var mySql = new SqlClass();

//�����ύ����
function submitForm()
{
    //�ύ����
    var i = 0;
    var showStr = "���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    //showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
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





/**=========================================================================
    �޸�״̬����ʼ
    �޸�ԭ�򣺰���������Ϣ�����ύ
    �� �� �ˣ�zhangzheng ����У��
    �޸����ڣ�2009.01.10
   =========================================================================
**/
function CaseBackSave()
{
	
    //���Ƚ��зǿ��ֶμ���
    if (beforeSubmit())
    {
  	  fm.action = "./LLCaseBackInputSave.jsp?Flag=1";
      submitForm();
    }

}

/**=========================================================================
    �޸�״̬����ʼ
    �޸�ԭ�򣺰���������Ϣ��ɲ���
    �� �� �ˣ������
    �޸����ڣ�2005.11.17
   =========================================================================
**/

function CaseBackSubmit()
{
	  if(fm.BackTypeQ.value=='0')
	  {
		  //ֻ��ʵ�����>0ʱ����Ҫ�ж��Ƿ��Ѻ����ݽ���
		  var tClmNo = fm.ClmNo.value;
		  //var strSql = " select distinct 1 from ljaget where otherno='"+tClmNo+"' and Sumgetmoney>0";
		  //prompt("",strSql);
		  mySql = new SqlClass();
		mySql.setResourceName("claim.LLCaseBackInputSql");
		mySql.setSqlId("LLCaseBackSql1");
		mySql.addSubPara(tClmNo ); 
		  var tljagety = easyExecSql(mySql.getString());
		  if (tljagety)
		  {
			  //strSql = " select distinct 1 from ljapay,ljagetclaim where actugetno=payno and incomeno='"+tClmNo+"'";
			  //prompt("",strSql);
			  mySql = new SqlClass();
			mySql.setResourceName("claim.LLCaseBackInputSql");
			mySql.setSqlId("LLCaseBackSql2");
			mySql.addSubPara(tClmNo ); 
			  var tljapay = easyExecSql(mySql.getString());
			  if (tljapay == null)
			  {
				  alert("���Ⱥ����ݽ���!");
				  return false;
			  }
		  } 
	  }
	  
	  fm.action = "./LLCaseBackInputSave.jsp?Flag=2";
      submitForm();
    
}
/**=========================================================================
    �޸�״̬����ʼ
    �޸�ԭ�򣺰���������Ϣ��ѯ������tClmNoΪ������ⰸ��
    �� �� �ˣ������
    �޸����ڣ�2005.10.23
   =========================================================================
**/
function CaseBackQuery()
{
    var tClmNo = fm.ClmNo.value;
	var strUrl="LLClaimQueryInput.jsp?claimNo="+tClmNo+"&phase=0";
	//�ⰸ��ѯ
	window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
    
}
/**=========================================================================
    �޸�״̬����ʼ
    �޸�ԭ�򣺷��ذ�ť ���ص���������ҳ��
    �� �� �ˣ������
    �޸����ڣ�2005.10.21
   =========================================================================
**/

function goToBack()
{
    location.href="LLCaseBackMissInput.jsp";
}
/**=========================================================================
    �޸�״̬����ʼ
    �޸�ԭ�򣺰�������ԭ��Ǽ�
    �� �� �ˣ������
    �޸����ڣ�2005.11.17
   =========================================================================
**/

function queryCaseBack()
{
    var tClmNo = fm.ClmNo.value;
   /* var strSql = " select BackNo from LLCaseBack where "
               + " ClmNo = '" + tClmNo + "'";*/
     mySql = new SqlClass();
		mySql.setResourceName("claim.LLCaseBackInputSql");
		mySql.setSqlId("LLCaseBackSql3");
		mySql.addSubPara(tClmNo ); 
    var tBackCase = easyExecSql(mySql.getString());
    if (tBackCase != null)
    {
        fm.BackNo.value = tBackCase[0][0];
    }
}

/**=========================================================================
    �޸�״̬����ʼ
    �޸�ԭ���г��ⰸ�µ�Ӧ���ܱ��б�
    �� �� �ˣ������
    �޸����ڣ�2005.11.22
   =========================================================================
**/

function queryLJsPayGrid()
{
	var tClmNo = fm.ClmNo.value;
	/*var strSQL = " select GetNoticeNo,AppntNo,SumDuePayMoney,PayDate,case BankOnTheWayFlag " 
	           + " when '1' then '��' when '0' then '��' end "
	           + " from ljspay where otherno ='" + tClmNo + "'" 
               + " order by GetNoticeNo ";*/
     mySql = new SqlClass();
		mySql.setResourceName("claim.LLCaseBackInputSql");
		mySql.setSqlId("LLCaseBackSql4");
		mySql.addSubPara(tClmNo ); 
	turnPage.pageLineNum = 5;
	turnPage.queryModal(mySql.getString(), LJsPayGrid);
}

/**=========================================================================
    �޸�״̬����ʼ
    �޸�ԭ�򣺻��˰���δ������Ļ��˶��� 
              ��ӡ�ԭ������ۡ�����ԭ�����ܽ���������ԭ�����͡�
    �� �� �ˣ������
    �޸����ڣ�2005.12.28 
   =========================================================================
**/
function selectCaseBack()   
{
	var tClmNo = fm.ClmNo.value;
    /*var strSql = " select backno,clmno,backdesc,backreason,remark ,applyuser,"
               + " applydate,orirealypay,case origivetype when '0' then '����' when '1' then '�ܸ�' when '2' "
	           + " then '�ͻ�����' when '3' then '��˾����' end,newgivetype,"
	           +" CheckBackPreFlag, (select codename from ldcode where codetype = 'whetherbackpreflag' and code = CheckBackPreFlag) "
               + " from llcaseback where "
               + " clmno = '" + tClmNo + "'"
               + " and backstate = '0'";*/
    mySql = new SqlClass();
	mySql.setResourceName("claim.LLCaseBackInputSql");
	mySql.setSqlId("LLCaseBackSql5");
	mySql.addSubPara(tClmNo ); 
    //prompt("",strSql);
    var tCaseBack = easyExecSql(mySql.getString());
    if (tCaseBack != null)
    {
    	
        fm.BackNo.value      = tCaseBack[0][0];//���˺�
        fm.ClmNo.value       = tCaseBack[0][1];//�ⰸ��
        fm.BackDesc.value    = tCaseBack[0][2];//��������
        fm.BackReason.value  = tCaseBack[0][3];//����ԭ��
        fm.Remark.value      = tCaseBack[0][4];//��ע
        fm.ApplyUesr.value   = tCaseBack[0][5];//������
        fm.ApplyDate.value   = tCaseBack[0][6];//��������ʱ��
        fm.OriRealyPay.value = tCaseBack[0][7];//ԭ�����ܶ�
        fm.OriGiveType.value = tCaseBack[0][8];//ԭ�������
        fm.NewGiveType.value = tCaseBack[0][9];//���������
        fm.whetherBackPre.value = tCaseBack[0][10];//�Ƿ�ͬʱ����Ԥ����Ϣ��־
        fm.whetherBackPreName.value = tCaseBack[0][11];//�Ƿ�ͬʱ����Ԥ����Ϣ����
        showOneCodeName('llclaimconclusion','NewGiveType','NewGiveTypeName');
        showOneCodeName('llbackreason','BackReason','BackReasonName');
        
    }
    
}

/**=========================================================================
   =========================================================================
**/
    

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
        //fm.CaseBackSubmitBt.disabled = true;
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
        goToBack();
    }
    
}

/**=========================================================================
=========================================================================
**/
function afterSubmit2( FlagStr, content )
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
        fm.CaseBackSubmitBt.disabled = true;
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
        selectCaseBack();
        queryLJsPayGrid();
        fm.CaseBackSubmitBt.disabled = false;
    }
    
}



/**=========================================================================
    �޸�ԭ�򣺰��������漰��Ӧ�ռ�¼�ĺ���
    �� �� �ˣ�����
    �޸����ڣ�2005-11-23 17:42
   =========================================================================
**/
function CaseFeeCancel()
{
	  fm.action = "./LLCaseBackFeeSave.jsp";
      fm.target = "fraSubmit";
      submitForm();
}

//�ύ�����水ť��Ӧ����
function PrintClick()
{

  
  //var arrReturn = new Array();
  var tSel = LJsPayGrid.getSelNo();
  if( tSel == 0 || tSel == null )
  {
		alert( "����ѡ��һ����¼���ٵ����ӡ֪ͨ�鰴ť��" );
	  return;
	}
	else
	{
		var tPrtNoticeNo = LJsPayGrid.getRowColData(tSel-1,1);
		fm.PrtNoticeNo.value = tPrtNoticeNo;
		fm.action = "./LLCaseBackNoticeSave.jsp";
		fm.target = "../f1print";
		submitForm();
	    //fm.submit();
	}
	//submitForm();
	showInfo.close();
	fm.target="fraSubmit";//Modify by zhaorx 2006-11-29
}


//2009-01-10 zhangzheng ˫������������Ӧ����
function afterCodeSelect( cCodeName, Field ) {

    //alert(cCodeName);  

	//�Ƿ�ͬʱ����Ԥ����Ϣ
    if(cCodeName=="whetherbackpreflag"){
    	
    	if(fm.whetherBackPre.value=='1'&&fm.BackTypeQ.value=='1'){
    		
    		alert("������û�е����״̬,����ͬʱ����Ԥ����Ϣ!");
        }
    	else{
    		

    	}
    	
        return true;
    }
}


//�ύǰ��У�顢����
function beforeSubmit()
{

	  if(fm.NewGiveType.value == ""|| fm.NewGiveType.value == null)
	  {
	  	   alert("������˺��������۲���Ϊ�գ�");
	  	   return false;
	  }
	  
	  if(fm.BackReason.value == "" || fm.BackReason.value == null)
	  {
	  	   alert("����ԭ����Ϊ�գ�");
	  	   return false;
	  }
	  
	  if(fm.BackDesc.value == "" || fm.BackDesc.value == null)
	  {
	  	   alert("����ԭ����ϸ˵������Ϊ�գ�");
	  	   return false;
	  }
	  
	  //�ⰸ״̬Ϊ���״̬ʱ����ѡ���Ƿ����Ԥ����Ϣ
	  if(fm.BackTypeQ.value=='0')
	  {
		  if(fm.whetherBackPre.value == "" || fm.whetherBackPre.value == null)
		  {
		  	   alert("�ⰸ״̬Ϊ���״̬ʱ��Ҫѡ���Ƿ����Ԥ����Ϣ!");
		  	   return false;
		  }
	  }
	  
	  return true;
}