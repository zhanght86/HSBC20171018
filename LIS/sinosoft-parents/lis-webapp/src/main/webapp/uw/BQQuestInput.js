//�������ƣ�QuestInput.js
//�����ܣ������¼��
//�������ڣ�2002-06-19 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����

//���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="0";
var flag;
var k = 0;
var turnPage = new turnPageClass();
var QuestionOperator = "";

//�ύ�����水ť��Ӧ����
function submitForm(tOperator)
{
   if(getAllChecked()==false)
       return false;
   // return false;
    if(verifyInput()==false)
     return false;
	if(tOperator=='0')
	{
		//����
		QuestionOperator = "INSERT";
	}
	else if(tOperator=='1')
	{
		QuestionOperator = "DELETE";
		var tRowNum = QuestGrid.getSelNo();
		if(tRowNum<=0)
		{
			alert("��ѡ����Ҫɾ������!");
			return false;
		}
		if(document.all("hiddenQuestionSeq").value=='')
		{
			alert("û����Ҫɾ���������!");
			return false;
		}
		if (!confirm('ȷ��ɾ��?'))
		{
		return false;
		}
		//alert(tRowNum);
		//return false;
	}
	else if(tOperator=='2')
	{
		QuestionOperator = "UPDATE";
		var tRowNum = QuestGrid.getSelNo();
		if(tRowNum<=0)
		{
			alert("��ѡ����Ҫ�޸ĵ���!");
			return false;
		}
		if(document.all("hiddenQuestionSeq").value=='')
		{
			alert("û����Ҫ�޸ĵ������!");
			return false;
		}
		//tongmeng 2007-12-12 modify
		//���ڷ��ظ�������������޸ĸڵĲ������޸�
		var tBackObjType = QuestGrid.getRowColData(QuestGrid.getSelNo() - 1, 8);
		//alert(tBackObjType);
		/*if(tBackObjType=='1'||tBackObjType=='4')
		{
			alert("���͸�������޸ĸںͻ����Ĳ������޸��·����!");
			return false;
			}*/
		if (!confirm('ȷ���޸�?'))
		{
		return false;
		}
	}
	/*if(LoadFlag=="1"&&document.all('BackObj').value=="5"){
	  alert("������Լ¼��ʱ������ѡ�񡰷��Ͷ���Ϊ��������޸ĸڡ���������ѡ�񡰷��Ͷ��󡱣�");
	  return;
	}*/
    var i = 0;
    var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();	document.all('hiddenQuestionOperator').value=QuestionOperator;
    fm.submit(); //�ύ
}


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
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    
    
   
    //parent.close();
  }
  else
  { 
	var content="�����ɹ���";
  	var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
	//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  	initInpBox();
  	
  	//parent.close();
  	
    //ִ����һ������
  }
  questAllNeedQuestion()
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


function manuchkspecmain()
{
	fm.submit();
}

