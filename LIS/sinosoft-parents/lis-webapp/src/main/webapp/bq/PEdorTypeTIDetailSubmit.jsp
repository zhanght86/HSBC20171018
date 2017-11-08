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
<%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.bq.*"%>
 <%@page import="com.sinosoft.lis.pubfun.*"%>
 <%@page import="java.lang.*"%>
  <%@page import="com.sinosoft.service.*" %> 
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
  
  LPEdorItemSchema tLPEdorItemSchema=new LPEdorItemSchema();
  //执行动作：insert 添加纪录，update 修改纪录，delete 删除纪录
  GlobalInput tG = new GlobalInput();
  tG = (GlobalInput)session.getValue("GI");
  tLPEdorItemSchema.setEdorAcceptNo(request.getParameter("EdorAcceptNo"));
  tLPEdorItemSchema.setEdorNo(request.getParameter("EdorNo"));
  tLPEdorItemSchema.setEdorAppNo(request.getParameter("EdorAppNo"));
  tLPEdorItemSchema.setEdorType(request.getParameter("EdorType"));
  tLPEdorItemSchema.setContNo(request.getParameter("ContNo"));
  tLPEdorItemSchema.setPolNo(request.getParameter("PolNo"));
  System.out.println("encode:"+tLPEdorItemSchema.encode());
  
  LPInsuAccOutSchema tLPInsuAccOutSchema=new LPInsuAccOutSchema();
  LPInsuAccInSet tLPInsuAccInSet=new LPInsuAccInSet();

  String EdorNo=request.getParameter("EdorNo");
  String EdorType=request.getParameter("EdorType");
  String ContNo=request.getParameter("ContNo");
  String OutPolNo="";
  String OutInsuAccNo="";
  //String OutPayPlanCode="";
  String AccOutType="1";
  String AccOutUnit=request.getParameter("OutUnit");
  String AccOutBala="";

  if(fmAction.equals("INSERT||DEALOUT"))
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
	  //String[] tOutPayPlanCode=request.getParameterValues("LCGrpInsuAccGrid5");//转出子账户
	  String[] tCanMoveUnit=request.getParameterValues("LCGrpInsuAccGrid6");//可转出单位
	  //String[] tAccOutUnit=request.getParameterValues("LCGrpInsuAccGrid8");//变动单位
	  String[] tAccOutBala=request.getParameterValues("LCGrpInsuAccGrid9");//变动金额
	  
	  OutPolNo=tOutPolNo[tSelNo];
	  OutInsuAccNo=tOutInsuAccNo[tSelNo];
	  //OutPayPlanCode=tOutPayPlanCode[tSelNo];
  
  
	  /*设置账户转出信息*/
	  tLPInsuAccOutSchema.setEdorNo(EdorNo);
  	tLPInsuAccOutSchema.setEdorType(EdorType);
  	tLPInsuAccOutSchema.setContNo(ContNo);
  	tLPInsuAccOutSchema.setOutPolNo(OutPolNo);
  	tLPInsuAccOutSchema.setOutInsuAccNo(OutInsuAccNo);
  	//tLPInsuAccOutSchema.setOutPayPlanCode(OutPayPlanCode);
  	tLPInsuAccOutSchema.setAccOutType(AccOutType);
  	tLPInsuAccOutSchema.setAccOutUnit(AccOutUnit);
  	//tLPInsuAccOutSchema.setAccOutBala(AccOutBala);
  	  
	  //System.out.println("AccOutType:"+AccOutType);
	  System.out.println("AccOutUnit:"+AccOutUnit);
	  //System.out.println("AccOutBala:"+AccOutBala);

		
  }
  else if(fmAction.equals("DELETE||CANCELOUT"))
  {
  	  /*设置账户转出信息*/
	  tLPInsuAccOutSchema.setEdorNo(EdorNo);
  	tLPInsuAccOutSchema.setEdorType(EdorType);
  	tLPInsuAccOutSchema.setContNo(ContNo);
  	tLPInsuAccOutSchema.setOutPolNo(request.getParameter("PolNoDO"));
  	tLPInsuAccOutSchema.setOutInsuAccNo(request.getParameter("InsuAccNo"));
  	//tLPInsuAccOutSchema.setOutPayPlanCode(request.getParameter("PayPlanCode"));
  	//tLPInsuAccOutSchema.setAccOutType(AccOutType);
  	//tLPInsuAccOutSchema.setAccOutUnit(AccOutUnit);
  	//tLPInsuAccOutSchema.setAccOutBala(AccOutBala);
  	  
	  //System.out.println("AccOutType:"+AccOutType);
	  System.out.println("AccOutUnit:"+AccOutUnit);
	  //System.out.println("AccOutBala:"+AccOutBala);
  }
  else if(fmAction.equals("INSERT||DEALIN"))
  {
  
  
  String tChk[] = request.getParameterValues("InpLCInsuAccGridChk"); 
		  
		  String[] tInPolNo=request.getParameterValues("LCInsuAccGrid2");//转入保单号
		  String[] tInInsuAccNo=request.getParameterValues("LCInsuAccGrid3");//转入账户
		  //String[] tInPayPlanCode=request.getParameterValues("LCInsuAccGrid5");//转入子账户
		  String[] tAccInRate=request.getParameterValues("LCInsuAccGrid7");//转入比例
		  //String[] tAccInBala=request.getParameterValues("LCInsuAccGrid10");//转入金额
		  
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
			           	tLPInsuAccInSchema.setContNo(ContNo);
			           	tLPInsuAccInSchema.setInPolNo(tInPolNo[index]);
			           	tLPInsuAccInSchema.setInInsuAccNo(tInInsuAccNo[index]);
			           	//tLPInsuAccInSchema.setInPayPlanCode(tInPayPlanCode[index]);
			           	
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
			           				//if(tAccInBala[index]!=null||!tAccInBala[index].equals(""))
			           				//{
			           				//	AccInType="2";
			           				//}
			           			}
			           		}
			           		System.out.println("AccInType:"+AccInType);   
			           	}
			           		if(AccInType.equals("3"))
			           		{
			          System.out.println("tAccInRate[index]:"+tAccInRate[index]);
			          
			           			if(tAccInRate[index]==null||tAccInRate[index].equals(""))
			           			{
			           				FlagStr="Fail";
			           				Content = "请输入转入比例!";
			           				break;
			           			}else if(Double.parseDouble(tAccInRate[index])*1000%50!=0)
			           			{
			           				FlagStr="Fail";
			           				Content = "转入比例必须为百分之五的整数倍!";
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
									
					          //if(AccInType.equals("2"))
					          //{
					          //	if(tAccInBala[index]==null||tAccInBala[index].equals(""))
					          //	{
					          //		FlagStr="Fail";
					          //		Content = "请输入转入比例!";
					          //		break;
					          //	}else{
					          //		if(!PubFun.isNumeric(tAccInBala[index]))
					          //		{
					          //			FlagStr="Fail";
					          //			Content = "请输入正确的转入比例!";
					          //			break;
					          //		}
					          //	}
					          //}
				    
				tLPInsuAccInSchema.setAccInType(AccInType);
				tLPInsuAccInSchema.setAccInRate(tAccInRate[index]);
				//tLPInsuAccInSchema.setAccInBala(tAccInBala[index]);
				tLPInsuAccInSet.add(tLPInsuAccInSchema);
			  }
  
  }
  
  }
  else if(fmAction.equals("DELETE||CANCELIN"))//转入撤销
  {

  }    

  if(FlagStr.equals("Fail"))
  {
  	//错误处理
  }else
  {
	  try
	  {
	  	 //EdorDetailUI tEdorDetailUI   = new EdorDetailUI();
	  	String busiName="EdorDetailUI";
	    BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	  	 //准备传输数据 VData
	  	 VData tVData = new VData();  
		 //保存个人保单信息(保全)
		 tVData.addElement(tG);	 	
		 tVData.addElement(tLPEdorItemSchema);
		 tVData.addElement(tLPInsuAccOutSchema);
		 tVData.addElement(tLPInsuAccInSet);
		 System.out.println("tEdorDetailUI.submitData:");
		 tBusinessDelegate.submitData(tVData,fmAction,busiName);
			
	  	//如果在Catch中发现异常，则不从错误类中提取错误信息
	 	if (FlagStr=="")
	  	{
	    		tError = tBusinessDelegate.getCErrors();
	    		if (!tError.needDealError())
	    		{
	      			Content = " 操作成功";
	    			FlagStr = "Succ";
	    			//判断转入和转出是否都有记录
	    			String strSQL1 = "select count(*) from LPInsuAccOut where edorno = '"+tLPEdorItemSchema.getEdorNo()+"'";
            		String strSQL2 = "select count(*) from LPInsuAccIn where edorno = '"+tLPEdorItemSchema.getEdorNo()+"'";
            
            		ExeSQL ex = new ExeSQL();
            
            		String count1 = ex.getOneValue(strSQL1);
            		String count2 = ex.getOneValue(strSQL2);
            		System.out.println("count1::"+count1+";;count2::"+count2);
            		if(Integer.parseInt(count1)*Integer.parseInt(count2)==0)
            		{    
            			LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
        				tLPEdorItemDB.setEdorNo(tLPEdorItemSchema.getEdorNo());
        				tLPEdorItemSchema.setSchema(tLPEdorItemDB.query().get(1));
        				tLPEdorItemSchema.setEdorState("3");
        				MMap mmap = new MMap();
        				VData lzVData = new VData();
        				mmap.put(tLPEdorItemSchema, "UPDATE");
        				lzVData.add(mmap);
        				PubSubmit tPubSubmit = new PubSubmit();
        				if(!tPubSubmit.submitData(lzVData, ""))
        				{
        				Content = " 保全状态修改失败！";
	    				FlagStr = "Fail";
        				}
            		}
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

