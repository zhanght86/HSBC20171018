//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�
var turnPage = new turnPageClass();          //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���

var showInfo;
var mDebug="0";
var arrStrReturn = new Array();
var arrGrid;

//�ύ�����水ť��Ӧ����
function submitForm()
{
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
	try {
		fm.sql_where.value = eval("top.opener.fm.sql_where.value");
	} catch (ex) {
		fm.sql_where.value = "";
	}

  document.getElementById("fm").submit(); //�ύ
}


function QueryClick()
{
	// ��ʼ�����
	// ��дSQL���
	var strSQL = "";
	var i, j, m, n;	
	initSysCertifyGrid();
	try {
		fm.sql_where.value = eval("top.opener.fm.sql_where.value");
	} catch (ex) {
		fm.sql_where.value = "";
	}
	//QUERY_FLAG
	var tQUERY_FLAG = eval("top.opener.fm.QUERY_FLAG.value");
	//alert('1');
	if(trim(fm.CertifyCode.value)=='')
	{
		alert('�������뵥֤����');
		return false;
	}
	//alert(tQUERY_FLAG);
	//fm.sql_where.value = " and StateFlag = '0' "
	if(trim(fm.CertifyCode.value)=='9994')
	{
	   if(fm.CertifyNo.value==null||trim(fm.CertifyNo.value)==""){
	     alert("��¼�뵥֤���룡");
	     return false;
	   }
		if(tQUERY_FLAG=='0')
		{
		strSQL = "select A.* from ("
		       + " select  '9994' x,grpcontno y,'A'||a.managecom z,'D'||a.agentcode m,a.operator n,null o,'SYS' p,"
		       + " null q,null r,null s,a.getpoldate t from lcgrpcont a "
           + " where a.appflag='1' and a.getpoldate is null and printcount>0 "
           + " ) A where 1=1 "
           + getWherePart('A.y','CertifyNo')
           + getWherePart('A.z','SendOutCom')
           + getWherePart('A.m','ReceiveCom')
           + " order by A.y"
           ;
      }
	
	else
		{
   //��ȡԭ������Ϣ
    strSQL = "select certifycode,certifyno,sendoutcom,receivecom,operator,validdate,handler,handledate,sendno,takebackno,makedate,takebackdate from LZSysCertify  WHERE 1=1 "				 			
			 + fm.sql_where.value +" "
			 + getWherePart('CertifyCode', 'CertifyCode') 
	     + getWherePart('CertifyNo', 'CertifyNo')
			 + getWherePart('SendOutCom','SendOutCom')
			 + getWherePart('ReceiveCom','ReceiveCom')
			 + getWherePart('Handler','Handler')
			 + getWherePart('HandleDate','HandleDate')
			 " order by CertifyNo ";	
			}
		}
	//prompt('',strSQL);		 				 
	turnPage.queryModal(strSQL, SysCertifyGrid); 
 if (!turnPage.strQueryResult) {
  	alert("��ѯ�޽��!");
  //easyQueryClickInit();
  return "";
}   
	arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
 
  return true;	
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
  showInfo.close();
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
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
    parent.fraSubmit.getGridResult();
    
    arrGrid = null;
    if( arrStrReturn[0] == '0|0^' ) {
    	SysCertifyGrid.clearData();
    } else {
			arrGrid = decodeEasyQueryResult(arrStrReturn[0]);
    	useSimulationEasyQueryClick(arrStrReturn[0]);
    }
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
  	alert("��SysCertTakeBack.js-->resetForm�����з����쳣:��ʼ���������!");
  }
} 

//�ύǰ��У�顢����  
function beforeSubmit()
{
  //��Ӳ���	
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

function returnParent()
{
  var arrReturn = new Array();
	var tSel = SysCertifyGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
	{
		//alert( "����ѡ��һ����¼���ٵ�����ذ�ť��" );
		top.close();
	}
	else
	{
			try
			{	
				//alert(tSel);
				arrReturn = getQueryResult();
				top.opener.afterQuery( arrReturn );
			}
			catch(ex)
			{
				alert( "û�з��ָ����ڵ�afterQuery�ӿڡ�" + ex );
			}
			top.close();
	}
}

function getQueryResult()
{
	var arrSelected = null;
	tRow = SysCertifyGrid.getSelNo();
	//alert("Selected row:" + tRow);
	if( tRow == 0 || tRow == null || arrDataSet == null )
	{
		return arrSelected;
	}
	arrSelected = new Array();
	//������Ҫ���ص�����
	//arrSelected[0] = arrDataSet[tRow-1];
	arrSelected[0] = SysCertifyGrid.getRowData(tRow-1);
	return arrSelected;
}


function useSimulationEasyQueryClick(strData) {
  //�����ѯ����ַ���
  turnPage.strQueryResult  = strData;
  
  //ʹ��ģ������Դ������д�ڲ��֮ǰ
  turnPage.useSimulation   = 1;  
    
  //����ַ��������ض�ά����
  var tArr                 = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //��MULTILINE���,ʹMULTILINE��ʾʱ���ֶ�λ��ƥ�����ݿ���ֶ�λ��
  var filterArray          = new Array(0, 1, 3, 4, 15);

  //�������������������ͬ��ѯ����һ��turnPage����ʱ����ʹ�ã���ü��ϣ��ݴ�
  turnPage.arrDataCacheSet = clearArrayElements(turnPage.arrDataCacheSet);
  
  //���˶�ά���飬ʹ֮��MULTILINEƥ��
  turnPage.arrDataCacheSet = chooseArray(tArr, filterArray);
  
  //���ó�ʼ������MULTILINE����VarGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
  turnPage.pageDisplayGrid = SysCertifyGrid;
  
  //���ò�ѯ��ʼλ��
  turnPage.pageIndex       = 0;
  
  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, turnPage.pageLineNum);
  
  //����MULTILINE������ʾ��ѯ���
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);	
  
  //�����Ƿ���ʾ��ҳ��ť
  if (turnPage.queryAllRecordCount > turnPage.pageLineNum) {
    try { window.divPage.style.display = ""; } catch(ex) { }
  } else {
    try { window.divPage.style.display = "none"; } catch(ex) { }
  }
  
  //���뽫������������Ϊһ�����ݿ�
  turnPage.blockPageNum = turnPage.queryAllRecordCount / turnPage.pageLineNum;
}