
package com.da.projetocrud.dao;

import com.da.projetocrud.model.Carro;
import com.da.projetocrud.util.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//cadastrar, atualizar e deletar são void, estrutura parecida

public class CarroDAO {
    public void cadastrar(Carro carro) throws ClassNotFoundException, SQLException {
        Connection con = Conexao.getConexao();
        String sql = "insert into carro (marca, modelo, ano, motor, placa, dono, cnh, telefone, endereco) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement comando = con.prepareStatement(sql);
        //ATRIBUTOS ÚTEIS PARA O MÉTODO
        comando.setString(1, carro.getMarca());
        comando.setString(2, carro.getModelo());
        comando.setInt(3, carro.getAno());
        comando.setString(4, carro.getMotor());
        comando.setString(5, carro.getPlaca());
        comando.setString(6, carro.getDono());
        comando.setString(7, carro.getCnh());
        comando.setString(8, carro.getTelefone());
        comando.setString(9, carro.getEndereco());
    
        comando.execute();
        
        comando.close();
        con.close();
    }
    
    public void atualizar(Carro carro) throws ClassNotFoundException, SQLException {
        Connection con = Conexao.getConexao();
        String sql = "update carro set marca=?, modelo=?, ano=?, motor=?, placa=?, dono=?, cnh=?, telefone=?, endereco=? where id = ?";
        PreparedStatement comando = con.prepareStatement(sql);
        //ATRIBUTOS ÚTEIS PARA O MÉTODO
        comando.setString(1, carro.getMarca());
        comando.setString(2, carro.getModelo());
        comando.setInt(3, carro.getAno());
        comando.setString(4, carro.getMotor());
        comando.setString(5, carro.getPlaca());
        comando.setString(6, carro.getDono());
        comando.setString(7, carro.getCnh());
        comando.setString(8, carro.getTelefone());
        comando.setString(9, carro.getEndereco());
        comando.setInt(10, carro.getId());
    
        comando.execute();
        
        comando.close();
        con.close();
    }
    
    public void deletar(Carro carro) throws ClassNotFoundException, SQLException {
        Connection con = Conexao.getConexao();
        String sql = "delete from carro where id = ?";
        PreparedStatement comando = con.prepareStatement(sql);
        //ATRIBUTOS ÚTEIS PARA O MÉTODO
        comando.setInt(1, carro.getId());
    
        comando.execute();
        
        comando.close();
        con.close();
    }
    //consultarPorId e consultarTodos são return, a estrutura é diferente para retornar dados
    public Carro consultarPorId(Carro carro) throws ClassNotFoundException, SQLException {
        Connection con = Conexao.getConexao();
        String sql = "select id, marca, modelo, ano, motor, placa, dono, cnh, telefone, endereco from carro where id = ?";
        PreparedStatement comando = con.prepareStatement(sql);
        //ATRIBUTOS ÚTEIS PARA O MÉTODO
        comando.setInt(1, carro.getId());
                                                     //agora é ResultSet rs = comando.executeQuery() em consultarPorId e consultarTodos (porque é return)
        ResultSet rs = comando.executeQuery();       //era comando.execute() em cadastrar, atualizar e deletar (porque era void)
        Carro c = new Carro();   //consultarPorId e consultarTodos mudam a partir dessa linha                    
        if (rs.next()) {
            c.setId(rs.getInt("id"));                //no ConsultarPorId inverte, é setId fora e getInt("id") dentro
            c.setMarca(rs.getString("marca"));       //cadastrar, atualizar e deletar era setInt fora e getId() dentro
            c.setModelo(rs.getString("modelo"));
            c.setAno(rs.getInt("ano"));
            c.setMotor(rs.getString("motor"));
            c.setPlaca(rs.getString("placa"));
            c.setDono(rs.getString("dono"));
            c.setCnh(rs.getString("cnh"));
            c.setTelefone(rs.getString("telefone"));
            c.setEndereco(rs.getString("endereco"));
        }
        
        return c;     //consultarPorId e consultarTodos não têm linha1 comando.close() e linha2 con.close(), apenas linha return c
    }
    
    public List<Carro> consultarTodos() throws ClassNotFoundException, SQLException {
        Connection con = Conexao.getConexao();
        String sql = "select id, marca, modelo, ano, motor, placa, dono, cnh, telefone, endereco from carro";
        PreparedStatement comando = con.prepareStatement(sql);
        //ATRIBUTOS ÚTEIS PARA O MÉTODO
        //Não precisa nem de id, por isso não tem a linha setInt(getId("id")
        
        ResultSet rs = comando.executeQuery();   //consultarTodos é List<Carro> lcarro = new ArrayList<>()
        List<Carro> lcarro = new ArrayList<>();  //consultarPorId é Carro car = new Carro()
        while (rs.next()) {
            Carro c = new Carro();               //após a condição while (rs.next()), faz new Carro() 
            c.setId(rs.getInt("id"));            //enquanto houver atributos na próxima linha, guarde eles no objeto car que fizemos o new Carro()
            c.setMarca(rs.getString("marca"));   //consultarTodos pega até id e armazena no objeto car
            c.setModelo(rs.getString("modelo"));
            c.setAno(rs.getInt("ano"));
            c.setMotor(rs.getString("motor"));
            c.setPlaca(rs.getString("placa"));
            c.setDono(rs.getString("dono"));
            c.setCnh(rs.getString("cnh"));
            c.setTelefone(rs.getString("telefone"));
            c.setEndereco(rs.getString("endereco"));
            lcarro.add(c);                           //os 10 atributos foram guardados no objeto car, que foi adicionado à lista lcarro
            
        }
        
        return lcarro;  //quando todos os objetos forem adicionados à lista, retorna a lista lcarro (tabela com várias tuplas)
    }
} //FIM
