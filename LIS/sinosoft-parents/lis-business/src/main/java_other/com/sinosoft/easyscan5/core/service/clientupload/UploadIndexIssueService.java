package com.sinosoft.easyscan5.core.service.clientupload;

import com.sinosoft.easyscan5.base.service.BaseService;
import com.sinosoft.easyscan5.core.vo.easyscan.UploadIssueIndexVo;


public interface UploadIndexIssueService extends BaseService{


	public String saveIssueIndex(UploadIssueIndexVo uploadIssueIndexVo) throws Exception;

}
