
//�������ƣ�LLCaseBackMiss.js
//�����ܣ���������ϵ���ز���
//�������ڣ�2005-10-20 11:50
//������  ��wanzh
//���¼�¼��

//���ļ��а����ͻ�����Ҫ����ĺ������¼�
var showInfo;
var mDebug="0";
var mSwitch = parent.VD.gVSwitch;
var turnPage = new turnPageClass();  //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
var turnPage2 = new turnPageClass();
var mySql = new SqlClass();

//�ύ��ɺ���������
function afterSubmit( FlagStr, content )
{
    showInfo.close();
    if (FlagStr == "Fail" )
    {
        var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
        //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
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
        var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
        //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=350;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
        //ֱ��ȡ��������ת����������
        location.href="LLAppealInput.jsp?claimNo="+fm.ClmNo.value;
    }
    tSaveFlag ="0";
}

//�ύ����
function submitForm()
{
    var i = 0;
    var showStr = "���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    //showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    document.getElementById("fm").submit(); //�ύ
    tSaveFlag ="0";
}

/**=========================================================================
    �޸�״̬����ʼ
    �޸�ԭ�򣺳�ʼ�����1����ʾ��ѡ�ⰸ�б�
    �� �� �ˣ������
    �޸����ڣ�2005.10.21
   =========================================================================
**/

function queryGrid()
{
	if(fm.RptNo.value==null||fm.RptNo.value=="")
	{
	      alert("��¼��Ҫ���˵��ⰸ�ţ�");
	      return;
	}
	
	//2009-01-09 zhangzheng ���Ӷ�δ����������Ϣʱ�Ļ��˴���(�᰸����ǰ���˵���˽׶�)
    /*var strSQL = "select k1,k2,k3,k4,k5,k6,k7,k8 from"
               + "( "
               + "  select rgtno k1,  rgtantname k2,  RgtDate k3, assigneename k4,   (case clmstate  when '50' then '�᰸' when '60' then  '���'  end) k5, accidentdate k6,"	  
               + "  endcasedate k7,  '0' k8"
               + "  from llregister where 1 = 1"
               + getWherePart( 'RgtNo' ,'RptNo')
               + getWherePart( 'RgtantName' ,'RptorName','like')
               + getWherePart( 'RgtDate' ,'RgtDate')
               + getWherePart( 'accidentdate' ,'accidentdate')
               + getWherePart( 'endcasedate' ,'endcasedate')
               + getWherePart( 'AssigneeName' ,'AssigneeName','like')
               + " and MngCom like '" + fm.ManageCom.value + "%%'"
               + " and not exists(select 1 from llcaseback b where b.clmno=llregister.rgtno and backstate='0')"
               + " and ClmState in('60','50')"   
               + " and RgtState <> '13' "//RgtState 13 ���߰���
               + " and RgtClass='1'"
               + " )"
               + " order by k1 ";*/
	
		mySql = new SqlClass();
		mySql.setResourceName("claim.LLCaseBackMissInputSql");
		mySql.setSqlId("LLCaseBackMissSql1");
		mySql.addSubPara(  fm.RptNo.value  );
		mySql.addSubPara(  fm.RptorName.value  );
		mySql.addSubPara(  fm.RgtDate.value  );
		mySql.addSubPara(  fm.accidentdate.value  );
		mySql.addSubPara(  fm.endcasedate.value  );
		mySql.addSubPara(  fm.AssigneeName.value  );
		mySql.addSubPara(  fm.ManageCom.value  );

    //prompt("���˹����ѯ�����ⰸsql",strSQL);
    turnPage.queryModal(mySql.getString(),LLCaseBackGrid);
}
/**=========================================================================
    �޸�״̬����ʼ
    �޸�ԭ����ӦLLCaseBackGrid����¼���ѡ��һ���ⰸ��¼
    �� �� �ˣ������
    �޸����ڣ�2005.10.21
   =========================================================================
**/

function LLCaseBackGridClick()
{
}


