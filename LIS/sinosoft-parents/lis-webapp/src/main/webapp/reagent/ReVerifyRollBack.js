//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�
var showInfo;
var mDebug="0";
var showInfo;
var flag;
var mSwitch = parent.VD.gVSwitch;
var turnPage = new turnPageClass();
var k = 0;
var Result_flag="0"; //��ѯ�Ƿ������ݱ��

// ��ѯ��ť
function easyQueryClick()
{
	if(fm.GetNoticeNo.value == "" || fm.GetNoticeNo.value == null)
	{
		alert("�������շѺ�");
		return;
	}	
	// ��ʼ�����
	initPolGrid();
	//var strSQL = "select a.managecom,a.contno,a.prtno,a.payintv,(select riskname from lmriskapp where riskcode=a.riskcode),a.appntname,a.insuredname,a.cvalidate, "
  //+" a.paytodate,a.agentcode,(select agentnew from lrascription where oldpolno=a.polno union select agentcode from lradimascription where polno=a.polno ) "
  //+" ,b.sumactupaymoney,b.enteraccdate,b.confdate from lcpol a ,ljapayperson b "
  //+" where a.polno=b.polno and a.appflag='1' and a.paytodate=b.curpaytodate and b.paytype='ZC' "
  //+ getWherePart( 'b.GetNoticeNo','GetNoticeNo' )
	//+ getWherePart( 'a.ContNo','ContNo' )
	//+ getWherePart( 'a.PrtNo','PrtNo' )
	//+ getWherePart( 'a.AgentCode','AgentCode' )
	//+ getWherePart( 'a.AppntNo','AppntNo' )
	//+ getWherePart( 'a.InsuredNo','InsuredNo' )
	//+ " and a.ManageCom like '" + ComCode + "%%'"
	//+ " order by a.polno"
	
	var strSQL = wrapSql('reagent.ReVerifyRollBackInputSql','ReVerifyRollBackInputSql1',[fm.GetNoticeNo.value,fm.ContNo.value,fm.PrtNo.value,fm.AgentCode.value,fm.AppntNo.value,fm.InsuredNo.value,ComCode]);

	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
  
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
    alert("û�з��������ı�����");
    Result_flag="0";
    return ;
  }
  Result_flag="1";
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);  
  //���ó�ʼ������MULTILINE����HealthGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
  turnPage.pageDisplayGrid = PolGrid;              
  //����SQL���
  turnPage.strQuerySql     = strSQL;   
  //���ò�ѯ��ʼλ��
  turnPage.pageIndex       = 0;    
  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  //����MULTILINE������ʾ��ѯ���
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  return true;
}

function submitForm()
{
	if(Result_flag=="1")
	{  
  		var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();	
    	document.getElementById("fm").submit();
  }
  else
  {
  	 alert("���Ȳ�ѯ�����������ı�����Ϣ֮���ٵ���˲�����");
  }
}

//�ύǰ��У�顢����  
function beforeSubmit()
{
	var tSelNo = PolGrid.getSelNo();
	if( tSelNo == 0 || tSelNo == null )
	{
		alert( "����ѡ��һ����¼���ٵ�����ճ�����ť��" );
		return false;
	}else{
		fm.ContNo.value = PolGrid.getRowColData(tSelNo-1,1); 
	}
    return true;
}  

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
  showInfo.close();
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
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
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    resetForm();
  }
}


//���ð�ť��Ӧ����,Form�ĳ�ʼ�������ڹ�����+Init.jsp�ļ���ʵ�֣�����������ΪinitForm()
function resetForm()
{
  try
  {
	  initForm();
  }
  catch(re)
  {
  	alert("��IndiDueFeeInputCancel.js-->resetForm�����з����쳣:��ʼ���������!");
  }
} 

//ȡ����ť��Ӧ����
function cancelForm()
{
//  window.location="../common/html/Blank.html";
    showDiv(operateButton,"true"); 
    showDiv(inputButton,"false");
}         

//��ʾfrmSubmit��ܣ���������
function showSubmitFrame(cDebug)
{
  if(cDebug=="1")
  {
			parent.fraMain.rows = "0,0,50,82,*";
  }
 	else {
  		parent.fraMain.rows = "0,0,0,82,*";
 	}
}
          
         
//��ʾdiv����һ������Ϊһ��div�����ã��ڶ�������Ϊ�Ƿ���ʾ�����Ϊ"true"����ʾ��������ʾ
function showDiv(cDiv,cShow)
{
  if (cShow=="true")
  {
    cDiv.style.display="";
  }
  else
  {
    cDiv.style.display="none";  
  }
}

function PersonMulti()
{
	var StartDate=fmMulti.StartDate.value;
	var EndDate=fmMulti.EndDate.value;
	if(StartDate==null||StartDate==""||EndDate==null||EndDate=="")
	{
	  alert("����¼����ֹ����");
	  return false;	
	}
  var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
 var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  document.getElementById("fmMulti").submit();	
}

function SpecPersonMulti()
{
  var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  
  fmMulti.spec.value = "1";
  
  document.getElementById("fmMulti").submit();	  
}


