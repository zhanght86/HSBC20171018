//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�
var arrDataSet;
var showInfo;
var mDebug="0";
var turnPage = new turnPageClass();
var str_sql = "",sql_id = "", my_sql = "";   //�󶨱����������ݿ�


//�ύ�����水ť��Ӧ����
function submitForm()
{
    var i = 0;
    var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
    //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    //initPolGrid();
    //showSubmitFrame("1");
    fm.submit(); //�ύ
}



//�ύ�����水ť��Ӧ����
function printPol()
{
   var tRow = PolGrid.getSelNo();	
   if( tRow == 0 || tRow == null ){
	 	  alert( "����ѡ��һ����¼���ٵ������ť��" );
	    return;
	 }
	 else
	 {	 	
	 	  fm.PrtSeq.value = PolGrid.getRowColData(tRow-1,1);
	 	  fm.Code.value = PolGrid.getRowColData(tRow-1,2);
	 	  fm.ContNo.value = PolGrid.getRowColData(tRow-1,3);
	 	  
	 	  if(!checkdata(fm.ContNo.value,fm.PrtSeq.value,fm.Code.value))
	 	  {
	 	      return;
	 	  }
	 	  
      if(fm.Code.value=="BQ88")
	    {
	        fm.action="./ReEdorAskSave.jsp";
	    }
	    else{
	    	  fm.action="./bqRePrintSave.jsp";
	    }
	 	  fm.target = "../f1print";
	 	  fm.submit();
	 }
}

function getQueryResult()
{
	var arrSelected = null;
	tRow = PolGrid.getSelNo();
	if( tRow == 0 || tRow == null || arrDataSet == null )
		return arrSelected;

	arrSelected = new Array();
	//������Ҫ���ص�����
	//arrSelected[0] = arrDataSet[tRow-1];
	arrSelected[0]=new Array();
	arrSelected[0][0]=PolGrid.getRowColData(tRow-1,1);
	arrSelected[0][1]=PolGrid.getRowColData(tRow-1,3);
	arrSelected[0][1]
	return arrSelected;
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
  	//parent.fraInterface.initForm();
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");       	
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  }
  
  easyQueryClick();
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
  	alert("��Proposal.js-->resetForm�����з����쳣:��ʼ���������!");
  }
} 

//�ύǰ��У�顢����  
function beforeSubmit()
{
  //��Ӳ���	
}           

// ��ѯ��ť
function easyQueryClick()
{
	var tCode = fm.all('CodeS').value;
//Deleted By QianLy for QC7209 on 2006-9-29-------
//	if(tCode == ""){
//		alert("��ѡ�񲹴�˱�֪ͨ�����ͣ�");
//		return false;
//	}
//-----------------
	if(fm.ContNoS.value == "" && fm.PrtSeqS.value == ""){
	    alert("���ߴ�ӡǰ��������Ҫ����˱�֪ͨ��ı����Ż�֪ͨ��ţ�\n���ߵ���ȫ������ӡҳ����д�ӡ��");
	    return false;
	}
		 //��дSQL���
	var strSQL = "";
//====================del=========liuxiaosong=================2006-11-06------------start====================
//	strSQL = "select a.prtseq,a.code,a.otherno,b.managecom,b.agentcode from loprtmanager a, lccont b where 1=1 and  a.otherno = b.contno and a.stateflag in ('1','0')"
//	         + " and trim(a.code) in (select trim(code) from ldcode where codetype = 'bquwnotice') "//Add By QianLy on 2006-10-14
//	         + getWherePart('a.code', 'CodeS')
//	         + getWherePart('a.otherno', 'ContNoS')
//	         + getWherePart('a.PrtSeq', 'PrtSeqS');
//====================del=========liuxiaosong=================2006-11-06------------end====================
//====================add=========liuxiaosong=================2006-11-06------------end====================
    //��SQLЧ�ʴ�0.031S��ߵ�0.016S������50%����
//	strSQL = "select a.prtseq,a.code,a.otherno,b.managecom,b.agentcode from loprtmanager a, lccont b where 1=1 and  a.otherno = b.contno and a.stateflag in ('1','0')"
//			 + getWherePart('a.code', 'CodeS')
//	         + " and trim(a.code) in (select trim(code) from ldcode where codetype = 'bquw') "
//	         + getWherePart('a.otherno', 'ContNoS')
//	         + getWherePart('a.PrtSeq', 'PrtSeqS');
//====================add=========liuxiaosong=================2006-11-06------------end====================
	sql_id = "RnewRePrintSql0";
	my_sql = new SqlClass();
	my_sql.setResourceName("uw.RnewRePrintSql"); //ָ��ʹ�õ�properties�ļ���
	my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
	my_sql.addSubPara(document.getElementsByName(trim('CodeS'))[0].value);//ָ������Ĳ���
	my_sql.addSubPara(document.getElementsByName(trim('ContNoS'))[0].value);//ָ������Ĳ���
	my_sql.addSubPara(document.getElementsByName(trim('PrtSeqS'))[0].value);//ָ������Ĳ���
	str_sql = my_sql.getString();
  var aResult = easyExecSql(str_sql);  
  //alert(aResult);
	turnPage.strQueryResult  = easyQueryVer3(strSQL);  
  
  //alert(turnPage.strQueryResult);
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
   alert("û��Ҫ�ύ����֪ͨ�����Ϣ��");
    return false;
  }
  
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //���ó�ʼ������MULTILINE����VarGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
  turnPage.pageDisplayGrid = PolGrid;    
          
  //����SQL���
  turnPage.strQuerySql     = strSQL; 
  
  //���ò�ѯ��ʼλ��
  turnPage.pageIndex       = 0;  
  
  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  //tArr = chooseArray(arrDataSet,[0,1,3,4]) 
  //����MULTILINE������ʾ��ѯ���
  
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  //displayMultiline(tArr, turnPage.pageDisplayGrid);
}
//��ʾfrmSubmit��ܣ���������
function showSubmitFrame(cDebug)
{
  if(cDebug=="1")
  {
	parent.fraMain.rows = "0,0,50,82,*";
  }
 	else {
  		parent.fraMain.rows = "0,0,0,0,*";
 	}
}

function checkdata(aContNo,aPrtSeq,aCode)
{
	if(aContNo == "" || aContNo == null)
	{
		alert("������Ϊ�գ�");
		return false;
	}
	if(aPrtSeq == "" || aPrtSeq == null)
	{
		alert("������Ϊ�գ�");
		return false;
	}
	if(aCode == "" || aCode == null)
	{
		alert("������Ϊ�գ�");
		return false;
	}
	
	
	var wherepart = " and activityid = ";
	if(aCode == "BQ90")
	   wherepart += "'0000001106'";
	else
	   wherepart += "'0000001280'";
	
	
	sql_id = "RnewRePrintSql1";
	my_sql = new SqlClass();
	my_sql.setResourceName("uw.RnewRePrintSql"); //ָ��ʹ�õ�properties�ļ���
	my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
	my_sql.addSubPara(aContNo);//ָ������Ĳ���
	my_sql.addSubPara(aPrtSeq);//ָ������Ĳ���
	my_sql.addSubPara(wherepart);//ָ������Ĳ���
	str_sql = my_sql.getString();
	var tResult = easyExecSql(str_sql,1,0);
	if(tResult != null)
	{
		alert("��֪ͨ��δ��ӡ�������������ӡ��");
		return false;
	}
	return true;
		
}