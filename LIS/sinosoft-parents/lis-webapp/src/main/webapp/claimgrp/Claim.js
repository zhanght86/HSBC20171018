//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="0";
var mSwitch = parent.VD.gVSwitch;
var turnPage = new turnPageClass(); 
var mySql = new SqlClass();
//��ʾdiv����һ������Ϊһ��div�����ã��ڶ�������Ϊ�Ƿ���ʾ�����Ϊ"true"����ʾ��������ʾ
/*
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

*/

// ��ȫ��ѯ��ť
function easyQueryClick()
{
	// ��ʼ�����
	initPolGrid();
	
	// ��дSQL���
	// ��дSQL���
	var strSQL = "";
	var strSQL1 ="";
	var strSQL2="";
	
	//��������ȡ   �᰸����
	if(fm.StartDate.value!="")
	{
	  //strSQL2=" and  a.EndCaseDate >= '"+fm.StartDate.value+"'";	
	  mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLGrpReportSimpleInputSql");
		mySql.setSqlId("LLGrpReportSimpleSql1");
		mySql.addSubPara(fm.StartDate.value ); 
		mySql.addSubPara(fm.StartDate.value ); 
		mySql.addSubPara(fm.RgtNo.value ); 
		mySql.addSubPara(fm.ManageCom.value ); 
        }
	 if(fm.EndDate.value!="")
	{
	  //strSQL2=strSQL2+" and a.EndCaseDate <= '"+fm.EndDate.value+"'";
	  mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLGrpReportSimpleInputSql");
		mySql.setSqlId("LLGrpReportSimpleSql2");
		mySql.addSubPara(fm.StartDate.value ); 
		mySql.addSubPara(fm.EndDate.value ); 
		mySql.addSubPara(fm.StartDate.value ); 
		mySql.addSubPara(fm.EndDate.value ); 
		mySql.addSubPara(fm.RgtNo.value ); 
		mySql.addSubPara(fm.ManageCom.value ); 
	  	
        }
	/*strSQL = "select unique a.RgtNo,a.RealPay,to_char(a.EndCaseDate,'yyyy-mm-dd') EndCaseDate,b.appntname";
	strSQL = strSQL + " from LLClaim a,LLClaimPolicy b   ";
	strSQL = strSQL + "   where  a.ClmNo=b.ClmNo ";
	//����͸�����Ӧ�ö����Բ����
	strSQL = strSQL + "   and (a.GiveType='0' or a.GiveType='1' or a.GiveType='4')";
	
        strSQL = strSQL + strSQL2;
        strSQL = strSQL + " and a.RgtNo in (select RgtNo from LLRegister ";
        strSQL = strSQL + "  where EndCaseFlag='1'";
	strSQL = strSQL + " and  ClmState!='4'  and ClmState is not null";
        strSQL = strSQL + strSQL2;		
        	
	strSQL = strSQL + ")";
        strSQL = strSQL + " and a.ClmState!='4' ";				
        strSQL = strSQL + " and a.ClmState is not null";
	strSQL = strSQL + " and a.ClmState not in ('10','20','30','40')";
	strSQL = strSQL + getWherePart('a.ClmNo','RgtNo');*/

	//��վ����
	 if (fm.ManageCom.value=="" || fm.ManageCom.value==null || !isNumeric(fm.ManageCom.value))
        {
        	fm.ManageCom.value=fm.OPManageCom.value;
        }
	//strSQL = strSQL + getWherePart('a.mngcom','ManageCom','like');   //�������
    //    strSQL = strSQL + " order by a.RgtNo,to_char(a.EndCaseDate,'yyyy-mm-dd')";
	
//��ѯSQL�����ؽ���ַ���
	  turnPage.strQueryResult  = easyQueryVer3(mySql.getString(), 1, 0, 1);   
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
  	PolGrid.clearData();
    alert("����������������");
    return false;
  }
  
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //���ó�ʼ������MULTILINE����
  turnPage.pageDisplayGrid = PolGrid;    
          
  //����SQL���
  turnPage.strQuerySql     = strSQL; 
  
  //���ò�ѯ��ʼλ��
  turnPage.pageIndex = 0;  
  
  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  var arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  
  //����MULTILINE������ʾ��ѯ���
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
}

