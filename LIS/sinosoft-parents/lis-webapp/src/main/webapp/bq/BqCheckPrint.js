
//�������ƣ�EdorNoticePrint.js
//�����ܣ���ȫ֪ͨ�����ߴ�ӡ����̨
//�������ڣ�2005-08-02 16:20:22
//������  ��liurx
//���¼�¼��  ������    ��������      ����ԭ��/���� 


//���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var turnPage = new turnPageClass();



//�ύ�����水ť��Ӧ����
function printCheck()
{
  //����У��
  if (!verifyInput2())
  {
     return false;
  }
   
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
  
  var tSel = NoticeGrid.getSelNo();

  if( tSel == 0 || tSel == null )
  {
  	    showInfo.close();	
		alert( "����ѡ��һ����¼!" );
  }
  else
  {
		if(!verifyCheck())
		{
			return;
		}
		PrtSeq = NoticeGrid.getRowColData(tSel-1,1);
		showInfo.close();	
		if(PrtSeq != null && PrtSeq != "")
		{
			fm.PrtSeq.value = PrtSeq;
			fm.SelEdorAcceptNo.value = NoticeGrid.getRowColData(tSel-1,2);
	    	fm.fmtransact.value = "PRINT";
	    	fm.target = "f1print";		
		    fm.submit();
		}	
  }
}


function verifyCheck()
{	
	var StrSQL = "SELECT * FROM LZCard WHERE 1=1"
	           + " and CertifyCode = '" + fm.CertifyCode.value + "'"
	           + " and StateFlag = '0'"
	           + " and ReceiveCom = 'A" + comcode + "'"
	           + " and StartNo <= '" + fm.CheckNo.value + "'"
	           + " and EndNo >= '" + fm.CheckNo.value + "'";
	var tResult = easyExecSql(StrSQL);
	if(tResult == null)
	{
		showInfo.close();
		alert("������վݺ���Ч��");
		return false;
	}
	return true;
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
	var iHeight=350;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  }
}

