//���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="1";
var turnPage = new turnPageClass();          //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
var sqlresourcename = "bq.PEdorTypeENInputSql";
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes;";

//�˺���δʹ��
/*
function edorTypePTReturn()
{
		initForm();
}
*/


function edorTypeENSave()
{
	  var row = PolGrid.mulLineCount;
    //�����ж���ѡ�����ֱ��ʱ�����ѡ�����գ���Ҫ�󽫸�����һ��ѡ��
    var tFlag=false;
    var i = 0;
      for(var m = 0; m < row; m++ )
      {
        if(PolGrid.getChkNo(m))
        {
         var riskcode = PolGrid.getRowColData(m,1);
		     var mainRiskcode = PolGrid.getRowColData(m,3);	
         if(riskcode==mainRiskcode)
         {
        	tFlag=true;
         }
          i = i+1;
        var rnewFlag = PolGrid.getRowColData(m,7);
				if(rnewFlag == '' || rnewFlag == null)
				{
					alert("��"+(m+1)+"�е���������״̬���δ��ѡ����ѡ��");
					return;
				}  
        }
      }
      if(i == 0)
      {
        alert("��ѡ����Ҫ��������֣�");
        return false;
      }
      //
      var tNoChek=false;
      for(var k = 0; k < row;k++ )
      {
        if(!PolGrid.getChkNo(k))
        {
         var riskcode = PolGrid.getRowColData(k,1);
		     var mainRiskcode = PolGrid.getRowColData(k,3);	
         if(riskcode!=mainRiskcode)
         {
          tNoChek=true;
         }
       }
      }
      if(tNoChek&&tFlag)
      {
        alert("�Ѿ�ѡ�����գ���ѡ�񸽼���һ����");
        return false;
      }	
	//return;


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
  document.all('fmtransact').value = "INSERT||MAIN";
  fm.submit();
}

function customerQuery()
{
//	window.open("./LCAppntIndQuery.html");
	window.open("./LCAppntIndQuery.html","",sFeatures);
}

//�ύ�����水ť��Ӧ����
//�˺���δʹ��
/*
function submitForm()
{
  var i = 0;
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");

  initLCAppntIndGrid();
 //  showSubmitFrame(mDebug);
  fm.submit(); //�ύ
}
*/

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content,Result )
{
  showInfo.close();
  if (FlagStr == "Success" )
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
    /***************��������������������Ϊ1**********************/
    var rowNum=PolGrid.mulLineCount;
    for (i=0;i<rowNum;i++)
		{
			if(PolGrid.getChkNo(i))
			{
				PolGrid.setRowColData(i,9,"1")
			}
		}
    
  }
  else if (FlagStr == "Fail" )
  {
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content + "  ����ȷ���������±��棡" ;
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
var iWidth=550;      //�������ڵĿ��; 
var iHeight=250;     //�������ڵĸ߶�; 
var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

showInfo.focus();
  }
 

}

//�ύǰ��У�顢����
function beforeSubmit()
{
  //��Ӳ���
}

//---------------------------
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
 /*
function showSubmitFrame(cDebug)
{
	if( cDebug == "1" )
		parent.fraMain.rows = "0,0,50,82,*";
	else
		parent.fraMain.rows = "0,0,0,72,*";
}
*/
/*********************************************************************
 *  ��ѯ������ϸ��Ϣʱִ�еĺ���,��ѯ����һ��2ά���飬�����±��[0][0]��ʼ
 *  ����  ��  ��ѯ���صĶ�ά����
 *  ����ֵ��  ��
 *********************************************************************
 */
/*
function afterQuery( arrQueryResult )
{

}
*/

function returnParent()
{
    top.opener.initEdorItemGrid();
    top.opener.getEdorItemGrid();
    top.close();
}


function showNotePad()
{
	var MissionID = top.opener.document.all("MissionID").value;
	var SubMissionID = top.opener.document.all("SubMissionID").value;
	var ActivityID = '0000000003';
	var OtherNo = top.opener.document.all("OtherNo").value;

	var OtherNoType = "1";
	if(MissionID == null || MissionID == "")
	{
		alert("MissionIDΪ�գ�");
		return;
	}
	var varSrc= "&MissionID="+ MissionID + "&SubMissionID="+ SubMissionID + "&ActivityID="+ ActivityID + "&PrtNo="+ OtherNo + "&NoType="+ OtherNoType;
	var newWindow = OpenWindowNew("../uw/WorkFlowNotePadFrame.jsp?Interface= ../uw/WorkFlowNotePadInput.jsp" + varSrc,"���������±��鿴","left");
}
function QueryEdorInfo()
{
	var tEdortype=top.opener.document.all('EdorType').value;
	if(tEdortype!=null || tEdortype !='')
	{
//	var strSQL="select distinct edorcode, edorname from lmedoritem where edorcode = '" + tEdortype + "'";
    
    var strSQL = "";
	var sqlid1="PEdorTypeENInputSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	mySql1.addSubPara(tEdortype);//ָ������Ĳ���
	strSQL=mySql1.getString();
    
    }
    else
	{
		alert('δ��ѯ����ȫ������Ŀ��Ϣ��');
	}
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
	var arrSelected = new Array();
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
	arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
    try {document.all('EdorType').value = arrSelected[0][0];} catch(ex) { }; //ְҵ���
    try {document.all('EdorTypeName').value = arrSelected[0][1];} catch(ex) { }; //ְҵ���
}
function Edorquery()
{
	//alert("$$$$$$$$$")
	var ButtonFlag = top.opener.document.all('ButtonFlag').value;
	if(ButtonFlag!=null && ButtonFlag=="1")
    {
       divEdorquery.style.display = "none";
    }
    else
 	{
 		divEdorquery.style.display = "";
 	}
}

