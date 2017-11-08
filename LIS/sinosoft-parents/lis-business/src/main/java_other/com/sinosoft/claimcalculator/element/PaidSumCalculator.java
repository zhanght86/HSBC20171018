/***********************************************************************
 * Module:  PaidSumCalculator.java
 * Author:  
 * Purpose: 累加要素（类型）：
 * 1-金额 2-天数 3-次数
 ***********************************************************************/

package com.sinosoft.claimcalculator.element;
import org.apache.log4j.Logger;

import com.sinosoft.claimcalculator.ClaimCalculatorElement;
import com.sinosoft.claimcalculator.pubfun.CalculatorPubFun;
import com.sinosoft.lis.schema.LCalculatorTraceSchema;

public class PaidSumCalculator extends ClaimCalculatorElement {
	private static Logger logger = Logger.getLogger(PaidSumCalculator.class);
   private float paidSum;
   
   /** 得到经累加器控制后的理赔赔付金额。
    * 
    */
   public double getClaimCalculationResultDetail() {
      // TODO: implement
	   //这个字段作为累加器计算的返回值
	   double tUsedLimit = 0;
	   try{
		   /*--使用20150511讨论的方案，不再汇总历史sum，而是根据上一条记录来生成本条trace记录，原来sum的算法注掉不用，暂不删除   --*/

			//现在金额、天、次累加器都统一调用一个类了，但遇到天、次累加器时，需要把this.mApplyPay替换成天、次
			if ("2".equals(this.factorType.getFactorTypeCode())){
				//天数累加器，本次申请天数调特定方法获取
//				LLClaimCalAutoBL tLLClaimCalAutoBL = new LLClaimCalAutoBL();
//				this.mApplyPay = tLLClaimCalAutoBL.getSumDayCount(this.mLLClaimDetailSchema);
				/** 
				 * TODO 当账单未录入天数时，【现】mApplyPay默认为0
				 */
				this.mApplyPay = CalculatorPubFun.getSumDayCount(this.mLLClaimDutyFeeSchema);
			} else if ("3".equals(this.factorType.getFactorTypeCode())){
				//次数累加器，本次申请次数默认为1
				/**
				 * 诊疗次数去账单明细的诊疗次数
				 * modify 20151209 by wsp  begin 
				 */
				if(mLLClaimDutyFeeSchema==null || mLLClaimDutyFeeSchema.getTimes()==0){
					this.mApplyPay = 1;
				}else{
					this.mApplyPay = mLLClaimDutyFeeSchema.getTimes();
				}
				/** end */
			}

			//本次理赔申请金额：即累加器控制前的理赔金额
			double tCurClaimPaidSum = this.mApplyPay;

		   LCalculatorTraceSchema tLCalculatorTraceSchema = new LCalculatorTraceSchema();
		   if ("1".equals(this.type.getTypeCode())){
			   //1-限额
			   /* 	//这是按照sum汇总既往trace的原始算法，暂时注掉！！
			   //调用以下方法获得本累加器的剩余可赔付金额
			   double tRemainedLimit = getRemainedLimit();
			   if (tRemainedLimit >= tCurClaimPaidSum){
				   //足额就全部通过
				   tUsedLimit = tCurClaimPaidSum;
			   }else{
				   //不足额就扣除全部累加器余额，即只能赔付理赔申请金额的一部分
				   tUsedLimit = tRemainedLimit;
			   }
			   //保存累加器轨迹：存一条trace相当于本累加器剩余可赔付金额-tUsedLimit
			   if (!dealCalculatorTrace(tUsedLimit,tRemainedLimit)){
				   tUsedLimit = 0;
			   }
			   */
			   /**
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ 
				 * 限额累加器计算
				 * No1.0 计算步骤一：得到本账单的上一条相关的trace记录
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ 
				 */
			   //xuyunpeng add and change start
			   /*
			   LCalculatorTraceSchema tLastRelatedTrace = getLastRelatedTrace();
			    */
			   
			   LCalculatorTraceSchema tLastRelatedTrace = getLastRelatedTracegxyp();
			   //xuyunpeng add and change end
			   //上一条trace的余额
			   double tLastSumNotPay = tLastRelatedTrace.getSumNotPay();  
			   //上一条trace的总已赔
			   double tLastSumPaid = tLastRelatedTrace.getSumPaid();
			   //xuyunpeng add 该账单第一次进行赔付，以前没有赔过
			   if(tLastSumPaid==0){
				   if((factorValue-tCurClaimPaidSum)>0){
					   tLCalculatorTraceSchema.setApplyPay(tCurClaimPaidSum);//本次要赔付
					   tLCalculatorTraceSchema.setSumNotPay(factorValue-tCurClaimPaidSum);//本次赔付后余额
					   tLCalculatorTraceSchema.setSumPaid(tLastSumPaid+tCurClaimPaidSum);//本账单历史总赔付
					   
				   }else{
					   tLCalculatorTraceSchema.setApplyPay(factorValue);//本次要赔付
					   tLCalculatorTraceSchema.setSumNotPay(0);//本次赔付后余额
					   tLCalculatorTraceSchema.setSumPaid(factorValue);//本账单历史总赔付
				   }
				   
			   }else{
				   
				   if((tLastSumNotPay-tCurClaimPaidSum)>0){
					   tLCalculatorTraceSchema.setApplyPay(tCurClaimPaidSum);//本次要赔付
					   tLCalculatorTraceSchema.setSumNotPay(tLastSumNotPay-tCurClaimPaidSum);//本次赔付后余额
					   tLCalculatorTraceSchema.setSumPaid(tLastSumPaid+tCurClaimPaidSum);//本账单历史总赔付

				   }else{
					   tLCalculatorTraceSchema.setApplyPay(tLastSumNotPay);//本次要赔付
					   tLCalculatorTraceSchema.setSumNotPay(0);//本次赔付后余额
					   tLCalculatorTraceSchema.setSumPaid(tLastSumPaid+tLastSumNotPay);//本账单历史总赔付
				   }
				   
			   }
			   
			   tUsedLimit = tLCalculatorTraceSchema.getApplyPay();
			   
			   //xuyunpeng add 注释 start
			  /* /**
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ 
				 * 限额累加器计算
				 * No2.0 计算步骤二：得到汇总当前getdutycode的全部赔付
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ 
				 *
			   //与该get关联的总赔付
			   /*double tAllSumPaid = getAllRelatedSumPaid();
			   
			   /**
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ 
				 * 限额累加器计算
				 * No3.0 计算步骤三：得到本get上一个账单的最后一个限额类累加器的trace
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ 
				 *
			   LCalculatorTraceSchema tLastLimitTrace = getLastLimitTrace();
			   //本get上一个账单的最后一个限额trace
			   double tLastSumNotPay_Limit = tLastLimitTrace.getSumNotPay();

			   */
			 //xuyunpeng add 注释 end 
			   
			   //xuyunpeng add 注释 start
			 /*
			   /**
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ 
				 * 限额累加器计算
				 * No4.0 计算步骤四：预查询完毕，开始按照设计逐个计算赋值trace的相关字段
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ 
				 *
			   //本次待赔，取账单金额
			   tLCalculatorTraceSchema.setApplyPay(tCurClaimPaidSum); 
			   
			   //如果本保单、本get的上一个账单已经有余额了，本账单就不用套公式计算，直接全额进入余额
			   if (tLastSumNotPay_Limit > 0 && tLastLimitTrace.getContNo().equals(this.mLLClaimDetailSchema.getContNo())){
				   //已经有余额了，本账单金额直接进入余额
				   //总待赔
				   //why？？？？？
				   tLCalculatorTraceSchema.setSumApplyPay(tLastLimitTrace.getSumNotPay()+tCurClaimPaidSum);
				   //modify by wsp 2015
				   if(tCurClaimPaidSum>this.factorValue - tAllSumPaid){
					   //赔付
					   tLCalculatorTraceSchema.setUsedLimit(this.factorValue - tAllSumPaid);
					   //余额
					   tLCalculatorTraceSchema.setSumNotPay(tLastSumNotPay_Limit+tCurClaimPaidSum-tLCalculatorTraceSchema.getUsedLimit());
				   }else{
					   //赔付
					   tLCalculatorTraceSchema.setUsedLimit(tCurClaimPaidSum);
					   //余额
					   tLCalculatorTraceSchema.setSumNotPay(tLastSumNotPay_Limit);
				   }
				   //总已赔
				   tLCalculatorTraceSchema.setSumPaid(tLCalculatorTraceSchema.getUsedLimit()); 
				   tUsedLimit = tLCalculatorTraceSchema.getUsedLimit();
//				   //赔付
//				   tLCalculatorTraceSchema.setUsedLimit( 0 );
//				   //余额
//				   tLCalculatorTraceSchema.setSumNotPay(tLastSumNotPay_Limit+tCurClaimPaidSum);
//				   //总已赔
//				   tLCalculatorTraceSchema.setSumPaid( 0 ); 
//				   tUsedLimit = 0;
			   } else {
				   //尚未有余额，要套公式算
				   //总待赔：上一条余额+本账单(第一次)
				   if (feeAlreadyInCache()){
					   //本账单不是第一次计算
					   tLCalculatorTraceSchema.setSumApplyPay(tLastSumNotPay);
				   } else {
					   //本账单第一次计算
					   tLCalculatorTraceSchema.setSumApplyPay(tLastSumNotPay + tCurClaimPaidSum);
				   }
				   //赔付=min(总已赔（上一条的）,限额-关联总赔付）
				   if ( tLastRelatedTrace.getCalculatorCode()!=null && !"".equals(tLastRelatedTrace.getCalculatorCode())){
					   //是否换保单了要走不通的分支：
					   if (this.mLLClaimDetailSchema.getContNo().equals(tLastRelatedTrace.getContNo())){
						   //同一保单
						   if ( tLastSumPaid > this.factorValue - tAllSumPaid ){
							   tLCalculatorTraceSchema.setUsedLimit(this.factorValue - tAllSumPaid);
						   } else {
							   tLCalculatorTraceSchema.setUsedLimit( tLastSumPaid );
						   }
						   //余额 = 总已赔（上一条的）+总待赔-赔付
						   tLCalculatorTraceSchema.setSumNotPay( tLastSumPaid + tLCalculatorTraceSchema.getSumApplyPay() - tLCalculatorTraceSchema.getUsedLimit() );
					   } else {
						   //换了保单
						   if (tLCalculatorTraceSchema.getSumApplyPay() > this.factorValue - tAllSumPaid){
							   tLCalculatorTraceSchema.setUsedLimit(this.factorValue - tAllSumPaid);
						   } else {
							   tLCalculatorTraceSchema.setUsedLimit( tLCalculatorTraceSchema.getSumApplyPay() );
						   }
						   //余额 = 总待赔-赔付
						   tLCalculatorTraceSchema.setSumNotPay( tLCalculatorTraceSchema.getSumApplyPay() - tLCalculatorTraceSchema.getUsedLimit() );
					   }
				   } else {
					   //如果没有上一条，就拿账单金额直接计算
					   if ( tCurClaimPaidSum > this.factorValue - tAllSumPaid ){
						   //modify 20151209 by wsp begin
						   /**
						    * TODO 题添加提醒，修改次数
						    *
						   if ("2".equals(this.factorType.getFactorTypeCode()) || "3".equals(this.factorType.getFactorTypeCode())){
							   tLCalculatorTraceSchema.setUsedLimit(0);
						   }else{
							   tLCalculatorTraceSchema.setUsedLimit(this.factorValue - tAllSumPaid);
						   }
						   //end
					   } else {
						   tLCalculatorTraceSchema.setUsedLimit( tCurClaimPaidSum );
					   }
					   //余额 = 总已赔（上一条的）+总待赔-赔付
					   tLCalculatorTraceSchema.setSumNotPay( tLastSumPaid + tLCalculatorTraceSchema.getSumApplyPay() - tLCalculatorTraceSchema.getUsedLimit() );
				   }
				   tUsedLimit = tLCalculatorTraceSchema.getUsedLimit();
				   //总已赔 = min(赔付,限额)
				   tLCalculatorTraceSchema.setSumPaid( tLCalculatorTraceSchema.getUsedLimit()); 
			   }
			   tLCalculatorTraceSchema.setBeforeUsedLimit(this.factorValue - tAllSumPaid);
			   tLCalculatorTraceSchema.setAfterUsedLimit(this.factorValue - tAllSumPaid - tUsedLimit);
			   /**
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ 
				 * 限额累加器计算
				 * No5.0 计算步骤五：赋值trace的相关字段后，保存该schema
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ 
				 */
			   
			   //xuyunpeng add 注释 end

			   //保存累加器轨迹
			   if (!dealCalculatorTrace(tLCalculatorTraceSchema)){
				   tUsedLimit = 0;
			   }

		   }else if ("2".equals(this.type.getTypeCode())){
			   //2-免赔额 
			   //经本累加器控制后本次应赔付金额
			   /* 	//这是按照sum汇总既往trace的原始算法，暂时注掉！！
			   //以下方法通过汇总累加器轨迹获得本累加器的既往已申请赔付总金额
			   double tClaimSum = getClaimSum();
			   if (tClaimSum >= this.FactorValue){
				   //如果本次理赔之前既往赔付总金额就已超免赔额，则直接返回原申请金额，本累加器不再控制。
				   return tCurClaimPaidSum;
			   }
			   //既往总金额+本次申请金额-免赔额（即累加器factorvalue），即为本次可赔付金额，如果该值<0则按0算即本次不赔付
			   tDuePaidSum = tClaimSum + tCurClaimPaidSum - this.FactorValue;
			   if (tDuePaidSum < 0){
				   //进入这个分支，表示算上本次理赔后已赔金额仍达不到免赔额标准，故本次赔付0，同时存一条金额等于本次申请金额的累加器轨迹
				   tDuePaidSum = 0;
				   if (!dealCalculatorTrace(tCurClaimPaidSum,this.FactorValue - tClaimSum)){
					   tDuePaidSum = 0;
				   }
			   }else{
				 //进入这个分支，表示算上本次理赔后已申请赔付总金额首次超越免赔额标准，故本次赔付金额=tDuePaidSum，同时存一条金额=FactorValue - tClaimSum的累加器轨迹金额
				   if (!dealCalculatorTrace(this.FactorValue - tClaimSum,this.FactorValue - tClaimSum)){
					   tDuePaidSum = 0;
				   }
			   }
			   */


			   /**
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ 免赔额累加器默认为一个dutyget的优先累加器，这需要靠累加器order的设定来保证，程序里现在不做专门控制
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0 计算步骤一：得到上一条相关的trace记录（如果有）
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
				 */

			   LCalculatorTraceSchema tLastRelatedTrace = getLastRelatedTrace();
			   //上一条trace的余额
			   double tLastSumNotPay = tLastRelatedTrace.getSumNotPay();  

			   /**
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ 免赔额累加器计算
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.0 计算步骤二：本get的上一条免赔额trace的赔付
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
				 */

			   LCalculatorTraceSchema tLastRelatedDeductibleTrace = getLastRelatedDeductibleTrace();
			   //本get的上一条免赔额trace的赔付
			   double tLastPaid = tLastRelatedDeductibleTrace.getUsedLimit();

			   /**
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ 免赔额累加器计算
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No3.0 计算步骤三：根据账单和上一条trace记录（如果有），设置本条trace记录
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
				 */

			   if (tLastPaid > 0){
				   //如果本get已经存了一条超过免赔额的trace（约定该trace的赔付=免赔额），则之后该get的免赔额失效，不用再存trace了，申请金额也原样返回
				   tUsedLimit = tCurClaimPaidSum;
			   } else {
				   //本待赔，取账单金额
				   tLCalculatorTraceSchema.setApplyPay(tCurClaimPaidSum);
				   //总待赔：上一条余额+本账单——只有本账单第一次计算时需要加入账单金额，而免赔额一定是第一个计算的累加器，所以肯定要加
				   tLCalculatorTraceSchema.setSumApplyPay(tLastSumNotPay + tCurClaimPaidSum);
				   //赔付、余额
				   if ( tLCalculatorTraceSchema.getSumApplyPay() >= this.factorValue){
				   	//如总待赔超过免赔额，约定赔付字段=免赔额、余额字段=总待赔-免赔额
					   tLCalculatorTraceSchema.setUsedLimit(this.factorValue);
					   tLCalculatorTraceSchema.setSumNotPay(tLCalculatorTraceSchema.getSumApplyPay() - this.factorValue);
					   tUsedLimit = tLCalculatorTraceSchema.getSumNotPay() ;
				   } else {
				   	//如总待赔不超过免赔额，约定赔付字段=0、余额字段=总待赔
					   tLCalculatorTraceSchema.setUsedLimit(0);
					   tLCalculatorTraceSchema.setSumNotPay(tLCalculatorTraceSchema.getSumApplyPay());
					   tUsedLimit = 0 ;
				   }
				   //总已赔，约定免赔额累加器总已赔=0
				   tLCalculatorTraceSchema.setSumPaid(0);
				   /**
					 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ 免赔额累加器计算
					 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No4.0 计算步骤四：赋值trace的相关字段后，保存该schema
					 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ 
					 */
				   //保存累加器轨迹
				   if (!dealCalculatorTraceNew(tLCalculatorTraceSchema)){
					   tUsedLimit = 0;
				   }
			   }
			   
		   }else if ("3".equals(this.type.getTypeCode())){
			   //3-计算(公式)，即赔付
			   /* 	//这是按照sum汇总既往trace的原始算法，暂时注掉！！
			   //以下方法executePay中将调用LMCalcode中的sql公式重新计算理赔申请金额
			   tCurClaimPaidSum = executePay(tCurClaimPaidSum, this.getCalCode(),"");
			   //调用以下方法获得本累加器的剩余可赔付金额
			   double tRemainedLimit = getRemainedLimit();
			   if (tRemainedLimit >= tCurClaimPaidSum){
				   //足额就全部通过 
				   tUsedLimit = tCurClaimPaidSum;
			   }else{
				   //不足额就扣除全部累加器余额，即只能赔付理赔申请金额的一部分
				   tUsedLimit = tRemainedLimit;
			   }
			   //保存累加器轨迹：存一条trace相当于本累加器剩余可赔付金额-tUsedLimit
			   if (!dealCalculatorTrace(tUsedLimit,tRemainedLimit)){
				   tUsedLimit = 0;
			   }
			   */


			   /**
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ 赔付累加器计算
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0 计算步骤一：得到上一条相关的trace记录（如果有）
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ 
				 */

			   LCalculatorTraceSchema tLastRelatedTrace = getLastRelatedTrace();
			   //上一条trace的总待赔
			   double tLastSumNotPay = tLastRelatedTrace.getSumNotPay();
			   //上一条trace的总已赔
			   double tLastSumPaid = tLastRelatedTrace.getSumPaid();
			   
				/**
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ 赔付累加器计算
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.0 计算步骤二：根据账单和上一条trace记录（如果有），设置本条trace记录
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
				 */

			   //本待赔，取账单金额
			   tLCalculatorTraceSchema.setApplyPay(tCurClaimPaidSum);
			   //总待赔：上一条余额+(本账单第一次)
			   if (feeAlreadyInCache()){
				   //本账单不是第一次计算
				   tLCalculatorTraceSchema.setSumApplyPay(tLastSumNotPay);
			   } else {
				   //本账单第一次计算
				   tLCalculatorTraceSchema.setSumApplyPay(tLastSumNotPay + tCurClaimPaidSum);
			   }
			   //赔付，取值来自计算结果
			   tLCalculatorTraceSchema.setUsedLimit(executePay(tCurClaimPaidSum, this.getCalCode(),""));
			   //余额 = 总待-赔
			   //sl modified 20150608 赔付累加器的余额字段不为零会导致后续的限额累加器控制产生混乱，此处改为0：实际上赔付累加器的未赔金额其实可以用ApplyPay-UsedLimit得到，因此字段里也不必记录
			   //tLCalculatorTraceSchema.setSumNotPay(tLCalculatorTraceSchema.getSumApplyPay() - tLCalculatorTraceSchema.getUsedLimit());
			   tLCalculatorTraceSchema.setSumNotPay( 0 );
			   //总已赔 = 上一条trace的总已赔+本条赔付
			   tLCalculatorTraceSchema.setSumPaid(tLastSumPaid + tLCalculatorTraceSchema.getUsedLimit());

			   tUsedLimit = tLCalculatorTraceSchema.getUsedLimit();
			   /**
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ 赔付累加器计算
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No3.0 计算步骤三：赋值trace的相关字段后，保存该schema
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ 
				 */
			   //保存累加器轨迹
			   if (!dealCalculatorTraceNew(tLCalculatorTraceSchema)){
				   tUsedLimit = 0;
			   }

		   }else{
			   logger.info("PaidSumCalculator中传入了系统不支持的累加器类型，代码="+this.type.getTypeCode());
			   return 0;
		   }
		   //当要素类型是天、次时，需要把结果划算成金额，再返回
		   if ( tUsedLimit > 0 ){
				if ("2".equals(this.factorType.getFactorTypeCode()) || "3".equals(this.factorType.getFactorTypeCode())){
					//天数/次数累加器，乘以单天/单次金额将计算结果天数/次数换算成金额
					tUsedLimit = tUsedLimit * this.defaultValue;
					if(tUsedLimit<=0){
						tUsedLimit=mLLClaimDutyFeeSchema.getCalSum();
					}
				}
		   }
		   return tUsedLimit;
	   }
	   catch(Exception e){
		   logger.info(e.getMessage());
		   return 0;
	   }
		   
   }   
   
   /** 经本次理赔后累加器的要素值（余额）处理。
    * 
    */
   public boolean dealFactorValue(double tUsedLimit) {
      return true;
   }
   
   public void setPaidSum(float tPaidSum) {
	   this.paidSum = tPaidSum;
   }
   
   public float getPaidSum() {
      return this.paidSum;
   }
	public static void main(String args[]) {
//		

	}

}