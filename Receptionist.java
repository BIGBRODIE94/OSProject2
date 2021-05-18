import java.util.concurrent.Semaphore;
public class Receptionist {
private static Semaphore sem = new Semaphore(1, true); // declares a
// semaphore with
// 1 permit, as there is
// only 1 receptionist
private static Receptionist receptionistInstance = new Receptionist(); // One
// Receptionist
// Object
// only
private Receptionist() {
// make it private as we only have one receptionist
}
public static Receptionist getReceptionistInstance() {
return receptionistInstance;
}
// I have created two methods here because if there occurs some exception in
// the processing of doRegisterPatient method we are sure that we release
// the permit
public void registerPatient(Patient p) {
if(!p.isHasEntered() || p.isRegistered()) {
return;
}
try {
sem.acquire();
} catch (InterruptedException e) {
// TODO: handle exception
e.printStackTrace();
}
try {
doRegisterPatient(p);
} catch (InterruptedException e) {
// TODO Auto-generated catch block
e.printStackTrace();
} finally {
sem.release();
}
}
// Making this as private, as it is mend only for this class
private void doRegisterPatient(Patient p) throws InterruptedException {
System.out.println("Receptionist registers patient " + p.getId());
p.setRegistered(true);
System.out.println("Patient " + p.getId() + " leaves receptionist and sits in waiting room");
}
}