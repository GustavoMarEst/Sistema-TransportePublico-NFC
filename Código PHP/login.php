<?php 

$servername = 'localhost';
$username = 'root';
$password = '';
$dbname = 'bd_tesis';

$conexion = new mysqli($servername, $username, $password, $dbname);

if($conexion -> connect_errno){
    echo "Ha ocurrido un error en la conexion";
}

$login = $_POST['login'];
$contraseña = $_POST['contraseña'];

$query = "SELECT * FROM usuario WHERE correo = '$login' AND contraseña ='$contraseña'";
$resutaldo = mysqli_query($conexion, $query);

if($resutaldo -> num_rows > 0){
    $usuario = mysqli_fetch_assoc($resutaldo);
    $nombre = $usuario['nombre'];
    $id = $usuario['id'];
    $uid = $usuario['uid'];
    echo "Se ha iniciado sesión correctamente," . $nombre . "," . $id . "," . $uid;
}else{
    echo "Uno de los campos no es correcto";
}

?>