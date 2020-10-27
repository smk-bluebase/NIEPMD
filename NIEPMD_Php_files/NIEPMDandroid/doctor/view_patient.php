<?php
include("../config.php");

$db = new DB_Connect();
$con = $db->connect();

$id = $_POST['id'];

$sql_query = "SELECT * FROM tblpatient WHERE ID = '$id'";

$res = mysqli_query($con, $sql_query);

$result = [];

while($row = mysqli_fetch_array($res)){
    $result['tblpatient'] = array("id"=>$row['ID'],
        "docId"=>$row['Docid'],
        "patientName"=>$row['PatientName'],
        "patientContactNo"=>$row['PatientContno'],
        "patientEmail"=>$row['PatientEmail'],
        "patientGender"=>$row['PatientGender'],
        "patientAddress"=>$row['PatientAdd'],
        "patientAge"=>$row['PatientAge'],
        "patientMedicalHistory"=>$row['PatientMedhis'],
        "creationDate"=>$row['CreationDate'],
        "updationDate"=>$row['UpdationDate']);
}

$sql_query = "SELECT * FROM tblmedicalhistory WHERE PatientID = '$id'";

$res = mysqli_query($con, $sql_query);

while($row = mysqli_fetch_array($res)){
    $result['tblmedicalhistory'][] = array("id"=>$row['ID'],
        "patientId"=>$row['PatientID'],
        "bloodPressure"=>$row['BloodPressure'],
        "bloodSugar"=>$row['BloodSugar'],
        "weight"=>$row['Weight'],
        "temperature"=>$row['Temperature'],
        "medicalPres"=>$row['MedicalPres'],
        "creationDate"=>$row['CreationDate']);
}

echo json_encode($result);

mysqli_close($con);
?>