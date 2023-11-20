<?php 

$servername = 'localhost';
$username = 'root';
$password = '';
$dbname = 'bd_tesis';

$conexion = new mysqli($servername, $username, $password, $dbname);

if($conexion -> connect_errno){
    echo "Ha ocurrido un error en la conexion";
}

 $uid = $_POST['uid'];
 $id = $_POST['id'];


$query = "UPDATE usuario SET uid = '$uid' WHERE id = '$id'";
$resultado = mysqli_query($conexion, $query);

if ($resultado) {
    echo "UID enlazado correctamente";
} else {
    echo "Error al enlazar el UID";
}

 ?>