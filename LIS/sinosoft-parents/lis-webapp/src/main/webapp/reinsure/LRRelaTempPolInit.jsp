<%@include file="/i18n/language.jsp"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�LRRelaTempPolInit.jsp
//�����ܣ��������α���
//�������ڣ�2007-09-29 11:10:36
//������  ��zhangbin
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  
<%
	String tRIContNo 		= request.getParameter("RIContNo");
	String tRIPreceptNo = request.getParameter("RIPreceptNo");
	String tCalFeeType 	=  request.getParameter("CalFeeType");
	String tCalFeeTerm 	=  request.getParameter("CalFeeTerm");
	String tOperateNo  	=  request.getParameter("OperateNo");
	String tCodeType 	 	=  request.getParameter("CodeType");
	String tSerialNo 	 	=  request.getParameter("SerialNo");
%>                            

<SCRIPT src="../common/javascript/Common.js"></SCRIPT>

<script type="text/javascript">


// �����ĳ�ʼ��������¼���֣�
function initInpBox(){
	fm.RIContNo.value		 	='<%=tRIContNo%>';
	fm.RIPreceptNo.value 	='<%=tRIPreceptNo%>';
	fm.CalFeeType.value	 	='<%=tCalFeeType%>';
	fm.CalFeeTerm.value	 	='<%=tCalFeeTerm%>';
	fm.OperateNo.value	 	='<%=tOperateNo%>';
	fm.OperateType.value 	='<%=tCodeType%>';
	fm.SerialNo.value 		='<%=tSerialNo%>';
	divGrpTempInsuListTitle.style.display='';
	
	//������ֻ��������ת����־ 
	fm.DeTailFlag.value = "1"; //1-������ 2-������ 
	
}

// ������ĳ�ʼ��
function initSelBox()
{  
  try
  {
  }
  catch(ex)
  {
    myAlert("��ReInsureInit.jsp-->"+"InitSelBox�����з����쳣:��ʼ���������!"); 
  }
}                                        

function initForm()
{
  try
  {
	  initInpBox();
		initSelBox();
		initGrpTempInsuListGrid();
		initIndTempListGrid();
		initSearchResultGrid();
		initRelaListGrid();
		initIndRelaListGrid();
		
		if(fm.OperateType.value=='01'){ //����
			divIndPart1.style.display='';
			divGrpPart1.style.display='none';
			divIndPart2.style.display='';
			divGrpPart2.style.display='none';
			fm.DeleteAllButton.style.display='none';
			
		}else if(fm.OperateType.value=='05'){ //����
			divIndPart1.style.display='none';
			divGrpPart1.style.display='';
			divIndPart2.style.display='none';
			divGrpPart2.style.display='';
			fm.DeleteAllButton.style.display='';
			
		}
		if(fm.OperateNo.value!=null&&fm.OperateNo.value!='null'&&fm.OperateNo.value!=''){
			divGrpTempInsuListTitle.style.display='none';
			QueryPagePolInfo(); //��ʾ�ٷֱ�����Ϣ
		}else{
			divGrpTempInsuListTitle.style.display='';
			showTempGrp(); //��ʾ���͹������ٷ���������,�����������ٷ�
		}
  }
  catch(re)
  {
    myAlert("TempReInAnswerInit.jsp-->"+"��ʼ���������!");
  }
}



