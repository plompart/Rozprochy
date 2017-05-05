package server;

import com.google.protobuf.Any;
import grpc.*;
import grpc.HospitalGrpc.HospitalImplBase;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by Patryk on 2017-05-04.
 */

public class HospitalImpl extends HospitalImplBase {
    String[] mainExamsSet = {"cholesterol", "ALT", "AST", "potassium", "sodium"};
    String[] minorExamsSet = {"WBC", "RBC", "HGB", "HCT"};

    @Override
    public void addExamination(ExaminationRequest er, StreamObserver<Result> so){
        System.out.println("Got request from: " + er.getClientName());
        try{
            if(!er.getClientType().equals(ClientType.WORKER))throw new Exception("Wrong type of client");
            FileWriter fw = new FileWriter("data.txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            Examination any = er.getArguments(0).unpack(Examination.class);
            String examination = "";
            examination += any.getPatientName() + " ";
            examination += any.getDoctorName() + " ";
            examination += any.getWorkerName() + " ";
            if(any.getDate().matches("\\d{4}-\\d{2}-\\d{2}")){
                examination += any.getDate() + " ";
            }else throw new Exception("Wrong date format");
            Map<String, Integer> mainExams = any.getMainExamsMap();
            Map<String, Double> minorExams = any.getMinorExamsMap();
            for (String key : mainExamsSet) {
                examination += mainExams.get(key) + " ";
            }
            for (String key : minorExamsSet) {
                examination += minorExams.get(key) + " ";
            }
            pw.println(examination);
            pw.close();
            bw.close();
            fw.close();
            Result result = Result.newBuilder().setResult(true).build();
            so.onNext(result);
            so.onCompleted();
        }catch(Exception e){
            so.onError(new StatusRuntimeException(Status.PERMISSION_DENIED.withDescription(e.getMessage())));
            e.printStackTrace();
        }
    }

    @Override
    public void getExamination(ExaminationRequest er, StreamObserver<Examination> so){
        System.out.println("Got request from: " + er.getClientName());
        try{
            PatientName any = er.getArguments(0).unpack(PatientName.class);
            if(er.getClientType().equals(ClientType.WORKER)){
                throw new Exception("Wrong type of client");
            }else if(!er.getClientName().equals(any.getPatientName()) && er.getClientType().equals(ClientType.PATIENT)){
                throw new Exception("Wrong type of client");
            }
            File file = new File("data.txt");
            Scanner scanner = new Scanner(file);
            String line = null;
            String[] parts;

            while(true){
                line = scanner.nextLine();
                parts = line.split(" ");
                if(parts[0].equals(any.getPatientName())){
                    break;
                }
            }
            Examination examination = createExamination(parts);
            so.onNext(examination);
            so.onCompleted();
        } catch (Exception e) {
            so.onError(new StatusRuntimeException(Status.PERMISSION_DENIED.withDescription(e.getMessage())));
            e.printStackTrace();
        }
    }

    @Override
    public void getAllExaminations(ExaminationRequest er, StreamObserver<Examination> so){
        System.out.println("Got request from: " + er.getClientName());
        try{
            if(!er.getClientType().equals(ClientType.DOCTOR)){
                throw new Exception("Wrong type of client");
            }
            File file = new File("data.txt");
            Scanner scanner = new Scanner(file);
            String line = null;
            String[] parts;

            while(scanner.hasNext()){
                line = scanner.nextLine();
                parts = line.split(" ");
                Examination examination = createExamination(parts);
                so.onNext(examination);
            }
            so.onCompleted();
        } catch (Exception e) {
            so.onError(new StatusRuntimeException(Status.PERMISSION_DENIED.withDescription(e.getMessage())));
            e.printStackTrace();
        }
    }

    @Override
    public void getAllExaminationsForDoctor(ExaminationRequest er, StreamObserver<Examination> so){
        System.out.println("Got request from: " + er.getClientName());
        try{
            if(!er.getClientType().equals(ClientType.DOCTOR)){
                throw new Exception("Wrong type of client");
            }
            File file = new File("data.txt");
            Scanner scanner = new Scanner(file);
            String line = null;
            String[] parts;

            while(scanner.hasNext()){
                line = scanner.nextLine();
                parts = line.split(" ");
                if(parts[1].equals(er.getClientName())){
                    Examination examination = createExamination(parts);
                    so.onNext(examination);
                }
            }
            so.onCompleted();
        } catch (Exception e) {
            so.onError(new StatusRuntimeException(Status.PERMISSION_DENIED.withDescription(e.getMessage())));
            e.printStackTrace();
        }
    }

    @Override
    public void getExaminationsWithBiggerCholesterol(ExaminationRequest er, StreamObserver<Examination> so){
        System.out.println("Got request from: " + er.getClientName());
        try{
            if(!er.getClientType().equals(ClientType.DOCTOR)){
                throw new Exception("Wrong type of client");
            }
            Cholesterol any = er.getArguments(0).unpack(Cholesterol.class);
            File file = new File("data.txt");
            Scanner scanner = new Scanner(file);
            String line = null;
            String[] parts;

            while(scanner.hasNext()){
                line = scanner.nextLine();
                parts = line.split(" ");
                if(Integer.parseInt(parts[4]) > (any.getCholesterol())){
                    Examination examination = createExamination(parts);
                    so.onNext(examination);
                }
            }
            so.onCompleted();
        } catch (Exception e) {
            so.onError(new StatusRuntimeException(Status.PERMISSION_DENIED.withDescription(e.getMessage())));
            e.printStackTrace();
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
}
