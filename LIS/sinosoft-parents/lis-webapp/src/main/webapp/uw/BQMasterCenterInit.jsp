<%
//�������ƣ�ProposalApproveInit.jsp
//�����ܣ�
//�������ڣ�2002-06-19 11:10:36
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>                          

<script language="JavaScript">

//��ʼ��ҳ�洫�ݹ����Ĳ���
function initParam()
{
   document.all('Operator').value = nullToEmpty(Operator );
   document.all('ComCode').value = nullToEmpty(ComCode);
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

// �����ĳ�ʼ��������¼���֣�
function initInpBox()
{ 

  try
  {                                   
	// ������ѯ����
    document.all('ContNo').value = '';
    document.all('ManageCom').value = '';
    //document.all('AgentCode').value = '';
    //document.all('AgentGroup').value = '';
  }
  catch(ex)
  {
  	alert("��һ������");
    alert("��ProposalApproveInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}

// ������ĳ�ʼ��
function initSelBox()
{  
  try                 
  {
  }
  catch(ex)
  {
  	alert("�ڶ�������");
    alert("��ProposalApproveInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}                                        

function initForm()
{
  try
  {
  initParam();
	initInpBox();  
	initSelBox();      
	initPolGrid();
	initSelfPolGrid();
	easyQueryClickSelf();
  }
  catch(re)
  {
    alert("ProposalApproveInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}


// ������Ϣ�б��ĳ�ʼ��
function initPolGrid()
  {                               
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="50px";            		//�п�
      iArray[0][2]=10;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ������0��ʾ������
      
      iArray[1]=new Array();
      iArray[1][0]="������";         		//����
      iArray[1][1]="120px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ������0��ʾ������
      
      iArray[2]=new Array();
      iArray[2][0]="��������";         		//����
      iArray[2][1]="0px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ������0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="��ͬ��";         		//����
      iArray[3][1]="120px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ������0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="Ͷ����";         		//����
      iArray[4][1]="120px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ������0��ʾ������
      
      iArray[5]=new Array();
      iArray[5][0]="�����������";         		//����
      iArray[5][1]="0px";            		//�п�
      iArray[5][2]=200;            			//�����ֵ
      iArray[5][3]=3;              			//�Ƿ���������,1��ʾ������0��ʾ������ 
      
      iArray[6]=new Array();
      iArray[6][0]="�������������";         		//����
      iArray[6][1]="0px";            		//�п�
      iArray[6][2]=200;            			//�����ֵ
      iArray[6][3]=3;              			//�Ƿ���������,1��ʾ������0��ʾ������ 

      iArray[7]=new Array();
      iArray[7][0]="�������Id";         		//����
      iArray[7][1]="0px";            		//�п�
      iArray[7][2]=60;            			//�����ֵ
      iArray[7][3]=3;              			//�Ƿ���������,1��ʾ������0��ʾ������ 
      
      iArray[8]=new Array();
      iArray[8][0]="��������";         		//����
      iArray[8][1]="60px";            		//�п�
      iArray[8][2]=200;            			//�����ֵ
      iArray[8][3]=0;              			//�Ƿ���������,1��ʾ������0��ʾ������ 

      iArray[9]=new Array();
      iArray[9][0]="��������";         		//����
      iArray[9][1]="50px";            		//�п�
      iArray[9][2]=60;            			//�����ֵ
      iArray[9][3]=0;              			//�Ƿ���������,1��ʾ������0��ʾ������ 


      
      PolGrid = new MulLineEnter( "fm" , "PolGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      PolGrid.mulLineCount = 5;   
      PolGrid.displayTitle = 1;
      PolGrid.locked = 1;
      PolGrid.canSel = 1;
      PolGrid.hiddenPlus = 1;
      PolGrid.hiddenSubtraction = 1;
      PolGrid.selBoxEventFuncName = "HighlightAllRow";
      PolGrid.loadMulLine(iArray);  
      }
      catch(ex)
      {
      	alert("����������");
        alert(ex);
      }
}

// ���˱�����Ϣ�б��ĳ�ʼ��
function initSelfPolGrid()
{                               
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="50px";            		//�п�
      iArray[0][2]=10;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ������0��ʾ������
      
      iArray[1]=new Array();
      iArray[1][0]="������";         		//����
      iArray[1][1]="120px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ������0��ʾ������
      
      iArray[2]=new Array();
      iArray[2][0]="��������";         		//����
      iArray[2][1]="0px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ������0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="��ͬ��";         		//����
      iArray[3][1]="120px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ������0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="Ͷ����";         		//����
      iArray[4][1]="120px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ������0��ʾ������
      
      iArray[5]=new Array();
      iArray[5][0]="�����������";         		//����
      iArray[5][1]="0px";            		//�п�
      iArray[5][2]=200;            			//�����ֵ
      iArray[5][3]=3;              			//�Ƿ���������,1��ʾ������0��ʾ������ 
      
      iArray[6]=new Array();
      iArray[6][0]="�������������";         		//����
      iArray[6][1]="0px";            		//�п�
      iArray[6][2]=200;            			//�����ֵ
      iArray[6][3]=3;              			//�Ƿ���������,1��ʾ������0��ʾ������ 

      iArray[7]=new Array();
      iArray[7][0]="�������Id";         		//����
      iArray[7][1]="0px";            		//�п�
      iArray[7][2]=60;            			//�����ֵ
      iArray[7][3]=3;              			//�Ƿ���������,1��ʾ������0��ʾ������ 

      iArray[8]=new Array();
      iArray[8][0]="��������";         		//����
      iArray[8][1]="60px";            		//�п�
      iArray[8][2]=200;            			//�����ֵ
      iArray[8][3]=0;              			//�Ƿ���������,1��ʾ������0��ʾ������ 

      iArray[9]=new Array();
      iArray[9][0]="��������";         		//����
      iArray[9][1]="50px";            		//�п�
      iArray[9][2]=60;            			//�����ֵ
      iArray[9][3]=0;              			//�Ƿ���������,1��ʾ������0��ʾ������ 

      SelfPolGrid = new MulLineEnter( "fm" , "SelfPolGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      SelfPolGrid.mulLineCount = 5;   
      SelfPolGrid.displayTitle = 1;
      SelfPolGrid.locked = 1;
      SelfPolGrid.canSel = 1;
      SelfPolGrid.hiddenPlus = 1;
      SelfPolGrid.hiddenSubtraction = 1;
      SelfPolGrid.selBoxEventFuncName = "QuestQuery";    
      SelfPolGrid.loadMulLine(iArray);  
      }
      catch(ex)
      {
        alert(ex);
      }
}




</script>