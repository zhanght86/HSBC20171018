var showInfo;
 var turnPage = new turnPageClass();
 var turnPage1 = new turnPageClass();
 var mAllType = '2';
//�Ƿ�Ϊ����
function isdigit(c){
  return(c>='0'&&c<='9');
}

function initEdorType(cObj)
{
	mEdorType = " #1# and length(trim(ComCode))=4 and comcode not like #8699%%# ";
	showCodeList('comcode',[cObj],[0],null,mEdorType,"1",1);
}

function actionKeyUp(cObj)
{	
	mEdorType = " #1# and length(trim(ComCode))=4 and comcode not like #8699%%# ";
	showCodeListKey('comcode',[cObj],[0],null,mEdorType,"1",1);
}

function queryData(ttype)
{
	 initNoBonusRiskGrid();  
         initBonusRiskGrid();
	if(trim(document.all('BonusCalYear').value)=='')
	{
		alert('������������������');
		document.all('BonusCalYear').focus();
		return false;
	}
	mAllType = ttype;
	if(ttype=='0')
	{
	       var sqlid827144842="DSHomeContSql827144842";
var mySql827144842=new SqlClass();
mySql827144842.setResourceName("get.BonusRiskPreInputSql");//ָ��ʹ�õ�properties�ļ���
mySql827144842.setSqlId(sqlid827144842);//ָ��ʹ�õ�Sql��id
mySql827144842.addSubPara(document.all('BonusCalYear').value);//ָ������Ĳ���
mySql827144842.addSubPara(fm.BonusCalRisk.value);//ָ������Ĳ���
var strSQL=mySql827144842.getString();

	       
//	       var strSQL = " select riskcode,riskname from lmriskapp a where bonusflag = '1' "
//                          + " and not exists (select '1' from LoBonusRiskRem where fiscalyear='"+document.all('BonusCalYear').value+"' "
//                          + " and riskcode=a.riskcode and state='0') "
//                          +getWherePart('a.riskcode','BonusCalRisk')
//                          + " order by riskcode ";
                 
        	//initBonusRiskGrid();
                turnPage1.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
		//alert(strSQL);
		if (!turnPage1.strQueryResult) 
		{
			alert("��ѯ�����ݣ�");
			return false;
		}
		
		//��ѯ�ɹ������ַ��������ض�ά����
		turnPage1.arrDataCacheSet = decodeEasyQueryResult(turnPage1.strQueryResult,0,0,turnPage1);
		//���ó�ʼ������MULTILINE����VarGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
		turnPage1.pageDisplayGrid = BonusRiskGrid;
		//����SQL���
		turnPage1.strQuerySql = strSQL;
		//���ò�ѯ��ʼλ��
		turnPage1.pageIndex = 0;
		//�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
		var tArr = new Array();
                tArr=turnPage1.getData(turnPage1.arrDataCacheSet,turnPage1.pageIndex,MAXSCREENLINES,turnPage1);
		//����MULTILINE������ʾ��ѯ���
		displayMultiline(tArr, turnPage1.pageDisplayGrid,turnPage1);
 
          }
        else if(ttype=='1')
	{  
  	      var sqlid827145049="DSHomeContSql827145049";
					var mySql827145049=new SqlClass();
					mySql827145049.setResourceName("get.BonusRiskPreInputSql");//ָ��ʹ�õ�properties�ļ���
					mySql827145049.setSqlId(sqlid827145049);//ָ��ʹ�õ�Sql��id
					mySql827145049.addSubPara(fm.BonusCalRisk.value);//ָ������Ĳ���
					mySql827145049.addSubPara(fm.BonusCalYear.value);//ָ������Ĳ���
					var strSQL=mySql827145049.getString();
  	       
//  	       var strSQL = " select riskcode,(select riskname from lmriskapp where riskcode=a.riskcode),decode(state,'0','δ����','1','�Ѹ���') "
//  	                  + " from LoBonusRiskRem a where state='0'"
//                          +getWherePart('a.riskcode','BonusCalRisk')
//                          +getWherePart('a.fiscalyear','BonusCalYear')
//                          + " order by riskcode ";
                 
        	//initNoBonusRiskGrid();
                turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
		//alert(strSQL);
		if (!turnPage.strQueryResult) 
		{
			alert("��ѯ�����ݣ�");
			return false;
		}
		
		//��ѯ�ɹ������ַ��������ض�ά����
		turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult,0,0,turnPage);
		//���ó�ʼ������MULTILINE����VarGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
		turnPage.pageDisplayGrid = NoBonusRiskGrid;
		//����SQL���
		turnPage.strQuerySql = strSQL;
		//���ò�ѯ��ʼλ��
		turnPage.pageIndex = 0;
		//�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
		var tArr = new Array();
    tArr=turnPage.getData(turnPage.arrDataCacheSet,turnPage.pageIndex,MAXSCREENLINES,turnPage);
		//����MULTILINE������ʾ��ѯ���
		displayMultiline(tArr, turnPage.pageDisplayGrid,turnPage);
	}
        	
}
function submitForm()
{
	
if(verifyInput()) 
  {	  	
  	var i = 0;
	  var showStr="����׼����ӡ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
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
}

function addData()
{
	
	if(trim(document.all('BonusCalYear').value)=='')
	{
		alert('������������������');
		document.all('BonusCalYear').focus();
		return false;
	}
	document.all('hideaction').value='INSERT';
	if(!submitForm())
	{
		return false;
	}
	document.all('hideaction').value='';
}

function delData()
{
	var tSel = NoBonusRiskGrid.getSelNo();
	//alert('tSel:'+tSel);
	if(tSel==0)
	{
		alert('��ѡ��һ�м�¼����ɾ������');
		return false;
	}
        
        if (!confirm('ȷ��ɾ������?'))
	{
		return false;
	}
	
	document.all('hideaction').value='DELETE';
	if(!submitForm())
	{
		return false;
	}
	document.all('hideaction').value='';
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
	document.all('hideaction').value='';
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
    parent.fraInterface.document.all('compute').disabled = false;
  }
  else
  { 
    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;  
  	parent.fraInterface.document.all('compute').disabled = false;
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=350;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  }
   initNoBonusRiskGrid();  
   initBonusRiskGrid(); 
   //ȥ��ѡ���
   //�ر������㷨
   if (FlagStr != "Fail" )
   {
      document.all('checkbox1').checked = false;
      document.all('divOtherGrid').style.display="none";
   }
  queryData(mAllType);
  mAllType = '2';
}




