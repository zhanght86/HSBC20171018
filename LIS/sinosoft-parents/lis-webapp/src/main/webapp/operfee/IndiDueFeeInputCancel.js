//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�
var showInfo;
var mDebug="0";
var showInfo;
var flag;
var mSwitch = parent.VD.gVSwitch;
var turnPage = new turnPageClass();
var k = 0;

var tResourceName="operfee.IndiDueFeeCancelSql";
var tResourceSQL1="IndiDueFeeCancelSql1";
var tResourceSQL2="IndiDueFeeCancelSql2";
var tResourceSQL3="IndiDueFeeCancelSql3";
var tResourceSQL4="IndiDueFeeCancelSql4"; 

// ��ѯ��ť
function easyQueryClick()
{
	// ��ʼ�����
	initPolGrid();
	if(document.all('ContNo2').value==""&&document.all('PrtNo').value=="")
	{
	  alert("�����ź�ӡˢ�Ų���ͬʱΪ�գ�");
      return ;
	}
	/*var strSQL = "select ContNo,ProposalContNo,AppntName,InsuredName from LCCont where 1=1"
				 + " and AppFlag='1'"	//�б�
				 + " and conttype = '1'"	//1-������Ͷ����,2-�����ܵ�
				 //+ " and PayIntv>0"					 			 
				 //+ " and grpContno = '00000000000000000000'"
				 + " and exists (select 1 from ljspay where otherno=LCCont.ContNo)"
				 + getWherePart( 'ContNo','ContNo2' )
				 + getWherePart( 'PrtNo' )
				 + " and ManageCom like '" + ComCode + "%%'"
				 + " order by contno"*/
	
	//var strSQL = wrapSql(tResourceName,tResourceSQL1,[fm.ContNo2.value,fm.PrtNo.value,ComCode]); 
	var sqlid="IndiDueFeeCancelSql1";
		var mySql31=new SqlClass();
		 var sql31 = "";
		// alert('1');
	if(document.all('ContNo2').value!=null&&document.all('ContNo2').value!='')
	{
		sqlid="IndiDueFeeCancelSql1";
		mySql31.setResourceName("operfee.IndiDueFeeCancelSql"); //ָ��ʹ�õ�properties�ļ���
		mySql31.setSqlId(sqlid);//ָ��ʹ�õ�Sql��id
		mySql31.addSubPara(document.all('ContNo2').value);//ָ������Ĳ���
		mySql31.addSubPara(ComCode);//ָ������Ĳ���
	    sql31=mySql31.getString();
	}
	else
	{
		sqlid="IndiDueFeeCancelSql2";
		mySql31.setResourceName("operfee.IndiDueFeeCancelSql"); //ָ��ʹ�õ�properties�ļ���
		mySql31.setSqlId(sqlid);//ָ��ʹ�õ�Sql��id
		mySql31.addSubPara(document.all('PrtNo').value);//ָ������Ĳ���
		mySql31.addSubPara(ComCode);//ָ������Ĳ���
	    sql31=mySql31.getString();
	}
	turnPage.strQueryResult  = easyQueryVer3(sql31, 1, 0, 1);  
  turnPage.queryModal(sql31,PolGrid);
  /*
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
    alert("û�з��������ĸ��˵���");
    return ;
  }
  
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
  */
  return true;
}

function submitForm()
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

		showInfo.focus();
    	document.getElementById("fm").submit();
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

function easyQueryAddClick()
{
	var tSelNo = PolGrid.getSelNo()-1;
	fm.ContNo.value = PolGrid.getRowColData(tSelNo,1);	
}