function ReportQueryClick()
{
   	/*var tsql="select rptno,rptdate,accidentdate,grpname from llreport where mngcom like '"+document.all('ManageCom').value+"%%' and AvaliReason='01' "
   	        + getWherePart('RptNo','RgtNo')
   	        +"order by rptdate desc";   */
   	 mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLGrpReportSimpleInputSql");
		mySql.setSqlId("LLGrpReportSimpleSql3");
		mySql.addSubPara(document.all('ManageCom').value); 
		mySql.addSubPara(fm.RgtNo.value );         
   	turnPage.queryModal(mySql.getString(),PolGrid,1,1);
   		
}

function ReportAccQueryClick()
{
   /*	var tsql="select rptno,rptdate,accidentdate,grpname from llreport where mngcom like '"+document.all('ManageCom').value+"%%' and AvaliReason='02' "
   	        + getWherePart('RptNo','RgtNo')
   	        +"order by rptdate desc";   */
   	mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLGrpReportSimpleInputSql");
		mySql.setSqlId("LLGrpReportSimpleSql4");
		mySql.addSubPara(document.all('ManageCom').value); 
		mySql.addSubPara(fm.RgtNo.value );  
   	turnPage.queryModal(mySql.getString(),PolGrid,1,1);
   		
}

//ȡ��ѡ�����Ŀ
function GetSelvalue()
{
	var tSel = PolGrid.getSelNo();
    document.all('RgtNo').value = PolGrid. getRowColData(tSel-1,1);
    	   	fm.submit();

}


//����������ӡ
function PrintClaimBatch()
{
	var selno = PolGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("��ѡ��Ҫ��ӡ���ⰸ��");
	      return;
	      
	}	
	//add suox 2005-12-27
	var tSel = PolGrid.getSelNo();
        document.all('RgtNo').value = PolGrid. getRowColData(tSel-1,1);
        if (fm.BOX.checked)
				{
					fm.action = "./ClaimPrtPDF_VER1.jsp?operator=batch";
				}
				else
				{
					fm.action = "./ClaimPrtPDF.jsp?operator=batch";
		
				}
        fm.target=".";
	  		fm.submit();
	
}

//֧���嵥
function PrintClaimPayBill()
{
	var selno = PolGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("��ѡ��Ҫ��ӡ���ⰸ��");
	      return;
	}	
	var tSel = PolGrid.getSelNo();
	document.all('RgtNo').value = PolGrid. getRowColData(tSel-1,1);
        if (fm.BOX.checked)
        {
		fm.action = "./ClaimPrtPDF_VER1.jsp?operator=PayBill";
	}
	else
	{
		fm.action = "./ClaimPrtPDF.jsp?operator=PayBill";
		
		}
        fm.target=".";
        fm.submit();
	
}
//֧���嵥EXCEL��
function PrintClaimPayBillExcel()
{
	var selno = PolGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("��ѡ��Ҫ��ӡ���ⰸ��");
	      return;
	}	
	var tSel = PolGrid.getSelNo();
	document.all('RgtNo').value = PolGrid. getRowColData(tSel-1,1);
        if (fm.BOX.checked)
        {
		fm.action = "./ClaimPrtPDF_VER1.jsp?operator=PayBillExcel";
	}
	else
	{
		fm.action = "./ClaimPrtPDF.jsp?operator=PayBillExcel";
		
		}
        fm.target=".";
        fm.submit();
	
}

//����嵥
function PrintClaimGetBill()
{
	var selno = PolGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("��ѡ��Ҫ��ӡ���ⰸ��");
	      return;
	}	
	var tSel = PolGrid.getSelNo();
        document.all('RgtNo').value = PolGrid. getRowColData(tSel-1,1);
	if (fm.BOX.checked)
	{
		fm.action = "./ClaimPrtPDF_VER1.jsp?operator=GetBill";
	}
	else
	{
		fm.action = "./ClaimPrtPDF.jsp?operator=GetBill";
		
		}
        fm.target=".";
	  fm.submit();
}
//����վ�
function PrintGetBill()
{
		var selno = PolGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("��ѡ��Ҫ��ӡ���ⰸ��");
	      return;
	}	
	var tSel = PolGrid.getSelNo();
        document.all('RgtNo').value = PolGrid. getRowColData(tSel-1,1);
	if (fm.BOX.checked)
	{
	fm.action = "./ClaimPrtPDF_VER1.jsp?operator=lksj";
	}
	else
	{
	fm.action = "./ClaimPrtPDF.jsp?operator=lksj";
		
	}
        fm.target=".";
	  fm.submit();
	
}
/*

//�ύ��Ĵ������
function afterSubmita( FlagStr, content )
{
	//alert("ok");
   
	try {
		if(showInfo!=null)
	        showInfo.close();
		}
	catch(e){}
 
	if (FlagStr == "Fail" )
	{
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
//		showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=350;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
		
		showInfo.focus();


	}
	else
	{
    
	    content = "�����ļ��ɹ���";
	    var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
//	    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
  	 	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=350;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
		
		showInfo.focus();


    }

}
*/