function QuestQuery()
{
	// ��ʼ�����
	var i,j,m,n;
	//initQuestGrid();
	
	
	// ��дSQL���
	k++;
	var strSQL = "";
	//tongmeng 2007-09-12 modify
	//�����ʹ��5.3������
	//if (tFlag == "1")
	//{
	//	strSQL = "select code,cont from ldcodemod where "+k+"="+k				 	
	//			 + " and codetype = 'Question'"
	//			 + " and code <> '99'";
				 
//		strSQL = "select code,cont from ldcodemod where "+k+"="+k				 	
//				 + " and codetype = 'Question'";
	//}
	
	     var sqlid6="BQQuestInputSql6";
		 var mySql6=new SqlClass();
		 mySql6.setResourceName("uw.BQQuestInputSql");
		 mySql6.setSqlId(sqlid6);//ָ��ʹ��SQL��id
		 mySql6.addSubPara(k);//ָ���������
		 mySql6.addSubPara(k);//ָ���������
		 strSQL = mySql6.getString();
		
	//alert(strSQL);
	
  //��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
  
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
    alert("û�����������");
    return "";
  }
  
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //���ó�ʼ������MULTILINE����HealthGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
  //turnPage.pageDisplayGrid = QuestGrid;    
          
  //����SQL���
  //turnPage.strQuerySql     = strSQL; 
  
  //���ò�ѯ��ʼλ��
  //turnPage.pageIndex       = 0;  
  
  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  //var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  
  //����MULTILINE������ʾ��ѯ���
  //displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  var returnstr = "";
  var n = turnPage.arrDataCacheSet.length;  
    if (n > 0)
  {
  	for( i = 0;i < n; i++)
  	{
  		m = turnPage.arrDataCacheSet[i].length;
  		//alert("M:"+m);
  		if (m > 0)
  		{
  			for( j = 0; j< m; j++)
  			{
  				if (i == 0 && j == 0)
  				{
  					returnstr = "0|^"+turnPage.arrDataCacheSet[i][j];
  				}
  				if (i == 0 && j > 0)
  				{
  					returnstr = returnstr + "|" + turnPage.arrDataCacheSet[i][j];
  				}
  				if (i > 0 && j == 0)
  				{
  					returnstr = returnstr+"^"+turnPage.arrDataCacheSet[i][j];
  				}
  				if (i > 0 && j > 0)
  				{
  					returnstr = returnstr+"|"+turnPage.arrDataCacheSet[i][j];
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
  //alert("returnstr:"+returnstr);		
  fm.Quest.CodeData = returnstr;
  return "";	
}


function QueryCont(tContNo, tFlag)
{	
	
	// ��дSQL���

	k++;

	var strSQL = "";

	//if (tFlag == "1")
	//{
//		strSQL = "select issuecont from lcissuepol where "+k+"="+k				 	
//				 + " and ContNo = '"+tContNo+"'";
	//}
	
	//alert(strSQL);
		
		 var sqlid1="BQQuestInputSql1";
	 	 var mySql1=new SqlClass();
	 	 mySql1.setResourceName("uw.BQQuestInputSql");
	 	 mySql1.setSqlId(sqlid1);//ָ��ʹ��SQL��id
	 	 mySql1.addSubPara(k);//ָ���������
	 	 mySql1.addSubPara(k);//ָ���������
	 	 mySql1.addSubPara(tContNo);//ָ���������
	 	 strSQL = mySql1.getString();
	
  //��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
  
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
    alert("û��¼����������");
    return "";
  }
  
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  var returnstr = "";
  var n = turnPage.arrDataCacheSet.length;
  //alert("N:"+n);
  if (n > 0)
  {
  	m = turnPage.arrDataCacheSet[0].length;
  		//alert("M:"+m);
  		if (m > 0)
  		{
  			//alert("turnPage:"+turnPage.arrDataCacheSet[0][0]);
			returnstr = turnPage.arrDataCacheSet[0][0];
  		}
  		else
  		{
  			alert("û��¼����������");
  			return "";
  		}
  	
  }
  else
  {
  	alert("û��¼����������");
	return "";
  }

  if (returnstr == "")
  {
  	alert("û��¼����������");
  }
  
  //alert("returnstr:"+returnstr);		
  document.all('Content').value = returnstr;
  //alert("�Ѿ�¼�����������뿼�����������¼�룡");
  return returnstr;
}

function initAppnt()
{
	var tContNo = fm.ContNo.value;
//	strsql = " select appntno,appntname from lccont where  ContNo = '"+tContNo+"' ";
	
	 var sqlid2="BQQuestInputSql2";
	 var mySql2=new SqlClass();
	 mySql2.setResourceName("uw.BQQuestInputSql");
	 mySql2.setSqlId(sqlid2);//ָ��ʹ��SQL��id
	 mySql2.addSubPara(tContNo);//ָ���������
	 strsql = mySql2.getString();
	
  //��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult = easyQueryVer3(strsql, 1, 1, 1);  

  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
      
    return "";
  }
  
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  fm.CustomerNo.value = turnPage.arrDataCacheSet[0][0];
 
  fm.CustomerName.value = turnPage.arrDataCacheSet[0][1];
}
function initInsured()
{
	var tContNo = fm.ContNo.value;
////	alert(tContNo+","+document.all('QuestionObj').value);
//	strsql = "select insuredno,Name from lcinsured "
//	       + "where  ContNo = '"+tContNo+"' "
//	       + "and SequenceNo = '"+document.all('QuestionObj').value+"'";
	
	 var QuestionObj3 = document.all('QuestionObj').value;
	 var sqlid3="BQQuestInputSql3";
	 var mySql3=new SqlClass();
	 mySql3.setResourceName("uw.BQQuestInputSql");
	 mySql3.setSqlId(sqlid3);//ָ��ʹ��SQL��id
	 mySql3.addSubPara(tContNo);//ָ���������
	 mySql3.addSubPara(QuestionObj3);//ָ���������
	 strsql = mySql3.getString();
	
	
  //��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult = easyQueryVer3(strsql, 1, 1, 1);  

  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
      
    alert("�޴˿ͻ�");
    return ;
  }
  
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  fm.CustomerNo.value = turnPage.arrDataCacheSet[0][0];
 
  fm.CustomerName.value = turnPage.arrDataCacheSet[0][1];
}

//tongmeng 2007-11-08 add
//��ʼ����ѯ������Ҫ�ظ��������
function questAllNeedQuestion()
{
	initQuestGrid();
//	strsql = " select edorno,issuetype,issuecont,replyresult,(select username from lduser where usercode=a.operator),makedate,(select codename from ldcode where codetype='operatepos' and code=a.operatepos), "
//	       + " backobjtype,needprint,serialno,(select codename from ldcode where codetype='bqbackobj' and comcode=a.BackObjType),(case when state is not null then state else 'x' end),'',proposalcontno from lpissuepol a where "
//	       + " edorno='"+fm.EdorNo.value+"' and edortype='"+fm.EdorType.value+"' and contno='"+fm.ContNo.value+"' "
//	       + " and (state is null or state='')"
//	       //+ " and prtseq is null "
//	       ;

	 var sqlid4="BQQuestInputSql4";
	 var mySql4=new SqlClass();
	 mySql4.setResourceName("uw.BQQuestInputSql");
	 mySql4.setSqlId(sqlid4);//ָ��ʹ��SQL��id
	 mySql4.addSubPara(fm.EdorNo.value);//ָ���������
	 mySql4.addSubPara(fm.EdorType.value);//ָ���������
	 mySql4.addSubPara(fm.ContNo.value);//ָ���������
	 strsql = mySql4.getString();
	
	//alert(strsql);
  //��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult = easyQueryVer3(strsql, 1, 1, 1);  

  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
    return ;
  }
  
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  turnPage.pageDisplayGrid = QuestGrid;    
          
  //����SQL���
  turnPage.strQuerySql     = strsql; 
  
  //���ò�ѯ��ʼλ��
  turnPage.pageIndex       = 0;  
  
  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  
  //����MULTILINE������ʾ��ѯ���
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
}

