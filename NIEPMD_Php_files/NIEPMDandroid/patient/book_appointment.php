<?php
include("../config.php");

$db = new DB_Connect();
$con = $db->connect();

$doctorSpecialization = $_POST["doctorSpecialization"];
$doctorId = $_POST["doctorId"];
$userId = $_POST["userId"];
$consultancyFees = $_POST["consultancyFees"];
$appointmentDate = $_POST["appointmentDate"];
$appointmentTime = $_POST["appointmentTime"];
$userStatus = 1;
$doctorStatus = 1;

$sql_query = "INSERT INTO appointment (doctorSpecialization, doctorId, userId, consultancyFees, appointmentDate, appointmentTime, userStatus, doctorStatus) VALUES ('".$doctorSpecialization."', '".$doctorId."', '".$userId."', '".$consultancyFees."', '".$appointmentDate."', '".$appointmentTime."', '".$userStatus."', '".$doctorStatus."')";

$res = mysqli_query($con, $sql_query);

$result = $res;

echo json_encode([["status"=>$result]]);

mysqli_close($con);
?>