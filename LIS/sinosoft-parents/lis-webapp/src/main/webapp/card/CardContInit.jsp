<%
    //�������ƣ�ContInit.jsp
//�����ܣ�
//�������ڣ�2005-05-12 08:49:52
//������  ��
//���¼�¼��  ������    ��������     ����ԭ��/����
%>


<%@ page import="com.sinosoft.lis.pubfun.*" %>
<%
    String CurrentDate = PubFun.getCurrentDate();
    String CurrentTime = PubFun.getCurrentTime();
%>
<script language="JavaScript">
var loadFlag =<%=tLoadFlag%>;

//��ʼ�������
function initInpBox() {

}

// ������ĳ�ʼ��
function initSelBox() {

}

//����ʼ��
function initForm() {


}


function initInpBox2()
{
    try
    {
        //SelPolNo��ǰѡ�����ֵı�����
        fm.SelPolNo.value = '';
        try {
            mSwitch.deleteVar("PolNo");
        } catch(ex) {
        }
        ;
        fm.InsuredNo.value = '';
        fm.ManageCom.value = ComCode;
    }
    catch(ex)
    {
        alert("��ContInsuredInit2.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
    }
}

function initSelBox2()
{
    try
    {
        /*********  hanlin 20050416  ��֪���Ǹ�ʲô�õġ�
           if(checktype=="1")
           {
             param="121";
             fm.pagename.value="121";
           }
           if(checktype=="2")
           {
             param="22";
             fm.pagename.value="22";
           }
        *********/
    }
    catch(ex)
    {
        alert("��ContInsuredInit2.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
    }
}

function initForm3(){
try
    {
    initBnfGrid();
//    BnfGrid.setRowColData(0,1,"1"); //�������������
    }catch(ex){
       alert("��CardContInit.jsp-->��ʼ���������!"); 
    }
}

function initForm2()
{
    //alert("initForm2 ContNo=="+document.all("ContNo").value);
    try
    {
        initInpBox2();
        initSelBox2();
        initInsuredGrid();
        initImpartGrid2();
        initBnfGrid();


        //initImpartDetailGrid2();

        initPolGrid();
        initPolOtherGrid();

        // initGrpInfo();
		

        // hanlin 20050416
        //��ContInit.jsp���ѽ����Ϲ��ڴ�ע�͵�
        if (scantype == "scan")
        {
            setFocus();
        }

        //Q:��Ϊ��֪��Auditing��ʲô����ע�͵���
        /***********************
          if(Auditing=="1")
          {
              var tmSwitch = top.opener.parent.VD.gVSwitch;
                  mSwitch.deleteVar( "ContNo" );
                mSwitch.addVar( "ContNo", "", tmSwitch.getVar("ContNo") );
                mSwitch.updateVar("ContNo", "", tmSwitch.getVar("ContNo"));
                mSwitch.deleteVar( "ProposalContNo" );
                mSwitch.addVar( "ProposalContNo", "", tmSwitch.getVar("ContNo") );
                mSwitch.updateVar("ProposalContNo", "", tmSwitch.getVar("ContNo"));
                mSwitch.deleteVar( "PrtNo" );
                mSwitch.addVar( "PrtNo", "", tmSwitch.getVar("PrtNo") );
                mSwitch.updateVar("PrtNo", "", tmSwitch.getVar("PrtNo"));
                mSwitch.deleteVar( "GrpContNo" );
                mSwitch.addVar( "GrpContNo", "", tmSwitch.getVar("GrpContNo") );
                mSwitch.updateVar("GrpContNo", "", tmSwitch.getVar("GrpContNo"));
          }
        ��************************/

        //getContInfo();

        //��ѯ��������Ϣ��
        getInsuredInfo();

        DivGrpNoname.style.display = "none";

        //�ж��Ƿ��Ǽ�ͥ���������������ʾ�������б�

        /********
          if(document.all('FamilyType').value==''||document.all('FamilyType').value==null||document.all('FamilyType').value=='0'||document.all('FamilyType').value=='false'){
            document.all('FamilyType').value='0';
            divTempFeeInput.style.display="none";
            getProposalInsuredInfo();          //��ø��˵���Ϣ����д�����˱�
          }
        *********/
    }
    catch(re)
    {
        alert("ContInsuredInit2.jsp-->InitForm�����з����쳣:��ʼ���������!");
    }

    //hanlin 20050416
    ContType = fm.ContType.value;
    if (fm.ContType.value == "2")
    {
        initContPlanCode();
        initExecuteCom();

    }

    //���ղ�ͬ��LoadFlag���в�ͬ�Ĵ���
    //�õ�����ĵ���λ��,Ĭ��Ϊ1,��ʾ���˱���ֱ��¼��.
    /**********************************************
    *
    *LoadFlag=1  -- ����Ͷ����ֱ��¼��
    *LoadFlag=2  -- �����¸���Ͷ����¼��
    *LoadFlag=3  -- ����Ͷ������ϸ��ѯ
    *LoadFlag=4  -- �����¸���Ͷ������ϸ��ѯ
    *LoadFlag=5  -- ����
    *LoadFlag=6  -- ��ѯ
    *LoadFlag=7  -- ��ȫ�±�����
    *LoadFlag=8  -- ��ȫ����������
    *LoadFlag=9  -- ������������
    *LoadFlag=10 -- ��������
    *LoadFlag=13 -- ������Ͷ���������޸�
    *LoadFlag=16 -- ������Ͷ������ѯ
    *LoadFlag=23 -- �ŵ��˱��б��ƻ����
    *LoadFlag=25 -- �����˱��б��ƻ����
    *LoadFlag=99 -- �涯����
    *
    ************************************************/
    showCodeName();
    //���뺺��
    //�����µ�¼��
    if (LoadFlag == "1") {
        divLCInsuredPerson.style.display = "none";
        divFamilyType.style.display = "";
        //divPrtNo.style.display="";
        divSamePerson.style.display = "";
        DivGrpNoname.style.display = "none";
        fm.PolTypeFlag.value = '0';
        return;
    }

    //�����¸���Ͷ����¼��
    if (LoadFlag == "2") {
        fm.InsuredSequencename.value = "������������";
        divFamilyType.style.display = "none";
        divSamePerson.style.display = "none";
        //divPrtNo.style.display="none";
        divTempFeeInput.style.display = "none";
        divInputContButton.style.display = "none";
        divGrpInputContButton.style.display = "";
        divLCInsuredPerson.style.display = "none";
        DivRelation.style.display = "none";
        DivAddress.style.display = "none";
        DivClaim.style.display = "none";
        return;
    }

    //����Ͷ������ϸ��ѯ
    if (LoadFlag == "3") {
        divTempFeeInput.style.display = "";
        //getInsuredInfo(); ǰ���Ѿ����й�
        return;
    }

    //�����¸���Ͷ������ϸ��ѯ
    if (LoadFlag == "4") {
        divFamilyType.style.display = "none";
        //divPrtNo.style.display="none";
        divInputContButton.style.display = "none";
        divApproveContButton.style.display = "";
        divTempFeeInput.style.display = "none";
        divAddDelButton.style.display = "none";
        divSamePerson.style.display = "none";
        divLCInsuredPerson.style.display = "none";
        divCheckInsuredButton.style.display = "none";
        //getInsuredInfo();  ǰ���Ѿ����й�
        return;
    }

    //����
    if (LoadFlag == "5") {
        // alert("����")
        divTempFeeInput.style.display = "";
        //getInsuredInfo();  ǰ���Ѿ����й�
        divFamilyType.style.display = "";
        divAddDelButton.style.display = "none";
        fm.butBack.style.display = "none";
        divSamePerson.style.display = "none";

        divInputContButton.style.display = "none";
        divApproveContButton.style.display = "";
        divLCInsuredPerson.style.display = "none";
        //alert("����1")
        return;
    }

    //��ѯ
    if (LoadFlag == "6") {
        //�������б�
        divTempFeeInput.style.display = "";
        //divPrtNo.style.display="";
        //¼����ť
        divInputContButton.style.display = "none";

        //divQueryButton.style.display="";
        //divFamilyType.style.display="";
        //��ӡ�ɾ�����޸ı�����
        divAddDelButton.style.display = "none";
        //��������Ϣ����ѯ����ť
        fm.InsuredButBack.style.display = "none";
        //����Ͷ����Ϊ�������˱��ˣ����������checkbox
        divSamePerson.style.display = "none";
        divInputQuery.style.display = "";
        return;
    }

    //��ȫ�±�����
    if (LoadFlag == "7") {
        divFamilyType.style.display = "none";
        divSamePerson.style.display = "none";
        //divPrtNo.style.display="none";
        divTempFeeInput.style.display = "none";
        divInputContButton.style.display = "none";
        divGrpInputContButton.style.display = "";
        divLCInsuredPerson.style.display = "none";
        fm.InsuredSequencename.value = "������������";
        return;
    }

    //��ȫ����������
    if (LoadFlag == "8") {
        return;
    }

    //������������
    if (LoadFlag == "9") {
        return;
    }

    //��������
    if (LoadFlag == "10") {
        return;
    }

    //������Ͷ���������޸�
    if (LoadFlag == "13") {
        divFamilyType.style.display = "none";
        divSamePerson.style.display = "none";
        //divPrtNo.style.display="none";
        divTempFeeInput.style.display = "none";
        divInputContButton.style.display = "none";
        divLCInsuredPerson.style.display = "none";
        divApproveModifyContButton.style.display = "";
        //getInsuredInfo();  ǰ���Ѿ����й�
        return;
    }

    //������Ͷ������ѯ
    if (LoadFlag == "16") {
        fm.InsuredSequencename.value = "������������";
        divTempFeeInput.style.display = "none";
        divInputContButton.style.display = "none";
        divQueryButton.style.display = "";
        divFamilyType.style.display = "none";
        divAddDelButton.style.display = "none";
        fm.butBack.style.display = "none";
        divSamePerson.style.display = "none";
        divLCInsuredPerson.style.display = "none";
        DivRelation.style.display = "none";
        DivAddress.style.display = "none";
        DivClaim.style.display = "none";
        return;
    }

    //�����˱��б��ƻ����
    if (LoadFlag == "25") {
        divTempFeeInput.style.display = "";
        //getInsuredInfo(); //ǰ���Ѿ����й�
        divFamilyType.style.display = "";
        divAddDelButton.style.display = "";
        fm.butBack.style.display = "none";
        divSamePerson.style.display = "none";
        divInputContButton.style.display = "none";
        divApproveContButton.style.display = "none";
        divLCInsuredPerson.style.display = "none";
        divchangplan.style.display = "";
        return;
    }

    //�涯����
    if (this.LoadFlag == "99") {
        if (checktype == "1") {
            //divAddDelButton.style.display="none";
            //divInputContButton.style.display="none";
            autoMoveButton.style.display = "";
            return;
        }
        if (checktype == "2") {
        ����  //divAddDelButton.style.display="none";
            //divInputContButton.style.display="none";
            autoMoveButton.style.display = "";
            return;
        }
        return;
    }

}


// ��������Ϣ�б�ĳ�ʼ��
function initInsuredGrid()
{
    var iArray = new Array();
    try
    {
        iArray[0] = new Array();
        iArray[0][0] = "���";
        //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
        iArray[0][1] = "40px";
        //�п�
        iArray[0][2] = 1;
        //�����ֵ
        iArray[0][3] = 0;
        //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[1] = new Array();
        iArray[1][0] = "�ͻ�����";
        //����
        iArray[1][1] = "80px";
        //�п�
        iArray[1][2] = 20;
        //�����ֵ
        iArray[1][3] = 0;
        //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�

        iArray[2] = new Array();
        iArray[2][0] = "����";
        //����
        iArray[2][1] = "60px";
        //�п�
        iArray[2][2] = 20;
        //�����ֵ
        iArray[2][3] = 0;
        //�Ƿ���������,1��ʾ����0��ʾ������


        iArray[3] = new Array();
        iArray[3][0] = "�Ա�";
        //����
        iArray[3][1] = "40px";
        //�п�
        iArray[3][2] = 20;
        //�����ֵ
        iArray[3][3] = 2;
        //�Ƿ���������,1��ʾ����0��ʾ������
        iArray[3][4] = "Sex";


        iArray[4] = new Array();
        iArray[4][0] = "��������";
        //����
        iArray[4][1] = "80px";
        //�п�
        iArray[4][2] = 20;
        //�����ֵ
        iArray[4][3] = 0;

        iArray[5] = new Array();
        iArray[5][0] = "���һ�������˹�ϵ";
        //����
        iArray[5][1] = "100px";
        //�п�
        iArray[5][2] = 20;
        //�����ֵ
        iArray[5][3] = 2;
        iArray[5][4] = "Relation";
        //�Ƿ����ô���:null||""Ϊ������
        iArray[5][9] = "�����������˹�ϵ|code:Relation&NOTNULL";
        //iArray[5][18]=300;

        iArray[6] = new Array();
        iArray[6][0] = "�ͻ��ڲ���";
        //����
        iArray[6][1] = "80px";
        //�п�
        iArray[6][2] = 20;
        //�����ֵ
        iArray[6][3] = 2;
        iArray[6][10] = "MutiSequenceNo";
        iArray[6][11] = "0|^1|��һ�������� ^2|�ڶ��������� ^3|������������";

        InsuredGrid = new MulLineEnter("fm", "InsuredGrid");
        //��Щ���Ա�����loadMulLineǰ
        InsuredGrid.mulLineCount = 0;
        InsuredGrid.displayTitle = 1;
        InsuredGrid.canSel = 1;
        InsuredGrid.selBoxEventFuncName = "getInsuredDetail";
        //���RadioBoxʱ��Ӧ��JS����
        InsuredGrid.hiddenPlus = 1;
        //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
        InsuredGrid.hiddenSubtraction = 1;
        InsuredGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
        alert(ex);
    }
}

// �����˸�֪��Ϣ�б�ĳ�ʼ��
function initImpartGrid2() {
    var iArray = new Array();

    try {
        iArray[0] = new Array();
        iArray[0][0] = "���";
        //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
        iArray[0][1] = "30px";
        //�п�
        iArray[0][2] = 10;
        //�����ֵ
        iArray[0][3] = 0;
        //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[1] = new Array();
        iArray[1][0] = "��֪���";
        //����
        iArray[1][1] = "60px";
        //�п�
        iArray[1][2] = 60;
        //�����ֵ
        iArray[1][3] = 2;
        //�Ƿ���������,1��ʾ����0��ʾ������
        //      iArray[1][4]="ImpartVer";
        iArray[1][9] = "��֪���|len<=5";
        iArray[1][10] = "InsuredImpart";
        iArray[1][11] = "0|^01|����������֪^02|������֪";
        iArray[1][18] = 300;

        iArray[2] = new Array();
        iArray[2][0] = "��֪����";
        //����
        iArray[2][1] = "60px";
        //�п�
        iArray[2][2] = 60;
        //�����ֵ
        iArray[2][3] = 2;
        //�Ƿ���������,1��ʾ����0��ʾ������
        iArray[2][4] = "ImpartCode";
        iArray[2][5] = "2|3";
        iArray[2][6] = "0|1";
        //iArray[2][7]="getImpartCode";
        iArray[2][9] = "��֪����|len<=4";
        iArray[2][15] = "ImpartVer";
        iArray[2][17] = "1";
        iArray[2][18] = 700;

        iArray[3] = new Array();
        iArray[3][0] = "��֪����";
        //����
        iArray[3][1] = "250px";
        //�п�
        iArray[3][2] = 200;
        //�����ֵ
        iArray[3][3] = 1;
        //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[4] = new Array();
        iArray[4][0] = "��д����";
        //����
        iArray[4][1] = "150px";
        //�п�
        iArray[4][2] = 150;
        //�����ֵ
        iArray[4][3] = 1;
        //�Ƿ���������,1��ʾ����0��ʾ������
        iArray[4][9] = "��д����|len<=200";

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

        ImpartInsuredGrid = new MulLineEnter("fm", "ImpartInsuredGrid");
        //��Щ���Ա�����loadMulLineǰ
        ImpartInsuredGrid.mulLineCount = 0;
        ImpartInsuredGrid.displayTitle = 1;
        //ImpartGrid.tableWidth   ="500px";
        ImpartInsuredGrid.loadMulLine(iArray);

        //��Щ����������loadMulLine����
        //ImpartGrid.setRowColData(1,1,"asdf");
    }
    catch(ex) {
        alert(ex);
    }
}
// ��֪��ϸ��Ϣ�б�ĳ�ʼ��
function initImpartDetailGrid2() {
    var iArray = new Array();

    try {
        iArray[0] = new Array();
        iArray[0][0] = "���";
        //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
        iArray[0][1] = "30px";
        //�п�
        iArray[0][2] = 10;
        //�����ֵ
        iArray[0][3] = 0;
        //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[1] = new Array();
        iArray[1][0] = "��֪���";
        //����
        iArray[1][1] = "60px";
        //�п�
        iArray[1][2] = 60;
        //�����ֵ
        iArray[1][3] = 2;
        //�Ƿ���������,1��ʾ����0��ʾ������
        //      iArray[1][4]="ImpartVer";
        iArray[1][9] = "��֪���|len<=5";
        iArray[1][10] = "InsuredImpartDetail";
        iArray[1][11] = "0|^01|����������֪^02|������֪";
        iArray[1][18] = 300;

        iArray[2] = new Array();
        iArray[2][0] = "��֪����";
        //����
        iArray[2][1] = "60px";
        //�п�
        iArray[2][2] = 60;
        //�����ֵ
        iArray[2][3] = 2;
        //�Ƿ���������,1��ʾ����0��ʾ������
        iArray[2][4] = "ImpartCode";
        iArray[2][5] = "2";
        iArray[2][6] = "0";
        //iArray[2][7]="getImpartCode";
        iArray[2][9] = "��֪����|len<=4";
        iArray[2][15] = "ImpartVer";
        iArray[2][17] = "1";
        iArray[2][18] = 700;

        iArray[3] = new Array();
        iArray[3][0] = "����";
        //����
        iArray[3][1] = "450px";
        //�п�
        iArray[3][2] = 2000;
        //�����ֵ
        iArray[3][3] = 1;
        //�Ƿ���������,1��ʾ����0��ʾ������


        /*******************************
              iArray[3]=new Array();
              iArray[3][0]="��֪����";         		//����
              iArray[3][1]="250px";            		//�п�
              iArray[3][2]=200;            			//�����ֵ
              iArray[3][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������

              iArray[4]=new Array();
              iArray[4][0]="��ʼʱ��";         		//����
              iArray[4][1]="150px";            		//�п�
              iArray[4][2]=150;            			//�����ֵ
              iArray[4][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
              iArray[4][9]="��ʼʱ��|date";

              iArray[5]=new Array();
              iArray[5][0]="����ʱ��";         		//����
              iArray[5][1]="90px";            		//�п�
              iArray[5][2]=90;            			//�����ֵ
              iArray[5][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
              iArray[5][9]="����ʱ��|date";

              iArray[6]=new Array();
              iArray[6][0]="֤����";         		//����
              iArray[6][1]="90px";            		//�п�
              iArray[6][2]=90;            			//�����ֵ
              iArray[6][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������

              iArray[7]=new Array();
              iArray[7][0]="Ŀǰ���";         		//����
              iArray[7][1]="90px";            		//�п�
              iArray[7][2]=90;            			//�����ֵ
              iArray[7][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������


              iArray[8]=new Array();
              iArray[8][0]="�ܷ�֤��";         		//����
              iArray[8][1]="90px";            		//�п�
              iArray[8][2]=90;            			//�����ֵ
              iArray[8][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
              iArray[8][4]="yesno";
        *******************************/

        ImpartDetailGrid = new MulLineEnter("fm", "ImpartDetailGrid");
        //��Щ���Ա�����loadMulLineǰ
        ImpartDetailGrid.mulLineCount = 0;
        ImpartDetailGrid.displayTitle = 1;
        ImpartDetailGrid.loadMulLine(iArray);

    }
    catch(ex) {
        alert(ex);
    }
}
//������������Ϣ�б��ʼ��
function initPolGrid() {
    var iArray = new Array();


    try {
        iArray[0] = new Array();
        iArray[0][0] = "���";
        //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
        iArray[0][1] = "30px";
        //�п�
        iArray[0][2] = 10;
        //�����ֵ
        iArray[0][3] = 0;
        //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[1] = new Array();
        iArray[1][0] = "���յ����ֺ���";
        //����
        iArray[1][1] = "60px";
        //�п�
        iArray[1][2] = 60;
        //�����ֵ
        iArray[1][3] = 0;
        //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[2] = new Array();
        iArray[2][0] = " ���ֱ���";
        //����
        iArray[2][1] = "60px";
        //�п�
        iArray[2][2] = 60;
        //�����ֵ
        iArray[2][3] = 2;
        //�Ƿ���������,1��ʾ����0��ʾ������
        iArray[2][4] = "RiskCode";
        //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[3] = new Array();
        iArray[3][0] = " ����(Ԫ)";
        //����
        iArray[3][1] = "60px";
        //�п�
        iArray[3][2] = 60;
        //�����ֵ
        iArray[3][3] = 0;
        //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[4] = new Array();
        iArray[4][0] = "����(Ԫ)";
        //����
        iArray[4][1] = "0px";
        //�п�
        iArray[4][2] = 60;
        //�����ֵ
        iArray[4][3] = 3;
        //�Ƿ���������,1��ʾ����0��ʾ������


        PolGrid = new MulLineEnter("fm", "PolGrid");
        PolGrid.mulLineCount = 0;
        PolGrid.displayTitle = 1;
        PolGrid.canSel = 1;
        PolGrid.selBoxEventFuncName = "getPolDetail";
        PolGrid.hiddenPlus = 1;
        PolGrid.hiddenSubtraction = 1;
        PolGrid.loadMulLine(iArray);
    }
    catch(ex) {
        alert(ex);
    }
}


function initBnfGrid() {
    var iArray = new Array();

    try {

        iArray[0] = new Array();
        iArray[0][0] = "���"; //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
        iArray[0][1] = "30px"; //�п�
        iArray[0][2] = 10; //�����ֵ
        iArray[0][3] = 0; //�Ƿ���������,1��ʾ����0��ʾ������
if(CardRisk!=null&&(CardRisk=="111603"||CardRisk=="141812"||CardRisk=="311603")){
        iArray[1] = new Array();
        iArray[1][0] = "���������"; //����
        iArray[1][1] = "80px"; //�п�
        iArray[1][2] = 40; //�����ֵ
        iArray[1][3] = 0; //�Ƿ���������,1��ʾ����0��ʾ������
        //iArray[1][4] = "bnftype";
        //iArray[1][9] = "����|code:bnftype"; //У��
        iArray[1][10]="bnforder"
        iArray[1][11]="0|^1|���������"
}else{
        iArray[1] = new Array();
        iArray[1][0] = "���������"; //����
        iArray[1][1] = "80px"; //�п�
        iArray[1][2] = 40; //�����ֵ
        iArray[1][3] = 2; //�Ƿ���������,1��ʾ����0��ʾ������
        iArray[1][4] = "bnftype";
        iArray[1][9] = "����|code:bnftype"; //У��
        //iArray[1][10]="bnforder"
        //iArray[1][11]="0|^1|���������"
}
        iArray[2] = new Array();
        iArray[2][0] = "����"; //����
        iArray[2][1] = "80px"; //�п�
        iArray[2][2] = 30; //�����ֵ
        iArray[2][3] = 1; //�Ƿ���������,1��ʾ����0��ʾ������
        iArray[2][9] = "����|len<=20"; //У��
        iArray[2][22]="confirmSecondInput1";
        
if(CardRisk!=null&&(CardRisk=="141812"||CardRisk=="111603"||CardRisk=="311603")){       
        iArray[3] = new Array();
        iArray[3][0] = "�Ա�"; //����
        iArray[3][1] = "0px"; //�п�
        iArray[3][2] = 30; //�����ֵ
        iArray[3][3] = 3; //�Ƿ���������,1��ʾ����0��ʾ������
        iArray[3][4] = "sex";
        iArray[3][9] = "�Ա�|code:sex"; //У��
     }else{
        iArray[3] = new Array();
        iArray[3][0] = "�Ա�"; //����
        iArray[3][1] = "80px"; //�п�
        iArray[3][2] = 30; //�����ֵ
        iArray[3][3] = 2; //�Ƿ���������,1��ʾ����0��ʾ������
        iArray[3][4] = "sex";
        iArray[3][9] = "�Ա�|code:sex"; //У��
     
     }
        
if(CardRisk!=null&&(CardRisk=="111603"||CardRisk=="141812"||CardRisk=="311603")){
        iArray[4] = new Array();
        iArray[4][0] = "֤������"; //����
        iArray[4][1] = "0px"; //�п�
        iArray[4][2] = 40; //�����ֵ
        iArray[4][3] = 2; //�Ƿ���������,1��ʾ����0��ʾ������
        iArray[4][4] = "IDType";
        iArray[4][9] = "֤������|code:IDType";
        
        iArray[5] = new Array();
        iArray[5][0] = "֤������"; //����
        iArray[5][1] = "0px"; //�п�
        iArray[5][2] = 80; //�����ֵ
        iArray[5][3] = 1; //�Ƿ���������,1��ʾ����0��ʾ������
        iArray[5][9] = "֤������|len<=20";
}else{
        iArray[4] = new Array();
        iArray[4][0] = "֤������"; //����
        iArray[4][1] = "50px"; //�п�
        iArray[4][2] = 40; //�����ֵ
        iArray[4][3] = 2; //�Ƿ���������,1��ʾ����0��ʾ������
        iArray[4][4] = "IDType";
        iArray[4][9] = "֤������|code:IDType";
        
        iArray[5] = new Array();
        iArray[5][0] = "֤������"; //����
        iArray[5][1] = "130px"; //�п�
        iArray[5][2] = 80; //�����ֵ
        iArray[5][3] = 1; //�Ƿ���������,1��ʾ����0��ʾ������
        iArray[5][9] = "֤������|len<=20";
}
if(CardRisk!=null&&(CardRisk=="141812")){
        iArray[6] = new Array();
        iArray[6][0] = "�뱻���˹�ϵ"; //����
        iArray[6][1] = "60px"; //�п�
        iArray[6][2] = 60; //�����ֵ
        iArray[6][3] = 2; //�Ƿ���������,1��ʾ����0��ʾ������
        //iArray[6][4] = "cardbnfrelation1";
        iArray[6][9] = "�뱻���˹�ϵ|code:Relation";
        iArray[6][10]="cardrelation2"
        iArray[6][11]="0|^31|��ĸ|^32|��Ů|^33|��ż|^30|����"
}else if(CardRisk!=null&&(CardRisk=="311603")){
        iArray[6] = new Array();
        iArray[6][0] = "�뱻���˹�ϵ"; //����
        iArray[6][1] = "60px"; //�п�
        iArray[6][2] = 60; //�����ֵ
        iArray[6][3] = 2; //�Ƿ���������,1��ʾ����0��ʾ������
        //iArray[6][4] = "cardbnfrelation1";
        iArray[6][9] = "�뱻���˹�ϵ|code:Relation";
        iArray[6][10]="cardrelation2"
        iArray[6][11]="0|^31|��ĸ|^32|��Ů|^33|��ż|^30|����"
        //iArray[6][9] = "�뱻���˹�ϵ|code:Relation";
        //iArray[6][10]="cardbnfrelation1"
        // iArray[6][11]="0|^01|�ɷ�^02|����|^03|����|^04|ĸ��|^05|����|^06|Ů��|^30|����"
}else if(CardRisk!=null&&(CardRisk=="111603")){
        iArray[6] = new Array();
        iArray[6][0] = "�뱻���˹�ϵ"; //����
        iArray[6][1] = "60px"; //�п�
        iArray[6][2] = 60; //�����ֵ
        iArray[6][3] = 2; //�Ƿ���������,1��ʾ����0��ʾ������
        //iArray[6][4] = "cardbnfrelation2";
        iArray[6][9] = "�뱻���˹�ϵ|code:Relation";
        iArray[6][10]="cardrelation2"
        iArray[6][11]="0|^31|��ĸ|^33|��ż|^30|����"
        //iArray[6][9] = "�뱻���˹�ϵ|code:Relation";
}else{
        iArray[6] = new Array();
        iArray[6][0] = "�뱻���˹�ϵ"; //����
        iArray[6][1] = "0px"; //�п�
        iArray[6][2] = 60; //�����ֵ
        iArray[6][3] = 3; //�Ƿ���������,1��ʾ����0��ʾ������
        iArray[6][4] = "Relation";
        iArray[6][9] = "�뱻���˹�ϵ|code:Relation";
}

        

if(CardRisk!=null&&(CardRisk=="111603"||CardRisk=="141812"||CardRisk=="311603")){
        iArray[7] = new Array();
        iArray[7][0] = "����˳��"; //����
        iArray[7][1] = "0px"; //�п�
        iArray[7][2] = 40; //�����ֵ
        iArray[7][3] = 2; //�Ƿ���������,1��ʾ����0��ʾ������
        iArray[7][4] = "bnforder";
        iArray[7][9] = "����˳��|code:OccupationType";
        
        

        iArray[8] = new Array();
        iArray[8][0] = "����ݶ�"; //����
        iArray[8][1] = "0px"; //�п�
        iArray[8][2] = 40; //�����ֵ
        iArray[8][3] = 1; //�Ƿ���������,1��ʾ����0��ʾ������
        iArray[8][9] = "�������|num&len<=10";

        iArray[9] = new Array();
        iArray[9][0] = "סַ������ţ�"; //����
        iArray[9][1] = "0px"; //�п�
        iArray[9][2] = 100; //�����ֵ
        iArray[9][3] = 3; //�Ƿ���������,1��ʾ����0��ʾ������
        iArray[9][9] = "סַ|len<=80";
}else{
        iArray[7] = new Array();
        iArray[7][0] = "����˳��"; //����
        iArray[7][1] = "50px"; //�п�
        iArray[7][2] = 40; //�����ֵ
        iArray[7][3] = 2; //�Ƿ���������,1��ʾ����0��ʾ������
        iArray[7][4] = "bnforder";
        iArray[7][9] = "����˳��|code:OccupationType";

        iArray[8] = new Array();
        iArray[8][0] = "����ݶ�"; //����
        iArray[8][1] = "50px"; //�п�
        iArray[8][2] = 40; //�����ֵ
        iArray[8][3] = 1; //�Ƿ���������,1��ʾ����0��ʾ������
        iArray[8][9] = "�������|num&len<=10";

        iArray[9] = new Array();
        iArray[9][0] = "סַ������ţ�"; //����
        iArray[9][1] = "80px"; //�п�
        iArray[9][2] = 100; //�����ֵ
        iArray[9][3] = 1; //�Ƿ���������,1��ʾ����0��ʾ������
        iArray[9][9] = "סַ|len<=80";
}


        iArray[10] = new Array();
        iArray[10][0] = "����"; //����
        iArray[10][1] = "50px"; //�п�
        iArray[10][2] = 30; //�����ֵ
        iArray[10][3] = 2; //�Ƿ���������,1��ʾ����0��ʾ������
        iArray[10][4] = "customertype";


        iArray[11] = new Array();
        iArray[11][0] = "����������"; //����
        iArray[11][1] = "00px"; //�п�
        iArray[11][2] = 30; //�����ֵ
        iArray[11][3] = 3;

        iArray[12] = new Array();
        iArray[12][0] = "�����������ڲ��ͻ���"; //����
        iArray[12][1] = "0px"; //�п�
        iArray[12][2] = 30; //�����ֵ
        iArray[12][3] = 3; //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[13] = new Array();
        iArray[13][0] = "���������˿ͻ���"; //����
        iArray[13][1] = "0px"; //�п�
        iArray[13][2] = 30; //�����ֵ
        iArray[13][3] = 3; //�Ƿ���������,1��ʾ����0��ʾ������


        BnfGrid = new MulLineEnter("fm", "BnfGrid");

        //��Щ���Ա�����loadMulLineǰ
        BnfGrid.mulLineCount = 0;

        BnfGrid.displayTitle = 1;
        BnfGrid.addEventFuncName="InitBnfType";
        BnfGrid.loadMulLine(iArray);


        //��Щ����������loadMulLine����
        //BnfGrid.setRowColData(0,8,"1");
        //BnfGrid.setRowColData(0,9,"1");
    } catch(ex) {
        alert("��ProposalInit.jsp-->initBnfGrid�����з����쳣:��ʼ���������!");
    }
}



function getContInfo()

{
    try {
        document.all('ContNo').value = mSwitch.getVar("ContNo");
        if (document.all('ContNo').value == "false") {
            document.all('ContNo').value = "";
        }
    } catch(ex) {
    }
    ;
    try {
        document.all('PrtNo').value = mSwitch.getVar("PrtNo");
    } catch(ex) {
    }
    ;
    try {
        document.all('ProposalContNo').value = mSwitch.getVar("ProposalContNo");
    } catch(ex) {
    }
    ;
    try {
        document.all('GrpContNo').value = mSwitch.getVar("GrpContNo");
    } catch(ex) {
    }
    ;
    try {
        document.all('FamilyType').value = mSwitch.getVar("FamilyType");
    } catch(ex) {
    }
    ;
    try {
        document.all('PolTypeFlag').value = mSwitch.getVar("PolType");
        if (document.all('PolTypeFlag').value == "false") {
            document.all('PolTypeFlag').value = "0";
        }
    } catch(ex) {
    }
    ;
    try {
        document.all('InsuredPeoples').value = mSwitch.getVar("InsuredPeoples");
        if (document.all('InsuredPeoples').value == "false") {
            document.all('InsuredPeoples').value = "";
        }
    } catch(ex) {
    }
    ;
}

function initContPlanCode()
{
    //alert(mSwitch.getVar( "ProposalGrpContNo" ));
    document.all("ContPlanCode").CodeData = getContPlanCode(mSwitch.getVar("ProposalGrpContNo"));
}

function initExecuteCom()
{
    document.all("ExecuteCom").CodeData = getExecuteCom(mSwitch.getVar("ProposalGrpContNo"));
}

function initGrpInfo()
{
    //fm.PrtNo.value=mSwitch.getVar('PrtNo');
    //fm.SaleChnl.value=mSwitch.getVar('SaleChnl');
    //fm.ManageCom.value=mSwitch.getVar('ManageCom');
    //fm.AgentCode.value=mSwitch.getVar('AgentCode');
    //fm.AgentGroup.value=mSwitch.getVar('AgentGroup');
    //fm.GrpName.value=mSwitch.getVar('GrpName');
    //fm.CValiDate.value=mSwitch.getVar('CValiDate');
    //fm.ProposalGrpContNo.value = mSwitch.getVar('ProposalGrpContNo');
}

function queryinfo()
{
	var sqlid34="CardContInputSql34";
	var mySql34=new SqlClass();
	mySql34.setResourceName("card.CardContInputSql"); //ָ��ʹ�õ�properties�ļ���
	mySql34.setSqlId(sqlid34);//ָ��ʹ�õ�Sql��id
	mySql34.addSubPara(prtNo);//ָ������Ĳ���
	//"select * from lccont where prtno='" + prtNo + "'"
    arrResult = easyExecSql(mySq34.getString());
    if (arrResult != null) {
        querycont();
        queryappnt();
        queryinsured();
        querybnf();
        queryrisk();
    }
}


//����������Ϣ��
function initPolOtherGrid() 
{
    var iArray = new Array();
    try 
    {
        iArray[0] = new Array();
        iArray[0][0] = "���";
        iArray[0][1] = "30px";
        iArray[0][2] = 10;
        iArray[0][3] = 0;

        iArray[1] = new Array();
        iArray[1][0] = "���ֱ���";
        iArray[1][1] = "50px";
        iArray[1][2] = 30;
        iArray[1][3] = 0;

		    iArray[2] = new Array();
        iArray[2][0] = "���α���";
        iArray[2][1] = "50px";
        iArray[2][2] = 30;
        iArray[2][3] = 3;
        
        iArray[3] = new Array();
        iArray[3][0] = "������������";
        iArray[3][1] = "80px";
        iArray[3][2] = 30;
        iArray[3][3] = 0;
        
        iArray[4] = new Array();
        iArray[4][0] = "Դ��������";
        iArray[4][1] = "60px";
        iArray[4][2] = 30;
        iArray[4][3] = 3;
        iArray[4][4]="otherparmname";
        
        iArray[5] = new Array();
        iArray[5][0] = "��������";
        iArray[5][1] = "40px";
        iArray[5][2] = 30;
        iArray[5][3] = 3;
        
        iArray[6] = new Array();
        iArray[6][0] = "����ֵ";
        iArray[6][1] = "150px";
        iArray[6][2] = 30;
        iArray[6][3] = 2;
				iArray[6][4]="queryriskother";
				iArray[6][17]="4";
				iArray[6][15]="otherparmname";
        
        iArray[7] = new Array();
        iArray[7][0] = "˳���";
        iArray[7][1] = "150px";
        iArray[7][2] = 30;
        iArray[7][3] = 3;

        PolOtherGrid = new MulLineEnter("fm", "PolOtherGrid");
				//��Щ���Ա�����loadMulLineǰ
				PolOtherGrid.mulLineCount = 0;
				PolOtherGrid.displayTitle = 1;
				PolOtherGrid.hiddenPlus=1;
				PolOtherGrid.hiddenSubtraction=1;
				PolOtherGrid.loadMulLine(iArray);
    }
    catch(ex) 
    {
        alert("��CardContInit.jsp-->initPolOtherGrid�����з����쳣:��ʼ���������!");
    }
}

/*****************************************************
 *   ����
 *   
 *****************************************************
 */


</script>
