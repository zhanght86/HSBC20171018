package com.sinosoft.lis.ebusiness;
import org.apache.log4j.Logger;

import java.util.Vector;

import com.sinosoft.lis.db.*;
import com.sinosoft.lis.list.*;
import com.sinosoft.lis.pubfun.CheckFieldCom;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.FieldCarrier;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubCheckField;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.tb.ContBL;
import com.sinosoft.lis.tb.ContInsuredBL;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.SysConst;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;








//
//VData tBVVData = new VData();
//
//tBVVData.add("select ? *Rate/1000*(chgpaymode209( ? )/12)* ?  From Rate209 where ? =Sex and ? =AppAge and ? =PayEndYear and ? =PayEndYearFlag and chgpayintv209( ? )=PayIntv");
//
//TransferData tParams = new Transferdata();
//tParams. setNameAndValue("0","double:10000.0");
//tParams. setNameAndValue("1"," int:0");
//tParams. setNameAndValue("2"," double:1.0");
//tParams. setNameAndValue("3"," int:0");
//tParams. setNameAndValue("4"," int:29");
//tParams. setNameAndValue("5"," int:10");
//tParams. setNameAndValue("6"," String:Y");
//tParams. setNameAndValue("7"," int:0");
//tBVVData.add(tParams);
//
//ExeSQL tExeSQL = new ExeSQL();
//String tReturn = tExeSQL.getOneValue(tBVVData);


/**
 * 自助卡单激活帐号密码校验处理类
 * param:Vector:卡号,密码
 * author:zz
 * return:标识位和提示信息
 * 标识位:success:通过卡号和密码校验;fail:未通过
 */
public class ActivateBL
{
private static Logger logger = Logger.getLogger(ActivateBL.class);

	
	private String inputNo="";//输入的卡号
	private String inputPassword="";//输入的密码
	private VData mResult=new VData();//返回的结果集
	private VData tVData=new VData();//提交数据库的外围容器
	private MMap map=new MMap();//提交到数据库的结果集
	private String strAct="";//动作
	
	//保单信息
	private LCPolSchema tLCPolSchema = new LCPolSchema();//前台传入的保单信息
	private LCPolSchema mLCPolSchema = new LCPolSchema();//从数据库得到通过核销产生的保单信息
	private LCContSchema mLCContSchema = new LCContSchema();//从数据库得到通过核销产生的合同信息
	
	//投保人与被保人信息
	private LCAppntSchema tLCAppntSchema = new LCAppntSchema();
	private LCInsuredSchema tLCInsuredSchema = new LCInsuredSchema();
	private LCAddressSchema tLCAddressSchema = new LCAddressSchema(); //投保人地址信息
	private LCAddressSchema tLCAddressSchema2 = new LCAddressSchema(); //被保人地址信息
	private LCInsuredSet mLCInsuredSet = new LCInsuredSet();
	private LCAddressSet mLCAddressSet2 = new LCAddressSet();
	private String mSelfFlag = "";
	private ExeSQL mExeSQL = new ExeSQL();
	private LCPolSet mLCPolSet = new LCPolSet();
	private String mCValiDate = "";
	private int mcnt = 0;//循环变量，全局用
	ContBL tContBL=new ContBL();
	ContInsuredBL tContInsuredBL = new ContInsuredBL();
	

	//定义自助卡单的默认操作员
	public final String  Auto_CERTIFY_Opertor="KD";
	
	//定义返回标志
	public final String Success="success";//成功标志
	public final String Fail="fail";//失败标志
	
	
	public Vector submitData(Vector inParam)
    {
		strAct = (String) inParam.get(0);
        logger.debug("==>ActivateBL: 激活动作:"+strAct);

        //校验卡号密码
        if (strAct.equals("Check"))
        {
           
            if (!CheckActivate(inParam))
            {
                logger.debug("录入卡号密码未通过校验!");
                mResult.add("录入卡号密码未通过校验");
            }

            return mResult;
        }
        
        
        //自助卡单确认激活
        if (strAct.equals("Confirm"))
        {
          if (!ConfirmActivate(inParam))
          {
              // @@ 错误处理
        	  logger.debug("激活确认失败!");
          }

          return mResult;
        }
        
        
        //查询接口:卡号、险种名称、激活时间、生效时间、保险止期
        if (strAct.equals("CheckQuery"))
        {
        	//校验卡号密码
        	logger.debug("************开始进行卡号密码的校验**********");
        	inputNo=(String)inParam.get(1);
        	inputPassword=(String)inParam.get(2);
        	logger.debug("本次需要进行激活校验的卡号:"+inputNo);
        	logger.debug("本次需要进行激活校验的密码:"+inputPassword);
        	//校验:卡号不能为空
        	if(inputNo==null||inputNo.equals(""))
        	{
        		//@@错误处理
        	    logger.debug("卡号为空!");	    
        	    mResult.add(Fail);
        	    mResult.add("对不起，您输入的卡号位数不正确，请重新输入!");
        	    return mResult;
        	}
        	logger.debug("***************校验卡号不能为空结束!*********\n");
        	
        	//校验:卡号长度必须是20
        	logger.debug("卡号的长度:"+inputNo.length());
        	if(inputNo.length()!=20)
        	{
        		//@@错误处理
        	    logger.debug("卡号长度不是20位!");  	    
        	    mResult.add(Fail);
        	    mResult.add("对不起，您输入的卡号位数不正确，请重新输入!");
        	    return mResult;
        	}
        	
        	
        	logger.debug("***************校验卡号长度结束!*********\n");
        	
            //校验：卡号密码必须匹配，否则不能激活
            boolean checkflag=true;
        	DESPlus tDESPlus;
    		try 
    		{
    			tDESPlus = new DESPlus();
    			String newPassword="";

    			//如果生成的密码为空,则重新计算,直到生成了一个密码为止
    			newPassword=tDESPlus.getEncryptKey(inputNo);

    			logger.debug("根据卡号重新计算生成的密码:"+newPassword);
    			logger.debug("录入的密码:"+inputPassword);
    			
    			if(!newPassword.equals(inputPassword))
    			{
    				checkflag=false;
    				logger.debug("卡号"+inputNo+"的自助卡单录入密码错误");
    			}
    		} 
    		catch (Exception e) 
    		{
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    			checkflag=false;
    			logger.debug("校验卡号和密码匹配时出现异常!");
    		}
    		finally
    		{
    			if(checkflag==false)
    			{
    				logger.debug("Finallly:卡号"+inputNo+"的自助卡单录入密码错误");
    				mResult.add(Fail);
    	    	    mResult.add("对不起，您输入的密码与卡号不符，请核对卡号与密码后重新输入!");
    	    	    return mResult;
    			}
    		}
    		
    		
    		logger.debug("***************校验卡号和密码匹配结束*********\n");
            
            if (!ActivateQuery(inParam))
            {
               // @@ 错误处理
        	   logger.debug("已激活卡的基本信息查询失败!");
        	   mResult.add("FAIL");
               mResult.add("已激活卡的基本信息查询失败!");
        	   return mResult;
            }

          return mResult;
        }
        
        
        //定时批处理接口:将当天内没有激活成功的数据，再通过接口进行确认这批数据是否已经激活
        if (strAct.equals("BatchQuery"))
        {
            
            if (!BatchAutoQuery(inParam))
            {
               // @@ 错误处理
        	   logger.debug("查询卡单险种信息失败!");
        	   mResult.add("跑批业务处理失败!");
        	   return mResult;
            }

          return mResult;
        }
        
        
        //查询卡单的详细信息接口
        if (strAct.equals("DetailQuery"))
        {
            
            if (!DetailAutoQuery(inParam))
            {
               // @@ 错误处理
        	   logger.debug("已激活卡的详细信息查询失败!");
        	   mResult.add("已激活卡的详细信息查询失败!");
        	   return mResult;
            }

          return mResult;
        }


        return mResult;
    }
	
	
	
	private boolean CheckPassword(Vector inParam) {
		mResult.clear();
    	String mInputNo=(String)inParam.get(1);
    	String mInputPassword=(String)inParam.get(2);
		boolean checkflag=true;
    	DESPlus tDESPlus;
		try 
		{
			tDESPlus = new DESPlus();
			String newPassword="";

			//如果生成的密码为空,则重新计算,直到生成了一个密码为止
			newPassword=tDESPlus.getEncryptKey(mInputNo);

			logger.debug("根据卡号重新计算生成的密码:"+newPassword);
			logger.debug("录入的密码:"+mInputPassword);
			
			if(!newPassword.equals(mInputPassword))
			{
				checkflag=false;
				logger.debug("卡号"+inputNo+"的自助卡单录入密码错误");
			}
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			checkflag=false;
			logger.debug("校验卡号和密码匹配时出现异常!");
		}
		finally
		{
			if(checkflag==false)
			{
				logger.debug("Finallly:卡号"+inputNo+"的自助卡单录入密码错误");
				mResult.add("FAIL");
	    	    mResult.add("对不起，您输入的密码与卡号不符，请核对卡号与密码后重新输入!");
	    	    return false;
			}
		}
		return true;
	}



	/**
	 * 查询卡单的详细信息接口:
	 * 标志位（1—成功0失败）、提示信息
	 * 保单信息:卡号、险种名称、生效日期、保险责任终止日期,保额,保费;
       投保人信息项:投保人姓名,性别,证件类型,证件号码,出生日期,联系电话,通讯地址,邮编,电子邮件,职业代码,职业类别,与被保人关系;
       被保人信息项:被保人姓名,性别,证件类型,证件号码,出生日期,联系电话,通讯地址,电子邮件,邮编,职业代码,职业类别
	 * author:zz
	 */
    public boolean DetailAutoQuery(Vector inParam)
    {
    	mResult.clear();
    	logger.debug("详细信息接口");
    	inputNo=(String)inParam.get(1);
    	logger.debug("查询详细信息的卡号:"+inputNo);
    	
    	String result="";//返回结果,是保单信息+投保人信息+被保人信息拼出的字符串
    	
    	//查询保单信息
    	String LcPolSql="  select contno,(select riskname from lmrisk where riskcode = lcpol.riskcode and riskversion = lcpol.riskversion),cvalidate,enddate,amnt,prem"
    		       + "  from lcpol"
    		       + "  where contno='"+inputNo+"'";
    	logger.debug("详细信息接口:查询保单信息的SQL:"+LcPolSql);
    	
    	String strReturn = execSql(LcPolSql);
        String strR = strReturn.substring(0, 3);
        if (strR.equals("100"))
        {
            mResult.add("FAIL");
        }
        
        result=strReturn;
        
        
        //查询投保人信息
        String LCAppntSql="select t.Appntname,t.Appntsex,t.idtype,t.idno,a.Phone,t.Appntbirthday,a.PostalAddress,a.EMail,a.ZipCode,(select OccupationName from LDOccupation where LDOccupation.OccupationCode = t.OccupationCode ),t.OccupationType,t.RelationToInsured "
            	+"from lcappnt t,lccont p,lcaddress a  	where t.contno=p.contno  and t.appntno=p.appntno and a.customerno=t.appntno and a.addressno=t.addressno and t.contno='"+inputNo+"'";
        logger.debug("详细信息接口:查询投保人信息的SQL:"+LCAppntSql);

        strReturn = execSql(LCAppntSql);
        strR = strReturn.substring(0, 3);
        if (strR.equals("100"))
        {
        	mResult.add("FAIL");
        }
        
        //将查询结果相连,以^分为三段
        result=result+strReturn.substring(3);
    	
        
        //查询被保人信息
        String LCAInsuredSql=    
        "select k.relationtoappnt, k.name,k.sex,k.idtype,k.idno,k.birthday,a.Phone,a.PostalAddress,a.EMail,a.ZipCode,(select OccupationName from LDOccupation where LDOccupation.OccupationCode = k.OccupationCode ),k.OccupationType " 
        	+"from lcinsured k,lccont p,lcaddress a  	where k.contno=p.contno  and k.insuredno=p.insuredno and a.customerno=k.insuredno and a.addressno=k.addressno "
        	+"and k.contno='"+inputNo+"'";
        logger.debug("详细信息接口:查询被保人信息的SQL:"+LCAInsuredSql);

        strReturn = execSql(LCAInsuredSql);
        strR = strReturn.substring(0, 3);
        if (strR.equals("100"))
        {
        	mResult.add("FAIL");
        }
        
        result=result+strReturn.substring(3);
        
        mResult.add(Success);
        mResult.add(result);
    	
    	logger.debug("卡号"+inputNo+"执行查询接口结束!");
    	
    	return true;
    }
	
	
	
	/**
	 * 定时批处理接口:将当天内没有激活成功的数据，再通过接口进行确认这批数据是否已经激活
	 * 标志位（1—成功0失败）、提示信息
	 * 卡号、激活状态（1:激活,0未激活）、激活时间、激活类型,生效日期,责任终止日期
	 * author:zz
	 */
    public boolean BatchAutoQuery(Vector inParam)
    {
    	mResult.clear();
    	logger.debug("定时批处理接口");
    	inputNo=(String)inParam.get(1);
    	
    	String inputNo2="";
    	String[] result=inputNo.split(",");
    	for(int i=0;i<result.length;i++)
    	{
    		if(i==0)
    		{
    			inputNo2="'"+result[i]+"'";
    		}
    		else
    		{
    			inputNo2=inputNo2+",'"+result[i]+"'";
    		}
    	}
    	logger.debug("inputNo2:"+inputNo2);
    	
    	
    	logger.debug("需要处理定时批处理的卡号:"+inputNo2);
    	
		//原有自助卡单校验，即非家庭单校验
    	String sql="";
		if(!isPortfolio(inputNo))
		{
	    	 sql=	" select a.contno,case when b.customgetpoldate is null then '0' else '1' end, "
	 	    		+"    b.customgetpoldate,a.standbyflag1,a.cvalidate,a.enddate from lcpol a,lccont b where a.contno=b.contno and a.contno in("+inputNo2+")";
	    	logger.debug("定时批处理接口SQL:"+sql);
		}
		else
		{
	    	 sql= " select b.familyid,case when (select customgetpoldate from lccont where contno=a.contno) is null then '0' else '1' end, "
		    	+ " (select customgetpoldate from lccont where contno=a.contno),a.standbyflag1,a.cvalidate,a.enddate from lcpol a,lcinsured b where a.contno=b.contno "
		    	+ "and sequenceno='1' and a.insuredno=b.insuredno and b.familyid in("+inputNo2+")";
		    	logger.debug("定时批处理接口SQL:"+sql);
		}

    	String strReturn = execSql(sql);
        String strR = strReturn.substring(0, 3);
        if (strR.equals("100"))
        {
            mResult.add("FAIL");
        }
        
        mResult.add(Success);
        mResult.add(strReturn);

    	logger.debug("卡号"+inputNo+"执行查询接口结束!");
    	
    	return true;
    }
	
	
	
