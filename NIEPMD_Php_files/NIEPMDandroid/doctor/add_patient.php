<?php
include("../config.php");

$db = new DB_Connect();
$con = $db->connect();

$docId = $_POST['docId'];
$patientName = $_POST['patientName'];
$patientContactNo = $_POST['patientContactNo'];
$patientEmail = $_POST['patientEmail'];
$patientGender = $_POST['patientGender'];
$patientAddress = $_POST['patientAddress'];
$patientAge = $_POST['patientAge'];
$patientMedicalHistory = $_POST['patientMedicalHistory'];

$sql_query = "INSERT INTO tblpatient (`Docid`, `PatientName`, `PatientContno`, `PatientEmail`, `PatientGender`, `PatientAdd`, `PatientAge`, `PatientMedhis`) VALUES ('$docId', '$patientName', '$patientContactNo', '$patientEmail', '$patientGender', '$patientAddress', '$patientAge', '$patientMedicalHistory')";

$res = mysqli_query($con, $sql_query);

$result = $res;

echo json_encode([["status"=>$result]]);

mysqli_close($con);
?>