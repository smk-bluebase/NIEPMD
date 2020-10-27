<?php
include("../config.php");

$db = new DB_Connect();
$con = $db->connect();

$id = $_POST['id'];
$patientName = $_POST['patientName'];
$patientContactNo = $_POST['patientContactNo'];
$patientEmail = $_POST['patientEmail'];
$patientGender = $_POST['patientGender'];
$patientAddress = $_POST['patientAddress'];
$patientAge = $_POST['patientAge'];
$patientMedicalHistory = $_POST['patientMedicalHistory'];

$sql_query = "UPDATE tblpatient SET PatientName = '$patientName', PatientContno = '$patientContactNo', PatientEmail = '$patientEmail', PatientGender = '$patientGender', PatientAdd= '$patientAddress', PatientAge = '$patientAge', PatientMedhis = '$patientMedicalHistory' WHERE ID = '$id'";

$res = mysqli_query($con, $sql_query);

$result = $res;

echo json_encode([["status"=>$result]]);

mysqli_close($con);
?>