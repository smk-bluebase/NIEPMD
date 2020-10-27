<?php
include("../config.php");

$db = new DB_Connect();
$con = $db->connect();

$docId = $_POST['docId'];
$patientName =$_POST['patientName'];
$patientAge = $_POST['patientAge'];
$diagnosis = $_POST['diagnosis'];
$gender = $_POST['gender'];
$posture = $_POST['posture'];
$gait = $_POST['gait'];
$muscle = $_POST['muscle'];
$involuntaryMuscle = $_POST['involuntaryMuscle'];
$nutrionalStatusOfMuscles = $_POST['nutrionalStatusOfMuscles'];
$bulkAndGirthOfMuscles = $_POST['bulkAndGirthOfMuscles'];
$jointRangeOfMotion = $_POST['jointRangeOfMotion'];
$musclePower = $_POST['musclePower'];
$contractureAndDeformity = $_POST['contractureAndDeformity'];
$limpLengthDiscrepancy = $_POST['limpLengthDiscrepancy'];
$developmentReflexesMoro = $_POST['developmentReflexesMoro'];
$galant = $_POST['galant'];
$rooting = $_POST['rooting'];
$sucking = $_POST['sucking'];
$atnr = $_POST['atnr'];
$sntr = $_POST['sntr'];
$extensorThrust = $_POST['extensorThrust'];
$tonyLabryinthReflex = $_POST['tonyLabryinthReflex'];
$righitingReflection = $_POST['righitingReflection'];
$eqilibriumReaction = $_POST['eqilibriumReaction'];
$balanceReaction = $_POST['balanceReaction'];
$supine = $_POST['supine'];
$prone = $_POST['prone'];
$sitting = $_POST['sitting'];
$standing = $_POST['standing'];
$running = $_POST['running'];
$rolling = $_POST['rolling'];
$commingToSitting = $_POST['commingToSitting'];
$crawling = $_POST['crawling'];
$walking = $_POST['walking'];
$standingOnLeg = $_POST['standingOnLeg'];
$climbingUp = $_POST['climbingUp'];
$climbingDown = $_POST['climbingDown'];
$sideWays = $_POST['sideWays'];
$backwards = $_POST['backwards'];
$verticals = $_POST['verticals'];
$upwards = $_POST['upwards'];
$unilateral = $_POST['unilateral'];

$sql_query = "INSERT INTO physiotheraphy (Docid, patname, age, diagnosis, gender, Posture, gait, Muscle, Invoulantary, Nutrional, Bulk, Joint, musclePower, Contracture, Limp, Moro, Galant, Rooting, Sucking, ANTR, SuckingSymmetrical, Extensor, Tony, Righiting, Eqilibrium, Balance, Supine, Prone, Sitting, Standing, Runing, Rolling, comesit, crawling, Walking, StandingLeg, Climnup, Climbdown, Sideways, Backwards, Verticals, SidewaysUpwards, Unilateral)
                VALUES ('$docId', '$patientName', '$patientAge', '$diagnosis', '$gender', '$posture', '$gait', '$muscle', '$involuntaryMuscle', '$nutrionalStatusOfMuscles', '$bulkAndGirthOfMuscles', '$jointRangeOfMotion', '$musclePower', '$contractureAndDeformity', '$limpLengthDiscrepancy', '$developmentReflexesMoro', '$galant', '$rooting', '$sucking', '$atnr', '$sntr', '$extensorThrust', '$tonyLabryinthReflex', '$righitingReflection', '$eqilibriumReaction', '$balanceReaction',
                '$supine', '$prone', '$sitting', '$standing', '$running', '$rolling', '$commingToSitting', '$crawling', '$walking', '$standingOnLeg', '$climbingUp', '$climbingDown', '$sideWays', '$backwards', '$verticals', '$upwards', '$unilateral')";

$res = mysqli_query($con, $sql_query);

$result = $res;

echo json_encode([["status"=>$result]]);

mysqli_close($con);
?>