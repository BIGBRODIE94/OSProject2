import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
public class App {
public static void main(String[] args) throws InterruptedException {
ArrayList<Doctor> doctors = new ArrayList<>();
ArrayList<Patient> patients = new ArrayList<>();
int doctorsCounter = 0;
// Creating 10 Patients
for (int i = 0; i < 10; i++) {
patients.add(new Patient());
}
// Creating 3 Doctors
for (int i = 0; i < 3; i++) {
doctors.add(new Doctor(++doctorsCounter));
}
// Patients Thread..
ExecutorService patientThreads = Executors.newCachedThreadPool();
for (int i = 0; i < patients.size(); i++) {
 int index = i ;
patientThreads.execute(new Runnable() {
@Override
public void run() {
patients.get(index).enters();
}
});
}
patientThreads.shutdown(); // will shutdown patientThreads executor,
// once all task have run.., but the main
// thread will not stop at this point and
// will continue..
ExecutorService doctorThreads = Executors.newCachedThreadPool();
ExecutorService registerPatients = Executors.newCachedThreadPool();
// We make threads until all patients have entered and advised..
while (true) {
for (int i = 0; i < patients.size(); i++) {
 Patient patient = patients.get(i);
registerPatients.execute(new Runnable() {
@Override
public void run() {
Receptionist.getReceptionistInstance().registerPatient(patient);
}
});
doctorThreads.execute(new Runnable() {
@Override
public void run() {
if (!doctors.get(0).isBusy()) {
try {
doctors.get(0).advise(patient);
} catch (InterruptedException e) {
// TODO Auto-generated catch block
e.printStackTrace();
}
} else if (!doctors.get(1).isBusy()) {
try {
doctors.get(1).advise(patient);
} catch (InterruptedException e) {
// TODO Auto-generated catch block
e.printStackTrace();
}
} else if (!doctors.get(2).isBusy()) {
try {
doctors.get(2).advise(patient);
} catch (InterruptedException e) {
// TODO Auto-generated catch block
e.printStackTrace();
}
} else {
return;
}
}
});
}
if (allPatientsEntered(patients) && allPatientsAdvised(patients)) {
break;
}
Thread.sleep(2000);
}
registerPatients.shutdown();
doctorThreads.shutdown();
patientThreads.awaitTermination(1, TimeUnit.DAYS);
}
private static boolean allPatientsAdvised(ArrayList<Patient> patients) {
for (Patient patient : patients) {
if (!patient.isAdvised()) {
return false;
}
}
return true;
}
// return false if any of the patient is still to enter..
private static boolean allPatientsEntered(ArrayList<Patient> patients) {
for (Patient patient : patients) {
if (!patient.isHasEntered()) {
return false;
}
}
return true;
}
}
