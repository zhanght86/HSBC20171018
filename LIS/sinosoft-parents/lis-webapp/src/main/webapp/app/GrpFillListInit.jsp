<%
//�������ƣ�GrpFillListInput.jsp
//�����ܣ�������������������ѯ��ʼ��
//�������ڣ�2006-04-08 16:30:57
//������  ��Chenrong
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->

<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
    GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>

<script language="JavaScript">

// �����ĳ�ʼ������ѯ������
function initInpBox()
{ 
    fm.PrtNo.value = "";
    fm.GrpContNo.value = "";
    fm.ContNo.value = "";
}

//���ؿؼ��ĳ�ʼ��
function initHiddenBox()
{
    fm.ManageCom.value = nullToEmpty("<%=tGI.ManageCom%>"); 
    fm.Operator.value = nullToEmpty("<%=tGI.Operator%>"); 
}

function nullToEmpty(string)
{
    if ((string == null) || (string == "null") || (string == "undefined"))
    {
        string = "";
    }
    return string;
}

function initForm()
{
    try 
    {
        initInpBox();
        initHiddenBox();
        initPolGrid();
    }
    catch(re) 
    {
        alert("ProposalApproveModifyInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
    }
}


// ������Ϣ�б�ĳ�ʼ��
function initPolGrid() 
{                               
    var iArray = new Array();
    try
    {
        iArray[0]=new Array();
        iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
        iArray[0][1]="30px";            		//�п�
        iArray[0][2]=10;            			//�����ֵ
        iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
        
        iArray[1]=new Array();
        iArray[1][0]="Ͷ������";         		//����
        iArray[1][1]="100px";            		//�п�
        iArray[1][2]=100;            			//�����ֵ
        iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
        
        iArray[2]=new Array();
        iArray[2][0]="�����ͬ��";         		//����
        iArray[2][1]="100px";            		//�п�
        iArray[2][2]=100;            			//�����ֵ
        iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
        
        iArray[3]=new Array();
        iArray[3][0]="��ͬ��";         		//����
        iArray[3][1]="100px";            		//�п�
        iArray[3][2]=100;            			//�����ֵ
        iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
        
        iArray[4]=new Array();
        iArray[4][0]="����������";         		//����
        iArray[4][1]="100px";            		//�п�
        iArray[4][2]=200;            			//�����ֵ
        iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
        
        iArray[5]=new Array();
        iArray[5][0]="����";         		//����
        iArray[5][1]="70px";            		//�п�
        iArray[5][2]=100;            			//�����ֵ
        iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
        
        iArray[6]=new Array();
        iArray[6][0]="����";         		//����
        iArray[6][1]="70px";            		//�п�
        iArray[6][2]=100;            			//�����ֵ
        iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
        
		iArray[7]=new Array();
		iArray[7][0]="����";         		
		iArray[7][1]="50px";            		 
		iArray[7][2]=60;            			
		iArray[7][3]=0;              		
		iArray[7][4]="Currency";              	  
		iArray[7][9]="����|code:Currency";        
        
        PolGrid = new MulLineEnter("document","PolGrid"); 
        //��Щ���Ա�����loadMulLineǰ
        PolGrid.mulLineCount = 5;   
        PolGrid.displayTitle = 1;
        PolGrid.locked = 1;
        PolGrid.canSel = 1;
        PolGrid.hiddenPlus = 1;
        PolGrid.hiddenSubtraction = 1;
        PolGrid.loadMulLine(iArray);  
        
        //��Щ����������loadMulLine����
        //PolGrid.setRowColData(1,1,"asdf");
    }
    catch(ex)
    {
        alert("ProposalApproveModifyInit.jsp-->initPolGrid�����з����쳣:��ʼ���������!");
    }
}

</script>
