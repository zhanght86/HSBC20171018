//�������ƣ�LLSecondUWRisk.js
//�����ܣ����ֺ˱���Ϣ����-------���ⲿ��
//�������ڣ�2005-01-06 11:10:36
//������  ��HYQ
//���¼�¼��  ������ yuejw   ��������     ����ԭ��/����

var showInfo;
var turnPage    = new turnPageClass();
var turnsubPage = new turnPageClass();
var turnPage1   = new turnPageClass();
var temp        = new Array();
var str_sql = "",sql_id = "", my_sql = "";   //�󶨱����������ݿ�
///***************��ѯ�˱�������������Ϣ�б�*********************/
function queryLLRiskGridInfo()
{
	var tContNo = fm.ContNo.value;//Modify by zhaorx 2006-09-23
//   	var tSql = " select t.polno,t.contno,t.riskcode,a.riskname,t.prem,t.amnt,t.cvalidate,t.enddate,t.payintv,t.payyears ,t.proposalno,a.subriskflag "
//   			+ " from lcpol t,lmriskapp a where 1=1 and t.riskcode = a.riskcode and t.appflag='1'"
//			+ " and t.contno='"+tContNo+"' and t.polno = t.mainpolno union "
//			+ " select t.polno,t.contno,t.riskcode,a.riskname,t.prem,t.amnt,t.cvalidate,t.enddate,t.payintv,t.payyears ,t.proposalno,a.subriskflag "
//   			+ " from lcpol t,lmriskapp a where 1=1 and t.riskcode = a.riskcode and t.appflag='1'"
//			+ " and t.contno='"+tContNo+"' and t.polno <> t.mainpolno order by 12";
   	sql_id = "LLSecondUWRiskSql0";
	my_sql = new SqlClass();
	my_sql.setResourceName("claim.LLSecondUWRiskSql"); //ָ��ʹ�õ�properties�ļ���
	my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
	my_sql.addSubPara(tContNo);//ָ������Ĳ���
	my_sql.addSubPara(tContNo);//ָ������Ĳ���
	str_sql = my_sql.getString();
	turnPage.queryModal(str_sql,LLRiskGrid)
	return true;  					
}

//������Ϣ�б� ������Ӧ
function LLRiskGridClick()
{
	cancelClick();
	var tSelNo =LLRiskGrid.getSelNo()-1;
	var tPolNo=LLRiskGrid.getRowColData(tSelNo,1);
	fm.RiskCode.value = LLRiskGrid.getRowColData(tSelNo,3);
	if(tSelNo != 0)
	{
	      fm.addbutton.disabled  = true;
	      fm.specbutton.disabled = true;
    }
    else
	{
		  fm.addbutton.disabled  = false;
	      fm.specbutton.disabled = false;
	}
//	var strSQL=" select tt.caseno,tt.batno,tt.contno,tt.polno,tt.uwno"
//		+" ,tt.passflag,(select codename from ldcode jj where jj.codetype='uwstate' and code=tt.passflag)"
//		+" ,tt.uwidea,tt.makedate,tt.maketime "
//		+" from lluwsub tt where 1=1 and tt.caseno='"+fm.CaseNo.value+"'"
//		+" and tt.polno='"+tPolNo+"' order by tt.batno desc ,tt.uwno desc ";
	sql_id = "LLSecondUWRiskSql1";
	my_sql = new SqlClass();
	my_sql.setResourceName("claim.LLSecondUWRiskSql"); //ָ��ʹ�õ�properties�ļ���
	my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
	my_sql.addSubPara(fm.CaseNo.value);//ָ������Ĳ���
	my_sql.addSubPara(tPolNo);//ָ������Ĳ���
	str_sql = my_sql.getString();
	turnsubPage.queryModal(str_sql,LLUWSubGrid)
	
	return true;  
}


/********************  �ӷѳб�***********************/ 

