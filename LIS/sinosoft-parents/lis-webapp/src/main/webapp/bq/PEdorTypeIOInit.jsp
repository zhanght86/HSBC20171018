
<%
	//PEdorTypeICInit.jsp
	//�����ܣ�ְҵ�����
	//�������ڣ�2005-5-17 11:48����
	//������  ��Lihs
	//���¼�¼��  ������    ��������     ����ԭ��/����
%>

<script language="JavaScript">

function initForm()
{
  try
  {
    Edorquery();
    initInpBox();
    initPolOldGrid();
    initImpartGrid();
    if (fm.AppObj.value != "G")
    {
        QueryCustomerInfo();
        divPerson.style.display = "";
    }
    queryInsuredOccupationInfo();
    queryPolInfo();
    initPolNewGrid();
    querychgOcc();
    querytImpartGrid();
  }
  catch(re)
  {
    alert("PEdorTypeIOInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

function initImpartGrid() {
    var iArray = new Array();

    try {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";            		//�п�
      iArray[0][2]=10;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="��֪���";         		//����
      iArray[1][1]="60px";            		//�п�
      iArray[1][2]=60;            			//�����ֵ
      iArray[1][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[1][4]="impartver";
      //add by jiaqiangli 2009-03-12 ��ȫֻȡ������֪������֪
      iArray[1][15]="1";
      iArray[1][16]="#1# and code in (#A01#,#A02#)";
      iArray[1][18]=300;
      iArray[1][19]=1;

      iArray[2]=new Array();
      iArray[2][0]="��֪����";         		//����
      iArray[2][1]="60px";            		//�п�
      iArray[2][2]=60;            			//�����ֵ
      iArray[2][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[2][4]="ImpartCode";
      iArray[2][5]="2|3|4";
      iArray[2][6]="0|1|2";
      iArray[2][9]="��֪����|len<=4";
      iArray[2][15]="ImpartVer";
      //iArray[2][16]="#02#";
      iArray[2][17]="1";
      iArray[2][18]=700;

      iArray[3]=new Array();
      iArray[3][0]="��֪����";         		//����
      iArray[3][1]="250px";            		//�п�
      iArray[3][2]=200;            			//�����ֵ
      iArray[3][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="��д����";         		//����
      iArray[4][1]="150px";            		//�п�
      iArray[4][2]=150;            			//�����ֵ
      iArray[4][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[4][9]="��д����|len<=200";

      ImpartGrid = new MulLineEnter( "fm" , "ImpartGrid" );
      //��Щ���Ա�����loadMulLineǰ
      ImpartGrid.mulLineCount = 0;
      ImpartGrid.displayTitle = 1;
      //ImpartGrid.tableWidth   ="500px";
      ImpartGrid.loadMulLine(iArray);

      //��Щ����������loadMulLine����
      //ImpartGrid.setRowColData(1,1,"asdf");
    }
    catch(ex) {

    }
}

function initInpBox()
{
    try
    {
        document.all('EdorAcceptNo').value = top.opener.document.all('EdorAcceptNo').value;
        document.all('ContNo').value = top.opener.document.all('ContNo').value;
        //document.all('EdorType').value = top.opener.document.all('EdorType').value;
        document.all('EdorItemAppDate').value = top.opener.document.all('EdorItemAppDate').value;
        //document.all('EdorValiDate').value = top.opener.document.all('EdorValiDate').value;
        document.all('InsuredNo').value = top.opener.document.all('InsuredNo').value;
        document.all('EdorNo').value = top.opener.document.all('EdorNo').value;
        document.all('EdorType').value = top.opener.document.all('EdorType').value;
        try{document.all('AppObj').value = top.opener.document.all('AppObj').value; }catch(ex){}
        showOneCodeName('EdorType', 'EdorType', 'EdorTypeName');
    }
    catch(ex)
    {
        alert("��PEdorTypeIOInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
    }
}

//������Ϣ�б�
function initPolOldGrid()
{
    var iArray = new Array();
    try
    {
        iArray[0]=new Array();
        iArray[0][0]="���";                    //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
        iArray[0][1]="30px";                    //�п�
        iArray[0][2]=10;                        //�����ֵ
        iArray[0][3]=0;                         //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[1]=new Array();
        iArray[1][0]="���ִ���";
        iArray[1][1]="60px";
        iArray[1][2]=100;
        iArray[1][3]=0;

         iArray[2]=new Array();
        iArray[2][0]="��������";
        iArray[2][1]="120px";
        iArray[2][2]=100;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="��������/����";
        iArray[3][1]="60px";
        iArray[3][2]=100;
        iArray[3][3]=0;
        iArray[3][21]=3;

        iArray[4]=new Array();
        iArray[4][0]="���ѱ�׼";
        iArray[4][1]="40px";
        iArray[4][2]=100;
        iArray[4][3]=7;
        iArray[4][21]=3;
        iArray[4][23]="0";

        iArray[5]=new Array();
        iArray[5][0]="ְҵ�ӷ�";
        iArray[5][1]="40px";
        iArray[5][2]=100;
        iArray[5][3]=7;
        iArray[5][21]=3;
        iArray[4][23]="0";
        
        iArray[6]=new Array();
				iArray[6][0]="����";
				iArray[6][1]="60px";
				iArray[6][2]=100;
				iArray[6][3]=2;
				iArray[6][4]="currency";

        PolOldGrid = new MulLineEnter( "fm" , "PolOldGrid" );
        //��Щ���Ա�����loadMulLineǰ
        PolOldGrid.mulLineCount = 0;
        PolOldGrid.displayTitle = 1;
        PolOldGrid.canSel=0;
        //PolOldGrid.canChk=1;
        PolOldGrid.hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
        PolOldGrid.hiddenSubtraction=1;
        PolOldGrid.loadMulLine(iArray);
        //��Щ����������loadMulLine����
    }
    catch(ex)
    {
        alert(ex);
    }
}

function initQuery()
{
    var i = 0;
    var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

    document.all('fmtransact').value = "QUERY||DETAIL";
    mFlag = "N";
    fm.submit();
}

function CondQueryClick()
{
    var i = 0;
    var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

    document.all('fmtransact').value = "QUERY||MAIN";
    var tContNo = document.all('ContNo').value;
    var tCustomerNo=document.all('CustomerNo1').value;

    if (tContNo==null&&tContNo==''&&tCustomerNo!=null&&tCustomerNo!='')
        mFlag = "P";
    else if(tCustomerNo==null&&tCustomerNo==''&&tContNo!=null&&tContNo!='')
        mFlag = "C";
    else if (tCustomerNo==null&&tCustomerNo==''&&tContNo==null&&tContNo=='')
        mFlag = "N";
    else
        mFlag = "A";
    fm.submit();
}

function detailQueryClick()
{
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    document.all('fmtransact').value = "QUERY||DETAIL";

    var tSel=LCInsuredGrid.getSelNo();

    if (tSel==0||tSel==null)
        alert("�����ǿռ�¼!");
    else
    {
        var tCustomerNo =LCInsuredGrid.getRowColData(tSel-1,2);
        document.all('CustomerNo1').value =tCustomerNo;
        document.all('ContNo').value =LCInsuredGrid.getRowColData(tSel-1,1);
        fm.submit();
    }
}

function newOccupatioinInfo(){
//      var strsql = "select OccupationCode,OccupationType from lpinsured where 1=1 and EdorNo = '"+top.opener.document.all('EdorNo').value+"' and EdorType = '"+top.opener.document.all('EdorType').value+"' and insuredno = '"+top.opener.document.all('InsuredNo').value+"'";
     var strsql = "";
     var sqlid1="PEdorTypeIOInputSql14";
     var mySql1=new SqlClass();
     mySql1.setResourceName("bq.PEdorTypeIOInputSql"); //ָ��ʹ�õ�properties�ļ���
     mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
     mySql1.addSubPara(top.opener.document.all('EdorNo').value);//ָ������Ĳ���
     mySql1.addSubPara(top.opener.document.all('EdorType').value);//ָ������Ĳ���
     mySql1.addSubPara(top.opener.document.all('InsuredNo').value);//ָ������Ĳ���
     strsql=mySql1.getString();
     var aResult = easyExecSql(strsql,1,0);
   if(aResult != null){
      document.all('OccupationCode').value = aResult[0][0];
      document.all('OccupationType').value = aResult[0][1];
   }
}

function initDiv()
{
    divLPInsuredDetail.style.display ='none';
    divDetail.style.display='none';
}

//������Ϣ�б�
function initPolNewGrid()
{
    var iArray = new Array();
    try
    {
       iArray[0]=new Array();
        iArray[0][0]="���";                    //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
        iArray[0][1]="30px";                    //�п�
        iArray[0][2]=10;                        //�����ֵ
        iArray[0][3]=0;                         //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[1]=new Array();
        iArray[1][0]="���ִ���";
        iArray[1][1]="60px";
        iArray[1][2]=100;
        iArray[1][3]=0;

         iArray[2]=new Array();
        iArray[2][0]="��������";
        iArray[2][1]="120px";
        iArray[2][2]=100;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="��������/����";
        iArray[3][1]="60px";
        iArray[3][2]=100;
        iArray[3][3]=0;
        iArray[3][21]=3;

        iArray[4]=new Array();
        iArray[4][0]="���ѱ�׼";
        iArray[4][1]="40px";
        iArray[4][2]=100;
        iArray[4][3]=7;
        iArray[4][21]=3;
        iArray[4][23]="0";

        iArray[5]=new Array();
        iArray[5][0]="ְҵ�����/�˷�";
        iArray[5][1]="40px";
        iArray[5][2]=100;
        iArray[5][3]=7;
        iArray[5][21]=3;
        iArray[5][23]="0";
        
        iArray[6]=new Array();
				iArray[6][0]="����";
				iArray[6][1]="60px";
				iArray[6][2]=100;
				iArray[6][3]=2;
				iArray[6][4]="currency";

        PolNewGrid = new MulLineEnter("fm", "PolNewGrid");
        //��Щ���Ա�����loadMulLineǰ
        PolNewGrid.mulLineCount = 0;
        PolNewGrid.displayTitle = 1;
        PolNewGrid.canSel=0;
        //PolOldGrid.canChk=1;
        PolNewGrid.hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
        PolNewGrid.hiddenSubtraction=1;
        PolNewGrid.loadMulLine(iArray);
        //��Щ����������loadMulLine����
      }
      catch(ex)
      {
          alert(ex);
      }
}

</script>
