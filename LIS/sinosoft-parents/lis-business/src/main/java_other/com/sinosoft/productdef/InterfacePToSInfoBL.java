

package com.sinosoft.productdef;

import com.sinosoft.utility.*;
import com.sinosoft.lis.db.InterfacePToSInfoDB;
import com.sinosoft.lis.db.SUBRISKCODETOPLANCODEINFODB;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.InterfacePToSInfoSchema;
import com.sinosoft.lis.vdb.SUBRISKCODETOPLANCODEINFODBSet;
import com.sinosoft.lis.vschema.InterfacePToSInfoSet;
import com.sinosoft.lis.vschema.SUBRISKCODETOPLANCODEINFOSet;



public class InterfacePToSInfoBL  {
 /** 错误处理类，每个需要错误处理的类中都放置该类 */
 public  CErrors mErrors=new CErrors();
 private VData mResult = new VData();
 /** 往后面传输数据的容器 */
 //private VData mInputData= new VData();
 /** 全局数据 */
 private GlobalInput mGlobalInput =new GlobalInput() ;
 /** 数据操作字符串 */
// private String mOperate;
 /** 业务处理相关变量 */
 private MMap map=new MMap();
 
 private String Operator;
 private InterfacePToSInfoSchema mInterfacePToSInfoSchema;
 private SUBRISKCODETOPLANCODEINFOSet mSubRiskCodeToPlanCodeInfoset;
 
 
 public InterfacePToSInfoBL() {
 }

/**
* 传输数据的公共方法
 * @param: cInputData 输入的数据
   *         cOperate 数据操作
* @return:
*/
 public boolean submitData(VData cInputData,String cOperate)
 {
	 System.out.println("BL.......................");
   if( !getInputData(cInputData)){
	   return false;
   }
   if(!check())
   {
    return false;
   }
    //进行业务处理
 if(!deal()){
	 return false;
 }
   PubSubmit tPubSubmit=new PubSubmit();
   if(!tPubSubmit.submitData(mResult)){
	   mErrors.copyAllErrors(tPubSubmit.mErrors);
	   return false;
   }
    return true;
}
 
