<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�UWAppInit.jsp
//�����ܣ�����Ͷ����Ϣ��ѯ
//�������ڣ�2002-06-19 11:10:36
//������  �� WHN
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>                          

<SCRIPT src="../common/javascript/Common.js"></SCRIPT>

<script language="JavaScript">
// �����ĳ�ʼ��������¼���֣�
function initInpBox()
{   
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
    alert("��UWAppInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}                                        

function initForm(tContNo,tCustomerNo,tType)
{
  try
  { //alert("########"+tContNo);
    //initInpBox();    
  	//initContGrid();
  	document.all("OldContNo").value=tContNo;
  	document.all("OldCustomerNo").value=tCustomerNo;
		initPolGrid();
		initNotPolGrid();
		initGrpPolGrid();
		initHide(tContNo,tCustomerNo,tType);
		//alert("tContNo "+tContNo);
		//alert("tCustomerNo "+tCustomerNo);
		if(tCustomerNo!=null && tCustomerNo!='')
		{
			queryPersonInfo(tCustomerNo);
			//queryCont();
			queryPol(tContNo,tCustomerNo);
			queryNotPol(tContNo,tCustomerNo);
			queryGrpPol(tContNo,tCustomerNo);
		}
		initButton();
		
  	//alert("ContNoHide+ "+fm.ContNoHide.value);
  }
  catch(re)
  {
    alert("UWAppInit.jsp-->InitForm�����з����쳣:��ʼ���������!"+re);
  }
}

//����δ�б�����������Ϣ
function initNotPolGrid()
{
      var iArray = new Array();
      try
      {
          iArray[0]=new Array();
          iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
          iArray[0][1]="30px";            		//�п�
          iArray[0][2]=10;            			//�����ֵ
          iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
          
          //���ձ���Ͷ������
          //���ձ������Ͷ������
          iArray[1]=new Array();
          iArray[1][0]="��ͬ��";         		//����
          iArray[1][1]="0px";            		//�п�
          iArray[1][2]=100;            			//�����ֵ
          iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
          
          iArray[2]=new Array();
          iArray[2][0]="ӡˢ��";         		//����
          iArray[2][1]="120px";            		//�п�
          iArray[2][2]=100;            			//�����ֵ
          iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
          
          iArray[3]=new Array();
          iArray[3][0]="��������";         		//����
          iArray[3][1]="120px";            		//�п�
          iArray[3][2]=100;            			//�����ֵ
          iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
           
          iArray[4]=new Array();
          iArray[4][0]="Ͷ����";         		//����
          iArray[4][1]="80px";            		//�п�
          iArray[4][2]=100;            			//�����ֵ
          iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
          
          iArray[5]=new Array();
          iArray[5][0]="��������";         		//����
          iArray[5][1]="80px";            		//�п�
          iArray[5][2]=100;            			//�����ֵ
          iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
           
          iArray[6]=new Array();
          iArray[6][0]="����";         		//����
          iArray[6][1]="100px";            		//�п�
          iArray[6][2]=200;            			//�����ֵ
          iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
          
          iArray[7]=new Array();
          iArray[7][0]="����";         		//����
          iArray[7][1]="60px";            		//�п�
          iArray[7][2]=200;            			//�����ֵ
          iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
          
          iArray[8]=new Array();
          iArray[8][0]="����";         		//����
          iArray[8][1]="100px";            		//�п�
          iArray[8][2]=100;            			//�����ֵ
          iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
          
          iArray[9]=new Array();
          iArray[9][0]="Ͷ����������";         		//����
          iArray[9][1]="80px";            		//�п�
          iArray[9][2]=100;            			//�����ֵ
          iArray[9][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

          iArray[10]=new Array();
          iArray[10][0]="Ͷ����״̬";         		//����
          iArray[10][1]="100px";            		//�п�
          iArray[10][2]=100;            			//�����ֵ
          iArray[10][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

          iArray[11]=new Array();
          iArray[11][0]="���";         		//����
          iArray[11][1]="40px";            		//�п�
          iArray[11][2]=100;            			//�����ֵ
          iArray[11][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

          iArray[12]=new Array();
          iArray[12][0]="����";         		//����
          iArray[12][1]="40px";            		//�п�
          iArray[12][2]=100;            			//�����ֵ
          iArray[12][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

          iArray[13]=new Array();
          iArray[13][0]="�ٱ�";         		//����
          iArray[13][1]="40px";            		//�п�
          iArray[13][2]=100;            			//�����ֵ
          iArray[13][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
          
          iArray[14]=new Array();
          iArray[14][0]="���۱��";         		//����
          iArray[14][1]="60px";            		//�п�
          iArray[14][2]=100;            			//�����ֵ
          iArray[14][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������   
          
          iArray[15]=new Array();
          iArray[15][0]="�����";         		//����
          iArray[15][1]="60px";            		//�п�
          iArray[15][2]=100;            			//�����ֵ
          iArray[15][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������   
          
          iArray[16]=new Array();
          iArray[16][0]="������֪";         		//����
          iArray[16][1]="60px";            		//�п�
          iArray[16][2]=100;            			//�����ֵ
          iArray[16][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������      
                    
          NotPolGrid = new MulLineEnter( "fm" , "NotPolGrid" ); 
          //��Щ���Ա�����loadMulLineǰ 
          //NotPolGrid.mulLineCount = 5;   
          NotPolGrid.displayTitle = 1;
          NotPolGrid.locked = 1;
          NotPolGrid.canSel = 1;
          NotPolGrid.hiddenPlus = 1;
          NotPolGrid.hiddenSubtraction = 1;
          NotPolGrid.selBoxEventFuncName = "ChoNotClick";
          NotPolGrid.loadMulLine(iArray);  
      }
      catch(ex)
      {
        alert(ex);
      }
	
}

// �ѳб�����������Ϣ
function initPolGrid()
  {                               
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";         			//�п�
      iArray[0][2]=10;          			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="��ͬ��";    	//����
      iArray[1][1]="0px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[2]=new Array();
      iArray[2][0]="ӡˢ��";         			//����
      iArray[2][1]="120px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="��������";         		//����
      iArray[3][1]="260px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[4]=new Array();
      iArray[4][0]="Ͷ����";         		//����
      iArray[4][1]="80px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="��������";         		//����
      iArray[5][1]="80px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[6]=new Array();
      iArray[6][0]="����ְҵ���";         		//����
      iArray[6][1]="80px";            		//�п�
      iArray[6][2]=100;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[7]=new Array();
      iArray[7][0]="����";         		//����
      iArray[7][1]="80px";            		//�п�
      iArray[7][2]=100;            			//�����ֵ
      iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[8]=new Array();
      iArray[8][0]="����";         		//����
      iArray[8][1]="80px";            		//�п�
      iArray[8][2]=100;            			//�����ֵ
      iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[9]=new Array();
      iArray[9][0]="����";         		//����
      iArray[9][1]="80px";            		//�п�
      iArray[9][2]=100;            			//�����ֵ
      iArray[9][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[10]=new Array();
      iArray[10][0]="������Ч����";         		//����
      iArray[10][1]="80px";            		//�п�
      iArray[10][2]=100;            			//�����ֵ
      iArray[10][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[11]=new Array();
      iArray[11][0]="����״̬";         		//����
      iArray[11][1]="80px";            		//�п�
      iArray[11][2]=100;            			//�����ֵ
      iArray[11][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[12]=new Array();
      iArray[12][0]="���";         		//����
      iArray[12][1]="80px";            		//�п�
      iArray[12][2]=100;            			//�����ֵ
      iArray[12][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[13]=new Array();
      iArray[13][0]="����";         		//����
      iArray[13][1]="80px";            		//�п�
      iArray[13][2]=100;            			//�����ֵ
      iArray[13][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[14]=new Array();
      iArray[14][0]="�˱�����";         		//����
      iArray[14][1]="80px";            		//�п�
      iArray[14][2]=100;            			//�����ֵ
      iArray[14][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[15]=new Array();
      iArray[15][0]="����";         		//����
      iArray[15][1]="80px";            		//�п�
      iArray[15][2]=100;            			//�����ֵ
      iArray[15][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[16]=new Array();
      iArray[16][0]="��ȫ";         		//����
      iArray[16][1]="80px";            		//�п�
      iArray[16][2]=100;            			//�����ֵ
      iArray[16][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      PolGrid = new MulLineEnter( "fm" , "PolGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      //PolGrid.mulLineCount = 10;   
      PolGrid.displayTitle = 1;
      PolGrid.locked = 1;
      PolGrid.canSel = 1;
      PolGrid.hiddenPlus = 1;
      PolGrid.hiddenSubtraction = 1;
      PolGrid.loadMulLine(iArray);
      
      PolGrid. selBoxEventFuncName = "ChoClick";
      
      //��Щ����������loadMulLine����
      //PolGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}

// ������Ϣ
function initGrpPolGrid()
  {                               
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";         			//�п�
      iArray[0][2]=10;          			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="��ͬ��";    	//����
      iArray[1][1]="0px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[2]=new Array();
      iArray[2][0]="ӡˢ��";         			//����
      iArray[2][1]="120px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="��������";         		//����
      iArray[3][1]="260px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[4]=new Array();
      iArray[4][0]="��������";         		//����
      iArray[4][1]="80px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="����";         		//����
      iArray[5][1]="80px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[6]=new Array();
      iArray[6][0]="����";         		//����
      iArray[6][1]="80px";            		//�п�
      iArray[6][2]=100;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[7]=new Array();
      iArray[7][0]="������Ч����";         		//����
      iArray[7][1]="80px";            		//�п�
      iArray[7][2]=100;            			//�����ֵ
      iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[8]=new Array();
      iArray[8][0]="����״̬";         		//����
      iArray[8][1]="80px";            		//�п�
      iArray[8][2]=100;            			//�����ֵ
      iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[9]=new Array();
      iArray[9][0]="����";         		//����
      iArray[9][1]="80px";            		//�п�
      iArray[9][2]=100;            			//�����ֵ
      iArray[9][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[10]=new Array();
      iArray[10][0]="��ȫ";         		//����
      iArray[10][1]="80px";            		//�п�
      iArray[10][2]=100;            			//�����ֵ
      iArray[10][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[11]=new Array();
      iArray[11][0]="ְҵ���";         		//����
      iArray[11][1]="80px";            		//�п�
      iArray[11][2]=100;            			//�����ֵ
      iArray[11][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[12]=new Array();
      iArray[12][0]="�Ƿ��ѳб�";         		//����
      iArray[12][1]="0px";            		//�п�
      iArray[12][2]=100;            			//�����ֵ
      iArray[12][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[13]=new Array();
      iArray[13][0]="�ŵ���ͬ��";         		//����
      iArray[13][1]="0px";            		//�п�
      iArray[13][2]=100;            			//�����ֵ
      iArray[13][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      GrpPolGrid = new MulLineEnter( "fm" , "GrpPolGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      //GrpPolGrid.mulLineCount = 10;   
      GrpPolGrid.displayTitle = 1;
      GrpPolGrid.locked = 1;
      GrpPolGrid.canSel = 1;
      GrpPolGrid.hiddenPlus = 1;
      GrpPolGrid.hiddenSubtraction = 1;
      GrpPolGrid.loadMulLine(iArray);
      
      GrpPolGrid. selBoxEventFuncName = "ChoGrpClick";
      
      //��Щ����������loadMulLine����
      //GrpPolGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}

function initContGrid()
{
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";            		//�п�
      iArray[0][2]=30;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="��ͬ��";         		//����
      iArray[1][1]="160px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[2]=new Array();
      iArray[2][0]="ӡˢ��";         		//����
      iArray[2][1]="160px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="Ͷ����Ч��";         		//����
      iArray[3][1]="160px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="Ͷ������";         		//����
      iArray[4][1]="120px";            		//�п�
      iArray[4][2]=200;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[5]=new Array();
      iArray[5][0]="����";         		//����
      iArray[5][1]="120px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[6]=new Array();
      iArray[6][0]="����";         		//����
      iArray[6][1]="120px";            		//�п�
      iArray[6][2]=100;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[7]=new Array();
      iArray[7][0]="����";         		//����
      iArray[7][1]="100px";            		//�п�
      iArray[7][2]=200;            			//�����ֵ
      iArray[7][3]=0;  
      
      ContGrid = new MulLineEnter( "fm" , "ContGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      ContGrid.mulLineCount = 3;   
      ContGrid.displayTitle = 1;
      ContGrid.hiddenPlus = 1;
      ContGrid.hiddenSubtraction = 1;
      ContGrid.locked = 1;
      ContGrid.canSel = 1;
      ContGrid.loadMulLine(iArray);  
      
      ContGrid.selBoxEventFuncName = "easyQueryClick";
      
      //��Щ����������loadMulLine����
      //InsuredGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}

function initHide(tContNo,tCustomerNo,tType)
{
	//alert(tType);
  if(tType=='1')
  {
	document.all('AppntNoHide').value= tCustomerNo;
	document.all('ContNoHide').value = tContNo;
	document.all('type').value = "1";
	}
	if(tType=='2')
	{
	document.all('InsureNoHide').value= tCustomerNo;
	document.all('ContNoHide').value = tContNo;	
	document.all('type').value = "2";	
	}
}

</script>