/**=========================================================================
    �޸�״̬���õ��������ֺ����������������ȷ���Ƿ������գ���������գ�
              ��ô�Ϳ���ִ�мӷѳб�������������գ���ô�Ͳ���ִ�мӷѳб���
    �޸�ԭ�򣺸����ղ��ܽ��С��ӷѳб�������
    �� �� �ˣ������
    �޸����ڣ�2005.11.03
   =========================================================================
**/
function showAdd()
{
    var tSelNo  = LLRiskGrid.getSelNo();
    if(tSelNo < 1)
    {
        alert("��ѡ��һ�м�¼��");
        return 1;
    }
    tSelNo = tSelNo - 1 ;
	var tClmNo=fm.CaseNo.value;
	var tContNo=fm.ContNo.value;
    var tBatNo=fm.BatNo.value;     
	var tInsuredNo=fm.InsuredNo.value; 
	var tQueryFlag=0;//0�ǲ�ѯ��1��ѯ 
    var tUrl = "../claimnb/LLUWAddFeeMain.jsp?ClmNo="+tClmNo+"&ContNo="+tContNo+"&BatNo="+tBatNo+"&InsuredNo="+tInsuredNo+"&QueryFlag="+tQueryFlag;
    window.open(tUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
   
}

/***********************  ��Լ�б�********************* */ 


/**=========================================================================
    �޸�״̬���õ��������ֺ����������������ȷ���Ƿ������գ���������գ�
              ��ô�Ϳ���ִ����Լ�б�������������գ���ô�Ͳ���ִ����Լ�б���
    �޸�ԭ�򣺸����ղ��ܽ��С���Լ�б�������
    �� �� �ˣ������
    �޸����ڣ�2005.11.03
   =========================================================================
**/
function showSpec()
{
  	var tSelNo  = LLRiskGrid.getSelNo();
    if(tSelNo < 1)
    {
        alert("��ѡ��һ�м�¼��");
        return 1;
    }
    tSelNo = tSelNo - 1 ;
	var tProposalNo = LLRiskGrid.getRowColData(tSelNo,11);
	var tContNo = fm.ContNo.value;
	var tBatNo  = fm.BatNo.value;
	if (tContNo != "" )
	{ 	
        var tClmNo=fm.CaseNo.value;
	    var tContNo=fm.ContNo.value;
	    var tBatNo=fm.BatNo.value; 
        var tInsuredNo=fm.InsuredNo.value;         
	    var tQueryFlag=0;//0�ǲ�ѯ��1��ѯ     
        var tUrl = "../claimnb/LLUWSpecMain.jsp?ClmNo="+tClmNo+"&ContNo="+tContNo+"&BatNo="+tBatNo+"&InsuredNo="+tInsuredNo+"&QueryFlag="+tQueryFlag+"&ProposalNo="+tProposalNo+"&BatNo="+tBatNo;
        window.open(tUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	 }
	 else
	 {
		 alert("û�еõ���ͬ��");
	 }
}

/***************[ȡ����ť]*********************/
function cancelClick()
{
	fm.UWState.value="";
	fm.UWStateName.value="";
	fm.UWIdea.value="";
}

///***************[ȷ����ť]*********************/
function uwSaveClick()
{
	var tSelNo =LLRiskGrid.getSelNo()-1;
	if(tSelNo < 0)
	{
		alert("��ѡ�񱣵����֣�");
		return;
	}
	if(fm.UWState.value=="")
	{
		alert("��ѡ�����ֺ˱�����!");
		return;
	}
	document.all('PolNo').value = LLRiskGrid.getRowColData(tSelNo,1);	//��������
	fm.action = "./LLSecondUWRiskChk.jsp";
 	submitForm(); //�ύ
}

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
  fm.submit(); //�ύ
}


/******************�ύ�����,���������ݷ��غ�ִ�еĲ���******************************/ 
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
  	//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  }
  LLRiskGridClick();
  cancelClick();
}

/*********************************************************************
 *  �޸�ԭ�򣺲�ѯ�˱���������Ϣ
 *  �޸���  �������
 *  �޸����ڣ�2005/12/21/15:20
 *********************************************************************
 */
function queryInsuredInfo()
{
	var arrReturn1 = new Array();
	var arrReturn2 = new Array();
	var arrReturn3 = new Array();
	var tContNo    = fm.ContNo.value;
	var tInsuredNo = fm.InsuredNo.value; 
//	var tSql1 = "select insuredno,name,sex,birthday,NativePlace,RelationToMainInsured,RelationToAppnt,OccupationCode,OccupationType from LCInsured t where 1=1"
//							+ " and contno='"+tContNo+"'"
//							+ " and insuredno ='"+tInsuredNo+"'";
	sql_id = "LLSecondUWRiskSql2";
	my_sql = new SqlClass();
	my_sql.setResourceName("claim.LLSecondUWRiskSql"); //ָ��ʹ�õ�properties�ļ���
	my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
	my_sql.addSubPara(tContNo);//ָ������Ĳ���
	my_sql.addSubPara(tInsuredNo);//ָ������Ĳ���
	str_sql = my_sql.getString();
	turnPage1.strQueryResult  = easyQueryVer3(str_sql, 1, 0, 1);  
  
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage1.strQueryResult)
  {
    alert("��������Ϣ��ѯʧ��!");
    return "";
  }
 
  //��ѯ�ɹ������ַ��������ض�ά����
  arrResult1 = decodeEasyQueryResult(turnPage1.strQueryResult);	
  
  
  document.all('InsuredNo1').value = arrResult1[0][0];
  document.all('Name').value = arrResult1[0][1];
  
  document.all('Sex').value = arrResult1[0][2];
  showOneCodeName('sex','Sex','SexName');
 
  document.all('InsuredAppAge').value = calAge(arrResult1[0][3]);
  
  document.all('NativePlace').value = arrResult1[0][4];
  showOneCodeName('nativeplace','NativePlace','NativePlaceName');
  
  document.all('RelationToMainInsured').value = arrResult1[0][5];
  showOneCodeName('relation','RelationToMainInsured','RelationToMainInsuredName');
  
  document.all('RelationToAppnt').value = arrResult1[0][6];
  showOneCodeName('relationtoappnt','RelationToAppnt','RelationToAppntName');
  //��ѯְҵ����
  var sql = "select occupationname from ldoccupation where occupationcode = '"+arrResult1[0][7]+"'";
  var result = easyExecSql(sql);
  if(result == "" || result == null)
  {
      document.all('OccupationCode').value = "";
  }
  else
  {
  	  document.all('OccupationCode').value = result[0][0];
  }
  	
  document.all('OccupationType').value = arrResult1[0][8];
  showOneCodeName('occupationtype','OccupationType','OccupationTypeName');
  
  return true;
 
}
