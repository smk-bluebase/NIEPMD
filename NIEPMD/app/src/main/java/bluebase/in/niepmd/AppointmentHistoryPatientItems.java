package bluebase.in.niepmd;

class AppointmentHistoryPatientItems {
    private int id;
    private String doctorName;
    private String doctorSpecialization;
    private String consultancyFees;
    private String appointmentDate;
    private String appointmentTime;
    private String postingDate;
    private String userStatus;
    private String doctorStatus;

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDoctorSpecialization() {
        return doctorSpecialization;
    }

    public void setDoctorSpecialization(String doctorSpecialization) {
        this.doctorSpecialization = doctorSpecialization;
    }

    public String getConsultancyFees() {
        return consultancyFees;
    }

    public void setConsultancyFees(String consultancyFees) {
        this.consultancyFees = consultancyFees;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(String appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(String appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public String getPostingDate() {
        return postingDate;
    }

    public void setPostingDate(String postingDate) {
        this.postingDate = postingDate;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public String getDoctorStatus() {
        return doctorStatus;
    }

    public void setDoctorStatus(String doctorStatus) {
        this.doctorStatus = doctorStatus;
    }
}
