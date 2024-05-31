/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.yiverthdevs.dentalcare;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DentalCare {
    // Variables
        PreparedStatement ps = null;
        private ResultSet rs;
        
    // Se establece una variable para la conexion
    private Connection conexion;

    public DentalCare() {
        //Variables donde se almacenan los datos necesarios para la conexion (datos mysql)
        String usuario = "root";
        String password = "";
        String url = "jdbc:mysql://localhost:3308/dentalcare";
        
        //Condicion para establecer la conexion y validar si es exitosa o no
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");      // Conector jdbc
            this.conexion = DriverManager.getConnection(url, usuario, password);      // se le pasan las variables al driver
            System.out.println("Conexion exitosa");       // Mensaje de exito si se establece la conexion
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DentalCare.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            System.out.println("Error de conexion: " + ex.getMessage());    // Mensaje de error de error si no se establece la conexion
        }
    }
    
    // Metodo para el registro de un nuevo usuario
    public int RegistroUsuario(String nombreusuario, String email, String telefono, String contraseña, String genero) {
        // Variable
        int respuesta = 0;
        
        //condicion para agregar los datos del usuario a la tabla (registro usuario) de la base de datos (DentalCare)
        try {
            //Se restablece el valor del autoincremento para el id
            ps = conexion.prepareStatement("alter table RegistroUsuario auto_increment = 1");
            ps.executeUpdate();
            ps.close();
            
            // Se registra un nuevo usuario a la base de datos 
            ps = conexion.prepareStatement("INSERT INTO REGISTROUSUARIO(NOMBREUSUARIO, EMAIL, TELEFONO, CONTRASEÑA, GENERO) VALUES(?,?,?,?,?)");
            ps.setString(1, nombreusuario);
            ps.setString(2, email);
            ps.setString(3, telefono);
            ps.setString(4, contraseña);
            ps.setString(5, genero);
            respuesta = ps.executeUpdate();
            System.out.println("Usuario registrado correctamente");
        } catch (SQLException e) {
            System.out.println("Error al registrar: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return respuesta;
    }
    // Metodo para modificar usuario registrado
    public int modificarUsuario (String nombreusuario, String email, String telefono, String contraseña, String genero, String idregistrousuario){
        // Variable
        int respuesta=0;
         
        try {
            // Se modifica un usuario registrado en la base de datos 
            ps = conexion.prepareStatement("update registrousuario set nombreusuario=?, email=?, telefono=?, contraseña=?, genero=? where idregistrousuario=?");
            ps.setString(1, nombreusuario);
            ps.setString(2, email);
            ps.setString(3, telefono);
            ps.setString(4, contraseña);
            ps.setString(5, genero);
            ps.setString(6, idregistrousuario);
            respuesta=ps.executeUpdate();
            System.out.println("Usuario modificado correctamente");
        } catch (Exception e){
            System.out.println("Error al modificar");
            e.printStackTrace();
            
        }finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        
        return respuesta;
    }
    // Metodo para eliminar usuario registrado en la base de datos 
    public int eliminarUsuario(String idregistrousuario){
        // Variable
        int respuesta=0;
        try {
            ps=conexion.prepareStatement("delete from registrousuario where idregistrousuario=?");
            ps.setString(1, idregistrousuario);
            respuesta=ps.executeUpdate();
            System.out.println("Usuario eliminado correctamente");
            
        } catch (Exception e) {
            System.out.println("Error al eliminar usuario");
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return respuesta;
    }
    
    // Se listan los usuarios registrados en la base de datos para poder visualizarlos en la interfaz 
    public ArrayList<listarRegistroUsuario> ListarUsuario(){
        ArrayList<listarRegistroUsuario> respuesta=new ArrayList<>();
        try {
            // Se selecciona la tabla registro usuario a listar
            ps=conexion.prepareStatement("SELECT * FROM registrousuario");
            rs=ps.executeQuery();
            // Esto es una condicion que valida los campos que van hacer listados 
            while (rs.next()){
                listarRegistroUsuario usuario=new listarRegistroUsuario();
                usuario.setIdregistrousuario(rs.getString("idregistrousuario"));
                usuario.setNombreusuario(rs.getString("nombreusuario"));
                usuario.setEmail(rs.getString("email"));
                usuario.setTelefono(rs.getString("telefono"));
                usuario.setContrseña(rs.getString("contraseña"));
                usuario.setGenero(rs.getString("genero"));
                respuesta.add(usuario);
            }
        } catch (SQLException e) {
            
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return respuesta;
        
        
    }
    
}