function queryone(parm1,parm2)
{
	//alert(QuestGrid.getRowColData(QuestGrid.getSelNo() - 1, 10));
	document.all("hiddenQuestionSeq").value = QuestGrid.getRowColData(QuestGrid.getSelNo() - 1, 10);
	document.all("EdorNo").value = QuestGrid.getRowColData(QuestGrid.getSelNo() - 1, 1);
	//alert(QuestGrid.getRowColData(QuestGrid.getSelNo() - 1, 12));
	document.all("hiddenQuestionState").value = QuestGrid.getRowColData(QuestGrid.getSelNo() - 1, 12);
	document.all("Content").value = QuestGrid.getRowColData(QuestGrid.getSelNo() - 1, 3);
	document.all("NeedPrintFlag").value = QuestGrid.getRowColData(QuestGrid.getSelNo() - 1, 9);
	document.all("hiddenProposalContNo").value = QuestGrid.getRowColData(QuestGrid.getSelNo() - 1, 14);
	if(document.all("NeedPrintFlag").value =='Y')
	{
	    document.all("IFNeedFlagName").value ='�·�';
	    }
	else
	{
	    document.all("IFNeedFlagName").value ='���·�';
	    }
	
	document.all("BackObj").value = QuestGrid.getRowColData(QuestGrid.getSelNo() - 1, 8);
	document.all("BackObjName").value = QuestGrid.getRowColData(QuestGrid.getSelNo() - 1, 11);
	
}

