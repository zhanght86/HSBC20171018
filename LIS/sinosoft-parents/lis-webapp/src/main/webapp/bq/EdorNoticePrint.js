
//�������ƣ�EdorNoticePrint.js
//�����ܣ���ȫ֪ͨ�����ߴ�ӡ����̨
//�������ڣ�2005-08-02 16:20:22
//���¼�¼��  ������    ��������      ����ԭ��/����


//���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var turnPage = new turnPageClass();



//�ύ�����水ť��Ӧ����
function printNotice()
{
  var i = 0;
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  //showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

  var tSel = NoticeGrid.getSelNo();

  if( tSel == 0 || tSel == null )
  {
  	    showInfo.close();
		alert( "����ѡ��һ����¼!" );
  }
  else
  {
		PrtSeq = NoticeGrid.getRowColData(tSel-1,1);
		fm.PrtSeq.value = PrtSeq;
		fm.fmtransact.value = "PRINT";
		fm.target = "f1print";
		document.getElementById("fm").submit();
		showInfo.close();
  }
}



//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
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
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  }
}
function queryNotice()
{
	var tNoticeType = fm.NoticeType.value;
	var strSql = "";
	if(tNoticeType == null || tNoticeType == "")
	{
		alert("��ѡ��֪ͨ�����ͣ� ");
		fm.NoticeType.focus();
		return;
	}
	if(trim(fm.OtherNo.value) == "" && trim(fm.NoticeNo.value) == "")
	{
	  if(fm.StartDate.value == null || trim(fm.StartDate.value) == "")
	  {
		alert("��ѡ��ͳ�����ڣ� ");
		fm.StartDate.focus();
		return;
	  }
	  if(fm.EndDate.value == null || trim(fm.EndDate.value) == "")
	  {
		alert("��ѡ��ͳ��ֹ�ڣ� ");
		fm.EndDate.focus();
		return;
	  }
	}
	strSql = getSql(tNoticeType);
	//alert(strSql) ;
    var brr = easyExecSql(strSql);
    if(brr)
    {
    	initNoticeGrid(tNoticeType);
     	turnPage.queryModal(strSql,NoticeGrid);
    }
    else
    {
     	var tNoticeTypeName = fm.NoticeTypeName.value;
     	if(tNoticeTypeName == null||tNoticeTypeName=="")
     	{
     		alert("û��Ҫ��ӡ�ı�ȫ֪ͨ�飡 ");

     	}
        else
        {
     	   alert("û��Ҫ��ӡ�ı�ȫ"+tNoticeTypeName+"�� ");
        }
        initNoticeGrid(tNoticeType);
     	return ;
    }

}
// ����ʱ��ѯ֪ͨ�������б�(֮���Ը�Ϊ�������������Ϊ�˴�ӡ�귵��ʱ���²�ѯ������У�֮������Ӻ��������ǵ���ԭ���ĺ���������Ϊ���غ�鲻����¼ʱ�����Ի������û���ɲ���)
function easyQueryClick()
{
	var tNoticeType = fm.NoticeType.value;
	var strSql = "";
	if(tNoticeType == null || tNoticeType == "")
	{
		alert("��ѡ��֪ͨ�����ͣ�");
		fm.NoticeType.focus();
		return;
	}
	if(trim(fm.OtherNo.value) == "" && trim(fm.NoticeNo.value) == ""&& trim(fm.ContNo.value) == ""&& trim(fm.EdorAcceptNo.value) == "")
	{
	  if(fm.StartDate.value == null || trim(fm.StartDate.value) == "")
	  {
		alert("��ѡ��ͳ�����ڣ� ");
		fm.StartDate.focus();
		return;
	  }
	  if(fm.EndDate.value == null || trim(fm.EndDate.value) == "")
	  {
		alert("��ѡ��ͳ��ֹ�ڣ� ");
		fm.EndDate.focus();
		return;
	  }
	}
	strSql = getSql(tNoticeType);
    var brr = easyExecSql(strSql);
    if(brr)
    {
    	initNoticeGrid(tNoticeType);
     	turnPage.queryModal(strSql,NoticeGrid);
    }
    else
    {
     	var tNoticeTypeName = fm.NoticeTypeName.value;
     	if(tNoticeTypeName == null||tNoticeTypeName=="")
     	{
     		//alert("û��Ҫ��ӡ�ı�ȫ֪ͨ�飡");

     	}
        else
        {
     	   //alert("û��Ҫ��ӡ�ı�ȫ"+tNoticeTypeName);
        }
        initNoticeGrid(tNoticeType);
     	return ;
    }

}
//����ҵ��Ա��Ϣ��ѯҳ��
function queryAgent()
{
	if(document.all('ManageCom').value=="")
	{
		 alert("����ѡ����������");
		 return;
	}
	var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+document.all('ManageCom').value+"&branchtype=1","AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');

}
//ѡ��ҵ��Ա������������
function afterQuery2(arrResult)
{
  if(arrResult!=null)
  {
    fm.AgentCode.value = arrResult[0][0];
  }
}
//У��ֺ�����ȵ�����
function isYearNum()
{
 	var tYear = fm.FiscalYear.value;
	if(tYear!=null && tYear!="")
	{
		if (!isNumeric(tYear))
		{
			alert("������ֵ��������Ч���֣�");
			fm.FiscalYear.value = "";
			fm.FiscalYear.focus();
			return;
		}
		if(tYear<1900||tYear>9999)
		{
			alert("����������������������Ч�����ֵ(1900-9999)��");
			fm.FiscalYear.value = "";
			fm.FiscalYear.focus();
			return;
		}
	}
}
//ѡ��֪ͨ�����ͺ���ʾ���ض�������������ʼ���������б�
function noticeSel()
{
	var NoticeType = fm.NoticeType.value;
	initNoticeInp(NoticeType);
    initNoticeGrid(NoticeType);
}
//����֪ͨ��������ʾ����Ҫ�ļ�������
function initNoticeInp(tNoticeType)
{
    //Ĭ��
    
    switch(tNoticeType)
    {
    	case   "":
    	case "30":         divContTitle.style.display = '';
    	                   divEdorAcceptTitle.style.display = 'none';
    	                   divCont.style.display = '';
    	                   divBonus.style.display = '';
    	                   divCustomerTitle.style.display = '';
    	                   divCustomer.style.display = '';
    	                   divOtherNo.style.display = '';
    	                   divOtherNo2.style.display = 'none';
    	                   break;
    	                   
    	                   
//=====add========liuxiaosong==============2007-1-4===========start=============
      case "34":         divContTitle.style.display = '';
      									 divContTitle.innerText='���屣����'
    	                   divEdorAcceptTitle.style.display = 'none';
    	                   divCont.style.display = 'none';
    	                   divBonus.style.display = '';
    	                   divCustomerTitle.style.display = 'none';
    	                   divCustomer.style.display = 'none';    
    	                   divOtherNo.style.display = '';
    	                   divOtherNo2.style.display = 'none';	                   
    	                   break;
      case "35":         divContTitle.style.display = '';
      									 divContTitle.innerText='���屣����'
    	                   divEdorAcceptTitle.style.display = 'none';
    	                   divCont.style.display = 'none';
    	                   divBonus.style.display = '';
    	                   divCustomerTitle.style.display = 'none';
    	                   divCustomer.style.display = 'none';    	
    	                   divOtherNo.style.display = '';
    	                   divOtherNo2.style.display = 'none';                   
    	                   break;
//=====add========liuxiaosong==============2007-1-4============end=============


    	case "BQ31":
    	case "BQ32":
    	case "BQ51":
    	case "BQ52":
    	case "BQ27":
    	case "BQ28":       divCont.style.display = 'none';
    	                   divBonus.style.display = 'none';
    	                   divCustomerTitle.style.display = '';
    	                   divCustomer.style.display = '';
                           divContTitle.style.display = 'none';
    	                   divEdorAcceptTitle.style.display = '';
    	                   divOtherNo.style.display = '';
    	                   divOtherNo2.style.display = 'none';
    	                   break;
    	case "BQ48":
    	case "BQ49":       divCont.style.display = 'none';
    	                   divBonus.style.display = 'none';
    	                   divCustomerTitle.style.display = 'none';
    	                   divCustomer.style.display = 'none';
                           divContTitle.style.display = 'none';
    	                   divEdorAcceptTitle.style.display = '';
    	                   divOtherNo.style.display = '';
    	                   divOtherNo2.style.display = 'none';
    	                   break;
    	case "41":
    	case "42":
    	case "BQ21":
    	case "BQ22":
    	case "BQ29":
    	case "BQ30":
    	case "BQ34":
    	case "BQ38":
    	case "BQ39":
    	case "BQ10":
    	case "BQ17":       divCont.style.display = '';
    	                   divBonus.style.display = 'none';
    	                   divCustomerTitle.style.display = '';
    	                   divCustomer.style.display = '';
    	                   divContTitle.style.display = '';
    	                   divEdorAcceptTitle.style.display = 'none';
    	                   divOtherNo.style.display = '';
    	                   divOtherNo2.style.display = 'none';
    	                   break;
    	case "BQ18":       divCont.style.display = '';
    	                   divBonus.style.display = 'none';
    	                   divCustomerTitle.style.display = '';
    	                   divCustomer.style.display = '';
    	                   divContTitle.style.display = '';
    	                   divEdorAcceptTitle.style.display = 'none';
    	                   divOtherNo.style.display = '';
    	                   divOtherNo2.style.display = 'none';
    	                   break;    
    	case "BQ71": 
    	case "BQ72":       divOtherNo.style.display = 'none';
    	                   divOtherNo2.style.display = '';
    	                   divCustomerTitle.style.display = 'none';
    	                   divCustomer.style.display = 'none';
    	                   divCont.style.display = '';
    	                   divBonus.style.display = 'none';
    	                   break;
    	default:
    	                   divContTitle.style.display = '';
    	                   divEdorAcceptTitle.style.display = 'none';
    	                   divCont.style.display = 'none';
    	                   divBonus.style.display = 'none';
    	                   divCustomerTitle.style.display = 'none';
    	                   divCustomer.style.display = 'none';
    	                   divOtherNo.style.display = '';
    	                   divOtherNo2.style.display = 'none';
    	                   break;
    }
}
//����֪ͨ�����ͷ������ض���sql��ѯ���
function getSql(tNoticeType)
{
   var strSql = "";
   switch(tNoticeType)
   {
   	 //����ֺ�ҵ�������������嵥
   	 case "35":         // strSql = "select a.prtseq,a.otherno, "
//			 	                 			 + " (select codename from ldcode where codetype = 'station' and code = a.managecom ), "
//		 	                         + " b.grpname,"
//	                             + " (select name from laagent where agentcode = trim(a.agentcode)), "
//	                             + " b.cvalidate,"
//	                             + " (case when b.appflag = '4' then '��ֹ' else '�б�' end), "
//	                             + " a.standbyflag1 "
//	                             + " from lcgrppol b,loprtmanager a where 1 = 1 "
//	                             + " and a.StateFlag = '0' "
//	                             + getWherePart('a.prtseq', 'NoticeNo')
//	                             + getWherePart('a.otherno', 'OtherNo')
//	                             + getWherePart('a.Code', 'NoticeType')
//	                             + getWherePart('a.standbyflag1', 'FiscalYear')
//		                           + " and a.standbyflag3='bqnotice' and b.grpcontno = a.otherno "
//		                           + getWherePart('a.ManageCom', 'ManageCom', 'like')
//	                             + " and a.ManageCom LIKE '" + comcode.substring(0,6) + "%'";//��½����Ȩ�޿���;
   	 
	   	 					var sqlid1="EdorNoticePrintSql1";
							var mySql1=new SqlClass();
							mySql1.setResourceName("bq.EdorNoticePrintSql");
							mySql1.setSqlId(sqlid1); //ָ��ʹ��SQL��id
							mySql1.addSubPara(window.document.getElementsByName(trim("NoticeNo"))[0].value);//ָ���������
							mySql1.addSubPara(window.document.getElementsByName(trim("OtherNo"))[0].value);
							mySql1.addSubPara(window.document.getElementsByName(trim("NoticeType"))[0].value);
							mySql1.addSubPara(window.document.getElementsByName(trim("FiscalYear"))[0].value);
							mySql1.addSubPara(window.document.getElementsByName(trim("ManageCom"))[0].value);
							mySql1.addSubPara(comcode.substring(0,6));
							strSql = mySql1.getString();
   	 
   	                        break;
   	 case "34":          //strSql = "select a.prtseq,a.otherno, "
//			 	                 			 + " (select codename from ldcode where codetype = 'station' and code = a.managecom ), "
//		 	                         + " b.grpname,"
//	                             + " (select name from laagent where agentcode = trim(a.agentcode)), "
//	                             + " b.cvalidate,"
//	                             + " (case when b.appflag = '4' then '��ֹ' else '�б�' end), "
//	                             + " a.standbyflag1 "
//	                             + " from lcgrppol b,loprtmanager a where 1 = 1 "
//	                             + " and a.StateFlag = '0' "
//	                             + getWherePart('a.prtseq', 'NoticeNo')
//	                             + getWherePart('a.otherno', 'OtherNo')
//	                             + getWherePart('a.Code', 'NoticeType')
//	                             + getWherePart('a.standbyflag1', 'FiscalYear')
//		                           + " and a.standbyflag3='bqnotice' and b.grpcontno = a.otherno "
//		                           + getWherePart('a.ManageCom', 'ManageCom', 'like')
//	                             + " and a.ManageCom LIKE '" + comcode.substring(0,6) + "%'";//��½����Ȩ�޿���;
   	                    
							var sqlid2="EdorNoticePrintSql2";
							var mySql2=new SqlClass();
							mySql2.setResourceName("bq.EdorNoticePrintSql");
							mySql2.setSqlId(sqlid2); //ָ��ʹ��SQL��id
							mySql2.addSubPara(window.document.getElementsByName(trim("NoticeNo"))[0].value);//ָ���������
							mySql2.addSubPara(window.document.getElementsByName(trim("OtherNo"))[0].value);
							mySql2.addSubPara(window.document.getElementsByName(trim("NoticeType"))[0].value);
							mySql2.addSubPara(window.document.getElementsByName(trim("FiscalYear"))[0].value);
							mySql2.addSubPara(window.document.getElementsByName(trim("ManageCom"))[0].value);
							mySql2.addSubPara(comcode.substring(0,6));
							strSql = mySql2.getString();
   	 
   	 						break;
   	  //�ֺ�ҵ��������
   	  //�������룺polno�����Ͷ���Ͷ���ˣ���������������ֺ������
   	  case "30":          //strSql = "select a.prtseq,b.contno, "
//			 	                 + " (select codename from ldcode where codetype = 'station' and code = a.managecom ), "
//		 	                     + " b.appntname,"
//	                             + " (select name from laagent where agentcode = trim(a.agentcode)), "
//	                             + " b.cvalidate,"
//	                             + " (case when b.appflag = '4' then '��ֹ' else '�б�' end), "
//	                             + " a.remark "
//	                             + " from lccont b,loprtmanager a where 1 = 1 "
//	                             + getWherePart('a.ManageCom', 'ManageCom', 'like')
//	                             + getWherePart('a.prtseq', 'NoticeNo')
//	                             + getWherePart('b.contno', 'OtherNo')
//	                             + getWherePart('a.Code', 'NoticeType')
//	                             + getWherePart('a.AgentCode', 'AgentCode')
//	                             + getWherePart('b.SaleChnl', 'SaleChnl')
//	                             + getWherePart('a.remark', 'FiscalYear')
//	                             + getWherePart('b.appntname', 'CustomerName')
//		                         + " and a.otherno = b.contno and b.appflag = '1' "
//	                             + " and a.ManageCom LIKE '" + comcode.substring(0,6) + "%%'" //��½����Ȩ�޿���
//	                             + " and a.StateFlag = '0' ";
   	  
							var sqlid3="EdorNoticePrintSql3";
							var mySql3=new SqlClass();
							mySql3.setResourceName("bq.EdorNoticePrintSql");
							mySql3.setSqlId(sqlid3); //ָ��ʹ��SQL��id
							mySql3.addSubPara(window.document.getElementsByName(trim("ManageCom"))[0].value);//ָ���������
							mySql3.addSubPara(window.document.getElementsByName(trim("NoticeNo"))[0].value);
							mySql3.addSubPara(window.document.getElementsByName(trim("OtherNo"))[0].value);
							mySql3.addSubPara(window.document.getElementsByName(trim("NoticeType"))[0].value);
							mySql3.addSubPara(window.document.getElementsByName(trim("AgentCode"))[0].value);
							mySql3.addSubPara(window.document.getElementsByName(trim("SaleChnl"))[0].value);
							mySql3.addSubPara(window.document.getElementsByName(trim("FiscalYear"))[0].value);
							mySql3.addSubPara(window.document.getElementsByName(trim("CustomerName"))[0].value);
							mySql3.addSubPara(comcode.substring(0,6));
							strSql = mySql3.getString();
   	  
   	                        break;
   	  //��Ϣ֪ͨ�飬����֪ͨ��,����֪ͨ��
   	  //�������룺edoracceptno�����Ͷ���������
   	  case "BQ31":         // strSql = "select a.prtseq,a.otherno,"
//		 	                     + " (select codename from ldcode where codetype = 'station' and code = a.managecom ), "
//		 	                     + " b.edorappname "
//	                             + " from lpedorapp b,loprtmanager a  where 1 = 1 "
//	                             + getWherePart('a.ManageCom', 'ManageCom', 'like')
//	                             + getWherePart('a.prtseq', 'NoticeNo')
//	                             + getWherePart('a.OtherNo', 'OtherNo')
//	                             + getWherePart('a.Code', 'NoticeType')
//	                             + getWherePart('b.edorappname', 'CustomerName')
//	                             + " and b.edoracceptno = a.otherno "
//	                             + " and a.ManageCom LIKE '" + comcode.substring(0,6) + "%%'" //��½����Ȩ�޿���
//	                             + " and a.StateFlag = '0' and exists( select 1 from lwmission where activityid='0000000009'  and missionprop1=b.edoracceptno)";

							var sqlid4="EdorNoticePrintSql4";
							var mySql4=new SqlClass();
							mySql4.setResourceName("bq.EdorNoticePrintSql");
							mySql4.setSqlId(sqlid4); //ָ��ʹ��SQL��id
							mySql4.addSubPara(window.document.getElementsByName(trim("ManageCom"))[0].value);//ָ���������
							mySql4.addSubPara( window.document.getElementsByName(trim("NoticeNo"))[0].value);
							mySql4.addSubPara(window.document.getElementsByName(trim("OtherNo"))[0].value);
							mySql4.addSubPara(window.document.getElementsByName(trim("NoticeType"))[0].value);
							mySql4.addSubPara(window.document.getElementsByName(trim("CustomerName"))[0].value);							
							mySql4.addSubPara(comcode.substring(0,6));
							strSql = mySql4.getString();
   	  
   	  
   	  
   	                        break;
   	  case "BQ32":
   	  case "BQ52":
   	  case "BQ27":
   	  case "BQ28":        //strSql = "select a.prtseq,a.otherno,"
//		 	                     + " (select codename from ldcode where codetype = 'station' and code = a.managecom ), "
//		 	                     + " b.edorappname "
//	                             + " from lpedorapp b,loprtmanager a  where 1 = 1 "
//	                             + getWherePart('a.ManageCom', 'ManageCom', 'like')
//	                             + getWherePart('a.prtseq', 'NoticeNo')
//	                             + getWherePart('a.OtherNo', 'OtherNo')
//	                             + getWherePart('a.Code', 'NoticeType')
//	                             + getWherePart('b.edorappname', 'CustomerName')
//	                             + " and b.edoracceptno = a.otherno "
//	                             + " and a.ManageCom LIKE '" + comcode.substring(0,6) + "%%'" //��½����Ȩ�޿���
//	                             + " and a.StateFlag = '0' ";
						   	
   	  						var sqlid5="EdorNoticePrintSql5";
							var mySql5=new SqlClass();
							mySql5.setResourceName("bq.EdorNoticePrintSql");
							mySql5.setSqlId(sqlid5); //ָ��ʹ��SQL��id
							mySql5.addSubPara(window.document.getElementsByName(trim("ManageCom"))[0].value);//ָ���������
							mySql5.addSubPara( window.document.getElementsByName(trim("NoticeNo"))[0].value);
							mySql5.addSubPara(window.document.getElementsByName(trim("OtherNo"))[0].value);
							mySql5.addSubPara(window.document.getElementsByName(trim("NoticeType"))[0].value);
							mySql5.addSubPara(window.document.getElementsByName(trim("CustomerName"))[0].value);							
							mySql5.addSubPara(comcode.substring(0,6));
							strSql = mySql5.getString();

   	  						
   	  
   	                        break;
   	  case "BQ48":
   	  case "BQ49":     //   strSql = "select a.prtseq,a.otherno,"
//		 	                     + " (select codename from ldcode where codetype = 'station' and code = a.managecom )  "
//	                             + " from loprtmanager a where 1 = 1 "
//	                             + getWherePart('a.ManageCom', 'ManageCom', 'like')
//	                             + getWherePart('a.prtseq', 'NoticeNo')
//	                             + getWherePart('a.OtherNo', 'OtherNo')
//	                             + getWherePart('a.Code', 'NoticeType')
//	                             + " and a.ManageCom LIKE '" + comcode.substring(0,6) + "%%'" //��½����Ȩ�޿���
//	                             + " and a.StateFlag = '0' ";
   	  
						   	var sqlid6="EdorNoticePrintSql6";
							var mySql6=new SqlClass();
							mySql6.setResourceName("bq.EdorNoticePrintSql");
							mySql6.setSqlId(sqlid6); //ָ��ʹ��SQL��id
							mySql6.addSubPara(window.document.getElementsByName(trim("ManageCom"))[0].value);//ָ���������
							mySql6.addSubPara( window.document.getElementsByName(trim("NoticeNo"))[0].value);
							mySql6.addSubPara(window.document.getElementsByName(trim("OtherNo"))[0].value);
							mySql6.addSubPara(window.document.getElementsByName(trim("NoticeType"))[0].value);							
							mySql6.addSubPara(comcode.substring(0,6));
							strSql = mySql6.getString();


   	                       break;
   	  //��ȡ֪ͨ�飬�������룺polno�����Ͷ�������������
   	  case "BQ17":     //   strSql = "select a.prtseq,b.contno,"
//		 	                     + " (select codename from ldcode where codetype = 'station' and code = a.managecom ), "
//		 	                     + " b.insuredname,"
//	                             + " (select name from laagent where agentcode = trim(a.agentcode)), "
//	                             + " b.cvalidate,"
//	                             + " (case when exists(select * from lccontstate where statetype = 'Available' and enddate is null and state = '1' and polno = a.otherno ) then 'ʧЧ' else '��Ч' end)  "
//	                             + " from lcpol b,loprtmanager a where 1 = 1 "
//	                             + getWherePart('a.ManageCom', 'ManageCom', 'like')
//	                             + getWherePart('a.prtseq', 'NoticeNo')
//	                             + getWherePart('b.contno', 'OtherNo')
//	                             + getWherePart('a.Code', 'NoticeType')
//	                             + getWherePart('a.AgentCode', 'AgentCode')
//	                             + getWherePart('b.SaleChnl', 'SaleChnl')
//	                             + getWherePart('b.insuredname', 'CustomerName')
//	                             + " and a.otherno = b.contno and b.polno=b.mainpolno "//otherno�����contno
//	                             + " and a.ManageCom LIKE '" + comcode.substring(0,6) + "%%'" //��½����Ȩ�޿���
//	                             + " and a.StateFlag = '0' ";
	                             //modify by jiaqiangli 2009-05-13 ֱ���ж�loprtmanager ����������һ�飬����Ҫ����
															// + " and exists(select 1 from lcget where polno = b.polno and  getdutycode in (select getdutycode from LMDutyGet f where GetType1 = '1' and getdutycode = f.getdutycode))";
						   	
   	  						var sqlid7="EdorNoticePrintSql7";
							var mySql7=new SqlClass();
							mySql7.setResourceName("bq.EdorNoticePrintSql");
							mySql7.setSqlId(sqlid7); //ָ��ʹ��SQL��id
							mySql7.addSubPara(window.document.getElementsByName(trim("ManageCom"))[0].value);//ָ���������
							mySql7.addSubPara( window.document.getElementsByName(trim("NoticeNo"))[0].value);
							mySql7.addSubPara(window.document.getElementsByName(trim("OtherNo"))[0].value);
							mySql7.addSubPara(window.document.getElementsByName(trim("NoticeType"))[0].value);
							mySql7.addSubPara(window.document.getElementsByName(trim("AgentCode"))[0].value);
							mySql7.addSubPara(window.document.getElementsByName(trim("SaleChnl"))[0].value);
							mySql7.addSubPara(window.document.getElementsByName(trim("CustomerName"))[0].value);
							mySql7.addSubPara(comcode.substring(0,6));
							strSql = mySql7.getString();
	
   	  			
	                             
   	                       break;
   	  //������ȡ֪ͨ�飬�������룺polno�����Ͷ��󣺱�����
   	  //��������1	GetType1	CHAR(1)	"0--���ڽ� 1--���" LMDutyGet
   	  case "BQ18":     //   strSql = "select a.prtseq,b.contno,"
//		 	                     + " (select codename from ldcode where codetype = 'station' and code = a.managecom ), "
//		 	                     + " b.insuredname,"
//	                             + " (select name from laagent where agentcode = trim(a.agentcode)), "
//	                             + " b.cvalidate,"
//	                             + " (case when exists(select * from lccontstate where statetype = 'Available' and enddate is null and state = '1' and polno = a.otherno ) then 'ʧЧ' else '��Ч' end)  "
//	                             + " from lcpol b,loprtmanager a where 1 = 1 "
//	                             + getWherePart('a.ManageCom', 'ManageCom', 'like')
//	                             + getWherePart('a.prtseq', 'NoticeNo')
//	                             + getWherePart('b.contno', 'OtherNo')
//	                             //+ getWherePart('a.Code', 'NoticeType')
//	                             + getWherePart('a.AgentCode', 'AgentCode')
//	                             + getWherePart('b.SaleChnl', 'SaleChnl')
//	                             + getWherePart('b.insuredname', 'CustomerName')
//	                             + " and code = 'BQ17'"
//	                             + " and a.otherno = b.polno "
//	                             + " and a.ManageCom LIKE '" + comcode.substring(0,6) + "%%'" //��½����Ȩ�޿���
//	                             + " and a.StateFlag = '0' "
//							     + " and exists(select 1 from lcget where polno = b.polno and  getdutycode in (select getdutycode from LMDutyGet f where GetType1 = '0' and getdutycode = f.getdutycode))";
	                             
						   	var sqlid8="EdorNoticePrintSql8";
							var mySql8=new SqlClass();
							mySql8.setResourceName("bq.EdorNoticePrintSql");
							mySql8.setSqlId(sqlid8); //ָ��ʹ��SQL��id
							mySql8.addSubPara(window.document.getElementsByName(trim("ManageCom"))[0].value);//ָ���������
							mySql8.addSubPara( window.document.getElementsByName(trim("NoticeNo"))[0].value);
							mySql8.addSubPara(window.document.getElementsByName(trim("OtherNo"))[0].value);
							mySql8.addSubPara(window.document.getElementsByName(trim("AgentCode"))[0].value);
							mySql8.addSubPara(window.document.getElementsByName(trim("SaleChnl"))[0].value);
							mySql8.addSubPara(window.document.getElementsByName(trim("CustomerName"))[0].value);
							mySql8.addSubPara(comcode.substring(0,6));
							strSql = mySql8.getString();
   	  
   	                        break;
   	  //������ֹ֪ͨ��
   	  //�������룺polno�����Ͷ���Ͷ����
   	  case "BQ34":     //   strSql = "select a.prtseq,b.contno,"
//		 	                     + " (select codename from ldcode where codetype = 'station' and code = a.managecom ), "
//		 	                     + " b.appntname,"
//	                             + " (select name from laagent where agentcode = trim(a.agentcode)), "
//	                             + " b.cvalidate,"
//	                             + " '��ֹ'  "
//	                             + " from lcpol b,loprtmanager a where 1 = 1 "
//	                             + getWherePart('b.ManageCom', 'ManageCom', 'like')
//	                             + getWherePart('a.ManageCom', 'ManageCom', 'like')
//	                             + getWherePart('a.prtseq', 'NoticeNo')
//	                             + getWherePart('b.contno', 'OtherNo')
//	                             + getWherePart('a.Code', 'NoticeType')
//	                             + getWherePart('a.AgentCode', 'AgentCode')
//	                             + getWherePart('b.SaleChnl', 'SaleChnl')
//	                             + getWherePart('b.appntname', 'CustomerName')
//	                             + " and a.otherno = b.polno and b.appflag = '4' and b.polno = b.mainpolno "
//	                             + " and a.ManageCom LIKE '" + comcode.substring(0,6) + "%%'" //��½����Ȩ�޿���
//	                             + " and a.StateFlag = '0' ";
   	  
						  	var sqlid9="EdorNoticePrintSql9";
							var mySql9=new SqlClass();
							mySql9.setResourceName("bq.EdorNoticePrintSql");
							mySql9.setSqlId(sqlid9); //ָ��ʹ��SQL��id
							mySql9.addSubPara(window.document.getElementsByName(trim("ManageCom"))[0].value);//ָ���������
							mySql9.addSubPara( window.document.getElementsByName(trim("ManageCom"))[0].value);
							mySql9.addSubPara(window.document.getElementsByName(trim("NoticeNo"))[0].value);
							mySql9.addSubPara(window.document.getElementsByName(trim("OtherNo"))[0].value);
							mySql9.addSubPara(window.document.getElementsByName(trim("NoticeType"))[0].value);
							mySql9.addSubPara(window.document.getElementsByName(trim("AgentCode"))[0].value);
							mySql9.addSubPara(window.document.getElementsByName(trim("SaleChnl"))[0].value);
							mySql9.addSubPara(window.document.getElementsByName(trim("CustomerName"))[0].value);
							mySql9.addSubPara(comcode.substring(0,6));
							strSql = mySql9.getString();
   	  
   	                        break;
   	  //ʧЧԤ��ֹ֪ͨ��
   	  //�������룺polno�����Ͷ���Ͷ����
   	  case "BQ29":   //     strSql = "select a.prtseq,b.contno,"
//		 	                     + " (select codename from ldcode where codetype = 'station' and code = a.managecom ), "
//		 	                     + " b.appntname,"
//	                             + " (select name from laagent where agentcode = trim(a.agentcode)), "
//	                             + " b.cvalidate,"
//	                             + " 'ʧЧ' "
//	                             + " from lcpol b,loprtmanager a where 1 = 1 "
//	                             + " and exists(select 'X' from lccontstate where statetype='Available' and enddate is null and state = '1' and polno = b.polno)"
//	                             + getWherePart('a.ManageCom', 'ManageCom', 'like')
//	                             + getWherePart('a.prtseq', 'NoticeNo')
//	                             + getWherePart('b.contno', 'OtherNo')
//	                             + getWherePart('a.Code', 'NoticeType')
//	                             + getWherePart('a.AgentCode', 'AgentCode')
//	                             + getWherePart('b.SaleChnl', 'SaleChnl')
//	                             + getWherePart('b.appntname', 'CustomerName')
//	                             + " and a.otherno = b.polno "
//	                             + " and a.ManageCom LIKE '" + comcode.substring(0,6) + "%%'" //��½����Ȩ�޿���
//	                             + " and a.StateFlag = '0' ";
   	  
						  	var sqlid10="EdorNoticePrintSql10";
							var mySql10=new SqlClass();
							mySql10.setResourceName("bq.EdorNoticePrintSql");
							mySql10.setSqlId(sqlid10); //ָ��ʹ��SQL��id
							mySql10.addSubPara(window.document.getElementsByName(trim("ManageCom"))[0].value);//ָ���������
							mySql10.addSubPara(window.document.getElementsByName(trim("NoticeNo"))[0].value);
							mySql10.addSubPara(window.document.getElementsByName(trim("OtherNo"))[0].value);
							mySql10.addSubPara(window.document.getElementsByName(trim("NoticeType"))[0].value);
							mySql10.addSubPara(window.document.getElementsByName(trim("AgentCode"))[0].value);
							mySql10.addSubPara(window.document.getElementsByName(trim("SaleChnl"))[0].value);
							mySql10.addSubPara(window.document.getElementsByName(trim("CustomerName"))[0].value);
							mySql10.addSubPara(comcode.substring(0,6));
							strSql = mySql10.getString();
						   	  
   	  
   	                        break;

   	  //����ʧЧ֪ͨ��
   	  //�������룺contno�����Ͷ���Ͷ����
   	      case "42":
//   	                      strSql = "select a.prtseq,a.otherno,"
//		 	                     + " (select codename from ldcode where codetype = 'station' and code = a.managecom ), "
//		 	                     + " b.appntname,"
//	                             + " (select name from laagent where agentcode = trim(a.agentcode)), "
//	                             + " b.cvalidate,"
//	                             + " 'ʧЧ'  "
//	                             + " from lccont b,loprtmanager a where 1 = 1 "
//	                             + " and exists(select 'X' from lccontstate where statetype='Available' and enddate is null and state = '1' and contno = a.otherno)"
//	                             + getWherePart('a.ManageCom', 'ManageCom', 'like')
//	                             + getWherePart('a.prtseq', 'NoticeNo')
//	                             + getWherePart('a.OtherNo', 'OtherNo')
//	                             + getWherePart('a.Code', 'NoticeType')
//	                             + getWherePart('a.AgentCode', 'AgentCode')
//	                             + getWherePart('b.SaleChnl', 'SaleChnl')
//	                             + getWherePart('b.appntname', 'CustomerName')
//	                             + " and a.otherno = b.contno "
//	                             + " and a.ManageCom LIKE '" + comcode.substring(0,6) + "%%'" //��½����Ȩ�޿���
//	                             + " and a.StateFlag = '0' and a.makedate>='2009-6-15'"; //ֻ�ܻ�ȡ��ϵͳ���ߺ������
   	                      
	   	                    var sqlid11="EdorNoticePrintSql11";
							var mySql11=new SqlClass();
							mySql11.setResourceName("bq.EdorNoticePrintSql");
							mySql11.setSqlId(sqlid11); //ָ��ʹ��SQL��id
							mySql11.addSubPara(window.document.getElementsByName(trim("ManageCom"))[0].value);//ָ���������
							mySql11.addSubPara(window.document.getElementsByName(trim("NoticeNo"))[0].value);
							mySql11.addSubPara(window.document.getElementsByName(trim("OtherNo"))[0].value);
							mySql11.addSubPara(window.document.getElementsByName(trim("NoticeType"))[0].value);
							mySql11.addSubPara(window.document.getElementsByName(trim("AgentCode"))[0].value);
							mySql11.addSubPara(window.document.getElementsByName(trim("SaleChnl"))[0].value);
							mySql11.addSubPara(window.document.getElementsByName(trim("CustomerName"))[0].value);
							mySql11.addSubPara(comcode.substring(0,6));
							strSql = mySql11.getString();
   	                      
   	                        break;

   	  //�Ե�Ԥ��ֹ֪ͨ�顢�Ե���ֹ֪ͨ�顢�����Ե�Ԥ��ֹ֪ͨ��
   	  //�����Ե�֪ͨ�顢����ʧЧ֪ͨ�顢������Ѻ�����֪ͨ��
   	  //�������룺contno�����Ͷ���Ͷ����
      case "41":
      case "BQ21":
      case "BQ22":
      case "BQ38":
      case "BQ39":
      case "BQ10":
//    	                  strSql = "select a.prtseq,a.otherno,"
//		 	                     + " (select codename from ldcode where codetype = 'station' and code = a.managecom ), "
//		 	                     + " b.appntname,"
//	                             + " (select name from laagent where agentcode = trim(a.agentcode)), "
//	                             + " b.cvalidate,"
//	                             + " (case when exists(select * from lccontstate where statetype = 'Available' and enddate is null and state = '1' and contno = a.otherno ) then 'ʧЧ' else '��Ч' end)  "
//	                             + " from lccont b,loprtmanager a where 1 = 1 "
//	                             + getWherePart('a.ManageCom', 'ManageCom', 'like')
//	                             + getWherePart('a.prtseq', 'NoticeNo')
//	                             + getWherePart('a.OtherNo', 'OtherNo')
//	                             + getWherePart('a.Code', 'NoticeType')
//	                             + getWherePart('a.AgentCode', 'AgentCode')
//	                             + getWherePart('b.SaleChnl', 'SaleChnl')
//	                             + getWherePart('b.appntname', 'CustomerName')
//	                             + " and a.otherno = b.contno "
//	                             + " and a.ManageCom LIKE '" + comcode.substring(0,6) + "%%'" //��½����Ȩ�޿���
//	                             + " and a.StateFlag = '0' ";
    	                  
    	                    var sqlid12="EdorNoticePrintSql12";
							var mySql12=new SqlClass();
							mySql12.setResourceName("bq.EdorNoticePrintSql");
							mySql12.setSqlId(sqlid12); //ָ��ʹ��SQL��id
							mySql12.addSubPara(window.document.getElementsByName(trim("ManageCom"))[0].value);//ָ���������
							mySql12.addSubPara(window.document.getElementsByName(trim("NoticeNo"))[0].value);
							mySql12.addSubPara(window.document.getElementsByName(trim("OtherNo"))[0].value);
							mySql12.addSubPara(window.document.getElementsByName(trim("NoticeType"))[0].value);
							mySql12.addSubPara(window.document.getElementsByName(trim("AgentCode"))[0].value);
							mySql12.addSubPara(window.document.getElementsByName(trim("SaleChnl"))[0].value);
							mySql12.addSubPara(window.document.getElementsByName(trim("CustomerName"))[0].value);
							mySql12.addSubPara(comcode.substring(0,6));
							strSql = mySql12.getString();
 	                      
    	                  
   	                        break;

      //<!-- XinYQ added on 2006-03-06 : ����֪ͨ��(������ȡ��ʽ���) : BGN -->
   	  case "BQ92":    //    strSql = "select a.PrtSeq, "
//   	                             +        "a.StandbyFlag2, "
//   	                             +        "(select Name from LDCom where ComCode = a.ManageCom) "
//	                             +   "from LOPrtManager a "
//	                             +  "where 1 = 1 "
//	                             +    "and a.Code = 'BQ92' "
//	                             +    "and a.OtherNoType = '06' "
//	                             + getWherePart('a.PrtSeq', 'NoticeNo')
//	                             + getWherePart('a.ManageCom', 'ManageCom', 'like')
//	                             + getWherePart('a.StandbyFlag2', 'OtherNo')
//	                             +    "and a.ManageCom like '" + comcode.substring(0,6) + "%%' "    //��½����Ȩ�޿���
//	                             +    "and a.StateFlag = '0'";
						   
   	  						var sqlid13="EdorNoticePrintSql13";
							var mySql13=new SqlClass();
							mySql13.setResourceName("bq.EdorNoticePrintSql");
							mySql13.setSqlId(sqlid13); //ָ��ʹ��SQL��id
							mySql13.addSubPara(window.document.getElementsByName(trim("NoticeNo"))[0].value);//ָ���������
							mySql13.addSubPara(window.document.getElementsByName(trim("ManageCom"))[0].value);
							mySql13.addSubPara(window.document.getElementsByName(trim("OtherNo"))[0].value);
							mySql13.addSubPara(comcode.substring(0,6));
							strSql = mySql13.getString();
   	  
   	  						
   	                        break;
      //<!-- XinYQ added on 2006-03-06 : ����֪ͨ��(������ȡ��ʽ���) : END -->

      //<!-- XinYQ added on 2006-06-28 : �ո��ѷ�ʽ���֪ͨ�� : BGN -->
   	  case "BQ93":    //    strSql = "select a.PrtSeq, "
//   	                             +        "a.StandbyFlag2, "
//   	                             +        "(select Name from LDCom where ComCode = a.ManageCom) "
//	                             +   "from LOPrtManager a "
//	                             +  "where 1 = 1 "
//	                             +    "and a.Code = 'BQ93' "
//	                             +    "and a.OtherNoType = '06' "
//	                             + getWherePart('a.PrtSeq', 'NoticeNo')
//	                             + getWherePart('a.ManageCom', 'ManageCom', 'like')
//	                             + getWherePart('a.StandbyFlag2', 'OtherNo')
//	                             +    "and a.ManageCom like '" + comcode.substring(0,6) + "%%' "    //��½����Ȩ�޿���
//	                             +    "and a.StateFlag = '0'";
//   	  
						   	var sqlid14="EdorNoticePrintSql14";
							var mySql14=new SqlClass();
							mySql14.setResourceName("bq.EdorNoticePrintSql");
							mySql14.setSqlId(sqlid14); //ָ��ʹ��SQL��id
							mySql14.addSubPara(window.document.getElementsByName(trim("NoticeNo"))[0].value);//ָ���������
							mySql14.addSubPara(window.document.getElementsByName(trim("ManageCom"))[0].value);
							mySql14.addSubPara(window.document.getElementsByName(trim("OtherNo"))[0].value);
							mySql14.addSubPara(comcode.substring(0,6));
							strSql = mySql14.getString();
   	  
   	                        break;
      //<!-- XinYQ added on 2006-06-28 : �ո��ѷ�ʽ���֪ͨ�� : END -->

   	  //��˾��Լ֪ͨ��
   	  //�������룺contno�����Ͷ���Ͷ����
   	   case "BQ30":
//   	                      strSql = "select a.prtseq,a.otherno,"
//		 	                     + " (select codename from ldcode where codetype = 'station' and code = a.managecom ), "
//		 	                     + " b.appntname,"
//	                             + " (select name from laagent where agentcode = trim(a.agentcode)), "
//	                             + " b.cvalidate,"
//	                             + " '��ֹ'  "
//	                             + " from lccont b,loprtmanager a where 1 = 1 "
//	                             + getWherePart('a.ManageCom', 'ManageCom', 'like')
//	                             + getWherePart('a.prtseq', 'NoticeNo')
//	                             + getWherePart('a.OtherNo', 'OtherNo')
//	                             + getWherePart('a.Code', 'NoticeType')
//	                             + getWherePart('a.AgentCode', 'AgentCode')
//	                             + getWherePart('b.SaleChnl', 'SaleChnl')
//	                             + getWherePart('b.appntname', 'CustomerName')
//	                             + " and a.otherno = b.contno and b.appflag = '4' "
//	                             + " and a.ManageCom LIKE '" + comcode.substring(0,6) + "%%'" //��½����Ȩ�޿���
//	                             + " and a.StateFlag = '0' ";
   	                      
	   	                    var sqlid15="EdorNoticePrintSql15";
							var mySql15=new SqlClass();
							mySql15.setResourceName("bq.EdorNoticePrintSql");
							mySql15.setSqlId(sqlid15); //ָ��ʹ��SQL��id
							mySql15.addSubPara(window.document.getElementsByName(trim("ManageCom"))[0].value)//ָ���������
							mySql15.addSubPara(window.document.getElementsByName(trim("NoticeNo"))[0].value);
							mySql15.addSubPara(window.document.getElementsByName(trim("OtherNo"))[0].value);
							mySql15.addSubPara(window.document.getElementsByName(trim("NoticeType"))[0].value);
							mySql15.addSubPara(window.document.getElementsByName(trim("AgentCode"))[0].value);
							mySql15.addSubPara(window.document.getElementsByName(trim("SaleChnl"))[0].value);
							mySql15.addSubPara(window.document.getElementsByName(trim("CustomerName"))[0].value);
							mySql15.addSubPara(comcode.substring(0,6));
							strSql = mySql15.getString();
   	                      
   	                        break;
   	  case "BQ00":          // strSql = "select a.prtseq,a.otherno,"
//		 	                         + " (select codename from ldcode where codetype = 'station' and code = a.managecom ) "
//	                             + ",a.StandByflag2 from loprtmanager a where 1 = 1 "
//	                             + getWherePart('a.ManageCom', 'ManageCom', 'like')
//	                             + getWherePart('a.prtseq', 'NoticeNo')
//	                             + getWherePart('a.OtherNo', 'OtherNo')
//	                             + getWherePart('a.Code', 'NoticeType')
//	                             + " and a.ManageCom LIKE '" + comcode.substring(0,6) + "%%'" //��½����Ȩ�޿���
//	                             + " and a.StateFlag = '0' ";
   	  
						   	var sqlid16="EdorNoticePrintSql16";
							var mySql16=new SqlClass();
							mySql16.setResourceName("bq.EdorNoticePrintSql");
							mySql16.setSqlId(sqlid16); //ָ��ʹ��SQL��id
							mySql16.addSubPara(window.document.getElementsByName(trim("ManageCom"))[0].value);//ָ���������
							mySql16.addSubPara(window.document.getElementsByName(trim("NoticeNo"))[0].value);
							mySql16.addSubPara(window.document.getElementsByName(trim("OtherNo"))[0].value);
							mySql16.addSubPara(window.document.getElementsByName(trim("NoticeType"))[0].value);
							mySql16.addSubPara(comcode.substring(0,6));
							strSql = mySql16.getString();
  
	                        break;    
   	  case "BQ01":       //    strSql = "select a.prtseq,a.otherno,"
//		 	                       + " (select codename from ldcode where codetype = 'station' and code = a.managecom ) "
//	                             + ",a.StandByflag2 from loprtmanager a where 1 = 1 "
//	                             + getWherePart('a.ManageCom', 'ManageCom', 'like')
//	                             + getWherePart('a.prtseq', 'NoticeNo')
//	                             + getWherePart('a.OtherNo', 'OtherNo')
//	                             + getWherePart('a.Code', 'NoticeType')                             
//	                             + " and a.ManageCom LIKE '" + comcode.substring(0,6) + "%%'" //��½����Ȩ�޿���
//	                             + " and a.StateFlag = '0' ";	   
   	  
						  	var sqlid17="EdorNoticePrintSql17";
							var mySql17=new SqlClass();
							mySql17.setResourceName("bq.EdorNoticePrintSql");
							mySql17.setSqlId(sqlid17); //ָ��ʹ��SQL��id
							mySql17.addSubPara(window.document.getElementsByName(trim("ManageCom"))[0].value);//ָ���������
							mySql17.addSubPara(window.document.getElementsByName(trim("NoticeNo"))[0].value);
							mySql17.addSubPara(window.document.getElementsByName(trim("OtherNo"))[0].value);
							mySql17.addSubPara(window.document.getElementsByName(trim("NoticeType"))[0].value);
							mySql17.addSubPara(comcode.substring(0,6));
							strSql = mySql17.getString();
   	  
	                        break;        
	  case "BQ71":      //     strSql = "select a.prtseq,a.StandbyFlag1,a.otherno,"
//		 	                       + " (select codename from ldcode where codetype = 'station' and code = a.managecom ) "
//	                             + ",a.makedate from loprtmanager a where 1 = 1 "
//	                             + getWherePart('a.ManageCom', 'ManageCom', 'like')
//	                             + getWherePart('a.StandbyFlag1', 'ContNo')
//	                             + getWherePart('a.OtherNo', 'EdorAcceptNo')
//	                             + getWherePart('a.Code', 'NoticeType')                             
//	                             + " and a.ManageCom LIKE '" + comcode.substring(0,6) + "%%'" //��½����Ȩ�޿���
//	                             + " and a.StateFlag = '0' ";	
	  
						    var sqlid18="EdorNoticePrintSql18";
							var mySql18=new SqlClass();
							mySql18.setResourceName("bq.EdorNoticePrintSql");
							mySql18.setSqlId(sqlid18); //ָ��ʹ��SQL��id
							mySql18.addSubPara(window.document.getElementsByName(trim("ManageCom"))[0].value);//ָ���������
							mySql18.addSubPara(window.document.getElementsByName(trim("ContNo"))[0].value);
							mySql18.addSubPara(window.document.getElementsByName(trim("EdorAcceptNo"))[0].value);
							mySql18.addSubPara(window.document.getElementsByName(trim("NoticeType"))[0].value);
							mySql18.addSubPara(comcode.substring(0,6));
							strSql = mySql18.getString();
	  
	                        break;   
	  case "BQ72":        //   strSql = "select a.prtseq,a.otherno,a.StandbyFlag1,"
//		 	                       + " (select codename from ldcode where codetype = 'station' and code = a.managecom ) "
//	                             + ",a.makedate from loprtmanager a where 1 = 1 "
//	                             + getWherePart('a.ManageCom', 'ManageCom', 'like')
//	                             + getWherePart('a.StandbyFlag1', 'EdorAcceptNo')
//	                             + getWherePart('a.OtherNo', 'ContNo')
//	                             + getWherePart('a.Code', 'NoticeType')
//	                             + getWherePart('a.ReqOperator', 'AgentCode')                             
//	                             + " and a.ManageCom LIKE '" + comcode.substring(0,6) + "%%'" //��½����Ȩ�޿���
//	                             + " and a.StateFlag = '0' ";	 
	  
	  
						    var sqlid19="EdorNoticePrintSql19";
							var mySql19=new SqlClass();
							mySql19.setResourceName("bq.EdorNoticePrintSql");
							mySql19.setSqlId(sqlid19); //ָ��ʹ��SQL��id
							mySql19.addSubPara(window.document.getElementsByName(trim("ManageCom"))[0].value);//ָ���������
							mySql19.addSubPara(window.document.getElementsByName(trim("EdorAcceptNo"))[0].value);
							mySql19.addSubPara(window.document.getElementsByName(trim("ContNo"))[0].value);
							mySql19.addSubPara(window.document.getElementsByName(trim("NoticeType"))[0].value);
							mySql19.addSubPara(window.document.getElementsByName(trim("AgentCode"))[0].value);
							mySql19.addSubPara(comcode.substring(0,6));
							strSql = mySql19.getString();
	  
	                        break;                                               	                      
   	  default:       //     strSql = "select a.prtseq,a.otherno,"
//		 	                     + " (select codename from ldcode where codetype = 'station' and code = a.managecom ) "
//	                             + " from loprtmanager a where 1 = 1 "
//	                             + getWherePart('a.ManageCom', 'ManageCom', 'like')
//	                             + getWherePart('a.prtseq', 'NoticeNo')
//	                             + getWherePart('a.OtherNo', 'OtherNo')
//	                             + getWherePart('a.Code', 'NoticeType')
//	                             + " and a.ManageCom LIKE '" + comcode.substring(0,6) + "%%'" //��½����Ȩ�޿���
//	                             + " and a.StateFlag = '0' ";
   	  
   	  
					   	    var sqlid20="EdorNoticePrintSql20";
							var mySql20=new SqlClass();
							mySql20.setResourceName("bq.EdorNoticePrintSql");
							mySql20.setSqlId(sqlid20); //ָ��ʹ��SQL��id
							mySql20.addSubPara(window.document.getElementsByName(trim("ManageCom"))[0].value);//ָ���������
							mySql20.addSubPara(window.document.getElementsByName(trim("NoticeNo"))[0].value);
							mySql20.addSubPara(window.document.getElementsByName(trim("OtherNo"))[0].value);
							mySql20.addSubPara(window.document.getElementsByName(trim("NoticeType"))[0].value);
							mySql20.addSubPara(comcode.substring(0,6));
							strSql = mySql20.getString();
   	  
   	                        break;
   }
   if(trim(fm.StartDate.value) != "" && trim(fm.EndDate.value) != "")
   {
       strSql += getDateCondition(tNoticeType);//ͳ����ֹ�ڵ�����
   }
   return strSql;
}
function getDateCondition(tNoticeType)
{
   var strSql = "";
   switch(tNoticeType)
   {
   	   case "30":
   	                      strSql =" and exists (select 'x' from lobonuspol c where c.contno = a.otherno and c.fiscalyear=a.remark and c.agetdate is not null and c.sgetdate>='"+fm.StartDate.value+"' and c.sgetdate<='"+fm.EndDate.value+"')" ;//Ӧ���ֺ�Ļ����ȵ���Ч��Ӧ�ղ�ѯ
   	                      break;
   	   case "BQ17":
   	                      strSql = " And to_date(a.standbyflag6,'yyyy-mm-dd') Between '"+fm.StartDate.value+"' And '"+fm.EndDate.value+"'";
   	                      break; 
   	   case "BQ18":
   	                      strSql = " And to_date(a.standbyflag7,'yyyy-mm-dd') Between '"+fm.StartDate.value+"' And '"+fm.EndDate.value+"'";
   	                      break;   	                      	                      
   	   case "BQ21":
   	   case "BQ22":
   	   case "BQ38":
   	   case "BQ39":
   	   case "BQ29":
   	   case "42":
   	                      strSql = " And to_date(a.StandByflag1,'yyyy-mm-dd') Between '"+fm.StartDate.value+"' And '"+fm.EndDate.value+"'";
   	                      break;
    	case "BQ00":
   	                      strSql = " And to_date(a.StandByflag2,'yyyy-mm-dd') Between '"+fm.StartDate.value+"' And '"+fm.EndDate.value+"'";
   	                      break;  	                      
   	  case "BQ01":
   	                      strSql = " And to_date(a.StandByflag2,'yyyy-mm-dd') Between '"+fm.StartDate.value+"' And '"+fm.EndDate.value+"'";
   	                      break;
   	  default:            
   	                      strSql = " And a.makedate Between '"+fm.StartDate.value+"' And '"+fm.EndDate.value+"'";
   	                      break;
   	
   }
   return strSql;
}
//��ʼ��������������ȡ��λ
function initManageCom()
{
	if(comcode.substring(0,6) !=null && comcode.substring(0,6) != "")
	{
    	var i,j,m,n;
	    var returnstr;
	    var tTurnPage = new turnPageClass();
//	    var strSQL = "select comcode,name from ldcom where comcode like '"+comcode.substring(0,6)+"%%' order by comcode";
	    
	    var sqlid21="EdorNoticePrintSql21";
		var mySql21=new SqlClass();
		mySql21.setResourceName("bq.EdorNoticePrintSql");
		mySql21.setSqlId(sqlid21); //ָ��ʹ��SQL��id
		mySql21.addSubPara(comcode.substring(0,6))
		var strSQL=mySql21.getString();
	    
	    tTurnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 1, 1);
        if (tTurnPage.strQueryResult == "")
        {
          return "";
        }
        tTurnPage.arrDataCacheSet = decodeEasyQueryResult(tTurnPage.strQueryResult);
        var returnstr = "";
        var n = tTurnPage.arrDataCacheSet.length;
        if (n > 0)
        {
        	for( i = 0;i < n; i++)
        	{
  	        	m = tTurnPage.arrDataCacheSet[i].length;
        		if (m > 0)
  		        {
  		        	for( j = 0; j< m; j++)
  			        {
  		 		        if (i == 0 && j == 0)
  			        	{
  				        	returnstr = "0|^"+tTurnPage.arrDataCacheSet[i][j];
  			        	}
  			        	if (i == 0 && j > 0)
  			        	{
  					        returnstr = returnstr + "|" + tTurnPage.arrDataCacheSet[i][j];
  				        }
  			         	if (i > 0 && j == 0)
  				        {
  					        returnstr = returnstr+"^"+tTurnPage.arrDataCacheSet[i][j];
  				        }
  			        	if (i > 0 && j > 0)
  				        {
  					        returnstr = returnstr+"|"+tTurnPage.arrDataCacheSet[i][j];
  				        }
     		        }
  		        }
  	            else
  	            {
  		          	alert("��ѯʧ��!!");
  			        return "";
  		        }
             }
         }
         else
         {
	         alert("��ѯʧ��!");
	         return "";
         }
         fm.ManageCom.CodeData = returnstr;
         return returnstr;
	}

}
