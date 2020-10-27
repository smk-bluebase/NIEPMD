<?php
include("../config.php");

$db = new DB_Connect();
$con = $db->connect();

$userId = $_POST['userId'];

$sql_query = "SELECT doctors.doctorName as doctorName, appointment.* FROM appointment JOIN doctors ON doctors.id=appointment.doctorId WHERE appointment.userId = '".$userId."'";

$res = mysqli_query($con, $sql_query);

$result = [];

while($row = mysqli_fetch_array($res)){
    $result[] = array("appointmentId"=>$row['id'],
        "doctorName"=>$row['doctorName'],
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