function onReportSelected(parm1,parm2)
{
	document.all('CalManageCom').value=AgentGrid.getRowColData(AgentGrid.getSelNo() - 1, 1);
        document.all('IndexCalNo').value=AgentGrid.getRowColData(AgentGrid.getSelNo() - 1, 3);
	//fm.
	//alert(AgentGrid.getRowColData(AgentGrid.getSelNo() - 1, 6));
	//var tSel = AgentGrid.getSelNo();
	//alert('tSel:'+tSel);
	//if(document.all('IndexCalNo').value=='')
	//{
	//	alert('���������������');
	//}
	//else
	//{
	/*
		document.all('HideReportCode').value = AgentGrid.getRowColData(AgentGrid.getSelNo() - 1, 1);
		var strSQL = " select count(*) from LAAgentAutoReportLog "
		           + " where logtype='01' and indexcalno='"+document.all('IndexCalNo').value+"'"
		           + " and reportcode='"+document.all('HideReportCode').value+"'";
		var strQueryResult = easyQueryVer3(strSQL, 1, 1, 1);
		if(strQueryResult)
		{
			var tArr = new Array();
                        tArr = decodeEasyQueryResult(strQueryResult);
                        if(tArr[0][0]<=0)
                        {
                        	AgentGrid.setRowColData(AgentGrid.getSelNo() - 1,3,'δ����');
                        }
                        else
                        {
		
	                   var sql = "select decode(state,'00','������','01','�Ѽ���','�������') from LAAgentAutoReportLog"
	                           + " where logtype='01' and indexcalno='"+document.all('IndexCalNo').value+"'"
	                           + " and reportcode='"+document.all('HideReportCode').value+"'";
	                   strQueryResult = easyQueryVer3(sql, 1, 1, 1);  
	                   var tArr1 = new Array();
                               tArr1 = decodeEasyQueryResult(strQueryResult);
                               AgentGrid.setRowColData(AgentGrid.getSelNo() - 1,3,tArr1[0][0]);
	        
	                }
                }  
        }      
        */
}

