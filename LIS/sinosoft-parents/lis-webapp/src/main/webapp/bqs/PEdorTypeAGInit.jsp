<%
//�������ƣ�PEdorTypeAGInit.jsp
//�����ܣ�
//�������ڣ�2005-6-16
//������  ��Nicholas
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->


<script language="JavaScript">
function initInpBox()
{

  try
  {
    //Edorquery();
    document.all('EdorAcceptNo').value = top.opener.document.all('EdorAcceptNo').value;
    document.all('EdorNo').value = top.opener.document.all('EdorNo').value;
    document.all('EdorType').value = top.opener.document.all('EdorType').value;
    document.all('ContNo').value = top.opener.document.all('ContNo').value;
    document.all('InsuredNo').value = top.opener.document.all('InsuredNo').value;
    document.all('PolNo').value = top.opener.document.all('PolNo').value;
    document.all('EdorItemAppDate').value = top.opener.document.all('EdorItemAppDate').value;
    document.all('EdorValiDate').value = top.opener.document.all('EdorValiDate').value;
    showOneCodeName('EdorCode','EdorType','EdorTypeName');

    //easyQueryClick();

  }
  catch(ex)
  {
    alert("��GEdorTypeWTInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }
}

function initSelBox()
{  
  try           
  {
//    setOption("t_sex","0=��&1=Ů&2=����");
//    setOption("sex","0=��&1=Ů&2=����");
//    setOption("reduce_flag","0=����״̬&1=�����");
//    setOption("pad_flag","0=����&1=�潻");
  }
  catch(ex)
  {
    alert("��PEdorTypeWTInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}

function initForm()
{
  try
  {
    initInpBox();
    //initSelBox();
    //easyQueryClick();
    //initLPPolGrid();
    QueryCustomerInfo();
    //formatGetModeAbout();
    //initQuery();
    //ctrlGetEndorse();
    initPolGrid();
    //QueryEdorInfo();
    queryBankInfo();
  }
  catch(re)
  {
    alert("PEdorTypeWTInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

// ������Ϣ�б�ĳ�ʼ��

// ��ѯ��ť
function easyQueryClick()
{


}

function initQuery()
{

}
 function QueryCustomerInfo()
{
	var tContNo=top.opener.document.all('ContNo').value;
	var strSQL=""
	//alert("------"+tContNo+"---------");
	if(tContNo!=null || tContNo !='')
	  {
	 /*  strSQL = "SELECT APPNTNAME,APPNTIDTYPE,APPNTIDNO,INSUREDNAME,INSUREDIDTYPE,INSUREDIDNO FROM LCCONT WHERE 1=1 AND "
							+"CONTNO='"+tContNo+"'"; */
	  
	  
	var sqlid1 = "PEdorTypeAGInitSql1";
  	var mySql1 = new SqlClass();
  	mySql1.setResourceName("bqs.PEdorTypeAGInitSql"); // ָ��ʹ�õ�properties�ļ���
  	mySql1.setSqlId(sqlid1);// ָ��ʹ�õ�Sql��id
  	mySql1.addSubPara(tContNo);// ָ������Ĳ���
  	strSQL = mySql1.getString();
	//alert("-----------"+strSQL+"------------");
	}
	else
	{
		alert('û�пͻ���Ϣ��');
	}
	var arrSelected = new Array();
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
	//arrResult = easyExecSql("select * from LDGrp where GrpNo = '" + arrQueryResult[0][0] + "'", 1, 0);
  if(!turnPage.strQueryResult){
		alert("��ѯʧ��");
	}
	else
	{
  arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
  try {document.all('AppntName').value = arrSelected[0][0];} catch(ex) { }; //Ͷ��������
  try {document.all('AppntIDType').value = arrSelected[0][1];} catch(ex) { }; //Ͷ����֤������
  try {document.all('AppntIDNo').value = arrSelected[0][2];} catch(ex) { }; //Ͷ����֤������
  try {document.all('InsuredName').value = arrSelected[0][3];} catch(ex) { }; //����������
  try {document.all('InsuredIDType').value = arrSelected[0][4];} catch(ex) { }; //������֤������
  try {document.all('InsuredIDNo').value = arrSelected[0][5];} catch(ex) { }; //������֤������

  showOneCodeName('idtype','AppntIDType','AppntIDTypeName');
  showOneCodeName('idtype','InsuredIDType','InsuredIDTypeName');
  }
}

//������Ϣ�б�
function initPolGrid()
{
    var iArray = new Array();

    try
    {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";            		//�п�
      iArray[0][2]=100;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="��ȡ��Ŀ";
      iArray[1][1]="170px";
      iArray[1][2]=100;
      iArray[1][3]=0;

      iArray[2]=new Array();
      iArray[2][0]="��������";
      iArray[2][1]="100px";
      iArray[2][2]=100;
      iArray[2][3]=0;

      iArray[3]=new Array();
      iArray[3][0]="�������";
      iArray[3][1]="100px";
      iArray[3][2]=100;
      iArray[3][3]=0;

      iArray[4]=new Array();
      iArray[4][0]="����֪ͨ�����";
      iArray[4][1]="0px";
      iArray[4][2]=20;
      iArray[4][3]=3;

      iArray[5]=new Array();
      iArray[5][0]="���α���";
      iArray[5][1]="0px";
      iArray[5][2]=10;
      iArray[5][3]=3;

      iArray[6]=new Array();
      iArray[6][0]="�������α���";
      iArray[6][1]="0px";
      iArray[6][2]=6;
      iArray[6][3]=3;

      iArray[7]=new Array();
      iArray[7][0]="������������";
      iArray[7][1]="0px";
      iArray[7][2]=6;
      iArray[7][3]=3;

      iArray[8]=new Array();
      iArray[8][0]="���ֱ���";
      iArray[8][1]="0px";
      iArray[8][2]=10;
      iArray[8][3]=3;

      iArray[9]=new Array();
      iArray[9][0]="��������";
      iArray[9][1]="100px";
      iArray[9][2]=100;
      iArray[9][3]=0;

      iArray[10]=new Array();
      iArray[10][0]="Ӧ������";
      iArray[10][1]="100px";
      iArray[10][2]=100;
      iArray[10][3]=0;

	    PolGrid = new MulLineEnter( "fm" , "PolGrid" );
	    //��Щ���Ա�����loadMulLineǰ
	    //PolGrid.mulLineCount = 3;
	    PolGrid.displayTitle = 1;
	    PolGrid.canChk=0;
	    PolGrid.hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
	    PolGrid.hiddenSubtraction=1;
	    PolGrid.loadMulLine(iArray);
	    PolGrid.selBoxEventFuncName ="" ;
	    //��Щ����������loadMulLine����
	    //��ʾ���ϱ�׼����ȡ����Ϣ
/* 	    var strSql="SELECT distinct (select distinct GetDutyName from LMDutyGetAlive where GetDutyCode=b.GetDutyCode and GetDutyKind=b.GetDutyKind),"
	    				+ " (select count(*) + 1 from LJAGetDraw where PolNo=b.PolNo and DutyCode=b.DutyCode and GetDutyKind=b.GetDutyKind and GetDutyCode=b.GetDutyCode and GetDate<b.GetDate),"
	    				+ " b.GetMoney,b.GetNoticeNo,b.DutyCode,b.GetDutyCode,b.GetDutyKind,b.RiskCode,"
	    				+ " nvl((select decode(GetType1,'0','���ڽ�','1','���','δ֪') from LMDutyGet where GetDutyCode=b.GetDutyCode),'�޼�¼'),"
	    				+ " to_char(b.GetDate,'YYYY-MM-DD')"
	            + " FROM LJAGetDraw b"
	            + " WHERE exists(select 'y' from LPGet where PolNo=b.PolNo and DutyCode=b.DutyCode and GetDutyKind=b.GetDutyKind and GetDutyCode=b.GetDutyCode and GetToDate=b.LastGettoDate and EdorNo='"+document.all('EdorNo').value+"' and EdorType='"+document.all('EdorType').value+"')"
	            + " and (b.RReportFlag='1' or b.ComeFlag='1')"
	            + " and b.GetDate<=to_date('" + document.all('EdorValiDate').value + "','YYYY-MM-DD')"
	            //+ " and not exists(select 'X' from LCPol where PolNo=b.PolNo and GetForm='1')"
							+ " and not exists(select 'A' from LCContState where ContNo=b.ContNo and StateType='Loan' and State='1' and EndDate is null and StartDate<=b.GetDate)"
							+ " and not exists(select 'B' from LCContState where PolNo='" + document.all('PolNo').value + "' and StateType='PayPrem' and State='1' and EndDate is null and StartDate<=b.GetDate)";
														 //alert(strSql);
//			easyQueryVer3Modal(strSql,PolGrid);

 */

	    var sqlid2 = "PEdorTypeAGInitSql2";
	  	var mySql2 = new SqlClass();
	  	mySql2.setResourceName("bqs.PEdorTypeAGInitSql"); // ָ��ʹ�õ�properties�ļ���
	  	mySql2.setSqlId(sqlid2);// ָ��ʹ�õ�Sql��id
	  	mySql2.addSubPara(document.all('EdorNo').value);// ָ������Ĳ���
	  	mySql2.addSubPara(document.all('EdorType').value);// ָ������Ĳ���
	  	mySql2.addSubPara(document.all('EdorValiDate').value);// ָ������Ĳ���
	  	mySql2.addSubPara(document.all('PolNo').value);// ָ������Ĳ���
	  	var strSql = mySql2.getString();
        turnPage.strQueryResult  = easyQueryVer3(strSql, 1, 0, 1);
        //�ж��Ƿ��ѯ�ɹ�
        if (!turnPage.strQueryResult)
        {
            return false;
        }
        //��ѯ�ɹ������ַ��������ض�ά����
        turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
        //���ó�ʼ������MULTILINE����
        turnPage.pageDisplayGrid = PolGrid;
        //����SQL���
        turnPage.strQuerySql = strSQL;
        //���ò�ѯ��ʼλ��
        turnPage.pageIndex = 0;
        //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
        arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
        //����MULTILINE������ʾ��ѯ���
        displayMultiline(arrDataSet, turnPage.pageDisplayGrid);

			//������ȡ���
		  var i;
			var sumGetMoney = 0; //��ȡ���ĺ�
			for (i=0;i<PolGrid.mulLineCount;i++)
			{
					sumGetMoney = sumGetMoney + eval(PolGrid.getRowColData(i,3));
			}
			document.all('MoneyAdd').value = sumGetMoney;
    }
    catch(ex)
    {
      alert(ex);
    }
}

</script>
