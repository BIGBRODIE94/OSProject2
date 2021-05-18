import java.util.concurrent.Semaphore;
public class Doctor {
private static Semaphore sem = new Semaphore(3, true); // A semphore of 3,
// as the maximum
// number of doctors
// available is only
// 3, make this
// semaphore as
// static as we want
// it to be used by
// all doctors
private int id = 0;
private Nurse nurse;
private boolean isBusy;
private static int counter = 0;
public Doctor(int id) {
this.id = ++counter;
this.nurse = new Nurse(counter); // making the nurse's id same as
// doctor's id
this.isBusy = false;
}
public boolean isBusy() {
return isBusy;
}
public void advise(Patient p) throws InterruptedException {
if(!p.isRegistered() || p.isAdvised())
return;
try {
sem.acquire();
this.isBusy = true;
this.nurse.takesPatient(p);
Thread.sleep(100);
System.out.println("Patient " + p.getId() + " enters doctor " + this.id + "'s office");
System.out.println("Doctor " + this.id + " listens to symptoms from patient "+ p.getId());
Thread.sleep(200);
System.out.println("Patient " + p.getId() + " receives advice from doctor" + this.id);
System.out.println("Patient " + p.getId() + " leaves");
p.setAdvised(true);
this.isBusy = false;
} catch (InterruptedException e) {
e.printStackTrace();
}
finally {
sem.release();
}
}
}
