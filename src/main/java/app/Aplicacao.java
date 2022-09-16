package app;

import static spark.Spark.*;
import service.TalherService;


public class Aplicacao {
	
	private static TalherService talherService = new TalherService();
	
    public static void main(String[] args) {
        port(6789);
        
        staticFiles.location("/public");
        
        post("/talher/insert", (request, response) -> talherService.insert(request, response));

        get("/talher/:id", (request, response) -> talherService.get(request, response));
        
        get("/talher/list/:orderby", (request, response) -> talherService.getAll(request, response));

        get("/talher/update/:id", (request, response) -> talherService.getToUpdate(request, response));
        
        post("/talher/update/:id", (request, response) -> talherService.update(request, response));
           
        get("/talher/delete/:id", (request, response) -> talherService.delete(request, response));

             
    }
}