//�������ƣ�LLSecondUW.js
//�����ܣ���ͬ���˹��˱�------����˱�
//�������ڣ�2002-06-19 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����
//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="1";
var turnPage = new turnPageClass();
var mySql = new SqlClass();
// ��ʼ����ť------------�ñ��������µ����к�ͬ ,���г� �Ƿ��뵱ǰ�ⰸ���
function initLCContGridQuery()
{
	var tInsuredNo = fm.InsuredNo.value;
	var tCaseNo = fm.CaseNo.value;
	
	// ���� �������˺��롱 ��ѯ ����������������� ������ ��ת����ʱΪ ��������⣩,2009-01-20 zhangzheng ����ֻ�ܲ�ѯ������
	//var ssql="select t.name from lcinsured t where t.insuredno='"+tInsuredNo+"'";
	mySql = new SqlClass();
	mySql.setResourceName("claim.LLSecondUWInputSql");
	mySql.setSqlId("LLSecondUWSql1");
	mySql.addSubPara(tInsuredNo); 
	var arr = easyExecSql(mySql.getString());
	document.all('InsuredName').value =arr[0][0];	
	/*var strSQL =" select a.contno,a.appntno,a.appntname,a.managecom,a.proposalcontno,a.prtno ,'0-���' as reflag"
 			+" from lccont a,lcinsured b where 1=1 and a.appflag='1' and a.contno = b.contno"	
 			+" and a.contno in (select c.contno from llclaimpolicy c where c.clmno='"+tCaseNo+"' )"
 			+" and a.grpcontno='00000000000000000000'"
 			+" and b.insuredno = '"+tInsuredNo+"' "
 			+" union "
 			+" select a.contno,a.appntno,a.appntname,a.managecom,a.proposalcontno,a.prtno,'1-�����' as reflag"
 			+" from lccont a,lcinsured b where 1=1 and a.appflag='1' and a.contno = b.contno"	
 			+" and a.contno not in (select c.contno from llclaimpolicy c where c.clmno='"+tCaseNo+"' )"
 			+" and a.grpcontno='00000000000000000000'"
 			+" and b.insuredno = '"+tInsuredNo+"' "
 			+" order by reflag "  ;*/
 	mySql = new SqlClass();
	mySql.setResourceName("claim.LLSecondUWInputSql");
	mySql.setSqlId("LLSecondUWSql2");
	mySql.addSubPara(tCaseNo); 
	mySql.addSubPara(tInsuredNo); 
	mySql.addSubPara(tCaseNo); 
	mySql.addSubPara(tInsuredNo); 
	//prompt("������˳�ʼ����ѯ",strSQL);
	var arrResult = easyExecSql(mySql.getString());
    if (arrResult)
    {
    	displayMultiline(arrResult,LCContGrid);
    }
	return true;
}


//[ȷ��]��ť-------������� ����׼������
function LLSeUWSaveClick()
{
	  //����
    /*var strSql4 = "select InEffectFlag from LLCUWBatch where "
                + " caseno = '" + document.all('CaseNo').value + "'";*/
    mySql = new SqlClass();
	mySql.setResourceName("claim.LLSecondUWInputSql");
	mySql.setSqlId("LLSecondUWSql3");
	mySql.addSubPara(document.all('CaseNo').value);
	var tState = easyExecSql(mySql.getString());

    if (tState)
    {

        for (var i = 0; i < tState.length; i++)
        {

            if (tState[i] == '0')
            {
                alert("����Ķ��˻�û�����Ƿ���Ч����,�����ٴη������!");
                return false;
            }
        }

        //����Ƿ���ڶ��˵ļӷ���Ϣ�����б������(by zl 2006-1-6 11:40)
        if (!checkLjspay(document.all('CaseNo').value))
        {
            return false;//�˴����ù��з���
        }
    }

    
    //����Ƿ���д����()
    var row = LCContGrid.mulLineCount;
    var i=0;
    for(var m=0;m<row;m++ )
    {
    	if(LCContGrid.getChkNo(m))
    	{
    		i=i+1;
    		// ����Ƿ������д����е�����
    	}
    }
    if(i==0)
	{
		alert("��û��ѡ���κκ�ͬ��");
		return;
	}
	if(trim(fm.Note.value)=="") 
	{
		alert("����д������Ŀǰ����״�����ܣ�");
		return;
	}

	
	//var sysdatearr=easyExecSql("select to_date(sysdate) from dual");//ȡ��������ʱ����Ϊϵͳ��ǰ����
	mySql = new SqlClass();
	mySql.setResourceName("claim.LLSecondUWInputSql");
	mySql.setSqlId("LLSecondUWSql4");
	mySql.addSubPara("1");
	var sysdatearr=easyExecSql(mySql.getString());
	fm.CurrentDate.value=sysdatearr[0][0];

	
	fm.action="LLSecondUWSave.jsp";
	submitForm(); //�ύ
}

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
	
  document.getElementById("fm").submit(); //�ύ
}


//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
  showInfo.close();
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
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
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  }
}


//[������ѯ]��ť
function showPolDetail()
{
	//����Ƿ���д����()
    var row = LCContGrid.mulLineCount;
    var i=0;
    for(var m=0;m<row;m++ )
    {
    	if(LCContGrid.getChkNo(m))
    	{
    		i=i+1;
    	}
    }
    if(i==0)
	{
		alert("��û��ѡ���κκ�ͬ��");
		return;
	}
	if(i>1)
	{
		alert("��ֻѡ��һ����ͬ��¼��");
		return;
	}
	var tContNo = LCContGrid.getRowColData(i-1,1);//��������
	var tPrtNo  = LCContGrid.getRowColData(i-1,6);//ӡˢ����
	var strUrl  = "../sys/PolDetailQueryMain.jsp?PrtNo="+tPrtNo+"&ContNo="+tContNo+"&IsCancelPolFlag=0";
	//window.open(strUrl, "������ѯ", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1");  
	window.open(strUrl, "","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");  		  
} 

//[��ȫ��ϸ]��ť
function showPolBqEdor()
{
	//����Ƿ���д����()
    var row = LCContGrid.mulLineCount;
    var i=0;
    for(var m=0;m<row;m++ )
    {
    	if(LCContGrid.getChkNo(m))
    	{
    		i=i+1;
    	}
    }
    if(i==0)
	{
		alert("��û��ѡ���κκ�ͬ��");
		return;
	}
	if(i>1)
	{
		alert("��ֻѡ��һ����ͬ��¼��");
		return;
	}
	var tContNo=LCContGrid.getRowColData(i-1,1);//��ͬ����
	var strUrl="../sys/PolBqEdorMain.jsp?ContNo="+tContNo+"&flag=0&IsCancelPolFlag=0";
	//window.open(strUrl, "��ȫ��ϸ", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1");  	
	window.open(strUrl, "","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");  	  
} 