//�����嵥Excel��
function  PrintFeeBill()
{
		var selno = PolGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("��ѡ��Ҫ��ӡ���ⰸ��");
	      return;
	}	
	var tSel = PolGrid.getSelNo();
        document.all('RgtNo').value = PolGrid. getRowColData(tSel-1,1);
	if (fm.BOX.checked)
	{
		fm.action = "./ClaimPrtPDF_VER1.jsp?operator=FeeBill";
	}
	else
	{
		fm.action = "./ClaimPrtPDF.jsp?operator=FeeBill";
		}
        fm.target=".";
	  fm.submit();
	
}

//����嵥excel��
function  PrintclaimBill()
{
		var selno = PolGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("��ѡ��Ҫ��ӡ���ⰸ��");
	      return;
	}	
	var tSel = PolGrid.getSelNo();
        document.all('RgtNo').value = PolGrid. getRowColData(tSel-1,1);
	if (fm.BOX.checked)
	{
		fm.action = "./ClaimPrtPDF_VER1.jsp?operator=claimBillExcel";
	}
	else
	{
		fm.action = "./ClaimPrtPDF.jsp?operator=claimBillExcel";
	}
        fm.target=".";
	  fm.submit();
	
}

//��������嵥------------��ͣ
function  PrintClaimPer()
{
		var selno = PolGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("��ѡ��Ҫ��ӡ���ⰸ��");
	      return;
	}	
	var tSel = PolGrid.getSelNo();
        document.all('RgtNo').value = PolGrid. getRowColData(tSel-1,1);
	
	fm.action = "./ClaimPrtPDF.jsp?operator=ClaimPer";
        fm.target=".";
	  fm.submit();
	
}
//�籣�ʵ���ӡ
function  PrintCaseReceiptClass()
{
		var selno = PolGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("��ѡ��Ҫ��ӡ���ⰸ��");
	      return;
	}	
	var tSel = PolGrid.getSelNo();
        document.all('RgtNo').value = PolGrid. getRowColData(tSel-1,1);
	
	fm.action = "./ClaimPrtPDF.jsp?operator=PrintCaseReceiptClass";
        fm.target=".";
	  fm.submit();
	
}

//������Ϣ����
function  PrintReportClass()
{	
	var selno = PolGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("��ѡ��Ҫ�������ⰸ��");
	      return;
	}	
	var tSel = PolGrid.getSelNo();
  document.all('RgtNo').value = PolGrid. getRowColData(tSel-1,1);
	
	fm.action = "./ClaimPrtPDF.jsp?operator=PrintReportClass";
  fm.target=".";
	fm.submit();
	
}

//������Ϣ����2
function  PrintAccReportClass()
{	
	var selno = PolGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("��ѡ��Ҫ�������ⰸ��");
	      return;
	}	
	var tSel = PolGrid.getSelNo();
  document.all('RgtNo').value = PolGrid. getRowColData(tSel-1,1);
	
	fm.action = "./ClaimPrtPDF.jsp?operator=PrintAccReportClass";
  fm.target=".";
	fm.submit();
	
}

//����᰸���浥
function PrintClaimOver()
{
		var selno = PolGrid.getSelNo()-1;
		if (selno <0)
		{
	      alert("��ѡ��Ҫ��ӡ���ⰸ��");
	      return;
		}	
		var tSel = PolGrid.getSelNo();
    document.all('RgtNo').value = PolGrid. getRowColData(tSel-1,1);
			if (fm.BOX.checked)
			{
				//��ӡԭʼ�ļ�
				fm.action = "./ClaimPrtPDF_VER1.jsp?operator=PrintClaimOver";
			}
			else
			{
				//��ӡ���ļ�
				fm.action = "./ClaimPrtPDF.jsp?operator=PrintClaimOver";
			}

    fm.target=".";
	  fm.submit();
}

