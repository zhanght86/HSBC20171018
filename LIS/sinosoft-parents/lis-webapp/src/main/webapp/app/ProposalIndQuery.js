//
//�������ƣ�ProposalIndQuery.js
//�����ܣ�����״̬�켣��ѯ
//�������ڣ�2007-03-26 10:02
//������  ��Fuqx
//���¼�¼��  ������    ��������     ����ԭ��/����

//var arrDataSet;
var turnPage = new turnPageClass();
var sqlresourcename = "app.ProposalIndQuerySql";
//�򵥲�ѯ
function easyQuery()
{	/*
	if (document.all('PolNo').value == "" && fm.PrtNo.value == "" && fm.InsuredName.value == "" && fm.InsuredNo.value == "" )
	{
			alert("�����ѯ������");
			return;
	}
	*/
	//���¼���˳�������������������е�����һ����Բ�ѯ ���� ����¼����������
	if(fm.PrtNo.value ==""&&fm.ContNo.value==""&&fm.AppntName.value==""&&fm.InsuredName.value==""&&fm.InsuredNo.value==""){
	   if(fm.ManageCom.value==""){
	      alert("��¼�����������������������е�һ�");
	      return false;
	   }
	}
	if(fm.StartDate.value==""||fm.EndDate.value==""){
		alert("����¼����ʼ���ںͽ������ڣ�����ʱ�䷶Χ���ܳ���һ���£�");
		return false;
	}
	lockScreen('lkscreen');
	initCodeGrid();
		
	// ��дSQL���
	/*
	var strSQL = " select a.PrtNo ,'',a.salechnl ,a.managecom ,a.insuredname ,a.appntname ,"
	           + " (select Name from LAAgent where agentcode = a.agentcode) ,a.Polapplydate ,"
	           + " (select decode(min(to_char(c.makedate, 'yyyy-mm-dd') || ' ' || c.maketime),null,"
	           + "  '��ɨ���', min(to_char(c.makedate, 'yyyy-mm-dd') || ' ' ||c.maketime))"
	           + " from es_doc_main c	where c.doccode = a.prtno) ," 
           	   + " nvl((select max(outdate) from lbmission where processid = '0000000003' and activityid = '0000001099' and missionprop1=a.prtno),"
           	   + " (select max(makedate) from lccont where prtno=a.prtno)),"           
               + " (select max(outdate) from lbmission where processid = '0000000003' and activityid = '0000001090' and missionprop1=a.prtno),"
               + " (select min(makedate) from lbmission where processid = '0000000003' and activityid = '0000001100' and missionprop1 = a.prtno),"
               + " (select max(makedate) from lcissuepol where contno =a.contno and replyman is not null),"
               + " a.uwdate ,"
               + " (select to_char(max(EnterAccDate),'yyyy-mm-dd') from LJTempfee where  (otherno=a.contno or otherno=a.prtno)) , "
               + " (select max(outdate) from lbmission where processid = '0000000003' and activityid = '0000001150' and missionprop1=a.prtno),"
               + " (select to_char(f.makedate,'yyyy-mm-dd')||' '||f.maketime from LCContPrint f where f.contno=a.contno) ,"
               + " decode(a.Appflag,1,'��ǩ��','δǩ��') "
               + "  from lccont a where a.conttype='1' and a.appflag <> '9' "
               		+ getWherePart('a.Makedate','StartDate','>=')
               		+ getWherePart('a.Makedate','EndDate','<=')
             		+ getWherePart('a.PrtNo','PrtNo')
             		+ getWherePart('a.ContNo','ContNo')
             		+ getWherePart('a.InsuredNo','InsuredNo')
             		+ getWherePart('a.AppntName','AppntName')
             		+ getWherePart('a.InsuredName','InsuredName')
             		+ getWherePart('a.ManageCom','ManageCom')
               + " and a.ManageCom like '" + comCode + "%%' order by a.prtno,a.makedate";
	*/

var sqlid1="ProposalIndQuerySql1";
var mySql1=new SqlClass();
mySql1.setResourceName(sqlresourcename);
mySql1.setSqlId(sqlid1);
mySql1.addSubPara(fm.StartDate.value);
mySql1.addSubPara(fm.EndDate.value);
mySql1.addSubPara(fm.PrtNo.value);
mySql1.addSubPara(fm.ContNo.value);
mySql1.addSubPara(fm.InsuredNo.value);
mySql1.addSubPara(fm.AppntName.value);
mySql1.addSubPara(fm.InsuredName.value);
mySql1.addSubPara(fm.ManageCom.value);
mySql1.addSubPara(comCode);
var strSQL =mySql1.getString();	

//	prompt("abc",strSQL);
	turnPage.queryModal(strSQL, CodeGrid);  
	unlockScreen('lkscreen');  
}

function easyPrint()
{
if(fm.PrtNo.value ==""&&fm.ContNo.value==""&&fm.AppntName.value==""&&fm.InsuredName.value==""&&fm.InsuredNo.value==""){
	   if(fm.ManageCom.value==""){
	      alert("��¼�����������������������е�һ�");
	      return false;
	   }
	}
if(fm.StartDate.value==""||fm.EndDate.value==""){
	alert("����¼����ʼ���ںͽ������ڣ�����ʱ�䷶Χ���ܳ���һ���£�");
	return false;
}
	//easyQueryPrint(2,'CodeGrid','turnPage');
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


  var arrReturn = new Array();
		fm.target = "../f1print";
		//fm.action = "";
		document.getElementById("fm").submit();
		showInfo.close();
}

