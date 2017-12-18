package rpc.core;

import java.io.Closeable;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.util.Arrays;

import rpc.io.ObjectWritable;
import rpc.io.UTF8;
import rpc.io.Writable;

//import com.google.protobuf.GeneratedMessage;
//import com.google.protobuf.Message;
public class RPC {
	public static VersionedProtocol getProxy(Class<? extends VersionedProtocol> protocol, InetSocketAddress addr,
			int rpcTimeout){
		VersionedProtocol protocolImpl=null;
		protocolImpl=(VersionedProtocol)Proxy. newProxyInstance(
				protocol.getClassLoader(),
				new Class[]{protocol},
				new Invoker(protocol,addr,rpcTimeout));
		return protocolImpl;
	}
	
	public static Server getServer(VersionedProtocol instance,String bindAddress, int port){
		return new Server(instance,bindAddress,port);
	}
	


	/**
	 * 客户端通信代理组件
	 * @author 无言的雨
	 *
	 */
	private static class Invoker implements InvocationHandler { 
		private Client.ConnectionId remoteId;
		private Client client;

		public Invoker(Class<? extends VersionedProtocol> protocol, InetSocketAddress addr,
				int rpcTimeout) {
			super();
			this.remoteId=new Client.ConnectionId(protocol, addr, rpcTimeout);
			client=new Client();
		}

		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable { 
			client.setValueClass(method.getReturnType());//设置返回值的类型
			Writable value=client.call(remoteId,new Invocation(method,args));
			return value;
		}
	}

	public static class Invocation implements Serializable,Writable {
		private static final long serialVersionUID = 1L;
		private String methodName;
		private Class[] parameterClasses;
		private Object[] parameters;
		public Invocation() {
			super();
		}
		public Invocation(Method method, Object[] parameters) {
			super();
			this.methodName = method.getName();
			this.parameterClasses = method.getParameterTypes();
			this.parameters = parameters;
		}
		public String getMethodName() {
			return methodName;
		}
		public void setMethodName(String methodName) {
			this.methodName = methodName;
		}
		public Class[] getParameterClasses() {
			return parameterClasses;
		}
		public void setParameterClasses(Class[] parameterClasses) {
			this.parameterClasses = parameterClasses;
		}
		public Object[] getParameters() {
			return parameters;
		}
		public void setParameters(Object[] parameters) {
			this.parameters = parameters;
		}
		
		@Override
		public void write(DataOutput out) throws IOException {
			UTF8.writeString(out, methodName);
			out.writeInt(parameterClasses.length);
			for (int i = 0; i < parameterClasses.length; i++) {
				ObjectWritable.writeObject(out, parameters[i], parameterClasses[i]);
			}
		}
		@Override
		public void readFields(DataInput in) throws IOException {
			methodName = UTF8.readString(in);
			parameters = new Object[in.readInt()];
			parameterClasses = new Class[parameters.length];
			ObjectWritable objectWritable = new ObjectWritable();
			for (int i = 0; i < parameters.length; i++) {
				parameters[i] = ObjectWritable.readObject(in, objectWritable);
				parameterClasses[i] = objectWritable.getDeclaredClass();
			}
		}
		
//		@Override
//		public void write(DataOutput out) throws IOException {
//			UTF8.writeString(out, methodName);
//			out.writeInt(parameterClasses.length);
//			for (int i = 0; i < parameterClasses.length; i++) {
////				out.write(parameters[i].toByteArray())
//
//				Message a = (Message) parameters[0];
//				out.write(a.toByteArray());
//				 
////				ObjectWritable.writeObject(out, parameters[i], parameterClasses[i]);
//			}
//		}
//		@Override
//		public void readFields(DataInput in) throws IOException {
//			methodName = UTF8.readString(in);
//			parameters = new Object[in.readInt()];
//			parameterClasses = new Class[parameters.length];
//			ObjectWritable objectWritable = new ObjectWritable();
//			for (int i = 0; i < parameters.length; i++) {
//				parameters[i] = ObjectWritable.readObject(in, objectWritable);
//				parameterClasses[i] = objectWritable.getDeclaredClass();
//			}
//		}

	}
}
