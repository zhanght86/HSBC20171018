var showInfo;

var turnPage = new turnPageClass();          //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
var sqlresourcename = "bq.PEdorTypeDBInputSql";


function getCustomerGrid()
{
    var tContNo=document.all("ContNo").value;
    if(tContNo!=null&&tContNo!="")
    {
/*        var strSQL = " Select a.appntno, 'Ͷ����', a.appntname, "
                    +" (select trim(n.code)||'-'||trim(n.CodeName) from LDCode n where trim(n.codetype) = 'sex' and trim(n.code) = trim(appntsex)),"
                    +" a.appntbirthday, "
                    +" (select trim(m.code)||'-'||trim(m.CodeName) from LDCode m where trim(m.codetype) = 'idtype' and trim(m.code) = trim(idtype)), "
                    +" a.idno "
                    +" From lcappnt a Where contno='" + tContNo+"'"
                    +" Union"
                    +" Select i.insuredno, '������', i.name, "
                    +" (select trim(u.code)||'-'||trim(u.CodeName) from LDCode u where trim(u.codetype) = 'sex' and trim(u.code) = trim(sex)),"
                    +" i.Birthday,"
                    +" (select trim(y.code)||'-'||trim(y.CodeName) from LDCode y where trim(y.codetype) = 'idtype' and trim(y.code) = trim(idtype)), "
                    +" i.IDNo "
                    +" From lcinsured i Where contno='" + tContNo+"'";
*/        
    var strSQL = "";
	var sqlid1="PEdorTypeDBInputSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	mySql1.addSubPara(tContNo);//ָ������Ĳ���
	mySql1.addSubPara(tContNo);
	strSQL=mySql1.getString();
        
        arrResult = easyExecSql(strSQL);
        if (arrResult)
        {
            displayMultiline(arrResult,CustomerGrid);
        }
    }
}


function sedorTypeDBSave()
{
  if (PolGrid.mulLineCount <= 0) {
    alert("û�з����������ֺ������֣��ı���!");
    return false;
  }
  
 if(!verify())
 {
    return false;
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
  document.all("fmtransact").value = "EdorSave";
  fm.action="./PEdorTypeDBSubmit.jsp";
  fm.submit();
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{

    showInfo.close();
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

    if (FlagStr == "Success")
    {
      queryBackFee();
    }
}


function returnParent()
{
    top.close();
    top.opener.focus();
    top.opener.getEdorItemGrid();
}

function showNotePad()
{
    var MissionID = top.opener.document.all("MissionID").value;
    var SubMissionID = top.opener.document.all("SubMissionID").value;;
    var ActivityID = "0000000003";
    var OtherNo = top.opener.document.all("OtherNo").value;;

    var OtherNoType = "1";
    if(MissionID == null || MissionID == "")
    {
        alert("MissionIDΪ�գ�");
        return;
    }
    var varSrc= "&MissionID="+ MissionID + "&SubMissionID="+ SubMissionID + "&ActivityID="+ ActivityID + "&PrtNo="+ OtherNo + "&NoType="+ OtherNoType;
    var newWindow = OpenWindowNew("../uw/WorkFlowNotePadFrame.jsp?Interface= ../uw/WorkFlowNotePadInput.jsp" + varSrc,"���������±��鿴","left");
}


function Edorquery()
{
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



function afterCount(tFlagStr,tContent,tMulArray)
{
        //��ʼ��MulLine
        if (tFlagStr == "Success")
        {
           displayMultiline(tMulArray,PolGrid,turnPage);
        }else
        	{
        		
          var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + tContent ;
           //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:300px");
		   var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
			var iWidth=550;      //�������ڵĿ��; 
			var iHeight=250;     //�������ڵĸ߶�; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
			showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
       		       		
        }

}


function verify()
{
	var k=0;
  for (i=0; i<PolGrid.mulLineCount; i++)
  {
     
     if (PolGrid.getChkNo(i) && (PolGrid.getRowColData(i, 7) == null || PolGrid.getRowColData(i, 7)=='')) {
     
     alert("��" + (i+1) + "����¼����ȡ��");
        return false;
        
     }
      if (PolGrid.getChkNo(i)&& (parseFloat(PolGrid.getRowColData(i, 7)) > PolGrid.getRowColData(i, 6)))
      {
        alert("��" + (i+1) + "��ʵ����ڿ����������ȷ�Ͻ�");
        return false;
      } if (PolGrid.getChkNo(i)&& (parseFloat(PolGrid.getRowColData(i, 7))<=0))
      {
        alert("��" + (i+1) + "���޿�������������ȡ���С�ڵ���0,�뷵�أ�");
        return false;
      }else if(PolGrid.getChkNo(i))
      	{
           k++;
       }   
  }
  if(k==0)
  {
  	    alert("������ѡ��һ����¼��Ҫô�뷵��");
        return false;
  	}
  
  return true;
}