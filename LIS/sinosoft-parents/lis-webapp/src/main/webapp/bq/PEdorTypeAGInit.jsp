<%
//�������ƣ�PEdorTypeAGInit.jsp
//�����ܣ�
//�������ڣ�2005-6-16
%>
<!--�û�У����-->


<script language="JavaScript">

function initForm()
{
    try
    {
        Edorquery();
        initInputBox();
        initPolGrid();
        initBonusGrid();
        queryPolInfo();
        queryCustomerInfo();
        queryPolGrid();
        queryBonusGrid();
        queryBankInfo();
    }
    catch (ex)
    {
        alert("�� PEdorTypeAGInit.jsp --> initForm ���溯���з����쳣�� ");
    }
}

function initInputBox()
{
    try
    {
        document.all('EdorAcceptNo').value = top.opener.document.all('EdorAcceptNo').value;
        document.all('EdorNo').value = top.opener.document.all('EdorNo').value;
        document.all('EdorType').value = top.opener.document.all('EdorType').value;
        document.all('ContNo').value = top.opener.document.all('ContNo').value;
        document.all('InsuredNo').value = top.opener.document.all('InsuredNo').value;
        document.all('PolNo').value = top.opener.document.all('PolNo').value;
        document.all('EdorItemAppDate').value = top.opener.document.all('EdorItemAppDate').value;
        document.all('EdorValiDate').value = top.opener.document.all('EdorValiDate').value;
        document.all('AppObj').value = top.opener.document.all('AppObj').value;
        showOneCodeName('EdorType','EdorType','EdorTypeName');
    }
    catch (ex)
    {
        alert("�� PEdorTypeAGInit.jsp --> initInputBox �в������з����쳣�� ");
    }
    try
    {
        if (fm.AppObj.value == "G")
        {
            divAppntInfo.style.display = "none";
        }
    }
    catch (ex)
    {
        alert("�� PEdorTypeAGInit.jsp --> initInputBox �в������з����쳣�� ");
    }
}

//������Ϣ�б�
function initPolGrid()
{
    var iArray = new Array();

    try
    {
        iArray[0]=new Array();
        iArray[0][0]="���";                  //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
        iArray[0][1]="30px";                  //�п�
        iArray[0][2]=30;                     //�����ֵ
        iArray[0][3]=0;                       //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[1]=new Array();
        iArray[1][0]="��ȡ��Ŀ";
        iArray[1][1]="170px";
        iArray[1][2]=200;
        iArray[1][3]=0;

        iArray[2]=new Array();
        iArray[2][0]="��������";
        iArray[2][1]="120px";
        iArray[2][2]=100;
        iArray[2][3]=0;
        iArray[2][21]=3;

        iArray[3]=new Array();
        iArray[3][0]="�������";
        iArray[3][1]="120px";
        iArray[3][2]=150;
        iArray[3][3]=0;
        iArray[3][21]=3;

        iArray[4]=new Array();
        iArray[4][0]="����֪ͨ�����";
        iArray[4][1]="0px";
        iArray[4][2]=0;
        iArray[4][3]=3;

        iArray[5]=new Array();
        iArray[5][0]="���α���";
        iArray[5][1]="120px";
        iArray[5][2]=150;
        iArray[5][3]=0;
        iArray[5][21]=3;

        iArray[6]=new Array();
        iArray[6][0]="�������α���";
        iArray[6][1]="0px";
        iArray[6][2]=0;
        iArray[6][3]=3;

        iArray[7]=new Array();
        iArray[7][0]="������������";
        iArray[7][1]="0px";
        iArray[7][2]=0;
        iArray[7][3]=3;

        iArray[8]=new Array();
        iArray[8][0]="���ֱ���";
        iArray[8][1]="0px";
        iArray[8][2]=0;
        iArray[8][3]=3;

        iArray[9]=new Array();
        iArray[9][0]="��������";
        iArray[9][1]="120px";
        iArray[9][2]=150;
        iArray[9][3]=0;

        PolGrid = new MulLineEnter("fm", "PolGrid");
        //��Щ���Ա�����loadMulLineǰ
        PolGrid.mulLineCount = 0;
        PolGrid.displayTitle = 1;
        PolGrid.canChk=0;
        PolGrid.hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
        PolGrid.hiddenSubtraction=1;
        PolGrid.selBoxEventFuncName ="" ;
        PolGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
        alert("�� PEdorTypeAGInit.jsp --> initPolGrid �����з����쳣: ��ʼ���������");
    }
}

//������Ϣ�б�
function initBonusGrid()
{
    var iArray = new Array();

    try
    {
        iArray[0]=new Array();
        iArray[0][0]="���";                  //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
        iArray[0][1]="30px";                  //�п�
        iArray[0][2]=30;                      //�����ֵ
        iArray[0][3]=0;                       //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[1]=new Array();
        iArray[1][0]="������ȡ����";
        iArray[1][1]="160px";
        iArray[1][2]=200;
        iArray[1][3]=0;
        iArray[1][21]=3;

        iArray[2]=new Array();
        iArray[2][0]="������";
        iArray[2][1]="160px";
        iArray[2][2]=200;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="�������";
        iArray[3][1]="160px";
        iArray[3][2]=200;
        iArray[3][3]=0;
        iArray[3][21]=3;

        iArray[4]=new Array();
        iArray[4][0]="��������";
        iArray[4][1]="160px";
        iArray[4][2]=200;
        iArray[4][3]=0;

        BonusGrid = new MulLineEnter("fm", "BonusGrid");
        //��Щ���Ա�����loadMulLineǰ
        BonusGrid.mulLineCount = 0;
        BonusGrid.displayTitle = 1;
        BonusGrid.canChk=0;
        BonusGrid.hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
        BonusGrid.hiddenSubtraction=1;
        BonusGrid.selBoxEventFuncName ="";
        BonusGrid.loadMulLine(iArray);
    }
    catch (ex)
    {
        alert("�� PEdorTypeAGInit.jsp --> initPolGrid �����з����쳣: ��ʼ���������");
    }
}

</script>