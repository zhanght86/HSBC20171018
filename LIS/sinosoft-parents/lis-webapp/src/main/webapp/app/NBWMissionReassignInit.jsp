<%
//�������ƣ�NBWMissionReassignInit.jsp
//�����ܣ�����Լ�������
//�������ڣ�2006-2-20 14:40
//������  ��chenrong
//���¼�¼��  ������    ��������     ����ԭ��/����
%> 
<script language="JavaScript">

//��ʼ��ҳ�洫�ݹ����Ĳ���
function initParam()
{
   document.all('Operator').value = nullToEmpty("<%= tGI.Operator %>");
   document.all('ComCode').value = nullToEmpty("<%= tGI.ComCode %>");
}

//��null���ַ���תΪ��
function nullToEmpty(string)
{
	if ((string == "null") || (string == "undefined"))
	{
		string = "";
	}
	return string;
}

function initForm()
{
    try
    {
        initParam();
        initAllPolGrid();      
        initInpBox();          
    }
    catch(re)
    {
        alert("��NBWMissionReassignInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
    }
}

//��ʼ�������
function initInpBox()
{
    try
    {
		fm.DefaultOperator.value = "";
		fm.DesignateOperator.value = "";
    }
    catch(ex)
    {
        alert("��NBWMissionReassignInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
    }
}

// ������Ϣ�б�ĳ�ʼ��
function initAllPolGrid()
{                               
    var iArray = new Array();
  
    try
    {
        iArray[0] = new Array();
        iArray[0][0] = "���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
        iArray[0][1] = "30px";            		//�п�
        iArray[0][2] = 30;            			//�����ֵ
        iArray[0][3] = 0;              			//�Ƿ���������,1��ʾ����0��ʾ������
        
        iArray[1] = new Array();
        iArray[1][0] = "Ͷ������";         		//����
        iArray[1][1] = "160px";            		//�п�
        iArray[1][2] = 100;            			//�����ֵ
        iArray[1][3] = 0;              			//�Ƿ���������,1��ʾ����0��ʾ������
        
        iArray[2] = new Array();
        iArray[2][0] = "����Լ״̬";         		//����
        iArray[2][1] = "160px";            		//�п�
        iArray[2][2] = 100;            			//�����ֵ
        iArray[2][3] = 0;              			//�Ƿ���������,1��ʾ����0��ʾ������
        
        iArray[3] = new Array();
        iArray[3][0] = "����Ա����";         		//����
        iArray[3][1] = "100px";            		//�п�
        iArray[3][2] = 100;            			//�����ֵ
        iArray[3][3] = 0;              			//�Ƿ���������,1��ʾ����0��ʾ������
        
        iArray[4] = new Array();
        iArray[4][0] = "��������";         		//����
        iArray[4][1] = "70px";            		//�п�
        iArray[4][2] = 100;            			//�����ֵ
        iArray[4][3] = 0;              			//�Ƿ���������,1��ʾ����0��ʾ������
            
        iArray[5] = new Array();
        iArray[5][0] = "����ʱ��";         		//����
        iArray[5][1] = "70px";            		//�п�
        iArray[5][2] = 200;            			//�����ֵ
        iArray[5][3] = 0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
        
        iArray[6] = new Array();
        iArray[6][0] = "ɨ������";         		//����
        iArray[6][1] = "70px";            		//�п�
        iArray[6][2] = 200;            			//�����ֵ
        iArray[6][3] = 0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
        
        iArray[7] = new Array();
        iArray[7][0] = "����Ա����";         		//����
        iArray[7][1] = "0px";            		//�п�
        iArray[7][2] = 60;            			//�����ֵ
        iArray[7][3] = 3;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
        
                    
        iArray[8] = new Array();
        iArray[8][0] = "�����������";         		//����
        iArray[8][1] = "0px";            		//�п�
        iArray[8][2] = 200;            			//�����ֵ
        iArray[8][3] = 3;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
        
        iArray[9] = new Array();
        iArray[9][0] = "�������������";         		//����
        iArray[9][1] = "0px";            		//�п�
        iArray[9][2] = 200;            			//�����ֵ
        iArray[9][3] = 3;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
        
        iArray[10] = new Array();
        iArray[10][0] = "�������Id";         		//����
        iArray[10][1] = "0px";            		//�п�
        iArray[10][2] = 60;            			//�����ֵ
        iArray[10][3] = 3;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
        
        
        AllPolGrid = new MulLineEnter( "document" , "AllPolGrid" ); 
        //��Щ���Ա�����loadMulLineǰ
        AllPolGrid.mulLineCount = 5;
        AllPolGrid.displayTitle = 1;
        AllPolGrid.locked = 1;
        AllPolGrid.canSel = 0;
        AllPolGrid.canChk = 1;
        AllPolGrid.hiddenPlus = 1;
        AllPolGrid.hiddenSubtraction = 1;
   //     AllPolGrid.selBoxEventFuncName = "setTextValue";
        AllPolGrid.loadMulLine(iArray);     
    
    }
    catch(ex)
    {
        alert("��NBWMissionReassignInit.jsp-->initAllPolGrid�����з����쳣:��ʼ���������!");
    }
}


</script>