	/**
	 * 查询接口:卡号、险种编码、险种名称、激活时间、生效时间、保险止期
	 * 标志位（1—成功0失败）、提示信息(查询失败时，则没有返回信息)
	 * author:zz
	 */
    public boolean ActivateQuery(Vector inParam)
    {
    	mResult.clear();
    	logger.debug("开始执行查询接口");
    	String inputNo = (String)inParam.get(1);
    	String sql="";
		//原有自助卡单校验，即非家庭单校验
    	
		if(!isPortfolio(inputNo))
		{
	    	 sql="select a.contno,a.riskcode,(select riskname from lmrisk where riskcode=a.riskcode and riskversion=a.riskversion) "
	    		+" ,b.customgetpoldate,a.cvalidate,a.enddate,b.contno from lcpol a,lccont b "
	    		+" where b.contno='"+inputNo+"' and a.contno=b.contno and b.CustomGetPolDate is not null";
	    	logger.debug("查询接口:卡号、险种编码、险种名称、激活时间、生效时间、保险止期SQL:"+sql);
		}
		else
		{
			  String mCertiCode=GetCertifyType(inputNo);
	    	if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
	    		 sql="select b.familyid,(select riskcode from lmcardrisk where certifycode='"+mCertiCode+"' ), "
	    		    	 	+"(select contplanname from ldplan where contplancode in (select riskcode from lmcardrisk where certifycode='"+mCertiCode+"')) "
	    			    	+" ,b.customgetpoldate,b.cvalidate,(select enddate from lcpol where contno=b.contno and rownum=1),b.contno from lccont b ,lcinsured c  "
	    			    	+" where b.familyid ='"+inputNo+"' and b.contno=c.contno and b.CustomGetPolDate is not null order by  c.sequenceno ";
	    	}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
	    		 sql="select b.familyid,(select riskcode from lmcardrisk where certifycode='"+mCertiCode+"' ), "
	    		    	 	+"(select contplanname from ldplan where contplancode in (select riskcode from lmcardrisk where certifycode='"+mCertiCode+"')) "
	    			    	+" ,b.customgetpoldate,b.cvalidate,(select enddate from lcpol where contno=b.contno limit 1),b.contno from lccont b ,lcinsured c  "
	    			    	+" where b.familyid ='"+inputNo+"' and b.contno=c.contno and b.CustomGetPolDate is not null order by  c.sequenceno ";
	    	}
		    	logger.debug("查询接口:卡号、险种编码、险种名称、激活时间、生效时间、保险止期SQL:"+sql);
		    	
//		    	sql="select b.familyid,(select riskcode from lmcardrisk where certifycode='"+"?certifycode?"+"' ), "
//			    	 	+"(select contplanname from ldplan where contplancode in (select riskcode from lmcardrisk where certifycode='"+"?certifycode?"+"')) "
//				    	+" ,b.customgetpoldate,b.cvalidate,(select enddate from lcpol where contno=b.contno and rownum = 1),b.contno from lccont b ,lcinsured c  "
//				    	+" where b.familyid ='"+"?contno?"+"' and b.contno=c.contno and b.CustomGetPolDate is not null order by  c.sequenceno ";
//				    	logger.debug("查询接口:卡号、险种编码、险种名称、激活时间、生效时间、保险止期SQL:"+sql);
		}

    	String strReturn = execSql(sql);
        String strR = strReturn.substring(0, 3);
        if (strR.equals("100"))
        {
            mResult.add("FAIL");
        }
        mResult.add(Success);
        mResult.add(strReturn);
    	
    	
    	logger.debug("卡号"+inputNo+"执行查询接口结束!");
    	
    	return true;
    }
    
    
    /**
     * 执行查询
     * @param inParam
     * @return
     */
    public String execSql(String strSQL)
    {
        logger.debug("Server ExecSql: " + strSQL);
        String strReturn = mExeSQL.getEncodedResult(strSQL, 1);

        return strReturn;
    }
	
	
	/**
	 * 校验卡号，密码是否正确,是否已经激活,是否已经过了激活的有效期
	 * 输入:两项:卡号,密码
	 * 输出:三项:通过标志(1:通过;0:不通过)，返回信息,险种编码
	 * author:zz
	 */
    public boolean ConfirmActivate(Vector inParam)
    {
    	mResult.clear();
    	logger.debug("开始执行确认激活操作");
    	
    	//获取数据
    	if(!GetInputData(inParam))
    	{
    		return false;
    	}

    	//首先判断数据库中是否生成了保单信息,如果还没有调用方法先产生保单信息
    	boolean flag=false;
    	flag=CheckProduceLCPol(tLCPolSchema.getContNo());
    	if(flag==false)
    	{
    		mResult.add(Fail);
        	mResult.add("生成保单信息出错,请先查明原因再进行激活!");
            return false;
    	}    	
    	
    	//其次判断数据库中是否核销成功,否则调用方法进行核销
    	flag=false;
    	flag=CheckSign(tLCPolSchema.getContNo());
    	if(flag==false)
    	{
    		mResult.add(Fail);
        	mResult.add("签单错误,请先查明原因再进行激活!");
            return false;
    	}
		LCContSet tLCContSet = new LCContSet();
		LCContDB tLCContDB = new LCContDB();
		String tSql = "select a.* from lccont a,lcinsured b where a.prtno='"+"?prtno?"+"' and a.contno=b.contno and a.insuredno=b.insuredno order by b.sequenceno";
		logger.debug("sql:"+tSql);
		
		SQLwithBindVariables sqlvb6 = new SQLwithBindVariables();
		sqlvb6.sql(tSql);
		sqlvb6.put("prtno", tLCPolSchema.getPrtNo());
		tLCContSet = tLCContDB.executeQuery(sqlvb6);
		if(tLCContSet.size()<=0){
			mResult.add(Fail);
        	mResult.add("合同信息不存在!");
            return false;
		}
		for(mcnt=1;mcnt<=tLCContSet.size();mcnt++){
			mLCContSchema = tLCContSet.get(mcnt);
			if(mLCContSchema.getContNo()==null || "".equals(mLCContSchema.getContNo())){
    			mResult.add(Fail);
            	mResult.add("合同信息错误：合同号为空!");
                return false;
    		}
			tLCInsuredSchema = mLCInsuredSet.get(mcnt);
			tLCAddressSchema2 = mLCAddressSet2.get(mcnt);
			tLCInsuredSchema.setContNo(mLCContSchema.getContNo());
			tLCInsuredSchema.setFamilyID(mLCContSchema.getFamilyID());
			tLCAppntSchema.setContNo(mLCContSchema.getContNo());
			if("00".equals(tLCInsuredSchema.getRelationToAppnt())){
				tLCAppntSchema.setRelationToInsured("00");
			}else{
				tLCAppntSchema.setRelationToInsured("");
			}
			//增加一个mLCContSchema.getContNo()是否为空的校验 yanglh
			LCPolDB tLCPolDB = new LCPolDB();
			tLCPolDB.setContNo(mLCContSchema.getContNo());
			mLCPolSet = tLCPolDB.query();
			if(mLCPolSet.size()<=0){
    			mResult.add(Fail);
            	mResult.add("保单信息!");
                return false;
    		}
//    				进行规则校验
			for(int ci = 1; ci <= mLCPolSet.size();ci++){
				tLCPolSchema = mLCPolSet.get(ci);
	            tLCPolSchema.setPolTypeFlag("0");//保单类型标记 0 --个人单,1 --无名单,2 --（团单）公共帐户  
	        	tLCPolSchema.setCValiDate(mCValiDate);
	        	tLCPolSchema.setStandbyFlag1("在线");//卡单激活类型
				
	        	mLCPolSchema = mLCPolSet.get(ci);
	            mLCPolSchema.setPolTypeFlag("0");//保单类型标记 0 --个人单,1 --无名单,2 --（团单）公共帐户  
	        	mLCPolSchema.setCValiDate(mCValiDate);
	        	mLCPolSchema.setStandbyFlag1("在线");//卡单激活类型
		    	if(!CheckData())
		    	{
		    		return false;
		    	}
			}
	    	
	    	//存储投被保人信息,客户号合并,更改激活日期和生效日期
	    	if(!DealData())
	    	{
	    		return false;
	    	}
		}
    	//将数据放入VData中,调用PubSubmit进行数据库的访问
        PubSubmit ps=new PubSubmit();
    	if(!ps.submitData(tVData))
    	{   	
        	mResult.add(Fail);
        	mResult.add("保存投保人与被保人信息出错!");
            return false;
    	}
    	
    	mResult.add(Success);
    	mResult.add("您的卡单已经激活成功，请您妥善保管您的卡式保单!"+"||"+mLCPolSchema.getMakeDate()+"||"+mLCPolSchema.getCValiDate()+"||"+mLCPolSchema.getEndDate());
    	
    	logger.debug("卡号"+tLCPolSchema.getPolNo()+"激活确认结束!");
    	
    	return true;
    }
    
    
    
    /**
	 * 获取输入信息
	 * param:Vector
	 * author:zz
	 */
    private boolean GetInputData(Vector ttInParam)
    {
    	String actionFlag=(String)ttInParam.get(1);
    	logger.debug("激活数据来源:"+actionFlag);
	
    	//HX:代表是数据是从核心这边传入
    	if(actionFlag.equals("EC"))
    	{
    		
    		
    		mSelfFlag = "new";
    		String startNo = (String)ttInParam.get(2);
    		int tPeoples = Integer.parseInt((String)ttInParam.get(3));
    		LDPlanDB tLDPlanDB = new LDPlanDB();
    		LDPlanSet tLDPlanSet = new LDPlanSet();
    		String tSql = "select * from ldplan ld where ld.contplancode in (select riskcode from lmcardrisk lm where lm.certifycode='"+"?certifycode?"+"')";
    		SQLwithBindVariables sqlvb7 = new SQLwithBindVariables();
    		sqlvb7.sql(tSql);
    		sqlvb7.put("certifycode", GetCertifyType((String)ttInParam.get(2)));
    		tLDPlanSet = tLDPlanDB.executeQuery(sqlvb7);
    		if(tLDPlanSet.size()<=0){
    			mResult.add(Fail);
            	mResult.add("产品信息不存在!");
                return false;
    		}
    		if(tLDPlanSet.get(1).getPeoples3()!=tPeoples){
    			mResult.add(Fail);
            	mResult.add("被保人数量错误，传入"+tPeoples+"名被保人信息，应传"+tLDPlanSet.get(1).getPeoples3()+"名被保人!");
                return false;
    		}
    		//被保人信息
    		for(int i=0;i<tPeoples;i++){
    			LCInsuredSchema aLCInsuredSchema = new LCInsuredSchema();
    			aLCInsuredSchema.setContNo((String)ttInParam.get(2));//保单号
        		aLCInsuredSchema.setPrtNo((String)ttInParam.get(2));//保单号
        		aLCInsuredSchema.setGrpContNo("00000000000000000000");
        		aLCInsuredSchema.setRelationToMainInsured("00");
        		aLCInsuredSchema.setSequenceNo((String)ttInParam.get(17+14*i));
        	   	aLCInsuredSchema.setName((String)ttInParam.get(4+14*i));              //姓名 
        	   	aLCInsuredSchema.setSex((String)ttInParam.get(5+14*i));                 //性别
        	   	aLCInsuredSchema.setBirthday((String)ttInParam.get(8+14*i));      //出生日期 
        	   	aLCInsuredSchema.setIDType((String)ttInParam.get(6+14*i));         //证件类型
        	   	aLCInsuredSchema.setIDNo((String)ttInParam.get(7+14*i));              //证件号码 	    		
        	   	aLCInsuredSchema.setOccupationType((String)ttInParam.get(13+14*i));       //职业类别
        	   	aLCInsuredSchema.setOccupationCode((String)ttInParam.get(12+14*i));       //职业代码
        	   	aLCInsuredSchema.setRelationToAppnt((String)ttInParam.get(16+14*i));
        	   	mLCInsuredSet.add(aLCInsuredSchema);
        	   	
        	   	LCAddressSchema aLCAddressSchema2 = new LCAddressSchema();
        	   	aLCAddressSchema2.setPostalAddress((String)ttInParam.get(10+14*i));//联系地址
                aLCAddressSchema2.setZipCode((String)ttInParam.get(14+14*i));         //邮政编码
                aLCAddressSchema2.setPhone((String)ttInParam.get(9+14*i));            //联系电话 
                aLCAddressSchema2.setEMail((String)ttInParam.get(11+14*i));             //电子邮箱
                mLCAddressSet2.add(aLCAddressSchema2);			
    		}
    		
    		int plus = 14*(tPeoples-1);//偏移量，每多一个被保人，投保人信息就向后推12位
    		
    		mCValiDate = (String)ttInParam.get(15+plus);
    		tLCPolSchema.setContNo((String)ttInParam.get(2));
    		tLCPolSchema.setPrtNo((String)ttInParam.get(2));
    		tLCPolSchema.setInsuredPeoples("1");//被保人人数
            tLCPolSchema.setPolTypeFlag("0");//保单类型标记 0 --个人单,1 --无名单,2 --（团单）公共帐户  
        	tLCPolSchema.setCValiDate((String)ttInParam.get(15+plus));
        	tLCPolSchema.setStandbyFlag1("在线");//卡单激活类型
        	logger.debug("Save:CValidate:"+tLCPolSchema.getCValiDate());

            logger.debug("设置保单基本信息结束...");       
              		
        	// 投保人信息部分
        	logger.debug("开始设置投保人基本信息...");
       		tLCAppntSchema.setContNo((String)ttInParam.get(2));
       		tLCAppntSchema.setPrtNo((String)ttInParam.get(2));
       		tLCAppntSchema.setGrpContNo("00000000000000000000");
       		tLCAppntSchema.setAppntGrade("M");//投保人级别:M ---主投保人,S ---从头保人
       		tLCAppntSchema.setAppntName((String)ttInParam.get(18+plus));              //姓名 
       		tLCAppntSchema.setAppntSex((String)ttInParam.get(24+plus));                  //性别
       		tLCAppntSchema.setAppntBirthday((String)ttInParam.get(25+plus));       //出生日期 
       		tLCAppntSchema.setIDType((String)ttInParam.get(19+plus));          //证件类型
       		tLCAppntSchema.setIDNo((String)ttInParam.get(20+plus));                 //证件号码     		
       		tLCAppntSchema.setOccupationType((String)ttInParam.get(23+plus));     //职业类别
       		tLCAppntSchema.setOccupationCode((String)ttInParam.get(22+plus));      //职业代码
       		
        		
        	tLCAddressSchema.setPostalAddress((String)ttInParam.get(27+plus));//联系地址
        	tLCAddressSchema.setZipCode((String)ttInParam.get(26+plus));       //邮政编码
        	tLCAddressSchema.setPhone((String)ttInParam.get(21+plus));          //联系电话 
        	tLCAddressSchema.setEMail((String)ttInParam.get(28+plus));           //电子邮箱
        			
        	logger.debug("设置投保人信息结束...");    			
        			
        	logger.debug("开始设置被保人基本信息...");
        			
    		
    	
    		logger.debug("被置投保人信息结束...");		
    	}
    	else
    	{
    		TransferData tTransferData = new TransferData();
			tTransferData = (TransferData)ttInParam.get(7);
			mSelfFlag = (String) tTransferData.getValueByName("SelfFlag");
			if("new".equals(mSelfFlag)){
	    		tLCPolSchema.setSchema((LCPolSchema)ttInParam.get(2));
	    		tLCAppntSchema.setSchema((LCAppntSchema)ttInParam.get(3));
	        	mLCInsuredSet.set((LCInsuredSet)ttInParam.get(4));
	        	tLCAddressSchema.setSchema((LCAddressSchema)ttInParam.get(5));
	        	mLCAddressSet2.set((LCAddressSet)ttInParam.get(6));
			}else{
				tLCPolSchema.setSchema((LCPolSchema)ttInParam.get(2));
	    		tLCAppntSchema.setSchema((LCAppntSchema)ttInParam.get(3));
	        	tLCInsuredSchema.setSchema((LCInsuredSchema)ttInParam.get(4));
	        	mLCInsuredSet.clear();
	        	mLCInsuredSet.add(tLCInsuredSchema);
	        	tLCAddressSchema.setSchema((LCAddressSchema)ttInParam.get(5));
	        	tLCAddressSchema2.setSchema((LCAddressSchema)ttInParam.get(6));
	        	mLCAddressSet2.clear();
	        	mLCAddressSet2.add(tLCAddressSchema2);
			}
    	}
    	
    	return true;
    }
    
    /**
	 * 查询财务信息是否核销成功
	 * author:zz
	 */
    private boolean CheckSign(String tcontno)
    {
    	logger.debug("查询是否已经核销财务信息的卡号:"+tcontno);
    	String sql="select * from ljtempfee where otherno='"+"?otherno?"+"' and confflag='1'";
    	logger.debug("查询是否已经核销财务信息的SQL:"+sql);    	;
    	SSRS tSSRS=new SSRS();
    	SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
    	sqlbv8.sql(sql);
    	sqlbv8.put("otherno", tcontno);
    	tSSRS=mExeSQL.execSQL(sqlbv8);
    	logger.debug("查询是否已经核销财务信息的返回记录数量:"+tSSRS.getMaxRow());
    	if(tSSRS.getMaxRow()==0)
    	{
    		SinApproveBL tSinApproveBL=new SinApproveBL();
    		if(!tSinApproveBL.Sign(tcontno))
    		{
    			logger.debug("签发"+tcontno+"的保单信息出错!");
				return false;
    		}
    	}
    	return true;
    }
    
    
    /**
	 * 查询是否已产生保单信息,如果还没哟生成,则调用方法实时生成保单信息
	 * author:zz
	 */
    private boolean CheckProduceLCPol(String tcontno)
    {
    	logger.debug("查询是否已经产生保单信息的卡号:"+tcontno);
    	LCContDB tLCContDB=new LCContDB();
    	tLCContDB.setPrtNo(tcontno); 
    	LCContSet tLCContSet = tLCContDB.query();
    	if(tLCContSet.size()<=0)
    	{
    		LJTempFeeDB tLJTempFeeDB=new LJTempFeeDB();
    		String feeSql="select * from ljtempfee where otherno='"+"?otherno?"+"' and enteraccdate is not null";
    		logger.debug("查询卡号"+tcontno+"是否已到帐或已缴费的sql:"+feeSql);
    		
    		SQLwithBindVariables sqlvb9 = new SQLwithBindVariables();
    		sqlvb9.sql(feeSql);
    		sqlvb9.put("otherno", tcontno);
    		LJTempFeeSet tLJTempFeeSet=new LJTempFeeSet();
    		tLJTempFeeSet=tLJTempFeeDB.executeQuery(sqlvb9);
    		logger.debug("tLJTempFeeSet.size()"+tLJTempFeeSet.size());
    		if(tLJTempFeeSet.size()!=0)
    		{
    			LJTempFeeSchema tLJTempFeeSchema=new LJTempFeeSchema();
    			tLJTempFeeSchema=tLJTempFeeSet.get(1);
    			SinApproveBL tSinApproveBL=new SinApproveBL();
    			if(!tSinApproveBL.Proposal(tLJTempFeeSchema))
    			{
    				logger.debug("生成"+tcontno+"的保单信息出错!");
    				return false;
    			}  
    		}
    	}    	

    	return true;
    }
    
    
    /**
	 * 存储投被保人信息,更改激活日期和生效日期
	 * author:zz
	 */
    private boolean DealData()
    {
        logger.debug("开始准备数据保存投被保人信息,并更改激活日期和生效日期");        

        //准备保单表,投被保人的数据
        if(!PrepareLCPol())
        {
        	return false;
        }        
        
        //客户号合并:ProposalBL.getCustomerNo方法
        if(!DealCustomerNo())
        {
        	return false;
        }
        
        //准备插入数据库中的数据
        if(!PrepareData())
        {
        	return false;
        }
        
    	return true;
    }
    
    
    
    /**
	 * 准备插入数据库中的数据
	 * author:zz
	 */
    private boolean PrepareData()
    {
    	logger.debug("准备插入数据库中的数据");
    	
    	//删除核销时产生的保单数据,虚拟投,被保人信息,虚拟被保人信息以及在激活界面录错的投,被保人信息
    	//删除LDPerson LCAddress时注意：虚拟投被保人是同一客户号
    	String sqlA="delete from lcpol where contno='"+"?contno?"+"'";   
    	SQLwithBindVariables sqlvb10 = new SQLwithBindVariables();
    	sqlvb10.sql(sqlA);
    	sqlvb10.put("contno", mLCContSchema.getContNo());
    	
    	String sqlB="delete from lcappnt where contno='"+"?contno?"+"' ";
    	SQLwithBindVariables sqlvb11 = new SQLwithBindVariables();
    	sqlvb11.sql(sqlB);
    	sqlvb11.put("contno", mLCContSchema.getContNo());
    	
    	String sqlC="delete from lcinsured where contno='"+"?contno?"+"'";
    	SQLwithBindVariables sqlvb12 = new SQLwithBindVariables();
    	sqlvb12.sql(sqlC);
    	sqlvb12.put("contno", mLCContSchema.getContNo());
    	
    	String sqlD="delete from LDPerson where customerno = (select appntno from lccont where contno = '"+"?contno?"+"')";
    	SQLwithBindVariables sqlvb13 = new SQLwithBindVariables();
    	sqlvb13.sql(sqlD);
    	sqlvb13.put("contno", mLCContSchema.getContNo());
    	
    	String sqlE="delete from LCAddress where customerno = (select appntno from lccont where contno = '"+"?contno?"+"')";
    	SQLwithBindVariables sqlvb14 = new SQLwithBindVariables();
    	sqlvb14.sql(sqlE);
    	sqlvb14.put("contno", mLCContSchema.getContNo());
    	
    	String sqlF="delete from lccont where contno='"+"?contno?"+"'";
    	SQLwithBindVariables sqlvb15 = new SQLwithBindVariables();
    	sqlvb15.sql(sqlF);
    	sqlvb15.put("contno", mLCContSchema.getContNo());
    	
    	map.put(sqlvb10,"DELETE");
    	map.put(sqlvb11,"DELETE");
    	map.put(sqlvb12,"DELETE");
    	map.put(sqlvb13,"DELETE");
    	map.put(sqlvb14,"DELETE");
    	map.put(sqlvb15,"DELETE");        
        
        //投保人信息        
        tLCAppntSchema.setOperator(Auto_CERTIFY_Opertor);
		tLCAppntSchema.setMakeDate(PubFun.getCurrentDate());
		tLCAppntSchema.setMakeTime(PubFun.getCurrentTime());
		tLCAppntSchema.setModifyDate(PubFun.getCurrentDate());
		tLCAppntSchema.setModifyTime(PubFun.getCurrentTime());
		tLCAppntSchema.setManageCom(mLCPolSchema.getManageCom());
		LCAppntSchema aLCAppntSchema = new LCAppntSchema();
		aLCAppntSchema = tLCAppntSchema.getSchema();
		map.put(aLCAppntSchema,"DELETE&INSERT");
		LCAddressSchema aLCAddressSchema = new LCAddressSchema();
		aLCAddressSchema = tLCAddressSchema.getSchema();
		map.put(aLCAddressSchema, "DELETE&INSERT");
        
        //被保人信息
		tLCInsuredSchema.setAppntNo(tLCAppntSchema.getAppntNo());
		tLCInsuredSchema.setManageCom(mLCPolSchema.getManageCom());
		tLCInsuredSchema.setExecuteCom(tLCInsuredSchema.getManageCom());
		tLCInsuredSchema.setOperator(Auto_CERTIFY_Opertor);
		tLCInsuredSchema.setMakeDate(PubFun.getCurrentDate());
		tLCInsuredSchema.setMakeTime(PubFun.getCurrentTime());
		tLCInsuredSchema.setModifyDate(PubFun.getCurrentDate());
		tLCInsuredSchema.setModifyTime(PubFun.getCurrentTime());
		LCInsuredSchema aLCInsuredSchema = new LCInsuredSchema();
		aLCInsuredSchema = tLCInsuredSchema.getSchema();
        map.put(aLCInsuredSchema,"INSERT");
        if(!"00".equals(tLCAppntSchema.getRelationToInsured()))
        {
        	LCAddressSchema aLCAddressSchema2 = new LCAddressSchema();
        	aLCAddressSchema2 = tLCAddressSchema2.getSchema();
        	map.put(aLCAddressSchema2, "INSERT");
        }
          
                
        /**
         * 更新生效日期
    	 * lcpol的cvalidate,getstartdate(起领日期),,LastRevDate(最近复效日期),enddate,paytodate,PayEndDate(后三个是终止日期)
    	 * lcget的gettodate,getstartdate,GetEndDate
    	 * lcduty的firstpaydate,getstartdate,payenddate,paytodate,enddate(后三个为终止日期)
    	 * lcprem中paystartdate,payenddate,paytodate(后两个为终止日期)
    	 * ljapayperson中curpaytodate（终止日期）
    	 *  //lacommision中cvalidate(生效日期),这个表考虑并发因素，暂时不予修改
    	 * 这些起始日期==生效日期,终止日期=起始日期+保险期间
    	 */
        for(int pi=1;pi<=mLCPolSet.size();pi++){
        	mLCPolSchema = mLCPolSet.get(pi);
	        logger.debug("卡号"+tLCPolSchema.getContNo()+"的生效日期:"+tLCPolSchema.getCValiDate());
	        logger.debug("卡单"+mLCPolSchema.getContNo()+"是否指定生效日期标志:"+mLCPolSchema.getSpecifyValiDate());
	        logger.debug("卡单"+mLCPolSchema.getContNo()+"的保险年期:"+mLCPolSchema.getInsuYear()+",保险年期标志:"+mLCPolSchema.getInsuYearFlag());
	
	        logger.debug("保险责任的终止日期:"+PubFun.newCalDate(PubFun.newCalDate(mLCPolSchema.getCValiDate(),"D",-1),mLCPolSchema.getInsuYearFlag(),mLCPolSchema.getInsuYear()));
	        mLCPolSchema.setEndDate(PubFun.newCalDate(mLCPolSchema.getCValiDate(),mLCPolSchema.getInsuYearFlag(),mLCPolSchema.getInsuYear()));
	        mLCPolSchema.setPaytoDate(mLCPolSchema.getEndDate());
	        mLCPolSchema.setPayEndDate(mLCPolSchema.getEndDate());
	        mLCPolSchema.setGetStartDate(mLCPolSchema.getCValiDate());
	        mLCPolSchema.setLastRevDate(mLCPolSchema.getCValiDate());     
	        
	        //更新客户号以及生效日期
	        
	        SQLwithBindVariables sqlvb16 = new SQLwithBindVariables();
	        sqlvb16.sql("update LJAPayPerson set appntno= '"+"?appntno?"+"',curpaytodate='"+"?curpaytodate?"+"' where contno = '"+"?contno?"+"'");
	    	sqlvb16.put("appntno", tLCAppntSchema.getAppntNo());
	    	sqlvb16.put("curpaytodate", mLCPolSchema.getEndDate());
	    	sqlvb16.put("contno", mLCPolSchema.getContNo());
	    	
	    	SQLwithBindVariables sqlvb17 = new SQLwithBindVariables();
	    	sqlvb17.sql("update LJAPay set appntno= '"+"?appntno?"+"' where incomeno = '"+"?incomeno?"+"'");
	    	sqlvb17.put("appntno", tLCAppntSchema.getAppntNo());
	    	sqlvb17.put("incomeno", mLCPolSchema.getContNo());
	    	
	    	SQLwithBindVariables sqlvb18 = new SQLwithBindVariables();
	    	sqlvb18.sql("update LCGet set insuredno= '"+"?insuredno?"+"',gettodate= '"+"?gettodate?"+"',getstartdate='"+"?getstartdate?"+"',getenddate='"+"?getenddate?"+"' where contno = '"+"?contno?"+"'");
	    	sqlvb18.put("insuredno", tLCInsuredSchema.getInsuredNo());
	    	sqlvb18.put("gettodate", mLCPolSchema.getCValiDate());
	    	sqlvb18.put("getstartdate", mLCPolSchema.getCValiDate());
	    	sqlvb18.put("getenddate", mLCPolSchema.getEndDate());
	    	sqlvb18.put("contno", mLCPolSchema.getContNo());
	    	
	    	SQLwithBindVariables sqlvb19 = new SQLwithBindVariables();
	    	sqlvb19.sql("update lcduty set firstpaydate= '"+"?firstpaydate?"+"',getstartdate='"+"?getstartdate?"+"',paytodate='"+"?paytodate?"+"',payenddate='"+"?payenddate?"+"',enddate='"+"?enddate?"+"' where contno = '"+"?contno?"+"'");
	    	sqlvb19.put("firstpaydate", mLCPolSchema.getCValiDate());
	    	sqlvb19.put("getstartdate", mLCPolSchema.getCValiDate());
	    	sqlvb19.put("paytodate", mLCPolSchema.getEndDate());
	    	sqlvb19.put("payenddate", mLCPolSchema.getEndDate());
	    	sqlvb19.put("enddate", mLCPolSchema.getEndDate());
	    	sqlvb19.put("contno", mLCPolSchema.getContNo());
	    	
	    	SQLwithBindVariables sqlvb20 = new SQLwithBindVariables();
	    	sqlvb20.sql("update LCPrem set appntno='"+"?appntno?"+"',paystartdate= '"+"?paystartdate?"+"',paytodate='"+"?paytodate?"+"',payenddate='"+"?payenddate?"+"' where contno = '"+"?contno?"+"'");
	    	sqlvb20.put("appntno", tLCAppntSchema.getAppntNo());
	    	sqlvb20.put("paystartdate", mLCPolSchema.getCValiDate());
	    	sqlvb20.put("paytodate", mLCPolSchema.getEndDate());
	    	sqlvb20.put("payenddate", mLCPolSchema.getEndDate());
	    	sqlvb20.put("contno", mLCPolSchema.getContNo());
	        
	        map.put(sqlvb16, "UPDATE");
	        map.put(sqlvb17, "UPDATE");        
	        map.put(sqlvb18, "UPDATE");
	        map.put(sqlvb19, "UPDATE");
	        map.put(sqlvb20, "UPDATE");
	                               
	    	//保单部分
	        mLCPolSchema.setAppFlag("1");
	        mLCPolSchema.setExpiryFlag("");
	    	mLCPolSchema.setAppntNo(tLCAppntSchema.getAppntNo());
	    	mLCPolSchema.setInsuredNo(tLCInsuredSchema.getInsuredNo());
	    	mLCPolSchema.setModifyDate(PubFun.getCurrentDate());
	        mLCPolSchema.setModifyTime(PubFun.getCurrentTime());
    	}
        mLCContSchema.setAppFlag("1");
        mLCContSchema.setPaytoDate(mLCPolSchema.getEndDate());     	
    	mLCContSchema.setAppntNo(tLCAppntSchema.getAppntNo());
    	mLCContSchema.setInsuredNo(tLCInsuredSchema.getInsuredNo());
    	mLCContSchema.setModifyDate(PubFun.getCurrentDate());
    	mLCContSchema.setModifyTime(PubFun.getCurrentTime());   	
        
        map.put(mLCPolSet,"INSERT");
        LCContSchema aLCContSchema = new LCContSchema();
        aLCContSchema = mLCContSchema.getSchema();
        map.put(aLCContSchema, "DELETE&INSERT");
        
        SQLwithBindVariables sqlvb21 = new SQLwithBindVariables();
        sqlvb21.sql("delete from lccontstate where contno='"+"?contno?"+"' and statetype='Terminate' and statereason='01'");
        sqlvb21.put("contno", mLCPolSchema.getContNo());
//      对于还未激活就通过满期批处理程序置上保单终止状态的，激活时需要修改保单状态
		map.put(sqlvb21, "DELETE");
        
        tVData.clear();
    	tVData.add(map);
    	
    	return true;
    }

    
    
    /**
	 * 进行客户号合并,并准备ldperson的客户信息
	 * author:zz
	 */
    private boolean DealCustomerNo()
    {
	    if(mcnt==1){// 投保人客户号合并只作一次
	    	logger.debug("开始进行投,被保人的客户号合并");
	    	//得到客户号    	
	    	
	    	//处理投保人信sysdatesysdate息:进行客户号合并,如果是新客户,则返回值是空字符串
	    	String tAppntNo = "";
	    	tAppntNo = tContBL.getCustomerNo(tLCAppntSchema, tLCAddressSchema);
	    	tLCAppntSchema.setAppntNo(tAppntNo);
//*	    	String sql="select nvl(max(addressno/1),0) from lcaddress where customerno='"+"?customerno?"+"'";
	    	String sql="select (case when max(addressno/1) is not null then max(addressno/1) else 0 end) from lcaddress where customerno='"+"?customerno?"+"'";
	    	SQLwithBindVariables sqlvb22 = new SQLwithBindVariables();
	    	sqlvb22.sql(sql);
	    	sqlvb22.put("customerno", tAppntNo);
	    	String taddressno = mExeSQL.getOneValue(sqlvb22);
	    	tLCAppntSchema.setAddressNo(String.valueOf(Integer.parseInt(taddressno)+1));
			
	        //客户号合并，新增地址信息    
	    	tLCAddressSchema.setAddressNo(String.valueOf(Integer.parseInt(taddressno)+1));
	    	tLCAddressSchema.setCustomerNo(tAppntNo);
	    	tLCAddressSchema.setOperator(Auto_CERTIFY_Opertor);
			tLCAddressSchema.setMakeDate(PubFun.getCurrentDate());
			tLCAddressSchema.setMakeTime(PubFun.getCurrentTime());
			tLCAddressSchema.setModifyDate(PubFun.getCurrentDate());
			tLCAddressSchema.setModifyTime(PubFun.getCurrentTime());
			
	    	if(tLCAppntSchema.getAppntNo().equals(""))
	    	{
	    		//产生新的客户号
	    		tLCAppntSchema.setAppntNo(PubFun1.CreateMaxNo("CustomerNo", "SN"));
	    		tLCAppntSchema.setAddressNo("1");
	    		
	    		tLCAddressSchema.setCustomerNo(tLCAppntSchema.getAppntNo());
	    		tLCAddressSchema.setAddressNo("1");
	    		
	    		logger.debug("投保人客户号:"+tLCAppntSchema.getAppntNo());
	    		
	    		//当客户号为空时,表明是新客户,所以需要往客户信息表(ldperson)增加新的客户资料
	    		LDPersonSchema tLDPersonSchema=new LDPersonSchema();
	    		tLDPersonSchema.setCustomerNo(tLCAppntSchema.getAppntNo());
	    		tLDPersonSchema.setName(tLCAppntSchema.getAppntName());              //姓名 
	    		tLDPersonSchema.setSex(tLCAppntSchema.getAppntSex());                //性别
	    		tLDPersonSchema.setBirthday(tLCAppntSchema.getAppntBirthday());      //出生日期 
	    		tLDPersonSchema.setIDType(tLCAppntSchema.getIDType());          //证件类型
	    		tLDPersonSchema.setIDNo(tLCAppntSchema.getIDNo());              //证件号码     	
	    		tLDPersonSchema.setOccupationType(tLCAppntSchema.getOccupationType());       //职业类别
	    		tLDPersonSchema.setOccupationCode(tLCAppntSchema.getOccupationCode());       //职业代码
	    		tLDPersonSchema.setSalary("0.00");
	    		tLDPersonSchema.setStature("0.00");
	    		tLDPersonSchema.setAvoirdupois("0.00");
	    		tLDPersonSchema.setMakeDate(PubFun.getCurrentDate());
	    		tLDPersonSchema.setMakeTime(PubFun.getCurrentTime());
	    		tLDPersonSchema.setModifyDate(PubFun.getCurrentDate());
	    		tLDPersonSchema.setModifyTime(PubFun.getCurrentTime());
	    		tLDPersonSchema.setOperator(Auto_CERTIFY_Opertor);//自助卡单默认操作员
	    		
	    		//放到map中,准备插入数据库 
	    		LDPersonSchema aLDPersonSchema = new LDPersonSchema();
	    		aLDPersonSchema = tLDPersonSchema.getSchema();
	    		map.put(aLDPersonSchema,"INSERT");
	    	}
	    }
    	//处理被保人信息
    	if("00".equals(tLCAppntSchema.getRelationToInsured()))
    	{
    		tLCInsuredSchema.setInsuredNo(tLCAppntSchema.getAppntNo());
            //投被保人为同一人，共用地址信息
    		tLCInsuredSchema.setAddressNo(tLCAppntSchema.getAddressNo());
 		
    	}
    	else
    	{
    		String tInsuredNo = "";
    		tInsuredNo = tContInsuredBL.getCustomerNo(tLCInsuredSchema, tLCAddressSchema2);
    		tLCInsuredSchema.setInsuredNo(tInsuredNo);
//    	  	String sql1="select nvl(max(addressno/1),0) from lcaddress where customerno='"+"?customerno?"+"'";
    		String sql1="select (case when max(addressno/1) is not null then max(addressno/1) else 0 end) from lcaddress where customerno='"+"?customerno?"+"'";
    	  	SQLwithBindVariables sqlvb23 = new SQLwithBindVariables();
    	  	sqlvb23.sql(sql1);
    	  	sqlvb23.put("customerno", tInsuredNo);
        	String taddressno1 = mExeSQL.getOneValue(sqlvb23);
        	tLCInsuredSchema.setAddressNo(String.valueOf(Integer.parseInt(taddressno1)+1));//客户号合并，新增地址信息
        	
        	tLCAddressSchema2.setCustomerNo(tInsuredNo);
        	tLCAddressSchema2.setAddressNo(String.valueOf(Integer.parseInt(taddressno1)+1));
        	tLCAddressSchema2.setOperator(Auto_CERTIFY_Opertor);
    		tLCAddressSchema2.setMakeDate(PubFun.getCurrentDate());
    		tLCAddressSchema2.setMakeTime(PubFun.getCurrentTime());
    		tLCAddressSchema2.setModifyDate(PubFun.getCurrentDate());
    		tLCAddressSchema2.setModifyTime(PubFun.getCurrentTime());
        	
    		if(tLCInsuredSchema.getInsuredNo().equals(""))
        	{
        		//产生新的客户号
    			tLCInsuredSchema.setInsuredNo(PubFun1.CreateMaxNo("CustomerNo", "SN"));
    			tLCInsuredSchema.setAddressNo("1");   
    			
    			tLCAddressSchema2.setCustomerNo(tLCInsuredSchema.getInsuredNo());
        		tLCAddressSchema2.setAddressNo("1");
        		
        		//当客户号为空时,表明是新客户,所以需要往客户信息表(ldperson)增加新的客户资料
        		LDPersonSchema mLDPersonSchema=new LDPersonSchema();
        		mLDPersonSchema.setCustomerNo(tLCInsuredSchema.getInsuredNo());
        		mLDPersonSchema.setName(tLCInsuredSchema.getName());              //姓名 
        		mLDPersonSchema.setSex(tLCInsuredSchema.getSex());                //性别
        		mLDPersonSchema.setBirthday(tLCInsuredSchema.getBirthday());      //出生日期 
        		mLDPersonSchema.setIDType(tLCInsuredSchema.getIDType());          //证件类型
        		mLDPersonSchema.setIDNo(tLCInsuredSchema.getIDNo());              //证件号码 
        		mLDPersonSchema.setOccupationType(tLCInsuredSchema.getOccupationType());       //职业类别
        		mLDPersonSchema.setOccupationCode(tLCInsuredSchema.getOccupationCode());       //职业代码
        		mLDPersonSchema.setSalary("0.00");
        		mLDPersonSchema.setStature("0.00");
        		mLDPersonSchema.setAvoirdupois("0.00");
        		mLDPersonSchema.setMakeDate(PubFun.getCurrentDate());
        		mLDPersonSchema.setMakeTime(PubFun.getCurrentTime());
        		mLDPersonSchema.setModifyDate(PubFun.getCurrentDate());
        		mLDPersonSchema.setModifyTime(PubFun.getCurrentTime());
        		mLDPersonSchema.setOperator(Auto_CERTIFY_Opertor);//自助卡单默认操作员
        		
        		//放到map中,准备插入数据库 
        		LDPersonSchema aLDPersonSchema = new LDPersonSchema();
        		aLDPersonSchema = mLDPersonSchema.getSchema();
        		map.put(aLDPersonSchema,"INSERT");
        	}
    	}
    	
    	logger.debug("被保险保人客户号:"+tLCInsuredSchema.getInsuredNo());
    	
    	return true;
    }
    
    
    /**
	 * 准备保单表的数据
	 * author:zz
	 */
    private boolean PrepareLCPol()
    {
    	logger.debug("开始准备保单表,投被保人的数据");
    	
    	logger.debug("开始准备lcpol数据");
    	//合同部分
    	mLCContSchema.setCustomGetPolDate(PubFun.getCurrentDate());//激活日期
    	mLCContSchema.setGetPolDate(PubFun.getCurrentDate());
    	mLCContSchema.setCValiDate(tLCPolSchema.getCValiDate());
    	mLCContSchema.setAppntName(tLCAppntSchema.getAppntName());
    	mLCContSchema.setAppntSex(tLCAppntSchema.getAppntSex());
    	mLCContSchema.setAppntBirthday(tLCAppntSchema.getAppntBirthday());
    	mLCContSchema.setAppntIDType(tLCAppntSchema.getIDType());
    	mLCContSchema.setAppntIDNo(tLCAppntSchema.getIDNo());
    	mLCContSchema.setInsuredName(tLCInsuredSchema.getName());
    	mLCContSchema.setInsuredSex(tLCInsuredSchema.getSex());
    	mLCContSchema.setInsuredBirthday(tLCInsuredSchema.getBirthday());
    	mLCContSchema.setInsuredIDType(tLCInsuredSchema.getIDType());
    	mLCContSchema.setInsuredIDNo(tLCInsuredSchema.getIDNo());    
    	
    	//保单部分  
    	for(int pi = 1;pi <= mLCPolSet.size();pi++){
    		mLCPolSchema = mLCPolSet.get(pi);
	    	mLCPolSchema.setCValiDate(tLCPolSchema.getCValiDate());//生效日期
	    	mLCPolSchema.setStandbyFlag1(tLCPolSchema.getStandbyFlag1());//激活类型:电话/在线
	    	mLCPolSchema.setSpecifyValiDate(tLCPolSchema.getSpecifyValiDate());//是否指定生效日期标志
	        //如果是从电子商务网站在线激活则该字段不会有值,以此来判断激活方式
	        if(tLCPolSchema.getStandbyFlag1()==null||tLCPolSchema.getStandbyFlag1().equals(""))
	        {
	        	mLCPolSchema.setStandbyFlag1("在线");
	        }
	        //置责任终止日期
	        logger.debug("卡单"+mLCPolSchema.getContNo()+"的InsureYear： "+mLCPolSchema.getInsuYear());
	        logger.debug("卡单"+mLCPolSchema.getContNo()+"的InsureYearFlag： "+mLCPolSchema.getInsuYearFlag());
	        //从生效日期算起得到责任终止日期
	        String endDate=PubFun.newCalDate(mLCPolSchema.getCValiDate(),mLCPolSchema.getInsuYearFlag(),mLCPolSchema.getInsuYear());
	        logger.debug("卡单"+mLCPolSchema.getContNo()+"的EndDate： "+mLCPolSchema.getEndDate());
	        mLCPolSchema.setEndDate(endDate);
	        
	    	//保单中投保人信息部分
	        mLCPolSchema.setAppntName(tLCAppntSchema.getAppntName());//投保人姓名
	
	        //保单中被保人信息
	        mLCPolSchema.setInsuredName(tLCInsuredSchema.getName());//被保人姓名
	        mLCPolSchema.setInsuredBirthday(tLCInsuredSchema.getBirthday());//被保人生日
	        mLCPolSchema.setInsuredSex(tLCInsuredSchema.getSex());//被保人性别
	        mLCPolSchema.setInsuredPeoples(tLCPolSchema.getInsuredPeoples());//被保人人数
	        mLCPolSchema.setOccupationType(tLCInsuredSchema.getOccupationType());//被保人职业类别/工种编码
	        mLCPolSchema.setInsuredAppAge(PubFun.calInterval3(tLCInsuredSchema.getBirthday(),
	        		tLCPolSchema.getCValiDate(), "Y"));
    	}
    	return true;
    }

	/**
	 * 进行投保规则的校验
	 * author:zz
	 */
    private boolean CheckData()
    {
    	
    	//校验1:保单生效日期不能小于当前系统日期,且不能超过激活日期的30天;
    	logger.debug("卡号"+tLCPolSchema.getPrtNo()+"的生效日期:"+tLCPolSchema.getCValiDate());
    	logger.debug("卡号"+tLCPolSchema.getPrtNo()+"的激活日期:"+PubFun.getCurrentDate());
    	if(!(tLCPolSchema.getCValiDate()==null||tLCPolSchema.getCValiDate().equals("")))
    	{
    		//指定的生效日期不能小于系统日期
    		if(!PubFun.checkDate(PubFun.getCurrentDate(),tLCPolSchema.getCValiDate()))
    		{
    			mResult.add(Fail);
    	    	mResult.add("指定的生效日期不能早于激活日期!");
    	    	return false;
    		}
    		
    		//指定的生效日期不能超过激活日期的30天
    		String CvaliEndDate=PubFun.newCalDate(PubFun.getCurrentDate(), "D", 30);
    		logger.debug("生效截止日期:"+CvaliEndDate);
    		if(!PubFun.checkDate(tLCPolSchema.getCValiDate(),CvaliEndDate))
    		{
    			mResult.add(Fail);
    	    	mResult.add("指定的生效日期不能晚于激活日期的30天!");
    	    	return false;
    		}
    		//指定生效日期,则
    		tLCPolSchema.setSpecifyValiDate("Y");
    	}
    	else
    	{
    		//如果没指定生效日期,则默认生效日期是激活日期的第2天
    		tLCPolSchema.setCValiDate(PubFun.newCalDate(PubFun.getCurrentDate(), "D", 1));
    		tLCPolSchema.setSpecifyValiDate("N");
    		logger.debug("默认的生效日期:"+tLCPolSchema.getCValiDate());
    	}
    	
    	
    	//得到险种定义描述
    	LMRiskAppSchema mLMRiskAppSchema=new LMRiskAppSchema();
    	LMRiskAppDB mLMRiskAppDB=new LMRiskAppDB();
    	mLMRiskAppDB.setRiskCode(mLCPolSchema.getRiskCode());
    	if(!mLMRiskAppDB.getInfo())
    	{
    		mResult.add(Fail);
	    	mResult.add("查询险种"+mLCPolSchema.getRiskCode()+"的描述信息错误!");
	    	return false;
    	}
    	mLMRiskAppSchema.setSchema(mLMRiskAppDB.getSchema());
    	
    	
    	
    	//校验2:校验被保人的年龄必须符合险种定义要求   
        logger.debug("被保险人出生日期:"+tLCInsuredSchema.getBirthday());
        logger.debug("指定的生效日期:"+tLCPolSchema.getCValiDate());
        
        int tLCInsuredAge = 0;
        tLCInsuredAge = PubFun.calInterval3(tLCInsuredSchema.getBirthday(),
        		tLCPolSchema.getCValiDate(), "Y");
        logger.debug("被保险人年龄:" + tLCInsuredAge);

        //如果生效日期当天恰好过生日,则按照过生日后的年龄进行投保年龄校验，只要符合投保年龄要求,准许出单
        logger.debug("tLCInsuredSchema.getBirthday().substring(5):"+tLCInsuredSchema.getBirthday().substring(5));
        logger.debug("tLCPolSchema.getCValiDate().substring(5):"+tLCPolSchema.getCValiDate().substring(5));
        if(tLCInsuredSchema.getBirthday().substring(5).equals(tLCPolSchema.getCValiDate().substring(5)))
        {
        	//逻辑上把被保人的生日往前推一天,相当于生效日的前一天过生日
        	String calDate=PubFun.newCalDate(tLCInsuredSchema.getBirthday(), "D", -1);
        	tLCInsuredAge = PubFun.calInterval3(calDate,tLCPolSchema.getCValiDate(), "Y");
        	logger.debug("被保险人年龄2:" + tLCInsuredAge);
        }
//
//        
//        logger.debug("险种"+mLMRiskAppSchema.getRiskCode()+"的最小被保人年龄:"+mLMRiskAppSchema.getMinInsuredAge());
//        if ((mLMRiskAppSchema.getMaxInsuredAge()>0)&&(tLCInsuredAge < mLMRiskAppSchema.getMinInsuredAge()))
//        {
//        	mResult.add(Fail);
//	    	mResult.add("被保险人年龄不能小于"+mLMRiskAppSchema.getMinInsuredAge()+"岁!");
//	    	return false;
//        }
//        
//        
//        logger.debug("险种"+mLMRiskAppSchema.getRiskCode()+"的最大被保人年龄:"+mLMRiskAppSchema.getMaxInsuredAge());
//        
//        if((mLMRiskAppSchema.getMaxInsuredAge()>0)&&(tLCInsuredAge > mLMRiskAppSchema.getMaxInsuredAge()))
//        {
//           	mResult.add(Fail);
//    	    mResult.add("被保险人年龄不能大于"+mLMRiskAppSchema.getMaxInsuredAge()+"岁!");
//    	    return false;
//        }
    	if(!checkLMRiskRole("00")) //校验投保人
    	{
    		return false;
    	}
    	if(!checkLMRiskRole("01")) //校验被保人
    	{
    		return false;
    	}         
        if("old".equals(mSelfFlag)){
	        //校验3:如果被保险人年龄>=18岁,则被保人必须是投保人本人
	        logger.debug("险种"+mLMRiskAppSchema.getRiskCode()+"的最小投保人年龄:"+mLMRiskAppSchema.getMinAppntAge());
	        if ((tLCInsuredAge >= 18)&&(!"00".equals(tLCAppntSchema.getRelationToInsured())))
	        {
	        	mResult.add(Fail);
		    	mResult.add("被保险人年龄大于等于18周岁,被保险人必须是投保人本人!");
		    	return false;
	        }
	        logger.debug("校验3:如果被保险人年龄>=18岁,则被保人必须是投保人本人结束!");
	        
	        
	        //校验4:如果被保险人年龄<18岁,则投保人必须是被保险人的父母
	        logger.debug("tLCAppntIndSchema.getRelationToInsured():"+tLCAppntSchema.getRelationToInsured());
	        if ((tLCInsuredAge < 18)&&("00".equals(tLCAppntSchema.getRelationToInsured())))
	        {
	        	mResult.add(Fail);
		    	mResult.add("被保险人年龄小于18周岁,投保人必须为被保险人父母!");
		    	return false;
	        }
	        logger.debug("校验4:如果被保险人年龄<18岁,则投保人必须是被保险人的父母结束!");
        }
        
        //更新保单表中被保人的投保年龄
        mLCPolSchema.setInsuredAppAge(tLCInsuredAge);
        

        //校验5:对投保人的证件类型是身份证时进行生日、性别、证件联合校验
        logger.debug("投保人的证件类型:"+tLCAppntSchema.getIDType().trim());
        logger.debug("投保人的证件号码:"+tLCAppntSchema.getIDNo().trim());
        if(tLCAppntSchema.getIDType().trim()!=null&&tLCAppntSchema.getIDType().trim().equals("0"))
        {
        	if(!CheckIDBirthdaySex(tLCAppntSchema.getAppntBirthday(),tLCAppntSchema.getAppntSex(),tLCAppntSchema.getIDNo(),"0"))
        	{
        		return false;
        	}
        }        
        
        //校验6:对被保险人的证件类型是身份证时进行生日、性别、证件联合校验
        logger.debug("被保险人的证件类型:"+tLCInsuredSchema.getIDType().trim());
        logger.debug("被保险人的证件号码:"+tLCInsuredSchema.getIDNo().trim());
        //因为当投被保人是同一人的时候,信息是一样的,所以只有当与被保人关系不是本人的时候才进行校验
        if(!("00".equals(tLCAppntSchema.getRelationToInsured()))&&tLCInsuredSchema.getIDType().trim()!=null&&tLCInsuredSchema.getIDType().trim().equals("0"))
        {
        	if(!CheckIDBirthdaySex(tLCInsuredSchema.getBirthday(),tLCInsuredSchema.getSex(),tLCInsuredSchema.getIDNo(),"1"))
        	{
        		return false;
        	}
        }        
        
        //校验7:被保人邮编只能是6位数字//不校验投保人邮编
//		if (tLCAddressSchema.getZipCode().trim().length() != 6) 
//		{
//			mResult.add(Fail);
//		    mResult.add("投保人邮编信息应为6位阿拉伯数字!");
//			return false;
//		}
		
		if (tLCAddressSchema2.getZipCode().trim().length() != 6) 
		{
			mResult.add(Fail);
		    mResult.add("被保人邮编信息应为6位阿拉伯数字!");
			return false;
		}
			
//		if (PubFun.isNumeric(tLCAddressSchema.getZipCode().trim())==false)
//		{
//			mResult.add(Fail);
//		    mResult.add("投保人邮编信息应为6位阿拉伯数字!");
//			return false;
//		}
		
		if (PubFun.isNumeric(tLCAddressSchema2.getZipCode().trim())==false)
		{
			mResult.add(Fail);
		    mResult.add("被保人邮编信息应为6位阿拉伯数字!");
			return false;
		}
		
		//校验8:如果投保人与被保人的关系是父母,则姓名,性别,出生日期不能全部相同,否则认定是同一人
		if(!"00".equals(tLCAppntSchema.getRelationToInsured()))
		{
			if(tLCAppntSchema.getAppntName().trim().equals(tLCInsuredSchema.getName().trim())&&tLCAppntSchema.getAppntSex().trim().equals(tLCInsuredSchema.getSex().trim())&&tLCAppntSchema.getAppntBirthday().equals(tLCInsuredSchema.getBirthday()))
			{
				mResult.add(Fail);
		    	mResult.add("当与被保人关系为父母时,被保人信息不能与投保人相同!");
			    return false; 
			}
		}
		
        
        LMCheckFieldSchema tLMCheckFieldSchema = new LMCheckFieldSchema();
        tLMCheckFieldSchema.setRiskCode(mLCPolSchema.getRiskCode());
        tLMCheckFieldSchema.setRiskVer(mLCPolSchema.getRiskVersion());
        tLMCheckFieldSchema.setFieldName("TBINSERT");
        
        logger.debug("Check 卡号:"+mLCPolSchema.getContNo()+"的职业类别:"+tLCInsuredSchema.getOccupationType());
        logger.debug("Check 卡号:"+mLCPolSchema.getContNo()+"的险种编码:"+mLCPolSchema.getRiskCode());
        logger.debug("Check 卡号:"+mLCPolSchema.getContNo()+"的生效日期:"+tLCPolSchema.getCValiDate());
        
        String pInsuredNo = tContInsuredBL.getCustomerNo(tLCInsuredSchema, tLCAddressSchema2);
//        String pInsuredNo=mLCPolSchema.getInsuredNo();
        logger.debug("Check 卡号:"+mLCPolSchema.getContNo()+"的客户号:"+pInsuredNo);
        
        VData aVData=new VData();
        TransferData tTransferData = new TransferData();
        tTransferData.setNameAndValue("OccupationType",tLCInsuredSchema.getOccupationType());
        tTransferData.setNameAndValue("RiskCode", mLCPolSchema.getRiskCode());
        tTransferData.setNameAndValue("CValiDate", tLCPolSchema.getCValiDate());
        tTransferData.setNameAndValue("InsuredNo", pInsuredNo);
        aVData.add(tTransferData);
        aVData.add(tLMCheckFieldSchema);
        
        PubCheckField tPubCheckField = new PubCheckField();
        if (!tPubCheckField.submitData(aVData, "CKBYFIELD"))
        {
        	mResult.add(Fail);
	    	mResult.add((String)tPubCheckField.getResultMess().getObject(0));
            return false;
        }        
        
//        //校验10：必须是第一次激活，否则不能重复激活  
//        ??2009-02-05 前面已经校验过了，此处不必重复校验  	
//     	String ttCheckSql="select CustomGetPolDate from lccont where contno='"+inputNo+"' ";
//    	logger.debug("校验卡单"+inputNo+"是否已经激活的sql:"+ttCheckSql);
//    	ExeSQL ttCheckExeSQL=new ExeSQL();
//    	SSRS ttCheckSSRS = ttCheckExeSQL.execSQL(ttCheckSql);
//    	
//    	if(ttCheckSSRS.getMaxRow()>0)
//    	{
//    		logger.debug("从数据库中查询的卡单"+inputNo+"的CustomGetPolDate:"+ttCheckSSRS.GetText(1,1));
//    		if(!(ttCheckSSRS.GetText(1,1)==null||ttCheckSSRS.GetText(1,1).equals("")))
//    		{
//    			logger.debug(":卡号"+inputNo+"的卡式保单已经激活，每张卡只能激活一次");
//				mResult.add(Fail);
//	    	    mResult.add("对不起，您的卡式保单已经激活，每张卡只能激活一次!");
//	    	    return false;
//    		}
//    	}        
//        校验11：承保规则校验
        if (!CheckTBField("INSERT"))
        {
        	mResult.add(Fail);
	    	return false;
        }        
        logger.debug("激活校验结束!");

    	return true;
    }
    
	/***************************************************************************
	 * 角色表LMRiskRole表校验方法
	 * 校验投被保人年龄、性别是否符合该险种的要求
	 */
	private boolean checkLMRiskRole(String aRiskRole) {
		LMRiskRoleDB aLMRiskRoleDB = new LMRiskRoleDB();
		LMRiskRoleSet aLMRiskRoleSet = new LMRiskRoleSet();
		aLMRiskRoleDB.setRiskCode(mLCPolSchema.getRiskCode());
		aLMRiskRoleDB.setRiskRole(aRiskRole);
		aLMRiskRoleSet = aLMRiskRoleDB.query();

		boolean appntSex = false; // 投保人性别校验是否通过
		boolean maxAppntAge = false; // 投保人年龄校验是否通过
		boolean minAppntAge = false; // 投保人年龄校验是否通过
		boolean insuSex = false; // 被保人性别校验是否通过
		boolean maxInsuAge = false; // 被保人年龄校验是否通过
		boolean minInsuAge = false; // 被保人年龄校验是否通过


		if (aRiskRole.equals("00")) {
			if (aLMRiskRoleSet.size() > 0) {
				for (int i = 0; i < aLMRiskRoleSet.size(); i++) {
					// 判断性别
					if (aLMRiskRoleSet.get(i + 1).getRiskRoleSex().equals("2")
							|| tLCAppntSchema.getAppntSex().equals(
									aLMRiskRoleSet.get(i + 1).getRiskRoleSex())) {
						appntSex = true;
					}
					int maxAppntAgeIntv = PubFun
							.calInterval(tLCAppntSchema.getAppntBirthday(),
									tLCPolSchema.getCValiDate(), aLMRiskRoleSet
											.get(i + 1).getMAXAppAgeFlag()
											.trim());
					int minAppntAgeIntv = PubFun
							.calInterval(tLCAppntSchema.getAppntBirthday(),
									tLCPolSchema.getCValiDate(), aLMRiskRoleSet
											.get(i + 1).getMinAppAgeFlag()
											.trim());
					logger.debug("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
					logger.debug("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
					logger.debug("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
					logger.debug("minAgeIntv==" + minAppntAgeIntv);
					logger.debug("maxAgeIntv==" + maxAppntAgeIntv);
					// 判断年龄
					if (minAppntAgeIntv >= aLMRiskRoleSet.get(i + 1)
							.getMinAppAge()) {
						minAppntAge = true;
					}
					if (maxAppntAgeIntv <= aLMRiskRoleSet.get(i + 1)
							.getMAXAppAge()) {
						maxAppntAge = true;
					}

				}
			} else {
				appntSex = true; // 投保人性别校验是否通过
				maxAppntAge = true; // 投保人年龄校验是否通过
				minAppntAge = true; // 投保人年龄校验是否通过

			}

			// 校验完毕进行判断是否通过角色校验
			logger.debug("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
			logger.debug("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
			logger.debug("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
			logger.debug("appntSex==" + appntSex);
			logger.debug("maxAppntAge==" + maxAppntAge);
			logger.debug("minAppntAge==" + minAppntAge);

			if (!appntSex) {
				// @@错误处理
				mResult.add(Fail);
	    	    mResult.add("投保人性别不符合投保规则要求!");
				return false;
			}
			if (!maxAppntAge) {
				// @@错误处理
				mResult.add(Fail);
	    	    mResult.add("投保人年龄不符合最大投保年龄要求!");				
				return false;
			}
			if (!minAppntAge) {
				// @@错误处理
				mResult.add(Fail);
	    	    mResult.add("投保人年龄不符合最小投保年龄要求!");				
				return false;
			}

		}
		if (aRiskRole.equals("01")) {

			if (aLMRiskRoleSet.size() > 0) {
				for (int i = 0; i < aLMRiskRoleSet.size(); i++) {
					// 判断性别
					if ((aLMRiskRoleSet.get(i + 1).getRiskRoleSex().equals("2"))
							|| (tLCInsuredSchema.getSex().equals(aLMRiskRoleSet
									.get(i + 1).getRiskRoleSex()))) {
						insuSex = true;
					}
					logger.debug("===="
							+ aLMRiskRoleSet.get(i + 1).getMAXAppAgeFlag());
					logger.debug("===="
							+ aLMRiskRoleSet.get(i + 1).getMinAppAgeFlag()
									.trim());
					int maxAgeIntv = PubFun
							.calInterval(tLCInsuredSchema.getBirthday(), tLCPolSchema
									.getCValiDate(), aLMRiskRoleSet.get(i + 1)
									.getMAXAppAgeFlag().trim());
					int minAgeIntv = PubFun
							.calInterval(tLCInsuredSchema.getBirthday(), tLCPolSchema
									.getCValiDate(), aLMRiskRoleSet.get(i + 1)
									.getMinAppAgeFlag().trim());
					logger.debug("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
					logger.debug("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
					logger.debug("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
					logger.debug("minAgeIntv==" + minAgeIntv);
					logger.debug("maxAgeIntv==" + maxAgeIntv);
					logger.debug("mLCInsuredBL.getBirthday()=="
							+ tLCInsuredSchema.getBirthday());

					// 判断年龄
					if (minAgeIntv >= aLMRiskRoleSet.get(i + 1).getMinAppAge()) {
						minInsuAge = true;
					}
					if (maxAgeIntv <= aLMRiskRoleSet.get(i + 1).getMAXAppAge()) {
						maxInsuAge = true;
					}
				}
			} else {
				insuSex = true; // 被保人性别校验是否通过
				maxInsuAge = true; // 被保人年龄校验是否通过
				minInsuAge = true; // 被保人年龄校验是否通过
			}
			// 校验完毕进行判断是否通过角色校验
			logger.debug("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
			logger.debug("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
			logger.debug("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
			logger.debug("insuSex==" + insuSex);
			logger.debug("maxInsuAge==" + maxInsuAge);
			logger.debug("minInsuAge==" + minInsuAge);

			if (!insuSex) {
				// @@错误处理
				mResult.add(Fail);
	    	    mResult.add("被保人性别不符合投保规则要求!");						
				return false;
			}
			if (!maxInsuAge) {
				// @@错误处理
				mResult.add(Fail);
	    	    mResult.add("被保人年龄不符合最大投保年龄要求!");
				return false;
			}
			if (!minInsuAge) {
				// @@错误处理
				mResult.add(Fail);
	    	    mResult.add("被保人年龄不符合最小投保年龄要求!");				
				return false;
			}
		}
		return true;

	}
    
    
    /**
	 * 对投、被保人的证件类型是身份证时进行生日、性别、证件联合校验
	 * 输入:三项:出生日期,性别,证件号码,投,被保人标志(投保人:0,被保人:1)
	 * 输出:true,false
	 * author:zz
	 */
    public boolean CheckIDBirthdaySex(String tBirthday,String tSex,String tIdNo,String flag)
    {
    	String tmpbirthday = "";
		String tmpsex = "";
		FDate tFDate = new FDate();
		if (tIdNo.trim().length() == 18) 
		{
			tmpbirthday = tIdNo.substring(6, 14);
			tmpsex = tIdNo.substring(16, 17);		
		} 
		else if (tIdNo.trim().length() == 15) 
		{
			tmpbirthday = tIdNo.substring(6, 12);
			tmpbirthday = "19" + tmpbirthday;			
			tmpsex = tIdNo.substring(14, 15);			
		}
		else 
		{
			logger.debug("待校验的身份证号码位数错误!");
			mResult.add(Fail);
			if(flag.equals("0"))
			{
				mResult.add("投保人身份证号码位数错误!");
			}
			else
			{
				mResult.add("被保险人身份证号码位数错误!");
			}	    	
			return false;
		}
		logger.debug("校验人的身份证号中的出生日期: "+tmpbirthday);			
		logger.debug("待校验的身份证号中的性别: "+tmpsex);

		if (tBirthday== null || tBirthday.equals("")) 
		{
			logger.debug("投保人出生日期格式不对!");
			mResult.add(Fail);
			if(flag.equals("0"))
			{
				mResult.add("投保人出生日期格式有误!");
			}
			else
			{
				mResult.add("被保险人出生日期格式有误!");
			}
			return false;
		}
		
		logger.debug("输入的待校验的出生日期:"+tBirthday);
		logger.debug("tmpbirthday:"+tmpbirthday);
		if (tFDate.getDate(tBirthday).compareTo(tFDate.getDate(tmpbirthday)) != 0) 
		{
			logger.debug("校验人的身份证号与出生日期不符!");
			mResult.add(Fail);
			if(flag.equals("0"))
			{
				mResult.add("投保人身份证号与出生日期不符!");
			}
			else
			{
				mResult.add("被保险人身份证号与出生日期不符!");
			}
			return false;
		}

		logger.debug("输入的待校验的性别:"+tSex);
		if (tmpsex.equals("0") || tmpsex.equals("2") || tmpsex.equals("4")
				|| tmpsex.equals("6") || tmpsex.equals("8")) 
		{
			if (tSex == null || tSex.trim().equals("0")) 
			{
				mResult.add(Fail);
				if(flag.equals("0"))
				{
					mResult.add("投保人性别与证件号码校验有误!");
				}
				else
				{
					mResult.add("被保险人性别与证件号码校验有误!");
				}
				return false;
			}
		} 
		else 
		{
			if (tSex == null || tSex.trim().equals("1")) 
			{
				mResult.add(Fail);
				if(flag.equals("0"))
				{
					mResult.add("投保人性别与证件号码校验有误!");
				}
				else
				{
					mResult.add("被保险人性别与证件号码校验有误!");
				}
				return false;
			}
		}
        return true;	
    }
	
    

	/**
	 * 校验卡号，密码是否正确,是否已经激活,是否已经过了激活的有效期
	 * 输入:两项:卡号,密码
	 * 输出:三项:通过标志(1:通过;0:不通过)，返回信息,险种编码
	 * author:zz
	 */
    public boolean CheckActivate(Vector inParam)
    {
    	mResult.clear();
    	logger.debug("************开始进行激活的校验**********");
    	inputNo=(String)inParam.get(1);
    	inputPassword=(String)inParam.get(2);
    	logger.debug("本次需要进行激活校验的卡号:"+inputNo);
    	logger.debug("本次需要进行激活校验的密码:"+inputPassword);
    	
    	//校验1:卡号不能为空
    	if(inputNo==null||inputNo.equals(""))
    	{
    		//@@错误处理
    	    logger.debug("卡号为空!");	    
    	    mResult.add(Fail);
    	    mResult.add("对不起，您输入的卡号位数不正确，请重新输入!");
    	    return false;
    	}
    	
    	logger.debug("***************校验卡号不能为空结束!*********\n");
    	
    	
    	//校验2:卡号长度必须是20
    	logger.debug("卡号的长度:"+inputNo.length());
    	if(inputNo.length()!=20)
    	{
    		//@@错误处理
    	    logger.debug("卡号长度不是20位!");  	    
    	    mResult.add(Fail);
    	    mResult.add("对不起，您输入的卡号位数不正确，请重新输入!");
    	    return false;
    	}
    	
    	
    	logger.debug("***************校验卡号长度结束!*********\n");
    	
    	
    	//校验3：激活日期必须截止日期前,如果超过截止日期,不能激活
    	boolean checkflag=true;//默认通过校验

    	String activateDateTime=PubFun.getCurrentDate()+" "+PubFun.getCurrentTime();
   	    logger.debug("*****激活时间:"+activateDateTime); 
   	    
   	    //获得指定年月的最后一天,由于卡号上只印刷截止年份的最后两位,而前两位是去当前日期的头两位,这样的处理方式在不跨千年(1999-2000)的时候没有问题,当跨千年的时候则会出现组合出的截止日期错误,如希望是3001底截止,但是拼出的日期会是2001,后面的开发人员要注意!
   	    String endYear=PubFun.getCurrentDate().substring(0,2)+inputNo.substring(8,10);
   	    String endMonth=inputNo.substring(10,12);
   	    int endDay=PubFun.getActualMaximum(Integer.parseInt(endYear),Integer.parseInt(endMonth));
   	    
   	    logger.debug("激活截止年:"+endYear);
   	    logger.debug("激活截止月:"+endMonth);
   	    logger.debug("激活截止日:"+endDay);

    	String endDateTime=endYear+"-"+endMonth+"-"+endDay+" "+"24:00:00";
    	logger.debug("*****截止时间:"+endDateTime);
    	
    	
    	boolean flag;
		try 
		{
			//如果截止日期<激活日期,则返回true
			flag = PubFun.isDateBefore(endDateTime,activateDateTime);
			logger.debug("日期比较标志:"+flag);
			
			if(flag==true)
			{
				checkflag=false;
			}
		} 
		catch (java.text.ParseException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.debug("日期比较出现异常!");
			checkflag=false;
		}
		finally
		{
			if(checkflag==false)
			{
				//@@错误处理
	    	    logger.debug("卡号"+inputNo+"已经错过激活日期");
	    	    mResult.add(Fail);
	    	    mResult.add("对不起，您的卡式保单已经超过激活时间，不能进行激活！如有疑问，请拨打95596进行咨询!");
	    	    return false;
			}
		}
    	
    	logger.debug("***************校验激活截止日期结束*********");
    	
    	
    	//校验4:卡号必须能够在单证系统中查到
    	String sql="select 1 from lzcard where certifycode='"+"?certifycode?"+"' "
    		      +" and startno='"+"?startno?"+"' and endno='"+"?endno?"+"'"
    		      // modify by duanyh 2009-05-18 有可能单证核销后该单仍处于未激活状态，需查lzcardb表
    		      +" union"
    		      +" select 1 from lzcardb where certifycode='"+"?certifycode?"+"' "
    		      +" and startno <= '"+"?startno?"+"' and endno >= '"+"?endno?"+"'";
    	logger.debug("校验卡号是否在单证系统中的sql:"+sql);    	
   
    	SQLwithBindVariables sqlvb24 = new SQLwithBindVariables();
    	sqlvb24.sql(sql);
    	sqlvb24.put("certifycode", GetCertifyType(inputNo));
    	sqlvb24.put("startno", inputNo);
    	sqlvb24.put("endno", inputNo);
//*    	sqlvb24.put("certifycode", GetCertifyType(inputNo));
    	SSRS tSSRS=new SSRS();
        tSSRS=mExeSQL.execSQL(sqlvb24);
        logger.debug("校验4:返回结果:"+tSSRS.getMaxRow());
        if(tSSRS.getMaxRow()==0)
        {
        	//@@错误处理
    	    logger.debug("卡号"+"在单证系统中查询不到,录入错误！");
    	    mResult.add(Fail);
    	    mResult.add("对不起，您输入的卡号不存在！请核对您的卡号是否正确，如有疑问，请拨打95596进行咨询！");
    	    return false;
        }
        
        logger.debug("***************校验卡号是否在单证系统中结束!*********\n");
        
        
        //校验5:该卡单必须已经交过费了,如果未缴费则不能激活
        sql="select 1 from ljtempfee where otherno='"+"?otherno?"+"' and enteraccdate is not null";
        logger.debug("校验卡单是否已经交过费或是否到帐的sql:"+sql);

        SQLwithBindVariables sqlvb25 = new SQLwithBindVariables();
        sqlvb25.sql(sql);
        sqlvb25.put("otherno", inputNo);
        tSSRS=mExeSQL.execSQL(sqlvb25);
        logger.debug("校验5:返回结果:"+tSSRS.getMaxRow());
        if(tSSRS.getMaxRow()==0)
        {
        	//@@错误处理
        	logger.debug("卡号"+"必须先缴费或等缴费到帐后才能激活！");
        	mResult.add(Fail);
        	mResult.add("对不起，您输入的卡号还没有缴费或款项还没有到帐,请先缴费或等款项到帐后再进行激活！");
        	return false;
        }
  
        logger.debug("***************校验卡单必须已经缴费中结束!*********\n");
        
           
        //校验6：卡号密码必须匹配，否则不能激活
        checkflag=true;
    	DESPlus tDESPlus;
		try 
		{
			tDESPlus = new DESPlus();
			String newPassword="";

			//如果生成的密码为空,则重新计算,直到生成了一个密码为止
			newPassword=tDESPlus.getEncryptKey(inputNo);

			logger.debug("根据卡号重新计算生成的密码:"+newPassword);
			logger.debug("录入的密码:"+inputPassword);
			
			if(!newPassword.equals(inputPassword))
			{
				checkflag=false;
				logger.debug("卡号"+inputNo+"的自助卡单录入密码错误");
			}
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			checkflag=false;
			logger.debug("校验卡号和密码匹配时出现异常!");
		}
		finally
		{
			if(checkflag==false)
			{
				logger.debug("Finallly:卡号"+inputNo+"的自助卡单录入密码错误");
				mResult.add(Fail);
	    	    mResult.add("对不起，您输入的密码与卡号不符，请核对卡号与密码后重新输入!");
	    	    return false;
			}
		}
		
		
		logger.debug("***************校验卡号和密码匹配结束*********\n");
		
		
		//校验7-1||校验7-2
		if (strAct.equals("Check"))
        {
			//原有自助卡单校验，即非家庭单校验
			if(!isPortfolio(inputNo))
			{
				//校验7-1：必须是第一次激活，否则不能重复激活
		     	sql="select a.CustomGetPolDate, b.riskcode from lccont a, lcpol b where a.contno='"+"?contno?"+"' and a.contno = b.contno ";
		    	logger.debug("校验卡单"+inputNo+"是否已经激活的sql:"+sql);
		    	
		    	SQLwithBindVariables sqlvb26 = new SQLwithBindVariables();
		    	sqlvb26.sql(sql);
		    	sqlvb26.put("contno", inputNo);
		    	tSSRS = mExeSQL.execSQL(sqlvb26);
		    	if(tSSRS.getMaxRow()>0)
		    	{
		    		logger.debug("从数据库中查询的卡单"+inputNo+"的CustomGetPolDate:"+tSSRS.GetText(1,1));	    		
		    		if(!(tSSRS.GetText(1,1)==null||tSSRS.GetText(1,1).equals("")))
		    		{
		    			logger.debug(":卡号"+inputNo+"的卡式保单已经激活，每张卡只能激活一次");
						mResult.add(Fail);
			    	    mResult.add("对不起，您的卡式保单已经激活，每张卡只能激活一次!");
			    	    return false;
		    		}
		    	}
		    	//如果查询不到结果,则表示该保单没有核销,所以需要查询lmcardRisk(定额单险种描述表)查询险种代码
		    	else
		    	{
		    		sql="select certifycode,riskcode from lmcardrisk where certifycode='"+"?certifycode?"+"' ";
			    	logger.debug("从lmcardRisk中查询卡单"+inputNo+"对应的险种编码的Sql:"+sql);
			    	
			    	SQLwithBindVariables sqlvb27 = new SQLwithBindVariables();
			    	sqlvb27.sql(sql);
			    	sqlvb27.put("certifycode", GetCertifyType(inputNo));
			    	tSSRS = mExeSQL.execSQL(sqlvb27);
			    	if(tSSRS.getMaxRow()==0)
			    	{
			    		//查询不到记录,表示描述有问题,报错返回
			    		logger.debug(":卡号"+inputNo+"缺乏相应的险种描述!");
						mResult.add(Fail);
			    	    mResult.add("对不起，系统出现异常,请拨打95596查询原因!");
			    	    return false;
			    	}
		    	}
		    	
	    		//通过卡单和密码校验
	        	mResult.add(Success);
	        	mResult.add("卡号"+inputNo+"的卡式保单通过卡号和密码正确!"+"||"+tSSRS.GetText(1,2));
			}
			else
			{
				//校验7-1：必须是第一次激活，否则不能重复激活
		     	sql="select a.CustomGetPolDate, b.riskcode from lccont a, lcpol b where a.familyid='"+"?familyid?"+"' and a.contno = b.contno  and CustomGetPolDate is not null ";
		    	logger.debug("校验卡单"+inputNo+"是否已经激活的sql:"+sql);
		    	
		    	SQLwithBindVariables sqlvb28 = new SQLwithBindVariables();
		    	sqlvb28.sql(sql);
		    	sqlvb28.put("familyid", inputNo);
		    	tSSRS = mExeSQL.execSQL(sqlvb28);
		    	if(tSSRS.getMaxRow()>0)
		    	{
		    		logger.debug("从数据库中查询的卡单"+inputNo+"的CustomGetPolDate:"+tSSRS.GetText(1,1));	    		
		    		if(!(tSSRS.GetText(1,1)==null||tSSRS.GetText(1,1).equals("")))
		    		{
		    			logger.debug(":卡号"+inputNo+"的卡式保单已经激活，每张卡只能激活一次");
						mResult.add(Fail);
			    	    mResult.add("对不起，您的卡式保单已经激活，每张卡只能激活一次!");
			    	    return false;
		    		}
		    		else
		    		{

			    		sql="select certifycode,riskcode from lmcardrisk where certifycode='"+"?certifycode?"+"' ";
				    	logger.debug("从lmcardRisk中查询卡单"+inputNo+"对应的险种编码的Sql:"+sql);
				    	
				    	SQLwithBindVariables sqlvb29 = new SQLwithBindVariables();
				    	sqlvb29.sql(sql);
				    	sqlvb29.put("certifycode", GetCertifyType(inputNo));
				    	tSSRS = mExeSQL.execSQL(sqlvb29);
				    	if(tSSRS.getMaxRow()==0)
				    	{
				    		//查询不到记录,表示描述有问题,报错返回
				    		logger.debug(":卡号"+inputNo+"缺乏相应的险种描述!");
							mResult.add(Fail);
				    	    mResult.add("对不起，系统出现异常,请拨打95596查询原因!");
				    	    return false;
				    	}
			    	
		    		}
		    	}
		    	//如果查询不到结果,则表示该保单没有核销,所以需要查询lmcardRisk(定额单险种描述表)查询险种代码
		    	else
		    	{
		    		sql="select certifycode,riskcode from lmcardrisk where certifycode='"+"?certifycode?"+"' ";
			    	logger.debug("从lmcardRisk中查询卡单"+inputNo+"对应的险种编码的Sql:"+sql);
			    	
			    	SQLwithBindVariables sqlvb30 = new SQLwithBindVariables();
			    	sqlvb30.sql(sql);
			    	sqlvb30.put("certifycode", GetCertifyType(inputNo));
			    	tSSRS = mExeSQL.execSQL(sqlvb30);
			    	if(tSSRS.getMaxRow()==0)
			    	{
			    		//查询不到记录,表示描述有问题,报错返回
			    		logger.debug(":卡号"+inputNo+"缺乏相应的险种描述!");
						mResult.add(Fail);
			    	    mResult.add("对不起，系统出现异常,请拨打95596查询原因!");
			    	    return false;
			    	}
		    	}
		    	
	    		//通过卡单和密码校验
	        	mResult.add(Success);
	        	mResult.add("卡号"+inputNo+"的卡式保单通过卡号和密码正确!"+"||"+tSSRS.GetText(1,2));
			}
        }
		else
		{
			//原有自助卡单校验，即非家庭单校验
			if(!isPortfolio(inputNo))
			{
				//校验7-2：查询接口时要增加对未激活卡单的校验,如果该卡单还未激活,则提示卡尚未激活，提示信息为“对不起，您的卡式保单尚未激活，请在有效期XXXXX内进行激活
		     	sql="select 1 from lccont where contno='"+"?contno?"+"' and CustomGetPolDate is null ";
		    	logger.debug("校验卡单"+inputNo+"是否已经激活的sql:"+sql);
		    	
		    	SQLwithBindVariables sqlvb31 = new SQLwithBindVariables();
		    	sqlvb31.sql(sql);
		    	sqlvb31.put("contno", inputNo);
		    	tSSRS=mExeSQL.execSQL(sqlvb31);
		    	if(tSSRS.getMaxRow()>0)
		    	{
		    		logger.debug(":卡号"+inputNo+"的卡式保单尚未激活");
					mResult.add(Fail);
			    	mResult.add("对不起，您的卡式保单尚未激活，请在有效期"+endDateTime+"内进行激活");
			    	return false;
		    	} 	
			}
			else
			{
				//校验7-2：查询接口时要增加对未激活卡单的校验,如果该卡单还未激活,则提示卡尚未激活，提示信息为“对不起，您的卡式保单尚未激活，请在有效期XXXXX内进行激活
		     	sql="select 1 from lccont where familyid ='"+"?familyid?"+"' and CustomGetPolDate is null ";
		    	logger.debug("校验卡单"+inputNo+"是否已经激活的sql:"+sql);
		    	
		    	SQLwithBindVariables sqlvb32 = new SQLwithBindVariables();
		    	sqlvb32.sql(sql);
		    	sqlvb32.put("familyid", inputNo);
		    	tSSRS=mExeSQL.execSQL(sqlvb32);
		    	if(tSSRS.getMaxRow()>0)
		    	{
		    		logger.debug(":卡号"+inputNo+"的卡式保单尚未激活");
					mResult.add(Fail);
			    	mResult.add("对不起，您的卡式保单尚未激活，请在有效期"+endDateTime+"内进行激活");
			    	return false;
		    	} 
			}
		}

		logger.debug("***************卡单是否激活校验结束!*********\n");

		
    	return true;
    }
    
    
  /**
   * 得到自助卡单的单证类型
   * parem:CertifyNo
   * return :CertityCode
   * 卡号的3-8是24+四位版本号,从0015开始; 
   * 如果5,6的数字都还是0,则表示还没有产生借位,则系统组合出的单证类型为24+7-8位的两位数字,如2415,2416;
   * 如果第5位是0,而第6位不是0,表明第6位的数字被借用,则系统组合出的单证类型为24+6-8位的三位数字,如24100,24101;
   * 如果第5位不是0,不管第6位是否为0,系统组合出的单证就是卡号的3-8位数字,如241000,241001
   */
  public String GetCertifyType(String x) 
  {
	  logger.debug("ActivateBL:开始根据卡号得到对应的单证类型");
	  String inputNo2=x;
	  logger.debug("需要拼出卡单类型的卡号:"+inputNo2);

	  String certifycode="";//单证类型
	  
	  //zy 2010-01-15 新单证采用新规则，老单证采用原有规则，通过单证号码第三位判断，如果为7则认为是新单证
	  if(inputNo2.substring(2,3).equals("7"))
	  {
		  
		  certifycode=inputNo2.substring(2,8);
	  }
	  else
	  {
		  //没有借位
		  if(inputNo2.substring(4,5).equals("0")&&inputNo2.substring(5,6).equals("0"))
		  {
			  certifycode=inputNo2.substring(2,4)+inputNo2.substring(6,8);
		  }
		  //两位都被借,即四位版本号的第1位被借用
		  else if(inputNo2.substring(5,6).equals("0"))
		  {
			  certifycode=inputNo2.substring(2,8);
		  }
		  //四位版本号的第2位被借用
		  else
		  {
			  certifycode=inputNo2.substring(2,4)+inputNo2.substring(5,8);
		  }
	  }

	  logger.debug("卡号"+inputNo2+"对应的单证类型:"+certifycode);
	  
	  return certifycode;
  }
	private boolean CheckTBField(String operType) {
	// 保单 mLCPolSchema mLCGrpPolBL
	// 投保人 mLCAppntBL mLCAppntGrpBL
	// 被保人 tLCInsuredSchemaSet tLCInsuredSchemaSetNew
	// 受益人 mLCBnfBLSet mLCBnfBLSetNew
	// 告知信息 mLCCustomerImpartBLSet mLCCustomerImpartBLSetNew
	// 特别约定 mLCSpecBLSet mLCSpecBLSetNew
	// 保费项表 mLCPremBLSet 保存特殊的保费项数据(目前针对磁盘投保，不用计算保费保额类型)
	// 给付项表 mLCGetBLSet
	// 一般的责任信息 mLCDutyBL
	// 责任表 mLCDutyBLSet
	String strMsg = "";
	boolean MsgFlag = false;

	String RiskCode = mLCPolSchema.getRiskCode();
	// logger.debug("测试 by yaory riskcode="+RiskCode);
	// logger.debug("测试 by yaory"+mLCPolSchema.getPrem());
	try {
		VData tVData = new VData();
		CheckFieldCom tCheckFieldCom = new CheckFieldCom();

		// 计算要素
		FieldCarrier tFieldCarrier = new FieldCarrier();
		tFieldCarrier.setPrem(mLCPolSchema.getPrem()); // 保费
		tFieldCarrier.setInsuredNo(mLCPolSchema.getInsuredNo()); // add by
		// yaory
		logger.debug("测试 fieldcarrier====" + mLCPolSchema.getInsuredNo());
		logger.debug("测试 fieldcarrier====mLCPolSchema.getOperator():"
				+ mLCPolSchema.getOperator());
		logger.debug("测试 fieldcarrier====mLCPolSchema.getPolNo():"
				+ mLCPolSchema.getPolNo());
		tFieldCarrier.setAppAge(PubFun.calInterval3(tLCInsuredSchema.getBirthday(),tLCPolSchema.getCValiDate(), "Y")); // 被保人年龄
		tFieldCarrier.setInsuredName(tLCInsuredSchema.getName()); // 被保人姓名
		tFieldCarrier.setSex(tLCInsuredSchema.getSex()); // 被保人性别
		tFieldCarrier.setMult(mLCPolSchema.getMult()); // 投保份数
		tFieldCarrier.setPolNo(mLCPolSchema.getPolNo()); // 投保单号码
		tFieldCarrier.setMainPolNo(mLCPolSchema.getMainPolNo()); // 主险号码
		tFieldCarrier.setRiskCode(mLCPolSchema.getRiskCode()); // 险种编码
		tFieldCarrier.setCValiDate(tLCPolSchema.getCValiDate()); // 生效日期
		tFieldCarrier.setAmnt(mLCPolSchema.getAmnt()); // 保额
		tFieldCarrier.setInsuredBirthday(tLCInsuredSchema.getBirthday()); // 被保人出生日期
		tFieldCarrier.setInsuYear(mLCPolSchema.getInsuYear()); // 保险期间
		tFieldCarrier.setInsuYearFlag(mLCPolSchema.getInsuYearFlag()); // 保险期间单位
		tFieldCarrier.setPayEndYear(mLCPolSchema.getPayEndYear()); // 交费期间
		tFieldCarrier.setPayEndYearFlag(mLCPolSchema.getPayEndYearFlag()); // 交费期间单位
		tFieldCarrier.setPayIntv(mLCPolSchema.getPayIntv()); // 交费方式
		tFieldCarrier.setPayYears(mLCPolSchema.getPayYears()); // 交费年期
		tFieldCarrier.setOccupationType(tLCInsuredSchema.getOccupationType()); // 被保人职业类别
		tFieldCarrier.setGetYear(mLCPolSchema.getGetYear()); // 领取年龄
		LCDutySet tLCDutySet = new LCDutySet();
		LCDutyDB tLCDutyDB = new LCDutyDB();
		tLCDutyDB.setPolNo(mLCPolSchema.getPolNo());
		tLCDutySet = tLCDutyDB.query();
		if(tLCDutySet.size()<=0){
			mResult.add(Fail);
        	mResult.add("数据有误：险种责任信息不存在" );
			return false;
		}
		LCDutySchema tLCDutySchema = new LCDutySchema();
		tLCDutySchema = tLCDutySet.get(1);
		tFieldCarrier.setGetIntv(tLCDutySchema.getGetIntv());
		tFieldCarrier.setGrpPolNo(mLCPolSchema.getGrpPolNo());
		tFieldCarrier.setContNo(mLCPolSchema.getContNo());
		tFieldCarrier.setEndDate(PubFun.newCalDate(tLCPolSchema.getCValiDate(),mLCPolSchema.getInsuYearFlag(),mLCPolSchema.getInsuYear()));
		tFieldCarrier.setEdorType("");
		tFieldCarrier.setPeakLine(tLCDutySchema.getPeakLine());
		// logger.debug("保单类型为："+mLCPolSchema.getPolTypeFlag());
		tFieldCarrier.setPolTypeFlag(mLCPolSchema.getPolTypeFlag());
		tFieldCarrier.setGetLimit(tLCDutySchema.getGetLimit());
		tFieldCarrier.setLiveGetMode(mLCPolSchema.getLiveGetMode());
		tFieldCarrier.setGetDutyKind(""); // 用于115被保险投保年龄和领取年龄的校验 
		tFieldCarrier.setBonusGetMode(mLCPolSchema.getBonusGetMode());// zhangzheng
		tFieldCarrier.setPolapplydate(mLCPolSchema.getPolApplyDate());
		// if (mark != null && mark.equals("card")) {
		tFieldCarrier.setOccupationCode(tLCInsuredSchema.getOccupationCode());
		// 传入全局变量Operator
		tFieldCarrier.setOperator(mLCPolSchema.getOperator());
		tFieldCarrier.setSellType(mLCContSchema.getSellType());
		
		tFieldCarrier.setIDNo(tLCInsuredSchema.getIDNo());
		tFieldCarrier.setAgentCode(mLCPolSchema.getAgentCode());
		//
		logger.debug("ProposalBL中销售方式：" + mLCContSchema.getSellType());
		tFieldCarrier.setMult(1);
		tFieldCarrier.setContPlanCode(tLCInsuredSchema.getContPlanCode());
		// }
		LCPremSet mLCPremSet = new LCPremSet();
		LCPremDB mLCPremDB = new LCPremDB();
		mLCPremDB.setPolNo(mLCPolSchema.getPolNo());
		mLCPremSet = mLCPremDB.query();
		if(mLCPremSet.size()<=0){
			mResult.add(Fail);
        	mResult.add("数据有误" );
			return false;
		}
		LCPremSchema tLCPremSchema = null;
		// logger.debug("测试 by yaory mlcpremblset="+mLCPremBLSet.size(
		// ));
		for (int i = 1; i <= mLCPremSet.size(); i++) {

			tLCPremSchema = mLCPremSet.get(i);

		}
		/*
		 * Lis5.3 upgrade get
		 * logger.debug("进入算法表的管理费比例为"+tLCPremSchema
		 * .getManageFeeRate());
		 * tFieldCarrier.setManageFeeRate(tLCPremSchema.getManageFeeRate());
		 */
		// logger.debug("进入算法表的责任编码为"+tLCPremSchema.getDutyCode());
		// tLCPremSchema可能为空，不能直接使用的，，edit by yaory,,hehe,,,,,
		try {
			tFieldCarrier.setDutyCode(tLCPremSchema.getDutyCode());
		} catch (Exception ex) {
			logger.debug("lmcheckfield中没有责任代码 ");
		}
		
		//适合单责任险种-因现在只有乘意险及贷款无忧需此要素，这两个险种都是单责任的，所以加入
		tFieldCarrier.setFloatRate(mLCPolSchema.getFloatRate());
	
		// edit by yaory logger.debug("没有执行以下");
		if (mLCPolSchema.getStandbyFlag1() != null) {
			tFieldCarrier.setStandbyFlag1(mLCPolSchema.getStandbyFlag1());
		}
		if (mLCPolSchema.getStandbyFlag2() != null) {
			tFieldCarrier.setStandbyFlag2(mLCPolSchema.getStandbyFlag2());
		}
		if (mLCPolSchema.getStandbyFlag3() != null) {
			tFieldCarrier.setStandbyFlag3(mLCPolSchema.getStandbyFlag3());
		}

		// 为卡单地域控制加入计算要素
		if (mLCPolSchema.getManageCom() != null) {
			tFieldCarrier.setManageCom(mLCPolSchema.getManageCom());
		}
		tFieldCarrier.setSaleChnl(mLCPolSchema.getSaleChnl());

		tVData.add(tFieldCarrier);

		LMCheckFieldSchema tLMCheckFieldSchema = new LMCheckFieldSchema();
		tLMCheckFieldSchema.setRiskCode(RiskCode);
		tLMCheckFieldSchema.setFieldName("TB" + operType); // 投保
	
		tVData.add(tLMCheckFieldSchema);
		if (tCheckFieldCom.CheckField(tVData) == false) {
			logger.debug("hehehehehe");
			return false;
		} else {
			logger.debug("Check Data");

			LMCheckFieldSet mLMCheckFieldSet = tCheckFieldCom
					.GetCheckFieldSet();
			logger.debug("@@@@@@@@@@@@@mLMCheckFieldSet.size():"
					+ mLMCheckFieldSet.size());
			for (int n = 1; n <= mLMCheckFieldSet.size(); n++) {
				LMCheckFieldSchema tField = mLMCheckFieldSet.get(n);
				if ((tField.getReturnValiFlag() != null)
						&& tField.getReturnValiFlag().equals("N")) {
					if ((tField.getMsgFlag() != null)
							&& tField.getMsgFlag().equals("Y")) {
						MsgFlag = true;
						strMsg = strMsg + tField.getMsg() + " ; ";

						break;
					}
				}
			}
			if (MsgFlag == true) {
				// @@错误处理
				mResult.add(Fail);
	        	mResult.add("数据有误：" + strMsg);
				return false;
			}
		}
	} catch (Exception ex) {
		// @@错误处理
		CError.buildErr(this, "发生错误，请检验CheckField模块:" + ex);

		return false;
	}

	return true;
}
  //zy 2010-01-26 校验是否为组合产品
  private boolean isPortfolio(String fContno)
  {
	  boolean PortfolioFlag=false;
	  String mCertiCode=GetCertifyType(fContno);
	  String pSql ="select portfolioflag from lmcardrisk where certifycode='"+"?certifycode?"+"'";
	  
	  SQLwithBindVariables sqlvb33 = new SQLwithBindVariables();
	  sqlvb33.sql(pSql);
	  sqlvb33.put("certifycode", mCertiCode);
	  String mPortfolio = mExeSQL.getOneValue(sqlvb33);
	  if(mPortfolio==null || "".equals(mPortfolio))
		  PortfolioFlag=false;
	  else
		  PortfolioFlag=true;
	  return PortfolioFlag;
  }
      
    /**
     * 默认构造方法，
     * 
     * 
     */
  public ActivateBL() 
  {
	  
  }
    
  public static void main(String[] args) 
  {
		// TODO Auto-generated method stub
		/**
	  	logger.debug("开始设置保单基本信息...");
		LCPolSchema tLCPolSchema = new LCPolSchema();
		 
		tLCPolSchema.setContNo("86240015091800000001");
		tLCPolSchema.setPrtNo("86240015091800000001");
		tLCPolSchema.setInsuredPeoples("1");//被保人人数
		tLCPolSchema.setPolTypeFlag("0");//保单类型标记 0 --个人单,1 --无名单,2 --（团单）公共帐户           
		tLCPolSchema.setCValiDate(PubFun.getCurrentDate());
		tLCPolSchema.setStandbyFlag1("电话");//卡单激活类型
		logger.debug("Save:CValidate:"+tLCPolSchema.getCValiDate());
    	logger.debug("设置保单基本信息结束..."); 		    		
    		
		// 投保人信息部分
		logger.debug("开始设置投保人基本信息...");
		LCAppntSchema tLCAppntSchema = new LCAppntSchema();
			
		tLCAppntSchema.setContNo(tLCPolSchema.getContNo());//保单号
		tLCAppntSchema.setPrtNo(tLCPolSchema.getContNo());
		tLCAppntSchema.setGrpContNo("00000000000000000000");
		tLCAppntSchema.setAppntGrade("M");//投保人级别:M ---主投保人,S ---从头保人
		tLCAppntSchema.setAppntName("DHJ");              //姓名 
		tLCAppntSchema.setAppntSex("0");                //性别
		tLCAppntSchema.setAppntBirthday("1982-01-01");      //出生日期 
		tLCAppntSchema.setIDType("0");          //证件类型
		tLCAppntSchema.setIDNo("220322198201011913");              //证件号码 :测试证件和性别,出生日期方面的校验
		tLCAppntSchema.setOccupationType("1");      
		tLCAppntSchema.setOccupationCode("5166153");      //职业代码
		tLCAppntSchema.setRelationToInsured("00"); //与被保险人关系
		logger.debug("投保人与被保人关系:"+tLCAppntSchema.getRelationToInsured());
		
		LCAddressSchema tLCAddressSchema = new LCAddressSchema(); 
		tLCAddressSchema.setPostalAddress("测试联系地址");//联系地址
		tLCAddressSchema.setZipCode("451511");        //邮政编码
		tLCAddressSchema.setPhone("56565");            //联系电话 
		tLCAddressSchema.setEMail("zz3tr03243@sina.com");            //电子邮箱
			
		logger.debug("设置投保人信息结束...");
			
			
		logger.debug("开始设置被保人基本信息...");
			
		//被保人信息
		LCInsuredSchema tLCInsuredSchema = new LCInsuredSchema();
		LCAddressSchema tLCAddressSchema2 = new LCAddressSchema(); 			
		if(tLCAppntSchema.getRelationToInsured().equals("00"))
		{
			//被保人是投保人本人
			tLCInsuredSchema.setContNo(tLCPolSchema.getContNo());//保单号			
	  		tLCInsuredSchema.setName(tLCAppntSchema.getAppntName());              //姓名 
	  		tLCInsuredSchema.setSex(tLCAppntSchema.getAppntSex());                //性别
	  		tLCInsuredSchema.setBirthday(tLCAppntSchema.getAppntBirthday());      //出生日期 
	  		tLCInsuredSchema.setIDType(tLCAppntSchema.getIDType());          //证件类型
	  		tLCInsuredSchema.setIDNo(tLCAppntSchema.getIDNo());              //证件号码 	  	
	  		tLCInsuredSchema.setOccupationType(tLCAppntSchema.getOccupationType());       //职业类别
	  		tLCInsuredSchema.setOccupationCode(tLCAppntSchema.getOccupationCode());       //职业代码
	  		
			tLCAddressSchema2.setPostalAddress("测试联系地址");//联系地址
			tLCAddressSchema2.setZipCode("451511");        //邮政编码
			tLCAddressSchema2.setPhone("56565");            //联系电话 
			tLCAddressSchema2.setEMail("zz3tr03243@sina.com");            //电子邮箱
		}
		else
		{
			//投保人是被保人的父亲或母亲
			tLCInsuredSchema.setContNo(tLCPolSchema.getContNo());//保单号			
			tLCInsuredSchema.setName("ZZ");              //姓名 
			tLCInsuredSchema.setSex("1");                //性别
			tLCInsuredSchema.setBirthday("1991-04-01");      //出生日期 
			tLCInsuredSchema.setIDType("0");          //证件类型
			tLCInsuredSchema.setIDNo("110101199104011501");              //证件号码 :测试证件和性别,出生日期方面的校验
			tLCInsuredSchema.setOccupationType("1");      
			tLCInsuredSchema.setOccupationCode("5166153");      //职业代码
			
			tLCAddressSchema2.setPostalAddress("测试联系地址");//联系地址
			tLCAddressSchema2.setZipCode("451511");        //邮政编码
			tLCAddressSchema2.setPhone("56565");            //联系电话 
			tLCAddressSchema2.setEMail("zz3tr03243@sina.com");            //电子邮箱
		}
			
		logger.debug("被置投保人信息结束...");		
		*/
		
		String tAction = "BatchQuery";
		
		Vector tVector=new Vector();	
		VData tResult=new VData();
			
			
		tVector.addElement(tAction);
		tVector.addElement("86728001110200000001");
		tVector.addElement("02039483");
//		tVector.add("HX");//表示数据是从核心业务系统传到后台
//		tVector.addElement(tLCPolSchema);
//		tVector.addElement(tLCAppntSchema);
//		tVector.addElement(tLCInsuredSchema);
//		tVector.addElement(tLCAddressSchema);
//		tVector.addElement(tLCAddressSchema2);
		
		String Content="";
		String FlagStr="";
			 
		//激活处理类
		try
		{
		  	//将操作类型，管理机构，操作员添加到容器中
		    //激活处理类
		    ActivateBL tActivateBL=new ActivateBL();
		    tResult=(VData)tActivateBL.submitData(tVector);
		      	
		    logger.debug("返回的标识位:"+tResult.get(0));
		    logger.debug("返回的提示信息:"+tResult.get(1));
		}
		catch(Exception ex)
		{
			 Content  = "激活失败，原因是:" + ex.toString();
		     FlagStr  = "Fail";
		}
		
		
		//如果在Catch中发现异常，则不从错误类中提取错误信息
		logger.debug("***:Flag : " + FlagStr);
		if (FlagStr=="")
		{
		  if (tResult.get(0).equals("1"))
		  {
		      Content = (String)tResult.get(1);
		      FlagStr = "Succ";
		  }
		  else
		  {
		      Content = (String)tResult.get(1);
		      FlagStr = "Fail";
		  }
		}
		
	  
  }

}