// ��ѯ֪ͨ�������б�
function easyQueryClick()
{ 
    var strSql = "";
    var invoiceType = document.all("InvoiceType").value;
    document.all("ChequeType").value = invoiceType;    //�������ֶθ�ֵΪѡ��Ʊ������
    

	
    if(invoiceType == null || invoiceType == "BQ35"){//Ĭ�ϴ���˷�Ʊ
    	if(_DBT==_DBO){
//    		 strSql = "select a.prtseq,a.standbyflag1,a.standbyflag2,"
//	 	         + " (select edorname from lmedoritem where (edorcode) = trim(a.standbyflag3) and appobj in('I','B') and rownum = 1),"
//	 	         + " (select name from ldcom where comcode = a.managecom),"
//	 	         + " (select name from laagent where agentcode =trim(a.agentcode)) "
//	 	         + " from loprtmanager a where 1 = 1 "
//                 + getWherePart('a.ManageCom', 'ManageCom', 'like')
//                 + getWherePart('a.standbyflag1', 'EdorAcceptNo')
//                 + getWherePart('a.standbyflag2', 'ContNo') 
//                 + getWherePart('a.AgentCode', 'AgentCode') 
//                 + " and (a.standbyflag1) in(select (incomeno) from ljapay where incometype = '10')"
//                 + " and a.ManageCom like '" + comcode.substring(1,4) + "%'" //��½����Ȩ�޿��� 
//                 + " and a.StateFlag = '0' and a.code = 'BQ35'";
    		 
    		 var  ManageCom1 = window.document.getElementsByName(trim("ManageCom"))[0].value;
    		 var  EdorAcceptNo1 = window.document.getElementsByName(trim("EdorAcceptNo"))[0].value;
    		 var  ContNo1 = window.document.getElementsByName(trim("ContNo"))[0].value; 
    		 var  AgentCode1 = window.document.getElementsByName(trim("AgentCode"))[0].value;
    		 var  comcode1 = comcode.substring(1,4);
    		 var sqlid1="BqCheckPrintSql1";
    		 var mySql1=new SqlClass();
    		 mySql1.setResourceName("bq.BqCheckPrintSql");
    		 mySql1.setSqlId(sqlid1);//ָ��ʹ��SQL��id
    		 mySql1.addSubPara(ManageCom1);//ָ���������
    		 mySql1.addSubPara(EdorAcceptNo1);//ָ���������
    		 mySql1.addSubPara(ContNo1);//ָ���������
    		 mySql1.addSubPara(AgentCode1);//ָ���������
    		 mySql1.addSubPara(comcode1);//ָ���������
    		 strSql = mySql1.getString();
    		 
    	}else if(_DBT==_DBM){
//    		 strSql = "select a.prtseq,a.standbyflag1,a.standbyflag2,"
//	 	         + " (select edorname from lmedoritem where (edorcode) = trim(a.standbyflag3) and appobj in('I','B') limit 1),"
//	 	         + " (select name from ldcom where comcode = a.managecom),"
//	 	         + " (select name from laagent where agentcode =trim(a.agentcode)) "
//	 	         + " from loprtmanager a where 1 = 1 "
//                 + getWherePart('a.ManageCom', 'ManageCom', 'like')
//                 + getWherePart('a.standbyflag1', 'EdorAcceptNo')
//                 + getWherePart('a.standbyflag2', 'ContNo') 
//                 + getWherePart('a.AgentCode', 'AgentCode') 
//                 + " and (a.standbyflag1) in(select (incomeno) from ljapay where incometype = '10')"
//                 + " and a.ManageCom like '" + comcode.substring(1,4) + "%'" //��½����Ȩ�޿��� 
//                 + " and a.StateFlag = '0' and a.code = 'BQ35'";
    		 
    		 var  ManageCom2 = window.document.getElementsByName(trim("ManageCom"))[0].value;
    		 var  EdorAcceptNo2 = window.document.getElementsByName(trim("EdorAcceptNo"))[0].value;
    		 var  ContNo2 = window.document.getElementsByName(trim("ContNo"))[0].value; 
    		 var  AgentCode2 = window.document.getElementsByName(trim("AgentCode"))[0].value;
    		 var  comcode2 = comcode.substring(1,4);
    		 var sqlid2="BqCheckPrintSql2";
    		 var mySql2=new SqlClass();
    		 mySql2.setResourceName("bq.BqCheckPrintSql");
    		 mySql2.setSqlId(sqlid2);//ָ��ʹ��SQL��id
    		 mySql2.addSubPara(ManageCom2);//ָ���������
    		 mySql2.addSubPara(EdorAcceptNo2);//ָ���������
    		 mySql2.addSubPara(ContNo2);//ָ���������
    		 mySql2.addSubPara(AgentCode2);//ָ���������
    		 mySql2.addSubPara(comcode2);//ָ���������
    		 strSql = mySql2.getString();
    	}
	   
	}else if(invoiceType != null && invoiceType == "BQ36"){
		if(_DBT==_DBO){
//			   strSql = "select a.prtseq,a.standbyflag1,a.standbyflag2,"
//		 	         + " (select edorname from lmedoritem where (edorcode) = trim(a.standbyflag3) and appobj ='G' and rownum = 1),"
//		 	         + " (select name from ldcom where comcode = a.managecom),"
//		 	         + " (select name from laagent where agentcode =trim(a.agentcode)) "
//		 	         + " from loprtmanager a where 1 = 1 "
//	                 + getWherePart('a.ManageCom', 'ManageCom', 'like')
//	                 + getWherePart('a.standbyflag1', 'EdorAcceptNo')
//	                 + getWherePart('a.standbyflag2', 'ContNo') 
//	                 + getWherePart('a.AgentCode', 'AgentCode') 
//	                 + " and (a.standbyflag1) in(select (incomeno) from ljapay where incometype = '10')"
//	                 + " and a.ManageCom like '" + comcode.substring(1,4) + "%'" //��½����Ȩ�޿��� 
//	                 + " and a.StateFlag = '0' and a.code = 'BQ36'";
			   
			     var  ManageCom3 = window.document.getElementsByName(trim("ManageCom"))[0].value;
	    		 var  EdorAcceptNo3 = window.document.getElementsByName(trim("EdorAcceptNo"))[0].value;
	    		 var  ContNo3 = window.document.getElementsByName(trim("ContNo"))[0].value; 
	    		 var  AgentCode3 = window.document.getElementsByName(trim("AgentCode"))[0].value;
	    		 var  comcode3 = comcode.substring(1,4);
	    		 var sqlid3="BqCheckPrintSql3";
	    		 var mySql3=new SqlClass();
	    		 mySql3.setResourceName("bq.BqCheckPrintSql");
	    		 mySql3.setSqlId(sqlid3);//ָ��ʹ��SQL��id
	    		 mySql3.addSubPara(ManageCom3);//ָ���������
	    		 mySql3.addSubPara(EdorAcceptNo3);//ָ���������
	    		 mySql3.addSubPara(ContNo3);//ָ���������
	    		 mySql3.addSubPara(AgentCode3);//ָ���������
	    		 mySql3.addSubPara(comcode3);//ָ���������
	    		 strSql = mySql3.getString();
			   
		}else if(_DBT==_DBM){
//			   strSql = "select a.prtseq,a.standbyflag1,a.standbyflag2,"
//		 	         + " (select edorname from lmedoritem where (edorcode) = trim(a.standbyflag3) and appobj ='G' and limit 1),"
//		 	         + " (select name from ldcom where comcode = a.managecom),"
//		 	         + " (select name from laagent where agentcode =trim(a.agentcode)) "
//		 	         + " from loprtmanager a where 1 = 1 "
//	                 + getWherePart('a.ManageCom', 'ManageCom', 'like')
//	                 + getWherePart('a.standbyflag1', 'EdorAcceptNo')
//	                 + getWherePart('a.standbyflag2', 'ContNo') 
//	                 + getWherePart('a.AgentCode', 'AgentCode') 
//	                 + " and (a.standbyflag1) in(select (incomeno) from ljapay where incometype = '10')"
//	                 + " and a.ManageCom like '" + comcode.substring(1,4) + "%'" //��½����Ȩ�޿��� 
//	                 + " and a.StateFlag = '0' and a.code = 'BQ36'";
			   
			     var  ManageCom4 = window.document.getElementsByName(trim("ManageCom"))[0].value;
	    		 var  EdorAcceptNo4 = window.document.getElementsByName(trim("EdorAcceptNo"))[0].value;
	    		 var  ContNo4 = window.document.getElementsByName(trim("ContNo"))[0].value; 
	    		 var  AgentCode4 = window.document.getElementsByName(trim("AgentCode"))[0].value;
	    		 var  comcode4 = comcode.substring(1,4);
	    		 var sqlid4="BqCheckPrintSql4";
	    		 var mySql4=new SqlClass();
	    		 mySql4.setResourceName("bq.BqCheckPrintSql");
	    		 mySql4.setSqlId(sqlid4);//ָ��ʹ��SQL��id
	    		 mySql4.addSubPara(ManageCom4);//ָ���������
	    		 mySql4.addSubPara(EdorAcceptNo4);//ָ���������
	    		 mySql4.addSubPara(ContNo4);//ָ���������
	    		 mySql4.addSubPara(AgentCode4);//ָ���������
	    		 mySql4.addSubPara(comcode4);//ָ���������
	    		 strSql = mySql4.getString();
		}
	 
	                 
//	var QueryCode = "select code from ldcode where codetype = 'bqgrpcheck' and trim(comcode) = '" + comcode.substring(1,4) + "'";
	
	 var  comcode5 = comcode.substring(1,4);
	 var sqlid5="BqCheckPrintSql5";
	 var mySql5=new SqlClass();
	 mySql5.setResourceName("bq.BqCheckPrintSql");
	 mySql5.setSqlId(sqlid5);//ָ��ʹ��SQL��id
	 mySql5.addSubPara(comcode5);//ָ���������
	 var QueryCode = mySql5.getString();
	
	var tResult = easyExecSql(QueryCode);
	if(tResult == null)
	{
	    alert("��ѯ�õ�½�������ñ�ȫ��Ʊ����ʧ�ܣ�");
	    return false;
	}
	document.all('CertifyCode').value = tResult[0][0];	                 
	               
	}			 	         

    turnPage.queryModal(strSql,NoticeGrid);
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

//��ʼ��������������ȡ��λ
function initManageCom()
{
	if(comcode.substring(0,6) !=null && comcode.substring(0,6) != "")
	{
    	var i,j,m,n;
	    var returnstr;
	    var tTurnPage = new turnPageClass();
//	    var strSQL = "select comcode,name from ldcom where comcode like '"+comcode.substring(1,6)+"%%'";
	    
         var  comcode6 = comcode.substring(1,6);
		 var sqlid6="BqCheckPrintSql6";
		 var mySql6=new SqlClass();
		 mySql6.setResourceName("bq.BqCheckPrintSql");
		 mySql6.setSqlId(sqlid6);//ָ��ʹ��SQL��id
		 mySql6.addSubPara(comcode6);//ָ���������
		 var strSQL = mySql6.getString();
	    
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
