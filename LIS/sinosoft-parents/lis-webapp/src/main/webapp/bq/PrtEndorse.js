//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="1";
var turnPage = new turnPageClass();          //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���

//�ύ�����水ť��Ӧ����
function submitForm()
{
  var i = 0;
 // var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  //var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
 // showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");    
  PrtEdor(); //�ύ
}
function submitFormold()
{
  var i = 0;
 // var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  //var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
 // showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");    
  PrtEdorold(); //�ύ
}

function prtCashValue() {
  PrtEdor("CashValue"); //�ύ
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
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

    //showDiv(operateButton,"true"); 
    //showDiv(inputButton,"false"); 
    //ִ����һ������
  }
}



//���ð�ť��Ӧ����,Form�ĳ�ʼ�������ڹ�����+Init.jsp�ļ���ʵ�֣�����������ΪinitForm()
function resetForm()
{
  try
  {
	  initForm();
  }
  catch(re)
  {
  	alert("��Proposal.js-->resetForm�����з����쳣:��ʼ���������!");
  }
} 

//ȡ����ť��Ӧ����
function cancelForm()
{
    showDiv(operateButton,"true"); 
    showDiv(inputButton,"false"); 
}
 
//�ύǰ��У�顢����  
function beforeSubmit()
{
  //��Ӳ���	
}           

//Click�¼������������ͼƬʱ�����ú���
function addClick()
{
  //����������Ӧ�Ĵ���
  showDiv(operateButton,"false"); 
  showDiv(inputButton,"true"); 
}           

//Click�¼�����������޸ġ�ͼƬʱ�����ú���
function updateClick()
{
  //����������Ӧ�Ĵ���
  alert("update click");
}           

//Click�¼������������ѯ��ͼƬʱ�����ú���
function queryClick()
{
  //����������Ӧ�Ĵ���
	alert("query click");
	  //��ѯ���������һ��ģ̬�Ի��򣬲��ύ�������������ǲ�ͬ��
  //��ˣ����еĻ����Ҳ���Բ��ø�ֵ��
}           

