package grpc;

import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.2.0)",
    comments = "Source: hospital.proto")
public final class HospitalGrpc {

  private HospitalGrpc() {}

  public static final String SERVICE_NAME = "hospital.Hospital";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<grpc.ExaminationRequest,
      grpc.Result> METHOD_ADD_EXAMINATION =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "hospital.Hospital", "AddExamination"),
          io.grpc.protobuf.ProtoUtils.marshaller(grpc.ExaminationRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(grpc.Result.getDefaultInstance()));
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<grpc.ExaminationRequest,
      grpc.Examination> METHOD_GET_EXAMINATION =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "hospital.Hospital", "GetExamination"),
          io.grpc.protobuf.ProtoUtils.marshaller(grpc.ExaminationRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(grpc.Examination.getDefaultInstance()));
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<grpc.ExaminationRequest,
      grpc.Examination> METHOD_GET_ALL_EXAMINATIONS =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING,
          generateFullMethodName(
              "hospital.Hospital", "GetAllExaminations"),
          io.grpc.protobuf.ProtoUtils.marshaller(grpc.ExaminationRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(grpc.Examination.getDefaultInstance()));
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<grpc.ExaminationRequest,
      grpc.Examination> METHOD_GET_ALL_EXAMINATIONS_FOR_DOCTOR =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING,
          generateFullMethodName(
              "hospital.Hospital", "GetAllExaminationsForDoctor"),
          io.grpc.protobuf.ProtoUtils.marshaller(grpc.ExaminationRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(grpc.Examination.getDefaultInstance()));
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<grpc.ExaminationRequest,
      grpc.Examination> METHOD_GET_EXAMINATIONS_WITH_BIGGER_CHOLESTEROL =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING,
          generateFullMethodName(
              "hospital.Hospital", "GetExaminationsWithBiggerCholesterol"),
          io.grpc.protobuf.ProtoUtils.marshaller(grpc.ExaminationRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(grpc.Examination.getDefaultInstance()));

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static HospitalStub newStub(io.grpc.Channel channel) {
    return new HospitalStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static HospitalBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new HospitalBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary and streaming output calls on the service
   */
  public static HospitalFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new HospitalFutureStub(channel);
  }

  /**
   */
  public static abstract class HospitalImplBase implements io.grpc.BindableService {

    /**
     */
    public void addExamination(grpc.ExaminationRequest request,
        io.grpc.stub.StreamObserver<grpc.Result> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_ADD_EXAMINATION, responseObserver);
    }

    /**
     */
    public void getExamination(grpc.ExaminationRequest request,
        io.grpc.stub.StreamObserver<grpc.Examination> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_EXAMINATION, responseObserver);
    }

    /**
     */
    public void getAllExaminations(grpc.ExaminationRequest request,
        io.grpc.stub.StreamObserver<grpc.Examination> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_ALL_EXAMINATIONS, responseObserver);
    }

    /**
     */
    public void getAllExaminationsForDoctor(grpc.ExaminationRequest request,
        io.grpc.stub.StreamObserver<grpc.Examination> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_ALL_EXAMINATIONS_FOR_DOCTOR, responseObserver);
    }

    /**
     */
    public void getExaminationsWithBiggerCholesterol(grpc.ExaminationRequest request,
        io.grpc.stub.StreamObserver<grpc.Examination> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_EXAMINATIONS_WITH_BIGGER_CHOLESTEROL, responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_ADD_EXAMINATION,
            asyncUnaryCall(
              new MethodHandlers<
                grpc.ExaminationRequest,
                grpc.Result>(
                  this, METHODID_ADD_EXAMINATION)))
          .addMethod(
            METHOD_GET_EXAMINATION,
            asyncUnaryCall(
              new MethodHandlers<
                grpc.ExaminationRequest,
                grpc.Examination>(
                  this, METHODID_GET_EXAMINATION)))
          .addMethod(
            METHOD_GET_ALL_EXAMINATIONS,
            asyncServerStreamingCall(
              new MethodHandlers<
                grpc.ExaminationRequest,
                grpc.Examination>(
                  this, METHODID_GET_ALL_EXAMINATIONS)))
          .addMethod(
            METHOD_GET_ALL_EXAMINATIONS_FOR_DOCTOR,
            asyncServerStreamingCall(
              new MethodHandlers<
                grpc.ExaminationRequest,
                grpc.Examination>(
                  this, METHODID_GET_ALL_EXAMINATIONS_FOR_DOCTOR)))
          .addMethod(
            METHOD_GET_EXAMINATIONS_WITH_BIGGER_CHOLESTEROL,
            asyncServerStreamingCall(
              new MethodHandlers<
                grpc.ExaminationRequest,
                grpc.Examination>(
                  this, METHODID_GET_EXAMINATIONS_WITH_BIGGER_CHOLESTEROL)))
          .build();
    }
  }

  /**
   */
  public static final class HospitalStub extends io.grpc.stub.AbstractStub<HospitalStub> {
    private HospitalStub(io.grpc.Channel channel) {
      super(channel);
    }

    private HospitalStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected HospitalStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new HospitalStub(channel, callOptions);
    }

    /**
     */
    public void addExamination(grpc.ExaminationRequest request,
        io.grpc.stub.StreamObserver<grpc.Result> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_ADD_EXAMINATION, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getExamination(grpc.ExaminationRequest request,
        io.grpc.stub.StreamObserver<grpc.Examination> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GET_EXAMINATION, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getAllExaminations(grpc.ExaminationRequest request,
        io.grpc.stub.StreamObserver<grpc.Examination> responseObserver) {
      asyncServerStreamingCall(
          getChannel().newCall(METHOD_GET_ALL_EXAMINATIONS, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getAllExaminationsForDoctor(grpc.ExaminationRequest request,
        io.grpc.stub.StreamObserver<grpc.Examination> responseObserver) {
      asyncServerStreamingCall(
          getChannel().newCall(METHOD_GET_ALL_EXAMINATIONS_FOR_DOCTOR, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getExaminationsWithBiggerCholesterol(grpc.ExaminationRequest request,
        io.grpc.stub.StreamObserver<grpc.Examination> responseObserver) {
      asyncServerStreamingCall(
          getChannel().newCall(METHOD_GET_EXAMINATIONS_WITH_BIGGER_CHOLESTEROL, getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class HospitalBlockingStub extends io.grpc.stub.AbstractStub<HospitalBlockingStub> {
    private HospitalBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private HospitalBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected HospitalBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new HospitalBlockingStub(channel, callOptions);
    }

    /**
     */
    public grpc.Result addExamination(grpc.ExaminationRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_ADD_EXAMINATION, getCallOptions(), request);
    }

    /**
     */
    public grpc.Examination getExamination(grpc.ExaminationRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GET_EXAMINATION, getCallOptions(), request);
    }

    /**
     */
    public java.util.Iterator<grpc.Examination> getAllExaminations(
        grpc.ExaminationRequest request) {
      return blockingServerStreamingCall(
          getChannel(), METHOD_GET_ALL_EXAMINATIONS, getCallOptions(), request);
    }

    /**
     */
    public java.util.Iterator<grpc.Examination> getAllExaminationsForDoctor(
        grpc.ExaminationRequest request) {
      return blockingServerStreamingCall(
          getChannel(), METHOD_GET_ALL_EXAMINATIONS_FOR_DOCTOR, getCallOptions(), request);
    }

    /**
     */
    public java.util.Iterator<grpc.Examination> getExaminationsWithBiggerCholesterol(
        grpc.ExaminationRequest request) {
      return blockingServerStreamingCall(
          getChannel(), METHOD_GET_EXAMINATIONS_WITH_BIGGER_CHOLESTEROL, getCallOptions(), request);
    }
  }

  /**
   */
  public static final class HospitalFutureStub extends io.grpc.stub.AbstractStub<HospitalFutureStub> {
    private HospitalFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private HospitalFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected HospitalFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new HospitalFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<grpc.Result> addExamination(
        grpc.ExaminationRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_ADD_EXAMINATION, getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<grpc.Examination> getExamination(
        grpc.ExaminationRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GET_EXAMINATION, getCallOptions()), request);
    }
  }

  private static final int METHODID_ADD_EXAMINATION = 0;
  private static final int METHODID_GET_EXAMINATION = 1;
  private static final int METHODID_GET_ALL_EXAMINATIONS = 2;
  private static final int METHODID_GET_ALL_EXAMINATIONS_FOR_DOCTOR = 3;
  private static final int METHODID_GET_EXAMINATIONS_WITH_BIGGER_CHOLESTEROL = 4;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final HospitalImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(HospitalImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_ADD_EXAMINATION:
          serviceImpl.addExamination((grpc.ExaminationRequest) request,
              (io.grpc.stub.StreamObserver<grpc.Result>) responseObserver);
          break;
        case METHODID_GET_EXAMINATION:
          serviceImpl.getExamination((grpc.ExaminationRequest) request,
              (io.grpc.stub.StreamObserver<grpc.Examination>) responseObserver);
          break;
        case METHODID_GET_ALL_EXAMINATIONS:
          serviceImpl.getAllExaminations((grpc.ExaminationRequest) request,
              (io.grpc.stub.StreamObserver<grpc.Examination>) responseObserver);
          break;
        case METHODID_GET_ALL_EXAMINATIONS_FOR_DOCTOR:
          serviceImpl.getAllExaminationsForDoctor((grpc.ExaminationRequest) request,
              (io.grpc.stub.StreamObserver<grpc.Examination>) responseObserver);
          break;
        case METHODID_GET_EXAMINATIONS_WITH_BIGGER_CHOLESTEROL:
          serviceImpl.getExaminationsWithBiggerCholesterol((grpc.ExaminationRequest) request,
              (io.grpc.stub.StreamObserver<grpc.Examination>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static final class HospitalDescriptorSupplier implements io.grpc.protobuf.ProtoFileDescriptorSupplier {
    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return grpc.HospitalProto.getDescriptor();
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (HospitalGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new HospitalDescriptorSupplier())
              .addMethod(METHOD_ADD_EXAMINATION)
              .addMethod(METHOD_GET_EXAMINATION)
              .addMethod(METHOD_GET_ALL_EXAMINATIONS)
              .addMethod(METHOD_GET_ALL_EXAMINATIONS_FOR_DOCTOR)
              .addMethod(METHOD_GET_EXAMINATIONS_WITH_BIGGER_CHOLESTEROL)
              .build();
        }
      }
    }
    return result;
  }
}
