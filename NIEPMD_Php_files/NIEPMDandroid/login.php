<?php
include("config.php");

$db = new DB_Connect();
$con = $db->connect();

$email = $_POST["email"];
$password = $_POST["password"];
$doctorOrPatient = $_POST["doctorOrPatient"];

$result = array("status"=>"false");

//Patient
if($doctorOrPatient == 0){
	$sql_query = "SELECT * FROM users WHERE email = '".$email."' AND password = '".$password."'";
 
	$res = mysqli_query($con, $sql_query);
	
	while($row = mysqli_fetch_array($res)){
		$result = array("status"=>"true", "doctororpatient"=>0, "id"=>$row['id']);
	}
}
//Doctor
else{
	$sql_query = "SELECT * FROM doctors WHERE docEmail = '".$email."' AND password = '".$password."'";
		
	$res = mysqli_query($con, $sql_query);

	while($row = mysqli_fetch_array($res)){
		$result = array("status"=>"true", "doctororpatient"=>1, "id"=>$row['id']);
	}
}
 
echo json_encode([$result]);
 
mysqli_close($con);
?>