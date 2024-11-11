
package com.da.projetocrud.controller;

import com.da.projetocrud.dao.CarroDAO;
import com.da.projetocrud.model.Carro;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "CarroController", urlPatterns = {"/CarroController"})
public class CarroController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
            /* TODO output your page here. You may use following sample code. */
            
        String op = request.getParameter("op"); //CÓDIGO COMEÇA AQUI

        if (op.equals("Novo Carro")) {
            request.getRequestDispatcher("tela_novocadastro.jsp").forward(request, response);
        } else if (op.equals("Cadastrar")) { //usuário não fornece id, por isso não tem getParameter
            
            //faz o getParameter para cada atributo
            //todos atributos tem um getParameter, que pega o que foi escrito no input da index, e armazena na variável txtmarca.
            
            //1º getParameter
            String marca = request.getParameter("txtmarca");
            String modelo = request.getParameter("txtmodelo");
            int ano = Integer.parseInt(request.getParameter("txtano"));
            String motor = request.getParameter("txtmotor");
            String placa = request.getParameter("txtplaca");
            String dono = request.getParameter("txtdono");
            String cnh = request.getParameter("txtcnh");
            String telefone = request.getParameter("txttelefone");
            String endereco = request.getParameter("txtendereco");

            //2º new Carro
            Carro carro = new Carro();  //linha1: new Carro() 
            carro.setMarca(marca);      //linha2: setAtributo(atributo)
            carro.setModelo(modelo);
            carro.setAno(ano);
            carro.setMotor(motor);
            carro.setPlaca(placa);
            carro.setDono(dono);
            carro.setCnh(cnh);
            carro.setTelefone(telefone);
            carro.setEndereco(endereco);

            //3º new CarroDAO com try/catch
            CarroDAO cdao = new CarroDAO();  //lcarro = cdao.consultarTodos() é uma lista local que traz o resultado do método consultarTodos
            try {                            //é lcarro em todos os métodos, pois se o conteúdo sempre será o da lista do consultarTodos(), use o mesmo nome de lista para facilitar
                cdao.cadastrar(carro);       //carro é o objeto com os 10 atributos	
                request.setAttribute("msgSucesso", "Carro cadastrado com sucesso"); //linha1: setAttribute(key "msgSucesso", value "mensagem sucesso")
                List<Carro> lcarro = cdao.consultarTodos(); //linha1: atualiza a lista sem apertar o botão consultarTodos
                request.setAttribute("lcarro", lcarro);     //linha2: atualiza a lista sem apertar o botão consultarTodos
                request.getRequestDispatcher("index.jsp").forward(request, response); //linha2: getRequestDispatcher(envia para a jsp de sucesso)
            } catch (ClassNotFoundException | SQLException ex) {
                request.setAttribute("msgErro", "Falha ao cadastrar o novo carro"); //linha1: setAttribute(variável msgErro, mensagem erro)
                request.getRequestDispatcher("tela_erros.jsp").forward(request, response); //linha2: getRequestDispatcher(envia para a jsp de erro)
            }
        } else if (op.equals("Consultar Todos")) {

            //1º getParameter
            //consultarTodos não precisa de nenhum atributo digitado no input para ser executado
            
            //2º new Carro (consultarTodos é List)          
            List<Carro> lcarro = new ArrayList<>(); //lista lcarro original chamada pelos outros métodos da servlet, para já atualizar a página da index imediatamente

            //3º new CarroDAO com try/catch
            CarroDAO cdao = new CarroDAO();
            try {                               //método consultarTodos é o único sem parâmetro nos ()
                lcarro = cdao.consultarTodos(); //consultarTodos tem seu resultado armazenado na lista criada
                request.setAttribute("msgSucesso", "Consulta de todos os carros bem sucedida");
                request.setAttribute("lcarro", lcarro);
                request.getRequestDispatcher("index.jsp").forward(request, response);
            } catch (ClassNotFoundException | SQLException ex) {
                request.setAttribute("msgErro", "Falha ao consultar a lista de carros");
                request.getRequestDispatcher("tela_erros.jsp").forward(request, response);
            }

        } else if (op.equals("Filtrar")) { //consultarPorId

            //1º getParameter    
            int id = Integer.parseInt(request.getParameter("txtid")); //somente na Servlet, na linha getParameter, que faz Integer.parseInt (pois recebeu parâmetro String "txtid" e precisa converter para int)

            //2º new Carro
            Carro carro = new Carro();
            carro.setId(id);
            //PERGUNTAR ENRICO
            List<Carro> lcarro = new ArrayList<>(); //facilitar o html da index em 1 linha

            //3º new CarroDAO com try/catch
            CarroDAO cdao = new CarroDAO();
            try {
                carro = cdao.consultarPorId(carro); //consultarPorId tem seu resultado armazenado no objeto criado
                if (carro.getDono() != null) { //use atributo String, id não serve
                    lcarro.add(carro);                      //lista facilita o envio para a index
                    request.setAttribute("lcarro", lcarro); //lista facilita o envio para a index
                    request.setAttribute("msgSucesso", "Carro encontrado com sucesso");
                } else {
                    request.setAttribute("msgSucesso", "Carro não encontrado");
                }
                request.getRequestDispatcher("index.jsp").forward(request, response);

            } catch (ClassNotFoundException | SQLException ex) {
                request.setAttribute("msgErro", "Falha ao filtrar por id");
                request.getRequestDispatcher("tela_erros.jsp").forward(request, response);
            }
        } else if (op.equals("Deletar")) {

            //1º getParameter
            int id = Integer.parseInt(request.getParameter("txtid"));

            //2º new Carro
            Carro carro = new Carro();
            carro.setId(id);

            //3º new CarroDAO com try/catch
            CarroDAO cdao = new CarroDAO();
            try {
                cdao.deletar(carro);
                request.setAttribute("msgSucesso", "Carro deletado com sucesso");
                List<Carro> lcarro = cdao.consultarTodos(); //quando deletar, já atualiza a lista e mostra ao usuário
                request.setAttribute("lcarro", lcarro);     //quando deletar, já atualiza a lista e mostra ao usuário
                request.getRequestDispatcher("index.jsp").forward(request, response);
            } catch (ClassNotFoundException | SQLException ex) {
                request.setAttribute("msgErro", "Falha ao deletar");
                request.getRequestDispatcher("tela_erros.jsp").forward(request, response);
            }
        } else if (op.equals("Editar")) { //consultarPorId disfarçado

            //1º getParameter
            int id = Integer.parseInt(request.getParameter("txtid"));

            //2º new Carro
            Carro carro = new Carro();
            carro.setId(id);

            //3º new CarroDAO com try/catch
            CarroDAO cdao = new CarroDAO();
            try {                                       //consultarPorId o métodos cdao tem seu resultado armazenado no objeto carro
                carro = cdao.consultarPorId(carro);     //consultarTodos o método cdao tem seu resultado armazenado na lista lcarro
                request.setAttribute("carro", carro);   //key = "carro" (nome da variável), value é Object, pode ser objeto, lista ou String direto
                request.getRequestDispatcher("tela_formularioeditar.jsp").forward(request, response);

            } catch (ClassNotFoundException | SQLException ex) {
                request.setAttribute("msgErro", "Falha ao editar");
                request.getRequestDispatcher("tela_erros.jsp").forward(request, response);
            }

        } else if (op.equals("Atualizar")) {
            
            //1º getParameter
            int id = Integer.parseInt(request.getParameter("txtid"));
            String marca = request.getParameter("txtmarca");
            String modelo = request.getParameter("txtmodelo");
            int ano = Integer.parseInt(request.getParameter("txtano"));
            String motor = request.getParameter("txtmotor");
            String placa = request.getParameter("txtplaca");
            String dono = request.getParameter("txtdono");
            String cnh = request.getParameter("txtcnh");
            String telefone = request.getParameter("txttelefone");
            String endereco = request.getParameter("txtendereco");

            //2º new Carro
            Carro carro = new Carro();
            carro.setId(id);
            carro.setMarca(marca);
            carro.setModelo(modelo);
            carro.setAno(ano);
            carro.setMotor(motor);
            carro.setPlaca(placa);
            carro.setDono(dono);
            carro.setCnh(cnh);
            carro.setTelefone(telefone);
            carro.setEndereco(endereco);

            //3º new CarroDAO com try/catch
            CarroDAO cdao = new CarroDAO();
            
            try {
                cdao.atualizar(carro);
                request.setAttribute("msgSucesso", "Carro atualizado com sucesso");
                List<Carro> lcarro = cdao.consultarTodos();
                request.setAttribute("lcarro", lcarro);
                request.getRequestDispatcher("index.jsp").forward(request, response);

            } catch (ClassNotFoundException | SQLException ex) {
                request.setAttribute("msgErro", "Falha ao atualizar");
                request.getRequestDispatcher("tela_erros.jsp").forward(request, response);
            }
        }
    }
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
} //FIM