/**=========================================================================
    �޸�״̬����ʼ
    �޸�ԭ��[����]��ť�����밸������ԭ����д
    �� �� �ˣ������				
    �޸����ڣ�2005.10.21
   =========================================================================
**/

function CaseBackClaim()
{
	var selno = LLCaseBackGrid.getSelNo() - 1;
	if (selno <0)
	{
	      alert("��ѡ��Ҫ���˴�����ⰸ��");
	      return;
	}
	
	var tClmNo = LLCaseBackGrid.getRowColData(selno,1);
	var tBackType = LLCaseBackGrid.getRowColData(selno,8);

	
	var strSQL="";
	var tCount="";
	
	//2009-03-11 zhangzheng ��������ⰸû�н᰸,��Ҫ�ж����Ƿ��Ѿ���Ԥ���ⰸ,�����ͬ����Ϊ�Ѹ���
	if(tBackType=="1")
	{
		//strSQL = "select count(1) from ljagetclaim where feeoperationtype='B' and otherno='"+tClmNo+"'";
		mySql = new SqlClass();
		mySql.setResourceName("claim.LLCaseBackMissInputSql");
		mySql.setSqlId("LLCaseBackMissSql2");
		mySql.addSubPara(  tClmNo);
		//prompt("У���Ƿ����Ԥ�����sql",strSQL)
	    tCount = easyExecSql(mySql.getString());
	    if (tCount != "0")
	    {
	    	tBackType='0';
	    }
	}
	
	//alert(tBackType);

	
	//У���Ƿ��д˰����������ڴ���Ļ��˰���
	/*strSQL = "select count(1) from llcaseback "
                + " where clmno = '" + tClmNo + "'"
                + " and backstate = '0'";*/
   		mySql = new SqlClass();
		mySql.setResourceName("claim.LLCaseBackMissInputSql");
		mySql.setSqlId("LLCaseBackMissSql3");
		mySql.addSubPara(  tClmNo);
		
    tCount = easyExecSql(mySql.getString());
    if (tCount != "0")
    {
        alert("�˰�������δ��ɵĻ���,����������!");
        return;
    }
	
	location.href="LLCaseBackInput.jsp?claimNo="+tClmNo+"&SubmitFlag=0&BackType="+tBackType+"";
	
}

/**=========================================================================
    �޸�״̬����ʼ
    �޸�ԭ��δ��ɵĻ��˹�������
    �� �� �ˣ������
    �޸����ڣ�2005.11.23
   =========================================================================
**/
function querySelfGrid()
{
   /* var strSQL = "select backno ,clmno ,backdate ,backdesc,backtype from llcaseback "
	         + " where 1=1 and operator = '" + fm.Operator.value + "'"
	         + " and backstate = '0'"
	         + " order by backno ";*/
	mySql = new SqlClass();
	mySql.setResourceName("claim.LLCaseBackMissInputSql");
	mySql.setSqlId("LLCaseBackMissSql4");
	mySql.addSubPara(   fm.Operator.value );
	//prompt("�ѱ�����˼�¼�İ�����ѯ",strSQL);
    turnPage2.queryModal(mySql.getString(),SelfLLCaseBackGrid);
}

/**=========================================================================
    �޸�״̬����ʼ
    �޸�ԭ����Ӧδ������ɵĹ������еĺ���SelfLLCaseBackGridClick
    �� �� �ˣ������
    �޸����ڣ�2005.11.23
   =========================================================================
**/
function SelfLLCaseBackGridClick()
{
    var i = SelfLLCaseBackGrid.getSelNo();
    if (i != '0')
    {
        i = i - 1;
        
        var tBackNo   = SelfLLCaseBackGrid.getRowColData(i,1);
        var tClmNo    = SelfLLCaseBackGrid.getRowColData(i,2);
        var tBackDesc = SelfLLCaseBackGrid.getRowColData(i,4);
    	var tBackType = SelfLLCaseBackGrid.getRowColData(i,5);
    	//alert(tBackType);
        location.href = "LLCaseBackInput.jsp?claimNo="+tClmNo+"&SubmitFlag=1&BackType="+tBackType+"";
    }
}