function getAllChecked()
{
   //alert(fm.Questionnaire.value);
 if(fm.Questionnaire.value =="Y")
 {
   var tHealthItem = "";
   var tOccupationItem = "";
   var tFinanceItem = "";
  // alert(window.document.forms[0].elements.length);
   for (elementsNum=0; elementsNum<window.document.forms[0].elements.length; elementsNum++)
		{
			if (window.document.forms[0].elements[elementsNum].type != null && window.document.forms[0].elements[elementsNum].type != "")
			{
			//if(window.document.forms[0].elements[elementsNum].type=='checkbox')
			//  {alert("Name:"+window.document.forms[0].elements[elementsNum].name); 
			//  alert("ID:"+window.document.forms[0].elements[elementsNum].id);
			// alert("Substring(0,1)"+window.document.forms[0].elements[elementsNum].name.substring(0,1));
			//  alert("Substring(0,2)"+window.document.forms[0].elements[elementsNum].name.substring(0,2));}
				if(window.document.forms[0].elements[elementsNum].type=='checkbox'
				&&window.document.forms[0].elements[elementsNum].name.substring(0,1)=='H'
				)
				{
			//	alert("��ǰΪ�����ʾ�");
				  //�����ʾ�
				//  alert(window.document.forms[0].elements[elementsNum].value);
				//  alert(window.document.forms[0].elements[elementsNum].id);
				  if(window.document.forms[0].elements[elementsNum].checked)
				  {
				//  alert("ѡ����"+window.document.forms[0].elements[elementsNum].value);
				    
				     tHealthItem = tHealthItem + "#" + window.document.forms[0].elements[elementsNum].id 
				                   +"-"+window.document.forms[0].elements[elementsNum].value;
				 //    alert(tHealthItem);
				     
				  }
				}else if(window.document.forms[0].elements[elementsNum].type=='checkbox'
				&&window.document.forms[0].elements[elementsNum].name.substring(0,1)=='O'
				){
			//	alert("��ǰΪְҵ�ʾ�");
				    //ְҵ�ʾ�
				  //  alert(window.document.forms[0].elements[elementsNum].value);
				  //  alert(window.document.forms[0].elements[elementsNum].id);
				  if(window.document.forms[0].elements[elementsNum].checked)
				  {
				  //  alert("ѡ����"+window.document.forms[0].elements[elementsNum].value);
				     tOccupationItem = tOccupationItem + "#" + window.document.forms[0].elements[elementsNum].id
				                  +"-"+window.document.forms[0].elements[elementsNum].value;
				//     alert(tOccupationItem);
				     
				  }
				}
				else if(window.document.forms[0].elements[elementsNum].type=='checkbox'
				&&window.document.forms[0].elements[elementsNum].name.substring(0,2)=='FO'
				){
			 //   alert("��ǰΪ��������ʾ�");
				    //����������ʾ�
				  //   alert(window.document.forms[0].elements[elementsNum].value);
				  //   alert(window.document.forms[0].elements[elementsNum].id);
				  if(window.document.forms[0].elements[elementsNum].checked)
				  {
				  //   alert("ѡ�У� ���� idΪ��"+window.document.forms[0].elements[elementsNum].id+"value Ϊ��"+window.document.forms[0].elements[elementsNum].value);
				     tFinanceItem = tFinanceItem + "#" + window.document.forms[0].elements[elementsNum].id
				                 +"-"+window.document.forms[0].elements[elementsNum].value;
				  //   alert(tFinanceItem);
				     
				  }
				}
			}
		}
	//	alert(tAllChecked);
   fm.HealthCheck.value = tHealthItem;
   fm.Occupation.value = tOccupationItem;
   fm.FinanceO.value = tFinanceItem;
		//alert("tHealthItem�� "+tHealthItem);
		//alert("tOccupationItem�� "+tOccupationItem);
	    //alert("tFinanceItem�� "+tFinanceItem);
		if((tHealthItem ==""||tHealthItem ==null)&&(tOccupationItem==""||tOccupationItem==null)&&(tFinanceItem==""||tFinanceItem==null))
		{
		  alert("������ѡ��һ��������е�һ�֣�");
		  return false;
		}
  	}
  		return true;
}

