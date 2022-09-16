package service;

import java.util.Scanner;
import java.time.LocalDate;
import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import dao.TalherDAO;
import model.Talher;
import spark.Request;
import spark.Response;


public class TalherService {

	private TalherDAO talherDAO = new TalherDAO();
	private String form;
	private final int FORM_INSERT = 1;
	private final int FORM_DETAIL = 2;
	private final int FORM_UPDATE = 3;
	private final int FORM_ORDERBY_ID = 1;
	private final int FORM_ORDERBY_DESCRICAO = 2;
	private final int FORM_ORDERBY_PRECO = 3;
	
	
	public TalherService() {
		makeForm();
	}

	
	public void makeForm() {
		makeForm(FORM_INSERT, new Talher(), FORM_ORDERBY_DESCRICAO);
	}

	
	public void makeForm(int orderBy) {
		makeForm(FORM_INSERT, new Talher(), orderBy);
	}

	
	public void makeForm(int tipo, Talher talher, int orderBy) {
		String nomeArquivo = "form.html";
		form = "";
		try{
			Scanner entrada = new Scanner(new File(nomeArquivo));
		    while(entrada.hasNext()){
		    	form += (entrada.nextLine() + "\n");
		    }
		    entrada.close();
		}  catch (Exception e) { System.out.println(e.getMessage()); }
		
		String umtalher = "";
		if(tipo != FORM_INSERT) {
			umtalher += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umtalher += "\t\t<tr>";
			umtalher += "\t\t\t<td align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;<a href=\"/talher/list/1\">Novo talher</a></b></font></td>";
			umtalher += "\t\t</tr>";
			umtalher += "\t</table>";
			umtalher += "\t<br>";			
		}
		
		if(tipo == FORM_INSERT || tipo == FORM_UPDATE) {
			String action = "/talher/";
			String name, descricao, buttonLabel;
			if (tipo == FORM_INSERT){
				action += "insert";
				name = "Inserir talher";
				descricao = "leite, pão, ...";
				buttonLabel = "Inserir";
			} else {
				action += "update/" + talher.getID();
				name = "Atualizar talher (ID " + talher.getID() + ")";
				descricao = talher.getDescricao();
				buttonLabel = "Atualizar";
			}
			umtalher += "\t<form class=\"form--register\" action=\"" + action + "\" method=\"post\" id=\"form-add\">";
			umtalher += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umtalher += "\t\t<tr>";
			umtalher += "\t\t\t<td colspan=\"3\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;" + name + "</b></font></td>";
			umtalher += "\t\t</tr>";
			umtalher += "\t\t<tr>";
			umtalher += "\t\t\t<td colspan=\"3\" align=\"left\">&nbsp;</td>";
			umtalher += "\t\t</tr>";
			umtalher += "\t\t<tr>";
			umtalher += "\t\t\t<td>&nbsp;Descrição: <input class=\"input--register\" type=\"text\" name=\"descricao\" value=\""+ descricao +"\"></td>";
			umtalher += "\t\t\t<td>Preco: <input class=\"input--register\" type=\"text\" name=\"preco\" value=\""+ talher.getPreco() +"\"></td>";
			umtalher += "\t\t\t<td>Quantidade: <input class=\"input--register\" type=\"text\" name=\"quantidade\" value=\""+ talher.getQuantidade() +"\"></td>";
			umtalher += "\t\t</tr>";
			umtalher += "\t\t<tr>";
			umtalher += "\t\t\t<td>&nbsp;Data de fabricação: <input class=\"input--register\" type=\"text\" name=\"dataFabricacao\" value=\""+ talher.getDataFabricacao().toString() + "\"></td>";
			umtalher += "\t\t\t<td>Data de validade: <input class=\"input--register\" type=\"text\" name=\"dataValidade\" value=\""+ talher.getDataValidade().toString() + "\"></td>";
			umtalher += "\t\t\t<td align=\"center\"><input type=\"submit\" value=\""+ buttonLabel +"\" class=\"input--main__style input--button\"></td>";
			umtalher += "\t\t</tr>";
			umtalher += "\t</table>";
			umtalher += "\t</form>";		
		} else if (tipo == FORM_DETAIL){
			umtalher += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umtalher += "\t\t<tr>";
			umtalher += "\t\t\t<td colspan=\"3\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;Detalhar talher (ID " + talher.getID() + ")</b></font></td>";
			umtalher += "\t\t</tr>";
			umtalher += "\t\t<tr>";
			umtalher += "\t\t\t<td colspan=\"3\" align=\"left\">&nbsp;</td>";
			umtalher += "\t\t</tr>";
			umtalher += "\t\t<tr>";
			umtalher += "\t\t\t<td>&nbsp;Descrição: "+ talher.getDescricao() +"</td>";
			umtalher += "\t\t\t<td>Preco: "+ talher.getPreco() +"</td>";
			umtalher += "\t\t\t<td>Quantidade: "+ talher.getQuantidade() +"</td>";
			umtalher += "\t\t</tr>";
			umtalher += "\t\t<tr>";
			umtalher += "\t\t\t<td>&nbsp;Data de fabricação: "+ talher.getDataFabricacao().toString() + "</td>";
			umtalher += "\t\t\t<td>Data de validade: "+ talher.getDataValidade().toString() + "</td>";
			umtalher += "\t\t\t<td>&nbsp;</td>";
			umtalher += "\t\t</tr>";
			umtalher += "\t</table>";		
		} else {
			System.out.println("ERRO! Tipo não identificado " + tipo);
		}
		form = form.replaceFirst("<UM-talher>", umtalher);
		
		String list = new String("<table width=\"80%\" align=\"center\" bgcolor=\"#f3f3f3\">");
		list += "\n<tr><td colspan=\"6\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;Relação de talhers</b></font></td></tr>\n" +
				"\n<tr><td colspan=\"6\">&nbsp;</td></tr>\n" +
    			"\n<tr>\n" + 
        		"\t<td><a href=\"/talher/list/" + FORM_ORDERBY_ID + "\"><b>ID</b></a></td>\n" +
        		"\t<td><a href=\"/talher/list/" + FORM_ORDERBY_DESCRICAO + "\"><b>Descrição</b></a></td>\n" +
        		"\t<td><a href=\"/talher/list/" + FORM_ORDERBY_PRECO + "\"><b>Preço</b></a></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Detalhar</b></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Atualizar</b></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Excluir</b></td>\n" +
        		"</tr>\n";
		
		List<Talher> talhers;
		if (orderBy == FORM_ORDERBY_ID) {                 	talhers = talherDAO.getOrderByID();
		} else if (orderBy == FORM_ORDERBY_DESCRICAO) {		talhers = talherDAO.getOrderByDescricao();
		} else if (orderBy == FORM_ORDERBY_PRECO) {			talhers = talherDAO.getOrderByPreco();
		} else {											talhers = talherDAO.get();
		}

		int i = 0;
		String bgcolor = "";
		for (Talher p : talhers) {
			bgcolor = (i++ % 2 == 0) ? "#fff5dd" : "#dddddd";
			list += "\n<tr bgcolor=\""+ bgcolor +"\">\n" + 
            		  "\t<td>" + p.getID() + "</td>\n" +
            		  "\t<td>" + p.getDescricao() + "</td>\n" +
            		  "\t<td>" + p.getPreco() + "</td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"/talher/" + p.getID() + "\"><img src=\"/image/detail.png\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"/talher/update/" + p.getID() + "\"><img src=\"/image/update.png\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"javascript:confirmarDeletetalher('" + p.getID() + "', '" + p.getDescricao() + "', '" + p.getPreco() + "');\"><img src=\"/image/delete.png\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "</tr>\n";
		}
		list += "</table>";		
		form = form.replaceFirst("<LISTAR-talher>", list);				
	}
	
	
	public Object insert(Request request, Response response) {
		String descricao = request.queryParams("descricao");
		float preco = Float.parseFloat(request.queryParams("preco"));
		int quantidade = Integer.parseInt(request.queryParams("quantidade"));
		LocalDateTime dataFabricacao = LocalDateTime.parse(request.queryParams("dataFabricacao"));
		LocalDate dataValidade = LocalDate.parse(request.queryParams("dataValidade"));
		
		String resp = "";
		
		Talher talher = new Talher(-1, descricao, preco, quantidade, dataFabricacao, dataValidade);
		
		if(talherDAO.insert(talher) == true) {
            resp = "talher (" + descricao + ") inserido!";
            response.status(201); // 201 Created
		} else {
			resp = "talher (" + descricao + ") não inserido!";
			response.status(404); // 404 Not found
		}
			
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}

	
	public Object get(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id"));		
		Talher talher = (Talher) talherDAO.get(id);
		
		if (talher != null) {
			response.status(200); // success
			makeForm(FORM_DETAIL, talher, FORM_ORDERBY_DESCRICAO);
        } else {
            response.status(404); // 404 Not found
            String resp = "talher " + id + " não encontrado.";
    		makeForm();
    		form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");     
        }

		return form;
	}

	
	public Object getToUpdate(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id"));		
		Talher talher = (Talher) talherDAO.get(id);
		
		if (talher != null) {
			response.status(200); // success
			makeForm(FORM_UPDATE, talher, FORM_ORDERBY_DESCRICAO);
        } else {
            response.status(404); // 404 Not found
            String resp = "talher " + id + " não encontrado.";
    		makeForm();
    		form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");     
        }

		return form;
	}
	
	
	public Object getAll(Request request, Response response) {
		int orderBy = Integer.parseInt(request.params(":orderby"));
		makeForm(orderBy);
	    response.header("Content-Type", "text/html");
	    response.header("Content-Encoding", "UTF-8");
		return form;
	}			
	
	public Object update(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
		Talher talher = talherDAO.get(id);
        String resp = "";       

        if (talher != null) {
        	talher.setDescricao(request.queryParams("descricao"));
        	talher.setPreco(Float.parseFloat(request.queryParams("preco")));
        	talher.setQuantidade(Integer.parseInt(request.queryParams("quantidade")));
        	talher.setDataFabricacao(LocalDateTime.parse(request.queryParams("dataFabricacao")));
        	talher.setDataValidade(LocalDate.parse(request.queryParams("dataValidade")));
        	talherDAO.update(talher);
        	response.status(200); // success
            resp = "talher (ID " + talher.getID() + ") atualizado!";
        } else {
            response.status(404); // 404 Not found
            resp = "talher (ID \" + talher.getId() + \") não encontrado!";
        }
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}

	
	public Object delete(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
        Talher talher = talherDAO.get(id);
        String resp = "";       

        if (talher != null) {
            talherDAO.delete(id);
            response.status(200); // success
            resp = "talher (" + id + ") excluído!";
        } else {
            response.status(404); // 404 Not found
            resp = "talher (" + id + ") não encontrado!";
        }
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}
}