<?php
include("../config.php");

$db = new DB_Connect();
$con = $db->connect();

$patientId = $_POST['patientId'];
$bloodPressure = $_POST['bloodPressure'];
$bloodSugar = $_POST['bloodSugar'];
$weight = $_POST['weight'];
$temperature = $_POST['temperature'];
$medicalPres = $_POST['medicalPres'];

$sql_query = "INSERT INTO tblmedicalhistory (`PatientID`, `BloodPressure`, `BloodSugar`, `Weight`, `Temperature`, `MedicalPres`) VALUES ('$patientId', '$bloodPressure', '$bloodSugar', '$weight', '$temperature', '$medicalPres')";

$res = mysqli_query($con, $sql_query);

$result = $res;

echo json_encode($result);

mysqli_close($con);
?>