 private boolean check()
 {
 
  return true;
 }
private boolean getInputData(VData cInputData) {

		TransferData data = (TransferData) cInputData.getObjectByObjectName("TransferData", 0);
		mGlobalInput=(GlobalInput)cInputData.getObjectByObjectName("GlobalInput",0);		
		mInterfacePToSInfoSchema=(InterfacePToSInfoSchema)cInputData.getObjectByObjectName("InterfacePToSInfoSchema",0);
		mSubRiskCodeToPlanCodeInfoset=(SUBRISKCODETOPLANCODEINFOSet)cInputData.getObjectByObjectName("SUBRISKCODETOPLANCODEINFOSet",0);
		Operator=(String)data.getValueByName("Operator");
		System.out.println(mGlobalInput.Operator);
		return true;
	}
public CErrors getErrors() {
			// TODO Auto-generated method stub
			return mErrors;
}

@SuppressWarnings("unchecked")
public boolean deal(){
try {
	if("save".equals(Operator.toLowerCase())){
		InterfacePToSInfoDB tInterfacePToSInfoDB=new InterfacePToSInfoDB();
		tInterfacePToSInfoDB.setProductCode(mInterfacePToSInfoSchema.getProductCode());
		tInterfacePToSInfoDB.setIProductCode(mInterfacePToSInfoSchema.getIProductCode());
		tInterfacePToSInfoDB.setIsEffectiveDate(mInterfacePToSInfoSchema.getIsEffectiveDate());
		tInterfacePToSInfoDB.setIsSpouseCode(mInterfacePToSInfoSchema.getIsSpouseCode());
		tInterfacePToSInfoDB.setIsStaffCode(mInterfacePToSInfoSchema.getIsStaffCode());
		tInterfacePToSInfoDB.setIsJoinCode(mInterfacePToSInfoSchema.getIsJoinCode());
		tInterfacePToSInfoDB.setIsPremPayPeriod(mInterfacePToSInfoSchema.getIsPremPayPeriod());
		tInterfacePToSInfoDB.setIsBenefitOption(mInterfacePToSInfoSchema.getIsBenefitOption());
		tInterfacePToSInfoDB.setIsBenefitPeriod(mInterfacePToSInfoSchema.getIsBenefitPeriod());
		tInterfacePToSInfoDB.setIsChannel(mInterfacePToSInfoSchema.getIsChannel());
		tInterfacePToSInfoDB.setIsCurrency(mInterfacePToSInfoSchema.getIsCurrency());
		tInterfacePToSInfoDB.setIsPar(mInterfacePToSInfoSchema.getIsPar());
		tInterfacePToSInfoDB.setIsUreUWType(mInterfacePToSInfoSchema.getIsUreUWType());
		if(tInterfacePToSInfoDB.getInfo()){
		mErrors.addOneError("该产品已经存在，不能重复添加！");
			return false;
		}
		if(mSubRiskCodeToPlanCodeInfoset!=null&&mSubRiskCodeToPlanCodeInfoset.size()>0){
			System.out.println("存在附加险。。。。。。。。。。。。");
			
		for (int i = 1; i <=mSubRiskCodeToPlanCodeInfoset.size(); i++) {
			mSubRiskCodeToPlanCodeInfoset.get(i).setMAKEDATE(PubFun.getCurrentDate());
			mSubRiskCodeToPlanCodeInfoset.get(i).setMAKETIME(PubFun.getCurrentTime());
			mSubRiskCodeToPlanCodeInfoset.get(i).setMODIFYDATE(PubFun.getCurrentDate());
			mSubRiskCodeToPlanCodeInfoset.get(i).setMODIFYTIME(PubFun.getCurrentTime());
			SUBRISKCODETOPLANCODEINFODB tSUBRISKCODETOPLANCODEINFODB=new SUBRISKCODETOPLANCODEINFODB();
			tSUBRISKCODETOPLANCODEINFODB.setRISKCODE(mSubRiskCodeToPlanCodeInfoset.get(i).getRISKCODE());
			tSUBRISKCODETOPLANCODEINFODB.setPLANCODE(mSubRiskCodeToPlanCodeInfoset.get(i).getPLANCODE());
			tSUBRISKCODETOPLANCODEINFODB.setSUBRISKCODE(mSubRiskCodeToPlanCodeInfoset.get(i).getSUBRISKCODE());
			tSUBRISKCODETOPLANCODEINFODB.setSUBPLANCODE(mSubRiskCodeToPlanCodeInfoset.get(i).getSUBPLANCODE());
			
			//判断附加险主键是否为空
			if(mSubRiskCodeToPlanCodeInfoset.get(i).getISBENEFITOPTION()==null||"".equals(mSubRiskCodeToPlanCodeInfoset.get(i).getISBENEFITOPTION().trim())||
					mSubRiskCodeToPlanCodeInfoset.get(i).getSUBRISKCODE()==null||"".equals(mSubRiskCodeToPlanCodeInfoset.get(i).getSUBRISKCODE().trim())||
					mSubRiskCodeToPlanCodeInfoset.get(i).getSUBPLANCODE()==null||"".equals(mSubRiskCodeToPlanCodeInfoset.get(i).getSUBPLANCODE().trim())){
				mErrors.addOneError("附加险的是否与保障计划相关，保障计划类型，与保障相关的值三个信息必须填写！");
				return false;
			}
			if(tSUBRISKCODETOPLANCODEINFODB.getInfo()){
				mErrors.addOneError("产品 "+tInterfacePToSInfoDB.getProductCode()+" 的附加险 "+tSUBRISKCODETOPLANCODEINFODB.getRISKCODE()+" 已经存在，不能重复添加！");
				return false;
			}
		}
		}
		mInterfacePToSInfoSchema.setMAKEDATE(PubFun.getCurrentDate());
		mInterfacePToSInfoSchema.setMAKETIME(PubFun.getCurrentTime());
		mInterfacePToSInfoSchema.setMODIFYDATE(PubFun.getCurrentDate());
		mInterfacePToSInfoSchema.setMODIFYTIME(PubFun.getCurrentTime());
		map.put(mInterfacePToSInfoSchema, "INSERT");
		map.put(mSubRiskCodeToPlanCodeInfoset, "INSERT");
		
		
	}else if("del".equals(Operator.toLowerCase())){
		InterfacePToSInfoDB tInterfacePToSInfoDB=new InterfacePToSInfoDB();
		tInterfacePToSInfoDB.setProductCode(mInterfacePToSInfoSchema.getProductCode());
		tInterfacePToSInfoDB.setIProductCode(mInterfacePToSInfoSchema.getIProductCode());
		tInterfacePToSInfoDB.setIsEffectiveDate(mInterfacePToSInfoSchema.getIsEffectiveDate());
		tInterfacePToSInfoDB.setIsSpouseCode(mInterfacePToSInfoSchema.getIsSpouseCode());
		tInterfacePToSInfoDB.setIsStaffCode(mInterfacePToSInfoSchema.getIsStaffCode());
		tInterfacePToSInfoDB.setIsJoinCode(mInterfacePToSInfoSchema.getIsJoinCode());
		tInterfacePToSInfoDB.setIsPremPayPeriod(mInterfacePToSInfoSchema.getIsPremPayPeriod());
		tInterfacePToSInfoDB.setIsBenefitOption(mInterfacePToSInfoSchema.getIsBenefitOption());
		tInterfacePToSInfoDB.setIsBenefitPeriod(mInterfacePToSInfoSchema.getIsBenefitPeriod());
		tInterfacePToSInfoDB.setIsChannel(mInterfacePToSInfoSchema.getIsChannel());
		tInterfacePToSInfoDB.setIsCurrency(mInterfacePToSInfoSchema.getIsCurrency());
		tInterfacePToSInfoDB.setIsPar(mInterfacePToSInfoSchema.getIsPar());
		tInterfacePToSInfoDB.setIsUreUWType(mInterfacePToSInfoSchema.getIsUreUWType());
		if(!tInterfacePToSInfoDB.getInfo()){
		mErrors.addOneError("该产品信息不存在，不能删除！");
			return false;
		}
		//删除时附加险一并删除
		SUBRISKCODETOPLANCODEINFODB tSUBRISKCODETOPLANCODEINFODB=new SUBRISKCODETOPLANCODEINFODB();
		tSUBRISKCODETOPLANCODEINFODB.setRISKCODE(mInterfacePToSInfoSchema.getProductCode());
		SUBRISKCODETOPLANCODEINFOSet tSUBRISKCODETOPLANCODEINFOSet=tSUBRISKCODETOPLANCODEINFODB.query();
		if(tSUBRISKCODETOPLANCODEINFOSet!=null&&tSUBRISKCODETOPLANCODEINFOSet.size()>0)
		map.put(tSUBRISKCODETOPLANCODEINFOSet, "DELETE");
		map.put(mInterfacePToSInfoSchema, "DELETE");
	}else if("modify".equals(Operator.toLowerCase())){
		System.out.println("modify...........");
			
		InterfacePToSInfoDB tInterfacePToSInfoDB=new InterfacePToSInfoDB();
		tInterfacePToSInfoDB.setProductCode(mInterfacePToSInfoSchema.getProductCode());
		tInterfacePToSInfoDB.setIProductCode(mInterfacePToSInfoSchema.getIProductCode());
		tInterfacePToSInfoDB.setIsEffectiveDate(mInterfacePToSInfoSchema.getIsEffectiveDate());
		tInterfacePToSInfoDB.setIsSpouseCode(mInterfacePToSInfoSchema.getIsSpouseCode());
		tInterfacePToSInfoDB.setIsStaffCode(mInterfacePToSInfoSchema.getIsStaffCode());
		tInterfacePToSInfoDB.setIsJoinCode(mInterfacePToSInfoSchema.getIsJoinCode());
		tInterfacePToSInfoDB.setIsPremPayPeriod(mInterfacePToSInfoSchema.getIsPremPayPeriod());
		tInterfacePToSInfoDB.setIsBenefitOption(mInterfacePToSInfoSchema.getIsBenefitOption());
		tInterfacePToSInfoDB.setIsBenefitPeriod(mInterfacePToSInfoSchema.getIsBenefitPeriod());
		tInterfacePToSInfoDB.setIsChannel(mInterfacePToSInfoSchema.getIsChannel());
		tInterfacePToSInfoDB.setIsCurrency(mInterfacePToSInfoSchema.getIsCurrency());
		tInterfacePToSInfoDB.setIsPar(mInterfacePToSInfoSchema.getIsPar());
		tInterfacePToSInfoDB.setIsUreUWType(mInterfacePToSInfoSchema.getIsUreUWType());
		InterfacePToSInfoSet tInterfacePToSInfoSet = tInterfacePToSInfoDB.query();
		InterfacePToSInfoSchema mInterfacePToSInfoSchema2 = tInterfacePToSInfoSet.get(1);
		if(mInterfacePToSInfoSchema2==null)
		System.out.println("null。。。。。。。。。。。。。。。");
		

		mInterfacePToSInfoSchema.setMAKEDATE(mInterfacePToSInfoSchema2.getMAKEDATE());
		mInterfacePToSInfoSchema.setMAKETIME(mInterfacePToSInfoSchema2.getMAKETIME());
		if(!tInterfacePToSInfoDB.getInfo()){
			mErrors.addOneError("该产品信息不存在，不能更新！");
			return false;
		}
	
//		SUBRISKCODETOPLANCODEINFOSet mSubRiskCodeToPlanCodeInfoset3 = new SUBRISKCODETOPLANCODEINFOSet();
		if(mSubRiskCodeToPlanCodeInfoset!=null&&mSubRiskCodeToPlanCodeInfoset.size()>0){
			for (int i = 1; i <=mSubRiskCodeToPlanCodeInfoset.size(); i++) {
				SUBRISKCODETOPLANCODEINFODB tSUBRISKCODETOPLANCODEINFODB=new SUBRISKCODETOPLANCODEINFODB();
				tSUBRISKCODETOPLANCODEINFODB.setRISKCODE(mSubRiskCodeToPlanCodeInfoset.get(i).getRISKCODE());
				tSUBRISKCODETOPLANCODEINFODB.setPLANCODE(mSubRiskCodeToPlanCodeInfoset.get(i).getPLANCODE());
				tSUBRISKCODETOPLANCODEINFODB.setSUBRISKCODE(mSubRiskCodeToPlanCodeInfoset.get(i).getSUBRISKCODE());
				tSUBRISKCODETOPLANCODEINFODB.setSUBPLANCODE(mSubRiskCodeToPlanCodeInfoset.get(i).getSUBPLANCODE());
				
				System.out.println(mSubRiskCodeToPlanCodeInfoset.get(i).getISBENEFITOPTION()+"||"+
						mSubRiskCodeToPlanCodeInfoset.get(i).getSUBRISKCODE()+"||"+
						mSubRiskCodeToPlanCodeInfoset.get(i).getSUBPLANCODE());
				//确认附加险主键不为空
				if(mSubRiskCodeToPlanCodeInfoset.get(i).getISBENEFITOPTION()==null||"".equals(mSubRiskCodeToPlanCodeInfoset.get(i).getISBENEFITOPTION().trim())||
						mSubRiskCodeToPlanCodeInfoset.get(i).getSUBRISKCODE()==null||"".equals(mSubRiskCodeToPlanCodeInfoset.get(i).getSUBRISKCODE().trim())||
						mSubRiskCodeToPlanCodeInfoset.get(i).getSUBPLANCODE()==null||"".equals(mSubRiskCodeToPlanCodeInfoset.get(i).getSUBPLANCODE().trim())){
					mErrors.addOneError("附加险的是否与保障计划相关，保障计划类型，与保障相关的值三个信息必须填写！");
					return false;
				}
				
				if(tSUBRISKCODETOPLANCODEINFODB.getInfo()){
					
					SUBRISKCODETOPLANCODEINFOSet mSubRiskCodeToPlanCodeInfoset2 = tSUBRISKCODETOPLANCODEINFODB.query();
					if(mSubRiskCodeToPlanCodeInfoset2!=null)
					System.out.println(mSubRiskCodeToPlanCodeInfoset2.size()+"...........mSubRiskCodeToPlanCodeInfoset2...........");
					mSubRiskCodeToPlanCodeInfoset.get(i).setMAKEDATE(mSubRiskCodeToPlanCodeInfoset2.get(1).getMAKEDATE());
					mSubRiskCodeToPlanCodeInfoset.get(i).setMAKETIME(mSubRiskCodeToPlanCodeInfoset2.get(1).getMAKETIME());
					mSubRiskCodeToPlanCodeInfoset.get(i).setMODIFYDATE(PubFun.getCurrentDate());
					mSubRiskCodeToPlanCodeInfoset.get(i).setMODIFYTIME(PubFun.getCurrentTime());
					
				}else{
					mSubRiskCodeToPlanCodeInfoset.get(i).setMAKEDATE(PubFun.getCurrentDate());
					mSubRiskCodeToPlanCodeInfoset.get(i).setMAKETIME(PubFun.getCurrentTime());
					mSubRiskCodeToPlanCodeInfoset.get(i).setMODIFYDATE(PubFun.getCurrentDate());
					mSubRiskCodeToPlanCodeInfoset.get(i).setMODIFYTIME(PubFun.getCurrentTime());
				}
			}
			SUBRISKCODETOPLANCODEINFODB tSUBRISKCODETOPLANCODEINFODB2 = new SUBRISKCODETOPLANCODEINFODB();
			tSUBRISKCODETOPLANCODEINFODB2.setRISKCODE(mInterfacePToSInfoSchema.getProductCode());
			tSUBRISKCODETOPLANCODEINFODB2.setPLANCODE(mInterfacePToSInfoSchema.getIProductCode());
			
			tSUBRISKCODETOPLANCODEINFODB2.deleteSQL();			//删除原有附加险
			
			map.put(mSubRiskCodeToPlanCodeInfoset, "INSERT");	//添加附加险
		}
		mInterfacePToSInfoSchema.setMODIFYDATE(PubFun.getCurrentDate());
		mInterfacePToSInfoSchema.setMODIFYTIME(PubFun.getCurrentTime());
		map.put(mInterfacePToSInfoSchema, "UPDATE");
	}
	mResult.add(map);
} catch (Exception e) {
	e.printStackTrace();
	return false;
}
	return true;
}
 public static void main(String[] args) {
 }
}

