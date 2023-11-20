<?php 

$servername = 'localhost';
$username = 'root';
$password = '';
$dbname = 'bd_tesis';

$conexion = new mysqli($servername, $username, $password, $dbname);

if($conexion -> connect_errno){
    echo "Ha ocurrido un error en la conexion";
}

 $nombre = $_POST['nombre'];
 $apellido = $_POST['apellido'];
 $correo = $_POST['correo'];
 $contraseña = $_POST['contraseña'];

$query = "SELECT * FROM usuario WHERE correo = '$correo'";
$resultado = mysqli_query($conexion, $query);

if($resultado -> num_rows > 0){
    echo "El correo electronico ingresado ya fue registrado";
}else{
    $query ="INSERT INTO usuario (nombre,apellido,correo,contraseña) values ('$nombre','$apellido','$correo','$contraseña')";

    if(mysqli_query($conexion, $query)){
        echo "El registro se ha completado Exito";
    }else{
        echo "Ha ocurrido un Error al registrar"; 
    }
}
 ?>