//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="0";
var showInfo;
var flag;
var mSwitch = parent.VD.gVSwitch;
var CurrentTime;
var SubTime;
var turnPage = new turnPageClass();
var turnPage2 = new turnPageClass();
//�ύ�����水ť��Ӧ����

var tResourceName="operfee.IndiDueFeeInputSql";
var tResourceSQL1="IndiDueFeeInputSql1";
var tResourceSQL2="IndiDueFeeInputSql2";
var tResourceSQL3="IndiDueFeeInputSql3";
var tResourceSQL4="IndiDueFeeInputSql4"; 

function queryAgent()
{
	if(document.all('ManageCom').value==""){
		 alert("����¼����������Ϣ��"); 
		 return;
	}	
    if(document.all('AgentCode').value == "")	{  
	  var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+document.all('ManageCom').value,"AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');	  	  
	}	
	if(document.all('AgentCode').value != "")	 {
		var cAgentCode = fm.AgentCode.value;  //��������	
		//var strSql = "select AgentCode,Name from LAAgent where AgentCode='" + cAgentCode +"'";
		var strSql = wrapSql(tResourceName,tResourceSQL4,[cAgentCode]); 
    	var arrResult = easyExecSql(strSql);
    	if (arrResult != null) {
      		alert("��ѯ���:  �����˱���:["+arrResult[0][0]+"] ����������Ϊ:["+arrResult[0][1]+"]");
      	} 
	}	
}

function submitForm()
{
  //�ύǰ�ļ���
  if(beforeSubmit())
  {  	  
  var i = 0;
  var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
   
  document.getElementById("fm").submit(); //�ύ 
  }
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit1( FlagStr, content )
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
  	//parent.fraInterface.initForm();
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=350;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

//    showDiv(operateButton,"true"); 
//    showDiv(inputButton,"false"); 
    //ִ����һ������
    resetForm();
    
  }
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
	var iHeight=350;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    resetForm();
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
  	alert("��LJSPayPersonInput.js-->resetForm�����з����쳣:��ʼ���������!");
  }
} 

//ȡ����ť��Ӧ����
function cancelForm()
{
//  window.location="../common/html/Blank.html";
    showDiv(operateButton,"true"); 
    showDiv(inputButton,"false");     
}
 
//�ύǰ��У�顢����  
function beforeSubmit()
{
	var tSel = ContGrid.getSelNo();	
	if( tSel == 0 || tSel == null )
	{
		alert( "����ѡ��һ����¼���ٵ�����հ�ť��" );
		return false;
	}else
	{
        document.all('ContNo1').value = ContGrid.getRowColData(tSel-1,1);;
    }
    return true;
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

function PersonSingle()
{
	if(beforeSubmit())
    {  
    	var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
		fm.action = "./IndiDueFeeQuery.jsp?DealType=" + DealType;
		showInfo.focus();
    	document.getElementById("fm").submit();
    }  
}

function easyQueryAddClick()
{
	 
    var arrReturn = new Array();
	var tSel = ContGrid.getSelNo();	
	if( tSel == 0 || tSel == null )
	{
		alert( "����ѡ��һ����¼���ٵ�����ذ�ť��" );
	}	
	else
	{
		var tRow = ContGrid.getSelNo() - 1;	        
        var tContNo=ContGrid.getRowColData(tRow,1);  
	    //var strSQL = "select PolNo,PrtNo,RiskCode,RiskVersion,prem,AppntName,InsuredName,PaytoDate from LCPol where "
	    //           +"paytodate<payenddate "
	    //           +" and exists (select riskcode from lmriskpay WHERE urgepayflag='Y' and lmriskpay.riskcode=lcpol.riskcode)"
	    //           +" and (StopFlag='0' or StopFlag is null)"	                        
			//	   +" and AppFlag='1'"
			//	   +" and grppolno = '00000000000000000000'"
			//	   +" and managecom like '"+managecom+"%%'"
			//	   +" and payintv>0 "
			//	 //+" and paytodate>='" + CurrentTime + "' and paytodate<='" + SubTime +"'"			 
	    //           +" and ContNo='"+tContNo+"'"
      //             +" union "
      //             +" select PolNo, PrtNo, RiskCode, RiskVersion, prem, AppntName, InsuredName,PaytoDate from LCPol where"
      //             +" rnewflag='-1' and AppFlag = '1'"
      //             +" and ContNo='"+tContNo+"'";
     
        var strSQL = wrapSql(tResourceName,tResourceSQL1,[managecom,tContNo,tContNo]); 
       turnPage2.queryModal(strSQL, PolGrid); 
     } 	
}

function getTime(StartTime,tSubTime)
	{
	   CurrentTime = StartTime;
	   SubTime = tSubTime; 
	}

// ��ѯ��ť
function easyQueryClick()
{  
	
	if((fm.ContNo.value == "" || fm.ContNo.value == null)&&(fm.PrtNo.value == "" || fm.PrtNo.value == null))
	{
		alert("���Ŵ��������뱣���Ż���ӡˢ��");
		return;
	}	
	if(!rnewuw_check())
	{
		alert("�õ����������������޺˱����ۣ����ɴ���");
		return;
	}
	var i = 0;
  	var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

	fm.action = "./IndiDueFeeQueryDate.jsp?DealType=" + DealType;
	document.getElementById("fm").submit();
}
//�����Ƿ��ڶ���״̬��ѯ
function rnewuw_check()
{
	var check_str="";
	if(fm.PrtNo.value == "" || fm.PrtNo.value == null)
	{
	  //check_str = " select count(b.contno)  from LCRnewStateHistory a,lccont b where a.contno = b.contno and a.state ='2' and b.contno='"+fm.ContNo.value+"'";
	  check_str =  wrapSql(tResourceName,tResourceSQL2,[fm.ContNo.value]); 
	}
	else
	{
		//check_str = " select count(b.contno)  from LCRnewStateHistory a,lccont b where a.contno = b.contno and a.state ='2' and b.prtno='"+fm.PrtNo.value+"'";
	  check_str =  wrapSql(tResourceName,tResourceSQL3,[fm.PrtNo.value]); 
	}
	if(easyExecSql(check_str, 1, 1, 1)==0)
	{
		return true;
	}
	else
	{
		return false;
	}
	
}


function showRecord(strRecord)
{
	//prompt("",strRecord);
	turnPage.useSimulation   = 1;
	turnPage.strQueryResult  = strRecord;  
  
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
    alert("û�з��������ı�����");
    return ;
  }
  
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);  
  //���ó�ʼ������MULTILINE����HealthGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
  turnPage.pageDisplayGrid = ContGrid;              
  //����SQL���
  var strSQL="";
  turnPage.strQuerySql     = strSQL;   
  //���ò�ѯ��ʼλ��
  turnPage.pageIndex       = 0;    
  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  var arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  //����MULTILINE������ʾ��ѯ���
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  
  //�����Ƿ���ʾ��ҳ��ť
  /*
  if (turnPage.queryAllRecordCount > turnPage.pageLineNum) {
    try { window.divPage.style.display = ""; } catch(ex) { }
  } else {
    try { window.divPage.style.display = "none"; } catch(ex) { }
  }
  */
  //���뽫������������Ϊһ�����ݿ�
  turnPage.dataBlockNum = turnPage.queryAllRecordCount;
  turnPage.blockPageNum = turnPage.queryAllRecordCount / turnPage.pageLineNum;
	
}

function afterQuery2(arrResult)
{  
  if(arrResult!=null)
  {
  	fm.AgentCode.value = arrResult[0][0];
  }
}
function afterQuery( FlagStr, content )
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
    //var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
  }
}
