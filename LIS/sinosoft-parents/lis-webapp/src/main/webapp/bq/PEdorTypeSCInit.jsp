<%
//�������ƣ�PEdorInputInit.jsp
//�����ܣ�
//�������ڣ�2007-12-10 16:49:22
//������  ��PST 
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->


<script language="JavaScript">

function initForm()
{
  try
  {
    initInpBox();
    initLCCSpecGrid();
    initSpecBox();
    initLCPReSpecGrid();
    initPreSpecBox();
    initLPCSpecGrid();
    initPSpecBox();

  }
  catch(re)
  {
    alert("PEdorTypeSCInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

function initInpBox()
{

  try
  {
    Edorquery();
    try { document.getElementsByName("AppObj")[0].value = top.opener.document.getElementsByName("AppObj")[0].value; } catch (ex) {}
    document.all('EdorAcceptNo').value = top.opener.document.all('EdorAcceptNo').value;
    document.all('EdorNo').value = top.opener.document.all('EdorNo').value;
    document.all('ContNo').value = top.opener.document.all('ContNo').value;
    try { document.all('GrpContNo').value = top.opener.document.all('GrpContNo').value; } catch (ex) {}
    document.all('EdorType').value = top.opener.document.all('EdorType').value;
    document.all('EdorItemAppDate').value = top.opener.document.all('EdorItemAppDate').value;
    document.all('EdorValiDate').value = top.opener.document.all('EdorValiDate').value;
    document.all('InsuredNo').value = top.opener.document.all('InsuredNo').value;
    fm.PolNo.value=top.opener.document.all('PolNo').value;
    showOneCodeName('EdorCode','EdorType','EdorTypeName');
    fm.Speccontent.value="";
  }
  catch(ex)
  {
    alert("��PEdorTypeSCInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!" + ex);
  }
}

function initLCPReSpecGrid()
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
        iArray[1][0]="��ͬ��";
        iArray[1][1]="120px";
        iArray[1][2]=100;
        iArray[1][3]=0;

        iArray[2]=new Array();
        iArray[2][0]="���ֺ�";
        iArray[2][1]="0px";
        iArray[2][2]=100;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="��ȫ�����";
        iArray[3][1]="0px";
        iArray[3][2]=100;
        iArray[3][3]=0;



        iArray[4]=new Array();
        iArray[4][0]="��Լ����";
        iArray[4][1]="400px";
        iArray[4][2]=100;
        iArray[4][3]=0;

        iArray[5]=new Array();
        iArray[5][0]="��ˮ��";
        iArray[5][1]="90px";
        iArray[5][2]=100;
        iArray[5][3]=0;


        iArray[6]=new Array();
        iArray[6][0]="��ӡ��ˮ��";
        iArray[6][1]="0px";
        iArray[6][2]=100;
        iArray[6][3]=0;

        iArray[7]=new Array();
        iArray[7][0]="��Լ����";
        iArray[7][1]="0px";
        iArray[7][2]=100;
        iArray[7][3]=0;
                
        LCPReSpecGrid = new MulLineEnter( "fm" , "LCPReSpecGrid" );
        //��Щ���Ա�����loadMulLineǰ
        LCPReSpecGrid.mulLineCount = 0;
        LCPReSpecGrid.displayTitle = 1;
        LCPReSpecGrid.canSel=0;
        LCPReSpecGrid.hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
        LCPReSpecGrid.hiddenSubtraction=1; 
        //LCPReSpecGrid.selBoxEventFuncName ="showSpecInfo";
        LCPReSpecGrid.loadMulLine(iArray);
        //��Щ����������loadMulLine����
    }
    catch(ex)
    {
      alert(ex);
    }
}

function initLCCSpecGrid()
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
        iArray[1][0]="��ͬ��";
        iArray[1][1]="120px";
        iArray[1][2]=100;
        iArray[1][3]=0;

        iArray[2]=new Array();
        iArray[2][0]="���ֺ�";
        iArray[2][1]="0px";
        iArray[2][2]=100;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="��ȫ�����";
        iArray[3][1]="0px";
        iArray[3][2]=100;
        iArray[3][3]=0;



        iArray[4]=new Array();
        iArray[4][0]="��Լ����";
        iArray[4][1]="400px";
        iArray[4][2]=100;
        iArray[4][3]=0;

        iArray[5]=new Array();
        iArray[5][0]="��ˮ��";
        iArray[5][1]="90px";
        iArray[5][2]=100;
        iArray[5][3]=0;


        iArray[6]=new Array();
        iArray[6][0]="��ӡ��ˮ��";
        iArray[6][1]="0px";
        iArray[6][2]=100;
        iArray[6][3]=0;

        iArray[7]=new Array();
        iArray[7][0]="��Լ����";
        iArray[7][1]="0px";
        iArray[7][2]=100;
        iArray[7][3]=0;
                
        LCCSpecGrid = new MulLineEnter( "fm" , "LCCSpecGrid" );
        //��Щ���Ա�����loadMulLineǰ
        LCCSpecGrid.mulLineCount = 0;
        LCCSpecGrid.displayTitle = 1;
        LCCSpecGrid.canSel=1;
        LCCSpecGrid.hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
        LCCSpecGrid.hiddenSubtraction=1; 
        LCCSpecGrid.selBoxEventFuncName ="showSpecInfo";
        LCCSpecGrid.loadMulLine(iArray);
        //��Щ����������loadMulLine����
    }
    catch(ex)
    {
      alert(ex);
    }
}

function initLPCSpecGrid()
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
        iArray[1][0]="��ͬ��";
        iArray[1][1]="120px";
        iArray[1][2]=100;
        iArray[1][3]=0;

        iArray[2]=new Array();
        iArray[2][0]="���ֺ�";
        iArray[2][1]="0px";
        iArray[2][2]=100;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="��ȫ�����";
        iArray[3][1]="0px";
        iArray[3][2]=100;
        iArray[3][3]=0;

        iArray[4]=new Array();
        iArray[4][0]="��Լ����";
        iArray[4][1]="400px";
        iArray[4][2]=100;
        iArray[4][3]=0;

        iArray[5]=new Array();
        iArray[5][0]="��ˮ��";
        iArray[5][1]="90px";
        iArray[5][2]=100;
        iArray[5][3]=0;


        iArray[6]=new Array();
        iArray[6][0]="��ӡ��ˮ��";
        iArray[6][1]="0px";
        iArray[6][2]=100;
        iArray[6][3]=0;
 
        iArray[7]=new Array();
        iArray[7][0]="��Լ����";
        iArray[7][1]="0px";
        iArray[7][2]=100;
        iArray[7][3]=0;       
        LPCSpecGrid = new MulLineEnter( "fm" , "LPCSpecGrid" );
        //��Щ���Ա�����loadMulLineǰ
        LPCSpecGrid.mulLineCount = 0;
        LPCSpecGrid.displayTitle = 1;
        LPCSpecGrid.canSel=0;
        LPCSpecGrid.hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
        LPCSpecGrid.hiddenSubtraction=1; 
        LPCSpecGrid.selBoxEventFuncName ="showPSpecInfo";
        LPCSpecGrid.loadMulLine(iArray);
        //��Щ����������loadMulLine����
    }
    catch(ex)
    {
      alert(ex);
    }
}
</script>
