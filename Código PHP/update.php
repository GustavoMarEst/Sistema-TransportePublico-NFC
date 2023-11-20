<?php 

$servername = 'localhost';
$username = 'root';
$password = '';
$dbname = 'bd_tesis';

$conexion = new mysqli($servername, $username, $password, $dbname);

if($conexion -> connect_errno){
    echo "Ha ocurrido un error en la conexion";
}

$id = $_POST['id'];
$credito = $_POST['credito'];

$query = "UPDATE usuario SET credito = '$credito' WHERE id = '$id'";
$resutaldo = mysqli_query($conexion, $query);

if($resutaldo){
    echo "La recarga se ha realizado correctamente";
}else{
    echo "Ha ocurrido un error al actualizar el crédito";
}

?>