<!--�û�У����-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     //���ҳ��ؼ��ĳ�ʼ����
%>                            

<script language="JavaScript">
//���ذ�ť��ʼ��
var str = "";
// �����ĳ�ʼ��������¼���֣�
function initInpBox()
{ 
  try
  {                                   
	// ��־��ѯ����
	
    fm.all('OtherNo').value = '';
    fm.all('OtherNoType').value = '';
    fm.all('IsPolFlag').value = '';    
    fm.all('PrtNo').value = '';
    fm.all('ManageCom').value = '';
    fm.all('Operator').value = '';    
    fm.all('MakeDate').value = '';
    fm.all('MakeTime').value = '';
    fm.all('ModifyDate').value = '';    
    fm.all('ModifyTime').value = '';
    
    fm.all('tOtherNo').value = '';
    fm.all('tOtherNoType').value = '';
    fm.all('tIsPolFlag').value = '';    
    fm.all('tPrtNo').value = '';
    fm.all('tManageCom').value = '';
    fm.all('tOperator').value = '';    
    fm.all('tMakeDate').value = '';
    fm.all('tMakeTime').value = '';
    fm.all('tModifyDate').value = '';    
    fm.all('tModifyTime').value = '';    
    
  }
  catch(ex)
  {
    alert("��LogQueryInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
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
    alert("��LogQueryInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}                                        

function initForm()
{
  try
  {
    initInpBox();
    initSelBox();    
	  initLogGrid();
  }
  catch(re)
  {
    alert("LogQueryInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

// ��־��Ϣ�б�ĳ�ʼ��
function initLogGrid()
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
      iArray[1][0]="��������";         		//����
      iArray[1][1]="120px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������     

      iArray[2]=new Array();
      iArray[2][0]="������������";         		//����
      iArray[2][1]="70px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������     

      iArray[3]=new Array();
      iArray[3][0]="ӡˢ����";         		//����
      iArray[3][1]="120px";            		//�п�
      iArray[3][2]=200;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="�������";         		//����
      iArray[4][1]="120px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[5]=new Array();
      iArray[5][0]="���һ���޸�����";         		//����
      iArray[5][1]="120px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������      
      
      LogGrid = new MulLineEnter( "fm" , "LogGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      LogGrid.mulLineCount = 10;   
      LogGrid.displayTitle = 1;
      LogGrid.locked = 1;
      LogGrid.canSel = 1;
      LogGrid.selBoxEventFuncName ="getLogDetail" ;     //���RadioBoxʱ��Ӧ��JS����     
      LogGrid.hiddenPlus = 1;
      LogGrid.hiddenSubtraction = 1;
      LogGrid.loadMulLine(iArray);       
    
      //��Щ����������loadMulLine����
      //LogGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}
</script>