//�������check,����Ϊ��ѡ��
function clearAllCheck()
{
  for (elementsNum=0; elementsNum<window.document.forms[0].elements.length; elementsNum++)
		{
			if (window.document.forms[0].elements[elementsNum].type != null && window.document.forms[0].elements[elementsNum].type != "")
			{
				if(window.document.forms[0].elements[elementsNum].type=='checkbox'
				//||window.document.forms[0].elements[elementsNum].type=='radio'
				)
				{
				  //alert(window.document.forms[0].elements[elementsNum].id);
				  window.document.forms[0].elements[elementsNum].checked= false;
				}
			}
		}
}

//�����������Զ����������Ŀ
function showBodyCheck(ID)
{
	clearAllCheck();
	//alert(ID);
	var strValue;
     strValue=ID.split("#");
    for(n=0;n<strValue.length;n++)
    {
     // alert(strValue[n]);
      if(strValue[n]!=null&&strValue[n]!='')
      {
      	   eval("document.fm."+strValue[n]+".checked = true");
      	  // window.document.forms[0].elements[elementsNum].checked
      }
    }
}

function queryquestionnaire()
{
    //��ѯ�������Ŀ
//    var tSQL_Sub = " select asktype,asktypename,askcontentno,askcontentname from lcquestionnaire  "
//                 + " where proposalcontno = '"+fm.hiddenProposalContNo.value+"' ";
    
     var Proposal5 = fm.hiddenProposalContNo.value;
     var sqlid5="BQQuestInputSql5";
	 var mySql5=new SqlClass();
	 mySql5.setResourceName("uw.BQQuestInputSql");
	 mySql5.setSqlId(sqlid5);//ָ��ʹ��SQL��id
	 mySql5.addSubPara(Proposal5);//ָ���������
	 var tSQL_Sub = mySql5.getString();
    
    arrResult=easyExecSql(tSQL_Sub);
   // alert(arrResult.length);
    var tHealthCheck = "";
    var tOccupation ="";
    var tFinanceO ="";
    if(arrResult!=null)       
    {
    	fm.Questionnaire.value = 'Y';
         if(fm.BackObj.value=="2")
            divQuestionnaire.style.display='';
    	 for(i=0;i<arrResult.length;i++)
    	 {
    	 	 if(arrResult[i][0]=='H')
    	 	 {
    	 	    //�����ʾ�
    	 	    //����-�ʾ���� H-001
    	 	 	 tHealthCheck = tHealthCheck + '#'+arrResult[i][0]+arrResult[i][2];
    	 	 }
    	 	  else if(arrResult[i][0]=='O')
    	 	 {
    	 	    //ְҵ�ʾ�
    	 	 	  tOccupation = tOccupation + '#'+arrResult[i][0]+arrResult[i][2];
    	 	 }
              else if(arrResult[i][0]=='FO')
             {
                //���������ʾ�
                tFinanceO = tFinanceO + '#'+arrResult[i][0]+arrResult[i][2];
             }
    	 }
    }else{
    	fm.Questionnaire.value = 'N';
       divQuestionnaire.style.display='none';
    }  
   // alert(tAllCheckedItem); 
   // alert(tItemOther);  
        var tAllChecked = tHealthCheck+'#'+tOccupation+'#'+tFinanceO;     
      // alert(tAllChecked);
      showBodyCheck(tAllChecked);
	//	fm.proposalContNo.value = UWSpecGrid.getRowColData(tSelNo,5);
}

//tongmeng 2009-02-11 add
//�����ѡ�����������幦�ܹ���
function getClickIssueCode(Quest,Content)
{
	var tSendObj = document.all('BackObj').value;
	//alert(tManageCom.length);
	//if(length(tManageCom))
	if(tSendObj==null||tSendObj=='')
	{
		alert("����¼�뷢�Ͷ���!");
		return false;
	}
	var tSendSQL = " 1 and sendobj=#"+tSendObj+"# ";

  showCodeList('Quest',[Quest,Content],[0,1],null, tSendSQL, "1",'1');
}

function getKeyUpIssueCode(Quest,Content)
{
	var tSendObj = document.all('BackObj').value;
	var tSendSQL = " 1 and sendobj=#"+tSendObj+"# ";
    getBankCodeSQL = getBankCodeSQL + " and AgentCom like #"+tAgentBankCode+"%#" ;
    showCodeListKey('Quest',[Quest,Content],[0,1],null, tSendSQL, "1",'1');
}