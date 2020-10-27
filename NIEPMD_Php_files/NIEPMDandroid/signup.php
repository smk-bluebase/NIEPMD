<?php
include("config.php");
require 'sendmail.php';

$db = new DB_Connect();
$con = $db->connect();

$userName = $_POST['userName'];
$password = $_POST['password'];
$email = $_POST['email'];
$doctorOrPatient = $_POST['doctorOrPatient'];

$otp = rand(10000, 100000);

$subject = "NIEPMD App OTP";

$message = "Dear ".$userName.",<br/><br/>

Welcome, to NIEPMD! Please enter<br/> 
the OTP below to signup to our services.<br/><br/>

OTP - ".$otp."<br/><br/>

Looking forward to a successful doctor appointment and diagnosis!<br/><br/>

Regards,<br/>
BlueBase Team";

$result = array("status"=>"false");

function insertIntoSignUp(){
    global $con;
    global $userName;
    global $password;
    global $email;
    global $doctorOrPatient;
    global $otp;
    global $subject;
    global $message;
    global $result;

    $sql_query = "SELECT count(*) FROM signup WHERE email = '".$email."'";

    $res = mysqli_query($con, $sql_query);
    
    while($row = mysqli_fetch_array($res)){
        if($row["0"] == 0){
            $sql_query = "INSERT INTO signup (username, password, email, otp, doctororpatient) VALUES ('".$userName."', '".$password."', '".$email."', '".$otp."', '".$doctorOrPatient."')";

            $con->query($sql_query);

            if(sendMail($email, $subject, $message)){
                $result = array("status"=>"true");
            }
            
        }else{
            $sql_query = "UPDATE signup SET `username` = '".$userName."', `password` = '".$password."', `otp` = '".$otp."', `doctororpatient` = '".$doctorOrPatient."' WHERE `email` = '".$email."'";

            $con->query($sql_query);
            
            if(sendMail($email, $subject, $message)){
                $result = array("status"=>"true");
            }

        }
    }
}

if($doctorOrPatient == 0){
    $sql_query = "SELECT count(*) FROM users WHERE email = '".$email."'";

    $res = mysqli_query($con, $sql_query);

    while($row = mysqli_fetch_array($res)){
        if($row['0'] == 0){
            insertIntoSignUp();
        }
    }
}else{
    $sql_query = "SELECT count(*) FROM doctors WHERE docEmail = '".$email."'";

    $res = mysqli_query($con, $sql_query);

    while($row = mysqli_fetch_array($res)){
        if($row['0'] == 0){
            insertIntoSignUp();
        }
    }
}

echo json_encode([$result]);

mysqli_close($con);
?>