//�����ٷ������б�
function initGrpTempInsuListGrid(){
	var iArray = new Array();
  try
  {
	  iArray[0]=new Array();
	  iArray[0][0]="���";         		//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
	  iArray[0][1]="30px";         		//�п�
	  iArray[0][2]=10;          			//�����ֵ
	  iArray[0][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
	
	  iArray[1]=new Array();
	  iArray[1][0]="ӡˢ��";    		//����
	  iArray[1][1]="80px";            //�п�
	  iArray[1][2]=100;            		//�����ֵ
	  iArray[1][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
	  
		if(fm.OperateType.value=='01'){
	  	iArray[2]=new Array();
	  	iArray[2][0]="Ͷ��������";  //����
	  	iArray[2][1]="80px";            //�п�
	  	iArray[2][2]=100;            		//�����ֵ
	  	iArray[2][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
	  }else if(fm.OperateType.value=='05'){
	  	iArray[2]=new Array();
	  	iArray[2][0]="����Ͷ������";  //����
	  	iArray[2][1]="80px";            //�п�
	  	iArray[2][2]=100;            		//�����ֵ
	  	iArray[2][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
	  }
	  
	  if(fm.OperateType.value=='01'){
	  	iArray[3]=new Array();
	  	iArray[3][0]="������";  				//����
	  	iArray[3][1]="80px";            //�п�
	  	iArray[3][2]=100;            		//�����ֵ
	  	iArray[3][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
		}else if(fm.OperateType.value=='05'){
			iArray[3]=new Array();
	  	iArray[3][0]="���屣����";  		//����
	  	iArray[3][1]="80px";            //�п�
	  	iArray[3][2]=100;            		//�����ֵ
	  	iArray[3][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
		}
		
	  iArray[4]=new Array();
	  iArray[4][0]="�ٷ�״̬";    		//����
	  iArray[4][1]="60px";            //�п�
	  iArray[4][2]=100;            		//�����ֵ
	  iArray[4][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
		
		iArray[5]=new Array();
	  iArray[5][0]="�ٷ�״̬����";    //����
	  iArray[5][1]="60px";            //�п�
	  iArray[5][2]=100;            		//�����ֵ
	  iArray[5][3]=3;              		//�Ƿ���������,1��ʾ����0��ʾ������
		
		iArray[6]=new Array();
	  iArray[6][0]="����״̬";    		//����
	  iArray[6][1]="60px";            //�п�
	  iArray[6][2]=100;            		//�����ֵ
	  iArray[6][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
		
		iArray[7]=new Array();
	  iArray[7][0]="���屣��״̬����"; //����
	  iArray[7][1]="0px";            //�п�
	  iArray[7][2]=100;            		//�����ֵ
	  iArray[7][3]=3;              		//�Ƿ���������,1��ʾ����0��ʾ������
	  
	  iArray[8]=new Array();
	  iArray[8][0]="�ٷֹ������к�"; //����
	  iArray[8][1]="60px";            //�п�
	  iArray[8][2]=100;            		//�����ֵ
	  iArray[8][3]=3;              		//�Ƿ���������,1��ʾ����0��ʾ������

	  GrpTempInsuListGrid = new MulLineEnter( "fm" , "GrpTempInsuListGrid" ); 
	  //��Щ���Ա�����loadMulLineǰ
	  GrpTempInsuListGrid.mulLineCount = 0;   
	  GrpTempInsuListGrid.displayTitle = 1;
	  GrpTempInsuListGrid.locked = 1;
	  GrpTempInsuListGrid.hiddenPlus = 1;
	  GrpTempInsuListGrid.canSel = 1;
	  GrpTempInsuListGrid.hiddenSubtraction = 1;
	  GrpTempInsuListGrid.selBoxEventFuncName = "QueryPolInfo"; 
	  GrpTempInsuListGrid.loadMulLine(iArray);  
  }
  catch(ex){
    myAlert(ex);
  }
}

function initIndTempListGrid(){
	var iArray = new Array();
  try
  {
		iArray[0]=new Array();
	  iArray[0][0]="���";         		//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
	  iArray[0][1]="30px";         		//�п�
	  iArray[0][2]=10;          			//�����ֵ
	  iArray[0][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
	
	  iArray[1]=new Array();
	  iArray[1][0]="��������";    		//����
	  iArray[1][1]="80px";            //�п�
	  iArray[1][2]=100;            		//�����ֵ
	  iArray[1][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
	
	  iArray[2]=new Array();
	  iArray[2][0]="���ֱ���";        //����
	  iArray[2][1]="80px";            //�п�
	  iArray[2][2]=100;            		//�����ֵ
	  iArray[2][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
	  
	  iArray[3]=new Array();
	  iArray[3][0]="���α���";        //����
	  iArray[3][1]="60px";            //�п�
	  iArray[3][2]=100;            		//�����ֵ
	  if(fm.DeTailFlag.value=='1'){
	  	iArray[3][3]=3;              		//�Ƿ���������,1��ʾ����0��ʾ������
	  }else{
	  	iArray[3][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
	  }
	  
	  iArray[4]=new Array();
	  iArray[4][0]="����";         		//����
	  iArray[4][1]="60px";            //�п�
	  iArray[4][2]=100;            		//�����ֵ
	  iArray[4][3]=3;              		//�Ƿ���������,1��ʾ����0��ʾ������
	  
	  iArray[5]=new Array();
	  iArray[5][0]="����";         		//����
	  iArray[5][1]="60px";            //�п�
	  iArray[5][2]=100;            		//�����ֵ
	  iArray[5][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
	  
	  iArray[6]=new Array();
	  iArray[6][0]="����";         		//����
	  iArray[6][1]="60px";            //�п�
	  iArray[6][2]=100;            		//�����ֵ
	  iArray[6][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
	
	  iArray[7]=new Array();
	  iArray[7][0]="���ձ���";        //����
	  iArray[7][1]="60px";            //�п�
	  iArray[7][2]=100;            		//�����ֵ
	  iArray[7][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
	  
	  iArray[8]=new Array();
	  iArray[8][0]="�ۼƷ��ձ���";    //����
	  iArray[8][1]="0px";           	//�п�
	  iArray[8][2]=100;            		//�����ֵ
	  iArray[8][3]=3;              		//�Ƿ���������,1��ʾ����0��ʾ������
	  
	  iArray[9]=new Array();
	  iArray[9][0]="Ͷ��������";        //����
	  iArray[9][1]="100px";           //�п�
	  iArray[9][2]=100;            		//�����ֵ
	  iArray[9][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
	  
	  iArray[10]=new Array();
	  iArray[10][0]="�˱�����";        //����
	  iArray[10][1]="100px";           //�п�
	  iArray[10][2]=100;           		 //�����ֵ
	  iArray[10][3]=0;            		 //�Ƿ���������,1��ʾ����0��ʾ������
	  
	  iArray[11]=new Array();
	  iArray[11][0]="�˱����۱���";    //����
	  iArray[11][1]="0px";           	 //�п�
	  iArray[11][2]=100;           		 //�����ֵ
	  iArray[11][3]=3;            		 //�Ƿ���������,1��ʾ����0��ʾ������
	  
	  iArray[12]=new Array();
	  iArray[12][0]="�˱����۱��뱸��"; //����
	  iArray[12][1]="0px";           //�п�
	  iArray[12][2]=100;           		 //�����ֵ
	  iArray[12][3]=3;             		 //�Ƿ���������,1��ʾ����0��ʾ������
	  
	  iArray[13]=new Array();
	  iArray[13][0]="�����˱���"; //����
	  iArray[13][1]="0px";           //�п�
	  iArray[13][2]=100;           		 //�����ֵ
	  iArray[13][3]=3;             		 //�Ƿ���������,1��ʾ����0��ʾ������
	  
	  iArray[14]=new Array();
	  iArray[14][0]="�ٷ��������к�"; //����
	  iArray[14][1]="60px";           //�п�
	  iArray[14][2]=100;           		 //�����ֵ
	  iArray[14][3]=3;             		 //�Ƿ���������,1��ʾ����0��ʾ������
	
	  IndTempListGrid = new MulLineEnter( "fm" , "IndTempListGrid" ); 
	  //��Щ���Ա�����loadMulLineǰ
	  IndTempListGrid.mulLineCount = 0;   
	  IndTempListGrid.displayTitle = 1;
	  IndTempListGrid.locked = 1;
	  IndTempListGrid.hiddenPlus = 1;
	  IndTempListGrid.canSel = 0;
	  IndTempListGrid.canChk = 1;
	  IndTempListGrid.hiddenSubtraction = 1;
	  IndTempListGrid.loadMulLine(iArray);  
  }
  catch(ex){
    myAlert(ex);
  }
}

//�ŵ��˱����
function initGrpUWResultGrid(){
	var iArray = new Array();
  try
  {
	  iArray[0]=new Array();
	  iArray[0][0]="���";         		//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
	  iArray[0][1]="30px";         		//�п�
	  iArray[0][2]=10;          			//�����ֵ
	  iArray[0][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
	
	  iArray[1]=new Array();
	  iArray[1][0]="ӡˢ��";    		//����
	  iArray[1][1]="80px";            //�п�
	  iArray[1][2]=100;            		//�����ֵ
	  iArray[1][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
	
	  iArray[2]=new Array();
	  iArray[2][0]="����Ͷ������";        //����
	  iArray[2][1]="80px";            //�п�
	  iArray[2][2]=100;            		//�����ֵ
	  iArray[2][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
	  
	  iArray[3]=new Array();
	  iArray[3][0]="���ϼƻ�";        //����
	  iArray[3][1]="60px";            //�п�
	  iArray[3][2]=100;            		//�����ֵ
	  iArray[3][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
	  
	  iArray[4]=new Array();
	  iArray[4][0]="�ٱ���ͬ";         		//����
	  iArray[4][1]="60px";            //�п�
	  iArray[4][2]=100;            		//�����ֵ
	  iArray[4][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
	
	
	  iArray[5]=new Array();
	  iArray[5][0]="�ٱ�����";         		//����
	  iArray[5][1]="60px";            //�п�
	  iArray[5][2]=100;            		//�����ֵ
	  iArray[5][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
	
	  iArray[6]=new Array();
	  iArray[6][0]="�ٷ��Ժ˹���";         		//����
	  iArray[6][1]="60px";            //�п�
	  iArray[6][2]=100;            		//�����ֵ
	  iArray[6][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
	  
	  iArray[7]=new Array();
	  iArray[7][0]=" �Ժ˽��";    //����
	  iArray[7][1]="150px";            //�п�
	  iArray[7][2]=100;            		//�����ֵ
	  iArray[7][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
	
	  GrpUWResultGrid = new MulLineEnter( "fm" , "GrpUWResultGrid" ); 
	  //��Щ���Ա�����loadMulLineǰ
	  GrpUWResultGrid.mulLineCount = 0;   
	  GrpUWResultGrid.displayTitle = 1;
	  GrpUWResultGrid.locked = 1;
	  GrpUWResultGrid.hiddenPlus = 1;
	  GrpUWResultGrid.canSel = 0;
	  GrpUWResultGrid.hiddenSubtraction = 1;
	  GrpUWResultGrid.loadMulLine(iArray);  
  }
  catch(ex){
    myAlert(ex);
  }
}
//���˺˱����
function initIndUWResultGrid(){
		var iArray = new Array();
    try
    {
	    iArray[0]=new Array();
	    iArray[0][0]="���";         		//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
	    iArray[0][1]="30px";         		//�п�
	    iArray[0][2]=10;          			//�����ֵ
	    iArray[0][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
	
	    iArray[1]=new Array();
	    iArray[1][0]="ӡˢ��";    		//����
	    iArray[1][1]="100px";            //�п�
	    iArray[1][2]=100;            		//�����ֵ
	    iArray[1][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
	
	    iArray[2]=new Array();
	    iArray[2][0]="����Ͷ������";        //����
	    iArray[2][1]="100px";            //�п�
	    iArray[2][2]=100;            		//�����ֵ
	    iArray[2][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
	    
	    iArray[3]=new Array();
	    iArray[3][0]="���ϼƻ�";        //����
	    iArray[3][1]="60px";            //�п�
	    iArray[3][2]=100;            		//�����ֵ
	    iArray[3][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
	    
	    iArray[4]=new Array();
	    iArray[4][0]="���˱�������";         		//����
	    iArray[4][1]="100px";            //�п�
	    iArray[4][2]=100;            		//�����ֵ
	    iArray[4][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
	
	
	    iArray[5]=new Array();
	    iArray[5][0]="Ͷ�������ֺ���";         		//����
	    iArray[5][1]="100px";            //�п�
	    iArray[5][2]=100;            		//�����ֵ
	    iArray[5][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
	
	    iArray[6]=new Array();
	    iArray[6][0]="����������";         		//����
	    iArray[6][1]="60px";            //�п�
	    iArray[6][2]=100;            		//�����ֵ
	    iArray[6][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
	    
	    iArray[7]=new Array();
	    iArray[7][0]="�����˱���";    //����
	    iArray[7][1]="100px";            //�п�
	    iArray[7][2]=100;            		//�����ֵ
	    iArray[7][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
	    
	    iArray[8]=new Array();
	    iArray[8][0]="���ֱ���";         	//����
	    iArray[8][1]="80px";            //�п�
	    iArray[8][2]=100;            		//�����ֵ
	    iArray[8][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
	    
	    iArray[9]=new Array();
	    iArray[9][0]="���δ���";         	//����
	    iArray[9][1]="80px";            //�п�
	    iArray[9][2]=100;            		//�����ֵ
	    iArray[9][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
	    
	    iArray[10]=new Array();
	    iArray[10][0]="�ٱ���ͬ";      //����
	    iArray[10][1]="100px";            //�п�
	    iArray[10][2]=100;            	 //�����ֵ
	    iArray[10][3]=0;              	 //�Ƿ���������,1��ʾ����0��ʾ������
	    
	    iArray[11]=new Array();
	    iArray[11][0]="�ٱ�����";    //����
	    iArray[11][1]="100px";            //�п�
	    iArray[11][2]=100;            		//�����ֵ
	    iArray[11][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
	    
	    iArray[12]=new Array();
	    iArray[12][0]="�ٷ��Ժ˹���";      //����
	    iArray[12][1]="100px";            //�п�
	    iArray[12][2]=100;            	 //�����ֵ
	    iArray[12][3]=0;              	 //�Ƿ���������,1��ʾ����0��ʾ������
	    
	    iArray[13]=new Array();
	    iArray[13][0]="�Ժ˽��";    //����
	    iArray[13][1]="200px";            //�п�
	    iArray[13][2]=200;            		//�����ֵ
	    iArray[13][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
	
	    IndUWResultGrid = new MulLineEnter( "fm" , "IndUWResultGrid" ); 
	    //��Щ���Ա�����loadMulLineǰ
	    IndUWResultGrid.mulLineCount = 0;   
	    IndUWResultGrid.displayTitle = 1;
	    IndUWResultGrid.locked = 1;
	    IndUWResultGrid.hiddenPlus = 1;
	    IndUWResultGrid.canSel = 0;
	    IndUWResultGrid.hiddenSubtraction = 1;
	    IndUWResultGrid.loadMulLine(iArray);  
    }
    catch(ex){
      myAlert(ex);
    }
}


function initSearchResultGrid(){
		var iArray = new Array();
    try
    {
	    iArray[0]=new Array();
	    iArray[0][0]="���";         		//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
	    iArray[0][1]="30px";         		//�п�
	    iArray[0][2]=10;          			//�����ֵ
	    iArray[0][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
	
	    iArray[1]=new Array();
	    iArray[1][0]="ӡˢ��";    		//����
	    iArray[1][1]="100px";            //�п�
	    iArray[1][2]=100;            		//�����ֵ
	    iArray[1][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
	
	    iArray[2]=new Array();
	    iArray[2][0]="����Ͷ������";        //����
	    iArray[2][1]="100px";            //�п�
	    iArray[2][2]=100;            		//�����ֵ
	    iArray[2][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
	    
	    iArray[3]=new Array();
	    iArray[3][0]="���ϼƻ�";        //����
	    iArray[3][1]="60px";            //�п�
	    iArray[3][2]=100;            		//�����ֵ
	    iArray[3][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
	    
	    iArray[4]=new Array();
	    iArray[4][0]="���˱�������";         		//����
	    iArray[4][1]="100px";            //�п�
	    iArray[4][2]=100;            		//�����ֵ
	    iArray[4][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
	
	
	    iArray[5]=new Array();
	    iArray[5][0]="Ͷ�������ֺ���";         		//����
	    iArray[5][1]="100px";            //�п�
	    iArray[5][2]=100;            		//�����ֵ
	    iArray[5][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
	
	    iArray[6]=new Array();
	    iArray[6][0]="����������";         		//����
	    iArray[6][1]="60px";            //�п�
	    iArray[6][2]=100;            		//�����ֵ
	    iArray[6][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
	    
	    iArray[7]=new Array();
	    iArray[7][0]="�����˱���";    //����
	    iArray[7][1]="100px";            //�п�
	    iArray[7][2]=100;            		//�����ֵ
	    iArray[7][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
	    
	    iArray[8]=new Array();
	    iArray[8][0]="���ֱ���";         	//����
	    iArray[8][1]="100px";            //�п�
	    iArray[8][2]=100;            		//�����ֵ
	    iArray[8][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
	    
	    iArray[9]=new Array();
	    iArray[9][0]="���δ���";       	//����
	    iArray[9][1]="100px";           //�п�
	    iArray[9][2]=100;            		//�����ֵ
	    iArray[9][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
	    
	    iArray[10]=new Array();
	    iArray[10][0]="�ٷֺ˱�����";    	//����
	    iArray[10][1]="100px";            //�п�
	    iArray[10][2]=100;            		//�����ֵ
	    iArray[10][3]=3;              		//�Ƿ���������,1��ʾ����0��ʾ������
	
	    SearchResultGrid = new MulLineEnter( "fm" , "SearchResultGrid" ); 
	    //��Щ���Ա�����loadMulLineǰ
	    SearchResultGrid.mulLineCount = 0;   
	    SearchResultGrid.displayTitle = 1;
	    SearchResultGrid.locked = 1;
	    SearchResultGrid.hiddenPlus = 1;
	    SearchResultGrid.canSel = 0;
    	SearchResultGrid.canChk = 1; 
	    SearchResultGrid.hiddenSubtraction = 1;
	    SearchResultGrid.loadMulLine(iArray);  
    }
    catch(ex){
      myAlert(ex);
    }
}

function initIndRelaListGrid(){
	var iArray = new Array();
  try
  {
	  iArray[0]=new Array();
		iArray[0][0]="���";         		//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
		iArray[0][1]="30px";         		//�п�
		iArray[0][2]=10;          			//�����ֵ
		iArray[0][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
		
		iArray[1]=new Array();
		iArray[1][0]="��������";    		//����
		iArray[1][1]="80px";            //�п�
		iArray[1][2]=100;            		//�����ֵ
		iArray[1][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
		
		iArray[2]=new Array();
		iArray[2][0]="���ֱ���";        //����
		iArray[2][1]="80px";            //�п�
		iArray[2][2]=100;            		//�����ֵ
		iArray[2][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
		
		iArray[3]=new Array();
		iArray[3][0]="���α���";        //����
		iArray[3][1]="60px";            //�п�
		iArray[3][2]=100;            		//�����ֵ
		if(fm.DeTailFlag.value=='1'){
	  	iArray[3][3]=3;              		//�Ƿ���������,1��ʾ����0��ʾ������
	  }else{
	  	iArray[3][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
	  }
		
		iArray[4]=new Array();
		iArray[4][0]="����";         		//����
		iArray[4][1]="60px";            //�п�
		iArray[4][2]=100;            		//�����ֵ
		iArray[4][3]=3;              		//�Ƿ���������,1��ʾ����0��ʾ������
		
		iArray[5]=new Array();
		iArray[5][0]="����";         		//����
		iArray[5][1]="60px";            //�п�
		iArray[5][2]=100;            		//�����ֵ
		iArray[5][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
		
		iArray[6]=new Array();
		iArray[6][0]="����";         		//����
		iArray[6][1]="60px";            //�п�
		iArray[6][2]=100;            		//�����ֵ
		iArray[6][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
		
		iArray[7]=new Array();
		iArray[7][0]="���ձ���";        //����
		iArray[7][1]="60px";            //�п�
		iArray[7][2]=100;            		//�����ֵ
		iArray[7][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
		
		iArray[8]=new Array();
		iArray[8][0]="�ۼƷ��ձ���";    //����
		iArray[8][1]="0px";           //�п�
		iArray[8][2]=100;            		//�����ֵ
		iArray[8][3]=3;              		//�Ƿ���������,1��ʾ����0��ʾ������
		
		iArray[9]=new Array();
		iArray[9][0]="Ͷ��������";        //����
		iArray[9][1]="100px";           //�п�
		iArray[9][2]=100;            		//�����ֵ
		iArray[9][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
		
		iArray[10]=new Array();
		iArray[10][0]="�˱�����";        //����
		iArray[10][1]="100px";           //�п�
		iArray[10][2]=100;           		 //�����ֵ
		iArray[10][3]=0;            		 //�Ƿ���������,1��ʾ����0��ʾ������
		
		iArray[11]=new Array();
		iArray[11][0]="�˱����۱���";    //����
		iArray[11][1]="0px";           //�п�
		iArray[11][2]=100;           		 //�����ֵ
		iArray[11][3]=3;            		 //�Ƿ���������,1��ʾ����0��ʾ������
		
		iArray[12]=new Array();
		iArray[12][0]="�˱����۱��뱸��"; //����
		iArray[12][1]="0px";           //�п�
		iArray[12][2]=100;           		 //�����ֵ
		iArray[12][3]=3;             		 //�Ƿ���������,1��ʾ����0��ʾ������
	  
	  iArray[13]=new Array();
		iArray[13][0]="�����˱���"; //����
		iArray[13][1]="0px";           //�п�
		iArray[13][2]=100;           		 //�����ֵ
		iArray[13][3]=3;             		 //�Ƿ���������,1��ʾ����0��ʾ������
		
		iArray[14]=new Array();
		iArray[14][0]="�ٱ��������"; //����
		iArray[14][1]="60px";           //�п�
		iArray[14][2]=100;           		 //�����ֵ
		iArray[14][3]=3;             		 //�Ƿ���������,1��ʾ����0��ʾ������
	  
	  IndRelaListGrid = new MulLineEnter( "fm" , "IndRelaListGrid" ); 
	  //��Щ���Ա�����loadMulLineǰ
	  IndRelaListGrid.mulLineCount = 0;   
	  IndRelaListGrid.displayTitle = 1;
	  IndRelaListGrid.locked = 1;
	  IndRelaListGrid.hiddenPlus = 1;
	  IndRelaListGrid.canSel = 0;
  	IndRelaListGrid.canChk = 1; 
	  IndRelaListGrid.hiddenSubtraction = 1;
	  IndRelaListGrid.loadMulLine(iArray);  
  }
  catch(ex){
    myAlert(ex);
  }	
}

function initRelaListGrid(){
		var iArray = new Array();
    try
    {
	    iArray[0]=new Array();
	    iArray[0][0]="���";         		//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
	    iArray[0][1]="30px";         		//�п�
	    iArray[0][2]=10;          			//�����ֵ
	    iArray[0][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
	
	    iArray[1]=new Array();
	    iArray[1][0]="ӡˢ��";    		//����
	    iArray[1][1]="100px";            //�п�
	    iArray[1][2]=100;            		//�����ֵ
	    iArray[1][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
	
	    iArray[2]=new Array();
	    iArray[2][0]="����Ͷ������";        //����
	    iArray[2][1]="100px";            //�п�
	    iArray[2][2]=100;            		//�����ֵ
	    iArray[2][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
	    
	    iArray[3]=new Array();
	    iArray[3][0]="���ϼƻ�";        //����
	    iArray[3][1]="60px";            //�п�
	    iArray[3][2]=100;            		//�����ֵ
	    iArray[3][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
	    
	    iArray[4]=new Array();
	    iArray[4][0]="���˱�������";         		//����
	    iArray[4][1]="100px";            //�п�
	    iArray[4][2]=100;            		//�����ֵ
	    iArray[4][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
	
	
	    iArray[5]=new Array();
	    iArray[5][0]="Ͷ�������ֺ���";         		//����
	    iArray[5][1]="100px";            //�п�
	    iArray[5][2]=100;            		//�����ֵ
	    iArray[5][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
	
	    iArray[6]=new Array();
	    iArray[6][0]="����������";         		//����
	    iArray[6][1]="60px";            //�п�
	    iArray[6][2]=100;            		//�����ֵ
	    iArray[6][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
	    
	    iArray[7]=new Array();
	    iArray[7][0]="�����˱���";    //����
	    iArray[7][1]="100px";            //�п�
	    iArray[7][2]=100;            		//�����ֵ
	    iArray[7][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
	    
	    iArray[8]=new Array();
	    iArray[8][0]="���ֱ���";         	//����
	    iArray[8][1]="70px";            //�п�
	    iArray[8][2]=100;            		//�����ֵ
	    iArray[8][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
	    
	    iArray[9]=new Array();
	    iArray[9][0]="���δ���";       	//����
	    iArray[9][1]="70px";           //�п�
	    iArray[9][2]=100;            		//�����ֵ
	    iArray[9][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������

			iArray[10]=new Array();
	    iArray[10][0]="������ʽ";    	//����
	    iArray[10][1]="120px";            //�п�
	    iArray[10][2]=100;            		//�����ֵ
	    iArray[10][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
	    
	    iArray[11]=new Array();
	    iArray[11][0]="�ٷֺ˱�����";    	//����
	    iArray[11][1]="100px";            //�п�
	    iArray[11][2]=100;            		//�����ֵ
	    iArray[11][3]=3;              		//�Ƿ���������,1��ʾ����0��ʾ������
			
			iArray[12]=new Array();
	    iArray[12][0]="������ʽ���";    	//����
	    iArray[12][1]="100px";            //�п�
	    iArray[12][2]=100;            		//�����ֵ
	    iArray[12][3]=3;              		//�Ƿ���������,1��ʾ����0��ʾ������
			
	    RelaListGrid = new MulLineEnter( "fm" , "RelaListGrid" ); 
	    //��Щ���Ա�����loadMulLineǰ
	    RelaListGrid.mulLineCount = 0;   
	    RelaListGrid.displayTitle = 1;
	    RelaListGrid.locked = 1;
	    RelaListGrid.hiddenPlus = 1;
	    RelaListGrid.canSel = 0;
    	RelaListGrid.canChk = 1; 
	    RelaListGrid.hiddenSubtraction = 1;
	    RelaListGrid.loadMulLine(iArray);  
    }
    catch(ex){
      myAlert(ex);
    }
}
</script>
