//�������ƣ�UWGErrInit.jsp
//�����ܣ�����˱�δ��ԭ���ѯ
//�������ڣ�2002-06-19 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����

//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="1";
var tPOLNO = "";
var turnPage = new turnPageClass();


//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
  //showInfo.close();
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
    //[start]  add by liuzhijie ˵��:��[start]...[end]֮��Ĵ�������滻�����һ����ע�ʹ��롢�Թ������������
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=350;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	//[end]
  }
  else
  { 
    //ִ����һ������
  }
}



// ��ѯ��ť
function easyQueryClick()
{
	// ��ʼ�����
	initUWInfoGrid();
	initUWSubInfoGrid();
	
	// ��дSQL���
	var strSQL = "";
	var GrpContNo = document.all('GrpContNo').value;
	
	var tSql = " select (select name from ldcom where comcode = a.managecom),a.managecom, a.salechnl,"
	          +" a.grppolno,a.prtno,a.grpname,(select riskcode from lmrisk where riskcode = a.riskcode),"
	          +" a.prem, a.amnt,(select count(*) from lcpol where grpcontno = a.grpcontno),"
	          +" a.agentcode, (select name from laagent where agentcode = a.agentcode),"
	          +" nvl((select sum(realpay) from llclaimpolicy where grpcontno = a.grpcontno),0)"
	          +" from lcgrppol a "
	          +" where a.grpcontno = '"+GrpContNo+"'";
	turnPage.queryModal(tSql, UWInfoGrid,0,0,30);
	var tSubSql=" select distinct (a.clmno), a.caseno,(select riskname from lmrisk where riskcode = a.riskcode),"
	           +" a.realpay, b.accidentreason,a.insuredname, a.insuredno	from llclaimpolicy a, llregister b"
	           +" where a.clmno = b.rgtno and a.grpcontno='"+GrpContNo+"'";
	turnPage.queryModal(tSubSql, UWSubInfoGrid,0,0,30);
}

function displayEasyResult( arrResult )
{
	var i, j, m, n;

	if( arrResult == null )
		alert( "û���ҵ���ص�����!" );
	else
	{
		arrGrid = arrResult;
		// ��ʾ��ѯ���
		n = arrResult.length;
		for( i = 0; i < n; i++ )
		{
			m = arrResult[i].length;
			for( j = 0; j < m; j++ )
			{
				UWErrGrid.setRowColData( i, j+1, arrResult[i][j] );
			} // end of for
		} // end of for
		//alert("result:"+arrResult);
	} // end of if
}
