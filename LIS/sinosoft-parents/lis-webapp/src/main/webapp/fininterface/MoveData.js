//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�
var turnPage = new turnPageClass();         //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
var turnPage1 = new turnPageClass();
var showInfo;
var mDebug="0";
var Action;
var tRowNo=0;

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
  }
}

function initQuery() 
{
 /**
  var strSql = "select b.parametervalue,a.makedate,(select min(m.parametervalue) || '��' || max(m.parametervalue) from FIOperationParameter m ,FIOperationLog n where m.eventno = n.eventno and m.parametertype in ('StartDate','EndDate') and n.PerformState = '0' and exists ( select 1 from FIOperationParameter g where g.eventno = m.eventno and g.parametertype = 'BatchNo' and g.parametervalue = b.parametervalue)) as datelenth,a.operator from FIOperationLog  a , FIOperationParameter b where a.eventno = b.eventno and b.EventType = '02' and b.parametertype = 'BatchNo' and a.PerformState = '0' and not exists (select 1 from FIOperationParameter c , FIOperationLog v where c.eventno = v.eventno and v.performstate = '0' and c.eventtype = '11' and c.parametertype = 'BatchNo' and c.parametervalue = b.parametervalue)";
  strSql = strSql + " union select a.AppNo,a.AppDate,'',a.Applicant from FIDataFeeBackApp a where 1=1 and AppState = '04' and not exists (select 1 from FIOperationParameter c, FIOperationLog v where c.eventno = v.eventno and v.performstate = '0' and c.eventtype = '11' and c.parametertype = 'BatchNo' and c.parametervalue = a.AppNo)"
  */
	var mySql1=new SqlClass();
		mySql1.setResourceName("fininterface.MoveDataSql"); //ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId("MoveDataSql1");//ָ��ʹ�õ�Sql��id
		mySql1.addSubPara(1);//ָ������Ĳ���
	var	strSql= mySql1.getString();
  
  turnPage1.queryModal(strSql, MoveDataGrid);
} 
 
function SubmitForm()
{
  if(MoveDataGrid.getSelNo()) 
  { 
     document.all("sBatchNo").value = MoveDataGrid.getRowColData(MoveDataGrid.getSelNo()-1, 1);
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
     document.getElementById("fm").submit(); //�ύ
  }
  else 
  {
    alert("����ѡ��һ�����κ���Ϣ��"); 
  }
}