//�����㷨���ݱ���
function saveOtherData()
{
	if(trim(document.all('BonusCalYear').value)=='')
	{
		alert('������������������');
		document.all('BonusCalYear').focus();
		return false;
	}
	
	document.all('hideaction').value='OTHERSAVE';
	if(!submitForm())
	{
		return false;
	}
	document.all('hideaction').value='';
}


function setDefaultClass()
{
	if(trim(document.all('BonusCalYear').value)=='')
	{
		alert('������������������');
		document.all('checkbox1').checked = false;
		document.all('BonusCalYear').focus();
		return false;
	}
	mAllType = '2';
	if(document.all('checkbox1').checked == true)
	{
	  document.all('divOtherGrid').style.display="";
	  
	  var sqlid830154605="DSHomeContSql830154605";
var mySql830154605=new SqlClass();
mySql830154605.setResourceName("get.BonusRiskPreInputSql");//ָ��ʹ�õ�properties�ļ���
mySql830154605.setSqlId(sqlid830154605);//ָ��ʹ�õ�Sql��id
mySql830154605.addSubPara(document.all('BonusCalYear').value);//ָ������Ĳ���
mySql830154605.addSubPara(document.all('BonusCalYear').value);//ָ������Ĳ���
mySql830154605.addSubPara(document.all('BonusCalYear').value);//ָ������Ĳ���
document.all('WorkDetail').value=mySql830154605.getString();

	  
	  
//	  document.all('WorkDetail').value = "select * from lcpol a where grppolno = \'00000000000000000000\' "
//	                             + " and appflag='1' and cvalidate <= '"+document.all('BonusCalYear').value+ "'||'-12-31' "
//	                             + " and exists (select riskcode from lmriskapp where bonusflag = '1' and riskcode=a.riskcode) "
//	                             + " and not exists (select '1' from LoBonusRiskRem where FiscalYear='"+document.all('BonusCalYear').value+"'"
//	                             + " and state='00' and riskcode = a.riskcode) "
//                                     + " and not exists (select polno from LOBonusPol "
//                                     + " where FiscalYear="+document.all('BonusCalYear').value+" and polno = a.polno and GroupID='1')";

          var sqlid830154901="DSHomeContSql830154901";
var mySql830154901=new SqlClass();
mySql830154901.setResourceName("get.BonusRiskPreInputSql");//ָ��ʹ�õ�properties�ļ���
mySql830154901.setSqlId(sqlid830154901);//ָ��ʹ�õ�Sql��id
mySql830154901.addSubPara(document.all('BonusCalYear').value);//ָ������Ĳ���
var tSQL=mySql830154901.getString();
          
//          var tSQL = " select trim(BonusCondition) from lobonusmain where FiscalYear="+document.all('BonusCalYear').value+" and GroupID='1'";
          //prompt('1',tSQL);
          var strQueryResult1 = easyQueryVer3(tSQL,1,1,1);
          if(!strQueryResult1)
          {
          	document.all('OtherCondition').value='';
          }
          else 
     	  {
     	    var arr = decodeEasyQueryResult(strQueryResult1);
     	    document.all('OtherCondition').value=arr[0][0];
     	  }
        }
        else
	{
	   document.all('divOtherGrid').style.display="none";
	}	
}


function clearAllShow()
{  
   initNoBonusRiskGrid();  
   initBonusRiskGrid();
   document.all('checkbox1').checked = false;
   document.all('divOtherGrid').style.display="none";
}