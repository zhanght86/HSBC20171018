
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����

//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="0";
var flag;
var str = "";
var turnPage = new turnPageClass();
var k = 0;
var cflag = "1";  //�������Ͳ���λ�� 1.����
var str_sql = "",sql_id = "", my_sql = "";

//�ύ�����水ť��Ӧ����
function submitForm()
{
  var i = 0;

  if(fm.SubMissionID.value == "")
  {
  	alert("��¼������֪ͨ��,��δ��ӡ,������¼���µ�����֪ͨ��!");
  	//easyQueryClick();
  	parent.close();
  	return;
  	}
  var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
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


//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");      
    showInfo.close();
    alert(content);
    parent.close();
  }
  else
  { 
	var showStr="�����ɹ�";  	
  	showInfo.close();
  	alert(showStr);
  	parent.close();
  	
    //ִ����һ������
  }
}


//��ʾfrmSubmit��ܣ���������
function showSubmitFrame(cDebug)
{
  if(cDebug=="1")
  {
			parent.fraMain.rows = "0,0,50,82,*";
  }
 	else {
  		parent.fraMain.rows = "0,0,0,82,*";
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



 
  //��ѯ��ͬ��Ϣ��Ͷ������Ϣ

 function returnParent()
 {
 	var tContNo = fm.ContNo.value;
 	var tPrtSeq = fm.PrtSeq.value;
 
 
	var arrReturn1 = new Array();
	var arrReturn2 = new Array();
	
	
		try{
//			sql = "select * from LCCont where ContNo= '"+tContNo+"'"
			sql_id = "RReportDealEachSql1";
			my_sql = new SqlClass();
			my_sql.setResourceName("uw.RReportDealEachSql"); //ָ��ʹ�õ�properties�ļ���
			my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
			my_sql.addSubPara(tContNo);//ָ������Ĳ���
			str_sql = my_sql.getString();
			arrReturn1 =easyExecSql(str_sql);
			
			if (arrReturn1 == null) {
			  alert("δ�鵽��ͬ��Ϣ");
			} else {
			   displayCont(arrReturn1[0]);
			}
//			select * from LCRReport where ContNo = '" + tContNo + "' and PrtSeq = '"+tPrtSeq+"'
			sql_id = "RReportDealEachSql2";
			my_sql = new SqlClass();
			my_sql.setResourceName("uw.RReportDealEachSql"); //ָ��ʹ�õ�properties�ļ���
			my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
			my_sql.addSubPara(tContNo);//ָ������Ĳ���
			my_sql.addSubPara(tPrtSeq);//ָ������Ĳ���
			str_sql = my_sql.getString();
			arrReturn2 =easyExecSql(str_sql);
			
			if (arrReturn1 == null) {
			  alert("δ�鵽����ԭ��");
			} else {
			  displayAppnt(arrReturn2[0]);
			}
		}
		catch(ex)
		{
			alert( "����ѡ��һ���ǿռ�¼��");
			
		}
		
	
  }
 
  
  function displayCont(cArr)
  {     
 
  	try { document.all('ContNo1').value = cArr[1]; } catch(ex) { };
  	try { document.all('PrtNo').value = cArr[3]; } catch(ex) { };
  	try { document.all('ManageCom').value = cArr[9]; } catch(ex) { };
  	try { document.all('SaleChnl').value = cArr[16]; } catch(ex) { };
  	try { document.all('AgentCode').value = cArr[12]; } catch(ex) { };
  	try { document.all('AgentGroup').value = cArr[13]; } catch(ex) { };
  	try { document.all('AgentCom').value = cArr[11]; } catch(ex) { };
  		
   }
   function displayAppnt(cArr)
   {
   	
  	try { document.all('Contente').value = cArr[9]; } catch(ex) { };
  	try { document.all('CustomerNo1').value = cArr[6]; } catch(ex) { };
  	try { document.all('CustomerName').value = cArr[7]; } catch(ex) { };
  	try { document.all('RReportReason').value = cArr[22]; } catch(ex) { };
    showOneCodeName('rreportreason','RReportReason','RReportReasonname');
  	
   }
   
   function easyQueryClick()
{
	// ��дSQL���
	var strsql = "";
	
	var tContNo = fm.ContNo.value;
	var tPrtSeq = fm.PrtSeq.value;

  if(tContNo != ""&& tPrtSeq != "")
  {
//	strsql = "select RReportItemCode,RReportItemName,Remark from LCRReportItem where 1=1"				
//				 + " and ContNo = '"+ tContNo + "'"
//				 + " and PrtSeq = '"+ tPrtSeq +"'";
	sql_id = "RReportDealEachSql3";
	my_sql = new SqlClass();
	my_sql.setResourceName("uw.RReportDealEachSql"); //ָ��ʹ�õ�properties�ļ���
	my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
	my_sql.addSubPara(tContNo);//ָ������Ĳ���
	my_sql.addSubPara(tPrtSeq);//ָ������Ĳ���
	strsql = my_sql.getString();		 
	
  //��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult = easyQueryVer3(strsql, 1, 1, 1);  
  
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
    return "";
  }
  
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //���ó�ʼ������MULTILINE����HealthGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
  turnPage.pageDisplayGrid = InvestigateGrid;    
          
  //����SQL���
  turnPage.strQuerySql     = strsql; 
  
  //���ò�ѯ��ʼλ��
  turnPage.pageIndex       = 0;  
  
  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  
  //����MULTILINE������ʾ��ѯ���
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  }
  return true;
 
}



