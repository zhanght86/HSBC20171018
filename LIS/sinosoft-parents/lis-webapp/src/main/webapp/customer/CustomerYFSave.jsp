<%
//�������ƣ�TempFinFeeSave.jsp
//�����ܣ�
//�������ڣ�2009-11-14
//������  ���ű�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.vdb.*"%>
  <%@page import="com.sinosoft.lis.sys.*"%>
  <%@page import="com.sinosoft.lis.vbl.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
	<%@page import="com.sinosoft.lis.finfee.*"%>
	<%@page import="com.sinosoft.service.*" %>
	<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
  loggerDebug("CustomerYFSave","��ʼִ��Saveҳ��");
  
  GlobalInput globalInput = new GlobalInput();
	globalInput.setSchema((GlobalInput)session.getValue("GI"));
  
  TransferData mTransferData = new TransferData();
  
  LJTempFeeSet mLJTempFeeSet = new LJTempFeeSet();
  LJTempFeeClassSet mLJTempFeeClassSet = new LJTempFeeClassSet();
  
  //FinFeeUI mFinFeeUI = new FinFeeUI();
  
  BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  
  CErrors tError = null;
  String mOperateType = request.getParameter("OperateType");
  
  loggerDebug("CustomerYFSave","������������"+mOperateType);
  String FlagStr = "";
  String Content = "";
  String mDescType = "";	//��������־��Ӣ��ת���ɺ��ֵ���ʽ
  //������Ϣ
  String mManageCom				= request.getParameter("ManageCom");
  String mOperator 	 			= request.getParameter("Operator"	);
  String ActuGetNo5 = request.getParameter("ActuGetNo5"	);
  //���񽻷���Ϣ
	String[] mTempNum				=	request.getParameterValues("FinFeeGridNo"	); 
	String[] mPayMode				=	request.getParameterValues("FinFeeGrid1"	); //���ѷ�ʽ
  String[] mChequeNo			=	request.getParameterValues("FinFeeGrid5"	); //֧Ʊ��
  String[] mChequeDate  	=	request.getParameterValues("FinFeeGrid6"	); //֧Ʊ����
  ////֧Ʊ��������
  String[] mBankCode    	=	request.getParameterValues("FinFeeGrid7"	);
  String[] mBankAccNo   	=	request.getParameterValues("FinFeeGrid9"	);
  String[] mAccName     	=	request.getParameterValues("FinFeeGrid10"	);
  ////�շ�����
  String[] mInBankCode  	=	request.getParameterValues("FinFeeGrid11"	);
  String[] mInBankAccNo 	=	request.getParameterValues("FinFeeGrid12"	);
  String[] mInAccName   	=	request.getParameterValues("FinFeeGrid13"	);
  
  String[] mTempFeeNo			=	request.getParameterValues("FinFeeGrid18"	);
  String[] mPayUserName		=	request.getParameterValues("FinFeeGrid19"	);
  
  //ת��
  //String[] mGetNoticeNo  	=	request.getParameterValues("FinFeeGrid17"	);
	//String[] mBenefiPerson=	request.getParameterValues("FinFeeGrid"	); //������
	
	//ҵ�񽻷���Ϣ
  String[] mNum						=	request.getParameterValues("TempToGridNo"	); 
  String[] mGetNoticeNo 	=	request.getParameterValues("TempToGrid1"	); 		//Ӧ�պ���
  String[] mTempFeeType 	=	request.getParameterValues("TempToGrid18"	); 		//��������
  String[] mPayDate 			=	request.getParameterValues("TempToGrid3"	); 		//��������
  String[] mPayMoney 			=	request.getParameterValues("TempToGrid4"	); 		//���ѽ��
  String[] mCurrency 			=	request.getParameterValues("TempToGrid5"	); 		//����
  
  String[] mAgentGroup 		=	request.getParameterValues("TempToGrid9"	); 		//���������
  String[] mAgentCode			=	request.getParameterValues("TempToGrid10"	); 		//�����˱���
  String[] mOtherNo 			=	request.getParameterValues("TempToGrid11"	); 		//ҵ�����
  String[] mOtherNoType		=	request.getParameterValues("TempToGrid12"	); 		//ҵ�����
  String[] mAPPntName 		=	request.getParameterValues("TempToGrid16"	); 		//Ͷ����
  String[] mPolicyCom 		=	request.getParameterValues("TempToGrid7"	); 		//�������
	
  LJTempFeeClassSchema tLJTempFeeClassSchema;
  
  if(mOperateType.equals("INSERT")){
  	loggerDebug("CustomerYFSave","��ʼִ��INSERTҳ��");
  	
	LJTempFeeSchema tLJTempFeeSchema ;
		if(mNum!=null){
			int tLength = mNum.length;
			String tLimit ;
			String tNo 		; 
			String serNo 	; 
  		for(int i = 0 ;i < tLength ;i++){
  			tLimit = PubFun.getNoLimit(mPolicyCom[i]);
  			loggerDebug("CustomerYFSave","11111111111111111111111111  mTempFeeNo: "+mTempFeeNo[0]);
  			if("".equals(mTempFeeNo[0])||mTempFeeNo[0]==null){
  				
  				tNo = "TS"+PubFun1.CreateMaxNo("GETNOTICENO", tLimit);// ����֪ͨ��ż��ݽ��Ѻ�
  				loggerDebug("CustomerYFSave","aaaaaaaaaaaaaaaaaaaaaaaaaaaaa : "+tNo);
  			}else{
  				tNo = mTempFeeNo[0];
  				loggerDebug("CustomerYFSave","bbbbbbbbbbbbbbbbbbbbbbbbbbbbb : "+tNo);
  			}
  			
  			serNo = PubFun1.CreateMaxNo("SERIALNO", tLimit);
  			
  			//�����շ�
  			tLJTempFeeClassSchema = new LJTempFeeClassSchema();
  			tLJTempFeeClassSchema.setTempFeeNo(		tNo							);
  			tLJTempFeeClassSchema.setPayMode(	 		mPayMode[0]			);
		  	tLJTempFeeClassSchema.setChequeNo( 		mChequeNo[0]		); //Ʊ�ݺ���
		  	tLJTempFeeClassSchema.setPayMoney(	 	mPayMoney[i]		); 
		  	tLJTempFeeClassSchema.setCurrency(	  mCurrency[i]		);
		  	tLJTempFeeClassSchema.setPayDate(			mPayDate[0]			);
		  	tLJTempFeeClassSchema.setChequeDate(	mChequeDate[0]	);
		  	tLJTempFeeClassSchema.setEnterAccDate(mPayDate[0]			);
		  	tLJTempFeeClassSchema.setConfFlag(		"0"							);
		  	tLJTempFeeClassSchema.setSerialNo(		serNo						);
		  	tLJTempFeeClassSchema.setBankCode(		mBankCode[0]		);
		  	tLJTempFeeClassSchema.setBankAccNo(		mBankAccNo[0]		);
		  	tLJTempFeeClassSchema.setAccName(			mAccName[0]			);
		  	tLJTempFeeClassSchema.setInBankCode(	mInBankCode[0]	);
		  	tLJTempFeeClassSchema.setInBankAccNo(	mInBankAccNo[0]	);
		  	tLJTempFeeClassSchema.setInAccName(		mInAccName[0]		);
		  	tLJTempFeeClassSchema.setEnterAccDate(mPayDate[0]			);
		  	tLJTempFeeClassSchema.setManageCom(		mManageCom			);
		  	tLJTempFeeClassSchema.setPolicyCom(		mPolicyCom[i]		);
		  	tLJTempFeeClassSchema.setOperator(		mOperator				);
		  	tLJTempFeeClassSchema.setOtherNo(			mOtherNo[i]			);
		  	tLJTempFeeClassSchema.setOtherNoType(	mOtherNoType[i]	);
		  	
		 // 	tLJTempFeeClassSchema.setPayUserName(	mPayUserName[0]	);
		  	
		  	mLJTempFeeClassSet.add(tLJTempFeeClassSchema);
		  	
  			//�����շ�
  			tLJTempFeeSchema = new LJTempFeeSchema();
  			tLJTempFeeSchema.setTempFeeNo(		tNo									);
  			tLJTempFeeSchema.setTempFeeType(	mTempFeeType[i]			);
  			tLJTempFeeSchema.setRiskCode(	 		"000000"						);
  			tLJTempFeeSchema.setOtherNo(			mOtherNo[i]					);
  			tLJTempFeeSchema.setOtherNoType(	mOtherNoType[i]			);
  			tLJTempFeeSchema.setPayMoney(			mPayMoney[i]				);
  			tLJTempFeeSchema.setPayDate(			mPayDate[i]					);
  			tLJTempFeeSchema.setEnterAccDate(	mPayDate[0]					);
  			tLJTempFeeSchema.setSerialNo(			serNo								);
  			tLJTempFeeSchema.setConfFlag(			"0"									);
  			tLJTempFeeClassSchema.setSerialNo(serNo								);
  			tLJTempFeeSchema.setManageCom(		mManageCom					);
  			tLJTempFeeSchema.setPolicyCom(		mPolicyCom[i]				);
  			tLJTempFeeSchema.setAPPntName(		mAPPntName[i]				);
  			tLJTempFeeSchema.setAgentGroup(		mAgentGroup[i]			);
  			tLJTempFeeSchema.setAgentCode(		mAgentCode[i]				);
  			tLJTempFeeSchema.setCurrency(			mCurrency[i]				);
  			tLJTempFeeSchema.setRemark(				mGetNoticeNo[i]			);
  			
  			mLJTempFeeSet.add(tLJTempFeeSchema);
  		}
  	}
	}
	
  if(mOperateType.equals("INSERT")){
    mDescType = "�����շ�";
  }
  if(mOperateType.equals("UPDATE")){
    mDescType = "�޸�ƥ����Ϣ�ɹ�";
  }
  if(mOperateType.equals("DELETE")){
    mDescType = "ɾ�����񽻷���Ϣ";
  }
  if(mOperateType.equals("QUERY")){
    mDescType = "��ѯ���񽻷���Ϣ";
  }
  
  
  
		
		
		
  VData tVData = new VData();
  try{
  	tVData.addElement(		globalInput					);
  	tVData.addElement(		mTransferData				);
    tVData.addElement(		mLJTempFeeClassSet	);
    tVData.addElement(		mLJTempFeeSet				);
    
    LJAGetSchema tLJAGetSchema = new LJAGetSchema();
		tLJAGetSchema.setActuGetNo(ActuGetNo5);
		tVData.addElement(tLJAGetSchema);
    

    if(tBusinessDelegate.submitData(tVData, mOperateType,"FinFeeUI"))
    {
    }	
  }catch(Exception ex){
    Content = mDescType+"ʧ�ܣ�ԭ����:" + ex.toString();
    FlagStr = "Fail";
  }

  //�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
  String tFinFeeNo = "";
  if (FlagStr==""){
    //tError = mFinFeeUI.mErrors;
    tError = tBusinessDelegate.getCErrors(); 
    if (!tError.needDealError()){
      Content = mDescType+"�ɹ�";    
    	FlagStr = "Succ";
    }
    else{
    	Content = mDescType+" ʧ�ܣ�ԭ����:" + tError.getFirstError();
    	FlagStr = "Fail";
    }
  }
%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
