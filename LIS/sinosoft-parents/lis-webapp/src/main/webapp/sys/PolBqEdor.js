//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="0";
var mSwitch = parent.VD.gVSwitch;
var turnPage = new turnPageClass(); 

//������ӡ���������������Ѿ���д��
function initEdorType(cObj)
{
	var tRiskCode = document.all('RiskCode').value;
	mEdorType = " 1 and RiskCode=#112103# and AppObj=#I#";
	//alert(mEdorType);
	showCodeList('EdorCode',[cObj], null, null, mEdorType, "1");
	//alert('bbb');
}

function actionKeyUp(cObj)
{
	var tRiskCode = document.all('RiskCode').value;
	mEdorType = " 1 and RiskCode=#112103# and AppObj=#I#";
	//alert(mEdorType);
	showCodeListKey('EdorCode',[cObj], null, null, mEdorType, "1");
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

//��ʾfrmSubmit��ܣ���������
function showSubmitFrame(cDebug)
{
  if(cDebug=="1")
  {
			parent.fraMain.rows = "0,0,0,0,*";
  }
 	else {
  		parent.fraMain.rows = "0,0,0,82,*";
 	}
	
	parent.fraMain.rows = "0,0,0,0,*";
}


/*********************************************************************
 *  ��ѯ���������б�ȫ��Ŀ��д��MulLine
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function getBqEdorGrid()
{
	initBqEdorGrid();
   // var tEdorAcceptNo = document.all('EdorAcceptNo').value;
   // alert("tOtherNoType="+tOtherNoType);
    if(tContNo!=null)
    {var   strSQL = "";

      /*   var   strSQL = " select edoracceptno , (select edorconfno from lpedorapp where edoracceptno = lpedoritem.edoracceptno), "+
                         " (select edorname from lmedoritem where edorcode = edortype and appobj in('I','B') and rownum = 1) , edorappdate, edorvalidate, " +
         				" (select CodeName from LDCode b where b.codetype = 'edorstate' and trim(b.code) = trim(edorstate)),"  +
         				" (select operator from lpedorapp where edoracceptno=lpedoritem.edoracceptno ) from lpedoritem " +
         				" where contno = '" + tContNo + "' order by edorappdate";*/
       mySql = new SqlClass();
		mySql.setResourceName("sys.PolBqEdorSql");
		mySql.setSqlId("PolBqEdorSql1");
		mySql.addSubPara(tContNo);   
     //turnPage.queryModal(strSQL, BqEdorGrid);
	 //arrResult = easyExecSql(strSQL);
     //alert(arrResult);
     //displayMultiline(arrResult,BqEdorGrid); 
     
  turnPage.strQueryResult  = easyQueryVer3(mySql.getString(), 1, 0, 1); 
// alert(turnPage.strQueryResult);
     
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
  	BqEdorGrid.clearData();
    alert("�ñ���û�б�ȫ��ʷ��");
    return false;
  }
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  //���ó�ʼ������MULTILINE����
  turnPage.pageDisplayGrid = BqEdorGrid;    
       
  //����SQL���
  turnPage.strQuerySql     = strSQL; 
  
  //���ò�ѯ��ʼλ��
  turnPage.pageIndex = 0;  
  
  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  var arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  arrGrid = arrDataSet;
  //����MULTILINE������ʾ��ѯ���
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);

    }
}


// ��ѯ��ť
function QueryClick()
{
	var tContNo1 = document.all('ContNo1').value;
	//alert('Contno1:'+tContNo1 );
	var strSQL="";
	if (tflag=="0")
		{
			/*strSQL = " select EdorAcceptNo,OtherNo,"
			       + " OthernoType ,EdorAppName,AppType,EdorAppDate,"
			       + " (select CodeName from LDCode b where b.codetype = 'edorstate' and trim(b.code) = trim(edorstate))," 
			       + " operator from lpedorapp "
			       + " where OtherNo='" + tContNo1 + "'"
	               + " order by makedate asc, maketime asc";*/
//			         
//			  strSQL =  " select EdorAcceptNo, EdorNo, " 
//	        		//	+ " (select distinct edorcode||'-'||edorname from lmedoritem m where  trim(m.edorcode) = trim(edortype)), " 
//	                    + " InsuredNo, PolNo,"
//	                    + " (select m.riskname from lcpol b ,lmrisk m  where b.polno =LPEdorItem.polno and b.riskcode=m.riskcode),"
//	                    + " EdorAppDate, EdorValiDate, "
//	                    + " (select CodeName from LDCode a where a.codetype = 'edorappreason' and trim(a.code) = trim(appreason)), " 
//	                    + " (select CodeName from LDCode b where b.codetype = 'edorstate' and trim(b.code) = trim(edorstate))" 
//	                    + " from LPEdorItem " 
//	                    + " where ContNo='" + tContNo1 + "'"
//	                    + " order by makedate asc, maketime asc";
 		mySql = new SqlClass();
		mySql.setResourceName("sys.PolBqEdorSql");
		mySql.setSqlId("PolBqEdorSql2");
		mySql.addSubPara(tContNo1);   
		}
	else
		{
			
			/*strSQL = " select EdorAcceptNo,OtherNo,"
			       + " OthernoType ,EdorAppName,AppType,EdorAppDate,"
			       + " (select CodeName from LDCode b where b.codetype = 'edorstate' and trim(b.code) = trim(edorstate))," 
			       + " Operator from LPEdorApp "
	               + " where 1=1"
	               + getWherePart( 'LPEdorApp.OtherNo','ContNo1')
	               + " order by makedate asc, maketime asc";*/
		mySql = new SqlClass();
		mySql.setResourceName("sys.PolBqEdorSql");
		mySql.setSqlId("PolBqEdorSql3");
		mySql.addSubPara(tContNo1);  
// 	         strSQL =  " select EdorAcceptNo, EdorNo, " 
//	        		//	+ " (select distinct edorcode||'-'||edorname from lmedoritem m where  trim(m.edorcode) = trim(edortype)), " 
//	                    + " InsuredNo, PolNo,"
//	                    + " (select m.riskname from lcpol b ,lmrisk m  where b.polno =LPEdorItem.polno and b.riskcode=m.riskcode),"
//	                    + " EdorAppDate, EdorValiDate, "
//	                    + " (select CodeName from LDCode a where a.codetype = 'edorappreason' and trim(a.code) = trim(appreason)), " 
//	                    + " (select CodeName from LDCode b where b.codetype = 'edorstate' and trim(b.code) = trim(edorstate))" 
//	                    + " from LPEdorItem " 
//	                    + " where 1=1"
//	                    + getWherePart( 'LPEdorItem.ContNo','ContNo1')
//	                    + " order by makedate asc, maketime asc";
			}	
	   //��ѯSQL�����ؽ���ַ���
        turnPage.strQueryResult  = easyQueryVer3(mySql.getString(), 1, 0, 1);  
  
  		//�ж��Ƿ��ѯ�ɹ�
       if (!turnPage.strQueryResult)
  	   {
  		   BqEdorGrid.clearData();
       	   alert("δ�鵽�ñ�����ȫ������Ϣ��");
           return false;
  	   }
  		//��ѯ�ɹ������ַ��������ض�ά����
  		turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  		//���ó�ʼ������MULTILINE����
  		turnPage.pageDisplayGrid = BqEdorGrid;    
       
  		//����SQL���
  		turnPage.strQuerySql     = strSQL; 
  
  		//���ò�ѯ��ʼλ��
  		turnPage.pageIndex = 0;  
  
 		//�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  		var arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  		arrGrid = arrDataSet;
  		//����MULTILINE������ʾ��ѯ���
  		displayMultiline(arrDataSet, turnPage.pageDisplayGrid);	
}


