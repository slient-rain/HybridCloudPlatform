
package protocol.protocol.test;

import java.io.IOException;

import protocol.protocolWritable.test.NodeHeartbeatRequest;
import protocol.protocolWritable.test.NodeHeartbeatResponse;
import protocol.protocolWritable.test.RegisterNodeManagerRequest;
import protocol.protocolWritable.test.RegisterNodeManagerResponse;
import rpc.core.VersionedProtocol;


public interface ResourceTrackerProtocol extends VersionedProtocol {
  
  public RegisterNodeManagerResponse registerNodeManager(
      RegisterNodeManagerRequest request) ;

  public NodeHeartbeatResponse nodeHeartbeat(NodeHeartbeatRequest request);

}
