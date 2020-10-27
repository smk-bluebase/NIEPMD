<?php
include('../config.php');

$db = new DB_Connect();
$con = $db->connect();

$viewId = $_POST['viewId'];

$sql_query = "SELECT * FROM tblpatient WHERE ID = '".$viewId."'";

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
        "creationDate"=>$row['CreationDate']);
}

$sql_query = "SELECT * FROM tblmedicalhistory WHERE PatientID = '".$viewId."'";

$res = mysqli_query($con, $sql_query);

while($row = mysqli_fetch_array($res)){
    $result['tblmedicalhistory'][] = array("bloodPressure"=>$row['BloodPressure'],
        "bloodSugar"=>$row['BloodSugar'],
        "weight"=>$row['Weight'],
        "temperature"=>$row['Temperature'],
        "medicalPrescription"=>$row['MedicalPres'],
        "creationDate"=>$row['CreationDate']);
}

echo json_encode([$result]);

mysqli_close($con);
?>