//Click�¼����������ɾ����ͼƬʱ�����ú���
function deleteClick()
{
  //����������Ӧ�Ĵ���
  alert("delete click");
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

/*********************************************************************
 *  ��ʾfrmSubmit��ܣ���������
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function showSubmitFrame(cDebug)
{
	if( cDebug == "1" )
		parent.fraMain.rows = "0,0,50,82,*";
	else 
		parent.fraMain.rows = "0,0,0,72,*";
}
// ǰ̨��ѯʱ���õĲ�ѯ��ť��ע��Ҫ��easyQueryClick()ͬ��
function queryEndorse()
{
	var EdorState;
    if(trim(fm.ManageCom.value).length == 2 && trim(fm.EdorAcceptNo.value) == "" && trim(fm.ContNo.value) == "" && trim(fm.EdorNo.value) == "")
    {
	    alert("��ѯ����Ϊ�ܹ�˾����ѡ��������ѯ������\n�����뱣ȫ����ţ������ţ������ţ�");
	    return;
	}
	
	if(fm.ContNo.value == "" && fm.EdorNo.value == "" && fm.EdorAcceptNo.value == "")
	{
	    alert("���ߴ�ӡǰ�������뱣���ţ���ȫ����Ż������ţ�\n���ߵ���ȫ������ӡҳ����д�ӡ��");
	    return;
	}
		
	// ��ʼ�����
	initPEdorMainGrid();
	var tReturn = parseManageComLimit();

	// ��дSQL���
	var strSQL = "";
//	strSQL = " select distinct EdorConfNo,     "           
//								+" otherno,       "            
//								+" a.edorappname, "            
//								+" a.EdorAppDate, "            
//								+" a.modifytime,  "            
//								+" a.edoracceptno,"            
//								+" b.edorno       "            
//                +" from LPEdorApp a,lpedoritem b  "          
//                +" where a.edoracceptno=b.edoracceptno "   
//                +" and exists (select 'Y' "                 
//								+"	from lpedorprint c "      
//								+" where c.prttimes = 0 "     
//								+"	 and c.edorno = b.edorno) "
//								 +" and a.ManageCom LIKE '" + comcode.substring(0,6) + "%%'" //��½����Ȩ�޿���
//	              +" and a.EdorState in ('0', '6') "   
//					      + getWherePart( 'a.otherno','ContNo' )
//				        + getWherePart( 'a.EdorConfNo','EdorNo' )
//				        + getWherePart('a.EdorAcceptNo','EdorAcceptNo')
//				        + getWherePart('a.ManageCom', 'ManageCom', 'like')
//	              +" order by a.EdorAppDate,a.modifytime";
	 //alert(strSQL);
	var sqlid1 = "PrtEndorseSql1";
	var mySql1 = new SqlClass();
	mySql1.setResourceName("bq.PrtEndorseSql"); // ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);// ָ��ʹ�õ�Sql��id
	mySql1.addSubPara(comcode.substring(0,6));// ָ������Ĳ���
	mySql1.addSubPara(window.document.getElementsByName(trim('ContNo'))[0].value);// ָ������Ĳ���
	mySql1.addSubPara(window.document.getElementsByName(trim('EdorNo'))[0].value);// ָ������Ĳ���
	mySql1.addSubPara(window.document.getElementsByName(trim('EdorAcceptNo'))[0].value);// ָ������Ĳ���
	mySql1.addSubPara(window.document.getElementsByName(trim('ManageCom'))[0].value);// ָ������Ĳ���
	strSQL = mySql1.getString();
				 
//��ѯSQL�����ؽ���ַ���
	  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
	  
	  //�ж��Ƿ��ѯ�ɹ�
	  if (!turnPage.strQueryResult) 
	  {
	    alert("�������ѯ������������������Ѵ�ӡ!");
	    initPEdorMainGrid();
	    return;
	  }
	  else
	  {
	  	//��ѯ�ɹ������ַ��������ض�ά����
	  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
	  
	  //���ó�ʼ������MULTILINE����VarGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
	  turnPage.pageDisplayGrid = PEdorMainGrid;    
	          
	  //����SQL���
	  turnPage.strQuerySql     = strSQL; 
	  
	  //���ò�ѯ��ʼλ��
	  turnPage.pageIndex       = 0;  
	  
	  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
	  var arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
	  arrGrid = arrDataSet;
	  //����MULTILINE������ʾ��ѯ���
	  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
		}
}
// ��ӡ�󷵻�ʱ���õĲ�ѯ��ť��ע��Ҫ��queryEndorse()ͬ��
function easyQueryClick()
{
	if(fm.ContNo.value == "" && fm.EdorNo.value == "" && fm.EdorAcceptNo.value == "")
	{
	    alert("�����뱣���ţ���ȫ����Ż������ţ�");
	    return;
	}
	var EdorState;
		
	// ��ʼ�����
	initPEdorMainGrid();
	var tReturn = parseManageComLimit();

	// ��дSQL���
	var strSQL = "";
//	strSQL = "select distinct EdorNo, ContNo,InsuredNo,EdorAppDate,modifytime,edoracceptno from LPEdorItem where EdorNo in (select EdorNo from lpedorprint where prttimes = 0) and EdorState in ('0','6')"//+" and"+tReturn
//				 +" "+ getWherePart( 'ContNo' )
//				 +" "+ getWherePart( 'EdorNo' )
//				 +" " + getWherePart('EdorAcceptNo')
//				 +" and ManageCom LIKE '" + comcode.substring(0,6) + "%%'" //��½����Ȩ�޿���
//				 +" " + getWherePart('ManageCom', 'ManageCom', 'like')
//	       + " order by EdorAppDate,modifytime";
	 //alert(strSQL);
	var sqlid2 = "PrtEndorseSql2";
	var mySql2 = new SqlClass();
	mySql2.setResourceName("bq.PrtEndorseSql"); // ָ��ʹ�õ�properties�ļ���
	mySql2.setSqlId(sqlid2);// ָ��ʹ�õ�Sql��id
	mySql2.addSubPara(window.document.getElementsByName(trim('ContNo'))[0].value);// ָ������Ĳ���
	mySql2.addSubPara(window.document.getElementsByName(trim('EdorNo'))[0].value);// ָ������Ĳ���
	mySql2.addSubPara(window.document.getElementsByName(trim('EdorAcceptNo'))[0].value);// ָ������Ĳ���
	mySql2.addSubPara(comcode.substring(0,6));// ָ������Ĳ���
	mySql2.addSubPara(window.document.getElementsByName(trim('ManageCom'))[0].value);// ָ������Ĳ���
	var strSQl = mySql2.getString();			 
				 
//��ѯSQL�����ؽ���ַ���
	  turnPage.strQueryResult  = easyQueryVer3(strSQl, 1, 0, 1);  
	  
	  //�ж��Ƿ��ѯ�ɹ�
	  if (!turnPage.strQueryResult) 
	  {
	  	//��ӡ���ص���ʱ�����ѯʧ�ܲ������Ի���
	    //alert("�������ѯ������������������Ѵ�ӡ!");
	    initPEdorMainGrid();
	    return;
	  }
	  else
	  {
	  	//��ѯ�ɹ������ַ��������ض�ά����
	  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
	  
	  //���ó�ʼ������MULTILINE����VarGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
	  turnPage.pageDisplayGrid = PEdorMainGrid;    
	          
	  //����SQL���
	  turnPage.strQuerySql     = strSQL; 
	  
	  //���ò�ѯ��ʼλ��
	  turnPage.pageIndex       = 0;  
	  
	  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
	  var arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
	  arrGrid = arrDataSet;
	  //����MULTILINE������ʾ��ѯ���
	  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
		}
}


//��ѯ��ϸ��Ϣ
function detailEdor()
	{
		var arrReturn = new Array();
		var tSel = PEdorMainGrid.getSelNo();
	
		if( tSel == 0 || tSel == null )
			alert( "����ѡ��һ����¼���ٲ鿴" );
		else
		{
			if (PEdorMainGrid.getRowColData(tSel-1,1)==null||PEdorMainGrid.getRowColData(tSel-1,1)=='')
			{
				alert("�뵥����ѯ��ť!");
				return;
			}
			//alert(top.opener.document.all('EdorState').value);
			if (top.opener.document.all('EdorState').value =='2') 
			{
				arrReturn = getQueryResult();
				document.all('EdorNo').value = arrReturn[0][6];
				document.all('PolNo').value=arrReturn[0][1];
			
				detailEdorType();
			}
			else
			{
				alert("��û������ȷ�ϣ�");
				top.close();
			}
		}
	}
	//��ӡ����
function PrtEdor(type)
{
	var arrReturn = new Array();
				
		var tSel = PEdorMainGrid.getSelNo();
	
		if( tSel == 0 || tSel == null )
			alert( "����ѡ��һ����¼���ٵ���鿴��ť��" );
		else
		{
			arrReturn = getQueryResult();
			document.all('EdorNo').value = arrReturn[6];
			document.all('ContNo').value=arrReturn[1];	
			/*parent.fraInterface.*/fm.action = "../f1print/EndorsementF1PJ1.jsp?type="+type;
			/*parent.fraInterface.*/fm.target="f1print";		 	
			// showSubmitFrame(mDebug);
			document.getElementById("fm").submit();
			/*parent.fraInterface.*/fm.action = "./PEdorQueryOut.jsp";
			/*parent.fraInterface.*/fm.target="fraSubmit";
		}
}
function PrtEdorold(type)
{
	var arrReturn = new Array();
				
		var tSel = PEdorMainGrid.getSelNo();
	
		if( tSel == 0 || tSel == null )
			alert( "����ѡ��һ����¼���ٵ���鿴��ť��" );
		else
		{
			arrReturn = getQueryResult();
			document.all('EdorNo').value = arrReturn[7];
			document.all('ContNo').value=arrReturn[1];	
			/*parent.fraInterface.*/fm.action = "../f1print/EndorsementF1P.jsp?type="+type;
			/*parent.fraInterface.*/fm.target="f1print";		 	
			// showSubmitFrame(mDebug);
			document.getElementById("fm").submit();
			/*parent.fraInterface.*/fm.action = "./PEdorQueryOut.jsp";
			/*parent.fraInterface.*/fm.target="fraSubmit";
		}
}

