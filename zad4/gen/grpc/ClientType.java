// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: hospital.proto

package grpc;

/**
 * Protobuf enum {@code hospital.ClientType}
 */
public enum ClientType
    implements com.google.protobuf.ProtocolMessageEnum {
  /**
   * <code>PATIENT = 0;</code>
   */
  PATIENT(0),
  /**
   * <code>DOCTOR = 1;</code>
   */
  DOCTOR(1),
  /**
   * <code>WORKER = 2;</code>
   */
  WORKER(2),
  UNRECOGNIZED(-1),
  ;

  /**
   * <code>PATIENT = 0;</code>
   */
  public static final int PATIENT_VALUE = 0;
  /**
   * <code>DOCTOR = 1;</code>
   */
  public static final int DOCTOR_VALUE = 1;
  /**
   * <code>WORKER = 2;</code>
   */
  public static final int WORKER_VALUE = 2;


  public final int getNumber() {
    if (this == UNRECOGNIZED) {
      throw new java.lang.IllegalArgumentException(
          "Can't get the number of an unknown enum value.");
    }
    return value;
  }

  /**
   * @deprecated Use {@link #forNumber(int)} instead.
   */
  @java.lang.Deprecated
  public static ClientType valueOf(int value) {
    return forNumber(value);
  }

  public static ClientType forNumber(int value) {
    switch (value) {
      case 0: return PATIENT;
      case 1: return DOCTOR;
      case 2: return WORKER;
      default: return null;
    }
  }

  public static com.google.protobuf.Internal.EnumLiteMap<ClientType>
      internalGetValueMap() {
    return internalValueMap;
  }
  private static final com.google.protobuf.Internal.EnumLiteMap<
      ClientType> internalValueMap =
        new com.google.protobuf.Internal.EnumLiteMap<ClientType>() {
          public ClientType findValueByNumber(int number) {
            return ClientType.forNumber(number);
          }
        };

  public final com.google.protobuf.Descriptors.EnumValueDescriptor
      getValueDescriptor() {
    return getDescriptor().getValues().get(ordinal());
  }
  public final com.google.protobuf.Descriptors.EnumDescriptor
      getDescriptorForType() {
    return getDescriptor();
  }
  public static final com.google.protobuf.Descriptors.EnumDescriptor
      getDescriptor() {
    return grpc.HospitalProto.getDescriptor().getEnumTypes().get(0);
  }

  private static final ClientType[] VALUES = values();

  public static ClientType valueOf(
      com.google.protobuf.Descriptors.EnumValueDescriptor desc) {
    if (desc.getType() != getDescriptor()) {
      throw new java.lang.IllegalArgumentException(
        "EnumValueDescriptor is not for this type.");
    }
    if (desc.getIndex() == -1) {
      return UNRECOGNIZED;
    }
    return VALUES[desc.getIndex()];
  }

  private final int value;

  private ClientType(int value) {
    this.value = value;
  }

  // @@protoc_insertion_point(enum_scope:hospital.ClientType)
}

