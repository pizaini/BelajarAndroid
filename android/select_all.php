<?php
include('connection.php');
$query = 'select * from mahasiswa';
$result = mysql_query($query) or die(mysql_error());

$data = array();
while($row = mysql_fetch_object($result)){
	$data['mahasiswa'][]= $row;
}
echo json_encode($data);
?>