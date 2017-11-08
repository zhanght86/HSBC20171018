<%
//GrPEdorTypeTISubmit.jsp
//程序功能：
//创建日期：2007-05-24 16:49:22
//创建人  ：ck
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.bq.*"%>
 <%@page import="com.sinosoft.lis.pubfun.*"%>
 <%@page import="java.lang.*"%>
 <%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>

<%
 //接收信息，并作校验处理。
  //输入参数
  //个人批改信息
  System.out.println("-----PGsubmit---");
  CErrors tError = null;
  //后面要执行的动作：添加，修改      
  String FlagStr = "";
  String Content = "";
  String fmAction = request.getParameter("Transact");
  
  LPGrpEdorItemSchema tLPGrpEdorItemSchema=new LPGrpEdorItemSchema();
  //执行动作：insert 添加纪录，update 修改纪录，delete 删除纪录
  GlobalInput tG = new GlobalInput();
  tG = (GlobalInput)session.getValue("GI");
  tLPGrpEdorItemSchema.setEdorAcceptNo(request.getParameter("EdorAcceptNo"));
  tLPGrpEdorItemSchema.setEdorNo(request.getParameter("EdorNo"));
  tLPGrpEdorItemSchema.setEdorAppNo(request.getParameter("EdorAppNo"));
  tLPGrpEdorItemSchema.setEdorType(request.getParameter("EdorType"));
  tLPGrpEdorItemSchema.setGrpContNo(request.getParameter("GrpContNo"));
  System.out.println("encode:"+tLPGrpEdorItemSchema.encode());
  
  LPInsuAccOutSchema tLPInsuAccOutSchema=new LPInsuAccOutSchema();
  LPInsuAccInSet tLPInsuAccInSet=new LPInsuAccInSet();
  
  //LPTransformSet tLPTransformSet=new LPTransformSet();

  String EdorNo=request.getParameter("EdorNo");
  String EdorType=request.getParameter("EdorType");
  String GrpContNo=request.getParameter("GrpContNo");
  String OutPolNo="";
  String OutInsuAccNo="";
  String OutPayPlanCode="";
  String AccOutType="";
  String AccOutUnit="";
  String AccOutBala="";

  if(fmAction.equals("INSERT||PMAIN"))
  {
	  String tLCGrpInsuAccNo[] = request.getParameterValues("LCGrpInsuAccGridNo"); //序号字段
	  String tRadio[] = request.getParameterValues("InpLCGrpInsuAccGridSel");
	  //注意：radio字段的名字结构为Inp+GridName+Sel，这里GridName=LCGrpInsuAccGrid
	  int tCount = tLCGrpInsuAccNo.length;   //纪录的总行数
	  int tSelNo =0 ;
	  for (int i = 0; i < tCount; i++){
		if (tRadio [i].equals("1")) {             //如果该行被选中
			   tSelNo = i;
		 }
	  }
	  String[] tOutPolNo=request.getParameterValues("LCGrpInsuAccGrid2");//转出保单
	  String[] tOutInsuAccNo=request.getParameterValues("LCGrpInsuAccGrid3");//转出账户
	  String[] tOutPayPlanCode=request.getParameterValues("LCGrpInsuAccGrid5");//转出子账户
	  String[] tCanMoveUnit=request.getParameterValues("LCGrpInsuAccGrid7");//可转出单位
	  String[] tAccOutUnit=request.getParameterValues("LCGrpInsuAccGrid9");//变动单位
	  String[] tAccOutBala=request.getParameterValues("LCGrpInsuAccGrid10");//变动金额
	  
	  OutPolNo=tOutPolNo[tSelNo];
	  OutInsuAccNo=tOutInsuAccNo[tSelNo];
	  OutPayPlanCode=tOutPayPlanCode[tSelNo];

	  if(tAccOutUnit[tSelNo]!=null&&!tAccOutUnit[tSelNo].equals(""))
	  {
	  	if(!PubFun.isNumeric(tAccOutUnit[tSelNo]))
	  	{
	  		FlagStr="Fail";
	  		Content = "请输入正确的转出单位数!";
	  	}else
	  	{
		  	if(Double.parseDouble(tAccOutUnit[tSelNo])>Double.parseDouble(tCanMoveUnit[tSelNo]))
	  	  	{
	  	  		FlagStr="Fail";
		  		Content = "输入的单位数应小于或等于可转出单位!";
	  	  	}else
	  		{
				AccOutType="1";
				AccOutUnit=tAccOutUnit[tSelNo];
	  		}
	  	}
	  }else if(tAccOutBala[tSelNo]!=null&&!tAccOutBala[tSelNo].equals(""))
	  {
	  	if(!PubFun.isNumeric(tAccOutBala[tSelNo]))
	  	{
	  		FlagStr="Fail";
	  		Content = "请输入正确的转出金额数!";
	  	}else
	  	{
			AccOutType="2";
			AccOutBala=tAccOutBala[tSelNo];
	  	}
	  }
	  
	  /*设置账户转出信息*/
	  tLPInsuAccOutSchema.setEdorNo(EdorNo);
  	  tLPInsuAccOutSchema.setEdorType(EdorType);
  	  tLPInsuAccOutSchema.setGrpContNo(GrpContNo);
  	  tLPInsuAccOutSchema.setOutPolNo(OutPolNo);
  	  tLPInsuAccOutSchema.setOutInsuAccNo(OutInsuAccNo);
  	  tLPInsuAccOutSchema.setOutPayPlanCode(OutPayPlanCode);
  	  tLPInsuAccOutSchema.setAccOutType(AccOutType);
  	  tLPInsuAccOutSchema.setAccOutUnit(AccOutUnit);
  	  tLPInsuAccOutSchema.setAccOutBala(AccOutBala);
  	  
	  System.out.println("AccOutType:"+AccOutType);
	  System.out.println("AccOutUnit:"+AccOutUnit);
	  System.out.println("AccOutBala:"+AccOutBala);
	  
	  if(!FlagStr.equals("Fail"))
  	  {
		  String tChk[] = request.getParameterValues("InpLCInsuAccGridChk"); 
		  
		  String[] tInPolNo=request.getParameterValues("LCInsuAccGrid2");//转入保单号
		  String[] tInInsuAccNo=request.getParameterValues("LCInsuAccGrid3");//转入账户
		  String[] tInPayPlanCode=request.getParameterValues("LCInsuAccGrid5");//转入子账户
		  String[] tAccInRate=request.getParameterValues("LCInsuAccGrid9");//转入比例
		  String[] tAccInBala=request.getParameterValues("LCInsuAccGrid10");//转入金额
		  
		  String AccInType="";	  //1-份额2-金额3-比例
		  String flag="";
		  		  
		  //参数格式=" Inp+MulLine对象名+Chk"  
	          for(int index=0;index<tChk.length;index++)
	          {
			if(tChk[index].equals("1"))
			{
				LPInsuAccInSchema tLPInsuAccInSchema=new LPInsuAccInSchema();
				tLPInsuAccInSchema.setEdorNo(EdorNo);
				tLPInsuAccInSchema.setEdorType(EdorType);
				tLPInsuAccInSchema.setGrpContNo(GrpContNo);
				tLPInsuAccInSchema.setInPolNo(tInPolNo[index]);
				tLPInsuAccInSchema.setInInsuAccNo(tInInsuAccNo[index]);
				tLPInsuAccInSchema.setInPayPlanCode(tInPayPlanCode[index]);
				
				flag="1";
				if(AccOutType.equals("1"))//按照单位计算
				{				
					System.out.println("tAccInRate[index]:"+tAccInRate[index]);
					AccInType="3";
					if(tAccInRate[index]==null||tAccInRate[index].equals(""))
					{
						FlagStr="Fail";
						Content = "请输入转入比例!";
						break;
					}else{
						if(!PubFun.isNumeric(tAccInRate[index]))
						{
							FlagStr="Fail";
							Content = "请输入正确的转入比例!";
							break;
						}
					}
				}else if(AccOutType.equals("2"))
				{					
					if(AccInType.equals(""))
					{
						System.out.println("tAccInRate[index]:"+tAccInRate[index]);   
						if(tAccInRate[index]!=null&&!tAccInRate[index].equals(""))
						{
							AccInType="3";
						}else
						{
							if(tAccInBala[index]!=null||!tAccInBala[index].equals(""))
							{
								AccInType="2";
							}
						}
					}
					System.out.println("AccInType:"+AccInType);   
					
					if(AccInType.equals("3"))
					{
						if(tAccInRate[index]==null||tAccInRate[index].equals(""))
						{
							FlagStr="Fail";
							Content = "请输入转入比例!";
							break;
						}else{
							if(!PubFun.isNumeric(tAccInRate[index]))
							{
								FlagStr="Fail";
								Content = "请输入正确的转入比例!";
								break;
							}
						}
					}
									
					if(AccInType.equals("2"))
					{
						if(tAccInBala[index]==null||tAccInBala[index].equals(""))
						{
							FlagStr="Fail";
							Content = "请输入转入金额!";
							break;
						}else{
							if(!PubFun.isNumeric(tAccInBala[index]))
							{
								FlagStr="Fail";
								Content = "请输入正确的转入金额!";
								break;
							}
						}
					}
				}
				tLPInsuAccInSchema.setAccInType(AccInType);
				tLPInsuAccInSchema.setAccInRate(tAccInRate[index]);
				tLPInsuAccInSchema.setAccInBala(tAccInBala[index]);
				tLPInsuAccInSet.add(tLPInsuAccInSchema);
			}
		  }
		  System.out.println("flag:"+flag);     
		  if(flag.equals(""))
		  {
			FlagStr="Fail";
			Content = "请选择转入账户!";
		  }	
		  //判断输入是否符合逻辑
		  if(!FlagStr.equals("Fail")&&tLPInsuAccInSet.size()>0)
		  {
		  	System.out.println("FlagStr1:"+FlagStr);
 			System.out.println("Content1:"+Content);
 			System.out.println("AccOutType:"+AccOutType);
 			 
		  	double rate=0;
		  	double bala=0;
		  	if(AccOutType.equals("1"))
		  	{
		  		rate=0;
		  		for(int i=1;i<=tLPInsuAccInSet.size();i++)
		  		{
		  			System.out.println("tLPInsuAccInSet.get(i).getAccInRate():"+tLPInsuAccInSet.get(i).getAccInRate());
		  			rate+=tLPInsuAccInSet.get(i).getAccInRate();
		  		}
		  		if(rate!=1)
		  		{
		  			FlagStr="Fail";
					Content = "转入比例必须=1!";
		  		}
		  	}
		  	if(AccOutType.equals("2"))
		  	{
		  		System.out.println("tLPInsuAccInSet.get(1).getAccInType():"+tLPInsuAccInSet.get(1).getAccInType());
		  		if(tLPInsuAccInSet.get(1).getAccInType().equals("2"))
		  		{
			  		bala=0;
			  		for(int i=1;i<=tLPInsuAccInSet.size();i++)
			  		{
			  			bala+=tLPInsuAccInSet.get(i).getAccInBala();
			  		}
			  		if(bala!=tLPInsuAccOutSchema.getAccOutBala())
			  		{
			  			FlagStr="Fail";
						Content = "转入金额必须等于转出金额!";
			  		}
		  		}else if(tLPInsuAccInSet.get(1).getAccInType().equals("3"))
		  		{
		  			rate=0;
			  		for(int i=1;i<=tLPInsuAccInSet.size();i++)
			  		{
			  			rate+=tLPInsuAccInSet.get(i).getAccInRate();
			  		}
			  		System.out.println("rate:"+rate);
			  		if(rate!=1)
			  		{
			  			FlagStr="Fail";
						Content = "转入比例必须=1!";
			  		}
		  		}
		  	}
		  }
	  }
  }
  else if(fmAction.equals("DELETE||PMAIN"))//交易撤销
  {
  	  String tLPInsuAccNo[] = request.getParameterValues("LPInsuAccGridNo"); //序号字段
	  String aRadio[] = request.getParameterValues("InpLPInsuAccGridSel");
	  //注意：radio字段的名字结构为Inp+GridName+Sel，这里GridName=LPInsuAccGrid
	  int aCount = tLPInsuAccNo.length;   //纪录的总行数
	  int aSelNo =0 ;
	  for (int i = 0; i < aCount; i++){
		if (aRadio [i].equals("1")) {             //如果该行被选中
			   aSelNo = i;
		 }
	  }
	  String[] tSerialNo=request.getParameterValues("LPInsuAccGrid13");//流水号
	  String[] tOutPolNo=request.getParameterValues("LPInsuAccGrid2");//保单号
	  String[] tOutInsuAccNo=request.getParameterValues("LPInsuAccGrid3");//转出账户
	  String[] tOutPayPlanCode=request.getParameterValues("LPInsuAccGrid4");//转出子账户
  	  tLPInsuAccOutSchema.setSerialNo(tSerialNo[aSelNo]);
  	  tLPInsuAccOutSchema.setOutPolNo(tOutPolNo[aSelNo]);
  	  tLPInsuAccOutSchema.setOutInsuAccNo(tOutInsuAccNo[aSelNo]);
  	  tLPInsuAccOutSchema.setOutPayPlanCode(tOutPayPlanCode[aSelNo]);
  }    

  if(FlagStr.equals("Fail"))
  {
  	//错误处理
  }else
  {
	  try
	  {
	  	 PEdorPGDetailUI tPEdorPGDetailUI   = new PEdorPGDetailUI();
	  	 //准备传输数据 VData
	  	 VData tVData = new VData();  
		 //保存个人保单信息(保全)
		 tVData.addElement(tG);	 	
		 tVData.addElement(tLPGrpEdorItemSchema);
		 tVData.addElement(tLPInsuAccOutSchema);
		 tVData.addElement(tLPInsuAccInSet);
	         tPEdorPGDetailUI.submitData(tVData,fmAction);
			
	  	//如果在Catch中发现异常，则不从错误类中提取错误信息
	 	if (FlagStr=="")
	  	{
	    		tError = tPEdorPGDetailUI.mErrors;
	    		if (!tError.needDealError())
	    		{
	      			Content = " 操作成功";
	    			FlagStr = "Succ";
	    		}
	    		else
	    		{
	    			Content = " 操作失败，原因是:" + tError.getFirstError();
	    			FlagStr = "Fail";
	    		}
	  	}
	  }
	  catch(Exception ex)
	  {
		Content = fmAction+"失败，原因是:" + ex.toString();
		FlagStr = "Fail";
	  }
  }
%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