function QueryClick1()
{
	
	document.all('Transact').value ="QUERY|EDOR";
  var i = 0;
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
//  showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");  
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
  var iWidth=550;      //�������ڵĿ��; 
  var iHeight=250;     //�������ڵĸ߶�; 
  var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
  var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
  showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

  showInfo.focus();

  document.getElementById("fm").submit(); //�ύ 
}

function getQueryResult()
{
	var arrSelected = null;
	tRow = BqEdorGrid.getSelNo();
	if( tRow == 0 || tRow == null || arrGrid == null )
		return arrSelected;
	arrSelected = new Array();
	//������Ҫ���ص�����
	//arrSelected[0] = arrGrid[tRow-1];
	arrSelected[0] = BqEdorGrid.getRowData(tRow-1);
	//alert(arrSelected[0][0]);
	return arrSelected;
}

	
function returnParent()
{
	//top.opener.initSelfGrid();
	//top.opener.easyQueryClickSelf();
	top.close();
}

	//��ӡ����
function PrtEdor()
{
	var arrReturn = new Array();
				
		var tSel = BqEdorGrid.getSelNo();
	
			
		if( tSel == 0 || tSel == null )
			alert( "����ѡ��һ����¼���ٵ���鿴��ť��" );
		else
		{
			var state =BqEdorGrid. getRowColData(tSel-1,6) ;
	        
			if (state=="��������")
				alert ("��ѡ����������������״̬�����ܴ�ӡ��");
			else{
			  var EdorAcceptNo=BqEdorGrid. getRowColData(tSel-1,1) ;
			  
			    //var tEdorAcceptNo = SelfGrid.getRowColData(selno, 1);
	            //var tMissionID = SelfGrid.getRowColData(selno, 5);
	            //var tSubMissionID = SelfGrid.getRowColData(selno, 6);
	            //var tLoadFlag = "edorApp";
	            
				var varSrc="&EdorAcceptNo=" + EdorAcceptNo;
				var newWindow = OpenWindowNew("../sys/BqDetailQueryFrame.jsp?Interface= ../sys/BqDetailQuery.jsp" + varSrc,"��ȫ��ѯ��ϸ","left");	
				//window.open("./BqDetailQuery.jsp?EdorAcceptNo="+EdorAcceptNo+"");
				//fm.target="f1print";	
			
			//	fm.submit();

			}
		}
}


/*********************************************************************
 *  MulLine��RadioBox����¼�����ʾ��Ŀ��ϸ��ť
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function getBqEdorDetail()
{
    //alert(fm.ContNo.value);
    var tSelNo = BqEdorGrid.getSelNo();
    document.all('EdorAcceptNo').value = BqEdorGrid.getRowColData(tSelNo-1, 2);
}

//��ȫ������ϸ��ť
function gotoDetail()
{
	var tSelNo = BqEdorGrid.getSelNo();
	if( tSelNo == 0 || tSelNo == null )
	{
	    alert( "����ѡ��һ����¼���ٵ����ϸ��ѯ��ť��" );
	    return;      
	}
	var tEdorAcceptNo = BqEdorGrid.getRowColData(tSelNo-1, 1);
    //alert(tEdorAcceptNo);
    var tContNo1 = document.all('ContNo1').value;
	var varSrc="&EdorAcceptNo=" + tEdorAcceptNo+"&OtherNoType=3&tContNo="+tContNo1;
	var newWindow = OpenWindowNew("../sys/BqDetailQueryFrame.jsp?Interface= ../sys/BqDetailQuery.jsp" + varSrc,"��ȫ��ѯ��ϸ","left");	
}
