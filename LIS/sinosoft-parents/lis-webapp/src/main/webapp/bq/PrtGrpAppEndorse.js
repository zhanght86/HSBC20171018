//�������ƣ�PrtAppEndorse.js
//�����ܣ�
//�������ڣ�2005-03-03 
//������  ��FanX
//���¼�¼��  ������    ��������     ����ԭ��/����
//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="1";
var turnPage = new turnPageClass();          //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���

//�ύ�����水ť��Ӧ����
function submitForm()
{  
  PrtEdor(); //�ύ
}


function prtCashValue() {
  PrtEdor("CashValue"); //�ύ
}
function initManageCom()
{
	if(comcode.substring(0,6) !=null && comcode.substring(0,6) != "")
	{
    	var i,j,m,n;
	    var returnstr;
	    var tTurnPage = new turnPageClass();
	    var strSQL = "select comcode,name from ldcom where comcode like '"+comcode.substring(0,6)+"%%'";
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

// ��ѯ��ť
function easyQueryClick()
{
		
	// ��ʼ�����
	initPEdorAppGrid();
	var tReturn = parseManageComLimit();

	// ��дSQL���
	var strSQL = "";
	strSQL = "select EdorAcceptNo, OtherNo,EdorAppName,codename,EdorAppDate,(select distinct edorno from lpgrpedoritem where lpgrpedoritem.edoracceptno = LPEdorApp.edoracceptno) from LPEdorApp,ldcode where 1=1 and trim(code) = trim(EdorState) and trim(codetype) = 'edorstate' and edorstate <> '0' and edorstate <> 'b' and exists (select 'A' from lpgrpedoritem a,lpedorprint b where a.edorno = b.edorno and a.edoracceptno = LPEdorApp.edoracceptno)"//+" and"+tReturn
	       +" and OtherNoType = '4' "
	       +" and ManageCom LIKE '" + comcode.substring(0,6) + "%%'" //��½����Ȩ�޿���
				 +" " + getWherePart('ManageCom', 'ManageCom', 'like')
				 +" " + getWherePart( 'OtherNo' , 'OtherNo')
				 +" " + getWherePart('EdorAcceptNo')
				 +" " + getWherePart('ManageCom', 'ManageCom', 'like')
	       + " order by EdorAppDate";
	 //alert(strSQL);
				 
				 
          //��ѯSQL�����ؽ���ַ���
	  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
	  
	  //�ж��Ƿ��ѯ�ɹ�
	  if (!turnPage.strQueryResult) 
	  {
	    alert("��ѯʧ�ܣ�");
	  }
	  else
	  {
	  //��ѯ�ɹ������ַ��������ض�ά����
	  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
	  
	  //���ó�ʼ������MULTILINE����VarGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
	  turnPage.pageDisplayGrid = PEdorAppGrid;    
	          
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
		var tSel = PEdorAppGrid.getSelNo();
	
		if( tSel == 0 || tSel == null )
			alert( "����ѡ��һ����¼���ٲ鿴" );
		else
		{
			if (PEdorAppGrid.getRowColData(tSel-1,1)==null||PEdorAppGrid.getRowColData(tSel-1,1)=='')
			{
				alert("�뵥����ѯ��ť!");
				return;
			}
			//alert(top.opener.document.all('EdorState').value);
			if (top.opener.document.all('EdorState').value =='2') 
			{
				arrReturn = getQueryResult();
				document.all('EdorAcceptNo').value = arrReturn[0][0];
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
function PrtEdor(printType)
{
	var arrReturn = new Array();
				
		var tSel = PEdorAppGrid.getSelNo();
	
		if( tSel == 0 || tSel == null )
			alert( "����ѡ��һ����¼���ٵ���鿴��ť��" );
		else
		{
			arrReturn = getQueryResult();
			document.all('EdorAcceptNo').value = arrReturn[0];
			fm.action = "../f1print/AppEndorsementF1PJ1.jsp?type="+printType;
			fm.target="f1print";		 	
			document.getElementById("fm").submit();
		}
}
function PrtEdorold(type)
{
	var arrReturn = new Array();
				
		var tSel = PEdorAppGrid.getSelNo();
	
		if( tSel == 0 || tSel == null )
			alert( "����ѡ��һ����¼���ٵ���鿴��ť��" );
		else
		{
			arrReturn = getQueryResult();
			document.all('EdorNo').value = arrReturn[0];
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
				
		//var tSel = PEdorAppGrid.getSelNo();
	
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
	var tSel = PEdorAppGrid.getSelNo();
	
	
		
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
	tRow = PEdorAppGrid.getSelNo();
	if( tRow == 0 || tRow == null || arrGrid == null )
		return arrSelected;
	arrSelected = new Array();
	arrSelected = PEdorAppGrid.getRowData(tRow - 1);	
	return arrSelected;
}

function PrtEdorBill()
{
	var arrReturn = new Array();
	
	var tSel = PEdorAppGrid.getSelNo();
	
	
	if( tSel == 0 || tSel == null )
		alert( "����ѡ��һ����¼���ٵ���鿴��ť��" );
	else
	{

			var EdorNo=PEdorAppGrid. getRowColData(tSel-1,6) ;
			//alert(EdorNo);
			var QuerySQL, arrResult;
      QuerySQL = "select 'X' from LPEdorPrint2 where EdorNo = '" + EdorNo + "'";
            //alert(QuerySQL);
      try
      {
          arrResult = easyExecSql(QuerySQL, 1, 0);
      }
      catch (ex)
      {
          alert("���棺��ѯ�����嵥��Ϣ�����쳣�� ");
          return;
      }
      if (arrResult == null)
      {
          alert("�ñ����˴�������Ŀû�������嵥��Ϣ�� ");
          return;
      }
      else
      {
          fm.action = "../f1print/ReEndorsementF1PJ1.jsp?EdorNo=" + EdorNo + "&type=Bill";
          fm.target = "_blank";
          document.getElementById("fm").submit();
      }
			//var varSrc="&EdorNo=" + EdorNo;
			//var newWindow = OpenWindowNew("../sys/BqDetailQueryFrame.jsp?Interface= ../sys/BqDetailQuery.jsp" + varSrc,"��ȫ��ѯ��ϸ","left");
			//fm.action = "../f1print/EndorsementF1PJ1.jsp?EdorNo=" + EdorNo;
			//fm.target="f1print";
			//document.getElementById("fm").submit();
		}
	
}