//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();

var tResourceName="get.LFGetNoticeSql";
var tResourceSQL1="LFGetNoticeSql1";
var tResourceSQL2="LFGetNoticeSql2";

//�ύ�����水ť��Ӧ����
function printPol()
{
  var showStr = "�����ύ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  
  var tSel = PolGrid.getSelNo();

  if( tSel == 0 || tSel == null )
	{
		alert( "����ѡ��һ����¼���ٵ����ӡ��ť��" );
		return false;
	}
	else
	{
		fm.PrtSeq.value = PolGrid.getRowColData(PolGrid.getSelNo()-1, 1);
   var tResult = fm.PrtSeq.value;
   //alert(fm.PrtSeq.value);
   if(tResult == null || tResult == ""){
       alert("��ѯ��ȡ�嵥��Ϣʧ�ܣ�");
       return;
   }
   fm.action = "../bq/EdorNoticePrintSave.jsp";
   fm.target = "f1print";
   document.submit();
	}
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
  //showInfo.close();
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

    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=350;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

    //showDiv(operateButton,"true"); 
    //showDiv(inputButton,"false"); 
    //ִ����һ������
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

//��ʾfrmSubmit��ܣ���������
function showSubmitFrame(cDebug)
{
  if(cDebug=="1")
  {
			parent.fraMain.rows = "0,0,50,82,*";
  }
 	else
 	{
  		parent.fraMain.rows = "0,0,0,82,*";
 	}
}

// ��ѯ��ť
function easyQueryClick()
{
	initPolGrid();

  //���ȼ���¼���
 if(trim(fm.GrpContNo.value)==""){
 		alert("��¼���ŵ���");
 		return;
 }
	
	// ��дSQL���
	//var strSQL = "select a.prtseq,a.otherno,b.grpname,a.standbyflag1,a.standbyflag2,a.ReqOperator " 
	//					 + "from loprtmanager a,lcgrpcont b " 
	//					 + "where a.othernotype = '01' and code = 'BQ54' and a.otherno = b.GrpContNo " 
	//					 + getWherePart('a.otherno','GrpContNo');
	var strSQL = wrapSql(tResourceName,tResourceSQL1,[fm.GrpContNo.value]); 
	
//	alert(strSQL);
	turnPage.queryModal(strSQL, PolGrid);   
}

function reportDetailClick()
{
	initLFGetGrid();
	var tSel = PolGrid.getSelNo();
	var tGrpContNo = PolGrid.getRowColData(PolGrid.getSelNo()-1, 2);
	var tSerialNo = PolGrid.getRowColData(PolGrid.getSelNo()-1, 4);
	//var tSql = " select a.contno,b.name,decode(a.getdutykind, '1','����','2','����','3','����','4','����','5','����','6','����','7','����','8','����','9','����','����'),"
	//				 + " '��' || (select count(distinct GetNoticeNo)+1 from ljagetdraw where getdate < a.getdate and contno = a.contno and insuredno = a.insuredno) || '��',a.getdate,"
	//				 + " sum(a.getmoney)||'Ԫ',decode(c.paymode, '1','�ֽ�','4','����ת��','����'),"
	//				 + " nvl((select bankname from ldbank where bankcode = c.bankcode),''),nvl(c.accname,''),nvl(c.bankaccno,'') "
	//				 + " from ljagetdraw a, lcinsured b, ljaget c"
	//				 + " where a.grpcontno = b.grpcontno "
	//				 + " and a.contno = b.contno "
	//				 + " and a.insuredno = b.insuredno "
	//				 + " and a.serialno = c.serialno"
	//				 + " and a.actugetno = c.actugetno"
	//				 + " and a.grpcontno = '"+tGrpContNo+"'"
	//				 + " and a.serialno = '"+tSerialNo+"' "
	//				 + " group by a.contno,b.name,a.getdutykind,a.insuredno,a.getdate,c.paymode,c.bankcode,c.bankaccno,c.accname order by a.ContNo";
  var tSql = wrapSql(tResourceName,tResourceSQL2,[tGrpContNo,tSerialNo]); 
	turnPage1.queryModal(tSql, LFGetGrid);
	var rowNum=LFGetGrid.mulLineCount;
	//alert(rowNum);
	if(rowNum<1)
	{
		divGetDrawInfo.style.display = 'none';
	}else {
		divGetDrawInfo.style.display = '';	
	}
}