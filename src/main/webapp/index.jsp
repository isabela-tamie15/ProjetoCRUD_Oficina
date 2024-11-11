
<%@page import="java.util.List"%>
<%@page import="com.da.projetocrud.model.Carro"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html> 
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="estiloCSS/index.css" type="text/css">
        <title>Sistema de Gestão de Carros</title>
    </head>
    <body>
        <header>
            <h1> Sistema de Gestão de Carros </h1>
        </header>
        <% String msg = (String) request.getAttribute("msgSucesso"); %>
        <div id="div-conteudo">
            <p>
                Mensagem: 
                <% if (msg != null) { %>
                <% out.print(msg); %>
                <% } %>
            </p>

            <div id="div-form">
                <form action="CarroController" method="GET">
                    <input type="number" name="txtid" required placeholder="Digite um ID"> 
                    <input type="submit" name="op" value="Filtrar"> 
                </form>

                <form action="CarroController" method="GET">
                    <input type="submit" name="op" value="Novo Carro">
                    <input type="submit" name="op" value="Consultar Todos">
                </form>
            </div>
            <br>
            <% List<Carro> lcarro = (List<Carro>) request.getAttribute("lcarro"); %>
            <% if (lcarro != null) { %> <%-- Se tiver objeto carro na lista, mostra em tabela os atributos --%>
            <table>
                <tr> <%-- linha1 do cabeçalho da tabela --%>
                    <th>Id</th> <%-- coluna1 do cabeçalho, usa tag <th></th> --%>
                    <th>Marca</th> 
                    <th>Modelo</th>
                    <th>Ano</th>
                    <th>Motor</th>
                    <th>Placa</th>
                    <th>Dono</th>
                    <th>CNH</th>
                    <th>Telefone</th>
                    <th>Endereço</th>
                    <th>Editar</th> <%-- coluna11 com imagem, que ao clicar, invoca o método da Servlet --%>
                    <th>Remover</th> <%-- coluna12 com imagem, que ao clicar, invoca o método da Servlet --%>
                </tr> <%-- fim linha1, contendo os atributos acima no cabeçalho da tabela --%>
                <% for (Carro carro : lcarro) { %>
                <tr> <%-- linha2 do conteúdo da tabela --%>
                    <td><% out.print(carro.getId()); %></td> <%-- coluna1 do conteúdo, usa tag <td></td> --%>
                    <td><% out.print(carro.getMarca()); %></td>
                    <td><% out.print(carro.getModelo()); %></td>
                    <td><% out.print(carro.getAno()); %></td>
                    <td><% out.print(carro.getMotor()); %></td>
                    <td><% out.print(carro.getPlaca()); %></td>
                    <td><% out.print(carro.getDono()); %></td>
                    <td><% out.print(carro.getCnh()); %></td>
                    <td><% out.print(carro.getTelefone()); %></td>
                    <td><% out.print(carro.getEndereco()); %></td> <%-- coluna10 do conteúdo, último atributo que o usuário insere dados --%>
                    <td>
                        <a href="CarroController?op=Editar&txtid=<% out.print(carro.getId()); %>"> <%-- tag <a> insere links pelo href="" --%>
                            <img width="20px" src="imagens/edit01.jpg" alt="Editar">
                        </a>
                    </td>
                    <td>                            <%-- href="" e onclick="" SEMPRE ABRA E FECHE ASPAS PARA CADA UM --%>
                        <a href="CarroController?op=Deletar&txtid=<% out.print(carro.getId()); %>" onclick="return confirmarDeletar()">
                            <img width="20px" src="imagens/trash01.jpg" alt="Deletar"> <%-- alt é o texto que explica a imagem, caso ela não carregue na página --%>
                        </a>
                    </td>
                </tr>
                <% } %> <%-- FIM for (Carro carro : lcarro) --%>
            </table> <%-- FIM tabela, fecha os códigos java antes e depois junto --%>
            <% }%> <%-- FIM if (lcarro != null) --%>
            <script src="msg_javascript/script.js"></script> <%-- Sempre 1 linha antes de fechar o </body>, colocar a tag <script></script> com o src = caminho do arquivo --%>
        </div>
    </body>
</html>
