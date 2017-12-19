

package state.core;


/** Base Yarn Exception.
 *
 * NOTE: All derivatives of this exception, which may be thrown by a remote
 * service, must include a String only constructor for the exception to be
 * unwrapped on the client.
 */
public class YarnRuntimeException extends RuntimeException {

  private static final long serialVersionUID = -7153142425412203936L;
  public YarnRuntimeException(Throwable cause) { super(cause); }
  public YarnRuntimeException(String message) { super(message); }
  public YarnRuntimeException(String message, Throwable cause) {
    super(message, cause);
  }
}
