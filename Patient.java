import java.util.concurrent.Semaphore;
public class Patient {
private int id;
private boolean isRegistered;
private boolean hasEntered;
private boolean isAdvised;
private static int counter = 0;
private static Semaphore sem = new Semaphore(3, true);
public Patient() {
this.id = ++counter;
this.isRegistered = false;
this.hasEntered = false;
this.isAdvised = false;
}
public int getId() {
return id;
}
public boolean isRegistered() {
return isRegistered;
}
public boolean isHasEntered() {
return hasEntered;
}
public void setHasEntered(boolean hasEntered) {
this.hasEntered = hasEntered;
}
public void setRegistered(boolean isRegistered) {
this.isRegistered = isRegistered;
}
public boolean isAdvised() {
return isAdvised;
}
public void setAdvised(boolean isAdvised) {
this.isAdvised = isAdvised;
}
public void enters() {
try {
sem.acquire();
System.out.println("Patient " + this.id + " enters waiting room, waits for receptionist");
this.hasEntered = true;
Thread.sleep(1000);
} catch (InterruptedException e) {
// TODO: handle exception
e.printStackTrace();
} finally {
sem.release();
}
}
}