function checkMainPol(spanId)
{
	var rowNum=PolGrid.mulLineCount;//������
	var rowLine = spanId.substr(11,1); //��ǰ��ѡ�е�������
	
	if(document.all(spanId).all('InpPolGridChk').value=='1')
	{
		var riskcode = PolGrid.getRowColData(rowLine,1);
		var mainRiskcode = PolGrid.getRowColData(rowLine,3);
		//alert(riskcode+"||"+mainRiskcode);
		if(riskcode==mainRiskcode)
		{
			var i;
			for(i = 0;i<rowNum;i++)
			{
				var tMainRiskCode = PolGrid.getRowColData(i,3);
				var tRiskCode = PolGrid.getRowColData(i,1);
				var hasAdd = false;
				if(tMainRiskCode == mainRiskcode && tRiskCode != tMainRiskCode)
				{
					//alert(tRiskCode);
					PolGrid.checkBoxSel(i+1);
					hasAdd = true;
				}
			}
			//if(hasAdd)
			//{
			//	alert("��ʾ����ѡ�е��Ƕ������գ���Ҫ�����������µĸ�����һ������");
			//}
		}
		else
		{
			var i;
			var isShortPol = false;
			for(i = 0; i< rowNum; i++)
			{
				var tMainRiskCode = PolGrid.getRowColData(i,1);
				if(tMainRiskCode == mainRiskcode)
				{
					isShortPol = true;
					PolGrid.checkBoxSel(i+1);
				}
			}
			if(isShortPol)
			{
				var i;
				/***************�п��ܴ��ڶ����������ж�����ڸ����յĿ���*********************/
				for(i = 0;i < rowNum; i++)
				{
					var tRiskcode = PolGrid.getRowColData(i,1) 
					var tMainRiskcode = PolGrid.getRowColData(i,3);
					if(tMainRiskcode == mainRiskcode && tRiskcode != tMainRiskcode)
					{
						PolGrid.checkBoxSel(i+1);
					}
				}
				alert("��ʾ����ѡ�е��Ƕ��ڸ����գ���Ҫ��������������һ������");	
				return ;
			}
		}
	}
	
	if(document.all(spanId).all('InpPolGridChk').value=='0')
	{
		var hasAppSaveFlag = PolGrid.getRowColData(rowLine,9);
		//alert(hasAppSaveFlag);
		if(hasAppSaveFlag == '1')
		{
			
			if(!confirm("��ʾ�����ѶԸ����ֽ����˱�����������ȷ��ȥ����ѡ�"))
			{
				PolGrid.checkBoxSel(rowLine+1);
				return;
			}
			
		}
		var riskcode = PolGrid.getRowColData(rowLine,1);
		var mainRiskcode = PolGrid.getRowColData(rowLine,3);
		//alert(riskcode+"||"+mainRiskcode);
		if(riskcode==mainRiskcode)
		{
			var i;
			//alert("��ʾ����ѡ�е��Ƕ������գ���Ҫ�����������µĸ�����һ������");
			for(i = 0;i<rowNum;i++)
			{
				var tRiskCode = PolGrid.getRowColData(i,3);
				if(tRiskCode == mainRiskcode)
				{
					//alert(tRiskCode);
					PolGrid.checkBoxNotSel(i+1);
				}
			}
		}
		else
		{
			var i;
			var isShortPol = false;
			for(i = 0; i< rowNum; i++)
			{
				var tRiskCode = PolGrid.getRowColData(i,1);
				if(tRiskCode == mainRiskcode)
				{
					isShortPol = true;
					PolGrid.checkBoxNotSel(i+1);
				}
			}
			if(isShortPol)
			{
				var i;
				/***************�п��ܴ��ڶ����������ж�����ڸ����յĿ���*********************/
				for(i = 0;i < rowNum; i++)
				{
					var tRiskcode = PolGrid.getRowColData(i,1) 
					var tMainRiskcode = PolGrid.getRowColData(i,3);
					if(tMainRiskcode == mainRiskcode && tRiskcode != tMainRiskcode)
					{
						//alert("ȡ�����ж��ڸ����յĸ�ѡ��");
						PolGrid.checkBoxNotSel(i+1);
					}
				}
				
			}
		}
	}

	//alert("һ��������˸����ݿ⣡LMRisk,LMRiskApp,LMRiskedoritem��")
	
}

function verify()
{
	var k=0;
  for (i=0; i<PolGrid.mulLineCount; i++)
  {
     
      if (PolGrid.getChkNo(i)&& (parseFloat(PolGrid.getRowColData(i, 7)) > PolGrid.getRowColData(i, 6)))
      {
        alert("��" + (i+1) + "��ʵ����ڿ����������ȷ�Ͻ�");
        return false;
      } if (PolGrid.getChkNo(i)&& (parseFloat(PolGrid.getRowColData(i, 7))<=0))
      {
        alert("��" + (i+1) + "���޿������,�뷵�أ�");
        return false;
      }else if(PolGrid.getChkNo(i))
      	{
           k++;
       }   
  }
  if(k==0)
  {
  	    alert("������ѡ��һ��Ҫ���Ļ����¼��Ҫô�뷵��");
        return false;
  	}
  
  return true;
}