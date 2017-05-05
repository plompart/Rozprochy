package client;

import com.google.protobuf.Any;
import grpc.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.io.BufferedReader;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by Patryk on 2017-05-05.
 */

public class HospitalClient {
    private final ManagedChannel channel;
    private final HospitalGrpc.HospitalStub hospNonBlockingStub;
    private final HospitalGrpc.HospitalBlockingStub hospBlockingStub;
    private String clientName;
    private ClientType clientType;

    public HospitalClient(String host, int port) {
        channel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext(true)
                .build();

        hospBlockingStub = HospitalGrpc.newBlockingStub(channel);
        hospNonBlockingStub = HospitalGrpc.newStub(channel);
    }

    public static void main(String[] args) throws Exception {
        HospitalClient client = new HospitalClient("localhost", 12345);
        client.getID();
        client.loop();
    }

    private void getID() throws InterruptedException {
        String line = null;
        String[] parts;
        BufferedReader in = new BufferedReader(new java.io.InputStreamReader(System.in));
        try {
            System.out.print("Enter your ID: ");
            System.out.flush();
            line = in.readLine();
            parts = line.split("");
            if (parts[0].equals("d") && parts[1].equals("_")) {
                clientType = ClientType.DOCTOR;
            }else if(parts[0].equals("w") && parts[1].equals("_")){
                clientType = ClientType.WORKER;
            }else{
                clientType = ClientType.PATIENT;
            }
            clientName = line;
        } catch (java.io.IOException ex) {
            System.err.println(ex);
        }
    }

    private void loop()  throws InterruptedException {
        try {
            String line = null;
            String[] parts;
            BufferedReader in = new BufferedReader(new java.io.InputStreamReader(System.in));
            do {
                try {
                    System.out.println("MENU: ");
                    System.out.println("1.add exam");
                    System.out.println("2.get exam");
                    System.out.println("3.get all exams");
                    System.out.println("4.get all exams for doctor");
                    System.out.println("5.get exams with bigger cholesterol");
                    System.out.println("6.exit");
                    System.out.flush();
                    line = in.readLine();
                    if (line == null) {
                        continue;
                    }
                    if(line.equals("1")){
                        System.out.println("Write fields in given order:");
                        System.out.println("PatientName DoctorName WorkerName Date cholesterol ALT AST potassium sodium WBC RBC HGB HCT");
                        line = in.readLine();
                        parts = line.split(" ");
                        Examination examination = createExamination(parts);

                        ExaminationRequest request = ExaminationRequest
                                .newBuilder()
                                .setClientType(clientType)
                                .setClientName(clientName)
                                .addArguments(Any.pack(examination))
                                .build();

                        Result result = hospBlockingStub.addExamination(request);
                        System.out.println("Result: " + result.getResult());
                    }else if (line.equals("2")) {
                        System.out.println("Enter name of patient: ");
                        line = in.readLine();
                        PatientName patientName = PatientName.newBuilder().setPatientName(line).build();

                        ExaminationRequest request = ExaminationRequest
                                .newBuilder()
                                .setClientType(clientType)
                                .setClientName(clientName)
                                .addArguments(Any.pack(patientName))
                                .build();
                        Examination result = hospBlockingStub.getExamination(request);
                        readExamination(result);
                    }else if (line.equals("3")){
                        StreamObserver<Examination> responseObserver = new StreamObserver<Examination>() {
                            @Override
                            public void onNext(Examination result) {
                                readExamination(result);
                            }

                            @Override
                            public void onError(Throwable t) {
                                t.printStackTrace();
                            }

                            @Override
                            public void onCompleted() {
                                System.out.println("All examinations found");
                            }
                        };

                        ExaminationRequest request = ExaminationRequest
                                .newBuilder()
                                .setClientType(clientType)
                                .setClientName(clientName)
                                .build();

                        hospNonBlockingStub.getAllExaminations(request, responseObserver);
                    }else if(line.equals("4")){
                        StreamObserver<Examination> responseObserver = new StreamObserver<Examination>() {
                            @Override
                            public void onNext(Examination result) {
                                readExamination(result);
//                                try{
//                                    Thread.sleep(1000);
//                                }catch(InterruptedException e){
//                                    e.printStackTrace();
//                                }
                            }

                            @Override
                            public void onError(Throwable t) {
                                t.printStackTrace();
                            }

                            @Override
                            public void onCompleted() {
                                System.out.println("All examinations found");
                            }
                        };

                        ExaminationRequest request = ExaminationRequest
                                .newBuilder()
                                .setClientType(clientType)
                                .setClientName(clientName)
                                .build();

                        hospNonBlockingStub.getAllExaminationsForDoctor(request, responseObserver);
                    }else if(line.equals("5")){
                        StreamObserver<Examination> responseObserver = new StreamObserver<Examination>() {
                            @Override
                            public void onNext(Examination result) {
                                readExamination(result);
                            }

                            @Override
                            public void onError(Throwable t) {
                                t.printStackTrace();
                            }

                            @Override
                            public void onCompleted() {
                                System.out.println("All examinations found");
                            }
                        };

                        System.out.println("Set level of cholesterol: ");
                        line = in.readLine();
                        Cholesterol cholesterol = Cholesterol.newBuilder().setCholesterol(Integer.parseInt(line)).build();
                        ExaminationRequest request = ExaminationRequest
                                .newBuilder()
                                .setClientType(clientType)
                                .setClientName(clientName)
                                .addArguments(Any.pack(cholesterol))
                                .build();

                        hospNonBlockingStub.getExaminationsWithBiggerCholesterol(request, responseObserver);
                    }
                } catch (java.io.IOException ex) {
                    System.err.println(ex);
                }
            }
            while (!line.equals("6"));
        } finally {
            shutdown();
        }
    }

    private Examination createExamination(String[] parts) {
        String[] mainExams = {"cholesterol", "ALT", "AST", "potassium", "sodium"};
        String[] minorExams = {"WBC", "RBC", "HGB", "HCT"};
        int counter = 0;
        Examination.Builder examination = Examination
                .newBuilder()
                .setPatientName(parts[counter++])
                .setDoctorName(parts[counter++])
                .setWorkerName(parts[counter++])
                .setDate(parts[counter++]);

        for(int i = 0; i < mainExams.length; i++){
            examination.putMainExams(mainExams[i],Integer.parseInt(parts[counter++]));
        }

        for(int i = 0; i < minorExams.length; i++){
            examination.putMinorExams(minorExams[i],Double.parseDouble(parts[counter++]));
        }

        return examination.build();
    }

    private void readExamination(Examination examination) {
        System.out.print("Result: ");
        Map<String,Integer> mainExams = examination.getMainExamsMap();
        Map<String,Double> minorExams = examination.getMinorExamsMap();
        System.out.print("Patient:" + examination.getPatientName() + " ");
        System.out.print("Doctor:" + examination.getDoctorName() + " ");
        System.out.print("Worker:" + examination.getWorkerName() + " ");
        System.out.print(examination.getDate() + " ");
        mainExams.forEach((key, value) -> {
            System.out.print(key + ":" + value + " ");
        });
        minorExams.forEach((key, value) -> {
            System.out.print(key + ":" + value + " ");
        });
        System.out.println();
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }
}
