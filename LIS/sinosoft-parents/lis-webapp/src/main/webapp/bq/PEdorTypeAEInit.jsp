<%
//PEdorTypeAEInit.jsp
//�����ܣ�
//�������ڣ�2002-06-19 11:10:36
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<%
     //���ҳ��ؼ��ĳ�ʼ����
%>
<script language="JavaScript">
var i=2;
var j=4;
var turnPage = new turnPageClass();          //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
var mySql=new SqlClass();

function initForm()
{
  try
  {
    initInpBox();
    initImpartGrid();
    initInsuredGrid();
    //initQuery();
     getInsuredInfo();
    newOccupatioinInfo();
    //QueryEdorInfo();
    //showCustomerImpart();
    AppntInfShow();
    showLPAppnt(); //��ѯ�Ѿ�¼���Ͷ���˱����Ϣ
    showimpart();
    queryLRInfo();
  }
  catch(re)
  {
    alert("EdorTypeAEInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

function initInpBox()
{
  try
  {
	    Edorquery();
	    document.all('EdorAcceptNo').value = top.opener.document.all('EdorAcceptNo').value;
	    document.all('EdorNo').value = top.opener.document.all('EdorNo').value;
	    document.all('ContNo').value = top.opener.document.all('ContNo').value;
	    document.all('EdorType').value = top.opener.document.all('EdorType').value;
	    document.all('EdorItemAppDate').value = top.opener.document.all('EdorItemAppDate').value;
	    document.all('EdorValiDate').value = top.opener.document.all('EdorValiDate').value;
	    showOneCodeName('PEdorType','EdorType','EdorTypeName');

     	document.all('EdorReason').value = '';       //���ԭ��
        document.all('deathdate').value = '';        //��������
        document.all('AppntNo').value = '';          //Ͷ���˿ͻ���
        document.all('LastName').value = '';         //Ͷ����-��
        document.all('FirstName').value = '';        //Ͷ����-��
        document.all('AppntName').value = '';        //Ͷ����-����
        document.all('LastNameEn').value = '';       //Ӣ��-��
        document.all('FirstNameEn').value = '';      //Ӣ��-��
        document.all('LastNamePY').value = '';       //ƴ��-��
        document.all('FirstNamePY').value = '';      //ƴ��-��
        document.all('AppntIDType').value = '';      //֤�����ͱ���
        document.all('AppntIDTypeName').value = '';  //֤����������
        document.all('AppntIDNo').value = '';        //֤������
        document.all('AppntSex').value = '';         //Ͷ�����Ա����
        document.all('AppntSexName').value = '';     //Ͷ�����Ա�����
        document.all('AppntBirthday').value = '';    //��������
        document.all('AppntMarriage').value = '';    //����״������
        document.all('AppntMarriageName').value = '';  //����״������
        document.all('AppntLanguage').value = '';      //���Ա���
        document.all('AppntLanguageName').value = '';  //��������
        document.all('AppntNativePlace').value = '';   //��������
        document.all('AppntNativePlaceName').value = '';  //��������
        document.all('AppntRgtAddress').value = '';     //�������ڵ�
        document.all('AppntWorkType').value='';         //ְҵ
        document.all('AppntOccupationCode').value = '';  //ְҵ����
        document.all('AppntOccupationCodeName').value = '';  //ְҵ����
        document.all('AppntOccupationType').value='';      //ְҵ���
        document.all('AppntOccupationTypeName').value='';  //ְҵ������
        document.all('PluralityType').value='';          //��ְ
        document.all('AddressNo').value = '';            //��ַ����
        document.all('PostalAddress').value = '';        //ͨѶ��ַ
        document.all('ZipCode').value = '';              //ͨѶ��ַ�ʱ�
        document.all('HomeAddress').value = '';         //סַ
        document.all('HomeZipCode').value = '';         //סַ�ʱ�
        document.all('Mobile').value = '';              //��ѡ�طõ绰
        document.all('GrpPhone').value = '';            //������ϵ�绰
        document.all('GrpName').value = '';             //������λ
        document.all('EMail').value = '';               //����
        document.all('GetBankCode').value = '';        //������
        document.all('BankName').value='';             //����������
        document.all('GetBankAccNo').value = '';       //�˻�����
        document.all('GetAccName').value = '';         //�˻�����
  }
  catch(ex)
  {
    alert("��PEdorTypeAEInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }
}

//��ʼ����ѯ
function initQuery()
{
//   var strSQL ="select * from LPAppnt,LPAddress where LPAppnt.EdorNo=LPAddress.EdorNo and"
//              +" LPAppnt.EdorType=LPAddress.EdorType and LPAppnt.AppntNo=LPAddress.CustomerNo and"
//              +" LPAppnt.AddressNo=LPAddress.AddressNo and LPAppnt.EdorNo='"+fm.EdorNo.value+"' and"
//              +" LPAppnt.EdorType='"+fm.EdorType.value+"' and LPAppnt.ContNo='"+fm.ContNo.value+"'"
//              ;
	var strSQL ="";
  mySql=new SqlClass();
  mySql.setResourceName("bq.PEdorTypeAE");
  mySql.setSqlId("PEdorTypeAESql20");
  mySql.addSubPara(fm.EdorNo.value); 
  mySql.addSubPara(fm.EdorType.value); 
  mySql.addSubPara(fm.ContNo.value); 
  strSQL=mySql.getString();
  var arrResult = easyExecSql(strSQL, 1, 0);
    if (arrResult == null)
       {
       i=0;
       j=0;
//         var strSQL ="select * from LCAppnt,LCAddress where LCAppnt.AppntNo=LCAddress.CustomerNo and"
//              +" LCAppnt.AddressNo=LCAddress.AddressNo and  LCAppnt.ContNo='"+fm.ContNo.value+"'";
        var strSQL ="";
        mySql=new SqlClass();
        mySql.setResourceName("bq.PEdorTypeAE");
        mySql.setSqlId("PEdorTypeAESql21");
        mySql.addSubPara(fm.ContNo.value); 
        strSQL=mySql.getString();
        arrResult = easyExecSql(strSQL, 1, 0);
        if (arrResult == null)
        {
          alert("��Ͷ������Ϣ");
        }
        else
        {
            displayAddress(arrResult);
        }
      }
    else
    {
        displayAddress(arrResult);
        i=2;
        j=4;
    }
//    var InsuredResult = easyExecSql("select InsuredNo,Name,Sex,Birthday,IDType,IDNo from LCInsured where ContNo ='"+fm.ContNo.value+"'",1,0)
//    if(InsuredResult == null){
//      alert("�ޱ�������Ϣ");
//    }
//    else{
//      DisplayInsured(InsuredResult);
//    }
     getInsuredInfo();

}

function getInsuredInfo()
{
    var ContNo=document.all("ContNo").value;
    var tEdorNo = document.all("EdorNo").value;
    var tEdorType = document.all("EdorType").value;
    if(ContNo!=null&&ContNo!="")
    {
        mySql=new SqlClass();
        mySql.setResourceName("bq.PEdorTypeAE");
        mySql.setSqlId("PEdorTypeAESql1");
        mySql.addSubPara(ContNo); 
        mySql.addSubPara(tEdorNo); 
        mySql.addSubPara(tEdorType); 
        //var strSQL ="select i.InsuredNo,i.Name, (select trim(u.code)||'-'||trim(u.CodeName) from LDCode u where trim(u.codetype) = 'sex' and trim(u.code) = trim(sex)),i.Birthday,(select trim(y.code)||'-'||trim(y.CodeName) from LDCode y where trim(y.codetype) = 'idtype' and trim(y.code) = trim(idtype)),i.IdNo,i.Relationtomaininsured,(select w.CodeName from ldcode w where w.codetype = 'relation' and w.code = i.Relationtomaininsured),i.RelationToAppnt,(select w.CodeName from ldcode w where w.codetype = 'relation' and w.code = i.RelationToAppnt) from LPInsured i where i.ContNo='"+ContNo+"' and i.edorno = '" + tEdorNo + "' and edortype = '" + tEdorType + "' order by i.sequenceno";
        var  brrResult = easyExecSql(mySql.getString());
        //�ж��Ƿ��ѯ�ɹ�
        if (!brrResult)
        {
            mySql=new SqlClass();
            mySql.setResourceName("bq.PEdorTypeAE");
            mySql.setSqlId("PEdorTypeAESql2");
            mySql.addSubPara(ContNo);         
            //strSQL ="select i.InsuredNo,i.Name, (select trim(u.code)||'-'||trim(u.CodeName) from LDCode u where trim(u.codetype) = 'sex' and trim(u.code) = trim(sex)),i.Birthday,(select trim(y.code)||'-'||trim(y.CodeName) from LDCode y where trim(y.codetype) = 'idtype' and trim(y.code) = trim(idtype)),i.IdNo,i.Relationtomaininsured,(select w.CodeName from ldcode w where w.codetype = 'relation' and w.code = i.Relationtomaininsured),i.RelationToAppnt,(select w.CodeName from ldcode w where w.codetype = 'relation' and w.code = i.RelationToAppnt) from LCInsured i where i.ContNo='"+ContNo+"' order by i.sequenceno";
            brrResult = easyExecSql(mySql.getString());
            if (!brrResult)
            {
                alert("��ѯͶ�����뱻���˹�ϵʧ�ܣ�");
                return false;
            }
            
        }
        
        displayMultiline(brrResult, InsuredGrid);
        
    }
    if (InsuredGrid.mulLineCount > 0)
    {
        tRelation = InsuredGrid.getRowColData(0, 9);
    }
    if (InsuredGrid.mulLineCount > 1)
    {
        tRelation2 = InsuredGrid.getRowColData(1, 9);
    }
    //alert(tRelation);

}

//��ʾ��ѯ���
function displayAddress(arrResult)
{

     //document.all('PCustomerNo').value = arrResult[0][3+parseInt(i)];
     //document.all('PName').value =arrResult[0][5+parseInt(i)];
     //document.all('PSex').value = arrResult[0][6+parseInt(i)];

    // document.all('PBirthday').value = arrResult[0][7+parseInt(i)];


    // document.all('PIDType').value = arrResult[0][10+parseInt(i)];
    // document.all('PIDNo').value =arrResult[0][11+parseInt(i)];


    //var oldSex =document.all('AppntSex').value;
   // var oldBirthday = document.all('AppntBirthday').value;
   // var oldOccupationType = document.all('OccupationType').value;
   // var oldName=document.all('AppntName').value;
   
}



function initInsuredGrid() {
     var iArray = new Array();

     try {
          iArray[0]=new Array();
      iArray[0][0]="���";                  //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";                  //�п�
      iArray[0][2]=10;                      //�����ֵ
      iArray[0][3]=0;                       //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="�����˿ͻ���";              //����
      iArray[1][1]="60px";                 //�п�
      iArray[1][2]=90;                      //�����ֵ
      iArray[1][3]=0;                       //�Ƿ���������,1��ʾ����0��ʾ������
      iArray[1][4]="InsuredNo";
      iArray[1][9]="�����˿ͻ���|len<=90";

      iArray[2]=new Array();
      iArray[2][0]="����";                    //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[2][1]="30px";                  //�п�
      iArray[2][2]=50;                      //�����ֵ
      iArray[2][3]=0;
      iArray[2][4]="InsureName";
      iArray[2][9]="����|len<=50";

      iArray[3]=new Array();
      iArray[3][0]="�Ա�";                  //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[3][1]="30px";                  //�п�
      iArray[3][2]=30;                      //�����ֵ
      iArray[3][3]=0;
      iArray[3][9]="�Ա�|len<=30";

      iArray[4]=new Array();
      iArray[4][0]="��������";                  //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[4][1]="50px";                  //�п�
      iArray[4][2]=50;                      //�����ֵ
      iArray[4][3]=8;
      iArray[4][4]="Birthday";
      iArray[4][9]="��������|len<=50";

      iArray[5]=new Array();
      iArray[5][0]="֤������";                  //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[5][1]="40px";                  //�п�
      iArray[5][2]=100;                      //�����ֵ
      iArray[5][3]=0;
      iArray[5][9]="֤������|len<=40";

      iArray[6]=new Array();
      iArray[6][0]="֤����";                    //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[6][1]="100px";                 //�п�
      iArray[6][2]=150;                      //�����ֵ
      iArray[6][3]=0;
      iArray[6][4]="IDNo";
      iArray[6][9]="֤����|len<=80";


      iArray[7]=new Array();
      iArray[7][0]="���������˹�ϵ";                    //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[7][1]="70px";                  //�п�
      iArray[7][2]=100;                      //�����ֵ
      iArray[7][3]=2;
      iArray[7][4]="relation";
      iArray[7][5]="7|8";
      iArray[7][6]="0|1";
      iArray[7][9]="���������˹�ϵ|len<=50";

      iArray[8]=new Array();
      iArray[8][0]="���������˹�ϵ";                    //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[8][1]="70px";                  //�п�
      iArray[8][2]=100;                      //�����ֵ
      iArray[8][3]=0;


      iArray[9]=new Array();
      iArray[9][0]="��Ͷ���˹�ϵ";                    //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[9][1]="70px";                  //�п�
      iArray[9][2]=100;                      //�����ֵ
      iArray[9][3]=2;
      iArray[9][4]="relation";
      iArray[9][5]="9|10";
      iArray[9][6]="0|1";
      iArray[9][9]="��Ͷ���˹�ϵ|len<=50";

      iArray[10]=new Array();
      iArray[10][0]="��Ͷ���˹�ϵ";                    //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[10][1]="70px";                  //�п�
      iArray[10][2]=100;                      //�����ֵ
      iArray[10][3]=0;
      
      InsuredGrid = new MulLineEnter( "fm" , "InsuredGrid" );
      //��Щ���Ա�����loadMulLineǰ
      InsuredGrid.mulLineCount = 0;
      InsuredGrid.displayTitle = 1;
      InsuredGrid.canChk=0;
      InsuredGrid.canSel =0;
      InsuredGrid.hiddenPlus=1;
      InsuredGrid.hiddenSubtraction=1;
      //ImpartGrid.tableWidth   ="500px";
      InsuredGrid.loadMulLine(iArray);

      //��Щ����������loadMulLine����
      //ImpartGrid.setRowColData(1,1,"asdf");
    }
    catch(ex) {
      alert(ex);
    }
}

// ��֪��Ϣ�б�ĳ�ʼ��
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
      iArray[1][15]="1";
      iArray[1][16]="#1# and code in (#A01#,#A02#)";
      iArray[1][18]=300;

      iArray[2]=new Array();
      iArray[2][0]="��֪����";         		//����
      iArray[2][1]="60px";            		//�п�
      iArray[2][2]=60;            			//�����ֵ
      iArray[2][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[2][4]="ImpartCode";
      iArray[2][5]="2|3|4";
      iArray[2][6]="0|1|2";
      iArray[2][9]="��֪����|len<=5";
      iArray[2][15]="ImpartVer";
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

//      iArray[5]=new Array();
//      iArray[5][0]="��֪�ͻ�����";         		//����
//      iArray[5][1]="90px";            		//�п�
//      iArray[5][2]=90;            			//�����ֵ
//      iArray[5][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
//      iArray[5][4]="CustomerType";
//      iArray[5][9]="��֪�ͻ�����|len<=18";
//      
//      iArray[6]=new Array();
//      iArray[6][0]="��֪�ͻ�����";         		//����
//      iArray[6][1]="90px";            		//�п�
//      iArray[6][2]=90;            			//�����ֵ
//      iArray[6][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
//      iArray[6][4]="CustomerNo";
//      iArray[6][9]="��֪�ͻ�����";
//      iArray[6][15]="Cont";

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
      alert(ex);
    }
}

function newOccupatioinInfo(){
     mySql=new SqlClass();
     mySql.setResourceName("bq.PEdorTypeAE");
     mySql.setSqlId("PEdorTypeAESql3");
     mySql.addSubPara(top.opener.document.all('EdorNo').value); 
     mySql.addSubPara(top.opener.document.all('EdorType').value);
     mySql.addSubPara(top.opener.document.all('InsuredNo').value);
     
     //var strsql = "select OccupationCode,OccupationType from lpinsured where 1=1 and EdorNo = '"+top.opener.document.all('EdorNo').value+"' and EdorType = '"+top.opener.document.all('EdorType').value+"' and insuredno = '"+top.opener.document.all('InsuredNo').value+"'";
     var aResult = easyExecSql(mySql.getString(),1,0);
   if(aResult != null){
      document.all('OccupationCode').value = aResult[0][0];
      document.all('OccupationType').value = aResult[0][1];
   }
}


</script>