function changeEdorPrint()
{
	//var arrReturn = new Array();
				
		//var tSel = PEdorMainGrid.getSelNo();
	
		//if( tSel == 0 || tSel == null )
			//alert( "����ѡ��һ����¼���ٵ���鿴��ť��" );
		//else
		//{
			//arrReturn = getQueryResult();
			//document.all('EdorNo').value = arrReturn[0];
			//document.all('ContNo').value=arrReturn[1];	
			/*parent.fraInterface.*/fm.action = "./ChangeEdorPrint.jsp";
			/*parent.fraInterface.*/fm.target="f1print"; 
			//document.getElementById("fm").submit();
			/*parent.fraInterface.*/fm.action = "./PEdorQueryOut.jsp";
			/*parent.fraInterface.*/fm.target="fraSubmit";
		//}
		window.open("./ChangeEdorPrint.jsp");
}
// ���ݷ��ظ�����
function returnParent()
{
	var arrReturn = new Array();
	var tSel = PEdorMainGrid.getSelNo();
	
	
		
	if( tSel == 0 || tSel == null )
		top.close();
		//alert( "����ѡ��һ����¼���ٵ�����ذ�ť��" );
	else
	{
		
			try
			{
				arrReturn = getQueryResult();
				top.opener.afterQuery( arrReturn );
			}
			catch(ex)
			{
				//alert( "û�з��ָ����ڵ�afterQuery�ӿڡ�" + ex );
			}
			top.close();
		
	}
}

