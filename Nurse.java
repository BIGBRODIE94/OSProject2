public class Nurse {
private int id;
public Nurse(int id) {
this.id = id;
}
public int getId() {
return id;
}
public void takesPatient(Patient p) {
System.out.println("Nurse " + this.id + " takes patient " + p.getId() + " to doctor's office");
}
}
