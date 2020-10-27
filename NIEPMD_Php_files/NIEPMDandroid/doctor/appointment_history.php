<?php
include("../config.php");

$db = new DB_Connect();
$con = $db->connect();

$doctorId = $_POST['doctorId'];

$sql_query = "SELECT appointment.id as appointmentId, appointment.*, users.* FROM appointment JOIN users ON appointment.userId = users.id WHERE appointment.doctorId = '$doctorId'";

$res = mysqli_query($con, $sql_query);

$result = [];

while($row = mysqli_fetch_array($res)){
    $result[] = array("appointmentId"=>$row['appointmentId'],
        "fullName"=>$row['fullName'],
        "doctorSpecialization"=>$row['doctorSpecialization'],
        "consultancyFees"=>$row['consultancyFees'],
        "appointmentDate"=>$row['appointmentDate'],
        "appointmentTime"=>$row['appointmentTime'],
        "postingDate"=>$row['postingDate'],
        "userStatus"=>$row['userStatus'],
        "doctorStatus"=>$row['doctorStatus']);
}

echo json_encode($result);

mysqli_close($con);
?>