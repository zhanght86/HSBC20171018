<%
//**************************************************************************************************
//Name��LPStateQueryInit.jsp
//Function���ⰸ״̬��ѯҳ��
//Author�� wangjm
//Date: 2006-4-7
//Desc: 
//**************************************************************************************************
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

    document.all('ClmNo').value = '';
    document.all('CustomerNo').value = '';
    document.all('GrpContNo').value = '';
    document.all('CustomerName').value = '';
    document.all('ManageCom').value = nullToEmpty(manageCom);
  }
  catch(ex)
  {
    alert("��ProposalQueryInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}

// ������ĳ�ʼ��
function initSelBox()
{  
  try 
  {
//    setOption("t_sex","0=��&1=Ů&2=����");      
//    setOption("sex","0=��&1=Ů&2=����");        
//    setOption("reduce_flag","0=����״̬&1=�����");
//    setOption("pad_flag","0=����&1=�潻");   
  }
  catch(ex)
  {
    alert("��ProposalQueryInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
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
    initInpBox();
    initSelBox();   
    initClaimGrid();
    initClaimStateGrid();   
  }
  catch(re)
  {
    alert("ProposalQueryInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

// �ⰸ��Ϣ�б�ĳ�ʼ��
function initClaimGrid()
  {     
    var iArray = new Array();
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";            //�п�
      iArray[0][2]=10;            //�����ֵ
      iArray[0][3]=0;              //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="������";        //����
      iArray[1][1]="80px";           //�п�
      iArray[1][2]=100;            //�����ֵ
      iArray[1][3]=0;              //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="��λ����";       //����
      iArray[2][1]="120px";          //�п�
      iArray[2][2]=100;            //�����ֵ
      iArray[2][3]=0;              //�Ƿ���������,1��ʾ����0��ʾ������
      iArray[3]=new Array();
      iArray[3][0]="���屣����";     //����
      iArray[3][1]="80px";         //�п�
      iArray[3][2]=100;            //�����ֵ
      iArray[3][3]=0;              //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="����/��������Ա";  //����
      iArray[4][1]="80px";          //�п�
      iArray[4][2]=100;            //�����ֵ
      iArray[4][3]=0;              //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="�������";       //����
      iArray[5][1]="80px";         //�п�
      iArray[5][2]=100;            //�����ֵ
      iArray[5][3]=0;              //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[6]=new Array();
      iArray[6][0]="";             //����
      iArray[6][1]="0px";         //�п�
      iArray[6][2]=100;            //�����ֵ
      iArray[6][3]=3;              //�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[7]=new Array();
      iArray[7][0]="";         //����
      iArray[7][1]="0px";         //�п�
      iArray[7][2]=30;           //�����ֵ
      iArray[7][3]=3;            //�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[8]=new Array();
      iArray[8][0]="����״̬";             //����
      iArray[8][1]="80px";         //�п�
      iArray[8][2]=100;            //�����ֵ
      iArray[8][3]=0;              //�Ƿ���������,1��ʾ����0��ʾ������      
      
      ClaimGrid = new MulLineEnter( "document" , "ClaimGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      ClaimGrid.mulLineCount = 5;   
      ClaimGrid.displayTitle = 1;
      ClaimGrid.locked = 1;
      ClaimGrid.canSel = 1;
 //   ClaimGrid.canChk = 0;
      ClaimGrid.hiddenPlus = 1;
      ClaimGrid.hiddenSubtraction = 1;
      ClaimGrid.selBoxEventFuncName ="displayClick";
      ClaimGrid.loadMulLine(iArray);      
   }
    catch(ex)
    {
        alert(ex);
    }
}

// �ⰸ״̬�б�ĳ�ʼ��
function  initClaimStateGrid()
  {   
                           
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";         //�п�
      iArray[0][2]=10;            //�����ֵ
      iArray[0][3]=0;              //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="�ⰸ״̬��ϸ";     //����
      iArray[1][1]="200px";           //�п�
      iArray[1][2]=100;            //�����ֵ
      iArray[1][3]=0;              //�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[2]=new Array();
      iArray[2][0]="��������";         //����
      iArray[2][1]="200px";            //�п�
      iArray[2][2]=100;              //�����ֵ
      iArray[2][3]=0;                 //�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[3]=new Array();
      iArray[3][0]="���˲���Ա";      //����
      iArray[3][1]="100px";          //�п�
      iArray[3][2]=100;            //�����ֵ
      iArray[3][3]=0;              //�Ƿ���������,1��ʾ����0��ʾ������

      ClaimStateGrid = new MulLineEnter( "document" , "ClaimStateGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      ClaimStateGrid.mulLineCount = 5;   
      ClaimStateGrid.displayTitle = 1;
      ClaimStateGrid.locked = 1;
      ClaimStateGrid.hiddenPlus = 1;
      ClaimStateGrid.hiddenSubtraction = 1;
      ClaimStateGrid.canSel = 0;
      ClaimStateGrid.canChk = 0;
      ClaimStateGrid.loadMulLine(iArray);  
    }
      catch(ex)
      {
        alert(ex);
      }
}
</script>