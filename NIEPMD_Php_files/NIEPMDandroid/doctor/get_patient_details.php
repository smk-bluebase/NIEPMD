<?php
include("../config.php");

$db = new DB_Connect();
$con = $db->connect();

$id = $_POST['id'];

$sql_query = "SELECT * FROM tblpatient WHERE ID = '".$id."'";

$res = mysqli_query($con, $sql_query);

$result = [];

while($row = mysqli_fetch_array($res)){
    $result[] = array("id"=>$row['ID'],
        "patientName"=>$row['PatientName'],
        "contactNo"=>$row['PatientContno'],
        "patientEmail"=>$row['PatientEmail'],
        "patientGender"=>$row['PatientGender'],
        "patientAddress"=>$row['PatientAdd'],
        "patientAge"=>$row['PatientAge'],
        "medicalHistory"=>$row['PatientMedhis']);
}

echo json_encode($result);

mysqli_close($con);
?>