function getQueryResult()
{
	var arrSelected = null;
	tRow = PEdorMainGrid.getSelNo();
	if( tRow == 0 || tRow == null || arrGrid == null )
		return arrSelected;
	arrSelected = new Array();
	arrSelected = PEdorMainGrid.getRowData(tRow - 1);
//  alert(arrSelected);
/*	
	tRow = 10 * turnPage.pageIndex + tRow; //10����multiline������
	//������Ҫ���ص�����
	arrSelected[0] = turnPage.arrDataCacheSet[tRow-1];
	//arrSelected[0] = arrGrid[tRow-1];
	//alert(arrSelected[0][0]);
*/	
	return arrSelected;
}

function print(){
	var selno = PEdorMainGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("��ѡ��Ҫ���������");
	      return;
	}	
	//document.all('EdorAcceptNo').value = PEdorMainGrid.getRowColData(selno, 6);
	var EdorNo = PEdorMainGrid.getRowColData(selno, 7);
	fm.action = "../f1print/EndorsementF1PJ1.jsp?EdorNo=" + EdorNo;
  fm.target="f1print";
  document.getElementById("fm").submit();
}
//��ʼ��������������ȡ��λ
function initManageCom()
{
	if(comcode.substring(0,6) !=null && comcode.substring(0,6) != "")
	{
    	var i,j,m,n;
	    var returnstr;
	    var tTurnPage = new turnPageClass();
	   // var strSQL = "select comcode,name from ldcom where comcode like '"+comcode.substring(1,6)+"%%'";
	    var strSQL= "";
	    var sqlid3 = "PrtEndorseSql3";
		var mySql3 = new SqlClass();
		mySql3.setResourceName("bq.PrtEndorseSql"); // ָ��ʹ�õ�properties�ļ���
		mySql3.setSqlId(sqlid3);// ָ��ʹ�õ�Sql��id
		mySql3.addSubPara(comcode.substring(0,6));// ָ������Ĳ���
		strSQl = mySql3.getString();			 
	    tTurnPage.strQueryResult  = easyQueryVer3(strSQl, 1, 1, 1); 
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