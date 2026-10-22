package web.financialmanagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller 
public class HomeController {

    @GetMapping("/")
    @ResponseBody
    public String homePage(){
        return """
                <!DOCTYPE html>
                    <html lang="pt">
                    <head>
                        <meta charset="UTF-8">
                        <meta name="viewport" content="width=device-width, initial-scale=1.0">
                        <title>Pagina</title>
                    </head>
                    <body>
                        <h1>Hello World!</h1>
                    </body>
                    </html>
                """;
    }

}
