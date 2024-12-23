package bookNow.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SwaggerRedirectController {
    @GetMapping("/")
    public String redirectToSwagger() {
        return "redirect:https://booknow-98891799677.europe-west6.run.app/swagger-ui/index.html";
    }
}
