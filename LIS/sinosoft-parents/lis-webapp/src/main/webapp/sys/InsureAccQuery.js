//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="0";


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

//��ʾfrmSubmit��ܣ���������
function showSubmitFrame(cDebug)
{
  if(cDebug=="1")
  {
			parent.fraMain.rows = "0,0,0,0,*";
  }
 	else {
  		parent.fraMain.rows = "0,0,0,82,*";
 	}
	
	parent.fraMain.rows = "0,0,0,0,*";
}


/*********************************************************************
 *  ��ʾ������ϸ��Ϣ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function showPolDetail()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
	var cPolNo = "";
	if( tSel != null && tSel != 0 )
		cPolNo = PolGrid.getRowColData( tSel - 1, 1 );

	if( cPolNo == null || cPolNo == "" )
		alert("��ѡ��һ�ű������ٽ��в�ѯ  ����Ͷ������ϸ  ����");
	else
	{
			window.open("../sys/PolDetailQueryMain.jsp?PolNo=" + cPolNo + "&IsCancelPolFlag=0","","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
	}
}
       