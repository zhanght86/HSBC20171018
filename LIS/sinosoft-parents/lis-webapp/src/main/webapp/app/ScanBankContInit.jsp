<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�ScanBankContInit.jsp
//�����ܣ���������Լɨ�������¼��
//�������ڣ�2005-07-18 11:10:36
//������  ��ccvip
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     //���ҳ��ؼ��ĳ�ʼ����
%>                            

<script language="JavaScript">

// �����ĳ�ʼ��������¼���֣�
function initInpBox()
{ 

  try
  {                                   
	// ������ѯ����
    document.all('PrtNo').value = '';
    document.all('ManageCom').value = '';
    document.all('InputDate').value = '';
  }
  catch(ex)
  {
    alert("��GroupUWAutoInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}
                            

function initForm()
{
  try
  {
    initInpBox();
    initSelfGrpGrid();
    initGrpGrid();
    easyQueryClickSelf();
  }
  catch(re)
  {
    alert("ProposalQueryInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

// ������Ϣ�б�ĳ�ʼ��
function initGrpGrid()
  {     
                             
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";            	//�п�
      iArray[0][2]=10;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="ӡˢ��";         	//����
      iArray[1][1]="140px";            	//�п�
      iArray[1][2]=170;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="�������";         	//����
      iArray[2][1]="120px";            	//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="ɨ������";         	//����
      iArray[3][1]="200px";            	//�п�
      iArray[3][2]=200;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������


      iArray[4]=new Array();
      iArray[4][0]="�����������";      //����
      iArray[4][1]="0px";            		//�п�
      iArray[4][2]=200;            			//�����ֵ
      iArray[4][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[5]=new Array();
      iArray[5][0]="�������������";    //����
      iArray[5][1]="0px";            		//�п�
      iArray[5][2]=200;            			//�����ֵ
      iArray[5][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[6]=new Array();
      iArray[6][0]="�������Id";      //����
      iArray[6][1]="0px";            		//�п�
      iArray[6][2]=60;            			//�����ֵ
      iArray[6][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������       
      
      iArray[7]=new Array();
      iArray[7][0]="��֤����";         		//����
      iArray[7][1]="0px";            		//�п�
      iArray[7][2]=60;            			//�����ֵ
      iArray[7][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������

      GrpGrid = new MulLineEnter( "fm" , "GrpGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      GrpGrid.mulLineCount = 5;   
      GrpGrid.displayTitle = 1;
      GrpGrid.locked = 1;
      GrpGrid.canSel = 1;
      GrpGrid.canChk = 0;
      GrpGrid.hiddenPlus = 1;
      GrpGrid.hiddenSubtraction = 1;        
      GrpGrid.loadMulLine(iArray);  
      }
      catch(ex)
      {
        alert(ex);
      }
}

// ������Ϣ�б�ĳ�ʼ��
function initSelfGrpGrid()
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
      iArray[1][0]="ӡˢ��";         		//����
      iArray[1][1]="140px";            		//�п�
      iArray[1][2]=170;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="�������";         		//����
      iArray[2][1]="120px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="ɨ������";         		//����
      iArray[3][1]="200px";            		//�п�
      iArray[3][2]=200;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������


      iArray[4]=new Array();
      iArray[4][0]="�����������";         		//����
      iArray[4][1]="0px";            		//�п�
      iArray[4][2]=200;            			//�����ֵ
      iArray[4][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[5]=new Array();
      iArray[5][0]="�������������";         		//����
      iArray[5][1]="0px";            		//�п�
      iArray[5][2]=200;            			//�����ֵ
      iArray[5][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[6]=new Array();
      iArray[6][0]="�������Id";         		//����
      iArray[6][1]="0px";            		//�п�
      iArray[6][2]=60;            			//�����ֵ
      iArray[6][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������     
      
      iArray[7]=new Array();
      iArray[7][0]="��֤����";         		//����
      iArray[7][1]="0px";            		//�п�
      iArray[7][2]=60;            			//�����ֵ
      iArray[7][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������   

      SelfGrpGrid = new MulLineEnter( "fm" , "SelfGrpGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      SelfGrpGrid.mulLineCount = 5;   
      SelfGrpGrid.displayTitle = 1;
      SelfGrpGrid.locked = 1;
      SelfGrpGrid.canSel = 1;
      SelfGrpGrid.canChk = 0;
      SelfGrpGrid.hiddenPlus = 1;
      SelfGrpGrid.hiddenSubtraction = 1;   
      SelfGrpGrid.selBoxEventFuncName = "InitGoToInput";     
      SelfGrpGrid.loadMulLine(iArray);  
      }
      catch(ex)
      {
        alert(ex);
      }
}
</script>
