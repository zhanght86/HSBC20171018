//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="1";
var turnPage = new turnPageClass();          //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
//var turnPage1 = new turnPageClass();
var sqlresourcename = "bq.PEdorTypeRCInputSql";

function returnParent()
{
  top.opener.getEdorItemGrid();
  top.close();
  top.opener.focus();
}

function edorTypeRCSave()
{
    var tRemindMode = fm.RemindMode.value;
    var tOldRemindMode = fm.OldRemindMode.value;
    
    if(tRemindMode == null || tRemindMode =="")
    {
        alert("��ѡ�����������ѷ�ʽ");
        fm.RemindMode.focus();
        return;
    }
    if(tRemindMode == tOldRemindMode){
    	 alert("����������ѷ�ʽ��ԭ�������ѷ�ʽһ����������");
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

    document.all('fmtransact').value = "INSERT";
    fm.submit();

}


//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
  showInfo.close();
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

    //alert(FlagStr);
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
function showSubmitFrame(cDebug)
{
    if( cDebug == "1" )
        parent.fraMain.rows = "0,0,50,82,*";
    else
        parent.fraMain.rows = "0,0,0,72,*";
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
function initRCQuery()
{
    //��ѯ��¼����
//    var strsql = "select edorstate from lpedoritem where edorno = '" + document.all('EdorNo').value + "' and edortype = 'RC' and contno = '"+document.all('ContNo').value+"'";
    
    var strsql = "";
	var sqlid1="PEdorTypeRCInputSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	mySql1.addSubPara(document.all('EdorNo').value);//ָ������Ĳ���
	mySql1.addSubPara(document.all('ContNo').value);
	strsql=mySql1.getString();
    
    var ret = easyExecSql(strsql);
    var state = ret[0][0];
//    strsql = "select nvl(XQremindflag,'1') from lccont where contno = '"+document.all('ContNo').value+"'";
		
	var sqlid2="PEdorTypeRCInputSql2";
	var mySql2=new SqlClass();
	mySql2.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
	mySql2.addSubPara(document.all('ContNo').value);//ָ������Ĳ���
	strsql=mySql2.getString();
		
		ret = easyExecSql(strsql);
		fm.OldRemindMode.value = ret[0][0];
    if(state != "3")//�ȴ�¼�롣
    {
//    	strsql = "select XQremindflag from lpcont where contno = '"+document.all('ContNo').value+"' and edorno = '"+fm.EdorNo.value+"'";
			
	var sqlid3="PEdorTypeRCInputSql3";
	var mySql3=new SqlClass();
	mySql3.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql3.setSqlId(sqlid3);//ָ��ʹ�õ�Sql��id
	mySql3.addSubPara(document.all('ContNo').value);//ָ������Ĳ���
	mySql3.addSubPara(fm.EdorNo.value);
	strsql=mySql3.getString();
			
			ret = easyExecSql(strsql);
			fm.RemindMode.value = ret[0][0];
    }
    else
    {

    }
    
    if(fm.OldRemindMode.value == '1')
    {
    	fm.OldRemindModeName.value = "��Ҫ��������";
    }else{
    	fm.OldRemindModeName.value = "����Ҫ��������";
    }
    
    if(fm.RemindMode.value == '1')
    {
    	fm.RemindModeName.value = "��Ҫ��������";
    }
    if(fm.RemindMode.value == '0'){
    	fm.RemindModeName.value = "����Ҫ��������";
    }
}



function initDivLayer()
{
    var sAppobj;
    try
    {
        sAppobj = document.getElementsByName("AppObj")[0].value;
    }
    catch (ex) {}
    if (sAppobj != null)
    {
        if (sAppobj.trim() == "I")
        {
            try
            {
                showOneCodeName('PEdorType', 'EdorType', 'EdorTypeName');
                document.all("divGetMoneyTitle").style.display = "";
                document.all("divGetMoneyInput").style.display = "";
            }
            catch (ex) {}
        }
        else if (sAppobj.trim() == "G")
        {
            try
            {
                showOneCodeName('GEdorType', 'EdorType', 'EdorTypeName');
                document.all("divGetMoneyTitle").style.display = "none";
                document.all("divGetMoneyInput").style.display = "none";
            }
            catch (ex) {}
        }
    }
}