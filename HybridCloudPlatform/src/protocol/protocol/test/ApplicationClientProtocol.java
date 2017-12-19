package protocol.protocol.test;


import java.io.Closeable;


import protocol.protocolWritable.test.ApplicationSubmissionContext;
import protocol.protocolWritable.test.GetNewApplicationResponse;
import protocol.protocolWritable.test.ResultStatus;

import rpc.core.VersionedProtocol;


public interface ApplicationClientProtocol extends VersionedProtocol {
	/**
	 * resourceManager分配一个一个新的applicationId+最大可申请资源量
	 * @return
	 */
	public GetNewApplicationResponse  getNewApplication();
	
	/**
	 * 将Application提交到ResourceManager
	 * @param request
	 * @return
	 */
	public ResultStatus submitApplication(ApplicationSubmissionContext request);
}
